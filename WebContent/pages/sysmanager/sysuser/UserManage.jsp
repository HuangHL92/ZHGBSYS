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
            el : 'tree-div',//Ŀ��div����  
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
            el : 'tree-div',//Ŀ��div����  
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
            id : id,//Ĭ�ϵ�nodeֵ��?node=-100  
            href:"javascript:radow.doEvent('querybyid','"+document.getElementById('ereaid').value+"')"
      });
      tree.setRootNode(root);
      tree.render();
      root.expand();
}); 
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

	return "<a href=\"javascript:openJGGrantWin('"+rs.get('userid')+"')\">������Ȩ</a>";
}
function openJGGrantWin(userid){

	document.getElementById("radow_parent_data").value = userid;
	var CurUserType = document.getElementById("CurUserType").value;
	var CurLoginname = document.getElementById("CurLoginname").value;
	doOpenPupWin("/radowAction.do?method=doEvent&pageModel=pages.sysmanager.ZWHZYQ_001_003.MechanismComWindow&userid="+userid,
		"������Ȩ����",600,500,null);
}
function MKGrantRender(value, params, rs, rowIndex, colIndex, ds){
	return "<a href=\"javascript:openMKGrantWin('"+rs.get('userid')+"')\">��ɫ��Ȩ</a>";
}
function openMKGrantWin(userid){
	document.getElementById("radow_parent_data").value = userid;
	var CurUserType = document.getElementById("CurUserType").value;
	var CurLoginname = document.getElementById("CurLoginname").value;
	doOpenPupWin("/radowAction.do?method=doEvent&pageModel=pages.sysmanager.ZWHZYQ_001_003.ModuleComWindow&userid="+userid,
		"ģ���ɫ���ƴ���",500,390,null);
}
function reset(value, params, rs, rowIndex, colIndex, ds){
	return "<img src='"+contextPath+"/images/icon_photodesk.gif'  onclick=\"radow.doEvent('reset','"+value+"');\">"

}

