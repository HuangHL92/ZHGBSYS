<%@page import="org.apache.commons.lang.StringUtils"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comboxWithTree.js"></script>
<%

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
.x-grid3-row,.x-grid3-row-table{
	min-height: 35px;
}
/* .x-grid3-cell-inner,.x-grid3-col-numberer{
	height:35px;
	line-height:30px;
} */
.x-grid3-cell-enable{
	outline: none;
}
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


.x-grid3-cell{
	vertical-align: middle!important;
}

</style>
<script  type="text/javascript">

var g_contextpath = '<%= request.getContextPath() %>';


</script>

			<!-- record_batch -->
<div id="groupTreePanel"></div>



<odin:hidden property="fabd00"/>
<odin:hidden property="fabd02"/>
<odin:hidden property="mnur02"/>
<odin:editgrid2 property="memberGrid" load="selectRow" hasRightMenu="false" autoFill="true"  bbarId="pageToolBar"  url="/">
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="fabd00" />
		<odin:gridDataCol name="fabd02" />
		<odin:gridDataCol name="mntp05"/>
		<odin:gridDataCol name="mnur01"/>
		<odin:gridDataCol name="mnur02"/>
		<odin:gridDataCol name="fabd01" isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn2></odin:gridRowNumColumn2>
		<odin:gridEditColumn2 dataIndex="fabd02" width="130" header="����" editor="text" edited="true" align="center"/>
		<odin:gridEditColumn2 dataIndex="mntp05" width="60" header="ģ�����ģʽ" editorId="asd" editor="select" selectData="['2','������'],['1','��ֱ��λ'],['4','�����У']" edited="true" align="center"/>
		<odin:gridEditColumn2 dataIndex="fabd01" width="70" header="�޸�ʱ��" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="fabd00" width="150" header="����" editor="text" edited="false"   renderer="viewGW"  align="center"/>
		<odin:gridEditColumn2 dataIndex="fabd00" width="80" header="ģ�ⷽ������" editor="text" edited="false" align="center" renderer="shareInfo" isLast="true"/>
		
	</odin:gridColumnModel>
</odin:editgrid2>

		



<odin:toolBar property="btnToolBar" applyTo="groupTreePanel">
	<odin:fill />
<%-- 	<odin:buttonForToolBar text="�����ȶ�" icon="image/icon021a2.gif" handler="openViewFABD" id="openViewFABD" />
	<odin:buttonForToolBar text="����" icon="image/icon021a2.gif" handler="copymntp" id="copymntp" /> --%>
	<odin:buttonForToolBar text="�ص��λ����" icon="image/icon021a2.gif" handler="openZDGW" id="openZDGW" />
	<odin:buttonForToolBar text="����" icon="image/icon021a2.gif" handler="loadadd" id="loadadd" />
	 <odin:buttonForToolBar text="ɾ��" icon="image/icon021a3.gif" handler="infoDelete" isLast="true" id="infoDelete" /> 
	<%-- <odin:buttonForToolBar text="�浵" icon="image/icon021a6.gif" handler="cundang" isLast="true" id="cundang" /> --%>
</odin:toolBar>

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




function openZSGW(){//��ְ����λ����
	openKqzsdw();
}
Ext.onReady(function() {
	openProcessWin().hide();
	var viewSize = Ext.getBody().getViewSize();
	var memberGrid = Ext.getCmp('memberGrid');
	memberGrid.setHeight(viewSize.height-40);
	Ext.get('commForm').setWidth(viewSize.width);
	memberGrid.setWidth(viewSize.width);
	
	memberGrid.on('rowclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index)
		document.getElementById('fabd00').value = rc.data.fabd00;
		document.getElementById('fabd02').value = rc.data.fabd02;
		document.getElementById('mnur02').value = rc.data.mnur02;
	});
	
	
	
	
	
	
	
	
	
	
});
function shareInfo(value, params, record, rowIndex, colIndex, ds) {
	var mnur02 = record.data.mnur02;
	var mnur01 = record.data.mnur01;
	if(mnur02!=''&&mnur02!=null){
		return "������Դ��" + mnur02;
	}else{
/* 		var rtnStr = "<div align='center' width='100%' ><font color=blue>"
			+ "<a style='cursor:pointer;' onclick=\"openProcessWin('"+record.data.fabd00+"');\">����</a>"
			//+ "<a style='cursor:pointer;padding-left:30px;' onclick=\"openGDview('"+record.data.yn_id+"');\">�鿴�浵</a>"
			+ "</font></div>"; */
		var rtnStr
		if(mnur01!=''&&mnur01!=null){
			rtnStr = "��������" + mnur01 ;
			//rtnStr = "��������" + mnur01 + "<br/>" + rtnStr;
		}
		if($('#viewMode').val()=='1'){
			rtnStr = "";
		}
		return rtnStr;
	}
	
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
	radow.doEvent('saveMntp');
}







