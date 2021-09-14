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
<table class="x-form-item2" style="width: 101.3%;background-color: rgb(209,223,245);border-left: 1px solid rgb(153,187,232);border-top: 1px solid rgb(153,187,232);border-right: 1px solid rgb(153,187,232);">
	<tr>
		<td><div style="width: 5px"></div></td>
		<odin:select2 property="nd" label="年度：" maxlength="4" onchange="changeData()" multiSelect="false" width="80"></odin:select2>
		<odin:textEdit property="seachNameOrIdCard" label="姓名或身份证号："  width="160" />
		<odin:select2 property="seachTrainClass" label="培训类型：" multiSelect="true" codeType="TrainClass" width="140"></odin:select2>
		<td align="right" style="width:50%"><odin:button text="查询" property="personQuery" handler="PersonQuery"></odin:button></td>
	</tr>
</table> 
<odin:editgrid2 property="QueryPersonGrid" hasRightMenu="false" topBarId="btnToolBar" title="" autoFill="true"  pageSize="20" bbarId="pageToolBar"  url="/">
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="id" />
		<odin:gridDataCol name="a0101"/>
		<odin:gridDataCol name="a0104"/>
		<odin:gridDataCol name="a0184"/>
		<odin:gridDataCol name="g11027"/>
		<odin:gridDataCol name="a0192a"/>
		<odin:gridDataCol name="trainclass"/>
		<odin:gridDataCol name="a1131"/>
		<odin:gridDataCol name="g11042"/>
		<odin:gridDataCol name="g11039"/>
		<odin:gridDataCol name="a1108"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn2></odin:gridRowNumColumn2>
		<odin:gridEditColumn2 dataIndex="id" width="10" hidden="true" editor="text" header="id主键" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="a0101" width="50" header="姓名" editor="text" edited="false" align="center" />
		<odin:gridEditColumn2 dataIndex="a0104" width="40" header="性别" editor="select" codeType="GB2261" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="a0184" width="120" header="身份证号" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="a0192a" width="150" header="现工作单位" editor="text" edited="false" align="center" />
		<odin:gridEditColumn2 dataIndex="g11027" width="80" header="现任职务层次" editor="select" codeType="TrainZB09" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="trainclass" width="55" header="培训类型" editor="select" codeType="TrainClass" edited="false" align="center" />
		<odin:gridEditColumn2 dataIndex="a1131" width="160" header="班次" editor="text" edited="false" align="center" />
		<odin:gridEditColumn2 dataIndex="g11042" width="120" header="课程名称" editor="text" edited="false" align="center" />
		<odin:gridEditColumn2 dataIndex="g11039" width="70" header="考试类别" editor="select" codeType="GB2230" edited="false" align="center" />
		<odin:gridEditColumn2 dataIndex="a1108" width="60" header="获得学时数" editor="text" edited="false" align="center" isLast="true"/>
	</odin:gridColumnModel>
</odin:editgrid2>
<odin:hidden property="elearningid"/>
<script type="text/javascript">
var g_contextpath = '<%= request.getContextPath() %>';
Ext.onReady(function() {
	var viewSize = Ext.getBody().getViewSize();
	var QueryPersonGrid = Ext.getCmp('QueryPersonGrid');
	QueryPersonGrid.setHeight(viewSize.height-38);
	QueryPersonGrid.setWidth(viewSize.width);
	QueryPersonGrid.on('rowdblclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index)
		var id = rc.data.id;
		var trainclass = rc.data.trainclass;
		if(trainclass==1){
			$h.openPageModeWin('selectWin','pages.train.TrainPerson','查看',900,430,id,g_contextpath);
		}else if(trainclass==2){
			$h.openPageModeWin('selectWin','pages.train.HandleLeader','查看',700,300,id,g_contextpath);
		}else if(trainclass==3){
			$h.openPageModeWin('selectWin','pages.train.HandleElearning','查看',700,300,id,g_contextpath);
		}else{
			$h.openPageModeWin('selectWin','pages.train.HandleScore','查看',700,300,id,g_contextpath);
		}
	});
});
function reload(){
	radow.doEvent('QueryPersonGrid.dogridquery');
}

function PersonQuery(){
	if(document.getElementById('seachNameOrIdCard').value==""||document.getElementById('seachNameOrIdCard').value==null){
		$h.alert('系统提示','请填写姓名或身份证号');
		return;
	}
	radow.doEvent("QueryPersonGrid.dogridquery");
}
</script>