function setPageSize1(){
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
		doOpenPupWin(url, "����ÿҳ����", 300, 150);
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

</script>

<% 
	String ereaname = (String)(new GroupManagePageModel().areaInfo.get("areaname"));
	String ereaid = (String)(new GroupManagePageModel().areaInfo.get("areaid"));
	String manager = (String)(new GroupManagePageModel().areaInfo.get("manager"));
%>
<body style="height:100%;margin:0;padding:0">
	<odin:toolBar property="treeDivBar">
		<odin:separator></odin:separator>
		<odin:buttonForToolBar text="�½�" icon="images/add.gif" id="createGroupBtn" tooltip="�����û���"/>
		<odin:separator></odin:separator>
		<odin:buttonForToolBar text="�޸�" icon="images/keyedit.gif" id="modifyGroupBtn" tooltip="�޸��û���"/>
		<odin:separator></odin:separator>
		<odin:buttonForToolBar text="ע��" icon="images/back.gif" id="deleteGroupBtn" tooltip="ע���û���"/>
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
							<td><span style="font-size:12px">��ǰ������֯:</span><span id="groupname" style="font-size:12px"></span></td>
							<%-- <odin:textEdit property="optionGroup" label="��ǰ������֯" width="110" disabled="true"/> --%>
							<odin:hidden property="optionGroup"/>
						</tr>
					</table>
					<odin:groupBox title="������" property="ggBox">
						<table width="100%">
							<tr>
								<odin:textEdit property="searchUserNameBtn"  label="�û���"/>
								<odin:textEdit property="searchUserBtn" label="�û���¼��"/>
								<odin:select2 property="useful" label="�û�״̬"/>
								<odin:select2 property="isleader" data="['1','��'],['0','��']" label="�Ƿ�Ϊ����Ա"/>
							</tr>
							<tr>
								<td height="20" colspan="4"></td>
							</tr>
						</table>
					</odin:groupBox>
					<odin:editgrid property="memberGrid" title="�û��б�" autoFill="true" width="590" height="392" bbarId="pageToolBar" pageSize="20" isFirstLoadData="false" url="/">
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
							<odin:gridColumn dataIndex="loginname" width="90" header="�û���¼��" align="center"/>
							<odin:gridColumn dataIndex="username" width="90" header="�û���" align="center"/>
							<odin:gridColumn dataIndex="useful" width="60" header="�û�״̬" align="center"  renderer="statusVal"/>
							<odin:gridColumn dataIndex="isleader" header="����Ա" width="50" renderer="radow.commUserfulForGrid"  align="center"/>
							<odin:gridColumn dataIndex="usertype" header="�û�����" width="70" renderer="getUserType" align="center"/>
							<odin:gridColumn dataIndex="empid" width="80" header="������" align="center" hidden="true" />
							<odin:gridColumn dataIndex="createdate" width="80" header="����ʱ��" renderer="resetDate" align="center" hidden="true"/>
							<odin:gridColumn dataIndex="op1" header="��Ȩ" width="60" renderer="JGGrantRender" align="center"/>
							<odin:gridColumn dataIndex="op3" header="��Ȩ" width="60" renderer="MKGrantRender" align="center" isLast="true"/>
						</odin:gridColumnModel>
					</odin:editgrid>
				</td>
			</tr>
		</table>
	</div>

	<odin:toolBar property="btnToolBar">
		<odin:textForToolBar text="<h3>��֯�û�����</h3>" />
		<odin:fill />
		<odin:separator></odin:separator>
		<odin:buttonForToolBar text="��������" icon="images/icon/reset.gif"  id="resetUserPsw" tooltip="�����û�����"/>
		<odin:separator></odin:separator>
		<odin:buttonForToolBar text="�½��û�" icon="images/add.gif" id="createUserBtn" tooltip="�����µ��û���ֱ�Ӽ������֯"/>
		<odin:separator></odin:separator>
		<odin:buttonForToolBar text="�޸��û�" icon="images/keyedit.gif"  id="modifyUserBtn" tooltip="�޸��û���Ϣ"/>
		<odin:separator></odin:separator>
		<odin:buttonForToolBar text="ע���û�" icon="images/back.gif" id="removeUserBtn" tooltip="��ѡ���û�����֯���Ƴ�����ע��"/>
		<odin:separator></odin:separator>
		<odin:buttonForToolBar text="��ѯ" icon="images/search.gif" id="findUserBtn" tooltip="��ѯ�û�"/>
		<odin:separator></odin:separator>
		<odin:buttonForToolBar text="��ѯ��������" icon="images/icon/reset.gif" id="resetQuery" tooltip="�������ò�ѯ����"/>
		<odin:separator></odin:separator>
		<odin:buttonForToolBar text="����ÿҳ����" icon="images/keyedit.gif" id="setPageSize" handler="setPageSize1" tooltip="��������ÿҳ��¼����" />
		<odin:separator></odin:separator>
		<odin:buttonForToolBar text="�û�������" icon="images/icon/folderClosed.gif" id="groupSort" tooltip="�û�����ʾ����" isLast="true"/>
	</odin:toolBar>
	<div height="100%">
		<odin:panel contentEl="groupTreeContent" property="groupTreePanel" topBarId="btnToolBar"></odin:panel>
	</div>
</body>
<odin:window src="/blank.htm" id="createGroupWin" width="320" height="180" title="�û������Ӵ���" modal="true"></odin:window>	
<odin:window src="/blank.htm" id="modifyGroupWin" width="320" height="150" title="�û����޸Ĵ���" modal="true"></odin:window>	
<odin:window src="/blank.htm" id="createUserWin" width="500" height="390" title="������Ա�û�����" modal="true"></odin:window>
<odin:window src="/blank.htm" id="modifyUserWin" width="500" height="500" title="�޸ĳ�Ա�û�����" modal="true"></odin:window>
<odin:window src="/blank.htm" id="groupSortWin" width="280" height="430" title="�û�������" modal="true"></odin:window>
<odin:window src="/blank.htm" id="UserDetailWin" width="500" height="500" title="�û�����ҳ��" modal="true"></odin:window>
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

function getUserType(value, params, rs, rowIndex, colIndex, ds){
	if(value=='1'){
		return 'ϵͳ����Ա';
	}else if(value=='2'){
		return '��ͨ�û�';
	}else if(value=='3'){
		return '��ȫ����Ա';
	}else{
		return '��ȫ���Ա';
	}
}
</script>
