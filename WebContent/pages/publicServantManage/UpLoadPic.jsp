<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<script type="text/javascript"src="<%=request.getContextPath()%>/basejs/ext/adapter/ext/ext-base.js"></script>
<script src="<%=request.getContextPath()%>/basejs/ext/ext-all.js"> </script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script src="<%=request.getContextPath()%>/basejs/odin.js"> </script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/odin.css"/>
    
<style type="text/css">
    #attachPhoto {
		width: 400px;
		height: 20px;
	}
	.file_txt{ 
		height:22px; 
		border:1px solid #b5b8c8; 
		width:342px;
		margin-bottom: 3px;
	}
	.file_file{ 
		position:absolute; 
		right:-3px; 
		height:25px;
		width:60px;
		top:0;
		filter:alpha(opacity:0);
		opacity: 0;
	}
	.file-box{ 
		position:relative;
		width:420px;
		margin-left: -3px;
		}
	.file_btn{ 
		background-color:#b5b8c8; 
		border:1px solid #b5b8c8;
		height:24px;
		width:60px;
		margin-bottom:3px;
		margin-right:8px;
		cursor:pointer;
	}
		/* .file-box{ position:relative;width:320px}
		.file_txt{ height:22px; border:1px solid #b5b8c8; width:180px;}
		.file_btn{ background-color:#b5b8c8; border:1px solid #b5b8c8;height:24px; width:60px;cursor:pointer;}
		.file_file{ position:absolute; right:85px; height:25px;width:60px;top:0;filter:alpha(opacity:0);opacity: 0;}/* filter:alpha(opacity:0);opacity: 0; right:80px; height:24px;width:260px */ */
</style>

<odin:hidden property="path_save"/>
<odin:hidden property="attachPhoto_path"/>
<table style="cellpadding:0px;cellspacing:0px;" >
	<tr>
		<!-- <td style="width:60px;" align="right"><label style="font-size:12px;">&nbsp;&nbsp;&nbsp;&nbsp;�����������&nbsp;&nbsp;</label></td> -->
		<td>
			<div class="file-box" >
				<form action="<%=request.getContextPath()%>/servlet/UpLoadPhotoServlet" name="submitPic" id="submitPic" method="post" enctype="multipart/form-data">
					<!-- <input type='text' name='attachPhoto' id='attachPhoto' class='file_txt' />
					<input type='button' class='file_btn' value='���...'  style="cursor: pointer"/>
					<input type="file" name="excelFile" class="file_file" id="excelFile" size="28" onchange="formatPhoto()" /> -->
					<div><input type="file" id="attachPhoto" name="attachPhoto" title="ѡ����Ƭ" onchange="javascript:formatPhoto(this);" /><br/><br/></div>
					<!-- <input class="file_btn2" value="�ϴ�" onclick="formSubmit();" type="hidden"/> -->
				</form>
			</div>
		</td>
	</tr>
</table>

<script type="text/javascript">
	/**
	 * �ж��ϴ����ļ��Ƿ�Ϊ��Ƭ��ʽ
	 */
	function formatPhoto(img){
		var filePath = $(img).val();
		if("" != filePath){
			var fileType = getFileType(filePath);
			//�ж��ϴ��ĸ����Ƿ�ΪͼƬ
			if("zip"!=fileType){
				// $("#attachPhoto").val("");
				$(img).val("");
				alert("���ϴ�zip��ʽ�ļ�");
			}/* else {
				//��ȡ������С����λ��KB��
				var fileSize = $("#attachPhoto").files[0].size / 1024;
				// var fileSize = $(img).files[0].size / 1024;
				if(fileSize > 2048){
					// $("#attachPhoto").val("");
					$(img).val("");
					alert("ͼƬ��С���ܳ���2MB");
				}
			}  */
		}
	}

	/**
	 * ��ȡ�ļ�����׺
	 */
	function getFileType(filePath){
		var startIndex = filePath.lastIndexOf(".");
		if(startIndex != -1) {
			return filePath.substring(startIndex+1, filePath.length).toLowerCase();
		} else {
			return "";
		}
	}
	
	function formSubmit(){
		//var fileName = document.getElementById('attachPhoto').value;
		//var fileType = parent.document.getElementById('fileType').value;
		var photodate='';
		if(parent.document.getElementById('updateTime')){
			photodate = parent.document.getElementById('updateTime').value;
		}
		if(document.getElementById('attachPhoto').value!=""){
					parent.Ext.Msg.wait("���ڵ��룬���Ժ�...","ϵͳ��ʾ");
					Ext.Ajax.request({
				   		timeout: 900000,
				   		url: "<%=request.getContextPath()%>/UpLoadPhotoServlet?photodate="+photodate,
				   		isUpload : true,  
				        form : "submitPic",
				   		method :"post",
				   		success: function(response) {
				   			parent.Ext.Msg.hide();
				      		eval(response.responseText);
				           }
				      });
		}else{
			alert('��ѡ���ļ�֮�������ϴ���');
		}
	}
</script>
