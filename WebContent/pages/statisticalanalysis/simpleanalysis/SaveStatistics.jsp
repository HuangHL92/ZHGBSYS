<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<%@include file="/comOpenWinInit.jsp" %>
<script type="text/javascript">
 var node0 = new Ext.tree.TreeNode({ text:"自定义统计类",id:"z" });
 
	Ext.onReady(function(){
		 var  treepanel = new Ext.tree.TreePanel({
			el:"tree_div",
			bodyStyle: 'background-color:white;',
			width:383,
			height:450,
			frame:true,
			bodyBorder :false,
			useArrows:true,
			autoScroll:true,
			animate:true,
			enableDD:false,
			containerScroll: true,
			rootVisible: false
		
			
		});
		var rootNode=new Ext.tree.TreeNode({text:"根"});
	     	
	    treepanel.setRootNode(rootNode);
	    rootNode.appendChild(node0);
	
	    
	    treepanel.render();
	    node0.expand();
	    rootNode.expand();
	    
	    treepanel.on('click',treeClick);
	    function treeClick(node,e){
	    	var id = node.id.split(',')[0];
	    	var name = node.text;
	    	if(id!='z'){
	    		document.getElementById("conditionName").value=name;
	    		document.getElementById("checknodeid").value=id;
		    	document.getElementById("name").value=name;
	    	}
	    }
	 
	 });
	function addChild(i,name,nid,pid){
    var n0;
		if(pid=='1'){
			n0=new Ext.tree.TreeNode({text:name,id:nid+","+pid,icon:"image/u175.png"});
		}
		if(pid=='2'){
			n0=new Ext.tree.TreeNode({text:name,id:nid+","+pid,icon:"image/u333.png"});
		}
		if(i=='3'){
			node0.appendChild(n0);
		}
	}
	function replaceName(id){
		Ext.MessageBox.show({
		    title:"系统提示",
		    msg:"自定义统计名称已存在，是否覆盖？",
		    buttons:{"ok":"覆盖","cancel":"取消"},
			modal:true,
			closable:false,
		    fn:function(e){
				if(e == "ok"){
					radow.doEvent("replaceEvent",id);
				}
				if(e == "cancel"){
					return ;
				}
			
			}
		  
		});

	}
	/* function replaceAll(id){
		var IDString = id + "@" ;
		Ext.MessageBox.show({
		    title:"系统提示",
		    msg:"自定义统计名称已存在，是否覆盖？",
		    buttons:{"ok":"覆盖","cancel":"取消"},
			modal:true,
			closable:false,
		    fn:function(e){
				if(e == "ok"){
					radow.doEvent("replaceEvent",IDString);
				}
				if(e == "cancel"){
					return ;
				}
			
			}
		  
		});

	} */
/* 	function removeMess(){
		Ext.MessageBox.show({
		    title:"系统提示",
		    msg:"是否确定删除此条统计条件？",
		    buttons:{"ok":"确定","cancel":"取消"},
			modal:true,
			closable:false,
		    fn:function(e){
				if(e == "ok"){
					radow.doEvent("reloadpp");
				}
				if(e == "cancel"){
					return ;
				}
			
			}
		  
		});

	} */
/* 	function reloadpp(){
		realParent.realParent.parent.reload();
	}
 */
</script>
<div id="tree_div">
	 
</div>
<div id="btn_div">
<table>
<tr>
<td>
<odin:textEdit property="conditionName" label="保存名称" width="300" ></odin:textEdit>
</td>
</tr>
</table>

<table>
<tr>
	<td  id="abc" width="105px"></td>
	<%-- <td  id="del" >
	<odin:button text="删除" property="delBtn" ></odin:button>
	</td> 
	<td width="70px"></td>--%>
	<td  id="starts" >
	<odin:button text="保存" property="save"></odin:button>
	</td>
	<td width="105px"></td>
	<td>
	<odin:button text="关闭" property="close"></odin:button>
	</td>
</tr>
</table>
</div>
<odin:hidden property="checknodeid"/>
<odin:hidden property="name"/>