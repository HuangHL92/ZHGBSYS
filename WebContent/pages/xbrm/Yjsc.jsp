<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.SysOrgTreePageModel"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<%@include file="/comOpenWinInit.jsp" %>
<style>
.x-fieldset{
height: 510px;
}
</style>

<odin:hidden property="cur_hj" value="0" title="��ǰ����"/>
<odin:hidden property="cur_hj_4" value="4" title="���۾���֧����"/>
<odin:hidden property="dc005" />
<odin:hidden property="RMRY" />
<odin:hidden property="rbId" />
<odin:groupBox property="gp1" title="ѡ�񷽰�">
<div style="float: left;position: relative;">
	<table width="240" cellspacing="0" cellpadding="0">
		<tr style="background-color: #cedff5">
			<td style="padding-left: 30"> <input type="checkbox" id="continueCheckbox" onclick="continueChoose()"><font style="font-size: 13">����ѡ��</font></td>
			<td style="padding-left: 30"><input type="checkbox" id="existsCheckbox" onclick="existsChoose()" ><font style="font-size: 13">�����¼�</font></td>
		</tr>
		<tr>
			<td colspan="2">
				<div id="tree-div" style="overflow: auto;  border: 2px solid #c3daf9; height: 450px;"></div>
				<odin:hidden property="codevalueparameter" />
				<odin:hidden property="SysOrgTreeIds" value="{}"/>
			</td>
		</tr>
	</table>
</div>
<div style="float: left;position: relative;">
<table style="width: 100%;height: 100%;">
	<tr>
		<odin:select2 property="xzlb" label="ѡ�����" onchange="changexzfn"
		 data="['1','��ԱԤ��'],['2','��Աɸѡ']" value="1" canOutSelectList="false" required="true"/>
	</tr>
	<tr>
		<odin:select2 property="xzfn" label="ѡ�񷽰�" required="true"/>
	</tr>
	<tr><td style="height: 370px;">&nbsp;</td></tr>
	<tr>
		<td align="right" colspan="2">
			<table>
				<tr>
					<%-- <td><odin:button property="scry" text="������Ա"/></td>
					<td style="width: 5px;">&nbsp;</td> --%>
					<td><odin:button property="yjsc" handler="yjsc" text="һ������"/></td>
				</tr>
			</table>
		
		
	</tr>
</table>
</div>
</odin:groupBox>
<%@include file="/pages/customquery/otjs.jsp" %>
<script type="text/javascript">
function changexzfn(){
	document.getElementById("xzfn").value='';
	document.getElementById("xzfn_combo").value='';
	radow.doEvent('initX');
}
Ext.onReady(function() {
	
	if(!!parent.Ext.getCmp(subWinId).initialConfig.RMRY){
		document.getElementById("RMRY").value=parent.Ext.getCmp(subWinId).initialConfig.RMRY;
	}
	
	if(!!parent.Ext.getCmp(subWinId).initialConfig.rbId){
		document.getElementById("rbId").value=parent.Ext.getCmp(subWinId).initialConfig.rbId;
	}
	if(!!parent.Ext.getCmp(subWinId).initialConfig.cur_hj){
		document.getElementById("cur_hj").value=parent.Ext.getCmp(subWinId).initialConfig.cur_hj;
	}
	var viewSize = Ext.getBody().getViewSize();
	
	Ext.get("gp1").setHeight(viewSize.height);
});


function closewin(n){
	$h.alert('ϵ�y��ʾ','������Ա�� '+n+' �ˣ�',function(){
		parent.odin.ext.getCmp('yijianshengcheng').close();
	});
	
}
function closewin2(n){
	
	parent.radow.doEvent('gridcq.dogridquery');
	parent.odin.ext.getCmp('yijianshengcheng').close();
	
	
}

function yjsc(){
	document.getElementById("SysOrgTreeIds").value=Ext.util.JSON.encode(doQuery());
	var xzlb = document.getElementById("xzlb").value;
	if(xzlb==''){
		$h.alert('ϵ�y��ʾ','��ѡ�����');
		return;
	}
	if(xzlb=='1'){
		radow.doEvent('scyj.onclick');
	}else if(xzlb=='2'){
		radow.doEvent('scry.onclick');
	}
}

</script>

