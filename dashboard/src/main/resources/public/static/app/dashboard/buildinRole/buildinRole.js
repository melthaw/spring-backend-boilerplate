'use strict';

angular.module('webapp')
    .config(function ($stateProvider, webappSettings) {
        $stateProvider
            .state('dashboard.buildinRole', {
                url: '/buildinRole',
                abstract: true,
                template: '<div ui-view></div>'
            })
            .state('dashboard.buildinRole.list', {
                url: '/list',
                templateUrl: webappSettings.tplsBasePath + '/dashboard/buildinRole/list.html',
                controller: 'BuildinRoleController'
            });
    })
    .controller('BuildinRoleController', function ($scope, $log, $http, $state, dialogs, notifyService, roleService, privilegeService, webappSettings) {

        $scope.sysRoles = [];
        $scope.users = [];

        $scope.userTotalElements = 0;
        $scope.userFilter = {};

        $scope.currentRole = {};
        $scope.selectAllTag = false;

        $scope.bindedRoles = [];

        $scope.currentTypedRoleCode = '';  //SYS_ROLE,APP_ROLE
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

        $scope.getUsersByRole = function (type, roleId) {
            $scope.currentRole.type = type;
            $scope.currentRole.roleId = roleId;
            $scope.refreshUsers();
        };

        $scope.refreshUsers = function () {
            roleService.getUsersBySysRoleId($scope.currentRole.roleId, $scope.userFilter).success(function (result) {
                $scope.users = result.content;
                $scope.userTotalElements = result.totalElements;
            });
        };

        $scope.refreshSysRoles = function () {
            $scope.users = [];
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
                            roleService.bindUsers4SysRole($scope.currentRole.roleId, {userIds: selectedIds}).success(function (result) {
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
                roleService.unBindUsers4SysRole($scope.currentRole.roleId, {userIds: selectedIds}).success(function (result) {
                    $scope.refreshUsers();
                });
            });
        };

        $scope.userCurrentPage = 1;
        $scope.userPageChanged = function () {
            $scope.userFilter.start = $scope.userCurrentPage - 1;
            $scope.getUsersByRole($scope.currentRole.type, $scope.currentRole.roleId);
        };

        $scope.showSelected = function (node) {
            $scope.currentTypedRoleCode = node.code;
            $scope.virtual = node.virtual;
            $scope.showSelectedData();
        };

        $scope.showSelectedData = function () {
            privilegeService.listGrantedRoles($scope.currentTypedRoleCode).success(function (result) {
                $scope.roles = result;
                angular.forEach($scope.sysRoles, function (sysRole) {
                    sysRole.checked = false;
                });
                angular.forEach($scope.roles, function (role) {
                    angular.forEach($scope.sysRoles, function (sysRole) {
                        if (sysRole.code.trim() == role.code.trim()) {
                            sysRole.checked = true;
                        }
                    });
                });
            });
        };

        $scope.save = function () {
            $scope.save4SysRole();
        };


        $scope.viewPrivileges = function (code) {
            var typedCode = 'SYS_ROLE:' + code;
            dialogs.create(webappSettings.tplsBasePath + '/dashboard/privilege/privilegesView.html', 'PrivilegesViewController', {typedCode: typedCode}, {
                size: 'sm'
            });
        };

        $scope.save4SysRole = function () {
            var bindTypedRoleCodes = [];
            var unBindTypedRoleCodes = [];
            angular.forEach($scope.sysRoles, function (role) {
                if (role.checked) {
                    bindTypedRoleCodes.push('SYS_ROLE:' + role.code);
                } else {
                    unBindTypedRoleCodes.push('SYS_ROLE:' + role.code);
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

        $scope.selectAllSysRoleTag = false;

        $scope.toggleAllSysRoles = function () {
            $scope.selectAllSysRoleTag = !$scope.selectAllSysRoleTag;
            angular.forEach($scope.sysRoles, function (role) {
                role.checked = $scope.selectAllSysRoleTag;
            });
        };

        $scope.toggleCheck = function (role) {
            role.checked = !role.checked;
        };


        $scope.init = function () {
            roleService.getSysRoles().success(function (result) {
                $scope.sysRoles = result;
            });
        };

        $scope.init();

    });
