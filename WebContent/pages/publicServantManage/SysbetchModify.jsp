<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@page import="com.lbs.cp.util.SysManagerUtil"%>
<%@include file="/comOpenWinInit.jsp" %>
<%@page
	import="com.insigma.siis.local.business.helperUtil.SysManagerUtils"%>
<%@page import="com.insigma.odin.framework.util.commform.BuildUtil.ItemValue"%>
<script type="text/javascript" src="basejs/helperUtil.js"></script>

<style>
.x-form-item {
	width: 100%;
	height: 100%;
	margin: 0px 0px 0px 0px;
	padding: 0px 0px 0px 0px;
}
</style>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript">


	function link2tab(id) {
		window.realParent.tabs.activate(id);
	}
	/**ɾ��ѡ����  ����**/
	function delRow(obj) {
		var grid = odin.ext.getCmp(obj.initialConfig.cls);
		var sm = grid.getSelectionModel();
		var selections = sm.getSelections();
		var store = grid.store;
		for (var i = 0; i < selections.length; i++) {
			var selected = selections[i];
			store.remove(selected);
		}
		grid.view.refresh();
	}
	/**�����**/
	function addRow(obj) {
		radow.addGridEmptyRow(obj.initialConfig.cls, 0);
	}
	/**ɾ��ѡ����  ����**/
	function delCheckedRow(obj) {
		var grid = odin.ext.getCmp(obj.initialConfig.cls);
		var arrayObj = new Array();
		;
		var store = grid.store;
		var i = store.getCount() - 1;
		if (store.getCount() > 0) {
			for (var i = store.getCount() - 1; i >= 0; i--) {
				var ck = grid.getStore().getAt(i).get("logchecked");
				if (ck == true) {
					store.remove(grid.getStore().getAt(i));
				}
			}
		}
	}

	function a1151Change() {
		
		var a1151Type = document.getElementById('a1151Type').value;

		/* if ("0" == a1151Type) {
			selecteWinEnable('a1517');
		} else {
			selecteWinDisable('a1517');
		} */
		radow.doEvent('change');
	}

	function setParentValue2(obj) {
		var id = obj.id;
		var value = obj.value;
		if (id.indexOf('_combo') != -1) {
			id = id.split('_combo')[0];
			if (value == '') {
				document.getElementById(id).value = '';
				onA0160Change();
			}
			value = document.getElementById(id).value
		}
		realParent.document.getElementById(id).value = value;
	}
	//����
	function setParentA0120Value(record, index) {
		realParent.document.getElementById('a0111').value = record.data.key;
	}
	//������
	function setParentA0120Value(record, index) {
		realParent.document.getElementById('a0114').value = record.data.key;
	}

	function setA1404aValue(record, index) {//��������
		Ext.getCmp('a1404a').setValue(record.data.value);
	}
	
	function setA0216aValue(record,index){//ְ����
		Ext.getCmp('a0216a').setValue(record.data.value);
	}
	//�������
	function yearChange() {
		var now = new Date();
		var year = now.getFullYear();
		var yearList = document.getElementById("a1521");
		for (var i = 0; i <= 50; i++) {
			year = year - 1;
			yearList.options[i] = new Option(year, year);
		}
	}

	function count() {
		radow.doEvent('count');
	}

	function count1() {
		radow.doEvent('count1');
	}
</script>
	<%-----------------------------��λ������Ϣ-----------------------------------------------------%>
	<div id="floatToolDiv7"></div>
	<odin:toolBar property="floatToolBar7" applyTo="floatToolDiv7">
		<odin:fill />
		<odin:buttonForToolBar text="ִ���޸�" id="save7" isLast="true"
			icon="images/save.gif" cls="x-btn-text-icon" />
	</odin:toolBar>
	<odin:groupBox property="s11" title="����������Ϣ�����޸�">
		<table cellspacing="2" width="98%" align="center">
			<tr>
				<label id="bz2" style="font-size: 12; color: red">ע����������޸�</label>
			</tr>
			<tr>
				<tags:PublicTextIconEdit property="b0124" label="��λ������ϵ" codetype="ZB87" maxlength="8" readonly="true"/>
				<tags:PublicTextIconEdit property="b0127" label="��λ����" codetype="ZB03" maxlength="8" readonly="true"/>
				<tags:PublicTextIconEdit property="b0131" label="��λ�������" codetype="ZB04" maxlength="8" readonly="true"/>
			</tr>
			<tr>
				<odin:select2 property="b0194" tpl="<%=ItemValue.tpl_Value%>" data="['1','���˵�λ'],['2','�������'],['3','��������']"  label="���˵�λ��ʶ"></odin:select2>
			</tr>
		</table>
	</odin:groupBox>
