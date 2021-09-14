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
	<odin:buttonForToolBar text="�ǼǱ����" id="DJBBtn" menu="DJB"
		icon="images/icon_photodesk.gif" cls="x-btn-text-icon"></odin:buttonForToolBar>
	<odin:buttonForToolBar text="����������" id="getSheet" handler="getSheetBab"
		icon="images/icon_photodesk.gif" cls="x-btn-text-icon" />
	<odin:buttonForToolBar text="�༭���˸���" id="uploadFileForPerson"
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
	<odin:editgrid property="peopleInfoGrid" title="�Ǽ���Ա����"
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
			<odin:gridColumn dataIndex="a0101" width="110" header="����"
				align="center" />
			<odin:gridEditColumn2 dataIndex="a0104" width="100" header="�Ա�"
				align="center" editor="select" edited="false" codeType="GB2261" />
			<odin:gridColumn dataIndex="a0107" width="130" header="��������"
				align="center" editor="text" edited="false" />
			<odin:gridEditColumn2 dataIndex="a0117" width="100" header="����"
				align="center" editor="select" edited="false" codeType="GB3304" />
			<odin:gridEditColumn2 dataIndex="a0141" width="130" header="������ò"
				align="center" editor="select" edited="false" codeType="GB4762" />
			<odin:gridColumn dataIndex="a0192" width="130" header="������λ��ְ��"
				align="center" isLast="true" />
		</odin:gridColumnModel>
		<odin:gridJsonData>
	        {
                   data:[]
            }
            </odin:gridJsonData>
	</odin:editgrid>
</form>

