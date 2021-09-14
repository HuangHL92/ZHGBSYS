<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>

<style>
.x-panel-bwrap,.x-panel-body {
	height: 100%
}

.picOrg {
	background-image:
		url("<%=request.getContextPath()%>/pages/sysorg/org/images/companyOrgImg2.png")
		!important;
}

.picInnerOrg {
	background-image:
		url("<%=request.getContextPath()%>/pages/sysorg/org/images/insideOrgImg1.png")
		!important;
}

.picGroupOrg {
	background-image:
		url("<%=request.getContextPath()%>/pages/sysorg/org/images/groupOrgImg1.png")
		!important;
}

</style>
<script type="text/javascript" src="commform/basejs/json2.js"></script>
<script type="text/javascript">


	var continueCount = 0;//连续选择计数
	var changeNode = "";
	var childNodes = "";
	var continueOne;//连续选择传入第一个对象
	var top = "";//
	var tag = 0;
	var nocheck = 1;
	function existsChoose() {
		var existsCheckbox = document.getElementById('existsCheckbox');
		var continueCheckbox = document.getElementById('continueCheckbox');
		if (existsCheckbox.checked == false) {
			existsCheckbox.checked = false;
		} else {
			existsCheckbox.checked = true;
			continueCheckbox.checked = false;
		}
	}
	function continueChoose() {
		var existsCheckbox = document.getElementById('existsCheckbox');
		var continueCheckbox = document.getElementById('continueCheckbox');
		if (continueCheckbox.checked == false) {
			continueCheckbox.checked = false;
		} else {
			continueCount = 0;
			tag = 0;
			continueCheckbox.checked = true;
			existsCheckbox.checked = false;
		}
	}
	function continueCheck(one, two) {
		//判断选择第二次的方向，如果朝上，执行朝上方法，否则朝下执行
		//判断是否有上级节点，上级节点往下找，如果没有上级节点返回false
		upOrDown(one, two);
		if (tag == 1) {
			if (continueCheckDownLoop(one, two.id) == 1) {
				two.attributes.tag="2";
				return 1;
			}
		} else {
			if (continueCheckDownLoop(two, one.id) == 1) {
				one.attributes.tag="2";
				return 1;
			}
		}
	}

	function continueCheckDownLoop(one, two) {
		one.attributes.tag="1";
		if (one.id == two) {
			return 1;
		} else {
			if (!one.ui.checkbox.checked) {
				one.ui.checkbox.checked = true;
				changeNode += one.id+":"+one.ui.checkbox.checked+":1,";	
			}
		}
		var node = one;
		//一直往下找
		if (node.childNodes.length > 0) {
			for (var i = 0; i < node.childNodes.length; i++) {
				if (continueCheckDownLoop(node.childNodes[i], two) == 1) {
					return 1;
				}
			}
		} else {
			//平级查找
			nocheck = 0;
			if (continueCheckSameLoop(one, two) == 1) {
				return 1;
			}
		}
	}

	function continueCheckSameLoop(one, two) {
		one.attributes.tag="1";
		if (one.id == two) {
			return 1;
		} else {
			if (!one.ui.checkbox.checked && nocheck == 1) {
				one.ui.checkbox.checked = true;
				changeNode += one.id+":"+one.ui.checkbox.checked+":1,";	
			}
		}
		var node = one;
		if (node.parentNode.childNodes.length > 0) {
			for (var i = 0; i < node.parentNode.childNodes.length; i++) {
				if (node.parentNode.childNodes[i].id == one.id) {
					nocheck = 1;
					//如果没有平级的下一个node，那么我们找上级
					if (i + 1 < node.parentNode.childNodes.length) {
						//找下级如果找不到找平级
						if (continueCheckDownLoop(
								node.parentNode.childNodes[i + 1], two) == 1) {
							return 1;
						} else {
							if (continueCheckSameLoop(
									node.parentNode.childNodes[i + 1], two) == 1) {
								return 1;
							}
						}
					} else {
						//找上级
						nocheck = 0;
						if (continueCheckUpLoop(one, two)) {
							return 1;
						}
					}
				}
			}
		}
	}

	function getValue() {
		var URLParams = new Array();
		var aParams = document.location.search.substr(1).split('&');
		for (i = 0; i < aParams.length; i++) {
			var aParam = aParams[i].split('=');
			URLParams[aParam[0]] = aParam[1];
		}
		return URLParams["roleid"];
	}
	Ext.tree.TreeCheckNodeUI = function() {
		//'multiple':多选; 'single':单选; 'cascade':级联多选 
		this.checkModel = 'multiple';

		//only leaf can checked 
		this.onlyLeafCheckable = false;

		Ext.tree.TreeCheckNodeUI.superclass.constructor.apply(this, arguments);
	};

	var nodeSelectedSet = {};

	function loopRoot(rootnode){//[节点对象，是否包含下级，是否选中本身]
		  for(var i =0;i<rootnode.childNodes.length ;i++){
			  var cNode = rootnode.childNodes[i];
			  if(cNode.ui.checkbox.checked){
				  nodeSelectedSet[cNode.id]=[cNode,true,true];
			  }else{
				  loopParent(cNode);//本级若有一个未被选中，上级都改成不包含下级
			  }
			  if(cNode.childNodes.length>0){
				  loopRoot(cNode);
			  }else{
				  if(cNode.attributes.tag==1&&!cNode.ui.checkbox.checked){//没有手动展开下级机构， 但包含下级     本身没被选中
					  if(cNode.isLeaf()){//是叶子节点
						  //nodeSelectedSet[cNode.id]=[cNode,false,false];//[节点对象，不包含下级，本身没被选中]
					  }else{//不是叶子节点
						  nodeSelectedSet[cNode.id]=[cNode,true,false];//[节点对象，包含下级，本身没被选中]
					  }
				  }else if(cNode.attributes.tag!=1&&cNode.ui.checkbox.checked){//不包含下级，没有展开下级机构，但本身被选中
					  if(cNode.isLeaf()){//是叶子节点
						  
					  }else{//不是叶子节点
						  loopParent(cNode);
					  }
					  nodeSelectedSet[cNode.id]=[cNode,false,true];
					  
				  }else if(cNode.attributes.tag==1&&cNode.ui.checkbox.checked&&cNode.isLeaf()){//叶子节点  包含下级，当作不包含下级。
					  nodeSelectedSet[cNode.id]=[cNode,false,true];
				  }
			  }
		  }
		  
	}
	
	//父级节点设置不包含下级。
	function loopParent(cNode){
		if(cNode.parentNode){
			if(nodeSelectedSet[cNode.parentNode.id]){
				nodeSelectedSet[cNode.parentNode.id][1]=false;
			}
			loopParent(cNode.parentNode);
		}
	}

	var oldSelectIdArrayCount = 0;
	var oldSelectIdArray = new Array();
	var count = 0;//计数器
	function doQueryNext() {
		var nextProperty = document.getElementById('nextProperty').value;
		if (nextProperty == "") {
			return;
		}
		var tree = Ext.getCmp("group");
		var node = tree.getRootNode();
		oldSelectIdArray.length = 0;//清楚
		loopNext(node, nextProperty);
		oldSelectIdArray[count % oldSelectIdArray.length].select();
		count += 1;
	}

	function loopNext(node, nextProperty) {
		if (node.childNodes.length > 0) {
			for (var i = 0; i < node.childNodes.length; i++) {
				if (node.childNodes[i].text.indexOf(nextProperty) >= 0) {
					oldSelectIdArray.push(node.childNodes[i]);
					loopNext(node.childNodes[i], nextProperty);
				} else {
					loopNext(node.childNodes[i], nextProperty);
				}
			}
		}
	}

	function grantTabChange(tabObj, item) {
		if (item.getId() == 'tab2') {
			odin.ext.getCmp('resourcegrid').view.refresh(true);
		}
	}

	function returnWin(returnValue) {
		if (window.opener) { //解决Chrome浏览器的兼容问题
			window.opener.Sure(returnValue);
		} else {
			window.returnValue = returnValue; //返回值
		}
		window.close();
	}

	function OnInput(e) {
		oldSelectIdArray.length = 0;
		loopNextTag = 0;
		oldSelectCount = 0;
		oldSelectId = "1";
		count = 0;
	}
	
	
	function tishi() {
		radow.doEvent('Zhishow');
		parent.exeOtherTab('402888045c960861015c9661add50003','freathh');

		
	}
