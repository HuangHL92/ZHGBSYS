<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<div id="panel_content">
<table border="0" id="myform" width="100%"  cellpadding="0" cellspacing="0">
	<tr>
		<td colspan="6" height="10"></td>
	</tr>
   <tr>
     <odin:textEdit property="worker0" label="����1" required="true"/> 
	 <odin:textEdit property="name" label="����2"/>
	 <odin:textEdit property="description" label="����3"/>
   </tr>
   <tr>
		<td colspan="6" height="10"></td>
	</tr>	 
 </table>
</div>
<script>
<odin:menu property="myMenu">
	<odin:menuItem text="ֹͣ" property="stop"></odin:menuItem>
	<odin:menuItem text="����"></odin:menuItem> 
	<odin:menuItem text="ˢ��" isLast="true"></odin:menuItem>
</odin:menu>

var fileNameSim = "txt_test";
var separator = ",";
//var querySQLSim = "select * from ajjb.ac02";
var querySQLSim = "select * from aa10 ";
var uuid="<%=java.util.UUID.randomUUID().toString()%>";
//var fileNameSim = "����Ԥ��"+uuid;
var fileNameSim = "����Ԥ��";
var sheetNameSim = "����Ԥ��";
var withoutHead="true";
function txtExp(){
	//querySQLSim = "select * from aa10 ";
    var win = odin.ext.getCmp('simpleTextExpWin');
    win.setTitle('�ı���������');
    win.setSize(500,350); //���  �߶�
	odin.showWindowWithSrc('simpleTextExpWin',contextPath+'/sys/text/simpleExpTextWindow.jsp');
}


	
	

	function expPersonInfo(){
	    var win = odin.ext.getCmp('simpleExpWin');
	    win.setTitle('excel��������');
	    win.setSize(500,350); //���  �߶�
		odin.showWindowWithSrc('simpleExpWin',contextPath+"/sys/excel/simpleExpExcelWindow.jsp");
	}
	
function impTest(){
	var win = odin.ext.getCmp('simpleExpWin');
	win.setTitle('excel�������');
    win.setSize(500,350); //���  �߶�
	odin.showWindowWithSrc('simpleExpWin',contextPath+"/sys/excel/impExcelWindow.jsp");
}	

//����
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
	<odin:buttonForToolBar text="����excel" id="impBtn" handler="impTest"></odin:buttonForToolBar>
	<odin:buttonForToolBar text="�����ѡ��" id="gsBtn" handler="gridColSelect"></odin:buttonForToolBar>
	<odin:buttonForToolBar text="����excel" id="test2" handler="expPersonInfo"></odin:buttonForToolBar>
	<odin:buttonForToolBar text="����txt" id="texttest" handler="txtExp"></odin:buttonForToolBar>
	<odin:buttonForToolBar text="���Բ˵�" menu="myMenu"></odin:buttonForToolBar>
	<odin:buttonForToolBar text="�����ѯ" id="toolBarBtn3"></odin:buttonForToolBar>
	<odin:buttonForToolBar text="������ʾ��ȷ���Ƿ��ѯ" id="toolBarBtn2"></odin:buttonForToolBar>
	<odin:buttonForToolBar text="��ѯ" id="toolBarBtn1" isLast="true"></odin:buttonForToolBar>
</odin:toolBar>
<odin:panel  property="mypanel" topBarId="toolBar1" contentEl="panel_content"/> 

<odin:toolBar property="toolBar2">
	<odin:fill></odin:fill>
	<odin:buttonForToolBar text="���ӿ���" handler="addEmpRow" isLast="true"></odin:buttonForToolBar>
</odin:toolBar>
<odin:editgrid property="grid6" bbarId="pageToolBar" topBarId="toolBar2" sm="cell" remoteSort="true" hasRightMenu="true" hasAllRightMenu="true" clicksToEdit="false" isFirstLoadData="false" url="/" title="�ɱ༭���" width="780" height="200">
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
  <odin:gridEditColumn header="��Դ����" width="160" dataIndex="name" editor="text"/>
  <odin:gridEditColumn  header="��Դ����" width="160" dataIndex="description" editor="text" />
  <odin:gridEditColumn  dataIndex="lastchange"  isLast="true" editor="date"/>
</odin:gridColumnModel>		
</odin:editgrid>

<odin:grid property="grid7" pageSize="150" bufferView="true" bbarId="pageToolBar" rightMenuId="myMenu" isFirstLoadData="false" url="/" title="��ͨ���" width="780" height="200">
<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
  <odin:gridDataCol name="id" />
  <odin:gridDataCol name="name" />
  <odin:gridDataCol name="description"/>
  <odin:gridDataCol name="lastChange" isLast="true"/>
</odin:gridJsonDataModel>
<odin:gridColumnModel>
  <odin:gridRowNumColumn />
  <odin:gridColumn header="ʱ��" width="160" dataIndex="time" editor="date" edited="true"/>
  <odin:gridColumn header="��Դ����" width="160" dataIndex="name" editor="text"/>
  <odin:gridColumn  header="��Դ����" width="260" dataIndex="description" editor="text" />
  <odin:gridColumn  dataIndex="lastChange" width="260"  isLast="true" editor="date"/>
  
</odin:gridColumnModel>		
</odin:grid>
<odin:window src="/blank.htm" id="simpleTextExpWin" width="500" height="350" title="���ڲ���"></odin:window>
<odin:window src="/blank.htm" id="simpleExpWin" width="500" height="350" title="���ڲ���"></odin:window>
<odin:window src="/blank.htm" id="win1" width="500" height="350" title="���ڲ���"></odin:window>
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
