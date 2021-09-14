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

<body >
<odin:hidden property="quxz"/>
<odin:toolBar property="btnToolBar">
    <odin:fill />
	<%-- <odin:buttonForToolBar text="修改" icon="images/i_2.gif" id="editegl" handler="editegl" />
	<odin:separator /> --%>
	<odin:buttonForToolBar text="增加" icon="images/add.gif" id="addWin" handler="addWin" isLast="true"/>
	<%-- <odin:separator />
	<odin:buttonForToolBar text="删除" icon="image/delete.png"  isLast="true" id="deleteBtn"/> --%>
</odin:toolBar>

<table    style="width:100%">
<tr>
	<td  valign="top" width="300" >
		 <table  >
		 	<tr >
		 		<td  >
<td  valign="top" width="300" >
		 <table  >
		 	<tr >
		 		<td  >
		 			<%-- <odin:toolBar property="btnToolBarET">
    					<odin:fill />
						<odin:buttonForToolBar text="修改" icon="images/i_2.gif" id="editegl" handler="editegl" />
						<odin:separator />
						<odin:buttonForToolBar text="增加" icon="images/add.gif" id="addBtn" handler="addegl" isLast="true"/>
						<odin:buttonForToolBar text="删除" icon="image/delete.png"  isLast="true" id="deleteBtn"/>
					</odin:toolBar> --%>
		 			<odin:editgrid2 property="mdgrid" hasRightMenu="false" width="500"   topBarId="btnToolBarET" title="" autoFill="true" pageSize="20" bbarId="pageToolBar" url="/">
							<odin:gridJsonDataModel>
								<odin:gridDataCol name="code_name" />
								<odin:gridDataCol name="code_value" />
								</odin:gridJsonDataModel>
								<odin:gridColumnModel>
								<odin:gridRowNumColumn2></odin:gridRowNumColumn2>	
								 <odin:gridEditColumn2 header="区域编码" dataIndex="code_value" width="100" align="center" editor="text" edited="false" hidden="true" />
								<odin:gridEditColumn2 header="区域换届名称" align="center" edited="false" width="400" dataIndex="code_name" editor="text"  />
							  </odin:gridColumnModel>
					 </odin:editgrid2>
		 		</td>
			</tr>
		 </table>
	</td>
<td valign="top" >
	 <div id="girdDiv" style="width: 100%;height:100%;">

				<table  >
				<tr>
					<td>
