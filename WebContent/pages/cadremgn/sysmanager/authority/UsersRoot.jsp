<%@page import="com.insigma.odin.framework.persistence.HBUtil"%>
<%@page import="com.insigma.odin.framework.persistence.HBSession"%>
<%@page import="com.insigma.siis.local.business.helperUtil.SysManagerUtils"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@page import="com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_003.SetInfoGroupWindowPageModel"%>
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
	left: 543px;
	top: 280px;
	width: 20px;
} 

.djmain-title{
	background-color: rgb(34,131,235);
	color: white!important;
	height: 78px;
}
.dj-login-info p,.dj-login-info a{
	color: white!important;
}
</style>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">


<script type="text/javascript" src="basejs/ColumnNodeUI.js"></script>

<!-- Common Styles for the examples -->
<link rel="stylesheet" type="text/css" href="basejs/ext/ux/css/column-tree.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/qdstyle.css" />
<script src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/basejs/jquery-ui/jquery-1.10.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<link  href="<%=request.getContextPath()%>/css/newview/djzonghe.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript">

var ids="";
Ext.onReady(function() {
	var ht=document.body.clientHeight-110;
	document.getElementById('tree-div').style.height=ht+5;
	document.getElementById('tree').style.height=ht;
    var Tree = Ext.tree;
    
	var tree = new Tree.TreePanel( {
	      id:'group',
          el : 'tree-div',//目标div容器
          split:false,
          //height:ht,
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
                dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.cadremgn.sysmanager.authority.UsersRoot&eventNames=orgTreeJsonData',
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
          text :  "",
          draggable : false,
          id : '-1'//默认的node值：?node=-100
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

<odin:toolBar property="treeDivBar">
	<odin:textForToolBar text="用户及所属部门列表"></odin:textForToolBar>
	<odin:fill />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="排序" tooltip="排序" id="userSort" isLast="true"/>
</odin:toolBar>
<!--头部-->
<div class="djmain-top">
	<h1 class="djmain-title"><span class="djmain-log"></span>干部综合管理系统</h1>
	<div class="djmain-login" >
		<span id="displayPicture" class="dj-login-img">
		<!-- <img src="${ctxStatic}/index/images/tmp-photo.png" /> -->
		<img src="images/newview/user1.jpg" class="user-image" style="cursor: pointer;">
		<!--  <div id="userDropDown" style="z-index: 99999;position: fixed;">
			<ul>
				<li><a href="#" onclick="openChangePasswordWin3();return false;"><span></span> 修改密码</a></li>
				<li><a href="#" onclick="logout();return false;"><span></span> 退出系统</a></li>
			</ul>
		</div> -->
		</span>
		<div class="dj-login-info">
			<p class="dj-login-name"><%=SysManagerUtils.getUserName()%></p>
			<p><a href="#" onclick="openChangePasswordWin3(true);return false;"><span></span> 修改密码</a></p>
			<p><a href="#" onclick="openEXPOrclWin();return false;"><span></span> 数据库备份</a></p>
			<p style="margin-top: 3px;"><a href="#" onclick="logout();return false;"><span></span> 退出系统</a></p>
		</div>
	</div>
</div>
<div id="groupTreeContent" style="height:100%;width: 100%">
<table style="width: 98%" >
	<tr>
		<td width="275" valign="top">
			<div id="tree-div" style="overflow:auto; width: 275px; border: 2px solid #c3daf9;">
					<div id="1"></div>
				<odin:panel contentEl="1" property="groupPanel" topBarId="treeDivBar"></odin:panel>
					</div>
				
		</td>
		<td>
		<div id="tree" style="width:100%;overflow:auto;">
		<div id="usertable" style="width: 98%;height: 120px;border: 2px solid #c3daf9;">
			<table  id="myform">
				<tr>
					<td style="height: 10px;"></td>
				</tr>
				<tr>
					<odin:textEdit property="loginname" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;用户登录名" readonly="true"></odin:textEdit>
					<odin:textEdit property="username" label="姓名" readonly="true"></odin:textEdit>
					<odin:textEdit property="usertype" label="&nbsp;用户类型" readonly="true"></odin:textEdit>
					<odin:select property="validity" codeType="VALIDITY" label="密码有效期" readonly="true"></odin:select>	
				</tr>
				<tr>
					<td style="height: 10px;"></td>
				</tr>
				<tr>
					<odin:textEdit property="work" label="&nbsp;工作单位" readonly="true"></odin:textEdit>
					<odin:textEdit property="mobile" label="&nbsp;手机" readonly="true"></odin:textEdit>
					<odin:textEdit property="tel" label="&nbsp;办公电话" readonly="true"></odin:textEdit>
					<odin:textEdit property="email" label="&nbsp;邮件" readonly="true"></odin:textEdit>
				</tr>
				
				<tr>
					<odin:textEdit property="status" label="&nbsp;状态" readonly="true"/>
				</tr>
			</table>
		</div>
		<div id="qxtable" style="width: 100%;">
			<table id="appTable" style="width: 100%;text-align: center;">
				<tr>
					<td height="30px"></td>
				</tr>
				
				<tr>
					<td height="20px"></td>
				</tr>
			
			</table>
		</div>
		</div>
			<table>
				<tr>
					<odin:hidden property="checkedgroupid"/>
					<odin:hidden property="forsearchgroupid"/>
					<odin:hidden property="groupid"/>
					<odin:hidden property="userid"/>
				</tr>
			</table>
		</td>
	</tr>
</table>
</div>


<odin:toolBar property="btnToolBar">
	<odin:buttonForToolBar text="新建部门" tooltip="新建部门" id="createGroup"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="删除部门" tooltip="删除部门" handler="deleteGroup"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="新建用户" tooltip="新建用户" id="create"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="更改用户（部门）" tooltip="更改用户（部门）" id="reset"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="删除用户" tooltip="删除用户" id="deleteUser" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="锁定用户" id="lock"  />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="解锁用户" id="unlock" isLast="true" />
	<%-- <odin:separator></odin:separator>
	 <odin:buttonForToolBar text="设置用户部门" tooltip="设置用户部门" id="setUserDept"/>
	<odin:separator></odin:separator> 
	<odin:buttonForToolBar text="用户类型" tooltip="定义用户类型" id="changeType"  isLast="true"/> --%>
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

/**
 * 修改密码
 */
function openChangePasswordWin3(closable){
	//radow.util.openWindow('pswin','pages.sysmanager.user.PsWindow');
	$h.openWin('pswin1','pages.sysmanager.user.PsWindow','修改密码',350,230, '',g_contextpath,window,{maximizable:false,resizable:false,closable:closable});
}
/**
 * 导出oracle
 */
function openEXPOrclWin(){
	$h.openWin('EXPOrc','pages.sysmanager.oraclebak.ExpDmp','导出数据',1050,530, '',g_contextpath,window,{maximizable:false,resizable:false,closable:true});
}

function changeUserType(userid){
	$h.openPageModeWin('usertype','pages.cadremgn.sysmanager.authority.AlterUserTypeWindow','定义或修改用户类型',300,150,userid,'<%=request.getContextPath()%>');
}
function inherit2(){
	radow.doEvent("inherit.onclick");
}

function personRange2(){
	radow.doEvent("personRange.onclick");
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

//新增部门
function createGroup(){
	$h.openWin('userGroup','pages.cadremgn.sysmanager.authority.UserGroup','新增部门及管理员',300,160, document.getElementById('userid').value,'<%=request.getContextPath()%>',window,{maximizable:false,resizable:false});
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
	$h.openWin('createUser','pages.cadremgn.sysmanager.authority.CreateUser','新增普通用户',550,290, document.getElementById('userid').value,'<%=request.getContextPath()%>',window,{maximizable:false,resizable:false});
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
	$h.openWin('resetUser','pages.cadremgn.sysmanager.authority.ResetUser','更改用户（部门）',550,240, document.getElementById('userid').value,'<%=request.getContextPath()%>',window,{maximizable:false,resizable:false});
}

//更改部门
function resetGroup(){
	$h.openWin('resetGroup','pages.cadremgn.sysmanager.authority.SubSystem','更改部门',300,150, document.getElementById('userid').value,'<%=request.getContextPath()%>',window,{maximizable:false,resizable:false});
}

Ext.onReady(function(){
	var viewSize = Ext.getBody().getViewSize();
	var t = document.getElementById("groupTreePanel_panel");
	t.style.width= viewSize.width;
	t.style.height= viewSize.height-100;
});





var g_contextpath = '<%=request.getContextPath()%>';
function logout(){
	if (confirm('确定要退出系统吗?')){
	  	window.top.location.href=g_contextpath+'/mainLogOff.jsp';//'/logoffAction.do';
	  } 
}


</script>


