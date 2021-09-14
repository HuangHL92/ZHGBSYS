<%@ tag pageEncoding="GBK" body-content="empty" small-icon=""
 display-name="任免表下拉框" description="任免表文本输入框" %>
 <%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ attribute name="label" required="false" %>
<%@ attribute name="required" required="false" %>
<%@ attribute name="property" required="true" %>
<%@ attribute name="cls" required="false" %>
<%@ attribute name="codetypeJS" required="false" %> 
<%@ attribute name="onchange" required="false" %> 
<%@tag import="com.insigma.siis.local.business.helperUtil.CodeType2js"%>
<%
String ctxpathemp = request.getContextPath();
String store = (String)session.getAttribute(codetypeJS);
if(store==null){
	store = CodeType2js.getCodeTypeJS(codetypeJS);
	session.setAttribute(codetypeJS,store);
}

%>


<input name="<%=property %>" id="<%=property %>" type="hidden"/>
<input name="<%=property %>_combo"  id="<%=property %>_combo" type="text"/>
<script type="text/javascript">
var <%=property %>_comboData = <%=store %>;
var <%=property %>_store = new Ext.data.SimpleStore({
			data : <%=property %>_comboData,
			fields : ['key', 'value'],
			createFilterFn : odin.createFilterFn
		});
Ext.onReady(function() {
	var <%=property %>_combo = new Ext.form.ComboBox({
				store : <%=property %>_store,
				displayField : 'value',
				canOutSelectList : 'false',
				typeAhead : false,
				id : '<%=property %>_combo',
				mode : 'local',
				//emptyText : '请您选择...',
				cls:'<%=cls %>',
				editable : true,
				triggerAction : 'all',
				hideTrigger : false,
				onSelect : function(record, index) {
					if (this.fireEvent('beforeselect', this, record, index) !== false) {
						this.setValue(record.data[this.valueField
								|| this.displayField]);
						this.collapse();
						odin.doAccForSelect(this);
						this.fireEvent('select', this, record, index);
						<%=onchange==null?"":onchange+"(record,index); "%>
					}
				},
				applyTo : '<%=property %>_combo'
			});
	<%-- Ext.getCmp('<%=property %>_combo').addListener('blur', odin.doAccForSelect); --%>
	Ext.getCmp('<%=property %>_combo').addListener('select', odin.setHiddenTextValue);
	Ext.getCmp('<%=property %>_combo').addListener('focus', odin.comboFocus);
	Ext.getCmp('<%=property %>_combo').addListener('expand', odin.setListWidth);
	Ext.getCmp('<%=property %>_combo').addListener('invalid', odin.trrigerCommInvalid);
	
	Ext.query("#<%=property %>_combo+img")[0].style.display="none";
	
	var obj = Ext.query("#<%=property %>_combo")[0];
	var <%=property %>_width = parseInt(obj.parentNode.style.width);
	try{
		obj.parentNode.style.width=<%=property %>_width-17;
	}catch(e){
		
	}
	
	<%-- 注释掉输入框右上方图标：tongzj  2017/5/29 --%>
	<%-- Ext.query("#<%=property %>_combo+img")[0].style.right=0; --%>
	<%-- var img = Ext.query("#<%=property %>_combo+img")[0];
	var obj = Ext.query("#<%=property %>_combo")[0];
	var imgHidden = function(){
		Ext.query("#<%=property %>_combo+img")[0].style.display="none";
	};
	var imgShow = function(){
		Ext.query("#<%=property %>_combo+img")[0].style.display="block";
	}  --%>

	/* img.onmouseout=imgHidden;
	img.onmouseover=imgShow;
	obj.onmouseout=imgHidden;
	obj.onmouseover=imgShow; */
});
</script>