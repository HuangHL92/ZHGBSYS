<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<odin:hidden property="clickIndex"/>
<odin:toolBar property="btnToolBar">
	<odin:textForToolBar text=""/>
	<odin:fill/>
	
	<odin:buttonForToolBar text="补充信息项下发" id="normPubBtn" />
	<odin:buttonForToolBar text="补充代码集下发" id="codePubBtn" />
	<odin:buttonForToolBar text="校验方案下发" id="verificationPubBtn" />
	<odin:buttonForToolBar text="主题分库模型下发" id="modelPubBtn" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="查询" id="query" />
	<odin:buttonForToolBar text="重置" id="reset" isLast="true"/>
</odin:toolBar>
<div id="panel_content">
<table border="0" id="myform" width="100%"  cellpadding="0" cellspacing="0">
	<tr>
		<td colspan="6" height="17"></td>
	</tr>
   
   <tr>
		<td colspan="6" height="17">
			<odin:select2 property="reporttype"  label="下发类型" data=""/>
		    <odin:dateEdit property="createtimesta" format="Ymd" label="下发日期从" /> 
			<odin:dateEdit property="createtimeend" format="Ymd" label="至"/>
		</td>
		
	</tr>
   <tr>
		<td colspan="6" height="15"></td>
	</tr>	 
 </table>
</div>

			<odin:panel contentEl="panel_content" property="mypanel" topBarId="btnToolBar" ></odin:panel>
			<odin:editgrid property="MGrid" title="" autoFill="true" bbarId="pageToolBar" pageSize="20" isFirstLoadData="false" url="/">
			<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
					
					<odin:gridDataCol name="filename" />
					<odin:gridDataCol name="imptime" />
					<odin:gridDataCol name="empgroupname" />
					<odin:gridDataCol name="isvirety" />
					<odin:gridDataCol name="wrongnumber" />
					<odin:gridDataCol name="totalnumber" />
					
					<odin:gridDataCol name="imprecordid" isLast="true"/>
					
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					
					<odin:gridEditColumn header="下发类型" width="50" dataIndex="filename"
						edited="false" editor="text" />
					<odin:gridEditColumn header="下发日期" align="center" width="50"
						dataIndex="imptime" editor="text" edited="false" />
					
					<odin:gridEditColumn header="下发包"  hidden="true" dataIndex="isvirety" align="center"
						selectData="['0','否'],['1','是']" edited="false" editor="text" width="50" />
					<odin:gridEditColumn header="下发文件" dataIndex="wrongnumber" align="center"
						edited="false" editor="text" width="50" />
					<odin:gridEditColumn header="下发状态" dataIndex="totalnumber" align="center"
						edited="false" editor="text" width="50" />
					<odin:gridEditColumn  align="center" width="60" header="详情" dataIndex="imprecordid" 
						editor="text" edited="false" renderer="detailRender"  />
					<odin:gridEditColumn header="id" dataIndex="imprecordid" align="center" hidden="true"
						edited="false" editor="text" width="340" isLast="true" />
					
				</odin:gridColumnModel>
			</odin:editgrid>

			
<odin:window src="/blank.htm" id="codePubwin" width="600" height="302" title="补充信息项下发">
</odin:window>
<odin:window src="/blank.htm" id="normPubwin" width="600" height="480" maximizable="false" title="补充代码集下发">
</odin:window>
<odin:window src="/blank.htm" id="verificationPubwin" width="600" height="302" title="校验方案下发">
</odin:window>
<odin:window src="/blank.htm" id="modelPubwin" width="600" height="480" maximizable="false" title="主题分库模型下发">
</odin:window>
<script type="text/javascript">


function impTest(){
	var win = odin.ext.getCmp('simpleExpWin');
	win.setTitle('导入窗口');
   /// win.setSize(500,350); //宽度  高度
	odin.showWindowWithSrc('simpleExpWin',contextPath+"/pages/dataverify/DataVerify.jsp");
}
/**
* 数据授权的render函数
*/
function detailRender(value, params, rs, rowIndex, colIndex, ds){
	return "<a href=\"javascript:openDetailWin('"+value+"')\">详情</a>";
}
function openDetailWin(imprecordid){
	document.getElementById("radow_parent_data").value = imprecordid;
	doOpenPupWin("/radowAction.do?method=doEvent&pageModel=pages.dataverify.ImpDetail&imprecordid="+imprecordid,
		"导入详情",650,446,null);
}
function grantTabChange(tabObj,item){
	if(item.getId()=='tab2'){
		odin.ext.getCmp('groupgrid').view.refresh(true);
	}
}
function clickquery(){
	document.getElementById("query").click();
}

function impScheme(){
	 odin.showWindowWithSrc("impExcelWin",contextPath+"/pages/dataimpexcel/Dataimpexcel.jsp"); 
}

</script>