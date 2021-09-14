<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.FontConfigPageModel"%>
<style>
<%=FontConfigPageModel.getFontConfig()%>
.vfontConfig{
color: red;
}

#border {
	position: relative;
	left: 0px;
	top: 0px;
	width: 0px;
	border: 1px solid #99bbe8;
}

#toolBar8 {
	width: 1172px !important;
}
</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" charset="gbk" src="js/lengthValidator.js"></script>
<%String ctxPath = request.getContextPath(); 

%>
<script type="text/javascript">
function formcheck(){
	return odin.checkValue(document.forms.commForm);
}
function setA6004Value(record,index){//ѧ��
	Ext.getCmp('a6004').setValue(record.data.value);
}
function setA6006Value(record,index){//ѧλ
	Ext.getCmp('a6006').setValue(record.data.value);
}
function setA6108Value(record,index){//ѧ��
	Ext.getCmp('a6108').setValue(record.data.value);
}
function setA6110Value(record,index){//ѧλ
	Ext.getCmp('a6110').setValue(record.data.value);
}
function saveTrain(){
	var a1107 = document.getElementById('a1107').value;//��ѵ��ʼʱ��
	var a1111 = document.getElementById('a1111').value;//��ѵ����ʱ��
	var text1 = dateValidate(a1107);
	var text2 = dateValidate(a1111);
	if(text1!==true){
		parent.$h.alert('ϵͳ��ʾ','��ѵ��ʼʱ��' + text1, null,400);
		return false;
	}
	if(text2!==true){
		parent.$h.alert('ϵͳ��ʾ','��ѵ����ʱ��' + text2, null,400);
		return false;
	}
	radow.doEvent('saveA11.onclick');
}
//�������ڲ��Ǹ����� �޷���ʾ�ڵ�ǰλ�á�
function deleteRow(){ 
	var sm = Ext.getCmp("TrainingInfoGrid").getSelectionModel();
	if(!sm.hasSelection()){
		parent.$h.alert("ϵͳ��ʾ","��ѡ��һ�����ݣ�");
		return;
	}
	parent.Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ�ȷ��ɾ����",function(id) { 
		if("yes"==id){
			radow.doEvent('deleteRow',sm.lastActive+'');
		}else{
			return;
		}		
	});	
}
</script>
<br/><br/>

<odin:groupBox property="s1" title="����¼����Ա��Ϣ">
	<table cellspacing="2" width="460" align="left">
		<tr>
			<odin:select2 property="a6001" label="����¼����Ա" codeType="XZ09"/>
			<odin:NewDateEditTag property="a6002" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;¼��ʱ��" maxlength="80"/>
<%-- 			<odin:textEdit property="a6003" label="&nbsp;&nbsp;&nbsp;&nbsp;¼��ʱ������ò" validator="a6003Length" />
 --%>			<odin:select2 property="a6003" label="&nbsp;&nbsp;&nbsp;&nbsp;¼��ʱ������ò" labelSpanId="a6003SpanId" validator="a6003Length"  codeType="GB4762" ></odin:select2>
			
			<tags:PublicTextIconEdit property="a6009" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��Ա��Դ���" codetype="ZB146" readonly="true" />
		</tr>
		<tr>
		
			<tags:PublicTextIconEdit property="a6005" label="¼��ʱѧ������" onchange="setA6004Value" codetype="ZB64" readonly="true" />
			<odin:textEdit property="a6004" label="¼��ʱѧ������" validator="a6004Length" />
			
			<tags:PublicTextIconEdit property="a6007" label="¼��ʱѧλ����" onchange="setA6006Value" codetype="GB6864" readonly="true" />
			<odin:textEdit property="a6006" label="¼��ʱѧλ����" validator="a6006Length" />
			
		</tr>
		<tr>
			<odin:numberEdit property="a6008" label="&nbsp;&nbsp;&nbsp;&nbsp;¼��ʱ���㹤��ʱ��" maxlength="2" />
			<odin:select2 property="a6010" label="�Ƿ���������Ŀ��Ա" codeType="XZ09" />
			<odin:select2 property="a6011" label="�Ƿ�����ʿ��" codeType="XZ09" />
			<odin:select2 property="a6012" label="�Ƿ����۴�ѧ��ʿ��" codeType="XZ09" />
		</tr>
		<tr>
			<odin:select2 property="a6013" label="�Ƿ�м���" codeType="XZ09"/>
			<odin:select2 property="a6014" label="�Ƿ��к�����ѧ����" codeType="XZ09"/>
			<odin:numberEdit property="a6015" label="��ѧ����" maxlength="2"/>
			<odin:select2 property="a6016" label="�Ƿ��к��⹤������" codeType="XZ09"/>
		</tr>
		<tr>
			<odin:textEdit property="a6017" label="���⹤������" validator="a6_101716Length"/>
		</tr>
		<tr>
			<odin:textEdit property="a6401" label="����׼��֤��" validator="a6401Length" maxlength="50"/>
			<odin:numberEdit property="a6402" label="����ְҵ��������" maxlength="3"/>
			<odin:numberEdit property="a6403" label="���۷���" maxlength="3"/>
			<odin:numberEdit property="a6404" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;������Ŀ����" maxlength="3" />
		</tr>
		<tr>
			<odin:numberEdit property="a6405" label="רҵ�������Է���" maxlength="3"/>
			<odin:numberEdit property="a6406" label="������Ŀ���Գɼ��ܷ�" maxlength="3"/>
			<odin:numberEdit property="a6407" label="רҵ���Գɼ�" maxlength="3"/>
			<odin:numberEdit property="a6408" label="���Գɼ�" maxlength="3"/>
		</tr>
	</table>
