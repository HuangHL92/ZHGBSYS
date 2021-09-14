<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@page import="com.lbs.cp.util.SysManagerUtil"%>
<%@ page import="com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_003.MechanismComWindowPageModel"%>
<%@page import="com.insigma.siis.local.business.helperUtil.SysManagerUtils"%>
    <script type="text/javascript" src="basejs/ColumnNodeUI.js"></script>
    <script type="text/javascript" src="basejs/ext/ux/TreeCheckNodeUI.js"></script>
<%
	String ereaid = (String) (new MechanismComWindowPageModel().areaInfo.get("areaid"));
%>

    <link rel="stylesheet" type="text/css" href="basejs/ext/ux/css/column-tree.css" />
<style type="text/css">

#savebtn{font:bold 12px tahoma, arial, helvetica, sans-serif;color:#15428b}
.save {background: url('<%=request.getContextPath()%>/images/save.gif') left  no-repeat !important;}
</style>
	<odin:toolBar property="btnToolBar" applyTo="toolbar">		
		<odin:commformtextForToolBar text="<font style=\\'bold 12px tahoma, arial, helvetica, sans-serif;\\' color=\\'#15428b\\'>不可见管理类别</font>"></odin:commformtextForToolBar>
		<odin:fill />
		<odin:buttonForToolBar text="保存" icon="images/save.gif" id="save" handler="dogant" isLast="true"/>
	</odin:toolBar>
	<odin:hidden property="changeNode"/>
	<odin:hidden property="ryIds"/>



<div id="right" style="height:100%;width:100%; position: absolute;padding-left: 10px;">
	<div id="toolbar"></div>

<div id="ry" ></div>

<%-- <table style="padding-bottom: 15px;padding-left: 10">
	<tr align="center">
		<td colspan="2">
		<odin:button text="<font id=\\'savebtn\\' ><h1 style=\\'display:inline\\'>&nbsp;保存</h1></font>',width:'60',iconCls:'save" handler="dogant"></odin:button>
		</td>
	</tr>
</table> --%>
</div>


<script type="text/javascript">
var ids="";
var changeNode = "";
Ext.onReady(function(){
//	Ext.getCmp('grid6').store.reload();
	var userid = parent.document.getElementById('radow_parent_data').value;	  

      var treegrid = new Ext.tree.ColumnTree({
      	id:'treegrid',
          el:'ry',
          autoHeight:true,
          rootVisible:false,	
          autoScroll:true,
          border:false,
//          title: '不可见公务员列表',          
          columns:[{
              header:'管理类别',
              width:175,
              dataIndex:'task'
          },{
              header:'不可浏览',
              width:100,
              dataIndex:'duration'
          },{
              header:'不可维护',
              width:106,
              dataIndex:'user'
          }],

          loader: new Ext.tree.TreeLoader({
              dataUrl:'radowAction.do?method=doEvent&pageModel=pages.sysmanager.ZWHZYQ_001_001.UserAddWindow&eventNames=orgTreeGridJsonData&userid='+userid+'&b0111='+parent.document.getElementById('checkedgroupid').value,
              uiProviders:{
                  'col': Ext.tree.ColumnNodeUI
              }
          }),

           	root: new Ext.tree.AsyncTreeNode({
              text:'Tasks' 

          })
      });
      treegrid.render();
      treegrid.expandAll();
//      document.getElementById('tree').style.height = document.getElementById('org').style.height;
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


function lbClick(th){
	var fid = document.getElementById('ry');
    var box = fid.getElementsByTagName('input');
    
	var a = th.id;
	
	if(a.substr(a.length-1)=="0"){
		if(th.checked==false){
			for (var i = 0; i < box.length; i++) {
				
				if(box[i].id==th.id){
					box[i+1].checked=false;
				}
			}
		}
		
		if(th.checked==true){
			for (var i = 0; i < box.length; i++) {
				
				if(box[i].id==th.id){
					box[i+1].checked=true;
					
				}
	             /* if(box[i].id!=th.id&&(box[i].id.substr(box[i].id.length-1)=="1")){
	            	 box[i].checked=false;
	             } */
			}
		}
	}
}


 function dogant(){
	 var fid1 = document.getElementById('ry');
     var box1 = fid1.getElementsByTagName('input');
	 var count = 0;
     var result1 = '';
     for (var i = 0; i < box1.length; i++) {
         if (box1[i].type == 'checkbox') {
        	 result1 = result1 + box1[i].id + ':'+box1[i].checked+':'+box1[i].value+',';
         }
     }
//     alert(result);
//     document.getElementById('ryIds').value = result1;
     radow.doEvent('saveUser',result1); //机构，人员，模块
 }

</script>
