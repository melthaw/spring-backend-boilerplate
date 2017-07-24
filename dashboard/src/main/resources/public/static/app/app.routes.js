'use strict';

angular.module('webapp')
    .config(function ($stateProvider, $urlRouterProvider) {
        $urlRouterProvider.otherwise('/dashboard/appuser/list');
        // 控制台
        $stateProvider.state('dashboard', {
            url: '/dashboard',
            templateUrl: '/static/app/common/dashboard_layout.html',
            controller: 'MainController',
            ncyBreadcrumb: {
                label: '控制台'
            }
        });
    });


