'use strict';

angular.module('webapp')
    .factory('privilegeService', function ($http, $rootScope, webappSettings, pageRequestHelper) {
        return {
            listResourceSummaries: function () {
                return $http.get(webappSettings.basicApiUrl + '/resources');
            },
            getPrivileges: function (typedCode) {
                return $http.get(webappSettings.basicApiUrl + '/resources/byRole/' + typedCode);
            },
            listGrantedRoles: function (code) {
                return $http.get(webappSettings.basicApiUrl + '/resources/' + code + '/roles');
            },
            grantRolesToResource: function (code, typedRoleCodes) {
                return $http.post(webappSettings.basicApiUrl + '/resources/' + code + '/roles', typedRoleCodes);
            },
            revokeRolesFromResource: function (code, typedRoleCodes) {
                return $http.delete(webappSettings.basicApiUrl + '/resources/' + code + '/roles?typedRoleCodes=' + typedRoleCodes.join(','));
            }
        };
    })
    .controller('PrivilegesViewController', function ($scope, $timeout, $log, $http, $state, $modalInstance, privilegeService, data) {
        $scope.typedCode = data.typedCode;
        $scope.privileges = [];

        $scope.treeOptions = {
            nodeChildren: "children",
            dirSelectable: true,
            injectClasses: {
                ul: "a1",
                li: "a2",
                liSelected: "a7",
                iExpanded: "a3",
                iCollapsed: "a4",
                iLeaf: "a5",
                label: "a6",
                labelSelected: "a8"
            }
        };

        $scope.close = function () {
            $modalInstance.close();
        };

        $scope.getPrivileges = function () {
            privilegeService.getPrivileges($scope.typedCode).success(function (result) {
                $scope.privileges = result;
            });
        };

        $scope.getPrivileges()
    });