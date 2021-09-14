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
		<odin:textIconEdit property="sp0102" iconClick="searchA01" width="'100%'"  title="����" colspan="1"/>
		<%-- <odin:textEdit property="sp0102" width="'100%'" title="����" colspan="1"/> --%>
		<td width="12%" class="titleTd" colspan="1">�Ա�</td>
		<odin:select2 property="sp0103"  title="�Ա�" codeType="GB2261" colspan="1"/>
		<td width="12%" class="titleTd" colspan="1">��������</td>
		<odin:dateEdit format="Ymd" property="sp0104"  width="'100%'" title="��������" colspan="1"/>
		<td width="12%" class="titleTd" colspan="1">�Ļ��̶�</td>
		<odin:textEdit property="sp0105" width="'100%'" title="�Ļ��̶�" colspan="1"/>
	</tr>
	<tr>
		<td class="titleTd" colspan="1">�ֵ�λ<br/>��ְ��</td>
		<odin:textarea property="sp0106" title="���ε�λ��ְ��" colspan="2" rows="5"/>
		<td class="titleTd" colspan="1">�������Ƽ�<br/>ѡ��Ƹ����<br/>����ְ��</td>
		<odin:textarea property="sp0107" title="�������Ƽ�ѡ��Ƹ��������ְ��" colspan="2" rows="5"/>
		<td class="titleTd" colspan="1">����</td>
		<odin:textarea property="sp0108" title="����" colspan="1" rows="5"/>
	</tr>
	<tr>
		<td class="titleTd" colspan="1">�����������ż�ְ�������˴���</td>
		<odin:textarea property="sp0109" title="�����������ż�ְ�������˴���" colspan="7" rows="5"/>
	</tr>
	<tr>
		<td class="titleTd" colspan="1">��������</td>
		<odin:textarea property="sp0110" title="��������" colspan="7" rows="5"/>
	</tr>
	<tr>
		<td class="titleTd" colspan="1">�ɲ����ڵ�λ���</td>
		<odin:textarea property="sp0111" title="�ɲ����ڵ�λ���" colspan="7" rows="5"/>
	</tr>
	<tr>
		<td class="titleTd" colspan="1">����ҵ�����ܲ������</td>
		<odin:textarea property="sp0112" title="����ҵ�����ܲ������" colspan="7" rows="5"/>
	</tr>
	<tr>
		<td class="titleTd" colspan="1">�ɲ����ܲ������</td>
		<odin:textarea property="sp0113" title="�ɲ����ܲ������" colspan="7" rows="5"/>
	</tr>
	<tr height="60" style="background-color: white;">
		<tags:JUpload2 property="file03" label="ѡ���ļ�" fileTypeDesc="�����ļ�"  colspan="8"
		uploadLimit="20" width="80" fileSizeLimit="20MB" fileTypeExts="*.*" labelTdcls="titleTd"/>
	</tr>
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
		<td align="left" style="">
			<odin:button text="�˻���һ��"  property="saveB" handler="saveB"></odin:button>
		</td>
		<td><button onclick=" location.replace(location.href); ">ˢ��</button></td>
	</tr>
</table>
</div>

<script type="text/javascript">
Ext.onReady(function() {
	
	$('#sp0102').parent().parent().parent().parent().parent().attr('width','13%'); 
	$('#sp0103').parent().parent().parent().attr('width','13%'); 
	$('#sp0104').parent().parent().parent().parent().attr('width','13%'); 
	$('#sp0105').parent().parent().parent().attr('width','13%'); 
	/*��ie�н����������(��ֹֻ��һ����ʾ����Ҫ���ie�������⣬ie8�е�����Ϊ100%ʱ���ı��������ɻ���ʱ*/
	$('textarea').each(function(){$(this).css('width',$(this).innerWidth())})
	var viewSize = Ext.getBody().getViewSize();
	
	if(typeof parentParam!='undefined'&&typeof parentParam.sp0100!='undefined')
		document.getElementById('sp0100').value = parentParam.sp0100;
	
	
	
	
	
	
	
});
function saveCallBack(t,fg){
	var sp0100 = $('#sp0100').val();
	$h.alert('ϵͳ��ʾ', t, function(){
		parent.$('#sp0100').val($('#sp0100').val());
		parent.$('#a0000').val($('#a0000').val());
		parent.infoSearch();
		window.close();
		
		//window.close();
	});
}

