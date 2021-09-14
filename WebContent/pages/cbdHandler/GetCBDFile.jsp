<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<odin:toolBar property="btnToolBar" applyTo="ftpUpManagePanel">
	<odin:fill />
	<odin:buttonForToolBar id="getCBDBtn" text="接收" handler="getZipFile" icon="images/keyedit.gif"/>
	<odin:separator></odin:separator>
	<%-- <odin:buttonForToolBar id="ansBtn" text="批复" menu="answer" icon="images/keyedit.gif"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar id="backCBDBtn" text="打回" handler="back" icon="images/keyedit.gif"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar id="checkCDBtn" text="批复" handler="reply" icon="images/keyedit.gif"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar id="localcbdo" text="本级呈报单操作" menu="localCBD" icon="images/keyedit.gif" />
	<odin:separator></odin:separator> --%>
	<odin:buttonForToolBar id="uploadZipBtn" text="导入数据包"  handler="uploadZip" icon="images/icon/exp.png"/>
	<odin:separator></odin:separator>
	<%--<odin:buttonForToolBar text="查看呈报单流程" id="SBprocess" handler="SBprocessBtn"></odin:buttonForToolBar>
	<odin:separator></odin:separator>--%>
	<odin:buttonForToolBar text="下载呈报单数据包" id="getCBDZipBtn" handler="createCBDZip" icon="images/icon/exp.png"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar id="btnsx" text="刷新" icon="images/sx.gif" cls="x-btn-text-icon" isLast="true"  />
</odin:toolBar>
<!--  
<odin:panel contentEl="ftpUpContent" property="ftpUpManagePanel" topBarId="btnToolBar" />-->
<div id="ftpUpContent" style="width:100%;height: 100%; overflow: auto;">
<div id="ftpUpManagePanel"></div>
<odin:editgrid property="CBDGrid" title="待接收文件列表" autoFill="true" height="550" isFirstLoadData="false" url="/" grouping="true" groupCol="transTypeCn">
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="cbdchecked" />
		<odin:gridDataCol name="fileName" />
		<odin:gridDataCol name="filePath" />
		<odin:gridDataCol name="orgName" />
		<odin:gridDataCol name="transType" />
		<odin:gridDataCol name="transTypeCn" />
		<odin:gridDataCol name="dataInfo" />
		<odin:gridDataCol name="createTime" />
		<odin:gridDataCol name="status" />
		<odin:gridDataCol name="cbd_id" />
		<odin:gridDataCol name="cbd_name" />
		<odin:gridDataCol name="personid" />
		<odin:gridDataCol name="personname" />
		<odin:gridDataCol name="username" />
		<odin:gridDataCol name="isOther"  isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn></odin:gridRowNumColumn>
		<odin:gridColumn header="selectall" width="25" gridName="CBDGrid" editor="checkbox" dataIndex="cbdchecked" edited="true"/>
		<odin:gridColumn dataIndex="fileName" width="250" header="文件名称"  align="left"/>
		<odin:gridColumn dataIndex="orgName" width="100" header="登记单位"  align="center"   />
		<odin:gridColumn dataIndex="transType" width="100" header="传输类型"  align="center"   hidden="true" />
		<odin:gridColumn dataIndex="transTypeCn" header="传输类型" align="center"  hidden="true"   />
		<odin:gridColumn dataIndex="dataInfo" width="200" header="文件包描述"  align="center"  hidden="true" renderer="radow.renderer.renderAlt"/>
		<odin:gridColumn dataIndex="createTime" width="180" header="请求时间"  align="center"   />
		<odin:gridColumn dataIndex="status" width="200" header="状态"  align="center" renderer="accpetRender" />
		<odin:gridColumn dataIndex="username" width="200" header="操作用户"  align="center"  isLast="true" />
	</odin:gridColumnModel>
