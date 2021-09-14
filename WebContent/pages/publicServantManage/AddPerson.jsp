<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%
String ctxPath = request.getContextPath(); 
String a0000 = request.getParameter("a0000");
%>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.FontConfigPageModel"%>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.InfoPageModel"%>
<%@page import="com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_003.InfoComWindowPageModel"%>
<style>
<%=FontConfigPageModel.getFontConfig()%>
.vfontConfig{
color: red;
}
	.width-200{
		width: 218px !important;
	}
	.x-panel-body{
		
	}
	.btn3 {
	    BORDER-RIGHT: #7b9ebd 1px solid; 
	    PADDING-RIGHT: 2px; 
	    BORDER-TOP: #7b9ebd 1px solid; 
	    PADDING-LEFT: 2px; 
	    FONT-SIZE: 12px; 
	    FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0, StartColorStr=#ffffff, EndColorStr=#cecfde); 
	    BORDER-LEFT: #7b9ebd 1px solid; 
	    CURSOR: hand; COLOR: black; 
	    PADDING-TOP: 2px; 
	    BORDER-BOTTOM: #7b9ebd 1px solid;
	}
	
	.x-form-item{
		width: 100%;
		height: 100%;
		margin: 0px 0px 0px 0px;
		padding: 0px 0px 0px 0px;
	}
	.v_standard tr td{
		border-left: 2px solid #477aaf;
		border-top: 2px solid #477aaf;
		font-size: 16px;
		margin: 0px;
		padding:0px;
		font-family: '宋体';
		text-align: center;
		overflow: hidden;
		
		}
	.v_standard tr td textarea{
		font-family: '宋体', Simsun;
		font-size:15px;
		font-style: normal;
		font-variant:normal;
		font-weight:normal;
		text-align: center;
		border: 0px;
		margin: 0px;
		padding: 0px;
	}
	.v_standard tr td input{
		font-family: '宋体';
		font-size:14px;
		font-style: normal;
		font-variant:normal;
		font-weight:normal;
		border: 0px;
		margin: 0px!important;
		padding: 0px!important;
	}
	.top-last{
	border-bottom: 2px solid #477aaf;
	}
	.left-last{
	border-right: 2px solid #477aaf;
	}
	.v_standard{
	
	}
	.label-clor{
	background-color: #d4e9fc;
	}
	.width13-60{
	width: 60px !important;
	}
	.width24-80{
	width: 80px !important;
	}
	.width56-75{
	width: 75px !important;
	}
	.width56-78{
	width: 60px !important;
	}
	.width5T6-150{
	width: 157px !important;
	}
	.width2T3-140{
	width: 157px !important;
	}
	.width2T3T4-220{
	width: 220px !important;
	}
	.width3T4-140{
	width: 157px !important;
	}
	.width6T7-195{
	width: 218px !important;
	}
	.width7-120{
	width: 136px !important;
	}
	.width3_7-410{
	width: 454px !important;
	
	}
	.width2_7-410{
	width: 536px !important;
	
	}
	.font-left{
	text-align: left !important;
	}
	.height1234-40{
	height: 40px !important;
	}
	.heightNew-40{
	height: 40px !important;
	}
	.height5678-80{
	height: 96px !important;
	}
	.height5i6i7i8-20{
	height: 24px !important;
	}
	.height9-35{
	height: 35px !important;
	}
	.height10-359{
	height: 365px !important;
	}
	.width-478{
	width: 536px !important;
	}
	.height10-445{
	height: 465px !important;
	}
	.input-text{
		text-align: center;
		line-height: 36px !important;
		border: 0px;
		
	}
	.input-text_left{
		text-align: left;
		line-height: 36px !important;
		border: 0px;
		
	}
	.input-text2{
		text-align: center;
		line-height: 24px !important;
		border: 0px;
		
	}
	.no-y-scroll{
		overflow-y:hidden !important;
	}
	.fontdisplay{
		padding-top: 0px !important;
		text-align:center;
		line-height: 40px !important;
	}
	
	div .TAwrap {   
	  display:table;   
	  _position:relative;   
	  overflow:hidden;   
	}   
	div .TAsubwrap {   
		
	  vertical-align:middle;   
	  display:table-cell;   
	  _position:absolute;   
	  z-index:-100;
	  _top:47%;  
	  
	}   
	div .TAcontent {   
		display: inline-block;
		_display: block!important;
		font-family: '宋体';
		font-size:14px;
		font-style: normal;
		font-variant:normal;
		font-weight:normal;
		line-height:142%;
	  _position:relative;   
	  _top:-47%;  
	  _left:  -50%; 
	  text-align: left;
	}
.v_standard_left tr td{
		border:0px;
		text-align: left;
		}
		
.cellbgclor{
	background-color: rgb(242,247,253)!important;
	background-image: none!important;
}
.x-item-disabled INPUT,{
	background-color: white !important;
}
.x-item-disabled,.x-form-field-wrap x-form-field-trigger-wrap{
	background-color: rgb(242,247,253) !important;
}
#ext-gen34,#ext-gen35,#ext-gen42,#ext-gen11,#ext-gen36 {
	background-color: rgb(242,247,253) !important;
}
div .a0140Class {   
		display: inline-block;
		_display: block!important;
		font-family: '宋体';
		font-size:14px;
		font-style: normal;
		font-variant:normal;
		font-weight:normal;
		line-height:142%;
	  _position:relative;   
	  _top:-47%;  
	  _left:  -50%; 
	  text-align: center;
	}
.frot{
	color: A6A6A6;
}


</style>
<script type="text/javascript" src="<%=ctxPath%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=ctxPath%>/basejs/helperUtil.js"></script>

<script type="text/javascript">
var ctxPath = '<%=ctxPath%>';
function getPdfPath(){
	var pdfPath = document.getElementById('pdfPath').value;
	return pdfPath;
}

//查看权限变量
var buttonDisabled = false;
var A01value={},A29value={},A71value={},A53value={} ,A37value={},A31value={},A30value={},A36value={};
var spFeild = $h.spFeildAll.a01.concat($h.spFeildAll.a29).concat($h.spFeildAll.a11).concat($h.spFeildAll.a31).concat($h.spFeildAll.a30).concat($h.spFeildAll.a53).concat($h.spFeildAll.a37);
function trim(s) { return s.replace(/^\s+|\s+$/g, ""); };


//验证身份证号并获取出生日期
function getBirthdatByIdNo(iIdNo) {
	var tmpStr = "";
	var idDate = "";
	var tmpInt = 0;
	var strReturn = "";
	
	iIdNo = trim(iIdNo);
	if (iIdNo.length == 15) {
		tmpStr = iIdNo.substring(6, 12);
		tmpStr = "19" + tmpStr;
		tmpStr = tmpStr.substring(0, 4) + tmpStr.substring(4, 6) + tmpStr.substring(6)
		
		return tmpStr;
	} else {
		tmpStr = iIdNo.substring(6, 14);
		tmpStr = tmpStr.substring(0, 4) + tmpStr.substring(4, 6) + tmpStr.substring(6)
		return tmpStr;
	}
}
function savePersonSub(a,b,confirm,isIdcard){
	var a0101 = document.getElementById('a0101').value.replace(/\s/g, "");//姓名
	var a0184 = document.getElementById('a0184').value;//身份证号
	var a0107 = document.getElementById('a0107').value;//出生年月
	var a0104 = document.getElementById('a0104').value;//性别
	var a0111_combo = document.getElementById('comboxArea_a0111').value;//籍贯
	var a0114 = document.getElementById('comboxArea_a0114').value.replace(/\s/g, "");//出生地
	
	//var a0114_combo = document.getElementById('comboxArea_a0114').value;//出生地
	
	
	var a0117 = document.getElementById('a0117').value;//民族
	var a0128 = document.getElementById('a0128').value;//健康状况
	var a0134 = document.getElementById('a0134').value;//参加工作时间
	var a0160 = document.getElementById('a0160').value;//人员类别
	var a0165 = document.getElementById('a0165').value;//管理类别
	var a1701 = document.getElementById('a1701').value;//简历
	var a0121 = document.getElementById('a0121').value;//编制类型
	if(a0101==''){
		$h.alert('系统提示：','姓名不能为空！',null,220);
		return false;
	}
/* 	if (a0101.indexOf(" ") >=0){
		$h.alert('系统提示',"姓名不能包含空格！",null,220);
		return false;
	} */
    /* if(!(/^[\u3220-\uFA29]+$/.test(a0101))){
    	$h.alert('系统提示',"姓名不能包含非汉字字符！",null,220);
		return false;
    } */
	if(a0101.length>18){
		$h.alert('系统提示：','姓名字数不能超过18！',null,220); 
		return false;
	}
	if(a0104==''){
		$h.alert('系统提示：','性别不能为空！',null,220); 
		return false;
	}
	if(a0107==''){
		$h.alert('系统提示：','出生年月不能为空！',null,220); 
		return false;
	}
	if(a0117==''){
		$h.alert('系统提示：','民族不能为空！',null,220); 
		return false;
	}
	if(a0111_combo==''){
		$h.alert('系统提示：','籍贯不能为空！',null,220); 
		return false;
	}
	if(a0114==''){
		$h.alert('系统提示：','出生地不能为空！',null,220); 
		return false;
	}
	
	if(a0128==''){
		$h.alert('系统提示：','健康状况不能为空！',null,220); 
		return false;
	}
	if(a0134==''){
		$h.alert('系统提示：','参加工作时间不能为空！',null,220); 
		return false;
	}
	if(a1701==''){
		$h.alert('系统提示：','简历不能为空！',null,220); 
		return false;
	}
	if(a0165==''){
		$h.alert('系统提示：','管理类别不能为空！',null,220); 
		return false;
	}
	if(a0160==''){
		$h.alert('系统提示：','人员类别不能为空！',null,220); 
		return false;
	}
	if(a0121==''){
		$h.alert('系统提示：','编制类型不能为空！',null,220); 
		return false;
	}
	//出生日期格式
	var datetext = $h.date(a0107);
	if(datetext!==true){
		$h.alert('系统提示：','出生年月：'+datetext,null,320);
		return false;
	}
	
	
	//其他信息页面校验
	if(document.getElementById('Iorthers')){
		orthersWindow = document.getElementById('Iorthers').contentWindow;
		if(orthersWindow){
			if (orthersWindow.formcheck() == true) {//全局校验
			}else{
				var tabs = Ext.getCmp('rmbTabs');
				tabs.activate(tabs.getItem('orthers'));
				return false;
			}
		}
	}
	//业务信息页面校验
	if(document.getElementById('IBusinessInfo')){
		orthersWindow = document.getElementById('IBusinessInfo').contentWindow;
		if(orthersWindow){
			if (orthersWindow.formcheck() == true) {//全局校验
			}else{
				var tabs = Ext.getCmp('rmbTabs');
				tabs.activate(tabs.getItem('IBusinessInfo'));
				return false;
			}
		}
	}
	
	var birthdaya0184 = getBirthdatByIdNo(a0184);
	var birthdaya0107 = Ext.getCmp('a0107').getValue();//出身年月
	var msg = '出生日期与身份证不一致，<br/>是否继续保存？';
	if(isIdcard&&(birthdaya0107==''||(birthdaya0107!=birthdaya0184&&birthdaya0107!=birthdaya0184.substring(0, 6)))){
		$h.confirm("系统提示：",msg,200,function(id) { 
			if("ok"==id){
				radow.doEvent('save.onclick',confirm);
			}else{
				return false;
			}		
		});	
		return false;
	}else{
		radow.doEvent('save.onclick',confirm);
		//其他信息
		
	}
}

function exportLrmBtnNrm(){
	var a0184 = document.getElementById('a0184').value;//身份证号
	var vtext = $h.IDCard(a0184);
	if(vtext!==true){
		//$h.alert('系统提示：',vtext,null,320);
		$h.confirm("系统提示：",vtext+'，<br/>是否继续打印？',400,function(id) { 
			if("ok"==id){
				//Ext.getCmp('a0184').clearInvalid();
				radow.doEvent('exportLrmBtnNrm.onclick');
			}else{
				return false;
			}		
		});
	}else{
		radow.doEvent('exportLrmBtnNrm.onclick');
	}
}

function exportLrmBtn(){
	var a0184 = document.getElementById('a0184').value;//身份证号
	var vtext = $h.IDCard(a0184);
	if(vtext!==true){
		//$h.alert('系统提示：',vtext,null,320);
		$h.confirm("系统提示：",vtext+'，<br/>是否继续打印？',400,function(id) { 
			if("ok"==id){
				//Ext.getCmp('a0184').clearInvalid();
				radow.doEvent('exportLrmBtn.onclick');
			}else{
				return false;
			}		
		});
	}else{
		radow.doEvent('exportLrmBtn.onclick');
	}
}

