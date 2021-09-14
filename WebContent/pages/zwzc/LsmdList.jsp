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


<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/basejs/ext/ux/css/LockingGridView.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/ext/ux/LockingGridView.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js" type="text/javascript"></script>
<style>
.x-grid3-cell-inner{
	white-space:normal !important;
	height:48px;
}
.x-grid3-cell-inner span,.x-grid3-col-numberer{
	height:48px;
	display: table-cell;
	vertical-align: middle;
	width: 1000px;
	width:auto\9;
}

.x-grid3-row{
	overflow: hidden!important;
	height:48px;
	/* border-bottom:1px solid black;
	border-collapse: collapse; */
}
.x-grid3-col{
	vertical-align: middle!important;
	
}
.x-grid3-row-table{
	height: 48px;
}
.x-grid-group-hd div{font: bold 14px tahoma, arial, helvetica, sans-serif;}
.x-grid3-summary-row .x-grid3-cell-inner{font-weight: bold;padding-bottom: 4px;font-size: 14px;}
.x-grid3-hd-row td{font-size: 14px;}
.x-grid3-row td,.x-grid3-summary-row td{font-size: 16px;}

</style>
<%
	String userid = SysManagerUtils.getUserId();
	List<Object[]> gridDataCollist = CommSQL.getHIS_CONFIG_INIT(userid);
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
<odin:hidden property="mdid"/>
<div id="grid" style="align:left top;width:100%;height:100%;overflow:auto;">
			<odin:editgrid2 property="noticeSetgrid" hasRightMenu="false" autoFill="false" 
			locked="true" enableColumnHide="false" 
			 bbarId="pageToolBar" pageSize="50" isFirstLoadData="false" url="/">
						<odin:gridJsonDataModel>
										<odin:gridDataCol name="personcheck" />
										<odin:gridDataCol name="a0000" />

										<%

							int i = 0;
							for(Object[] o : gridDataCollist){
								String name = o[0].toString();
								i++;
								if(i==size ){
									%>
										<odin:gridDataCol name="<%=name %>" isLast="true" />
										<%
								}else{
									%>
										<odin:gridDataCol name="<%=name %>" />
										<%
								}

							}
						%>
									</odin:gridJsonDataModel>
						<odin:gridColumnModel2>
										<%-- <odin:gridEditColumn2 locked="true" header="selectall" width="40"
											editor="checkbox" dataIndex="personcheck" edited="true"
											hideable="false" gridName="persongrid"
											checkBoxClick="getCheckList2" checkBoxSelectAllClick="getCheckList" sortable="false"/> --%>
										<odin:gridRowNumColumn2 header="&nbsp;序<br/>&nbsp;号" width="50"></odin:gridRowNumColumn2>
										<%
							int i = 0;
							for(Object[] o : CommSQL.getHIS_CONFIG_INIT(userid)){
								String name = o[0].toString();
								String editor = o[5].toString().toLowerCase();
								String header = o[2].toString();
								String desc = o[6].toString();
								String width = Integer.valueOf(o[3].toString())+25+"";
								String codeType = o[4]==null?"":o[4].toString();
								String renderer = o[7]==null?"":o[7].toString();
								String align = o[9].toString();
								String sortable = o[10]==null?"true":o[10].toString();
								boolean locked =false;
								if("a0101".equals(name)){
									locked = true;
								}
								i++;
								if(!"a0000".equals(name)){
									if(i==size ){
										%>
										<odin:gridEditColumn2 dataIndex="<%=name %>" width="<%=width %>" header="<%=header %>"
											 align="<%=align %>" editor="<%=editor %>" edited="false" codeType="<%=codeType %>"
											renderer="<%=renderer %>"   isLast="true" sortable="<%=sortable %>"/>

										<%

									}else{
										%>
										<odin:gridEditColumn2 dataIndex="<%=name %>" width="<%=width %>" header="<%=header %>"
											align="<%=align %>" editor="<%=editor %>" locked="<%=locked %>" edited="false" codeType="<%=codeType %>"
										renderer="<%=renderer %>"	 sortable="<%=sortable %>"/>
										<%
									}
								}

							}
						%>
									</odin:gridColumnModel2>
					</odin:editgrid2>
		</div>

<script type="text/javascript">
Ext.onReady(function() {
	$("#mdid").val(GetQueryString("mdid"));
	document.getElementById('grid').style.width = document.body.clientWidth;
	Ext.getCmp('noticeSetgrid').setWidth(document.body.clientWidth);
	Ext.getCmp('noticeSetgrid').setHeight(document.body.clientHeight);
	
	
	$('#query_type').val(parent.Ext.getCmp(subWinId).initialConfig.query_type);
	$('#searchdata').val(realParent.$('#searchdata').val());
	
	
	
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
