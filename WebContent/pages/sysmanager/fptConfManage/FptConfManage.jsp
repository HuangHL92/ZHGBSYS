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
					<odin:textEdit property="serverbaseurl" label="本系统FTP服务根目录" required="true"></odin:textEdit>
					<td height="20"></td>
					<odin:textEdit property="backupfile" label="本地备份文件根目录" required="true"></odin:textEdit>
				</tr>
				<tr>
					<odin:textEdit property="localfile" label="本地数据存放根目录" required="true"></odin:textEdit>
					<td height="20"></td>
					<%--
					<odin:textEdit property="serverport" label="本系统FTP服务端口" required="true"></odin:textEdit>
					 --%>
				</tr>
				<tr>
					<td colspan="4">
						<label id="bz1" style="font-size: 12;color: red;line-height: 15px">注：路径以反斜杠“/”作为分隔符（如：D:/TFTP），路径默认转为大写。</label><br>
						<label id="bz2" style="font-size: 12;color: red">注：三个目录需配置成不同的路径。</label><br>
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
	<odin:textForToolBar text="<h3>FPT配置管理</h3>" />
	<odin:fill />
	
	<odin:buttonForToolBar id="btnSave" isLast="true" text="保存"
		icon="images/save.gif" cls="x-btn-text-icon" />
</odin:toolBar>
<odin:panel contentEl="addResourceContent" property="addResourcePanel"
	topBarId="btnToolBar"></odin:panel>


<script type="text/javascript">
Ext.onReady(function() {
	//页面调整
	document.getElementById("addResourceContent").style.width = document.body.clientWidth-4 + "px";
	document.getElementById("addResourceContent").style.height = Ext.getBody().getViewSize().height*0.5 + "px";
	 Ext.getCmp('addResourcePanel').setWidth(document.body.clientWidth);
	 Ext.getCmp('btnToolBar').setWidth(document.body.clientWidth);
});
</script>