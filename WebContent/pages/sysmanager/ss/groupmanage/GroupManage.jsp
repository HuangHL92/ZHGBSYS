<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@page import="com.insigma.siis.local.pagemodel.sysmanager.ss.groupmanage.GroupManagePageModel"%>

<script type="text/javascript">
Ext.onReady(function() {
	  var man = document.all.manager.value;
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
                  dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysmanager.ss.groupmanage.GroupManage&eventNames=orgTreeJsonData'
            })
        });
      }else{
        var tree = new Tree.TreePanel( {
            el : 'tree-div',//Ŀ��div����
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
            id : document.getElementById('ereaid').value,//Ĭ�ϵ�nodeֵ��?node=-100
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
			<odin:groupBox title="������" property="ggBox">
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
						<odin:textIconEdit property="searchBtn" label="�û������Ʋ�ѯ"/>
						<td width="120"></td>
						<td><odin:button property="searchAllBtn" text="��ѯ������"></odin:button></td>
					</tr>
					<tr>
						<td height="5" colspan="4"></td>
					</tr>
				</table>
			</odin:groupBox>
			<odin:simplePanel title="�û�����Ϣ" width="600px" bodyWidth="400px">
				<table  height="300px" width="550px" >
					<tr>
						<odin:hidden property="id"/>
					</tr>
					<tr>
						<odin:textEdit property="name" label="�û�������"  readonly="true"></odin:textEdit>
						<odin:textEdit property="shortname" label="���" readonly="true"></odin:textEdit>
					</tr>
					<tr>
						<odin:textEdit property="ownerId" label="������" readonly="true"></odin:textEdit>
						<odin:textEdit property="parentid" label="������" readonly="true"></odin:textEdit>
					</tr>
					<tr>
						<odin:textEdit property="status" label="״̬" readonly="true"></odin:textEdit>
						<odin:textEdit property="desc" label="�û�������" readonly="true"></odin:textEdit>
					</tr>
					<tr>
						<odin:textEdit property="org" label="ϵͳ��������" readonly="true"></odin:textEdit>
						<odin:textEdit property="principal" label="����������" readonly="true"></odin:textEdit>
					</tr>
					<tr>
						<odin:textEdit property="linkman" label="��ϵ��" readonly="true"></odin:textEdit>
						<odin:textEdit property="address" label="��ַ" readonly="true"></odin:textEdit>
					</tr>
					<tr>
						<odin:textEdit property="tel" label="�绰" readonly="true"></odin:textEdit>
						<odin:textEdit property="districtcode" label="��������" readonly="true"></odin:textEdit>
					</tr>
					<tr>
						<odin:textEdit property="chargedept" label="���ܲ���" readonly="true"></odin:textEdit>
						<odin:textEdit property="createdate" label="����ʱ��" readonly="true"></odin:textEdit>
					</tr>
					<tr>
						<odin:textEdit property="otherinfo" label="������Ϣ" readonly="true"></odin:textEdit>
						<odin:select property="rate" label="����" codeType="RATE"></odin:select>
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
	<odin:textForToolBar text="<h3>�û������</h3>" />
	<odin:fill />
	<odin:buttonForToolBar text="�����û���" id="saveBtn" tooltip="�ڵ�ǰ�û����´��������û���"></odin:buttonForToolBar>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar id="editBtn"  text="�޸��û���"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar id="revokeBtn" text="ע���û���"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar id="reuseBtn" text="�����û���"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar id="deleteBtn" text="ɾ���û���" isLast="true" icon="images/qinkong.gif" cls="x-btn-text-icon" />
</odin:toolBar>
<odin:panel contentEl="groupTreeContent" property="groupTreePanel" topBarId="btnToolBar"></odin:panel>
	
	
	
<odin:window src="/blank.htm" id="addWin" width="500" height="300" title="�û������Ӵ���"></odin:window>
<odin:window src="/blank.htm" id="uptWin" width="515" height="320" title="�û����޸Ĵ���"></odin:window>
<odin:window src="/blank.htm" id="userWin" width="520" height="518" title="�û�������"></odin:window>
<odin:window src="/blank.htm" id="groupWin" width="670" height="407" title="�û��鴰��"></odin:window>