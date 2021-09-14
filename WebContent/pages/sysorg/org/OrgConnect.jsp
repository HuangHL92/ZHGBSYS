<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.SysOrgTreePageModel"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>


<html class="ext-strict x-viewport">
<script src="<%=request.getContextPath()%>/pages/xbrm2/js/jquery-1.4.4.min.js" type="text/javascript"></script>
<%@include file="/comOpenWinInit.jsp" %>

<style>
.x-panel-bwrap {
	height: 100%
}

.x-panel-body {
	height: 100%
}
.busy{
	height: 406px;

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
        String B0111=(String)request.getParameter("subWinIdBussessId");
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


<odin:hidden property="sql2" />
<odin:hidden property="B0111" value="<%=B0111%>"/>
<odin:hidden property="selectType" />

<odin:hidden property="selectUnitId" />

<div id="groupTreeContent" style="height: 94%;width: 100%;margin-left: 12px;">
	<table style="height: 100%;">
		<tr valign="top">
			<td width="20%" height="100%" rowspan="0" colspan="0">
				<div style="height: 5%;background-color: #cedff5;" align="center">
					<input type="checkbox"  id="continueCheckbox" style="display:none" onclick="continueChoose()"><!-- <font style="font-size: 13">连续选择</font> -->
					<input type="checkbox" id="existsCheckbox" checked="checked" onclick="existsChoose()"><font style="font-size: 13">包含下级</font>
				</div>
				<div class="busy">
					<div id="tree-div" style="border: 2px solid #c3daf9;height: 99%; width: 98%;"></div>
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
								<td style="width: 150px;" >
									<div style="font-size: 12px;margin-top: 10px;margin-left: 10px;color: red;">多个社会信用代码/机构名查询，请按逗号隔开。</div>
								</td>
								<td>
									<odin:select property="tpye" label="" canOutSelectList="true" value = '1' data="['1','社会信用代码'],['2','机构名称']" width="120"></odin:select>
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
										<odin:gridDataCol name="unitid" />
										<odin:gridDataCol name="jgsy_name" />
										<odin:gridDataCol name="unify_code" />
										<odin:gridDataCol name="dwlb" isLast="true"/>
									</odin:gridJsonDataModel>
									<odin:gridColumnModel>
										<odin:gridRowNumColumn></odin:gridRowNumColumn>
										<odin:gridEditColumn2 dataIndex="jgsy_name" header="机构名称" width="120" align="center" editor="select" edited="false" />
										<odin:gridColumn dataIndex="unify_code" edited="false" header="机构社会信用代码" width="150"  align="center" />
										<odin:gridEditColumn2 dataIndex="dwlb" header="机构类型" width="100" align="center" editor="select" edited="false" isLast="true" />
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
										<odin:gridDataCol name="personcheck"  />
										<odin:gridDataCol name="unitid" />
										<odin:gridDataCol name="jgsy_name" />
										<odin:gridDataCol name="unify_code" />
										<odin:gridDataCol name="dwlb" isLast="true"/>
									</odin:gridJsonDataModel>
									<odin:gridColumnModel>
										<odin:gridRowNumColumn></odin:gridRowNumColumn>
										<odin:gridEditColumn2 dataIndex="jgsy_name" header="机构名称" width="120" align="center" editor="select" edited="false" />
										<odin:gridColumn dataIndex="unify_code" edited="false" header="机构社会信用代码" width="150"  align="center" />
										<odin:gridEditColumn2 dataIndex="dwlb" header="机构类型" width="100" align="center" editor="select" edited="false" isLast="true" />
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
<div align="right" style="margin-top: 20px;">
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
	
	if(!!parent.Ext.getCmp(subWinId).initialConfig.ynId){
		document.getElementById("ynId").value=parent.Ext.getCmp(subWinId).initialConfig.ynId;
	}
	if(!!parent.Ext.getCmp(subWinId).initialConfig.yntype){
		document.getElementById("yntype").value=parent.Ext.getCmp(subWinId).initialConfig.yntype;
	} 
	
	$("#appointment").val(parent.$("#appointment").val());
	if (parent.$("#appointment").val()=="1"){
		$("#selectByPersonIdBtn").attr("disabled","true");
		$("#selectByYnIdBtn").attr("disabled","true");
	}
	var gridcq = Ext.getCmp("gridcq");
	var selectName = Ext.getCmp("selectName");
	var gStore = gridcq.getStore();
	var sStore = selectName.getStore();
    gStore.getModifiedRecords();
    //点击选中数据事件
	gridcq.on("rowclick",function(o, index, o2){
		var rowData = gStore.getAt(index);
		var count = sStore.getCount();
		var flag = true;
		for(var i=0;i<count;i++){
			/* record = sStore.getAt(i);
			if(rowData.data.unitid==record.data.unitid){
				flag = false;
				break;
			} */
			Ext.Msg.alert('系统提示:','只能添加一个机构');
			flag = false;
			break;
		}
		if(flag){
			sStore.insert(sStore.getCount(),rowData);
			gStore.remove(rowData);
			gridcq.view.refresh();
		}
		
	});

	selectName.on("rowclick",function(o, index, o2){
		var rowData = sStore.getAt(index);
		var count = gStore.getCount();
		var flag = true;
		//判断待选列表中是否有此机构
		for(var i=0;i<gStore.data.length;i++){
			if(rowData.data.unitid==gStore.getAt(i).data.unitid){
				flag=false;
			}
		}
		if(flag){
			gStore.insert(gStore.getCount(),rowData);
		}
		sStore.remove(rowData);
		selectName.view.refresh();
	});
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
	   *   保存机构关联信息
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
       // toDOQuery();
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
       // toDOQuery();

	}
	
	Ext.onReady(function() {
		
		//根据父页面的结构树加载机构树  2019.12.05 yzk
		var LWflag=parent.Ext.getCmp(subWinId).initialConfig.LWflag;
		var sign;
		if(LWflag=="1"){
			sign="look"
		}else{
			sign="write";
		}
		
		var Tree = Ext.tree;
		var isFirst = 1;
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
										dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysorg.org.OrgConnect&eventNames=orgTreeOrgConnect&unsort=1',
										baseParams : {sign: sign}
									}),
							listeners : {
								'expandnode' : function(node) {
	                                //获取展开节点的信息
									var existsCheckbox = document
											.getElementById('existsCheckbox');
									if (isFirst++ > 1 && existsCheckbox.checked) {
										showChildNode(node);
										if (node.attributes.checked == true) {
											for (var i = 0; i < node.childNodes.length; i++) {
												node.childNodes[i].ui.checkbox.checked = true;
												node.childNodes[i].attributes.checked = true;
												node.childNodes[i].attributes.tag = "1";
											}
										} else {
											for (var i = 0; i < node.childNodes.length; i++) {
												node.childNodes[i].ui.checkbox.checked = false;
												node.childNodes[i].attributes.checked = false;
												node.childNodes[i].attributes.tag = "1";
											}
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
				tree.on("check", function(node, checked) {
					//当连续选择按钮为选中，当点击node.id为不选中不处理 当点击node.id 为选中设置为第一node.id 并且 点击第二个node.id 为选中  选中所有可见node.id
					//node.attributes.tag    1、连续勾选下级 0是连续取消下级 2是什么都不做
					//获取是否包含下级的值
					var existsCheckbox = document
							.getElementById('existsCheckbox');
					
					
					//如果包含下级被选中,此次事件是选中
					if (existsCheckbox.checked) {
						loop(node);
						node.expand();
						//showChildNode(node);
					}
					if(!existsCheckbox.checked){
						if (node.ui.checkbox.checked == true) {
							node.attributes.tag="1";
							node.ui.checkbox.checked = true;
							node.attributes.checked = true;	
					    }else{
					    	node.attributes.tag="0";
							node.ui.checkbox.checked = false;
							node.attributes.checked = false;	
					    }
					}
					toDOQuery();
					//alert(changeNode);
				}); //注册"check"事件
				tree.render();
				root.expand();
				var viewSize = Ext.getBody().getViewSize();
				var tableTab1 = document.getElementById("tree-div");
				tableTab1.style.height = viewSize.height - 49 + "px";//87 82
				LEFT_HEIGHT = viewSize.height - 20;
				$("#tree-div >div").height("406px");
				$("#tree-div ").height("406px");
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

	
	//递归展开所有子节点
	function showChildNode(node) {
		var childnodes = node.childNodes;
		for (var i = 0; i < childnodes.length; i++) { //从节点中取出子节点依次遍历
			var rootnode = childnodes[i];
			rootnode.expand();
			if (node.hasChildNodes()) {

			}
			//alert(result); //LJ遍历呈现树-LJ
			if (rootnode.childNodes.length > 0) {
				showChildNode(rootnode); //如果存在子节点  递归
			}
		}
	}
	function loop(node) {
		var isContain = true;
		if (node.ui.checkbox.checked == true) {
			if (isContain) {
				node.attributes.tag="1";
				node.ui.checkbox.checked = true;
				node.attributes.checked = true;
				if (node.childNodes.length > 0) {
					for (var i = 0; i < node.childNodes.length; i++) {
						node.childNodes[i].attributes.tag="1";
						node.childNodes[i].ui.checkbox.checked = true;
						node.childNodes[i].attributes.checked = true;
						loop(node.childNodes[i]);
					}
				}
			} else {
				node.attributes.tag="2";
				node.ui.checkbox.checked = true;
				node.attributes.checked = true;
			}
		}
		if (node.ui.checkbox.checked == false) {
			node.attributes.tag="1";
			node.ui.checkbox.checked = false;
			node.attributes.checked = false;
			for (var i = 0; i < node.childNodes.length; i++) {
				node.childNodes[i].attributes.tag="1";
				node.childNodes[i].ui.checkbox.checked = false;
				node.childNodes[i].attributes.checked = false;
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
	function findchildnode(node) {
		var childnodes = node.childNodes;

		var length = 0;
		if (typeof (childnodes.length) != undefined && childnodes.length)
			length = childnodes.length;

		for (var i = 0; i < length; i++) { //从节点中取出子节点依次遍历
			var tNode = childnodes[i];
			allCheckedString += "," + tNode.text;
			if (typeof (tNode.length) == undefined || !tNode.length
					|| tNode.length <= 0) {

			} else if (tNode.childNodes.length > 0) { //判断子节点下是否存在子节点，个人觉得判断是否leaf不太合理，因为有时候不是leaf的节点也可能没有子节点
				findchildnode(tNode); //如果存在子节点  递归
			}
		}
	}

	//点击查询
	function toDOQuery() {
		// alert("点击查询");
		//先找到选择的单位  
		var o = doQuery();
		if (isObjectEmpty(o)) {
			Ext.Msg.alert('系统提示:', '请在左边机构树选择单位！');
			return;
		}
		document.getElementById("selectUnitId").value = JSON.stringify(o);
		radow.doEvent('queryFromData');
	}

	function isObjectEmpty(obj) {
		for ( var key in obj) {
			if (key) {
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
		for ( var nodeid in nodeSelectedSet) {
			var selectedNode = nodeSelectedSet[nodeid][0];//被选中的节点
			nodeRealSelectedSet[nodeid] = selectedNode.text
			+ "(下级机构):true:true";
			}
		nodeSelectedSet = {};
		return nodeRealSelectedSet;

	}

</script>
</html>