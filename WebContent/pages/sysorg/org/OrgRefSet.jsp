<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ page import="com.insigma.siis.local.pagemodel.sysorg.org.OrgSortPageModel"%>
<%@include file="/comOpenWinInit.jsp" %>
<style>

</style>
<script type="text/javascript">
var treechecked="";
// 	var map = new Map();
Ext.onReady(function() {
	var man = document.getElementById('manager').value;
      var Tree = Ext.tree;
      var tree = new Tree.TreePanel( {
    	  id : 'group',
          el : 'tree-div',//Ŀ��div����
          split:false,
          //width:510,
          minSize: 200,
          rootVisible: false,
          autoScroll : true,
          animate : true,
          border:false,
          enableDD : false,
          containerScroll : true,
          loader : new Tree.TreeLoader( {
                dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysorg.org.PublicWindow&eventNames=orgTreeJsonInfoImp'
          }),
          listeners:{
        	  scope:this,
				checkchange:function(node,checked){
// 					node.attributes.checked=checked;
				  	loop(node)
// 					var id=node.attributes['id'];
// 					var chs=tree.getChecked();
// 					for(var i=0;i<chs.length;i++){
// 						if(checked==true){//��ǰ�ڵ㱻ѡ��
// 							if(chs[i].attributes['id'].length<id.length
// 									&&id.substring(0,chs[i].attributes['id'].length)==chs[i].attributes['id']){//ѭ���ڵ�С�ڵ�ǰ�ڵ�,����ֱ���ϼ� ����ȡ��ѡ��
// 								chs[i].ui.toggleCheck(false);//ѭ���ڵ�ȡ��ѡ��
// 							}
// 							if(chs[i].attributes['id'].length>id.length
// 									&&chs[i].attributes['id'].substring(0,id.length)==id){//ѭ���ڵ㳤�ȴ��ڵ�ǰ�ڵ����ǵ�ǰ�ڵ�ֱ���¼�
// 								chs[i].ui.toggleCheck(false);//ѭ���ڵ�ȡ��ѡ��
// 							}
// 						}
// 					}
// 					var treeRoot = tree.getRootNode();
// 					backopernode(treeRoot,id);
				},
				'expandnode':function(node){
	        		//��ȡչ���ڵ����Ϣ
	        		if(node.attributes.tag=="1"){
	        			node.attributes.tag="2";
	        			for(var i=0;i<node.childNodes.length;i++){
	        			 	node.childNodes[i].ui.checkbox.checked=true;
	        			 	node.childNodes[i].attributes.checked = true;
	        			 	node.childNodes[i].attributes.tag="1";
	        			}
					}else if(node.attributes.tag=="0"){
						node.attributes.tag="2";
						for(var i=0;i<node.childNodes.length;i++){
	        			 	node.childNodes[i].ui.checkbox.checked=false;
	        			 	node.childNodes[i].attributes.checked = false;
	        			 	node.childNodes[i].attributes.tag="0";
	        			 }
					}
	        	}
	        }
      });
      treechecked=tree;
      var root_id=document.getElementById("subWinIdBussessId").value;
      if(root_id==''||root_id=='null'||root_id==null){//��ҳ��û��ѡ�����
    	  root_id=document.getElementById('ereaid').value;
      }else{//��ҳ��ѡ�����
    	  if(root_id.length>7){//��ֹ����id��ȡ�����жϳ��ȣ���ȡ���ڵ�id����Ϊ���ڵ�
    		  root_id=root_id.substring(0,root_id.length-4);
    	  }else{
    		  root_id='-1';
    	  }
      }
     // document.getElementById('ereaid').value=root_id;
      var root = new Tree.AsyncTreeNode( {
          text :  '',
          iconCls : document.getElementById('picType').value,
          draggable : false,
          id : root_id,
          href:"javascript:radow.doEvent('querybyid','"+root_id+"')"
    });
      tree.setRootNode(root);
      tree.render();
      root.expand(false,true, callback);

      new Ext.Button({
  		id:'DownBt',
  	    text:'��&nbsp;&nbsp;��',
  	    //cls :'inline pl',
  	    renderTo:"DownBt1",
  	    handler:function(){
  	    	exportExcel();
  	    }
  	});
}); 
var callback = function (node){
	if(node.hasChildNodes()) {
		node.eachChild(function(child){
			child.expand();
		})
	}
}
function backopernode(treeRootNode, id){
	var childNodes = treeRootNode.childNodes;
	for(var i=0;i<childNodes.length;i++){
		var nodeCh = childNodes[i];
		var idCh=nodeCh.attributes['id'];
		if(idCh.length>id.length
				&&idCh.substring(0,id.length)==id){
// 			nodeCh.attributes.checked=true;
			nodeCh.ui.toggleCheck(true);
		}
		if(nodeCh.childNodes.length > 0){
			backopernode(nodeCh,id);
		}
	}
}
function loop(node) {
	if (node.ui.checkbox.checked == true) {
		//node.expand();
		node.attributes.tag="1";
		node.attributes.checked = true;
		if (node.childNodes.length > 0) {
			for (var i = 0; i < node.childNodes.length; i++) {
			  	  node.childNodes[i].attributes.tag="1";
				  node.childNodes[i].ui.checkbox.checked=true;
// 				  changeNode += node.childNodes[i].id+":"+node.childNodes[i].ui.checkbox.checked+":1,";
				  loop(node.childNodes[i]);
			}
		}
	} else {
		node.attributes.tag="0";
// 		map.set(node.attributes['id'], 0);
		node.ui.checkbox.checked = false;
		node.attributes.checked = false;
		for (var i = 0; i < node.childNodes.length; i++) {
		  node.childNodes[i].attributes.tag="0";
		  node.childNodes[i].ui.checkbox.checked=false;
// 		  changeNode += node.childNodes[i].id+":"+node.childNodes[i].ui.checkbox.checked+":0,";
		  loop(node.childNodes[i]);
		}
	}
}
//�����б�����

function exportExcel(){
	var idcheckedarr="";
	var root_id=document.getElementById('ereaid').value;
	var orgref=document.getElementById('orgref').value;
	
	var chs=treechecked.getChecked();
	for(var i=0;i<chs.length;i++){
		idcheckedarr=idcheckedarr+chs[i].attributes['id']+"@"+chs[i].attributes['text']+",";
	}
	if(idcheckedarr.length>0){
		idcheckedarr.substring(0,idcheckedarr.length-1);
	}
	var path = '<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.sysorg.org.OrgRefSet&eventNames=exportExcel';
	ShowCellCover('start','ϵͳ��ʾ','���ڵ���������Ϣ ,�����Ե�...');
   	Ext.Ajax.request({
   		timeout: 900000,
   		url: path,
   		async: true,
   		method :"post",
   		params : {
   			'orgref':orgref,
			'idcheckedarr':idcheckedarr,
			'root_id':root_id
   		},
		callback: function (options, success, response) {
			if (success) {
				var result = response.responseText;
				if(result){
					//Ext.Msg.hide();
					Ext.Msg.alert('ϵͳ��ʾ','���óɹ���', function(){realParent.radow.doEvent('orgInfoGrid.dogridquery');parent.Ext.getCmp('orgimpWin').close();});
				}
      	   }
        }
   });
}

function ShowCellCover(elementId, titles, msgs) {	
	Ext.MessageBox.buttonText.ok = "�ر�";
	if(elementId.indexOf("start") != -1){
	
		Ext.MessageBox.show({
			title:titles,
			msg:msgs,
			width:300,
	        height:300,
			closable:false,
			modal:true,
			progress:true,
			wait:true,
			animEl: 'elId',
			increment:5, 
			waitConfig: {interval:150}
		});
	}
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
	<odin:hidden property="idcheckedarr"/><!-- ��ѡ�еĽڵ� -->
	<odin:hidden property="ereaname" value="<%=ereaname%>" />
	<odin:hidden property="ereaid" value="<%=ereaid%>" />
	<odin:hidden property="manager" value="<%=manager%>" />
	<odin:hidden property="picType" value="<%=picType%>" />
<div id="main" >


<table style="height:100%;width:100%;" border="0">
	<tr>
		<odin:select2 property="orgref" codeType="SSKSBM" value="01" required="true" label="��������"></odin:select2>
		<td width="10px">
			<div id='DownBt1'></div>
		</td>
	</tr>
	<tr>
		<td valign="top" colspan="3" style="height:100%;width:100%;"><!-- s b -->
			<div id="tree-div" style="overflow: auto; border: 2px solid #c3daf9;float: left;width:100%;height:100%;"></div>
		</td>
	</tr>
</table>
</div>

<script type="text/javascript">
function setWidthHeight(){
	var height=document.body.clientHeight;
	var width=document.body.clientWidth;
	document.getElementById("tree-div").style.width=width-5+'px';
}
Ext.onReady(function() {	
	window.onresize=setWidthHeight;
	setWidthHeight();
});
</script>
