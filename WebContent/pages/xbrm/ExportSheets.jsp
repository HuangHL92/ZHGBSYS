<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@include file="/comOpenWinInit2.jsp" %>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<style>
</style>
<script type="text/javascript">
function getCheckList2(index){
	
}
function getCheckList(gridId,fieldName,obj){
	
}
</script>
<odin:editgrid property="gridcq" topBarId="topbar"  width="230"  pageSize="9999"  clicksToEdit="false"
			autoFill="false" >
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="personcheck" />	
		<odin:gridDataCol name="a0000" />
		<odin:gridDataCol name="js0100" />
		<odin:gridDataCol name="a0101" />
		<odin:gridDataCol name="dc001" />
		<odin:gridDataCol name="a0104" />
		<odin:gridDataCol name="a0163" />
		<odin:gridDataCol name="a0247" />
		<odin:gridDataCol name="a0192a" isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn></odin:gridRowNumColumn>
		<odin:gridEditColumn2 locked="true" header="selectall" width="40"
					editor="checkbox" dataIndex="js0100" edited="true"
					hideable="false" gridName="gridcq" 
					checkBoxClick="getCheckList2" checkBoxSelectAllClick="getCheckList" />
		<%-- <odin:gridColumn dataIndex="a019998" header="Ԥ��" width="30" align="center" /> --%>
		<odin:gridEditColumn dataIndex="a0101" header="����" width="45" edited="false" editor="text" align="center" />
		<odin:gridEditColumn2 dataIndex="dc001" header="�������" width="110" edited="true" editor="select"  align="center" />
		<%-- <odin:gridEditColumn2 dataIndex="a0247" header="��������" width="80"  align="center" editor="select" edited="false"  codeType="ZB122"/> --%>
		<odin:gridEditColumn2 dataIndex="a0163" header="��Ա״̬" hidden="true" width="45" align="center" editor="select" edited="false" isLast="true" codeType="ZB126" />
	</odin:gridColumnModel>
	<odin:gridJsonData>
		{
	        data:[]
	    }
	</odin:gridJsonData>
</odin:editgrid>
<odin:hidden property="rbId" title="����id"/>
<odin:hidden property="a0000" title="��Աid"/>
<odin:hidden property="js0100" title="������Աid"/>

<script type="text/javascript">
Ext.onReady(function() {
	//var viewSize = Ext.getBody().getViewSize();
	
	document.getElementById('rbId').value = parentParam.rb_id;		
});

</script>

