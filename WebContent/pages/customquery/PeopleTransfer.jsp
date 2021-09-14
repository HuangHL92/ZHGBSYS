<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<meta http-equiv="X-UA-Compatible"content="IE=8">

<style>
body {
	background-color: rgb(223,232,246);
}
span {
	font-family: "微软雅黑"、"黑体";
	font-size: 15px;
	color: rgb(21,66,139);
}
</style>

<%
	String ctxPath = request.getContextPath();
%>
<%@include file="/comOpenWinInit.jsp" %>
<script type="text/javascript">
Ext.onReady(function() {
	document.getElementById('user').focus();
});
function setAllValue(a0101){
	document.getElementById('msg').innerHTML = a0101;
}
function checkid(){
	var loginName = document.getElementById('user').value;
	if(!loginName){
		document.getElementById('exit').innerHTML = '<img src="images/wrong.gif" />' + '<font style="color: red;font-size: 14px">用户不能为空！</font>';
		return;
	}
	var a0000 = document.getElementById('a0000').value;
	
	Ext.Ajax.request({
		 method: 'POST',   		
		 url: "<%=ctxPath%>/MsgResponse.do?method=checkid",
		 params: {'a0000':a0000,'loginName':loginName},
		 success: function(response, options){
			 var res = Ext.util.JSON.decode(response.responseText);
			 var result = res.data;
			 if(result){
				 if(result.wrong){
					 odin.alert(result.wrong);
					 return;
				 }
				 if(result.error){
					 document.getElementById('exit').innerHTML = 
						 '<img src="images/wrong.gif" />' + '<font style="color: red;font-size: 14px">' + result.error + '</font>';
					 return;
				 }
				 if(result.userName){
					 document.getElementById('exit').innerHTML = 
						 '<img src="images/right1.gif" />' + '<font style="color: green;font-size: 14px">&nbsp;检测到用户:' + result.userName + '</font>';
					 return;
				 }
			 }
//			 alert(data1);
//			 console.log('data1:'+data1);
		 },
		 failure : function(response, options){ 
		 	alert("网络异常！");
		}  
	});
}
function transfer(){
	var comment = document.getElementById('exit').innerHTML;
	if(comment.indexOf("检测到用户") <= 0){
		odin.error("请输入正确用户！");
		return;
	}
	var days = document.getElementById('days').value;
	var reg = /^[1-9]*[1-9][0-9]*$/;
	if(!reg.test(days)){
		odin.error("请输入正确的有效日期(大于0的正整数日期)！");
		return;
	}else{
		if(days.length > 3){
			odin.error("有效日期不能大于999！");
			return;
		}
	}
	var name = document.getElementById('msg').innerHTML;
	var loginName = document.getElementById('user').value;
	var param = name + "," + loginName + "," +days;
	if(comment.indexOf("检测到用户") > 0){
		radow.doEvent("trueTransfer",param);
	}
}

function doubleA0000(name){
	odin.error(name + " 正在人员调转中（人员不可被多次同时调转）",function(){
		radow.doEvent("closeWin.onclick");
	});
}

function transferSucces(){
	odin.alert("人员调转成功！",function(){
		parent.odin.ext.getCmp('peopleTrans').close();
		window.realParent.reloadGrid();
	});
}
</script>

<odin:hidden property="a0000"/>
<odin:hidden property="a0101"/>

<div id="toolDiv" style="width: 433px;"></div>
<odin:toolBar property="floatToolBar" applyTo="toolDiv">
	<odin:fill/>
	<odin:buttonForToolBar text="确认调转" id="saveid" handler="transfer" icon="images/icon/exp.png" cls="x-btn-text-icon" isLast="true"/>
	<%-- <odin:buttonForToolBar text="关闭" id="closeWin" icon="images/wrong.gif" cls="x-btn-text-icon" /> --%>
</odin:toolBar>

<br>
&nbsp;&nbsp;<span>现将&nbsp;<font id="msg" style="color: red;"></font>&nbsp;的信息调转给</span>
<br><br>
<table>
	<tr>
		<td width="70"></td>
		<td><span>用户：</span></td>
		<td><input type="text" id="user" value="" onblur="checkid()" width="50"/></td>
		<td></td>
	</tr>
	<tr>
		<td></td>
		<td></td>
		<td><span id="exit">&nbsp;</span></td>
	</tr>
	<tr>
		<td></td>
		<td><span>&nbsp;</span></td>
	</tr>
	<tr>
		<td></td>
		<td><span>有效期：</span></td>
		<td><input type="text" id="days" value="" width="50"/></td>
		<td><span>天</span></td>
	</tr>
	<tr>
		<td height="20"></td>
	</tr>
</table>

<br>
<span style="color: red;font-size: 14px">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：转入用户若超过有效日期仍未处理该人员，则人员将转移至 “工作台”——“事务提醒”——“退回人员”一栏。</span>



