</odin:editgrid>
</div>
<odin:hidden property="cbd_name"/>
<odin:hidden property="getFlag" value="0"/>
<odin:window src="/blank.htm" id="editCBD" width="550" height="450" maximizable="false" title="录入呈报单">
</odin:window>
<odin:window src="/" modal="true" id="editFileWindow" title="附件记录列表" width="500" height="350"></odin:window>
<odin:window src="/blank.htm" id="simpleExpWin" width="560" height="350" maximizable="false" title="附件窗口"></odin:window>
<odin:window src="/blank.htm" id="backWin" width="560" height="350" maximizable="false" modal="true" title="打回意见窗口"></odin:window>
<odin:window src="/blank.htm" id="modifyFileWindow" width="560" height="300" maximizable="false" title="查看/删除附件窗口"></odin:window>
<odin:window src="/blank.htm" id="uploadZipWin" width="560" height="350" maximizable="false" title="上传呈报单数据包"></odin:window>

<odin:window src="/blank.htm"  id="SBprocessSystemWindow" width="800" height="600" title="呈报单流程" modal="true"/>			
<script type="text/javascript">

//创建双击事件：双击文件名称可以下载呈报单上报文件
function ondbclick(value, params, record, rowIndex, colIndex, ds){
	return "<a ondblclick=\"javascript:getZipFile1('"+record.get('cbd_id')+"@"+record.get('cbd_name')+"@"+record.get('filePath')+"@1')\">"+value+"</a>";
}
//获取页面信息的公用方法，主要获取主键，名称和文件地址，返回值格式为：呈报单主键@呈报单名称@文件路径@选中记录数
	function getValue(){
	
		var gridId = "CBDGrid";//页面grid主键
		var grid = Ext.getCmp(gridId);
		//定义变量值
		var filePath = '';
		var cbd_id = '';
		var cbd_name = '';
		var status = '';
		var count = 0;
		var personid = '';
		var personname = '';
		
		var sm = grid.getSelectionModel();
		var selections = sm.getSelections();
		var store = grid.store;
		for (var i = 0; i < store.data.length; i++) {
			var selected = store.getAt(i);
			var record = selected.data;
			if (record.cbdchecked) {
				cbd_id = record.cbd_id ;
				filePath = record.filePath ;
				cbd_name = record.cbd_name ;
				status = record.status;
				personname = record.personname;
				personid = record.personid;
				count=count+1;
			}
		}
		return cbd_id+"@"+cbd_name+"@"+filePath+"@"+count+"@"+status+"@"+personname+"@"+personid;
	}
	

	//查看呈报单流程
	function SBprocessBtn(value){
		radow.doEvent("processSystem",value);
	}
	//查看呈报单流程
	function SBprocessBtn1(){
		var value=getValue();
		var values = value.split("@");
		var cbd_id = values[0];
		var cbd_name = values[1];
		var filePath = values[2];
		var count = values[3];
		var status = values[4];
		var personname = values[5];
		var personid = values[6]
		if(count > 1){
			alert("只能选择一条呈报单记录！");
			return;
		}
		if (cbd_id == '') {
			alert("请选择一条记录！");
			return;
		}
		radow.doEvent("processSystem",cbd_id+"@"+cbd_name+"@"+personname+"@"+personid+"@"+status+"@"+filePath);
	}
	
	//导出呈报单压缩文件
	function createCBDZip(){
		var value=getValue();
		var values = value.split("@");
		var cbd_id = values[0];
		var cbd_name = values[1];
		var filePath = values[2];
		var count = values[3];
		if(count > 1){
			alert("只能选择一条呈报单记录！");
			return;
		}
		if (cbd_id == '') {
			alert("请选择一条记录！");
			return;
		}
		
		url="<%=request.getContextPath()%>/CBDFiledownServlet?method=getZipFile&flag=g&cbd_id="+cbd_id+"&filePath="+filePath;
		var iframe = document.createElement("iframe");
		iframe.src = url;
		iframe.style.display = "none";
		document.body.appendChild(iframe);
	}