<odin:window src="/blank.htm" id="simpleExpWin" width="560" height="350" maximizable="false" title="��������" modal="true"></odin:window>
<odin:window src="/blank.htm" id="modifyFileWindow" width="560" height="300" maximizable="false" title="�鿴/ɾ����������" modal="true"></odin:window>

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

	//-----------------------------------------------�Ը��˽��в���--------------------------------------	
	//���ɱ����ʱ���
	function addCbdBtn() {
		var a0000 = '';
		var a0101 = '';
		var gridId = "peopleInfoGrid";
		if (!Ext.getCmp(gridId)) {
			odin.error("Ҫ������grid�����ڣ�gridId=" + gridId);
			return;
		}
		var grid = Ext.getCmp(gridId);
		var store = grid.getStore();
		if (store.getCount() == 0) {
			odin.error("δѡ����Ա��");
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
			odin.error("δѡ����Ա��");
			return;
		}

		a0101 = a0101.substring(0, a0101.length - 1);
		a0000 = a0000.substring(0, a0000.length - 1);
		radow.doEvent('addCBD', a0000 + "@" + a0101);

	}

	function getCheckListForPerson() {
		radow.doEvent('getCheckListForPerson');
	}

	//��ȡҳ������_����
	function getValueForPerson() {

		var gridId = "peopleInfoGrid";
		var grid = Ext.getCmp(gridId);
		var store = grid.store;
		var count = 0;
		var a0000 = '';//��Ա���
		var a0101 = '';//��Ա����
		var a0184 = '';//��Ա���֤��
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
	 * �����ǼǱ��
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
			odin.error("Ҫ������grid�����ڣ�gridId=" + gridId);
			return;
		}
		var grid = Ext.getCmp(gridId);
		var store = grid.getStore();
		if (store.getCount() == 0) {
			odin.error("û��Ҫ���������ݣ�");
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
			odin.error("��ѡ��Ҫ�������У�");
			return;
		}

		a0101 = a0101.substring(0, a0101.length - 1);
		a0000 = a0000.substring(0, a0000.length - 1);
		radow.doEvent('checkPer', a0000 + "@" + a0101);

	}

	/**
	 * �����������
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
			odin.error("Ҫ������grid�����ڣ�gridId=" + gridId);
			return;
		}
		var grid = Ext.getCmp(gridId);
		var store = grid.getStore();
		if (store.getCount() == 0) {
			odin.error("û��Ҫ���������ݣ�");
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
			odin.error("��ѡ��Ҫ�������У�");
			return;
		}

		a0101 = a0101.substring(0, a0101.length - 1);
		a0000 = a0000.substring(0, a0000.length - 1);
		radow.doEvent('getSheet', a0000 + "@" + a0101);

	}

	/**
	 * �����ǼǱ��
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
			odin.error("Ҫ������grid�����ڣ�gridId=" + gridId);
			return;
		}
		var grid = Ext.getCmp(gridId);
		var store = grid.getStore();
		if (store.getCount() == 0) {
			odin.error("û��Ҫ���������ݣ�");
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
			odin.error("��ѡ��Ҫ�������У�");
			return;
		}
		a0101 = a0101.substring(0, a0101.length - 1);
		a0000 = a0000.substring(0, a0000.length - 1);
		doOpenPupWin(encodeURI(encodeURI(contextPath
				+ "/pages/exportexcel/djbLoad.jsp?a0000=" + a0000 + "&a0101="
				+ a0101 + "&download=true")), "�����ļ�", 600, 200);
	}

	//�ǼǱ�༭����
	function editFilePer() {

		var value = getValueForPerson();
		var values = value.split("@");

		if (values[0] == '') {
			alert("��ѡ��Ҫ�ϴ���������Ա��¼��");
			return;
		}
		if (values[3] > 1) {
			alert("ֻ��ѡ��һ����Ա��¼��");
			return;
		}
		var name = values[1] + "@" + values[2];

		var win = odin.ext.getCmp('simpleExpWin');
		win.setTitle('���봰��');
		odin.showWindowWithSrc('simpleExpWin', contextPath
				+ "/pages/search/EditFile.jsp?flag=1&uuid=" + values[0]
				+ "&uname=" + name);

	}

	//�鿴/ɾ������
	function modifyFilePer() {

		var value = getValueForPerson();
		var values = value.split("@");

		if (values[0] == '') {
			alert("��ѡ����Ա��¼��");
			return;
		}
		if (values[3] > 1) {
			alert("ֻ��ѡ��һ����Ա��¼��");
			return;
		}

		radow.doEvent("modifyAttach", values[0] + "@1");

	}

	function ml(a0000, allName) {
		doOpenPupWin(encodeURI(encodeURI(contextPath
				+ "/pages/exportexcel/ExpTempDjbWindow.jsp?a0000=" + a0000
				+ "&a0101=" + allName)), "�����ļ�", 600, 400);
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
				doOpenPupWin(encodeURI(encodeURI(contextPath + "/pages/exportexcel/ExpWin.jsp?dw="+dw+"&gbs="+gbs+"&sys="+sys+"&djs="+djs+"&zhs="+zhs+"&djgridString="+djgridString+"&zhgridString="+zhgridString+"&tmpType=['3', '����Ա�ǼǱ�����'],['4', '���չ���Ա��������أ���λ������Ա�ǼǱ�����']")),
			 			"�����ļ�", 500, 160);
			}else{
				doOpenPupWin(encodeURI(encodeURI(contextPath + "/pages/exportexcel/ExpWin.jsp?dw="+dw+"&gbs="+gbs+"&sys="+sys+"&djs="+djs+"&zhs="+zhs+"&djgridString="+djgridString+"&zhgridString="+zhgridString+"&tmpType=['13', '����Ա�ǼǱ�����'],['14', '���չ���Ա��������أ���λ������Ա�ǼǱ�����']")),
			 			"�����ļ�", 500, 160);
			}
		}else{
			alert("û�еǼ��κ���Ա���ܵ�����");
		}

	}

	<odin:menu property="attachPerson">
	<odin:menuItem text="�ϴ�����" property="editFilePer" handler="editFilePer" ></odin:menuItem>
	<odin:menuItem text="�鿴/ɾ������" property="modifyFilePerBtn" handler="modifyFilePer" isLast="true" ></odin:menuItem>
	</odin:menu>

	<odin:menu property="DJB">
	<odin:menuItem text="���ɵǼǱ�" property="createDjb" handler="createExcelTemp" ></odin:menuItem>
	<odin:menuItem text="�鿴�ǼǱ�" property="getDjb" handler="expExcelTemp" isLast="true" ></odin:menuItem>
	</odin:menu>

	function onHide(id, e) {
		Ext.getCmp(id).on('hide', function() {
			eval(e);
		});
	}
</script>