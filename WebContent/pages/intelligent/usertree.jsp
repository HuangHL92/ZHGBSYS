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
	  var rootchecked=false;
	  try{
		  var rootchecked= Boolean(getRootchecked());
	  }catch(exception){
		  
	  }	  
	  var curRow= getCurRow();
	  document.getElementById("levelid").value=levelid;
	  document.getElementById("currow").value=curRow;
      var Tree = Ext.tree;
      tree = new Tree.TreePanel( {
            el : 'tree-div',//Ŀ��div����
            autoScroll : true, // �Զ�����  
            stateful:true,
            animate : true,// ����Ч��  
            border:false,// �߿� 
            enableDD : true,// �Ƿ�֧����קЧ�� 
            checkModel: 'multiple',//cascade ������ѡ�����и������ӽ�� parentCascade ������ѡ�����и���� childCascade  ������ѡ�������ӽ��
            containerScroll : true,// �Ƿ�֧�ֹ�����
            loader : new Tree.TreeLoader( {
            	  baseAttrs: { uiProvider: Ext.tree.TreeCheckNodeUI },
                  dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.intelligent.usertree&eventNames=orgTreeJsonData&levelid='+levelid+'&curRow='+getCurRow
            })
      });
      var root = new Tree.AsyncTreeNode( {
            text : '�û��˵�',
            draggable : false,
            checked:rootchecked,
            id : jsareaid//Ĭ�ϵ�nodeֵ��?node=-100
      });
      tree.setRootNode(root);
      tree.on("check",function(node,checked){
      	 changeNode += node.id+":"+checked+","
     	//alert(node.id+" = "+checked)
      }); //ע��"check"�¼� 
      tree.render();
      root.expand();

}); 


function getRootchecked(){   
    var URLParams = new Array();    
    var aParams = document.location.search.substr(1).split('&'); 
    for (i=0; i < aParams.length;i++){    
        var aParam = aParams[i].split('=');   
        URLParams[aParam[0]] = aParam[1];    
    }   
    return URLParams["rootchecked"];   
}  

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

function getCurRow(){   
     var URLParams = new Array();    
     var aParams = document.location.search.substr(1).split('&'); 
     for (i=0; i < aParams.length;i++){    
         var aParam = aParams[i].split('=');   
         URLParams[aParam[0]] = aParam[1];    
     }   
     return URLParams["curRow"];   
}

Ext.tree.TreeCheckNodeUI = function() { 
//'multiple':��ѡ; 'single':��ѡ; 'cascade':������ѡ 
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
function preflush(users,currow){
	window.parent.setUser(users,currow);
	var win = parent.Ext.getCmp('grantWindow');
    if(win){
    	win.hide();
    }
}

function reflush(){
	window.parent.location.reload();
}
</script>



<div id="addResourceContent">
</div>
<odin:hidden property="area" value="<%=areaname%>" />
<odin:hidden property="id" />
<odin:hidden property="currow" />
<odin:hidden property="levelid"/>
<odin:toolBar property="btnToolBar">
	<odin:textForToolBar text="<h3>���ô���</h3>" />
	<odin:fill />
	<odin:buttonForToolBar id="save1" handler="doSave" isLast="true"
		text="ȷ��" icon="images/save.gif" cls="x-btn-text-icon" />
</odin:toolBar>
<odin:panel contentEl="addResourceContent" property="addResourcePanel"
	topBarId="btnToolBar" width="300"></odin:panel>
<odin:tab id="tab" width="300" height="420" tabchange="grantTabChange">
<odin:tabModel  >
	
	<odin:tabItem title="�û�����" id="tab2" ></odin:tabItem>
	<odin:tabItem title="��ɫ����" id="tab3" ></odin:tabItem>
	<odin:tabItem title="�û�������" id="tab1" isLast="true"></odin:tabItem>
</odin:tabModel > 
<odin:tabCont itemIndex="tab1"  >

					<div id="tree-div"
						style="overflow: auto; height: 400px; width: 350px; border: 2px solid #c3daf9;"></div>
					
</odin:tabCont>
 <odin:tabCont itemIndex="tab2">
 			<table>

 			<tr>
 			<td colspan="2"  align="left">
 			<odin:select property="user" label="�û�" codeType="USER" tpl="keyvalue" minChars="1"  canOutSelectList="true" isPageSelect="true" ></odin:select>
 			</td>
 			<td colspan="2" width="40" >

 			</td>
 			<td colspan="2"   width="300" align="right">
 			<odin:button text="��ѯ" property="search_person"></odin:button>
 			</td>
 			</tr>

 			</table>
			<odin:editgrid property="grid1" isFirstLoadData="false" 
				url="/" title="�û��б�" width="300" height="280" bbarId="pageToolBar" pageSize="100">
				<odin:gridJsonDataModel id="userid" root="data">
					<odin:gridDataCol name="logchecked" />
					<odin:gridDataCol name="userid" />
					<odin:gridDataCol name="loginname" />
					<odin:gridDataCol name="username" isLast="true" />
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridColumn header="selectall" gridName="resourcegrid"
						dataIndex="logchecked" editor="checkbox" edited="true" width="40" />
					<odin:gridEditColumn header="��¼����" align="center" width="80"
						dataIndex="loginname" editor="text" edited="false"/>
					<odin:gridEditColumn header="�û�����" align="center" width="150"
						dataIndex="username" editor="text" edited="false" isLast="true" />
				</odin:gridColumnModel>
			</odin:editgrid>

</odin:tabCont>

<odin:tabCont itemIndex="tab3">
 			<div id="roleQueryContent">
	<table width="100%">

		<tr>
			<td colspan="2">
	    	<odin:textEdit property="roleQName" label="��ɫ����"></odin:textEdit>
	    	</td>
 			<td colspan="2" width="40" >

 			</td>
 			<td colspan="2" align="right">
 			<odin:button text="��ѯ" property="search_role"></odin:button>
 			</td>
		</tr>
		
	</table>		
</div>
<odin:panel contentEl="roleQueryContent" property="roleQueryPanel" topBarId="btnToolBar"></odin:panel>


<odin:gridSelectColJs name="status" codeType="USEFUL"></odin:gridSelectColJs>
<odin:gridSelectColJs name="owner" codeType="USER1"></odin:gridSelectColJs>
<odin:editgrid property="grid6" bbarId="pageToolBar" url="/" title="��ɫ��Ϣ��" isFirstLoadData="false" width="300" height="280">
<odin:gridJsonDataModel  id="roleid" root="data" totalProperty="totalCount">
	<odin:gridDataCol name="checked" />
  <odin:gridDataCol name="id" />
  <odin:gridDataCol name="desc" />
  <odin:gridDataCol name="name" isLast="true"/>
</odin:gridJsonDataModel>
<odin:gridColumnModel>
  <odin:gridRowNumColumn ></odin:gridRowNumColumn>
 <odin:gridColumn  header="selectall"  gridName="grid6" dataIndex="checked" editor="checkbox" edited="true" />
  <odin:gridEditColumn header="��ɫid" hidden="true" dataIndex="id" editor="text" />
  <odin:gridEditColumn hidden="true" dataIndex="oldrolename" editor="text" />
  <odin:gridEditColumn header="��ɫ����" align="center" width="100" dataIndex="name" selectOnFocus="true" editor="text" edited="false"  />
  <odin:gridEditColumn header="��ɫ����" width="130" dataIndex="desc" editor="text" selectOnFocus="true" edited="false" isLast="true"/>
</odin:gridColumnModel>			
</odin:editgrid>	

</odin:tabCont>
</odin:tab>
