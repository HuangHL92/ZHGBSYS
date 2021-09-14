/***
 * **************************
 * 浙大网新恩普软件有限公司
 * 核三框架的核心js文件
 * version:1.0
 * auther:jinwei
 * date:2010-8-18
 * **************************
 * 
 * **************************
 */
var dt = {
	items:[],            //所有可加载的总桌面项目，为JSON数组格式
	defaultColsCount:3,  //默认列数
	colsCount:null,      //当前列数
	totalcols:3,         //总列数
	defaultPanelIndex:0, //默认的面板索引
	selfOrder:null,      //用户自拖动后的新的顺序，如果没有的话取后台的默认顺序
	cookie:new odin.ext.state.CookieProvider(),
	isSaveToCookie:false,
	isFirstShowAll:false,//用户第一次登录时是否需要显示全部桌面项
	tools:[{             //工具栏
	        id:'gear',
	        //qtip:'显示全部欢迎桌面项！',
	        handler: function(){
	            odin.question("您确定要对欢迎桌面项进行配置吗？",function(rtn){
	            	if(rtn=='ok'){
	            		//dt.showAllDesktop();
	            		odin.showWindowWithSrc('deskConWin','sys/DesktopConfig.jsp');
	            	}
	            });
	        }
	    },{
	        id:'close',
	        //qtip:'关闭该欢迎桌面项！',
	        handler: function(e, target, panel){
	        	odin.question("您确定要关闭该欢迎桌面项吗？",function(rtn){
	            	if(rtn=='ok'){
	            		eval("delete dt.selfOrder."+panel.getId());
			        	dt.saveSelfOrder();
			        	//alert(odin.encode(dt.selfOrder));
			            panel.ownerCt.remove(panel, true);
	            	}
	            });
	        }
	    }],
	showAllDesktop:function(){
		dt.cookie.clear('selfOrder');
	    dt.selfOrder = null;
		dt.saveSelfOrder();
		window.location.reload();
	},   
	getDeskTopItems:function(){
		var req = odin.Ajax.request(contextPath+"/common/desktopAction.do?method=query",{},odin.ajaxSuccessFunc,null,false,false);
		this.items = odin.ext.decode(req.responseText).data;
	},
	saveSelfOrder:function(){
		if(this.isSaveToCookie){
			this.cookie.set("selfOrder",odin.ext.encode(this.selfOrder));
		}else{
			odin.Ajax.request(contextPath+"/common/desktopAction.do?method=saveDeskconfig",{"deskconfig":odin.ext.encode(this.selfOrder)},dt.saveOrderResult,null,true,false);
		}
	},
	saveOrderResult:function(res){
		//odin.alert("保存配置信息成功");
	},
	getSelfOrder:function(){
		if(this.isSaveToCookie){
			this.selfOrder = odin.ext.decode(this.cookie.get("selfOrder",null));
		}else{
			var req = odin.Ajax.request(contextPath+"/common/desktopAction.do?method=getSelfDeskconfig",{},odin.ajaxSuccessFunc,null,false,false);
			var data = odin.ext.decode(req.responseText).data;
			if(data!=null && data!=""){
				this.selfOrder = odin.ext.decode(data);
			}
		}
		return this.selfOrder;
	},
	sortDesktopItems:function(){  //排序
		var len = this.items.length;
		for(var j=0;j<len-1;j++){
			for(var i=0;i<len;i++){
				if(i+1<len){
					if(this.items[i].orderno>this.items[i+1].orderno){
						var temp = this.items[i];
						this.items[i]=this.items[i+1];
						this.items[i+1] = temp;
					}
				}
			}
		}
		//alert(odin.encode(this.items));
	},
	showDesktop:function(){
		this.totalcols = dt_totalcols_count; 
		var dtitems = [];
		for(var j=0;j<this.items.length;j++){
			var t = this.items[j];
			if(this.selfOrder!=null){
				var selfDefValue = eval("this.selfOrder."+t.name);
				if(typeof selfDefValue != 'undefined'){
					t.orderno = selfDefValue;
				}else{
					continue;
				}
			}
		}
		this.sortDesktopItems();		
		for(var i=0;i<this.totalcols;i++){
			var itemconfig = {};
			itemconfig.columnWidth=1/parseInt(this.totalcols,10);
			itemconfig.style="padding:10px 0 10px 10px";
			itemconfig.items = [];
			for(var j=0;j<this.items.length;j++){
				var t = this.items[j];
				if(this.selfOrder!=null){
					var selfDefValue = eval("this.selfOrder."+t.name);
					if(typeof selfDefValue == 'undefined'){
						continue;
					}
				}else{
					if(!this.isFirstShowAll){
						continue;
					}
				}
				var order = parseInt(t.orderno,10);
				if(order<(i*10+10) && order>=(i*10)){
					var ct = {};
					ct.style = 'padding:0px 0 10px 0px';
					ct.title = t.title;
					ct.tools = this.tools;
					ct.height = parseInt(t.height,10);
					ct.frame = false;
					ct.id = t.name;
					ct.html = '<iframe width="100%" height="100%" scrolling="no" frameborder="0" src="'+contextPath+t.url+'"></iframe>';
					itemconfig.items[itemconfig.items.length++] = ct;
				}
			}
			if(itemconfig.items.length==0){
				itemconfig.items[0]={
					style:'padding:0px 0 10px 0px',
	                title: '占位面板',
	                html: '',
	                id:'dp_'+this.defaultPanelIndex++,
	                hidden:true
				}
			}
			dtitems[i] = itemconfig;
		}
		//alert(odin.ext.encode(dtitems));
		return dtitems;
	},
	desktopEnddrag:function(e){
		var ci = e.columnIndex;
		var position = e.position;
		var cueSource = e.panel;
		var rIndex = ci*10+position;
		for(o in dt.selfOrder){
			var value = eval("dt.selfOrder."+o);
			if(value>=rIndex&&value<(ci*10+10)){
				var index = value+1;
				eval("dt.selfOrder."+o+"="+index);
			}
		}
		eval("dt.selfOrder."+cueSource.getId()+"="+rIndex);
		//alert(odin.encode(dt.selfOrder));
		dt.saveSelfOrder();
	}
};
var desktop = function(){
	dt.getDeskTopItems();
	var selfOrder = dt.getSelfOrder();
	if(typeof  selfOrder=='undefined' || selfOrder==null || selfOrder==""){
		if(dt.isFirstShowAll){
			dt.selfOrder = {};
			for(var i=0;i<dt.items.length;i++){
				eval("dt.selfOrder."+dt.items[i].name+"="+parseInt(dt.items[i].orderno,10));
			}
			dt.saveSelfOrder();
		}else{
			odin.question("您没有可显示的欢迎桌面项，需要现在进行配置吗？",function(rtn){
	            if(rtn=='ok'){
	            	odin.showWindowWithSrc('deskConWin','sys/DesktopConfig.jsp');
	            }
	        });
		}
	}
	//alert(odin.encode(dt.selfOrder));
	var items = dt.showDesktop();
	return new odin.ext.ux.Portal({
	   items:items,
	   bodyStyle:'background:url(images/welcome.jpg);',    
	   applyTo: odin.ext.get('desktop')
	});
}
odin.ext.onReady(
	function(){
	    var p = desktop();
	    p.addListener('drop',dt.desktopEnddrag,p);
    }
);