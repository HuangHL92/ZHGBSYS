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
<odin:hidden property="b0111" title="机构id"/>
<odin:hidden property="id" title="考核id"/>
<odin:hidden property="orifileid" title="关联 id"  value="1"></odin:hidden>
<odin:hidden property="xfMainOid"/>

<div id="bar" ></div>
<table style="width:350;align:center" >
    <tr>
		<td height="2"></td>
	</tr>
	<tr >
		<odin:textEdit property="checkyear" label="考核年份" required="true"></odin:textEdit>		
		<odin:dateEdit property="checktime" label="考核时间" format="Ymd" required="true"></odin:dateEdit>																									
	</tr>
	<tr>
		<td height="30"></td>
	</tr>
	<tr >
		<%-- <odin:textEdit property="checkfile" inputType="file" label="附件上传" size="30"></odin:textEdit> --%> 	
		<td colspan="4">
	       <iframe id="frame"  name="frame" height="33px" width="500px" src="<%=request.getContextPath() %>/pages/sysorg/org/adfile.jsp" frameborder=”no” border=”0″ marginwidth=”0″ marginheight=”0″ scrolling=”no” allowtransparency=”yes”></iframe>
		</td>																						
	</tr>
</table>
<odin:toolBar property="ToolBar" applyTo="bar" >
    <odin:fill />

	<odin:buttonForToolBar text="保存" id="save" icon="image/icon021a2.gif" handler="save" isLast="true"/>
	
</odin:toolBar>	
<script type="text/javascript">
Ext.onReady(function() {
	var height=document.body.clientHeight;
	var width=document.body.clientWidth;
	document.getElementById("ToolBar").style.width=width+'px';
	var myDate = new Date();
	var year = myDate.getFullYear();
	document.getElementById("checkyear").value=year;
});

function refresh(){//刷新
	document.getElementById('t2').value="";
	document.getElementById('dc001').value="";
	//radow.doEvent("tableadd");
}



function save(){//新增
	
 	var checkyear = document.getElementById('checkyear').value;
	var checktime = document.getElementById('checktime').value;
	
	if(checkyear==""||checktime==""){
		alert("考核年份与考核时间不能为空");
		return
	}
	if(checkyear>2018){
		alert("考核年份不能大于2018");
		return
	}
	
	var ori = document.getElementById('xfMainOid').value;
	if(ori!=null && ori != '' && ori != 'null'){
		//文件
		var file = encodeURI(encodeURI(ori));
		//获取uuid
		//var uuid = getUUID();
		
		 document.getElementById('orifileid').value=ori;

	}
	radow.doEvent("save");
}

function saveCallBack(t){
	$h.alert('系统提示', t, function(){
		//parent.Ext.getCmp('grid1').getStore().reload();
		realParent.Ext.getCmp('grid1').getStore().reload();
		window.close();
	});
}

function scfj(){
	var xfoid=document.getElementById('xfMainOid').value;
	frame.window.imp(xfoid);
	//parent.odin.ext.getCmp('grid1').store.reload();
}


</script>

