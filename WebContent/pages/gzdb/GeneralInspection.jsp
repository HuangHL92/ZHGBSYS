<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%@page import="com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_003.CompetenceManagePageModel"%>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<%@page import="com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_004.LogManagePageModel"%>
<%@page import="com.insigma.siis.local.pagemodel.gzdb.GeneralInspectionPageModel"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comboxWithTree.js"></script>
<%@page import="net.sf.json.JSONArray"%>
<script  type="text/javascript">
var g_contextpath = '<%= request.getContextPath() %>';
</script>
<%@include file="/comOpenWinInit2.jsp" %>
<html>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>�����ļ�</title>
	<odin:MDParam></odin:MDParam>
    <script src="<%=request.getContextPath()%>/basejs/jquery-ui/jquery-1.10.2.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
    <script src="<%=request.getContextPath()%>/jwjc/js/underscore.js" type="text/javascript"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/jwjc/js/common.js" charset="utf-8"></script>
<body style="overflow-y:hidden;overflow-x:hidden">    
<style>
#sqInfo input{
	border: 1px solid #c0d1e3 !important;
	
}

</style>
<odin:toolBar property="btnToolBar">
    <odin:fill />
	<%-- <odin:buttonForToolBar text="�޸�" icon="images/i_2.gif" id="editegl" handler="editegl" />
	<odin:separator /> --%>
	<odin:buttonForToolBar text="�Ƽ����ĵ�����" icon="images/icon/table.gif" id="add1" handler="add1" isLast="true"/>
	<%-- <odin:separator />  --%>
	<%-- <odin:buttonForToolBar text="�������ĵ�����" icon="images/icon/table.gif" id="add2" handler="add2" />
	<odin:separator /> 
	<odin:buttonForToolBar text="�������ĵ�����" icon="images/icon/table.gif" id="add3" handler="add3"/>
	<odin:separator />  --%>
	<%-- <odin:buttonForToolBar text="�Ƽ����ĵ�����" icon="images/add.gif" id="addWin" handler="addWin" /> --%>
	<%-- <odin:separator />
	<odin:buttonForToolBar text="ɾ��" icon="image/delete.png"  isLast="true" id="deleteBtn"/> --%>
</odin:toolBar>
<odin:toolBar property="btnToolBarb">
    <odin:fill />
	<%-- <odin:buttonForToolBar text="�޸�" icon="images/i_2.gif" id="editegl" handler="editegl" />
	<odin:separator /> --%>
	<%-- <odin:buttonForToolBar text="�Ƽ����ĵ�����" icon="images/icon/table.gif" id="add1" handler="add1" />
	<odin:separator />  --%>
	<odin:buttonForToolBar text="�������ĵ�����" icon="images/icon/table.gif" id="add2" handler="add2" isLast="true"/>
	<%-- <odin:separator />  --%>
	<%-- <odin:buttonForToolBar text="�������ĵ�����" icon="images/icon/table.gif" id="add3" handler="add3"/>
	<odin:separator />  --%>
	<%-- <odin:buttonForToolBar text="�������ĵ�����" icon="images/add.gif" id="addWin1" handler="addWin1" /> --%>
	<%-- <odin:separator />
	<odin:buttonForToolBar text="ɾ��" icon="image/delete.png"  isLast="true" id="deleteBtn"/> --%>
</odin:toolBar>
<odin:toolBar property="btnToolBarc">
    <odin:fill />
	<%-- <odin:buttonForToolBar text="�޸�" icon="images/i_2.gif" id="editegl" handler="editegl" />
	<odin:separator /> --%>
	<%-- <odin:buttonForToolBar text="�Ƽ����ĵ�����" icon="images/icon/table.gif" id="add1" handler="add1" />
	<odin:separator /> 
	<odin:buttonForToolBar text="�������ĵ�����" icon="images/icon/table.gif" id="add2" handler="add2" />
	<odin:separator />  --%>
	<odin:buttonForToolBar text="�������ĵ�����" icon="images/icon/table.gif" id="add3" handler="add3" isLast="true"/>
	<%-- <odin:separator /> 
	<odin:buttonForToolBar text="�������ĵ�����" icon="images/add.gif" id="addWin2" handler="addWin2" > --%>
	<%-- <odin:separator />
	<odin:buttonForToolBar text="ɾ��" icon="image/delete.png"  isLast="true" id="deleteBtn"/> --%>
