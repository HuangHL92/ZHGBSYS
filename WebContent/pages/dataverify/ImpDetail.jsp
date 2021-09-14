<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@include file="/comOpenWinInit.jsp" %>
<odin:toolBar property="btnToolBar">
	<odin:fill></odin:fill>
	<odin:buttonForToolBar text="����" id="impBtn" />
	<odin:separator></odin:separator>
	<%-- <odin:buttonForToolBar text="�����������ݰ�" id="empBtn"  /> 
	<odin:separator></odin:separator> --%>
	<odin:buttonForToolBar text="���" id="rejectBtn" isLast="true"/>
</odin:toolBar>
<div id="panel_content">
<odin:hidden property="imprecordid" />
</div>

			<odin:panel contentEl="panel_content" property="mypanel" topBarId="btnToolBar" ></odin:panel>
			<odin:editgrid property="MGrid" title="��������" autoFill="true" bbarId="pageToolBar" pageSize="100" isFirstLoadData="false" url="/">
			<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
					
					<odin:gridDataCol name="mc" />
					<odin:gridDataCol name="bm" />
					<odin:gridDataCol name="ved002" />
					<odin:gridDataCol name="vru004" />
					
					<odin:gridDataCol name="vru005" isLast="true"/>
					
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					
					<odin:gridEditColumn header="����" width="30" dataIndex="mc"
						edited="false" editor="text" />
					<odin:gridEditColumn header="����" align="center" width="50"
						dataIndex="bm" editor="text" edited="false" />
						
					<odin:gridEditColumn header="��Ϣ��" dataIndex="vru004" align="center"
						editor="select" codeType="VSL003" edited="false" width="50" />
					<odin:gridEditColumn  align="center" width="50" header="��Ϣ��"  
						editor="select" codeType="VSL004" edited="false" dataIndex="vru005"  />
						
					<odin:gridEditColumn header="������Ϣ" dataIndex="ved002" align="center"
						edited="false" editor="text" width="60" isLast="true"  />
					
				</odin:gridColumnModel>
			</odin:editgrid>

			
<odin:window src="/blank.htm" id="win1" width="700" height="392" title="����">
</odin:window>
<odin:window src="/blank.htm" id="simpleExpWin" width="600" height="500" title="����">
</odin:window>
<script>

function impTest(){
	var win = odin.ext.getCmp('simpleExpWin');
	win.setTitle('���봰��');
    win.setSize(900,700); //���  �߶�
	odin.showWindowWithSrc('simpleExpWin',contextPath+"/pages/dataverify/DataVerify.jsp");
}
/**
* ������Ȩ��render����
*/
function detailRender(value, params, rs, rowIndex, colIndex, ds){
	return "<a href=\"javascript:openDetailWin('"+value+"')\">�޸�</a>";
}
function openDetailWin(imprecordid){
/**	document.getElementById("radow_parent_data").value = imprecordid;
	doOpenPupWin("/radowAction.do?method=doEvent&pageModel=pages.sysmanager.datagroup.UserDataGrant&imprecordid="+imprecordid,
		"��������",270,446,null);*/
}
function grantTabChange(tabObj,item){
	if(item.getId()=='tab2'){
		odin.ext.getCmp('groupgrid').view.refresh(true);
	}
}
</script>