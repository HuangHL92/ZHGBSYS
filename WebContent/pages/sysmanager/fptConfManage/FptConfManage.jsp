<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>

<script type="text/javascript">

</script>
<style>
div{
border:solid 0px !important;
}
</style>

<div id="addResourceContent">
<odin:hidden property="fptconfigid"/>
<table height="100%">

	<tr>
		
		<td>
			<table width="550px" height="100%">
				
				<tr>
					<odin:textEdit property="serverbaseurl" label="��ϵͳFTP�����Ŀ¼" required="true"></odin:textEdit>
					<td height="20"></td>
					<odin:textEdit property="backupfile" label="���ر����ļ���Ŀ¼" required="true"></odin:textEdit>
				</tr>
				<tr>
					<odin:textEdit property="localfile" label="�������ݴ�Ÿ�Ŀ¼" required="true"></odin:textEdit>
					<td height="20"></td>
					<%--
					<odin:textEdit property="serverport" label="��ϵͳFTP����˿�" required="true"></odin:textEdit>
					 --%>
				</tr>
				<tr>
					<td colspan="4">
						<label id="bz1" style="font-size: 12;color: red;line-height: 15px">ע��·���Է�б�ܡ�/����Ϊ�ָ������磺D:/TFTP����·��Ĭ��תΪ��д��</label><br>
						<label id="bz2" style="font-size: 12;color: red">ע������Ŀ¼�����óɲ�ͬ��·����</label><br>
					</td>
				</tr>
				<tr>
					<td height="20"></td>
				</tr>					
			</table>
		</td>
	</tr>
</table>
</div>
<odin:toolBar property="btnToolBar">
	<odin:textForToolBar text="<h3>FPT���ù���</h3>" />
	<odin:fill />
	
	<odin:buttonForToolBar id="btnSave" isLast="true" text="����"
		icon="images/save.gif" cls="x-btn-text-icon" />
</odin:toolBar>
<odin:panel contentEl="addResourceContent" property="addResourcePanel"
	topBarId="btnToolBar"></odin:panel>


<script type="text/javascript">
Ext.onReady(function() {
	//ҳ�����
	document.getElementById("addResourceContent").style.width = document.body.clientWidth-4 + "px";
	document.getElementById("addResourceContent").style.height = Ext.getBody().getViewSize().height*0.5 + "px";
	 Ext.getCmp('addResourcePanel').setWidth(document.body.clientWidth);
	 Ext.getCmp('btnToolBar').setWidth(document.body.clientWidth);
});
</script>