<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<script type="text/javascript">
 function change() {
	if (document.getElementById('imp').value.indexOf('.log') == -1){
		alert("��ѡ��.log��ʽ���ļ�");
		return;
	 } 
	document.getElementById('logimp').submit();
	}
</script>

<body>
	<div align="center">
		
		<form action="<%= request.getContextPath()%>/ImpServlet" method="post" name="logimp"  enctype="multipart/form-data" >
			<br>
			<span style="font-size:12px">��ѡ����־�ļ���</span><input type="file" name="implog" id="imp"/>
			<input type="submit" style="display:none" value="����"/>
		</form>
		<!-- <table>
			<tr>
				<td width="90"></td>
				<td>
					
				</td>
			</tr>
		</table> -->
		
		<input type="button" value="&nbsp;&nbsp;��&nbsp;&nbsp;��&nbsp;&nbsp;" onclick="change()">
	</div>
</body>





