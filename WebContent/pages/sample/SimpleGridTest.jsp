<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<div id="panel_content">
<table border="0" id="myform" width="100%"  cellpadding="0" cellspacing="0">
	<tr>
		<td colspan="6" height="10"></td>
	</tr>
   <tr>
     <odin:textEdit property="worker0" label="条件1" required="true"/> 
	 <odin:textEdit property="name" label="条件2"/>
	 <odin:textEdit property="description" label="条件3"/>
   </tr>
   <tr>
		<td colspan="6" height="10"></td>
	</tr>	 
 </table>
</div>
<script>
<odin:menu property="myMenu">
	<odin:menuItem text="停止" property="stop"></odin:menuItem>
	<odin:menuItem text="运行"></odin:menuItem> 
	<odin:menuItem text="刷新" isLast="true"></odin:menuItem>
</odin:menu>

var fileNameSim = "txt_test";
var separator = ",";
//var querySQLSim = "select * from ajjb.ac02";
var querySQLSim = "select * from aa10 ";
var uuid="<%=java.util.UUID.randomUUID().toString()%>";
//var fileNameSim = "到龄预测"+uuid;
var fileNameSim = "到龄预测";
var sheetNameSim = "到龄预测";
var withoutHead="true";
function txtExp(){
	//querySQLSim = "select * from aa10 ";
    var win = odin.ext.getCmp('simpleTextExpWin');
    win.setTitle('文本导出测试');
    win.setSize(500,350); //宽度  高度
	odin.showWindowWithSrc('simpleTextExpWin',contextPath+'/sys/text/simpleExpTextWindow.jsp');
}


	
	

	function expPersonInfo(){
	    var win = odin.ext.getCmp('simpleExpWin');
	    win.setTitle('excel导出测试');
	    win.setSize(500,350); //宽度  高度
		odin.showWindowWithSrc('simpleExpWin',contextPath+"/sys/excel/simpleExpExcelWindow.jsp");
	}
	
function impTest(){
	var win = odin.ext.getCmp('simpleExpWin');
	win.setTitle('excel导入测试');
    win.setSize(500,350); //宽度  高度
	odin.showWindowWithSrc('simpleExpWin',contextPath+"/sys/excel/impExcelWindow.jsp");
}	

//测试
function test2(){
	var s = odin.ext.getCmp("grid6").store;
	alert(s.getAt(document.getElementById("worker0").value).get('name'));
}

function gridColSelect(){
	var colDataIndexs=radow.getGridVisibleCol("grid6");
}

function addEmpRow(){
	radow.addGridEmptyRow("grid6",0);
}
</script>

<odin:toolBar property="toolBar1">
	<odin:fill></odin:fill>
	<odin:buttonForToolBar text="导入excel" id="impBtn" handler="impTest"></odin:buttonForToolBar>
	<odin:buttonForToolBar text="表格列选择" id="gsBtn" handler="gridColSelect"></odin:buttonForToolBar>
	<odin:buttonForToolBar text="导出excel" id="test2" handler="expPersonInfo"></odin:buttonForToolBar>
	<odin:buttonForToolBar text="导出txt" id="texttest" handler="txtExp"></odin:buttonForToolBar>
	<odin:buttonForToolBar text="测试菜单" menu="myMenu"></odin:buttonForToolBar>
	<odin:buttonForToolBar text="特殊查询" id="toolBarBtn3"></odin:buttonForToolBar>
	<odin:buttonForToolBar text="弹出提示框确认是否查询" id="toolBarBtn2"></odin:buttonForToolBar>
	<odin:buttonForToolBar text="查询" id="toolBarBtn1" isLast="true"></odin:buttonForToolBar>
</odin:toolBar>
<odin:panel  property="mypanel" topBarId="toolBar1" contentEl="panel_content"/> 

<odin:toolBar property="toolBar2">
	<odin:fill></odin:fill>
	<odin:buttonForToolBar text="增加空行" handler="addEmpRow" isLast="true"></odin:buttonForToolBar>
</odin:toolBar>
<odin:editgrid property="grid6" bbarId="pageToolBar" topBarId="toolBar2" sm="cell" remoteSort="true" hasRightMenu="true" hasAllRightMenu="true" clicksToEdit="false" isFirstLoadData="false" url="/" title="可编辑表格" width="780" height="200">
<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
  <odin:gridDataCol name="check" />
  <odin:gridDataCol name="id" />
  <odin:gridDataCol name="name" />
  <odin:gridDataCol name="description"/>
  <odin:gridDataCol name="lastchange" isLast="true"/>
</odin:gridJsonDataModel>
<odin:gridColumnModel>
  <odin:gridRowNumColumn />
  <odin:gridColumn header="selectall"   gridName="grid6" dataIndex="check" edited="true" width="8" editor="checkbox"/>
  <odin:gridEditColumn header="资源名称" width="160" dataIndex="name" editor="text"/>
  <odin:gridEditColumn  header="资源描述" width="160" dataIndex="description" editor="text" />
  <odin:gridEditColumn  dataIndex="lastchange"  isLast="true" editor="date"/>
</odin:gridColumnModel>		
</odin:editgrid>

<odin:grid property="grid7" pageSize="150" bufferView="true" bbarId="pageToolBar" rightMenuId="myMenu" isFirstLoadData="false" url="/" title="普通表格" width="780" height="200">
<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
  <odin:gridDataCol name="id" />
  <odin:gridDataCol name="name" />
  <odin:gridDataCol name="description"/>
  <odin:gridDataCol name="lastChange" isLast="true"/>
</odin:gridJsonDataModel>
<odin:gridColumnModel>
  <odin:gridRowNumColumn />
  <odin:gridColumn header="时间" width="160" dataIndex="time" editor="date" edited="true"/>
  <odin:gridColumn header="资源名称" width="160" dataIndex="name" editor="text"/>
  <odin:gridColumn  header="资源描述" width="260" dataIndex="description" editor="text" />
  <odin:gridColumn  dataIndex="lastChange" width="260"  isLast="true" editor="date"/>
  
</odin:gridColumnModel>		
</odin:grid>
<odin:window src="/blank.htm" id="simpleTextExpWin" width="500" height="350" title="窗口测试"></odin:window>
<odin:window src="/blank.htm" id="simpleExpWin" width="500" height="350" title="窗口测试"></odin:window>
<odin:window src="/blank.htm" id="win1" width="500" height="350" title="窗口测试"></odin:window>
<script type="text/javascript">
<!--
odin.ext.onReady(function(){
	odin.ext.get("worker0").on("paste",function(e){
		if(window.clipboardData){
			alert(window.clipboardData.getData('Text'));
		}else{
			alert(e.browserEvent.clipboardData.getData("text/plain"));
		}
	});
});
//-->
</script>
