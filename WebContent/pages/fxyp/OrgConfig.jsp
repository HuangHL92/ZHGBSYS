<%@page import="java.util.List"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@page import="com.insigma.siis.local.business.helperUtil.SysManagerUtils"%>
<%@page import="com.insigma.siis.local.pagemodel.customquery.CommSQL"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/ext/adapter/ext/ext-base.js"></script>
<script src="<%=request.getContextPath()%>/basejs/ext/ext-all.js"> </script>
<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>	

<html>
<head>
<title>全国公务员管理信息系统</title>
<script type="text/javascript">
var g_contextpath = '<%=request.getContextPath()%>';

</script>
<script src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js" type="text/javascript"></script>

<style type="text/css">
.iconToolBarBtnSave{background-image: url("<%=request.getContextPath()%>/commform/img/icon/save.gif") !important;}
.iconToolBarBtnRefresh{background-image: url("<%=request.getContextPath()%>/commform/img/icon/refresh.gif") !important;}
</style>
</head>

<body style="overflow:hidden;">
<odin:toolBar property="btnToolBar" applyTo="divbtnToolBar">
	<odin:fill />
	
		
	<odin:buttonForToolBar id="iconToolBarBtnSave" isLast="true" text="确定" handler="submitForm"
		icon="../customquery/icon/save.gif" cls="x-btn-text-icon" />
</odin:toolBar>

<div id="divbtnToolBar"></div>	

<form name="cform" id="cform" action="">

<div id="tree-div" style="overflow-x:hidden; overflow-y:scroll; border: 2px solid #c3daf9; height: 200px; font-size: 16px;padding-bottom: 60px;"></div>
<odin:hidden property="mntp00"/>
<odin:hidden property="mntp05"/>
</form>



