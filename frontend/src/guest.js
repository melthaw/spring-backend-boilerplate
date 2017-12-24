
import "bootstrap/dist/css/bootstrap.min.css";
import "./asserts/lib/admin-lte/dist/css/AdminLTE.min.css";
import "./asserts/lib/admin-lte/dist/css/skins/_all-skins.min.css";
import "font-awesome/css/font-awesome.min.css";
import "./asserts/css/custom.css";

import "./asserts/lib/html5shiv.min";
import jQuery from "jquery";
window.$ = window.jQuery = jQuery;
require('./asserts/lib/jquery-migrate-1.4.1.min');
require('bootstrap');


$(document).ready(function () {
    if ($.browser.msie) {
        window.location = '/browsers';
        return;
    }

    $('#username').bind('keyup', function (e) {
        if (e.keyCode === 13) {
            $('#password').focus();
        }
    });
    $('#password').bind('keyup', function (e) {
        if (e.keyCode === 13) {
            login();
        }
    });

    $('#loginBtn').bind('click', function (e) {
        $('#loginError').hide();
        $('#errorMessage').hide();
        if (!$('#username').val()) {
            $('#errorMessage').text('请输入用户名!');
            $('#errorMessage').show();
            return;
        }
        if (!$('#password').val()) {
            $('#errorMessage').text('请输入密码!');
            $('#errorMessage').show();
            return;
        }
        $('form').submit();
    });

    var timeInMills = new Date().getTime();
    var imgIndex = (parseInt(timeInMills / (60 * 1000)) % 53 + 1);
    while (imgIndex.toString().length < 3) {
        imgIndex = '0' + imgIndex;
    }

    $('.indexBackGroudStyle').css('background-image', 'url(\'./asserts/imgs/bg/Image' + imgIndex + '.jpg\')');

});
