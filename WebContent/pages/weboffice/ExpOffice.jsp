<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@page import="com.insigma.siis.local.pagemodel.weboffice.ExpOfficePageModel"%>
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
Ext.onReady(function() {
	//newDoc();//��ʼ����word
	var nodemenu;
    var tree = new Ext.tree.TreePanel({    
        region: 'center',
        el:'div1',
        collapsible: true,//True��ʾΪ����ǿ������ģ����Զ���Ⱦһ��չ��/�������ֻ���ť��ͷ��������      
        title: '�Զ��嵼��',//�����ı�    
        width: 280, 
        height:200,
        border : false,//���    
        autoScroll: true,//�Զ�������    
        containerScroll : true,
        animate : true,//����Ч��    
        rootVisible: false,//���ڵ��Ƿ�ɼ�    
        autoHeight:true,
        split: true,    
        loader : new Ext.tree.TreeLoader( {
            dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.weboffice.ExpOffice&eventNames=orgTreeJsonData'
      	})     
    }); 
    tree.on('click',treeClick); //tree����¼�
    tree.on('contextmenu',rigthd);
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
 var id=node.parentNode.id; //���ڵ�-1,type name
 if(id==-1){
	 return;
 }
	var type = node.id;
	var name = node.text;
	var xz = "xz";
	var aaa=id+","+type+","+name+","+xz;
	deleteRow2(aaa);//�����¼�

} 

function rigthd(node,e){
	 var id=node.parentNode.id; //���ڵ�-1,type name
	  if(id==-1){
	 	 return;
	  }
	node.select();
	e.preventDefault();
/* 	if(id==1||id==2){
		nodemenu = new Ext.menu.Menu({
			items:[{
				text : "����",
				//id   : "addNode",
				//icon : 'module_img/ZWHZYQ_001_007/level3_16.gif',
			  handler:function(){
				 	var type = node.id;
				 	var name = node.text;
				 	var xz = "xz";
				 	var aaa=id+","+type+","+name+","+xz;
				 	deleteRow2(aaa);//�����¼�
			  }
			  }
			  ]  
	 		
		});
	}else{ */
		nodemenu = new Ext.menu.Menu({
			items:[{
				text : "����",
				//id   : "addNode",
				//icon : 'module_img/ZWHZYQ_001_007/level3_16.gif',
			  handler:function(){
				 	var type = node.id;
				 	var name = node.text;
				 	var xz = "xz";
				 	var aaa=id+","+type+","+name+","+xz;
				 	deleteRow2(aaa);//�����¼�
			  }
			  },
			  
			  {
					text : "��ӡ",
					//id   : "delete",
				  //iconCls:'leaf',//�Ҽ�����ǰ��СͼƬ  
				  handler:function(){
					 	var type = node.id;
					 	var name = node.text;
					 	var dy = "dy";
					 	var aaa=id+","+type+","+name+","+dy;
					 	deleteRow3(aaa);//�����¼�
				  }
				  }
			  
			  ]  
	 		
		}); 
		 var xy=e.getXY();
		 var x1=xy[0];
		 var y1=xy[1];
		 var xys= new Array();
		 xys[0]=x1;
		 if(y1>=340){
			 xys[1]=338;
		 }else{
			 xys[1]=y1; 
		 }
		 
		 nodemenu.showAt(xys); 
}

function deleteRow2(aaa){ 
	   Ext.MessageBox.minWidth = 240;
		 Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ����ת����<label><input type='radio' name='type' id='type1' value='yes' checked='checked'/>��</label><label><input type='radio' name='type' id='type1' value='no'/>��</label>",function(ida) { 
			if("yes"==ida){
				var rad=$('input:radio:checked').val();
				aaa=aaa+","+rad
				//window.opener.document.getElementById("test").value = aaa; 
				dialogArguments.document.getElementById("test").value = aaa;
				//alert(dialogArguments.document.getElementById("test").value);
				window.close();
			}else{
				return;;
			}		
		});	 
}
function deleteRow3(aaa){ 
	/*  Ext.MessageBox.minWidth = 240;
		 Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ����ת����<label><input type='radio' name='type' id='type1' value='yes'/>��</label><label><input type='radio' name='type' id='type1' value='no'/>��</label>",function(ida) { 
			if("yes"==ida){ */
				var rad="yes";
				aaa=aaa+","+rad
				//alert(aaa);
				//window.opener.document.getElementById("test").value = aaa; 
				dialogArguments.document.getElementById("test1").value = aaa;
				window.close();
			/* }else{
				return;;
			}		
		});	  */
}

</SCRIPT>

</head>
<body>
<div style="float:left; overflow-y:auto; height: 400px;width: :100px;"  id="div1" >


</body>
</html>