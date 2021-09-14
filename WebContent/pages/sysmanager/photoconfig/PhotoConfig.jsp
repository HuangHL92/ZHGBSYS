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
<odin:groupBox property="hzbBox" title="缓存目录配置">
	<table>
		<tr>
			<odin:textEdit property="hzb_path" colspan="10" label="系统配置缓存文件存储路径"	required="true" width="200" maxlength="500" validator="validater"></odin:textEdit>
		</tr>
	</table>
</odin:groupBox>
	<br>
<odin:groupBox property="photoBox" title="照片路径配置" >
	<table>
		<tr>
			<odin:textEdit property="photopath" colspan="10" label="照片存放路径" required="true" width="250" validator="validater"></odin:textEdit>
		</tr>
	</table>
</odin:groupBox>
	<br>
<odin:groupBox property="ftpBox" title="FTP参数配置">
	<table>
		<tr>
			<odin:textEdit property="trans_server_baseurl" colspan="4" label="FTP服务根目录"	required="true" width="200" maxlength="500" validator="validater"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="local_backup_file" colspan="4" label="FTP本地备份文件目录"	required="true" width="200" maxlength="500" validator="validater"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="local_file_baseurl" colspan="4" label="FTP本地数据存放目录" required="true" width="200" maxlength="500" validator="validater" ></odin:textEdit>
		</tr>
	</table>
</odin:groupBox>
		<br>
<odin:groupBox property="logBox" title="日志输出配置">
	<table>
		<tr>
			<odin:select2 property="logout_isuseful"  label="日志输出开关" required="true"  data="['ON','开'],['OFF','关']" editor="false"></odin:select2>
		</tr>
	</table>
</odin:groupBox>
<br>

<odin:groupBox property="importBox" title="按机构导入数据(HZB1.0)">
	<table>
		<tr>
			<odin:select2 property="import_isuseful"  label="按机构导入数据(HZB1.0)开关" required="true"  data="['ON','开'],['OFF','关']" editor="false"></odin:select2>
		</tr>
	</table>
</odin:groupBox>
<br>
<odin:groupBox property="importTgBox" title="按机构导入数据(套改版)">
	<table>
		<tr>
			<odin:select2 property="import_isusefultg"  label="按机构导入数据(套改版)开关" required="true"  data="['ON','开'],['OFF','关']" editor="false"></odin:select2>
		</tr>
	</table>
</odin:groupBox>
<br>
<odin:groupBox property="mysqlExportBox" title="MYSQL数据库上报">
	<table>
		<tr>
			<odin:select2 property="mysqlexport_isuseful"  label="MYSQL数据库上报开关" required="true"  data="['ON','开'],['OFF','关']" editor="false"></odin:select2>
		</tr>
	</table>
</odin:groupBox>
<br>
<odin:groupBox property="zhtBox" title="直统">
	<table>
		<tr>
			<odin:select2 property="zht_isuseful"  label="直统开关" required="true"  data="['ON','开'],['OFF','关']" editor="false"></odin:select2>
		</tr>
	</table>
</odin:groupBox>
<br>
<odin:groupBox property="ppsBox" title="pps">
	<table>
		<tr>
			<odin:select2 property="pps_isuseful"  label="pps数据抽样" required="true"  data="['ON','开'],['OFF','关']" editor="false"></odin:select2>
		</tr>
	</table>
</odin:groupBox>
<br>
<br>
<odin:groupBox property="oauditBox" title="干部一处审核">
	<table>
		<tr>
			<odin:textEdit property="oaudit" label="干部一处审核账号" required="true" ></odin:textEdit>
		</tr>
	</table>
</odin:groupBox>
<br>
<br>
<odin:groupBox property="ttfauditBox" title="干部处审核">
	<table>
		<tr>
			<odin:textEdit property="ttfaudit" label="干部处审核账号" required="true" ></odin:textEdit>
		</tr>
	</table>
</odin:groupBox>
<br>
<odin:groupBox property="ttfauditBox" title="硬盘使用情况">
	<table>
		<tr>
			<odin:textEdit property="sy" label="可用空间"  ></odin:textEdit>
			<odin:textEdit property="ysy" label="已使用"  ></odin:textEdit>
		</tr>
	</table>
