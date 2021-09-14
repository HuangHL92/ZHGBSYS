<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<script type="text/javascript">
 function change() {
	if (document.getElementById('imp').value.indexOf('.log') == -1){
		alert("请选择.log格式的文件");
		return;
	 } 
	document.getElementById('logimp').submit();
	}
</script>

<body>
	<div align="center">
		
		<form action="<%= request.getContextPath()%>/ImpServlet" method="post" name="logimp"  enctype="multipart/form-data" >
			<br>
			<span style="font-size:12px">请选择日志文件：</span><input type="file" name="implog" id="imp"/>
			<input type="submit" style="display:none" value="导入"/>
		</form>
		<!-- <table>
			<tr>
				<td width="90"></td>
				<td>
					
				</td>
			</tr>
		</table> -->
		
		<input type="button" value="&nbsp;&nbsp;导&nbsp;&nbsp;入&nbsp;&nbsp;" onclick="change()">
	</div>
</body>





