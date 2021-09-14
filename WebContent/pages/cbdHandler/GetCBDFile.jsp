<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<odin:toolBar property="btnToolBar" applyTo="ftpUpManagePanel">
	<odin:fill />
	<odin:buttonForToolBar id="getCBDBtn" text="����" handler="getZipFile" icon="images/keyedit.gif"/>
	<odin:separator></odin:separator>
	<%-- <odin:buttonForToolBar id="ansBtn" text="����" menu="answer" icon="images/keyedit.gif"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar id="backCBDBtn" text="���" handler="back" icon="images/keyedit.gif"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar id="checkCDBtn" text="����" handler="reply" icon="images/keyedit.gif"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar id="localcbdo" text="�����ʱ�������" menu="localCBD" icon="images/keyedit.gif" />
	<odin:separator></odin:separator> --%>
	<odin:buttonForToolBar id="uploadZipBtn" text="�������ݰ�"  handler="uploadZip" icon="images/icon/exp.png"/>
	<odin:separator></odin:separator>
	<%--<odin:buttonForToolBar text="�鿴�ʱ�������" id="SBprocess" handler="SBprocessBtn"></odin:buttonForToolBar>
	<odin:separator></odin:separator>--%>
	<odin:buttonForToolBar text="���سʱ������ݰ�" id="getCBDZipBtn" handler="createCBDZip" icon="images/icon/exp.png"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar id="btnsx" text="ˢ��" icon="images/sx.gif" cls="x-btn-text-icon" isLast="true"  />
</odin:toolBar>
<!--  
<odin:panel contentEl="ftpUpContent" property="ftpUpManagePanel" topBarId="btnToolBar" />-->
<div id="ftpUpContent" style="width:100%;height: 100%; overflow: auto;">
<div id="ftpUpManagePanel"></div>
<odin:editgrid property="CBDGrid" title="�������ļ��б�" autoFill="true" height="550" isFirstLoadData="false" url="/" grouping="true" groupCol="transTypeCn">
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="cbdchecked" />
		<odin:gridDataCol name="fileName" />
		<odin:gridDataCol name="filePath" />
		<odin:gridDataCol name="orgName" />
		<odin:gridDataCol name="transType" />
		<odin:gridDataCol name="transTypeCn" />
		<odin:gridDataCol name="dataInfo" />
		<odin:gridDataCol name="createTime" />
		<odin:gridDataCol name="status" />
		<odin:gridDataCol name="cbd_id" />
		<odin:gridDataCol name="cbd_name" />
		<odin:gridDataCol name="personid" />
		<odin:gridDataCol name="personname" />
		<odin:gridDataCol name="username" />
		<odin:gridDataCol name="isOther"  isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn></odin:gridRowNumColumn>
		<odin:gridColumn header="selectall" width="25" gridName="CBDGrid" editor="checkbox" dataIndex="cbdchecked" edited="true"/>
		<odin:gridColumn dataIndex="fileName" width="250" header="�ļ�����"  align="left"/>
		<odin:gridColumn dataIndex="orgName" width="100" header="�Ǽǵ�λ"  align="center"   />
		<odin:gridColumn dataIndex="transType" width="100" header="��������"  align="center"   hidden="true" />
		<odin:gridColumn dataIndex="transTypeCn" header="��������" align="center"  hidden="true"   />
		<odin:gridColumn dataIndex="dataInfo" width="200" header="�ļ�������"  align="center"  hidden="true" renderer="radow.renderer.renderAlt"/>
		<odin:gridColumn dataIndex="createTime" width="180" header="����ʱ��"  align="center"   />
		<odin:gridColumn dataIndex="status" width="200" header="״̬"  align="center" renderer="accpetRender" />
		<odin:gridColumn dataIndex="username" width="200" header="�����û�"  align="center"  isLast="true" />
	</odin:gridColumnModel>
