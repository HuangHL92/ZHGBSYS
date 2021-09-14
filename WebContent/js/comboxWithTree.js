/**
 * @author    zoulei 2018年4月11日
 例如职务层次一级节点是不能选的判断如何单独控制
 代码搜索用录入框触发，去掉搜索按钮
 可以按照代码搜索

 */
Ext.ux.form.ComboBoxWidthTree = Ext.extend(Ext.form.ComboBox, {
	store:new Ext.data.SimpleStore({fields:[],data:[[]]}),   
	selectStore : {},
    editable:true,   
    ischecked:false,   
    mode: 'local',   
    triggerAction:'all',   
    maxHeight: 400,   
    listWidth:350,
    listHeight:180,
    nodeDbl:function(){},
    property:'',
    tpl: '<div style="height:200px;"><div id="treep1"></div></div>',   
    applyTo:'',
    codetype:'',
    codename:'',
    selectedClass:'',   
    onSelect:Ext.emptyFn,
    listeners :{
    	'beforequery': function(){},
    	'collapse': function(){
			if(this.gridId!=""){
				//Ext.getCmp(this.gridId).getSelectionModel().getSelected().set(this.property,$('#'+this.property+'_combotree').val());
				
				//Ext.getCmp(this.gridId).activeEditor.completeEdit();
			}
		}
    },
  //重新加载树
    rebuildTree: function(codetype,codename){
    	
    	//alert(this.tree2);
    	//this.treeExt();
    	this.tree2.getLoader().baseParams['codetype']=codetype;
    	this.tree2.getLoader().baseParams['codename']=codename;
    	this.tree3.getLoader().baseParams['codetype']=codetype;
    	this.tree3.getLoader().baseParams['codename']=codename;
    	this.codetype=codetype;
    	this.codename=codename;
    	//判断数据是否已加载，若加载了，根据codetype重新加载
    	if(this.tree3.rendered)
    		this.tree3.getLoader().load( this.tree3.getRootNode());
    	if(this.tree2.rendered)
    		this.tree2.getLoader().load( this.tree2.getRootNode());
    },
    tree2:'',
    tree3:'',
    gridId:'',  
    /**
     * 树面板
     */
    treeExt: function(){
    	var comboxWithPanel = this;
    	var treeLoader = new Ext.tree.TreeLoader( {
    	        dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.yngwyUtil.CodeValueUtil&eventNames=codeValueJsonData'
    	        ,baseParams: {'ischecked':this.ischecked,'codetype':this.codetype,'formproperty':this.property,'codename':this.codename}
    		})
    	if(this.ischecked){
    		treeLoader['baseAttrs']={ uiProvider: Ext.tree.TreeCheckNodeUI };
    	}
    	var tree2 = new Ext.tree.TreePanel({  
    		loader : treeLoader,
    		rootVisible: false,//是否显示最上级节点
    	    border:false,   
    	    autoHeight :false,
    	    height : comboxWithPanel.listHeight,
    	    width : comboxWithPanel.listWidth,
    	    autoScroll:true,   
    	    checkModel: 'multiple',
    	    listeners: {  
    	        'dblclick':function(node,e){  
    	        	comboxWithPanel.nodeDbl(node,e);
    	        },
    	        'load' : function(node,e){
    	        	//alert(document.getElementById(comboxWithPanel.property).value)
    	        	//showChildNode(node);
					//treeclick(node);
    	        },
    	        'checkchange' : function(node, checked) {
					//node.expand();
					//treeclick(node)
					
    	        }
    	    },
    	    root:new Ext.tree.AsyncTreeNode({text: '下拉树插件',id:'-1'})
    	   
    	    }); 
    	this.tree2=tree2;
    	function loopNode(node,arrayNode){
    		if(node.ui.checkbox&&node.ui.checkbox.checked==true){
    			arrayNode.push([node.id,node.attributes.selectText,node]);  
		    }
    		
    		for(var li =0;li<node.childNodes.length ;li++){
    			loopNode(node.childNodes[li],arrayNode);
    		}
    	}
    	
    	//获取节点子节点信息
    	function treeclick(node){
    		//获取选中状态
    		if(node.ui.checkbox){
    			var flag = node.ui.checkbox.checked;
        		if(node.hasChildNodes()){
        			node.expand();
        			node.eachChild(function(child){
        				//选中子节点
        				child.ui.toggleCheck(flag);
        				//子节点赋值
        				child.attributes.checked = flag;
        				//递归选中子节点
        				treeclick(child);
        			})
        		}
    		}
    		
    	}
    	//父节点选择
    	//递归展开所有子节点
    	function showChildNode(node) {
    			var childnodes = node.childNodes;
    			for (var i = 0; i < childnodes.length; i++) { //从节点中取出子节点依次遍历
    				var rootnode = childnodes[i];
    				if(node.hasChildNodes()){
    					rootnode.expand();
    				}
    				//alert(rootnode.childNodes.length); //LJ遍历呈现树-LJ
    				if (rootnode.childNodes.length > 0) {
    					showChildNode(rootnode); //如果存在子节点  递归
    				}
    			}
    	}
    	if(this.ischecked){
    		tree2.on("check",function(node,checked){
    			var rootNode = tree2.getRootNode();
    			var arrayNode = [];
    			loopNode(rootNode,arrayNode);
    			var nodeids = "";
    			var nodetext = "";
    			for(var arri=0;arri<arrayNode.length;arri++){
    				nodeids = nodeids+arrayNode[arri][0]+","
    				nodetext = nodetext+arrayNode[arri][1]+","
    			}
    			if(arrayNode.length>0){
    				nodeids = nodeids.substr(0,nodeids.length-1);
    				nodetext = nodetext.substr(0,nodetext.length-1);
    			}
    			document.getElementById(comboxWithPanel.property).value=nodeids;
            	document.getElementById(comboxWithPanel.property+'_combotree').value=nodetext;
    		});
    		tree2.on("checkchange",function(node,checked){
    			node.expand();
				treeclick(node)
    		});
    		tree2.on("load",function(node,checked){
    			treeclick(node)
    		});
    	}
    	var isload = false;
    	var tree3 = new Ext.tree.TreePanel({   //查询树
    		loader : new Ext.tree.TreeLoader( {
    	        dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.yngwyUtil.CodeValueUtil&eventNames=searchCodeValueJsonData'
    	        ,baseParams: {'ischecked':this.ischecked,'codetype':this.codetype,'formproperty':this.property,'codename':this.codename}
    	        ,listeners :{
    	        	//防止在加载时又发送请求。
	    			'load':function(){
	    				isload = true;
	    			},
	    			'dblclick':function(node,e){  
	    	        	comboxWithPanel.nodeDbl(node,e);
	    	        }
	    			
	    		}
    	        
    		}),
    		clearOnLoad :true,
    		rootVisible: false,//是否显示最上级节点
    	    border:false,   
    	    height:comboxWithPanel.listHeight,
    	    width:comboxWithPanel.listWidth,
    	    autoScroll:true,   
    	    hidden :true,
    	    root:new Ext.tree.AsyncTreeNode({text: '下拉树插件',id:'-1'})
    	    
    	    });
    	this.tree3=tree3;
    	var myMask;
    	var inputField = new Ext.form.TextField({width:295,
			enableKeyEvents:true,
			listeners :{
			          render: function(p) {  
						  // Append the Panel to the click handler's argument list.  
						 p.getEl().on('mousemove', function(p){  
							var svalue = inputField.getValue();
							if(svalue==''||svalue==null){
								tree3.hide();
								tree2.show();
								return;
							}
						  });   

					  } ,'keyup':function(a,b){
						 /* if(b.getKey()!='13'){
							  return;
						  }*/
    				var svalue = inputField.getValue();
    				if(svalue==''||svalue==null){
	    				tree3.hide();
	    				tree2.show();
    					return;
    				}
    				//若没加载完，不触发查询按钮
    				if(!isload){
    					return;
    				}
    				isload = false;
    				
    				myMask.show();

    				//设置搜索框参数，用作后台条件过滤
    				tree3.getLoader().baseParams['svalue']=svalue;
    				tree3.getLoader().load( tree3.getRootNode(),function(){
    					//Ext.getCmp(comboxWithPanel.property+'back').show();
	    				tree3.show();
	    				tree2.hide();
	    				myMask.hide();
    				} ) 
    				
    			}
    			
    		}
		});
    	var border = new Ext.Panel({   
    	    //title:'面板title',   
    	    layout:'fit',   
    	    border:false,   
    	    autoHeight:true,
    	    //width : comboxWithPanel.listWidth,
			align : 'right',
    	    tbar:[
    	    	inputField
    	    	,
    	    	/*new Ext.Toolbar.Button({id:comboxWithPanel.property+'search',cls:'x-btn-color-default',overCls:'x-btn-color-default-over',disabled:false,text:'搜索',
    	    		listeners :{
    	    			'click':function(){
    	    				var svalue = Ext.getCmp(comboxWithPanel.property+'paramSrName').getValue();
    	    				if(svalue==''||svalue==null){
    	    					return;
    	    				}
    	    				//若没加载完，不触发查询按钮
    	    				if(!isload){
    	    					return;
    	    				}
    	    				isload = false;
    	    				
    	    				myMask.show();

    	    				//设置搜索框参数，用作后台条件过滤
    	    				tree3.getLoader().baseParams={'svalue':svalue};
    	    				tree3.getLoader().load( tree3.getRootNode(),function(){
    	    					Ext.getCmp(comboxWithPanel.property+'back').show();
        	    				tree3.show();
        	    				tree2.hide();
        	    				myMask.hide();
    	    				} ) 
    	    				
    	    			}
    	    			
    	    		}}),*/
    	    		new Ext.Toolbar.Button({id:comboxWithPanel.property+'clear',cls:'x-btn-color-default',overCls:'x-btn-color-default-over',disabled:false,text:'清空',
    	    			tooltip:'清空页面“'+comboxWithPanel.label+'”项的录入信息！',
    	    		
        	    		listeners :{
        	    			'click':function(){
        	    				var rootNode = tree2.getRootNode();
        	        			var arrayNode = [];
        	        			loopNode(rootNode,arrayNode);
        	        			var nodeids = "";
        	        			var nodetext = "";
        	        			for(var arri=0;arri<arrayNode.length;arri++){
        	        				arrayNode[arri][2].ui.checkbox.checked=false;
        	        			}
        	    				document.getElementById(comboxWithPanel.property).value='';
        	    	        	document.getElementById(comboxWithPanel.property+'_combotree').value='';
        	    	        	Ext.getCmp(comboxWithPanel.property+'_combotree').collapse();
        	    			}
        	    			
        	    		}}),
    	    		new Ext.Toolbar.Fill()/*,	
    	    		new Ext.Toolbar.Button({id:comboxWithPanel.property+'back',cls:'x-btn-color-default',overCls:'x-btn-color-default-over',disabled:false,text:'返回',hidden:true,
        	    		listeners :{
        	    			'click':function(){
        	    				Ext.getCmp(comboxWithPanel.property+'back').hide();
        	    				tree3.hide();
        	    				tree2.show();
        	    			}
        	    		}})*/
            	    	
            	    	
    	    	
    	    	],   
    	    	
    	   // bbar:[new Ext.form.TextField({id: 'aa',width:60}),{text:'查找二'}],   
    	    items: [tree2, tree3 ] 
    	    
    	    });   
    	
    	comboxWithPanel.on('expand',function(){   
    	    	border.render(comboxWithPanel.property+'_treePanel');
    	    	tree2.setHeight(comboxWithPanel.listHeight-5)
    	    	tree3.setHeight(comboxWithPanel.listHeight-5)
    	    	var bind = false;
    	    	//面板展开后聚焦搜索框，并绑定回车查询事件 ,创建面板的遮罩对象
    			var execfn = function(){
    				try{
    					if(document.getElementById(comboxWithPanel.property+'_treePanel')){
	    					if(document.getElementById(comboxWithPanel.property+'_treePanel').children){
	    						var num=document.getElementById(comboxWithPanel.property+'_treePanel').children.length;
	    						for(var i=0;i<(num-1);i++){
	    							document.getElementById(comboxWithPanel.property+'_treePanel').removeChild(document.getElementById(comboxWithPanel.property+'_treePanel').children(0));
	    						}
	    					}
    					}
    				}catch(err){
    					
    				}
    				
    				try{
    					if(inputField){
    						inputField.focus();
		    				myMask = new Ext.LoadMask(border.getEl(), {msg:"请稍候..."});
    						if(!bind){
		    					bind = true;
		    					$('#'+comboxWithPanel.property+'paramSrName').bind('keydown', function(event) {
								　　if (event.keyCode == "13") {
								　　　　//回车执行查询
								　　　　$('#'+comboxWithPanel.property+'search').click();
										return false;
								　　}
								});
		    				}
    					}
    					
    				}catch(err){
    					
    				}
    				
    				//清除下拉选
    				if(comboxWithPanel.gridId!=""){
    					comboxWithPanel.clearCheck()
    				}
    			};
    			setTimeout(execfn,300);
    	    }); 
    	
    	
    },
    ck : function(node,obj){
    	if(node.ui.checkbox){
    		node.ui.checkbox.checked = false;
    	}
		if(node.hasChildNodes()){
			node.eachChild(function(child){
				//子节点赋值
				child.attributes.checked = false;
				//递归选中子节点
				obj.ck(child,obj);
			})
		}
    },
    clearCheck : function(){
    	var node = this.tree2.getRootNode();
    	this.ck(node,this);
    },
 // private
    initComponent : function(){
        Ext.form.ComboBox.superclass.initComponent.call(this);
        this.addEvents(
            /**
             * @event expand
             * Fires when the dropdown list is expanded
             * @param {Ext.form.ComboBox} combo This combo box
             */
            'expand',
            /**
             * @event collapse
             * Fires when the dropdown list is collapsed
             * @param {Ext.form.ComboBox} combo This combo box
             */
            'collapse',
            /**
             * @event beforeselect
             * Fires before a list item is selected. Return false to cancel the selection.
             * @param {Ext.form.ComboBox} combo This combo box
             * @param {Ext.data.Record} record The data record returned from the underlying store
             * @param {Number} index The index of the selected item in the dropdown list
             */
            'beforeselect',
            /**
             * @event select
             * Fires when a list item is selected
             * @param {Ext.form.ComboBox} combo This combo box
             * @param {Ext.data.Record} record The data record returned from the underlying store
             * @param {Number} index The index of the selected item in the dropdown list
             */
            'select',
            /**
             * @event beforequery
             * Fires before all queries are processed. Return false to cancel the query or set the queryEvent's
             * cancel property to true.
             * @param {Object} queryEvent An object that has these properties:<ul>
             * <li><code>combo</code> : Ext.form.ComboBox <div class="sub-desc">This combo box</div></li>
             * <li><code>query</code> : String <div class="sub-desc">The query</div></li>
             * <li><code>forceAll</code> : Boolean <div class="sub-desc">True to force "all" query</div></li>
             * <li><code>cancel</code> : Boolean <div class="sub-desc">Set to true to cancel the query</div></li>
             * </ul>
             */
            'beforequery'
        );
        if(this.transform){
            var s = Ext.getDom(this.transform);
            if(!this.hiddenName){
                this.hiddenName = s.name;
            }
            if(!this.store){
                this.mode = 'local';
                var d = [], opts = s.options;
                for(var i = 0, len = opts.length;i < len; i++){
                    var o = opts[i],
                        value = (o.hasAttribute ? o.hasAttribute('value') : o.getAttributeNode('value').specified) ? o.value : o.text;
                    if(o.selected && Ext.isEmpty(this.value, true)) {
                        this.value = value;
                    }
                    d.push([value, o.text]);
                }
                this.store = new Ext.data.ArrayStore({
                    'id': 0,
                    fields: ['value', 'text'],
                    data : d,
                    autoDestroy: true
                });
                this.valueField = 'value';
                this.displayField = 'text';
            }
            s.name = Ext.id(); // wipe out the name in case somewhere else they have a reference
            if(!this.lazyRender){
                this.target = true;
                this.el = Ext.DomHelper.insertBefore(s, this.autoCreate || this.defaultAutoCreate);
                this.render(this.el.parentNode, s);
                Ext.removeNode(s); // remove it
            }else{
                Ext.removeNode(s); // remove it
            }
        }
        //auto-configure store from local array data
        else if(this.store){
            this.store = Ext.StoreMgr.lookup(this.store);
            if(this.store.autoCreated){
                this.displayField = this.valueField = 'field1';
                if(!this.store.expandData){
                    this.displayField = 'field2';
                }
                this.mode = 'local';
            }
        }

        this.selectedIndex = -1;
        if(this.mode == 'local'){
            if(!Ext.isDefined(this.initialConfig.queryDelay)){
                this.queryDelay = 10;
            }
            if(!Ext.isDefined(this.initialConfig.minChars)){
                this.minChars = 0;
            }
        }
        this.treeExt();
    },
 // private
    //手动输入时会执行查询，会将下拉重新初始化。将查询的代码注释了。防止下拉框被重新初始化。
    initEvents : function(){
        Ext.form.ComboBox.superclass.initEvents.call(this);

        this.keyNav = new Ext.KeyNav(this.el, {
            "up" : function(e){
                //this.inKeyMode = true;
               // this.selectPrev();
            },

            "down" : function(e){
               /* if(!this.isExpanded()){
                    this.onTriggerClick();
                }else{
                    this.inKeyMode = true;
                    this.selectNext();
                }*/
            },

            "enter" : function(e){
                /*this.onViewClick();
                this.delayedCheck = true;
                this.unsetDelayCheck.defer(10, this);*/
            },

            "esc" : function(e){
                this.collapse();
            },

            "tab" : function(e){
                this.onViewClick(false);
                return true;
            },

            scope : this,

            doRelay : function(foo, bar, hname){
                /*if(hname == 'down' || this.scope.isExpanded()){
                   return Ext.KeyNav.prototype.doRelay.apply(this, arguments);
                }
                return true;*/
            },

            forceKeyDown : true
        });
       /* this.queryDelay = Math.max(this.queryDelay || 10,
                this.mode == 'local' ? 10 : 250);
        this.dqTask = new Ext.util.DelayedTask(this.initQuery, this);
        if(this.typeAhead){
            this.taTask = new Ext.util.DelayedTask(this.onTypeAhead, this);
        }
        if(this.editable !== false && !this.enableKeyEvents){
            this.mon(this.el, 'keyup', this.onKeyUp, this);
        }*/
    },
    setSelectValue :function(id){
			alert(this.selectStore[id]);
    	if(this.selectStore[id]){
    		document.getElementById(this.property).value=id;
        	document.getElementById(this.property+'_combotree').value=this.selectStore[id];
    	}
    	
    }
});
Ext.reg('ComboBoxWidthTree', Ext.ux.form.ComboBoxWidthTree); 
Ext.tree.TreeCheckNodeUI = function() { 
	//'multiple':多选; 'single':单选; 'cascade':级联多选 
	this.checkModel = 'multiple'; 

	//only leaf can checked 
	this.onlyLeafCheckable = false; 

	Ext.tree.TreeCheckNodeUI.superclass.constructor.apply(this, arguments); 
	}; 

	Ext.extend(Ext.tree.TreeCheckNodeUI, Ext.tree.TreeNodeUI, { 

	renderElements : function(n, a, targetNode, bulkRender){ 
	var tree = n.getOwnerTree(); 
	this.checkModel = tree.checkModel || this.checkModel; 
	this.onlyLeafCheckable = tree.onlyLeafCheckable || false; 

	// add some indent caching, this helps performance when rendering a large tree 
	this.indentMarkup = n.parentNode ? n.parentNode.ui.getChildIndent() : ''; 

	var cb = (!this.onlyLeafCheckable || a.leaf); 
	var href = a.href ? a.href : Ext.isGecko ? "" : "#"; 
	var buf = ['<li class="x-tree-node"><div ext:tree-node-id="',n.id,'" class="x-tree-node-el x-tree-node-leaf x-unselectable ', a.cls,'" unselectable="on">', 
	'<span class="x-tree-node-indent">',this.indentMarkup,"</span>", 
	'<img src="', this.emptyIcon, '" class="x-tree-ec-icon x-tree-elbow" />', 
	'<img src="', a.icon || this.emptyIcon, '" class="x-tree-node-icon',(a.icon ? " x-tree-node-inline-icon" : ""),(a.iconCls ? " "+a.iconCls : ""),'" unselectable="on" />', 
	cb ? ('<input class="x-tree-node-cb" type="checkbox" ' + (a.checked ? 'checked="checked" />' : '/>')) : '', 
	'<a hidefocus="on" class="x-tree-node-anchor" href="',href,'" tabIndex="1" ', 
	a.hrefTarget ? ' target="'+a.hrefTarget+'"' : "", '><span unselectable="on">',n.text,"</span></a></div>", 
	'<ul class="x-tree-node-ct" style="display:none;"></ul>', 
	"</li>"].join(''); 

	var nel; 
	if(bulkRender !== true && n.nextSibling && (nel = n.nextSibling.ui.getEl())){ 
	this.wrap = Ext.DomHelper.insertHtml("beforeBegin", nel, buf); 
	}else{ 
	this.wrap = Ext.DomHelper.insertHtml("beforeEnd", targetNode, buf); 
	} 

	this.elNode = this.wrap.childNodes[0]; 
	this.ctNode = this.wrap.childNodes[1]; 
	var cs = this.elNode.childNodes; 
	this.indentNode = cs[0]; 
	this.ecNode = cs[1]; 
	this.iconNode = cs[2]; 
	var index = 3; 
	if(cb){ 
	this.checkbox = cs[3]; 
	Ext.fly(this.checkbox).on('click', this.check.createDelegate(this,[null])); 
	index++; 
	} 
	this.anchor = cs[index]; 
	this.textNode = cs[index].firstChild; 
	}, 

	// private 
	check : function(checked){ 
	var n = this.node; 
	var tree = n.getOwnerTree(); 
	this.checkModel = tree.checkModel || this.checkModel; 

	if( checked === null ) { 
	checked = this.checkbox.checked; 
	} else { 
	this.checkbox.checked = checked; 
	} 

	n.attributes.checked = checked; 
	tree.fireEvent('check', n, checked); 

	if(!this.onlyLeafCheckable && this.checkModel == 'cascade'){ 
	var parentNode = n.parentNode; 
	if(parentNode !== null) { 
	this.parentCheck(parentNode,checked); 
	} 
	if( !n.expanded && !n.childrenRendered ) { 
	n.expand(false,false,this.childCheck); 
	} 
	else { 
	this.childCheck(n); 
	} 
	}else if(this.checkModel == 'single'){ 
	var checkedNodes = tree.getChecked(); 
	for(var i=0;i<checkedNodes.length;i++){ 
	var node = checkedNodes[i]; 
	if(node.id != n.id){ 
	node.getUI().checkbox.checked = false; 
	node.attributes.checked = false; 
	tree.fireEvent('check', node, false); 
	} 
	} 
	} 

	}, 

	// private 
	childCheck : function(node){ 
	var a = node.attributes; 
	if(!a.leaf) { 
	var cs = node.childNodes; 
	var csui; 
	for(var i = 0; i < cs.length; i++) { 
	csui = cs[i].getUI(); 
	if(csui.checkbox.checked ^ a.checked) 
	csui.check(a.checked); 
	} 
	} 
	}, 

	// private 
	parentCheck : function(node ,checked){ 
	var checkbox = node.getUI().checkbox; 
	if(typeof checkbox == 'undefined')return ; 
	if(!(checked ^ checkbox.checked))return; 
	if(!checked && this.childHasChecked(node))return; 
	checkbox.checked = checked; 
	node.attributes.checked = checked; 
	node.getOwnerTree().fireEvent('check', node, checked); 

	var parentNode = node.parentNode; 
	if( parentNode !== null){ 
	this.parentCheck(parentNode,checked); 
	} 
	}, 

	// private 
	childHasChecked : function(node){ 
	var childNodes = node.childNodes; 
	if(childNodes || childNodes.length>0){ 
	for(var i=0;i<childNodes.length;i++){ 
	if(childNodes[i].getUI().checkbox.checked) 
	return true; 
	} 
	} 
	return false; 
	}, 

	toggleCheck : function(value){ 
	var cb = this.checkbox; 
	if(cb){ 
	var checked = (value === undefined ? !cb.checked : value); 
	this.check(checked); 
	} 
	} 
	}); 

