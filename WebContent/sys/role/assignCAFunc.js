var tree;
/**
 * ���ع�����
 */
var timeObj = null;
function onLoad(){
	olddate = new Date();
	odin.ext.get(document.body).mask(odin.msg, odin.msgCls);
    tree = new dhtmlXTreeObject("treeBox", "100%", "100%", 0);
	tree.setImagePath(contextPath+"/basejs/tree/imgs/csh_bluebooks/");
    tree.enableCheckBoxes(1);
	tree.enableThreeStateCheckboxes(false);
	tree.loadXML(contextPath + "/sys/roleAction.do?method=getCAFuncXmlInfo");
	timeObj = window.setInterval("testIsLoadComplete()",50);
}
function testIsLoadComplete(){
	if(tree.getItemText('S000000')=="ҵ��˵�"){
		odin.ext.get(document.body).unmask();
		if(timeObj!=null){
			window.clearInterval(timeObj);
			timeObj = null;
		}
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
}

/**
 * ѡ��ù��ܺ��һ����Ȩ�ύ
 */
function submit(){
	var fm =  document.roleForm;
	fm.method.value = "saveCAFuncs";
	if (isOnceLoad == "1") {
		var list=tree.getAllCheckedBranches();//��ȡ����ѡ�кʹ��ڰ�ѡ״̬��id
		var ids;
		if (list.substr(list.length-1,1)==',')
			ids = list.substr(0,list.length-1);
		else
			ids = list;
		fm.funcids.value = ids;
	}
	//alert(fm.funcids.value);	
	parent.odin.ext.getCmp('funcCAWindow').hide();
	odin.submit(fm,saveSuccess,saveSuccess);
}
/**
 * ����ɹ���ʾ
 * @param {Object} response
 */
function saveSuccess(response){
	parent.odin.alert(response.mainMessage);
}
