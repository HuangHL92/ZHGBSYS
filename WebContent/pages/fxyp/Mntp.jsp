<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comboxWithTree.js"></script>
<%
String viewMode = request.getParameter("view");
if(StringUtils.isEmpty(viewMode)){
	viewMode = "0";
}
%>
<style>

.x-tip .x-tip-bd{font-size: 14px;}
.x-tip h3{font-size: 14px;}
.x-tip .x-tip-bd-inner{font-size: 14px;}
.x-form-field{font: 14px tahoma, arial, helvetica, sans-serif;}
.x-combo-list-item{font: 14px tahoma, arial, helvetica, sans-serif;}
.x-form-item{font: 14px tahoma, arial, helvetica, sans-serif;}
.x-grid-group-hd div{font: bold 14px tahoma, arial, helvetica, sans-serif;}
.ext-safari .x-btn button{font-size: 14px;}
.x-fieldset legend{font-size: 14px;}
.x-grid3-summary-row .x-grid3-cell-inner{font-weight: bold;padding-bottom: 4px;font-size: 14px;}
.x-panel-fbar select,.x-panel-fbar label{font: 14px arial, tahoma, helvetica, sans-serif;}
.ext-el-mask-msg div{font-size: 14px;}
.loading-indicator{font-size: 14px;}
.x-tab-strip span.x-tab-strip-text{font-size: 14px;}
.x-form-invalid-msg{font-size: 14px;}
.x-form fieldset legend{font-size: 14px;}
.x-small-editor .x-form-field{font-size: 14px;}
.x-btn{font-size: 14px;}
.x-btn button{font-size: 14px;}
.x-toolbar td,.x-toolbar span,.x-toolbar input,.x-toolbar div,.x-toolbar select,.x-toolbar label{font-size: 14px;}
.x-grid3-hd-row td,.x-grid3-row td,.x-grid3-summary-row td{font-size: 14px;}
.x-grid3-topbar,.x-grid3-bottombar{font-size: 14px;}
.x-grid-empty{font-size: 14px;}
.x-dd-drag-ghost{font-size: 14px;}
.x-tree-node{font-size: 14px;}
.x-tip .x-tip-mc{font-size: 14px;}
.x-tip .x-tip-header-text{font-size: 14px;}
.x-tip .x-tip-body{font-size: 14px;}
.x-date-inner th span{font-size: 14px;}
.x-date-middle,.x-date-left,.x-date-right{font-size: 14px;}
.x-date-inner a{font-size: 14px;}
.x-date-mp td{font-size: 14px;}
.x-date-mp-btns button{font-size: 14px;}
.x-menu-list-item{font-size: 14px;}
#x-debug-browser .x-tree .x-tree-node a span{font-size: 14px;}
.x-combo-list-hd{font-size: 14px;}
.x-combo-list-small .x-combo-list-item{font-size: 14px;}
.x-panel-header{font-size: 14px;}
.x-panel-tl .x-panel-header{font-size: 14px;}
.x-panel-mc{font-size: 14px;}
.x-window-tl .x-window-header{font-size: 14px;}
.x-window-mc{font-size: 14px;}
.x-progress-text{font-size: 14px;}
.x-window-dlg .x-window-header-text{
	font-size: 18px;
}
.x-window-dlg .ext-mb-text{
	font-size: 18px;
}


input{
	border: 1px solid #c0d1e3 !important;
}
.x-grid3-cell-inner, .x-grid3-hd-inner{
	white-space:normal !important;
}
.ext-ie .x-grid3-cell-enable{
	height: auto !important;
}
</style>
<script  type="text/javascript">

var g_contextpath = '<%= request.getContextPath() %>';


</script>

			<!-- record_batch -->
<div id="groupTreePanel"></div>



<odin:hidden property="viewMode" value="<%=viewMode %>"/>
<odin:hidden property="mntp00"/>
<odin:hidden property="mntp04"/>
<odin:hidden property="mntp05"/>
<odin:editgrid2 property="memberGrid" load="selectRow" hasRightMenu="false" autoFill="true"  bbarId="pageToolBar"  url="/">
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="mnur01" />
		<odin:gridDataCol name="mnur02" />
		<odin:gridDataCol name="mntp00" />
		<odin:gridDataCol name="mntp01"/>
		<odin:gridDataCol name="mntp02"/>
		<odin:gridDataCol name="mntp03"/>
		<odin:gridDataCol name="mntp05"/>
		<odin:gridDataCol name="fabd00"/>
		<odin:gridDataCol name="mntp04" isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn2></odin:gridRowNumColumn2>
		<odin:gridEditColumn2 dataIndex="mntp04" width="130" header="����" editor="text" edited="true" align="center"/>
		<odin:gridEditColumn2 dataIndex="mntp02" width="50" header="�޸�ʱ��" editor="text" edited="false" align="center"/>
