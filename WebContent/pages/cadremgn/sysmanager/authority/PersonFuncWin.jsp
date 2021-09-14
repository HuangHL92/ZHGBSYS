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
	
				<div style="width: 100%;height: 520px;margin-left: 20px;">
					<table>
						<tr>
							<td>
							 	<div style="width: 390px;height: 480px">
							 		<odin:groupBox property="g1" title="系统定义的数据界面列表">
							 		<odin:editgrid property="contentList" url="/" height="450">
										<odin:gridJsonDataModel id="id" root="data">
											<odin:gridDataCol name="fid"/>
											<odin:gridDataCol name="fname" isLast="true"/>
										</odin:gridJsonDataModel>
										<odin:gridColumnModel>
											<odin:gridRowNumColumn></odin:gridRowNumColumn>
											<odin:gridEditColumn header="权限ID" align="left" edited="false" width="55" dataIndex="fid" editor="text" hidden="true"/>
											<odin:gridEditColumn header="方案名称" align="left" edited="false" width="300" dataIndex="fname" editor="text" isLast="true"/>
										</odin:gridColumnModel>
									</odin:editgrid>
									</odin:groupBox>
							 	</div>
							</td>
							<td align="center">
								<div id='rigthBtn' style="width: 50px"></div>
								<br/>
								<div id='rigthAllBtn'></div>
								<br>
								<div id='liftBtn'></div>
								<br>
								<div id='liftAllBtn'></div>
							<td>
								<div style="width: 390px;height: 480px">
								<odin:groupBox property="g2" title="用户授权方案（缺省方案）">
							 		<odin:editgrid property="contentList2" url="/" height="450">
										<odin:gridJsonDataModel id="id" root="data">
											<odin:gridDataCol name="fid"/>
											<odin:gridDataCol name="fname" isLast="true"/>
										</odin:gridJsonDataModel>
										<odin:gridColumnModel>
											<odin:gridRowNumColumn></odin:gridRowNumColumn>
											<odin:gridEditColumn header="权限ID" align="left" edited="false" width="55" dataIndex="fid" editor="text" hidden="true"/>
											<odin:gridEditColumn header="选中方案名称" align="left" edited="false" width="300" dataIndex="fname" editor="text" isLast="true"/>
										</odin:gridColumnModel>
									</odin:editgrid>
								</odin:groupBox>
							 	</div>
							</td>
						</tr>
					</table>
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
	 		
			new Ext.Button({
				icon : 'images/icon/rightOne.png',
				id:'btn1',
			    cls :'inline pl',
			    renderTo:"rigthBtn" ,
			    handler:function(){
			    	radow.doEvent("rigthB");
			    }
			});
			new Ext.Button({
				icon : 'images/icon/leftOne.png',
				id:'btn2',
			    cls :'inline pl',
			    renderTo:"liftBtn" ,
			    handler:function(){
			    	radow.doEvent("liftB");
			    }
			});
			
			new Ext.Button({
				icon : 'images/icon/rightAll.png',
				id:'btn2',
			    cls :'inline pl',
			    renderTo:"rigthAllBtn",
			    handler:function(){
			    	radow.doEvent("rigthBAll");
			    }
			});
			new Ext.Button({
				icon : 'images/icon/leftAll.png',
				id:'btn4',
			    cls :'inline pl',
			    renderTo:"liftAllBtn",
			    handler:function(){
			    	radow.doEvent("liftBAll");
			    }
			});	
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
