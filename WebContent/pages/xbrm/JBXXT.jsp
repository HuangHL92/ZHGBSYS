<%@page import="com.insigma.siis.local.pagemodel.xbrm.constant.RMHJ"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@page isELIgnored="false" %>
<%@include file="/comOpenWinInit2.jsp" %>

<%
RMHJ r = new RMHJ();
request.setAttribute("RMHJ", r.rmhj);
request.setAttribute("RYFL", r.ryfl);
%>
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/xbrm/jquery-ui-12.1.css">
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery-ui1.10.4.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<link href="<%=request.getContextPath()%>/jqueryUpload/uploadify.css" rel="stylesheet" type="text/css" />

<script src="<%=request.getContextPath()%>/jqueryUpload/jquery.uploadify.js" type="text/javascript"></script>

<style>
body{
margin: 1px;overflow: auto;
font-family:'����',Simsun;
word-break:break-all;
}
.ui-tabs .ui-tabs-panel{padding: 0px;padding-left: 3px;}
.ui-helper-reset{font-size: 12px;}
.x-form-field-wrap{width: 100%!important;}/*���ڿ�  */
.GBx-fieldset .x-form-trigger{right: 0px;}/*ͼ�����  */
.GBx-fieldset input{width: 100%!important;}
.GBx-fieldset .x-fieldset{padding-bottom: 0px;margin-bottom: -12px;margin-top: 12px}

.GBx-fieldset .x-fieldset-bwrap{overflow-y: auto;}

.marginbottom0px .x-form-item{margin-bottom: 0px;}

