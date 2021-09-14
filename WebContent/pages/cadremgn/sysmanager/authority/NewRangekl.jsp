<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@page import="com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_003.SetInfoGroupWindowPageModel"%>
<%@include file="/comOpenWinInit2.jsp" %>
<link rel="stylesheet" type="text/css" href="../../resources/css/ext-all.css" />
<%
//add gy-linjun 2019-11-4
String ctxPath=request.getContextPath(); 
%>
<style>
.qxbutton {
	font-size:13px !important;
	width: 145px !important;
	height: 30px !important;
	/* margin-left: 30px;
	margin-top:20px;
	margin-bottom: 20px; 
	margin: 30px !important;*/
	margin: 30px 45px !important;
}
#one {
	position: absolute;
	left: 315px;
	top: 189px;
	width: 20px;
}
#two {
	position: absolute;
	left: 553px;
	top: 189px;
	width: 20px;
}
#three {
	position: absolute;
	left: 315px;
	top: 280px;
	width: 20px;
}
#four {
	position: absolute;
	left: 553px;
	top: 280px;
	width: 20px;
} 
/* #five {
	position: absolute;
	left: 315px;
	top: 371px;
	width: 20px;
}  */
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
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/qdstyle.css" />
<script  type="text/javascript" src="basejs/jquery.js"></script>
<script  type="text/javascript" src="basejs/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script type="text/javascript">
var ids="";
var user;
Ext.onReady(function() {
	//var man = document.getElementById('manager').value;
    var Tree = Ext.tree;
    var ss = document.getElementById('subWinIdBussessId').value;
	var v = ss.substring(ss.length-2);
	var user1;
	if(v=='&1'){
		user1 = ss.substring(0,ss.length-2);
	}else{
		user1 = ss;
	}
	var tree = new Tree.TreePanel( {
	      id:'group',
          el : 'tree-div',//Ŀ��div����
          split:false,
          /* width: 164, */
          minSize: 164,
          maxSize: 164,
          rootVisible: false,
          autoScroll : true,
          animate : true,
          border:false,
          enableDD : true,
          containerScroll : true ,
          loader : new Tree.TreeLoader( {
                dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.cadremgn.sysmanager.authority.NewRangekl&eventNames=orgTreeJsonData',
                //baseParams : {userid: ''}
                baseAttrs: { uiProvider: Ext.tree.TreeCheckNodeUI }
          }),
          listeners : {
  			'click' : function(node) {
  				var userid  = node.id;
  				document.getElementById('userid').value = userid;    
  				//alert("userid"+userid+"**user1="+user1);
  				var param = userid + "," + user1
  				radow.doEvent("chooseUser",param);
  				Ext.MessageBox.alert('��ʾ','Ȩ�޿�¡�ɹ�');
  				/* var loader = Ext.getCmp("treegrid").getLoader();
  				Ext.apply(loader.baseParams,{userid: node.id});
  				Ext.getCmp("treegrid").root.reload();
  				Ext.getCmp("treegrid").expandAll(); */
  				
  			//	Ext.getCmp('id').close();
  			}
  		  }
      });
   
    var root = new Tree.AsyncTreeNode( {
          text :  document.getElementById('ereaname').value,
          draggable : false,
          id : '-1'/* ,//Ĭ�ϵ�nodeֵ��?node=-100
          href:"javascript:radow.doEvent('querybyid','"+document.getElementById('ereaid').value+"')" */
    });
    tree.setRootNode(root);
    tree.render();
    root.expand(false,true, callback);//Ĭ��չ��
    //tree.expandAll();
});

var callback = function (node){//��չ���¼�
	if(node.hasChildNodes()) {
		node.eachChild(function(child){
			child.expand();
		})
	}
}

</script>
<% 
	String ereaname = (String)(new SetInfoGroupWindowPageModel().areaInfo.get("areaname"));
	String ereaid = (String)(new SetInfoGroupWindowPageModel().areaInfo.get("areaid"));
	String manager = (String)(new SetInfoGroupWindowPageModel().areaInfo.get("manager"));
%>
<odin:toolBar property="treeDivBar">
	<odin:textForToolBar text="��¡�û�Ȩ���б�"></odin:textForToolBar>
	<odin:fill />
	<odin:separator></odin:separator>

</odin:toolBar>
<div id="groupTreeContent" style="height:100%;width: 100%">
<table width="100%">
	<tr>
		<td width="275" valign="top">
			<div id="tree-div" style="overflow:auto; height: 530px; width: 425px; border: 2px solid #c3daf9;">
					<div id="1"></div>
				<odin:panel contentEl="1" property="groupPanel" topBarId="treeDivBar"></odin:panel>
					</div>
		</td>
		<td>
			<table>
				<tr>
					<odin:hidden property="checkedgroupid"/>
					<odin:hidden property="forsearchgroupid"/>
					<odin:hidden property="ereaname" value="<%=ereaname%>" />
					<odin:hidden property="ereaid" value="<%=ereaid%>" />
					<odin:hidden property="manager" value="<%=manager%>" />
					<odin:hidden property="groupid"/>
					<odin:hidden property="userid"/>
				</tr>
			</table>
		</td>
	</tr>
