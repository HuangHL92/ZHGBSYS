<%@ tag pageEncoding="GBK" body-content="empty" small-icon=""
 display-name="所属行政区" description="单位信息查询（简单）" %>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ attribute name="label" required="false" %>
<%@ attribute name="required" required="false" %>
<%@ attribute name="property" required="true" %>
<%@ attribute name="url" required="true" %>
<%@ attribute name="rootId" required="false" %>
<%@ attribute name="rootText" required="false" %>
<%@ attribute name="level" required="false" %>
<%@ attribute name="height" required="false" %>
<%@ attribute name="width" required="false" %>
<%@ attribute name="rootVisible" required="false" %>
<%@ attribute name="onselect" required="false" description="当单击一个行政区进行选择时触发"%>
<%@ attribute name="selectEvent" required="false" description="选中时触发该事件"%>
<%
 	String id = null;
	String text = null;
	boolean isFromFile = true;
	if(isFromFile){
		id = (rootId==null?com.insigma.odin.framework.util.GlobalNames.udSysConfig.get("UD_AREA_ID"):rootId);
		text = (rootText==null?com.insigma.odin.framework.util.GlobalNames.udSysConfig.get("UD_AREA_NAME"):rootText);
	}
%>
<style>
.x-combo-list-inner{
	overflow:scroll;
}
</style>
<td nowrap align='right'><span style='font-size:12px'><%=(required!=null&&required.equals("true"))?"<font color=red>*</font>":""%><%=label==null?"":label%></span>&nbsp;
	<odin:hidden property="<%=property%>"/>
</td>
<td>
 <div class="x-form-item"><div class="x-form-element">
 	<div id="comboxWithTree_<%=property%>"></div>
 </div></div>	
 </td>
 <%--<odin:tree url='<%=url %>' 
 	property='<%="t_"+property%>' onclick='<%="treeclick_"+property%>' rootId='<%=id%>' rootText='<%=text%>' rootVisible="<%=rootVisible%>"></odin:tree>
 
 --%>
 
 
 
 
 
 <script>
 Ext.onReady(function() {
	var tree_<%="t_"+property%> = new Ext.tree.TreePanel({
		autoScroll: true,
		id : '<%="t_"+property%>',
		rootVisible : <%=rootVisible==null?false:rootVisible%>,
		selectNodeModel : 'exceptRoot',
		loader : new Ext.tree.TreeLoader({
			dataUrl : '<%=request.getContextPath()+url %>' 
		}),
		border : false,
		root : new Ext.tree.AsyncTreeNode({
					expanded : true,
					text : '<%=text%>',
					id : '<%=id%>'
				})
	});
	Ext.getCmp('<%="t_"+property%>').addListener('click', treeclick_<%=property%>);
});
 
 
 
 
 
 Ext.onReady(function(){
	 var comboxWithTree = new Ext.form.ComboBox({
	 	id:'comboxArea_<%=property%>',
	    store:new Ext.data.SimpleStore({fields:[],data:[[]]}),
	    editable:false,
	    shadow:true,
	    mode: 'local',
	    triggerAction:'all',
	    width:<%=width==null?160:width%>,
	    maxHeight:<%=height==null?200:height%>,
	    tpl: "<tpl for='.'><div style='height:<%=height==null?200:height%>px'><div id='tree1_<%=property%>'></div></div></tpl>",
	    selectedClass:'',
	    selectNodeModel:'exceptRoot',
	    onSelect:Ext.emptyFn
	  });   
	   
	  comboxWithTree.on('expand',function(){
		    Ext.getCmp('t_<%=property%>').render('tree1_<%=property%>');   
	  });   
	  comboxWithTree.render('comboxWithTree_<%=property%>');  
 }); 
function treeclick_<%=property%>(node){
     odin.ext.getCmp('comboxArea_<%=property%>').setValue(node.text); 
     radow.$('<%=property%>').value = node.id; 
     odin.ext.getCmp('comboxArea_<%=property%>').collapse();
     var onselect = <%=onselect==null?"''":onselect%>;
     if(onselect!==""){
     	onselect(node.id,node.text);
     }
     var selectEvent = "<%=selectEvent==null?"":selectEvent%>";
     if(selectEvent!=""){
     	radow.doEvent(selectEvent);
     }
 } 
 </script> 
