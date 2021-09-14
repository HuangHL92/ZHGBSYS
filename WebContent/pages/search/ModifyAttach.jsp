<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>

<odin:toolBar property="btnToolBar">
	<odin:fill />
	<odin:buttonForToolBar text="上传附件" id="uploadAttachBtn" handler="uploadAttach" cls="x-btn-text-icon"></odin:buttonForToolBar>
	<odin:buttonForToolBar text="删除附件" id="deleAttach" cls="x-btn-text-icon" isLast="true" />
</odin:toolBar>

<div id="panel_content">
</div>

<odin:panel contentEl="panel_content" property="mypanel" topBarId="btnToolBar" ></odin:panel>
<odin:editgrid property="AttachGrid" title="" autoFill="true" bbarId="pageToolBar" pageSize="20" isFirstLoadData="false" url="/">
<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
	<odin:gridDataCol name="checked" />
	<odin:gridDataCol name="id" />
	<odin:gridDataCol name="filename" />
	<odin:gridDataCol name="uploaddate" />
	<odin:gridDataCol name="beizhu" />
	<odin:gridDataCol name="filepath" isLast="true"/>
		
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn></odin:gridRowNumColumn>
		<odin:gridColumn header="selectall" width="25" gridName="AttachGrid" editor="checkbox" dataIndex="checked" edited="true"/>
		<odin:gridEditColumn header="附件名称" width="100" dataIndex="filename" edited="false" editor="text" />
		<odin:gridEditColumn header="附件备注信息" width="100" dataIndex="beizhu" edited="false" editor="text" />
		<odin:gridEditColumn header="附件上传时间" width="60" dataIndex="uploaddate" editor="text" edited="false" isLast="true"/>
	</odin:gridColumnModel>
</odin:editgrid>
<odin:hidden property="cbd_id"/>
<odin:hidden property="flag"/>
<odin:hidden property="cbd_name"/>
<odin:window src="/blank.htm" id="simpleExpWin" width="550" height="300" maximizable="false" title="附件窗口"></odin:window>
<script type="text/javascript">
//呈报单上传附件
	function uploadAttach(){
		var cbd_id = document.getElementById("cbd_id").value;
		var flag = document.getElementById("flag").value;
		var cbd_name = document.getElementById("cbd_name").value;
		
		var win = odin.ext.getCmp('simpleExpWin');
		win.setTitle('导入窗口');
		odin.showWindowWithSrc('simpleExpWin', contextPath
				+ "/pages/search/EditFile.jsp?flag="+flag+"&uuid="+cbd_id+"&uname="+cbd_name);
	}
</script>