<%--  		<odin:gridEditColumn2 dataIndex="mntp01" width="60" header="ģ���������" editorId="asd" editor="select" selectData="['1','�������'],['2','���л���'],['3','�����齨']" edited="true" align="center"/>
 --%>	<odin:gridEditColumn2 dataIndex="mntp00" width="260" header="����" editor="text" edited="false"   renderer="viewGW" align="center"/>
		<odin:gridEditColumn2 dataIndex="mntp00" width="80" header="ģ�ⷽ������" editor="text" edited="false" align="center" renderer="shareInfo" isLast="true"/>
	</odin:gridColumnModel>
</odin:editgrid2>

		



<odin:toolBar property="btnToolBar" applyTo="groupTreePanel">
	<odin:fill />
	<odin:buttonForToolBar text="�����ȶ�" icon="image/icon021a2.gif" handler="openViewFABD" id="openViewFABD" />
	<odin:buttonForToolBar text="����" icon="image/icon021a2.gif" handler="copymntp" id="copymntp" />
	<odin:buttonForToolBar text="����" icon="image/icon021a2.gif" handler="loadadd" id="loadadd" />
	<%-- <odin:separator></odin:separator>
	<odin:buttonForToolBar text="�޸�" icon="image/icon021a6.gif" handler="infoUpdate"  id="infoUpdate"/>
	<odin:separator></odin:separator> --%>
	<odin:buttonForToolBar text="ɾ��" icon="image/icon021a3.gif" handler="infoDelete"  id="infoDelete" />
	<odin:buttonForToolBar text="�浵" icon="image/icon021a6.gif" handler="cundang" isLast="true" id="cundang" />
</odin:toolBar>


<odin:hidden property="mntp01"/>
	
<script type="text/javascript">
function openZSGW(){//��ְ����λ����
	openKqzsdw();
}
Ext.onReady(function() {
	var viewSize = Ext.getBody().getViewSize();
	var memberGrid = Ext.getCmp('memberGrid');
	memberGrid.setHeight(viewSize.height-40);
	Ext.get('commForm').setWidth(viewSize.width);
	memberGrid.setWidth(viewSize.width);
	
	memberGrid.on('rowclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index)
		document.getElementById('mntp00').value = rc.data.mntp00;
		document.getElementById('mntp04').value = rc.data.mntp04;
		document.getElementById('mntp05').value = rc.data.mntp05;
	});
	
	/* memberGrid.on('rowdblclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index)
		document.getElementById('mntp00').value = rc.data.mntp00;
		document.getElementById('mntp04').value = rc.data.mntp04;
		document.getElementById('mntp05').value = rc.data.mntp05;
		openKqzsdw( rc.data.mntp00, rc.data.mntp05);
	}); */
	
	
	
	
	var win = new Ext.Window({
		title : 'ѡ������',
		layout : 'fit',
		width : 200,
		height : 101,
		closeAction : 'hide',
		closable : true,
		modal : true,
		id : 'expFile',
		collapsed:false,
		collapsible:false,
		bodyStyle : 'background-color:#FFFFFF',
		plain : true,
		titleCollapse:false,
		listeners:{'hide':function(){radow.doEvent("memberGrid.dogridquery");}},
		contentEl:"addmntp"
		             
	});
	
	win.show();
	win.hide();
	
	
	
	
});

function openNewWindow(url,name){
	var fulls = "left=0,screenX=0,top=0,screenY=0,scrollbars=1";    //���嵯�����ڵĲ���
	if (window.screen) {
	   var ah = screen.availHeight - 40;
	   var aw = screen.availWidth - 10;
	   fulls += ",height=" + ah;
	   fulls += ",innerHeight=" + ah;
	   fulls += ",width=" + aw;
	   fulls += ",innerWidth=" + aw;
	   fulls += ",resizable"
	} else {
	   fulls += ",resizable"; // ���ڲ�֧��screen���Ե�������������ֹ�������󻯡� manually
	}
	window.open(url,name,fulls);
}
function loadadd(){
	//var win = Ext.getCmp("expFile");	
	//win.show();
	saveMntp();
}