</script>
<%-- <div id="groupTreeContent1" style="height: 10%"><odin:button text="确定" property="Zhishow" handler="tishi"></odin:button> </div> --%>
<div id="groupTreeContent" style="height: 100%">
	<table width="100%">
		<tr valign="top">
			<td> <odin:editgrid property="peopleInfoGrid" title="用户列表"
					autoFill="false" width="550" height="500" bbarId="pageToolBar"
					pageSize="20">
					<odin:gridJsonDataModel>
						<odin:gridDataCol name="personcheck" />
						<odin:gridDataCol name="a0000" />
						<odin:gridDataCol name="loginname" />
						<odin:gridDataCol name="b0101" isLast="true"/>
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
						<odin:gridRowNumColumn></odin:gridRowNumColumn>
						<odin:gridColumn header="selectall" width="50" editor="checkbox"
							dataIndex="personcheck" edited="true" gridName="persongrid"
							checkBoxClick="getCheckList()"
							checkBoxSelectAllClick="getCheckList()" />
						<odin:gridEditColumn2 dataIndex="a0000" width="110" header="id" hideable="false" editor="text"
							align="center" hidden="true" />
						<odin:gridColumn dataIndex="loginname" width="110" header="用户名"
							align="center" />
						<odin:gridColumn dataIndex="b0101" width="215" header="用户所在机构"
							align="center"edited="false" isLast="true" />
					</odin:gridColumnModel>
					<odin:gridJsonData>
	{
        data:[]
    }
