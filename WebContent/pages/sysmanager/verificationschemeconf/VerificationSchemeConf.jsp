<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"  %>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<div id="main">
<odin:hidden property="username"/>
<odin:hidden property="dbClickVsc001"/><!-- 双击选中的校验方案主键 -->
<odin:hidden property="isdefault"/><!--双击选中的方案是否默认方案  -->
<!--<div id="panel_content" >
		<odin:hidden property="nothing"/>
		<odin:toolBar property="btnToolBar" applyTo="panel_content">
		<odin:fill></odin:fill>
		<odin:buttonForToolBar text="查询" id="query" icon="images/search.gif"  tooltip="查询信息校验方案" />
		<odin:separator></odin:separator>
		<odin:buttonForToolBar text="校验参数配置" id="paraSet" icon="image/u53.png" tooltip="配置信息校验项可以使用的运算符参数" />
		<odin:separator></odin:separator>
		<odin:buttonForToolBar text="校核函数定义" id="checkFunction" icon="image/u53.gif"  tooltip="自定义校核函数" handler="checkF"/>
		<odin:buttonForToolBar text="校核表达式定义" id="checkExpression" icon="images/search.gif"  tooltip="查询信息校验方案" handler="checkE" />
		<odin:buttonForToolBar text="刷新页面" id="btnReload"  isLast="true" icon="images/icon/click.gif"/>
		</odin:toolBar>
