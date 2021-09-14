<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.SysOrgTreePageModel"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>


<html class="ext-strict x-viewport">
<script src="<%=request.getContextPath()%>/pages/xbrm2/js/jquery-1.4.4.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<%@include file="/comOpenWinInit2.jsp" %>
<style>
.x-panel-bwrap {
	height: 100%
}

.x-panel-body {
	height: 100%
}
.busy{
	height: 450px;

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
.x-grid3-scroller{
overflow-y: scroll;
}
</style>
<% 		String type=(String)request.getParameter("PersonType");
		
 %>
<script type="text/javascript" src="commform/basejs/json2.js"></script>

<odin:hidden property="ynId" />
<odin:hidden property="PersonType" value="<%=type%>"/>
<odin:hidden property="yntype" />
<odin:hidden property="checkedgroupid" />
<odin:hidden property="cueRowIndex" />
<odin:hidden property="codevalueparameter" />
<odin:hidden property="sql" />
<odin:hidden property="mark" /> <!--  是否搜索刷新grid的标记 -->

<odin:hidden property="appointment" />
<odin:hidden property="selectByInputYnIdHidden" />

<odin:hidden property="bz00" />
<odin:hidden property="sql2" />

<odin:hidden property="selectType" />

<odin:hidden property="selectUnitId" />

<div id="groupTreeContent" style="height: 92%;width: 100%;">
	<table style="height: 95%;">
		<tr valign="top">
			<td width="20%" height="100%" rowspan="0" colspan="0">
				<div style="height: 5%;background-color: #cedff5;" align="center">
					<input type="checkbox"  id="continueCheckbox" style="display:none" onclick="continueChoose()"><!-- <font style="font-size: 13">连续选择</font> -->
					<input type="checkbox" id="existsCheckbox" checked="checked" onclick="existsChoose()"><font style="font-size: 13">包含下级</font>
				</div>
				<div class="busy">
					<div id="tree-div" style="border: 2px solid #c3daf9;height: 100%; width: 98%;"></div>
				</div>
			</td>  
			<td id="gridcqtd" style="width: 67%;height: 100%;">
				<table style="width: 100%;height: 100%;border: 2px solid #c3daf9;">
					<tr>
						
					</tr>					
					<tr   style="width: 100%;">
						<td colspan="3">
						<table style="height:50px;width:100%">
							<tr>
								<td style="width: 130px;" >
									<div style="font-size: 12px;margin-top: 10px;margin-left: 10px;color: red;">多个姓名/身份证查</br>询，请按逗号隔开。</div>
								</td>
								<td>
									<odin:select property="tpye" label="" canOutSelectList="true" value = '1' data="['1','姓名'],['2','身份证号码']" width="120"></odin:select>
								</td>
								<td>
									<textarea  class="x-form-text x-form-field" style="width: 390px;height:25px;  margin-top:1px;" name="queryName" id="queryName"  ></textarea> 
									
								</td>
								<td> <input id="buttonSearch" type="button" style="position: relative;top: 2px;" onclick="toDOQuery()" value="搜索"></td>
							</tr>
						</table>
						</td>
					</tr>
					<tr style="width: 100%;" align="center">
						<td width="46%" height="100%">
							
							<div id="selectByPersonIdDiv" >
								<odin:editgrid property="gridcq" title="待选列表"  width="300" height="350" pageSize="9999"
									autoFill="false" >
									<odin:gridJsonDataModel>
										<odin:gridDataCol name="personcheck"  />
										<odin:gridDataCol name="a0000" />
										<odin:gridDataCol name="a0101" />
										<odin:gridDataCol name="a0104" />
										<odin:gridDataCol name="a0163" />
										<odin:gridDataCol name="a0192a" isLast="true"/>
									</odin:gridJsonDataModel>
									<odin:gridColumnModel>
										<odin:gridRowNumColumn></odin:gridRowNumColumn>
										<odin:gridEditColumn2 header="selectall" width="0"
											editor="checkbox" dataIndex="personcheck" edited="true" hidden="true"
											hideable="false" gridName="persongrid" />
										<odin:gridEditColumn header="主键" editor="text" hidden="true" edited="true" dataIndex="a0000" />
										<odin:gridColumn dataIndex="a0101" header="姓名" width="55" align="center" />
										<odin:gridEditColumn2 dataIndex="a0104" header="性别" width="45" align="center" editor="select" edited="false" codeType="GB2261" />
										<odin:gridColumn dataIndex="a0192a" edited="false" header="单位职务" width="100"  align="center" />
										<odin:gridEditColumn2 dataIndex="a0163" header="人员状态" width="65" align="center" editor="select" edited="false" isLast="true" codeType="ZB126" />
									</odin:gridColumnModel>
									<odin:gridJsonData>
										{
									        data:[]
									    }
									</odin:gridJsonData>
								</odin:editgrid>
							</div>

						</td>
						<td style="width: 8%;height: 100%;" align="center">
							<div id='rigthBtn'  style="display: none"></div>
							<br>
							<div id='rigthAllBtn' title="全选"></div>
							<br>
							<div id='liftBtn' style="display: none"></div>
							<br>
							<div id='liftAllBtn' title="全选"></div>
						</td>
						<td width="46%;" height="100%;" align="center">
							<div id="selectByPersonIdDiv2">
								<odin:editgrid property="selectName" title="输出列表" width="300" height="350" autoFill="false" >
									<odin:gridJsonDataModel>
											<odin:gridDataCol name="personcheck2"  />
											<odin:gridDataCol name="a0000" />
											<odin:gridDataCol name="a0101" />
											<odin:gridDataCol name="a0104" />
											<odin:gridDataCol name="a0163" />
											<odin:gridDataCol name="a0192a" isLast="true"/>
										</odin:gridJsonDataModel>
										<odin:gridColumnModel>
											<odin:gridRowNumColumn></odin:gridRowNumColumn>
											<odin:gridEditColumn2 header="selectall" width="0"
																  hidden="true"	editor="checkbox" dataIndex="personcheck2" edited="true"
												hideable="false" gridName="persongrid" />
											<odin:gridEditColumn header="主键" editor="text" hidden="true" edited="true" dataIndex="a0000" />
											<odin:gridColumn dataIndex="a0101" header="姓名" width="55" align="center" />
											<odin:gridEditColumn2 dataIndex="a0104" header="性别" align="center" width="45" editor="select" edited="false" codeType="GB2261" />
											<odin:gridColumn dataIndex="a0192a" edited="false" header="单位职务" width="100" align="center" />
										<odin:gridEditColumn2 dataIndex="a0163" header="人员状态" width="65" align="center" editor="select" edited="false" isLast="true" codeType="ZB126" />
										</odin:gridColumnModel>
										<odin:gridJsonData>
											{
										        data:[]
										    }
										</odin:gridJsonData>
								</odin:editgrid>
							</div>

						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	
</div>
<div align="right" >
	<table>
		<tr>
			<td>
				<input type="button" height="20" width="40" value="清除待选列表" onclick="clearRst()">&nbsp;&nbsp;
			</td>
			<div style="display:none">
	 			<!-- odin:select2  property="tplb"  label="从其它会议中提取（选择后点击确定即可）"  odin:select2  -->
	 		</div>
			<td><input type="button" height="20" width="40" value="&nbsp;确&nbsp;&nbsp;定&nbsp;" onclick="saveSelect()">&nbsp;&nbsp;</td>
		</tr>
	</table>
</div>

<script type="text/javascript">

function resetPiCiSelect(jsonArray){ 
	//selectByInputYnId
	var objSelect = $("#selectByInputYnId");
	$("option", $("#selectByInputYnId")).remove();
	objSelect.append("<option value=''>&nbsp;&nbsp;</option>");
	$(jsonArray).each(function() { 
		objSelect.append("<option value='" + this.code + "'>" + this.name + "</option>");
	});
	
}

function selectByInputYnIdClick(){
	$("#selectByYnIdDiv").css("display","none");
	$("#selectByYnIdDiv2").css("display","none");
	
	$("#selectByPersonIdDiv").css("display","block");  
	$("#selectByPersonIdDiv2").css("display","block"); 
	
	var val = $("#selectByInputYnId").val(); 
	val = val.replace(/\s*/g,""); 
	if (val == ""){
		$("input[name='rdoSwitch']").removeAttr("disabled");
		$("#buttonSearch").removeAttr("disabled"); 
	} else{
		$("#selectByInputYnIdHidden").val(val);
		radow.doEvent('queryFromData'); 
		$("input[name='rdoSwitch']").attr("disabled","true");
		$("#buttonSearch").attr("disabled","true");
	}
}

document.onkeydown=function() { 
	
	if (event.keyCode == 13) { 
		if (document.activeElement.type == "textarea") {
			toDOQuery();
			return false;
		}
	}else if(event.keyCode == 27){	//禁用ESC
	        return false;   
	}
}



Ext.onReady(function() {
	
	/*if(!!parent.Ext.getCmp(subWinId).initialConfig.ynId){
		document.getElementById("ynId").value=parent.Ext.getCmp(subWinId).initialConfig.ynId;
	}
	if(!!parent.Ext.getCmp(subWinId).initialConfig.yntype){
		document.getElementById("yntype").value=parent.Ext.getCmp(subWinId).initialConfig.yntype;
	} */
	document.getElementById("checkedgroupid").value=parent.document.getElementById('checkedgroupid').value;
	/*$("#appointment").val(parent.$("#appointment").val());
	if (parent.$("#appointment").val()=="1"){
		$("#selectByPersonIdBtn").attr("disabled","true");
		$("#selectByYnIdBtn").attr("disabled","true");
	}*/
	var gridcq = Ext.getCmp("gridcq");
	var selectName = Ext.getCmp("selectName");
	var gStore = gridcq.getStore();
	var sStore = selectName.getStore();
    gStore.getModifiedRecords();
	gridcq.on("rowclick",function(o, index, o2){
		var rowData = gStore.getAt(index);
		var count = sStore.getCount();
		var flag = true;
		for(var i=0;i<count;i++){
			record = sStore.getAt(i);
			if(rowData.data.a0000==record.data.a0000){
				flag = false;
				break;
			}
		}
		if(flag){
			sStore.insert(sStore.getCount(),rowData);
		}
		gStore.remove(rowData);
		gridcq.view.refresh();
	});

	selectName.on("rowclick",function(o, index, o2){
		var rowData = sStore.getAt(index);
		var count = gStore.getCount();
		var flag = true;
		for(var i=0;i<count;i++){
			record = gStore.getAt(i);
			if(rowData.data.a0000==record.data.a0000){
				flag = false;
				break;
			}
		}
		if(flag){
			gStore.insert(gStore.getCount(),rowData);
		}

		sStore.remove(rowData);
		selectName.view.refresh();
	});

	//
    // var sm = gridcq.CheckboxSelectionModel();
	//
    // sm.handleMouseDown = Ext.emptyFn;//不响应MouseDown事件
    // sm.on('rowselect',function(sm_,rowIndex,record){//行选中的时候
    //     alert(index);
    //     console.log(index)
    //     rigthBtnFun();
    // }, this);

    // gridcq.on("rowselect",function(o, index, o2){
    //     alert(index);
    //     console.log(index)
    //     rigthBtnFun();
    // });
    // selectName.on("rowselect",function(o, index, o2){
    //     liftBtnFun();
	//
    // });
    // var sm = gridcq.RowSelectionModel({
    //     listeners:{
    //         'rowselect':function(sm,rowIndex,r){
    //             alert(1);
	//
    //             Ext.log("select",rowIndex,r.get('name'))
    //             rigthBtnFun();
    //         }
    //     }
    // });
    // sm.handleMouseDown = Ext.emptyFn;//不响应MouseDown事件
    // sm.on('rowselect', function (sm_, rowIndex, record) {//行选中的时候
    //     alert(1);
    // }, this);
    // sm.on('rowdeselect', function (sm_, rowIndex, record) {//行未选中的时候
    //     alert(2);
    // }, this);
    // gridcq.CheckboxSelectionModel({
    //     listeners: {
    //         'rowselect': function (sm, rowIndex, record) {
    //             console.log('rowselect', rowIndex)
    //         },
    //         'rowdeselect': function (sm, rowIndex, record) {
    //             console.log('rowdeselect', rowIndex)
    //         },
    //         'selectionchange': function (sm) {
    //             console.log('selectionchange', sm.getSelections().length);
    //         }
    //     }
	//
	//
    // });
});



	/***
	  * 根据人员选择
	  *
	  */
	function selectByPersonId(){ 
		$("#selectByYnIdDiv").css("display","none");
		$("#selectByYnIdDiv2").css("display","none");
		
		$("#selectByPersonIdDiv").css("display","block");  
		$("#selectByPersonIdDiv2").css("display","block"); 
		
		$("#selectType").val("0");
		$("#selectByInputYnIdHidden").val(""); //取消按导入批次选择
		$("#buttonSearch").removeAttr("disabled");
	}
	/***
	  * 根据批次选择
	  *
	  */	
	function selectByYnId(){ 
		$("#selectByYnIdDiv").css("display","block"); 
		$("#selectByYnIdDiv2").css("display","block"); 
		$("#selectByPersonIdDiv").css("display","none");
		$("#selectByPersonIdDiv2").css("display","none"); 
		$("#selectType").val("1");
		$("#selectByInputYnIdHidden").val(""); //取消按导入批次选择
		$("#buttonSearch").attr("disabled","true");
		radow.doEvent('queryFromYnId'); 	
	}
	
	/***
	   *   保存人员信息
	 *
	 */
	function saveSelect(){  
		var appointment = $("#appointment").val(); 
		if (appointment == "1") {
			$h.confirm("系统提示：",'确认正式任命后，人员信息会从中间库转入到正式库，是否继续？',200,function(id) {
				if (id == 'ok'){
					radow.doEvent('doAppointment');
				}
			});
		} else { 
		
		var o = doQuery();
		var a = JSON.stringify(o);
			
			radow.doEvent('saveSelect',a);
		}
	}
	function clearSelect(){
		radow.doEvent('clearSelect');
	}
	function clearRst(){
		radow.doEvent('clearRst');
	}
	
	var continueCount = 0;//连续选择计数
	var changeNode = {};//每次操作的记录
	var childNodes = "";
	var continueOne;//连续选择传入第一个对象
	var top = "";//
	var tag = 0;
	var nocheck = 1;

	var nodeSelectedSet = {};

	function existsChoose() {
		var existsCheckbox = document.getElementById('existsCheckbox');
		var continueCheckbox = document.getElementById('continueCheckbox');
		if (existsCheckbox.checked == false) {
			existsCheckbox.checked = false;
		} else {
			existsCheckbox.checked = true;
			continueCheckbox.checked = false;
		}
        toDOQuery();
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
        toDOQuery();

	}
	function rigthBtnFun(){
		radow.doEvent('rigthBtn.onclick');
	}
	function rigthAllBtnFun(){
		radow.doEvent('rigthAllBtn.onclick');
	}
	function liftBtnFun(){
		radow.doEvent('liftBtn.onclick');
	}
	function liftAllBtnFun(){
		radow.doEvent('liftAllBtn.onclick');
	}
	Ext.onReady(function() {
		new Ext.Button({
			icon : 'images/icon/rightOne.png',
			id:'btn1',
		    cls :'inline pl',
		    renderTo:"rigthBtn",
		    handler:function(){
		    	rigthBtnFun();
		    }
		});
		new Ext.Button({
			icon : 'images/icon/rightAll.png',
			id:'btn2',
		    cls :'inline pl',
		    renderTo:"rigthAllBtn",
		    handler:function(){
		    	rigthAllBtnFun();
		    }
		});
		new Ext.Button({
			icon : 'images/icon/leftOne.png',
			id:'btn3',
		    cls :'inline pl',
		    renderTo:"liftBtn",
		    handler:function(){
		    	liftBtnFun();
		    }
		});
		new Ext.Button({
			icon : 'images/icon/leftAll.png',
			id:'btn4',
		    cls :'inline pl',
		    renderTo:"liftAllBtn",
		    handler:function(){
		    	liftAllBtnFun();
		    }
		});		
		
		//根据父页面的结构树加载机构树  2019.12.05 yzk
		//var LWflag=parent.Ext.getCmp(subWinId).initialConfig.LWflag;
		var sign="write";
		/*if(LWflag=="1"){
			sign="look"
		}else{
			sign="write";
		}*/
		
		var Tree = Ext.tree;
				var tree = new Tree.TreePanel(
						{
							id : 'group',
							el : 'tree-div',//目标div容器
							split : false,
							width : 240,
							rootVisible : false,
							autoScroll : true,
							animate : true,
							border : false,
							//height:350,
							enableDD : false,
							containerScroll : true,
							checkModel : 'multiple',
							loader : new Tree.TreeLoader(
									{
										baseAttrs : {
											uiProvider : Ext.tree.TreeCheckNodeUI
										},
										//dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysorg.org.SysOrgTree&eventNames=orgTreeJsonData&tag=1',
										dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysorg.org.PublicWindow&eventNames=orgTreeJsonDataPeople&unsort=1',
										baseParams : {sign: sign}
									//dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysorg.org.SysOrg&eventNames=orgTreeJsonData&tag=1'
									}),
							listeners : {
								'expandnode' : function(node) {
									//获取展开节点的信息
									if (node.attributes.tag == "1") {
										node.attributes.tag = "2";
										for (var i = 0; i < node.childNodes.length; i++) {
											node.childNodes[i].ui.checkbox.checked = true;
											node.childNodes[i].attributes.tag = "1";
										}
									} else if (node.attributes.tag == "0") {
										node.attributes.tag = "2";
										for (var i = 0; i < node.childNodes.length; i++) {
											node.childNodes[i].ui.checkbox.checked = false;
											node.childNodes[i].attributes.tag = "0";
										}
									}
								}
							}
						});
				var root = new Tree.AsyncTreeNode({
					checked : false,
					text : '',
					iconCls : '',
					draggable : false,
					expanded : true,
					id : '-1'//,//默认的node值：?node=-100
				//href:"javascript:radow.doEvent('querybyid','"+document.getElementById('ereaid').value+"')"
				});
				tree.setRootNode(root);
				tree.on(
								"check",
								function(node, checked) {
									//当连续选择按钮为选中，当点击node.id为不选中不处理 当点击node.id 为选中设置为第一node.id 并且 点击第二个node.id 为选中  选中所有可见node.id
									//node.attributes.tag    1、连续勾选下级 0是连续取消下级 2是什么都不做
									var existsCheckbox = document
											.getElementById('existsCheckbox');
									var continueCheckbox = document
											.getElementById('continueCheckbox');
									if (checked && existsCheckbox.checked
											&& !continueCheckbox.checked) {
										node.attributes.tag = "1";
										loop(node)
									} else if (!checked
											&& existsCheckbox.checked) {
										node.attributes.tag = "0";
									}
									if (continueCheckbox.checked) {
										if (checked) {
											if (continueCount == 1) {
												continueCheckbox.checked = false;
												continueCheck(continueOne, node);
											}
											if (continueCount == 0) {

												continueOne = node;
												continueCount = 1;
											}
										}
									}
									//如果包含下级被选中,此次事件是选中
									if (existsCheckbox.checked) {
										loop(node);
									}
									if (existsCheckbox.checked) {
										if (checked) {
											//changeNode += node.id+":"+checked+":1,";
											changeNode[node.id] = changeNode[node.id] ? (changeNode[node.id]
													+ checked + ":1,")
													: (checked + ":1,");
										} else {
											//changeNode += node.id+":"+checked+":2,";
											changeNode[node.id] = changeNode[node.id] ? (changeNode[node.id]
													+ checked + ":2,")
													: (checked + ":2,");
										}
									} else {
										//changeNode += node.id+":"+checked+":0,";
										changeNode[node.id] = changeNode[node.id] ? (changeNode[node.id]
												+ checked + ":0,")
												: (checked + ":0,");
									}
									toDOQuery();
									//alert(changeNode);
								}); //注册"check"事件
				tree.render();
				root.expand();
        var viewSize = Ext.getBody().getViewSize();
        var tableTab1 = document.getElementById("tree-div");
        tableTab1.style.height = viewSize.height-49+"px";//87 82
        LEFT_HEIGHT=viewSize.height-20;
        $("#tree-div >div").height("440px");
        $("#tree-div ").height("440px");
        //alert($("#tree-div >div").height());
			});
	function findNode(node, one, two) {
		if (tag == 1) {
			return one;
		}
		if (tag == 2) {
			return two;
		}
		if (node.id == one.id) {
			tag = 1;
			return one;
		}
		if (node.id == two.id) {
			tag = 2;
			return two;
		}
		if (node.childNodes.length > 0) {
			for (var i = 0; i < node.childNodes.length; i++) {
				if (tag != 0) {
					return;
				}
				findNode(node.childNodes[i], one, two);
			}
		}
	}
	function upOrDown(one, two) {
		var tree = Ext.getCmp("group");
		if (tree.getRootNode().id == two.id) {
			return two;
		}
		if (tree.getRootNode().id == one.id) {
			return one;
		}
		var node = tree.getRootNode();
		//一直往下找
		if (node.childNodes.length > 0) {
			for (var i = 0; i < node.childNodes.length; i++) {
				if (tag != 0) {
					return one;
				}
				findNode(node.childNodes[i], one, two);
			}
		}
	}
	function continueCheck(one, two) {
		//判断选择第二次的方向，如果朝上，执行朝上方法，否则朝下执行
		//判断是否有上级节点，上级节点往下找，如果没有上级节点返回false
		upOrDown(one, two);
		if (tag == 1) {
			if (continueCheckDownLoop(one, two.id) == 1) {
				two.attributes.tag = "2";
				return 1;
			}
		} else {
			if (continueCheckDownLoop(two, one.id) == 1) {
				one.attributes.tag = "2";
				return 1;
			}
		}
	}

	function continueCheckUpLoop(one, two) {
		one.attributes.tag = "1";
		if (one.id == two) {
			return 1;
		} else {
			if (!one.ui.checkbox.checked && nocheck == 1) {
				one.ui.checkbox.checked = true;
				//changeNode += one.id+":"+one.ui.checkbox.checked+":1,";	
				changeNode[one.id] = changeNode[one.id] ? (changeNode[one.id]
						+ one.ui.checkbox.checked + ":1,")
						: (one.ui.checkbox.checked + ":1,");
			}
		}
		try {
			if (one.parentNode.parentNode.childNodes.length > 0) {
				for (var i = 0; i < one.parentNode.parentNode.childNodes.length; i++) {
					if (one.parentNode.parentNode.childNodes[i].id == one.parentNode.id) {
						if (i + 1 < one.parentNode.parentNode.childNodes.length) {
							//找下级如果找不到找平级
							if (continueCheckDownLoop(
									one.parentNode.parentNode.childNodes[i + 1],
									two) == 1) {
								return 1;
							} else {
								if (continueCheckSameLoop(
										one.parentNode.childNodes[i + 1], two) == 1) {
									return 1;
								}
							}
						} else {
							if (continueCheckUpLoop(
									one.parentNode.parentNode.childNodes[0],
									two) == 1) {
								return 1;
							}
						}
					}
				}
			}
		} catch (e) {

		}
	}
	function continueCheckDownLoop(one, two) {
		one.attributes.tag = "1";
		if (one.id == two) {
			return 1;
		} else {
			if (!one.ui.checkbox.checked) {
				one.ui.checkbox.checked = true;
				//changeNode += one.id+":"+one.ui.checkbox.checked+":1,";	
				changeNode[one.id] = changeNode[one.id] ? (changeNode[one.id]
						+ one.ui.checkbox.checked + ":1,")
						: (one.ui.checkbox.checked + ":1,");
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
		one.attributes.tag = "1";
		if (one.id == two) {
			return 1;
		} else {
			if (!one.ui.checkbox.checked && nocheck == 1) {
				one.ui.checkbox.checked = true;
				//changeNode += one.id+":"+one.ui.checkbox.checked+":1,";
				changeNode[one.id] = changeNode[one.id] ? (changeNode[one.id]
						+ one.ui.checkbox.checked + ":1,")
						: (one.ui.checkbox.checked + ":1,");
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

	function loop(node) {
		if (node.ui.checkbox.checked == true) {
			//node.expand();
			if (node.childNodes.length > 0) {
				for (var i = 0; i < node.childNodes.length; i++) {
					node.childNodes[i].attributes.tag = "1";
					node.childNodes[i].ui.checkbox.checked = true;
					//changeNode += node.childNodes[i].id+":"+node.childNodes[i].ui.checkbox.checked+":1,";
					changeNode[node.childNodes[i].id] = changeNode[node.childNodes[i].id] ? (changeNode[node.childNodes[i].id]
							+ node.childNodes[i].ui.checkbox.checked + ":1,")
							: (node.childNodes[i].ui.checkbox.checked + ":1,");
					loop(node.childNodes[i]);
				}
			}
		} else {
			node.ui.checkbox.checked = false;
			for (var i = 0; i < node.childNodes.length; i++) {
				node.childNodes[i].attributes.tag = "0";
				node.childNodes[i].ui.checkbox.checked = false;
				changeNode[node.childNodes[i].id] = changeNode[node.childNodes[i].id] ? (changeNode[node.childNodes[i].id]
						+ node.childNodes[i].ui.checkbox.checked + ":0,")
						: (node.childNodes[i].ui.checkbox.checked + ":0,");
				loop(node.childNodes[i]);
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

	Ext.extend(
					Ext.tree.TreeCheckNodeUI,
					Ext.tree.TreeNodeUI,
					{

						renderElements : function(n, a, targetNode, bulkRender) {
							var tree = n.getOwnerTree();
							this.checkModel = tree.checkModel
									|| this.checkModel;
							this.onlyLeafCheckable = tree.onlyLeafCheckable || false;

							// add some indent caching, this helps performance when rendering a large tree 
							this.indentMarkup = n.parentNode ? n.parentNode.ui
									.getChildIndent() : '';

							var cb = (!this.onlyLeafCheckable || a.leaf);
							var href = a.href ? a.href : Ext.isGecko ? "" : "#";
							var buf = [
									'<li class="x-tree-node"><div ext:tree-node-id="',n.id,'" class="x-tree-node-el x-tree-node-leaf x-unselectable ', a.cls,'" unselectable="on">',
									'<span class="x-tree-node-indent">',
									this.indentMarkup,
									"</span>",
									'<img src="', this.emptyIcon, '" class="x-tree-ec-icon x-tree-elbow" />',
									'<img src="',
									a.icon || this.emptyIcon,
									'" class="x-tree-node-icon',
									(a.icon ? " x-tree-node-inline-icon" : ""),
									(a.iconCls ? " " + a.iconCls : ""),
									'" unselectable="on" />',
									cb ? ('<input class="x-tree-node-cb" type="checkbox" ' + (a.checked ? 'checked="checked" />'
											: '/>'))
											: '',
									'<a hidefocus="on" class="x-tree-node-anchor" href="',
									href,
									'" tabIndex="1" ',
									a.hrefTarget ? ' target="' + a.hrefTarget
											+ '"' : "",
									'><span unselectable="on">',
									n.text,
									"</span></a></div>",
									'<ul class="x-tree-node-ct" style="display:none;"></ul>',
									"</li>" ].join('');

							var nel;
							if (bulkRender !== true && n.nextSibling
									&& (nel = n.nextSibling.ui.getEl())) {
								this.wrap = Ext.DomHelper.insertHtml(
										"beforeBegin", nel, buf);
							} else {
								this.wrap = Ext.DomHelper.insertHtml(
										"beforeEnd", targetNode, buf);
							}

							this.elNode = this.wrap.childNodes[0];
							this.ctNode = this.wrap.childNodes[1];
							var cs = this.elNode.childNodes;
							this.indentNode = cs[0];
							this.ecNode = cs[1];
							this.iconNode = cs[2];
							var index = 3;
							if (cb) {
								this.checkbox = cs[3];
								Ext.fly(this.checkbox).on(
										'click',
										this.check.createDelegate(this,
												[ null ]));
								index++;
							}
							this.anchor = cs[index];
							this.textNode = cs[index].firstChild;
						},

						// private 
						check : function(checked) {
							var n = this.node;
							var tree = n.getOwnerTree();
							this.checkModel = tree.checkModel
									|| this.checkModel;

							if (checked === null) {
								checked = this.checkbox.checked;
							} else {
								this.checkbox.checked = checked;
							}

							n.attributes.checked = checked;
							tree.fireEvent('check', n, checked);

							if (!this.onlyLeafCheckable
									&& this.checkModel == 'cascade') {
								var parentNode = n.parentNode;
								if (parentNode !== null) {
									this.parentCheck(parentNode, checked);
								}
								if (!n.expanded && !n.childrenRendered) {
									n.expand(false, false, this.childCheck);
								} else {
									this.childCheck(n);
								}
							} else if (this.checkModel == 'single') {
								var checkedNodes = tree.getChecked();
								for (var i = 0; i < checkedNodes.length; i++) {
									var node = checkedNodes[i];
									if (node.id != n.id) {
										node.getUI().checkbox.checked = false;
										node.attributes.checked = false;
										tree.fireEvent('check', node, false);
									}
								}
							}

						},

						// private 
						childCheck : function(node) {
							var a = node.attributes;
							if (!a.leaf) {
								var cs = node.childNodes;
								var csui;
								for (var i = 0; i < cs.length; i++) {
									csui = cs[i].getUI();
									if (csui.checkbox.checked ^ a.checked)
										csui.check(a.checked);
								}
							}
						},

						// private 
						parentCheck : function(node, checked) {
							var checkbox = node.getUI().checkbox;
							if (typeof checkbox == 'undefined')
								return;
							if (!(checked ^ checkbox.checked))
								return;
							if (!checked && this.childHasChecked(node))
								return;
							checkbox.checked = checked;
							node.attributes.checked = checked;
							node.getOwnerTree().fireEvent('check', node,
									checked);

							var parentNode = node.parentNode;
							if (parentNode !== null) {
								this.parentCheck(parentNode, checked);
							}
						},

						// private 
						childHasChecked : function(node) {
							var childNodes = node.childNodes;
							if (childNodes || childNodes.length > 0) {
								for (var i = 0; i < childNodes.length; i++) {
									if (childNodes[i].getUI().checkbox.checked)
										return true;
								}
							}
							return false;
						},

						toggleCheck : function(value) {
							var cb = this.checkbox;
							if (cb) {
								var checked = (value === undefined ? !cb.checked
										: value);
								this.check(checked);
							}
						}
					});

	function loopRoot(rootnode) {//[节点对象，是否包含下级，是否选中本身]
		for (var i = 0; i < rootnode.childNodes.length; i++) {
			var cNode = rootnode.childNodes[i];
			if (cNode.ui.checkbox.checked) {
				nodeSelectedSet[cNode.id] = [ cNode, true, true ];
			} else {
				loopParent(cNode);//本级若有一个未被选中，上级都改成不包含下级
			}
			if (cNode.childNodes.length > 0) {
				loopRoot(cNode);
			} else {
				if (cNode.attributes.tag == 1 && !cNode.ui.checkbox.checked) {//没有手动展开下级机构， 但包含下级     本身没被选中
					if (cNode.isLeaf()) {//是叶子节点
						//nodeSelectedSet[cNode.id]=[cNode,false,false];//[节点对象，不包含下级，本身没被选中]
					} else {//不是叶子节点
						nodeSelectedSet[cNode.id] = [ cNode, true, false ];//[节点对象，包含下级，本身没被选中]
					}
				} else if (cNode.attributes.tag != 1
						&& cNode.ui.checkbox.checked) {//不包含下级，没有展开下级机构，但本身被选中
					if (cNode.isLeaf()) {//是叶子节点

					} else {//不是叶子节点
						loopParent(cNode);
					}
					nodeSelectedSet[cNode.id] = [ cNode, false, true ];

				} else if (cNode.attributes.tag == 1
						&& cNode.ui.checkbox.checked && cNode.isLeaf()) {//叶子节点  包含下级，当作不包含下级。
					nodeSelectedSet[cNode.id] = [ cNode, false, true ];
				}
			}
		}

	}
	//父级节点设置不包含下级。
	function loopParent(cNode) {
		if (cNode.parentNode) {
			if (nodeSelectedSet[cNode.parentNode.id]) {
				nodeSelectedSet[cNode.parentNode.id][1] = false;
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
	
	var allCheckedString = "";
	function findchildnode(node){
        var childnodes = node.childNodes;
        
        var length = 0;
        if (typeof(childnodes.length) != undefined && childnodes.length) length = childnodes.length;
         
        for(var i=0;i<length;i++){  //从节点中取出子节点依次遍历
            var tNode = childnodes[i]; 
            allCheckedString+=","+tNode.text;
            if (typeof(tNode.length) == undefined || !tNode.length || tNode.length<=0){
            	
            }else if(tNode.childNodes.length>0){  //判断子节点下是否存在子节点，个人觉得判断是否leaf不太合理，因为有时候不是leaf的节点也可能没有子节点
                findchildnode(tNode);    //如果存在子节点  递归
            }    
        } 
    }
	
	//点击查询
	function toDOQuery(){ 
	//alert("点击查询");
		//先找到选择的单位  
		var o = doQuery();
		if (isObjectEmpty(o)){
		Ext.Msg.alert('系统提示:','请在左边机构树选择单位！');
			return;
		}
	
		document.getElementById("selectUnitId").value = JSON.stringify(o);

	
		radow.doEvent('queryFromData');
	}
	
	function isObjectEmpty(obj){
		for(var key in obj){
		    if(key){
		      return false
		    }
	  	}
	  	return true
	}
	
	function doQuery() {
		//alert( JSON.stringify(changeNode));
		//alert("点击查询");
		var treenode = Ext.getCmp('group');
		var rootNode = treenode.getRootNode();
		loopRoot(rootNode);
		
		var nodeRealSelectedSet = {};//需要输出的节点。
		for(var nodeid in nodeSelectedSet){  
		  var selectedNode = nodeSelectedSet[nodeid][0];//被选中的节点
		  //如果下级没有展开节点，且本级没有被选中，但有包含下级选中标志。
		  if(!nodeSelectedSet[nodeid][2]){
			  nodeRealSelectedSet[nodeid] = selectedNode.text+"(下级机构):true:true";//节点名称+ 包含下级+ 本及是否被选中
			  
		  }
		  if(nodeSelectedSet[nodeid][2]&&!nodeSelectedSet[nodeid][1]){
			  var pNode = selectedNode.parentNode;
			  if(!nodeSelectedSet[pNode.id]||!nodeSelectedSet[pNode.id][1]){//父节点不存在  或 父节点不包含下级
				  nodeRealSelectedSet[nodeid] = selectedNode.text+":false:true";//节点名称+ 不包含下级+ 本及被选中
				  
			  }
			 
		  }
		  //以上下级节点未被展开
		  //下面过滤重复的包含下级
	      if(nodeSelectedSet[nodeid][1]){//包含下级
			  if(selectedNode.parentNode){//有上级节点
			  	  var pNode = selectedNode.parentNode;
			  	  if(!nodeSelectedSet[pNode.id]||!nodeSelectedSet[pNode.id][1]){//上级没有被选中 或 上级节点不包含下级
			  		nodeRealSelectedSet[nodeid] = selectedNode.text+"(全部机构):true:true";//节点名称+ 包含下级+ 本及是否被选中
			  	  }
			  }
	      }else{
	    	  nodeRealSelectedSet[nodeid] = selectedNode.text+":true:true";//节点名称+ 不包含下级+ 本及是否被选中
	      }
	    } 
		
		//alert( JSON.stringify(nodeRealSelectedSet));
		nodeSelectedSet = {};

		return nodeRealSelectedSet;
		//radow.doEvent('dogrant',JSON.stringify(nodeRealSelectedSet));
		
	}	
	
	
	function selectofSelection() {
		//alert( JSON.stringify(changeNode));
		var treenode = Ext.getCmp('group');
		var rootNode = treenode.getRootNode();
		loopRoot(rootNode);
		
		var nodeRealSelectedSet = {};//需要输出的节点。
		for(var nodeid in nodeSelectedSet){  
		  var selectedNode = nodeSelectedSet[nodeid][0];//被选中的节点
		  //如果下级没有展开节点，且本级没有被选中，但有包含下级选中标志。
		  if(!nodeSelectedSet[nodeid][2]){
			  nodeRealSelectedSet[nodeid] = selectedNode.text+"(下级机构):true:false";//节点名称+ 包含下级+ 本及是否被选中
			  continue;
		  }
		  if(nodeSelectedSet[nodeid][2]&&!nodeSelectedSet[nodeid][1]){
			  var pNode = selectedNode.parentNode;
			  if(!nodeSelectedSet[pNode.id]||!nodeSelectedSet[pNode.id][1]){//父节点不存在  或 父节点不包含下级
				  nodeRealSelectedSet[nodeid] = selectedNode.text+":false:true";//节点名称+ 不包含下级+ 本及被选中
				  
			  }
			  continue;
		  }
		  //以上下级节点未被展开
		  //下面过滤重复的包含下级
	      if(nodeSelectedSet[nodeid][1]){//包含下级
			  if(selectedNode.parentNode){//有上级节点
			  	  var pNode = selectedNode.parentNode;
			  	  if(!nodeSelectedSet[pNode.id]||!nodeSelectedSet[pNode.id][1]){//上级没有被选中 或 上级节点不包含下级
			  		nodeRealSelectedSet[nodeid] = selectedNode.text+"(全部机构):true:true";//节点名称+ 包含下级+ 本及是否被选中
			  	  }
			  }
	      }else{
	    	  nodeRealSelectedSet[nodeid] = selectedNode.text+":false:true";//节点名称+ 不包含下级+ 本及是否被选中
	      }
	    } 
		
		//alert( JSON.stringify(nodeRealSelectedSet));
		nodeSelectedSet = {};

		return nodeRealSelectedSet;
		//radow.doEvent('dogrant',JSON.stringify(nodeRealSelectedSet));
		
	}	
</script>



<script type="text/javascript">

</script>
</html>