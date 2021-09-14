<%@ tag pageEncoding="GBK" body-content="empty" small-icon=""
 display-name="任免表日期输入框" description="任免表日期输入框" %>
 <%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ attribute name="label" required="false" %>
<%@ attribute name="required" required="false" %>
<%@ attribute name="property" required="true" %>
<%@ attribute name="cls" required="false" %>
<%@ attribute name="format" required="false" %>
<%
String ctxpathemp = request.getContextPath();
%>
<input name="<%=property %>" id="<%=property %>" class="<%=cls %>" type="text" required="<%=required %>" label="<%=label %>>"/>
<script type="text/javascript">
Ext.onReady(function() {
	var <%=property %> = new Ext.form.DateField({
				id : '<%=property %>',
				format : '<%=format %>',
				maxValue : '2099-12-31',
				minValue : '1900-01-01',
				onSelect : function(m, d) {
					this.setValue(d);
					
					this.fireEvent('select', this, d);
					this.menu.hide();
				},
				applyTo : '<%=property %>'
			});
	//Ext.getCmp('<%=property %>').addListener('invalid', odin.trrigerCommInvalid);
	
	
	var img = Ext.query("#<%=property %>+img")[0];
	img.style.display="none";
	var obj = Ext.query("#<%=property %>")[0]
	var imgHidden = function(){
		Ext.query("#<%=property %>+img")[0].style.display="none";
	};
	var imgShow = function(){
		Ext.query("#<%=property %>+img")[0].style.display="block";
	}
	img.onmouseout=imgHidden;
	img.onmouseover=imgShow;
	obj.onmouseout=imgHidden;
	obj.onmouseover=imgShow;
	
});
</script>
	
