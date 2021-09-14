<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<script type="text/javascript">
function deleteRow(){ 
	var sm = Ext.getCmp("TrainingInfoGrid").getSelectionModel();
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

<odin:groupBox property="s3" title="��ѵ��Ϣ">
<odin:toolBar property="toolBar8" applyTo="tol2">
				<odin:fill></odin:fill>
				<odin:buttonForToolBar text="����" id="save" icon="images/save.gif" cls="x-btn-text-icon" ></odin:buttonForToolBar>
				<odin:buttonForToolBar text="����" id="TrainingInfoAddBtn" icon="images/add.gif"></odin:buttonForToolBar>
				<odin:buttonForToolBar text="ɾ��" isLast="true" icon="images/back.gif" id="delete" handler="deleteRow"></odin:buttonForToolBar>
</odin:toolBar>
<div style="border: 1px solid #99bbe8;">
<div id=tol2 align="center"></div>
<%--<odin:textEdit property="a0000" label="��Աid" ></odin:textEdit>--%>
<odin:hidden property="a1100" title="����id" ></odin:hidden>	
<table cellspacing="2" width="98%" align="center">
	<tr>
		<odin:textEdit property="a1131" label="��ѵ������" ></odin:textEdit>	
		<odin:select2 property="a1101" label="��ѵ���" codeType="ZB29"></odin:select2>	
		<!--<odin:dateEdit property="a1107" label="��ѵʱ��" format="Ymd"></odin:dateEdit>
		<odin:dateEdit property="a1111" label="��" format="Ymd"></odin:dateEdit>
	-->
	</tr>
	<tr>
	    <odin:dateEdit property="a1107" label="��ѵʱ��" format="Ymd"></odin:dateEdit>
		<odin:dateEdit property="a1111" label="��" format="Ymd"></odin:dateEdit>
		<!--<odin:textEdit property="a1114" label="��ѵ���쵥λ" ></odin:textEdit>		
		<odin:textEdit property="a1107a" label="" readonly="true">��</odin:textEdit>
		<odin:textEdit property="a1107b" label="��" readonly="true">��</odin:textEdit>
	-->
	</tr>
	<tr>
	    <odin:textEdit property="a1107a" label="" readonly="true">��</odin:textEdit>
		<odin:textEdit property="a1107b" label="��" readonly="true">��</odin:textEdit>
		<!--<odin:select property="a1127" label="��ѵ�������" codeType="ZB27"></odin:select>
		<odin:select property="a1104" label="��ѵ���״̬" codeType="ZB30"></odin:select>
		<odin:select property="a1151" label="����(��)��ʶ" data="['1','��'],['0','��']"></odin:select>
	-->
	</tr>
	<tr>
	    <odin:textEdit property="a1114" label="��ѵ���쵥λ" ></odin:textEdit>	
		<!--<odin:select property="a1101" label="��ѵ���" codeType="ZB29"></odin:select>
		-->
		<odin:textEdit property="a1121a" label="��ѵ��������" ></odin:textEdit>
	</tr>
	<tr>
	    <odin:select2 property="a1127" label="��ѵ�������" codeType="ZB27"></odin:select2>
	    <odin:select2 property="a1104" label="��ѵ���״̬" codeType="ZB30"></odin:select2>
	</tr>
	<tr>
	    <odin:select2 property="a1151" label="����(��)��ʶ" data="['1','��'],['0','��']"></odin:select2>
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
				  <odin:gridEditColumn2 header="��ѵ���" dataIndex="a1101" editor="select" codeType="ZB29" width="100"/>
				  <odin:gridEditColumn header="��ѵ������" dataIndex="a1131" editor="text" width="100"/>
				  <odin:gridEditColumn header="��ѵʱ��" dataIndex="a1107" editor="text" width="100"/>
				  <odin:gridEditColumn header="��" dataIndex="a1111" editor="text" width="100"/>
				  <odin:gridEditColumn header="��ѵʱ�����£�" dataIndex="a1107a" editor="text" width="100"/>
				  <odin:gridEditColumn header="��" dataIndex="a1107b" editor="text" width="100"/>
				  <odin:gridEditColumn header="��ѵ���쵥λ" dataIndex="a1114" editor="text" width="100"/>
				  <odin:gridEditColumn header="��ѵ��������" dataIndex="a1121a" editor="text" width="100"/>
				  <odin:gridEditColumn2 header="��ѵ�������" dataIndex="a1127" editor="select" codeType="ZB27" width="100"/>
				  <odin:gridEditColumn2 header="��ѵ���״̬" dataIndex="a1104" editor="select" codeType="ZB30" width="100"/>
				  <odin:gridEditColumn header="����(��)��ʶ" dataIndex="a1151" editor="select" selectData="['1','��'],['0','��']" width="100" isLast="true"/>
				</odin:gridColumnModel>
			</odin:grid>
		</td>
	</tr>
</table>
</div>
</odin:groupBox>