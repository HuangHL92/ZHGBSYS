
function XartWebui_Ticker(){this.GlobalID="";this.ElementID="";this.CharDelay=0;this.LineDelay=0;this.Lines=new Array();this.NextTickerDelay=0;this.TickerType="default";this.qzAaw=0;this.qzzw=0;this.qzAfy=0;this.OnEnd=function(){qzAgz();};}
XartWebui_Ticker.prototype.GetProperty=function(propName){return this[propName];};XartWebui_Ticker.prototype.SetProperty=function(propName,propValue){this[propName]=propValue;};function qzwe(qzcn){if(qzcn.TickerType=="statusbar"){return window.status;}else{return qzAga(document.getElementById(qzcn.ElementID).innerHTML);}}
function qzwd(qzcn,value){if(qzcn.TickerType=="statusbar"){window.status=value;}else{document.getElementById(qzcn.ElementID).innerHTML=qzAgb(value);}}
function qzAgb(value){if(navigator.userAgent.indexOf("MSIE")!=-1||navigator.userAgent.indexOf("Opera")!=-1){return value.replace("&","&amp;");}else{return value.replace("&"," ");}}
function qzAga(value){if(navigator.userAgent.indexOf("MSIE")!=-1||navigator.userAgent.indexOf("Opera")!=-1){return value.replace("&amp;","&");}else{return value;}}
function rcr_StartTicker(qzcn){qzwd(qzcn,"");var qzbn="rcr_PrintNextChar("+qzcn.GlobalID+")";qzcn.qzAaw=window.setInterval(qzbn,qzcn.CharDelay);}
function qzAfz(qzcn){window.clearInterval(qzcn.qzAaw);}
function rcr_PrintNextChar(qzcn){if(qzwe(qzcn).length==qzcn.Lines[qzcn.qzzw].length){qzAig(qzcn);}else{qzwd(qzcn,qzwe(qzcn)+qzcn.Lines[qzcn.qzzw].charAt(qzcn.qzAfy));qzcn.qzAfy++;}}
function qzAig(qzcn){qzcn.qzAfy=0;window.clearInterval(qzcn.qzAaw);if(qzcn.qzzw==qzcn.Lines.length-1){if(qzcn.Loop){qzcn.qzzw=0;}else{qzAfz(qzcn);var qzbn="rcr_OnEnd("+qzcn.GlobalID+")",qzAol=window.setTimeout(qzbn,qzcn.NextTickerDelay);return null;}}else{qzcn.qzzw++;}
var qzbn="rcr_StartTicker("+qzcn.GlobalID+")",qzAnx=window.setTimeout(qzbn,qzcn.LineDelay);}
function rcr_OnEnd(qzcn){qzcn.OnEnd();}
function qzAgz(){}