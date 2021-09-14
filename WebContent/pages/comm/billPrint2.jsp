<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@page import="com.insigma.odin.framework.util.SysUtil"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title></title>
<odin:head></odin:head>
<script type="text/javascript" src="<%=request.getContextPath()%>/ReportServer?op=emb&resource=finereport.js"></script>
</head>
<body>
<%
	String preview = request.getParameter("preview");
	if("0".equals(preview)){
%>
<table align="center" width="96%">
	<tr>
		<td align="center" height="40" colspan="2"><div class="x-form-item">打印发送成功！</div></td>
	</tr>
	<tr>
		<td align="center" colspan="2">
			<input id="closeWin" type="button" style="cursor:hand;" onclick="doCloseWin()" value="&nbsp;&nbsp;关闭&nbsp;&nbsp;"></input>
			<input type="button" style="cursor:hand;" onclick="doPrint()" value="再次打印"></input>
		</td>
	</tr>
</table>
<%
	}else{
%>
<iframe id="reportFrame" width="98%" height="100%" scrolling="no"></iframe>
<%
	}
%>
</body>
<script type="text/javascript">
	var preview = "<%=preview%>";
	String.prototype.replaceAll  = function(s1,s2){    
		return this.replace(new RegExp(s1,"gm"),s2);
	}
    function doPrint(){
   		var reportlet = "<%=request.getParameter("reportlet")%>";
   		var reportletArray = reportlet.split(",");
   		for(var i=0;i<reportletArray.length;i++){
   			var temp = reportletArray[i];
   			if(temp.indexOf("/") != 0){
   				temp = "/"+temp;
   			}
   			var endIndex = temp.lastIndexOf(".cpt");
   			if(endIndex <1 || (endIndex+4) != temp.length ){ //不以cpt结尾
   				temp =  temp.replace(/[.]/gi,"/");
   				temp += ".cpt";
   			}
   			reportletArray[i] = temp;
   		}
        var src="<%=request.getContextPath()%>/ReportServer?";    
        var commonParams = "1=1";
		commonParams = commonParams + "&currentUsername=<%=SysUtil.getCurrentUser(request).getLoginname()%>";
		<%String params = request.getParameter("params");		
		if(params!=null){%>
		 	 var params = "<%=params.replace("~","&").replace("^","=")%>"; 
		<%}else{%>
			 var params = "";
		<%}%>
		if(params.indexOf("{")!=-1){ //多张报表
			params = params.substring(1,params.length-1); //去掉括号
		}else{//一张报表
			//params = commonParams + "&" + params;
		}
		var paramsArray = params.split(",");
		var paramsStr = "";
		if("1" == preview){
			var preSrc = src+"reportlet="+reportletArray[0]+"&" + commonParams;
			if(paramsArray.length>0 && paramsArray[0]!=""){
				preSrc = preSrc + "&" + paramsArray[0];
			}
			doPreview(preSrc);
			return;
		}
		for(i=0;i<paramsArray.length;i++){
			var replet;
			if(reportletArray.length > i){
				replet = reportletArray[i];
			}else{
				replet = reportletArray[0];
			}
			paramsStr = paramsStr + ",{reportlet=" + replet + "&" + commonParams + "&" + paramsArray[i] +"}" ;
		}
		paramsStr = "(" + paramsStr.substring(1) + ")" ;
		var i=0;
		paramsStr = paramsStr.replace("=",":"); //先将第一个=替换掉
		while(paramsStr.indexOf("&")!=-1){
			i = paramsStr.indexOf("&");
			paramsStr = paramsStr.substring(0,i)+","+paramsStr.substring(i+1).replace("=",":");
		}
		//paramsStr = paramsStr.replaceAll("&",",");
		src = src + "reportlets=" + paramsStr;
		var setup = '<%=request.getParameter("setup")%>';
        var printmode = "<%=request.getParameter("printmode")%>";
        if(printmode.toUpperCase()=="PDF"){
        	FR.doURLPDFPrint(src,!setup);
        }else{
        	FR.doPrintURL(src);
        }
    }
	function doPreview(src){
		src　=　FR.cjkEncode(src);
		document.getElementById("reportFrame").src = src;
	}
    function doCloseWin(){
		var windowId = "win_pup";
		parent.odin.ext.getCmp(windowId).hide();
	}
    $(function(){
    	doPrint();
    });
</script>
</html>
