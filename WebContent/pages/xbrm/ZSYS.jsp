<%@page import="com.insigma.siis.local.pagemodel.xbrm.constant.RMHJ"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@page isELIgnored="false" %>
<%@include file="/comOpenWinInit2.jsp" %>


<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/xbrm/jquery-ui-12.1.css">
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery-ui1.10.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<link href="<%=request.getContextPath()%>/jqueryUpload/uploadify.css" rel="stylesheet" type="text/css" />

<script src="<%=request.getContextPath()%>/jqueryUpload/jquery.uploadify.js" type="text/javascript"></script>

<style>
body{
margin: 1px;overflow: auto;
font-family:'����',Simsun;
word-break:break-all;
}
.ui-tabs .ui-tabs-panel{padding: 0px;padding-left: 3px;}
.ui-helper-reset{font-size: 12px;}
.x-form-field-wrap{width: 100%!important;}/*���ڿ�  */
.GBx-fieldset .x-form-trigger{right: 0px;}/*ͼ�����  */
.GBx-fieldset input{width: 100%!important;}
.GBx-fieldset .x-fieldset{padding-bottom: 0px;margin-bottom: -12px;margin-top: 12px}

.GBx-fieldset .x-fieldset-bwrap{overflow-y: auto;}



.marginbottom0px .x-form-item{margin-bottom: 0px;}

