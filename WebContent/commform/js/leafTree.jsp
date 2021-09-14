<%@page contentType="text/javascript; charset=GBK"language="java"%>var _VersionInfo="Version:1.0&#13;2.0"
var bMoveable=true;var regionTree=false;var regionnewTree=false;var workTypeTree=false;var sysfTree=false;var calendar=false;document.writeln('<iframe id=worktypeTreeLayer src=""  Author=zhangsk frameborder=0 style="position: absolute; width: 280; height: 220; z-index: 9998; display: none"></iframe>');var workType_outObject1;var workType_outObject2;document.writeln('<iframe id=regionTreeLayer src=""  Author=zhangsk frameborder=0 style="position: absolute; width: 280; height: 400; z-index: 9998; display: none"></iframe>');var region_outObject1;var region_outObject2;document.writeln('<iframe id=regionnewTreeLayer src=""  Author=zhangsk frameborder=0 style="position: absolute; width: 280; height: 400; z-index: 9998; display: none"></iframe>');var regionnew_outObject1;var regionnew_outObject2;document.writeln('<iframe id=sysfTreeLayer src=""  Author=zhangsk frameborder=0 style="position: absolute; width: 280; height: 400; z-index: 9998; display: none"></iframe>');var sysf_outObject1;var sysf_outObject2;function setWorkTypeTree(tt,disObjName,valObjName,condition)
{if(arguments.length>4){alert("对不起！传入本控件的参数太多！");return;}
if(arguments.length==0){alert("对不起！您没有传回本控件任何参数！");return;}
if(""!=condition&&undefined!=condition){condition="?condition="+condition;}else{condition="";}
var srcFrame=document.all.worktypeTreeLayer.src;if(""==srcFrame){document.all.worktypeTreeLayer.src="<%=request.getContextPath()%>/common/workType.jsp"+condition;}
var dads=document.all.worktypeTreeLayer.style;var th=tt;var ttop=tt.offsetTop;var thei=tt.clientHeight;var tleft=tt.offsetLeft;var ttyp=tt.type;while(tt=tt.offsetParent){ttop+=tt.offsetTop;tleft+=tt.offsetLeft;}
dads.top=(ttyp=="image")?ttop+thei:ttop+thei+6;dads.left=tleft;dads.display='';workType_outObject1=arguments[1];workType_outObject2=arguments[2];workTypeTree=true;regionTree=false;regionnewTree=false;calendar=false;}
function workerType_setValue(dispaly_value,code_value){parent.workType_outObject1.value=dispaly_value;parent.workType_outObject2.value=code_value;parent.workTypeTree_close()}
function workerType_clearvalue_tree(){parent.workType_outObject1.value="";parent.workType_outObject2.value="";parent.workTypeTree_close()}
function close_WorkType_Frame(){parent.workTypeTree_close()}
function workTypeTree_close(){regionTree=false;regionnewTree=false;workTypeTree=false;calendar=false;document.all.worktypeTreeLayer.style.display="none";}
function clearValue(obj1,obj2){obj1.value="";obj2.value="";}
function document.onclick(){with(window.event)
{if(workTypeTree){if(srcElement.getAttribute("Author")!="zhansk"&&srcElement!=workType_outObject1){regionTree=false;regionnewTree=false;workTypeTree=false;calendar=false;workTypeTree_close();}}
if(regionTree){if(srcElement.getAttribute("Author")!="zhansk"&&srcElement!=region_outObject1){regionTree=false;workTypeTree=false;calendar=false;regionTree_close();}}
if(regionnewTree){if(srcElement.getAttribute("Author")!="zhansk"&&srcElement!=regionnew_outObject1){regionnewTree=false;regionTree=false;workTypeTree=false;calendar=false;regionnewTree_close();}}
if(sysfTree){if(srcElement.getAttribute("Author")!="zhansk"&&srcElement!=sysf_outObject1){sysfTree=false;regionTree=false;regionnewTree=false;workTypeTree=false;calendar=false;sysfTree_close();}}
if(calendar){if(srcElement.getAttribute("Author")!="wayx"&&srcElement!=outObject&&srcElement!=outButton)
{regionTree=false;regionnewTree=false;workTypeTree=false;calendar=false;closeLayer();}}}}
function setRegionTree(tt,disObjName,valObjName,condition)
{if(arguments.length>4){alert("对不起！传入本控件的参数太多！");return;}
if(arguments.length==0){alert("对不起！您没有传回本控件任何参数！");return;}
var srcFrame=document.all.regionTreeLayer.src;if(""!=condition&&undefined!=condition){condition="?condition="+condition;}else{condition="";}
if(""==srcFrame){document.all.regionTreeLayer.src="<%=request.getContextPath()%>/common/group.jsp"+condition;}
var dads=document.all.regionTreeLayer.style;var th=tt;var ttop=tt.offsetTop;var thei=tt.clientHeight;var tleft=tt.offsetLeft;var ttyp=tt.type;while(tt=tt.offsetParent){ttop+=tt.offsetTop;tleft+=tt.offsetLeft;}
dads.top=(ttyp=="image")?ttop+thei:ttop+thei+6;dads.left=tleft;dads.display='';region_outObject1=arguments[1];region_outObject2=arguments[2];regionTree=true;calendar=false;}
function region_setValue(dispaly_value,code_value){parent.region_outObject1.value=dispaly_value;parent.region_outObject2.value=code_value;parent.regionTree_close()}
function region_clearvalue_tree(){parent.region_outObject1.value="";parent.region_outObject2.value="";parent.regionTree_close()}
function close_region_Frame(){parent.regionTree_close()}
function regionTree_close(){regionTree=false;workTypeTree=false;calendar=false;document.all.regionTreeLayer.style.display="none";}
function setRegionnewTree(tt,disObjName,valObjName,condition)
{if(arguments.length>4){alert("对不起！传入本控件的参数太多！");return;}
if(arguments.length==0){alert("对不起！您没有传回本控件任何参数！");return;}
var srcFrame=document.all.regionnewTreeLayer.src;if(""!=condition&&undefined!=condition){condition="?condition="+condition;}else{condition="";}
if(""==srcFrame){document.all.regionnewTreeLayer.src="<%=request.getContextPath()%>/common/region.jsp"+condition;}
var dads=document.all.regionnewTreeLayer.style;var th=tt;var ttop=tt.offsetTop;var thei=tt.clientHeight;var tleft=tt.offsetLeft;var ttyp=tt.type;while(tt=tt.offsetParent){ttop+=tt.offsetTop;tleft+=tt.offsetLeft;}
dads.top=(ttyp=="image")?ttop+thei:ttop+thei+6;dads.left=tleft;dads.display='';regionnew_outObject1=arguments[1];regionnew_outObject2=arguments[2];regionnewTree=true;regionTree=false;calendar=false;}
function regionnew_setValue(dispaly_value,code_value){parent.regionnew_outObject1.value=dispaly_value;parent.regionnew_outObject2.value=code_value;parent.regionnewTree_close()}
function regionnew_clearvalue_tree(){parent.regionnew_outObject1.value="";parent.regionnew_outObject2.value="";parent.regionnewTree_close()}
function close_regionnew_Frame(){parent.regionnewTree_close()}
function regionnewTree_close(){regionnewTree=false;regionTree=false;workTypeTree=false;calendar=false;document.all.regionnewTreeLayer.style.display="none";}
function setSysfTree(tt,disObjName,valObjName,condition)
{if(arguments.length>4){alert("对不起！传入本控件的参数太多！");return;}
if(arguments.length==0){alert("对不起！您没有传回本控件任何参数！");return;}
var srcFrame=document.all.sysfTreeLayer.src;if(""!=condition&&undefined!=condition){condition="?condition="+condition;}else{condition="";}
if(""==srcFrame){document.all.sysfTreeLayer.src="<%=request.getContextPath()%>/common/region.jsp"+condition;}
var dads=document.all.sysfTreeLayer.style;var th=tt;var ttop=tt.offsetTop;var thei=tt.clientHeight;var tleft=tt.offsetLeft;var ttyp=tt.type;while(tt=tt.offsetParent){ttop+=tt.offsetTop;tleft+=tt.offsetLeft;}
dads.top=(ttyp=="image")?ttop+thei:ttop+thei+6;dads.left=tleft;dads.display='';sysf_outObject1=arguments[1];sysf_outObject2=arguments[2];sysfTree=true;calendar=false;}
function sysf_setValue(dispaly_value,code_value){parent.sysf_outObject1.value=dispaly_value;parent.sysf_outObject2.value=code_value;parent.sysfTree_close()}
function sysf_clearvalue_tree(){parent.sysf_outObject1.value="";parent.sysf_outObject2.value="";parent.sysfTree_close()}
function close_sysf_Frame(){parent.sysfTree_close()}
function sysfTree_close(){sysfTree=false;workTypeTree=false;calendar=false;document.all.sysfTreeLayer.style.display="none";}
function editfunction(dispaly_value,code_value){var right=parent.document.all("right");right.src="functionAction.do?method=findByKey&functionid="+code_value;}