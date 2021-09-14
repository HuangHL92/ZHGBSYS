<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<div id="tooldiv"></div>
<%@include file="/comOpenWinInit.jsp" %>
<%@ page import="com.insigma.siis.local.business.helperUtil.SysManagerUtils"%>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script src="basejs/jquery-ui/jquery-1.10.2.js"></script>
<script src="basejs/jquery-ui/ui/jquery.ui.core.js"></script>
<script src="basejs/jquery-ui/ui/jquery.ui.widget.js"></script>
<script src="basejs/jquery-ui/ui/jquery.ui.progressbar.js"></script>
<link href="<%=request.getContextPath()%>/rmb/css/main.css" rel="stylesheet">
<%

String loginUserid = SysManagerUtils.getUserId();
String hiddenIsInclude = "false";
if(!"40288103556cc97701556d629135000f".equals(loginUserid)){%>
<script>
Ext.onReady(function() {
	document.getElementById("includeSub").parentNode.parentNode.parentNode.parentNode.style.display="none"
});
</script>
<%}
%>
<style>
 #jltab__tab2 {
 	margin-left: 165px;
 }
</style>

<script type="text/javascript" src="basejs/helperUtil.js"></script>	
<table width="1200px" height="300" align="center">
	<tr>
		<td style="width: 590px">
			<odin:groupBox title="角色信息">	
			<table style="width: 100%;">
				<tr>
					<td>
						<span style="color:#F00;font-size:12px">提示：拖动表格行可排序</span>
					</td>
					<td align="right">
						<odin:button property="addRole" text="新增角色" handler="addRole"></odin:button>
					</td>
				</tr>
			</table>
			<div>
				<odin:editgrid2 property="grid6" height="450" hasRightMenu="false" pageSize="20" url="/" autoFill="true" forceNoScroll="true">
			<odin:gridJsonDataModel  id="roleid" root="data" totalProperty="totalCount">
		 		<odin:gridDataCol name="roleid" />
		 		<odin:gridDataCol name="rolecheck"  />
		 		<odin:gridDataCol name="roledesc" />
		  		<odin:gridDataCol name="rolecode" />
		  		<odin:gridDataCol name="rolecol" />
		  		<odin:gridDataCol name="owner" />
		  		<odin:gridDataCol name="rolename" isLast="true"/>
			</odin:gridJsonDataModel>
		<odin:gridColumnModel>
		 <odin:gridRowNumColumn2 ></odin:gridRowNumColumn2>
		  	<odin:gridEditColumn2 header="角色id" hidden="true" dataIndex="roleid" editor="text" edited="false"/>
		  	<odin:gridEditColumn2 locked="true" header="selectall" width="40"
							editor="checkbox" dataIndex="rolecheck" edited="true" />
		  	<odin:gridEditColumn2 header="角色名称" align="center" width="150" dataIndex="rolename" selectOnFocus="true" editor="text" edited="true"  />
		  	<odin:gridEditColumn2 header="角色描述" width="200" dataIndex="roledesc" editor="text" selectOnFocus="true" edited="true" align="center"/>
		  	<odin:gridEditColumn2 header="角色编码"  width="80" dataIndex="rolecode" edited="false" editor="text" align="center" hidden="true" />
		  	<odin:gridEditColumn2 header="信息项授权" width="100" dataIndex="rolecol" editor="text" edited="false" align="center" renderer="openCol"/>
		  	<odin:gridEditColumn2 header="角色作者"  width="80" dataIndex="owner" edited="false" editor="text" align="center" hidden="true" />
		  	 <odin:gridEditColumn2 sortable="false" align="center" width="60" header="删除" dataIndex="op2"  editor="text" edited="false" renderer="commGridColDelete" isLast="true" />
		</odin:gridColumnModel>			
		</odin:editgrid2>	
			</div>
		</odin:groupBox>
		</td>
		<td style="width: 590px">
			<odin:groupBox title="菜单功能">		
				<div style="width: 100%;text-align: right;margin-right: 10px;">
					<odin:button property="" text="保存" handler="save"></odin:button>
				</div>
				<div id="tree-div6"/>
			</odin:groupBox>
		</td>
	</tr>
	
</table>
<odin:toolBar property="btnToolBar" applyTo="tooldiv">
	<odin:commformtextForToolBar text="" property="text11"></odin:commformtextForToolBar>
	<odin:fill/>
	<odin:buttonForToolBar id="Save" handler="dogrant" text="保存" isLast="true"/>
</odin:toolBar>

<odin:hidden property="fid"/>
<odin:hidden property="fname"/>
<odin:hidden property="fid2"/>
<odin:hidden property="fname2"/>
<odin:hidden property="userid"/>
<odin:hidden property="result1"/>
<odin:hidden property="result2"/>
<odin:hidden property="result3"/>
<odin:hidden property="result7"/>
<odin:hidden property="result4"/>
<odin:hidden property="result5"/>
<odin:hidden property="roleId"/>
<odin:hidden property="functionJson"/>
<odin:hidden property="username"/>
<script type="text/javascript">

Ext.onReady(function() {
	$h.initGridSort('grid6',function(g){
		radow.doEvent('rolesort');
	});
});
window.onbeforeunload=function(){
	radow.doEvent('removeSession');
}
function commGridColDelete(value, params, record, rowIndex, colIndex, ds){
		return "<img src='"+contextPath+"/images/qinkong.gif' title='删除！' style='cursor: pointer;' onclick=\"radow.doEvent('roleDelete','"+record.get('roleid')+"');\">";
}

function openCol(value, params, record, rowIndex, colIndex, ds){
	/* return "<a href=\"javascript:alert('" + record.get('roleid') + "')\">信息项授权</a>"; */
	if("<%=loginUserid%>"==record.get("owner")){
		return "<a style='cursor: pointer;' href=\"javascript:tableCol('"+record.get('roleid')+"')\">信息项授权<a/>";
	}else{
		return "&nbsp;";
	}
}

