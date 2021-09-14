<%@ tag pageEncoding="GBK" body-content="empty" small-icon=""
 display-name="任免表文本输入框(垂直居中)" description="任免表文本输入框(垂直居中) " %>
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
<%@ attribute name="textareaStyle" required="false" %>
<%@ attribute name="title" required="false" %>
<%@ attribute name="textareaCls" required="false" %>  
<%@ attribute name="type" required="false" %>
<%@ attribute name="offsetTop" required="true" %>
<%@ attribute name="divStyle" required="false" %>

<div class="<%=cls %>" id="out_<%=property %>" style="overflow:hidden;display:block;">
	<div class="TAwrap <%=cls %>" id="wrapdiv_<%=property %>">   
		<div class="TAsubwrap">   
			<div class="TAcontent" style="<%=divStyle %>"  id="div_<%=property %>"></div>   
		</div>   
	</div>
</div>
<textarea id="<%=property %>" name="<%=property %>" style="<%=textareaStyle %>" 
ondblclick="<%=ondblclick==null?"":ondblclick%>" <%=readonly==null?"":"readonly='readonly'"%> 
<%=onkeypress==null?"":"onkeypress='"+onkeypress+"(this)' onblur='"+onkeypress+"(this)'" %> 
class="<%=cls %> <%=textareaCls %>"  label="<%=label %>" title="<%=title %>"><%=defaultValue %></textarea>
<script>
if (window.attachEvent) {
	document.getElementById('<%=property %>').attachEvent('onblur',function(){
		<%=property %>onblur()});
	document.getElementById("wrapdiv_<%=property %>").attachEvent('onclick',function(){
		wrapdiv_<%=property %>onclick()});
}
if (window.addEventListener) {
	document.getElementById("<%=property %>").addEventListener('blur',function(){
		<%=property %>onblur()});
	document.getElementById("wrapdiv_<%=property %>").addEventListener('click',function(){
		wrapdiv_<%=property %>onclick()});
}

Ext.onReady(function(){
	<%=property %>onblur();
});
function wrapdiv_<%=property %>onclick(){
	onClickEvent('<%=property %>','out_<%=property %>','div_<%=property %>',false);
}
function <%=property %>onblur(){
	onblurEvent('<%=property %>','out_<%=property %>','div_<%=property %>','wrapdiv_<%=property %>',false,<%=offsetTop %>);
}
</script>








