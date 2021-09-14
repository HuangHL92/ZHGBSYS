<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8,chrome=1">
<table style="width:100%;cellpading:10;cellspacing:0;border:0;margin-top:5px" >
	<tr>
		<td>
			<table  style="margin:5px;">
			<tr>
			<td>
			<odin:textEdit property="codeName" required="true" label="请输入" width="213" maxlength="100"  validator="check" invalidText="注:名称不能为空"/>
			</td>
		    </tr>
			</table>
		</td>
	</tr>
	<tr>
	<td>
	<table  style="margin-top:5px;">
		<tr>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td>&nbsp;&nbsp;&nbsp;</td>
			<td>
			<odin:button text="&nbsp;确&nbsp;定&nbsp;" property="save"/>
			</td>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
			<td>
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
	
	Ext.onReady(function(){
		
	}, this, {
		delay : 500
	});

	function check() {
		var codeName = document.all.codeName.value;
		if(""==codeName.trim()) {
			return false;
		} else {
			return true;
		}
	}
</script>
