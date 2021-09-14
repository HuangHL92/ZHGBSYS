<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ include file="/comOpenWinInit2.jsp" %>

<script type="text/javascript" src="commform/basejs/json2.js"></script>
<odin:hidden property="checkedgroupid" />
<odin:hidden property="cueRowIndex" />
<odin:hidden property="mark" /> <!--  �Ƿ�����ˢ��grid�ı�� -->

<odin:hidden property="appointment" />
<odin:hidden property="selectByInputYnIdHidden" />

<odin:hidden property="selectType" />

<odin:hidden property="selectUnitId" />

<div id="addPublishContent">
	<table style="height: 95%;width: 100%;" >
		<tr>
			<odin:select2 property="meetingname" label="������������������"  onchange="updatepublish"></odin:select2>
			<td rowspan="3"></td>
			<odin:textEdit property="xmeetingname"  label="���Ƶ��Ļ�������"  disabled="true"></odin:textEdit>
		</tr>
		<tr>
			<odin:select2 property="agendaname" label="������������������" onchange="updatetitle" disabled="true"></odin:select2>
			<odin:textEdit property="xagendaname" label="���Ƶ�����������" disabled="true"></odin:textEdit>
		</tr>
		<tr>
			<odin:select2 property="titlename" label="������������������" onchange="updateperson" disabled="true"></odin:select2>
			<odin:textEdit property="xtitlename" label="���Ƶ��ı�������" disabled="true"></odin:textEdit>
		</tr>
		<tr style="width: 100%;" align="center">
			<td width="47%" height="100%" colspan="2">
				
				<div id="selectByPersonIdDiv" >
					<odin:editgrid property="gridcq" title="��ѡ�б�"  width="300" height="450" pageSize="9999"
						autoFill="false" >
						<odin:gridJsonDataModel>
							<odin:gridDataCol name="personcheck"  />
							<odin:gridDataCol name="a0000" />
							<odin:gridDataCol name="a0101" />
							<odin:gridDataCol name="a0104" />
							<odin:gridDataCol name="a0107" />
							<odin:gridDataCol name="sh000" />
							<odin:gridDataCol name="a0192a" isLast="true"/>
						</odin:gridJsonDataModel>
						<odin:gridColumnModel>
							<odin:gridRowNumColumn></odin:gridRowNumColumn>
							<odin:gridEditColumn2 header="selectall" width="0"
								editor="checkbox" dataIndex="personcheck" edited="true" hidden="true"
								hideable="false" gridName="persongrid" />
							<odin:gridEditColumn header="����" editor="text" hidden="true" edited="true" dataIndex="a0000" />
							<odin:gridColumn dataIndex="a0101" header="����" width="55" align="center" />
							<odin:gridEditColumn2 dataIndex="a0104" header="�Ա�" width="40" align="center" editor="select" edited="false" codeType="GB2261" />
							<odin:gridColumn dataIndex="a0107" header="��������" width="65" align="center" />
							<odin:gridColumn header="�ϻ�����" editor="text" hidden="true" dataIndex="sh000" />
							<odin:gridColumn dataIndex="a0192a" edited="false" header="��λְ��" width="170"  align="center" isLast="true" />
						</odin:gridColumnModel>
						<odin:gridJsonData>
							{
						        data:[]
						    }
						</odin:gridJsonData>
					</odin:editgrid>
				</div>

			</td>
			<td style="width: 6%;height: 100%;" align="center">
				<div id='rigthBtn'  style="display: none"></div>
				<br>
				<div id='rigthAllBtn' title="ȫѡ"></div>
				<br>
				<div id='liftBtn' style="display: none"></div>
				<br>
				<div id='liftAllBtn' title="ȫѡ"></div>
			</td>
			<td width="47%;" height="100%;" align="center" colspan="2">
				<div id="selectByPersonIdDiv2">
					<odin:editgrid property="selectName" title="����б�" width="300" height="450" autoFill="false" >
						<odin:gridJsonDataModel>
								<odin:gridDataCol name="personcheck2"  />
								<odin:gridDataCol name="a0000" />
								<odin:gridDataCol name="a0101" />
								<odin:gridDataCol name="a0104" />
								<odin:gridDataCol name="a0107" />
								<odin:gridDataCol name="sh000" />
								<odin:gridDataCol name="a0192a" isLast="true"/>
							</odin:gridJsonDataModel>
							<odin:gridColumnModel>
								<odin:gridRowNumColumn></odin:gridRowNumColumn>
								<odin:gridEditColumn2 header="selectall" width="0"
													  hidden="true"	editor="checkbox" dataIndex="personcheck2" edited="true"
									hideable="false" gridName="persongrid" />
								<odin:gridEditColumn header="����" editor="text" hidden="true" edited="true" dataIndex="a0000" />
								<odin:gridColumn dataIndex="a0101" header="����" width="55" align="center" />
								<odin:gridEditColumn2 dataIndex="a0104" header="�Ա�" width="40" align="center" editor="select" edited="false" codeType="GB2261" />
								<odin:gridColumn dataIndex="a0107" header="��������" width="65" align="center" />
								<odin:gridColumn header="�ϻ�����" editor="text" hidden="true" dataIndex="sh000" />
								<odin:gridColumn dataIndex="a0192a" edited="false" header="��λְ��" width="170"  align="center" isLast="true" />
							</odin:gridColumnModel>
							<odin:gridJsonData>
								{
							        data:[]
							    }
							</odin:gridJsonData>
					</odin:editgrid>
				</div>

			</td>
		</tr>
	</table>	
