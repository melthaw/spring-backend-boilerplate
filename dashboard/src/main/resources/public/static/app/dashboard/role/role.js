'use strict';

angular.module('webapp')
    .config(function ($stateProvider, webappSettings) {
        $stateProvider
            .state('dashboard.role', {
                url: '/role',
                abstract: true,
                template: '<div ui-view></div>'
            })
            .state('dashboard.role.list', {
                url: '/list',
                templateUrl: webappSettings.tplsBasePath + '/dashboard/role/list.html',
                controller: 'RoleController'
            });
    })
    .factory('roleService', function ($http, $rootScope, webappSettings, pageRequestHelper) {
        return {
            getSysRoles: function () {
                return $http.get(webappSettings.basicApiUrl + '/roles/sysroles');
            },
            getSysRoles4Privilege: function () {
                return $http.get(webappSettings.basicApiUrl + '/roles/sysroles/privilege');
            },
            getAppRoles: function (queryCriteria) {
                return $http.get(webappSettings.basicApiUrl + '/roles/extroles' + pageRequestHelper.buildRequest(queryCriteria));
            },
            getAppRolesList: function () {
                return $http.get(webappSettings.basicApiUrl + '/roles/extroles/list');
            },
            getUsersBySysRoleId: function (id, queryCriteria) {
                return $http.get(webappSettings.basicApiUrl + '/roles/sysroles/' + id + '/users' + pageRequestHelper.buildRequest(queryCriteria));
            },
            getUsersByAppRoleId: function (id, queryCriteria) {
                return $http.get(webappSettings.basicApiUrl + '/roles/extroles/' + id + '/users' + pageRequestHelper.buildRequest(queryCriteria));
            },
            createAppRole: function (request) {
                return $http.post(webappSettings.basicApiUrl + '/roles/extroles', request);
            },
            updateAppRole: function (id, request) {
                return $http.post(webappSettings.basicApiUrl + '/roles/extroles/' + id, request);
            },
            deleteAppRole: function (id) {
                return $http.delete(webappSettings.basicApiUrl + '/roles/extroles/' + id);
            },
            bindUsers4AppRole: function (id, request) {
                return $http.post(webappSettings.basicApiUrl + '/roles/extroles/' + id + '/bindUsers', request);
            },
            unBindUsers4AppRole: function (id, request) {
                return $http.post(webappSettings.basicApiUrl + '/roles/extroles/' + id + '/unBindUsers', request);
            },
            bindUsers4SysRole: function (id, request) {
                return $http.post(webappSettings.basicApiUrl + '/roles/sysroles/' + id + '/bindUsers', request);
            },
            unBindUsers4SysRole: function (id, request) {
                return $http.post(webappSettings.basicApiUrl + '/roles/sysroles/' + id + '/unBindUsers', request);
            }
        };
    })
    .controller('RoleController', function ($scope, $log, $http, $state, dialogs, notifyService, roleService, webappSettings) {

        $scope.sysRoles = [];
        $scope.appRoles = [];
        $scope.users = [];

        $scope.appRoleTotalElements = 0;
        $scope.userTotalElements = 0;


        $scope.appRoleFilter = {};
        $scope.userFilter = {};

        $scope.currentRole = {};

        $scope.selectAllTag = false;

        $scope.getUsersByRole = function (type, roleId) {
            $scope.currentRole.type = type;
            $scope.currentRole.roleId = roleId;
            $scope.refreshUsers();
        };

        $scope.refreshUsers = function () {
            switch ($scope.currentRole.type) {
                case 'sysRole':
                    roleService.getUsersBySysRoleId($scope.currentRole.roleId, $scope.userFilter).success(function (result) {
                        $scope.users = result.content;
                        $scope.userTotalElements = result.totalElements;
                    });
                    break;
                case 'appRole':
                    roleService.getUsersByAppRoleId($scope.currentRole.roleId, $scope.userFilter).success(function (result) {
                        $scope.users = result.content;
                        $scope.userTotalElements = result.totalElements;
                    });
                    break;
                default:
                    return;
            }
        };

        $scope.refreshAppRoles = function () {
            $scope.users = [];
            roleService.getAppRoles($scope.appRoleFilter).success(function (result) {
                $scope.appRoles = result.content;
                $scope.appRoleTotalElements = result.totalElements;
            });
        };

        $scope.refreshSysRoles = function () {
            $scope.users = [];
        };

        $scope.deleteAppRole = function (roleId) {
            dialogs.confirm('确认', '是否要删除？', {'size': 'sm'}).result.then(function () {
                roleService.deleteAppRole(roleId).success(function () {
                    notifyService.success('已删除');
                    $scope.refreshAppRoles();
                });
            });
        };


        $scope.toggleAllItems = function () {
            $scope.selectAllTag = !$scope.selectAllTag;
            angular.forEach($scope.users, function (item) {
                item.checked = $scope.selectAllTag;
            });
        };

        $scope.toggleCheck = function (user) {
            user.checked = !user.checked;
        };

        $scope.addAppRole = function () {
            dialogs.create(webappSettings.tplsBasePath + '/dashboard/role/edit.html', 'RoleEditController', {}, {
                size: 'md'
            }).result.then(function (data) {
                if (data) {
                    $scope.refreshAppRoles();
                }
            });
        };

        $scope.editAppRole = function (appRole) {
            dialogs.create(webappSettings.tplsBasePath + '/dashboard/role/edit.html', 'RoleEditController', {
                id: appRole.id,
                name: appRole.name,
                code: appRole.code
            }, {
                size: 'md'
            }).result.then(function (data) {
                if (data) {
                    $scope.refreshAppRoles();
                }
            });
        };

        $scope.bind = function () {
            if ($scope.currentRole.roleId) {
                dialogs.create(webappSettings.tplsBasePath + '/dashboard/contacts/select-org-user.html', 'SelectOrgUserController', {}, {
                    size: 'lg'
                }).result.then(function (selectedUsers) {
                    if (selectedUsers) {
                        var selectedIds = [];
                        angular.forEach(selectedUsers, function (value) {
                            selectedIds.push(value.id);
                        });
                        dialogs.confirm('确认', '是否要添加选中项？', {'size': 'sm'}).result.then(function () {
                            switch ($scope.currentRole.type) {
                                case 'sysRole':
                                    roleService.bindUsers4SysRole($scope.currentRole.roleId, {userIds: selectedIds}).success(function (result) {
                                        $scope.refreshUsers();
                                    });
                                    break;
                                case 'appRole':
                                    roleService.bindUsers4AppRole($scope.currentRole.roleId, {userIds: selectedIds}).success(function (result) {
                                        $scope.refreshUsers();
                                    });
                                    break;
                                default:
                                    return;
                            }


                        });
                    }
                });
            } else {
                notifyService.error('请先选择一个角色');
            }

        };

        $scope.unbind = function () {
            var selectedIds = [];
            angular.forEach($scope.users, function (user) {
                if (user.checked) {
                    if (user.username != 'administrator') {
                        selectedIds.push(user.id);
                    }
                }
            });
            dialogs.confirm('确认', '是否要解绑选中项？', {'size': 'sm'}).result.then(function () {
                switch ($scope.currentRole.type) {
                    case 'sysRole':
                        roleService.unBindUsers4SysRole($scope.currentRole.roleId, {userIds: selectedIds}).success(function (result) {
                            $scope.refreshUsers();
                        });
                        break;
                    case 'appRole':
                        roleService.unBindUsers4AppRole($scope.currentRole.roleId, {userIds: selectedIds}).success(function (result) {
                            $scope.refreshUsers();
                        });
                        break;
                    default:
                        return;
                }
            });
        };

        $scope.appRoleCurrentPage = 1;
        $scope.appRolePageChanged = function () {
            $scope.appRoleFilter.start = $scope.appRoleCurrentPage - 1;
            $scope.refreshAppRoles();
        };

        $scope.userCurrentPage = 1;
        $scope.userPageChanged = function () {
            $scope.userFilter.start = $scope.userCurrentPage - 1;
            $scope.getUsersByRole($scope.currentRole.type, $scope.currentRole.roleI);
        };

        $scope.init = function () {
            roleService.getSysRoles().success(function (result) {
                $scope.sysRoles = result;
            });
        };

        $scope.init();

    })
    .controller('RoleEditController', function ($scope, $http, $modalInstance, notifyService, tooltipService, roleService, data) {
        $scope.appRole = {};

        $scope.close = function () {
            $modalInstance.close();
        };

        $scope.validateForm = function () {
            var validated = true;
            if (!$scope.appRole.code) {
                tooltipService.showToolTip('appRole.code', '角色编码不能为空');
                validated = false;
            } else if ($scope.appRole.code.length < 2 || $scope.appRole.code.length > 10) {
                tooltipService.showToolTip('appRole.code', '角色编码必须为2至10位字符');
                validated = false;
            }
            if (!$scope.appRole.name) {
                tooltipService.showToolTip('appRole.name', '角色名称不能为空');
                validated = false;
            } else if ($scope.appRole.name.length < 2 || $scope.appRole.name.length > 10) {
                tooltipService.showToolTip('appRole.name', '角色名称必须为2至10位字符');
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
            $scope.validateForm().then(function() {
                if (data.id) {
                    roleService.updateAppRole(data.id, $scope.appRole).success(function (result) {
                        $scope.appRole = result;
                        notifyService.success("已保存");
                        $modalInstance.close($scope.appRole);
                    });
                } else {
                    roleService.createAppRole($scope.appRole).success(function (result) {
                        $scope.appRole = result;
                        notifyService.success("已保存");
                        $modalInstance.close($scope.appRole);
                    });
                }
            });
        };

        $scope.init = function () {
            if (data.id) {
                $scope.appRole = data;
            }
        };

        $scope.init();

    });
