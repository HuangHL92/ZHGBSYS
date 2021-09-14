<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@page import="com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_003.CompetenceManagePageModel"%>
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
                  dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysmanager.ZWHZYQ_001_003.CompetenceManage&eventNames=orgTreeJsonData'
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
                  dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysmanager.ZWHZYQ_001_003.CompetenceManage&eventNames=orgTreeJsonData'
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
	String ereaname = (String)(new CompetenceManagePageModel().areaInfo.get("areaname"));
	String ereaid = (String)(new CompetenceManagePageModel().areaInfo.get("areaid"));
	String manager = (String)(new CompetenceManagePageModel().areaInfo.get("manager"));
%>
<odin:toolBar property="treeDivBar">
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="信息项组设置" id="setInfoGroupBtn" tooltip="用于设置信息项组"/>
</odin:toolBar>
<div id="groupTreeContent" style="height: 100%">
<table width="100%">
	<tr>
		<td width="175">
		<table width="175">
			<tr>
				<td>
				<div id="tree-div"
					style="overflow:auto; height: 460px; width: 175px; border: 2px solid #c3daf9;">
					<div id="1"></div>
				<odin:panel contentEl="1" property="groupPanel" topBarId="treeDivBar"></odin:panel>
					</div>
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
					<td height="5" colspan="4"></td>
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
					<td height="5" colspan="4"></td>
				</tr>
			</table>
			</odin:groupBox>
		<odin:editgrid property="memberGrid" title="用户列表" autoFill="false" width="590" height="320" bbarId="pageToolBar" pageSize="20" isFirstLoadData="false" url="/">
			<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="logchecked"/>
					<odin:gridDataCol name="id" />
					<odin:gridDataCol name="loginname"/>
					<odin:gridDataCol name="name"/>
					<odin:gridDataCol name="status" />
					<odin:gridDataCol name="isleader" />
					<odin:gridDataCol name="otherinfo" />
					<odin:gridDataCol name="createdate" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridColumn header="" width="25" editor="checkbox" dataIndex="logchecked" edited="true"/>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridColumn dataIndex="loginname" width="110" header="用户登录名" align="center"/>
					<odin:gridColumn dataIndex="name" width="100" header="用户名" align="center"/>
					<odin:gridColumn dataIndex="status" width="100" header="用户状态" align="center"  renderer="statusVal"/>
					<odin:gridColumn dataIndex="isleader" header="是否为管理员" width="80" renderer="radow.commUserfulForGrid"  align="center"/>
					<odin:gridColumn dataIndex="otherinfo" width="130" header="创建者" align="center" />
					<odin:gridColumn dataIndex="createdate" width="130" header="创建时间" align="center" isLast="true"/>
					<%-- <odin:gridColumn dataIndex="loginname" width="110" header="用户登录名" align="center"/>
					<odin:gridColumn dataIndex="name" width="100" header="姓名" align="center"/>
					<odin:gridColumn dataIndex="desc" width="130" header="描述" align="center"/>
					<odin:gridColumn dataIndex="status" width="100" header="状态" align="center"  renderer="statusVal"/>
					<odin:gridColumn dataIndex="isleader" header="管理员" width="80" renderer="radow.commUserfulForGrid"  align="center"/>
					<odin:gridColumn dataIndex="loginname" header="修改" width="50" renderer="radow.commGrantForGrid" align="center"/>
					<odin:gridColumn dataIndex="id" header="重置密码" width="100" renderer="reset" align="center"/>
					<odin:gridColumn dataIndex="id" header="删除" width="50" renderer="radow.commGridColDelete" align="center"/>
					<odin:gridColumn dataIndex="id" header="数据授权" width="100" renderer="dataGrantRender" align="center" isLast="true"/> --%>
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
	<odin:buttonForToolBar text="人员查看控制" id="perFindComBtn" tooltip="用于控制用户对于某机构人员的不可见"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="机构权限分配" id="mechanismComBtn" tooltip="用于分配用户的机构权限"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="模块权限分配" id="ModuleComBtn" tooltip="用于分配用户的模块权限"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="信息项权限分配" id="InfoComBtn" tooltip="用于分配用户的信息项权限"/>
	<odin:separator></odin:separator> 
	<odin:buttonForToolBar text="查询" id="findUserBtn" tooltip="查询用户" isLast="true"/>
</odin:toolBar>
<odin:panel contentEl="groupTreeContent" property="groupTreePanel" topBarId="btnToolBar"></odin:panel>

<odin:window src="/blank.htm" id="ModuleComWin" width="500" height="300" title="模块权限分配页面" modal="true"></odin:window>	
<odin:window src="/blank.htm" id="mechanismComWin" width="600" height="500" title="机构权限分配页面" modal="true"></odin:window>	
<odin:window src="/blank.htm" id="personComWin" width="900" height="500" title="人员查看权限分配页面" modal="true"></odin:window>	
<odin:window src="/blank.htm" id="setInfoGroupWin" width="900" height="500" title="信息项组设置窗口" modal="true"></odin:window>
<odin:window src="/blank.htm" id="InfoComWin" width="600" height="500" title="信息项组权限分配页面" modal="true"></odin:window>


