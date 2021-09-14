<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.SysOrgPageModel"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@include file="/comOpenWinInit.jsp" %>

<html style="background-color: rgb(223,232,246);">
<meta http-equiv="X-UA-Compatible"content="IE=8">

<%
    SysOrgPageModel sys = new SysOrgPageModel();
	String picType = (String) (sys.areaInfo
			.get("picType"));
	String ereaname = (String) (sys.areaInfo
			.get("areaname"));
	String ereaid = (String) (sys.areaInfo
			.get("areaid"));
	String manager = (String) (sys.areaInfo
			.get("manager"));
	
	String ctxPath = request.getContextPath();
%>
<style>
font {
	font-family: "微软雅黑";
}
body{
	background-color: rgb(223,232,246);
}
</style>

<script type="text/javascript">
var path = "<%=request.getContextPath()%>";

var unid;
var tree;

Ext.onReady(function(){	
	
	Ext.override(Ext.tree.TreeNodeUI, {
		onDblClick : function(e) {
			e.preventDefault();
	        if(this.disabled){
	            return;
	        }
	        if(!this.animating && this.node.hasChildNodes() && !this.node.attributes.dblclick){
	            this.node.toggle();
	        }
	        if(this.node.disabled){//节点disabled 不促发下面事件
	            return;
	        }
	        if(this.checkbox){
	            //this.toggleCheck();
	        }
	       
			this.fireEvent("dblclick", this.node, e);
		}
	});
	
	var Tree = Ext.tree;
	 tree = new Tree.TreePanel( {
	  	  id:'group',
	        el : 'tree-div',//目标div容器
	        split:false,
	        height: 200,
	        minSize: 164,
	        maxSize: 164,
	        rootVisible: false,//是否显示最上级节点
	        autoScroll : true,
	        animate : true,
	        border:false,
	        enableDD : false,
	        containerScroll : true,
	        loader : new Tree.TreeLoader( {
	              dataUrl : '<%=ctxPath%>/radowAction.do?method=doEvent&pageModel=pages.sysorg.org.PublicWindow&eventNames=orgTreeJsonDataPeople'
	        })
	 });
		var root = new Tree.AsyncTreeNode({
			checked : false,
			text : '<%=ereaname %>',
			iconCls : '<%=picType %>',
			draggable : false,
			id : '<%=ereaid %>'
			/* href : "javascript:radow.doEvent('querybyid','"
					+ parent.document.getElementById('ereaid').value + "')", */
		});
		tree.setRootNode(root);
		tree.render();
		//tree.expandPath(root.getPath(),null,function(){addnode();});

		tree.on('click', clickUnid);
		function clickUnid(node) {
	 		document.getElementById("unid").value = node.id;
	    }
}); 

function addnode(){
	var nodeadd = tree.getRootNode(); 
	var newnode = new Ext.tree.TreeNode({ 
		  text: '其它现职人员', 
          expanded: false, 
          icon: '<%=ctxPath%>/pages/sysorg/org/images/insideOrgImg1.png',
  	      id:'X001',
          leaf: false
      });
      newnode.appendChild(new Ext.tree.TreeNode({ 
		  text: '职务为空的其它现职人员', 
          expanded: true, 
          icon: '<%=ctxPath%>/pages/sysorg/org/images/insideOrgImg1.png',
  	      id:'X0010',
          leaf: true
      }));
      nodeadd.appendChild(newnode);
}


function reloadTree() {
	var tree = Ext.getCmp("group");
	//获取选中的节点  
	var node = tree.getSelectionModel().getSelectedNode();  
	if(node == null) { //没有选中 重载树  
		tree.root.reload();
	} else {        //重载树 并默认选中上次选择的节点    
	    var path = node.getPath('id');  
	    tree.getLoader().load(tree.getRootNode(),  
	                function(treeNode) {  
	                    tree.expandPath(path, 'id', function(bSucess, oLastNode) {  
	                                tree.getSelectionModel().select(oLastNode);  
	                            });  
	                }, this);    
	}  
}

/* 关闭 */
function closeWin(){
	radow.doEvent("closeWin.onclick");
}

function ShowCellCover(elementId, titles, msgs)
{	
	Ext.MessageBox.buttonText.ok = "关闭";
	if(elementId.indexOf("start") != -1){
	
		Ext.MessageBox.show({
			title:titles,
			msg:msgs,
			width:300,
	        height:300,
			closable:false,
		//	buttons: Ext.MessageBox.OK,		
			modal:true,
			progress:true,
			wait:true,
			animEl: 'elId',
			increment:5, 
			waitConfig: {interval:150}
			//,icon:Ext.MessageBox.INFO        
		});
	}else if(elementId.indexOf("success") != -1){
			Ext.MessageBox.show({
				title:titles,
				msg:msgs,
				width:300,
		        height:300,
		        modal:true,
				closable:true,
				//icon:Ext.MessageBox.INFO,  
				buttons: Ext.MessageBox.OK
			});
			/*
			setTimeout(function(){
					Ext.MessageBox.hide();
			, 2000);
			*/
					
	}else if(elementId.indexOf("failure") != -1){
			Ext.MessageBox.show({
				title:titles,
				msg:msgs,
				width:300,
				modal:true,
		        height:300,
				closable:true,
				//icon:Ext.MessageBox.INFO,
				buttons: Ext.MessageBox.OK		
			});
			/*
			setTimeout(function(){
					Ext.MessageBox.hide();
			}, 2000);
			*/
	}else {
			Ext.MessageBox.show({
				title:titles,
				msg:msgs,
				width:300,
				modal:true,
		        height:300,
				closable:true,
				//icon:Ext.MessageBox.INFO,
				buttons: Ext.MessageBox.OK		
			});
		}
}
</script>

<odin:hidden property="unid" value=""/>

<div style="width: 382px;height: 200px">
	<table style="width: 100%;">
		<tr style="background-color: rgb(208,223,240)">
			<td>
				<font style="font-size: 13px;">&nbsp选择要导入的单位</font>
			</td>
		</tr>
		<tr>
			<td style="background-color: white;width: 100%;height: 200px;">
				<div id="tree-div"></div>
			</td>
		</tr>
	</table>
</div>

<iframe width="380px" height="160px" src="<%=request.getContextPath() %>/pages/customquery/import.jsp" frameborder=”no” border=”0″ marginwidth=”0″ marginheight=”0″ scrolling=”no” allowtransparency=”yes”></iframe>

</html>