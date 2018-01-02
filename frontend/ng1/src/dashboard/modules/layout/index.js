import angular from "angular";
import uirouter from "angular-ui-router";

import routing from "./layout.routes.js";
import MainController from "./main.controller.js";
import topbar from "../../directives/topbar.directive.js";
import menubar from "../../directives/menu.directive.js";

export default angular.module('dashboard.layout', [uirouter, topbar, menubar])
    .config(routing)
    .controller('MainController', MainController)
    .name;