.marginbottom0px table,.marginbottom0px table tr th,.marginbottom0px table tr td
{ border:1px solid #74A6CC; padding: 3px; border-right-width: 0px;  }

.marginbottom0px table
{line-height: 25px; text-align: center; border-collapse: collapse;border-right-width: 1px;margin-bottom:20px;}   

.marginbottom0px .x-form-item tr td{
	border:0px;
}
.titleTd{
	background-color: rgb(192,220,241);
	font-weight: bold;
	font-size: 12px;
}

.bh{ display: none; }
.tbh{ display: none; }
.comboh{cursor: pointer!important;background:none!important;background-color:white!important;}
.aclass{font-size: 12px; padding-left: 3px!important;line-height: 30px;}

TEXTAREA.x-form-field{overflow-y:auto;}

.x-grid3-row TD{height: 28px;line-height: 28px;vertical-align: middle;}
.x-grid3-cell-inner{padding-top: 0px;}
.x-tip-header .x-tool{background-image: none;}
</style>
<script type="text/javascript">
//�����ϴ�����ӳټ���
var fnSet={};
$h.ready = function(f,id){
	fnSet[id] = f;
}
var gridJsonStore = {}

function doAddPerson(){//������Ա
	var rbId = document.getElementById('rbId').value;
	var cur_hj = document.getElementById('cur_hj').value;
	var cur_hj_4 = document.getElementById('cur_hj_4').value;
	var dc005 = document.getElementById('dc005').value;
	if(rbId==''){
		$h.alert('ϵͳ��ʾ','��������Ϣ��');
		return;
	}
	$h.openWin('findById321','pages.xbrm.SelectPersonByName','������/���֤��ѯ ',820,520,null,contextPath,window,
			{maximizable:false,resizable:false,RMRY:'������Ա',
		rbId:rbId,cur_hj:cur_hj,cur_hj_4:cur_hj_4,dc005:dc005});
			
	
}

function yjsc(){
	var rbId = document.getElementById('rbId').value;
	
	if(rbId==''){
		$h.alert('ϵͳ��ʾ','��������Ϣ��');
		return;
	}
	$h.openWin('yijianshengcheng','pages.xbrm.Yjsc','���䷽�� ',560,560,null,contextPath,window,
			{maximizable:false,resizable:false,RMRY:'������Ա',
		rbId:rbId,cur_hj:'${RMHJ.DONG_YI}'});
}

function infoDelete(){//�Ƴ���Ա
	var js0100 = document.getElementById('js0100').value;
	var a0101 = document.getElementById('a0101').value;
	if(js0100==''){
		$h.alert('ϵͳ��ʾ','��ѡ����Ա��');
		return;
	}
	Ext.Msg.buttonText.yes = 'ɾ����Ա';
	Ext.Msg.buttonText.no = '�Ƴ���Ա';
	Ext.Msg.buttonText.cancel='ȡ��';
	$h.confirm3btn("ϵͳ��ʾ��",'��������'+a0101+'��<br/>�����ɾ����Ա������ɾ������Ա�����м�ʵ��¼�Լ�������<br/>'+
	'������Ƴ���Ա�������Ӹû����Ƴ���Ա����ʵ��¼�͸������ᱻɾ����',450,function(id) { 
		if("yes"==id){
			radow.doEvent('allDelete',js0100);
		}if("no"==id){
			radow.doEvent('hjDelete',js0100);
		}else{
			return false;
		}		
	});
}
function updateNRM(){//��������������Ϣ��
	Ext.Msg.buttonText.ok = 'ȷ������';
	//Ext.Msg.buttonText.no = '�Ƴ���Ա';
	Ext.Msg.buttonText.cancel='ȡ��';
	$h.confirm("ϵͳ��ʾ��",'����������������Ա����������Ϣ������������Ϣ������֮�������ж���Ա����������Ϣ�е����ģ�ϵͳ���Զ����»�����Ϣ��',450,function(id) { 
		if("ok"==id){
			radow.doEvent('updateNRM');
		}if("cancel"==id){
			return false;
		}else{
			return false;
		}		
	});
}
//��������ѯ
function queryByNameAndIDS(list){
	radow.doEvent('queryByNameAndIDS',list);
}
function doDC(){//�������ά��
	//radow.doEvent('doDC');
	var g_contextpath = '<%= request.getContextPath() %>';
	var rbid = document.getElementById('rbId').value;
	
	$h.openPageModeWin('DeployClass','pages.xbrm.DeployClass','���ά��ҳ��',820,650,{rb_id:rbid},g_contextpath);
	
}
function getCheckList2(index){
	
}
function getCheckList(gridId,fieldName,obj){
	
}

function exportSheets(){
	var g_contextpath = '<%= request.getContextPath() %>';
	var rbid = document.getElementById('rbId').value;
	
	$h.openPageModeWin('ExportSheets','pages.xbrm.ExportSheets','�������',1020,620,{rb_id:rbid},g_contextpath);
	
}
function dc001Select(record,index){
	Ext.getCmp('gridcq').stopEditing(false)
}

var yjrenderer = function(){
	var i = 0;
	var cb = {};
	var ret = {
			
		rd : function(value, params, record, rowIndex, colIndex, ds, colorExp){
	
			//return "<div style=' background-position: -19px -8px;width:31px;height:30px; background-image: url(icos/yujing2.jpg)'></div>";
			var imgsrc = '';
			if(record.data.js0119=='1'){
				imgsrc = "icos/emergency-off.png";
			}else if(record.data.js0119=='2'){
				imgsrc = "icos/emergency-y.png";
			}else{
				imgsrc = "icos/emergency-g.png";
			}
			
			if(value!=''&&value!=null){
				i++;
				//alert(i)
				cb['divrend_'+i] = value;
				//new Ext.ToolTip( { target: 'divrend_'+i, html: value });
				return "<div id='divrend_"+i+"' style='width:30px;height:30px;'><image style='width:24px;height:24px;' src='"+imgsrc+"'/></div>";
			}
			else
				return '';
				
		},
		destroy : function(){
			
			for(var rdid in cb){
				if(!document.getElementById(rdid)){//�����Ԥ��div������
					if(!!Ext.getCmp(rdid+"_tip")){//��Ӧ��tip���ڣ�������
						//alert(rdid+"_tip")
						Ext.getCmp(rdid+"_tip").destroy();
						delete cb[rdid];
					}
					
				}
				
			} 
			//i = 0; cb = {};
			//alert(i);
			return this; 
		},
		initTip : function(){
			this.destroy();
			//Ԥ����Ϣ
			for(var rdid in cb){
				new Ext.ToolTip( { 
					target: rdid, 
					id : rdid+"_tip",
					html: cb[rdid],
					title: 'Ԥ��ԭ��</br>',
					trackMouse:true,
					anchor: 'left',
					dismissDelay: 150000,
			        //autoHide: false,
			        closable: true,
			       // draggable:true,
					anchorOffset: 0
				});
			}
			return this;
		}
	}
	return ret;
}();



</script>
<%-- <%@include file="/comOpenWinInit2.jsp" %> --%>
<odin:hidden property="rbId" title="����id"/>
<odin:hidden property="a0000" title="��Աid"/>
<odin:hidden property="a0101" title="����"/>
<odin:hidden property="js0100" title="������Աid"/>
<odin:hidden property="docpath" title="�ĵ���ַ" />
<odin:hidden property="cur_hj" value="0" title="��ǰ����"/>
<odin:hidden property="cur_hj_4" value="4_1" title="���۾���֧����"/>
<odin:hidden property="tplb" value="" title="�������"/>
<odin:hidden property="dc005" value="1" title="����ʶ"/>
<odin:hidden property="downfile" />
<odin:toolBar property="topbar">
	<odin:buttonForToolBar id="doDC" text="���ά��" handler="doDC" icon="image/icon021a6.gif" />
		<odin:separator></odin:separator>
	<odin:buttonForToolBar text="ѡ����Ա" id="doAddPerson" handler="doAddPerson" icon="image/icon021a2.gif" />
	<odin:buttonForToolBar text="�Ƴ�" icon="image/icon021a3.gif" handler="infoDelete" id="infoDelete" />
	<%-- <odin:fill/>
	<odin:buttonForToolBar text="һ������" icon="image/icon021a2.gif" isLast="true" handler="yjsc" id="yjsc" /> --%>
	
	<odin:fill/>
	<odin:buttonForToolBar text="ģ��ɲ�����" icon="image/icon021a2.gif" handler="imitate" id="imitate" isLast="true"/>
</odin:toolBar>
<odin:toolBar property="bbarid">
</odin:toolBar>



<odin:toolBar property="bbar" applyTo="bbardiv">
	<!-- �����Ƽ� -->
	<odin:buttonForToolBar text="�ֹ����쵼��ͨ������" cls="bh" icon="images/keyedit.gif" id="btn2" handler="exportExcel"/>
	<odin:buttonForToolBar text="���Ƽ����������ѡ����ʡ��ί�������" cls="bh" icon="images/keyedit.gif" id="btn3" handler="exportExcel"/>
	<%-- <odin:buttonForToolBar text="�Ƽ����������ܱ�" cls="bh"  id="btn4" /> 
	<odin:buttonForToolBar text="�������񽻴���" cls="bh"  id="btn5" />
	<odin:buttonForToolBar text="�Ƽ�������ܱ�" cls="bh"  id="btn6" />--%>
	<!-- ���� -->
	<odin:buttonForToolBar text="�����������������ܱ�" cls="bh" icon="images/keyedit.gif" id="btn7" handler="exportExcel"/>
	<!-- ���۾��� -->
	<%-- <odin:buttonForToolBar text="���ר���ɲ����佨�鷽��" cls="bh" icon="images/keyedit.gif" id="btn9" handler="exportExcel"/>
	<odin:buttonForToolBar text="��ί��ɲ����佨�鷽��" cls="bh" icon="images/keyedit.gif" id="btn10" handler="exportExcel"/> --%>
	<odin:buttonForToolBar text="��ί����Ʊ" cls="bh" icon="images/keyedit.gif" id="btn11" handler="exportExcel"/>
	<odin:buttonForToolBar text="��ί����Ʊ���" cls="bh" icon="images/keyedit.gif" id="btn12" handler="exportExcel"/>
	
	<!-- ����ְ -->
	<odin:buttonForToolBar text="�ֹ���(��λ)�쵼��ͨ������" cls="bh" icon="images/keyedit.gif" id="btn17" handler="exportExcel"/>
	<odin:buttonForToolBar text="�ɲ�̸�����ŷ���" icon="images/keyedit.gif" cls="bh"  id="btn14" handler="exportExcel"/>
	
	<odin:buttonForToolBar text="��ʾ" icon="images/keyedit.gif" cls="bh"  id="btn16" handler="exportExcel"/>
	<odin:fill></odin:fill>
	
	<odin:buttonForToolBar text="����" icon="images/icon/save.gif" id="addRow" handler="addRow"/>
	<odin:buttonForToolBar text="����������Ϣ" icon="images/icon/save.gif" id="exportExcel" handler="exportJBXXBExcel"/>
	<odin:buttonForToolBar text="����" icon="images/icon/save.gif" handler="saveobj" isLast="true"/>
</odin:toolBar>

<div id="tabs" >
	<div style="width: 100%; margin-top: 3px;padding-bottom:20px;">
	  <div style="width: 350px;float: left;" id="gridDiv">
		<odin:editgrid2 property="gridcq"  topBarId="topbar" grouping="true" groupCol="dc001" 
		bbarId="bbarid"  load="selectRow"  isFirstLoadData="false"  width="350"  pageSize="9999" 
		afteredit="ffed" clicksToEdit="false" 
		groupTextTpl="{text} ({[values.rs.length]} {[values.rs.length > 1 ? \"��\" : \"��\"]})"
			autoFill="false" >
			<odin:gridJsonDataModel>
				<odin:gridDataCol name="pcheck" /> 
				<odin:gridDataCol name="a0000" />
				<odin:gridDataCol name="js0118" />
				<odin:gridDataCol name="js0119" />
				<odin:gridDataCol name="js0100" />
				<odin:gridDataCol name="a0101" />
				<odin:gridDataCol name="dc001" />
				<odin:gridDataCol name="dc004" />
				<odin:gridDataCol name="a0104" />
				<odin:gridDataCol name="dc001_2" />
				
				<odin:gridDataCol name="a0192a"/>
				<odin:gridDataCol name="havefine" isLast="true"/>
			</odin:gridJsonDataModel>
			<odin:gridColumnModel>
				<odin:gridRowNumColumn></odin:gridRowNumColumn>
				<odin:gridEditColumn2 locked="true" header="selectall" width="40"
							editor="checkbox" dataIndex="pcheck" edited="true"
							hideable="false" gridName="gridcq" 
							checkBoxClick="getCheckList2" checkBoxSelectAllClick="getCheckList" menuDisabled="true" /> 
				<%-- <odin:gridColumn dataIndex="a019998" header="Ԥ��" width="30" align="center" /> --%>
				<odin:gridEditColumn2 dataIndex="a0101" header="����" width="65" sortable="false" menuDisabled="true" edited="false" editor="text" align="center" />
				<odin:gridEditColumn2 dataIndex="dc001" menuDisabled="true" header="�������" sortable="false" width="100" onSelect="dc001Select" edited="true"   editor="select" editorId="dc001" align="center" />
				<odin:gridEditColumn2 dataIndex="dc001_2" menuDisabled="true" header="̸���������" hidden="true" sortable="false" width="180" onSelect="dc001Select" edited="true"   editor="select" editorId="dc001_2" align="center" />
				<%-- <odin:gridEditColumn2 dataIndex="js0118" menuDisabled="true" header="Ԥ��" renderer="yjrenderer.rd" sortable="false" width="45" edited="false" editor="text" align="center" /> --%>
				<odin:gridEditColumn2 dataIndex="js0118" menuDisabled="true" header="Ԥ��" renderer="showLink" sortable="false" width="55" edited="false" editor="text" align="center" />
				
				<odin:gridEditColumn2 dataIndex="a0163" header="��Ա״̬" hidden="true" width="45" align="center" editor="select" edited="false" codeType="ZB126" />
				<odin:gridEditColumn2 dataIndex="havefine" menuDisabled="true" header="������Ϣ" renderer="showLinkFine" sortable="false" width="60" edited="false" editor="text" align="center" isLast="true"/>
			</odin:gridColumnModel>
			<odin:gridJsonData>
				{
			        data:[]
			    }
			</odin:gridJsonData>
		</odin:editgrid2>
	  </div>
			  
	  <div id="peopleInfo" style="float: left;">
	  	<div style="margin-bottom:20px;">
	      <odin:groupBox  property="gp1" title="��������ȡ�������ѯί�к�">
	        <div style="height: 5px;float: left;width: 100%;padding: 0px;margin: 0px;font-size: 0px;"></div>
	        <div  class="marginbottom0px GBx-fieldset" >
	        	<table style="width: 100%">
						<tr rowspan="2">
							<td width="14%" class="titleTd" colspan="1" rowspan="2">��ǩ���</td>
							<td width="14%" class="titleTd" colspan="1">��ȡ���</td>
							<td style="border:0px;style="width:500px;"" colspan="2">
								<odin:checkBoxGroup property="tqyjcheck" data="['name1', '�ͼ������'],['name2', '��������'],['name3', '��Ժ'],['name4', '���Ժ'],['name5', '������������'],['name6', '����']"/>
							</td>
						</tr>
						<tr>
							<td width="14%" class="titleTd" colspan="1">����������</td>
							<td style="width:100px;">
								<odin:radio property="grsxch" value="1" label="��" styleClass="x-form-item"></odin:radio>
							</td>
							<td style="width:100px;">
								<odin:radio property="grsxch" value="0" label="��"></odin:radio>
							</td>
						</tr>
						<tr style="height:50px;">
							<td class="titleTd" colspan="2">��������</td>
							<odin:textarea property="gzsy" title="��������" colspan="2" rows="5"></odin:textarea>
						</tr>
						<tr style="height:50px;">
							<td class="titleTd" colspan="2">��Ϣ��ѯί�к���������</td>
							<odin:textarea property="qtsy" title="��Ϣ��ѯί�к���������" colspan="2" rows="5"></odin:textarea>
						</tr>
					</table>
	        </div>
	      </odin:groupBox>
	    </div>
	     
		<div id="tab-1" class="GBx-fieldset" style="">
			<odin:groupBox property="jbxxb" title="�쵼�ɲ������й���������ʵ���������Ϣ��">
		      		<div id="jibenxinxi" class="marginbottom0px" >
						<table style="width:100%;">
						  	<tr>
						  		<td width="8%" class="titleTd">����</td>
						  		<td width="8%" class="titleTd">����</td>
						  		<td width="5%" class="titleTd">�Ա�</td>
						  		<td width="20%" class="titleTd">����ְ��</td>
						  		<td width="14%" class="titleTd">���֤��</td>
						  		<td width="14%" class="titleTd">������ѯ��</td>
						  		<td width="14%" class="titleTd">�����</td>
						  	</tr>
						  	<tbody id="tableID">
						  		<!-- <tr>
							  		<td width="14%">
							  			<input name='tableIDinput' type="text" />
							  		</td>
									<td style="margin:0px;">
										<input name='tableIDinput' type="text" />
									</td>
									<td style="margin:0px;">
										<input name='tableIDinput' type="text" />
									</td>
									<td style="margin:0px;">
										<input name='tableIDinput' type="text" />
									</td>
									<td style="margin:0px;">
										<input type="text" />
									</td>
									<td style="margin:0px;">
										<input name='tableIDinput' type="text" />
									</td>
									<td style="margin:0px;">
										<input name='tableIDinput' type="text" />
									</td>
							  	</tr>
							  	<tr>
							  		<td width="14%">
							  			<input name='tableIDinput' type="text" />
							  		</td>
									<td style="margin:0px;">
										<input name='tableIDinput' type="text" />
									</td>
									<td style="margin:0px;">
										<input name='tableIDinput' type="text" />
									</td>
									<td style="margin:0px;">
										<input name='tableIDinput' type="text" />
									</td>
									<td style="margin:0px;">
										<input name='tableIDinput' type="text" />
									</td>
									<td style="margin:0px;">
										<input name='tableIDinput' type="text" />
									</td>
									<td style="margin:0px;">
										<input name='tableIDinput' type="text" />
									</td>
						  		</tr> -->
						  	</tbody>
						  				  	
		 			 </table>
					</div>
		     </odin:groupBox>
		</div> 
	  </div>
	</div>
  	<div id="bbardiv"></div>
</div>


<odin:hidden property="js0100s" title="��Աѡ�񼯺�"/>
<!-- ������ʱʱ���� -->
<odin:hidden property="nrmid" title="�ֶ�"/>
<odin:hidden property="nrmdesc" title="�ֶ�����"/>
<odin:hidden property="nrmvalue" title="ֵ"/>
<odin:hidden property="objson" title="objson"  />
<script type="text/javascript">
	
	
	function saveobj(){
		var tbody=document.getElementById("tableID");
		var jsonT="[";
		for(var i=0;i<tbody.rows.length;i++){
			jsonT+='{"target":"'+tbody.rows[i].cells[0].getElementsByTagName("INPUT")[0].value+'",';
			jsonT+='"name":"'+tbody.rows[i].cells[1].getElementsByTagName("INPUT")[0].value+'",';
			jsonT+='"sex":"'+tbody.rows[i].cells[2].getElementsByTagName("INPUT")[0].value+'",';
			jsonT+='"zw":"'+tbody.rows[i].cells[3].getElementsByTagName("INPUT")[0].value+'",';
			jsonT+='"idcard":"'+tbody.rows[i].cells[4].getElementsByTagName("INPUT")[0].value+'",';
			jsonT+='"house":"'+tbody.rows[i].cells[5].getElementsByTagName("INPUT")[0].value+'",';
			jsonT+='"date":"'+tbody.rows[i].cells[6].getElementsByTagName("INPUT")[0].value+'"},';
		}
		jsonT=jsonT.substr(0,jsonT.length-1);
		jsonT+="]";
		document.getElementById('objson').value=jsonT;
		radow.doEvent('save');
		
	}
	
	
	function exportJBXXBExcel(){
		radow.doEvent('exportJBXXTExcel');
	}
	
	function addRow(){
		var tr="<tr style=\"height:30px;\">";
		tr=tr+ "<td><input type=\"text\" /></td>";
		tr=tr+ "<td><input type=\"text\" /></td>";
		tr=tr+ "<td><input type=\"text\" /></td>";
		tr=tr+ "<td><input type=\"text\" /></td>";
		tr=tr+ "<td><input type=\"text\" /></td>";
		tr=tr+ "<td><input type=\"text\" /></td>";
		tr=tr+ "<td><input type=\"text\" /></td>";
		tr=tr+ "</tr>";
		$("#tableID").append(tr);
	}
	
	function reloadTree(){
		setTimeout(xx,1000);
	}
	function xx(){
		var downfile = document.getElementById('downfile').value;
		/* w = window.open("ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile))); */
		window.location.href="ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile));
		ShowCellCover("","��ܰ��ʾ","�����ɹ���");
		setTimeout(function(){},3000);
	}
	
	function getSelect(){
	//��������Դ[��������Դ]
    var combostore = new Ext.data.SimpleStore({
        fields: ['id', 'name'],
        data: [["4", 'ȫ��'], ["4_1", '����'], ["4_2", '���ר���'], ["4_3", '��ί��ɲ����佨�鷽��']]
    });
    //����Combobox
    var combobox = new Ext.form.ComboBox({
        store: combostore,
        width: 150,
        displayField: 'name',
        valueField: 'id',
        triggerAction: 'all',
        emptyText: '��ѡ��...',
        allowBlank: true,
        blankText: '��ѡ����Ա���',
        editable :false,
        cls:'comboh',
        id:'tljdcombo',
        mode: 'local' ,
        forceSelection :true,
        value: "4" ,
        listeners :{
        	show:function(){
        		$('#tljdcombo').removeClass('x-trigger-noedit').parent().removeClass('x-item-disabled');
        	},
        	render:function(){Ext.getCmp('tljdcombo').hide();}
        }
    });
    //Combobox��ȡֵ
    combobox.on('select', function () {
    	if($('#cur_hj').val()=='${RMHJ.TAO_LUN_JUE_DING}'){
    		$('#cur_hj_4').val(combobox.getValue());
    		loadgriddata();
    		//Ext.getCmp('gridcq').store.groupField='company';
    	}
    });
    return combobox;
}
function radiochecked(r){
	if($('#cur_hj').val()=='${RMHJ.TAO_LUN_JUE_DING}'){
		if(r.checked){
			$('#cur_hj_4').val(r.inputValue);
			loadgriddata();
		};
	}
}
function radiochecked2(r){
	if($('#cur_hj').val()=='${RMHJ.REN_MIAN_ZHI}'){
		if(r.checked){
			$('#dc005').val(r.inputValue);
			loadgriddata();
		};
	}
}
function tabclick(obj){
	//$('#tabs ul li a').bind('click', function(event) {
		
    	var $o = $(obj).attr('fn');
    	if(!!fnSet[$o]){
    		fnSet[$o]();//��ʼ���ļ����
	    	delete fnSet[$o];
	    	
    	}
    	if(!!fnSet[$o+"_info"]){
    		fnSet[$o+"_info"]();//��ʼ���ļ����
	    	delete fnSet[$o+"_info"];
    	}
    	
    	var $fn = $(obj).attr('fn');
    	if($fn=='file12'){
    		//ְ��Ԥ��������Ա������Ϣ
    		$( "#personInfo" ).css("display","none");
    	}else{
    		$( "#personInfo" ).css("display","block");
    	}
    	
    	//��ť��ʾ����
    	var $btid = $(obj).attr('btid');
    	if($btid!=null){
    		var arrayid = eval($btid);
    		for(var i=0;i<arrayid.length;i++){
    			if(arrayid[i][1]==1&&!!Ext.getCmp(arrayid[i][0])){
    				Ext.getCmp(arrayid[i][0]).removeClass('bh');
    			}else if(arrayid[i][1]==0&&!!Ext.getCmp(arrayid[i][0])){
    				Ext.getCmp(arrayid[i][0]).addClass('bh');
    			}
    		}
    	}
    	
    	//���� ��ȡ��ǰ���ڵ���Ա
    	var $hj = $(obj).attr('hj');
    	if($hj!=$('#cur_hj').val()){
    		$('#cur_hj').val($hj);
    		if(($hj=="${RMHJ.DONG_YI}"&&$('#cur_hj').val()=="${RMHJ.JI_BEN_QING_KUANG}")||($hj=="${RMHJ.JI_BEN_QING_KUANG}"&&$('#cur_hj').val()=="${RMHJ.DONG_YI}")){
    			
    		}else{
    			
    			loadgriddata();
    			
    		}
    		
    		
    	}else{
    		
    	}
    	if($hj=='4'){
    		Ext.getCmp('bbarid').show();
    	}else{
    		Ext.getCmp('bbarid').hide();
    	}
    	if($hj=='5'){
    		Ext.getCmp('bbarid2').show();
    	}else{
    		Ext.getCmp('bbarid2').hide();
    	}
    	
	//});
}


/* Ext.onReady(function(){
	var jbqkGB = $("#jbxxb .x-fieldset-bwrap");
	jbqkGB.css('height',212);
}) */


Ext.onReady(function(){
	
	
	Ext.getCmp('gridcq').getBottomToolbar().insertButton(0,[
		new Ext.Spacer({width:1,height:25}), 
		{boxLabel: 'ȫ��',hidden:true, name: 'rb-col',xtype: 'radio', inputValue: '4', listeners:{check:radiochecked}},
		{boxLabel: '����', name: 'rb-col',xtype: 'radio', inputValue: '4_1',checked: true ,listeners:{check:radiochecked}},
        {boxLabel: '��ǻ�', name: 'rb-col',xtype: 'radio', inputValue: '4_2',listeners:{check:radiochecked}},
        {boxLabel: '��ί��', name: 'rb-col',xtype: 'radio', inputValue: '4_3',listeners:{check:radiochecked}}
	]);
		
	
	new Ext.Toolbar({
		renderTo:Ext.getCmp('gridcq').bbar,
		items:[
			new Ext.Spacer({width:1,height:25}), 
			{boxLabel: '�������', name: 'rb-col2',xtype: 'radio', inputValue: '1', checked: true ,listeners:{check:radiochecked2}},
			{boxLabel: '̸���������', name: 'rb-col2',xtype: 'radio', inputValue: '2',listeners:{check:radiochecked2}}
		],
		id:"bbarid2"
	}).hide(); 
	
	Ext.getCmp('bbarid').hide();
	$(function() {
	    $( "#tabs" ).tabs({ 'select': function(event, ui) { tabclick(ui.newTab.closest("li a").context) }});
	    $( "#ulTitle" ).css("display","block");
	    
	});
	
	if(typeof parentParam!= 'undefined'){
		document.getElementById('rbId').value=parentParam.rb_id;
	}else{
		document.getElementById('rbId').value='c42981e1-d876-4d5c-9e85-13eb5bad13eb';
	}
	
	_ulTitleObj = $( "#ulTitle" );
	var viewSize = Ext.getBody().getViewSize();
	var peopleInfoGrid =Ext.getCmp('gridcq');
	
	peopleInfoGrid.setHeight(viewSize.height-60);
		
	var gridobj = document.getElementById('forView_gridcq');
	var grid_pos = $h.pos(gridobj);
	
	$( "#peopleInfo" ).css('width',viewSize.width-grid_pos.left-peopleInfoGrid.getWidth()-6);
	//$( "#peopleInfo" ).css('height',peopleInfoGrid.getHeight());
	
	var gp1 = Ext.get("gp1");//������Ϣ
	
	var g_hight = gp1.getHeight()//������Ϣ+������ĸ�
	
	//�������
	//var jbqkGB = $("#jbqkGB .x-fieldset-bwrap");
	//jbqkGB.css('width',gp1.getWidth()-25);
	//jbqkGB.css('height',peopleInfoGrid.getHeight()-g_hight-39);
	//var offest = 0;
	
	var jbqkGB1 = $("#jbxxb .x-fieldset-bwrap");
	jbqkGB1.css('height',250);
	
	$( "#pdata" ).css('width',gp1.getWidth()-140);
	
	peopleInfoGrid.on('rowclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index);
		if(rc.data.js0100!=$('#js0100').val()){//�����б���ѡ���У�ͬһ�е������򲻴������²�ѯ��ϸ��Ϣ
			$('#js0100').val(rc.data.js0100);
			$('#a0101').val(rc.data.a0101);	
			document.getElementById('a0000').value=rc.data.a0000;
			radow.doEvent('peopleInfo',rc.data.a0000);
		}
	});
	
	$('#js0103').parent().parent().parent().attr('width','20%');
	$('#js0102').parent().parent().parent().attr('width','20%');
	$('#js0502').parent().parent().parent().parent().attr('rowspan',2);//����ʱ��
	$('#js0503').parent().parent().parent().attr('rowspan',2);
	$('#js0504').parent().parent().parent().attr('rowspan',2);
	$('#js0108').parent().parent().parent().attr('width','25%'); 
	$('#js0117').parent().parent().parent().attr('width','25%');
	$('#js0402').parent().parent().parent().attr('width','37%');
	$('#js1002').parent().parent().parent().attr('width','30%');
		
	$h.initGridSort('gridcq',function(g){//һ��remove һ��insert һ��sort һ���ᴥ������renderer
		fieldsort();
		yjrenderer.initTip();
		var cur_hj = document.getElementById('cur_hj').value;
		var cur_hj_4 = document.getElementById('cur_hj_4').value;
		if(cur_hj=='${RMHJ.TAO_LUN_JUE_DING}'){
			cur_hj=cur_hj_4;
		}
		if(cur_hj=='${RMHJ.JI_BEN_QING_KUANG}'){
			cur_hj='${RMHJ.DONG_YI}';
		}
		if(cur_hj=='${RMHJ.REN_MIAN_ZHI}'&&$('#dc005').val()=='${RYFL.TAN_HUA_AN_PAI}'){
			cur_hj='5_2';
		}
		updateGridReadStore(g.store,cur_hj);
		radow.doEvent('personsort');
	});
	$('#js0108,#js0111,#js0117').each(function(){$(this).css('width',$(this).innerWidth())})
});