</odin:editgrid>
</div>
<odin:hidden property="cbd_name"/>
<odin:hidden property="getFlag" value="0"/>
<odin:window src="/blank.htm" id="editCBD" width="550" height="450" maximizable="false" title="¼��ʱ���">
</odin:window>
<odin:window src="/" modal="true" id="editFileWindow" title="������¼�б�" width="500" height="350"></odin:window>
<odin:window src="/blank.htm" id="simpleExpWin" width="560" height="350" maximizable="false" title="��������"></odin:window>
<odin:window src="/blank.htm" id="backWin" width="560" height="350" maximizable="false" modal="true" title="����������"></odin:window>
<odin:window src="/blank.htm" id="modifyFileWindow" width="560" height="300" maximizable="false" title="�鿴/ɾ����������"></odin:window>
<odin:window src="/blank.htm" id="uploadZipWin" width="560" height="350" maximizable="false" title="�ϴ��ʱ������ݰ�"></odin:window>

<odin:window src="/blank.htm"  id="SBprocessSystemWindow" width="800" height="600" title="�ʱ�������" modal="true"/>			
<script type="text/javascript">

//����˫���¼���˫���ļ����ƿ������سʱ����ϱ��ļ�
function ondbclick(value, params, record, rowIndex, colIndex, ds){
	return "<a ondblclick=\"javascript:getZipFile1('"+record.get('cbd_id')+"@"+record.get('cbd_name')+"@"+record.get('filePath')+"@1')\">"+value+"</a>";
}
//��ȡҳ����Ϣ�Ĺ��÷�������Ҫ��ȡ���������ƺ��ļ���ַ������ֵ��ʽΪ���ʱ�������@�ʱ�������@�ļ�·��@ѡ�м�¼��
	function getValue(){
	
		var gridId = "CBDGrid";//ҳ��grid����
		var grid = Ext.getCmp(gridId);
		//�������ֵ
		var filePath = '';
		var cbd_id = '';
		var cbd_name = '';
		var status = '';
		var count = 0;
		var personid = '';
		var personname = '';
		
		var sm = grid.getSelectionModel();
		var selections = sm.getSelections();
		var store = grid.store;
		for (var i = 0; i < store.data.length; i++) {
			var selected = store.getAt(i);
			var record = selected.data;
			if (record.cbdchecked) {
				cbd_id = record.cbd_id ;
				filePath = record.filePath ;
				cbd_name = record.cbd_name ;
				status = record.status;
				personname = record.personname;
				personid = record.personid;
				count=count+1;
			}
		}
		return cbd_id+"@"+cbd_name+"@"+filePath+"@"+count+"@"+status+"@"+personname+"@"+personid;
	}
	

	//�鿴�ʱ�������
	function SBprocessBtn(value){
		radow.doEvent("processSystem",value);
	}
	//�鿴�ʱ�������
	function SBprocessBtn1(){
		var value=getValue();
		var values = value.split("@");
		var cbd_id = values[0];
		var cbd_name = values[1];
		var filePath = values[2];
		var count = values[3];
		var status = values[4];
		var personname = values[5];
		var personid = values[6]
		if(count > 1){
			alert("ֻ��ѡ��һ���ʱ�����¼��");
			return;
		}
		if (cbd_id == '') {
			alert("��ѡ��һ����¼��");
			return;
		}
		radow.doEvent("processSystem",cbd_id+"@"+cbd_name+"@"+personname+"@"+personid+"@"+status+"@"+filePath);
	}
	
	//�����ʱ���ѹ���ļ�
	function createCBDZip(){
		var value=getValue();
		var values = value.split("@");
		var cbd_id = values[0];
		var cbd_name = values[1];
		var filePath = values[2];
		var count = values[3];
		if(count > 1){
			alert("ֻ��ѡ��һ���ʱ�����¼��");
			return;
		}
		if (cbd_id == '') {
			alert("��ѡ��һ����¼��");
			return;
		}
		
		url="<%=request.getContextPath()%>/CBDFiledownServlet?method=getZipFile&flag=g&cbd_id="+cbd_id+"&filePath="+filePath;
		var iframe = document.createElement("iframe");
		iframe.src = url;
		iframe.style.display = "none";
		document.body.appendChild(iframe);
	}

