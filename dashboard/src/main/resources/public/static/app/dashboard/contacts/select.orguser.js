'use strict';

angular.module('webapp')
    .controller('SelectOrgUserController', function ($scope, $http, $timeout, $modalInstance, notifyService, tooltipService, sysUserService) {
        $scope.currentPage = 1;
        $scope.filter = {
            username: ''
        };
        $scope.appusers = [];
        $scope.selectAll = false;
        $scope.hasUserChecked = false;

        $scope.onFilterKeyup = function (event) {
            if (event.keyCode == 13) {
                $scope.getUsers();
            }
        };

        $scope.getUsers = function () {
            sysUserService.list($scope.filter).success(function (result) {
                $scope.appusers = result.content;
                $scope.totalItems = result.totalElements;
            });
        };

        $scope.pageChanged = function () {
            $scope.filter.start = $scope.currentPage - 1;
            $scope.getUsers();
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

        $scope.confirm = function () {
            var selectedUsers = $scope.getSelectedUsers();
            if (selectedUsers.length == 0) {
                notifyService.notify("请选择联系人");
                return;
            }
            $scope.close(selectedUsers);
        };

        $scope.close = function (selectedUsers) {
            $modalInstance.close(selectedUsers);
        };

        $scope.getUsers();
    });
