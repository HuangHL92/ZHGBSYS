<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>

<%@page import="com.insigma.siis.local.pagemodel.sysmanager.group.GroupManagePageModel"%>
<%
		String areaname = (String)new GroupManagePageModel().areaInfo.get("areaname");
%>
<script type="text/javascript">
var tree;
var changeNode = "";
Ext.onReady(function() {
	  <%
		String areaid = (String)new GroupManagePageModel().areaInfo.get("areaid");
	  %>
	  var jsareaid = <%=areaid%>;
	  var levelid = getValue();
	  var isdesign= getIsdesign();
	  document.all.levelid.value=levelid;
      var Tree = Ext.tree;
      tree = new Tree.TreePanel( {
            el : 'tree-div',//目标div容器
            autoScroll : true, 
            stateful:true,
            animate : true,
            border:false,
            enableDD : true,
            checkModel: 'cascade',
            containerScroll : true,
            loader : new Tree.TreeLoader( {
            	  baseAttrs: { uiProvider: Ext.tree.TreeCheckNodeUI },
                  dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.intelligent.usertree&eventNames=orgTreeJsonData&levelid='+levelid+'&isdesign='+isdesign
            })
      });
      var root = new Tree.AsyncTreeNode( {
            text : '用户菜单',
            draggable : false,
            id : jsareaid//默认的node值：?node=-100
      });
      tree.setRootNode(root);
      tree.on("check",function(node,checked){
      	 changeNode += node.id+":"+checked+","
     	//alert(node.id+" = "+checked)
      }); //注册"check"事件 
      tree.render();
      root.expand();

}); 

function getValue(){   
     var URLParams = new Array();    
     var aParams = document.location.search.substr(1).split('&'); 
     for (i=0; i < aParams.length;i++){    
         var aParam = aParams[i].split('=');   
         URLParams[aParam[0]] = aParam[1];    
     }   
     return URLParams["levelid"];   
}  
function getIsdesign(){   
     var URLParams = new Array();    
     var aParams = document.location.search.substr(1).split('&'); 
     for (i=0; i < aParams.length;i++){    
         var aParam = aParams[i].split('=');   
         URLParams[aParam[0]] = aParam[1];    
     }   
     return URLParams["isdesign"];   
}

Ext.tree.TreeCheckNodeUI = function() { 
//'multiple':多选; 'single':单选; 'cascade':级联多选 
this.checkModel = 'multiple'; 

//only leaf can checked 
this.onlyLeafCheckable = false; 

Ext.tree.TreeCheckNodeUI.superclass.constructor.apply(this, arguments); 
}; 

