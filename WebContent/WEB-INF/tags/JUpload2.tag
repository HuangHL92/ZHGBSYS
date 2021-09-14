<%@tag import="com.insigma.odin.framework.sys.SysfunctionManager"%>
<%@ tag pageEncoding="GBK" body-content="empty" small-icon=""
	display-name="jquery�ϴ����" description="jquery�ϴ����"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ attribute name="label" required="false" description="�ϴ���ť����"%>
<%@ attribute name="property" required="true" description="" %>
<%@ attribute name="colspan" required="true"  description="һ������ռ�ĵ�Ԫ�����" %>
<%@ attribute name="labelTdcls" required="false"  description="��ť���ڵ�td��ʽ" %>
<%@ attribute name="width" required="false"  description="��ť���" %>

<%@ attribute name="fileTypeExts" required="false" description="�ϴ��ļ�����(*.*)��������ͷֺŸ���"%>
<%@ attribute name="fileTypeDesc" required="false" description="�ϴ��ļ�����"%>
<%@ attribute name="uploadLimit" required="false" description="�ϴ��ļ���������"%>
<%@ attribute name="fileSizeLimit" required="false" description="�ϴ��ļ���С����ֱ�Ӽӵ�λ��"%>
<%@ attribute name="onSelect" required="false"  description="ѡ���¼�" %>




<%
String pageModel = request.getParameter("pageModel");
label = label==null?"":label;
width = width==null?"120":width;
fileTypeExts = fileTypeExts==null?"*.*":fileTypeExts;
fileTypeDesc = fileTypeDesc==null?"�����ļ�":fileTypeDesc;
uploadLimit = uploadLimit==null?"0":uploadLimit;
fileSizeLimit = fileSizeLimit==null?"0":fileSizeLimit;

colspan = (Integer.valueOf(colspan)-1)+"";
onSelect = onSelect==null?"":("'onDialogClose':"+onSelect+",");
%>



<td class="<%=labelTdcls%>">
        <%--������Ϊ�ļ���������--%>
    <input type="file"  name="<%=property %>" id="<%=property %>" />
</td>
<td colspan="<%=colspan%>">    
    <div id="<%=property %>fileQueue" style="float: left;padding: 5px;">
    </div>
    
   <!--  <p>
   <a href="javascript:$('#<%=property %>').uploadify('upload','*')">�ϴ�</a> 
        <a href="javascript:$('#uploadify').uploadify('upload','*')">�ϴ�</a>| 
        <a href="javascript:$('#uploadify').uploadify('cancel','*')">ȡ���ϴ�</a>
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
        'auto'           : false, //����true �Զ��ϴ� ����false����Ҫ�ֶ������ť 
        'multi'          : true,  
        'wmode'          : 'transparent',  
        'width'			 : <%=width%>,  
        'uploadLimit'    : <%=uploadLimit%>,  
        'fileTypeExts'        : '<%=fileTypeExts%>',  
        'fileTypeDesc'       : '<%=fileTypeDesc%>',
        'pageModel'    :'<%=pageModel%>',
        'overrideEvents' : ['onDialogClose','onUploadError', 'onSelectError'],
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
        	alert(queueSizeLimit)
        },
        'onSelectError' :function(file, errorCode, errorMsg) {
            var msgText = "�ϴ�ʧ��\n";
            switch (errorCode) {
                case SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED:
                    //this.queueData.errorMsg = "ÿ������ϴ� " + this.settings.queueSizeLimit + "���ļ�";
                    msgText += "ÿ������ϴ� " + this.settings.uploadLimit + "���ļ�";
                    break;
                case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
                    msgText += "�ļ���С��������( " + this.settings.fileSizeLimit + " )";
                    break;
                case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
                    msgText += "�ļ���СΪ0";
                    break;
                case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
                    msgText += "�ļ���ʽ����ȷ������ " + this.settings.fileTypeExts;
                    break;
                default:
                    msgText += "������룺" + errorCode + "\n" + errorMsg;
            }
            alert(msgText);
        },
        'onUploadError':function(file, errorCode, errorMsg, errorString) {
            // �ֹ�ȡ����������ʾ
            if (errorCode == SWFUpload.UPLOAD_ERROR.FILE_CANCELLED
                    || errorCode == SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED) {
                return;
            }
            var msgText = "�ϴ�ʧ��\n";
            switch (errorCode) {
                case SWFUpload.UPLOAD_ERROR.HTTP_ERROR:
                    msgText += "HTTP ����\n" + errorMsg;
                    break;
                case SWFUpload.UPLOAD_ERROR.MISSING_UPLOAD_URL:
                    msgText += "�ϴ��ļ���ʧ���������ϴ�";
                    break;
                case SWFUpload.UPLOAD_ERROR.IO_ERROR:
                    msgText += "IO����";
                    break;
                case SWFUpload.UPLOAD_ERROR.SECURITY_ERROR:
                    msgText += "��ȫ�Դ���\n" + errorMsg;
                    break;
                case SWFUpload.UPLOAD_ERROR.UPLOAD_LIMIT_EXCEEDED:
                    msgText += "ÿ������ϴ� " + this.settings.uploadLimit + "��";
                    break;
                case SWFUpload.UPLOAD_ERROR.UPLOAD_FAILED:
                    msgText += errorMsg;
                    break;
                case SWFUpload.UPLOAD_ERROR.SPECIFIED_FILE_ID_NOT_FOUND:
                    msgText += "�Ҳ���ָ���ļ��������²���";
                    break;
                case SWFUpload.UPLOAD_ERROR.FILE_VALIDATION_FAILED:
                    msgText += "��������";
                    break;
                default:
                    msgText += "�ļ�:" + file.name + "\n������:" + errorCode + "\n"
                            + errorMsg + "\n" + errorString;
            }
            alert(msgText);
            return parameters;
        }
    
    });  
});  
 </script>
