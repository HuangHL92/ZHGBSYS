<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ page import="com.insigma.odin.framework.sys.entity.SbdsUserlog" %>
<%@ page import="com.insigma.odin.framework.privilege.vo.*" %>
<%@ page import="com.insigma.odin.framework.privilege.*" %>
<%@ page import="com.insigma.odin.framework.util.DateUtil" %>
<%@ page import="java.util.*"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%
	SbdsUserlog userlog=(SbdsUserlog)request.getAttribute("userlog");
	FunctionVO sysfunction=(FunctionVO)request.getAttribute("sysfunction");
	List<UserVO> userlist = PrivilegeManager.getInstance().getIUserControl().queryByName(userlog.getAae011(),true);
	UserVO user = userlist.get(0);
	String username = user.getLoginname();
%>

<html>
<head>
<odin:head/>
</head>
<body style="width:100%;height=100%">
<span style="position:absolute;top:1;left:1">
  <p class="rightup"><img src="<%=request.getContextPath()%>/images/camera.gif" width="24" height="24"></p>
</span>
<!-- <span style="position:absolute;top:7;left:720">
  <p class="rightup"><a href="javascript:history.back();"><SPAN style="FONT-SIZE: 12px">返回</SPAN></a></p>
</span> -->
<span style="position:absolute;top:2;left:80">
	<table width="600">
		<tr>
			<td align="center">
				<SPAN style="FONT-SIZE: 12px"><b><%=sysfunction.getTitle()%>&nbsp;&nbsp;由 <%= username %> 于 <%=DateUtil.formatDateTime(userlog.getAae036())%> 办理</b></SPAN>
			</td>
		</tr>
	</table>
</span>
<div id="oriPanel" style="width:100%;height=100%"></div>

</body>
</html> 
<SCRIPT LANGUAGE="JavaScript">
	Ext.onReady(function(){
	    var bh = document.body.clientHeight+10;
	    
	    var p = new Ext.Panel({
	        title: '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;操作日志',
	        collapsible:false,
	        renderTo: oriPanel,	        
	        width:'100%',
	        height:'100%',
	        html: '<Iframe id="oriSource" src="<%=request.getContextPath()%>/sys/OpLogAction.do?method=getOriSource&opseno=<%=userlog.getOpseno()%>" width="100%" height="'+ bh +'" scrolling="auto" frameborder="0" style="clear:both;margin-left:0px;margin-right:0px;float:right;"></Iframe>'
	    });
	});
</SCRIPT>