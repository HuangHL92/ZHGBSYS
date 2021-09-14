<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<odin:toolBar property="btnToolBar">
	<odin:fill />
	<odin:buttonForToolBar id="btnsx" text="刷新" icon="images/sx.gif" cls="x-btn-text-icon" />
</odin:toolBar>
<odin:panel contentEl="ftpUpContent" property="ftpUpManagePanel" topBarId="btnToolBar" />
<div id="ftpUpContent" style="width:100%;height: 100%; overflow: auto;">
<odin:editgrid property="Fgrid" title="待接收文件列表(D:\\\\KingbsData)" autoFill="true" isFirstLoadData="false" url="/" >
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="fname" />
		<odin:gridDataCol name="fabsolutepath"  isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn></odin:gridRowNumColumn>
		<odin:gridColumn dataIndex="fname" width="150" header="文件名称"  align="left" />
		<odin:gridColumn dataIndex="fabsolutepath" width="150" header="文件路径"  align="left" />
		<odin:gridColumn dataIndex="fabsolutepath" width="50" header="操作"  align="center" renderer="accpetRender" isLast="true" />
	</odin:gridColumnModel>
</odin:editgrid>
</div>
<script type="text/javascript">

function accpetRender(value, params, rs, rowIndex, colIndex, ds){
	if(value!=''){
		return '<a href="javascript:void(0);" onclick="openPage(\''+value+'\');">导入</a>';
	}
}
function openPage(value){
	radow.doEvent('imp3btn',value);
	
}

</script>