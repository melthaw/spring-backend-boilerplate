'use strict';

angular.module('webapp')
    .controller('ContactsOrgTreeController', function ($scope, $log, $http, $state, dialogs, notifyService, contactsService, webappSettings) {
        $scope.$parent.switchTab('OrgTreeTab');
        $scope.currentPage = 1;
        $scope.totalItems = 0;
        $scope.filter = {
            username: '',
            limit: 10
        };
        $scope.appusers = [];
        $scope.currentOrgId = null;
        $scope.onNodeSelect = function(orgId) {
            $scope.currentOrgId = orgId;
            $scope.filter.username = '';
            $scope.getUsers();
        };

        $scope.onFilterKeyup = function(event) {
            if (event.keyCode == 13) {
                $scope.getUsers();
            }
        };

        $scope.getUsers = function() {
            if (!$scope.currentOrgId) {
                return;
            }
            contactsService.getUsersByOrg($scope.currentOrgId, $scope.filter).success(function (result) {
                $scope.appusers = result.content;
                $scope.totalItems = result.totalElements;
            });
        };

        $scope.pageChanged = function () {
            $scope.filter.start = $scope.currentPage - 1;
            $scope.getUsers();
        };

        $scope.showDetail = function (appuser) {
            dialogs.create(webappSettings.tplsBasePath + '/dashboard/organization/app-user-detail.html', 'AppUserDetailController', appuser, {
                size: 'md'
            });
        };
    });
