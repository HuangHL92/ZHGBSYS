<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8,chrome=1">
<title>�½�������Ϣ���ͼ�</title>
<table width="100%" cellpadding="10" cellspacing="0" border="0"  >
	<tr>
		<td>
			<table  style="margin:10px;">
				<tr>
					<odin:textEdit property="addTypeSequence" required="true" label="�� &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��" width="213" maxlength="100"/>
			    </tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<table  style="margin:10px;">
				<tr>
					<td>
					<odin:textEdit property="addTypeName" required="true" label="��Ϣ������" width="213" maxlength="100"/>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<table  style="margin:10px;">
				<tr>
					<td>
					<odin:select2 property="table_code" required="true" label="������Ϣ��" width="213" maxlength="100"/>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
	<td>
	<table  style="margin:10px;">
		<tr>
			<td width="4px">
			<odin:textarea property="addTypeDesc" cols="36" rows="6" label="��Ϣ������" maxlength="4000"/>
			</td>
		</tr>
	</table>
	</td>
	</tr>
	<tr>
	<td>
	<table  style="margin:10px;">
		<tr>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td>
			<odin:button text="&nbsp;��&nbsp;��&nbsp;" property="save"/>
			</td>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td width="10px">
			<odin:button text="&nbsp;ȡ&nbsp;��&nbsp;" property="cancel" handler="cancel"/>
			</td>
		</tr>
	</table>
	</td>
	</tr>
</table>
<script type="text/javascript">

	function cancel(){
        radow.doEvent("close");       	
	}
	
	function propertyValueOnchange(){
		document.all.changeflag.value='true';
	}

</script>
