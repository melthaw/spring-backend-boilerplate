export default function customHttpProvider($httpProvider) {
    $httpProvider.interceptors.push(function ($rootScope, $window, $q) {
        return {
            'request': function (config) {
                $rootScope.loading = true;
                return config;
            },

            'response': function (response) {
                $rootScope.loading = false;
                if (response.data) {
                    var dataStr = JSON.stringify(response.data);
                    response.data = JSON.parse(dataStr);
                }
                return response.data;
            },

            // // optional method
            // 'responseError': function (rejection) {
            //     // do something on error
            //     var status = rejection.status;
            //     if (status === 401) {
            //         $window.location.assign('/login');
            //         return;
            //     } else if (status === 403) {
            //         notifyService.error('无权限访问!');
            //     } else if (status === 404) {
            //         notifyService.error('找不到相应资源!');
            //     } else {
            //         if (rejection.data.errorMessage) {
            //             notifyService.error(rejection.data.errorMessage);
            //         } else {
            //             notifyService.error('系统错误,原因未知!');
            //         }
            //
            //     }
            //     return $q.reject(rejection);
            // }
        };
    });
}

customHttpProvider.$inject = ['$httpProvider'];