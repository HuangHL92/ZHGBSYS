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
	<odin:buttonForToolBar text="����" icon="image/icon021a2.gif" handler="addScore"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="ɾ��" icon="image/icon021a3.gif" handler="deleteScore" isLast="true"/>
</odin:toolBar> --%>
<table class="x-form-item2" style="width: 101.3%;background-color: rgb(209,223,245);border-left: 1px solid rgb(153,187,232);border-top: 1px solid rgb(153,187,232);border-right: 1px solid rgb(153,187,232);">
	<tr>
		<td><div style="width: 5px"></div></td>
		<odin:select2 property="nd" label="��ȣ�" maxlength="4" onchange="changeData()" multiSelect="false" width="80"></odin:select2>
		<odin:textEdit property="seachName" label="������"  width="100" />
		<odin:select2 property="seachG11027" label="����ְ���Σ�" multiSelect="true" codeType="TrainZB09" width="150"></odin:select2>
		<odin:select2 property="seachG11039" label="�������" multiSelect="false" codeType="GB2230" width="150"></odin:select2>
		<td align="right" style="width:30%"><odin:button text="��ѯ" property="personQuery" handler="PersonQuery"></odin:button></td>
	</tr>
</table> 
<odin:editgrid2 property="scoreGrid" hasRightMenu="false" topBarId="btnToolBar" title="" autoFill="true" pageSize="20" bbarId="pageToolBar"  url="/">
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="pcheck" />
		<odin:gridDataCol name="scoreid" />
		<odin:gridDataCol name="a0101"/>
		<odin:gridDataCol name="a0104"/>
		<odin:gridDataCol name="g11027"/>
		<odin:gridDataCol name="a0192a"/>
		<odin:gridDataCol name="g11039"/>
		<odin:gridDataCol name="a1108"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn2></odin:gridRowNumColumn2>
		<odin:gridEditColumn2 dataIndex="scoreid" width="110" hidden="true" editor="text" header="score����" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="a0101" width="50" header="����" editor="text" edited="false" align="center" />
		<odin:gridEditColumn2 dataIndex="a0104" width="40" header="�Ա�" editor="select" codeType="GB2261" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="a0192a" width="170" header="�ֹ�����λ" editor="text" edited="false" align="center" />
		<odin:gridEditColumn2 dataIndex="g11027" width="100" header="����ְ����" editor="select" codeType="TrainZB09" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="g11039" width="140" header="�������" editor="select" codeType="GB2230" edited="false" align="center" />
		<odin:gridEditColumn2 dataIndex="a1108" width="140" header="���ѧʱ��" editor="text" edited="false" align="center" isLast="true"/>
	</odin:gridColumnModel>
</odin:editgrid2>
<odin:hidden property="scoreid"/>
<script type="text/javascript">
var g_contextpath = '<%= request.getContextPath() %>';
Ext.onReady(function() {
	var viewSize = Ext.getBody().getViewSize();
	var scoreGrid = Ext.getCmp('scoreGrid');
	scoreGrid.setHeight(viewSize.height-43);
	scoreGrid.setWidth(viewSize.width);
	/* scoreGrid.on('rowclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index)
		document.getElementById('scoreid').value = rc.data.scoreid;
	}); */
	scoreGrid.on('rowdblclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index)
		var scoreid = rc.data.scoreid;
		$h.openPageModeWin('loadadd','pages.train.HandleScore','�޸�',700,300,scoreid,g_contextpath);
	});
});
function reload(){
	radow.doEvent('scoreGrid.dogridquery');
}

function addScore(){
	$h.openPageModeWin('addScore','pages.train.HandleScore','����',700,300,'',g_contextpath);
}
function deleteScore(){
	radow.doEvent('deleteScore');
}
function deleteCallBack(){
	Ext.getCmp('scoreGrid').getStore().reload();
	Ext.example.msg('','ɾ���ɹ�',1);
}
function PersonQuery(){
	radow.doEvent("scoreGrid.dogridquery");
}
</script>
