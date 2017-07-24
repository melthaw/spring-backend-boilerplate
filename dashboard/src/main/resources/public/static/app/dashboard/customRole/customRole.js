'use strict';

angular.module('webapp')
    .config(function ($stateProvider, webappSettings) {
        $stateProvider
            .state('dashboard.customRole', {
                url: '/customRole',
                abstract: true,
                template: '<div ui-view></div>'
            })
            .state('dashboard.customRole.list', {
                url: '/list',
                templateUrl: webappSettings.tplsBasePath + '/dashboard/customRole/list.html',
                controller: 'CustomRoleController'
            });
    })
    .controller('CustomRoleController', function ($scope, $log, $http, $state, dialogs, notifyService, roleService, privilegeService, webappSettings) {
        $scope.appRoles = [];
        $scope.users = [];
        $scope.bindedRoles = [];

        $scope.currentTypedRoleCode = '';
        $scope.virtual = false;
        $scope.treeOptions = {
            nodeChildren: "children",
            dirSelectable: true,
            injectClasses: {
                ul: "a1",
                li: "a2",
                liSelected: "a7",
                iExpanded: "a3",
                iCollapsed: "a4",
                iLeaf: "a5",
                label: "a6",
                labelSelected: "a8"
            }
        };
        $scope.showSelected = function (node) {
            $scope.currentTypedRoleCode = node.code;
            $scope.virtual = node.virtual;
            $scope.showSelectedData();
        };


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
            roleService.getUsersByAppRoleId($scope.currentRole.roleId, $scope.userFilter).success(function (result) {
                $scope.users = result.content;
                $scope.userTotalElements = result.totalElements;
            });
        };

        $scope.refreshAppRoles = function () {
            $scope.users = [];
            roleService.getAppRoles($scope.appRoleFilter).success(function (result) {
                $scope.appRoles = result.content;
                $scope.appRoleTotalElements = result.totalElements;
            });
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
                            roleService.bindUsers4AppRole($scope.currentRole.roleId, {userIds: selectedIds}).success(function (result) {
                                $scope.refreshUsers();
                            });
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
                roleService.unBindUsers4AppRole($scope.currentRole.roleId, {userIds: selectedIds}).success(function (result) {
                    $scope.refreshUsers();
                });
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
            $scope.getUsersByRole($scope.currentRole.type, $scope.currentRole.roleId);
        };

        $scope.showSelectedData = function () {
            privilegeService.listGrantedRoles($scope.currentTypedRoleCode).success(function (result) {
                $scope.roles = result;
                angular.forEach($scope.appRoles, function (appRole) {
                    appRole.checked = false;
                });
                angular.forEach($scope.roles, function (role) {
                    angular.forEach($scope.appRoles, function (appRole) {
                        if (appRole.code.trim() == role.code.trim()) {
                            appRole.checked = true;
                        }
                    });
                });
            });
        };

        $scope.save = function () {
            $scope.save4AppRole();
        };

        $scope.viewPrivileges = function (code) {
            var typedCode = 'APP_ROLE:' + code;
            dialogs.create(webappSettings.tplsBasePath + '/dashboard/privilege/privilegesView.html', 'PrivilegesViewController', {typedCode: typedCode}, {
                size: 'sm'
            });
        };

        $scope.save4AppRole = function () {
            var bindTypedRoleCodes = [];
            var unBindTypedRoleCodes = [];
            angular.forEach($scope.appRoles, function (role) {
                if (role.checked) {
                    bindTypedRoleCodes.push('APP_ROLE:' + role.code);
                } else {
                    unBindTypedRoleCodes.push('APP_ROLE:' + role.code);
                }
            });
            dialogs.confirm('确认', '确认保存？', {'size': 'sm'}).result.then(function () {
                privilegeService.grantRolesToResource($scope.currentTypedRoleCode, bindTypedRoleCodes).success(function (json) {
                    privilegeService.revokeRolesFromResource($scope.currentTypedRoleCode, unBindTypedRoleCodes).success(function (json) {
                        $scope.showSelectedData();
                        notifyService.success('已保存');
                    });
                });
            });
        };


        $scope.selectAllAppRoleTag = false;


        $scope.toggleAllAppRoles = function () {
            $scope.selectAllAppRoleTag = !$scope.selectAllAppRoleTag;
            angular.forEach($scope.appRoles, function (role) {
                role.checked = $scope.selectAllAppRoleTag;
            });
        };

        $scope.toggleCheck = function (role) {
            role.checked = !role.checked;
        };

        $scope.init = function () {
            roleService.getAppRoles($scope.appRoleFilter).success(function (result) {
                $scope.appRoles = result.content;
                $scope.appRoleTotalElements = result.totalElements;
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
            $scope.validateForm().then(function () {
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

    }) ;
