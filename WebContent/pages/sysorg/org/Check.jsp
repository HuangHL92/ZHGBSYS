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
<odin:hidden property="b0111" title="����id"/>
<odin:hidden property="id" title="����id"/>
<odin:hidden property="orifileid" title="���� id"  value="1"></odin:hidden>
<odin:hidden property="xfMainOid"/>

<div id="bar" ></div>
<table style="width:350;align:center" >
    <tr>
		<td height="2"></td>
	</tr>
	<tr >
		<odin:textEdit property="checkyear" label="�������" required="true"></odin:textEdit>		
		<odin:dateEdit property="checktime" label="����ʱ��" format="Ymd" required="true"></odin:dateEdit>																									
	</tr>
	<tr>
		<td height="30"></td>
	</tr>
	<tr >
		<%-- <odin:textEdit property="checkfile" inputType="file" label="�����ϴ�" size="30"></odin:textEdit> --%> 	
		<td colspan="4">
	       <iframe id="frame"  name="frame" height="33px" width="500px" src="<%=request.getContextPath() %>/pages/sysorg/org/adfile.jsp" frameborder=��no�� border=��0�� marginwidth=��0�� marginheight=��0�� scrolling=��no�� allowtransparency=��yes��></iframe>
		</td>																						
	</tr>
</table>
<odin:toolBar property="ToolBar" applyTo="bar" >
    <odin:fill />

	<odin:buttonForToolBar text="����" id="save" icon="image/icon021a2.gif" handler="save" isLast="true"/>
	
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

function refresh(){//ˢ��
	document.getElementById('t2').value="";
	document.getElementById('dc001').value="";
	//radow.doEvent("tableadd");
}



function save(){//����
	
 	var checkyear = document.getElementById('checkyear').value;
	var checktime = document.getElementById('checktime').value;
	
	if(checkyear==""||checktime==""){
		alert("��������뿼��ʱ�䲻��Ϊ��");
		return
	}
	if(checkyear>2018){
		alert("������ݲ��ܴ���2018");
		return
	}
	
	var ori = document.getElementById('xfMainOid').value;
	if(ori!=null && ori != '' && ori != 'null'){
		//�ļ�
		var file = encodeURI(encodeURI(ori));
		//��ȡuuid
		//var uuid = getUUID();
		
		 document.getElementById('orifileid').value=ori;

	}
	radow.doEvent("save");
}

function saveCallBack(t){
	$h.alert('ϵͳ��ʾ', t, function(){
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

