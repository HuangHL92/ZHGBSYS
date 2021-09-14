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
          el : 'tree-div',//目标div容器
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
  				Ext.MessageBox.alert('提示','权限克隆成功');
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
          id : '-1'/* ,//默认的node值：?node=-100
          href:"javascript:radow.doEvent('querybyid','"+document.getElementById('ereaid').value+"')" */
    });
    tree.setRootNode(root);
    tree.render();
    root.expand(false,true, callback);//默认展开
    //tree.expandAll();
});

var callback = function (node){//仅展看下级
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
	<odin:textForToolBar text="克隆用户权限列表"></odin:textForToolBar>
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
	<%-- <odin:buttonForToolBar text="新建部门" tooltip="新建部门" id="createGroup"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="删除部门" tooltip="删除部门" handler="deleteGroup"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="新建用户" tooltip="新建用户" id="create"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="更改用户（部门）" tooltip="更改用户（部门）" id="reset"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="删除用户" tooltip="删除用户" id="deleteUser"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="设置用户部门" tooltip="设置用户部门" id="setUserDept"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="用户类型" tooltip="定义用户类型" id="changeType" /> --%>
	<odin:separator isLast="true"></odin:separator>
	<%-- <odin:fill />
	<odin:buttonForToolBar text="继承组管理员权限" tooltip="继承组管理员权限" id="inherit"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="机构（人员）范围管理" tooltip="机构（人员）范围管理" id="personRange"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="功能权限" tooltip="功能权限" id="personFunc"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="信息项权限" tooltip="信息项权限" id="tableCol"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="信息集权限" tooltip="信息集权限" id="table" isLast="true"/> --%>
</odin:toolBar>
<odin:panel contentEl="groupTreeContent" property="groupTreePanel" topBarId="btnToolBar"></odin:panel>

<odin:window src="/blank.htm" id="CreateIGWin" width="500" height="150" title="信息项组编辑页面" modal="true"></odin:window>	
	
<script type="text/javascript">

var ctxPath='<%=ctxPath%>';

function setUserDeptWin(userid){
	
	var p = {'userid': userid};
	
	$h.openPageModeWin('infotodoWin','pages.cadremgn.sysmanager.authority.SetUserDept','设置用户部门',310,110, p , ctxPath);

}

function setUserDept(){
	radow.doEvent("setUserDept");
	/* Ext.MessageBox.prompt("系统提示","查询名称：",function(btn,value){
		if(btn=='ok'){
			if(value.trim()==''){
				Ext.MessageBox.alert("系统提示","请输入名称！");
			}
			radow.doEvent("saveCon.onclick",value);
		}
	},this,false,""); */
}

function changeUserType(userid){
	$h.openWin('usertype','pages.cadremgn.sysmanager.authority.AlterUserTypeWindow','定义或修改用户类型',300,200,userid,'<%=request.getContextPath()%>');
}
function inherit2(){
	radow.doEvent("inherit.onclick");
}
//机构编辑权限
function personRange2(){
	radow.doEvent("personRange.onclick"); 
}
//克隆功能  /*2019-10-24  LINJUN*/
function personRange3(){
	alert("克隆...");
	radow.doEvent("personRange3.onclick"); 
}
//机构浏览权限
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
	$h.openPageModeWin('UserManagerSort','pages.cadremgn.sysmanager.authority.UserManagerSort','用户及所属部门排序',480,590, document.getElementById('userid').value,'<%=request.getContextPath()%>',null,{maximizable:false,resizable:false});
}
//菜单权限
function personFunction(){
	<%-- $h.openPageModeWin('personFunc','pages.cadremgn.sysmanager.authority.PersonFunc','菜单功能权限',700,700, document.getElementById('userid').value,'<%=request.getContextPath()%>',null,{maximizable:false,resizable:false}); --%>
	$h.openWinNomal('personFunc','pages.cadremgn.sysmanager.authority.PersonFunc','菜单功能权限',1200,600, document.getElementById('userid').value,'<%=request.getContextPath()%>',null,{maximizable:false,resizable:false});
}
//表册权限
function personFuncBook(){
	$h.openPageModeWin('personFuncBook','pages.cadremgn.sysmanager.authority.PersonFuncBook','表格名册权限',880,590, document.getElementById('userid').value,'<%=request.getContextPath()%>',null,{maximizable:false,resizable:false});
}
//查询、视图权限、统计权限
function personFuncQuery(){
	$h.openPageModeWin('personFuncQuery','pages.cadremgn.sysmanager.authority.PersonFuncQuery','查询统计权限',880,590, document.getElementById('userid').value,'<%=request.getContextPath()%>',null,{maximizable:false,resizable:false});
}
//自定义表单
function personFuncWin(){
	$h.openPageModeWin('personFuncWin','pages.cadremgn.sysmanager.authority.PersonFuncWin','所有家庭成员查看权限',880,590, document.getElementById('userid').value,'<%=request.getContextPath()%>',null,{maximizable:false,resizable:false});
}

