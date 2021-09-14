<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="com.insigma.odin.framework.sys.SysfunctionManager"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%
	String functionid = request.getParameter("functionid");
	if (functionid == null)
		functionid = "";
	SysfunctionManager.setModuleSysfunctionidCache(functionid);
%>
<div id='forView_div_1' style='width: 100%;'>
	<div id='gridDiv_div_1'></div>
</div>

<script type="text/javascript" src="<%=request.getContextPath()%>/pages/oplog/WzMDOpLogList.js"></script>

<script>
	parent.wzOpSql = '@_PSItqiEKenPmIrunQ8CCZk6Vjw7rEavsnaCzdBHq7L5Iplx36/HxMcFZkv7S3tu2c4sI8Bie6Z9a0DL5/yYE1KjC3X2L2Q7R_@'
	parent.wzColsInfo = [{"comments":null,"column_name":"PRSENO","data_length":"22","table_name":"AA10","data_type":"NUMBER"},
		{"comments":"代码类别","column_name":"AAA100","data_length":"254","table_name":"AA10","data_type":"VARCHAR2"},
		{"comments":"代码值","column_name":"AAA102","data_length":"254","table_name":"AA10","data_type":"VARCHAR2"},
		{"comments":"代码名称","column_name":"AAA103","data_length":"254","table_name":"AA10","data_type":"VARCHAR2"}];
</script>