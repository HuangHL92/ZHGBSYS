<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@include file="/comOpenWinInit.jsp" %>
<script  type="text/javascript">
//alert(parent.window.document.getElementById('iframe_workUnits').contentWindow.document.getElementById("a0201b").value);

function InitGrid(){
	var grid_A01 = odin.ext.getCmp('gridA01');
	//获得列模式
	var count = grid_A01.colModel;
	//获得顺序对应的列
	var col = count.getColumnById(2);
	col.sortable=false;
	col.menuDisabled=true;  //把小箭头去掉
	
	radow.doEvent('gridA01.dogridquery',"0","200");
} 

</script>

<div  style="width:98%">
<odin:hidden property="a0000"/>

<script type="text/javascript">



Ext.onReady(function(){
	
	
});
var isload = true;
/* function rowRenderer(value, params, record,rowIndex,colIndex,ds){
	if(value==a0000&&isload){
		Ext.getCmp('gridA01').getSelectionModel().selectRow(rowIndex,true);
		isload = false;
	}
	return value;
} */

//人员列表单击事件 
function rowClickPeople(a,index){
	var gridcq = Ext.getCmp("gridA01");
	var gStore = gridcq.getStore();
	
	//姓名
	var a0101 = gStore.getAt(index).data.a0101;
	document.getElementById("a0101").innerText = a0101;
	
	//当前序号
	var sort = gStore.getAt(index).data.sort;
	document.getElementById("sortSelect").innerText = sort;
	document.getElementById("sortNow").value = sort;
	
	//id
	var a0000 = gStore.getAt(index).data.a0000;
	document.getElementById("a0000").value = a0000;
}


function save(){
	
	
	var a0000  = document.getElementById('a0000').value;
	if(a0000 == null || a0000 == ""){
		Ext.Msg.alert("提示信息", "请选择记录！");
	　　return false; 
	}
	
	//判断调整序号是否合法
	var sort  = document.getElementById('sort').value;				//调整序号
	var sortMax  = document.getElementById('sortMax').value;		//最大序号
	var sortNow  = document.getElementById('sortNow').value;		//当前序号
	
	
	if (!(/(^[1-9]\d*$)/.test(sort))) { 
　　　　
		Ext.Msg.alert("提示信息", "输入的不是正整数！");
　　　　return false; 
　　}else { 
	
		if(sort == sortNow){
			Ext.Msg.alert("提示信息", "当前序号不能与调整序号一致！");
			return false; 
		}
　　　　
		if(parseInt(sort) > parseInt(sortMax)){
			Ext.Msg.alert("提示信息", "调整序号大于最大序号！");
			return false; 
		}
		
		radow.doEvent("save");
		
　　} 
	
}


function backSort(){
	
	//当前序号
	var sort = document.getElementById('sort').value;
	document.getElementById("sortSelect").innerText = sort;
	document.getElementById("sortNow").value = sort;
	
}

</script>
<table style="width:96%;">
	<tr>
		<td style="width:96%">
			
			<div id="clear_search" style="width:60%">
			<font style="font-size: 12px;">&nbsp;&nbsp;姓名：</font>
						<font id="a0101" style="font-size: 12px;"></font>
						<font style="font-size: 12px;">&nbsp;&nbsp;&nbsp;&nbsp;当前序号：</font>
						<font id="sortSelect" style="font-size: 12px;"></font>
			
			<table style="width:100%;" >
				<tr align="left">
					<odin:textEdit property="sort" label="调整序号" required="true" ></odin:textEdit>
					<td align="right">
						<odin:button text="&nbsp;&nbsp;确&nbsp;&nbsp;认&nbsp;&nbsp;" property="save" handler="save"
							></odin:button>
					</td>
					<td align="right">
						&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</td>
					<td align="right">
						
						<odin:button text="&nbsp;&nbsp;载入列表&nbsp;&nbsp;" property="load"></odin:button>
					</td>
				</tr>
			</table>
		  </div>
		  
			
			<odin:editgrid property="gridA01"   height="567" cellmousedown="rowClickPeople"
				bbarId="pageToolBar" isFirstLoadData="false" url="/"   topBarId=""  pageSize="800">
				<odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="a0000" />
					<odin:gridDataCol name="sessionid" />
					<odin:gridDataCol name="sort" />
					<odin:gridDataCol name="a0192a" />
					<odin:gridDataCol name="a0101" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridEditColumn sortable="false"   header="姓名"  edited="false"  dataIndex="a0101" align="center" editor="text"  width="80"/>
					<odin:gridEditColumn sortable="false"   header="工作单位及职务"  edited="false"  dataIndex="a0192a" align="left" editor="text"  width="150"/>
					<odin:gridEditColumn sortable="false"   header="序号"  edited="false"  dataIndex="sort" align="center" editor="text"  width="80"/>
					<odin:gridEditColumn sortable="false"   header="选中"  edited="false" hidden="true" dataIndex="a0000" editor="text" />
					<odin:gridEditColumn sortable="false" header="人员编号" edited="false"  dataIndex="a0000" align="center" editor="text" hidden="true" isLast="true"   />
					
				</odin:gridColumnModel>
			</odin:editgrid>	
		</td>
		<!-- <td style="width:6%"></td>
		<td style="width:14%;" align="center">
			<div id='UpBtn1'></div>
			<br>
			<div id='DownBtn1'></div>
			<br>
			<div id='saveBtn1'></div>
			<br>
			<div id='closeBtn1'></div>
		</td> -->
		
	</tr>
</table>
<odin:hidden property="sortMax"/>
<odin:hidden property="sortNow"/>
<odin:hidden property="a0000"/>

</div>




