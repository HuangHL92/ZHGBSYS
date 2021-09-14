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
<%@ attribute name="codetype" required="false" %>
<%@ attribute name="selectMenuStyle" required="false" %>
<%@ attribute name="selectTDStyle" required="false" %>
<%@ attribute name="selectDivStyle" required="false" %>
<%@ attribute name="outSelect" required="false" %>

<input type="hidden" class="ipt" id="<%=property %>" name="<%=property %>" value="<%=defaultValue %>">
<input id="<%=property %>Text" name="<%=property %>Text" class="<%=cls %>" <%=onblur %> style="cursor:pointer;<%=textareaStyle %> ">
<div class="DemoView"  style="<%=selectDivStyle %> ">
	<div id="<%=property %>Menu" class="selectMenu" style="text-align:left;cursor:pointer;height:200px;width:auto;overflow-y:auto;overflow-x:hidden;_
	 <%=selectMenuStyle%>">
		<table style="min-height:0px;max-height:200px;width:auto;overflow-y:auto;overflow-x:hidden; " id="<%=property %>table" class="selecttable" border="0" cellspacing="0px" cellpadding="0px;"></table>
	<div>
	<em id="<%=property %>Arrow" class="searchArrow hh abs"></em>
</div>

<script>


Ext.onReady(function(){
	var jsonLength = CodeTypeKey.<%=codetype %>.length;
	var is<%=property %>Init = false;
	if (window.attachEvent) {
		

		document.getElementById("<%=property %>Text").attachEvent('onclick',function(){
			if(!is<%=property %>Init){
				initSelectData("<%=property %>",CodeTypeJson.<%=codetype %>,CodeTypeKey.<%=codetype %>,"<%=selectTDStyle==null?"":selectTDStyle %>");
			}
			is<%=property %>Init = true;
			showNationComobo('<%=property %>Menu','<%=property %>','<%=property %>','<%=property %>Arrow',"<%=property %>",jsonLength)});
		document.getElementById("<%=property %>Text").attachEvent('onblur',function(){
			<%=property %>onblur();});
	}
	if (window.addEventListener) {
		

		document.getElementById("<%=property %>Text").addEventListener('click',function(){
			if(!is<%=property %>Init){
				initSelectData("<%=property %>",CodeTypeJson.<%=codetype %>,CodeTypeKey.<%=codetype %>,"<%=selectTDStyle==null?"":selectTDStyle %>");
			}
			is<%=property %>Init = true;
			showNationComobo('<%=property %>Menu','<%=property %>','<%=property %>','<%=property %>Arrow',"<%=property %>",jsonLength)});
		document.getElementById("<%=property %>Text").addEventListener('blur',function(){
			<%=property %>onblur();});
	}

	
	<%=property %>setShowValue();
	
});
function <%=property %>setShowValue(){
	setSelectShowValue('<%=property %>',CodeTypeJson.<%=codetype %>,document.getElementById('<%=property %>').value,<%=outSelect==null?"false":"true"%>);
}
function <%=property %>onblur(){
	if(!<%=outSelect==null?"false":"true"%>){
		var jsonLength = CodeTypeKey.<%=codetype %>.length;
		valideNation("<%=property %>",CodeTypeJsonr.<%=codetype %>,'<%=label%>',jsonLength);
	}
}
	
</script>