function savePerson(a,b,confirm){
	var a0101 = document.getElementById('a0101').value;//姓名
	var a0184 = document.getElementById('a0184').value;//身份证号
	var a0107 = document.getElementById('a0107').value;//出生年月
	var orthersWindow = null;
	//校验身份证是否合法
	if(a0184==''){
		$h.alert('系统提示：','身份证号不能为空!',null,220);
		return false;
	}
	if(a0184.length>18){
		$h.alert('系统提示：','身份证号不能超过18位!',null,220);
		return false;
	}
	
	
	var vtext = $h.IDCard(a0184);
	if(vtext!==true){
		//$h.alert('系统提示：',vtext,null,320);
		$h.confirm("系统提示：",vtext+'，<br/>是否继续保存？',400,function(id) { 
			if("ok"==id){
				Ext.getCmp('a0184').clearInvalid();
				savePersonSub(a,b,confirm,false);
			}else{
				return false;
			}		
		});
	}else{
		savePersonSub(a,b,confirm,true);
	}
}
///任免表展示方法
function printAppointment(){
	var a0000 = document.getElementById('a0000').value;
	if(a0000){
		radow.doEvent("printFalse",a0000);
	}
	//$h.openWin('addOrgWin6','pages.publicServantManage.Appointment','任免表展示',800,900,document.getElementById('a0000').value,ctxPath);
}

//信息集录入保存 
function InfoSave(){
	var a0000 = document.getElementById('a0000').value;
	var orthersWindow = document.getElementById('IInfo').contentWindow;

	orthersWindow.radow.doEvent('InfoSave.onclick','1');

	//radow.doEvent('InfoSave.onclick');
	//window.location.href="radowAction.do?method=doEvent&pageModel=pages.publicServantManage.Info&eventNames=infoSave&a0000="+a0000;
}



function link2tab(id){
	window.parent.tabs.activate(id);	
}

/**删除选中行  单条**/
function delRow(obj){
	var grid = odin.ext.getCmp(obj.initialConfig.cls);
	var sm = grid.getSelectionModel();
	var selections = sm.getSelections();
	var store = grid.store;
	for(var i=0;i<selections.length;i++){
		var selected = selections[i];
		store.remove(selected);				
	}
	grid.view.refresh();			
}
/**添加行**/
function addRow(obj){
	radow.addGridEmptyRow(obj.initialConfig.cls,0);
}
/**删除选中行  多条**/
function delCheckedRow(obj){
	var grid = odin.ext.getCmp(obj.initialConfig.cls);
	var arrayObj = new Array();;
	var store = grid.store;
	var i=store.getCount()-1;
	if(store.getCount() > 0){
		for( var i = store.getCount()-1 ;i>=0; i-- ){
			var ck = grid.getStore().getAt(i).get("logchecked");
	        if(ck == true){
	        	store.remove(grid.getStore().getAt(i));
			}
		}
	}
}

function all(){
	alert(document.getElementById('a0144').value);
}
function seeNext(){
	var a2970 = document.getElementById('a2970');
	var str = a2970.value;
	if(str=='03'){
		document.getElementById('xts').style.display = 'none';
		document.getElementById('xts2').style.display = 'none';
	}else{
		document.getElementById('xts').style.display = 'block';
		document.getElementById('xts2').style.display = 'block';
	}
}
//人员类别 专业技术类公务员任职资格联动
function selectchange(record,index){
	if(document.getElementById('Iorthers')){
		var orthersWindow = document.getElementById('Iorthers').contentWindow;
		if(orthersWindow){
			orthersWindow.onA0160Change(record,index);
		}
	}
}

function showdialog(){
	var isUpdate = document.getElementById('isUpdate').value;
	if(isUpdate == 2){
		return;
	}
	var newwin = Ext.getCmp('picupload');
	newwin.show();
	var iframe = document.getElementById('iframe_picupload');
	iframe.src=iframe.src;
}

function namevalidator(value){
	if(value.length>18){
		return '姓名字数不能超过18';
	}
	var a0101obj = document.getElementById('a0101');
	//去除前面的点
	while(true){
		var firstStr = value.substring(0,1);
		if(firstStr==='.'||firstStr==='。'||firstStr==='·'){
			a0101obj.value=value=value.substring(1,value.length);
		}else{
			break;
		}
	}
	//将。.转为·
	if(value.match(/\.|。|\r\n/g)){
		a0101obj.value = value.replace(/\.|。/g,"·").replace(/\r\n/g,"");
	}
	return true;
}
function namevalidator2(obj){
	var value = obj.value;
	if(value.length>18){
		return '姓名字数不能超过18';
	}
	var a0101obj = obj;
	//去除前面的点
	while(true){
		var firstStr = value.substring(0,1);
		if(firstStr==='.'||firstStr==='。'||firstStr==='·'){
			a0101obj.value=value=value.substring(1,value.length);
		}else{
			break;
		}
	}
	//将。.转为·
	if(value.match(/\.|。|\r\n/g)){
		a0101obj.value = value.replace(/\.|。/g,"·").replace(/\r\n/g,"");
	}
	return true;
}

function nameCheck(obj){
	radow.doEvent('nameCheck',obj.value);
}


</script>

<div id="floatToolDiv" style="position: absolute;top: 0px;width:100%;z-index:1"></div>

<div id="divrmb1">

	<br/><br/>
	<!-----------------------------人员基本信息------------------------------------------------------->
	<odin:hidden property="a0192" title="工作单位职务简称"/>
	<odin:hidden property="a0000" title="主键a0000" ></odin:hidden>
	<odin:hidden property="age" title="年龄" ></odin:hidden>
	<odin:hidden property="a0148" title="职务层级" ></odin:hidden>
	<odin:hidden property="a0163" title="人员状态" ></odin:hidden>
	<odin:hidden property="status" title="删除状态" ></odin:hidden>
	<!-- <odin:hidden property="a0195" title="统计关系所在单位" ></odin:hidden> -->
	<odin:hidden property="a0194" title="基层工作经历年限" ></odin:hidden>
	<odin:hidden property="tbr" title="填报人" ></odin:hidden>
	<odin:hidden property="tbsj" title="填报时间" ></odin:hidden>
	<odin:hidden property="a0197" title="基层工作年限2年以上" ></odin:hidden>
	<odin:hidden property="a0155" title="公务员登记时间" ></odin:hidden>
	<!-- 1：有修改权限，2：无修改权限 -->
	<odin:hidden property="isUpdate" title="是否有修改权限" ></odin:hidden>
	
	<odin:hidden property="tbrjg" title="填报机构" ></odin:hidden>
	<odin:hidden property="cbdw" title="呈报单位" ></odin:hidden>
	<odin:hidden property="a0199" title="是否为正在增加的人员" ></odin:hidden>
	<odin:hidden property="a0141d" title="记录本人三个政治面貌" ></odin:hidden>
	<odin:hidden property="a0191" title="与列表关联" ></odin:hidden>
	<odin:hidden property="a015a" title="职务拼接方式" ></odin:hidden>
	<odin:hidden property="a0148c" title="职务层次批准日期" ></odin:hidden>
	<odin:hidden property="jsnlsj" title="计算年龄时间" ></odin:hidden>
	<odin:hidden property="nl" title="年龄" ></odin:hidden>
	<odin:hidden property="a0117a" title="民族(汉字)" ></odin:hidden>
	<odin:hidden property="a0104a" title="性别(汉字)" ></odin:hidden>
	
	<odin:hidden property="orgid" title="历史离退人员所在的机构id" ></odin:hidden>
	<!-- <odin:hidden property="a0192d" title="职级" ></odin:hidden> -->
	<odin:hidden property="a0120" title="级别" ></odin:hidden>
	<odin:hidden property="a0122" title="专业技术任职资格" ></odin:hidden>
	
	<odin:hidden property="pdfPath" title="pdf路径"/>
	<odin:hidden property="jlgs"/>
	<odin:hidden property="jlnr"/>
	<table cellpadding="0px;" align="center">
		<tr>
			<td>
				<table id="v_standard" class="v_standard" cellspacing="0px" cellpadding="0px;" align="left">
					<!--第1行- onblur="nameCheck(this);"-->
					<tr>
						<td class="label-clor width13-60 height1234-40 fontConfig" id="a0101SpanId_s">姓&nbsp;&nbsp;名</td>
						<td class="width24-80" style="background-color: rgb(242,247,253)"><tags:TextAreainput2 property="a0101" validator="namevalidator"  cls="width24-80 height1234-40 no-y-scroll cellbgclor" label="姓名" required="true"/></td>
						<td class="label-clor fontConfig" id="a0104SpanId_s">性&nbsp;&nbsp;别</td>
						<td class="width24-80" style="background-color: rgb(242,247,253)"><tags:SelectInput property="a0104" codetypeJS="GB2261" cls=" height1234-40 width24-80 input-text cellbgclor"/></td>
						<td class="label-clor width56-75 fontConfig" id="a0107SpanId_s">出生年月</td>
						<td class="width56-75"><tags:TextAreainput3 property="a0107" label="出生年月" cls="width24-80 height1234-40 no-y-scroll cellbgclor" vtype="dateBT"/> </td>
						<td class="left-last label-clor width7-120" rowspan="4">
							<div style="width:120px; height:174px;cursor: pointer;margin: 0px;padding: 0px;" onclick="showdialog()">
								<img alt="照片" id="personImg" style="display: block;margin: 0px;padding: 0px;cursor: pointer; " width="148" height="185" src=""  /> 
							</div>
						
						</td>
						<td class="left-last top-last " valign="top" style="width: 200px;background-color: rgb(166,202,240);text-align: left;"  rowspan="10">
							<div style="width: 100%;height: 100%;">
								<table>
									<tr>
										<td style="height: 5px; border: none;"></td>
									</tr>
								</table>
								<div id="a0163Divid" style="font-size: 18px;color: rgb(0,0,128);width: 100%; text-align: center;"></div>
								<table class="v_standard_left"  border="0" style="margin-left: 10px;">
									<tr><td><br/> </td></tr>
									<tr>
										<td align="left" style="text-align: left;"><span id="a0195SpanId_s" style="font-size: 14px;" class="fontConfig">统计关系所在单位</span></td>
									</tr>
									<tr style="width: 100px;">
										<tags:PublicTextIconEdit onchange="setParentValue" property="a0195" label2="统计关系所在单位" readonly="true" codetype="orgTreeJsonData"></tags:PublicTextIconEdit>
									</tr>
									<tr><td style="height: 5px"></td></tr>
									<tr align="left">
										<td align="left" style="text-align: left;"><span id="a0184SpanId_s" style="font-size: 14px;" class="fontConfig">公民身份证号码</span></td>
									</tr>
									<tr>
										<odin:textEdit property="a0184" required="true" validator="$h.IDCard" width="177" maxlength="18"></odin:textEdit>
									</tr>
									<tr><td style="height: 5px"></td></tr>
									<tr align="left">
										<td align="left" style="text-align: left;"><span id="a0165SpanId_s" style="font-size: 14px;" class="fontConfig">管理类别</span></td>
									</tr>
									<tr>
										<odin:select2 property="a0165" codeType="ZB130" width="160"></odin:select2>
									</tr>
									<tr><td style="height: 5px"></td></tr>
									<tr align="left">
										<td align="left" style="text-align: left;"><span id="a0160SpanId_s" style="font-size: 14px;" class="fontConfig">人员类别</span></td>
									</tr>
									<tr>