//呈报单状态
function accpetRender(value, params, rs, rowIndex, colIndex, ds){
	if(value=='0'){
		return '未接收';
	}else if(value=='1'){
		return '已接收';
	}else if(value=='2'){
		return '已打回';
	}else if(value=='3'){
		return '已批复';
	}
}

	//接收文件
	function getZipFile(){
		var value=getValue();
		var values = value.split("@");
		var cbd_id = values[0];
		var cbd_name = values[1];
		var filePath = values[2];
		var count = values[3];
		if(count > 1){
			alert("只能选择一条呈报单记录！");
			return;
		}
		if (cbd_id == '') {
			alert("请选择一条记录！");
			return;
		}
		
		url="<%=request.getContextPath()%>/CBDFiledownServlet?method=getZipFile&flag=g&cbd_id="+cbd_id+"&filePath="+filePath;
		var iframe = document.createElement("iframe");
		iframe.src = url;
		iframe.style.display = "none";
		document.body.appendChild(iframe);
		
	}
	
	//文件名双击接收文件
	function getZipFile1(value){
		var values = value.split("@");
		var cbd_id = values[0];
		var cbd_name = values[1];
		var filePath = values[2];
		var count = values[3];
		if(count > 1){
			alert("只能选择一条呈报单记录！");
			return;
		}
		if (cbd_id == '') {
			alert("请选择一条记录！");
			return;
		}
		
		url="<%=request.getContextPath()%>/CBDFiledownServlet?method=getZipFile&cbd_id="+cbd_id+"&filePath="+filePath;
		var iframe = document.createElement("iframe");
		iframe.src = url;
		iframe.style.display = "none";
		document.body.appendChild(iframe);
		
	}
	
