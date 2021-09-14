<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%> 
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@page
	import="com.insigma.siis.local.pagemodel.search.ComSearchPageModel"%>
<%@page
	import="com.insigma.siis.local.pagemodel.sysorg.org.SysOrgPageModel"%>
<%@page
	import="com.insigma.siis.local.pagemodel.sysmanager.group.GroupManagePageModel"%>
<%@page import="com.insigma.siis.local.pagemodel.exportexcel.FiledownServlet"%>

<%
	String areaname = (String) new GroupManagePageModel().areaInfo
			.get("areaname");
%>
<script type="text/javascript" src="<%=request.getContextPath() %>/basejs/helperUtil.js"></script>
<odin:toolBar property="btnToolBar" applyTo="groupTreePanel">
	<odin:fill />

	<odin:buttonForToolBar text="重置" id="clear"
		icon="images/sx.gif" cls="x-btn-text-icon" />
	<odin:separator />
	<odin:buttonForToolBar text="查询" icon="images/search.gif"
		cls="x-btn-text-icon" id="getTree" handler="doQuery"
		tooltip="对所在机构及其下级机构进行查询" />
	<odin:separator />
	<odin:buttonForToolBar text="汇总表" id="gethzb"
		icon="images/icon_photodesk.gif" cls="x-btn-text-icon"
		handler="expHzb" />
	<odin:separator />
	<odin:buttonForToolBar text="登记表操作" id="DJBBtn" menu="DJB" icon="images/icon_photodesk.gif" cls="x-btn-text-icon"></odin:buttonForToolBar>
	<odin:separator />
	<odin:buttonForToolBar text="标准名册" id="bzbcBtn"  menu="bzbc"
		icon="images/dtree/folder.gif" cls="x-btn-text-icon"/>
	<odin:separator />
	<odin:buttonForToolBar text="照片名册" id="zpmcBtn"  menu="zpmc"
		icon="images/dtree/imgfolder.gif" cls="x-btn-text-icon"/>
	<odin:separator />
	<odin:buttonForToolBar text="其他表格" id="qtbg" handler="othertem"
		icon="images/right.gif" cls="x-btn-text-icon"/>
	<odin:separator /> 
	<odin:buttonForToolBar text="设置每页条数" icon="images/keyedit.gif" id="setPageSize" handler="setPageSize1" isLast="true" tooltip="用于设置每页记录条数" />	
</odin:toolBar>

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
function refreshWin(){
	window.location.reload();
}

function showtemplate(){
    
	radow.doEvent('showtemplate'); 
}