<%-- 									 <odin:select2 property="a0160" codeType="ZB125" width="144" onchange="selectchange"></odin:select2> 
 --%>									 <tags:PublicTextIconEdit property="a0160" onchange="selectchange" codetype="ZB125" width="160" label2="人员类别" readonly="true" />
										 
									</tr>
									<tr><td style="height: 5px"></td></tr>
									<tr align="left">
										<td align="left" style="text-align: left;"><span id="a0121SpanId_s" style="font-size: 14px;"  class="fontConfig">编制类型</span></td>
									</tr>
									<tr>
										<odin:select2 property="a0121" codeType="ZB135" width="160"></odin:select2>
									</tr>
									<!-- 现职务层次 -->
									<tr><td style="height: 5px"></td></tr>
									<tr align="left">
										<td align="left" style="text-align: left;"><span id="a0221SpanId_s" style="font-size: 14px;" class="fontConfig">现职务层次</span></td>
									</tr>
									<tr>
										<%-- <odin:select2 property="a0221" codeType="ZB09" width="144"></odin:select2> --%>
										<%-- <odin:select2 hideTrigger="true" property="a0221" codeType="ZB09" width="160" ondblclick="$h.openWin('RankAddPageWin','pages.publicServantManage.RankAddPage','现职务层次',710,360,document.getElementById('a0000').value,ctxPath)" onkeypress="$h.openWin('RankAddPageWin','pages.publicServantManage.RankAddPage','现职务层次',710,360,document.getElementById('a0000').value,ctxPath)" readonly="true"></odin:select2> --%>
										<odin:select2 hideTrigger="true" property="a0221" codeType="ZB09" width="177" ondblclick="a0221Click()" readonly="true"></odin:select2>
									</tr>
									<!-- 现职务层次时间 -->
									<tr><td style="height: 5px"></td></tr>
									<tr align="left">
										<td align="left" style="text-align: left;"><span id="a0288SpanId_s" class="fontConfig" style="font-size: 14px;">任现职务层次时间</span></td>
									</tr>
									<tr>
										<odin:textEdit property="a0288" width="177" readonly="true"></odin:textEdit>
									</tr>
									<!-- 职级 -->
									<tr><td style="height: 5px"></td></tr>
									<tr align="left">
										<td align="left" style="text-align: left;"><span id="a0192eSpanId_s" style="font-size: 14px;" class="fontConfig">现职级 </span></td>
									</tr>
									<tr>
										<%-- <odin:textEdit property="a0192e" width="144" readonly="true"></odin:textEdit> --%>
										<%-- <odin:select2 hideTrigger="true" property="a0192e" codeType="ZB148" width="160" ondblclick="$h.openWin('RradeRankAddPageWin','pages.publicServantManage.RradeRankAddPage','现职级',710,360,document.getElementById('a0000').value,ctxPath)" onkeypress="$h.openWin('RradeRankAddPageWin','pages.publicServantManage.RradeRankAddPage','现职级',710,360,document.getElementById('a0000').value,ctxPath)" readonly="true"></odin:select2> --%>
										<odin:select2 hideTrigger="true" property="a0192e" codeType="ZB148" width="177" ondblclick="a0192eClick()" readonly="true"></odin:select2>
									</tr>
									<!-- 现职级时间 -->
									<tr><td style="height: 5px"></td></tr>
									<tr align="left">
										<td align="left" style="text-align: left;"><span id="a0192cSpanId_s" class="fontConfig" style="font-size: 14px;">任现职级时间</span></td>
									</tr>
									<tr>
										<odin:textEdit property="a0192c" width="177" readonly="true"></odin:textEdit>
									</tr>
									
									<!-- A99Z1的信息项 -->
									<tr><td style="height: 5px"></td></tr>
									<tr align="left">
										<td>
											<odin:hidden property="a99Z100" title="id(a99Z100" ></odin:hidden>
											<input type="checkbox" name="a99z101" id="a99z101"/>
											<label id="a99z103SpanId" for="a99z101" style="font-size: 12px;">是否考录</label>
											<input type="checkbox" name="a99z103" id="a99z103"/>
											<label id="a99z103SpanId" for="a99z103" style="font-size: 12px;">是否选调生</label>
										</td>
									</tr>
									
									<tr><td style="height: 5px"></td></tr>
									<tr align="left">
										<td align="left" style="text-align: left;"><span id="a99z102SpanId_s" class="fontConfig" style="font-size: 14px;">录用时间</span></td>
									</tr>
									<tr>
										<odin:NewDateEditTag property="a99z102" isCheck="true" maxlength="8" width="177"></odin:NewDateEditTag>
									</tr>
									
									<tr><td style="height: 5px"></td></tr>
									<tr align="left">
										<td align="left" style="text-align: left;"><span id="a99z104SpanId_s" class="fontConfig" style="font-size: 14px;">进入选调生时间</span></td>
									</tr>
									<tr>
										<odin:NewDateEditTag property="a99z104" isCheck="true" maxlength="8" width="177"></odin:NewDateEditTag>
									</tr>
									
									
									<!-- 其他信息 -->
									<%-- <tr><td><br/> </td></tr>
									<tr align="left">
										<td align="left" style="text-align: left;">
											<odin:groupBox title="其他信息" >
												<span>&nbsp;</span>
												<odin:button property="entry" handler="entryClick" text="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;进入管理&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"></odin:button>
												<span>&nbsp;</span>
												<odin:button property="logout" handler="logoutClick" text="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;退出管理&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"></odin:button>
												<span>&nbsp;</span>
												<odin:button property="appointRemove" handler="appointRemoveClick" text="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;拟任免&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"></odin:button>
												<span>&nbsp;</span>
												
												
												<span>&nbsp;</span>
												<odin:button property="supply" handler="supplyClick" text="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;补充信息&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"></odin:button>
												<span>&nbsp;</span>
											</odin:groupBox>
										</td>
									</tr> --%>
									<%-- <tr align="left">
										<odin:button property="orthers" handler="orthersClick" text="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;其他信息&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"></odin:button>
									</tr> --%>
									
									<tr align="left">
										<td align="left" style="text-align: left;">
											<span>&nbsp;</span>
											<odin:button property="supply" handler="supplyClick" text="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;补充信息&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"></odin:button>
											<span>&nbsp;</span>
											<%-- <odin:button property="orthers" handler="orthersClick" text="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;其他信息&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"></odin:button> --%>
										</td>
									</tr>
									
									
								</table>
							</div>
						<td/>
					</tr>
					<%--第2行--<tags:Treeinput property="a0111" cls="height1234-40 width24-80 input-text" codetypeJS="ZB01" label="籍贯" />--%>
					<tr>
						<td class="label-clor height1234-40 fontConfig" id="a0117SpanId_s">民&nbsp;&nbsp;族</td>
						<td style="position: relative;" style="background-color: rgb(242,247,253)"><tags:SelectInput property="a0117" label="民族" cls="height1234-40 width24-80 input-text cellbgclor" codetypeJS="GB3304" />
						</td>
						<td class="label-clor fontConfig" id="a0111SpanId_s">籍&nbsp;&nbsp;贯</td>
						<td style="position: relative;" style="background-color: rgb(242,247,253)"><tags:PublicTextIconEdit2 property="a0111" codename="code_name3" codetype="ZB01" label="籍贯" cls="height1234-40 width24-80 no-y-scroll cellbgclor"  /> 
							
						</td>
						<td class="label-clor fontConfig"  id="a0114SpanId_s">出&nbsp;生&nbsp;地</td>
						<td style="position: relative;"><tags:PublicTextIconEdit2 property="a0114" cls="TAwrap height1234-40 width24-80 no-y-scroll cellbgclor" codename="code_name3" codetype="ZB01" label="出生地"/></td>
					</tr>
				 
					<tr>
						<td class="label-clor height1234-40 fontConfig" id="a0140SpanId_s">入&nbsp;&nbsp;党<br/>时&nbsp;&nbsp;间</td>				<%--link2tab('tab_addPartyTime')--%>
						<%-- <td><tags:TextAreainput2 property="a0140" cls="width24-80 height1234-40 no-y-scroll cellbgclor" ondblclick="$h.openWin('addPartyTime','pages.publicServantManage.AddPartyTimeAddPage','入党时间',600,300,document.getElementById('a0000').value,ctxPath)" onkeypress="$h.openWin('addPartyTime','pages.publicServantManage.AddPartyTimeAddPage','入党时间',600,300,document.getElementById('a0000').value,ctxPath)" readonly="true"/></td> --%>
						<td style="background-color: rgb(242,247,253)"><tags:TextAreainput2 property="a0140" cls="width24-80 height1234-40 no-y-scroll cellbgclor" ondblclick="a0140Click()"  readonly="true"/></td>
						<td class="label-clor fontConfig" id="a0134SpanId_s">参加工<br/>作时间</td>
						<td style="background-color: rgb(242,247,253)"><tags:TextAreainput3 property="a0134" label="参加工作时间" cls="width24-80 height1234-40 no-y-scroll cellbgclor" vtype="dateBT"/></td>
							
						<td class="label-clor fontConfig" id="a0128SpanId_s">健康状况</td>
						<td style="background-color: rgb(242,247,253)"><tags:SelectInput property="a0128" cls="height1234-40 width24-80 input-text cellbgclor" codetypeJS="GB2261D"/></td>
					</tr>
				
					<tr>
						<td class="label-clor height1234-40 fontConfig" id="a0196SpanId_s">专业技<br/>术职务</td>                                               <%--ondblclick="link2tab('tab_professSkill')"--%>
						<%-- <td colspan="2"><tags:TextAreainput2 property="a0196" label="专业技术职务" cls="width2T3-140 height1234-40 no-y-scroll cellbgclor" ondblclick="$h.openWin('professSkill','pages.publicServantManage.ProfessSkillAddPage','专业技术职务',950,660,document.getElementById('a0000').value,ctxPath)" onkeypress="$h.openWin('professSkill','pages.publicServantManage.ProfessSkillAddPage','专业技术职务',950,660,document.getElementById('a0000').value,ctxPath)" readonly="true"/> </td> --%>
						<td colspan="2" style="background-color: rgb(242,247,253)"><tags:TextAreainput2 property="a0196" label="专业技术职务" cls="width2T3-140 height1234-40 no-y-scroll cellbgclor" ondblclick="a0196Click()" readonly="true"/> </td>
						<td class="label-clor fontConfig" id="a0187aSpanId_s">熟悉专业<br/>有何特长</td>
						<td colspan="2"><tags:TextAreainput2  property="a0187a"  cls="width5T6-150 height1234-40 no-y-scroll cellbgclor" required="false" label="专长"/></td>
					</tr>
					<%--插入两行
					<tr>
						<td class="label-clor heightNew-40" >人&nbsp;&nbsp;&nbsp;员<br/>类&nbsp;&nbsp;&nbsp;别</td>
						<td colspan="3"><tags:SelectInput property="a0160" cls="heightNew-40 width2T3T4-220 input-text" label="人员类别" codetypeJS="ZB125"/> </td>
						<td class="label-clor " >管理类别</td>
						<td colspan="3" class="left-last"><tags:SelectInput property="a0165" cls="heightNew-40 width6T7-195 input-text" label="管理类别" onchange="selectchange" codetypeJS="ZB130"/></td>
					</tr>
					<tr>
						<td class="label-clor heightNew-40" >职&nbsp;&nbsp;&nbsp;级</td>
						<td colspan="3"><tags:SelectInput property="a0192d" cls="heightNew-40 width2T3T4-220 input-text" label="职级"  codetypeJS="ZB99"/></td>
						<td class="label-clor " >身份证号</td>
						<td colspan="3" class="left-last"><tags:Textinput vtype="IDCard" property="a0184" cls="heightNew-40 width6T7-195 input-text" label="身份证号" required="true"/> </td>
					</tr>
					--%>
					<%--第5行--%>
					<tr>
						<td class="label-clor height5678-80 fontConfig" rowspan="4" id="xlxwSpanId_s">学&nbsp;历<br/>学&nbsp;位</td>
						<td class="label-clor fontConfig" rowspan="2">全日制<br/>教&nbsp;&nbsp;育</td>															<%--link2tab('tab_degrees')--%>
						<%-- <td colspan="2"><tags:Textinput property="qrzxl" label="全日制教育：学历" cls="width3T4-140 height5i6i7i8-20 input-text2 cellbgclor" ondblclick="$h.openWin('degrees','pages.publicServantManage.DegreesAddPage','学历学位',850,450,document.getElementById('a0000').value,ctxPath)" onkeypress="$h.openWin('degrees','pages.publicServantManage.DegreesAddPage','学历学位',850,450,document.getElementById('a0000').value,ctxPath)" readonly="true"/> </td> --%>
						<td colspan="2" style="background-color: rgb(242,247,253)"><tags:Textinput property="qrzxl" label="全日制教育：学历" cls="width3T4-140 height5i6i7i8-20 input-text2 cellbgclor" ondblclick="qrzxlClick()" readonly="true"/> </td>
						<td class="label-clor fontConfig" rowspan="2">毕业院校<br/>系及专业</td>
						<%-- <td class="left-last" colspan="2"><tags:Textinput property="qrzxlxx" label="院校系及专业(学历)" cls="width6T7-195 height5i6i7i8-20 input-text2 cellbgclor" ondblclick="$h.openWin('degrees','pages.publicServantManage.DegreesAddPage','学历学位',850,450,document.getElementById('a0000').value,ctxPath)" onkeypress="$h.openWin('degrees','pages.publicServantManage.DegreesAddPage','学历学位',850,450,document.getElementById('a0000').value,ctxPath)" readonly="true"/> </td> --%>
						<td class="left-last" colspan="2" style="background-color: rgb(242,247,253);"><tags:Textinput property="qrzxlxx" label="院校系及专业(学历)" cls="width6T7-195 height5i6i7i8-20 left-last cellbgclor" ondblclick="qrzxlxxClick()" readonly="true"/> </td>
					</tr>
					<%--第6行--%>
					<tr>
						
						
						<%-- <td colspan="2"><tags:Textinput property="qrzxw" label="全日制教育：学位" cls="width3T4-140 height5i6i7i8-20 input-text2 cellbgclor" ondblclick="$h.openWin('degrees','pages.publicServantManage.DegreesAddPage','学历学位',850,450,document.getElementById('a0000').value,ctxPath)" onkeypress="$h.openWin('degrees','pages.publicServantManage.DegreesAddPage','学历学位',850,450,document.getElementById('a0000').value,ctxPath)" readonly="true"/></td> --%>
						<td colspan="2" style="background-color: rgb(242,247,253)"><tags:Textinput property="qrzxw" label="全日制教育：学位" cls="width3T4-140 height5i6i7i8-20 input-text2 cellbgclor" ondblclick="qrzxwClick()" readonly="true"/></td>
						
						<%-- <td class="left-last" colspan="2"><tags:Textinput property="qrzxwxx" label="院校系及专业(学位)" cls="width6T7-195 height5i6i7i8-20 input-text2 cellbgclor" ondblclick="$h.openWin('degrees','pages.publicServantManage.DegreesAddPage','学历学位',850,450,document.getElementById('a0000').value,ctxPath)" onkeypress="$h.openWin('degrees','pages.publicServantManage.DegreesAddPage','学历学位',850,450,document.getElementById('a0000').value,ctxPath)" readonly="true"/></td> --%>
						<td class="left-last" colspan="2" style="background-color: rgb(242,247,253)"><tags:Textinput property="qrzxwxx" label="院校系及专业(学位)" cls="width6T7-195 height5i6i7i8-20 left-last cellbgclor" ondblclick="qrzxwxxClick()" readonly="true"/></td>
					</tr>
					<%--第7行--%>
					<tr>
						
						<td class="label-clor fontConfig" rowspan="2">在&nbsp;&nbsp;职<br/>教&nbsp;&nbsp;育</td>
						<%-- <td colspan="2"><tags:Textinput property="zzxl" label="在职教育：学历" cls="width3T4-140 height5i6i7i8-20 input-text2 cellbgclor" ondblclick="$h.openWin('degrees','pages.publicServantManage.DegreesAddPage','学历学位',850,450,document.getElementById('a0000').value,ctxPath)" onkeypress="$h.openWin('degrees','pages.publicServantManage.DegreesAddPage','学历学位',850,450,document.getElementById('a0000').value,ctxPath)" readonly="true"/> </td> --%>
						<td colspan="2" style="background-color: rgb(242,247,253)"><tags:Textinput property="zzxl" label="在职教育：学历" cls="width3T4-140 height5i6i7i8-20 input-text2 cellbgclor" ondblclick="zzxlClick()" readonly="true"/> </td>
						<td class="label-clor fontConfig" rowspan="2">毕业院校<br/>系及专业</td>
						<%-- <td class="left-last" colspan="2"><tags:Textinput property="zzxlxx" label="院校系及专业(学历)" cls="width6T7-195 height5i6i7i8-20 input-text2 cellbgclor" ondblclick="$h.openWin('degrees','pages.publicServantManage.DegreesAddPage','学历学位',850,450,document.getElementById('a0000').value,ctxPath)" onkeypress="$h.openWin('degrees','pages.publicServantManage.DegreesAddPage','学历学位',850,450,document.getElementById('a0000').value,ctxPath)" readonly="true"/></td> --%>
						<td class="left-last" colspan="2" style="background-color: rgb(242,247,253)"><tags:Textinput property="zzxlxx" label="院校系及专业(学历)" cls="width6T7-195 height5i6i7i8-20 left-last cellbgclor" ondblclick="zzxlxxClick()" readonly="true"/></td>
					</tr>
					<%--第8行--%>
					<tr>
						
						
						<%-- <td colspan="2"><tags:Textinput property="zzxw" label="在职教育：学位" cls="width3T4-140 height5i6i7i8-20 input-text2 cellbgclor" ondblclick="$h.openWin('degrees','pages.publicServantManage.DegreesAddPage','学历学位',850,450,document.getElementById('a0000').value,ctxPath)" onkeypress="$h.openWin('degrees','pages.publicServantManage.DegreesAddPage','学历学位',850,450,document.getElementById('a0000').value,ctxPath)" readonly="true"/></td> --%>
						<td colspan="2" style="background-color: rgb(242,247,253)"><tags:Textinput property="zzxw" label="在职教育：学位" cls="width3T4-140 height5i6i7i8-20 input-text2 cellbgclor" ondblclick="zzxwClick()" readonly="true"/></td>
						
						<%-- <td class="left-last" colspan="2"><tags:Textinput property="zzxwxx" label="院校系及专业(学位)" cls="width6T7-195 height5i6i7i8-20 input-text2 cellbgclor" ondblclick="$h.openWin('degrees','pages.publicServantManage.DegreesAddPage','学历学位',850,450,document.getElementById('a0000').value,ctxPath)" onkeypress="$h.openWin('degrees','pages.publicServantManage.DegreesAddPage','学历学位',850,450,document.getElementById('a0000').value,ctxPath)" readonly="true"/></td> --%>
						<td class="left-last" colspan="2" style="background-color: rgb(242,247,253)"><tags:Textinput property="zzxwxx" label="院校系及专业(学位)" cls="width6T7-195 height5i6i7i8-20 left-last cellbgclor" ondblclick="zzxwxxClick()" readonly="true"/></td>
					</tr>
					
					<%--第9行--%>
					<tr>
						<td class="label-clor height9-35 fontConfig" colspan="2" id="a0192aSpanId_s">工作单位及职务</td>															<%--link2tab('tab_workUnits')--%>
						<%-- <td class="left-last" colspan="5"><tags:Textinput property="a0192a" cls="height9-35 input-text_left width3_7-410 cellbgclor" label="工作单位及职务全称" ondblclick="$h.openWin('workUnits','pages.publicServantManage.WorkUnitsAddPage','工作单位及职务',950,660,document.getElementById('a0000').value,ctxPath)" onkeypress="$h.openWin('workUnits','pages.publicServantManage.WorkUnitsAddPage','工作单位及职务',950,660,document.getElementById('a0000').value,ctxPath)" readonly="true"/></td> --%>
						<td class="left-last" colspan="5" style="background-color: rgb(242,247,253)"><tags:Textinput property="a0192a" cls="height9-35 input-text_left width3_7-410 cellbgclor" label="工作单位及职务全称" ondblclick="a0192aClick()" readonly="true"/></td>
					</tr>
					<%--第10行--%>
					<tr>
						<td class="top-last label-clor height10-359">
							<div style="width: 100%;height: 100%;position: relative;">
								<span style="font-family: STHeiti; font-size: 16px;position: absolute;top:150px;left: 22px" id="a1701SpanId_s" class="fontConfig">简<br/>历</span>
								&nbsp;<div onclick="showwin()" title="简历生成" style="width:100%;height:30px;background:url(images/jl2.png) no-repeat center center;margin-bottom: 0px;position: absolute;top:2px;left:0px;cursor:pointer;"></div>
							</div>
						</td>
						<td class="left-last top-last" colspan="6" style="background-color: rgb(242,247,253)"><tags:TextAreainput property="a1701" 
						cls="height10-359 width2_7-410 font-left cellbgclor x-form-textarea x-form-field " ondblclick="cc()"/>  </td>
					</tr>
					<tr>
						<td style="height: 20px; border: none;" colspan="7" >
						</td>
					</tr>
					<%--第1行--%>
					<tr>
						<td class="label-clor width13-60 height5678-80 fontConfig" colspan="1" id="a14z101SpanId_s" >奖<br/>惩<br/>综<br/>述</td>
						<td style="background-color: rgb(242,247,253)" class="left-last  width-478" colspan="6" ><tags:TextAreainput property="a14z101" label="奖惩综述" cls="width-478 height5678-80 font-left cellbgclor" ondblclick="$h.openWin('rewardPunish','pages.publicServantManage.RewardPunishAddPage','奖惩情况',832,500,document.getElementById('a0000').value,ctxPath)" onkeypress="$h.openWin('rewardPunish','pages.publicServantManage.RewardPunishAddPage','奖惩情况',832,500,document.getElementById('a0000').value,ctxPath)" readonly="true"/></td>
						<td class="left-last top-last " valign="top" id="jc" style="width: 200px;background-color: rgb(166,202,240);text-align: left;"  rowspan="14">
							<div style="width: 100%;height: 100%;">
								<div id="a0163Divid2 style="font-size: 18px;color: rgb(0,0,128);width: 100%; text-align: center;"></div>
								<table class="v_standard_left"  border="0" style="margin-left: 10px;"></table>
							</div>
						<td/>
					</tr>
					<%--第2行--%>
					<tr>
						<td class="label-clor height5678-80 fontConfig" colspan="1" id="a15z101SpanId_s">年度<br/>考核<br/>结果<br/>综述</td>
						<td style="background-color: rgb(242,247,253)" class="left-last" colspan="6"><tags:TextAreainput property="a15z101" label="年度考核结果综述" cls="width-478 height5678-80 font-left cellbgclor" ondblclick="$h.openWin('assessmentInfo','pages.publicServantManage.AssessmentInfoAddPage','年度考核情况',830,360,document.getElementById('a0000').value,ctxPath)" onkeypress="$h.openWin('assessmentInfo','pages.publicServantManage.AssessmentInfoAddPage','年度考核情况',830,360,document.getElementById('a0000').value,ctxPath)" readonly="true"/></td>
					</tr>
					<%--第3行--%>
					<tr>
						<td class="label-clor " rowspan="11" id="tdrowspan" style="position:relative;text-align:center;" align="center">
							<div id="upb" style="margin-top:40px;position:relative;z-index:2;width:53px;height:25px;border-radius:5px;-moz-border-radius:5px;behavior:url(PIE.htc);-webkit-border-radius:5px;border:1px solid #7b9ebd;background:url(images/up.png) transparent no-repeat 3px center;cursor:pointer;" onclick="up()">
								<span style="line-height:25px;padding-left:18px;font-size:15px;">上移</span>
							</div>
							<div id="downb" style="margin-top:10px;position:relative;z-index:2;width:53px;height:25px;border-radius:5px;-moz-border-radius:5px;behavior:url(PIE.htc);-webkit-border-radius:5px;border:1px solid #7b9ebd;background:url(images/down.png) transparent no-repeat 3px center;cursor:pointer;" onclick="down()">
								<span style="line-height:25px;padding-left:18px;font-size:15px;">下移</span>
							</div>
							<div style="height:240px;margin-top:20px;">
								<span style="position:relative;vertical-align: middle;"  id="a36SpanId_s" class="fontConfig">家<br/>庭<br/>主<br/>要<br/>成<br/>员<br/>及<br/>社<br/>会<br/>重<br/>要<br/>关<br/>系</span>
							</div>
						    <div id="addrowBtn" style="position:relative;width:53px;border:1px solid #7b9ebd;background:url(images/add.png) transparent no-repeat 1px center;cursor:pointer;" onclick="addA36row()">
								<span style="line-height:25px;padding-left:18px;font-size:15px;">新增</span>
							</div>
							<br/>
							<div id="deleterowBtn" style="position:relative;width:53px;border:1px solid #7b9ebd;background:url(images/delete.png) transparent no-repeat 1px center;cursor:pointer;" onclick="deleteA36row()">
								<span style="line-height:25px;padding-left:18px;font-size:15px;">删除</span>
							</div>
						</td>
						<td class="label-clor fontConfig" id="a3604aSpanId_s">称&nbsp;&nbsp;谓</td>
						<td class="label-clor width56-75 fontConfig" id="a3601SpanId_s">姓&nbsp;&nbsp;名</td>
						<td class="label-clor width56-75 fontConfig" id="a3607SpanId_s">出生年月</td>
						<td class="label-clor fontConfig" id="a3627SpanId_s">政&nbsp;治<br/>面&nbsp;貌</td>
						<td class="label-clor width-200 left-last fontConfig" id="a3611SpanId_s" colspan="2">工作单位及职务</td>
						
					</tr>
					
					<%--第4行--%>
					<% for(Integer i=1;i<=10;i++){ 
						String tr_i = "tr_"+i;
						String a3604a_i = "a3604a_"+i;
						String a3601_i = "a3601_"+i;
						String a3607_iF = "a3607_"+i+"F";
						String a3607_i = "a3607_"+i;
						String a3627_i = "a3627_"+i;
						String a3611_i = "a3611_"+i;
						String a3600_i = "a3600_"+i;
						%>
					<tr id="<%=tr_i %>">
						<td style="background-color: rgb(242,247,253)"><tags:SelectInputF property="<%=a3604a_i %>" codetypeJS="GB4761" cls="height1234-40 width24-80 input-text cellbgclor" /></td>
						<td style="background-color: rgb(242,247,253)">
							<%-- <tags:TextAreainput2 property="<%=a3601_i %>" onchange="namevalidator2(this)" cls="width56-75 height1234-40 no-y-scroll cellbgclor"/> --%>
							<tags:Textinput property="<%=a3601_i %>" onchange="namevalidator2(this)" cls="height1234-40 input-text width56-75 cellbgclor" onfocus="onF(this)"/>
						</td>
						<td style="background-color: rgb(242,247,253)" >
							<%-- <tags:TextAreainput5 property="<%=a3607_i %>" cls="width24-80 height1234-40 no-y-scroll cellbgclor" label="出生年月" isY="true"/> --%>
							<tags:Textinput property="<%=a3607_iF %>" cls="width24-80 height1234-40 no-y-scroll cellbgclor input-text" onfocus="onF(this)" onBlur="onB(this)"/>
							<odin:hidden property="<%=a3607_i %>"/>
							
						</td>
						<td style="background-color: rgb(242,247,253)" ><tags:SelectInput property="<%=a3627_i %>" codetypeJS="GB4762" cls="height1234-40 width56-75 input-text cellbgclor"/></td>
						<td style="background-color: rgb(242,247,253)" class="left-last " colspan="2">
							<%-- <tags:TextAreainput2 property="<%=a3611_i %>" cls="height1234-40 width-200 no-y-scroll cellbgclor" /> --%>
							<tags:Textinput property="<%=a3611_i %>" cls="height1234-40 input-text_left width-200 cellbgclor" onfocus="onF(this)"/>
							<%-- <tags:TextAreainput property="<%=a3611_i %>" cls="width-200 height1234-40 font-left cellbgclor textarea" onfocus="onF(this)" style=""/> --%>
							
							<%-- <tags:TextAreainput2  property="<%=a3611_i %>"  cls="width-200 height1234-40 no-y-scroll cellbgclor" onfocus="onF(this)"/> --%>
							<odin:hidden property="<%=a3600_i %>"/>
						</td>
					</tr>
					<%} %>
					
					<%--第14行--%>
					<tr id="targetNodeTR">
						<td class="top-last label-clor height5678-80 fontConfig" >备<br/><br/>注</td>
						<td class="left-last top-last" colspan="6"><tags:TextAreainput property="a0180" label="备注" cls="width-478 height5678-80 font-left" /></td>
					</tr>
					<tr>
						<td style="height: 20px;border: none;">
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	
	<%-- <div style="width: 100%;text-align: center;">
		<Iframe width="800px" height="300px" scrolling="auto" id="Iorthers" 
		frameborder="0" src="<%=ctxPath%>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.OrthersAddPage" style="clear:both;margin-left:0px;margin-right:0px;"></Iframe>
	</div> --%>
	
