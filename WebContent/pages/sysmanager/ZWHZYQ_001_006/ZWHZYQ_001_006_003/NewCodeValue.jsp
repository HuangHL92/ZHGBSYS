<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<odin:hidden property="nodeId"/>
<odin:hidden property="leaf"/>
<odin:toolBar property="btnToolBar" applyTo="toolBarDiv">
	<odin:textForToolBar text=""/>
	<odin:fill/>
	<odin:buttonForToolBar text="ˢ��" handler="refresh" icon="images/icon/refresh.png" isLast="true"/>
</odin:toolBar>
<div id="treeDiv">
</div>
<script type="text/javascript">
	var changeNode = "";
	Ext.onReady(function(){ 
	}, this, {
		delay : 500
	});
	
	var tree;
	function showTree(nodeId) {
		var obj=document.getElementById("treeDiv").parentNode;
		obj.removeChild(document.getElementById("treeDiv"));
		var oDiv=document.createElement("div");
		oDiv.setAttribute("id","treeDiv");
		obj.appendChild(oDiv);
		var url = 'radowAction.do?method=doEvent&pageModel=pages.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_003.NewCodeValue&eventNames=getTreeJsonData&nodeId='+nodeId;
		tree = new Ext.tree.TreePanel({
		id : 'schemeTreeId',
		el : 'treeDiv',
	  	autoScroll : true, 
           stateful:true,
           animate : true,
           border:false,
           enableDD : false,        //�Ƿ�֧����ק
           checkModel: 'cascade',
           rootVisible : false,		//�Ƿ����ظ��ڵ�,�ܶ�����£�����ѡ�����ظ��ڵ�����������
           containerScroll : true,
		   loader : new Ext.tree.TreeLoader({
		       dataUrl : url
		  }),
		  listeners : {
			  'click' : function(node) {
				  document.all.nodeId.value=node.id;
				  document.all.leaf.value=node.leaf;
			  }
		  }
	    });
		var root = new Ext.tree.AsyncTreeNode({
			id : '-1', 
			text : '�����׼������Ϣ��', 
			draggable : false
		});
		tree.setRootNode(root);
		tree.on("check",function(node,checked){
    		changeNode += node.id+":"+checked+",";
   	    });  
		tree.render();
	}
	
	function append(nodeId){
		radow.doEvent("append",nodeId);
	}
	
	function modify(nodeId) {
		radow.doEvent("modify",nodeId);
	}
	
	function deleteBtn(nodeId) {
		radow.doEvent("delete",nodeId);
	}
	
	function show(nodeId) {
		radow.doEvent("show",nodeId);
	}
	
	function shade(nodeId) {
		radow.doEvent("shade",nodeId);
	}
	
	function download(nodeId) {
		radow.doEvent("download",nodeId);
	}
	
	function reloadTree(){
		var tree = Ext.getCmp("schemeTreeId");
		tree.root.reload();
		tree.expandAll();
	}
	
	function modifyNode(nodeId,nodeName) {
		var node = tree.getNodeById(nodeId);
		node.setText(nodeName);
	}
	
	function clear(){
		document.all.nodeId.value="";
	}
	
	/*����£ ����*/
	function partExpand(){
		var tree = Ext.getCmp("schemeTreeId");
		tree.collapseAll();
	}
	
	function extend(){
		var tree = Ext.getCmp("schemeTreeId");
		tree.root.expand(true);
	}
	
	function clearNodeId() {
		document.all.nodeId.value="";
	}
</script>
<odin:window src="" modal="true" id="AddCodeValueCue" width="350" height="130" title="���������" closable="true" maximizable="false"></odin:window>
<odin:window src="" modal="true" id="ModifyCodeValueCue" width="350" height="130" title="�������޸�" closable="true" maximizable="false"></odin:window>
<odin:window src="" modal="true" id="CodeValueDeliverCue" width="580" height="600" title="��չ��׼�����·�" closable="true" maximizable="false"></odin:window>
<odin:window src="" modal="true" id="CodeValueRecieveCue" width="500" height="250" title="�������" closable="true" maximizable="false"></odin:window>