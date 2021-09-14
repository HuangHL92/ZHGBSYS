<%@page import="com.insigma.siis.local.pagemodel.comm.CommonQueryBS"%>
<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="UTF-8"%>
<%@include file="/comOpenWinInit2.jsp" %>
<%String ctxPath = request.getContextPath(); 
	//String pdfFilePath = request.getParameter("pdfFilePath");
	Object pdfFilePath = request.getSession().getAttribute("pdfFilePath");
	if(pdfFilePath != null){
		pdfFilePath = pdfFilePath.toString();
	}
	request.getSession().removeAttribute("pdfFilePath");
	CommonQueryBS.systemOut("pdfFilePath---->"+pdfFilePath);

%>	    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<style>
.pdfobject-container {
	width: 100%;
	max-width: 600px;
	height: expression(document.body.clientHeight+"px");
	margin: 2em 0;
}

.pdfobject { border: solid 1px #666; }
#results { padding: 1rem; }
.hidden { display: none; }
.success { color: #4F8A10; background-color: #DFF2BF; }
.fail { color: #D8000C; background-color: #FFBABA; }
</style>
<script >
	if (!document.addEventListener) {
	    // IE6~IE8
	    document.write('<script src="<%=request.getContextPath()%>/basejs/jquery/ieBetter.js"><\/script>');	
	}
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<div id="example1"></div>
</body>
<script src="<%=request.getContextPath()%>/basejs/jquery/jquery-1.7.2.min.js"></script>
<script src="<%=request.getContextPath()%>/basejs/jquery/pdfobject.js"></script>
<script>
	//PDFObject.embed("/insiis6/ziploud/5f060ec182fe43c383407ea8d6e7f576/20160603180056/1.Pdf", "#example1");
	function setpdf(){
		PDFObject.embed("<%=pdfFilePath%>", "#example1");
	} 
	
	function setpdf1(filePath){
		PDFObject.embed(filePath, "#example1");
	}
	
	function createPdf(){
	    var url = '<%=pdfFilePath%>';
	    if(url==""||url=='null'){
	       url = realParent.getPdfPath();
	       setpdf1(url);
	    }else{
	       setpdf1(url);
	    }
	} 

</script>
</html>