//�ʱ���״̬
function accpetRender(value, params, rs, rowIndex, colIndex, ds){
	if(value=='0'){
		return 'δ����';
	}else if(value=='1'){
		return '�ѽ���';
	}else if(value=='2'){
		return '�Ѵ��';
	}else if(value=='3'){
		return '������';
	}
}

	//�����ļ�
	function getZipFile(){
		var value=getValue();
		var values = value.split("@");
		var cbd_id = values[0];
		var cbd_name = values[1];
		var filePath = values[2];
		var count = values[3];
		if(count > 1){
			alert("ֻ��ѡ��һ���ʱ�����¼��");
			return;
		}
		if (cbd_id == '') {
			alert("��ѡ��һ����¼��");
			return;
		}
		
		url="<%=request.getContextPath()%>/CBDFiledownServlet?method=getZipFile&flag=g&cbd_id="+cbd_id+"&filePath="+filePath;
		var iframe = document.createElement("iframe");
		iframe.src = url;
		iframe.style.display = "none";
		document.body.appendChild(iframe);
		
	}
	
	//�ļ���˫�������ļ�
	function getZipFile1(value){
		var values = value.split("@");
		var cbd_id = values[0];
		var cbd_name = values[1];
		var filePath = values[2];
		var count = values[3];
		if(count > 1){
			alert("ֻ��ѡ��һ���ʱ�����¼��");
			return;
		}
		if (cbd_id == '') {
			alert("��ѡ��һ����¼��");
			return;
		}
		
		url="<%=request.getContextPath()%>/CBDFiledownServlet?method=getZipFile&cbd_id="+cbd_id+"&filePath="+filePath;
		var iframe = document.createElement("iframe");
		iframe.src = url;
		iframe.style.display = "none";
		document.body.appendChild(iframe);
		
	}
	
