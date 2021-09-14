
function fixDialog(p_bEnableContextMenu){try{if(dialogArguments==undefined){return}}catch(e){return}
p_bEnableContextMenu=(p_bEnableContextMenu==true);if(window.name==''){window.name="winDialog";}
goToURL=function(p_strURL){var oLink=document.createElement("A");document.body.insertAdjacentElement('beforeEnd',oLink);with(oLink){href=p_strURL;target=window.name;click();}}
window.navigate=goToURL;window.location.assign=goToURL;window.location.reload=function(){window.navigate(window.location.href)}
window.location.replace=goToURL;var bWasPassedWindow=false;try{bWasPassedWindow=dialogArguments.location!=undefined;}catch(e){}
if(bWasPassedWindow){window.opener=dialogArguments;}
attachEvent("onload",function(){var oHead=document.getElementsByTagName("HEAD")[0];var oBase=document.createElement("BASE");oBase.target=window.name;oHead.insertAdjacentElement('AfterBegin',oBase);var colUtags=document.all.tags("U");for(var u=0;u<colUtags.length;u++){if(colUtags[u].style.accelerator){colUtags[u].style.textDecoration='none';}}});document.attachEvent("onkeydown",function(){switch(event.keyCode){case 27:{close();break;}
case 18:{if(event.repeat){break}
var colUtags=document.all.tags("U");for(var u=0;u<colUtags.length;u++){if(colUtags[u].style.accelerator){colUtags[u].style.textDecoration=(colUtags[u].style.textDecoration!='none')?'none':'underline';}}
setTimeout("document.body.focus();",200);event.cancelBubble=true;break;}
case 116:{window.location.reload();break;}
case 82:{if(event.ctrlKey){window.location.reload();}
break;}
case 8:{history.back();break;}
case 37:{if(event.altKey){history.back();}
break;}
case 39:{if(event.altKey){history.forward();}
break;}}});document.attachEvent("oncontextmenu",function(){if(p_bEnableContextMenu){var strMenuHTML='<html><body>'+'<st'+'yle type="text/css">\r\n'+'BODY {background:buttonface;  padding:2px; font:x-small Tahoma, sans-serif; font-size:80%;}\r\n'+'A {padding:0 4px 0 4px; text-decoration:none; color:buttontext; WIDTH:115%; display:block; cursor:default;}\r\n'+'A:hover {background:highlight; color:highlighttext;}\r\n'+'</st'+'yle>\r\n'+'<scr'+'ipt language="Javascript">\r\n'+'function reload(){parent.location=parent.location.href}\r\n'+'</scr'+'ipt>\r\n'+'<a href="#" onclick="parent.window.opener.external.AddFavorite(parent.document.location.href, parent.document.title)">Add To Favorites</a>\r\n'+'<a href="#" onclick="parent.location=\'view-source:\'+parent.document.location.href">View Source</a>\r\n'+'<a href="#" onclick="parent.print()">Print</a>\r\n'+'<a href="#" onclick="parent.location.reload()">Refresh</a>\r\n'+'</body></html>'+'';var oPop=window.createPopup();var oPopBody=oPop.document.body;oPopBody.style.cssText='border:2px threedhighlight outset;';oPopBody.innerHTML=strMenuHTML;oPop.show(event.clientX,event.clientY,130,75,document.body);return false;}})}