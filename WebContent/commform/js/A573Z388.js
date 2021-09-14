
if(!window.XartWebui_Keyboard_Loaded){window.qqPa=null;window.qqPc=new Array();window.qqPf=function(_1,_2,_3){this.Control=_1;this.qztk=_2;this.Handler=_3;};window.qqPd=function(_4,_5){for(var i=0;i<qqPc.length;i++){if(qqPc[i].Control==_4&&qqPc[i].qztk==_5){eval(qqPc[i].Handler);return true;}}
return false;};if(document.layers){document.captureEvents(Event.KEYPRESS);}
window.XartWebui_HandleKeyPress=function(e){XartWebui_ProcessKeyPress(e);};window.XartWebui_ProcessKeyPress=function(e){if(!qqPa||!qqPa.KeyboardEnabled){return true;}
if(document.activeElement&&!document.activeElement.XartWebuiInput&&(document.activeElement.nodeName=="INPUT"||document.activeElement.nodeName=="SELECT"||document.activeElement.nodeName=="TEXTAREA")){return true;}
var _9;if(cart_browser_ie){e=window.event;_9=e.keyCode;}else{_9=e.which;}
var _a=String.fromCharCode(_9);if(_9>111&&_9<123){_a="F"+(_9-111);}else{if(_9==13){_a="Enter";}else{if(_9==27){_a="Esc";}else{if(_9==29){_a="PgUp";}else{if(_9==30){_a="PgDn";}}}}}
var _b="";if(e.shiftKey){_b+="Shift+";}
if(e.ctrlKey){_b+="Ctrl+";}
if(e.altKey){_b+="Alt+";}
_b+=_a;if(!qqPd(qqPa,_b)){return true;}
if(cart_browser_ie){e.cancelBubble=true;e.returnValue=false;}else{e.preventDefault();e.stopPropagation();}
return false;};window.XartWebui_RegisterKeyHandler=function(_c,_d,_e){qqPc[qqPc.length]=new qqPf(_c,_d,_e);};window.XartWebui_Keyboard_Loaded=true;}