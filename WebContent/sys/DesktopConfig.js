function loadConfigGridData(){
	var store = odin.ext.getCmp('deskConfigGrid').store;
	var data = parent.dt.items;
	var len = data.length;
	var selfOrder = parent.dt.selfOrder;
	for(var i=0;i<len;i++){
		var temp = {};
		temp.id = data[i].id;
		temp.title = data[i].title;
		temp.name = data[i].name;
		temp.orderno = data[i].orderno;
		if(typeof  selfOrder!='undefined' && selfOrder!=null){
			var selfDefValue = eval("selfOrder."+data[i].name);
			if(typeof selfDefValue == 'undefined'){
				temp.isshow = false;
			}else{
				temp.isshow = true;
			}
		}else{
			temp.isshow = false;
		}
		temp.isshowback = temp.isshow;
		store.add(new odin.ext.data.Record(temp));
	}
}

function saveConfig(){
	var store = odin.ext.getCmp('deskConfigGrid').store;
	var len = store.getCount();
	var isSave = false;
	for(var i=0;i<len;i++){
		var data = store.getAt(i);
		var isshow = data.get('isshow');
		if(isshow !=data.get('isshowback')){
			isSave = true;
			if(parent.dt.selfOrder==null){
				parent.dt.selfOrder = {};
			}
			if(isshow){
				eval("parent.dt.selfOrder."+data.get("name")+"="+data.get('orderno'));
			}else{
				eval("delete parent.dt.selfOrder."+data.get("name"));
			}
		}
	}
	parent.odin.ext.getCmp('deskConWin').hide();
	if(isSave){
		parent.dt.saveSelfOrder();
		parent.location.reload();
	}	
}