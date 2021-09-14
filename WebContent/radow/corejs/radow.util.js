/***
 * **************************
 * 浙大网新恩普软件有限公司
 * 核三Radow框架的核心js文件
 * auther:jinwei
 * date:2013-10-10
 * version:6.0
 * version:6.0.1 调整addGridEmptyRow方法，使得支持可在某个给定索引的位置增加空行功能 jinwei 2013.10.10
 * version:6.0.2 调整获取页面textarea元素集合方法，解决name为空时JS报错（集成编辑器可能会导致）-- jinwei 2013.10.22
 * version:6.0.3 修复获取某div下面的子元素时而此div不存在时有bug问题 -- jinwei 2013.12.10
 * version:6.0.4 调整自动填充对象到界面方法，增加是否允许填空值，同时增加按照区域清空方法（修复数字填值时导致的异常） -- jinwei 2014.6.16
 * version:6.0.5 调整获取页面隐藏域方法，自动跳过name为空的隐藏域 -- jinwei 2014.10.13
 * * ***************************
 */
odin.ext.namespace('radow.util');
radow.util = {
	$:function(id){
		return document.getElementById(id);
	},
	N$:function(name){
		return document.getElementsByName(name);
	},
	NF$:function(name){
		return document.getElementsByName(name)[0];
	},
	TG$:function(tagName){
		return document.getElementsByTagName(tagName);
	},
	getAllChildEleNames:function(id){
		var parentEle = this.$(id);
		if(parentEle==null){
			return "";
		}
		var inputEle = parentEle.getElementsByTagName('input');
		var childEleNames = "";
		for(var j=0;j<inputEle.length;j++){
			childEleNames += "@"+inputEle[j].name+"@";
		}
		inputEle = parentEle.getElementsByTagName('textarea');
		for(var j=0;j<inputEle.length;j++){
			childEleNames += "@"+inputEle[j].name+"@";
		}
		return childEleNames;
	},
	getAllChildHiddenElement:function(id){
		var inputEle = null;
		if(typeof id != 'undefined' && id!=null){
			var parentEle = this.$(id);
			inputEle = parentEle.getElementsByTagName('input');
			
		}else{
			inputEle = this.TG$('input');
		}
		var hiddenObj = new Array();
		var index = 0;
		for(var j=0;j<inputEle.length;j++){
			//alert(inputEle[j].name+"_"+inputEle[j].type);
			if(inputEle[j].type == 'hidden'){
				//alert(inputEle[j].name);
				if(inputEle[j].name == "") continue;
				hiddenObj[index++] = inputEle[j].name;
			}
		}
		//alert(hiddenObj);
		return hiddenObj;
	},
	getAllChildAreaElement:function(id){
		var inputEle = null;
		if(typeof id != 'undefined' && id!=null){
			var parentEle = this.$(id);
			inputEle = parentEle.getElementsByTagName('textarea');
			
		}else{
			inputEle = this.TG$('textarea');
		}
		var areaObj = new Array();
		var index = 0;
		for(var j=0;j<inputEle.length;j++){
			if(inputEle[j].name == "") continue;
			areaObj[index++] = inputEle[j].name;
		}
		return areaObj;
	},
	openWindow:function(id,pageModel){
		var src = contextPath +'/radowAction.do?method=doEvent&pageModel='+pageModel;
		odin.showWindowWithSrc(id,src);
	},
	reloadPage:function(isReloadParent){
		/*var url = contextPath +'/radowAction.do?method=doEvent&pageModel='+page_pageModel;
		if(page_model!=''){
			url  += "&model=" + page_model;
		}
		if(page_bs!=''){
			url += "&bs=" + page_bs;
		}*/
		if(isReloadParent){
			//parent.window.location.href = url;
			parent.window.location.reload();
		}else{
			//window.location.href = url;
			window.location.reload();
		}
	},
	/**
	*根据一段JSon内容自动填充页面同名元素的值
	*jsonDataSource json格式的数据源，字符串类型，如："{'aab001':'123','aad009':'name'}"
	*isParent:是否在父页面
	*isFillEmptyValue：是否允许填充空值到页面
	*/
	autoFillElementValue:function(jsonDataSource,isParent,isFillEmptyValue){
		var ds = odin.ext.decode(jsonDataSource);
		var element_tree = null;
		if(isParent){
			element_tree = parent.page_element_tree;
		}else{
			element_tree = page_element_tree;
		}
		for(o in element_tree){
			var value = eval("ds."+o);
			if(typeof value == 'number'){
				value = ""+value;
			}
			var oObj = eval("element_tree."+o);
			if(oObj.type!="normal"){
				if(typeof isFillEmptyValue!='undefined' && true == isFillEmptyValue){
					if(typeof value == "undefined" || value=="null" || value == null){
						value = "";
					}
					radow.setValue(o,oObj.type,value,isParent);
					continue;
				}else{
					if(typeof value != "undefined" && value!="" && value!="null" && value!=null){
						radow.setValue(o,oObj.type,value,isParent);
						continue;
					}
				}
			}
			/*if(oObj.type!="normal" && typeof value != "undefined" && value!="" && value!="null" && value!=null){
				radow.setValue(o,oObj.type,value,isParent);
				continue;
			}*/
			for(child in oObj){
				if(child=='type'||child=='data')continue;
				value = eval("ds."+child);
				if(typeof value == 'number'){
					value = ""+value;
				}
				//alert(child);alert(value);
				/*if(typeof value != "undefined" && value!="" && value!="null" && value!=null){
					radow.setValue(child,eval("element_tree."+o+"."+child).type,value,isParent);
					break;
				}*/
				if(typeof isFillEmptyValue!='undefined' && true == isFillEmptyValue){
					if(typeof value == "undefined" || value=="null" || value == null){
						value = "";
					}
					radow.setValue(child,eval("element_tree."+o+"."+child).type,value,isParent);
					continue;
				}else{
					if(typeof value != "undefined" && value!="" && value!="null" && value!=null){
						radow.setValue(child,eval("element_tree."+o+"."+child).type,value,isParent);
						continue;
					}
				}
			}
		}
	},
	autoFillTextValue:function(jsonDataSource,isParent,isHtml){
		var ds = odin.ext.decode(jsonDataSource);
		for(o in ds){
			var value = eval("ds."+o);
			if(typeof isHtml == 'undefined'){
				isHtml = 'text';
			}else{
				isHtml = 'html';
			}
			try{
				radow.setValue(o,isHtml,value,isParent);
			}catch(e){
			}
		}
	},
	/**
	 * 根据一串json数据自动匹配填值，如{aab001:123}，则填充到id为aab001或“aab001_*”的元素中去
	 * @param {} jsonDataSource
	 * @param {} isParent
	 * @param {} cueDiv 当cueDiv为空时则填充整个页面匹配的元素值，否则的话只填充位于cueDiv下的元素信息
	 */
	autoFillElementByLike:function(jsonDataSource,isParent,cueDiv){
		var type = typeof(jsonDataSource);
		var data = null;
		if(type=='string'){
			data = odin.ext.decode(jsonDataSource);
		}else{
			data =  jsonDataSource;
		}
		var element_tree = null;
		if(isParent){
			element_tree = parent.page_element_tree;
		}else{
			element_tree = page_element_tree;
		}
		for(var id in data){
			for(var o in element_tree){
				var oObj = eval("element_tree."+o);
				if((id == o || o.indexOf(id+"_")==0)&&o.indexOf('_combo')<0){
					var value = data[id];
					if(oObj.type!="normal" && typeof value != "undefined" && value!="null" && value!=null){
						if(typeof cueDiv!='undefined' && odin.ext.query("#"+cueDiv+" #"+o).length>0){
							radow.setValue(o,oObj.type,value,isParent);
						}else if(cueDiv=='undefined'){
							radow.setValue(o,oObj.type,value,isParent);
						}
					}
				}
				for(child in oObj){
					if(child=='type'||child=='data')continue;
					if(id == child || child.indexOf(id+"_")==0 ||(id=='cpcombo' && child.indexOf("cpquery")==0 && child.indexOf("_combo")==-1)){
						if(id=='cpcombo' && child.indexOf("cpquery")==0 && data[id] != ""){
							setSelectData(child,odin.ext.decode(data[id].replace(/\\/g,"")));
						}else{
							var value = data[id];
							//alert(child);alert(value);
							if((id == child || child.indexOf(id+"_")==0)&&child.indexOf('_combo')<0){
								if(typeof cueDiv!='undefined' && odin.ext.query("#"+cueDiv+" #"+child).length>0){
									radow.setValue(child,eval("element_tree."+o+"."+child).type,value,isParent);
								}else if(cueDiv=='undefined'){
									radow.setValue(child,eval("element_tree."+o+"."+child).type,value,isParent);
								}
							}
						}
					}
				}
			}
		}
	},
	/**
	*	设置元素的值
	*   id:元素ID，或name
	*	type:元素类型
	*	value:新的值
	*	isParent：是否是设父页面的该元素
	*/
	setValue:function(id,type,value,isParent){
		//alert(id+"_"+type+"_"+value+"_"+isParent);
		if(value==null||value=='null'){
			value = "";
		}
		var pre = (isParent?"parent.":"");
		if(type=='textfield' || type=='trigger' ||type=='textarea' 
			|| type=='numberfield' || type=='datefield' || type =='hidden' || type == 'textarea'){
			if(type=='numberfield' && value.indexOf(".")==0){
				eval(pre+"document.getElementById('"+id+"').value='"+parseFloat(value,10)+"'");
			}else{
				eval(pre+"document.getElementById('"+id+"').value='"+value+"'");
			}
		}else if(type == 'combo'){
			eval(pre+"odin.setSelectValue('"+id+"','"+value+"');");
		}else if(type == 'checkbox'){
			eval(pre+"document.getElementById('"+id+"').checked="+value);
		}else if(type == 'radio'){
			var res = eval(pre+"document.getElementsByName('"+id+"')");
			for(var i=0;i<res.length;i++){
				var rValue = res[i].value;
				if(rValue==value){
					res[i].checked = true;
				}
			}
		}else if(type == 'text'){
			eval(pre+"document.getElementById('"+id+"').innerText='"+value+"'");
		}else if(type == 'html'){
			eval(pre+"document.getElementById('"+id+"').innerHTML='"+value+"'");
		}
	},
	addGridEmptyRow:function(id,rowIndex){
		var grid = odin.ext.getCmp(id);
		var store = grid.store;
		var gridColModel = grid.getColumnModel();
		var count = gridColModel.getColumnCount();
		var data = {};
		for(i =0;i<count;i++){
			var dataIndex = null;
			try{
				dataIndex = gridColModel.getDataIndex(i);
				if(dataIndex!=''){
					eval("data."+gridColModel.getDataIndex(i)+'=""');
				}
			}catch(e){
				continue;
			}
		}
		if(typeof rowIndex != 'undefined'){
			store.insert(rowIndex,new odin.ext.data.Record(data));
			grid.view.refresh(true);
		}else{
			store.insert(store.getCount(),new odin.ext.data.Record(data));	
		}
	},
	/**
	 * 获取表格的可见列，隐藏列、checkbox、行号列都过滤掉
	 * @param {} gridId   表格ID
	 */
	getGridVisibleCol:function(gridId){
		var g = odin.ext.getCmp(gridId);
		var cm = g.getColumnModel();
		var colCount = cm.getColumnCount();
		var colDataIndex = []; //存放最终选择的列
		for(var i=0;i<colCount;i++){
			//alert('第'+i+'行 '+'colName:'+cm.getColumnHeader(i)+','+'isHidden:'+cm.isHidden(i)+','+'dataIndex:'+cm.getDataIndex(i));
			//增加显示的列到数组不能为行号以及全选checkbox
			if(cm.isHidden(i)!=undefined && !cm.isHidden(i) && cm.getColumnHeader(i).indexOf('odin.selectAllFuncForE3')==-1){
				colDataIndex.push(cm.getDataIndex(i));
			}	
		}
		//alert(colDataIndex.uniqueEx());
		return colDataIndex.uniqueEx();
	},
	/**
	 * 设置下拉框的宽度（或者说是带图标的输入框，图标位置混乱），主要用在tab页下的那种下拉框的下拉项宽度太窄，即不和下拉框整体宽度一致，而是选项的宽度小于整体宽度，即变窄的情况
	 * @param {} triggerEleId
	 */
	setTriggerEleWidth : function(triggerEleId) {
		var cmp = odin.ext.getCmp(triggerEleId);
		var w = cmp.width;
		cmp.setWidth(0);
		cmp.setWidth(w + 16);
	},
	/**
	 * 清除某些Div下的所有表单表格数据信息
	 * @param {} divIds div的id，多个用逗号隔开
	 * 
	 */
	clearDivData:function(divIds){
		var divs = divIds.split(",");
		var gridDivs = radow.getGridDivs();
		if(gridDivs != null){	
			gridDivs = gridDivs.uniqueEx();	
		}
		for(var i=0;i<divs.length;i++){
			var divId = divs[i];
			var tagNames = ["input", "textarea"];
			for (var n = 0; n < tagNames.length; n++) {
				var elList = document.getElementById(divId).getElementsByTagName(tagNames[n]);
				for (var m = 0; m < elList.length; m++) {
					var inputEl = elList.item(m);
					var id = inputEl.id;
					if(odin.ext.getCmp(id+"_combo")){
						odin.setSelectValue(id,"");
					}
					inputEl.value = "";
					inputEl.setAttribute("value", "");
				}
			}
			if(gridDivs != null){
				var divList =document.getElementById(divId).getElementsByTagName("div");
				for (var j = 0; j < divList.length; j++) {
					var cdivId = divList.item(j).id;
					if (cdivId.indexOf("gridDiv_") == 0) {
						var gid = cdivId.substr(cdivId.indexOf("_")+1);
						var grid = odin.ext.getCmp(gid);
						var store = grid.store;
						var count = store.getCount();
						for(var t=0;t<count;t++){ 
							store.remove(store.getAt(0));
						}
					}
				}
			}
		}
	}
};
Array.prototype.uniqueEx=function(){
	var l=this.length;
	var arr=[];
	var tmp={}
	for(var i=0;i<l;i++){
		if(!tmp[this[i]]){
	  		arr.push(this[i]);
	   		tmp[this[i]]=1;
	  	}
	}
	return arr;
};

