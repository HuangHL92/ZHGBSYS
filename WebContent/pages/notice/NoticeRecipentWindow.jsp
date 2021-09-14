<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/js/jquery-1.8.2.js"></script>
<style>
body{
overflow-x: hidden ! important;
}
</style>
<script type="text/javascript" src="basejs/pageUtil.js"></script>
<%@include file="/comOpenWinInit.jsp" %>
<div>
	<odin:editgrid property="noticeRecipentgrid" title="" autoFill="true" bbarId="pageToolBar" pageSize="20" isFirstLoadData="false" url="/" >
	<odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
		<odin:gridDataCol name="id" />
		<odin:gridDataCol name="noticeid"/>
		<odin:gridDataCol name="recipientid" />
		<odin:gridDataCol name="recipientname" />
		<odin:gridDataCol name="see" isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
	<odin:gridRowNumColumn></odin:gridRowNumColumn>
		<odin:gridEditColumn dataIndex="id" width="90" hidden="true" header="id" align="center" editor="text" edited="false"/>
		<odin:gridEditColumn header="接收人" dataIndex="recipientname" align="center" edited="false" editor="text" width="100" />
		<odin:gridEditColumn header="是否已查看" dataIndex="see" align="center" edited="false" editor="text" width="50" renderer="isSee" isLast="true"/>
	</odin:gridColumnModel>
	</odin:editgrid>
</div>

 <script type="text/javascript">  


    Ext.onReady(function() {
    	//页面调整
    	 Ext.getCmp('noticeRecipentgrid').setHeight(Ext.getBody().getViewSize().height-objTop(document.getElementById('forView_noticeRecipentgrid'))[0]-2);
    	 Ext.getCmp('noticeRecipentgrid').setWidth((Ext.isIE?Ext.getBody().getViewSize().width:window.innerWidth)-objTop(document.getElementById('forView_noticeRecipentgrid'))[1]-2); 
    	
    });
    
  	//是否查看 
	function isSee(see){
		var status = "已查看";
		
		if(see == 0){
			status = "未查看";
		}
		return status;
	}
	
</script>