<%-- function personRange(user){
	 alert("usersManager-personRange(user)="+user);
	$h.openPageModeWin('newRange','pages.cadremgn.sysmanager.authority.NewRange','机构（编辑）范围管理',430,535, user,'<%=request.getContextPath()%>',null,{maximizable:false,resizable:false},true);
}

function personRange3(user){
	 alert("usersManager-personRange3(user)="+user);
	$h.openPageModeWin('newRange','pages.cadremgn.sysmanager.authority.NewRange3','机构(克隆)管理',430,535, user,'<%=request.getContextPath()%>',null,{maximizable:false,resizable:false},true);
} --%>

function personRangeLL2(user){
	$h.openPageModeWin('newRangeLook','pages.cadremgn.sysmanager.authority.NewRangeLook','机构（浏览）范围管理',430,535, user,'<%=request.getContextPath()%>',null,{maximizable:false,resizable:false},true);
}

//新增部门
function createGroup(){
	$h.openWin('userGroup','pages.cadremgn.sysmanager.authority.UserGroup','新增部门及管理员',300,160, document.getElementById('userid').value,'<%=request.getContextPath()%>',null,{maximizable:false,resizable:false});
}
//删除部门
function deleteGroup(){
	var userid = document.getElementById('userid').value;
	if(userid){
		radow.doEvent('deleteGroup',userid);
	}else{
		Ext.Msg.alert('系统提示',"请先选择要删除的部门");
	}
}
function deleteSuccess(){
	Ext.Msg.alert('系统提示',"删除成功",function(){
		document.getElementById('userid').value = '';
		reloadTree();
	});
}
//新增用户
function createUser(){
	$h.openWin('createUser','pages.cadremgn.sysmanager.authority.CreateUser','新增普通用户',550,240, document.getElementById('userid').value,'<%=request.getContextPath()%>',null,{maximizable:false,resizable:false});
}

function deleteUserSuccess(){
	Ext.Msg.alert('系统提示',"删除用户成功",function(){
		document.getElementById('userid').value = '';
		reloadTree();
	});
}

function reloadTree() {
    var tree = Ext.getCmp("group");
    tree.root.reload();
    tree.root.expand(false,true, callback);//默认展开
}

//更改用户（部门）
function resetUser(){
	$h.openWin('resetUser','pages.cadremgn.sysmanager.authority.ResetUser','更改用户（部门）',550,240, document.getElementById('userid').value,'<%=request.getContextPath()%>',null,{maximizable:false,resizable:false});
}

//更改部门
function resetGroup(){
	$h.openPageModeWin('resetGroup','pages.cadremgn.sysmanager.authority.SubSystem','更改部门',300,150, document.getElementById('userid').value,'<%=request.getContextPath()%>',null,{maximizable:false,resizable:false});
}

function tableCol(user){
	$h.openWin('tableCol','pages.cadremgn.sysmanager.authority.TableCol','信息项权限设置',700,590,user,'<%=request.getContextPath()%>',null,{maximizable:false,resizable:false});
}

function table(user){
	$h.openWin('table','pages.cadremgn.sysmanager.authority.TableCode','信息集权限设置',780,590,user,'<%=request.getContextPath()%>',null,{maximizable:false,resizable:false});
}

Ext.onReady(function(){
	var viewSize = Ext.getBody().getViewSize();
	var t = document.getElementById("groupTreePanel_panel");
	t.style.width= viewSize.width;
});
</script>


