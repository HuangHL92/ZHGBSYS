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
<odin:hidden property="a0000" title="人员id"/>
<odin:hidden property="b0111" title="机构id"/>
<odin:hidden property="orifileid" title="关联 id"  value="1"></odin:hidden>
<odin:hidden property="xfMainOid"/>
<odin:hidden property="train_id"/>


<div id="bar" ></div>
<table style="width:350;align:left" >
    <tr>
		<td height="2"></td>
	</tr>
	<tr >	
		<odin:select2 property="year"  label="年份" style="width:100%"></odin:select2>																	
	</tr>
	<tr>
	<odin:select2 property="wj02" data="['政治建设评价材料','政治建设评价材料'],['年度考核','年度考核评价材料'],['专项考核材料','专项考核材料']
	,['巡视报告','巡视报告'],['巡视整改材料','巡视整改材料'],['年度述职述廉材料','年度述职述廉材料'],['民主生活会材料','民主生活会材料']
	,['整改清单','整改清单']" width="130"  label="材料类型"  />
	</tr>
	<tr>
		<td height="30"></td>
	</tr>
	<tr >
		<%-- <odin:textEdit property="checkfile" inputType="file" label="附件上传" size="30"></odin:textEdit> --%> 	
		<td colspan="4">
	       <iframe id="frame"  name="frame" height="33px" width="500px" src="<%=request.getContextPath() %>/pages/sysorg/org/adfile2.jsp" frameborder=”no” border=”0″ marginwidth=”0″ marginheight=”0″ scrolling=”no” allowtransparency=”yes”></iframe>
		</td>																						
	</tr>
</table>
<odin:toolBar property="ToolBar" applyTo="bar" >
    <odin:fill />

	<odin:buttonForToolBar text="保存" id="save" icon="image/icon021a2.gif" handler="save" isLast="true"/>
	
</odin:toolBar>	
<script type="text/javascript">
Ext.onReady(function() {
    document.getElementById("b0111").value=parentParams.b0111;
    document.getElementById("train_id").value=parentParams.train_id;
    var b0111=document.getElementById("b0111").value; 
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
	var a0000=document.getElementById("a0000").value;
    var b0111=document.getElementById("b0111").value;
	var year = document.getElementById('year').value;
	
	if(year==""){
		alert("年份不能为空");
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
	scfj();

	setTimeout("radow.doEvent('save')", 1000 )

}

function saveCallBack(t){
	$h.alert('系统提示', t, function(){
		//parent.Ext.getCmp('grid1').getStore().reload();
		realParent.Ext.getCmp('dwGrid').getStore().reload();
		parent.Ext.getCmp('CJWJGLdw').close()
	});
}

function scfj(){
	var xfoid=document.getElementById('xfMainOid').value;
	frame.window.imp(xfoid);
	//parent.odin.ext.getCmp('grid1').store.reload();
	
}


</script>