</odin:gridJsonData>
				</odin:editgrid></td>
		</tr>
	</table>
</div>





<script type="text/javascript">

Ext.onReady(function(){
	//alert(122222222222222222);
	$("#ext-gen8").children().after("<input type='button' onclick='tishi();' id='abc' value='确定' style='position:absolute;right:1px;top:1px;background-color:#cddef3; '/>");
});
// var extdiv = document.getElementById("ext-gen8");
 //var divhtml = extdiv.innerHTML;
 
// extdiv.innerHTML = divhtml+"<input type='button' value='按钮'>";
//
/* Ext.onReady(function(){
	var pgrid = Ext.getCmp('peopleInfoGrid');
	
	var bbar = pgrid.getBottomToolbar();
	 bbar.insertButton(11,[
						new Ext.menu.Separator({cls:'xtb-sep'}),
						new Ext.Spacer({width:100}),
						new Ext.Button({
							icon : 'images/icon/save.gif',
							id:'Zhishow',
						    text:'确定',
						    handler:function(){
								radow.doEvent('tishi');
						    }
						}),
						
						]); 

}); */

	function defChecked(ids){
	//odin.ext.getCmp(gridId).store.getAt(rowIndex).set(colName, true);
}

	function lict(){
	document.getElementById('checkList').value = '';
	}
    function getPersonId(){
    	var personId = document.getElementById('checkList').value;
    	return personId;
    } 
     
	function getCheckList() {
		radow.doEvent('getCheckList');
	}
	
	function getPersonIdForDj(){
    	var personId = document.getElementById('a0000').value;
    	return personId;
	}
	
	function getPersonNameForDj(){
    	var personName = document.getElementById('a0101').value;
    	return personName;
	}
	
	
	//标准名册
	function downLoadTmp() {
		document.getElementById('tabname').value = '标准名册';
		var a0000 = '';
		var a0101 = '';
		var filename = '';
		var j = 0;
		var gridId = "peopleInfoGrid";
		if (!Ext.getCmp(gridId)) {
			odin.error("要展示的grid不存在！gridId=" + gridId);
			return;
		}
		var grid = Ext.getCmp(gridId);
		var store = grid.getStore();
		if (store.getCount() == 0) {
			odin.error("没有要展示的数据！");
			return;
		}

		var dwbm = document.getElementById('a0201b').value;
		var list = document.getElementById("checkList").value;
		var checkList = 1;
		if (typeof (list) != 'undefined' && list != '')
			doOpenPupWin(encodeURI(encodeURI(contextPath
					+ "/pages/exportexcel/ExpWin1.jsp?dwbm=" + dwbm
					+ "&checkList=" + checkList
					+ "&tmpType=['5', '标准名册']")), "标准文件", 450,
					210);
		else
///			alert("没有选择任何人员不能导出！");
			var r=confirm("您确定要展示当前所有人员吗？");
			if (r==true){
				radow.doEvent("chooseall");
				doOpenPupWin(encodeURI(encodeURI(contextPath
					+ "/pages/exportexcel/ExpWin1.jsp?dwbm=" + dwbm
					+ "&checkList=" + checkList
					+ "&tmpType=['5', '标准名册']")), "标准文件", 450,
					210);
				
			}
			

	}
	
	
	//标准自定义
	function downLoadZdyTmp() {
		document.getElementById('tabname').value='自定义标准名册';
		radow.doEvent("chooseout");
	}
	function zdy(){
		var zdys = document.getElementById('ddd').value;
		////alert(cfa);
		var a0000 = '';
		var a0101 = '';
		var filename = '';
		var j = 0;
		var gridId = "peopleInfoGrid";
		if (!Ext.getCmp(gridId)) {
			odin.error("要展示的grid不存在！gridId=" + gridId);
			return;
		}
		var grid = Ext.getCmp(gridId);
		var store = grid.getStore();
		if (store.getCount() == 0) {
			odin.error("没有要展示的数据！");
			return;
		}
		
		var dwbm = document.getElementById('a0201b').value;
		var list = document.getElementById("checkList").value;
		var checkList = 3;
		
		if (typeof (list) != 'u <input type="button" style="cursor:hand;"  onclick="formSubmit()" value="下载文件">ndefined' && list != '')
			doOpenPupWin(encodeURI(encodeURI(contextPath
					+ "/pages/exportexcel/ExpWin1.jsp?dwbm=" + dwbm
					+ "&checkList=" + checkList
					+ "&tmpType="+zdys)), "自定义文件", 450,
					210);
		else
///			alert("没有选择任何人员不能导出！");
			var r=confirm("您确定要展示当前所有人员吗？");
			if (r==true){
				radow.doEvent("chooseall");
				doOpenPupWin(encodeURI(encodeURI(contextPath
					+ "/pages/exportexcel/ExpWin1.jsp?dwbm=" + dwbm
					+ "&checkList=" + checkList
					+ "&tmpType="+zdys)), "自定义文件", 450,
					210);
				
			}
	}
	
	function downLoadTmp2() {
		document.getElementById('tabname').value='照片名册';
		var a0000 = '';
		var a0101 = '';
		var filename = '';
		var j = 0;
		var gridId = "peopleInfoGrid";
		if (!Ext.getCmp(gridId)) {
			odin.error("要展示的grid不存在！gridId=" + gridId);
			return;
		}
		
		var grid = Ext.getCmp(gridId);
		var store = grid.getStore();
		if (store.getCount() == 0) {
			odin.error("没有要展示的数据！");
			return;
		}

		var dwbm = document.getElementById('a0201b').value;
		var list = document.getElementById("checkList").value;
		var checkList = 2;
		if (typeof (list) != 'undefined' && list != '')
			doOpenPupWin(encodeURI(encodeURI(contextPath
					+ "/pages/exportexcel/ExpWin2.jsp?dwbm=" + dwbm
					+ "&checkList=" + checkList
					+ "&tmpType=['6', '照片名册(每行4人)'],['8','照片名册(每行1人)']")), "下载文件", 500,
					210);
		else
///			alert("没有选择任何人员不能导出！");
			var r=confirm("您确定要展示当前所有人员吗？");
			if (r==true){
				radow.doEvent("chooseall");
				doOpenPupWin(encodeURI(encodeURI(contextPath
					+ "/pages/exportexcel/ExpWin2.jsp?dwbm=" + dwbm
					+ "&checkList=" + checkList
					+ "&tmpType=['6', '照片名册(每行4人)'],['8','照片名册(每行1人)']")), "下载文件", 500,
					210);
				
			}

	}
	
	function downLoadTmp4() {
		document.getElementById('tabname').value='自定义照片名册';
		radow.doEvent('zpzdy');
	}
	function zpzdy(){
		var zdys = document.getElementById('ddd').value;
		var a0000 = '';
		var a0101 = '';
		var filename = '';
		var j = 0;
     	var gridId = "peopleInfoGrid";
		if (!Ext.getCmp(gridId)) {
			odin.error("要展示的grid不存在！gridId=" + gridId);
			return;
		}
		var grid = Ext.getCmp(gridId);
		var store = grid.getStore();
		if (store.getCount() == 0) {
			odin.error("没有要展示的数据！");
			return;
		}
		
		var dwbm = document.getElementById('a0201b').value;
		var list = document.getElementById("checkList").value;
		var checkList = 4;
		if (typeof (list) != 'u <input type="button" style="cursor:hand;"  onclick="formSubmit()" value="下载文件">ndefined' && list != '')
			doOpenPupWin(encodeURI(encodeURI(contextPath
					+ "/pages/exportexcel/ExpWin2.jsp?dwbm=" + dwbm
					+ "&checkList=" + checkList
					+ "&tmpType="+zdys)), "自定义文件", 450,
					210);
		else
///			alert("没有选择任何人员不能导出！");
			var r=confirm("您确定要展示当前所有人员吗？");
			if (r==true){
				radow.doEvent("chooseall");
				doOpenPupWin(encodeURI(encodeURI(contextPath
					+ "/pages/exportexcel/ExpWin2.jsp?dwbm=" + dwbm
					+ "&checkList=" + checkList
					+ "&tmpType="+zdys)), "自定义文件", 450,
					210);
				
			}			
		
	}
	
	/**
	 * 导出登记表册
	 * @param {} gridId
	 * @param {} fileName
	 * @param {} sheetName
	 * @param {} headNames
	 * @param {} isFromInterface
	 */
	function expExcelTemp() {
		var a0000 = '';
		var a0101 = '';
		var filename = '';
		var j = 0;
		var gridId = "peopleInfoGrid";
		if (!Ext.getCmp(gridId)) {
			odin.error("要导出的grid不存在！gridId=" + gridId);
			return;
		}
		var grid = Ext.getCmp(gridId);
		var store = grid.getStore();
		if (store.getCount() == 0) {
			odin.error("没有要导出的数据！");
			return;
		}

		var sm = grid.getSelectionModel();
		var selections = sm.getSelections();
		for (var i = 0; i < store.data.length; i++) {
			var selected = store.getAt(i);
			var record = selected.data;
			if (record.personcheck) {
				a0000 = a0000 + record.a0000 + ',';
				a0101 = a0101 + record.a0101 + ',';
				j++;
			}
		}
		if (a0000 == '') {
			odin.error("请选中要导出的行！");
			return;
		}
		if(j>3){
			for(var i = 0; i < 3; i++){
				var selected = store.getAt(i);
				var record = selected.data;
				if (record.personcheck) {
					filename = filename + record.a0101 + ',';
				}
			}
			filename = filename.substring(0, filename.length - 1)+'等';
		}else{
			for(var i = 0; i < j; i++){
				var selected = store.getAt(i);
				var record = selected.data;
				if (record.personcheck) {
					filename = filename + record.a0101 + ',';
				}
			}
			filename = filename.substring(0, filename.length - 1);
		}
		a0101 = a0101.substring(0, a0101.length - 1);
		a0000 = a0000.substring(0, a0000.length - 1);
		document.getElementById('a0000').value = a0000;
		document.getElementById('a0101').value = a0101;
		doOpenPupWin(encodeURI(encodeURI(contextPath
				+ "/pages/exportexcel/djbLoad.jsp?download=true&filename="+filename)), "下载文件", 600, 200);
	}
	 
	function ml(a0000,allName){
		document.getElementById('a0000').value = a0000;
		document.getElementById('a0101').value = allName;
		doOpenPupWin(encodeURI(encodeURI(contextPath
				+ "/pages/exportexcel/ExpTempDjbWindow.jsp")), "下载文件", 600, 520);
	}
	
	//登记表编辑附件
	function editFile(){
		
		var value = getValue();
		var values = value.split("@");
		
		if (values[0] == '') {
			alert("请选中要上传附件的人员记录！");
			return;
		}
		if(values[3] > 1){
			alert("只能选择一条人员记录！");
			return;
		}
		var name = values[1]+"@"+values[2];
		
		var win = odin.ext.getCmp('simpleExpWin');
		win.setTitle('导入窗口');
		odin.showWindowWithSrc('simpleExpWin', contextPath
				+ "/pages/search/EditFile.jsp?flag=1&uuid="+values[0]+"&uname="+name);
		
	}
	
	//查看/删除附件
	function modifyFile(){
		
		var value = getValue();
		var values = value.split("@");
		
		if (values[0] == '') {
			alert("请选择人员记录！");
			return;
		}
		if(values[3] > 1){
			alert("只能选择一条人员记录！");
			return;
		}
		
		radow.doEvent("modifyAttach",values[0]+"@1");
		
	}
	
	//获取页面数据
	function getValue(){
		
		var gridId = "peopleInfoGrid";
		var grid = Ext.getCmp(gridId);
		var store = grid.store;
		var count = 0;
		var a0000 ='';//人员编号
		var a0101 = '';//人员姓名
		var a0184 = '';//人员身份证号
		for (var i = 0; i < store.data.length; i++) {
			var selected = store.getAt(i);
			var record = selected.data;
			if (record.personcheck) {
				a0000 = record.a0000;
				a0101 = record.a0101;
				a0184 = record.a0184;
				count=count+1;
			}
		}
		return a0000+"@"+a0101+"@"+a0184+"@"+count;
	}
	
	/**
	 * 导出登记表册
	 * @param {} gridId
	 * @param {} fileName
	 * @param {} sheetName
	 * @param {} headNames
	 * @param {} isFromInterface
	 */
	function createExcelTemp() {
		var a0000 = '';
		var a0101 = '';
		var gridId = "peopleInfoGrid";
		if (!Ext.getCmp(gridId)) {
			odin.error("要导出的grid不存在！gridId=" + gridId);
			return;
		}
		var grid = Ext.getCmp(gridId);
		var store = grid.getStore();
		if (store.getCount() == 0) {
			odin.error("没有要导出的数据！");
			return;
		}

		var sm = grid.getSelectionModel();
		var selections = sm.getSelections();
		var store = grid.store;
		for (var i = 0; i < store.data.length; i++) {
			var selected = store.getAt(i);
			var record = selected.data;
			if (record.personcheck) {
				a0000 = a0000 + record.a0000 + ',';
				a0101 = a0101 + record.a0101 + ',';
			}
		}
		if (a0000 == '') {
			odin.error("请选中要导出的行！");
			return;
		}
		
		a0101 = a0101.substring(0, a0101.length - 1);
		a0000 = a0000.substring(0, a0000.length - 1);
		radow.doEvent('checkPer',a0000+"@"+a0101);
		
	}	
	//人员新增修改窗口窗口
	var personTabsId = [];
	function addTab(atitle, aid, src, forced, autoRefresh, param) {
		var tab = parent.tabs.getItem(aid);
		if (forced)
			aid = 'R' + (Math.random() * Math.random() * 100000000);
		if (tab && !forced) {
			parent.tabs.activate(tab);
			if (typeof autoRefresh != 'undefined' && autoRefresh) {
				document.getElementById('I' + aid).src = src;
			}
		} else {
			personTabsId.push(aid);
			parent.tabs
					.add(
							{
								title : (atitle),
								id : aid,
								tabid : aid,
								personid : aid,
								html : '<Iframe width="100%" height="100%" scrolling="auto" id="I'
										+ aid
										+ '" frameborder="0" src="'
										+ src
										+ '&a0000='+aid+'" style="clear:both;margin-left:0px;margin-right:0px;float:right;"></Iframe>',
								listeners : {//判断页面是否更改，

								},
								closable : true
							}).show();

		}
	}

