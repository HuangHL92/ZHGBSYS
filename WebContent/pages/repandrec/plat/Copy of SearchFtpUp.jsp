<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<odin:toolBar property="btnToolBar">
	<odin:fill />
	<odin:buttonForToolBar id="btnAdd" text="确定"  />
</odin:toolBar>
<odin:panel contentEl="ftpUpContent" property="ftpUpManagePanel" topBarId="btnToolBar" />
<div id="ftpUpContent" style="width:100%;height: 100%; overflow: auto;">
<odin:editgrid property="grid" title="上行FTP配置" autoFill="true"	 isFirstLoadData="false" url="/">
	<odin:gridJsonDataModel  root="data" totalProperty="totalCount">
		<odin:gridDataCol name="id" />
		<odin:gridDataCol name="name" />
		<odin:gridDataCol name="hostname" />
		<odin:gridDataCol name="username" isLast="true"/>
		
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn></odin:gridRowNumColumn>
		<odin:gridColumn dataIndex="id" width="10" header="FTP内部id" hidden="true" align="center" />
		<odin:gridColumn dataIndex="name" width="200" header="FTP名称"  align="center" />
		<odin:gridColumn dataIndex="hostname" width="200" header="FTP主机地址" align="center" />
		<odin:gridColumn dataIndex="username" width="200" header="FTP用户名" align="center" />
	</odin:gridColumnModel>
</odin:editgrid>
<input type="hidden" name="id" id="id" />
</div>

<script type="text/javascript">

</script>