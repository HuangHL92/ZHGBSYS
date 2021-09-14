<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%String ctxPath = request.getContextPath(); 
%>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.FontConfigPageModel"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="js/lengthValidator.js"></script>
<%@include file="/comOpenWinInit2.jsp" %>
<body>
	
<odin:toolBar property="toolBar8" applyTo="tol2">
				<odin:fill></odin:fill>
				<odin:buttonForToolBar text="����" id="TrainingInfoAddBtn" icon="images/add.gif"></odin:buttonForToolBar>
				<odin:buttonForToolBar text="����" id="save1" handler="saveTrain" icon="images/save.gif" cls="x-btn-text-icon" isLast="true"></odin:buttonForToolBar>
				<%-- <odin:buttonForToolBar text="&nbsp;&nbsp;ɾ��" isLast="true" icon="images/back.gif" id="delete" handler="deleteRow"></odin:buttonForToolBar> --%>
</odin:toolBar>

<div id="border">
<div id="tol2" align="left"></div>
<odin:hidden property="a0134" title="�μӹ���ʱ��"/>
<odin:hidden property="a0500" title="����id"></odin:hidden>
<odin:hidden property="a0000" title="��Ա���"></odin:hidden>
<table cellspacing="2" width="600" align="center" style="width: 100%">
	<tr>
		<tags:PublicTextIconEdit property="a0501b" codetype="ZB09" width="160" label="ְ����" required="true" readonly="true"/>
		<td>&nbsp;</td>
		<odin:select2 property="a0524" label="״&nbsp;&nbsp;̬&nbsp;" codeType="ZB14" onchange="setA0517Disabled" required="true"></odin:select2>
		<td>&nbsp;</td>
		<odin:NewDateEditTag property="a0504" isCheck="true" label="��׼����" maxlength="8" required="true"></odin:NewDateEditTag>
	</tr>
	<tr>
		<odin:NewDateEditTag property="a0517" isCheck="true" label="&nbsp;&nbsp;��ֹ����" maxlength="8"></odin:NewDateEditTag>
		<%-- <odin:textEdit property="a0511" label="&nbsp;&nbsp;��׼�ĺ�" validator="a0511Length"></odin:textEdit> --%>
	</tr>
	<tr>
		<td colspan="8">
			
			<odin:grid property="TrainingInfoGrid" topBarId="toolBar8" sm="row"  isFirstLoadData="false" url="/"
			 height="230">
				<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
					<%-- <odin:gridDataCol name="a0525" /> --%>
					<odin:gridDataCol name="a0500" />
			  		<odin:gridDataCol name="a0501b" />
			  		<odin:gridDataCol name="a0504"/>
			  		<%-- <odin:gridDataCol name="a0511" /> --%>
			  		<odin:gridDataCol name="a0517" />
			   		<odin:gridDataCol name="a0524"/>			   		
			   		<odin:gridDataCol name="delete" isLast="true"/>			   		
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
				  <odin:gridRowNumColumn />
				  <%-- <odin:gridEditColumn2 header="�����ʶ" width="50" editor="checkbox" dataIndex="a0525" required="true" checkBoxClick="a05checkBoxColClick"  edited="true"/> --%>
				  <odin:gridEditColumn2 header="����" dataIndex="a0500" editor="text" edited="false" width="100" hidden="true"/>
				  <odin:gridEditColumn2 header="ְ����" align="center" dataIndex="a0501b" editor="select" edited="false" codeType="ZB09" width="100"/>
				  <odin:gridEditColumn2 header="��׼����" align="center" dataIndex="a0504" editor="text" edited="false" width="100"/>
				 <%--  <odin:gridEditColumn header="��׼�ĺ�" align="center" dataIndex="a0511" editor="text" edited="false" width="100"/> --%>
				  <odin:gridEditColumn2 header="��ֹ����" align="center" dataIndex="a0517" editor="text" width="100" edited="false"/>
				  <%-- <odin:gridEditColumn header="״̬" dataIndex="a0524" editor="text" edited="false" width="100"/> --%>
				  <odin:gridEditColumn2 header="״̬" align="center" dataIndex="a0524" editor="select" edited="false" selectData="['1','����'],['0','����']" width="100"/>
				    <odin:gridEditColumn2 width="45" header="����" dataIndex="delete" editor="text" edited="false" renderer="deleteRowRenderer" isLast="true"/>
				</odin:gridColumnModel>
			</odin:grid>
		</td>
	</tr>
