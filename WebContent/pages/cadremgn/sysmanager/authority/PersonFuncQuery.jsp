<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@include file="/comOpenWinInit2.jsp" %>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script src="basejs/jquery-ui/jquery-1.10.2.js"></script>
<script src="basejs/jquery-ui/ui/jquery.ui.core.js"></script>
<script src="basejs/jquery-ui/ui/jquery.ui.widget.js"></script>
<script src="basejs/jquery-ui/ui/jquery.ui.progressbar.js"></script>
<style>
 #jltab__tab2 {
 	margin-left: 165px;
 }
</style>
<script type="text/javascript" src="basejs/helperUtil.js"></script>	
<div id="tooldiv"></div>
	
		    	<div style="width: 410px;height: 480px;margin: 5px;margin-left: 24px;display: inline;">
		    		<odin:groupBox title="查询输出权限设置" property="group1">
		    			<div id="tree-div4" style="height: 427px"></div>
		    		
		    		</odin:groupBox>
		    	</div>
		    	<div style="width: 410px;height: 480px;margin: 5px;display: inline;">
		    		<odin:groupBox title="统计输出权限设置" property="group2">
		    			<div id="tree-div5" style="height: 427px"></div>
		    		
		    		</odin:groupBox>
		    	</div>
	
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
<odin:hidden property="functionJson"/>

<script type="text/javascript">
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
	
	var result4 = '';
	var roonodes4 = Ext.getCmp('tree4').getRootNode().childNodes;
	if(roonodes4.length>0){
		for(var i=0;i<roonodes4.length;i++){
			var r = roonodes4[i];
			for(var j=0;j<r.childNodes.length;j++){
				var rootnode = r.childNodes[j];
				if(rootnode.attributes.checked){
					result4 = result4 + rootnode.attributes.id + ",";
				}
			}
		}
		result4 = result4.substring(0,result4.length-1);
	}
	document.getElementById('result4').value = result4;
	
	var result5 = '';
	var roonodes5 = Ext.getCmp('tree5').getRootNode().childNodes;
	if(roonodes5.length>0){
		for(var i=0;i<roonodes5.length;i++){
			var r = roonodes5[i];
			for(var j=0;j<r.childNodes.length;j++){
				var rootnode = r.childNodes[j];
				if(rootnode.attributes.checked){
					result5 = result5 + rootnode.attributes.id + ",";
				}
			}
		}
		result5 = result5.substring(0,result5.length-1);
	}
	document.getElementById('result5').value = result5;
	
	
	radow.doEvent('dogrant');
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

	 Ext.onReady(function() {
		 var Tree = Ext.tree;
		 
		 var tree4 = new Ext.tree.TreePanel({    
			 id: 'tree4',
			 region: 'center',  
	         el: 'tree-div4',
	         //True表示为面板是可收缩的，并自动渲染一个展开/收缩的轮换按钮在头部工具条    
	         collapsible: true,    
	         /* title: '标题',//标题文本  */   
	         width: 390,
	         height: 420,   
	         border : false,//表框    
	         autoScroll: true,//自动滚动条    
	         animate : true,//动画效果    
	         rootVisible: false,//根节点是否可见    
	         split: true,    
	         loader : new Tree.TreeLoader( {
	        	 dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.cadremgn.sysmanager.authority.PersonFunc&eventNames=qryview',
	             baseParams : {userid: document.getElementById('subWinIdBussessId').value}
	        }),
	        listeners: {        
	        	scope:this,
				checkchange:function(node,checked){
					if (node.ui.checkbox.checked == true) {
						node.attributes.checked = true;
						if (node.childNodes.length > 0) {
							for (var i = 0; i < node.childNodes.length; i++) {
								  node.childNodes[i].ui.checkbox.checked=true;
								  node.childNodes[i].attributes.checked = true;
								  //loop2(node.childNodes[i]);
							}
						}
					} else {
						node.ui.checkbox.checked = false;
						node.attributes.checked = false;
						for (var i = 0; i < node.childNodes.length; i++) {
						  node.childNodes[i].ui.checkbox.checked=false;
						  node.childNodes[i].attributes.checked = false;
						  //loop2(node.childNodes[i]);
						}
					}
				} 
	        }
	     });    
		 var root4 = new Tree.AsyncTreeNode( {
		      text :  '',
		      draggable : false,
		      id : '-1'
		  });
		  tree4.setRootNode(root4);
		  tree4.render();
		  tree4.expandAll();
		 
		 var tree5 = new Ext.tree.TreePanel({    
			 id: 'tree5',
			 region: 'center',  
	         el: 'tree-div5',
	         //True表示为面板是可收缩的，并自动渲染一个展开/收缩的轮换按钮在头部工具条    
	         collapsible: true,    
	         /* title: '标题',//标题文本  */   
	         width: 390,
	         height: 420,      
	         border : false,//表框    
	         autoScroll: true,//自动滚动条    
	         animate : true,//动画效果    
	         rootVisible: false,//根节点是否可见    
	         split: true,    
	         loader : new Tree.TreeLoader( {
	        	 dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.cadremgn.sysmanager.authority.PersonFunc&eventNames=statistics',
	             baseParams : {userid: document.getElementById('subWinIdBussessId').value}
	         }),
	         listeners: {        
	        	scope:this,
				checkchange:function(node,checked){
					if (node.ui.checkbox.checked == true) {
						node.attributes.checked = true;
						if (node.childNodes.length > 0) {
							for (var i = 0; i < node.childNodes.length; i++) {
								  node.childNodes[i].ui.checkbox.checked=true;
								  node.childNodes[i].attributes.checked = true;
								  //loop2(node.childNodes[i]);
							}
						}
					} else {
						node.ui.checkbox.checked = false;
						node.attributes.checked = false;
						for (var i = 0; i < node.childNodes.length; i++) {
						  node.childNodes[i].ui.checkbox.checked=false;
						  node.childNodes[i].attributes.checked = false;
						  //loop2(node.childNodes[i]);
						}
					}
				} 
	        }
	     });    
		 var root5 = new Tree.AsyncTreeNode( {
		      text :  '',
		      draggable : false,
		      id : '-1'
		  });
		 tree5.setRootNode(root5);
		 tree5.render();
		 tree5.expandAll();
		 
	});

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