</div>-->
<div id="groupTreeContent" style="height: 100%;padding-top: 0px;">
	<table >
		<tr>
			 <td  id="table_id1">
			<odin:toolBar property="toolBar1">
			<odin:textForToolBar text="<h1 style=color:rgb(21,66,139);size:11px >&nbsp;校验方案信息</h1>"></odin:textForToolBar>
			<odin:fill></odin:fill>
			<odin:buttonForToolBar text="新增" id="addScheme" icon="images/add.gif" tooltip="新增信息校验方案" />
			<odin:buttonForToolBar text="导出" id="expScheme" icon="images/icon/exp.png" tooltip="导出信息校验方案" handler="expS"/>
			<odin:buttonForToolBar text="导入" id="impScheme" handler="impScheme" icon="images/icon/exp.png"  tooltip="导入信息校验方案" isLast="true"></odin:buttonForToolBar>
		</odin:toolBar>	
		<odin:editgrid property="VeriySchemeGrid" title=""   bbarId="pageToolBar" topBarId="toolBar1"  pageSize="10" isFirstLoadData="false" url="/"><!-- bbarId="pageToolBar" -->
		<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
			<odin:gridDataCol name="vsc001" />
			<odin:gridDataCol name="vsc002" />
			<odin:gridDataCol name="vsc003" />
			<odin:gridDataCol name="vsc004" />
			<odin:gridDataCol name="vsc005" />
			<odin:gridDataCol name="vsc006" />
			<odin:gridDataCol name="vsc007" />
			<odin:gridDataCol name="vsc008" />
			<odin:gridDataCol name="vsc009" />
			<odin:gridDataCol name="vsc010" />
			<odin:gridDataCol name="vsc011"  isLast="true"/>
		</odin:gridJsonDataModel>
		<odin:gridColumnModel>
			<odin:gridRowNumColumn></odin:gridRowNumColumn>
			<odin:gridEditColumn header="校验方案编码" width="100" dataIndex="vsc001" edited="false" editor="text" align="center"  hidden="true" />
			<odin:gridEditColumn header="校验方案名称" width="120" dataIndex="vsc002" edited="false" editor="text" align="center" />
			<odin:gridEditColumn header="创建人员" width="130" dataIndex="vsc005" edited="false" editor="text" align="center" hidden="true"/>
			<odin:gridEditColumn header="创建人所属用户组" width="130" dataIndex="vsc004" edited="false" editor="text" align="center" hidden="true"/>
			<odin:gridEditColumn header="创建时间" width="130" dataIndex="vsc006" edited="false" editor="text" align="center" hidden="true"/>
			<odin:gridEditColumn2 header="默认基础方案" width="80" dataIndex="vsc007" edited="false" editor="select" codeType="VSC007" align="center" hidden="true"/>
			<odin:gridEditColumn2 header="导入标记" width="60" dataIndex="vsc008" edited="false" editor="select" align="center" selectData="['0', '否'],['1', '是']" hidden="true"/>
			<odin:gridEditColumn2 header="状态" width="60" dataIndex="vsc003" edited="false"  editor="select" align="center"  selectData="['0', '待启用'],['1', '已启用']" renderer="runorstop"/>
			<odin:gridEditColumn header="备注" width="100" dataIndex="vsc009" edited="false" editor="text" align="center" />
			<odin:gridEditColumn header="导出人员Id" width="50" dataIndex="vsc010" edited="false" editor="text" align="center" hidden="true"/>
			<odin:gridEditColumn header="导出人员所在单位编码" width="50" dataIndex="vsc011" edited="false" editor="text" align="center" hidden="true"  />
			<odin:gridEditColumn edited="false" dataIndex="updatedelete" header="操作" editor="text" isLast="true" renderer="uod" width="100" align="center"/>
		</odin:gridColumnModel>
	</odin:editgrid>
			</td>
			<td  id="table_id2">
		<odin:toolBar property="toolBar2">
		<odin:textForToolBar text="<h1 id=&quot;abc&quot; style=&quot;color:rgb(21,66,139);size:11px&quot; >校验方案信息</h1>"></odin:textForToolBar>
		<odin:fill></odin:fill>
		<odin:buttonForToolBar text="新增" id="addrule" handler="addRule" icon="images/add.gif" tooltip="新增当前方案的规则"/>
		<odin:buttonForToolBar text="全部启用" id="runAllRule" handler="runAllRule" icon="images/icon/right.gif" tooltip="启用当前所有规则"></odin:buttonForToolBar>
		<odin:buttonForToolBar text="复制到...方案" id="copyRow" handler="copyRow" icon="images/icon/exp.png" tooltip="复制列表中选中规则到其他方案"></odin:buttonForToolBar>
		<odin:separator></odin:separator>
		<odin:buttonForToolBar text="校核函数定义" id="checkFunction" icon="image/u53.png" tooltip="自定义校核函数"  handler="checkF"  isLast="true"/>
		</odin:toolBar>
	<odin:editgrid property="VerifyRuleGrid"  topBarId="toolBar2" bbarId="pageToolBar"  isFirstLoadData="false" url="/">
	<odin:gridJsonDataModel   root="data" >
			<odin:gridDataCol name="vsc001" />
			<odin:gridDataCol name="vsc002" />
			<odin:gridDataCol name="vru001" />
			<odin:gridDataCol name="vru002" />
			<odin:gridDataCol name="vru003" />
			<odin:gridDataCol name="vru004_name" />
			<odin:gridDataCol name="vru005_name" />
			<odin:gridDataCol name="vru006" />
			<odin:gridDataCol name="vru007" />
			<odin:gridDataCol name="vru008" />
			<odin:gridDataCol name="vru009"  isLast="true"/>
		</odin:gridJsonDataModel>
		<odin:gridColumnModel>
			<odin:gridRowNumColumn></odin:gridRowNumColumn>
			<odin:gridEditColumn header="校验方案编码" width="35" dataIndex="vsc001"	edited="false" editor="text" align="center"  hidden="true"  />
			<odin:gridEditColumn header="校验方案" width="150" dataIndex="vsc002"	edited="false" editor="text" align="center"  hidden="true"  />
			<odin:gridEditColumn header="校验规则编码" width="50" dataIndex="vru001"	edited="false" editor="text" align="center"  hidden="true" />
			<odin:gridEditColumn header="校验规则名称" width="180" dataIndex="vru002"	edited="false" editor="text" align="left"   />
			<odin:gridEditColumn2 header="类别" width="100" dataIndex="vru003"	edited="false" editor="select" align="center" codeType="VRU003"  />
			<odin:gridEditColumn header="主校验信息集" width="150" dataIndex="vru004_name"	edited="false" editor="text" align="center"   />
			<odin:gridEditColumn header="主校验信息项" width="150" dataIndex="vru005_name"	edited="false" editor="text" align="center"    />
			<odin:gridEditColumn header="校验类型" width="100" dataIndex="vru006"	edited="false" editor="text" align="center"   hidden="true"  />
			<odin:gridEditColumn2 header="状态" width="60" dataIndex="vru007"	edited="false" editor="select" align="center"  selectData="['0', '待启用'],['1', '已启用']" renderer="rosRule" />
			<odin:gridEditColumn2 header="操作" width="100" dataIndex="updatedelete" edited="false" editor="text" renderer="uodRule" align="center"/>
			<odin:gridEditColumn header="返回信息" width="180" dataIndex="vru008"	edited="false" editor="text" align="center"  hidden="true" />
			<odin:gridEditColumn header="拼接好的SQL语句" width="50" dataIndex="vru009"	edited="false" editor="text" align="center"  hidden="true"   isLast="true" />
		</odin:gridColumnModel>
		</odin:editgrid>
			</td>
		</tr>
	</table>
