<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@page import="com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_003.CompetenceManagePageModel"%>
<%@page import="com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_004.LogManagePageModel"%>
<style>

.x-grid3-cell-inner, .x-grid3-hd-inner{
white-space:normal !important;
word-break:break-all;
}

</style>
<script  type="text/javascript">
function logExp(){
		
		var username = document.getElementById('searchUserNameBtn').value;
		var type = document.getElementById('searchTypeBtn').value;
		var object = document.getElementById('searchObjectBtn').value;
		var info = document.getElementById('searchInfoBtn').value;
		var start = document.getElementById('start').value;
		var end = document.getElementById('end').value;
		username = encodeURI(encodeURI(username));
		type = encodeURI(encodeURI(type));
		object = encodeURI(encodeURI(object));
		info = encodeURI(encodeURI(info));
		start = encodeURI(encodeURI(start));
		end = encodeURI(encodeURI(end));
		//w = window.location.href("FileDownServlet?method=doPost&username="+username +"&type="+type+"&object="+object+"&info="+info+"&start="+start+"&end="+end);
		//setTimeout("doprocess()",1500);
		
		
		var url="<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.sysmanager.ZWHZYQ_001_004.FileExpWindow&username="+username +"&type="+type+"&object="+object+"&info="+info+"&start="+start+"&end="+end;
   
   		odin.openWindow('fileExpWin','��־����',url,600,150);
		
		
		//doprocess();
		//Ext.Ajax.request({
		//			url: "FileDownServlet?method=doPost",
		//			params: {username:username,
		//					type:type,
		//					object:object,
		//					info:info,
		//					start:start,
		//					end:end},
		//			success : function(response) {
		//						alert('���ݳɹ���');
		//		                //eval(response.responseText);
		//		            },
		//		    failure:function(){
		//	     		 alert("����ʧ�ܣ�");
		//	         }
		//		});
		
	 /* $.ajax({
	    url: contextPath+"/FileDownServlet",    //�����url��ַ
	    dataType: "text",   //���ظ�ʽΪjson
	    async: true, //�����Ƿ��첽��Ĭ��Ϊ�첽����Ҳ��ajax��Ҫ����
	    data: { "username":username,"type":type,"object":object,"info":info,"start":start,"end":end },    //����ֵ
	    type: "post",   //����ʽ
	    success: function(req) {

	    },
	    error: function() {
	        alert("ʧ��");
	    }
	});  */
	//odin.alert("�������ɱ����ļ������Ժ�...");
}

function opwin() {
	var win = odin.ext.getCmp('fileImpWin');
	win.setTitle('���봰��');
	/// win.setSize(500,350); //���  �߶�
	odin.showWindowWithSrc('fileImpWin', contextPath
			+ "/pages/sysmanager/ZWHZYQ_001_004/FileImpWindow.jsp");
}
</script>
<% 
	String users = (String)(new LogManagePageModel().users);
%>

<div id="groupTreeContent" >
<div id="groupTreePanel"></div>
<odin:hidden property="users"/>

<odin:groupBox title="������" property="ggBox">
	<table >
		<tr>
			<odin:select2 property="searchUserNameBtn" editor="true" size="30" data="<%=users%>" label="�����û�"/>
			<odin:select2 property="searchTypeBtn" size="30" label="��������" />
			<odin:textEdit property="searchObjectBtn" size="33" label="��������"/>
			
		</tr>
		<tr>
			<odin:select2 property="searchInfoBtn" size="30" label="��Ϣ��" />
			<odin:dateEdit property="start" readonly="false" size="30" label="��ʼʱ��"></odin:dateEdit>
			<odin:dateEdit property="end" readonly="false" size="30" label="����ʱ��"></odin:dateEdit>
		</tr>
		
	</table>
