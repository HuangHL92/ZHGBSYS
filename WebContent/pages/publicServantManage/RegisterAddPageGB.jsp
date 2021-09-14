<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%String ctxPath = request.getContextPath(); 
%>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<%@include file="/comOpenWinInit2.jsp" %>
<odin:toolBar property="toolBar8" applyTo="tol2">
	<odin:fill></odin:fill>
	<odin:buttonForToolBar text="新增" id="TrainingInfoAddBtn" handler="addnew" icon="images/add.gif"></odin:buttonForToolBar>
	<odin:buttonForToolBar text="保存" id="save1" handler="saveTrain" icon="images/save.gif" cls="x-btn-text-icon" isLast="true"></odin:buttonForToolBar>
</odin:toolBar>

<div id="tol2" align="left"></div>
<table  style="width: 100%">
	<tr>
		<odin:NewDateEditTag property="a2947" isCheck="true" label="进入公务员队伍时间" required="true" maxlength="8"></odin:NewDateEditTag>
		<odin:NewDateEditTag property="a2949" isCheck="true" label="公务员登记时间" required="true" maxlength="8"></odin:NewDateEditTag>
	</tr>
	
</table>
	<odin:grid property="RegisterInfoGrid" sm="row"  isFirstLoadData="false" url="/"
		 height="260"  >
		<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
			<odin:gridDataCol name="n2900" />
			<odin:gridDataCol name="a2947" />
	   		<odin:gridDataCol name="a2949"/>
	   		<odin:gridDataCol name="delete" isLast="true"/>
		</odin:gridJsonDataModel>
		<odin:gridColumnModel>
		  <odin:gridRowNumColumn />
		  <odin:gridColumn header="id" dataIndex="n2900" editor="text" hidden="true"/>
		  <odin:gridEditColumn2 header="进入公务员队伍时间" align="center" dataIndex="a2947" editor="text" edited="false" width="250"/>
		  <odin:gridEditColumn2 header="公务员登记时间" align="center" dataIndex="a2949" editor="text" edited="false" width="250"/>
		  <odin:gridEditColumn2 width="100" header="操作" dataIndex="delete" editor="text" edited="false" renderer="deleteRowRenderer" isLast="true" align="center"/>
		</odin:gridColumnModel>
	</odin:grid>
<odin:hidden property="a0000" title="人员主键"/>
<odin:hidden property="n2900" title="登记主键"/>
<script type="text/javascript">

Ext.onReady(function(){
	document.getElementById("a0000").value = realParent.document.getElementById("a0000").value;
	
	
	var firstload = true;
	var pgrid = Ext.getCmp("RegisterInfoGrid");
	var dstore = pgrid.getStore();
	dstore.on({  
       load:{  
           fn:function(){  
           	 if(firstload){
           		  $h.selectGridRow('RegisterInfoGrid',0);
           		  firstload = false;
             }
           }      
       },  
       scope:this      
   });  
	
	
});
function saveTrain(){
	radow.doEvent('save.onclick');
}
function deleteRowRenderer(value, params, record,rowIndex,colIndex,ds){
	var n2900 = record.data.n2900;
	if(realParent.buttonDisabled){
		return "删除";
	}
	return "<a href=\"javascript:deleteRow2(&quot;"+n2900+"&quot;)\">删除</a>";
}
function deleteRow2(n2900){ 
	Ext.Msg.confirm("系统提示","是否确认删除？",function(id) { 
		if("yes"==id){
			radow.doEvent('deleteRow',n2900);
		}else{
			return;
		}		
	});	
}

function addnew(){
	$('#n2900').val('');
	$('#a2947').val('');
	$('#a2947_1').val('');
	$('#a2949').val('');
	$('#a2949_1').val('');
}

function lockINFO(){
	Ext.getCmp("TrainingInfoAddBtn").disable(); 
	Ext.getCmp("save1").disable(); 
	Ext.getCmp("RegisterInfoGrid").getColumnModel().setHidden(4,true);
}

</script>