</body>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/ext/adapter/ext/ext-base.js"></script>
<script src="<%=request.getContextPath()%>/basejs/ext/ext-all.js"> </script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/basejs/ext/resources/css/ext-all.css"/>
<script type="text/javascript">
Ext.MessageBox.buttonText['ok']="确定";
Ext.apply(Ext, function(){
    var E = Ext,
        idSeed = 0,
        scrollWidth = null;

    return {
        
        emptyFn : function(){},

        
        BLANK_IMAGE_URL : parent.Ext.BLANK_IMAGE_URL,

        extendX : function(supr, fn){
            return Ext.extend(supr, fn(supr.prototype));
        },

        
        getDoc : function(){
            return Ext.get(document);
        },

        
        num : function(v, defaultValue){
            v = Number(Ext.isEmpty(v) || Ext.isArray(v) || typeof v == 'boolean' || (typeof v == 'string' && v.trim().length == 0) ? NaN : v);
            return isNaN(v) ? defaultValue : v;
        },

        
        value : function(v, defaultValue, allowBlank){
            return Ext.isEmpty(v, allowBlank) ? defaultValue : v;
        },

        
        escapeRe : function(s) {
            return s.replace(/([-.*+?^${}()|[\]\/\\])/g, "\\$1");
        },

        sequence : function(o, name, fn, scope){
            o[name] = o[name].createSequence(fn, scope);
        },

        
        addBehaviors : function(o){
            if(!Ext.isReady){
                Ext.onReady(function(){
                    Ext.addBehaviors(o);
                });
            } else {
                var cache = {}, 
                    parts,
                    b,
                    s;
                for (b in o) {
                    if ((parts = b.split('@'))[1]) { 
                        s = parts[0];
                        if(!cache[s]){
                            cache[s] = Ext.select(s);
                        }
                        cache[s].on(parts[1], o[b]);
                    }
                }
                cache = null;
            }
        },

        
        getScrollBarWidth: function(force){
            if(!Ext.isReady){
                return 0;
            }

            if(force === true || scrollWidth === null){
                    
                var div = Ext.getBody().createChild('<div class="x-hide-offsets" style="width:100px;height:50px;overflow:hidden;"><div style="height:200px;"></div></div>'),
                    child = div.child('div', true);
                var w1 = child.offsetWidth;
                div.setStyle('overflow', (Ext.isWebKit || Ext.isGecko) ? 'auto' : 'scroll');
                var w2 = child.offsetWidth;
                div.remove();
                
                scrollWidth = w1 - w2 + 2;
            }
            return scrollWidth;
        },


        
        combine : function(){
            var as = arguments, l = as.length, r = [];
            for(var i = 0; i < l; i++){
                var a = as[i];
                if(Ext.isArray(a)){
                    r = r.concat(a);
                }else if(a.length !== undefined && !a.substr){
                    r = r.concat(Array.prototype.slice.call(a, 0));
                }else{
                    r.push(a);
                }
            }
            return r;
        },

        
        copyTo : function(dest, source, names){
            if(typeof names == 'string'){
                names = names.split(/[,;\s]/);
            }
            Ext.each(names, function(name){
                if(source.hasOwnProperty(name)){
                    dest[name] = source[name];
                }
            }, this);
            return dest;
        },

        
        destroy : function(){
            Ext.each(arguments, function(arg){
                if(arg){
                    if(Ext.isArray(arg)){
                        this.destroy.apply(this, arg);
                    }else if(typeof arg.destroy == 'function'){
                        arg.destroy();
                    }else if(arg.dom){
                        arg.remove();
                    }
                }
            }, this);
        },

        
        destroyMembers : function(o, arg1, arg2, etc){
            for(var i = 1, a = arguments, len = a.length; i < len; i++) {
                Ext.destroy(o[a[i]]);
                delete o[a[i]];
            }
        },

        
        clean : function(arr){
            var ret = [];
            Ext.each(arr, function(v){
                if(!!v){
                    ret.push(v);
                }
            });
            return ret;
        },

        
        unique : function(arr){
            var ret = [],
                collect = {};

            Ext.each(arr, function(v) {
                if(!collect[v]){
                    ret.push(v);
                }
                collect[v] = true;
            });
            return ret;
        },

        
        flatten : function(arr){
            var worker = [];
            function rFlatten(a) {
                Ext.each(a, function(v) {
                    if(Ext.isArray(v)){
                        rFlatten(v);
                    }else{
                        worker.push(v);
                    }
                });
                return worker;
            }
            return rFlatten(arr);
        },

        
        min : function(arr, comp){
            var ret = arr[0];
            comp = comp || function(a,b){ return a < b ? -1 : 1; };
            Ext.each(arr, function(v) {
                ret = comp(ret, v) == -1 ? ret : v;
            });
            return ret;
        },

        
        max : function(arr, comp){
            var ret = arr[0];
            comp = comp || function(a,b){ return a > b ? 1 : -1; };
            Ext.each(arr, function(v) {
                ret = comp(ret, v) == 1 ? ret : v;
            });
            return ret;
        },

        
        mean : function(arr){
           return arr.length > 0 ? Ext.sum(arr) / arr.length : undefined;
        },

        
        sum : function(arr){
           var ret = 0;
           Ext.each(arr, function(v) {
               ret += v;
           });
           return ret;
        },

        
        partition : function(arr, truth){
            var ret = [[],[]];
            Ext.each(arr, function(v, i, a) {
                ret[ (truth && truth(v, i, a)) || (!truth && v) ? 0 : 1].push(v);
            });
            return ret;
        },

        
        invoke : function(arr, methodName){
            var ret = [],
                args = Array.prototype.slice.call(arguments, 2);
            Ext.each(arr, function(v,i) {
                if (v && typeof v[methodName] == 'function') {
                    ret.push(v[methodName].apply(v, args));
                } else {
                    ret.push(undefined);
                }
            });
            return ret;
        },

        
        pluck : function(arr, prop){
            var ret = [];
            Ext.each(arr, function(v) {
                ret.push( v[prop] );
            });
            return ret;
        },

        
        zip : function(){
            var parts = Ext.partition(arguments, function( val ){ return typeof val != 'function'; }),
                arrs = parts[0],
                fn = parts[1][0],
                len = Ext.max(Ext.pluck(arrs, "length")),
                ret = [];

            for (var i = 0; i < len; i++) {
                ret[i] = [];
                if(fn){
                    ret[i] = fn.apply(fn, Ext.pluck(arrs, i));
                }else{
                    for (var j = 0, aLen = arrs.length; j < aLen; j++){
                        ret[i].push( arrs[j][i] );
                    }
                }
            }
            return ret;
        },

        
        getCmp : function(id){
            return Ext.ComponentMgr.get(id);
        },

        
        useShims: E.isIE6 || (E.isMac && E.isGecko2),

        
        
        type : function(o){
            if(o === undefined || o === null){
                return false;
            }
            if(o.htmlElement){
                return 'element';
            }
            var t = typeof o;
            if(t == 'object' && o.nodeName) {
                switch(o.nodeType) {
                    case 1: return 'element';
                    case 3: return (/\S/).test(o.nodeValue) ? 'textnode' : 'whitespace';
                }
            }
            if(t == 'object' || t == 'function') {
                switch(o.constructor) {
                    case Array: return 'array';
                    case RegExp: return 'regexp';
                    case Date: return 'date';
                }
                if(typeof o.length == 'number' && typeof o.item == 'function') {
                    return 'nodelist';
                }
            }
            return t;
        },

        intercept : function(o, name, fn, scope){
            o[name] = o[name].createInterceptor(fn, scope);
        },

        
        callback : function(cb, scope, args, delay){
            if(typeof cb == 'function'){
                if(delay){
                    cb.defer(delay, scope, args || []);
                }else{
                    cb.apply(scope, args || []);
                }
            }
        }
    };
}());

