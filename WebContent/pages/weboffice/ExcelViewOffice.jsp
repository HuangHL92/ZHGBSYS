<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%String ctxPath = request.getContextPath();%>
<%@page import="com.insigma.siis.local.pagemodel.weboffice.ExcelViewOfficePageModel"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="js/lengthValidator.js"></script>
<%@include file="/comOpenWinInit2.jsp" %>
<odin:head></odin:head>
<odin:base></odin:base>
<odin:hidden property="downfile" />

<div id="border"> 
<div id="tol2" align="left"></div>
<odin:toolBar property="ToolBar">
			<odin:fill />
			<odin:buttonForToolBar text="上移" id="btn1" handler="up"/>
			<odin:buttonForToolBar text="下移" id="btn2" handler="down"/>
			<odin:buttonForToolBar text="确定" id="btn3" handler="Save" isLast="true"/>			
		</odin:toolBar>
			<odin:grid property="TrainingInfoGrid" height="500" title="" autoFill="true" topBarId="ToolBar" bbarId="pageToolBar" sm="row" isFirstLoadData="false" pageSize="200"  url="/" rowDbClick="rowDbClick">
				<odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="id"  />
					<odin:gridDataCol name="type"  />
					<odin:gridDataCol name="filename" />
			  		<odin:gridDataCol name="creattime"  />
			  		<odin:gridDataCol name="selty"  /> 
			  		<odin:gridDataCol name="updatetime" isLast="true"/>
<%-- 			  		<odin:gridDataCol name="realpath"/> --%>
<%-- 			   		<odin:gridDataCol name="type" isLast="true"/>		   		 --%>
				</odin:gridJsonDataModel>
					<odin:gridColumnModel>
				    <odin:gridRowNumColumn></odin:gridRowNumColumn>
				  	<odin:gridEditColumn2 header="主键" dataIndex="id" editor="text" edited="false" width="100" hidden="true"/>
				  	<odin:gridEditColumn2 header="标识" dataIndex="type" editor="text" edited="false" width="100" hidden="true"/>
				  	<odin:gridEditColumn2 header="方案名称" align="center" dataIndex="filename" editor="text" width="100"/>
				  	<odin:gridEditColumn2 header="创建时间" align="center" dataIndex="creattime" editor="text" edited="false"  width="100" />
				  	<odin:gridEditColumn2 header="修改时间" align="center" dataIndex="updatetime" editor="text" edited="false" width="100"/>
				  	<odin:gridEditColumn2 header="是否通用标识" align="center" dataIndex="selty" editor="text" edited="true" width="100" hidden="true"/> 
<%-- 				  	<odin:gridEditColumn2  hidden="true" width="200" header="文件路径" dataIndex="realpath" editor="text" edited="false"  /> --%>
<%-- 				  	<odin:gridEditColumn2 hidden="true" width="200" header="标识" dataIndex="type" editor="text" edited="false"  /> --%>
				  	<odin:gridEditColumn2 width="65" header="操作" dataIndex="delet" editor="text" edited="false" renderer="deleteRowRenderer" isLast="true"/>
					</odin:gridColumnModel>
										
			</odin:grid>
			
			<%-- <table>
				<tr align="center"><!-- align="center" -->
					<td  ><odin:button text="上移" property="btn1" handler="up"></odin:button></td>
					<td ><odin:button text="下移"  property="btn2" handler="down"></odin:button></td>
					<td ><odin:button text="保存"  property="btn3" handler="Save"></odin:button></td>
				</tr>
            </table> --%>
			
</div> 

<script type="text/javascript">
 Ext.onReady(function(){
	var height=document.body.clientHeight;
	var width=document.body.clientWidth;
	Ext.getCmp("TrainingInfoGrid").setHeight(height);
	Ext.getCmp("TrainingInfoGrid").setWidth(width);
}); 

function rowDbClick(grid,rowIndex,event){/* 获取文件名称,回传至父页面 */
	var gridId = "TrainingInfoGrid";
	var grid = Ext.getCmp(gridId);
	var record = grid.getStore().getAt(rowIndex);   //获取当前行的数据
	var type = record.data.type;
	var id = record.data.id;
	var selty = record.data.selty;
	var typeid = type+","+id+","+selty;
	//window.opener.document.getElementById("filename").value = record.data.id; 
	window.opener.document.getElementById("filename").value = typeid; 
	window.close();
}



function deleteRowRenderer(value, params, record,rowIndex,colIndex,ds){
	var id = record.data.id;
	var filename = record.data.filename;
	var fileid = id+","+filename;
	/* if(realParent.buttonDisabled){
		return "删除";
	} */
	return "<a href=\"javascript:deleteRow2(&quot;"+id+"&quot;)\">删除</a>&nbsp&nbsp&nbsp&nbsp<a href=\"javascript:expword(&quot;"+fileid+"&quot;)\">下载</a>";
}
function deleteRow2(template){ 
	
		Ext.Msg.confirm("系统提示","是否确认删除？",function(ida) { 
			if("yes"==ida){
				radow.doEvent('deleteRow',template);
			}else{
				return;
			}		
		});	
	} 
 
function expword(fileid){
	Ext.Msg.confirm("系统提示","是否下载该模板",function(ida) { 
		if("yes"==ida){
			radow.doEvent("expword",fileid);
		}else{
			return;;
		}		
	});	 
}
 
function reloadTree(){
	setTimeout(xx,1000);
}
function xx(){
	var downfile = document.getElementById('downfile').value;
	/* w = window.open("ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile))); */
	window.location.href="ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile));
	//ShowCellCover("","温馨提示","导出成功！");
	setTimeout(cc,3000);
}
function cc(){
	
}
function reload(){
	odin.reset();
}
function Save(){
	radow.doEvent("Save");
}

function up(){
	//radow.doEvent("up");
    var grid = odin.ext.getCmp('TrainingInfoGrid');
	
	var sm = grid.getSelectionModel().getSelections();
	var store = grid.store;
	//alert(store.getCount());
	
	if (sm.length<=0){
		alert('请选中需要模板!')
		return;	
	}
	if(sm.length>1){
		alert('只能选中一个需要排序的模板!')
		return;
	}
	var selectdata = sm[0];  //选中行中的第一行
	var index = store.indexOf(selectdata);
	if (index==0){
		alert('该模板已经排在最顶上!')
		return;
	}
	
	store.remove(selectdata);  //移除
	store.insert(index-1, selectdata);  //插入到上一行前面
	
	grid.getSelectionModel().selectRow(index-1,true);  //选中上移动后的行	
	
	grid.getView().refresh();
}

function down(){
	//radow.doEvent("down");
var grid = odin.ext.getCmp('TrainingInfoGrid');
	
	var sm = grid.getSelectionModel().getSelections();
	var store = grid.store;
	if (sm.length<=0){
		alert('请选中需要排序的模板!')
		return;	
	}
	if(sm.length>1){
		alert('只能选中一个需要排序的模板!')
		return;
	}
	var selectdata = sm[0];  //选中行中的第一行
	var index = store.indexOf(selectdata);
	var total = store.getCount();
	if (index==(total-1) ){
		alert('该模板已经排在最底上!')
		return;
	}
	
	store.remove(selectdata);  //移除
	store.insert(index+1, selectdata);  //插入到上一行前面
	
	grid.getSelectionModel().selectRow(index+1,true);  //选中上移动后的行	
	grid.view.refresh();
}
</script>
<style>

</style>