function infoUpdate(){
	var mntp00 = document.getElementById('mntp00').value;

	if(mntp00==''){
		$h.alert('ϵͳ��ʾ','��ѡ��һ�����μ�¼��');
		return;
	}
	
	
}


function copymntp(){//�Ƴ���Ա
	var mntp00 = document.getElementById('mntp00').value;
	var mntp04 = document.getElementById('mntp04').value;
	if(mntp00==''){
		$h.alert('ϵͳ��ʾ','��ѡ��һ�����μ�¼��');
		return;
	}
	$h.confirm("ϵͳ��ʾ��",'ȷ�ϸ��Ʊ����䷽��?',400,function(id) { 
		if("ok"==id){
			radow.doEvent('copymntp',mntp00);
		}else{
			return false;
		}		
	});
}

function cundang(){
	var mntp00 = document.getElementById('mntp00').value;
	radow.doEvent('cundang',mntp00);
}

function infoDelete(){//�Ƴ���Ա
	var mntp00 = document.getElementById('mntp00').value;
	var mntp04 = document.getElementById('mntp04').value;
	if(mntp00==''){
		$h.alert('ϵͳ��ʾ','��ѡ��һ�����μ�¼��');
		return;
	}
	$h.confirm("ϵͳ��ʾ��",'ɾ�����μ�¼����ɾ�������������еĵ����¼��ȷ��ɾ�����Σ�'+mntp04+"?",400,function(id) { 
		if("ok"==id){
			radow.doEvent('allDelete',mntp00);
		}else{
			return false;
		}		
	});
}


function selectRow(a,store){
	var peopleInfoGrid =Ext.getCmp('memberGrid');
	var len = peopleInfoGrid.getStore().data.length;
	if( len > 0 ){//Ĭ��ѡ���һ�����ݡ�
		var flag = true;
		for(var i=0;i<len;i++){
			var rc = peopleInfoGrid.getStore().getAt(i);
			if(rc.data.yn_id==$('#mntp00').val()){
				
				peopleInfoGrid.getSelectionModel().selectRow(i,true);
				peopleInfoGrid.fireEvent('rowclick',peopleInfoGrid,0,this);
				flag= false;
				setTimeout(function(){peopleInfoGrid.getView().scroller.dom.scrollTop = (i-12)*27;},100);
				return;
			}
		}
		peopleInfoGrid.getSelectionModel().selectRow(0,true);
		peopleInfoGrid.fireEvent('rowclick',peopleInfoGrid,0,this);
	}
}
function infoSearch(){
	radow.doEvent('memberGrid.dogridquery');
}


function saveMntp(){
	var mntp01 = "2";
	$("#mntp01").val(mntp01);
	radow.doEvent('saveMntp');
}


function openKqzsdw(mntp00,mntp05){
	//var mntp00 = document.getElementById('mntp00').value;
	//var mntp05 = document.getElementById('mntp05').value;

	if(mntp00==''){
		$h.alert('ϵͳ��ʾ','��ѡ��һ�����μ�¼��');
		return;
	}
	$h.openWin('Kqzsdw','pages.fxyp.Kqzsdw','ѡ���ȱְλ',1650,1000,null,g_contextpath,null,
			{mntp00:mntp00,mntp05:mntp05,scroll:"scroll:yes;"},true);
}
function openKqzsjzhj(mntp00,mntp05,mntp01){
	//var mntp00 = document.getElementById('mntp00').value;
	//var mntp05 = document.getElementById('mntp05').value;

	if(mntp00==''){
		$h.alert('ϵͳ��ʾ','��ѡ��һ�����μ�¼��');
		return;
	}
	$h.openWin('Kqzsdw','pages.fxyp.Kqzsjzhj','ģ�����',1650,1000,null,g_contextpath,null,
			{mntp00:mntp00,mntp05:mntp05,mntp01:mntp01,scroll:"scroll:yes;"},true);
}
function openKqzsjzhjView(mntp00,mntp05,mntp01){
	//var mntp00 = document.getElementById('mntp00').value;
	//var mntp05 = document.getElementById('mntp05').value;

	if(mntp00==''){
		$h.alert('ϵͳ��ʾ','��ѡ��һ�����μ�¼��');
		return;
	}
	$h.openWin('Kqzsdw','pages.fxyp.KqzsjzhjView','ģ����䷽���鿴',1650,1000,null,g_contextpath,null,
			{mntp00:mntp00,mntp05:mntp05,mntp01:mntp01,scroll:"scroll:yes;"},true);
}




