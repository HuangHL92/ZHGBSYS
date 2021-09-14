
window.history.forward(1);  

Ext.ux.TabCloseMenu = function(){
	    var tabs, menu, ctxItem;
	    this.init = function(tp){
	        tabs = tp;
	        tabs.on('contextmenu', onContextMenu);
	    }
	    function onContextMenu(ts, item, e){
	        if(!menu){ // create context menu on first right click
	            menu = new Ext.menu.Menu([{
	                id: tabs.id + '-close',
	                text: '�رձ�ҳ',
	                handler : function(){
	                    tabs.remove(ctxItem);
	                }
	            },{
	                id: tabs.id + '-close-others',
	                text: '�ر�����ҳ',
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
	
	var todaystr=YYMMDD();
	var weekdaystr=weekday();
	//var today2str=solarDay2();
	var today3str=solarDay3().replace(/(^\s*)|(\s*$)/g, "");
	
    var tabs = new Ext.TabPanel({
    	region:'center',
    	deferredRender:false,
    	frame:true,
    	minTabWidth:30,
    	tabWidth:135,
    	enableTabScroll:true,
    	defaults: {autoScroll:false,autoWidth:true},
    	plugins: new Ext.ux.TabCloseMenu(),
    	activeItem: 0,
    	items:{
    		title:'��ӭ',
    		closable:false,
    		id:'homepage',
    		iconCls:'iconwelcome',
    		html:'<Iframe width="100%" height="100%" scrolling="auto" frameborder="0" src="'+g_contextpath+'/Welcome.jsp'+'" style="clear:both;margin-left:0px;margin-right:0px;float:right;"></Iframe>'
    	},
    	initEvents: function(){
	        Ext.TabPanel.superclass.initEvents.call(this);
	        this.on('add', this.onAdd, this, {target: this});
	        this.on('remove', this.onRemove, this, {target: this});
	
	        this.mon(this.strip, 'mousedown', this.onStripMouseDown, this);
	        this.mon(this.strip, 'contextmenu', this.onStripContextMenu, this);
	        if(this.enableTabScroll){
	            this.mon(this.strip, 'mousewheel', this.onWheel, this);
	        }
			
			this.mon(this.strip, 'dblclick', this.onTitleDbClick, this);
	    },
	    onTitleDbClick:function(e){
	    	var t = this.findTargets(e);  
			if (t.item.fireEvent('beforeclose', t.item) !== false) {  
				t.item.fireEvent('close', t.item); 
				if(t.item.closable){
	            	this.remove(t.item);
	            }               
			}
		}
    });
    
    function loadPage(treenode, url,text){
		//alert(url);
		addTab(text,treenode.id,g_contextpath+url, false);
	};
	
	function addTab(atitle,aid,src,forced,autoRefresh){
      var tab=tabs.getItem(aid);
      if (forced)
      	aid = 'R'+(Math.random()*Math.random()*100000000);
      if (tab && !forced){ 
 		tabs.activate(tab);
		if(typeof autoRefresh!='undefined' && autoRefresh){
			document.getElementById('I'+aid).src = src;
		}
      }else{
        tabs.add({
        title: (atitle),
        id: aid,
        html: '<Iframe width="100%" height="100%" scrolling="auto" id="I'+aid+'" frameborder="0" src="'+src+'" style="clear:both;margin-left:0px;margin-right:0px;float:right;"></Iframe>',
	    closable:true
        }).show();  
		
      }
    };
    
    Ext.onReady(function(){

      Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
		
		var hd = new Ext.Panel({
			border: false,
			layout:'anchor',
			region:'north',
			height:65,
			items: [{
				xtype:'box',
				el:'header',
				border:false
				//anchor: 'none -25'
			}
			/*
			new Ext.Toolbar({
					items:[
						   		'    ',
						   		'<img src="'+g_contextpath+'/img/icon/currentuser.gif" height=16 width=16>',
								'��ǰ�û�:'+g_username ,								
								'-', 
								'',
								todaystr,
								weekdaystr,
								'-',
								today3str,
								'ϵͳ�汾:'+odin.version,
								'->',
								new Ext.Toolbar.Button({
									text:'����',
									icon:g_contextpath+'/img/icon/help.gif',
									cls:'x-btn-text-icon',
									tooltip :'��ʾ������Ϣ'
								}),
								new Ext.Toolbar.Button({
									text:'�˳�',
									cls:'x-btn-text-icon',
									icon:g_contextpath+'/img/icon/rss_load.gif',
									tooltip :'�˳�ϵͳ',
									handler :function(b, pressed){
										//alert('logout');
										if (confirm('ȷ��Ҫ�˳�ϵͳ��?'))
										  window.top.location.href=g_contextpath+'/logoffAction.do';}
								})
						   ]
				})
			*/
			]
		});
		
		
      var leftnav= new Ext.Panel({
                    region:'west',
                    id:'west-panel',
                    title:'������',
                    split:false,
                    width: 224,
                    minSize: 224,
                    maxSize: 224,
                    collapsible: true,
                    margins:'0 1 0 1',
                    layout:'accordion',
                    layoutConfig:{
                        animate:true
                    },
                    items: [{
                        contentEl: 'mainmenu2',
                        title:'ҵ��˵�',
                        //icon:g_contextpath+'/img/icon/folder_go.png',
                        //cls:'x-btn-text-icon',
                        iconCls:'iconnav',
                        border:false
                    }
                    /*
                    ,{
                        title:'��������',
                        html:'<p>ĳЩ��Ϣ</p>',
                        iconCls:'iconsearch',
                        border:false
                    }
                    ,{
                        title:'�ҵ���־',
                        html:'<p>��־��Ϣ</p>',
                        iconCls:'iconlog',
                        border:false
                    }
                    */
                    ]
                });
      var viewport = new Ext.Viewport({
            layout:'border',
            items:[hd,leftnav,tabs]
        });
      //tree.render();
    });
	