function saveNRMValue(t,v,id){
	if($('#js0100').val()!=''){
		$('#nrmid').val(id);
		$('#nrmvalue').val(v);
		$('#nrmdesc').val($('#'+id).attr('titleLabel'));
		radow.doEvent('saveNRMValue');
	} 
	
	
}


function fieldsort(){
	var g =Ext.getCmp('gridcq');
	var cur_hj = document.getElementById('cur_hj').value;
	var cur_hj_4 = document.getElementById('cur_hj_4').value;
	
	if(cur_hj=='${RMHJ.REN_MIAN_ZHI}'&&$('#dc005').val()=='${RYFL.TAN_HUA_AN_PAI}'){
		g.store.sort('dc001_2','ASC');
	}else{
		g.store.sort('dc001','ASC');
	}
}


function selectRow(a,store){

	var peopleInfoGrid =Ext.getCmp('gridcq');
	var len = peopleInfoGrid.getStore().data.length;
	if( len > 0 ){//Ĭ��ѡ���һ�����ݡ�
		var flag = true;
		for(var i=0;i<len;i++){
			var rc = peopleInfoGrid.getStore().getAt(i);
			if(rc.data.a0000==$('#a0000').val()){
				
				$('#js0100').val(rc.data.js0100);
				peopleInfoGrid.getSelectionModel().selectRow(i,true);
				flag= false;
				setTimeout(function(){peopleInfoGrid.getView().scroller.dom.scrollTop = (i-10)*27;},100);
				break;
			}
		}
		if(flag){
			//ѡ���һ��
			//peopleInfoGrid.getSelectionModel().selectRow(0,true);
			//peopleInfoGrid.fireEvent('rowclick',peopleInfoGrid,0);
		}
		
	}else{
		/* if(parseInt($('#cur_hj').val())>1)
			peopleInfoGrid.getGridEl().select('.x-grid3-body',true).elements[0]
			.dom.innerHTML='<a class="aclass" href="javascript:void(0) onclick="radow.doEvent(\'addprevHJ\');">��������ϸ����ڵĸɲ���Ϣ</a>';
		 */
	}
	saveStore(peopleInfoGrid.store);
	yjrenderer.initTip();
}

