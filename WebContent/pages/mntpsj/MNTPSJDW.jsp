<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.SysOrgPageModel"%>
<%@ page import="java.util.*"%> 
<%@ page import="java.text.*"%> 
<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@include file="/comOpenWinInit.jsp" %>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comboxWithTree.js"></script>
<link rel="stylesheet" type="text/css" href="mainPage/css/bootstrap-combined.min.css"> 
<link rel="stylesheet" type="text/css" href="mainPage/css/odin-font-size.css"> 
<style>

input{
	border: 1px solid #c0d1e3 !important;
}
.x-grid3-cell-inner, .x-grid3-hd-inner{
	white-space:normal !important;
}
.ext-ie .x-grid3-cell-enable{
	height: auto !important;
}

div.x-form-field-wrap{
	display: inline-block;
	/* margin-right:300px;  */
}
div.x-form-field-wrap1{
	display: inline-block;
}
div.x-form-item{
/* width: 80px; */
}
</style>
<odin:hidden property="fabd00"/>
<odin:hidden property="famx00"/>
<odin:hidden property="famx01"/>
<odin:hidden property="addType" value="2"/>
<div id="TitleContent" style="width: 100%;text-align:right;">

	<table class="FAINFO" style="display: inline-block;width: 300px;margin-right:130px; margin-top: 5px;">
		<tr>
			<odin:textEdit property="fabd02" label="名称"  ></odin:textEdit>
			<td>&nbsp;</td>
			<odin:select2 property="mntp05" label="模式" width="100" data="['2','区县市'],['1','市直单位'],['4','国企高校']" /><%-- ,['5','乡镇街道'] --%>
		</tr>
	</table>
	<div style="display: inline-block;margin-right: 10px;">
	<button type='button' class="btn btn-primary" onclick="infoDelete()" style="margin: -15px 10px 5px 10px;">删除</button>
	<button type='button' class="btn btn-primary GBSC" onclick="copyFAMX(1,1)" style="margin: -15px 10px 5px 10px;display: none;">现状</button>
	<button type='button' class="btn btn-primary GBSC" onclick="copyFAMX(1,2)" style="margin: -15px 10px 5px 10px;display: none;">空方案</button>
	<button type='button' class="btn btn-primary GBSC" onclick="copyFAMX(2,2)" style="margin: -15px 10px 5px 10px;display: none;">现状方案</button>
	<button type='button' class="btn btn-primary GBSC" onclick="copyFAMX(3,2)" style="margin: -15px 10px 5px 10px;display: none;">复制方案</button>
	<button type='button' class="btn btn-primary GBQT" onclick="loadadd(1)" style="margin: -15px 10px 5px 10px;">现状</button>
	<button type='button' class="btn btn-primary GBQT" onclick="loadadd(2)" style="margin: -15px 10px 5px 10px;">模拟方案</button>
	</div>
</div>
<odin:editgrid2 property="memberGrid" height="500" hasRightMenu="false" autoFill="true"    bbarId="pageToolBar"  url="/">
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="famx00" />
		<odin:gridDataCol name="famx02" />
		<odin:gridDataCol name="famx03" />
		<odin:gridDataCol name="fabd00" />
		<odin:gridDataCol name="fabd06" />
		<odin:gridDataCol name="famx01"/>
		<odin:gridDataCol name="set" isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn2></odin:gridRowNumColumn2>
		<odin:gridEditColumn2 dataIndex="famx02" width="20" header="排序号" menuDisabled="true" sortable="false" editor="text" edited="false" align="center"    />
		<odin:gridEditColumn2 dataIndex="famx03" width="100" header="名称"  menuDisabled="true" sortable="false"  editor="text" edited="true" align="center"/>
		<odin:gridEditColumn2 dataIndex="famx01" width="60" header="模拟调配类型" menuDisabled="true" sortable="false"  editorId="asd" editor="select" selectData="['1','现状'],['2','模拟情况']" edited="true" align="center"/>
		<odin:gridEditColumn2 dataIndex="set" width="60" header="操作" editor="text" menuDisabled="true" sortable="false"  edited="false" align="center" renderer="operFA" isLast="true"/>
	</odin:gridColumnModel>
</odin:editgrid2>




<script type="text/javascript">
function operFA(value, params, record, rowIndex, colIndex, ds) {
	var famx00 = record.get("famx00");
	var famx01 = record.get("famx01");
	var fabd06 = record.get("fabd06");
	result="<table width='100%'><tr><td width='100%' align='center'><a href=\"javascript:choosedw('"+famx00+"','"+famx01+"','"+fabd06+"')\">选择单位</a></td></tr></table>"
/* 		result="<table width='100%'><tr><td width='100%' align='center'><a href=\"javascript:choosedw('"+famx00+"','1')\">选择单位</a></td></tr></table>" */
	
	
	return result;
	
	
}

