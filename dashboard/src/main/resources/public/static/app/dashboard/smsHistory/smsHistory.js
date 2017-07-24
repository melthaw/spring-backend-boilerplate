'use strict';

angular.module('webapp')
    .config(function ($stateProvider, webappSettings) {
        $stateProvider
            .state('dashboard.smsHistory', {
                url: '/smsHistory',
                abstract: true,
                template: '<div ui-view></div>'
            })
            .state('dashboard.smsHistory.list', {
                url: '/list',
                templateUrl: webappSettings.tplsBasePath + '/dashboard/smsHistory/list.html',
                controller: 'SmsHistoryController'
            })
            .state('dashboard.smsHistory.detail', {
                url: '/:id',
                templateUrl: webappSettings.tplsBasePath + '/dashboard/smsHistory/detail.html',
                controller: 'SmsHistoryDetailController'
            });
    })
    .factory('smsHistoryService', function ($http, $rootScope, webappSettings, pageRequestHelper) {
        return {
            listAuthEventPage: function (queryCriteria) {
                return $http.get(webappSettings.basicApiUrl + '/smsHistories' + pageRequestHelper.buildRequest(queryCriteria));
            }
        };
    })
    .controller('SmsHistoryController', function ($scope, $log, $http, $filter, $state, $timeout, dialogs, notifyService, smsHistoryService, webappSettings) {

        $scope.filter = {
            cellphone: '',
            category: '',
            status: '',
            beginDate: '',
            endDate: ''
        };
        $scope.smsHistories = [];

        $scope.advancedSearch = false
        $scope.totalElements = 0;
        $scope.currentPage = 1;

        $scope.toggleAdvancedSearch = function () {
            $scope.advancedSearch = !$scope.advancedSearch;
        };

        $scope.refreshList = function () {
            smsHistoryService.listAuthEventPage($scope.filter).success(function (result) {
                $scope.smsHistories = result.content;
                $scope.totalElements = result.totalElements;
            });
        };

        $scope.resetFilters = function () {
            $scope.filter = {
                cellphone: '',
                category: '',
                status: '',
                beginDate: '',
                endDate: ''
            };
        };

        $scope.startOpen = function ($event) {
            $event.preventDefault();
            $event.stopPropagation();

            $scope.startOpened = true;
        };

        $scope.endOpen = function ($event) {
            $event.preventDefault();
            $event.stopPropagation();

            $scope.endOpened = true;
        };

        $scope.viewDetail = function (item) {
            dialogs.create(webappSettings.tplsBasePath + '/dashboard/smsHistory/detail.html', 'SmsHistoryDetailController', item, {
                size: 'md'
            });
        };


        $scope.pageChanged = function () {
            $scope.filter.start = $scope.currentPage - 1;
            $scope.refreshList();
        };

        $timeout(function () {
            jQuery('.datepicker').datepicker({
                language: 'zh-CN',
                format: 'yyyy-mm-dd',
                autoclose: true
            });
        }, 500);

        $scope.refreshList();

    })
    .controller('SmsHistoryDetailController', function ($scope, $log, $http, $state, $modalInstance, data) {
        $scope.item = data;

        $scope.close = function () {
            $modalInstance.close(data);
        };
    });
