<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<odin:toolBar property="btnToolBar" applyTo="panel_content">
	<odin:fill/>
	<%--<odin:buttonForToolBar text="�½��Ǽ�" id="addCBD" ></odin:buttonForToolBar>
	<odin:separator></odin:separator>
	 <odin:buttonForToolBar text="�޸ĳʱ���" id="modifyCBDBtn" handler="modifyCBD"></odin:buttonForToolBar>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="���سʱ���" id="getCBD" handler="expExcelTemp"></odin:buttonForToolBar>
	<odin:separator></odin:separator>
   <odin:buttonForToolBar text="�鿴�ʱ�������" id="process" handler="processBtn"></odin:buttonForToolBar>
    <odin:separator></odin:separator>--%>
    <odin:buttonForToolBar text="�鿴" id="look" menu="lookMenu" icon="images/icon/table.gif"></odin:buttonForToolBar>
	<odin:separator />
	<odin:buttonForToolBar text="�ʱ���״̬" id="lookStatus" menu="statusMenu" icon="images/icon/table.gif"></odin:buttonForToolBar>
	<odin:separator />
	<odin:buttonForToolBar id="btndown" text="�����ʱ���" icon="images/icon/exp.png" cls="x-btn-text-icon" />
    <odin:separator />
	<odin:buttonForToolBar id="btnsx" text="ˢ��" icon="images/sx.gif" cls="x-btn-text-icon" isLast="true" />
	<%--<odin:buttonForToolBar text="�༭����" id="uploadFile" menu="attach"></odin:buttonForToolBar>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="�ϱ��ʱ���" id="repBtn" handler="rep" icon="images/icon/exp.png"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="���سʱ������ݰ�" id="getCBDZipBtn" handler="getCBDZip" isLast="true" icon="images/icon/exp.png"/> --%>
</odin:toolBar>
<odin:hidden property="a0000"/>
<div id="panel_content">
</div>
<!-- 
<odin:panel contentEl="panel_content" property="mypanel" topBarId="btnToolBar" ></odin:panel> -->
<odin:editgrid property="CBDGrid" title="" autoFill="true" bbarId="pageToolBar" pageSize="20" isFirstLoadData="false" url="/">
<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
		<odin:gridDataCol name="checked" />
		<odin:gridDataCol name="cbd_id" />
		<odin:gridDataCol name="cbd_name" />
		<odin:gridDataCol name="cbd_path" />
		<odin:gridDataCol name="cbd_date" />
		<odin:gridDataCol name="cbd_word" />
		<odin:gridDataCol name="cbd_year" />
		<odin:gridDataCol name="word" />
		<odin:gridDataCol name="cbd_no" />
		<odin:gridDataCol name="cbd_leader" />
		<odin:gridDataCol name="cbd_date1" />
		<odin:gridDataCol name="cbd_organ" />
		<odin:gridDataCol name="cbd_text" />
		<odin:gridDataCol name="cbd_personid" />
		<odin:gridDataCol name="cbd_personname" />
		<odin:gridDataCol name="cbd_userid" />
		<odin:gridDataCol name="cbd_username" />
		<odin:gridDataCol name="status" />
		
		<odin:gridDataCol name="objectno" isLast="true"/>
		
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn></odin:gridRowNumColumn>
		<odin:gridColumn header="selectall" width="25" gridName="CBDGrid" editor="checkbox" dataIndex="checked" edited="true"/>
		<odin:gridEditColumn2 header="�ʱ�������" width="70" dataIndex="cbd_name"
			edited="false" editor="select" />
		<odin:gridEditColumn header="�ʱ���������" align="center" width="60" hidden="true"
			dataIndex="word" editor="text" edited="false" />
		<odin:gridEditColumn header="�쵼��ν"  hidden="true" dataIndex="cbd_leader" align="center"
			edited="false" editor="text" width="50" />
		<odin:gridEditColumn header="�ʱ�������" dataIndex="cbd_date1" align="center" hidden="false"
			edited="false" editor="text" width="50" />
		<odin:gridEditColumn  align="center" width="50" header="�а쵥λ" dataIndex="cbd_organ" 
			editor="text" edited="false" hidden="true" />
		<odin:gridEditColumn header="�ʱ�����Ա����" dataIndex="cbd_personname" align="center" hidden="true"
			edited="false" editor="text" width="100" />
		<odin:gridEditColumn header="�ʱ�������" dataIndex="cbd_path" align="center" hidden="false"
		edited="false" editor="text" width="50" renderer="getType"/>
		<odin:gridEditColumn2 header="�ʱ���״̬" dataIndex="status" align="center" hidden="false" edited="false" editor="select" width="40" codeType="CBDSTATUS"/>
		
		<odin:gridEditColumn header="�����û�" dataIndex="cbd_username" align="center" hidden="false"
		edited="false" editor="text" width="50" isLast="true" />
		
	</odin:gridColumnModel>
