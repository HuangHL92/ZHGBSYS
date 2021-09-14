<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@page import="com.insigma.siis.local.business.sysmanager.ZWHZYQ_001_007.ZWHZYQ_001_007_BS"%>
<%@page import="com.insigma.siis.local.business.entity.InterfaceScript"%>
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8,chrome=1">
<%
	String nodeId = request.getParameter("nodeId");
	String opmode = request.getParameter("opmode");
	String preNodeId = request.getParameter("preNodeId");

	//�ű���ֵ���ں�̨��ֵ�����������б䶯
	String sqlText = "";
	ZWHZYQ_001_007_BS bs = new ZWHZYQ_001_007_BS();
	String[] nodeId_s = nodeId.split("_");
	if(!"NEW".equals(opmode)){
		InterfaceScript script = bs.getScriptByIsn(nodeId_s[1]);
		sqlText = script.getInterfaceScriptSql();
	}
%>
<script src="<%=request.getContextPath()%>/editarea/edit_area/edit_area_full.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
	var nodeId = '<%=nodeId%>';
	var opmode = '<%=opmode%>';
	var preNodeId = '<%=preNodeId%>';
	editAreaLoader.init({
		id : "interfaceScriptSql", 
		start_highlight : true, 
		cursor_position : "begin", 
		allow_toggle : false, 
		language : "zh", 
		syntax : "sql", 
		font_size : "10", 
		begin_toolbar : "DYear,|,DMonth,|,DDay,|,SYear,|,SMonth,|", 
		toolbar : "search,|,go_to_line, |, undo,|, redo", 
		end_toolbar : "|, copy", 
		show_line_colors : true, 
		replace_tab_by_spaces : 4
	});
	 
	function extactColConfHrefGridEdit(value, params, record, rowIndex, colIndex, ds){
		if(value == null){
			return;
		}
		return '<a href="javascript:void(0);" onclick="radow.doEvent(\'update\',\'' 
				+ record.get('extract_scheme_id') + ',' 
				+ record.get('extract_script_id') + ',' 
				+ record.get('extract_code_colname') + '\');">�༭</a>';
	 			
}
</script>

<odin:toolBar property="btnToolBar" applyTo="toolBarDIV">
	<odin:fill/>
	<odin:buttonForToolBar text="���Խű�" id="testScript" icon="images/icon/save.gif" cls="x-btn-text-icon"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="ɾ��" id="delBtn" icon="images/icon/delete.gif" cls="x-btn-text-icon"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="����" id="save" icon="images/icon/save.gif" cls="x-btn-text-icon" handler="save" isLast="true"/>
</odin:toolBar>
<span style="position: absolute; top: 7; left: 8; z-index: 1000; font-size: 12px; font-weight: normal; font-family: Arial" id="gridT"></span>
<div id="toolBarDIV"></div>
<div id="alldiv" style="overflow-x:hidden;overflow-y:scroll;width:100%;height:100%;">
<odin:hidden property="nodeId"/>
<odin:hidden property="opmode" />
<odin:hidden property="preNodeId"/>
<odin:hidden property="interfaceConfigId"/>
<odin:hidden property="sqlText"/>
<odin:hidden property="preAvailabilityStateId"/>
<odin:hidden property="preInterfaceScriptCreateDate"/>
	<table border="0" cellpadding="0" cellspacing="0" style="margin-top: 5">
		<tr>
			<td>
				<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="20"><odin:textEdit property="interfaceScriptId" label="�ӿڽű�����" width="160" required="true" maxlength="100" disabled="true"/></td>
						<td width="88"></td>
						<td><odin:numberEdit property="interfaceScriptSequence" label="�ӿڽű����" width="50" required="true" maxlength="5"/></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="20"><odin:textEdit property="interfaceScriptName" label="�ӿڽű�����" width="213" required="true" maxlength="100" colspan="5"/></td>
						<td width="55"></td>
						<td><!-- <span style="font-size:12px">�Ƿ�ɱ༭</span> --></td>
						<td width="2"></td>
						<td>
							<!--<input type="checkbox" id ="isIsEdit" title="�Ƿ�ɱ༭" onclick="clickcond();" checked="checked"/>-->
							<odin:hidden property="interfaceScriptIsedit" value="true"/>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="26"><odin:textEdit property="describe4" label="�ӿڽű�����" width="0"/></td>
						<td><odin:textarea property="interfaceScriptDesc" cols="88" rows="3" maxlength="4000"/></td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<hr width="572" size="1" color="#D2E0F1" style="border: 0;margin-left: 2" align="left"/>
	<table border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td>
				<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="4"></td>
						<td width="32"></td>
						<td>
						<odin:select property="interfaceScriptStateId" disabled="false" editor="false" label="��Ч��״̬" width="80" data="['0', '��Ч'],['1', '��Ч']">
                        </odin:select>
						</td>
						<td width="80"></td>
						<odin:hidden property="interfaceScriptCreateUser"/>
						<td>
						<odin:textEdit property="interfaceScriptCreateUserName" label="������Ա" width="90" disabled="true"/>
						<td width="25"></td>
						<odin:hidden property="interfaceScriptChangeUser"/>
						<odin:textEdit property="interfaceScriptChangeUserName" label="����޸���Ա" width="90" disabled="true"/>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="48"></td>
						<odin:textEdit property="interfaceScriptCreateDate" label="��������" width="123" disabled="true"></odin:textEdit>
						<td width="13"></td>
						<odin:textEdit property="interfaceScriptChangeDate" label="����޸�����" width="123" disabled="true"></odin:textEdit>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<hr width="572" size="1" color="#D2E0F1" style="border: 0;margin-left: 2" align="left"/>
	<table>
		<tr>
		<td width="48">
			<odin:textEdit property="targetTableName" label="���ݼ�" width="180" required="true" validator="checkTargetTableName" invalidText="ֻ���������Сд��ĸ�����֡���_����" maxlength="200"/>
		</td>
		</tr>
	</table>
	<table border="0" cellpadding="0" cellspacing="0" width="100%" style="margin-top: 5px;" >
		<tr>
			<td align="left" valign="top">
				<odin:tab id="tab" width="572" tabchange="tabChange">
					<odin:tabModel>
						<odin:tabItem title="ִ�нű�" id="tab1" isLast="true"/>
					</odin:tabModel>
					<odin:tabCont itemIndex="tab1" className="">
						<table border="0" cellpadding="0" cellspacing="0" width="100%" style="margin-top: 6px">
							<tr>
								<td colspan="5"><textarea id="interfaceScriptSql"
										 style="width: 570px; height: 210px" name="interfaceScriptSql"><%=sqlText%></textarea></td>
							</tr>
							<tr><td width="524"></td><td><!--<odin:button text="�༭�ű�" property="editScript"/>--></td></tr>
						</table>
					</odin:tabCont>
				</odin:tab>
			</td>
		</tr>
	</table>
