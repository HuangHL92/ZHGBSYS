<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>


<%@page import="net.sf.json.JSONArray"%>
<html>
<head>
<link rel="icon" href="/hzb/images/favicon.ico" mce_href="/hzb/images/favicon.ico" type="image/x-icon">  
	<link rel="shortcut icon" href="/hzb/images/favicon.ico" mce_href="/hzb/images/favicon.ico" type="image/x-icon">
	<link rel="stylesheet" type="text/css" href="/hzb/basejs/ext/resources/css/ext-all.css"/>




<script type="text/javascript"src="<%=request.getContextPath()%>/basejs/ext/adapter/ext/ext-base.js"></script>
<script src="<%=request.getContextPath()%>/basejs/ext/ext-all.js"> </script>
<script type="text/javascript" src="<%=request.getContextPath()%>basejs/helperUtil.js"></script>
<script src="<sicp3:rewrite forward='md5'/>"> </script>
<script src="<sicp3:rewrite forward='globals'/>"> </script>
<script src="<%=request.getContextPath()%>/basejs/odin.js"> </script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/odin.css"/>

<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>�����ļ�</title>
<odin:head/>
<odin:MDParam></odin:MDParam>
</head>
<style type = "text/css">
#addResourcePanel{  width:820;height:5px};
/* #pipei{position:relative;left:30px;top: -20px;} */
#addResourceContent{position:relative;left:15px;}
 #infos{width:800} 
#guanli{position:relative;left:20px;}
#span{position:relative;left:263px;top:68px;}
</style>

<script type="text/javascript">
Ext.onReady(function() {
      var Tree = Ext.tree;
      var tree = new Tree.TreePanel( {
 	  	id:'group',
       el : 'tree-div',//Ŀ��div����
       autoScroll : true,
       rootVisible: false,
       split:false,
       width: 224,
       height: 325,
       minSize: 224,
       maxSize: 224,
       border:false,
       animate : true,
       enableDD : false,
       containerScroll : true,
       loader : new Tree.TreeLoader( {
             dataUrl : '<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.sysorg.org.PublicWindow&eventNames=orgTreeJsonDataLeftTree'
       })
   });
	var root = new Tree.AsyncTreeNode({
		checked : false,
		draggable : false,
		id :'-1'//Ĭ�ϵ�nodeֵ��?node=-100
	});
	tree.setRootNode(root);
	tree.render();
  	//ҳ�����
  		/* document.getElementById("addResourceContent").style.width = document.body.clientWidth + "px";
  		document.getElementById("addResourcePanel").style.width = document.body.clientWidth-1 + "px"; */
}); 
function reloadTree() {
	var tree = Ext.getCmp("group");
	//��ȡѡ�еĽڵ�  
	var node = tree.getSelectionModel().getSelectedNode();  
	if(node == null) { //û��ѡ�� ������  
		tree.root.reload();
	} else {        //������ ��Ĭ��ѡ���ϴ�ѡ��Ľڵ�    
	    var path = node.getPath('id');  
	    tree.getLoader().load(tree.getRootNode(),  
	                function(treeNode) {  
	                    tree.expandPath(path, 'id', function(bSucess, oLastNode) {  
	                                tree.getSelectionModel().select(oLastNode);  
	                            });  
	                }, this);    
	}  
}
</script>
<body>
<%@include file="/comOpenWinInit.jsp" %>
<odin:hidden property="uuid"/>
<odin:hidden property="filename"/>
<odin:hidden property="b0111new"/>
<odin:hidden property="b0111p"/>

<odin:hidden property="psncount"/>
<odin:hidden property="count"/>
<odin:hidden property="type11"/>
<odin:hidden property="ftype"/>
<div id="allpanel">
<div id="addResourcePanel"></div>
<iframe width="825" height="85px" src="<%=request.getContextPath() %>/pages/dataverify/DataVerify2.jsp" frameborder=��no�� border=��0�� marginwidth=��0�� marginheight=��0�� scrolling=��no�� allowtransparency=��yes��></iframe>

<div id="addResourceContent">
<odin:groupBox title="ƥ����Ϣ" property="infos">
<table >
	<tr>
		<td height="325px">
			<div id="tree-div"
						style="overflow: auto;height: 100%; width: 250px; border: 2px solid #c3daf9;"></div>
		</td>
		<td>
			<div id="aa2"  style="width: 540px">
				<table style="width: 540px">
				<!-- 
				<tr>
					<td colspan="4" align="center">
						<odin:button text="�½�����" property="xjjg"></odin:button>
					</td>
				</tr>
				 -->
				 <tr id="pipei">
				  	<td align="right"><span style="font-size: 12">ƥ�䷽ʽ:&nbsp;</span></td>
				  	<td ><odin:radio property="xz" label="�Զ�ƥ��" value="1"></odin:radio></td>
				  	<td ></td>
					<td ><odin:radio property="xz" label="�ֶ�ƥ��" value="2"></odin:radio></td>
				 </tr>
				 <tr height="15">
					<td></td><td></td>
					<td></td><td></td>
				</tr>
				 <tr >
					<odin:select2 property="a0165" codeType='ZB130' label="������Ա���:" ></odin:select2>
					<td><font style="font-size: 12">�����Ͻ����ᱻ����</font></td>
					<td>&nbsp;&nbsp;&nbsp;
					<input type="checkbox" checked="checked" id="fxz" /><span style="font-size: 12">����ְ��Ա</span></td>
				</tr>
				<tr height="15">
					<td></td><td></td>
					<td></td><td></td>
				</tr>
				  <tr>
				    <odin:textEdit property="b0101" label="��������:" readonly="true"></odin:textEdit>
					<odin:textEdit property="b0114" label="��������:" readonly="true"></odin:textEdit>
					
				</tr>
				<tr height="15">
					<td></td><td></td>
					<td></td><td></td>
				</tr>
				<tr>
					<odin:textEdit property="psnNum" label="��Ա����:" readonly="true"></odin:textEdit>
					<odin:textEdit property="linkpsn" label="��ϵ��:" readonly="true"></odin:textEdit>
				</tr>
				<tr height="15">
					<td></td><td></td>
					<td></td><td></td>
				</tr>
				<tr>
					<odin:textEdit property="linktel" label="��ϵ�绰:" readonly="true"></odin:textEdit>
					<td></td><td></td>
				</tr>
				<tr height="15">
					<td></td><td></td>
					<td></td><td></td>
				</tr>
				<tr>
					<odin:textarea label="ƥ����Ϣ:" property="info" readonly="true" colspan="4" rows="4"  readonly="true"></odin:textarea>
				</tr>
				</table>
			</div>	
		</td>
	</tr>
 </table>
