<%@page contentType="text/javascript; charset=GBK"language="java"%>function SelectObj(editerObj){this.edtObj=editerObj;this.getParentObj=SEL_getParentObj;this.getBaseObj=SEL_getBaseObj;this.onvalidate=SEL_onvalidate;this.onReady=SEL_onDocumentReady;this.eventBand=SEL_eventBand;var ParObj=null;var BasObj=null;}
function SEL_getParentObj(){if(this.ParObj==null){this.ParObj=new BaseObj(this.edtObj);}
return this.ParObj;}
function SEL_getBaseObj(){if(this.BasObj==null){this.BasObj=new BaseObj(this.edtObj);}
return this.BasObj;}
function SEL_onvalidate(){if(!this.getBaseObj().commonCheck())return false;return true;}
function SEL_onDocumentReady(){try{if(this.edtObj.options(0).text!="«Î—°‘Ò...")
this.edtObj.add(new Option("«Î—°‘Ò...",""),0);}catch(e){}
var isNoSelected=this.edtObj.getAttribute("isNoSelected");if(isNoSelected!=null&&isNoSelected.toUpperCase()=="TRUE"){this.edtObj.selectedIndex=0;return;}
var flag=false;var isNotInDW=this.edtObj.getAttribute("isNotInDW");isNotInDW=isNotInDW==null?"false":isNotInDW.toLowerCase();for(var i=1;i<this.edtObj.options.length;i++){if(this.edtObj.options(i).isSelected!=null&&this.edtObj.options(i).isSelected.toUpperCase()=="TRUE"){this.edtObj.selectedIndex=i;flag=true;break;}
if(isNotInDW!="true")
if(this.edtObj.options(i).selected)flag=true;}
if(!flag)this.edtObj.selectedIndex=0;this.getParentObj().onReady()}
function SEL_eventBand(){}