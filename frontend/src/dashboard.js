import "bootstrap/dist/css/bootstrap.min.css";
import "font-awesome/css/font-awesome.min.css";
import "./asserts/lib/admin-lte/dist/css/AdminLTE.min.css";
import "./asserts/lib/admin-lte/dist/css/skins/_all-skins.min.css";
import "./asserts/css/custom.css";


import jQuery from "jquery";
window.$ = window.jQuery = jQuery;

import angular from "angular";
import uirouter from "angular-ui-router";
import routing from "./dashboard.routes";


import layout from "./dashboard/modules/layout";
import shared from "./dashboard/modules/shared";
import AdminLTE from "./asserts/lib/admin-lte/dist/js/app";

window.$.AdminLTE = AdminLTE;

angular.module('dashboard', [uirouter, shared, layout])
    .config(routing);