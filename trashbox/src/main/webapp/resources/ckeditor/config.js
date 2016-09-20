/**
 * @license Copyright (c) 2003-2015, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';

};

//기본 ckeditor업로드 버튼 클릭시 이미지 탭으로 실행
CKEDITOR.on('dialogDefinition', function (ev) {
    var dialogName = ev.data.name;
    var dialog = ev.data.definition.dialog;
    var dialogDefinition = ev.data.definition;
    if (dialogName == 'image') {
      dialog.on('show', function (obj) {
        this.selectPage('Upload');
        });
      dialogDefinition.removeContents('advanced'); //자세히탭 삭제
      dialogDefinition.removeContents('Link');  //링크탭 삭제
      }
});





