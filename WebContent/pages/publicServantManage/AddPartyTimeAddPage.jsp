<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@ include file="/comOpenWinInit2.jsp" %>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.FontConfigPageModel"%>
<style>
<%=FontConfigPageModel.getFontConfig()%>
</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/customquery.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="js/lengthValidator.js"></script>


<div id="cover_wrap"></div>

<script type="text/javascript">

function v_test(type){
	
	//��ȡ����ѡ��ֵ
	var a0141 = document.getElementById('a0141').value;		//������ò
 	var a0144 = document.getElementById('a0144').value;		//�뵳ʱ��
 	var a3921 = document.getElementById('a3921').value;		//�ڶ�����
 	var a3927 = document.getElementById('a3927').value;		//�������� 
 	
 	
 	//���������òΪȺ�ڡ��޵��ɣ�����������������
 	if(a0141 == 12 || a0141 == 13){
 		$h.dateDisable('a0144');
 		$h.selecteDisable('a3921');
 		$h.selecteDisable('a3927');
 	}else{
 		selecteEnable('a3921');
 		selecteEnable('a3927');
 	}
 	
 	
 	
 	
 	if(a3921 == 12 || a3921 == 13){
 		
 		document.getElementById('a3921').value=''
	 	document.getElementById('a3921_combo').value='';
 		alert('����ѡ��Ⱥ�ڻ��޵��ɣ�');
 		return false;
 	}
 	
	if(a3927 == 12 || a3927 == 13){
		document.getElementById('a3927').value=''
		document.getElementById('a3927_combo').value='';
		alert('����ѡ��Ⱥ�ڻ��޵��ɣ�');
		return false;
 	}
 	
	
 		//�ж�������ò
 		if(type=='1'){
 			
 			if(a0141 == a3921 && a0141 != ''){
 				document.getElementById('a0141').value='';
 	 		 	document.getElementById('a0141_combo').value='';
 	 		 	alert('������ڶ�������ͬ��');
 	 		 	return false;
 			}
 			if(a0141 == a3927 && a0141 != ''){
 				document.getElementById('a0141').value=''
 	 		 	document.getElementById('a0141_combo').value='';
 	 		 	alert('���������������ͬ��');
 	 		 	return false;
 			}
 			
 			if((a0141=='02' || a0141=='01' || a0141=='03') && (a3921=='02' || a3921=='01' || a3921=='03') ){
 				document.getElementById('a0141').value='';
 	 		 	document.getElementById('a0141_combo').value='';
 	 		 	alert('������ڶ�������ͬ��');
 	 		 	return false;
 	 	 	}
 			
 			if((a0141=='02' || a0141=='01' || a0141=='03') && (a3927=='02' || a3927=='01' || a3927=='03') ){
 				document.getElementById('a0141').value=''
 	 		 	document.getElementById('a0141_combo').value='';
 	 		 	alert('���������������ͬ��');
 	 		 	return false;
 	 	 	}
 			
 		}
 		
 		
 		//�ڶ������ж�
 		if(type == '2'){
 			
 			//�Ƿ��������ò�ظ�
 			if(a0141==a3921 && a3921 != ''){
 				document.getElementById('a3921').value=''
 	 		 	document.getElementById('a3921_combo').value='';
 	 		 	alert('�ڶ�������������ò������ͬ��');
 	 		 	return false;
 			}
 			//���ɺ͵���������ͬ 
 			if(a3921 != '' && a3921==a3927){
 				document.getElementById('a3921').value=''
 	 		 	document.getElementById('a3921_combo').value='';
 	 		 	alert('�ڶ�������������ɲ�����ͬ��');
 	 		 	return false;
 			}
 			
 			if((a0141=='02' || a0141=='01' || a0141=='03') && (a3921=='02' || a3921=='01' || a3921=='03') ){
 				document.getElementById('a3921').value=''
 	 		 	document.getElementById('a3921_combo').value='';
 	 		 	alert('�ڶ�������������ò������ͬ��');
 	 		 	return false;
 	 	 	}
 			
 			if((a3921=='02' || a3921=='01' || a3921=='03') && (a3927=='02' || a3927=='01' || a3927=='03') ){
 				document.getElementById('a3921').value=''
 	 		 	document.getElementById('a3921_combo').value='';
 	 		 	alert('�ڶ�������������ɲ�����ͬ��');
 	 		 	return false;
 	 	 	}
 			
 		}
 		//�жϵ�������
 		if(type==3){
 			//�ж��Ƿ��ǰ��������ͬ
 			if(a3927==a3921 || a3927==a0141){
 				document.getElementById('a3927').value=''
 	 		 	document.getElementById('a3927_combo').value='';
 	 		 	if(a3927==a3921 && a3927 !=''){
 	 		 		alert('����������ڶ����ɲ�����ͬ��');
 	 		 	}
 	 		 	if(a3927==a0141 && a0141 != ''){
 	 		 		alert('����������������ò������ͬ��');
 	 		 	}
 	 		 	return false;
 			}
 			
 			
 			if((a3927=='02' || a3927=='01' || a3927=='03') && (a3921=='02' || a3921=='01' || a3921=='03') ){
 				document.getElementById('a3927').value='';
 	 		 	document.getElementById('a3927_combo').value='';
 	 		 	alert('����������ڶ����ɲ�����ͬ��');
 	 		 	return false;
 	 	 	}
 			
 			if((a0141=='02' || a0141=='01' || a0141=='03') && (a3927=='02' || a3927=='01' || a3927=='03') ){
 				document.getElementById('a3927').value=''
 	 		 	document.getElementById('a3927_combo').value='';
 	 		 	alert('����������������ò������ͬ��');
 	 		 	return false;
 	 	 	}
 		}
 		
 		
 		//����ж��Ƿ��й��������ɣ��С��뵳ʱ�䡱���Ա༭ 
 		if(a0141=='02' || a0141=='01' || a3927=='02' || a3927=='01' || a3921=='02' || a3921=='01' ){//�й���������
 	 		$h.dateEnable('a0144');
 	 	} else {
 	 		$h.dateDisable('a0144');
 	 		
 	 		/* Ext.getCmp('a0144_1').setValue('');
	 		document.getElementById("a0144").value='';
	 		Ext.getCmp('a0144_1').setDisabled(true); */
 	 	}
 	
} 
 
