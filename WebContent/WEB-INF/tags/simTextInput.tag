<%@ tag pageEncoding="GBK" body-content="empty" small-icon="" display-name="label文本输入框" description="简单文本输入框，带label" %>
<%@ attribute name="id" required="true" description="输入框ID" %>
<%@ attribute name="size" required="false" description="输入框长度size，默认40"%>
<%@ attribute name="label" required="true" description="输入框名称"%>
<%@ attribute name="maxlength" required="false" description="输入框允许输入最大字符个数"%>
<%
if(size==null || "".equals(size.trim())){
	size = "40"; //
}
if(label!=null && !"".equals(label.trim())){
%>
	<label for="<%=id%>" style="font-size: 16px"><%=label%>：</label>
<%
}
String maxStr = "";
if(maxlength!=null && "".equals(maxlength.trim())){
	maxStr = "maxlength=\""+this.maxlength+"\"";
}
%>
<input type="text" id="<%=id%>" name="<%=id%>" size="<%=size%>" <%=maxStr%>/>