<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@page import="com.insigma.siis.local.pagemodel.sysmanager.ss.groupmanage.GroupManagePageModel"%>

<script type="text/javascript">
Ext.onReady(function() {
	  var man = document.all.manager.value;
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
                  dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysmanager.ss.groupmanage.GroupManage&eventNames=orgTreeJsonData'
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
            enableDD : true,
            containerScroll : true,
            loader : new Tree.TreeLoader( {
                  dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysmanager.ss.groupmanage.GroupManage&eventNames=orgTreeJsonData'
            })
        });
      }
      var root = new Tree.AsyncTreeNode( {
            text : document.all.ereaname.value,
            draggable : false,
            id : document.getElementById('ereaid').value,//默认的node值：?node=-100
            href:"javascript:radow.doEvent('querybyid','"+document.getElementById('ereaid').value+"')"
      });
      tree.setRootNode(root);
      tree.render();
      root.expand();
}); 
</script>

<% 
	String ereaname = (String)(new GroupManagePageModel().areaInfo.get("areaname"));
	String ereaid = (String)(new GroupManagePageModel().areaInfo.get("areaid"));
    String manager = (String)(new GroupManagePageModel().areaInfo.get("manager"));
%>

<div id="groupTreeContent">
<table>
	<tr>
		<td>
		<table width="40%">
			<tr>
				<td>
				<div id="tree-div"
					style="overflow: auto; height: 450px; width: 175px; border: 2px solid #c3daf9;"></div>
				</td>
			</tr>
		</table>
		</td>
		<td>
			<odin:groupBox title="搜索框" property="ggBox">
				<table>
					<tr>
					<td height="2"></td><td width="140"></td><td width="120"></td><td width="100"></td>
					</tr>
					<tr>
						<odin:hidden property="ereaname" value="<%= ereaname%>" />
						<odin:hidden property="ereaid" value="<%=ereaid%>" />
						<odin:hidden property="manager" value="<%= manager%>" />
					</tr>
					<tr>
						<odin:textIconEdit property="searchBtn" label="用户组名称查询"/>
						<td width="120"></td>
						<td><odin:button property="searchAllBtn" text="查询所有组"></odin:button></td>
					</tr>
					<tr>
						<td height="5" colspan="4"></td>
					</tr>
				</table>
			</odin:groupBox>
			<odin:simplePanel title="用户组信息" width="600px" bodyWidth="400px">
				<table  height="300px" width="550px" >
					<tr>
						<odin:hidden property="id"/>
					</tr>
					<tr>
						<odin:textEdit property="name" label="用户组名称"  readonly="true"></odin:textEdit>
						<odin:textEdit property="shortname" label="简称" readonly="true"></odin:textEdit>
					</tr>
					<tr>
						<odin:textEdit property="ownerId" label="持有者" readonly="true"></odin:textEdit>
						<odin:textEdit property="parentid" label="父类组" readonly="true"></odin:textEdit>
					</tr>
					<tr>
						<odin:textEdit property="status" label="状态" readonly="true"></odin:textEdit>
						<odin:textEdit property="desc" label="用户组描述" readonly="true"></odin:textEdit>
					</tr>
					<tr>
						<odin:textEdit property="org" label="系统机构编码" readonly="true"></odin:textEdit>
						<odin:textEdit property="principal" label="机构负责人" readonly="true"></odin:textEdit>
					</tr>
					<tr>
						<odin:textEdit property="linkman" label="联系人" readonly="true"></odin:textEdit>
						<odin:textEdit property="address" label="地址" readonly="true"></odin:textEdit>
					</tr>
					<tr>
						<odin:textEdit property="tel" label="电话" readonly="true"></odin:textEdit>
						<odin:textEdit property="districtcode" label="地区代码" readonly="true"></odin:textEdit>
					</tr>
					<tr>
						<odin:textEdit property="chargedept" label="主管部门" readonly="true"></odin:textEdit>
						<odin:textEdit property="createdate" label="创建时间" readonly="true"></odin:textEdit>
					</tr>
					<tr>
						<odin:textEdit property="otherinfo" label="其他信息" readonly="true"></odin:textEdit>
						<odin:select property="rate" label="级别" codeType="RATE"></odin:select>
					</tr>
					<tr>
						<td height="70" colspan="4"></td>
					</tr>
				</table>
			</odin:simplePanel> 
		</td>
	</tr>
</table>
</div>

<odin:toolBar property="btnToolBar">
	<odin:textForToolBar text="<h3>用户组管理</h3>" />
	<odin:fill />
	<odin:buttonForToolBar text="创建用户组" id="saveBtn" tooltip="在当前用户组下创建其子用户组"></odin:buttonForToolBar>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar id="editBtn"  text="修改用户组"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar id="revokeBtn" text="注销用户组"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar id="reuseBtn" text="启用用户组"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar id="deleteBtn" text="删除用户组" isLast="true" icon="images/qinkong.gif" cls="x-btn-text-icon" />
</odin:toolBar>
<odin:panel contentEl="groupTreeContent" property="groupTreePanel" topBarId="btnToolBar"></odin:panel>
	
	
	
<odin:window src="/blank.htm" id="addWin" width="500" height="300" title="用户组增加窗口"></odin:window>
<odin:window src="/blank.htm" id="uptWin" width="515" height="320" title="用户组修改窗口"></odin:window>
<odin:window src="/blank.htm" id="userWin" width="520" height="518" title="用户管理窗口"></odin:window>
<odin:window src="/blank.htm" id="groupWin" width="670" height="407" title="用户组窗口"></odin:window>