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
<%@ attribute name="vtype" required="false" %> 
<%@ attribute name="validator" required="false" %> 
<%@ attribute name="onkeypress" required="false" %>
<%@ attribute name="onchange" required="false" %>
<%@ attribute name="onfocus" required="false" %>
<%@ attribute name="onBlur" required="false" %>

<%
String ctxpathemp = request.getContextPath();
%>

<input name="<%=property%>" 
	class="<%=cls%>" id="<%=property%>" required="<%=required==null?"false":required%>" value="<%=defaultValue==null?"":defaultValue%>" label="<%=label%>"
	<%=readonly==null?"":"readonly='readonly'"%> onchange="<%=onchange==null?"":onchange%>" onBlur="<%=onBlur==null?"":onBlur%>"
	ondblclick="<%=ondblclick==null?"":ondblclick%>" onkeypress="<%=onkeypress==null?"":onkeypress%>" onfocus="<%=onfocus==null?"":onfocus%>"/>

<script type="text/javascript">
	Ext.onReady(function() {
			var <%=property%> = new Ext.form.TextField({
						allowBlank : !<%=required==null?"false":required%>,
						width : 160,
						<%=vtype==null?"":"vtype:"+"'"+vtype+"',"%>
						<%=validator==null?"":"validator:"+validator+","%>
						id : '<%=property%>',
						inputType : 'text',
						applyTo : '<%=property%>'
					});
		});
</script>
