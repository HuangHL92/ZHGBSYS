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
         collapsible: true,//True��ʾΪ����ǿ������ģ����Զ���Ⱦһ��չ��/�������ֻ���ť��ͷ��������      
         title: '��Ԫ������',//�����ı�    
         width: 185, 
         height:840,
         border : true,//���    
         autoScroll: true,//�Զ�������    
         containerScroll : true,
         animate : true,//����Ч��    
         rootVisible: false,//���ڵ��Ƿ�ɼ�    
         split: true,    
         loader : new Ext.tree.TreeLoader( {
             dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.weboffice.ExcelView&eventNames=orgTreeJsonData'
       	})     
     }); 
	    tree.on('click',treeClick); //tree����¼�
		var root = new Ext.tree.AsyncTreeNode({
			checked : true,
			text : "������λ",
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
		//addBookmark1(id);//�����¼�
	
	
	
}  */





Ext.onReady(function() {
	//newDoc();//��ʼ����word
    var tree = new Ext.tree.TreePanel({    
        region: 'center',
        el:'div1',
        collapsible: true,//True��ʾΪ����ǿ������ģ����Զ���Ⱦһ��չ��/�������ֻ���ť��ͷ��������      
        title: '�Զ��嵼��',//�����ı�    
        width: 300, 
        height:840,
        border : false,//���    
        autoScroll: true,//�Զ�������    
        containerScroll : true,
        animate : true,//����Ч��    
        rootVisible: false,//���ڵ��Ƿ�ɼ�    
        split: true,    
        loader : new Ext.tree.TreeLoader( {
            dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.weboffice.ExcelView&eventNames=orgTreeJsonData'
      	})     
    }); 
    tree.on('click',treeClick); //tree����¼�
	var root = new Ext.tree.AsyncTreeNode({
		checked : true,
		text : "������λ",
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
 var aa=node.parentNode.id; //���ڵ�-1,type name
//alert(aa);
/* if(aa==-1){
	return;
}  */

	var type = node.id;
	var name = node.text;
	//var pathname=node.pathname;
	//alert(pathname);
	deleteRow2(type,name);//�����¼�

} 




/*  function deleteRowRenderer(type,name){
	 alert(type);
	 alert(name); 
	//return "<a href=\"javascript:deleteRow2(&quot;"+type+","+name+"&quot;)\" ></a>";
	return "<a href=\"javascript:deleteRow2(&quot;"+type+","+name+"&quot;)\" >����</a>";
}  */
function deleteRow2(type,name){ 
var aaa=type+","+name;
	alert(aaa);
		 Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ�ȷ�ϵ�����",function(ida) { 
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