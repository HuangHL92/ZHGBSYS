<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<script type="text/javascript">
var userid = '<%=request.getParameter("userid")%>';
var tree = null;
Ext.onReady(function() {
      var Tree = Ext.tree;
      tree = new Tree.TreePanel( {
            el : 'tree-div',//目标div容器
            autoScroll : true,
            split:false,
            width: 250,
            minSize: 250,
            maxSize: 250,
            border:false,
            animate : true,
            enableDD : true,
            containerScroll : true,
            loader : new Tree.TreeLoader( {
                  dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysmanager.datagroup.UserDataGrant&eventNames=treeJsonData&userid=<%=request.getParameter("userid")%>'
            })
      });
      var root = new Tree.AsyncTreeNode( {
            text : '数据组织树',
            draggable : false,
            id : 'ROOT',
            href:"javascript:void(0)"
      });
      tree.setRootNode(root);
      tree.render();
      root.expand();
      tree.on("checkchange",function(node,checked){
      		var idEle = document.getElementById("dataGroupIds");
          	var ids = idEle.value;
          	cueChecked = checked;
          	if(checked){
          		ids += node.id+",";
          	}else{
          		ids = ids.replace(node.id+",","");
          	}
          	//alert(node.id+"||"+ids);
          	idEle.value = ids;
          	radow.doEvent("dataGroupClick",node.id);
      });
      tree.on("click",function(node,e){
      	   radow.doEvent("dataGroupClick",node.id); 
      });
      document.getElementById("userid").value = userid;
}); 
function querySpeDataGroup(isQuery,dataGroup){
	var win = parent.odin.ext.getCmp("win_pup");
	var p1 = odin.ext.getCmp("P1");
	var speGid = document.getElementById("speGroupId");
	var id = dataGroup.id;
	var groupcode = dataGroup.groupcode;
	if(isQuery){
		win.setSize(500,446);
		p1.setSize(486,410);
		odin.ext.getCmp("speGrid").doLayout();
		speGid.value = id;
		radow.doEvent("speGrid.dogridquery");
	}else{
		win.setSize(270,446);
		p1.setSize(256,410);
		if(p1.store){
			p1.store.removeAll();
		}
		speGid.value = "";
	}
	win.center();
}
</script>
<div id="dataGroupContent">
<table>
	<tr>
		<td>
		<div id="tree-div"
			style="overflow: auto;height: 400px; width: 250px; border: 2px solid #c3daf9;"></div>
		</td>
		<td>
			<odin:grid property="speGrid" title="" autoFill="false" width="245" height="378" topBarId="pageToolBar" isFirstLoadData="false" url="/">
			<odin:gridJsonDataModel>
					<odin:gridDataCol name="id"/>
					<odin:gridDataCol name="checked"/>
					<odin:gridDataCol name="groupcode" />
					<odin:gridDataCol name="name" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridColumn header="" width="25" editor="checkbox" dataIndex="checked" edited="true"/>
					<odin:gridColumn dataIndex="groupcode" width="80" header="编码" align="center"/>
					<odin:gridColumn dataIndex="name" width="100" header="名称" align="center" isLast="true"/>
				</odin:gridColumnModel>
			</odin:grid>
		</td>
	</tr>
</table>
</div>
<odin:toolBar property="btnToolBar">
	<odin:textForToolBar text="<h3></h3>" />
	<odin:fill />
	<odin:buttonForToolBar id="btnSave" isLast="true" text="保存" icon="images/save.gif" cls="x-btn-text-icon" />
</odin:toolBar>
<odin:panel contentEl="dataGroupContent" property="P1" topBarId="btnToolBar" height="410" width="256"></odin:panel>
<odin:hidden property="dataGroupIds"/>
<odin:hidden property="userid"/>
<odin:hidden property="speGroupId"/>