</div>
<odin:toolBar property="btnToolBar">
	<odin:fill/>
	<odin:buttonForToolBar id="btnSave" text="����"  icon="images/save.gif" cls="x-btn-text-icon" isLast="true"/>
</odin:toolBar>
<odin:panel contentEl="addPublishContent" property="addPublishPanel" topBarId="btnToolBar"></odin:panel>
<odin:hidden property="publishid"/>
<odin:hidden property="meetingid"/>
<odin:hidden property="titleid"/>
<script type="text/javascript">
function saveCallBack(){
	parent.reloadSelData();
	window.close();
}

document.onkeydown=function() { 
	
	if (event.keyCode == 13) { 
		if (document.activeElement.type == "textarea") {
			toDOQuery();
			return false;
		}
	}else if(event.keyCode == 27){	//����ESC
	        return false;   
	}
}

/***
 *   ������Ա��Ϣ
*
*/
function saveSelect(){  
	radow.doEvent('saveSelect');
}
function rigthBtnFun(){
	radow.doEvent('rigthBtn.onclick');
}
function rigthAllBtnFun(){
	radow.doEvent('rigthAllBtn.onclick');
}
function liftBtnFun(){
	radow.doEvent('liftBtn.onclick');
}
function liftAllBtnFun(){
	radow.doEvent('liftAllBtn.onclick');
}

Ext.onReady(function() {
	var viewSize = Ext.getBody().getViewSize();
	document.getElementById('meetingid').value = parent.document.getElementById('meetingid').value;
	document.getElementById('xmeetingname').value = parent.document.getElementById('meetingname_combo').value;
	document.getElementById('publishid').value = parent.document.getElementById('publish_id').value;
	document.getElementById('xagendaname').value = parent.document.getElementById('publishname').value;
	document.getElementById('titleid').value = parent.document.getElementById('title_id').value;
	document.getElementById('xtitlename').value = parent.document.getElementById('titlename').value;
	new Ext.Button({
		icon : 'images/icon/rightOne.png',
		id:'btn1',
	    cls :'inline pl',
	    renderTo:"rigthBtn",
	    handler:function(){
	    	rigthBtnFun();
	    }
	});
	new Ext.Button({
		icon : 'images/icon/rightAll.png',
		id:'btn2',
	    cls :'inline pl',
	    renderTo:"rigthAllBtn",
	    handler:function(){
	    	rigthAllBtnFun();
	    }
	});
	new Ext.Button({
		icon : 'images/icon/leftOne.png',
		id:'btn3',
	    cls :'inline pl',
	    renderTo:"liftBtn",
	    handler:function(){
	    	liftBtnFun();
	    }
	});
	new Ext.Button({
		icon : 'images/icon/leftAll.png',
		id:'btn4',
	    cls :'inline pl',
	    renderTo:"liftAllBtn",
	    handler:function(){
	    	liftAllBtnFun();
	    }
	});	
});


Ext.onReady(function() {
	var gridcq = Ext.getCmp("gridcq");
	var selectName = Ext.getCmp("selectName");
	var gStore = gridcq.getStore();
	var sStore = selectName.getStore();
    gStore.getModifiedRecords();
	gridcq.on("rowclick",function(o, index, o2){
		var rowData = gStore.getAt(index);
		var count = sStore.getCount();
		var flag = true;
		for(var i=0;i<count;i++){
			record = sStore.getAt(i);
			if(rowData.data.a0000==record.data.a0000){
				flag = false;
				break;
			}
		}
		if(flag){
			sStore.insert(sStore.getCount(),rowData);
		}
		gStore.remove(rowData);
		gridcq.view.refresh();
	});

	selectName.on("rowclick",function(o, index, o2){
		var rowData = sStore.getAt(index);
		var count = gStore.getCount();
		var flag = true;
		for(var i=0;i<count;i++){
			record = gStore.getAt(i);
			if(rowData.data.a0000==record.data.a0000){
				flag = false;
				break;
			}
		}
		if(flag){
			gStore.insert(gStore.getCount(),rowData);
		}

		sStore.remove(rowData);
		selectName.view.refresh();
	});

});

function updatepublish(){
	var xmeetingname=document.getElementById('meetingname').value;
	if(xmeetingname!=null){
		document.getElementById('agendaname').value = "";
		document.getElementById('titlename').value = "";
		document.getElementById('agendaname_combo').value = "";
		document.getElementById('titlename_combo').value = "";
		Ext.getCmp('agendaname_combo').setDisabled(false);
		Ext.getCmp('titlename_combo').setDisabled(true);
		radow.doEvent("updatepublish");
	}else{
		document.getElementById('agendaname').value = "";
		document.getElementById('titlename').value = "";
		document.getElementById('agendaname_combo').value = "";
		document.getElementById('titlename_combo').value = "";
		Ext.getCmp('agendaname_combo').setDisabled(true);
		Ext.getCmp('titlename_combo').setDisabled(true);
	}
}

function updatetitle(){
	var agendaname=document.getElementById('agendaname').value;
	if(agendaname!=null){
		document.getElementById('titlename').value = "";
		document.getElementById('titlename_combo').value = "";
		Ext.getCmp('titlename_combo').setDisabled(false);
		radow.doEvent("updatetitle");
	}else{
		document.getElementById('titlename').value = "";
		document.getElementById('titlename_combo').value = "";
		Ext.getCmp('titlename_combo').setDisabled(true);
	}
	
}

function updateperson(){
	var titlename=document.getElementById('titlename').value;
	radow.doEvent("gridcq.dogridquery");
}

function saveCallBack(){
	parent.reloadPerGrid();
	window.close();
}

</script>
