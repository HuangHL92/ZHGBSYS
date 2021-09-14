<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%
    //nodeId代表本节点的Id,preNodeId代表父节点的Id,opmode代表该节点是新建还是修改
	String nodeId = request.getParameter("nodeId");
	String opmode = request.getParameter("opmode");
	String preNodeId = request.getParameter("preNodeId");
%>
<script type="text/javascript">
	var nodeId='<%=nodeId%>';
	var opmode='<%=opmode%>';
	var preNodeId='<%=preNodeId%>';
</script>
<odin:toolBar property="btnToolBar" applyTo="btnToolDiv">
	<odin:fill/>
	<odin:buttonForToolBar text="删除" id="delBtn" icon="images/icon/delete.gif" cls="x-btn-text-icon"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="保存" id="save" icon="images/icon/save.gif" cls="x-btn-text-icon" isLast="true"/>
</odin:toolBar>
<odin:floatDiv property="btnToolDiv" width="100%">
	<table border="0" cellpadding="0" cellspacing="0">
		<tr>
			<td height="30"></td>
		</tr>
	</table>
</odin:floatDiv>
<%--overflow-y:hidden '表示没有垂直滚动条 overflow-x:hidden '表示没有水平滚动条 --%>
<div id="alldiv" style="width:100%;height:100%;">
<odin:panel contentEl="editView" property="editPanel"></odin:panel>
<odin:hidden property="nodeId"/>
<odin:hidden property="opmode"/>
<odin:hidden property="preNodeId"/>
<div id="editView">
	<table border="0" cellpadding="0" cellspacing="0" style="margin-top:5">
		<tr>
			<td>
				<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="24"><odin:textEdit property="interfaceConfigId" label="接口方案编码" width="160" required="true" validator="checkInterfaceConfigId" invalidText="只允许输入大小写字母、数字、‘_’。"/></td>
						<td width="88"></td>
						<td><odin:numberEdit property="interfaceConfigSequence" required="true" label="接口方案序号" width="50" maxlength="5"/></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="24"><odin:textEdit property="interfaceConfigName" required="true" label="接口方案名称" width="213" maxlength="100"/></td>
						<td width="55"></td>
						<td><!-- <span style="font-size:12px">是否可编辑</span> --></td>
						<td width="2"></td>
						<td>
							<!--<input type="checkbox" id ="icIsEdit" title="是否可编辑" onclick="clickcond();" checked="checked"/>-->
							<odin:hidden property="interfaceConfigIsedit" value="true"/>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="30"><odin:textEdit property="describe4" label="接口方案描述" width="0"/></td>
						<td><odin:textarea property="interfaceConfigDesc" cols="88" rows="3" maxlength="4000"/></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td align="left" colspan="6">
				<hr width="574" size="2" color="#D2E0F1" style="border:0"/>
			</td>
		</tr>
		<tr>
			<td>
				<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="40">
						<odin:select property="interfaceConfigStateId" disabled="false" editor="false" label="有效性状态" width="80" data="['0', '无效'],['1', '有效']">
                        </odin:select>
						</td>
						<td width="80"></td>
						<odin:hidden property="interfaceConfigCreateUser"/>
						<td>
						<odin:textEdit property="interfaceConfigCreateUserName" label="创建人员" width="80" disabled="true"/>
						</td>
						<td width="45">
						<odin:textEdit property="interfaceConfigChangeUserName" label="最后修改人员" width="80" disabled="true"/>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<table border="0" cellpadding="0" cellspacing="0">
					<tr>
						<td width="52"></td>
						<td><odin:textEdit property="interfaceConfigCreateDate" label="创建日期" width="123" disabled="true"/></td>
						<td width="13"></td>
						<td><odin:textEdit property="interfaceConfigChangeDate" label="最后修改日期" width="123" disabled="true"/></td>
						<td width="26"></td>
						<odin:hidden property="interfaceConfigChangeUser"/>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				<div id="gridDiv_list"></div>
			</td>
		</tr>
	</table>