</odin:groupBox>
<odin:groupBox property="s2" title="ѡ������Ϣ">
	<table cellspacing="2" width="460" align="left">
		<tr>
			<odin:select2 property="a2970" label="ѡ����" codeType="ZB137" />
			<odin:select2 property="a2970a" label="ѡ������Դ" codeType="ZB138" />
			<odin:textEdit property="a2970b" label="ѡ������ʼ������λ" validator="a2970bLength"></odin:textEdit>
			<odin:NewDateEditTag property="a6104" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;����ѡ����ʱ��" maxlength="8" />
		</tr>
		<tr>
			<tags:PublicTextIconEdit property="a6109" label="ѡ��ʱѧ������" onchange="setA6108Value" codetype="ZB64" readonly="true" />
			<odin:textEdit property="a6108" label="ѡ��ʱѧ������" validator="a6108Length"></odin:textEdit>
			<tags:PublicTextIconEdit property="a6111" label="¼��ʱѧλ����" onchange="setA6110Value" codetype="GB6864" readonly="true" />
			<odin:textEdit property="a6110" label="¼��ʱѧλ����" validator="a6110Length"/>
		</tr>
		<tr>
			<odin:numberEdit property="a2970c" label="�ڻ���������ع���ʱ��" maxlength="4">��</odin:numberEdit>
<%-- 			<odin:textEdit property="a6107" label="ѡ��ʱ������ò" validator="a6107Length"></odin:textEdit>
 --%>			<odin:select2 property="a6107" label="ѡ��ʱ������ò" labelSpanId="a6107SpanId" validator="a6107Length"   codeType="GB4762" ></odin:select2>
			
			<odin:select2 property="a6112" label="�Ƿ����۴�ѧ��ʿ��" codeType="XZ09"/>
			<odin:select2 property="a6113" label="�Ƿ��к�����ѧ����" codeType="XZ09"/>
		</tr>
		<tr>
			<odin:numberEdit property="a6114" label="��ѧ����" maxlength="2"/>
			<odin:select2 property="a6115" label="�Ƿ��к��⹤������" codeType="XZ09"/>
			<odin:textEdit property="a6116" label="���⹤������" validator="a6_101716Length"/>
		</tr>
		<tr>
			<odin:textEdit property="a6401_1" label="����׼��֤��" validator="a6401_1Length" maxlength="25"/>
			<odin:numberEdit property="a6402_1" label="����ְҵ��������" maxlength="3"/>
			<odin:numberEdit property="a6403_1" label="���۷���" maxlength="3"/>
			<odin:numberEdit property="a6404_1" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;������Ŀ����" maxlength="3"/>
		</tr>
		<tr>
			<odin:numberEdit property="a6405_1" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;רҵ�������Է���" maxlength="3"/>
			<odin:numberEdit property="a6406_1" label="������Ŀ���Գɼ��ܷ�" maxlength="3"/>
			<odin:numberEdit property="a6407_1" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;רҵ���Գɼ�" maxlength="3"/>
			<odin:numberEdit property="a6408_1" label="���Գɼ�" maxlength="3"/>
		</tr>
	</table>
</odin:groupBox>
<odin:groupBox property="s3" title="������ѡ��Ϣ">
	<table cellspacing="2" width="460" align="left">
		<tr>
			<odin:select2 property="a2950" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;������ѡ" codeType="XZ09"></odin:select2>
			<odin:select2 property="a6202" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��ѡ���" codeType="ZB142" />
			<odin:select2 property="a6203" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��ѡ��ʽ" codeType="ZB143" />
			<odin:select2 property="a6204" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ԭ��λ�㼶" codeType="ZB141" />
		</tr>
		<tr>
			<odin:NewDateEditTag property="a6205" label="��ѡʱ��" maxlength="8"/>
		</tr>
	</table>