</odin:toolBar>
<table  style="width:100%">
<!-- <table    style="width:100%">
<tr>
	<td  valign="top" width="300" >
		 <table  >
		 	<tr >
		 		<td  > -->
<td  valign="top" width="25%">
		 <table  >
		 	<tr >
		 		<td  >
		 			<%-- <odin:toolBar property="btnToolBarET">
    					<odin:fill />
						<odin:buttonForToolBar text="�޸�" icon="images/i_2.gif" id="editegl" handler="editegl" />
						<odin:separator />
						<odin:buttonForToolBar text="����" icon="images/add.gif" id="addBtn" handler="addegl" isLast="true"/>
						<odin:buttonForToolBar text="ɾ��" icon="image/delete.png"  isLast="true" id="deleteBtn"/>
					</odin:toolBar> --%>
		 			<odin:editgrid2 property="mdgrid" hasRightMenu="false" width="500"   topBarId="btnToolBarET" title="" autoFill="true" pageSize="20" bbarId="pageToolBar" url="/">
							<odin:gridJsonDataModel>
								<odin:gridDataCol name="mdid" />
								<odin:gridDataCol name="code_name" />
								<odin:gridDataCol name="code_value" />
								<odin:gridDataCol name="cz" />
								<odin:gridDataCol name="type" />
								<odin:gridDataCol name="mnur02" />
								<odin:gridDataCol name="userid" />
								<odin:gridDataCol name="sq" />
								</odin:gridJsonDataModel>
								<odin:gridColumnModel>
								<odin:gridRowNumColumn2></odin:gridRowNumColumn2>	
								 <odin:gridEditColumn2 header="�������" dataIndex="code_value" width="100" align="center" editor="text" edited="false" hidden="true" />
								<odin:gridEditColumn2 header="���򻻽�����" align="center" edited="false" width="150" dataIndex="code_name" editor="text"  />
								<odin:gridEditColumn2 header="������" align="center" edited="false" width="90" dataIndex="mnur02" editor="text"  />
								<odin:gridEditColumn2 header="����" dataIndex="cz" width="100" align="center" editor="text" edited="false"  renderer="Grant" />
								<odin:gridEditColumn2 header="����" dataIndex="sq" width="100" align="center" editor="text" edited="false"  renderer="sqRenderer" isLast="true"/>
							  </odin:gridColumnModel>
					 </odin:editgrid2>
		 		</td>
			</tr>
		 </table>
	</td>
<td valign="top" >
	 <div id="girdDiv" style="width: 100%;height:100%;">

				<table  >
				<tr>
					<td>
<%-- <form id="formId" name="data" method="post"  action="<%=request.getContextPath()%>/DownloadServlet?method=appendixUpload" enctype="multipart/form-data">
	<table align="center" width="96%">	
		<tr>
			<td width="10"></td>
			<td width="20">
		       <iframe id="frame" name="frame" height="33px" width="280px" src="<%=request.getContextPath() %>/pages/sysorg/org/adfile2.jsp" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes"></iframe>
			</td>
			<td  height="15" >
	 		 <span  id = "CPDText"></span>
	 		</td>
			<td>
				<img src="<%=request.getContextPath()%>/images/go1.jpg" onclick="sc()">
			</td>
		</tr>
	</table>
	<odin:hidden property="pid"/>
</form> --%>
<table style="width: 100%;">
	<tr>
		<td>
			<odin:editgrid2 property="fileGrid" topBarId="btnToolBar" bbarId="pageToolBar" autoFill="false" height="150"
				 hasRightMenu="false" url="/">
				<odin:gridJsonDataModel id="name" root="data" totalProperty="totalCount">
					
					<odin:gridDataCol name="tjlx"/>
					<odin:gridDataCol name="tjlxl"/>
					<odin:gridDataCol name="fileurl"/>
					<odin:gridDataCol name="filename"/>
					<odin:gridDataCol name="xjqy_code"/>
					<odin:gridDataCol name="material"/>
					<odin:gridDataCol name="download"/>
					<%-- <odin:gridDataCol name="operateU" isLast="true"/> --%>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn2 />
					<odin:gridEditColumn2 dataIndex="tjlx" width="300"  editor="text" edited="false" header="�Ƽ����ĵ�" />
					<odin:gridEditColumn2 dataIndex="download" width="420" editor="text" edited="false" header="�����ļ�" renderer="download"/>
					<odin:gridEditColumn2 dataIndex="material" width="100" editor="text" edited="false" header="�ϴ�" renderer="updatematerial"/>
					<%-- <odin:gridEditColumn2 dataIndex="operateU" width="150" header="����" editor="text" edited="false" 
						 align="center" renderer="operateUod" isLast="true"/> --%>
				</odin:gridColumnModel>
			</odin:editgrid2>
