<%@page import="com.insigma.siis.local.pagemodel.jzsp.SPUtil"%>
<%@page import="com.insigma.siis.local.business.helperUtil.SysManagerUtils"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<%@include file="/comOpenWinInit2.jsp" %>
<%
String sp0100 = request.getParameter("sp0100");
String ctxPath = request.getContextPath();
String curUser = SysManagerUtils.getUserId();
String curGroup = SysManagerUtils.getUserGroupid();
String url = ctxPath + "/radowAction.do?method=doEvent&pageModel=pages.jzsp.jgld.ViewJGLD";
String[] spInfo = SPUtil.getSPInfo(sp0100);
if(spInfo!=null){
	if("user".equals(spInfo[0])&&curUser.equals(spInfo[1])){//审批用户为当前用户
		url = ctxPath + "/radowAction.do?method=doEvent&pageModel=pages.jzsp.jgld.AddJGLD";
	}else if("group".equals(spInfo[0])&&curGroup!=null&&curGroup.equals(spInfo[1])){
		url = ctxPath + "/radowAction.do?method=doEvent&pageModel=pages.jzsp.jgld.AddJGLD";
	}
}

%>
<style>

</style>
<div id="divapproves">
<odin:hidden property="sp0100" />
<odin:editgrid property="gridcq" height="600" pageSize="9999" forceNoScroll="true" autoFill="true" >
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="spbl02" />
		<odin:gridDataCol name="spbl05" />
		<odin:gridDataCol name="spbl08" />
		<odin:gridDataCol name="spbl06" isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn></odin:gridRowNumColumn>
		<odin:gridColumn dataIndex="spbl02" header="用户名" width="100" align="center" />
		<odin:gridEditColumn2 dataIndex="spbl06" header="审批情况" width="100" align="center" editor="text" edited="false" />
		<odin:gridColumn dataIndex="spbl05" edited="false" header="操作时间" width="100"  align="center"/>
		<odin:gridColumn dataIndex="spbl08" edited="false" header="备注" width="100"  align="center"  isLast="true" />
		
	</odin:gridColumnModel>
	<odin:gridJsonData>
		{
	        data:[]
	    }
	</odin:gridJsonData>
</odin:editgrid>
</div>

<script type="text/javascript">



Ext.onReady(function() {
	
	if(typeof parentParam!='undefined'&&typeof parentParam.sp0100!='undefined'){
		document.getElementById('sp0100').value = parentParam.sp0100;
	}
		
	
	var viewSize = Ext.getBody().getViewSize();
	var gridcq = Ext.getCmp("gridcq");
	
	gridcq.setWidth(viewSize.width-20);
	var tabs = new Ext.TabPanel({
		id:'ApprTabs',
		title:'helloal',
		region: 'center',
		margins: '0 3 0 0',
		activeTab: 0,
		//applyTo:'rmbtabsdiv',
		enableTabScroll: true,
		autoScroll:false,
		frame : true,
		//plugins: new Ext.ux.TabCloseMenu(),
		listeners:{
		   'tabchange':function(obj1,obj2){
			}
		},
		items: [{
			autoScroll:true,
			id:"BusinessInfo",
			title : '业务信息',
			html: '<Iframe width="100%" height="100%" scrolling="auto" id="IBusinessInfo" frameborder="0" src="<%=url%>"></Iframe>'
			
		},{
			autoScroll:true,
			id:"approves",
			title : '审批详情',
			contentEl:'divapproves'
		}]
	}); 
	var viewport = new Ext.Viewport({
		layout: 'border',
		items: [tabs]
	});
	
	
	
});

</script>


