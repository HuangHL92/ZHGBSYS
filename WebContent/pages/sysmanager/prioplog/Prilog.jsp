<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<odin:toolBar property="btnToolBar">
	<odin:textForToolBar text="<h3>权限系统日志</h3>"/>
	<odin:fill/>
	<odin:buttonForToolBar id="btn_query"  text="查询"  icon="/images/search.gif" cls="x-btn-text-icon"/>
	<odin:buttonForToolBar id="clean"  text="清屏"  icon="/images/sx.gif" isLast="true" cls="x-btn-text-icon"/>
</odin:toolBar>

<div id="QueryContent">
	<table width="100%">
		<tr>
			<td height="16"  colspan="4"></td>
		</tr>
		<tr>
	    	<odin:select property="model"  label="模块" data="['RESOURCE', '资源'],['ROLE', '角色'],['USER', '用户'],['GROUP', '用户组']" />
	    	<odin:dateEdit property="timefr" label="时间" ></odin:dateEdit>
	    	<odin:textEdit property="otherMess"  label="关键信息"/>
		</tr>
		<tr>
			<td height="16" colspan="4"></td>
		</tr>
	</table>		
</div>
<odin:panel contentEl="QueryContent" property="QueryPanel" topBarId="btnToolBar"></odin:panel>

<odin:editgrid property="loggrid" title="日志管理" autoFill="false" width="771" height="410" bbarId="pageToolBar" pageSize="20" isFirstLoadData="false" url="/">
			<odin:gridJsonDataModel  id="logid" root="data" totalProperty="totalCount">
					  <odin:gridDataCol name="logid" />
					  <odin:gridDataCol name="loginname" />
					  <odin:gridDataCol name="opdate" />
					  <odin:gridDataCol name="opdesc" />
					  <odin:gridDataCol name="opaddress" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					  <odin:gridRowNumColumn></odin:gridRowNumColumn>
					  <odin:gridColumn header="logid" hidden="true" dataIndex="id"/>
					  <odin:gridColumn header="操作人员" dataIndex="loginname" align="center" width="105"/>
					  <odin:gridColumn header="操作时间" dataIndex="opdate" align="center" width="155"/>
					  <odin:gridColumn header="操作描述" dataIndex="opdesc" align="center" width="190"/>
					  <odin:gridColumn header="操作备注" dataIndex="opaddress" align="center" width="240"/>
					  <odin:gridColumn header="删除" dataIndex="logid" renderer="radow.commGridColDelete" align="center" width="40" isLast="true"/>
				</odin:gridColumnModel>
			</odin:editgrid>

