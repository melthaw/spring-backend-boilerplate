import angular from "angular";

export default function run($rootScope, AuthenticationServcie) {
    $rootScope.grantedMenus = [];
    $rootScope.grantedPortalMenus = [];
    $rootScope.grantedDashboardMenus = [];
    AuthenticationServcie.authenticate().then(function successCallback(currentUser) {
        $rootScope.currentUser = currentUser;
        $rootScope.currentUser.isAuthenticated = true;
        AuthenticationServcie.getUserGrantedMenus().then(function (grantedMenus) {
            angular.forEach(grantedMenus, function (grantedMenu) {
                $rootScope.grantedMenus.push(grantedMenu);
                if (grantedMenu.type.split('menu:')[1] === 'portal') {
                    $rootScope.grantedPortalMenus = grantedMenu.children;
                }
                if (grantedMenu.type.split('menu:')[1] === 'dashboard') {
                    $rootScope.grantedDashboardMenus.push(grantedMenu);
                }
            });
        });

        AuthenticationServcie.getSystemSetting().then(function (branding) {
            $rootScope.branding = branding;
        });
    }, function errorCallback(response) {
        $rootScope.grantedMenus = [];
        $window.location = '/login';
    });
}

run.$inject = ['$rootScope', 'AuthenticationServcie'];