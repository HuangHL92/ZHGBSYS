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
<odin:hidden property="rb_no"/>
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
		<odin:gridDataCol name="rb_leadview"/>
		<odin:gridDataCol name="rb_status"/>
		<odin:gridDataCol name="rbm_status"/>
		<odin:gridDataCol name="rb_userid" isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn2></odin:gridRowNumColumn2>
		<odin:gridEditColumn2 dataIndex="rb_id" width="110" hidden="true" editor="text" header="����" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="rb_name" width="190" header="��������" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="rb_date" width="140" header="��������" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="rb_no" width="140" header="���α��" editor="text" edited="false"  align="center"/>
		<odin:gridEditColumn2 dataIndex="rb_org" width="140" header="�������" editor="text" edited="false"  align="center"/>
		<odin:gridEditColumn2 dataIndex="rb_applicant" width="140" header="������" editor="text" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="rb_leadview"  header="�������" editor="text" edited="false" renderer="leadviewshow"  align="center"/>
		<odin:gridEditColumn2 dataIndex="rb_status"  header="״̬" editor="text" edited="false" renderer="statusshow"  align="center"/>
		<odin:gridEditColumn2 dataIndex="rbm_status"  header="�ϲ�״̬" editor="text" edited="false" isLast="true" renderer="rbmRenderer"  align="center"/>
	</odin:gridColumnModel>
</odin:editgrid2>


<odin:toolBar property="btnToolBar" applyTo="groupTreePanel">
	<odin:textForToolBar text="<h3>ѡ�����ù���</h3>" />
	<odin:fill />
	<odin:buttonForToolBar text="�쵼����" icon="image/icon021a4.gif" id="leadsuggest" handler="leadsugg"></odin:buttonForToolBar>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="����" icon="image/icon021a2.gif" handler="loadadd" id="loadadd" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="�޸�" icon="image/icon021a6.gif" handler="infoUpdate"  id="infoUpdate"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="ɾ��" icon="image/icon021a3.gif" handler="infoDelete" id="infoDelete" />
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="��ѯ" icon="images/search.gif" id="infoSearch" isLast="true" handler="infoSearch"/>
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
		document.getElementById('rb_no').value = rc.data.rb_no;
		$h.openPageModeWin('qcjs','pages.xbrm.QCJS','�ɲ�ѡ������',1150,800,{rb_id:rc.data.rb_id},g_contextpath);
		radow.doEvent('memberGrid.dogridquery');
		//mk.hide(); //�ر�  
	});
	
});

function leadviewshow(value, params, record, rowIndex, colIndex, ds){
	if(value=='1'){
		return 'ͬ��';
	}else if(value=='2'){
		return '��ͬ��';
	}
}

function rbmRenderer(value,params,record,rowIndex,colIndex,ds){
	if(value=='2'){
		return '';
	} else if(value=='1'){
		return "<a href=\"javascript:void();\" onclick=\"batchMergeCancel('"+record.data.rb_id+"')\">���غϲ�</a>";
	} else {
		return "<a href=\"javascript:void();\" onclick=\"batchMergeSent('"+record.data.rb_id+"')\">����ϲ�</a>";
	}
}
function batchMergeCancel(rb_id){
	radow.doEvent('rbmCancel',rb_id);
}
function batchMergeSent(rb_id){
	//radow.doEvent('rbmSent',rb_id);
	$h.showModalDialog('picupload',g_contextpath+'/pages/xbrm/MGDeptWin.jsp?rb_id='+rb_id+'','��Ϣ����', 300,100,null,{rb_id : rb_id},true);

}
function statusshow(value,params,record,rowIndex,colIndex,ds){
	if(value=='1'){
		return '���';
	}else{
		return '������';
	}
}

function leadsugg(){
	radow.doEvent('leadsuggWin');
	/* Ext.Msg.prompt("�쵼�������","������'1'��'2'��1��ʾͬ�⣬2��ʾ��ͬ�⡣",function(btn,text){
		if(btn=='ok'){
			if(text=='1' || text=='2'){
				radow.doEvent('leadsuggest',text);
			}else{
				$h.alert('ϵͳ��ʾ','���벻��ȷ!');
			}
		}else if(btn=='cancel'){
			return false;
		}
	}); */
}

function spwin() {
	var shareTypeCmbstores = new Ext.data.SimpleStore({
		fields : ['id', 'value'],
		data : [['1', 'ͬ��'], ['2', '��ͬ��']]
	});
	var shareTypeCmb = new Ext.form.ComboBox({
		editable : false,
		triggerAction : 'all',
		fieldLabel : '<font color="red">*</font>�������',
		labelStyle : "text-align:right;",  
		store : shareTypeCmbstores,
		displayField : 'value',
		valueField : 'id',
		name : 'type',
		mode : 'local',
		anchor : '90%',
		value : '1',
		allowBlank : false
	})
	var okbtn = new Ext.Button({
		text : 'ȷ��',
		handler : function() {
			radow.doEvent('leadsuggest', shareTypeCmb.getValue());
			insert_Win.close();
			//alert("ֵ�� :"+shareTypeCmb.getValue()+" ��ʾֵ��:"+shareTypeCmb.getRawValue())
		}
	});
	var nobtn = new Ext.Button({
		text : '�ر�',
		handler : function() {
			insert_Win.close();
		}
	});
	var insert_Win = new Ext.Window({
		plain : true,
		layout : 'form',
		resizable : false, // �ı��С
		draggable : true, // �������϶�
		closeAction : 'close',// �ɱ��ر� close or hide
		modal : true, // ģ̬����
		width : 300,
		height : 110,
		title : '�쵼�������',
		items : [shareTypeCmb],
		bodyStyle:"background-color: white;padding:10px 5px 0;",
		autoScroll : true,
		buttonAlign : 'center',
		loadMask : true,
		bbar : ['->','-',okbtn,'-', nobtn]
	});
	insert_Win.show();
}

function loadadd(){
	$h.openPageModeWin('loadadd','pages.xbrm.AddJSGL','��������',580,300,{rb_id:''},g_contextpath);
}
function infoUpdate(){
	var rb_id = document.getElementById('rb_id').value;

	if(rb_id==''){
		$h.alert('ϵͳ��ʾ','��ѡ��һ�����μ�¼��');
		return;
	}
	
	$h.openPageModeWin('loadadd','pages.xbrm.AddJSGL','�޸�����',580,300,{rb_id:rb_id},g_contextpath);
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


