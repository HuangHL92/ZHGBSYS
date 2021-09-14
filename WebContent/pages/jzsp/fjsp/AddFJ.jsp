<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<%@include file="/comOpenWinInit2.jsp" %>
<style>
body{
margin: 1px;overflow: auto;
background-color: #f5f5f5;
font-family:'����',Simsun;
}
.tb , .tb tr th, .tb tr td
{ border:1px solid #74A6CC;  border-right-width: 0px;  }
.tb
{ text-align: center; border-collapse: collapse;border-width: 2px; }
.titleTd{
	background-color: rgb(192,220,241);
	font-weight: bold;
	font-size: 12px;
	line-height:20px;  

	letter-spacing:3px;  
}
.x-form-text, textarea.x-form-field{background-image: none;}
.x-form-field-wrap{width: 100%!important;}/*���ڿ�  */
.tb .x-form-trigger{right: 0px;}/*ͼ�����  */
.tb input{width: 100%!important;border: none;}
.tb textarea{border: none;overflow: auto;word-break:break-all;};
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
<odin:hidden property="spp00"/>
<odin:hidden property="a0000"/>
<odin:hidden property="usertype" title="���� �û����ǻ���"/>
			<!-- record_batch -->
<div style="width: 100%;text-align: center;">
<h1 style="margin: 15px auto;font-family: ����С���μ���,'����',Simsun;font-size: 20px;">�쵼��ְ����</h1>
<div style="height: 170px;overflow-y:auto; ">
<table style="width: 98%;" class="tb">
	<tr>
		<td class="titleTd" width="15%" colspan="1">����</td>
		<odin:textEdit property="spp13" width="'100%'" title="����" colspan="1"/>
		<td width="15%" class="titleTd" colspan="1">��ְ����</td>
		<odin:select2 property="spp02"  title="��ְ����" data="['1','�쵼�ɲ����ż�ְ����']" colspan="1"/>
	</tr>
	<tr height="120" style="background-color: white;">
		<tags:JUpload2 property="file03" label="�����ϴ�" fileTypeDesc="�����ļ�"  colspan="4"
		uploadLimit="20" width="80" fileSizeLimit="20MB" fileTypeExts="*.*" labelTdcls="titleTd"/>
	</tr>
	<%-- <tr>
		<td class="titleTd" colspan="1">�������</td>
		<odin:textarea property="spp06" title="�ɲ����ڵ�λ���" colspan="3" rows="5"/>
	</tr>
	<tr height="60" style="background-color: white;">
		<tags:JUpload2 property="file06" label="���ܱ��ϴ�" fileTypeDesc="�����ļ�"  colspan="4"
		uploadLimit="20" width="80" fileSizeLimit="20MB" fileTypeExts="*.*" labelTdcls="titleTd"/>
	</tr>
	<tr>
		<td class="titleTd" colspan="1">���쵼���</td>
		<odin:textarea property="spp07" title="����ҵ�����ܲ������" colspan="3" rows="5"/>
	</tr> --%>
	
	
</table>

</div>
<table style="width: 100%;">
	<tr>
		<tags:ComBoxWithTree property="spb04" label="�ϱ���λ" readonly="true" codetype="USER" nodeDblclick="nodeclick" />
 		<odin:textEdit property="spbl08" label="��ע" /> 
		<td align="left" style="">
			<odin:button text="����"  property="save"></odin:button>
		</td>
		<td align="left" style="">
			<odin:button text="���沢�ϱ�"  property="saveS" handler="saveS"></odin:button>
		</td>
		<%-- <td align="left" style="">
			<odin:button text="�˻���һ��"  property="saveB" handler="saveB"></odin:button>
		</td> --%>
		
	</tr>
</table>
</div>

<script type="text/javascript">
Ext.onReady(function() {
	$('#spp02').parent().parent().parent().attr('width','33%'); 

	/*��ie�н����������(��ֹֻ��һ����ʾ����Ҫ���ie�������⣬ie8�е�����Ϊ100%ʱ���ı��������ɻ���ʱ*/
	$('textarea').each(function(){$(this).css('width',$(this).innerWidth())})
	var viewSize = Ext.getBody().getViewSize();
	
	if(typeof parentParam!='undefined'&&typeof parentParam.spp00!='undefined')
		document.getElementById('spp00').value = parentParam.spp00;
	
	
	
	
	
	
	
});
function saveCallBack(t,fg){
	var spp00 = $('#spp00').val();
	$h.alert('ϵͳ��ʾ', t, function(){
		parent.$('#spp00').val($('#spp00').val());

		parent.infoSearch();
		window.close();
		
		//window.close();
	});
}

var g_contextpath = '<%= request.getContextPath() %>';

function nodeclick(node,e){
	$('#usertype').val(node.attributes.ntype);
}
function saveS(){
	radow.doEvent('save.onclick','ss2');
}
function saveB(){
	radow.doEvent('save.onclick','ss3');
}
function setDisabled1(){//�Ǽǽڵ�
	 $('#spbl08SpanId').css('display','none');
	$('#spbl08').css('display','none');
	/*$('#saveB').css('display','none');
	$('#spp06,#spp07').each(function(){
		$(this).attr('readonly','readonly').attr('unselectable','on').
		css('background-color','rgb(235,235,228)')});
	$('#file06').text('���ܱ���'); */
}
function setDisabled2(){//һ������
	
}

function setDisabled3(){//��������
	
	
}




var setFileLength = (function(){//�ļ��ϴ�
	var flength = 0,curfindex=0,msg="";
	window.onUploadSuccess = function(file, jsondata, response){
		curfindex++;
		updateProgress(curfindex,flength,jsondata.file_name);
		if(curfindex==flength){
			Ext.Msg.hide();
			saveCallBack(msg);
		}
	}
	function updateProgress(cur,total,fname){
		if (fname.length > 15) {
			fname = fname.substr(0,15) + '...';
		}
		Ext.MessageBox.updateProgress(cur / total, '�����ϴ��ļ�:'+fname+'����ʣ'+(total-cur)+'��');
	}
	
	return function(t){
		msg = t;
		curfindex=0;
		flength = eval("$('#file03').data('uploadify').queueData.queueLength;");
		if(flength>0){
			$h.progress('��ȴ�', '�����ϴ��ļ�...',null,300);
		}else{
			Ext.Msg.hide();
			saveCallBack(t);
		}
	}
})();

//�ļ�����
function download(id){
	
	//���ظ���
	//encodeURI��������urlת�룬������Ĵ����������� ����̨���յ�ʱ�������ת�봦��ת������
	window.location="PublishFileServlet?method=downloadFile&SPid="+encodeURI(encodeURI(id));
	
}



</script>


