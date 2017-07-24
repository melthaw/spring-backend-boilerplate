'use strict';

angular.module('webapp')
    .controller('SelectUserController', function ($scope, $http, $timeout, $modalInstance, notifyService, tooltipService, contactsService, data) {
            $scope.currentPage = 1;
            $scope.filter = {
                username: '',
                limit: 50
            };
            $scope.receiverFilter = {
                username: ''
            };
            $scope.ccUserFilter = {
                username: ''
            };
            $scope.appusers = [];
            $scope.appusersCache = [];
            $scope.receivers = [];
            $scope.receiversCache = [];
            $scope.ccUsers = [];
            $scope.ccUsersCache = [];
            $scope.currentBizId = null;
            $scope.currentBizType = 'recentContacts';
            $scope.hasUserChecked = false;
            $scope.hasReceiverChecked = false;
            $scope.hasCCUserChecked = false;
            $scope.needPageBar = false;
            $scope.deniedActions = ["", "EDIT", "", ""];
            $scope.editable = true;


            $scope.pageChanged = function () {
                $scope.filter.start = $scope.currentPage - 1;
                $scope.getUsers();
            };

            $scope.onNodeSelect = function (bizId, bizType) {
                console.log('bizId:' + bizId + ', bizType:' + bizType);
                $scope.currentBizId = bizId;
                $scope.currentBizType = bizType;
                $scope.filter.username = '';
                $scope.needPageBar = false;
                $scope.getUsers();
            };

            $scope.getUsers = function () {
                if (!$scope.currentBizType || $scope.currentBizType == 'organization') {
                    contactsService.getUsersByOrg($scope.currentBizId, $scope.filter).success(function (result) {
                        $scope.appusers = result.content;
                        $scope.totalItems = result.totalElements;
                        if ($scope.totalItems && $scope.totalItems > $scope.filter.limit) {
                            $scope.needPageBar = true;
                        } else {
                            $scope.needPageBar = false;
                        }
                    });
                } else if ($scope.currentBizType == 'contactsGroup') {
                    contactsService.getGroupById($scope.currentBizId).success(function (result) {
                        if (result && result.users) {
                            $scope.appusers = result.users;
                            $scope.appusersCache = result.users;
                        } else {
                            $scope.appusersCache = [];
                        }
                    });
                } else if ($scope.currentBizType == 'recentContacts') {
                    contactsService.getRecentUsers().success(function (result) {
                        $scope.appusers = result;
                        $scope.appusersCache = result;
                    });
                }
            };

            $scope.filterUsers = function () {
                // if (!$scope.currentBizType && !$scope.currentBizId) {
                //     return;
                // }

                var usernameCondition = $scope.filter.username;
                usernameCondition = usernameCondition.toLowerCase();
                if (usernameCondition.trim() != "") {
                    contactsService.getAllUsers({username: usernameCondition, limit: 50}).success(function (result) {
                        $scope.appusers = result.content
                    });
                } else {
                    $scope.appusers = []
                }


                // if (!$scope.currentBizType || $scope.currentBizType == 'organization') {
                //     $scope.getUsers();
                // } else if ($scope.currentBizType == 'contactsGroup' || $scope.currentBizType == 'recentContacts') {
                //     $scope.appusers = [];
                //     if ($scope.appusersCache && $scope.appusersCache.length > 0) {
                //         var len = $scope.appusersCache.length;
                //         var usernameCondition = $scope.filter.username;
                //         usernameCondition = usernameCondition.toLowerCase();
                //         for (var i = 0; i < len; i++) {
                //             var appUser = $scope.appusersCache[i];
                //             appUser.checked = false;
                //             if (!usernameCondition || appUser.username.indexOf(usernameCondition) > -1) {
                //                 $scope.appusers.push(appUser);
                //             }
                //         }
                //     }
                // }
            };

            $scope.selectAllClick = function () {
                if ($scope.appusers && $scope.appusers.length > 0) {
                    var checked = !$scope.hasUserChecked;
                    var len = $scope.appusers.length;
                    for (var i = 0; i < len; i++) {
                        $scope.appusers[i].checked = checked;
                    }
                }
            };

            $scope.$watch('appusers', function (newValue, oldValue) {
                var hasChecked = false;
                if ($scope.appusers && $scope.appusers.length > 0) {
                    var len = $scope.appusers.length;
                    for (var i = 0; i < len; i++) {
                        if ($scope.appusers[i].checked) {
                            hasChecked = true;
                            break;
                        }
                    }
                }
                $scope.hasUserChecked = hasChecked;
            }, true);

            $scope.selectAllReceivers = function () {
                if ($scope.receivers && $scope.receivers.length > 0) {
                    var checked = !$scope.hasReceiverChecked;
                    var len = $scope.receivers.length;
                    for (var i = 0; i < len; i++) {
                        $scope.receivers[i].sendSMS = checked;
                    }
                }
            };

            $scope.$watch('receivers', function (newValue, oldValue) {
                var hasChecked = false;
                if ($scope.receivers && $scope.receivers.length > 0) {
                    var len = $scope.receivers.length;
                    for (var i = 0; i < len; i++) {
                        if ($scope.receivers[i].sendSMS) {
                            hasChecked = true;
                            break;
                        }
                    }
                }
                $scope.hasReceiverChecked = hasChecked;
            }, true);

            $scope.selectAllCCUsers = function () {
                if ($scope.ccUsers && $scope.ccUsers.length > 0) {
                    var checked = !$scope.hasCCUserChecked;
                    var len = $scope.ccUsers.length;
                    for (var i = 0; i < len; i++) {
                        $scope.ccUsers[i].sendSMS = checked;
                    }
                }
            };

            $scope.$watch('ccUsers', function (newValue, oldValue) {
                var hasChecked = false;
                if ($scope.ccUsers && $scope.ccUsers.length > 0) {
                    var len = $scope.ccUsers.length;
                    for (var i = 0; i < len; i++) {
                        if ($scope.ccUsers[i].sendSMS) {
                            hasChecked = true;
                            break;
                        }
                    }
                }
                $scope.hasCCUserChecked = hasChecked;
            }, true);

            $scope.getSelectedUsers = function () {
                var selectedUsers = [];
                if ($scope.appusers && $scope.appusers.length > 0) {
                    var len = $scope.appusers.length;
                    for (var i = 0; i < len; i++) {
                        var appUser = $scope.appusers[i];
                        if (appUser.checked) {
                            selectedUsers.push(appUser);
                        }
                    }
                }
                return selectedUsers;
            };

            $scope.addToReceiver = function () {
                var selectedUsers = $scope.getSelectedUsers();
                var len = selectedUsers.length;
                var existCCUserNames = [];
                for (var i = 0; i < len; i++) {
                    var selectedUser = selectedUsers[i];
                    if ($scope.isInCCUsers(selectedUser.id)) {
                        existCCUserNames.push(selectedUser.username);
                    }
                }
                if (existCCUserNames.length > 0) {
                    notifyService.notify('联系人 ' + existCCUserNames.join('，') + ' 在抄送人中已存在!');
                    return;
                }
                for (var i = 0; i < len; i++) {
                    var selectedUser = selectedUsers[i];
                    if (!$scope.isInReceivers(selectedUser.id)) {
                        $scope.receiversCache.push(angular.copy(selectedUser));
                        selectedUser.shownable = false;
                        selectedUser.checked = false;
                    }
                }
                $scope.receivers = $scope.receiversCache;
                $scope.receiverFilter.username = '';
            };

            $scope.recoverUser = function (users, item) {
                angular.forEach(users, function (user, i) {
                    if (user.id == item.id) {
                        user.shownable = true;
                    }
                });
            };

            $scope.isInReceivers = function (userId) {
                var len = $scope.receiversCache.length;
                for (var i = 0; i < len; i++) {
                    var receiver = $scope.receiversCache[i];
                    if (userId == receiver.id) {
                        return true;
                    }
                }
                return false;
            };

            $scope.deleteReceiver = function (userId) {
                var len = $scope.receiversCache.length;
                for (var i = 0; i < len; i++) {
                    var receiver = $scope.receiversCache[i];
                    if (receiver.id == userId) {
                        $scope.receiversCache.splice(i, 1);
                        $scope.recoverUser($scope.appusers, receiver);
                        break;
                    }
                }
                $scope.filterReceivers();
            };

            $scope.addToCCUser = function () {

                var selectedUsers = $scope.getSelectedUsers();
                var len = selectedUsers.length;
                var existReceiverNames = [];
                for (var i = 0; i < len; i++) {
                    var selectedUser = selectedUsers[i];
                    if ($scope.isInReceivers(selectedUser.id)) {
                        existReceiverNames.push(selectedUser.username);
                    }
                }
                if (existReceiverNames.length > 0) {
                    notifyService.notify('联系人 ' + existReceiverNames.join('，') + ' 在主送人中已存在!');
                    return;
                }
                for (var i = 0; i < len; i++) {
                    var selectedUser = selectedUsers[i];
                    if (!$scope.isInCCUsers(selectedUser.id)) {
                        $scope.ccUsersCache.push(angular.copy(selectedUser));
                        selectedUser.shownable = false;
                        selectedUser.checked = false;
                    }
                }
                $scope.ccUsers = $scope.ccUsersCache;
                $scope.ccUserFilter.username = '';
            };

            $scope.isInCCUsers = function (userId) {
                var len = $scope.ccUsersCache.length;
                for (var i = 0; i < len; i++) {
                    var ccUser = $scope.ccUsersCache[i];
                    if (userId == ccUser.id) {
                        return true;
                    }
                }
                return false;
            };

            $scope.deleteCCUser = function (userId) {
                var len = $scope.ccUsersCache.length;
                for (var i = 0; i < len; i++) {
                    var ccUser = $scope.ccUsersCache[i];
                    if (ccUser.id == userId) {
                        $scope.ccUsersCache.splice(i, 1);
                        $scope.recoverUser($scope.appusers, ccUser);
                        break;
                    }
                }
                $scope.filterCCUsers();
            };

            $scope.filterReceivers = function () {
                $scope.receivers = [];
                if (!$scope.receiverFilter.username) {
                    $scope.receivers = $scope.receiversCache;
                    return;
                }
                if ($scope.receiversCache && $scope.receiversCache.length > 0) {
                    var len = $scope.receiversCache.length;
                    var usernameCondition = $scope.receiverFilter.username;
                    usernameCondition = usernameCondition.toLowerCase();
                    for (var i = 0; i < len; i++) {
                        var receiver = $scope.receiversCache[i];
                        if (receiver.username.indexOf(usernameCondition) > -1) {
                            $scope.receivers.push(receiver);
                        }
                    }
                }
            };

            $scope.filterCCUsers = function () {
                $scope.ccUsers = [];
                if (!$scope.ccUserFilter.username) {
                    $scope.ccUsers = $scope.ccUsersCache;
                    return;
                }
                if ($scope.ccUsersCache && $scope.ccUsersCache.length > 0) {
                    var len = $scope.ccUsersCache.length;
                    var usernameCondition = $scope.ccUserFilter.username;
                    usernameCondition = usernameCondition.toLowerCase();
                    for (var i = 0; i < len; i++) {
                        var ccUser = $scope.ccUsersCache[i];
                        if (ccUser.username.indexOf(usernameCondition) > -1) {
                            $scope.ccUsers.push(ccUser);
                        }
                    }
                }
            };

            $scope.confirm = function () {
                if ($scope.receiversCache.length == 0) {
                    notifyService.notify("主送人不能为空!");
                    return;
                }
                $scope.close({
                    receivers: $scope.receiversCache,
                    ccUsers: $scope.ccUsersCache,
                    deniedActions: $scope.deniedActions
                });
            };

            $scope.close = function (selectedUsers) {
                $modalInstance.close(selectedUsers);
            };

            $scope.init = function () {
                if (data.receivers) {
                    $scope.receivers = data.receivers;
                    $scope.receiversCache = data.receivers;
                }
                if (data.ccUsers) {
                    $scope.ccUsers = data.ccUsers;
                    $scope.ccUsersCache = data.ccUsers;
                }

                if (data.editable == false) {
                    $scope.editable = data.editable;
                }

                $scope.getUsers()
            };


            $scope.init();
        }
    );
