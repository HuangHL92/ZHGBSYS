<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>

<script  type="text/javascript">
var g_contextpath = '<%= request.getContextPath() %>';

function expFile(value, params, record, rowIndex, colIndex, ds) {
	return "<div align='center' width='100%' ><font color=blue>"
	+ "<a style='cursor:pointer;' onclick=\"openProcessWin('"+value+"');\">�����ϻ����</a>"
	+ "</font></div>"
}
function openProcessWin(v){
	var src = g_contextpath+'/PublishFileServlet?method=downloadshanghuicailiao&rb_id='+v;
	var selecthtml = '<select id="expParm"  name="expParm" style="margin:10px;"> '+
		  '<option value ="&cur_hj_4=4_1">����</option>'+
		  '<option value="&cur_hj_4=4_2">��ǻ�</option>'+
		  '<option value="&cur_hj_4=4_3">��ί��</option>'+
		'</select>';
		
	var win = new Ext.Window({
		html : selecthtml+'<button style="margin:10px;"  onclick="$(\'#iframe_expFile\').attr(\'src\',\''+src+'\'+$(\'#expParm\').val());this.disabled=true;">�����ʼִ�е���</button><iframe width="100%" frameborder="0" id="iframe_expFile" name="iframe_expFile" height="80%" src=""></iframe>',
		title : '�����ϻ����',
		layout : 'fit',
		width : 400,
		height : 350,
		closeAction : 'close',
		closable : true,
		modal : true,
		id : 'expFile',
		collapsed:false,
		collapsible:false,
		bodyStyle : 'background-color:#FFFFFF',
		plain : true,
		titleCollapse:false,
		listeners:{}            
	});
	win.show();
}

</script>

			<!-- record_batch -->
<div id="groupTreePanel"></div>
<odin:hidden property="rb_id"/>
<odin:hidden property="rb_name"/>
<odin:groupBox title="��ѯ����">
<table style="width: 100%;">
	<tr>
		<odin:textEdit property="rb_name1" label="��������" ></odin:textEdit>
		<odin:textEdit property="rb_date1" label="��������" ></odin:textEdit>
		<%-- <odin:select2 property="rb_type" label="����" data="['1','���ӻ���'],['2','�������']"></odin:select2> --%>
	</tr>
</table>
</odin:groupBox>
<odin:editgrid2 property="memberGrid" load="selectRow" hasRightMenu="false" title="������Ϣ" autoFill="true"  bbarId="pageToolBar"  url="/">
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="rb_id" />
		<odin:gridDataCol name="rb_name"/>
		<odin:gridDataCol name="rb_date"/>
		<odin:gridDataCol name="rb_no"/>
		<odin:gridDataCol name="rb_org"/>
		<odin:gridDataCol name="rb_applicant"/>
		<odin:gridDataCol name="rb_userid" isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn2></odin:gridRowNumColumn2>
		<odin:gridEditColumn2 dataIndex="rb_id" width="110" hidden="true" editor="text" header="����" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="rb_name" width="190" header="��������" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="rb_date" width="140" header="��������" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="rb_no" width="140" header="���α��" editor="text" edited="false"  align="center"/>
		<odin:gridEditColumn2 dataIndex="rb_org" width="140" header="�������" editor="text" edited="false"  align="center"/>
		<odin:gridEditColumn2 dataIndex="rb_applicant" width="140" header="������" editor="text" edited="false" isLast="true"  align="center"/>
		
		<%-- <odin:gridEditColumn2 dataIndex="rb_id" width="140" header="����" editor="text" renderer="expFile" edited="false"  align="center" isLast="true"  />
		 --%>
	</odin:gridColumnModel>
</odin:editgrid2>

		



<odin:toolBar property="btnToolBar" applyTo="groupTreePanel">
	<odin:textForToolBar text="<h3>��ʵ���ι���</h3>" />
	<odin:fill />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="����" icon="image/icon021a2.gif" handler="loadadd" id="loadadd" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="�޸�" icon="image/icon021a6.gif" handler="infoUpdate"  id="infoUpdate"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="ɾ��" icon="image/icon021a3.gif" handler="infoDelete" id="infoDelete" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="��ѯ" icon="images/search.gif" id="infoSearch" handler="infoSearch" isLast="true"/>
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
		document.getElementById('rb_id').value = rc.data.rb_id;
		document.getElementById('rb_name').value = rc.data.rb_name;
	});
	
	memberGrid.on('rowdblclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index)
		document.getElementById('rb_id').value = rc.data.rb_id;
		$h.openPageModeWin('qcjs','pages.xbrm.JBXXT','������Ϣ',1150,800,{rb_id:rc.data.rb_id},g_contextpath);
	});
	
});
function loadadd(){
	$h.openPageModeWin('loadadd','pages.xbrm.AddJSGL','��������',510,300,{rb_id:''},g_contextpath);
}
function infoUpdate(){
	var rb_id = document.getElementById('rb_id').value;

	if(rb_id==''){
		$h.alert('ϵͳ��ʾ','��ѡ��һ�����μ�¼��');
		return;
	}
	
	$h.openPageModeWin('loadadd','pages.xbrm.AddJSGL','�޸�����',510,300,{rb_id:rb_id},g_contextpath);
}
function infoDelete(){//�Ƴ���Ա
	var rb_id = document.getElementById('rb_id').value;
	var rb_name = document.getElementById('rb_name').value;
	if(rb_id==''){
		$h.alert('ϵͳ��ʾ','��ѡ��һ�����μ�¼��');
		return;
	}
	$h.confirm("ϵͳ��ʾ��",'ɾ�����μ�¼����ɾ�������������еĸɲ���ʵ��¼�Լ�������ȷ��ɾ�����Σ�'+rb_name+"?",400,function(id) { 
		if("ok"==id){
			radow.doEvent('allDelete',rb_id);
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
			if(rc.data.rb_id==$('#rb_id').val()){
				
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
function infoSearch(){
	radow.doEvent('memberGrid.dogridquery');
}
</script>