</div>
	
<div id="rmbtabsdiv"></div>
	
<div id="moreRow" style="display: none;">
	<odin:hidden property="rowLength" value="10" />
	<table id="tableMoreRow">
		<% for(Integer i=11;i<=30;i++){ 
			String tr_i = "tr_"+i;
			String a3604a_i = "a3604a_"+i;
			String a3601_i = "a3601_"+i;
			String a3607_i = "a3607_"+i;
			String a3607_iF = "a3607_"+i+"F";
			String a3627_i = "a3627_"+i;
			String a3611_i = "a3611_"+i;
			String a3600_i = "a3600_"+i;
			%>
			<tr id="<%=tr_i %>">
				<td style="background-color: rgb(242,247,253)" class="width13-60 height1234-40"><tags:SelectInputF property="<%=a3604a_i %>" codetypeJS="GB4761" cls="height1234-40 width24-80 input-text cellbgclor"/></td>
				<td style="background-color: rgb(242,247,253)">
					<%-- <tags:TextAreainput2 property="<%=a3601_i %>" onchange="namevalidator2(this)" cls="width56-75 height1234-40 no-y-scroll cellbgclor" onfocus="onF(this)"/> --%>
					<tags:Textinput property="<%=a3601_i %>" onchange="namevalidator2(this)" cls="height1234-40 input-text width56-75 cellbgclor" onfocus="onF(this)"/>
				</td>
				<td style="background-color: rgb(242,247,253)">
					<%-- <tags:TextAreainput5 property="<%=a3607_i %>" cls="width24-80 height1234-40 no-y-scroll cellbgclor" isY="true" label="出生年月"/> --%>
					<tags:Textinput property="<%=a3607_iF %>" cls="width24-80 height1234-40 no-y-scroll cellbgclor input-text" onfocus="onF(this)" onBlur="onB(this)"/>
					<odin:hidden property="<%=a3607_i %>"/>
				</td>
				<td style="background-color: rgb(242,247,253)"><tags:SelectInput property="<%=a3627_i %>" codetypeJS="GB4762" cls="height1234-40 width56-75 input-text cellbgclor"/></td>
				<td style="background-color: rgb(242,247,253)" class="left-last" colspan="2">
					<%-- <tags:TextAreainput2 property="<%=a3611_i %>" cls="height1234-40 width-200 no-y-scroll cellbgclor" /> --%>
					<tags:Textinput property="<%=a3611_i %>" cls="height1234-40 input-text_left width-200 cellbgclor" onfocus="onF(this)"/>
					<odin:hidden property="<%=a3600_i %>"/>
				</td>
			</tr>
		<%} %>
	</table>