function saveparty(){
	
	//�뵳ʱ��
	var a0144 = document.getElementById('a0144').value;	
	var a0144_1 = document.getElementById('a0144_1').value;	
	
	
	var text1 = dateValidate(a0144_1);
	if(a0144_1.indexOf(".") > 0){
		text1 = dateValidate(a0144);
	}
	if(text1!==true){
		$h.alert('ϵͳ��ʾ','�뵳ʱ�䣺' + text1, null,400);
		return false;
	}
	
	
	/* var a0141 = document.getElementById('a0141').value;		//������ò
	if(a0141==''){
		$h.alert('ϵͳ��ʾ��','������ò����Ϊ�գ�',null,220); 
		return false;
	} */
	
	v_test('1');
	v_test('2');
	v_test('3');
	radow.doEvent('save.onclick');
}


function pageCallback(){
	
	var a0144 = document.getElementById('a0144').value;		//�뵳ʱ��
	if(a0144 != null && a0144 != ""){
		
		document.getElementById('a0144_1').focus();
		document.getElementById('a0144_1').blur();
	}
	
	//var a0141 = document.getElementById('a0141').value;
 	
}
</script>
<odin:toolBar property="btnToolBar" applyTo="divbtnToolBar">
	<odin:fill />
	<odin:buttonForToolBar id="save1" isLast="true" handler="saveparty" text="ȷ��"
		icon="images/save.gif" cls="x-btn-text-icon" />
</odin:toolBar>

