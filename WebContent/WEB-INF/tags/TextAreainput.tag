<%@ tag pageEncoding="GBK" body-content="empty" small-icon=""
 display-name="任免表文本输入框" description="任免表文本输入框" %>
 <%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ attribute name="label" required="false" %>
<%@ attribute name="required" required="false" %>
<%@ attribute name="property" required="true" %>
<%@ attribute name="cls" required="false" %>
<%@ attribute name="defaultValue" required="false" %>
<%@ attribute name="readonly" required="false" %>
<%@ attribute name="ondblclick" required="false" %> 
<%@ attribute name="onchange" required="false" %>
<%@ attribute name="onkeypress" required="false" %>
<%@ attribute name="onkeyup" required="false" %>
<%@ attribute name="onfocus" required="false" %>
<%@ attribute name="onblur" required="false" %>
<%@ attribute name="onpropertychange" required="false" %>  
<%@ attribute name="style" required="false" %>  
<%
String ctxpathemp = request.getContextPath();
%>
<textArea name="<%=property%>" 
	class="<%=cls%>" id="<%=property%>" required="<%=required==null?"false":required%>" value="<%=defaultValue==null?"":defaultValue%>" label="<%=label%>"
	<%=readonly==null?"":"readonly='readonly'"%> ondblclick="<%=ondblclick==null?"":ondblclick%>" onkeypress="<%=onkeypress==null?"":onkeypress%>" onchange="<%=onchange==null?"":onchange%>" 
	onpropertychange="<%=onpropertychange==null?"":onpropertychange%>" onkeyup="<%=onkeyup==null?"":onkeyup%>" style="<%=style==null?"":style%>"
	onfocus="<%=onfocus==null?"":onfocus%>" onblur="<%=onblur==null?"":onblur%>"><%=defaultValue==null?"":defaultValue%></textArea>
	
<script type="text/javascript">
Ext.onReady(function(){
	var <%=property%> = new Ext.form.TextArea(
		{ 
		  id:'<%=property%>',
		  applyTo:'<%=property%>'
		});
});

</script>