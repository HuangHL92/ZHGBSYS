<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<odin:hidden property="printType"/>
<odin:hidden property="printId"/>
<odin:hidden property = "nodeid" />
<odin:hidden property = "text" />
<script type="text/javascript">
	Ext.onReady(function(){
		var nodemenu; 
		var tree = new Ext.tree.TreePanel({
			title : '汇总版访问接口方案配置', 
			id : 'schemeTreeId', 
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
			rootVisible : true,			//是否隐藏根节点,很多情况下，我们选择隐藏根节点增加美观性
			border : true,				//边框
			animate : true,				//动画效果
			tbar : [
			        {
			        	text : '新建方案或脚本', 
			        	icon : 'images/add.gif',
			        	handler : function(){
			        		var nodeid = document.getElementById('nodeid').value;
			        		if(nodeid == 'S000000'){
								if(newFlag()){                                                //如果已经打开了一个创建方案或脚本页面
									odin.alert("您好，您当前已经打开一个新建数据访问接口方案或数据访问接口方案脚本信息页面，请检查。");
								}else{
									var newNode = {
												id : 'FA_' + generateUUID(), 
												text : "新建数据访问接口方案...", 
												leaf : true, 
												parentId : nodeid,
												icon : 'module_img/ZWHZYQ_001_007/level1_16.gif'  
										};
									addTab(newNode, 'NEW', 'SCHEME');
								}
							}else if(nodeid.substring(0, 2) == "FA"){
								if(newFlag()){                                                //如果已经打开了一个创建方案或脚本页面
									odin.alert("您好，您当前已经打开一个新建数据访问接口方案或数据访问接口方案脚本信息页面，请检查。");
								}else{
									var newNode = { 
												id : 'JB_' + generateUUID(), 
												text : "新建数据访问接口方案脚本...", 
												leaf : true,
												parentId : nodeid, 
												icon : 'module_img/ZWHZYQ_001_007/level3_16.gif'  
										};  
										addTab(newNode, 'NEW', 'SCRIPT'); 
								}
							}
			        	}
			        }
			        //, '-', 
			        //{
			        //	text : '修改方案', 
			        //	icon : 'images/keyedit.gif', 
			        //	handler : function(){
			        //		var tabs = Ext.getCmp('schemeTabPanelId');
			        //		var nodeid = document.getElementById('nodeid').value;
			        //		var tab = tabs.getItem(nodeid);
					//		
					//		var nodeid = document.getElementById('nodeid').value;
					//		var printId = document.getElementById('printId').value;
					//		var text = document.getElementById('text').value;
					//		var modifyNode = {
					//							id : nodeid, 
					//							text : text, 
					//							leaf : true, 
					//							parentId : printId
					//					};
					//		modifyTab(modifyNode, 'modify', 'SCHEME');
					//		
			        //	}
			        //}
			        ],
			loader : new Ext.tree.TreeLoader({
				dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysmanager.ZWHZYQ_001_007.ZWHZYQ_001_007_Main&eventNames=getTreeJsonData'
			}),	
			listeners : {
				'click': function(node){                                       //左键单击事件
					node.select();
					
					document.getElementById('nodeid').value = node.id;
					document.getElementById('text').value = node.text;
					document.getElementById('printId').value = node.parentNode.id;
					
					var tabs = Ext.getCmp('schemeTabPanelId');                      
					if(node.id != 'S000000'){                                  //如果单击的不是根节点
						if(node.id.substring(0, 2) == 'FA'){                   //如果单击的是方案节点
							addTab(node, 'UPDATE', 'SCHEME');                  //添加方案Tab页面 
						}else if(node.id.substring(0, 2) == 'JB'){             //如果单击的是脚本页面
							addTab(node, 'UPDATE', 'SCRIPT');                  //添加脚本Tab页面
						}
					}else{
						tabs.setActiveTab("defaultTab");                       //如果单击根节点,显示defaultTab即是帮助页面
					}
				},
				'contextmenu' : function(node, e){                             //鼠标右键单击事件
					node.select();
					e.preventDefault();
					if(nodemenu != null){
						nodemenu.removeAll();
					}
					if(node.id.substring(0, 2) != 'FA' && node.id.substring(0, 2) != 'JB'){       //如果右击的树节点不是方案和脚本节点
						nodemenu = new Ext.menu.Menu({                                            //打开创建接口方案菜单
							items : [{
								text : "新建数据访问接口方案", 
								id : 'addNode', 
								icon : 'module_img/ZWHZYQ_001_007/level1_16.gif',
								handler : function(){
									if(newFlag()){                                                //如果已经打开了一个创建方案或脚本页面
										odin.alert("您好，您当前已经打开一个新建数据访问接口方案或数据访问接口方案脚本信息页面，请检查。");
									}else{
										var newNode = {
												id : 'FA_' + generateUUID(), 
												text : "新建数据访问接口方案...", 
												leaf : true, 
												icon : 'module_img/ZWHZYQ_001_007/level1_16.gif'  
										};
										node.expand();
										node.appendChild(newNode);
										newNode = tree.getNodeById(newNode.id);
										tree.getSelectionModel().select(newNode);
										addTab(newNode, 'NEW', 'SCHEME');
									}
								}
							}]
						});
						//nodemenu.showAt(e.getPoint())
					}else if(node.id.substring(0, 2) == 'FA'){
						nodemenu = new Ext.menu.Menu({
							items : [{
								text : "新建数据访问接口方案脚本", 
								id : 'addNode', 
								icon : 'module_img/ZWHZYQ_001_007/level3_16.gif',
								handler : function(){
									if(newFlag()){
										odin.alert("您当前已经新建一个数据访问接口方案或数据访问接口方案脚本信息页面！请检查。");
									}else{
										var newNode = { 
												id : 'JB_' + generateUUID(), 
												text : "新建数据访问接口方案脚本...", 
												leaf : true, 
												icon : 'module_img/ZWHZYQ_001_007/level3_16.gif'  
										};  
									    node.expand();
										node.appendChild(newNode);
										newNode = tree.getNodeById(newNode.id);
										tree.getSelectionModel().select(newNode);
										addTab(newNode, 'NEW', 'SCRIPT'); 
									}
								}
							}]
						});
						//nodemenu.showAt(e.getPoint())
					}
				}
			}
		});

		var root = new Ext.tree.AsyncTreeNode({
			id : 'S000000', 
			text : '汇总版访问接口方案', 
			draggable : false, //根节点不容许拖动
			expanded : true, 
			icon : 'module_img/ZWHZYQ_001_007/level1_16.gif'
		});
		tree.setRootNode(root);
		root.expand(true);
	
		var src = '';
		
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
			         {title : '操作区', id : 'defaultTab', html : '<iframe id="0*0*0*0*0" src="' + src + '" scrolling="auto" frameborder="0" width="100%" height="100%" style="clear:both;margin-left:0px;margin-right:0px;float:right;border-style：none" allowtransparency="yes"></iframe>'}
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

	function addTab(node, opmode, nodetype){
		var tabs = Ext.getCmp('schemeTabPanelId');
		var tabId = node.id;
		var tab = tabs.getItem(tabId);
		var title = "";
		var src = "";
		if(opmode == 'NEW'){
			if(nodetype == 'SCHEME'){
				title = "方案:新建数据访问接口方案...";
			}else if(nodetype == 'SCRIPT'){
				title = "脚本:新建数据访问接口方案脚本...";
			}
		}else{
			if(nodetype == 'SCHEME'){
				title = "方案:" + node.text;
			}else if(nodetype == 'SCRIPT'){
				title = "脚本:" + node.parentNode.text + "->" + node.text;
			}
		}
		if('S000000' == tabId){
			tabs.setActiveTab('defaultTab');
		}else{
			if(tab == null){
				if(nodetype == 'SCHEME'){                             //nodeId代表本节点的Id,preNodeId代表父节点的Id,opmode代表该节点是新建还是修改
					src = contextPath + "/radowAction.do?method=doEvent&pageModel=pages.sysmanager.ZWHZYQ_001_007.ZWHZYQ_001_007_SchemeModify&nodeId=" + node.id + "&preNodeId=" + node.parentId + "&opmode=" + opmode;
				}else if(nodetype == 'SCRIPT'){
					if(opmode == 'NEW'){
						src = contextPath + "/radowAction.do?method=doEvent&pageModel=pages.sysmanager.ZWHZYQ_001_007.ZWHZYQ_001_007_ScriptModify&nodeId=" + node.id + "&preNodeId=" + node.parentId + "&opmode=" + opmode;
					}else{
						src = contextPath + "/radowAction.do?method=doEvent&pageModel=pages.sysmanager.ZWHZYQ_001_007.ZWHZYQ_001_007_ScriptModify&nodeId=" + node.id + "&preNodeId=" + node.parentNode.id + "&opmode=" + opmode;
					}
				}
				tabs.add({
					title : title, 
					id : tabId, 
					closable : true, 
					border : false, 
					autoScroll : true, 
					html : '<iframe id="' + node.id + '" src="' + src + '" scrolling="auto" frameborder="0" width="100%" height="100%" allowtransparency="yes"></iframe>'   
				}).show();
			}
			tabs.setActiveTab(tabId);                                 //将tab页面加到工具条上面去。
		}
	}

	/*树收拢 方法*/
	function partExpand(){
		var tree = Ext.getCmp("schemeTreeId");
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
		var tree = Ext.getCmp('schemeTreeId');
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
		var tree = Ext.getCmp("schemeTreeId");
		tree.root.reload();
		tree.expandAll();
	}

	function newFlag(){
		var tabs = Ext.getCmp('schemeTabPanelId');
		var flag = false;
		tabs.items.each(function(item){   
			var title = item.title;  
			if(title == '方案:新建数据访问接口方案...' || title == '脚本:新建数据访问接口方案脚本...'){ 
				flag = true;     
			}
		}); 
		return flag;
	}

	function refreshTree(){
		var tree = Ext.getCmp("schemeTreeId");
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
</script>
<body>
	<table border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td><div id="treeDiv"></div></td>
			<td><div id="opareaDiv"></div></td>
		</tr>
	</table>
	<div id="messageBox"></div>
</body>
<script>
    function generateUUID(){
        var d = new Date().getTime();
        var uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
            var r = (d + Math.random()*16)%16 | 0;
            d = Math.floor(d/16);
            return (c=='x' ? r : (r&0x7|0x8)).toString(16);
        });
        return uuid;
    } 
</script>
