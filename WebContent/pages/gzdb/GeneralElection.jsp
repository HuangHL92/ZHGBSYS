<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@include file="/comOpenWinInit2.jsp" %>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.FontConfigPageModel"%>
<%
    String ctxPath = request.getContextPath();
%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=gbk">
    <meta content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0">
    <meta http-equiv="x-ua-compatible" content="IE=edge,chrome=1;IE10;IE=EmulateIE8;"><!--IE=IE10;IE=EmulateIE8;-->
    <%--Grid import --%>
    <script type="text/javascript">
        var contextpath = '<%= request.getContextPath() %>';
    </script>
    <link rel="stylesheet" type="text/css" href="<%=ctxPath%>/mainPage/css/font-awesome.css">
    <!--[if lte IE 7]>
    <link rel="stylesheet" type="text/css" href="<%=ctxPath%>/mainPage/css/font-awesome-ie7.min.css">
    <![endif]-->
    <script src="<%=ctxPath%>/basejs/jquery-ui/jquery-1.10.2.js"></script>
    <%--Grid end--%>
    <script src="<%=ctxPath%>/jwjc/js/underscore.js" type="text/javascript"></script>

    <script type="text/javascript" src="<%=ctxPath%>/jwjc/js/moment.min.js"></script>
    <script type="text/javascript" src="<%=ctxPath%>/basejs/helperUtil.js"></script>
    <script type="text/javascript" src="<%=ctxPath%>/jwjc/js/examine/common.js"></script>
    <script type="text/javascript" src="<%=ctxPath%>/jwjc/js/common.js"></script>
    <link rel="stylesheet" href="<%=ctxPath%>/jwjc/css/common.css">
    <style>
	#EGLInfo input{
	border: 1px solid #c0d1e3 !important;
}
    </style>
</head>



<body style="overflow: hidden;">
<div id="btnToolBarDiv" style="padding-bottom: 0px; width: 98%"></div>
<odin:toolBar property="btnToolBar" applyTo="btnToolBarDiv">
    <odin:fill />
	<odin:buttonForToolBar text="修改" icon="images/i_2.gif" id="editegl" handler="editegl" />
	<odin:separator />
	<odin:buttonForToolBar text="增加" icon="images/add.gif" id="addBtn" handler="addegl" />
	<odin:separator />
	<odin:buttonForToolBar text="删除" icon="image/delete.png"  isLast="true" id="deleteBtn"/>
</odin:toolBar>

<!-- <table    style="width:100%"> -->
<!-- <tr> -->
	<!-- <td  valign="top" width="300" >
		 <table  >
		 	<tr >
		 		<td  > -->
<%-- <td  valign="top" width="300" >
		 <table  >
		 	<tr >
		 		<td  >
		 			<odin:editgrid2 property="mdgrid" hasRightMenu="false" width="500"   topBarId="btnToolBarET" title="" autoFill="true" pageSize="20" bbarId="pageToolBar" url="/">
							<odin:gridJsonDataModel>
								<odin:gridDataCol name="quxz" />
								</odin:gridJsonDataModel>
								<odin:gridColumnModel>
								<odin:gridRowNumColumn2></odin:gridRowNumColumn2>	
								<odin:gridEditColumn2 header="区域换届名称" align="center" edited="false" width="400" dataIndex="quxz" editor="text"  />
							  </odin:gridColumnModel>
					 </odin:editgrid2>
		 		</td>
			</tr>
		 </table>
	</td> --%>
<!-- <td valign="top" > -->
	<!--  <div id="girdDiv" style="width: 100%;height:100%;">

				<table  >
				<tr>
					<td> -->
