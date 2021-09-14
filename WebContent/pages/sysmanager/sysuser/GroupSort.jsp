<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ page import="com.insigma.siis.local.pagemodel.sysorg.org.OrgSortPageModel"%>
<style>
.x-panel-bwrap,.x-panel-body{
height: 100%
}
.picOrg {
	background-image:url(<%=request.getContextPath()%>/pages/sysorg/org/images/companyOrgImg2.png) !important;
}
.picInnerOrg {
	background-image:url(<%=request.getContextPath()%>/pages/sysorg/org/images/insideOrgImg1.png) !important;
}
.picGroupOrg {
	background-image:url(<%=request.getContextPath()%>/pages/sysorg/org/images/groupOrgImg1.png) !important;
}
</style>
<script type="text/javascript">
Ext.onReady(function() {
	  var man = document.getElementById('manager').value;
      var Tree = Ext.tree;
      var tree = new Tree.TreePanel( {
    	  id : 'group',
          el : 'tree-div',//目标div容器
          split:false,
          width: 164,
          minSize: 164,
          maxSize: 164,
          rootVisible: false,
          autoScroll : true,
          animate : true,
          border:false,
          enableDD : false,
          containerScroll : true,
          loader : new Tree.TreeLoader( {
                dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysmanager.sysuser.GroupSort&eventNames=orgTreeJsonDataOpenTree'
          })
      });
      var root = new Tree.AsyncTreeNode( {
            text :  document.getElementById('ereaname').value,
            iconCls : document.getElementById('picType').value,
            draggable : false,
            id : document.getElementById('ereaid').value,//默认的node值：?node=-100
            href:"javascript:radow.doEvent('querybyid','"+document.getElementById('ereaid').value+"')"
      });
      tree.setRootNode(root);
      tree.render();
      root.expand();

}); 
var changeNode =[];
function upbutton() {
	var tree = Ext.getCmp("group");
	var node = tree.getSelectionModel().getSelectedNode();
	if(node.previousSibling==null){
		alert("用户组已到最上端!");
		return;
	}
	var insertBeforenode = node.previousSibling;
	node.parentNode.insertBefore(node,insertBeforenode);
	node.select();
	changeNode.push(node);
}

function downbutton() {
	var tree = Ext.getCmp("group");
	var node = tree.getSelectionModel().getSelectedNode();
	if(node.nextSibling==null){
		alert("用户组已到最下端!");
		return;
	}
	if(node.nextSibling.nextSibling==null){
		node.parentNode.insertBefore(node.nextSibling,node);
		node.select();
		changeNode.push(node);
		return;
	} 
	var insertBeforenode = node.nextSibling.nextSibling;
	node.parentNode.insertBefore(node,insertBeforenode);
	node.select();
	changeNode.push(node);
}
function reloadTree() {
    var tree = Ext.getCmp("group");
	var node = tree.getSelectionModel().getSelectedNode();  
	if(node == null) { //没有选中 重载树  
		tree.root.reload();
	} else {        //重载树 并默认选中上次选择的节点    
	    var path = node.getPath('id');  
	    tree.getLoader().load(node.parentNode,  
	                function(treeNode) {  
	                    tree.expandPath(path, 'id', function(bSucess, oLastNode) {  
	                                tree.getSelectionModel().select(oLastNode);  
	                            });  
	                }, this);    
	}  
}
function YesBtn(){
	if(changeNode.length==0){
		alert('您未进行排序操作');
		return;
	}
	//alert(changeNode);
	changeNode=unique(changeNode);
	//alert(changeNode[0].text);
	var ids ="";
	var childNodes;
	for(var i=0;i<changeNode.length;i++){
		childNodes =changeNode[i].parentNode.childNodes;
		for(var j=0;j<childNodes.length;j++){
			ids=ids+childNodes[j].id+",";
		}
	}
	//alert(ids);
	radow.doEvent('YesNew', ids);
}

function unique(arr) {
     var result = [], isRepeated;
     for (var i = 0, len = arr.length; i < arr.length; i++) {
         isRepeated = false;
         for (var j = 0, leng = result.length; j < leng; j++) {
             if (arr[i].id == result[j].id || arr[i].id.substring(0,arr[i].id.length-4)==result[j].id.substring(0,result[j].id.length-4)) {
                 isRepeated = true;
                 break;
             }
         }
         if (!isRepeated) {
             result.push(arr[i]);
         }
     }
     return result;
 }
</script>

<%
	String ereaname = (String) (new OrgSortPageModel().areaInfo
			.get("areaname"));
	String ereaid = (String) (new OrgSortPageModel().areaInfo
			.get("areaid"));
	String manager = (String) (new OrgSortPageModel().areaInfo
			.get("manager"));
	String picType = (String)(new OrgSortPageModel().areaInfo.get("picType"));
%>

<div>
<table>
	<tr>
		<td>
		<table>
			<tr>
				<td>
				<div id="tree-div"
					style="overflow: auto; height: 360px; border: 2px solid #c3daf9;"></div>
				</td>
			</tr>
			<tr>
				<odin:hidden property="checkedgroupid" />
				<odin:hidden property="forsearchgroupid" />
				<odin:hidden property="ereaname" value="<%=ereaname%>" />
				<odin:hidden property="ereaid" value="<%=ereaid%>" />
				<odin:hidden property="manager" value="<%=manager%>" />
				<odin:hidden property="picType" value="<%=picType%>" />
			</tr>
		</table>
		</td>
		<td width="20px"></td>
		<td>
		<odin:button property="UpBt" text="上移" handler="upbutton"></odin:button><br>
		<odin:button property="DownBt" text="下移" handler="downbutton"></odin:button><br>
		<odin:button property="YesBtnNew" text="确定" handler="YesBtn"></odin:button><br>
		<odin:button property="closeBtn" text="关闭"></odin:button>
		</td>
	</tr>
</table>
</div>
<div>
</div>