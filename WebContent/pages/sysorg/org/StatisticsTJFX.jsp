<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>

<!-- 弹出窗的公共方法 -->
<script type="text/javascript">
    function tytj(param){
	    $h.openWin('GeneralStatistics','pages.statisticalanalysis.simpleanalysis.GeneralStatistics','自定义通用统计',700,500,param,'<%=request.getContextPath()%>');
    }
	function tjfx(param){ 
		$h.openWin('SimpleStatistics','pages.sysorg.org.SimpleStatistics','简单统计图',1000,650,param,'<%=request.getContextPath()%>');
	}
	function tjfx2(param){
		$h.openWin('TwoDStatisticsShow','pages.statisticalanalysis.simpleanalysis.TwoDStatisticsShow','二维统计图',997,645,param,'<%=request.getContextPath()%>');
	}
	function fixed_query(param){
		$h.openWin('TwoDimensionStatistics','pages.sysorg.org.TwoDimensionStatistics','二维统计图',1013,650,param,'<%=request.getContextPath()%>');
	}
	function ewedit(param){
		$h.openWin('TwoDStatistics','pages.statisticalanalysis.simpleanalysis.TwoDStatistics','自定义二维统计',1000,650,param,'<%=request.getContextPath()%>');
	}
</script>
<!--  <style type="text/css"> 
.x-tree-node-collapsed .x-tree-node-icon { 
background: url(image/u175.png); 
} 
.x-tree-node-expanded .x-tree-node-icon { 
background:transparent url(/hzb/image/u175.png); 
} 
.x-tree-node-leaf .x-tree-node-icon{ 
background:transparent url(/hzb/image/u175.png); 
} 
</style> -->


<script type="text/javascript">
 var node0 = new Ext.tree.TreeNode({ text:"基本信息类",id:"j" });
 var node1 = new Ext.tree.TreeNode({text:"干部统计类",id:"g"});
 var node2 = new Ext.tree.TreeNode({text:"其他类",id:"q" });
 var node3 = new Ext.tree.TreeNode({ text:"自定义统计类",id:"z" });
 
	Ext.onReady(function(){
		 var  treepanel = new Ext.tree.TreePanel({
			el:"tree_div",
			bodyStyle: 'background-color:white;',
			width:383,
			height:450,
			frame:true,
			bodyBorder :false,
			useArrows:true,
			autoScroll:true,
			animate:true,
			enableDD:false,
			containerScroll: true,
			rootVisible: false
		
			
		});
		var rootNode=new Ext.tree.TreeNode({text:"根"});
	     	
	    treepanel.setRootNode(rootNode);
	    rootNode.appendChild(node0);
		rootNode.appendChild(node1);  
	    rootNode.appendChild(node2);
	    rootNode.appendChild(node3);
	    
	    treepanel.render();
	    rootNode.expand();
	    treepanel.on('dblclick', treedbClick);
	    function treedbClick(node,e){
	    	document.getElementById("checknodeid").value=node.id;
	    	//alert(document.getElementById("checknodeid").value);
	    	if(node.id!="j"&&node.id!="g"&&node.id!="q"&&node.id!="z"){
	    		radow.doEvent("tjBtn.onclick");
	    	}
	      
	    };
	    treepanel.on('click',treeClick);
	    function treeClick(node,e){
	    	document.getElementById("checknodeid").value=node.id;
	    	var mark = node.id.split(",")[0];
	    	 if(mark.length<5){
	    		 if(node.id!="j"&&node.id!="g"&&node.id!="q"&&node.id!="z"){
	    			 document.getElementById("del").style.display='none';
	 		    	document.getElementById("edit").style.display='none';
	 		    	document.getElementById("starts").style.display='';
	 		    	document.getElementById("abc").style.width='113px';
	    		 }else{
	    			document.getElementById("del").style.display='none';
	 		    	document.getElementById("edit").style.display='none';
	 		    	document.getElementById("starts").style.display='none';
	 		    	document.getElementById("abc").style.width='169px'; 
	    		 }
	    	}else{
	    		document.getElementById("del").style.display='';
		    	document.getElementById("edit").style.display='';
		    	document.getElementById("starts").style.display='';
		    	document.getElementById("abc").style.width='49px';
	    	} 
	    }
	 });
	function addChild(i,name,nid,pid){
    var n0;
		if(pid=='1'){
			n0=new Ext.tree.TreeNode({text:name,id:nid+","+pid,icon:"image/u175.png"});
		}
		if(pid=='2'){
			n0=new Ext.tree.TreeNode({text:name,id:nid+","+pid,icon:"image/u333.png"});
		}
		if(i=='0'){
			node0.appendChild(n0);
		}
		if(i=='1'){
			//var n1=new Ext.tree.TreeNode({text:name,id:nid,icon:ic});
			node1.appendChild(n0);
		}	
		if(i=='2'){
			//var n2=new Ext.tree.TreeNode({text:name,id:nid,icon:ic});
			node2.appendChild(n0);
		}
		if(i=='3'){
			node3.appendChild(n0);
		}
	}

</script>


<!-- <div id="bar_div"></div>
<odin:toolBar property="btnToolBar" applyTo="bar_div">
	
	<odin:fill/>
	<odin:separator></odin:separator>
	<odin:textForToolBar text="常用统计列表" isLast="true"/>
	</odin:toolBar>  -->
<div id="tree_div">
	 
</div>
<div id="btn_div">
<table  >
<tr  >
	<td  id="abc" width="169px" ></td>
	<td  id="edit" style="display:none" >
	<odin:button text="编辑" property="editorBtn" ></odin:button>
	</td>
	<td width="50px"></td>
	<td  id="del" style="display:none"> 
	<odin:button text="删除" property="deleteBtn"></odin:button>
	</td>
	<td width="50px"></td>
	<td  id="starts"  style="display:none">
	<odin:button text="开始统计" property="tjBtn"></odin:button>
	</td>
	<td width="50px"></td>
	<td >
	<odin:button text="关闭" property="closeBtn"></odin:button>
	</td>
</tr>
</table>
</div>
<odin:hidden property="checknodeid"/>


