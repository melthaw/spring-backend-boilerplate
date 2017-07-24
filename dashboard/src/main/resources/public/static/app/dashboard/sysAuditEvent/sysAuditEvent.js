'use strict';

angular.module('webapp')
    .config(function ($stateProvider, webappSettings) {
        $stateProvider
            .state('dashboard.sysAuditEvent', {
                url: '/sysAuditEvent',
                template: '<div ui-view></div>'
            })
            .state('dashboard.sysAuditEvent.list', {
                url: '/list',
                templateUrl: webappSettings.tplsBasePath + '/dashboard/sysAuditEvent/list.html',
                controller: 'SysAuditEventController'
            })
            .state('dashboard.sysAuditEvent.detail', {
                url: '/:id',
                templateUrl: webappSettings.tplsBasePath + '/dashboard/sysAuditEvent/detail.html',
                controller: 'SysAuditEventDetailController'
            });
    })
    .factory('sysAuditEventService', function ($http, $rootScope, webappSettings, pageRequestHelper) {
        return {
            listAuditEventPage: function (queryCriteria) {
                return $http.get(webappSettings.basicApiUrl + '/sysAuditEvents' + pageRequestHelper.buildRequest(queryCriteria));
            },
            getAuditEventDetail: function (id) {
                return $http.get(webappSettings.basicApiUrl + '/sysAuditEvents/' + id);
            },
            deleteAuditEventsByDay: function (day) {
                return $http.delete(webappSettings.basicApiUrl + '/sysAuditEvents/byDay/' + day);
            },
            deleteAuditEventsBeforeDay: function (day) {
                return $http.delete(webappSettings.basicApiUrl + '/sysAuditEvents/beforeDay/' + day);
            }
        };
    })
    .controller('SysAuditEventController', function ($scope, $log, $http, $filter, $state, $timeout, dialogs, notifyService, sysAuditEventService, webappSettings) {

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
            sysAuditEventService.listAuditEventPage($scope.filter).success(function (result) {
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
            dialogs.create(webappSettings.tplsBasePath + '/dashboard/sysAuditEvent/detail.html', 'SysAuditEventDetailController', {id: item.id}, {
                size: 'lg'
            });
        };


        $scope.pageChanged = function () {
            $scope.filter.start = $scope.currentPage - 1;
            $scope.refreshList();
        };


        $scope.deleteHistories = function () {
            dialogs.create(webappSettings.tplsBasePath + '/dashboard/sysAuditEvent/confirmDate.html', 'SysAuditEventDateConfirmController', {}, {
                'windowClass': "modal inmodal",
                'size': 'sm'
            }).result.then(function (selectedDate) {
                if (selectedDate) {
                    // $log.info(selectedDate)
                    sysAuditEventService.deleteAuditEventsBeforeDay(selectedDate).success(function (result) {
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
    .controller('SysAuditEventDetailController', function ($scope, $log, $http, $state, $modalInstance, sysAuditEventService, data) {
        $scope.item = {};

        $scope.close = function () {
            $modalInstance.close(data);
        };

        $scope.init = function () {
            if (data.id) {
                sysAuditEventService.getAuditEventDetail(data.id).success(function (result) {
                    $scope.item = result;
                });
            }
        };
        $scope.init();

    })
    .controller('SysAuditEventDateConfirmController', function ($scope, $timeout, $log, $http, $state, $modalInstance, notifyService) {
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
