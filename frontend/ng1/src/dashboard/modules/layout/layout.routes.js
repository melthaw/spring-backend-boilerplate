routes.$inject = ['$stateProvider'];

export default function routes($stateProvider) {
    $stateProvider
        .state('dashboard', {
            url: '/dashboard',
            template: require('./dashboard_layout.html'),
            controller: 'MainController'
        });
}