function searchA01(){
	var sp0102 = Ext.getCmp('sp0102').getValue();
	var sp0100 = $('#sp0100').val();
	//alert(sp0102);
	if(sp0100!=''){
		return;
	}
	if(sp0102==''){
		$h.alert('ϵͳ��ʾ','������������');
		return;
	}
	radow.doEvent('searchA01',sp0102);
}
var g_contextpath = '<%= request.getContextPath() %>';
function openPwin(){
	var sp0102 = Ext.getCmp('sp0102').getValue();
	$h.openWin('findById321','pages.jzsp.NameSearch','��Ա�б�',820,520,null,g_contextpath,window,
			{maximizable:false,resizable:false,
		sp0102:sp0102});
}

document.onkeydown=function() { 
	
	if (event.keyCode == 13) { 
		if (document.activeElement.id == "sp0102") {
			searchA01();
			return false;
		}
	}else if(event.keyCode == 27){	//����ESC
	        return false;   
	}
}
function setInfo(a0000){
	radow.doEvent('searchA01ByA0000',a0000);
}

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
	//��ע����
	$('#spbl08SpanId').css('display','none');
	$('#spbl08').css('display','none');
	$('#saveB').css('display','none');
	$('#sp0113').each(function(){$(this).
		attr('readonly','readonly').attr('unselectable','on').
		css('background-color','rgb(235,235,228)')});
}
function setDisabled2(){//һ������
	$('#save').css('display','none');
	Ext.getCmp('sp0103_combo').setDisabled(true);
	Ext.getCmp('sp0104').setDisabled(true);
	Ext.getCmp('sp0102').setDisabled(true);
	$('#sp0103_combo,#sp0104,#sp0102').each(function(){
		$(this).removeAttr('disabled')});
	$('#sp0105,#sp0106,#sp0107,#sp0108,#sp0109,#sp0110,#sp0112,#sp0111').each(function(){
		$(this).attr('readonly','readonly').attr('unselectable','on').
		css('background-color','rgb(235,235,228)')});
	
	$('#sp0102').parent().parent().parent().parent().parent().css('background-color','rgb(235,235,228)'); 
	$('#sp0103').parent().parent().parent().css('background-color','rgb(235,235,228)'); 
	$('#sp0105').parent().parent().parent().css('background-color','rgb(235,235,228)'); 
	$('#sp0104').parent().parent().parent().parent().css('background-color','rgb(235,235,228)'); 

	$('#file03').text('����');
	//��������
	$('#spb04SpanId').css('display','none');
	Ext.getCmp('spb04_combotree').hide();
	$('#saveS').css('display','none');
}

function setDisabled3(){//��������
	$('#save').css('display','none');
	Ext.getCmp('sp0103_combo').setDisabled(true);
	Ext.getCmp('sp0104').setDisabled(true);
	Ext.getCmp('sp0102').setDisabled(true);
	$('#sp0103_combo,#sp0104,#sp0102').each(function(){
		$(this).removeAttr('disabled')});
	$('#sp0105,#sp0106,#sp0107,#sp0108,#sp0109,#sp0110,#sp0113,#sp0111').each(function(){
		$(this).attr('readonly','readonly').attr('unselectable','on').
		css('background-color','rgb(235,235,228)')});
	
	$('#sp0102').parent().parent().parent().parent().parent().css('background-color','rgb(235,235,228)'); 
	$('#sp0103').parent().parent().parent().css('background-color','rgb(235,235,228)'); 
	$('#sp0105').parent().parent().parent().css('background-color','rgb(235,235,228)'); 
	$('#sp0104').parent().parent().parent().parent().css('background-color','rgb(235,235,228)'); 

	$('#file03').text('����');
	
}

function setDisabled4(){//��������
	$('#save').css('display','none');
	Ext.getCmp('sp0103_combo').setDisabled(true);
	Ext.getCmp('sp0104').setDisabled(true);
	Ext.getCmp('sp0102').setDisabled(true);
	$('#sp0103_combo,#sp0104,#sp0102').each(function(){
		$(this).removeAttr('disabled')});
	$('#sp0105,#sp0106,#sp0107,#sp0108,#sp0109,#sp0110,#sp0111,#sp0112').each(function(){
		$(this).attr('readonly','readonly').attr('unselectable','on').
		css('background-color','rgb(235,235,228)')});
	
	$('#sp0102').parent().parent().parent().parent().parent().css('background-color','rgb(235,235,228)'); 
	$('#sp0103').parent().parent().parent().css('background-color','rgb(235,235,228)'); 
	$('#sp0105').parent().parent().parent().css('background-color','rgb(235,235,228)'); 
	$('#sp0104').parent().parent().parent().parent().css('background-color','rgb(235,235,228)'); 
	
	
	//��������
	$('#spb04SpanId').css('display','none');
	Ext.getCmp('spb04_combotree').hide();
	$('#saveS button').text('����ͨ��');
	$('#saveB button').text('������ͨ��');

	$('#file03').text('����');
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


