// 选择文件后立即上传，显示服务器端图片进行裁剪
var ImagePlugin = {
	initUpload : function (obj,ref,dbtype){
		var dialog = "<div id='fileDialog'></div>";
		$(obj).parent().remove("#fileDialog").append(dialog);
		var contentUrl = System.rootPath + "/common/upload-iframe2.jsp?dbtype="+dbtype;
		$("#fileDialog").dialog({
			title:$(obj).val(),
			width:420,
			height:360,
			shadow:true,
			modal :true,
			closed: true,
			href : contentUrl,
			onOpen : function () {
			},
			onClose:function(){
				// 记录修改过的blob字段
				var flag = $("#fileDialog").data('flag');
				if(flag == true){
					var serverPath = $("#fileDialog").data("serverPath");
					$(obj).data("serverPath",serverPath);
					$("#"+ref).val(serverPath);
					var blobUpdateFields = $("#sys_blobUpdateFields").data("fields");
					if(!blobUpdateFields){
						blobUpdateFields = [];
					}
					blobUpdateFields.push(ref);
					$("#sys_blobUpdateFields").data("fields",blobUpdateFields);
				}
				$("#fileDialog").dialog('destroy',true);
			},
			onLoad : function(){
				if(dbtype == '60'){
					$("#preview_box").hide();
				}else{				
					$("#preview_box").show();
				}
				//$("#uploadFile").css({
	    		//	"top":"140px",
	    		//	"left":0,
	    		//	"z-index":"1000"});
				var serverPath = $(obj).data("serverPath");
				var id = $("#"+ref).val();
				if(serverPath && serverPath!=''){
					ImagePlugin.loadFile(System.rootPath+"/uploadfile/"+serverPath,dbtype,90,120);
				}
				/*
				else if(id != ""){
					var url = System.rootPath+"/lob/photo!getById.action?id="+id;
					if(dbtype == '60'){
						url = System.rootPath+"/lob/photo!getEmbedById.action?id="+id;
					}
					ImagePlugin.loadFile(url,dbtype,90,120);
				}
				*/
				else {
					if (id != "") {
						var url = System.rootPath+"/lob/photo!getById.action?id="+id;
						if(dbtype == '60'){
							url = System.rootPath+"/lob/photo!getEmbedById.action?id="+id;
						}
						ImagePlugin.loadFile(url,dbtype,90,120);
					}
					else {
						if (dbtype != '60') {
							var url = System.rootPath+"/lob/photo!getById.action?id=-1";		// 如果没有照片，我们也必须显示缺省照片，by YZQ on 2012/08/25				
							ImagePlugin.loadFile(url,dbtype,90,120);
						}
					}
					
				}
			}
		});
		
		$("#fileDialog").dialog('open');
	},
	loadFile : function (path,dbtype,w,h){
		if(dbtype == 60){
			/*if($.browser.msie){
				$("#preview_madia").empty().append("<embed id='preview' type='application/x-mplayer2'  autostart='true'  src='"+path+"' width='90%' height='90%' loop='false'></embed>");
			}else{
				$("#preview_madia").empty().append("<video controls='controls' id='preview' autoplay='true'  src='"+path+"' width='90%' height='90%' loop='false' />");
			}*/
			if(navigator.userAgent.indexOf('Chrome')!=-1){
				$("#preview_madia").empty().append("<video controls='controls' id='preview' autoplay='true'  src='"+path+"' width='90%' height='90%' loop='false' />");
			}else{
				$("#preview_madia").empty().append("<embed id='preview' type='application/x-mplayer2'  autostart='true'  src='"+path+"' width='90%' height='90%' loop='false'></embed>");
			}
		}else{
			var j = $("#preview").data('Jcrop');
			if(j){
				j.destroy();
			}
			$("#preview").attr("src",path);
			// 根据原始图像比例，缩放显示，by YZQ on 2015/04/09
			var orgH = h;
			h = h > 220 ? 220 : h;
			var r = (h * 1.0) / orgH;
			w = parseInt(w * r);
			//alert(w + "-" + h);
			//w = w > 215 ? 215 : w;
			//h = h > 250 ? 250 : h;
			$("#preview").css({
				width:w,
				height:h
			});
			$("#crop_preview").attr("src",path);
			$("#crop_preview").css({
				width:90,
				height:120
			});
			ImagePlugin.initJcrop();// 初始化剪裁
		}
	},
	destoryUpload : function (flag) {	
		$("#fileDialog").data('flag',flag);
		$("#fileDialog").dialog('close');
	},
	ChageFile : function (sender,dbtype,formid){
		var typeReg = /.jpg|.gif|.png|.bmp/i;
		if(dbtype == 60){
			typeReg = /.avi|.wmv|.rmvb|.mpg|.3gp|.mp4|.mpeg|.mp3|.swf/i;
		}
		if(!$(sender).val().match(typeReg)){ //忽略大小写
			$.messager.alert("错误提醒","格式无效!","error");
			return false; 
		}
		$("#dbtype").data("changed",true);
		ImagePlugin.startUpload(formid);
	},
	startUpload : function (formid){
		var changed = $("#dbtype").data("changed");
		if(changed == true){
			System.openLoadMask("#fileDialog", "正在上传...");
			$("#browseBtn").linkbutton('disable');
			$("#btnOk").linkbutton('disable');
			$("#btnCancel").linkbutton('disable');
			$('#'+formid).submit();
		}else{
			$.messager.alert('提示','请选择文件!','info');
		}
	},
	callback : function(serverPath,w,h){
		$("#fileDialog").data("serverPath",serverPath);
		System.closeLoadMask("#fileDialog");
		var dbtype = $("#dbtype").val();
		ImagePlugin.loadFile(System.rootPath+"/uploadfile/"+serverPath,dbtype,w,h);
		$("#browseBtn").linkbutton('enable');
		$("#btnOk").linkbutton('enable');
		$("#btnCancel").linkbutton('enable');
	},
	initJcrop : function (){//简单的事件处理程序，响应自onChange,onSelect事件，按照上面的Jcrop调用
		$.Jcrop("#preview",{
			onChange:ImagePlugin.showPreview,
			onSelect:ImagePlugin.showPreview,
			aspectRatio:3.0/4.0
		});	
		//$.Jcrop('#preview',{
		//	setSelect: [0,0,90,120]
		//});
		$("#x").val(0);
		$("#y").val(0);
		$("#w").val(0);
		$("#h").val(0);
	},
	showPreview :function (coords){
		if(parseInt(coords.w) > 0){
			//计算预览区域图片缩放的比例，通过计算显示区域的宽度(与高度)与剪裁的宽度(与高度)之比得到
			var rx = $("#preview_box").width() / coords.w; 
			var ry = $("#preview_box").height() / coords.h;
			var w = Math.round(rx * $("#preview").width());
			var h = Math.round(rx * $("#preview").height());
			//通过比例值控制图片的样式与显示
			$("#crop_preview").css({
				width:w + "px",	//预览图片宽度为计算比例值与原图片宽度的乘积
				height:h + "px",	//预览图片高度为计算比例值与原图片高度的乘积
				marginLeft:"-" + Math.round(rx * coords.x) + "px",
				marginTop:"-" + Math.round(ry * coords.y) + "px"
			});
			$("#x").val(coords.x);
			$("#y").val(coords.y);
			$("#w").val(coords.w);
			$("#h").val(coords.h);
		}
	},
	cutImage : function (){
		var changed = $("#dbtype").data("changed");
		if(changed == true){
			var dbtype = $("#dbtype").val();
			if(dbtype == 60){
				ImagePlugin.destoryUpload(true);
			}else{// 裁剪图片。
				if($("#w").val() == '0' || $("#h").val()=='0'){
					ImagePlugin.destoryUpload(true);
				}else{
					System.openLoadMask("#fileDialog", "正在上传裁剪区域图片...");
					$.ajax({
						url:System.rootPath+'/lob/photo!cutImage.action',
						data:{'x':$('#x').val(),'y':$('#y').val(),'w':$('#w').val(),'h':$('#h').val(),'uploadFileFileName':$("#fileDialog").data("serverPath")},
						success : function(filename){
							$("#fileDialog").data("serverPath",filename);
							ImagePlugin.destoryUpload(true);
						},
						error : function (){
							$.messager.alert('错误','网络错误，请稍后在试','error');
						},
						complete : function (){
							System.closeLoadMask("#fileDialog");
						}
					});
				}
			}
		}else{
			$.messager.alert('提示','请选择文件!','info');
		}
	}
};