//自定义表格展示
function othertem(){
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
	var person = document.getElementById('checkList').value;
	if(person == ''){
		odin.error("请选择要展示的人员！");
		return;
	}
	//radow.doEvent('choosedata');
	var url = contextPath + "/pages/publicServantManage/chooseZDYtem.jsp";
	doOpenPupWin(url, "选择模板", 300, 200);
	
}
function showtem(value){
	radow.doEvent('showtem',value);
}
function setPageSize1(){
	//odin.grid.menu.setPageSize('memberGrid');
	var gridId = 'peopleInfoGrid';
	if (!Ext.getCmp(gridId)) {
		odin.error("要导出的grid不存在！gridId=" + gridId);
		return;
	}
	var grid = Ext.getCmp(gridId);
	var store = grid.getStore();
	if (store.getCount() == 0) {
		//odin.error("请先查询出数据后再进行本操作！");
		//return;
	}
	var pageingToolbar = (grid.getBottomToolbar() || grid.getTopToolbar());
	if (pageingToolbar && pageingToolbar.pageSize) {
		gridIdForSeting = gridId;
		var url = contextPath + "/sys/comm/commSetGrid.jsp";
		doOpenPupWin(url, "设置每页条数", 300, 200);
	} else {
		odin.error("非分页grid不能使用此功能！");
		return;
	}
}


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
	Ext
			.onReady(function() {
				var man = document.getElementById('manager').value;
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
							enableDD : false,
							containerScroll : true,
							checkModel : 'multiple',
							loader : new Tree.TreeLoader(
									{
										baseAttrs : {
											uiProvider : Ext.tree.TreeCheckNodeUI
										},
        	 						 dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysorg.org.PublicWindow&eventNames=orgTreeJsonDataPeople'
									}),
					          listeners:{
					        	'expandnode':function(node){
					        		//获取展开节点的信息
					        		if(node.attributes.tag=="1"){
					        			node.attributes.tag="2";
					        			for(var i=0;i<node.childNodes.length;i++){
					        			 	node.childNodes[i].ui.checkbox.checked=true;
					        			 	node.childNodes[i].attributes.tag="1";
					        			}
									}else if(node.attributes.tag=="0"){
										node.attributes.tag="2";
										for(var i=0;i<node.childNodes.length;i++){
					        			 	node.childNodes[i].ui.checkbox.checked=false;
					        			 	node.childNodes[i].attributes.tag="0";
					        			 }
									}
					        	}
					        }
						});
				tree.on('checkchange', function(node, checked) {
					if (node.attributes.href.indexOf('您没有权限') > 0) {
						node.getUI().checkbox.checked = false;
						alert('您没有权限');
					}
				}, tree);
				var root = new Tree.AsyncTreeNode({
					checked : false,
					text : document.getElementById('ereaname').value,
					iconCls : document.getElementById('picType').value,
					draggable : false,
					expanded : true,
					id : document.getElementById('ereaid').value
				//,//默认的node值：?node=-100
				//href:"javascript:radow.doEvent('querybyid','"+document.getElementById('ereaid').value+"')"
				});
				tree.setRootNode(root);
				tree.on("check", function(node, checked) {
					//当连续选择按钮为选中，当点击node.id为不选中不处理 当点击node.id 为选中设置为第一node.id 并且 点击第二个node.id 为选中  选中所有可见node.id
			    	  //node.attributes.tag    1、连续勾选下级 0是连续取消下级 2是什么都不做
			    	  var existsCheckbox=document.getElementById('existsCheckbox');
			    	  var continueCheckbox=document.getElementById('continueCheckbox');
			    	  if(checked&&existsCheckbox.checked&&!continueCheckbox.checked){
					  	node.attributes.tag="1";
					  	loop(node)
			    	  }else if(!checked&&existsCheckbox.checked){
			    	    node.attributes.tag="0";
			    	  }
			    	  if(continueCheckbox.checked){
			    		  if(checked){
			    			  if(continueCount == 1){
			    				  continueCheckbox.checked=false;
								  continueCheck(continueOne,node);
			    			  }
			    			  if(continueCount==0){
			
			    				  continueOne=node;
			    				  continueCount=1;
			    			  }
			    		  }
			    	  }
			    	  //如果包含下级被选中,此次事件是选中
					  if(existsCheckbox.checked){
						  loop(node);
					  }
					  if(existsCheckbox.checked){
					        if(checked){
					        	changeNode += node.id+":"+checked+":1,";
					        }else{
					        	changeNode += node.id+":"+checked+":2,";
					        }
					  }else{
					  		changeNode += node.id+":"+checked+":0,";
					  }
					//alert(changeNode);
				}); //注册"check"事件
				tree.render();
				root.expand();
				var tree = Ext.getCmp("group");
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

	function continueCheckUpLoop(one, two) {
		one.attributes.tag="1";
		if (one.id == two) {
			return 1;
		} else {
			if (!one.ui.checkbox.checked && nocheck == 1) {
				one.ui.checkbox.checked = true;
				changeNode += one.id+":"+one.ui.checkbox.checked+":1,";	
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

	function loop(node) {
		if (node.ui.checkbox.checked == true) {
			//node.expand();
			if (node.childNodes.length > 0) {
				for (var i = 0; i < node.childNodes.length; i++) {
				  	  node.childNodes[i].attributes.tag="1";
					  node.childNodes[i].ui.checkbox.checked=true;
					  changeNode += node.childNodes[i].id+":"+node.childNodes[i].ui.checkbox.checked+":1,";
					  loop(node.childNodes[i]);
				}
			}
		} else {
			node.ui.checkbox.checked = false;
			for (var i = 0; i < node.childNodes.length; i++) {
			  node.childNodes[i].attributes.tag="0";
			  node.childNodes[i].ui.checkbox.checked=false;
			  changeNode += node.childNodes[i].id+":"+node.childNodes[i].ui.checkbox.checked+":0,";
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

	Ext
			.extend(
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
	var nodeSelectedSet = {};
	
	function doQuery() {
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
		radow.doEvent('query2',JSON.stringify(nodeRealSelectedSet));
		nodeSelectedSet = {};
	}
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
</script>

<%
       SysOrgPageModel sys = new SysOrgPageModel();
       String picType = (String) (sys.areaInfo
		       .get("picType"));
       String ereaname = (String) (sys.areaInfo
		       .get("areaname"));
       String ereaid = (String) (sys.areaInfo
		       .get("areaid"));
       String manager = (String) (sys.areaInfo
		.get("manager"));
%>

<div id="groupTreeContent" style="height: 100%">
<div id="groupTreePanel"></div>
	<table width="100%">
		<tr valign="top">
			<td width="175">
				<table width="175">
					<tr>
						<td valign="top">
							
								<table style="width: 100%; background-color: #cedff5">
									<tr>
										<td style="padding-left: 30"><input type="checkbox"
											id="continueCheckbox" onclick="continueChoose()"><font
											style="font-size: 13">连续选择</font></td>
										<td style="padding-left: 30"><input type="checkbox"
											id="existsCheckbox" onclick="existsChoose()"
											><font style="font-size: 13">包含下级</font></td>
									</tr>
								</table>
								<table style="background-color: #cedff5">
									<tr>
										<td>
											<table>
												<tr>
													<td>
														<div id="tree-div"
															style="overflow: auto;  border: 2px solid #c3daf9;"></div>
													</td>
												</tr>
												<tr>
													<odin:hidden property="codevalueparameter" />
												</tr>
											</table>
											<table style="width: 100%; background-color: #cedff5">
										        <tr>
											        <td></td>
											        <td><label style="font-size: 12">定位机构</label></td>
											        <td><input type="text" id="nextProperty" size="17"
												            class=" x-form-text x-form-field"
												            onpropertychange="OnInput (this)"></td>
											        <td align="right"><odin:button property="selectOrgsBtn"
													        text="下一个" handler="doQueryNext" /></td>
										       </tr>
										       <tr>
											        <td width="20"></td>
											        <td align="right"><odin:button property="selectOrgsBtn"
													        text="确定" handler="doQuery"></odin:button></td>
											        <td align="right"><odin:button property="cancelBtn"
													        text="取消"></odin:button></td>
										      </tr>
									       </table>
										</td>
									</tr>
								</table>
						</td>
						<odin:hidden property="area" value="<%=areaname%>" />
						<odin:hidden property="id" />
						<odin:hidden property="fid" />
						<odin:hidden property="checkedgroupid" />
						<odin:hidden property="forsearchgroupid" />
						<odin:hidden property="sql" />
						<odin:hidden property="a0201b" />
						<odin:hidden property="checkList" />
						<odin:hidden property="ddd" />
						<odin:hidden property="tabname" />
						<odin:hidden property="a0000"/>
						<odin:hidden property="a0101"/>
						<odin:hidden property="ereaname" value="<%=ereaname%>" />
						<odin:hidden property="ereaid" value="<%=ereaid%>" />
						<odin:hidden property="manager" value="<%=manager%>" />
						<odin:hidden property="picType" value="<%=picType%>" />
					</tr>
				</table>
			</td>
			<td><odin:panel title="高级查询" contentEl="condition"
					property="conditionPanel" collapsed="true" collapsible="true"></odin:panel>
				<div id="condition">
					<odin:groupBox title="机构范围" property="ggBox">
						<table width="100%">
							<tr>
								<td><odin:select2 property="a0160" label="人员类别"
										codeType="ZB125"></odin:select2></td>
								<td><odin:select2 property="a0163" label="人员状态"
										codeType="ZB126"></odin:select2></td>
								<td>
									<table>
										<tr>
											<odin:numberEdit property="age" label="年龄 " maxlength="3"
												width="50" />
											<td>—</td>
											<odin:numberEdit property="age1" maxlength="3" width="50" />
										</tr>
									</table>
								</td>
							</tr>
						</table>
						<table width="100%">
							<tr>
								<td width="30%" style="padding-left: 86"><odin:checkbox
										property="female" label="女"></odin:checkbox></td>
								<td width="30%" style="padding-left: 112"><odin:checkbox
										property="minority" label="少数民族"></odin:checkbox></td>
								<td width="30%" style="padding-left: 95"><odin:checkbox
										property="nonparty" label="非中共党员"></odin:checkbox></td>
							</tr>
						</table>
					</odin:groupBox>
					<odin:groupBox title="最高职务层次" property="zwBox">
						<table width="100%">
							<tr>
								<td>
									<table>
										<tr>
											<span style="font: 12px">职务层次</span>
										</tr>
										<tr>
											<td><tags:PublicTextIconEdit codetype="ZB09"
												property="duty" label2="职务层次" readonly="true" width="100" />
											</td>
											<td width="14px"></td>
											<td><tags:PublicTextIconEdit codetype="ZB09"
												property="duty1" readonly="true" width="100" label="至"
												label2="职务层次" /></td>
										</tr>
									</table>
								</td>
								<odin:select2 property="a0219" label="职务类别" codeType="ZB42"></odin:select2>
							</tr>
							<tr>
								<td>
									<table>
										<tr>
											<span style="font: 12px">任现职务层次时间</span>
										</tr>
										<tr>
											<odin:dateEdit property="dutynow" width="100" format="Ymd" />
											<td width="14px"></td>
											<odin:dateEdit property="dutynow1" width="100" format="Ymd"
												label="至" />
										</tr>
									</table>
								<td>
							</tr>
							<tr>

							</tr>
						</table>
					</odin:groupBox>
					<odin:groupBox title="学历" property="xlBox">
						<table width="100%">
							<tr align="left">
								<td>
									<table>
										<tr>
										    <td>
											    <span style="font: 12px">学</span>
										    </td>
										    <td>
											    <span style="font: 12px">历</span>
										    </td>
											<td><tags:PublicTextIconEdit property="edu"
												readonly="true" codetype="ZB64" width="100" label2="学历" /></td>
											<td width="14px"></td>
											<td>
											   <!--  <span style="font: 12px">至</span> -->
										    </td>
											<td><tags:PublicTextIconEdit codetype="ZB64"
												readonly="true" property="edu1" width="100" label2="学历" label="至"/></td>
										</tr>
									</table>
								</td>
								<td width="80%" style="padding-left: 112"><odin:checkbox property="allday"
										 label="全日制 "></odin:checkbox></td>
							</tr>
						</table>
					</odin:groupBox>

				</div> <odin:editgrid property="peopleInfoGrid" title="人员信息表"
					autoFill="false" width="550" height="500" bbarId="pageToolBar"
					pageSize="20">
					<odin:gridJsonDataModel>
						<odin:gridDataCol name="personcheck" />
						<odin:gridDataCol name="a0000" />
						<odin:gridDataCol name="a0101" />
						<odin:gridDataCol name="a0104" />
						<odin:gridDataCol name="a0107" />
						<odin:gridDataCol name="a0117" />
						<odin:gridDataCol name="a0141" />
						<odin:gridDataCol name="a0192a" />
						<odin:gridDataCol name="a0184" />
						<odin:gridDataCol name="a0148" isLast="true" />
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
						<odin:gridRowNumColumn></odin:gridRowNumColumn>
						<odin:gridColumn header="selectall" width="40" editor="checkbox"
							dataIndex="personcheck" edited="true" gridName="persongrid"
							checkBoxClick="getCheckList()"
							checkBoxSelectAllClick="getCheckList()" />
						<odin:gridColumn dataIndex="a0000" width="110" header="id"
							align="center" hidden="true" />
						<odin:gridColumn dataIndex="a0101" width="110" header="姓名"
							align="center" />
						<odin:gridEditColumn2 dataIndex="a0104" width="100" header="性别"
							align="center" editor="select" edited="false" codeType="GB2261" />
						<odin:gridColumn dataIndex="a0107" width="130" header="出生日期"
							align="center" editor="text" edited="false" />
						<odin:gridEditColumn2 dataIndex="a0117" width="130" header="民族"
							align="center" editor="select" edited="false" codeType="GB3304" />
						<odin:gridEditColumn2 dataIndex="a0141" width="130" header="政治面貌"
							align="center" editor="select" edited="false" codeType="GB4762" />
						<odin:gridColumn dataIndex="a0192a" width="130" header="工作单位及职务"
							align="center" />
						<odin:gridEditColumn2 dataIndex="a0148" width="130" header="职务层次"
							align="center" isLast="true" editor="select" edited="false"
							codeType="ZB09" />
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

//汇总表
function expHzb(){
		var a0000 = '';
		var a0101 = '';
		var gridId = "peopleInfoGrid";
		if (!Ext.getCmp(gridId)) {
			odin.error("要导出的grid不存在！gridId=" + gridId);
			return;
		}
		var grid = Ext.getCmp(gridId);
		var store = grid.getStore();
		var fileName = "公务员登记备案汇总表.doc";
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
	
		window.location="<%=request.getContextPath()%>/FiledownServlet?downLoad=true&excelType=7&fileName="+ fileName +"&a0000="+ a0000 +"&a0101="+ a0101;
	}

	Ext.onReady(function() {
		//页面调整，获取高度
		var vheight = Ext.getBody().getViewSize().height;
		var sheight = objTop(document.getElementById('forView_peopleInfoGrid'))[0];
		vheight = (vheight == 0)?548:vheight;
		var treeheight = (sheight == 0)?70:sheight;
		sheight = (sheight == 0)?120:sheight;
		
		//获取宽度
		var vwidth = Ext.isIE?Ext.getBody().getViewSize().width:window.innerWidth;
		var swidth = objTop(document.getElementById('forView_peopleInfoGrid'))[1];
		vwidth = (vwidth == 0)?1115:vwidth;
		swidth = (swidth == 0)?260:swidth;
		var cwidth = document.body.clientWidth;
		cwidth = (cwidth == 0)?1115:cwidth;
		
		//设置高宽
		Ext.getCmp('peopleInfoGrid').setHeight(vheight-sheight-4);//设置grid表格高度
		Ext.getCmp('peopleInfoGrid').setWidth(vwidth-swidth-2+16);//设置grid表格宽度
		document.getElementById("tree-div").style.height =(vheight-treeheight-4)-63;//设置树高度
		document.getElementById('groupTreePanel').style.width = cwidth+16;//设置工具栏宽度
		
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
	
	

	
<odin:menu property="DJB">
<odin:menuItem text="生成登记表" property="createDjb" handler="createExcelTemp" ></odin:menuItem>
<odin:menuItem text="查看登记表" property="getDjb" handler="expExcelTemp" isLast="true"></odin:menuItem>
</odin:menu>

<odin:menu property="bzbc">
<odin:menuItem text="标准名册" property="createbzbcBtn" handler="downLoadTmp"></odin:menuItem>
<odin:menuItem text="自定义标准名册" property="createzdyBtn" handler="downLoadZdyTmp" isLast="true"></odin:menuItem>
</odin:menu> 

<odin:menu property="zpmc">
<odin:menuItem text="照片名册" property="zpbc2" handler="downLoadTmp2" ></odin:menuItem>
<odin:menuItem text="自定义照片名册" property="zpzdy" handler="downLoadTmp4" isLast="true"></odin:menuItem>
</odin:menu>

</script>
<odin:window src="/blank.htm" id="simpleExpWin" width="560" height="350" maximizable="false" title="上传附件窗口"></odin:window>
<odin:window src="/blank.htm" id="modifyFileWindow" width="560" height="300" maximizable="false" title="查看/删除附件窗口"></odin:window>
<odin:window src="/blank.htm" id="othertem" width="300" height="200" maximizable="false" title="选择表格模板"></odin:window>
<odin:hidden property="ddd"/>


