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
	if(record.data.sp0114=='0'){
		return "<div align='center' width='100%' ><font color=blue>"
		+ "<a style='cursor:pointer;' onclick=\"infoUpdate();\">�޸�</a>"
		+ "</font></div>";
	}else if(record.data.sp0114=='1'){//������
		if(record.data.spb03=='<%=curUser%>'||record.data.spb04=='<%=curGroup%>'){
			return "<div align='center' width='100%' ><font color=blue>"
			+ "<a style='cursor:pointer;' onclick=\"infoUpdate();\">����</a>"
			+ "</font></div>";
		}else{
			return "<div align='center' width='100%' ><font color=blue>"
			+ "<a style='cursor:pointer;' onclick=\"infoUpdate();\">����</a>"
			+ "</font></div>";
		}
	}else{
		return "<div align='center' width='100%' ><font color=blue>"
		+ "<a style='cursor:pointer;' onclick=\"infoUpdate();\">����</a>"
		+ "</font></div>";
	}
	return "";
}


</script>

			<!-- record_batch -->
<div id="groupTreePanel"></div>
<odin:hidden property="sp0100"/>
<odin:hidden property="a0000"/>
<table style="width: 250px;">
	<tr>
		<odin:select2 property="sp0114" canOutSelectList="false"  label="����״̬" onchange="infoSearch" value="99" data="['all','ȫ��'],['99','����'],['1','������'],['2','����ͨ��'],['3','����δͨ��']" />
	</tr>
</table>
<odin:editgrid2 property="memberGrid" load="selectRow" hasRightMenu="false" title="���ż�ְ�����б�" autoFill="true"  bbarId="pageToolBar"  url="/">
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="sp0100" />
		<odin:gridDataCol name="mcheck" />
		<odin:gridDataCol name="a0000"/>
		<odin:gridDataCol name="sp0102"/>
		<odin:gridDataCol name="sp0103"/>
		<odin:gridDataCol name="sp0104"/>
		<odin:gridDataCol name="sp0106"/>
		<odin:gridDataCol name="sp0107" />
		<odin:gridDataCol name="spb02" />
		<odin:gridDataCol name="spb03" />
		<odin:gridDataCol name="spb04" />
		<odin:gridDataCol name="sp0114" />
		<odin:gridDataCol name="sp0108" isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn2></odin:gridRowNumColumn2>
		<odin:gridEditColumn2 locked="true" header="selectall" width="40"
							editor="checkbox" dataIndex="mcheck" edited="true"
							hideable="false" gridName="memberGrid" 
							 menuDisabled="true" /> 
		<odin:gridEditColumn2 dataIndex="sp0102" width="110" editor="text" header="����" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="sp0103" width="50" header="�Ա�" editor="select" codeType="GB2261" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="sp0104" width="70" header="��������" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="sp0106" width="140" header="���ε�λ��ְ��" editor="text" edited="false"  align="center"/>
		<odin:gridEditColumn2 dataIndex="sp0107" width="140" header="����ְ��" editor="text" edited="false"  align="center"/>
		<odin:gridEditColumn2 dataIndex="sp0108" width="50" header="����" editor="text" edited="false"  align="center"/>
		
		<odin:gridEditColumn2 dataIndex="sp0114" width="70" header="״̬" editor="select" selectData="['0','δ����'],['1','������'],['2','����ͨ��'],['3','����δͨ��']" edited="false"  align="center" />
		<odin:gridEditColumn2 dataIndex="sp0100" width="70" header="����" editor="text"  edited="false" renderer="expFile" align="center" isLast="true"  />
		
	</odin:gridColumnModel>
</odin:editgrid2>

		



<odin:toolBar property="btnToolBar" applyTo="groupTreePanel">
	<odin:fill />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="����" icon="image/icon021a2.gif" handler="loadadd" id="loadadd" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="����" icon="image/icon021a6.gif" handler="infoUpdate"  id="infoUpdate" isLast="true"/>
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
		document.getElementById('sp0100').value = rc.data.sp0100;
		document.getElementById('a0000').value = rc.data.a0000;
	});
	
	memberGrid.on('rowdblclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index);
		document.getElementById('sp0100').value = rc.data.sp0100;
		if(rc.data.sp0114!='0'){//�ǵǼ�״̬
			$h.openPageModeWin('JGLDView','pages.jzsp.jgld.ApprJGLD&sp0100='+rc.data.sp0100,'���������쵼�ɲ�����������',750,800,{sp0100:rc.data.sp0100},g_contextpath);
		}else{
			$h.openPageModeWin('JGLDAdd','pages.jzsp.jgld.AddJGLD','���������쵼�ɲ�����������',750,800,{sp0100:rc.data.sp0100},g_contextpath);
		}
	});
	
});
function loadadd(){//��������
	var peopleInfoGrid =Ext.getCmp('memberGrid');
	var store = peopleInfoGrid.store;
	var sp0100s = "";
	for(var i = 0; i < store.getCount(); i++){
		var rowData = store.getAt(i);
		if(rowData.data.mcheck == true){
			sp0100s = sp0100s + rowData.data.sp0100 + ",";
		}
	} 
	if(sp0100s==''){
		Ext.Msg.alert("ϵͳ��ʾ","��ѡ�����ݣ�");
		return;
	}
	$h.openPageModeWin('STJZHZ','pages.jzsp.jgld.STJZHZ','�쵼�ɲ����ż������������',1150,800,{sp0100s:sp0100s},g_contextpath);
}
function infoUpdate(){
	var sp0100 = document.getElementById('sp0100').value;
	var sm = Ext.getCmp("memberGrid").getSelectionModel();
	
	if(!sm.hasSelection()){
		Ext.Msg.alert("ϵͳ��ʾ","��ѡ��һ�����ݣ�");
		return;
	}
	var selections = sm.getSelections();
	var rc = selections[0];
	if(rc.data.sp0114!='0'){//�ǵǼ�״̬
		$h.openPageModeWin('JGLDView','pages.jzsp.jgld.ApprJGLD&sp0100='+rc.data.sp0100,'���������쵼�ɲ�����������',750,800,{sp0100:rc.data.sp0100},g_contextpath);
	}else{
		$h.openPageModeWin('JGLDAdd','pages.jzsp.jgld.AddJGLD','���������쵼�ɲ�����������',750,800,{sp0100:rc.data.sp0100},g_contextpath);
	}
}
function infoDelete(){//�Ƴ���Ա
	var sm = Ext.getCmp("memberGrid").getSelectionModel();
	
	if(!sm.hasSelection()){
		Ext.Msg.alert("ϵͳ��ʾ","��ѡ��һ�����ݣ�");
		return;
	}
	var selections = sm.getSelections();
	var rc = selections[0];
	if(rc.data.sp0114!='0'){
		return;
	}
	$h.confirm("ϵͳ��ʾ��",'ȷ��ɾ��������Ϣ��'+rc.data.sp0102+"?",300,function(id) { 
		if("ok"==id){
			radow.doEvent('allDelete',rc.data.sp0100);
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
	var sp0114 = $('#sp0114').val();
	if(typeof rc!='undefined'){
		sp0114 = rc.data.key;
	}
	if(sp0114=='99'){
		Ext.getCmp('loadadd').enable();
	}else{
		Ext.getCmp('loadadd').disable();
	}
	radow.doEvent('memberGrid.dogridquery');
}
</script>


