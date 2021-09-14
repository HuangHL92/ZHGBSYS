<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="js/lengthValidator.js"></script>
<style>
#grid1 {
	width: 316px !important;
}
.cellyellow{
background: yellow !important;
background-color: yellow !important;
}
</style>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/qdstyle.css" />
<script type="text/javascript">
</script>
<%@include file="/comOpenWinInit2.jsp" %>	

<div id="btnToolBarDiv" style="width:100%;display:none"></div>		
<div id="div_data" style="width:100%;">								
		<odin:editgrid property="grid1" autoFill="true" forceNoScroll="true" pageSize="100"
			topBarId="ToolBar" bbarId="pageToolBar" url="/">
			<odin:gridJsonDataModel id="id" root="data">
					<odin:gridDataCol name="dataname" />		
			        <odin:gridDataCol name="oldvalue" /> 
					<odin:gridDataCol name="newvalue" />	
					<odin:gridDataCol name="changedatetime" />	
					<odin:gridDataCol name="username" isLast="true" />

			</odin:gridJsonDataModel>
			<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridEditColumn header="信息项" align="center" edited="false" width="150" dataIndex="dataname" editor="text" />
					<odin:gridEditColumn2 header="原值" align="center" edited="false" width="100" dataIndex="oldvalue" editor="text" />
					<odin:gridEditColumn2 header="新值" align="center" edited="false" width="100" dataIndex="newvalue" editor="text" />
					<odin:gridEditColumn2 header="修改时间" align="center" edited="false" width="120" dataIndex="changedatetime" editor="text" renderer="subdate"/>
					<odin:gridEditColumn header="修改人" align="center" edited="false" width="120" dataIndex="username" isLast="true" editor="text" />
					<%-- <odin:gridEditColumn header="实有数量" align="center" renderer="countrenderer" edited="false" width="150" dataIndex="countnum" editor="text" />
					<odin:gridEditColumn2 header="对应职务层次" align="center" edited="false" width="150" dataIndex="zwcode" editor="select" isLast="true" codeType="ZB09"/> --%>
			 </odin:gridColumnModel>
		</odin:editgrid>																
</div>
	
<odin:toolBar property="ToolBar" applyTo="btnToolBarDiv">
    <odin:fill />
    <odin:buttonForToolBar text="刷新" id="refresh" icon="images/icon/table.gif" handler="refresh"/>
	<odin:separator />
	<odin:buttonForToolBar text="新增" id="insert" icon="image/icon021a2.gif" handler="insert"/>
	<odin:separator />
	<odin:buttonForToolBar text="修改" id="update" icon="image/icon021a6.gif" handler="update"/>
	<odin:separator />
	<odin:buttonForToolBar text="删除" id="deleteBtn" icon="image/icon021a3.gif" handler="deleteBtn" isLast="true"/>
</odin:toolBar>

<odin:hidden property="ids" title="类别id"/>
<odin:hidden property="dataname" title="数据项"/>
<odin:hidden property="objectid" title="人员id"/>
<odin:hidden property="ereaid" title="单位代码"/>
<odin:window src="/blank.htm" id="Check" width="600" height="250"
	title="考核页面" modal="true"></odin:window>
<script type="text/javascript">
function setWidthHeight(){
	document.getElementById("btnToolBarDiv").parentNode.parentNode.style.overflow='hidden';
	var height=document.body.clientHeight;
	var width=document.body.clientWidth;
	document.getElementById("btnToolBarDiv").parentNode.style.width=width+'px';
	var height_top=document.getElementById("btnToolBarDiv").offsetHeight;
	//var clear_search_height=document.getElementById("clear_search").offsetHeight;
	document.getElementById("btnToolBarDiv").style.width=width+'px';
	Ext.getCmp("grid1").setHeight(height-height_top);
	Ext.getCmp("grid1").setWidth(width);
}
Ext.onReady(function() {	
	window.onresize=setWidthHeight;
	setWidthHeight();
	document.getElementById('dataname').value = parentParam.dataname;
	document.getElementById('objectid').value = parentParam.objectid;
	document.getElementById('ereaid').value = parentParam.ereaid;
	//alert(parentParam.gwcode);
	//alert(parentParam.ereaid);
});
function refresh(){//刷新
	
	radow.doEvent("refresh");
}

function insert(){//新增
	
	radow.doEvent("insert");
}

function update(){  //修改
	
	
	radow.doEvent("update");
}

function deleteBtn(){  //删除
   			
	radow.doEvent('deleteBtn');
	
	
}

function subdate(value, params, rs, rowIndex, colIndex, ds){
	var subdate = value.substring(0,10)
	return subdate ;
}
//来文原件
function file(value, params, rs, rowIndex, colIndex, ds){
	var url = rs.get('fileurl');
	var name = rs.get('filename');
	
	 if(name != null && name != ''){
		return "<a href=\"javascript:downloads('" + url + "')\">"+name+"</a>";
	} 
	
	
	 if(name != null && name != ''){
	
		return "<a href=\"javascript:downloads('" + url + "')\">"+name+"</a>";
	} 	
} 
/*下载*/
function downloads(url){
	window.location="YearCheckServlet?method=YearCheckFile&filePath="+encodeURI(encodeURI(url));
}
</script>