function viewGW(value, params, record, rowIndex, colIndex, ds) {
	var ret = "<div align='center' width='100%' ><font color=blue>";
	
	if(record.data.fabd00){
		ret += "<a style='cursor:pointer;padding-left:15px;' onclick=\"openViewFABD('','','','"+record.data.fabd00+"');\">�����ȶԲ鿴</a>"
		+ "</font></div>";
		return ret;
	}
	
	
	/* + "<a style='cursor:pointer;padding-left:15px;' onclick=\"openKqzsdw('"+value+"','"+record.data.mntp05+"');\">�������</a>" */
	if((record.data.mnur02==''||record.data.mnur02==null)&&$('#viewMode').val()!='1'){
		ret += "<a style='cursor:pointer;' onclick=\"openKqzsjzhj('"+value+"','"+record.data.mntp05+"','"+record.data.mntp01+"');\">ģ�����</a>"
		+ "<a style='cursor:pointer;padding-left:15px;' onclick=\"openViewGW('"+value+"');\">��λ��ѡ��</a>"
		+ "<a style='cursor:pointer;padding-left:15px;' onclick=\"openViewRYBD('"+value+"');\">�������(��λ)</a>";
	}
	
	//+ "<a style='cursor:pointer;padding-left:15px;' onclick=\"openKqzsjzhjView('"+value+"','"+record.data.mntp05+"','"+record.data.mntp01+"');\">ģ�����鿴</a>"
	
	
	//ret += "<a style='cursor:pointer;padding-left:15px;' onclick=\"openViewFABD('"+value+"');\">�����ȶ�</a>";
	
	ret += "<a style='cursor:pointer;padding-left:15px;' onclick=\"openViewRYBD_GW('"+value+"');\">�������(��λ)</a>";
	if((record.data.mnur02==''||record.data.mnur02==null)&&$('#viewMode').val()!='1'){
		ret += "<a style='cursor:pointer;padding-left:15px;' onclick=\"openCBDY('"+value+"');\">��������</a>";
	}
	ret += "<a style='cursor:pointer;padding-left:15px;' onclick=\"openTJT('"+value+"');\">ͳ��ͼ</a>";
	if((record.data.mnur02==''||record.data.mnur02==null)&&$('#viewMode').val()!='1'){
		ret += "<a style='cursor:pointer;padding-left:15px;' onclick=\"openRYMD('"+value+"');\">��Ա����</a>";
	}
	
	ret += "<a style='cursor:pointer;padding-left:15px;' onclick=\"openBDHZ('"+value+"');\">�䶯����</a>"
	+ "</font></div>";
	return ret;
	
}
function openViewGW(mntp00){
	$h.openPageModeWin('RXFXYP','pages.fxyp.RXFXYP','�ɲ�ģ�������ѡ�������б�',1250,900,
			{mntp00:mntp00,scroll:"scroll:yes;"},g_contextpath);
	//openNewWindow('<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.fxyp.RXFXYP&mntp00='+mntp00,'�ɲ�ģ�������ѡ�������б�')
}

