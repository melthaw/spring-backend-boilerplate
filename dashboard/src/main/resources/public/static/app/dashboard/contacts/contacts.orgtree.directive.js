'use strict';

angular.module('webapp')
    .directive('contactsorgtree', ['$http', '$sce', '$timeout', 'dialogs', 'webappSettings', 'notifyService', 'organizationService', 'contactsService',
        function ($http, $sce, $timeout, dialogs, webappSettings, notifyService, organizationService, contactsService) {
            return {
                restrict: 'AE',
                transclude: true,
                replace: true,
                scope: {
                    onnodeselect: '&onnodeselect'
                },
                templateUrl: '/static/app/dashboard/contacts/contacts-orgtree-panel-tpl.html',
                link: function ($scope, elem, attrs) {
                    $scope.rootOrgs = [];
                    $scope.currentOrgs = [];
                    $scope.groupFilter = {
                        name: '',
                        limit: 100
                    };
                    var treeEl = jQuery('.contacts-org-tree');
                    var rootNodeId = 'rootNode';
                    var recentContactsNodeId = rootNodeId + '_recentContacts';
                    var generalContactsNodeId = rootNodeId + '_generalContacts';
                    function initTree() {
                        var treeConfig = {
                            'core': {
                                'themes': {'icons': true},
                                'data': getTreeData(),
                                'dblclick_toggle': true,
                                'check_callback': true
                            }
                        };
                        treeEl.jstree(treeConfig);
                        treeEl.on('select_node.jstree', function (e, data) {
                            $scope.onSelectNode(data.node);
                        });

                        treeEl.on('open_node.jstree', function (e, data) {
                            loadChildren(data.node);
                        });
                        treeEl.on('ready.jstree', function (e, data) {
                            getTree().open_node(rootNodeId);
                            getTree().open_node(generalContactsNodeId);
                        });
                    }

                    function getTree() {
                        return treeEl.jstree(true);
                    }

                    function loadChildren(node) {
                        if (node.original.isLoaded) {
                            return;
                        }
                        deleteNode(node.id + '_childMockNode');
                        var type = node.original.type;
                        if (type == 'contactsGroup') {
                            loadContactsGroupNodes(node);
                        }
                        //else if (type == 'organization') {
                        //    loadOrgNodes(node);
                        //}
                    }

                    function loadContactsGroupNodes(node) {
                        var nodeId = node.id;
                        showNodeLoading(nodeId);
                        contactsService.getGroups($scope.groupFilter).success(function (result) {
                            if (result && result.content && result.content.length > 0) {
                                var len = result.content.length;
                                var tree = getTree();
                                for (var i=0; i<len; i++) {
                                    var group = result.content[i];
                                    var childNodeId = nodeId + '_' + group.id;
                                    tree.create_node(nodeId, {
                                        id: childNodeId,
                                        bizId: group.id,
                                        text: group.name,
                                        type: 'contactsGroup',
                                        isLoaded: false
                                    });
                                }
                            }
                            node.original.isLoaded = true;
                            hideNodeLoading(nodeId);
                        });
                    }

                    function loadOrgNodes(node) {
                        var nodeId = node.id;
                        showNodeLoading(nodeId);
                        organizationService.getChildrenOrgs(node.original.bizId).success(function (result) {
                            if (result && result.length > 0) {
                                var len = result.length;
                                var tree = getTree();
                                for (var i=0; i<len; i++) {
                                    var org = result[i];
                                    var childNodeId = nodeId + '_' + org.id;
                                    tree.create_node(nodeId, {
                                        id: childNodeId,
                                        bizId: org.id,
                                        text: org.name,
                                        type: 'organization',
                                        isLoaded: false,
                                        children: [{id: childNodeId + '_childMockNode'}]
                                    });
                                }
                            }
                            node.original.isLoaded = true;
                            hideNodeLoading(nodeId);
                        });
                    }

                    function deleteNode(nodeId) {
                        var tree = getTree();
                        tree.delete_node($('#' + nodeId));
                    }

                    function showNodeLoading(nodeId) {
                        jQuery('#' + nodeId).addClass('jstree-loading');
                    }

                    function hideNodeLoading(nodeId) {
                        jQuery('#' + nodeId).removeClass('jstree-loading');
                    }

                    function getTreeData() {
                        var treeData = {
                            id: rootNodeId,
                            text: '最近、常用联系人及当前部门',
                            isLoaded: true,
                            children: [
                                {
                                    id: recentContactsNodeId,
                                    text: '最近联系人',
                                    isLoaded: false,
                                    type: 'recentContacts'
                                },
                                {
                                    id: generalContactsNodeId,
                                    text: '常用联系人',
                                    isLoaded: false,
                                    type: 'contactsGroup',
                                    children: [{id: generalContactsNodeId + '_childMockNode'}]
                                }
                            ]
                        };
                        if ($scope.currentOrgs.length > 0) {
                            var len = $scope.currentOrgs.length;
                            for (var i=0; i<len; i++) {
                                var org = $scope.currentOrgs[i];
                                var currentOrgNodeId = rootNodeId + '_' + org.id;
                                var parentOrgName = org.parentName ? '(' + org.parentName + ')' : '';
                                treeData.children.push({
                                    id: currentOrgNodeId,
                                    bizId: org.id,
                                    text: org.name + parentOrgName,
                                    type: 'organization',
                                    isLoaded: false
                                });
                            }
                        }
                        return treeData;
                    }

                    $scope.onSelectNode = function (node) {
                        var nodeId = node.id;
                        if (nodeId == rootNodeId || nodeId == generalContactsNodeId) {
                            return;
                        }
                        $scope.onnodeselect({bizId: node.original.bizId, bizType: node.original.type});
                    };


                    $scope.getCurrentOrg = function () {
                        contactsService.getCurrentOrg().success(function (result) {
                            $scope.currentOrgs = result;
                            initTree();
                        });
                    };
                    $scope.getCurrentOrg();
                }
            };
    }]);
