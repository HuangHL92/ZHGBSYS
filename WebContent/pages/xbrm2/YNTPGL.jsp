<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>

<script  type="text/javascript">
var g_contextpath = '<%= request.getContextPath() %>';

//������Ϣ
function shareInfo(value, params, record, rowIndex, colIndex, ds) {
	if(value!=''&&value!=null){
		return "<div align='center' width='100%' ><font color=blue>"
		+ "<a style='cursor:pointer;' onclick=\"radow.doEvent('unsaveShareInfo','"+record.data.yn_id+"');\">ȡ������</a>"
		//+ "<a style='cursor:pointer;padding-left:30px;' onclick=\"openGDview('"+record.data.yn_id+"');\">�鿴�浵</a>"
		+ "</font></div>"
	}else{
		return "<div align='center' width='100%' ><font color=blue>"
		+ "<a style='cursor:pointer;' onclick=\"openProcessWin('"+record.data.yn_id+"');\">����</a>"
		//+ "<a style='cursor:pointer;padding-left:30px;' onclick=\"openGDview('"+record.data.yn_id+"');\">�鿴�浵</a>"
		+ "</font></div>"
	}
	
}
var hjJSON = {'TPHJ1':'����ɲ����һ����','TPHJ3':'ʡί���ר�����ɲ����һ����','TPHJ2':'ʡί��ί��ɲ����һ����'}; //,'TPHJ4':'��ί���ר�����','TPHJ5':'��ί��ί��'
//����
function infoDesc(value, params, record, rowIndex, colIndex, ds) {
	if(value!=''&&value!=null){
		return "<div align='center' width='100%' >"
		+ "<a  onclick=\"return false;\">"+hjJSON[value]+"("+(record.data.gname||'')+record.data.info02+")"+"</a>"
		+ "</div>"
	}else{
		return "";
	}
	
}

