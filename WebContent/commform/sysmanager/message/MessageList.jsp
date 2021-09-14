<%@ page contentType="text/html; charset=GBK" language="java" %>
<%@ page import="java.util.List,java.util.ArrayList,com.lbs.cp.taglib.Editor,
	java.util.Map,java.util.LinkedHashMap,com.lbs.cp.taglib.Formatter"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<html><sicp3:base/><sicp3:body>
<%
	List editors=new ArrayList();
  	editors.add(new Editor("text","roleName","角色名称"));
  	editors.add(new Editor("text","roledesc","角色描述"));

  	List header=new ArrayList();
	header.add(new Formatter("msgSender","发送人"));
  	header.add(new Formatter("msgSubject","主题"));
  	header.add(new Formatter("msgMessage","消息内容"));
  	header.add(new Formatter("msgSendtime","发送时间"));
  	header.add(new Formatter("codeIsread","状态"));

	Map hidden=new LinkedHashMap();
    hidden.put("msgId","信息id");

	pageContext.setAttribute("editor",editors);
   	pageContext.setAttribute("header",header);
  	pageContext.setAttribute("hidden",hidden);
%>
<sicp3:table topic="系统消息" action="messageAction.do" headerMeta="header" hiddenMeta="hidden" mode="radio"/>
<sicp3:bottom/>
<sicp3:errors/>
</sicp3:body></html>
