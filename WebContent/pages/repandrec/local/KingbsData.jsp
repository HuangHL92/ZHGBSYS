<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<odin:toolBar property="btnToolBar">
	<odin:fill />
	<odin:buttonForToolBar id="confbtn" text="ȷ��" icon="images/add.gif" cls="x-btn-text-icon" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar id="btnsx" text="ȡ��" icon="images/sx.gif" isLast="true" cls="x-btn-text-icon" />
</odin:toolBar>
<odin:panel contentEl="ftpUpContent" property="ftpUpManagePanel" topBarId="btnToolBar" />
<div id="ftpUpContent" >
<odin:editgrid property="Fgrid" autoFill="true" isFirstLoadData="false" url="/" >
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="kbsdeptid" />
		<odin:gridDataCol name="kbsdeptname"  isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn></odin:gridRowNumColumn>
		<odin:gridColumn  width="20" editor="checkbox" header="" dataIndex="checked" edited="true"/>
		<odin:gridColumn dataIndex="kbsdeptid" width="150" hidden="true" header="�ļ�����"  align="left" />
		<odin:gridColumn dataIndex="kbsdeptname" width="150" header="�ļ�·��" isLast="true" align="left" />
	</odin:gridColumnModel>
</odin:editgrid>
</div>
<script type="text/javascript">

</script>