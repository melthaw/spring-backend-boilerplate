import angular from "angular";
import AuthenticationServcie from "./authentication.service";
import escapeFilter from "./escape.filter";
import httpProvider from "./httpProvider.config";
import run from "./run";

export default angular.module('dashboard.shared', [])
    .constant('Constants', {
        developerMode: false,
        basicApiUrl: '/api',
        tplsBasePath: '/static/app'
    })
    .config(httpProvider)
    .filter('escape', escapeFilter)
    .service('AuthenticationServcie', AuthenticationServcie)
    .run(run)
    .name;