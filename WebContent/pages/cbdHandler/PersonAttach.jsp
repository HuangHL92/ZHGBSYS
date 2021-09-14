<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@page
	import="com.insigma.siis.local.pagemodel.search.ComSearchPageModel"%>
<%@page
	import="com.insigma.siis.local.pagemodel.sysorg.org.SysOrgPageModel"%>
<%@page
	import="com.insigma.siis.local.pagemodel.sysmanager.group.GroupManagePageModel"%>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<odin:floatDiv property="btnToolBarDiv" position="up"></odin:floatDiv>
<odin:toolBar property="btnToolBar" applyTo="btnToolBarDiv">
	<odin:fill></odin:fill>
	<odin:buttonForToolBar text="登记表操作" id="DJBBtn" menu="DJB"
		icon="images/icon_photodesk.gif" cls="x-btn-text-icon"></odin:buttonForToolBar>
	<odin:buttonForToolBar text="导出备案表" id="getSheet" handler="getSheetBab"
		icon="images/icon_photodesk.gif" cls="x-btn-text-icon" />
	<odin:buttonForToolBar text="编辑个人附件" id="uploadFileForPerson"
		menu="attachPerson" icon="images/keyedit.gif" isLast="true"></odin:buttonForToolBar>
</odin:toolBar>

<table>
	<tr>
		<td height="25"></td>
	</tr>
</table>

<form name="simpleExcelForm" method="post"
	action="<%=request.getContextPath()%>/FiledownServlet"
	target="simpleExpFrame">
	<odin:editgrid property="peopleInfoGrid" title="登记人员名单"
		topBarId="toolBar1" width="400" height="350" autoFill="false"
		bbarId="pageToolBar" pageSize="7">
		<odin:gridJsonDataModel>
			<odin:gridDataCol name="personcheck" />
			<odin:gridDataCol name="a0000" />
			<odin:gridDataCol name="a0101" />
			<odin:gridDataCol name="a0104" />
			<odin:gridDataCol name="a0107" />
			<odin:gridDataCol name="a0117" />
			<odin:gridDataCol name="a0141" />
			<odin:gridDataCol name="a0192" />
			<odin:gridDataCol name="a0184" isLast="true" />
		</odin:gridJsonDataModel>
		<odin:gridColumnModel>
			<odin:gridRowNumColumn></odin:gridRowNumColumn>
			<odin:gridColumn header="selectall" width="40" gridName="personGrid"
				editor="checkbox" dataIndex="personcheck" edited="true"
				checkBoxClick="getCheckListForPerson()"
				checkBoxSelectAllClick="getCheckListForPerson()" />
			<odin:gridColumn dataIndex="a0000" width="110" header="id"
				align="center" hidden="true" />
			<odin:gridColumn dataIndex="a0101" width="110" header="姓名"
				align="center" />
			<odin:gridEditColumn2 dataIndex="a0104" width="100" header="性别"
				align="center" editor="select" edited="false" codeType="GB2261" />
			<odin:gridColumn dataIndex="a0107" width="130" header="出生日期"
				align="center" editor="text" edited="false" />
			<odin:gridEditColumn2 dataIndex="a0117" width="100" header="民族"
				align="center" editor="select" edited="false" codeType="GB3304" />
			<odin:gridEditColumn2 dataIndex="a0141" width="130" header="政治面貌"
				align="center" editor="select" edited="false" codeType="GB4762" />
			<odin:gridColumn dataIndex="a0192" width="130" header="工作单位及职务"
				align="center" isLast="true" />
		</odin:gridColumnModel>
		<odin:gridJsonData>
	        {
                   data:[]
            }
            </odin:gridJsonData>
	</odin:editgrid>
</form>

<odin:window src="/blank.htm" id="simpleExpWin" width="560" height="350" maximizable="false" title="附件窗口" modal="true"></odin:window>
<odin:window src="/blank.htm" id="modifyFileWindow" width="560" height="300" maximizable="false" title="查看/删除附件窗口" modal="true"></odin:window>

<odin:hidden property="checkListForPerson"/>
<odin:hidden property="djgridString"/>
<odin:hidden property="zhgridString"/>
<odin:hidden property="djs" />
<odin:hidden property="gbs"/>
<odin:hidden property="sys"/>
<odin:hidden property="zhs"/>
<odin:hidden property="dw"/>
<odin:hidden property="flag"/>

