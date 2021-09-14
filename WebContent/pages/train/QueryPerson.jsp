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
		<odin:select2 property="nd" label="��ȣ�" maxlength="4" onchange="changeData()" multiSelect="false" width="80"></odin:select2>
		<odin:textEdit property="seachNameOrIdCard" label="���������֤�ţ�"  width="160" />
		<odin:select2 property="seachTrainClass" label="��ѵ���ͣ�" multiSelect="true" codeType="TrainClass" width="140"></odin:select2>
		<td align="right" style="width:50%"><odin:button text="��ѯ" property="personQuery" handler="PersonQuery"></odin:button></td>
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
		<odin:gridEditColumn2 dataIndex="id" width="10" hidden="true" editor="text" header="id����" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="a0101" width="50" header="����" editor="text" edited="false" align="center" />
		<odin:gridEditColumn2 dataIndex="a0104" width="40" header="�Ա�" editor="select" codeType="GB2261" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="a0184" width="120" header="���֤��" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="a0192a" width="150" header="�ֹ�����λ" editor="text" edited="false" align="center" />
		<odin:gridEditColumn2 dataIndex="g11027" width="80" header="����ְ����" editor="select" codeType="TrainZB09" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="trainclass" width="55" header="��ѵ����" editor="select" codeType="TrainClass" edited="false" align="center" />
		<odin:gridEditColumn2 dataIndex="a1131" width="160" header="���" editor="text" edited="false" align="center" />
		<odin:gridEditColumn2 dataIndex="g11042" width="120" header="�γ�����" editor="text" edited="false" align="center" />
		<odin:gridEditColumn2 dataIndex="g11039" width="70" header="�������" editor="select" codeType="GB2230" edited="false" align="center" />
		<odin:gridEditColumn2 dataIndex="a1108" width="60" header="���ѧʱ��" editor="text" edited="false" align="center" isLast="true"/>
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
			$h.openPageModeWin('selectWin','pages.train.TrainPerson','�鿴',900,430,id,g_contextpath);
		}else if(trainclass==2){
			$h.openPageModeWin('selectWin','pages.train.HandleLeader','�鿴',700,300,id,g_contextpath);
		}else if(trainclass==3){
			$h.openPageModeWin('selectWin','pages.train.HandleElearning','�鿴',700,300,id,g_contextpath);
		}else{
			$h.openPageModeWin('selectWin','pages.train.HandleScore','�鿴',700,300,id,g_contextpath);
		}
	});
});
function reload(){
	radow.doEvent('QueryPersonGrid.dogridquery');
}

function PersonQuery(){
	if(document.getElementById('seachNameOrIdCard').value==""||document.getElementById('seachNameOrIdCard').value==null){
		$h.alert('ϵͳ��ʾ','����д���������֤��');
		return;
	}
	radow.doEvent("QueryPersonGrid.dogridquery");
}
</script>
