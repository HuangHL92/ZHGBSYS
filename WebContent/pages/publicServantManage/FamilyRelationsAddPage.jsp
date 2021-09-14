<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<script type="text/javascript">
function deleteRow(){ 
	var sm = Ext.getCmp("FamilyRelationsGrid").getSelectionModel();
	if(!sm.hasSelection()){
		Ext.Msg.alert("系统提示","请选择一行数据！");
		return;
	}
	Ext.Msg.confirm("系统提示","是否确认删除？",function(id) { 
		if("yes"==id){
			radow.doEvent('deleteRow',sm.lastActive+'');
		}else{
			return;
		}		
	});	
}
</script>

<odin:groupBox property="s3" title="家庭主要成员及重要社会关系">
<%--<odin:textEdit property="a0000" label="人员id" ></odin:textEdit>--%>
<odin:hidden property="a3600" title="主键id" ></odin:hidden>		
<table cellspacing="2" width="98%" align="center">
	<tr>
		<odin:select property="a3604a" label="称谓" codeType="GB4761"></odin:select>
		<odin:textEdit property="a3601" label="姓名" ></odin:textEdit>		
	</tr>
	<tr>
		<odin:dateEdit property="a3607" label="出身日期" format="Ymd"></odin:dateEdit>
		<odin:select property="a3627" label="政治面貌" codeType="GB4762"></odin:select>		
	</tr>
	<tr>
		<odin:textEdit property="a3611" label="工作单位及职务" ></odin:textEdit>	
		
	</tr>
	<tr>
		<td colspan="8">
			<odin:toolBar property="toolBar7">
				<odin:fill></odin:fill>
				<odin:buttonForToolBar text="保存" id="save" icon="images/save.gif" cls="x-btn-text-icon" ></odin:buttonForToolBar>
				<odin:buttonForToolBar text="新增" id="FamilyRelationsAddBtn" icon="images/add.gif"></odin:buttonForToolBar>
				<odin:buttonForToolBar text="删除" isLast="true" icon="images/back.gif" id="delete" handler="deleteRow"></odin:buttonForToolBar>
			</odin:toolBar>
			<odin:grid property="FamilyRelationsGrid" topBarId="toolBar7" sm="row"  forceNoScroll="true" isFirstLoadData="false" url="/"
			 height="260" title="家庭主要成员及重要社会关系">
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
				  <odin:gridEditColumn header="称谓" dataIndex="a3604a" codeType="GB4761" editor="select"/>
				  <odin:gridEditColumn header="姓名" dataIndex="a3601" editor="text"/>
				  <odin:gridEditColumn header="出身日期" dataIndex="a3607" editor="text"/>
				  <odin:gridEditColumn header="政治面貌" dataIndex="a3627" codeType="GB4762" editor="select"/>
				  <odin:gridEditColumn header="工作单位及职务" dataIndex="a3611" editor="text" isLast="true"/>
				</odin:gridColumnModel>
			</odin:grid>
		</td>
	</tr>
</table>
</odin:groupBox>