</odin:editgrid>
<odin:window src="/blank.htm" id="newUpCBD" width="560" height="450"
	maximizable="false" title="�����ϱ��ʱ���" modal="true"></odin:window>
<odin:window src="/blank.htm"  id="processSystemWindow" width="1000" height="500" title="�ʱ�������" modal="true"/>			
<odin:window src="/blank.htm" id="editCBD" width="550" height="450" maximizable="false" title="¼��ʱ���">
</odin:window>
<odin:window src="/blank.htm" modal="true" id="ReportCBD" title="�ϱ��ʱ���ҳ��" width="600" height="480"></odin:window>

<script type="text/javascript">

//��ȡҳ���е�ֵ
	function getValue(){
		
		var cbd_id = '';
		var cbd_name = '';
		var cbd_path = '';
		var gridId = "CBDGrid";
		var grid = Ext.getCmp(gridId);
		//var store = grid.getStore();
		var count = 0;
		var sm = grid.getSelectionModel();
		var selections = sm.getSelections();
		var store = grid.store;
		for (var i = 0; i < store.data.length; i++) {
			var selected = store.getAt(i);
			var record = selected.data;
			if (record.checked) {
				cbd_id =  record.cbd_id;
				cbd_name = record.cbd_name;
				cbd_path = record.cbd_path;
				count = count+1;
			}
		}
		return cbd_id+"@"+cbd_name+"@"+count+"@"+cbd_path;
	}
	
	//�鿴�ʱ�������
    function processBtn(){
    	var value=getValue();
		var values = value.split("@");
		var cbd_id = values[0];
		var cbd_name = values[1];
		var count = values[2];
		var cbd_path = values[3];

		if (cbd_id == '') {
			alert("��ѡ��һ���ʱ�����¼��");
			return;
		}
		if(count > 1 ){
			alert("ֻ��ѡ��һ����¼��");
			return;
		}
		radow.doEvent("processSystem",cbd_id+"@"+cbd_name+"@"+cbd_path);
	}
	//�鿴�ʱ�������
    function processBtn1(value){
		radow.doEvent("processSystem",value);
	}
	
	//ˢ��
    function reloadGrid(){
		radow.doEvent("btnsx.onclick");
	}
	
/**
	 * �����ʱ���
	 */
	function expExcelTemp() {
	
		var value=getValue();
		var values = value.split("@");
		var cbd_id = values[0];
		var cbd_name = values[1];
		var count = values[2];
		var cbd_path = values[3];

		if (cbd_id == '') {
			alert("��ѡ��һ���ʱ�����¼��");
			return;
		}
		if(count > 1 ){
			alert("ֻ��ѡ��һ����¼��");
			return;
		}
		
		doOpenPupWin(encodeURI(encodeURI(contextPath
				+ "/pages/cbdHandler/cbdLoad.jsp?cbd_id=" + cbd_id +"&cbd_name="+cbd_name+ "&download=true&flag=u&cbd_type="+cbd_path)), "�����ļ�", 600, 200);
	}
	
	//�鿴/ɾ������
	function modifyFile(){
		
		//��ȡҳ���е�ֵ
		var value=getValue();
		var values = value.split("@");
		
		if (values[0] == '') {
			alert("��ѡ��һ���ʱ�����¼��");
			return;
		}
		if(values[2] > 1){
			alert("ֻ��ѡ��һ���ʱ�����¼��");
			return;
		}
		
		radow.doEvent("modifyAttach",values[0]+"@1");
		
	}
	
	//�ʱ����ϴ�����
	function editFile(){
		
		//��ȡҳ���е�ֵ
		var value=getValue();
		var values = value.split("@");
		var cbd_id = values[0];
		var cbd_name = values[1];
		var count = values[2];
		
		if (cbd_id == '') {
			alert("��ѡ��Ҫ�ϴ������ĳʱ�����¼��");
			return;
		}
		if(count > 1){
			alert("ֻ��ѡ��һ���ʱ�����¼��");
			return;
		}
		
		var win = odin.ext.getCmp('simpleExpWin');
		win.setTitle('���봰��');
		odin.showWindowWithSrc('simpleExpWin', contextPath
				+ "/pages/search/EditFile.jsp?flag=0&uuid="+cbd_id+"&uname="+cbd_name);
	}
	
	//�޸ĳʱ���
	function modifyCBD(){
	
		//��ȡҳ���е�ֵ
		var value=getValue();
		var values = value.split("@");
		var cbd_id = values[0];
		var cbd_name = values[1];
		var count = values[2];
		var cbd_path = values[3];				
		if (cbd_id == '') {
			alert("��ѡ��Ҫ�鿴�ĳʱ�����¼��");
			return;
		}
		if(count > 1){
			alert("ֻ��ѡ��һ���ʱ�����¼��");
			return;
		}
		radow.doEvent('modifyCBD',cbd_id+"@"+cbd_path);
	}
	
	//�ʱ����ϱ�
	function rep(){
	
		//��ȡҳ���е�ֵ
		var value=getValue();
		var values = value.split("@");
		var cbd_id = values[0];
		var cbd_name = values[1];
		var count = values[2];
		
		if (cbd_id == '') {
			alert("��ѡ��Ҫ�ϱ��ĳʱ�����¼��");
			return;
		}
		if(count > 1){
			alert("ֻ��ѡ��һ���ʱ�����¼��");
			return;
		}
		radow.doEvent('repBtn',cbd_id+"@"+cbd_name);
	}
	
	//�����ʱ���ѹ���ļ�
	function getCBDZip(){

		var value=getValue();
		var values = value.split("@");
		var cbd_id = values[0];
		var cbd_name = values[1];
		var count = values[2];
		var cbd_path = values[3];
		if (cbd_id == '') {
			alert("��ѡ��һ���ʱ�����¼��");
			return;
		}
		if(count > 1 ){
			alert("ֻ��ѡ��һ����¼��");
			return;
		}
		
		doOpenPupWin(encodeURI(encodeURI(contextPath
				+ "/pages/cbdHandler/CBDZipDownLoad.jsp?cbd_id=" + cbd_id +"&cbd_name="+cbd_name+ "&download=true&flag=u&cbd_type="+cbd_path)), "���سʱ������ݰ�", 600, 250);
		
	}
	
	//�����ϱ��ʱ���
	function addUpCBD(){
		var value=getValue();
		var values = value.split("@");
		var cbd_id = values[0];
		var cbd_name = values[1];
		var count = values[2];
		var cbd_path = values[3];
		if(count > 1){
			alert("ֻ��ѡ��һ���ʱ�����¼��");
			return;
		}
		if (cbd_id == '') {
			alert("��ѡ��һ���ʱ�����¼��");
			return;
		}
		if(cbd_path == '1'){
			alert("ѡ�еĳʱ�����¼Ϊ�ϱ��ʱ�����ֻ�б����ʱ������������ϱ��ʱ�����");
			return;
		}
		radow.doEvent('addUpCBD',cbd_id);
		
	}
	
	//���ݳʱ��������ͱ��뷵����������
	function getType(value, params, record, rowIndex, colIndex, ds){
		if(value == '1'){
			return '�����ʱ���';
		}else if(value == '2'){
			return '�ϱ��ʱ���';
		}
	}
	
