<%@page import="com.insigma.odin.framework.util.GlobalNames"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%
/**
 * **************************
 * 浙大网新恩普软件有限公司
 * 核三框架Radow核心JSP文件，禁止自行修改
 * version:6.0.1  开始受控
 *
 *
 * **************************
**/
%>
<%
	StopWatch w = new StopWatch();
	w.start();
	Logger log = Logger.getLogger("RadowJSP");
	String LOADING_JSP_PATH = GlobalNames.sysConfig.get("LOADING_JSP_PATH");
	if(LOADING_JSP_PATH==null || LOADING_JSP_PATH.trim().equals("")){
		LOADING_JSP_PATH = "loading.jsp";
	}
%>
<script>
var radow_begin = new Date().getTime(); 
</script>
<jsp:include page='<%=LOADING_JSP_PATH%>' />
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-local.tld" prefix="ol"%>
<%@ page import="com.insigma.odin.framework.safe.SafeControlCenter" %>
<%@ page import="com.insigma.odin.framework.safe.util.SafeConst" %>
<%
	out.flush();
	SafeControlCenter.getInstance(SafeConst.PDT_INSIIS).safeValidate(request,SafeConst.VT_APPCONTEXT,SafeConst.PDT_INSIIS_COMP_ODIN);
	SafeControlCenter.getInstance().safeValidate(SafeConst.VT_EXPIRATION,SafeConst.PDT_INSIIS_COMP_ODIN);
%>
<%@page import="com.insigma.odin.framework.util.StopWatch"%>
<%@page import="com.insigma.odin.framework.sys.SysfunctionManager"%>
<%@page import="com.insigma.odin.framework.util.DateUtil"%>
<%@page import="java.util.Date"%>
<%@page import="org.apache.log4j.Logger"%>
<%@page import="com.insigma.odin.framework.persistence.HBSession"%>
<%@page import="com.insigma.odin.framework.persistence.HBUtil"%>
<%@page import="com.insigma.odin.framework.sys.entity.SbdsUserlog"%>
<%@page import="com.insigma.odin.framework.sys.oplog.OpLogManager"%>
<%@page import="java.io.InputStream"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
</title>
<odin:head />
<odin:MDParam></odin:MDParam>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/shared/examples.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/shared/examples.js"></script>
<!-- 日期时间 -->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/basejs/ux/css/Spinner.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/basejs/ux/css/ColumnHeaderGroup.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/ux/ColumnHeaderGroup.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/ux/Spinner.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/ux/SpinnerField.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/ux/DateTimeField.js"></script>

<script src="<%=request.getContextPath()%>/radow/corejs/radow.js"></script>
<script src="<%=request.getContextPath()%>/radow/corejs/radow.PageModeEngine.js"></script>
<script src="<%=request.getContextPath()%>/radow/corejs/radow.util.js"></script>
<script src="<%=request.getContextPath()%>/radow/corejs/radow.renderer.js"></script>
<script src="<%=request.getContextPath()%>/radow/corejs/radow.business.js"></script>
<%
	String bs =  request.getParameter("bs")==null?"":request.getParameter("bs");
	if(!"".equals(bs)){
%>
<script src="<%=request.getContextPath()%>/radow/corejs/radow.cm.js"></script>
<%
	}
%>
<script src="<%=request.getContextPath()%>/basejs/odin.grid.menu.js"></script>
<script type="text/javascript">
var radow_loaded;
function radow_load(){
	radow_loaded = new Date().getTime();
	document.getElementById("loadedDiv").innerHTML = "文档加载时间："+(radow_loaded-radow_begin);
}
var radow_finished;
function radow_finish(){
	radow_finished = new Date().getTime();
	document.getElementById("finishedDiv").innerHTML = "页面渲染时间："+(radow_finished-radow_loaded);
	//alert(document.getElementById("loadedDiv").innerHTML+"===="+document.getElementById("finishedDiv").innerHTML);
}
</script>
</head>
<body onload="radow_load()" >


<input type="text" style="position: absolute!important;width: 0px!important;height: 0px!important;display: none;" />
<div style="position: absolute;top: 1px;left: 1px;z-index: 9000000;font-size: 12px;color: red;display: none;">
	<div id="loadedDiv"></div>
    <div id="finishedDiv"></div>
</div>

<odin:base></odin:base>

<form name="commForm" id="commForm" >
<%
	String pageUrl = (String)request.getAttribute("PageUrl");
	InputStream is = pageContext.getServletContext().getResourceAsStream(pageUrl);
	if(is==null){
		out.println("<script>window.location.href=contextPath+'/error/404.jsp';</script>");
	}else{
%>
<jsp:include page='<%=(String)request.getAttribute("PageUrl")%>'/>
<%
	}
%>
<jsp:include page='local.jsp'/>
<input type="hidden" name="radow_parent_data" id="radow_parent_data" value=""/> 

</form>

<!-- 弹出窗口 --> 
<odin:window id="win_pup" src="blank.htm" title="" modal="true" width="0" height="0" />


</body>

<script>

	var page_events = eval(<%=(String)request.getAttribute("FirstBatchEvents")%>);
	var page_element_tree = {};
	var page_pageModel = '<%=request.getParameter("pageModel")%>';
	var page_model = '<%=request.getParameter("model")==null?"":request.getParameter("model")%>';
	var page_bs = '<%=request.getParameter("bs")==null?"":request.getParameter("bs")%>';
	var page_initParams = '<%=request.getParameter("initParams")==null?"":request.getParameter("initParams")%>';
	
	var page_disabled = '<%=request.getParameter("disabled")==null?"":request.getParameter("disabled")%>';
	
	var page_url_params = "";
	try{
		page_url_params = odin.ext.urlDecode(location.search.substr(1));
	}catch(e){
		
	}
	if(page_initParams!="" && page_initParams.indexOf("=_@")<0){
		page_initParams = page_url_params["initParams"];
	}
	
	radow.radowInit();
	odin.ext.onReady(
		function(){
			radow.autoMakePageEleTree();
			radow.batchEvents(page_events);
			if(typeof radow_select_info == 'object' && radow_select_info.length>0){
				odin.loadDataForSelectStoreBatch(radow_select_info,function(){radow.doEvent('doInit');radow_finish();});
				delete radow_select_info;
			}else{
				//刚开始时需要触发初始化事件
				radow.doEvent('doInit');
				radow_finish();//统计JS运行和渲染时间
			}
			<%
				String opseno = request.getParameter("opseno");
				if(opseno!=null && !opseno.equals("")){
					HBSession hbsess=HBUtil.getHBSession();
					SbdsUserlog userlog=(SbdsUserlog)hbsess.get(SbdsUserlog.class, new Long(opseno));
					String form = OpLogManager.getOriSource(userlog.getForm());
					if(form!=null && !"".equals(form)){
						out.println("window.formData = "+form+";");
					}
				}
			%>
			if(odin.isWorkpf && typeof getWpfNavBarSetInfo!='undefined'){
				try{
					qtweb.setWebInfo(getWpfNavBarSetInfo());
				}catch(e){
				}
			}
	    }
	);
	<%
		if ("true".equals(request.getParameter("disabled"))) {
			out.println("var currentActionDisabled = true;");
		}else{
			out.println("var currentActionDisabled = false;");
		}
	%>
</script>
<script type="text/javascript">window.onerror = function(){return true;}</script>
</html>
<%
	w.stop();
	log.info(request.getContextPath()+"："+SysfunctionManager.getCurrentSysfunction().getTitle()+"————模块：【"+DateUtil.formatDateTime(new Date())+"】"+w.elapsedTime());
%>