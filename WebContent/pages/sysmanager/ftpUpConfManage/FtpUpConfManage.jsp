<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<odin:toolBar property="btnToolBar" applyTo="ftpUpManagePanel">
	<odin:fill />
	<odin:buttonForToolBar id="btnAdd" text="新增" icon="images/add.gif" cls="x-btn-text-icon" isLast="true"/>
</odin:toolBar>
<!--  
<odin:panel contentEl="ftpUpContent" property="ftpUpManagePanel" topBarId="btnToolBar" />-->
<div id="ftpUpContent" style="width:100%;height: 100%; overflow: auto;">
<div id="ftpUpManagePanel"></div>
<odin:editgrid property="grid" title="上行FTP配置" autoFill="true"	 isFirstLoadData="false" url="/">
	<odin:gridJsonDataModel  root="data" totalProperty="totalCount">
		<odin:gridDataCol name="name" />
		<odin:gridDataCol name="hostname" />
		<odin:gridDataCol name="port" />
		<odin:gridDataCol name="username" />
		<odin:gridDataCol name="status" />
		<odin:gridDataCol name="id" isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn></odin:gridRowNumColumn>
		<odin:gridColumn dataIndex="id" width="10" header="FTP内部id" hidden="true" align="center" />
		<odin:gridColumn dataIndex="name" width="200" header="FTP名称"  align="center" />
		<odin:gridColumn dataIndex="hostname" width="200" header="FTP主机地址" align="center" />
		<odin:gridColumn dataIndex="port" width="80" header="FTP端口" align="center" />
		<odin:gridColumn dataIndex="username" width="200" header="FTP用户名" align="center" />
		<odin:gridEditColumn2 dataIndex="status" width="120" header="状态" align="center" codeType="USEFUL" editor="select" edited="false"/>
		<odin:gridColumn dataIndex="id" width="150" header="操作列" align="center"  renderer="editorRender" isLast="true" />
	</odin:gridColumnModel>
</odin:editgrid>
<input type="hidden" name="id" id="id" />
</div>
<odin:window src="/blank.htm" id="addWin" width="430" height="310" title="新增FTP配置信息"/>
<odin:window src="/blank.htm" id="editWin" width="360" height="392" title="编辑FTP配置信息"/>
<script type="text/javascript">
function editorRender(value, params, rs, rowIndex, colIndex, ds){
	return "<a href=\"javascript:editFtp('"+value+"')\">修改</a> | <a href=\"javascript:testConnFtp('"+value+"')\">测试连通</a> | <a href=\"javascript:delFtp('"+value+"')\">删除</a> ";
}
function editFtp(id){
	radow.doEvent("editWin",id);
}
function testConnFtp(id){
	radow.doEvent("conntest",id);
}
function delFtp(id){
	radow.doEvent("delftp",id);
}
Ext.onReady(function() {
	//页面调整
	 Ext.getCmp('grid').setHeight(Ext.getBody().getViewSize().height-objTop(document.getElementById('forView_grid'))[0]-4);
	 Ext.getCmp('grid').setWidth((Ext.isIE?Ext.getBody().getViewSize().width:window.innerWidth)-objTop(document.getElementById('forView_grid'))[1]-2); 
	 document.getElementById('ftpUpManagePanel').style.width = document.body.clientWidth;
	 //Ext.getCmp('btnToolBar').setWidth(document.body.clientWidth);
	 document.getElementById("ftpUpContent").style.width = document.body.clientWidth;
});
function objTop(obj){
    var tt = obj.offsetTop;
    var ll = obj.offsetLeft;
    while(true){
    	if(obj.offsetParent){
    		obj = obj.offsetParent;
    		tt+=obj.offsetTop;
    		ll+=obj.offsetLeft;
    	}else{
    		return [tt,ll];
    	}
	}
    return tt;  
}
</script>