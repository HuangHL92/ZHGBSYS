<%@include file="/commform/basejs/loading/loading.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin-commform.tld" prefix="odin"%>
<%@page import="java.io.InputStream"%>
<%@page
	import="com.insigma.odin.framework.commform.local.sys.LoginManager"%>
<%
	String pagePaneUrl = "";
%>
<%@page import="com.insigma.odin.framework.sys.SysfunctionManager"%>
<%@page import="com.insigma.odin.framework.util.commform.ObjectUtil"%>
<%@page import="com.insigma.odin.framework.util.commform.InvokeUtil"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>����</title>


</head>
<body>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/ReportServer?op=resource&resource=/com/fr/web/core/page.css"></link>

<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/ReportServer?op=resource&resource=/com/fr/web/load.css"></link>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/ReportServer?op=resource&resource=/com/fr/web/load.js"></script>

<style type="text/css">
/*����chrome������ұ߿���±߿���ʾ��bug*/
.x-table {
	overflow: visible;
}
</style>

<script type="text/javascript">   
	//������������(jquery��ext)����������
	if($.browser.safari){
		Date.prototype.setMonth=Date.brokenSetMonth; //��ԭ���ڵ�setMonth����
	}
</script>

<odin:commformhead />
<odin:commformMDParam></odin:commformMDParam>
<odin:base>


	<odin:form action="/pages/comm/commAction.do?method=doAction"
		method="post">
		<%
			String method = request.getParameter("method");
					int n = (method.indexOf("-") >= 0 ? method.indexOf("-") : method.length());
					String url = method.substring(0, n).replace(".", "/").toLowerCase();
					String param1 = SysfunctionManager.getModuleSysfunction().getParam1();
					if (!ObjectUtil.equals(param1, "")) {
						pagePaneUrl = param1;
					} else {
						pagePaneUrl = "/" + url + ".cpt";
					}
					String pageurl = "/WEB-INF/reportlets/" + pagePaneUrl;
					pagePaneUrl = request.getContextPath() + "/ReportServer?reportlet=" + pagePaneUrl;
					//System.out.println(pagePaneUrl+"����*******************"+method);
					//System.out.println(pageurl);
					InputStream inputStream = request.getSession().getServletContext().getResourceAsStream(pageurl);
					if (inputStream != null) { //���ļ�
						inputStream.close();
					} else {
		%>δ�ҵ���ҳ�棡<%
			return;
					}
					pageurl = "/pages/" + url + ".jsp";
					inputStream = request.getSession().getServletContext().getResourceAsStream(pageurl);
					if (inputStream != null) { //���ļ�
						inputStream.close();
		%>
		<odin:commformtoolBar property="defaultRepToolBar">
			<odin:fill />
			<odin:commformbuttonForToolBar property="prevPage" text=""
				tooltip="��һҳ" handler="rep_opPage('gotoPreviousPage')"
				icon="/commform/basejs/ext/resources/images/default/grid/page-prev.gif"
				cls="x-btn-text-icon" />
			<odin:commformtextForToolBar property="pnum" text="" />
			<odin:commformbuttonForToolBar property="nextPage" text=""
				tooltip="��һҳ" handler="rep_opPage('gotoNextPage')"
				icon="/commform/basejs/ext/resources/images/default/grid/page-next.gif"
				cls="x-btn-text-icon" />
			<odin:commformbuttonForToolBar property="setupPage" text="ҳ������"
				handler="rep_opPage('pageSetup')"
				icon="/commform/images/icon/yemian.gif" cls="x-btn-text-icon" />
			<odin:commformbuttonForToolBar property="printPage" text="��ӡ"
				handler="rep_opPage('flashPrint')"
				icon="/commform/images/icon/dayin.gif" cls="x-btn-text-icon" />
			<odin:commformbuttonForToolBar property="exportPage" text="����"
				handler="rep_opPage('exportReportToExcel')"
				icon="/commform/images/icon/daochu.gif" cls="x-btn-text-icon" />
			<odin:commformbuttonForToolBar text="��ѯ" handler="doQuery"
				icon="/commform/images/icon/chaxun.gif" cls="x-btn-text-icon" />
			<odin:commformbuttonForToolBar isLast="true" text="ˢ��"
				handler="doReset" icon="commform//images/icon/chongzhi.gif"
				cls="x-btn-text-icon" />
		</odin:commformtoolBar>
		<odin:commformtoolBar property="simpleRepToolBar">
			<odin:fill />
			<odin:commformbuttonForToolBar property="prevPage" text=""
				tooltip="��һҳ" handler="rep_opPage('gotoPreviousPage')"
				icon="/commform/basejs/ext/resources/images/default/grid/page-prev.gif"
				cls="x-btn-text-icon" />
			<odin:commformbuttonForToolBar property="pnum" text="" />
			<odin:commformbuttonForToolBar property="nextPage" text=""
				tooltip="��һҳ" handler="rep_opPage('gotoNextPage')"
				icon="/commform/basejs/ext/resources/images/default/grid/page-next.gif"
				cls="x-btn-text-icon" />
			<odin:commformbuttonForToolBar property="printPage" text="��ӡ"
				handler="rep_opPage('flashPrint')"
				icon="/commform/images/icon/dayin.gif" cls="x-btn-text-icon" />
			<odin:commformbuttonForToolBar text="��ѯ" handler="doQuery"
				icon="/commform/images/icon/chaxun.gif" cls="x-btn-text-icon" />
			<odin:commformbuttonForToolBar isLast="true" text="ˢ��"
				handler="doReset" icon="/commform/images/icon/chongzhi.gif"
				cls="x-btn-text-icon" />
		</odin:commformtoolBar>
		<jsp:include page="<%=pageurl%>" />
		<%
			} else { //���ļ�
						String PACKAGE_NAME = "com.insigma.siis.local.business";
						String className = request.getParameter("method").substring(0, n);
						String businessBSName = PACKAGE_NAME + "." + className + "BS";
						if (!"true".equals(request.getParameter("exp")) && !"true".equals(request.getParameter("print")) && InvokeUtil.isExistsMethod(businessBSName, "buildForm")) { //BS���������buildForm����
		%>
		<odin:dynamic></odin:dynamic>
		<%
			} else { //���ļ���BS��buildForm
		%>
		<odin:commformtoolBar property="defaultRepToolBar">
			<odin:fill />
			<odin:commformbuttonForToolBar property="prevPage" text=""
				tooltip="��һҳ" handler="rep_opPage('gotoPreviousPage')"
				icon="/commform/basejs/ext/resources/images/default/grid/page-prev.gif"
				cls="x-btn-text-icon" />
			<odin:commformtextForToolBar property="pnum" text="" />
			<odin:commformbuttonForToolBar property="nextPage" text=""
				tooltip="��һҳ" handler="rep_opPage('gotoNextPage')"
				icon="/commform/basejs/ext/resources/images/default/grid/page-next.gif"
				cls="x-btn-text-icon" />
			<odin:commformbuttonForToolBar property="setupPage" text="ҳ������"
				handler="rep_opPage('pageSetup')"
				icon="/commform/images/icon/yemian.gif" cls="x-btn-text-icon" />
			<odin:commformbuttonForToolBar property="printPage" text="��ӡ"
				handler="rep_opPage('flashPrint')"
				icon="/commform/images/icon/dayin.gif" cls="x-btn-text-icon" />
			<odin:commformbuttonForToolBar property="exportPage" text="����"
				handler="rep_opPage('exportReportToExcel')"
				icon="/commform/images/icon/daochu.gif" cls="x-btn-text-icon" />
			<odin:commformbuttonForToolBar text="��ѯ" handler="doQuery"
				icon="/commform/images/icon/chaxun.gif" cls="x-btn-text-icon" />
			<odin:commformbuttonForToolBar isLast="true" text="ˢ��"
				handler="doReset" icon="/commform/images/icon/chongzhi.gif"
				cls="x-btn-text-icon" />
		</odin:commformtoolBar>
		<div id="div_1"></div>
		<odin:panel contentEl="div_1" property="queryPanel"
			topBarId="defaultRepToolBar" />
		<%
			}
					}
		%>
	</odin:form>

	<div id="reportPane"></div>

	<!-- �������� -->
	<odin:commformwindow id="win_pup" src="/blank.htm" title=""
		modal="true" width="0" height="0" />
