<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@include file="/comOpenWinInit.jsp" %>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="js/lengthValidator.js"></script>
<style>
#grid1 {
	width: 316px !important;
}
</style>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/qdstyle.css" />
<script type="text/javascript">

</script>


<div id="bar" ></div>
<table style="width:350;align:left" >
    <tr>
		<td height="2"></td>
	</tr>
	<tr>
		  	<odin:textarea property="zbmc"  label="材料名称" cols="70" rows="4" maxlength="800" ></odin:textarea>
	</tr>
	<tr>
		<td height="30"></td>
	</tr>
	<tr >
		<%-- <odin:textEdit property="checkfile" inputType="file" label="附件上传" size="30"></odin:textEdit> --%> 	
		<td colspan="4">
	       <iframe id="frame"  name="frame" height="33px" width="515px" src="<%=request.getContextPath() %>/pages/sysorg/org/adfile2.jsp" frameborder=”no” border=”0″ marginwidth=”0″ marginheight=”0″ scrolling=”no” allowtransparency=”yes”></iframe>
		</td>																						
	</tr>
</table>
<odin:toolBar property="ToolBar" applyTo="bar" >
    <odin:fill />

	<odin:buttonForToolBar text="保存" id="save" icon="image/icon021a2.gif" handler="save" isLast="true"/>
	
</odin:toolBar>	
<odin:hidden property="zbid"/>
<script type="text/javascript">
Ext.onReady(function() {
	document.getElementById('zbid').value = document.getElementById('subWinIdBussessId').value; 
	var height=document.body.clientHeight;
	var width=document.body.clientWidth;
	document.getElementById("ToolBar").style.width=width+'px';
});

function refresh(){//刷新
	document.getElementById('t2').value="";
	document.getElementById('dc001').value="";
	//radow.doEvent("tableadd");
}



function save(){//新增
	var a=document.getElementById("zbmc").value;
	if(a==null || a==''){
		$h.alert('','请填写材料名称！')
		frame.window.imd();
		return;
	}
	scfj();

	setTimeout("radow.doEvent('save')", 1000 );

}

function saveCallBack(t){
	$h.alert('系统提示', t, function(){
		//parent.Ext.getCmp('grid1').getStore().reload();
		realParent.Ext.getCmp('zxGrid').getStore().reload();
		parent.Ext.getCmp("CJWJGLdw").close();
	});
}
function gg() {
	//window.close();
	//var parentWin = window.opener;
   radow.doEvent("fileGrid.dogridquery");
   realParent.radow.doEvent("zxGrid.dogridquery");
}
function scfj(){
	var oid=document.getElementById('zbid').value;
	frame.window.impdx(oid);
	//parent.odin.ext.getCmp('grid1').store.reload();
	
}


</script>

