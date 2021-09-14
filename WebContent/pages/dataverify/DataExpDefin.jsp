<%@page import="com.insigma.odin.framework.db.DBUtil.DBType"%>
<%@page import="com.insigma.odin.framework.db.DBUtil"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/cadremgn/sysmanager/ViewCreate/vc.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comboxWithTree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script type="text/javascript">
function expDefineGridDbClick(grid,rowIndex,colIndex,event){
	var record = grid.store.getAt(rowIndex);//获得双击的当前行的记录
	var INXid=record.get("inx_id");//指标代码类型
	document.getElementById("INXid").value=INXid;
	radow.doEvent("loadtable",INXid);
}
</script>
<div>
<odin:hidden property="INXid"/><!-- 交换格式定义主键 -->
<div   class="area4">
	<table id="tabcon0" style="width:100%;height:100%;">
		<tr>
			<td style="width:320;">
				<odin:groupBox title="交换格式定义" >
				<odin:editgrid property="expDefineGrid" forceNoScroll="true" width="320" height="495" url="/" rowDbClick="expDefineGridDbClick">
					<odin:gridJsonDataModel   root="data" >
						<odin:gridDataCol name="checked"  />
						<odin:gridDataCol name="inx_id"  />
						<odin:gridDataCol name="inx_name"  isLast="true"/>
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
						<odin:gridRowNumColumn></odin:gridRowNumColumn>
						<odin:gridEditColumn header="名称" width="105" dataIndex="inx_id" hidden="true" edited="false" editor="text" align="left" />
						<odin:gridEditColumn2 header="名称" width="215" dataIndex="inx_name" edited="false" sortable="false" menuDisabled="true"  editor="text" align="left" isLast="true"/>
					</odin:gridColumnModel>
				</odin:editgrid>
				</odin:groupBox>
			</td>
		</tr>
	</table>
</div>
<div id="infojihe" class="area4">
	<table id="tabcon1" style="width:100%;height:100%;">
		<tr>
			<td style="width:320;">
				<odin:groupBox title="信息集" >
				<odin:editgrid property="tableList2Grid" forceNoScroll="true" width="320" height="495" url="/" >
					<odin:gridJsonDataModel   root="data" >
						<odin:gridDataCol name="checked"  />
						<odin:gridDataCol name="tblcod"  />
						<odin:gridDataCol name="tblcpt"  isLast="true"/>
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
						<odin:gridRowNumColumn></odin:gridRowNumColumn>
						<odin:gridColumn header="selectall" gridName="tableList2Grid" checkBoxClick="checkClicktable" checkBoxSelectAllClick="getCheckAll" align="center"  editor="checkbox" edited="true" dataIndex="checked" />
						<odin:gridEditColumn header="信息集名" width="105" dataIndex="tblcod" hidden="true" edited="false" editor="text" align="left" />
						<odin:gridEditColumn2 header="信息集名" width="215" dataIndex="tblcpt" edited="false" sortable="false" menuDisabled="true"  editor="text" align="left" isLast="true"/>
					</odin:gridColumnModel>
				</odin:editgrid>
				</odin:groupBox>
			</td>
		</tr>
	</table>
