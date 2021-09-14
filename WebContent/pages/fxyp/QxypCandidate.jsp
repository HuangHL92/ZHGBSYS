<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.SysOrgTreePageModel"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>


<html class="ext-strict x-viewport">
<script src="<%=request.getContextPath()%>/pages/xbrm2/js/jquery-1.4.4.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<%@include file="/comOpenWinInit2.jsp" %>
<style>
.x-panel-bwrap {
	height: 100%
}

.x-panel-body {
	height: 100%
}
.busy{
	height: 450px;

}
.picOrg {
	background-image:
		url("<%=request.getContextPath()%>/pages/sysorg/org/images/companyOrgImg2.png")
		!important;
}

.picInnerOrg {
	background-image:
		url("<%=request.getContextPath()%>/pages/sysorg/org/images/insideOrgImg1.png")
		!important;
}

.picGroupOrg {
	background-image:
		url("<%=request.getContextPath()%>/pages/sysorg/org/images/groupOrgImg1.png")
		!important;
}
.x-grid3-scroller{
overflow-y: scroll;
}
</style>
<% 		String type=(String)request.getParameter("PersonType");
		
 %>
<script type="text/javascript" src="commform/basejs/json2.js"></script>
<odin:hidden property="tp00" />
<odin:hidden property="ynId" />
<odin:hidden property="PersonType" value="<%=type%>"/>
<odin:hidden property="yntype" />
<odin:hidden property="checkedgroupid" />
<odin:hidden property="cueRowIndex" />
<odin:hidden property="codevalueparameter" />
<odin:hidden property="sql" />
<odin:hidden property="mark" /> <!--  是否搜索刷新grid的标记 -->

<odin:hidden property="appointment" />
<odin:hidden property="selectByInputYnIdHidden" />

<odin:hidden property="selectType" />

<odin:hidden property="selectUnitId" />

