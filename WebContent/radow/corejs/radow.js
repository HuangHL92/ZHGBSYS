/***
 * **************************
 * 浙大网新恩普软件有限公司
 * 核三Radow框架标签库的核心js文件
 * version:2.0
 * auther:jinwei
 * date:2009-10-21
 * **************************
 * 修改记录
 * **************************
 * cueversion:2.0.1   修改了\n和'导致的无法提交问题 2009-12-31
 * cueversion:2.0.2   修改了页面中有些元素的id为数字开头（一般是默认的时候会出现这种情况）而导致的问题 2010-1-19
 * cueversion:2.0.3   增加了对Menu单击事件的支持，其使用和Button一样即某menuitem.onclick 2010-1-27
 * cueversion:2.0.4   增加了对事件触发而引起的Ajax请求方式的动态支持，即采用同步还是异步 2010-2-25
 * cueversion:2.0.5   增加了填充非表单元素的内容，即text或html 2010-3-18
 * cueversion:2.0.6   增加了对radio和checkbox的支持 2010-3-23
 * cueversion:2.0.7   修改了一个bug，因ID为总划线引起的
 * cueversion:2.0.8   修改了一个bug，关于同步异步的2010-6-28
 * cueversion:2.0.9   将遮罩提前到doEvent之处，即非ajax时遮罩，而是之前就遮罩 2010.8.19
 * cueversion:2.1.0   因为增加了IsFirstDisabled标注而对该文件做了升级处理 2010.8.20
 * cueversion:2.1.1   修复了保存时第一时间将按钮灰掉而导致的有些必选没填时无法恢复的问题 2010.9.3
 * cueversion:2.1.2   修改了afteredit自动注册事件导致表格下拉列其值不是代码的问题 2010.12.30
 * cueversion:2.1.3   修改了radow.util里统一设置方法的bug 2011.1.26
 * cueversion:2.1.4   修改了给单选动态绑定事件的方法，以前的attachEvent只支持IE，非IE下不能使用，因而对其做了修复。2011.2.18
 * cueversion:2.1.5   修改了getGridDivs方法，使其能支持谷歌浏览器下的情况。2011.2.18
 * cueversion:2.1.6   修改了关于stopEditing的操作，使其能更合理的处理停止编辑动作。 2011.5.5
 * cueversion:2.1.7   修改了IsFirstDisabled标注的bug  2011.7.20
 * cueversion:4.0.0   升级到extjs3而做的一些调整（2011.8）
 * cueversion:4.0.1   doTriggerClick事件做了兼容火狐的修改（2011.8.26）
 * cueversion:4.0.2   修改了doEvent方法，因为授权模块在opera下会有个异常(2011.9.26)
 * cueversion:5.0.0   增加了获取表格所有可见且有意义的列名，如radow.getGridVisibleCol（2012.5.29）
 * cueversion:5.0.1   增加了设置带图标元素的宽度方法（2012.8.17）
 * cueversion:5.0.2   修改util中的setValue方法，当给数字框设置值时自动进行数字类型转换（2013.1.22）
 * cueversion:5.0.3   修改自动禁止按钮，且在返回时自动处理启用 jinwei（2013.3.7）
 * cueversion:6.0.0   整合基本稳定
 * cueversion:6.0.1   修复模块在被潜入到第三方系统时出现的跨域访问问题 jinwei -- 2013.7.23
 * cueversion:6.0.2   修复div_10及以上时请求参数不能正确传到后台问题 -- jinwei 2013.8.27
 * cueversion:6.0.3   修复新整合commform后的原radow方式提示信息有回车报错问题 -- jinwei 2013.9.3
 * cueversion:6.0.4   修复doFocus方法不支持下拉框的问题 -- jinwei 2013.9.4
 * cueversion:6.0.5   调整doEvent方法，防止因为取不到主界面的当前登录名而导致数据传不到子页面去问题 -- jinwei 2013.10.22
 * cueversion:6.0.6   修复多行文本框有回车时值无法传入后台问题 -- jinwei 2013.11.6
 * cueversion:6.0.7   将页面加载和doInit的遮罩合并成一个，即在doInit完成后去掉遮罩 -- jinwei 2013.11.12
 * cueversion:6.0.8   修复当通过打开模式对话框时无法获取Main.jsp页面的登录名信息而出现事件无法触发问题 -- jinwei 2014.1.10
 * cueversion:6.0.9   修改doEvent时对是否要停止表格编辑的处理判断，使得更严谨 -- jinwei 2014.2.19
 * cueversion:6.1.0   修改doEvent使grid编辑后事件只遮罩grid自身，解决afteredit和query不能同时触发的问题 -- ljd 2014.2.20
 * cueversion:6.1.1   修复PageModel模式下存储过程抛出带单引号和回车异常时信息无法弹出 -- jinwei 2014.9.25
 * cueversion:6.1.2   修复在支持类似等百度编辑器时多行文本name为空的问题 -- jinwei 2014.10.10
 * cueversion:6.1.3   临时处理个人工作台下下拉框小键盘回车无法选中问题 -- jinwei 2014.11.3
 * cueversion:6.1.4   修改doFocus  修复combo onchange提示框时会自动打开下拉 -- ljd 2014.12.4
 * cueversion:6.1.5   修改doEvent方法，增加isQT参数 -- ljd 2015.3.31
 * cueversion:6.1.6   增加小贴士功能 -- ljd 2015.6.10
 * cueversion:6.1.7   在doEvent后执行函数pageCallback，该函数在页面上定义 -- zoulei 2016.5.31
 * cueversion:6.1.8   增加doEventAsync异步方法 -- zhangjian 2016.8.1
 * **************************
 */
