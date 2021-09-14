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
        #div{
            margin-top:15px;
        }

    </style>
</head>



<body style="overflow: hidden;">
<div id="btnToolBarDiv" style="padding-bottom: 0px; width: 98%"></div>
<odin:toolBar property="btnToolBar" applyTo="btnToolBarDiv">
    <odin:fill />
	<odin:buttonForToolBar  id="addbtn" handler="add" text="新增" icon="images/add.gif" />
	<odin:separator />
	<odin:buttonForToolBar text="删除" icon="image/delete.png"  isLast="true" id="deleteBtn"/>
</odin:toolBar>
<odin:editgrid property="grid1" sm="row" bbarId="pageToolBar"   autoFill="true" title="工作督办列表" cellDbClick="editbtn">
    <odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
    	<odin:gridDataCol name="checked"></odin:gridDataCol>
        <odin:gridDataCol name="gzdbid"></odin:gridDataCol>
        <odin:gridDataCol name="sxmc"></odin:gridDataCol>
        <odin:gridDataCol name="jtwt"></odin:gridDataCol>
        <odin:gridDataCol name="zgmb"></odin:gridDataCol>
        <odin:gridDataCol name="qtld"></odin:gridDataCol>
        <odin:gridDataCol name="zrcs"></odin:gridDataCol>
        <odin:gridDataCol name="cs001"></odin:gridDataCol>
        <odin:gridDataCol name="zgrw"></odin:gridDataCol>
        <odin:gridDataCol name="wcsx"></odin:gridDataCol>
        <odin:gridDataCol name="wcl"></odin:gridDataCol>
       <%--  <odin:gridDataCol name="wcbz1"></odin:gridDataCol>--%>
        <odin:gridDataCol name="wcbz2"></odin:gridDataCol> 
       <%--  <odin:gridDataCol name="cjr"></odin:gridDataCol>
        <odin:gridDataCol name="createdon" isLast="true"></odin:gridDataCol> --%>
    </odin:gridJsonDataModel>
    <odin:gridColumnModel>
        <odin:gridRowNumColumn width="30"/>
        <odin:gridColumn header="selectall" width="30" gridName="monthlyWorkGrid" dataIndex="checked" editor="checkbox" edited="true" hidden="false"/>
        <odin:gridEditColumn header="id主键" dataIndex="gzdbid" width="100" align="center" editor="text" edited="false" hidden="true" />
        <odin:gridEditColumn header="事项名称" dataIndex="sxmc" width="500" align="center" editor="text" edited="false"  hidden="false"  />
        <odin:gridEditColumn header="具体问题" dataIndex="jtwt" width="200" align="center"  editor="text" edited="false"  hidden="false"  />
        <odin:gridEditColumn header="整改目标" dataIndex="zgmb" hidden="false" width="240" align="center" editor="text" edited="false" />
        <odin:gridEditColumn header="牵头领导" dataIndex="qtld" width="200" align="center" editor="text" edited="false" />
        <odin:gridEditColumn header="责任处室" dataIndex="zrcs" width="250" align="left" editor="text"  edited="false" />
        <odin:gridEditColumn header="整改措施" dataIndex="zgrw" width="300" align="left" editor="text" edited="false" />
        <odin:gridEditColumn header="配合处室" dataIndex="cs001" width="150" align="center" editor="text" edited="false" />
        <odin:gridEditColumn header="完成时限" dataIndex="wcsx" width="150" align="center" editor="text" edited="false" />
        <odin:gridEditColumn header="完成率" dataIndex="wcl" width="80" align="center"  editor="text" edited="false" />
        <%-- <odin:gridEditColumn header="完成标志1" dataIndex="wcbz1" width="150" align="center" editor="select"  selectData="['1','完成'],['2','进行中'],['3','未启动']" edited="false" />--%>
        <odin:gridEditColumn header="长期坚持" dataIndex="wcbz2" width="100" align="center" editor="select" selectData="['1','是'],['0','否']" edited="false" /> 
        <%-- <odin:gridEditColumn header="创建人" dataIndex="cjr" width="150" align="center" editor="text" edited="false" />
        <odin:gridEditColumn header="创建时间" dataIndex="createdon" width="150" align="center" editor="text" edited="false" isLast="true" /> --%>
    </odin:gridColumnModel>
    <odin:gridJsonData>
    {
       data:[]
    }
    </odin:gridJsonData>
</odin:editgrid>
 <odin:hidden property="gzdbid" />
</body>

<%--<odin:window src="/blank.htm" title="新增年度年休假计划" width="750" height="450" id="addWindows" modal="true"></odin:window>--%>

<%--<odin:window src="/blank.htm" title="修改年度年休假计划" width="1000" height="500" id="editWindows" modal="true"></odin:window>--%>

<script>

Ext.onReady(function(){
	odin.ext.getCmp('grid1').setHeight(document.body.clientHeight -28);
});

//修改
function editbtn(){
	var list = odin.ext.getCmp('grid1').getSelectionModel().getSelections();	
    if(list.length==1){  
    	var gzdbid=list[0].get('gzdbid');
        document.getElementById('gzdbid').value=gzdbid;
        edit(gzdbid);
    	
	}else if(list!=null && list.length>1 ){
		var msg = '只能选择一条需要修改的督办内容!';
        odin.alert(msg);
	}else{
		var msg = '请先选择需要修改的督办内容!';
        odin.alert(msg);
	} 
		
}
function GridReload(){
    odin.alert('保存成功');
    var grid = Ext.getCmp("grid1");
    var ds = grid.getStore();
    ds.reload();
}
function doTrainQuery() {
    radow.doEvent("grid1.dogridquery");
}
function showPerct(value, params, record, rowIndex, colIndex, ds) {
	
	return value+"%";
	
}

function add () {
	$h.openWin('add1', 'pages.gzdb.WorkSuperviseEdit&operation='+"add"+"&gzdbid=", '新增工作督办', 1230, 400, '', contextpath);
}

function edit () {
    var id = document.getElementById('gzdbid').value;
    if (feildIsNull(id)) {
        $h.alert('系统提示', '请选择一行工作督办内容！');
        return;
    }
    <%--$h.openWin('SimpleStatistics','pages.sysorg.org.SimpleStatistics','自定义通用统计图',1010,650,param,'<%=request.getContextPath()%>');--%>
    $h.openWin('update1', 'pages.gzdb.WorkSuperviseEdit&operation='+"edit"+"&gzdbid="+id, '查看(修改)任务完成情况', 1230, 610, id, contextpath);
    $("#gzdbid").val("");
    
}
var Func = {
    init: function () {
        doTrainQuery();
    },
    clickEvent: function () {
        radow.doEvent(event);
    }


}

function rowCallBack(param) {

    if (param.edit == 'delete') {
        $h.alert('系统提示', '删除成功！');
    } else if (param.edit == 'edit') {
        if (param.val == 1) {
            $h.alert('系统提示', '保存成功！');
        } else if (param.val == 2) {
            $h.alert('系统提示', '修改成功！');
        }

    }
    doTrainQuery();

}
</script>

</html>