//�洢����store
function saveStore(store,record){
	
	var r = store.reader;
	var data = r.jsonData.data;
	
	if(typeof record!='undefined'){
		//���ĺ�������еĻ���
		updateGridJsonStore(record);


	}
	
	var readRecords = {};
	var hj = $('#cur_hj')
    readRecords[store.reader.meta.root] = data;  //"data"      
    readRecords[store.reader.meta.totalProperty] = data.length;//"totalCount"
    var cur_hj = document.getElementById('cur_hj').value;
	var cur_hj_4 = document.getElementById('cur_hj_4').value;
	if(cur_hj=='${RMHJ.TAO_LUN_JUE_DING}'){
		cur_hj=cur_hj_4;
	}
	if(cur_hj=='${RMHJ.JI_BEN_QING_KUANG}'){
		cur_hj='${RMHJ.DONG_YI}';
	}
	if(cur_hj=='${RMHJ.REN_MIAN_ZHI}'&&$('#dc005').val()=='${RYFL.TAN_HUA_AN_PAI}'){
		cur_hj='5_2';
	}
	gridJsonStore[cur_hj]=readRecords;
}
//������������ʱ��ն������ݻ��棬 
function clearGridJsonStore(cur_hj){
	delete gridJsonStore[cur_hj];
}
//
//�������ݻ���
function updateGridJsonStore(record){
	var data;
	//̸������ֻ���µ�ǰ�Լ������
	var cur_hj = document.getElementById('cur_hj').value;
	if(cur_hj=='${RMHJ.REN_MIAN_ZHI}'&&$('#dc005').val()=='${RYFL.TAN_HUA_AN_PAI}'){
		data = gridJsonStore[cur_hj+"_2"]["data"];
		var js0100 = record.data.js0100;
		for(var i=0;i<data.length;i++){
			if(js0100==data[i].js0100){
				data[i]=record.data;
				return;
			}
		}
		return;
	}
	
	for(cur_hj in gridJsonStore){
		data = gridJsonStore[cur_hj]["data"];
		var js0100 = record.data.js0100;
		for(var i=0;i<data.length;i++){
			if(js0100==data[i].js0100){
				data[i]=record.data;
				break;
			}
		}
	}
}
//����reader����
function updateGridReadStore(store,cur_hj){
	var data;
	data = gridJsonStore[cur_hj]["data"];
	for(var i=0;i<data.length;i++){
		data[i]=store.getAt(i).data;
	}
	
}
//�������ݻ���
function deleteGridJsonStoreRecord(js0100){
	var data;
	for(var cur_hj in gridJsonStore){
		data = gridJsonStore[cur_hj]["data"];
		
		for(var i=0;i<data.length;i++){
			if(js0100==data[i].js0100){
				data.splice(i,1);//ɾ����������
				break;
			}
		}
	}
}
//�������ݣ������л��棬����ػ��棬���������̨
function loadgriddata(){
	var cur_hj = document.getElementById('cur_hj').value;
	var cur_hj_4 = document.getElementById('cur_hj_4').value;
	if(cur_hj=='${RMHJ.TAO_LUN_JUE_DING}'){
		cur_hj=cur_hj_4;
	}
	if(cur_hj=='${RMHJ.JI_BEN_QING_KUANG}'){
		cur_hj='${RMHJ.DONG_YI}';
	}
	
	var peopleInfoGrid =Ext.getCmp('gridcq');
	var  columnModel = peopleInfoGrid.getColumnModel(); 
	
	if(cur_hj=='${RMHJ.REN_MIAN_ZHI}'&&$('#dc005').val()=='${RYFL.TAN_HUA_AN_PAI}'){
		cur_hj='5_2';
		columnModel.setHidden(4,false);
		columnModel.setHidden(3,true);
		peopleInfoGrid.store.groupField='dc001_2';
	}else{
		columnModel.setHidden(4,true);
		columnModel.setHidden(3,false);
		peopleInfoGrid.store.groupField='dc001';
	}
	
	fieldsort();
	
	if(!!gridJsonStore[cur_hj]){
		peopleInfoGrid.store.loadData(gridJsonStore[cur_hj], false);
	}else{
		radow.doEvent('gridcq.dogridquery');
	}
}
//����groupbox�����
function adjustGB(oriObj,refObj,offset){
	oriObj.css('width',refObj.width());
	oriObj.css('height',refObj.height()-offset);
}

