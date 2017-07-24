'use strict';

angular.module('webapp')
    .config(function ($stateProvider, webappSettings) {
        $stateProvider
            .state('dashboard.contacts', {
                url: '/contacts',
                templateUrl: webappSettings.tplsBasePath + '/dashboard/contacts/index.html',
                controller: 'ContactsController'
            })
            .state('dashboard.contacts.orgtree', {
                url: '/orgtree',
                templateUrl: webappSettings.tplsBasePath + '/dashboard/contacts/contacts-org-tree.html',
                controller: 'ContactsOrgTreeController'
            })
            .state('dashboard.contacts.list', {
                url: '/list',
                templateUrl: webappSettings.tplsBasePath + '/dashboard/contacts/contacts-list.html',
                controller: 'ContactsListController'
            })
            .state('dashboard.contacts.general', {
                url: '/general',
                templateUrl: webappSettings.tplsBasePath + '/dashboard/contacts/contacts-general-list.html',
                controller: 'ContactsGeneralController'
            });
    })
    .factory('contactsService', function ($http, $rootScope, webappSettings, pageRequestHelper) {
        return {
            getUsersByOrg: function (orgId, queryCriteria) {
                return $http.get(webappSettings.basicApiUrl + '/contact/organizations/' + orgId + '/users' + pageRequestHelper.buildRequest(queryCriteria));
            },
            getAllUsers: function (queryCriteria) {
                return $http.get(webappSettings.basicApiUrl + '/contact/users' + pageRequestHelper.buildRequest(queryCriteria));
            },
            getGroups: function (queryCriteria) {
                return $http.get(webappSettings.basicApiUrl + '/contact/groups' + pageRequestHelper.buildRequest(queryCriteria));
            },
            getGroupById: function (groupId) {
                return $http.get(webappSettings.basicApiUrl + '/contact/groups/' + groupId);
            },
            createGroup: function (group) {
                return $http.post(webappSettings.basicApiUrl + '/contact/groups', group);
            },
            updateGroup: function (group) {
                return $http.post(webappSettings.basicApiUrl + '/contact/groups/' + group.id, group);
            },
            deleteGroup: function (groupId) {
                return $http.delete(webappSettings.basicApiUrl + '/contact/groups/' + groupId);
            },
            addUsersToGroup: function (groupId, userIds) {
                return $http.post(webappSettings.basicApiUrl + '/contact/groups/' + groupId + '/users', userIds);
            },
            removeUsersFromGroup: function (groupId, userIds) {
                return $http.delete(webappSettings.basicApiUrl + '/contact/groups/' + groupId + '/users/' + userIds.join(','));
            },
            getCurrentOrg: function () {
                return $http.get(webappSettings.basicApiUrl + '/contact/myOrganization');
            },
            getRecentUsers: function () {
                return $http.get(webappSettings.basicApiUrl + '/contact/recent');
            }
        };
    })
    .controller('ContactsController', function ($rootScope, $scope, $log, $http, $state, dialogs, notifyService, webappSettings) {
        $scope.currentTab = null;
        $scope.switchTab = function (tabName) {
            $scope.currentTab = tabName;
        };
        $scope.init = function () {
            var currentStateName = $state.current.name;
            if (currentStateName == 'dashboard.contacts.orgtree') {
                $scope.switchTab('OrgTreeTab');
            } else if (currentStateName == 'dashboard.contacts.list') {
                $scope.switchTab('ListTab');
            } else if (currentStateName == 'dashboard.contacts.general') {
                $scope.switchTab('GeneralTab');
            }
        };
        $scope.init();
    });
