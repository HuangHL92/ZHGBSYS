<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" errorPage="" %>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%
	String ctxpath = request.getContextPath();
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>��ǩ����</title>
<odin:head />
</head>

<body>
<odin:base>

<odin:grid property="grid6" title="�ҵı��(Ĭ������Ϊ���飬�������json�ṹ)" width="600">
<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
  <odin:gridDataCol name="company" />
  <odin:gridDataCol name="price" type="float"/>
  <odin:gridDataCol name="change" type="float"/>
  <odin:gridDataCol name="pctChange" type="float"/>
  <odin:gridDataCol name="lastChange" isLast="true"/>
</odin:gridJsonDataModel>
<odin:gridColumnModel>
  <odin:gridRowNumColumn />
  <odin:gridColumn id="yes" header="��˾" width="160" dataIndex="company" />
  <odin:gridColumn  header="�۸�" dataIndex="price" />
  <odin:gridColumn  dataIndex="change" />
  <odin:gridColumn  dataIndex="pctChange" />
  <odin:gridColumn  dataIndex="lastChange" isLast="true"/>
</odin:gridColumnModel>
<odin:gridJsonData>
 {
     'totalCount':2,
	 'data':[{'id':1,'company':'��˾1','price':20.0,'change':12,'lastChange':'���ĸı�'},
	 {'id':2,'company':'��˾2','price':22.0,'change':54,'lastChange':'���ĸı�1'} ]
 }
</odin:gridJsonData>		
</odin:grid>

</odin:base>  
</body>
</html>
