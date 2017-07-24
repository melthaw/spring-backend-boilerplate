'use strict';

angular.module('webapp')
    .config(function ($stateProvider, webappSettings) {
        $stateProvider
            .state('dashboard.appuser', {
                url: '/appuser',
                abstract: true,
                template: '<div ui-view></div>'
            })
            .state('dashboard.appuser.list', {
                url: '/list',
                templateUrl: webappSettings.tplsBasePath + '/dashboard/appuser/list.html',
                controller: 'AppUsersController'
            });
    })
    .factory('appUserService', function ($http, $rootScope, webappSettings, pageRequestHelper) {
        return {
            list: function (queryCriteria) {
                return $http.get(webappSettings.basicApiUrl + '/appusers' + pageRequestHelper.buildRequest(queryCriteria));
            },
            findById: function (id) {
                return $http.get(webappSettings.basicApiUrl + '/appusers/' + id);
            },
            disable: function (id) {
                return $http.post(webappSettings.basicApiUrl + '/appusers/' + id + '/disable', {});
            },
            enable: function (id) {
                return $http.post(webappSettings.basicApiUrl + '/appusers/' + id + '/enable', {});
            },
            getAppUserActiveData: function (id) {
                return $http.get(webappSettings.basicApiUrl + '/appusers/' + id + '/activeData');
            },
            getAppUserCreatedTasks: function (id, queryCriteria) {
                return $http.get(webappSettings.basicApiUrl + '/appusers/' + id + '/createdTasks' + pageRequestHelper.buildRequest(queryCriteria));
            },
            getAppUserCreatedActivities: function (id, queryCriteria) {
                return $http.get(webappSettings.basicApiUrl + '/appusers/' + id + '/createdActivities' + pageRequestHelper.buildRequest(queryCriteria));
            },
            getAppUserCreatedTopics: function (id, queryCriteria) {
                return $http.get(webappSettings.basicApiUrl + '/appusers/' + id + '/createdTopics' + pageRequestHelper.buildRequest(queryCriteria));
            }
        };
    })
    .controller('AppUsersController', function ($scope, $log, $http, $state, dialogs, notifyService, appUserService, webappSettings) {
        $scope.currentPage = 1;
        $scope.filter = {
            username: '',
            cellphone: '',
            email: '',
            enabled: ''
        };
        $scope.appusers = [];
        $scope.features = {advancedSearch: false};

        $scope.advancedSearch = false;

        $scope.toggleAdvancedSearch = function () {
            $scope.advancedSearch = !$scope.advancedSearch;
        };

        $scope.disableUser = function (id) {
            dialogs.confirm('确认', '是否禁用用户？', {'size': 'sm'}).result.then(function () {
                appUserService.disable(id).success(function (result) {
                    notifyService.success('已禁用');
                    $scope.refreshList();
                })
            });
        };

        $scope.enableUser = function (id) {
            dialogs.confirm('确认', '是否启用用户？', {'size': 'sm'}).result.then(function () {
                appUserService.enable(id).success(function (result) {
                    notifyService.success('已启用');
                    $scope.refreshList();
                })
            });
        };

        $scope.refreshList = function () {
            appUserService.list($scope.filter).success(function (result) {
                $scope.appusers = result.content;
                $scope.totalItems = result.totalElements;
            });
        };

        $scope.pageChanged = function () {
            $scope.filter.start = $scope.currentPage - 1;
            $scope.refreshList();
        };

        $scope.showDetail = function (item) {
            dialogs.create(webappSettings.tplsBasePath + '/dashboard/appuser/detail.html', 'AppUserTabsController', {id: item.id}, {
                size: 'lg'
            });
        };

        $scope.resetFilter = function () {
            $scope.filter = {
                username: '',
                cellphone: '',
                email: '',
                enabled: ''
            };
        };

        $scope.pageChanged = function () {
            $scope.filter.start = $scope.currentPage - 1;
            $scope.refreshList();
        };

        $scope.refreshList();
    })
    .controller('AppUserTabsController', function ($scope, $log, $http, $state, $modalInstance, appUserService, data) {

        $scope.close = function () {
            $modalInstance.close(data);
        };

        $scope.getAppUserActiveData = function () {
            appUserService.getAppUserActiveData(data.id).success(function (json) {
                $scope.activeData = json;
            });
        };

        // 创建的任务列表
        $scope.createdTasks = [];
        $scope.createdTasksQueryCriteria = {
            currentPage: 1,
            totalItems: 0
        };
        $scope.createdTasksPageChanged = function () {
            $scope.createdTasksQueryCriteria.start = $scope.createdTasksQueryCriteria.currentPage - 1;
            $scope.getAppUserCreatedTasks();
        };
        $scope.getAppUserCreatedTasks = function () {
            appUserService.getAppUserCreatedTasks(data.id, $scope.createdTasksQueryCriteria).success(function (result) {
                $scope.createdTasks = result.content;
                $scope.createdTasksQueryCriteria.totalItems = result.totalElements;
            });
        };

        //创建的活动列表
        $scope.createdActivities = [];
        $scope.createdActivitiesQueryCriteria = {
            currentPage: 1,
            totalItems: 0
        };
        $scope.createdActivitiesPageChanged = function () {
            $scope.createdActivitiesQueryCriteria.start = $scope.createdActivitiesQueryCriteria.currentPage - 1;
            $scope.getAppUserCreatedActivities();
        };
        $scope.getAppUserCreatedActivities = function () {
            appUserService.getAppUserCreatedActivities(data.id, $scope.createdActivitiesQueryCriteria).success(function (result) {
                $scope.createdActivities = result.content;
                $scope.createdActivitiesQueryCriteria.totalItems = result.totalElements;
            });
        };

        //创建的话题列表
        $scope.createdTopics = [];
        $scope.createdTopicsQueryCriteria = {
            currentPage: 1,
            totalItems: 0
        };
        $scope.createdTopicsPageChanged = function () {
            $scope.createdTopicsQueryCriteria.start = $scope.createdTopicsQueryCriteria.currentPage - 1;
            $scope.getAppUserCreatedTopics();
        };
        $scope.getAppUserCreatedTopics = function () {
            appUserService.getAppUserCreatedTopics(data.id, $scope.createdTopicsQueryCriteria).success(function (result) {
                $scope.createdTopics = result.content;
                $scope.createdTopicsQueryCriteria.totalItems = result.totalElements;
            });
        };

        $scope.init = function () {
            appUserService.findById(data.id).success(function (json) {
                $scope.appuser = json;
            });
        };

        $scope.init();
    })
    .controller('AppUserController4Select', function ($scope, $log, $http, $state, $modalInstance, dialogs, notifyService, tooltipService, appUserService) {
        $scope.selectIds = [];
        $scope.appusers = [];
        $scope.selectAllTag = false;

        $scope.queryCriteria = {};

        $scope.currentPage = 1;
        $scope.pageChanged = function () {
            $log.log('Page changed to: ' + $scope.currentPage);
            $scope.queryCriteria.start = $scope.currentPage - 1;
            $scope.init();
        };

        $scope.close = function () {
            $modalInstance.close();
        };

        $scope.ok = function () {
            var ids = [];
            angular.forEach($scope.appusers, function (item) {
                if (item.checked) {
                    ids.push(item.id);
                }
            });
            if (ids.length > 0) {
                $modalInstance.close(ids);
            } else {
                notifyService.error('请选择至少一项');
            }

        };

        $scope.pageChanged = function () {
            $scope.queryCriteria.start = $scope.currentPage - 1;
            $scope.init();
        };

        $scope.init = function () {
            appUserService.list($scope.queryCriteria).success(function (result) {
                $scope.appusers = result.content;
                $scope.totalItems = result.totalElements;
            });
        };

        $scope.toggleAllItems = function () {
            $scope.selectAllTag = !$scope.selectAllTag;
            angular.forEach($scope.appusers, function (item) {
                item.checked = $scope.selectAllTag;
            });
        };

        $scope.toggleCheck = function (item) {
            item.checked = !item.checked;
        };

        $scope.init();
    });
