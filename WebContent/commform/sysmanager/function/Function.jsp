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
    if(confirm("�˲������ܻ��ˣ�ȷ��Ҫɾ����ѡ�еĴ�����")){
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
	header.add(new Formatter("functionid","Ȩ��ID"));
  	header.add(new Formatter("title","����"));
  	header.add(new Formatter("parent","��ID"));
  	header.add(new Formatter("type","����"));
  	header.add(new Formatter("log","�Ƿ����־"));
	header.add(new Formatter("owner","������Ա"));

//    Map orderInPage = new LinkedHashMap();
//    orderInPage.put("functionid",Constants.H_STRING_EN);
//    orderInPage.put("title",Constants.H_STRING);
//    orderInPage.put("parent",Constants.H_STRING_EN);
//    orderInPage.put("type",Constants.H_STRING);
//    orderInPage.put("log",Constants.H_STRING);
//    orderInPage.put("owner",Constants.H_STRING);

	Map hidden=new LinkedHashMap();
    hidden.put("functionid","Ȩ��ID");

    Map buttons=new LinkedHashMap();
    buttons.put("�ϴ�Ȩ���б�",upload);
	buttons.put("�� ��",addFunction);
	buttons.put("�� ��","editData()");
	buttons.put("ɾ ��","delData()");
	buttons.put("�� ��","closeWindow(\"functionQueryForm\")");

   	pageContext.setAttribute("header",header);
  	pageContext.setAttribute("hidden",hidden);
    pageContext.setAttribute("button",buttons);
//    pageContext.setAttribute("orderInPage",orderInPage);
%>
<sicp3:hidden property="stringData" value="<%=stringData%>"/>

<sicp3:title title="Ȩ���б����"/>
<sicp3:query action="/functionQueryAction.do?method=query"  topic="Ȩ���б��ѯ">
<tr>
<sicp3:texteditor property="functionid" label="Ȩ��ID" disable="false"></sicp3:texteditor>
<sicp3:texteditor property="title" label="����" disable="false"></sicp3:texteditor>
<sicp3:texteditor property="parent" label="��ID" disable="false"></sicp3:texteditor>
</tr>
<tr>
<sicp3:texteditor property="type" label="����" disable="false"></sicp3:texteditor>
<sicp3:texteditor property="log" label="�Ƿ����־" disable="false"></sicp3:texteditor>
<sicp3:texteditor property="owner" label="������Ա" disable="false"></sicp3:texteditor>
</tr>
</sicp3:query>
<sicp3:table topic="Ȩ���б���Ϣ" action="/functionQueryAction.do" headerMeta="header" hiddenMeta="hidden"
   mode="radio"/>
<sicp3:buttons buttonMeta="button"/>
<sicp3:bottom/>
<sicp3:errors/></sicp3:body></html>
