<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@include file="/comOpenWinInit2.jsp" %>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script src="basejs/jquery-ui/jquery-1.10.2.js"></script>
<script src="basejs/jquery-ui/ui/jquery.ui.core.js"></script>
<script src="basejs/jquery-ui/ui/jquery.ui.widget.js"></script>
<script src="basejs/jquery-ui/ui/jquery.ui.progressbar.js"></script>
  <style>

	</style>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<div id="addSceneContent">
			<odin:editgrid property="errorGrid"  width="100%" url="/" bbarId="pageToolBar"
			pageSize="20">
			<odin:gridJsonDataModel   root="data" >
					<odin:gridDataCol name="per001"  />
					<odin:gridDataCol name="per005"/>
					<odin:gridDataCol name="per002"/>
					<odin:gridDataCol name="per003"/>
					<odin:gridDataCol name="per004"  isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridColumn  dataIndex="per001" hidden="true" header="����"/>
					<odin:gridColumn dataIndex="per005" header="�ϴ��û�" align="center"/>
					<odin:gridEditColumn header="δ������Ա���֤��/��Ƭ"  dataIndex="per002"  edited="false"  editor="text" align="center"/>
					<odin:gridEditColumn header="δ����ԭ��"  dataIndex="per003"  edited="false" editor="text" align="center"/>
					<odin:gridEditColumn header="�滻����ʱ��"   dataIndex="per004"  edited="false" editor="text" align="center" isLast="true"/>
				</odin:gridColumnModel>
				<odin:gridJsonData>
					{
				        data:[]
				    }
			</odin:gridJsonData>
			</odin:editgrid>
</div>
<script type="text/javascript">
	function expExcelFromGrid(){

		var excelName = null;

		//excel�������Ƶ�ƴ��
		var pgrid = Ext.getCmp('errorGrid');
		var dstore = pgrid.getStore();
		excelName = "���������Ϣ" + "_" + Ext.util.Format.date(new Date(), "YmdHis");

		odin.grid.menu.expExcelFromGrid('errorGrid', excelName, null,null, false);

	}

	Ext.onReady(function(){
		var pgrid = Ext.getCmp('errorGrid');
		var bbar = pgrid.getBottomToolbar();
		bbar.insertButton(11,[
			new Ext.menu.Separator({cls:'xtb-sep'}),
			new Ext.Button({
				icon : 'images/icon/table.gif',
				id:'saveSortBtn',
				text:'����Excel',
				handler:function(){
					expExcelFromGrid();
				}
			}),

		]);
	});
</script>
