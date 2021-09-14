<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@include file="/comOpenWinInit.jsp" %>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="js/lengthValidator.js"></script>
<style>
#grid1 {
	width: 316px !important;
}
</style>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/qdstyle.css" />
<script type="text/javascript">

</script>
<odin:hidden property="id" title="id"/>
<odin:hidden property="a0000" title="��Աid"/>
<odin:hidden property="ids" title="��Ա��id"/>
<odin:window src="/blank.htm" id="Information" width="300" height="250"
	title="�ල��Ϣҳ��" modal="true"></odin:window>

<table style="width:970;height:350">
    
	<tr>
		<td >		
<odin:toolBar property="ToolBar">
    <odin:fill />
    <odin:buttonForToolBar text="ˢ��" id="refresh" icon="images/icon/table.gif" handler="refresh"/>
	<odin:separator />
	<odin:buttonForToolBar text="����" id="insert" icon="image/icon021a2.gif" handler="insert"/>
	<odin:separator />
	<odin:buttonForToolBar text="�޸�" id="update" icon="image/icon021a6.gif" handler="update"/>
	<odin:separator />
	<odin:buttonForToolBar text="ɾ��" id="deleteBtn" icon="image/icon021a3.gif" handler="deleteBtn" isLast="true"/>
</odin:toolBar>											
		<odin:grid property="grid1" autoFill="false" topBarId="ToolBar" bbarId="pageToolBar" url="/">
			<odin:gridJsonDataModel id="id" root="data">
			        <odin:gridDataCol name="personcheck" />
					<odin:gridDataCol name="id" />
					<odin:gridDataCol name="a0000" />
					<odin:gridDataCol name="position" />
					<odin:gridDataCol name="informationtype" />
					<odin:gridDataCol name="matter" />
					<odin:gridDataCol name="startdate" />
					<odin:gridDataCol name="dealorg" />
					<odin:gridDataCol name="result" />
					<odin:gridDataCol name="influencetime" />
					<odin:gridDataCol name="filenumber" isLast="true" />
			</odin:gridJsonDataModel>
			<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridEditColumn2 header="selectall" width="40" editor="checkbox" dataIndex="personcheck" edited="true"
							hideable="false" gridName="persongrid"/>
					<odin:gridEditColumn header="����ID" align="center" edited="false" width="45" dataIndex="id" editor="text" hidden="true"/>
					<odin:gridEditColumn header="��ԱID" align="center" edited="false" width="50" dataIndex="a0000" editor="text" hidden="true"/>
					<odin:gridEditColumn header="����" align="center" edited="false" width="80" dataIndex="informationtype" editor="select" codeType="JDTYPE"/>
					<odin:gridEditColumn header="ʱ��" align="center" edited="false" width="80" dataIndex="startdate" editor="text" />
					<odin:gridEditColumn header="��Ҫ����" align="center" edited="false" width="255" dataIndex="matter" editor="text"  />	
					<odin:gridEditColumn header="����λ" align="center" edited="false" width="95" dataIndex="dealorg" editor="text" />
					<odin:gridEditColumn header="������" align="center" edited="false" width="120" dataIndex="result" editor="text" codeType="RESULT"/>
					<odin:gridEditColumn header="Ӱ����" align="center" edited="false" width="80" dataIndex="influencetime" editor="text" />
					<odin:gridEditColumn header="�ļ���" align="center" edited="false" width="120" dataIndex="filenumber" editor="text"/>		   
					<odin:gridEditColumn header="Ԥ��" align="center" edited="false" width="60" dataIndex="result" editor="text" isLast="true" />		   
			 </odin:gridColumnModel>
		</odin:grid>																
		</td>																									
	</tr>
</table>

<script type="text/javascript">
Ext.onReady(function() {
	//var viewSize = Ext.getBody().getViewSize();
	
	//document.getElementById('a0000').value = parentParam.a0000;	
	//alert(document.getElementById('a0000').value);
});

function refresh(){//ˢ��
	
	radow.doEvent("refresh");
}

function insert(){//����
	
	radow.doEvent("insert");
}

function update(){  //�޸�
	
	
	radow.doEvent("update");
}

function deleteBtn(){  //ɾ��
	
			
	radow.doEvent('delete');			
	
}

function open(){  
	var g_contextpath = '<%= request.getContextPath() %>';
	var id = document.getElementById('id').value;
	var a0000 = document.getElementById('a0000').value;
	
	var ids = id+"@@@"+a0000;
	
	$h.openPageModeWin('Information','pages.publicServantManage.Information','�ල��Ϣ',550,300,{rb_id:ids},g_contextpath);
}

function openinsert(){  
	var g_contextpath = '<%= request.getContextPath() %>';
	var a0000 = document.getElementById('a0000').value;
	

	$h.openPageModeWin('Information','pages.publicServantManage.Information','�ල��Ϣ',550,300,{rb_id:a0000},g_contextpath);
}
</script>

