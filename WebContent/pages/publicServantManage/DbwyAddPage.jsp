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
<odin:hidden property="id" title="����id"></odin:hidden>
<table cellspacing="2" width="900" align="center" style="width: 100%">
	<tr>
		<odin:select2  property="rank" label="����"  data="['1', '����'],['2', 'ʡ'],['3', '��'],['4', '������']" ></odin:select2>
        <odin:select2  property="dbwy" label="����ίԱ"  data="['1', '��ίίԱ'],['2', '�˴�ί'],['3', '�˴����'],['4', '��ЭίԱ'],['5', '������'],['6', '��ίίԱ']" ></odin:select2>
		<odin:textEdit property="xqjb"  label="ѡ��/���"  />
	</tr>
	<tr>
		<odin:NewDateEditTag property="rzsj" isCheck="true" label="��ְʱ��"/>
        <odin:NewDateEditTag property="mzsj" isCheck="true" label="��ְʱ��"/>
	</tr>
	<tr>
		<td colspan="12">
			<odin:grid property="TrainingInfoGrid" topBarId="btnToolBar" sm="row"  isFirstLoadData="false" url="/"
			 height="330">
				<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="id" />
			  		<odin:gridDataCol name="rank" />
			  		<odin:gridDataCol name="dbwy"/>
			  		<odin:gridDataCol name="xqjb" />
			  		<odin:gridDataCol name="rzsj"/>
			   		<odin:gridDataCol name="mzsj" />	
			   		<odin:gridDataCol name="delete" isLast="true"/>			  		   		
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
				  <odin:gridRowNumColumn />
				  <%-- <odin:gridEditColumn2 header="�����ʶ" width="50" editor="checkbox" dataIndex="a0525" required="true" checkBoxClick="a05checkBoxColClick"  edited="true"/> --%>
				  <odin:gridEditColumn2 header="����" align="center" dataIndex="rank" editor="select" edited="false" width="300" selectData="['1', '����'],['2', 'ʡ'],['3', '��'],['4', '������']"/>
				  <odin:gridEditColumn2 header="����ίԱ" align="center" dataIndex="dbwy" editor="select" edited="false" width="300" selectData="['1', '��ίίԱ'],['2', '�˴�ί'],['3', '�˴����'],['4', '��ЭίԱ'],['5', '������'],['6', '��ίίԱ']" />
				  <odin:gridEditColumn2 header="ѡ��/���" align="center" dataIndex="xqjb" editor="text" edited="false"  width="300"/>
				  <odin:gridEditColumn2 header="��ְʱ��" align="center" dataIndex="rzsj" editor="text" edited="false" width="200"/>
				  <odin:gridEditColumn2 header="��ְʱ��" align="center" dataIndex="mzsj" editor="text" width="200" edited="false"/>
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
	var id1 = record.data.id;
	if(realParent.buttonDisabled){
		return "ɾ��";
	}
	return "<a href=\"javascript:deleteRow2(&quot;"+id1+"&quot;)\">ɾ��</a>";
}
function deleteRow2(id1){ 
	Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ�ȷ��ɾ����",function(id) { 
		if("yes"==id){
			radow.doEvent('deleteRow',id1);
		}else{
			return;
		}		
	});	
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
	var rank = document.getElementById("rank").value;//����
	var dbwy = document.getElementById("dbwy").value;//����ίԱ
	var rzsj= document.getElementById("rzsj").value;//��ְʱ��

	
	//��׼���ڡ�״̬���� ����̨��У�飩
	if(!rank){
		$h.alert('ϵͳ��ʾ','������Ϊ�գ�', null,200);
		return false;
	}
	if(!dbwy){
		
		$h.alert('ϵͳ��ʾ',' ����Ϊ�գ�', null,200);
		return false;
	}
	
	if("0"==rzsj && !rzsj){
		$h.alert('ϵͳ��ʾ','��ְʱ�䲻��Ϊ�գ�', null,200);
		return false;
	}	
	radow.doEvent('saveDbwy.onclick');
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