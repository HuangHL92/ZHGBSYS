<%@ tag pageEncoding="GBK" body-content="empty" small-icon=""
 display-name="所属行政区" description="单位信息查询（简单）" %>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ attribute name="label" required="false" %>
<%@ attribute name="required" required="false" %>
<%@ attribute name="property" required="true" %>
<%@ attribute name="rootId" required="false" %>
<%@ attribute name="rootText" required="false" %>
<%@ attribute name="level" required="false" %>
<%@ attribute name="height" required="false" %>
<%@ attribute name="width" required="false" %>
<%@ attribute name="onselect" required="false" description="当单击一个行政区进行选择时触发"%>
<%@ attribute name="selectEvent" required="false" description="选中时触发该事件"%>
<%@ attribute name="way" required="false" description="0表示从配置库AA01里取行政区划编码信息，否则是从用户所属用户组里取，默认为0"%>
<%@ page import="com.insigma.siis.local.pagemodel.comm.CommonQueryBS" %>
<%
 	String id = null;
	String text = null;
	String rootVisible = "true";
	boolean isFromFile = false;
	if(way==null || way.equals("0")){
		isFromFile = true;
	}else{ //取用户所属用户组的第一个用户组ID
		java.util.List<com.insigma.odin.framework.privilege.vo.GroupVO> list = com.insigma.odin.framework.util.SysUtil.getCurrentUser(request).getUserGroups();
		if(list!=null && list.size()>0){
			for(int i=0;i<list.size();i++){
				com.insigma.odin.framework.privilege.vo.GroupVO g = list.get(i);
				if(id==null){
					id = g.getId();
				}else{
					id += ","+g.getId();
				}
				id += ":"+g.getName();
			}
			CommonQueryBS.systemOut(id);
			rootVisible = "false";
		}else{
			isFromFile = true;
		}
	} 
	if(isFromFile){
		id = (rootId==null?com.insigma.odin.framework.util.GlobalNames.udSysConfig.get("UD_AREA_ID"):rootId);
		text = (rootText==null?com.insigma.odin.framework.util.GlobalNames.udSysConfig.get("UD_AREA_NAME"):rootText);
	}
%>
<td nowrap align='right'><span style='font-size:12px'><%=(required!=null&&required.equals("true"))?"<font color=red>*</font>":""%><%=label==null?"所属行政区":label%></span>&nbsp;
	<odin:hidden property="<%=property%>"/>
</td>
<td>
 <div class="x-form-item"><div class="x-form-element">
 	<div id="comboxWithTree_<%=property%>"></div>
 </div></div>	
 </td>
 <odin:tree url='<%="/comm/areaQueryAction.do?method=query"+(level==null?"":("&level="+level))%>' 
 	property='<%="t_"+property%>' onclick='<%="treeclick_"+property%>' rootId='<%=id%>' rootText='<%=text%>' rootVisible="<%=rootVisible%>"></odin:tree>
 
 <script>
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