</table>
</div>
<%-- <div id="one"><img src="<%=request.getContextPath()%>/image/one.png"> </div>
<div id="two"><img src="<%=request.getContextPath()%>/image/two.png"> </div>
<div id="three"><img src="<%=request.getContextPath()%>/image/three.png"> </div>
<div id="four"><img src="<%=request.getContextPath()%>/image/four.png"> </div> --%>

<%-- <div id="five"><img src="<%=request.getContextPath()%>/image/five.png"> </div> --%>
<odin:toolBar property="btnToolBar">
	<%-- <odin:buttonForToolBar text="�½�����" tooltip="�½�����" id="createGroup"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="ɾ������" tooltip="ɾ������" handler="deleteGroup"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="�½��û�" tooltip="�½��û�" id="create"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="�����û������ţ�" tooltip="�����û������ţ�" id="reset"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="ɾ���û�" tooltip="ɾ���û�" id="deleteUser"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="�����û�����" tooltip="�����û�����" id="setUserDept"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="�û�����" tooltip="�����û�����" id="changeType" /> --%>
	<odin:separator isLast="true"></odin:separator>
	<%-- <odin:fill />
	<odin:buttonForToolBar text="�̳������ԱȨ��" tooltip="�̳������ԱȨ��" id="inherit"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="��������Ա����Χ����" tooltip="��������Ա����Χ����" id="personRange"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="����Ȩ��" tooltip="����Ȩ��" id="personFunc"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="��Ϣ��Ȩ��" tooltip="��Ϣ��Ȩ��" id="tableCol"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="��Ϣ��Ȩ��" tooltip="��Ϣ��Ȩ��" id="table" isLast="true"/> --%>
</odin:toolBar>
<odin:panel contentEl="groupTreeContent" property="groupTreePanel" topBarId="btnToolBar"></odin:panel>

<odin:window src="/blank.htm" id="CreateIGWin" width="500" height="150" title="��Ϣ����༭ҳ��" modal="true"></odin:window>	
	
<script type="text/javascript">

var ctxPath='<%=ctxPath%>';

function setUserDeptWin(userid){
	
	var p = {'userid': userid};
	
	$h.openPageModeWin('infotodoWin','pages.cadremgn.sysmanager.authority.SetUserDept','�����û�����',310,110, p , ctxPath);

}

function setUserDept(){
	radow.doEvent("setUserDept");
	/* Ext.MessageBox.prompt("ϵͳ��ʾ","��ѯ���ƣ�",function(btn,value){
		if(btn=='ok'){
			if(value.trim()==''){
				Ext.MessageBox.alert("ϵͳ��ʾ","���������ƣ�");
			}
			radow.doEvent("saveCon.onclick",value);
		}
	},this,false,""); */
}

function changeUserType(userid){
	$h.openWin('usertype','pages.cadremgn.sysmanager.authority.AlterUserTypeWindow','������޸��û�����',300,200,userid,'<%=request.getContextPath()%>');
}
function inherit2(){
	radow.doEvent("inherit.onclick");
}
//�����༭Ȩ��
function personRange2(){
	radow.doEvent("personRange.onclick"); 
}
//��¡����  /*2019-10-24  LINJUN*/
function personRange3(){
	alert("��¡...");
	radow.doEvent("personRange3.onclick"); 
}
//�������Ȩ��
function personRangeLL(){
	radow.doEvent("personRangeLL.onclick");
}

function personFunc2(){
	radow.doEvent("personFunc.onclick");
}
function personFuncBook2(){
	radow.doEvent("personFuncBook.onclick");
}
function personFuncQuery2(){
	radow.doEvent("personFuncQuery.onclick");
}
function personFuncWin2(){
	radow.doEvent("personFuncWin.onclick");
}

function tableCol2(){
	radow.doEvent("tableCol.onclick");
}