var realParent = parent.Ext.getCmp("setOrgConfig").initialConfig.thisWin;
function submitForm(){
	var tree = Ext.getCmp("group");
	var treeRoot = tree.getRootNode();
	var childNodes = treeRoot.childNodes;
	var jsonp = {};
	for(var i=0;i<childNodes.length;i++){
		var node = childNodes[i];
		jsonp[node.id] = node.ui.checkbox.checked;
	 	//alert(node.id+":"+node.ui.checkbox.checked);
	}
	//return;
	/* var checkboxC = document.getElementsByName("checkboxC"); 
	var p = "";
    for(var i=0;i<checkboxC.length;i++){
         if(checkboxC[i].checked){
         	p = p + checkboxC[i].value+",";
       }
    }   */    
    //Ext.util.JSON.encode({aaa:111})
	//cform
	Ext.Ajax.request({
		method: 'POST',
        async: true,
        form: 'cform',
        params : {'jsonp':Ext.util.JSON.encode(jsonp)},
        //timeout :30000,//按毫秒计算
		url: "<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.fxyp.Kqzsjzhj&eventNames=saveOrg_config",
		success: function(resData){
			//alert();
			var cfg = Ext.util.JSON.decode(resData.responseText);
			if(0==cfg.messageCode){
				
				eval(cfg.elementsScript);
				realParent.radow.doEvent('noticeSetgrid.dogridquery');
				parent.Ext.getCmp("setOrgConfig").close();
			}else{
				Ext.Msg.alert('系统提示',cfg.mainMessage)
			}
			
				
		},
		failure : function(res, options){ 
			Ext.Msg.hide();
			alert("网络异常！");
		}  
	});
}


var scrollFunc=function(e){
   //var direct=0;
   //e=e || window.event;
   //alert(e.clientX)
   treed.scrollTop//=treed.scrollTop+1

}
Ext.onReady(function() {
	$('#mntp00').val(realParent.$('#mntp00').val())
	$('#mntp05').val(realParent.$('#mntp05').val())
	
	
    var Tree = Ext.tree;
    var tree = new Tree.TreePanel( {
  	  id :'group',
        el : 'tree-div',//目标div容器
        split:false,
        width: 380,
        rootVisible: true,
        autoScroll : true,
        enableDD: true,
        animate : true,
        border:false,
        containerScroll : true,
       
        checkModel: 'multiple',
        loader : new Tree.TreeLoader( {
      	  baseAttrs: { uiProvider: Ext.tree.TreeCheckNodeUI },
      	  dataUrl : '<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.fxyp.Kqzsjzhj&eventNames=org_config_tree'
      	  ,baseParams: {mntp00:realParent.$('#mntp00').val(),mntp05:realParent.$('#mntp05').val()}	
        }),
        listeners:{
      	'expandnode':function(node){
      		//获取展开节点的信息
      		
      	}/* ,
      	'startdrag':function(a,b,c){
      		//alert(b.id);
      		//treed.scrollTop=c
      	} */
      }
    });
    var root = new Tree.AsyncTreeNode( {
  	    checked : false,
        text :  '单位名称显示',
        iconCls : '',
        draggable : false,
        expanded:true,
        id : '-1',
        listeners:{
          	'checkchange':function(node,checked){
	          	for(var i=0;i<node.childNodes.length;i++){
	    		 	node.childNodes[i].ui.checkbox.checked=checked;
	    		}
          	}
          }
    });
    tree.setRootNode(root);
    /* tree.on("check",function(node,checked){
    	for(var i=0;i<node.childNodes.length;i++){
		 	node.childNodes[i].ui.checkbox.checked=true;
		}
    }); */
  	  
  	 
  	  
    tree.render();
    root.expand();
    var treed =  document.getElementById('tree-div');
    try{
    	document.attachEvent('onmousewheel',scrollFunc);
    }catch(e){
    	
    }
    
    var viewSize = Ext.getBody().getViewSize();
    treed.style.height = viewSize.height-90;
    
	
}); 


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
    	
    	
    	Ext.override(Ext.tree.TreeNodeUI, {
    		onDblClick : function(e) {
    			e.preventDefault();
    	        if(this.disabled){
    	            return;
    	        }
    	        if(!this.animating && this.node.hasChildNodes() && !this.node.attributes.dblclick){
    	            //this.node.toggle();
    	        }
    	        if(this.node.disabled){//节点disabled 不促发下面事件
    	            return;
    	        }
    	        if(this.checkbox){
    	            //this.toggleCheck();
    	        }
    			this.fireEvent("dblclick", this.node, e);
    		},
    		
    		onClick : function(e){
                if(this.dropping){
                    e.stopEvent();
                    return;
                }
                if(this.fireEvent("beforeclick", this.node, e) !== false){
                    if(!this.disabled && this.node.attributes.href){
                        this.fireEvent("click", this.node, e);
                        return;
                    }
                    e.preventDefault();
                    if(this.disabled){
                        return;
                    }

                    if(this.node.attributes.singleClickExpand && !this.animating && this.node.hasChildNodes()){
                        this.node.toggle();
                    }
                    if(this.checkbox){
        	            this.toggleCheck();
        	        }
                    this.fireEvent("click", this.node, e);
                }else{
                    e.stopEvent();
                }
            }
    		
    	});
    	
    	
</script>


</html>
