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

	<odin:buttonForToolBar text="����" id="clear"
		icon="images/sx.gif" cls="x-btn-text-icon" />
	<odin:separator />
	<odin:buttonForToolBar text="��ѯ" icon="images/search.gif"
		cls="x-btn-text-icon" id="getTree" handler="doQuery"
		tooltip="�����ڻ��������¼��������в�ѯ" />
	<odin:separator />
	<odin:buttonForToolBar text="���ܱ�" id="gethzb"
		icon="images/icon_photodesk.gif" cls="x-btn-text-icon"
		handler="expHzb" />
	<odin:separator />
	<odin:buttonForToolBar text="�ǼǱ����" id="DJBBtn" menu="DJB" icon="images/icon_photodesk.gif" cls="x-btn-text-icon"></odin:buttonForToolBar>
	<odin:separator />
	<odin:buttonForToolBar text="��׼����" id="bzbcBtn"  menu="bzbc"
		icon="images/dtree/folder.gif" cls="x-btn-text-icon"/>
	<odin:separator />
	<odin:buttonForToolBar text="��Ƭ����" id="zpmcBtn"  menu="zpmc"
		icon="images/dtree/imgfolder.gif" cls="x-btn-text-icon"/>
	<odin:separator />
	<odin:buttonForToolBar text="�������" id="qtbg" handler="othertem"
		icon="images/right.gif" cls="x-btn-text-icon"/>
	<odin:separator /> 
	<odin:buttonForToolBar text="����ÿҳ����" icon="images/keyedit.gif" id="setPageSize" handler="setPageSize1" isLast="true" tooltip="��������ÿҳ��¼����" />	
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

