<%@page import="com.insigma.siis.local.pagemodel.cadremgn.sysmanager.authority.TableColInterface"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@include file="/comOpenWinInit2.jsp" %>
<script type="text/javascript" src="js/lengthValidator.js"></script>
<script type="text/javascript" src="../../basejs/helperUtil.js"></script>
<style>
.x-panel-header{
border: 0px;
}
.x-toolbar span{
	font: bold;
}
</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/customquery.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript">
</script>

<odin:toolBar property="toolBar0" applyTo="toolBar_div" >
	<odin:textForToolBar text="��Ӧҵ������ѡ��"></odin:textForToolBar>
	<odin:fill></odin:fill>
	<odin:buttonForToolBar text="����" id="save" handler="saveData" icon="images/save.gif"></odin:buttonForToolBar>
	<odin:separator isLast="true"></odin:separator>
</odin:toolBar>

<div>
	<div id ="toolBar_div" style="width:100%;"></div>
	<odin:hidden property="a0000"/>
	<odin:hidden property="refBsId"/>
	<table>
   		<tr>
   			<odin:select2 property="refBs" label="����ҵ������" data="['A02','ְ����Ϣ'],['A08','ѧ��ѧλ��Ϣ']" onchange="refreshP2" required="true" />
   		</tr>
	 	<tr>
	 	<td width="600px" colspan="2">
		 	<div id="zwxx" style="display: none;">
				<odin:editgrid2 property="WorkUnitsGrid" load="selectRow" isFirstLoadData="false" height="300" width="600" autoFill="true"  url="/" >
					<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
						<odin:gridDataCol name="a0281"/>
							<odin:gridDataCol name="a0200" />
					  		<odin:gridDataCol name="a0201b" />
					  		<odin:gridDataCol name="a0201a" />
					  		<odin:gridDataCol name="a0215a" />
					  		<odin:gridDataCol name="a0222" />
					   		<odin:gridDataCol name="a0255"/>
					   		<odin:gridDataCol name="a0243"/>
					   		<odin:gridDataCol name="a0245" isLast="true"/>
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
					  <odin:gridRowNumColumn />
						  	<odin:gridEditColumn2 header="id" edited="false" dataIndex="a0200" editor="text" width="10" hidden="true"/>
						  	<odin:gridEditColumn2 header="��ְ��������" edited="false"  dataIndex="a0201b"  editor="text" width="10" hidden="true"/>
						  	<odin:gridEditColumn2 header="��ְ����" edited="false" dataIndex="a0201a"  editor="text" width="200"/>
						  	<odin:gridEditColumn2 header="ְ������" edited="false"  dataIndex="a0215a" editor="text" width="100"/>
						  	<odin:gridEditColumn2 header="��λ���" edited="false"  dataIndex="a0222" editor="text" hidden="true"/>
						  	<odin:gridEditColumn2 header="��ְ״̬" edited="false" dataIndex="a0255"  codeType="ZB14" editor="select" width="100"/>
							<odin:gridEditColumn2 header="��ְʱ��" edited="false"  dataIndex="a0243" editor="text" width="80"/>
						  	<odin:gridEditColumn2 header="��ְ�ĺ�" edited="false"  dataIndex="a0245" editor="text" isLast="true" width="80"/>
					</odin:gridColumnModel>
				</odin:editgrid2>
		 	</div>
			<div id="xwxlxx" style="display: none;">
		 		<odin:editgrid2 property="degreesgrid" load="selectRow" isFirstLoadData="false" autoFill="true" topBarId="toolBar2" url="/"   
			 		height="300" width="600">
					<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
						<odin:gridDataCol name="a0899"/>
			     		<odin:gridDataCol name="a0800" />
				  		<odin:gridDataCol name="a0837" />
				  		<odin:gridDataCol name="a0801b" />
				   		<odin:gridDataCol name="a0901b" />
				   		<odin:gridDataCol name="a0814" />
				   		<odin:gridDataCol name="a0827" />			   		
				   		<odin:gridDataCol name="a0811" />
				   		<odin:gridDataCol name="a0804" />
				   		<odin:gridDataCol name="a0807" />
				   		<odin:gridDataCol name="a0904" />
				   		<odin:gridDataCol name="a0801a" />
				   		<odin:gridDataCol name="a0901a" />
				   		<odin:gridDataCol name="a0824" isLast=""/>
				   		
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
						<odin:gridRowNumColumn />
						<odin:gridEditColumn header="id" dataIndex="a0800" editor="text" edited="false" hidden="true"/>
						<odin:gridEditColumn2 header="���" dataIndex="a0837" codeType="ZB123" edited="false" width="80" editor="select"/>
						<odin:gridEditColumn header="ѧ��" dataIndex="a0801a" edited="false" editor="text" width="80"/>
						<odin:gridEditColumn header="ѧλ" dataIndex="a0901a" edited="false" editor="text" width="80"/>
						<odin:gridEditColumn header="ѧУ��Ժϵ" dataIndex="a0814" edited="false" editor="text" width="80"/>
						<odin:gridEditColumn header="רҵ" dataIndex="a0824" edited="false" editor="text" width="80" />
						<odin:gridEditColumn2 header="��ѧʱ��" edited="false"  dataIndex="a0804" editor="text" width="80" />
						<odin:gridEditColumn2 header="�ϣ��ޣ�ҵʱ��" edited="false"  dataIndex="a0807" editor="text" width="80" isLast="true" />
					</odin:gridColumnModel>
				</odin:editgrid2>
		 	</div>
		</td>
		</tr>
	</table>
