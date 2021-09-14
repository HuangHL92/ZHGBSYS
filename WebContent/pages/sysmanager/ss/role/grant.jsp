<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<odin:toolBar property="btnToolBar">
	<odin:textForToolBar text=""/>
	<odin:fill/>
	<odin:buttonForToolBar text="��ѯ" id="query"/>
	<odin:buttonForToolBar  text="��Ȩ"  id="save" />
	<odin:buttonForToolBar text="�����Ȩ" id="remove" isLast="true"/>
</odin:toolBar>
<div id="panel_content">
<table border="0" id="myform" width="100%"  cellpadding="0" cellspacing="0">
	<tr>
		<td colspan="6" height="17"></td>
	</tr>
   <tr>
   		<tags:BasicAreaQuery property="gsearch"   selectEvent="groupEvent" way="1" />
   		<!--<odin:hidden property="gsearch"  /> -->
	    <odin:textEdit property="loginname" label="��¼��" /> 
		<odin:textEdit property="name" label="����"/>
   </tr>
   <tr>
		<td colspan="6" height="15"></td>
	</tr>	 
 </table>
</div>
<odin:panel contentEl="panel_content" property="mypanel" topBarId="btnToolBar" ></odin:panel>
<odin:tab id="tab"  height="450" tabchange="grantTabChange">
	<odin:tabModel>
		<odin:tabItem title="�û���Ȩ"  id="tab1"></odin:tabItem>
		<odin:tabItem title="�û�����Ȩ" id="tab2" isLast="true"></odin:tabItem>
	</odin:tabModel>
	<odin:tabCont itemIndex="tab1" className="tab">
			<odin:editgrid property="usergrid" bbarId="pageToolBar"
				isFirstLoadData="true" url="/" title=""
				width="770" height="385" title="" pageSize="50">
				<odin:gridJsonDataModel id="gridid" root="data"
					totalProperty="totalCount">
					<odin:gridDataCol name="usercheck" />
					<odin:gridDataCol name="id" />
					<odin:gridDataCol name="loginname" />
					<odin:gridDataCol name="status" />
					<odin:gridDataCol name="name" isLast="true" />
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridColumn header="selectall" gridName="usergrid" align="center" width="70"
						editor="checkbox" edited="true" dataIndex="usercheck" />
					<odin:gridEditColumn header="�û�id" width="30" dataIndex="id"
						edited="false" editor="text" hidden="true" />
					<odin:gridEditColumn header="��¼��" align="center" width="250"
						dataIndex="loginname" editor="text" edited="false" />
					<odin:gridEditColumn header="����" dataIndex="name" align="center"
						edited="false" editor="text" width="340" />
					<odin:gridEditColumn header="״̬" align="center" editor="text"
						edited="false" renderer="radow.commUserfulForGrid" width="100"
						dataIndex="status" isLast="true" />
				</odin:gridColumnModel>
			</odin:editgrid>
	</odin:tabCont>

	<odin:tabCont itemIndex="tab2">
			<odin:editgrid property="groupgrid" bbarId="pageToolBar"
				isFirstLoadData="true" url="/" pageSize="50"
				title="" width="770" height="360">
				<odin:gridJsonDataModel id="id" root="data"
					totalProperty="totalCount">
					<odin:gridDataCol name="groupcheck" />
					<odin:gridDataCol name="id" />
					<odin:gridDataCol name="name" />
					<odin:gridDataCol name="shortname" />
					<odin:gridDataCol name="status" isLast="true" />
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn />
					<odin:gridColumn header="selectall" gridName="groupgrid"
						align="center" width="70" editor="checkbox" dataIndex="groupcheck"
						edited="true" />
					<odin:gridEditColumn header="�û���ID" hidden="true" align="center" edited="false"
						dataIndex="id" editor="text" edited="false" />
					<odin:gridEditColumn header="�û�����" dataIndex="name" align="center"  editor="text"
						edited="false" width="200" />
					<odin:gridEditColumn header="���" width="300" align="center" dataIndex="shortname"
						edited="false" editor="text" />
					<odin:gridEditColumn header="״̬"
						renderer="radow.commUserfulForGrid" align="center"  edited="false" align="center"
						width="100" dataIndex="status" selectData="['1','��Ч'],['0','��Ч']"
						editor="select" isLast="true" />
				</odin:gridColumnModel>
			</odin:editgrid>
	</odin:tabCont>
</odin:tab>
<odin:window src="/blank.htm" id="win1" width="560" height="392" title="��Ȩ��Ϣ����">
</odin:window>
<odin:window src="/blank.htm" id="win2" width="560" height="392" title="�����Ȩ��Ϣ����">
</odin:window>

<script>
function grantTabChange(tabObj,item){
	if(item.getId()=='tab2'){
		odin.ext.getCmp('groupgrid').view.refresh(true);
	}
}
</script>