.marginbottom0px table,.marginbottom0px table tr th,.marginbottom0px table tr td
{ border:1px solid #74A6CC; padding: 3px; border-right-width: 0px;  }

.marginbottom0px table
{line-height: 25px; text-align: center; border-collapse: collapse;border-right-width: 1px; }   

.titleTd{
	background-color: rgb(192,220,241);
	font-weight: bold;
	font-size: 12px;
}

.bh{ display: none; }
.tbh{ display: none; }
.comboh{cursor: pointer!important;background:none!important;background-color:white!important;}
.aclass{font-size: 12px; padding-left: 3px!important;line-height: 30px;}

TEXTAREA.x-form-field{overflow-y:auto;}

.x-grid3-row TD{height: 28px;line-height: 28px;vertical-align: middle;}
.x-grid3-cell-inner{padding-top: 0px;}
.x-tip-header .x-tool{background-image: none;}
</style>

<odin:hidden property="rbId" title="����id"/>
<odin:hidden property="block" value="" title="ְ��Ԥ����ʾ����"/>
<odin:hidden property="docpath" title="�ĵ���ַ" />

<odin:toolBar property="bbar" applyTo="bbardiv">
	<!-- ְ��Ԥ�� -->
	<odin:buttonForToolBar text="ְ��Ԥ���" icon="images/keyedit.gif" id="btn4" handler="exportExcel"/>
	<odin:fill></odin:fill>
	<odin:buttonForToolBar text="����ְ��Ԥ���" icon="images/icon/save.gif" id="saveZS" isLast="true"/>
</odin:toolBar>

		  <div style="width: 100%; margin-top: 3px;">
		   <div id="peopleInfo" style="float: left; margin-left: 3px;" class="GBx-fieldset">
		    <odin:groupBox property="zsysGB" title="ְ��Ԥ��" >
	      		<div id="yushen" class="marginbottom0px">
					<table style="width: 100%">
						<tr>
							<td width="19%" class="titleTd" colspan="1" rowspan="2">���䱸�ɲ��ĵ�����λ�������������</td>
							<td width="10%" class="titleTd" colspan="2">ְ���㼶</td>
							<td width="10%" class="titleTd" colspan="2">�˶�ְ��</td>
							<td width="10%" class="titleTd" colspan="2">����ְ��</td>
							<td width="10%" class="titleTd" colspan="2">����ְ��</td>
							<td width="10%" class="titleTd" colspan="2">����ְ��</td>
							<td width="12%" class="titleTd" colspan="2">�Ƿ�ְ��</td>
							<td width="19%" class="titleTd" colspan="1" rowspan="2">��ע</td>
						</tr>
						<tr>
							<td class="titleTd" colspan="1">����</td>
							<td class="titleTd" colspan="1">�Ƽ�</td>
							<td class="titleTd" colspan="1">�쵼ְ��</td>
							<td class="titleTd" colspan="1">���쵼ְ��</td>
							<td class="titleTd" colspan="1">�쵼ְ��</td>
							<td class="titleTd" colspan="1">���쵼ְ��</td>
							<td class="titleTd" colspan="1">�쵼ְ��</td>
							<td class="titleTd" colspan="1">���쵼ְ��</td>
							<td class="titleTd" colspan="1">�쵼ְ��</td>
							<td class="titleTd" colspan="1">���쵼ְ��</td>
							<td class="titleTd" colspan="1">�쵼<br>ְ��</td>
							<td class="titleTd" colspan="1">����<br>��ְ<br>��</td>
						</tr>
						 <%
						 int rownum = 1;
						 for(int i=1;i<=8;i++){
							    String tr_i = "tr_"+rownum;
							    String td_js1201 = "td_js1201_"+rownum;
							    String js1201_i = "js1201_"+rownum;
							    String td_js1202_1_i = "td_js1202_1_"+rownum;
							    String td_js1202_2_i = "td_js1202_2_"+rownum;
							    String js1202_i = "js1202_"+rownum;
							    String js1202_1 = "js1202_1_"+rownum;
							    String js1202_2 = "js1202_2_"+rownum;
							    String js1203_i = "js1203_"+rownum;
							    String js1204_i = "js1204_"+rownum;
							    String js1205_i = "js1205_"+rownum;
							    String js1206_i = "js1206_"+rownum;
							    String js1207_i = "js1207_"+rownum;
							    String js1208_i = "js1208_"+rownum;
							    String js1209_i = "js1209_"+rownum;
							    String js1210_i = "js1210_"+rownum;
							    String js1211_i = "js1211_"+rownum;
							    String js1212_i = "js1212_"+rownum;
							    String js1213_i = "js1213_"+rownum;
							    %>
							<tr id="<%=tr_i %>">
							    <%-- <td colspan="1" id="<%=td_js1201 %>">
							    <tags:rmbPopWinInput property="<%=js1201_i %>" cls="width-80 height-40 no-y-scroll" label="������" 
title="" textareaStyle="display:none;text-align:center;" textareaCls="cellbgclor x-form-field" codetype="ZB01" codename="code_name3"
 defaultValue="" hiddenValue=""/>
							        <tags:rmbPopWinInput2 property="<%=js1201_i %>"  label="���䵥λ" 
							    title="" textareaStyle="width:114px;float:left;" textareaCls="right_input_btn" codetype="orgTreeJsonData" 
							    defaultValue="" hiddenValue=""/> 
							    </td>--%>
							    
							    <%-- <tags:PublicTextIconEdit isLoadData="false"  property="<%=js1201_i %>" label2="���䵥λ"  codetype="orgTreeJsonData" colspan="1" readonly="true" /> --%> 
							    <%-- <tags:PublicTextIconEdit3  property="a0195" readonly="true" label="ͳ�ƹ�ϵ���ڵ�λ" colspan="1" codetype="orgTreeJsonData"></tags:PublicTextIconEdit3> --%>
							    <odin:textEdit property="<%=js1201_i %>" title="��λ����" colspan="1" readonly="true"></odin:textEdit>
							    <td colspan="1" id="<%=td_js1202_1_i %>">
							        <input type="radio" name="<%=js1202_i %>" style="width: auto !important;" id="<%=js1202_1 %>" value="1" checked="checked" disabled="disabled"/>
							    </td>
							    <td colspan="1" id="<%=td_js1202_2_i %>" >
							        <input type="radio" name="<%=js1202_i %>" style="width: auto !important;" id="<%=js1202_2 %>" value="0" disabled="disabled"/>
							    </td>
							    <odin:numberEdit property="<%=js1203_i %>" maxlength="3" width="'100%'" title="�˶�ְ���쵼ְ��" colspan="1" readonly="true"/>
							    <odin:numberEdit property="<%=js1204_i %>" maxlength="3" width="'100%'" title="�˶�ְ�����쵼ְ��" colspan="1" readonly="true"/>
							    <odin:numberEdit property="<%=js1205_i %>" maxlength="3" width="'100%'" title="����ְ���쵼ְ��" colspan="1" readonly="true"/>
							    <odin:numberEdit property="<%=js1206_i %>" maxlength="3" width="'100%'" title="����ְ�����쵼ְ��" colspan="1" readonly="true"/>
							    <odin:numberEdit property="<%=js1207_i %>" maxlength="3" width="'100%'" title="����ְ���쵼ְ��" colspan="1" readonly="true"/>
							    <odin:numberEdit property="<%=js1208_i %>" maxlength="3" width="'100%'" title="����ְ�����쵼ְ��" colspan="1" readonly="true"/>
							    <odin:numberEdit property="<%=js1209_i %>" maxlength="3" width="'100%'" title="����ְ���쵼ְ��" colspan="1" readonly="true"/>
							    <odin:numberEdit property="<%=js1210_i %>" maxlength="3" width="'100%'" title="����ְ�����쵼ְ��" colspan="1" readonly="true"/>
							    <odin:select2 property="<%=js1211_i %>"  width="'100%'" title="��ֵ���쵼ְ��" colspan="1" codeType="XZ09" value="0" readonly="true"/>
							    <odin:select2 property="<%=js1212_i %>"  width="'100%'" title="��ֵ�����쵼ְ��" colspan="1" codeType="XZ09" value="0" readonly="true"/>
							    <odin:textarea property="<%=js1213_i %>"  maxlength="500" title="��ע" colspan="1" rows="3" readonly="true"/>
							</tr>
						 <%
						     rownum++;
						    }
						 %>
						<tr>
						    <td colspan="1" class="titleTd">�ɲ��ල�����</td>
						   <%--  <odin:textarea property="js1214"  maxlength="500" title="�ɲ��ල�����" colspan="13" rows="2"/> --%>
							<td  colspan="6">
								<odin:radio property="js1214" value="ͬ��" label="ͬ��"></odin:radio>
							</td>
							<td colspan="6">
								<odin:radio property="js1214" value="��ͬ��" label="��ͬ��"></odin:radio>
							</td>
						
						</tr>
					
						
					</table>
					
				</div>
				<div id="bbardiv" ></div> 
	      	</odin:groupBox>
	      	
	      	</div>
		  </div>
	  
	   

  





<script type="text/javascript">
Ext.onReady(function(){
	if(typeof parentParam!= 'undefined'){
		document.getElementById('rbId').value=parentParam.rb_id;
	}else{
		document.getElementById('rbId').value='c42981e1-d876-4d5c-9e85-13eb5bad13eb';
	}
	//groupbox�����
	var jbqkGB = $("#zsysGB .x-fieldset-bwrap");
	jbqkGB.css('width',870);
	jbqkGB.css('height',560);
	
});

function showArow(){
	var block = parseInt(document.getElementById('block').value);
	for(var t = block+1; t <=30; t++){ //tr_len��Ҫ���Ƶ�tr����  
	     $("#tr_"+t).hide();  
	}
	for(var i = 1; i <=block; i++){ //��ʾtr����
	     $("#tr_"+i).show();  
	} 
}


function downloadByUUID(){
	var downloadUUID = document.getElementById('docpath').value;
	window.location='<%= request.getContextPath() %>/PublishFileServlet?method=downloadFile&uuid='+downloadUUID;
	return false
}

function ShowCellCover(elementId, titles, msgs) {	
	Ext.MessageBox.buttonText.ok = "�ر�";
	if(elementId.indexOf("start") != -1){
	
		Ext.MessageBox.show({
			title:titles,
			msg:msgs,
			width:300,
	        height:300,
			closable:false,
			modal:true,
			progress:true,
			wait:true,
			animEl: 'elId',
			increment:5, 
			waitConfig: {interval:150}
		});
	}
}

/*��� ��*/
function exportExcel(obj){
	var rbId=document.getElementById("rbId").value;
	var buttonid = obj.id;
	var buttontext = obj.text;
	//alert(param);
	var path = '<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.xbrm.ZSYS&eventNames=ExpGird';
	//alert(path);
	ShowCellCover('start','ϵͳ��ʾ','���������� ,�����Ե�...');
   	Ext.Ajax.request({
   		timeout: 60000,
   		url: path,
   		async: true,
   		method :"post",
   		form : 'commform',
   		params : {rbId:rbId,buttonid:buttonid,buttontext:buttontext},
        callback: function (options, success, response) {
      	   if (success) {
      		   Ext.Msg.hide();
      		   var result = response.responseText;
 			   if(result){
 				  var cfg = Ext.util.JSON.decode(result);
 					if(0==cfg.messageCode){
 						if("�����ɹ���"!=cfg.mainMessage){
 							Ext.Msg.alert('ϵͳ��ʾ:',cfg.mainMessage);
 							return;
 						}
 						if(cfg.elementsScript!=""){
 							if(cfg.elementsScript.indexOf("\n")>0){
 	 							cfg.elementsScript = cfg.elementsScript.replace(/\r/gi,"");
 	 							cfg.elementsScript = cfg.elementsScript.replace(/\n/gi,"\\n");
 	 						}
 	 						
 	 						//console.log(cfg.elementsScript);
 	 						eval(cfg.elementsScript);
 						}else{
 							
 		 					
 						}
 						
 					}else{
 						Ext.Msg.alert('ϵͳ��ʾ:',cfg.mainMessage);
						return;
 					}
 				}
      	   }
        }
   });
}

</script>
