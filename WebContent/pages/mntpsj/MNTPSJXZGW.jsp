<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<html class="ext-strict x-viewport">
<script src="<%=request.getContextPath()%>/pages/xbrm2/js/jquery-1.4.4.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comboxWithTree.js"></script>
<link rel="stylesheet" type="text/css" href="mainPage/css/bootstrap-combined.min.css"> 
<%@include file="/comOpenWinInit.jsp" %>

<odin:hidden property="fabd00"/>
<odin:hidden property="famx00"/>
<odin:hidden property="b0111"/>
<odin:hidden property="a0200"/>
<odin:hidden property="a0200s"/>

<script type="text/javascript">
function init(){//��ʼ������
	document.getElementById("fabd00").value=parentParams.fabd00;
	if(parentParams.b0111){
		document.getElementById("b0111").value=parentParams.b0111;
	}
	if(parentParam.famx00){
		document.getElementById("famx00").value=parentParam.famx00;
	} 
	updateGrid();
}

function updateGrid(){
	radow.doEvent('updateGrid');
}



</script>
<div id="TitleContent" style="width: 100%;text-align: right;">
<button type='button' class="btn btn-primary " onclick="saveGW2()" style="margin: 5px 10px 0px 10px;">����</button>
</div>

<table>
	<tr height="30">
		<odin:textEdit property="mntpname" label="ģ�������" width="560" readonly="true"></odin:textEdit>
	</tr>
	<tr height="30">
		<tags:ComBoxWithTree property="mntp_b01" listWidth="543" listHeight="350"  label="���䵥λ" readonly="true" nodeDblclick="changeB01" width="560" codetype="B01" />
	</tr>
	<tr>
		<td colspan="2" width="800">
				<odin:editgrid2 property="allGrid" hasRightMenu="false"   height="440" title="��״��λ��<span style=color:#FF4500>��˫����λ��Ŀ��ѡ�ж����λ������水ť������Ӹ�λ��</span>" forceNoScroll="true" autoFill="true" pageSize="200"  url="/">
					<odin:gridJsonDataModel>
						<odin:gridDataCol name="a0200"/>
						<odin:gridDataCol name="a0000"/>
						<odin:gridDataCol name="a0215a"/>
						<odin:gridDataCol name="a0201e"/>
						<odin:gridDataCol name="a0101"/>
						<odin:gridDataCol name="a0192a"/>
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
						<odin:gridRowNumColumn></odin:gridRowNumColumn>
						<odin:gridEditColumn2 header="selectall" width="20"
							editor="checkbox" dataIndex="personcheck" edited="true"
							hideable="false" gridName="elearningGrid" 
							/>
						<odin:gridEditColumn2 dataIndex="a0215a" width="100" menuDisabled="true" sortable="false" header="��λ����" editor="text" edited="false" align="center" />
						<odin:gridEditColumn2 dataIndex="a0201e" width="40" menuDisabled="true" sortable="false" header="��λ���" editor="select" codeType="ZB129" edited="false" align="center" />
						<odin:gridEditColumn2 dataIndex="a0101" width="50" menuDisabled="true" sortable="false" header="���θɲ�" editor="text" edited="false" isLast="true" align="center" />
					</odin:gridColumnModel>
				</odin:editgrid2>
		</td>
	</tr>
	
</table>



<script type="text/javascript">
function changeB01(){
	radow.doEvent('allGrid.dogridquery');
}


function saveGW2(){
	
	var grid = Ext.getCmp('allGrid');
	var store = grid.getStore();
	var length = store.getCount();
	var a0200s='';
	for (var i = 0; i < length; i++) {
		var selected = store.getAt(i);
		var record = selected.data;
		if (record.personcheck) {
			a0200s = a0200s + record.a0200 + ',';
		}
	} 
	if (a0200s == '') {
		odin.alert("��ѡ���λ��");
		return;
	}
	a0200s = a0200s.substring(0, a0200s.length - 1);
	$("#a0200s").val(a0200s);
	radow.doEvent('save',"a0200s");
}


function saveGW(){
	if($('#a0200').val()==''){
		$h.alert('','��ѡ���λ!');
		return;
	}
	radow.doEvent('save',"a0200");
}


Ext.onReady(function(){
	var noticeSetgrid = Ext.getCmp('allGrid');
	noticeSetgrid.on('rowclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index)
		$('#a0200').val(rc.data.a0200);
	});
	noticeSetgrid.on('rowdblclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index)
		$('#a0200').val(rc.data.a0200);
		saveGW()
	});
	
	parent.Ext.getCmp(subWinId).on('beforeclose',function(){
		realParent.infoSearch();
	})
	
}); 

</script>
</html>