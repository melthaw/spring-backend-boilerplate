import angular from 'angular';

function menubar() {
    return {
        restrict: 'AE',
        template: require('./menu.html'),
        transclude: true,
        replace: true
    }
}

export default angular.module('directives.menu', [])
    .directive('menubar', menubar)
    .name;
