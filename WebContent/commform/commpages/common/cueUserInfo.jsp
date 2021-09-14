<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ page import="com.lbs.leaf.cp.util.SysUtil"%>
<%@page import="com.insigma.odin.framework.commform.local.sys.LoginManager"%>
<%@page import="com.insigma.siis.local.comm.SysConstantSystype"%>
<%@page import="com.insigma.odin.framework.commform.local.sys.SafeManager"%>
<%@page import="com.insigma.odin.framework.util.commform.SysConst"%>
<%@page import="com.insigma.odin.framework.util.commform.ObjectUtil"%>
<script>

parent.document.all('userChName').innerText = "<%=LoginManager.getCurrentRate().equals(SafeManager.RATE_DANWEI)?LoginManager.getCurrentAab004():LoginManager.getCurrentOperatorName()%>";

parent.document.all('userid').value = "<%=SysUtil.getCacheCurrentUser().getUser().getUserid()%>";
parent.document.all('currentUsername').value = "<%=SysUtil.getCacheCurrentUser().getUser().getUsername()%>";
parent.document.all('currentRoleFlag').value = "<%=LoginManager.getCurrentRoleFlag()%>"; //1网上申报 2基数申报
parent.document.all('currentAab301').value = "<%=LoginManager.getCurrentAab301()%>"; 
parent.document.all('currentAaa027').value = "<%=LoginManager.getCurrentAaa027()%>"; 
parent.getFirstOpenInfo();
//parent.isFirstLogin("<%//=(session.getAttribute("macAddr")==null?"":((String)session.getAttribute("macAddr")))%>");
//parent.doJssbUserOperation();
<%String systype = SysConst.getServerSystype();
  if(ObjectUtil.nvl(systype,"").startsWith("wssb")){
	  systype = "sionline";
  }else{
	  systype = "insiis";
  }
%>
parent.document.all('systype').value = "<%=systype%>";
</script>
