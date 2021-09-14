<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk" />
<META HTTP-EQUIV="Pragma" CONTENT="no-cache">
<META HTTP-EQUIV="Cache-Control" CONTENT="no-cache">
<META HTTP-EQUIV="Expires" CONTENT="0">
<title>全国公务员管理信息系统</title>
<odin:head/>
<script type="text/javascript" src="<%=request.getContextPath()%>/sys/DesktopConfig.js"></script>
</head>
<body style="overflow: hidden;">

<odin:editgrid property="deskConfigGrid" isFirstLoadData="false" url="/" width="388" height="220" title="">
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="id" />
	  	<odin:gridDataCol name="title" />
	  	<odin:gridDataCol name="name" />
	  	<odin:gridDataCol name="orderno" />
	  	<odin:gridDataCol name="isshowback" />
	    <odin:gridDataCol name="isshow" isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn />
		<odin:gridColumn header="标题" width="60" dataIndex="title"/>
		<odin:gridColumn editor="checkbox" edited="true" header="是否显示" align="center" dataIndex="isshow" isLast="true"/>
	</odin:gridColumnModel>
</odin:editgrid>
<table width="100%">
	<tr>
		<td width="280"></td>
		<td><img src="../images/baocun.gif" onclick="saveConfig()"/></td>
	</tr>
</table>
<script type="text/javascript">
odin.ext.onReady(function(){
	loadConfigGridData();
});
</script>
</body>
</html>
