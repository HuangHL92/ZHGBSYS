<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<style>
#addSceneContent{
border:solid 0px !important;
}
</style>
<div id="addSceneContent">
<div id="tooldiv"></div>
<table style="border:solid 0px !important">
	<tr>
		<odin:select2 property="a0163" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�Ƿ���ְ"  data="[0,'ȫ��'],['1','��ְ']" value="1" />		
		<odin:select2 property="vsc001" label="У�鷽��"  required="true"></odin:select2>
		
	</tr>
	<tr>
		<td colspan="6">
			<odin:editgrid property="ruleGrid" url="/">
			<odin:gridJsonDataModel   root="data" >
					<odin:gridDataCol name="checked"  />
					<odin:gridDataCol name="vru001"/>
					<odin:gridDataCol name="vru002"/>
					<odin:gridDataCol name="vru003"/>
					<odin:gridDataCol name="vru003_name"  isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridColumn header="selectall" gridName="ruleGrid" align="center" 
						editor="checkbox" edited="true" dataIndex="checked" />
					<odin:gridColumn dataIndex="vru001" header="�������" hidden="true"></odin:gridColumn>
					<odin:gridEditColumn header="У�����" width="100" dataIndex="vru002"  edited="false" editor="text" align="center"/>
					<odin:gridEditColumn header="У������"  dataIndex="vru003"  edited="false" editor="text" align="center" hidden="true"/>
					<odin:gridEditColumn header="У����������"  dataIndex="vru003_name"  edited="false" editor="text" align="center" isLast="true"/>
				</odin:gridColumnModel>
			</odin:editgrid>
		</td>
	</tr>
</table>

</div>
<odin:toolBar property="btnToolBar" applyTo="tooldiv">
	<odin:fill/>
	<odin:buttonForToolBar id="btnSave"  text="��ʼУ��" icon="images/icon/right.gif" isLast="true" />
</odin:toolBar>

<script type="text/javascript">
Ext.onReady(function() {
	//ҳ�����
	 Ext.getCmp('btnToolBar').setWidth(document.body.clientWidth);
	 document.getElementById("addSceneContent").style.width = document.body.clientWidth-4;
	 Ext.getCmp('ruleGrid').setWidth(560);
});
function hide(){
	document.getElementById("ext-gen8").style.visibility="hidden";
	document.getElementById("a0163SpanId").style.visibility="hidden";
}
</script>