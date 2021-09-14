<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>ҵ�������Ȩ</title>
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
	<odin:buttonForToolBar isLast="true" text="����"  handler="doSave"/>
</odin:toolBar>

<table width="100%">
	<tr>
		<td colspan="2">
			<table cellpadding="0" cellspacing="0" width="98%">
		   	<odin:tabLayOut/>
		   		<tr>
		   			<odin:textEdit property="user" label="�û���" />
		   			<odin:textEdit property="usern" label="�û�����" />
		   			<td>
						<odin:button text="�� ѯ" handler="doQuery"/>
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
	
<odin:hidden property="username"/><!-- �û��� -->
<odin:hidden property="operatorname"/><!-- �û����� -->	
	
</table>

<odin:gridWithPagingTool property="queryList" applyTo="grid1" title="Ȩ���б�" isFirstLoadData="false" rowClick="doClick" autoFill="false" forceNoScroll="false" 
	width="330" height="400" url="/sys/sysoprightacl/SysOpRightAclAction.do?method=intelligentSearchUserInfo" >
	<odin:gridJsonDataModel id="username" root="data" totalProperty="totalCount">
		<odin:gridDataCol name="username" />
	  	<odin:gridDataCol name="operatorname" isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn/>
		<odin:gridColumn  header="�û���" dataIndex="username" width="120"/>
	  	<odin:gridColumn  header="�û�����" dataIndex="operatorname" width="160" isLast="true"/>
	</odin:gridColumnModel>
	<odin:gridJsonData>
		{
			'totalCount':0,
	 		'data':[]	
		}
	</odin:gridJsonData>
</odin:gridWithPagingTool>
	
<odin:editgrid property="list" title="Ȩ���б�" applyTo="grid2" autoFill="false" width="450" beforeedit="doReady" height="400" sm="row">
	<odin:gridDataModel>
		<odin:gridDataCol name="check" />	
		<odin:gridDataCol name="oprightcode" />
 			<odin:gridDataCol name="oprightdesc" isLast="true"/>
	</odin:gridDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn/>
		<odin:gridColumn  header="selectall" gridName="list" dataIndex="check" editor="checkbox" edited="true" width="30"/>
		<odin:gridColumn  header="����Ȩ�ޱ��" dataIndex="oprightcode" width="90"/>
		<odin:gridColumn  header="����Ȩ������" dataIndex="oprightdesc" width="280" isLast="true"/>
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