/**
	 * 导出呈报单
	 */
	function expExcelTemp() {
	
		var value=getValue();
		var values = value.split("@");
		var cbd_id = values[0];
		var cbd_name = values[1];
		var filePath = values[2];
		var count = values[3];
		
		if (cbd_id == '') {
			alert("请选择一条记录！");
			return;
		}
		if(count > 1){
			alert("只能选择一条记录！");
			return;
		}

		doOpenPupWin(encodeURI(encodeURI(contextPath
				+ "/pages/cbdHandler/cbdLoad.jsp?cbd_id=" + cbd_id +"&cbd_name="+cbd_name+ "&download=true&flag=g&cbd_type=0")), "下载文件", 600, 200);
	}
	
	function checkCBDInfo(){
		var value=getValue();
		var values = value.split("@");
		var cbd_id = values[0];
		var cbd_name = values[1];
		var filePath = values[2];
		var count = values[3];
		
		radow.doEvent('checkCBD',cbd_id+"@editFile");
	}
	function checkCBDInfo1(){
		var value=getValue();
		var values = value.split("@");
		var cbd_id = values[0];
		var cbd_name = values[1];
		var filePath = values[2];
		var count = values[3];
		
		radow.doEvent('checkCBD',cbd_id+"@modifyFile");
	}
	//本级呈报单编辑附件
	function editFile(){
		
		var value=getValue();
		var values = value.split("@");
		var cbd_id = values[0];
		var cbd_name = values[1];
		var filePath = values[2];
		var count = values[3];
		
		if (cbd_id == '') {
			alert("请选择一条记录！");
			return;
		}
		if(count > 1){
			alert("只能选择一条记录！");
			return;
		}
		var win = odin.ext.getCmp('simpleExpWin');
		win.setTitle('导入窗口');
		odin.showWindowWithSrc('simpleExpWin', contextPath
				+ "/pages/search/EditFile.jsp?flag=2&uuid="+cbd_id+"&uname="+cbd_name);
		
	}
	
	//新增本级呈报单
	function addCBDInfo(){
	
		var value=getValue();
		var values = value.split("@");
		var cbd_id = values[0];
		var cbd_name = values[1];
		var filePath = values[2];
		var count = values[3];
		
		if(cbd_id == ''){
			alert("请选择一条呈报单记录！");
			return;
		}
		if(count > 1){
			alert("只能选择一条呈报单记录！");
			return;
		}
		
		radow.doEvent('modifyCBD',cbd_id);
		
	}
	
	//修改本级呈报单
	function modifyCBDInfo(){
	
		var value=getValue();
		var values = value.split("@");
		var cbd_id = values[0];
		var cbd_name = values[1];
		var filePath = values[2];
		var count = values[3];
		
		if(cbd_id == ''){
			alert("请选择一条呈报单记录！");
			return;
		}
		if(count > 1){
			alert("只能选择一条呈报单记录！");
			return;
		}
		
		radow.doEvent('modifyCBD',cbd_id);
	}

	//录入打回意见
	function back(){
		
		var value=getValue();
		var values = value.split("@");
		var cbd_id = values[0];
		var cbd_name = values[1];
		var filePath = values[2];
		var count = values[3];
		var status = values[4];
		if(status == '2'){
			alert("该条呈报单已经被打回，不能再次打回！");
			return;
		}
		if(status == '3'){
			alert("该条呈报单已经批复，不能作打回操作！");
			return;
		}
		if(cbd_id == ''){
			alert("请选择一条呈报单记录！");
			return;
		}
		if(count > 1){
			alert("只能选择一条呈报单记录！");
			return;
		}
		
		radow.doEvent('getBackWin',cbd_id+"@"+cbd_name+"@"+filePath);
	}
	
	//批复
	function reply(){
	
		var value=getValue();
		var values = value.split("@");
		var cbd_id = values[0];
		var cbd_name = values[1];
		var filePath = values[2];
		var count = values[3];
		
		if(cbd_id == ''){
			alert("请选择一条呈报单记录！");
			return;
		}
		if(count > 1){
			alert("只能选择一条呈报单记录！");
			return;
		}
		
		radow.doEvent('reply',cbd_id+"@"+cbd_name+"@"+filePath);
	}
		//查看/删除附件
	function modifyFile(){
		
		var value=getValue();
		var values = value.split("@");
		
		if (values[0] == '') {
			alert("请选择一条呈报单记录！");
			return;
		}
		if(values[3] > 1){
			alert("只能选择一条呈报单记录！");
			return;
		}
		
		radow.doEvent("modifyAttach",values[0]+"@2");
		
	}
	
	//导入呈报单数据包
	function uploadZip(){
		
		var win = odin.ext.getCmp('simpleExpWin');
		win.setTitle('导入窗口');
		odin.showWindowWithSrc('simpleExpWin', contextPath
				+ "/pages/cbdHandler/UploadZip.jsp?flag=3");
	}
	
	Ext.onReady(function() {
		//页面调整
		 Ext.getCmp('CBDGrid').setHeight(Ext.getBody().getViewSize().height-objTop(document.getElementById('forView_CBDGrid'))[0]-4);
		 Ext.getCmp('CBDGrid').setWidth((Ext.isIE?Ext.getBody().getViewSize().width:window.innerWidth)-objTop(document.getElementById('forView_CBDGrid'))[1]-2); 
		 document.getElementById('ftpUpManagePanel').style.width = document.body.clientWidth;
		 //Ext.getCmp('btnToolBar').setWidth(document.body.clientWidth);
		 document.getElementById("ftpUpContent").style.width = document.body.clientWidth;
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
<odin:menu property="localCBD">
<odin:menuItem text="录入本级呈报单" property="createCBD" handler="addCBDInfo" ></odin:menuItem>
<odin:menuItem text="查看本级呈报单" property="getCBD" handler="expExcelTemp" ></odin:menuItem>
<odin:menuItem text="上传附件" property="editFile"  handler="checkCBDInfo" ></odin:menuItem>
<odin:menuItem text="查看/删除附件" property="modifyFileBtn" handler="checkCBDInfo1" isLast="true" ></odin:menuItem>
</odin:menu>

<odin:menu property="answer">
<odin:menuItem property="checkCDBtn" text="同意" handler="reply" ></odin:menuItem>
<odin:menuItem property="backCBDBtn" text="打回" handler="back" isLast="true" ></odin:menuItem>
</odin:menu>
</script>