<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>


<style>
div{
border:solid 0px !important;
}
</style>

<div id="addResourceContent" style="width:75%;padding-top: 10px;padding-left: 20px;">
<odin:hidden property="fptconfigid"/>
<odin:groupBox property="hzbBox" title="����Ŀ¼����">
	<table>
		<tr>
			<odin:textEdit property="hzb_path" colspan="10" label="ϵͳ���û����ļ��洢·��"	required="true" width="200" maxlength="500" validator="validater"></odin:textEdit>
		</tr>
	</table>
</odin:groupBox>
	<br>
<odin:groupBox property="photoBox" title="��Ƭ·������" >
	<table>
		<tr>
			<odin:textEdit property="photopath" colspan="10" label="��Ƭ���·��" required="true" width="250" validator="validater"></odin:textEdit>
		</tr>
	</table>
</odin:groupBox>
	<br>
<odin:groupBox property="ftpBox" title="FTP��������">
	<table>
		<tr>
			<odin:textEdit property="trans_server_baseurl" colspan="4" label="FTP�����Ŀ¼"	required="true" width="200" maxlength="500" validator="validater"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="local_backup_file" colspan="4" label="FTP���ر����ļ�Ŀ¼"	required="true" width="200" maxlength="500" validator="validater"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="local_file_baseurl" colspan="4" label="FTP�������ݴ��Ŀ¼" required="true" width="200" maxlength="500" validator="validater" ></odin:textEdit>
		</tr>
	</table>
</odin:groupBox>
		<br>
<odin:groupBox property="logBox" title="��־�������">
	<table>
		<tr>
			<odin:select2 property="logout_isuseful"  label="��־�������" required="true"  data="['ON','��'],['OFF','��']" editor="false"></odin:select2>
		</tr>
	</table>
</odin:groupBox>
<br>

<odin:groupBox property="importBox" title="��������������(HZB1.0)">
	<table>
		<tr>
			<odin:select2 property="import_isuseful"  label="��������������(HZB1.0)����" required="true"  data="['ON','��'],['OFF','��']" editor="false"></odin:select2>
		</tr>
	</table>
</odin:groupBox>
<br>
<odin:groupBox property="importTgBox" title="��������������(�׸İ�)">
	<table>
		<tr>
			<odin:select2 property="import_isusefultg"  label="��������������(�׸İ�)����" required="true"  data="['ON','��'],['OFF','��']" editor="false"></odin:select2>
		</tr>
	</table>
</odin:groupBox>
<br>
<odin:groupBox property="mysqlExportBox" title="MYSQL���ݿ��ϱ�">
	<table>
		<tr>
			<odin:select2 property="mysqlexport_isuseful"  label="MYSQL���ݿ��ϱ�����" required="true"  data="['ON','��'],['OFF','��']" editor="false"></odin:select2>
		</tr>
	</table>
</odin:groupBox>
<br>
<odin:groupBox property="zhtBox" title="ֱͳ">
	<table>
		<tr>
			<odin:select2 property="zht_isuseful"  label="ֱͳ����" required="true"  data="['ON','��'],['OFF','��']" editor="false"></odin:select2>
		</tr>
	</table>
</odin:groupBox>
<br>
<odin:groupBox property="ppsBox" title="pps">
	<table>
		<tr>
			<odin:select2 property="pps_isuseful"  label="pps���ݳ���" required="true"  data="['ON','��'],['OFF','��']" editor="false"></odin:select2>
		</tr>
	</table>
</odin:groupBox>
<br>
<br>
<odin:groupBox property="oauditBox" title="�ɲ�һ�����">
	<table>
		<tr>
			<odin:textEdit property="oaudit" label="�ɲ�һ������˺�" required="true" ></odin:textEdit>
		</tr>
	</table>
</odin:groupBox>
<br>
<br>
<odin:groupBox property="ttfauditBox" title="�ɲ������">
	<table>
		<tr>
			<odin:textEdit property="ttfaudit" label="�ɲ�������˺�" required="true" ></odin:textEdit>
		</tr>
	</table>
</odin:groupBox>
<br>
<odin:groupBox property="ttfauditBox" title="Ӳ��ʹ�����">
	<table>
		<tr>
			<odin:textEdit property="sy" label="���ÿռ�"  ></odin:textEdit>
			<odin:textEdit property="ysy" label="��ʹ��"  ></odin:textEdit>
		</tr>
	</table>
