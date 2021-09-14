<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@include file="/comOpenWinInit.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>单项要素调研</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script src="<sicp3:rewrite forward='md5'/>"> </script>
<script src="<sicp3:rewrite forward='globals'/>"> </script>
<script type="text/javascript"src="<%=request.getContextPath()%>/basejs/ext/adapter/ext/ext-base.js"></script>
<script src="<%=request.getContextPath()%>/basejs/ext/ext-all.js"> </script>
<script src="<%=request.getContextPath()%>/basejs/odin.js"> </script>
<script src="<%=request.getContextPath()%>/js/echarts.js"> </script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/pingyin.js"></script>

<script src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js" type="text/javascript"></script>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/odin.css"/>
<script type="text/javascript">
Ext.onReady(function() {
	
	document.getElementById("left").style.height= document.body.clientHeight-3;
	document.getElementById("right").style.height=document.body.clientHeight-23;
	document.getElementById("center").style.height=document.body.clientHeight-23;
	document.getElementById("ry").style.height=document.body.clientHeight-43;
	document.getElementById("dw").style.height=document.body.clientHeight-43;
	document.getElementById("div2").style.height=document.body.clientHeight-420;
	
		var leftWidth=document.getElementById("left").style.width;
		//加载树
	    var tree = new Ext.tree.TreePanel({   
	     id:'codeTree',
         region: 'center',
         el:'div1',
         collapsible: true,//True表示为面板是可收缩的，并自动渲染一个展开/收缩的轮换按钮在头部工具条      
         title: '代码列表',//标题文本    
         width: leftWidth, 
         height:380,
         border : true,//表框    
         autoScroll: true,//自动滚动条    
         containerScroll : true,
         animate : true,//动画效果    
         rootVisible: false,//根节点是否可见    
         split: true,    
         loader : new Ext.tree.TreeLoader( {
             dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.templateconf.SingleElementSurvey&eventNames=orgTreeJsonData'
       	})     
     }); 
	    tree.on('click',treeClick); //tree点击事件
		var root = new Ext.tree.AsyncTreeNode({
			checked : true,
			text : "其他单位",
			iconCls : "picOrg",
			draggable : true,
			id : "-1"
		});
		tree.setRootNode(root);
		tree.render();
		root.expand(false,true,callback);
		var callback = function (node){
			if(node.hasChildNodes()) {
				node.eachChild(function(child){
					child.expand();
				})
			}
		}
		
});

$(function(){
	//document.getElementById("ry").style.display="none";
	document.getElementById("ry").style.visibility="hidden";//隐藏
	document.getElementById("dw").style.visibility="hidden";//隐藏
	//document.getElementById("MainBody").style.visibility="visible";//显
});

function treeClick(node,e){
	
}

function seek(){
	 //获取树的选中项
    var tree = Ext.getCmp('codeTree');
    var nodes = tree.getRootNode().childNodes; 
    var queryCondition="";
    for (var j = 0; j < nodes.length; j++) {  
        var node = tree.getRootNode().childNodes[j];  
        if (node.hasChildNodes()) {  
            for (var i = 0; i < node.childNodes.length; i++) {  
                if (node.childNodes[i].getUI().checkbox.checked) {  
                     //alert(node.childNodes[i].id+'---'+node.childNodes[i].text);  
                     var childNode=node.childNodes[i];
                     var parentNode=childNode.parentNode;
                    // alert(parentNode.id+"---"+childNode.id);
                     queryCondition+=parentNode.id+"---"+childNode.id+"@@";
                }  
            }  
        }  
    } 
    if(queryCondition != "" && queryCondition != null){	
      queryCondition=queryCondition.substring(0,queryCondition.length-2);
      //alert(queryCondition);
      document.getElementById('queryCondition').value=queryCondition;
      //radow.doEvent("seek",queryCondition);
      radow.doEvent("dwlb.dogridquery");
      document.getElementById("dw").style.visibility="visible";//显示
      document.getElementById("ry").style.visibility="hidden";//隐藏
    }else{
    	Ext.Msg.show({  
    	    title:'提示',  
    	    msg: '请选择条件后查询',  
    	    buttons: Ext.Msg.OK,  
    	    icon: Ext.Msg.INFO     //注意此处为INFO  
    	}); 
    }
   
}


function showry(val){
	//alert(val);
	// radow.doEvent("showry",val);
	document.getElementById('ryids').value=val;
	radow.doEvent("peoplelb.dogridquery");
	document.getElementById("ry").style.visibility="visible";//显
}


function showPeople(personId){
	//alert(personId);
	radow.doEvent('showPeople',personId);
	
}


function doRowDbClick(grid,rowIndex,event){
	var record = grid.getStore().getAt(rowIndex);   //获取当前行的数据
	
	var dw=record.get("0");
	var ry=record.get("1");
	
	showry(ry);
}

