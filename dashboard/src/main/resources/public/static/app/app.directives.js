'use strict';

angular.module('webapp')
    .directive('topbar', function () {
        return {
            restrict: 'AE',
            templateUrl: '/static/app/common/topbar.html',
            transclude: true,
            replace: true
        };
    })
    .directive('topbar1', function () {
        return {
            restrict: 'AE',
            templateUrl: '/static/app/common/topbar-topfix.html',
            transclude: true,
            replace: true
        };
    })
    .directive('menubar', function () {
        return {
            restrict: 'AE',
            templateUrl: '/static/app/common/menu.html',
            transclude: true,
            replace: true,
            link: function ($scope, elem, attrs) {
                elem.ready(function () {
                    jQuery.AdminLTE.tree('.sidebar');
                })
            }
        };
    })
    .directive('footbar', function () {
        return {
            restrict: 'AE',
            templateUrl: '/static/app/common/footer.html',
            transclude: true,
            replace: true
        };
    })
    .directive('emailerror', function () {
        return {
            restrict: 'AE',
            templateUrl: '/static/app/directives/emailerror.html',
            transclude: true,
            replace: true
        };
    })
    .directive('maxlengtherror', function () {
        return {
            restrict: 'AE',
            templateUrl: '/static/app/directives/maxlengtherror.html',
            transclude: true,
            replace: true
        };
    })
    .directive('minlengtherror', function () {
        return {
            restrict: 'AE',
            templateUrl: '/static/app/directives/minlengtherror.html',
            transclude: true,
            replace: true
        };
    })
    .directive('numerror', function () {
        return {
            restrict: 'AE',
            templateUrl: '/static/app/directives/numerror.html',
            transclude: true,
            replace: true
        };
    }).directive('requirederror', function () {
        return {
            restrict: 'AE',
            templateUrl: '/static/app/directives/requirederror.html',
            transclude: true,
            replace: true
        };
    })
    .directive('urlerror', function () {
        return {
            restrict: 'AE',
            templateUrl: '/static/app/directives/urlerror.html',
            transclude: true,
            replace: true
        };
    })
    .directive('paging', function () {
        return {
            restrict: 'AE',
            templateUrl: '/static/app/directives/paging.html',
            replace: true,
            scope: {
                pageData: '=',
                onGotoPage: '&'
            },
            link: function (scope, elements, attrs) {
                //initialize the pages navigation
                scope.$watch('pageData', function (data) {
                    scope.navPages = [];
                    if (data == undefined || data.length == 0) {
                        return;
                    }
                    var start = Math.max(data.number - 2, 1);
                    var end = Math.min(data.number + 2, data.totalPages);

                    for (var i = start; i <= end; i++) {
                        scope.navPages.push({'number': i});
                    }
                });

                scope.firstPage = function () {
                    scope.gotoPage(0);
                };
                scope.lastPage = function () {
                    scope.gotoPage(scope.pageData.totalPages);
                };
                scope.previousPage = function () {
                    scope.gotoPage(scope.pageData.number - 1);
                };
                scope.nextPage = function () {
                    scope.gotoPage(scope.pageData.number + 1)
                };
                scope.gotoPage = function (pageIndex) {
                    pageIndex = (pageIndex == undefined) ? 0 : pageIndex - 1;
                    scope.onGotoPage({params: {start: pageIndex}});
                };
                scope.isCurrentPage = function (pageIndex) {
                    return scope.pageData.number == pageIndex - 1;
                };
            }
        };
    });
