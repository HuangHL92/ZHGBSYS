<%@ tag pageEncoding="GBK" body-content="empty" small-icon="" display-name="label�ı������" description="���ı�����򣬴�label" %>
<%@ attribute name="id" required="true" description="�����ID" %>
<%@ attribute name="size" required="false" description="����򳤶�size��Ĭ��40"%>
<%@ attribute name="label" required="true" description="���������"%>
<%@ attribute name="maxlength" required="false" description="�����������������ַ�����"%>
<%
if(size==null || "".equals(size.trim())){
	size = "40"; //
}
if(label!=null && !"".equals(label.trim())){
%>
	<label for="<%=id%>" style="font-size: 16px"><%=label%>��</label>
<%
}
String maxStr = "";
if(maxlength!=null && "".equals(maxlength.trim())){
	maxStr = "maxlength=\""+this.maxlength+"\"";
}
%>
<input type="text" id="<%=id%>" name="<%=id%>" size="<%=size%>" <%=maxStr%>/>