function table2(){
	radow.doEvent("table.onclick");
}
function userSort(){
	$h.openPageModeWin('UserManagerSort','pages.cadremgn.sysmanager.authority.UserManagerSort','�û���������������',480,590, document.getElementById('userid').value,'<%=request.getContextPath()%>',null,{maximizable:false,resizable:false});
}
//�˵�Ȩ��
function personFunction(){
	<%-- $h.openPageModeWin('personFunc','pages.cadremgn.sysmanager.authority.PersonFunc','�˵�����Ȩ��',700,700, document.getElementById('userid').value,'<%=request.getContextPath()%>',null,{maximizable:false,resizable:false}); --%>
	$h.openWinNomal('personFunc','pages.cadremgn.sysmanager.authority.PersonFunc','�˵�����Ȩ��',1200,600, document.getElementById('userid').value,'<%=request.getContextPath()%>',null,{maximizable:false,resizable:false});
}
//���Ȩ��
function personFuncBook(){
	$h.openPageModeWin('personFuncBook','pages.cadremgn.sysmanager.authority.PersonFuncBook','�������Ȩ��',880,590, document.getElementById('userid').value,'<%=request.getContextPath()%>',null,{maximizable:false,resizable:false});
}
//��ѯ����ͼȨ�ޡ�ͳ��Ȩ��
function personFuncQuery(){
	$h.openPageModeWin('personFuncQuery','pages.cadremgn.sysmanager.authority.PersonFuncQuery','��ѯͳ��Ȩ��',880,590, document.getElementById('userid').value,'<%=request.getContextPath()%>',null,{maximizable:false,resizable:false});
}
//�Զ����
function personFuncWin(){
	$h.openPageModeWin('personFuncWin','pages.cadremgn.sysmanager.authority.PersonFuncWin','���м�ͥ��Ա�鿴Ȩ��',880,590, document.getElementById('userid').value,'<%=request.getContextPath()%>',null,{maximizable:false,resizable:false});
}

<%-- function personRange(user){
	 alert("usersManager-personRange(user)="+user);
	$h.openPageModeWin('newRange','pages.cadremgn.sysmanager.authority.NewRange','�������༭����Χ����',430,535, user,'<%=request.getContextPath()%>',null,{maximizable:false,resizable:false},true);
}

function personRange3(user){
	 alert("usersManager-personRange3(user)="+user);
	$h.openPageModeWin('newRange','pages.cadremgn.sysmanager.authority.NewRange3','����(��¡)����',430,535, user,'<%=request.getContextPath()%>',null,{maximizable:false,resizable:false},true);
} --%>

function personRangeLL2(user){
	$h.openPageModeWin('newRangeLook','pages.cadremgn.sysmanager.authority.NewRangeLook','�������������Χ����',430,535, user,'<%=request.getContextPath()%>',null,{maximizable:false,resizable:false},true);
}

//��������
function createGroup(){
	$h.openWin('userGroup','pages.cadremgn.sysmanager.authority.UserGroup','�������ż�����Ա',300,160, document.getElementById('userid').value,'<%=request.getContextPath()%>',null,{maximizable:false,resizable:false});
}
//ɾ������
function deleteGroup(){
	var userid = document.getElementById('userid').value;
	if(userid){
		radow.doEvent('deleteGroup',userid);
	}else{
		Ext.Msg.alert('ϵͳ��ʾ',"����ѡ��Ҫɾ���Ĳ���");
	}
}
function deleteSuccess(){
	Ext.Msg.alert('ϵͳ��ʾ',"ɾ���ɹ�",function(){
		document.getElementById('userid').value = '';
		reloadTree();
	});
}
//�����û�
function createUser(){
	$h.openWin('createUser','pages.cadremgn.sysmanager.authority.CreateUser','������ͨ�û�',550,240, document.getElementById('userid').value,'<%=request.getContextPath()%>',null,{maximizable:false,resizable:false});
}

function deleteUserSuccess(){
	Ext.Msg.alert('ϵͳ��ʾ',"ɾ���û��ɹ�",function(){
		document.getElementById('userid').value = '';
		reloadTree();
	});
}

function reloadTree() {
    var tree = Ext.getCmp("group");
    tree.root.reload();
    tree.root.expand(false,true, callback);//Ĭ��չ��
}

//�����û������ţ�
function resetUser(){
	$h.openWin('resetUser','pages.cadremgn.sysmanager.authority.ResetUser','�����û������ţ�',550,240, document.getElementById('userid').value,'<%=request.getContextPath()%>',null,{maximizable:false,resizable:false});
}

//���Ĳ���
function resetGroup(){
	$h.openPageModeWin('resetGroup','pages.cadremgn.sysmanager.authority.SubSystem','���Ĳ���',300,150, document.getElementById('userid').value,'<%=request.getContextPath()%>',null,{maximizable:false,resizable:false});
}

function tableCol(user){
	$h.openWin('tableCol','pages.cadremgn.sysmanager.authority.TableCol','��Ϣ��Ȩ������',700,590,user,'<%=request.getContextPath()%>',null,{maximizable:false,resizable:false});
}

function table(user){
	$h.openWin('table','pages.cadremgn.sysmanager.authority.TableCode','��Ϣ��Ȩ������',780,590,user,'<%=request.getContextPath()%>',null,{maximizable:false,resizable:false});
}

Ext.onReady(function(){
	var viewSize = Ext.getBody().getViewSize();
	var t = document.getElementById("groupTreePanel_panel");
	t.style.width= viewSize.width;
});
</script>


