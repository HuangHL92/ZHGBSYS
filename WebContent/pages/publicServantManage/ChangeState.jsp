<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@include file="/comOpenWinInit.jsp" %>
<%@page import="com.insigma.odin.framework.persistence.HBUtil"%>
<%@page import="com.insigma.odin.framework.persistence.HBSession"%>
<%@page import="com.insigma.siis.local.business.entity.A01"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript">

function sure(){
	var a0163 = document.getElementById('a0163').value;		//��Ա״̬
	var a0163_combo = document.getElementById('a0163_combo').value;		//��Ա״̬�ı� 
	var xgsj = document.getElementById('xgsj').value;		//״̬���ʱ��
	var xgsj_1 = document.getElementById('xgsj_1').value;		//״̬���ʱ��
	
	var a0163Odl = document.getElementById("a0163Odl").value;  	//ԭ��Ա����״̬
	
	if(a0163 == null || a0163 == ""){
		Ext.Msg.alert("ϵͳ��ʾ","��Ա״̬����Ϊ�գ�");
		return;
	}
	
	if(a0163Odl != a0163 && a0163 != 1){
		if(xgsj == null || xgsj == ""){
			Ext.Msg.alert("ϵͳ��ʾ","״̬���ʱ�䲻��Ϊ�գ�");
			return;
		}
	}
	
	
	var text4 = dateValidateBeforeTady(xgsj_1);
	if(xgsj_1.indexOf(".") > 0){
		text4 = dateValidateBeforeTady(xgsj);
	}
	if(text4!==true){
		$h.alert('ϵͳ��ʾ','״̬���ʱ�䣺' + text4, null,400);
		return false;
	}
	
	radow.doEvent('changeStateYY');
}


Ext.onReady(function(){
	
	
	//��ø�ҳ����б����� 
	var type = window.realParent.document.getElementById("tableType").value;
	document.getElementById("type").value = type;
	
	//����ҳ��Ԥ������Ϣ
	
	var a0163P = window.realParent.document.getElementById("a0163").value;
	var a0163_comboP = window.realParent.document.getElementById("a0163_combo").value;
	
	document.getElementById("a0163Odl").value = a0163P;
	document.getElementById("a0163").value = a0163P;
	document.getElementById("a0163_combo").value = a0163_comboP;
	
	
	
})

function a0163Change(){
	
	var a0163 = document.getElementById('a0163').value;		//��Ա״̬
	var a0163Odl = document.getElementById("a0163Odl").value;  	//ԭ��Ա����״̬
	
	
	if(a0163Odl != a0163 && a0163 != 1){		//�ı�����Ա����״̬����״̬���ʱ����Ա༭
		
		//Ext.getCmp('xgsj').setDisabled(false);
	
		$h.dateEnable('xgsj');
		
	}else{						//δ�ı�����Ա����״̬����״̬���ʱ�䲻���Ա༭
		//$h.dateDisable('xgsj');
		
		$h.dateDisable('xgsj');
		/* Ext.getCmp('xgsj').setValue('');
		Ext.getCmp('xgsj').setDisabled(true); */
	}
	
	/* if(a0163 == 1){
		$h.dateDisable('xgsj');
	} */
	
	
}

</script>
<odin:hidden property="a0163Odl"/>
<odin:hidden property="msg"/>
<odin:hidden property="type"/>
<div style="display:block;margin-left: 80" id="group">
<table>	
<tr>
	<td>&nbsp;</td>
</tr>
<tr>
	<td>&nbsp;</td>
</tr>	
<tr>
	<%-- <tags:PublicTextIconEdit property="a0163" label="��Ա״̬" codetype="ZB126" readonly="true"  onchange="a0163Change"></tags:PublicTextIconEdit> --%>
	<odin:select property="a0163" label="��Ա״̬" canOutSelectList="true" onchange="a0163Change" data="['1','��ְ��Ա'],['21','������Ա'],['22','������Ա'],['23','��ȥ��'],['29','������Ա']"></odin:select>
</tr>
<tr>
	<td>&nbsp;</td>
</tr>
<tr>
<%-- <odin:dateEdit property="xgsj" label="״̬���ʱ��" format="Ymd" disabled="true"></odin:dateEdit> --%>

<odin:NewDateEditTag property="xgsj"  label="״̬���ʱ��" isCheck="true" maxlength="8" disabled="true"></odin:NewDateEditTag>
</tr>
<tr>
	<td>&nbsp;</td>
</tr>
</table>
</div>


<div>
<table align="center">
<tr>
<td><odin:button text="ȷ��(Y)" property="yesBtn" handler="sure"></odin:button></td>
<td width="20"></td>
<%-- <td><odin:button text="ȡ��(C)" property="cancelBtn"></odin:button></td> --%>
</tr>
</table>
</div>