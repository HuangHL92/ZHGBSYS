<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.WarnWindowSRPageModel"%>
<%@include file="/comOpenWinInit.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<odin:toolBar property="btnToolBar" applyTo="btnToolBarDiv">
	<odin:fill />
	<odin:buttonForToolBar text="�����б�" icon="images/save.gif" id="dataSave" isLast = "true"/>
</odin:toolBar>

<div>
<div id="btnToolBarDiv" style="width:682px;"></div>
</div>
<odin:tab id="tab" width="682px" >
<odin:tabModel>
<odin:tabItem title="��������" isLast="true" id="tab4"></odin:tabItem>
</odin:tabModel>
<odin:tabCont itemIndex="tab4">
<br>
<% int birthdayCount = WarnWindowSRPageModel.getBirthDaycount(); %>
<label id="birth" style="font-size: 12;font-weight: bold;color: rgb(66,139,202);" >&nbsp;&nbsp;������Ա����<%=birthdayCount%>���ڹ�����</label>
<br><br>
<odin:editgrid property="persongrid1"
				bbarId="pageToolBar" isFirstLoadData="true" url="/" pageSize="20" height="350">
				<odin:gridJsonDataModel root="data" totalProperty="totalCount">
				
				    <odin:gridDataCol name="a0000" />
					
					<odin:gridDataCol name="a0101" />
					
					<odin:gridDataCol name="a0104" />
					
					<odin:gridDataCol name="a0107" />
					
					<odin:gridDataCol name="a0192a" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					
					<odin:gridEditColumn header="id" edited="false" width="200" dataIndex="a0000" editor="text" hidden="true"/>
					
					<odin:gridEditColumn header="����" edited="false" width="80" dataIndex="a0101" editor="text" align="center" />
					
					<odin:gridEditColumn header="�Ա�" edited="false" width="80" dataIndex="a0104" editor="text" renderer="GB2261" align="center" />
					
					<odin:gridEditColumn header="��������" edited="false" width="80" dataIndex="a0107" editor="text" align="center" />
					
					<odin:gridEditColumn header="������λ��ְ��" edited="false" width="410" dataIndex="a0192a" editor="text" isLast="true"/>
				</odin:gridColumnModel>
			</odin:editgrid>
</odin:tabCont>
</odin:tab>


<script type="text/javascript">

//ҳ���Ҽ�����
document.oncontextmenu=rightMouse;
function rightMouse() {
	return false;
}

function GB2261(value,params,record,rowIndex,colIndex,ds){
	if(value == '1'){
		value = '��';
	}
	if(value == '2'){
		value = 'Ů';
	}
	return value;
}
</script>
