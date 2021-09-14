<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"  %>

<odin:toolBar property="btnToolBar" >
	<odin:textForToolBar text="<h3></h3>" />
	<odin:fill></odin:fill>
	<odin:buttonForToolBar id="btnSaveContinue"  text="保存并新增"  icon="images/save.gif" cls="x-btn-text-icon"/>
	<odin:buttonForToolBar id="btnSave"  text="保存"  icon="images/save.gif" cls="x-btn-text-icon"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar id="btnReload" text="刷新"  icon="images/icon/click.gif" cls="x-btn-text-icon"/>
	<odin:buttonForToolBar id="btnClose" isLast="true" text="关闭"  icon="images/back.gif" cls="x-btn-text-icon"/>
</odin:toolBar>
<div id="panel_content">
<odin:hidden property="vsc001"/><!-- verify_scheme 校验方案主键 ,校验方案编码识别一组校验；（verify_scheme与verify_rule一对多）-->
</div>
<odin:panel contentEl="panel_content" property="mypanel" topBarId="btnToolBar" ></odin:panel>
<!-- 校验规则信息 -->
<odin:hidden property="vru001"/>	<!--校验项编码主键；-->
<odin:hidden property="vru006"/>	<!--校验类型-->
<odin:hidden property="vru007" value="0"/>	<!--有效标记默认：0；非空；     0-无效(未启用)，1-有效（已启用）-->
<odin:hidden property="vru008"/>	<!--返回信息（校验类别）：(表名)_(字段名)未通过 _（校验项名称）-->
<odin:hidden property="vru009"/>	<!--备用字段-->

<!-- 校验sql信息 -->
<odin:hidden property="vsl001"/><!-- 校验语句列表编码(校验语句编码+列表顺序码)；主键 -->
<odin:hidden property="vsl005"  /> <!--字段1数据类型" -->
<odin:hidden property="vsl010"  /> <!--字段2数据类型" -->
<odin:hidden property="vsl013"  /> <!--备用字段" -->

<!-- 点击当前Grid行号 -->
<odin:hidden property="cueRowIndex"/>

<odin:groupBox title="校验规则信息">
	<table style="width:100%" >
		<tr>
			<odin:textEdit property="vru002" label="校验项名称" colspan="4"  maxlength="45" size="75"/>	<!--校验项名称默认：(表名)_(字段名)（校验类型）；可修改-->
			<odin:select2 property="vru003" label="校验类别" codeType="VRU003" value="1" required="true" size="10" />	<!--默认为1；类别  1-错误，2-警告，3-提示-->
		</tr>
		
	</table>
