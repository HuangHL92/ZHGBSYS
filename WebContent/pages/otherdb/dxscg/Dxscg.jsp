<%@page import="com.insigma.siis.local.epsoft.config.AppConfig"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>

<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>

<script  type="text/javascript">


var g_contextpath = '<%= request.getContextPath() %>';

function impPerson(){
	$h.showWindowWithSrc('simpleExpWin3', g_contextpath
			+ "/pages/otherdb/dxscg/DataVerify5.jsp?i=1",'���봰��',530,180);	
}

</script>
<div id="groupTreePanel"></div>

<odin:groupBox title="��ѯ����">
<table style="width: 100%;">
	<tr>
		<odin:textEdit property="cvo001q" label="����" ></odin:textEdit>
		<odin:select2 property="cvo002q" label="�Ա�" canOutSelectList="false" codeType="GB2261" ></odin:select2>
		<odin:textEdit property="cvo008q" label="ѡƸ���" ></odin:textEdit>
		<odin:textEdit property="cvo009q" label="�������" ></odin:textEdit>
	</tr>
</table>
</odin:groupBox>
<odin:editgrid2 property="memberGrid" hasRightMenu="false" title="��ѧ�������Ϣ" autoFill="true"  bbarId="pageToolBar"  url="/">
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="ckrow"/>
		<odin:gridDataCol name="cvo000"/>
		<odin:gridDataCol name="cvo001"/>
		<odin:gridDataCol name="cvo002c"/>
		<odin:gridDataCol name="cvo002"/>
		<odin:gridDataCol name="cvo003"/>
		<odin:gridDataCol name="cvo004"/>
		<odin:gridDataCol name="cvo005"/>
		<odin:gridDataCol name="cvo006c"/>
		<odin:gridDataCol name="cvo006"/>
		<odin:gridDataCol name="cvo007"/>
		<odin:gridDataCol name="cvo008"/>
		<odin:gridDataCol name="cvo009"/>
		<odin:gridDataCol name="cvo010"/>
		<odin:gridDataCol name="cvo011"/>
		
		<odin:gridDataCol name="a0000" isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn2></odin:gridRowNumColumn2>
		<odin:gridEditColumn dataIndex="ckrow"  width="30" header="selectall" gridName="memberGrid" editor="checkbox"  edited="true" align="center"/>
		<odin:gridEditColumn2 dataIndex="cvo000" width="50" hidden="true" editor="text" header="����" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="cvo001" width="50" header="����" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="cvo002" width="50" header="�Ա�" editor="text" edited="false"  align="center"/>
		
		<odin:gridEditColumn2 dataIndex="cvo003" width="50" header="��������" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="cvo004" width="50" header="�뵳ʱ��" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="cvo005" width="150" header="����ְ��" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="cvo006" width="80" header="ѧ��" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="cvo007" width="150" header="��ҵԺУ��רҵ" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="cvo008" width="50" header="ѡƸ���" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="cvo009" width="50" header="�������" editor="text" edited="false"  align="center"/>
		<odin:gridEditColumn2 dataIndex="cvo010" width="100" header="��ע" editor="text" edited="false" isLast="true" hidden="true"  />
	</odin:gridColumnModel>
</odin:editgrid2>

		



<odin:toolBar property="btnToolBar" applyTo="groupTreePanel">
	<odin:textForToolBar text="<h3>����</h3>" />
	<odin:fill />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="����" icon="image/icon021a2.gif" handler="loadadd" id="loadadd" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="�޸�" icon="image/icon021a6.gif" handler="infoUpdate"  id="infoUpdate"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="ɾ��" icon="image/icon021a3.gif"  handler="infoDelete" id="infoDelete" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="��ѯ" icon="images/search.gif" id="infoSearch" handler="infoSearch" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="����" icon="images/icon/exp.png" handler="impPerson" id="cxwth"/>
	<odin:separator isLast="true"></odin:separator>
</odin:toolBar>

<div style="display: none;">
<iframe id="frameid" src=""></iframe>
</div>
<script type="text/javascript">
Ext.onReady(function() {
	var viewSize = Ext.getBody().getViewSize();
	var memberGrid = Ext.getCmp('memberGrid');
	memberGrid.setHeight(viewSize.height-120);
	Ext.get('commForm').setWidth(viewSize.width);
	memberGrid.setWidth(viewSize.width);
	
	memberGrid.on('rowdblclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index)
		$h.openPageModeWin('loadadd','pages.otherdb.dxscg.Dxscginfo','�޸�',580,300,{'cvo000':rc.data.cvo000},g_contextpath);
	});
	
});

function loadadd(){
	$h.openPageModeWin('loadadd','pages.otherdb.dxscg.Dxscginfo','����',580,300,{'cvo000':''},g_contextpath);
}
function infoUpdate(){
	var record = getRecord('memberGrid');
	if(record==null){
		return;
	}
	var cvo000 = record.data.cvo000;
	$h.openPageModeWin('loadadd','pages.otherdb.dxscg.Dxscginfo','�޸�',580,300,{'cvo000':cvo000},g_contextpath);
}
function infoDelete(){//�Ƴ���Ա
	var grid=Ext.getCmp("memberGrid");
	var store = grid.getStore();
	var rowCount = store .getCount();
	var count = 0;
	var cvo000s = '';
	for(var i=0;i<rowCount;i++) {
		var record=store.getAt(i);
		if(record.data.ckrow == true){
			cvo000s = cvo000s + record.data.cvo000+",";
			count ++ ;
		}
	}
	if(count==0){
		odin.alert("�빴ѡ���ݣ�");
		return null;
	}
	
	$h.confirm("ϵͳ��ʾ��",'��ȷ��Ҫɾ����ѡ��������?',200,function(id) { 
		if("ok"==id){
			radow.doEvent('allDelete',cvo000s);
		}else{
			return false;
		}		
	});
}


function infoSearch(){
	radow.doEvent('memberGrid.dogridquery');
}

function getRecord(gridid){
	var grid=Ext.getCmp(gridid);
	var store = grid.getStore();
	var rowCount = store .getCount();
	var count = 0;
	var record = null;
	for(var i=0;i<rowCount;i++) {
		var record2=store.getAt(i);
		if(record2.data.ckrow == true){
			record = record2;
			count ++ ;
		}
	}
	if(count==0){
		odin.alert("�빴ѡһ�����ݣ�");
		return null;
	}
	if(count>1){
		odin.alert("��ֻ��ѡһ�����ݣ�");
		return null;
	}
	return record;
}

</script>


