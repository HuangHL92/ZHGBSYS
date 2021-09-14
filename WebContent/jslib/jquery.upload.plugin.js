/**
 * 上传文件插件
 */
var UploadPlugin = {
	initUpload : function(obj,ref){
		var dialog = "<div id='fileDialog'></div>";
		$(obj).parent().remove("#fileDialog").append(dialog);
		var contentUrl = System.rootPath + "/common/upload-plugin.jsp";
		$("#fileDialog").dialog({
			title:$(obj).val(),
			width:400,
			height:100,
			shadow:true,
			modal :true,
			href : contentUrl,
			onOpen : function () {
				$("#fileDialog").data("fileId",$("#"+ref).val());
			},
			onClose:function(){
				var fileId = $("#fileDialog").data("fileId");
				$("#"+ref).val(fileId);
				$("#fileDialog").dialog('destroy',true);
			},
			onLoad : function(){
			}
		});
	},
	ChageFile : function (sender){
		var typeReg = /.exe|.bat/i;
		if($(sender).val().match(typeReg)){ //忽略大小写
			$.messager.alert("错误提醒","格式无效!","error");
			return false; 
		}
		$("#fileDialog").data("changed",true);
	},
	startUpload : function(formid){
		var changed = $("#fileDialog").data("changed");
		if(changed == true){
			System.openLoadMask("#fileDialog", "正在上传...");
			$("#browseBtn").linkbutton('disable');
			$("#clearBtn").linkbutton('disable');
			$('#'+formid).submit();
		}else{
			$.messager.alert('提示','请选择文件!','info');
		}
	},
	callback : function(fileId){
		$("#fileDialog").data("fileId",fileId);
		System.closeLoadMask("#fileDialog");
		//$("#browseBtn").linkbutton('enable');
		//$("#clearBtn").linkbutton('enable');
		$("#fileDialog").dialog('close');
	},
	clearData : function(){
		$("#fileDialog").data("fileId","");
	},
	download:function(ref){
		var id = $("#"+ref).val();
		if(id == ""){
			$.messager.alert("提示","无文件!","info");
		}else{
			$("#hiddenIfr").attr("src",System.rootPath+"/lob/photo!downloadBinary.action?id="+id);
		}
	}
};