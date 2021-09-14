<%@page import="com.insigma.siis.local.business.helperUtil.SysManagerUtils"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@page import="com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_003.SetInfoGroupWindowPageModel"%>
<link rel="stylesheet" type="text/css" href="../../resources/css/ext-all.css" />
<%
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
Ext.onReady(function() {
	//var man = document.getElementById('manager').value;
    var Tree = Ext.tree;
    
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
                dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.cadremgn.sysmanager.authority.UsersManager&eventNames=orgTreeJsonData',
                //baseParams : {userid: ''}
                baseAttrs: { uiProvider: Ext.tree.TreeCheckNodeUI }
          }),
          listeners : {
  			'click' : function(node) {
  				var userid  = node.id;
  				document.getElementById('userid').value = userid;
  				radow.doEvent("chooseUser",userid);
  				/* var loader = Ext.getCmp("treegrid").getLoader();
  				Ext.apply(loader.baseParams,{userid: node.id});
  				Ext.getCmp("treegrid").root.reload();
  				Ext.getCmp("treegrid").expandAll(); */
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
	<odin:textForToolBar text="�û������������б�"></odin:textForToolBar>
	<odin:fill />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="����" tooltip="����" id="userSort" isLast="true"/>
</odin:toolBar>
<div id="groupTreeContent" style="height:100%;width: 100%">
<table style="width: 98%">
	<tr>
		<td width="275" valign="top">
			<div id="tree-div" style="overflow:auto; height: 530px; width: 275px; border: 2px solid #c3daf9;">
					<div id="1"></div>
				<odin:panel contentEl="1" property="groupPanel" topBarId="treeDivBar"></odin:panel>
					</div>
				
		</td>
		<td>
		<div id="tree" style="align:left top;width:100%;overflow:auto;">
		<div id="usertable" style="width: 98%;height: 150px;border: 2px solid #c3daf9;">
			<table  id="myform">
				<tr>
					<td style="height: 10px;"></td>
				</tr>
				<tr>
					<odin:textEdit property="loginname" label="�û���¼��" readonly="true"></odin:textEdit>
					<odin:textEdit property="username" label="����" readonly="true"></odin:textEdit>
				</tr>
				<tr>
					<odin:textEdit property="usertype" label="&nbsp;�û�����" readonly="true"></odin:textEdit>
					<odin:select property="validity" codeType="VALIDITY" label="������Ч��" readonly="true"></odin:select>	
				</tr>
				<tr>
					<odin:textEdit property="work" label="&nbsp;������λ" readonly="true"></odin:textEdit>
					<odin:textEdit property="mobile" label="&nbsp;�ֻ�" readonly="true"></odin:textEdit>
				</tr>
				<tr>
					<odin:textEdit property="tel" label="&nbsp;�칫�绰" readonly="true"></odin:textEdit>
					<odin:textEdit property="email" label="&nbsp;�ʼ�" readonly="true"></odin:textEdit>
				</tr>
				<tr>
					<odin:textEdit property="status" label="&nbsp;״̬" readonly="true"/>
				</tr>
				<tr>
			</table>
		</div>
		<div id="qxtable" style="width: 100%;height: 340px;">
			<table>
				<tr>
					<!-- <td><input class="bluebutton bigbutton-customquery qxbutton"  type="button" value="�̳������ԱȨ��" onclick="inherit2()"></td> -->
					<td>&nbsp;&nbsp;<input class="bluebutton bigbutton-customquery qxbutton" type="button" value="�������༭����Χ����" onclick="personRange2()"></td>
					<!-- <td><input class="bluebutton bigbutton-customquery qxbutton" type="button" value="�������������Χ����" onclick="personRangeLL()"></td> -->
					<td>&nbsp;&nbsp;<input class="bluebutton bigbutton-customquery qxbutton" type="button" value="�˵�����Ȩ��" onclick="personFunc2()"></td>
				</tr>
				<tr>	
					<td><input class="bluebutton bigbutton-customquery qxbutton" type="button" value="�û�����" onclick="checkUserType()"></td>
				</tr>
				<!-- <tr>	
					<td><input class="bluebutton bigbutton-customquery qxbutton" type="button" value="�������Ȩ��" onclick="personFuncBook2()"></td>
				</tr>
				<tr>
					<td><input class="bluebutton bigbutton-customquery qxbutton" type="button" value="¼����漰Ӧ��Ȩ��" onclick="personFuncWin2()"></td>
					<td><input class="bluebutton bigbutton-customquery qxbutton" type="button" value="��ѯͳ��Ȩ��" onclick="personFuncQuery2()"></td>
				</tr> -->
				<!-- <tr>	
				<td>&nbsp;&nbsp;<input class="bluebutton bigbutton-customquery qxbutton" type="button" value="�˵�����Ȩ��" onclick="personFunc2()"></td>
				<td>&nbsp;&nbsp;<input class="bluebutton bigbutton-customquery qxbutton" type="button" value="��Ϣ��Ȩ��" onclick="table2()"></td>
				<td><input class="bluebutton bigbutton-customquery qxbutton" type="button" value="��Ϣ��Ȩ��" onclick="tableCol2()"></td>
					<td><input class="bluebutton bigbutton-customquery qxbutton" type="button" value="���м�ͥ��Ա�鿴Ȩ��" onclick="personFuncWin2()"></td>
				</tr>
				<tr>
				<td>&nbsp;&nbsp;<input class="bluebutton bigbutton-customquery qxbutton" type="button" value="��¡" onclick="personRange3()"></td>
				</tr>
				<tr>
					<td>&nbsp;&nbsp;<input class="bluebutton bigbutton-customquery qxbutton" type="button" value="���м�ͥ��Ա�鿴Ȩ��" onclick="personFuncWin2()"></td>
					<td></td>
				</tr> -->
			</table>
			
		</div>
		</div>
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
<%-- <odin:toolBar property="btnToolBar">
	<odin:buttonForToolBar text="�½�����" tooltip="�½�����" id="createGroup"/>
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
	<odin:buttonForToolBar text="�û�����" tooltip="�����û�����" id="changeType" /> 
	<odin:separator isLast="true"></odin:separator>
	<odin:fill />
	<odin:buttonForToolBar text="�̳������ԱȨ��" tooltip="�̳������ԱȨ��" id="inherit"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="��������Ա����Χ����" tooltip="��������Ա����Χ����" id="personRange"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="����Ȩ��" tooltip="����Ȩ��" id="personFunc"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="��Ϣ��Ȩ��" tooltip="��Ϣ��Ȩ��" id="tableCol"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="��Ϣ��Ȩ��" tooltip="��Ϣ��Ȩ��" id="table" isLast="true"/> 
</odin:toolBar> --%>
<odin:panel contentEl="groupTreeContent" property="groupTreePanel" ></odin:panel>

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
	$h.openWin('usertype','pages.cadremgn.sysmanager.authority.AlterUserTypeWindow','������޸��û�����',300,300,userid,'<%=request.getContextPath()%>');
}
function checkUserType(){
	radow.doEvent("changeType.onclick");
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

function personRange(user,tablename){
	//alert(tablename);
	var title;
	if(tablename=='competence_userdept')
		title='�������༭����Χ����';
	/* else if(tablename=='competence_userdept_look')
		title='�������������Χ����'; */
	$h.openPageModeWin('newRange','pages.cadremgn.sysmanager.authority.NewRange&tablename='+tablename,title,430,535, user,'<%=request.getContextPath()%>',null,{maximizable:false,resizable:false},true);
}

function personRangekl(user){
	$h.openPageModeWin('newRangekl','pages.cadremgn.sysmanager.authority.NewRangekl','����(��¡)����',430,535, user,'<%=request.getContextPath()%>',null,{maximizable:false,resizable:false},true);
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
	t.style.height= viewSize.height;
});
</script>