</table>
</div>
<odin:hidden property="a0531" value="0"/>
<odin:hidden property="a0525"/>
</body>
<script type="text/javascript">
function deleteRowRenderer(value, params, record,rowIndex,colIndex,ds){
	var a0500 = record.data.a0500;
	if(realParent.buttonDisabled){
		return "ɾ��";
	}
	return "<a href=\"javascript:deleteRow2(&quot;"+a0500+"&quot;)\">ɾ��</a>";
}
function deleteRow2(a0500){ 
	Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ�ȷ��ɾ����",function(id) { 
		if("yes"==id){
			radow.doEvent('deleteRow',a0500);
		}else{
			return;
		}		
	});	
}

function setA0517Disabled(){
	var value = document.getElementById("a0524").value;
	if("0"==value || value==''){
		document.getElementById("a0517_1").readOnly=false;
		document.getElementById("a0517_1").disabled=false;
		document.getElementById("a0517_1").style.backgroundColor="#fff";
		document.getElementById("a0517_1").style.backgroundImage="url(<%=ctxPath%>/commform/basejs/ext/resources/images/default/form/text-bg.gif)";
		
<%-- 		document.getElementById("a0511").readOnly=false;
		document.getElementById("a0511").disabled=false;
		document.getElementById("a0511").style.backgroundColor="#fff";
		document.getElementById("a0511").style.backgroundImage="url(<%=ctxPath%>/commform/basejs/ext/resources/images/default/form/text-bg.gif)"; --%>
	}else if("1"==value){
		document.getElementById("a0517_1").readOnly=true;
		document.getElementById("a0517_1").disabled=true;
		document.getElementById("a0517_1").style.backgroundColor="#EBEBE4";
		document.getElementById("a0517_1").style.backgroundImage="none";
		document.getElementById("a0517").value="";
		document.getElementById("a0517_1").value="";
		
		/* document.getElementById("a0511").readOnly=true;
		document.getElementById("a0511").disabled=true;
		document.getElementById("a0511").style.backgroundColor="#EBEBE4";
		document.getElementById("a0511").style.backgroundImage="none";
		document.getElementById("a0511").value=""; */
	}
}


function a0501bChange(){
	var a0501b = document.getElementById("a0501b").value;
	
	if(a0501b == "1A98" || a0501b == "1C98" || a0501b == "27" || a0501b == "1B98" || a0501b == "911" || a0501b == "C98"){
		
		$h.dateDisable('a0504');
		
		<%-- document.getElementById("a0517_1").readOnly=false;
		document.getElementById("a0517_1").disabled=false;
		document.getElementById("a0517_1").style.backgroundColor="#fff";
		document.getElementById("a0517_1").style.backgroundImage="url(<%=ctxPath%>/commform/basejs/ext/resources/images/default/form/text-bg.gif)"; --%>
	}else{
		
		$h.dateEnable('a0504');
		
		/* document.getElementById("a0517_1").readOnly=true;
		document.getElementById("a0517_1").disabled=true;
		document.getElementById("a0517_1").style.backgroundColor="#EBEBE4";
		document.getElementById("a0517_1").style.backgroundImage="none";
		document.getElementById("a0517").value="";
		document.getElementById("a0517_1").value=""; */
	}
	
}