</td>
				   </tr>
				   
				   <tr>
		<td>
			<odin:editgrid2 property="file1Grid" topBarId="btnToolBarb" bbarId="pageToolBar" autoFill="false" height="150"
				 hasRightMenu="false" url="/">
				<odin:gridJsonDataModel id="name" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="tjlx"/>
					<odin:gridDataCol name="tjlxl"/>
					<odin:gridDataCol name="fileurl"/>
					<odin:gridDataCol name="filename"/>
					<odin:gridDataCol name="xjqy_code"/>
					<odin:gridDataCol name="material"/>
					<odin:gridDataCol name="download"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn2 />
					<%-- <odin:gridEditColumn2 dataIndex="giid" width="200" editor="text" edited="false" header="���쿼��id"  hidden="true"/> --%>
					<%--  <odin:gridEditColumn2 header="���ء���" dataIndex="qxs" width="200" align="center"  editor="select"  selectData="['001.001.004.001','�ϳ���'],['001.001.004.004','������'],['001.001.004.005','������'],['001.001.004.006','������'],['001.001.004.00F','Ǯ����'],['001.001.004.007','��ɽ��'],['001.001.004.008','�ຼ��'],['001.001.004.00E','��ƽ��'],['001.001.004.00D','������'],['001.001.004.00B','ͩ®��'],['001.001.004.009','������'],['001.001.004.00A','�ٰ���'],['001.001.004.00C','������']" edited="false" /> --%>
					<%-- <odin:gridEditColumn2 dataIndex="qxs" width="200" editor="text" edited="false" header="���ء���"/> --%>
					<%-- <odin:gridEditColumn2 header="��������" dataIndex="tjlx" width="100" align="center" editor="select" selectData="['01','������'],['02','�˴���'],['03','��Э��']" edited="false" /> --%>
					<%-- <odin:gridEditColumn2 dataIndex="tjl" width="400" editor="text" edited="false" header="�Ƽ����ĵ�" renderer="recommend"/> --%>
					<%-- <odin:gridEditColumn2 dataIndex="cpl" width="800" editor="text" edited="false" header="�������ĵ�"  renderer="evaluation"/> --%>
					<%-- <%-- <odin:gridEditColumn2 dataIndex="cll" width="400" editor="text" edited="false" header="�������ĵ�"  renderer="material"/> --%>
					<%-- <odin:gridEditColumn2 dataIndex="operateU1" width="150" header="����" editor="text" edited="false" 
						 align="center" renderer="operateUod1" isLast="true"/> --%>
					<odin:gridEditColumn2 dataIndex="tjlx" width="300"  editor="text" edited="false" header="�������ĵ�" />
					<odin:gridEditColumn2 dataIndex="download" width="420" editor="text" edited="false" header="�����ļ�" renderer="download"/>
					<odin:gridEditColumn2 dataIndex="material" width="100" editor="text" edited="false" header="�ϴ�" renderer="updatematerial"/>
				</odin:gridColumnModel>
			</odin:editgrid2>
