<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<script src="<%=request.getContextPath()%>/pages/xbrm2/js/jquery-1.4.4.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<%@ include file="/comOpenWinInit2.jsp" %>
<link href="<%=request.getContextPath()%>/jqueryUpload/uploadify.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath()%>/jqueryUpload/jquery.uploadify.js" type="text/javascript"></script>
<div id="addTitleContent">
<table width="100%">
		<tr>
	    	<%-- <odin:textEdit property="titlename"   label="��������" required="true"></odin:textEdit> --%>
	    	<odin:textarea property="titlename"  label="��������"  cols="80" rows="4" maxlength="800" required="false" ></odin:textarea>
		</tr>
		<tr>
			<odin:textarea property="titleBz"  label="����˵��"  cols="80" rows="4" maxlength="800" required="false" ></odin:textarea>
		</tr>
		<tr>
			<odin:select2 property="titleType" label="��������" data="['1', 'һ������'],['2', '��������'],['3', '��������']"></odin:select2>
			<%-- <odin:textEdit property="titleBz" label="����˵��" ></odin:textEdit> --%>
			
		</tr>
		<tr>
			<odin:textEdit property="sortid" label="����" ></odin:textEdit>
			
		</tr>
		<%-- <tr>
			<td  colspan="2" align="left">
				<tags:JUpload property="titlefile" uploadLimit="1" label="ѡ���ļ�" fileTypeDesc="�����ļ�"   fileSizeLimit="20MB" fileTypeExts="*.*" />
			</td>
			<td>
				<odin:button text="�����ϴ�" property="fujSave" handler="fujSave" ></odin:button>
			</td>
		</tr> --%>
</table>
<form id="formId" name="data" method="post"  action="<%=request.getContextPath()%>/DownloadServlet?method=appendixUpload" enctype="multipart/form-data">
	<table align="center" width="96%">	
		<tr>
			<td width="50"></td>
			<td>
		       <iframe id="frame" name="frame" height="33px" width="330px" src="<%=request.getContextPath() %>/pages/sysorg/org/adfile2.jsp" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes"></iframe>
			</td>
			<td>
				<%-- <img src="<%=request.getContextPath()%>/images/go1.jpg" onclick="sc()"> --%>
				<odin:button text="�ύ" property="sc" handler="sc" ></odin:button>
			</td>
			<td width="50"></td>
		</tr>
	</table>
	<odin:hidden property="pid"/>
</form>
<table width="60%">
	<!-- <tr>
	<td  height="15" align="center">
	  <span style="text-align: center;display:block;" id = "div_fujian"></span>
	  </td>
	</tr> -->
	<tr>
		<td>
			<odin:editgrid2 property="fileGrid" bbarId="pageToolBar" autoFill="false" height="57"
				 hasRightMenu="false" url="/">
				<odin:gridJsonDataModel id="name" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="add00"/>
					<odin:gridDataCol name="fileurl"/>
					<odin:gridDataCol name="filename" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn2 />
					<odin:gridEditColumn2 dataIndex="filename" width="500" editor="text" edited="false" header="��������" renderer="file"/>
					<odin:gridEditColumn2 dataIndex="operateU" width="100" header="����" editor="text" edited="false" 
						menuDisabled="true" sortable="false" align="center" renderer="operateUod" isLast="true"/>
				</odin:gridColumnModel>
			</odin:editgrid2>
		</td>
	</tr>
	
</table>	
</div>
<odin:toolBar property="btnToolBar">
	<odin:fill/>
	<odin:buttonForToolBar id="btnSave" text="����"  icon="images/save.gif" cls="x-btn-text-icon" isLast="true"/>
</odin:toolBar>
<odin:panel contentEl="addTitleContent" property="addTitlePanel" topBarId="btnToolBar"></odin:panel>
<odin:hidden property="publishid"/>
<odin:hidden property="meetingid"/>
<odin:hidden property="titleid"/>
<odin:hidden property="type"/>
<odin:hidden property="titlePath"/>
<script type="text/javascript">
function saveCallBack(){
	parent.reloadSelData();
	//window.close();
}
//����ύ
function sc(){
	var pid = document.getElementById('titleid').value;
		window.frames['frame'].impd(pid);
	//frame.window.imp(pid);
}
function gg() {
    radow.doEvent("fileGrid.dogridquery");
}

Ext.onReady(function() {
	//var viewSize = Ext.getBody().getViewSize();
	document.getElementById('pid').value = document.getElementById('subWinIdBussessId2').value;
	document.getElementById('meetingid').value = parent.document.getElementById('meetingid').value;
	document.getElementById('publishid').value = parent.document.getElementById('publish_id').value;
	document.getElementById('titleid').value = parent.document.getElementById('title_id').value;
	//alert(parentParam.type);
	//document.getElementById('titleid').value = parentParam.title_id;
	document.getElementById('type').value = parentParam.type;
});
//grid��������ʾ�Ĳ������
function operateUod(value, params, record,rowIndex,colIndex,ds){
	return "<a href=\"javascript:deleteRow('"+rowIndex+"')\">ɾ��</a>&nbsp;";
}
//������ذ�ť
function downloadRow(rowIndex) {
	radow.doEvent("downloadFile", rowIndex);
}

// ����
function downloadFile(url) {
	var downfile = url;
	//w = window.open("ProblemDownServlet?method=downFile&prid=" + encodeURI(encodeURI(downfile)));
	window.location.href="ProblemDownServlet?method=downFile&prid=" + encodeURI(encodeURI(downfile));
}
function download(id){
	var personPath = document.getElementById('titlePath').value;
	//alert(personPath);
	window.location="ProblemDownServlet?method=downFile&prid="+personPath;
}
//ɾ��
function deleteRow(rowIndex) {
	// ȷ��ɾ��
	$h.confirm("ϵͳ��ʾ��",'�Ƿ�ȷ��ɾ����',200,function(id) { 
		if("ok"==id){
			radow.doEvent("deleteFi", rowIndex);
			window.frames['frame'].imd();
		}else{
			return false;
		}		
	});
}
function file(value, params, rs, rowIndex, colIndex, ds){
	var wj05 = rs.get('fileurl');
	var name = rs.get('filename');
	var url=wj05.replace(/\\/g,"/");
	debugger;
	 if(name != null && name != ''){
		return "<a href=\"javascript:downloadFile('" +url +"')\">"+name+"</a>";
	} 
	

} 
//�鿴�������
function fujSave(){
	var titleid =  document.getElementById('titleid').value ;
	/* if (publish_id == null || publish_id =="" ) {
		odin.alert("����ѡ��,���ύ������");
		return;
	} */
	$h.openWin("AppendixWin","pages.meeting.CadresPlanningAllocationAppendix", "�����б�", 760, 560, titleid,"<%=request.getContextPath()%>");
}
</script>
