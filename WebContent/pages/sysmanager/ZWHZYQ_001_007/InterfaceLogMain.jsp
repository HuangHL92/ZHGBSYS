<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<html>
<head>
<style type="text/css">
</style>
</head>
<body style="margin:0;padding:0">
<div id="groupTreeContent" style="width: 100%;margin:30px 0px 0px 10px;">
	<odin:groupBox title="搜索框" property="ggBox">
		<table>
			<tr>
			<td><odin:dateEdit property="start" readonly="false" size="20" label="开始时间"></odin:dateEdit></td>
			<td width="50px"></td>
			<td><odin:dateEdit property="end" readonly="false" size="20" label="结束时间" ></odin:dateEdit></td>
			<td width="50px"></td>
			<td><odin:button text="查询" property="query" handler="func"></odin:button></td>
			</tr>
		</table>
	</odin:groupBox>
	<odin:editgrid property="list" title="日志主信息" isFirstLoadData="false" url="/" height="450" bbarId="pageToolBar" pageSize="20" remoteSort="true" 
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
			<odin:gridColumn dataIndex="interface_log_id" hidden="true" header="接口日志编码"/>
			<odin:gridColumn dataIndex="interface_config_id" width="110" header="接口方案编码" align="center"/>
			<odin:gridColumn dataIndex="interface_config_name" width="110" header="接口方案名称" align="center"/>
			<odin:gridColumn dataIndex="interface_script_id" hidden="true" header="接口脚本编码" align="center"/>
			<odin:gridColumn dataIndex="interface_script_name" width="110" header="接口脚本名称" align="center"/>
			<odin:gridColumn dataIndex="interface_exec_sql" hidden="true" header="数据访问接口脚本执行SQL语句"  align="center"/>
			<odin:gridColumn dataIndex="interface_original_sql" hidden="true" header="数据访问接口脚本原始SQL语句" align="center"/>
			<odin:gridColumn dataIndex="interface_requesttime" width="100" header="接口访问时间" align="center"/>
			<odin:gridColumn dataIndex="interface_access_ip" width="130" header="接口访问来源IP地址" align="center"/>
			<odin:gridColumn dataIndex="execute_state_id" header="执行状态代码" hidden="true" align="center"/>
			<odin:gridColumn dataIndex="operate_username" header="访问用户" width="80" align="center"/>
			<odin:gridColumn dataIndex="interface_comments" width="130" header="备注信息" align="center" isLast="true"/>
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



