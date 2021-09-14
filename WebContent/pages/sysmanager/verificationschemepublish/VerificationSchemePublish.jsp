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
					<td colspan="30"><div >&nbsp;ѡ�񷢲���֯:</div></td>
				</tr>
				<tr>
					<td>
						<tags:PublicOrgCheck  property="SysOrgTree" style ="overflow: auto; height: 420px; width: 150px; border: 2px solid #c3daf9;"  />
					</td>
				</tr>
			</table>
		</td>
		<td>
			<odin:groupBox title="��ѯ����" property="ggBox">
				<table>
					<tr>
						<odin:select property="filePublishType" codeType="FILE_PUBLISH_TYPE" label="�ļ�����"  required="true"/>
					 	<odin:textEdit property="fileName"  label="�ļ�����" /> 
					 	<odin:textEdit property="fileSuffix"  label="�ļ���׺" /> 
						<td colspan="2">
							<table>
								<tr>
									<td>
										<odin:dateEdit property="startLastModefyTime" format="Ymd" label="����޸�ʱ��" size="8" /> 
										<odin:dateEdit property="endLastModefyTime" format="Ymd" label="&nbsp;-" size="8"/>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</odin:groupBox> 
			
			<odin:gridSelectColJs name="filePublishType" codeType="FILE_PUBLISH_TYPE"></odin:gridSelectColJs>
			<odin:editgrid property="fileGrid" title="У�鷽����Ϣ" autoFill="true" width="590" height="340" bbarId="pageToolBar" pageSize="20"
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
					<odin:gridEditColumn header="�ļ�����" width="50" dataIndex="fileName" edited="false" editor="text" align="center" />
					<odin:gridEditColumn header="�ļ�����" width="40" dataIndex="filePublishType" edited="false" editor="select" codeType="FILE_PUBLISH_TYPE" align="center" />
					<odin:gridEditColumn header="�ļ���׺" width="40" dataIndex="fileSuffix" edited="false" editor="text"  align="center" />
					<odin:gridEditColumn header="�ļ�·��" width="40" dataIndex="filePath" edited="false" editor="text" hidden="true" align="center" />
					<odin:gridEditColumn header="����޸�ʱ��" width="40" dataIndex="lastModefyTime" edited="false" editor="text" align="center"  isLast="true" />
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
	<odin:buttonForToolBar text="��ѯ" id="query"
		icon="images/search.gif"  />
	<odin:buttonForToolBar text="����" id="reset" 
		icon="images/sx.gif" tooltip="��������" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="ˢ��" id="reloadButton" isLast="true"
		icon="images/sx.gif" tooltip="ˢ�±�ҳ��" />
	
</odin:toolBar>


