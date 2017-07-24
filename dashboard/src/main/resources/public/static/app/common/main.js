'use strict';

angular.module('webapp')
    .controller('MainController', function ($scope, $rootScope, $log, $http, $state, $window, $timeout, dialogs, messageService, webappSettings) {
        $timeout(function () {
            $.AdminLTE.pushMenu("[data-toggle='offcanvas']")
            $.AdminLTE.tree('.sidebar');
            var $ul = angular.element('.treeview-menu li.active').parent();
            if (($ul.is('.treeview-menu')) && (!$ul.is(':visible'))) {
                //Close the menu
                $ul.slideDown('normal', function () {
                    //Add the class active to the parent li
                    $ul.addClass('menu-open');
                    $.AdminLTE.layout.fix();
                });
            }
        }, 1000);

        if (angular.element("body").hasClass('sidebar-open')) {
            angular.element("body").removeClass('sidebar-open');
            angular.element("body").removeClass('sidebar-collapse')
        }

        $scope.changePwd = function () {
            var dlg = dialogs.create(webappSettings.tplsBasePath + '/shared/preference/changepwd.html', 'PreferenceChangePwdController', {}, {
                windowClass: "modal inmodal",
                size: 'md'
            });
            dlg.result.then(function (result) {
                if (result === 'success') {
                    dialogs.notify('即将注销', '新密码已生效,请重新登录!').result.then(function (callback) {
                        $window.location = '/logout';
                    });
                }
            });
        };

        $scope.profile = function () {
            dialogs.create(webappSettings.tplsBasePath + '/shared/preference/profile.html', 'PreferenceController', {}, {
                windowClass: "modal inmodal",
                size: 'sm'
            }).result.then(function (result) {
            });
        };


    });
