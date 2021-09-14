<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<html class="ext-strict x-viewport">
<script src="<%=request.getContextPath()%>/pages/xbrm2/js/jquery-1.4.4.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comboxWithTree.js"></script>
<link rel="stylesheet" type="text/css" href="mainPage/css/bootstrap-combined.min.css"> 
<link rel="stylesheet" type="text/css" href="mainPage/css/odin-font-size.css"> 
<%@include file="/comOpenWinInit.jsp" %>

<odin:hidden property="b0111"/>
<odin:hidden property="fabd00"/>
<odin:hidden property="fxyp00"/>
<odin:hidden property="a0000"/>
<odin:hidden property="a0200"/>

<!-- 通过选择模拟情况 -->
<odin:hidden property="famx00sel"/>
<odin:hidden property="flag" value="1"/>

<script type="text/javascript">
function init(){//初始化参数
	document.getElementById("fabd00").value=parentParams.fabd00;
	document.getElementById("a0000").value=parentParams.a0000;
	document.getElementById("a0200").value=parentParams.a0200;
	document.getElementById("b0111").value=parentParams.b0111;
	document.getElementById("famx00sel").value=parentParams.famx00;
	updateGrid();
}

function updateGrid(){
	radow.doEvent('updateGrid');
}



</script>
<div id="TitleContent" style="width: 100%;text-align: right;">
<button type='button' class="btn btn-primary" onclick="saveGW()" style="margin: 5px 10px 0px 10px;">保存去向</button>
</div>
<table>
	<tr height="30">
		<odin:textEdit property="mntpname" label="模拟调配名" colspan="4" width="560" readonly="true"></odin:textEdit>
	</tr>
	<tr height="30">
		<odin:select2 property="famx00" label="调配方案" colspan="4" width="560"/>
	</tr>
	<tr height="30">
		<tags:ComBoxWithTree property="mntp_b01" listWidth="543" listHeight="350" label="调配单位" colspan="4" readonly="true" nodeDblclick="changeB01" width="560" codetype="B01" />
	</tr>
	<tr height="30">
		<odin:textEdit property="tp0101" label="人员姓名" width="200" readonly="true"> </odin:textEdit>
		<odin:textEdit property="b0101" label="单位名" width="200" readonly="true"> </odin:textEdit>
	</tr>
	<tr>
		<td colspan="4" width="800">
				<odin:editgrid2 property="allGrid" hasRightMenu="false"  bbarId="pageToolBar" height="360" title="去向岗位表" forceNoScroll="true" autoFill="true" pageSize="200"  url="/">
					<odin:gridJsonDataModel>
						<odin:gridDataCol name="fxyp00"/>
						<odin:gridDataCol name="a0215a"/>
						<odin:gridDataCol name="a0201e"/>
						<odin:gridDataCol name="a0101"/>
						<odin:gridDataCol name="a0192a"/>
						<odin:gridDataCol name="sortid"/>
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
						<odin:gridRowNumColumn></odin:gridRowNumColumn>
						<odin:gridEditColumn2 dataIndex="a0215a" width="100" menuDisabled="true" sortable="false"  header="岗位名称" editor="text" edited="false" align="center" />
						<odin:gridEditColumn2 dataIndex="a0201e" width="40" menuDisabled="true" sortable="false" header="岗位类别" editor="select" codeType="ZB129" edited="false" align="center" />
						<odin:gridEditColumn2 dataIndex="a0101" width="50" menuDisabled="true" sortable="false" header="现任干部" editor="text" edited="false" isLast="true" align="center" />
					</odin:gridColumnModel>
				</odin:editgrid2>
		</td>
	</tr>
	
</table>



<script type="text/javascript">
function changeB01(){
	radow.doEvent('allGrid.dogridquery');
}



function saveGW(){
	if($('#fxyp00').val()==''){
		$h.alert('','请选择去向岗位!');
		return;
	}
	if($('#famx00').val()==''){
		$h.alert('','请选调配方案!');
		return;
	}
	if($('#mntp_b01').val()==''){
		$h.alert('','请选调配去向单位!');
		return;
	}
	radow.doEvent('save');
}


Ext.onReady(function(){
	var noticeSetgrid = Ext.getCmp('allGrid');
	noticeSetgrid.on('rowclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index)
		$('#fxyp00').val(rc.data.fxyp00);
	});
	
	noticeSetgrid.on('rowdblclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index)
		$('#fxyp00').val(rc.data.fxyp00);
		saveGW()
	});
}); 

</script>
</html>