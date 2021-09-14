<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<odin:toolBar property="btnToolBar" applyTo="panel_content">
	<odin:fill/>
	<%--<odin:buttonForToolBar text="新建登记" id="addCBD" ></odin:buttonForToolBar>
	<odin:separator></odin:separator>
	 <odin:buttonForToolBar text="修改呈报单" id="modifyCBDBtn" handler="modifyCBD"></odin:buttonForToolBar>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="下载呈报单" id="getCBD" handler="expExcelTemp"></odin:buttonForToolBar>
	<odin:separator></odin:separator>
   <odin:buttonForToolBar text="查看呈报单流程" id="process" handler="processBtn"></odin:buttonForToolBar>
    <odin:separator></odin:separator>--%>
    <odin:buttonForToolBar text="查看" id="look" menu="lookMenu" icon="images/icon/table.gif"></odin:buttonForToolBar>
	<odin:separator />
	<odin:buttonForToolBar text="呈报单状态" id="lookStatus" menu="statusMenu" icon="images/icon/table.gif"></odin:buttonForToolBar>
	<odin:separator />
	<odin:buttonForToolBar id="btndown" text="导出呈报单" icon="images/icon/exp.png" cls="x-btn-text-icon" />
    <odin:separator />
	<odin:buttonForToolBar id="btnsx" text="刷新" icon="images/sx.gif" cls="x-btn-text-icon" isLast="true" />
	<%--<odin:buttonForToolBar text="编辑附件" id="uploadFile" menu="attach"></odin:buttonForToolBar>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="上报呈报单" id="repBtn" handler="rep" icon="images/icon/exp.png"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="下载呈报单数据包" id="getCBDZipBtn" handler="getCBDZip" isLast="true" icon="images/icon/exp.png"/> --%>
</odin:toolBar>
<odin:hidden property="a0000"/>
<div id="panel_content">
</div>
<!-- 
<odin:panel contentEl="panel_content" property="mypanel" topBarId="btnToolBar" ></odin:panel> -->
<odin:editgrid property="CBDGrid" title="" autoFill="true" bbarId="pageToolBar" pageSize="20" isFirstLoadData="false" url="/">
<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
		<odin:gridDataCol name="checked" />
		<odin:gridDataCol name="cbd_id" />
		<odin:gridDataCol name="cbd_name" />
		<odin:gridDataCol name="cbd_path" />
		<odin:gridDataCol name="cbd_date" />
		<odin:gridDataCol name="cbd_word" />
		<odin:gridDataCol name="cbd_year" />
		<odin:gridDataCol name="word" />
		<odin:gridDataCol name="cbd_no" />
		<odin:gridDataCol name="cbd_leader" />
		<odin:gridDataCol name="cbd_date1" />
		<odin:gridDataCol name="cbd_organ" />
		<odin:gridDataCol name="cbd_text" />
		<odin:gridDataCol name="cbd_personid" />
		<odin:gridDataCol name="cbd_personname" />
		<odin:gridDataCol name="cbd_userid" />
		<odin:gridDataCol name="cbd_username" />
		<odin:gridDataCol name="status" />
		
		<odin:gridDataCol name="objectno" isLast="true"/>
		
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn></odin:gridRowNumColumn>
		<odin:gridColumn header="selectall" width="25" gridName="CBDGrid" editor="checkbox" dataIndex="checked" edited="true"/>
		<odin:gridEditColumn2 header="呈报单名称" width="70" dataIndex="cbd_name"
			edited="false" editor="select" />
		<odin:gridEditColumn header="呈报单审批字" align="center" width="60" hidden="true"
			dataIndex="word" editor="text" edited="false" />
		<odin:gridEditColumn header="领导称谓"  hidden="true" dataIndex="cbd_leader" align="center"
			edited="false" editor="text" width="50" />
		<odin:gridEditColumn header="呈报单日期" dataIndex="cbd_date1" align="center" hidden="false"
			edited="false" editor="text" width="50" />
		<odin:gridEditColumn  align="center" width="50" header="承办单位" dataIndex="cbd_organ" 
			editor="text" edited="false" hidden="true" />
		<odin:gridEditColumn header="呈报单人员姓名" dataIndex="cbd_personname" align="center" hidden="true"
			edited="false" editor="text" width="100" />
		<odin:gridEditColumn header="呈报单类型" dataIndex="cbd_path" align="center" hidden="false"
		edited="false" editor="text" width="50" renderer="getType"/>
		<odin:gridEditColumn2 header="呈报单状态" dataIndex="status" align="center" hidden="false" edited="false" editor="select" width="40" codeType="CBDSTATUS"/>
		
		<odin:gridEditColumn header="操作用户" dataIndex="cbd_username" align="center" hidden="false"
		edited="false" editor="text" width="50" isLast="true" />
		
	</odin:gridColumnModel>
