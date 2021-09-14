<%@tag import="com.insigma.odin.framework.sys.SysfunctionManager"%>
<%@ tag pageEncoding="GBK" body-content="empty" small-icon=""
	display-name="jquery�ϴ����" description="jquery�ϴ����"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ attribute name="label" required="false" description="�ϴ���ť����"%>
<%@ attribute name="property" required="true" description="" %>

<%@ attribute name="fileTypeExts" required="false" description="�ϴ��ļ�����(*.*)��������ͷֺŸ���"%>
<%@ attribute name="fileTypeDesc" required="false" description="�ϴ��ļ�����"%>
<%@ attribute name="uploadLimit" required="false" description="�ϴ��ļ���������"%>
<%@ attribute name="fileSizeLimit" required="false" description="�ϴ��ļ���С����ֱ�Ӽӵ�λ��"%>



<%
String pageModel = request.getParameter("pageModel");
label = label==null?"":label;
fileTypeExts = fileTypeExts==null?"*.*":fileTypeExts;
fileTypeDesc = fileTypeDesc==null?"�����ļ�":fileTypeDesc;
uploadLimit = uploadLimit==null?"0":uploadLimit;
fileSizeLimit = fileSizeLimit==null?"0":fileSizeLimit;

%>

<div style="width:100%;height: 100%">
        <%--������Ϊ�ļ���������--%>
    <input type="file" name="<%=property %>" id="<%=property %>" />
    <div id="<%=property %>fileQueue" style="float: left;">
    </div>
    
</div>

<script>
$(document).ready(function() {  
    $("#<%=property %>").uploadify({  
    	'method':'post',
        'swf'       : '<%=request.getContextPath()%>/jqueryUpload/uploadify.swf',
        'uploader'  : '<%=request.getContextPath()%>/servlet/JUpload', 
		'width'			 :    30,
		'height'	     :    15,
        'folder'         : '/upload',  
        'queueID'        : '<%=property %>fileQueue',
        'cancelImg'      : 'jqueryUpload/uploadify-cancel.png',
        'buttonText'     : '<%=label%>',
        'fileSizeLimit'  : '<%=fileSizeLimit%>',
        'auto'           : false, //����true �Զ��ϴ� ����false����Ҫ�ֶ������ť 
        'multi'          : true,  
        'wmode'          : 'transparent',  
        'uploadLimit'    : <%=uploadLimit%>,  
        'fileTypeExts'        : '<%=fileTypeExts%>',  
        'fileTypeDesc'       : '<%=fileTypeDesc%>',
        'pageModel'    :'<%=pageModel%>',
        'onUploadStart'  :function(file){
        	var obj = $helper().getFormInfo('commForm');
        	obj['uploadType'] = 'UPLOAD';
        	obj['fileid'] = '<%=property %>';
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
        }
    });  
});  
 </script>
