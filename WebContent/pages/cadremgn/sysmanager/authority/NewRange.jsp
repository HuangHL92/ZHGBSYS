<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@page
	import="com.insigma.siis.local.pagemodel.sysorg.org.SysOrgPageModel"%>
<%@page
	import="com.insigma.siis.local.pagemodel.sysmanager.group.GroupManagePageModel"%>
<%@include file="/comOpenWinInit2.jsp"%>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>

<%
	String ctxPath = request.getContextPath();
	String tablename=(String)request.getParameter("tablename");
	//System.out.println(tablename);
%>
<style>
.x-panel-body {
	height: 95%
}

.x-panel-bwrap {
	height: 100%
}

.picOrg {
	background-image: url(<%=request.getContextPath()%>/pages/sysorg/org/images/companyOrgImg2.png)
		!important;
}

.picInnerOrg {
	background-image: url(<%=request.getContextPath()%>/pages/sysorg/org/images/insideOrgImg1.png)
		!important;
}

.picGroupOrg {
	background-image: url(<%=request.getContextPath()%>/pages/sysorg/org/images/groupOrgImg1.png)
		!important;
}

td {
	border: 0px solid red;
}
</style>
<odin:hidden property="tablename" value="<%=tablename %>" />
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript">
	var num = 1;
	var b0111s;
	var nodeChecked='';
	var bhxjNodes ='';
	var qxxjNodes ='';
	var nodeTemp=new Array();
	var nodes = new Array();
	Ext.onReady(function() {
				
				var b0111 = "";
				var ss = document.getElementById('subWinIdBussessId').value;
				var v = ss.substring(ss.length - 2);
				var user;
				if (v == '&1') {
					user = ss.substring(0, ss.length - 2);
				} else {
					user = ss;
				}
				var man = document.getElementById('manager').value;
				var Tree = Ext.tree;
				var tablename = document.getElementById('tablename').value;
				var eventNames;
				
				if(tablename=='competence_userdept')
					eventNames='orgTreeJsonNewArray';
				/* else if(tablename=='competence_userdept_look')
					eventNames='orgTreeJsonNewArrayLook'; */
				
				var tree = new Ext.tree.TreePanel(
						{
							id : 'tree1',
							region : 'center',
							el : 'tree-div',
							//True��ʾΪ����ǿ������ģ����Զ���Ⱦһ��չ��/�������ֻ���ť��ͷ��������    
							collapsible : true,
							/* title: '����',//�����ı�  */
							/* width: 100, */
							border : false,//���    
							autoScroll : true,//�Զ�������    
							animate : true,//����Ч��    
							rootVisible : false,//���ڵ��Ƿ�ɼ�    
							split : true,
							checkModel : 'single',
							/* tbar:new Ext.Toolbar({items: [{  xtype: "checkbox",
							    boxLabel : "�����¼�",
							    id:'isContain',
							    checked:true,
							    hidden:true
							 }],
							height:25,
							layout :'column'}), */
							loader : new Tree.TreeLoader(
									{
										dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysorg.org.PublicWindow&eventNames='+eventNames,
										baseParams : {
											userid : user
										}
									}),
							listeners : {
								scope : this,
								checkchange : function(node, checked) {
									//ѡ��ڵ�ʱ �������ӽڵ� ��չ��������Ӧ���¼��ڵ���Ϣ 
									b0111s = document.getElementById('b0111s').value;
									//ƴ���ַ���������̨
// 									function montageStr(node,checked)
										var id=node.id;
										if($("#includeSub").is(':checked')==true){
											//��ѡ�����¼�
											nodeChecked = id+':'+checked;
											bhxjNodes +=nodeChecked+',';
											nodeTemp.push(node);
										}else{
											//û�й�ѡ�����¼�
											nodeChecked = id+':'+checked;
											qxxjNodes += nodeChecked+',';
										}
									
									nodes = b0111s.split(",");
									
									if (document.getElementById('includeSub').value == "true") {
										loop(node);
// 										node.expand();
// 										showChildNode(node);
									}
									var childNode =node.id;
									if (node.attributes.checked) {
										if (nodes.indexOf(childNode) == -1) {
											nodes.push(childNode)
											document.getElementById("b0111s").value = nodes;
											b0111s = document.getElementById('b0111s').value;
										}
										//addAttributes(node);
									} else {
										var index = nodes.indexOf(childNode);
										
										if (index != -1) {
										
											nodes.splice(index, 1);
											document.getElementById("b0111s").value = nodes;
											}
										//addAttributes(node);
									}
									//console.log("nodes11: " + nodes)
									
									document.getElementById('isFirst').value = '';
								
								},
								'expandnode' : function(node) {
									//��ȡչ���ڵ����Ϣ
									//console.log(node);
// 									alert(bhxjNodes);
// 									alert(nodeTemp.length);
									//�����û��Ĳ���ȥ���õ�һ��չ���¼�ʱ�¼��Ƿ�ѡ,ԭ���Ǵ����ݿ��ѯ
									for(var n=0;n<nodeTemp.length;n++){
										if(node.id=nodeTemp[n].id){
											if (node.attributes.checked == true) {
										         for (var i = 0; i < node.childNodes.length; i++) {
													node.childNodes[i].ui.checkbox.checked = true;
													node.childNodes[i].attributes.checked = true;
												}
											}else{
												 for (var i = 0; i < node.childNodes.length; i++) {
														node.childNodes[i].ui.checkbox.checked = false;
														node.childNodes[i].attributes.checked = false;
													}
											}
										}
									}
									
									var ss = document.getElementById('subWinIdBussessId').value;
									b0111s = document.getElementById("b0111s").value;
									nodes = b0111s.split(",");
									if (ss.substring(ss.length - 2) == '&1'
											&& document.getElementById('isFirst').value == '1') {
										}
									if (document.getElementById('includeSub').value == "true") {
										if(document.getElementById('isFirst').value == ''){
// 											showChildNode(node)
										}
										if (node.attributes.checked == true) {
									         for (var i = 0; i < node.childNodes.length; i++) {
												node.childNodes[i].ui.checkbox.checked = true;
												node.childNodes[i].attributes.checked = true;
											}

											if (nodes.indexOf(node.id) == -1) {
												nodes.push(node.id);
												document.getElementById("b0111s").value = nodes;
											}
											for (var i = 0; i < node.childNodes.length; i++) {

												if (node.childNodes[i].attributes.checked) {
													var childNode = node.childNodes[i].id;
													if (nodes.indexOf(childNode) == -1) {
														nodes.push(childNode);
														document.getElementById("b0111s").value = nodes;
													}
												}

											}
											//console.log("nodess:" + nodes)
										} else {
										    for (var i = 0; i < node.childNodes.length; i++) {
												node.childNodes[i].ui.checkbox.checked = false;
												node.childNodes[i].attributes.checked = false;
												}
											var childNode =node.id ;
											var index = nodes.indexOf(childNode);
											if (index != -1) {
												nodes.splice(index, 1);
												document.getElementById("b0111s").value = nodes;
											}
											for (var i = 0; i < node.childNodes.length;i++) {
												if (!node.childNodes[i].attributes.checked) {
													var childNode =node.childNodes[i].id ;
													var index = nodes.indexOf(childNode);
													
													if (index != -1) {
														nodes.splice(index, 1);
														document.getElementById("b0111s").value = nodes;
													}
												}
											}
										}

									}
								},
								'append' : function(tree, node, node) {
									if (!node.isLeaf() && node.getDepth() < 2) {
										node.expand();
									}
								}
							}
						});
				treechecked = tree;
				
				var root_id = '-1';
				// document.getElementById('ereaid').value=root_id;
				var root = new Tree.AsyncTreeNode({
					text : '',
					iconCls : document.getElementById('picType').value,
					draggable : false,
					id : root_id
				/* ,
					      href:"javascript:radow.doEvent('querybyid','"+root_id+"')" */
				});
				tree.setRootNode(root);
				tree.render();
				//root.expand(false,true, callback);
				//root.expand((document.getElementById('subWinIdBussessId').value)?true:false,true, callback);
			});

	//001.001:true:1   true��ʾѡ��checked   1��ʾ�����¼�like
	
	
	
	
	
	
	function loop(node) {
		var isContain = true;
		if (node.ui.checkbox.checked == true) {
			if (isContain) {
				//node.attributes.tag="1";
				node.ui.checkbox.checked = true;
				node.attributes.checked = true;
				if (node.childNodes.length > 0) {
					for (var i = 0; i < node.childNodes.length; i++) {
						//node.childNodes[i].attributes.tag="1";
						node.childNodes[i].ui.checkbox.checked = true;
						node.childNodes[i].attributes.checked = true;
						loop(node.childNodes[i]);
					}
				}
			} else {
				//node.attributes.tag="2";
				node.ui.checkbox.checked = true;
				node.attributes.checked = true;
			}
		}
		if (node.ui.checkbox.checked == false) {
			//node.attributes.tag="1";
			node.ui.checkbox.checked = false;
			node.attributes.checked = false;
			for (var i = 0; i < node.childNodes.length; i++) {
				//node.childNodes[i].attributes.tag="1";
				node.childNodes[i].ui.checkbox.checked = false;
				node.childNodes[i].attributes.checked = false;
				loop(node.childNodes[i]);
			}
		}
	}


	var callback = function(node) {
		if (node.hasChildNodes()) {
			node.eachChild(function(child) {
				child.expand();
			})
		}
	}

	function reloadTree() {
		var tree = Ext.getCmp("group");
		//tree.root.reload();
		//var path =tree.getPath(groupid);
		//tree.collapseAll();
		//tree����  
		//��ȡѡ�еĽڵ�  
		var node = tree.getSelectionModel().getSelectedNode();
		if (node == null) { //û��ѡ�� ������  
			tree.root.reload();
		} else { //������ ��Ĭ��ѡ���ϴ�ѡ��Ľڵ�    
			var path = node.getPath('id');
			tree.getLoader().load(tree.getRootNode(), function(treeNode) {
				tree.expandPath(path, 'id', function(bSucess, oLastNode) {
					tree.getSelectionModel().select(oLastNode);
				});
			}, this);
		}
	}

	function goTo() {
		var grid = Ext.getCmp('persongrid2');
		var first = grid.getStore().getCount();
		if (first == 0) {
			alert("���Ȳ�ѯ����Ա�����ܽ��ж�λ����");
			return;
		}
		var value = document.getElementById("seachName").value;
		if (!value) {
			return;
		}
		var record = grid.getSelectionModel().getSelected();
		if (record) {
			var seletedGird = grid.getSelectionModel().getSelections();//���ѡ�е���
			var num = grid.getStore().indexOf(seletedGird[0]);//���ѡ�еĵ�һ����store�ڵ��к�
			radow.doEvent("goToByName", num + "|" + value);
		} else {
			radow.doEvent("goToByName", value);
		}
	}

	function getTextValue() {
		var value = document.getElementById("seachName").value;
		if (value) {
			return value;
		} else {
			return;
		}
	}

	function deletePeople() {
		radow.doEvent("deletePerson");
	}

	<odin:menu property="deleteM">
	<odin:menuItem text="ɾ��" handler="deletePeople" isLast="true"></odin:menuItem>
	</odin:menu>
