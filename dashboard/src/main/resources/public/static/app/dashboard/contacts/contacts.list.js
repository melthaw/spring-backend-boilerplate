'use strict';

angular.module('webapp')
    .controller('ContactsListController', function ($scope, $log, $http, $state, dialogs, notifyService, contactsService, webappSettings) {
        $scope.currentPage = 1;
        $scope.totalItems = 0;
        $scope.filter = {
            username: '',
            position: '',
            contactPhone: '',
            email: ''
        };
        $scope.appusers = [];



        $scope.advancedSearch = false;

        $scope.toggleAdvancedSearch = function () {
            $scope.advancedSearch = !$scope.advancedSearch;
        };


        $scope.onFilterKeyup = function (event) {
            if (event.keyCode == 13) {
                $scope.getUsers();
            }
        };

        $scope.getUsers = function () {
            contactsService.getAllUsers($scope.filter).success(function (result) {
                $scope.appusers = result.content;
                $scope.totalItems = result.totalElements;
                $scope.$parent.totalItems = result.totalElements;
            });
        };

        $scope.spliceOrganizationNames = function(organizations) {
            var organizationNames = [];
            angular.forEach(organizations, function(organization) {
                organizationNames.push(organization.name);
            });
            return organizationNames.join('，');
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

        $scope.resetFilters = function () {
            $scope.filter = {
                username: '',
                position: '',
                contactPhone: '',
                email: ''
            };
        };

        $scope.getUsers();
    });
