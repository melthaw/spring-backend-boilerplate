import angular from 'angular';

function topbar() {
    return {
        restrict: 'AE',
        template: require('./topbar.html'),
        transclude: true,
        replace: true
    }
}

export default angular.module('directives.topbar', [])
    .directive('topbar', topbar)
    .name;
