'use strict';

angular.module('webapp')
    .constant('webappSettings', {
        developerMode: false,
        basicApiUrl: '/api',
        tplsBasePath: '/static/app'
    })
    .config(['dialogsProvider', function (dialogsProvider) {
        //dialogsProvider.useBackdrop('static');
        //dialogsProvider.useEscClose(true);
        //dialogsProvider.useCopy(true);
        //dialogsProvider.setSize('sm');

        //$translateProvider.translations('cn-ZH', {
        //    DIALOGS_ERROR: '错误',
        //    DIALOGS_ERROR_MSG: '未知的错误。',
        //    DIALOGS_CLOSE: '关闭',
        //    DIALOGS_PLEASE_WAIT: '请等待',
        //    DIALOGS_PLEASE_WAIT_ELIPS: '请等待...',
        //    DIALOGS_PLEASE_WAIT_MSG: '待操作完成',
        //    DIALOGS_PERCENT_COMPLETE: '% 已完成',
        //    DIALOGS_NOTIFICATION: '提示',
        //    DIALOGS_NOTIFICATION_MSG: '未知应用提示。',
        //    DIALOGS_CONFIRMATION: '确认',
        //    DIALOGS_CONFIRMATION_MSG: '需要确认。',
        //    DIALOGS_OK: '确定',
        //    DIALOGS_YES: '是的',
        //    DIALOGS_NO: '不'
        //});
    }])
    .config(['$httpProvider', function ($httpProvider) {
        $httpProvider.interceptors.push(function ($rootScope, $q) {
            return {
                'request': function (config) {
                    $rootScope.loading = true;
                    return config;
                },

                'response': function (response) {
                    $rootScope.loading = false;
                    return response;
                }
            };
        });

        /* Intercept http errors */
        var interceptor = function ($rootScope, $q, $window, notifyService, $injector) {

            function success(response) {
                if (response.data.succeed === false) {
                    notifyService.error(response.data.message);
                    return $q.reject(response);
                }
                return response;
            }

            function error(response) {
                var status = response.status;
                var config = response.config;

                if (status === 401) {
                    // $location.path('/login');
                    $window.location.assign('/login');
                    return;
                } else if (status === 403) {
                    notifyService.error('无权限访问!');
                } else if (status === 404) {
                    notifyService.error('找不到相应资源!');
                } else {
                    if (response.data.errorMessage) {
                        notifyService.error(response.data.errorMessage);
                    } else {
                        notifyService.error('系统错误,原因未知!');
                    }

                }
                return $q.reject(response);
            }

            return function (promise) {
                return promise.then(success, error);
            };
        };

        $httpProvider.responseInterceptors.push(interceptor);
    }])
    //.factory('notifyService', ['dialogs',function (dialogs) {
    //    return {
    //        notify: function (message) {
    //            return dialogs.notify('提示', message, defaultOpts).result;
    //        },
    //        error : function (message) {
    //            return dialogs.error('错误', message, defaultOpts).result;
    //        },
    //        success: function (message) {
    //            return dialogs.notify('成功', message, defaultOpts).result;
    //        }
    //    };
    //}])
    //.factory('notifyService', ['$window', function ($window) {
    //    return {
    //        notify: function (message) {
    //            return $window.alert('提示:' + message);
    //        },
    //        error: function (message) {
    //            return $window.alert('错误:' + message);
    //        },
    //        success: function (message) {
    //            return $window.alert('成功:' + message);
    //        }
    //    };
    //}])
    .config(['paginationConfig', function (paginationConfig) {
        paginationConfig.itemsPerPage = 20;
        paginationConfig.boundaryLinks = true;
        paginationConfig.directionLinks = true;
        paginationConfig.firstText = '«';
        paginationConfig.previousText = '‹';
        paginationConfig.nextText = '›';
        paginationConfig.lastText = '»';
        paginationConfig.rotate = false;
        paginationConfig.maxSize = 5;
    }])
    .config(['pagerConfig', function (paginationConfig) {
        paginationConfig.itemsPerPage = 20;
        paginationConfig.boundaryLinks = true;
        paginationConfig.directionLinks = true;
        paginationConfig.firstText = '«';
        paginationConfig.previousText = '‹';
        paginationConfig.nextText = '›';
        paginationConfig.lastText = '»';
        paginationConfig.rotate = true;
    }])
    .factory('Enums', [function () {
        var service = {
            Gender: {
                MALE: '男',
                FEMALE: '女'
            },
            TaskStatus: {
                PENDING: '未处理',
                COMPLETE: '已完成'
            },
            ActivityStatus: {
                PENDING: '未处理',
                COMPLETE: '已完成'
            },
            SmsCategory: {
                REGISTER: '注册',
                FORGET_PASSWORD: '找回密码'
            },
            SmsStatus: {
                SENT: '发送成功',
                FAILED: '发送失败'
            },
            AppUserType: {
                PATIENT: '患者',
                GUARDIAN: '监护人',
                EXPERT: '专业人士',
                OTHER: '其他'
            }
        }

        return service;
    }])
    .factory('notifyService', ['$window', function ($window) {
        return {
            notify: function (message) {
                toastr.options = {
                    closeButton: true,
                    progressBar: true,
                    showMethod: 'slideDown',
                    timeOut: 2000
                };
                return toastr.info(message, '提示');
            },
            warning: function (message) {
                toastr.options = {
                    closeButton: true,
                    progressBar: true,
                    showMethod: 'slideDown',
                    timeOut: 2000
                };
                return toastr.warning(message, '提示');
            },
            error: function (message) {
                toastr.options = {
                    closeButton: true,
                    progressBar: true,
                    showMethod: 'slideDown',
                    timeOut: 2000
                };
                return toastr.error(message, '操作失败');
            },
            success: function (message) {
                toastr.options = {
                    closeButton: true,
                    progressBar: true,
                    showMethod: 'slideDown',
                    timeOut: 2000
                };
                return toastr.success(message, '操作成功');
            }
        };
    }])
    .factory('tooltipService', [function () {
        return {
            showToolTip: function (modelname, msg) {
                $("[ng-model*='" + modelname + "']").attr("title", msg);
                $("[ng-model*='" + modelname + "']").tooltip({trigger: "manual"});
                $("[ng-model*='" + modelname + "']").tooltip("show");
                $("[ng-model*='" + modelname + "']").click(function () {
                    $(this).tooltip("destroy")
                });
            }
        }
    }])
    .factory('validationService', [function () {
        return {
            validatePhoneNumber: function (phoneNumber) {
                var teleReg = /^((0\d{2,3})-)(\d{7,8})$/;
                var mobileReg = /^1\d{10}$/;
                return (teleReg.test(phoneNumber) || mobileReg.test(phoneNumber));
            },
            validateEmail: function (emailAddress) {
                var emailRegex = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
                return emailRegex.test(emailAddress);
            },
            validateFixedPhoneNumber: function (number) {
                return number.length <= 13;
            },
            validatePositiveInteger: function (number) {
                return number.length <= 13;
            }
        }
    }])
    .factory('pageRequestHelper', [function () {
        return {
            buildRequest: function (pageRequest) {
                var targetRequest = {
                    start: 0,
                    limit: 20
                };
                targetRequest = angular.extend(targetRequest, pageRequest);
                var httpQueryCriteria = '';
                for (var p in targetRequest) {
                    httpQueryCriteria += (httpQueryCriteria == '') ? '?' : '&';
                    httpQueryCriteria += p + '=' + targetRequest[p];
                }
                return httpQueryCriteria;
            }
        }
    }])
    .factory('authenticateSerivce', ['$http', '$q', 'webappSettings', function ($http, $q, webappSettings) {
        var deferred = $q.defer();
        $http.get(webappSettings.basicApiUrl + '/my/profile').success(function (data) {
            deferred.resolve(data);
        });
        return deferred.promise();
    }])
    .factory('loginProfileService', ['$http', '$q', 'webappSettings', function ($http, $q, webappSettings) {
        return {
            authenticate: function () {
                return $http.get(webappSettings.basicApiUrl + '/my/profile');
            },
            getUserGrantedMenus: function () {
                return $http.get(webappSettings.basicApiUrl + '/my/menus');
            },
            getSystemSetting: function () {
                 return $http.get('/guest/settings/system');
            }
        };
    }])
    .run(function run($rootScope, $http, $timeout, $state, $window, $urlRouter, notifyService, loginProfileService) {
        $rootScope.oaBodyClass = "skin-blue sidebar-mini";
        $rootScope.grantedMenus = [];
        $rootScope.grantedPortalMenus = [];
        $rootScope.grantedDashboardMenus = [];
        loginProfileService.authenticate().success(function (currentUser) {
            $rootScope.currentUser = currentUser;
            $rootScope.currentUser.isAuthenticated = true;
            loginProfileService.getUserGrantedMenus().success(function (grantedMenus) {
                $rootScope.groupGrantedMenus(grantedMenus);
                $rootScope.$on('$stateChangeStart', function (event, toState) {
                    if (toState.name && toState.name != '') {
                        if (!$rootScope.isGrantedState(toState)) {
                            notifyService.error('无权限访问!');
                            event.preventDefault();
                            return false;
                        }
                    }
                });
            });

             loginProfileService.getSystemSetting().success(function (result) {
                 $rootScope.systemSetting = result;
             });
        }).error(function () {
            $rootScope.grantedMenus = [];
            $window.location = '/login';
        });

        $rootScope.groupGrantedMenus = function (grantedMenus) {
            angular.forEach(grantedMenus, function (grantedMenu) {
                $rootScope.grantedMenus.push(grantedMenu);
                if (grantedMenu.type.split('menu:')[1] === 'portal') {
                    $rootScope.grantedPortalMenus = grantedMenu.children;
                }
                if (grantedMenu.type.split('menu:')[1] === 'dashboard') {
                    $rootScope.grantedDashboardMenus.push(grantedMenu);
                }
            });
        };

        $rootScope.isAuthenticated = function () {

            if ($rootScope.currentUser === undefined) {
                return false;
            }
            if ($rootScope.currentUser.isAuthenticated === undefined) {
                return false;
            }
            if (!$rootScope.currentUser.isAuthenticated) {
                return false;
            }
            return true;
        };
        $rootScope.hasRole = function (role) {

            if (!$rootScope.isAuthenticated()) {
                return false;
            }
            if ($rootScope.currentUser.roles[role] === undefined) {
                return false;
            }
            return $rootScope.currentUser.roles[role];
        };

        $rootScope.isGrantedMenu = function (menuCode) {

            if (!$rootScope.isAuthenticated()) {
                return false;
            }

            var granted = false;
            angular.forEach($rootScope.grantedMenus, function (grantedMenu) {
                if (grantedMenu.code == menuCode) {
                    granted = true;
                    return granted;
                }
            });
            return granted;
        };

        $rootScope.isGrantedState = function ($state) {

            // if (!$rootScope.isAuthenticated()) {
            //     return false;
            // }
            //
            // var granted = false;
            // angular.forEach($rootScope.grantedMenus, function (grantedMenu) {
            //     var state = grantedMenu.expression.split('state:')[1];
            //     if (state && state.contains($state.name)) {
            //         granted = true;
            //         return granted;
            //     }
            // });
            // return granted;

            return true;
        };

        $rootScope.dashboardTabActive = true;

        var setAllInactive = function () {
            $rootScope.dashboardTabActive = false;
            angular.forEach($rootScope.tabs, function (tab) {
                tab.active = false;
            });
        };

        $rootScope.dashboardTabTitle = '首页';
        $rootScope.tabs = [{
            active: true,
            title: $rootScope.dashboardTabTitle,
            tabUrl: '',
            data: ''
        }];

        $rootScope.removeTab = function (i) {
            $rootScope.tabs.splice(i, 1);
        };

        $rootScope.addTab = function (tab) {
            setAllInactive();
            $rootScope.tabs.push({
                active: true,
                title: tab.title,
                tabUrl: tab.tabUrl,
                data: tab.data
            });
        };

        $rootScope.showTab = function (i) {
            setAllInactive();
            // if (i >= 0) {
            //     $scope.tabs[i].active = true;
            // } else {
            //     $rootScope.dashboardTabActive = true;
            // }
            // $rootScope.dashboardTabActive = true;
            if ($rootScope.tabs[0]) {
                $rootScope.tabs[0].active = true;
            }
            return;
            // $scope.apply();
        };


    });