</script>

<%
	SysOrgPageModel sys = new SysOrgPageModel();
	String picType = (String) (sys.areaInfo.get("picType"));
	String ereaname = (String) (sys.areaInfo.get("areaname"));
	String ereaid = (String) (sys.areaInfo.get("areaid"));
	String manager = (String) (sys.areaInfo.get("manager"));
	String areaname = (String) new GroupManagePageModel().areaInfo.get("areaname");
	String checktime = (String) request.getAttribute("checktime");
%>

<odin:hidden property="sql" />
<odin:hidden property="checkList" />
<odin:hidden property="b0111OrimpID" />
<!-- ��ҳ�洫�����ҪУ��ĵ�λ���� -->
<odin:checkbox property="includeSub" value="false"
	onclick="inclubdeClick()" label="�����¼�"></odin:checkbox>
<odin:toolBar property="float" applyTo="toolDiv">
	<odin:commformtextForToolBar text="" property="text11"></odin:commformtextForToolBar>
	<odin:fill />
	<odin:buttonForToolBar text="��Ȩ" handler="saveSystem" id="btn1"
		icon="images/icon/query.gif" isLast="true"></odin:buttonForToolBar>
</odin:toolBar>

<div style="height: 100%; width: 100%">
	<div id="toolDiv"></div>
	<table cellspacing="0" align="center">
		<tr>
			<div style="height: 5px"></div>
		</tr>
		<tr>
			<td valign="top" width="50px" id="tdtree">
				<div id="tree-div"
					style="overflow: auto; height: 467px; width: 400px; left: 10px">
				</div>
			</td>
		</tr>
	</table>
