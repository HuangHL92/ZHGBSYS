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
font-family:'宋体',Simsun;
word-break:break-all;
}
.ui-tabs .ui-tabs-panel{padding: 0px;padding-left: 3px;}
.ui-helper-reset{font-size: 12px;}
.x-form-field-wrap{width: 100%!important;}/*日期宽  */
.GBx-fieldset .x-form-trigger{right: 0px;}/*图标对其  */
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

<odin:hidden property="rbId" title="批次id"/>
<odin:hidden property="block" value="" title="职数预审显示行数"/>
<odin:hidden property="docpath" title="文档地址" />

<odin:toolBar property="bbar" applyTo="bbardiv">
	<!-- 职数预审 -->
	<odin:buttonForToolBar text="职数预审表" icon="images/keyedit.gif" id="btn4" handler="exportExcel"/>
	<odin:fill></odin:fill>
	<odin:buttonForToolBar text="保存职数预审表" icon="images/icon/save.gif" id="saveZS" isLast="true"/>
</odin:toolBar>

		  <div style="width: 100%; margin-top: 3px;">
		   <div id="peopleInfo" style="float: left; margin-left: 3px;" class="GBx-fieldset">
		    <odin:groupBox property="zsysGB" title="职数预审" >
	      		<div id="yushen" class="marginbottom0px">
					<table style="width: 100%">
						<tr>
							<td width="19%" class="titleTd" colspan="1" rowspan="2">拟配备干部的地区或单位（含内设机构）</td>
							<td width="10%" class="titleTd" colspan="2">职数层级</td>
							<td width="10%" class="titleTd" colspan="2">核定职数</td>
							<td width="10%" class="titleTd" colspan="2">现配职数</td>
							<td width="10%" class="titleTd" colspan="2">拟免职数</td>
							<td width="10%" class="titleTd" colspan="2">拟配职数</td>
							<td width="12%" class="titleTd" colspan="2">是否超职数</td>
							<td width="19%" class="titleTd" colspan="1" rowspan="2">备注</td>
						</tr>
						<tr>
							<td class="titleTd" colspan="1">处级</td>
							<td class="titleTd" colspan="1">科级</td>
							<td class="titleTd" colspan="1">领导职数</td>
							<td class="titleTd" colspan="1">非领导职数</td>
							<td class="titleTd" colspan="1">领导职数</td>
							<td class="titleTd" colspan="1">非领导职数</td>
							<td class="titleTd" colspan="1">领导职数</td>
							<td class="titleTd" colspan="1">非领导职数</td>
							<td class="titleTd" colspan="1">领导职数</td>
							<td class="titleTd" colspan="1">非领导职数</td>
							<td class="titleTd" colspan="1">领导<br>职数</td>
							<td class="titleTd" colspan="1">非领<br>导职<br>数</td>
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
							    <tags:rmbPopWinInput property="<%=js1201_i %>" cls="width-80 height-40 no-y-scroll" label="出生地" 