<div id="divbtnToolBar"></div>	
<odin:hidden property="a0107" title="��������"/>
<table cellspacing="4" width="100%" align="center">
<tr>
<td>
<odin:select2 property="a0141" label="������ò" labelSpanId="a0141SpanId" codeType="GB4762" onchange="v_test1"></odin:select2>
</td>
<td>
<odin:NewDateEditTag property="a0144" label="�뵳ʱ��" isCheck="true" labelSpanId="a0144SpanId" maxlength="8" ></odin:NewDateEditTag>
</td>
</tr>
<tr>
<td>
<odin:select2 property="a3921" label="�ڶ�����" onchange="v_test2" labelSpanId="a3921SpanId" codeType="GB4762"></odin:select2>
</td>
<td>
<odin:select2 property="a3927" label="��������" onchange="v_test3" codeType="GB4762"></odin:select2>
</td>
</tr>
<!--<tr>
<td>
<odin:textEdit property="a" label="���ĵ���"></odin:textEdit>
</td>
</tr>
-->
</table>
<script type="text/javascript">
Ext.onReady(function(){
	
	realParent.numC = 0;
	
	odin.setSelectValue("a0141",$("#a0141", realParent.document).val());
	odin.setSelectValue("a0144",$("#a0144", realParent.document).val());
	odin.setSelectValue("a3927",$("#a3927", realParent.document).val());
	odin.setSelectValue("a3921",$("#a3921", realParent.document).val());
	
	
	document.getElementById("a0107").value=$("#a0107", realParent.document).val();
	
	$h.applyFontConfig($h.spFeildAll.a01);
	
	
	if(realParent.buttonDisabled){
		document.getElementById('cover_wrap').className = "divcover_wrap";
	}
	
	
	
	document.getElementById('divbtnToolBar').style.width = document.body.clientWidth;
	var f = (realParent.fieldsDisabled+"").replace(",a0107","");
	var s = (realParent.selectDisabled+"").replace(",a0107","");
	//����Ϣ����ϸ��Ȩ�޿��ƣ��Ƿ����ά�� 
	$h.fieldsDisabled(f.split(",")); 
	//����Ϣ����ϸ��Ȩ�޿��ƣ��Ƿ���Բ鿴
	var imgdata = "url(<%=request.getContextPath()%>/image/suo.jpg)";
	$h.selectDisabled(s.split(","),imgdata); 
});



function v_test1(){
	
	var type = '1';
	
	//��ȡ����ѡ��ֵ
	var a0141 = document.getElementById('a0141').value;		//������ò
 	var a0144 = document.getElementById('a0144').value;		//�뵳ʱ��
 	var a3921 = document.getElementById('a3921').value;		//�ڶ�����
 	var a3927 = document.getElementById('a3927').value;		//�������� 
 	
 	//���������òΪȺ�ڡ��޵��ɣ�����������������
 	if(a0141 == 12 || a0141 == 13){
 		$h.dateDisable('a0144');
 		$h.selecteDisable('a3921');
 		$h.selecteDisable('a3927');
 	}else{
 		selecteEnable('a3921');
 		selecteEnable('a3927');
 	}
 	
 	if(a3921 == 12 || a3921 == 13){
 		
 		document.getElementById('a3921').value=''
	 	document.getElementById('a3921_combo').value='';
 		alert('����ѡ��Ⱥ�ڻ��޵��ɣ�');
 		return false;
 	}
 	
	if(a3927 == 12 || a3927 == 13){
		document.getElementById('a3927').value=''
		document.getElementById('a3927_combo').value='';
		alert('����ѡ��Ⱥ�ڻ��޵��ɣ�');
		return false;
 	}
 	
 		
 		//�ж�������ò
 		if(type=='1'){
 			
 			if(a0141 == a3921 && a0141 != ''){
 				document.getElementById('a0141').value='';
 	 		 	document.getElementById('a0141_combo').value='';
 	 		 	alert('������ڶ�������ͬ��');
 	 		 	return false;
 			}
 			if(a0141 == a3927 && a0141 != ''){
 				document.getElementById('a0141').value=''
 	 		 	document.getElementById('a0141_combo').value='';
 	 		 	alert('���������������ͬ��');
 	 		 	return false;
 			}
 			
 			if((a0141=='02' || a0141=='01' || a0141=='03') && (a3921=='02' || a3921=='01' || a3921=='03') ){
 				document.getElementById('a0141').value='';
 	 		 	document.getElementById('a0141_combo').value='';
 	 		 	alert('������ڶ�������ͬ��');
 	 		 	return false;
 	 	 	}
 			
 			if((a0141=='02' || a0141=='01' || a0141=='03') && (a3927=='02' || a3927=='01' || a3927=='03') ){
 				document.getElementById('a0141').value=''
 	 		 	document.getElementById('a0141_combo').value='';
 	 		 	alert('���������������ͬ��');
 	 		 	return false;
 	 	 	}
 			
 		}
 		
 		
 		//����ж��Ƿ��й��������ɣ��С��뵳ʱ�䡱���Ա༭ 
 		if(a0141=='02' || a0141=='01' || a3927=='02' || a3927=='01' || a3921=='02' || a3921=='01' ){//�й���������
 	 		$h.dateEnable('a0144');
 	 	} else {
 	 		$h.dateDisable('a0144');
 	 	}
 	
} 