<div id="groupTreeContent" style="height: 92%;width: 100%;">
	<table style="height: 95%;width: 100%;" >
		<tr style="width: 100%;" align="center">
			<td width="47%" height="100%">
				
				<div id="selectByPersonIdDiv" >
					<odin:editgrid property="gridcq" title="待选列表"  width="300" height="560" pageSize="9999"
						autoFill="false" >
						<odin:gridJsonDataModel>
							<odin:gridDataCol name="personcheck"  />
							<odin:gridDataCol name="a0000" />
							<odin:gridDataCol name="a0101" />
							<odin:gridDataCol name="a0104" />
							<odin:gridDataCol name="a0107" />
							<odin:gridDataCol name="a0221" />
							<odin:gridDataCol name="a0288" />
							<odin:gridDataCol name="a0192a" isLast="true"/>
						</odin:gridJsonDataModel>
						<odin:gridColumnModel>
							<odin:gridRowNumColumn></odin:gridRowNumColumn>
							<odin:gridEditColumn2 header="selectall" width="0"
								editor="checkbox" dataIndex="personcheck" edited="true" hidden="true"
								hideable="false" gridName="persongrid" />
							<odin:gridEditColumn header="主键" editor="text" hidden="true" edited="true" dataIndex="a0000" />
							<odin:gridColumn dataIndex="a0101" header="姓名" width="55" align="center" />
							<odin:gridEditColumn2 dataIndex="a0104" header="性别" width="40" align="center" editor="select" edited="false" codeType="GB2261" />
							<odin:gridColumn dataIndex="a0107" header="出生日期" width="65" align="center" />
							<odin:gridEditColumn2 dataIndex="a0221" header="现职务层次" width="80" align="center" editor="select" edited="false" codeType="ZB09" />
							<odin:gridColumn dataIndex="a0288" header="现职务层次时间"  width="65" align="center" />	
							<odin:gridColumn dataIndex="a0192a" edited="false" header="单位职务" width="130"  align="center" isLast="true" />
						</odin:gridColumnModel>
						<odin:gridJsonData>
							{
						        data:[]
						    }
						</odin:gridJsonData>
					</odin:editgrid>
				</div>

			</td>
			<td style="width: 6%;height: 100%;" align="center">
				<div id='rigthBtn'  style="display: none"></div>
				<br>
				<div id='rigthAllBtn' title="全选"></div>
				<br>
				<div id='liftBtn' style="display: none"></div>
				<br>
				<div id='liftAllBtn' title="全选"></div>
			</td>
			<td width="47%;" height="100%;" align="center">
				<div id="selectByPersonIdDiv2">
					<odin:editgrid property="selectName" title="输出列表" width="300" height="560" autoFill="false" >
						<odin:gridJsonDataModel>
								<odin:gridDataCol name="personcheck2"  />
								<odin:gridDataCol name="a0000" />
								<odin:gridDataCol name="a0101" />
								<odin:gridDataCol name="a0104" />
								<odin:gridDataCol name="a0107" />
								<odin:gridDataCol name="a0221" />
								<odin:gridDataCol name="a0288" />
								<odin:gridDataCol name="a0192a" isLast="true"/>
							</odin:gridJsonDataModel>
							<odin:gridColumnModel>
								<odin:gridRowNumColumn></odin:gridRowNumColumn>
								<odin:gridEditColumn2 header="selectall" width="0"
													  hidden="true"	editor="checkbox" dataIndex="personcheck2" edited="true"
									hideable="false" gridName="persongrid" />
								<odin:gridEditColumn header="主键" editor="text" hidden="true" edited="true" dataIndex="a0000" />
								<odin:gridColumn dataIndex="a0101" header="姓名" width="55" align="center" />
								<odin:gridEditColumn2 dataIndex="a0104" header="性别" width="40" align="center" editor="select" edited="false" codeType="GB2261" />
								<odin:gridColumn dataIndex="a0107" header="出生日期" width="65" align="center" />
								<odin:gridEditColumn2 dataIndex="a0221" header="现职务层次" width="80" align="center" editor="select" edited="false" codeType="ZB09" />
								<odin:gridColumn dataIndex="a0288" header="现职务层次时间"  width="65" align="center" />	
								<odin:gridColumn dataIndex="a0192a" edited="false" header="单位职务" width="130"  align="center" isLast="true" />
							</odin:gridColumnModel>
							<odin:gridJsonData>
								{
							        data:[]
							    }
							</odin:gridJsonData>
					</odin:editgrid>
				</div>

			</td>
		</tr>
	</table>
	
</div>
<div align="right" >
	<table>
		<tr>
			<div style="display:none">
	 			<!-- odin:select2  property="tplb"  label="从其它会议中提取（选择后点击确定即可）"  odin:select2  -->
	 		</div>
			<td><input type="button" height="20" width="40" value="&nbsp;确&nbsp;&nbsp;定&nbsp;" onclick="saveSelect()">&nbsp;&nbsp;</td>
		</tr>
	</table>
</div>

<script type="text/javascript">

function resetPiCiSelect(jsonArray){ 
	//selectByInputYnId
	var objSelect = $("#selectByInputYnId");
	$("option", $("#selectByInputYnId")).remove();
	objSelect.append("<option value=''>&nbsp;&nbsp;</option>");
	$(jsonArray).each(function() { 
		objSelect.append("<option value='" + this.code + "'>" + this.name + "</option>");
	});
	
}

function selectByInputYnIdClick(){
	$("#selectByYnIdDiv").css("display","none");
	$("#selectByYnIdDiv2").css("display","none");
	
	$("#selectByPersonIdDiv").css("display","block");  
	$("#selectByPersonIdDiv2").css("display","block"); 
	
	var val = $("#selectByInputYnId").val(); 
	val = val.replace(/\s*/g,""); 
	if (val == ""){
		$("input[name='rdoSwitch']").removeAttr("disabled");
		$("#buttonSearch").removeAttr("disabled"); 
	} else{
		$("#selectByInputYnIdHidden").val(val);
		radow.doEvent('queryFromData'); 
		$("input[name='rdoSwitch']").attr("disabled","true");
		$("#buttonSearch").attr("disabled","true");
	}
}

