<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<odin:hidden property="nodeId"/>
<odin:hidden property="leaf"/>
<odin:toolBar property="btnToolBar" applyTo="toolBarDiv">
	<odin:textForToolBar text=""/>
	<odin:fill/>
	<odin:buttonForToolBar text="刷新" handler="refresh" icon="images/icon/refresh.png" isLast="true"/>
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
		var url = 'radowAction.do?method=doEvent&pageModel=pages.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_002.CodeValue&eventNames=getTreeJsonData&nodeId='+nodeId;
		tree = new Ext.tree.TreePanel({
		id : 'schemeTreeId',
		el : 'treeDiv',
	  	autoScroll : true, 
           stateful:true,
           animate : true,
           border:false,
           enableDD : false,        //是否支持拖拽
           checkModel: 'cascade',
           rootVisible : false,		//是否隐藏根节点,很多情况下，我们选择隐藏根节点增加美观性
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
			text : '扩充标准代码信息项', 
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
	
	function sorter(nodeId){
		radow.doEvent("sorter",nodeId);
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
	
	function addNode(nodeId,subNodeId,nodeName){
		var node = tree.getNodeById(subNodeId);
		var newNode = {
				id   : nodeId, 
				text : nodeName, 
				leaf : true
		};
		node.appendChild(newNode);
		if(node.leaf) {
			node.parentNode.reload();
		} else {
			node.reload();
		}
	}
	
	function modifyNode(nodeId,nodeName) {
		var node = tree.getNodeById(nodeId);
		var begin = node.text.indexOf('<');
		var end = node.text.indexOf('>')+1;
		var text = node.text.substring(begin,end)+nodeName+"</span>";
		node.setText(text);
	}
	
	function deleteAction(nodeId) {
		var node = tree.getNodeById(nodeId);
		node.parentNode.parentNode.reload();
	}
	
	function shadeNode(nodeId) {
		var node = tree.getNodeById(nodeId);
		var begin = node.text.indexOf('>')+1;
		var end = node.text.lastIndexOf('<');
		var text = node.text.substring(begin,end);
		text = "<span style='background-color: #C0C0C0;'>"+text+"</span>"
		node.setText(text);
	}
	
	function showNode(nodeId) {
		var node = tree.getNodeById(nodeId);
		var begin = node.text.indexOf('>')+1;
		var end = node.text.lastIndexOf('<');
		var text = node.text.substring(begin,end);
		text = "<span style='background-color: none;'>"+text+"</span>"
		node.setText(text);
	}
	
	function impTest() {
		var win = odin.ext.getCmp('CodeValueRecieveCue');
		win.setTitle('导入窗口');
		odin.showWindowWithSrc('CodeValueRecieveCue', contextPath
				+ "/pages/sysmanager/ZWHZYQ_001_006/ZWHZYQ_001_006_002/CodeValueRecieveCue.jsp");
	}
	
	function reloadthis() {
		radow.doEvent("reloadthis");
	}
	
	/*树收拢 方法*/
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
<odin:window src="" modal="true" id="AddCodeValueCue" width="350" height="130" title="代码项添加" closable="true" maximizable="false"></odin:window>
<odin:window src="" modal="true" id="ModifyCodeValueCue" width="350" height="130" title="代码项修改" closable="true" maximizable="false"></odin:window>
<odin:window src="" modal="true" id="CodeValueDeliverCue" width="580" height="500" title="扩展标准代码下发" closable="true" maximizable="false"></odin:window>
<odin:window src="" modal="true" id="CodeValueRecieveCue" width="350" height="180" title="代码接收" closable="true" maximizable="false"></odin:window>
<odin:window src="" modal="true" id="CodeValueCueSort" width="350" height="400" title="排序" closable="true" maximizable="false"></odin:window>