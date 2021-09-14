<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ page import="com.insigma.odin.framework.sys.batch.entity.SbdsUserlog" %>
<%@ page import="com.insigma.odin.framework.sys.batch.entity.Sysfunction" %>
<%@ page import="com.lbs.cp.sysmanager.entity.SysUser" %>
<%@ page import="com.insigma.odin.framework.util.DateUtil" %>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%
	SbdsUserlog userlog=(SbdsUserlog)request.getAttribute("userlog");
	Sysfunction sysfunction=(Sysfunction)request.getAttribute("sysfunction");
	SysUser sysuser=(SysUser)request.getAttribute("sysuser");
%>

<html>
<head>
<odin:commformhead/>
</head>
<body>
<span style="position:absolute;top:1;left:1">
  <p class="rightup"><img src="<%=request.getContextPath()%>/commform/img/camera.gif" width="24" height="24"></p>
</span>
<!-- <span style="position:absolute;top:7;left:720">
  <p class="rightup"><a href="javascript:history.back();"><SPAN style="FONT-SIZE: 12px">返回</SPAN></a></p>
</span> -->
<span style="position:absolute;top:2;left:80">
	<table width="600">
		<tr>
			<td align="center">
				<SPAN style="FONT-SIZE: 12px"><b><%=sysfunction.getTitle()%>&nbsp;&nbsp;由 <%=sysuser.getOperatorname()%> 于 <%=DateUtil.formatDateTime(userlog.getAae036())%> 办理</b></SPAN>
			</td>
		</tr>
	</table>
</span>
</body>
</html> 
<SCRIPT LANGUAGE="JavaScript">
	Ext.onReady(function(){
	    var p = new Ext.Panel({
	        title: '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;操作日志',
	        collapsible:false,
	        renderTo: document.body,
	        width:785,
	        height:517,
	        html: '<Iframe id="oriSource" src="<%=request.getContextPath()%>/sys/OpLogAction.do?method=getOriSource&opseno=<%=userlog.getOpseno()%>" width="100%" height="100%" scrolling="auto" frameborder="0" style="clear:both;margin-left:0px;margin-right:0px;float:right;"></Iframe>'
	    });
	});
</SCRIPT>