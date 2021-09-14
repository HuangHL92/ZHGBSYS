<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<script type="text/javascript">
	var nodeId=window.parent.document.getElementById("nodeId").value; 
</script>
<odin:hidden property="nodeId"/>
<div id="gridDiv_list" >
<div id="toolDiv"></div>
</div>

<odin:toolBar property="btnToolBar1" applyTo="toolDiv">
	<odin:fill/>
	<odin:buttonForToolBar text="�·�" id="deliver" icon="images/icon_photodesk.gif" cls="x-btn-text-icon"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="����" id="recieve" handler="impTest" icon="images/folder_go.png" cls="x-btn-text-icon"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="�޸�" id="update" icon="images/add.gif" cls="x-btn-text-icon"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="�½�" id="add" icon="images/add.gif" cls="x-btn-text-icon" handler="addAction"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="ɾ��" id="delParam" icon="images/icon/delete.gif" cls="x-btn-text-icon"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="ˢ��" id="refreshBtn" icon="images/icon/refresh.png" cls="x-btn-text-icon" handler="refresh" />
</odin:toolBar>
<odin:editgrid property="list" applyTo="gridDiv_list"
	topBarId="btnToolBar1" isFirstLoadData="false" url="/" sm="checkbox" rowDbClick="update" autoFill="true">
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="selected"/>
		<odin:gridDataCol name="add_value_sequence"/>
		<odin:gridDataCol name="add_value_name"/>
		<odin:gridDataCol name="add_value_id"/>
		<odin:gridDataCol name="add_type_id"/>
		<odin:gridDataCol name="code_type"/>
		<odin:gridDataCol name="add_value_detail"/>
		<odin:gridDataCol name="isused"/>
		<odin:gridDataCol name="publish_status"/>
		<odin:gridDataCol name="op"/>
		<odin:gridDataCol name="multilineshow" isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn/>
		<odin:gridColumn header="selectall" gridName="list" dataIndex="selected" edited="true" editor="checkbox" width="45"/>
		<odin:gridEditColumn header="���" dataIndex="add_value_sequence" edited="false" editor="text" hidden="true"/>
		<odin:gridEditColumn header="������Ϣ������" dataIndex="add_value_name" edited="false" editor="text" width="80" align="center"/>
		<odin:gridEditColumn header="������Ϣ�����" dataIndex="add_value_id" edited="false" editor="text" hidden="true"/>
		<odin:gridEditColumn header="������Ϣ������" dataIndex="add_type_id" edited="false" editor="text" hidden="true"/>
		<odin:gridEditColumn header="���뼯����" dataIndex="code_type" edited="false" editor="text" align="center"  hidden="true"/>
		<odin:gridEditColumn header="��Ϣ������" dataIndex="add_value_detail" edited="false" editor="text" hidden="true"/>
		<odin:gridEditColumn header="�Ƿ�ʹ��" dataIndex="isused" edited="false" editor="select" width="80" selectData="['0','��'],['1','��']" align="center"/>
		<odin:gridEditColumn header="����״̬" dataIndex="publish_status" edited="false" editor="text" width="80" align="center"/>
		<odin:gridEditColumn header="����" dataIndex="op" edited="false" editor="text" width="80" align="center" renderer="operate"/>
		<odin:gridEditColumn header="����Ϣά������ʾ����" dataIndex="multilineshow" edited="false" editor="text" hidden="true" isLast="true"/>
	</odin:gridColumnModel>
</odin:editgrid>
<odin:window src="" modal="true" id="addNewValue" width="350" height="445" title="������Ϣ��" closable="true" maximizable="false"></odin:window>
<odin:window src="" modal="true" id="updateAddValue" width="400" height="425" title="������Ϣ��" closable="true" maximizable="false"></odin:window>
<odin:window src="" modal="true" id="deliverCue" width="500" height="380" title="������Ϣ���·�" closable="true" maximizable="false"></odin:window>
<odin:window src="" modal="true" id="RecieveCue" width="350" height="180" title="��Ϣ������" closable="true" maximizable="false"></odin:window>

<script type="text/javascript">
	
	Ext.onReady(function(){
		document.all.nodeId.value = nodeId;
		radow.doEvent('list.dogridquery');
	}, this, {
		delay : 500
	});
	
	function update(grid) {
		grid.getSelectionModel().each(function(rec){
			radow.doEvent('update',rec.get('add_value_id'));
		});
	}
	
	//�޸İ�ť�¼�
	function updateOne() {
		
	}
	
	//������ť�¼�
	function addAction(){
		var nodeId = document.all.nodeId.value;
		radow.doEvent("addAction",nodeId);
	}
	
	function refresh(){
		getSNode();
	}

	function getSNode(){
		var node = window.parent.getNode();
		if(node == null)
			document.all.nodeId.value = '';
		else
			document.all.nodeId.value = node.id; 
		var value = document.all.nodeId.value;
		if(value == '' || value == 'S000000')
			document.all.gridT.innerHTML = '';
		else
			radow.doEvent('disPlayDsa', value); 
	}
	
    function generateUUID(){
        var d = new Date().getTime();
        var uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
            var r = (d + Math.random()*16)%16 | 0;
            d = Math.floor(d/16);
            return (c=='x' ? r : (r&0x7|0x8)).toString(16);
        });
        return uuid;
    } 
    
    function operate(value, params, record, rowIndex, colIndex, ds){
    	if(value == null){
    		return;
    	}
    	return '<a href="javascript:void(0);" onclick="radow.doEvent(\'publish\',\'' + record.get('add_value_id') + '\');">����</a>';
    }
    
    function impTest() {
		var win = odin.ext.getCmp('RecieveCue');
		win.setTitle('���봰��');
		odin.showWindowWithSrc('RecieveCue', contextPath
				+ "/pages/sysmanager/ZWHZYQ_001_006/ZWHZYQ_001_006_001/AddValueRecieveCue.jsp");
	}
	//����ȫ������
	function exportAll(){
	    odin.grid.menu.expExcelFromGrid('list', null, null,null, false);
    }
	Ext.onReady(function() {
		//ҳ�����
		Ext.getCmp('list').setHeight(document.body.clientHeight*0.97);
		Ext.getCmp('list').setWidth((Ext.isIE?Ext.getBody().getViewSize().width:window.innerWidth)-objTop(document.getElementById('forView_list'))[1]-2); 
		//document.getElementById("panel_content").style.width = Ext.getCmp('list').getWidth() + "px";
		//document.getElementById("panel_content").style.height = document.body.clientHeight + "px";
		//document.getElementById("toolDiv").style.width = Ext.getCmp('list').getWidth()-1 + "px";
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
</script>