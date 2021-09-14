<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<script type="text/javascript">
var tree = null;
Ext.onReady(function() {
      var Tree = Ext.tree;
      tree = new Tree.TreePanel( {
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
            loader : new Tree.TreeLoader( {
                  dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysmanager.datagroup.DataGroup&eventNames=treeJsonData'
            })
      });
      /*
      tree.on('checkchange', function(node, checked) {
			node.expand();
			node.attributes.checked = checked;
			node.eachChild(function(child) {
			child.ui.toggleCheck(checked);
			child.attributes.checked = checked;
			child.fireEvent('checkchange', child, checked);
			});
	  }, tree);
	  */
      var root = new Tree.AsyncTreeNode( {
            text : '������֯��',
            draggable : false,
            id : 'ROOT',
            href:"javascript:void(0)"
      });
      tree.setRootNode(root);
      tree.render();
      root.expand();

}); 
function queryById(eventName,id){
	odin.ext.getCmp("btnSave").setText("����");
	document.getElementById("parent").value = "";
	radow.doEvent(eventName,id);
}
function newBtnClick(){
	var node = tree.getSelectionModel().getSelectedNode();
	if(node == null){
		odin.info("��ѡ��һ���ϼ������������ڵ�����½���");
	}
	odin.formClear(document.commForm);
	odin.setSelectValue('dgtype','1');
	odin.ext.getCmp("btnSave").setText("����");
	document.getElementById("parent").value = node.id;

}
/**
* ��Ϊ����ʱ���ڵ�ǰ�ڵ�����һ���ӽڵ㣻��Ϊ����ʱ������Ƿ��޸��������ƣ�����޸��������и���
**/
function changeTreeNode(isSave,id,name){
	var node = tree.getSelectionModel().getSelectedNode();
	if(isSave){
		var newNode = new Ext.tree.TreeNode({
			text:name,
			id:id,
			leaf:true,
			href:"javascript:queryById('querybyid','"+id+"')"
		});
		if(node.isLeaf()){
			node.leaf = false;
		}
		node.appendChild(newNode);
		if(!node.isExpanded()){
			node.expand();
		}
	}else{
		node.setText(name);
	}
}
/**
* ��ǰ̨����ɾ�����
*/
function deleteGroup(id){
	var node = tree.getNodeById(id);
	if(node){
		node.remove();
		odin.formClear(document.commForm);
		odin.setSelectValue('dgtype','1');
	}
}
var tid = 100;
function test(){
	var node = tree.getSelectionModel().getSelectedNode();
	var newNode = new Ext.tree.TreeNode({
		text:"���Խڵ�",
		id:++tid,
		leaf:true
	});
	if(node.isLeaf()){
		node.leaf = false;
	}
	node.appendChild(newNode);
	if(!node.isExpanded()){
		node.expand();
	}
	odin.formClear(document.commForm);
	odin.setSelectValue('dgtype','1');
}
function testcheck(){
	var checkNodes = tree.getChecked();
	alert(checkNodes.length);
}
</script>
<div id="dataGroupContent" style="height: 95%">
<table height="100%" width="100%">
	<tr>
		<td valign="top" width="250px">
			<table height="100%">
				<tr>
					<td>
					<div id="tree-div"
						style="overflow: auto;height: 100%; width: 250px; border: 2px solid #c3daf9;"></div>
					</td>
				</tr>
			</table>
		</td>
		<td valign="top">
			<odin:groupBox title="�������Ϣ">
				<table width="100%">
					<tr>
						<odin:textEdit property="name" label="����������" required="true"></odin:textEdit>
						<odin:textEdit property="groupcode" label="���������" required="true"></odin:textEdit>
					</tr>
					<tr>
						<odin:numberEdit property="rate" label="�����鼶��" maxlength="2" required="true"></odin:numberEdit>
						<odin:select property="dgtype" label="���������" required="true" value="1"></odin:select>
					</tr>					
				</table>
			</odin:groupBox>
			<odin:hidden property="id"/>
			<odin:hidden property="parent"/>
		</td>
	</tr>
</table>
</div>
<odin:toolBar property="btnToolBar">
	<odin:textForToolBar text="<h3>�����������</h3>" />
	<odin:fill />
	<odin:buttonForToolBar id="btnNew" text="�½�" icon="images/i_2.gif"
		cls="x-btn-text-icon" handler="newBtnClick"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar id="btnDelete" text="ɾ��"
		icon="images/qinkong.gif" cls="x-btn-text-icon" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar id="btnSave" isLast="true" text="����"
		icon="images/save.gif" cls="x-btn-text-icon" />
</odin:toolBar>
<odin:panel contentEl="dataGroupContent" property="P1"
	topBarId="btnToolBar"></odin:panel>


