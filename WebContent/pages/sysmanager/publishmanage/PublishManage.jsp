<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<style>
.x-panel-bwrap,.x-panel-body {
	height: 100%
}

.picOrg {
	background-image:
		url(<%=request.getContextPath()%>/pages/sysorg/org/images/companyOrgImg2.png) !important;
}

.picInnerOrg {
	background-image:
		url(<%=request.getContextPath()%>/pages/sysorg/org/images/insideOrgImg1.png) !important;
}

.picGroupOrg {
	background-image: url(<%=request.getContextPath()%>/pages/sysorg/org/images/groupOrgImg1.png)
		!important;
}
</style>

<odin:floatDiv property="bar_div" position="up"></odin:floatDiv>
<table>
	<tr>
		<td height="26"></td>
	</tr>
</table>
<table width="100%" style="width: 100%;height: 90%">
	<tr>
		<td width="1" >
			<table width="160" style="display:none">
				<tr>
					<td colspan="30"><div >&nbsp;选择发布组织:</div></td>
				</tr>
				<tr>
					<td>
						<tags:PublicOrgCheck  property="SysOrgTree" style ="overflow: auto; height: 420px; width: 150px; border: 2px solid #c3daf9;"  />
					</td>
				</tr>
			</table>
		</td>
		<td>
			<odin:groupBox title="选择组织">
				<table>
					<tr>
						<odin:textIconEdit property="searchDeptBtn" required="true" size="90" colspan="4" label="组织机构"/>
						<odin:hidden property="ftpid"/>
					</tr>
				</table>
			</odin:groupBox>
			<odin:groupBox title="查询条件" property="ggBox">
				<table>
					<tr>
						<odin:select2 property="filePublishType" codeType="FILE_PUBLISH_TYPE" label="发布类型"  required="true"/>
					</tr>
				</table>
				<table id="1" style="display:none" title="信息校验方案查询条件">
					<odin:textEdit property="vsc002"  label="&nbsp;方案名称" maxlength="45" /> 
					<td colspan="2">
					<table>
						<tr>
							<td>
								<odin:dateEdit property="vsc006Start" format="Ymd" label="创建时间" size="8" maxlength="8"/> 
								<odin:dateEdit property="vsc006End" format="Ymd" label="&nbsp;-" size="8" maxlength="8"/>
							</td>
						</tr>
					</table>
					</td>
					<odin:textEdit property="vsc005Name"  label="&nbsp;&nbsp;&nbsp;创建人" maxlength="10" /> 
					<tags:BasicQuery property="vsc004" label="创建机构"  url="/radowAction.do?method=doEvent&pageModel=pages.sysmanager.verificationschemeconf.VerificationSchemeConf&eventNames=orgTreeJsonData" selectEvent="" />
				</table>	
				<!-- 其他发布类型的查询条件使用，一下几个table -->
				<table id="2" style="display:none"  title="扩展标准代码查询条件"></table>	
				<table id="3" style="display:none"  title="待扩展查询条件"></table>	
				<table id="4" style="display:none"  title="待扩展查询条件"></table>	
				<table id="9" style="display:none"  title="待扩展查询条件"></table>	
			</odin:groupBox> 
			<odin:editgrid property="fileGrid" title="发布信息" autoFill="true" width="590" height="340" bbarId="pageToolBar" pageSize="20"
				isFirstLoadData="false" url="/" hasRightMenu="false">
				<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="publishName" />
					<odin:gridDataCol name="filePublishType" />
					<odin:gridDataCol name="publishInfoId" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridColumn header="selectall" gridName="fileGrid" align="center" width="5"
						editor="checkbox" edited="true" dataIndex="mcheck" />
					<odin:gridEditColumn header="发布信息概述" width="50" dataIndex="publishName" edited="false" editor="text" align="center" />
					<odin:gridEditColumn2 header="发布类型" width="20" dataIndex="filePublishType" edited="false" editor="select" codeType="FILE_PUBLISH_TYPE" align="center" />
					<odin:gridEditColumn header="发布信息主键" width="40" dataIndex="publishInfoId" edited="false" hidden="true" editor="text" align="center"  isLast="true" />
				</odin:gridColumnModel>
			</odin:editgrid>
		</td>
	</tr>
</table>
<odin:window src="/blank.htm" id="deptWin" width="355" height="350" maximizable="false" title="窗口"/>

<odin:toolBar property="btnToolBar" applyTo="bar_div">
	<odin:textForToolBar text="<h3></h3>" />
	<odin:separator></odin:separator>
	<odin:fill />
	
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="查询" id="query"
		icon="images/search.gif"  />
	<odin:buttonForToolBar text="重置" id="reset" 
		icon="images/sx.gif" tooltip="清空输入框" />
	
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="发布" id="publish" isLast="true"
		icon="images/search.gif" tooltip="发布勾选信息" />
</odin:toolBar>
