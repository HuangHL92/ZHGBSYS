<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@include file="/comOpenWinInit.jsp" %>
<%
	String initParams=request.getParameter("initParams");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>配备状况</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script src="<%=request.getContextPath()%>/basejs/ext/ext-all.js"> </script>
<script src="<%=request.getContextPath()%>/basejs/ext/ext-lang-zh_CN-GBK.js"> </script>
<script src="<%=request.getContextPath()%>/basejs/odin.js"> </script>
<script src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/js/cllauth.js"> </script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/pingyin.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/basejs/jquery-ui/jquery-1.10.2.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/basejs/ext/resources/css/ext-all.css"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript">
	$(function(){
		$('#unid').val('<%=initParams%>');
	});
	
	function confirm(){
		 var grid = odin.ext.getCmp('pbzk');
		 var store = grid.store;
		 var i = store.getCount()-1;
		 var count=0;
		 var names="";	
		 if (store.getCount() > 0) {
				for (var i = store.getCount() - 1; i >= 0; i--) {
					var ck = grid.getStore().getAt(i).get("logchecked");
					if (ck == true) {
						count++;
						names += grid.getStore().getAt(i).get("a0101") + ",";
					}
				}
			}
		 names=names.substring(0,names.length-1);
		 radow.doEvent("confirm",names);
		 
	}
	
	function close(){
		 radow.doEvent("close");
	}
</script>
</head>
<body>
	<odin:hidden property="unid"/>
	 <odin:editgrid2 property="pbzk" bbarId="pageToolBar" autoFill="true" url="/" pageSize="10" isFirstLoadData="false"  remoteSort="false"
					 height="document.body.clientHeight-30" rowDbClick="doRowDbClick" sm="row" >
			
		<odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
			<odin:gridDataCol name="logchecked" />
			<odin:gridDataCol name="a0000" />
			<odin:gridDataCol name="a0101" />
			<odin:gridDataCol name="a0192" />
		</odin:gridJsonDataModel>
		<odin:gridColumnModel>
			<odin:gridRowNumColumn></odin:gridRowNumColumn>
			<odin:gridColumn dataIndex="logchecked" header="selectall" gridName="pbzk" edited="true" width="8" editor="checkbox"></odin:gridColumn>
			<odin:gridEditColumn header="人员id" dataIndex="a0000" align="center" edited="false"  hidden="true"
				 editor="text"/>
			<odin:gridEditColumn header="姓名" dataIndex="a0101"  align="center" edited="false" hidden="false"
				 editor="text"/>
			<odin:gridEditColumn header="职务" dataIndex="a0192" align="center" edited="false" 
				 editor="text" isLast="true"/>

		</odin:gridColumnModel>
	</odin:editgrid2>
	<div id="toolbardiv"></div>
	
	<odin:toolBar property="toolBar2" applyTo="toolbardiv">
			<odin:buttonForToolBar text="确定" handler="confirm"></odin:buttonForToolBar>
			<odin:separator></odin:separator>
			<odin:buttonForToolBar text="关闭" handler="close"></odin:buttonForToolBar>
	
			<odin:fill isLast="true"></odin:fill>
	</odin:toolBar>
</body>
</html>