function adjustGB2(oriObj,refObj,offset){
	var peopleInfoGrid =Ext.getCmp('gridcq');
	oriObj.css('width',refObj.width());
	oriObj.css('height',peopleInfoGrid.getHeight()-39);
}
//����������Ӧtd��
function adjustSelectWidth(id){
	Ext.getCmp(id+'_combo').setWidth($('#tdid_'+id).width()+3);
}

var flength = 0;curfindex=0;
function onUploadSuccess(file, jsondata, response){
	curfindex++;
	updateProgress(curfindex,flength,jsondata.file_name);
	if(curfindex==flength){
		Ext.Msg.hide();
		Ext.example.msg('','����ɹ�!');
	}
}
function setFileLength(){
	curfindex=0;
	flength = 0;var hz = '';
	for(i=2;i<12;i++){
		if(i<10){
			hz = '0'+i;
		}else{
			hz = i;
		}
		if(!fnSet["file"+hz]){//�Ƿ��Ѿ���ʼ��
			flength = flength + eval("$('#file"+hz+"').data('uploadify').queueData.queueLength;");
		}
	}
	if(flength>0){
		$h.progress('��ȴ�', '�����ϴ��ļ�...',null,300);
	}else{
		Ext.Msg.hide();
		Ext.example.msg('','����ɹ�!');
	}
}
function updateProgress(cur,total,fname){
	if (fname.length > 15) {
		fname = fname.substr(0,15) + '...';
	}
	Ext.MessageBox.updateProgress(cur / total, '�����ϴ��ļ�:'+fname+'����ʣ'+(total-cur)+'��');
}
//�ļ�����
function download(id){
	
	//���ظ���
	//encodeURI��������urlת�룬������Ĵ����������� ����̨���յ�ʱ�������ת�봦��ת������
	window.location="PublishFileServlet?method=downloadFile&id="+encodeURI(encodeURI(id));
	
}

