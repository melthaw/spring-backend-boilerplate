Angular-notys
=========
>Gmail and Noty inspired notification for AngularJS

[→ See Demo ←](http://steve-ng.github.io/angular-notys/#/api/showNoty)

___

Getting Started
=========


**Step 1: Installation through bower**

    bower install angular-notys


**Step 2: AngularJS project set up**

Add angular-noty as a dependency in your angular project 


    angular.module('your_app_name', ['angular-noty'])
 
Add angular-noty directive to the line after your <body>  


    <body>
    <div noty-container></div> 
Finally inject noty to your services/controllers


    angular.module('your_app_name').controller('your_controller_name', 
    ["$scope", "noty", function ($scope, noty,) {
    
**Step 3: To call noty **


    noty.showNoty({
      text: "Deleted an email",
      ttl: 1000000, //time to live in miliseconds
      type: "default", //success, warning 
      options: ['Undo', 'Dismiss'], 
      optionsCallBack:  function callback(optionClicked, optionIndexClicked) {
        //handling code for options clicked
      }
    })