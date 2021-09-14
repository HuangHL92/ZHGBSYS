<jsp:include page='loading.jsp' />
<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@page import="com.insigma.odin.framework.util.commform.CertUtil"%>
<%@page import="com.insigma.odin.framework.sys.entity.SbdsUserlog"%>
<%@page import="com.insigma.odin.framework.commform.hibernate.HSession"%>
<%@page import="com.insigma.odin.framework.commform.hibernate.HUtil"%>
<%@page import="java.sql.Blob,java.util.List,java.util.ArrayList,com.insigma.odin.framework.sys.oplog.commform.OpLogManager;"%>
<html>  
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<head>

<odin:head></odin:head>
<script src="<%=request.getContextPath()%>/radow/corejs/radow.js"></script>
<script src="<%=request.getContextPath()%>/radow/corejs/radow.PageModeEngine.js"></script>
<script src="<%=request.getContextPath()%>/radow/corejs/radow.util.js"></script>
<script src="<%=request.getContextPath()%>/radow/corejs/radow.renderer.js"></script>
<script src="<%=request.getContextPath()%>/radow/corejs/radow.business.js"></script>
<script src="<%=request.getContextPath()%>/radow/corejs/radow.cm.js"></script>
<script src="<%=request.getContextPath()%>/basejs/odin.grid.menu.js"></script>
<script type="text/javascript">
var page_url_params = odin.ext.urlDecode(location.search.substr(1));
</script>
</head>
<odin:base></odin:base>
<%
	Long opseno = Long.valueOf(request.getParameter("opseno"));
	SbdsUserlog sbdsUserlog = new SbdsUserlog();
	HSession sess = HUtil.getHSession();
	Boolean isNeedSign = CertUtil.isNeedSign();
	List<Object> list=new ArrayList();
	list=sess.createQuery("from SbdsUserlog where opseno=:opseno").setLong("opseno",opseno).list();
	String orisource = null;
	if (list.size() == 1 ) {
		sbdsUserlog=(SbdsUserlog)list.get(0);
		Blob orisourceb=sbdsUserlog.getOrisourceb();
		orisource = OpLogManager.getOriSource(orisourceb); //解压，未压缩也不影响
	}
	if (orisource == null) {
		orisource = "此笔业务无截图界面！<br><br>注：可能为导盘等特殊业务。请通过数据界面进行查看。";
	}
    //System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"+orisource);
%>
<body>
<%=orisource%>
<script type="text/javascript">
Ext.onReady(function(){
	if(typeof loading != 'undefined'){
		loading.remove();
	}
	Ext.select(".ext-el-mask").remove();
	Ext.select(".ext-el-mask-msg").remove();
}); 
radow.radowInit();
function radow_load(){}
	var buttons=document.getElementsByTagName("button");
	for(var i=0;i<buttons.length;i++){
		buttons[i].setAttribute("disabled","true");
		//buttons[i].parentNode.removeChild(buttons[i]);
		//alert("ok");
	}
	
	var edits=document.getElementsByTagName("input");
	for(var i=0;i<edits.length;i++){
		if(edits[i].type=="checkbox"){
			//edits[i].onclick="";
			edits[i].setAttribute("disabled","true");
		}else{
			//edits[i].setAttribute("disabled","true");
			edits[i].setAttribute("readOnly","true");		
			edits[i].setAttribute("onchange","");
		}
	}
	var a=document.getElementsByTagName("a");
	for(var i=0;i<a.length;i++){
		a[i].setAttribute("disabled","true");
		a[i].setAttribute("onclick","");		
		a[i].setAttribute("href","#");
	}
	var iframe=document.getElementsByTagName('iframe');
	for(var i=0;i<iframe.length;i++){
		iframe[i].src="";
	}
  function correctPNG() 
   {
   for(var i=0; i<document.images.length; i++)
   {
      var img = document.images[i]
      var imgName = img.src.toUpperCase()
      if (imgName.substring(imgName.length-3, imgName.length) == "PNG")
         {
         var imgID = (img.id) ? "id='" + img.id + "' " : ""
         var imgClass = (img.className) ? "class='" + img.className + "' " : ""
         var imgTitle = (img.title) ? "title='" + img.title + "' " : "title='" + img.alt + "' "
         var imgStyle = "display:inline-block;" + img.style.cssText 
         if (img.align == "left") imgStyle = "float:left;" + imgStyle
         if (img.align == "right") imgStyle = "float:right;" + imgStyle
         if (img.parentElement.href) imgStyle = "cursor:hand;" + imgStyle        
         var strNewHTML = "<div " + imgID + imgClass + imgTitle
         + " onclick=\"showInfo()\"; style=\"" + "width:" + img.width + "px; height:" + img.height + "px;" + imgStyle + ";"
         + "filter:progid:DXImageTransform.Microsoft.AlphaImageLoader"
         + "(src=\'" + img.src + "\', sizingMethod='scale');\"></div>" 
         img.outerHTML = strNewHTML
         i = i-1
         }

      }
   }
   var isNeedSign=<%=isNeedSign%>;
   if (isNeedSign){
    	window.attachEvent("onload", correctPNG);
    }
    

	function showInfo(){ 
		doOpenPupWin("<%=request.getContextPath()%>/pages/commAction.do?method=wssb.psrewage.CertManager","证书信息",650,300,'');
	}
	
//-->
</script>
<odin:window id="win_pup" src="/blank.htm" title="" modal="true" width="0" height="0" />
<%
	if (isNeedSign) {
%>
<div id="tuzhangcontent"
	style="position: absolute; width: 120; height: 109; top: 30%; left: 55%; margin: 0px 0 0 -100px;">

<img src="<%=request.getContextPath()%>/commform/img/tuzhang.png" width="120"
	height="109" onclick="showInfo()" title="点击查看证书详情" /></div>
<%
	}
%>
</body>
</html>