<%@ tag pageEncoding="GBK" body-content="empty" small-icon=""
 display-name="��������������(��ֱ����)" description="��������������(��ֱ����) " %>
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
<%@ attribute name="warnCls" required="false" %>

<input name="<%=property %>" id="<%=property %>" type="hidden" value="">
<input name="<%=property %>_1"  class="<%=textareaCls %>" id="<%=property %>_1" <%=readonly!=null?"readonly=readonly":"" %> style="<%=textareaStyle %> float:left;" value="<%=defaultValue %>"
type="text" maxlength="8" label="<%=label %>" onkeypress="return event.keyCode>=48&&event.keyCode<=57" ng-pattern="/[^a-zA-Z]/" ondblclick="<%=ondblclick %>">
<div class="date-invalid-icon" id="<%=property %>_err" style="<%=warnCls%>">
	<p id="<%=property %>_desc" title="���ڸ�ʽ����ȷ��ֻ������6λ��8λ����Ч��������С�����Ҫ����1990�꣬��ȷ��ʽΪ��200808��20080804" style="width:100%;height:100%;"></p>
</div>
<script>
if (window.attachEvent) {
	document.getElementById('<%=property %>_1').attachEvent('onblur',function(){
			rmbblurDate_bj('<%=property %>',false,true);
	});
	document.getElementById("<%=property %>_1").attachEvent('onfocus',function(){rmbrestoreDate('<%=property %>');});
}

if (window.addEventListener) {
	document.getElementById("<%=property %>_1").addEventListener('blur',function(){
			rmbblurDate_bj('<%=property %>',false,true);
	});
	document.getElementById("<%=property %>_1").addEventListener('focus',function(){rmbrestoreDate('<%=property %>');});
}

Ext.onReady(function(){
	rmbblurDate_bj('<%=property %>',false,true);
});

</script>








