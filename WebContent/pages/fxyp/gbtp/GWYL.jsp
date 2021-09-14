<%@page import="com.insigma.odin.framework.util.GlobalNames"%>
<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@include file="/comOpenWinInit2.jsp" %>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<link rel="stylesheet" type="text/css" href="mainPage/css/bootstrap-combined.min.css"> 
<style>
body{
width: 100%!important;
}
.hero-unit{
	padding: 5px 30px;
    margin: 0px;
    font-family: 宋体;
}
.gwmane,blockquote,.gwdesc{
	display: inline-block;
}
.gwdesc{
	width: 900px;
	margin: 0px 0px 0px 0px;
	padding: 0px;
}
</style>

<div class="hero-unit">
	<h2 class="gwmane">
		
	</h2>
	<!-- <blockquote>
		<p class="gwdesc">
			
		</p>
	</blockquote> -->
	<a class='btn btn-primary bjtj' onclick='rybd()' style="margin-left: 780px;">人员比对</a>
</div>

<%@include file="../../mntpsj/resourse/photoListTemplate.jsp" %>
<odin:hidden property="query_id"/>
<odin:hidden property="a0000s"/>
<script type="text/javascript">
<%
String gwid = request.getParameter("gwid");

%>
Ext.onReady(function(){
	if(""!='<%=gwid==null?"":gwid%>'){
		$('#query_id').val('<%=gwid%>');
	}else{
		document.getElementById("query_id").value=parentParam.query_id;
	}
	
	//alert(parentParam.query_id)
}); 
function setGWTJInfo(data){
	var html = "";
	$.each(data,function(i,item){
		html += (i+1)+"、"+item['mxname']+"："+(item['mxdsec']||"")+"&nbsp;&nbsp;";
	});
	//$('.gwdesc').html(html);
}

function rybd(){
	var a0000s='';
	$('input:checkbox[name=a01a0000]:checked').each(function(i){
		a0000s = a0000s + $(this).attr('a0000') + ',';
     });
	if (a0000s == '') {
		odin.alert("请选择人员！");
		return;
	}
	a0000s = a0000s.substring(0, a0000s.length - 1);
	$("#a0000s").val(a0000s);
	radow.doEvent('tpbj.onclick');
}
function clearSelected() {
	 //列表
	/* var gridId = "elearningGrid";
	var fieldName = "personcheck";
	var store = odin.ext.getCmp(gridId).store;
	var length = store.getCount();
	for (var i = 0; i < length; i++) {
		store.getAt(i).set(fieldName, false);
	}*/
	$('input:checkbox[name=a01a0000]:checked').prop('checked', false);
	$('.gbtj-list').css('background-color','rgb(255,255,255)');
   $('#a0000s').val(''); 
}
</script>