</div>
<%-- <odin:tab id="tab" width="100%"> --%>
<%-- <odin:tabModel>
<odin:tabItem title="信息校验方案" id="tab1"></odin:tabItem>
<odin:tabItem title="信息校验规则" id="tab2" isLast="true"></odin:tabItem>
</odin:tabModel>
---------------------------------------信息校验方案 tab1--------------------------------------------------------
<odin:tabCont itemIndex="tab1">	
	
	<odin:toolBar property="btnToolBar">
		<odin:fill></odin:fill>
		<odin:buttonForToolBar text="查询" id="query" icon="images/search.gif"  tooltip="查询信息校验方案" />
		<odin:separator></odin:separator>
		<odin:buttonForToolBar text="校验参数配置" id="paraSet" icon="image/u53.png" tooltip="配置信息校验项可以使用的运算符参数" />
		<odin:separator></odin:separator>
		<odin:buttonForToolBar text="刷新页面" id="btnReload"  isLast="true" icon="images/icon/click.gif"/>
	</odin:toolBar>
	

	
	
	<div id="panel_content" >
		<odin:hidden property="nothing"/>
	</div>
	<odin:toolBar property="toolBar1">
		<odin:textForToolBar text="<h1 style=color:rgb(21,66,139);size:11px >&nbsp;校验方案信息</h1>"></odin:textForToolBar>
		<odin:fill></odin:fill>
		<odin:fill></odin:fill>
		<odin:buttonForToolBar text="查询" id="query" icon="images/search.gif"  tooltip="查询信息校验方案" />
		<odin:separator></odin:separator>
		<odin:buttonForToolBar text="校验参数配置" id="paraSet" icon="image/u53.png" tooltip="配置信息校验项可以使用的运算符参数" />
		<odin:separator></odin:separator>
		<odin:buttonForToolBar text="新增" id="addScheme" icon="images/add.gif" tooltip="新增信息校验方案" />
		<odin:buttonForToolBar text="修改" id="updateScheme" icon="image/u53.png" tooltip="修改信息校验方案"/>
		<odin:buttonForToolBar text="删除" id="delScheme" icon="images/icon/delete.gif" tooltip="选中的信息校验方案删除" />
		<odin:buttonForToolBar text="保存" id="saveUpdateScheme" icon="images/save.gif" tooltip="选中的信息校验方案修改名称、备注、基础默认方案标记"/>
		<odin:separator></odin:separator>
		<odin:buttonForToolBar text="启用" id="run"  tooltip="选中的信息校验方案启用，启用后则方案可选用" icon="images/icon/right.gif"/>
		<odin:buttonForToolBar text="停用" id="stop" tooltip="选中的信息校验方案停用，停用后则方案不可选用" icon="images/back.gif"/>
		<odin:separator></odin:separator>
		<odin:buttonForToolBar text="导出" id="expScheme" icon="images/icon/exp.png" tooltip="导出信息校验方案"/>
		<odin:buttonForToolBar text="导入" id="impScheme" handler="impScheme" icon="images/icon/exp.png"  tooltip="导入信息校验方案" ></odin:buttonForToolBar>
		<odin:buttonForToolBar text="刷新页面" id="btnReload"  isLast="true" icon="images/icon/click.gif"/>
	</odin:toolBar>		
	<odin:gridSelectColJs2 name="vsc007" codeType="VSC007"></odin:gridSelectColJs2>
	<odin:panel contentEl="panel_content" property="mypanel1" topBarId="btnToolBar" ></odin:panel>
	<odin:editgrid property="VeriySchemeGrid" title=""   bbarId="pageToolBar" topBarId="toolBar1"  pageSize="10" isFirstLoadData="false" url="/"><!-- bbarId="pageToolBar" -->
	<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
			<odin:gridDataCol name="mcheck" />
			<odin:gridDataCol name="vsc001" />
			<odin:gridDataCol name="vsc002" />
			<odin:gridDataCol name="vsc003" />
			<odin:gridDataCol name="vsc004" />
			<odin:gridDataCol name="vsc005" />
			<odin:gridDataCol name="vsc006" />
			<odin:gridDataCol name="vsc007" />
			<odin:gridDataCol name="vsc008" />
			<odin:gridDataCol name="vsc009" />
			<odin:gridDataCol name="vsc010" />
			<odin:gridDataCol name="vsc011"  isLast="true"/>
		</odin:gridJsonDataModel>
		<odin:gridColumnModel>
			<odin:gridRowNumColumn></odin:gridRowNumColumn>
			<odin:gridColumn header="selectall" gridName="VeriySchemeGrid" align="center" width="50"
				editor="checkbox" edited="true" dataIndex="mcheck" />
			<odin:gridColumn dataIndex="vsc001" header="新增规则" width="60" align="center" renderer="setter" sortable="false" hidden="true"/>
			<odin:gridColumn dataIndex="vsc001" header="修改" width="60" align="center" hidden="true" renderer="openEditer"/>
			<odin:gridEditColumn header="校验方案编码" width="100" dataIndex="vsc001" edited="false" editor="text" align="center"  hidden="true" />
			<odin:gridEditColumn header="校验方案名称" width="150" dataIndex="vsc002" edited="false" editor="text" align="center" />
			<odin:gridEditColumn header="创建人员" width="130" dataIndex="vsc005" edited="false" editor="text" align="center" />
			<odin:gridEditColumn header="创建人所属用户组" width="130" dataIndex="vsc004" edited="false" editor="text" align="center" />
			<odin:gridEditColumn header="创建时间" width="130" dataIndex="vsc006" edited="false" editor="text" align="center" />
			<odin:gridEditColumn2 header="默认基础方案" width="80" dataIndex="vsc007" edited="false" editor="select" codeType="VSC007" align="center" hidden="false"/>
			<odin:gridEditColumn2 header="导入标记" width="60" dataIndex="vsc008" edited="false" editor="select" align="center" selectData="['0', '否'],['1', '是']" hidden="true"/>
			<odin:gridEditColumn2 header="状态" width="60" dataIndex="vsc003" edited="false"  editor="select" align="center"  selectData="['0', '待启用'],['1', '已启用']" renderer="runorstop"/>
			<odin:gridEditColumn header="备注" width="220" dataIndex="vsc009" edited="false" editor="text" align="center" />
			<odin:gridEditColumn header="导出人员Id" width="50" dataIndex="vsc010" edited="false" editor="text" align="center" hidden="true"/>
			<odin:gridEditColumn header="导出人员所在单位编码" width="50" dataIndex="vsc011" edited="false" editor="text" align="center" hidden="true"  isLast="true" />
		</odin:gridColumnModel>
	</odin:editgrid>