function infoDelete(){//�Ƴ���Ա
	var fabd00 = document.getElementById('fabd00').value;
	var fabd02 = document.getElementById('fabd02').value;
	var mnur02 = document.getElementById('mnur02').value;
	if(fabd00==''){
		$h.alert('ϵͳ��ʾ','��ѡ��һ�����μ�¼��');
		return;
	}
	if(mnur02=='' || mnur02==null){
		$h.confirm("ϵͳ��ʾ��",'ɾ�����μ�¼����ɾ�������������еĵ����¼��ȷ��ɾ�����Σ�'+fabd02+"?",400,function(id) { 
			if("ok"==id){
				radow.doEvent('allDelete',fabd00);
			}else{
				return false;
			}		
		});
	}else{
		$h.confirm("ϵͳ��ʾ��",'ɾ���ü�¼��ɾ�������ϵ��ȷ��ɾ�����ι���'+fabd02+"?",400,function(id) { 
			if("ok"==id){
				radow.doEvent('allDelete',fabd00);
			}else{
				return false;
			}		
		});
	}
	
}


function selectRow(a,store){
	var peopleInfoGrid =Ext.getCmp('memberGrid');
	var len = peopleInfoGrid.getStore().data.length;
	if( len > 0 ){//Ĭ��ѡ���һ�����ݡ�
		var flag = true;
		for(var i=0;i<len;i++){
			var rc = peopleInfoGrid.getStore().getAt(i);
			if(rc.data.fabd00==$('#fabd00').val()){
				
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




function viewGW(value, params, record, rowIndex, colIndex, ds) {
	var ret = "<div align='center' width='100%' ><font color=blue>";
	
	ret += "<a style='cursor:pointer;padding-left:15px;' onclick=\"openBDFAPage('"+value+"');\">���õ�λ</a>"
	ret += "<a style='cursor:pointer;padding-left:15px;' onclick=\"openViewFABD('"+value+"');\">�鿴</a>"
	var mnur02 = record.data.mnur02;
	var mnur01 = record.data.mnur01;
	if(mnur02!=''&&mnur02!=null){
		ret += "</font></div>";
		return ret;
	}else{
		ret +=  "<a style='cursor:pointer;padding-left:15px;' onclick=\"openProcessWin('"+record.data.fabd00+"');\">����</a>";
			//+ "<a style='cursor:pointer;padding-left:30px;' onclick=\"openGDview('"+record.data.yn_id+"');\">�鿴�浵</a>"
		ret += "</font></div>";
		return ret;
	}
	
	
	
}
function openViewFABD(fabd00){//�����ȶ�
	$h.openWin('ViewFABDWinsj','pages.mntpsj.MNTPSJOP','ģ����䷽��',1810,950,'','<%=request.getContextPath()%>',null,
			{fabd00:fabd00,maximizable:true},true);
}

function openBDFAPage(fabd00){
	//var fabd00 = $('#fabd00').val();
	if(fabd00==''){
		alert('��ѡ�񷽰�');
		return;
	}
    $h.openWin('MNTPlistsj','pages.mntpsj.MNTPSJDW','��λ����',900,570,null,'<%= request.getContextPath() %>',null,
      {fabd00:fabd00},true);
}

function openZDGW(){
	$h.openPageModeWin('openZDGW','pages.mntpsj.PZZDGWTJ','��Ա��Ϣ',1440,900,null,'<%= request.getContextPath() %>');
}

</script>

<script type="text/javascript">
Ext.onReady(function() {
	
	
	
	
});
</script>