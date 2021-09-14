<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>

<%
String ContextPath = request.getContextPath();
String username = request.getParameter("username");
String type = request.getParameter("type");
String object = request.getParameter("object");
String info = request.getParameter("info");
String start = request.getParameter("start");
String end = request.getParameter("end");
username = java.net.URLDecoder.decode(username,"utf-8");
type = java.net.URLDecoder.decode(type,"utf-8");
object = java.net.URLDecoder.decode(object,"utf-8");
info = java.net.URLDecoder.decode(info,"utf-8");
start = java.net.URLDecoder.decode(start,"utf-8");
end = java.net.URLDecoder.decode(end,"utf-8");
%>

<%@page import="java.io.File"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="java.math.BigDecimal"%>
<script type="text/javascript">
var isfinishE = null;

function updateProgress(cur,total){
	Ext.MessageBox.updateProgress(cur / total, '正在处理第' + cur + '条，一共'+total+'条');
}
var doprocess = function(){
	Ext.MessageBox.progress('请等待', '正在生成日志文件...');
	isfinishE = setInterval("radow.doEvent('isfinish');",100);
}

</script>
<odin:hidden  property="username"/>
<odin:hidden property="type"/>
<odin:hidden property="object"/>
<odin:hidden property="start"/>
<odin:hidden property="end"/>
<odin:hidden property="info"/>
<odin:hidden property="path"/>
<div align="center">
<br>


<%
String path = this.getServletContext().getRealPath("/")+"pages\\sysmanager\\ZWHZYQ_001_004\\log\\";
/*读取文件*/
File file = new File(path);
if(file.isDirectory()){
	File[] files = file.listFiles();
	
	for(File f : files){
		BigDecimal bg = new BigDecimal(f.length());
		Double d = bg.divide(new BigDecimal(1024*1024),2, BigDecimal.ROUND_HALF_UP).doubleValue();  
		String htmla = "<a href='javascript:void(0)' onclick=\"openp('"+ContextPath+"/FileDownServlet?path="+URLEncoder.encode(URLEncoder.encode(f.getName(),"utf8"),"utf8")+"')\" >下载</a>";
		out.write(f.getName()+htmla+"  "+d.toString()+"MB"+"<br/>");
	}
	
}
%>
<br>
<odin:button text="&nbsp;&nbsp;重&nbsp;新&nbsp;备&nbsp;份&nbsp;&nbsp;" property="rebackup"></odin:button>
</div>		
	

<script type="text/javascript">
function openp(url){
	window.open(url);
}
Ext.onReady(function(){
	document.getElementById('username').value='<%=username%>';
	document.getElementById('type').value='<%=type%>';
	document.getElementById('object').value='<%=object%>';
	document.getElementById('info').value='<%=info%>';
	document.getElementById('start').value='<%=start%>';
	document.getElementById('end').value='<%=end%>';
	document.getElementById('path').value='<%=path.replaceAll("\\\\","/")%>';
});

</script>