</odin:tabCont>	

<!-- -------------------------------------------信息校验规则 tab2 --------------------------------------------------- -->
<odin:tabCont itemIndex="tab2">
	<odin:toolBar property="toolBar2">
		<odin:textForToolBar text="<h1 style=color:rgb(21,66,139);size:11px >&nbsp;</h1>"></odin:textForToolBar>
		<odin:fill></odin:fill>
		<odin:buttonForToolBar text="复制" id="copyRow" handler="copyRow" icon="images/icon/exp.png" tooltip="新增当前方案的规则"></odin:buttonForToolBar>
		<odin:buttonForToolBar text="新增" id="addrule" handler="addRule" icon="images/add.gif" tooltip="复制列表中选中规则到其他方案"></odin:buttonForToolBar>
		<odin:buttonForToolBar text="启用" id="runRule"  tooltip="启用选中的校验规则" icon="images/icon/right.gif" handler="runRow"/>
		<odin:buttonForToolBar text="停用" id="stopRule" tooltip="停用选中的校验规则" icon="images/back.gif" handler="stopRow"/>
		<odin:buttonForToolBar text="删除" id="delRow" handler="delRow" icon="images/icon/delete.gif" tooltip="删除列表中选中的校验规则" ></odin:buttonForToolBar>
		<odin:buttonForToolBar text="全删" id="delAll" icon="images/icon/delete.gif"  isLast="true"  tooltip="删除列表中全部的校验规则"></odin:buttonForToolBar>
	</odin:toolBar>
	<odin:editgrid property="VerifyRuleGrid" width="100%"   topBarId="toolBar2" bbarId="pageToolBar"  isFirstLoadData="false" url="/">
	<odin:gridJsonDataModel   root="data" >
			<odin:gridDataCol name="vsc001" />
			<odin:gridDataCol name="vsc002" />
			<odin:gridDataCol name="vru001" />
			<odin:gridDataCol name="vru002" />
			<odin:gridDataCol name="vru003" />
			<odin:gridDataCol name="vru004" />
			<odin:gridDataCol name="vru005" />
			<odin:gridDataCol name="vru006" />
			<odin:gridDataCol name="vru007" />
			<odin:gridDataCol name="vru008" />
			<odin:gridDataCol name="vru009"  isLast="true"/>
		</odin:gridJsonDataModel>
		<odin:gridColumnModel>
			<odin:gridRowNumColumn></odin:gridRowNumColumn>
	
			<odin:gridEditColumn header="校验方案编码" width="35" dataIndex="vsc001"	edited="false" editor="text" align="center"  hidden="true"  />
			<odin:gridEditColumn header="校验方案" width="150" dataIndex="vsc002"	edited="false" editor="text" align="center"  hidden="false"  />
			<odin:gridEditColumn header="校验规则编码" width="50" dataIndex="vru001"	edited="false" editor="text" align="center"  hidden="true" />
			<odin:gridEditColumn header="校验规则名称" width="380" dataIndex="vru002"	edited="false" editor="text" align="left"   />
			<odin:gridEditColumn2 header="类别" width="100" dataIndex="vru003"	edited="false" editor="select" align="center" codeType="VRU003"  />
			<odin:gridEditColumn header="主校验信息集" width="150" dataIndex="vru004"	edited="false" editor="text" align="center"   />
			<odin:gridEditColumn header="主校验信息项" width="200" dataIndex="vru005"	edited="false" editor="text" align="center"    />
			<odin:gridEditColumn header="校验类型" width="100" dataIndex="vru006"	edited="false" editor="text" align="center"   hidden="true"  />
			<odin:gridEditColumn2 header="启用标记" width="80" dataIndex="vru007"	edited="false" editor="select" align="center"  selectData="['0', '待启用'],['1', '已启用']"  />
			<odin:gridEditColumn header="返回信息" width="180" dataIndex="vru008"	edited="false" editor="text" align="center"  hidden="true" />
			<odin:gridEditColumn header="拼接好的SQL语句" width="50" dataIndex="vru009"	edited="false" editor="text" align="center"  hidden="true"   isLast="true" />
		</odin:gridColumnModel>
	</odin:editgrid>
