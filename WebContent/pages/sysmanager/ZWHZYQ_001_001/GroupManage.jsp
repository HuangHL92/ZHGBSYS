<%@page import="com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_001.Units"%>
<%@page import="com.insigma.odin.framework.sys.manager.sysmanager.comm.entity.Sysopright"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@page import="com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_001.GroupManagePageModel"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/openWinSmall.js"></script>
<script type="text/javascript" src="pages/sysmanager/ZWHZYQ_001_001/js/comboSearch.js"></script> 
	<style type="text/css">
	    .listname {
		font-family: "微软雅黑", "黑体", "宋体";
		font-size: 14px;
		font-weight: bold;
	}
	    .listsex {
		font-family: "宋体";
		font-size: 12px;
	}
	    .listtitle {
		font-family: "黑体", "宋体";
		font-size: 13px;
	}
	    .listnormal {
		font-family: "宋体";
		font-size: 12px;
		text-decoration: none;
	}
		psinfo{
			background:transparent;
		}
	    .psinfo_name {
		font-family: "微软雅黑", "黑体", "宋体";
		font-size: 16px;
		font-weight: bold;
		color: #333333;
	}
	    .psinfo_title {
		font-family: "宋体";
		color: #000000;
		width: 60px;
		font-weight: 400;
		font-size: 13px;
	}
	.psinfo_brief {
		font-family: "宋体";
		margin-left: 3px;
		margin-right: 15px;
		color: #000000;
		font-size: 12px;
		font-weight:bold;
	}
	    .psinfo_data {
		font-family: "宋体";
		margin-left: 5px;
		margin-right: 15px;
		color: #333333;
		font-size: 12px;
	}
	.search-item {
	    font:normal 11px tahoma, arial, helvetica, sans-serif;
	    padding:3px 10px 3px 10px;
	    border:1px solid #fff;
	    border-bottom:1px solid #eeeeee;
	    white-space:normal;
	    color:#555;
	}
	.search-item h3 {
	    display:block;
	    font:inherit;
	    font-weight:bold;
	    color:#222;
	}
	
	.search-item h3 span {
	    float: right;
	    font-weight:normal;
	    margin:0 0 5px 5px;
	    width:100px;
	    display:block;
	    clear:none;
	}
	.button {
		background-color:#CDB38B
	}
	</style>
<script type="text/javascript">
<odin:menu property="tjMenu">
<odin:menuItem text="单位授权" property="grantTjBtn" handler="openTjJGGrantWin" isLast="true" ></odin:menuItem>
</odin:menu>
var ctxPath = '<%=request.getContextPath()%>';
var tree;
var root;
var commParams = {}; //全局comm参数
Ext.onReady(function() {
	Ext.QuickTips.init();
	
      var id = document.getElementById('ereaid').value;

      var Tree = Ext.tree;
      tree = new Tree.TreePanel( {
 	  id:'group',
//       el : 'tree-div',//目标div容器
       split:false,
       collapseMode : 'mini',
       monitorResize :true, 
       width: 280,
       minSize: 164,
       maxSize: 164,
       rootVisible: false,//是否显示最上级节点
       autoScroll : true,
       animate : true,
       border:false,
       enableDD : false,
       containerScroll : true,
/*        bbar:new Ext.Toolbar({
    	   items : ['定位机构',
    	            search,"<span style='width:120'></span>",
    	            new Ext.Button({
 						text: "<font color='#15428b'><b>查询全部</b></font>",
   						cls:'button',
   						handler: function(){
   							search.onTriggerClick();
   							}
					}),'&nbsp;','-','&nbsp;',new Ext.Button({
 							text: "<font color='#15428b'><b>重置</b></font>",
   							cls:'button',
   							handler: function(){
							search.clearValue();
							Ext.apply(tree,{ rootVisible: false});
		              		var newNodeCfg = {  
		                		 };  
		                 	Ext.applyIf(newNodeCfg,root.attributes);  
		                 	tree.setRootNode(newNodeCfg);  
		              		tree.expandPath(root.getPath(),null,function(){addnode();}); 
   							}
		})
    	         ],
    	         height:45,
    	         layout :'column'
       }),  */ 
       listeners: {
           click: function(n) {
//               Ext.Msg.alert('Navigation Tree Click', 'You clicked: "' + n.attributes.text + '"');
					radow.doEvent('querybyid',n.attributes.id);
           }
       },
       loader : new Tree.TreeLoader( {
             dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysorg.org.PublicWindow&eventNames=orgTreeJsonDataLeftTree'
       })
   });
	 root = new Tree.AsyncTreeNode({
		text : document.getElementById('ereaname').value,
		draggable : false,
		id : document.getElementById('ereaid').value,//默认的node值：?node=-100
		href : "javascript:radow.doEvent('querybyid','G001')"
	});
	tree.setRootNode(root);
//	tree.render();
	tree.expandPath(root.getPath(),null,function(){addnode();});


	       var mytable = new Ext.TabPanel({
                renderTo: 'tree-div',
                id:'qTab',
                autoWidth:true,
               
                activeTab: 0,//激活的页数
                frame: false, 
                border : false, 
                items: [{
                	title:"机构",
                	items:[tree]
                }
               ,
               {
                  title:"查询" ,
                  contentEl:'tab2'
               }
               ]
 
            });
}); 