Ext.extend(Ext.tree.TreeCheckNodeUI, Ext.tree.TreeNodeUI, { 

renderElements : function(n, a, targetNode, bulkRender){ 
var tree = n.getOwnerTree(); 
this.checkModel = tree.checkModel || this.checkModel; 
this.onlyLeafCheckable = tree.onlyLeafCheckable || false; 

// add some indent caching, this helps performance when rendering a large tree 
this.indentMarkup = n.parentNode ? n.parentNode.ui.getChildIndent() : ''; 

var cb = (!this.onlyLeafCheckable || a.leaf); 
var href = a.href ? a.href : Ext.isGecko ? "" : "#"; 
var buf = ['<li class="x-tree-node"><div ext:tree-node-id="',n.id,'" class="x-tree-node-el x-tree-node-leaf x-unselectable ', a.cls,'" unselectable="on">', 
'<span class="x-tree-node-indent">',this.indentMarkup,"</span>", 
'<img src="', this.emptyIcon, '" class="x-tree-ec-icon x-tree-elbow" />', 
'<img src="', a.icon || this.emptyIcon, '" class="x-tree-node-icon',(a.icon ? " x-tree-node-inline-icon" : ""),(a.iconCls ? " "+a.iconCls : ""),'" unselectable="on" />', 
cb ? ('<input class="x-tree-node-cb" type="checkbox" ' + (a.checked ? 'checked="checked" />' : '/>')) : '', 
'<a hidefocus="on" class="x-tree-node-anchor" href="',href,'" tabIndex="1" ', 
a.hrefTarget ? ' target="'+a.hrefTarget+'"' : "", '><span unselectable="on">',n.text,"</span></a></div>", 
'<ul class="x-tree-node-ct" style="display:none;"></ul>', 
"</li>"].join(''); 

var nel; 
if(bulkRender !== true && n.nextSibling && (nel = n.nextSibling.ui.getEl())){ 
this.wrap = Ext.DomHelper.insertHtml("beforeBegin", nel, buf); 
}else{ 
this.wrap = Ext.DomHelper.insertHtml("beforeEnd", targetNode, buf); 
} 

this.elNode = this.wrap.childNodes[0]; 
this.ctNode = this.wrap.childNodes[1]; 
var cs = this.elNode.childNodes; 
this.indentNode = cs[0]; 
this.ecNode = cs[1]; 
this.iconNode = cs[2]; 
var index = 3; 
if(cb){ 
this.checkbox = cs[3]; 
Ext.fly(this.checkbox).on('click', this.check.createDelegate(this,[null])); 
index++; 
} 
this.anchor = cs[index]; 
this.textNode = cs[index].firstChild; 
}, 

// private 
check : function(checked){ 
var n = this.node; 
var tree = n.getOwnerTree(); 
this.checkModel = tree.checkModel || this.checkModel; 

if( checked === null ) { 
checked = this.checkbox.checked; 
} else { 
this.checkbox.checked = checked; 
} 

n.attributes.checked = checked; 
tree.fireEvent('check', n, checked); 

if(!this.onlyLeafCheckable && this.checkModel == 'cascade'){ 
var parentNode = n.parentNode; 
if(parentNode !== null) { 
this.parentCheck(parentNode,checked); 
} 
if( !n.expanded && !n.childrenRendered ) { 
n.expand(false,false,this.childCheck); 
} 
else { 
this.childCheck(n); 
} 
}else if(this.checkModel == 'single'){ 
var checkedNodes = tree.getChecked(); 
for(var i=0;i<checkedNodes.length;i++){ 
var node = checkedNodes[i]; 
if(node.id != n.id){ 
node.getUI().checkbox.checked = false; 
node.attributes.checked = false; 
tree.fireEvent('check', node, false); 
} 
} 
} 

}, 

// private 
childCheck : function(node){ 
var a = node.attributes; 
if(!a.leaf) { 
var cs = node.childNodes; 
var csui; 
for(var i = 0; i < cs.length; i++) { 
csui = cs[i].getUI(); 
if(csui.checkbox.checked ^ a.checked) 
csui.check(a.checked); 
} 
} 
}, 

// private 
parentCheck : function(node ,checked){ 
var checkbox = node.getUI().checkbox; 
if(typeof checkbox == 'undefined')return ; 
if(!(checked ^ checkbox.checked))return; 
if(!checked && this.childHasChecked(node))return; 
checkbox.checked = checked; 
node.attributes.checked = checked; 
node.getOwnerTree().fireEvent('check', node, checked); 

var parentNode = node.parentNode; 
if( parentNode !== null){ 
this.parentCheck(parentNode,checked); 
} 
}, 

// private 
childHasChecked : function(node){ 
var childNodes = node.childNodes; 
if(childNodes || childNodes.length>0){ 
for(var i=0;i<childNodes.length;i++){ 
if(childNodes[i].getUI().checkbox.checked) 
return true; 
} 
} 
return false; 
}, 

toggleCheck : function(value){ 
var cb = this.checkbox; 
if(cb){ 
var checked = (value === undefined ? !cb.checked : value); 
this.check(checked); 
} 
} 
}); 


function doSave() {
	//alert(changeNode);
	radow.doEvent('dogrant',changeNode);
}

function grantTabChange(tabObj,item){
	if(item.getId()=='tab2'){
		odin.ext.getCmp('grid1').view.refresh(true);
	}
}
function preflush(){
	window.parent.location.reload();
}
</script>



<div id="addResourceContent">
</div>
<odin:hidden property="area" value="<%=areaname%>" />
<odin:hidden property="id" />
<odin:hidden property="fid" />
<odin:hidden property="levelid"/>
<odin:toolBar property="btnToolBar">
	<odin:textForToolBar text="<h3>配置窗口</h3>" />
	<odin:fill />
	<odin:buttonForToolBar id="save1" handler="doSave" isLast="true"
		text="保存" icon="images/save.gif" cls="x-btn-text-icon" />
</odin:toolBar>
<odin:panel contentEl="addResourceContent" property="addResourcePanel"
	topBarId="btnToolBar" width="300"></odin:panel>
<odin:tab id="tab" width="300" height="420" tabchange="grantTabChange">
<odin:tabModel  >
	<odin:tabItem title="用户组配置" id="tab1"></odin:tabItem>
	<odin:tabItem title="用户配置" id="tab2" isLast="true">
</odin:tabItem>
</odin:tabModel > 
<odin:tabCont itemIndex="tab1"  >

					<div id="tree-div"
						style="overflow: auto; height: 400px; width: 280px; border: 2px solid #c3daf9;"></div>
					
</odin:tabCont>
 <odin:tabCont itemIndex="tab2">
	<table>
		<tr>
			<td>
			<odin:editgrid property="grid1" isFirstLoadData="false" 
				url="/" title="用户列表" width="280" height="300" bbarId="pageToolBar" pageSize="20">
				<odin:gridJsonDataModel id="gridid" root="data">
					<odin:gridDataCol name="logchecked" />
					<odin:gridDataCol name="userid" />
					<odin:gridDataCol name="username" isLast="true" />
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridColumn header="selectall" gridName="resourcegrid"
						dataIndex="logchecked" editor="checkbox" edited="true" width="40" />
					<odin:gridEditColumn header="用户名称" align="center" width="180"
						dataIndex="username" editor="text" edited="false" isLast="true" />
				</odin:gridColumnModel>
			</odin:editgrid></td>
		</tr>
	</table>
</odin:tabCont>
</odin:tab>
