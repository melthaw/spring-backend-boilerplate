'use strict';

angular.module('webapp')
    .directive('privilegetree', ['$http', '$sce', '$timeout', 'dialogs', 'webappSettings', 'notifyService', 'privilegeService', function ($http, $sce, $timeout, dialogs, webappSettings, notifyService, privilegeService) {
        return {
            restrict: 'AE',
            transclude: true,
            replace: true,
            scope: {
                id: '@id',
                onnodeselect: '&onnodeselect'
            },
            templateUrl: '/static/app/dashboard/privilege/tree-panel-tpl.html',
            link: function ($scope, elem, attrs) {
                $scope.resources = [];
                function initTree() {
                    jQuery('.privilege-tree').html('<div class="' + $scope.id + '"></div>');
                    var treeEl = jQuery('.' + $scope.id);
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
                        $scope.onSelectNode(data.node.original);
                    });
                    $scope.isInit = true;
                }

                function getTree() {
                    var treeEl = jQuery('.' + $scope.id);;
                    return treeEl.jstree(true);
                }

                function getSelectedNode() {
                    var tree = getTree();
                    var selected = tree.get_selected(), i, j, len;
                    len = selected.length;
                    for (i = 0, j = len; i < j; i++) {
                        selected = selected.concat(tree.get_node(selected[i]).parents);
                    }
                    selected = jQuery.vakata.array_unique(selected);
                    var selectedNodes = [];
                    len = selected.length;
                    for (i = 0; i < len; i++) {
                        if (selected[i] != '#') {
                            selectedNodes.push(tree.get_node(selected[i]));
                        }
                    }
                    if (selectedNodes.length > 0) {
                        return selectedNodes[0];
                    }
                    return null;
                }

                function getTreeData() {
                    var treeData = [];
                    if ($scope.resources && $scope.resources.length > 0) {
                        var len = $scope.resources.length;
                        for (var i = 0; i < len; i++) {
                            var resource = $scope.resources[i];
                            var nodeData = createNodeData(resource);
                            if (resource.children && resource.children.length > 0) {
                                createCascadeChildren(nodeData, resource.children);
                            }
                            treeData.push(nodeData);
                        }
                    }
                    return treeData;
                }

                function createCascadeChildren(parent, resources) {
                    var len = resources.length;
                    for (var i = 0; i < len; i++) {
                        var resource = resources[i];
                        var nodeData = createNodeData(resource);
                        if (resource.children && resource.children.length > 0) {
                            createCascadeChildren(nodeData, resource.children);
                        }
                        parent.children.push(nodeData);
                    }
                }

                function createNodeData(resource) {
                    return {
                        id: resource.code,
                        text: resource.name,
                        code: resource.code,
                        virtual: resource.virtual,
                        isLoaded: false,
                        children: []
                    };
                }

                $scope.onSelectNode = function (original) {
                    $scope.onnodeselect({node: original});
                };

                $scope.getResources = function () {
                    privilegeService.listResourceSummaries().success(function (result) {
                        $scope.resources = result;
                        initTree();
                    });
                };
                $scope.getResources();
            }
        };
    }]);
