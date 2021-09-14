<%@ tag pageEncoding="GBK" body-content="empty" small-icon=""
 display-name="任免表文本输入框" description="任免表文本输入框" %>
 <%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ attribute name="label" required="false" %>
<%@ attribute name="required" required="false" %>
<%@ attribute name="property" required="true" %>
<%@ attribute name="cls" required="false" %>
<%@ attribute name="width" required="false" %>
<%@ attribute name="height" required="false" %>
<%@ attribute name="codetypeJS" required="false" %>
<%
String ctxpathemp = request.getContextPath();
%>

<style>
.x-combo-list-inner{
	
	
}
</style>

<input name="<%=property %>" id="<%=property %>" type="hidden"/>
 <div class="x-form-item"><div class="x-form-element">
 	<div id="comboxWithTree_<%=property%>">
 	</div>
 </div></div>	

 <script>
 Ext.onReady(function() {
 var codetype_<%=property%> = <%=codetypeJS%>;
 codetype_<%=property%>.text='<%=label%>';
	var tree_<%="t_"+property%> = new Ext.tree.TreePanel({
		autoScroll: true,
		id : '<%="t_"+property%>',
		rootVisible : <%=true%>,
		selectNodeModel : 'exceptRoot',
		/*loader : new Ext.tree.TreeLoader({
			dataUrl : '' 
		}),*/
		border : false,
		root :codetype_<%=property%>
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
	    cls:"<%=cls%>",
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
		    //下拉框宽度 
		    var tree1_div = Ext.query("#tree1_<%=property%>");//overflow:scroll !important;width:250px !important;
			var obj = tree1_div[0].parentNode.parentNode;
			obj.style.width='250px';
			obj.style.overflow='scroll';
			obj.parentNode.style.width='250px';
	  });   
	  comboxWithTree.render('comboxWithTree_<%=property%>');  
	  
	  
	 
	var img = Ext.query("#comboxArea_<%=property %>+img")[0];
	var obj = Ext.query("#comboxArea_<%=property %>")[0]
	img.style.display="none";
	var imgHidden = function(){
		Ext.query("#comboxArea_<%=property %>+img")[0].style.display="none";
	};
	var imgShow = function(){
		Ext.query("#comboxArea_<%=property %>+img")[0].style.display="block";
	}
	img.onmouseout=imgHidden;
	img.onmouseover=imgShow;
	obj.onmouseout=imgHidden;
	obj.onmouseover=imgShow;
 }); 
function treeclick_<%=property%>(node){
     odin.ext.getCmp('comboxArea_<%=property%>').setValue(node.text); 
     radow.$('<%=property%>').value = node.id; 
     odin.ext.getCmp('comboxArea_<%=property%>').collapse();
     var onselect = '';
     if(onselect!==""){
     	onselect(node.id,node.text);
     }
     var selectEvent = "";
     if(selectEvent!=""){
     	radow.doEvent(selectEvent);
     }
 } 
 </script> 
	