</div>
<!-- 调整对比表【左460/->205】【485/->218】【不需要的需要将codeList2Grid1隐藏掉 】 -->
<div  id="fldzbx" class="area4">
	<table id="tabcon2" style="width:100%;height:100%;">
		<tr>
			<td style="width:320;">
				<odin:groupBox title="信息项(提示:请勾选信息项，作为上报的信息项)" >
				<odin:editgrid property="codeList2Grid" forceNoScroll="true" width="320" height="235" url="/"  >
					<odin:gridJsonDataModel   root="data" >
						<odin:gridDataCol name="ctci" />
						<odin:gridDataCol name="col_name" />
						<odin:gridDataCol name="col_name1" />
						<odin:gridDataCol name="code_type" />
						<odin:gridDataCol name="col_code" />
						<odin:gridDataCol name="col_data_type" />
						<odin:gridDataCol name="table_code" />
						<odin:gridDataCol name="col_data_type_should" />
						<odin:gridDataCol name="checked"  isLast="true" />
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
						<odin:gridRowNumColumn></odin:gridRowNumColumn>
						<odin:gridEditColumn header="指标项名" width="105"  dataIndex="col_name" hidden="true" edited="false" editor="text" align="left" />
						<odin:gridEditColumn2 header="指标项名" width="215"  dataIndex="col_name1"  edited="false" sortable="false" menuDisabled="true" editor="text" align="left"/>
						<odin:gridEditColumn header="指标代码类型" width="105"  dataIndex="code_type" hidden="true" edited="false" editor="text" align="left"/>
						<odin:gridEditColumn header="列数据类型" width="105"  dataIndex="col_data_type" hidden="true" edited="false" editor="text" align="left"/>
						<odin:gridEditColumn header="指标项" width="105"  dataIndex="col_code" hidden="true" edited="false" editor="text" align="left"/>
						<odin:gridEditColumn header="信息表" width="105"  dataIndex="table_code" hidden="true" edited="false" editor="text" align="left"/>
						<odin:gridEditColumn header="指标代码类型2" width="105" hidden="true" dataIndex="col_data_type_should"  edited="false" editor="text" align="left"/>
						<odin:gridColumn header="selectall" width="70" gridName="codeList2Grid" checkBoxClick="checkClickCode" align="center"  editor="checkbox" edited="true" dataIndex="checked"  isLast="true" checkBoxSelectAllClick="checkAll"/>
					</odin:gridColumnModel>
				</odin:editgrid>
				</odin:groupBox>
			</td>
		</tr>
		<tr>
			<td style="width:320;">
				<odin:groupBox title="已选择的信息项(提示:操作可实现移除与排序)" >
				<odin:editgrid property="codeList2Grid1" forceNoScroll="true" width="320" height="235" url="/"  >
					<odin:gridJsonDataModel   root="data" >
					    <odin:gridDataCol name="ctci" />
						<odin:gridDataCol name="col_name" />
						<odin:gridDataCol name="col_name1" />
						<odin:gridDataCol name="code_type" />
						<odin:gridDataCol name="col_code" />
						<odin:gridDataCol name="col_data_type" />
						<odin:gridDataCol name="table_code" />
						<odin:gridDataCol name="col_data_type_should" />
						<odin:gridDataCol name="caozuo"  isLast="true" />
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
						<odin:gridRowNumColumn></odin:gridRowNumColumn>
						<odin:gridEditColumn header="指标项名" width="105"  dataIndex="col_name" hidden="true" edited="false" editor="text" align="left" />
						<odin:gridEditColumn2 header="指标项名" width="215"  dataIndex="col_name1" sortable="false" menuDisabled="true"  edited="false" editor="text" align="left"/>
						<odin:gridEditColumn header="指标代码类型" width="105"  dataIndex="code_type" hidden="true" edited="false" editor="text" align="left"/>
						<odin:gridEditColumn header="列数据类型" width="105"  dataIndex="col_data_type" hidden="true" edited="false" editor="text" align="left"/>
						<odin:gridEditColumn header="指标项" width="105"  dataIndex="col_code" hidden="true" edited="false" editor="text" align="left"/>
						<odin:gridEditColumn header="信息表" width="105"  dataIndex="table_code" hidden="true" edited="false" editor="text" align="left"/>
						<odin:gridEditColumn header="指标代码类型2" width="105" hidden="true" dataIndex="col_data_type_should"  edited="false" editor="text" align="left"/>
						<odin:gridEditColumn header="操作" width="70" align="center" edited="false" editor="text" dataIndex="caozuo" renderer="runorstop"  isLast="true" />
					</odin:gridColumnModel>
				</odin:editgrid>
				</odin:groupBox>
			</td>
		</tr>
	</table>
</div>

<div id="divsave4" style="width:1000px;text-align:right;display:block;" >
	<table id="tabcon4" border="0">
		<tr>
			<td style="text-align:right;width:800px;" >
				<odin:button text="新增保存" property="addbtn" handler="saveInfo"></odin:button>
			</td>
			<td width="100px">
			</td>
			<td style="text-align:right;width:100px;" >
				<odin:button text="修改保存" property="btn4" handler="saveInfo"></odin:button>
			</td>
			<td width="100px">
			</td>
			<td style="width:30;text-align:left;width:145px;">
				<odin:button text="删除" property="btn5" handler="delInfo"></odin:button>
			</td>
		</tr>
		<tr height="9px">
			<td colspan="3"></td>
		</tr>
	</table>
