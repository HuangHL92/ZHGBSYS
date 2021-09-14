<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ include file="/comOpenWinInit2.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comboxWithTree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/jquery/json2.js"></script>
<script type="text/javascript">
var SKBSid=parentParam.SKBSid;
var tree2;
Ext.onReady(function() {
	var Tree = Ext.tree;
	tree2 = new Ext.tree.TreePanel({    
		 id:'tree2',
	      region: 'center',  
	      el: 'tree-div1',
	      //True表示为面板是可收缩的，并自动渲染一个展开/收缩的轮换按钮在头部工具条    
	      collapsible: true,    
	      /* title: '标题',//标题文本  */   
	      /* width: 100, */ 
	      width: 500,
	      height:350,
	      border : false,//表框    
	      autoScroll: true,//自动滚动条    
	      animate : true,//动画效果    
	      rootVisible: false,//根节点是否可见    
	      split: true, 
		  loader : new Tree.TreeLoader( {
               dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.fxyp.SKBSBM&eventNames=definedList&SKBSid='+SKBSid
		  }) ,
	       listeners: {        
	    	   scope:this,
				checkchange:function(node,checked){
				  	loop2(node)
				},
				'dblclick':function(node,e){
					if(node.parentNode.id!=-1){
						 radow.doEvent('explain',node.parentNode.id+node.id);
					}
				}  

	      }   
	  });    
	 var root2 = new Tree.AsyncTreeNode( {
        text :  '',
        draggable : false,
        id : '-1'
  });
  tree2.setRootNode(root2);
  tree2.render();
  root2.expandAll();
}); 

function updateTree() {
	var tree = Ext.getCmp("tree2");
	tree.root.reload();
	tree.expandAll();
}

function loop2(node) {
	if (node.ui.checkbox.checked == true) {
		node.ui.checkbox.checked = true;
		node.attributes.checked = true;
		if (node.childNodes.length > 0) {
			for (var i = 0; i < node.childNodes.length; i++) {
				  node.childNodes[i].ui.checkbox.checked=true;
				  node.childNodes[i].attributes.checked = true;
				  loop2(node.childNodes[i]);
			}
		}
	} else {
		node.ui.checkbox.checked = false;
		node.attributes.checked = false;
		for (var i = 0; i < node.childNodes.length; i++) {
		  node.childNodes[i].ui.checkbox.checked=false;
		  node.childNodes[i].attributes.checked = false;
		  loop2(node.childNodes[i]);
		}
	}
}

</script>

<div id="meetingPowerContent">
<table width="100%">
		<tr>
			<td height="20"  colspan="4"></td>
		</tr>
		<tr>
			<td id="SKBSname" colspan="4" style="text-align:center"><td>
		</tr>
		<tr>
			<td colspan="4">
				<odin:groupBox title="部门列表">
						<div id="tree-div1" style="float:left; border: 2px solid #c3daf9;">
						</div>
				</odin:groupBox>
			</td>
		</tr>
</table>	
</div>
<odin:toolBar property="btnToolBar">
	<odin:fill/>
	<odin:buttonForToolBar id="btnSave" text="保存"  icon="images/save.gif" cls="x-btn-text-icon" handler="save" isLast="true"/>
</odin:toolBar>
<odin:panel contentEl="meetingPowerContent" property="meetingPowerPanel" topBarId="btnToolBar"></odin:panel>
<odin:hidden property="SKBSid"/>
<script type="text/javascript">
Ext.onReady(function() {
	var viewSize = Ext.getBody().getViewSize();
	document.getElementById('SKBSid').value = parentParam.SKBSid;
});
var jsontree={};
function save(){
	var selNodes = tree2.getChecked();
	var flag=false;
	Ext.each(selNodes, function(node){
		flag=true;
		var parentnode=node.parentNode;
		jsontree[parentnode.id+node.id]=node.id;
	});
	radow.doEvent('save',JSON.stringify(jsontree));
}
</script>
