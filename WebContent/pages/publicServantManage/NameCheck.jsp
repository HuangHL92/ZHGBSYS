<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@include file="/comOpenWinInit.jsp" %>
<%

String namecheck = request.getParameter("namecheck");
request.setAttribute("namecheck",namecheck);
%>
<%@page import="java.net.URLEncoder"%>
<style>
.inline{
display: inline;
margin-left: 20px;
}
</style>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript">
function getselectGrid(){
	var sm = Ext.getCmp("persongrid").getSelectionModel();
	if(!sm.hasSelection()){
		Ext.Msg.alert("ϵͳ��ʾ","�༭��Ҫѡ����Ա��");
		return;
	}
	var record = sm.getSelected();
	//alert(record.data.a0000);
	radow.doEvent('loadSelectedPerson',record.data.a0000);//
	realParent.Ext.getCmp('nameCheck').hide();
}
</script>


<input type="hidden" name="namecheck" id="namecheck" value="${namecheck}"/>

<table cellspacing="2" width="98%" align="left">
	
	<tr>
		<td colspan="8">
			
			<odin:editgrid property="persongrid" 
					 isFirstLoadData="false" url="/" height="300"  topBarId="" pageSize="500">
					<odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
						<odin:gridDataCol name="a0101" />
						<odin:gridDataCol name="a0104" />
						<odin:gridDataCol name="a0107" />
						<odin:gridDataCol name="a0192a"/>
						<odin:gridDataCol name="a0000" isLast="true"/>
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
						<odin:gridRowNumColumn></odin:gridRowNumColumn>
						<odin:gridEditColumn2 header="����" edited="false" width="60" align="center"  dataIndex="a0101" editor="text" />
						<odin:gridEditColumn2 header="�Ա�" edited="false" width="40" align="center"  dataIndex="a0104" codeType="GB2261" editor="select" />
						<odin:gridEditColumn2 header="��������" edited="false" width="100" align="center"  dataIndex="a0107" editor="text"/>
						<odin:gridEditColumn2 header="������λ��ְ��" edited="false" width="100" align="center"  dataIndex="a0192a" editor="text"/>
						<odin:gridEditColumn2 header="id" edited="false" width="200" dataIndex="a0000" editor="text" hidden="true"  isLast="true"/>
					</odin:gridColumnModel>
				</odin:editgrid>
		</td>
		
	</tr>
	<tr>
		<td align="right">
			<div id="btn"></div>
			<script type="text/javascript">
				Ext.onReady(function(){
					new Ext.Button({
						text: '�༭',
						id:'edit',
						cls :'inline',
						handler:function(){getselectGrid();},
						renderTo:"btn"
						});
					new Ext.Button({
						text: '����',
						id:'cancel',
						cls :'inline',
						handler:function(){realParent.Ext.getCmp('nameCheck').hide();},
						renderTo:"btn"
						});
				});
			</script>
		</td>
		
	</tr>
</table>
