<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%--用户接受路径参数的值，并保存 --%>
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
			<odin:gridEditColumn header="姓名"  editor="text" dataIndex="a0101" edited="false" width="70" />
			<odin:gridEditColumn2 header="性别"  editor="select" dataIndex="a0104" codeType="GB2261" edited="false" width="40"/>
			<odin:gridEditColumn2 header="民族"  editor="select" dataIndex="a0117" codeType="GB3304" edited="false" width="70"/>
			<odin:gridEditColumn header="身份证号"  editor="text" dataIndex="a0184" edited="false" width="150"/>
			<odin:gridEditColumn2 header="现职务层次"  editor="select" dataIndex="a0221" codeType="ZB09" edited="false" width="150"/>
			<odin:gridEditColumn header="任职机构及职务全称" editor="text" dataIndex="a0192a" edited="false" isLast="true" width="200"/>
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
	grid.colModel.setColumnWidth(0,grid_column_0*grid_width,'');//第0列
	grid.colModel.setColumnWidth(1,grid_column_1*grid_width,'');//第1列
	grid.colModel.setColumnWidth(2,grid_column_2*grid_width,'');//第2列
	grid.colModel.setColumnWidth(3,grid_column_3*grid_width,'');//第3列
	grid.colModel.setColumnWidth(4,grid_column_4*grid_width,'');//第4列
	grid.colModel.setColumnWidth(5,grid_column_5*grid_width,'');//第5列
	grid.colModel.setColumnWidth(6,grid_column_6*grid_width,'');//第6列
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
	//动态设置列宽
	grid.colModel.setColumnWidth(0,grid_column_0*grid_width,'');//第0列
	grid.colModel.setColumnWidth(1,grid_column_1*grid_width,'');//第1列
	grid.colModel.setColumnWidth(2,grid_column_2*grid_width,'');//第2列
	grid.colModel.setColumnWidth(3,grid_column_3*grid_width,'');//第3列
	grid.colModel.setColumnWidth(4,grid_column_4*grid_width,'');//第4列
	grid.colModel.setColumnWidth(5,grid_column_5*grid_width,'');//第5列
	grid.colModel.setColumnWidth(6,grid_column_6*grid_width,'');//第6列
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
							    text:'导出Excel',
							    handler:function(){
							    	expExcelFromGrid();
							    }
							})
							]); 
});
function expExcelFromGrid(){
	
	var excelName = null;
	
	//excel导出名称的拼接 
	var pgrid = Ext.getCmp('gridfc');
	var dstore = pgrid.getStore();
	excelName = "人员信息" + "_" + Ext.util.Format.date(new Date(), "YmdHis");
	
	odin.grid.menu.expExcelFromGrid('gridfc', excelName, null,null, false);
	
}
</script>