//�Զ�����չʾ
function othertem(){
	var a0000 = '';
	var a0101 = '';
	var filename = '';
	var j = 0;
	var gridId = "peopleInfoGrid";
	if (!Ext.getCmp(gridId)) {
		odin.error("Ҫչʾ��grid�����ڣ�gridId=" + gridId);
		return;
	}
	var grid = Ext.getCmp(gridId);
	var store = grid.getStore();
	if (store.getCount() == 0) {
		odin.error("û��Ҫչʾ�����ݣ�");
		return;
	}
	var person = document.getElementById('checkList').value;
	if(person == ''){
		odin.error("��ѡ��Ҫչʾ����Ա��");
		return;
	}
	//radow.doEvent('choosedata');
	var url = contextPath + "/pages/publicServantManage/chooseZDYtem.jsp";
	doOpenPupWin(url, "ѡ��ģ��", 300, 200);
	
}
function showtem(value){
	radow.doEvent('showtem',value);
}
function setPageSize1(){
	//odin.grid.menu.setPageSize('memberGrid');
	var gridId = 'peopleInfoGrid';
	if (!Ext.getCmp(gridId)) {
		odin.error("Ҫ������grid�����ڣ�gridId=" + gridId);
		return;
	}
	var grid = Ext.getCmp(gridId);
	var store = grid.getStore();
	if (store.getCount() == 0) {
		//odin.error("���Ȳ�ѯ�����ݺ��ٽ��б�������");
		//return;
	}
	var pageingToolbar = (grid.getBottomToolbar() || grid.getTopToolbar());
	if (pageingToolbar && pageingToolbar.pageSize) {
		gridIdForSeting = gridId;
		var url = contextPath + "/sys/comm/commSetGrid.jsp";
		doOpenPupWin(url, "����ÿҳ����", 300, 200);
	} else {
		odin.error("�Ƿ�ҳgrid����ʹ�ô˹��ܣ�");
		return;
	}
}


	var continueCount = 0;//����ѡ�����
	var changeNode = "";
	var childNodes = "";
	var continueOne;//����ѡ�����һ������
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
							el : 'tree-div',//Ŀ��div����
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
					        		//��ȡչ���ڵ����Ϣ
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
					if (node.attributes.href.indexOf('��û��Ȩ��') > 0) {
						node.getUI().checkbox.checked = false;
						alert('��û��Ȩ��');
					}
				}, tree);
				var root = new Tree.AsyncTreeNode({
					checked : false,
					text : document.getElementById('ereaname').value,
					iconCls : document.getElementById('picType').value,
					draggable : false,
					expanded : true,
					id : document.getElementById('ereaid').value
				//,//Ĭ�ϵ�nodeֵ��?node=-100
				//href:"javascript:radow.doEvent('querybyid','"+document.getElementById('ereaid').value+"')"
				});
				tree.setRootNode(root);
				tree.on("check", function(node, checked) {
					//������ѡ��ťΪѡ�У������node.idΪ��ѡ�в����� �����node.id Ϊѡ������Ϊ��һnode.id ���� ����ڶ���node.id Ϊѡ��  ѡ�����пɼ�node.id
			    	  //node.attributes.tag    1��������ѡ�¼� 0������ȡ���¼� 2��ʲô������
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
			    	  //��������¼���ѡ��,�˴��¼���ѡ��
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
				}); //ע��"check"�¼�
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
		//һֱ������
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
		//�ж�ѡ��ڶ��εķ���������ϣ�ִ�г��Ϸ�����������ִ��
		//�ж��Ƿ����ϼ��ڵ㣬�ϼ��ڵ������ң����û���ϼ��ڵ㷵��false
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
							//���¼�����Ҳ�����ƽ��
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
		//һֱ������
		if (node.childNodes.length > 0) {
			for (var i = 0; i < node.childNodes.length; i++) {
				if (continueCheckDownLoop(node.childNodes[i], two) == 1) {
					return 1;
				}
			}
		} else {
			//ƽ������
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
					//���û��ƽ������һ��node����ô�������ϼ�
					if (i + 1 < node.parentNode.childNodes.length) {
						//���¼�����Ҳ�����ƽ��
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
						//���ϼ�
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
		//'multiple':��ѡ; 'single':��ѡ; 'cascade':������ѡ 
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
		
		var nodeRealSelectedSet = {};//��Ҫ����Ľڵ㡣
		for(var nodeid in nodeSelectedSet){  
		  var selectedNode = nodeSelectedSet[nodeid][0];//��ѡ�еĽڵ�
		  //����¼�û��չ���ڵ㣬�ұ���û�б�ѡ�У����а����¼�ѡ�б�־��
		  if(!nodeSelectedSet[nodeid][2]){
			  nodeRealSelectedSet[nodeid] = selectedNode.text+"(�¼�����):true:false";//�ڵ�����+ �����¼�+ �����Ƿ�ѡ��
			  continue;
		  }
		  if(nodeSelectedSet[nodeid][2]&&!nodeSelectedSet[nodeid][1]){
			  var pNode = selectedNode.parentNode;
			  if(!nodeSelectedSet[pNode.id]||!nodeSelectedSet[pNode.id][1]){//���ڵ㲻����  �� ���ڵ㲻�����¼�
				  nodeRealSelectedSet[nodeid] = selectedNode.text+":false:true";//�ڵ�����+ �������¼�+ ������ѡ��
				  
			  }
			  continue;
		  }
		  //�����¼��ڵ�δ��չ��
		  //��������ظ��İ����¼�
	      if(nodeSelectedSet[nodeid][1]){//�����¼�
			  if(selectedNode.parentNode){//���ϼ��ڵ�
			  	  var pNode = selectedNode.parentNode;
			  	  if(!nodeSelectedSet[pNode.id]||!nodeSelectedSet[pNode.id][1]){//�ϼ�û�б�ѡ�� �� �ϼ��ڵ㲻�����¼�
			  		nodeRealSelectedSet[nodeid] = selectedNode.text+"(ȫ������):true:true";//�ڵ�����+ �����¼�+ �����Ƿ�ѡ��
			  	  }
			  }
	      }else{
	    	  nodeRealSelectedSet[nodeid] = selectedNode.text+":false:true";//�ڵ�����+ �������¼�+ �����Ƿ�ѡ��
	      }
	    } 
		
		//alert( JSON.stringify(nodeRealSelectedSet));
		radow.doEvent('query2',JSON.stringify(nodeRealSelectedSet));
		nodeSelectedSet = {};
	}
	function loopRoot(rootnode){//[�ڵ�����Ƿ�����¼����Ƿ�ѡ�б���]
		  for(var i =0;i<rootnode.childNodes.length ;i++){
			  var cNode = rootnode.childNodes[i];
			  if(cNode.ui.checkbox.checked){
				  nodeSelectedSet[cNode.id]=[cNode,true,true];
			  }else{
				  loopParent(cNode);//��������һ��δ��ѡ�У��ϼ����ĳɲ������¼�
			  }
			  if(cNode.childNodes.length>0){
				  loopRoot(cNode);
			  }else{
				  if(cNode.attributes.tag==1&&!cNode.ui.checkbox.checked){//û���ֶ�չ���¼������� �������¼�     ����û��ѡ��
					  if(cNode.isLeaf()){//��Ҷ�ӽڵ�
						  //nodeSelectedSet[cNode.id]=[cNode,false,false];//[�ڵ���󣬲������¼�������û��ѡ��]
					  }else{//����Ҷ�ӽڵ�
						  nodeSelectedSet[cNode.id]=[cNode,true,false];//[�ڵ���󣬰����¼�������û��ѡ��]
					  }
				  }else if(cNode.attributes.tag!=1&&cNode.ui.checkbox.checked){//�������¼���û��չ���¼�������������ѡ��
					  if(cNode.isLeaf()){//��Ҷ�ӽڵ�
						  
					  }else{//����Ҷ�ӽڵ�
						  loopParent(cNode);
					  }
					  nodeSelectedSet[cNode.id]=[cNode,false,true];
					  
				  }else if(cNode.attributes.tag==1&&cNode.ui.checkbox.checked&&cNode.isLeaf()){//Ҷ�ӽڵ�  �����¼��������������¼���
					  nodeSelectedSet[cNode.id]=[cNode,false,true];
				  }
			  }
		  }
		  
	}
	
	//�����ڵ����ò������¼���
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
	var count = 0;//������
	function doQueryNext() {
		var nextProperty = document.getElementById('nextProperty').value;
		if (nextProperty == "") {
			return;
		}
		var tree = Ext.getCmp("group");
		var node = tree.getRootNode();
		oldSelectIdArray.length = 0;//���
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
		if (window.opener) { //���Chrome������ļ�������
			window.opener.Sure(returnValue);
		} else {
			window.returnValue = returnValue; //����ֵ
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
											style="font-size: 13">����ѡ��</font></td>
										<td style="padding-left: 30"><input type="checkbox"
											id="existsCheckbox" onclick="existsChoose()"
											><font style="font-size: 13">�����¼�</font></td>
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
											        <td><label style="font-size: 12">��λ����</label></td>
											        <td><input type="text" id="nextProperty" size="17"
												            class=" x-form-text x-form-field"
												            onpropertychange="OnInput (this)"></td>
											        <td align="right"><odin:button property="selectOrgsBtn"
													        text="��һ��" handler="doQueryNext" /></td>
										       </tr>
										       <tr>
											        <td width="20"></td>
											        <td align="right"><odin:button property="selectOrgsBtn"
													        text="ȷ��" handler="doQuery"></odin:button></td>
											        <td align="right"><odin:button property="cancelBtn"
													        text="ȡ��"></odin:button></td>
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
			<td><odin:panel title="�߼���ѯ" contentEl="condition"
					property="conditionPanel" collapsed="true" collapsible="true"></odin:panel>
				<div id="condition">
					<odin:groupBox title="������Χ" property="ggBox">
						<table width="100%">
							<tr>
								<td><odin:select2 property="a0160" label="��Ա���"
										codeType="ZB125"></odin:select2></td>
								<td><odin:select2 property="a0163" label="��Ա״̬"
										codeType="ZB126"></odin:select2></td>
								<td>
									<table>
										<tr>
											<odin:numberEdit property="age" label="���� " maxlength="3"
												width="50" />
											<td>��</td>
											<odin:numberEdit property="age1" maxlength="3" width="50" />
										</tr>
									</table>
								</td>
							</tr>
						</table>
						<table width="100%">
							<tr>
								<td width="30%" style="padding-left: 86"><odin:checkbox
										property="female" label="Ů"></odin:checkbox></td>
								<td width="30%" style="padding-left: 112"><odin:checkbox
										property="minority" label="��������"></odin:checkbox></td>
								<td width="30%" style="padding-left: 95"><odin:checkbox
										property="nonparty" label="���й���Ա"></odin:checkbox></td>
							</tr>
						</table>
					</odin:groupBox>
					<odin:groupBox title="���ְ����" property="zwBox">
						<table width="100%">
							<tr>
								<td>
									<table>
										<tr>
											<span style="font: 12px">ְ����</span>
										</tr>
										<tr>
											<td><tags:PublicTextIconEdit codetype="ZB09"
												property="duty" label2="ְ����" readonly="true" width="100" />
											</td>
											<td width="14px"></td>
											<td><tags:PublicTextIconEdit codetype="ZB09"
												property="duty1" readonly="true" width="100" label="��"
												label2="ְ����" /></td>
										</tr>
									</table>
								</td>
								<odin:select2 property="a0219" label="ְ�����" codeType="ZB42"></odin:select2>
							</tr>
							<tr>
								<td>
									<table>
										<tr>
											<span style="font: 12px">����ְ����ʱ��</span>
										</tr>
										<tr>
											<odin:dateEdit property="dutynow" width="100" format="Ymd" />
											<td width="14px"></td>
											<odin:dateEdit property="dutynow1" width="100" format="Ymd"
												label="��" />
										</tr>
									</table>
								<td>
							</tr>
							<tr>

							</tr>
						</table>
					</odin:groupBox>
					<odin:groupBox title="ѧ��" property="xlBox">
						<table width="100%">
							<tr align="left">
								<td>
									<table>
										<tr>
										    <td>
											    <span style="font: 12px">ѧ</span>
										    </td>
										    <td>
											    <span style="font: 12px">��</span>
										    </td>
											<td><tags:PublicTextIconEdit property="edu"
												readonly="true" codetype="ZB64" width="100" label2="ѧ��" /></td>
											<td width="14px"></td>
											<td>
											   <!--  <span style="font: 12px">��</span> -->
										    </td>
											<td><tags:PublicTextIconEdit codetype="ZB64"
												readonly="true" property="edu1" width="100" label2="ѧ��" label="��"/></td>
										</tr>
									</table>
								</td>
								<td width="80%" style="padding-left: 112"><odin:checkbox property="allday"
										 label="ȫ���� "></odin:checkbox></td>
							</tr>
						</table>
					</odin:groupBox>

				</div> <odin:editgrid property="peopleInfoGrid" title="��Ա��Ϣ��"
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
						<odin:gridColumn dataIndex="a0101" width="110" header="����"
							align="center" />
						<odin:gridEditColumn2 dataIndex="a0104" width="100" header="�Ա�"
							align="center" editor="select" edited="false" codeType="GB2261" />
						<odin:gridColumn dataIndex="a0107" width="130" header="��������"
							align="center" editor="text" edited="false" />
						<odin:gridEditColumn2 dataIndex="a0117" width="130" header="����"
							align="center" editor="select" edited="false" codeType="GB3304" />
						<odin:gridEditColumn2 dataIndex="a0141" width="130" header="������ò"
							align="center" editor="select" edited="false" codeType="GB4762" />
						<odin:gridColumn dataIndex="a0192a" width="130" header="������λ��ְ��"
							align="center" />
						<odin:gridEditColumn2 dataIndex="a0148" width="130" header="ְ����"
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
	
	
	//��׼����
	function downLoadTmp() {
		document.getElementById('tabname').value = '��׼����';
		var a0000 = '';
		var a0101 = '';
		var filename = '';
		var j = 0;
		var gridId = "peopleInfoGrid";
		if (!Ext.getCmp(gridId)) {
			odin.error("Ҫչʾ��grid�����ڣ�gridId=" + gridId);
			return;
		}
		var grid = Ext.getCmp(gridId);
		var store = grid.getStore();
		if (store.getCount() == 0) {
			odin.error("û��Ҫչʾ�����ݣ�");
			return;
		}

		var dwbm = document.getElementById('a0201b').value;
		var list = document.getElementById("checkList").value;
		var checkList = 1;
		if (typeof (list) != 'undefined' && list != '')
			doOpenPupWin(encodeURI(encodeURI(contextPath
					+ "/pages/exportexcel/ExpWin1.jsp?dwbm=" + dwbm
					+ "&checkList=" + checkList
					+ "&tmpType=['5', '��׼����']")), "��׼�ļ�", 450,
					210);
		else
///			alert("û��ѡ���κ���Ա���ܵ�����");
			var r=confirm("��ȷ��Ҫչʾ��ǰ������Ա��");
			if (r==true){
				radow.doEvent("chooseall");
				doOpenPupWin(encodeURI(encodeURI(contextPath
					+ "/pages/exportexcel/ExpWin1.jsp?dwbm=" + dwbm
					+ "&checkList=" + checkList
					+ "&tmpType=['5', '��׼����']")), "��׼�ļ�", 450,
					210);
				
			}
			

	}
	
	
	//��׼�Զ���
	function downLoadZdyTmp() {
		document.getElementById('tabname').value='�Զ����׼����';
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
			odin.error("Ҫչʾ��grid�����ڣ�gridId=" + gridId);
			return;
		}
		var grid = Ext.getCmp(gridId);
		var store = grid.getStore();
		if (store.getCount() == 0) {
			odin.error("û��Ҫչʾ�����ݣ�");
			return;
		}
		
		var dwbm = document.getElementById('a0201b').value;
		var list = document.getElementById("checkList").value;
		var checkList = 3;
		
		if (typeof (list) != 'u <input type="button" style="cursor:hand;"  onclick="formSubmit()" value="�����ļ�">ndefined' && list != '')
			doOpenPupWin(encodeURI(encodeURI(contextPath
					+ "/pages/exportexcel/ExpWin1.jsp?dwbm=" + dwbm
					+ "&checkList=" + checkList
					+ "&tmpType="+zdys)), "�Զ����ļ�", 450,
					210);
		else
///			alert("û��ѡ���κ���Ա���ܵ�����");
			var r=confirm("��ȷ��Ҫչʾ��ǰ������Ա��");
			if (r==true){
				radow.doEvent("chooseall");
				doOpenPupWin(encodeURI(encodeURI(contextPath
					+ "/pages/exportexcel/ExpWin1.jsp?dwbm=" + dwbm
					+ "&checkList=" + checkList
					+ "&tmpType="+zdys)), "�Զ����ļ�", 450,
					210);
				
			}
	}
	
	function downLoadTmp2() {
		document.getElementById('tabname').value='��Ƭ����';
		var a0000 = '';
		var a0101 = '';
		var filename = '';
		var j = 0;
		var gridId = "peopleInfoGrid";
		if (!Ext.getCmp(gridId)) {
			odin.error("Ҫչʾ��grid�����ڣ�gridId=" + gridId);
			return;
		}
		
		var grid = Ext.getCmp(gridId);
		var store = grid.getStore();
		if (store.getCount() == 0) {
			odin.error("û��Ҫչʾ�����ݣ�");
			return;
		}

		var dwbm = document.getElementById('a0201b').value;
		var list = document.getElementById("checkList").value;
		var checkList = 2;
		if (typeof (list) != 'undefined' && list != '')
			doOpenPupWin(encodeURI(encodeURI(contextPath
					+ "/pages/exportexcel/ExpWin2.jsp?dwbm=" + dwbm
					+ "&checkList=" + checkList
					+ "&tmpType=['6', '��Ƭ����(ÿ��4��)'],['8','��Ƭ����(ÿ��1��)']")), "�����ļ�", 500,
					210);
		else
///			alert("û��ѡ���κ���Ա���ܵ�����");
			var r=confirm("��ȷ��Ҫչʾ��ǰ������Ա��");
			if (r==true){
				radow.doEvent("chooseall");
				doOpenPupWin(encodeURI(encodeURI(contextPath
					+ "/pages/exportexcel/ExpWin2.jsp?dwbm=" + dwbm
					+ "&checkList=" + checkList
					+ "&tmpType=['6', '��Ƭ����(ÿ��4��)'],['8','��Ƭ����(ÿ��1��)']")), "�����ļ�", 500,
					210);
				
			}

	}
	
	function downLoadTmp4() {
		document.getElementById('tabname').value='�Զ�����Ƭ����';
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
			odin.error("Ҫչʾ��grid�����ڣ�gridId=" + gridId);
			return;
		}
		var grid = Ext.getCmp(gridId);
		var store = grid.getStore();
		if (store.getCount() == 0) {
			odin.error("û��Ҫչʾ�����ݣ�");
			return;
		}
		
		var dwbm = document.getElementById('a0201b').value;
		var list = document.getElementById("checkList").value;
		var checkList = 4;
		if (typeof (list) != 'u <input type="button" style="cursor:hand;"  onclick="formSubmit()" value="�����ļ�">ndefined' && list != '')
			doOpenPupWin(encodeURI(encodeURI(contextPath
					+ "/pages/exportexcel/ExpWin2.jsp?dwbm=" + dwbm
					+ "&checkList=" + checkList
					+ "&tmpType="+zdys)), "�Զ����ļ�", 450,
					210);
		else
///			alert("û��ѡ���κ���Ա���ܵ�����");
			var r=confirm("��ȷ��Ҫչʾ��ǰ������Ա��");
			if (r==true){
				radow.doEvent("chooseall");
				doOpenPupWin(encodeURI(encodeURI(contextPath
					+ "/pages/exportexcel/ExpWin2.jsp?dwbm=" + dwbm
					+ "&checkList=" + checkList
					+ "&tmpType="+zdys)), "�Զ����ļ�", 450,
					210);
				
			}			
		
	}
	
	/**
	 * �����ǼǱ��
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
			odin.error("Ҫ������grid�����ڣ�gridId=" + gridId);
			return;
		}
		var grid = Ext.getCmp(gridId);
		var store = grid.getStore();
		if (store.getCount() == 0) {
			odin.error("û��Ҫ���������ݣ�");
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
			odin.error("��ѡ��Ҫ�������У�");
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
			filename = filename.substring(0, filename.length - 1)+'��';
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
				+ "/pages/exportexcel/djbLoad.jsp?download=true&filename="+filename)), "�����ļ�", 600, 200);
	}
	 
	function ml(a0000,allName){
		document.getElementById('a0000').value = a0000;
		document.getElementById('a0101').value = allName;
		doOpenPupWin(encodeURI(encodeURI(contextPath
				+ "/pages/exportexcel/ExpTempDjbWindow.jsp")), "�����ļ�", 600, 520);
	}
	
	//�ǼǱ�༭����
	function editFile(){
		
		var value = getValue();
		var values = value.split("@");
		
		if (values[0] == '') {
			alert("��ѡ��Ҫ�ϴ���������Ա��¼��");
			return;
		}
		if(values[3] > 1){
			alert("ֻ��ѡ��һ����Ա��¼��");
			return;
		}
		var name = values[1]+"@"+values[2];
		
		var win = odin.ext.getCmp('simpleExpWin');
		win.setTitle('���봰��');
		odin.showWindowWithSrc('simpleExpWin', contextPath
				+ "/pages/search/EditFile.jsp?flag=1&uuid="+values[0]+"&uname="+name);
		
	}
	
	//�鿴/ɾ������
	function modifyFile(){
		
		var value = getValue();
		var values = value.split("@");
		
		if (values[0] == '') {
			alert("��ѡ����Ա��¼��");
			return;
		}
		if(values[3] > 1){
			alert("ֻ��ѡ��һ����Ա��¼��");
			return;
		}
		
		radow.doEvent("modifyAttach",values[0]+"@1");
		
	}
	
	//��ȡҳ������
	function getValue(){
		
		var gridId = "peopleInfoGrid";
		var grid = Ext.getCmp(gridId);
		var store = grid.store;
		var count = 0;
		var a0000 ='';//��Ա���
		var a0101 = '';//��Ա����
		var a0184 = '';//��Ա���֤��
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
	 * �����ǼǱ��
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
			odin.error("Ҫ������grid�����ڣ�gridId=" + gridId);
			return;
		}
		var grid = Ext.getCmp(gridId);
		var store = grid.getStore();
		if (store.getCount() == 0) {
			odin.error("û��Ҫ���������ݣ�");
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
			odin.error("��ѡ��Ҫ�������У�");
			return;
		}
		
		a0101 = a0101.substring(0, a0101.length - 1);
		a0000 = a0000.substring(0, a0000.length - 1);
		radow.doEvent('checkPer',a0000+"@"+a0101);
		
	}	
	//��Ա�����޸Ĵ��ڴ���
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
								listeners : {//�ж�ҳ���Ƿ���ģ�

								},
								closable : true
							}).show();

		}
	}

//���ܱ�
function expHzb(){
		var a0000 = '';
		var a0101 = '';
		var gridId = "peopleInfoGrid";
		if (!Ext.getCmp(gridId)) {
			odin.error("Ҫ������grid�����ڣ�gridId=" + gridId);
			return;
		}
		var grid = Ext.getCmp(gridId);
		var store = grid.getStore();
		var fileName = "����Ա�ǼǱ������ܱ�.doc";
		if (store.getCount() == 0) {
			odin.error("û��Ҫ���������ݣ�");
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
			odin.error("��ѡ��Ҫ�������У�");
			return;
		}
	
		a0101 = a0101.substring(0, a0101.length - 1);
		a0000 = a0000.substring(0, a0000.length - 1);
	
		window.location="<%=request.getContextPath()%>/FiledownServlet?downLoad=true&excelType=7&fileName="+ fileName +"&a0000="+ a0000 +"&a0101="+ a0101;
	}

	Ext.onReady(function() {
		//ҳ���������ȡ�߶�
		var vheight = Ext.getBody().getViewSize().height;
		var sheight = objTop(document.getElementById('forView_peopleInfoGrid'))[0];
		vheight = (vheight == 0)?548:vheight;
		var treeheight = (sheight == 0)?70:sheight;
		sheight = (sheight == 0)?120:sheight;
		
		//��ȡ���
		var vwidth = Ext.isIE?Ext.getBody().getViewSize().width:window.innerWidth;
		var swidth = objTop(document.getElementById('forView_peopleInfoGrid'))[1];
		vwidth = (vwidth == 0)?1115:vwidth;
		swidth = (swidth == 0)?260:swidth;
		var cwidth = document.body.clientWidth;
		cwidth = (cwidth == 0)?1115:cwidth;
		
		//���ø߿�
		Ext.getCmp('peopleInfoGrid').setHeight(vheight-sheight-4);//����grid���߶�
		Ext.getCmp('peopleInfoGrid').setWidth(vwidth-swidth-2+16);//����grid�����
		document.getElementById("tree-div").style.height =(vheight-treeheight-4)-63;//�������߶�
		document.getElementById('groupTreePanel').style.width = cwidth+16;//���ù��������
		
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
<odin:menuItem text="���ɵǼǱ�" property="createDjb" handler="createExcelTemp" ></odin:menuItem>
<odin:menuItem text="�鿴�ǼǱ�" property="getDjb" handler="expExcelTemp" isLast="true"></odin:menuItem>
</odin:menu>

<odin:menu property="bzbc">
<odin:menuItem text="��׼����" property="createbzbcBtn" handler="downLoadTmp"></odin:menuItem>
<odin:menuItem text="�Զ����׼����" property="createzdyBtn" handler="downLoadZdyTmp" isLast="true"></odin:menuItem>
</odin:menu> 

<odin:menu property="zpmc">
<odin:menuItem text="��Ƭ����" property="zpbc2" handler="downLoadTmp2" ></odin:menuItem>
<odin:menuItem text="�Զ�����Ƭ����" property="zpzdy" handler="downLoadTmp4" isLast="true"></odin:menuItem>
</odin:menu>

</script>
<odin:window src="/blank.htm" id="simpleExpWin" width="560" height="350" maximizable="false" title="�ϴ���������"></odin:window>
<odin:window src="/blank.htm" id="modifyFileWindow" width="560" height="300" maximizable="false" title="�鿴/ɾ����������"></odin:window>
<odin:window src="/blank.htm" id="othertem" width="300" height="200" maximizable="false" title="ѡ����ģ��"></odin:window>
<odin:hidden property="ddd"/>