document.onkeydown=function() { 
	
	if (event.keyCode == 13) { 
		if (document.activeElement.type == "textarea") {
			toDOQuery();
			return false;
		}
	}else if(event.keyCode == 27){	//禁用ESC
	        return false;   
	}
}

/***
 *   保存人员信息
*
*/
function saveSelect(){  
/* 	var appointment = $("#appointment").val(); 
	if (appointment == "1") {
		$h.confirm("系统提示：",'确认正式任命后，人员信息会从中间库转入到正式库，是否继续？',200,function(id) {
			if (id == 'ok'){
				radow.doEvent('doAppointment');
			}
		});
	} else { 
	
	var o = doQuery();
	var a = JSON.stringify(o); */
		
/* 		radow.doEvent('saveSelect',a); */
	radow.doEvent('saveSelect');
}

function rigthBtnFun(){
	radow.doEvent('rigthBtn.onclick');
}
function rigthAllBtnFun(){
	radow.doEvent('rigthAllBtn.onclick');
}
function liftBtnFun(){
	radow.doEvent('liftBtn.onclick');
}
function liftAllBtnFun(){
	radow.doEvent('liftAllBtn.onclick');
}

Ext.onReady(function() {
	var tp00 = parent.document.getElementById("tp00").value;
// 	var tp00='001';
	document.getElementById("tp00").value = tp00;
	new Ext.Button({
		icon : 'images/icon/rightOne.png',
		id:'btn1',
	    cls :'inline pl',
	    renderTo:"rigthBtn",
	    handler:function(){
	    	rigthBtnFun();
	    }
	});
	new Ext.Button({
		icon : 'images/icon/rightAll.png',
		id:'btn2',
	    cls :'inline pl',
	    renderTo:"rigthAllBtn",
	    handler:function(){
	    	rigthAllBtnFun();
	    }
	});
	new Ext.Button({
		icon : 'images/icon/leftOne.png',
		id:'btn3',
	    cls :'inline pl',
	    renderTo:"liftBtn",
	    handler:function(){
	    	liftBtnFun();
	    }
	});
	new Ext.Button({
		icon : 'images/icon/leftAll.png',
		id:'btn4',
	    cls :'inline pl',
	    renderTo:"liftAllBtn",
	    handler:function(){
	    	liftAllBtnFun();
	    }
	});	
});


Ext.onReady(function() {
	var gridcq = Ext.getCmp("gridcq");
	var selectName = Ext.getCmp("selectName");
	var gStore = gridcq.getStore();
	var sStore = selectName.getStore();
    gStore.getModifiedRecords();
	gridcq.on("rowclick",function(o, index, o2){
		var rowData = gStore.getAt(index);
		var count = sStore.getCount();
		var flag = true;
		for(var i=0;i<count;i++){
			record = sStore.getAt(i);
			if(rowData.data.a0000==record.data.a0000){
				flag = false;
				break;
			}
		}
		if(flag){
			sStore.insert(sStore.getCount(),rowData);
		}
		gStore.remove(rowData);
		gridcq.view.refresh();
	});

	selectName.on("rowclick",function(o, index, o2){
		var rowData = sStore.getAt(index);
		var count = gStore.getCount();
		var flag = true;
		for(var i=0;i<count;i++){
			record = gStore.getAt(i);
			if(rowData.data.a0000==record.data.a0000){
				flag = false;
				break;
			}
		}
		if(flag){
			gStore.insert(gStore.getCount(),rowData);
		}

		sStore.remove(rowData);
		selectName.view.refresh();
	});

});

	
</script>



<script type="text/javascript">

</script>
</html>