<%@include file="/commform/basejs/loading/loading.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin-commform.tld" prefix="odin"%>
<%@ page import="com.insigma.siis.local.pagemodel.comm.CommonQueryBS" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>����excel�ļ�</title>
<odin:head/>
</head>
<%   String cbd_id=(java.net.URLDecoder.decode(request.getParameter("cbd_id"), "UTF-8")==null?"":java.net.URLDecoder.decode(request.getParameter("cbd_id"), "UTF-8")) ;
	request.getSession().setAttribute("cbd_id",cbd_id);
	String cbd_name = java.net.URLDecoder.decode(request.getParameter("cbd_name"), "UTF-8");
	String bj_cbdid = (java.net.URLDecoder.decode(request.getParameter("bj_cbdid"), "UTF-8")==null?"":java.net.URLDecoder.decode(request.getParameter("bj_cbdid"), "UTF-8")) ;
	CommonQueryBS.systemOut(bj_cbdid);
    String personname = java.net.URLDecoder.decode(request.getParameter("personname"), "UTF-8");
%>
<body >
<odin:base>

<form name="simpleExcelForm" method="post"  action="<%=request.getContextPath()%>/CBDFiledownServlet?method=getZipFile"  target="simpleExpFrame">
<odin:groupBox title="������Ϣ">
<table align="center" width="96%">
	<tr>
		<odin:textEdit property="linkpsn" required="true" size="38" label="��ϵ��"/>
	</tr>
	<tr>
		<odin:textEdit property="linktel" required="true" size="38" label="��ϵ�绰"/>
	</tr>
	<tr>
		<odin:textarea property="remark" colspan="200" label="�� ע"/>
	</tr>
	<tr>
		<td height="10" colspan="2"></td>
	</tr>
	<tr>
		<td align="right" colspan="2">
			<input type="button" style="cursor:hand;"  onclick="formSubmit()" value="�������ݰ�">
		</td>
	</tr>
	<tr>
		<td height="6" colspan="2"></td>
	</tr>
</table>
</odin:groupBox>	
	<input type="hidden" name="cbd_id" value="<%=(request.getParameter("cbd_id")==null?"":request.getParameter("cbd_id"))%>"/>
	<input type="hidden" name="cbd_name" value="<%=cbd_name%>"/>
	<input type="hidden" name="flag" value="<%=(request.getParameter("flag")==null?"":request.getParameter("flag")) %>">
	<input type="hidden" name="bj_cbdid" value="<%=bj_cbdid%>" id="bj_cbdid"/>
	<input type="hidden" name="personname" value="<%=personname%>" id="personname"/>
</form>

<iframe id="simpleExpFrame" name="simpleExpFrame" width="0" height="0" style="display:none;" ></iframe>

<iframe id="waitForEndFrame" name="waitForEndFrame" width="0" height="0" style="display:none;" ></iframe>

<script>

/** �״������б����ݿ�ʼ */
Ext.onReady(function(){
});

function formSubmit(){
	var s = document.getElementById('bj_cbdid').value;
	if(document.getElementById('linkpsn').value!="" && document.getElementById('linktel').value!=""){
		parent.fileNameSim = undefined;
		odin.ext.get(document.body).mask('����������...', odin.msgCls);
		document.simpleExcelForm.submit();
		doCloseWin();
		//doWaitingForEnd();
		//parent.radow.doEvent('reload');
	}else{
		alert('��������ϵ�˺���ϵ�绰��');
	}
}

function doWaitingForEnd(){
	document.getElementById("waitForEndFrame").src=contextPath + "/sys/WaitForEnd.jsp";
}

function doCloseWin(){
	odin.ext.get(document.body).mask('����ˢ��ҳ��......', odin.msgCls);
	parent.doHiddenPupWin();
}
</script>

</odin:base>
<odin:response onSuccess="doSuccess"/>
</body>
</html>