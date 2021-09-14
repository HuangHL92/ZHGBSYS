<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>    
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>Insert title here</title>
<odin:head/>
</head>
<body>

<div id="west">
    <iframe src="/insiis/samples/tag/tagDemo2.jsp" width="100%" height="100%" ></iframe>
  </div>
<div id="cn">
    <p>Hi. I'm the west panel.</p>
  </div>
  <div id="center2">
  <!-- iframe src="tagDemo4.jsp" width="100%" height="100%"></iframe-->
  </div>
  <odin:westLayOut contentDiv="west" title="业务菜单" height="300" >
     <odin:itemModel>
        <odin:layOutItem contentDiv="cn" title="业务菜单1"></odin:layOutItem>
        <odin:layOutItem contentDiv="center2" title="业务菜单2" isLast="true"></odin:layOutItem>
     </odin:itemModel>
  </odin:westLayOut>  
</body>
</html>