</td>
				   </tr>
				   
				    <tr>
		<td>
			<odin:editgrid2 property="file2Grid" topBarId="btnToolBarc" bbarId="pageToolBar" autoFill="false" height="150"
				 hasRightMenu="false" url="/">
				<odin:gridJsonDataModel id="name" root="data" totalProperty="totalCount">
					<odin:gridDataCol name="tjlx"/>
					<odin:gridDataCol name="tjlxl"/>
					<odin:gridDataCol name="fileurl"/>
					<odin:gridDataCol name="filename"/>
					<odin:gridDataCol name="xjqy_code"/>
					<odin:gridDataCol name="material"/>
					<odin:gridDataCol name="download"/>
					<%-- <odin:gridDataCol name="giid"/> --%>
					<%-- <odin:gridDataCol name="tjl"/>
					<odin:gridDataCol name="cpl"/> --%>
					<%-- <odin:gridDataCol name="cll"/>
					<odin:gridDataCol name="tjlx"/>
					<odin:gridDataCol name="xjqy"/>
					<odin:gridDataCol name="fileurl"/>
        			<odin:gridDataCol name="filename"/>
					<odin:gridDataCol name="operateU2" isLast="true"/> --%>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn2 />
					<%-- <odin:gridEditColumn2 dataIndex="giid" width="200" editor="text" edited="false" header="���쿼��id"  hidden="true"/> --%>
					<%--  <odin:gridEditColumn2 header="���ء���" dataIndex="qxs" width="200" align="center"  editor="select"  selectData="['001.001.004.001','�ϳ���'],['001.001.004.004','������'],['001.001.004.005','������'],['001.001.004.006','������'],['001.001.004.00F','Ǯ����'],['001.001.004.007','��ɽ��'],['001.001.004.008','�ຼ��'],['001.001.004.00E','��ƽ��'],['001.001.004.00D','������'],['001.001.004.00B','ͩ®��'],['001.001.004.009','������'],['001.001.004.00A','�ٰ���'],['001.001.004.00C','������']" edited="false" /> --%>
					<%-- <odin:gridEditColumn2 dataIndex="qxs" width="200" editor="text" edited="false" header="���ء���"/> --%>
					<%-- <odin:gridEditColumn2 header="��������" dataIndex="tjlx" width="100" align="center" editor="select" selectData="['01','������'],['02','�˴���'],['03','��Э��']" edited="false" /> --%>
					<%-- <odin:gridEditColumn2 dataIndex="tjl" width="400" editor="text" edited="false" header="�Ƽ����ĵ�" renderer="recommend"/>
					<odin:gridEditColumn2 dataIndex="cpl" width="400" editor="text" edited="false" header="�������ĵ�"  renderer="evaluation"/> --%>
					<%-- <odin:gridEditColumn2 dataIndex="cll" width="800" editor="text" edited="false" header="�������ĵ�"  renderer="material"/>
					<odin:gridEditColumn2 dataIndex="operateU2" width="150" header="����" editor="text" edited="false" 
						 align="center" renderer="operateUod2" isLast="true"/> --%>
				    <odin:gridEditColumn2 dataIndex="tjlx" width="300"  editor="text" edited="false" header="�������ĵ�" />
				    <odin:gridEditColumn2 dataIndex="download" width="420" editor="text" edited="false" header="�����ļ�" renderer="download"/>
					<odin:gridEditColumn2 dataIndex="material" width="100" editor="text" edited="false" header="�ϴ�" renderer="updatematerial"/>
				</odin:gridColumnModel>
			</odin:editgrid2>
</td>
				   </tr>
				   
				</table>
			</div>
		</td>
	</tr>
</table>
</div>
</td>
</table>
<div id="sqInfo">
	<div style="margin-left: 20px;margin-top: 10px;font-size:20px">
		<table>
		<tr id="chooseSQ">
			<tags:ComBoxWithTree property="mnur01" label="ѡ���û�" width="280" readonly="true" ischecked="true" codetype="USER" listHeight="300" />
		</tr>
		</table>
		<div style="margin-left: 100px;margin-top: 15px;">
			<odin:button text="ȷ��" property="saveSQInfo" handler="saveSQInfo" />
		</div>
	</div>
</div>
<odin:hidden property="pid"/>
<odin:hidden property="mdid"/>
<odin:hidden property="code_value"/>
<script>
Ext.onReady(function() {
	//document.getElementById('pid').value = document.getElementById('subWinIdBussessId2').value;
    openSQWin();
	 hideWin();
   var viewSize = Ext.getBody().getViewSize();
	 var editgrid = Ext.getCmp('fileGrid');
	 var editgrid1 = Ext.getCmp('file1Grid');
	 var editgrid2 = Ext.getCmp('file2Grid');
	 var mdgrid = Ext.getCmp('mdgrid');
	 mdgrid.setHeight(viewSize.height-10 ); 
	 //editgrid.setHeight(viewSize.height-20);
	 mdgrid.setWidth(475);
  editgrid.setWidth(viewSize.width - 500);
  editgrid1.setWidth(viewSize.width - 500);
  editgrid2.setWidth(viewSize.width - 500);
  
  editgrid.on('rowdblclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index);
		var code_value = rc.data.code_value;
	});
	
});


