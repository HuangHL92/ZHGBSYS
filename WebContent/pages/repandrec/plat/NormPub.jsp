<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<% 
	String picType = (String)(new SysOrgPageModel().areaInfo.get("picType"));
	String ereaname = (String)(new SysOrgPageModel().areaInfo.get("areaname"));
	String ereaid = (String)(new SysOrgPageModel().areaInfo.get("areaid"));
	String manager = (String)(new SysOrgPageModel().areaInfo.get("manager"));
%>
<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.SysOrgPageModel"%>
<odin:toolBar property="btnToolBar">
	<odin:textForToolBar text=""/>
	<odin:fill/>
	<odin:buttonForToolBar text="下发" id="reppackagebtn"/>
	<odin:buttonForToolBar  text="关闭"  id="reset" isLast="true"/>
</odin:toolBar>
<div id="panel_content">
<odin:hidden property="ereaname" value="<%=ereaname%>" />
					<odin:hidden property="ereaid" value="<%=ereaid%>" />
					<odin:hidden property="manager" value="<%=manager%>" />
					<odin:hidden property="picType" value="<%=picType%>" />
</div>
<odin:panel contentEl="panel_content" property="mypanel" topBarId="btnToolBar" ></odin:panel>
<odin:hidden property="searchDeptid"/>
		<div>
		<table >
			<tr>
				<td align="center" colspan="6">
					<table >
						<tr>
						</tr>
						<tr>
						<odin:textEdit property="linkpsn" size="38" label="联系人"/>
						<odin:textEdit property="linktel" size="38" label="联系电话"/>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td align="center" colspan="6">
					<table >
					
						<tr align="right">
							<odin:textarea property="remark" colspan="255" rows="4" label="备 注"/>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td colspan="6">
				</td>
			</tr>
		</table>
			<div style="padding-left:24px;margin:12px 0 5px 0;font-family:Microsoft YaHei;">
			<span style="font-size:80%">请选择要下发的信息集</span>
			</div>
			<div id="interfaceScheme" style="margin-left:24px;overflow: auto; height: 200px; width: 540px; border: 1px solid #c3daf9;">
			</div>
		</div>
		
<odin:window src="/blank.htm" id="simpleExpWin" width="560" height="350" maximizable="false" title="窗口"></odin:window>
<odin:window src="/blank.htm" id="deptWin" width="255" height="350" maximizable="false" title="窗口">
</odin:window>
<script>
Ext.onReady(function() {
	  var man = document.getElementById('manager').value;
      var Tree = Ext.tree;
      var tree = new Tree.TreePanel( {
          el : 'tree-div',//目标div容器
          split:false,
          width: 164,
          minSize: 164,
          maxSize: 164,
          rootVisible: true,//是否显示最上级节点
          autoScroll : true,
          animate : true,
          border:false,
          enableDD : true,
          containerScroll : true,
          loader : new Tree.TreeLoader( {
                dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysorg.org.SysOrg&eventNames=orgTreeJsonData'
          })
      });
      var root = new Tree.AsyncTreeNode( {
            text :  document.getElementById('ereaname').value,
            iconCls : document.getElementById('picType').value,
            draggable : false,
            id : document.getElementById('ereaid').value,//默认的node值：?node=-100
            href:"javascript:radow.doEvent('querybyid','"+document.getElementById('ereaid').value+"')"
      });
      tree.setRootNode(root);
      tree.render();
      root.expand();
}); 
function reloadTree(){
	setTimeout(xx,1000);
	///window.location.href="ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile));
}
function xx(){
	var downfile = document.getElementById('downfile').value;
	w = window.open("ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile)));
	setTimeout(cc,3000);
}
function cc()
{
	w.close();
}
function grantTabChange(tabObj,item){
	if(item.getId()=='tab1'){
		odin.ext.getCmp('tabimp').view.refresh(1);
	}
	if(item.getId()=='tab2'){
		odin.ext.getCmp('tabimp').view.refresh(2);
	}
}

var changeNode = "";
	Ext.onReady(function(){
		showTree();
	}, this, {
		delay : 500
	});
	
	var tree;
	function showTree() {
			var url = 'radowAction.do?method=doEvent&pageModel=pages.sysmanager.ZWHZYQ_001_006.DeliverCue&eventNames=getTreeJsonData';
			tree = new Ext.tree.TreePanel({
			id : 'schemeTreeId',
			el : 'interfaceScheme',
		  	autoScroll : true, 
            stateful:true,
            animate : true,
            border:false,
            enableDD : false,
            rootVisible: true,
            checkModel: 'cascade',
            containerScroll : true,
			loader : new Ext.tree.TreeLoader({
				baseAttrs: { uiProvider: Ext.tree.TreeCheckNodeUI },
				dataUrl : url
			})
		});
		var root = new Ext.tree.AsyncTreeNode({
			id : 'S000000', 
			text : '人员信息', 
			draggable : false,
			uiProvider: Ext.tree.TreeCheckNodeUI,
			expanded : true
		});
		tree.setRootNode(root);
		tree.on("check",function(node,checked){
    		changeNode += node.id+":"+checked+",";
   	    });  
		root.expand(true);
		tree.render();
	}
	function rootHide() {
		alert('root');
		tree.rootVisible = false;
	}
	function doSave() {
		var root = tree.getRootNode(); 
		radow.doEvent('save',changeNode);
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
		//add some indent caching, this helps performance when rendering a large tree 
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
	//private 
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
	//private 
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
	//private 
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
	//private 
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