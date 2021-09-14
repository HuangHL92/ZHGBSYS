<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>

<odin:toolBar property="btnToolBar" applyTo="btnToolBarDiv">
	<odin:fill />
	<odin:buttonForToolBar text="pps数据抽样" id="ppsStart" icon="images/icon/exp.png" handler="start"></odin:buttonForToolBar>
	<odin:separator />
	<odin:buttonForToolBar text="保存列表" icon="images/save.gif" id="dataSave"/>
	<odin:separator />
	<odin:buttonForToolBar text="导出excel" id="excelOut" icon="images/icon/exp.png" handler="outData" isLast="true"></odin:buttonForToolBar>
</odin:toolBar>

<div id="btnToolBarDiv" style="width:955px;"></div>
<div style="width:100%;">
	<div id="clear_search" style="width:60%">
			<table style="width:100%;" >
				<tr align="left">
					<odin:select property="type" label="" canOutSelectList="true" value = '2' data="['1','地方'],['2','中央']" width="120"></odin:select>
					<odin:textEdit property="sort" label="抽取数量"/> 
				</tr>
			</table>
		</div>
	<odin:editgrid  property="ppsData" height="425" width="700" title="" autoFill="true" bbarId="pageToolBar" pageSize="9999"  url="/" load="refreshPerson">
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="a0000" />
		<odin:gridDataCol name="a0184" />
		<odin:gridDataCol name="a0101" />
		<odin:gridDataCol name="a0192a" />
		<odin:gridDataCol name="b0127" />
		<odin:gridDataCol name="a0221" />
		<odin:gridDataCol name="a0219" isLast="true" />
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
	<odin:gridRowNumColumn></odin:gridRowNumColumn>
			<odin:gridEditColumn2 dataIndex="a0101" width="60" header="姓  名"
				align="center" editor="text" edited="false"/>
			<odin:gridEditColumn2 header="身份证号 " edited="false" width="90"
				align="center" dataIndex="a0184" editor="text" />
			<odin:gridEditColumn2 dataIndex="a0192a" width="110" header="工作单位及职务"
				align="center" editor="text" edited="false"/>	
			<odin:gridEditColumn2 dataIndex="b0127" width="110" header="机构层次" codeType="ZB03"
				align="center" editor="select" edited="false"/>
			<odin:gridEditColumn2 dataIndex="a0221" width="110" header="职务层次" codeType="ZB09"
				align="center" editor="select" edited="false"/>
			<odin:gridEditColumn2 dataIndex="a0219" width="110" header="是否领导职务" codeType="ZB42"
				align="center" editor="select" edited="false"/>	
			<odin:gridEditColumn2 dataIndex="a0000" width="100" header="人员id"
				hideable="false" editor="text" align="center" isLast="true"
				hidden="true" />
	</odin:gridColumnModel>
	</odin:editgrid>
</div>
<odin:hidden property="a0000S" title="a0000S值"/>
<odin:hidden property="a0200S" title="a0200S值"/>
<script type="text/javascript">
function setWidthHeight(){
	document.getElementById("btnToolBarDiv").parentNode.parentNode.style.overflow='hidden';
	var height=document.body.clientHeight;
	var width=document.body.clientWidth;
	document.getElementById("btnToolBarDiv").parentNode.style.width=width+'px';
	var height_top=document.getElementById("btnToolBarDiv").offsetHeight;
	//var clear_search_height=document.getElementById("clear_search").offsetHeight;
	document.getElementById("btnToolBarDiv").style.width=width+'px';
	Ext.getCmp("ppsData").setHeight(height-height_top);
	Ext.getCmp("ppsData").setWidth(width);
}
Ext.onReady(function() {
	setWidthHeight();
	window.onresize=setWidthHeight;
});
function outData(){
	odin.grid.menu.expExcelFromGrid('ppsData', "pps数据抽样", null,null, false);
}
function start(){
	var sort  = document.getElementById('sort').value;
	if(sort==""||sort==null){
		Ext.Msg.alert("提示信息", "抽取数量不能为空！");
	　　　　return false; 
	}
	if (!(/(^[1-9]\d*$)/.test(sort))) { 
			Ext.Msg.alert("提示信息", "抽取数量必须是正整数！");
	　　　　return false; 
	}
	
	
	Ext.Msg.wait('请稍候...','系统提示：');
	
	
	radow.doEvent("execute.onclick");
}


function refreshPerson(){
	Ext.Msg.hide();
	
}
</script>