</div>
<!-- 代码关系区域开始 -->
<odin:toolBar property="btnToolBar1">
	<odin:textForToolBar text=""/>
	<odin:fill/>
	<odin:buttonForToolBar text="新建" id="addParam" icon="images/add.gif" cls="x-btn-text-icon" handler="add"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="删除" id="delParam" icon="images/icon/delete.gif" cls="x-btn-text-icon"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="刷新" id="refreshBtn" icon="images/icon/refresh.png" cls="x-btn-text-icon" handler="refresh" isLast="true"/>
</odin:toolBar>
<odin:gridSelectColJs name="interface_parameter_type" selectData="['1','String'],['3','Date'],['2','Double']"></odin:gridSelectColJs>
<odin:editgrid property="list" applyTo="gridDiv_list"
	topBarId="btnToolBar1" isFirstLoadData="false" url="/" sm="checkbox" autoFill="true">
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="selected"/>
		<odin:gridDataCol name="interface_parameter_sequence"/>
		<odin:gridDataCol name="interface_config_id"/>
		<odin:gridDataCol name="interface_parameter_name"/>
		<odin:gridDataCol name="interface_parameter_type"/>
		<odin:gridDataCol name="interface_parameter_desc" isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn/>
		<odin:gridColumn header="selectall" gridName="list" dataIndex="selected" edited="true" editor="checkbox" width="45"/>
		<odin:gridEditColumn header="序号" dataIndex="interface_parameter_sequence" edited="false" editor="text" width="50" align="center"/>
		<odin:gridEditColumn header="方案编码" dataIndex="interface_config_id" edited="false" editor="text" hidden="true"/>
		<odin:gridEditColumn header="参数编码" dataIndex="interface_parameter_name" edited="true" editor="text" width="136" align="center"/>
		<odin:gridEditColumn header="参数类型" dataIndex="interface_parameter_type" edited="true" editor="select" width="136" align="center" selectData="['1','String'],['3','Date'],['2','Double']"/>
		<odin:gridEditColumn header="参数名称" dataIndex="interface_parameter_desc" edited="true" editor="text" width="136" align="center" isLast="true"/>
	</odin:gridColumnModel>
</odin:editgrid>
<!-- 代码转换区域结束 -->

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

	//是否可编辑复选框单击事件
	function clickcond(){
		var icIsEdit = document.all.icIsEdit.checked;
		if(icIsEdit){
			document.all.interfaceConfigIsedit.value = "true";
			radow.doEvent("disabledAll", 'true');
		}else{
			document.all.interfaceConfigIsedit.value = "false";
			radow.doEvent("disabledAll", 'false');
		}
	}
	//检测接口方案编码是否是只包含字母数字和下划线。
	function checkInterfaceConfigId(){
		var interfaceConfigId = document.all.interfaceConfigId.value; 
		var pattern = /^([A-Z|a-z|0-9|\_]+)*$/;
		flag = pattern.test(interfaceConfigId);
		if(!flag){
			return false;
		}else{
			return true;
		}
	}	
	//tab切换事件
	function tabChange(){
		var tabs = Ext.getCmp('tab');
		var tab = tabs.getActiveTab();
		var icIsEdit = document.all.icIsEdit.checked;//
		if('tab2' == tab.id){
			if('NEW' == document.all.opmode.value || !icIsEdit){
				Ext.getCmp('addParam').disable();
				Ext.getCmp('delParam').disable();
				Ext.getCmp('refreshBtn').disable();
			}else{
				Ext.getCmp('addParam').enable();
				Ext.getCmp('delParam').enable();
				Ext.getCmp('refreshBtn').enable();
			}
			refresh();
		}
	}
	
	function add(){
		var gridStore = Ext.getCmp("list").store;
		var len = gridStore.getCount();
		odin.addGridRowData('list', {interface_config_id : '', interface_parameter_sequence : len + 1, 
			interface_parameter_name : '', interface_parameter_type : '', interface_parameter_desc : ''});
		haveChanage();
	}
	
	function haveChanage(){
		document.all.isEdit.value = "1";
	}
	
	//刷新方案参数列表
	function refresh(){
		radow.doEvent('list.dogridquery');
	}
		
	function disableEdit(){
		document.getElementById('icIsEdit').disabled=true;
	}
	
	function enableEdit(){
		document.getElementById('icIsEdit').disabled=false;
	}
</script>