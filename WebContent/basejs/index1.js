
window.history.forward(1);
var curparent;  
var commParams = {}; //全局comm参数
var psqueryBuffer = "";
var cpqueryBuffer = "";
var psidqueryBuffer = "";
var viewMode = 0; // 0为1024模式，1为整屏模式
function changeView() {
	var fs = parent.document.getElementsByTagName("frameset")[0];
	if (viewMode == 0) {
		viewMode = 1;
		fs.cols = "0,*,0";
		odin.ext.get("header").dom.style.width = "100%";
		odin.ext.get("header_top").dom.style.width = "100%";
	} else {
		viewMode = 0;
		fs.cols = "*,1024,*";
	}
	window.setTimeout(autoSetMenuWidth, 200);
}
// 自动调整菜单栏宽度
function autoSetMenuWidth() {
	if (!odin.ext.isIE) {
		var t = document.getElementById("header_nav").clientWidth;
		var width = t - 307;
		//document.getElementById("header_bady").style.width = width + "px";
	}
}
function changeTheme(themeType){
	if(!themeType){
		if(document.getElementById("indexCss").getAttribute("href")=="css/theme-test.css"){
			odin.ext.util.CSS.swapStyleSheet('indexCss', 'css/index.css'); 
			odin.ext.util.CSS.swapStyleSheet('extThemeCss', 'basejs/ext/resources/css/xtheme-blue.css');
		}else{
			odin.ext.util.CSS.swapStyleSheet('indexCss', 'css/theme-test.css'); 
			odin.ext.util.CSS.swapStyleSheet('extThemeCss', 'basejs/ext/resources/css/ext-all-xtheme-green.css');
		}
	}
}
 
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
	                text: '关闭本页',
	                handler : function(){
	                    tabs.remove(ctxItem);
	                }
	            },{
	                id: tabs.id + '-close-others',
	                text: '关闭其他页',
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
    	defaults: {autoScroll:true,autoWidth:true},
    	plugins: new Ext.ux.TabCloseMenu(),
    	activeItem: 0,
    	items:{
    		title:'欢迎',
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
			this.mon(this.strip, 'click', this.onTitleClick, this);
	    },
	    onTitleClick:function(e){
	    	var t = this.findTargets(e); 
	    	if(t.item.title=='人员信息维护'){
	    		document.getElementById('I'+t.item.id).contentWindow.reloadtree();
	    	}
	    	if(t.item.title=='登记记录'){
	    		document.getElementById('I'+t.item.id).contentWindow.reloadGrid();
	    	}
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
    var radow_begin = null; 
    function loadPage(treenode, url,text){
		//alert(url);
    	radow_begin = new Date().getTime(); 
    	addTab(treenode.text,treenode.id,g_contextpath+url, false);
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
		if(tab.title=='人员信息维护'){
			document.getElementById('I'+aid).contentWindow.reloadtree();
		}
		if(tab.title=='登记记录'){
			document.getElementById('I'+aid).contentWindow.reloadGrid();
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
    function setPsqueryBuffer(psquery) {
		psqueryBuffer = psquery;
	};
	function getPsqueryBuffer() {
		return psqueryBuffer;
	};
	/**
	 * 打开弹出窗口
	 */
	function doOpenPupWinOnTop(src, title, width, height, initParams, parentWin) {
		if (parentWin == null) {
			parentWin = this;
		}
		doOpenPopWindow(src, title, width, height, initParams, parentWin);
	};
	/**
	 * 打开弹出窗口
	 */
	function doOpenPupWindow(src, title, width, height, initParams, parentWin) {
		if (parentWin == null) {
			parentWin = this;
		}
		doOpenPopWindow(src, title, width, height, initParams, parentWin);
	};
	/**
	 * 打开弹出窗口
	 */
	function doOpenPopWindow(src, title, width, height, initParams, parentWin) {
		if (parentWin == null) {
			parentWin = this;
		}
		tempParentWin = parentWin;
		var windowId = "pupWindow";
		var popWindow = Ext.getCmp(windowId);
		popWindow.setTitle(title); // 标题
		if (width <= 1) {
			if (width >= 0) {// 小数
				width = document.body.clientWidth * width;
			} else { // 负数
				width = document.body.clientWidth + width;
			}
		}
		if (height <= 1) {
			if (height >= 0) {// 小数
				height = document.body.clientHeight * height;
			} else { // 负数
				height = document.body.clientHeight + height;
			}
		}
		popWindow.setSize(width, height); // 宽度 高度
		showWindowWithSrc(windowId, src, initParams, "doPupWinOnload(this)");
		popWindow.center(); // 居中
	};
	/**
	 * 兼容IE、Firefox的iframe窗口获取函数
	 */
	function getFrame(id) {
		return document.getElementById(id).contentWindow || document.frames[id];
	}
	/**
	 * 显示窗口状态改变触发的事件
	 */
	function doTopOnreadystatechange(iframe) {
		if (iframe.readyState == 'complete') {
			/*try {
				getFrame(iframe.id).setParentWin(tempParentWin);
			} catch (e) {
			}*/
			getFrame(iframe.id).setParentWin(tempParentWin);
			radow.cm.parentWin=tempParentWin;
		}
	}
	var tempParentWin;
		/**
	 * 显示窗口加载完成触发的事件
	 */
	function doPupWinOnload(iframe) {
		getFrame(iframe.id).setParentWin(tempParentWin);

	}
	/**
	 * 根据传入的地址显示窗口
	 */
	function showWindowWithSrc(windowId, newSrc, initParams, onload) {
		if (newSrc.indexOf("http:") == -1 && newSrc.indexOf("www.") == -1 && newSrc.indexOf(contextPath) != 0) {
			newSrc = contextPath + newSrc;
		}
		if (document.all("iframe_" + windowId)) {
			newSrc = addUrlParam(newSrc, "initParams=" + encodeURIComponent(initParams));
			document.all("iframe_" + windowId).src = newSrc;
		} else {
			Ext.getCmp(windowId).html = "<iframe style=\"background:white;border:none;\" width=\"100%\" height=\"100%\" id=\"iframe_" + windowId + "\" name=\"iframe_" + windowId + (onload == null ? "" : "\" onload=\"" + onload) + "\" src=\"" + newSrc + "\"></iframe>";
		}
		if (initParams) {
			commParams={};
			commParams.initParams = initParams;
			commParams.initParamsUsedBy = null;
		}
		Ext.getCmp(windowId).show(Ext.getCmp(windowId));
		Ext.getCmp(windowId).focus();
	};
	
	/**
	 * 给url加参数
	 * @param {} url 原始url
	 * @param {} paramUrl 参数url，如 &a=1&b=2
	 * @return {} 返回值
	 */
	function addUrlParam(url, paramUrl) {
		var paramArray = paramUrl.split("&");
		var newUrl = url.replace("#", "");
		for (var i = 0; i < paramArray.length; i++) {
			var param = paramArray[i];
			if (param.indexOf("=") != -1) {
				var paramName = param.substring(0, param.indexOf("=")).trim();
				if (newUrl.indexOf(paramName + "=") != -1) {
					var re = eval('/(' + paramName + '=)([^&]*)/gi');
					newUrl = newUrl.replace(re, param);
				} else {
					if (newUrl.indexOf("?") != -1) {
						newUrl = newUrl + "&" + param;
					} else {
						newUrl = newUrl + "?" + param;
					}
				}
			}
		}
		return newUrl;
	}
	
	
    Ext.onReady(function(){

      Ext.state.Manager.setProvider(new Ext.state.CookieProvider());
		
		var hd = new Ext.Panel({
			border: false,
			layout:'anchor',
			region:'north',
			height:65,
			contentEl:'header'
		});
		
		var csb = new Ext.Panel({
			
			region:'center',
	    	deferredRender:false,
	    	frame:true,
	    	border: true,
			layout:'anchor',
	    	defaults: {autoScroll:true,autoWidth:true},
			contentEl:'centerbody'
		});
		
	
	  if(typeof menuroot == 'undefined'){
	     window.menuroot = new Ext.tree.TreeNode({text: '业务菜单',draggable:false, id:'menuroot'});;
	  }	
      var leftnav= new Ext.Panel({
                    region:'west',
                    id:'west-panel',
                    title:' ',
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
                    contentEl : 'leftall'
                    
                });
      
       var bm = new Ext.Panel({
			border: true,
			layout:'anchor',
			region:'south',
			height:30,
			items: [{
				xtype:'box',
				el:'bottom',
				border:false
			}
			]
		});         
      Ext.override(Ext.layout.BorderLayout, {
	    onLayout : function(ct, target){
	        var collapsed;
	        if(!this.rendered){
	            target.position();
	            target.addClass('x-border-layout-ct');
	            var items = ct.items.items;
	            collapsed = [];
	            for(var i = 0, len = items.length; i < len; i++) {
	                var c = items[i];
	                var pos = c.region;
	                if(c.collapsed){
	                    collapsed.push(c);
	                }
	                c.collapsed = false;
	                if(!c.rendered){
	                    c.cls = c.cls ? c.cls +' x-border-panel' : 'x-border-panel';
	                    c.render(target, i);
	                }
	                this[pos] = pos != 'center' && c.split ?
	                    new Ext.layout.BorderLayout.SplitRegion(this, c.initialConfig, pos) :
	                    new Ext.layout.BorderLayout.Region(this, c.initialConfig, pos);
	                this[pos].render(target, c);
	            }
	            this.rendered = true;
	        }

	        var size = target.getViewSize();
	        if (size.width < this.minWidth) {
	            target.setStyle('width', this.minWidth + 'px');
	            size.width = this.minWidth;
	            target.up('').setStyle('overflow', 'auto');
	        } else {
	            target.setStyle('width', '');
	        }//新增加的  尝试控制最小高度
	        if (size.height < this.minHeight) {
	            target.setStyle('height', this.minHeight + 'px');
	            size.height = this.minHeight;
	            target.up('').setStyle('overflow', 'auto');
	        } else {
	            target.setStyle('height', '');
	        }
	        if(size.width < 20 || size.height < 20){ // display none?
	            if(collapsed){
	                this.restoreCollapsed = collapsed;
	            }
	            return;
	        }else if(this.restoreCollapsed){
	            collapsed = this.restoreCollapsed;
	            delete this.restoreCollapsed;
	        }
	
	        var w = size.width, h = size.height;
	        var centerW = w, centerH = h, centerY = 0, centerX = 0;
	
	        var n = this.north, s = this.south, west = this.west, e = this.east, c = this.center;
	        if(!c){
	            throw 'No center region defined in BorderLayout ' + ct.id;
	        }
	
	        if(n && n.isVisible()){
	            var b = n.getSize();
	            var m = n.getMargins();
	            b.width = w - (m.left+m.right);
	            b.x = m.left;
	            b.y = m.top;
	            centerY = b.height + b.y + m.bottom;
	            centerH -= centerY;
	            n.applyLayout(b);
	        }
	        if(s && s.isVisible()){
	            var b = s.getSize();
	            var m = s.getMargins();
	            b.width = w - (m.left+m.right);
	            b.x = m.left;
	            var totalHeight = (b.height + m.top + m.bottom);
	            b.y = h - totalHeight + m.top;
	            centerH -= totalHeight;
	            s.applyLayout(b);
	        }
	        if(west && west.isVisible()){
	            var b = west.getSize();
	            var m = west.getMargins();
	            b.height = centerH - (m.top+m.bottom);
	            b.x = m.left;
	            b.y = centerY + m.top;
	            var totalWidth = (b.width + m.left + m.right);
	            centerX += totalWidth;
	            centerW -= totalWidth;
	            west.applyLayout(b);
	        }
	        if(e && e.isVisible()){
	            var b = e.getSize();
	            var m = e.getMargins();
	            b.height = centerH - (m.top+m.bottom);
	            var totalWidth = (b.width + m.left + m.right);
	            b.x = w - totalWidth + m.left;
	            b.y = centerY + m.top;
	            centerW -= totalWidth;
	            e.applyLayout(b);
	        }
	
	        var m = c.getMargins();
	        var centerBox = {
	            x: centerX + m.left,
	            y: centerY + m.top,
	            width: centerW - (m.left+m.right),
	            height: centerH - (m.top+m.bottom)
	        };
	        c.applyLayout(centerBox);
	
	        if(collapsed){
	            for(var i = 0, len = collapsed.length; i < len; i++){
	                collapsed[i].collapse(false);
	            }
	        }
	
	        if(Ext.isIE && Ext.isStrict){ // workaround IE strict repainting issue
	            target.repaint();
	        }
	    }
	});  
	
	
	var i = 0;
	while (true) {
		i++;
		var menurooti = eval('typeof(menuroot' + i + ')=="undefined"?null:menuroot' + i);
		if (menurooti) {
			var elId = 'mainmenudiv' + i;
			leftnav.add({
						contentEl : elId,
						title : menurooti.text,
						iconCls : 'iconnav',
						border : false,
						listeners : {
							expand : function() {
								setTimeout('tree' + (this.contentEl.substring(this.contentEl.length - (this.contentEl.length-"mainmenudiv".length))) + '.doLayout()');
							}
						}
					});
		} else {
			break;
		}
	}
      var viewport = new Ext.Viewport({
            layout:'border',
            layoutConfig: {
                minWidth: 1024,
                minHeight:543//minHeight:Ext.isIE?543:643
            },
            //items:[hd,leftnav,tabs]
            items:[hd,csb] 
        });
      viewport.on("resize",function(obj,adjWidth,adjHeight,rawWidth,rawHeight){
      		setTimeout("autoSetMenuWidth()",200);
      });
      document.getElementById('leftall').style.display='block';   
      document.getElementById('header').style.display='block';   
      //tree.render();
    });
function getSysType() {
	return "insiis";
}