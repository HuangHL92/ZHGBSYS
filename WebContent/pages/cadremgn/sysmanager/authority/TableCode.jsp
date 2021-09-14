<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@include file="/comOpenWinInit.jsp" %>
<link rel="stylesheet" type="text/css" href="../../resources/css/ext-all.css" />
<style>


</style>
    <!-- GC -->
 	<!-- LIBS -->
 	<script type="text/javascript" src="../../adapter/ext/ext-base.js"></script>
 	<!-- ENDLIBS -->

    <script type="text/javascript" src="../../ext-all.js"></script>

    <script type="text/javascript" src="basejs/ColumnNodeUI.js"></script>

    <!-- Common Styles for the examples -->
    <link rel="stylesheet" type="text/css" href="../examples.css" />

    <link rel="stylesheet" type="text/css" href="basejs/ext/ux/css/column-tree.css" />
<script  type="text/javascript" src="basejs/jquery.js"></script>
<script  type="text/javascript" src="basejs/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script type="text/javascript">
var ids="";
Ext.onReady(function() {
	 var Tree = Ext.tree;
	
	var treegrid = new Ext.tree.ColumnTree({
    	id:'treegrid',
        el:'tree',
        width:726,
        autoHeight:true,
        rootVisible:false,
        autoScroll:true,
        title: '信息集权限列表',
        
        columns:[{
            header:'信息集',
            width:280,
            dataIndex:'task'
        },{
            header:'查看',
            width:110,
            dataIndex:'look'
        },{
            header:'新增',
            width:110,
            dataIndex:'add'
        },{
            header:'修改',
            width:110,
            dataIndex:'change'
        },{
            header:'删除',
            width:110,
            dataIndex:'del'
        }],

        loader: new Ext.tree.TreeLoader({
            dataUrl:'radowAction.do?method=doEvent&pageModel=pages.cadremgn.sysmanager.authority.TableCode&eventNames=orgTreeTableJsonData&userid='+document.getElementById('subWinIdBussessId').value,
            uiProviders:{
                'col': Ext.tree.ColumnNodeUI
            }
        }),
        root: new Ext.tree.AsyncTreeNode({
            text:'Tasks' 

        })
    });
    treegrid.render();
    treegrid.expandAll();
});

var callback = function (node){//仅展看下级
	if(node.hasChildNodes()) {
		node.eachChild(function(child){
			child.expand();
		})
	}
}

function clickCheck(th,num){
	var checked = th.checked;
	//alert(checked);
	if(num =='1'){//点击的是  树节点
		 //展开树
		 var id = th.id;
	
		 var root = Ext.getCmp("treegrid").root;
		 for(var i=0;i<root.childNodes.length;i++){
			 var nod = root.childNodes[i];
			 //console.log(nod);
			 
			 var ids = nod.attributes.id;
			 var nodid = ids.split('|')[1];
			 if(nodid==id){
				 nod.expand();
				 break;
			 }
		 }
		 var fid = document.getElementById('tree');
	     var box = fid.getElementsByTagName('input');
	     for (var i = 0; i < box.length; i++) {
	    	 var names = box[i].name;
	    	 var nodName = names.split('|')[1];
	    	 if(nodName==th.name){
	    		 box[i].checked=checked;
	    	 }
	     }
	}
}

function reloadTree() {
    var tree = Ext.getCmp("group");
    tree.root.reload();
    tree.root.expand(false,true, callback);//默认展开
}

function saveFunc(){
	var fid = document.getElementById('tree');
    var box = fid.getElementsByTagName('input');
	var count = 0;
    var result = '';
    for (var i = 0; i < box.length; i++) {
        if (box[i].type == 'checkbox') {
        	if(box[i].id == 'LOOK'||box[i].id == 'ADD'||box[i].id == 'CHANGE'||box[i].id == 'DEL'){
        		
        	}else{
                result = result + box[i].id + ':' + box[i].name + ':' + box[i].checked + ',';
        	}
        }
    }
   ids=result;
   radow.doEvent('dogrant',ids);
}
</script>

<odin:toolBar property="treeDivBar">
	<odin:textForToolBar text="用户及所属部门列表"></odin:textForToolBar>
</odin:toolBar>
<div id="groupTreeContent" style="height:100%">
<table width="100%" border="1">
	<tr>
		<td>
		<div id="tree" style="align:left top;width:100%;height:100%;overflow:auto;"></div>
			<table>
				<tr>
					<odin:hidden property="groupid"/>
					<odin:hidden property="userid"/>
				</tr>
			</table>
		</td>
	</tr>
</table>
</div>

<odin:toolBar property="btnToolBar">
	<odin:commformtextForToolBar text="" property="text11"></odin:commformtextForToolBar>
	<odin:fill />
	<odin:buttonForToolBar text="保存" tooltip="保存" handler="saveFunc" isLast="true"/>
</odin:toolBar>
<odin:panel contentEl="groupTreeContent" property="groupTreePanel" topBarId="btnToolBar"></odin:panel>

<odin:window src="/blank.htm" id="CreateIGWin" width="500" height="150" title="信息项组编辑页面" modal="true"></odin:window>	



