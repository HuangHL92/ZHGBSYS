<%@include file="/commform/basejs/loading/loading.jsp"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin-commform.tld" prefix="odin"%>
<%@page import="java.io.InputStream"%>
<%@page import="com.insigma.odin.framework.commform.local.sys.LoginManager"%>
<%
	String pagePaneUrl = "";
%>
<%@page import="com.insigma.odin.framework.sys.SysfunctionManager"%>
<%@page import="com.insigma.odin.framework.util.commform.ObjectUtil"%>
<%@page import="com.insigma.odin.framework.util.commform.InvokeUtil"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>报表</title>


</head>
<body>

<odin:commformhead />
<odin:commformMDParam></odin:commformMDParam>
<odin:base>


	<odin:form action="/pages/comm/commAction.do?method=doAction" method="post">
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
					//System.out.println(pagePaneUrl+"测试*******************"+method);
					//System.out.println(pageurl);
					InputStream inputStream = request.getSession().getServletContext().getResourceAsStream(pageurl);
					if (inputStream != null) { //有文件
						inputStream.close();
					} else {
		%>未找到此页面！<%
			return;
					}
					pageurl = "/pages/" + url + ".jsp";
					inputStream = request.getSession().getServletContext().getResourceAsStream(pageurl);
					if (inputStream != null) { //有文件
						inputStream.close();
		%>
		<odin:commformtoolBar property="defaultRepToolBar">
			<odin:fill />
			<odin:commformbuttonForToolBar property="prevPage" text="" tooltip="上一页" handler="rep_opPage('gotoPreviousPage')" icon="/commform/basejs/ext/resources/images/default/grid/page-prev.gif" cls="x-btn-text-icon" />
			<odin:commformtextForToolBar property="pnum" text="" />
			<odin:commformbuttonForToolBar property="nextPage" text="" tooltip="下一页" handler="rep_opPage('gotoNextPage')" icon="/commform/basejs/ext/resources/images/default/grid/page-next.gif" cls="x-btn-text-icon" />
			<!-- <odin:commformbuttonForToolBar property="setupPage" text="页面设置" handler="rep_opPage('pageSetup')" icon="/commform/images/icon/yemian.gif" cls="x-btn-text-icon" /> -->
			<odin:commformbuttonForToolBar property="printPage" text="打印" handler="rep_opPage('pfdPrint')" icon="/commform/images/icon/dayin.gif" cls="x-btn-text-icon" />
			<odin:commformbuttonForToolBar property="exportPage" text="导出" handler="rep_opPage('exportReportToExcel')" icon="/commform/images/icon/daochu.gif" cls="x-btn-text-icon" />
			<odin:commformbuttonForToolBar text="查询" handler="doQuery" icon="/commform/images/icon/chaxun.gif" cls="x-btn-text-icon" />
			<odin:commformbuttonForToolBar isLast="true" text="刷新" handler="doReset" icon="commform//images/icon/chongzhi.gif" cls="x-btn-text-icon" />
		</odin:commformtoolBar>
		<odin:commformtoolBar property="simpleRepToolBar">
			<odin:fill />
			<odin:commformbuttonForToolBar property="prevPage" text="" tooltip="上一页" handler="rep_opPage('gotoPreviousPage')" icon="/commform/basejs/ext/resources/images/default/grid/page-prev.gif" cls="x-btn-text-icon" />
			<odin:commformbuttonForToolBar property="pnum" text="" />
			<odin:commformbuttonForToolBar property="nextPage" text="" tooltip="下一页" handler="rep_opPage('gotoNextPage')" icon="/commform/basejs/ext/resources/images/default/grid/page-next.gif" cls="x-btn-text-icon" />
			<odin:commformbuttonForToolBar property="printPage" text="打印" handler="rep_opPage('flashPrint')" icon="/commform/images/icon/dayin.gif" cls="x-btn-text-icon" />
			<odin:commformbuttonForToolBar text="查询" handler="doQuery" icon="/commform/images/icon/chaxun.gif" cls="x-btn-text-icon" />
			<odin:commformbuttonForToolBar isLast="true" text="刷新" handler="doReset" icon="/commform/images/icon/chongzhi.gif" cls="x-btn-text-icon" />
		</odin:commformtoolBar>
		<jsp:include page="<%=pageurl%>" />
		<%
			} else { //无文件
						String PACKAGE_NAME = "com.insigma.siis.local.business";
						String className = request.getParameter("method").substring(0, n);
						String businessBSName = PACKAGE_NAME + "." + className + "BS";
						if (!"true".equals(request.getParameter("exp")) && !"true".equals(request.getParameter("print")) && InvokeUtil.isExistsMethod(businessBSName, "buildForm")) { //BS类存在且有buildForm方法
		%>
		<odin:dynamic></odin:dynamic>
		<%
			} else { //无文件无BS的buildForm
		%>
		<odin:commformtoolBar property="defaultRepToolBar">
			<odin:fill />
			<odin:commformbuttonForToolBar property="prevPage" text="" tooltip="上一页" handler="rep_opPage('gotoPreviousPage')" icon="/commform/basejs/ext/resources/images/default/grid/page-prev.gif" cls="x-btn-text-icon" />
			<odin:commformtextForToolBar property="pnum" text="" />
			<odin:commformbuttonForToolBar property="nextPage" text="" tooltip="下一页" handler="rep_opPage('gotoNextPage')" icon="/commform/basejs/ext/resources/images/default/grid/page-next.gif" cls="x-btn-text-icon" />
			<!-- <odin:commformbuttonForToolBar property="setupPage" text="页面设置" handler="rep_opPage('pageSetup')" icon="/commform/images/icon/yemian.gif" cls="x-btn-text-icon" /> -->
			<odin:commformbuttonForToolBar property="printPage" text="打印" handler="rep_opPage('flashPrint')" icon="/commform/images/icon/dayin.gif" cls="x-btn-text-icon" />
			<odin:commformbuttonForToolBar property="exportPage" text="导出" handler="rep_opPage('exportReportToExcel')" icon="/commform/images/icon/daochu.gif" cls="x-btn-text-icon" />
			<odin:commformbuttonForToolBar text="查询" handler="doQuery" icon="/commform/images/icon/chaxun.gif" cls="x-btn-text-icon" />
			<odin:commformbuttonForToolBar isLast="true" text="刷新" handler="doReset" icon="/commform/images/icon/chongzhi.gif" cls="x-btn-text-icon" />
		</odin:commformtoolBar>
		<div id="div_1"></div>
		<odin:panel contentEl="div_1" property="queryPanel" topBarId="defaultRepToolBar" />
		<%
			}
					}
		%>
	</odin:form>

	<iframe id="reportFrame" width="100%" height="200" frameborder="0" style="margin: 0px" src=""></iframe>