//grid��������ʾ�Ĳ������
function Grant(value, params, record,rowIndex,colIndex,ds){
	var code_value = record.get('code_value');
	if(record.get("type")==1){
		return "<a href=\"javascript:dRow('"+code_value+"')\">����</a>&nbsp;";
	}
}
function updatematerial(value, params, record, rowIndex, colIndex, ds) {
	var tjlxl=record.get("tjlxl");
	var xjqy_code=record.get("xjqy_code");
	var param=tjlxl+"&&"+xjqy_code;
	
	return "<a href=\"javascript:sq(&quot;"+param+"&quot;)\">�ϴ�</a>";
}

function download(value, params, record, rowIndex, colIndex, ds) {
	
	var tjlxl=record.get("tjlxl");
	var xjqy_code=record.get("xjqy_code");
	
	var t=record.get("fileurl");
	var g=record.get("filename");
	var Info = "";
	if(t==null||t==""){
		return;
	}
	//ֻ��һ����Ϣʱ
	if(t.indexOf("&")==-1){
		var url=t.replace(/\\/g,"/");
		Info = "<a href=\"javascript:downloadFile('"+url+"')\">"+g+"</a>&nbsp;";
		return Info;
	}
		var a=record.get("fileurl").split("&");
		var b=record.get("filename").split("&");
		//var url=a.replace(/\\/g,"/");
		for(var i=0;i<a.length;i++){
			var url=a[i].replace(/\\/g,"/");
			if(i==0){
				Info = Info+"<a href=\"javascript:downloadFile('"+url+"')\">"+b[i]+"</a>&nbsp;&nbsp;&nbsp;&nbsp;";
				continue;
			}
			if(i==a.length-1){
				Info = Info+"<a href=\"javascript:downloadFile('"+url+"')\">"+b[i]+"</a>&nbsp;&nbsp;&nbsp;&nbsp;";
				continue;
			}
			
			Info = Info+"<a href=\"javascript:downloadFile('"+url+"')\">"+b[i]+"</a>&nbsp;&nbsp;&nbsp;&nbsp;";
			}
		return Info;
}
//����
function downloadFile(url) {
	var downfile = url;
	//w = window.open("ProblemDownServlet?method=downFile&prid=" + encodeURI(encodeURI(downfile)));
	window.location.href="ProblemDownServlet?method=downFile&prid=" + encodeURI(encodeURI(downfile));
}
function sq(param){ 
	//radow.doEvent('sq',meeting_id);	
	$h.openWin('publishWin','pages.gzdb.CadresPlanning','�ĵ���Ϣ',760, 560,param,g_contextpath);
	
}
function mdRenderer(value, params, record, rowIndex, colIndex, ds) {
	if(record.get("type")==2){
		return null;
	}
	var code_value=record.get("code_value");
	var code_name=record.get("code_name");
	if(record.get("type")==1){
		return "<a href=\"javascript:\">"+code_name+"</a>";
	}
}

function sqRenderer(value, params, record, rowIndex, colIndex, ds) {
	if(record.get("type")==2){
		return null;
	}
	var code_value=record.get("code_value");
	if(record.get("type")==1&&record.get("userid")=='40288103556cc97701556d629135000f'){
		return "<a href=\"javascript:sqRow2(&quot;"+code_value+"&quot;)\">��Ȩ</a>";
	}
}
function sqRow2(code_value){ 
	radow.doEvent('sqRow',code_value);	
	
}
function openSQWin(){
	var win = Ext.getCmp("addsq");	
	if(win){
		win.show();	
		return;
	}
	win = new Ext.Window({
		title : '��Ȩά��',
		layout : 'fit',
		width : 380,
		height : 171,
		closeAction : 'hide',
		closable : true,
		modal : true,
		id : 'addsq',
		collapsed:false,
		collapsible:false,
		bodyStyle : 'background-color:#FFFFFF',
		plain : true,
		titleCollapse:false,
		contentEl:"sqInfo",
		listeners:{}
		           
	});
	win.show();
}

