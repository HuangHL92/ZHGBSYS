<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"  %>
<%@ include file="/comOpenWinInit2.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<style>
.div-inline {
	float: left;
	margin-left: 15;
}
</style>
<odin:toolBar property="btnToolBar" >
	<odin:textForToolBar text="<h3></h3>" />
	<odin:fill></odin:fill>
	<odin:buttonForToolBar id="savebtn" handler="savebtnhand" text="保存"  icon="images/save.gif" cls="x-btn-text-icon"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar id="addbtn"  text="新增"  icon="images/save.gif" cls="x-btn-text-icon"/>
	<odin:buttonForToolBar id="deletebtn" text="删除"  icon="images/icon/click.gif" cls="x-btn-text-icon" isLast="true"/>
</odin:toolBar>
<div id="panel_content">
</div>
<odin:panel contentEl="panel_content" property="mypanel" topBarId="btnToolBar" ></odin:panel>
<!-- 点击当前Grid行号 -->
<odin:hidden property="id"/>
<odin:hidden property="cueRowIndex"/>
<odin:hidden property="is_select_para"/>
<div class="div-inline" style="height: 565;width: 270">
	<odin:groupBox title="校核函数名称" >
					<td >
						<odin:grid property="functionList"  height="565" rowClick="clickTable"  url="/">
							<odin:gridJsonDataModel root="data">
								<odin:gridDataCol name="id" />
								<odin:gridDataCol name="describe" />
								<odin:gridDataCol name="checkname" isLast="true" />
							</odin:gridJsonDataModel>
							<odin:gridColumnModel>
								<odin:gridRowNumColumn></odin:gridRowNumColumn>
								<odin:gridEditColumn header="主键" dataIndex="id" edited="false" editor="text" align="left" hidden="true" />
								<odin:gridEditColumn header="函数描述" dataIndex="describe" edited="false" editor="text" align="left" hidden="true" />
								<odin:gridEditColumn header="校核函数名称" dataIndex="checkname" edited="false" editor="text" align="left" isLast="true" />
							</odin:gridColumnModel>
						</odin:grid>
					</td>
	</odin:groupBox>
</div>
<div class="div-inline" style="height: 600;width: 68%">
	<odin:groupBox title="校核函数定义">
		<table style="width:100%" >
			<tr>
				<odin:textEdit property="checkname" label="校验函数名称" colspan="1"  maxlength="98" size="98"/>	 
			</tr>
			<tr>
				<odin:textarea property="describe" label="函数功能描述" colspan="4" rows="6" cols="99"/>
			</tr>
		</table>
	</odin:groupBox>
		<odin:groupBox title="函数参数字段录入">
			<table style="width:100%" >
				<tr>
					<odin:numberEdit property="num" label="参数序号（K）" colspan="2"  maxlength="34" size="34" minValue="1" onblur="onchangeNum(this)"/>	
					<odin:textEdit property="paradescribe" label="参数描述（L）" colspan="2"  maxlength="34" size="34"/>	 
				</tr>
				<tr>
					<odin:select property="type" label="参数类型" editor="false" colspan="2" codeType="TC01" maxlength="31" size="31"/>	 
					<td style="padding-left:89" colspan="2"><odin:checkbox property="is_select" label="参数为可选参数" /></td>	
				</tr>
			</table>
		</odin:groupBox>
		
		<odin:groupBox title="函数参数列表">
			<odin:toolBar property="toolBar2">
				<odin:fill></odin:fill>
				<odin:buttonForToolBar text="保存" id="savecond" handler="saveconhandler" icon="images/icon/save.gif" ></odin:buttonForToolBar>