</odin:editgrid>
<odin:window src="/blank.htm" id="newUpCBD" width="560" height="450"
	maximizable="false" title="生成上报呈报单" modal="true"></odin:window>
<odin:window src="/blank.htm"  id="processSystemWindow" width="1000" height="500" title="呈报单流程" modal="true"/>			
<odin:window src="/blank.htm" id="editCBD" width="550" height="450" maximizable="false" title="录入呈报单">
</odin:window>
<odin:window src="/blank.htm" modal="true" id="ReportCBD" title="上报呈报单页面" width="600" height="480"></odin:window>

<script type="text/javascript">

//获取页面中的值
	function getValue(){
		
		var cbd_id = '';
		var cbd_name = '';
		var cbd_path = '';
		var gridId = "CBDGrid";
		var grid = Ext.getCmp(gridId);
		//var store = grid.getStore();
		var count = 0;
		var sm = grid.getSelectionModel();
		var selections = sm.getSelections();
		var store = grid.store;
		for (var i = 0; i < store.data.length; i++) {
			var selected = store.getAt(i);
			var record = selected.data;
			if (record.checked) {
				cbd_id =  record.cbd_id;
				cbd_name = record.cbd_name;
				cbd_path = record.cbd_path;
				count = count+1;
			}
		}
		return cbd_id+"@"+cbd_name+"@"+count+"@"+cbd_path;
	}
	
	//查看呈报单流程
    function processBtn(){
    	var value=getValue();
		var values = value.split("@");
		var cbd_id = values[0];
		var cbd_name = values[1];
		var count = values[2];
		var cbd_path = values[3];

		if (cbd_id == '') {
			alert("请选择一条呈报单记录！");
			return;
		}
		if(count > 1 ){
			alert("只能选择一条记录！");
			return;
		}
		radow.doEvent("processSystem",cbd_id+"@"+cbd_name+"@"+cbd_path);
	}
	//查看呈报单流程
    function processBtn1(value){
		radow.doEvent("processSystem",value);
	}
	
	//刷新
    function reloadGrid(){
		radow.doEvent("btnsx.onclick");
	}
	
/**
	 * 导出呈报单
	 */
	function expExcelTemp() {
	
		var value=getValue();
		var values = value.split("@");
		var cbd_id = values[0];
		var cbd_name = values[1];
		var count = values[2];
		var cbd_path = values[3];

		if (cbd_id == '') {
			alert("请选择一条呈报单记录！");
			return;
		}
		if(count > 1 ){
			alert("只能选择一条记录！");
			return;
		}
		
		doOpenPupWin(encodeURI(encodeURI(contextPath
				+ "/pages/cbdHandler/cbdLoad.jsp?cbd_id=" + cbd_id +"&cbd_name="+cbd_name+ "&download=true&flag=u&cbd_type="+cbd_path)), "下载文件", 600, 200);
	}
	
	//查看/删除附件
	function modifyFile(){
		
		//获取页面中的值
		var value=getValue();
		var values = value.split("@");
		
		if (values[0] == '') {
			alert("请选择一条呈报单记录！");
			return;
		}
		if(values[2] > 1){
			alert("只能选择一条呈报单记录！");
			return;
		}
		
		radow.doEvent("modifyAttach",values[0]+"@1");
		
	}
	
	//呈报单上传附件
	function editFile(){
		
		//获取页面中的值
		var value=getValue();
		var values = value.split("@");
		var cbd_id = values[0];
		var cbd_name = values[1];
		var count = values[2];
		
		if (cbd_id == '') {
			alert("请选中要上传附件的呈报单记录！");
			return;
		}
		if(count > 1){
			alert("只能选择一条呈报单记录！");
			return;
		}
		
		var win = odin.ext.getCmp('simpleExpWin');
		win.setTitle('导入窗口');
		odin.showWindowWithSrc('simpleExpWin', contextPath
				+ "/pages/search/EditFile.jsp?flag=0&uuid="+cbd_id+"&uname="+cbd_name);
	}
	
	//修改呈报单
	function modifyCBD(){
	
		//获取页面中的值
		var value=getValue();
		var values = value.split("@");
		var cbd_id = values[0];
		var cbd_name = values[1];
		var count = values[2];
		var cbd_path = values[3];				
		if (cbd_id == '') {
			alert("请选择要查看的呈报单记录！");
			return;
		}
		if(count > 1){
			alert("只能选择一条呈报单记录！");
			return;
		}
		radow.doEvent('modifyCBD',cbd_id+"@"+cbd_path);
	}
	
	//呈报单上报
	function rep(){
	
		//获取页面中的值
		var value=getValue();
		var values = value.split("@");
		var cbd_id = values[0];
		var cbd_name = values[1];
		var count = values[2];
		
		if (cbd_id == '') {
			alert("请选中要上报的呈报单记录！");
			return;
		}
		if(count > 1){
			alert("只能选择一条呈报单记录！");
			return;
		}
		radow.doEvent('repBtn',cbd_id+"@"+cbd_name);
	}
	
	//导出呈报单压缩文件
	function getCBDZip(){

		var value=getValue();
		var values = value.split("@");
		var cbd_id = values[0];
		var cbd_name = values[1];
		var count = values[2];
		var cbd_path = values[3];
		if (cbd_id == '') {
			alert("请选择一条呈报单记录！");
			return;
		}
		if(count > 1 ){
			alert("只能选择一条记录！");
			return;
		}
		
		doOpenPupWin(encodeURI(encodeURI(contextPath
				+ "/pages/cbdHandler/CBDZipDownLoad.jsp?cbd_id=" + cbd_id +"&cbd_name="+cbd_name+ "&download=true&flag=u&cbd_type="+cbd_path)), "下载呈报单数据包", 600, 250);
		
	}
	
	//生成上报呈报单
	function addUpCBD(){
		var value=getValue();
		var values = value.split("@");
		var cbd_id = values[0];
		var cbd_name = values[1];
		var count = values[2];
		var cbd_path = values[3];
		if(count > 1){
			alert("只能选择一条呈报单记录！");
			return;
		}
		if (cbd_id == '') {
			alert("请选择一条呈报单记录！");
			return;
		}
		if(cbd_path == '1'){
			alert("选中的呈报单记录为上报呈报单，只有本级呈报单可以生成上报呈报单！");
			return;
		}
		radow.doEvent('addUpCBD',cbd_id);
		
	}
	
	//根据呈报单的类型编码返回类型名称
	function getType(value, params, record, rowIndex, colIndex, ds){
		if(value == '1'){
			return '本级呈报单';
		}else if(value == '2'){
			return '上报呈报单';
		}
	}
	
