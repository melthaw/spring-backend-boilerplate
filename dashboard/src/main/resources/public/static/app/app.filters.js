'use strict';

angular.module('webapp')
    .filter('escape', ['webappSettings', function (webappSettings) {
        return function (input) {
            if (input) {
                return webappSettings.basicApiUrl + '/files/' + window.encodeURIComponent(window.encodeURIComponent(input));
            }
            return "";
        }
    }])
    .filter('text_overflow', [function () {
        return function (input, max, tail) {
            if (!input) return '';

            max = parseInt(max, 10);

            if (!max) {
                return input;
            }

            if (input.length <= max) {
                return input;
            }

            input = input.substr(0, max);
            return input + (tail || '…');
        }
    }])
    .filter('enums_trans', ['Enums', function (Enums) {
        return function (input, type) {
            if (input && Enums[type]) {
                return Enums[type][input];
            }
            return "";
        }
    }])
    .filter('propsFilter', function () {
        return function (items, props) {
            var out = [];

            if (angular.isArray(items)) {
                var keys = Object.keys(props);

                items.forEach(function (item) {
                    var itemMatches = false;

                    for (var i = 0; i < keys.length; i++) {
                        var prop = keys[i];
                        var text = props[prop].toLowerCase();
                        if (item[prop].toString().toLowerCase().indexOf(text) !== -1) {
                            itemMatches = true;
                            break;
                        }
                    }

                    if (itemMatches) {
                        out.push(item);
                    }
                });
            } else {
                // Let the output be the input untouched
                out = items;
            }

            return out;
        };
    })
    .filter('trustHtml', function ($sce) {
        return function (input) {
            return $sce.trustAsHtml(input);
        }
    })
    .filter('join_props', [function () {
        return function (input, propNames, propJoinTag, joinTag) {
            if (!input || input == '') return '';

            var resultArr = [];
            angular.forEach(input, function (value) {
                var keys = propNames.split(',');
                var itemAttr = [];
                angular.forEach(keys, function (key) {
                    if (value[key]) {
                        itemAttr.push(value[key]);
                    }
                });

                var temp = itemAttr.join(propJoinTag);
                resultArr.push(temp);
            });

            return resultArr.join(joinTag);
        }
    }])
    .filter('join_props_arr', [function () {
        return function (input, propNames, propJoinTag, joinTag) {
            if (!input || input == '') return '';
            var resultArr = [];
            angular.forEach(input, function (value) {
                var keys = propNames.split(',');
                if (keys.length > 0) {
                    var arrayOpt = keys[0];
                    var propConfig = arrayOpt.split('.');
                    if (propConfig.length > 0) {
                        var arrayName = propConfig[0];
                        var arrayPropName = propConfig[1];
                        if (value[arrayName] && angular.isArray(value[arrayName])) {
                            angular.forEach(value[arrayName], function (item) {
                                var itemAttr = [];
                                itemAttr.push(item[arrayPropName]);
                                if (value[keys[1]]) {
                                    itemAttr.push(value[keys[1]])
                                    var temp = itemAttr.join(propJoinTag);
                                    resultArr.push(temp);
                                }
                            });
                        }
                    }
                }
            });

            return resultArr.join(joinTag);
        }
    }])
    .filter('contains', function () {
        return function (array, needle) {
            if (!array) {
                return -1;
            }

            if (typeof array === 'string' || array instanceof String) {
                return array.toLowerCase().indexOf(needle) >= 0;
            } else {
                return array.indexOf(needle) >= 0;
            }

        };
    });