</odin:tabCont>		
</odin:tab>	 --%>	
</div>
<odin:window src="/blank.htm" id="paraSetWin" width="580" height="500" maximizable="false" title="信息校验项运算符配置界面" closable="true" modal="true" />
<odin:window src="/blank.htm" id="addSchemeWin" width="605" height="245" maximizable="false" title="新增修改校验方案信息" closable="true" modal="true" />
<odin:window src="/blank.htm" id="addwin" width="780" height="500" maximizable="false" title="新增修改校验规则信息"  closable="true" modal="true" />
<odin:window src="/blank.htm" id="impExcelWin" width="450" height="150" title="方案导入窗口"  closable="true" modal="true" />
<odin:window src="/blank.htm" id="expExcelWin" width="560" height="150" title="方案导出窗口"  closable="true" modal="true" />
<odin:window src="/blank.htm" id="copyWin" width="350" height="200" title="复制校验规则窗口" closable="true" modal="true"/>

<odin:window src="/blank.htm" id="checkFunctionWin" width="780" height="500" maximizable="false" title="校核函数"  closable="true" modal="true" />
<odin:window src="/blank.htm" id="checkExpressionWin" width="605" height="245" maximizable="false" title="校核表达式" closable="true" modal="true" />
<script>
/* function openEditer(value, params, record,rowIndex,colIndex,ds){
	if(value){
		return "<img src='"+contextPath+"/images/update.gif' title='' style='cursor:pointer' onclick=\"radow.doEvent('updateScheme','"+value+"');\">";
	}else{
		return null;
	}
} */
/* function setter(value, params, record,rowIndex,colIndex,ds){
	if(value){
		return "<img src='"+contextPath+"/images/article.gif' title=''  style='cursor:pointer' onclick=\"radow.doEvent('addMyscript','"+value+"');\">";
	}else{
		return null;
	}
} */
function changeh1(text){
	document.getElementById("abc").innerHTML="当前校验方案:"+text;
	
	
}
function changetext(){
	var objs=document.getElementById("abc").innerHTML="校验方案信息";
}
function addRule(){
	radow.doEvent("addMyscript")
}
function runorstop(value, params, record,rowIndex,colIndex,ds){
	if(value==0){
		return "<img  src='"+contextPath+"/images/wrong.gif' title=''  style='cursor:pointer' onclick=runScheme(this,'"+value+"','"+rowIndex+"')>";
	}else{
		return "<img  src='"+contextPath+"/images/right1.gif' title=''  style='cursor:pointer' onclick=runScheme(this,'"+value+"','"+rowIndex+"')>";
	}
}
var img;
var img2;
function runScheme(obj,v,rowIndex){
	img=obj;
	radow.doEvent("runOrStopScheme",v+','+rowIndex);

}
function changePic(v){
	if(v==0){
		img.src=contextPath+"/images/right1.gif";
	}else{
		img.src=contextPath+"/images/wrong.gif";
	}
}

