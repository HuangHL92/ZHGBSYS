<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@page import="com.insigma.odin.framework.commform.local.sys.LoginManager"%>
<%@page import="com.insigma.odin.framework.util.commform.ObjectUtil"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title></title>     
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/ReportServer?op=resource&resource=/com/fr/web/load.css"></link>
<script type="text/javascript" src="<%=request.getContextPath()%>/ReportServer?op=resource&resource=/com/fr/web/load.js"></script>
</head>
<body style='overflow:hidden'>
<table align="center" width="96%">
	<tr>
		<td align="center" height="40" colspan="2"><div style="font-size:16px;">打印发送成功！</div></td>
	</tr>
	<tr>
		<td align="center" colspan="2">
			<input id="closeWin" type="button" style="cursor:hand;font-size:13px;" onclick="doCloseWin()" value="&nbsp;&nbsp;关闭&nbsp;&nbsp;"></input>
			<input type="button" style="cursor:hand;font-size:13px;" onclick="doPrint()" value="再次打印"></input>
		</td>
	</tr>
</table>
</body>
</html>

<script type='text/javascript'>
	String.prototype.replaceAll  = function(s1,s2){    
		return this.replace(new RegExp(s1,"gm"),s2);    
	}
    function doPrint(){
   		var reportlet = "<%=request.getParameter("reportlet")%>";
   		var reportletArray = reportlet.split(",");
        var src="<%=request.getContextPath()%>/ReportServer?";    
        var commonParams = "currentAab301=<%=ObjectUtil.nvl(LoginManager.getCurrentAab301(),"null")%>";
		commonParams = commonParams + "&currentAaa027=<%=ObjectUtil.nvl(LoginManager.getCurrentAaa027(),"null")%>";
		commonParams = commonParams + "&currentLoginName=<%=ObjectUtil.nvl(LoginManager.getCurrentLoginName(),"null")%>";
		commonParams = commonParams + "&currentAaz001=<%=ObjectUtil.nvl(LoginManager.getCurrentSysuser().getString("aaz001"),"null")%>";
		commonParams = commonParams + "&currentAab023=<%=ObjectUtil.nvl(LoginManager.getCurrentSysuser().getString("aab023"),"null")%>";
		commonParams = commonParams + "&currentAaf015=<%=ObjectUtil.nvl(LoginManager.getCurrentSysuser().getString("aaf015"),"null")%>";
		<%String params = request.getParameter("params");
		if(params!=null){
			 params = params.replace("~","&").replace("^","=");
			 %>
		 	 var params = "<%=params%>"; 
		<%}else{%>
			 var params = "";
		<%}%>
		if(params.indexOf("{")!=-1){ //多张报表
			params = params.substring(1,params.length-1); //去掉括号
		}else{//一张报表
			params = commonParams + "&" + params;
		}
		var paramsArray = params.split(";");
		var paramsStr = "";
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
		src = src + "reportlets=" + paramsStr.replace(/\+/g,"%2B");
		var setup = <%=request.getParameter("setup")%>;
        var printmode = "<%=request.getParameter("printmode")%>";
        if(printmode.toUpperCase()=="PDF"){
        	FR.doPrintURL_PDF(src,!setup);
        }else{
        	FR.doPrintURL(src);
        }
    }
    function doCloseWin(){
		var windowId = "win_billprint";
		parent.Ext.getCmp(windowId).hide();
	}
    doPrint();
</script>