<odin:menu property="attach">
<odin:menuItem text="�ϴ�����" property="editFile" handler="editFile" ></odin:menuItem>
<odin:menuItem text="�鿴/ɾ������" property="modifyFileBtn" handler="modifyFile" isLast="true" ></odin:menuItem>
</odin:menu>

<odin:menu property="lookMenu">
<odin:menuItem text="�����ʱ���" property="bjBtn" ></odin:menuItem>
<odin:menuItem text="�ϱ��ʱ���" property="sbBtn" isLast="true" ></odin:menuItem>
</odin:menu>

<odin:menu property="statusMenu">
<odin:menuItem text="������" property="inProcess" ></odin:menuItem>
<odin:menuItem text="��ͬ��" property="allEnd" ></odin:menuItem>
<odin:menuItem text="���˻�" property="back" isLast="true" ></odin:menuItem>
</odin:menu>

//<odin:menu property="newCBD">
//<odin:menuItem text="���ɱ����ʱ���" property="addCBD" ></odin:menuItem>
//<odin:menuItem text="�����ϱ��ʱ���" property="addUpCBDBtn" handler="addUpCBD" isLast="true" ></odin:menuItem>
//</odin:menu>

function onHide(id,e){
	Ext.getCmp(id).on('hide',function(){eval(e);});
}

function expCBD(){
	var fileName="�ʱ���Ա����.xls";
	var excelType="100";
	var viewType="";
	var value=getValue();
	var values = value.split("@");
	var cbd_id = values[0];
	if (cbd_id == '') {
		alert("���ȹ�ѡ�ʱ�����¼��");
		return;
	}
	var a0000 = document.getElementById('a0000').value;
	window.location="<%=request.getContextPath() %>/FiledownServlet?fileName=" + encodeURI(encodeURI(fileName)) +"&excelType="+excelType+"&viewType="+viewType+"&a0000="+a0000+"&download=true";
}
Ext.onReady(function() {
	//ҳ�����
	 Ext.getCmp('CBDGrid').setHeight(Ext.getBody().getViewSize().height-objTop(document.getElementById('forView_CBDGrid'))[0]-4);
	 Ext.getCmp('CBDGrid').setWidth((Ext.isIE?Ext.getBody().getViewSize().width:window.innerWidth)-objTop(document.getElementById('forView_CBDGrid'))[1]-2); 
	 document.getElementById('panel_content').style.width = document.body.clientWidth;
//	 Ext.getCmp('mypanel').setWidth(document.body.clientWidth);
	 //document.getElementById("ftpUpContent").style.width = document.body.clientWidth;
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