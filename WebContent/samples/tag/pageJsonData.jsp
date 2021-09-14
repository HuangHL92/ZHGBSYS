<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" errorPage="" %>
<%@ page import="com.insigma.siis.local.pagemodel.comm.CommonQueryBS" %>
<%
    CommonQueryBS.systemOut("start:"+request.getParameter("start"));
    CommonQueryBS.systemOut("limit:"+request.getParameter("limit"));
    CommonQueryBS.systemOut("test:"+request.getParameter("test"));
    if(request.getParameter("test")!=null){ 	
    	CommonQueryBS.systemOut(java.net.URLDecoder.decode(request.getParameter("test"),"UTF-8"));
    }
%>
 {
     'totalCount':200,
	 'data':[{'id':1,'company':'公司1','price':20.0,'change':12,'lastChange':'最后的改变'},
	 {'id':2,'company':'公司2','price':22.0,'change':54,'lastChange':'最后的改变1'},
	 {'id':3,'company':'公司1','price':20.0,'change':12,'lastChange':'最后的改变'},
	 {'id':4,'company':'公司1','price':20.0,'change':12,'lastChange':'最后的改变'},
	 {'id':5,'company':'公司1','price':20.0,'change':12,'lastChange':'最后的改变'},
	 {'id':6,'company':'公司1','price':20.0,'change':12,'lastChange':'最后的改变'},
	 {'id':7,'company':'公司1','price':20.0,'change':12,'lastChange':'最后的改变'},
	 {'id':8,'company':'公司1','price':20.0,'change':12,'lastChange':'最后的改变'},
	 {'id':9,'company':'公司1','price':20.0,'change':12,'lastChange':'最后的改变'},
	 {'id':10,'company':'公司1','price':20.0,'change':12,'lastChange':'最后的改变'},
	 {'id':11,'company':'公司1','price':20.0,'change':12,'lastChange':'最后的改变'},
	 {'id':12,'company':'公司1','price':20.0,'change':12,'lastChange':'最后的改变'},
	 {'id':13,'company':'公司1','price':20.0,'change':12,'lastChange':'最后的改变'}
	 ]
 }