function tableCol(roleid){
	$h.openWin('tableCol','pages.cadremgn.sysmanager.authority.TableCol','信息项权限设置',700,590,roleid,'<%=request.getContextPath()%>',null,{maximizable:false,resizable:false});
}

function selectGrid(){
	radow.doEvent('grid6.dogridquery');
}
function addRole(){
	$h.openPageModeWin('roleWindow','pages.cadremgn.sysmanager.authority.NewRole','新建角色',500,100,'','<%=request.getContextPath()%>');
}
//新增子系统
function addSub(){
	window.close();
	parent.$h.openPageModeWin('newRange','pages.cadremgn.sysmanager.authority.NewRange','子系统范围管理',1200,800, '','<%=request.getContextPath()%>',null,{maximizable:false,resizable:false},true);
}
//修改子系统
function changeSub(subid){
	window.close();
	parent.$h.openPageModeWin('newRange','pages.cadremgn.sysmanager.authority.NewRange','子系统范围管理',1200,800, subid,'<%=request.getContextPath()%>',null,{maximizable:false,resizable:false},true);
}

function dogrant(){
	radow.doEvent('saveUser');
}
function save(){
	if(changeNode==''){
		Treeload();
		return;
	}
	var grid6 = odin.ext.getCmp('grid6');
	var sm = grid6.getSelectionModel();
	var roleSelections = sm.getSelections();//获取当前行
	if(roleSelections[0]==null||roleSelections[0]==undefined){
		alert("请先选择角色！");
		return;
	}
	var roleid = roleSelections[0].data.roleid;
	var value = changeNode+"@"+roleid+'@'+changeNodeText;
	radow.doEvent('save',value);
}

var tree;
var changeNode = "";
var changeNodeText = "";
Ext.onReady(function() {
    var Tree = Ext.tree;
    tree = new Tree.TreePanel( {
		  id : 'roleTree',
          el : 'tree-div6',//目标div容器
          autoScroll : true,
          rootVisible: false,
          stateful:true,
          animate : true,
          height:455,
          border:false,
          enableDD : true,
          checkModel: 'cascade',
          containerScroll : true,
          listeners: {
        	  load: function(n) {
        		 /*  if(n.hasChildNodes()) {
        			  var cn = n.childNodes;
        			  for(var ni=0; ni<cn.length;ni++){
        				 //权限分配是 不能有 系统管理 菜单选项
        				  if(cn[ni].id=='S010000'){
        					  cn[ni].remove();
        				  }
        			  }
        			  
        		  } */
              }
          },

          loader : new Tree.TreeLoader( {
          	  baseAttrs: { uiProvider: Ext.tree.TreeCheckNodeUI },
                dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.cadremgn.sysmanager.authority.PersonFunc&eventNames=orgTreeJsonData'
          })
    });
    var root = new Tree.AsyncTreeNode( {
          text : '业务菜单树',
          draggable : false,
          id : 'S000000'//默认的node值：?node=-100
    });
    tree.setRootNode(root);
    tree.on("check",function(node,checked){
    	 changeNode += node.id+":"+checked+","
    	 changeNodeText += node.text+":"+checked+","
   	//alert(node.id+" = "+checked)
    }); //注册"check"事件 
    
    tree.render();
    root.expand(false,true, callback);//默认展开
}); 
var callback = function (node){//仅展看下级
	if(node.hasChildNodes()) {
		node.eachChild(function(child){
			child.expand();
		})
	}
}

function Treeload(){
	tree.root.reload();
	tree.root.expand(false,true, callback);//默认展开
	changeNode="";
	changeNodeText="";
}
 	
	 
	 function ShowCellCover(elementId, titles, msgs){	
	 	Ext.MessageBox.buttonText.ok = "关闭";
	 	if(elementId.indexOf("start") != -1){
	 	
	 		Ext.MessageBox.show({
	 			title:titles,
	 			msg:msgs,
	 			width:300,
	 	        height:300,
	 			closable:false,
	 		//	buttons: Ext.MessageBox.OK,		
	 			modal:true,
	 			progress:true,
	 			wait:true,
	 			animEl: 'elId',
	 			increment:5, 
	 			waitConfig: {interval:150}
	 			//,icon:Ext.MessageBox.INFO        
	 		});
	 	}else if(elementId.indexOf("success") != -1){
	 			Ext.MessageBox.show({
	 				title:titles,
	 				msg:msgs,
	 				width:300,
	 		        height:300,
	 		        modal:true,
	 				closable:true,
	 				//icon:Ext.MessageBox.INFO,  
	 				buttons: Ext.MessageBox.OK
	 			});
	 			/*
	 			setTimeout(function(){
	 					Ext.MessageBox.hide();
	 			, 2000);
	 			*/
	 					
	 	}else if(elementId.indexOf("failure") != -1){
	 			Ext.MessageBox.show({
	 				title:titles,
	 				msg:msgs,
	 				width:300,
	 				modal:true,
	 		        height:300,
	 				closable:true,
	 				//icon:Ext.MessageBox.INFO,
	 				buttons: Ext.MessageBox.OK		
	 			});
	 			/*
	 			setTimeout(function(){
	 					Ext.MessageBox.hide();
	 			}, 2000);
	 			*/
	 	}else {
	 			Ext.MessageBox.show({
	 				title:titles,
	 				msg:msgs,
	 				width:300,
	 				modal:true,
	 		        height:300,
	 				closable:true,
	 				//icon:Ext.MessageBox.INFO,
	 				buttons: Ext.MessageBox.OK		
	 			});
	 		}

	 	
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
</script>
