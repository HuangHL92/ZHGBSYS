<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>ͨ�����</title>
<odin:head/>
<odin:MDParam></odin:MDParam>
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/auditing/AuditingList.js"></script>
</head>

<body style="overflow-y:hidden;overflow-x:hidden">

<odin:base>

<odin:toolBar property="btnToolBar">
	<odin:fill/>
    <odin:opLogButtonForToolBar/>
    <odin:buttonForToolBar text="���" handler="doAudit" icon="../images/i_2.gif" cls="x-btn-text-icon"/>
    <odin:textForToolBar text="" isLast="true"></odin:textForToolBar>
</odin:toolBar>


<odin:gridWithPagingTool  topToolBar="btnToolBar" sm="row" rowDbClick="doRowDClick" property="auditGrid" title="������б�" url="/common/pageQueryAction.do?method=query" isFirstLoadData="false" autoFill="false" forceNoScroll="false" width="795" height="495">
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
	</odin:gridJsonDataModel>                 
	<odin:gridColumnModel>   
	    <odin:gridColumn  header="ѡ��" dataIndex="isselect" editor="checkbox" edited="true"/>
		<odin:gridColumn  header="��־��ˮ��" renderer="renderOpseno" dataIndex="opseno" width="70" hidden="true"/>
		<odin:gridColumn  header="ҵ����ˮ��" dataIndex="seno" width="70" />
	  	<odin:gridColumn  header="ҵ������" dataIndex="functiontitle" width="100" />
	  	<odin:gridColumn  header="ժҪ��Ϣ" renderer="renderDigest" dataIndex="digest" width="340"/>
	  	<odin:gridColumn  header="������" dataIndex="aae011" width="60" />
	  	<odin:gridColumn  header="��������" renderer="doFormatBirthDay" dataIndex="aae036" width="100"/>
	  	<odin:gridColumn  header="��ϸ��Ϣ" renderer="initLookInfo" dataIndex="opseno" isLast="true" width="60"/>
	</odin:gridColumnModel>
</odin:gridWithPagingTool>
</odin:base>
<script type="text/javascript">
Ext.onReady(function(){
	doGetAuditGridData();
});                         
</script>
                                 
</body>
</html>