<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@page import="com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_003.CompetenceManagePageModel"%>
<%@page import="com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_004.LogManagePageModel"%>
<style>

.x-grid3-cell-inner, .x-grid3-hd-inner{
white-space:normal !important;
word-break:break-all;
}

</style>
<script  type="text/javascript">
function logExp(){
		
		var username = document.getElementById('searchUserNameBtn').value;
		var type = document.getElementById('searchTypeBtn').value;
		var object = document.getElementById('searchObjectBtn').value;
		var info = document.getElementById('searchInfoBtn').value;
		var start = document.getElementById('start').value;
		var end = document.getElementById('end').value;
		username = encodeURI(encodeURI(username));
		type = encodeURI(encodeURI(type));
		object = encodeURI(encodeURI(object));
		info = encodeURI(encodeURI(info));
		start = encodeURI(encodeURI(start));
		end = encodeURI(encodeURI(end));
		//w = window.location.href("FileDownServlet?method=doPost&username="+username +"&type="+type+"&object="+object+"&info="+info+"&start="+start+"&end="+end);
		//setTimeout("doprocess()",1500);
		
		
		var url="<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.sysmanager.ZWHZYQ_001_004.FileExpWindow&username="+username +"&type="+type+"&object="+object+"&info="+info+"&start="+start+"&end="+end;
   
   		odin.openWindow('fileExpWin','日志备份',url,600,150);
		
		
		//doprocess();
		//Ext.Ajax.request({
		//			url: "FileDownServlet?method=doPost",
		//			params: {username:username,
		//					type:type,
		//					object:object,
		//					info:info,
		//					start:start,
		//					end:end},
		//			success : function(response) {
		//						alert('备份成功！');
		//		                //eval(response.responseText);
		//		            },
		//		    failure:function(){
		//	     		 alert("备份失败！");
		//	         }
		//		});
		
	 /* $.ajax({
	    url: contextPath+"/FileDownServlet",    //请求的url地址
	    dataType: "text",   //返回格式为json
	    async: true, //请求是否异步，默认为异步，这也是ajax重要特性
	    data: { "username":username,"type":type,"object":object,"info":info,"start":start,"end":end },    //参数值
	    type: "post",   //请求方式
	    success: function(req) {

	    },
	    error: function() {
	        alert("失败");
	    }
	});  */
	//odin.alert("正在生成备份文件，请稍后...");
}

function opwin() {
	var win = odin.ext.getCmp('fileImpWin');
	win.setTitle('导入窗口');
	/// win.setSize(500,350); //宽度  高度
	odin.showWindowWithSrc('fileImpWin', contextPath
			+ "/pages/sysmanager/ZWHZYQ_001_004/FileImpWindow.jsp");
}
</script>
<% 
	String users = (String)(new LogManagePageModel().users);
%>

<div id="groupTreeContent" >
<div id="groupTreePanel"></div>
<odin:hidden property="users"/>

<odin:groupBox title="搜索框" property="ggBox">
	<table >
		<tr>
			<odin:select2 property="searchUserNameBtn" editor="true" size="30" data="<%=users%>" label="操作用户"/>
			<odin:select2 property="searchTypeBtn" size="30" label="操作类型" />
			<odin:textEdit property="searchObjectBtn" size="33" label="操作对象"/>
			
		</tr>
		<tr>
			<odin:select2 property="searchInfoBtn" size="30" label="信息集" />
			<odin:dateEdit property="start" readonly="false" size="30" label="开始时间"></odin:dateEdit>
			<odin:dateEdit property="end" readonly="false" size="30" label="结束时间"></odin:dateEdit>
		</tr>
		
	</table>
