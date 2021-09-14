<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@include file="/comOpenWinInit2.jsp" %>
<style>
body {
	overflow:auto;
}
</style>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script src="<%=request.getContextPath()%>/jqueryUpload/jquery.uploadify.js" type="text/javascript"></script>
<div style="height: 700px;overflow-y: scroll;">
<div align="center" style="margin-top: 12px;margin-bottom: 12px"><span id="nd" style="font-weight:bold;color:#F00"></span><span>年度</span><span id="name" style="font-weight:bold;color:#F00"></span><span>培训情况</span></div>
<odin:groupBox property="gb1" title="脱产人员培训">
<odin:editgrid2 property="personGrid" hasRightMenu="false" title="" autoFill="true"  bbarId="pageToolBar"  url="/" pageSize="10">
		<odin:gridJsonDataModel>
			<odin:gridDataCol name="trainname"/>
			<odin:gridDataCol name="g11028"/>
			<odin:gridDataCol name="g11029"/>
			<odin:gridDataCol name="g11032"/>
			<odin:gridDataCol name="a1108"/>
		</odin:gridJsonDataModel>
		<odin:gridColumnModel>
			<odin:gridRowNumColumn2></odin:gridRowNumColumn2>
			<odin:gridEditColumn2 dataIndex="trainname" width="160" editor="text" header="培训班次名称" edited="false" align="center"/>
			<odin:gridEditColumn2 dataIndex="g11028" width="80" header="是否班组长 " editor="select" codeType="XZ09" edited="false" align="center"/>
			<odin:gridEditColumn2 dataIndex="g11029" width="90" header="是否优秀学员" editor="select" codeType="XZ09" edited="false" align="center"/>
			<odin:gridEditColumn2 dataIndex="g11032" width="90" header="学员考勤信息" editor="text" edited="false" align="center"/>
			<odin:gridEditColumn2 dataIndex="a1108" width="90" header="获得学时数" editor="text" edited="false" align="center" isLast="true"/>
		</odin:gridColumnModel>
</odin:editgrid2>
</odin:groupBox>
<odin:groupBox property="gb2" title="领导干部上讲台">
<odin:editgrid2 property="leaderGrid" hasRightMenu="false" topBarId="btnToolBar" title="" autoFill="true"  bbarId="pageToolBar"  url="/">
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="trainname"/>
		<odin:gridDataCol name="g11037"/>
		<odin:gridDataCol name="g11038"/>
		<odin:gridDataCol name="a1107c"/>
		<odin:gridDataCol name="a1108"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn2></odin:gridRowNumColumn2>
		<odin:gridEditColumn2 dataIndex="trainname" width="160" editor="text" header="培训班次名称" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="g11037" width="80" header="上讲台时间" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="g11038" width="90" header="讲课题目" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="a1107c" width="90" header="培训时长" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="a1108" width="90" header="获得学时数" editor="text" edited="false" align="center" isLast="true"/>
	</odin:gridColumnModel>
</odin:editgrid2>
</odin:groupBox>
<odin:groupBox property="gb3" title="网络培训">
<odin:editgrid2 property="elearningGrid" hasRightMenu="false" topBarId="btnToolBar" title="" autoFill="true"  bbarId="pageToolBar"  url="/">
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="g11042"/>
		<odin:gridDataCol name="a1107"/>
		<odin:gridDataCol name="a1111"/>
		<odin:gridDataCol name="a1108"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn2></odin:gridRowNumColumn2>
		<odin:gridEditColumn2 dataIndex="g11042" width="100" header="课程名称" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="a1107" width="100" header="学习开始日期" editor="text" edited="false" align="center" />
		<odin:gridEditColumn2 dataIndex="a1111" width="100" header="学习结束日期" editor="text" edited="false" align="center" />
		<odin:gridEditColumn2 dataIndex="a1108" width="90" header="获得学时数" editor="text" edited="false" align="center" isLast="true"/>
	</odin:gridColumnModel>
</odin:editgrid2>
</odin:groupBox>
<odin:groupBox property="gb4" title="考试成绩">
<odin:editgrid2 property="scoreGrid" hasRightMenu="false" topBarId="btnToolBar" title="" autoFill="true"  bbarId="pageToolBar"  url="/">
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="g11039" />
		<odin:gridDataCol name="g11040"/>
		<odin:gridDataCol name="g11041"/>
		<odin:gridDataCol name="a1108"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn2></odin:gridRowNumColumn2>
		<odin:gridEditColumn2 dataIndex="g11039" width="100" header="考试类别" editor="select" codeType="GB2230" edited="false" align="center" />
		<odin:gridEditColumn2 dataIndex="g11040" width="100" header="考试时间" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="g11041" width="100" header="考试成绩" editor="text" edited="false" align="center" />
		<odin:gridEditColumn2 dataIndex="a1108" width="90" header="获得学时数" editor="text" edited="false" align="center" isLast="true"/>
	</odin:gridColumnModel>
</odin:editgrid2>
</odin:groupBox>
</div>
<odin:hidden property="a0184param"/>
<odin:hidden property="ndparam"/>
<script type="text/javascript">
var g_contextpath = '<%= request.getContextPath() %>';
Ext.onReady(function() {
	var viewSize = Ext.getBody().getViewSize();
	var personGrid = Ext.getCmp('personGrid');
	personGrid.setHeight(viewSize.height-450);
	personGrid.setWidth(viewSize.width-25);
	var leaderGrid = Ext.getCmp('leaderGrid');
	leaderGrid.setHeight(viewSize.height-450);
	leaderGrid.setWidth(viewSize.width-25);
	var elearningGrid = Ext.getCmp('elearningGrid');
	elearningGrid.setHeight(viewSize.height-450);
	elearningGrid.setWidth(viewSize.width-25);
	var scoreGrid = Ext.getCmp('scoreGrid');
	scoreGrid.setHeight(viewSize.height-450);
	scoreGrid.setWidth(viewSize.width-25);
	personGrid.on('rowdblclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index)
		var leacerid = rc.data.leacerid;
		var param = leacerid+'@'+document.getElementById('trainid').value;
		$h.openPageModeWin('loadadd','pages.train.HandleLeader','修改',700,300,param,g_contextpath);
	});
});
function loadTitle(param){
	document.getElementById("name").innerText = param.split(",")[0];
	document.getElementById("nd").innerText = param.split(",")[1];
}
</script>
