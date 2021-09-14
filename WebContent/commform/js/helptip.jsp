<%@page contentType="text/javascript; charset=GBK"language="java"%>function showHelpTip(e,sHtml,bHideSelects){var el=e.target||e.srcElement;while(el.tagName!="A")
el=el.parentNode;if(el._helpTip){helpTipHandler.hideHelpTip(el);}
helpTipHandler.hideSelects=Boolean(bHideSelects);helpTipHandler.createHelpTip(el,sHtml);helpTipHandler.positionToolTip(e);el.onblur=helpTipHandler.anchorBlur;el.onkeydown=helpTipHandler.anchorKeyDown;}
var helpTipHandler={hideSelects:false,helpTip:null,showSelects:function(bVisible){if(!this.hideSelects)return;var selects=[];if(document.all)
selects=document.all.tags("SELECT");var l=selects.length;for(var i=0;i<l;i++)
selects[i].runtimeStyle.visibility=bVisible?"":"hidden";},create:function(){var d=document.createElement("DIV");d.className="help-tooltip";d.onmousedown=this.helpTipMouseDown;d.onmouseup=this.helpTipMouseUp;document.body.appendChild(d);this.helpTip=d;},createHelpTip:function(el,sHtml){if(this.helpTip==null){this.create();}
var d=this.helpTip;d.innerHTML=sHtml;d._boundAnchor=el;el._helpTip=d;return d;},helpTipMouseDown:function(e){var d=this;var el=d._boundAnchor;if(!e)e=event;var t=e.target||e.srcElement;while(t.tagName!="A"&&t!=d)
t=t.parentNode;if(t==d)return;el._onblur=el.onblur;el.onblur=null;},helpTipMouseUp:function(){var d=this;var el=d._boundAnchor;el.onblur=el._onblur;el._onblur=null;el.focus();},anchorBlur:function(e){var el=this;helpTipHandler.hideHelpTip(el);},anchorKeyDown:function(e){if(!e)e=window.event
if(e.keyCode==27){helpTipHandler.hideHelpTip(this);}},removeHelpTip:function(d){d._boundAnchor=null;d.style.filter="none";d.innerHTML="";d.onmousedown=null;d.onmouseup=null;d.parentNode.removeChild(d);},hideHelpTip:function(el){var d=el._helpTip;d.style.visibility="hidden";el.onblur=null;el._onblur=null;el._helpTip=null;el.onkeydown=null;this.showSelects(true);},positionToolTip:function(e){this.showSelects(false);var scroll=this.getScroll();var d=this.helpTip;if(d.offsetWidth>=scroll.width)
d.style.width=scroll.width-10+"px";else
d.style.width="";if(e.clientX>scroll.width-d.offsetWidth)
d.style.left=scroll.width-d.offsetWidth+scroll.left+"px";else
d.style.left=e.clientX-2+scroll.left+"px";if(e.clientY+d.offsetHeight+18<scroll.height)
d.style.top=e.clientY+18+scroll.top+"px";else if(e.clientY-d.offsetHeight>0)
d.style.top=e.clientY+scroll.top-d.offsetHeight+"px";else
d.style.top=scroll.top+5+"px";d.style.visibility="visible";},getScroll:function(){if(document.all&&typeof document.body.scrollTop!="undefined"){var ieBox=document.compatMode!="CSS1Compat";var cont=ieBox?document.body:document.documentElement;return{left:cont.scrollLeft,top:cont.scrollTop,width:cont.clientWidth,height:cont.clientHeight};}
else{return{left:window.pageXOffset,top:window.pageYOffset,width:window.innerWidth,height:window.innerHeight};}}};