function hideWin(){
	var win = Ext.getCmp("addsq");	
	if(win){
		win.hide();	
	}
}
function saveSQInfo(){
	radow.doEvent("saveSQInfo");
	Ext.getCmp("addsq").hide();
}

function addWin() {
	var list = odin.ext.getCmp('mdgrid').getSelectionModel().getSelections();	
    if(list.length==1){  
    	var xjqy=list[0].get('code_value');
        //document.getElementById('code_value').value=xjqy;
    }  
	$h.showWindowWithSrc('addWin', contextPath
			+ '/pages/gzdb/AddGeneralInspection.jsp?xjqy=' + xjqy,'�����Ƽ����ĵ�����',530,550);	
}

function addWin1() {
	var list = odin.ext.getCmp('mdgrid').getSelectionModel().getSelections();	
    if(list.length==1){  
    	var xjqy=list[0].get('code_value');
        //document.getElementById('code_value').value=xjqy;
    }  
	$h.showWindowWithSrc('addWin1', contextPath
			+ '/pages/gzdb/AddGeneralInspection1.jsp?xjqy=' + xjqy,'�����������ĵ�����',530,550);	
}

function addWin2() {
	var list = odin.ext.getCmp('mdgrid').getSelectionModel().getSelections();	
    if(list.length==1){  
    	var xjqy=list[0].get('code_value');
        //document.getElementById('code_value').value=xjqy;
    }  
	$h.showWindowWithSrc('addWin2', contextPath
			+ '/pages/gzdb/AddGeneralInspection2.jsp?xjqy=' + xjqy,'�����������ĵ�����',530,550);	
}
// ����ύ
function sc(){
	var pid = document.getElementById('sh000').value;
	window.frames['frame'].imp(pid);
	//frame.window.imp(pid);
}
function gg() {
	//window.close();
	//var parentWin = window.opener;
   radow.doEvent("fileGrid.dogridquery");
}
function yulan_rmb(sh000){
	document.getElementById('mainIframe').src='<%= request.getContextPath() %>/radowAction.do?method=doEvent&pageModel=pages.meeting.MeetingPreview&publishid=sh000@@'+sh000;
} 
//����ԭ��
function recommend(value, params, rs, rowIndex, colIndex, ds){
	
	var tjlx = rs.get('tjlx');
	var fileurl = rs.get('fileurl');
	var filename = rs.get('filename');
	 if(tjlx.substr(0, 3) =='�Ƽ���'){
		 
		 var url=fileurl.replace(/\\/g,"/");
		return "<a href=\"javascript:downloadFile('" +url +"')\">"+filename+"</a>";
	} 
} 
//����ԭ��
function evaluation(value, params, rs, rowIndex, colIndex, ds){
	var tjlx = rs.get('tjlx');
	var fileurl = rs.get('fileurl');
	var filename = rs.get('filename');
	 if(tjlx.substr(0, 3) =='������'){
		 
		 var url=fileurl.replace(/\\/g,"/");
		return "<a href=\"javascript:downloadFile('" +url +"')\">"+filename+"</a>";
	} 
} 

//����ԭ��
function material(value, params, rs, rowIndex, colIndex, ds){
	var tjlx = rs.get('tjlx');
	var fileurl = rs.get('fileurl');
	var filename = rs.get('filename');
	 if(tjlx.substr(0, 3) =='������'){
		 
		 var url=fileurl.replace(/\\/g,"/");
		return "<a href=\"javascript:downloadFile('" +url +"')\">"+filename+"</a>";
	} 
} 
//����ر�,ǰ��ҳ�� �������ݸ���
function colseWin( html ){
	//����ǰһҳ��
	if(realParent.document.getElementById("div_fujian")!=null){
		realParent.document.getElementById("div_fujian").innerHTML =html;
	}
	//window.close();
}

//����ر�,ǰ��ҳ�� �������ݸ���
function fz( meetingname ){
	//����ǰһҳ��
	if(document.getElementById("CPDText")!=null){
		document.getElementById("CPDText").innerHTML =meetingname;
	}
	//window.close();
}

