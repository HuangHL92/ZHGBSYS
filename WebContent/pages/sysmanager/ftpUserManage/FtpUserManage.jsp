<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<odin:toolBar property="btnToolBar" applyTo="ftpUpManagePanel">
	<odin:fill />
	<odin:buttonForToolBar id="btnAdd" text="����" icon="images/add.gif" cls="x-btn-text-icon" isLast="true"/>
</odin:toolBar>
<!-- 
<odin:panel contentEl="ftpUpContent" property="ftpUpManagePanel" topBarId="btnToolBar" /> -->
<div id="ftpUpContent" style="width:100%;height: 100%; overflow: auto;">
<div id="ftpUpManagePanel"></div>
<odin:editgrid property="grid" title="FTP�û�����" autoFill="true"	 isFirstLoadData="false" url="/">
	<odin:gridJsonDataModel  root="data" totalProperty="totalCount">
		<odin:gridDataCol name="depict" />
		<odin:gridDataCol name="userid" />
		<odin:gridDataCol name="homedirectory" />
		<odin:gridDataCol name="enableflag" />
		<odin:gridDataCol name="writepermission" />
		<odin:gridDataCol name="idletime" />
		<odin:gridDataCol name="uploadrate" />
		<odin:gridDataCol name="downloadrate" />
		<odin:gridDataCol name="maxloginnumber" />
		<odin:gridDataCol name="maxloginperip" />
		<odin:gridDataCol name="opid" isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn></odin:gridRowNumColumn>
		<odin:gridColumn dataIndex="depict" width="200" header="��������"  align="center" />
		<odin:gridColumn dataIndex="userid" width="200" header="��¼��"  align="center" />
		<odin:gridColumn dataIndex="homedirectory" width="200" header="��Ŀ¼" align="center" />
		<odin:gridEditColumn2 dataIndex="enableflag" width="120" header="����״̬" align="center" codeType="HAS" editor="select" edited="false"/>
		<odin:gridEditColumn2 dataIndex="writepermission" width="120" header="��дȨ��" align="center" codeType="HAS" editor="select"  edited="false"/>
		<odin:gridColumn dataIndex="idletime" width="200" header="����ʱ��(��)" align="center" />
		<odin:gridColumn dataIndex="uploadrate" width="200" header="�ϴ�����(K/S)" align="center" />
		<odin:gridColumn dataIndex="downloadrate" width="200" header="��������(K/S)" align="center" />
		<odin:gridColumn dataIndex="maxloginnumber" width="200" header="�û�����¼��" align="center" />
		<odin:gridColumn dataIndex="maxloginperip" width="200" header="��IP����¼��" align="center" />
		<odin:gridColumn dataIndex="opid" width="150" header="������" align="center"  renderer="editorRender" isLast="true" />
	</odin:gridColumnModel>
</odin:editgrid>
<input type="hidden" name="id" id="id" />
</div>
<odin:window src="/blank.htm" id="editWin" width="360" height="400" title="�༭FTP�û���Ϣ"/>
<script type="text/javascript">
function editorRender(value, params, rs, rowIndex, colIndex, ds){
	return "<a href=\"javascript:editFtp('"+value+"')\">�޸�</a> | <a href=\"javascript:delFtp('"+value+"')\">ɾ��</a> ";
}
function editFtp(id){
	radow.doEvent("editWin",id);
}
function delFtp(id){
	radow.doEvent("delftp",id);
}
Ext.onReady(function() {
	//ҳ�����
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