var radow={
	radowElementType:"textfield,trigger,textarea,numberfield,datefield,combo,lovcombo,radio,checkbox,button,grid,editorgrid,menu-item",
	cueElement:null, //暂存当前元素
	cuePageEditGrids:[], //用来记录当前页的编辑表格
	disableBtns:[], //禁止按钮集合
	cueMessageCode:"0", //当前最近一次请求的状体，0表示成功，1表示失败
	//selfEventPrefix:"SP@", //如果事件类型以这个开头则认为其是自定义事件，即不绑定，但其它处理和正常事件一致
	/**
	 * 自动绑定事件到页面元素上
	 * @param {Object} evtents
	 */
	batchEvents:function(events){
        for ( i = 0; i < events.length; i++) {
            var eleId = events[i].event.substring(0, events[i].event.lastIndexOf("\."));
            if (eleId == "")
                continue;
            var eleType = radow.findElementTypeByID(eleId);
            //跳过自定义事件的注册(其实不用这样来做的，因为只要名字不在正常事件范围类就不会注册，如"dogridquery"事件)
            //if(eleType.indexOf(radow.selfEventPrefix)==0) continue;
            if (eleType == 'combo'||eleType == 'lovcombo') {
                eleId += "_combo";
            }
            var eventType = events[i].event.substr(events[i].event.lastIndexOf("\.") + 1);
            //alert(eventType);
            try {
                if (eventType == 'onchange') {
                    //alert(eleId);
                    if (eleType == 'combo') {
                        odin.ext.getCmp(eleId).onSelect = function(record, index) {
                            if (this.fireEvent('beforeselect', this, record, index) !== false) {
                                this.setValue(record.data[this.valueField || this.displayField]);
                                this.collapse();
                                odin.doAccForSelect(this);
                                this.fireEvent('select', this, record, index);
                                radow.doOnSelectChange(record, index, this);
                            }
                        };
                    }/* else if(eleType == 'datefield'){
                     odin.ext.getCmp(eleId).addListener('change',radow.doOnChange,this);
                     odin.ext.getCmp(eleId).menuListeners.sid = eleId;
                     odin.ext.getCmp(eleId).menuListeners.select = function(m,d){
                     this.setValue(d);
                     //radow.dateOnChange(this.menuListeners.sid);
                     }
                     }*/else {
                        odin.ext.getCmp(eleId).addListener('change', radow.doOnChange, this);
                    }
                } else if (eventType == 'onblur') {
                    odin.ext.getCmp(eleId).addListener('blur', radow.doOnBlur, this);
                } else if (eventType == 'onclick') {
                    if (eleType == "checkbox" || eleType == "img") {
                        odin.ext.get(eleId).on('click', radow.doOnClick, this);
                    } else if (eleType == "radio") {
                        var radioObjs = document.getElementsByName(eleId);
                        for ( r = 0; r < radioObjs.length; r++) {
                            //radioObjs[r].attachEvent('onclick',radow.doOnClick);
                            odin.ext.get(radioObjs[r]).on('click', radow.doOnClick);
                        }
                    } else {
                        odin.ext.getCmp(eleId).addListener('click', radow.doOnClick, this);
                    }
                } else if (eventType == 'onfocus') {
                    odin.ext.getCmp(eleId).addListener('focus', radow.doOnFocus, this);
                } else if (eventType == 'afteredit') {
                    odin.ext.getCmp(eleId).addListener('afteredit', radow.doAfterEdit);
                } else if (eventType == 'beforeedit') {
                    odin.ext.getCmp(eleId).addListener('beforeedit', radow.doBeforeEdit);
                } else if (eventType == 'rowclick') {
                    odin.ext.getCmp(eleId).addListener('rowclick', radow.doGridRowClick);
                } else if (eventType == 'rowdbclick') {
                    odin.ext.getCmp(eleId).addListener('rowdblclick', radow.doGridRowDbClick);
                } else if (eventType == 'ontriggerclick') {
                    odin.ext.get(eleId + "_icon").on('click', radow.doTriggerClick, this);
                    //odin.ext.getCmp(eleId).on('triggerclick', radow.doTriggerClick);
                } else if (eventType == 'tabchange') {
                    odin.ext.getCmp(eleId).addListener('tabchange', radow.doTabChange);
                }
            } catch(exp1) {

            }
        }

	},
	doTabChange:function(tabPanel,activeTab){
		radow.cueElement = null;
		var tabId =  activeTab.getId();
		var tabPanelId = tabPanel.getId();
		radow.doEvent(tabPanelId+".tabchange",tabId);
	},
	doGridRowClick:function(grid,rowIndex,event){
		radow.cueElement = null;
		var id = grid.getId();
		var namePath = radow.findElementNamePathById(id);
       	//eval("page_element_tree."+namePath+".data="+odin.ext.encode("["+odin.ext.encode(grid.store.getAt(rowIndex).data)+"]"));
        eval("page_element_tree."+namePath+".cueRowIndex='"+rowIndex+"'");
        //alert(odin.ext.encode(eval("page_element_tree."+grid.getId()+".data")));
        radow.doEvent(id+".rowclick");
	},
	doGridRowDbClick:function(grid,rowIndex,event){
		radow.cueElement = null;
		var id = grid.getId();
		var namePath = radow.findElementNamePathById(id);
        //eval("page_element_tree."+namePath+".data="+odin.ext.encode("["+odin.ext.encode(grid.store.getAt(rowIndex).data)+"]"));
        //alert(odin.ext.encode(eval("page_element_tree."+grid.getId()+".data")));
        eval("page_element_tree."+namePath+".cueRowIndex='"+rowIndex+"'");
        radow.doEvent(id+".rowdbclick");
	},
	doAfterEdit:function(e){
		radow.cueElement = null;
		var grid = e.grid;
        var record = e.record;
        var field = e.field;
        var originalValue = e.originalValue;
        var value = e.value;
        var row = e.row;
        var column = e.column;
        var id = grid.getId();
        var namePath = radow.findElementNamePathById(id);       
        //eval("page_element_tree."+namePath+".data="+odin.ext.encode("["+odin.ext.encode(record.data)+"]"));
        //alert(odin.ext.encode(eval("page_element_tree."+namePath+".data")));
        eval("page_element_tree."+namePath+".cueRowIndex='"+row+"'");
        odin.afterEditForEditGrid(e);
        radow.doEvent(id+".afteredit");
        grid.view.refresh(true);
	},
	doBeforeEdit:function(e){
		radow.cueElement = null;
		var grid = e.grid;
        var record = e.record;
        var field = e.field;
        var originalValue = e.originalValue;
        var value = e.value;
        var row = e.row;
        var column = e.column;
		var cancel = e.cancel;
		var id = grid.getId();
		e.cancel = false;
		var namePath = radow.findElementNamePathById(id); 
		//eval("page_element_tree."+namePath+".data="+odin.ext.encode("["+odin.ext.encode(record.data)+"]"));
        //alert(odin.ext.encode(eval("page_element_tree."+namePath+".data")));
        eval("page_element_tree."+namePath+".cueRowIndex='"+row+"'");
        radow.doEvent(id+".beforeedit");
	},
	getGridDivs:function(){
		var html = document.body.innerHTML;
		return html.match(/gridDiv_[^\s|^}|^>|^"|^'|^\\]*/g); //2011.2.18增加了“|^"”，其是因为在谷歌浏览器下【老的匹配出的值是gridDiv_grid6Data"】属性都带双引号的，而IE下没有
	},
	/**
	*自动构建页面元素类型树
	*/
	autoMakePageEleTree:function(){
		var e = odin.ext.ComponentMgr.all;
		var count = e.getCount();
		for(i=0;i<count;i++){
			try{
				var element = e.item(i);
				var xType = element.getXType();
				//alert(xType);
				var isMenuItem = (element instanceof Ext.menu.Item);
				if(isMenuItem){
					xType = "menu-item";
				}
				var eleId = element.getId();
				if(radow.radowElementType.indexOf(xType)>=0){
					var eleId = element.getId();
					if(eleId.indexOf("-")>=0) continue;
					if(eleId.indexOf('combo')>=0){
						eval("page_element_tree."+eleId.replace('_combo','')+"={}");
						eval("page_element_tree."+eleId.replace('_combo','')+".type = '"+xType+"'");
					}else{
						eval("page_element_tree."+eleId+"={}");
						eval("page_element_tree."+eleId+".type = '"+xType+"'");
					}
					radow.setLabelInfo(eleId);
				}
			}catch(e){
				//
			}
		}
		var gridDivs = radow.getGridDivs();
		//alert(gridDivs);
		if(gridDivs != null){	
			gridDivs = gridDivs.uniqueEx();	
			for(i=0;i<gridDivs.length;i++){
				var gid = gridDivs[i];
				gid = gid.substr(gid.indexOf("_")+1);
				var grid = odin.ext.getCmp(gid);
				eval("page_element_tree."+gid+"={}");
				eval("page_element_tree."+gid+".type = '"+grid.getXType()+"'");
				radow.setGridREDAndLabel(gid);
			}
		}
		//
		var hiddenEle = radow.getAllChildHiddenElement(null);
		for(var j=0;j<hiddenEle.length;j++){
			//alert(hiddenEle[j]);
			if(hiddenEle[j].match(/^div_\d+Data$/)!=null){
				continue;
			}
			var temp = eval("page_element_tree."+hiddenEle[j]);
			if(typeof temp == 'undefined'){
				eval("page_element_tree."+hiddenEle[j]+"={}");
				eval("page_element_tree."+hiddenEle[j]+".type = 'hidden'");	
			}
		}
		var areaEle = radow.getAllChildAreaElement(null);
		for(var j=0;j<areaEle.length;j++){
			if(areaEle[j]==""){
				continue;
			}
			var temp = eval("page_element_tree."+areaEle[j]);
			if(typeof temp == 'undefined'){
				eval("page_element_tree."+areaEle[j]+"={}");
				eval("page_element_tree."+areaEle[j]+".type = 'textarea'");
			}
		}
		page_element_tree.radow_parent_data = {};
		page_element_tree.radow_parent_data.type = 'textfield';
		
		//alert(odin.ext.encode(page_element_tree));
		radow.autoAddOtherInput();
		radow.autoAddDivParent();
	},
	autoAddOtherInput:function(){ //含radio、checkbox等
		var eList = document.getElementsByTagName("input");
		var count = eList.length;
		var pageEleTreeText = odin.ext.encode(page_element_tree);
		for(var c=0;c<count;c++){
			var cueEle = eList[c];
			var name = cueEle.name;
			if(!name){
				name = cueEle.id;
			}
			var type = cueEle.type;
			if(pageEleTreeText.indexOf("\""+name+"\":")<0){
				try{
					eval("page_element_tree."+name+"={}");
					if(type=="text"){				
						eval("page_element_tree."+name+".type = 'hidden'");	
					}else if(type == "radio" || type == "checkbox"){
						eval("page_element_tree."+name+".type = '"+type+"'");	
					}
				}catch(e){
				}
			}
		}
	},
	autoAddDivParent:function(){
		var newtree = {};
		var html = document.body.innerHTML;
		var divs = html.match(/div_\d+/g); //修改前/div_[^\s|^}|^>|^[|^"|^.|^']+/g
		for(var j=0;j<page_events.length;j++){
			var eventDataRank = page_events[j].eventDataRank;
			if(eventDataRank!=""){
				if(divs==null) divs = new Array();
				divs.push(eventDataRank);	
			}
		}
		if(divs!=null && divs.length>0){
			divs = divs.uniqueEx();
		}
		if(divs != null){
			//alert(divs);
			for(o in page_element_tree){	
				var source = eval("page_element_tree."+o);
				var child = null;	
				var isChild = false;				
				for(i=0;i<divs.length;i++){
					var div = divs[i];
					//alert(div);
					var divHtml = radow.getAllChildEleNames(div);
					//alert(divHtml);
					if(divHtml.indexOf("@"+o+"@")>=0){
						var ele = null;
						if(typeof eval("newtree."+div) != 'object'){
							ele = eval("newtree."+div+"={}");
							ele.type = 'normal';
						}else{
							ele = eval("newtree."+div);
						}
						child = eval("ele."+o+"={}");
						isChild = true;
						break;
					}
				}
				if(!isChild){
					child = eval("newtree."+o+"={}");
				}
				for(p in source){
					eval("child."+p+"=source."+p);
				}
			}
			page_element_tree = newtree;
		}	
		//alert(odin.encode(page_element_tree));
	},
	/**
	*根据元素ID从页面元素树里找到其类型
	*/	
	findElementTypeByID:function(id){
		var objNamePath = radow.findElementNamePathById(id);//eval("page_element_tree."+);
		if(objNamePath!=null){
			return eval("page_element_tree."+objNamePath).type;
		}else{
		    var tmp = document.getElementById(id);
		    
			if(tmp && tmp.tagName.toLowerCase() === 'img'){
				return 'img';
			}
		}
		return null;
	},
	findElementNamePathById:function(id){
		for(o in page_element_tree){
			var st = eval("page_element_tree."+o);
			if(o==id){
				return o;
			}else{
				for(sto in st){
					if(sto == id){
						return o+"."+sto;
					}
				}
			}
		}
		return null
	},
	/**
	*所有表单元素的onchange触发类
	*/
	doOnChange:function(elementObj){
		radow.cueElement = elementObj;
		var eventType = "onchange";
		this.doEvent(elementObj.getId().replace('_combo','')+"."+eventType);
	},
	doOnSelectChange:function(record,index,ele){
		radow.cueElement = ele;
		var eventType = "onchange";
		this.doEvent(ele.getId().replace('_combo','')+"."+eventType);
	},
	doOnBlur:function(elementObj){
		radow.cueElement = elementObj;
		var eventType = "onblur";
		this.doEvent(elementObj.getId().replace('_combo','')+"."+eventType);
	},
	dateOnChange:function(id){
		radow.cueElement = document.getElementById(id);
		radow.doEvent(id+".onchange");
	},
	doOnClick:function(elementObj){
		var eventType = "onclick";		
		var id;
		try{
			id = elementObj.getId();
			var e = radow.getElementEventObj(id+"."+eventType);
			if(e.firstDisabled){
				if(e.noRequiredValidate==false && odin.checkValue(document.forms.commForm)){
					radow.disableBtns.push(elementObj);
					elementObj.disable();
				}
			}
		}catch(e){	
			id = window.event.srcElement.name
			if(id==''){
				id = window.event.srcElement.id;
			}
		}
		radow.cueElement = document.getElementById(id);
		radow.doEvent(id+"."+eventType);
	},
	/**
	 * 处理按钮变灰后的自动启用操作
	 */
	doBtnsEnable:function(){
		var len = radow.disableBtns.length;
		for(var i=0;i<len;i++){
			var btn = radow.disableBtns[i];
			btn.enable();
		}
	},
	doTriggerClick:function(element){
		var id = '';
		if (window.event) {
			id = window.event.srcElement.name;
			if (id == '') {
				id = window.event.srcElement.id;
			}	
		}else if(element.target){ //兼容火狐
			id = element.target.id;
		}
		id = id.replace("_icon","");
		var value = odin.ext.getCmp(id).getValue();
		//alert(id+"_"+value);
		var eventType = "ontriggerclick";
		this.doEvent(id+"."+eventType,value);
	},
	doOnFocus:function(elementObj){
		var eventType = "onfocus";
		this.doEvent(elementObj.getId().replace('_combo','')+"."+eventType);
	},
	stopEditing:function(){
		if(this.cuePageEditGrids.length>0){
			for(i=0;i<this.cuePageEditGrids.length;i++){
				this.cuePageEditGrids[i].stopEditing();
			}
			return;
		}
		var gridDivs = radow.getGridDivs();
		if (gridDivs != null) {
			gridDivs = gridDivs.uniqueEx();
			for (i = 0; i < gridDivs.length; i++) {
				var gid = gridDivs[i];
				gid = gid.substr(gid.indexOf("_")+1);
				var grid = odin.ext.getCmp(gid);
				if(grid.getXType()=='editorgrid'){
					this.cuePageEditGrids[this.cuePageEditGrids.length] = grid;
					grid.stopEditing();
				}
			}
		}	
	},
	/**
	*核心事件处理公共类 
	* isDoStopEditing 表示该事件是否需要做stopEditing操作
	*/
	doEvent:function(event,eventParameter,isDoStopEditing){
		if (typeof isDoStopEditing=='undefined' || isDoStopEditing==true) {
			radow.stopEditing();
		}
		var eventNames = event;
		//alert(eventNames);
		var url = contextPath+'/radowAction.do?method=doEvent';
		var autoMask = true;
		var asynchronous = false;
		var param = {};
		var e = null;
		if(page_bs!="" && event.indexOf("dogridquery")>0){
			e = radow.getElementEventObj("dogridquery");
		}else{
			e = radow.getElementEventObj(event);
		}
		if(typeof e == 'object' && e!=null){
			if(e.oplog){
				//oplog == true  表示要记录日志
				param.userlog = odin.doOpLog();
			}
			autoMask = (e.autoNoMask?false:true);
			autoMask=false;
			if(autoMask){
				var tmpCmp =  null;			    
			    if(eventParameter != null && eventParameter !=""){
			    	try{
			    		var tmpEP = Ext.decode(eventParameter);
	                    tmpCmp = Ext.getCmp(tmpEP.currentDiv);
	                    
	                    if(tmpCmp != null&&tmpEP.currentEvent ==="onchange"&& tmpCmp.getXType()==="editorgrid"){
	                        param.tmpEl = tmpCmp.getEl();                        
	                    }else{
	                        param.tmpEl = Ext.get(document.body);
	                    }
			    	}catch(exception){
			    		 param.tmpEl = Ext.get(document.body);
			    	}			    				        
			    }else{
			        param.tmpEl = Ext.get(document.body);
			    }	
			    try{
			    	if(!radow.cm.is_autoNoMask_docheck || event!='doCheck'){
				    	param.tmpEl.mask(odin.msg, odin.msgCls);
				    }
			    }catch(exception){
			    	if(!Ext.isIE8)
			    		param.tmpEl.mask(odin.msg, odin.msgCls);
			    }
				autoMask = false;
			}
		}
		param.pageModel = page_pageModel;
		param.eventNames = eventNames;
		if(page_model!=''){
			param.model = page_model;
		}
		if(page_bs!=""){
			param.bs = page_bs;
			if(event=='doSave'){
				param.declarelog = radow.cm.doOpLog();
			}
		}
		if(page_initParams!=''){
			param.initParams = page_initParams;
		}
		var pdObj = null;
		try {
			pdObj = parent.document.getElementById('radow_parent_data'); 
		}catch(eobj){ //在opera下，授权模块这里会报出异常，所以进行了捕获
			pdObj = {};
			pdObj.value = "";
		}
		try{
			if(!odin.isWorkpf){
				param.currentLoginName = top.frames[1].currentLoginName; //增加登录时的用户名参数
			}
		}catch(eobj){}
		if(event == 'doInit'){
			autoMask = false;
			if(pdObj!=null){
				page_element_tree.radow_parent_data.data = pdObj.value;
			}
			param.rc = odin.ext.encode(page_element_tree);
		}else{ //非初始化事件
			//alert(odin.encode(e));
			var reqContent = radow.getRequestContentByEvent(e);
			//alert(odin.encode(reqContent));
			param.radow_parent_data = "";
			if(pdObj!=null){
				reqContent.radow_parent_data = {type:'textfield',data:pdObj.value};
			}else{
				reqContent.radow_parent_data = {type:'textfield',data:''};
			}
			param.rc = odin.ext.encode(reqContent);
			asynchronous = e.synchronous;
		}
		var isSentReq = true;
		if(eventNames!='doInit' && e.noRequiredValidate==false){
			isSentReq = odin.checkValue(document.forms.commForm);
			if(!isSentReq){
				radow.doBtnsEnable();
			}
		}
		if(typeof eventParameter != 'undefined' && eventParameter !=''){ //事件参数
			param.eventParameter = eventParameter;
		}
		
		try{
			if(qtobj){
				param.isQT=true;
			}			
		}catch(exception){
			param.isQT=false;
		}
		
		
		if(isSentReq){
			var index = eventNames.toLowerCase().indexOf('dogridquery');
			if(index>0){
				//alert(eventNames.substr(0,index-1));
				if(param.tmpEl != null){
				    param.tmpEl.unmask();
				    delete  param.tmpEl;
				}else{
				    Ext.get(document.body).unmask();
				}
				var gridName = eventNames.substr(0,index-1);
				if(page_bs!=""){
					param.cueGridId = gridName;
					eventNames = "dogridquery";
				}
				odin.ext.getCmp(gridName).store.proxy = new odin.ext.data.HttpProxy({'url':url+"&pageModel="+page_pageModel+"&eventNames="+eventNames});
				odin.loadPageGridWithQueryParams(gridName,param);
			}else{
				odin.Ajax.request(url,param,radow.responseAccessCenter,radow.responseAccessCenter,asynchronous,autoMask);
			}
		}else{
			Ext.get(document.body).unmask();
		}
	},
	/**
	*核心事件处理公共类异步 
	* isDoStopEditing 表示该事件是否需要做stopEditing操作
	*/
	doEventAsync:function(event,eventParameter,isDoStopEditing){
		if (typeof isDoStopEditing=='undefined' || isDoStopEditing==true) {
			radow.stopEditing();
		}
		var eventNames = event;
		//alert(eventNames);
		var url = contextPath+'/radowAction.do?method=doEvent';
		var autoMask = true;
		var asynchronous = true;
		var param = {};
		var e = null;
		if(page_bs!="" && event.indexOf("dogridquery")>0){
			e = radow.getElementEventObj("dogridquery");
		}else{
			e = radow.getElementEventObj(event);
		}
		if(typeof e == 'object' && e!=null){
			if(e.oplog){
				//oplog == true  表示要记录日志
				param.userlog = odin.doOpLog();
			}
			autoMask = (e.autoNoMask?false:true);
			if(autoMask){
				var tmpCmp =  null;			    
			    if(eventParameter != null && eventParameter !=""){
			    	try{
			    		var tmpEP = Ext.decode(eventParameter);
	                    tmpCmp = Ext.getCmp(tmpEP.currentDiv);
	                    
	                    if(tmpCmp != null&&tmpEP.currentEvent ==="onchange"&& tmpCmp.getXType()==="editorgrid"){
	                        param.tmpEl = tmpCmp.getEl();                        
	                    }else{
	                        param.tmpEl = Ext.get(document.body);
	                    }
			    	}catch(exception){
			    		 param.tmpEl = Ext.get(document.body);
			    	}			    				        
			    }else{
			        param.tmpEl = Ext.get(document.body);
			    }	
			    try{
			    	if(!radow.cm.is_autoNoMask_docheck || event!='doCheck'){
				    	param.tmpEl.mask(odin.msg, odin.msgCls);
				    }
			    }catch(exception){
			    	param.tmpEl.mask(odin.msg, odin.msgCls);
			    }
				autoMask = false;
			}
		}
		param.pageModel = page_pageModel;
		param.eventNames = eventNames;
		if(page_model!=''){
			param.model = page_model;
		}
		if(page_bs!=""){
			param.bs = page_bs;
			if(event=='doSave'){
				param.declarelog = radow.cm.doOpLog();
			}
		}
		if(page_initParams!=''){
			param.initParams = page_initParams;
		}
		var pdObj = null;
		try {
			pdObj = parent.document.getElementById('radow_parent_data'); 
		}catch(eobj){ //在opera下，授权模块这里会报出异常，所以进行了捕获
			pdObj = {};
			pdObj.value = "";
		}
		try{
			if(!odin.isWorkpf){
				param.currentLoginName = top.frames[1].currentLoginName; //增加登录时的用户名参数
			}
		}catch(eobj){}
		if(event == 'doInit'){
			autoMask = false;
			if(pdObj!=null){
				page_element_tree.radow_parent_data.data = pdObj.value;
			}
			param.rc = odin.ext.encode(page_element_tree);
		}else{ //非初始化事件
			//alert(odin.encode(e));
			var reqContent = radow.getRequestContentByEvent(e);
			//alert(odin.encode(reqContent));
			param.radow_parent_data = "";
			if(pdObj!=null){
				reqContent.radow_parent_data = {type:'textfield',data:pdObj.value};
			}else{
				reqContent.radow_parent_data = {type:'textfield',data:''};
			}
			param.rc = odin.ext.encode(reqContent);
		}
		var isSentReq = true;
		if(eventNames!='doInit' && e.noRequiredValidate==false){
			isSentReq = odin.checkValue(document.forms.commForm);
			if(!isSentReq){
				radow.doBtnsEnable();
			}
		}
		if(typeof eventParameter != 'undefined' && eventParameter !=''){ //事件参数
			param.eventParameter = eventParameter;
		}
		
		try{
			if(qtobj){
				param.isQT=true;
			}			
		}catch(exception){
			param.isQT=false;
		}
		
		
		if(isSentReq){
			var index = eventNames.toLowerCase().indexOf('dogridquery');
			if(index>0){
				//alert(eventNames.substr(0,index-1));
				if(param.tmpEl != null){
				    param.tmpEl.unmask();
				    delete  param.tmpEl;
				}else{
				    Ext.get(document.body).unmask();
				}
				var gridName = eventNames.substr(0,index-1);
				if(page_bs!=""){
					param.cueGridId = gridName;
					eventNames = "dogridquery";
				}
				odin.ext.getCmp(gridName).store.proxy = new odin.ext.data.HttpProxy({'url':url+"&pageModel="+page_pageModel+"&eventNames="+eventNames});
				odin.loadPageGridWithQueryParams(gridName,param);
			}else{
				odin.Ajax.request(url,param,radow.responseAccessCenter,radow.responseAccessCenter,asynchronous,autoMask);
			}
		}else{
			Ext.get(document.body).unmask();
		}
	},
	/**
	*事件统一响应处理函数
	*/
	responseAccessCenter:function(response){
		radow.doBtnsEnable();
		radow.cueMessageCode = response.messageCode;
		if(response.elementsScript.indexOf("\n")>0){
			response.elementsScript = response.elementsScript.replace(/\r/gi,"");
			response.elementsScript = response.elementsScript.replace(/\n/gi,"\\n");
		}
		if(response.mainMessage.indexOf("'")>0 && response.mainMessage.indexOf("\\'")<0){
			response.mainMessage = response.mainMessage.replace(/'/gi,"\\'");
		}
		if(response.mainMessage.indexOf("\n")>0){
			response.mainMessage = response.mainMessage.replace(/\r/gi,"");
			response.mainMessage = response.mainMessage.replace(/\n/gi,"\\n");
		}
		if(response.messageCode==1 && response.mainMessage.indexOf("radow.cm.confirm")>=0){//通过异常来处理confirm机制 jinwei 2013,3.14
			eval(response.mainMessage.replace(/\\/g,''));
			return;
		}else{
			eval(response.elementsScript);//执行脚本
		}
		if(response.selfResponseFunc!=null&&response.selfResponseFunc!=''){
			eval(response.selfResponseFunc)(response);
		}else{
			//alert(odin.encode(response));
			if(response.nextEvents.length>0){
				var msgFuncCont = "";
				var doEventScript = "";
				for(var i=0;i<response.nextEvents.length;i++){
					var ne = response.nextEvents[i];
					if(ne.nextEventName!='' && ne.nextEventValue==''){
						var e = "radow.doEvent('"+ne.nextEventName+"','"+ne.nextEventParameter+"');";
						msgFuncCont += e;
						doEventScript += e;
					}else if(ne.nextEventName!='' && ne.nextEventValue!=''){
						msgFuncCont += "if(arguments[0]=='"+ne.nextEventValue+"'){";
						msgFuncCont += "radow.doEvent('"+ne.nextEventName+"','"+ne.nextEventParameter+"');";
						msgFuncCont += "}";
					}else if(ne.nextEventValue!='' && ne.nextBackFunc!=''){
						msgFuncCont += "if(arguments[0]=='"+ne.nextEventValue+"'){";
						msgFuncCont += ne.nextBackFunc;
						msgFuncCont += "}";
					}else if(ne.nextBackFunc!=''){
						msgFuncCont += ne.nextBackFunc;
					}				
				}
				//alert(response.messageType+"('"+response.mainMessage+"',function(){"+msgFuncCont+"})");
				if(response.showMsg){
					if(response.messageCode==1 && radow.cm){
						radow.cm.doFailMsgAndFail(response,msgFuncCont);
					}else{
						if(response.messageCode==1){
							msgFuncCont = "";
						}
						eval(response.messageType+"('"+response.mainMessage+"',function(){radow.doFocus(response);"+msgFuncCont+"})");
					}
				}else{
					eval(doEventScript);
				}
			}else{
				if(response.showMsg){
					if(response.messageCode==1 && radow.cm){
						radow.cm.doFailMsgAndFail(response);
					}else{
						eval(response.messageType+"('"+response.mainMessage+"',function(){radow.doFocus(response);})"); //弹出提示框
					}
				}
			}
		}
		/*if(response.messageCode=='1' && radow.cm){
			radow.cm.clearInvalid(document.commForm);
		}*/
		/*
		if(response.mainMessage != ''){
			eval(response.messageType+"('"+response.mainMessage+"')"); //弹出提示框
		}
		eval(response.elementsScript);//执行脚本
		//触发下一个事件
		if(response.nextEventName!=null && response.nextEventName!=""){
			radow.doEvent(response.nextEventName);
		}
		*/
		//请求后执行该行数，  主要用于初始化后执行
		if(pageCallback){
			pageCallback(response);
		}
	},
	doFocus:function(response){ //处理焦点
		try{
			if(radow.cueElement!=null){
			//if(response.messageCode==1 && radow.cueElement!=null){ //处理失败，抛出异常那种
				if(radow.cueElement.type=='hidden'){
					var realEle = odin.ext.getCmp(radow.cueElement.id+"_combo");
					if(realEle){
						realEle.focus();
					}
				}else{
					var id=radow.cueElement.id;
					if(id.indexOf('combo')==-1){
						radow.cueElement.focus();
					}
				}
			}
		}catch(e){
		}
	},
	/**
	*   获取所有元素的值
	*	构建页面元素的请求{ type:'',data:'',aab002:{type:''}} 
	{"aab001":{"type":"textfield"},"aab002":{"type":"numberfield"},"aab003":{"type":"textfield"},"aab004_combo":{"type":"combo"},"aab007":{"type":"textfield"},"aab006":{"type":"radio"},"aab008":{"type":"textfield"},"aab005":{"type":"checkbox"},"btn1":{"type":"button"}}
	*/
	getRequestContent:function(e){
		for(id in page_element_tree){
			radow.getElementDataToRC(id);
			var child = eval("page_element_tree."+id);
			if(child.type=='grid' || child.type=='editorgrid'){
				if(e.event.indexOf(id)==0 && e.gridDataRank == 'cuerow'){
					child = radow.addOneDataToRC(page_element_tree,id,child);
				}else{
					child = radow.addGridDataToRC(page_element_tree,id,child);
				}
			}else if(child.type == 'normal'){
				for(c in child){
					var cEle = eval("page_element_tree."+id+"."+c);
					if(typeof cEle == 'object'){
						for(var s in cEle){
							if(s!='type' && s!='cueRowIndex'){
								if(cEle.type !='grid' && cEle.type!='editorgrid'){
									radow.getElementDataToRC(id+"."+c);
								}else{
									if(e.event.indexOf(c)==0 && e.gridDataRank == 'cuerow'){
										radow.addOneDataToRC(page_element_tree,id+"."+c,cEle);
									}else{
										radow.addGridDataToRC(page_element_tree,id+"."+c,cEle);
									}
								}
							}					
						}
					}				
				}
			}
		}
		//alert(odin.ext.encode(page_element_tree));
	},
	getElementDataToRC:function(id){
		try{
			var type;
			try{
				type = eval('page_element_tree.'+id+'.type');
			}catch(e){
				return;
			}
			var index = id.indexOf(".");
			var valueId = (index>0?id.substr(index+1):id);
			radow.setLabelInfo(valueId,id);
			if(type == 'textfield' || type=='textarea' || type=='numberfield' 
					|| type=='datefield' || type =='hidden' || type == 'textarea'){
				var textValue = document.getElementById(valueId).value.replace(/\r\n/g,'\\r\\n');
				textValue = textValue.replace(/\n/g,"\\n");
				textValue = textValue.replace(/\"/g,"\\\"");
				textValue = textValue.replace(/\'/g,"\\\'");
				//alert(textValue);	
				eval('page_element_tree.'+id+'.data="'+textValue+'"');
			}else if(type == 'combo'){
				eval('page_element_tree.'+id+'.data="'+document.getElementById(valueId).value+'"');
			}else if(type == 'lovcombo'){
				eval('page_element_tree.'+id+'.data="'+odin.ext.getCmp(valueId+"_combo").getCheckedValue('key')+'"');
			}else if(type == 'checkbox'){
				var checked = document.getElementById(valueId).checked;
				//alert(checked);
				if(checked){
					eval('page_element_tree.'+id+'.data="1"');
				}else{
					eval('page_element_tree.'+id+'.data="0"');
				}
			}else if(type == 'radio'){
				var radios = document.getElementsByName(valueId);
				eval('page_element_tree.'+id+'.data="0"');
				for(i=0;i<radios.length;i++){
					if(radios[i].checked){
						eval('page_element_tree.'+id+'.data="'+radios[i].value+'"');
					}
				}
			}else if(type == 'normal'){ //div normal …………
				var divObj = eval('page_element_tree.'+id);
				//alert(odin.encode(page_element_tree));
				divObj.data = "";
				for(var child in divObj){
					if(child == 'type' || child== 'data') continue;
					//alert(id+"."+child);
					radow.getElementDataToRC(id+"."+child);
				}
			}else if(type == 'trigger'){
				eval('page_element_tree.'+id+'.data="'+document.getElementById(valueId).value+'"');
			}/*else if(typeof type == 'object'){
				for(var objId in type){
					radow.getElementDataToRC(id+"."+objId);
				}
			}*/else{
				eval('page_element_tree.'+id+'.data=""');
			}
		}catch(e){
		}
	},
	getElementEventObj:function(eventName){
		var e = null;
		for(var i=0;i<page_events.length;i++){
			var pe = page_events[i];
			if(pe.event==eventName){
				e = pe;
			}
		}
		return e;
	}
	,
	/**
	*根据事件名获取事件所需要的数据
	*/
	getRequestContentByEvent:function(e){
		var reqCont = {};
		var index = e.event.indexOf('.');
		var id = ( index>=0?e.event.substr(0,index):"" );
		var tempid = radow.findElementNamePathById(id);
		if(tempid!=null){
			id = tempid;
		}
		var element = null;
		if(id!=""){
			element = eval("page_element_tree."+id)
		}
		if(e==null || (e.eventDataRank=='' && e.eventDataSelfDefine=='')){
			//找不到对应的事件，或者该事件没有在后台标注出来(取全部数据) 
			//排除editorgrid即它什么都不标时取当前编辑行数据
			/*
			if(element !=null && (element.type=='grid' || element.type=='editorgrid') ){
				if(e.gridDataRank == 'cuerow'){
					reqCont = radow.addOneDataToRC(reqCont,id,element);
				}else{
					reqCont = radow.addGridDataToRC(reqCont,id,element);
				}
			}else{
				radow.getRequestContent();
				reqCont = page_element_tree;
			}
			*/
			radow.getRequestContent(e);
			reqCont = page_element_tree;
		}else{
			reqCont = radow.getEventDataRank(reqCont,e.eventDataRank);
			reqCont = radow.getEventDataSelfDefine(reqCont,e.eventDataSelfDefine);
			if(element !=null && (element.type=='grid' || element.type=='editorgrid') ){
				if(e.gridDataRank == 'cuerow'){
					reqCont = radow.addOneDataToRC(reqCont,id,element);
				}else{
					reqCont = radow.addGridDataToRC(reqCont,id,element);
				}
			}
		}
		//alert(odin.ext.encode(reqCont));
		return reqCont;
	},
	addOneDataToRC:function(rc,id,pageTreeNode){
		var realId = id;
		if(id.indexOf(".")>0){
			realId = id.substr(id.indexOf(".")+1);
			var t = eval("rc."+id.substr(0,id.indexOf("."))+"={}");
			t.type = "normal";
			t.data = "";
		}
		var obj = eval("rc."+id+"={}");
		obj.type = pageTreeNode.type;
		obj.cueRowIndex = pageTreeNode.cueRowIndex;
		var store = odin.ext.getCmp(realId).store;
		if(store.getCount()>pageTreeNode.cueRowIndex){
			var data = new Array();
			data[0] = odin.encode(odin.ext.getCmp(realId).store.getAt(pageTreeNode.cueRowIndex).data);
			obj.data = odin.encode(data);
		}else{
			obj.data = "";
		}
		radow.setGridREDAndLabel(realId,id);
		return rc;
	},
	addGridDataToRC:function(rc,id,pageTreeNode){
		var realId = id;
		if(id.indexOf(".")>0){
			realId = id.substr(id.indexOf(".")+1);
			var t = eval("rc."+id.substr(0,id.indexOf("."))+"={}");
			t.type = "normal";
			t.data = "";
		}
		//alert(realId);
		var s = odin.ext.getCmp(realId).store;
		var data = new Array();
		for(var i =0;i<s.getCount();i++){
			data[i] = odin.ext.encode(odin.ext.encode(s.getAt(i).data));
		}
		var obj = eval("rc."+id+"={}");
		obj.type = pageTreeNode.type;
		obj.cueRowIndex = pageTreeNode.cueRowIndex;
		obj.data = odin.encode(data);
		radow.setGridREDAndLabel(realId,id);
		return rc;
	},
	getEventDataRank:function(rc,eventDataRank){
		if(eventDataRank=='' || eventDataRank=='null'){
			return rc;
		}		
		
		var childEleNames = radow.getAllChildEleNames(eventDataRank);
		//alert(childEleNames);
		//radow.addElementToRC(page_element_tree,childEleNames,rc);
		
		rc = radow.addElementToRC(eval('page_element_tree.'+eventDataRank),childEleNames,rc,eventDataRank);
		return rc;
	},
	addElementToRC:function(node,childEleNames,rc,eventDataRank){
		var rcObj = {};
		rcObj.type = "normal";
		rcObj.data = "";
		for(id in node){
			if(childEleNames.indexOf("@"+id)>=0){
				radow.getElementDataToRC(eventDataRank+"."+id);
				var obj = eval('rcObj.'+id+"={}");
				var pageEle = eval('node.'+id);
				obj.type = pageEle.type;
				obj.data = pageEle.data;
			}	
		}
		eval("rc."+eventDataRank+"=rcObj");
		return rc;
	},
	getEventDataSelfDefine:function(rc,eventDataSelfDefine){
		if(eventDataSelfDefine=='' || eventDataSelfDefine=='null'){
			return rc;
		}
		var selfs = eventDataSelfDefine.split(",");
		for(var i=0;i<selfs.length;i++){
			var id = selfs[i];
			radow.getElementDataToRC(id);
			var obj = eval('rc.'+id+"={}");
			var pageEle = eval('page_element_tree.'+id);
			obj.type = pageEle.type;
			obj.data = pageEle.data;
		}
		return rc;
	},
	radowInit:function(){
		for(o in radow.util){
			eval("radow."+o+"=radow.util."+o);
		}
		for(o in radow.PageModeEngine){
			eval("radow."+o+"=radow.PageModeEngine."+o);
		}
		for(o in radow.renderer){
			eval("radow."+o+"=radow.renderer."+o);
		}
	},
	/**
	 * 根据事件获取当前元素ID
	 * @param {} e
	 * @return {}
	 */
	getCueElement:function(e){
		var id = null;
		if (window.event) {
			id = window.event.srcElement.name;
			if (id == '') {
				id = window.event.srcElement.id;
			}	
		}else if(e.getTarget){
			id = e.getTarget().getId();
		}else if(e.target){ //兼容火狐
			id = e.target.id;
		}
		return id;
	},
	/**
	 * 根据元素ID设置元素的label，到页面请求参数中
	 * @param {} valueId 元素的真实ID
	 * @param {} id 元素在页面元素中的路径，即可为div_1.aab001
	 */
	setLabelInfo:function(valueId,id){
		var labelId = valueId.replace('_combo','')
		var labelObj = document.getElementById(labelId+"SpanId");
		if(!id){
			id = labelId;
		}
		if(labelObj){
			var label = labelObj.innerText;
			if(label.indexOf("*")==0){
				label = label.substr(1);
			}
			eval('page_element_tree.'+id+'.label="'+label+'"');
		}
		var REDH = radow.getREDType(valueId);
		eval('page_element_tree.'+id+'.REDH="'+REDH+'"');
	},
	/**
	 * 取项目的编辑类型
	 */
	getREDType:function(fieldName) {
		var obj = Ext.getCmp(fieldName);
		var el = Ext.getDom(fieldName);
		if (fieldName.indexOf("_combo") != -1) {
			return "N";
		}
		if (obj || el) {
			if (obj && obj.getSize().height == 0 && obj.getSize().width == 0) {
				return "H";
			} else if (el.required && (el.required == 'true' || el.required == true)) {
				return "R";
			} else if (el.disabled && (el.disabled == 'true' || el.disabled == true)) {
				return "D";
			} else if (!el.disabled || (el.disabled == 'false')) {
				return "E";
			} else {
				return "N";
			}
		} else {
			return "N";
		}
	},
	setGridREDAndLabel:function(gridId,id){
		if(!id){
			id = gridId;
		}
		var gridColumnModel = Ext.getCmp(gridId).getColumnModel();
		var redInfo = {};
		var labelInfo = {};
		var selectallInfo = {};
		var colsType = {};
		for (var j = 0; j < gridColumnModel.getColumnCount(); j++) {
			var dataIndex = gridColumnModel.getDataIndex(j);
			if(dataIndex==""){
				continue;
			}
			var column = gridColumnModel.getColumnById(gridColumnModel.getColumnId(j));
			labelInfo[dataIndex] = (gridColumnModel.getColumnHeader(j) == null ? "" : gridColumnModel.getColumnHeader(j));
			redInfo[dataIndex] = radow.gridRED(column);
			var selectallcol = "selectall_" + gridId + "_" + dataIndex;
			var selectallcolObj = document.getElementById(selectallcol);
			if (selectallcolObj) {
				var ischecked = false;
				if(selectallcolObj.className=='x-grid3-check-col-on'){
					ischecked = true;
				}
				selectallInfo[dataIndex] = ischecked;
			}
			if(column.editor){
				var xtype = column.editor.getXType();
				if(xtype.indexOf('date')>=0){
					colsType[dataIndex] = "date";
				}else if(xtype.indexOf('number')>=0){
					colsType[dataIndex] = "number";
				}else{
					colsType[dataIndex] = "string";
				}
			}
		}
		eval('page_element_tree.'+id+'.label='+odin.encode(labelInfo));
		eval('page_element_tree.'+id+'.REDH='+odin.encode(redInfo));
		eval('page_element_tree.'+id+'.selectall='+odin.encode(selectallInfo));
		eval('page_element_tree.'+id+'.colsType='+odin.encode(colsType));
		var ispage = radow.isPageGridDiv(gridId);
		eval('page_element_tree.'+id+'.isPageGrid='+ispage);
		if(ispage){
			var pageingToolbar = (Ext.getCmp(gridId).getBottomToolbar() || Ext.getCmp(gridId).getTopToolbar());
			eval('page_element_tree.'+id+'.limit='+pageingToolbar.pageSize);
		}
	},
	/**
	 * 取项目的grid编辑类型
	 */
	gridRED:function(column) {
		if (column.hidden && (column.hidden == 'true' || column.hidden == true)) {
			return "H";
		} else if (column.required && (column.required == 'true' || column.required == true)) {
			return "R";
		} else if (column.editable && (column.editable == 'true' || column.editable == true)) {
			return "E";
		} else if (!column.editable || column.editable == "false") {
			return "D";
		} else {
			return "N";
		}
	},
	/**
	 * Ext的xtype转换成type：number、date、string
	 * @param {} xtype
	 * @return {String}
	 */
	toType:function(xtype) {
		if (xtype == null) {
			return "string";
		}
		if (xtype.indexOf("number") >= 0) {
			return "number";
		} else if (xtype.indexOf("date") >= 0) {
			return "date";
		} else {
			return "string";
		}
	},
	/**
	 * 判断是否为分页grid
	 */
	isPageGridDiv:function(divId) {
		if (Ext.getCmp(divId) && ((Ext.getCmp(divId).getTopToolbar() && Ext.getCmp(divId).getTopToolbar().pageSize) || (Ext.getCmp(divId).getBottomToolbar() && Ext.getCmp(divId).getBottomToolbar().pageSize))) {
			return true;
		} else {
			return false;
		}
	},
	/**
	 * 初始化事件处理完后的后续处理
	 */
	doInitRes:function(){
		if(typeof loading != 'undefined'){
			loading.remove();
		}
	}
	
	,
	/**
	 * 小贴士
	 * title----贴士标题
	 * autoHide----间隔多久关闭
	 * html ----贴士内容如：
	 * '<font color="red">参保户籍人数（覆盖率）：</font><br/>实际参保人数 / 户籍人数<hr style="border:0;background-color:#7FBFEE;height:1px;"/><font color="red">已登记人数（ 登记率）：</font><br/>已登记人数 / 户籍人口总数<hr style="border:0;background-color:#7FBFEE;height:1px;"/><font color="red">需调查人数（比例）：</font><br/>需调查人数 / 户籍人口总数<hr style="border:0;background-color:#7FBFEE;height:1px;"/><font color="red">已调查人数（比例）：</font><br/>已调查人数 / 需调查人数'
	 */
	openTips:function(title,autoHide,html){
		var tipw = new MyLib.TipsWindow(
				    {
				        title: title,
				        autoHide: autoHide,
				        html:html
				    });
				    tipw.show();
	}
};
function pageCallback(){}