</div>
<script type="text/javascript">

function refreshP2(){
	var ref = document.getElementById('refBs').value;
	if(ref == "A02"){
		document.getElementById("xwxlxx").style.display="none";
		document.getElementById("zwxx").style.display="block";
		radow.doEvent('WorkUnitsGrid.dogridquery');
	} else if(ref == "A08"){
		document.getElementById("zwxx").style.display="none";
		document.getElementById("xwxlxx").style.display="block";
		radow.doEvent('degreesgrid.dogridquery');
	}
}
function refreshP(a0000, ref){
	if(ref == "A02"){
		document.getElementById("zwxx").style.display="block";
		document.getElementById("xwxlxx").style.display="none";
		radow.doEvent('WorkUnitsGrid.dogridquery');
	} else if(ref == "A08"){
		document.getElementById("zwxx").style.display="none";
		document.getElementById("xwxlxx").style.display="block";
		radow.doEvent('degreesgrid.dogridquery');
	}
}

function selectRow(){
	var refid = document.getElementById('refBsId').value;
	var ref = document.getElementById('refBs').value;
	var gridname = "";
	var gridnamecol = "";
	if(ref == "A02"){
		gridname = "WorkUnitsGrid";
		gridnamecol = "a0200";
	} else if(ref == "A08"){
		gridname = "degreesgrid";
		gridnamecol = "a0800";
	}
	var grid = Ext.getCmp(gridname);
	var store = grid.store;
	for(var i=0;i<store.getCount();i++){
	    var recored = store.getAt(i);
		if(recored.get(gridnamecol)==refid){
			grid.getSelectionModel().selectRow(i);
		}
	} 
}

function saveData(a0000, ref){
	var refBs = document.getElementById('refBs').value;
	if(refBs == "A02"){
		var row = Ext.getCmp("WorkUnitsGrid").getSelectionModel().getSelected();
		var bsid = row.data.a0200;
		radow.doEvent('saveRef', bsid);
	} else if(refBs == "A08"){
		var row = Ext.getCmp("degreesgrid").getSelectionModel().getSelected();
		var bsid = row.data.a0800;
		radow.doEvent('saveRef', bsid);
	}
}

</script>

<div id="cover_wrap1"></div>
