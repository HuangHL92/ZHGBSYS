<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>ϵͳ����</title>
<odin:head/>
<odin:MDParam></odin:MDParam>
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/comm/SysCmd.js"></script>
</head>

<body style="overflow-y:hidden;overflow-x:hidden">

<odin:base>

<odin:groupBox title="���������Ϣ">
	<table width="100%"><tr><td>
		<table>
			<tr>
				<odin:textEdit property="command" required="true" label="������" value="SetSuccLevel"></odin:textEdit>
			</tr>
			<tr>
				<odin:textEdit property="url" required="true" label="�����ַ" size="60" value="http://127.0.0.1:8090/insiis"></odin:textEdit>
			</tr>
			<tr>
				<odin:textEdit property="param" label="����ִ�в���" size="100" value="level:0.5"></odin:textEdit>
			</tr>
		</table>
	</td>
	<td>
		<odin:button text="����" handler="sendCmd"></odin:button>
	</td>	
	</tr></table>
</odin:groupBox>
<table width="100%" bgcolor="#dddddd">
  <tr>
  	<td colspan="2" style="font-size: 12px;color: red;">
  	ע�⣺<br>
  	<ol>
  	<li>1�������ַд���硰http://127.0.0.1:8090/insiis��</li>
  	<li>2������д��Ϊ�������Ͳ���ֵ�м��á�:��������������֮���á�;���������硰aab001:10001;aab002:test��</li>
  	</ol>
  	</td>
  </tr>
</table>
</odin:base>
<script type="text/javascript">
Ext.onReady(function(){
	
});                         
</script>                              
</body>
</html>