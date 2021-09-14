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


<div id="btnToolBarDiv" style="width: 1019px;"></div>
<odin:toolBar property="btnToolBar" applyTo="btnToolBarDiv">
				<odin:fill></odin:fill>
				<odin:buttonForToolBar text="����" id="TrainingInfoAddBtn" icon="images/add.gif"></odin:buttonForToolBar>
				<odin:buttonForToolBar text="����" id="save1" handler="saveTrain" icon="images/save.gif" cls="x-btn-text-icon" isLast="true"></odin:buttonForToolBar>
				<%-- <odin:buttonForToolBar text="&nbsp;&nbsp;ɾ��" isLast="true" icon="images/back.gif" id="delete" handler="deleteRow"></odin:buttonForToolBar> --%>
</odin:toolBar>

<div id="border" >
<div id="tol2" align="left"></div>
<odin:hidden property="a0000" title="��Ա���"></odin:hidden>
<odin:hidden property="st00" title="����id"></odin:hidden>
<odin:hidden property="STJZ" title="���ż�ְְ�� "></odin:hidden>
<table cellspacing="2" width="900" align="center" style="width: 100%">
	<tr>
		<odin:textEdit property="stname"  width="160" label="��������" required="true" />
		<td>&nbsp;</td>
		<odin:textEdit property="stjob"  width="160" label="����ְ��" required="true" />
		<td>&nbsp;</td>
		<odin:select2 property="stnature" label="��������" data="['����','����'],['�����','�����'],['��ҵЭ��','��ҵЭ��'],['����','����']" required="true"></odin:select2>
		<td>&nbsp;</td>
		<odin:select2 property="status" label="״&nbsp;&nbsp;̬&nbsp;" data="['1','����'],['0','����']" onchange="setClosingDateDisabled" required="true"></odin:select2>
	</tr>
	<tr>
		<odin:NewDateEditTag property="startdate" isCheck="true" label="ͬ���ְ�ڼ� ��ʼ" maxlength="8"></odin:NewDateEditTag>
		<td>&nbsp;</td>
		<odin:NewDateEditTag property="closingdate" isCheck="true" label="ͬ���ְ�ڼ� ��ֹ" maxlength="8"></odin:NewDateEditTag>
		<td>&nbsp;</td>
		<odin:textEdit property="sessionsnum"  width="160" label="���ν���"  />
		<td>&nbsp;</td>
		<odin:select2 property="salary" label="�Ƿ�ȡн" data="['1','��'],['0','��']"  required="true"></odin:select2>
	</tr>
	<tr>
	<odin:NewDateEditTag property="approvaldate" isCheck="true" label="��׼����" maxlength="8" required="true" ></odin:NewDateEditTag>
	</tr>
	<tr>
		<td colspan="12">
			
			<odin:grid property="TrainingInfoGrid" topBarId="btnToolBar" sm="row"  isFirstLoadData="false" url="/"
			 height="330">
				<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="st00" />
			  		<odin:gridDataCol name="stname" />
			  		<odin:gridDataCol name="stjob"/>
			  		<odin:gridDataCol name="stnature" />
			  		<odin:gridDataCol name="status"/>
			   		<odin:gridDataCol name="approvaldate"/>			  
			   		<odin:gridDataCol name="startdate"/>	
			   		<odin:gridDataCol name="closingdate"/>	
			   		<odin:gridDataCol name="sessionsnum"/>	
			   		<odin:gridDataCol name="salary"/>		 		
			   		<odin:gridDataCol name="delete" isLast="true"/>			   		
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
				  <odin:gridRowNumColumn />
				  <%-- <odin:gridEditColumn2 header="�����ʶ" width="50" editor="checkbox" dataIndex="a0525" required="true" checkBoxClick="a05checkBoxColClick"  edited="true"/> --%>
				  <odin:gridEditColumn2 header="��������" align="center" dataIndex="stname" editor="text" edited="false" width="300"/>
				  <odin:gridEditColumn2 header="����ְ��" align="center" dataIndex="stjob" editor="text" edited="false" width="300"/>
				  <odin:gridEditColumn2 header="��������" align="center" dataIndex="stnature" editor="text" edited="false"  width="300"/>
				  <odin:gridEditColumn2 header="״̬" align="center" dataIndex="status" editor="select" edited="false" selectData="['1','����'],['0','����']" width="300"/>
				  <odin:gridEditColumn2 header="��׼����" align="center" dataIndex="approvaldate" editor="text" edited="false" width="200"/>
				 <%--  <odin:gridEditColumn header="��׼�ĺ�" align="center" dataIndex="a0511" editor="text" edited="false" width="100"/> --%>
				 <odin:gridEditColumn2 header="��ʼ����" align="center" dataIndex="startdate" editor="text" edited="false" width="200"/>
				  <odin:gridEditColumn2 header="��ֹ����" align="center" dataIndex="closingdate" editor="text" width="200" edited="false"/>
				  <%-- <odin:gridEditColumn header="״̬" dataIndex="a0524" editor="text" edited="false" width="100"/> --%>
				  <odin:gridEditColumn2 header="���ν���" align="center" dataIndex="sessionsnum" editor="text" edited="false" width="300"/>
				  <odin:gridEditColumn2 header="�Ƿ�ȡн" align="center" dataIndex="salary" editor="select" edited="false" selectData="['1','��'],['0','��']" width="300"/>
				  <odin:gridEditColumn2 width="150" header="����" dataIndex="delete" editor="text" edited="false" renderer="deleteRowRenderer" isLast="true"/>
				</odin:gridColumnModel>
			</odin:grid>
		</td>
	</tr>
