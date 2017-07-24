'use strict';

angular.module('webapp')
    .config(function ($stateProvider, webappSettings) {
        $stateProvider
            .state('dashboard.sysuser', {
                url: '/sysuser',
                abstract: true,
                template: '<div ui-view></div>'
            })
            .state('dashboard.sysuser.list', {
                url: '/list',
                templateUrl: webappSettings.tplsBasePath + '/dashboard/sysuser/list.html',
                controller: 'SysUserController'
            });
    })
    .factory('sysUserService', function ($http, $rootScope, webappSettings, pageRequestHelper) {
        return {
            list: function (queryCriteria) {
                return $http.get(webappSettings.basicApiUrl + '/sysusers' + pageRequestHelper.buildRequest(queryCriteria));
            },
            findById: function (id) {
                return $http.get(webappSettings.basicApiUrl + '/sysusers/' + id);
            },
            create: function (sysuser) {
                return $http.post(webappSettings.basicApiUrl + '/sysusers', sysuser);
            },
            update: function (id, sysuser) {
                return $http.post(webappSettings.basicApiUrl + '/sysusers/' + id, sysuser);
            },
            changePwd: function (id, sysuser) {
                return $http.post(webappSettings.basicApiUrl + '/sysusers/' + id + '/password', sysuser);
            },
            delete: function (id) {
                return $http.delete(webappSettings.basicApiUrl + '/sysusers/' + id);
            },
            disable: function (id) {
                return $http.post(webappSettings.basicApiUrl + '/sysusers/' + id + '/disable', {});
            },
            enable: function (id) {
                return $http.post(webappSettings.basicApiUrl + '/sysusers/' + id + '/enable', {});
            }
        };
    })
    .controller('SysUserController', function ($scope, $log, $http, $state, dialogs, notifyService, sysUserService, webappSettings) {
        $scope.currentPage = 1;
        $scope.filter = {
            username: '',
            cellphone: '',
            email: '',
            enabled: ''
        };
        $scope.sysusers = [];
        $scope.features = {advancedSearch: false};

        $scope.advancedSearch = false;

        $scope.toggleAdvancedSearch = function () {
            $scope.advancedSearch = !$scope.advancedSearch;
        };

        $scope.refreshList = function () {
            sysUserService.list($scope.filter).success(function (result) {
                $scope.sysusers = result.content;
                $scope.totalItems = result.totalElements;
            });
        };

        $scope.pageChanged = function () {
            $log.log('Page changed to: ' + $scope.currentPage);
            $scope.filter.start = $scope.currentPage - 1;
            $scope.refreshList();
        };

        $scope.addUser = function () {
            dialogs.create(webappSettings.tplsBasePath + '/dashboard/sysuser/add.html', 'AddSysUserController', {}, {
                'windowClass': "modal inmodal",
                'size': 'md'
            }).result.then($scope.refreshList);
        };

        $scope.editUser = function (id) {
            dialogs.create(webappSettings.tplsBasePath + '/dashboard/sysuser/edit.html', 'EditSysUserController', {'id': id}, {
                'windowClass': "modal inmodal",
                'size': 'md'
            }).result.then($scope.refreshList);
        };

        $scope.changePwd = function (id) {
            dialogs.create(webappSettings.tplsBasePath + '/dashboard/sysuser/changepwd.html', 'ChangeSysUserPwdController', {'id': id}, {
                windowClass: "modal inmodal",
                size: 'md'
            });
        };

        $scope.deleteUser = function (id) {
            dialogs.confirm('确认', '是否删除用户？删除后不能撤销', {'size': 'sm'}).result.then(function () {
                sysUserService.delete(id).success(function (result) {
                    notifyService.success('已删除');
                    $scope.refreshList();
                })
            });
        };

        $scope.disableUser = function (id) {
            dialogs.confirm('确认', '是否禁用用户？', {'size': 'sm'}).result.then(function () {
                sysUserService.disable(id).success(function (result) {
                    notifyService.success('已禁用');
                    $scope.refreshList();
                })
            });
        };

        $scope.enableUser = function (id) {
            dialogs.confirm('确认', '是否启用用户？', {'size': 'sm'}).result.then(function () {
                sysUserService.enable(id).success(function (result) {
                    notifyService.success('已启用');
                    $scope.refreshList();
                })
            });
        };

        $scope.showDetail = function (item) {
            dialogs.create(webappSettings.tplsBasePath + '/dashboard/sysuser/detail.html', 'SysUserDetailController', item, {
                size: 'md'
            });
        };

        $scope.resetFilter = function () {
            $scope.filter = {
                username: '',
                cellphone: '',
                email: '',
                enabled: ''
            };
        };

        $scope.pageChanged = function () {
            $scope.filter.start = $scope.currentPage - 1;
            $scope.refreshList();
        };

        $scope.refreshList();
    })
    .controller('SysUserDetailController', function ($scope, $log, $http, $state, $modalInstance, data) {
        $scope.sysuser = data;

        $scope.close = function () {
            $modalInstance.close(data);
        };
    })
    .controller('AddSysUserController', function ($scope, $http, $modalInstance, notifyService, tooltipService, sysUserService, validationService) {
        $scope.sysuser = {};

        $scope.validateForm = function () {

            var validated = true;
            if (!$scope.sysuser.username) {
                tooltipService.showToolTip('sysuser.username', '用户名不能为空');
                validated = false;
            } else if ($scope.sysuser.username.length < 2 || $scope.sysuser.username.length > 20) {
                tooltipService.showToolTip('sysuser.username', '用户名必须为2至20位字符');
                validated = false;
            }
            if (!$scope.sysuser.cellphone) {
                tooltipService.showToolTip('sysuser.cellphone', '联系电话不能为空');
                validated = false;
            } else if (!validationService.validatePhoneNumber($scope.sysuser.cellphone)) {
                tooltipService.showToolTip('sysuser.cellphone', '请输入正确的联系电话');
                validated = false;
            }
            if (!$scope.sysuser.email) {
                tooltipService.showToolTip('sysuser.email', '邮箱不能为空');
                validated = false;
            } else if (!validationService.validateEmail($scope.sysuser.email)) {
                tooltipService.showToolTip('sysuser.email', '请输入正确的邮箱地址');
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
                    sysUserService.create($scope.sysuser).success(function (result) {
                        sysUserService.changePwd(result, $scope.sysuser).success(function (data) {
                            notifyService.success("已保存");
                            $scope.close();
                        });

                    });
                }
            );
        };

        $scope.close = function () {
            $modalInstance.close($scope.sysuser);
        };
    })
    .controller('EditSysUserController', function ($scope, $http, $modalInstance, notifyService, tooltipService, sysUserService, validationService, data) {
        $scope.id = data.id;
        $scope.sysuser = {};
        $scope.init = function () {
            sysUserService.findById($scope.id).success(function (result) {
                $scope.sysuser = result;
            });
        };

        $scope.validateForm = function () {

            var validated = true;

            if (!$scope.sysuser.cellphone) {
                tooltipService.showToolTip('sysuser.cellphone', '联系电话不能为空');
                validated = false;
            } else if (!validationService.validatePhoneNumber($scope.sysuser.cellphone)) {
                tooltipService.showToolTip('sysuser.cellphone', '请输入正确的联系电话');
                validated = false;
            }
            if (!$scope.sysuser.email) {
                tooltipService.showToolTip('sysuser.email', '邮箱不能为空');
                validated = false;
            } else if (!validationService.validateEmail($scope.sysuser.email)) {
                tooltipService.showToolTip('sysuser.email', '请输入正确的邮箱地址');
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
                    sysUserService.update($scope.id, $scope.sysuser).success(function (result) {
                        notifyService.success("已修改");
                        $scope.close();
                    });
                }
            );
        };

        $scope.close = function () {
            $modalInstance.close($scope.sysuser);
        };

        $scope.init();
    })
    .controller('ChangeSysUserPwdController', function ($scope, $http, $modalInstance, tooltipService, notifyService, sysUserService, data) {
        $scope.id = data.id;
        $scope.sysuser = {};
        $scope.init = function () {
            sysUserService.findById($scope.id).success(function (result) {
                $scope.sysuser = result;
            });
        };

        $scope.validateForm = function () {

            var validated = true;
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
                    sysUserService.changePwd($scope.id, $scope.sysuser).success(function (result) {
                        notifyService.success("已修改");
                        $scope.close();
                    });
                }
            );
        };

        $scope.close = function () {
            $modalInstance.close($scope.sysuser);
        };
        $scope.init();
    });
