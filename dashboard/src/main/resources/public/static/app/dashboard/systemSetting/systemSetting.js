'use strict';

angular.module('webapp')
    .config(function ($stateProvider, webappSettings) {
        $stateProvider
            .state('dashboard.systemSetting', {
                url: '/systemSetting',
                template: '<div ui-view></div>'
            })
            .state('dashboard.systemSetting.detail', {
                url: '/detail',
                templateUrl: webappSettings.tplsBasePath + '/dashboard/systemSetting/detail.html',
                controller: 'SystemSettingController'
            })
            .state('dashboard.systemSetting.edit', {
                url: '/edit',
                templateUrl: webappSettings.tplsBasePath + '/dashboard/systemSetting/edit.html',
                controller: 'SystemSettingEditController'
            });
    })
    .factory('systemSettingService', function ($http, $rootScope, webappSettings) {
        return {
            getSystemSetting: function () {
                return $http.get(webappSettings.basicApiUrl + '/settings/system');
            },
            updateSystemSetting: function (systemSetting) {
                return $http.post(webappSettings.basicApiUrl + '/settings/system', systemSetting);
            }
        };
    })
    .controller('SystemSettingController', function ($scope, $log, $http, $filter, $state, dialogs, notifyService, systemSettingService, webappSettings) {
        $scope.systemSetting = {};

        $scope.edit = function () {
            $state.go('dashboard.systemSetting.edit');
        };

        $scope.init = function () {
            systemSettingService.getSystemSetting().success(function (result) {
                $scope.systemSetting = result;
            });
        };

        $scope.init();

    })
    .controller('SystemSettingEditController', function ($scope, $rootScope, $log, $http, $state, dialogs, uiUploader, validationService, sharedAttachmentService, systemSettingService, notifyService, tooltipService, webappSettings) {
        $scope.systemSetting = {};
        $scope.files = [];

        $scope.fileChanged = function (e) {
            var files = e.target.files;
            uiUploader.addFiles(files);
            $scope.files = uiUploader.getFiles();
            $scope.$apply();
        };

        $scope.btn_remove = function (file) {
            dialogs.confirm('确认', '是否要删除附件？', {'size': 'sm'}).result.then(function () {
                if (file.id) {
                    sharedAttachmentService.deleteById(file.id).success(function (result) {
                        $scope.systemSetting.fileObjectId = null;
                    });
                } else {
                    $scope.systemSetting.fileObjectId = null;
                }
                uiUploader.removeFile(file);
                $scope.files = [];
            });
        };

        $scope.validateForm = function () {
            var validated = true;
            if (!$scope.systemSetting.name) {
                tooltipService.showToolTip('systemSetting.name', '名称不能为空');
                validated = false;
            }
            if (!$scope.systemSetting.name.trim().length > 25) {
                tooltipService.showToolTip('systemSetting.name', '名称不能超过25个字');
                validated = false;
            }
            if (!$scope.systemSetting.contactEmail) {
                tooltipService.showToolTip('systemSetting.contactEmail', '电子邮箱不能为空');
                validated = false;
            } else if (!validationService.validateEmail($scope.systemSetting.contactEmail)) {
                tooltipService.showToolTip('systemSetting.contactEmail', '请输入正确的电子邮箱');
                validated = false;
            }
            if (!$scope.systemSetting.contactPhone) {
                tooltipService.showToolTip('systemSetting.contactPhone', '联系电话不能为空');
                validated = false;
            } else if (!validationService.validatePhoneNumber($scope.systemSetting.contactPhone)) {
                tooltipService.showToolTip('systemSetting.contactPhone', '请输入正确的联系电话');
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
            $scope.validateForm().then(function () {
                // if (uiUploader.getFiles().length > 0) {
                //     angular.forEach(uiUploader.getFiles(), function (file) {
                //         var request = {
                //             name: $scope.systemSetting.name,
                //             category: 'SystemSetting',
                //             uploadedBy: $rootScope.currentUser.username,
                //             file: file
                //         };
                //         if (!request.file.id) {
                //             sharedAttachmentService.upload(request).success(function (fileId) {
                //                 request.file.loaded = true;
                //                 request.file.id = fileId;
                //                 $scope.systemSetting.fileObjectId = fileId;
                //                 $scope.saveSystemSetting();
                //             });
                //         } else {
                //             $scope.saveSystemSetting();
                //         }
                //     });
                // } else {
                //     $scope.saveSystemSetting();
                // }

                $scope.saveSystemSetting();
            });
        };

        $scope.saveSystemSetting = function () {
            systemSettingService.updateSystemSetting($scope.systemSetting).success(function (result) {
                notifyService.success("已保存");
                $state.go('dashboard.systemSetting.detail');
            });
        };

        $scope.back = function () {
            $state.go('dashboard.systemSetting.detail');
        };


        $scope.init = function () {
            uiUploader.removeAll();
            systemSettingService.getSystemSetting().success(function (result) {
                $scope.systemSetting = result;
                if ($scope.systemSetting.fileObjectId) {
                    sharedAttachmentService.getFileObjectDetail($scope.systemSetting.fileObjectId).success(function (file) {
                        var buildFile = {
                            id: file.id,
                            name: file.originalFilename,
                            loaded: true
                        };
                        $scope.files.push(buildFile);
                        uiUploader.addFiles($scope.files);
                    });
                }
            });
        };

        $scope.init();

    });
