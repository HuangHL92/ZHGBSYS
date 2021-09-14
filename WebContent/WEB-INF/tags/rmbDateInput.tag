<%@ tag pageEncoding="GBK" body-content="empty" small-icon=""
 display-name="任免表日期输入框(垂直居中)" description="任免表日期输入框(垂直居中) " %>
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

<div class="<%=cls %>" id="out_<%=property %>" style="overflow:hidden;display:block;">
	<div class="TAwrap <%=cls %>" id="wrapdiv_<%=property %>">   
		<div class="TAsubwrap">   
			<div class="TAcontent" id="div_<%=property %>"></div>   
		</div>   
	</div>
</div>
<input id="<%=property %>" name="<%=property %>" style="<%=textareaStyle %>" maxlength="8" value="<%=defaultValue %>"
class="<%=cls %> <%=textareaCls %>" <%=onblur==null?"":"onblur='"+onblur+"'" %> label="<%=label %>" title="<%=title %>"/>
<script>
if (window.attachEvent) {
	document.getElementById('<%=property %>').attachEvent('onblur',function(){
		<%=property %>onSubstrblurEvent()});
	document.getElementById("wrapdiv_<%=property %>").attachEvent('onclick',function(){
		onClickEvent('<%=property %>','out_<%=property %>','div_<%=property %>',false)});
}
if (window.addEventListener) {
	document.getElementById("<%=property %>").addEventListener('blur',function(){
		<%=property %>onSubstrblurEvent()});
	document.getElementById("wrapdiv_<%=property %>").addEventListener('click',function(){
		onClickEvent('<%=property %>','out_<%=property %>','div_<%=property %>',false)});
}
Ext.onReady(function(){
	<%=property %>onSubstrblurEvent();
});

function <%=property %>onSubstrblurEvent(){
	onSubstrblurEvent('<%=property %>','out_<%=property %>','div_<%=property %>','wrapdiv_<%=property %>',false,<%=offsetTop %>,'<%=label %>');
}


</script>








