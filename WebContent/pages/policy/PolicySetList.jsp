<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/js/jquery-1.8.2.js"></script>
<style>
body{
overflow-x: hidden ! important;
}
</style>
<script type="text/javascript" src="basejs/pageUtil.js"></script>
<odin:hidden property="path"></odin:hidden>
<odin:toolBar property="btnToolBar" applyTo="toolDiv">
	<odin:textForToolBar text=""/>
	<odin:textForToolBar text="<h4></h4>"/>
	<odin:fill/>
	<odin:buttonForToolBar  text="����"  id="add" icon="images/add.gif" isLast="true" handler="addWin"/>
</odin:toolBar>

<odin:hidden  property="sql"></odin:hidden>
<div>
	<div id="toolDiv" ></div>
	<odin:editgrid property="policySetgrid" title="" autoFill="true" bbarId="pageToolBar" pageSize="20" isFirstLoadData="false" url="/" >
	<odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
		<odin:gridDataCol name="id" />
		<odin:gridDataCol name="fileurl" />
		<odin:gridDataCol name="title" />
		<odin:gridDataCol name="username" />
		<odin:gridDataCol name="filename" />
		<odin:gridDataCol name="secret" />
		<odin:gridDataCol name="updatetime" isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
	<odin:gridRowNumColumn></odin:gridRowNumColumn>
		<odin:gridEditColumn dataIndex="id" width="90" hidden="true" header="id" align="center" editor="text" edited="false"/>
		<odin:gridEditColumn dataIndex="fileurl" width="90" hidden="true" header="�ļ����·��" align="center" editor="text" edited="false"/>
		<odin:gridEditColumn header="����" dataIndex="title" align="center" edited="false" editor="text" width="50" />
		<odin:gridEditColumn header="������" dataIndex="username" align="center" edited="false" editor="text" width="50" />
		<odin:gridEditColumn header="�ļ�����" dataIndex="filename" align="center" edited="false" editor="text" width="50" />
		<odin:gridEditColumn header="�ȼ�" dataIndex="secret" align="center" edited="false" editor="select" width="50" selectData="['01','�����쵼�������ν���'],['02','�����쵼���ӹ滮��Ҫ'],['03','��������ɲ�����'],['04','���ڵ���ɲ���Ů�ɲ�����'],['05','���ڼ����ɲ�������Ϊ'],['06','���ڸɲ����˹���'],['07','��������ɲ����齨��'],['08','��������']"/>
		<odin:gridEditColumn header="����ʱ��" dataIndex="updatetime" align="center" edited="false" editor="text" width="50" />
		<odin:gridEditColumn header="����" dataIndex="downtimes" align="center" renderer="GrantRender" edited="false" editor="text" width="50"  isLast="true"/>
	</odin:gridColumnModel>
	</odin:editgrid>
</div>
<iframe  id="iframe_expBZYP" style="display: none;" src=""></iframe>
 <script type="text/javascript">  


    Ext.onReady(function() {
    	//ҳ�����
    	 Ext.getCmp('policySetgrid').setHeight(Ext.getBody().getViewSize().height-objTop(document.getElementById('forView_policySetgrid'))[0]-4);
    	 Ext.getCmp('policySetgrid').setWidth((Ext.isIE?Ext.getBody().getViewSize().width:window.innerWidth)-objTop(document.getElementById('forView_policySetgrid'))[1]-2); 
    	 document.getElementById('toolDiv').style.width = Ext.getCmp('policySetgrid').getWidth() +'px';
    });
    
    
    function verify(){
    	var count = 0;
    	var grid = Ext.getCmp("policySetgrid");
    	var sm = grid.getSelectionModel();
		var selections = sm.getSelections();
    	var filePath = "";
    	var status="";
    	var grid = odin.ext.getCmp('policySetgrid');
		var store = grid.store;
		for (var i = 0; i < store.data.length; i++) {
			var selected = store.getAt(i);
			var record = selected.data;
			if (record.check) {
				filePath = record.zipfile;
				status = record.status;
				count += 1;
			}
		}
		if(count == 0){
			odin.alert("��ѡ��Ҫ���ص����ݣ�");
			return;
		}
		if(count > 1){
			odin.alert("��֧�ֵ�����¼���أ�");
			return;
		}
		if(status != "�������"){
			odin.alert("�ȴ�������ɺ����أ�");
			return;
		} 
		if(filePath != ""){
			radow.doEvent("v",encodeURI(filePath));
 		}else{
			odin.error("�ļ�·���쳣���޷����أ�");
		} 
		
    }
    
   	
	function download(){
		var filePath="";
		var id="";
    	var grid = odin.ext.getCmp('policySetgrid');
		var store = grid.store;
		for (var i = 0; i < store.data.length; i++) {
			var selected = store.getAt(i);
			var record = selected.data;
			if (record.check) {
				filePath = record.zipfile;
				id = record.id;
			}
		}
		window.location="ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(filePath));
		radow.doEvent("count",id);
		refresh();
	}
	
	
	function addWin() {
		$h.showWindowWithSrc('addWin', contextPath
				+ "/pages/policy/addPolicy.jsp?i=1",'�������߷��洰��',530,250);	
	}
	
	function GrantRender(value, params, rs, rowIndex, colIndex, ds){
		return "<a href=\"javascript:downloadPolicyFile('"+rs.get('id')+"','"+rs.get('fileurl')+"')\">����</a>" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + 
		 "<a href=\"javascript:lookFileOnline('"+rs.get('id')+"','"+rs.get('fileurl')+"')\">Ԥ��</a>" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + 
		"<a href=\"javascript:deletePolicyFile('"+rs.get('id')+"','"+rs.get('fileurl')+"')\">ɾ��</a>" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	}
	
	
	//ɾ�����߷���
	//encodeURI��������urlת�룬������Ĵ����������� 
	function deletePolicyFile(id, fileurl){
		
		//ͨ��ajax����ɾ�����߷��� 
		$.ajax({
			url:'<%=request.getContextPath()%>/PublishFileServlet?method=deletePolicyFile',
			type:"GET",
			data:{
				"id":id,
				"filePath":encodeURI(fileurl)
			},
			success:function(){
				radow.doEvent('policySetgrid.dogridquery');
				odin.alert("���߷���ɾ���ɹ�!");
				parent.gzt.window.location.reload();
			},
			error:function(){
				odin.alert("���߷���ɾ��ʧ��!");		
			}
		});
		
	}


	//�������߷����ļ� 
	//encodeURI��������urlת�룬������Ĵ����������� ����̨���յ�ʱ�������ת�봦��ת������
	function downloadPolicyFile(id, fileurl){
		//window.location="PublishFileServlet?method=downloadPolicyFile&filePath="+encodeURI(encodeURI(fileurl));
	
		$('#iframe_expBZYP').attr('src',"<%= request.getContextPath() %>/PublishFileServlet?method=downloadPolicyFile&filePath="+encodeURI(encodeURI(fileurl)));
		return false
	
	}
	
	function lookFileOnline(id,fileurl){
		window.open("<%=request.getContextPath()%>/SorlQueryServlet?path="+encodeURI(encodeURI(fileurl)));
	}
</script>