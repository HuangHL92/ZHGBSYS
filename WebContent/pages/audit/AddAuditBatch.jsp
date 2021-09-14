<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<%
	String ctxPath = request.getContextPath();
%>
<html>
<head>
<link type="text/css" rel="stylesheet" href="<%=ctxPath%>/pages/huiyi/static/layui/css/layui.css"/>
<script src="<%=request.getContextPath()%>/pages/huiyi/static/layui/lay/modules/layer.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=ctxPath%>/basejs/helperUtil.js"></script>

<script src="<%=ctxPath%>/picCut/js/jquery-1.4.2.js" type="text/javascript"></script>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<script type="text/javascript">var contextPath = "<%=ctxPath%>";</script>
<title>�����Ա</title>
<style type="text/css">
#tablef{width:430px;position:relative;left:8px;}

#popDiv {
   display: none;
   width: 765px;
   height:150px;
   overflow-y:scroll;
   border: 1px #74c0f9 solid;
   background: #FFF;
   position: absolute;
   left: 97px;
   margin-top:76px;
   color: #323232;
   z-index:321;
}   

div#bottomDiv .x-btn {
    background-color: #2196f3!important;
    border-radius: 30px;
    width: 156px!important;
}
</style>
</head>
<%@include file="/comOpenWinInit.jsp" %>
<body style="overflow-x:hidden">
<odin:base>


 <odin:groupBox property="wjxx" title="������Ϣ" >
<table border="0" id="myform2" align="center" width="100%"  cellpadding="0" cellspacing="0">
<odin:tabLayOut />
    <tr>
	 <odin:textEdit property="auditBatchNo"  label="����"   />
	 <odin:select2 property="batchType"  label="��������"   codeType="AUDIT_TYPE" />
	 <odin:textEdit property="auditDeptName"  label="����"  disabled="true" />
	 <odin:hidden property="auditDept"/>
	 
    </tr>
    <tr>
    <odin:textEdit property="deptSubTime"  label="�����ύʱ��"  disabled="true" />
	 <odin:textEdit property="auditSubTime"  label="�����ύʱ��"  disabled="true" />
	<%--  <odin:textEdit property="feedbacktime"  label="����ʱ��"  disabled="true" /> --%>
	 
    </tr>
</table>
</odin:groupBox> 
<div id="toolDiv"></div>
<odin:groupBox property="fbinfo" title="��Ա�б�" >
 <div id="gridDiv" style="width: 100%;height:300px;overflow: auto;">
	<odin:editgrid2 property="gzGrid"   height="300"
			hasRightMenu="false" autoFill="true"  url="/">
				<odin:gridJsonDataModel>
					<odin:gridDataCol name="oid"/>
					<odin:gridDataCol name="a0000"/>
					<odin:gridDataCol name="a0101"/>
					<odin:gridDataCol name="a0184"/>
					<odin:gridDataCol name="family"/>
					<odin:gridDataCol name="a0192a" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn2></odin:gridRowNumColumn2>
					<odin:gridEditColumn2 dataIndex="oid" width="10" editor="text" header="����" hidden="true"/>
					<odin:gridEditColumn2 dataIndex="a0000" width="10" editor="text" header="a0000" hidden="true"/>
					<odin:gridEditColumn2 dataIndex="a0101" width="30" header="����" editor="text" edited="false"  sortable="false" align="center"/>
					<odin:gridEditColumn2 dataIndex="a0184" width="50" header="���֤" editor="text" edited="false"   sortable="false" align="center" />
					<odin:gridEditColumn2 dataIndex="a0192a" width="80" header="��λְ��" editor="text" edited="false"  sortable="false" align="center"  />
					<odin:gridEditColumn2 dataIndex="family" width="80" header="��ͥ��Ա" editor="text" edited="false"  sortable="false" align="center"  />
					<odin:gridEditColumn2 dataIndex="oid" width="30" header="����" editor="text" edited="false"  sortable="false" align="center" isLast="true" renderer="renderOption" />
				</odin:gridColumnModel>
			</odin:editgrid2>
</div>
</odin:groupBox>
 <div id="bottomDiv" style="width: 100%;">
		<table style="width: 100%; background-color: #cedff5">
			<tr align="center">
				<td>
					<table>
						<tr>
							<td>
								<odin:button text="����" property="save" ></odin:button>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
</div>
<odin:toolBar property="btnToolBar" applyTo="toolDiv">
	<odin:fill />
	<odin:buttonForToolBar text="����" icon="images/icon/exp.png"  handler="UpBtn" id="UpBtn"/>
	 <odin:separator></odin:separator>
	<odin:buttonForToolBar text="����" icon="images/icon/exp.png"  handler="DownBtn"  id="DownBtn"/>
	 <odin:separator></odin:separator>
	<odin:buttonForToolBar text="ɾ��" icon="images/delete.png"  handler="deleteColumn" id="deleteColumn"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="������Ա" icon="images/icon/exp.png" handler="impExcel"  id="impExcel"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="������ʡ����Ա" icon="images/icon/exp.png" handler="addPerson"  id="addPerson"/>
	 <odin:separator></odin:separator>
	<odin:buttonForToolBar text="ѡ����Ա" icon="images/icon/exp.png" handler="impData" id="impData" isLast="true" />
