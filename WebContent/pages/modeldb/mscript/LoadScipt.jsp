<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<odin:toolBar property="btnToolBar">
	<odin:fill/>
	<odin:buttonForToolBar id="btnSave"  text="����"  icon="images/save.gif" cls="x-btn-text-icon"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar id="btnClose" isLast="true" text="�ر�"  icon="images/back.gif" cls="x-btn-text-icon"/>
</odin:toolBar>
<odin:panel contentEl="addRoleContent" property="addRolePanel" topBarId="btnToolBar" />

<div id="addRoleContent">
<odin:groupBox property="s1" >
	<odin:hidden property="handletime" />
	<odin:hidden property="slmodelid" />
	<odin:hidden property="modelobjid" />
	<odin:hidden property="fromtables" />
	<odin:hidden property="conditionid" />
	
<table align="center" width="100%">
	<tr>
		 <odin:textEdit property="objname" label="��/��ͼ��" required="true" /> 
		<odin:select property="objtype" label="����" data="['1','��'],['2','��ͼ']" required="true" />
	</tr>
	<tr>
		<odin:textEdit property="objnote" label="ע��" required="true" /> 
		<odin:textEdit property="normnumbers" label="ָ����" readonly="true" /> 
	</tr>
</table>
<table width="100%">
			<tr>
				<td width="20%">
				<table width="100%">
					<tr>
						<td>
						<div id="tree-div"
							style="overflow:auto; height: 320px; width: 175px; border: 2px solid #c3daf9;"></div>
						</td>
					</tr>
				</table>
				</td>
				<td width="100%">
				<odin:editgrid property="tableGrid" autoFill="true"  height="320" sm="row" isFirstLoadData="false"  url="/">
					<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
							<odin:gridDataCol name="logchecked"/>
							<odin:gridDataCol name="table" />
							<odin:gridDataCol name="norm"/>
							<odin:gridDataCol name="note"/>
							<odin:gridDataCol name="isleader" isLast="true"/>
						</odin:gridJsonDataModel>
						<odin:gridColumnModel>
							<odin:gridColumn header="" width="25" editor="checkbox" dataIndex="logchecked" edited="true"/>
							<odin:gridRowNumColumn></odin:gridRowNumColumn>
							<odin:gridColumn dataIndex="table"  header="��" align="center"/>
							<odin:gridColumn dataIndex="norm" header="ָ��" align="center"/>
							<odin:gridColumn dataIndex="note" header="ע��" align="center"/>
							<odin:gridColumn dataIndex="isleader" header="��ע��" align="center" isLast="true"/>
						</odin:gridColumnModel>
					</odin:editgrid>
				</td>
			</tr>
		</table>
<input type="hidden" name="id" id="id" />
</odin:groupBox>

</div>
<script type="text/javascript"><!--
<!--
Ext.onReady(function() {
      var Tree = Ext.tree;
      var tree = new Tree.TreePanel( {
            el : 'tree-div',//Ŀ��div����
            autoScroll : true,
            split:false,
            width: 224,
            minSize: 224,
            maxSize: 224,
            border:false,
            animate : true,
            enableDD : true,
            containerScroll : true,
            loader : new Tree.TreeLoader({
                  dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysmanager.resource.resource&eventNames=orgTreeJsonData'
            })
      });
      var root = new Tree.AsyncTreeNode( {
            text : 'ҵ��˵���',
            draggable : false,
            id : 'S000000',//Ĭ�ϵ�nodeֵ��?node=-100
            href:"javascript:radow.doEvent('querybyid','S000000')"
      });
      tree.setRootNode(root);
      tree.render();
      root.expand();

}); 

//-->
</script>