<
	<!-- 弹出窗口 -->
	<odin:commformwindow id="win_pup" src="/blank.htm" title="" modal="true" width="0" height="0" />
</odin:base>
</body>

<script type="text/javascript"><!--
	var initParams='<%=request.getParameter("initParams")==null?"":request.getParameter("initParams")%>';
	if (initParams!="") {
		commParams.initParams = initParams;
	}
	currentActionDisabled = '<%=request.getParameter("disabled")==null?"":request.getParameter("disabled")%>';
	if(currentActionDisabled){
		if("true" == currentActionDisabled ||currentActionDisabled==true){
			var buttons=document.getElementsByTagName("button");
			for(var i=0;i<buttons.length;i++){
				buttons[i].setAttribute("disabled","true");
			}
			var edits=document.getElementsByTagName("input");
			for(var i=0;i<edits.length;i++){
				if(edits[i].type=="checkbox"){
					edits[i].setAttribute("disabled","true");
				}else{
					edits[i].setAttribute("readOnly","true");		
					edits[i].setAttribute("onchange","");
				}
			}
		}
	}
	var repurl = "";
	var pageSqlWhere = "";
	var pagePane = document.getElementById("reportFrame");
	div.innerHTML = '<iframe id="reportFrame" width="100%" height="200" frameborder="0" style="margin: 0px" src=""></iframe>';
	Ext.onReady(function() {
	//是否第一次就查询
	var isFirstClickedQuery = true;
	//页面组装条件
	var pageSqlWhere = "";
	// 查询之后操作     
	pagePane.onload = function(){
		pagePane.contentWindow.contentPane.on("afterload",function(){
			window.setTimeout("Ext.get(document.body).unmask()",100);
			<%if("true".equals(request.getParameter("exp"))){%> 
			  rep_opPage('exportReportToExcel');
			  parent.Ext.get(parent.document.body).unmask();
			<%}%> 
			<%if("true".equals(request.getParameter("print"))){%> 
			  rep_opPage('flashPrint');
			  parent.Ext.get(parent.document.body).unmask();
			<%}%> 
			if(isFirstClickedQuery){
				opItemVisible("","prevPage",true);
				opItemVisible("","nextPage",true);
				opItemVisible("","setupPage",true);
				opItemVisible("","printPage",true);
				opItemVisible("","exportPage",true);
				isFirstClickedQuery = false;
			}     
		    var cPageIndex = pagePane.contentWindow.contentPane.currentPageIndex;     
		    var pv = "第" + cPageIndex + "页/共" + pagePane.contentWindow.contentPane.reportTotalPage + "页";   
		    opItemLabel("","pnum",pv);
			if(pagePane.contentWindow.contentPane.reportTotalPage<=1){
				opItemVisible("","prevPage",false);
				opItemVisible("","pnum",false);
				opItemVisible("","nextPage",false);
			}else{
				opItemVisible("","prevPage",true);
				opItemVisible("","pnum",true);
				opItemVisible("","nextPage",true);
			}
		})
	};      
	    
	
	 
	<%if("true".equals(request.getParameter("query"))||"true".equals(request.getParameter("exp"))||"true".equals(request.getParameter("print"))){%>
		<%if("true".equals(request.getParameter("exp"))){%>
		  window.setTimeout("parent.Ext.get(parent.document.body).mask('正在导出...',odin.msgCls)",100);
		<%}%>
		<%if("true".equals(request.getParameter("print"))){%>
		  window.setTimeout("parent.Ext.get(parent.document.body).mask('正在打印...',odin.msgCls)",100);
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
	     // 布局
		function repLayout(){
			document.getElementById("reportFrame").height=document.body.clientHeight-document.getElementById("div_1").offsetHeight-32;
		}
		repLayout();
		window.onresize=repLayout;
	<%}%>
	})
	
	function setPageSqlWhere(thePageSqlWhere){
		pageSqlWhere=thePageSqlWhere;
	}
	function pagePaneLoad(){ //加载
		var href = window.location.href;
		try{
			href = decodeURI(href);
		}catch(e){}
		var src="<%=pagePaneUrl%>";
		if(href.indexOf("?")!=-1){ //将外部的参数也作为报表的参数
			src = src + "&" + href.substr(href.indexOf("?")+1);
		}
		src = src + "&__showtoolbar__=false"; //隐藏内置工具栏
		src = src + "&__filename__="+((href.indexOf(".do?")!=-1?MDParam.title:"") + Ext.util.Format.date(new Date(), "Ymd"));
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
		src　= src.replace(/%/g,'％').replace(/\+/g, "%2B"); //%换成双字节的％，+换成%2B，否则会有bug
		//odin.alert(src);
		src　=　cjkEncode(src); 
		//alert(src);
		pagePane.src = src;
		window.setTimeout("Ext.get(document.body).mask('正在查询...',odin.msgCls)",100);
	}
	
	function isLoaded(){
		if(pagePane.contentWindow.contentPane.reportTotalPage==null){
			odin.error("请先点击查询，再进行本操作！");
			return false;
		}
		return true;
	}
	
	function rep_opPage(opstr){ //报表页面操作（上一页、下一页、打印等）
		if(isLoaded()==false){
			return;
		}
		if(opstr.indexOf("(")==-1){
			opstr = opstr+"()";
		}
		pagePaneOpstr = 'pagePane.contentWindow.contentPane.'+opstr;
		if(opstr.toLowerCase().indexOf('print')!=-1 ){ //打印
			doPrint();
		}else if(opstr.toLowerCase().indexOf('export')!=-1 ){ //导出
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
				odin.error("当前操作的用户与登陆的用户不一致！请重新登陆！",logoff);
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
--></script>

<jsp:include page='/radow/local.jsp' />
<script>
	odin.ext.onReady(
		function(){
			try{
				if(odin.isWorkpf && typeof getWpfNavBarSetInfo!='undefined'){
					qtweb.setWebInfo(getWpfNavBarSetInfo());
				}
			}catch(e){}
		}
	)
</script>

</html>