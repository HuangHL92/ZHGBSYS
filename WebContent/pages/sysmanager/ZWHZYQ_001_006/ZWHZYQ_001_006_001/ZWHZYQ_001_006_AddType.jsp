<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8,chrome=1">
<title>新建补充信息类型集</title>
<table width="100%" cellpadding="10" cellspacing="0" border="0"  >
	<tr>
		<td>
			<table  style="margin:10px;">
				<tr>
					<odin:textEdit property="addTypeSequence" required="true" label="序 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号" width="213" maxlength="100"/>
			    </tr>
			</table>
		</td>
	</tr>
	<tr>
		<td>
			<table  style="margin:10px;">
				<tr>
					<td>
					<odin:textEdit property="addTypeName" required="true" label="信息集名称" width="213" maxlength="100"/>
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
					<odin:select2 property="table_code" required="true" label="所在信息集" width="213" maxlength="100"/>
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
			<odin:textarea property="addTypeDesc" cols="36" rows="6" label="信息集描述" maxlength="4000"/>
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
			<odin:button text="&nbsp;保&nbsp;存&nbsp;" property="save"/>
			</td>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td width="10px">
			<odin:button text="&nbsp;取&nbsp;消&nbsp;" property="cancel" handler="cancel"/>
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
