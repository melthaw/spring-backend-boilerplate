'use strict';

angular.module('webapp')
    .config(function ($stateProvider, webappSettings) {
        $stateProvider
            .state('dashboard.appAuditEvent', {
                url: '/appAuditEvent',
                template: '<div ui-view></div>'
            })
            .state('dashboard.appAuditEvent.list', {
                url: '/list',
                templateUrl: webappSettings.tplsBasePath + '/dashboard/appAuditEvent/list.html',
                controller: 'AppAuditEventController'
            })
            .state('dashboard.appAuditEvent.detail', {
                url: '/:id',
                templateUrl: webappSettings.tplsBasePath + '/dashboard/appAuditEvent/detail.html',
                controller: 'AppAuditEventDetailController'
            });
    })
    .factory('appAuditEventService', function ($http, $rootScope, webappSettings, pageRequestHelper) {
        return {
            listAuditEventPage: function (queryCriteria) {
                return $http.get(webappSettings.basicApiUrl + '/appAuditEvents' + pageRequestHelper.buildRequest(queryCriteria));
            },
            getAuditEventDetail: function (id) {
                return $http.get(webappSettings.basicApiUrl + '/appAuditEvents/' + id);
            },
            deleteAuditEventsByDay: function (day) {
                return $http.delete(webappSettings.basicApiUrl + '/appAuditEvents/byDay/' + day);
            },
            deleteAuditEventsBeforeDay: function (day) {
                return $http.delete(webappSettings.basicApiUrl + '/appAuditEvents/beforeDay/' + day);
            }
        };
    })
    .controller('AppAuditEventController', function ($scope, $log, $http, $filter, $state, $timeout, dialogs, notifyService, appAuditEventService, webappSettings) {

        $scope.filter = {
            requestedBy: '',
            error: '',
            beginDate: '',
            endDate: ''
        };
        $scope.audits = [];

        $scope.advancedSearch = false;
        $scope.totalElements = 0;
        $scope.currentPage = 1;

        $scope.toggleAdvancedSearch = function () {
            $scope.advancedSearch = !$scope.advancedSearch;
        };

        $scope.refreshList = function () {
            appAuditEventService.listAuditEventPage($scope.filter).success(function (result) {
                $scope.audits = result.content;
                $scope.totalElements = result.totalElements;
            });
        };

        $scope.resetFilters = function () {
            $scope.filter = {
                requestedBy: '',
                error: '',
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
            dialogs.create(webappSettings.tplsBasePath + '/dashboard/appAuditEvent/detail.html', 'AppAuditEventDetailController', {id: item.id}, {
                size: 'lg'
            });
        };


        $scope.pageChanged = function () {
            $scope.filter.start = $scope.currentPage - 1;
            $scope.refreshList();
        };


        $scope.deleteHistories = function () {
            dialogs.create(webappSettings.tplsBasePath + '/dashboard/appAuditEvent/confirmDate.html', 'AppAuditEventDateConfirmController', {}, {
                'windowClass': "modal inmodal",
                'size': 'sm'
            }).result.then(function (selectedDate) {
                if (selectedDate) {
                    // $log.info(selectedDate)
                    appAuditEventService.deleteAuditEventsBeforeDay(selectedDate).success(function (result) {
                        notifyService.success("已删除");
                    });
                }
            });
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
    .controller('AppAuditEventDetailController', function ($scope, $log, $http, $state, $modalInstance, appAuditEventService, data) {
        $scope.item = {};

        $scope.close = function () {
            $modalInstance.close(data);
        };

        $scope.init = function () {
            if (data.id) {
                appAuditEventService.getAuditEventDetail(data.id).success(function (result) {
                    $scope.item = result;
                });
            }
        };
        $scope.init();

    })
    .controller('AppAuditEventDateConfirmController', function ($scope, $timeout, $log, $http, $state, $modalInstance, notifyService) {
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
