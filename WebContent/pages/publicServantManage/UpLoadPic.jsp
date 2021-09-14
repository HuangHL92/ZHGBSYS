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
		<!-- <td style="width:60px;" align="right"><label style="font-size:12px;">&nbsp;&nbsp;&nbsp;&nbsp;考察材料名称&nbsp;&nbsp;</label></td> -->
		<td>
			<div class="file-box" >
				<form action="<%=request.getContextPath()%>/servlet/UpLoadPhotoServlet" name="submitPic" id="submitPic" method="post" enctype="multipart/form-data">
					<!-- <input type='text' name='attachPhoto' id='attachPhoto' class='file_txt' />
					<input type='button' class='file_btn' value='浏览...'  style="cursor: pointer"/>
					<input type="file" name="excelFile" class="file_file" id="excelFile" size="28" onchange="formatPhoto()" /> -->
					<div><input type="file" id="attachPhoto" name="attachPhoto" title="选择照片" onchange="javascript:formatPhoto(this);" /><br/><br/></div>
					<!-- <input class="file_btn2" value="上传" onclick="formSubmit();" type="hidden"/> -->
				</form>
			</div>
		</td>
	</tr>
</table>

<script type="text/javascript">
	/**
	 * 判断上传的文件是否为照片格式
	 */
	function formatPhoto(img){
		var filePath = $(img).val();
		if("" != filePath){
			var fileType = getFileType(filePath);
			//判断上传的附件是否为图片
			if("zip"!=fileType){
				// $("#attachPhoto").val("");
				$(img).val("");
				alert("请上传zip格式文件");
			}/* else {
				//获取附件大小（单位：KB）
				var fileSize = $("#attachPhoto").files[0].size / 1024;
				// var fileSize = $(img).files[0].size / 1024;
				if(fileSize > 2048){
					// $("#attachPhoto").val("");
					$(img).val("");
					alert("图片大小不能超过2MB");
				}
			}  */
		}
	}

	/**
	 * 获取文件名后缀
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
					parent.Ext.Msg.wait("正在导入，请稍后...","系统提示");
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
			alert('请选择文件之后再做上传！');
		}
	}
</script>