</div>
<odin:hidden property="area" value="<%=areaname%>" />
<odin:hidden property="ereaname" value="<%=ereaname%>" />
<odin:hidden property="ereaid" value="<%=ereaid%>" />
<odin:hidden property="manager" value="<%=manager%>" />
<odin:hidden property="picType" value="<%=picType%>" />
<odin:hidden property="b0111Like" />
<odin:hidden property="ping" />
<odin:hidden property="contain" />
<odin:hidden property="userid" />
<odin:hidden property="sign" />
<odin:hidden property="isFirst" />
<odin:hidden property="b0111s" />
<script type="text/javascript">
	Ext.onReady(function() {
		document.getElementById('toolDiv').style.width = document.body.clientWidth+'px';
	});

	//��ѡ��״̬�ı�ʱ �޸Ķ�Ӧ��value�������¼���ѡ��
	function inclubdeClick() {
		if (document.getElementById('includeSub').value == "false") {
			document.getElementById('includeSub').value = "true";
		} else {
			document.getElementById('includeSub').value = "false"
		}
	}
	//�ݹ�չ�����нڵ� ����ȡ�ڵ��attributes.checkedֵ
	function addAttributes(node) {
		var childNode = node.id;
	    nodes = document.getElementById("b0111s").value.split(",");
		if (node.hasChildNodes()) {
			for (var i = 0; i < node.childNodes.length; i++) {
				childNode = node.childNodes[i].id;
				if (node.childNodes[i].attributes.checked) {
					if (nodes.indexOf(childNode) == -1) {
						nodes.push(childNode);
						document.getElementById("b0111s").value = nodes;
					}
				}else{
					var index = nodes.indexOf(childNode);
					if (index != -1) {
						nodes.splice(index, 1);
						document.getElementById("b0111s").value = nodes;
						}
					}
				//�ݹ����
				addAttributes(node.childNodes[i]);
			}
		} else{
			if (node.attributes.checked) {
				if (nodes.indexOf(childNode) == -1) {
					nodes.push(childNode);
					document.getElementById("b0111s").value = nodes;
				}
			}else{
				var index = nodes.indexOf(childNode);
				if (index != -1) {
					nodes.splice(index, 1);
					document.getElementById("b0111s").value = nodes;
					}
				}
		}
		
	}
