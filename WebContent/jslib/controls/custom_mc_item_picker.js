var CustomMcItemPicker = {
	// 移动标准项
	moveStandardItems: function(fromObj, toObj) {
		var fromObjOptions=fromObj.options; 
		for(var i=0;i<fromObjOptions.length;i++){ 
			var itemType = fromObjOptions[i].getAttribute("itemtype");  
			if(itemType == '1'){ 
				toObj.appendChild(fromObjOptions[i]); 
				i--; 
			} 
		} 
	},
	
	
	//选中项向左移动或向右移动 
	moveLeftOrRight: function(fromObj,toObj){ 
		var fromObjOptions=fromObj.options; 
		for(var i=0;i<fromObjOptions.length;i++){ 
			if(fromObjOptions[i].selected){ 
				toObj.appendChild(fromObjOptions[i]); 
				i--; 
			} 
		} 
	},
	
	//左边全部右移动，或右边全部左移 
	moveLeftOrRightAll: function(fromObj,toObj){ 
		var fromObjOptions=fromObj.options; 
		for(var i=0;i<fromObjOptions.length;i++){ 
			fromObjOptions[0].selected=true; 
			toObj.appendChild(fromObjOptions[i]); 
			i--; 
		} 
	},
	
	
	//向上移动 
	moveUp: function (selectObj){ 
		var theObjOptions=selectObj.options; 
		for(var i=1;i<theObjOptions.length;i++) { 
			if( theObjOptions[i].selected && !theObjOptions[i-1].selected ) { 
				CustomMcItemPicker.swapOptionProperties(theObjOptions[i],theObjOptions[i-1]); 
			} 
		} 
	},
	
	//向下移动 
	moveDown: function (selectObj){ 
		var theObjOptions=selectObj.options; 
		for(var i=theObjOptions.length-2;i>-1;i--) { 
			if( theObjOptions[i].selected && !theObjOptions[i+1].selected ) { 
				CustomMcItemPicker.swapOptionProperties(theObjOptions[i],theObjOptions[i+1]); 
			} 
		} 
	},
	
	//移动至最顶端 
	moveToTop: function (selectObj){ 
		var theObjOptions=selectObj.options; 
		var oOption=null; 
		for(var i=0;i<theObjOptions.length;i++) { 
			if( theObjOptions[i].selected && oOption) { 
				selectObj.insertBefore(theObjOptions[i],oOption); 
			} 
			else if(!oOption && !theObjOptions[i].selected) { 
				oOption=theObjOptions[i]; 
			} 
		} 
	},
	
	//移动至最低端 
	moveToBottom: function (selectObj){ 
		var theObjOptions=selectObj.options; 
		var oOption=null; 
		for(var i=theObjOptions.length-1;i>-1;i--) { 
			if( theObjOptions[i].selected ) { 
				if(oOption) { 
					oOption=selectObj.insertBefore(theObjOptions[i],oOption); 
				} 
				else oOption=selectObj.appendChild(theObjOptions[i]); 
			} 
		} 
	},
	
	//全部选中 
	selectAllOption: function (selectObj){ 
		var theObjOptions=selectObj.options; 
		for(var i=0;i<theObjOptions.length;i++){ 
			theObjOptions[0].selected=true; 
		} 
	},

	/* private function */ 
	swapOptionProperties: function (option1,option2){ 
		//option1.swapNode(option2); 
		var tempStr=option1.value; 
		option1.value=option2.value; 
		option2.value=tempStr;

		var tempValSource=option1.valSource;// 
		option1.valSource=option2.valSource;// 
		option2.valSource=tempValSource;//

		tempStr=option1.text; 
		option1.text=option2.text; 
		option2.text=tempStr; 
		tempStr=option1.selected; 
		option1.selected=option2.selected; 
		option2.selected=tempStr; 
	},
	
	getSelectedOptions: function (){ 
		var ObjSelect = document.frmMcItem.destItem; 
		var itemField=""; 
		var itemName=""; 
		if(ObjSelect&&ObjSelect.options&&ObjSelect.options.length>0){ 
			var len = ObjSelect.options.length; 
			for(var j=0; j<len; j++){ 
				itemField += ObjSelect.options[j].value + ","; 
				itemName += ObjSelect.options[j].text + ","; 
			} 
		} 
		
		return itemField;
	}
};