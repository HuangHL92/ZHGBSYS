<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@page import="java.io.InputStream"%>
<%@page import="com.insigma.odin.framework.sys.SysfunctionManager"%>
<%
	String pagePaneUrl = "";
%>
<%@page import="com.insigma.odin.framework.util.SysUtil"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>����</title>
<odin:head />
<odin:MDParam></odin:MDParam>
<script type="text/javascript" src="<%=request.getContextPath()%>/ReportServer?op=resource&resource=/com/fr/web/load.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/ReportServer?op=resource&resource=/com/fr/web/core/page.css"></link>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/ReportServer?op=resource&resource=/com/fr/web/load.css"></link>
<script type="text/javascript">
	function doQuery(){
		pagePaneLoad();
	}
	function doReset(){
		window.location.reload();
	}
	function rep_opPage(obj){ //����ҳ���������һҳ����һҳ����ӡ�ȣ�
		var op = "";
		if(obj.id){
			op = obj.id;
		}else{
			op = obj;
		}
		if(op=="printPage"){
			op = "flashPrint";
		}else if(op=="exportPage"){
			op = "exportReportToExcel";
		}else if(op=="gotoPreviousPage"){
			odin.ext.getCmp("gotoNextPage").enable();
		}else if(op=="gotoNextPage"){
			odin.ext.getCmp("gotoPreviousPage").enable();
		}else if(op=="gotoFirstPage"){
			odin.ext.getCmp("gotoNextPage").enable();
		}else if(op=="gotoLastPage"){
			odin.ext.getCmp("gotoPreviousPage").enable();
		}
		eval("pagePane." + op + "();");
	}
</script>
</head>
<body>
<odin:base>
		<odin:toolBar property="defaultRepToolBar">
			<odin:textForToolBar text="<div id=pnum></div>" />
			<odin:fill />
			<odin:buttonForToolBar id="gotoFirstPage" text="��ҳ" handler="rep_opPage" />
			<odin:buttonForToolBar id="gotoPreviousPage" text="��һҳ" handler="rep_opPage" />
			<odin:buttonForToolBar id="gotoNextPage" text="��һҳ"handler="rep_opPage"/>
			<odin:buttonForToolBar id="gotoLastPage" text="ĩҳ" handler="rep_opPage" />
			<odin:buttonForToolBar id="pageSetup" text="ҳ������" handler="rep_opPage"/>
			<odin:buttonForToolBar id="printPage" text="��ӡ" handler="rep_opPage"/>
			<odin:buttonForToolBar id="exportPage" text="����" handler="rep_opPage"/>
			<odin:buttonForToolBar text="��ѯ" handler="doQuery"/>
			<odin:buttonForToolBar isLast="true" text="ˢ��" handler="doReset"/>
		</odin:toolBar>
		<%
			String method = request.getParameter("method");
			int n = (method.indexOf("-") >= 0 ? method.indexOf("-") : method.length());
			String url = method.substring(0, n).replace(".", "/").toLowerCase();
			String param1 = SysfunctionManager.getModuleSysfunction().getParam1();
			if (param1!=null && !"".equals(param1)) {
				pagePaneUrl = param1;
			} else {
				pagePaneUrl = "/" + url + ".cpt";
			}
			pagePaneUrl = request.getContextPath() + "/ReportServer?reportlet=" + pagePaneUrl;
			String pageurl = "/pages/" + url + ".jsp";
			InputStream inputStream = request.getSession().getServletContext().getResourceAsStream(pageurl);
			if (inputStream != null) { //���ļ�
				inputStream.close();
		%>
		<div id="repQueryDiv"><jsp:include page="<%=pageurl%>" /></div>
		<%
			} else { //���ļ�
				//String PACKAGE_NAME = "com.insigma.siis.local.business";
				//String className = request.getParameter("method").substring(0, n);
				//String businessBSName = PACKAGE_NAME + "." + className + "BS";						
		%>
		<div id="repQueryDiv"></div>
		<%
			}
		%>
		<odin:panel contentEl="repQueryDiv" property="queryPanel"  topBarId="defaultRepToolBar" />

		<div id="reportPane"></div>

</odin:base>
</body>

<script type="text/javascript"><!--       
	$(function() {      
	//var $toolbar = $("#div_1");      
	var $reportPane = $("#reportPane").PagePane();       
	// ȡ��ʵ�ʵ�PagePane����PagePane��ȫ�ֱ���      
	pagePane = $reportPane.data("PagePane");// james:get object of the pagepane      

	// ��ѯ֮�����     
	pagePane.on("afterload", function(){  
		<%if("true".equals(request.getParameter("exp"))){%> 
		  //rep_opPage('exportReportToExcel');
		  parent.Ext.get(parent.document.body).unmask();
		<%}%> 
		<%if("true".equals(request.getParameter("print"))){%> 
		  //rep_opPage('flashPrint');
		  parent.Ext.get(parent.document.body).unmask();
		<%}%> 
	    // currentPageIndex�Ǵ�0��ʼ��      
	    var cPageIndex = pagePane.currentPageIndex + 1;     
	    var pv = "��" + cPageIndex + "ҳ/��" + pagePane.reportTotalPage + "ҳ";
	    if(cPageIndex==1){
	    	odin.ext.getCmp("gotoPreviousPage").disable();
	    }
	    if(cPageIndex == pagePane.reportTotalPage){
	    	odin.ext.getCmp("gotoNextPage").disable();
	    }
	    document.getElementById("pnum").innerHTML = "<b>"+pv+"</b>";   
	});      
	 
	<%if("true".equals(request.getParameter("query"))||"true".equals(request.getParameter("exp"))||"true".equals(request.getParameter("print"))){%>
		doQuery();
	<%}else{%> 
	    // ����      
		var $container = $("<div>").appendTo($("body")).css({height:document.body.clientHeight-document.getElementById("repQueryDiv").offsetHeight-32, width:"100%"}).__border__([{      
		region : "center", el : $reportPane   }]);      
		$container.doLayout();      
		$(window).resize(function() {      
			$container.doLayout();      
		})
	<%}%>
	});
	
	function pagePaneLoad(){ //����
		var href = window.location.href;
		var src="<%=pagePaneUrl%>";
		if(href.indexOf("?")!=-1){ //���ⲿ�Ĳ���Ҳ��Ϊ����Ĳ���
			src = src + "&" + href.substr(href.indexOf("?")+1);
		}
		src = src + "&fr_filename="+(href.indexOf(".do?")!=-1?MDParam.title:"") + new Date().format("Ymd");
		src = src + "&currentUsername=<%=SysUtil.getCurrentUser(request).getLoginname()%>";
		var conditions = document.getElementsByTagName("input");
		for(var i=0;i<conditions.length;i++){
			var o = conditions[i];
			src += "&" + (o.name?o.name:o.id) + "=" + o.value;
		}
		src��= src.replace(/%/g,'��'); //%����˫�ֽڵģ����������bug
		src��=��FR.cjkEncode(src);
		pagePane.load(src);
	}

	var currentUsername = "<%=SysUtil.getCacheCurrentUser().getLoginname()%>";
	function logoff(){
		window.top.location.href = '<%=request.getContextPath()%>' + '/logoffAction.do'
	}
	Ext.onReady(
		function(){
			pagePaneLoad();
	    }
	);
-->
</script>

</html>