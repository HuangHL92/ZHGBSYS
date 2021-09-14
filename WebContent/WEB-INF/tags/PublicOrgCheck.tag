<%@ tag pageEncoding="GBK" body-content="empty" small-icon=""  display-name="公共组件查询" description="公共组件查询（简单）" %>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ attribute name="property" required="true" %>
<%@ attribute name="label" required="false" %>
<%@ attribute name="rows" required="false" %>
<%@ attribute name="colspan" required="false" %>
<%@ attribute name="cols" required="false" %>
<%@ attribute name="style" required="false" %>

<script type="text/javascript">
<% 
	String contextpath = request.getContextPath();
	if(rows==null || rows.equals("")){
		rows = "5";
	}
	if(colspan==null || colspan.equals("")){
		colspan = "3";
	}
	if(cols == null || cols.equals("")){
		cols = "30";
	}
%>

var queryOrgs = function (){
	var winId = "winId"+Math.round(Math.random()*10000);
	var url = "<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.sysorg.org.SysOrgTree&property=<%=property %>&closewin="+winId;
	odin.openWindow(winId,'选择机构',url,266,455);
};
function returnwin<%=property%>(rs){
	if(rs!=null){
		var resulta = rs.split("{,}");
		document.getElementById('<%=property%>').value=resulta[0];
		document.getElementById('SysOrgTreeIds').value=resulta[1];
		//document.getElementById('b0101name').focus();
	}
}
function myBrowser(){
    var userAgent = navigator.userAgent; //取得浏览器的userAgent字符串
    var isOpera = userAgent.indexOf("Opera") > -1;
    if (isOpera) {
        return "Opera"
    }; //判断是否Opera浏览器
    if (userAgent.indexOf("Firefox") > -1) {
        return "FF";
    } //判断是否Firefox浏览器
    if (userAgent.indexOf("Chrome") > -1){
  		return "Chrome";
 	}
    if (userAgent.indexOf("Safari") > -1) {
        return "Safari";
    } //判断是否Safari浏览器
    if (userAgent.indexOf("compatible") > -1 && userAgent.indexOf("MSIE") > -1 && !isOpera) {
        return "IE";
    }; //判断是否IE浏览器
    return "IE";
}


</script>
<odin:textarea property="<%=property %>" label="<%=label%>" style="<%=style %>" rows="<%=rows%>" colspan="<%=colspan%>" cols="<%=cols%>"  onclick="queryOrgs()"/>
<odin:hidden property="SysOrgTreeIds" value="{}"/>