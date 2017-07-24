'use strict';

angular.module('webapp')
    .config(function ($stateProvider, webappSettings) {
        $stateProvider
            .state('dashboard.archiveduser', {
                url: '/archiveduser',
                abstract: true,
                template: '<div ui-view></div>'
            })
            .state('dashboard.archiveduser.list', {
                url: '/list',
                templateUrl: webappSettings.tplsBasePath + '/dashboard/archiveduser/list.html',
                controller: 'ArchivedUserController'
            });
    })
    .factory('archivedUserService', function ($http, $rootScope, webappSettings, pageRequestHelper) {
        return {
            list: function (queryCriteria) {
                return $http.get(webappSettings.basicApiUrl + '/archivedusers' + pageRequestHelper.buildRequest(queryCriteria));
            },
            findById: function (id) {
                return $http.get(webappSettings.basicApiUrl + '/archivedusers/' + id);
            }
        };
    })
    .controller('ArchivedUserController', function ($scope, $log, $http, $state, dialogs, notifyService, archivedUserService, webappSettings) {
        $scope.currentPage = 1;
        $scope.filter = {
            username: ''
        };
        $scope.archivedusers = [];
        $scope.features = {advancedSearch: false};

        $scope.advancedSearch = false;

        $scope.toggleAdvancedSearch = function () {
            $scope.advancedSearch = !$scope.advancedSearch;
        };

        $scope.refreshList = function () {
            archivedUserService.list($scope.filter).success(function (result) {
                $scope.archivedusers = result.content;
                $scope.totalItems = result.totalElements;
            });
        };

        $scope.pageChanged = function () {
            $log.log('Page changed to: ' + $scope.currentPage);
            $scope.filter.start = $scope.currentPage - 1;
            $scope.refreshList();
        };

        $scope.showDetail = function (item) {
            dialogs.create(webappSettings.tplsBasePath + '/dashboard/archiveduser/detail.html', 'ArchivedUserDetailController', item, {
                size: 'md'
            });
        };

        $scope.resetFilter = function () {
            $scope.filter = {
                username: ''
            };
        };

        $scope.pageChanged = function () {
            $scope.filter.start = $scope.currentPage - 1;
            $scope.refreshList();
        };

        $scope.refreshList();
    })
    .controller('ArchivedUserDetailController', function ($scope, $log, $http, $state, $modalInstance, data) {
        $scope.archiveduser = data;

        $scope.close = function () {
            $modalInstance.close(data);
        };
    });

