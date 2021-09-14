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
			title : '��ѡ��Ҫά���Ĵ��뼯', 
			id : 'addTree', 
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
			rootVisible : false,		//�Ƿ����ظ��ڵ�,�ܶ�����£�����ѡ�����ظ��ڵ�����������
			border : true,				//�߿�
			animate : true,				//����Ч��
			loader : new Ext.tree.TreeLoader({
				dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysmanager.ZWHZYQ_001_006.ZWHZYQ_001_006_002.CodeTypeMain&eventNames=getTreeJsonData'
			}),	
			listeners : {
				'click': function(node){                                       //��������¼�
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
			text : '�����׼����', 
			draggable : false, //���ڵ㲻�����϶�
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
			activeTab : 0, //Ĭ�ϼ����һ��Tabҳ
			animScroll : true, 
			enableTabScroll : true, 
			plugins : new Ext.ux.TabCloseMenu(), 
			border : false, 
			items : [
			         {title : '�������', id : 'tab2', html : '<iframe id="dataInterfaceFrame" src="' + src + '"  scrolling="auto" frameborder="0" width="100%" height="100%" allowtransparency="yes"></iframe>'}
			],
			tbar:[
			      '->', 
					{
						text : '��£', 
						icon : 'images/icon/folderClosed.gif', 
						handler : function(){
							var childWindow = document.getElementById('dataInterfaceFrame').contentWindow;
							childWindow.partExpand();
						}
					}, '-', 
					{
						text : 'չ��', 
						icon : 'images/icon/folderOpen.gif', 
						handler : function(){
							var childWindow = document.getElementById('dataInterfaceFrame').contentWindow;
							childWindow.extend();
						}
					}, '-', 
					{
						text : '����', 
						icon : 'images/icon/folderClosed.gif', 
						handler : function(){
							var childWindow = document.getElementById('dataInterfaceFrame').contentWindow;
							var nodeId = document.getElementById('nodeId').value;
							childWindow.sorter(nodeId);
						}
					}, '-', 
			        {
			        	text : '����', 
			        	icon : 'images/add.gif', 
			        	handler : function(){
			        		var childWindow = document.getElementById('dataInterfaceFrame').contentWindow;
			        		var nodeId = document.getElementById('nodeId').value;
			        		childWindow.append(nodeId);
			        	}
			        }, '-', 
			        {
			        	text : '�޸�', 
			        	icon : 'images/i_2.gif', 
			        	handler : function(){
			        		var childWindow = document.getElementById('dataInterfaceFrame').contentWindow;
			        		var nodeId = document.getElementById('nodeId').value;
			        		childWindow.modify(nodeId);
			        	}
			        }, '-', 
			        {
			        	text : 'ɾ��', 
			        	icon : 'images/icon/delete.gif', 
			        	handler : function(){
			        		var childWindow = document.getElementById('dataInterfaceFrame').contentWindow;
			        		var nodeId = document.getElementById('nodeId').value;
			        		childWindow.deleteBtn(nodeId);
			        	}
			        }, '-', 
			        {
			        	text : '��ʾ', 
			        	icon : 'images/icon/folderClosed.gif', 
			        	handler : function(){
			        		var childWindow = document.getElementById('dataInterfaceFrame').contentWindow;
			        		var nodeId = document.getElementById('nodeId').value;
			        		childWindow.show(nodeId);
			        	}
			        }, '-', 
			        {
			        	text : '����', 
			        	icon : 'images/icon/refresh.png', 
			        	handler : function(){
			        		var childWindow = document.getElementById('dataInterfaceFrame').contentWindow;
			        		var nodeId = document.getElementById('nodeId').value;
			        		childWindow.shade(nodeId);
			        	}
			        }, '-', 
			        {
			        	text : '�·�', 
			        	icon : 'images/icon/imp.gif', 
			        	handler : function(){
			        		var childWindow = document.getElementById('dataInterfaceFrame').contentWindow;
			        		var nodeId = document.getElementById('nodeId').value;
			        		childWindow.download(nodeId);
			        	}
			        }, '-', 
			        {
			        	text : '����', 
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
	
	function getNode(){
		var tree = Ext.getCmp("addTree");
		var node = tree.getSelectionModel().getSelectedNode(); 
		return node;
	}

	/*����£ ����*/
	function partExpand(){
		var tree = Ext.getCmp("addTree");
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

	//tab�������������
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
			if(title == '����:�½����ݷ��ʽӿڷ���...' || title == '�½���Ϣ�ṹ����...'){ 
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
	
	/*�ر���Ӵ���ҳ��*/
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