function uod(value, params, record,rowIndex,colIndex,ds){
	var username=document.getElementById("username").value;
	if(record.data.vsc007==0||username=='admin'){
		return "<a href=\"javascript:updateSchemeRow('"+rowIndex+"')\">修改</a>&nbsp&nbsp<a href=\"javascript:deleteSchemeRow('"+rowIndex+"')\">删除</a>";
	}else{
		return "<a >——</a>&nbsp&nbsp<a>——</a>";
	}
}

function rosRule(value, params, record,rowIndex,colIndex,ds){
	if(value==0){
		return "<img  src='"+contextPath+"/images/wrong.gif' title=''  style='cursor:pointer' onclick=runRule(this,'"+value+"','"+rowIndex+"')>";
	}else{
		return "<img  src='"+contextPath+"/images/right1.gif' title=''  style='cursor:pointer' onclick=runRule(this,'"+value+"','"+rowIndex+"')>";
	}
}

function runRule(obj,v,rowIndex){
	img2=obj;
	radow.doEvent("runOrStopRule",v+','+rowIndex);
}

function uodRule(value, params, record,rowIndex,colIndex,ds){
	var username=document.getElementById("username").value;
	var isdef=document.getElementById("isdefault").value;
	if(isdef=='1'&&username=='admin'){
		return "<a href=\"javascript:updateRuleRow('"+rowIndex+"')\">修改</a>&nbsp&nbsp<a href=\"javascript:deleteRuleRow('"+rowIndex+"')\">删除</a>";
	}else if(isdef=='0'){
		return "<a href=\"javascript:updateRuleRow('"+rowIndex+"')\">修改</a>&nbsp&nbsp<a href=\"javascript:deleteRuleRow('"+rowIndex+"')\">删除</a>";
	}else{
		return "<a >——</a>&nbsp&nbsp<a>——</a>";
	}
	
}
function updateRuleRow(index){
	radow.doEvent("updateRule",index);
}
function deleteRuleRow(index){
	radow.doEvent("delRowBefore",index);
}
function updateSchemeRow(index){
	radow.doEvent("updateScheme.onclick",index);
}
function deleteSchemeRow(index){
	radow.doEvent("delScheme.onclick",index);
}
function grantTabChange(tabObj,item){
	if(item.getId()=='tab2'){
		odin.ext.getCmp('groupgrid').view.refresh(true);
	}
}