function v_test2(){
	var type = '2';
	
	//��ȡ����ѡ��ֵ
	var a0141 = document.getElementById('a0141').value;		//������ò
 	var a0144 = document.getElementById('a0144').value;		//�뵳ʱ��
 	var a3921 = document.getElementById('a3921').value;		//�ڶ�����
 	var a3927 = document.getElementById('a3927').value;		//�������� 
 	
 	//���������òΪȺ�ڡ��޵��ɣ�����������������
 	if(a0141 == 12 || a0141 == 13){
 		$h.dateDisable('a0144');
 		$h.selecteDisable('a3921');
 		$h.selecteDisable('a3927');
 	}else{
 		selecteEnable('a3921');
 		selecteEnable('a3927');
 	}
 	
 	if(a3921 == 12 || a3921 == 13){
 		
 		document.getElementById('a3921').value=''
	 	document.getElementById('a3921_combo').value='';
 		alert('����ѡ��Ⱥ�ڻ��޵��ɣ�');
 		return false;
 	}
 	
	if(a3927 == 12 || a3927 == 13){
		document.getElementById('a3927').value=''
		document.getElementById('a3927_combo').value='';
		alert('����ѡ��Ⱥ�ڻ��޵��ɣ�');
		return false;
 	}
 	
 		
 		
 		
 		//�ڶ������ж�
 		if(type == '2'){
 			
 			//�Ƿ��������ò�ظ�
 			if(a0141==a3921 && a3921 != ''){
 				document.getElementById('a3921').value=''
 	 		 	document.getElementById('a3921_combo').value='';
 	 		 	alert('�ڶ�������������ò������ͬ��');
 	 		 	return false;
 			}
 			//���ɺ͵���������ͬ 
 			if(a3921 != '' && a3921==a3927){
 				document.getElementById('a3921').value=''
 	 		 	document.getElementById('a3921_combo').value='';
 	 		 	alert('�ڶ�������������ɲ�����ͬ��');
 	 		 	return false;
 			}
 			
 			if((a0141=='02' || a0141=='01' || a0141=='03') && (a3921=='02' || a3921=='01' || a3921=='03') ){
 				document.getElementById('a3921').value=''
 	 		 	document.getElementById('a3921_combo').value='';
 	 		 	alert('�ڶ�������������ò������ͬ��');
 	 		 	return false;
 	 	 	}
 			
 			if((a3921=='02' || a3921=='01' || a3921=='03') && (a3927=='02' || a3927=='01' || a3927=='03') ){
 				document.getElementById('a3921').value=''
 	 		 	document.getElementById('a3921_combo').value='';
 	 		 	alert('�ڶ�������������ɲ�����ͬ��');
 	 		 	return false;
 	 	 	}
 			
 		}
 		
 		
 		
 		//����ж��Ƿ��й��������ɣ��С��뵳ʱ�䡱���Ա༭ 
 		if(a0141=='02' || a0141=='01' || a3927=='02' || a3927=='01' || a3921=='02' || a3921=='01' ){//�й���������
 	 		$h.dateEnable('a0144');
 	 	} else {
 	 		$h.dateDisable('a0144');
 	 	}
 	
} 