<odin:editgrid2 property="memberGrid"  topBarId="btnToolBar" hasRightMenu="false" autoFill="true" forceNoScroll="true" bbarId="pageToolBar"  url="/" title="" >
    <odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
        <odin:gridDataCol name="dhxjid"></odin:gridDataCol>
        <odin:gridDataCol name="xjqy"></odin:gridDataCol>
        <odin:gridDataCol name="hylx"></odin:gridDataCol>
        <odin:gridDataCol name="hymc"></odin:gridDataCol>
        <odin:gridDataCol name="sj"></odin:gridDataCol>
        <odin:gridDataCol name="fileurl"></odin:gridDataCol>
        <odin:gridDataCol name="hfileurl"></odin:gridDataCol>
        <odin:gridDataCol name="jfileurl"></odin:gridDataCol>
        <%-- <odin:gridDataCol name="wcbz"></odin:gridDataCol> --%>
        <odin:gridDataCol name="filename"></odin:gridDataCol>
        <odin:gridDataCol name="hfilename"></odin:gridDataCol>
        <odin:gridDataCol name="jfilename"></odin:gridDataCol>
        <odin:gridDataCol name="cz"></odin:gridDataCol>
    </odin:gridJsonDataModel>
    <odin:gridColumnModel>
        <odin:gridRowNumColumn2/>
        <odin:gridEditColumn2 header="id主键" dataIndex="dhxjid" width="100" align="center" editor="text" edited="false" hidden="true" />
        <odin:gridEditColumn dataIndex="fileurl" width="90" hidden="true" header="文件相对路径" align="center" editor="text" edited="false"/>
        <odin:gridEditColumn2 header="会议类型" dataIndex="hylx" width="100" align="center" editor="select" selectData="['01','党代会'],['02','人代会'],['03','政协会']" edited="false" />
       <odin:gridEditColumn2 header="会议名称" dataIndex="hymc" width="200" align="center"  editor="text"  edited="false" />
        <%-- <odin:gridEditColumn2 header="选举区域" dataIndex="xjqy" width="200" align="center"  editor="select"  codeType="QUXZ" edited="false" /> --%>
        <odin:gridEditColumn2 header="时间" dataIndex="sj" width="200" align="center"  editor="text" edited="false"  />
        <odin:gridEditColumn2 header="选举材料" dataIndex="filename" hidden="false" width="240" align="center" editor="text" edited="false"  renderer="recommend"/>
         <odin:gridEditColumn2 header="选举结果" dataIndex="jfilename" hidden="false" width="240" align="center" editor="text" edited="false"  renderer="jrecommend"/>
       <%--  <odin:gridEditColumn2 header="结果" dataIndex="wcbz" width="200" align="center" selectData="['01','未完成'],['02','推进中'],['03','已完成']" editor="select" edited="false" /> --%>
        <odin:gridEditColumn2 header="会议材料" dataIndex="hfilename" align="center" width="200"  editor="text"  edited="false" renderer="GrantRender" />
        <odin:gridEditColumn2 header="操作" dataIndex="cz" width="150" align="center" editor="text" edited="false" isLast="true" renderer="GrantCz"/>
        <%-- <odin:gridEditColumn header="完成标志1" dataIndex="wcbz1" width="150" align="center" editor="select"  selectData="['1','完成'],['2','进行中'],['3','未启动']" edited="false" />--%>
    </odin:gridColumnModel>
    <odin:gridJsonData>
    {
       data:[]
    }
    </odin:gridJsonData>
</odin:editgrid2>
</td>
				   </tr>
				</table>
			</div>
		</td>
	</tr>
</table>
<div id="EGLInfo">
	<div style="margin-left: 10px;margin-top: 30px;margin-right: 10px;">
		<table>
		  <tr>
			<odin:select2 property="hylx" width="300" data="['01','党代会'],['02','人代会'],['03','政协会']" label="会议类型" ></odin:select2>
			<%-- <odin:select2 property="xjqy" width="200" codeType="QUXZ" value=""  label="换届区域" ></odin:select2> --%>
			<%-- <odin:textEdit property="sj" width="200" label="时间" ></odin:textEdit> --%>
		  </tr>
		 <tr>
			<odin:textEdit property="hymc" label="会议名称" width="300" maxlength="100"/>
		 </tr>
		 <tr>
			<%-- <odin:textEdit property="sj" label="时间" width="300" maxlength="100"/> --%>
			<odin:dateEdit property="sj" width="300" label="时间："
				selectOnFocus="true" format="Y-m-d"
				onkeyup="odin.commInputMask(this,'Y-m-d')" />
		</tr>
		 <%--  <tr>
		  	<odin:dateEdit property="ejwc" width="200" label="二阶段计划完成："
				selectOnFocus="true" format="Y-m-d"
				onkeyup="odin.commInputMask(this,'Y-m-d')" />
		  	<odin:dateEdit property="sajwc" width="200" label="三阶段计划完成："
				selectOnFocus="true" format="Y-m-d"
				onkeyup="odin.commInputMask(this,'Y-m-d')" />
		  </tr> --%>
		</table>
		<odin:hidden property="dhxjid"/>
		<div style="margin-left: 145px;margin-top: 15px;">
			<odin:button text="确定" property="saveETCInfo" handler="saveEGLInfo" />
		</div>
	</div>
</div>
</body>
<iframe  id="iframe_expBZYP" style="display: none;" src=""></iframe>
<odin:hidden property="xjqy"/>
<odin:hidden property="dhxjid"/>
<script>
var Func = {
	    init: function () {
	        doTrainQuery();
	    },
	    clickEvent: function () {
	        radow.doEvent(event);
	    }


	}
