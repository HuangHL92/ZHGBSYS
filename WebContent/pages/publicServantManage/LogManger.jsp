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
	
	<odin:editgrid2 property="gridAudit" title="审核记录" height="208" isFirstLoadData="false"
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
			<odin:gridEditColumn2 dataIndex="username" header="审核人" menuDisabled="true"
				edited="false" editor="text" align="center"  />
			<odin:gridEditColumn2 dataIndex="adt03" header="审核类型" menuDisabled="true"
				edited="false" editor="text" align="center"  />
			<odin:gridEditColumn2 dataIndex="adt02" header="审核内容" menuDisabled="true"
				edited="false" editor="text" align="center"  />
			<odin:gridEditColumn2 dataIndex="adt01" header="审核时间" menuDisabled="true"
				edited="false" editor="text" align="center" isLast="true" />
		</odin:gridColumnModel>
		<odin:gridJsonData>
			{
		        data:[]
		    }
		</odin:gridJsonData>
	</odin:editgrid2>
	<div style="height: 20px;"></div>
	
	<odin:groupBox title="搜索框">
		<table align="center" width="100%">
			<tr>
				<odin:select data="<%=data1%>" property="userlog" label="操作用户"></odin:select>
				<odin:select data="<%=data2%>" property="eventtype" label="操作类型"></odin:select>
				<odin:select data="<%=data3%>" property="eventobject" label="信息集"></odin:select>
			</tr>
			<tr>
				<odin:dateEdit property="startTime" label="开始时间"></odin:dateEdit>
				<odin:dateEdit property="endTime" label="结束时间"></odin:dateEdit>
				<td></td>
				<td><odin:button text="查询" handler="queryLogMain" property="query"></odin:button></td>
			</tr>
		</table>
	</odin:groupBox>
	
	<odin:editgrid2 property="gridMain" title="修改信息集" height="208" isFirstLoadData="false"
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
			<odin:gridEditColumn2 dataIndex="username" header="修改人" menuDisabled="true"
				edited="false" editor="text" align="center"  />
			<odin:gridEditColumn2 dataIndex="eventtype" header="操作类型" menuDisabled="true"
				edited="false" editor="text" align="center"  />
			<odin:gridEditColumn2 dataIndex="eventobject" header="操作信息集" menuDisabled="true"
				edited="false" editor="text" align="center"  />
			<odin:gridEditColumn2 dataIndex="system_operate_date" header="操作时间" menuDisabled="true"
				edited="false" editor="text" align="center" isLast="true" />
		</odin:gridColumnModel>
		<odin:gridJsonData>
			{
		        data:[]
		    }
		</odin:gridJsonData>
	</odin:editgrid2>
	<odin:editgrid2 property="gridDeatil" title="修改明细" height="208"
		isFirstLoadData="false" clicksToEdit="false" bbarId="pageToolBar" forceNoScroll="true" autoFill="true" pageSize="20">
		<odin:gridJsonDataModel>
			<odin:gridDataCol name="dataname" />
			<odin:gridDataCol name="oldvalue" />
			<odin:gridDataCol name="newvalue" />
			<odin:gridDataCol name="changedatetime" isLast="true"/>
		</odin:gridJsonDataModel>
		<odin:gridColumnModel>
			<odin:gridRowNumColumn />
			<odin:gridEditColumn2 dataIndex="dataname" header="修改信息项"  menuDisabled="true"
				edited="false" editor="text" align="center" />
			<odin:gridEditColumn2 dataIndex="oldvalue" header="旧值"  menuDisabled="true"
				edited="false" editor="text" align="center" />
			<odin:gridEditColumn2 dataIndex="newvalue" header="新值"    menuDisabled="true"
				edited="false" editor="text" align="center" />
			<odin:gridEditColumn2 dataIndex="changedatetime" header="修改时间"  menuDisabled="true"
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
	
	//查询
	function queryLogMain(){
		radow.doEvent("gridMain.dogridquery");
	}
</script>