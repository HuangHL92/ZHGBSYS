<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<script type="text/javascript" src="mainPage/js/jquery.js"></script>
<script type="text/javascript">

</script>

<style>
div{
border:solid 0px !important;
}
</style>

<div id="addResourceContent">
<odin:hidden property="fptconfigid"/>

	<table style="width: 100%;height: 100%;">
		
		<tr style="height:30px;">
			<odin:textEdit property="username" label="���ݿ��û���" value="ZHGBSYS" required="true"></odin:textEdit>
			<odin:textEdit property="password" label="���ݿ�����" value="ZHGBSYS123" required="true"></odin:textEdit>
		</tr>
		<tr style="height:30px;">
		    <odin:textEdit property="sid"  label="���ݿ������" value="localhost:5236" required="true"></odin:textEdit>
		    <odin:textEdit property="savepath" value="/root/expdm" label="����·��" required="true"></odin:textEdit>
		</tr>
		<tr style="height:30px;">
			<td colspan="4">
			    <label id="bz1" style="font-size: 12;color: red">ע��·���Է�б�ܡ�/����Ϊ�ָ������磺C:/HZB����</label><br>
				<label id="bz2" style="font-size: 12;color: red">ע�����ݿ������Ϊ�������õ��������ݿ�����������</label><br>
				<label id="bz3" style="font-size: 12;color: red">ע����������Ϣ��������޷��������ݿ��ļ�</label><br>
			</td>
		</tr>
		<tr>
			<td colspan="4">
				<iframe width="100%" style="border: 1px solid rgb(179,201,229);background-color: black;" id="iframe_expFile" name="iframe_expFile"  height="80%" src=""></iframe>
			</td>
		</tr>
	</table>
</div>
<odin:toolBar property="btnToolBar">
	<odin:textForToolBar text="<h3>���ݿ�������Ϣ</h3>" />
	<odin:fill />
	
	<odin:buttonForToolBar id="btnExp" text="����" handler="expSRC" isLast="true"
		icon="images/save.gif" cls="x-btn-text-icon" />
	<%-- <odin:buttonForToolBar id="stopExp" isLast="true" text="��ֹ����"  
		icon="images/save.gif"  cls="x-btn-text-icon" /> --%>
</odin:toolBar>
<odin:panel contentEl="addResourceContent" property="addResourcePanel"
	topBarId="btnToolBar"></odin:panel>


<script type="text/javascript">
Ext.onReady(function() {
	//ҳ�����
	document.getElementById("addResourceContent").style.width = document.body.clientWidth-4 + "px";
	document.getElementById("addResourceContent").style.height = Ext.getBody().getViewSize().height + "px";
	 Ext.getCmp('addResourcePanel').setWidth(document.body.clientWidth);
	 Ext.getCmp('btnToolBar').setWidth(document.body.clientWidth);
});


function expSRC(){
	var username = $('#username').val();
	var password = $('#password').val();
	var sid = $('#sid').val();
	var savepath = $('#savepath').val();
	if(username==''){
		alert('���������ݿ��û���');
		return;
	}
	if(password==''){
		alert('���������ݿ�����');
		return;
	}
	if(sid==''){
		alert('���������ݿ������');
		return;
	}
	if(savepath==''){
		alert('�����뱸��·��');
		return;
	}
	var s = "&username="+username;
	s+= "&password="+password;
	s+= "&sid="+sid;
	s+= "&savepath="+savepath;
	var src = contextPath+'/PublishFileServlet?method=expPBCX'+s;
	$('#iframe_expFile').attr('src',src);
}


</script>