function Ztrue(){
	alert(111);
}
function UpBtn(){
	alert(111);
}
function DownBtn(){
	alert(111);
}
function setPageSize1(){
	alert(111);
}


	Ext.onReady(function() {
		//页面调整
		 Ext.getCmp('peopleInfoGrid').setHeight(Ext.getBody().getViewSize().height-objTop(document.getElementById('forView_peopleInfoGrid'))[0]-4);
		 Ext.getCmp('peopleInfoGrid').setWidth((Ext.isIE?Ext.getBody().getViewSize().width:window.innerWidth)-objTop(document.getElementById('forView_peopleInfoGrid'))[1]-2); 
		 document.getElementById('groupTreePanel').style.width = document.body.clientWidth;
		 //Ext.getCmp('btnToolBar').setWidth(document.body.clientWidth);
		 document.getElementById("tree-div").style.height = Ext.getCmp('peopleInfoGrid').getHeight()-63;
		
					
		 
	});
	function objTop(obj){
	    var tt = obj.offsetTop;
	    var ll = obj.offsetLeft;
	    while(true){
	    	if(obj.offsetParent){
	    		obj = obj.offsetParent;
	    		tt+=obj.offsetTop;
	    		ll+=obj.offsetLeft;
	    	}else{
	    		return [tt,ll];
	    	}
		}
	    return tt;  
	}
	
</script>
<odin:window src="/blank.htm" id="simpleExpWin" width="560" height="350" maximizable="false" title="上传附件窗口"></odin:window>
<odin:window src="/blank.htm" id="modifyFileWindow" width="560" height="300" maximizable="false" title="查看/删除附件窗口"></odin:window>
<odin:window src="/blank.htm" id="othertem" width="300" height="200" maximizable="false" title="选择表格模板"></odin:window>
<odin:hidden property="ddd"/>


