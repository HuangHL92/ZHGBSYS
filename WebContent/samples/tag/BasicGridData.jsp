<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ page import="com.insigma.siis.local.pagemodel.comm.CommonQueryBS" %>
<% 
     CommonQueryBS.systemOut("test:"+request.getParameter("test"));
%>      
{
     'totalCount':2,
	 'data':[{'id':1,'company':'公司1','price':60.0,'change':12,'lastChange':'最后的改变'},
	 {'id':2,'company':'公司2','price':22.0,'change':54,'lastChange':'最后的改变1'},
	 {'id':3,'company':'公司3','price':30.0,'change':54,'lastChange':'最后的改变1'} ]
 }