function openProcessWin(v){
	var selecthtml = '<div  style="padding:10px;"><label for="tphjselect">��������:&nbsp;&nbsp;</label><select id="tphjselect"  name="tphjselect" > '+
		'<option value="TPHJ1">����ɲ����һ����</option>'+
		'<option value="TPHJ3">ʡί���ר�����ɲ����һ����</option>'+
		'<option value="TPHJ2">ʡί��ί��ɲ����һ����</option>'+
	 	//'<option value="TPHJ4">ʡί���ר�����</option>'+
	 	//'<option value="TPHJ5">��ί��ί��</option>'+ 
		'</select></div><div  style="padding:10px;"><label for="male">�û���:&nbsp;&nbsp;&nbsp;&nbsp;</label> <input type="text" name="uname" id="uname" style="width:183px;"/></div>';
		
	var win = new Ext.Window({
		html : selecthtml+'<div  style="padding:10px;margin-left:200px;"><button style="cursor:pointer;" onclick="saveShareInfo(\''+v+'\')">����</button></div>',
		title : '������Ϣ',
		layout : 'fit',
		width : 300,
		height : 161,
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

//���湲����Ϣ
function saveShareInfo(yn_id){
	if($('#tphjselect').val()==''){
		$h.alert('ϵͳ��ʾ','��ѡ�񷽰����ͣ�');
		return;
	}
	if($('#uname').val()==''){
		$h.alert('ϵͳ��ʾ','�������û�����');
		return;
	}
	$('#tphjselectH').val($('#tphjselect').val());
	$('#unameH').val($('#uname').val());
	radow.doEvent('saveShareInfo',yn_id);
}
</script>

<!-- ������� -->
<div id="groupTreePanel"></div>
	
	<odin:hidden property="tphjselectH" title="��������id"/>
	<odin:hidden property="unameH"  title="�����û�id"/>
	<odin:hidden property="yn_id"/>
	<odin:hidden property="yn_name"/>
	<odin:groupBox title="��ѯ����">

	<table style="width: 100%;">
		<tr>
			<odin:textEdit property="yn_name1" label="��������" ></odin:textEdit>
			<odin:select2 property="yn_type1" label="����" data="['TPHJ1','����ɲ����һ����'],['TPHJ3','ʡί���ר�����ɲ����һ����'],['TPHJ2','ʡί��ί��ɲ����һ����']"></odin:select2> <!-- ['TPHJ1','����'],['TPHJ2','��ί���ר������Ա����'],['TPHJ3','�������'],['TPHJ4','��ί���ר�����'],['TPHJ5','��ί��ί��'] -->
		</tr>
	</table>
</odin:groupBox>

<!-- ������ʾ�б� -->
<odin:editgrid2 property="memberGrid" load="selectRow" hasRightMenu="false" title="������Ϣ" autoFill="true"  bbarId="pageToolBar"  url="/">
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="yn_id" />
		<odin:gridDataCol name="yn_name"/>
		<odin:gridDataCol name="yn_type"/>
		<odin:gridDataCol name="info01"/>
		<odin:gridDataCol name="info02"/>
		<odin:gridDataCol name="gname"/>
		<odin:gridDataCol name="yn_date" isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn2></odin:gridRowNumColumn2>
		<odin:gridEditColumn2 dataIndex="yn_id" width="110" hidden="true" editor="text" header="����" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="yn_name" width="190" header="����" editor="text" edited="false" align="center"/>
		
		<odin:gridEditColumn2 dataIndex="yn_type" width="140" header="��ǰ��������" editor="select" selectData="['TPHJ1','����ɲ����һ����'],['TPHJ3','ʡί���ר�����ɲ����һ����'],['TPHJ2','ʡί��ί��ɲ����һ����']" edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="yn_date" width="140" header="�Ǽ�����" editor="text"   edited="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="info01" width="190" header="������Ϣ" editor="text" edited="false" align="center" renderer="infoDesc" />
		<odin:gridEditColumn2 dataIndex="info02" width="190" header="����" editor="text" edited="false" align="center" renderer="shareInfo" isLast="true"/>
	</odin:gridColumnModel>
</odin:editgrid2>

		


<!-- ���������� -->
<odin:toolBar property="btnToolBar" applyTo="groupTreePanel">
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
	
	//���񵥻�����������yn_id,��������yn_name��������ֵ
	memberGrid.on('rowclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index)
		document.getElementById('yn_id').value = rc.data.yn_id;
		document.getElementById('yn_name').value = rc.data.yn_name;
	});
	
	//����˫���������ɲ���Ϣһ��������
	memberGrid.on('rowdblclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index)
		document.getElementById('yn_id').value = rc.data.yn_id;
		var id=rc.data.yn_id+","+rc.data.yn_type;
		/* $h.openPageModeWin('qcjs','pages.xbrm2.TPB','�ɲ����佨�鷽��',1150,800,
				{yn_id:rc.data.yn_id,yn_type:rc.data.yn_type,scroll:"scroll:yes;"},g_contextpath);  
		 
		*/
		var winHeight = $(top).outerHeight()+160;
		var winWidth = $(top).width() * 0.95;
		top.$h.openWin('qcjs','pages.xbrm2.TPB','�ɲ���Ϣһ����',winWidth,winHeight,
				id,g_contextpath,null,{maximizable:false,resizable:true,draggable:false}, true); 
		 
	
	});
	
});

/**
 * ��������
 *
 */
function loadadd(){
	$h.openPageModeWin('loadadd','pages.xbrm2.AddYNTP','��������',510,220,{yn_id:''},g_contextpath);
}

/**
 * �鿴�鵵
 *
 */
function openGDview(yn_id){
	$h.openPageModeWin('gdck','pages.xbrm2.TPBView','�ɲ����佨�鷽���浵��Ϣ',1150,800,
			{yn_id:yn_id,scroll:"scroll:no;"},g_contextpath);
}

/**
 * ���������޸�
 *
 */
function infoUpdate(){
	var yn_id = document.getElementById('yn_id').value;

	if(yn_id==''){
		$h.alert('ϵͳ��ʾ','��ѡ��һ�����μ�¼��');
		return;
	}
	
	$h.openPageModeWin('loadadd','pages.xbrm2.AddYNTP','�޸�����',510,220,{yn_id:yn_id},g_contextpath);
}

/**
 * ɾ������
 *
 */
function infoDelete(){ 
	var yn_id = document.getElementById('yn_id').value;
	var yn_name = document.getElementById('yn_name').value;
	if(yn_id==''){
		$h.alert('ϵͳ��ʾ','��ѡ��һ�����μ�¼��');
		return;
	}
	$h.confirm("ϵͳ��ʾ��",'ɾ�����μ�¼����ɾ�������������еĵ����¼��ȷ��ɾ�����Σ�'+yn_name+"?",400,function(id) { 
		if("ok"==id){
			radow.doEvent('allDelete',yn_id);
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
			if(rc.data.yn_id==$('#yn_id').val()){
				
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

/**
 * ��ѯ
 *
 */
function infoSearch(){
	radow.doEvent('memberGrid.dogridquery');
}
</script>

