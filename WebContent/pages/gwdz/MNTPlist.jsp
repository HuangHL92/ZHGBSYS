<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.SysOrgPageModel"%>
<%@ page import="java.util.*"%> 
<%@ page import="java.text.*"%> 
<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@include file="/comOpenWinInit.jsp" %>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comboxWithTree.js"></script>
<style>

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
<odin:hidden property="fabd00"/>
<odin:hidden property="famx00"/>
<odin:toolBar property="btnToolBar"  >
	<odin:fill />
	<odin:buttonForToolBar text="ɾ��" icon="image/icon021a3.gif" handler="infoDelete"  id="infoDelete" />
	<odin:buttonForToolBar text="�����״��ģ�ⷽ��" icon="image/icon021a2.gif" handler="loadadd" id="loadadd" isLast="true" />
</odin:toolBar>
<odin:editgrid2 property="memberGrid" height="500" hasRightMenu="false" autoFill="true" topBarId="btnToolBar"  bbarId="pageToolBar"  url="/">
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="famx00" />
		<odin:gridDataCol name="famx02" />
		<odin:gridDataCol name="fabd00" />
		<odin:gridDataCol name="famx01"/>
		<odin:gridDataCol name="mntp04"/>
		<odin:gridDataCol name="mntp00"/>
		<odin:gridDataCol name="set" isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn2></odin:gridRowNumColumn2>
		<odin:gridEditColumn2 dataIndex="famx00" hidden="true" header="������ϸ����"  editor="text"  edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="famx02" width="20" header="�����"  editor="text" edited="false" align="center"    />
		<odin:gridEditColumn2 dataIndex="famx01" width="60" header="ģ���������" editorId="asd" editor="select" selectData="['1','��״'],['2','ģ�����']" edited="true" align="center"/>
		<odin:gridEditColumn2 dataIndex="mntp04" width="90" header="ģ���������"  editor="text" edited="false" align="center"    />
		<odin:gridEditColumn2 dataIndex="mntp00" hidden="true" header="��������"  editor="text"  edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="set" width="90" header="����" editor="text" edited="false" align="center" renderer="operFA" isLast="true"/>
	</odin:gridColumnModel>
</odin:editgrid2>




<script type="text/javascript">
function operFA(value, params, record, rowIndex, colIndex, ds) {
	var famx00 = record.get("famx00");
	var famx01 = record.get("famx01");
	var mntp00 = record.get("mntp00");
	result="<table width='100%'><tr><td width='100%' align='center'><a href=\"javascript:choosedw('"+famx00+"','"+famx01+"','"+mntp00+"')\">ѡ��λ</a></td></tr></table>"
/* 		result="<table width='100%'><tr><td width='100%' align='center'><a href=\"javascript:choosedw('"+famx00+"','1')\">ѡ��λ</a></td></tr></table>" */
	
	
	return result;
	
	
}

function choosedw(famx00,famx01,mntp00){
	$h.openPageModeWin('choosedw','pages.gwdz.CHOOSEdw','����ѡ��',530,550,{famx00:famx00,famx01:famx01,mntp00:mntp00},'<%= request.getContextPath() %>');
}

function reload(){
	radow.doEvent('memberGrid.dogridquery');
}
function infoDelete(){
	var famx00=document.getElementById('famx00').value;
	if(famx00==null || famx00 ==''){
		$h.alert('ϵͳ��ʾ��','��ѡ��Ա��У�',null,150);
 		return;	
	}
	Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ�ȷ��ɾ����",function(id) { 
		if("yes"==id){
			radow.doEvent('infoDelete');
		}else{
			return;
		}		
	});	
	
}
function loadadd(){
	var fabd00=document.getElementById('fabd00').value;
	if(fabd00==null || fabd00==''){
		$h.alert('ϵͳ��ʾ��','��ѡ�񷽰���',null,150);
 		return;	
	}
	radow.doEvent('saveFABD');
}

Ext.onReady(function(){
	$h.initGridSort('memberGrid',function(g){
		radow.doEvent('rolesort');
	});	
	
	$('#fabd00').val(parent.Ext.getCmp(subWinId).initialConfig.fabd00);
	parent.Ext.getCmp(subWinId).on('beforeclose',function(){
		realParent.radow.doEvent('initX');
	})
	var pgrid = Ext.getCmp('memberGrid')
	var bbar = pgrid.getTopToolbar();
 	 bbar.insertButton(0,[	
 							new Ext.Spacer({width:20}),
 							'���ƣ�',
							createSel2()
						]);
 	 
 	 
 	 
 	 Ext.getCmp('fabd02').on('blur',function(){
 		 if(this.getValue()==''){
 			 return;
 		 }
 		radow.doEvent('saveFabd02')
	 })
});
function createSel2(){
	var ets01_combo = new Ext.form.TextField({fieldLabel:'����', id:'fabd02'});
	return ets01_combo;
}
</script>
