<%@page import="com.insigma.odin.framework.sys.manager.sysmanager.comm.entity.Sysopright"%>
<%@page import="com.insigma.siis.local.pagemodel.gbwh.GXGAXXLRPageModel"%>
<%@page import="java.util.List"%>
<%@page import="com.insigma.odin.framework.persistence.HBUtil"%>
<%@page import="com.insigma.siis.local.pagemodel.customquery.CommSQL"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@include file="/comOpenWinInit2.jsp" %> 
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="js/lengthValidator.js"></script>

<%String ctxPath = request.getContextPath(); %>

<html>
<head>
<meta charset="ISO-8859-1">
<style>


/*
.th1{width:30px;} .th2{width:70px} .th3{width:90px} .th5{width:30px;} 
#gbxlh {
	width:500px;
	height:275px;
	float:left;
}
#gbth {
	width:500px;
	height:275px;
	float:left;
} 
table{
	width:100%;
	overflow-y:auto;
	rules:all;
	border: 1px solid #C1DAD7;
	frame:box;
	border-collapse: collapse;
}
th,td{
	border-left: 1px solid #C1DAD7;
	border-bottom: 1px solid #C1DAD7;
	font-family: ����;
	font-weight: normal;
	text-align:center;
	height: 50px;
}
th {
	background-color: #CAE8EA;
.divclass{
	width:500px;
	height:275px;
	float:right;
}
} */

</style>
</head>
<body>
<table style="width:1100px;height:600px">
<colgroup>
<col width="55%"/><!-- ���ش��� -->
<col width="5%" />
<col width="40%" />
</colgroup>

	<tr>
	<td width="650px" height="280px">
	<!-- <td  height="280px"> -->
	<odin:toolBar property="ToolBar1">
    <odin:textForToolBar text="<h1 style=color:rgb(21,66,139);size:11px >&nbsp;���ﻰ</h1>" isLast="true"></odin:textForToolBar>
 
<%-- 	<odin:buttonForToolBar text="����" id="checkBtn" icon="image/icon021a2.gif" handler="check" isLast="true"/> --%>
</odin:toolBar>	
			<odin:grid property="xlhgrid" autoFill="false" topBarId="ToolBar1" sm="row"  url="/" 
					 height="250" pageSize="50" >
			<odin:gridJsonDataModel id="id" root="data">
					<odin:gridDataCol name="id" />
					<odin:gridDataCol name="name" />
					<odin:gridDataCol name="xlhtime" />
					<odin:gridDataCol name="xlhtext"  />
					<odin:gridDataCol name="delete" isLast="true"/> 
			</odin:gridJsonDataModel>
			<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>		
					<odin:gridEditColumn header="id" align="center" edited="false" width="20" dataIndex="name" editor="text" hidden="true"/>			
					<odin:gridEditColumn header="������" align="center" edited="false" width="70" dataIndex="name" editor="text" />
					<odin:gridEditColumn header="��¼ʱ��" align="center" edited="false" width="80" dataIndex="xlhtime" editor="text"  />
					<odin:gridEditColumn2 header="���ﻰ����" align="center" edited="false" width="270" dataIndex="xlhtext" editor="text" />
			 		<odin:gridEditColumn header="����" width="50" dataIndex="delete" editor="text" edited="false" renderer="deleteRowRenderer01" isLast="true"/> 
			 </odin:gridColumnModel>
		</odin:grid>
		</td>
		<td style="background:#CAE8EA;"></td>
		<td>
			<table style="width:400px;">
				<tr><td><odin:textEdit property="gbxlhtime" label="��¼ʱ��" ></odin:textEdit></td></tr>
				<tr><td><odin:textarea property="gbxlhtext" label="���ﻰ����" colspan='3' rows="14" cols="64" ></odin:textarea></td></tr>
				
				<tr>
				<td style="margin-right:30px;" colspan="3" >
					<odin:button text="��&nbsp;&nbsp;��" property="add1" handler="add1"></odin:button>
				</td>
				<td colspan="3" align="center">
					<odin:button text="��&nbsp;&nbsp;��" property="save1" handler="save1" />
				</td>
				</tr>
				
				<%-- <tr style="height:0px;"></tr>
				<tr>
				<td style="width:200px;"></td> 
				<td >
					<odin:button text="ˢ&nbsp;&nbsp;��" property="flash1" handler="flash1"></odin:button>
				</td>
				<td style="width:320px;"></td>
				<td>
					<odin:button text="��&nbsp;&nbsp;��" property="save1" handler="save1"></odin:button>
				</td>
				<td style="width:0px;"></td>  --%>
			
			</table>
		</td>
	</tr>
	<tr ><td style="height:20px;width:650px;background:#CAE8EA;"></td>
	<td style="height:20px;width:400px;background:#CAE8EA;"></td>
	<td style="height:20px;width:400px;background:#CAE8EA;"></td></tr>
	<tr>
	<td width="650px" height="280px">
	<odin:toolBar property="ToolBar2">
    <odin:textForToolBar text="<h1 style=color:rgb(21,66,139);size:11px >&nbsp;̸��</h1>" isLast="true"></odin:textForToolBar>
 
