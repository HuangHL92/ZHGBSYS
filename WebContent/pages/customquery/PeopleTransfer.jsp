<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<meta http-equiv="X-UA-Compatible"content="IE=8">

<style>
body {
	background-color: rgb(223,232,246);
}
span {
	font-family: "΢���ź�"��"����";
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
		document.getElementById('exit').innerHTML = '<img src="images/wrong.gif" />' + '<font style="color: red;font-size: 14px">�û�����Ϊ�գ�</font>';
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
						 '<img src="images/right1.gif" />' + '<font style="color: green;font-size: 14px">&nbsp;��⵽�û�:' + result.userName + '</font>';
					 return;
				 }
			 }
//			 alert(data1);
//			 console.log('data1:'+data1);
		 },
		 failure : function(response, options){ 
		 	alert("�����쳣��");
		}  
	});
}
function transfer(){
	var comment = document.getElementById('exit').innerHTML;
	if(comment.indexOf("��⵽�û�") <= 0){
		odin.error("��������ȷ�û���");
		return;
	}
	var days = document.getElementById('days').value;
	var reg = /^[1-9]*[1-9][0-9]*$/;
	if(!reg.test(days)){
		odin.error("��������ȷ����Ч����(����0������������)��");
		return;
	}else{
		if(days.length > 3){
			odin.error("��Ч���ڲ��ܴ���999��");
			return;
		}
	}
	var name = document.getElementById('msg').innerHTML;
	var loginName = document.getElementById('user').value;
	var param = name + "," + loginName + "," +days;
	if(comment.indexOf("��⵽�û�") > 0){
		radow.doEvent("trueTransfer",param);
	}
}

function doubleA0000(name){
	odin.error(name + " ������Ա��ת�У���Ա���ɱ����ͬʱ��ת��",function(){
		radow.doEvent("closeWin.onclick");
	});
}

function transferSucces(){
	odin.alert("��Ա��ת�ɹ���",function(){
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
	<odin:buttonForToolBar text="ȷ�ϵ�ת" id="saveid" handler="transfer" icon="images/icon/exp.png" cls="x-btn-text-icon" isLast="true"/>
	<%-- <odin:buttonForToolBar text="�ر�" id="closeWin" icon="images/wrong.gif" cls="x-btn-text-icon" /> --%>
</odin:toolBar>

<br>
&nbsp;&nbsp;<span>�ֽ�&nbsp;<font id="msg" style="color: red;"></font>&nbsp;����Ϣ��ת��</span>
<br><br>
<table>
	<tr>
		<td width="70"></td>
		<td><span>�û���</span></td>
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
		<td><span>��Ч�ڣ�</span></td>
		<td><input type="text" id="days" value="" width="50"/></td>
		<td><span>��</span></td>
	</tr>
	<tr>
		<td height="20"></td>
	</tr>
</table>

<br>
<span style="color: red;font-size: 14px">
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ע��ת���û���������Ч������δ�������Ա������Ա��ת���� ������̨���������������ѡ��������˻���Ա��һ����</span>



