</odin:groupBox>
<table >
	<tr>
		<td width="50%">
			<odin:editgrid property="memberGrid" hasRightMenu="false" title="日志主信息" autoFill="true"  forceNoScroll="false"  height="380" bbarId="pageToolBar" pageSize="20" isFirstLoadData="false" url="/">
			<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="system_log_id" />
					<odin:gridDataCol name="userlog"/>
					<odin:gridDataCol name="eventtype"/>
					<odin:gridDataCol name="eventobject" />
					<odin:gridDataCol name="objectname" />
					<odin:gridDataCol name="system_operate_date" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridColumn dataIndex="system_log_id" width="110" hidden="true" header="日志主键" align="center"/>
					<odin:gridColumn dataIndex="userlog" editor="select" width="110" header="操作用户" codeType="USER"  align="center"/>
					<odin:gridColumn dataIndex="eventtype" width="190" header="操作类型" align="center" editor="text"/>
					<odin:gridColumn dataIndex="eventobject" width="140" header="信息集" align="center" editor="text"/>
					<odin:gridColumn dataIndex="objectname" header="操作对象" width="80" align="center" editor="text"/>
					<odin:gridColumn dataIndex="system_operate_date" width="130" header="创建时间" align="center" editor="text" isLast="true" renderer="time"/>
				</odin:gridColumnModel>
			</odin:editgrid>
		</td>
		<td width="50%">
			<odin:editgrid property="logGrid" title="日志明细信息"  hasRightMenu="false"   autoFill="true" forceNoScroll="false"    height="380" bbarId="pageToolBar" pageSize="20" isFirstLoadData="false" url="/">
			<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="systemlogdetailid" />
					<odin:gridDataCol name="dataname"/>
					<odin:gridDataCol name="oldvalue"/>
					<odin:gridDataCol name="newvalue" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridColumn dataIndex="dataname" width="150" header="信息项" align="center"/>
					<odin:gridColumn dataIndex="oldvalue" width="300" header="原值" align="center"/>
					<odin:gridColumn dataIndex="newvalue" width="300" header="新值" align="center" isLast="true"/>
				</odin:gridColumnModel>
			</odin:editgrid>
		</td>
	</tr>
</table>
		
</div>

<odin:toolBar property="btnToolBar" applyTo="groupTreePanel">
	<odin:textForToolBar text="<h3>日志管理</h3>" />
	<odin:fill />
	<odin:separator></odin:separator>
	<%-- <odin:buttonForToolBar text="日志归档" id="logSaveBtn" tooltip="用于日志迁移"/> --%>
	<odin:buttonForToolBar text="导出excel" icon="images/icon/exp.png" id="expExcel" handler="expExcel"/>
	<odin:separator></odin:separator>
	<%-- <odin:buttonForToolBar text="日志迁移" icon="images/icon/exp.png" id="logMoveBtn" tooltip="用于日志迁移"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="备份日志" icon="images/mylog.gif" id="logExpBtn" handler="logExp" tooltip="用于日志备份"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="载入日志" icon="images/icon/imp.gif" id="logImpBtn22" handler="opwin" tooltip="用于日志载入"/>
	<odin:separator></odin:separator>  --%>
	<odin:buttonForToolBar text="查询" icon="images/search.gif" id="findLogBtn" tooltip="查询日志" isLast="true"/>
</odin:toolBar>
<!-- 
<odin:panel contentEl="groupTreeContent" property="groupTreePanel" topBarId="btnToolBar"></odin:panel> -->

<odin:window src="/blank.htm" id="ModuleComWin" width="500" height="300" title="模块权限分配页面" modal="true"></odin:window>
<odin:window src="/blank.htm" id="fileImpWin" width="400" height="170" title="日志导入页面" modal="true"></odin:window>		
<odin:window src="/blank.htm" id="logMoveWin" width="430" height="330" title="日志迁移页面" modal="true"></odin:window>

<script type="text/javascript">
function expExcel(){
	odin.grid.menu.expExcelFromGrid('memberGrid', null, null,null, false);
}
Ext.onReady(function() {
	//页面调整
	 Ext.getCmp('logGrid').setHeight((Ext.getBody().getViewSize().height-140));
	 Ext.getCmp('memberGrid').setHeight(Ext.getCmp('logGrid').getHeight());
//	 Ext.getCmp('groupTreePanel').setWidth(document.body.clientWidth);
	 //document.getElementById("groupTreePanel").style.width = document.body.clientWidth;
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

function time(value) {
	var length = value.length;
	
	if(length > 16){
		value  = value.substring(0,19);
	}
	
	return value;
}


function flash() {
	radow.doEvent('memberGrid.dogridquery');
	
}
</script>