</odin:groupBox>
</div>
</div>
<odin:toolBar property="btnToolBar" >
	<odin:textForToolBar text="<h3>&nbsp;֧��HZB��ZB3��ʽ����</h3>" />
	<odin:fill />
	<odin:buttonForToolBar id="btnNew1" handler="formSubmit2" text="������ʱ��" icon="images/i_2.gif" isLast="true"
		cls="x-btn-text-icon" />
	<%-- <odin:separator></odin:separator>
	<odin:buttonForToolBar id="btnClose" handler="closeWin" isLast="true"  text="�ر�"
		icon="images/save.gif" cls="x-btn-text-icon" /> --%>
</odin:toolBar>
<odin:panel contentEl="allpanel" width="830" topBarId="btnToolBar" property="ssszzzzss"></odin:panel>

<odin:window src="/blank.htm" id="addOrgImpWin" width="800" height="500"
	title="�½��¼�����ҳ��" modal="true"></odin:window>
<script>
	
function formSubmit2(){
	var filename = document.getElementById('filename').value;
 	var a0165 = document.getElementById('a0165').value;
 	var uuid = document.getElementById('uuid').value;
	var count = document.getElementById('psncount').value;
	var count1 = document.getElementById('count').value;
	var type11 = document.getElementById('type11').value;
	var b0111new = document.getElementById('b0111new').value;
	var count2 =  (Number(count)+Number(count1));
	var ftype = document.getElementById('ftype').value;
	//����ְ��Ա
	var fxz = document.getElementById('fxz').checked;
	if(ftype == '31'){
		odin.alert('���ݰ�Ϊ����Ա�������ݰ�������а���Ա���빦�ܣ�');
		return;
	}
	if(b0111new == ''){
		odin.alert('���Ƚ��л�����Ϣƥ�䣡');
		return;
	}
	/* if(!confirm("���豸��ԭ�����ݣ����ڡ����ݵ��������е������ݡ�������������ȷ�ϡ���������ֹ���롣")){
		
	}  */else {
	if(type11 == "MYSQL" && count2>100000){
		alert("������Ѿ�����"+count1+"�˵����ݣ���ǰ���ݰ��ڰ���"+count+"�˵����ݣ���������������泬��10�������ݻᵼ�����л���");
		var param = {};
		param.uuid = uuid;
		param.filename = filename;
		Ext.Ajax.request({
				url:'<%=request.getContextPath()%>/UploadFileServlet?method=zzbFileImpZzb&uuid='+uuid+'&filename='+filename+'&b0111new='+b0111new+'&a0165='+a0165+'&fxz='+fxz,
				params : {'uuid':uuid,'filename':filename},
				success:function(){
					//realParent.odin.ext.getCmp('simpleExpWin').hide();
					parent.Ext.getCmp(subWinId).close(); 
				}
			});
		realParent.$h.showWindowWithSrcMin('simpleExpWin5',contextPath +'/pages/repandrec/local/KingbsWinfresh1.jsp?id=' + uuid,'��������',500,240);
			
	}else{
		var param = {};
		param.uuid = uuid;
		param.filename = filename;
		Ext.Ajax.request({
				url:'<%=request.getContextPath()%>/UploadFileServlet?method=zzbFileImpZzb&uuid='+uuid+'&filename='+filename+'&b0111new='+b0111new+'&a0165='+a0165+'&fxz='+fxz,
				params : {'uuid':uuid,'filename':filename},
				success:function(){
					//realParent.odin.ext.getCmp('simpleExpWin').hide();
					parent.Ext.getCmp(subWinId).close(); 
				}
			});
		realParent.$h.showWindowWithSrcMin('simpleExpWin5',contextPath +'/pages/repandrec/local/KingbsWinfresh1.jsp?id=' + uuid,'��������',500,240);
	}

	}
}
function doCloseWin1(type){
	parent.odin.ext.getCmp('simpleExpWin11111').close();
}

//�رմ���
function closeWin(){
	parent.odin.ext.getCmp('impNewHzbORGWin').close();
}

function changeData(){
	alert("��⵽���ݰ��汾���ɣ���ʹ��2018��HZB���ݰ���");
	window.location.reload();
}
</script>
<odin:response onSuccess="doSuccess"/>
</body>
</html>