function addnode(){
	var nodeadd = tree.getRootNode(); 
	var newnode = new Ext.tree.TreeNode({ 
		  text: '无管理单位用户', 
         expanded: false, 
         icon: '<%=request.getContextPath()%>/pages/sysorg/org/images/insideOrgImg1.png',
 	      id:'X001',
         leaf: true ,
         href:"javascript:radow.doEvent('querybyid','X001')"
     });

     nodeadd.appendChild(newnode);
}


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

	return "<a href=\"javascript:openJGGrantWin('"+rs.get('userid')+"','"+rs.get('owner')+"')\">机构授权</a>";
}
function openJGGrantWin(userid,owner){

	document.getElementById("radow_parent_data").value = userid;
	var usertype = document.getElementById("CurUserType").value;
	if(usertype!='2'){
		odin.alert("您好，您非管理员用户，没有操作此模块的权限");
		return;
	}
	/* var CurUserid = document.getElementById('CurUserid').value;
	var CurLoginname = document.getElementById('CurLoginname').value;
	if(owner!=CurUserid && CurLoginname !='system'){
		odin.alert("您好，你不具备该用户的操作权限！");
		return;
	} */
	doOpenPupWinSmall("/radowAction.do?method=doEvent&pageModel=pages.sysmanager.ZWHZYQ_001_003.MechanismComWindow&userid="+userid,
		"机构和人员控制",700,530,{resizable:false},null); 
//	Ext.getCmp('win_pup').resizable =false;
}

function openTjJGGrantWin(){
	var userid = '';
	var owner = '';
	var k = 0;
	var isLeader = document.getElementById("Leader").value;
	if(isLeader=='0'){
		odin.alert("您好，您非管理员用户，没有操作此模块的权限");
		return;
	}
	var grid = Ext.getCmp("memberGrid");
	var store = grid.getStore();
	for (var i = 0; i < store.getCount(); i++) {
	    var record = store.getAt(i);
	    var ck = record.get('logchecked');
	    if(ck){
	    	userid = record.get('userid');
	    	owner = record.get('owner');
	    	k++;
	    }
	}
	if(k==0){
		odin.alert("请选择要授权的人员！");
		return;
	} else if(k>1){
		odin.alert("请选择一个人员进行授权！");
		return;
	}
	/* var CurUserid = document.getElementById('CurUserid').value;
	if(owner!=CurUserid){
		odin.alert("您好，你不具备该用户的操作权限！");
		return;
	} */
	document.getElementById("radow_parent_data").value = userid;
	doOpenPupWinSmall("/radowAction.do?method=doEvent&pageModel=pages.sysmanager.ZWHZYQ_001_003.MechanismComTJWindow&userid="+userid,
		"统计单位授权窗口",600,500,null);
}

