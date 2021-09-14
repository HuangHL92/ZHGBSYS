<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" errorPage="" %>
<%@ page import="com.insigma.siis.local.pagemodel.comm.CommonQueryBS" %>
<%
    CommonQueryBS.systemOut("query:"+request.getParameter("query"));
	CommonQueryBS.systemOut("start:"+request.getParameter("start"));
	CommonQueryBS.systemOut("limit:"+request.getParameter("limit"));
    if(request.getParameter("query")!=null){ 	
    	CommonQueryBS.systemOut(java.net.URLDecoder.decode(request.getParameter("query"),"UTF-8"));
    }
%>
{'result' : 30,
	'rows': [
				 { 'psid': 1, 'name': '伟雄', 'sex': '男', 'iscode': '3301831972120700763', 'pscode': '12345678', 'birthdate':'1972-12-07','workdate':'1994-07-01','laborrel':'在保','psstatus':'在职','cpname':'IVAO China Division','mdtype':'事业在职','account':'251.45'},
				 { 'psid': 2, 'name': '1岑伟雄', 'sex': '男', 'iscode': '3301831972120700763', 'pscode': '12345676', 'birthdate':'1972-12-07','workdate':'1994-07-01','laborrel':'在保','psstatus':'在职','cpname':'IVAO China Division','mdtype':'事业在职','account':'251.45'},
				 { 'psid': 3, 'name': '陈曦', 'sex': '女', 'iscode': '3122311978042876123', 'pscode': '12345679', 'birthdate':'1978-04-28','workdate':'1999-07-15','laborrel':'在保',  'psstatus':'在职','cpname':'测试单位11111111111111111111111111111111111111111111111111111111','mdtype':'公务员在职','account':'65.15'}
			]
}
