<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@page import="com.insigma.siis.local.pagemodel.weboffice.ExcelViewPageModel"%>
<%@page isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/pingyin.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/basejs/jquery-ui/jquery-1.10.2.js"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/basejs/ext/resources/css/ext-all.css"/>

<SCRIPT LANGUAGE=javascript>
/* Ext.onReady(function() {
	 var tree = new Ext.tree.TreePanel({    
         region: 'center',
         el:'div1',
         collapsible: true,//True表示为面板是可收缩的，并自动渲染一个展开/收缩的轮换按钮在头部工具条      
         title: '单元格属性',//标题文本    
         width: 185, 
         height:840,
         border : true,//表框    
         autoScroll: true,//自动滚动条    
         containerScroll : true,
         animate : true,//动画效果    
         rootVisible: false,//根节点是否可见    
         split: true,    
         loader : new Ext.tree.TreeLoader( {
             dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.weboffice.ExcelView&eventNames=orgTreeJsonData'
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


function treeClick(node,e){
	//var id = node.id.split(',')[0];
	var aa=node.parentNode.id; 
	alert(aa);
	if(aa==-1){
		return;
	}
	
		var id = node.id;
		id=aa+"_"+id;
		var name = node.text;
		//addBookmark1(id);//加载事件
	
	
	
}  */





Ext.onReady(function() {
	//newDoc();//初始化打开word
    var tree = new Ext.tree.TreePanel({    
        region: 'center',
        el:'div1',
        collapsible: true,//True表示为面板是可收缩的，并自动渲染一个展开/收缩的轮换按钮在头部工具条      
        title: '自定义导出',//标题文本    
        width: 300, 
        height:840,
        border : false,//表框    
        autoScroll: true,//自动滚动条    
        containerScroll : true,
        animate : true,//动画效果    
        rootVisible: false,//根节点是否可见    
        split: true,    
        loader : new Ext.tree.TreeLoader( {
            dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.weboffice.ExcelView&eventNames=orgTreeJsonData'
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

function treeClick(node,e){
//var id = node.id.split(',')[0];
 var aa=node.parentNode.id; //根节点-1,type name
//alert(aa);
/* if(aa==-1){
	return;
}  */

	var type = node.id;
	var name = node.text;
	//var pathname=node.pathname;
	//alert(pathname);
	deleteRow2(type,name);//加载事件

} 




/*  function deleteRowRenderer(type,name){
	 alert(type);
	 alert(name); 
	//return "<a href=\"javascript:deleteRow2(&quot;"+type+","+name+"&quot;)\" ></a>";
	return "<a href=\"javascript:deleteRow2(&quot;"+type+","+name+"&quot;)\" >导出</a>";
}  */
function deleteRow2(type,name){ 
var aaa=type+","+name;
	alert(aaa);
		 Ext.Msg.confirm("系统提示","是否确认导出？",function(ida) { 
			if("yes"==ida){
				//alert(window.opener.document.getElementById("test").value);
				window.opener.document.getElementById("test").value = aaa; 
			}else{
				return;;
			}		
		});	 
}




</SCRIPT>




</head>
<body>
<div style="float:left;"  id="div1">


</body>
</html>