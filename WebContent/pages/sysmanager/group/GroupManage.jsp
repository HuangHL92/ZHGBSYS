<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@page import="com.insigma.siis.local.pagemodel.sysmanager.group.GroupManagePageModel"%>

<script type="text/javascript">
Ext.onReady(function() {
	  var man = document.getElementById('manager').value;
      var Tree = Ext.tree;
      if(man == 'true'){
	       var tree = new Tree.TreePanel( {
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
                  dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysmanager.group.GroupManage&eventNames=orgTreeJsonData'
            })
        });
      }else{
        var tree = new Tree.TreePanel( {
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
                  dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysmanager.group.GroupManage&eventNames=orgTreeJsonData'
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
	var loginname=rs.get('loginname');
	var url="<a   href=\"javascript:radow.doEvent('dogridunlock','"+loginname+"');\">����</a>";
	if(value=='1'){
		return '����';
	}else if(value=='0'){
		return '����'+url;
	}else{
		return '״̬�쳣';
	}
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
	GroupManagePageModel gm=new GroupManagePageModel();
	String ereaname = (String)(gm.areaInfo.get("areaname"));
	String ereaid = (String)(gm.areaInfo.get("areaid"));
	String manager = (String)(gm.areaInfo.get("manager"));
%>

<div id="groupTreeContent" style="height: 100%;overflow:auto">
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
					<odin:textEdit property="optionGroup" label="��ǰ������֯" width="110" disabled="true"/>
				</tr>
				<tr>
					<td height="5" colspan="4"></td>
				</tr>
			</table>
			<odin:groupBox title="������" property="ggBox">
			<table width="100%">
				<tr>
					<odin:textIconEdit property="searchGroupBtn"  label="��֯��"/>
					<odin:textIconEdit property="searchUserBtn" label="�û���¼��"/>
				</tr>
				<tr>
					<td height="5" colspan="4"></td>
				</tr>
			</table>
			</odin:groupBox>
		<odin:editgrid property="memberGrid" title="��֯�û�����" autoFill="false" width="590" height="320" bbarId="pageToolBar" pageSize="20" isFirstLoadData="false" url="/">
			<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="logchecked"/>
					<odin:gridDataCol name="id" />
					<odin:gridDataCol name="loginname"/>
					<odin:gridDataCol name="name"/>
					<odin:gridDataCol name="desc" />
					<odin:gridDataCol name="status" />
					<odin:gridDataCol name="isleader" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridColumn header="" width="25" editor="checkbox" dataIndex="logchecked" edited="true"/>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridColumn dataIndex="loginname" width="110" header="�û���¼��" align="center"/>
					<odin:gridColumn dataIndex="name" width="100" header="����" align="center"/>
					<odin:gridColumn dataIndex="desc" width="130" header="����" align="center"/>
					<odin:gridColumn dataIndex="status" width="100" header="״̬" align="center"  renderer="statusVal"/>
					<odin:gridColumn dataIndex="isleader" header="����Ա" width="80" renderer="radow.commUserfulForGrid"  align="center"/>
					<odin:gridColumn dataIndex="loginname" header="�޸�" width="50" renderer="radow.commGrantForGrid" align="center"/>
					<odin:gridColumn dataIndex="id" header="��������" width="100" renderer="reset" align="center"/>
					<odin:gridColumn dataIndex="id" header="ɾ��" width="50" renderer="radow.commGridColDelete" align="center"/>
					<odin:gridColumn dataIndex="id" header="������Ȩ" width="100" renderer="dataGrantRender" align="center" isLast="true"/>
				</odin:gridColumnModel>
			</odin:editgrid>
		</td>
	</tr>
</table>
</div>

<odin:toolBar property="btnToolBar">
	<odin:textForToolBar text="<h3>��֯����</h3>" />
	<odin:fill />
	/*<odin:buttonForToolBar text="��Ϊͳ����" id="setArea" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="ȡ��ͳ����" id="cancelArea" />
	<odin:separator></odin:separator>*/
	<odin:buttonForToolBar text="���ӳ�Ա" id="addMemberBtn" tooltip="�����Ѵ��ڵĻ����Ǹ���֯��Ա���û�"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="������Ա" id="createUserBtn" tooltip="�����µ��û���ֱ�Ӽ������֯"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="��Ϊ����Ա" id="setLeaderBtn" tooltip="��ѡ�е���ͨ��Ա����Ϊ�������Ա"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="ȡ������Ա" id="cancelLeaderBtn" tooltip="��ѡ�еĹ���Ա����Ϊ��ͨ��Ա"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="�Ƴ���Ա" id="removeUserBtn" isLast="true" tooltip="��ѡ�г�Ա����֯���Ƴ�,�����Աû�м���������֯,��Ӧ���ȰѸó�Ա���뵽������֯������Ƴ�"/>
</odin:toolBar>
<odin:panel contentEl="groupTreeContent" property="groupTreePanel" topBarId="btnToolBar"></odin:panel>
	
<odin:window src="/blank.htm" id="createUserWin" width="470" height="400" title="������Ա�û�����"></odin:window>
<odin:window src="/blank.htm" id="userShowWin" width="490" height="475" title="�û���ѯ����"></odin:window>
<odin:window src="/blank.htm" id="userWin" width="450" height="450" title="��֯��Ա���Ӵ���"></odin:window>
<odin:window src="/blank.htm" id="updateUserWin" width="285" height="240" title="��Ա��Ϣ�޸Ĵ���"></odin:window>
<odin:window src="/blank.htm" id="groupWin" width="490" height="415" title="��֯��Ϣ����"></odin:window>