</div>


</div>
<script type="text/javascript">
/* zxw修改样式 */
function checkClicktable(num,un,dataIndex,gridid){//选中信息集，查询显示，信息项
	//var grid = Ext.getCmp(gridid);
	cleanInfo();
	radow.doEvent("tabletofld");
}
function getCheckAll(){
	cleanInfo();
	radow.doEvent("tabletofld");
}
function cleanInfo(){
}
function checkClickCode(rowIndex,un,dataIndex,gridid){//选中信息项，实现该信息项移至预览信息项
	radow.doEvent("checkClickCode",rowIndex);
}
//全选
function checkAll(grid_id,col_id){
	if($("#selectall_codeList2Grid_checked").attr("class")=="x-grid3-check-col-on"){
		radow.doEvent("checkClickCodeAll");
	}
	
}
function delInfo(obj){
	var grid = Ext.getCmp("expDefineGrid");
	var store = grid.getStore();
	var records = grid.getSelectionModel().getSelections();
	if(records.length>0){
		 var record = records[0]; 
		 var inx_name = record.get("inx_name");
		 var inx_id = record.get("inx_id");
		 Ext.MessageBox.confirm("提示","确定删除交换格式：“"+inx_name+"”吗？",function(id){
		 	if(id=="yes"){
		 		radow.doEvent("delInfo",inx_id);
		 	}
		 });
	}
   
}
function saveInfo(obj){
	var INXid = document.getElementById("INXid").value;
	if(obj.id=="addbtn"){
		INXid = "";
	}
	if(INXid==''){
		Ext.MessageBox.prompt("输入框","请输入交换格式名称：",function(bu,txt){    
			 if(bu=="ok"&&txt!=''){
				 radow.doEvent("saveInfo",txt);
			 }
		        
		},this); 
	}else{
		radow.doEvent("saveInfo");
	}
	//radow.doEvent("saveInfo");
}

function runorstop(value, params, record,rowIndex,colIndex,ds){
	var contextPath = '<%=request.getContextPath()%>';
	return "<img  src='"+contextPath+"/images/wrong.gif' title='删除' style='cursor:pointer' onclick=delThisOne(this,'"+value+"','"+rowIndex+"')><a>&nbsp;&nbsp;</a><img  src='"+contextPath+"/image/up.png' title='上移' style='cursor:pointer' onclick=upThisOne(this,'"+value+"','"+rowIndex+"')><img src='"+contextPath+"/image/down.png' title='下移' style='cursor:pointer' onclick=downThisOne(this,'"+value+"','"+rowIndex+"')>";
}
function delThisOne(obj,value,rowIndex){
	radow.doEvent("delThisOne",rowIndex);
}


function upThisOne(obj,value,rowIndex){
	var grid_id4='codeList2Grid1';
	var grid4=Ext.getCmp(grid_id4);
	var store = grid4.getStore();
	var selections = grid4.getSelectionModel().getSelections();
	   var record = selections[0]; 
	   var index = store.indexOf(record);
	    if (index > 0) {  
	        store.removeAt(index);  
	        store.insert(index - 1, record);  
	        grid4.getView().refresh();
	        grid4.getSelectionModel().selectRange(index - 1, index - 1);  
	    }  
	//radow.doEvent("upThisOne",rowIndex);
}
function downThisOne(obj,value,rowIndex){
	var grid_id4='codeList2Grid1';
	var grid=Ext.getCmp(grid_id4);
	var store = grid.getStore();
	var records = grid.getSelectionModel().getSelections();
    var record = records[0]; 
    var index = store.indexOf(record);
    if (index < store.getCount() - 1) {  
        store.removeAt(index);  
        store.insert(index + 1, record);  
        grid.getView().refresh(); 
        grid.getSelectionModel().selectRange(index + 1, index + 1);  
    }  
	//radow.doEvent("downThisOne",rowIndex);
}
</script>
