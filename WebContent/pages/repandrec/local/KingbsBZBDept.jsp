<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@include file="/comOpenWinInit.jsp" %>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script src="<sicp3:rewrite forward='globals'/>"> </script>

<script src="<%=request.getContextPath()%>/basejs/odin.js"> </script>
<script src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js" type="text/javascript"></script>
<% 
	String picType = (String)(new SysOrgPageModel().areaInfo.get("picType"));
	String ereaname = (String)(new SysOrgPageModel().areaInfo.get("areaname"));
	String ereaid = (String)(new SysOrgPageModel().areaInfo.get("areaid"));
	String manager = (String)(new SysOrgPageModel().areaInfo.get("manager"));
%>
<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.SysOrgPageModel"%>
<odin:toolBar property="btnToolBar">
	<odin:textForToolBar text=""/>
	<odin:fill/>
	<odin:buttonForToolBar text="确定" id="btnAdd"/>
	<odin:buttonForToolBar  text="关闭"  id="closeBtn" isLast="true"/>
</odin:toolBar>
<div id="panel_content">
<odin:hidden property="ereaname" value="<%=ereaname%>" />
					<odin:hidden property="ereaid" value="<%=ereaid%>" />
					<odin:hidden property="manager" value="<%=manager%>" />
					<odin:hidden property="picType" value="<%=picType%>" />
</div>
<odin:panel contentEl="panel_content" property="mypanel" topBarId="btnToolBar" ></odin:panel>
<odin:hidden property="searchDeptid"/>
	<div>
	<odin:editgrid property="Grid" title="" autoFill="true" bbarId="pageToolBar" pageSize="50" isFirstLoadData="false" url="/">
			<odin:gridJsonDataModel  root="data" totalProperty="totalCount">
			<odin:gridDataCol name="checked"/>
		<odin:gridDataCol name="b0111" />
		<odin:gridDataCol name="b0101" />
		<odin:gridDataCol name="b0114" isLast="true"/>
		
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn></odin:gridRowNumColumn>
		<odin:gridColumn  width="20" editor="checkbox" header="" dataIndex="checked" edited="true"/>
		<odin:gridColumn dataIndex="b0111" width="10" header="机构id" hidden="true" align="left" />
		<odin:gridColumn dataIndex="b0101" width="200" header="机构名称"  align="center" />
		<odin:gridColumn dataIndex="b0114" width="200" header="组织机构编码" isLast="true" align="center" />
	</odin:gridColumnModel>
</odin:editgrid>
	</div>
		
<odin:window src="/blank.htm" id="simpleExpWin" width="560" height="350" maximizable="false" title="窗口"></odin:window>
<odin:window src="/blank.htm" id="deptWin" width="255" height="350" maximizable="false" title="窗口">
</odin:window>
<script>
function reloadTree(){
	setTimeout(xx,1000);
	///window.location.href="ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile));
}
function xx(){
	var downfile = realParent。document.getElementById('downfile').value;
	w = window.open("ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile)));
	setTimeout(cc,3000);
}
function cc()
{
	w.close();
}

</script>