<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<html>
<head>
<style type="text/css">
</style>
</head>
<body style="margin:0;padding:0">
<div id="groupTreeContent" style="width: 100%;margin:30px 0px 0px 10px;">
	<odin:groupBox title="������" property="ggBox">
		<table>
			<tr>
			<td><odin:dateEdit property="start" readonly="false" size="20" label="��ʼʱ��"></odin:dateEdit></td>
			<td width="50px"></td>
			<td><odin:dateEdit property="end" readonly="false" size="20" label="����ʱ��" ></odin:dateEdit></td>
			<td width="50px"></td>
			<td><odin:button text="��ѯ" property="query" handler="func"></odin:button></td>
			</tr>
		</table>
	</odin:groupBox>
	<odin:editgrid property="list" title="��־����Ϣ" isFirstLoadData="false" url="/" height="450" bbarId="pageToolBar" pageSize="20" remoteSort="true" 
	autoFill="true">
	<odin:gridJsonDataModel>
			<odin:gridDataCol name="interface_log_id" />
			<odin:gridDataCol name="interface_config_id"/>
			<odin:gridDataCol name="interface_config_name"/>
			<odin:gridDataCol name="interface_script_id" />
			<odin:gridDataCol name="interface_script_name"/>
			<odin:gridDataCol name="interface_exec_sql" />
			<odin:gridDataCol name="interface_original_sql" />
			<odin:gridDataCol name="interface_requesttime" />
			<odin:gridDataCol name="interface_access_ip" />
			<odin:gridDataCol name="execute_state_id" />
			<odin:gridDataCol name="operate_username" />
			<odin:gridDataCol name="interface_comments" isLast="true"/>
		</odin:gridJsonDataModel>
		<odin:gridColumnModel>
			<odin:gridRowNumColumn></odin:gridRowNumColumn>
			<odin:gridColumn dataIndex="interface_log_id" hidden="true" header="�ӿ���־����"/>
			<odin:gridColumn dataIndex="interface_config_id" width="110" header="�ӿڷ�������" align="center"/>
			<odin:gridColumn dataIndex="interface_config_name" width="110" header="�ӿڷ�������" align="center"/>
			<odin:gridColumn dataIndex="interface_script_id" hidden="true" header="�ӿڽű�����" align="center"/>
			<odin:gridColumn dataIndex="interface_script_name" width="110" header="�ӿڽű�����" align="center"/>
			<odin:gridColumn dataIndex="interface_exec_sql" hidden="true" header="���ݷ��ʽӿڽű�ִ��SQL���"  align="center"/>
			<odin:gridColumn dataIndex="interface_original_sql" hidden="true" header="���ݷ��ʽӿڽű�ԭʼSQL���" align="center"/>
			<odin:gridColumn dataIndex="interface_requesttime" width="100" header="�ӿڷ���ʱ��" align="center"/>
			<odin:gridColumn dataIndex="interface_access_ip" width="130" header="�ӿڷ�����ԴIP��ַ" align="center"/>
			<odin:gridColumn dataIndex="execute_state_id" header="ִ��״̬����" hidden="true" align="center"/>
			<odin:gridColumn dataIndex="operate_username" header="�����û�" width="80" align="center"/>
			<odin:gridColumn dataIndex="interface_comments" width="130" header="��ע��Ϣ" align="center" isLast="true"/>
		</odin:gridColumnModel>
	</odin:editgrid>
</div>
<script type="text/javascript">
	function func() {
		radow.doEvent("query");
	}
</script>
</body>	
</html>



