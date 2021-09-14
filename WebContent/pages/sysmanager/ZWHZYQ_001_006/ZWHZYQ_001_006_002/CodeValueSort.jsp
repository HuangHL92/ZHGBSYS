<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<script  type="text/javascript">

function InitGrid(){
	var grid_codevalue = odin.ext.getCmp('gridCodevalue');
	//获得列模式
	var count = grid_codevalue.colModel;
	//获得顺序对应的列
	var col = count.getColumnById(2);
	col.sortable=false;
	col.menuDisabled=true;  //把小箭头去掉
	
	radow.doEvent('gridCodevalue.dogridquery',"0","200");
} 


/*btnUP*/
function UpBtn(){	
	var grid = odin.ext.getCmp('gridCodevalue');
	
	var sm = grid.getSelectionModel().getSelections();
	var store = grid.store;
	//alert(store.getCount());
	
	if (sm.length<=0){
		alert('请选中需要排序的代码!')
		return;	
	}
	
	var selectdata = sm[0];  //选中行中的第一行
	var index = store.indexOf(selectdata);
	if (index==0){
		alert('该代码已经排在最上端!')
		return;
	}
	
	store.remove(selectdata);  //移除
	store.insert(index-1, selectdata);  //插入到上一行前面
	
	grid.getSelectionModel().selectRow(index-1,true);  //选中上移动后的行	
	
	grid.getView().refresh();
}


function DownBtn(){	
	var grid = odin.ext.getCmp('gridCodevalue');
	
	var sm = grid.getSelectionModel().getSelections();
	var store = grid.store;
	if (sm.length<=0){
		alert('请选中需要排序的代码!')
		return;	
	}
	
	var selectdata = sm[0];  //选中行中的第一行
	var index = store.indexOf(selectdata);
	var total = store.getCount();
	if (index==(total-1) ){
		alert('该代码已经排在最下端!')
		return;
	}
	
	store.remove(selectdata);  //移除
	store.insert(index+1, selectdata);  //插入到上一行前面
	
	grid.getSelectionModel().selectRow(index+1,true);  //选中上移动后的行	
}

</script>

<div  style="width:98%">
<odin:hidden property="subCodeValue"/>
<odin:hidden property="codetype"/>
<table style="width:90%;">
	<tr>
		<td style="width:80%">
			
			<odin:editgrid2 property="gridCodevalue"   height="440" 
						bbarId="pageToolBar" isFirstLoadData="false" url="/"   topBarId=""  pageSize="200">
						<odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
							<odin:gridDataCol name="codevalue" />
							<odin:gridDataCol name="codename" isLast="true"/>
						</odin:gridJsonDataModel>
						<odin:gridColumnModel>
							<odin:gridRowNumColumn></odin:gridRowNumColumn>
							<odin:gridEditColumn sortable="false" header="编码" edited="false" width="200" dataIndex="codevalue"  hidden="true" align="center" editor="text"   />
							<odin:gridEditColumn  header="代码名称" edited="false" width="250" dataIndex="codename" align="center" editor="text" isLast="true" />
						</odin:gridColumnModel>
					</odin:editgrid2>	
		</td>
		<td style="width:0%">
			<odin:button property="UpBtn"  handler="UpBtn" text="上移"></odin:button><br>
			<odin:button property="DownBtn" handler="DownBtn"  text="下移"></odin:button><br>
			<odin:button property="closeBtn" text="关闭"></odin:button><br>
			
			<odin:button property="saveBtn" text="保存"></odin:button>
			
		</td>
	
	</tr>
</table>


</div>