function delRow(a,b,c){
	var grid = odin.ext.getCmp('VerifyRuleGrid');
	var sm = grid.getSelectionModel();
	var selections = sm.getSelections();
	var store = grid.store;
	var vru001s = '';
	for(var i=0;i<selections.length;i++){
		var selected = selections[i];
		//store.remove(selected);
		vru001s = vru001s +selections[i].data.vru001+",";
	}
	//alert(selections[0].data.vru001);
	//grid.view.refresh();
	radow.doEvent('delRowBefore',vru001s);
}

function copyRow(a,b,c){
	var grid = odin.ext.getCmp('VerifyRuleGrid');
	var sm = grid.getSelectionModel();
	var selections = sm.getSelections();
	var store = grid.store;
	var vru001s = '';
	for(var i=0;i<selections.length;i++){
		var selected = selections[i];
		//store.remove(selected);
		vru001s = vru001s +selections[i].data.vru001+",";
	}
	//alert(selections[0].data.vru001);
	//grid.view.refresh();
	radow.doEvent('openCopyWin',vru001s);
}
function runRow(a,b,c){
	var grid = odin.ext.getCmp('VerifyRuleGrid');
	var sm = grid.getSelectionModel();
	var selections = sm.getSelections();
	var store = grid.store;
	var vru001s = '';
	for(var i=0;i<selections.length;i++){
		var selected = selections[i];
		//store.remove(selected);
		vru001s = vru001s +selections[i].data.vru001+",";
	}
	//alert(selections[0].data.vru001);
	//grid.view.refresh();
	radow.doEvent('runRule',vru001s);
}

function stopRow(a,b,c){
	var grid = odin.ext.getCmp('VerifyRuleGrid');
	var sm = grid.getSelectionModel();
	var selections = sm.getSelections();
	var store = grid.store;
	var vru001s = '';
	for(var i=0;i<selections.length;i++){
		var selected = selections[i];
		//store.remove(selected);
		vru001s = vru001s +selections[i].data.vru001+",";
	}
	//alert(selections[0].data.vru001);
	//grid.view.refresh();
	radow.doEvent('stopRule',vru001s);
}

function expS(){
	var grid = odin.ext.getCmp('VeriySchemeGrid');
	var sm = grid.getSelectionModel();
	var selections = sm.getSelections();
	var store = grid.store;
	var vsc001s = '';
	for(var i=0;i<selections.length;i++){
		var selected = selections[i];
		//store.remove(selected);
		vsc001s = vsc001s +selections[i].data.vsc001+",";
	}
	//alert(selections[0].data.vru001);
	//grid.view.refresh();
	radow.doEvent('expScheme',vsc001s);
}

var vsc001Exp = '';
var fileName = '';
function expScheme(vsc001,fName){
	vsc001Exp = vsc001;
	fileName = fName;
	odin.showWindowWithSrc('expExcelWin',contextPath+"/pages/sysmanager/verificationschemeconf/ExcelExpWindow.jsp");
}


function impScheme(){
	 odin.showWindowWithSrc("impExcelWin",contextPath+"/pages/sysmanager/verificationschemeconf/ExcelImpWindow.jsp"); 
	 
}

