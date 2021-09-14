<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<script type="text/javascript">
function deleteRow(){ 
	var sm = Ext.getCmp("TrainingInfoGrid").getSelectionModel();
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

<odin:groupBox property="s3" title="培训信息">
<odin:toolBar property="toolBar8" applyTo="tol2">
				<odin:fill></odin:fill>
				<odin:buttonForToolBar text="保存" id="save" icon="images/save.gif" cls="x-btn-text-icon" ></odin:buttonForToolBar>
				<odin:buttonForToolBar text="新增" id="TrainingInfoAddBtn" icon="images/add.gif"></odin:buttonForToolBar>
				<odin:buttonForToolBar text="删除" isLast="true" icon="images/back.gif" id="delete" handler="deleteRow"></odin:buttonForToolBar>
</odin:toolBar>
<div style="border: 1px solid #99bbe8;">
<div id=tol2 align="center"></div>
<%--<odin:textEdit property="a0000" label="人员id" ></odin:textEdit>--%>
<odin:hidden property="a1100" title="主键id" ></odin:hidden>	
<table cellspacing="2" width="98%" align="center">
	<tr>
		<odin:textEdit property="a1131" label="培训班名称" ></odin:textEdit>	
		<odin:select2 property="a1101" label="培训类别" codeType="ZB29"></odin:select2>	
		<!--<odin:dateEdit property="a1107" label="培训时间" format="Ymd"></odin:dateEdit>
		<odin:dateEdit property="a1111" label="至" format="Ymd"></odin:dateEdit>
	-->
	</tr>
	<tr>
	    <odin:dateEdit property="a1107" label="培训时间" format="Ymd"></odin:dateEdit>
		<odin:dateEdit property="a1111" label="至" format="Ymd"></odin:dateEdit>
		<!--<odin:textEdit property="a1114" label="培训主办单位" ></odin:textEdit>		
		<odin:textEdit property="a1107a" label="" readonly="true">月</odin:textEdit>
		<odin:textEdit property="a1107b" label="零" readonly="true">天</odin:textEdit>
	-->
	</tr>
	<tr>
	    <odin:textEdit property="a1107a" label="" readonly="true">月</odin:textEdit>
		<odin:textEdit property="a1107b" label="零" readonly="true">天</odin:textEdit>
		<!--<odin:select property="a1127" label="培训机构类别" codeType="ZB27"></odin:select>
		<odin:select property="a1104" label="培训离岗状态" codeType="ZB30"></odin:select>
		<odin:select property="a1151" label="出国(境)标识" data="['1','是'],['0','否']"></odin:select>
	-->
	</tr>
	<tr>
	    <odin:textEdit property="a1114" label="培训主办单位" ></odin:textEdit>	
		<!--<odin:select property="a1101" label="培训类别" codeType="ZB29"></odin:select>
		-->
		<odin:textEdit property="a1121a" label="培训机构名称" ></odin:textEdit>
	</tr>
	<tr>
	    <odin:select2 property="a1127" label="培训机构类别" codeType="ZB27"></odin:select2>
	    <odin:select2 property="a1104" label="培训离岗状态" codeType="ZB30"></odin:select2>
	</tr>
	<tr>
	    <odin:select2 property="a1151" label="出国(境)标识" data="['1','是'],['0','否']"></odin:select2>
	</tr>
	<tr>
		<td colspan="8">
			
			<odin:grid property="TrainingInfoGrid" topBarId="toolBar8" sm="row"  isFirstLoadData="false" url="/"
			 height="230">
				<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="a1100" />
			  		<odin:gridDataCol name="a1101" />
			  		<odin:gridDataCol name="a1131" />
			  		<odin:gridDataCol name="a1107" />
			  		<odin:gridDataCol name="a1111" />
			  		<odin:gridDataCol name="a1107a" />
			  		<odin:gridDataCol name="a1107b" />
			  		<odin:gridDataCol name="a1114" />
			  		<odin:gridDataCol name="a1121a" />
			  		<odin:gridDataCol name="a1127" />
			  		<odin:gridDataCol name="a1104" />			
			   		<odin:gridDataCol name="a1151" isLast="true"/>			   		
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
				  <odin:gridRowNumColumn />
				  <odin:gridEditColumn header="id" dataIndex="a1100" editor="text" width="100" hidden="true"/>
				  <odin:gridEditColumn2 header="培训类别" dataIndex="a1101" editor="select" codeType="ZB29" width="100"/>
				  <odin:gridEditColumn header="培训班名称" dataIndex="a1131" editor="text" width="100"/>
				  <odin:gridEditColumn header="培训时间" dataIndex="a1107" editor="text" width="100"/>
				  <odin:gridEditColumn header="至" dataIndex="a1111" editor="text" width="100"/>
				  <odin:gridEditColumn header="培训时长（月）" dataIndex="a1107a" editor="text" width="100"/>
				  <odin:gridEditColumn header="天" dataIndex="a1107b" editor="text" width="100"/>
				  <odin:gridEditColumn header="培训主办单位" dataIndex="a1114" editor="text" width="100"/>
				  <odin:gridEditColumn header="培训机构名称" dataIndex="a1121a" editor="text" width="100"/>
				  <odin:gridEditColumn2 header="培训机构类别" dataIndex="a1127" editor="select" codeType="ZB27" width="100"/>
				  <odin:gridEditColumn2 header="培训离岗状态" dataIndex="a1104" editor="select" codeType="ZB30" width="100"/>
				  <odin:gridEditColumn header="出国(境)标识" dataIndex="a1151" editor="select" selectData="['1','是'],['0','否']" width="100" isLast="true"/>
				</odin:gridColumnModel>
			</odin:grid>
		</td>
	</tr>
</table>
</div>
</odin:groupBox>