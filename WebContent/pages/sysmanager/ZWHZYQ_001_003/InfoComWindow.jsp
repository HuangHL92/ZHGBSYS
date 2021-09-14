<%@page import="com.insigma.odin.framework.sys.manager.sysmanager.comm.entity.Sysopright"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@page import="com.insigma.siis.local.pagemodel.sysmanager.group.GroupManagePageModel"%>
<link rel="stylesheet" type="text/css" href="../../resources/css/ext-all.css" />

    <!-- GC -->
 	<!-- LIBS -->
 	<script type="text/javascript" src="../../adapter/ext/ext-base.js"></script>
 	<!-- ENDLIBS -->

    <script type="text/javascript" src="../../ext-all.js"></script>

    <script type="text/javascript" src="basejs/ColumnNodeUI.js"></script>

    <!-- Common Styles for the examples -->
    <link rel="stylesheet" type="text/css" href="../examples.css" />

    <link rel="stylesheet" type="text/css" href="basejs/ext/ux/css/column-tree.css" />
<script  type="text/javascript" src="basejs/jquery.js"></script>
<script  type="text/javascript" src="basejs/jquery.min.js"></script>
<script  type="text/javascript">
var ids="";
Ext.onReady(function(){
	var userid = getValue();
    var tree = new Ext.tree.ColumnTree({
        el:'tree',
        width:565,
        autoHeight:true,
        rootVisible:false,
        autoScroll:true,
//        title: '信息项权限组列表',
        
        columns:[{
            header:'信息项权限组名称',
            width:350,
            dataIndex:'task'
        },{
            header:'浏览',
            width:0,
            dataIndex:'duration'
        },{
            header:'维护',
            width:106,
            dataIndex:'user'
        }],

        loader: new Ext.tree.TreeLoader({
            dataUrl:'radowAction.do?method=doEvent&pageModel=pages.sysmanager.ZWHZYQ_001_003.InfoComWindow&eventNames=orgTreeJsonData&userid='+userid,
            uiProviders:{
                'col': Ext.tree.ColumnNodeUI
            }
        }),

         	root: new Ext.tree.AsyncTreeNode({
            text:'Tasks' 

        })
    });
    tree.render();
    tree.expandAll();
});
function getValue(){   
    var URLParams = new Array();    
    var aParams = document.location.search.substr(1).split('&'); 
    for (i=0; i < aParams.length;i++){    
        var aParam = aParams[i].split('=');   
        URLParams[aParam[0]] = aParam[1];    
    }   
    return URLParams["userid"];   
}
var beginnum=-1;
var endnum=-1;
var index=0;
 function checkhorizon(th){
	 var name=th.name;
	 var fid = document.getElementById('tree');
     var box = fid.getElementsByTagName('input');
     var type2 =  document.getElementById('type2');
     var type1 =  document.getElementById('type1');
     if(type2.checked){
    	 for (var i = 0; i < box.length; i++) {
             if(box[i].id==th.id){
           	  if(index==0){
           		  beginnum=i;
           	  }
           	  if(index==1){
           		  endnum=i;
           	  }
           	index++;
           	box[i].checked=true;
             }
         }
     }else{
    	 beginnum=-1;
    	 endnum=-1;
    	 index=0;
     } 
	 checkChild(th);
	 var a = th.id;
	 if(type1.checked==false&&type2.checked==false){
		 if(a.substr(a.length-1)=="0"){
			 if(th.checked==false){
				 for (var i = 0; i < box.length; i++) {
		             if(box[i].id==th.id){
		           	 	box[i+1].checked=false;
		             }
		         }
			 }
		 }
		 if(a.substr(a.length-1)=="1"){
			 if(th.checked==true){
				 for (var i = 0; i < box.length; i++) {
		             if(box[i].id==th.id){
		           	 	box[i-1].checked=true;
		             }
		         }
			 }
		 }
	 }
	 
	  
}
 function checkChild(th){
	 var type1 =  document.getElementById('type1');
	 var type2 =  document.getElementById('type2');
	 var str = '';
	 var start=0;
	 var end=0;
	 if(type1.checked){
		 var fid = document.getElementById('tree');
	      var box = fid.getElementsByTagName('input');
	      for (var i = 0; i < box.length; i++) {
	          if (box[i].id ==th.id && box[i].name==th.name) {
	              start=i;
	              for(var j = i+1; j < box.length; j++){
	            	  if(box[j].name==th.name){
	            		  end=j;
	            		  break;
	            	  }
	              }
	          }
	      }
	      if(end==0){
	    	  end=start+1;
	      }
	      if(th.checked){
		 	for(var i = start; i < end; i++){
			 var id1 = th.id;
			 var type = id1.substr(id1.length-1);
			 if(type=="0"){
				 for(var i = start; i < end; i++){
					 var childtype = box[i].id;
					 if(childtype.substr(childtype.length-1)=="0"){
						 box[i].checked=true;
					 	}
					 }
			 }
			 if(type=="1"){
				 for(var i = start-1; i < end-1; i++){
					 var childtype = box[i].id;
					/*  if(childtype.substr(childtype.length-1)=="1"){ */
						 box[i].checked=true;
					 /* 	} */
					 }
			 }
		 	}
	     }else{
	    	 for(var i = start; i < end; i++){
	    		 var id1 = th.id;
				 var type = id1.substr(id1.length-1);
				 if(type=="0"){
					 for(var i = start; i < end; i++){

							 box[i].checked=false;

						 }
				 }
				 if(type=="1"){
					 for(var i = start-1; i < end-1; i++){
						 var childtype = box[i].id;
						 if(childtype.substr(childtype.length-1)=="1"){
							 box[i].checked=false;
						 	}
						 }
				 }
			 }
	     }
	 }
	 if(type2.checked){
		 var fid = document.getElementById('tree');
	     var box = fid.getElementsByTagName('input');
		 if(beginnum!=-1&&endnum!=-1){
			 if(beginnum>endnum){
				 var t =beginnum;
				 beginnum=endnum;
				 endnum=t;
			 }
			 var childtype1 = box[beginnum].id;
			 var childtype2 = box[endnum].id;
			 if(childtype1.substr(childtype1.length-1)==childtype2.substr(childtype2.length-1)){
				 if(childtype1.substr(childtype1.length-1)=="0"){
					 for(var i = beginnum; i < endnum+1; i++){
						 var childtype = box[i].id;
						 if(childtype.substr(childtype.length-1)=="0"){
							 box[i].checked=true;
						 	} 
					 }
				 }
				 if(childtype1.substr(childtype1.length-1)=="1"){
					 for(var i = beginnum-1; i < endnum+1; i++){
							 box[i].checked=true;
					 }
				 }
				 beginnum=-1;
				 endnum=-1;
				 index=0;
			 }
		 }
	 }
	 
 }
 function dogant(){
	 var fid = document.getElementById('tree');
     var box = fid.getElementsByTagName('input');
	 var count = 0;
     var result = '';
     for (var i = 0; i < box.length; i++) {
         if (box[i].type == 'checkbox' && box[i].checked) {
             result = result + box[i].id + ',';
         }
     }
    ids=result;
	 radow.doEvent('dogrant',ids);
 }
 function changeType1(){
	 var type1 =  document.getElementById('type1');
	 var type2 =  document.getElementById('type2');
	 if(type1.checked){
		 type2.checked=false;
	 }
 }
 function changeType2(){
	 var type1 =  document.getElementById('type1');
	 var type2 =  document.getElementById('type2');
	 if(type2.checked){
		 type1.checked=false;
	 }
 }
</script>
<div id="groupTreeContent" style="height:100%" >
<%-- <div>
<table>
	<tr>
		<td><odin:checkbox property="type1" label="包含下级"  onclick="changeType1()" /></td>
		<td><odin:checkbox property="type2" label="连续选择" onclick="changeType2()" /></td>
	</tr>
</table>

</div> --%>
	<div id="tree"></div>
</div>

<odin:toolBar property="btnToolBar">
	<odin:textForToolBar text="<h3>信息项权限组管理</h3>" />
	<odin:fill />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="保存" handler="dogant" tooltip="将取消打钩的信息项权限组中所包含的信息项的权限，未打钩的侧拥有权限" isLast="true"/>
</odin:toolBar>
<odin:panel contentEl="groupTreeContent" property="groupTreePanel" topBarId="btnToolBar"></odin:panel>
<odin:window src="/blank.htm" id="mechanismRemWin" width="600" height="500" title="机构权限移除页面"></odin:window>	

