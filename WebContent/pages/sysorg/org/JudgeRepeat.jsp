<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<odin:toolBar property="btnToolBar" applyTo="btnToolBarDiv">
	<odin:fill />
	<odin:buttonForToolBar text="�����б�Excel" id="exportExcel" handler="exportExcel" icon="images/icon/exp.png" isLast="true"></odin:buttonForToolBar>
</odin:toolBar>

<div id="btnToolBarDiv" style="width: 1135px;"></div>

<div style="width:100%;" style="margin:0px 0px 0px 0px;">

<div style="margin:0px 0px 0px 0px;width:100px;" id="id_div_2">
		<table style="width:100%;margin:0px 0px 0px 0px;" border="0">
			<col width="20%">
			<col width="8%">
			<col width="72%"> 
			<tr>
				<td colspan="3" height="9"></td>
			</tr>
			<tr>
				<td>
					<table>
						<tr>
							<odin:textEdit property="b0114" label="��������" />
						</tr>
					</table>
				</td>
				<td>
					<odin:checkbox property="isnotchecked" label="�����ձ���"></odin:checkbox>
				</td>
				<td align="left">
					<odin:button text="��ѯ" property="search"></odin:button>
				</td>
				
			<%-- 	<odin:textEdit property="b0101" label="��������"/>
				<odin:textEdit property="b0104" label="�������"/> --%>
			</tr>
			<tr>
				<td colspan="3" height="7"></td>
			</tr>
			<tr>
				<td colspan="3">
					<odin:editgrid property="repeatInfogrid" height="425" width="1135" title=""  cellDbClick="DbClick_grid_func"
					autoFill="true" bbarId="pageToolBar" pageSize="20" isFirstLoadData="false" url="/" >
						<odin:gridJsonDataModel>
							<odin:gridDataCol name="b0121" />
							<odin:gridDataCol name="b0111" />
							<odin:gridDataCol name="b0194" />
							<odin:gridDataCol name="b0114" />
							<odin:gridDataCol name="b0101" />
							<odin:gridDataCol name="b0104" />
							<odin:gridDataCol name="b0180" isLast="true" />
						</odin:gridJsonDataModel>
						<odin:gridColumnModel>
						<odin:gridRowNumColumn></odin:gridRowNumColumn>
							<odin:gridEditColumn header="����id" dataIndex="b0111" align="center" edited="false" editor="text"  hidden="true"/>
							<odin:gridEditColumn header="�ϼ�����" dataIndex="b0121" align="center" edited="false" editor="text" width="200"/>
							<odin:gridEditColumn header="��������" dataIndex="b0194" align="center" edited="false" editor="text" width="100"/>
							<odin:gridEditColumn header="��������" dataIndex="b0114"  edited="false" editor="text" width="100"/>
							<odin:gridEditColumn header="��������" dataIndex="b0101" align="center" edited="false" editor="text"  width="10"/>
							<odin:gridEditColumn header="���" dataIndex="b0104" align="center" edited="false" editor="text" width="200"/>
							<odin:gridEditColumn header="��ע��Ϣ" dataIndex="b0180"  edited="false" editor="text" isLast="true" width="400"/>		
						</odin:gridColumnModel>
						</odin:editgrid>
				</td>
			</tr>
		</table>
	
</div>
</div>
<div>
<script type="text/javascript">
//�б�˫���¼�
function DbClick_grid_func(grid,rowIndex,colIndex,event){
	var record = grid.store.getAt(rowIndex);//���˫���ĵ�ǰ�еļ�¼
	//��������
	radow.doEvent("DbClick_grid",""+","+record.get("b0111"));
}
var init_width="";
var init_height="";
function jcHeightWidth(){
	var grid=Ext.getCmp("repeatInfogrid");
	document.getElementById("id_div_2").style.width=document.body.offsetWidth;
	grid.setWidth(document.body.offsetWidth);
	grid.setHeight(document.body.offsetHeight-86);
	
	var grid_width=grid.getWidth();
	grid.colModel.setColumnWidth(0,grid_column_0*grid_width,'');//��0��
	grid.colModel.setColumnWidth(1,grid_column_1*grid_width,'');//��1��
	grid.colModel.setColumnWidth(2,grid_column_2*grid_width,'');//��2��
	grid.colModel.setColumnWidth(3,grid_column_3*grid_width,'');//��3��
	grid.colModel.setColumnWidth(4,grid_column_4*grid_width,'');//��4��
	grid.colModel.setColumnWidth(5,grid_column_5*grid_width,'');//��5��
	grid.colModel.setColumnWidth(6,grid_column_6*grid_width,'');//��6��
	
}
var grid_column_0=0.03;
var grid_column_1=0.07;
var grid_column_2=0.15;
var grid_column_3=0.10;
var grid_column_4=0.15;
var grid_column_5=0.25;
var grid_column_6=0.23;
Ext.onReady(function() {
	
	var grid=Ext.getCmp("repeatInfogrid");
	grid.setWidth(document.body.offsetWidth);
	document.getElementById("id_div_2").style.width=document.body.offsetWidth;
	//�����б���
	grid.setHeight(document.body.offsetHeight-86);
	var grid_width=grid.getWidth();
	grid.colModel.setColumnWidth(0,grid_column_0*grid_width,'');//��0��
	grid.colModel.setColumnWidth(1,grid_column_1*grid_width,'');//��1��
	grid.colModel.setColumnWidth(2,grid_column_2*grid_width,'');//��2��
	grid.colModel.setColumnWidth(3,grid_column_3*grid_width,'');//��3��
	grid.colModel.setColumnWidth(4,grid_column_4*grid_width,'');//��4��
	grid.colModel.setColumnWidth(5,grid_column_5*grid_width,'');//��5��
	grid.colModel.setColumnWidth(6,grid_column_6*grid_width,'');//��6��
	
	window.onresize=jcHeightWidth;
});
//��ʼ����ȣ����ú��򲻹���
Ext.onReady(function() {
	$(document.body).css({
	    "overflow-x":"hidden",//��ֹbody�������
	    "overflow-y":"auto"
	  });
	checkboxvl("isnotchecked");
});
function checkboxvl(id){
	try{
		var node_66;
		var node_55;
		var node_5_height;
		var node_6_height;
		node_66=$("#"+id).parent();
		node_6_height=node_66[0].offsetHeight;
		node_55=node_66.children();
		node_5_height=node_55[0].offsetHeight;
		var mt=(node_6_height-node_5_height)/2;
		$("#"+id).parent().parent().parent().css({"top": mt+"px"});
	}catch(err){
	}
}


function exportExcel(){
	
	var excelName = null;
	
	//excel�������Ƶ�ƴ�� 
	var pgrid = Ext.getCmp('repeatInfogrid');
	var dstore = pgrid.getStore();
	
	var num = dstore.getTotalCount()
	
	if(num != 0){
		//����б��һ����
		excelName = dstore.getAt(0).get('b0101');
		
		if(num > 1){
			excelName = excelName + "��" + num +"������";
		}
	}
	
	excelName = "������Ϣ" + "_" + excelName
	+ "_" + Ext.util.Format.date(new Date(), "Ymd");
	
	odin.grid.menu.expExcelFromGrid('repeatInfogrid', excelName, null,null, false);
	
}  

</script>