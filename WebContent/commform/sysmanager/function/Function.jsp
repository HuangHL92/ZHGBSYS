<%@ page contentType="text/html; charset=GBK" language="java" %>
<%@ page import="java.util.List,
com.lbs.commons.GlobalNames,com.lbs.apps.query.QueryInfo,
java.util.ArrayList,java.util.Map,java.util.LinkedHashMap,com.lbs.cp.taglib.Formatter" %>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3" %>
<script language="javascript">
function editData(){
   var t = editObj("chk");
  if(!t){
    return t;
  }
  window.location.href="<html:rewrite page='/functionAction.do?method=findByKey'/>&stringData=" +
    document.all("stringData").value + "&" + getAlldata(document.all.tableform);
}
function delData(){
  var t = delObj("chk");
  if(!t){
    return t;
  }
    if(confirm("此操作不能回退，确信要删除您选中的代码吗？")){
      window.location.href="<html:rewrite page='/functionAction.do?method=delete'/>&stringData=" +
      	document.all("stringData").value + "&" + getAlldata(document.all.tableform);
    }else{
      return false;
    }
}

//-->
</script>
<html><sicp3:base/><sicp3:body>
<%
	String stringData = "";
	QueryInfo qi = (QueryInfo)pageContext.findAttribute(GlobalNames.QUERY_INFO);
    if(null != qi){
    	stringData = qi.getStringData();
    }
	String addFunction = "window.location.href=\"" +
           request.getContextPath() + "/sysmanager/addFunction.do?stringData=" + stringData + "\"";
  	String upload = "window.location.href=\"" +
           request.getContextPath() + "/sysmanager/upload.do?stringData=" + stringData + "\"";



  	List header=new ArrayList();
	header.add(new Formatter("functionid","权限ID"));
  	header.add(new Formatter("title","标题"));
  	header.add(new Formatter("parent","父ID"));
  	header.add(new Formatter("type","类型"));
  	header.add(new Formatter("log","是否记日志"));
	header.add(new Formatter("owner","开发人员"));

//    Map orderInPage = new LinkedHashMap();
//    orderInPage.put("functionid",Constants.H_STRING_EN);
//    orderInPage.put("title",Constants.H_STRING);
//    orderInPage.put("parent",Constants.H_STRING_EN);
//    orderInPage.put("type",Constants.H_STRING);
//    orderInPage.put("log",Constants.H_STRING);
//    orderInPage.put("owner",Constants.H_STRING);

	Map hidden=new LinkedHashMap();
    hidden.put("functionid","权限ID");

    Map buttons=new LinkedHashMap();
    buttons.put("上传权限列表",upload);
	buttons.put("增 加",addFunction);
	buttons.put("修 改","editData()");
	buttons.put("删 除","delData()");
	buttons.put("关 闭","closeWindow(\"functionQueryForm\")");

   	pageContext.setAttribute("header",header);
  	pageContext.setAttribute("hidden",hidden);
    pageContext.setAttribute("button",buttons);
//    pageContext.setAttribute("orderInPage",orderInPage);
%>
<sicp3:hidden property="stringData" value="<%=stringData%>"/>

<sicp3:title title="权限列表管理"/>
<sicp3:query action="/functionQueryAction.do?method=query"  topic="权限列表查询">
<tr>
<sicp3:texteditor property="functionid" label="权限ID" disable="false"></sicp3:texteditor>
<sicp3:texteditor property="title" label="标题" disable="false"></sicp3:texteditor>
<sicp3:texteditor property="parent" label="父ID" disable="false"></sicp3:texteditor>
</tr>
<tr>
<sicp3:texteditor property="type" label="类型" disable="false"></sicp3:texteditor>
<sicp3:texteditor property="log" label="是否记日志" disable="false"></sicp3:texteditor>
<sicp3:texteditor property="owner" label="开发人员" disable="false"></sicp3:texteditor>
</tr>
</sicp3:query>
<sicp3:table topic="权限列表信息" action="/functionQueryAction.do" headerMeta="header" hiddenMeta="hidden"
   mode="radio"/>
<sicp3:buttons buttonMeta="button"/>
<sicp3:bottom/>
<sicp3:errors/></sicp3:body></html>
