<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ page isELIgnored="false"%>
<%@ page import="com.insigma.siis.local.pagemodel.publicServantManage.LogMangerPageModel"%>

<head>
	<style type="text/css">
		body {
			background-color: rgb(214,227,243);
		}
		
		#panel_content{
			margin-top: 25px;
		}
		
	</style>
	<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
</head>

<body>
	<% 
		LogMangerPageModel lmp = new LogMangerPageModel();
		String data1 = lmp.getSelectData("1");
		String data2 = lmp.getSelectData("2");
		String data3 = lmp.getSelectData("3");
	%>
	
	<odin:hidden property="a0000"/>
	<odin:hidden property="system_log_id"/>
	

	<div id="panel_content" align="center">
		<div></div>
	</div>
	
	<odin:editgrid2 property="gridAudit" title="��˼�¼" height="208" isFirstLoadData="false"
		clicksToEdit="false"  autoFill="true" forceNoScroll="true" pageSize="200">
		<odin:gridJsonDataModel>
			<odin:gridDataCol name="adt00" />
			<odin:gridDataCol name="adt01" />
			<odin:gridDataCol name="userid" />
			<odin:gridDataCol name="username" />
			<odin:gridDataCol name="adt02" />
			<odin:gridDataCol name="adt03" isLast="true"/>
		</odin:gridJsonDataModel>
		<odin:gridColumnModel>
			<odin:gridRowNumColumn />
			<odin:gridEditColumn2 dataIndex="username" header="�����" menuDisabled="true"
				edited="false" editor="text" align="center"  />
			<odin:gridEditColumn2 dataIndex="adt03" header="�������" menuDisabled="true"
				edited="false" editor="text" align="center"  />
			<odin:gridEditColumn2 dataIndex="adt02" header="�������" menuDisabled="true"
				edited="false" editor="text" align="center"  />
			<odin:gridEditColumn2 dataIndex="adt01" header="���ʱ��" menuDisabled="true"
				edited="false" editor="text" align="center" isLast="true" />
		</odin:gridColumnModel>
		<odin:gridJsonData>
			{
		        data:[]
		    }
		</odin:gridJsonData>
	</odin:editgrid2>
	<div style="height: 20px;"></div>
	
	<odin:groupBox title="������">
		<table align="center" width="100%">
			<tr>
				<odin:select data="<%=data1%>" property="userlog" label="�����û�"></odin:select>
				<odin:select data="<%=data2%>" property="eventtype" label="��������"></odin:select>
				<odin:select data="<%=data3%>" property="eventobject" label="��Ϣ��"></odin:select>
			</tr>
			<tr>
				<odin:dateEdit property="startTime" label="��ʼʱ��"></odin:dateEdit>
				<odin:dateEdit property="endTime" label="����ʱ��"></odin:dateEdit>
				<td></td>
				<td><odin:button text="��ѯ" handler="queryLogMain" property="query"></odin:button></td>
			</tr>
		</table>
	</odin:groupBox>
	
	<odin:editgrid2 property="gridMain" title="�޸���Ϣ��" height="208" isFirstLoadData="false"
		clicksToEdit="false" bbarId="pageToolBar" autoFill="true" forceNoScroll="true" pageSize="20">
		<odin:gridJsonDataModel>
			<odin:gridDataCol name="system_log_id" />
			<odin:gridDataCol name="username" />
			<odin:gridDataCol name="eventtype" />
			<odin:gridDataCol name="eventobject" />
			<odin:gridDataCol name="system_operate_date" isLast="true"/>
		</odin:gridJsonDataModel>
		<odin:gridColumnModel>
			<odin:gridRowNumColumn />
			<odin:gridEditColumn2 dataIndex="system_log_id" header="ID" menuDisabled="true"
				edited="false" editor="text" align="center" hidden="true" />
			<odin:gridEditColumn2 dataIndex="username" header="�޸���" menuDisabled="true"
				edited="false" editor="text" align="center"  />
			<odin:gridEditColumn2 dataIndex="eventtype" header="��������" menuDisabled="true"
				edited="false" editor="text" align="center"  />
			<odin:gridEditColumn2 dataIndex="eventobject" header="������Ϣ��" menuDisabled="true"
				edited="false" editor="text" align="center"  />
			<odin:gridEditColumn2 dataIndex="system_operate_date" header="����ʱ��" menuDisabled="true"
				edited="false" editor="text" align="center" isLast="true" />
		</odin:gridColumnModel>
		<odin:gridJsonData>
			{
		        data:[]
		    }
		</odin:gridJsonData>
	</odin:editgrid2>
	<odin:editgrid2 property="gridDeatil" title="�޸���ϸ" height="208"
		isFirstLoadData="false" clicksToEdit="false" bbarId="pageToolBar" forceNoScroll="true" autoFill="true" pageSize="20">
		<odin:gridJsonDataModel>
			<odin:gridDataCol name="dataname" />
			<odin:gridDataCol name="oldvalue" />
			<odin:gridDataCol name="newvalue" />
			<odin:gridDataCol name="changedatetime" isLast="true"/>
		</odin:gridJsonDataModel>
		<odin:gridColumnModel>
			<odin:gridRowNumColumn />
			<odin:gridEditColumn2 dataIndex="dataname" header="�޸���Ϣ��"  menuDisabled="true"
				edited="false" editor="text" align="center" />
			<odin:gridEditColumn2 dataIndex="oldvalue" header="��ֵ"  menuDisabled="true"
				edited="false" editor="text" align="center" />
			<odin:gridEditColumn2 dataIndex="newvalue" header="��ֵ"    menuDisabled="true"
				edited="false" editor="text" align="center" />
			<odin:gridEditColumn2 dataIndex="changedatetime" header="�޸�ʱ��"  menuDisabled="true"
				edited="false" editor="text" align="center" hidden="true" isLast="true"/>
		</odin:gridColumnModel>
		<odin:gridJsonData>
			{
		        data:[]
		    }
		</odin:gridJsonData>
	</odin:editgrid2>
</body>

<script>
	Ext.onReady(function(){  
		Ext.getCmp('gridMain').setWidth((Ext.isIE?Ext.getBody().getViewSize().width:window.innerWidth)-objTop(document.getElementById('forView_gridMain'))[1]-22); 
		Ext.getCmp('gridAudit').setWidth((Ext.isIE?Ext.getBody().getViewSize().width:window.innerWidth)-objTop(document.getElementById('forView_gridAudit'))[1]-22); 
		Ext.getCmp('gridDeatil').setWidth((Ext.isIE?Ext.getBody().getViewSize().width:window.innerWidth)-objTop(document.getElementById('forView_gridDeatil'))[1]-22); 
		
		document.getElementById("a0000").value = window.parent.document.getElementById("a0000").value;
	});  
	
	
	function objTop(obj){
	    var tt = obj.offsetTop;
	    var ll = obj.offsetLeft;
	    while(true){
	    	if(obj.offsetParent){
	    		obj = obj.offsetParent;
	    		tt+=obj.offsetTop;
	    		ll+=obj.offsetLeft;
	    	}else{
	    		return [tt,ll];
	    	}
		}
	    return tt;  
	}
	
	//��ѯ
	function queryLogMain(){
		radow.doEvent("gridMain.dogridquery");
	}
</script>