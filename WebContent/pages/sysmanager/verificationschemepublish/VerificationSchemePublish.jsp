<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/jquery/jquery-1.7.2.min.js"></script>
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
		<td width="150">
			<table width="150">
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
			<odin:groupBox title="查询条件" property="ggBox">
				<table>
					<tr>
						<odin:select property="filePublishType" codeType="FILE_PUBLISH_TYPE" label="文件类型"  required="true"/>
					 	<odin:textEdit property="fileName"  label="文件名称" /> 
					 	<odin:textEdit property="fileSuffix"  label="文件后缀" /> 
						<td colspan="2">
							<table>
								<tr>
									<td>
										<odin:dateEdit property="startLastModefyTime" format="Ymd" label="最后修改时间" size="8" /> 
										<odin:dateEdit property="endLastModefyTime" format="Ymd" label="&nbsp;-" size="8"/>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</odin:groupBox> 
			
			<odin:gridSelectColJs name="filePublishType" codeType="FILE_PUBLISH_TYPE"></odin:gridSelectColJs>
			<odin:editgrid property="fileGrid" title="校验方案信息" autoFill="true" width="590" height="340" bbarId="pageToolBar" pageSize="20"
				isFirstLoadData="false" url="/">
				<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="fileName" />
					<odin:gridDataCol name="filePublishType" />
					<odin:gridDataCol name="fileSuffix" />
					<odin:gridDataCol name="filePath" />
					<odin:gridDataCol name="lastModefyTime" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridColumn header="selectall" gridName="VeriySchemeGrid" align="center" width="20"
						editor="checkbox" edited="true" dataIndex="mcheck" />
					<odin:gridEditColumn header="文件名称" width="50" dataIndex="fileName" edited="false" editor="text" align="center" />
					<odin:gridEditColumn header="文件类型" width="40" dataIndex="filePublishType" edited="false" editor="select" codeType="FILE_PUBLISH_TYPE" align="center" />
					<odin:gridEditColumn header="文件后缀" width="40" dataIndex="fileSuffix" edited="false" editor="text"  align="center" />
					<odin:gridEditColumn header="文件路径" width="40" dataIndex="filePath" edited="false" editor="text" hidden="true" align="center" />
					<odin:gridEditColumn header="最后修改时间" width="40" dataIndex="lastModefyTime" edited="false" editor="text" align="center"  isLast="true" />
				</odin:gridColumnModel>
			</odin:editgrid>
			
		</td>
	</tr>
</table>

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
	<odin:buttonForToolBar text="刷新" id="reloadButton" isLast="true"
		icon="images/sx.gif" tooltip="刷新本页面" />
	
</odin:toolBar>


