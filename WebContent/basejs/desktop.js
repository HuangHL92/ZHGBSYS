/***
 * **************************
 * ������¶���������޹�˾
 * ������ܵĺ���js�ļ�
 * version:1.0
 * auther:jinwei
 * date:2010-8-18
 * **************************
 * 
 * **************************
 */
var dt = {
	items:[],            //���пɼ��ص���������Ŀ��ΪJSON�����ʽ
	defaultColsCount:3,  //Ĭ������
	colsCount:null,      //��ǰ����
	totalcols:3,         //������
	defaultPanelIndex:0, //Ĭ�ϵ��������
	selfOrder:null,      //�û����϶�����µ�˳�����û�еĻ�ȡ��̨��Ĭ��˳��
	cookie:new odin.ext.state.CookieProvider(),
	isSaveToCookie:false,
	isFirstShowAll:false,//�û���һ�ε�¼ʱ�Ƿ���Ҫ��ʾȫ��������
	tools:[{             //������
	        id:'gear',
	        //qtip:'��ʾȫ����ӭ�����',
	        handler: function(){
	            odin.question("��ȷ��Ҫ�Ի�ӭ���������������",function(rtn){
	            	if(rtn=='ok'){
	            		//dt.showAllDesktop();
	            		odin.showWindowWithSrc('deskConWin','sys/DesktopConfig.jsp');
	            	}
	            });
	        }
	    },{
	        id:'close',
	        //qtip:'�رոû�ӭ�����',
	        handler: function(e, target, panel){
	        	odin.question("��ȷ��Ҫ�رոû�ӭ��������",function(rtn){
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
		//odin.alert("����������Ϣ�ɹ�");
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
	sortDesktopItems:function(){  //����
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
	                title: 'ռλ���',
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
			odin.question("��û�п���ʾ�Ļ�ӭ�������Ҫ���ڽ���������",function(rtn){
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