<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" errorPage="" %>
<%@ page import="com.insigma.siis.local.pagemodel.comm.CommonQueryBS" %>
<%
    CommonQueryBS.systemOut("aac001:"+request.getParameter("aac001"));
    if(request.getParameter("aac001")!=null){ 	
    	CommonQueryBS.systemOut(java.net.URLDecoder.decode(request.getParameter("aac001"),"UTF-8"));
    }
%>
{
    messageCode:'0',
	facts:[{fname:'住院累计',fvalue:192.12 },{fname:'门诊累计',fvalue:22.12 },{fname:'特殊病累计',fvalue:0.0}],
	regist:[{rname:'异地安置',rid:10},{rname:'特殊病',rid:11},{rname:'转外就医',rid:10}]
}
