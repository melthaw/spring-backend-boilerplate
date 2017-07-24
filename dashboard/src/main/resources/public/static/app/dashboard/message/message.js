'use strict';

angular.module('webapp')
    .config(function ($stateProvider, webappSettings) {
        $stateProvider
            .state('dashboard.message', {
                url: '/message',
                abstract: true,
                template: '<div ui-view></div>'
            })
            .state('dashboard.message.list', {
                url: '/list',
                templateUrl: webappSettings.tplsBasePath + '/dashboard/message/list.html',
                controller: 'MessageListController'
            })
            .state('dashboard.message.quickSearch', {
                url: '/quickSearch',
                params: {
                    filter: ''
                },
                templateUrl: webappSettings.tplsBasePath + '/dashboard/message/quickSearch.html',
                controller: 'MessageQuickSearchController'
            });
    })
    .directive('messagepage', function () {
        return {
            restrict: 'AE',
            templateUrl: '/static/app/dashboard/message/message-page-tpl.html',
            transclude: true,
            replace: true
        };
    })
    .directive('messagetable', function () {
        return {
            restrict: 'AE',
            templateUrl: '/static/app/dashboard/message/message-table-tpl.html',
            transclude: true,
            replace: true
        };
    })
    .factory('messageService', function ($http, $rootScope, webappSettings, pageRequestHelper) {
        return {
            listAllMessages: function (queryCriteria) {
                return $http.get(webappSettings.basicApiUrl + '/messages' + pageRequestHelper.buildRequest(queryCriteria));
            },
            listEndedMessages: function (queryCriteria) {
                return $http.get(webappSettings.basicApiUrl + '/messages/ended' + pageRequestHelper.buildRequest(queryCriteria));
            },
            listNotEndMessages: function (queryCriteria) {
                return $http.get(webappSettings.basicApiUrl + '/messages/notend' + pageRequestHelper.buildRequest(queryCriteria));
            },
            listPendingMessages: function (queryCriteria) {
                return $http.get(webappSettings.basicApiUrl + '/messages/pending' + pageRequestHelper.buildRequest(queryCriteria));
            },
            countOfPendingMessages: function () {
                return $http.get(webappSettings.basicApiUrl + '/messages/countOfPending');
            },
            listProcessedMessages: function (queryCriteria) {
                return $http.get(webappSettings.basicApiUrl + '/messages/processed' + pageRequestHelper.buildRequest(queryCriteria));
            },
            listFavoriteMessages: function (queryCriteria) {
                return $http.get(webappSettings.basicApiUrl + '/messages/favorite' + pageRequestHelper.buildRequest(queryCriteria));
            },
            listMessagesByTitle: function (queryCriteria) {
                return $http.get(webappSettings.basicApiUrl + '/messages/byTitle' + pageRequestHelper.buildRequest(queryCriteria));
            },
            listMessagesByPaperCreator: function (queryCriteria) {
                return $http.get(webappSettings.basicApiUrl + '/messages/byCreator' + pageRequestHelper.buildRequest(queryCriteria));
            },
            listMessagesByReceiver: function (queryCriteria) {
                return $http.get(webappSettings.basicApiUrl + '/messages/byReceiver' + pageRequestHelper.buildRequest(queryCriteria));
            },
            countOfFavoriteMessages: function () {
                return $http.get(webappSettings.basicApiUrl + '/messages/countOfFavorite');
            },
            addMessageToFavorite: function (id) {
                return $http.post(webappSettings.basicApiUrl + '/messages/' + id + '/favorite', {});
            },
            removeMessageFromFavorite: function (id) {
                return $http.delete(webappSettings.basicApiUrl + '/messages/' + id + '/favorite');
            },
            getMessageDetail: function (id) {
                return $http.get(webappSettings.basicApiUrl + '/messages/' + id);
            },
            getMessageParticipant: function (id) {
                return $http.get(webappSettings.basicApiUrl + '/messages/' + id + '/participant');
            }
        };
    })
    .controller('MessageListController', function ($scope, $log, $http, $state, dialogs, notifyService, messageService, webappSettings) {

    })
    .controller('MessageQuickSearchController', function ($scope, $log, $http, $state, $stateParams, dialogs, notifyService, messageService) {

        $scope.messages = [];

        $scope.searchField = '';

        $scope.filter1 = {
            title: ''
        };
        $scope.filter2 = {
            creatorName: ''
        };
        $scope.filter3 = {
            receiverName: ''
        };

        $scope.totalElements = 0;
        $scope.currentPage = 1;


        $scope.currentTab = '';
        $scope.changeTab = function (currentTab) {
            $scope.currentTab = currentTab;
            $scope.refreshList();
        };


        $scope.refreshList = function () {
            var filter = {};
            switch ($scope.currentTab) {
                case 'title':
                    $scope.filter1.title = $scope.searchField;
                    filter = $scope.filter1;
                    if (filter.title && filter.title.length < 2) {
                        notifyService.warning("为提高搜索结果准确度,请至少输入2个字");
                        return;
                    }
                    messageService.listMessagesByTitle(filter).success(function (result) {
                        $scope.messages = result.content;
                        $scope.totalElements = result.totalElements;
                    });

                    break;
                case 'creator':
                    $scope.filter2.creatorName = $scope.searchField;
                    filter = $scope.filter2;
                    if (filter.creatorName && filter.creatorName.length < 2) {
                        notifyService.warning("为提高搜索结果准确度,请至少输入2个字");
                        return;
                    }
                    messageService.listMessagesByPaperCreator(filter).success(function (result) {
                        $scope.messages = result.content;
                        $scope.totalElements = result.totalElements;
                    });
                    break;
                case 'receiver':
                    $scope.filter3.receiverName = $scope.searchField;
                    filter = $scope.filter3;
                    if (filter.receiverName && filter.receiverName.length < 2) {
                        notifyService.warning("为提高搜索结果准确度,请至少输入2个字");
                        return;
                    }
                    messageService.listMessagesByReceiver(filter).success(function (result) {
                        $scope.messages = result.content;
                        $scope.totalElements = result.totalElements;
                    });
                    break;
                default:
                    return;
            }
            return;
        };

        $scope.showMsgDetail = function (message) {
            $state.go('dashboard.paper.detail', {
                id: message.bizRefId,
                messageId: message.id,
                favorite: message.favorite,
                status: message.status
            });

        };

        $scope.pageChanged = function () {
            switch ($scope.currentTab) {
                case 'title':
                    $scope.filter1.start = $scope.currentPage - 1;
                    break;
                case 'creator':
                    $scope.filter2.start = $scope.currentPage - 1;
                    break;
                case 'receiver':
                    $scope.filter3.start = $scope.currentPage - 1;
                    break;
            }
            $scope.refreshList();
        };

        $scope.init = function () {
            if ($stateParams.filter) {
                $scope.searchField = $stateParams.filter;
            }
            $scope.currentTab = 'title';
            $scope.refreshList();
        };

        $scope.init();
    });
