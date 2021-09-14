<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>

<%@include file="/comOpenWinInit.jsp" %>
<odin:toolBar property="btnToolBar">
</odin:toolBar>
<odin:panel contentEl="ftpUpContent" property="ftpUpManagePanel" topBarId="btnToolBar" />
<div id="ftpUpContent" >
<table>
	<tr>
		<odin:hidden property="psncount"  />
		<odin:hidden property="orgcount" />
	</tr>
</table>
<odin:hidden property="id"/>
<odin:editgrid property="Fgrid" height="200" autoFill="true" isFirstLoadData="false" url="/" >
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="name" />
		<odin:gridDataCol name="status" />
		<odin:gridDataCol name="info" isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn></odin:gridRowNumColumn>
		<odin:gridColumn dataIndex="name" sortable="false" width="100" header="阶段"  align="left" />
		<odin:gridColumn dataIndex="status" sortable="false" width="50" renderer="rendererthis" header="状态"  align="center" />
		<odin:gridColumn dataIndex="info" sortable="false" width="150" header="详情" isLast="true" align="center" />
	</odin:gridColumnModel>
</odin:editgrid>
</div>
<script type="text/javascript">
function myrefresh() 
{	
	Ext.getCmp("Fgrid").getStore().reload();
    ///radow.doEvent('btnsx');
} 
var timer1= window.setInterval("myrefresh()",15000); 

function rendererthis(value, params, rs, rowIndex, colIndex, ds){
	if(value=='0'){
		return "";
	} else if(value=='1'){
		return "<img src='<%=request.getContextPath()%>/basejs/ext/resources/images/default/grid/wait.gif'>";
	} else if(value=='2'){
		if(rowIndex==2){
			clearInterval(timer1);
			realParent.odin.ext.getCmp('MGrid').store.reload();
			alert('导入完成');
			//关闭详情窗口
			parent.Ext.getCmp(subWinId).close();
		}
		return "<img src='<%=request.getContextPath()%>/images/right1.gif'>";
	} else if(value=='4'){
		clearInterval(timer1);
		alert('导入失败');
		return "<img src='<%=request.getContextPath()%>/images/wrong.gif'>";
	}
}

</script>