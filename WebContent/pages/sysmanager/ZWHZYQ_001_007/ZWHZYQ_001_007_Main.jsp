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
			title : '���ܰ���ʽӿڷ�������', 
			id : 'schemeTreeId', 
			el : 'treeDiv',				//��������ӵ�һ��ָ����div��,�ǳ���Ҫ��
			region : 'west', 
			collapseMode : 'mini',		// ������Ϊ��mini��ʱ�򣬷ָ����ϻ����һ������ť������󣬻Ὣ�����������
			width : 200, 
			split : true, 
			height : Ext.getBody().getHeight(),
			frame : false,				//��������
			enableDD : false,			//�Ƿ�֧����קЧ��
			containerScroll : true,		//�Ƿ�֧�ֹ�����
			autoScroll : true, 
			rootVisible : true,			//�Ƿ����ظ��ڵ�,�ܶ�����£�����ѡ�����ظ��ڵ�����������
			border : true,				//�߿�
			animate : true,				//����Ч��
			tbar : [
			        {
			        	text : '�½�������ű�', 
			        	icon : 'images/add.gif',
			        	handler : function(){
			        		var nodeid = document.getElementById('nodeid').value;
			        		if(nodeid == 'S000000'){
								if(newFlag()){                                                //����Ѿ�����һ������������ű�ҳ��
									odin.alert("���ã�����ǰ�Ѿ���һ���½����ݷ��ʽӿڷ��������ݷ��ʽӿڷ����ű���Ϣҳ�棬���顣");
								}else{
									var newNode = {
												id : 'FA_' + generateUUID(), 
												text : "�½����ݷ��ʽӿڷ���...", 
												leaf : true, 
												parentId : nodeid,
												icon : 'module_img/ZWHZYQ_001_007/level1_16.gif'  
										};
									addTab(newNode, 'NEW', 'SCHEME');
								}
							}else if(nodeid.substring(0, 2) == "FA"){
								if(newFlag()){                                                //����Ѿ�����һ������������ű�ҳ��
									odin.alert("���ã�����ǰ�Ѿ���һ���½����ݷ��ʽӿڷ��������ݷ��ʽӿڷ����ű���Ϣҳ�棬���顣");
								}else{
									var newNode = { 
												id : 'JB_' + generateUUID(), 
												text : "�½����ݷ��ʽӿڷ����ű�...", 
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
			        //	text : '�޸ķ���', 
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
				'click': function(node){                                       //��������¼�
					node.select();
					
					document.getElementById('nodeid').value = node.id;
					document.getElementById('text').value = node.text;
					document.getElementById('printId').value = node.parentNode.id;
					
					var tabs = Ext.getCmp('schemeTabPanelId');                      
					if(node.id != 'S000000'){                                  //��������Ĳ��Ǹ��ڵ�
						if(node.id.substring(0, 2) == 'FA'){                   //����������Ƿ����ڵ�
							addTab(node, 'UPDATE', 'SCHEME');                  //��ӷ���Tabҳ�� 
						}else if(node.id.substring(0, 2) == 'JB'){             //����������ǽű�ҳ��
							addTab(node, 'UPDATE', 'SCRIPT');                  //��ӽű�Tabҳ��
						}
					}else{
						tabs.setActiveTab("defaultTab");                       //����������ڵ�,��ʾdefaultTab���ǰ���ҳ��
					}
				},
				'contextmenu' : function(node, e){                             //����Ҽ������¼�
					node.select();
					e.preventDefault();
					if(nodemenu != null){
						nodemenu.removeAll();
					}
					if(node.id.substring(0, 2) != 'FA' && node.id.substring(0, 2) != 'JB'){       //����һ������ڵ㲻�Ƿ����ͽű��ڵ�
						nodemenu = new Ext.menu.Menu({                                            //�򿪴����ӿڷ����˵�
							items : [{
								text : "�½����ݷ��ʽӿڷ���", 
								id : 'addNode', 
								icon : 'module_img/ZWHZYQ_001_007/level1_16.gif',
								handler : function(){
									if(newFlag()){                                                //����Ѿ�����һ������������ű�ҳ��
										odin.alert("���ã�����ǰ�Ѿ���һ���½����ݷ��ʽӿڷ��������ݷ��ʽӿڷ����ű���Ϣҳ�棬���顣");
									}else{
										var newNode = {
												id : 'FA_' + generateUUID(), 
												text : "�½����ݷ��ʽӿڷ���...", 
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
								text : "�½����ݷ��ʽӿڷ����ű�", 
								id : 'addNode', 
								icon : 'module_img/ZWHZYQ_001_007/level3_16.gif',
								handler : function(){
									if(newFlag()){
										odin.alert("����ǰ�Ѿ��½�һ�����ݷ��ʽӿڷ��������ݷ��ʽӿڷ����ű���Ϣҳ�棡���顣");
									}else{
										var newNode = { 
												id : 'JB_' + generateUUID(), 
												text : "�½����ݷ��ʽӿڷ����ű�...", 
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
			text : '���ܰ���ʽӿڷ���', 
			draggable : false, //���ڵ㲻�����϶�
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
			activeTab : 0, //Ĭ�ϼ����һ��Tabҳ
			animScroll : true, 
			enableTabScroll : true, 
			plugins : new Ext.ux.TabCloseMenu(), 
			border : false, 
			items : [
			         {title : '������', id : 'defaultTab', html : '<iframe id="0*0*0*0*0" src="' + src + '" scrolling="auto" frameborder="0" width="100%" height="100%" style="clear:both;margin-left:0px;margin-right:0px;float:right;border-style��none" allowtransparency="yes"></iframe>'}
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
	
	/*tabҳ�Ҽ��ر�  ����*/
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
	                text : '�رձ�ҳ', 
	                handler : function(){
	                    tabs.remove(ctxItem);
	                }
	            }, {
	                id : tabs.id + '-close-others', 
	                text : '�ر�����ҳ', 
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
	
	/*tabҳ�ر�  ����*/
	function removeTabEvent(tabpanel, tab){
		var tabTitle = tab.title;
		if(haveChanges.in_array(tab.id)){
			Ext.MessageBox.show({
            	title : 'ϵͳ��ʾ', 
           		msg : '���ã���ǰ������Ϣ�Ѿ��������ĵ���δ���棬��ȷ��Ҫȡ�����Ĳ��رոñ༭ҳ����', 
          		multiline : false, 
            	width : 280, 
            	buttons : Ext.Msg.YESNO, 
            	animEl : 'messageBox', 
            	fn : function(btn, text){
            		if(btn == 'yes'){
            			//�Ƴ�beforeremove�¼���Ϊ�˷�ֹtabpanel.remove(tab)ʱ������ѭ��
            			tabpanel.un('beforeremove', removeTabEvent);
            			if(tab.title == '����:�½����ݷ��ʽӿڷ���...' || tab.title == '�ű�:�½����ݷ��ʽӿڷ����ű�...'){
            				delTreeNode(tab.id);
            			}
            			deletehaveChange(tab.id);
            			//�Ƴ�tab
            			tabpanel.remove(tab);
            			//����beforeremove�¼�
            			tabpanel.addListener('beforeremove', removeTabEvent, tabpanel);
            		}
            	}
			});
		}else{
			tabpanel.un('beforeremove', removeTabEvent);
			if(tab.title == '����:�½����ݷ��ʽӿڷ���...' || tab.title == '�ű�:�½����ݷ��ʽӿڷ����ű�...'){
				delTreeNode(tab.id);
			}
			//�Ƴ�tab	 
			tabpanel.remove(tab);
			tabpanel.addListener('beforeremove', removeTabEvent, tabpanel);
		}
		//��һ��ܹؼ�
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
				title = "����:�½����ݷ��ʽӿڷ���...";
			}else if(nodetype == 'SCRIPT'){
				title = "�ű�:�½����ݷ��ʽӿڷ����ű�...";
			}
		}else{
			if(nodetype == 'SCHEME'){
				title = "����:" + node.text;
			}else if(nodetype == 'SCRIPT'){
				title = "�ű�:" + node.parentNode.text + "->" + node.text;
			}
		}
		if('S000000' == tabId){
			tabs.setActiveTab('defaultTab');
		}else{
			if(tab == null){
				if(nodetype == 'SCHEME'){                             //nodeId�����ڵ��Id,preNodeId�����ڵ��Id,opmode����ýڵ����½������޸�
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
			tabs.setActiveTab(tabId);                                 //��tabҳ��ӵ�����������ȥ��
		}
	}

	/*����£ ����*/
	function partExpand(){
		var tree = Ext.getCmp("schemeTreeId");
		tree.collapseAll();
	}

	function updateTab(text, isScript){
		var tabs = Ext.getCmp('schemeTabPanelId');
		var tab = tabs.getActiveTab();
		if(isScript){
			tab.setTitle("�ű�:" + text);
		}else{
			tab.setTitle("����:" + text);
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

	//tab�������������
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
			if(title == '����:�½����ݷ��ʽӿڷ���...' || title == '�ű�:�½����ݷ��ʽӿڷ����ű�...'){ 
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
