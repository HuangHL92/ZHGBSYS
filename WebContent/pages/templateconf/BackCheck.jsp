<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%String ctxPath = request.getContextPath();%>
<%@page import="com.insigma.siis.local.pagemodel.templateconf.BackCheckPageModel"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="js/lengthValidator.js"></script>
<%@include file="/comOpenWinInit2.jsp" %>
<%
 String sql1 = request.getParameter("initParams");
System.out.println(sql1);
%>

<script type="text/javascript">
   

</script>
<odin:hidden property="sqlz"/>
<odin:head></odin:head>
<odin:base></odin:base>
<odin:hidden property="sql" /> 
<div id="border">
<div id="tol2" align="left"></div>
			<odin:editgrid2  property="TrainingInfoGrid" height="500" title="" autoFill="true" bbarId="pageToolBar" sm="row" isFirstLoadData="false" pageSize="20"  url="/" >
				<odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="id"  />
					<odin:gridDataCol name="a0000"  />
					<odin:gridDataCol name="a0101" />
 			  		<odin:gridDataCol name="a0104"  /> 
 			  		<odin:gridDataCol name="a0107"  /> 
 			  		<odin:gridDataCol name="a0114a"  /> 
 			  		<odin:gridDataCol name="a0111a"  /> 
			  		<odin:gridDataCol name="a0192" isLast="true"/> 
				</odin:gridJsonDataModel>
					<odin:gridColumnModel>
				    <odin:gridRowNumColumn></odin:gridRowNumColumn>
				  	<odin:gridEditColumn2 header="主键" dataIndex="id" editor="text" edited="false" width="20" hidden="true"/>
				  	<odin:gridEditColumn2 header="人员id" dataIndex="a0000" editor="text" edited="false" width="20" hidden="true"/>
				  	<odin:gridEditColumn2 header="姓名" align="center" dataIndex="a0101" editor="text" edited="false"  width="50"/>
				  	<odin:gridEditColumn2 header="性别" align="center" dataIndex="a0104" editor="text" edited="false"  width="20" /> 
 				  	<odin:gridEditColumn2 header="出生日期" align="center" dataIndex="a0107" editor="text" edited="false" width="60"/> 
 				  	<odin:gridEditColumn2 header="出生地" align="center" dataIndex="a0114a" editor="text" edited="false" width="50" />
 				  	<odin:gridEditColumn2 header="籍贯" align="center" dataIndex="a0111a" editor="text" edited="false" width="50"/>
				  	<odin:gridEditColumn2 width="100" header="职务" dataIndex="a0192" editor="text" edited="false"   isLast="true"/>
					</odin:gridColumnModel>
			</odin:editgrid2 >
</div>
<script type="text/javascript">

/* Ext.onReady(function(){
  var vwin = window.dialogArguments; //得到window参数
  var doc = vwin.document.getElementById("sql").value; //获得父页面的值
  //alert(doc);
  document.getElementById("sqlz").value=doc;
	
}); */

</script>
<style>

</style>