<%-- 				<odin:buttonForToolBar text="新增" id="add" icon="images/icon/save.gif" ></odin:buttonForToolBar>
			<odin:buttonForToolBar text="上移" id="upRow" icon="images/icon/arrowup.gif" ></odin:buttonForToolBar>
				<odin:buttonForToolBar text="下移" id="downRow" icon="images/icon/arrowdown.gif"  ></odin:buttonForToolBar> --%>
				<odin:buttonForToolBar text="删除" handler="delRow"  icon="images/icon/delete.gif" isLast="true"></odin:buttonForToolBar>
			</odin:toolBar>
		
			<td colspan="1" style="height: 600;width: 300;">
					<odin:grid property="grid" autoFill="true" topBarId="toolBar2" rowClick="clickPara" height="270" sm="row" isFirstLoadData="false" bbarId="pageToolBar" url="/">
							<odin:gridJsonDataModel root="data">
									<odin:gridDataCol name="num" />
									<odin:gridDataCol name="paradescribe" />
									<odin:gridDataCol name="type" />
									<odin:gridDataCol name="is_select" isLast="true"/>
								</odin:gridJsonDataModel>
							<odin:gridColumnModel>
									<odin:gridRowNumColumn></odin:gridRowNumColumn>
									<odin:gridEditColumn dataIndex="num" header="参数序号" edited="false" align="center" width="35" editor="text"  />
									<odin:gridEditColumn dataIndex="paradescribe" header="参数描述"  edited="false"  align="center" width="60" editor="text"   sortable="false" />
									<odin:gridEditColumn dataIndex="type" header="参数类型"  edited="false"  align="center"  width="35" editor="text"  sortable="false"/>
									<odin:gridEditColumn dataIndex="is_select" header="可选否"  selectData="['0', '否'],['1', '是']"  edited="false" editor="select" align="center"  sortable="false" isLast="true"/>
							</odin:gridColumnModel>
						</odin:grid>
					</td>
	</odin:groupBox>
</div>

<script>
var j= jQuery.noConflict(); 
//删除一行参数
function delRow(a,b,c){
	var grid = odin.ext.getCmp('grid');
	var sm = grid.getSelectionModel();
	var selections = sm.getSelections();
	var store = grid.store;
	for(var i=0;i<selections.length;i++){
		var selected = selections[i];
		store.remove(selected);
	}	
	grid.view.refresh();
	alert("删除成功！");
//	radow.doEvent('delCount');
}

//点击函数名称显示值
function clickTable(grid,rowIndex,colIndex,event){
	var id=grid.store.getAt(rowIndex).get("id");
   	var describe=grid.store.getAt(rowIndex).get("describe");
   	var checkname=grid.store.getAt(rowIndex).get("checkname");
	j("#id").val(id);
	j("#describe").val(describe);
	j("#checkname").val(checkname);
	radow.doEvent('showFunction');
}


//函数保存校验
function savebtnhand(){
	var checkname=j("#checkname").val().trim();
	var describe=j("#describe").val().trim();
	if(checkname==""){
		alert("请填写校验函数名称！");
		return ;
	}
	if(describe==""){
		alert("请填写函数功能描述！");
		return ;
	}
	
	radow.doEvent('savebtnhand');
}

//参数保存校验
function saveconhandler(){
	var num=j("#num").val().trim();
	var type=j("#type").val().trim();
	if(!isNumber(num)){
		alert("参数序号必须为数字！");
		return ;
	}
	if(type==""){
		alert("请选择参数类型！");
		return ;
	}
	
	radow.doEvent('saveconhandler');
}
//是否为数字
function isNumber(val){
    var regPos = /^\d+(\.\d+)?$/; //非负浮点数
    var regNeg = /^(-(([0-9]+\.[0-9]*[1-9][0-9]*)|([0-9]*[1-9][0-9]*\.[0-9]+)|([0-9]*[1-9][0-9]*)))$/; //负浮点数
    if(regPos.test(val) || regNeg.test(val)){
        return true;
    }else{
        return false;
    }
}

//点击参数列表某一行，显示值到参数字段录入中
function clickPara(grid,rowIndex,colIndex,event){
	var num=grid.store.getAt(rowIndex).get("num");
   	var paradescribe=grid.store.getAt(rowIndex).get("paradescribe");
   	var type=grid.store.getAt(rowIndex).get("type");
   	var is_select=grid.store.getAt(rowIndex).get("is_select");
	j("#num").val(num);
	j("#paradescribe").val(paradescribe);
 	j("#type").val(type);
	j("#is_select_para").val(is_select); 
	radow.doEvent('setPara');
}

function onchangeNum(obj){
	if(!isNumber(parseInt(obj.value))){
		alert("参数序号必须为正整数！");
		return ;
	}
	if(obj.value.indexOf(".") != -1){
		alert("参数序号必须为正整数！");
		return ;
	}else{
		
	}
}
</script>