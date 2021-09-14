<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@page import="com.insigma.siis.local.pagemodel.sysmanager.ss.group.GroupManagePageModel"%>

<script type="text/javascript">
Ext.onReady(function() {
	  var man = document.getElementById('manager').value;
      var Tree = Ext.tree;
      if(man == 'true'){
	       var tree = new Tree.TreePanel( {
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
                  dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysmanager.ss.group.GroupManage&eventNames=orgTreeJsonData'
            })
        });
      }else{
        var tree = new Tree.TreePanel( {
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
                  dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysmanager.ss.group.GroupManage&eventNames=orgTreeJsonData'
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
	var loginname=rs.get('loginname');
	var url="<a   href=\"javascript:radow.doEvent('dogridunlock','"+loginname+"');\">解锁</a>";
	if(value=='1'){
		return '正常';
	}else if(value=='0'){
		return '锁定'+url;
	}else{
		return '状态异常';
	}
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
	String ereaname = (String)(new GroupManagePageModel().areaInfo.get("areaname"));
	String ereaid = (String)(new GroupManagePageModel().areaInfo.get("areaid"));
	String manager = (String)(new GroupManagePageModel().areaInfo.get("manager"));
%>

<div id="groupTreeContent" style="height: 90%">
<table width="100%">
	<tr>
		<td width="175">
		<table width="175">
			<tr>
				<td>
				<div id="tree-div"
					style="overflow:auto; height: 460px; width: 175px; border: 2px solid #c3daf9;"></div>
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
					<odin:textIconEdit property="searchGroupBtn"  label="组织名"/>
					<odin:textIconEdit property="searchUserBtn" label="用户登录名"/>
				</tr>
				<tr>
					<td height="5" colspan="4"></td>
				</tr>
			</table>
			</odin:groupBox>
		
		<odin:gridSelectColJs name="status" codeType="USEFUL"></odin:gridSelectColJs>
		<odin:gridSelectColJs name="isleader" codeType="ISLEADER"></odin:gridSelectColJs>
		<odin:toolBar property="btnToolBar1">
									<odin:textForToolBar text="新增用户请点击右边按钮" />
									<odin:fill></odin:fill>
									<odin:buttonForToolBar text="增加用户" id="addUser" isLast="true"/>
								</odin:toolBar>
		<odin:editgrid property="memberGrid"  topBarId="btnToolBar1" autoFill="false"  width="590" height="320" bbarId="pageToolBar" pageSize="20" isFirstLoadData="false" url="/">
			<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="logchecked"/>
					<odin:gridDataCol name="id" />
					<odin:gridDataCol name="loginname"/>
					<odin:gridDataCol name="name"/>
					<odin:gridDataCol name="desc" />
					<odin:gridDataCol name="dept" />
					<odin:gridDataCol name="status" />
					<odin:gridDataCol name="isleader" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridColumn  header="" width="25" editor="checkbox" dataIndex="logchecked" edited="true"/>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridColumn dataIndex="loginname" width="110" header="用户登录名"  align="center" editor="text" edited="true" required="true"/>
					<odin:gridColumn dataIndex="name" width="100" header="姓名" align="center" editor="text" edited="true" required="true"/>
					<odin:gridColumn dataIndex="desc" width="130" header="描述" align="center" editor="text" edited="true"/>
					<odin:gridColumn dataIndex="dept" width="130" header="部门" align="center" editor="text" edited="true"/>
					<odin:gridColumn dataIndex="isleader" header="管理员" width="80"   align="center" renderer="radow.commUserfulForGrid" edited="true" />	
					<odin:gridColumn dataIndex="status" width="100" header="状态" align="center"   editor="select" codeType="USEFUL" edited="true" />		
					<odin:gridColumn dataIndex="id" header="重置密码" width="100" renderer="reset" align="center"/>
					<odin:gridColumn dataIndex="id" header="删除" width="50" renderer="radow.commGridColDelete" align="center"/>
					<odin:gridColumn dataIndex="id" header="数据授权" width="100" renderer="dataGrantRender" align="center" isLast="true"/>
					
					
				</odin:gridColumnModel>
			</odin:editgrid>
		</td>
	</tr>
</table>
</div>

<odin:toolBar property="btnToolBar">
	<odin:textForToolBar text="<h3>组织管理</h3>" />
	<odin:fill />

	<odin:buttonForToolBar text="增加成员" id="addMemberBtn" tooltip="增加已存在的还不是该组织成员的用户"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="移除成员" id="removeUserBtn"  tooltip="将选中成员从组织中移除,如果成员没有加入其他组织,则应该先把该成员加入到其他组织后才能移除"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="设为管理员" id="setLeaderBtn" tooltip="将选中的普通成员设置为该组管理员"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="取消管理员" id="cancelLeaderBtn" tooltip="将选中的管理员设置为普通成员"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="保存" id="save" isLast="true" tooltip="保存当前打钩的用户信息"/>
</odin:toolBar>
<odin:panel contentEl="groupTreeContent" property="groupTreePanel" topBarId="btnToolBar"></odin:panel>
	
<odin:window src="/blank.htm" id="createUserWin" width="470" height="400" title="创建成员用户窗口"></odin:window>
<odin:window src="/blank.htm" id="userShowWin" width="490" height="475" title="用户查询窗口"></odin:window>
<odin:window src="/blank.htm" id="userWin" width="450" height="450" title="组织成员增加窗口"></odin:window>
<odin:window src="/blank.htm" id="updateUserWin" width="285" height="240" title="成员信息修改窗口"></odin:window>
<odin:window src="/blank.htm" id="groupWin" width="490" height="415" title="组织信息窗口"></odin:window>
