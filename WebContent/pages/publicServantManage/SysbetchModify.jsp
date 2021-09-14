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
	/**删除选中行  单条**/
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
	/**添加行**/
	function addRow(obj) {
		radow.addGridEmptyRow(obj.initialConfig.cls, 0);
	}
	/**删除选中行  多条**/
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
	//籍贯
	function setParentA0120Value(record, index) {
		realParent.document.getElementById('a0111').value = record.data.key;
	}
	//出生地
	function setParentA0120Value(record, index) {
		realParent.document.getElementById('a0114').value = record.data.key;
	}

	function setA1404aValue(record, index) {//奖惩名称
		Ext.getCmp('a1404a').setValue(record.data.value);
	}
	
	function setA0216aValue(record,index){//职务简称
		Ext.getCmp('a0216a').setValue(record.data.value);
	}
	//考核年度
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
	<%-----------------------------单位基本信息-----------------------------------------------------%>
	<div id="floatToolDiv7"></div>
	<odin:toolBar property="floatToolBar7" applyTo="floatToolDiv7">
		<odin:fill />
		<odin:buttonForToolBar text="执行修改" id="save7" isLast="true"
			icon="images/save.gif" cls="x-btn-text-icon" />
	</odin:toolBar>
	<odin:groupBox property="s11" title="机构基本信息批量修改">
		<table cellspacing="2" width="98%" align="center">
			<tr>
				<label id="bz2" style="font-size: 12; color: red">注：空项将不被修改</label>
			</tr>
			<tr>
				<tags:PublicTextIconEdit property="b0124" label="单位隶属关系" codetype="ZB87" maxlength="8" readonly="true"/>
				<tags:PublicTextIconEdit property="b0127" label="单位级别" codetype="ZB03" maxlength="8" readonly="true"/>
				<tags:PublicTextIconEdit property="b0131" label="单位性质类别" codetype="ZB04" maxlength="8" readonly="true"/>
			</tr>
			<tr>
				<odin:select2 property="b0194" tpl="<%=ItemValue.tpl_Value%>" data="['1','法人单位'],['2','内设机构'],['3','机构分组']"  label="法人单位标识"></odin:select2>
			</tr>
		</table>
	</odin:groupBox>