</div>

<odin:window src="/blank.htm"  id="pdfViewWin" width="700" height="500" title="任免表打印预览界面" modal="true"/>	


	<odin:hidden property="a0141"></odin:hidden>
	<odin:hidden property="a0144"></odin:hidden>
	<odin:hidden property="a3921"></odin:hidden>
	<odin:hidden property="a3927"></odin:hidden>
	
	
	<%--简历生成--%>
	<div id="jlcontent" style="display: none;">
	    <odin:tab id="jltab" >
		    <odin:tabModel>
		    	<odin:tabItem title="简历范例" id="tab1"></odin:tabItem>
		    	<odin:tabItem title="按职务自动生成简历" id="tab2" isLast="true"></odin:tabItem>
		    </odin:tabModel>
		    <odin:tabCont itemIndex="tab1">
				<span id="contenttext" style="font-family: '宋体', Simsun;">
					<%--日期16个字符 2个空格 描述--%> 
				<br>   
				1973.07--1977.09&nbsp;&nbsp;某某省某某县某某镇小学教师<br/>
				1977.09--1979.09&nbsp;&nbsp;某某省某某县某某镇初中教师<br/>
				1979.09--1988.11&nbsp;&nbsp;某某省某某市委党校教师（其间：1985.10--<br/>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1988.07在某某省委党校电教大专班学习）<br/>
				1988.11--1993.07&nbsp;&nbsp;某某省某某市委宣传部干事、副科长、科长<br/>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;（1987.09--1992.07在某某大学某某系某某专业学习）<br/>
				1993.07--1995.11&nbsp;&nbsp;某某省某某市某某局副局长<br/>
				1995.11--1998.05&nbsp;&nbsp;某某省某某市某某局局长<br/>
				1998.05--2005.09&nbsp;&nbsp;某某省某某市副市长（其间：2001.08--2004.05<br/>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;在中央党校某某专业研究生班学习）<br/>
				2005.09--2005.10&nbsp;&nbsp;某某省某某市委常委、副市长<br/>
				2005.10--2007.02&nbsp;&nbsp;某某省某某市委副书记、代市长<br/>
				2007.02--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;某某省某某市委副书记、市长<br/>
																<br/>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;第××届中央候补委员	
				</span>
				<%-- <odin:button text="全选" property="qx1" handler="selectall"></odin:button> --%>	
				<div style="height: 70px"></div>
			</odin:tabCont>
		    <odin:tabCont itemIndex="tab2">
		    	<br>
		    	<span id="contenttext2" style="font-family: '宋体', Simsun;height: 240px"></span>
		    	<odin:button property="qx2" text="&nbsp;&nbsp;全&nbsp;&nbsp;选&nbsp;&nbsp;" handler="selectall2"></odin:button>	
		    </odin:tabCont>
	    </odin:tab>
	</div>

<script type="text/javascript">
function cc(){
	radow.doEvent('compose');
	/* var a = document.getElementById('jlgs').value;
	document.getElementById('jlgs').value = '1';
	if(a!='1'){
		radow.doEvent('compose');
	} */
}

function setVisiable(){
	for(var i=1;i<=30;i++){ 
		/* if(document.getElementById('a3607_'+i).value!=''){
			eval('a3607_'+i+'onblur();');
		}
		if(document.getElementById('a3601_'+i).value!=''){
			eval('a3601_'+i+'onblur();');
		} */
		/* if(document.getElementById('a3611_'+i).value!=''){
			eval('a3611_'+i+'onblur();');
		} */
	}
}


//统计关系所在单位,改变时触发函数 
function setParentValue(record,index){
	//如果选择了“内设机构”则提示不允许选择 
	var a0195 = document.getElementById("a0195").value;
	radow.doEvent('a0195Change',a0195);
	
	
	//realParent.document.getElementById('a0195').value=record.data.key;
}
/**********************************************校验*******************************************************/
function a0140Click(){
	
	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
 		$h.openWin('addPartyTime','pages.publicServantManage.AddPartyTimeAddPage','入党时间',600,300,document.getElementById('a0000').value,ctxPath);
}
function a0140Click2(){
	
	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
 		$h.openWin('addPartyTime','pages.publicServantManage.AddPartyTimeAddPage','入党时间',600,300,document.getElementById('a0000').value,ctxPath);
}
function qrzxlClick(){
	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
 		$h.openWin('degrees','pages.publicServantManage.DegreesAddPage','学历学位',880,500,document.getElementById('a0000').value,ctxPath);
}
function qrzxlClick2(){
	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
 		$h.openWin('degrees','pages.publicServantManage.DegreesAddPage','学历学位',880,500,document.getElementById('a0000').value,ctxPath);
}
function a0196Click(){
	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
 		$h.openWin('professSkill','pages.publicServantManage.ProfessSkillAddPage','专业技术职务',700,480,document.getElementById('a0000').value,ctxPath);
}
function a0196Click2(){
	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
 		$h.openWin('professSkill','pages.publicServantManage.ProfessSkillAddPage','专业技术职务',700,480,document.getElementById('a0000').value,ctxPath);
}
function qrzxlxxClick(){
	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
 		$h.openWin('degrees','pages.publicServantManage.DegreesAddPage','学历学位',880,500,document.getElementById('a0000').value,ctxPath);
}
function qrzxlxxClick2(){
	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
 		$h.openWin('degrees','pages.publicServantManage.DegreesAddPage','学历学位',880,500,document.getElementById('a0000').value,ctxPath);
}

function qrzxwxxClick(){
	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
 		$h.openWin('degrees','pages.publicServantManage.DegreesAddPage','学历学位',880,500,document.getElementById('a0000').value,ctxPath);
}
function qrzxwxxClick2(){
	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
 		$h.openWin('degrees','pages.publicServantManage.DegreesAddPage','学历学位',880,500,document.getElementById('a0000').value,ctxPath);
}

function zzxlClick(){
	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
 		$h.openWin('degrees','pages.publicServantManage.DegreesAddPage','学历学位',880,500,document.getElementById('a0000').value,ctxPath);
}
function zzxlClick2(){
	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
 		$h.openWin('degrees','pages.publicServantManage.DegreesAddPage','学历学位',880,500,document.getElementById('a0000').value,ctxPath);
}


function zzxlxxClick(){
	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
 		$h.openWin('degrees','pages.publicServantManage.DegreesAddPage','学历学位',880,500,document.getElementById('a0000').value,ctxPath);
}
function zzxlxxClick2(){
	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
 		$h.openWin('degrees','pages.publicServantManage.DegreesAddPage','学历学位',880,500,document.getElementById('a0000').value,ctxPath);
}

function zzxwClick(){
	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
 		$h.openWin('degrees','pages.publicServantManage.DegreesAddPage','学历学位',880,500,document.getElementById('a0000').value,ctxPath);
}
function zzxwClick2(){
	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
 		$h.openWin('degrees','pages.publicServantManage.DegreesAddPage','学历学位',880,500,document.getElementById('a0000').value,ctxPath);
}

function zzxwxxClick(){
	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
 		$h.openWin('degrees','pages.publicServantManage.DegreesAddPage','学历学位',880,500,document.getElementById('a0000').value,ctxPath);
}
function zzxwxxClick2(){
	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
 		$h.openWin('degrees','pages.publicServantManage.DegreesAddPage','学历学位',880,500,document.getElementById('a0000').value,ctxPath);
}

function a0192aClick(){
	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
 		$h.openWin('workUnits','pages.publicServantManage.WorkUnitsAddPage','工作单位及职务',980,575,document.getElementById('a0000').value,ctxPath);
}
function a0192aClick2(){
	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
 		$h.openWin('workUnits','pages.publicServantManage.WorkUnitsAddPage','工作单位及职务',980,565,document.getElementById('a0000').value,ctxPath);
}

function a0221Click(){
	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
 		$h.openWin('RankAddPageWin','pages.publicServantManage.RankAddPage','现职务层次',711,361,document.getElementById('a0000').value,ctxPath);
}
function a0221Click2(){
	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
 		$h.openWin('RankAddPageWin','pages.publicServantManage.RankAddPage','现职务层次',711,361,document.getElementById('a0000').value,ctxPath);
}

function a0192eClick(){
	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
 		$h.openWin('RradeRankAddPageWin','pages.publicServantManage.RradeRankAddPage','现职级',711,361,document.getElementById('a0000').value,ctxPath);
}
function a0192eClick2(){
	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
 		$h.openWin('RradeRankAddPageWin','pages.publicServantManage.RradeRankAddPage','现职级',711,361,document.getElementById('a0000').value,ctxPath);
}
function qrzxwClick(){
	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
 		$h.openWin('degrees','pages.publicServantManage.DegreesAddPage','学历学位',880,500,document.getElementById('a0000').value,ctxPath);
}
function qrzxwClick2(){
	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
 		$h.openWin('degrees','pages.publicServantManage.DegreesAddPage','学历学位',880,500,document.getElementById('a0000').value,ctxPath);
}
//进入管理弹出框 
function entryClick(){
	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
 	$h.openWin('entry','pages.publicServantManage.EntryAddPage','进入管理',700,300,document.getElementById('a0000').value,ctxPath);
}
//退出管理弹出框 
function logoutClick(){
	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
 	$h.openWin('logout','pages.publicServantManage.LogoutAddPage','退出管理',600,300,document.getElementById('a0000').value,ctxPath);
}
//拟任免弹出框
function appointRemoveClick(){
	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
 	$h.openWin('appointRemove','pages.publicServantManage.AppointRemoveAddPage','拟任免',1150,300,document.getElementById('a0000').value,ctxPath);
}

//补充信息集录入弹出框 
function supplyClick(){
	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
 	$h.openWin('entry','pages.publicServantManage.Supply','补充信息',700,230,document.getElementById('a0000').value,ctxPath);
}

//其他信息集录入弹出框 
function orthersClick(){
	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
 	$h.openWin('entry','pages.publicServantManage.OrthersAddPage','其他信息',1300,1000,document.getElementById('a0000').value,ctxPath);
}