//来文原件
function recommend(value, params, rs, rowIndex, colIndex, ds){
	var fileurl = rs.get('fileurl');
	var filename = rs.get('filename');
	if(filename!=null){
		 var url=fileurl.replace(/\\/g,"/");
		return "<a href=\"javascript:downloadFile('" +url +"')\">"+filename+"</a>";
	}
} 
//来文原件
function jrecommend(value, params, rs, rowIndex, colIndex, ds){
	var fileurl = rs.get('jfileurl');
	var filename = rs.get('jfilename');
	if(filename!=null){	 
		 var url=fileurl.replace(/\\/g,"/");
		return "<a href=\"javascript:downloadFile('" +url +"')\">"+filename+"</a>";
	}
} 
//下载
function downloadFile(url) {
	var downfile = url;
	//w = window.open("ProblemDownServlet?method=downFile&prid=" + encodeURI(encodeURI(downfile)));
	window.location.href="ProblemDownServlet?method=downFile&prid=" + encodeURI(encodeURI(downfile));
}
function doTrainQuery() {
    radow.doEvent("mdgrid.dogridquery");
}

function gg() {
    radow.doEvent("memberGrid.dogridquery");
}

function editegl(){
	var selectedRows = Ext.getCmp('memberGrid').getSelectionModel().getSelections();
	if(selectedRows.length!=1){
		$h.alert('','请选择一行数据！');
		return;
	}
	var rc = selectedRows[0];
	Ext.getCmp('hylx').setValue(rc.data.hylx);
	Ext.getCmp('hymc').setValue(rc.data.hymc);
	Ext.getCmp('sj').setValue(rc.data.sj);
	Ext.getCmp('dhxjid').setValue(rc.data.dhxjid);
	//odin.setSelectValue('xjqy',rc.data.xjqy);
	//document.getElementById("t1").style.display="none";
	//radow.doEvent('setDisable','4');
	//document.getElementById("xjqy").setAttribute("readOnly",'true');
	//$(".showTable").css("readOnly","true");
	//document.getElementById("xjqy").readOnly=true;
	//xjqy.readOnly=true;
	//$('t1').attr('readonly','true');
	//$('#hjxjid').val(rc.data.hjxjid);
	openEGLWin();
}


 function addWin1() {
	var list = odin.ext.getCmp('mdgrid').getSelectionModel().getSelections();	
    if(list.length==1){  
    	var xjqy=list[0].get('code_value');
        //document.getElementById('code_value').value=xjqy;
    }  
	$h.showWindowWithSrc('addWin', contextPath
			+ '/pages/gzdb/AddMeetingElection.jsp?xjqy=' + xjqy,'新增大会选举窗口',530,550);	
} 

function GrantRender(value, params, rs, rowIndex, colIndex, ds){
	var hfileurl = rs.get('hfileurl');
	var hfilename = rs.get('hfilename');
	
	var url=hfileurl.replace(/\\/g,"/");
	if(hfilename!=null){
	return "<a href=\"javascript:downloadFile('" +url +"')\">"+hfilename+"</a>" + "&nbsp;" ;
	
	}
}

function GrantCz(value, params, rs, rowIndex, colIndex, ds){
	var hfileurl = rs.get('hfileurl');
	var fileurl = rs.get('fileurl');
	var jfileurl = rs.get('jfileurl');
	var dhxjid = rs.get("dhxjid");
	
	var url=hfileurl.replace(/\\/g,"/");
	return "<a href=\"javascript:sq(&quot;"+dhxjid+"&quot;)\">上传</a>&nbsp;&nbsp;<a href=\"javascript:deleteMeetingFile('"+rs.get('dhxjid')+"','"+rs.get('fileurl')+"','"+rs.get('hfileurl')+"','"+rs.get('jfileurl')+"')\">删除</a>" + "&nbsp;";
}

