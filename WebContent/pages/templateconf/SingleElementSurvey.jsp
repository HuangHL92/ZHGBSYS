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
<title>����Ҫ�ص���</title>
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
		//������
	    var tree = new Ext.tree.TreePanel({   
	     id:'codeTree',
         region: 'center',
         el:'div1',
         collapsible: true,//True��ʾΪ����ǿ������ģ����Զ���Ⱦһ��չ��/�������ֻ���ť��ͷ��������      
         title: '�����б�',//�����ı�    
         width: leftWidth, 
         height:380,
         border : true,//���    
         autoScroll: true,//�Զ�������    
         containerScroll : true,
         animate : true,//����Ч��    
         rootVisible: false,//���ڵ��Ƿ�ɼ�    
         split: true,    
         loader : new Ext.tree.TreeLoader( {
             dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.templateconf.SingleElementSurvey&eventNames=orgTreeJsonData'
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

$(function(){
	//document.getElementById("ry").style.display="none";
	document.getElementById("ry").style.visibility="hidden";//����
	document.getElementById("dw").style.visibility="hidden";//����
	//document.getElementById("MainBody").style.visibility="visible";//��
});

function treeClick(node,e){
	
}

function seek(){
	 //��ȡ����ѡ����
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
      document.getElementById("dw").style.visibility="visible";//��ʾ
      document.getElementById("ry").style.visibility="hidden";//����
    }else{
    	Ext.Msg.show({  
    	    title:'��ʾ',  
    	    msg: '��ѡ���������ѯ',  
    	    buttons: Ext.Msg.OK,  
    	    icon: Ext.Msg.INFO     //ע��˴�ΪINFO  
    	}); 
    }
   
}


function showry(val){
	//alert(val);
	// radow.doEvent("showry",val);
	document.getElementById('ryids').value=val;
	radow.doEvent("peoplelb.dogridquery");
	document.getElementById("ry").style.visibility="visible";//��
}


function showPeople(personId){
	//alert(personId);
	radow.doEvent('showPeople',personId);
	
}


function doRowDbClick(grid,rowIndex,event){
	var record = grid.getStore().getAt(rowIndex);   //��ȡ��ǰ�е�����
	
	var dw=record.get("0");
	var ry=record.get("1");
	
	showry(ry);
}

function clear(){
	var nodes = Ext.getCmp('codeTree').getChecked();
	if (nodes && nodes.length) {
		for (var i = 0; i < nodes.length; i++) {
			//����UI״̬Ϊδѡ��״̬
			nodes[i].getUI().toggleCheck(false);
			//���ýڵ�����Ϊδѡ��״̬
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
 	 	<span id="span1" style="height: 20px;width: 100%;text-align: center;border-bottom: 1px solid #000;"><br><font face="����">�ṹҪ��</font></span>
 	 	<div id="div1"></div>
 	 	<div id="div2" style="width:100%;text-align: center;border-bottom: 1px solid #000;border-top: 1px solid #000;">
 	 		<table>
 	 			<tr>
 	 				<td align="right"><font face="����" size="2">�������:</font></td>
 	 				<td><odin:radio property="zhtj" value="and" label="����" title="\"checked=\"" /></td>
 	 				<td><odin:radio property="zhtj" value="or" label="����"></odin:radio></td>
 	 			</tr>
 	 			<tr>
 	 				<td align="right"><font face="����" size="2">������:</font></td>
 	 				<td><odin:radio property="scjg" value="yes" label="��������" title="\"checked=\""/></td>
 	 				<td><odin:radio property="scjg" value="no" label="����������"></odin:radio></td>
 	 			</tr>
 	 			<tr>
 	 				<td><odin:button text="����" handler="seek"></odin:button></td>
 	 				<td><odin:button text="��ѡ" handler="clear"></odin:button></td>
 	 				<td></td>
 	 			</tr>
 	 		</table>
 	 	</div>
 	 </div> 
	 <div id="right" >
	 	<span id="span1" style="height: 20px;width: 100%;text-align: center;border-bottom: 1px solid #000;"><br><font face="����">�ɲ��б�</font></span>
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
					<odin:gridEditColumn header="����" dataIndex="a0000" align="center" edited="false" hidden="true"
						 editor="text"/>
					<odin:gridEditColumn header="����" dataIndex="a0101" align="center" edited="false" 
						 editor="text" />
					<odin:gridEditColumn header="��������" dataIndex="a0107" align="center" edited="false" 
						 editor="text" isLast="true"/>
		
				</odin:gridColumnModel>
			</odin:editgrid2>
	 	</div>
	 </div> 
	 <div id="center" style="">
	 	<span id="span2" style="height: 20px;width: 100%;text-align: center;border-bottom: 1px solid #000;"><br><font face="����">��λ�б�</font></span>
	 	
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
					<odin:gridEditColumn header="��λ����" dataIndex="0" align="center" edited="false" 
						 editor="text"/>
					<odin:gridEditColumn header="��Ա" dataIndex="1" align="center" edited="false" hidden="true"
						 editor="text" isLast="true"/>
		
				</odin:gridColumnModel>
			</odin:editgrid2>
	 	</div>
	 </div> 
	 <odin:hidden property="ryids"/>
	 <odin:hidden property="queryCondition"/>
</body>
</html>