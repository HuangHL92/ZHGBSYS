<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@include file="/comOpenWinInit.jsp" %>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<odin:toolBar property="btnToolBar">
	<odin:fill />
	<odin:buttonForToolBar id="confbtn" text="ȷ��" icon="images/add.gif" cls="x-btn-text-icon" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar id="btnsx" text="ˢ��" icon="images/sx.gif" isLast="true" cls="x-btn-text-icon" />
</odin:toolBar>
<odin:panel contentEl="ftpUpContent" property="ftpUpManagePanel" topBarId="btnToolBar" />
<div id="ftpUpContent" >
<odin:editgrid property="Fgrid" autoFill="true" isFirstLoadData="false" url="/" >
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="fname" />
		<odin:gridDataCol name="fabsolutepath"  isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn></odin:gridRowNumColumn>
		<odin:gridColumn  width="20" editor="checkbox" header="" dataIndex="checked" edited="true"/>
		<odin:gridColumn dataIndex="fname" width="150" hidden="true" header="�ļ�����"  align="left" />
		<odin:gridColumn dataIndex="fabsolutepath" width="150" header="�ļ�·��" isLast="true" align="left" />
	</odin:gridColumnModel>
</odin:editgrid>
</div>
<script type="text/javascript">

</script>