</table>
</div>
</body>
<script type="text/javascript">

//ɾ����
function deleteRowRenderer(value, params, record,rowIndex,colIndex,ds){
	var st00 = record.data.st00;
	if(realParent.buttonDisabled){
		return "ɾ��";
	}
	return "<a href=\"javascript:deleteRow2(&quot;"+st00+"&quot;)\">ɾ��</a>";
}
function deleteRow2(st00){ 
	Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ�ȷ��ɾ����",function(id) { 
		if("yes"==id){
			radow.doEvent('deleteRow',st00);
		}else{
			return;
		}		
	});	
}



//ͨ��״̬ȷ��������ֹ����
function setClosingDateDisabled(){
	var value = document.getElementById("status").value;
	if("0"==value || value==''){
		document.getElementById("closingdate_1").readOnly=false;
		document.getElementById("closingdate_1").disabled=false;
		document.getElementById("closingdate_1").style.backgroundColor="#fff";
		document.getElementById("closingdate_1").style.backgroundImage="url(<%=ctxPath%>/commform/basejs/ext/resources/images/default/form/text-bg.gif)";
		
	}else if("1"==value){
		document.getElementById("closingdate_1").readOnly=true;
		document.getElementById("closingdate_1").disabled=true;
		document.getElementById("closingdate_1").style.backgroundColor="#EBEBE4";
		document.getElementById("closingdate_1").style.backgroundImage="none";
		document.getElementById("closingdate").value="";
		document.getElementById("closingdate_1").value="";	
	}
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

//���ݴ���
function saveTrain(){
	var status = document.getElementById("status").value;//״̬
	var approvaldate= document.getElementById("approvaldate").value;//��׼����
	var closingdate= document.getElementById('closingdate').value;//��������
	var approvaldate_1 = document.getElementById("approvaldate_1").value;		//��׼����ҳ����ʾֵ
	var closingdate_1 = document.getElementById("closingdate_1").value;		//��������ҳ����ʾֵ 
	var stname = document.getElementById("stname").value;
	
	//��׼���ڡ�״̬���� ����̨��У�飩
	if(!status){
		$h.alert('ϵͳ��ʾ','״̬����Ϊ�գ�', null,200);
		return false;
	}
	if(!approvaldate_1){
		
		$h.alert('ϵͳ��ʾ','��׼���ڲ���Ϊ�գ�', null,200);
		return false;
	}
	
	if("0"==approvaldate && !closingdate_1){
		$h.alert('ϵͳ��ʾ','��ֹ���ڲ���Ϊ�գ�', null,200);
		return false;
	}	
	radow.doEvent('saveAssociation.onclick');
}



function lockINFO(){
	Ext.getCmp("TrainingInfoAddBtn").disable(); 
	Ext.getCmp("save1").disable(); 
	Ext.getCmp("TrainingInfoGrid").getColumnModel().setHidden(6,true); 
}
</script>
<style>
<%=FontConfigPageModel.getFontConfig()%>
</style>