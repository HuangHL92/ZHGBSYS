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
		
		<odin:dateEdit property="start" readonly="false" size="20" label="开始时间"></odin:dateEdit>
		<br>
		<odin:dateEdit property="end" readonly="false" size="20" label="结束时间"></odin:dateEdit>
		<br>
		<span style="font-size:12px">是否同时删除历史日志</span>
		<table>
			<tr>
				
				<td><odin:radio property="isDelete" value="1" label="是" /></td>
				<td><odin:radio property="isDelete" value="0" label="否"/></td>
			</tr>
		</table>
		<br>
		<odin:button text="&nbsp;&nbsp;迁&nbsp;&nbsp;移&nbsp;&nbsp;" handler ="doSave"/>
	</div>
	
	
</body> 




