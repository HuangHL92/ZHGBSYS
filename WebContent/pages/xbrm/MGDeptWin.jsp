<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@page import="net.sf.json.JSONArray"%>

<%
String rb_id =request.getParameter("rb_id");
%>

<html>
<head>
<script type="text/javascript"src="<%=request.getContextPath()%>/basejs/ext/adapter/ext/ext-base.js"></script>
<script src="<%=request.getContextPath()%>/basejs/ext/ext-all.js"> </script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script src="<sicp3:rewrite forward='md5'/>"> </script>
<script src="<sicp3:rewrite forward='globals'/>"> </script>
<script src="<%=request.getContextPath()%>/basejs/odin.js"> </script>
<link rel="stylesheet" type="text/css" href="/hzb/css/odin.css"/>

<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<style type="text/css">
#daoru{position:relative;left:0px;width: 300px;} 

/* #tableid{position:relative;left:2px;width: 490px;}  */
.td_2{border: 1px solid #b5b8c8;font-size: 12px;height: 30px;}
body{
font: normal 11px tahoma, arial, helvetica, sans-serif;
font-size: 11px;
}
</style>
<title>选择&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</title>
<odin:head/>
<odin:MDParam></odin:MDParam>
</head>
<%@include file="/comOpenWinInit2.jsp" %>
<body style="overflow-y:hidden;overflow-x:hidden">

<form name="excelForm" id="excelForm" method="post"  >	
<input id="rb_id" name="rb_id" type="hidden" value="<%=rb_id %>"/>
<odin:groupBox title="选择部门" property="daoru">
<table style="width: 100%">
<tr>
	<td style="display:none"><odin:radio property="rbmDept" value="00" /></td>
	<td><odin:radio property="rbmDept" value="01" label="一处" ></odin:radio></td>
	<td><odin:radio property="rbmDept" value="02" label="二处"></odin:radio></td>
	<td><odin:radio property="rbmDept" value="03" label="三处"></odin:radio></td>
</tr>
<tr style="height: 7px;">
	<td colspan="4" style="height: 7px;"></td>
</tr>
<tr>
	<td colspan="2"></td>
	<td align="center" valign="middle" colspan="2">
		<odin:button text="&nbsp;确&nbsp;定&nbsp;" property="impwBtn" handler="ajaxSubmit" /> 
		<%-- <img src="<%=request.getContextPath()%>/images/go1.jpg" onclick="formupload()" id="impwBtn"> --%>
	</td>
</tr>
	
</table>	
</odin:groupBox>

</form>
<div style="display: none;">
<iframe src="" id='downloadframe' ></iframe>
</div>
<script>
$(document).ready(function () {
	var id = document.getElementById('rb_id').value;
	$("input[name='rbmDept']").each(function(){
		var v=$(this).val();
		if('01'==v){
			$(this).attr('checked',true);
		}
	})
});

function ajaxSubmit(){
	
	Ext.Ajax.request({
		method: 'POST',
		form:'excelForm',
        async: true,
        //params : parm,
        timeout :300000,//按毫秒计算
		url: "<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.xbrm.JSGL&eventNames=rbmSent",
		success: function(resData){
			var cfg = Ext.util.JSON.decode(resData.responseText);
			if(0==cfg.messageCode){
				if(cfg.elementsScript.indexOf("\n")>0){
					cfg.elementsScript = cfg.elementsScript.replace(/\r/gi,"");
					cfg.elementsScript = cfg.elementsScript.replace(/\n/gi,"\\n");
				}
				eval(cfg.elementsScript);
			}else{
				
			}
		},
		failure : function(res, options){ 
			Ext.Msg.hide();
			alert("网络异常！");
		}  
	});
}
</script>
<odin:response onSuccess="doSuccess"/>
</body>
</html>