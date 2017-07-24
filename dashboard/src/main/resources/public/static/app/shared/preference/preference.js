'use strict';

angular.module('webapp')
    .config(function ($stateProvider, webappSettings) {
        $stateProvider
            .state('preference', {
                url: '/preference',
                abstract: true,
                template: '<div ui-view></div>'
            })
            .state('preference.detail', {
                url: '/detail',
                templateUrl: webappSettings.tplsBasePath + '/shared/preference/detail.html',
                controller: 'PreferenceController'
            })
            .state('preference.edit', {
                url: '/edit',
                templateUrl: webappSettings.tplsBasePath + '/shared/preference/edit.html',
                controller: 'PreferenceController'
            })
            .state('preference.changePwd', {
                url: '/changePwd',
                templateUrl: webappSettings.tplsBasePath + '/shared/preference/changepwd.html',
                controller: 'PreferenceChangePwdController'
            });
    })
    .factory('preferenceService', function ($http, $rootScope, webappSettings) {
        return {
            currentValue: function () {
                return $http.get(webappSettings.basicApiUrl + '/my/profile');
            },
            saveValue: function (data) {
                return $http.post(webappSettings.basicApiUrl + '/my/profile', data);
            },
            changePwd: function (data) {
                return $http.post(webappSettings.basicApiUrl + '/my/password', data);
            },
            deleteAvatar: function () {
                return $http.delete(webappSettings.basicApiUrl + '/my/avatar');
            },
            updateAvatar: function (avatarId) {
                return $http.post(webappSettings.basicApiUrl + '/my/avatar/' + avatarId, {});
            }
        };
    })
    .controller('PreferenceController', function ($scope, $rootScope, $log, $http, $state, $modalInstance, dialogs, preferenceService, webappSettings) {
        $scope.profile = {};

        $scope.close = function () {
            $modalInstance.close();
        };

        $scope.init = function () {
            preferenceService.currentValue().success(function (json) {
                if (json) {
                    $rootScope.currentUser.avatarId = json.avatarId;
                }
                $scope.profile = json;
            })
        };

        $scope.save = function () {
            preferenceService.saveValue($scope.profile).success(function (json) {
            })
        };

        $scope.edit = function () {
            dialogs.create(webappSettings.tplsBasePath + '/shared/preference/profileEdit.html', 'PreferenceEditController', {}, {
                windowClass: "modal inmodal",
                size: 'md'
            }).result.then(function (result) {
                $scope.init();
            });
        };

        $scope.init();
    })
    .controller('PreferenceEditController', function ($scope, $rootScope, $log, $http, $state, $timeout, $modalInstance, $filter, dialogs, uiUploader, notifyService, tooltipService, validationService, sharedAttachmentService, preferenceService) {
        $scope.profile = {};
        $scope.files = [];

        $scope.fileChanged = function (e) {
            var files = e.target.files;
            var avatar = []
            angular.forEach(files, function (file) {
                if (file.size > 300 * 1023) {
                    notifyService.error('头像[' + file.name + ']不能大于300k');
                } else {
                    avatar.push(file);
                }
            });

            uiUploader.addFiles(avatar);
            // uiUploader.addFiles(files);
            $scope.files = uiUploader.getFiles();
            $scope.$apply();
            $timeout(function () {
                $scope.btn_upload();
            }, 500);
        };

        $scope.btn_upload = function () {
            if (uiUploader.getFiles().length > 0) {
                angular.forEach(uiUploader.getFiles(), function (file) {
                    if (file.size > 300 * 1023) {
                        notifyService.error('头像[' + file.name + ']不能大于300k');
                    } else {
                        var request = {
                            name: $scope.profile.username,
                            category: 'Avatar',
                            uploadedBy: $rootScope.currentUser.username,
                            file: file
                        };
                        if (!request.file.id) {
                            sharedAttachmentService.upload(request).success(function (fileId) {
                                request.file.loaded = true;
                                request.file.id = fileId;
                                $scope.profile.avatarId = fileId;
                                $scope.updateAvatar(fileId);
                            });
                        }
                    }
                });
            }
        };

        $scope.btn_remove = function (file) {
            dialogs.confirm('确认', '是否要删除头像？', {'size': 'sm'}).result.then(function () {
                // if (file.id) {
                //     sharedAttachmentService.deleteById(file.id).success(function (result) {
                //         $scope.profile.avatarId = null;
                //     });
                // } else {
                //     $scope.profile.avatarId = null;
                // }
                preferenceService.deleteAvatar().success(function (json) {
                    uiUploader.removeFile(file);
                    $scope.files = [];
                    $scope.profile.avatarId = null;
                });
            });
        };

        $scope.startOpen = function ($event) {
            $event.preventDefault();
            $event.stopPropagation();

            $scope.startOpened = true;
        };

        $scope.close = function () {
            $modalInstance.close();
        };

        $scope.init = function () {
            uiUploader.removeAll();
            preferenceService.currentValue().success(function (json) {
                $scope.profile = json;
                if ($scope.profile.birthday) {
                    $scope.profile.birthday = $filter('date')($scope.profile.birthday, "yyyy-MM-dd")
                }
                if ($scope.profile.avatarId) {
                    sharedAttachmentService.getFileObjectDetail($scope.profile.avatarId).success(function (file) {
                        var buildFile = {
                            id: file.id,
                            name: file.originalFilename,
                            loaded: true
                        };
                        $scope.files.push(buildFile);
                        uiUploader.addFiles($scope.files);
                    });
                }
            })
        };

        $scope.validateForm = function () {
            var validated = true;
            if (!$scope.profile.cellphone) {
                tooltipService.showToolTip('profile.cellphone', '联系电话不能为空');
                validated = false;
            } else if (!validationService.validatePhoneNumber($scope.profile.cellphone)) {
                tooltipService.showToolTip('profile.cellphone', '请输入正确的联系电话');
                validated = false;
            }
            if (!$scope.profile.email) {
                tooltipService.showToolTip('profile.email', '邮箱不能为空');
                validated = false;
            } else if (!validationService.validateEmail($scope.profile.email)) {
                tooltipService.showToolTip('profile.email', '请输入正确的邮箱地址');
                validated = false;
            }

            return {
                then: function (func) {
                    if (validated) {
                        func.apply($scope);
                    }
                }
            }
        };


        $scope.updateAvatar = function (avatarId) {
            preferenceService.updateAvatar(avatarId).success(function (json) {
                notifyService.success("上传头像成功");
            })
        };

        $scope.save = function () {
            $scope.validateForm().then(function () {
                $scope.saveProfile();
            });
        };

        $scope.saveProfile = function () {
            $scope.profile.birthday = $filter('date')($scope.profile.birthday, "yyyy-MM-dd")
            preferenceService.saveValue($scope.profile).success(function (json) {
                notifyService.success("已保存");
                $modalInstance.close('success');
            })
        };

        $timeout(function () {
            jQuery('#datepicker').datepicker({
                language: 'zh-CN',
                format: 'yyyy-mm-dd',
                autoclose: true
            });
        }, 500);

        $scope.init();
    })
    .controller('PreferenceChangePwdController', function ($scope, $http, $modalInstance, tooltipService, notifyService, preferenceService) {
        $scope.sysuser = {
            oldPassword: '',
            newPassword: '',
            renewPassword: ''
        };


        $scope.validateForm = function () {
            var validated = true;
            if (!$scope.sysuser.oldPassword) {
                tooltipService.showToolTip('sysuser.oldPassword', '密码不能为空');
                validated = false;
            }
            if (!$scope.sysuser.newPassword) {
                tooltipService.showToolTip('sysuser.newPassword', '密码不能为空');
                validated = false;
            }
            if (!$scope.sysuser.renewPassword) {
                tooltipService.showToolTip('sysuser.renewPassword', '请再次输入密码');
                validated = false;
            }
            else if ($scope.sysuser.newPassword != $scope.sysuser.renewPassword) {
                tooltipService.showToolTip('sysuser.renewPassword', '密码不一致');
                validated = false;
            }

            return {
                then: function (func) {
                    if (validated) {
                        func.apply($scope);
                    }
                }
            }
        };

        $scope.save = function () {
            $scope.validateForm().then(
                function () {
                    preferenceService.changePwd($scope.sysuser).success(function (result) {
                        notifyService.success("已修改");
                        $scope.close('success');
                    });
                }
            );
        };

        $scope.close = function (msg) {
            $modalInstance.close(msg);
        };
    });