title="" textareaStyle="display:none;text-align:center;" textareaCls="cellbgclor x-form-field" codetype="ZB01" codename="code_name3"
 defaultValue="" hiddenValue=""/>
							        <tags:rmbPopWinInput2 property="<%=js1201_i %>"  label="拟配单位" 
							    title="" textareaStyle="width:114px;float:left;" textareaCls="right_input_btn" codetype="orgTreeJsonData" 
							    defaultValue="" hiddenValue=""/> 
							    </td>--%>
							    
							    <%-- <tags:PublicTextIconEdit isLoadData="false"  property="<%=js1201_i %>" label2="拟配单位"  codetype="orgTreeJsonData" colspan="1" readonly="true" /> --%> 
							    <%-- <tags:PublicTextIconEdit3  property="a0195" readonly="true" label="统计关系所在单位" colspan="1" codetype="orgTreeJsonData"></tags:PublicTextIconEdit3> --%>
							    <odin:textEdit property="<%=js1201_i %>" title="单位名称" colspan="1" readonly="true"></odin:textEdit>
							    <td colspan="1" id="<%=td_js1202_1_i %>">
							        <input type="radio" name="<%=js1202_i %>" style="width: auto !important;" id="<%=js1202_1 %>" value="1" checked="checked" disabled="disabled"/>
							    </td>
							    <td colspan="1" id="<%=td_js1202_2_i %>" >
							        <input type="radio" name="<%=js1202_i %>" style="width: auto !important;" id="<%=js1202_2 %>" value="0" disabled="disabled"/>
							    </td>
							    <odin:numberEdit property="<%=js1203_i %>" maxlength="3" width="'100%'" title="核定职数领导职数" colspan="1" readonly="true"/>
							    <odin:numberEdit property="<%=js1204_i %>" maxlength="3" width="'100%'" title="核定职数非领导职数" colspan="1" readonly="true"/>
							    <odin:numberEdit property="<%=js1205_i %>" maxlength="3" width="'100%'" title="现配职数领导职数" colspan="1" readonly="true"/>
							    <odin:numberEdit property="<%=js1206_i %>" maxlength="3" width="'100%'" title="现配职数非领导职数" colspan="1" readonly="true"/>
							    <odin:numberEdit property="<%=js1207_i %>" maxlength="3" width="'100%'" title="拟免职数领导职数" colspan="1" readonly="true"/>
							    <odin:numberEdit property="<%=js1208_i %>" maxlength="3" width="'100%'" title="拟免职数非领导职数" colspan="1" readonly="true"/>
							    <odin:numberEdit property="<%=js1209_i %>" maxlength="3" width="'100%'" title="拟配职数领导职数" colspan="1" readonly="true"/>
							    <odin:numberEdit property="<%=js1210_i %>" maxlength="3" width="'100%'" title="拟配职数非领导职数" colspan="1" readonly="true"/>
							    <odin:select2 property="<%=js1211_i %>"  width="'100%'" title="超值数领导职数" colspan="1" codeType="XZ09" value="0" readonly="true"/>
							    <odin:select2 property="<%=js1212_i %>"  width="'100%'" title="超值数非领导职数" colspan="1" codeType="XZ09" value="0" readonly="true"/>
							    <odin:textarea property="<%=js1213_i %>"  maxlength="500" title="备注" colspan="1" rows="3" readonly="true"/>
							</tr>
						 <%
						     rownum++;
						    }
						 %>
						<tr>
						    <td colspan="1" class="titleTd">干部监督处意见</td>
						   <%--  <odin:textarea property="js1214"  maxlength="500" title="干部监督处意见" colspan="13" rows="2"/> --%>
							<td  colspan="6">
								<odin:radio property="js1214" value="同意" label="同意"></odin:radio>
							</td>
							<td colspan="6">
								<odin:radio property="js1214" value="不同意" label="不同意"></odin:radio>
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
	//groupbox框调整
	var jbqkGB = $("#zsysGB .x-fieldset-bwrap");
	jbqkGB.css('width',870);
	jbqkGB.css('height',560);
	
});

function showArow(){
	var block = parseInt(document.getElementById('block').value);
	for(var t = block+1; t <=30; t++){ //tr_len是要控制的tr个数  
	     $("#tr_"+t).hide();  
	}
	for(var i = 1; i <=block; i++){ //显示tr个数
	     $("#tr_"+i).show();  
	} 
}


function downloadByUUID(){
	var downloadUUID = document.getElementById('docpath').value;
	window.location='<%= request.getContextPath() %>/PublishFileServlet?method=downloadFile&uuid='+downloadUUID;
	return false
}

function ShowCellCover(elementId, titles, msgs) {	
	Ext.MessageBox.buttonText.ok = "关闭";
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

/*输出 表*/
function exportExcel(obj){
	var rbId=document.getElementById("rbId").value;
	var buttonid = obj.id;
	var buttontext = obj.text;
	//alert(param);
	var path = '<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.xbrm.ZSYS&eventNames=ExpGird';
	//alert(path);
	ShowCellCover('start','系统提示','正在输出表册 ,请您稍等...');
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
 						if("操作成功！"!=cfg.mainMessage){
 							Ext.Msg.alert('系统提示:',cfg.mainMessage);
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
 						Ext.Msg.alert('系统提示:',cfg.mainMessage);
						return;
 					}
 				}
      	   }
        }
   });
}

</script>