<odin:menu property="attach">
<odin:menuItem text="上传附件" property="editFile" handler="editFile" ></odin:menuItem>
<odin:menuItem text="查看/删除附件" property="modifyFileBtn" handler="modifyFile" isLast="true" ></odin:menuItem>
</odin:menu>

<odin:menu property="lookMenu">
<odin:menuItem text="本级呈报单" property="bjBtn" ></odin:menuItem>
<odin:menuItem text="上报呈报单" property="sbBtn" isLast="true" ></odin:menuItem>
</odin:menu>

<odin:menu property="statusMenu">
<odin:menuItem text="办理中" property="inProcess" ></odin:menuItem>
<odin:menuItem text="已同意" property="allEnd" ></odin:menuItem>
<odin:menuItem text="已退回" property="back" isLast="true" ></odin:menuItem>
</odin:menu>

//<odin:menu property="newCBD">
//<odin:menuItem text="生成本级呈报单" property="addCBD" ></odin:menuItem>
//<odin:menuItem text="生成上报呈报单" property="addUpCBDBtn" handler="addUpCBD" isLast="true" ></odin:menuItem>
//</odin:menu>

function onHide(id,e){
	Ext.getCmp(id).on('hide',function(){eval(e);});
}

function expCBD(){
	var fileName="呈报人员名单.xls";
	var excelType="100";
	var viewType="";
	var value=getValue();
	var values = value.split("@");
	var cbd_id = values[0];
	if (cbd_id == '') {
		alert("请先勾选呈报单记录！");
		return;
	}
	var a0000 = document.getElementById('a0000').value;
	window.location="<%=request.getContextPath() %>/FiledownServlet?fileName=" + encodeURI(encodeURI(fileName)) +"&excelType="+excelType+"&viewType="+viewType+"&a0000="+a0000+"&download=true";
}
Ext.onReady(function() {
	//页面调整
	 Ext.getCmp('CBDGrid').setHeight(Ext.getBody().getViewSize().height-objTop(document.getElementById('forView_CBDGrid'))[0]-4);
	 Ext.getCmp('CBDGrid').setWidth((Ext.isIE?Ext.getBody().getViewSize().width:window.innerWidth)-objTop(document.getElementById('forView_CBDGrid'))[1]-2); 
	 document.getElementById('panel_content').style.width = document.body.clientWidth;
//	 Ext.getCmp('mypanel').setWidth(document.body.clientWidth);
	 //document.getElementById("ftpUpContent").style.width = document.body.clientWidth;
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
</script>