<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@include file="/comOpenWinInit2.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script  type="text/javascript">
//alert(parent.window.document.getElementById('iframe_workUnits').contentWindow.document.getElementById("a0201b").value);

/*btnUP*/
function UpBtn(){	
	var grid = odin.ext.getCmp('grid');
	
	var sm = grid.getSelectionModel().getSelections();
	var store = grid.store;
	//alert(store.getCount());
	
	if (sm.length<=0){
		alert('请选中需要排序的人员!')
		return;	
	}
	if(sm.length>1){
		alert('只能选中一个需要排序的人员!')
		return;
	}
	var selectdata = sm[0];  //选中行中的第一行
	var index = store.indexOf(selectdata);
	if (index==0){
		alert('该人员已经排在最顶上!')
		return;
	}
	
	store.remove(selectdata);  //移除
	store.insert(index-1, selectdata);  //插入到上一行前面
	
	grid.getSelectionModel().selectRow(index-1,true);  //选中上移动后的行	
	
	grid.getView().refresh();
}


function DownBtn(){	
	var grid = odin.ext.getCmp('grid');
	
	var sm = grid.getSelectionModel().getSelections();
	var store = grid.store;
	if (sm.length<=0){
		alert('请选中需要排序的机构!')
		return;	
	}
	if(sm.length>1){
		alert('只能选中一个需要排序的人员!')
		return;
	}
	var selectdata = sm[0];  //选中行中的第一行
	var index = store.indexOf(selectdata);
	var total = store.getCount();
	if (index==(total-1) ){
		alert('该人员已经排在最底上!')
		return;
	}
	
	store.remove(selectdata);  //移除
	store.insert(index+1, selectdata);  //插入到上一行前面
	
	grid.getSelectionModel().selectRow(index+1,true);  //选中上移动后的行	
	grid.view.refresh();
}

</script>

<div  style="width:98%">
<odin:hidden property="sql"/>
<script type="text/javascript">
Ext.onReady(function(){
	
	new Ext.Button({
		icon : 'images/icon/icon_upwards.gif',
		id:'UpBtn',
	    text:'上&nbsp;移',
	    cls :'inline pl',
	    renderTo:"UpBtn1",
	    handler:function(){
	    	UpBtn();
	    }
	});
	new Ext.Button({
		icon : 'images/icon/icon_adown.gif',
		id:'DownBtn',
	    text:'下&nbsp;移',
	    cls :'inline pl',
	    renderTo:"DownBtn1",
	    handler:function(){
	    	DownBtn();
	    }
	});
	/* new Ext.Button({
		icon : '',
		id:'sortPost',
	    text:'按职务层次',
	    cls :'inline pl',
	    renderTo:"sortPost1",
	    handler:function(){
	    	sortPost();
	    }
	});
	new Ext.Button({
		icon : '',
		id:'sortOrderCode',
	    text:'按序码排序',
	    cls :'inline pl',
	    renderTo:"sortOrderCode1",
	    handler:function(){
	    	sortOrderCode();
	    }
	}); */
	new Ext.Button({
		icon : 'images/icon/save.gif',
		id:'saveBtn',
	    text:'确&nbsp;定',
	    cls :'inline pl',
	    renderTo:"saveBtn1",
	    handler:function(){
	    	//saveBtn();
	    }
	});
	new Ext.Button({
		icon : 'images/icon/close.gif',
		id:'closeBtn',
	    text:'关&nbsp;闭',
	    renderTo:"closeBtn1",
	    cls :'inline pl'
	});
});
</script>

<table>
	<tr>
		<odin:select2 colspan="2" property="gbquery" onchange="query()" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;干部名册分类：" data="['0', '干部总名册'],['1', '重点岗位'],['3', '年轻干部'],['4', '专业干部'],['5', '干部梯队'],['6', '结构性干部'],['7', '年龄段干部'],['2', '职级晋升、到龄等相关名册']" title="干部名册分类" value="0"></odin:select2>
	</tr>
</table>
<table style="width:100%;">
	<tr>
		<td style="width:80%">
			<!-- <font id="a0201b_combo" style="font-size: 12px;"></font> -->
			<odin:editgrid2 property="grid"   height="420" 
				bbarId="pageToolBar" isFirstLoadData="false" url="/"   topBarId=""  pageSize="1000">
				<odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="uuid" />
					<odin:gridDataCol name="name" />
					<odin:gridDataCol name="sortid" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridEditColumn sortable="false"  header="排序" hidden="true" edited="false"  dataIndex="sortid" align="center" editor="text"  width="8" edited="true"/>
					<odin:gridEditColumn sortable="false"  header="干部名册名称"  edited="false"  dataIndex="name" align="center" editor="text"  />
					<odin:gridEditColumn sortable="false"  header="主键"  edited="false" hidden="true" dataIndex="uuid" editor="text" isLast="true"   />
				</odin:gridColumnModel>
			</odin:editgrid2>	
		</td>
		<td style="width:6%"></td>
		<td style="width:14%;" align="center">
			<%-- <odin:button property="UpBtn"  handler="UpBtn" text="上移"></odin:button><br>
			<odin:button property="DownBtn" handler="DownBtn"  text="下移"></odin:button><br>
			<odin:button property="saveBtn" text="保存"></odin:button> --%>
			
			<div id='UpBtn1'></div>
			<br>
			<div id='DownBtn1'></div>
			<br>
			<div id='saveBtn1'></div>
			<br>
			<div id='closeBtn1'></div>
		</td>
		<!-- <td style="width:5%"></td> -->
	</tr>
</table>


</div>

<script type="text/javascript">

Ext.onReady(function(){
//表单和移动
	var pgrid = Ext.getCmp('grid');
	
	
	var ddrow = new Ext.dd.DropTarget(pgrid.container,{
		ddGroup : 'GridDD',
		copy : false,
		notifyDrop : function(dd,e,data){
			
			var dstore = pgrid.getStore();
			
			
			//选中了多少行
			var rows = data.selections;
			//拖动到第几行
			
			
			var index = dd.getDragData(e).rowIndex;
			if (typeof(index) == "undefined"){
				return;
			}
			//修改store
			for ( i=0; i<rows.length; i++){
				var rowData = rows[i];
				if (!this.copy) dstore.remove(rowData);
				dstore.insert(index, rowData);
			}
			pgrid.view.refresh();
			
			
		}
	});
	
});

function query(){
	radow.doEvent("grid.dogridquery");
}
</script>


