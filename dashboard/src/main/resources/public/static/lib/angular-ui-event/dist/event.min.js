/*!
 * angular-ui-event
 * https://github.com/angular-ui/ui-event
 * Version: 1.0.0 - 2015-06-30T08:34:06.369Z
 * License: MIT
 */
!function(){"use strict";angular.module("ui.event",[]).directive("uiEvent",["$parse",function(n){return function(e,a,r){var t=e.$eval(r.uiEvent);angular.forEach(t,function(r,t){var i=n(r);a.bind(t,function(n){var a=Array.prototype.slice.call(arguments);a=a.splice(1),i(e,{$event:n,$params:a}),e.$$phase||e.$apply()})})}}])}();