<script type="text/javascript">

	//-----------------------------------------------对个人进行操作--------------------------------------	
	//生成本级呈报单
	function addCbdBtn() {
		var a0000 = '';
		var a0101 = '';
		var gridId = "peopleInfoGrid";
		if (!Ext.getCmp(gridId)) {
			odin.error("要导出的grid不存在！gridId=" + gridId);
			return;
		}
		var grid = Ext.getCmp(gridId);
		var store = grid.getStore();
		if (store.getCount() == 0) {
			odin.error("未选择人员！");
			return;
		}

		var sm = grid.getSelectionModel();
		var selections = sm.getSelections();
		var store = grid.store;
		for (var i = 0; i < store.data.length; i++) {
			var selected = store.getAt(i);
			var record = selected.data;
			if (record.personcheck) {
				a0000 = a0000 + record.a0000 + ',';
				a0101 = a0101 + record.a0101 + ',';
			}
		}
		if (a0000 == '') {
			odin.error("未选择人员！");
			return;
		}

		a0101 = a0101.substring(0, a0101.length - 1);
		a0000 = a0000.substring(0, a0000.length - 1);
		radow.doEvent('addCBD', a0000 + "@" + a0101);

	}

	function getCheckListForPerson() {
		radow.doEvent('getCheckListForPerson');
	}

	//获取页面数据_个人
	function getValueForPerson() {

		var gridId = "peopleInfoGrid";
		var grid = Ext.getCmp(gridId);
		var store = grid.store;
		var count = 0;
		var a0000 = '';//人员编号
		var a0101 = '';//人员姓名
		var a0184 = '';//人员身份证号
		for (var i = 0; i < store.data.length; i++) {
			var selected = store.getAt(i);
			var record = selected.data;
			if (record.personcheck) {
				a0000 = record.a0000;
				a0101 = record.a0101;
				a0184 = record.a0184;
				count = count + 1;
			}
		}
		return a0000 + "@" + a0101 + "@" + a0184 + "@" + count;
	}

	/**
	 * 导出登记表册
	 * @param {} gridId
	 * @param {} fileName
	 * @param {} sheetName
	 * @param {} headNames
	 * @param {} isFromInterface
	 */
	function createExcelTemp() {
		var a0000 = '';
		var a0101 = '';
		var gridId = "peopleInfoGrid";
		if (!Ext.getCmp(gridId)) {
			odin.error("要导出的grid不存在！gridId=" + gridId);
			return;
		}
		var grid = Ext.getCmp(gridId);
		var store = grid.getStore();
		if (store.getCount() == 0) {
			odin.error("没有要导出的数据！");
			return;
		}

		var sm = grid.getSelectionModel();
		var selections = sm.getSelections();
		var store = grid.store;
		for (var i = 0; i < store.data.length; i++) {
			var selected = store.getAt(i);
			var record = selected.data;
			if (record.personcheck) {
				a0000 = a0000 + record.a0000 + ',';
				a0101 = a0101 + record.a0101 + ',';
			}
		}
		if (a0000 == '') {
			odin.error("请选中要导出的行！");
			return;
		}

		a0101 = a0101.substring(0, a0101.length - 1);
		a0000 = a0000.substring(0, a0000.length - 1);
		radow.doEvent('checkPer', a0000 + "@" + a0101);

	}

	/**
	 * 导出备案表册
	 * @param {} gridId
	 * @param {} fileName
	 * @param {} sheetName
	 * @param {} headNames
	 * @param {} isFromInterface
	 */
	function getSheetBab() {
		var a0000 = '';
		var a0101 = '';
		var gridId = "peopleInfoGrid";
		if (!Ext.getCmp(gridId)) {
			odin.error("要导出的grid不存在！gridId=" + gridId);
			return;
		}
		var grid = Ext.getCmp(gridId);
		var store = grid.getStore();
		if (store.getCount() == 0) {
			odin.error("没有要导出的数据！");
			return;
		}

		var sm = grid.getSelectionModel();
		var selections = sm.getSelections();
		var store = grid.store;
		for (var i = 0; i < store.data.length; i++) {
			var selected = store.getAt(i);
			var record = selected.data;
			if (record.personcheck) {
				a0000 = a0000 + record.a0000 + ',';
				a0101 = a0101 + record.a0101 + ',';
			}
		}
		if (a0000 == '') {
			odin.error("请选中要导出的行！");
			return;
		}

		a0101 = a0101.substring(0, a0101.length - 1);
		a0000 = a0000.substring(0, a0000.length - 1);
		radow.doEvent('getSheet', a0000 + "@" + a0101);

	}

	/**
	 * 导出登记表册
	 * @param {} gridId
	 * @param {} fileName
	 * @param {} sheetName
	 * @param {} headNames
	 * @param {} isFromInterface
	 */
	function expExcelTemp() {
		var a0000 = '';
		var a0101 = '';
		var gridId = "peopleInfoGrid";
		if (!Ext.getCmp(gridId)) {
			odin.error("要导出的grid不存在！gridId=" + gridId);
			return;
		}
		var grid = Ext.getCmp(gridId);
		var store = grid.getStore();
		if (store.getCount() == 0) {
			odin.error("没有要导出的数据！");
			return;
		}

		var sm = grid.getSelectionModel();
		var selections = sm.getSelections();
		var store = grid.store;
		for (var i = 0; i < store.data.length; i++) {
			var selected = store.getAt(i);
			var record = selected.data;
			if (record.personcheck) {
				a0000 = a0000 + record.a0000 + ',';
				a0101 = a0101 + record.a0101 + ',';
			}
		}
		if (a0000 == '') {
			odin.error("请选中要导出的行！");
			return;
		}
		a0101 = a0101.substring(0, a0101.length - 1);
		a0000 = a0000.substring(0, a0000.length - 1);
		doOpenPupWin(encodeURI(encodeURI(contextPath
				+ "/pages/exportexcel/djbLoad.jsp?a0000=" + a0000 + "&a0101="
				+ a0101 + "&download=true")), "下载文件", 600, 200);
	}

	//登记表编辑附件
	function editFilePer() {

		var value = getValueForPerson();
		var values = value.split("@");

		if (values[0] == '') {
			alert("请选中要上传附件的人员记录！");
			return;
		}
		if (values[3] > 1) {
			alert("只能选择一条人员记录！");
			return;
		}
		var name = values[1] + "@" + values[2];

		var win = odin.ext.getCmp('simpleExpWin');
		win.setTitle('导入窗口');
		odin.showWindowWithSrc('simpleExpWin', contextPath
				+ "/pages/search/EditFile.jsp?flag=1&uuid=" + values[0]
				+ "&uname=" + name);

	}

	//查看/删除附件
	function modifyFilePer() {

		var value = getValueForPerson();
		var values = value.split("@");

		if (values[0] == '') {
			alert("请选择人员记录！");
			return;
		}
		if (values[3] > 1) {
			alert("只能选择一条人员记录！");
			return;
		}

		radow.doEvent("modifyAttach", values[0] + "@1");

	}

	function ml(a0000, allName) {
		doOpenPupWin(encodeURI(encodeURI(contextPath
				+ "/pages/exportexcel/ExpTempDjbWindow.jsp?a0000=" + a0000
				+ "&a0101=" + allName)), "下载文件", 600, 400);
	}

	function downLoadTmp() {
		var dw = document.getElementById("dw").value;
		var gbs = document.getElementById("gbs").value;
		var sys = document.getElementById("sys").value;
		var djs = document.getElementById("djs").value;
		var zhs = document.getElementById("zhs").value;

		var djgridString = document.getElementById("djgridString").value;
		var zhgridString = document.getElementById("zhgridString").value;
		if(typeof(djgridString)!='undefined'&&djgridString!=''){
			if(zhs!=''&&zhs!=0){
				doOpenPupWin(encodeURI(encodeURI(contextPath + "/pages/exportexcel/ExpWin.jsp?dw="+dw+"&gbs="+gbs+"&sys="+sys+"&djs="+djs+"&zhs="+zhs+"&djgridString="+djgridString+"&zhgridString="+zhgridString+"&tmpType=['3', '公务员登记备案表'],['4', '参照公务员法管理机关（单位）公务员登记备案表']")),
			 			"下载文件", 500, 160);
			}else{
				doOpenPupWin(encodeURI(encodeURI(contextPath + "/pages/exportexcel/ExpWin.jsp?dw="+dw+"&gbs="+gbs+"&sys="+sys+"&djs="+djs+"&zhs="+zhs+"&djgridString="+djgridString+"&zhgridString="+zhgridString+"&tmpType=['13', '公务员登记备案表'],['14', '参照公务员法管理机关（单位）公务员登记备案表']")),
			 			"下载文件", 500, 160);
			}
		}else{
			alert("没有登记任何人员不能导出！");
		}

	}

	<odin:menu property="attachPerson">
	<odin:menuItem text="上传附件" property="editFilePer" handler="editFilePer" ></odin:menuItem>
	<odin:menuItem text="查看/删除附件" property="modifyFilePerBtn" handler="modifyFilePer" isLast="true" ></odin:menuItem>
	</odin:menu>

	<odin:menu property="DJB">
	<odin:menuItem text="生成登记表" property="createDjb" handler="createExcelTemp" ></odin:menuItem>
	<odin:menuItem text="查看登记表" property="getDjb" handler="expExcelTemp" isLast="true" ></odin:menuItem>
	</odin:menu>

	function onHide(id, e) {
		Ext.getCmp(id).on('hide', function() {
			eval(e);
		});
	}
</script>