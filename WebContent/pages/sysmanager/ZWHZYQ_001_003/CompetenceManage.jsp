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
            el : 'tree-div',//Ŀ��div����
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
            el : 'tree-div',//Ŀ��div����
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
            id : document.getElementById('ereaid').value,//Ĭ�ϵ�nodeֵ��?node=-100
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
    tree.root.reload();
    tree.expandAll();
}
/**
* ������Ȩ��render����
*/
function dataGrantRender(value, params, rs, rowIndex, colIndex, ds){
	return "<a href=\"javascript:openDataGrantWin('"+value+"')\">������Ȩ</a>";
}
function openDataGrantWin(userid){
	document.getElementById("radow_parent_data").value = userid;
	doOpenPupWin("/radowAction.do?method=doEvent&pageModel=pages.sysmanager.datagroup.UserDataGrant&userid="+userid,
		"������Ȩ����",270,446,null);
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
	<odin:buttonForToolBar text="��Ϣ��������" id="setInfoGroupBtn" tooltip="����������Ϣ����"/>
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
					<odin:textEdit property="optionGroup" label="��ǰ������֯" width="110" disabled="true"/>
				</tr>
				<tr>
					<td height="5" colspan="4"></td>
				</tr>
			</table>
			<odin:groupBox title="������" property="ggBox">
			<table width="100%">
				<tr>
					<odin:textEdit property="searchUserNameBtn"  label="�û���"/>
					<odin:textEdit property="searchUserBtn" label="�û���¼��"/>
					<odin:select property="useful" label="�û�״̬"/>
					<odin:select property="isleader" label="�Ƿ�Ϊ����Ա"/>
				</tr>
				<tr>
					<td height="5" colspan="4"></td>
				</tr>
			</table>
			</odin:groupBox>
		<odin:editgrid property="memberGrid" title="�û��б�" autoFill="false" width="590" height="320" bbarId="pageToolBar" pageSize="20" isFirstLoadData="false" url="/">
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
					<odin:gridColumn dataIndex="loginname" width="110" header="�û���¼��" align="center"/>
					<odin:gridColumn dataIndex="name" width="100" header="�û���" align="center"/>
					<odin:gridColumn dataIndex="status" width="100" header="�û�״̬" align="center"  renderer="statusVal"/>
					<odin:gridColumn dataIndex="isleader" header="�Ƿ�Ϊ����Ա" width="80" renderer="radow.commUserfulForGrid"  align="center"/>
					<odin:gridColumn dataIndex="otherinfo" width="130" header="������" align="center" />
					<odin:gridColumn dataIndex="createdate" width="130" header="����ʱ��" align="center" isLast="true"/>
					<%-- <odin:gridColumn dataIndex="loginname" width="110" header="�û���¼��" align="center"/>
					<odin:gridColumn dataIndex="name" width="100" header="����" align="center"/>
					<odin:gridColumn dataIndex="desc" width="130" header="����" align="center"/>
					<odin:gridColumn dataIndex="status" width="100" header="״̬" align="center"  renderer="statusVal"/>
					<odin:gridColumn dataIndex="isleader" header="����Ա" width="80" renderer="radow.commUserfulForGrid"  align="center"/>
					<odin:gridColumn dataIndex="loginname" header="�޸�" width="50" renderer="radow.commGrantForGrid" align="center"/>
					<odin:gridColumn dataIndex="id" header="��������" width="100" renderer="reset" align="center"/>
					<odin:gridColumn dataIndex="id" header="ɾ��" width="50" renderer="radow.commGridColDelete" align="center"/>
					<odin:gridColumn dataIndex="id" header="������Ȩ" width="100" renderer="dataGrantRender" align="center" isLast="true"/> --%>
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
	<odin:buttonForToolBar text="��Ա�鿴����" id="perFindComBtn" tooltip="���ڿ����û�����ĳ������Ա�Ĳ��ɼ�"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="����Ȩ�޷���" id="mechanismComBtn" tooltip="���ڷ����û��Ļ���Ȩ��"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="ģ��Ȩ�޷���" id="ModuleComBtn" tooltip="���ڷ����û���ģ��Ȩ��"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="��Ϣ��Ȩ�޷���" id="InfoComBtn" tooltip="���ڷ����û�����Ϣ��Ȩ��"/>
	<odin:separator></odin:separator> 
	<odin:buttonForToolBar text="��ѯ" id="findUserBtn" tooltip="��ѯ�û�" isLast="true"/>
</odin:toolBar>
<odin:panel contentEl="groupTreeContent" property="groupTreePanel" topBarId="btnToolBar"></odin:panel>

<odin:window src="/blank.htm" id="ModuleComWin" width="500" height="300" title="ģ��Ȩ�޷���ҳ��" modal="true"></odin:window>	
<odin:window src="/blank.htm" id="mechanismComWin" width="600" height="500" title="����Ȩ�޷���ҳ��" modal="true"></odin:window>	
<odin:window src="/blank.htm" id="personComWin" width="900" height="500" title="��Ա�鿴Ȩ�޷���ҳ��" modal="true"></odin:window>	
<odin:window src="/blank.htm" id="setInfoGroupWin" width="900" height="500" title="��Ϣ�������ô���" modal="true"></odin:window>
<odin:window src="/blank.htm" id="InfoComWin" width="600" height="500" title="��Ϣ����Ȩ�޷���ҳ��" modal="true"></odin:window>