</odin:groupBox>
<odin:groupBox property="s4" title="����ѡ����Ϣ">
	<table cellspacing="2" width="460" align="left">
		<tr>
			<odin:select2 property="a2951" label="����ѡ��" codeType="XZ09"></odin:select2>
			<odin:select2 property="a6302" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;����ѡ�����" codeType="ZB142" />
			<odin:select2 property="a6303" label="ԭ��λ���" codeType="ZB144" />
			<odin:select2 property="a6304" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ԭ��λ�㼶" codeType="ZB141" />
		</tr>
		<tr>
			<odin:select2 property="a6305" label="ԭ��λְ�ƻ�ְ��" codeType="ZB145" />
			<odin:NewDateEditTag property="a6306" label="����ѡ��ʱ��" maxlength="8"/>
			<odin:select2 property="a6307" label="�Ƿ��к�����ѧ����" codeType="XZ09"/>
			<odin:numberEdit property="a6308" label="��ѧ����" maxlength="2"/>
		</tr>
		<tr>
			<odin:select2 property="a6309" label="&nbsp;&nbsp;&nbsp;&nbsp;�Ƿ��к��⹤������" codeType="XZ09"/>
			<odin:textEdit property="a6310" label="���⹤������" validator="a6_101716Length"/>
		</tr>
	</table>
</odin:groupBox>
<!-- 
<odin:groupBox property="s5" title="������Ϣ">
	<table cellspacing="2" width="100%" align="center">
		<tr>
			<odin:numberEdit property="a6401" label="����׼��֤��" maxlength="25"/>
			<odin:numberEdit property="a6402" label="����ְҵ��������" maxlength="3"/>
			<odin:numberEdit property="a6403" label="���۷���" maxlength="3"/>
			<odin:numberEdit property="a6404" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;������Ŀ����" maxlength="3"/>
		</tr>
	</table>
</odin:groupBox>
 -->
<odin:groupBox property="s6" title="��ѵ��Ϣ">
<odin:toolBar property="toolBar8" applyTo="tol2">
				<odin:fill></odin:fill>
				<odin:buttonForToolBar text="����" id="save1" handler="saveTrain" icon="images/save.gif" cls="x-btn-text-icon"></odin:buttonForToolBar>
				<odin:buttonForToolBar text="����" id="TrainingInfoAddBtn" icon="images/add.gif"></odin:buttonForToolBar>
				<odin:buttonForToolBar text="&nbsp;&nbsp;ɾ��" isLast="true" icon="images/back.gif" id="delete" handler="deleteRow"></odin:buttonForToolBar>
</odin:toolBar>

<!--<div style="border: 1px solid #99bbe8;">-->
<div id="border">
<div id="tol2" align="left"></div>
<%--<odin:textEdit property="a0000" label="��Աid" ></odin:textEdit>--%>
<odin:hidden property="a1100" title="����id"></odin:hidden>

