<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<% 
	String picType = (String)(new SysOrgPageModel().areaInfo.get("picType"));
	String ereaname = (String)(new SysOrgPageModel().areaInfo.get("areaname"));
	String ereaid = (String)(new SysOrgPageModel().areaInfo.get("areaid"));
	String manager = (String)(new SysOrgPageModel().areaInfo.get("manager"));
%>
<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.SysOrgPageModel"%>
<odin:toolBar property="btnToolBar">
	<odin:textForToolBar text=""/>
	<odin:fill/>
	<odin:buttonForToolBar text="�ϱ�" id="reppackagebtn" icon="images/icon/exp.png"  isLast="true"/>
</odin:toolBar>
<odin:panel contentEl="panel_content" property="mypanel" topBarId="btnToolBar" ></odin:panel>
<div id="panel_content">
<odin:hidden property="ereaname" value="<%=ereaname%>" />
					<odin:hidden property="ereaid" value="<%=ereaid%>" />
					<odin:hidden property="manager" value="<%=manager%>" />
					<odin:hidden property="picType" value="<%=picType%>" />
<odin:hidden property="ftpid"/>
<odin:hidden property="downfile"/>

		<div>
		<table >
			<tr>
				<td align="center" colspan="6">
					<table >
						<tr>
						<odin:textIconEdit property="searchDeptBtn" required="true" size="90" colspan="4" label="�ϱ�����"/>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td align="center" colspan="6">
					<table >
						<tr>
						<odin:textEdit property="linkpsn" required="true" size="38" label="��ϵ��"/>
						<odin:textEdit property="linktel" required="true" size="38" label="��ϵ�绰"/>
						</tr>
						<tr align="right">
							<odin:textarea property="remark" colspan="255" label="�� ע"/>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		</div>
</div>	
<odin:window src="/blank.htm" id="simpleExpWin" width="560" height="350" maximizable="false" title="����"></odin:window>
<odin:window src="/blank.htm" id="deptWin" width="355" height="350" maximizable="false" title="����">
</odin:window>
<script>
</script>