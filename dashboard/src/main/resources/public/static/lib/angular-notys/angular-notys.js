'use strict';

//	Module
var module = angular.module('angular-noty', ['ngAnimate'])
.constant("notyConstant", {
	'ttl': 1000000,
	'defaultNotyLocation': 'top',
	'defaultNotyType': 'default'
})
.service('noty',
['$rootScope', 'notyConstant',
function($rootScope, notyConstant) {

	this.showNoty = function(obj) {
		
		var message = {
			text: obj.text,
			options: obj.options,
			callback: obj.optionsCallBack,
			location: "top", 
			type: obj.type || notyConstant['defaultNotyType'],
			ttl: obj.ttl || notyConstant['ttl']
		}

		$rootScope.$broadcast("noty-message",message);
	}


}])
.directive("notyContainer", 
["$rootScope", "$timeout", 
function($rootScope, $timeout){
	return {
		restrict: 'A',
		template: 	'<div class="noty-div" ng-repeat="messagesObj in [top]" ng-class="computePositionClass(messagesObj.location)">' + 
					
					'		<div class="noty-item" ng-repeat="message in messagesObj.messages" ng-class="computeClasses(message)">' + 
								
					'			<span class="close" ng-click="deleteMessage(message)">&times;</span>' +
								
					'			<span ng-show="message.text" class="text">  ' +
					'				{{message.text}}' +  
					'			</span>' +

					'			<span class="link" ng-repeat="option in message.options" ng-click="clickedOption(message, option, $index)">{{option}}</span> '+
					
					'		</div>' +
					'</div>',
		scope: true,
		controller: ['$scope' , function($scope) {

			$scope.top = {
				location: "top",
				messages: []
			}

			$rootScope.$on("noty-message", function(event, message) {
				addMessage(message);
			});

			$scope.clickedOption = function(message, optionTextSelected, optionIndexSelected) {

				message.callback(optionTextSelected, optionIndexSelected);

				//delete the message after clicking status change
				$scope.deleteMessage(message);
			}

			$scope.deleteMessage = function (message) {
				var index = $scope[message.location].messages.indexOf(message);
				if (index > -1) {
					$scope[message.location].messages.splice(index, 1);
					$scope.safeApply();
				}
			};

			$scope.safeApply = function(fn) {
				var phase = this.$root.$$phase;
				if(phase == '$apply' || phase == '$digest') {
		    		if(fn && (typeof(fn) === 'function')) {
		    			fn();
		    		}
		    	} else {
		      		this.$apply(fn);
		    	}
			};

			$scope.computePositionClass = function(position) {

				if (position == "top"){
					return "noty-top";
				}
			}

			$scope.computeClasses = function (message) {

				var cssClass= "";

				if (message.type == "default" || message.type ==null) {
					cssClass += "default";
				}
				else if (message.type =="warning") {
					cssClass += "warning";
				}
				else if (message.type =="success") {
					cssClass += "success";
				}
				else if (message.type =="gmail") {
					cssClass += "gmail";
				}

				return cssClass;
			};


			function addMessage(message) {

				$scope[message.location].messages.push(message);

				if (message.ttl && message.ttl !== -1) {
					$timeout(function () {
						$scope.deleteMessage(message);
					}, message.ttl);
				}
			}

		}]
	}

}])





;
