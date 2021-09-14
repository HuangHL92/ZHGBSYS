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


//上移
function UpBtn(){	
	var grid = odin.ext.getCmp('gridA01');
	var sm = grid.getSelectionModel().getSelections();
	var store = grid.store;
	
	if (sm.length<=0){
		Ext.Msg.alert('系统提示','请选中需要排序的人员！');
		return;	
	}
	
	var selectdata = sm[0];  //选中行中的第一行
	var index = store.indexOf(selectdata);
	if (index==0){
		Ext.Msg.alert('系统提示','该人员已经排在最顶上！');
		return;
	}
	
	store.remove(selectdata);  //移除
	store.insert(index-1, selectdata);  //插入到上一行前面
	grid.getSelectionModel().selectRow(index-1,true);  //选中上移动后的行
	grid.getView().refresh();
}

//下移
function DownBtn(){	
	var grid = odin.ext.getCmp('gridA01');
	
	var sm = grid.getSelectionModel().getSelections();
	var store = grid.store;
	if (sm.length<=0){
		Ext.Msg.alert('系统提示','请选中需要排序的人员！');
		return;	
	}
	
	var selectdata = sm[0];  //选中行中的第一行
	var index = store.indexOf(selectdata);
	var total = store.getCount();
	if (index==(total-1) ){
		Ext.Msg.alert('系统提示','该人员已经排在最底下！');
		return;
	}
	
	store.remove(selectdata);  //移除
	store.insert(index+1, selectdata);  //插入到上一行前面
	grid.getSelectionModel().selectRow(index+1,true);  //选中上移动后的行	
	grid.view.refresh();
}

</script>

<div style="width:98%">
<odin:hidden property="a0201b" title="机构id"/>
<odin:hidden property="a0000"/>
<odin:hidden property="a0101" title="姓名"/>
<odin:hidden property="a0225" title="集体内排序"/>
<script type="text/javascript">

var a0000 = document.getElementById('subWinIdBussessId').value;
Ext.onReady(function(){
	
	document.getElementById("a0201b").value=realParent.document.getElementById("a0201b").value;
	
	var a0201b_combo = realParent.document.getElementById("a0201b_combo").value;
	
	document.getElementById("a0201b_combo").innerText = "当前排序机构："+ a0201b_combo
	
	document.getElementById("a0000").value=a0000;
	document.getElementById("a0101").value=realParent.window.document.getElementById('a0101').value;
	document.getElementById("a0225").value=realParent.document.getElementById("a0225").value;
});
var isload = true;
function rowRenderer(value, params, record,rowIndex,colIndex,ds){
	if(value==a0000&&isload){
		Ext.getCmp('gridA01').getSelectionModel().selectRow(rowIndex,true);
		isload = false;
	}
	return value;
}
</script>
<table style="width:93%;">
	<tr>
		<td style="width:93%">
			<font id="a0201b_combo" style="font-size: 12px;"></font>
			<odin:editgrid property="gridA01"   height="550" 
				bbarId="pageToolBar" isFirstLoadData="false" url="/"   topBarId=""  pageSize="1000">
				<odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="a0000" />
					<odin:gridDataCol name="a0192a" />
					<odin:gridDataCol name="a0101" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridEditColumn sortable="false"   header="姓名"  edited="false"  dataIndex="a0101" align="center" editor="text"  />
					<odin:gridEditColumn sortable="false"   header="工作单位及职务"  edited="false"  dataIndex="a0192a" align="center" editor="text" width="110"/>
					<odin:gridEditColumn sortable="false"   header="选中"  edited="false" hidden="true" renderer="rowRenderer" dataIndex="a0000" editor="text"  />
					<odin:gridEditColumn sortable="false" header="人员编号" edited="false"  dataIndex="a0000" align="center" editor="text" hidden="true" isLast="true"   />
				</odin:gridColumnModel>
			</odin:editgrid>	
		</td>
		<td style="width:7%">
			<odin:button property="UpBtn"  handler="UpBtn" text="上移"></odin:button><br>
			<odin:button property="DownBtn" handler="DownBtn"  text="下移"></odin:button><br>
			<odin:button property="saveBtn" text="保存"></odin:button>
		</td>
	</tr>
</table>
</div>