function ffed(e,b){
	alert(666)
	var dc003 = e.value;
	var js0100 = e.record.data.js0100;
	var grid =Ext.getCmp('gridcq');
	
	var store = grid.store;
	
	store.commitChanges() 
	//var r = store.reader;
	//var data = r.jsonData.data;
	//var readRecords = {};
   // readRecords[store.reader.meta.root] = data;        
   // readRecords[store.reader.meta.totalProperty] = data.length;                     	                   
    //store.loadData(readRecords, true);
	//grid.view.refresh();
	var dc003array = dc003.split("@@");
	if(dc003array.length==2){
		dc003 = dc003array[1];
		if(dc003=='999'){
			dc003='';
		}
		radow.doEvent('savedata',js0100+"@@@"+dc003+"");
		fieldsort();
		saveStore(store,e.record);
	}else{
		return;
	}
	
}

function downloadByUUID(){
	var downloadUUID = document.getElementById('docpath').value;
	window.location='<%= request.getContextPath() %>/PublishFileServlet?method=downloadFile&uuid='+downloadUUID;
	return false
}

function ShowCellCover(elementId, titles, msgs) {	
	Ext.MessageBox.buttonText.ok = "�ر�";
	if(elementId.indexOf("start") != -1){
	
		Ext.MessageBox.show({
			title:titles,
			msg:msgs,
			width:300,
	        height:300,
			closable:false,
			modal:true,
			progress:true,
			wait:true,
			animEl: 'elId',
			increment:5, 
			waitConfig: {interval:150}
		});
	}
}
/*��� ��*/
function exportExcel(obj){
	
	var peopleInfoGrid =Ext.getCmp('gridcq');
	var store = peopleInfoGrid.store;
	var js0100s = "";
	for(var i = 0; i < store.getCount(); i++){
		var rowData = store.getAt(i);
		if(rowData.data.pcheck == true){
			js0100s = js0100s + rowData.data.js0100 + ",";
		}
	}
	
	document.getElementById("js0100s").value=js0100s;
	
	var rbId=document.getElementById("rbId").value;
	var buttonid = obj.id;
	var buttontext = obj.text;
	//alert(param);
	var path = '<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.xbrm.QCJS&eventNames=ExpGird';
	//alert(path);
	ShowCellCover('start','ϵͳ��ʾ','��������ɲ����佨�鷽�� ,�����Ե�...');
   	Ext.Ajax.request({
   		timeout: 60000,
   		url: path,
   		async: true,
   		method :"post",
   		form : 'commform',
   		params : {rbId:rbId,buttonid:buttonid,buttontext:buttontext},
        callback: function (options, success, response) {
      	   if (success) {
      		   Ext.Msg.hide();
      		   var result = response.responseText;
 			   if(result){
 				  var cfg = Ext.util.JSON.decode(result);
 				 //alert(cfg.messageCode)
 					if(0==cfg.messageCode){
 						if("�����ɹ���"!=cfg.mainMessage){
 							Ext.Msg.alert('ϵͳ��ʾ:',cfg.mainMessage);
 							return;
 						}
 						if(cfg.elementsScript!=""){
 							if(cfg.elementsScript.indexOf("\n")>0){
 	 							cfg.elementsScript = cfg.elementsScript.replace(/\r/gi,"");
 	 							cfg.elementsScript = cfg.elementsScript.replace(/\n/gi,"\\n");
 	 						}
 	 						
 	 						//console.log(cfg.elementsScript);
 	 						eval(cfg.elementsScript);
 						}else{
							 		 					
 						}
 					}else{
 						Ext.Msg.alert('ϵͳ��ʾ:',cfg.mainMessage);
						return;
 					}
 				}
      	   }
        }
   });
}

