<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%String ctxPath = request.getContextPath(); 
%>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<style>
body {
	background-color: rgb(214,227,243);
}
.dasda td{width: 120px;padding-bottom: 5px;}
</style>

<table class="dasda" style="width:100%;">
	<%-- <tr>
		<odin:textEdit property="startdate" label="��ʼ����" ></odin:textEdit>
		<odin:textEdit property="enddate" label="��������" ></odin:textEdit>
		<odin:textEdit property="entrycontent" label="����"></odin:textEdit>
	</tr> --%>
	<tr>
		<td height="20px"></td>
	</tr>
</table>

	<%-- <odin:grid property="TrainInfoGrid" sm="row" isFirstLoadData="false" url="/"
				height="550" autoFill="false"  > --%>
				<odin:grid property="TrainInfoGrid"  autoFill="false" height="550" bbarId="pageToolBar" pageSize="20" isFirstLoadData="false" url="/" grouping="true" groupCol="updated" >
					<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
						<odin:gridDataCol name="a3600" />
						<odin:gridDataCol name="a3604a" />
						<odin:gridDataCol name="a3601" />
						<odin:gridDataCol name="a3607" />
						<odin:gridDataCol name="a3627" />
						<odin:gridDataCol name="a3611"/>
						<odin:gridDataCol name="a0184gz"/>
						<odin:gridDataCol name="a0111gz"/>
						<odin:gridDataCol name="a0115gz"/>
						<odin:gridDataCol name="a0111gzb"/>
						<odin:gridDataCol name="a3621"/>
						<odin:gridDataCol name="a3631"/>
						<odin:gridDataCol name="updated"></odin:gridDataCol>
						<odin:gridDataCol name="a3641" isLast="true"/>	
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
					  <odin:gridRowNumColumn />
					  <odin:gridEditColumn2 header="����" dataIndex="a3600" editor="text" edited="false" hidden="true"/>
					  <odin:gridEditColumn2 header="��ν" align="center" dataIndex="a3604a" editor="text" edited="false" width="100"/>
					  <odin:gridEditColumn2 header="����" align="center" dataIndex="a3601" editor="text" edited="false" width="100"/>
					  <odin:gridEditColumn2 header="��������" align="center" dataIndex="a3607" editor="text" edited="false" width="100"/>
					  <odin:gridEditColumn2 header="������ò" align="center" dataIndex="a3627" editor="text" edited="false" width="100"/>
					  <odin:gridEditColumn2 header="������λ��ְ��" align="center" dataIndex="a3611" editor="text" edited="false" width="250"/>
					  <odin:gridEditColumn2 header="���֤��" align="center" dataIndex="a0184gz" editor="text" edited="false" width="200"/>
					  <odin:gridEditColumn2 header="����" align="center" dataIndex="a0111gz" editor="select" edited="false" width="100" codeType="ZB01"/>
					  <odin:gridEditColumn2 header="��ס��" align="center" dataIndex="a0115gz" editor="select" edited="false" width="100" codeType="ZB01"/>
					  <odin:gridEditColumn2 header="���������" align="center" dataIndex="a0111gzb" editor="select" edited="false" width="100"  codeType="GB2659"/>
					  <odin:gridEditColumn2 header="����" align="center" dataIndex="a3621" editor="select" edited="false" width="100" codeType="GB3304"/>
					  <odin:gridEditColumn2 header="��Ա���" align="center" dataIndex="a3631" editor="select" edited="false" width="100" codeType="ZB06"/>
					  <odin:gridEditColumn2 header="��Ա��״" align="center" dataIndex="a3641" editor="select" edited="false" width="150" codeType="ZB56"/>
					  <odin:gridEditColumn2 editor="select" dataIndex="updated" hidden="true" edited="false" codeType="A36_GROUP" header="����" width="40" isLast="true"  />
					</odin:gridColumnModel>
			</odin:grid>
<odin:hidden property="a0000" title="��Ա����"/>
<odin:hidden property="a3600" title="����id" ></odin:hidden>	

<script type="text/javascript">

Ext.onReady(function(){
	document.getElementById("a0000").value = window.parent.document.getElementById("a0000").value;
});
</script>
