'use strict';

angular.module('webapp')
    .config(function ($stateProvider, webappSettings) {
        $stateProvider
            .state('dashboard.sysAuthEvent', {
                url: '/sysAuthEvent',
                abstract: true,
                template: '<div ui-view></div>'
            })
            .state('dashboard.sysAuthEvent.list', {
                url: '/list',
                templateUrl: webappSettings.tplsBasePath + '/dashboard/sysAuthEvent/list.html',
                controller: 'SysAuthEventController'
            })
            .state('dashboard.sysAuthEvent.detail', {
                url: '/:id',
                templateUrl: webappSettings.tplsBasePath + '/dashboard/sysAuthEvent/detail.html',
                controller: 'SysAuthEventDetailController'
            });
    })
    .factory('sysAuthEventService', function ($http, $rootScope, webappSettings, pageRequestHelper) {
        return {
            listAuthEventPage: function (queryCriteria) {
                return $http.get(webappSettings.basicApiUrl + '/sysAuthEvents' + pageRequestHelper.buildRequest(queryCriteria));
            },
            getAuthEventDetail: function (id) {
                return $http.get(webappSettings.basicApiUrl + '/sysAuthEvents/' + id);
            },
            deleteAuthEventsByDay: function (day) {
                return $http.delete(webappSettings.basicApiUrl + '/sysAuthEvents/byDay/' + day);
            },
            deleteAuthEventsBeforeDay: function (day) {
                return $http.delete(webappSettings.basicApiUrl + '/sysAuthEvents/beforeDay/' + day);
            }
        };
    })
    .controller('SysAuthEventController', function ($scope, $log, $http, $filter, $state, dialogs, notifyService, sysAuthEventService, webappSettings) {

        $scope.filter = {
            username: '',
            succeed: '',
            beginDate: '',
            endDate: ''
        };
        $scope.sysAuthEvents = [];

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
            sysAuthEventService.listAuthEventPage($scope.filter).success(function (result) {
                $scope.sysAuthEvents = result.content;
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
            dialogs.create(webappSettings.tplsBasePath + '/dashboard/sysAuthEvent/detail.html', 'SysAuthEventDetailController', item, {
                size: 'md'
            });
        };


        $scope.pageChanged = function () {
            $scope.filter.start = $scope.currentPage - 1;
            $scope.refreshList();
        };


        $scope.deleteHistories = function () {
            dialogs.create(webappSettings.tplsBasePath + '/dashboard/sysAuthEvent/confirmDate.html', 'SysAuthEventDateConfirmController', {}, {
                'windowClass': "modal inmodal",
                'size': 'sm'
            }).result.then(function (selectedDate) {
                if (selectedDate) {
                    // $log.info(selectedDate)
                    sysAuthEventService.deleteAuthEventsBeforeDay(selectedDate).success(function (result) {
                        notifyService.success("已删除");
                    });
                }
            });
        };

        $scope.refreshList();

    })
    .controller('SysAuthEventDetailController', function ($scope, $log, $http, $state, $modalInstance, data) {
        $scope.item = data;

        $scope.close = function () {
            $modalInstance.close(data);
        };
    })
    .controller('SysAuthEventDateConfirmController', function ($scope, $timeout, $log, $http, $state, $modalInstance, notifyService) {
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