</odin:base>
</body>

<script type="text/javascript">
	var repurl = "";
	var pageSqlWhere = "";
	$(function() {      
	//var $toolbar = $("#div_1");   
	var $reportPane = $("#reportPane").PagePane();       
	// ȡ��ʵ�ʵ�PagePane����PagePane��ȫ�ֱ���      
	pagePane = $reportPane.data("PagePane");// james:get object of the pagepane      
	//�Ƿ��һ�ξͲ�ѯ
	var isFirstClickedQuery = true;
	//ҳ����װ����
	var pageSqlWhere = "";
	// ��ѯ֮�����     
	pagePane.on("afterload", function(){
		window.setTimeout("Ext.get(document.body).unmask()",100);
		<%if("true".equals(request.getParameter("exp"))){%> 
		  rep_opPage('exportReportToExcel');
		  parent.Ext.get(parent.document.body).unmask();
		<%}%> 
		<%if("true".equals(request.getParameter("print"))){%> 
		  rep_opPage('flashPrint');
		  parent.Ext.get(parent.document.body).unmask();
		<%}%> 
	    // currentPageIndex�Ǵ�0��ʼ��      
	    var cPageIndex = pagePane.currentPageIndex + 1;     
	    var pv = "��" + cPageIndex + "ҳ/��" + pagePane.reportTotalPage + "ҳ";   
	    opItemLabel("","pnum",pv);
		if(isFirstClickedQuery){
			opItemVisible("","prevPage",true);
			opItemVisible("","nextPage",true);
			opItemVisible("","setupPage",true);
			opItemVisible("","printPage",true);
			opItemVisible("","exportPage",true);
			isFirstClickedQuery = false;
		}
		if(pagePane.reportTotalPage<=1){
			opItemVisible("","prevPage",false);
			opItemVisible("","pnum",false);
			opItemVisible("","nextPage",false);
			//opItemDisabled("","prevPage",true);
			//opItemDisabled("","nextPage",true);
		}else{
			opItemVisible("","prevPage",true);
			opItemVisible("","pnum",true);
			opItemVisible("","nextPage",true);
			//opItemDisabled("","prevPage",false);
			//opItemDisabled("","nextPage",false);
		}
	});      
	    
	
	 
	<%if("true".equals(request.getParameter("query"))||"true".equals(request.getParameter("exp"))||"true".equals(request.getParameter("print"))){%>
		<%if("true".equals(request.getParameter("exp"))){%>
		  window.setTimeout("parent.Ext.get(parent.document.body).mask('���ڵ���...',odin.msgCls)",100);
		<%}%>
		<%if("true".equals(request.getParameter("print"))){%>
		  window.setTimeout("parent.Ext.get(parent.document.body).mask('���ڴ�ӡ...',odin.msgCls)",100);
		<%}%>
		function doQuery(){
			pagePaneLoad();
			opItemDisabled("","doQuery",true);
		}
		Ext.onReady(
			function(){
				doQuery();
			}
		);
	<%}
	if(!"true".equals(request.getParameter("exp"))&&!"true".equals(request.getParameter("print"))){%> 
	    // ����
		var $container = $("<div>").appendTo($("body")).css({height:document.body.clientHeight-document.getElementById("div_1").offsetHeight-32, width:"100%"}).__border__([{      
		region : "center", el : $reportPane      
		}]);
		$container.doLayout();
		$(window).resize(function() {      
			$container.doLayout();      
		})
	<%}%>
	})
	
	function setPageSqlWhere(thePageSqlWhere){
		pageSqlWhere=thePageSqlWhere;
	}
	function pagePaneLoad(){ //����
		var href = window.location.href;
		try{
			href = decodeURI(href);
		}catch(e){}
		var src="<%=pagePaneUrl%>";
		if(href.indexOf("?")!=-1){ //���ⲿ�Ĳ���Ҳ��Ϊ����Ĳ���
			src = src + "&" + href.substr(href.indexOf("?")+1);
		}
		src = src + "&fr_filename="+((href.indexOf(".do?")!=-1?MDParam.title:"") + Ext.util.Format.date(new Date(), "Ymd"));
		src = src + "&currentAab301=<%=LoginManager.getCurrentAab301()%>";
		src = src + "&currentAaa027=<%=LoginManager.getCurrentAaa027()%>";
		src = src + "&currentLoginName=<%=LoginManager.getCurrentLoginName()%>";
		src = src + "&currentAaz001=<%=LoginManager.getCurrentSysuser().getString("aaz001")%>";
		src = src + "&currentAab023=<%=LoginManager.getCurrentSysuser().getString("aab023")%>";
		src = src + "&currentAaf015=<%=LoginManager.getCurrentSysuser().getString("aaf015")%>";
		
		var queryDiv = document.getElementById("div_1");
		for (var n = 0; n < tagNames.length; n++) {
			var inputList = queryDiv.getElementsByTagName(tagNames[n]);
			for (var j = 0; j < inputList.length; j++) {
				var inputObj = inputList.item(j);
				var index = src.indexOf("&" + inputObj.name + "=");
				var name = inputObj.name;
				var value = inputObj.value;
				if (Ext.getCmp(name + "_combo")) {
					if (value == "all") {
						value = "";
					}
				}
				var param =  "&" + name + "=" + value;
				if (index >= 0) {
					var repvalue = src.substr(index, src.indexOf("&", index + 1)
									- index + 1);
					src = src.replace(repvalue, param);
				} else {
					src = src + param;
				}
			}
		}
		if(pageSqlWhere!=""){
		  src = src + "&1="+ pageSqlWhere;
		  //alert(pageSqlWhere)
		  pageSqlWhere = "";
		}
		src��= src.replace(/%/g,'��').replace(/\+/g, "%2B"); //%����˫�ֽڵģ���+����%2B���������bug
		//odin.alert(src);
		src��=��FR.cjkEncode(src);
		//odin.alert(src);
		pagePane.load(src);
		window.setTimeout("Ext.get(document.body).mask('���ڲ�ѯ...',odin.msgCls)",100);
	}
	
	function isLoaded(){
		if(pagePane.reportTotalPage==null){
			odin.error("���ȵ����ѯ���ٽ��б�������");
			return false;
		}
		return true;
	}
	
	function rep_opPage(opstr){ //����ҳ���������һҳ����һҳ����ӡ�ȣ�
		if(isLoaded()==false){
			return;
		}
		if(opstr.indexOf("(")==-1){
			opstr = opstr+"()";
		}
		pagePaneOpstr = 'pagePane.'+opstr;
		if(opstr.toLowerCase().indexOf('print')!=-1 ){ //��ӡ
			doPrint();
		}else if(opstr.toLowerCase().indexOf('export')!=-1 ){ //����
			doExp();
		}else{
			eval(pagePaneOpstr);
		}
	}

	function doInitRep(){
		opItemVisible("","prevPage",false);
		opItemVisible("","nextPage",false);
		opItemVisible("","setupPage",false);
		opItemVisible("","printPage",false);
		opItemVisible("","exportPage",false);
	}
	var currentOpseno = <%=request.getParameter("opseno")%>;
	var currentLoginName = "<%=LoginManager.getCurrentLoginName()%>";
	function logoff(){
		window.top.location.href = '<%=request.getContextPath()%>' + '/logoffAction.do'
	}
	Ext.onReady(
		function(){
			if(currentLoginName != getCurrentLoginName()){
				odin.error("��ǰ�������û����½���û���һ�£������µ�½��",logoff);
				return;
			}
			doInitRep();
			<%if("true".equals(request.getParameter("query"))||"true".equals(request.getParameter("exp"))||"true".equals(request.getParameter("print"))){%>
				//doQuery();
			<%}else{%>
				doInit();
			<%}%>
	    }
	);
</script>

</html>