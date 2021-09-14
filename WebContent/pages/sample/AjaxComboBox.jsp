<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK" buffer="100kb"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<link type="text/css"  rel="stylesheet" href="<%=request.getContextPath()%>/css/jquery.ajax-combobox.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery-2.1.4.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/jquery/jquery.ajax-combobox.7.2.1.js"></script>
<title>AjaxComboBox示例 李小宁</title>
</head>
<body>
<table>
<tr>
	<td>
	<odin:textEdit property="area" label="地址" value="浙江省" size="30"></odin:textEdit>
	<odin:hidden property="area_primary_key"/>
	</td>
</tr>
</table>
<script>
$(function(){
	jQuery('#area').ajaxComboBox(
			'<%=request.getContextPath()%>/common/AjaxComboBoxAction.do?method=query&',
			  {
			    per_page : 10,
			    type     : '1' 
			  }
			); 
});
</script>
</body>
</html>