<%-- 	<odin:buttonForToolBar text="����" id="checkBtn" icon="image/icon021a2.gif" handler="check" isLast="true"/> --%>
</odin:toolBar>	
			<odin:grid property="thgrid" autoFill="false" topBarId="ToolBar2"  sm="row" isFirstLoadData="false" url="/" 
					 height="250" pageSize="50">
			<odin:gridJsonDataModel id="id" root="data">  
					<odin:gridDataCol name="id" />       
					<odin:gridDataCol name="name" />
					<odin:gridDataCol name="thdx" />
					<odin:gridDataCol name="thtime" />
					<odin:gridDataCol name="thtext" />
					<odin:gridDataCol name="delete" isLast="true"/> 
			</odin:gridJsonDataModel>
			<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>	
					<odin:gridEditColumn header="id" align="center" edited="false" width="20" dataIndex="id" editor="text" hidden="true"/>					
					<odin:gridEditColumn header="������" align="center" edited="false" width="70" dataIndex="name" editor="text"  />		
					<odin:gridEditColumn header="̸������" align="center" edited="false" width="80" dataIndex="thdx" editor="text" />
					<odin:gridEditColumn header="̸��ʱ��" align="center" edited="false" width="80" dataIndex="thtime" editor="text" />
					<odin:gridEditColumn2 width="270" header="̸������" dataIndex="thtext" editor="text" edited="false" align="center" />
			 		<odin:gridEditColumn header="����" width="50" dataIndex="delete" editor="text" edited="false" renderer="deleteRowRenderer02" isLast="true"/> 
			 </odin:gridColumnModel>
		</odin:grid>
		</td>
		<td style="background:#CAE8EA;"></td>
		<td>
			<table style="width:400px;">
			<tr><td><odin:textEdit property="gbthtime" label="��¼ʱ��" ></odin:textEdit>
			<odin:textEdit property="gbthdx" label="̸������"></odin:textEdit></td></tr>
			<tr><td><odin:textarea property="gbthtext" label="̸������" colspan='4' rows="14"></odin:textarea></td></tr>
			
			<tr>
				<td style="margin-right:30px;" colspan="3" >
					<odin:button text="��&nbsp;&nbsp;��" property="add2" handler="add2"></odin:button>
				</td>
				<td  colspan="3" align="right">
					<odin:button text="��&nbsp;&nbsp;��" property="save2" handler="save2" />
				</td> 
				<%-- <td style="width:200px;"></td> 
				<td >
					<odin:button text="ˢ&nbsp;&nbsp;��" property="flash2" handler="flash2"></odin:button>
				</td>
				<td style="width:200px;"></td>
				<td style="width:0px;"></td>
				<td>
					<odin:button text="��&nbsp;&nbsp;��" property="save2" handler="save2"></odin:button>
				</td>
				<td style="width:120px;"></td>  --%>
			</tr>
			</table>
		</td>
		</tr>
</table>



</body>

<odin:hidden property="a0000" title="��Աid"/>
<odin:hidden property="ids" title="������¼id"/>
<odin:hidden property="data01" title="���ﻰ����"/>
<odin:hidden property="id" title="��¼id"/>
<odin:hidden property="data02" title="̸������"/>
<odin:hidden property="gridsize1" title="���ﻰ��������"/>
<odin:hidden property="gridsize2" title="̸����������"/>
<script type="text/javascript">
Ext.onReady(function() {
	document.getElementById('a0000').value = parentParam.a0000;	
	//alert(document.getElementById('a0000').value);
});

function save1(){
	document.getElementById('gridsize1').value = Ext.getCmp("xlhgrid").getStore().getCount();
	radow.doEvent('save01');
	document.getElementById('gbxlhtime').value="";
	document.getElementById('gbxlhtext').value="";
}
function save2(){
	document.getElementById('gridsize2').value = Ext.getCmp("thgrid").getStore().getCount();
	radow.doEvent('save02');
	document.getElementById('gbthtime').value="";
	document.getElementById('gbthtext').value="";
	document.getElementById('gbthdx').value="";
}
function add1(){
	document.getElementById('gbxlhtime').value="";
	document.getElementById('gbxlhtext').value="";
}
function add2(){
	document.getElementById('gbthtime').value="";
	document.getElementById('gbthtext').value="";
	document.getElementById('gbthdx').value="";
}

function deleteRowRenderer01(){
	return "<a href=\"javascript:deletexlhRow()\">ɾ��</a>";
}
function deletexlhRow(){ 
	var sm = Ext.getCmp("xlhgrid").getSelectionModel();
	/* var index=sm.lastActive+1;
	alert(index);	 */
	
	if(!sm.hasSelection()){
		Ext.Msg.alert("ϵͳ��ʾ","��ѡ��һ�����ݣ�");
		return;
	}
	var gridSize = Ext.getCmp("xlhgrid").getStore().getCount();
	/* if(gridSize<=1){
		Ext.Msg.alert("ϵͳ��ʾ","���һ�������޷�ɾ����");
		return;
	} */
	Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ�ȷ��ɾ����",function(id) { 
		if("yes"==id){
			document.getElementById('gbxlhtime').value="";
			document.getElementById('gbxlhtext').value="";
			radow.doEvent('deleteRow01');
			//grid.getSelectionModel().selectRow(index-1,true);
		}else{
		}		
	});	
}

	
function deleteRowRenderer02(){
	return "<a href=\"javascript:deletethRow()\">ɾ��</a>";
}
function deletethRow(){ 
		var sm = Ext.getCmp("thgrid").getSelectionModel();
		/* var index=sm.lastActive+1;
		alert(index);	 */
	
		if(!sm.hasSelection()){
			Ext.Msg.alert("ϵͳ��ʾ","��ѡ��һ�����ݣ�");
			return;
		}
		var gridSize = Ext.getCmp("thgrid").getStore().getCount();
		/* if(gridSize<=1){
			Ext.Msg.alert("ϵͳ��ʾ","���һ�������޷�ɾ����");
			return;
		} */
		Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ�ȷ��ɾ����",function(id) { 
			
			if("yes"==id){
				document.getElementById('gbthtime').value="";
				document.getElementById('gbthtext').value="";
				document.getElementById('gbthdx').value="";
				radow.doEvent('deleteRow02');
				//grid.getSelectionModel().selectRow(index-1,true);
			}else{
			}		
		});	
}

</script>
</html>