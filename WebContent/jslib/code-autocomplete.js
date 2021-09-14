var Code={
	showTree:function(target,codeClass,codevalue,showvalue,clickCallBack,w){
		$(target).data("code",new code(codevalue,showvalue));
		if($("#codeContent").length<=0){
			$(target).parent().append("<div id=\"codeContent\" style=\"display:none;z-index:1000; position: absolute; border:#999 1px solid;height:200px;overflow: scroll;background: white;\">" +
					"<ul id=\"codeTree\" style=\"margin-top:0; width:160px;\"></ul></div>");
			initTree();
		}else{
			//if($("#codeTree").tree('options').url !=System.rootPath+"/code/code.action?codeClass="+codeClass){
			if($("#codeTree").data("target") != target){
				initTree();
			}
		}
		function initTree(){
			if(!w){
				w = $(target).width();
			}
			$("#codeTree").data("target",target);
			$("#codeTree").tree({
				width:w,
				url:System.rootPath+"/code/code.action?codeClass="+codeClass,
				onClick:function(node){
					clickCallBack(node);
					$(target).data("code_new",new code(node.id,node.text));
				},
				onDblClick:function(node){
					hidePanel();
				},
				onSelect :function (node){
					clickCallBack(node);
					$(target).data("code_new",new code(node.id,node.text));
				},
				onLoadSuccess : function(){
					//clickCallBack({id:'',text:''});//清理现有代码
				},
				onBeforeExpand:function(node){// $(this).tree('options') <=> $(target).combotree('tree').tree('options')
					var cc = node.attributes.codeClass == undefined ? codeClass:node.attributes.codeClass;
					$("#codeTree").tree("options").url=System.rootPath+"/code/code.action?codeClass="+cc+"&isclass="+node.attributes.isclass;
				}
			});
		}
		var timeOutID;
		var previousValue;
		var delay = 200;
		if(!$(target).hasClass("codeSelect")){
			$(target).addClass("codeSelect");
		}
//		$(target).unbind("keydown");
		$(target).bind("keydown",function(e){
			switch (e.keyCode) {
				case 17:
				case 67:
					return false;//ctrl + c || 按下鼠标 + ctrl+c
				case 38 :  // up
					up();
					break;
				case 40 :  // down
					down();
					break;
				case 13 :  // enter
					e.preventDefault();
					enter();
					return false;
				case 9 :
				case 27 :  // esc
					hidePanel();
					break;
				default :
					killTimeout();
					timeOutID = setTimeout(function() {
						var q = $(target).val().Trim();
						//if (previousValue != q) {
							previousValue = q;
							query(q);
						//}
					}, delay);
			}
		});//.bind('keyup',query);
		query($(target).val().Trim());// 初始执行查询
		function query(q){
			if(q){
				q = q.Trim();
				showPanel();
				if(q.length>0){
					$.ajax({
						type:'post',
						url:System.rootPath + "/code/code!queryCode.action",
						dataType:'json',
						data:{'querykey':q,'codeClass':codeClass},
						success:function(json){
							//$(target).data("code_new",new code('',q));
							$("#codeTree").tree('loadData',json);
						},
						error:function(){
							$("#codeTree").tree('loadData',[]);
						},
						complete:function(){
							killTimeout();
						}
					});
				}else{
					$("#codeTree").tree('reload');
				}
			}else{
				showPanel();
				var options = $("#codeTree").tree('options');
				options.width=w;
				options.url=System.rootPath+"/code/code.action?codeClass="+codeClass;
				$("#codeTree").tree(options);
				killTimeout();
				clickCallBack({id:'',text:''});//清理现有代码
			}
		}
		function killTimeout(){
			if(timeOutID){
				clearTimeout(timeOutID);
				timeOutID = null;
			}
		}
		function hidePanel(){
			killTimeout();
			var node = $("#codeTree").tree('getSelected');
			if(node){
				$(target).val(node.text);
			}else{
				if($(target).val()!=""){// 没有选择代码也没有清空代码，代码回复为原来的值
					var code = ($(target).data("code_new")!=null)?$(target).data("code_new"):$(target).data("code");
					clickCallBack({id:code.value,text:code.show});
				}
			}
			$("#codeContent").fadeOut("fast");
			$("body").unbind("mousedown");
			$(target).unbind('keydown');
			return false;
		}
		function showPanel(){
			//if($("#codeContent").is(":hidden")){
				var cityObj = $(target);
				var cityOffset = getPosition(target);
				$("#codeContent").slideDown("fast").css(
					{
					 //left:(cityOffset.left+($.browser.msie ? -5:0)) + "px",
					 //top:(cityOffset.top + ($.browser.msie ? -10:cityObj.outerHeight(true)))+"px"
					   left:cityOffset.left+"px",
					   top:cityOffset.top+"px"
					});
				document.getElementById("codeContent").style.overflow='scroll';
				$("body").bind("mousedown", function(event){
					event.preventDefault();// 组织默认事件执行
					if (!(event.target.id == target || event.target.id == "codeContent" || $(event.target).parents("#codeContent").length>0)) {
						hidePanel();
					}
				});
			//}
		}
		
		function getPosition(target){
			var cityOffset = $(target).position();
			if($.browser.msie){
				var op = $(target).get(0);//document.getElementById(target.replace('#',''));
				var hidleft=0;
				var hidtop=0;
			    while(op.offsetParent){
			        hidleft +=op.offsetLeft;
			        hidtop +=op.offsetTop;
			        if (op.tagName.toLowerCase() == "table"){
			        	break;
			        }
			    	op=op.offsetParent;			    	
			    }
			    return {left:hidleft,top:hidtop+$(target).outerHeight(true)};
			}else{
				return {left:cityOffset.left,top:cityOffset.top+$(target).outerHeight(true)};
			}
		}
		
		function enter(){
			var node = $("#codeTree").tree('getSelected');
			if(node){
				hidePanel();
			}else{
				query($(target).val().trim());
			}
		}
		function up(){
			var targetTree = $("#codeTree");
			var node = targetTree.tree('getSelected');
			if(node){	
				var prevNode = $(node.target).parent().prev().find('.tree-node');
				var flag = false;
				var isParent = false;
				while(prevNode.length<=0 && !flag){
					if($(node.target).parent().parent().is(".tree")){// 本身就是根节点				
						flag = true;
						prevNode = $(this.getTreeLastNode(targetTree).target);
					}else{
						var parentNode = targetTree.tree('getParent',node.target);
						if(parentNode){
							prevNode = $(parentNode.target);
							isParent = true;
						}else{
							flag = true;
						}
					}
				}
				if(prevNode.length>0){
					if(!targetTree.tree('isLeaf',prevNode.get(0)) && !isParent){									
						targetTree.tree('expandAll',prevNode.get(0));
						prevNode = System.code.getTreeLastNode(targetTree,$(prevNode.get(0)).attr('node-id'));
						targetTree.tree('select',prevNode.target);
					}else{
						targetTree.tree('select',prevNode.get(0));
					}
				}							
			}else{							
				targetTree.tree('select',System.code.getTreeLastNode(targetTree).target);
			}
		}
		function down(){
			var targetTree = $("#codeTree");
			var node = targetTree.tree('getSelected');
			if(node){
				// 展开当前节点下的所有节点
				//var rootNode = this.getRootNodeByNodeId(targetTree,node);
				targetTree.tree('expandAll',node.target);
				if(targetTree.tree('isLeaf',node.target)){//如果为叶子节点								
					var nextNode = $(node.target).parent().next().find('.tree-node');
					var isRoot = false;								
					while(nextNode.length<=0 && !isRoot){
						var parentNode = targetTree.tree('getParent',node.target);
						if(parentNode){
							if($(parentNode.target).parent().parent().is(".tree")){// 循环到根节点了
								isRoot = true;
							}
							nextNode =  $(parentNode.target).parent().next().find('.tree-node');
						}else{// 已经是根节点
							isRoot = true;
						}									
					}
					if(nextNode.length>0){
						targetTree.tree('select',nextNode.get(0));
					}else{
						if(isRoot){
							var root  = targetTree.tree('getRoot');
							targetTree.tree('select',root.target);
						}
					}
				}else{// 如果是展开的目录直接选择第一个子节点，没有展开选择相邻下一个节点
					var nextNode = $(node.target).next().find('.tree-node');
					if(nextNode.length>0){
						targetTree.tree('select',nextNode.get(0));
					}								
				}
			}else{
				var root  = targetTree.tree('getRoot');
				targetTree.tree('select',root.target);
			}
		}
	},
	getTreeLastNode:function (targetTree,nodeid){
		var root  = null;
		if(nodeid){
			root = [targetTree.tree('find',nodeid)];
		}else{
			root  = targetTree.tree('getRoots');
		}				
		var lastRoot = root[root.length-1];
		if(targetTree.tree('isLeaf',lastRoot.target)){// 最后一个根节点为叶子节点 直接返回
			return lastRoot;
		}else{
			var flag = true;
			var rNode = lastRoot;
			do{
				var childrens = targetTree.tree('getChildren',rNode.target);
				var lastChildren = childrens[childrens.length-1];
				if(targetTree.tree('isLeaf',lastChildren.target)){// 最后一个根节点为叶子节点 直接返回
					flag = false;
				}
				rNode = lastChildren;
			}while(flag);
			return rNode;
		}
	},
	getRootNodeByNodeId:function (targetTree,node){
		//var node = targetTree.tree('find',nodeid);// 直接传递node对象，防止node没有ID的情况
		var flag = true;
		do{
			if($(node.target).parent().parent().is(".tree")){// 本身就是根节点				
				flag = false;
			}else{
				node = targetTree.tree('getParent',node.target);
			}
		}while(flag);
		return node;
	},
	render:function(target,codeClass,codevalue,showvalue,multipleSelection,clickCallBack,w){
		var Url = System.rootPath + "/common/common-code-tree.jsp?codeClass="+codeClass+"&codevalue="+codevalue+"&showvalue="+encodeURI(encodeURI(showvalue)) + "&multipleSelection=" + multipleSelection + "&rand="+Math.random();
		if($("#_codeTreeWin").length==0){
			var divHtml = "<div id='_codeTreeWin' class='easyui-window'>";
				divHtml += "<iframe id='_codeTreeIframe' src="+Url+" width='100%' height='100%' scrolling='no' frameborder='0'></iframe>";
				divHtml += "</div>";
			$(document.body).append(divHtml);
		}else{
			$("#_codeTreeIframe").attr('src',Url);
		}
		$("#_codeTreeWin").window({
			title:'设置查询条件',
			//fit:true,
			width:500,
			height:400,
			collapsible:false,
			minimizable:false,
			draggable:true,
			shadow:true,
			modal : true,
			onClose : function(){
				var snode = $("#_codeTreeWin").data('snode');
				if(snode){
					if(typeof(clickCallBack)=="function"){
						$(target).val(snode.text);
						clickCallBack(snode);
					}
				}
				$("#_codeTreeWin").data('snode', null); //清除, by YZQ on 2012/11/20
			}
		});
	},
	closeTreeWin:function(snode){
		$("#_codeTreeWin").data('snode',snode);
		$("#_codeTreeWin").window('close');
	}
};