<odin:editgrid2 property="memberGrid"  topBarId="btnToolBar" hasRightMenu="false" autoFill="true" forceNoScroll="true" bbarId="pageToolBar"  url="/" title="" >
    <odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
    	<odin:gridDataCol name="checked"></odin:gridDataCol>
        <odin:gridDataCol name="hjxjid"></odin:gridDataCol>
        <odin:gridDataCol name="xjqy"></odin:gridDataCol>
        <odin:gridDataCol name="yjwc"></odin:gridDataCol>
        <odin:gridDataCol name="ejwc"></odin:gridDataCol>
        <odin:gridDataCol name="sajwc"></odin:gridDataCol>
        <odin:gridDataCol name="jd"></odin:gridDataCol>
        <odin:gridDataCol name="createdon"></odin:gridDataCol>
        <odin:gridDataCol name="cz"></odin:gridDataCol>
    </odin:gridJsonDataModel>
    <odin:gridColumnModel>
        <odin:gridRowNumColumn2/>
        <odin:gridEditColumn2 header="selectall" width="30" gridName="monthGrid" dataIndex="checked" editor="checkbox" edited="true" hidden="false"/>
        <odin:gridEditColumn2 header="id主键" dataIndex="hjxjid" width="100" align="center" editor="text" edited="false" hidden="true" />
       <odin:gridEditColumn2 header="选举区域" dataIndex="xjqy" width="200" align="center"  editor="select"  selectData="['001.001.004.001','上城区'],['001.001.004.004','拱墅区'],['001.001.004.005','西湖区'],['001.001.004.006','滨江区'],['001.001.004.00F','钱塘区'],['001.001.004.007','萧山区'],['001.001.004.008','余杭区'],['001.001.004.00E','临平区'],['001.001.004.00D','建德市'],['001.001.004.00B','桐庐县'],['001.001.004.009','富阳区'],['001.001.004.00A','临安区'],['001.001.004.00C','淳安县']" edited="false" />
        <%-- <odin:gridEditColumn2 header="选举区域" dataIndex="xjqy" width="200" align="center"  editor="select"  codeType="QUXZ" edited="false" /> --%>
        <odin:gridEditColumn2 header="一阶段计划完成" dataIndex="yjwc" width="200" align="center"  editor="text" edited="false"  hidden="false"  />
        <odin:gridEditColumn2 header="二阶段计划完成" dataIndex="ejwc" hidden="false" width="240" align="center" editor="text" edited="false" />
        <odin:gridEditColumn2 header="三阶段计划完成" dataIndex="sajwc" width="200" align="center" editor="text" edited="false" />
        <odin:gridEditColumn2 header="进度" dataIndex="jd" align="center" width="200"  editor="text"  edited="false" />
        <odin:gridEditColumn2 header="修改时间" dataIndex="createdon" width="300" align="center" editor="text" edited="false" />
        <odin:gridEditColumn2 header="操作" dataIndex="cz" width="150" align="center" editor="text" edited="false"  isLast="true"/>
        <%-- <odin:gridEditColumn header="完成标志1" dataIndex="wcbz1" width="150" align="center" editor="select"  selectData="['1','完成'],['2','进行中'],['3','未启动']" edited="false" />--%>
    </odin:gridColumnModel>
    <odin:gridJsonData>
    {
       data:[]
    }
    </odin:gridJsonData>
</odin:editgrid2>
<!-- </td>
				   </tr>
				</table>
			</div> -->
<!-- 		</td>
	</tr>
</table> -->
<div id="EGLInfo">
	<div style="margin-left: 10px;margin-top: 30px;margin-right: 10px;">
		<table>
		  <tr id="t1">
			<odin:select2 label="换&nbsp;&nbsp;届&nbsp;&nbsp;区&nbsp;&nbsp;域："  property="xjqy" width="200" data="['001.001.004.001','上城区'],['001.001.004.004','拱墅区'],['001.001.004.005','西湖区'],['001.001.004.006','滨江区'],['001.001.004.00F','钱塘区'],['001.001.004.007','萧山区'],['001.001.004.008','余杭区'],['001.001.004.00E','临平区'],['001.001.004.00D','建德市'],['001.001.004.00B','桐庐县'],['001.001.004.009','富阳区'],['001.001.004.00A','临安区'],['001.001.004.00C','淳安县']"  ></odin:select2>
			<%-- <odin:select2 property="xjqy" width="200" codeType="QUXZ" value=""  label="换届区域" ></odin:select2> --%>
		  </tr>
		  <tr>
		  	<odin:dateEdit property="yjwc" width="200" label="一阶段计划完成："
				selectOnFocus="true" format="Y-m-d"
				onkeyup="odin.commInputMask(this,'Y-m-d')" /> 
		  </tr>
		  <tr>
		  	<odin:dateEdit property="ejwc" width="200" label="二阶段计划完成："
				selectOnFocus="true" format="Y-m-d"
				onkeyup="odin.commInputMask(this,'Y-m-d')" />
		  
		  </tr>
		  <tr>
		  	<odin:dateEdit property="sajwc" width="200" label="三阶段计划完成："
				selectOnFocus="true" format="Y-m-d"
				onkeyup="odin.commInputMask(this,'Y-m-d')" />
		  </tr>
		</table>
		<odin:hidden property="hjxjid"/>
		<div style="margin-left: 245px;margin-top: 15px;">
			<odin:button text="确定" property="saveETCInfo" handler="saveEGLInfo" />
		</div>
	</div>
</div>
</body>
<script>
var Func = {
	    init: function () {
	        doTrainQuery();
	    },
	    clickEvent: function () {
	        radow.doEvent(event);
	    }


	}