// 	//չ���ӽڵ�
	function showChildNode(node) {
			var childnodes = node.childNodes;
			for (var i = 0; i < childnodes.length; i++) { //�ӽڵ���ȡ���ӽڵ����α���
				var rootnode = childnodes[i];
				if(node.hasChildNodes()){
					rootnode.expand();
				}
				//alert(result); //LJ����������-LJ
				/* if (rootnode.childNodes.length > 0) {
					showChildNode(rootnode); //��������ӽڵ�  �ݹ�
				} */
			}
	}

	function findchildnode(node) {
		var childnodes = node.childNodes;
		for (var i = 0; i < childnodes.length; i++) { //�ӽڵ���ȡ���ӽڵ����α���
			var rootnode = childnodes[i];
			//alert(result); //LJ����������-LJ
			if (rootnode.childNodes.length > 0) {
				findchildnode(rootnode); //��������ӽڵ�  �ݹ�
			}
		}
	}

	function saveSystem() {
		
		var sb='';
		if(bhxjNodes=='')
			bhxjNodes='&';
		if(qxxjNodes=='')
			qxxjNodes='&';
		sb=bhxjNodes+'#@#'+qxxjNodes;
		radow.doEvent('saveTree',sb);
	}

	function ShowCellCover(elementId, titles, msgs) {
		Ext.MessageBox.buttonText.ok = "�ر�";
		if (elementId.indexOf("start") != -1) {

			Ext.MessageBox.show({
				title : titles,
				msg : msgs,
				width : 300,
				height : 300,
				closable : false,
				modal : true,
				progress : true,
				wait : true,
				animEl : 'elId',
				increment : 5,
				waitConfig : {
					interval : 150
				}
			});
		}
	}
	//��ȡurl����
	function getQueryString(name) {
		var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
		var r = window.location.search.substr(1).match(reg);
		if (r != null) {
		return unescape(r[2]);
		}
		return null;
	}
</script>