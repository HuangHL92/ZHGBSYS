<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%
	GroupManagePageModel gm = new GroupManagePageModel();
	String ereaname = (String) (gm.areaInfo.get("areaname"));
	String ereaid = (String) (gm.areaInfo.get("areaid"));
	String manager = (String) (gm.areaInfo.get("manager"));
%>
<%@page
	import="com.insigma.siis.local.pagemodel.sysmanager.group.GroupManagePageModel"%>
<odin:toolBar property="btnToolBar">
	<odin:fill />
	<odin:buttonForToolBar id="btnSave" text="��Ȩ" icon="images/save.gif"
		cls="x-btn-text-icon" />
	<odin:separator></odin:separator>
	<odin:textForToolBar text="&nbsp;&nbsp;&nbsp;&nbsp;" isLast="true"/>
</odin:toolBar>
<odin:panel contentEl="addRoleContent" property="addRolePanel" 
	topBarId="btnToolBar" height="450" width="850"/>
<div id="addRoleContent" style="height: 100%; overflow: auto">
	<odin:hidden property="ids" />
	<odin:hidden property="checkedgroupid" />
	<odin:hidden property="forsearchgroupid" />
	<odin:hidden property="ereaname" value="<%=ereaname%>" />
	<odin:hidden property="ereaid" value="<%=ereaid%>" />
	<odin:hidden property="manager" value="<%=manager%>" />
	<table width="100%">
		<tr>
			<td width="30%"><odin:groupBox property="tree_table" title="�û���">
					<table width="100%">
						<tr>
							<td>
								<div id="tree-div"
									style="overflow: auto; height: 320px; width: 160px; border: 2px solid #c3daf9;"></div>
							</td>
						</tr>
					</table>
				</odin:groupBox></td>
			<td width="70%"><odin:editgrid property="Grid" title="�û��б�"
					autoFill="false" width="400" pageSize="20" height="330"
					bbarId="pageToolBar" isFirstLoadData="false" url="/">
					<odin:gridJsonDataModel id="id" root="data"
						totalProperty="totalCount">
						<odin:gridDataCol name="modelroleid" />
						<odin:gridDataCol name="userid" />
						<odin:gridDataCol name="loginname" />
						<odin:gridDataCol name="username" />
						<odin:gridDataCol name="useful"/>
						<odin:gridDataCol name="checked" isLast="true" />
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
						<odin:gridRowNumColumn></odin:gridRowNumColumn>
						<odin:gridColumn dataIndex="modelroleid" header="ģ���û�����id"
							hidden="true" />
						<odin:gridColumn dataIndex="userid" header="�û�id" hidden="true" />
						<odin:gridColumn dataIndex="loginname" width="120" header="�û���¼��"
							align="center" />
						<odin:gridColumn dataIndex="username" width="120" header="����"
							align="center" />
						<odin:gridColumn dataIndex="useful" width="100" header="״̬"
							align="center" renderer="statusVal" />
						<odin:gridColumn header="selectall" gridName="Grid" width="50" editor="checkbox"
							dataIndex="checked" edited="true"  isLast="true" />
					</odin:gridColumnModel>
				</odin:editgrid></td>
		</tr>
	</table>
	<input type="hidden" name="id" id="id" />
</div>
<script type="text/javascript">
function statusVal(value, params, rs, rowIndex, colIndex, ds) {
	var loginname = rs.get('loginname');
	if (value == '1') {
		return '����';
	} else if (value == '0') {
		return '����';
	} else {
		return '״̬�쳣';
	}
}
Ext.onReady(function() {
			var Tree = Ext.tree;
			var tree = new Tree.TreePanel(
					{
						el : 'tree-div',//Ŀ��div����
						autoScroll : true,
						split : false,
						width : 224,
						minSize : 224,
						maxSize : 224,
						border : false,
						animate : true,
						enableDD : true,
						containerScroll : true,
						loader : new Tree.TreeLoader(
								{
									dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysmanager.group.GroupManage&eventNames=orgTreeJsonData'
								})
					});
			var root = new Tree.AsyncTreeNode({
				text : document.getElementById('ereaname').value,
				draggable : false,
				id : document.getElementById('ereaid').value,//Ĭ�ϵ�nodeֵ��?node=-100
				href : "javascript:radow.doEvent('querybyid','"
						+ document.getElementById('ereaid').value + "')"
			});
			tree.setRootNode(root);
			tree.render();
			root.expand();

		});
</script>
