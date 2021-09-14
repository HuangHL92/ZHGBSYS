<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<style>

.x-grid3-cell-inner, .x-grid3-hd-inner{
white-space:normal !important;
word-break:break-all;
}

</style>
<script  type="text/javascript">

function opwin() {
	var win = odin.ext.getCmp('fileImpWin');
	win.setTitle('导入窗口');
	/// win.setSize(500,350); //宽度  高度
	odin.showWindowWithSrc('fileImpWin', contextPath
			+ "/pages/sysmanager/ZWHZYQ_001_004/FileImpWindow.jsp");
}
</script>

<div id="groupTreeContent" >
<div id="groupTreePanel"></div>


<odin:groupBox title="导入信息填选框" property="ggBox">
	<form name="excelForm" id="excelForm" method="post" action="<%=request.getContextPath()%>/TagFileServlet?method=addZhgbExcelFile" enctype="multipart/form-data" >
	<table >
		<tr>
			<odin:select2 property="importType" editor="true" size="30"  label="导入类型"/>
			<odin:textEdit width="720" inputType="file" colspan="4"  property="excelFile" label="选择文件" ></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="startNumber" size="33" label="导入的起始行（除标题后要导入数据的excel行数）"/>
			<odin:textEdit property="rowNumber" size="33" label="导入的总行数"/>
		</tr>
		<tr>
			<td colspan="4" align="center">
				<odin:button text="&nbsp;&nbsp;导&nbsp;入&nbsp;&nbsp;" property="impBtn" handler="formSubmit" />
			</td>
		</tr>
	</table>
	</form>
</odin:groupBox>
<table >
	<tr>
		<td width="50%">
			<odin:editgrid property="memberGrid" hasRightMenu="false" title="日志主信息" autoFill="true"  forceNoScroll="false"  height="380" bbarId="pageToolBar" pageSize="20" isFirstLoadData="false" url="/">
			<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="system_log_id" />
					<odin:gridDataCol name="userlog"/>
					<odin:gridDataCol name="eventtype"/>
					<odin:gridDataCol name="eventobject" />
					<odin:gridDataCol name="objectname" />
					<odin:gridDataCol name="system_operate_date" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridColumn dataIndex="system_log_id" width="110" hidden="true" header="日志主键" align="center"/>
					<odin:gridColumn dataIndex="userlog" editor="select" width="110" header="操作用户" codeType="USER"  align="center"/>
					<odin:gridColumn dataIndex="eventtype" width="190" header="操作类型" align="center" editor="text"/>
					<odin:gridColumn dataIndex="eventobject" width="140" header="信息集" align="center" editor="text"/>
					<odin:gridColumn dataIndex="objectname" header="操作对象" width="80" align="center" editor="text"/>
					<odin:gridColumn dataIndex="system_operate_date" width="130" header="创建时间" align="center" editor="text" isLast="true" renderer="time"/>
				</odin:gridColumnModel>
			</odin:editgrid>
		</td>
		<td width="50%">
			<odin:editgrid property="logGrid" title="日志明细信息"  hasRightMenu="false"   autoFill="true" forceNoScroll="false"    height="380" bbarId="pageToolBar" pageSize="20" isFirstLoadData="false" url="/">
			<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="systemlogdetailid" />
					<odin:gridDataCol name="dataname"/>
					<odin:gridDataCol name="oldvalue"/>
					<odin:gridDataCol name="newvalue" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridColumn dataIndex="dataname" width="150" header="信息项" align="center"/>
					<odin:gridColumn dataIndex="oldvalue" width="300" header="原值" align="center"/>
					<odin:gridColumn dataIndex="newvalue" width="300" header="新值" align="center" isLast="true"/>
				</odin:gridColumnModel>
			</odin:editgrid>
		</td>
	</tr>
</table>
		
</div>

<odin:toolBar property="btnToolBar" applyTo="groupTreePanel">
	<odin:textForToolBar text="<h3>日志管理</h3>" />
	<odin:fill />
	<odin:separator></odin:separator>
	<%-- <odin:buttonForToolBar text="日志归档" id="logSaveBtn" tooltip="用于日志迁移"/> --%>
	<odin:buttonForToolBar text="导出excel" icon="images/icon/exp.png" id="expExcel" handler="expExcel"/>
	<odin:separator></odin:separator>
	<%-- <odin:buttonForToolBar text="日志迁移" icon="images/icon/exp.png" id="logMoveBtn" tooltip="用于日志迁移"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="备份日志" icon="images/mylog.gif" id="logExpBtn" handler="logExp" tooltip="用于日志备份"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="载入日志" icon="images/icon/imp.gif" id="logImpBtn22" handler="opwin" tooltip="用于日志载入"/>
	<odin:separator></odin:separator>  --%>
	<odin:buttonForToolBar text="查询" icon="images/search.gif" id="findLogBtn" tooltip="查询日志" isLast="true"/>
</odin:toolBar>
<!-- 
<odin:panel contentEl="groupTreeContent" property="groupTreePanel" topBarId="btnToolBar"></odin:panel> -->

<odin:window src="/blank.htm" id="ModuleComWin" width="500" height="300" title="模块权限分配页面" modal="true"></odin:window>
<odin:window src="/blank.htm" id="fileImpWin" width="400" height="170" title="日志导入页面" modal="true"></odin:window>		
<odin:window src="/blank.htm" id="logMoveWin" width="430" height="330" title="日志迁移页面" modal="true"></odin:window>

<script type="text/javascript">
function expExcel(){
	odin.grid.menu.expExcelFromGrid('memberGrid', null, null,null, false);
}
Ext.onReady(function() {
	//页面调整
	 Ext.getCmp('logGrid').setHeight((Ext.getBody().getViewSize().height-140));
	 Ext.getCmp('memberGrid').setHeight(Ext.getCmp('logGrid').getHeight());
//	 Ext.getCmp('groupTreePanel').setWidth(document.body.clientWidth);
	 //document.getElementById("groupTreePanel").style.width = document.body.clientWidth;
});
function objTop(obj){
    var tt = obj.offsetTop;
    var ll = obj.offsetLeft;
    while(true){
    	if(obj.offsetParent){
    		obj = obj.offsetParent;
    		tt+=obj.offsetTop;
    		ll+=obj.offsetLeft;
    	}else{
    		return [tt,ll];
    	}
	}
    return tt;  
}

function time(value) {
	var length = value.length;
	
	if(length > 16){
		value  = value.substring(0,19);
	}
	
	return value;
}


function flash() {
	radow.doEvent('memberGrid.dogridquery');
	
}
</script>


