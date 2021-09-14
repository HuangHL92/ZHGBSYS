<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<%@include file="/comOpenWinInit.jsp" %>
<odin:hidden property="sublibrariesmodelid"/>
<odin:hidden property="personq"/>
<odin:hidden property="mllb"/>
<odin:hidden property="LWflag"/>

	<style>
#ext-gen242 {
	width: 550px !important;
	herght: 475px !important;
}

.x-panel-bwrap {
	height: 100%
}
.x-panel-body {
	height: 100%
}

</style>

<table>
	<tr>
		<td width="240">
			<table width="240" cellspacing="0" cellpadding="0">
				<tr style="background-color: #cedff5">
					<td style="padding-left: 30"> <input type="checkbox" id="continueCheckbox" onclick="continueChoose()"><font style="font-size: 13">连续选择</font></td>
					<td style="padding-left: 30"><input type="checkbox" id="existsCheckbox" onclick="existsChoose()" ><font style="font-size: 13">包含下级</font></td>
				</tr>
				<tr>
					<td colspan="2">
						<div id="tree-div" style="overflow: auto;  border: 2px solid #c3daf9; height: 200px;"></div>
						<odin:hidden property="codevalueparameter" />
					</td>
				</tr>
			</table>
		</td>
		<td >
		</td>
	</tr>

</table>
<table style="width: 90%" id="bp">
	<tr>
		<td align="right" ><odin:button text="开始查询" property="doQuery"></odin:button></td>
	</tr>
</table>
<script type="text/javascript">
<!--

//-->
document.getElementById('LWflag').value = realParent.document.getElementById('LWflag').value;
</script>



<%@include file="/pages/customquery/otjs.jsp" %>



<script type="text/javascript">
Ext.onReady(function(){	
	
	document.getElementById('personq').value = realParent.document.getElementById('personq').value;
	document.getElementById('mllb').value = realParent.document.getElementById('mllb').value
	
	
		window.onresize=resizeframe;
		resizeframe();
		
		
		document.getElementById('existsCheckbox').click();
		
		
	});
	
	function resizeframe(){
		var treediv = document.getElementById("tree-div");
		var viewSize = Ext.getBody().getViewSize();
		
		treediv.style.height = viewSize.height-80;
		Ext.getCmp('group').setWidth(viewSize.width-7);
		//document.getElementById("doQuery").style.height='40';
	}
	
	
</script>