/**
	 * �����ʱ���
	 */
	function expExcelTemp() {
	
		var value=getValue();
		var values = value.split("@");
		var cbd_id = values[0];
		var cbd_name = values[1];
		var filePath = values[2];
		var count = values[3];
		
		if (cbd_id == '') {
			alert("��ѡ��һ����¼��");
			return;
		}
		if(count > 1){
			alert("ֻ��ѡ��һ����¼��");
			return;
		}

		doOpenPupWin(encodeURI(encodeURI(contextPath
				+ "/pages/cbdHandler/cbdLoad.jsp?cbd_id=" + cbd_id +"&cbd_name="+cbd_name+ "&download=true&flag=g&cbd_type=0")), "�����ļ�", 600, 200);
	}
	
	function checkCBDInfo(){
		var value=getValue();
		var values = value.split("@");
		var cbd_id = values[0];
		var cbd_name = values[1];
		var filePath = values[2];
		var count = values[3];
		
		radow.doEvent('checkCBD',cbd_id+"@editFile");
	}
	function checkCBDInfo1(){
		var value=getValue();
		var values = value.split("@");
		var cbd_id = values[0];
		var cbd_name = values[1];
		var filePath = values[2];
		var count = values[3];
		
		radow.doEvent('checkCBD',cbd_id+"@modifyFile");
	}
	//�����ʱ����༭����
	function editFile(){
		
		var value=getValue();
		var values = value.split("@");
		var cbd_id = values[0];
		var cbd_name = values[1];
		var filePath = values[2];
		var count = values[3];
		
		if (cbd_id == '') {
			alert("��ѡ��һ����¼��");
			return;
		}
		if(count > 1){
			alert("ֻ��ѡ��һ����¼��");
			return;
		}
		var win = odin.ext.getCmp('simpleExpWin');
		win.setTitle('���봰��');
		odin.showWindowWithSrc('simpleExpWin', contextPath
				+ "/pages/search/EditFile.jsp?flag=2&uuid="+cbd_id+"&uname="+cbd_name);
		
	}
	
	//���������ʱ���
	function addCBDInfo(){
	
		var value=getValue();
		var values = value.split("@");
		var cbd_id = values[0];
		var cbd_name = values[1];
		var filePath = values[2];
		var count = values[3];
		
		if(cbd_id == ''){
			alert("��ѡ��һ���ʱ�����¼��");
			return;
		}
		if(count > 1){
			alert("ֻ��ѡ��һ���ʱ�����¼��");
			return;
		}
		
		radow.doEvent('modifyCBD',cbd_id);
		
	}
	
	//�޸ı����ʱ���
	function modifyCBDInfo(){
	
		var value=getValue();
		var values = value.split("@");
		var cbd_id = values[0];
		var cbd_name = values[1];
		var filePath = values[2];
		var count = values[3];
		
		if(cbd_id == ''){
			alert("��ѡ��һ���ʱ�����¼��");
			return;
		}
		if(count > 1){
			alert("ֻ��ѡ��һ���ʱ�����¼��");
			return;
		}
		
		radow.doEvent('modifyCBD',cbd_id);
	}

	//¼�������
	function back(){
		
		var value=getValue();
		var values = value.split("@");
		var cbd_id = values[0];
		var cbd_name = values[1];
		var filePath = values[2];
		var count = values[3];
		var status = values[4];
		if(status == '2'){
			alert("�����ʱ����Ѿ�����أ������ٴδ�أ�");
			return;
		}
		if(status == '3'){
			alert("�����ʱ����Ѿ���������������ز�����");
			return;
		}
		if(cbd_id == ''){
			alert("��ѡ��һ���ʱ�����¼��");
			return;
		}
		if(count > 1){
			alert("ֻ��ѡ��һ���ʱ�����¼��");
			return;
		}
		
		radow.doEvent('getBackWin',cbd_id+"@"+cbd_name+"@"+filePath);
	}
	
	//����
	function reply(){
	
		var value=getValue();
		var values = value.split("@");
		var cbd_id = values[0];
		var cbd_name = values[1];
		var filePath = values[2];
		var count = values[3];
		
		if(cbd_id == ''){
			alert("��ѡ��һ���ʱ�����¼��");
			return;
		}
		if(count > 1){
			alert("ֻ��ѡ��һ���ʱ�����¼��");
			return;
		}
		
		radow.doEvent('reply',cbd_id+"@"+cbd_name+"@"+filePath);
	}
		//�鿴/ɾ������
	function modifyFile(){
		
		var value=getValue();
		var values = value.split("@");
		
		if (values[0] == '') {
			alert("��ѡ��һ���ʱ�����¼��");
			return;
		}
		if(values[3] > 1){
			alert("ֻ��ѡ��һ���ʱ�����¼��");
			return;
		}
		
		radow.doEvent("modifyAttach",values[0]+"@2");
		
	}
	
	//����ʱ������ݰ�
	function uploadZip(){
		
		var win = odin.ext.getCmp('simpleExpWin');
		win.setTitle('���봰��');
		odin.showWindowWithSrc('simpleExpWin', contextPath
				+ "/pages/cbdHandler/UploadZip.jsp?flag=3");
	}
	
	Ext.onReady(function() {
		//ҳ�����
		 Ext.getCmp('CBDGrid').setHeight(Ext.getBody().getViewSize().height-objTop(document.getElementById('forView_CBDGrid'))[0]-4);
		 Ext.getCmp('CBDGrid').setWidth((Ext.isIE?Ext.getBody().getViewSize().width:window.innerWidth)-objTop(document.getElementById('forView_CBDGrid'))[1]-2); 
		 document.getElementById('ftpUpManagePanel').style.width = document.body.clientWidth;
		 //Ext.getCmp('btnToolBar').setWidth(document.body.clientWidth);
		 document.getElementById("ftpUpContent").style.width = document.body.clientWidth;
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
<odin:menu property="localCBD">
<odin:menuItem text="¼�뱾���ʱ���" property="createCBD" handler="addCBDInfo" ></odin:menuItem>
<odin:menuItem text="�鿴�����ʱ���" property="getCBD" handler="expExcelTemp" ></odin:menuItem>
<odin:menuItem text="�ϴ�����" property="editFile"  handler="checkCBDInfo" ></odin:menuItem>
<odin:menuItem text="�鿴/ɾ������" property="modifyFileBtn" handler="checkCBDInfo1" isLast="true" ></odin:menuItem>
</odin:menu>

<odin:menu property="answer">
<odin:menuItem property="checkCDBtn" text="ͬ��" handler="reply" ></odin:menuItem>
<odin:menuItem property="backCBDBtn" text="���" handler="back" isLast="true" ></odin:menuItem>
</odin:menu>
</script>