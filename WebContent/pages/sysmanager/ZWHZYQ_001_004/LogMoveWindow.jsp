<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<script type="text/javascript">
function doSave()
{
	radow.doEvent('doSave');     
}

</script>

<body>
	<div align="center">
		<div style="visibility: hidden;">
	<odin:radio property="isDelete" value="10" disabled="true"/>
	</div>
		
		<odin:dateEdit property="start" readonly="false" size="20" label="��ʼʱ��"></odin:dateEdit>
		<br>
		<odin:dateEdit property="end" readonly="false" size="20" label="����ʱ��"></odin:dateEdit>
		<br>
		<span style="font-size:12px">�Ƿ�ͬʱɾ����ʷ��־</span>
		<table>
			<tr>
				
				<td><odin:radio property="isDelete" value="1" label="��" /></td>
				<td><odin:radio property="isDelete" value="0" label="��"/></td>
			</tr>
		</table>
		<br>
		<odin:button text="&nbsp;&nbsp;Ǩ&nbsp;&nbsp;��&nbsp;&nbsp;" handler ="doSave"/>
	</div>
	
	
</body> 




