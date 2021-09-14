<%@page import="com.insigma.siis.local.business.helperUtil.CodeType2js"%>
<%@page import="com.insigma.siis.local.business.helperUtil.SysManagerUtils"%>
<%@page import="java.util.List"%>
<%@page import="com.insigma.siis.local.pagemodel.customquery.CommSQL"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@include file="/comOpenWinInit.jsp" %>


<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js" type="text/javascript"></script>
<style>
.x-grid3-cell-inner{
	white-space:normal !important;
	padding: 20px 3px 20px 5px;
}
.x-grid3-cell{
	vertical-align: middle!important;
}
.x-grid-group-hd div{font: bold 14px tahoma, arial, helvetica, sans-serif;}
.x-grid3-summary-row .x-grid3-cell-inner{font-weight: bold;padding-bottom: 4px;font-size: 14px;}
.x-grid3-hd-row td{font-size: 14px;}
.x-grid3-row td,.x-grid3-summary-row td{font-size: 16px;}
.x-grid3-row,.x-grid3-row-table{
	min-height: 35px;
	height: auto;
}
</style>
<%
	String userid = SysManagerUtils.getUserId();
	CommSQL.initA01_config(userid);
	List<Object[]> gridDataCollist = CommSQL.A01_CONFIG_LIST.get(userid);
	int size = gridDataCollist.size();
%>

<odin:hidden property="b0111"/>
<odin:hidden property="a1701Word"/>
<odin:hidden property="a0814Word"/>
<odin:hidden property="a0215aWord"/>
<odin:hidden property="query_type"/>
<odin:hidden property="searchdata"/>
<odin:hidden property="rmbs"/>
<odin:hidden property="colIndex"/>
<div id="grid" style="align:left top;width:100%;height:100%;overflow:auto;">
	<odin:editgrid2 property="noticeSetgrid" hasRightMenu="false" autoFill="false" 
	  enableColumnHide="false" 
	 bbarId="pageToolBar" pageSize="50" isFirstLoadData="false" url="/">
		<odin:gridJsonDataModel>
						<odin:gridDataCol name="personcheck" />
						<odin:gridDataCol name="a0000" />
						<odin:gridDataCol name="a0101" />
						<odin:gridDataCol name="a0104" />
						<odin:gridDataCol name="a0107" />
						<odin:gridDataCol name="a0192a" />
						<odin:gridDataCol name="zxs2018" />
						<odin:gridDataCol name="zxs2019" />
						<odin:gridDataCol name="zxs2020" />
						<odin:gridDataCol name="zxs" isLast="true"/>
					</odin:gridJsonDataModel>
		<odin:gridColumnModel>
			<odin:gridRowNumColumn2 header="&nbsp;序<br/>&nbsp;号" width="50"></odin:gridRowNumColumn2>
			<odin:gridEditColumn2 dataIndex="a0101" width="150" header="姓名" align="center" editor="text" edited="false" />
			<odin:gridEditColumn2 dataIndex="a0104" width="100" header="性别" align="center" editor="text" edited="false" />
			<odin:gridEditColumn2 dataIndex="a0192a" width="570" header="任现职务" align="center" editor="text" edited="false" />
			<odin:gridEditColumn2 dataIndex="zxs2018" width="120" header="2018总学时" align="center" editor="text" edited="false" />
			<odin:gridEditColumn2 dataIndex="zxs2019" width="120" header="2019总学时" align="center" editor="text" edited="false" />
			<odin:gridEditColumn2 dataIndex="zxs2020" width="120" header="2020总学时" align="center" editor="text" edited="false" />
			<odin:gridEditColumn2 dataIndex="zxs" width="120" header="总学时" align="center" editor="text" edited="false" isLast="true"/>
		</odin:gridColumnModel>
	</odin:editgrid2>
</div>

<script type="text/javascript">
Ext.onReady(function() {
	
	document.getElementById('grid').style.width = document.body.clientWidth;
	Ext.getCmp('noticeSetgrid').setWidth(document.body.clientWidth);
	Ext.getCmp('noticeSetgrid').setHeight(document.body.clientHeight);
	
	
	$('#query_type').val(parent.Ext.getCmp(subWinId).initialConfig.query_type);
	
	
	
	var pgrid = Ext.getCmp('noticeSetgrid');



	var bbar = pgrid.getBottomToolbar();
	 bbar.insertButton(11,[new Ext.Button({
			icon : 'images/icon/table.gif',
			id:'getAll',
		    text:'导出Excel',
		    handler:expExcelFromGrid
		})
		]);
	
});
function removeRmbs(a0000){
	var rmbs=document.getElementById('rmbs').value;
	document.getElementById('rmbs').value=rmbs.replace(a0000,"");
}

function expExcelFromGrid(){

    var excelName = null;
    //excel导出名称的拼接
    var pgrid = Ext.getCmp('noticeSetgrid');
    var row = pgrid.getSelectionModel().getSelections();
    var dstore = pgrid.getStore();
    var num = dstore.getTotalCount();
    var length = dstore.getCount();
    if (length == 0) {
        $h.alert('系统提示：', '没有要导出的数据！', null, 180);
        return;
    }
    odin.grid.menu.expExcelFromGrid('noticeSetgrid', excelName, null,null, false);
}

<%
String RrmbCodeType = (String)session.getAttribute("RrmbCodeType");
//RrmbCodeType = CodeType2js.getRrmbCodeType();
if(RrmbCodeType==null){
	RrmbCodeType = CodeType2js.getRrmbCodeType();
	session.setAttribute("RrmbCodeType",RrmbCodeType);
}
%>
<%=RrmbCodeType%>
function gllbM(value) {
	var returnV="";
	if(value){
		var v = value.split(",");
		for(i=0;i<v.length;i++){
			if(CodeTypeJson.ZB130[v[i]]){
				returnV += CodeTypeJson.ZB130[v[i]]+","
			}
		}
		returnV = returnV.substring(0,returnV.length-1);
	}
	
	return returnV;
	
}
</script>
