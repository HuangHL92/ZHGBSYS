<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<odin:toolBar property="btnToolBar" applyTo="btnToolBarDiv">
	<odin:fill />
	<odin:buttonForToolBar text="导出列表Excel" id="exportExcel" handler="exportExcel" icon="images/icon/exp.png" isLast="true"></odin:buttonForToolBar>
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
							<odin:textEdit property="b0114" label="机构编码" />
						</tr>
					</table>
				</td>
				<td>
					<odin:checkbox property="isnotchecked" label="包含空编码"></odin:checkbox>
				</td>
				<td align="left">
					<odin:button text="查询" property="search"></odin:button>
				</td>
				
			<%-- 	<odin:textEdit property="b0101" label="机构名称"/>
				<odin:textEdit property="b0104" label="机构简称"/> --%>
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
							<odin:gridEditColumn header="机构id" dataIndex="b0111" align="center" edited="false" editor="text"  hidden="true"/>
							<odin:gridEditColumn header="上级机构" dataIndex="b0121" align="center" edited="false" editor="text" width="200"/>
							<odin:gridEditColumn header="机构类型" dataIndex="b0194" align="center" edited="false" editor="text" width="100"/>
							<odin:gridEditColumn header="机构编码" dataIndex="b0114"  edited="false" editor="text" width="100"/>
							<odin:gridEditColumn header="机构名称" dataIndex="b0101" align="center" edited="false" editor="text"  width="10"/>
							<odin:gridEditColumn header="简称" dataIndex="b0104" align="center" edited="false" editor="text" width="200"/>
							<odin:gridEditColumn header="备注信息" dataIndex="b0180"  edited="false" editor="text" isLast="true" width="400"/>		
						</odin:gridColumnModel>
						</odin:editgrid>
				</td>
			</tr>
		</table>
	
</div>
</div>
<div>
<script type="text/javascript">
//列表双击事件
function DbClick_grid_func(grid,rowIndex,colIndex,event){
	var record = grid.store.getAt(rowIndex);//获得双击的当前行的记录
	//弹出窗口
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
	grid.colModel.setColumnWidth(0,grid_column_0*grid_width,'');//第0列
	grid.colModel.setColumnWidth(1,grid_column_1*grid_width,'');//第1列
	grid.colModel.setColumnWidth(2,grid_column_2*grid_width,'');//第2列
	grid.colModel.setColumnWidth(3,grid_column_3*grid_width,'');//第3列
	grid.colModel.setColumnWidth(4,grid_column_4*grid_width,'');//第4列
	grid.colModel.setColumnWidth(5,grid_column_5*grid_width,'');//第5列
	grid.colModel.setColumnWidth(6,grid_column_6*grid_width,'');//第6列
	
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
	//设置列表宽度
	grid.setHeight(document.body.offsetHeight-86);
	var grid_width=grid.getWidth();
	grid.colModel.setColumnWidth(0,grid_column_0*grid_width,'');//第0列
	grid.colModel.setColumnWidth(1,grid_column_1*grid_width,'');//第1列
	grid.colModel.setColumnWidth(2,grid_column_2*grid_width,'');//第2列
	grid.colModel.setColumnWidth(3,grid_column_3*grid_width,'');//第3列
	grid.colModel.setColumnWidth(4,grid_column_4*grid_width,'');//第4列
	grid.colModel.setColumnWidth(5,grid_column_5*grid_width,'');//第5列
	grid.colModel.setColumnWidth(6,grid_column_6*grid_width,'');//第6列
	
	window.onresize=jcHeightWidth;
});
//初始化宽度，设置横向不滚动
Ext.onReady(function() {
	$(document.body).css({
	    "overflow-x":"hidden",//禁止body横向滚动
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
	
	//excel导出名称的拼接 
	var pgrid = Ext.getCmp('repeatInfogrid');
	var dstore = pgrid.getStore();
	
	var num = dstore.getTotalCount()
	
	if(num != 0){
		//获得列表第一个人
		excelName = dstore.getAt(0).get('b0101');
		
		if(num > 1){
			excelName = excelName + "等" + num +"个机构";
		}
	}
	
	excelName = "机构信息" + "_" + excelName
	+ "_" + Ext.util.Format.date(new Date(), "Ymd");
	
	odin.grid.menu.expExcelFromGrid('repeatInfogrid', excelName, null,null, false);
	
}  

</script>