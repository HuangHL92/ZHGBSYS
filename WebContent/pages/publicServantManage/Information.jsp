<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@include file="/comOpenWinInit2.jsp" %>
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
<odin:hidden property="id" title="���id"/>
<odin:hidden property="a0000" title="��Աid"/>
<odin:hidden property="pid" title="�ල��Ϣid"/>
<div id="bar" ></div>
<table style="width:100%;align:center" >
				
	<tr >
		<odin:textEdit property="position" label="��ʱְ��" colspan="2" required="true"></odin:textEdit>	
		<odin:select property="informationtype" label="�ල����" colspan="2" codeType="JDTYPE" required="true"></odin:select>																									
	</tr>
	<tr>
	 
		   <odin:textarea property="matter" label="��Ҫ����" colspan="4"  rows="3" required="true"></odin:textarea>		
		
	</tr>
	<tr>
		<odin:dateEdit property="startdate" label="��ʼʱ��" format="Ymd" required="true"></odin:dateEdit>	
		<odin:textEdit property="dealorg" label="����λ" required="true"></odin:textEdit>			
	</tr>
	<tr>
		<odin:select property="result" label="������" codeType="RESULT" required="true"></odin:select>			
		<odin:dateEdit property="influencetime" label="Ӱ��ʱ��" format="Ymd" required="true"></odin:dateEdit>		
	</tr>
	<tr>
		<odin:textEdit property="filenumber"  label="�ļ���"  required="true"></odin:textEdit> 																								
	</tr>
</table>
<odin:toolBar property="ToolBar" applyTo="bar" >
    <odin:fill />
    
	<odin:buttonForToolBar text="����" id="save" icon="image/icon021a2.gif" handler="save" isLast="true"/>
</odin:toolBar>	
<script type="text/javascript">
Ext.onReady(function() {
	var viewSize = Ext.getBody().getViewSize();
	
	if(parentParam.rb_id==""){
		document.getElementById('id').value="";
	}else{
		document.getElementById('id').value = parentParam.rb_id;
	}
	
	
	
});


function save(){//����
	
	radow.doEvent("save");
}
function saveCallBack(t){
	$h.alert('ϵͳ��ʾ', t, function(){
		parent.Ext.getCmp('grid1').getStore().reload();
		window.close();
	});
}



</script>

