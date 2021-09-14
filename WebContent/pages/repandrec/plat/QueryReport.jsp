<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="basejs/pageUtil.js"></script>


<odin:hidden property="clickIndex"/>
<odin:toolBar property="btnToolBar" applyTo="toolDiv">
	<odin:textForToolBar text=""/>
	<odin:fill/>
	<odin:buttonForToolBar text="数据上报" id="repBtn" isLast="true" icon="images/icon/exp.png"/>
	<%--
	<odin:buttonForToolBar text="主题分库数据上报" id="repModelBtn" /> 
	
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="查询" id="query" />
	<odin:buttonForToolBar text="重置" id="clear" isLast="true"/>--%>
</odin:toolBar>
<div id="toolDiv"></div>
<div id="panel_content" style="border:solid 1px #c3daf9">
<table border="0" id="myform" width="100%"  cellpadding="0" cellspacing="0">
	<tr>
		<td colspan="6" height="17"></td>
	</tr>
   
   <tr>
		<odin:hidden property="reporttype" />
		<odin:dateEdit property="createtimesta" format="Ymd" label="上报日期从" /> 
		<odin:dateEdit property="createtimeend" format="Ymd" label="至"/>
		<td><odin:button text="查询" property="query"></odin:button></td>
			<td><odin:button text="重置" property="clear"></odin:button></td>
	</tr>
   <tr>
		<td colspan="6" height="15"></td>
	</tr>	 
 </table>
</div>
<!-- 
			<odin:panel contentEl="panel_content" property="mypanel" topBarId="btnToolBar" ></odin:panel> -->
			<odin:editgrid property="MGrid" title="" autoFill="true" bbarId="pageToolBar" pageSize="20" isFirstLoadData="false" url="/">
			<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
					
					<odin:gridDataCol name="reporttype" />
					<odin:gridDataCol name="reporttime" />
					<odin:gridDataCol name="recieveftpusername" />
					<odin:gridDataCol name="filename" />
					<odin:gridDataCol name="packagename" />
					<odin:gridDataCol name="reportstatue" />
					
					<odin:gridDataCol name="reportftpid" isLast="true"/>
					
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					
					<odin:gridEditColumn header="上报" width="50" dataIndex="reporttype"
						edited="false" selectData="['1','数据上报'],['2','主题分库模型上报']" editor="select" />
					<odin:gridEditColumn header="上报日期" align="center" width="50" hidden="true"
						dataIndex="reporttime" editor="text" edited="false" />
					<odin:gridEditColumn header="上报机构" dataIndex="recieveftpusername" align="center"
						edited="false" editor="text" width="50" />
					<odin:gridEditColumn header="上报包" dataIndex="packagename" align="center"
						edited="false" editor="text" width="150" />
					<odin:gridEditColumn header="上报文件"  hidden="true" dataIndex="filename" align="center"
						edited="false" editor="text" width="50" />
					<odin:gridEditColumn header="上报状态" dataIndex="reportstatue" align="center" hidden="true"
						edited="false" editor="text" width="50" />
					<%-- 
					<odin:gridEditColumn  align="center" width="60" header="详情" dataIndex="reportftpid" 
						editor="text" edited="false" renderer="detailRender"  />
					--%>
					<odin:gridEditColumn header="id" dataIndex="reportftpid" align="center" hidden="true"
						edited="false" editor="text" width="340" isLast="true" />
					
				</odin:gridColumnModel>
			</odin:editgrid>

			
<odin:window src="/blank.htm" id="modelwin" width="600" height="500" title="窗口">
</odin:window>
<odin:window src="/blank.htm" id="dataorgwin" width="630" height="500" maximizable="false" title="数据上报">
</odin:window>
<odin:window src="/blank.htm" id="refreshWin" width="550" height="400" maximizable="false" title="窗口" />
<script type="text/javascript">
Ext.onReady(function() {
	//页面调整
	Ext.getCmp('MGrid').setHeight(Ext.getBody().getViewSize().height-objTop(document.getElementById('forView_MGrid'))[0]-4);
	Ext.getCmp('MGrid').setWidth((Ext.isIE?Ext.getBody().getViewSize().width:window.innerWidth)-objTop(document.getElementById('forView_MGrid'))[1]-2); 
	document.getElementById("panel_content").style.width = Ext.getCmp('MGrid').getWidth() + "px";
	document.getElementById("toolDiv").style.width = Ext.getCmp('MGrid').getWidth()-1 + "px";
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