function RYGrantRender(value, params, rs, rowIndex, colIndex, ds){
	return "<a href=\"javascript:openRYGrantWin('"+rs.get('userid')+"','"+rs.get('owner')+"')\">人员查看控制</a>";
}
function openRYGrantWin(userid,owner){
	document.getElementById("radow_parent_data").value = userid;
	var isLeader = document.getElementById("Leader").value;
	if(isLeader=='0'){
		odin.alert("您好，您非管理员用户，没有操作此模块的权限");
		return;
	}
	/* var CurUserid = document.getElementById('CurUserid').value;
	var CurLoginname = document.getElementById('CurLoginname').value;
	if(owner!=CurUserid && CurLoginname !='system'){
		odin.alert("您好，你不具备该用户的操作权限！");
		return;
	} */
	doOpenPupWinSmall("/radowAction.do?method=doEvent&pageModel=pages.sysmanager.ZWHZYQ_001_003.PersonComWindow&userid="+userid,
		"人员查看控制窗口",900,500,null);
}
function openRYGrantWin1(userid,owner){
	document.getElementById("radow_parent_data").value = userid;
	var isLeader = document.getElementById("Leader").value;
	if(isLeader=='0'){
		odin.alert("您好，您非管理员用户，没有操作此模块的权限");
		return;
	}
	/* var CurUserid = document.getElementById('CurUserid').value;
	var CurLoginname = document.getElementById('CurLoginname').value;
	if(owner!=CurUserid && CurLoginname !='system'){
		odin.alert("您好，你不具备该用户的操作权限！");
		return;
	} */
	document.getElementById("radow_parent_data").value = userid;
	doOpenPupWinSmall("/radowAction.do?method=doEvent&pageModel=pages.sysmanager.sysuser.ManagementLevel&userid="+userid,
		"管理类别控制窗口",500,400,null);
}
function openMKGrantWin(userid,owner){
	document.getElementById("radow_parent_data").value = userid;
	var usertype = document.getElementById("CurUserType").value;
	if(usertype!='2'){
		odin.alert("您好，您非安全管理员，没有操作此模块的权限");
		return;
	}
	/* var CurUserid = document.getElementById('CurUserid').value;
	var CurLoginname = document.getElementById('CurLoginname').value;
	if(owner!=CurUserid && CurLoginname !='system'){
		odin.alert("您好，你不具备该用户的操作权限！");
		return;
	} */

	doOpenPupWinSmall("/radowAction.do?method=doEvent&pageModel=pages.sysmanager.ZWHZYQ_001_003.ModuleComWindow&userid="+userid,
		"模块角色控制窗口",500,390,null); 
//	document.getElementById("roleid").value = userid;
/* 	doOpenPupWin("/radowAction.do?method=doEvent&pageModel=pages.sysmanager.role.roletree&roleid="+userid,
			"模块控制窗口",330,530,null); */
}
function XXXGrantRender(value, params, rs, rowIndex, colIndex, ds){
	var usertype = document.getElementById("CurUserType").value;
	if(usertype!=2){
		return "<u style=\"color:#D3D3D3\">信息项权限组控制</u>" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + 
		"<u style=\"color:#D3D3D3\">功能模块控制</u>" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + 
		"<u style=\"color:#D3D3D3\">机构和人员授权</u>" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	}
	if(rs.data.usertype=='4'){
		return "<u style=\"color:#D3D3D3\">信息项权限组控制</u>" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + 
		"<u style=\"color:#D3D3D3\">功能模块控制</u>" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + 
		"<u style=\"color:#D3D3D3\">机构和人员授权</u>" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	}
	if(rs.data.usertype!='0'){
		return "<u style=\"color:#D3D3D3\">信息项权限组控制</u>" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + 
		"<u style=\"color:#D3D3D3\">功能模块控制</u>" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + 
		"<a href=\"javascript:openJGGrantWin('"+rs.get('userid')+"','"+rs.get('owner')+"')\">机构和人员授权</a>" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	}
	return "<a href=\"javascript:openXXXGrantWin('"+rs.get('userid')+"','"+rs.get('owner')+"')\">信息项权限组控制</a>" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + 
	"<a href=\"javascript:openMKGrantWin('"+rs.get('userid')+"','"+rs.get('owner')+"')\">功能模块控制</a>" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + 
	"<a href=\"javascript:openJGGrantWin('"+rs.get('userid')+"','"+rs.get('owner')+"')\">机构和人员授权</a>" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
//	"<a href=\"javascript:openRYGrantWin1('"+rs.get('userid')+"','"+rs.get('owner')+"')\">人员查看控制</a>"; 
}

