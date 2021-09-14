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
<%@ attribute name="divStyle" required="false" %>
<%@ attribute name="title" required="false" %>
<%@ attribute name="textareaCls" required="false" %>  
<%@ attribute name="hiddenValue" required="false" %>
<%@ attribute name="codetype" required="false" %>
<%@ attribute name="codename" required="false" %>
<%
String ctxPath = request.getContextPath();
%>


<input type="hidden" name="<%=property %>" id="<%=property %>" value=<%=hiddenValue %>>
<input id="comboxArea_<%=property %>" name="comboxArea_<%=property %>" style="<%=textareaStyle %>" readonly="readonly"
class="<%=cls %> <%=textareaCls %>"  label="<%=label %>" title="<%=title %>"
ondblclick="openDiseaseInfoCommonQuery('<%=property%>','<%=codetype%>','<%=codename==null?"":codename%>','<%=ctxPath%>','<%=label %>')" value='<%=defaultValue %>'/>
<div id = "comboxImg_<%=property %>" class="right_qry_div" style="<%=divStyle %>" onclick="openDiseaseInfoCommonQuery('<%=property%>','<%=codetype%>','<%=codename==null?"":codename%>','<%=ctxPath%>','<%=label %>')"></div>
<script>
Ext.onReady(function(){
	
	<%if(!"orgTreeJsonData".equals(codetype)){ %>
		<%=property %>ShowPopWinValue();
	<%}%>
		
});
<%if(!"orgTreeJsonData".equals(codetype)){ %>
function <%=property %>ShowPopWinValue(){
	
	var hiddenvalue = document.getElementById('<%=property %>').value;
	if(CodeTypeJson.<%=codetype %>[hiddenvalue]){
		document.getElementById("comboxArea_<%=property %>").value=CodeTypeJson.<%=codetype %>[hiddenvalue];
	}else{
		document.getElementById("comboxArea_<%=property %>").value='';
	}
}
<%}%>

</script>








