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
<%
	String areaname = (String) new GroupManagePageModel().areaInfo
			.get("areaname");
%>
<script type="text/javascript" src="<%=request.getContextPath() %>/basejs/helperUtil.js"></script>
<%-- <odin:toolBar property="btnToolBar" applyTo="groupTreePanel">
	<odin:fill />
	<odin:buttonForToolBar text="����" id="clear"
		icon="images/sx.gif" cls="x-btn-text-icon" isLast="true"/>
	<odin:separator />
	<odin:buttonForToolBar text="�½��Զ���ͳ��" icon="image/u175.png" id="zdytj"  isLast="true" handler="neworgid" />	
</odin:toolBar> --%>
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
<!-- �������Ĺ������� -->
<script type="text/javascript">
    function tytj(param){
    	var title = param.split('&')[1];
		var data = param.split('&')[0];
	    $h.openWin('GeneralStatistics','pages.statisticalanalysis.simpleanalysis.GeneralStatistics',title+'ͨ��ͳ�Ʊ༭',700,500,data,'<%=request.getContextPath()%>');
    }
	function tjfx(param){
		var title = param.split('&')[1];
		var data = param.split('&')[0];
		$h.openWin('SimpleStatistics','pages.sysorg.org.SimpleStatistics',title+'ͨ��ͳ��ͼ',1010,650,data,'<%=request.getContextPath()%>');
	}
	function tjfx2(param){
		var title = param.split('&')[1];
		var data = param.split('&')[0];
		$h.openWin('TwoDStatisticsShow','pages.statisticalanalysis.simpleanalysis.TwoDStatisticsShow',title+'��άͳ��ͼ',997,645,data,'<%=request.getContextPath()%>');
	}
	function fixed_query_tj(param){
		var title = param.split('&')[1];
		var data = param.split('&')[0];
		$h.openWin('Gbjbqk','pages.sysorg.org.Gbjbqk',title+'��άͳ��',1300,550,data,'<%=request.getContextPath()%>');
	}
	function fixed_query_nv_tj(param){
		var title = param.split('&')[1];
		var data = param.split('&')[0];
		$h.openWin('Gbjbqknv','pages.sysorg.org.gbjbqk.Gbjbqknv',title+'��άͳ��',1300,550,data,'<%=request.getContextPath()%>');
	}
	function fixed_query_shao_tj(param){
		var title = param.split('&')[1];
		var data = param.split('&')[0];
		$h.openWin('Gbjbqkshao','pages.sysorg.org.gbjbqk.Gbjbqkshao',title+'��άͳ��',1300,550,data,'<%=request.getContextPath()%>');
	}
	function fixed_query_fei_tj(param){
		var title = param.split('&')[1];
		var data = param.split('&')[0];
		$h.openWin('Gbjbqkfei','pages.sysorg.org.gbjbqk.Gbjbqkfei',title+'��άͳ��',1300,550,data,'<%=request.getContextPath()%>');
	}
	function fixed_query_xwxl_tj(param){
		var title = param.split('&')[1];
		var data = param.split('&')[0];
		$h.openWin('Gbjbqkxwxl','pages.sysorg.org.gbjbqk.Gbjbqkxwxl',title+'��άͳ��',1300,550,data,'<%=request.getContextPath()%>');
	}
	function fixed_query_nl_tj(param){
		var title = param.split('&')[1];
		var data = param.split('&')[0];
		$h.openWin('Gbjbqknl','pages.sysorg.org.gbjbqk.Gbjbqknl',title+'��άͳ��',1300,550,data,'<%=request.getContextPath()%>');
	}
	function fixed_query_xzcc_tj(param){
		var title = param.split('&')[1];
		var data = param.split('&')[0];
		$h.openWin('Gbjbqkxzcc','pages.sysorg.org.gbjbqk.Gbjbqkxzcc',title+'��άͳ��',1300,550,data,'<%=request.getContextPath()%>');
	}
	//ͳ�Ʒ���
	function fixed_query(param){
		var title = param.split('&')[1];
		var data = param.split('&')[0];
		$h.openWin('TwoDimensionStatistics','pages.sysorg.org.TwoDimensionStatistics',title+'��άͳ��',1013,650,data,'<%=request.getContextPath()%>');
	}
	function ewedit(param){
		var title = param.split('&')[1];
		var data = param.split('&')[0];
		$h.openWin('TwoDStatistics','pages.statisticalanalysis.simpleanalysis.TwoDStatistics',title+'��άͳ�Ʊ༭',1000,650,data,'<%=request.getContextPath()%>');
	}