</odin:groupBox>
<table >
	<tr>
		<td width="50%">
			<odin:editgrid property="memberGrid" hasRightMenu="false" title="��־����Ϣ" autoFill="true"  forceNoScroll="false"  height="380" bbarId="pageToolBar" pageSize="20" isFirstLoadData="false" url="/">
			<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="system_log_id" />
					<odin:gridDataCol name="userlog"/>
					<odin:gridDataCol name="eventtype"/>
					<odin:gridDataCol name="eventobject" />
					<odin:gridDataCol name="objectname" />
					<odin:gridDataCol name="system_operate_date" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridColumn dataIndex="system_log_id" width="110" hidden="true" header="��־����" align="center"/>
					<odin:gridColumn dataIndex="userlog" editor="select" width="110" header="�����û�" codeType="USER"  align="center"/>
					<odin:gridColumn dataIndex="eventtype" width="190" header="��������" align="center" editor="text"/>
					<odin:gridColumn dataIndex="eventobject" width="140" header="��Ϣ��" align="center" editor="text"/>
					<odin:gridColumn dataIndex="objectname" header="��������" width="80" align="center" editor="text"/>
					<odin:gridColumn dataIndex="system_operate_date" width="130" header="����ʱ��" align="center" editor="text" isLast="true" renderer="time"/>
				</odin:gridColumnModel>
			</odin:editgrid>
		</td>
		<td width="50%">
			<odin:editgrid property="logGrid" title="��־��ϸ��Ϣ"  hasRightMenu="false"   autoFill="true" forceNoScroll="false"    height="380" bbarId="pageToolBar" pageSize="20" isFirstLoadData="false" url="/">
			<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="systemlogdetailid" />
					<odin:gridDataCol name="dataname"/>
					<odin:gridDataCol name="oldvalue"/>
					<odin:gridDataCol name="newvalue" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridColumn dataIndex="dataname" width="150" header="��Ϣ��" align="center"/>
					<odin:gridColumn dataIndex="oldvalue" width="300" header="ԭֵ" align="center"/>
					<odin:gridColumn dataIndex="newvalue" width="300" header="��ֵ" align="center" isLast="true"/>
				</odin:gridColumnModel>
			</odin:editgrid>
		</td>
	</tr>
</table>
		
</div>

<odin:toolBar property="btnToolBar" applyTo="groupTreePanel">
	<odin:textForToolBar text="<h3>��־����</h3>" />
	<odin:fill />
	<odin:separator></odin:separator>
	<%-- <odin:buttonForToolBar text="��־�鵵" id="logSaveBtn" tooltip="������־Ǩ��"/> --%>
	<odin:buttonForToolBar text="����excel" icon="images/icon/exp.png" id="expExcel" handler="expExcel"/>
	<odin:separator></odin:separator>
	<%-- <odin:buttonForToolBar text="��־Ǩ��" icon="images/icon/exp.png" id="logMoveBtn" tooltip="������־Ǩ��"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="������־" icon="images/mylog.gif" id="logExpBtn" handler="logExp" tooltip="������־����"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="������־" icon="images/icon/imp.gif" id="logImpBtn22" handler="opwin" tooltip="������־����"/>
	<odin:separator></odin:separator>  --%>
	<odin:buttonForToolBar text="��ѯ" icon="images/search.gif" id="findLogBtn" tooltip="��ѯ��־" isLast="true"/>
</odin:toolBar>
<!-- 
<odin:panel contentEl="groupTreeContent" property="groupTreePanel" topBarId="btnToolBar"></odin:panel> -->

<odin:window src="/blank.htm" id="ModuleComWin" width="500" height="300" title="ģ��Ȩ�޷���ҳ��" modal="true"></odin:window>
<odin:window src="/blank.htm" id="fileImpWin" width="400" height="170" title="��־����ҳ��" modal="true"></odin:window>		
<odin:window src="/blank.htm" id="logMoveWin" width="430" height="330" title="��־Ǩ��ҳ��" modal="true"></odin:window>

<script type="text/javascript">
function expExcel(){
	odin.grid.menu.expExcelFromGrid('memberGrid', null, null,null, false);
}
Ext.onReady(function() {
	//ҳ�����
	 Ext.getCmp('logGrid').setHeight((Ext.getBody().getViewSize().height-140));
	 Ext.getCmp('memberGrid').setHeight(Ext.getCmp('logGrid').getHeight());
//	 Ext.getCmp('groupTreePanel').setWidth(document.body.clientWidth);
	 //document.getElementById("groupTreePanel").style.width = document.body.clientWidth;
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

function time(value) {
	var length = value.length;
	
	if(length > 16){
		value  = value.substring(0,19);
	}
	
	return value;
}


function flash() {
	radow.doEvent('memberGrid.dogridquery');
	
}
</script>


