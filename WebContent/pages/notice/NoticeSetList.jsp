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
<odin:toolBar property="btnToolBar" applyTo="toolDiv">
	<odin:textForToolBar text=""/>
	<odin:textForToolBar text="<h4></h4>"/>
	<odin:fill/>
	<odin:buttonForToolBar  text="����"  id="add" icon="images/add.gif" isLast="true" handler="addWin"/>
</odin:toolBar>
<odin:hidden property="b0111" title="����id" ></odin:hidden>
<div>
	<div id="toolDiv" ></div>
	<odin:editgrid property="noticeSetgrid" title="" autoFill="true" bbarId="pageToolBar" pageSize="20" isFirstLoadData="false" url="/" >
	<odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
		<odin:gridDataCol name="id" />
		<odin:gridDataCol name="title" />
		<odin:gridDataCol name="text" />
		<odin:gridDataCol name="filename" />
		<odin:gridDataCol name="fileurl" />
		<odin:gridDataCol name="a0000name" />
		<odin:gridDataCol name="secret" />
		<odin:gridDataCol name="updatetime" isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
	<odin:gridRowNumColumn></odin:gridRowNumColumn>
		<odin:gridEditColumn dataIndex="id" width="90" hidden="true" header="id" align="center" editor="text" edited="false"/>
		<odin:gridEditColumn dataIndex="fileurl" width="90" hidden="true" header="�ļ����·��" align="center" editor="text" edited="false"/>
		<odin:gridEditColumn header="����" dataIndex="title" align="center" edited="false" editor="text" width="80" />
		<odin:gridEditColumn header="������" dataIndex="a0000name" align="center" edited="false" editor="text" width="40" />
		<odin:gridEditColumn header="�ȼ�" dataIndex="secret" align="center" edited="false" editor="select" width="40" renderer="mj"/>
		<odin:gridEditColumn header="����ʱ��" dataIndex="updatetime" align="center" edited="false" editor="text" width="50" />
		<odin:gridEditColumn header="����" dataIndex="downtimes" align="center" renderer="GrantRender" edited="false" editor="text" width="50"  isLast="true"/>
	</odin:gridColumnModel>
	</odin:editgrid>
</div>

 <script type="text/javascript">  


    Ext.onReady(function() {
    	//ҳ�����
    	 Ext.getCmp('noticeSetgrid').setHeight(Ext.getBody().getViewSize().height-objTop(document.getElementById('forView_noticeSetgrid'))[0]-4);
    	 Ext.getCmp('noticeSetgrid').setWidth((Ext.isIE?Ext.getBody().getViewSize().width:window.innerWidth)-objTop(document.getElementById('forView_noticeSetgrid'))[1]-2); 
    	 document.getElementById('toolDiv').style.width = Ext.getCmp('noticeSetgrid').getWidth() +'px';
    });
    
    function mj(value, params, rs, rowIndex, colIndex, ds){
    	if(value==1){
    		return ;
    	}else if(value==2){
    		return "һ��";
    	}else if(value==3){
    		return "����";
    	}else if(value==4){
    		return "����";
    	}
    	
    	
    }
	
	function addWin() {
		
		var b0111 = document.getElementById('b0111').value;			//��ǰ�û���������
		
		if(b0111 == null || b0111 == ''){
			odin.alert("��ǰ�û��������������ڣ��޷�����֪ͨ����!");		
			return;
		}
		
		$h.showWindowWithSrc('addWin', contextPath
				+ "/pages/notice/addNotice.jsp?i=1",'����֪ͨ���洰��',650,700);	
	}
	
	function GrantRender(value, params, rs, rowIndex, colIndex, ds){
		return "<a href=\"javascript:updateNotice('"+rs.get('id')+"','"+rs.get('fileurl')+"')\">�޸�</a>" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + 
		"<a href=\"javascript:deleteNotice('"+rs.get('id')+"','"+rs.get('fileurl')+"')\">ɾ��</a>" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
		"<a href=\"javascript:noticeRecipentList('"+rs.get('id')+"')\">�鿴���</a>" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	}
	
	
	//ɾ��֪ͨ����
	//encodeURI��������urlת�룬������Ĵ����������� 
	function deleteNotice(id, fileurl){
		
		//ͨ��ajax����ɾ�����߷��� 
		$.ajax({
			url:'<%=request.getContextPath()%>/PublishFileServlet?method=deleteNotice',
			type:"GET",
			data:{
				"id":id,
				"filePath":encodeURI(fileurl)
			},
			success:function(){
				radow.doEvent('noticeSetgrid.dogridquery');
				odin.alert("֪ͨ����ɾ���ɹ�!");	
				parent.gzt.window.location.reload();
			},
			error:function(){
				odin.alert("֪ͨ����ɾ��ʧ��!");		
			}
		});
		
	}


	//����֪ͨ�����ļ� 
	//encodeURI��������urlת�룬������Ĵ����������� ����̨���յ�ʱ�������ת�봦��ת������
	function downloadNoticeFile(id, fileurl){
		window.location="PublishFileServlet?method=downloadPolicyFile&filePath="+encodeURI(encodeURI(fileurl));
	}
	
	//�޸�֪ͨ���� 
	function updateNotice(id, fileurl) {
		$h.showWindowWithSrc('updateWin', contextPath
				+ "/pages/notice/updateNotice.jsp?i=1&id="+id,'�޸�֪ͨ���洰��',750,900);	
	}
	
	//֪ͨ����鿴�б����� 
	function noticeRecipentList(noticeId){
		$h.openWin('noticeRecipentList','pages.notice.NoticeRecipentWindow','֪ͨ����鿴�б�',600,600,noticeId,contextPath);
	}
</script>