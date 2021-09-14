<%@page import="com.insigma.odin.framework.sys.manager.sysmanager.comm.entity.Sysopright"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@page import="com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_001.GroupManagePageModel"%>
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
            width: 210,
            height: 500,
            minSize: 164,
            maxSize: 164,
            rootVisible: true,
            autoScroll : true,
            animate : true,
            border:false,
            enableDD : true,
            containerScroll : true,
            loader : new Tree.TreeLoader( {
                  dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysmanager.sysuser.UserManage&eventNames=orgTreeJsonData'
            })
        });
      }else{
        var tree = new Tree.TreePanel( {
        	id:'group',
            el : 'tree-div',//目标div容器  
            split:false,
            width: 210,
            height: 500,
            minSize: 164,
            maxSize: 164,
            rootVisible: false,
            autoScroll : true,
            animate : true,
            border:false,
            enableDD : true,
            containerScroll : true,
            loader : new Tree.TreeLoader( {
                  dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysmanager.sysuser.UserManage&eventNames=orgTreeJsonData'
            })
        });
      }
      var id = document.getElementById('ereaid').value;
      var root = new Tree.AsyncTreeNode( {
            text :  document.getElementById('ereaname').value,
            draggable : false,
            id : id,//默认的node值：?node=-100  
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
    tree.getRootNode().reload();
    tree.expandAll();
}
function reloadThisGroup() {
	document.getElementById('groupname').innerHTML=document.getElementById('optionGroup').value;
}

/**
* 机构授权的render函数
*/
function JGGrantRender(value, params, rs, rowIndex, colIndex, ds){

	return "<a href=\"javascript:openJGGrantWin('"+rs.get('userid')+"')\">机构授权</a>";
}
function openJGGrantWin(userid){

	document.getElementById("radow_parent_data").value = userid;
	var CurUserType = document.getElementById("CurUserType").value;
	var CurLoginname = document.getElementById("CurLoginname").value;
	doOpenPupWin("/radowAction.do?method=doEvent&pageModel=pages.sysmanager.ZWHZYQ_001_003.MechanismComWindow&userid="+userid,
		"机构授权窗口",600,500,null);
}
function MKGrantRender(value, params, rs, rowIndex, colIndex, ds){
	return "<a href=\"javascript:openMKGrantWin('"+rs.get('userid')+"')\">角色授权</a>";
}
function openMKGrantWin(userid){
	document.getElementById("radow_parent_data").value = userid;
	var CurUserType = document.getElementById("CurUserType").value;
	var CurLoginname = document.getElementById("CurLoginname").value;
	doOpenPupWin("/radowAction.do?method=doEvent&pageModel=pages.sysmanager.ZWHZYQ_001_003.ModuleComWindow&userid="+userid,
		"模块角色控制窗口",500,390,null);
}
function reset(value, params, rs, rowIndex, colIndex, ds){
	return "<img src='"+contextPath+"/images/icon_photodesk.gif'  onclick=\"radow.doEvent('reset','"+value+"');\">"

}

function setPageSize1(){
	var gridId = 'memberGrid';
	if (!Ext.getCmp(gridId)) {
		odin.error("要导出的grid不存在！gridId=" + gridId);
		return;
	}
	var grid = Ext.getCmp(gridId);
	var store = grid.getStore();
	if (store.getCount() == 0) {
		//odin.error("请先查询出数据后再进行本操作！");
		//return;
	}
	var pageingToolbar = (grid.getBottomToolbar() || grid.getTopToolbar());
	if (pageingToolbar && pageingToolbar.pageSize) {
		gridIdForSeting = gridId;
		var url = contextPath + "/sys/comm/commSetGrid.jsp";
		doOpenPupWin(url, "设置每页条数", 300, 150);
	} else {
		odin.error("非分页grid不能使用此功能！");
		return;
	}
}

//日期格式处理
function resetDate(value, params, rs, rowIndex, colIndex, ds){
	var newdate = value.substring(0,10);
	return newdate;
}

//导出全部数据
function expAllData(){
	odin.grid.menu.expExcelFromGrid('memberGrid', null, null,
								null, false);
}

</script>

<% 
	String ereaname = (String)(new GroupManagePageModel().areaInfo.get("areaname"));
	String ereaid = (String)(new GroupManagePageModel().areaInfo.get("areaid"));
	String manager = (String)(new GroupManagePageModel().areaInfo.get("manager"));
%>
<body style="height:100%;margin:0;padding:0">
	<odin:toolBar property="treeDivBar">
		<odin:separator></odin:separator>
		<odin:buttonForToolBar text="新建" icon="images/add.gif" id="createGroupBtn" tooltip="创建用户组"/>
		<odin:separator></odin:separator>
		<odin:buttonForToolBar text="修改" icon="images/keyedit.gif" id="modifyGroupBtn" tooltip="修改用户组"/>
		<odin:separator></odin:separator>
		<odin:buttonForToolBar text="注销" icon="images/back.gif" id="deleteGroupBtn" tooltip="注销用户组"/>
	</odin:toolBar>
	<div id="groupTreeContent" style="height:100%;margin:0 auto;padding:0 auto">
		<table width="100%" height="100%" border="1">
			<tr height="100%">
				<td width="175" height="100%">
					<table width="175" height="100%">
						<tr>
							<td height="100%">
								<div id="tree-div" style="overflow:auto; height:100%; width: 175px; border: 2px solid #c3daf9;">
									<div id="1"></div>
									<odin:panel contentEl="1" property="groupPanel" topBarId="treeDivBar"></odin:panel>
								</div>
							</td>
						</tr>
					</table>
				</td>
				<td>
					<table width="100%">
						<tr>
							<odin:hidden property="checkedgroupid"/>
							<odin:hidden property="forsearchgroupid"/>
							<odin:hidden property="ereaname" value="<%=ereaname%>" />
							<odin:hidden property="ereaid" value="<%=ereaid%>" />
							<odin:hidden property="manager" value="<%=manager%>" />
							<odin:hidden property="CurLoginname" />
							<odin:hidden property="CurUserType" />
						</tr>
						<tr>
							<td><span style="font-size:12px">当前操作组织:</span><span id="groupname" style="font-size:12px"></span></td>
							<%-- <odin:textEdit property="optionGroup" label="当前操作组织" width="110" disabled="true"/> --%>
							<odin:hidden property="optionGroup"/>
						</tr>
					</table>
					<odin:groupBox title="搜索框" property="ggBox">
						<table width="100%">
							<tr>
								<odin:textEdit property="searchUserNameBtn"  label="用户名"/>
								<odin:textEdit property="searchUserBtn" label="用户登录名"/>
								<odin:select2 property="useful" label="用户状态"/>
								<odin:select2 property="isleader" data="['1','是'],['0','否']" label="是否为管理员"/>
							</tr>
							<tr>
								<td height="20" colspan="4"></td>
							</tr>
						</table>
					</odin:groupBox>
					<odin:editgrid property="memberGrid" title="用户列表" autoFill="true" width="590" height="392" bbarId="pageToolBar" pageSize="20" isFirstLoadData="false" url="/">
						<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
							<odin:gridDataCol name="logchecked"/>
							<odin:gridDataCol name="userid" />
							<odin:gridDataCol name="loginname"/>
							<odin:gridDataCol name="username"/>
							<odin:gridDataCol name="useful" />
							<odin:gridDataCol name="isleader" />
							<odin:gridDataCol name="empid" />
							<odin:gridDataCol name="usertype" />
							<odin:gridDataCol name="work" />
							<odin:gridDataCol name="email" />
							<odin:gridDataCol name="tel" />
							<odin:gridDataCol name="mobile" />
							<odin:gridDataCol name="createdate" isLast="true"/>
						</odin:gridJsonDataModel>
						<odin:gridColumnModel>
							<odin:gridRowNumColumn></odin:gridRowNumColumn>
							<odin:gridColumn header="" width="25" editor="checkbox" dataIndex="logchecked" edited="true"/>
							<odin:gridColumn dataIndex="loginname" width="90" header="用户登录名" align="center"/>
							<odin:gridColumn dataIndex="username" width="90" header="用户名" align="center"/>
							<odin:gridColumn dataIndex="useful" width="60" header="用户状态" align="center"  renderer="statusVal"/>
							<odin:gridColumn dataIndex="isleader" header="管理员" width="50" renderer="radow.commUserfulForGrid"  align="center"/>
							<odin:gridColumn dataIndex="usertype" header="用户类型" width="70" renderer="getUserType" align="center"/>
							<odin:gridColumn dataIndex="empid" width="80" header="创建者" align="center" hidden="true" />
							<odin:gridColumn dataIndex="createdate" width="80" header="创建时间" renderer="resetDate" align="center" hidden="true"/>
							<odin:gridColumn dataIndex="op1" header="授权" width="60" renderer="JGGrantRender" align="center"/>
							<odin:gridColumn dataIndex="op3" header="授权" width="60" renderer="MKGrantRender" align="center" isLast="true"/>
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
		<odin:buttonForToolBar text="新建用户" icon="images/add.gif" id="createUserBtn" tooltip="创建新的用户并直接加入该组织"/>
		<odin:separator></odin:separator>
		<odin:buttonForToolBar text="修改用户" icon="images/keyedit.gif"  id="modifyUserBtn" tooltip="修改用户信息"/>
		<odin:separator></odin:separator>
		<odin:buttonForToolBar text="注销用户" icon="images/back.gif" id="removeUserBtn" tooltip="将选中用户从组织中移除，并注销"/>
		<odin:separator></odin:separator>
		<odin:buttonForToolBar text="查询" icon="images/search.gif" id="findUserBtn" tooltip="查询用户"/>
		<odin:separator></odin:separator>
		<odin:buttonForToolBar text="查询条件重置" icon="images/icon/reset.gif" id="resetQuery" tooltip="用于重置查询条件"/>
		<odin:separator></odin:separator>
		<odin:buttonForToolBar text="设置每页条数" icon="images/keyedit.gif" id="setPageSize" handler="setPageSize1" tooltip="用于设置每页记录条数" />
		<odin:separator></odin:separator>
		<odin:buttonForToolBar text="用户组排序" icon="images/icon/folderClosed.gif" id="groupSort" tooltip="用户组显示排序" isLast="true"/>
	</odin:toolBar>
	<div height="100%">
		<odin:panel contentEl="groupTreeContent" property="groupTreePanel" topBarId="btnToolBar"></odin:panel>
	</div>
</body>
<odin:window src="/blank.htm" id="createGroupWin" width="320" height="180" title="用户组增加窗口" modal="true"></odin:window>	
<odin:window src="/blank.htm" id="modifyGroupWin" width="320" height="150" title="用户组修改窗口" modal="true"></odin:window>	
<odin:window src="/blank.htm" id="createUserWin" width="500" height="390" title="创建成员用户窗口" modal="true"></odin:window>
<odin:window src="/blank.htm" id="modifyUserWin" width="500" height="500" title="修改成员用户窗口" modal="true"></odin:window>
<odin:window src="/blank.htm" id="groupSortWin" width="280" height="430" title="用户组排序" modal="true"></odin:window>
<odin:window src="/blank.htm" id="UserDetailWin" width="500" height="500" title="用户详情页面" modal="true"></odin:window>
<script type="text/javascript">
var win_addwin;
var win_addwinnew;
Ext.onReady(function() {
	var pgrid = Ext.getCmp('memberGrid');

	var bbar = pgrid.getBottomToolbar();
	bbar.insertButton(11,[
						new Ext.menu.Separator({cls:'xtb-sep'}),
						new Ext.Spacer({width:100}),
						new Ext.Button({
							icon : 'images/icon/arrowup.gif',
							id:'UpBtn',
						    text:'上移',
						    handler:UpBtn
						}),
						new Ext.Button({
							icon : 'images/icon/arrowdown.gif',
							id:'DownBtn',
						    text:'下移',
						    handler:DownBtn
						}),
						new Ext.Button({
							icon : 'images/icon/save.gif',
							id:'saveSortBtn',
						    text:'保存排序',
						    handler:function(){
						    	if(canSaveSort()==false){
									return;
								}
								var d = bbar.getPageData(); 
								var pageNum = bbar.readPage(d); 
								radow.doEvent('usersort',bbar.initialConfig.pageSize+','+pageNum);
						    }
						})]);
	
	var dstore = pgrid.getStore();
	var ddrow = new Ext.dd.DropTarget(pgrid.container,{
					ddGroup : 'GridDD',
					copy : false,
					notifyDrop : function(dd,e,data){
						if(canSaveSort()===false){
							return false;
						}
						//选中了多少行
						var rows = data.selections;
						//拖动到第几行
						var index = dd.getDragData(e).rowIndex;
						if (typeof(index) == "undefined"){
							return;
						}
						Ext.Msg.confirm("系统提示","是否确认排序？",function(id) { 
							if("yes"==id){
								//修改store
								for ( i=0; i<rows.length; i++){
									var rowData = rows[i];
									if (!this.copy) dstore.remove(rowData);
									dstore.insert(index, rowData);
								}
								pgrid.view.refresh();
								radow.doEvent('usersort');
							}else{
								return;
							}		
						});
					}
				});
	
});

function UpBtn(){
	if(canSaveSort()===false){
		return;
	}

	var grid = odin.ext.getCmp('memberGrid');
	
	var sm = grid.getSelectionModel().getSelections();
	var store = grid.store;
	//alert(store.getCount());
	
	if (sm.length<=0){
		alert('请选中需要排序的人员!')
		return;	
	}
	
	var selectdata = sm[0];  //选中行中的第一行
	var index = store.indexOf(selectdata);
	if (index==0){
		alert('该人员已经排在最顶上!')
		return;
	}
	
	store.remove(selectdata);  //移除
	store.insert(index-1, selectdata);  //插入到上一行前面
	
	grid.getSelectionModel().selectRow(index-1,true);  //选中上移动后的行	
	
	grid.getView().refresh();
}

function DownBtn(){	
	if(canSaveSort()===false){
		return;
	}
	var grid = odin.ext.getCmp('memberGrid');
	
	var sm = grid.getSelectionModel().getSelections();
	var store = grid.store;
	if (sm.length<=0){
		alert('请选中需要排序的人员!')
		return;	
	}
	
	var selectdata = sm[0];  //选中行中的第一行
	var index = store.indexOf(selectdata);
	var total = store.getCount();
	if (index==(total-1) ){
		alert('该人员已经排在最底上!')
		return;
	}
	
	store.remove(selectdata);  //移除
	store.insert(index+1, selectdata);  //插入到上一行前面
	
	grid.getSelectionModel().selectRow(index+1,true);  //选中上移动后的行	
	grid.view.refresh();
}


function canSaveSort(){
	var checkedgroupid = document.getElementById("checkedgroupid").value;

	if(""==checkedgroupid||checkedgroupid==null||"undefined"==checkedgroupid){
		Ext.Msg.alert('系统提示','请点击左侧的用户组树！');
		return false;
	}
}

function getUserType(value, params, rs, rowIndex, colIndex, ds){
	if(value=='1'){
		return '系统管理员';
	}else if(value=='2'){
		return '普通用户';
	}else if(value=='3'){
		return '安全管理员';
	}else{
		return '安全审计员';
	}
}
</script>
