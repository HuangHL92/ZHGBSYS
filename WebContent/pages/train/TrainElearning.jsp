<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@include file="/comOpenWinInit2.jsp" %>
<style>
.x-form-item2 tr td .x-form-item{
margin-bottom: 0px !important;
}
</style>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script src="<%=request.getContextPath()%>/jqueryUpload/jquery.uploadify.js" type="text/javascript"></script>
<%-- <odin:toolBar property="btnToolBar">
	<odin:fill />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="增加" icon="image/icon021a2.gif" handler="addElearning"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="删除" icon="image/icon021a3.gif" handler="deleteElearning" isLast="true"/>
</odin:toolBar> --%>
<table class="x-form-item2" style="width: 101.3%;background-color: rgb(209,223,245);border-left: 1px solid rgb(153,187,232);border-top: 1px solid rgb(153,187,232);border-right: 1px solid rgb(153,187,232);">
	<tr>
		<td><div style="width: 5px"></div></td>
		<odin:select2 property="nd" label="年度：" maxlength="4" onchange="changeData()" multiSelect="false" width="80"></odin:select2>
		<odin:textEdit property="seachName" label="姓名："  width="100" />
		<odin:select2 property="seachG11027" label="现任职务层次：" multiSelect="true" codeType="TrainZB09" width="150"></odin:select2>
		<td align="right" style="width:55%"><odin:button text="查询" property="personQuery" handler="PersonQuery"></odin:button></td>
	</tr>
</table> 
<odin:editgrid2 property="elearningGrid" hasRightMenu="false" topBarId="btnToolBar" title="" autoFill="true"  pageSize="20" bbarId="pageToolBar"  url="/">
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="elearningid" />
		<odin:gridDataCol name="a0101"/>
		<odin:gridDataCol name="a0104"/>
		<odin:gridDataCol name="g11027"/>
		<odin:gridDataCol name="a0192a"/>
		<odin:gridDataCol name="g11042"/>
		<odin:gridDataCol name="a1108"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn2></odin:gridRowNumColumn2>
		<odin:gridEditColumn2 dataIndex="elearningid" width="110" hidden="true" editor="text" header="elearning主键" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="a0101" width="50" header="姓名" editor="text" edited="false" align="center" />
		<odin:gridEditColumn2 dataIndex="a0104" width="40" header="性别" editor="select" codeType="GB2261" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="a0192a" width="170" header="现工作单位" editor="text" edited="false" align="center" />
		<odin:gridEditColumn2 dataIndex="g11027" width="100" header="现任职务层次" editor="select" codeType="TrainZB09" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="g11042" width="180" header="课程名称" editor="text" edited="false" align="center" />
		<odin:gridEditColumn2 dataIndex="a1108" width="40" header="获得学时数" editor="text" edited="false" align="center" isLast="true"/>
	</odin:gridColumnModel>
</odin:editgrid2>
<odin:hidden property="elearningid"/>
<script type="text/javascript">
var g_contextpath = '<%= request.getContextPath() %>';
Ext.onReady(function() {
	var viewSize = Ext.getBody().getViewSize();
	var elearningGrid = Ext.getCmp('elearningGrid');
	elearningGrid.setHeight(viewSize.height-43);
	elearningGrid.setWidth(viewSize.width);
	/* elearningGrid.on('rowclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index)
		document.getElementById('elearningid').value = rc.data.elearningid;
	}); */
	elearningGrid.on('rowdblclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index)
		var elearningid = rc.data.elearningid;
		$h.openPageModeWin('loadadd','pages.train.HandleElearning','修改',700,300,elearningid,g_contextpath);
	});
});
function reload(){
	radow.doEvent('elearningGrid.dogridquery');
}

function addElearning(){
	$h.openPageModeWin('addElearning','pages.train.HandleElearning','新增',700,300,'',g_contextpath);
}
function deleteElearning(){
	radow.doEvent('deleteElearning');
}
function deleteCallBack(){
	Ext.getCmp('elearningGrid').getStore().reload();
	Ext.example.msg('','删除成功',1);
}
function PersonQuery(){
	radow.doEvent("elearningGrid.dogridquery");
}
</script>
