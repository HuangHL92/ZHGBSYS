<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%--�û�����·��������ֵ�������� --%>
<%@include file="/comOpenWinInit.jsp" %>

<div id="fancha_div" style="width:100%;height:100%;margin: 0 auto;">
	<odin:editgrid property="gridfc" title="" height="505"
		 autoFill="true" bbarId="pageToolBar">
		<odin:gridJsonDataModel>
			<odin:gridDataCol name="a0101" />
			<odin:gridDataCol name="a0104" />
			<odin:gridDataCol name="a0117" />
			<odin:gridDataCol name="a0184" />
			<odin:gridDataCol name="a0221" />
			<odin:gridDataCol name="a0192a" isLast="true" />
		</odin:gridJsonDataModel>
		<odin:gridColumnModel>
			<odin:gridRowNumColumn></odin:gridRowNumColumn>
			<odin:gridEditColumn header="����"  editor="text" dataIndex="a0101" edited="false" width="70" />
			<odin:gridEditColumn2 header="�Ա�"  editor="select" dataIndex="a0104" codeType="GB2261" edited="false" width="40"/>
			<odin:gridEditColumn2 header="����"  editor="select" dataIndex="a0117" codeType="GB3304" edited="false" width="70"/>
			<odin:gridEditColumn header="���֤��"  editor="text" dataIndex="a0184" edited="false" width="150"/>
			<odin:gridEditColumn2 header="��ְ����"  editor="select" dataIndex="a0221" codeType="ZB09" edited="false" width="150"/>
			<odin:gridEditColumn header="��ְ������ְ��ȫ��" editor="text" dataIndex="a0192a" edited="false" isLast="true" width="200"/>
		</odin:gridColumnModel>
		<odin:gridJsonData>
			{
		        data:[]
		    }
		</odin:gridJsonData>
	</odin:editgrid>
</div>
<script type="text/javascript">
function jcHeightWidth(){
	setTimeout(jcHeightWidth1,300); 
	
}
function jcHeightWidth1(){
	grid=Ext.getCmp("gridfc");
	grid.setHeight(document.body.offsetHeight);
	grid.setWidth(document.body.offsetWidth);
	grid_width=grid.getWidth();
	grid.colModel.setColumnWidth(0,grid_column_0*grid_width,'');//��0��
	grid.colModel.setColumnWidth(1,grid_column_1*grid_width,'');//��1��
	grid.colModel.setColumnWidth(2,grid_column_2*grid_width,'');//��2��
	grid.colModel.setColumnWidth(3,grid_column_3*grid_width,'');//��3��
	grid.colModel.setColumnWidth(4,grid_column_4*grid_width,'');//��4��
	grid.colModel.setColumnWidth(5,grid_column_5*grid_width,'');//��5��
	grid.colModel.setColumnWidth(6,grid_column_6*grid_width,'');//��6��
}
var grid_column_0=0.02;
var grid_column_1=0.1;
var grid_column_2=0.05;
var grid_column_3=0.1;
var grid_column_4=0.2;
var grid_column_5=0.2;
var grid_column_6=0.3;

Ext.onReady(function() {
	document.getElementById("fancha_div").parentNode.parentNode.style.overflow='hidden';
	grid=Ext.getCmp("gridfc");
	grid.setHeight(document.body.offsetHeight);
	grid.setWidth(document.body.offsetWidth);
	grid_width=grid.getWidth();
	//��̬�����п�
	grid.colModel.setColumnWidth(0,grid_column_0*grid_width,'');//��0��
	grid.colModel.setColumnWidth(1,grid_column_1*grid_width,'');//��1��
	grid.colModel.setColumnWidth(2,grid_column_2*grid_width,'');//��2��
	grid.colModel.setColumnWidth(3,grid_column_3*grid_width,'');//��3��
	grid.colModel.setColumnWidth(4,grid_column_4*grid_width,'');//��4��
	grid.colModel.setColumnWidth(5,grid_column_5*grid_width,'');//��5��
	grid.colModel.setColumnWidth(6,grid_column_6*grid_width,'');//��6��
	window.onresize=jcHeightWidth;
});
Ext.onReady(function(){
	var pgrid = Ext.getCmp('gridfc');
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
							})
							]); 
});
function expExcelFromGrid(){
	
	var excelName = null;
	
	//excel�������Ƶ�ƴ�� 
	var pgrid = Ext.getCmp('gridfc');
	var dstore = pgrid.getStore();
	excelName = "��Ա��Ϣ" + "_" + Ext.util.Format.date(new Date(), "YmdHis");
	
	odin.grid.menu.expExcelFromGrid('gridfc', excelName, null,null, false);
	
}
</script>