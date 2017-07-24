(function (angular, factory) {
    if (typeof define === 'function' && define.amd) {
        define(['angular', 'ckeditor'], function (angular) {
            return factory(angular);
        });
    } else {
        return factory(angular);
    }
}(angular || null, function (angular) {
    var app = angular.module('ngCkeditor', []);
    var $defer, loaded = false;

    app.run(['$q', '$timeout', function ($q, $timeout) {
        $defer = $q.defer();

        if (angular.isUndefined(CKEDITOR)) {
            throw new Error('CKEDITOR not found');
        }
        CKEDITOR.disableAutoInline = true;
        function checkLoaded() {
            if (CKEDITOR.status == 'loaded') {
                loaded = true;
                $defer.resolve();
            } else {
                checkLoaded();
            }
        }

        CKEDITOR.on('loaded', checkLoaded);
        $timeout(checkLoaded, 100);
    }])

    app.directive('ckeditor', ['$timeout', '$q', function ($timeout, $q) {
        'use strict';

        return {
            restrict: 'AC',
            require: ['ngModel', '^?form'],
            scope: false,
            link: function (scope, element, attrs, ctrls) {
                var ngModel = ctrls[0];
                var form = ctrls[1] || null;
                var EMPTY_HTML = '<p></p>',
                    isTextarea = element[0].tagName.toLowerCase() == 'textarea',
                    data = [],
                    isReady = false;

                if (!isTextarea) {
                    element.attr('contenteditable', true);
                }

                var onLoad = function () {
                    var options = {
                        toolbar: 'full',
                        toolbar_full: [
                            {
                                name: 'basicstyles',
                                items: ['Bold', 'Italic', 'Strike', 'Underline']
                            },
                            {
                                name: 'paragraph',
                                items: ['BulletedList', 'NumberedList', 'Blockquote']
                            },
                            {
                                name: 'editing',
                                items: ['JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock']
                            },
                            {
                                name: 'links',
                                items: ['Link', 'Unlink', 'Anchor']
                            },
                            {
                                name: 'tools',
                                items: ['SpellChecker', 'Maximize']
                            },
                            '/',
                            {
                                name: 'styles',
                                items: ['Format', 'FontSize', 'TextColor', 'Font', 'PasteText', 'PasteFromWord', 'RemoveFormat']
                            },
                            {
                                name: 'insert',
                                items: ['Image', 'Table', 'SpecialChar']
                            },
                            {name: 'forms', items: ['Outdent', 'Indent']},
                            {name: 'clipboard', items: ['Undo', 'Redo']},
                            {name: 'document', items: ['PageBreak']}
                        ],
                        disableNativeSpellChecker: false,
                        uiColor: '#FAFAFA',
                        height: '400px',
                        width: '100%'
                    };
                    options = angular.extend(options, scope[attrs.ckeditor]);

                    var instance = (isTextarea) ? CKEDITOR.replace(element[0], options) : CKEDITOR.inline(element[0], options),
                        configLoaderDef = $q.defer();

                    var maxlength = (options.maxlength ? options.maxlength : 5000);
                    instance.on('key', function (event) {
                        var oldhtml = instance.getData();
                        var description = oldhtml.replace(/<.*?>/ig, "");
                        var etop = $("#cke_1_top");
                        var _slen = maxlength - description.length;
                        var _label = etop.find("#canwrite");
                        if (description.length > maxlength) {
                            //alert("最多可以输入"+maxlength+"个文字，您已达到最大字数限制");
                            instance.setData(oldhtml.substring(0, maxlength));
                            _label.html("还可以输入0字");
                        } else {
                            _label.html("还可以输入" + _slen + "字");
                        }

                        $timeout(function () {
                            var data = instance.getData();
                            if (data.length > maxlength) {
                                var data = instance.getData();
                                data = data.substring(0, maxlength);
                                instance.setData(data);
                            }
                        }, 500);
                    });



                    element.bind('$destroy', function () {
                        instance.destroy(
                            false //If the instance is replacing a DOM element, this parameter indicates whether or not to update the element with the instance contents.
                        );
                    });




                    var setModelData = function (setPristine) {
                        var data = instance.getData();
                        if (data == '') {
                            data = null;
                        }
                        $timeout(function () { // for key up event
                            (setPristine !== true || data != ngModel.$viewValue) && ngModel.$setViewValue(data);
                            (setPristine === true && form) && form.$setPristine();
                        }, 0);
                    }, onUpdateModelData = function (setPristine) {
                        if (!data.length) {
                            return;
                        }


                        var item = data.pop() || EMPTY_HTML;
                        isReady = false;
                        instance.setData(item, function () {
                            setModelData(setPristine);
                            isReady = true;
                        });
                    }

                    //instance.on('pasteState',   setModelData);
                    instance.on('change', setModelData);
                    instance.on('blur', setModelData);
                    //instance.on('key',          setModelData); // for source view

                    instance.on('instanceReady', function () {
                        scope.$broadcast("ckeditor.ready");
                        scope.$apply(function () {
                            onUpdateModelData(true);
                        });

                        instance.document.on("keyup", setModelData);

                        // 最大字数描述
                        var oldhtml = instance.getData();
                        var description = oldhtml.replace(/<.*?>/ig, "");
                        var _slen = maxlength - description.length;
                        var canwrite = $("<label id='canwrite'>还可以输入" + _slen + "字</label>");
                        var etop = $("#cke_1_top");
                        if (etop.find("#canwrite").length < 1) {
                            canwrite.css({border: '1px #f1f1f1 solid', 'line-height': '28px', color: '#999'});
                            etop.prepend(canwrite);
                        }
                    });
                    instance.on('customConfigLoaded', function () {
                        configLoaderDef.resolve();
                    });

                    ngModel.$render = function () {
                        data.push(ngModel.$viewValue);
                        if (isReady) {
                            onUpdateModelData();
                        }
                    };
                };

                if (CKEDITOR.status == 'loaded') {
                    loaded = true;
                }
                if (loaded) {
                    onLoad();
                } else {
                    $defer.promise.then(onLoad);
                }
            }
        };
    }]);

    return app;
}));
