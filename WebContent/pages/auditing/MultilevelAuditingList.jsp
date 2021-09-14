<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>通用审核</title>
<odin:head/>
<odin:MDParam></odin:MDParam>
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/auditing/MultilevelAuditingList.js"></script>
</head>

<body style="overflow-y:hidden;overflow-x:hidden">

<odin:base>

<odin:toolBar property="btnToolBar">
	<odin:fill/>
    <odin:opLogButtonForToolBar/>
    <odin:buttonForToolBar text="查询" handler="doQuery" icon="../images/search.gif" cls="x-btn-text-icon"/>
    <odin:buttonForToolBar text="审核" handler="doAudit" icon="../images/i_2.gif" cls="x-btn-text-icon"/>
    <odin:textForToolBar text="" isLast="true"></odin:textForToolBar>
</odin:toolBar>

<span style="position:absolute;top:26;left:1;z-index:1000">
		<table id="simple">
			<tr>
				<td>
					<table width="160">
					<tr>
					<td>
						<table>
						<tr>
						<odin:dateEdit property="querydate1" label="业务经办期" width="100"/>
						<td>-</td>
						<odin:dateEdit property="querydate2" width="100"></odin:dateEdit>
						</tr>
						</table>
					</td>
					<td>
					<odin:textEdit property="querydigest" label="摘要" width="180"></odin:textEdit>
					</td>
						<odin:select property="aae011" label="经办人" codeType="AAE011" maxHeight="120" width="80"/>
					</tr>
					</table>
				</td>
			</tr>
		</table>
		</span>

<odin:gridSelectColJs name="status" codeType="AUDITSTATUS"></odin:gridSelectColJs>
<odin:editgrid topBarId="btnToolBar" bbarId="pageToolbar"  rowDbClick="doRowDClick" property="auditGrid" title="待审核列表" url="/common/pageQueryAction.do?method=query" isFirstLoadData="false" autoFill="false" forceNoScroll="false" width="795" height="495">
	<odin:gridJsonDataModel id="opseno" root="data" totalProperty="totalCount">
		<odin:gridDataCol name="seno" />
	  	<odin:gridDataCol name="functiontitle"/>
	  	<odin:gridDataCol name="digest"/>
	  	<odin:gridDataCol name="aae011"/>
	  	<odin:gridDataCol name="aae036"/>
	  	<odin:gridDataCol name="auflag"/>
	  	<odin:gridDataCol name="opseno" />
	  	<odin:gridDataCol name="functionid" />
	  	<odin:gridDataCol name="isselect" />
	  	<odin:gridDataCol name="status" />
	  	<odin:gridDataCol name="auditdesc" isLast="true"/>
	</odin:gridJsonDataModel>                 
	<odin:gridColumnModel>   
	    <odin:gridColumn  header="选择" dataIndex="isselect" editor="checkbox" edited="true"/>
		<odin:gridColumn  header="日志流水号" renderer="renderOpseno" dataIndex="opseno" width="70" hidden="true"/>
		<odin:gridColumn  header="业务流水号" dataIndex="seno" width="70" />
	  	<odin:gridColumn  header="业务名称" dataIndex="functiontitle" width="100" />
	  	<odin:gridColumn  header="审核结果" dataIndex="status" width="60" editor="select" codeType="AUDITSTATUS" edited="true"/>
	  	<odin:gridColumn  header="审核备注" dataIndex="auditdesc" width="120" editor="text" edited="true"/>
	  	<odin:gridColumn  header="摘要信息" renderer="renderDigest" dataIndex="digest" width="340"/>
	  	<odin:gridColumn  header="经办人" dataIndex="aae011" width="60" />
	  	<odin:gridColumn  header="经办日期" renderer="doFormatBirthDay" dataIndex="aae036" width="100"/>
	  	<odin:gridColumn  header="详细信息" renderer="initLookInfo" dataIndex="opseno" isLast="true" width="60"/>
	</odin:gridColumnModel>
</odin:editgrid>

</odin:base>
<script type="text/javascript">
Ext.onReady(function(){
	doQuery();
});   


</script>
                                 
</body>
</html>