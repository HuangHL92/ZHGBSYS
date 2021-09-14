<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<odin:toolBar property="btnToolBar" applyTo="toolDiv">
	<odin:fill />
	<odin:buttonForToolBar id="btnsx" text="刷新" icon="images/sx.gif" cls="x-btn-text-icon" isLast="true"/>
</odin:toolBar>
<!--
<odin:panel contentEl="ftpUpContent" property="ftpUpManagePanel" topBarId="btnToolBar" />  -->
<div id="toolDiv"></div>
<div id="ftpUpContent" style="width:100%;height: 100%;overflow: auto;">
<odin:editgrid property="grid" title="待接收文件列表" autoFill="true" isFirstLoadData="false" url="/" grouping="true" groupCol="transTypeCn">
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="fileName" />
		<odin:gridDataCol name="orgName" />
		<odin:gridDataCol name="transType" />
		<odin:gridDataCol name="transTypeCn" />
		<odin:gridDataCol name="dataInfo" />
		<odin:gridDataCol name="createTime" />
		<odin:gridDataCol name="isOther"  isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn></odin:gridRowNumColumn>
		<odin:gridColumn dataIndex="fileName" width="250" header="文件名称"  align="left"   renderer="radow.renderer.renderAlt" />
		<odin:gridColumn dataIndex="orgName" width="100" header="来源地"  align="center"   />
		<odin:gridColumn dataIndex="transType" width="100" header="传输类型"  align="center"   hidden="true" />
		<odin:gridColumn dataIndex="transTypeCn" header="传输类型" align="center"  hidden="true"   />
		<odin:gridColumn dataIndex="dataInfo" width="200" header="文件包描述"  align="center"   renderer="radow.renderer.renderAlt"/>
		<odin:gridColumn dataIndex="createTime" width="180" header="文件创建时间"  align="center"   />
		<odin:gridColumn dataIndex="isOther" width="200" header="操作列"  align="center" renderer="accpetRender"  isLast="true" />
	</odin:gridColumnModel>
</odin:editgrid>
<odin:window src="/blank.htm" id="refreshWin" width="550" height="400" maximizable="false" title="窗口" />
<odin:window src="/blank.htm" id="dataVerifyWin" width="960" height="500" title="信息校验" modal="true" />

</div>
<script type="text/javascript">
Ext.onReady(function() {
	//页面调整
	Ext.getCmp('grid').setHeight(Ext.getBody().getViewSize().height-objTop(document.getElementById('forView_grid'))[0]-4);
	Ext.getCmp('grid').setWidth((Ext.isIE?Ext.getBody().getViewSize().width:window.innerWidth)-objTop(document.getElementById('forView_grid'))[1]-2); 
	document.getElementById("ftpUpContent").style.width = document.body.clientWidth + "px";
	document.getElementById("toolDiv").style.width = Ext.getCmp('grid').getWidth() + "px";
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
function accpetRender(value, params, rs, rowIndex, colIndex, ds){
	if(value=='0'){
		return '<a href="javascript:void(0);" onclick="openPage(\''+value+'\');">校验</a>';
	}
}

function openPage(value){
	var lstSelRec = Ext.getCmp('grid').getSelectionModel().getSelections()[0];
	radow.doEvent('receiveData',lstSelRec.get('fileName'));
	if(value=='0'){
		///radow.doEvent("recieve");
		///odin.loadPageInTab("33s","/radowAction.do?method=doEvent&pageModel=pages.repandrec.plat.QueryRecieve&initParams=" +lstSelRec.get('fileName'),false,"数据接收",true);
		///parent.addTab("数据接收",aid+value,'/insiis6/radowAction.do?method=doEvent&pageModel=pages.modeldb.ModelDefine&initParams='+value,false,false)
		///parent.addTab('数据接收','数据接收','<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.repandrec.plat.QueryRecieve&initParams=' +lstSelRec.get('fileName'),false,false);
	}
}


</script>