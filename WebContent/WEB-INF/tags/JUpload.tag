<%@tag import="com.insigma.odin.framework.sys.SysfunctionManager"%>
<%@ tag pageEncoding="GBK" body-content="empty" small-icon=""
	display-name="jquery上传组件" description="jquery上传组件"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ attribute name="label" required="false" description="上传按钮名字"%>
<%@ attribute name="property" required="true" description="" %>

<%@ attribute name="fileTypeExts" required="false" description="上传文件类型(*.*)，多个类型分号隔开"%>
<%@ attribute name="fileTypeDesc" required="false" description="上传文件描述"%>
<%@ attribute name="uploadLimit" required="false" description="上传文件数量限制"%>
<%@ attribute name="fileSizeLimit" required="false" description="上传文件大小（可直接加单位）"%>



<%
String pageModel = request.getParameter("pageModel");
label = label==null?"":label;
fileTypeExts = fileTypeExts==null?"*.*":fileTypeExts;
fileTypeDesc = fileTypeDesc==null?"所有文件":fileTypeDesc;
uploadLimit = uploadLimit==null?"0":uploadLimit;
fileSizeLimit = fileSizeLimit==null?"0":fileSizeLimit;

%>

<div style="width:100%;height: 100%">
        <%--用来作为文件队列区域--%>
    <input type="file" name="<%=property %>" id="<%=property %>" />
    <div id="<%=property %>fileQueue" style="float: left;">
    </div>
    
   <!--  <p>
   <a href="javascript:$('#<%=property %>').uploadify('upload','*')">上传</a> 
        <a href="javascript:$('#uploadify').uploadify('upload','*')">上传</a>| 
        <a href="javascript:$('#uploadify').uploadify('cancel','*')">取消上传</a>
    </p> -->
    
</div>

<script>
$(document).ready(function() {  
    $("#<%=property %>").uploadify({  
    	'method':'post',
        'swf'       : '<%=request.getContextPath()%>/jqueryUpload/uploadify.swf',
        'uploader'  : '<%=request.getContextPath()%>/servlet/JUpload',    
        'folder'         : '/upload',  
        'queueID'        : '<%=property %>fileQueue',
        'cancelImg'      : 'jqueryUpload/uploadify-cancel.png',
        'buttonText'     : '<%=label%>',
        'fileSizeLimit'  : '<%=fileSizeLimit%>',
        'auto'           : false, //设置true 自动上传 设置false还需要手动点击按钮 
        'multi'          : true,  
        'wmode'          : 'transparent',  
        'uploadLimit'    : <%=uploadLimit%>,  
        'fileTypeExts'        : '<%=fileTypeExts%>',  
        'fileTypeDesc'       : '<%=fileTypeDesc%>',
        'pageModel'    :'<%=pageModel%>',
        'onUploadStart'  :function(file){
        	var obj = $helper().getFormInfo('commForm');
        	obj['uploadType'] = 'UPLOAD';
            obj['pageModel'] = '<%=pageModel%>';
            //console.log(obj)
            $("#<%=property %>").uploadify("settings","formData",obj );    
         },
        'onQueueComplete' : function(queueData) {
        	//console.log(queueData);
        	/* if(onQueueComplete){
        		onQueueComplete(queueData.uploadsSuccessful);
        	} */
            //alert(queueData.uploadsSuccessful + ' 上传成功.');
            /* $.ajax({
            	url:"test.jsp",
            	async:true,
            	success:function(){
            		//alert("这里是二次ajax请求！");
            		//$("#test").css("color",'red');
            	}
            }); */
        },
        'onUploadSuccess': function(file, data, response) {
        	var jsondata = eval("("+data+")");
        	var insId = '<%=property %>';
        	$('#'+file.id).find('.cancel').html("<a href='javascript:void(0)' onclick=\"$('#"+insId.replace(/'/g,"\\'")+"').uploadify('cancelByRemoveRemoteFile', '"+file.name.replace(/'/g,"\\'")+"', '"+insId.replace(/'/g,"\\'")+"','"+file.id.replace(/'/g,"\\'")+"','"+jsondata.file_pk.replace(/'/g,"\\'")+"')\">X</a>");
        	//console.log(file);
        	//console.log(response);
        	if(typeof(onUploadSuccess) != "undefined"){
        		onUploadSuccess(file, jsondata, response);
        	}
        }
    });  
});  
 </script>