function saveTrain(){
	var a0524 = document.getElementById("a0524").value;//״̬
	var a0504 = document.getElementById("a0504").value;//��׼����
	var a0517 = document.getElementById('a0517').value;//��������
	var a0504_1 = document.getElementById("a0504_1").value;		//��׼����ҳ����ʾֵ
	var a0517_1 = document.getElementById("a0517_1").value;		//��������ҳ����ʾֵ 
	
	var a0501b = document.getElementById("a0501b").value;
	
	//��׼���ڡ�״̬���� ��ְ���κ�̨��У�飩
	if(!a0524){
		$h.alert('ϵͳ��ʾ','״̬����Ϊ�գ�', null,200);
		return false;
	}
	if(!a0504_1){
		
		$h.alert('ϵͳ��ʾ','��׼���ڲ���Ϊ�գ�', null,200);
		return false;
	}
	
	if("0"==a0524 && !a0517_1){
		$h.alert('ϵͳ��ʾ','��ֹ���ڲ���Ϊ�գ�', null,200);
		return false;
	}
	var now = new Date();
	
	var value = a0504;
	var length = value.length;
	var year = value.substring(0,4);
	var month = value.substring(4,6);
	var day;
	if(length==8){
		day = value.substring(6,8);
	}else{
		day = '01';
	}
	year = parseInt(year,10); month = parseInt(month,10); day = parseInt(day,10);
	date2=new Date(year, month-1,day)
	if(Date.parse(date2)>Date.parse(now)){
		odin.alert("��׼���ڲ�������ϵͳ��ǰʱ��");
		return false;
	}
	
	value = a0517;
	var length = value.length;
	var year = value.substring(0,4);
	var month = value.substring(4,6);
	var day;
	if(length==8){
		day = value.substring(6,8);
	}else{
		day = '01';
	}
	year = parseInt(year,10); month = parseInt(month,10); day = parseInt(day,10);
	date2=new Date(year, month-1,day)
	
	if(Date.parse(date2)>Date.parse(now)){
		odin.alert("��ֹ���ڲ�������ϵͳ��ǰʱ��");
		return false;
	}
	
	
	
	var text1 = dateValidateBeforeTady(a0504_1);
	var text2 = dateValidateBeforeTady(a0517_1);
	
	if(a0504_1.indexOf(".") > 0){
		text1 = dateValidateBeforeTady(a0504);
	}
	if(a0517_1.indexOf(".") > 0){
		text2 = dateValidateBeforeTady(a0517);
	}
	
	if(text1!==true){
		$h.alert('ϵͳ��ʾ','��׼����' + text1, null,400);
		return false;
	}
	if(text2!==true){
		$h.alert('ϵͳ��ʾ','��ֹ����' + text2, null,400);
		return false;
	}
	
	radow.doEvent('saveA11.onclick');
}
function deleteRow(){ 
	var sm = Ext.getCmp("TrainingInfoGrid").getSelectionModel();
	if(!sm.hasSelection()){
		$h.alert("ϵͳ��ʾ","��ѡ��һ�����ݣ�");
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

Ext.onReady(function(){
	
	document.getElementById("a0134").value=$("#a0134", realParent.document).val();
	
	//$h.fieldsDisabled(realParent.fieldsDisabled);
	if(realParent.buttonDisabled){
		$h.setDisabled($h.disabledButtons.xzwcc);
	}
	
	
	/* var a0501b = document.getElementById("a0501b").value;
	
	if(a0501b == "1A98" || a0501b == "1C98" || a0501b == "27" || a0501b == "1B98" || a0501b == "911" || a0501b == "C98"){
		
		$h.dateDisable('a0504');
	}else{
		$h.dateEnable('a0504');
	} */
	
});


function lockINFO(){
	Ext.getCmp("TrainingInfoAddBtn").disable(); 
	Ext.getCmp("save1").disable(); 
	Ext.getCmp("TrainingInfoGrid").getColumnModel().setHidden(6,true); 
}

/* function a05checkBoxColClick(rowIndex,colIndex,dataIndex,gridName){
	var a0525=Ext.getCmp(gridName).store.data.itemAt(rowIndex).get("a0525");
	var TrainingInfoGrid = Ext.getCmp("TrainingInfoGrid").store;
	for(var i=0;i<TrainingInfoGrid.data.length;i++){
		TrainingInfoGrid.data.itemAt(i).set("a0525",false);
	}
	if(a0525 == true){
		TrainingInfoGrid.data.itemAt(rowIndex).set("a0525",true);
		document.getElementById('a0525').value='1';
	}else{
		TrainingInfoGrid.data.itemAt(rowIndex).set("a0525",false);
		document.getElementById('a0525').value='0';
	}
	//radow.doEvent('TrainingInfoGrid.rowclick',sm.lastActive+'');
	radow.doEvent('selectFun.click',rowIndex+""+"_"+document.getElementById('a0525').value);
} */
</script>
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
	width: 690px !important;
}
</style>
