<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<style>
#addSceneContent{
border:solid 0px !important;
}
</style>
<div id="addSceneContent">
 <div id="tooldiv"></div>
<table style="border:solid 0px !important;width: 560px;">
	<tr>
		<odin:select2 property="tableCode" label="��Ϣ��" ></odin:select2>
		<odin:select2 property="colCode" label="��Ϣ��" ></odin:select2>
		</tr>
	<tr>
		<td colspan="6">
			<odin:editgrid property="vsl006Grid" width="100%"  url="/">
			<odin:gridJsonDataModel   root="data" >
					<odin:gridDataCol name="checked"  />
					<odin:gridDataCol name="vsl006"/>
					<odin:gridDataCol name="vsl006_name"  isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridColumn header="selectall" gridName="vsl006Grid" align="center" 
						editor="checkbox" edited="true" dataIndex="checked" />
					<odin:gridEditColumn header="�����" width="100" dataIndex="vsl006"   edited="false" editor="select" align="center"  hidden="true"/>
					<odin:gridEditColumn header="�����" width="100" dataIndex="vsl006_name"   edited="false" editor="select" align="center"  hidden="false"   isLast="true" />
				</odin:gridColumnModel>
			</odin:editgrid>
		</td>
	</tr>
</table>
<%-- <table style="border:solid 0px !important">
	<tr>
		<td width="40%" >
			<table >
				<tr>
			    	<odin:select2 property="tableCode" label="��Ϣ��"  required="true"></odin:select2>
				</tr>
				<tr>
					<td height="32" colspan="4"></td>
				</tr>
				<tr>
					<odin:select property="colCode" label="��Ϣ��"  required="true"></odin:select>
				</tr>
				<tr>
					<td height="90" colspan="4"></td>
				</tr>
				
			</table>	
		</td>
		<td width="60%">
			<odin:editgrid property="vsl006Grid" width="100%"  url="/">
			<odin:gridJsonDataModel   root="data" >
					<odin:gridDataCol name="checked"  />
					<odin:gridDataCol name="vsl006"/>
					<odin:gridDataCol name="vsl006_name"  isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridColumn header="selectall" gridName="vsl006Grid" align="center" 
						editor="checkbox" edited="true" dataIndex="checked" />
					<odin:gridEditColumn header="�����" width="100" dataIndex="vsl006"   edited="false" editor="select" align="center"  hidden="true"/>
					<odin:gridEditColumn header="�����" width="100" dataIndex="vsl006_name"   edited="false" editor="select" align="center"  hidden="false"   isLast="true" />
				</odin:gridColumnModel>
			</odin:editgrid>
		</td>
	</tr>
</table> --%>

</div>
<odin:toolBar property="btnToolBar">
	<odin:fill/>
	<odin:buttonForToolBar id="btnSave"  text="����" icon="images/save.gif" isLast="true" />
</odin:toolBar>
<odin:panel contentEl="addSceneContent" property="addRolePanel" topBarId="btnToolBar"></odin:panel>

<script type="text/javascript">
Ext.onReady(function() {
	//ҳ�����
	 Ext.getCmp('btnToolBar').setWidth(document.body.clientWidth);
	 Ext.getCmp('addRolePanel').setWidth(document.body.clientWidth);
	 document.getElementById("addSceneContent").style.width = document.body.clientWidth-4; 
});
</script>