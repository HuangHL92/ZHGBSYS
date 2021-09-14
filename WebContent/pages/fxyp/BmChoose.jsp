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
	<odin:buttonForToolBar text="ѡ��" id="save" icon="images/save.gif"
		isLast="true" />
</odin:toolBar>

<div class="contentDiv">
	<div class="float_left1">
		<table>
			<tbody>
				<tr>
					<odin:select2 property="stype"
						label="<span style='margin-left:2%'>��ѯ������</span>"
						canOutSelectList="false" editor="false" value="name"
						data="['name','����'],['idCard','���֤��']" width="100"></odin:select2>
					<odin:textEdit property="colsm" width="200"></odin:textEdit>
					<td style="text-align: center; width: 200px;">
						<button class="bluebutton" type="button" onclick="serach()">����</button>
						<%-- <img src="<%=request.getContextPath()%>/images/bc.png" onclick="radow.doEvent('peopleInfoGrid.dogridquery')"> --%>
					</td>
				</tr>
			</tbody>
		</table>
	</div>
	<div id="gridDiv"
		style="width: 550px; height: 350px; border: 2px solid #c3daf9; float: left;">
		<odin:editgrid property="peopleInfoGrid" title="��Ա�б�" width="550"
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
				<odin:gridColumn dataIndex="a0101" edited="false" header="����"
					width="55" align="center" />
				<odin:gridEditColumn2 dataIndex="a0104" edited="false" header="�Ա�"
					width="45" align="center" editor="select" codeType="GB2261" />
				<odin:gridColumn dataIndex="a0184" edited="false" header="���֤��"
					width="140" align="center" hidden="true" />
				<odin:gridColumn dataIndex="a0192a" edited="false" header="��λְ��"
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
<!-- ��ѯ���� -->
<odin:hidden property="b0111" />
<!-- �����Э����ѡ����Ա��ѡ�������ŵ���Э����Ա��������֮ѡ��Э���ŵ���������Ա������ -->
<script type="text/javascript">
Ext.onReady(function() {
	document.getElementById("b0111").value=parent.document.getElementById('checkedgroupid').value;
});

// ����
function serach() {
    radow.doEvent("peopleInfoGrid.dogridquery");
}

function saveHandler() {
	var peopleInfoGrid = Ext.getCmp("peopleInfoGrid");
	var selModel = peopleInfoGrid.getSelectionModel();
	var selections = selModel.getSelections();	// ��ȡѡ��ļ�¼
	if (selections.length == 0) {
		$h.alert('ϵͳ��ʾ��','���ȹ�ѡ��Ա��',null,150);
		return;
	}
}
</script>