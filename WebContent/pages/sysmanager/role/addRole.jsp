<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>

<div id="addRoleContent">
<table width="100%">
		<tr>
			<td height="20"  colspan="4"></td>
		</tr>
		<tr>
	    	<odin:textEdit property="name" label="��ɫ����" required="true"></odin:textEdit>
	    	<odin:textEdit property="desc" label="��ɫ����"></odin:textEdit>
		</tr>
		<tr>
		    <odin:select2 property="OwnSystem" label="�û�����"  required="true" value="1" data="['1','����Ա'],['2','��ͨ�û�']"  ></odin:select2>
		</tr>
		<%--<tr>
		    <odin:select2 property="OwnSystem" label="��ɫ����ϵͳ" data="['1','���ܰ�ϵͳ'],['2','ͳ��ϵͳ']"  onchange="displayRoleID"></odin:select2>
		</tr>
		 <tr id="roleidTR" style="display:none;">
	    	<odin:textEdit property="roleid" label="��ɫ����" ></odin:textEdit>
		</tr> --%>
		<tr>
			<td height="20" colspan="4"></td>
		</tr>
</table>	
</div>
<odin:toolBar property="btnToolBar">
	<odin:textForToolBar text="<h3>�½���ɫ</h3>"/>
	<odin:fill/>
	<odin:buttonForToolBar id="btnSave"  text="����"  icon="images/save.gif" cls="x-btn-text-icon"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar id="btnClose" isLast="true" text="�ر�"  icon="images/back.gif" cls="x-btn-text-icon"/>
</odin:toolBar>
<odin:panel contentEl="addRoleContent" property="addRolePanel" topBarId="btnToolBar"></odin:panel>

<script type="text/javascript">
<!--

//-->
	//����ɫ����ϵͳѡ��ͳ��ϵͳ��ʱ����ʾ��ɫ���������
	function displayRoleID(){
		var OwnSystem = document.getElementById("OwnSystem").value;
		if(OwnSystem == '2'){
			document.getElementById("roleidTR").style.display='block';
		}
		if(OwnSystem == '1'){
			document.getElementById("roleidTR").style.display='none';
		}
	}
</script>