function clear(){
	var nodes = Ext.getCmp('codeTree').getChecked();
	if (nodes && nodes.length) {
		for (var i = 0; i < nodes.length; i++) {
			//设置UI状态为未选中状态
			nodes[i].getUI().toggleCheck(false);
			//设置节点属性为未选中状态
			nodes[i].attributes.checked = false;
		}
	}

}


</script>
<style type="text/css">
#left{ 
float:left; width:20%;border:1px solid #000;
} 
#right{ 
float:right;width:40%;border:1px solid #000;
} 
#center{ 
border:1px solid #000;
} 

</style>
</head>
<body>
 	 <div id="left" >
 	 	<span id="span1" style="height: 20px;width: 100%;text-align: center;border-bottom: 1px solid #000;"><br><font face="黑体">结构要素</font></span>
 	 	<div id="div1"></div>
 	 	<div id="div2" style="width:100%;text-align: center;border-bottom: 1px solid #000;border-top: 1px solid #000;">
 	 		<table>
 	 			<tr>
 	 				<td align="right"><font face="黑体" size="2">组合条件:</font></td>
 	 				<td><odin:radio property="zhtj" value="and" label="并且" title="\"checked=\"" /></td>
 	 				<td><odin:radio property="zhtj" value="or" label="或者"></odin:radio></td>
 	 			</tr>
 	 			<tr>
 	 				<td align="right"><font face="黑体" size="2">输出结果:</font></td>
 	 				<td><odin:radio property="scjg" value="yes" label="满足条件" title="\"checked=\""/></td>
 	 				<td><odin:radio property="scjg" value="no" label="不满足条件"></odin:radio></td>
 	 			</tr>
 	 			<tr>
 	 				<td><odin:button text="查找" handler="seek"></odin:button></td>
 	 				<td><odin:button text="重选" handler="clear"></odin:button></td>
 	 				<td></td>
 	 			</tr>
 	 		</table>
 	 	</div>
 	 </div> 
	 <div id="right" >
	 	<span id="span1" style="height: 20px;width: 100%;text-align: center;border-bottom: 1px solid #000;"><br><font face="黑体">干部列表</font></span>
	 	<div id="ry" style="overflow-y:auto; overflow-x:auto;width:100%; height:200px;">
	 		<!-- <table width="100%"></table> -->
	 		<odin:editgrid2 property="peoplelb" bbarId="pageToolBar" autoFill="true" url="/" pageSize="10" isFirstLoadData="false" forceNoScroll="true" remoteSort="false"
					  sm="row" height="$('#right').height()-45">
			
				<odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
					<%-- <odin:gridDataCol name="logchecked" /> --%>
					<odin:gridDataCol name="a0000" />
					<odin:gridDataCol name="a0101" />
					<odin:gridDataCol name="a0107" isLast="true"/>
					
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<%-- <odin:gridColumn dataIndex="logchecked" header="selectall" gridName="summaryFormula" edited="true" width="8" editor="checkbox"></odin:gridColumn> --%>
					<odin:gridEditColumn header="主键" dataIndex="a0000" align="center" edited="false" hidden="true"
						 editor="text"/>
					<odin:gridEditColumn header="姓名" dataIndex="a0101" align="center" edited="false" 
						 editor="text" />
					<odin:gridEditColumn header="出生日期" dataIndex="a0107" align="center" edited="false" 
						 editor="text" isLast="true"/>
		
				</odin:gridColumnModel>
			</odin:editgrid2>
	 	</div>
	 </div> 
	 <div id="center" style="">
	 	<span id="span2" style="height: 20px;width: 100%;text-align: center;border-bottom: 1px solid #000;"><br><font face="黑体">单位列表</font></span>
	 	
	 	<div id="dw" style="overflow-y:auto; overflow-x:auto;width:100%; height:200px;">
	 		<!-- <table></table> -->
	 		<odin:editgrid2 property="dwlb" bbarId="pageToolBar" autoFill="true" url="/" pageSize="10" isFirstLoadData="false" forceNoScroll="true" remoteSort="false" rowDbClick="doRowDbClick"
					  sm="row" height="$('#right').height()-45" >
			
				<odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
					<%-- <odin:gridDataCol name="logchecked" /> --%>
					<odin:gridDataCol name="0" />
					<odin:gridDataCol name="1" isLast="true"/>
					
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<%-- <odin:gridColumn dataIndex="logchecked" header="selectall" gridName="summaryFormula" edited="true" width="8" editor="checkbox"></odin:gridColumn> --%>
					<odin:gridEditColumn header="单位名称" dataIndex="0" align="center" edited="false" 
						 editor="text"/>
					<odin:gridEditColumn header="人员" dataIndex="1" align="center" edited="false" hidden="true"
						 editor="text" isLast="true"/>
		
				</odin:gridColumnModel>
			</odin:editgrid2>
	 	</div>
	 </div> 
	 <odin:hidden property="ryids"/>
	 <odin:hidden property="queryCondition"/>
</body>
</html>