// grid��������ʾ�Ĳ������
function operateUod(value, params, record,rowIndex,colIndex,ds){
	var giid = record.get('giid');
	var fileurl = record.get('fileurl');
	var url=fileurl.replace(/\\/g,"/");
	var ar = giid+'&&'+url;
	return "<a href=\"javascript:deleteRow('"+ar+"')\">ɾ��</a>&nbsp;";
}
//grid��������ʾ�Ĳ������
function operateUod1(value, params, record,rowIndex,colIndex,ds){
	var giid = record.get('giid');
	var fileurl = record.get('fileurl');
	var url=fileurl.replace(/\\/g,"/");
	var ar = giid+'&&'+url;
	return "<a href=\"javascript:deleteRow('"+ar+"')\">ɾ��</a>&nbsp;";
}
//grid��������ʾ�Ĳ������
function operateUod2(value, params, record,rowIndex,colIndex,ds){
	var giid = record.get('giid');
	var fileurl = record.get('fileurl');
	var url=fileurl.replace(/\\/g,"/");
	var ar = giid+'&&'+url;
	return "<a href=\"javascript:deleteRow('"+ar+"')\">ɾ��</a>&nbsp;";
}

// ������ذ�ť
function downloadRow(rowIndex) {
	radow.doEvent("downloadFile", rowIndex);
}

//������ذ�ť
function dRow(code_value){
	radow.doEvent("importSHBtn", code_value);
	//window.frames['frame'].impd(sh000);
	//radow.doEvent("file", rowIndex);
	<%-- $h.openWin("AppendixWin","pages.meeting.CadresPlanningAllocationAppendix", "�����б�", 760, 560, sh000,"<%=request.getContextPath()%>"); --%>
}
function add1() {
	var list = odin.ext.getCmp('mdgrid').getSelectionModel().getSelections();	
    if(list.length==1){  
    	var code_value=list[0].get('code_value');
        //document.getElementById('code_value').value=xjqy;
    }  
    var kc= code_value+'&&'+'1';
    radow.doEvent("importKc", kc);
}
function add2() {
	var list = odin.ext.getCmp('mdgrid').getSelectionModel().getSelections();	
    if(list.length==1){  
    	var code_value=list[0].get('code_value');
        //document.getElementById('code_value').value=xjqy;
    }  
    var kc= code_value+'&&'+'2';
    radow.doEvent("importKc", kc);
}
function add3() {
	var list = odin.ext.getCmp('mdgrid').getSelectionModel().getSelections();	
    if(list.length==1){  
    	var code_value=list[0].get('code_value');
        //document.getElementById('code_value').value=xjqy;
    }  
    var kc= code_value+'&&'+'3';
    radow.doEvent("importKc", kc);
}
// ����
function downloadFile(url) {
	var downfile = url;
	//w = window.open("ProblemDownServlet?method=downFile&prid=" + encodeURI(encodeURI(downfile)));
	window.location.href="ProblemDownServlet?method=downFile&prid=" + encodeURI(encodeURI(downfile));
}

// ɾ��
function deleteRow(ar) {
	// ȷ��ɾ��
	$h.confirm("ϵͳ��ʾ��",'�Ƿ�ȷ��ɾ����',200,function(id) { 
		if("ok"==id){
			radow.doEvent("deleteFile", ar);
		}else{
			return false;
		}		
	});
}
function gg() {
    radow.doEvent("fileGrid.dogridquery");
    radow.doEvent("file1Grid.dogridquery");
    radow.doEvent("file2Grid.dogridquery");
}
/* function restore(){
	parent.odin.ext.getCmp('grid1').store.reload();
	window.close();
} */

function info(type){
	document.all('excelFile').value='';
	odin.ext.get(document.body).unmask();

		doCloseWin(type);
}

var businessData = "";
function doCloseWin(type){
	odin.ext.get(document.body).mask('����ˢ��ҳ��......', odin.msgCls);
	parent.odin.ext.getCmp('impWin').hide();
	if(businessData!="" && type==1){
		if(typeof parent.resFuncImpExcel != 'undefined'){
			parent.resFuncImpExcel(odin.ext.decode(businessData));
		}
	}
}

</script>
</body>
</html>