function sq(param){ 
	//radow.doEvent('sq',meeting_id);	
	$h.openWin('CadresWord','pages.gzdb.CadresWord','大会选举',860, 485,param,contextpath);
	
}
//删除大会选举
//encodeURI，用来做url转码，解决中文传输乱码问题 
function deleteMeetingFile(dhxjid, fileurl,hfileurl){
	Ext.Msg.confirm("系统提示","是否确认删除？",function(id) { 
		if("yes"==id){
			//通过ajax请求，删除大会选举
			$.ajax({
				url:'<%=request.getContextPath()%>/PublishFileServlet?method=deleteMeetingFile',
				type:"GET",
				data:{
					"id":encodeURI(dhxjid),
					"filePath":encodeURI(fileurl),
					"hfilePath":encodeURI(hfileurl),
					"jfilePath":encodeURI(hfileurl)
				},
				success:function(){
					radow.doEvent('memberGrid.dogridquery');
					odin.alert("删除成功!");
					parent.gzt.window.location.reload();
				},
				error:function(){
					odin.alert("删除失败!");		
				}
			});
		}else{
			return;
		}		
	});
}


Ext.onReady(function() {
	openEGLWin();
	hideWin();
	
	var viewSize = Ext.getBody().getViewSize();
	 var editgrid = Ext.getCmp('memberGrid');
 	 var mdgrid = Ext.getCmp('mdgrid');
	 mdgrid.setHeight(viewSize.height-20 ); 
	 editgrid.setHeight(viewSize.height-20 );
	 mdgrid.setWidth(475);
    editgrid.setWidth(viewSize.width - 495);
    
    editgrid.on('rowdblclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index);
		//var dhxjid = rc.data.dhxjid;
		//var hylx = rc.data.hylx;
		//var hymc = rc.data.hymc;
		//var sj = rc.data.sj;
		odin.setSelectValue('hylx',rc.data.hylx);
		odin.setSelectValue('hymc',rc.data.hymc);
		odin.setSelectValue('sj',rc.data.sj);
		odin.setSelectValue('dhxjid',rc.data.dhxjid);
		openEGLWin();
	});
    
    mdgrid.on('rowdblclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index);
		var hjxjid = rc.data.hjxjid;
		var xjqy = rc.data.xjqy;
		//QXSPJGLInfo(hjxjid,xjqy);
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


//下载政策法规文件 
//encodeURI，用来做url转码，解决中文传输乱码问题 （后台接收的时候会再做转码处理，转回来）
function downloadMeetingFile(dhxjid, fileurl){
	var downfile = fileurl;
	var url=downfile.replace(/\\/g,"/");
	//window.location="PublishFileServlet?method=downloadPolicyFile&filePath="+encodeURI(encodeURI(fileurl));
	//w = window.open("ProblemDownServlet?method=downFile&prid=" + encodeURI(encodeURI(downfile)));
	window.location.href="ProblemDownServlet?method=downFile&prid=" + encodeURI(encodeURI(url));
	<%-- $('#iframe_expBZYP').attr('src',"<%= request.getContextPath() %>/PublishFileServlet?method=downloadMeetingFile&filePath="+encodeURI(encodeURI(fileurl))); --%>

}

function QXSPJGLInfo(hjxjid,xjqy){
	var contextPath = '<%=request.getContextPath()%>';
	  $h.openPageModeWin('hjxj', 'pages.gzdb.GeneralElectionEdit&&hjxjid='+hjxjid+'&&xjqy='+xjqy, '换届信息', 1800, 700, hjxjid, contextPath, null,
				{modal:true,collapsed:false,collapsible:false,titleCollapse:false,maximized:false});
	 /* $h.openPageModeWin('hjxj', 'pages.gzdb.GeneralElectionEdit&hjxjid='+hjxjid, '换届信息', 1800, 1200, hjxjid, contextPath, null,
			 { maximizable: false,resizable: false,closeAction: 'close'}); */
	/* $h.showWindowWithSrc('hjxj',contextPath + "/pages/gzdb/GeneralElectionEdit.jsp?hjxjid="+hjxjid,'换届信息',2100,1500,null,{maximizable:true,resizable:true},true); */
}

function addWin(){
	odin.setSelectValue('hylx','');
	$('#hymc').val('');
	$('#sj').val('');
	$('#dhxjid').val('');
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
		title : '大会选举',
		layout : 'fit',
		width : 450,
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