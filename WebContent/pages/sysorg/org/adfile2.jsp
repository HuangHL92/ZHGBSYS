<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<head>

<odin:head />
<script src="<%=request.getContextPath()%>/radow/corejs/radow.js"></script>
<script src="<%=request.getContextPath()%>/radow/corejs/radow.PageModeEngine.js"></script>
<script src="<%=request.getContextPath()%>/radow/corejs/radow.util.js"></script>
<script src="<%=request.getContextPath()%>/radow/corejs/radow.renderer.js"></script>
<script src="<%=request.getContextPath()%>/radow/corejs/radow.business.js"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js" type="text/javascript"></script>

<meta http-equiv="Content-Type" content="text/html; charset=GBK">
</head>
<body>
<form name="data" id="data" method="post"  enctype="multipart/form-data" >
	<div id="groupbox">
		<table>
			<tr>
			<odin:textEdit property="abc" inputType="file" label="&nbsp&nbsp�����ϴ�" size="30"></odin:textEdit> 
			</tr>
		</table>
					
				
	</div>	
	</form>
</body>
<script type="text/javascript">
function imp(oid){
	var file=document.getElementById('abc').value;
	if(file !=""&&oid!=""){
		var path = '<%=request.getContextPath()%>/YearCheckServlet?method=adsc2&oid='+oid;
		odin.ext.Ajax.request({
			url:path,
			isUpload:true,
			method:'post',
			fileUpload:true,
			form:'data',
			success:function(data){
				debugger;
					var result = data.responseText;
					if(result=="success"){
						//parent.radow.doEvent("file",oid);
						document.getElementById("abc").value="";
					/* 	parent.parentgridquery(); */
					} else{
						parent.odin.alert(result);
					}
						
				},
			failure : function(){
				alert("�ϴ�����ʧ��");
			}
		});
	}else{
		document.getElementById("abc").value="";
		/* parent.radow.doEvent("appendfile"); */
		//parent.parentgridquery();
	}
}
function impd(oid){
	var file=document.getElementById('abc').value;
	if(file !=""&&oid!=""){
		var path = '<%=request.getContextPath()%>/YearCheckServlet?method=adsc3&oid='+oid;
		odin.ext.Ajax.request({
			url:path,
			isUpload:true,
			method:'post',
			fileUpload:true,
			form:'data',
			success:function(data){
				debugger;
					var result = data.responseText;
					if(result=="success"){
						parent.radow.doEvent("file",oid);
						document.getElementById("abc").value="";
					/* 	parent.parentgridquery(); */
					} else{
						parent.odin.alert(result);
					}
						
				},
			failure : function(){
				alert("�ϴ�����ʧ��");
			}
		});
	}else{
		document.getElementById("abc").value="";
		/* parent.radow.doEvent("appendfile"); */
		//parent.parentgridquery();
	}
}

function impp(oid){
	var file=document.getElementById('abc').value;
	if(file !=""&&oid!=""){
		var path = '<%=request.getContextPath()%>/YearCheckServlet?method=adsc5&oid='+oid;
		odin.ext.Ajax.request({
			url:path,
			isUpload:true,
			method:'post',
			fileUpload:true,
			form:'data',
			success:function(data){
				debugger;
					var oid = data.responseText;
					if(oid!=""){
						parent.radow.doEvent("file",oid);
						document.getElementById("abc").value="";
					/* 	parent.parentgridquery(); */
					} else{
						parent.odin.alert(result);
					}
						
				},
			failure : function(){
				alert("�ϴ�����ʧ��");
			}
		});
	}else{
		document.getElementById("abc").value="";
		/* parent.radow.doEvent("appendfile"); */
		//parent.parentgridquery();
	}
}
function impdx(oid){
	var file=document.getElementById('abc').value;
	if(file !=""&&oid!=""){
		var path = '<%=request.getContextPath()%>/YearCheckServlet?method=adsc4&oid='+oid;
		odin.ext.Ajax.request({
			url:path,
			isUpload:true,
			method:'post',
			fileUpload:true,
			form:'data',
			success:function(data){
				debugger;
					var result = data.responseText;
					if(result=="success"){
						parent.radow.doEvent("file",oid);
						document.getElementById("abc").value="";
					/* 	parent.parentgridquery(); */
					} else{
						document.getElementById("abc").value="";
					}
						
				},
			failure : function(){
				alert("�ϴ�����ʧ��");
			}
		});
	}else{
		document.getElementById("abc").value="";
		/* parent.radow.doEvent("appendfile"); */
		//parent.parentgridquery();
	}
}	


function imd(){
	document.getElementById("abc").value="";
}
function uploadAppendixFile(oid) {
	/* 
	var v = param.split(",");
	var fid = v[0];
	var m0000 = v[1];	
	var type = v[2];//���θ����ϴ����   01���θ���   02���˸���
	 */
    /* var file = document.getElementById('filedata').value;
	alert(file);
    if ("" == file) {
		parent.odin.alert("��ѡ���ļ���");
		return;
    }
 	// ���һ����/����λ��
	var index = file.lastIndexOf("\\");
	// �ļ���
    var fileName = file.substring(index + 1, file.length);
    if (fileName.length > 100) {
    	parent.odin.alert("�ļ����Ƴ���100�֣���ǰ�ļ����Ƴ���Ϊ" + fileName.length);
    	return;
	} */
	
	var file=document.getElementById('abc').value;
	if(file !=""){
    var path = '<%=request.getContextPath()%>/YearCheckServlet?method=adsc2&oid='+oid;
	odin.ext.Ajax.request({
        url: path,
        isUpload: true,
        method: 'post',
        fileUpload: true,
        form: 'data',
        success: function (data) {
            var result = data.responseText;
            if(result=="success"){
				parent.radow.doEvent("personSave");
			}else{
				var msg = result.message;
				parent.odin.alert(msg);
			}
        },
        failure: function () {
        	parent.odin.alert("ϵͳ��ʾ��", "�ļ��ϴ�ʧ��");
        }
    });
	}else{
		alert("��ѡ��һ���ļ���");
		/* parent.radow.doEvent("appendfile"); */
		//parent.parentgridquery();
	}
}


</script>