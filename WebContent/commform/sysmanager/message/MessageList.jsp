<%@ page contentType="text/html; charset=GBK" language="java" %>
<%@ page import="java.util.List,java.util.ArrayList,com.lbs.cp.taglib.Editor,
	java.util.Map,java.util.LinkedHashMap,com.lbs.cp.taglib.Formatter"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<html><sicp3:base/><sicp3:body>
<%
	List editors=new ArrayList();
  	editors.add(new Editor("text","roleName","��ɫ����"));
  	editors.add(new Editor("text","roledesc","��ɫ����"));

  	List header=new ArrayList();
	header.add(new Formatter("msgSender","������"));
  	header.add(new Formatter("msgSubject","����"));
  	header.add(new Formatter("msgMessage","��Ϣ����"));
  	header.add(new Formatter("msgSendtime","����ʱ��"));
  	header.add(new Formatter("codeIsread","״̬"));

	Map hidden=new LinkedHashMap();
    hidden.put("msgId","��Ϣid");

	pageContext.setAttribute("editor",editors);
   	pageContext.setAttribute("header",header);
  	pageContext.setAttribute("hidden",hidden);
%>
<sicp3:table topic="ϵͳ��Ϣ" action="messageAction.do" headerMeta="header" hiddenMeta="hidden" mode="radio"/>
<sicp3:bottom/>
<sicp3:errors/>
</sicp3:body></html>