<!-- </div>	 -->
<!-- ����ת������ʼ -->
<odin:toolBar property="btnToolBar1">
	<odin:textForToolBar text=""/>
	<odin:fill/>
	<odin:buttonForToolBar text="�½�" id="addCodeConv" icon="images/icon/add.gif" cls="x-btn-text-icon" handler="add"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="ɾ��" id="del" icon="images/icon/delete.gif" cls="x-btn-text-icon"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="ˢ��" id="refreshBtn" icon="images/icon/refresh.png" cls="x-btn-text-icon" handler="refresh"/>
</odin:toolBar>
<odin:window src="" modal="true" id="ScriptEdit" width="818" height="460" title="�ű��༭" closable="false" maximizable="false"></odin:window>
<odin:window src="" modal="true" id="ScriptTest" width="750" height="415" title="�ű�����" closable="false" maximizable="false"></odin:window>

<!-- ����ת��������� -->
</div>
<script type="text/javascript">
	Ext.onReady(function(){
		document.all.nodeId.value = nodeId;
		document.all.opmode.value = opmode;
		document.all.preNodeId.value = preNodeId;
		radow.doEvent("queryObj");
		aotuoAlldivHight();
	}, this, {
		delay : 500
	});

	function aotuoAlldivHight(){
		document.getElementById("alldiv").style.height = document.body.clientHeight - 30;
	}
	
	function checkTargetTableName(){
		var targetTableName = document.all.targetTableName.value; 
		var pattern = /^([A-Z|a-z|0-9|\_]+)*$/;
		flag = pattern.test(targetTableName);
		if(!flag){
			return false;
		}else{
			return true;
		}
	}
	
	//�Ƿ�ɱ༭��ѡ�򵥻��¼�
	function clickcond(){
		var isIsEdit = document.all.isIsEdit.checked;
		if(isIsEdit){
			document.all.interfaceScriptIsedit.value = "true";
			radow.doEvent("disabledAll", 'true');
		}else{
			document.all.interfaceScriptIsedit.value = "false";
			radow.doEvent("disabledAll", 'false');
		}
	}
	
	//�ű��༭��ȡֵ  
	function getSqlValue(){
		document.all.sqlText.value = editAreaLoader.getValue('interfaceScriptSql');
	}
	
	function setValue(sqlvalue){
		sqlvalue = sqlvalue.replace(/&quot;/g, "'");//����Ҫ�Ľ��
		editAreaLoader.setValue('interfaceScriptSql', sqlvalue);
	}
	
	function save(){
		getSqlValue();
		radow.doEvent('save');
	}
	//�½�����ת��
	function add(){
		radow.doEvent('update', document.all.nodeId.value);
	}
	
	//tab�л��¼�
	function tabChange(){
		var tabs=Ext.getCmp('tab');
		var tab=tabs.getActiveTab();
		var isIsEdit = document.all.isIsEdit.checked;//
		if('tab2' == tab.id){
			if('NEW' == document.all.opmode.value || !isIsEdit){
				Ext.getCmp('addCodeConv').disable();
				Ext.getCmp('del').disable();
				Ext.getCmp('refreshBtn').disable();
			}else{
				Ext.getCmp('addCodeConv').enable();
				Ext.getCmp('del').enable();
				Ext.getCmp('refreshBtn').enable();
			}	
			refresh();
		}	
	}
	
	//ˢ�´���ת���б�
	function refresh(){
		radow.doEvent('list.dogridquery');		    	
	}
	
	function disableEdit(){
		document.getElementById('isIsEdit').disabled=true;
	}
	
	function enableEdit(){
		document.getElementById('isIsEdit').disabled=false;
	}
</script>