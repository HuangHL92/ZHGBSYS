<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@include file="/comOpenWinInit.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script type="text/javascript">

//ҳ���Ҽ�����
document.oncontextmenu=rightMouse;
function rightMouse() {
	return false;
}

function receive(){
	 var gridId = "persongrid1";
	 var selections = odin.ext.getCmp(gridId).getSelectionModel().getSelections();
	 var a0000 = selections[0].data.a0000;
	 var a0101 = selections[0].data.a0101;
	 var param = a0000 + "," + a0101;
	 radow.util.openWindow('peopleReceive','pages.customquery.PeopleReceive&initParams='+param);
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

<odin:menu property="receiveMenu">
<odin:menuItem text="��Ա����" handler="receive" isLast="true"></odin:menuItem>
</odin:menu>

</script>

<odin:toolBar property="btnToolBar" applyTo="btnToolBarDiv">
	<odin:fill />
	<odin:buttonForToolBar text="�����б�" icon="images/save.gif" id="dataSave" isLast = "true"/>
</odin:toolBar>

<div>
<div id="btnToolBarDiv" style="width:682px;"></div>
</div>
<odin:tab id="tab" width="682px" >
<odin:tabModel>
<odin:tabItem title="��ת����Ա" isLast="true" id="tab5"></odin:tabItem>
</odin:tabModel>
<odin:tabCont itemIndex="tab5">
<br>
<label id="getIn" style="font-size: 12;font-weight: bold;color: rgb(66,139,202);" >&nbsp;&nbsp;�뾡�촦�����д�ת�����Ա��ѡ��ĳһ��Ա�Ҽ����ɽ���"��Ա����"��</label>
<br><br>
<odin:editgrid property="persongrid1" hasRightMenu="true" rightMenuId="receiveMenu"
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
					
					<odin:gridEditColumn header="����" edited="false" width="80" dataIndex="a0101" editor="text" align="center"/>
					
					<odin:gridEditColumn header="�Ա�" edited="false" width="80" dataIndex="a0104" editor="text" renderer="GB2261" align="center" />
					
					<odin:gridEditColumn header="��������" edited="false" width="80" dataIndex="a0107" editor="text" align="center"/>
					
					<odin:gridEditColumn header="������λ��ְ��" edited="false" width="410" dataIndex="a0192a" editor="text" isLast="true"/>
				</odin:gridColumnModel>
			</odin:editgrid>
</odin:tabCont>
</odin:tab>

<odin:window src="/blank.htm" id="peopleReceive" width="450" height="280" title="��Ա����" modal="true"></odin:window>