var tableMoreRow = document.getElementById('tableMoreRow');
var trs = tableMoreRow.lastChild.childNodes;
var Tindex = 0;
function addA36row(){//table tbody tr input
	if(Tindex==20)return;
	var table = document.getElementById('v_standard');
	
	//第3行第一个td 标题
	var titleNode = document.getElementById("tdrowspan");//table.lastChild.firstChild.nextSibling.nextSibling.firstChild;
	var jcNode = document.getElementById("jc");
	
	var targetNode = document.getElementById("targetNodeTR");//table.lastChild.lastChild;
	
	var newNode = trs[0];
	if(!newNode.innerHTML){
		newNode = trs[Tindex];
	}
	//alert(newNode.innerHTML);
	titleNode.setAttribute("rowSpan",parseInt(titleNode.getAttribute("rowspan"))+1);
	jcNode.setAttribute("rowSpan",parseInt(jcNode.getAttribute("rowspan"))+1);
	table.lastChild.insertBefore(newNode, targetNode);
	//alert(titleNode.getAttribute("rowspan"));
	
	var rowLength = document.getElementById('rowLength');
	rowLength.value=parseInt(rowLength.value)+1+'';
	Tindex++;
}

var newwin = new Ext.Window({
		contentEl: "jlcontent",
		title : '简历范例',
		layout : 'fit',
		width : 525,
		overflow : 'hidden',
		height : 343,
		closeAction : 'hide',
		closable : true,
		minimizable : false,
		maximizable : false,
		modal : false,
		maximized:false,
		id : 'jlfl',
		bodyStyle : 'background-color:#FFFFFF; overflow-x:hidden; overflow-y:scroll',
		plain : true,
		listeners:{}
		});

//简历生成模式
function selectall(){
	var contenttext = document.getElementById("contenttext").innerText;
	Ext.getCmp("a1701").setValue(contenttext);
	var jlfl = Ext.getCmp("jlfl");
	jlfl.hide();
}
//简历生成模式
function selectall2(){
	var contenttext = document.getElementById("contenttext2").innerText;
	Ext.getCmp("a1701").setValue(contenttext);
	var jlfl = Ext.getCmp("jlfl");
	jlfl.hide();
}

function showwin(){
	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
 		document.getElementById("jlcontent").style.display="block";
 		var jlfl = Ext.getCmp("jlfl");
 		Ext.getCmp("jltab").activate("tab2");
 		if(!jlfl.rendered){  
 			jlfl.show();//alert("no reader")  
 		}else if(jlfl.hidden){  
 			jlfl.show();//alert("hidden")  
 		}else{  
 			jlfl.hide();//alert("show")  
 		}
}


/**
*新增窗口
*/
function openWin(id,url){
	var newwin = Ext.getCmp(id);
	if(!newwin.rendered){  
		newwin.show();
		var iframe = document.getElementById('iframe_'+id);
		iframe.src='<%=ctxPath%>/radowAction.do?method=doEvent&pageModel='+url;
		
	}else{  
		newwin.show();//alert("show")  
		var iframe = document.getElementById('iframe_'+id);
		iframe.src='<%=ctxPath%>/radowAction.do?method=doEvent&pageModel='+url;
	} 
}

	//newWin({id:'professSkill',title:'专业技术职务',modal:true,width:750,height:450,maximizable:true});
	//newWin({id:'degrees',title:'学历学位',modal:true,width:850,height:450,maximizable:true});
	//newWin({id:'workUnits',title:'工作单位及职务',modal:true,width:950,height:450,maximizable:true});
	//newWin({id:'addPartyTime',title:'入党时间',modal:true,width:600,height:300,maximizable:true});
	//newWin({id:'rewardPunish',title:'奖惩情况',modal:true,width:1050,height:450,maximizable:true});
	//newWin({id:'assessmentInfo',title:'年度考核情况',modal:true,width:800,height:490,maximizable:true});
	
	newWin({id:'nameCheck',title:'重名检测',modal:true,width:500,height:420,maximizable:true});
	
	newWin({id:'fontConfig',title:'字体设置',modal:true,width:380,height:230,maximizable:true});

	newWin({id:'picupload',title:'头像上传',modal:true,width:900,height:490,src:'<%=ctxPath%>/picCut/picwin.jsp'});


/*
//悬浮条页面位置定位
function scroll(obj){
	document.body.scrollTop=document.getElementById(obj.initialConfig.cls).offsetTop;
}


Ext.onReady(function(){
	//弹出窗口调整：原来的标签属性不够用。
	newWin({id:'professSkillAddPage',title:'专业技术职务'});
	newWin({id:'DegreesAddPage',title:'学位学历',width:800});
	newWin({id:'WorkUnitsAddPage',title:'工作单位及职务',width:1000,height:460});
	newWin({id:'RewardPunishAddPage',title:'奖惩情况',width:800});
	newWin({id:'AssessmentInfoAddPage',title:'年度考核情况'});
	newWin({id:'FamilyRelationsAddPage',title:'家庭主要成员及重要社会关系'});
	newWin({id:'TrainingInfoAddPage',title:'培训信息',width:800});
	
	//页面调整：tab页宽度渲染不是想要的那样，出现了左右滚动条
	var divobj = document.getElementById('tab1');
	divobj.style.width='100%';
	divobj.childNodes.item(0).childNodes.item(0).style.width='100%';
	
});

function grantTabChange(tabObj,item){
	if(item.getId()=='tab2'){
		//页面调整：还是左右滚动条问题。第二个tab页打开时 宽度比第一个大，不知道为什么。
		var divobj = document.getElementById('tab2');
		document.getElementById('tab2').style.width=document.getElementById('tab1').style.width;
		divobj.childNodes.item(0).childNodes.item(0).style.width=document.getElementById('tab1').style.width;
	}
}
//页面调整：重写， 调整悬浮条位置，  原在有tab页的情况看,有间隙。 
function eventhing(){ 
var offset=0;
if(odin.ScrollTop()>30){
	offset=0;
}else{
	offset=30-odin.ScrollTop();
}
document.getElementById("bottombar").style.top = (odin.ScrollTop()+offset - document.getElementById("bottombar").offsetHeight) + "px"; 
document.getElementById("bottombar").style.left = "0px"; 
} 

document.getElementById("bottombar").style.marginTop='28px';
function eventhing(){ 
var offset=0;
if(odin.ScrollTop()>28){
	offset=0;
}else{
	offset=30-odin.ScrollTop();
}
document.getElementById("bottombar").style.top = (odin.ScrollTop()+offset - document.getElementById("bottombar").offsetHeight) + "px"; 
document.getElementById("bottombar").style.left = "0px"; 
}
*/
var beforeclosefn = function(a){
   		var ret = isChange(thisTab.tabid);
   		return false;
   	}
window.onbeforeunload = function(){
    thisTab.un('beforeclose',beforeclosefn);
}
var thisTab = "",isveryfy=false;
Ext.onReady(function(){
	//初始化id
	var a0000id = '<%=a0000==null?"":a0000%>';
	if(a0000id==''){
		thisTab = parent.tabs.getActiveTab();
	}else{
		thisTab = parent.tabs.getItem(a0000id);
	}
	if(thisTab.initialConfig.errorInfo){
		isveryfy=true;
		var errorInfo = thisTab.initialConfig.errorInfo;
		spFeild = new Array();
		//专业技术职务 学历学位 工作单位及职务 入党时间 奖惩情况 年度考核结果 家庭主要成员及社会重要关系
		var specialFeild = {"a0196":false,//专业技术职务
							"xlxw":false,//学历学位
							"a0192a":false,//工作单位及职务
							"a0140":false,//入党时间
							"a14z101":false,//奖惩情况
							"a15z101":false,//年度考核结果
							"a36":false};//家庭主要成员及社会重要关系
		var specialFeildids = ['a0196','xlxw','a0192a','a0140','a14z101','a15z101','a36'];
		for(var it=0;it<errorInfo.length;it++){
			if('A06'==errorInfo[it].tableCode){
				specialFeild.a0196 = true;
			}
			if('A08'==errorInfo[it].tableCode){
				specialFeild.xlxw = true;
			}
			if('A02'==errorInfo[it].tableCode){
				specialFeild.a0192a = true;
			}
			if('A14'==errorInfo[it].tableCode){
				specialFeild.a14z101 = true;
			}
			if('A15'==errorInfo[it].tableCode){
				specialFeild.a15z101 = true;
			}
			if('A36'==errorInfo[it].tableCode){
				specialFeild.a36 = true;
			}
			if('a0141'==errorInfo[it].columnCode.toLowerCase()||'a0144'==errorInfo[it].columnCode.toLowerCase()||'a3921'==errorInfo[it].columnCode.toLowerCase()||'a3927'==errorInfo[it].columnCode.toLowerCase()){
				specialFeild.a0140 = true;
			}
			spFeild.push(errorInfo[it].columnCode.toLowerCase());
		}
		for(var i2=0;i2<specialFeildids.length;i2++){
			if(specialFeild[specialFeildids[i2]]){
				spFeild.push(specialFeildids[i2]);
			}
		}
		
	}


	var a0000 = thisTab.initialConfig.personid;
	var tabType = thisTab.initialConfig.tabType;

 	if(a0000.indexOf("addTab")!=-1){
		radow.doEvent('tabClick',a0000);
	}else{
		radow.doEvent('tabClick',a0000);
	} 

	
	thisTab.on('beforeclose',beforeclosefn);

	document.getElementById("divrmb1").style.display="block";
	/* document.getElementById("divrmb2").style.display="block"; */
<%-- 	var tabs = new Ext.TabPanel({
		id:'rmbTabs',
		title:'helloal',
		region: 'center',
		margins: '0 3 0 0',
		activeTab: 0,
		applyTo:'rmbtabsdiv',
		enableTabScroll: true,
		autoScroll:false,
		frame : true,
		//plugins: new Ext.ux.TabCloseMenu(),
listeners:{
   'tabchange':function(obj1,obj2){
	
	
	/** if(obj2.id == 'rmb1'){
		document.getElementById("InfoInputDiv").style.display="block";
		document.getElementById("InfoInputDiv").style.display="none";
	}
	if(obj2.id == 'Info'){
		document.getElementById("InfoInputDiv").style.display="none";
		document.getElementById("InfoInputDiv").style.display="block";
	} **/
	
	if(obj2.id=='rmb1'){
		pageCallback();
	}else if(obj2.id=='rmb2'){
		for(var i=1;i<=30;i++){ 
			if(document.getElementById('a3607_'+i).value!=''){
				eval('a3607_'+i+'onblur();');
			}
			if(document.getElementById('a3601_'+i).value!=''){
				eval('a3601_'+i+'onblur();');
			}
			if(document.getElementById('a3611_'+i).value!=''){
				eval('a3611_'+i+'onblur();');
			}
		}
		
	}else{}
}
},
		items: [{
			autoScroll:true,
			id:"rmb1",
			title : '任免表录入',
			contentEl:'divrmb1'
		},{
			autoScroll:true,
			id:"Info",
			title : '信息集录入',
			//contentEl:'divrmb2'
			html: ''
		},{
	
			autoScroll:true,
			id:"BusinessInfo",
			title : '业务信息',
			html: '<Iframe width="100%" height="100%" scrolling="auto" id="IBusinessInfo" frameborder="0" src="<%=ctxPath%>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.BusinessInfo" style="clear:both;margin-left:0px;margin-right:0px;float:right;"></Iframe>'
			
		},{
			autoScroll:true,
			id:"orthers",
			title : '其他信息',
			html: '<Iframe width="100%" height="100%" scrolling="auto" id="Iorthers" frameborder="0" src="<%=ctxPath%>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.OrthersAddPage" style="clear:both;margin-left:0px;margin-right:0px;float:right;"></Iframe>'
			
		}]
	}); 
	var viewport = new Ext.Viewport({
		layout: 'border',
		items: [tabs]
	});
	if(tabType == '1'){
		tabs.activate(tabs.getItem('rmb2'));
    	tabs.activate(tabs.getItem('rmb1'));	
	}else if(tabType == '2'){
    	tabs.activate(tabs.getItem('rmb1'));
		tabs.activate(tabs.getItem('rmb2'));
	}else if(tabType == '3'){
		tabs.activate(tabs.getItem('rmb2'));
    	tabs.activate(tabs.getItem('rmb1'));		
    	tabs.activate(tabs.getItem('orthers'));
	}else{
		tabs.activate(tabs.getItem('rmb2'));
    	tabs.activate(tabs.getItem('rmb1'));
	}
    
    
    applyFontConfig(spFeild);
    applyFontConfig($h.spFeildAll.a36);  --%>
    
});
//更改tab页的显示状态
function chengTabActive(tabType){
	
	var tabs = Ext.getCmp('rmbTabs');
	if(tabType == '1'){
		tabs.activate(tabs.getItem('rmb2'));
    	tabs.activate(tabs.getItem('rmb1'));	
	}else if(tabType == '2'){
    	tabs.activate(tabs.getItem('rmb1'));
		tabs.activate(tabs.getItem('rmb2'));
	}else if(tabType == '3'){
		tabs.activate(tabs.getItem('rmb2'));
    	tabs.activate(tabs.getItem('rmb1'));		
    	tabs.activate(tabs.getItem('orthers'));
	}else{
		tabs.activate(tabs.getItem('rmb2'));
    	tabs.activate(tabs.getItem('rmb1'));
	}
}

