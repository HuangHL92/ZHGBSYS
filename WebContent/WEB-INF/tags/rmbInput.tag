<%@ tag pageEncoding="GBK" body-content="empty" small-icon=""
 display-name="������ı������(��ֱ����)" description="������ı������(��ֱ����) " %>
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
<%@ attribute name="onfocus" required="false" %>
<%@ attribute name="onblur" required="false" %>
<%@ attribute name="validator" required="false" %>
<%@ attribute name="onpropertychange" required="false" %>  
<%@ attribute name="style" required="false" %>
<%@ attribute name="title" required="false" %>
<%@ attribute name="textareaCls" required="false" %>  
<%@ attribute name="type" required="false" %>

<input id="<%=property %>" name="<%=property %>" class="<%=cls %> <%=textareaCls %>"  readonly="<%=readonly %>"  style="<%=style %>">
							








