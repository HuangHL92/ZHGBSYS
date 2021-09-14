var tree;
/**
 * 加载功能树
 */
var timeObj = null;
var olddate = null;
function onLoad(){
	olddate = new Date();
	odin.ext.get(document.body).mask(odin.msg, odin.msgCls);
    tree = new dhtmlXTreeObject("treeBox", "100%", "100%", 0);
	tree.setImagePath(contextPath+"/basejs/tree/imgs/csh_bluebooks/");
    tree.enableCheckBoxes(1);
	if (isOnceLoad == "1") {
		tree.enableThreeStateCheckboxes(true);
		tree.loadXML(contextPath + "/sys/roleAction.do?method=getAllFuncXmlInfo&roleid=" +
		parent.cueRoleId +
		"&sysid=" +
		parent.sysId);
	}
	else {
		tree.attachEvent("onCheck",checkTreeItem);
		tree.enableThreeStateCheckboxes(false);
		tree.setXMLAutoLoading(contextPath + "/sys/roleAction.do?method=getFunctionXmlInfo&roleid=" +
		parent.cueRoleId +
		"&sysid=" +
		parent.sysId);
		tree.loadXML(contextPath + "/sys/roleAction.do?method=getFunctionXmlInfo&id=0&roleid=" +
		parent.cueRoleId +
		"&sysid=" +
		parent.sysId);
	}
	timeObj = window.setInterval("testIsLoadComplete()",50);
}
function testIsLoadComplete(){
	if(tree.getItemText('S000000')=="业务菜单"){
		odin.ext.get(document.body).unmask();
		if(timeObj!=null){
			window.clearInterval(timeObj);
			timeObj = null;
		}
		//alert(new Date().getElapsed(olddate));
	}
}
function checkTreeItem(id,state){
	var funObj = document.roleForm.funcids;
	if(state==0){
		if(funObj.value.indexOf(id)==0 && funObj.value.length==id.length){
			funObj.value = funObj.value.replace(id,'');
		}else if(funObj.value.indexOf(id)==0 && funObj.value.length>id.length){
			funObj.value = funObj.value.replace(id+",",'');
		}else{
			funObj.value = funObj.value.replace(","+id,'');
		}
	}else if(state==1){
		if(funObj.value==""){
			funObj.value = id;
		}else{
			funObj.value += ","+id;
		}
	}
	//alert(id);
	//alert(funObj.value);
}
function getRoleFuncIds(){
	if (isOnceLoad != "1") {
		var params = {};
		params.method = "getRoleFuncIds";
		params.roleid = parent.cueRoleId;
		var url = contextPath + "/sys/roleAction.do";
		odin.Ajax.request(url, params, getRFIdsSuccess, null, false, false);
	}
}
function getRFIdsSuccess(response){
	document.roleForm.cueRoleFuncs.value = response.data;
	document.roleForm.funcids.value = response.data;
}
/**
 * 选择好功能后的一个授权提交
 */
function submit(){
	//var list=tree.getAllCheckedBranches();//获取所有选中和处于半选状态的id
	//var ids = list.substr(0,list.length-1);
	//alert(ids);	
	var fm =  document.roleForm;
	fm.method.value = "saveRoleFuncs";
	//fm.funcids.value = ids;
	if (isOnceLoad == "1") {
		var list=tree.getAllCheckedBranches();//获取所有选中和处于半选状态的id
		var ids;
		if (list.substr(list.length-1,1)==',')
			ids = list.substr(0,list.length-1);
		else
			ids = list;
		fm.funcids.value = ids;
	}	
	fm.roleid.value = parent.cueRoleId;
	//alert(parent.cueRoleId);
	parent.odin.ext.getCmp('funcWindow').hide();
	odin.submit(fm,saveSuccess,saveSuccess);
}
/**
 * 保存成功提示
 * @param {Object} response
 */
function saveSuccess(response){
	parent.odin.alert(response.mainMessage);
}
