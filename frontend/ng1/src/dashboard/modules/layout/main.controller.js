import angular from "angular";
export default class MainController {
    constructor($timeout) {
        $timeout(function () {
            $.AdminLTE.pushMenu("[data-toggle='offcanvas']")
            $.AdminLTE.tree('.sidebar');
            var $ul = angular.element('.treeview-menu li.active').parent();
            if (($ul.is('.treeview-menu')) && (!$ul.is(':visible'))) {
                //Close the menu
                $ul.slideDown('normal', function () {
                    //Add the class active to the parent li
                    $ul.addClass('menu-open');
                    $.AdminLTE.layout.fix();
                });
            }
        }, 1000);
    }
}