//��ʾ��ϸ
function showLink(value, params, record, rowIndex, colIndex, ds) {
	//alert(222)
	var grid = Ext.getCmp('gridcq');
	var record = grid.getStore().getAt(rowIndex);
	var js0119 = record.get('js0119');
	var js0100 = record.get('js0100');
	
	var src = "";
	var alt = "";
	if("1"==js0119){
		src += "<%=request.getContextPath()%>/icos/emergency-off.png";
		alt = "���Ԥ��";
	} else if("2"==js0119){
		src += "<%=request.getContextPath()%>/icos/emergency-y.png";
		alt = "�Ƶ�Ԥ��";
	} else if("3"==js0119){
		src += "<%=request.getContextPath()%>/icos/emergency-g.png";
		alt = "�̵�Ԥ��";
	} else if("4"==js0119){
		<%-- src += "<%=request.getContextPath()%>/icos/fine.png";
		alt = "����"; --%>
		
		src = "";
		alt = "";
	} 
	
	if(""==src){
		return "";
	}
	
	//Ҫ��ʾ����ͼ��ſ����
	return "<img onclick='showDetail(\""+js0100+"\",\"-1\")' alt='"+alt+"' src='"+src+"' width='20' height='20' >";
	
	//ֻ��ʾ�鿴��ϸ
	//return "<a href='javascript:showDetail(\""+js0100+"\")'>�鿴��ϸ</a>";
}