</odin:groupBox>
<br>

<odin:groupBox property="importTgBox" title="�ɲ��ල�˲������л�">
	<table>
		<tr>
			<odin:select2 property="gbjdlqh" label="�ɲ��ල�˲�����" required="true"  data="['1','��'],['2','��']" editor="false"></odin:select2>
		</tr>
	</table>
</odin:groupBox>
<odin:groupBox property="zkjlcfBox" title="����������">
	<table>
		<tr>
			<td><odin:button text="ִ�м������" property="initJLForAll"></odin:button></td>
			<td><odin:button text="��ǩ����" property="biaoqiandaoru"></odin:button></td>
			<td><odin:button text="���⵼��" property="pingcedaoru"></odin:button></td>
			<td><odin:button text="������ϸ������������" property="jianlizs"></odin:button></td>
		</tr>
	</table>
</odin:groupBox>
<%-- <odin:groupBox property="wjgl" title="�ļ�����">
	<table>
		<tr>
			<td><odin:button text="ִ���ļ�����" property="doWJGL"></odin:button></td>
		</tr>
	</table>
</odin:groupBox> --%>
<br>
	
</div>
<odin:toolBar property="btnToolBar">
	<odin:textForToolBar text="<h3>ϵͳ�������ù���</h3>" />
	<odin:fill />
	<odin:buttonForToolBar id="btnSave"  text="����" icon="images/save.gif" cls="x-btn-text-icon" isLast="true"/>
</odin:toolBar>
<odin:panel contentEl="addResourceContent" property="addResourcePanel"	topBarId="btnToolBar"></odin:panel>


<script type="text/javascript">
Ext.onReady(function() {
	//ҳ����� 
//	document.getElementById("addResourceContent").style.width = document.body.clientWidth + "px";
//	document.getElementById("addResourceContent").style.height = Ext.getBody().getViewSize().height + "px";
	 Ext.getCmp('addResourcePanel').setWidth(document.body.clientWidth);
	 Ext.getCmp('btnToolBar').setWidth(document.body.clientWidth);
	 
	 
		 Ext.QuickTips.register({
			 text: "������ϵͳ��Ż����ļ���Ŀ¼��·���Է�б�ܡ�/����Ϊ�ָ�������D:/HZB����",
			 target: "hzb_path",
//			 hideDelay: 150,    û��
			 trackMouse: true,  
			 title: "��ʾ"
			 });

	 Ext.QuickTips.register({
		 text: "��������Ƭ·����·���Է�б�ܡ�/����Ϊ�ָ������磺C:/HZBPHOTOS����·��Ĭ��תΪ��д����ȷ����Ƭ·����ϵͳ��Ŀ¼�¡�",
		 target: "photopath",
//		 hideDelay: 150,    û��
		 trackMouse: true,  
		 title: "��ʾ"
		 });
	 Ext.QuickTips.register({
		 text: "·���Է�б�ܡ�/����Ϊ�ָ������磺D:/TFTP����·��Ĭ��תΪ��д������Ŀ¼�����óɲ�ͬ��·����",
		 target: "trans_server_baseurl",
//		 hideDelay: 150,    û��
		 trackMouse: true,   
		 title: "��ʾ"
		 });
	 Ext.QuickTips.register({
		 text: "·���Է�б�ܡ�/����Ϊ�ָ������磺D:/TFTP����·��Ĭ��תΪ��д������Ŀ¼�����óɲ�ͬ��·����",
		 target: "local_backup_file",
//		 hideDelay: 150,    û��
		 trackMouse: true,   
		 title: "��ʾ"
		 });
	 Ext.QuickTips.register({
		 text: "·���Է�б�ܡ�/����Ϊ�ָ������磺D:/TFTP����·��Ĭ��תΪ��д������Ŀ¼�����óɲ�ͬ��·����",
		 target: "local_file_baseurl",
//		 hideDelay: 150,    û��
		 trackMouse: true,   
		 title: "��ʾ"
		 });

});

function validater(value){
	var patrn=/^[(\.a-zA-Z)]:\/.+$/; 
	var path = value;
	if (!patrn.test(value)){
	   return  "��ʽ����ȷ����ȷ��ʽΪ���̷���:/�ļ������ơ�";
	} else{
		return true;
	}
	
}
</script>