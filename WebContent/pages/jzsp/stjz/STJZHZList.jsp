<%@page import="com.insigma.siis.local.business.helperUtil.SysManagerUtils"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>

<script  type="text/javascript">
var g_contextpath = '<%= request.getContextPath() %>';

<%
String curUser = SysManagerUtils.getUserId();
String curGroup = SysManagerUtils.getUserGroupid();

%>

function expFile(value, params, record, rowIndex, colIndex, ds) {
	if(record.data.spp08=='0'){
		if(record.data.spp11=='<%=curUser%>'){
			return "<div align='center' width='100%' ><font color=blue>"
			+ "<a style='cursor:pointer;' onclick=\"infoUpdate1('"+value+"');\">����</a>"
			+ "</font></div>";
		}else{
			return "<div align='center' width='100%' ><font color=blue>"
			+ "<a style='cursor:pointer;' onclick=\"infoUpdate('"+value+"');\">����</a>"
			+ "</font></div>";
		}
	}else if(record.data.spp08=='1'){//������
		if(record.data.spp09=='<%=curUser%>'||record.data.spp10=='<%=curGroup%>'){
			return "<div align='center' width='100%' ><font color=blue>"
			+ "<a style='cursor:pointer;' onclick=\"infoUpdate1('"+value+"');\">����</a>"
			+ "</font></div>";
		}else{
			return "<div align='center' width='100%' ><font color=blue>"
			+ "<a style='cursor:pointer;' onclick=\"infoUpdate('"+value+"');\">����</a>"
			+ "</font></div>";
		}
	}else{
		if(record.data.spp08=='2'&&record.data.spp12!='1'&&record.data.spp11=='<%=curUser%>'){//����ͨ�� δ����
			return "<div align='center' width='100%' ><font color=blue>"
			+ "<a style='cursor:pointer;' onclick=\"infoUpdate('"+value+"');\">����</a><span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>"
			+ "<a style='cursor:pointer;' onclick=\"\">ǩ��</a>"
			+ "</font></div>";
		}else{
			return "<div align='center' width='100%' ><font color=blue>"
			+ "<a style='cursor:pointer;' onclick=\"infoUpdate('"+value+"');\">����</a>"
			+ "</font></div>";
		}
		
	}
	return "";
}


</script>

			<!-- record_batch -->
<div id="groupTreePanel"></div>
<odin:hidden property="spp00"/>
<odin:hidden property="a0000"/>
<table style="width: 250px;">
	<tr>
		<odin:select2 property="spp08" canOutSelectList="false"  label="����״̬" onchange="infoSearch" value="99" data="['all','ȫ��'],['99','����'],['1','������'],['2','����ͨ��'],['3','����δͨ��']" />
	</tr>
</table>
<odin:editgrid2 property="memberGrid" load="selectRow" hasRightMenu="false" title="���ż�ְ���������" autoFill="true"  bbarId="pageToolBar"  url="/">
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="spp00" />
		<odin:gridDataCol name="spp02" />
		<odin:gridDataCol name="spp03" />
		<odin:gridDataCol name="spp04" />
		<odin:gridDataCol name="spp05" />
		<odin:gridDataCol name="spp08" />
		<odin:gridDataCol name="spp09" />
		<odin:gridDataCol name="spp10" />
		<odin:gridDataCol name="spp11" />
		<odin:gridDataCol name="spp12" />
		<odin:gridDataCol name="sp0108" isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn2></odin:gridRowNumColumn2>
		<odin:gridEditColumn2 dataIndex="spp02" width="110" editor="text" header="��������" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="spp03" width="50" header="�Ǽ�ʱ��" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="spp04" width="70" header="�������" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="spp05" width="140" header="���쵼���" editor="text" edited="false"  align="center"/>
		
		<odin:gridEditColumn2 dataIndex="spp08" width="70" header="״̬" editor="select" selectData="['0','�Ǽ���'],['1','������'],['2','����ͨ��'],['3','����δͨ��']" edited="false"  align="center" />
		<odin:gridEditColumn2 dataIndex="spp00" width="70" header="����" editor="text"  edited="false" renderer="expFile" align="center" isLast="true"  />
		
	</odin:gridColumnModel>
</odin:editgrid2>

		



<odin:toolBar property="btnToolBar" applyTo="groupTreePanel">
	<odin:fill />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="ɾ��" icon="image/icon021a2.gif" id="deleteA" />
</odin:toolBar>


<script type="text/javascript">
Ext.onReady(function() {
	var viewSize = Ext.getBody().getViewSize();
	var memberGrid = Ext.getCmp('memberGrid');
	memberGrid.setHeight(viewSize.height-120);
	Ext.get('commForm').setWidth(viewSize.width);
	memberGrid.setWidth(viewSize.width);
	
	memberGrid.on('rowclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index)
		document.getElementById('spp00').value = rc.data.spp00;
	});
	

	
});

function infoUpdate(spp00){
	if(!spp00){
		Ext.Msg.alert("ϵͳ��ʾ","��ѡ��һ�����ݣ�");
		return;
	}
	$h.openPageModeWin('STJZHZView','pages.jzsp.jgld.ViewSTJZHZ&spp00='+spp00,'�쵼�ɲ����ż����������ܱ�',1150,800,{spp00:spp00},g_contextpath);
}
function infoUpdate1(spp00){
	if(!spp00){
		Ext.Msg.alert("ϵͳ��ʾ","��ѡ��һ�����ݣ�");
		return;
	}
	$h.openPageModeWin('STJZHZ','pages.jzsp.jgld.STJZHZ','�쵼�ɲ����ż����������ܱ�',1150,800,{spp00:spp00},g_contextpath);
}
function infoDelete(){//�Ƴ���Ա
	var sm = Ext.getCmp("memberGrid").getSelectionModel();
	
	if(!sm.hasSelection()){
		Ext.Msg.alert("ϵͳ��ʾ","��ѡ��һ�����ݣ�");
		return;
	}
	var selections = sm.getSelections();
	var rc = selections[0];
	if(rc.data.spp08!='0'){
		return;
	}
	$h.confirm("ϵͳ��ʾ��","ɾ������ֻɾ������������Ϣ�����������Ա��Ϣ��ȷ��ɾ��?",300,function(id) { 
		if("ok"==id){
			radow.doEvent('allDelete',rc.data.spp00);
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
			if(rc.data.sp0100==$('#sp0100').val()){
				
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
function infoSearch(rc){
	
	radow.doEvent('memberGrid.dogridquery');
}
</script>