//��ʾ��ϸ
function showLinkFine(value, params, record, rowIndex, colIndex, ds) {
	//alert(111)
	var grid = Ext.getCmp('gridcq');
	var record = grid.getStore().getAt(rowIndex);
	var js0100 = record.get('js0100');
	
	
	var src = "";
	var alt = "";
	if("1"==value){
		src += "<%=request.getContextPath()%>/icos/fine.png";
		alt = "����";
	} else {
		src += "";
		alt = "";
	} 
	
	if(""==src){
		return "";
	}
	
	//Ҫ��ʾ����ͼ��ſ����
	return "<img onclick='showDetail(\""+js0100+"\",\"1\")' alt='"+alt+"' src='"+src+"' width='20' height='20' >";
}

function showDetail(js0100,type){
	$h.openWin('yjmx','pages.xbrm.YJMX','Ԥ����ϸ ',820,520,null,contextPath,window,{js0100:js0100,type:type});
}

//ģ��ɲ�����
function imitate(){
	var grid = Ext.getCmp('gridcq');
	//alert(222)
	var total = grid.getStore().getCount();//��������
	var record; //������
	var a0000s = ""; //���θɲ�����
	
	for(var i=0; i<total; i++){
		record = grid.getStore().getAt(i);
		if(true==record.get('pcheck')){
			a0000s += record.get('a0000')+"@#@";
		}
	}
	
	if(""==a0000s){
		Ext.MessageBox.alert("��ʾ","��ѡ����Ա!");
	} else{
		$h.openWin('mnrm','pages.xbrm.MNRM','ģ������ ',820,520,null,contextPath,window,{a0000s:a0000s});
	}
}
</script>
