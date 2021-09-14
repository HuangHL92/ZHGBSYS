<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>业务操作授权</title>
<odin:head/>

<script type="text/javascript" src="<%=request.getContextPath()%>/commform/sysmanager/sysoprightacl/SysOpRightAcl.js"></script>


<odin:MDParam></odin:MDParam>
</head>
<body>
<odin:base>

<odin:form action="/sys/sysoprightacl/SysOpRightAclAction.do?method=save" method="post">

<div id="btnToolBarDiv" style="height:0"></div>
<odin:toolBar property="btnToolBar" applyTo="btnToolBarDiv">
	<odin:textForToolBar text=""/>
	<odin:fill/>
	<odin:opLogButtonForToolBar/>
	<odin:buttonForToolBar isLast="true" text="保存"  handler="doSave"/>
</odin:toolBar>

<table width="100%">
	<tr>
		<td colspan="2">
			<table cellpadding="0" cellspacing="0" width="98%">
		   	<odin:tabLayOut/>
		   		<tr>
		   			<odin:textEdit property="user" label="用户名" />
		   			<odin:textEdit property="usern" label="用户姓名" />
		   			<td>
						<odin:button text="查 询" handler="doQuery"/>
					</td>
		   		</tr>
		   	</table>	
		</td>
	</tr>

	<tr>
		<td width="330" align="left" valign="top">
			<div id="grid1"></div>
		</td>			
		<td align="left" valign="top">
			<div id="grid2"></div>
		</td>
	</tr>
	
<odin:hidden property="username"/><!-- 用户名 -->
<odin:hidden property="operatorname"/><!-- 用户姓名 -->	
	
</table>

<odin:gridWithPagingTool property="queryList" applyTo="grid1" title="权限列表" isFirstLoadData="false" rowClick="doClick" autoFill="false" forceNoScroll="false" 
	width="330" height="400" url="/sys/sysoprightacl/SysOpRightAclAction.do?method=intelligentSearchUserInfo" >
	<odin:gridJsonDataModel id="username" root="data" totalProperty="totalCount">
		<odin:gridDataCol name="username" />
	  	<odin:gridDataCol name="operatorname" isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn/>
		<odin:gridColumn  header="用户名" dataIndex="username" width="120"/>
	  	<odin:gridColumn  header="用户姓名" dataIndex="operatorname" width="160" isLast="true"/>
	</odin:gridColumnModel>
	<odin:gridJsonData>
		{
			'totalCount':0,
	 		'data':[]	
		}
	</odin:gridJsonData>
</odin:gridWithPagingTool>
	
<odin:editgrid property="list" title="权限列表" applyTo="grid2" autoFill="false" width="450" beforeedit="doReady" height="400" sm="row">
	<odin:gridDataModel>
		<odin:gridDataCol name="check" />	
		<odin:gridDataCol name="oprightcode" />
 			<odin:gridDataCol name="oprightdesc" isLast="true"/>
	</odin:gridDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn/>
		<odin:gridColumn  header="selectall" gridName="list" dataIndex="check" editor="checkbox" edited="true" width="30"/>
		<odin:gridColumn  header="操作权限编号" dataIndex="oprightcode" width="90"/>
		<odin:gridColumn  header="操作权限描述" dataIndex="oprightdesc" width="280" isLast="true"/>
	</odin:gridColumnModel>
	<odin:gridJsonData>
		{
	 		data:[]	
		}
	</odin:gridJsonData>
</odin:editgrid>
		
<script>
	Ext.onReady(function(){   
		odin.loadPageGridWithQueryParams("queryList",{searchText:""});	
	});
</script>	
		
</odin:form>
</odin:base>
</body>
</html>