function choosedw(famx00,famx01,fabd06){
	//$h.openPageModeWin('choosedw','pages.gwdz.CHOOSEdw','部门选择',530,550,{famx00:famx00,famx01:famx01},'<%= request.getContextPath() %>');
	
	if(fabd06=='1'){//乡镇
		$h.openPageModeWin('choosedwsjXZ','pages.mntpsj.CHOOSExzdw','部门选择',530,550,{famx00:famx00,famx01:famx01},'<%= request.getContextPath() %>');
	}else{//区县
		$h.openPageModeWin('choosedwsj','pages.mntpsj.CHOOSEdw','部门选择',530,550,{famx00:famx00,famx01:famx01},'<%= request.getContextPath() %>');
	}
}

function reload(){
	radow.doEvent('memberGrid.dogridquery');
}
function infoDelete(){
	var famx00=document.getElementById('famx00').value;
	if(famx00==null || famx00 ==''){
		$h.alert('系统提示：','请选择行！',null,150);
 		return;	
	}
	$h.confirm("系统提示","删除模拟情况，将同时删除该情况下的调配数据，是否确认删除？",500,function(id) { 
		if("ok"==id){
			radow.doEvent('infoDelete');
		}else{
			return;
		}		
	});	
	
}
function copyFAMX(addType,addfamx01){
	var famx00=document.getElementById('famx00').value;
	var famx01=document.getElementById('famx01').value;
	var fabd00=document.getElementById('fabd00').value;
	//区县市 1 为新增空白方案 2为新增方案，模拟情况的人默认为现状  3为复制
	$('#addType').val(addType);
	
	if(addType == '1'||addType == '2'){
		if(fabd00==null || fabd00 ==''){
			$h.alert('系统提示：','参数fabd00不对！',null,150);
	 		return;	
		}
	}else{
		if(famx00==null || famx00 ==''){
			$h.alert('系统提示：','请选择被复制的方案！',null,150);
	 		return;	
		}
		if(famx01=='1'){
			$h.alert('系统提示：','请选择模拟方案！',null,150);
	 		return;	
		}
	}
	radow.doEvent('copyFAMX',addfamx01);
	
	/* var parm = {};
	parm['famx00'] = famx00;
	parm['fabd00'] = fabd00;
	//区县市 1 为新增空白方案 2为新增方案，模拟情况的人默认为现状  3为复制
	parm['type'] = type;
	Ext.Ajax.request({
		method : 'POST',
		form : 'commForm',
		async : true,
		params : parm,
		timeout : 300000,// 按毫秒计算
		url : contextPath+"/radowAction.do?method=doEvent&pageModel=pages.mntpsj.MNTPSJDW&eventNames=copyFAMX",
		success : function(resData) {
			console.log(resData)
			var cfg = Ext.util.JSON.decode(resData.responseText);
			if(cfg.messageCode=="1"){
				$h.alert('',cfg.mainMessage)
				return;
			}
			
			


			//responseText
			if (!!callback) {
				if(cfg.elementsScript&&cfg.elementsScript.indexOf("\\n")>0){
		          cfg.elementsScript = cfg.elementsScript.replace(/\\r/gi,"");
		          cfg.elementsScript = cfg.elementsScript.replace(/\\n/gi,"{RN}");
		        }
				callback(cfg);
			}
		},
		failure : function(res, options) {
			Ext.Msg.hide();
			alert("网络异常！");
		}
	}); */
	
}
function loadadd(addfamx01){
	var fabd00=document.getElementById('fabd00').value;
	if(fabd00==null || fabd00==''){
		$h.alert('系统提示：','请选择方案！',null,150);
 		return;	
	}
	radow.doEvent('saveFABD',addfamx01);
}
<%
String fabd00 = request.getParameter("fabd00");

%>
Ext.onReady(function(){
	$h.initGridSort('memberGrid',function(g){
		radow.doEvent('rolesort');
	});	
	
	if(""!='<%=fabd00==null?"":fabd00%>'){
		$('#fabd00').val('<%=fabd00%>');
	}else{
		$('#fabd00').val(parent.Ext.getCmp(subWinId).initialConfig.fabd00);
	}
	
	
	Ext.getCmp('memberGrid').on('rowclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index)
		document.getElementById('famx00').value = rc.data.famx00;
		document.getElementById('famx01').value = rc.data.famx01;
	});
	
	
	parent.Ext.getCmp(subWinId).on('beforeclose',function(){
		realParent.radow.doEvent('initX');
	})
	
	
	
 	 Ext.getCmp('fabd02').on('blur',function(){
 		 if(this.getValue()==''){
 			 return;
 		 }
 		radow.doEvent('saveFabd02');
	 })
 	 Ext.getCmp('mntp05_combo').on('select',function(){
 		 if(this.getValue()==''){
 			 return;
 		 }
 		 
 		changeBTN() 
 		radow.doEvent('saveFabd02');
	 });
	
});
function changeBTN(){
	if($('#mntp05').val()=='2'){
		$('.GBSC').show();
		$('.GBQT').hide();
		$('.FAINFO').css({'margin-right':'100px'});
		
	 }else{
		$('.GBSC').hide();
		$('.GBQT').show();
		$('.FAINFO').css({'margin-right':'258px'});
	 }
}
</script>
