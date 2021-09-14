<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@include file="/comOpenWinInit2.jsp" %>
<style>
</style>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script src="<%=request.getContextPath()%>/jqueryUpload/jquery.uploadify.js" type="text/javascript"></script>
<odin:toolBar property="btnToolBar">
	<odin:fill />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="增加" icon="image/icon021a2.gif" handler="addLeader"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="删除" icon="image/icon021a3.gif" handler="deleteLeader" isLast="true"/>
</odin:toolBar>
<odin:editgrid2 property="leaderGrid" hasRightMenu="false" topBarId="btnToolBar" title="" autoFill="true"  bbarId="pageToolBar"  url="/">
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="pcheck" />
		<odin:gridDataCol name="leacerid" />
		<odin:gridDataCol name="trainid"/>
		<odin:gridDataCol name="a0101"/>
		<odin:gridDataCol name="a0104"/>
		<odin:gridDataCol name="a0184"/>
		<odin:gridDataCol name="a1107c"/>
		<odin:gridDataCol name="g11038"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn2></odin:gridRowNumColumn2>
		<odin:gridEditColumn2 locked="true" header="selectall" width="30" menuDisabled="true" 
							editor="checkbox" dataIndex="pcheck" edited="true"
							hideable="false" gridName="gridcq"/>
		<odin:gridEditColumn2 dataIndex="leacerid" width="110" hidden="true" editor="text" header="leader主键" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="trainid" width="110" hidden="true" editor="text" header="班次主键" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="a0101" width="50" header="姓名" editor="text" edited="false" align="center" />
		<odin:gridEditColumn2 dataIndex="a0104" width="40" header="性别" editor="select" codeType="GB2261" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="a0184" width="140" header="身份证号" editor="text" edited="false" align="center" />
		<odin:gridEditColumn2 dataIndex="a1107c" width="70" header="培训时长" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="g11038" width="140" header="讲课题目" editor="text" edited="false" align="center" isLast="true"/>
	</odin:gridColumnModel>
</odin:editgrid2>
<odin:hidden property="leacerid"/>
<odin:hidden property="trainid"/>
<script type="text/javascript">
var g_contextpath = '<%= request.getContextPath() %>';
Ext.onReady(function() {
	var viewSize = Ext.getBody().getViewSize();
	var leaderGrid = Ext.getCmp('leaderGrid');
	leaderGrid.setHeight(viewSize.height);
	leaderGrid.setWidth(viewSize.width);
	/* leaderGrid.on('rowclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index)
		document.getElementById('leacerid').value = rc.data.leacerid;
	}); */
	leaderGrid.on('rowdblclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index)
		var leacerid = rc.data.leacerid;
		var param = leacerid+'@'+document.getElementById('trainid').value;
		$h.openPageModeWin('loadadd','pages.train.HandleLeader','修改',700,300,param,g_contextpath);
	});
});
function reload(){
	radow.doEvent('leaderGrid.dogridquery');
}

function addLeader(){
	var trainid = document.getElementById('trainid').value;
	var param = ''+'@'+trainid;
	$h.openPageModeWin('addLeader','pages.train.HandleLeader','新增',700,300,param,g_contextpath);
}
function deleteLeader(){
	radow.doEvent('deleteLeader');
}
function deleteCallBack(){
	Ext.getCmp('leaderGrid').getStore().reload();
	Ext.example.msg('','删除成功',1);
}
</script>
