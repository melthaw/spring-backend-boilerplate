'use strict';

angular.module('webapp')
    .config(function ($stateProvider, webappSettings) {
        $stateProvider
            .state('dashboard.appAuthEvent', {
                url: '/appAuthEvent',
                abstract: true,
                template: '<div ui-view></div>'
            })
            .state('dashboard.appAuthEvent.list', {
                url: '/list',
                templateUrl: webappSettings.tplsBasePath + '/dashboard/appAuthEvent/list.html',
                controller: 'AppAuthEventController'
            })
            .state('dashboard.appAuthEvent.detail', {
                url: '/:id',
                templateUrl: webappSettings.tplsBasePath + '/dashboard/appAuthEvent/detail.html',
                controller: 'AppAuthEventDetailController'
            });
    })
    .factory('appAuthEventService', function ($http, $rootScope, webappSettings, pageRequestHelper) {
        return {
            listAuthEventPage: function (queryCriteria) {
                return $http.get(webappSettings.basicApiUrl + '/appAuthEvents' + pageRequestHelper.buildRequest(queryCriteria));
            },
            getAuthEventDetail: function (id) {
                return $http.get(webappSettings.basicApiUrl + '/appAuthEvents/' + id);
            },
            deleteAuthEventsByDay: function (day) {
                return $http.delete(webappSettings.basicApiUrl + '/appAuthEvents/byDay/' + day);
            },
            deleteAuthEventsBeforeDay: function (day) {
                return $http.delete(webappSettings.basicApiUrl + '/appAuthEvents/beforeDay/' + day);
            }
        };
    })
    .controller('AppAuthEventController', function ($scope, $log, $http, $filter, $state, dialogs, notifyService, appAuthEventService, webappSettings) {

        $scope.filter = {
            username: '',
            succeed: '',
            beginDate: '',
            endDate: ''
        };
        $scope.appAuthEvents = [];

        $scope.advancedSearch = false
        $scope.totalElements = 0;
        $scope.currentPage = 1;
        $scope.format = 'yyyy-MM-dd';

        $scope.toggleAdvancedSearch = function () {
            $scope.advancedSearch = !$scope.advancedSearch;
        };

        $scope.refreshList = function () {
            $scope.filter.beginDate = $filter('date')($scope.filter.beginDate, "yyyy-MM-dd")
            $scope.filter.endDate = $filter('date')($scope.filter.endDate, "yyyy-MM-dd")
            appAuthEventService.listAuthEventPage($scope.filter).success(function (result) {
                $scope.appAuthEvents = result.content;
                $scope.totalElements = result.totalElements;
            });
        };

        $scope.resetFilters = function () {
            $scope.filter = {
                username: '',
                succeed: '',
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
            dialogs.create(webappSettings.tplsBasePath + '/dashboard/appAuthEvent/detail.html', 'AppAuthEventDetailController', item, {
                size: 'md'
            });
        };


        $scope.pageChanged = function () {
            $scope.filter.start = $scope.currentPage - 1;
            $scope.refreshList();
        };


        $scope.deleteHistories = function () {
            dialogs.create(webappSettings.tplsBasePath + '/dashboard/appAuthEvent/confirmDate.html', 'AppAuthEventDateConfirmController', {}, {
                'windowClass': "modal inmodal",
                'size': 'sm'
            }).result.then(function (selectedDate) {
                if (selectedDate) {
                    // $log.info(selectedDate)
                    appAuthEventService.deleteAuthEventsBeforeDay(selectedDate).success(function (result) {
                        notifyService.success("已删除");
                    });
                }
            });
        };

        $scope.refreshList();

    })
    .controller('AppAuthEventDetailController', function ($scope, $log, $http, $state, $modalInstance, data) {
        $scope.item = data;

        $scope.close = function () {
            $modalInstance.close(data);
        };
    })
    .controller('AppAuthEventDateConfirmController', function ($scope, $timeout, $log, $http, $state, $modalInstance, notifyService) {
        $scope.item = {};
        $scope.selectedDate = null;

        $scope.close = function () {
            $modalInstance.close();
        };

        $timeout(function () {
            jQuery('.datepicker').datepicker({
                language: 'zh-CN',
                format: 'yyyy-mm-dd',
                autoclose: true,
                endDate: '-15d'
            });
        }, 500);

        $scope.ok = function () {
            if ($scope.selectedDate) {
                $modalInstance.close($scope.selectedDate);
            } else {
                notifyService.notify("请选择时间");
            }

        };
    });