</script>
<script type="text/javascript">
/* var node0 = new Ext.tree.TreeNode({ text:"������Ϣ��",id:"j" }); */
/* var node1 = new Ext.tree.TreeNode({text:"�ɲ�ͳ����",id:"g"}); */
var node1 = new Ext.tree.TreeNode({text:"������Ϣͳ��",id:"g"});
 
/* var node2 = new Ext.tree.TreeNode({text:"������",id:"q" }); */
var node3 = new Ext.tree.TreeNode({ text:"�Զ���ͳ����",id:"z" });


	Ext.onReady(function(){
		 var  treepanel = new Ext.tree.TreePanel({
			el:"tree_div1",
			bodyStyle: 'background-color:white;',
			height:488,
			useArrows:true,
			autoScroll:true,
			animate:true,
			enableDD:false,
			containerScroll: true,
			border : false,
			rootVisible: false
		
			
		});
		var rootNode1=new Ext.tree.TreeNode({text:"��"});
	     	
	    treepanel.setRootNode(rootNode1);
	    /* rootNode1.appendChild(node0); */
		rootNode1.appendChild(node1);  
	    /* rootNode1.appendChild(node2); */
	    
	    treepanel.render();
	    rootNode1.expand(false,true, callback);
	   // rootNode1.expand();
	    treepanel.on('dblclick', treedbClick1);
	    function treedbClick1(node,e){
	    	document.getElementById("checknodeid1").value=node.id;
	    	//alert(document.getElementById("checknodeid").value);
	    	if(node.id!="j"&&node.id!="g"&&node.id!="q"){
	    		cyorgid(1);
	    	}
	    }
	 });
	var callback = function (node){
		if(node.hasChildNodes()) {
			node.eachChild(function(child){
				child.expand();
				/* child.getUI().toggleCheck(node.attributes.checked); 
				child.attributes.checked = node.attributes.checked;  
				mytoggleChecked(child); */
			})
		}
	}
	function addChild1(i,name,nid,pid){
	  		var n0;
			if(pid=='1'){
				n0=new Ext.tree.TreeNode({text:name,id:nid+","+pid,icon:"image/u175.png"});
			}
			if(pid=='2'){
				n0=new Ext.tree.TreeNode({text:name,id:nid+","+pid,icon:"image/u333.png"});
			}
			/* if(i=='0'){
				node0.appendChild(n0);
			} */
			if(i=='1'){
				//var n1=new Ext.tree.TreeNode({text:name,id:nid,icon:ic});
				node1.appendChild(n0);
			}	
			/* if(i=='2'){
				//var n2=new Ext.tree.TreeNode({text:name,id:nid,icon:ic});
				node2.appendChild(n0);
			} */
			if(i=='3'){
				node3.appendChild(n0);
			}
	}
	
	
	Ext.onReady(function(){
		 var  treepane2 = new Ext.tree.TreePanel({
			el:"tree_div2",
			bodyStyle: 'background-color:white;',
			height:488,
			useArrows:true,
			autoScroll:true,
			animate:true,
			enableDD:false,
			containerScroll: true,
			border : false,
			rootVisible: false
		});
		var rootNode2=new Ext.tree.TreeNode({text:"��"});
	     	
	    treepane2.setRootNode(rootNode2);
	    rootNode2.appendChild(node3);
	    
	    treepane2.render();
	    rootNode2.expand();
	    treepane2.on('dblclick', treedbClick2);
	    function treedbClick2(node,e){
	    	document.getElementById("checknodeid2").value=node.id;
	    	//alert(document.getElementById("checknodeid").value);
	    	if(node.id!="z"){
	    		cyorgid(2);
	    	}
	      
	    };
	    treepane2.on('click',treeClick);
	    function treeClick(node,e){
	    	alert("���ܻ�δ��������ȴ���");
	    	return;
	    	document.getElementById("checknodeid2").value=node.id;
	    	var mark = node.id.split(",")[0];
	   		if(node.id!="z"){
	   			document.getElementById("tree_div2").style.height='460px';
	   			document.getElementById("btn_tr").style.display='';
	   			document.getElementById("del").style.display='';
		    	document.getElementById("edit").style.display='';
		    	//document.getElementById("starts").style.display='';
		    	document.getElementById("abc").style.width='113px';
	   		}else{
	   			document.getElementById("tree_div2").style.height='490px';
	   			document.getElementById("btn_tr").style.display='none';
	   			document.getElementById("del").style.display='none';
		    	document.getElementById("edit").style.display='none';
		    	//document.getElementById("starts").style.display='none';
		    	document.getElementById("abc").style.width='169px'; 
	   		}
	    }
	 });

	function addChild2(i,name,nid,pid){
 	 var n0;
		if(pid=='1'){
			n0=new Ext.tree.TreeNode({text:name,id:nid+","+pid,icon:"image/u175.png"});
		}
		if(pid=='2'){
			n0=new Ext.tree.TreeNode({text:name,id:nid+","+pid,icon:"image/u333.png"});
		}
		/* if(i=='0'){
			node0.appendChild(n0);
		} */
		if(i=='1'){
			//var n1=new Ext.tree.TreeNode({text:name,id:nid,icon:ic});
			node1.appendChild(n0);
		}	
		/* if(i=='2'){
			//var n2=new Ext.tree.TreeNode({text:name,id:nid,icon:ic});
			node2.appendChild(n0);
		} */
		if(i=='3'){
			node3.appendChild(n0);
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
		}else {
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
	Ext.onReady(function() {
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
					try{
						if (node.attributes.href.indexOf('��û��Ȩ��') > 0) {
							node.getUI().checkbox.checked = false;
							alert('��û��Ȩ��');
						}
					}catch(err){
						
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
				//root.expand();
				 root.expand(false,true, callback1);
				var tree = Ext.getCmp("group");
			});
	var callback1 = function (node){
		if(node.hasChildNodes()) {
			node.eachChild(function(child){
				child.expand();
			})
		}
	}
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
									'><span unselectable="on" title="'+n.text+'">',
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
		alert( JSON.stringify(changeNode));
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
	
	function cyorgid(param){ //ͳ�ƻ�ȡ����ID
		radow.doEvent('cytj',orgid()+"@1@"+param);
		nodeSelectedSet = {};//????s
	}
	
	function editororgid(){ //�༭��ȡ����ID
		radow.doEvent('cytj',orgid()+"@2");
		nodeSelectedSet = {};
	}
	function neworgid(){ //�½��Զ���ͳ�ƻ�ȡ����ID
		radow.doEvent('cytj',orgid()+"@3");
		nodeSelectedSet = {};
	}
	
	function orgid(){ //��ȡ����ID
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
		var js_tj=JSON.stringify(nodeRealSelectedSet);
		if(js_tj=='{}'||js_tj==null){//Ϊ��Ĭ��ȫ��ѡ��
			var cNode = rootNode.childNodes[0];
			//console.log(nodeSelectedSet);
			nodeSelectedSet[cNode.id]=[cNode,true,true];
			//console.log(nodeSelectedSet);
			for(var nodeid in nodeSelectedSet){
				var selectedNode = nodeSelectedSet[nodeid][0];//��ѡ�еĽڵ�
				nodeRealSelectedSet[nodeid] = selectedNode.text+"(ȫ������):true:true";//�ڵ�����+ �����¼�+ �����Ƿ�ѡ��
			}
			js_tj=JSON.stringify(nodeRealSelectedSet);
		}
		return js_tj;
		
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
	function reload(){
		radow.doEvent('reload');
	}
	//Ĭ��ѡ�а����¼�
	Ext.onReady(function() {
		var existsCheckbox = document.getElementById('existsCheckbox');
		existsCheckbox.checked = true;
	});
	function setHeight(){
		try{
			var h_screen=document.body.offsetHeight;
			var tree_div_dom=document.getElementById("tree-div");
			tree_div_dom.style.height=parseInt(h_screen)-40;
			tree_div_dom.firstChild.style.height=parseInt(h_screen)-45;
			tree_div_dom.firstChild.firstChild.style.height=parseInt(h_screen)-45;
			//tree_div_dom.parentNode.style.height=parseInt(h_screen)-40;
			//tree_div_dom.parentNode.parentNode.style.height=parseInt(h_screen)-40;
			//tree_div_dom.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.style.height=parseInt(h_screen)-40;
			
			var tree_div1_dom=document.getElementById("tree_div1");
			tree_div1_dom.style.height=parseInt(h_screen)-30+"px";
			tree_div1_dom.firstChild.style.height=parseInt(h_screen)-35+"px";
			tree_div1_dom.firstChild.firstChild.style.height=parseInt(h_screen)-35+"px";
			//tree_div1_dom.parentNode.style.height=parseInt(h_screen)-30+"px";
			//tree_div1_dom.parentNode.parentNode.style.height=parseInt(h_screen)-30+"px";
			//tree_div1_dom.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.style.height=parseInt(h_screen)-30+"px";
			
			var tree_div2_dom=document.getElementById("tree_div2");
			tree_div2_dom.style.height=parseInt(h_screen)-30+"px";
			tree_div2_dom.firstChild.style.height=parseInt(h_screen)-35+"px";
			tree_div2_dom.firstChild.firstChild.style.height=parseInt(h_screen)-35+"px";
			//tree_div2_dom.parentNode.style.height=parseInt(h_screen)-30+"px";
			//tree_div2_dom.parentNode.style.height=parseInt(h_screen)-30+"px";
			//tree_div2_dom.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode.style.height=parseInt(h_screen)-30+"px";
			//alert("1");
			setWidth();
		}catch(err){
		}
	}
	
	function setWidth(){
		//var w_screen=document.body.offsetWidth;
		var width1=document.getElementById("table_width_id").rows[0].cells[0].offsetWidth;
		var tree_div_dom=document.getElementById("tree-div");
		/* tree_div_dom.style.width=width1;//0.18
		tree_div_dom.firstChild.style.width=width1; */
		tree_div_dom.firstChild.firstChild.style.width=width1;
		
		var width2=document.getElementById("table_width_id").rows[0].cells[1].offsetWidth;
		//var tree_div1_dom=document.getElementById("tree_div1");
		tree_div1_dom.style.width=width2;
		tree_div1_dom.firstChild.style.width=width2;
		tree_div1_dom.firstChild.firstChild.style.width=width2;
		
		var width3=document.getElementById("table_width_id").rows[0].cells[2].offsetWidth;
		//var tree_div2_dom=document.getElementById("tree_div2");
		tree_div2_dom.style.width=width3;
		tree_div2_dom.firstChild.style.width=width3;
		tree_div2_dom.firstChild.firstChild.style.width=width3;
	}
	
	//var t111 ="";
	Ext.onReady(function(){
		document.getElementById("tree_div1_tab").focus();
		//t111 = window.setInterval("setHeight()",1000);
		setHeight();
		
	});
	window.document.onkeydown = testKey;
	function testKey(evt){
	evt = (evt) ? evt : window.event
	if (evt.keyCode==122||evt.keyCode=='122') {//f11
		setTimeout(setHeight,1000); 
	}
	}
	/* window.setInterval(focus_func, 1000); 
	function focus_func(){ 
		document.getElementById("tree_div1_tab").focus();
	}  */
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
<div id="groupTreeContent" style="height: 100%;border:0px solid #F00;">
<!-- <div id="groupTreePanel" style="height: 100%;"></div> -->
	<table width="100%" height="100%" border="0" id="table_width_id">
		<tr valign="top">
			<td width="18%">
				<table width="100%" id="tree-div_tab">
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
								<table style="background-color: #cedff5;height: 460px;width:100%;" >
									<tr>
										<td>
											<table border="0">
												<tr>
													<td>
														<div id="tree-div"
															style="overflow: hidden;height: 460px;border: 2px solid #c3daf9;postion:relative;"></div>
													</td>
												</tr>
												<tr>
													<odin:hidden property="codevalueparameter" />
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
						<odin:hidden property="a0000"/>
						<odin:hidden property="a0101"/>
						<odin:hidden property="ereaname" value="<%=ereaname%>" />
						<odin:hidden property="ereaid" value="<%=ereaid%>" />
						<odin:hidden property="manager" value="<%=manager%>" />
						<odin:hidden property="picType" value="<%=picType%>" />
					</tr>
				</table>
			</td>
			<td width="41%">
			<table width="100%" id="tree_div1_tab">
					<tr>
						<td valign="top">
							
								<table style="width: 100%; background-color: #cedff5">
									<tr>
										<td style="text-align:center"><font
											style="font-size: 13">����ͳ��</font></td>
									</tr>
								</table>
								<table style="background-color: #cedff5;width: 100%;height: 472px;" >
									<tr>
										<td>
											<div id="tree_div1" style="overflow: hidden;height: 472px;border: 2px solid #c3daf9"></div>
										</td>
									</tr>
								</table>
						</td>
					</tr>
			</table>
			</td>
			<td width="41%">
			<table	width="100%" id="tree_div2_tab">
					<tr>
						<td valign="top">
							
								<table style="width: 100%; background-color: #cedff5">
									<tr>
										<td style="text-align:center"><font
											style="font-size: 13">�Զ���ͳ��</font></td>
									</tr>
								</table>
								<table style="background-color: #cedff5;width: 100%;height: 472px;" >
									<tr>
										<td>
											<div id="tree_div2" style="overflow: hidden;height: 472px;border: 2px solid #c3daf9"></div>
										</td>
									</tr>
									<tr id="btn_tr" style="display:none">
										<td>
										<div id="btn_div">
											<table  >
												<tr  >
											<td  id="abc" width="120px" ></td>
											<td  id="edit" style="display:none" >
											<odin:button text="�༭" property="editorBtn" handler="editororgid"></odin:button>
											</td>
											<td width="110px"></td>
											<td  id="del" style="display:none"> 
											<odin:button text="ɾ��" property="deleteBtn"></odin:button>
											</td>
											<td width="50px"></td>
											<td width="50px"></td>
										</tr>
										</table>
										</div>
										</td>
									</tr>
								</table>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
</div>
<script type="text/javascript">

</script>	
<odin:hidden property="checknodeid1"/>
<odin:hidden property="checknodeid2"/>
<odin:window src="/blank.htm" id="cytjfxWin" width="400" height="530"
	title="����ͳ�Ʒ���" modal="true"></odin:window>
<odin:window src="/blank.htm" id="zdytjfxWin" width="300" height="240"
	title="�Զ���ͳ�Ʒ���" modal="true"></odin:window>