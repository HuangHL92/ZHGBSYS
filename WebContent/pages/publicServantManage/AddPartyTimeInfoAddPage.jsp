<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.FontConfigPageModel"%>
<%@include file="/comOpenWinInit.jsp" %>
<style>
<%=FontConfigPageModel.getFontConfig()%>
</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>

<div id="cover_wrap"></div>

<script type="text/javascript">

function v_test(type){
	//��ȡ����ѡ��ֵ
	var a0141 = document.getElementById('a0141').value;		//��һ����
 	var a0144 = document.getElementById('a0144').value;		//�뵳ʱ��
 	var a3921 = document.getElementById('a3921').value;		//�ڶ�����
 	var a3927 = document.getElementById('a3927').value;		//�������� 
 	
 	
 		//$h.dateDisable('a0144');
 		
 		//�жϵ�һ����
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
 			
 			//�Ƿ�͵�һ�����ظ�
 			if(a0141==a3921 && a3921 != ''){
 				document.getElementById('a3921').value=''
 	 		 	document.getElementById('a3921_combo').value='';
 	 		 	alert('�ڶ��������һ���ɲ�����ͬ��');
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
 	 		 	alert('�ڶ��������һ���ɲ�����ͬ��');
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
 	 		 		alert('�����������һ���ɲ�����ͬ��');
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
 	 		 	alert('�����������һ���ɲ�����ͬ��');
 	 		 	return false;
 	 	 	}
 		}
 		
 		
 		//����ж��Ƿ��й��������ɣ��С��μ�ʱ�䡱���Ա༭ 
 		if(a0141=='02' || a0141=='01' || a0141=='03'|| a3927=='02' || a3927=='01' || a3927=='03' || a3921=='02' || a3921=='01' || a3921=='03' ){//�й���������
 	 		$h.dateEnable('a0144');
 	 	} else {
 	 		$h.dateDisable('a0144');
 	 	}
 	
} 
 
function saveparty(){
	v_test('1');
	v_test('2');
	v_test('3');
	radow.doEvent('save.onclick');
}


function pageCallback(){
	document.getElementById('a0144_1').focus();
	document.getElementById('a0144_1').blur();
	var a0141 = document.getElementById('a0141').value;
 	/* if(a0141=='02' || a0141=='01'){
 		//$h.dateEnable('a0144');
 	} else {
 		$h.dateDisable('a0144');
 	} */
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
<odin:select2 property="a0141" label="��һ����" labelSpanId="a0141SpanId" codeType="GB4762" onchange="v_test(1)"></odin:select2>
</td>
<td>
<odin:NewDateEditTag property="a0144" label="�μ�ʱ��" isCheck="true" labelSpanId="a0144SpanId" maxlength="8" ></odin:NewDateEditTag>
</td>
</tr>
<tr>
<td>
<odin:select2 property="a3921" label="�ڶ�����" onchange="v_test(2)" labelSpanId="a3921SpanId" codeType="GB4762"></odin:select2>
</td>
<td>
<odin:select2 property="a3927" label="��������" onchange="v_test(3)" codeType="GB4762"></odin:select2>
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
	odin.setSelectValue("a0141",$("#a0141", realParent.document).val());
	odin.setSelectValue("a0144",$("#a0144", realParent.document).val());
	odin.setSelectValue("a3927",$("#a3927", realParent.document).val());
	odin.setSelectValue("a3921",$("#a3921", realParent.document).val());
	document.getElementById("a0107").value=$("#a0107", realParent.document).val();
	
	$h.applyFontConfig($h.spFeildAll.a01);
	$h.fieldsDisabled(realParent.fieldsDisabled);
	
	if(realParent.buttonDisabled){
		document.getElementById('cover_wrap').className = "divcover_wrap";
	}
	
});
</script>

