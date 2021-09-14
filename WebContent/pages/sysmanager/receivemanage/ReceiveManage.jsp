<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<iframe id="waitForEndFrame" name="waitForEndFrame" width="0" height="0" style="display:none;" ></iframe>

<odin:toolBar property="btnToolBar" applyTo="ftpUpManagePanel">
	<odin:fill />
	<odin:buttonForToolBar id="btnsx1" handler="reload" text="ˢ��" icon="images/sx.gif" cls="x-btn-text-icon" isLast="true"/>
</odin:toolBar>
<!--<odin:panel contentEl="ftpUpContent" property="ftpUpManagePanel" topBarId="btnToolBar" />-->
<div id="ftpUpContent" style="width:100%;height: 100%; overflow: auto;">
<div id="ftpUpManagePanel"></div>
<odin:editgrid property="grid" title="�������ļ��б�" autoFill="true" isFirstLoadData="false" url="/" grouping="true" groupCol="transTypeCn">
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="fileName" />
		<odin:gridDataCol name="orgName" />
		<odin:gridDataCol name="transType" />
		<odin:gridDataCol name="transTypeCn" />
		<odin:gridDataCol name="dataInfo" />
		<odin:gridDataCol name="filePublishType" />
		<odin:gridDataCol name="createTime" />
		<odin:gridDataCol name="isOther"  isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn></odin:gridRowNumColumn>
		<odin:gridColumn dataIndex="fileName" width="350" header="�ļ�����"  align="left"   renderer="radow.renderer.renderAlt" />
		<odin:gridColumn dataIndex="orgName" width="100" header="��Դ��"  align="center"   />
		<odin:gridColumn dataIndex="transType" width="100" header="��������"  align="center"   hidden="true" />
		<odin:gridColumn dataIndex="transTypeCn" header="��������" align="center"  hidden="true"   />
		<odin:gridColumn dataIndex="dataInfo" width="200" header="�ļ�������"  align="center"   renderer="radow.renderer.renderAlt"/>
		<odin:gridEditColumn2 header="��������" width="100" dataIndex="filePublishType" edited="false" editor="select" codeType="FILE_PUBLISH_TYPE" align="center" />
		<odin:gridColumn dataIndex="createTime" width="100" header="�ļ�����ʱ��"  align="center"   />
		<odin:gridColumn dataIndex="isOther" width="100" header="������"  align="center" renderer="accpetRender"  isLast="true" />
	</odin:gridColumnModel>
</odin:editgrid>
</div>
<script type="text/javascript">

function accpetRender(value, params, rs, rowIndex, colIndex, ds){
	if(value=='0'){
		return '<a href="javascript:void(0);" onclick="openPage(\''+value+'\');">����</a>';
	}
}

function openPage(value){
	var lstSelRec = Ext.getCmp('grid').getSelectionModel().getSelections()[0];
	var filePublishType = lstSelRec.get('filePublishType');
	if(value=='0'){
		radow.doEvent("recieve",lstSelRec.get('fileName'));
		///odin.loadPageInTab("33s","/radowAction.do?method=doEvent&pageModel=pages.repandrec.plat.QueryRecieve&initParams=" +lstSelRec.get('fileName'),false,"���ݽ���",true);
		///parent.addTab("���ݽ���",aid+value,'/insiis6/radowAction.do?method=doEvent&pageModel=pages.modeldb.ModelDefine&initParams='+value,false,false)
		//TODO ���ݲ�ͬ�������ͣ����ø��ԵĽ���ҳ��
		
	}
}

function addTab(){
		var lstSelRec = Ext.getCmp('grid').getSelectionModel().getSelections()[0];
		parent.addTab('���ݽ���','���ݽ���','<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.repandrec.plat.QueryRecieve&initParams=' +lstSelRec.get('fileName'),false,false);
}
function setTimeOutLoad(){
	window.setTimeout('radow.doEvent("lfsearch")',3000);
}
function reload(){
		radow.doEventAsync("btnsx.onclick");
		odin.ext.get(document.body).mask('�ļ�������...', odin.msgCls);
		doWaitingForEnd();
}
function doWaitingForEnd(){
	document.getElementById("waitForEndFrame").src=contextPath + "/sys/WaitForEnd.jsp";
}
Ext.onReady(function() {
	//ҳ�����
	 Ext.getCmp('grid').setHeight(Ext.getBody().getViewSize().height-objTop(document.getElementById('forView_grid'))[0]-4);
	 Ext.getCmp('grid').setWidth((Ext.isIE?Ext.getBody().getViewSize().width:window.innerWidth)-objTop(document.getElementById('forView_grid'))[1]-2); 
	// Ext.getCmp('ftpUpManagePanel').setWidth(document.body.clientWidth);
	 //Ext.getCmp('btnToolBar').setWidth(document.body.clientWidth);
	 document.getElementById("ftpUpContent").style.width = document.body.clientWidth;
	 document.getElementById("ftpUpManagePanel").style.width = document.body.clientWidth-1;
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
<odin:window src="/blank.htm" id="QueryRecieveWin" width="450" height="300" title="���ݽ���" modal="true"></odin:window>