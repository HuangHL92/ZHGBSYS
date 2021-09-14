<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@include file="/comOpenWinInit.jsp" %>
<link rel="stylesheet" type="text/css" href="../../resources/css/ext-all.css" />
<style>


</style>

	<!-- ���ݹ��ݿͻ�Ҫ����Ϣ�����ɫ����Ȩ����ɫ����Ϣ������useridʵ����ת�����roleid !!!!!!! -->

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
	//var man = document.getElementById('manager').value;
    var Tree = Ext.tree;
    
    var treegrid = new Ext.tree.ColumnTree({
    	id:'treegrid',
        el:'tree',
        width:650,
        autoHeight:true,
        rootVisible:false,
        autoScroll:true,
        title: '��Ϣ��Ȩ���б�',
        
        columns:[{
            header:'��Ϣ��',
            width:230,
            dataIndex:'task'
        },{
            header:'��Ϣ��',
            width:220,
            dataIndex:'duration'
        },{
            header:'�鿴',
            width:99,
            dataIndex:'look'
        },{
            header:'�޸�',
            width:99,
            dataIndex:'change'
        }/* ,{
            header:'У��',
            width:82,
            dataIndex:'checkout'
        } */],


        loader: new Ext.tree.TreeLoader({
            dataUrl:'radowAction.do?method=doEvent&pageModel=pages.cadremgn.sysmanager.authority.TableCol&eventNames=orgTreeTableJsonData&userid='+document.getElementById('subWinIdBussessId').value,
            uiProviders:{
                'col': Ext.tree.ColumnNodeUI
            }
        }),
        root: new Ext.tree.AsyncTreeNode({
            text:'Tasks' 

        }),
        listeners:{
        	/* 'expandnode':function(node){
        		//��ȡչ���ڵ����Ϣ
        		var look=node.attributes.look;
        		var change=node.attributes.change;
        		var checkout=node.attributes.checkout;
        		var array = new Array(look,change,checkout);
        		//alert(array.length);
        		for(var i=0;i<array.length;i++){
        			var th_ = array[i];
        			//console.log(th_);
        			var th_look = th_.split('"');
            		var id = th_look[5];
            		var dom = document.getElementById(id);//��ȡ����Ľڵ�
          			if(dom.checked){
          				var fid = document.getElementById('tree');
              			var box = fid.getElementsByTagName('input');
          				for(var j = 0; j < box.length; j++){
              				if(box[j].name == dom.name){
              					box[j].checked = true;
              				}
              			}
          			}
        		}
        		
        	} */
        }
    });
    treegrid.render();
    //treegrid.expandAll();
});

var callback = function (node){//��չ���¼�
	if(node.hasChildNodes()) {
		node.eachChild(function(child){
			child.expand();
		})
	}
}

function clickCheck(th,num){
	var checked = th.checked;
	//alert(checked);
	if(num =='1'){//�������  ���ڵ�
		 //չ����
		 var ids = th.id;
		 var id = ids.split('|')[0];
	
		 var root = Ext.getCmp("treegrid").root;
		 for(var i=0;i<root.childNodes.length;i++){
			 var nod = root.childNodes[i];
			 //console.log(nod);
			 if(nod.attributes.id==id){
				 nod.expand();
				 break;
			 }
		 }
		 var fid = document.getElementById('tree');
	     var box = fid.getElementsByTagName('input');
	     for (var i = 0; i < box.length; i++) {
	    	 if(box[i].name==th.name){
	    		 box[i].checked=checked;
	    	 }
	     }
	}
}

function reloadTree() {
    var tree = Ext.getCmp("group");
    tree.root.reload();
    tree.expandAll();
}

function saveFunc(){
	var fid = document.getElementById('tree');
    var box = fid.getElementsByTagName('input');
	var count = 0;
    var result = '';
    for (var i = 0; i < box.length; i++) {
        if (box[i].type == 'checkbox') {
            result = result + box[i].id + ':' + box[i].name + ':' + box[i].checked + ',';
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
var islook = true;
//ȫ���鿴
function allLook(){
	if(islook){
		islook = false;
	}else{
		islook = true;
	}
	var checked = islook;
	var tree = Ext.getCmp("treegrid");
    tree.expandAll();

	var fid = document.getElementById('tree');
	var box = fid.getElementsByTagName('input');
	for (var i = 0; i < box.length; i++) {
	   	if(box[i].name.indexOf("LOOK")!=-1 ){
	    	box[i].checked=checked;
	    }
	}
}

var ischange = true;
//ȫѡ�޸�
function allChange(){
	if(ischange){
		ischange = false;
	}else{
		ischange = true;
	}
	var checked = ischange;
	var tree = Ext.getCmp("treegrid");
    tree.expandAll();

	var fid = document.getElementById('tree');
	var box = fid.getElementsByTagName('input');
	for (var i = 0; i < box.length; i++) {
	   	if(box[i].name.indexOf("CHANGE")!=-1 ){
	    	box[i].checked=checked;
	    }
	}
}

var isJy = true;
//ȫѡУ��
function allJy(){
	if(isJy){
		isJy = false;
	}else{
		isJy = true;
	}
	var checked = isJy;
	var tree = Ext.getCmp("treegrid");
    tree.expandAll();

	var fid = document.getElementById('tree');
	var box = fid.getElementsByTagName('input');
	for (var i = 0; i < box.length; i++) {
	   	if(box[i].name.indexOf("CHECKOUT")!=-1 ){
	    	box[i].checked=checked;
	    }
	}
}

</script>

<odin:toolBar property="treeDivBar">
	<odin:textForToolBar text="�û������������б�"></odin:textForToolBar>
</odin:toolBar>
<div id="groupTreeContent" style="height:100%">
<table width="100%" border="1">
	<tr>
		<td>
		<div id="tree" style="align:left top;width:100%;height:520px;overflow:auto;"></div>
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
	<odin:buttonForToolBar text="ȫѡ(ȫȡ��)�鿴" tooltip="ȫѡ(ȫȡ��)�鿴" handler="allLook"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="ȫѡ(ȫȡ��)�޸�" tooltip="ȫѡ(ȫȡ��)�޸�" handler="allChange"/>
	<%-- <odin:separator></odin:separator>
	<odin:buttonForToolBar text="ȫѡУ��" tooltip="ȫѡУ��" handler="allJy"/> --%>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="����" tooltip="����" handler="saveFunc" isLast="true"/>
</odin:toolBar>
<odin:panel contentEl="groupTreeContent" property="groupTreePanel" topBarId="btnToolBar"></odin:panel>

<odin:window src="/blank.htm" id="CreateIGWin" width="500" height="150" title="��Ϣ����༭ҳ��" modal="true"></odin:window>	



