
function editor_defaultConfig(objname){this.version="2.03"
this.width="auto";this.height="auto";this.bodyStyle='background-color: #FFFFFF; font-family: "宋体"; font-size: 12px;';this.imgURL=_editor_url+'images/';this.debug=0;this.replaceNextlines=0;this.plaintextInput=0;this.toolbar=[['fontname','fontsize','bold','italic','underline','separator','justifyleft','justifycenter','justifyright','separator','OrderedList','UnOrderedList','Outdent','Indent','separator','forecolor','backcolor','separator','HorizontalRule','Createlink','InsertTable','htmlmode','separator']];this.fontnames={"宋体":"宋体","黑体":"黑体","楷体_GB2312":"楷体_GB2312","隶书":"隶书","Comic Sans MS":"Comic Sans MS","Arial":"arial, helvetica, sans-serif","Courier New":"courier new, courier, mono","Georgia":"Georgia, Times New Roman, Times, Serif","Tahoma":"Tahoma, Arial, Helvetica, sans-serif","Times New Roman":"times new roman, times, serif","Verdana":"Verdana, Arial, Helvetica, sans-serif","impact":"impact","WingDings":"WingDings"};this.fontsizes={"1 (8 pt)":"1","2 (10 pt)":"2","3 (12 pt)":"3","4 (14 pt)":"4","5 (18 pt)":"5","6 (24 pt)":"6","7 (36 pt)":"7"};this.fontstyles=[];this.btnList={"bold":['Bold','加粗','editor_action(this.id)','ed_format_bold.gif'],"italic":['Italic','斜体','editor_action(this.id)','ed_format_italic.gif'],"underline":['Underline','下划线','editor_action(this.id)','ed_format_underline.gif'],"strikethrough":['StrikeThrough','Strikethrough','editor_action(this.id)','ed_format_strike.gif'],"subscript":['SubScript','Subscript','editor_action(this.id)','ed_format_sub.gif'],"superscript":['SuperScript','Superscript','editor_action(this.id)','ed_format_sup.gif'],"justifyleft":['JustifyLeft','左对齐','editor_action(this.id)','ed_align_left.gif'],"justifycenter":['JustifyCenter','居中','editor_action(this.id)','ed_align_center.gif'],"justifyright":['JustifyRight','右对齐','editor_action(this.id)','ed_align_right.gif'],"orderedlist":['InsertOrderedList','列表','editor_action(this.id)','ed_list_num.gif'],"unorderedlist":['InsertUnorderedList','列表2','editor_action(this.id)','ed_list_bullet.gif'],"outdent":['Outdent','左缩进','editor_action(this.id)','ed_indent_less.gif'],"indent":['Indent','右缩进','editor_action(this.id)','ed_indent_more.gif'],"forecolor":['ForeColor','字体颜色','editor_action(this.id)','ed_color_fg.gif'],"backcolor":['BackColor','背景颜色','editor_action(this.id)','ed_color_bg.gif'],"horizontalrule":['InsertHorizontalRule','水平线','editor_action(this.id)','ed_hr.gif'],"createlink":['CreateLink','超连接','editor_action(this.id)','ed_link.gif'],"insertimage":['InsertImage','插入图片','editor_action(this.id)','ed_image.gif'],"inserttable":['InsertTable','插入表格','editor_action(this.id)','insert_table.gif'],"htmlmode":['HtmlMode','编辑HTML','editor_setmode(\''+objname+'\')','ed_html.gif'],"popupeditor":['popupeditor','新窗口中编辑','editor_action(this.id)','fullscreen_maximize.gif'],"about":['about','版权说明','editor_about(\''+objname+'\')','ed_about.gif'],"custom1":['custom1','Purpose of button 1','editor_action(this.id)','ed_custom.gif'],"custom2":['custom2','Purpose of button 2','editor_action(this.id)','ed_custom.gif'],"custom3":['custom3','Purpose of button 3','editor_action(this.id)','ed_custom.gif'],"help":['showhelp','Help using editor','editor_action(this.id)','ed_help.gif']};}
function editor_generate(objname,userConfig){var config=new editor_defaultConfig(objname);if(userConfig){for(var thisName in userConfig){if(userConfig.hasOwnProperty(thisName)){if(userConfig[thisName]){config[thisName]=userConfig[thisName];}}}}
document.all[objname].config=config;var obj=document.all[objname];if(!config.width||config.width=="auto"){if(obj.style.width){config.width=obj.style.width;}
else if(obj.cols){config.width=(obj.cols*8)+22;}
else{config.width='100%';}}
if(!config.height||config.height=="auto"){if(obj.style.height){config.height=obj.style.height;}
else if(obj.rows){config.height=obj.rows*17}
else{config.height='200';}}
var tblOpen='<table border=0 cellspacing=0 cellpadding=0 style="float: left;"  unselectable="on"><tr><td style="border: none; padding: 1 0 0 0"><nobr>';var tblClose='</nobr></td></tr></table>\n';var toolbar='';var btnGroup,btnItem,aboutEditor;for(var btnGroup in config.toolbar){if(!(config.toolbar.hasOwnProperty(btnGroup))){continue;}
if(config.toolbar[btnGroup].length==1&&config.toolbar[btnGroup][0].toLowerCase()=="linebreak"){toolbar+='<br clear="all">';continue;}
toolbar+=tblOpen;for(var btnItem in config.toolbar[btnGroup]){if(config.toolbar[btnGroup].hasOwnProperty(btnItem)){var btnName=config.toolbar[btnGroup][btnItem].toLowerCase();if(btnName=="fontname"){toolbar+='<select id="_'+objname+'_FontName" onChange="editor_action(this.id)" unselectable="on" style="margin: 1 2 0 2; font-size: 12px;">';for(var fontname in config.fontnames){if(config.fontnames.hasOwnProperty(fontname)){toolbar+='<option value="'+config.fontnames[fontname]+'">'+fontname+'</option>'}}
toolbar+='</select>';continue;}
if(btnName=="fontsize"){toolbar+='<select id="_'+objname+'_FontSize" onChange="editor_action(this.id)" unselectable="on" style="margin: 1 2 0 0; font-size: 12px;">';for(var fontsize in config.fontsizes){if(config.fontsizes.hasOwnProperty(fontsize)){toolbar+='<option value="'+config.fontsizes[fontsize]+'">'+fontsize+'</option>'}}
toolbar+='</select>\n';continue;}
if(btnName=="fontstyle"){toolbar+='<select id="_'+objname+'_FontStyle" onChange="editor_action(this.id)" unselectable="on" style="margin: 1 2 0 0; font-size: 12px;">';+'<option value="">Font Style</option>';for(var i in config.fontstyles){if(config.fontstyles.hasOwnProperty(i)){var fontstyle=config.fontstyles[i];toolbar+='<option value="'+fontstyle.className+'">'+fontstyle.name+'</option>'}}
toolbar+='</select>';continue;}
if(btnName=="separator"){toolbar+='<span style="border: 1px inset; width: 1px; font-size: 16px; height: 16px; margin: 0 3 0 3"></span>';continue;}
var btnObj=config.btnList[btnName];if(btnName=='linebreak'){alert("htmlArea error: 'linebreak' must be in a subgroup by itself, not with other buttons.\n\nhtmlArea wysiwyg editor not created.");return;}
if(!btnObj){alert("htmlArea error: button '"+btnName+"' not found in button list when creating the wysiwyg editor for '"+objname+"'.\nPlease make sure you entered the button name correctly.\n\nhtmlArea wysiwyg editor not created.");return;}
var btnCmdID=btnObj[0];var btnTitle=btnObj[1];var btnOnClick=btnObj[2];var btnImage=btnObj[3];toolbar+='<button title="'+btnTitle+'" id="_'+objname+'_'+btnCmdID+'" class="btn" onClick="'+btnOnClick+'" onmouseover="if(this.className==\'btn\'){this.className=\'btnOver\'}" onmouseout="if(this.className==\'btnOver\'){this.className=\'btn\'}" unselectable="on"><img src="'+config.imgURL+btnImage+'" border=0 unselectable="on"></button>';}}}
var editor='<span id="_editor_toolbar"><table border=0 cellspacing=0 cellpadding=0 bgcolor="buttonface" style="padding: 1 0 0 2" width='+config.width+' unselectable="on"><tr><td>\n'
+toolbar
+'</td></tr></table>\n'
+'<textarea ID="_'+objname+'_editor" style="width:'+config.width+'; height:'+config.height+'; margin-top: -1px; margin-bottom: -1px;" wrap=soft></textarea>';editor+='<div id="_'+objname+'_cMenu" style="position: absolute; visibility: hidden;"></div>';if(!config.debug){document.all[objname].style.display="none";}
if(config.plaintextInput){var contents=document.all[objname].value;contents=contents.replace(/\r\n/g,'<br>');contents=contents.replace(/\n/g,'<br>');contents=contents.replace(/\r/g,'<br>');document.all[objname].value=contents;}
document.all[objname].insertAdjacentHTML('afterEnd',editor)
editor_setmode(objname,'init');for(var idx=0;idx<document.forms.length;idx++){var r=document.forms[idx].attachEvent('onsubmit',function(){editor_filterOutput(objname);});if(!r){alert("Error attaching event to form!");}}
return true;}
function editor_action(button_id){var BtnParts=Array();BtnParts=button_id.split("_");var objname=button_id.replace(/^_(.*)_[^_]*$/,'$1');var cmdID=BtnParts[BtnParts.length-1];var button_obj=document.all[button_id];var editor_obj=document.all["_"+objname+"_editor"];var config=document.all[objname].config;if(cmdID=='showhelp'){window.open(_editor_url+"popups/editor_help.html",'EditorHelp');return;}
if(cmdID=='popupeditor'){window.open(_editor_url+"popups/fullscreen.html?"+objname,'FullScreen','toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,width=640,height=480');return;}
if(editor_obj.tagName.toLowerCase()=='textarea'){return;}
var editdoc=editor_obj.contentWindow.document;editor_focus(editor_obj);var idx=button_obj.selectedIndex;var val=(idx!=null)?button_obj[idx].value:null;if(0){}
else if(cmdID=='custom1'){alert("Hello, I am custom button 1!");}
else if(cmdID=='custom2'){var myTitle="This is a custom title";var myText=showModalDialog(_editor_url+"popups/custom2.html",myTitle,"resizable: yes; help: no; status: no; scroll: no; ");if(myText){editor_insertHTML(objname,myText);}}
else if(cmdID=='custom3'){editor_insertHTML(objname,"It's easy to add buttons that insert text!");}
else if(cmdID=='FontName'&&val){editdoc.execCommand(cmdID,0,val);}
else if(cmdID=='FontSize'&&val){editdoc.execCommand(cmdID,0,val);}
else if(cmdID=='FontStyle'&&val){editdoc.execCommand('RemoveFormat');editdoc.execCommand('FontName',0,'636c6173734e616d6520706c616365686f6c646572');var fontArray=editdoc.all.tags("FONT");for(i=0;i<fontArray.length;i++){if(fontArray[i].face=='636c6173734e616d6520706c616365686f6c646572'){fontArray[i].face="";fontArray[i].className=val;fontArray[i].outerHTML=fontArray[i].outerHTML.replace(/face=['"]+/,"");}}
button_obj.selectedIndex=0;}
else if(cmdID=='ForeColor'||cmdID=='BackColor'){var oldcolor=_dec_to_rgb(editdoc.queryCommandValue(cmdID));var newcolor=showModalDialog(_editor_url+"popups/select_color.html",oldcolor,"resizable: no; help: no; status: no; scroll: no;");if(newcolor!=null){editdoc.execCommand(cmdID,false,newcolor);}}
else{if(cmdID.toLowerCase()=='subscript'&&editdoc.queryCommandState('superscript')){editdoc.execCommand('superscript');}
if(cmdID.toLowerCase()=='superscript'&&editdoc.queryCommandState('subscript')){editdoc.execCommand('subscript');}
if(cmdID.toLowerCase()=='createlink'){editdoc.execCommand(cmdID,1);}
else if(cmdID.toLowerCase()=='insertimage'){showModalDialog(_editor_url+"popups/insert_image.html",editdoc,"resizable: no; help: no; status: no; scroll: no; ");}
else if(cmdID.toLowerCase()=='inserttable'){showModalDialog(_editor_url+"popups/insert_table.html?"+objname,window,"resizable: yes; help: no; status: no; scroll: no; ");}
else{editdoc.execCommand(cmdID);}}
editor_event(objname);}
function editor_event(objname,runDelay){var config=document.all[objname].config;var editor_obj=document.all["_"+objname+"_editor"];if(runDelay==null){runDelay=0;}
var editdoc;var editEvent=editor_obj.contentWindow?editor_obj.contentWindow.event:event;if(editEvent&&editEvent.keyCode){var ord=editEvent.keyCode;var ctrlKey=editEvent.ctrlKey;var altKey=editEvent.altKey;var shiftKey=editEvent.shiftKey;if(ord==16){return;}
if(ord==17){return;}
if(ord==18){return;}
if(ctrlKey&&(ord==122||ord==90)){return;}
if((ctrlKey&&(ord==121||ord==89))||ctrlKey&&shiftKey&&(ord==122||ord==90)){return;}}
if(runDelay>0){return setTimeout(function(){editor_event(objname);},runDelay);}
if(this.tooSoon==1&&runDelay>=0){this.queue=1;return;}
this.tooSoon=1;setTimeout(function(){this.tooSoon=0;if(this.queue){editor_event(objname,-1);};this.queue=0;},333);editor_updateOutput(objname);editor_updateToolbar(objname);}
function editor_updateToolbar(objname,action){var config=document.all[objname].config;var editor_obj=document.all["_"+objname+"_editor"];if(action=="enable"||action=="disable"){var tbItems=new Array('FontName','FontSize','FontStyle');for(var btnName in config.btnList){if(config.btnList.hasOwnProperty(btnName)){tbItems.push(config.btnList[btnName][0]);}}
for(var idxN in tbItems){if(tbItems.hasOwnProperty(idxN)){var cmdID=tbItems[idxN];var tbObj=document.all["_"+objname+"_"+tbItems[idxN]];if(cmdID=="HtmlMode"||cmdID=="about"||cmdID=="showhelp"||cmdID=="popupeditor"){continue;}
if(tbObj==null){continue;}
var isBtn=(tbObj.tagName.toLowerCase()=="button")?true:false;if(action=="enable"){tbObj.disabled=false;if(isBtn){tbObj.className='btn'}}
if(action=="disable"){tbObj.disabled=true;if(isBtn){tbObj.className='btnNA'}}}}
return;}
if(editor_obj.tagName.toLowerCase()=='textarea'){return;}
var editdoc=editor_obj.contentWindow.document;var fontname_obj=document.all["_"+objname+"_FontName"];if(fontname_obj){var fontname=editdoc.queryCommandValue('FontName');if(fontname==null){fontname_obj.value=null;}
else{var found=0;for(i=0;i<fontname_obj.length;i++){if(fontname.toLowerCase()==fontname_obj[i].text.toLowerCase()){fontname_obj.selectedIndex=i;found=1;}}
if(found!=1){fontname_obj.value=null;}}}
var fontsize_obj=document.all["_"+objname+"_FontSize"];if(fontsize_obj){var fontsize=editdoc.queryCommandValue('FontSize');if(fontsize==null){fontsize_obj.value=null;}
else{var found=0;for(i=0;i<fontsize_obj.length;i++){if(fontsize==fontsize_obj[i].value){fontsize_obj.selectedIndex=i;found=1;}}
if(found!=1){fontsize_obj.value=null;}}}
var classname_obj=document.all["_"+objname+"_FontStyle"];if(classname_obj){var curRange=editdoc.selection.createRange();var pElement;if(curRange.length){pElement=curRange[0];}
else{pElement=curRange.parentElement();}
while(pElement&&!pElement.className){pElement=pElement.parentElement;}
var thisClass=pElement?pElement.className.toLowerCase():"";if(!thisClass&&classname_obj.value){classname_obj.value=null;}
else{var found=0;for(i=0;i<classname_obj.length;i++){if(thisClass==classname_obj[i].value.toLowerCase()){classname_obj.selectedIndex=i;found=1;}}
if(found!=1){classname_obj.value=null;}}}
var IDList=Array('Bold','Italic','Underline','StrikeThrough','SubScript','SuperScript','JustifyLeft','JustifyCenter','JustifyRight','InsertOrderedList','InsertUnorderedList');for(i=0;i<IDList.length;i++){var btnObj=document.all["_"+objname+"_"+IDList[i]];if(btnObj==null){continue;}
var cmdActive=editdoc.queryCommandState(IDList[i]);if(!cmdActive){if(btnObj.className!='btn'){btnObj.className='btn';}
if(btnObj.disabled!=false){btnObj.disabled=false;}}else if(cmdActive){if(btnObj.className!='btnDown'){btnObj.className='btnDown';}
if(btnObj.disabled!=false){btnObj.disabled=false;}}}}
function editor_updateOutput(objname){var config=document.all[objname].config;var editor_obj=document.all["_"+objname+"_editor"];var editEvent=editor_obj.contentWindow?editor_obj.contentWindow.event:event;var isTextarea=(editor_obj.tagName.toLowerCase()=='textarea');var editdoc=isTextarea?null:editor_obj.contentWindow.document;var contents;if(isTextarea){contents=editor_obj.value;}
else{contents=editdoc.body.innerHTML;}
if(config.lastUpdateOutput&&config.lastUpdateOutput==contents){return;}
else{config.lastUpdateOutput=contents;}
document.all[objname].value=contents;}
function editor_filterOutput(objname){editor_updateOutput(objname);var contents=document.all[objname].value;var config=document.all[objname].config;if(contents.toLowerCase()=='<p>&nbsp;</p>'){contents="";}
var filterTag=function(tagBody,tagName,tagAttr){tagName=tagName.toLowerCase();var closingTag=(tagBody.match(/^<\//))?true:false;if(tagName=='img'){tagBody=tagBody.replace(/(src\s*=\s*.)[^*]*(\*\*\*)/,"$1$2");}
if(tagName=='a'){tagBody=tagBody.replace(/(href\s*=\s*.)[^*]*(\*\*\*)/,"$1$2");}
return tagBody;};RegExp.lastIndex=0;var matchTag=/<\/?(\w+)((?:[^'">]*|'[^']*'|"[^"]*")*)>/g;contents=contents.replace(matchTag,filterTag);if(config.replaceNextlines){contents=contents.replace(/\r\n/g,' ');contents=contents.replace(/\n/g,' ');contents=contents.replace(/\r/g,' ');}
document.all[objname].value=contents;}
function editor_setmode(objname,mode){var config=document.all[objname].config;var editor_obj=document.all["_"+objname+"_editor"];if(document.readyState!='complete'){setTimeout(function(){editor_setmode(objname,mode)},25);return;}
var TextEdit='<textarea ID="_'+objname+'_editor" style="width:'+editor_obj.style.width+'; height:'+editor_obj.style.height+'; margin-top: -1px; margin-bottom: -1px;"></textarea>';var RichEdit='<iframe ID="_'+objname+'_editor"    style="width:'+editor_obj.style.width+'; height:'+editor_obj.style.height+';"></iframe>';if(mode=="textedit"||editor_obj.tagName.toLowerCase()=='iframe'){config.mode="textedit";var editdoc=editor_obj.contentWindow.document;var contents=editdoc.body.createTextRange().htmlText;editor_obj.outerHTML=TextEdit;editor_obj=document.all["_"+objname+"_editor"];editor_obj.value=contents;editor_event(objname);editor_updateToolbar(objname,"disable");editor_obj.onkeydown=function(){editor_event(objname);}
editor_obj.onkeypress=function(){editor_event(objname);}
editor_obj.onkeyup=function(){editor_event(objname);}
editor_obj.onmouseup=function(){editor_event(objname);}
editor_obj.ondrop=function(){editor_event(objname,100);}
editor_obj.oncut=function(){editor_event(objname,100);}
editor_obj.onpaste=function(){editor_event(objname,100);}
editor_obj.onblur=function(){editor_event(objname,-1);}
editor_updateOutput(objname);editor_focus(editor_obj);}
else{config.mode="wysiwyg";var contents=editor_obj.value;if(mode=='init'){contents=document.all[objname].value;}
editor_obj.outerHTML=RichEdit;editor_obj=document.all["_"+objname+"_editor"];var html="";html+='<html><head>\n';if(config.stylesheet){html+='<link href="'+config.stylesheet+'" rel="stylesheet" type="text/css">\n';}
html+='<style>\n';html+='body {'+config.bodyStyle+'} \n';for(var i in config.fontstyles){if(config.fontstyles.hasOwnProperty(i)){var fontstyle=config.fontstyles[i];if(fontstyle.classStyle){html+='.'+fontstyle.className+' {'+fontstyle.classStyle+'}\n';}}}
html+='</style>\n'
+'</head>\n'
+'<body contenteditable="true" topmargin=1 leftmargin=1'
+'>'
+contents
+'</body>\n'
+'</html>\n';var editdoc=editor_obj.contentWindow.document;editdoc.open();editdoc.write(html);editdoc.close();editor_updateToolbar(objname,"enable");editdoc.objname=objname;editdoc.onkeydown=function(){editor_event(objname);}
editdoc.onkeypress=function(){editor_event(objname);}
editdoc.onkeyup=function(){editor_event(objname);}
editdoc.onmouseup=function(){editor_event(objname);}
editdoc.body.ondrop=function(){editor_event(objname,100);}
editdoc.body.oncut=function(){editor_event(objname,100);}
editdoc.body.onpaste=function(){editor_event(objname,100);}
editdoc.body.onblur=function(){editor_event(objname,-1);}
if(mode!='init'){editor_focus(editor_obj);}}
if(mode!='init'){editor_event(objname);}}
function editor_focus(editor_obj){if(editor_obj.tagName.toLowerCase()=='textarea'){var myfunc=function(){editor_obj.focus();};setTimeout(myfunc,100);}
else{var editdoc=editor_obj.contentWindow.document;var editorRange=editdoc.body.createTextRange();var curRange=editdoc.selection.createRange();if(curRange.length==null&&!editorRange.inRange(curRange)){editorRange.collapse();editorRange.select();curRange=editorRange;}}}
function editor_about(objname){showModalDialog(_editor_url+"popups/about.html",window,"resizable: yes; help: no; status: no; scroll: no; ");}
function _dec_to_rgb(value){var hex_string="";for(var hexpair=0;hexpair<3;hexpair++){var myByte=value&0xFF;value>>=8;var nybble2=myByte&0x0F;var nybble1=(myByte>>4)&0x0F;hex_string+=nybble1.toString(16);hex_string+=nybble2.toString(16);}
return hex_string.toUpperCase();}
function editor_insertHTML(objname,str1,str2,reqSel){var config=document.all[objname].config;var editor_obj=document.all["_"+objname+"_editor"];if(str1==null){str1='';}
if(str2==null){str2='';}
if(document.all[objname]&&editor_obj==null){document.all[objname].focus();document.all[objname].value=document.all[objname].value+str1+str2;return;}
if(editor_obj==null){return alert("Unable to insert HTML.  Invalid object name '"+objname+"'.");}
editor_focus(editor_obj);var tagname=editor_obj.tagName.toLowerCase();var sRange;if(tagname=='iframe'){var editdoc=editor_obj.contentWindow.document;sRange=editdoc.selection.createRange();var sHtml=sRange.htmlText;if(sRange.length){return alert("Unable to insert HTML.  Try highlighting content instead of selecting it.");}
var oldHandler=window.onerror;window.onerror=function(){alert("Unable to insert HTML for current selection.");return true;}
if(sHtml.length){if(str2){sRange.pasteHTML(str1+sHtml+str2)}
else{sRange.pasteHTML(str1);}}else{if(reqSel){return alert("Unable to insert HTML.  You must select something first.");}
sRange.pasteHTML(str1+str2);}
window.onerror=oldHandler;}
else if(tagname=='textarea'){editor_obj.focus();sRange=document.selection.createRange();var sText=sRange.text;if(sText.length){if(str2){sRange.text=str1+sText+str2;}
else{sRange.text=str1;}}else{if(reqSel){return alert("Unable to insert HTML.  You must select something first.");}
sRange.text=str1+str2;}}
else{alert("Unable to insert HTML.  Unknown object tag type '"+tagname+"'.");}
sRange.collapse(false);sRange.select();}
function editor_getHTML(objname){var editor_obj=document.all["_"+objname+"_editor"];var isTextarea=(editor_obj.tagName.toLowerCase()=='textarea');if(isTextarea){return editor_obj.value;}
else{return editor_obj.contentWindow.document.body.innerHTML;}}
function editor_setHTML(objname,html){var editor_obj=document.all["_"+objname+"_editor"];var isTextarea=(editor_obj.tagName.toLowerCase()=='textarea');if(isTextarea){editor_obj.value=html;}
else{editor_obj.contentWindow.document.body.innerHTML=html;}}
function editor_appendHTML(objname,html){var editor_obj=document.all["_"+objname+"_editor"];var isTextarea=(editor_obj.tagName.toLowerCase()=='textarea');if(isTextarea){editor_obj.value+=html;}
else{editor_obj.contentWindow.document.body.innerHTML+=html;}}
function _isMouseOver(obj,event){var mouseX=event.clientX;var mouseY=event.clientY;var objTop=obj.offsetTop;var objBottom=obj.offsetTop+obj.offsetHeight;var objLeft=obj.offsetLeft;var objRight=obj.offsetLeft+obj.offsetWidth;if(mouseX>=objLeft&&mouseX<=objRight&&mouseY>=objTop&&mouseY<=objBottom){return true;}
return false;}
function editor_cMenu_generate(editorWin,objname){var parentWin=window;editorWin.event.returnValue=false;var cMenuOptions=[['Cut','Ctrl-X',function(){}],['Copy','Ctrl-C',function(){}],['Paste','Ctrl-C',function(){}],['Delete','DEL',function(){}],['---',null,null],['Select All','Ctrl-A',function(){}],['Clear All','',function(){}],['---',null,null],['About this editor...','',function(){alert("about this editor");}]];editor_cMenu.options=cMenuOptions;var cMenuHeader=''
+'<div id="_'+objname+'_cMenu" onblur="editor_cMenu(this);" oncontextmenu="return false;" onselectstart="return false"'
+'  style="position: absolute; visibility: hidden; cursor: default; width: 167px; background-color: threedface;'
+'         border: solid 1px; border-color: threedlightshadow threeddarkshadow threeddarkshadow threedlightshadow;">'
+'<table border=0 cellspacing=0 cellpadding=0 width="100%" style="width: 167px; background-color: threedface; border: solid 1px; border-color: threedhighlight threedshadow threedshadow threedhighlight;">'
+' <tr><td colspan=2 height=1></td></tr>';var cMenuList='';var cMenuFooter=''
+' <tr><td colspan=2 height=1></td></tr>'
+'</table></div>';for(var menuIdx in editor_cMenu.options){if(editor_cMenu.options.hasOwnProperty(menuIdx)){var menuName=editor_cMenu.options[menuIdx][0];var menuKey=editor_cMenu.options[menuIdx][1];var menuCode=editor_cMenu.options[menuIdx][2];if(menuName=="---"||menuName=="separator"){cMenuList+=' <tr><td colspan=2 class="cMenuDivOuter"><div class="cMenuDivInner"></div></td></tr>';}
else{cMenuList+='<tr class="cMenu" onMouseOver="editor_cMenu(this)" onMouseOut="editor_cMenu(this)" onClick="editor_cMenu(this, \''+menuIdx+'\',\''+objname+'\')">';if(menuKey){cMenuList+=' <td align=left class="cMenu">'+menuName+'</td><td align=right class="cMenu">'+menuKey+'</td>';}
else{cMenuList+=' <td colspan=2 class="cMenu">'+menuName+'</td>';}
cMenuList+='</tr>';}}}
var cMenuHTML=cMenuHeader+cMenuList+cMenuFooter;document.all['_'+objname+'_cMenu'].outerHTML=cMenuHTML;editor_cMenu_setPosition(parentWin,editorWin,objname);parentWin['_'+objname+'_cMenu'].style.visibility='visible';parentWin['_'+objname+'_cMenu'].focus();}
function editor_cMenu_setPosition(parentWin,editorWin,objname){var event=editorWin.event;var cMenuObj=parentWin['_'+objname+'_cMenu'];var mouseX=event.clientX+parentWin.document.all['_'+objname+'_editor'].offsetLeft;var mouseY=event.clientY+parentWin.document.all['_'+objname+'_editor'].offsetTop;var cMenuH=cMenuObj.offsetHeight;var cMenuW=cMenuObj.offsetWidth;var pageH=document.body.clientHeight+document.body.scrollTop;var pageW=document.body.clientWidth+document.body.scrollLeft;if(mouseX+5+cMenuW>pageW){var left=mouseX-cMenuW-5;}
else{var left=mouseX+5;}
if(mouseY+5+cMenuH>pageH){var top=mouseY-cMenuH+5;}
else{var top=mouseY+5;}
cMenuObj.style.top=top;cMenuObj.style.left=left;}
function editor_cMenu(obj,menuIdx,objname){var action=event.type;if(action=="mouseover"&&!obj.disabled&&obj.tagName.toLowerCase()=='tr'){obj.className='cMenuOver';for(var i=0;i<obj.cells.length;i++){obj.cells[i].className='cMenuOver';}}
else if(action=="mouseout"&&!obj.disabled&&obj.tagName.toLowerCase()=='tr'){obj.className='cMenu';for(var i=0;i<obj.cells.length;i++){obj.cells[i].className='cMenu';}}
else if(action=="click"&&!obj.disabled){document.all['_'+objname+'_cMenu'].style.visibility="hidden";var menucode=editor_cMenu.options[menuIdx][2];menucode();}
else if(action=="blur"){if(!_isMouseOver(obj,event)){obj.style.visibility='hidden';}
else{if(obj.style.visibility!="hidden"){obj.focus();}}}
else{alert("editor_cMenu, unknown action: "+action);}}