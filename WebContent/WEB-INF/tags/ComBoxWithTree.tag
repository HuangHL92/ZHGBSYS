<%@tag import="org.apache.commons.lang.StringUtils"%>
<%@tag import="com.insigma.siis.local.pagemodel.yngwyUtil.CodeValueUtilPageModel"%>
<%@ tag pageEncoding="GBK" body-content="empty" small-icon=""
 display-name="下拉树" description="下拉树" %>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ attribute name="property" required="true" %>
<%@ attribute name="label" required="false" %>
<%@ attribute name="required"  required="false" %>
<%@ attribute name="width" required="false" %>
<%@ attribute name="codename" required="false" %>
<%@ attribute name="codetype" required="true" %>
<%@ attribute name="disabled"  required="false" %>
<%@ attribute name="style" required="false" %>
<%@ attribute name="readonly" description="默认为true" required="false" %>
<%@ attribute name="listWidth" description="下拉框的宽度" required="false" %>
<%@ attribute name="listHeight" description="下拉框的高度" required="false" %>
<%@ attribute name="nodeDblclick" description="双击事件" required="false" %>
<%@ attribute name="ischecked" description="是否可多选"  required="false" %>
<%@ attribute name="colspan" required="false"  description="一行中所占的单元格个数" %>


<%@ attribute name="rootId" required="false" description="开发中"%>
<%@ attribute name="rootText" required="false" description="开发中"%>
<%@ attribute name="onselect" required="false" description="开发中"%>
<%@ attribute name="selectEvent" required="false" description="开发中"%>

<%
String selectStore = (String)session.getAttribute(codetype);
if(selectStore==null){
	//selectStore = CodeValueUtilPageModel.getCodeTypeJS(codetype);
	session.setAttribute(codetype,selectStore);
}

ischecked = ischecked==null?"ischecked:false,":("ischecked:true,");
String nodeDbl = nodeDblclick==null?"":("nodeDbl:"+nodeDblclick+",");
 String re_el = required=="true"?"<font color='red'>*</font>":"";
 String width_js = width==null?"width:160,":("width:"+width+",");
 String re_el2 = required=="true"?"allowBlank :false,":"";
 String readonlyStr = readonly=="false"?"":"document.getElementById('"+property+"_combotree').setAttribute('readOnly','readonly')";
 String readonlycu = readonly=="false"?"default":"pointer";
 String readonlycl = readonly=="false"?"":"this.nextSibling.click()";
 listWidth = listWidth==null?"":"listWidth:"+listWidth+",";
 listHeight = listHeight==null?"":"listHeight:"+listHeight+",";
 String disables =disabled;
if("true".equals(disables)){
	readonlycl="";
	readonlycu = "default";
	disabled="disabled";
}
if(StringUtils.isEmpty(colspan)){
	colspan = "1";
}else{
	colspan = (Integer.valueOf(colspan)-1)+"";
}

%>
<td nowrap="" align="right" class="label_class"><span class="input_class" style="FONT-SIZE: 12px" id="<%=property %>SpanId"> <%=re_el%> <%=label %>&nbsp;</span></td>
<td nowrap="" colspan="<%=colspan%>">
<div class="x-form-item">
	<div class="x-form-element">
	<input type="hidden" id="<%=property %>" name="<%=property %>" />
	<input type="text" id="<%=property %>_combotree" name="<%=property %>_combotree"  onkeyup="if(event.keyCode==8||event.keyCode==46){<%=property %>.value='';this.value='';}" style="cursor: <%=readonlycu%>;width: 160px;" onclick="<%=readonlycl %>"
		required="<%=required %>"  label="<%=label %>" <%=disabled %> />

	</div>
</div>
</td>

 <script>
 <%if(!"true".equals(disables)){%>
 Ext.onReady(function(){
	 new Ext.ux.form.ComboBoxWidthTree({
		 
	 	 selectStore:<%=selectStore %>,
		 property: '<%=property %>',
		 id:"<%=property %>_combotree",
		 label : '<%=label==null?"":label %>',
		 applyTo:"<%=property %>_combotree",
		 tpl: '<div style="height:200px;"><div id="<%=property %>_treePanel"></div></div>',
		 <%=width_js %>
		 <%=re_el2 %>
		 <%=listWidth %>
		 <%=listHeight %>
		 <%=nodeDbl %>
		 <%=ischecked %>
		 codetype:'<%=codetype %>',
		 codename:'<%=codename==null?"":codename %>'
		 
	 });
	 <%=readonlyStr %>;
 });
 <%
	}
	%>
 </script> 
