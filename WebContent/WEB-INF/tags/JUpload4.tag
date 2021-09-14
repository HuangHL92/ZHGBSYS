<%@tag import="com.insigma.odin.framework.sys.SysfunctionManager"%>
<%@ tag pageEncoding="GBK" body-content="empty" small-icon=""
	display-name="jquery上传组件" description="jquery上传组件"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ attribute name="label" required="false" description="上传按钮名字"%>
<%@ attribute name="property" required="true" description="" %>
<%@ attribute name="colspan" required="true"  description="一行中所占的单元格个数" %>
<%@ attribute name="labelTdcls" required="false"  description="按钮所在的td样式" %>
<%@ attribute name="width" required="false"  description="按钮宽度" %>

<%@ attribute name="fileTypeExts" required="false" description="上传文件类型(*.*)，多个类型分号隔开"%>
<%@ attribute name="fileTypeDesc" required="false" description="上传文件描述"%>
<%@ attribute name="uploadLimit" required="false" description="上传文件数量限制"%>
<%@ attribute name="fileSizeLimit" required="false" description="上传文件大小（可直接加单位）"%>
<%@ attribute name="onSelect" required="false"  description="选择事件" %>

<!-- 复制第二个标签，添加callback方法 uploadSuccess_callBack -->


<%
String pageModel = request.getParameter("pageModel");
label = label==null?"":label;
width = width==null?"120":width;
fileTypeExts = fileTypeExts==null?"*.*":fileTypeExts;
fileTypeDesc = fileTypeDesc==null?"所有文件":fileTypeDesc;
uploadLimit = uploadLimit==null?"0":uploadLimit;
fileSizeLimit = fileSizeLimit==null?"0":fileSizeLimit;

colspan = (Integer.valueOf(colspan)-1)+"";
onSelect = onSelect==null?"":("'onDialogClose':"+onSelect+",");
%>



<td class="<%=labelTdcls%>">
        <%--用来作为文件队列区域--%>
    <input type="file"  name="<%=property %>" id="<%=property %>" />
</td>
<td colspan="<%=colspan%>">    
    <div id="<%=property %>fileQueue" style="float: left;padding: 5px;">
    </div>
    
   <!--  <p>
   <a href="javascript:$('#<%=property %>').uploadify('upload','*')">上传</a> 
        <a href="javascript:$('#uploadify').uploadify('upload','*')">上传</a>| 
        <a href="javascript:$('#uploadify').uploadify('cancel','*')">取消上传</a>
    </p> -->
</td>


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
        'width'			 : <%=width%>,  
        <%=onSelect%>
        'uploadLimit'    : <%=uploadLimit%>,  
        'fileTypeExts'        : '<%=fileTypeExts%>',  
        'fileTypeDesc'       : '<%=fileTypeDesc%>',
        'pageModel'    :'<%=pageModel%>',
        'deleteCallback'    :'1',
        'onUploadStart'  :function(file){
        	var obj = $helper().getFormInfo('commForm');
        	obj['uploadType'] = 'UPLOAD';
        	obj['fileid'] = '<%=property %>';
        	obj['deleteCallback'] = '1';
            obj['pageModel'] = '<%=pageModel%>';
            //console.log(obj)
            $("#<%=property %>").uploadify("settings","formData",obj );    
         },
        'onQueueComplete' : function(queueData) {
        	if(typeof(onQueueComplete) != "undefined"){
        		onQueueComplete(queueData);
        	}
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
        	uploadSuccess_callBack();
        }
    });  
});  
 </script>
