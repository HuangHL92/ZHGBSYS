<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@page import="com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_003.SetInfoGroupWindowPageModel"%>
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
          el : 'tree-div',//Ŀ��div����
          split:false,
          width: 164,
          minSize: 164,
          maxSize: 164,
          rootVisible: false,
          autoScroll : true,
          animate : true,
          border:false,
          enableDD : true,
          containerScroll : true,
          loader : new Tree.TreeLoader( {
                dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysmanager.ZWHZYQ_001_003.SetInfoGroupWindow&eventNames=orgTreeJsonData'
          })
      });
    }else{
      var tree = new Tree.TreePanel( {
    	  id:'group',
          el : 'tree-div',//Ŀ��div����
          split:false,
          width: 164,
          minSize: 164,
          maxSize: 164,
          rootVisible: false,
          autoScroll : true,
          animate : true,
          border:false,
          enableDD : true,
          containerScroll : true,
          loader : new Tree.TreeLoader( {
                dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysmanager.ZWHZYQ_001_003.SetInfoGroupWindow&eventNames=orgTreeJsonData'
          })
      });
    }
    var root = new Tree.AsyncTreeNode( {
          text :  document.getElementById('ereaname').value,
          draggable : false,
          id : document.getElementById('ereaid').value,//Ĭ�ϵ�nodeֵ��?node=-100
          href:"javascript:radow.doEvent('querybyid','"+document.getElementById('ereaid').value+"')"
    });
    tree.setRootNode(root);
    tree.render();
    root.expand();
    
    var treegrid = new Ext.tree.ColumnTree({
    	id:'treegrid',
        el:'tree',
        width:677,
        autoHeight:true,
        rootVisible:false,
        autoScroll:true,
        title: '��Ϣ���б�',
        
        columns:[{
            header:'Ȩ����',
            width:175,
            dataIndex:'task'
        },{
            header:'��Ϣ��',
            width:350,
            dataIndex:'duration'
        },{
            header:'ѡ��',
            width:150,
            dataIndex:'user'
        }],

        loader: new Ext.tree.TreeLoader({
            dataUrl:'radowAction.do?method=doEvent&pageModel=pages.sysmanager.ZWHZYQ_001_003.SetInfoGroupWindow&eventNames=orgTreeGridJsonData',
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
     
     var t = Ext.getCmp("treegrid"); 
     t.collapseAll();
     var s = t.getRootNode().childNodes;
     var groupid = document.getElementById('groupid').value;
     for(var i=0;i<s.length;i++){
    	 if(groupid==s[i].id.substring(0,(s[i].id.length-1))){
    		 s[i].expand();
    		 break;
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
/* function InfotypeVal(value, params, rs, rowIndex, colIndex, ds){
	if(value=='1'){
		return '������Ϣ';
	}else if(value=='2'){
		return '����һ��';
	}else if(value=='3'){
		return '������';
	}else if(value=='4'){
		return '������Ϣ';
	}else if(value=='5'){
		return '�Զ�����Ϣ��';
	}else{
		return '��Ч';
	}
} */
</script>

<% 
	String ereaname = (String)(new SetInfoGroupWindowPageModel().areaInfo.get("areaname"));
	String ereaid = (String)(new SetInfoGroupWindowPageModel().areaInfo.get("areaid"));
	String manager = (String)(new SetInfoGroupWindowPageModel().areaInfo.get("manager"));
%>
<odin:toolBar property="treeDivBar">
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="�½�" id="CreateInfoGroupBtn" tooltip="�½���Ϣ����"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="�޸�" id="ModifyInfoGroupBtn" tooltip="�޸���Ϣ����"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="ɾ��" id="DeleteInfoGroupBtn" tooltip="ɾ����Ϣ����"/>
</odin:toolBar>
<div id="groupTreeContent" style="height:100%">
<table width="100%" border="1">
	<tr>
		<td width="175" valign="top">
			<div id="tree-div"
					style="overflow:auto; height: 460px; width: 175px; border: 2px solid #c3daf9;">
					<div id="1"></div>
				<odin:panel contentEl="1" property="groupPanel" topBarId="treeDivBar"></odin:panel>
					</div>
				
		</td>
		<td>
		<div id="tree" style="align:left top;width:100%;height:100%"></div>
			<table>
				<tr>
					<odin:hidden property="checkedgroupid"/>
					<odin:hidden property="forsearchgroupid"/>
					<odin:hidden property="ereaname" value="<%=ereaname%>" />
					<odin:hidden property="ereaid" value="<%=ereaid%>" />
					<odin:hidden property="manager" value="<%=manager%>" />
					<odin:hidden property="groupid"/>
				</tr>
			</table>
		</td>
	</tr>
</table>
</div>

<odin:toolBar property="btnToolBar">
	<odin:textForToolBar text="<h3>��Ϣ��Ȩ�������</h3>" />
	<odin:fill />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="����" handler="dogant" tooltip="����" isLast="true"/>
</odin:toolBar>
<odin:panel contentEl="groupTreeContent" property="groupTreePanel" topBarId="btnToolBar"></odin:panel>

<odin:window src="/blank.htm" id="CreateIGWin" width="500" height="150" title="��Ϣ����༭ҳ��" modal="true"></odin:window>	
	