</odin:groupBox>
	<odin:groupBox title="选择条件<label style='color:red'>（注：非相加计算类运算符：固定比对值、选择比对值、比对信息项只能任选其一）</label>">
		<table width="100%">
		    <tr>
				<odin:select2 property="vru004" label="校验信息集"    required="true" />	<!--表代码-->
				<odin:select2 property="vru005" label="校验信息项"  required="true"/>	<!--字段代码-->
				<%-- <odin:select property="vsl003" label="信息集1" codeType="VSL003" disabled="true" required="true" />
				<odin:select property="vsl004" label="信息项1" codeType="VSL004" disabled="true"  required="true" /> --%>
				<odin:hidden property="vsl003"/>
				<odin:hidden property="vsl004"/>
				
				<odin:select2 property="vsl006" label="运算符" codeType="1" size="25" required="true" colspan="4"/>
			</tr>
			<tr>
				<odin:textEdit property="vsl007" label="固定比对值" maxlength="10"/>
				<odin:select2 property="vsl013" label="选择比对值" colspan="6"/>
			</tr>
			<tr>
				<odin:select2 property="vsl008" label="比对信息集"  onchange="true"  />
				<odin:select2 property="vsl009" label="比对信息项"   />
			
				<td>
					<odin:radio property="vsl012" value="1" label="并且"  />
				</td>
				<td>
					<odin:radio property="vsl012" value="2" label="或者&nbsp;&nbsp;&nbsp;" />
				</td>
				<td>
					<odin:button text="增加" property="savecond"></odin:button>
				</td>
				<td>
					<odin:button text="修改" property="modefyCond"></odin:button>
				</td>
			</tr>
			
		</table>
	</odin:groupBox>
	<odin:toolBar property="toolBar2">
		<odin:fill></odin:fill>
		<odin:buttonForToolBar text="&nbsp;(+&nbsp;" id="addLeft"  ></odin:buttonForToolBar>
		<odin:buttonForToolBar text="&nbsp;+)&nbsp;" id="addRight"  ></odin:buttonForToolBar>
		<odin:separator></odin:separator>
		<odin:buttonForToolBar text="&nbsp;(-&nbsp;" id="delLeft"  ></odin:buttonForToolBar>
		<odin:buttonForToolBar text="&nbsp;-)&nbsp;" id="delRight"  ></odin:buttonForToolBar>
		<odin:separator></odin:separator>
		<odin:buttonForToolBar text="上移" id="upRow" icon="images/icon/arrowup.gif" ></odin:buttonForToolBar>
		<odin:buttonForToolBar text="下移" id="downRow" icon="images/icon/arrowdown.gif"  ></odin:buttonForToolBar>
		<odin:separator></odin:separator>
		<odin:buttonForToolBar text="删除" handler="delRow"  icon="images/icon/delete.gif" ></odin:buttonForToolBar>
		<odin:buttonForToolBar text="全删" id="delAll"  icon="images/icon/delete.gif"  isLast="true"></odin:buttonForToolBar>
	</odin:toolBar>
	<odin:gridSelectColJs name="vsl012" codeType="vsl012"  />
	<odin:gridSelectColJs name="vsl002" selectData="['0', ''],['1', '('],['2', '(('],['3', '((('],['4', '(((('],['5', '(((((']"></odin:gridSelectColJs>
	<odin:gridSelectColJs name="vsl011" selectData="['0', ''],['1', ')'],['2', '))'],['3', ')))'],['4', '))))'],['5', ')))))']"></odin:gridSelectColJs>
