<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%String ctxPath = request.getContextPath(); 
%>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.FontConfigPageModel"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="js/lengthValidator.js"></script>
<%@include file="/comOpenWinInit2.jsp" %>
<body>
	

<odin:toolBar property="toolBar8" applyTo="tol2">
				<odin:fill></odin:fill>
				<odin:buttonForToolBar text="����" id="save1" handler="saveXds" icon="images/save.gif" cls="x-btn-text-icon" isLast="true"></odin:buttonForToolBar>
</odin:toolBar>

<div id="border">
<div id="tol2" align="left"></div>
<odin:hidden property="a99Z100" title="����id"></odin:hidden>
<odin:hidden property="a0000" title="��Ա����"></odin:hidden>

<table cellspacing="2" width="600" align="left">
	<tr>
		<odin:select2 property="a99z103" label="�Ƿ�Ϊѡ����" data="['0','��'],['1','��']" onchange="setDisabled()" />
		<td>&nbsp;</td>
		<odin:select2 property="xdsgwcj" codeType="ZBXDSGWCJ" label="ѡ������λ�㼶" />
	</tr>
	<tr>	
		<odin:NewDateEditTag property="dbjjggzsj" label="���������ع���ʱ��" isCheck="true" labelSpanId="dbjjggzsjSpanId" maxlength="8" />
		<td>&nbsp;</td>
		<odin:select2 property="a99zxdslb" codeType="ZBXDSLB" label="ѡ�������" />
	</tr>
	<tr>
		<odin:textEdit property="xdsdwjzw" label="ѡ��ʱ��λ��ְ��" />
		<td>&nbsp;</td>
		<odin:select2 property="xdsly" label="ѡ������Դ" codeType="ZBXDSLY" />
	</tr>
	<tr>	
		<odin:textEdit property="dsjggzsj" label="���л��ع���ʱ�䣨X�꣩"  />
		<td>&nbsp;</td>
		<odin:textEdit property="jcgzjlsj"  label="���㹤������ʱ�䣨X�꣩" />
	</tr>
	<tr>
		<tags:PublicTextIconEdit property="xxmc" label="ѧУ����(��ѡ�������)" codetype="ZBSCHOOL" />
		<td>&nbsp;</td>
		<tags:PublicTextIconEdit property="zy" label="רҵ(��ѡ�������)" codetype="GB16835" />
	</tr>
	<tr>
		<odin:NewDateEditTag property="a99z104" label="����ѡ����ʱ��" isCheck="true" labelSpanId="a99z104SpanId" maxlength="8" />
	</tr>
</table>
</div>
</body>
<script type="text/javascript">
//����ѡ����
function saveXds(){
	var a99z103 = document.getElementById('a99z103').value;	
	if( a99z103 == '1' ){
		
		//ѡ������λ�㼶
		var xdsgwcj = document.getElementById('xdsgwcj').value;
		if( xdsgwcj == '' || xdsgwcj == null ){
			Ext.Msg.alert("ϵͳ��ʾ","ѡ������λ�㼶����Ϊ�գ�");
			return;
		}
		
		//���������ع���ʱ��
		var dbjjggzsj = document.getElementById('dbjjggzsj').value;	
		var dbjjggzsj_1 = document.getElementById('dbjjggzsj_1').value;	
		if( dbjjggzsj_1 == '' || dbjjggzsj_1 == null ){
			Ext.Msg.alert("ϵͳ��ʾ","���������ع���ʱ�䲻��Ϊ�գ�");
			return;
		}
		var text1 = dateValidate(dbjjggzsj_1);
		if(dbjjggzsj_1.indexOf(".") > 0){
			text1 = dateValidate(dbjjggzsj);
		}
		if(text1!==true){
			$h.alert('ϵͳ��ʾ','���������ع���ʱ�䣺' + text1, null,400);
			return false;
		} 
		
		//ѡ�������
		var a99zxdslb = document.getElementById('a99zxdslb').value;
		if( a99zxdslb == '' || a99zxdslb == null ){
			Ext.Msg.alert("ϵͳ��ʾ","ѡ���������Ϊ�գ�");
			return;
		}
		
		//ѡ��ʱ��λ��ְ��
		var xdsdwjzw = document.getElementById('xdsdwjzw').value;
		if( xdsdwjzw == '' || xdsdwjzw == null ){
			Ext.Msg.alert("ϵͳ��ʾ","ѡ��ʱ��λ��ְ����Ϊ�գ�");
			return;
		}
		
		//ѡ������Դ
		var xdsly = document.getElementById('xdsly').value;
		if( xdsly == '' || xdsly == null ){
			Ext.Msg.alert("ϵͳ��ʾ","ѡ������Դ����Ϊ�գ�");
			return;
		}
		
		//���л��ع���ʱ��
		var dsjggzsj = document.getElementById('dsjggzsj').value;
		if( dsjggzsj == '' || dsjggzsj == null ){
			Ext.Msg.alert("ϵͳ��ʾ","���л��ع���ʱ�䲻��Ϊ�գ�");
			return;
		}
		
		//���㹤������ʱ��
		var jcgzjlsj = document.getElementById('jcgzjlsj').value;
		if( jcgzjlsj == '' || jcgzjlsj == null ){
			Ext.Msg.alert("ϵͳ��ʾ","���㹤������ʱ�䲻��Ϊ�գ�");
			return;
		}
		
		//ѧУ����
		var xxmc = document.getElementById('xxmc_combo').value;
		if( xxmc == '' || xxmc == null ){
			Ext.Msg.alert("ϵͳ��ʾ","ѧУ���Ʋ���Ϊ�գ�");
			return;
		}
		
		//רҵ
		var zy = document.getElementById('zy_combo').value;
		if( zy == '' || zy == null ){
			Ext.Msg.alert("ϵͳ��ʾ","רҵ����Ϊ�գ�");
			return;
		}
		
	}

	radow.doEvent('save.onclick');
}

//����ѡ��������ѡ��
function setDisabled(){
	var a99z103 = document.getElementById("a99z103").value;
	if( a99z103 == '1' ){
	  $h.dateEnable('dbjjggzsj');
	  $h.dateEnable('a99z104');
	  selecteEnable('xdsgwcj');
	  selecteEnable('a99zxdslb');
	  selecteEnable('xdsly');
	  selecteEnable('xxmc');
	  selecteEnable('zy');
	  document.getElementById("xdsdwjzw").disabled=false;
	  document.getElementById("dsjggzsj").disabled=false;
	  document.getElementById("jcgzjlsj").disabled=false;
	}else{
	  $h.dateDisable('dbjjggzsj');
	  $h.dateDisable('a99z104');
	  selecteDisable('xdsgwcj');
 	  selecteDisable('a99zxdslb');
 	  selecteDisable('xdsly');
 	  selecteDisable('xxmc');
 	  selecteDisable('zy');
	  document.getElementById("xdsdwjzw").value="";
	  document.getElementById("xdsdwjzw").disabled=true;
	  document.getElementById("dsjggzsj").value="";
	  document.getElementById("dsjggzsj").disabled=true;
	  document.getElementById("jcgzjlsj").value="";
	  document.getElementById("jcgzjlsj").disabled=true;
	}
}



</script>
<style>
<%=FontConfigPageModel.getFontConfig()%>
.vfontConfig{
color: red;
}

#border {
	position: relative;
	left: 0px;
	top: 0px;
	width: 0px;
	border: 1px solid #99bbe8;
}

#toolBar8 {
	width: 690px !important;
}
</style>