function applyFontConfig(spFeildx){
	var cls = 'fontConfig';
	if(isveryfy){
		cls = 'vfontConfig';
	}
	for(var i_=0;i_<spFeildx.length;i_++){
		if(document.getElementById(spFeildx[i_]+'SpanId_s')){
			$('#'+spFeildx[i_]+'SpanId_s').addClass(cls);
		}else if(document.getElementById(spFeildx[i_]+'SpanId')){
			$('#'+spFeildx[i_]+'SpanId').addClass(cls);
		}
    	
    }
}

//判断页面是否更改，
function isChange(id){//A01value : A01 的json对象 原值
	if(buttonDisabled)window.parent.tabs.remove(id);
	
	Ext.applyIf(A01value,A29value);Ext.applyIf(A01value,A53value);Ext.applyIf(A01value,A36value);Ext.applyIf(A01value,A71value);
	Ext.applyIf(A01value,A37value);Ext.applyIf(A01value,A31value);Ext.applyIf(A01value,A30value);
	var orthersWindow = null;
	var ret = true;
	var objs = [];//所有输入框对象
	var inputs = document.getElementsByTagName("input");
	var textareas = document.getElementsByTagName("textarea");
	//其他信息
	if(document.getElementById('Iorthers')){
		orthersWindow = document.getElementById('Iorthers').contentWindow;
		var orthersinputs = orthersWindow.document.getElementsByTagName("input");
		var ortherstextareas = orthersWindow.document.getElementsByTagName("textarea");
		for (var m=0; m < orthersinputs.length; m++) {
     		objs.push(orthersinputs[m]);
 		}
 		for (var m=0; m < ortherstextareas.length; m++) {
     		objs.push(ortherstextareas[m]);
 		}
		//alert(document.getElementById('Iorthers').contentWindow);
	}
	
	
	for (var m=0; m < textareas.length; m++) {
     objs.push(textareas[m]);
 	}
 	for (var m=0; m < inputs.length; m++) {
     objs.push(inputs[m]);
 	}
 	
	for(var i=0;i<objs.length;i++){
		var obj = objs[i];
		//alert(obj.name+'\r\n'+A01value[obj.name]+':'+obj.value);
		if(A01value[obj.name]||A01value[obj.name]===null||A01value[obj.name]===''){//过滤输入框 在 a01和其他信息 中没有的情况   mysql :A01value[obj.name]===''
			//alert(obj.name+':\r\n'+A01value[obj.name]+'\r\n'+obj.value);
			if(obj.value==''&&(A01value[obj.name]===null||A01value[obj.name]==='null'||A01value[obj.name]==='')){//原值为null 现值为空 也相等
				continue;
			}else{
				if((A01value[obj.name]+'').replace(/(\n)+|(\r\n)+/g, "1")==(obj.value+'').replace(/(\n)+|(\r\n)+/g, "1")){
					continue;
				}else{
					ret = false;
					$h.confirm3btn('系统提示','当前信息已经修改,是否需要保存',null,function(iid){
					if(iid=='yes'){
							if(savePerson(null,null,'true')!==false){
								//if(orthersWindow){
								//	orthersWindow.radow.doEvent('saveOthers.onclick','1');
								//}
								//window.parent.tabs.remove(id);
							}
							
						}else if(iid=='no'){
							window.parent.tabs.remove(id);
						}else if(iid=='cancel'){
							return false;
						}
					});
					return false;
				}
			}
		}
	}
	if(ret){
		window.parent.tabs.remove(id);
	}
}

function pageCallback(){
	a0101onblur();
	a0140onblur();
	a0111onblur();
	a0114onblur();
	a0107onblur();
	a0134onblur();
	//去除入党时间div显示的样式，重新设置style 
	$("#div_a0140").removeClass("TAcontent");
	$("#div_a0140").addClass("a0140Class");
	a0187aonblur();
	a0196onblur();
	//a0128onblur();
}
function fixBase64(img) {
  if (BASE64_DATA.test(img.src)) {
      img.src = base64Path + "?" + img.src.slice(5);
  }
}




</script>

<script type="text/javascript">
/* Ext.onReady(function(){
		window.exchangeMenu =  new Ext.menu.Menu({
		id:'exchangeMenu',
		items:[  
			new Ext.menu.Item({
				//id:'exportLrmBtnNrm',
				disabled:false,
				text:'打印任免表（包含拟任免信息）',
				handler:exportLrmBtnNrm
				}), 
			new Ext.menu.Item({
				//id:'exportLrmBtn',
				disabled:false,
				text:'打印任免表（不包含拟任免信息）',
				handler:exportLrmBtn
				}) 
		]});
});	 */
</script>
<odin:toolBar property="floatToolBar" applyTo="floatToolDiv">
	<odin:fill />
	<odin:buttonForToolBar text="上一人员" id="lastp" icon="images/icon/left2.gif"/>
	<odin:buttonForToolBar text="下一人员" id="nextp" icon="images/icon//right2.gif"/>
	<odin:buttonForToolBar text="保存" id="saveid" handler="savePerson" icon="images/save.gif" cls="x-btn-text-icon" />
	<%-- <odin:buttonForToolBar text="打印任免表" id="printView" menu ="exchangeMenu" icon="image/u117.png" /> --%>
	<odin:buttonForToolBar text="打印任免表" id="printAppointment" handler= "printAppointment" icon="image/u117.png" isLast="true"/>
	<%-- <odin:buttonForToolBar text="设置" id="setting" isLast="true" handler="function(){$h.openWin('fontConfig','pages.publicServantManage.FontConfig','字体设置',380,230,document.getElementById('a0000').value,ctxPath);}" icon="image/u150.png" /> --%>
</odin:toolBar>
<script type="text/javascript">
var fieldsDisabled = <%=InfoComWindowPageModel.getInfoData()%>;
Ext.onReady(function(){
	if(buttonDisabled){
		//$h.setDisabled($h.disabledButtons.a01);
	}
	$h.fieldsDisabled(fieldsDisabled);
	
	document.getElementById('a0195_combo').style.width = '157px';
	document.getElementById('a0165_combo').style.width = '157px';
	document.getElementById('a0160_combo').style.width = '157px';
	document.getElementById('a0121_combo').style.width = '157px';
	
	
	
});	

var inputfields = {a0101:{next:'a0104_combo',type:'1',i:0},
		a0104_combo:{next:'a0107',type:'0',i:1},
		a0107:{next:'a0117_combo',type:'1',i:2},
		a0117_combo:{next:'comboxArea_a0111',type:'0',i:3},
		comboxArea_a0111:{next:'comboxArea_a0114',type:'2',i:4},
		comboxArea_a0114:{next:'a0140',type:'2',i:5},
		a0140:{next:'a0134',type:'1',i:6},
		a0134:{next:'a0128',type:'1',i:7},
		a0128:{next:'a0196',type:'1',i:8},
		a0196:{next:'a0187a',type:'1',i:9},
		a0187a:{next:'qrzxl',type:'1',i:10},
		qrzxl:{next:'qrzxlxx',type:'0',i:11},
		qrzxlxx:{next:'qrzxw',type:'0',i:12},
		qrzxw:{next:'qrzxwxx',type:'0',i:13},
		qrzxwxx:{next:'zzxl',type:'0',i:14},
		zzxl:{next:'zzxlxx',type:'0',i:15},
		zzxlxx:{next:'zzxw',type:'0',i:16},
		zzxw:{next:'zzxwxx',type:'0',i:17},
		zzxwxx:{next:'a0192a',type:'0',i:18},
		a0192a:{next:'a1701',type:'0',i:19},
		a1701:{next:'a0195_combo',type:'0',i:20},
		a0195_combo:{next:'a0184',type:'0',i:21},
		a0184:{next:'a0165_combo',type:'0',i:22},
		a0165_combo:{next:'a0160_combo',type:'0',i:23},
		a0160_combo:{next:'a0121_combo',type:'0',i:24},
		a0121_combo:{next:'a0221_combo',type:'0',i:25},
		a0221_combo:{next:'a0192e_combo',type:'0',i:26},
		a0192e_combo:{next:'a14z101',type:'0',i:27},
	
		a14z101:{next:'a15z101',type:'0',i:28},
		a15z101:{next:'a0180',type:'0',i:29},
		a0180:{next:'a0101',type:'0',i:30}
		};
		
var tabkeydown = function() {
	if (event.keyCode == 9) { 
		var eobj = event.srcElement;
		var ename = eobj.tagName.toLowerCase();
		if(ename=='textarea'||ename=='input'){
			var eid = eobj.id;
			if(inputfields[eid]){
				var tobj = inputfields[inputfields[eid].next];
				if(tobj.type=='0'){
					document.getElementById(inputfields[eid].next).focus();
				}else if(tobj.type=='1'){
					document.getElementById('wrapdiv_'+inputfields[eid].next).click();
				}else if(tobj.type=='2'){
					document.getElementById('wrapdiv_'+inputfields[eid].next.split('comboxArea_')[1]).click();
				}
			}
		}else{
			document.getElementById('wrapdiv_a0101').click();
		}
		if(!Ext.isIE){
			event.preventDefault(); 
		}
		return false;
	}
};


if(window.attachEvent){
     document.attachEvent('onkeydown',tabkeydown );  
     
}		
if(window.addEventListener){
	//document.addEventListener('keydown',tabkeydown,false ); 
} 	

function setA0221Value(text){
	radow.doEvent("setA0221Value",text);
}
function setA0192eValue(text){
	radow.doEvent("setA0192eValue",text);
}
function setA0195Value(key,value){
	radow.doEvent("setA0195Value",key+"|"+value);
}
function setA0288Value(text){
	radow.doEvent("setA0288Value",text);
}
function setA0192cValue(text){
	radow.doEvent("setA0192cValue",text);
}


//简历上面提示信息
Ext.onReady(function(){
	var jltab__tab1 = document.getElementById('jltab__tab1');
	var mubiao = jltab__tab1.parentNode;
	
	$("#"+mubiao.id).after("<div style='POSITION: absolute; LEFT: 320px; TOP: 5px;font-size: 12px;color:red;'>依据工作单位及职务自动生成</div>");  
	
	if(fieldsDisabled.indexOf("a3611") != -1){		//判断a3611是否没有权限编辑
		
		var formfield = null;
		for(var m=1;m<=30;m++){
			formfield= 'a3611_'+m;
			
			if(formobj = document.getElementById(formfield)){
				$(formobj).addClass('frot');
				formobj.readOnly = "true";
			}
		}
	}
	
	
});	


//填写身份证后，性别和出生日期联动显示，身份证分15位和18位处理 
function card(){
	var IDCard = document.getElementById("a0184").value;
	
	if(IDCard.length == 15 || IDCard.length == 18){
		var a0104 = 2;			//性别：1男，2女 
		var a0104_combo = "女";
		var a0107 = 200001;		//出生年月
		
		if(IDCard.length == 18){			//18位身份证联动处理 
			//性别
			a0104 -= IDCard.substring(16,17)%2;
			//出生年月 
			a0107 = IDCard.substring(6,14);
		}
		
		if(IDCard.length == 15){			//15位身份证联动处理 
			//性别
			a0104 -= IDCard.substring(14,15)%2;
			//出生年月 
			a0107 = "19" + IDCard.substring(6,12);
		}
		
		
		if(a0104 == 1){
			a0104_combo = "男";
		}
		//赋值 
		document.getElementById("a0107").value = a0107;
		document.getElementById("div_a0107").innerHTML = a0107;
		document.getElementById("a0104").value = a0104;
		document.getElementById("a0104_combo").value = a0104_combo;
	}
}


function onF(onId){
	
	var isF = onId.id.indexOf("F");
	
	if(isF == "-1"){		//不包含F
		var i = onId.id.substring(6,onId.id.length);
	}else{
		var i = onId.id.substring(6,onId.id.length-1);
	}
	
	
	//var i = onId.id.substring(6,onId.id.length-1);
	document.getElementById("num").value = i;
	
	var a3607 = onId.id.substring(0,5);
	
	
	if(isF == "-1"){		//不包含F
		var show = document.getElementById(onId.id.substring(0,onId.id.length)).value;
	}else{
		var show = document.getElementById(onId.id.substring(0,onId.id.length-1)).value;
	}
	
	//var show = document.getElementById(onId.id.substring(0,onId.id.length-1)).value;
	if(a3607 == "a3607"){
		document.getElementById(onId.id).value = show;
	}
	
}


//家庭成员的出生年月，焦点离开时间 
function onB(onId){
	var time = document.getElementById(onId.id).value;
	document.getElementById(onId.id.substring(0,onId.id.length-1)).value = time;
	
	var showTime;
	if(time != null && time != "" && time.length > 4){
		showTime = time.substring(0,4) + "." + time.substring(4,6);
		document.getElementById(onId.id).value = showTime;
	}
	
}