</odin:toolBar>
<script>

function doCloseWin(){
	parent.odin.ext.getCmp('editPerson').close();
}

function loadGrid(){
	radow.doEvent('gzGrid.dogridquery');
}


function addPerson(){
	$h.openWin('AddAuditPerson','pages.audit.AddAuditPerson','������Ա ',500, 180,null,contextPath,window,
			{maximizable:false,resizable:false});
}



function impData(){
	$h.openWin('findById','pages.audit.PersonQueryByName','������/���֤��ѯ ',900, 450,null,contextPath,window,
			{maximizable:false,resizable:false});
}

function impExcel(){
	var id=document.getElementById("subWinIdBussessId").value;
	if(id==""){
		alert("���ȱ���");
		return;
	}
	$h.showWindowWithSrc2('AuditExcelImp', contextPath+ "/pages/audit/AuditExcelImp.jsp", '���봰��', 600, 200, window,
				{maximizable:false,resizable:false,draggable:false}, true); 
}



function deleteColumn(){
	var gzGrid = odin.ext.getCmp('gzGrid');
	var store = gzGrid.getStore();
	var sm = gzGrid.getSelectionModel();
	var gzSelections = sm.getSelections();//��ȡ��ǰ��
	if(gzSelections.length==0){
		alert("��ѡ����");
		return;
	}
	$.each(gzSelections,function (i, gzSelection){
		store.remove(store.getById(gzSelection.id));
	});
	store.commitChanges();
}

function saveCallBack(msg){
	odin.alert(msg,function(){
		window.realParent.radow.doEvent('memberGrid.dogridquery');
		parent.odin.ext.getCmp('AuditBatch').close();
	});
}
function queryByNameAndIDS(list){
	
	radow.doEvent('queryByNameAndIDS',list);
}

function addColumnWithData(data){

		$.each(data,function (i, item){
			doAddColumnData(item);
		});
}

function doAddColumnData(data){
	var grid = odin.ext.getCmp('gzGrid');
	var store = grid.getStore();
	var p = new Ext.data.Record({  
		oid:data["oid"],a0000:data["a0000"],a0101:data["a0101"],a0184:data["a0184"],a0192a:data["a0192a"]
    });
	store.insert(store.getCount(), p);
	store.commitChanges();
}





function UpBtn(){	
	var grid = odin.ext.getCmp('gzGrid');
	
	var sm = grid.getSelectionModel().getSelections();
	var store = grid.store;
	//alert(store.getCount());
	
	if (sm.length<=0){
		alert('��ѡ����Ҫ�������Ա!')
		return;	
	}
	if(sm.length>1){
		alert('ֻ��ѡ��һ����Ҫ�������Ա!')
		return;
	}
	var selectdata = sm[0];  //ѡ�����еĵ�һ��
	var index = store.indexOf(selectdata);
	if (index==0){
		alert('����Ա�Ѿ��������!')
		return;
	}
	
	store.remove(selectdata);  //�Ƴ�
	store.insert(index-1, selectdata);  //���뵽��һ��ǰ��
	
	grid.getSelectionModel().selectRow(index-1,true);  //ѡ�����ƶ������	
	
	grid.getView().refresh();
}

function DownBtn(){	
	var grid = odin.ext.getCmp('gzGrid');
	
	var sm = grid.getSelectionModel().getSelections();
	var store = grid.store;
	if (sm.length<=0){
		alert('��ѡ����Ҫ�������Ա!')
		return;	
	}
	if(sm.length>1){
		alert('ֻ��ѡ��һ����Ҫ�������Ա!')
		return;
	}
	var selectdata = sm[0];  //ѡ�����еĵ�һ��
	var index = store.indexOf(selectdata);
	var total = store.getCount();
	if (index==(total-1) ){
		alert('����Ա�Ѿ����������!')
		return;
	}
	
	store.remove(selectdata);  //�Ƴ�
	store.insert(index+1, selectdata);  //���뵽��һ��ǰ��
	
	grid.getSelectionModel().selectRow(index+1,true);  //ѡ�����ƶ������	
	grid.view.refresh();
}


function renderOption(value, params, rs, rowIndex, colIndex, ds){
    
    var btn=" <div class=\"layui-btn-group\">";
    

	
	btn=btn+"<a class=\"layui-btn layui-btn-normal layui-btn-xs\" href=\"javascript:addAuditPersonFamily('" + value + "')\">��ͥ��Ա</a>";
	

    btn=btn+"</div>";
    return btn;
}


function addAuditPersonFamily(pid){
	radow.doEvent("checkSave",pid);
}
function openFamily(pid) {
    $h.openWin('AuditPersonFamily','pages.audit.AuditPersonFamily','��ͥ��Ա',window.parent.document.body.offsetWidth-650,700,pid,contextPath,null,
        {modal:true,collapsed:false,collapsible:false,titleCollapse:false,maximized:false});
}
</script>
</odin:base>
<odin:response onSuccess="doSuccess"/>
</body>
</html>