<odin:editgrid property="grid" autoFill="true" topBarId="toolBar2" height="270" sm="row" isFirstLoadData="false" bbarId="pageToolBar" url="/">
					<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
							<odin:gridDataCol name="vsl001" />
							<odin:gridDataCol name="vru001" />
							<odin:gridDataCol name="vsl002" />
							<odin:gridDataCol name="vsl003_name" />
							<odin:gridDataCol name="vsl004_name" />
							<odin:gridDataCol name="vsl003" />
							<odin:gridDataCol name="vsl004" />
							<odin:gridDataCol name="vsl005" />
							<odin:gridDataCol name="vsl006" />
							<odin:gridDataCol name="vsl007" />
							<odin:gridDataCol name="vsl013_name" />
							<odin:gridDataCol name="vsl013" />
							<odin:gridDataCol name="vsl008" />
							<odin:gridDataCol name="vsl009" />
							<odin:gridDataCol name="vsl008_name" />
							<odin:gridDataCol name="vsl009_name" />
							<odin:gridDataCol name="vsl010" />
							<odin:gridDataCol name="vsl011" />
							<odin:gridDataCol name="vsl012" isLast="true"/>
						</odin:gridJsonDataModel>
						<odin:gridColumnModel>
							<odin:gridRowNumColumn></odin:gridRowNumColumn>
							<odin:gridColumn dataIndex="vsl001" header="校验语句列表编码(校验语句编码+列表顺序码)；主键" hidden="true" align="center" sortable="false"/>
							<odin:gridColumn dataIndex="vru001" header="校验语句编码外键"  hidden="true"  align="center"/>
							<odin:gridEditColumn2 dataIndex="vsl002" header="左括号" edited="false" align="center" width="35" editor="select"  sortable="false" selectData="['0', ''],['1', '('],['2', '(('],['3', '((('],['4', '(((('],['5', '(((((']"/>
							<odin:gridColumn dataIndex="vsl003_name" header="校验信息集"  width="60" editor="text"  align="center"  sortable="false"/>
							<odin:gridColumn dataIndex="vsl004_name" header="校验信息项"  width="60"  editor="text"  align="center" sortable="false"/>
							<odin:gridColumn dataIndex="vsl003" header="校验信息集编码"  width="60"  hidden="true" editor="text"  align="center" sortable="false"/>
							<odin:gridColumn dataIndex="vsl004" header="校验信息项编码"  width="60"  hidden="true"  editor="text"  align="center" sortable="false"/>
							<odin:gridColumn dataIndex="vsl005" header="校验字段数据类型"  hidden="true"  align="center" sortable="false"/>
							<odin:gridEditColumn2 dataIndex="vsl006" header="操作符"  edited="false"  codeType="VSL006" editor="select" align="center" sortable="false"/>
							<odin:gridColumn dataIndex="vsl007" header="比对设定值" align="center" width="60"  sortable="false"/>
							<odin:gridColumn dataIndex="vsl013_name" header="比对选择值"  align="center" width="60"  sortable="false"/>
							<odin:gridColumn dataIndex="vsl013" header="比对选择编码" hidden="true"  align="center" width="60"  sortable="false"/>
							<odin:gridColumn dataIndex="vsl008_name" header="比对信息集"  width="60"   editor="text" align="center" sortable="false"/>
							<odin:gridColumn dataIndex="vsl009_name" header="比对信息项"  width="60"   editor="text" align="center" sortable="false"/>
							<odin:gridColumn dataIndex="vsl008" header="比对信息集编码"  width="60" hidden="true"  editor="text" align="center" sortable="false"/>
							<odin:gridColumn dataIndex="vsl009" header="比对信息项编码"  width="60" hidden="true"  editor="text" align="center" sortable="false"/>
							<odin:gridColumn dataIndex="vsl010" header="比对字段数据类型" hidden="true"   align="center" sortable="false"/>
							<odin:gridEditColumn2 dataIndex="vsl011" header="右括号"  edited="false"  align="center"  width="35" editor="select"  sortable="false" selectData="['0', ''],['1', ')'],['2', '))'],['3', ')))'],['4', '))))'],['5', ')))))']"/>
							<odin:gridEditColumn2 dataIndex="vsl012" header="连接符" codeType="VSL012" edited="false" editor="select" align="center"  sortable="false" isLast="true"/>

						</odin:gridColumnModel>
					</odin:editgrid>


<odin:window src="/blank.htm" id="win3" width="560" height="392" title="sql脚本查看">
</odin:window>

<script>
Ext.onReady(function() {
	document.getElementById('vru002SpanId').innerHTML='<font color="red">*</font>校验项名称';
});


function addEmpRow(){
	radow.addGridEmptyRow("grid",0);
}
function dataRender4del(value, params, rs, rowIndex, colIndex, ds){
	return "<a href=href='javascript:void(0)' onclick = del('"+rowIndex+"')>删除</a>";
}
function del(rowIndex){
	var grid = odin.ext.getCmp('grid');
	var store = grid.store;
	store.removeAt(rowIndex);
}

function delEmpRow1(){
	var grid = odin.ext.getCmp('grid');
	var arrayObj = new Array();;
	var store = grid.store;
	var i=store.getCount()-1;
	if(store.getCount() > 0){
		for( var i = store.getCount()-1 ;i>=0; i-- ){
			var ck = grid.getStore().getAt(i).get("logchecked");
	        if(ck == true){
	        	store.remove(grid.getStore().getAt(i));
			}
		}
	}
	grid.view.refresh();
}


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
	radow.doEvent('delCount');
}
</script>