function openViewRYBD(mntp00){//ģ�����
	$h.openWin('ViewRYBDWin','pages.fxyp.BZRYBD','ģ�����',1410,900,'','<%=request.getContextPath()%>',null,{mntp00:mntp00},true);
}
function openViewRYBD_GW(mntp00){//ģ�����
	$h.openWin('ViewRYBDGWWin','pages.fxyp.BZRYBDGW','ģ�����',1410,900,'','<%=request.getContextPath()%>',null,{mntp00:mntp00},true);
}
function openViewFABD(a,v,x,fabd00){//�����ȶ�
	$h.openWin('ViewFABDWin','pages.fxyp.BZFABD','�����ȶ�',1410,900,'','<%=request.getContextPath()%>',null,{fabd00:fabd00},true);
}
function openCBDY(mntp00){
  var publishid=mntp00;
  $h.openPageModeWin('DongYiPreview','pages.fxyp.DongYiPreview&publishid='+publishid,'ģ�����Ԥ��',1000,700,{publishid:publishid},g_contextpath);
}
function openRYMD(mntp00){//��Ա����
	$h.openWin('RYMDView','pages.fxyp.RYMD','��Ա����',1200,780,'','<%=request.getContextPath()%>',null,{mntp00:mntp00},true);
}
function openBDHZ(mntp00){//�䶯����
	$h.openWin('BDHZView','pages.fxyp.BDHZ','�䶯����',1400,1200,'','<%=request.getContextPath()%>',null,{mntp00:mntp00},true);
}
function openTJT(mntp00){
	$('#mntp00').val(mntp00);
	var url = g_contextpath + "/pages/fxyp/StructuralAnalysis.jsp?mntp00="+mntp00;
	$h.showWindowWithSrc("structuralAnalysis",url,"ͼ�����", 1400, 700,null,{closeAction:'close'},true);
}

function shareInfo(value, params, record, rowIndex, colIndex, ds) {
	/* if(value!=''&&value!=null){
		return "<div align='center' width='100%' ><font color=blue>"
		+ "<a style='cursor:pointer;' onclick=\"radow.doEvent('unsaveShareInfo','"+record.data.mntp00+"');\">ȡ������</a>"
		//+ "<a style='cursor:pointer;padding-left:30px;' onclick=\"openGDview('"+record.data.yn_id+"');\">�鿴�浵</a>"
		+ "</font></div>"
	}else{
		return "<div align='center' width='100%' ><font color=blue>"
		+ "<a style='cursor:pointer;' onclick=\"openProcessWin('"+record.data.mntp00+"');\">����</a>"
		//+ "<a style='cursor:pointer;padding-left:30px;' onclick=\"openGDview('"+record.data.yn_id+"');\">�鿴�浵</a>"
		+ "</font></div>"
	} */
	var mnur02 = record.data.mnur02;
	var mnur01 = record.data.mnur01;
	if(mnur02!=''&&mnur02!=null){
		return "������Դ��" + mnur02;
	}else{
		var rtnStr = "<div align='center' width='100%' ><font color=blue>"
			+ "<a style='cursor:pointer;' onclick=\"openProcessWin('"+record.data.mntp00+"');\">����</a>"
			//+ "<a style='cursor:pointer;padding-left:30px;' onclick=\"openGDview('"+record.data.yn_id+"');\">�鿴�浵</a>"
			+ "</font></div>";
		
		if(mnur01!=''&&mnur01!=null){
			rtnStr = "��������" + mnur01 + "<br/>" + rtnStr;
		}
		if($('#viewMode').val()=='1'){
			rtnStr = "";
		}
		return rtnStr;
	}
	//alert(mnur02)
	
	
}

function openProcessWin(v){
	var win = Ext.getCmp("tuiSong");	
	if(win){
		win.show();	
		return;
	}
	win = new Ext.Window({
		title : 'ѡ�����û�',
		layout : 'fit',
		width : 300,
		height : 161,
		closeAction : 'hide',
		closable : true,
		modal : true,
		id : 'tuiSong',
		collapsed:false,
		collapsible:false,
		bodyStyle : 'background-color:#FFFFFF',
		plain : true,
		titleCollapse:false,
		contentEl:"tuiSongInfo",
		listeners:{}
		           
	});
	win.show();
	return win;
}
</script>

<div id="tuiSongInfo">
	<div style="margin-left: 20px;margin-top: 10px;">
		<table>
		  <tr>
			<tags:ComBoxWithTree property="mnur01" label="ѡ���û�" readonly="true" ischecked="true" codetype="USER" />
		  </tr>
		</table>
		<div style="margin-left: 115px;margin-top: 15px;">
			<odin:button text="ȷ��" property="saveTuiSongInfo" />
		</div>
	</div>
</div>
<script type="text/javascript">
Ext.onReady(function() {
	openProcessWin().hide();
	
	
	if($('#viewMode').val()=='1'){
		Ext.getCmp('copymntp').hide();
		Ext.getCmp('loadadd').hide();
		Ext.getCmp('infoDelete').hide();
		Ext.getCmp('cundang').hide();
		
	}
	
	
});
</script>