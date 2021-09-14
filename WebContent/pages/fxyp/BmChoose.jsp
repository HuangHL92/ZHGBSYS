<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@include file="/comOpenWinInit2.jsp"%>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/jwjc/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>

<odin:toolBar property="btnToolBar">
	<odin:fill />
	<odin:buttonForToolBar text="选择" id="save" icon="images/save.gif"
		isLast="true" />
</odin:toolBar>

<div class="contentDiv">
	<div class="float_left1">
		<table>
			<tbody>
				<tr>
					<odin:select2 property="stype"
						label="<span style='margin-left:2%'>查询条件：</span>"
						canOutSelectList="false" editor="false" value="name"
						data="['name','姓名'],['idCard','身份证号']" width="100"></odin:select2>
					<odin:textEdit property="colsm" width="200"></odin:textEdit>
					<td style="text-align: center; width: 200px;">
						<button class="bluebutton" type="button" onclick="serach()">搜索</button>
						<%-- <img src="<%=request.getContextPath()%>/images/bc.png" onclick="radow.doEvent('peopleInfoGrid.dogridquery')"> --%>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div id="gridDiv"
		style="width: 550px; height: 350px; border: 2px solid #c3daf9; float: left;">
		<odin:editgrid property="peopleInfoGrid" title="人员列表" width="550"
			height="350" pageSize="999" autoFill="false" topBarId="btnToolBar"
			bbarId="pageToolBar">
			<odin:gridJsonDataModel>
				<odin:gridDataCol name="checked" />
				<odin:gridDataCol name="a0000" />
				<odin:gridDataCol name="a0101" />
				<odin:gridDataCol name="a0184" />
				<odin:gridDataCol name="a0104" />
				<odin:gridDataCol name="a0163" />
				<odin:gridDataCol name="a0192a" isLast="true" />
			</odin:gridJsonDataModel>
			<odin:gridColumnModel>
				<odin:gridRowNumColumn></odin:gridRowNumColumn>
				<odin:gridColumn header="selectall" width="30"
					gridName="peopleInfoGrid" dataIndex="checked" editor="checkbox"
					edited="true" hidden="false" />
				<odin:gridColumn dataIndex="a0101" edited="false" header="姓名"
					width="55" align="center" />
				<odin:gridEditColumn2 dataIndex="a0104" edited="false" header="性别"
					width="45" align="center" editor="select" codeType="GB2261" />
				<odin:gridColumn dataIndex="a0184" edited="false" header="身份证号"
					width="140" align="center" hidden="true" />
				<odin:gridColumn dataIndex="a0192a" edited="false" header="单位职务"
					width="385" align="left" isLast="true" />
			</odin:gridColumnModel>
			<odin:gridJsonData>
				{
					data:[]
				}
			</odin:gridJsonData>
		</odin:editgrid>
	</div>
</div>
<!-- 查询类型 -->
<odin:hidden property="b0111" />
<!-- 主办或协办已选的人员（选择主办存放的是协办人员主键，反之选择协办存放的是主办人员主键） -->
<script type="text/javascript">
Ext.onReady(function() {
	document.getElementById("b0111").value=parent.document.getElementById('checkedgroupid').value;
});

// 搜索
function serach() {
    radow.doEvent("peopleInfoGrid.dogridquery");
}

function saveHandler() {
	var peopleInfoGrid = Ext.getCmp("peopleInfoGrid");
	var selModel = peopleInfoGrid.getSelectionModel();
	var selections = selModel.getSelections();	// 获取选择的记录
	if (selections.length == 0) {
		$h.alert('系统提示：','请先勾选人员！',null,150);
		return;
	}
}
</script>