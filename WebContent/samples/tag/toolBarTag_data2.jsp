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
	facts:[{fname:'סԺ�ۼ�',fvalue:192.12 },{fname:'�����ۼ�',fvalue:22.12 },{fname:'���ⲡ�ۼ�',fvalue:0.0}],
	regist:[{rname:'��ذ���',rid:10},{rname:'���ⲡ',rid:11},{rname:'ת���ҽ',rid:10}]
}
