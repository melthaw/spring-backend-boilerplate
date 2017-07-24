/**
 * @license Copyright (c) 2003-2015, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function (config) {
    // Define changes to default configuration here. For example:
    // config.language = 'fr';
    // config.uiColor = '#AADC6E';
    config.font_names = '微软雅黑/Microsoft YaHei;宋体/Songti SC;黑体/Heiti SC;仿宋/FangSong;楷体/Kaiti SC;幼圆/Yuanti SC;' + config.font_names;
    config.font_defaultLabel = '微软雅黑';
    config.fontSize_defaultLabel = '16';
    config.image_previewText = '请插入图片';
};
