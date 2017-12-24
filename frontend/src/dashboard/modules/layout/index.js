import angular from "angular";
import uirouter from "angular-ui-router";

import routing from "./layout.routes";
import MainController from "./main.controller";
import topbar from "../../directives/topbar.directive";
import menubar from "../../directives/menu.directive";

export default angular.module('dashboard.layout', [uirouter, topbar, menubar])
    .config(routing)
    .controller('MainController', MainController)
    .name;
