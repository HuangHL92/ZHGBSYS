<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%String ctxPath = request.getContextPath(); 
String sign = request.getParameter("sign");
%>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.FontConfigPageModel"%>
<%@page import="com.insigma.siis.local.pagemodel.cadremgn.sysmanager.authority.TableColInterface"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<style>
body {
	background-color: rgb(214,227,243);
}
.dasda td{width: 120px;padding-bottom: 5px;}
</style>
<odin:toolBar property="toolBar8">
				<odin:fill></odin:fill>
				<odin:buttonForToolBar text="����" id="save" icon="images/save.gif" cls="x-btn-text-icon" ></odin:buttonForToolBar>
				<odin:buttonForToolBar text="����" id="TrainAddBtn" isLast="true" icon="images/add.gif"></odin:buttonForToolBar>
</odin:toolBar>

<table class="dasda" style="width:100%;margin-top: 50px;">
	<tr>
		<odin:textEdit property="startdate" label="��ʼ����" ></odin:textEdit>
		<odin:textEdit property="enddate" label="��������" ></odin:textEdit>
		<odin:textEdit property="entrycontent" label="����"></odin:textEdit>
	</tr>
	<tr>
		<td height="20px"></td>
	</tr>
</table>

	<odin:grid property="TrainInfoGrid" sm="row" isFirstLoadData="false" url="/" topBarId="toolBar8"
				height="500" autoFill="false"  >
					<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
						<odin:gridDataCol name="delete" />
						<odin:gridDataCol name="a1700" />
						<odin:gridDataCol name="a0101"/>
						<odin:gridDataCol name="start_date" />
						<odin:gridDataCol name="end_date" />
						<odin:gridDataCol name="entry_content"/>
						<odin:gridDataCol name="comm" isLast="true"/>	  		
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
					  <odin:gridRowNumColumn />
					  <odin:gridEditColumn2 width="45" header="����" dataIndex="delete" editor="text" edited="false" renderer="deleteRowRenderer" />					  
					  <odin:gridEditColumn2 header="����" dataIndex="a1700" editor="text" edited="false" hidden="true"/>
					  <odin:gridEditColumn2 header="��ʼ����" align="center" dataIndex="start_date" editor="text" edited="false" width="120"/>
					  <odin:gridEditColumn2 header="��������" align="center" dataIndex="end_date" editor="text" edited="false" width="120"/>
					  <odin:gridEditColumn2 header="����" align="center" dataIndex="entry_content" editor="text" edited="false" width="350"/>
					  <odin:gridEditColumn2 header="����" align="center" isLast="true" dataIndex="comm" renderer="showRenderer" editor="text" edited="false" width="150"/>
					</odin:gridColumnModel>
			</odin:grid>
<odin:hidden property="a0000" title="��Ա����"/>
<odin:hidden property="a1700" title="����id" ></odin:hidden>	
<odin:hidden property="title" title="����"/>

</body>
<script type="text/javascript">


var fieldsDisabled = <%=TableColInterface.getUpdateDataByTable("A11",sign)%>;
var selectDisabled = <%=TableColInterface.getSelectDataByTable("A11")%>;
<%-- Ext.onReady(function(){
	//����Ϣ����ϸ��Ȩ�޿��ƣ��Ƿ����ά�� 
	$h.fieldsDisabled(fieldsDisabled); 
	//����Ϣ����ϸ��Ȩ�޿��ƣ��Ƿ���Բ鿴
	//var imgdata = "<img src='<%=request.getContextPath()%>/image/quanxian1.png' />";
	var imgdata = "url(<%=request.getContextPath()%>/image/suo.jpg)";
	$h.selectDisabled(selectDisabled,imgdata); 
}); --%>
function inita1151(value, params, record,rowIndex,colIndex,ds){
	if(value==0){
		return "<span>��</span>";
	}else if(value==1){
		return "<span>��</span>";
	}else if(value==undefined||value==null||value==''){
		return "<span></span>";
	}else{
		return "<span>�쳣</span>";
	}
}
Ext.onReady(function(){
	document.getElementById("a0000").value = window.parent.document.getElementById("a0000").value;
});
function AddBtn(){
	radow.doEvent('TrainAddBtn.onclick');
}
function deleteRowRenderer(value, params, record,rowIndex,colIndex,ds){
	var a1700 = record.data.a1700; 
	return "<a href=\"javascript:deleteRow2(&quot;"+a1700+"&quot;)\">ɾ��</a>";
}
function deleteRow2(a1700){ 
	Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ�ȷ��ɾ����",function(id) { 
		if("yes"==id){
			radow.doEvent('deleteRow',a1700);
		}else{
			return;
		}		
	});	
}
function reShowMsg(){
	document.getElementById("a0000").value = window.parent.document.getElementById("a0000").value;
	radow.doEvent('initX');
}

function lockINFO(){
	Ext.getCmp("save").disable(); 
	Ext.getCmp("TrainAddBtn").disable(); 
	Ext.getCmp("TrainInfoGrid").getColumnModel().setHidden(1,true); 
}
var title;//���ձ�����Ϣ
function showRenderer(value, params, record,rowIndex,colIndex,ds){
	var a1700 = record.data.a1700;
	document.getElementById("a1700").value = a1700;
	//�������ñ���ķ���
	setTitle(record)
	return "<img id=\""+a1700+"\" alt=\"\" src=\"<%= request.getContextPath()%>/image/movie.png\" onclick=\"printView('"+a1700+"')\" style=\"cursor:pointer\">";
	/* return "<a href=\"javascript:deleteRow2(&quot;"+a1700+"&quot;)\">ɾ��</a>"; */
}
//���ñ�����Ϣ
function setTitle(record){
	var name = record.data.a0101;
	var start_time=record.data.start_date;
	var end_time=record.data.end_date;
	var entry_content=record.data.entry_content;
	title=name+" "+insertStr(start_time,4,".")+"--"+insertStr(end_time,4,".")+" "+entry_content;
}
function printView(a1700){
	$h.openPageModeWin('multimedia','pages.publicServantManage.Multimedia',title,1000,800,a1700,'<%=ctxPath%>');
	
}
function insertStr(soure, start, newStr){   
    return soure.slice(0, start) + newStr + soure.slice(start);
 }
</script>
