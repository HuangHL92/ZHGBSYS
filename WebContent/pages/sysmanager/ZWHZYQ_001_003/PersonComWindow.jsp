<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@page import="com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_003.PersonComWindowPageModel"%>
<link rel="stylesheet" type="text/css" href="../../resources/css/ext-all.css" />

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
<script type="text/javascript">
var ids="";
Ext.onReady(function() {
	var man = document.getElementById('manager').value;
    var Tree = Ext.tree;
    if(man == 'true'){
	       var tree = new Tree.TreePanel( {
	      id:'group',
          el : 'tree-div',//目标div容器
          split:false,
          width: 164,
          minSize: 164,
          maxSize: 164,
          rootVisible: false,
          autoScroll : true,
          height : Ext.getBody().getHeight()-80,
          animate : true,
          border:false,
          enableDD : true,
          containerScroll : true,
          loader : new Tree.TreeLoader( {
                dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysorg.org.PublicWindow&eventNames=orgTreeJsonDataLeftTree'
          })
      });
    }else{
      var tree = new Tree.TreePanel( {
    	  id:'group',
          el : 'tree-div',//目标div容器
          split:false,
          width: 164,
          minSize: 164,
          maxSize: 164,
          height : Ext.getBody().getHeight()-80,
          rootVisible: false,
          autoScroll : true,
          animate : true,
          border:false,
          enableDD : true,
          containerScroll : true,
          loader : new Tree.TreeLoader( {
                dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysorg.org.PublicWindow&eventNames=orgTreeJsonDataLeftTree'
          })
      });
    }
    var root = new Tree.AsyncTreeNode( {
          text :  document.getElementById('ereaname').value,
          draggable : false,
          id : document.getElementById('ereaid').value,//默认的node值：?node=-100
          href:"javascript:radow.doEvent('querybyid','"+document.getElementById('ereaid').value+"')"
    });
    tree.setRootNode(root);
    tree.render();
    root.expand();
    var userid = getValue();
    var treegrid = new Ext.tree.ColumnTree({
    	id:'treegrid',
        el:'tree',
        width:677,
        height : 415,
       // autoHeight:true,
        rootVisible:false,
        autoScroll:true,
        title: '不可见公务员列表',
        
        columns:[{
            header:'姓名',
            width:175,
            dataIndex:'task'
        },{
            header:'选择',
            width:150,
            dataIndex:'user'
        }],

        loader: new Ext.tree.TreeLoader({
            dataUrl:'radowAction.do?method=doEvent&pageModel=pages.sysmanager.ZWHZYQ_001_003.PersonComWindow&eventNames=orgTreeGridJsonData&userid='+userid,
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

function reloadTree() {
    var tree = Ext.getCmp("group");
    tree.root.reload();
    tree.expandAll();
}
function reloadGridTree() {
    var tree = Ext.getCmp("treegrid");
    tree.root.reload();
    tree.expandAll();
}
function checked(str){
	 var infoids = str.split(',');
	 var fid = document.getElementById('tree');
     var box = fid.getElementsByTagName('input');
     for (var i = 0; i < box.length; i++) {
    	 box[i].checked=false;
     }
     for (var i = 0; i < box.length; i++) {
        for(var j=0;j<infoids.length;j++){
        	if(box[i].id==infoids[j]){
        		box[i].checked=true;
        	}
        }
     }
}
function dogant(){
	 var fid = document.getElementById('tree');
    var box = fid.getElementsByTagName('input');
	 var count = 0;
    var result = '';
    for (var i = 0; i < box.length; i++) {
        if (box[i].type == 'checkbox' && box[i].checked) {
            result = result + box[i].id + ',';
        }
    }
   ids=result;
	 radow.doEvent('dogrant',ids);
}

function operateType(){
	//reloadTree();
	var tree = Ext.getCmp("group");
    tree.root.reload();
	reloadGridTree();
}
function getValue(){   
    var URLParams = new Array();    
    var aParams = document.location.search.substr(1).split('&'); 
    for (i=0; i < aParams.length;i++){    
        var aParam = aParams[i].split('=');   
        URLParams[aParam[0]] = aParam[1];    
    }   
    return URLParams["userid"];   
}
/* function InfotypeVal(value, params, rs, rowIndex, colIndex, ds){
	if(value=='1'){
		return '基本信息';
	}else if(value=='2'){
		return '三龄一历';
	}else if(value=='3'){
		return '拟任免';
	}else if(value=='4'){
		return '其他信息';
	}else if(value=='5'){
		return '自定义信息项';
	}else{
		return '无效';
	}
} */
</script>

<% 
	String ereaname = (String)(new PersonComWindowPageModel().areaInfo.get("areaname"));
	String ereaid = (String)(new PersonComWindowPageModel().areaInfo.get("areaid"));
	String manager = (String)(new PersonComWindowPageModel().areaInfo.get("manager"));
%>
<div id="groupTreeContent" style="height:100%">
	<table width="100%" border="1">
		<tr>
			<odin:select property="operateType" data="['0','职务层次'],['1','管理类别'],['2','自定义']" label="人员类别" onchange="operateType"></odin:select>
		</tr>
		<tr>
			<td width="175">
				<table width="175">
					<tr>
						<td>
							<div id="tree-div" style="overflow:auto; height: 410px; width: 175px; border: 2px solid #c3daf9;">
								<div id="1"></div>
							</div>
						</td>
					</tr>
				</table>
			</td>
			<td>
				<div id="tree" style="align:left top;width:100%;height:85%"></div>
				<table>
					<tr>
						<odin:hidden property="checkedgroupid"/>
						<odin:hidden property="forsearchgroupid"/>
						<odin:hidden property="ereaname" value="<%=ereaname%>" />
						<odin:hidden property="ereaid" value="<%=ereaid%>" />
						<odin:hidden property="manager" value="<%=manager%>" />
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>

<odin:toolBar property="btnToolBar">
	<odin:textForToolBar text="<h3>人员查看权限管理</h3>" />
	<odin:fill />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="保存" handler="dogant" tooltip="将勾选人员设为不可见，未勾选的则为可见人员" isLast="true"/>
</odin:toolBar>
<odin:panel contentEl="groupTreeContent" property="groupTreePanel" topBarId="btnToolBar"></odin:panel>

