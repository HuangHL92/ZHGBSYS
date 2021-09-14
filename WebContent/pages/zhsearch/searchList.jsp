<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ include file="/comOpenWinInit2.jsp" %>
<%
	String ctxPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" charset="GBK" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
<title>查询列表</title>
</head>
<script type="text/javascript">var ctx_path = "<%=ctxPath%>";</script>
<link rel="stylesheet" type="text/css" href="<%=ctxPath%>/css/odin.css" />
<link rel="stylesheet" type="text/css" href="<%=ctxPath%>/css/templete/templete-default.css" />
<script type="text/javascript" src="<%=ctxPath%>/basejs/jquery/jquery-1.12.4.min.js"></script>
<script type="text/javascript" src="<%=ctxPath%>/js/console.js"></script>
<script type="text/javascript" src="<%=ctxPath%>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=ctxPath%>/basejs/pageUtil.js"></script>
<body>
		<div id="girdDiv" style="width: 100%;margin:0px 0px 0px 0px;" >     
			<odin:editgrid property="searchListGrid" title="" autoFill="false" width="100%" bbarId="pageToolBar" pageSize="20" isFirstLoadData="false" url="/" >
				<odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="sjly" />
					<odin:gridDataCol name="queryid" />
					<odin:gridDataCol name="querysql" />
					<odin:gridDataCol name="queryname" isLast="true" />		
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridEditColumn header="数据来源" edited="false" align="center"  dataIndex="sjly" width="100" editor="text" ></odin:gridEditColumn>
					<odin:gridEditColumn header="主键"  dataIndex="queryid" align="center" editor="text" width="100" hidden="true" edited="false"/>
					<odin:gridEditColumn header="sql语句" dataIndex="querysql" align="center" edited="false" hidden="true" editor="text" width="150" />
					<odin:gridEditColumn header="方案名称" dataIndex="queryname" align="center" edited="false" editor="text" width="125"  />
					<odin:gridEditColumn header="操作" editor="text" dataIndex="queryid" renderer="dodetail" align="center" width="150" isLast="true" />
				</odin:gridColumnModel>
				</odin:editgrid>
		</div>
		<div style="display: none;">
		<iframe id="iframeCondition" width="100%" height="500px" src="<%=request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.cadremgn.sysmanager.ViewCondition&paramurl=UserDefinedQuery" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" ></iframe>
		</div>
		
		<div id="tree-div" style="overflow: auto;  border: 2px solid #c3daf9; height: 200px;display: none;"></div>
		<odin:hidden property="codevalueparameter" />
		<odin:hidden property="SysOrgTreeIds" value="{}"/>
			
		
		
		
	<odin:hidden property="sql"/>
	<odin:hidden property="qvid"/>
	<odin:hidden property="queryName" />
	<odin:hidden property="qrysql" />
	<script type="text/javascript">
		//关闭窗口
		function closeWin(){
			window.close();
			}

		//操作按钮
		 function dodetail(value, params, rs, rowIndex, colIndex, ds){
				return "<a href=\"javascript:deleteV('" + value + "')\">删除</a>&nbsp;"
			}
		//删除
		function deleteV(value){
			radow.doEvent('doDel',value);
			
		}
				
		 


			
	</script>
</body>
</html>