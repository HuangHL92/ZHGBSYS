<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@page import="net.sf.json.JSONArray"%>
<%@include file="/comOpenWinInit.jsp" %>

<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>�����ļ�</title>
	<odin:MDParam></odin:MDParam>
    <script src="<%=request.getContextPath()%>/basejs/jquery-ui/jquery-1.10.2.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
    <script src="<%=request.getContextPath()%>/jwjc/js/underscore.js" type="text/javascript"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/jwjc/js/common.js" charset="utf-8"></script>
<form id="formId" name="data" method="post"  action="<%=request.getContextPath()%>/DownloadServlet?method=appendixUpload" enctype="multipart/form-data">
	<table align="center" width="96%">	
		<tr>
			<td width="10"></td>
			<td width="20">
		       <iframe id="frame" name="frame" height="33px" width="280px" src="<%=request.getContextPath() %>/pages/sysorg/org/adfile2.jsp" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes"></iframe>
			</td>
			<td  height="15" >
	 		 <span  id = "CPDText"></span>
	 		</td>
			<%-- <td>
				<img src="<%=request.getContextPath()%>/images/go1.jpg" onclick="sc()">
			</td> --%>
		</tr>
	</table>
	<odin:hidden property="pid"/>
</form>
<table style="width: 100%;">
	<tr>
		<td>
			<odin:editgrid2 property="fileGrid" bbarId="pageToolBar" autoFill="false" height="387"
				 hasRightMenu="false" url="/">
				<odin:gridJsonDataModel id="name" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="agendaname"/>
					<odin:gridDataCol name="a0101"/>
					<odin:gridDataCol name="tp0118"/>
					<odin:gridDataCol name="tp0114"/>
					<odin:gridDataCol name="tp0115"/>
					<odin:gridDataCol name="sh000"/>
					<odin:gridDataCol name="meetingname"/>
					<odin:gridDataCol name="operateU" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn2 />
					<odin:gridEditColumn2 dataIndex="agendaname" width="250" editor="text" edited="false" header="��������"/>
					<odin:gridEditColumn2 dataIndex="a0101" width="80" editor="text" edited="false" header="����"/>
					<odin:gridEditColumn2 dataIndex="tp0118" width="220" editor="text" edited="false" header="����" renderer="file"/>
					<odin:gridEditColumn2 dataIndex="tp0114" width="80" editor="text" edited="false" header="��ע"/>
					<odin:gridEditColumn2 dataIndex="operateU" width="100" header="����" editor="text" edited="false" 
						 align="center" renderer="operateUod" isLast="true"/>
				</odin:gridColumnModel>
			</odin:editgrid2>
		</td>
	</tr>
</table>
<odin:hidden property="pid"/>
<script>
Ext.onReady(function() {
	document.getElementById('pid').value = document.getElementById('subWinIdBussessId2').value;
	
});

// ����ύ
function sc(){
	var pid = document.getElementById('sh000').value;
	window.frames['frame'].imp(pid);
	//frame.window.imp(pid);
}
function gg() {
	//window.close();
	//var parentWin = window.opener;
   radow.doEvent("fileGrid.dogridquery");
}
function yulan_rmb(sh000){
	document.getElementById('mainIframe').src='<%= request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.meeting.MeetingPreview&publishid=sh000@@'+sh000;
} 
//����ԭ��
function file(value, params, rs, rowIndex, colIndex, ds){
	var tp0115 = rs.get('tp0115');
	var tp0118 = rs.get('tp0118');
	
	var meetingname = rs.get('meetingname');
	fz(meetingname);
	 if(name != null && name != ''&& tp0115!=null && tp0115!=''){
		 var url=tp0115.replace(/\\/g,"/");
		return "<a href=\"javascript:downloadFile('" +url +"')\">"+tp0118+"</a>";
	} 
	

} 
//����ر�,ǰ��ҳ�� �������ݸ���
function colseWin( html ){
	//����ǰһҳ��
	if(realParent.document.getElementById("div_fujian")!=null){
		realParent.document.getElementById("div_fujian").innerHTML =html;
	}
	//window.close();
}

//����ر�,ǰ��ҳ�� �������ݸ���
function fz( meetingname ){
	//����ǰһҳ��
	if(document.getElementById("CPDText")!=null){
		document.getElementById("CPDText").innerHTML =meetingname;
	}
	//window.close();
}

// grid��������ʾ�Ĳ������
function operateUod(value, params, record,rowIndex,colIndex,ds){
	var sh000 = record.get('sh000');
	return "<a href=\"javascript:dRow('"+sh000+"')\">�ϴ�</a>&nbsp;" + 
	"&nbsp;<a href=\"javascript:deleteRow('"+rowIndex+"')\">ɾ��</a>&nbsp;";
}

// ������ذ�ť
function downloadRow(rowIndex) {
	radow.doEvent("downloadFile", rowIndex);
}

//����ϴ���ť
function dRow(sh000){
	window.frames['frame'].impd(sh000);
	//radow.doEvent("file", rowIndex);
	<%-- $h.openWin("AppendixWin","pages.meeting.CadresPlanningAllocationAppendix", "�����б�", 760, 560, sh000,"<%=request.getContextPath()%>"); --%>
}

// ����
function downloadFile(url) {
	var downfile = url;
	//w = window.open("ProblemDownServlet?method=downFile&prid=" + encodeURI(encodeURI(downfile)));
	window.location.href="ProblemDownServlet?method=downFile&prid=" + encodeURI(encodeURI(downfile));
}

// ɾ��
function deleteRow(rowIndex) {
	// ȷ��ɾ��
	$h.confirm("ϵͳ��ʾ��",'�Ƿ�ȷ��ɾ����',200,function(id) { 
		if("ok"==id){
			radow.doEvent("deleteFile", rowIndex);
			window.frames['frame'].imd();
		}else{
			return false;
		}		
	});
}
function gg() {
    radow.doEvent("fileGrid.dogridquery");
}
function restore(){
	parent.odin.ext.getCmp('grid1').store.reload();
	window.close();
}

function info(type){
	document.all('excelFile').value='';
	odin.ext.get(document.body).unmask();

		doCloseWin(type);
}

var businessData = "";
function doCloseWin(type){
	odin.ext.get(document.body).mask('����ˢ��ҳ��......', odin.msgCls);
	parent.odin.ext.getCmp('impWin').hide();
	if(businessData!="" && type==1){
		if(typeof parent.resFuncImpExcel != 'undefined'){
			parent.resFuncImpExcel(odin.ext.decode(businessData));
		}
	}
}
</script>