//家庭成员上移
/* function up(){
	var num = document.getElementById("num").value;
	var num2 = Number(num)-Number(1);
	
	
	if(num == null || num == ""){
		Ext.Msg.alert("系统提示","请先选择家庭成员！");
		return;
	}
	
	//判断是否已经是第一行
	if(num == 1){
		Ext.Msg.alert("系统提示","当前家庭成员已排在第一位！");
		return;
	}
	
	//获得当前选择的tr标签id
	var trId = "tr_"+ num
	
	var _row = document.getElementById(trId);
	//如果不是第一行，则与上一行交换顺序
	var _node = _row.previousSibling;
	while(_node && _node.nodeType != 1){
	  _node = _node.previousSibling;
	}
	if(_node){
	  swapNode(_row,_node);
	}
	
	document.getElementById("num").value = (Number(num)-Number(1));
	
	
	//将两列的id，对换
	document.getElementById("tr_" + num ).id = "trF100";
	document.getElementById("tr_" + num2 ).id = "tr_" + num;
	document.getElementById("trF100").id = "tr_" + num2;
	
	//id
	document.getElementById("a3600_" + num ).id = "a3600F100";
	document.getElementById("a3600_" + num2 ).id = "a3600_" + num;
	document.getElementById("a3600F100").id = "a3600_" + num2;
	
	//称谓
	document.getElementById("a3604a_" + num ).id = "a3604aF100";
	document.getElementById("a3604a_" + num2 ).id = "a3604a_" + num;
	document.getElementById("a3604aF100").id = "a3604a_" + num2;
	
	//姓名
	document.getElementById("a3601_" + num ).id = "a3601F100";
	document.getElementById("a3601_" + num2 ).id = "a3601_" + num;
	document.getElementById("a3601F100").id = "a3601_" + num2;
	
	//出生年月
	document.getElementById("a3607_" + num ).id = "a3607F100";
	document.getElementById("a3607_" + num2 ).id = "a3607_" + num;
	document.getElementById("a3607F100").id = "a3607_" + num2;
	
	//出生年月显示字段
	document.getElementById("a3607_" + num +"F").id = "a3607F100F";
	document.getElementById("a3607_" + num2 +"F").id = "a3607_" + num +"F";
	document.getElementById("a3607F100F").id = "a3607_" + num2 +"F";
	
	//政治面貌
	document.getElementById("a3627_" + num ).id = "a3627F100";
	document.getElementById("a3627_" + num2 ).id = "a3627_" + num;
	document.getElementById("a3627F100").id = "a3627_" + num2;
	
	//工作单位及职务 
	document.getElementById("a3611_" + num ).id = "a3611F100";
	document.getElementById("a3611_" + num2 ).id = "a3611_" + num;
	document.getElementById("a3611F100").id = "a3611_" + num2;
	
	
	//实现排序的数据库持久化
	//radow.doEvent("saveA36F");
}
 */
 
 //删除一列
 function deleteA36row(){ 
	 var num = document.getElementById("num").value;	//当前要删除的行编号
	 	
	 var tr = "tr_"	+ num;
	 var a3600Id = "a3600_"	+ num;
	 
	 var a3600 = document.getElementById(a3600Id).value;
	 
	 var A3601 = document.getElementById("a3601_" + num).value;
	 
	 Ext.Msg.confirm('系统提示','确定要删除  '+A3601+'  家庭成员吗？',
	      function(btn){
	        if(btn=='yes'){
	          	
	          	radow.doEvent("deleteA36F",a3600);
	        }else{
	        }
	        
	      },this);
	 //document.getElementById(tr).remove();
	 
 }


function up(){
	var num = document.getElementById("num").value;
	var num2 = Number(num)-Number(1);
	
	
	if(num == null || num == ""){
		Ext.Msg.alert("系统提示","请先选择家庭成员！");
		return;
	}
	
	//判断是否已经是第一行
	if(num == 1){
		Ext.Msg.alert("系统提示","当前家庭成员已排在第一位！");
		return;
	}
	
	//获得当前选择的tr标签id
	var trId = "tr_"+ num
	
	var _row = document.getElementById(trId);
	//如果不是第一行，则与上一行交换顺序
	var _node = _row.previousSibling;
	while(_node && _node.nodeType != 1){
	  _node = _node.previousSibling;
	}
	if(_node){
	  swapNode(_row,_node);
	}
	
	document.getElementById("num").value = (Number(num)-Number(1));
	
	
	//将两列的id，对换
	$("#"+ "tr_" + num).attr("id","trF100");
	$("#"+ "tr_" + num2 ).attr("id","tr_" + num);
	$("#trF100").attr("id","tr_" + num2);
	
	//id
	$("#"+ "a3600_" + num ).attr("id","a3600F100");
	$("#"+ "a3600_" + num2 ).attr("id","a3600_" + num); 
	$("#a3600F100").attr("id","a3600_" + num2);
	
	//称谓
	$("#"+ "a3604a_" + num ).attr("id","a3604aF100");
	$("#"+ "a3604a_" + num2 ).attr("id","a3604a_" + num); 
	$("#a3604aF100").attr("id","a3604a_" + num2);
	
	//姓名
	$("#"+ "a3601_" + num ).attr("id","a3601F100"); 
	$("#"+ "a3601_" + num2 ).attr("id","a3601_" + num); 
	$("#a3601F100").attr("id","a3601_" + num2); 
	
	//出生年月
	$("#"+ "a3607_" + num ).attr("id","a3607F100");
	$("#"+ "a3607_" + num2 ).attr("id","a3607_" + num);
	$("#a3607F100").attr("id","a3607_" + num2); 
	
	//出生年月显示字段
	$("#"+ "a3607_" + num +"F").attr("id","a3607F100F");
	$("#"+ "a3607_" + num2 +"F").attr("id","a3607_" + num +"F"); 
	$("#a3607F100F").attr("id","a3607_" + num2 +"F"); 
	
	//政治面貌
	$("#"+ "a3627_" + num ).attr("id","a3627F100");
	$("#"+ "a3627_" + num2 ).attr("id","a3627_" + num); 
	$("#a3627F100").attr("id","a3627_" + num2); 
	
	//工作单位及职务 
	$("#"+ "a3611_" + num ).attr("id","a3611F100");
	$("#"+ "a3611_" + num2 ).attr("id","a3611_" + num); 
	$("#a3611F100").attr("id","a3611_" + num2);
	
	
	//实现排序的数据库持久化
	radow.doEvent("saveA36F");
}

function down(){
	var num = document.getElementById("num").value;
	var num2 = Number(num) + Number(1);
	var total = document.getElementById("total").value;
	
	if(num == null || num == ""){
		Ext.Msg.alert("系统提示","请先选择家庭成员！");
		return;
	}
	//判断是否已经是最后一行
	if(num == total){
		Ext.Msg.alert("系统提示","当前家庭成员已排在最后一位！");
		return;
	}
	
	//获得当前选择的tr标签id
	var trId = "tr_"+ num
	
	var _row = document.getElementById(trId);
	//如果不是最后一行，则与下一行交换顺序
	var _node = _row.nextSibling;
	while(_node && _node.nodeType != 1){
	  _node = _node.nextSibling;
	}
	if(_node){
	  swapNode(_row,_node);
	}
	
	document.getElementById("num").value = (Number(num) + Number(1));
	
	//将两列的id，对换
	$("#"+ "tr_" + num).attr("id","trF100");
	$("#"+ "tr_" + num2 ).attr("id","tr_" + num);
	$("#trF100").attr("id","tr_" + num2);
	
	//id
	$("#"+ "a3600_" + num ).attr("id","a3600F100");
	$("#"+ "a3600_" + num2 ).attr("id","a3600_" + num); 
	$("#a3600F100").attr("id","a3600_" + num2);
	
	//称谓
	$("#"+ "a3604a_" + num ).attr("id","a3604aF100");
	$("#"+ "a3604a_" + num2 ).attr("id","a3604a_" + num); 
	$("#a3604aF100").attr("id","a3604a_" + num2);
	
	//姓名
	$("#"+ "a3601_" + num ).attr("id","a3601F100"); 
	$("#"+ "a3601_" + num2 ).attr("id","a3601_" + num); 
	$("#a3601F100").attr("id","a3601_" + num2); 
	
	//出生年月
	$("#"+ "a3607_" + num ).attr("id","a3607F100");
	$("#"+ "a3607_" + num2 ).attr("id","a3607_" + num);
	$("#a3607F100").attr("id","a3607_" + num2); 
	
	//出生年月显示字段
	$("#"+ "a3607_" + num +"F").attr("id","a3607F100F");
	$("#"+ "a3607_" + num2 +"F").attr("id","a3607_" + num +"F"); 
	$("#a3607F100F").attr("id","a3607_" + num2 +"F"); 
	
	//政治面貌
	$("#"+ "a3627_" + num ).attr("id","a3627F100");
	$("#"+ "a3627_" + num2 ).attr("id","a3627_" + num); 
	$("#a3627F100").attr("id","a3627_" + num2); 
	
	//工作单位及职务 
	$("#"+ "a3611_" + num ).attr("id","a3611F100");
	$("#"+ "a3611_" + num2 ).attr("id","a3611_" + num); 
	$("#a3611F100").attr("id","a3611_" + num2);
	
	//实现排序的数据库持久化
	radow.doEvent("saveA36F");
}






	
//家庭成员下移	
/* function down(){
	var num = document.getElementById("num").value;
	var num2 = Number(num) + Number(1);
	var total = document.getElementById("total").value;
	
	if(num == null || num == ""){
		Ext.Msg.alert("系统提示","请先选择家庭成员！");
		return;
	}
	//判断是否已经是最后一行
	if(num == total){
		Ext.Msg.alert("系统提示","当前家庭成员已排在最后一位！");
		return;
	}
	
	//获得当前选择的tr标签id
	var trId = "tr_"+ num
	
	var _row = document.getElementById(trId);
	//如果不是最后一行，则与下一行交换顺序
	var _node = _row.nextSibling;
	while(_node && _node.nodeType != 1){
	  _node = _node.nextSibling;
	}
	if(_node){
	  swapNode(_row,_node);
	}
	
	document.getElementById("num").value = (Number(num) + Number(1));
	
	//将两列的id，对换
	document.getElementById("tr_" + num ).id = "trF100";
	document.getElementById("tr_" + num2 ).id = "tr_" + num;
	document.getElementById("trF100").id = "tr_" + num2;
	
	//id
	document.getElementById("a3600_" + num ).id = "a3600F100";
	document.getElementById("a3600_" + num2 ).id = "a3600_" + num;
	document.getElementById("a3600F100").id = "a3600_" + num2;
	
	//称谓
	document.getElementById("a3604a_" + num ).id = "a3604aF100";
	document.getElementById("a3604a_" + num2 ).id = "a3604a_" + num;
	document.getElementById("a3604aF100").id = "a3604a_" + num2;
	
	//姓名
	document.getElementById("a3601_" + num ).id = "a3601F100";
	document.getElementById("a3601_" + num2 ).id = "a3601_" + num;
	document.getElementById("a3601F100").id = "a3601_" + num2;
	
	//出生年月
	document.getElementById("a3607_" + num ).id = "a3607F100";
	document.getElementById("a3607_" + num2 ).id = "a3607_" + num;
	document.getElementById("a3607F100").id = "a3607_" + num2;
	
	//出生年月显示字段
	document.getElementById("a3607_" + num +"F").id = "a3607F100F";
	document.getElementById("a3607_" + num2 +"F").id = "a3607_" + num +"F";
	document.getElementById("a3607F100F").id = "a3607_" + num2 +"F";
	
	//政治面貌
	document.getElementById("a3627_" + num ).id = "a3627F100";
	document.getElementById("a3627_" + num2 ).id = "a3627_" + num;
	document.getElementById("a3627F100").id = "a3627_" + num2;
	
	//工作单位及职务 
	document.getElementById("a3611_" + num ).id = "a3611F100";
	document.getElementById("a3611_" + num2 ).id = "a3611_" + num;
	document.getElementById("a3611F100").id = "a3611_" + num2;
	
	//实现排序的数据库持久化
	//radow.doEvent("saveA36F");
} */

//实现家庭成员js移动效果
function swapNode(node1,node2){
	//获取父结点
	var _parent = node1.parentNode;
	//获取两个结点的相对位置
	var _t1 = node1.nextSibling;
	var _t2 = node2.nextSibling;
	//将node2插入到原来node1的位置
	if(_t1)_parent.insertBefore(node2,_t1);
	else _parent.appendChild(node2);
	//将node1插入到原来node2的位置
	if(_t2)_parent.insertBefore(node1,_t2);
	else _parent.appendChild(node1);
}

$(function(){
	
	//是否考录
    $("#a99z101").change(function() {
    	var a99z101 = document.getElementById("a99z101").checked;
    	
    	if(!a99z101){
    		$h.dateDisable('a99z102');
    	}else{
    		$h.dateEnable('a99z102');
    	}
    	
    });
	
	//是否选调生
    $("#a99z103").change(function() {
    	var a99z103 = document.getElementById("a99z103").checked;
    	
		if(!a99z103){
			$h.dateDisable('a99z104');
    	}else{
    		$h.dateEnable('a99z104');
    	}
    	
    });
}); 


function bhua(){
	var a99z101Odl = document.getElementById("a99z101").checked;
	var a99z103Odl = document.getElementById("a99z103").checked;
	
	
	if(!a99z101Odl){
		$h.dateDisable('a99z102');
	}else{
		$h.dateEnable('a99z102');
	}
	
	if(!a99z103Odl){
		$h.dateDisable('a99z104');
	}else{
		$h.dateEnable('a99z104');
	}
}

</script>
<odin:hidden property="num"></odin:hidden>
<odin:hidden property="total"></odin:hidden>
