<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@include file="/comOpenWinInit2.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="js/lengthValidator.js"></script>
<%@page import="com.insigma.siis.local.pagemodel.cadremgn.sysmanager.authority.TableColInterface"%>


<style>

#bztp td{
	border: 1px solid #DDD;
	padding-left:10px;
	
}
 table{
	border-collapse:collapse
}
#table1 tr>td:first-child{
	background-color:#ADD8E6;	
}
textarea{
	font-family:SimHei;
	font-size:40px;	
	height:120px; 
	width:100%;
	border:none;
}
#tpry{
	padding-top:50px;
	font-size:12px;	
	border:none;
}
#tpgw{
	padding-top:50px;
	font-size:20px;	
	border:none;
}
#tptj{
	border:none;
}
/*
#table2 tr>td:first-child{
	background-color:#ADD8E6;	
}



td input{
	font-family:SimHei;
	font-size:15px;	
	margin-top:0px;
	margin-bottom:0px;
	height:20px; 
	width:100%;
	border:none;
}

td a{
	cursor:pointer;
} */

</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/customquery.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>

<odin:hidden property="bz00" title="主键id"></odin:hidden>
<odin:hidden property="tp00"/>
<odin:hidden property="b0111"/>
<odin:hidden property="rmbs"/>
<odin:hidden property="a0101"/>
<%-- <odin:hidden property="a0000"/> --%>

<odin:hidden property="a1701Word"/>
<odin:hidden property="a0814Word"/>
<odin:hidden property="a0215aWord"/>
<odin:hidden property="query_type"/>
<odin:hidden property="rmbs"/>
<odin:hidden property="colIndex"/>

<odin:toolBar property="toolBar6" applyTo="btnToolBarDiv">
				<odin:fill></odin:fill>
				<%-- <odin:buttonForToolBar text="删除" icon="images/back.gif" cls="x-btn-text-icon" id="delete" handler="deleteRow"></odin:buttonForToolBar> --%>
				<%--
				<odin:buttonForToolBar text="批量保存" id="saveAll" cls="x-btn-text-icon" icon="images/save.gif" ></odin:buttonForToolBar>
				--%>
			<%-- 	<odin:buttonForToolBar text="增加" id="BZZXKHAddBtn" icon="images/add.gif" cls="x-btn-text-icon"></odin:buttonForToolBar> --%>
				
				<odin:buttonForToolBar text="保存" id="save22" icon="images/save.gif" isLast="true"  cls="x-btn-text-icon" handler="save"></odin:buttonForToolBar>
</odin:toolBar>

<div id="btnToolBarDiv" align="center"></div>
<div id="bztp">
	<table width="800" id="table1" align="center" style="border: 1px solid #DDD;">
		<tr>
			<td width="120" height="120" style="text-align:center;font-family:SimHei;font-size:20px;background-color:#ADD8E6">岗位名：</td>
			<odin:textarea  property="tpgw" colspan="7" style="font-size:30px"></odin:textarea>
		</tr>
		<tr>
			<td width="120" height="120" style="text-align:center;font-family:SimHei;font-size:20px;background-color:#ADD8E6">条件设置：</td>
			<td style="text-align:left"><button onclick="TPTJClick()" type="button" style="border-radius:5px;background-color: #F08000;border: none;width:120px;height:30px;
    	cursor:pointer;color: white;text-align: center;text-decoration: none;display: inline-block;font-size: 16px;">设置调配条件</button>
		</td>
		</tr>
		<tr>
			<td width="120" height="120" style="text-align:center;font-family:SimHei;font-size:20px;background-color:#ADD8E6">推荐人员：</td>
			<odin:textarea  property="tpry" colspan="7" ondblclick="TPRYClick()" readonly="true"></odin:textarea>
		</tr>
	</table>
</div>


<%-- <odin:toolBar property="btnToolBar" applyTo="btnToolBarDiv">
				<odin:fill></odin:fill>
				<odin:buttonForToolBar text="保存" id="save" handler="save" icon="images/save.gif" cls="x-btn-text-icon" ></odin:buttonForToolBar>
				<odin:buttonForToolBar text="&nbsp;&nbsp;删除" isLast="true" icon="images/back.gif" id="delete" handler="deleteRow"></odin:buttonForToolBar>
</odin:toolBar> --%>

<script type="text/javascript">
var ctxPath = '<%= request.getContextPath() %>';
Ext.onReady(function() {
 	document.getElementById('b0111').value=parent.document.getElementById('checkedgroupid').value;
/* 	$('#lyjg').bind('click',function(){
		alert("aaa");
	})  */
/* 	document.getElementById('test').onclick=function(){
	alert('aaaa');
	} */
/* 	document.getElementById("checkedgroupid").value="testAAA" */


	closeWin();
});

function save(){	
	radow.doEvent("save.onclick");
}

/* function noticeSetgrid(){
	document.elementFromPoint(event.clientX,event.clientY);
	document.elementFromPoint(event.clientX,event.clientY);
	document.getElementById("a0101").value= document.elementFromPoint(event.clientX,event.clientY).text; 
	document.getElementById("a0101").value= document.elementFromPoint(event.clientX,event.clientY).text; 
    //其中的 obj.tagName=="A" 表示获取当前的标签是a标签
	alert(document.getElementById("a0101").value);
    radow.doEvent('noticeSetgrid');
}
 */
function openrmb(){
	$('a').bind('click',function(){
		document.getElementById("a0101").value=this.innerHTML;
		radow.doEvent('openrmb');
	}) 
	
	

  /*   $('#zzcx').bind('click',function(){
        var p = {maximizable:false,resizable:false};
        p['colIndex'] = 4;
        //alert($(this).parent().index())
        p['b0111'] = document.getElementById("checkedgroupid").value;
        p['query_type']="SZDWHZB";
        openMate(p);
       });
	
	$('#fzcx').bind('click',function(){
        var p = {maximizable:false,resizable:false};
        p['colIndex'] = 6;
        //alert($(this).parent().index())
        p['b0111'] = document.getElementById("checkedgroupid").value;
        p['query_type']="SZDWHZB";
        openMate(p);
       });
	
	$('#zscx').bind('click',function(){
        var p = {maximizable:false,resizable:false};
        p['colIndex'] = 8;
        //alert($(this).parent().index())
        p['b0111'] = document.getElementById("checkedgroupid").value;
        p['query_type']="SZDWHZB";
        openMate(p);
       }); */
}
 
 function TPTJClick(){
	 var tp00=document.getElementById('tp00').value;
	 var id = "";
	 $h.openPageModeWin('SetContition','pages.fxyp.QxypSetContition&initParams='+id,'调配条件设置',880,700,'',ctxPath,window);
 }
/* 	 $h.openWin('QxypSetContition','pages.fxyp.QxypSetContition','调配条件设置',1000,650,null,ctxPath,null,
				{tp00:tp00,scroll:"scroll:yes;"},true); */
	 
 
 function TPRYClick(){
	 var tp00=document.getElementById('tp00').value;
	 var id = "";
	 $h.openPageModeWin('Candidate','pages.fxyp.QxypCandidate&initParams='+id,'调配推荐人员',1200,600,'',ctxPath,window);	 
 }


</script>

	