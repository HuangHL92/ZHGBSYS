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
<%@ attribute name="hiddenValue" required="false" %>
<%@ attribute name="codetype" required="false" %>
<%@ attribute name="codename" required="false" %>
<%
String ctxPath = request.getContextPath();
%>

<div class="<%=cls %>" id="out_<%=property %>" style="overflow:hidden;display:block;">
	<div class="TAwrap <%=cls %>" id="wrapdiv_<%=property %>">   
		<div class="TAsubwrap">   
			<div class="TAcontent tab4show" id="div_<%=property %>"></div>   
		</div>   
	</div>
</div>
<input type="hidden" name="<%=property %>" id="<%=property %>" value=<%=hiddenValue %>>
<textarea id="comboxArea_<%=property %>" name="comboxArea_<%=property %>" style="<%=textareaStyle %>"  readonly
class="<%=cls %> <%=textareaCls %> tab4hide"  label="<%=label %>" title="<%=title %>"  onkeydown="onlyEnterKeyPress<%=property %>()"
ondblclick="openDiseaseInfoCommonQuery('<%=property%>','<%=codetype%>','<%=codename==null?"":codename%>','<%=ctxPath%>','<%=label %>')" ><%=defaultValue %></textarea>
<script>

document.getElementById("div_<%=property %>").setAttribute("dtype", "win");
document.getElementById("div_<%=property %>").setAttribute("aimid", "comboxArea_<%=property %>");

if (window.attachEvent) {
	document.getElementById('comboxArea_<%=property %>').attachEvent('onblur',function(){
		comboxArea_<%=property %>onblurEvent();});
	document.getElementById("wrapdiv_<%=property %>").attachEvent('onclick',function(){
		onClickEvent('comboxArea_<%=property %>','out_<%=property %>','div_<%=property %>',false)});
}
if (window.addEventListener) {
	document.getElementById("comboxArea_<%=property %>").addEventListener('blur',function(){
		comboxArea_<%=property %>onblurEvent();});
	document.getElementById("wrapdiv_<%=property %>").addEventListener('click',function(){
		onClickEvent('comboxArea_<%=property %>','out_<%=property %>','div_<%=property %>',false)});
}

Ext.onReady(function(){
	comboxArea_<%=property %>onblurEvent();
});
function comboxArea_<%=property %>onblurEvent(){
	onblurEvent('comboxArea_<%=property %>','out_<%=property %>','div_<%=property %>','wrapdiv_<%=property %>',false);
}
function onlyEnterKeyPress<%=property %>(){
	if (event.keyCode == 13) { 
		openDiseaseInfoCommonQuery('<%=property%>','<%=codetype%>','<%=codename==null?"":codename%>','<%=ctxPath%>','<%=label %>');
	}
}
</script>








