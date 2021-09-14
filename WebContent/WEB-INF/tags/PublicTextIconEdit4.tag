<%@ tag pageEncoding="GBK" body-content="empty" small-icon=""
 display-name="公共组件查询" description="公共组件查询（简单）" %>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ attribute name="label" required="false" %>
<%@ attribute name="codetype" required="true" %>
<%@ attribute name="codename" required="false" %>
<%@ attribute name="property" required="true" %>
<%@ attribute name="required" required="false" %>
<%@ attribute name="cls" required="false" %>
<%@ attribute name="defaultValue" required="false" %>
<%@ attribute name="readonly" required="false" %>
<%@ attribute name="ondblclick" required="false" %> 
<%@ attribute name="onkeypress" required="false" %> 
<%@ attribute name="onchange" required="false" %> 
<%@ attribute name="maxlength" required="false" %> 
<%@ attribute name="width" required="false" %>
<%@ attribute name="colspan" required="false" %>

<!-- 岗位类别专用字段codevalueparameter -->
<%@ attribute name="codevalueparameter" required="false" %> 
<%
String ctxPath = request.getContextPath(); 
required = required==null?"false":required;
defaultValue = defaultValue==null?"":defaultValue;

boolean readonly2 = readonly==null?false:Boolean.valueOf(readonly);
onkeypress = readonly2?("openDiseaseInfoCommonQuery"+property+"();"):"";
ondblclick = "openDiseaseInfoCommonQuery"+property+"();";
String onclick = "openDiseaseInfoCommonQuery"+property;
%>
<script type="text/javascript">
var openDiseaseInfoCommonQuery<%=property%> = function (){
	var _e = window.event; 
	if(!_e){
		if(arguments.callee.caller){
			_e = arguments.callee.caller.arguments[0]; 
		}
	}
	if (_e&&_e.keyCode == 8) { return false;}
	var codetype = "<%=codetype%>";
    var codename = "<%=codename==null?"code_name":codename%>";
    //var nsjg = "0";
    var nsjg = null;
    if(document.getElementById('codevalueparameter')){
    	<%="ZB09".equals(codetype)?"codevalueparameter = document.getElementById('codevalueparameter').value;":""%>
    }
    var winId = "winId"+Math.round(Math.random()*10000);
    var url="pages.sysorg.org.PublicWindow&aa="+Math.random()+"&property=<%=property %>&codetype=<%=codetype %>&closewin="+winId+"&codename="+codename+"&codevalueparameter=<%=codevalueparameter %>&nsjg="+nsjg+"&subWinId="+winId;
   	//alert(url);
   	var label='<%=label==null?"":label %>';
   	$h.openPageModeWin(winId,url,label,270,385,'','<%=ctxPath%>');
	<%--if(rs!=null){
		var rss = rs.split(",");
		Ext.getCmp('<%=property%>_combo').setValue(rss[1]);
		document.getElementById('<%=property%>').value=rss[0];
		var record = {data:{value:rss[1],key:rss[0]}};
		<%=onchange==null?"":onchange+"(record);" %>
	}--%>
};
function returnwin<%=property%>(rs){
	if(rs!=null){
		var rss = rs.split(",");
		Ext.getCmp('<%=property%>_combo').setValue(rss[1]);
		document.getElementById('<%=property%>').value=rss[0];
		var record = {data:{value:rss[1],key:rss[0]}};
		<%=onchange==null?"":onchange+"(record);" %>
	}
}

</script>


<odin:select2 property="<%=property %>" required="<%=required%>" value="<%=defaultValue%>" label="<%=label%>" colspan="<%=colspan %>" 
  ondblclick="<%=ondblclick %>" onkeypress="<%=onkeypress%>" codeType="<%=codetype %>" onchange="<%=onchange %>" 
   canOutSelectList="true" maxlength="<%=maxlength%>" width="<%=width %>"></odin:select2>

<script type="text/javascript">
Ext.onReady(function() {
	var img = Ext.query("#<%=property %>_combo+img")[0];
	var obj = Ext.query("#<%=property %>_combo")[0];
	img.style.backgroundImage='url("<%=ctxPath%>/basejs/ext/resources/images/default/form/search-trigger.gif")';
	obj.readOnly=<%=String.valueOf(readonly2)%>;
	var combo = Ext.getCmp('<%=property %>_combo');
	combo.removeListener('focus',odin.comboFocus,this);
	combo.removeListener('blur', odin.doAccForSelect,this);
	img.onclick=openDiseaseInfoCommonQuery<%=property%>;
	combo.addListener('expand', function(combo){combo.list.hide();});
});
</script>