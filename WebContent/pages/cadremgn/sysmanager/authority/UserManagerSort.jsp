<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ page import="com.insigma.siis.local.pagemodel.sysorg.org.OrgSortPageModel"%>
<%@include file="/comOpenWinInit2.jsp" %>
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
	
	new Ext.Button({
		icon : 'images/icon/icon_upwards.gif',
		id:'UpBt',
	    text:'上&nbsp;移',
	    cls :'inline pl',
	    renderTo:"UpBt1",
	    handler:function(){
	    	upbutton();
	    }
	});
	new Ext.Button({
		icon : 'images/icon/icon_adown.gif',
		id:'DownBt',
	    text:'下&nbsp;移',
	    cls :'inline pl',
	    renderTo:"DownBt1",
	    handler:function(){
	    	downbutton();
	    }
	});
	new Ext.Button({
		icon : 'images/icon/save.gif',
		id:'YesBtnNew',
	    text:'确&nbsp;定',
	    cls :'inline pl',
	    renderTo:"YesBtnNew1",
	    handler:function(){
	    	YesBtn();
	    }
	});
	new Ext.Button({
		icon : 'images/icon/close.gif',
		id:'closeBtn',
	    text:'关&nbsp;闭',
	    renderTo:"closeBtn1",
	    cls :'inline pl',
	    handler:function(){
	    	closeBtn();
	    }
	});
	
	var man = document.getElementById('manager').value;
      var Tree = Ext.tree;
      var tree = new Tree.TreePanel( {
    	  id : 'group',
          el : 'tree-div',//目标div容器
          split:false,
          width:377,
         // height:450,
          minSize: 200,
          rootVisible: false,
          autoScroll : true,
          animate : true,
          border:false,
          enableDD : true,
          containerScroll : true,
          loader : new Tree.TreeLoader( {
        	  dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.cadremgn.sysmanager.authority.UsersManager&eventNames=orgTreeJsonData',
        	  baseAttrs: { uiProvider: Ext.tree.TreeCheckNodeUI }
          })
      });
     tree.on("nodedragover", function(e){
    	  var node = e.target;
    	  //add 20180420 禁止拖放到下级或其他机构层级
    	  if("append"==e.point||e.dropNode.parentNode.id!=node.parentNode.id)return false;
    	   changeNode.push(node);
    	  return true;
    	  });
     /*  var root = new Tree.AsyncTreeNode( {
            text :  document.getElementById('ereaname').value,
            iconCls : document.getElementById('picType').value,
            draggable : false,
            id : document.getElementById('ereaid').value,//默认的node值：?node=-100
            href:"javascript:radow.doEvent('querybyid','"+document.getElementById('ereaid').value+"')"
      }); */
      var root_id=document.getElementById("subWinIdBussessId").value;
      if(root_id==''||root_id=='null'||root_id==null){//父页面没有选择机构
    	  root_id=document.getElementById('ereaid').value;
      }else{//父页面选择机构
    	  if(root_id.length>7){//防止机构id截取出错，判断长度，获取父节点id，作为根节点
    		  root_id=root_id.substring(0,root_id.length-4);
    	  }else{
    		  root_id='-1';
    	  }
      }
      var root = new Tree.AsyncTreeNode( {
    	  text :  document.getElementById('ereaname').value,
          draggable : false,
          id : '-1'
       /*    text :  '',
          iconCls : document.getElementById('picType').value,
          draggable : false,
          id : root_id,
          href:"javascript:radow.doEvent('querybyid','"+root_id+"')" */
    });
      tree.setRootNode(root);
      tree.render();
      root.expand(false,true, callback);
      
/*       var side  = document.getElementById("tree-div");
      var side_resize=function()  
      {  
              height = document.body.clientHeight;  
              document.getElementById("tree-div").style.height=height+"px";  
              width =  document.body.clientWidth;              
              document.getElementById("tree-div").style.width=width*0.6+"px"; 
              Ext.getCmp("group").setWidth(width*0.6+"px");
              document.getElementById('main').style.marginLeft = "2px";  
//          document.getElementById("main").style.width=width*0.8+"px";  
      }  
      side_resize();  
      window.onresize=side_resize;  */
//      tree.render();
}); 
var callback = function (node){
	if(node.hasChildNodes()) {
		node.eachChild(function(child){
			child.expand();
			/* child.getUI().toggleCheck(node.attributes.checked); 
			child.attributes.checked = node.attributes.checked;  
			mytoggleChecked(child); */
		})
	}
}
var changeNode =[];
function upbutton() {
	var tree = Ext.getCmp("group");
	var node = tree.getSelectionModel().getSelectedNode();
	if(node.previousSibling==null){
		alert("机构已到最上端!");
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
		alert("机构已到最下端!");
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
	var tempsplit='@@';
	for(var i=0;i<changeNode.length;i++){
		childNodes =changeNode[i].parentNode.childNodes;
		for(var j=0;j<childNodes.length;j++){
			ids=ids+childNodes[j].id+"$$"+childNodes[j].leaf+",";
		}
		ids=ids+tempsplit;
	}
	//alert(ids);
	radow.doEvent('YesNew', ids);
}

function closeBtn() {
	window.close();
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
{   
 
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
	<odin:hidden property="checkedgroupid" />
	<odin:hidden property="forsearchgroupid" />
	<odin:hidden property="ereaname" value="<%=ereaname%>" />
	<odin:hidden property="ereaid" value="<%=ereaid%>" />
	<odin:hidden property="manager" value="<%=manager%>" />
	<odin:hidden property="picType" value="<%=picType%>" />

<div id="main" >

<table style="height:100%;" border="0">
	<tr>
		<td valign="top">
			<div id="tree-div" style="overflow: auto; border: 2px solid #c3daf9;float: left;width:100%;height:100%;"></div>
		</td>
		<td width="22px">&nbsp;</td>
		<td align="right">
			<%-- <odin:button property="UpBt" text="上移" handler="upbutton" ></odin:button> --%>
			<div id='UpBt1'></div>
			<br>
			<%-- <odin:button property="DownBt" text="下移" handler="downbutton"></odin:button> --%>
			<div id='DownBt1'></div>
			<br>
			<%-- <odin:button property="YesBtnNew" text="确定" handler="YesBtn"></odin:button> --%>
			<div id='YesBtnNew1'></div>
			<br>
			<%-- <odin:button property="closeBtn" text="关闭"></odin:button> --%>
			<div id='closeBtn1'></div>
		</td>
		<td ></td>

	
	</tr>
</table>
</div>

