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
		font-family: "΢���ź�", "����", "����";
		font-size: 14px;
		font-weight: bold;
	}
	    .listsex {
		font-family: "����";
		font-size: 12px;
	}
	    .listtitle {
		font-family: "����", "����";
		font-size: 13px;
	}
	    .listnormal {
		font-family: "����";
		font-size: 12px;
		text-decoration: none;
	}
		psinfo{
			background:transparent;
		}
	    .psinfo_name {
		font-family: "΢���ź�", "����", "����";
		font-size: 16px;
		font-weight: bold;
		color: #333333;
	}
	    .psinfo_title {
		font-family: "����";
		color: #000000;
		width: 60px;
		font-weight: 400;
		font-size: 13px;
	}
	.psinfo_brief {
		font-family: "����";
		margin-left: 3px;
		margin-right: 15px;
		color: #000000;
		font-size: 12px;
		font-weight:bold;
	}
	    .psinfo_data {
		font-family: "����";
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
<odin:menuItem text="��λ��Ȩ" property="grantTjBtn" handler="openTjJGGrantWin" isLast="true" ></odin:menuItem>
</odin:menu>
var ctxPath = '<%=request.getContextPath()%>';
var tree;
var root;
var commParams = {}; //ȫ��comm����
Ext.onReady(function() {
	Ext.QuickTips.init();
	
      var id = document.getElementById('ereaid').value;

      var Tree = Ext.tree;
      tree = new Tree.TreePanel( {
 	  id:'group',
//       el : 'tree-div',//Ŀ��div����
       split:false,
       collapseMode : 'mini',
       monitorResize :true, 
       width: 280,
       minSize: 164,
       maxSize: 164,
       rootVisible: false,//�Ƿ���ʾ���ϼ��ڵ�
       autoScroll : true,
       animate : true,
       border:false,
       enableDD : false,
       containerScroll : true,
/*        bbar:new Ext.Toolbar({
    	   items : ['��λ����',
    	            search,"<span style='width:120'></span>",
    	            new Ext.Button({
 						text: "<font color='#15428b'><b>��ѯȫ��</b></font>",
   						cls:'button',
   						handler: function(){
   							search.onTriggerClick();
   							}
					}),'&nbsp;','-','&nbsp;',new Ext.Button({
 							text: "<font color='#15428b'><b>����</b></font>",
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
		id : document.getElementById('ereaid').value,//Ĭ�ϵ�nodeֵ��?node=-100
		href : "javascript:radow.doEvent('querybyid','G001')"
	});
	tree.setRootNode(root);
//	tree.render();
	tree.expandPath(root.getPath(),null,function(){addnode();});


	       var mytable = new Ext.TabPanel({
                renderTo: 'tree-div',
                id:'qTab',
                autoWidth:true,
               
                activeTab: 0,//�����ҳ��
                frame: false, 
                border : false, 
                items: [{
                	title:"����",
                	items:[tree]
                }
               ,
               {
                  title:"��ѯ" ,
                  contentEl:'tab2'
               }
               ]
 
            });
}); 

function addnode(){
	var nodeadd = tree.getRootNode(); 
	var newnode = new Ext.tree.TreeNode({ 
		  text: '�޹���λ�û�', 
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
		return '��Ч';
	}else if(value=='0'){
		return '��Ч';
	}else{
		return '״̬�쳣';
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
* ������Ȩ��render����
*/
function JGGrantRender(value, params, rs, rowIndex, colIndex, ds){

	return "<a href=\"javascript:openJGGrantWin('"+rs.get('userid')+"','"+rs.get('owner')+"')\">������Ȩ</a>";
}
function openJGGrantWin(userid,owner){

	document.getElementById("radow_parent_data").value = userid;
	var usertype = document.getElementById("CurUserType").value;
	if(usertype!='2'){
		odin.alert("���ã����ǹ���Ա�û���û�в�����ģ���Ȩ��");
		return;
	}
	/* var CurUserid = document.getElementById('CurUserid').value;
	var CurLoginname = document.getElementById('CurLoginname').value;
	if(owner!=CurUserid && CurLoginname !='system'){
		odin.alert("���ã��㲻�߱����û��Ĳ���Ȩ�ޣ�");
		return;
	} */
	doOpenPupWinSmall("/radowAction.do?method=doEvent&pageModel=pages.sysmanager.ZWHZYQ_001_003.MechanismComWindow&userid="+userid,
		"��������Ա����",700,530,{resizable:false},null); 
//	Ext.getCmp('win_pup').resizable =false;
}

function openTjJGGrantWin(){
	var userid = '';
	var owner = '';
	var k = 0;
	var isLeader = document.getElementById("Leader").value;
	if(isLeader=='0'){
		odin.alert("���ã����ǹ���Ա�û���û�в�����ģ���Ȩ��");
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
		odin.alert("��ѡ��Ҫ��Ȩ����Ա��");
		return;
	} else if(k>1){
		odin.alert("��ѡ��һ����Ա������Ȩ��");
		return;
	}
	/* var CurUserid = document.getElementById('CurUserid').value;
	if(owner!=CurUserid){
		odin.alert("���ã��㲻�߱����û��Ĳ���Ȩ�ޣ�");
		return;
	} */
	document.getElementById("radow_parent_data").value = userid;
	doOpenPupWinSmall("/radowAction.do?method=doEvent&pageModel=pages.sysmanager.ZWHZYQ_001_003.MechanismComTJWindow&userid="+userid,
		"ͳ�Ƶ�λ��Ȩ����",600,500,null);
}

function RYGrantRender(value, params, rs, rowIndex, colIndex, ds){
	return "<a href=\"javascript:openRYGrantWin('"+rs.get('userid')+"','"+rs.get('owner')+"')\">��Ա�鿴����</a>";
}
function openRYGrantWin(userid,owner){
	document.getElementById("radow_parent_data").value = userid;
	var isLeader = document.getElementById("Leader").value;
	if(isLeader=='0'){
		odin.alert("���ã����ǹ���Ա�û���û�в�����ģ���Ȩ��");
		return;
	}
	/* var CurUserid = document.getElementById('CurUserid').value;
	var CurLoginname = document.getElementById('CurLoginname').value;
	if(owner!=CurUserid && CurLoginname !='system'){
		odin.alert("���ã��㲻�߱����û��Ĳ���Ȩ�ޣ�");
		return;
	} */
	doOpenPupWinSmall("/radowAction.do?method=doEvent&pageModel=pages.sysmanager.ZWHZYQ_001_003.PersonComWindow&userid="+userid,
		"��Ա�鿴���ƴ���",900,500,null);
}
function openRYGrantWin1(userid,owner){
	document.getElementById("radow_parent_data").value = userid;
	var isLeader = document.getElementById("Leader").value;
	if(isLeader=='0'){
		odin.alert("���ã����ǹ���Ա�û���û�в�����ģ���Ȩ��");
		return;
	}
	/* var CurUserid = document.getElementById('CurUserid').value;
	var CurLoginname = document.getElementById('CurLoginname').value;
	if(owner!=CurUserid && CurLoginname !='system'){
		odin.alert("���ã��㲻�߱����û��Ĳ���Ȩ�ޣ�");
		return;
	} */
	document.getElementById("radow_parent_data").value = userid;
	doOpenPupWinSmall("/radowAction.do?method=doEvent&pageModel=pages.sysmanager.sysuser.ManagementLevel&userid="+userid,
		"���������ƴ���",500,400,null);
}
function openMKGrantWin(userid,owner){
	document.getElementById("radow_parent_data").value = userid;
	var usertype = document.getElementById("CurUserType").value;
	if(usertype!='2'){
		odin.alert("���ã����ǰ�ȫ����Ա��û�в�����ģ���Ȩ��");
		return;
	}
	/* var CurUserid = document.getElementById('CurUserid').value;
	var CurLoginname = document.getElementById('CurLoginname').value;
	if(owner!=CurUserid && CurLoginname !='system'){
		odin.alert("���ã��㲻�߱����û��Ĳ���Ȩ�ޣ�");
		return;
	} */

	doOpenPupWinSmall("/radowAction.do?method=doEvent&pageModel=pages.sysmanager.ZWHZYQ_001_003.ModuleComWindow&userid="+userid,
		"ģ���ɫ���ƴ���",500,390,null); 
//	document.getElementById("roleid").value = userid;
/* 	doOpenPupWin("/radowAction.do?method=doEvent&pageModel=pages.sysmanager.role.roletree&roleid="+userid,
			"ģ����ƴ���",330,530,null); */
}
function XXXGrantRender(value, params, rs, rowIndex, colIndex, ds){
	var usertype = document.getElementById("CurUserType").value;
	if(usertype!=2){
		return "<u style=\"color:#D3D3D3\">��Ϣ��Ȩ�������</u>" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + 
		"<u style=\"color:#D3D3D3\">����ģ�����</u>" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + 
		"<u style=\"color:#D3D3D3\">��������Ա��Ȩ</u>" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	}
	if(rs.data.usertype=='4'){
		return "<u style=\"color:#D3D3D3\">��Ϣ��Ȩ�������</u>" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + 
		"<u style=\"color:#D3D3D3\">����ģ�����</u>" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + 
		"<u style=\"color:#D3D3D3\">��������Ա��Ȩ</u>" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	}
	if(rs.data.usertype!='0'){
		return "<u style=\"color:#D3D3D3\">��Ϣ��Ȩ�������</u>" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + 
		"<u style=\"color:#D3D3D3\">����ģ�����</u>" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + 
		"<a href=\"javascript:openJGGrantWin('"+rs.get('userid')+"','"+rs.get('owner')+"')\">��������Ա��Ȩ</a>" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	}
	return "<a href=\"javascript:openXXXGrantWin('"+rs.get('userid')+"','"+rs.get('owner')+"')\">��Ϣ��Ȩ�������</a>" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + 
	"<a href=\"javascript:openMKGrantWin('"+rs.get('userid')+"','"+rs.get('owner')+"')\">����ģ�����</a>" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + 
	"<a href=\"javascript:openJGGrantWin('"+rs.get('userid')+"','"+rs.get('owner')+"')\">��������Ա��Ȩ</a>" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
//	"<a href=\"javascript:openRYGrantWin1('"+rs.get('userid')+"','"+rs.get('owner')+"')\">��Ա�鿴����</a>"; 
}

function openXXXGrantWin(userid,owner){
	document.getElementById("radow_parent_data").value = userid;
	var usertype = document.getElementById("CurUserType").value;
	if(usertype!='2'){
		odin.alert("���ã����ǹ���Ա�û���û�в�����ģ���Ȩ��");
		return;
	}
	/* var CurUserid = document.getElementById('CurUserid').value;
	var CurLoginname = document.getElementById('CurLoginname').value;
	if(owner!=CurUserid && CurLoginname !='system'){
		odin.alert("���ã��㲻�߱����û��Ĳ���Ȩ�ޣ�");
		return;
	} */
	doOpenPupWinSmall("/radowAction.do?method=doEvent&pageModel=pages.sysmanager.ZWHZYQ_001_003.InfoComWindow&userid="+userid,
		"��Ϣ��Ȩ������ƴ���", 600, 330, null);
}
function reset(value, params, rs, rowIndex, colIndex, ds){
	return "<img src='"+contextPath+"/images/icon_photodesk.gif'  onclick=\"radow.doEvent('reset','"+value+"');\">"

}

function setPageSize1(){
	//odin.grid.menu.setPageSize('memberGrid');
	var gridId = 'memberGrid';
	if (!Ext.getCmp(gridId)) {
		odin.error("Ҫ������grid�����ڣ�gridId=" + gridId);
		return;
	}
	var grid = Ext.getCmp(gridId);
	var store = grid.getStore();
	if (store.getCount() == 0) {
		//odin.error("���Ȳ�ѯ�����ݺ��ٽ��б�������");
		//return;
	}
	var pageingToolbar = (grid.getBottomToolbar() || grid.getTopToolbar());
	if (pageingToolbar && pageingToolbar.pageSize) {
		gridIdForSeting = gridId;
		var url = contextPath + "/sys/comm/commSetGrid.jsp";
		doOpenPupWin(url, "����ÿҳ����", 300, 200);
	} else {
		odin.error("�Ƿ�ҳgrid����ʹ�ô˹��ܣ�");
		return;
	}
}

//���ڸ�ʽ����
function resetDate(value, params, rs, rowIndex, colIndex, ds){
	var newdate = value.substring(0,10);
	return newdate;
}

//����ȫ������
function expAllData(){
	odin.grid.menu.expExcelFromGrid('memberGrid', null, null,
								null, false);
}

function setpp(obj){
	radow.doEvent('personviewp',obj.checked+"");
}


function deleteOrg(value, params, rs, rowIndex, colIndex, ds){
	return "<a href=\"javascript:deleteOrgById('"+rs.get('userdeptid')+"','"+rs.get('b0111')+"')\">ɾ��</a>";
}
function deleteOrgById(id,b01){
	radow.doEvent('deleteOrgById',id);
}
function deleteRender(value, params, rs, rowIndex, colIndex, ds){

	var usertype = document.getElementById("CurUserType").value;
	if(usertype!=1){
		return "<u style=\"color:#D3D3D3\">ɾ���û�</u>" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + 
		"<u style=\"color:#D3D3D3\">��������</u>" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	}
	var str = rs.get('userid')+","+rs.get('otherinfo');
	return "<a href=\"javascript:radow.doEvent('removeUserBtn.onclick','"+str+"');\">ɾ���û�</a>"+ "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + 
	"<a href=\"javascript:radow.doEvent('resetUserPsw.onclick','"+rs.get('userid')+"');\">��������</a>";
}	
/* <odin:menu property="loadaddMenum">
<odin:menuItem text="������ͨ�û�" property="createUserBtn" ></odin:menuItem>
<odin:menuItem text="��������Ա�û�" property="createAdminBtn" isLast="true"></odin:menuItem>
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
							<odin:hidden property="qType" title="��ѯ����"/>
<div id="main" style="margin:0;padding:0;">
	<odin:toolBar property="userbar">
		<odin:textForToolBar text="��ǰ������֯:<span id=\"groupname\" ></span>" isLast="true"></odin:textForToolBar>
	</odin:toolBar>
	<div id="groupTreePanel"></div>	
	<div id="tab2" class="x-hide-display">
		<table>
			<tr>
				<odin:textEdit property="searchUserNameBtn"  label="�û���"/>
			</tr>
			<tr>
				<odin:textEdit property="searchUserBtn" label="�û���¼��"/>
			</tr>
			<tr>
				<odin:select2 property="useful" label="�û�״̬"/>
			</tr>
			<tr>
			<odin:select2 property="searchUserTypeBtn" codeType="UT24" label="�û�����"/>
			</tr>
			<tr>
			<td height="20" colspan="4"></td>
			</tr>
			<tr valign="middle">
				<td></td>
				<td align="center">
					<odin:button text="��  ѯ"  property="findUserBtn" />
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
							<odin:gridColumn dataIndex="owner" width="90" hidden="true" header="������id" align="center"/>
							<%-- <odin:gridColumn header="" width="25" editor="checkbox" dataIndex="logchecked" edited="true"/> --%>
							<odin:gridColumn dataIndex="loginname" width="90" header="��¼��" align="center"/>
							<odin:gridColumn dataIndex="username" width="90" header="�û���" align="center"/>
							<odin:gridColumn dataIndex="useful" width="80" header="�û�״̬" align="center"  renderer="statusVal" />
							<odin:gridColumn dataIndex="isleader" header="����Ա" width="70" renderer="radow.commUserfulForGrid"  align="center" hidden="true" />
							<odin:gridColumn dataIndex="usertype" header="�û�����" width="100" align="center" renderer="getUserType"/>
							<odin:gridColumn dataIndex="b0101" width="180" header="������λ" align="center" />
							<odin:gridColumn dataIndex="b0114" width="80" header="��λ����" align="center"  />
							<odin:gridColumn dataIndex="empid" width="80" header="������" align="center" hidden="true" />
							<odin:gridColumn dataIndex="createdate" width="80" header="����ʱ��" renderer="resetDate" align="center" hidden="true" />
<%--  						<odin:gridColumn dataIndex="op1" header="��Ȩ" width="90" renderer="JGGrantRender" align="center"/>
							<odin:gridColumn dataIndex="op2" header="��Ȩ" width="90" renderer="RYGrantRender" align="center"/>
							<odin:gridColumn dataIndex="op3" header="��Ȩ" width="90" renderer="MKGrantRender" align="center"/> --%>
							<%-- <odin:gridColumn dataIndex="op2" header="��Ȩ" width="90" renderer="RYGrantRender" align="center" hidden="true" /> --%>
							<odin:gridColumn dataIndex="op4" header="��Ȩ" width="380" renderer="XXXGrantRender" align="center" /> 
							<odin:gridColumn dataIndex="op5" header="����" width="180" renderer="deleteRender" align="center" isLast="true"/> 
						</odin:gridColumnModel>
					</odin:editgrid>									
				</div>	
			
		</td>
	</tr>
</table>													
				
	<odin:toolBar property="btnToolBar" applyTo="groupTreePanel">
		<odin:textForToolBar text="<h3>��֯�û�����</h3>" />
		<odin:fill />
		<%-- <odin:buttonForToolBar text="�½��û�" icon="images/add.gif" id="createUserBtn" tooltip="�����µ��û���ֱ�Ӽ������֯"/> --%>
		<odin:buttonForToolBar text="�½��û�" icon="image/icon021a2.gif" id="createAdminBtn"/>
<%-- 		<odin:separator></odin:separator>
		<odin:buttonForToolBar text="�޸��û�������Ϣ" icon="images/keyedit.gif"  id="modifyUserBtn" tooltip="�޸��û���Ϣ"/>
		<odin:separator></odin:separator>
		<odin:buttonForToolBar text="ɾ���û�" icon="images/back.gif" id="removeUserBtn" tooltip="��ѡ���û�����֯���Ƴ�����ע��" /> 
		<odin:separator></odin:separator>
		<odin:buttonForToolBar text="��������" icon="images/icon/reset.gif"  id="resetUserPsw" tooltip="�����û�����"/>--%>

<%-- 		<odin:separator></odin:separator>
		<odin:buttonForToolBar text="��ѯ" icon="images/search.gif" id="findUserBtn" tooltip="��ѯ�û�"/> 
		<odin:separator></odin:separator>
		<odin:buttonForToolBar text="��ѯ��������" icon="images/icon/reset.gif" id="resetQuery" tooltip="�������ò�ѯ����"/>--%>
		<odin:separator></odin:separator>
		<odin:buttonForToolBar text="��Ϣ��Ȩ��������" icon="images/keyedit.gif" id="setInfoGroupBtn" tooltip="����������Ϣ����" />
		<odin:separator></odin:separator>
		<%--
		<odin:buttonForToolBar text="ͳ����Ȩ" id="tjgrantBtn" id="tjgrantBtnid" menu ="tjMenu" icon="images/icon/table.gif" cls="x-btn-text-icon" ></odin:buttonForToolBar>
		<odin:separator></odin:separator>
		--%>
		<odin:buttonForToolBar text="����ÿҳ����" icon="images/keyedit.gif" id="setPageSize" handler="setPageSize1" tooltip="��������ÿҳ��¼����" isLast="true"/>
<%-- 		<odin:separator></odin:separator>
		<odin:buttonForToolBar text="�û�������" icon="images/icon/folderClosed.gif" id="groupSort" tooltip="�û�����ʾ����" isLast="true"/> --%>
	</odin:toolBar>

</div>

<odin:window src="/blank.htm" id="createGroupWin" width="320" height="180" title="�û������Ӵ���" modal="true"></odin:window>	
<odin:window src="/blank.htm" id="modifyGroupWin" width="320" height="200" title="�û����޸Ĵ���" modal="true"></odin:window>	
<odin:window src="/blank.htm" id="createUserWin" width="1000" height="550" title="������Ա�û�����" modal="true" maximizable="false"></odin:window>
<odin:window src="/blank.htm" id="modifyUserWin" width="500" height="400" title="�޸ĳ�Ա�û�����" modal="true"></odin:window>
<odin:window src="/blank.htm" id="setInfoGroupWin" width="900" height="500" title="��Ϣ��Ȩ�������ô���" modal="true"></odin:window>
<odin:window src="/blank.htm" id="groupSortWin" width="280" height="430" title="�û�������" modal="true"></odin:window>
<odin:window src="/blank.htm" id="UserDetailWin" width="1000" height="550" title="�û�����" modal="true" ></odin:window>
<script type="text/javascript">
//��ͬ�û���ť��ʾ�Ŀ���
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
						    text:'����',
						    handler:UpBtn
						}),
						new Ext.Button({
							icon : 'images/icon/arrowdown.gif',
							id:'DownBtn',
						    text:'����',
						    handler:DownBtn
						}),
						new Ext.Button({
							icon : 'images/icon/save.gif',
							id:'saveSortBtn',
						    text:'��������',
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
						//ѡ���˶�����
						var rows = data.selections;
						//�϶����ڼ���
						var index = dd.getDragData(e).rowIndex;
						if (typeof(index) == "undefined"){
							return;
						}
						Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ�ȷ������",function(id) { 
							if("yes"==id){
								//�޸�store
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
		alert('��ѡ����Ҫ�������Ա!')
		return;	
	}
	
	var selectdata = sm[0];  //ѡ�����еĵ�һ��
	var index = store.indexOf(selectdata);
	if (index==0){
		alert('����Ա�Ѿ��������!')
		return;
	}
	
	store.remove(selectdata);  //�Ƴ�
	store.insert(index-1, selectdata);  //���뵽��һ��ǰ��
	
	grid.getSelectionModel().selectRow(index-1,true);  //ѡ�����ƶ������	
	
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
		alert('��ѡ����Ҫ�������Ա!')
		return;	
	}
	
	var selectdata = sm[0];  //ѡ�����еĵ�һ��
	var index = store.indexOf(selectdata);
	var total = store.getCount();
	if (index==(total-1) ){
		alert('����Ա�Ѿ����������!')
		return;
	}
	
	store.remove(selectdata);  //�Ƴ�
	store.insert(index+1, selectdata);  //���뵽��һ��ǰ��
	
	grid.getSelectionModel().selectRow(index+1,true);  //ѡ�����ƶ������	
	grid.view.refresh();
}


function canSaveSort(){
	var checkedgroupid = document.getElementById("checkedgroupid").value;

	if(""==checkedgroupid||checkedgroupid==null||"undefined"==checkedgroupid){
		Ext.Msg.alert('ϵͳ��ʾ','���������û�������');
		return false;
	}
}
function openUserTypeWin(userid,owner){
	if(document.getElementById('CurUserType').value!='2'){
		odin.alert("���ã����ǰ�ȫ����Ա��û�в�����ģ���Ȩ��!");
		return;
	}
	$h.openWin('usertype','pages.sysmanager.ZWHZYQ_001_003.AlterUserTypeWindow','������޸��û�����',300,200,userid,ctxPath);
}
function getUserType(value, params, rs, rowIndex, colIndex, ds){
	var usertype = document.getElementById("CurUserType").value;
	if(usertype==2){
		if(value=='4'){
			return "<a href=\"javascript:openUserTypeWin('"+rs.get('userid')+"','"+rs.get('owner')+"')\">δ����</a>";
		}else if(value=='0'){
			return "<a href=\"javascript:openUserTypeWin('"+rs.get('userid')+"','"+rs.get('owner')+"')\">��ͨ�û�</a>";
		}else if(value=='1'){
			return "<a href=\"javascript:openUserTypeWin('"+rs.get('userid')+"','"+rs.get('owner')+"')\">ϵͳ����Ա</a>"; 
		}else if(value=='2'){
			return "<a href=\"javascript:openUserTypeWin('"+rs.get('userid')+"','"+rs.get('owner')+"')\">��ȫ����Ա</a>" ;
		}else if(value=='3'){
			return "<a href=\"javascript:openUserTypeWin('"+rs.get('userid')+"','"+rs.get('owner')+"')\">��ƹ���Ա</a>";
		}else {
			return "<a href=\"javascript:openUserTypeWin('"+rs.get('userid')+"','"+rs.get('owner')+"')\">�쳣</a>";
		}
	}else{
		if(value=='4'){
			return "<u style=\"color:#D3D3D3\">δ����</u>";
		}else if(value=='0'){
			return "<u style=\"color:#D3D3D3\">��ͨ�û�</u>";
		}else if(value=='1'){
			return "<u style=\"color:#D3D3D3\">ϵͳ����Ա</u>"; 
		}else if(value=='2'){
			return "<u style=\"color:#D3D3D3\">��ȫ����Ա</u>" ;
		}else if(value=='3'){
			return "<u style=\"color:#D3D3D3\">��ƹ���Ա</u>";
		}else {
			return "<u style=\"color:#D3D3D3\">�쳣</u>";
		}
	}
	
}
Ext.onReady(function() {
	//ҳ�����
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