function openXXXGrantWin(userid,owner){
	document.getElementById("radow_parent_data").value = userid;
	var usertype = document.getElementById("CurUserType").value;
	if(usertype!='2'){
		odin.alert("您好，您非管理员用户，没有操作此模块的权限");
		return;
	}
	/* var CurUserid = document.getElementById('CurUserid').value;
	var CurLoginname = document.getElementById('CurLoginname').value;
	if(owner!=CurUserid && CurLoginname !='system'){
		odin.alert("您好，你不具备该用户的操作权限！");
		return;
	} */
	doOpenPupWinSmall("/radowAction.do?method=doEvent&pageModel=pages.sysmanager.ZWHZYQ_001_003.InfoComWindow&userid="+userid,
		"信息项权限组控制窗口", 600, 330, null);
}
function reset(value, params, rs, rowIndex, colIndex, ds){
	return "<img src='"+contextPath+"/images/icon_photodesk.gif'  onclick=\"radow.doEvent('reset','"+value+"');\">"

}

function setPageSize1(){
	//odin.grid.menu.setPageSize('memberGrid');
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
		doOpenPupWin(url, "设置每页条数", 300, 200);
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

function setpp(obj){
	radow.doEvent('personviewp',obj.checked+"");
}


function deleteOrg(value, params, rs, rowIndex, colIndex, ds){
	return "<a href=\"javascript:deleteOrgById('"+rs.get('userdeptid')+"','"+rs.get('b0111')+"')\">删除</a>";
}
function deleteOrgById(id,b01){
	radow.doEvent('deleteOrgById',id);
}
function deleteRender(value, params, rs, rowIndex, colIndex, ds){

	var usertype = document.getElementById("CurUserType").value;
	if(usertype!=1){
		return "<u style=\"color:#D3D3D3\">删除用户</u>" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + 
		"<u style=\"color:#D3D3D3\">密码重置</u>" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	}
	var str = rs.get('userid')+","+rs.get('otherinfo');
	return "<a href=\"javascript:radow.doEvent('removeUserBtn.onclick','"+str+"');\">删除用户</a>"+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + 
	"<a href=\"javascript:radow.doEvent('resetUserPsw.onclick','"+rs.get('userid')+"');\">密码重置</a>";
}	
/* <odin:menu property="loadaddMenum">
<odin:menuItem text="新增普通用户" property="createUserBtn" ></odin:menuItem>
<odin:menuItem text="新增管理员用户" property="createAdminBtn" isLast="true"></odin:menuItem>
</odin:menu> */
</script>

<% 
	String ctxPath = request.getContextPath(); 
	String ereaname = (String)(new GroupManagePageModel().areaInfo.get("areaname"));
	String ereaid = (String)(new GroupManagePageModel().areaInfo.get("areaid"));
	String manager = (String)(new GroupManagePageModel().areaInfo.get("manager"));
%>
							<odin:hidden property="checkedgroupid"/>
							<odin:hidden property="forsearchgroupid"/>
							<odin:hidden property="ereaname" value="<%=ereaname%>" />
							<odin:hidden property="ereaid" value="<%=ereaid%>" />
							<odin:hidden property="manager" value="<%=manager%>" />
							<odin:hidden property="CurLoginname" />
							<odin:hidden property="CurUserType" />
							<odin:hidden property="Leader" />
							<odin:hidden property="CurUserid" />
							<odin:hidden property="idForOrgQuery"/>
							<odin:hidden property="b0121"/>
							<odin:hidden property="roleid"/>
							<odin:hidden property="optionGroup"/>
							<odin:hidden property="qType" title="查询类型"/>
<div id="main" style="margin:0;padding:0;">
	<odin:toolBar property="userbar">
		<odin:textForToolBar text="当前操作组织:<span id=\"groupname\" ></span>" isLast="true"></odin:textForToolBar>
	</odin:toolBar>
	<div id="groupTreePanel"></div>	
	<div id="tab2" class="x-hide-display">
		<table>
			<tr>
				<odin:textEdit property="searchUserNameBtn"  label="用户名"/>
			</tr>
			<tr>
				<odin:textEdit property="searchUserBtn" label="用户登录名"/>
			</tr>
			<tr>
				<odin:select2 property="useful" label="用户状态"/>
			</tr>
			<tr>
			<odin:select2 property="searchUserTypeBtn" codeType="UT24" label="用户类型"/>
			</tr>
			<tr>
			<td height="20" colspan="4"></td>
			</tr>
			<tr valign="middle">
				<td></td>
				<td align="center">
					<odin:button text="查  询"  property="findUserBtn" />
				</td>
				<td></td>
			</tr>
		</table>
	</div>
	
	<table>
		<tr>
			<td style="width: 20%;">
 				<div id="tree-div" style="overflow:auto; height:100%; width: 300px;float: left;border: 2px solid #c3daf9;display: inline;">
				</div> 
			</td>
			<td style="width: 80%;">
				
					<div id="memgrid" style="">						
						<odin:editgrid property="memberGrid" hasRightMenu="false" autoFill="false"  bbarId="pageToolBar" pageSize="20" isFirstLoadData="false" url="/" topBarId="userbar">
						<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
							<odin:gridDataCol name="logchecked"/>
							<odin:gridDataCol name="userid" />
							<odin:gridDataCol name="loginname"/>
							<odin:gridDataCol name="username"/>
							<odin:gridDataCol name="useful" />
							<odin:gridDataCol name="usertype" />
							<odin:gridDataCol name="isleader" />
							<odin:gridDataCol name="otherinfo" />
							<odin:gridDataCol name="b0101" />
							<odin:gridDataCol name="b0114" />
							<odin:gridDataCol name="owner" />
							<odin:gridDataCol name="empid" />
							<odin:gridDataCol name="createdate" isLast="true"/>
						</odin:gridJsonDataModel>
						<odin:gridColumnModel>
							<odin:gridRowNumColumn></odin:gridRowNumColumn>
							<odin:gridColumn dataIndex="owner" width="90" hidden="true" header="创建者id" align="center"/>
							<%-- <odin:gridColumn header="" width="25" editor="checkbox" dataIndex="logchecked" edited="true"/> --%>
							<odin:gridColumn dataIndex="loginname" width="90" header="登录名" align="center"/>
							<odin:gridColumn dataIndex="username" width="90" header="用户名" align="center"/>
							<odin:gridColumn dataIndex="useful" width="80" header="用户状态" align="center"  renderer="statusVal" />
							<odin:gridColumn dataIndex="isleader" header="管理员" width="70" renderer="radow.commUserfulForGrid"  align="center" hidden="true" />
							<odin:gridColumn dataIndex="usertype" header="用户类型" width="100" align="center" renderer="getUserType"/>
							<odin:gridColumn dataIndex="b0101" width="180" header="所属单位" align="center" />
							<odin:gridColumn dataIndex="b0114" width="80" header="单位编码" align="center"  />
							<odin:gridColumn dataIndex="empid" width="80" header="创建者" align="center" hidden="true" />
							<odin:gridColumn dataIndex="createdate" width="80" header="创建时间" renderer="resetDate" align="center" hidden="true" />
<%--  						<odin:gridColumn dataIndex="op1" header="授权" width="90" renderer="JGGrantRender" align="center"/>
							<odin:gridColumn dataIndex="op2" header="授权" width="90" renderer="RYGrantRender" align="center"/>
							<odin:gridColumn dataIndex="op3" header="授权" width="90" renderer="MKGrantRender" align="center"/> --%>
							<%-- <odin:gridColumn dataIndex="op2" header="授权" width="90" renderer="RYGrantRender" align="center" hidden="true" /> --%>
							<odin:gridColumn dataIndex="op4" header="授权" width="380" renderer="XXXGrantRender" align="center" /> 
							<odin:gridColumn dataIndex="op5" header="操作" width="180" renderer="deleteRender" align="center" isLast="true"/> 
						</odin:gridColumnModel>
					</odin:editgrid>									
				</div>	
			
		</td>
	</tr>
</table>													
				
	<odin:toolBar property="btnToolBar" applyTo="groupTreePanel">
		<odin:textForToolBar text="<h3>组织用户管理</h3>" />
		<odin:fill />
		<%-- <odin:buttonForToolBar text="新建用户" icon="images/add.gif" id="createUserBtn" tooltip="创建新的用户并直接加入该组织"/> --%>
		<odin:buttonForToolBar text="新建用户" icon="image/icon021a2.gif" id="createAdminBtn"/>
<%-- 		<odin:separator></odin:separator>
		<odin:buttonForToolBar text="修改用户基本信息" icon="images/keyedit.gif"  id="modifyUserBtn" tooltip="修改用户信息"/>
		<odin:separator></odin:separator>
		<odin:buttonForToolBar text="删除用户" icon="images/back.gif" id="removeUserBtn" tooltip="将选中用户从组织中移除，并注销" /> 
		<odin:separator></odin:separator>
		<odin:buttonForToolBar text="密码重置" icon="images/icon/reset.gif"  id="resetUserPsw" tooltip="重置用户密码"/>--%>

<%-- 		<odin:separator></odin:separator>
		<odin:buttonForToolBar text="查询" icon="images/search.gif" id="findUserBtn" tooltip="查询用户"/> 
		<odin:separator></odin:separator>
		<odin:buttonForToolBar text="查询条件重置" icon="images/icon/reset.gif" id="resetQuery" tooltip="用于重置查询条件"/>--%>
		<odin:separator></odin:separator>
		<odin:buttonForToolBar text="信息项权限组设置" icon="images/keyedit.gif" id="setInfoGroupBtn" tooltip="用于设置信息项组" />
		<odin:separator></odin:separator>
		<%--
		<odin:buttonForToolBar text="统计授权" id="tjgrantBtn" id="tjgrantBtnid" menu ="tjMenu" icon="images/icon/table.gif" cls="x-btn-text-icon" ></odin:buttonForToolBar>
		<odin:separator></odin:separator>
		--%>
		<odin:buttonForToolBar text="设置每页条数" icon="images/keyedit.gif" id="setPageSize" handler="setPageSize1" tooltip="用于设置每页记录条数" isLast="true"/>
<%-- 		<odin:separator></odin:separator>
		<odin:buttonForToolBar text="用户组排序" icon="images/icon/folderClosed.gif" id="groupSort" tooltip="用户组显示排序" isLast="true"/> --%>
	</odin:toolBar>

</div>

<odin:window src="/blank.htm" id="createGroupWin" width="320" height="180" title="用户组增加窗口" modal="true"></odin:window>	
<odin:window src="/blank.htm" id="modifyGroupWin" width="320" height="200" title="用户组修改窗口" modal="true"></odin:window>	
<odin:window src="/blank.htm" id="createUserWin" width="1000" height="550" title="创建成员用户窗口" modal="true" maximizable="false"></odin:window>
<odin:window src="/blank.htm" id="modifyUserWin" width="500" height="400" title="修改成员用户窗口" modal="true"></odin:window>
<odin:window src="/blank.htm" id="setInfoGroupWin" width="900" height="500" title="信息项权限组设置窗口" modal="true"></odin:window>
<odin:window src="/blank.htm" id="groupSortWin" width="280" height="430" title="用户组排序" modal="true"></odin:window>
<odin:window src="/blank.htm" id="UserDetailWin" width="1000" height="550" title="用户详情" modal="true" ></odin:window>
<script type="text/javascript">
//不同用户按钮显示的控制
<%
Units units = new Units();
String usertype = units.getUserType();
%>
Ext.onReady(function() {
	var usertype = <%="'"+usertype+"'"%>
	if(usertype != 1){
		odin.ext.getCmp('createAdminBtn').hide();		
	}
	if(usertype !=2){
		odin.ext.getCmp('setInfoGroupBtn').hide();
	}
});


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
function openUserTypeWin(userid,owner){
	if(document.getElementById('CurUserType').value!='2'){
		odin.alert("您好，您非安全管理员，没有操作此模块的权限!");
		return;
	}
	$h.openWin('usertype','pages.sysmanager.ZWHZYQ_001_003.AlterUserTypeWindow','定义或修改用户类型',300,200,userid,ctxPath);
}
function getUserType(value, params, rs, rowIndex, colIndex, ds){
	var usertype = document.getElementById("CurUserType").value;
	if(usertype==2){
		if(value=='4'){
			return "<a href=\"javascript:openUserTypeWin('"+rs.get('userid')+"','"+rs.get('owner')+"')\">未定义</a>";
		}else if(value=='0'){
			return "<a href=\"javascript:openUserTypeWin('"+rs.get('userid')+"','"+rs.get('owner')+"')\">普通用户</a>";
		}else if(value=='1'){
			return "<a href=\"javascript:openUserTypeWin('"+rs.get('userid')+"','"+rs.get('owner')+"')\">系统管理员</a>"; 
		}else if(value=='2'){
			return "<a href=\"javascript:openUserTypeWin('"+rs.get('userid')+"','"+rs.get('owner')+"')\">安全管理员</a>" ;
		}else if(value=='3'){
			return "<a href=\"javascript:openUserTypeWin('"+rs.get('userid')+"','"+rs.get('owner')+"')\">审计管理员</a>";
		}else {
			return "<a href=\"javascript:openUserTypeWin('"+rs.get('userid')+"','"+rs.get('owner')+"')\">异常</a>";
		}
	}else{
		if(value=='4'){
			return "<u style=\"color:#D3D3D3\">未定义</u>";
		}else if(value=='0'){
			return "<u style=\"color:#D3D3D3\">普通用户</u>";
		}else if(value=='1'){
			return "<u style=\"color:#D3D3D3\">系统管理员</u>"; 
		}else if(value=='2'){
			return "<u style=\"color:#D3D3D3\">安全管理员</u>" ;
		}else if(value=='3'){
			return "<u style=\"color:#D3D3D3\">审计管理员</u>";
		}else {
			return "<u style=\"color:#D3D3D3\">异常</u>";
		}
	}
	
}
Ext.onReady(function() {
	//页面调整
	 Ext.getCmp('memberGrid').setHeight(Ext.getBody().getViewSize().height-objTop(document.getElementById('forView_memberGrid'))[0]-4);
	 Ext.getCmp('qTab').setHeight(Ext.getBody().getViewSize().height-objTop(document.getElementById('forView_memberGrid'))[0]-30);
	 document.getElementById('groupTreePanel').style.width = document.body.clientWidth;
	 document.getElementById('tree-div').style.height = Ext.getCmp('memberGrid').getHeight();
	 Ext.getCmp('memberGrid').setWidth(document.body.clientWidth-306);
	 Ext.getCmp('group').setHeight(document.body.clientHeight-83);

});
function objTop(obj){
    var tt = obj.offsetTop;
    var ll = obj.offsetLeft;
    while(true){
    	if(obj.offsetParent){
    		obj = obj.offsetParent;
    		tt+=obj.offsetTop;
    		ll+=obj.offsetLeft;
    	}else{
    		return [tt,ll];
    	}
	}
    return tt;  
}
function clickTree(){
	radow.doEvent("memberGrid.dogridquery");
}

</script>
