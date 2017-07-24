'use strict';

angular.module('webapp')
    .config(function ($stateProvider, webappSettings) {
        $stateProvider
            .state('dashboard.index', {
                url: '/index',
                templateUrl: webappSettings.tplsBasePath + '/dashboard/dashboard.html',
                controller: 'DashboardController',
                ncyBreadcrumb: {
                    label: '控制台'
                }
            });
    })
    .controller('DashboardController', function ($scope, $log, $http, $state, $window, dialogs, webappSettings) {
    })
    .controller('SaveSortController', function ($scope, $log, $http, $state, $modalInstance, notifyService, data) {
        $scope.item = data;

        $scope.close = function () {
            $modalInstance.close();
        };

        $scope.ok = function () {
            if ($scope.item.sorting != null && $scope.item.sorting != '') {
                $modalInstance.close($scope.item);
            } else {
                notifyService.error('请提供排序值');
            }
        };

    });
