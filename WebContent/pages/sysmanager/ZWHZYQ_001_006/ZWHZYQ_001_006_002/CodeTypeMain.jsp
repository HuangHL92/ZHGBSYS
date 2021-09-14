<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<style>

form {
	width: 100%;
	height: 100%;
}
#viewAr{
	width: 100%!important;
	height: 100%!important;
}
</style>
<odin:hidden property="printType"/>
<odin:hidden property="nodeId"/>
<script type="text/javascript">
	Ext.onReady(function(){
		var nodemenu; 
		var tree = new Ext.tree.TreePanel({
			title : '请选择要维护的代码集', 
			id : 'addTree', 
			el : 'treeDiv',				//将树形添加到一个指定的div中,非常重要！
			region : 'west', 
			collapseMode : 'mini',		// 当设置为“mini”时候，分割线上会出现一个收起按钮，点击后，会将面板缩起来。
			width : 200, 
			split : true, 
			height : Ext.getBody().getHeight(),
			frame : false,				//美化界面
			enableDD : false,			//是否支持拖拽效果
			containerScroll : true,		//是否支持滚动条
			autoScroll : true, 
			rootVisible : false,		//是否隐藏根节点,很多情况下，我们选择隐藏根节点增加美观性
			border : true,				//边框
			animate : true,				//动画效果
			loader : new Ext.tree.TreeLoader({
				dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_002.CodeTypeMain&eventNames=getTreeJsonData'
			}),	
			listeners : {
				'click': function(node){                                       //左键单击事件
					node.select();
					document.getElementById('dataInterfaceFrame').contentWindow.clearNodeId();
					document.getElementById('nodeId').value = node.id;
					var tabs = Ext.getCmp('schemeTabPanelId');
					if(node.id != 'S000000'){
						if(tabs.getActiveTab().id == 'tab2'){
							var dataframe = document.getElementById('dataInterfaceFrame');
							dataframe.contentWindow.showTree(node.id);
						}else{
							tabs.setActiveTab("tab2");
						}
					}
				}
			}
		});

		var root = new Ext.tree.AsyncTreeNode({
			id : 'S000000', 
			text : '扩充标准代码', 
			draggable : false, //根节点不容许拖动
			expanded : true
		});
		tree.setRootNode(root);
		root.expand(true);
	
		var src = 'radowAction.do?method=doEvent&pageModel=pages.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_002.CodeValue';
		
		var tabPanel = new Ext.TabPanel({
			id : 'schemeTabPanelId', 
			region : 'center', 
			height : Ext.getBody().getHeight(), 
			frame : false, 
			minTabWidth : 30, 
			tabWidth : 135, 
			activeTab : 0, //默认激活第一个Tab页
			animScroll : true, 
			enableTabScroll : true, 
			plugins : new Ext.ux.TabCloseMenu(), 
			border : false, 
			items : [
			         {title : '码表内容', id : 'tab2', html : '<iframe id="dataInterfaceFrame" src="' + src + '"  scrolling="auto" frameborder="0" width="100%" height="100%" allowtransparency="yes"></iframe>'}
			],
			tbar:[
			      '->', 
					{
						text : '收拢', 
						icon : 'images/icon/folderClosed.gif', 
						handler : function(){
							var childWindow = document.getElementById('dataInterfaceFrame').contentWindow;
							childWindow.partExpand();
						}
					}, '-', 
					{
						text : '展开', 
						icon : 'images/icon/folderOpen.gif', 
						handler : function(){
							var childWindow = document.getElementById('dataInterfaceFrame').contentWindow;
							childWindow.extend();
						}
					}, '-', 
					{
						text : '排序', 
						icon : 'images/icon/folderClosed.gif', 
						handler : function(){
							var childWindow = document.getElementById('dataInterfaceFrame').contentWindow;
							var nodeId = document.getElementById('nodeId').value;
							childWindow.sorter(nodeId);
						}
					}, '-', 
			        {
			        	text : '新增', 
			        	icon : 'images/add.gif', 
			        	handler : function(){
			        		var childWindow = document.getElementById('dataInterfaceFrame').contentWindow;
			        		var nodeId = document.getElementById('nodeId').value;
			        		childWindow.append(nodeId);
			        	}
			        }, '-', 
			        {
			        	text : '修改', 
			        	icon : 'images/i_2.gif', 
			        	handler : function(){
			        		var childWindow = document.getElementById('dataInterfaceFrame').contentWindow;
			        		var nodeId = document.getElementById('nodeId').value;
			        		childWindow.modify(nodeId);
			        	}
			        }, '-', 
			        {
			        	text : '删除', 
			        	icon : 'images/icon/delete.gif', 
			        	handler : function(){
			        		var childWindow = document.getElementById('dataInterfaceFrame').contentWindow;
			        		var nodeId = document.getElementById('nodeId').value;
			        		childWindow.deleteBtn(nodeId);
			        	}
			        }, '-', 
			        {
			        	text : '显示', 
			        	icon : 'images/icon/folderClosed.gif', 
			        	handler : function(){
			        		var childWindow = document.getElementById('dataInterfaceFrame').contentWindow;
			        		var nodeId = document.getElementById('nodeId').value;
			        		childWindow.show(nodeId);
			        	}
			        }, '-', 
			        {
			        	text : '隐藏', 
			        	icon : 'images/icon/refresh.png', 
			        	handler : function(){
			        		var childWindow = document.getElementById('dataInterfaceFrame').contentWindow;
			        		var nodeId = document.getElementById('nodeId').value;
			        		childWindow.shade(nodeId);
			        	}
			        }, '-', 
			        {
			        	text : '下发', 
			        	icon : 'images/icon/imp.gif', 
			        	handler : function(){
			        		var childWindow = document.getElementById('dataInterfaceFrame').contentWindow;
			        		var nodeId = document.getElementById('nodeId').value;
			        		childWindow.download(nodeId);
			        	}
			        }, '-', 
			        {
			        	text : '接收', 
			        	icon : 'images/icon/exp.png', 
			        	handler : function(){
			        		var childWindow = document.getElementById('dataInterfaceFrame').contentWindow;
			        		var nodeId = document.getElementById('nodeId').value;
			        		childWindow.impTest();
			        	}
			        }
			      ],
			listeners : {   
				beforeremove : removeTabEvent 
			}
		});
		
		var paraPanel = new Ext.Panel({
			id : 'paraPanel', 
			el : 'opareaDiv', 
			region : 'center', 
			layout : 'border', 
			autoScroll : false, 
			border : true, 
			width : Ext.getBody().getWidth() - 200, 
		    height : Ext.getBody().getHeight(), 
			items : [tabPanel]
		});
		var viewport = new Ext.Viewport({
	        layout : 'border', 
	        applyTo :"viewAr",
	        items : [
	            tree, 
	            paraPanel
	        ]
	    });
	});
	
	/*tab页右键关闭  方法*/
	Ext.ux.TabCloseMenu = function(){
    	var tabs, menu, ctxItem;
	    this.init = function(tp){
	        tabs = tp;
	        tabs.on('contextmenu', onContextMenu);
	    }
	    function onContextMenu(ts, item, e){
	    	if(!menu){
	    		menu = new Ext.menu.Menu([{
	    			id : tabs.id + '-close', 
	                text : '关闭本页', 
	                handler : function(){
	                    tabs.remove(ctxItem);
	                }
	            }, {
	                id : tabs.id + '-close-others', 
	                text : '关闭其他页', 
	                handler : function(){
	                    tabs.items.each(function(item){
	                        if(item.closable && item != ctxItem){
	                            tabs.remove(item);
	                        }
	                    });
	                }
	            }]);
	        }
	        ctxItem = item;
	        var items = menu.items;
	        items.get(tabs.id + '-close').setDisabled(!item.closable);
	        var disableOthers = true;
	        tabs.items.each(function(){
	            if(this != item && this.closable){
	                disableOthers = false;
	                return false;
	            }
	        });
	        items.get(tabs.id + '-close-others').setDisabled(disableOthers);
	        menu.showAt(e.getPoint());
	    }
	};

	var haveChanges = new Array();
	
	/*tab页关闭  方法*/
	function removeTabEvent(tabpanel, tab){
		var tabTitle = tab.title;
		if(haveChanges.in_array(tab.id)){
			Ext.MessageBox.show({
            	title : '系统提示', 
           		msg : '您好，当前窗口信息已经发生更改但尚未保存，您确定要取消更改并关闭该编辑页面吗？', 
          		multiline : false, 
            	width : 280, 
            	buttons : Ext.Msg.YESNO, 
            	animEl : 'messageBox', 
            	fn : function(btn, text){
            		if(btn == 'yes'){
            			//移除beforeremove事件，为了防止tabpanel.remove(tab)时进入死循环
            			tabpanel.un('beforeremove', removeTabEvent);
            			if(tab.title == '方案:新建数据访问接口方案...' || tab.title == '脚本:新建数据访问接口方案脚本...'){
            				delTreeNode(tab.id);
            			}
            			deletehaveChange(tab.id);
            			//移除tab
            			tabpanel.remove(tab);
            			//增加beforeremove事件
            			tabpanel.addListener('beforeremove', removeTabEvent, tabpanel);
            		}
            	}
			});
		}else{
			tabpanel.un('beforeremove', removeTabEvent);
			if(tab.title == '方案:新建数据访问接口方案...' || tab.title == '脚本:新建数据访问接口方案脚本...'){
				delTreeNode(tab.id);
			}
			//移除tab	 
			tabpanel.remove(tab);
			tabpanel.addListener('beforeremove', removeTabEvent, tabpanel);
		}
		//这一句很关键
		return false;
    }
	
	function getNode(){
		var tree = Ext.getCmp("addTree");
		var node = tree.getSelectionModel().getSelectedNode(); 
		return node;
	}

	/*树收拢 方法*/
	function partExpand(){
		var tree = Ext.getCmp("addTree");
		tree.collapseAll();
	}

	function updateTab(text, isScript){
		var tabs = Ext.getCmp('schemeTabPanelId');
		var tab = tabs.getActiveTab();
		if(isScript){
			tab.setTitle("脚本:" + text);
		}else{
			tab.setTitle("方案:" + text);
		}
	}

	function delTreeNode(id){
		var tree = Ext.getCmp('addTree');
		var node = tree.getNodeById(id);
		if(node != null){
			node.remove();
		}
	}

	function removeTab(tabId){
		var tabPanel = Ext.getCmp('schemeTabPanelId');
		tabPanel.un('beforeremove', removeTabEvent);
		var tab = tabPanel.getItem(tabId);
		tabPanel.remove(tab); 
		tabPanel.addListener('beforeremove', removeTabEvent, tabPanel);
	}

	//tab插件标记数组更新
	function addhaveChange(tabId){
		if(!haveChanges.in_array(tabId))
			haveChanges.push(tabId);
	}
	
	function deletehaveChange(tabId){
		haveChanges.remove(tabId);
	}

	function reloadTree(){
		var tree = Ext.getCmp("addTree");
		tree.root.reload();
		tree.expandAll();
	}
	
	function newFlag(){
		var tabs = Ext.getCmp('schemeTabPanelId');
		var flag = false;
		tabs.items.each(function(item){   
			var title = item.title;  
			if(title == '方案:新建数据访问接口方案...' || title == '新建信息结构类型...'){ 
				flag = true;     
			}
		}); 
		return flag;
	}

	function refreshTree(){
		var tree = Ext.getCmp("addTree");
		var node = tree.getSelectionModel().getSelectedNode();
		if(node == null){
			tree.getRootNode().reload()
		}else{
			var path = node.getPath('id');
			tree.getLoader().load(tree.getRootNode(),
					function(treeNode){tree.expandPath(path, 'id', 
						function(bSucess, oLastNode){
					tree.getSelectionModel().select(oLastNode);
					tree.root.expand(true);
				})
			}, this)
		}
	}
	
	Array.prototype.in_array = function(e){
		for(i = 0; i < this.length; i++){
			if(this[i] == e)
				return true;
		}
		return false;
	};

	Array.prototype.indexOf = function(val){
		for(var i = 0; i < this.length; i++){
			if(this[i] == val)
				return i;
		}
		return -1;
	};
	
	Array.prototype.remove = function(val){
		var index = this.indexOf(val);
		if(index > -1){
			this.splice(index, 1);
		}
	};
	
	/*关闭添加窗口页面*/
	function cancel() {
        refreshTree();
	}
	
	var sortNode;
	function checkNode(codetype){
		var tree = Ext.getCmp("addTree");
		var root =tree.getRootNode();
		var codetype = "leaf_"+codetype;
		if(root.childNodes.length>0){
			loop(root,codetype);
		}
		var node=sortNode;
		node.select();
		document.getElementById('dataInterfaceFrame').contentWindow.clearNodeId();
		document.getElementById('nodeId').value = node.id;
		var tabs = Ext.getCmp('schemeTabPanelId');
		if(node.id != 'S000000'){
			if(tabs.getActiveTab().id == 'tab2'){
				var dataframe = document.getElementById('dataInterfaceFrame');
				dataframe.contentWindow.showTree(node.id);
			}else{
				tabs.setActiveTab("tab2");
			}
		}
	}
	
	 function loop(node,codetype){
		  if(node.childNodes.length>0){
			  for(var i =0;i<node.childNodes.length ;i++){
				  if(node.childNodes[i].id==codetype){
				  		sortNode=node.childNodes[i];
				  }else{
				  	loop(node.childNodes[i],codetype)
				  }
			  }
			  
		  }
	  }
</script>
	<div id="viewAr" style="position: fixed;"></div>
	<div id="treeDiv"></div>
	<div id="opareaDiv" style="left: 205"></div>
	<div id="messageBox"></div>
