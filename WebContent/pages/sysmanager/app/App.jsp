<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<odin:toolBar property="btnToolBar">
	<odin:textForToolBar text="<h3>应用查询</h3>"/>
	<odin:fill/>
	<odin:buttonForToolBar id="btn_query"  text="查询"  icon="images/search.gif" cls="x-btn-text-icon"/>
	<odin:buttonForToolBar id="btn_create"  text="增加" icon="images/add.gif" cls="x-btn-text-icon"/>
	<odin:buttonForToolBar id="clean"  text="刷新"  icon="images/sx.gif" isLast="true" cls="x-btn-text-icon"/>
</odin:toolBar>

<div id="QueryContent">
	<table width="100%">
		<tr>
			<td height="16"  colspan="4"></td>
		</tr>
		<tr>
	    	<odin:textEdit property="appName"  label="应用名称"/>
	    	<odin:textEdit property="otherMess"  label="关键信息"/>
		</tr>
		<tr>
			<td height="16" colspan="4"></td>
		</tr>
	</table>		
</div>
<odin:panel contentEl="QueryContent" property="QueryPanel" topBarId="btnToolBar"></odin:panel>
<odin:gridSelectColJs name="parent" codeType="PROXY"/>
<odin:editgrid property="appgrid" title="应用管理" autoFill="false" width="771" height="394" topBarId="" bbarId="pageToolBar" pageSize="20" isFirstLoadData="false" url="/">
			<odin:gridJsonDataModel  id="appid" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="appid" />
					<odin:gridDataCol name="appName"/>
					<odin:gridDataCol name="appCode"/>
					<odin:gridDataCol name="appTitle" />
					<odin:gridDataCol name="appType"/>
					<odin:gridDataCol name="parent" />
					<odin:gridDataCol name="reqrandom"/>
					<odin:gridDataCol name="appDesc"  isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridColumn dataIndex="appName" header="应用名称" align="center" width="80" />
					<odin:gridColumn dataIndex="appCode" header="应用编码" align="center" width="80"/>
					<odin:gridColumn dataIndex="appTitle" header="应用标签" align="center" width="80"/>
					<odin:gridColumn dataIndex="appType" header="应用类别" align="center" width="80"/>
					<odin:gridColumn dataIndex="parent" header="父应用或者叫主应用" align="center" width="125" editor="select" codeType="PROXY"/>
					<odin:gridColumn dataIndex="reqrandom" header="请求时的随机数" align="center" width="115"/>
					<odin:gridColumn dataIndex="appDesc" header="描述" align="center" width="100"/>
					<odin:gridColumn dataIndex="appid" header="修改" renderer="radow.commGrantForGrid" align="center" width="40"/>
					<odin:gridColumn dataIndex="appid" header="删除" renderer="radow.commGridColDelete" align="center" width="40" isLast="true"/>
				</odin:gridColumnModel>
			</odin:editgrid>

<odin:window src="/blank.htm" id="operateWin" width="520" height="200" title="应用操作窗口"></odin:window>