</odin:groupBox>
<br>

<odin:groupBox property="importTgBox" title="干部监督核查网络切换">
	<table>
		<tr>
			<odin:select2 property="gbjdlqh" label="干部监督核查网络" required="true"  data="['1','内'],['2','外']" editor="false"></odin:select2>
		</tr>
	</table>
</odin:groupBox>
<odin:groupBox property="zkjlcfBox" title="整库简历拆分">
	<table>
		<tr>
			<td><odin:button text="执行简历拆分" property="initJLForAll"></odin:button></td>
			<td><odin:button text="标签导入" property="biaoqiandaoru"></odin:button></td>
			<td><odin:button text="评测导入" property="pingcedaoru"></odin:button></td>
			<td><odin:button text="简历明细综述重新生成" property="jianlizs"></odin:button></td>
		</tr>
	</table>
</odin:groupBox>
<%-- <odin:groupBox property="wjgl" title="文件归类">
	<table>
		<tr>
			<td><odin:button text="执行文件归类" property="doWJGL"></odin:button></td>
		</tr>
	</table>
</odin:groupBox> --%>
<br>
	
</div>
<odin:toolBar property="btnToolBar">
	<odin:textForToolBar text="<h3>系统参数配置管理</h3>" />
	<odin:fill />
	<odin:buttonForToolBar id="btnSave"  text="保存" icon="images/save.gif" cls="x-btn-text-icon" isLast="true"/>
</odin:toolBar>
<odin:panel contentEl="addResourceContent" property="addResourcePanel"	topBarId="btnToolBar"></odin:panel>


<script type="text/javascript">
Ext.onReady(function() {
	//页面调整 
//	document.getElementById("addResourceContent").style.width = document.body.clientWidth + "px";
//	document.getElementById("addResourceContent").style.height = Ext.getBody().getViewSize().height + "px";
	 Ext.getCmp('addResourcePanel').setWidth(document.body.clientWidth);
	 Ext.getCmp('btnToolBar').setWidth(document.body.clientWidth);
	 
	 
		 Ext.QuickTips.register({
			 text: "请输入系统存放缓存文件的目录，路径以反斜杠“/”作为分隔符（如D:/HZB）。",
			 target: "hzb_path",
//			 hideDelay: 150,    没用
			 trackMouse: true,  
			 title: "提示"
			 });

	 Ext.QuickTips.register({
		 text: "请输入照片路径，路径以反斜杠“/”作为分隔符（如：C:/HZBPHOTOS），路径默认转为大写，请确保照片路径在系统主目录下。",
		 target: "photopath",
//		 hideDelay: 150,    没用
		 trackMouse: true,  
		 title: "提示"
		 });
	 Ext.QuickTips.register({
		 text: "路径以反斜杠“/”作为分隔符（如：D:/TFTP），路径默认转为大写，三个目录需配置成不同的路径。",
		 target: "trans_server_baseurl",
//		 hideDelay: 150,    没用
		 trackMouse: true,   
		 title: "提示"
		 });
	 Ext.QuickTips.register({
		 text: "路径以反斜杠“/”作为分隔符（如：D:/TFTP），路径默认转为大写，三个目录需配置成不同的路径。",
		 target: "local_backup_file",
//		 hideDelay: 150,    没用
		 trackMouse: true,   
		 title: "提示"
		 });
	 Ext.QuickTips.register({
		 text: "路径以反斜杠“/”作为分隔符（如：D:/TFTP），路径默认转为大写，三个目录需配置成不同的路径。",
		 target: "local_file_baseurl",
//		 hideDelay: 150,    没用
		 trackMouse: true,   
		 title: "提示"
		 });

});

function validater(value){
	var patrn=/^[(\.a-zA-Z)]:\/.+$/; 
	var path = value;
	if (!patrn.test(value)){
	   return  "格式不正确！正确格式为【盘符】:/文件夹名称。";
	} else{
		return true;
	}
	
}
</script>