<table cellspacing="2" width="460" align="left">
	<tr>
		<odin:textEdit property="a1131" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��ѵ������" validator="a1131Length"></odin:textEdit>
		<odin:select2 property="a1101" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��ѵ���" codeType="ZB29"></odin:select2>
		<odin:textEdit property="a1114" label="��ѵ���쵥λ" validator="a1114Length"></odin:textEdit>
		<odin:textEdit property="a1121a" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��ѵ��������" validator="a1121aLength"></odin:textEdit>
	</tr>
	<tr>
		<odin:NewDateEditTag property="a1107" isCheck="true" label="��ѵ��ʼʱ��" maxlength="8"></odin:NewDateEditTag>
		<odin:NewDateEditTag property="a1111" isCheck="true" label="��ѵ����ʱ��" maxlength="8"></odin:NewDateEditTag>
		<odin:numberEdit property="a1107c" label="��ѵʱ��" decimalPrecision="1" maxlength="4"></odin:numberEdit>
	    <odin:numberEdit property="a1108" label="ѧʱ" decimalPrecision="1" maxlength="4"></odin:numberEdit>
	<%--
		<td align="right" style="padding-right: 8px;"><span id="a1107SpanId" style="font-size: 12" >��ѵʱ��</span> </td>
		<td colspan="3" align="left">
			<table cellpadding="0" cellspacing="0">
			  <tr style="padding-left: 0px;margin-left: 0px;">
			    <odin:numberEdit property="a1107" validator="dateValidate" maxlength="8" >��</odin:numberEdit>
				<odin:numberEdit property="a1111" validator="dateValidate" maxlength="8">&nbsp;&nbsp;&nbsp;</odin:numberEdit>
				<td><odin:button text="����ѧʱ" property="count"></odin:button> </td>
			  </tr>
			</table>
		</td>
	--%>
	</tr>
	<tr style="display: none;">
	    <odin:textEdit property="a1107a" label="" readonly="true">��</odin:textEdit>
		<odin:textEdit property="a1107b" label="��" readonly="true">��</odin:textEdit>
	</tr>
	<tr>
	    <odin:select2 property="a1127" label="��ѵ�������" codeType="ZB27"></odin:select2>
	    <odin:select2 property="a1104" label="��ѵ���״̬" codeType="ZB30"></odin:select2>
	     <odin:select2 property="a1151" label="��������������ѵ��ʶ" data="['1','��'],['0','��']"></odin:select2>
	</tr>
	<tr>
	   
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
			  		<odin:gridDataCol name="a1107c" type="float"/>
			  		<odin:gridDataCol name="a1108" type="float"/>
			  		<odin:gridDataCol name="a1114" />
			  		<odin:gridDataCol name="a1121a" />
			  		<odin:gridDataCol name="a1127" />
			  		<odin:gridDataCol name="a1104" />			
			   		<odin:gridDataCol name="a1151" isLast="true"/>			   		
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
				  <odin:gridRowNumColumn />
				  <odin:gridEditColumn header="id" dataIndex="a1100" editor="text"  width="100" edited="false" hidden="true"/>
				  <odin:gridEditColumn2 header="��ѵ���" dataIndex="a1101" editor="select" codeType="ZB29" edited="false" width="100"/>
				  <odin:gridEditColumn header="��ѵ������" dataIndex="a1131" editor="text" edited="false" width="100"/>
				  <odin:gridEditColumn header="��ѵ��ʼʱ��" dataIndex="a1107" editor="text" edited="false" width="100"/>
				  <odin:gridEditColumn header="��ѵ����ʱ��" dataIndex="a1111" editor="text" edited="false" width="100"/>
				  <odin:gridEditColumn header="��ѵʱ�����£�" dataIndex="a1107a" editor="text" edited="false" width="100" hidden="true"/>
				  <odin:gridEditColumn header="��" dataIndex="a1107b" editor="text" width="100" edited="false" hidden="true"/>
				  <odin:gridEditColumn header="��ѵʱ��" dataIndex="a1107c" editor="text" edited="false" width="100"/>
				  <odin:gridEditColumn header="ѧʱ" dataIndex="a1108" editor="text" edited="false" width="100"/>
				  <odin:gridEditColumn header="��ѵ���쵥λ" dataIndex="a1114" editor="text" edited="false" width="100"/>
				  <odin:gridEditColumn header="��ѵ��������" dataIndex="a1121a" editor="text" edited="false" width="100"/>
				  <odin:gridEditColumn2 header="��ѵ�������" dataIndex="a1127" editor="select" edited="false" codeType="ZB27" width="100"/>
				  <odin:gridEditColumn2 header="��ѵ���״̬" dataIndex="a1104" editor="select" edited="false" codeType="ZB30" width="100"/>
				  <odin:gridEditColumn2 header="��������������ѵ��ʶ" dataIndex="a1151" editor="select" edited="false" selectData="['1','��'],['0','��']" width="100" isLast="true"/>
				</odin:gridColumnModel>
			</odin:grid>
		</td>
	</tr>
</table>
</div>
</odin:groupBox>
<script type="text/javascript">
 Ext.onReady(function(){
 	
 	applyFontConfig();
 	if(parent.buttonDisabled){
		$h.setDisabled($h.disabledButtons.orthers);
	}
 	$h.fieldsDisabled(parent.fieldsDisabled);
 });
 function applyFontConfig(){
	var cls = 'fontConfig';
	if(parent.isveryfy){
		cls = 'vfontConfig';
	}
	//var $ = parent.$;
	var spFeild = parent.spFeild;
	for(var i_=0;i_<spFeild.length;i_++){
		if(document.getElementById(spFeild[i_]+'SpanId_s')){
			$('#'+spFeild[i_]+'SpanId_s').addClass(cls);
		}else if(document.getElementById(spFeild[i_]+'SpanId')){
			$('#'+spFeild[i_]+'SpanId').addClass(cls);
		}
    	
    }
}
</script>

