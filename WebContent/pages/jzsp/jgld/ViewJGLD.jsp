<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<%@include file="/comOpenWinInit2.jsp" %>
<style>
body{
margin: 1px;overflow: auto;
word-break:break-all;
background-color: #f5f5f5;
font-family:'����',Simsun;
}
.tb td{
background-color:rgb(235,235,228);
}
.tb , .tb tr th, .tb tr td
{ border:1px solid #74A6CC;  border-right-width: 0px;  }
.tb
{ text-align: center; border-collapse: collapse;border-width: 2px; }
.titleTd{
	background-color: rgb(192,220,241)!important;
	font-weight: bold;
	font-size: 12px;
	line-height:20px;  

	letter-spacing:3px;  
}
.x-form-text, textarea.x-form-field{background-image: none;}
.x-form-field-wrap{width: 100%!important;}/*���ڿ�  */
.tb .x-form-trigger{right: 0px;}/*ͼ�����  */
.tb input{width: 100%!important;border: none;}
.tb textarea{border: none;overflow: auto;background-color:rgb(235,235,228); };
.tb .x-form-item{margin-bottom: 0px;}
.ext-ie7 .x-form-text{margin-bottom: -1px!important;}
</style>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<link href="<%=request.getContextPath()%>/jqueryUpload/uploadify.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath()%>/jqueryUpload/jquery.uploadify.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comboxWithTree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script  type="text/javascript">

</script>
<odin:hidden property="sp0100"/>
<odin:hidden property="a0000"/>
<odin:hidden property="usertype" title="���� �û����ǻ���"/>
			<!-- record_batch -->
<div style="width: 100%;text-align: center;">
<h1 style="margin: 15px auto;font-family: ����С���μ���,'����',Simsun;font-size: 20px;">���������쵼�ɲ�������������쵼ְ��������</h1>
<div style="height: 560px;overflow-y:auto; ">
<table style="width: 97%;" class="tb">
	<tr height="30" style="background-color: white;">
		<td width="12%" class="titleTd" colspan="1">����</td>
		<odin:textEdit property="sp0102" width="'100%'" readonly="true" title="����" colspan="1"/>
		<td width="12%" class="titleTd" colspan="1">�Ա�</td>
		<odin:select2 property="sp0103" hideTrigger="true" title="�Ա�" readonly="true" codeType="GB2261" colspan="1"/>
		<td width="12%" class="titleTd"  colspan="1">��������</td>
		<odin:textEdit readonly="true" property="sp0104" width="'100%'" title="��������" colspan="1"/>
		<td width="12%" class="titleTd" colspan="1">�Ļ��̶�</td>
		<odin:textEdit property="sp0105" width="'100%'" title="�Ļ��̶�" readonly="true" colspan="1"/>
	</tr>
	<tr>
		<td class="titleTd" colspan="1">�ֵ�λ<br/>��ְ��</td>
		<odin:textarea property="sp0106" title="���ε�λ��ְ��" colspan="2" readonly="true" rows="5"/>
		<td class="titleTd" colspan="1">�������Ƽ�<br/>ѡ��Ƹ����<br/>����ְ��</td>
		<odin:textarea property="sp0107" title="�������Ƽ�ѡ��Ƹ��������ְ��" colspan="2" readonly="true" rows="5"/>
		<td class="titleTd" colspan="1">����</td>
		<odin:textarea property="sp0108" title="����" colspan="1" readonly="true" rows="5"/>
	</tr>
	<tr>
		<td class="titleTd" colspan="1">�����������ż�ְ�������˴���</td>
		<odin:textarea property="sp0109" title="�����������ż�ְ�������˴���" readonly="true" colspan="7" rows="5"/>
	</tr>
	<tr>
		<td class="titleTd" colspan="1">��������</td>
		<odin:textarea property="sp0110" title="��������" colspan="7" readonly="true" rows="5"/>
	</tr>
	<tr>
		<td class="titleTd" colspan="1">�ɲ����ڵ�λ���</td>
		<odin:textarea property="sp0111" title="�ɲ����ڵ�λ���" colspan="7" readonly="true" rows="5"/>
	</tr>
	<tr>
		<td class="titleTd" colspan="1">����ҵ�����ܲ������</td>
		<odin:textarea property="sp0112" title="����ҵ�����ܲ������" colspan="7" readonly="true" rows="5"/>
	</tr>
	<tr>
		<td class="titleTd" colspan="1">�ɲ����ܲ������</td>
		<odin:textarea property="sp0113" title="�ɲ����ܲ������" colspan="7" readonly="true" rows="5"/>
	</tr>
	<tr height="60" style="background-color: white;">
		<tags:JUpload2 property="file03" label="ѡ���ļ�" fileTypeDesc="�����ļ�"  colspan="8"
		uploadLimit="20" width="80" fileSizeLimit="20MB" fileTypeExts="*.*" labelTdcls="titleTd"/>
	</tr>
	<%-- <tr>
		<td align="right" colspan="8" style="padding-right: 30px;">
			<odin:button text="����"  property="save"></odin:button>
		</td>
	</tr> --%>
</table>

</div>
<table style="width: 100%;">
	<tr>
		<tags:ComBoxWithTree property="spb04" label="����λ" codetype="USER" disabled="true"/>
		<td><button onclick=" location.replace(location.href); ">ˢ��</button></td>
	</tr>
</table>
</div>

<script type="text/javascript">
Ext.onReady(function() {
	
	$('#sp0102').parent().parent().parent().attr('width','13%'); 
	$('#sp0103').parent().parent().parent().attr('width','13%'); 
	$('#sp0104').parent().parent().parent().attr('width','13%'); 
	$('#sp0105').parent().parent().parent().attr('width','13%'); 
	/*��ie�н����������(��ֹֻ��һ����ʾ����Ҫ���ie�������⣬ie8�е�����Ϊ100%ʱ���ı��������ɻ���ʱ*/
	$('textarea').each(function(){$(this).css('width',$(this).innerWidth())})
	var viewSize = Ext.getBody().getViewSize();
	
	if(typeof parentParam!='undefined'&&typeof parentParam.sp0100!='undefined')
		document.getElementById('sp0100').value = parentParam.sp0100;
	
	
	
	$('#file03').text('����');
	
	
	
});

var g_contextpath = '<%= request.getContextPath() %>';

function setDisabledInfo(){//�Ǽǽڵ�
	$('#spb04SpanId').css('display','none');
	$('#spb04_combotree').css('display','none');
}

//�ļ�����
function download(id){
	
	//���ظ���
	//encodeURI��������urlת�룬������Ĵ����������� ����̨���յ�ʱ�������ת�봦��ת������
	window.location="PublishFileServlet?method=downloadFile&SPid="+encodeURI(encodeURI(id));
	
}
</script>


