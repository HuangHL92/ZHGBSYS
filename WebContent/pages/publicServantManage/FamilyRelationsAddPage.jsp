<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<script type="text/javascript">
function deleteRow(){ 
	var sm = Ext.getCmp("FamilyRelationsGrid").getSelectionModel();
	if(!sm.hasSelection()){
		Ext.Msg.alert("ϵͳ��ʾ","��ѡ��һ�����ݣ�");
		return;
	}
	Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ�ȷ��ɾ����",function(id) { 
		if("yes"==id){
			radow.doEvent('deleteRow',sm.lastActive+'');
		}else{
			return;
		}		
	});	
}
</script>

<odin:groupBox property="s3" title="��ͥ��Ҫ��Ա����Ҫ����ϵ">
<%--<odin:textEdit property="a0000" label="��Աid" ></odin:textEdit>--%>
<odin:hidden property="a3600" title="����id" ></odin:hidden>		
<table cellspacing="2" width="98%" align="center">
	<tr>
		<odin:select property="a3604a" label="��ν" codeType="GB4761"></odin:select>
		<odin:textEdit property="a3601" label="����" ></odin:textEdit>		
	</tr>
	<tr>
		<odin:dateEdit property="a3607" label="��������" format="Ymd"></odin:dateEdit>
		<odin:select property="a3627" label="������ò" codeType="GB4762"></odin:select>		
	</tr>
	<tr>
		<odin:textEdit property="a3611" label="������λ��ְ��" ></odin:textEdit>	
		
	</tr>
	<tr>
		<td colspan="8">
			<odin:toolBar property="toolBar7">
				<odin:fill></odin:fill>
				<odin:buttonForToolBar text="����" id="save" icon="images/save.gif" cls="x-btn-text-icon" ></odin:buttonForToolBar>
				<odin:buttonForToolBar text="����" id="FamilyRelationsAddBtn" icon="images/add.gif"></odin:buttonForToolBar>
				<odin:buttonForToolBar text="ɾ��" isLast="true" icon="images/back.gif" id="delete" handler="deleteRow"></odin:buttonForToolBar>
			</odin:toolBar>
			<odin:grid property="FamilyRelationsGrid" topBarId="toolBar7" sm="row"  forceNoScroll="true" isFirstLoadData="false" url="/"
			 height="260" title="��ͥ��Ҫ��Ա����Ҫ����ϵ">
				<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="a3600" />
			  		<odin:gridDataCol name="a3604a" />
			  		<odin:gridDataCol name="a3601" />
			  		<odin:gridDataCol name="a3607" />
			  		<odin:gridDataCol name="a3627" />
			   		<odin:gridDataCol name="a3611" isLast="true"/>			   		
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
				  <odin:gridRowNumColumn />
				  <odin:gridEditColumn header="id" dataIndex="a3600" editor="text" hidden="true"/>
				  <odin:gridEditColumn header="��ν" dataIndex="a3604a" codeType="GB4761" editor="select"/>
				  <odin:gridEditColumn header="����" dataIndex="a3601" editor="text"/>
				  <odin:gridEditColumn header="��������" dataIndex="a3607" editor="text"/>
				  <odin:gridEditColumn header="������ò" dataIndex="a3627" codeType="GB4762" editor="select"/>
				  <odin:gridEditColumn header="������λ��ְ��" dataIndex="a3611" editor="text" isLast="true"/>
				</odin:gridColumnModel>
			</odin:grid>
		</td>
	</tr>
</table>
</odin:groupBox>