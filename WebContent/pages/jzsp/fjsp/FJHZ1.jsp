<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>

<%@include file="/comOpenWinInit2.jsp" %>
<style>
textarea{
width: 400px;overflow: auto;
}
.tb , .tb tr th, .tb tr td
{ border:1px solid #74A6CC;  border-right-width: 0px;  }
.tb
{ text-align: center; border-collapse: collapse;border-width: 2px; }
.titleTd{
	background-color: rgb(192,220,241);
	font-weight: bold;
	font-size: 12px;
	line-height:20px;  

	letter-spacing:3px;  
}
.tb .x-form-trigger{right: 0px;}/*ͼ�����  */
.tb input{width: 100%!important;border: none;}
.tb textarea{border: none;overflow: auto;word-break:break-all;};
.tb .x-form-item{margin-bottom: 0px;}


.ext-ie .x-grid3-cell-enable{
height: auto;
}
.x-grid3-cell-inner{
white-space: normal;
}
</style>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<link href="<%=request.getContextPath()%>/jqueryUpload/uploadify.css" rel="stylesheet" type="text/css" />
<script src="<%=request.getContextPath()%>/jqueryUpload/jquery.uploadify.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comboxWithTree.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script  type="text/javascript">

</script>
<odin:hidden property="sp0100s"/>
<odin:hidden property="spp00"/>
<odin:hidden property="usertype"/>
<table style="width: 100%;" class="tb">
	<tr>
		<td class="titleTd" width="15%" colspan="1">����</td>
		<odin:textEdit property="spp13" readonly="true" width="'100%'" title="����" colspan="1"/>
		<td width="15%" class="titleTd" colspan="1">��ְ����</td>
		<odin:select2 hideTrigger="true" readonly="true" property="spp02" width="'100%'" title="��ְ����" data="['1','�쵼�ɲ����ż�ְ����']" colspan="1"/>
	</tr>
	<tr height="60" style="background-color: white;">
		<tags:JUpload2 property="file03" label="�����ϴ�" fileTypeDesc="�����ļ�"  colspan="4"
		uploadLimit="20" width="80" fileSizeLimit="20MB" fileTypeExts="*.*" labelTdcls="titleTd"/>
	</tr>
</table>
<odin:editgrid2 property="memberGrid"  afteredit="afedt" load="doAddrow"
hasRightMenu="false" title="���ż�ְ�����б����" autoFill="true"  url="/"
 colHGroupRows="[{header:'',align:'center',colspan:1,rowcss:false},{header:'��ְ�쵼�ɲ�',align:'center',colspan:3},{header:'���ְ����',align:'center',colspan:2},{header:'ԭ��ְ�쵼�ɲ�',align:'center',colspan:2},{header:'',align:'center',colspan:1,rowcss:false},{header:'',align:'center',colspan:1,rowcss:false},{header:'',align:'center',colspan:1,rowcss:false}]">
	<odin:gridJsonDataModel>
		<odin:gridDataCol name="sp0100" />
		<odin:gridDataCol name="a0000"/>
		<odin:gridDataCol name="sp0102"/>
		<odin:gridDataCol name="sp0130"/>
		<odin:gridDataCol name="sp0131"/>
		<odin:gridDataCol name="sp0132"/>
		<odin:gridDataCol name="sp0133"/>
		<odin:gridDataCol name="sp0134"/>
		<odin:gridDataCol name="sp0103"/>
		<odin:gridDataCol name="sp0104"/>
		<odin:gridDataCol name="sp0106"/>
		<odin:gridDataCol name="sp0107" />
		<odin:gridDataCol name="sp0107b" />
		<odin:gridDataCol name="spb02" />
		<odin:gridDataCol name="spb03" />
		<odin:gridDataCol name="spb04" />
		<odin:gridDataCol name="sp0114" />
		<odin:gridDataCol name="sp0108" isLast="true"/>
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn2></odin:gridRowNumColumn2>
		<odin:gridEditColumn2 dataIndex="sp0102" width="110" editor="text" header="����" edited="true" menuDisabled="true" sortable="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="sp0106" width="140" header="���ε�λ��ְ��" editor="text" edited="true" menuDisabled="true" sortable="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="sp0130" width="140" header="Ŀǰ������ְ���" editor="text" edited="true" menuDisabled="true" sortable="false" align="center"/>
		
		<odin:gridEditColumn2 dataIndex="sp0107" width="140" header="����" editor="text" edited="true" menuDisabled="true" sortable="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="sp0107b" width="140" header="ְ��" editor="text" edited="true" menuDisabled="true" sortable="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="sp0131" width="140" header="����" editor="text" edited="true" menuDisabled="true" sortable="false" align="center"/>
		<odin:gridEditColumn2 dataIndex="sp0132" width="140" header="��ְʱְ��" editor="text" edited="true" menuDisabled="true" sortable="false" align="center"/>
		
		<odin:gridEditColumn2 dataIndex="sp0108" width="50" header="����" editor="text" edited="true" menuDisabled="true" sortable="false" align="center"/>
		
		<odin:gridEditColumn2 dataIndex="sp0133" width="70" header="��ע" editor="text" edited="true" menuDisabled="true" sortable="false" align="center" />
		<odin:gridEditColumn2 dataIndex="sp0134" width="70" header="�ѴǱ�־" editor="select" menuDisabled="true" sortable="false" codeType="XZ09" edited="true" align="center" isLast="true"  />
		
	</odin:gridColumnModel>
</odin:editgrid2>
<table style="width: 100%;">
  <tr>
    <odin:textarea property="spp04"  label="�������" rows="6" cols="20" />
    <odin:textarea property="spp05" label="���쵼���" rows="6" />
  </tr>
</table>
<table style="width: 30%;margin-left: 20%">
	<tr>
		<tags:ComBoxWithTree property="spb04" label="����λ" codetype="USER" nodeDblclick="nodeclick" />
		<td align="left" id="passtd" style="">
			<odin:button text="ͬ��"  property="saveS" handler="saveS"></odin:button>
		</td>
		<%-- <td align="left" id="dentd" style="">
			<odin:button text="δͨ��"  property="saveB" handler="saveB"></odin:button>
		</td> --%>
		
	</tr>
</table>

<script type="text/javascript">

function setDisabled1(){//�Ǽǽڵ�
	$('#saveB').css('display','none');
	$('#spp05').each(function(){$(this).
		attr('readonly','readonly').attr('unselectable','on').
		css('background-color','rgb(235,235,228)').css('background-image','none')});
}
function setDisabled2(){//������
	
	$('#spp05').each(function(){$(this).
		attr('readonly','readonly').attr('unselectable','on').
		css('background-color','rgb(235,235,228)').css('background-image','none')});

	

	$('#file03').text('���ϸ���');
}
Ext.onReady(function() {
	$('#spp02').parent().parent().parent().attr('width','33%'); 

	/*��ie�н����������(��ֹֻ��һ����ʾ����Ҫ���ie�������⣬ie8�е�����Ϊ100%ʱ���ı��������ɻ���ʱ*/
	$('textarea').each(function(){$(this).css('width',$(this).innerWidth())})
	
	var viewSize = Ext.getBody().getViewSize();
	var memberGrid = Ext.getCmp('memberGrid');
	var gridobj = document.getElementById('forView_memberGrid');
	var grid_pos = $h.pos(gridobj);
	memberGrid.setHeight(viewSize.height-130-grid_pos.top);

	if(typeof parentParam!='undefined'&&typeof parentParam.spp00!='undefined')
		document.getElementById('spp00').value = parentParam.spp00;

});

function saveCallBack(t,fg){
	//var sp0100 = $('#sp0100').val();
	$h.alert('ϵͳ��ʾ', t, function(){
	/* 	parent.$('#sp0100').val($('#sp0100').val());
		parent.$('#a0000').val($('#a0000').val());*/
		parent.infoSearch(); 
		window.close();
		
		//window.close();
	});
}
var g_contextpath = '<%= request.getContextPath() %>';

function saveS(){
	radow.doEvent('save.onclick','ss2');
}
function saveB(){
	radow.doEvent('save.onclick','ss3');
}
function nodeclick(node,e){
	$('#usertype').val(node.attributes.ntype);
}

function afedt(e,b){
	
	$('#field').val(e.field);
	$('#name').val(e.record.data.sp0102);
	$('#value').val(e.value);
	$('#sp01id').val(e.record.data.sp0100);
	$('#fieldTitle').val(e.grid.colModel.config[e.column].header);
	//console.log(b)
	//alert(e.record.data.sp0102)
	var grid =Ext.getCmp('memberGrid');
	var store = grid.store;
	store.commitChanges() 
	radow.doEvent('saveChange');
	//������
	var addrow = false;
	var rc = store.getAt(store.getCount()-1);
	for(d in rc.data){
		if(d!='sp0100'){
			if(rc.data[d]!=''){
				addrow=true;
				break;
			}
		}
		
	}
	if(addrow)
		doAddrow();
}


function doAddrow(){
	var grid =Ext.getCmp('memberGrid');
	var store = grid.store;
	var p = new Ext.data.Record({
		sp0100:guid(),a0000:'',sp0102:'',sp0130:'',sp0131:'',sp0132:'',sp0133:'',sp0134:'',sp0103:'',sp0104:'',sp0106:'',sp0107:'',sp0107b:'',spb02:'',spb03:'',spb04:'',sp0114:'',sp0108:''
    });
	store.insert(store.getCount(), p);
}


function S4() {
    return (((1+Math.random())*0x10000)|0).toString(16).substring(1);
}
function guid() {
    return (S4()+S4() +S4() +S4() +S4() +S4()+S4()+S4());
}

//�ļ�����
function download(id){
	
	//���ظ���
	//encodeURI��������urlת�룬������Ĵ����������� ����̨���յ�ʱ�������ת�봦��ת������
	window.location="PublishFileServlet?method=downloadFile&SPid="+encodeURI(encodeURI(id));
	
}
</script>
<odin:hidden property="field"/>
<odin:hidden property="value"/>
<odin:hidden property="sp01id"/>
<odin:hidden property="fieldTitle"/>
<odin:hidden property="name"/>

