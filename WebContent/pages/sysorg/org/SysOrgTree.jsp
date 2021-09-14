<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ page import="com.insigma.siis.local.pagemodel.sysorg.org.SysOrgTreePageModel"%>
<%
String closewin = request.getParameter("closewin");
String property = request.getParameter("property");
%>
<style>
	.x-panel-bwrap,.x-panel-body{
		height: 100%
	}
	.picOrg {
		background-image:url("<%=request.getContextPath()%>/pages/sysorg/org/images/companyOrgImg2.png") !important;
	}
	.picInnerOrg {
		background-image:url("<%=request.getContextPath()%>/pages/sysorg/org/images/insideOrgImg1.png") !important;
	}
	.picGroupOrg {
		background-image:url("<%=request.getContextPath()%>/pages/sysorg/org/images/groupOrgImg1.png") !important;
	}
</style>
<script type="text/javascript" src="commform/basejs/json2.js"></script>
<script type="text/javascript">
var continueCount = 0;//连续选择计数
var changeNode = {};//每次操作的记录
var childNodes = "";
var continueOne ;//连续选择传入第一个对象
var top ="" ;//
var tag =0;
var nocheck=1;
function existsChoose(){
	var existsCheckbox=document.getElementById('existsCheckbox');
	var continueCheckbox=document.getElementById('continueCheckbox');
	if(existsCheckbox.checked==false){
		existsCheckbox.checked=false;
	}else{
		existsCheckbox.checked=true;
		continueCheckbox.checked=false;
	}
}
function continueChoose(){
	var existsCheckbox=document.getElementById('existsCheckbox');
	var continueCheckbox=document.getElementById('continueCheckbox');
	if(continueCheckbox.checked==false){
		continueCheckbox.checked=false;
	}else{
		continueCount=0;
		tag = 0;
		continueCheckbox.checked=true;
		existsCheckbox.checked=false;
	}
}
Ext.onReady(function() {
	  document.getElementById('closewin').value='<%=closewin%>';
	  document.getElementById('property').value='<%=property%>';
	  var man = document.getElementById('manager').value;
      var Tree = Ext.tree;
      var tree = new Tree.TreePanel( {
    	  id :'group',
          el : 'tree-div',//目标div容器
          split:false,
          width: 240,
          rootVisible: false,
          autoScroll : true,
          animate : true,
          border:false,
          enableDD : false,
          containerScroll : true,
          checkModel: 'multiple',
          loader : new Tree.TreeLoader( {
        	  baseAttrs: { uiProvider: Ext.tree.TreeCheckNodeUI },
        	  dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysorg.org.SysOrgTree&eventNames=orgTreeJsonData&tag=1'
        	  //dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.sysorg.org.SysOrg&eventNames=orgTreeJsonData&tag=1'
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
      var root = new Tree.AsyncTreeNode( {
    	    checked : false,
            text :  document.getElementById('ereaname').value,
            iconCls : document.getElementById('picType').value,
            draggable : false,
            expanded:true,
            id : document.getElementById('ereaid').value//,//默认的node值：?node=-100
            //href:"javascript:radow.doEvent('querybyid','"+document.getElementById('ereaid').value+"')"
      });
      tree.setRootNode(root);
      tree.on("check",function(node,checked){
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
		        	//changeNode += node.id+":"+checked+":1,";
		        	changeNode[node.id]=changeNode[node.id]?(changeNode[node.id]+checked+":1,"):(checked+":1,");
		        }else{
		        	//changeNode += node.id+":"+checked+":2,";
		        	changeNode[node.id]=changeNode[node.id]?(changeNode[node.id]+checked+":2,"):(checked+":2,");
		        }
		  }else{
		  		//changeNode += node.id+":"+checked+":0,";
		  		changeNode[node.id]=changeNode[node.id]?(changeNode[node.id]+checked+":0,"):(checked+":0,");
		  }
      	  //alert(changeNode);
       }); //注册"check"事件
      tree.render();
      root.expand();
      var tree = Ext.getCmp("group");
}); 
function findNode(node,one,two){
	if(tag==1){
		return one;
	}
	if(tag==2){
		return two;
	}
	if(node.id==one.id){
		tag=1;
		return one;
	}
	if(node.id==two.id){
		tag=2;
		return two;
	}
	if(node.childNodes.length>0){
		for(var i =0 ;i<node.childNodes.length;i++){
			if(tag!=0){
				return;
			}
			findNode(node.childNodes[i],one,two);
		}
	}
}
function upOrDown(one,two){
    var tree = Ext.getCmp("group");
	if(tree.getRootNode().id==two.id){
		return two;
	}
	if(tree.getRootNode().id==one.id){
		return one;
	}
	var node = tree.getRootNode() ;
	//一直往下找
	if(node.childNodes.length>0){
		for(var i =0 ;i<node.childNodes.length;i++){
			if(tag!=0){
				return one;
			}
			findNode(node.childNodes[i],one,two);
		}
	}    
}
function continueCheck(one,two){
	//判断选择第二次的方向，如果朝上，执行朝上方法，否则朝下执行
	//判断是否有上级节点，上级节点往下找，如果没有上级节点返回false
	upOrDown(one,two);
	if(tag==1){
		if(continueCheckDownLoop(one,two.id)==1){
			two.attributes.tag="2";
			return 1;
		}
	}else{
		if(continueCheckDownLoop(two,one.id)==1){
			one.attributes.tag="2";
			return 1;
		}
	}
}

function continueCheckUpLoop(one,two){
	one.attributes.tag="1";
	if(one.id==two){
		return 1;
	}else{
		if(!one.ui.checkbox.checked&&nocheck==1){
			one.ui.checkbox.checked=true;
			//changeNode += one.id+":"+one.ui.checkbox.checked+":1,";	
			changeNode[one.id]=changeNode[one.id]?(changeNode[one.id]+one.ui.checkbox.checked+":1,"):(one.ui.checkbox.checked+":1,");
		}
	}
	try{
		if(one.parentNode.parentNode.childNodes.length>0){
			for(var i =0 ;i<one.parentNode.parentNode.childNodes.length;i++){
				if(one.parentNode.parentNode.childNodes[i].id==one.parentNode.id){
					if(i+1<one.parentNode.parentNode.childNodes.length){
						//找下级如果找不到找平级
						if(continueCheckDownLoop(one.parentNode.parentNode.childNodes[i+1],two)==1){
							return 1;
						}else{
							if(continueCheckSameLoop(one.parentNode.childNodes[i+1],two)==1){
								return 1;
							}
						}
					}else{
						if(continueCheckUpLoop(one.parentNode.parentNode.childNodes[0],two)==1){
							return 1;
						}
					}
				}
			}
		}
	}catch (e) { 

	}
}
function continueCheckDownLoop(one,two){
	one.attributes.tag="1";
	if(one.id==two){
		return 1;
	}else{
		if(!one.ui.checkbox.checked){
			one.ui.checkbox.checked=true;
			//changeNode += one.id+":"+one.ui.checkbox.checked+":1,";	
			changeNode[one.id]=changeNode[one.id]?(changeNode[one.id]+one.ui.checkbox.checked+":1,"):(one.ui.checkbox.checked+":1,");
		}
	}
	var node = one ;
	//一直往下找
	if(node.childNodes.length>0){
		for(var i =0 ;i<node.childNodes.length;i++){
			if(continueCheckDownLoop(node.childNodes[i],two)==1){
				return 1;
			}
		}
	}else{
		//平级查找
		nocheck=0;
		if(continueCheckSameLoop(one,two)==1){
			return 1;
		}
	}
}

function continueCheckSameLoop(one,two){
	one.attributes.tag="1";
	if(one.id==two){
		return 1;
	}else{
		if(!one.ui.checkbox.checked&&nocheck==1){
			one.ui.checkbox.checked=true;
			//changeNode += one.id+":"+one.ui.checkbox.checked+":1,";
			changeNode[one.id]=changeNode[one.id]?(changeNode[one.id]+one.ui.checkbox.checked+":1,"):(one.ui.checkbox.checked+":1,");
		}
	}
	var node = one ;
	if(node.parentNode.childNodes.length>0){
		for(var i = 0 ;i<node.parentNode.childNodes.length;i++){
			if(node.parentNode.childNodes[i].id==one.id){
				nocheck=1;
				//如果没有平级的下一个node，那么我们找上级
				if(i+1<node.parentNode.childNodes.length){
					//找下级如果找不到找平级
					if(continueCheckDownLoop(node.parentNode.childNodes[i+1],two)==1){
						return 1;
					}else{
						if(continueCheckSameLoop(node.parentNode.childNodes[i+1],two)==1){
							return 1;
						}
					}
				}else{
					//找上级
					nocheck=0;
					if(continueCheckUpLoop(one,two)){
						return 1;
					}
				}
			}
		}
	}
}

function loop(node){
	  if(node.ui.checkbox.checked==true){
		  //node.expand();
		  if(node.childNodes.length>0){
			  for(var i =0;i<node.childNodes.length ;i++){
			  	  node.childNodes[i].attributes.tag="1";
				  node.childNodes[i].ui.checkbox.checked=true;
				  //changeNode += node.childNodes[i].id+":"+node.childNodes[i].ui.checkbox.checked+":1,";
				  changeNode[node.childNodes[i].id]=changeNode[node.childNodes[i].id]?(changeNode[node.childNodes[i].id]+node.childNodes[i].ui.checkbox.checked+":1,"):(node.childNodes[i].ui.checkbox.checked+":1,");
				  loop(node.childNodes[i]);
			  }
		  }
	  }else{
		  node.ui.checkbox.checked=false;
		  for(var i =0;i<node.childNodes.length ;i++){
		  	  node.childNodes[i].attributes.tag="0";
			  node.childNodes[i].ui.checkbox.checked=false;
			  //changeNode += node.childNodes[i].id+":"+node.childNodes[i].ui.checkbox.checked+":0,";
			  changeNode[node.childNodes[i].id]=changeNode[node.childNodes[i].id]?(changeNode[node.childNodes[i].id]+node.childNodes[i].ui.checkbox.checked+":0,"):(node.childNodes[i].ui.checkbox.checked+":0,");
			  loop(node.childNodes[i]);
		  }
	  }
}
function getValue(){   
    var URLParams = new Array();    
    var aParams = document.location.search.substr(1).split('&'); 
    for (i=0; i < aParams.length;i++){    
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


	
	radow.doEvent('dogrant',JSON.stringify(nodeRealSelectedSet));
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
var oldSelectIdArray=new Array();
var count = 0;//计数器
function doQueryNext() {
	var nextProperty =document.getElementById('nextProperty').value;
	if(nextProperty==""){
		return;
	}
    var tree = Ext.getCmp("group");
	var node = tree.getRootNode();
	oldSelectIdArray.length = 0;//清楚
	loopNext(node,nextProperty);
	oldSelectIdArray[count%oldSelectIdArray.length].select();
	count+=1;
}

function loopNext(node,nextProperty){
	  if(node.childNodes.length>0){
		  for(var i =0;i<node.childNodes.length ;i++){
			  if(node.childNodes[i].text.indexOf(nextProperty)>=0){
				  oldSelectIdArray.push(node.childNodes[i]);
				  loopNext(node.childNodes[i],nextProperty);
			  }else{
				  loopNext(node.childNodes[i],nextProperty);
			  }
		  }
	  }
}

function grantTabChange(tabObj,item){
	if(item.getId()=='tab2'){
		odin.ext.getCmp('resourcegrid').view.refresh(true);
	}
}

function returnWin (returnValue) {
	if(window.opener) { //解决Chrome浏览器的兼容问题
		window.opener.Sure(returnValue);
 	}else{
 		window.returnValue=returnValue; //返回值
 	} 
	window.close();
}

function OnInput (e) {
	oldSelectIdArray.length = 0;
	loopNextTag=0;
	oldSelectCount=0;
	oldSelectId="1";
	count=0;
}
</script>

<%
	String ereaname = (String) (new SysOrgTreePageModel().areaInfo.get("areaname"));
	String ereaid = (String) (new SysOrgTreePageModel().areaInfo.get("areaid"));
	String manager = (String) (new SysOrgTreePageModel().areaInfo.get("manager"));
	String picType = (String)(new SysOrgTreePageModel().areaInfo.get("picType"));
%>

<div>
			<table >
				<tr>
					<td style="padding-left: 30">
					<input type="checkbox"  id="continueCheckbox" onclick="continueChoose()"><font style="font-size: 13">连续选择</font></td>
					<td style="padding-left: 30">
					<input type="checkbox"  id="existsCheckbox" onclick="existsChoose()" ><font style="font-size: 13">包含下级</font></td>
				</tr>
			</table>
<table>
	<tr>
		<td>
		<table>
			<tr>
				<td >
				<div id="tree-div"
					style="overflow: auto; height: 330px; border: 2px solid #c3daf9;" ></div>
				</td>
			</tr>
			<tr>
				<odin:hidden property="checkedgroupid" />
				<odin:hidden property="forsearchgroupid" />
				<odin:hidden property="ereaname" value="<%=ereaname%>" />
				<odin:hidden property="ereaid" value="<%=ereaid%>" />
				<odin:hidden property="manager" value="<%=manager%>" />
				<odin:hidden property="picType" value="<%=picType%>" />
				<odin:hidden property="closewin" />
				<odin:hidden property="property" />
			</tr>
		</table>
		</td>
	</tr>
</table>
<table>
			<tr>
			<td></td>
			<td><label style="font-size: 12" >定位机构</label></td>
			<td><input type="text" id ="nextProperty" size="17" class=" x-form-text x-form-field"  onpropertychange="OnInput (this)"></td>
			<td align="right">
			<odin:button property="selectOrgsBtn" text="下一个" handler="doQueryNext"  /></td>
			</tr>
			<tr>
			<td width="20"></td> 		
			<td align="right"><odin:button property="selectOrgsBtn" text="确定" handler="doQuery"></odin:button></td>
			<td align="right"><odin:button property="cancelBtn" text="取消"></odin:button></td>
			</tr>
</table>
</div>
