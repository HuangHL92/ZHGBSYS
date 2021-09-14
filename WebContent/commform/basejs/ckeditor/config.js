/*
Copyright (c) 2003-2011, CKSource - Frederico Knabben. All rights reserved.
For licensing, see LICENSE.html or http://ckeditor.com/license
*/

CKEDITOR.editorConfig = function( config )
{
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
	config.disableNativeSpellChecker = true;
	config.scayt_autoStartup = false;
	config.font_names = 'ËÎÌå;¿¬Ìå_GB2312;ÐÂËÎÌå;ºÚÌå;Á¥Êé;Ó×Ô²;Î¢ÈíÑÅºÚ;Arial;Comic Sans MS;Courier New;Tahoma;Times New Roman;Verdana';
	config.toolbar_Full =
    [
        //['Source','-','Preview','-','Templates'],
        ['Preview','Print'],
        //['Cut','Copy','Paste','PasteText','PasteFromWord','-','Print', 'SpellChecker', 'Scayt'],
        ['Cut','Copy','Paste','PasteText','PasteFromWord'],
        ['Undo','Redo','-','Find','Replace','-','SelectAll','RemoveFormat','-','Source'],
        //['Form', 'Checkbox', 'Radio', 'TextField', 'Textarea', 'Select', 'Button', 'ImageButton', 'HiddenField'],
        '/',
        ['Bold','Italic','Underline','Strike','-','Subscript','Superscript'],
        ['NumberedList','BulletedList','-','Outdent','Indent','Blockquote','CreateDiv'],
        ['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
        ['Link','Unlink','Anchor'],
        ['Image','Flash','Table','HorizontalRule','Smiley','SpecialChar','PageBreak'],
        // ['Table','HorizontalRule','Smiley','SpecialChar','PageBreak'],
        '/',
        ['Styles','Format','Font','FontSize'],
        ['TextColor','BGColor'],
        ['Maximize', 'ShowBlocks']
    ];

    config.toolbar_Basic =
    [
        ['Bold', 'Italic', '-', 'NumberedList', 'BulletedList', '-', 'Link', 'Unlink']
    ];

};
