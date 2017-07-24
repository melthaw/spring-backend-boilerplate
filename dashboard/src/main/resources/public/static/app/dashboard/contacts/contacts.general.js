'use strict';

angular.module('webapp')
    .controller('ContactsGeneralController', function ($scope, $log, $http, $state, dialogs, notifyService, contactsService, webappSettings) {
        $scope.currentPage = 1;
        $scope.totalItems = 0;
        $scope.filter = {
            name: ''
        };
        $scope.groups= [];
        $scope.appusers = [];
        $scope.selectedGroupId = null;

        $scope.selectAll = false;
        $scope.hasUserChecked = false;

        $scope.onFilterKeyup = function(event) {
            if (event.keyCode == 13) {
                $scope.getGroups();
            }
        };

        $scope.pageChanged = function () {
            $scope.filter.start = $scope.currentPage - 1;
            $scope.getGroups();
        };

        $scope.selectGroup = function(groupId) {
            $scope.selectedGroupId = groupId;
            $scope.getUsersByGroup();
        };

        $scope.getGroups = function() {
            contactsService.getGroups($scope.filter).success(function (result) {
                $scope.groups = result.content;
                $scope.totalItems = result.totalElements;
            });
        };

        $scope.getUsersByGroup = function() {
            contactsService.getGroupById($scope.selectedGroupId).success(function (result) {
                if (result && result.users) {
                    $scope.appusers = result.users;
                } else {
                    $scope.appusers = [];
                }
            });
        };

        $scope.selectAllClick = function() {
            if ($scope.appusers && $scope.appusers.length >0) {
                var checked = !$scope.hasUserChecked;
                var len = $scope.appusers.length;
                for (var i=0; i<len; i++) {
                    $scope.appusers[i].checked = checked;
                }
            }
        };

        $scope.$watch('appusers', function(newValue, oldValue) {
            var hasChecked = false;
            if ($scope.appusers && $scope.appusers.length >0) {
                var len = $scope.appusers.length;
                for (var i=0; i<len; i++) {
                    if ($scope.appusers[i].checked) {
                        hasChecked = true;
                        break;
                    }
                }
            }
            $scope.hasUserChecked = hasChecked;
        }, true);

        $scope.getSelectedUserIds = function() {
            var selectedUserIds = [];
            if ($scope.appusers && $scope.appusers.length >0) {
                var len = $scope.appusers.length;
                for (var i=0; i<len; i++) {
                    var appUser = $scope.appusers[i];
                    if (appUser.checked) {
                        selectedUserIds.push(appUser.id);
                    }
                }
            }
            return selectedUserIds;
        };

        $scope.addGroup = function () {
            dialogs.create(webappSettings.tplsBasePath + '/dashboard/contacts/group-add.html', 'GroupCreateController', {}, {
                'windowClass': "modal inmodal",
                'size': 'md'
            }).result.then($scope.getGroups);
        };

        $scope.editGroup = function (id) {
            dialogs.create(webappSettings.tplsBasePath + '/dashboard/contacts/group-edit.html', 'GroupEditController', {'id': id}, {
                'windowClass': "modal inmodal",
                'size': 'md'
            }).result.then($scope.getGroups);
        };

        $scope.deleteGroup = function (id) {
            dialogs.confirm('确认', '是否删除联系人分组？删除后不能撤销', {'size': 'sm'}).result.then(function () {
                contactsService.deleteGroup(id).success(function (result) {
                    notifyService.success('已删除');
                    $scope.getGroups();
                    $scope.appusers = [];
                    $scope.selectedGroupId = null;
                });
            });
        };

        $scope.selectOrgUser = function() {
            dialogs.create(webappSettings.tplsBasePath + '/dashboard/contacts/select-org-user.html', 'SelectOrgUserController', {}, {
                'windowClass': "modal inmodal",
                'size': 'lg'
            }).result.then(function(selectedUsers) {
                    console.log('selectedUsers:' + selectedUsers);
                    $scope.addUsersToGroup(selectedUsers);
                });
        };

        $scope.addUsersToGroup = function(selectedUsers) {
            if (selectedUsers && selectedUsers.length > 0) {
                var userIds = [];
                var len = selectedUsers.length;
                for (var i=0; i<len; i++) {
                    userIds.push(selectedUsers[i].id);
                }
                contactsService.addUsersToGroup($scope.selectedGroupId, userIds).success(function (result) {
                    notifyService.success('已添加');
                    $scope.getUsersByGroup();
                });
            }
        };

        $scope.removeUsersFromGroup = function() {
            dialogs.confirm('确认', '是否删除选中的联系人？删除后不能撤销', {'size': 'sm'}).result.then(function () {
                contactsService.removeUsersFromGroup($scope.selectedGroupId, $scope.getSelectedUserIds()).success(function (result) {
                    notifyService.success('已删除');
                    $scope.getUsersByGroup();
                });
            });
        };

        $scope.showUserDetail = function (appuser) {
            dialogs.create(webappSettings.tplsBasePath + '/dashboard/organization/app-user-detail.html', 'AppUserDetailController', appuser, {
                size: 'md'
            });
        };

        $scope.selectUser = function() {
            dialogs.create(webappSettings.tplsBasePath + '/dashboard/contacts/select-user.html', 'SelectUserController', {}, {
                'windowClass': "modal inmodal",
                'size': 'lg'
            }).result.then(function(selectResult) {
                    if (selectResult) {
                        console.log('receivers size:' + selectResult.receivers.length);
                        console.log('ccUsers size:' + selectResult.ccUsers.length);
                    }
                    console.log('selectResult:\n' + JSON.stringify(selectResult));
                });
        };

        $scope.getGroups();
    })
    .controller('GroupCreateController', function ($scope, $http, $timeout, $modalInstance, notifyService, tooltipService, contactsService) {
        $scope.group = {};

        $scope.validateForm = function () {
            var validated = true;
            if (!$scope.group.name) {
                tooltipService.showToolTip('group.name', '分组名称不能为空');
                validated = false;
            } else if ($scope.group.name.length < 2 || $scope.group.name.length > 10) {
                tooltipService.showToolTip('group.name', '分组名称必须为2至10位字符');
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
                    contactsService.createGroup($scope.group).success(function (result) {
                        notifyService.success("已保存");
                        $scope.close();
                    });
                }
            );
        };

        $scope.close = function () {
            $modalInstance.close($scope.group);
        };
    })
    .controller('GroupEditController', function ($scope, $http, $timeout, $modalInstance, notifyService, tooltipService, contactsService, data) {
        $scope.group = {};
        $scope.init = function () {
            contactsService.getGroupById(data.id).success(function (result) {
                $scope.group = result;
            });
        };

        $scope.validateForm = function () {
            var validated = true;
            if (!$scope.group.name) {
                tooltipService.showToolTip('group.name', '分组名称不能为空');
                validated = false;
            } else if ($scope.group.name.length < 2 || $scope.group.name.length > 10) {
                tooltipService.showToolTip('group.name', '分组名称必须为2至10位字符');
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
                    contactsService.updateGroup($scope.group).success(function (result) {
                        notifyService.success("已修改");
                        $scope.close();
                    });
                }
            );
        };

        $scope.close = function () {
            $modalInstance.close($scope.appuser);
        };

        $scope.init();
    });
