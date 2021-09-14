<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@page import="com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_002.PwdManagePageModel"%>
<script  type="text/javascript" src="basejs/jquery.js"></script>
<script  type="text/javascript" src="basejs/jquery.min.js"></script>
<script type="text/javascript">
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
            rootVisible: true,
            autoScroll : true,
            animate : true,
            border:false,
            enableDD : true,
            containerScroll : true,
            loader : new Tree.TreeLoader( {
                  dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysmanager.ZWHZYQ_001_002.PwdManage&eventNames=orgTreeJsonData'
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
            rootVisible: false,
            autoScroll : true,
            animate : true,
            border:false,
            enableDD : true,
            containerScroll : true,
            loader : new Tree.TreeLoader( {
                  dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysmanager.ZWHZYQ_001_002.PwdManage&eventNames=orgTreeJsonData'
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
}); 
function statusVal(value, params, rs, rowIndex, colIndex, ds){
	if(value=='1'){
		return '有效';
	}else if(value=='0'){
		return '无效';
	}else{
		return '状态异常';
	}
}


function reloadTree() {
    var tree = Ext.getCmp("group");
    tree.root.reload();
    tree.expandAll();
}
/**
* 数据授权的render函数
*/
function dataGrantRender(value, params, rs, rowIndex, colIndex, ds){
	return "<a href=\"javascript:openDataGrantWin('"+value+"')\">数据授权</a>";
}
function openDataGrantWin(userid){
	document.getElementById("radow_parent_data").value = userid;
	doOpenPupWin("/radowAction.do?method=doEvent&pageModel=pages.sysmanager.datagroup.UserDataGrant&userid="+userid,
		"数据授权窗口",270,446,null);
}

function reset(value, params, rs, rowIndex, colIndex, ds){
	return "<img src='"+contextPath+"/images/icon_photodesk.gif'  onclick=\"radow.doEvent('reset','"+value+"');\">"

}
</script>

<% 
	String ereaname = (String)(new PwdManagePageModel().areaInfo.get("areaname"));
	String ereaid = (String)(new PwdManagePageModel().areaInfo.get("areaid"));
	String manager = (String)(new PwdManagePageModel().areaInfo.get("manager"));
%>
<body style="height:100%;margin:0;padding:0">
<div id="groupTreeContent" style="height: 100%">
<table width="100%" height="100%" >
	<tr height="100%" >
		<td width="175" height="100%" >
		<table width="175" height="100%" >
			<tr>
				<td>
				<div id="tree-div"
					style="overflow:auto; height:100%; width: 175px; border: 2px solid #c3daf9;">
				</td>
			</tr>
		</table>
		</td>
		<td>
			<table>
				<tr>
					<odin:hidden property="checkedgroupid"/>
					<odin:hidden property="forsearchgroupid"/>
					<odin:hidden property="ereaname" value="<%=ereaname%>" />
					<odin:hidden property="ereaid" value="<%=ereaid%>" />
					<odin:hidden property="manager" value="<%=manager%>" />
				</tr>
				<tr>
					<td width="10"></td>
					<odin:textEdit property="optionGroup" label="当前操作组织" width="110" disabled="true"/>
				</tr>
				<tr>
					<td height="20" colspan="4"></td>
				</tr>
			</table>
			<odin:groupBox title="搜索框" property="ggBox">
			<table width="100%">
				<tr>
					<odin:textEdit property="searchUserNameBtn"  label="用户名"/>
					<odin:textEdit property="searchUserBtn" label="用户登录名"/>
					<odin:select property="useful" label="用户状态"/>
					<odin:select property="isleader" label="是否为管理员"/>
				</tr>
				<tr>
					<td height="20" colspan="4"></td>
				</tr>
			</table>
			</odin:groupBox>
		<odin:editgrid property="memberGrid" title="用户列表" autoFill="false" width="590" height="380" bbarId="pageToolBar" pageSize="20" isFirstLoadData="false" url="/">
			<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="logchecked"/>
					<odin:gridDataCol name="id" />
					<odin:gridDataCol name="loginname"/>
					<odin:gridDataCol name="name"/>
					<odin:gridDataCol name="status" />
					<odin:gridDataCol name="isleader" />
					<odin:gridDataCol name="empid" />
					<odin:gridDataCol name="createdate" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridColumn header="" width="25" editor="checkbox" dataIndex="logchecked" edited="true"/>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridColumn dataIndex="loginname" width="110" header="用户登录名" align="center"/>
					<odin:gridColumn dataIndex="name" width="100" header="用户名" align="center"/>
					<odin:gridColumn dataIndex="status" width="100" header="用户状态" align="center"  renderer="statusVal"/>
					<odin:gridColumn dataIndex="isleader" header="是否为管理员" width="80" renderer="radow.commUserfulForGrid"  align="center"/>
					<odin:gridColumn dataIndex="empid" width="130" header="创建者" align="center" />
					<odin:gridColumn dataIndex="createdate" width="130" header="创建时间" align="center" isLast="true"/>
				</odin:gridColumnModel>
			</odin:editgrid>
		</td>
	</tr>
</table>
</div>

<odin:toolBar property="btnToolBar">
	<odin:textForToolBar text="<h3>组织用户管理</h3>" />
	<odin:fill />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="密码重置" icon="images/icon/reset.gif"  id="resetUserPsw" tooltip="重置用户密码"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="密码修改" icon="images/keyedit.gif"  id="modifyUserPsw" tooltip="修改用户密码"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="查询" icon="images/search.gif"  id="findUserBtn" tooltip="查询用户" isLast="true"/>
</odin:toolBar>

<odin:panel contentEl="groupTreeContent" property="groupTreePanel" topBarId="btnToolBar"></odin:panel>
</body>
<odin:window src="/blank.htm" id="modifyUserPwdWin" width="300" height="200" title="用户密码修改唇口" modal="true"></odin:window>	