function doTrainQuery() {
    radow.doEvent("memberGrid.dogridquery");
}
function QXSPJGLInfo(hjxjid,xjqy){
	var contextPath = '<%=request.getContextPath()%>';
	 /*  $h.openPageModeWin('hjxj', 'pages.gzdb.GeneralElectionEdit&&hjxjid='+hjxjid+'&&xjqy='+xjqy, '换届信息', 1800, 700, hjxjid, contextPath, null,
				{modal:true,collapsed:false,collapsible:false,titleCollapse:false,maximized:false}); */
	  $h.openWin('hjxj', 'pages.gzdb.GeneralElectionEdit&xjqy='+xjqy+'&hjxjid='+hjxjid, '换届行事历', 1300, 500, hjxjid, contextPath,null,{},true);
	 /* $h.openPageModeWin('hjxj', 'pages.gzdb.GeneralElectionEdit&hjxjid='+hjxjid, '换届信息', 1800, 1200, hjxjid, contextPath, null,
			 { maximizable: false,resizable: false,closeAction: 'close'}); */
	/* $h.showWindowWithSrc('hjxj',contextPath + "/pages/gzdb/GeneralElectionEdit.jsp?hjxjid="+hjxjid,'换届信息',2100,1500,null,{maximizable:true,resizable:true},true); */
}
Ext.onReady(function() {
	openEGLWin();
	hideWin();
	
	var viewSize = Ext.getBody().getViewSize();
	 var editgrid = Ext.getCmp('memberGrid');
/* 	 var mdgrid = Ext.getCmp('mdgrid');
	 mdgrid.setHeight(viewSize.height-20 ); */
	 editgrid.setHeight(viewSize.height-20 );
	// mdgrid.setWidth(475);
    editgrid.setWidth(viewSize.width - 10);
    
    editgrid.on('rowdblclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index);
		odin.setSelectValue('hjxjid',rc.data.hjxjid);
		odin.setSelectValue('xjqy',rc.data.xjqy);
		var hjxjid = rc.data.hjxjid;
		var xjqy = rc.data.xjqy;
		QXSPJGLInfo(hjxjid,xjqy);
	});
	
	
});
//修改
function editbtn(){
	var list = odin.ext.getCmp('memberGrid').getSelectionModel().getSelections();	
    if(list.length==1){  
    	var gzdbid=list[0].get('hjxjid');
        document.getElementById('hjxjid').value=hjxjid;
        //edit(hjxjid);
    	
	}else if(list!=null && list.length>1 ){
		var msg = '只能选择一条需要修改的督办内容!';
        odin.alert(msg);
	}else{
		var msg = '请先选择需要修改的督办内容!';
        odin.alert(msg);
	} 
		
}
function addegl(){
	odin.setSelectValue('xjqy','');
	$('#yjwc').val('');
	$('#ejwc').val('');
	$('#sajwc').val('');
	$('#hjxjid').val('');
	//document.getElementById("t1").style.display="";
	//document.getElementById("xjqy").setDisabled=true;
	radow.doEvent('setDisable','3');
	//document.getElementById("xjqy").readOnly=true;
	//xjqy.readOnly=false;
	openEGLWin();
}
function editegl(){
	var selectedRows = Ext.getCmp('memberGrid').getSelectionModel().getSelections();
	if(selectedRows.length!=1){
		$h.alert('','请选择一行数据！');
		return;
	}
	var rc = selectedRows[0];
	Ext.getCmp('yjwc').setValue(rc.data.yjwc);
	Ext.getCmp('ejwc').setValue(rc.data.ejwc);
	Ext.getCmp('sajwc').setValue(rc.data.sajwc);
	odin.setSelectValue('xjqy',rc.data.xjqy);
	//document.getElementById("t1").style.display="none";
	radow.doEvent('setDisable','4');
	//document.getElementById("xjqy").setAttribute("readOnly",'true');
	//$(".showTable").css("readOnly","true");
	//document.getElementById("xjqy").readOnly=true;
	//xjqy.readOnly=true;
	//$('t1').attr('readonly','true');
	$('#hjxjid').val(rc.data.hjxjid);
	openEGLWin();
}
function delegl(value, params, record,rowIndex,colIndex,ds){
	var hjxjid = record.data.hjxjid;
	
	return "<a href=\"javascript:deleteRow2(&quot;"+hjxjid+"&quot;)\">删除</a>";
}
function deleteRow2(hjxjid){ 
	Ext.Msg.confirm("系统提示","是否确认删除？",function(id) { 
		if("yes"==id){
			radow.doEvent('allDelete',hjxjid);
		}else{
			return;
		}		
	});	
}

function openEGLWin(){
	var win = Ext.getCmp("addEGL");	
	if(win){
		win.show();	
		return;
	}
	win = new Ext.Window({
		title : '换届选举',
		layout : 'fit',
		width : 350,
		height : 251,
		closeAction : 'hide',
		closable : true,
		modal : true,
		id : 'addEGL',
		collapsed:false,
		collapsible:false,
		bodyStyle : 'background-color:#FFFFFF',
		plain : true,
		titleCollapse:false,
		contentEl:"EGLInfo",
		listeners:{}
		           
	});
	win.show();
}


function saveEGLInfo(){
	radow.doEvent("addEGLInfo");
	
}



function hideWin(){
	var win = Ext.getCmp("addEGL");	
	if(win){
		win.hide();	
	}
}
</script>

</html>