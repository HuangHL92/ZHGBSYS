<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<%@page import="net.sf.json.JSONArray"%>

<%
	String flag=request.getParameter("flag");
	String uuid = request.getParameter("uuid");
	String uname = new String(request.getParameter("uname").getBytes("iso-8859-1"),"gbk");;
	
%>
<html>
<head>
<script src="<sicp3:rewrite forward='md5'/>"> </script>
<script src="<sicp3:rewrite forward='globals'/>"> </script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/ext/adapter/ext/ext-base.js"></script>
<script src="<%=request.getContextPath()%>/basejs/ext/ext-all.js"> </script>
<script src="<%=request.getContextPath()%>/basejs/odin.js"> </script>
<script src="<%=request.getContextPath()%>/basejs/signature.js"> </script>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>�����ļ�</title>
<odin:head/>
<odin:MDParam></odin:MDParam>

<link rel="stylesheet" type="text/css" href="../../resources/css/ext-all.css" />

    <!-- GC -->
 	<!-- LIBS -->
 	<script type="text/javascript" src="../../adapter/ext/ext-base.js"></script>
 	<!-- ENDLIBS -->

    <script type="text/javascript" src="../../ext-all.js"></script>

    <script type="text/javascript" src="basejs/ColumnNodeUI.js"></script>

    <!-- Common Styles for the examples -->
    <link rel="stylesheet" type="text/css" href="../examples.css" />

    <link rel="stylesheet" type="text/css" href="basejs/ext/ux/css/column-tree.css" />
<script  type="text/javascript" src="basejs/jquery.js"></script>
<script  type="text/javascript" src="basejs/jquery.min.js"></script>


</head>

<body style="overflow-y:hidden;overflow-x:hidden">

<odin:base>

<form name="excelForm" id="excelForm" method="post"  action="<%=request.getContextPath()%>/UploadFileServlet?method=uploadAttachment" enctype="multipart/form-data" >	
<odin:hidden  property="valueimp" />
<odin:hidden  property="filevalue" />
<odin:hidden  property="uuid" value="<%=uuid %>" />
<odin:hidden  property="flag" value="<%=flag %>"/>
<odin:hidden  property="uname" value="<%=uname %>"/>

<table width="96%" id="v_standard2">
	<% for(Integer i=1;i<=1;i++){
			String file_id = "fileid_"+i;
			String text_id = "textid_"+i;
		%>
	<tr>
		<td style="{font :12px}" align="right">�ļ�ѡ��</td>
		<td ><div class="x-form-item"><input type="file" id="<%=file_id %>" name="<%=file_id %>" size="25" onchange="return FileUpload_onselect('<%=file_id %>')" /></div></td>
		<td style="{font :12px}" align="right">��ע</td>
		<td ><div class="x-form-item"><input type="text" id="<%=text_id %>" name="<%=text_id %>" size="20" maxlength="25" /></div></td>
	</tr>
	<%} %>
	<tr id="targetNodeTR">
		<td colspan = "2" align="center">
			<input type="button" value="����" id="addFileBtn"  onclick="addFile()"/>
		</td>
		<td align="center" colspan = "2">
			<input type="button" value="�ϴ�����" id="impwBtn" onclick="formupload()" />
		</td>
	</tr>
</table>

<div id="moreRow" style="display: none;">
	<odin:hidden property="rowLength" value="1" />
	<table id="tableMoreRow">
		<% for(Integer i=2;i<=20;i++){ 
			String file_id = "fileid_"+i;
			String text_id = "textid_"+i;
			%>
		<tr>
			<td style="{font :12px}" align="right">�ļ�ѡ��  </td>
			<td ><div class="x-form-item"><input type="file" id="<%=file_id %>" name="<%=file_id %>" size="25" onchange="return FileUpload_onselect('<%=file_id %>')" /></div></td>
			<td style="{font :12px}" align="right">��ע</td>
			<td ><div class="x-form-item"><input type="text" id="<%=text_id %>" name="<%=text_id %>" size="20" maxlength="25"/></div></td>
		</tr>
		<%} %>
	</table>
</div>
	
</form>

<script>

	//���Ӹ���ѡ����
	var tableMoreRow = document.getElementById('tableMoreRow');
	var trs = tableMoreRow.lastChild.childNodes;
	var Tindex = 0;
	function addFile(){
		
		//�ʱ���ֻ���ϴ�һ������
		/* var flag = odin.ext.get('flag').getValue();
		if(flag == '0' || flag == '2'){
			alert("�ʱ���ֻ���ϴ�һ������.");
			return;
		} */
		var val = document.getElementById('filevalue').value;
		if(val == ""){
			alert("����ѡ��Ҫ�ϴ����ļ���");
			return;
		}
		if(Tindex==10)return;
		var table = document.getElementById('v_standard2');
		
		var targetNode = document.getElementById("targetNodeTR");//table.lastChild.lastChild;
		
		var newNode = trs[0];
		if(!newNode.innerHTML){
			newNode = trs[Tindex];
		}
		table.lastChild.insertBefore(newNode, targetNode);
		
		var rowLength = document.getElementById('rowLength');
		rowLength.value=parseInt(rowLength.value)+1+'';
		Tindex++;
		document.getElementById('filevalue').value = "";
	}
	
	//�ϴ�����
	function formupload(){
		if(document.getElementById('fileid_1').value!=""){
			var v = odin.ext.get('valueimp').getValue();
			if(v=='1'){
				alert("����jpg��ʽ�ļ��������ϴ�");
				return;
			}
			
			var flag = odin.ext.get('flag').getValue();
			var uname = odin.ext.get('uname').getValue();	
			var uuid = odin.ext.get('uuid').getValue();
			
			odin.ext.get(document.body).mask('�����ϴ����ݲ�������......', odin.msgCls);
			odin.ext.Ajax.request({
				url:'<%=request.getContextPath()%>/UploadFileServlet?method=uploadAttachment&flag='+flag+"&uuid="+uuid+"&uname="+uname,
				isUpload:true,
				form:'excelForm',
				success:function(){
					parent.radow.doEvent('reload',uuid+"@"+flag);
				}
			});
			parent.odin.ext.getCmp('simpleExpWin').hide();
		}else{
			odin.info('��ѡ���ļ�֮���������봦��');
		}
	}

/** �״������б����ݿ�ʼ */
Ext.onReady(function(){
});

function FileUpload_onselect(filename){
    var path=document.getElementById(filename).value;
    document.getElementById('filevalue').value = path;
   	var photoExt=path.substr(path.lastIndexOf(".")).toLowerCase();//����ļ���׺��
	//if(photoExt!='.jpg'){
	//	document.getElementById("valueimp").value="1";
	//	alert("���ϴ���׺��Ϊjpg��ͼƬ!");
	//	return false;
	//}else{
	//	document.getElementById("valueimp").value="0";
	//}
}
</script>
</odin:base>
<odin:response onSuccess="doSuccess"/>
</body>
</html>