/* function getShowTow(){
	odin.ext.getCmp('tab').activate('tab2');
} */
Ext.onReady(function() {
	//页面调整
	 //Ext.getCmp('VeriySchemeGrid').setHeight(Ext.getBody().getViewSize().height-objTop(document.getElementById('forView_VeriySchemeGrid'))[0]-4)
	//alert(Ext.getBody().getViewSize().height);
	//alert(Ext.getBody().getViewSize().width);
	Ext.getCmp('VeriySchemeGrid').setHeight(505);
	Ext.getCmp('VeriySchemeGrid').setWidth(425);
	//Ext.getCmp('VeriySchemeGrid').setWidth((Ext.isIE?Ext.getBody().getViewSize().width:window.innerWidth)-objTop(document.getElementById('forView_VeriySchemeGrid'))[1]-2); 
	 //Ext.getCmp('mypanel1').setWidth(document.body.clientWidth);
	 //Ext.getCmp('toolBar1').setWidth(document.body.clientWidth);
	 document.getElementById("main").style.width = document.body.clientWidth;
		//页面调整
	Ext.getCmp('VerifyRuleGrid').setHeight(505);
	Ext.getCmp('VerifyRuleGrid').setWidth(927);	
	 //Ext.getCmp('VerifyRuleGrid').setHeight(Ext.getBody().getViewSize().height-objTop(document.getElementById('forView_VerifyRuleGrid'))[0]-4);
	 //Ext.getCmp('VerifyRuleGrid').setWidth((Ext.isIE?Ext.getBody().getViewSize().width:window.innerWidth)-objTop(document.getElementById('forView_VerifyRuleGrid'))[1]-2); 
});
function objTop(obj){
    var tt = obj.offsetTop;
    var ll = obj.offsetLeft;
    while(true){
    	if(obj.offsetParent){
    		obj = obj.offsetParent;
    		tt+=obj.offsetTop;
    		ll+=obj.offsetLeft;
    	}else{
    		return [tt,ll];
    	}
	}
    return tt;  
}

function runAllRule(){
	var vsc001=document.getElementById('dbClickVsc001').value;
	var mk = new Ext.LoadMask(document.body, {  
		msg: '正在启用，请稍候！',  
		removeMask: true //完成后移除  
		});  
		mk.show(); //显示  
	 Ext.Ajax.request({
			url : "radowAction.do?method=doEvent&pageModel=pages.sysmanager.verificationschemeconf.VerificationSchemeConf&eventNames=runAllRule&vsc001="+vsc001,
			success : function(data) {
				mk.hide(); //关闭  
				//console.log(data);
				//Ext.msg.alert(data.responseText);
				Ext.Msg.alert("系统提示",data.responseText);
				radow.doEvent("VerifyRuleGrid.dogridquery");
			}
		});
}
function checkF(){
	$h.openPageModeWin('checkFunctionWin','pages.cadremgn.sysmanager.CheckFunction','校核函数定义',1000,650,'','<%=request.getContextPath()%>');
} 
function checkE(){
	$h.openPageModeWin('checkExpressionWin','pages.cadremgn.sysmanager.CheckExpression','校核表达式定义',795,620,'','<%=request.getContextPath()%>');
} 
function checkExpressionWinQD(vscvru){
	$h.openPageModeWin('QueryCallWin2','pages.cadremgn.sysmanager.CheckExpression','逻辑校核条件维护',1010,645,vscvru,'<%=request.getContextPath()%>'); 
}

</script>

<script type="text/javascript">
Ext.onReady(function(){
	document.getElementById("groupTreeContent").parentNode.parentNode.style.overflow='hidden';
	window.onresize=resizeframe;
	resizeframe();
});

function resizeframe(){
	document.getElementById("groupTreeContent").parentNode.style.width=document.body.clientWidth+'px';
	var viewSize = Ext.getBody().getViewSize();
	document.getElementById("checkFunction").style.marginRight="160px";
	var businessInfoGrid =Ext.getCmp('VeriySchemeGrid');
	businessInfoGrid.setHeight(viewSize.height);//56 52

	var modelInfoGrid =Ext.getCmp('VerifyRuleGrid');
	modelInfoGrid.setHeight(viewSize.height);
	modelInfoGrid.setWidth(viewSize.width-270);
}
</script>