function v_test3(){
	
	var type = '3';
	
	//��ȡ����ѡ��ֵ
	var a0141 = document.getElementById('a0141').value;		//������ò
 	var a0144 = document.getElementById('a0144').value;		//�뵳ʱ��
 	var a3921 = document.getElementById('a3921').value;		//�ڶ�����
 	var a3927 = document.getElementById('a3927').value;		//�������� 
 	
 	//���������òΪȺ�ڡ��޵��ɣ�����������������
 	if(a0141 == 12 || a0141 == 13){
 		$h.dateDisable('a0144');
 		$h.selecteDisable('a3921');
 		$h.selecteDisable('a3927');
 	}else{
 		selecteEnable('a3921');
 		selecteEnable('a3927');
 	}
 	
 	if(a3921 == 12 || a3921 == 13){
 		
 		document.getElementById('a3921').value=''
	 	document.getElementById('a3921_combo').value='';
 		alert('����ѡ��Ⱥ�ڻ��޵��ɣ�');
 		return false;
 	}
 	
	if(a3927 == 12 || a3927 == 13){
		document.getElementById('a3927').value=''
		document.getElementById('a3927_combo').value='';
		alert('����ѡ��Ⱥ�ڻ��޵��ɣ�');
		return false;
 	}
 	
 		
 		//�жϵ�������
 		if(type==3){
 			//�ж��Ƿ��ǰ��������ͬ
 			if(a3927==a3921 || a3927==a0141){
 				document.getElementById('a3927').value=''
 	 		 	document.getElementById('a3927_combo').value='';
 	 		 	if(a3927==a3921 && a3927 !=''){
 	 		 		alert('����������ڶ����ɲ�����ͬ��');
 	 		 	}
 	 		 	if(a3927==a0141 && a0141 != ''){
 	 		 		alert('����������������ò������ͬ��');
 	 		 	}
 	 		 	return false;
 			}
 			
 			
 			if((a3927=='02' || a3927=='01' || a3927=='03') && (a3921=='02' || a3921=='01' || a3921=='03') ){
 				document.getElementById('a3927').value='';
 	 		 	document.getElementById('a3927_combo').value='';
 	 		 	alert('����������ڶ����ɲ�����ͬ��');
 	 		 	return false;
 	 	 	}
 			
 			if((a0141=='02' || a0141=='01' || a0141=='03') && (a3927=='02' || a3927=='01' || a3927=='03') ){
 				document.getElementById('a3927').value=''
 	 		 	document.getElementById('a3927_combo').value='';
 	 		 	alert('����������������ò������ͬ��');
 	 		 	return false;
 	 	 	}
 		}
 		
 		
 		//����ж��Ƿ��й��������ɣ��С��뵳ʱ�䡱���Ա༭ 
 		if(a0141=='02' || a0141=='01' || a3927=='02' || a3927=='01' || a3921=='02' || a3921=='01' ){//�й���������
 	 		$h.dateEnable('a0144');
 	 	} else {
 	 		$h.dateDisable('a0144');
 	 	}
 	
} 


function setA0144Disabled(){
	
	
	document.getElementById("a0144_1").readOnly=true;
	document.getElementById("a0144_1").disabled=true;
	document.getElementById("a0144_1").style.backgroundColor="#EBEBE4";
	document.getElementById("a0144_1").style.backgroundImage="none";
	document.getElementById("a0144").value="";
	document.getElementById("a0144_1").value="";
		
	
}

</script>

