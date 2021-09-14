<%@page import="com.insigma.siis.local.pagemodel.bzpj.PJGLPageModel"%>
<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.hzb.QXSLDBZHZB"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<script src="<%=request.getContextPath()%>/basejs/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>

<style>
#EGLInfo input{
	border: 1px solid #c0d1e3 !important;
}
.x-form-item{
overflow: visible!important;
}
</style>


<odin:toolBar property="btnToolBar" >
	<odin:fill />
	<odin:buttonForToolBar text="�޸�" icon="images/i_2.gif" id="editBtn" handler="editegl" />
	<odin:buttonForToolBar text="����" icon="images/add.gif" id="addBtn" handler="addegl" isLast="true"/>
</odin:toolBar>
<odin:editgrid2 property="memberGrid"  topBarId="btnToolBar" hasRightMenu="false" autoFill="true" forceNoScroll="true" bbarId="pageToolBar"  url="/">
	<odin:gridJsonDataModel>
	    
		<odin:gridDataCol name="xls00" />
		<odin:gridDataCol name="xls04"/>
		<odin:gridDataCol name="xls05"/>
		<odin:gridDataCol name="xls06"/>
		<odin:gridDataCol name="xls07" isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn2></odin:gridRowNumColumn2>
		<odin:gridEditColumn2 dataIndex="xls04" width="140" header="����" editor="text" edited="false" align="left"/>
		<odin:gridEditColumn2 dataIndex="xls06" width="140" header="����" editor="text" edited="false" align="left"/>
		<odin:gridEditColumn2 dataIndex="xls05" width="140" header="����" editor="text" edited="false" align="left"/>
		<odin:gridEditColumn2 dataIndex="xls00" width="60" header="����" editor="text" edited="false" renderer="delegl" isLast="true" hidden="true" align="center"/>
	</odin:gridColumnModel>
</odin:editgrid2>

<div id="EGLInfo">
	<div style="margin-left: 20px;margin-top: 10px;">
		<table>
		  <tr>
			<odin:textarea property="egl04" cols="70" rows="6" label="����" value="1s" colspan="4" />
		  </tr>
		  <tr>
		  	<odin:NewDateEditTag property="egl01"  label="����"/>
		  	<odin:select2 property="ets00"  label="ָ�귽��"  />
		  </tr>
		</table>
		<odin:hidden property="egl00"/>
		<div style="margin-left: 245px;margin-top: 15px;">
			<odin:button text="ȷ��" property="saveETCInfo" handler="saveEGLInfo" />
		</div>
	</div>
</div>
<script type="text/javascript">
function QXSPJGLInfo(egl00){
	var contextPath = '<%=request.getContextPath()%>';
	$h.showWindowWithSrc('QXSPJGL',contextPath + "/pages/bzpj/PJGLInfo.jsp?egl00="+egl00,'�ۺ�����',1800,1200,null,{maximizable:true,resizable:true},true);
}
Ext.onReady(function() {
	openEGLWin();
	hideWin();
	
	
	var viewSize = Ext.getBody().getViewSize();
	var memberGrid = Ext.getCmp('memberGrid');
	memberGrid.setHeight(viewSize.height);
	Ext.get('commForm').setWidth(viewSize.width);
	memberGrid.setWidth(viewSize.width);
	
	
	memberGrid.on('rowdblclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index)
		QXSPJGLInfo(rc.data.egl00);
	});
	
	
});

function addegl(){
	$('#egl01').val('');
	$('#egl01_1').val('');
	Ext.getCmp('ets00_combo').setValue('');
	$('#egl04').val('');
	$('#egl00').val('');
	openEGLWin();
}
function editegl(){
	var selectedRows = Ext.getCmp('memberGrid').getSelectionModel().getSelections();
	if(selectedRows.length!=1){
		$h.alert('','��ѡ��һ�����ݣ�');
		return;
	}
	var rc = selectedRows[0];
	Ext.getCmp('egl01_1').setValue(rc.data.egl01);
	$('#egl01').val(rc.data.egl01);
	odin.setSelectValue('ets00',rc.data.ets00);
	
	$('#egl04').val(rc.data.egl04);
	$('#egl00').val(rc.data.egl00);
	openEGLWin();
}
function delegl(value, params, record,rowIndex,colIndex,ds){
	var xls00 = record.data.xls00;
	
	return "<a href=\"javascript:deleteRow2(&quot;"+xls00+"&quot;)\">ɾ��</a>";
}
function deleteRow2(egl00){ 
	Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ�ȷ��ɾ����",function(id) { 
		if("yes"==id){
			radow.doEvent('allDelete',egl00);
		}else{
			return;
		}		
	});	
}

function openEGLWin(){
	var win = Ext.getCmp("addEGL");	
	if(win){
		win.show();	
		return;
	}
	win = new Ext.Window({
		title : '�ۺ���������',
		layout : 'fit',
		width : 550,
		height : 251,
		closeAction : 'hide',
		closable : true,
		modal : true,
		id : 'addEGL',
		collapsed:false,
		collapsible:false,
		bodyStyle : 'background-color:#FFFFFF',
		plain : true,
		titleCollapse:false,
		contentEl:"EGLInfo",
		listeners:{}
		           
	});
	win.show();
}


function saveEGLInfo(){
	radow.doEvent("addEGLInfo");
	
}



function hideWin(){
	var win = Ext.getCmp("addEGL");	
	if(win){
		win.hide();	
	}
}
</script>