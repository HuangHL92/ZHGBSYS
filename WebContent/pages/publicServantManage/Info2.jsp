<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ page import="java.util.Hashtable"%>

<%--web ���˼ƻ��걨 wzp --%>
<link href="<%=request.getContextPath()%>/pages/css/control.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/js/browerinfo.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/js/jquery-1.8.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/js/jquery.easing.1.3.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/js/stepBar.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/js/newpage.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/customquery.js"></script>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="com.insigma.siis.local.business.sysorg.org.CreateSysOrgBS"%>
<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.SysOrgPageModel"%>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.Info2PageModel"%>
<%@page import="com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_001.GroupManagePageModel"%>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.FontConfigPageModel"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<%String ctxPath = request.getContextPath(); 
String a0000 = request.getParameter("a0000");
%>

<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="js/lengthValidator.js"></script>
<script type="text/javascript">
var ctxPath = '<%=ctxPath%>';

$(function(){
	stepBar.init("stepBar", {
		step : 1,
		change : false,
		animation : true
	});
});
<%--��ʼ��--%>
function init(){
	step_func();
	daying11(1);
	var grid = odin.ext.getCmp('fjcl_div');
	var clientheight=document.body.clientHeight;
	var clientwidth=document.body.clientWidth;
}
function daying11(value){
	if(value<=stepvar){
		hidden_all();
		$("#"+arr[value-1]).css('display','');
		if(value==1){
		}else if(value==2){
			<%--��¼�ù���Ա�ƻ�����--%>
			radow.doEvent("list_xlygw.dogridquery");
		}else if(value==3){
			<%--��������--%>
			radow.doEvent("list_yscfj.dogridquery");
		}else if(value==4){
			odin.alert("�������!");
		}
	}
}

function hidden_all(){
	document.getElementById("fjcl_div").style.display='none';
	document.getElementById("xlygwyjhxq_div").style.display='none';
	document.getElementById("zrjhxx_div").style.display='none';
	document.getElementById("wcss_div").style.display='none';
}

</script>


<div id="stepBar" class="ui-stepBar-wrap">
	<div class="ui-stepBar">
		<div class="ui-stepProcess"></div>
	</div>
	<div class="ui-stepInfo-wrap">
		<table class="ui-stepLayout" border="0" cellpadding="0" cellspacing="0" style="height: 120px;">
			<tr>
				<td class="ui-stepInfo">
					
					<a class="ui-stepSequence" onclick="djb(1)">1</a>
					<p class="ui-stepName" >��Ա������Ϣ</p>
				</td>
				<td class="ui-stepInfo">
					<a class="ui-stepSequence" onclick="djb(2)">2</a>
					<p class="ui-stepName" >������λ��ְ��</p>
				</td>
				<td class="ui-stepInfo">
					<a class="ui-stepSequence" onclick="djb(3)">3</a>
					<p class="ui-stepName" >��ְ����</p>
				</td>
				<td class="ui-stepInfo">
					<a class="ui-stepSequence" onclick="djb(4)">4</a>
					<p class="ui-stepName" >��ְ��</p>
				</td>
				<td class="ui-stepInfo">
					<a class="ui-stepSequence" onclick="djb(5)">5</a>
					<p class="ui-stepName" >רҵ����ְ��</p>
				</td>
				<td class="ui-stepInfo">
					<a class="ui-stepSequence" onclick="djb(6)">6</a>
					<p class="ui-stepName">ѧ��ѧλ</p>
				</td>
				<td class="ui-stepInfo">
					<a class="ui-stepSequence" onclick="djb(7)">7</a>
					<p class="ui-stepName">��������</p>
				</td>
				<td class="ui-stepInfo">
					<a class="ui-stepSequence" onclick="djb(8)">8</a>
					<p class="ui-stepName">������Ϣ</p>
				</td>
				<td class="ui-stepInfo">
					<a class="ui-stepSequence" onclick="djb(9)">9</a>
					<p class="ui-stepName">��ͥ��Ա</p>
				</td>
				<td class="ui-stepInfo">
					<a class="ui-stepSequence" onclick="djb(10)">10</a>
					<p class="ui-stepName">�������</p>
				</td>
				<td class="ui-stepInfo">
					<a class="ui-stepSequence" onclick="djb(11)">11</a>
					<p class="ui-stepName">�˳�����</p>
				</td>
				<td class="ui-stepInfo">
					<a class="ui-stepSequence" onclick="djb(12)">12</a>
					<p class="ui-stepName">������</p>
				</td>
				<td class="ui-stepInfo">
					<a class="ui-stepSequence" onclick="djb(13)">13</a>
					<p class="ui-stepName">�Զ���ָ����</p>
				</td>
				<td class="ui-stepInfo">
					<a class="ui-stepSequence" onclick="djb(14)">14</a>
					<p class="ui-stepName">���ӵ���</p>
				</td>
			</tr>
		</table>
	</div>
</div>
<%--������� start --%>
<div id="wcss_div" style="display:'';">
	<hr style="height:1px;border:none;border-top:1px solid #99BBE8;" width="100%">
</div>
<%--������� end --%>

<script type="text/javascript">

</script>
<odin:hidden property="status" title="ɾ��״̬" ></odin:hidden>
<odin:hidden property="a0000" title="����a0000" ></odin:hidden>
<odin:hidden property="a0163" title="��Ա״̬" ></odin:hidden>
<odin:hidden property="comboxArea_a0111" title="����" ></odin:hidden>
<odin:hidden property="comboxArea_a0114" title="������" ></odin:hidden>
<!-- 1�����޸�Ȩ�ޣ�2�����޸�Ȩ�� -->
<odin:hidden property="isUpdate" title="�Ƿ����޸�Ȩ��" ></odin:hidden>

<!-- �뵳ʱ��ʹ�����ֵ -->
<odin:hidden property="a0141"></odin:hidden>
<odin:hidden property="a0144"></odin:hidden>
<odin:hidden property="a3921"></odin:hidden>
<odin:hidden property="a3927"></odin:hidden>

<!-- A01�����¼���У���Ϣ��¼��û�е���Ϣ������������Ϊ�˷�ֹ��Ϣ��ʧ -->
<odin:hidden property="a0180" title="��ע"></odin:hidden>
<odin:hidden property="a0221" title="��ְ����"></odin:hidden>
<odin:hidden property="a0288" title="��ְ����ʱ��"></odin:hidden>
<odin:hidden property="a0192e" title="��ְ��"></odin:hidden>
<odin:hidden property="a0192c" title="��ְ��ʱ��"></odin:hidden>

<odin:hidden property="qrzxl" title="ȫ����ѧ��"></odin:hidden>
<odin:hidden property="qrzxlxx" title="ȫ����ѧ��ѧУ"></odin:hidden>
<odin:hidden property="qrzxw" title="ȫ����ѧλ"></odin:hidden>
<odin:hidden property="qrzxwxx" title="ȫ����ѧλѧУ"></odin:hidden>
<odin:hidden property="zzxl" title="��ְѧ��"></odin:hidden>
<odin:hidden property="zzxlxx" title="��ְѧ��ѧУ"></odin:hidden>
<odin:hidden property="zzxw" title="��ְѧλ"></odin:hidden>
<odin:hidden property="zzxwxx" title="��ְѧλѧУ"></odin:hidden>

<odin:hidden property="b0194Type" value=''/>
<odin:hidden property="a0192f" title="������λ��ְ��ȫ�ƶ�Ӧ�ģ���ְʱ��" ></odin:hidden>

<div id="div1" style="margin-left:5%;display:'';width: 95%;border:1px solid #00F">
<!-- <odin:groupBox title="��Ա������Ϣ"> -->
	<div id="btnToolBarDivA01" style="width: 100%;"></div>
	<odin:toolBar property="btnToolBarA01" applyTo="btnToolBarDivA01">
					<odin:fill></odin:fill>
					<odin:buttonForToolBar text="��һ��Ա" id="lastp" icon="images/icon/left2.gif"/>
					<odin:buttonForToolBar text="��һ��Ա" id="nextp" icon="images/icon//right2.gif"/>
					<odin:buttonForToolBar text="����" id="InfoSaveA01" isLast="true" icon="images/save.gif" cls="x-btn-text-icon" handler="savePerson"></odin:buttonForToolBar>
	</odin:toolBar>
	<table style="width:100%;" >
		<col width="10%">
		<col width="23%">
		<col width="10%">
		<col width="23%">
		<col width="10%">
		<col width="24%">
		<tr>
			<odin:textEdit property="a0101" required="true" label="����"></odin:textEdit>
			<odin:textEdit property="a0184" label="�������֤����" required="true" validator="$h.IDCard"></odin:textEdit>
			<odin:select2 property="a0104" required="true" label="�Ա�" codeType="GB2261"></odin:select2>
			
		</tr>
		<tr>
			<odin:NewDateEditTag property="a0107" required="true" label="��������" isCheck="true" maxlength="8"></odin:NewDateEditTag>
			<odin:select2 property="a0117" required="true" label="����" codeType="GB3304"></odin:select2>		
			<tags:PublicTextIconEdit property="a0111" label="����" codetype="ZB01" readonly="true" required="true" onchange="a0111Change" codename="code_name3"></tags:PublicTextIconEdit>
		</tr>
		<tr>
			<tags:PublicTextIconEdit property="a0114" label="������" codetype="ZB01" readonly="true" required="true" onchange="a0114Change" codename="code_name3"></tags:PublicTextIconEdit>	
			<odin:textEdit property="a0140" label="�뵳ʱ��" ondblclick="a0140Click()" onkeypress="a0140Click2()"  onclick="a0140Click()"></odin:textEdit>
			<odin:NewDateEditTag property="a0134" label="�μӹ���ʱ��" isCheck="true" maxlength="8" required="true"></odin:NewDateEditTag>
		</tr>
		<tr>
			<odin:select2 property="a0128" required="true" label="����״��" codeType="GB2261D"></odin:select2>
			<odin:textEdit property="a0187a" label="ר��"></odin:textEdit>
			<odin:select2 property="a0165" codeType="ZB130" label="�������" required="true"></odin:select2>
		</tr>
		<tr>
			<tags:PublicTextIconEdit property="a0160" codetype="ZB125" width="160" label="��Ա���" readonly="true" required="true"/>
			<odin:select2 property="a0121" codeType="ZB135" label="��������" required="true"></odin:select2>
			<!-- <odin:select2 hideTrigger="true" property="a0221" codeType="ZB09" width="177" ondblclick="a0221Click()" onkeypress="a0221Click2()" readonly="true" label="��ְ����"></odin:select2> -->
		</tr>
		<!-- <tr>
			<odin:textEdit property="a0288" width="177" readonly="true" label="��ְ����ʱ��"></odin:textEdit>
			<odin:select2 hideTrigger="true" property="a0192e" codeType="ZB148" width="177" ondblclick="a0192eClick()" onkeypress="a0192eClick2()" readonly="true" label="��ְ��"></odin:select2>
			<odin:textEdit property="a0192c" width="177" readonly="true" label="��ְ��ʱ��"></odin:textEdit>
		</tr> -->
		<tr>
			<odin:textarea property="a1701" label="����" colspan='4' rows="16"></odin:textarea>
			
			<td class="top-last label-clor height10-359">
				<div style="width: 100%;height: 100%;position: relative;">
					<div onclick="showwin()"  title="��������" style="width:22px;height:20px;background-image:url(images/icon_column.gif);background-size:100%;  margin-bottom: 0px;position: absolute;top: 2px;left: 0px;cursor:pointer;"></div>
				</div>
			</td>
			<td class="left-last label-clor width7-120" rowspan="4">
				<div style="width:120px; height:170px;cursor: pointer;" onclick="showdialog()">
					<img alt="��Ƭ" id="personImg" style="display: block;margin: 0px;padding: 0px;cursor: pointer; " width="136" height="170" src=""  /> 
				</div>
			</td>
		</tr>
	</table>
	
<!-- </odin:groupBox> -->
<%--��������--%>
	<div id="jlcontent" style="display: none;">
	    <odin:tab id="jltab" >
		    <odin:tabModel>
		    	<odin:tabItem title="��������" id="tab1"></odin:tabItem>
		    	<odin:tabItem title="��ְ���Զ����ɼ���" id="tab2" isLast="true"></odin:tabItem>
		    </odin:tabModel>
		    <odin:tabCont itemIndex="tab1">
				<span id="contenttext" style="font-family: '����', Simsun;">
					<%--����16���ַ� 2���ո� ����--%> 
				<br>   
				1973.07--1977.09&nbsp;&nbsp;ĳĳʡĳĳ��ĳĳ��Сѧ��ʦ<br/>
				1977.09--1979.09&nbsp;&nbsp;ĳĳʡĳĳ��ĳĳ����н�ʦ<br/>
				1979.09--1988.11&nbsp;&nbsp;ĳĳʡĳĳ��ί��У��ʦ����䣺1985.10--<br/>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;1988.07��ĳĳʡί��У��̴�ר��ѧϰ��<br/>
				1988.11--1993.07&nbsp;&nbsp;ĳĳʡĳĳ��ί���������¡����Ƴ����Ƴ�<br/>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��1987.09--1992.07��ĳĳ��ѧĳĳϵĳĳרҵѧϰ��<br/>
				1993.07--1995.11&nbsp;&nbsp;ĳĳʡĳĳ��ĳĳ�ָ��ֳ�<br/>
				1995.11--1998.05&nbsp;&nbsp;ĳĳʡĳĳ��ĳĳ�־ֳ�<br/>
				1998.05--2005.09&nbsp;&nbsp;ĳĳʡĳĳ�и��г�����䣺2001.08--2004.05<br/>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�����뵳Уĳĳרҵ�о�����ѧϰ��<br/>
				2005.09--2005.10&nbsp;&nbsp;ĳĳʡĳĳ��ί��ί�����г�<br/>
				2005.10--2007.02&nbsp;&nbsp;ĳĳʡĳĳ��ί����ǡ����г�<br/>
				2007.02--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;ĳĳʡĳĳ��ί����ǡ��г�<br/>
																<br/>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;�ڡ����������ίԱ	
				</span>
				<%-- <odin:button text="ȫѡ" property="qx1" handler="selectall"></odin:button> --%>	
				<div style="height: 70px"></div>
			</odin:tabCont>
		    <odin:tabCont itemIndex="tab2">
		    	<br>
		    	<span id="contenttext2" style="font-family: '����', Simsun;height: 240px"></span>
		    	<odin:button property="qx2" text="&nbsp;&nbsp;ȫ&nbsp;&nbsp;ѡ&nbsp;&nbsp;" handler="selectall2"></odin:button>	
		    </odin:tabCont>
	    </odin:tab>
	</div>
<!-- <table style="width:100%;" >
	<col width="50%">
	<col width="50%">
	<tr>
		<td align="right">
			<odin:button property="InfoSaveA01" handler="savePerson" text="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"></odin:button>
		</td>
		<td align="left">
			<odin:button text="���沢��һ��" property="bcn1"></odin:button>
		</td>
	</tr>
</table> -->
</div>

<script type="text/javascript">
function savePerson(a,b,confirm){
	var a0101 = document.getElementById('a0101').value;//����
	var a0184 = document.getElementById('a0184').value;//���֤��
	var a0107 = document.getElementById('a0107').value;//��������
	
	var orthersWindow = null;
	
	
	if(a0101==''){
		$h.alert('ϵͳ��ʾ��','��������Ϊ�գ�',null,220);
		return false;
	}
	if (a0101.indexOf(" ") >=0){
		$h.alert('ϵͳ��ʾ',"�������ܰ����ո�",null,220);
		return false;
	}
	if(a0101.length>18){
		$h.alert('ϵͳ��ʾ��','�����������ܳ���18��',null,220); 
		return false;
	}
	
	//У�����֤�Ƿ�Ϸ�
	if(a0184==''){
		$h.alert('ϵͳ��ʾ��','���֤�Ų���Ϊ��!',null,220);
		return false;
	}
	if(a0184.length>18){
		$h.alert('ϵͳ��ʾ��','���֤�Ų��ܳ���18λ!',null,220);
		return false;
	}
	
	
	var vtext = $h.IDCard(a0184);
	if(vtext!==true){
		//$h.alert('ϵͳ��ʾ��',vtext,null,320);
		$h.confirm("ϵͳ��ʾ��",vtext+'��<br/>�Ƿ�������棿',400,function(id) { 
			if("ok"==id){
				Ext.getCmp('a0184').clearInvalid();
				savePersonSub(a,b,confirm,false);
			}else{
				return false;
			}		
		});
	}else{
		savePersonSub(a,b,confirm,true);
	}
}


function savePersonSub(a,b,confirm,isIdcard){
	var a0101 = document.getElementById('a0101').value;//����
	var a0184 = document.getElementById('a0184').value;//���֤��
	var a0107 = document.getElementById('a0107').value;//��������
	var a0104 = document.getElementById('a0104').value;//�Ա� 
	
	if(a0104==''){
		$h.alert('ϵͳ��ʾ��','�Ա���Ϊ�գ�',null,220);
		return false;
	}
	
	//�������ڸ�ʽ
	var datetext = $h.date(a0107);
	if(datetext!==true){
		$h.alert('ϵͳ��ʾ��','�������£�'+datetext,null,320);
		return false;
	}
	
	var birthdaya0184 = getBirthdatByIdNo(a0184);
	var birthdaya0107 = a0107;//��������
	var msg = '�������������֤��һ��,�Ϸ����ڸ�ʽΪ:'+birthdaya0184+'��'+birthdaya0184.substring(0, 6)+'��<br/>�Ƿ�������棿';
	if(isIdcard&&(birthdaya0107==''||(birthdaya0107!=birthdaya0184&&birthdaya0107!=birthdaya0184.substring(0, 6)))){
		$h.confirm("ϵͳ��ʾ��",msg,400,function(id) { 
			if("ok"==id){
				radow.doEvent('InfoSave.onclick',confirm);
			}else{
				return false;
			}		
		});	
		return false;
	}else{
		radow.doEvent('InfoSave.onclick',confirm);
		//������Ϣ
		
	}
}
</script>

<!--------------------------------------- ְ����Ϣ�� --------------------------------------------->
				<%@include file="/comOpenWinInit.jsp" %>
				<script type="text/javascript">
				function deleteRowRendererA02(value, params, record,rowIndex,colIndex,ds){
					var a0200 = record.data.a0200;
					if(parent.buttonDisabled){
						return "ɾ��";
					}
					return "<a href=\"javascript:deleteRow2(&quot;"+a0200+"&quot;)\">ɾ��</a>";
				}
				function deleteRow2(a0200){ 
					var gridSize = Ext.getCmp("WorkUnitsGrid").getStore().getCount();
					if(gridSize<=1){
						Ext.Msg.alert("ϵͳ��ʾ","���һ�������޷�ɾ����");
						return;
					}
					Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ�ȷ��ɾ����",function(id) { 
						if("yes"==id){
							radow.doEvent('deleteRowA02',a0200);
						}else{
							return;
						}		
					});	
				}
				
				function deleteRow(){ 
					var sm = Ext.getCmp("WorkUnitsGrid").getSelectionModel();
					if(!sm.hasSelection()){
						Ext.Msg.alert("ϵͳ��ʾ","��ѡ��һ�����ݣ�");
						return;
					}
					var gridSize = Ext.getCmp("WorkUnitsGrid").getStore().getCount();
					if(gridSize<=1){
						Ext.Msg.alert("ϵͳ��ʾ","���һ�������޷�ɾ����");
						return;
					}
					Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ�ȷ��ɾ����",function(id) { 
						if("yes"==id){
							radow.doEvent('deleteRowA02',sm.lastActive+'');
						}else{
							return;
						}		
					});	
				}
				Ext.onReady(function(){});
				//������λְ�������
				function a02checkBoxColClick(rowIndex,colIndex,dataIndex,gridName){
					if(parent.buttonDisabled){
						return;
					}
					
					var sr = getGridSelected(gridName);
					if(!sr){
						return;
					}
					//alert(sr.data.a0800);
					radow.doEvent('workUnitsgridchecked',sr.data.a0200);
				}
				
				
				
				function changeSelectData(item){
					var a0255f = Ext.getCmp("a0255_combo");
					var newStore = a0255f.getStore();
					newStore.removeAll();
					newStore.add(new Ext.data.Record(item.one));
					newStore.add(new Ext.data.Record(item.two));
					var keya0255 = document.getElementById("a0255").value;//alert(item.one.key+','+keya0255);
					if(item.one.key==keya0255){
						a0255f.setValue(item.one.value);
					}else if(keya0255==''){
						a0255f.setValue(item.one.value);
						document.getElementById("a0255").value=item.one.key;
					}else{
						a0255f.setValue(item.two.value);
						document.getElementById("a0255").value=item.two.key;
					}
				}
				
				var labelText={'a0255SpanId':['&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">*</font>��ְ״̬','&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<font color="red">*</font>����״̬'],
							   'a0201bSpanId':['<font color="red">*</font>��ְ��������','<font color="red">*</font>������������'],
							   'a0216aSpanId':['<font color="red">*</font>ְ������','<font color="red">*</font>��λ����'],
							   //'a0215aSpanId':['ְ�����ƴ���','��λ���ƴ���'],
							   'a0221SpanId':['ְ����','��λ���'],
							   'a0229SpanId':['�ֹܣ����£�����','��λ����'],
							   'a0243SpanId':['��ְʱ��','������ʼ']};
							   
				function changeLabel(type){
					for(var key in labelText){
						document.getElementById(key).innerHTML=labelText[key][type];
					}
				}		   
				function a0222SelChangePage(record,index){//��λ���onchangeʱ��ְ���θ�ֵΪ��
					document.getElementById("a0221").value='';
					document.getElementById("a0221_combo").value='';
					a0221achange();
					a0222SelChange(record,index)
				}	
				function a0222SelChange(record,index){
				
					var a0222 = document.getElementById("a0222").value;
					var a0201b = document.getElementById("a0201b").value;
					
				
					document.getElementById("codevalueparameter").value=a0222;
					
					if("01"==a0222){//���ӳ�Ա
						
						selecteEnable('a0201d','0');
					}else{
						
						selecteDisable('a0201d');
					}
					
					if("01"==a0222||"99"==a0222){//����Ա�����չ�����Ա��λor����
						
						selecteEnable('a0219');//ְ�����
						selecteEnable('a0251');//ְ������
						selecteWinEnable('a0247');//ѡ�����÷�ʽ
						document.getElementById('yimian').style.visibility="visible";
						
						changeSelectData({one:{key:'1',value:'����'},two:{key:'0',value:'����'}});
						changeLabel(0);
					}else if("02"==a0222||"03"==a0222){//��ҵ��λ�����λor��ҵ��λרҵ������λ
						selecteDisable('a0219');//ְ�����disabled
						selecteEnable('a0251');//ְ������
						selecteWinEnable('a0247');//ѡ�����÷�ʽ
						document.getElementById('yimian').style.visibility="visible";
						changeSelectData({one:{key:'1',value:'����'},two:{key:'0',value:'����'}});
						changeLabel(0);
						
					}else if("04"==a0222||"05"==a0222||"06"==a0222||"07"==a0222){//���ؼ������˸�λor������ͨ���˸�λor��ҵ��λ�������˸�λor��ҵ��λ��ͨ���˸�λ
						
						selecteDisable('a0219');//ְ�����
						selecteDisable('a0251');//ְ������
						selecteWinDisable('a0247');//ѡ�����÷�ʽ
						document.getElementById('yimian').style.visibility="hidden";
						changeSelectData({one:{key:'1',value:'��ְ'},two:{key:'0',value:'����ְ'}});
						changeLabel(1);
					}else{
						
						document.getElementById('yimian').style.visibility="hidden";
					}
					a0255SelChange();
					
				}
				function a0255SelChange(){
					var a0222 = document.getElementById("a0222").value;
					if("04"==a0222||"05"==a0222||"06"==a0222||"07"==a0222){//���ؼ������˸�λor������ͨ���˸�λor��ҵ��λ�������˸�λor��ҵ��λ��ͨ���˸�λ
						return;
					}
					
					var a0255 = $("input[name='a0255']:checked").val();
					if("1"==a0255){//����
						document.getElementById('yimian').style.visibility="hidden";
					}else if("0"==a0255){//����
						document.getElementById('yimian').style.visibility="visible";
					}
					document.getElementById('a0255').value = a0255;
				}
				
				function setA0216aValue(record,index){//ְ����
					Ext.getCmp('a0216a').setValue(record.data.value);
				}
				function setA0255Value(record,index){
					Ext.getCmp('a0255').setValue(record.data.key)
				}
				//a01ͳ�ƹ�ϵ���ڵ�λ
				function setParentValue(record,index){
					document.getElementById('a0195key').value = record.data.key;
					document.getElementById('a0195value').value = record.data.value;
					
					var a0195 = document.getElementById("a0195").value;
					var B0194 = radow.doEvent('a0195Change',a0195);
					
				}
				function witherTwoYear(){
				
					parent.document.getElementById('a0197').value=record.data.key;
				}
				//a01����
				function setParentA0120Value(record,index){
					parent.document.getElementById('a0120').value=record.data.key;
				}
				//a01 ���㹤������
				function setParentA0194Value(record,index){
					
				}
				
				function a0201bChange(record){
					//��ְ�ṹ��� �� ְ�����ƴ����Ӧ��ϵ
					radow.doEvent('setZB08Code',record.data.key);
					
					//�����ǰ��Ա��û�С�ͳ�ƹ�ϵ���ڵ�λ�������һ��ǵ�ǰû��ְ��,�����ͳ�ƹ�ϵ���ڵ�λ����ֵΪ��ְ���� 
					radow.doEvent("a0201bChange",record.data.key);
					
				}
				function a0251change(){//ְ������  �Ƹ����
					var a0251 = document.getElementById('a0251').value;
					var a0251bOBJ = document.getElementById('a0251b');
					var a0251bTD = document.getElementById('a0251bTD');
					if('26'==a0251){
						
					}else if('27'==a0251){
						a0251bOBJ.checked=true;
						
					}else{
						
					}
				}
				
				function setA0201eDisabled(){
					var a0201d = document.getElementById("a0201d").checked;
					document.getElementById('a0201eSpanId').innerHTML='��Ա���';
					if(!a0201d){
						document.getElementById("a0201e_combo").disabled=true;
						document.getElementById("a0201e_combo").style.backgroundColor="#EBEBE4";
						document.getElementById("a0201e_combo").style.backgroundImage="none";
						Ext.query("#a0201e_combo+img")[0].style.display="none";
						document.getElementById("a0201e").value="";
						document.getElementById("a0201e_combo").value="";
					}else{
						document.getElementById("a0201e_combo").readOnly=false;
						document.getElementById("a0201e_combo").disabled=false;
						document.getElementById("a0201e_combo").style.backgroundColor="#fff";
						document.getElementById("a0201e_combo").style.backgroundImage="url(<%=ctxPath%>/commform/basejs/ext/resources/images/default/form/text-bg.gif)";
						Ext.query("#a0201e_combo+img")[0].style.display="block";
						if(a0201d){
							document.getElementById('a0201eSpanId').innerHTML='<font color="red">*</font>��Ա���';
						}
					}
				}

				$(function(){
				    $("#a0201d").change(function() {
				    	setA0201eDisabled();
				    });
				}); 
				
				
				
				</script>
				<div id="div2" style="display:'';">
				<odin:hidden property="a0200" title="����id" ></odin:hidden>
				<odin:hidden property="a0255" title="��ְ״̬" ></odin:hidden>
				<odin:hidden property="a0225" title="��Ա������" value="0"></odin:hidden>
				<odin:hidden property="a0223" title="��ְ������" ></odin:hidden>
				<odin:hidden property="a0201c" title="�������" ></odin:hidden>
				<odin:hidden property="codevalueparameter" title="ְ���κ͸�λ��������"/>
				<odin:hidden property="ChangeValue" title="ְ�����ƴ���͵�λ��������"/>
				<odin:hidden property="a0271" value="0"/>
				<odin:hidden property="a0222" value='0'/>
				<odin:hidden property="a0195key" value=''/>
				<odin:hidden property="a0195value" value=''/>
				
				<!-- <odin:groupBox title="ְ����Ϣ��"> -->
				
				<div id="A02" style="margin-left:5%;width: 95%;border:1px solid #00F">
				<div id="btnToolBarDiv"></div>
				<odin:toolBar property="btnToolBar" applyTo="btnToolBarDiv">
								<odin:fill></odin:fill>
								<odin:buttonForToolBar text="����" id="WorkUnitsAddBtn" icon="images/add.gif"></odin:buttonForToolBar>
								<odin:buttonForToolBar text="����" id="save" isLast="true" icon="images/save.gif" cls="x-btn-text-icon" handler="saveA02" ></odin:buttonForToolBar>
				</odin:toolBar>
				
				<table style="width: 100%">
					<tr align="left">
						<td colspan="2">
							<table>
								<tr>
									<tags:PublicTextIconEdit3 onchange="setParentValue" property="a0195" label="ͳ�ƹ�ϵ���ڵ�λ" readonly="true" codetype="orgTreeJsonData" width="250"></tags:PublicTextIconEdit3>
									<td rowspan="2">
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<label for="a0197" style="font-size: 12px;" id="a0197SpanId">�Ƿ�����������ϻ��㹤������ </label>
										<input align="middle" type="checkbox" name="a0197"  id="a0197" onclick="witherTwoYear()" />
									</td>
								</tr>
							</table>
						</td>
					</tr>
					
					<tr align="left">
						<td colspan="2">
							<table>
								<tr>
									<odin:textEdit property="a0192a" width="550" label="ȫ��"  maxlength="1000"><span>&nbsp;&nbsp;(���������)</span></odin:textEdit>
									<td rowspan="2"><odin:button text="��������" property="UpdateTitleBtn" ></odin:button></td>
									<td rowspan="2"><odin:button text="����������" property="personGRIDSORT" handler="openSortWin" ></odin:button></td>
								</tr>
								<tr>
							       <odin:textEdit property="a0192" width="550" label="���"  maxlength="1000"><span>&nbsp;&nbsp;(��������)</span></odin:textEdit>
							    </tr>	
							</table>
						</td>
					</tr>
				    <tr>
					    <td>
					    	<table width="330"><tr><td>
						    	<odin:editgrid property="WorkUnitsGrid" sm="row"  forceNoScroll="true" isFirstLoadData="false" url="/" 
									 height="330" title="" pageSize="50">
										<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
								     		<odin:gridDataCol name="a0281"/>
								     		<odin:gridDataCol name="a0200" />
									  		<odin:gridDataCol name="a0201b" />
									  		<odin:gridDataCol name="a0201a" />
									  		<odin:gridDataCol name="a0215a" />
									  		<odin:gridDataCol name="a0216a" />
									  		<odin:gridDataCol name="a0222" />
									   		<odin:gridDataCol name="a0255" isLast="true"/>
										</odin:gridJsonDataModel>
										<odin:gridColumnModel>
										  <odin:gridRowNumColumn />
										  <odin:gridEditColumn2 header="���" editor="checkbox" dataIndex="a0281" checkBoxClick="a02checkBoxColClick" edited="true"/>
										  <odin:gridEditColumn2 header="id" edited="false" dataIndex="a0200" editor="text" hidden="true"/>
										  <odin:gridEditColumn2 header="��ְ��������" edited="false"  dataIndex="a0201b"  editor="text" hidden="true"/>
										  <odin:gridEditColumn2 header="��ְ����" edited="false" dataIndex="a0201a" renderer="changea0201a" editor="text"/>
										  <odin:gridEditColumn2 header="ְ�����ƴ���" edited="false"  dataIndex="a0215a" editor="select" codeType="ZB08" hidden="true"/>
										  <odin:gridEditColumn2 header="ְ������" edited="false"  dataIndex="a0216a" editor="text"/>
										  <odin:gridEditColumn2 header="��λ���" edited="false"  dataIndex="a0222" editor="text" hidden="true"/>
										  <odin:gridEditColumn2 header="��ְ״̬" edited="false" dataIndex="a0255"  codeType="ZB14" editor="select"/>
										  <odin:gridEditColumn header="����" dataIndex="delete" editor="text" edited="false" renderer="deleteRowRendererA02" isLast="true"/>
									</odin:gridColumnModel>
								</odin:editgrid>
								<label style="font-size: 13px;"><input type="checkbox" checked="checked" id="xsymzw" onclick="checkChange(this)"/>��ʾ����ְ��</label>
								<div id="btngroup"> </div>
								<div style="margin-top: 8px;" id="btngroup2"> </div>
								</td>
							</tr>
						</table>
				
				
				    	</td>
				    	<td >
				    		<table>
				    			<tr  align="left">
				    				<tags:PublicTextIconEdit3 codetype="orgTreeJsonData" onchange="a0201bChange" label="��ְ����" property="a0201b" defaultValue=""/>
				    				<!-- <odin:select2 property="a0255" label="��ְ״̬" required="true" onchange="a0255SelChange" value="1" codeType="ZB14"></odin:select2> -->
				    				
				    				<td align="right"><!-- <span id="a0195SpanId_s" style="font-size: 12px;">��ְ״̬&nbsp;</span> --></td>
								    <td align="left">
										<input align="middle" type="radio" name="a0255" id="a02551" checked="checked" value="1" class="radioItem"/>
										<label for="a0255" style="font-size: 12px;">����</label>
										<span>&nbsp;</span>
										<input align="middle" type="radio" name="a0255" id="a02550" value="0" class="radioItem"/>
										<label for="a0255" style="font-size: 12px;">����</label>
									</td>
								</tr>
								<tr>
									<odin:textEdit property="a0216a" label="ְ������" required="true" maxlength="50"></odin:textEdit>
									<!-- <odin:select2 property="a0201d" label="�Ƿ���ӳ�Ա" data="['1','��'],['0','��']" onchange="setA0201eDisabled"></odin:select2> -->
									<td></td>
									<td align="left" id="a0219TD">
										<input align="middle" type="checkbox" name="a0219" id="a0219" />
										<label id="a0219SpanId" for="a0219" style="font-size: 12px;">�쵼ְ��</label>
									</td>	
								</tr>
								
								<tr align="left">
								    <odin:select2 property="a0201e" label="��Ա���" codeType="ZB129"></odin:select2>
								    <td></td>
								    <td align="left" id="a0201dTD">
										<input align="middle" type="checkbox" name="a0201d" id="a0201d"/>
										<label id="a0201dSpanId" for="a0201d" style="font-size: 12px;">�Ƿ���ӳ�Ա</label>
									</td>
								    
								</tr>
								<tr>
									
									<tags:PublicTextIconEdit property="a0247" label="ѡ�����÷�ʽ" codetype="ZB122" readonly="true" required="true"></tags:PublicTextIconEdit>
									<td></td>
									<td align="left" id="a0251bTD">
										<input align="middle" type="checkbox" name="a0251b" id="a0251b" />
										<label id="a0251bfSpanId" for="a0251b" style="font-size: 12px;">�Ƹ����</label>
									</td>
									
								</tr>
								<tr>
									<odin:NewDateEditTag property="a0243" isCheck="true" labelSpanId="a0243SpanId" maxlength="8" label="��ְʱ��" required="true"></odin:NewDateEditTag>
									<odin:textEdit property="a0245" label="��ְ�ĺ�" validator="a0245Length"></odin:textEdit>
								</tr>
								<tr align="left">
								    <odin:hidden property="a0221a" value="0"/> 
								</tr>
								<tr align="left" >
								    <odin:hidden property="a0229" value="0"/>
								    <odin:hidden property="a0251" value="0"/>
								</tr>
								<tr>
									
								</tr>
								<tr id='yimian'>
									<odin:NewDateEditTag property="a0265" isCheck="true" label="��ְʱ��" labelSpanId="a0265SpanId"  maxlength="8"></odin:NewDateEditTag>
									<odin:textEdit property="a0267" label="��ְ�ĺ�" validator="a0267Length"></odin:textEdit>
								</tr>
								<tr>
									<!-- �¿����ְ��䶯���� tongzj 2017/5/29 -->
								</tr>
								<tr><td><br><td></tr>
								<tr><td><br><td></tr>
								<tr><td><br><td></tr>
				    		</table>
				    	</td>
				    </tr>
				    <tr>
				    	<td align="right" colspan="4"><div id="btngroup3" ></div></td>
				    </tr>
				</table>
				</div>
				<!-- </odin:groupBox> -->
				<odin:hidden property="a0281" title="�������"/>
				
				</div>
				<script type="text/javascript">
				Ext.onReady(function(){
						new Ext.Button({
							icon : 'images/icon/arrowup.gif',
							id:'UpBtn',
						    text:'����',
						    cls :'inline',
						    renderTo:"btngroup",
						    handler:UpBtn
						});
						new Ext.Button({
							icon : 'images/icon/arrowdown.gif',
							id:'DownBtn',
						    text:'����',
						    cls :'inline pl',
						    renderTo:"btngroup",
						    handler:DownBtn
						});
						new Ext.Button({
							icon : 'images/icon/save.gif',
							id:'saveSortBtn',
						    text:'��������',
						    cls :'inline pl',
						    renderTo:"btngroup",
						    handler:function(){
								radow.doEvent('worksort');
						    }
						});
						new Ext.Button({
							icon : 'images/icon/save.gif',
							id:'sortUseTimeS',
						    text:'����ְʱ������',
						    cls :'inline pl',
						    renderTo:"btngroup",
						    handler:function(){
								radow.doEvent('sortUseTime');
						    }
						});
					});
					//ͳ�ƹ�ϵ���ڵ�λ����ְ������������
					function saveA02(){
						
						var a0247 = document.getElementById('a0247').value;
						if(!a0247){
							$h.alert('ϵͳ��ʾ','ѡ�����÷�ʽ����Ϊ�գ�', null,200);
							return false;
						}
						
						//��ְʱ����֤ 
						var a0243 = document.getElementById("a0243").value;//��ְʱ��
						var a0265 = document.getElementById('a0265').value;//��ְʱ��
						var a0243_1 = document.getElementById("a0243_1").value;		//��ְʱ��ҳ����ʾֵ
						var a0265_1 = document.getElementById("a0265_1").value;		//��ְʱ��ҳ����ʾֵ 
						
						
						var now = new Date();
						var nowTime = now.toLocaleDateString();
						var year = nowTime.substring(0 , 4);//��
						var MonthIndex = nowTime.indexOf("��");
						var mon = nowTime.substring(5 , MonthIndex);//��
						var day = nowTime.substring(MonthIndex+1,nowTime.length-1);//��
						if(mon.length == 1){
							mon = "0" + mon;
						}
						if(day.length == 1){
							day = "0" + day;
						}
						
						nowTime = year + mon + day;//��ȡ��λ����ʱ���ַ���
						
						var time = a0243;
						if(time.length == 6){
							time = time + "01";
						}
						
						if(parseInt(time) > parseInt(nowTime)){
							odin.alert("��ְʱ�䲻������ϵͳ��ǰʱ��");
							return false;
						}
						
						var time2 = a0265;
						if(time2.length == 6){
							time2 = time2 + "01";
						}
						if(parseInt(time2) > parseInt(nowTime)){
							odin.alert("��ְʱ�䲻������ϵͳ��ǰʱ��");
							return false;
						}
						
						if(!a0243_1){
							$h.alert('ϵͳ��ʾ','��ְʱ�䲻��Ϊ�գ�', null,200);
							return false;
						}
						
						var a0255 = $("input[name='a0255']:checked").val();
						/* if("0"==a0255 && !a0265_1){
							$h.alert('ϵͳ��ʾ','��ְʱ�䲻��Ϊ�գ�', null,200);
							return false;
						} */
						
						
						var text1 = dateValidate(a0243_1);
						var text2 = dateValidate(a0265_1);
						
						if(a0243_1.indexOf(".") > 0){
							text1 = dateValidate(a0243);
						}
						if(a0265_1.indexOf(".") > 0){
							text2 = dateValidate(a0265);
						}
						
						
						if(text1!==true){
							$h.alert('ϵͳ��ʾ','��ְʱ��' + text1, null,400);
							return false;
						}
						if(text2!==true){
							$h.alert('ϵͳ��ʾ','��ְʱ��' + text2, null,400);
							return false;
						}
						
						
						/* if(!a0243){
							$h.alert('ϵͳ��ʾ','��ְʱ�䲻��Ϊ�գ�', null,200);
							return false;
						} */
						
						//ְ�����ƣ�������д�Ҳ������пո�������Ǻ����ַ�
						var a0216a = document.getElementById("a0216a").value;
						if(!a0216a){
							odin.alert("ְ�����Ʋ���Ϊ�գ�");
							return;
						}
						if (a0216a.indexOf(" ") >=0){
							odin.alert("ְ�����Ʋ��ܰ����ո�");
							return;
						}
					    if(!(/^[\u3220-\uFA29]+$/.test(a0216a))){
					    	odin.alert("ְ�����Ʋ��ܰ����Ǻ����ַ���");
							return;
					    }
						
						var a0201b = document.getElementById('a0201b').value;
						var a0195 = document.getElementById('a0195').value;
						if(a0201b==""){
							Ext.Msg.alert("ϵͳ��ʾ","���ȵ��ͼ�������ְ������ѡ��");
							return;
						}
						if(a0195 != null && a0195!=a0201b){
							Ext.MessageBox.confirm(
								'��ʾ',
								'ͳ�ƹ�ϵ���ڵ�λ����ְ��������ͬ���Ƿ�������֣�',
								function (btn){
									if(btn=='yes'){
										radow.doEvent('saveWorkUnits.onclick');
									}
								}
							);
						}else{
							radow.doEvent('saveWorkUnits.onclick');
						}
					}
				</script>
				<script>
				
				function changea0201a(value, params, record,rowIndex,colIndex,ds){
					if(record.data.a0201b=='-1'){
						return '<a title="'+value+'(������)">'+value+'(������)</a>';
					}else{
						return '<a title="'+value+'">'+value+'</a>';
					}	
				}
				function seta0255Value(value, params, record,rowIndex,colIndex,ds){
					var a0222 = record.data.a0222;
					var textValue = '';
					if("01"==a0222||"99"==a0222||"02"==a0222||"03"==a0222){
					   	textValue = getTextValue({one:{key:'1',value:'����'},two:{key:'0',value:'����'}},value);
					}else if("04"==a0222||"05"==a0222||"06"== a0222 || "07"==a0222){//���ؼ������˸�λor������ͨ���˸�λor��ҵ��λ�������˸�λor��ҵ��λ��ͨ���˸�λ
						textValue = getTextValue({one:{key:'1',value:'��ְ'},two:{key:'0',value:'����ְ'}},value);
					}	
					return '<a title="'+textValue+'">'+textValue+'</a>';
				}
				function getTextValue(item,v){
					if(item.one.key==v){
						return item.one.value;
					}else{
						return item.two.value;
					}
				}
				function checkChange(){
					var checkbox = document.getElementById("xsymzw");
					var grid = Ext.getCmp("WorkUnitsGrid");
					var store = grid.getStore();
					var vibility;
					if(checkbox.checked){
						vibility = "block";
					}else{
						vibility = "none";
					}
					
					var len = store.data.length;
					for(var i=0;i<len;i++){
						var data = store.getAt(i).data;
						var a0255 = data.a0255;//��ְ״̬
						if(a0255=='0'){
							grid.getView().getRow(i).style.display=vibility;
						}
					}
				}
				
				function UpBtn(){	
					var grid = odin.ext.getCmp('WorkUnitsGrid');
					
					var sm = grid.getSelectionModel().getSelections();
					var store = grid.store;
					
					
					if (sm.length<=0){
						alert('��ѡ����Ҫ�����ְ��!')
						return;	
					}
					
					var selectdata = sm[0];  //ѡ�����еĵ�һ��
					var index = store.indexOf(selectdata);
					if (index==0){
						alert('��ְ���Ѿ��������!')
						return;
					}
					
					store.remove(selectdata);  //�Ƴ�
					store.insert(index-1, selectdata);  //���뵽��һ��ǰ��
					
					grid.getSelectionModel().selectRow(index-1,true);  //ѡ�����ƶ������	
					
					grid.getView().refresh();
				}
				
				
				function DownBtn(){	
					var grid = odin.ext.getCmp('WorkUnitsGrid');
					
					var sm = grid.getSelectionModel().getSelections();
					var store = grid.store;
					if (sm.length<=0){
						alert('��ѡ����Ҫ�����ְ��!')
						return;	
					}
					
					var selectdata = sm[0];  //ѡ�����еĵ�һ��
					var index = store.indexOf(selectdata);
					var total = store.getCount();
					if (index==(total-1) ){
						alert('��ְ���Ѿ����������!')
						return;
					}
					
					store.remove(selectdata);  //�Ƴ�
					store.insert(index+1, selectdata);  //���뵽��һ��ǰ��
					
					grid.getSelectionModel().selectRow(index+1,true);  //ѡ�����ƶ������	
					grid.view.refresh();
				}
				
				Ext.onReady(function(){
					
					var pgrid = Ext.getCmp("WorkUnitsGrid");
					
					
					var bbar = pgrid.getBottomToolbar();
					
					
					var dstore = pgrid.getStore();
					var firstload = true;
					dstore.on({  
				       load:{  
				           fn:function(){  
				             checkChange();
				             if(firstload){
				       		    $h.selectGridRow('WorkUnitsGrid',0);
				       		    firstload = false;
				             }
				           }      
				       },  
				       scope:this      
				   });  
				   
				   var ddrow = new Ext.dd.DropTarget(pgrid.container,{
						ddGroup : 'GridDD',
						copy : false,
						notifyDrop : function(dd,e,data){
							//ѡ���˶�����
							var rows = data.selections;
							//�϶����ڼ���
							var index = dd.getDragData(e).rowIndex;
							if (typeof(index) == "undefined"){
								return;
							}
							//�޸�store
							for ( i=0; i<rows.length; i++){
								var rowData = rows[i];
								if (!this.copy) dstore.remove(rowData);
								dstore.insert(index, rowData);
							}
							pgrid.view.refresh();
							radow.doEvent('worksort');
						}
					});
				
				
				});
				
				function openSortWin(){
					var a0201b = document.getElementById("a0201b").value;
					if(a0201b==''){
						$h.alert('ϵͳ��ʾ��','����ѡ�����!');
						return;
					}
					parent.window.a0201b = a0201b;
					$h.openWin('A01SortGrid','pages.publicServantManage.PersonSort','����������',500,480,document.getElementById('subWinIdBussessId').value,'<%=ctxPath%>',window);
				}
				
				Ext.onReady(function(){
					
					//Ext.getCmp('WorkUnitsGrid').setWidth(581);
					Ext.getCmp('WorkUnitsGrid').setHeight(250)
					Ext.getCmp('WorkUnitsGrid').setWidth(document.body.clientWidth*0.48); 
					Ext.getCmp('a0192a').setWidth(document.body.clientWidth*0.48-32); 
					Ext.getCmp('a0192').setWidth(document.body.clientWidth*0.48-32); 
					
					document.getElementById('a0201bSpanId').innerHTML='<font color="red">*</font>��ְ����';		
					
					
					//�Ƿ����ε����� 
					a0255SelChange();
					$(".radioItem").change(function() {
						var a0255 = $("input[name='a0255']:checked").val();
						if("1"==a0255){//����
							document.getElementById('yimian').style.visibility="hidden";
						}else if("0"==a0255){//����
							document.getElementById('yimian').style.visibility="visible";
						}
						document.getElementById('a0255').value = a0255;
					})
					
					//������ְ����ѡ��򳤶�
					document.getElementById('a0201b_combo').style.width="190px";
				});
				</script>
				
				
				
<!--------------------------------------- ��ְ�� A051--------------------------------------------->

				<script type="text/javascript">
				function deleteRowRendererA051(value, params, record,rowIndex,colIndex,ds){
					var a0500 = record.data.a0500;
					if(parent.buttonDisabled){
						return "ɾ��";
					}
					return "<a href=\"javascript:deleteRowA051(&quot;"+a0500+"&quot;)\">ɾ��</a>";
				}
				function deleteRowA051(a0500){ 
					Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ�ȷ��ɾ����",function(id) { 
						if("yes"==id){
							radow.doEvent('deleteRowA051',a0500);
						}else{
							return;
						}		
					});	
				}
				</script>
				<div id="div4" style="display:'';">
				<div id="A051" style="margin-left:5%;width: 95%;border:1px solid #00F">
					<odin:toolBar property="toolBarA051" applyTo="tolA051" >
								<odin:fill></odin:fill>
								<odin:buttonForToolBar text="����"  id="TrainingInfoAddBtn" icon="images/add.gif"></odin:buttonForToolBar>
								<odin:buttonForToolBar text="����" id="saveA051" handler="saveTrain" icon="images/save.gif" isLast="true" cls="x-btn-text-icon" ></odin:buttonForToolBar>
					</odin:toolBar>
					<div>
					<odin:hidden property="a0500" title="����id" ></odin:hidden>
					<div>
					<div id="tolA051"></div>
					</div>
					<table>
						<tr>
							<td>
								<odin:grid property="TrainingInfoGrid" isFirstLoadData="false" forceNoScroll="true" url="/"   
								 height="210">
									<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
							     		<odin:gridDataCol name="a0500" />
								  		<odin:gridDataCol name="a0501b" />
								  		<odin:gridDataCol name="a0504"/>
								  		<odin:gridDataCol name="a0517" />
								   		<odin:gridDataCol name="a0524" isLast="true"/>
									</odin:gridJsonDataModel>
									<odin:gridColumnModel>
									  <odin:gridRowNumColumn />
									  <odin:gridEditColumn2 header="����" dataIndex="a0500" editor="text" edited="false" width="100" hidden="true"/>
									  <odin:gridEditColumn header="ְ��" dataIndex="a0501b" editor="select" edited="false" codeType="ZB148" width="100"/>
									  <odin:gridEditColumn header="��׼����" dataIndex="a0504" editor="text" edited="false" width="100"/>
									  <odin:gridEditColumn header="��ֹ����" dataIndex="a0517" editor="text" width="100" edited="false"/>
									  <odin:gridEditColumn2 header="״̬" dataIndex="a0524" editor="select" edited="false" selectData="['1','����'],['0','����']" width="100"/>
									  <odin:gridEditColumn width="45" header="����" dataIndex="delete" editor="text" edited="false" renderer="deleteRowRendererA051" isLast="true"/>
									</odin:gridColumnModel>
								</odin:grid>		
							</td>
							<td>
								<table>
									<tr><odin:select2 property="a0501b" label="ְ��" codeType="ZB148" required="true"></odin:select2></tr>
									<tr><td><span>&nbsp;</span></td></tr>
									<tr><odin:select2 property="a0524" label="״̬" codeType="ZB14" onchange="setA0517Disabled" required="true"></odin:select2></tr>
									<tr><td><span>&nbsp;</span></td></tr>
									<tr><odin:NewDateEditTag property="a0504" isCheck="true" label="��׼����" maxlength="8" required="true"></odin:NewDateEditTag></tr>
									<tr><td><span>&nbsp;</span></td></tr>
									<tr><odin:NewDateEditTag property="a0517" isCheck="true" label="��ֹ����" maxlength="8"></odin:NewDateEditTag>	</tr>
								</table>
							</td>
						</tr>
					</table>
					</div>
					</div>
				</div>
				<script type="text/javascript">
				Ext.onReady(function(){
					var firstload = true;
					var pgrid = Ext.getCmp("TrainingInfoGrid");
					var dstore = pgrid.getStore();
					dstore.on({  
				       load:{  
				           fn:function(){  
				           	 if(firstload){
				           		  $h.selectGridRow('TrainingInfoGrid',0);
				           		  firstload = false;
				             }
				           }      
				       },  
				       scope:this      
				   });  
				});
				Ext.onReady(function(){
					$h.applyFontConfig($h.spFeildAll.a08);
					if(parent.buttonDisabled){
						$h.setDisabled($h.disabledButtons.a08);
						
						var cover_wrap1 = document.getElementById('cover_wrap1');
						var ext_gridobj = Ext.getCmp('familyid');
						var gridobj = document.getElementById('forView_TrainingInfoGrid');
						var viewSize = Ext.getBody().getViewSize();
						var grid_pos = $h.pos(gridobj);
						cover_wrap1.className=  "divcover_wrap";
						cover_wrap1.style.cssText=  "height:" + $h.pos(gridobj).top + "px;";
					}
					$h.fieldsDisabled(parent.fieldsDisabled);
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
				Ext.onReady(function(){
					var side_resize=function(){
						 Ext.getCmp('TrainingInfoGrid').setHeight(400);
						 Ext.getCmp('TrainingInfoGrid').setWidth(document.body.clientWidth*0.64); 
					}
					side_resize();  
					window.onresize=side_resize; 
				})
				
				
		function saveTrain(){
			var a0524 = document.getElementById("a0524").value;//״̬
			var a0504 = document.getElementById("a0504").value;//��׼����
			var a0517 = document.getElementById('a0517').value;//��������
			var a0504_1 = document.getElementById("a0504_1").value;		//��׼����ҳ����ʾֵ
			var a0517_1 = document.getElementById("a0517_1").value;		//��������ҳ����ʾֵ 
			
			//��׼���ڡ�״̬���� ��ְ���κ�̨��У�飩
			if(!a0524){
				$h.alert('ϵͳ��ʾ','״̬����Ϊ�գ�', null,200);
				return false;
			}
			
			var now = new Date();
			var nowTime = now.toLocaleDateString();
			var year = nowTime.substring(0 , 4);//��
			var MonthIndex = nowTime.indexOf("��");
			var mon = nowTime.substring(5 , MonthIndex);//��
			var day = nowTime.substring(MonthIndex+1,nowTime.length-1);//��
			if(mon.length == 1){
				mon = "0" + mon;
			}
			if(day.length == 1){
				day = "0" + day;
			}
			
			nowTime = year + mon + day;//��ȡ��λ����ʱ���ַ���
			
			var time = a0504;
			if(time.length == 6){
				time = time + "01";
			}
			
			if(parseInt(time) > parseInt(nowTime)){
				odin.alert("��׼���ڲ�������ϵͳ��ǰʱ��");
				return false;
			}
			
			var time2 = a0517;
			if(time2.length == 6){
				time2 = time2 + "01";
			}
			if(parseInt(time2) > parseInt(nowTime)){
				odin.alert("��ֹ���ڲ�������ϵͳ��ǰʱ��");
				return false;
			}
			
			if(!a0504_1){
				$h.alert('ϵͳ��ʾ','��׼���ڲ���Ϊ�գ�', null,200);
				return false;
			}
			
			
			if("0"==a0524 && !a0517_1){
				$h.alert('ϵͳ��ʾ','��ֹ���ڲ���Ϊ�գ�', null,200);
				return false;
			}
			
			
			var text1 = dateValidate(a0504_1);
			var text2 = dateValidate(a0517_1);
			
			if(a0504_1.indexOf(".") > 0){
				text1 = dateValidate(a0504);
			}
			if(a0517_1.indexOf(".") > 0){
				text2 = dateValidate(a0517);
			}
			
			
			if(text1!==true){
				$h.alert('ϵͳ��ʾ','��׼����' + text1, null,400);
				return false;
			}
			if(text2!==true){
				$h.alert('ϵͳ��ʾ','��ֹ����' + text2, null,400);
				return false;
			}
			
			radow.doEvent('saveA11.onclick');
		}
			
		
		function setA0517Disabled(){
			var value = document.getElementById("a0524").value;
			if("0"==value || value==''){
				document.getElementById("a0517_1").readOnly=false;
				document.getElementById("a0517_1").disabled=false;
				document.getElementById("a0517_1").style.backgroundColor="#fff";
				document.getElementById("a0517_1").style.backgroundImage="url(<%=ctxPath%>/commform/basejs/ext/resources/images/default/form/text-bg.gif)";
				
			}else if("1"==value){
				document.getElementById("a0517_1").readOnly=true;
				document.getElementById("a0517_1").disabled=true;
				document.getElementById("a0517_1").style.backgroundColor="#EBEBE4";
				document.getElementById("a0517_1").style.backgroundImage="none";
				document.getElementById("a0517").value="";
				document.getElementById("a0517_1").value="";
				
			}
		}
				

		</script>		
		
		
<!--------------------------------------- ��ְ�� A050--------------------------------------------->

				<script type="text/javascript">
				function deleteRowRendererA050(value, params, record,rowIndex,colIndex,ds){
					var a0500 = record.data.a0500;
					if(parent.buttonDisabled){
						return "ɾ��";
					}
					return "<a href=\"javascript:deleteRowA050(&quot;"+a0500+"&quot;)\">ɾ��</a>";
				}
				function deleteRowA050(a0500){ 
					Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ�ȷ��ɾ����",function(id) { 
						if("yes"==id){
							radow.doEvent('deleteRowA050',a0500);
						}else{
							return;
						}		
					});	
				}
				</script>
				<div id="div3" style="display:'';">
				
				
				<div id="A050" style="margin-left:5%;width: 95%;border:1px solid #00F">
					<odin:toolBar property="toolBarA050" applyTo="tolA050" >
								<odin:fill></odin:fill>
								<odin:buttonForToolBar text="����"  id="rankAddBtn" icon="images/add.gif"></odin:buttonForToolBar>
								<odin:buttonForToolBar text="����" id="saveA050" handler="saveTrainA050"  icon="images/save.gif" isLast="true" cls="x-btn-text-icon" ></odin:buttonForToolBar>
					</odin:toolBar>
					<div>
					<odin:hidden property="a0500Post" title="����id" ></odin:hidden>
					<div>
					<div id="tolA050"></div>
					</div>
					<table>
						<tr>
							<td>
								<odin:grid property="rankGrid" isFirstLoadData="false" forceNoScroll="true" url="/"   
								 height="210">
									<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
							     		<odin:gridDataCol name="a0500" />
								  		<odin:gridDataCol name="a0501b" />
								  		<odin:gridDataCol name="a0504"/>
								  		<odin:gridDataCol name="a0517" />
								   		<odin:gridDataCol name="a0524" isLast="true"/>
									</odin:gridJsonDataModel>
									<odin:gridColumnModel>
									  <odin:gridRowNumColumn />
									  <odin:gridEditColumn2 header="����" dataIndex="a0500" editor="text" edited="false" width="100" hidden="true"/>
									  <odin:gridEditColumn header="ְ����" align="center" dataIndex="a0501b" editor="select" edited="false" codeType="ZB09" width="100"/>
									  <odin:gridEditColumn header="��׼����" align="center" dataIndex="a0504" editor="text" edited="false" width="100"/>
									  <odin:gridEditColumn header="��ֹ����" align="center" dataIndex="a0517" editor="text" width="100" edited="false"/>
									  <odin:gridEditColumn2 header="״̬" align="center" dataIndex="a0524" editor="select" edited="false" selectData="['1','����'],['0','����']" width="100"/>
									  <odin:gridEditColumn width="45" header="����" dataIndex="delete" editor="text" edited="false" renderer="deleteRowRendererA050" isLast="true"/>
									</odin:gridColumnModel>
								</odin:grid>		
							</td>
							<td>
								<table>
									<tr><tags:PublicTextIconEdit property="a0501bPost" codetype="ZB09" width="160" label="ְ����" required="true"/></tr>
									<tr><td><span>&nbsp;</span></td></tr>
									<tr><odin:select2 property="a0524Post" label="״̬" codeType="ZB14" onchange="setA0517DisabledA050" required="true"></odin:select2></tr>
									<tr><td><span>&nbsp;</span></td></tr>
									<tr><odin:NewDateEditTag property="a0504Post" isCheck="true" label="��׼����" maxlength="8" required="true"></odin:NewDateEditTag></tr>
									<tr><td><span>&nbsp;</span></td></tr>
									<tr><odin:NewDateEditTag property="a0517Post" isCheck="true" label="��ֹ����" maxlength="8"></odin:NewDateEditTag>	</tr>
								</table>
							</td>
						</tr>
					</table>
					</div>
					</div>
				</div>
				<script type="text/javascript">
				Ext.onReady(function(){
					var firstload = true;
					var pgrid = Ext.getCmp("rankGrid");
					var dstore = pgrid.getStore();
					dstore.on({  
				       load:{  
				           fn:function(){  
				           	 if(firstload){
				           		  $h.selectGridRow('rankGrid',0);
				           		  firstload = false;
				             }
				           }      
				       },  
				       scope:this      
				   });  
				});
				Ext.onReady(function(){
					$h.applyFontConfig($h.spFeildAll.a08);
					if(parent.buttonDisabled){
						$h.setDisabled($h.disabledButtons.a08);
						
						var cover_wrap1 = document.getElementById('cover_wrap1');
						var ext_gridobj = Ext.getCmp('rankGrid');
						var gridobj = document.getElementById('forView_rankGrid');
						var viewSize = Ext.getBody().getViewSize();
						var grid_pos = $h.pos(gridobj);
						cover_wrap1.className=  "divcover_wrap";
						cover_wrap1.style.cssText=  "height:" + $h.pos(gridobj).top + "px;";
					}
					$h.fieldsDisabled(parent.fieldsDisabled);
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
				Ext.onReady(function(){
					var side_resize=function(){
						 Ext.getCmp('rankGrid').setHeight(400);
						 Ext.getCmp('rankGrid').setWidth(document.body.clientWidth*0.64); 
					}
					side_resize();  
					window.onresize=side_resize; 
				})
	
				
	function setA0517DisabledA050(){
		var value = document.getElementById("a0524Post").value;
		if("0"==value || value==''){
			document.getElementById("a0517Post_1").readOnly=false;
			document.getElementById("a0517Post_1").disabled=false;
			document.getElementById("a0517Post_1").style.backgroundColor="#fff";
			document.getElementById("a0517Post_1").style.backgroundImage="url(<%=ctxPath%>/commform/basejs/ext/resources/images/default/form/text-bg.gif)";
			
		}else if("1"==value){
			document.getElementById("a0517Post_1").readOnly=true;
			document.getElementById("a0517Post_1").disabled=true;
			document.getElementById("a0517Post_1").style.backgroundColor="#EBEBE4";
			document.getElementById("a0517Post_1").style.backgroundImage="none";
			document.getElementById("a0517Post").value="";
			document.getElementById("a0517Post_1").value="";
		}
	}
	
	function saveTrainA050(){
		
		var a0524 = document.getElementById("a0524Post").value;//״̬
		var a0504 = document.getElementById("a0504Post").value;//��׼����
		var a0517 = document.getElementById('a0517Post').value;//��������
		var a0504_1 = document.getElementById("a0504Post_1").value;		//��׼����ҳ����ʾֵ
		var a0517_1 = document.getElementById("a0517Post_1").value;		//��������ҳ����ʾֵ 
		
		
		//��׼���ڡ�״̬���� ��ְ���κ�̨��У�飩
		if(!a0524){
			$h.alert('ϵͳ��ʾ','״̬����Ϊ�գ�', null,200);
			return false;
		}
		
		var now = new Date();
		var nowTime = now.toLocaleDateString();
		var year = nowTime.substring(0 , 4);//��
		var MonthIndex = nowTime.indexOf("��");
		var mon = nowTime.substring(5 , MonthIndex);//��
		var day = nowTime.substring(MonthIndex+1,nowTime.length-1);//��
		if(mon.length == 1){
			mon = "0" + mon;
		}
		if(day.length == 1){
			day = "0" + day;
		}
		
		nowTime = year + mon + day;//��ȡ��λ����ʱ���ַ���
		
		var time = a0504;
		if(time.length == 6){
			time = time + "01";
		}
		if(parseInt(time) > parseInt(nowTime)){
			odin.alert("��׼���ڲ�������ϵͳ��ǰʱ��");
			return false;
		}
		
		var time2 = a0517;
		if(time2.length == 6){
			time2 = time2 + "01";
		}
		if(parseInt(time2) > parseInt(nowTime)){
			odin.alert("��ֹ���ڲ�������ϵͳ��ǰʱ��");
			return false;
		}
		
		if(!a0504_1){
			$h.alert('ϵͳ��ʾ','��׼���ڲ���Ϊ�գ�', null,200);
			return false;
		}
		
		
		if("0"==a0524 && !a0517_1){
			$h.alert('ϵͳ��ʾ','��ֹ���ڲ���Ϊ�գ�', null,200);
			return false;
		}
		
		
		var text1 = dateValidate(a0504_1);
		var text2 = dateValidate(a0517_1);
		
		if(a0504_1.indexOf(".") > 0){
			text1 = dateValidate(a0504);
		}
		if(a0517_1.indexOf(".") > 0){
			text2 = dateValidate(a0517);
		}
		
		
		if(text1!==true){
			$h.alert('ϵͳ��ʾ','��׼����' + text1, null,400);
			return false;
		}
		if(text2!==true){
			$h.alert('ϵͳ��ʾ','��ֹ����' + text2, null,400);
			return false;
		}
		
		radow.doEvent('saveA12.onclick');
	}
		
	</script>		
						

<!--------------------------------------- רҵ����ְ����Ϣ�� --------------------------------------------->
				
				<style>
				<%=FontConfigPageModel.getFontConfig()%>
				#table{position:relative;top: -12px;left:5px;}
				#table2{position:relative;top: -20px; padding: 0px;margin: 0px;height:300}
				#tableA14{position:relative;top: -50px; padding: 0px;margin: 0px;height:300}
				.inline{
				display: inline;
				}
				.pl{
				margin-left: 8px;
				}
				</style>
				<script type="text/javascript">
				function setA0602Value(record,index){
					Ext.getCmp('a0602').setValue(record.data.value);
					Ext.getCmp('a0196').setValue(record.data.value);
				}
				function deleteRowRendererA06(value, params, record,rowIndex,colIndex,ds){
					var a0600 = record.data.a0600;
					if(parent.buttonDisabled){
						return "ɾ��";
					}
					return "<a href=\"javascript:deleteRowA06(&quot;"+a0600+"&quot;)\">ɾ��</a>";
				}
				function deleteRowA06(a0600){ 
					Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ�ȷ��ɾ����",function(id) { 
						if("yes"==id){
							radow.doEvent('deleteRowA06',a0600);
						}else{
							return;
						}		
					});	
				}
				
				</script>
				<div id="div5" style="display:'';">
				
				<div id="A06" style="margin-left:5%;width: 95%;border:1px solid #00F">
				<odin:toolBar property="toolBar1" applyTo="tol1">
								<odin:fill></odin:fill>
								<odin:buttonForToolBar text="����" id="professSkillAddBtn" icon="images/add.gif" ></odin:buttonForToolBar>
								<odin:buttonForToolBar text="����" id="saveA06" icon="images/save.gif" cls="x-btn-text-icon" isLast="true"></odin:buttonForToolBar>
				</odin:toolBar>
				<div id="main" style="border: 1px solid #99bbe8; padding: 0px;margin: 0px;" >
				<div id="tol1"></div>
				<odin:hidden property="sortid" title="�����"/>
				<table id="table" style="margin-top: 18px;">
					<tr>
						<td>
							<!-- <div id="divA06" style="width:330;margin-top: 20px;"> -->
							 <odin:editgrid property="professSkillgrid"  isFirstLoadData="false" forceNoScroll="true" url="/">
								<odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
								    <odin:gridDataCol name="a0699" />
									<odin:gridDataCol name="a0600" />
									<odin:gridDataCol name="a0601" />
									<odin:gridDataCol name="a0602" />
									<odin:gridDataCol name="a0604" />
									<odin:gridDataCol name="a0607" />
									<odin:gridDataCol name="a0611" isLast="true" />
								</odin:gridJsonDataModel>
								<odin:gridColumnModel>
									<odin:gridRowNumColumn></odin:gridRowNumColumn>
									<odin:gridEditColumn header="���" width="15" editor="checkbox" checkBoxClick="a06checkBoxColClick" dataIndex="a0699" edited="true"/>
									<odin:gridColumn header="id" dataIndex="a0600" editor="text" hidden="true"/>
									<odin:gridEditColumn2 header="רҵ�����ʸ����" dataIndex="a0601" codeType="GB8561" edited="false" editor="select" hidden="true"/>
									<odin:gridColumn header="רҵ�����ʸ�" dataIndex="a0602" editor="text" />
									<odin:gridColumn header="����ʸ�����" dataIndex="a0604" editor="text" />
									<odin:gridEditColumn2 header="��ȡ�ʸ�;��" dataIndex="a0607" codeType="ZB24" edited="false" editor="select" hidden="true"/>
									<odin:gridColumn header="��ί���������" dataIndex="a0611" editor="text"  hidden="true"/>		
									 <odin:gridEditColumn header="����" width="15" dataIndex="delete" editor="text" edited="false" renderer="deleteRowRendererA06" isLast="true"/>		
								</odin:gridColumnModel>
							 </odin:editgrid>
							<!-- </div> -->
							<div id="btngroupA06"> </div>
							<div style="margin-top: 8px;" id="btngroup6"> </div>
						</td>
						<td>
						  <div>
							<table id="table2">
								<tr>
									<odin:textEdit property="a0196" label="רҵ����ְ��" readonly="true"></odin:textEdit>
								</tr>
								<tr>
									<td height="50px"></td>
								</tr>
								<tr>
									<tags:PublicTextIconEdit property="a0601" label="רҵ�����ʸ�" onchange="setA0602Value" required="true" readonly="true" codetype="GB8561"></tags:PublicTextIconEdit>	
								</tr>
								<tr>
									<odin:textEdit property="a0602" label="רҵ�����ʸ�����" validator="a0602Length"></odin:textEdit>	
								</tr>
								<tr>
									<odin:NewDateEditTag property="a0604" label="����ʸ�����" maxlength="8" ></odin:NewDateEditTag>	
								</tr>
								<tr>
									<odin:select2 property="a0607" label="��ȡ�ʸ�;��" codeType="ZB24"></odin:select2>		
								</tr>
								<tr>
									<odin:textEdit property="a0611" label="��ί���������" validator="a0611Length"></odin:textEdit>
									<odin:hidden property="a0600" title="����id" ></odin:hidden>		
								</tr>
							</table>
						  </div>
						</td>
					</tr>
					</table>
				<odin:hidden property="a0699" title="���"/>
				</div>
				</div>
				
				</div>
				<script type="text/javascript">
				
				Ext.onReady(function(){
					var firstload = true;
					var pgrid = Ext.getCmp("professSkillgrid");
					var dstore = pgrid.getStore();
					dstore.on({  
				       load:{  
				           fn:function(){  
				       		 if(firstload){
				       		    $h.selectGridRow('professSkillgrid',0);
				       		    firstload = false;
				             }
				           }      
				       },  
				       scope:this      
				   });  
				
				});
				
				</script>
				<script type="text/javascript">
				Ext.onReady(function(){
					 $h.applyFontConfig($h.spFeildAll.a06);
					if(parent.buttonDisabled){
						$h.setDisabled($h.disabledButtons.a06);
						
						var cover_wrap1 = document.getElementById('cover_wrap1');
						var ext_gridobj = Ext.getCmp('professSkillgrid');
						var gridobj = document.getElementById('forView_professSkillgrid');
						var viewSize = Ext.getBody().getViewSize();
						var grid_pos = $h.pos(gridobj);
						cover_wrap1.className=  "divcover_wrap";
						cover_wrap1.style.cssText=  "height:" + $h.pos(gridobj).top + "px;";
					}
					$h.fieldsDisabled(parent.fieldsDisabled); 
				
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
				Ext.onReady(function(){
					new Ext.Button({
						icon : 'images/icon/arrowup.gif',
						id:'UpBtnA06',
					    text:'����',
					    cls :'inline',
					    renderTo:"btngroupA06",
					    handler:UpBtnA06
					});
					new Ext.Button({
						icon : 'images/icon/arrowdown.gif',
						id:'DownBtnA06',
					    text:'����',
					    cls :'inline pl',
					    renderTo:"btngroupA06",
					    handler:DownBtnA06
					});
					new Ext.Button({
						icon : 'images/icon/save.gif',
						id:'saveSortBtnA06',
					    text:'��������',
					    cls :'inline pl',
					    renderTo:"btngroupA06",
					    handler:function(){
							radow.doEvent('worksortA06');
					    }
					});
					
					Ext.getCmp('professSkillgrid').setHeight(375);
				});
				
				function UpBtnA06(){	
					var grid = odin.ext.getCmp('professSkillgrid');
					var sm = grid.getSelectionModel().getSelections();
					var store = grid.store;
					
					if (sm.length<=0){
						alert('��ѡ����Ҫ�����רҵ�����ʸ�!')
						return;	
					}
					
					var selectdata = sm[0];  //ѡ�����еĵ�һ��
					var index = store.indexOf(selectdata);
					if (index==0){
						alert('��רҵ�����ʸ��Ѿ��������!')
						return;
					}
					
					store.remove(selectdata);  //�Ƴ�
					store.insert(index-1, selectdata);  //���뵽��һ��ǰ��
					
					grid.getSelectionModel().selectRow(index-1,true);  //ѡ�����ƶ������	
					
					grid.getView().refresh();
				}
				
				
				function DownBtnA06(){	
					var grid = odin.ext.getCmp('professSkillgrid');
					
					var sm = grid.getSelectionModel().getSelections();
					var store = grid.store;
					if (sm.length<=0){
						alert('��ѡ����Ҫ�����רҵ�����ʸ�!')
						return;	
					}
					
					var selectdata = sm[0];  //ѡ�����еĵ�һ��
					var index = store.indexOf(selectdata);
					var total = store.getCount();
					if (index==(total-1) ){
						alert('��רҵ�����ʸ��Ѿ����������!')
						return;
					}
					
					store.remove(selectdata);  //�Ƴ�
					store.insert(index+1, selectdata);  //���뵽��һ��ǰ��
					
					grid.getSelectionModel().selectRow(index+1,true);  //ѡ�����ƶ������	
					grid.view.refresh();
				}
				
				function a06checkBoxColClick(rowIndex,colIndex,dataIndex,gridName){
					if(parent.buttonDisabled){
						return;
					}
					var sr = getGridSelected(gridName);
					if(!sr){
						return;
					}
					
					radow.doEvent('updateA06',sr.data.a0600);
				}
				Ext.onReady(function(){
					
					Ext.getCmp('professSkillgrid').setWidth(500);
					Ext.getCmp('professSkillgrid').setWidth(document.body.clientWidth*0.64); 
					//Ext.getCmp('professSkillgrid').setHeight(250)
					
				});
				</script>	

<!--------------------------------------- ѧ��ѧλ��Ϣ�� --------------------------------------------->

				
				<script type="text/javascript">
				
				function setA0801aValue(record,index){//ѧλ
					Ext.getCmp('a0801a').setValue(record.data.value);
				}
				function setA0901aValue(record,index){//ѧ��
					Ext.getCmp('a0901a').setValue(record.data.value);
				}
				function setA0824Value(record,index){//רҵ
					Ext.getCmp('a0824').setValue(record.data.value);
				}
				function onkeydownfn(id){
					if(id=='a0801b')
						Ext.getCmp('a0801a').setValue('');
					if(id=='a0901b')
						Ext.getCmp('a0901a').setValue('');
					if(id=='a0827')
						Ext.getCmp('a0824').setValue('');
				}
				odin.accCheckedForE3 = function(obj,rowIndex,colIndex,colName,gridId){
				        if(obj.getAttribute('alowCheck')=="false"){
				            return;
				        }
						if(!checkBoxColClick(rowIndex,colIndex,null,gridId)){
							return;
						}
				        if(obj.className=='x-grid3-check-col'){
							if (typeof(gridId)=='undefined'||(typeof(gridId)=='string'&&gridId == '')) {
								odin.checkboxds.getAt(rowIndex).set(colName, true);
							}else{
								odin.ext.getCmp(gridId).store.getAt(rowIndex).set(colName, true);
							}
							obj.className = 'x-grid3-check-col-on';
				        }else{
							if (typeof(gridId)=='undefined'||(typeof(gridId)=='string'&&gridId == '')) {
								odin.checkboxds.getAt(rowIndex).set(colName, false);
							}else{
								odin.ext.getCmp(gridId).store.getAt(rowIndex).set(colName, false);
								if(document.getElementById("selectall_"+gridId+"_"+colName)!=null){
									document.getElementById("selectall_"+gridId+"_"+colName).value='false';
									document.getElementById("selectall_"+gridId+"_"+colName).className='x-grid3-check-col';
								}	
							}
							obj.className = 'x-grid3-check-col';
				        }
				};
				//ѧ��ѧλ�������
				function checkBoxColClick(rowIndex,colIndex,dataIndex,gridName){
					
					
					if(gridName == "degreesgrid"){
						var sr = getGridSelected(gridName);
						
						if(!sr){
							return;
						}
						
						var msg='';
						if(sr.data.a0899==='true'||sr.data.a0899===true){
							msg = 'ȡ���ü�¼��,��ѧ��ѧλ���������<br/>ȷ��Ҫȡ������ü�¼��?';
						}else{
							msg = 'ѡ��ü�¼�󣬸�ѧ��ѧλ�����<br/>ȷ��Ҫѡ������ü�¼��?';
						}
						$h.confirm('ϵͳ��ʾ',msg,220,function(id){
							if("ok"==id){
								radow.doEvent('degreesgridchecked',sr.data.a0800);
							}else{
								
								return false;
							}
						});
					}
					
					
				}
				
				function deleteRowRendererA08(value, params, record,rowIndex,colIndex,ds){
					var a0800 = record.data.a0800;
					if(parent.buttonDisabled){
						return "ɾ��";
					}
					return "<a href=\"javascript:deleteRowA08(&quot;"+a0800+"&quot;)\">ɾ��</a>";
				}
				function deleteRowA08(a0800){ 
					Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ�ȷ��ɾ����",function(id) { 
						if("yes"==id){
							radow.doEvent('deleteRowA08',a0800);
						}else{
							return;
						}		
					});	
				}
				</script>
				<div id="div6" style="display:'';">
				
				<!-- <odin:groupBox title="ѧ��ѧλ��Ϣ��"> -->
				<div id="A08" style="margin-left:5%;width: 95%;border:1px solid #00F">
					<odin:toolBar property="toolBar2" applyTo="tol2" >
								<odin:fill></odin:fill>
								<odin:buttonForToolBar text="����"  id="degreesAddBtn" icon="images/add.gif"></odin:buttonForToolBar>
								<odin:buttonForToolBar text="����" id="saveA08" icon="images/save.gif" handler="saveDegree" isLast="true" cls="x-btn-text-icon" ></odin:buttonForToolBar>
					</odin:toolBar>
					<div>
					<odin:hidden property="a0800" title="����id" ></odin:hidden>
					<odin:hidden property="a0834" title="���ѧ����־" />
					<odin:hidden property="a0835" title="���ѧλ��־" />
					<input type="reset" name="reset" id="resetbtn" style="display: none;" />
					<div>
					<div id="tol2"></div>
					</div>
					<table>
						<tr>
							<td>
								<odin:grid property="degreesgrid" isFirstLoadData="false" forceNoScroll="true" url="/"   
								 height="210">
									<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
							     		<odin:gridDataCol name="a0899"/>
							     		<odin:gridDataCol name="a0800" />
								  		<odin:gridDataCol name="a0837" />
								  		<odin:gridDataCol name="a0801b" />
								   		<odin:gridDataCol name="a0901b" />
								   		<odin:gridDataCol name="a0814" />
								   		<odin:gridDataCol name="a0827" />			   		
								   		<odin:gridDataCol name="a0811" />
								   		<odin:gridDataCol name="a0804" />
								   		<odin:gridDataCol name="a0807" />
								   		<odin:gridDataCol name="a0904" />
								   		<odin:gridDataCol name="a0801a" />
								   		<odin:gridDataCol name="a0901a" />
								   		<odin:gridDataCol name="a0824" isLast="true"/>
								   		
									</odin:gridJsonDataModel>
									<odin:gridColumnModel>
									  <odin:gridRowNumColumn />
									  <odin:gridEditColumn header="���" width="25" editor="checkbox"  dataIndex="a0899" edited="true"/>
									  <odin:gridEditColumn header="id" dataIndex="a0800" editor="text" edited="false" hidden="true"/>
									  <odin:gridEditColumn2 header="���" dataIndex="a0837" codeType="ZB123" edited="false" editor="select"/>
									  <odin:gridEditColumn header="ѧ��" dataIndex="a0801a" edited="false" editor="text"/>
									  <odin:gridEditColumn header="ѧλ" dataIndex="a0901a" edited="false" editor="text"/>
									  <odin:gridEditColumn header="ѧУ��Ժϵ" dataIndex="a0814" edited="false" editor="text"/>
									  <odin:gridEditColumn header="רҵ" dataIndex="a0824" edited="false" editor="text" />
									  <odin:gridEditColumn header="����" dataIndex="delete" editor="text" edited="false" renderer="deleteRowRendererA08" isLast="true"/>
									</odin:gridColumnModel>
								</odin:grid>		
							</td>
							<td>
								<table>
									<tr><odin:select2 property="a0837" label="�������" required="true" codeType="ZB123"></odin:select2></tr>
									<tr><tags:PublicTextIconEdit property="a0801b" label="ѧ������" onchange="setA0801aValue" codetype="ZB64" readonly="true"></tags:PublicTextIconEdit></tr>
									<tr><odin:textEdit property="a0801a" label="ѧ������" validator="a0801aLength"></odin:textEdit></tr>
									<tr><odin:numberEdit property="a0811" label="ѧ������(��)" maxlength="3"></odin:numberEdit></tr>
									<tr><tags:PublicTextIconEdit property="a0901b" label="ѧλ����" onchange="setA0901aValue" codetype="GB6864" readonly="true"></tags:PublicTextIconEdit></tr>
									<tr><odin:textEdit property="a0901a" label="ѧλ����" validator="a0901aLength"></odin:textEdit></tr>
									<tr> <odin:textEdit property="a0814" label="ѧУ����λ������" validator="a0814Length"></odin:textEdit></tr>
									<tr><tags:PublicTextIconEdit property="a0827" label="��ѧרҵ���" onchange="setA0824Value" codetype="GB16835" readonly="true" /></tr>
									<tr><odin:textEdit property="a0824" label="��ѧרҵ����" validator="a0824Length"></odin:textEdit></tr>
									<tr><odin:NewDateEditTag property="a0804" label="��ѧʱ��"  maxlength="8"></odin:NewDateEditTag>	</tr>
									<tr><odin:NewDateEditTag property="a0807" label="�ϣ��ޣ�ҵʱ��" maxlength="8"></odin:NewDateEditTag></tr>
									<tr><odin:NewDateEditTag property="a0904" label="ѧλ����ʱ��" maxlength="8"></odin:NewDateEditTag></tr>
								</table>
							</td>
						</tr>
					</table>
					</div>
					</div>
				<!-- </odin:groupBox> -->
				<odin:hidden property="a0899" title="���"/>
				</div>
				<script type="text/javascript">
				Ext.onReady(function(){
					var firstload = true;
					var pgrid = Ext.getCmp("degreesgrid");
					var dstore = pgrid.getStore();
					dstore.on({  
				       load:{  
				           fn:function(){  
				           	 if(firstload){
				           		  $h.selectGridRow('degreesgrid',0);
				           		  firstload = false;
				             }
				           }      
				       },  
				       scope:this      
				   });  
				});
				Ext.onReady(function(){
					$h.applyFontConfig($h.spFeildAll.a08);
					if(parent.buttonDisabled){
						$h.setDisabled($h.disabledButtons.a08);
						
						var cover_wrap1 = document.getElementById('cover_wrap1');
						var ext_gridobj = Ext.getCmp('degreesgrid');
						var gridobj = document.getElementById('forView_degreesgrid');
						var viewSize = Ext.getBody().getViewSize();
						var grid_pos = $h.pos(gridobj);
						cover_wrap1.className=  "divcover_wrap";
						cover_wrap1.style.cssText=  "height:" + $h.pos(gridobj).top + "px;";
					}
					$h.fieldsDisabled(parent.fieldsDisabled);
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
				Ext.onReady(function(){
					var side_resize=function(){
						 Ext.getCmp('degreesgrid').setHeight(400);
						 //Ext.getCmp('degreesgrid').setWidth(800); 
						 Ext.getCmp('degreesgrid').setWidth(document.body.clientWidth*0.64); 
						 //document.getElementById('toolBar2').style.width = 1096;
					}
					side_resize();  
					window.onresize=side_resize; 
				})
				</script>

<!--------------------------------------- ������Ϣ�� --------------------------------------------->
				
				<script type="text/javascript">
				
				//������Ϣ׷��
				function appendRewardPunish(){ 
					var sm = Ext.getCmp("RewardPunishGrid").getSelectionModel();
					if(!sm.hasSelection()){
						alert("��ѡ��һ�����ݣ�");
						return;
					}
					radow.doEvent('appendonclick',sm.lastActive+'');
				}
				function setA1404aValue(record,index){//��������
					Ext.getCmp('a1404a').setValue(record.data.value);
				}
				
				function deleteRowRendererA14(value, params, record,rowIndex,colIndex,ds){
					var a1400 = record.data.a1400;
					if(parent.buttonDisabled){
						return "ɾ��";
					}
					return "<a href=\"javascript:deleteRowA14(&quot;"+a1400+"&quot;)\">ɾ��</a>";
				}
				function deleteRowA14(a1400){ 
					Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ�ȷ��ɾ����",function(id) { 
						if("yes"==id){
							radow.doEvent('deleteRowA14',a1400);
						}else{
							return;
						}		
					});	
				}
				</script>
				
				<div id="div7" style="display:'';">
				
				<!-- <odin:groupBox title="������Ϣ��"> -->
				<div id="A14" style="margin-left:5%;width: 95%;border:1px solid #00F">
				
					<odin:toolBar property="toolBar5" applyTo="tol3">
								<odin:fill></odin:fill>
								<odin:buttonForToolBar text="����" id="RewardPunishAddBtn" icon="images/add.gif"></odin:buttonForToolBar>
								<odin:buttonForToolBar text="����" id="saveA14" isLast="true" handler="saveReward" icon="images/save.gif" cls="x-btn-text-icon" ></odin:buttonForToolBar>
					</odin:toolBar>
					<div>
					<div id="tol3"></div>
					<div id="wzms">
						<table>
							<tr>
								<odin:textarea property="a14z101" cols="80" rows="4" colspan="5" label="��������" validator="a14z101Length"></odin:textarea>
							</tr>
						</table>
					</div>
					<div id="table1">
						
						 <table>
						 	<tr>
						 		<td>
						 					<odin:grid property="RewardPunishGrid" sm="row" forceNoScroll="true"  isFirstLoadData="false" url="/"
								 height="200">
									<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
										<odin:gridDataCol name="a1400" />
								  		<odin:gridDataCol name="a1404b" />
								  		<odin:gridDataCol name="a1404a" />
								   		<odin:gridDataCol name="a1415" />
								   		<odin:gridDataCol name="a1414" />
								   		<odin:gridDataCol name="a1428" />			   		
								   		<odin:gridDataCol name="a1411a" />
								   		<odin:gridDataCol name="a1407" />
								   		<odin:gridDataCol name="a1424" isLast="true"/>			   		
									</odin:gridJsonDataModel>
									<odin:gridColumnModel>
									  <odin:gridRowNumColumn />
									  <odin:gridEditColumn  header="id"  dataIndex="a1400" hidden="true" editor="text"/>
									  <odin:gridEditColumn2 header="�������ƴ���" dataIndex="a1404b" codeType="ZB65" edited="false" editor="select"/>
									  <odin:gridEditColumn  header="��������"  dataIndex="a1404a" edited="false" editor="text" />
									  <odin:gridEditColumn2 header="�ܽ���ʱְ����" dataIndex="a1415" edited="false" codeType="ZB09" editor="select" width="50"/>
									  <odin:gridEditColumn2 header="��׼���ؼ���" dataIndex="a1414" edited="false" codeType="ZB03" editor="select"/>
									  <odin:gridEditColumn2 header="��׼��������" dataIndex="a1428" edited="false" codeType="ZB128" editor="select" hidden="true"/>
									  <odin:gridEditColumn header="��׼����" dataIndex="a1411a" edited="false" editor="text" maxLength="30"/>
									  <odin:gridEditColumn header="��׼����" dataIndex="a1407" edited="false" editor="text" maxLength="8"/>
									  <odin:gridEditColumn header="���ͳ�������" dataIndex="a1424" edited="false" editor="text" maxLength="8" hidden="true"/>
									   <odin:gridEditColumn header="����" dataIndex="delete" editor="text" edited="false" renderer="deleteRowRendererA14" isLast="true"/>
									</odin:gridColumnModel>
								</odin:grid>
									</td>
									<td>
										<table id="tableA14">
											<tr id="btn" height="35px;">
												<td align="right"><odin:button text="׷�ӵ�ǰ��" handler="appendRewardPunish" property="append"></odin:button> </td>
												<td id="btn2" align="center"><odin:button text="ȫ���滻" property="addAll"></odin:button> </td>
											</tr>
											<tr height="35px;">
											<tags:PublicTextIconEdit property="a1404b" label="�������ƴ���" onchange="setA1404aValue" required="true" readonly="true" codetype="ZB65"></tags:PublicTextIconEdit>	
											</tr>
											<tr height="35px;"><odin:textEdit property="a1404a" label="��������" required="true"></odin:textEdit></tr>
											<tr height="35px;"><tags:PublicTextIconEdit property="a1415" label="�ܽ���ʱְ����" readonly="true" codetype="ZB09"></tags:PublicTextIconEdit></tr>
											<tr height="35px;"><odin:select2 property="a1414" label="��׼���ؼ���"  codeType="ZB03"></odin:select2>	</tr>
											<tr height="35px;"><tags:PublicTextIconEdit property="a1428" label="��׼��������" readonly="true" codetype="ZB128"></tags:PublicTextIconEdit></tr>
											<tr height="35px;"><odin:textEdit property="a1411a" label="��׼����" required="true"></odin:textEdit></tr>
											<tr height="35px;"><odin:NewDateEditTag property="a1407" label="��׼����" maxlength="8" isCheck="true" required="true"></odin:NewDateEditTag></tr>
											<tr height="35px;"><odin:NewDateEditTag property="a1424" label="���ͳ�������" maxlength="8" isCheck="true" ></odin:NewDateEditTag></tr>
											<odin:hidden property="a1400" title="����id" ></odin:hidden>
										</table>
									</td>
									</tr>
								</table>
					</div>
					</div>
					</div>
				<!-- </odin:groupBox> -->
				</div>
				<script type="text/javascript">
				
				Ext.onReady(function(){
					var firstload = true;
					var pgrid = Ext.getCmp("RewardPunishGrid");
					var dstore = pgrid.getStore();
					dstore.on({  
				       load:{  
				           fn:function(){  
				           	 if(firstload){
				           	 	$h.selectGridRow('RewardPunishGrid',0);
				           	 	firstload = false;
				           	 }
				           }      
				       },  
				       scope:this      
				   });  
				});
				Ext.onReady(function(){
					$h.applyFontConfig($h.spFeildAll.a14);
					if(parent.buttonDisabled){
						$h.setDisabled($h.disabledButtons.a14);
						
						var cover_wrap1 = document.getElementById('cover_wrap1');
						var cover_wrap2 = document.getElementById('cover_wrap2');
						var ext_gridobj = Ext.getCmp('RewardPunishGrid');
						var gridobj = document.getElementById('forView_RewardPunishGrid');
						var viewSize = Ext.getBody().getViewSize();
						var grid_pos = $h.pos(gridobj);
						
						cover_wrap1.className="divcover_wrap";
						cover_wrap1.style.cssText= "height:" + $h.pos(gridobj).top + "px;";
						
						cover_wrap2.className= "divcover_wrap";
						cover_wrap2.style.cssText= "margin-top: " + (grid_pos.top + ext_gridobj.getHeight()) + "px;"+
						"height:" + (viewSize.height - (grid_pos.top + ext_gridobj.getHeight()))+"px;";
						
					}
					$h.fieldsDisabled(parent.fieldsDisabled);
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
				Ext.onReady(function(){
					var side_resize=function(){
					     Ext.getCmp('RewardPunishGrid').setHeight(350);
						 //Ext.getCmp('RewardPunishGrid').setWidth(750); 
						 Ext.getCmp('a14z101').setWidth(document.body.clientWidth*0.64-55); 
						 //document.getElementById('btnToolBarDiv2').style.width = document.body.clientWidth*0.9 + 10;	
						 Ext.getCmp('RewardPunishGrid').setWidth(document.body.clientWidth*0.64); 
						 
						 
					}
					side_resize();  
					window.onresize=side_resize; 
				});
				</script>


<!--------------------------------------- ������Ϣ�� --------------------------------------------->

				
				<script type="text/javascript">
				
				
				function changedispaly(obj){
					var choose = Ext.getCmp('a0191').getValue();	
					if(choose){
						document.getElementById('choose').style.visibility='visible';
					}else{
						document.getElementById('choose').style.visibility='hidden';
					}
				}
				
				function yearChange(){
				    var now = new Date();
				    var year = now.getFullYear();
				    var yearList = document.getElementById("a1521");
				    for(var i=0;i<=50;i++){
				        year = year-1;
				        yearList.options[i] = new Option(year,year);
				    }
				}
				
				function deleteRowRendererA15(value, params, record,rowIndex,colIndex,ds){
					var a1500 = record.data.a1500;
					if(parent.buttonDisabled){
						return "ɾ��";
					}
					return "<a href=\"javascript:deleteRowA15(&quot;"+a1500+"&quot;)\">ɾ��</a>";
				}
				function deleteRowA15(a1500){ 
					Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ�ȷ��ɾ����",function(id) { 
						if("yes"==id){
							radow.doEvent('deleteRowA15',a1500);
						}else{
							return;
						}		
					});	
				}
				</script>
				
				<div id="div8" style="display:'';">
				
				<!-- <odin:groupBox title="������Ϣ��"> -->
				<div id="A15" style="margin-left:5%;width: 95%;border:1px solid #00F">
				<odin:toolBar property="toolBar6" applyTo="btnToolBarDiv2">
								<odin:fill></odin:fill>
								<odin:buttonForToolBar text="����" id="AssessmentInfoAddBtn" icon="images/add.gif" cls="x-btn-text-icon"></odin:buttonForToolBar>
								<odin:buttonForToolBar text="����" id="saveA15" icon="images/save.gif" isLast="true"  cls="x-btn-text-icon" ></odin:buttonForToolBar>
				</odin:toolBar>
				<div>
				<div id="btnToolBarDiv2" align="center" style=""></div>
				<odin:hidden property="a1500" title="����id" ></odin:hidden>
				<div id="wzms">
					<table>
						<tr>
							<td><odin:textarea property="a15z101" cols="117" rows="4" colspan="8" label="��������" validator="a15z101Length"></odin:textarea></td>
							<td>
								<div id="choose" style="visibility: hidden;">
									<table><odin:numberEdit property="a1527" label="&nbsp;&nbsp;&nbsp;ѡ����ȸ���" size="4"></odin:numberEdit></table>
								</div>
							</td>
							<td>
								<span>&nbsp;&nbsp;&nbsp;</span>
							</td>
							<td id="td" style="margin-left: 10px;"><odin:checkbox property="a0191" label="���б����" onclick="changedispaly(this)"></odin:checkbox></td>
						</tr>
					</table>
				</div>
				<div id="grid">
					<table>
						<tr>
							<td>
								<odin:grid property="AssessmentInfoGrid" sm="row"  forceNoScroll="true" isFirstLoadData="false" url="/"
							 height="200">
								<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
									<odin:gridDataCol name="a1500" />
							  		<odin:gridDataCol name="a1521" />
							   		<odin:gridDataCol name="a1517" isLast="true"/>			   		
								</odin:gridJsonDataModel>
								<odin:gridColumnModel>
								  <odin:gridRowNumColumn />
								  <odin:gridEditColumn header="id" dataIndex="a1500" editor="text" hidden="true"/>
								  <odin:gridEditColumn header="���" dataIndex="a1521" edited="false" editor="text"/>
								  <odin:gridEditColumn2  header="���˽������"  dataIndex="a1517" edited="false" editor="select" codeType="ZB18"/>
								   <odin:gridEditColumn header="����" dataIndex="delete" editor="text" edited="false" renderer="deleteRowRendererA15" isLast="true"/>
								</odin:gridColumnModel>
							</odin:grid>	
							</td>
							<td>
								<table>
									<tr height="50">
										<odin:select2 property="a1521" label="�������" required="true" maxlength="4" multiSelect="true" ></odin:select2>
									</tr>
									<tr height="50">
										<tags:PublicTextIconEdit property="a1517" label="���˽������" required="true" codetype="ZB18" readonly="true"></tags:PublicTextIconEdit>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</div>
				</div>
				</div>
				<!-- </odin:groupBox> -->
				</div>
				<script type="text/javascript">
				Ext.onReady(function(){
					if(parent.buttonDisabled){
						$h.setDisabled($h.disabledButtons.a15);
						
						var cover_wrap1 = document.getElementById('cover_wrap1');
						var cover_wrap2 = document.getElementById('cover_wrap2');
						var ext_gridobj = Ext.getCmp('AssessmentInfoGrid');
						var gridobj = document.getElementById('forView_AssessmentInfoGrid');
						var viewSize = Ext.getBody().getViewSize();
						var grid_pos = $h.pos(gridobj);
						
						cover_wrap1.className= "divcover_wrap";
						cover_wrap1.style.cssText= "height:" + $h.pos(gridobj).top + "px;";
						
						cover_wrap2.className= "divcover_wrap";
						cover_wrap2.style.cssText= "margin-top: " + (grid_pos.top + ext_gridobj.getHeight()) + "px;"+
						"height:" + (viewSize.height - (grid_pos.top + ext_gridobj.getHeight()))+"px;";
						
					}
					$h.fieldsDisabled(parent.fieldsDisabled);
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
				Ext.onReady(function(){
					var side_resize=function(){
						 //document.getElementById('btnToolBarDiv2').style.width = document.body.clientWidth*0.9 + 10;	
						 Ext.getCmp('AssessmentInfoGrid').setWidth(document.body.clientWidth*0.64); 
						 Ext.getCmp('a15z101').setWidth(document.body.clientWidth*0.64-57); 
					}
					side_resize();  
					window.onresize=side_resize; 
				});
				</script>


<!--------------------------------------- ��ͥ��Ա������ϵ --------------------------------------------->

				<script type="text/javascript">
				
				
				
				/* odin.accCheckedForE3 = function(obj,rowIndex,colIndex,colName,gridId){
				        if(obj.getAttribute('alowCheck')=="false"){
				            return;
				        }
						if(!checkBoxColClick(rowIndex,colIndex,null,gridId)){
							return;
						}
				        if(obj.className=='x-grid3-check-col'){
							if (typeof(gridId)=='undefined'||(typeof(gridId)=='string'&&gridId == '')) {
								odin.checkboxds.getAt(rowIndex).set(colName, true);
							}else{
								odin.ext.getCmp(gridId).store.getAt(rowIndex).set(colName, true);
							}
							obj.className = 'x-grid3-check-col-on';
				        }else{
							if (typeof(gridId)=='undefined'||(typeof(gridId)=='string'&&gridId == '')) {
								odin.checkboxds.getAt(rowIndex).set(colName, false);
							}else{
								odin.ext.getCmp(gridId).store.getAt(rowIndex).set(colName, false);
								if(document.getElementById("selectall_"+gridId+"_"+colName)!=null){
									document.getElementById("selectall_"+gridId+"_"+colName).value='false';
									document.getElementById("selectall_"+gridId+"_"+colName).className='x-grid3-check-col';
								}	
							}
							obj.className = 'x-grid3-check-col';
				        }
				}; */
				
				function deleteRowRendererA36(value, params, record,rowIndex,colIndex,ds){
					var a3600 = record.data.a3600;
					if(parent.buttonDisabled){
						return "ɾ��";
					}
					return "<a href=\"javascript:deleteRowA36(&quot;"+a3600+"&quot;)\">ɾ��</a>";
				}
				function deleteRowA36(a3600){ 
					Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ�ȷ��ɾ����",function(id) { 
						if("yes"==id){
							radow.doEvent('deleteRowA36',a3600);
						}else{
							return;
						}		
					});	
				}
				</script>
				<div id="div9" style="display:'';">
				
				<!-- <odin:groupBox title="��ͥ��Ա������ϵ"> -->
				<div id="A36" style="margin-left:5%;width: 95%;border:1px solid #00F">
					<odin:toolBar property="toolBarA36" applyTo="tolA36" >
								<odin:fill></odin:fill>
								<odin:buttonForToolBar text="����"  id="familyAddBtn" icon="images/add.gif"></odin:buttonForToolBar>
								<odin:buttonForToolBar text="����" id="saveA36" icon="images/save.gif" isLast="true" cls="x-btn-text-icon" ></odin:buttonForToolBar>
					</odin:toolBar>
					<div>
					<odin:hidden property="a3600" title="����id" ></odin:hidden>
					<div>
					<div id="tolA36"></div>
					</div>
					<table>
						<tr>
							<td>
								<odin:grid property="familyid" isFirstLoadData="false" forceNoScroll="true" url="/"   
								 height="210">
									<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
							     		<odin:gridDataCol name="a3600" />
								  		<odin:gridDataCol name="a3604a" />
								  		<odin:gridDataCol name="a3601" />
								   		<odin:gridDataCol name="a3607" />
								   		<odin:gridDataCol name="a3627" />
								   		<odin:gridDataCol name="a3611" />
								   		<odin:gridDataCol name="a3684" isLast="true"/>
								   		
									</odin:gridJsonDataModel>
									<odin:gridColumnModel>
									  <odin:gridRowNumColumn />
									  <odin:gridEditColumn header="id" dataIndex="a3600" editor="text" edited="false" hidden="true"/>
									  <odin:gridEditColumn2 header="��ν" dataIndex="a3604a" codeType="GB4761" edited="false" editor="select"/>
									  <odin:gridEditColumn header="����" dataIndex="a3601" edited="false" editor="text"/>
									  <odin:gridEditColumn header="��������" dataIndex="a3607" edited="false" editor="text"/>
									  <odin:gridEditColumn2 header="������ò" dataIndex="a3627" codeType="GB4762" edited="false" editor="select"/>
									  <odin:gridEditColumn header="������λ��ְ��" dataIndex="a3611" edited="false" editor="text" width="80"/>
									  <odin:gridEditColumn header="��ݺ���" dataIndex="a3684" edited="false" editor="text" width="70"/>
									  <odin:gridEditColumn header="����" dataIndex="delete" editor="text" edited="false" renderer="deleteRowRendererA36" isLast="true" width="30"/>
									</odin:gridColumnModel>
								</odin:grid>		
							</td>
							<td>
								<table>
									<tr><odin:select2 property="a3604a" label="��ν" required="true" codeType="GB4761"></odin:select2></tr>
									<tr><td><span>&nbsp;</span></td></tr>
									<tr><odin:textEdit property="a3601" label="����" required="true"></odin:textEdit></tr>
									<tr><td><span>&nbsp;</span></td></tr>
									<tr><odin:textEdit property="a3684" label="���֤����" validator="$h.IDCard"></odin:textEdit></tr>
									<tr><td><span>&nbsp;</span></td></tr>
									<tr><odin:NewDateEditTag property="a3607" label="��������"  maxlength="8"></odin:NewDateEditTag>	</tr>
									<tr><td><span>&nbsp;</span></td></tr>
									<tr><odin:select2 property="a3627" label="������ò" codeType="GB4762" required="true"></odin:select2></tr>
									<tr><td><span>&nbsp;</span></td></tr>
									<tr><odin:textEdit property="a3611" label="������λ��ְ��" required="true"></odin:textEdit></tr>
								</table>
							</td>
						</tr>
					</table>
					</div>
					</div>
				<!-- </odin:groupBox> -->
				
				</div>
				<script type="text/javascript">
				Ext.onReady(function(){
					var firstload = true;
					var pgrid = Ext.getCmp("familyid");
					var dstore = pgrid.getStore();
					dstore.on({  
				       load:{  
				           fn:function(){  
				           	 if(firstload){
				           		  $h.selectGridRow('familyid',0);
				           		  firstload = false;
				             }
				           }      
				       },  
				       scope:this      
				   });  
				});
				Ext.onReady(function(){
					$h.applyFontConfig($h.spFeildAll.a08);
					if(parent.buttonDisabled){
						$h.setDisabled($h.disabledButtons.a08);
						
						var cover_wrap1 = document.getElementById('cover_wrap1');
						var ext_gridobj = Ext.getCmp('familyid');
						var gridobj = document.getElementById('forView_familyid');
						var viewSize = Ext.getBody().getViewSize();
						var grid_pos = $h.pos(gridobj);
						cover_wrap1.className=  "divcover_wrap";
						cover_wrap1.style.cssText=  "height:" + $h.pos(gridobj).top + "px;";
					}
					$h.fieldsDisabled(parent.fieldsDisabled);
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
				Ext.onReady(function(){
					var side_resize=function(){
						 Ext.getCmp('familyid').setHeight(400);
						 Ext.getCmp('familyid').setWidth(document.body.clientWidth*0.64); 
						 //Ext.getCmp('familyid').setWidth(800); 
						 //document.getElementById('toolBarA36').style.width = 1096;
					}
					side_resize();  
					window.onresize=side_resize; 
				})
				</script>


<!--------------------------------------- ��ע��Ϣ�� --------------------------------------------->
<!-- <div id="div11" style="display:'';margin-top: 30px;"> -->
<div id="div13" style="display:'';margin-left:5%;width: 95%;border:1px solid #00F">

<%-----------------------------�Զ�����Ա��Ϣ��-------------------------------------------------------%>


	<div id="btnToolBarDivA71" style="width: 100%;"></div>
	<odin:toolBar property="btnToolBarA71" applyTo="btnToolBarDivA71">
					<odin:fill></odin:fill>
					<odin:buttonForToolBar text="����" id="bc13" isLast="true" icon="images/save.gif" cls="x-btn-text-icon"></odin:buttonForToolBar>
	</odin:toolBar>
	
	<%
Map<String, List<Object[]>> os_list = Info2PageModel.getInfoExt();
if(os_list!=null&&os_list.size()>0){
	for(String key : os_list.keySet()){
		List<Object[]> entitys = os_list.get(key);
		String[] kv = key.split("___");
		%>
		<odin:groupBox property="<%=kv[0] %>" title="<%=kv[1] %>">
			<table cellspacing="2" width="98%" align="center">
				<tr>
		<%
		
		Integer size = entitys.size(),index=0;;
		for(Object[] entity : entitys){
			String data_type = entity[5].toString();
			Object code_type = entity[3];
			if(index%3==0){
				%>
				</tr>
				<tr>
				<%
			}
			if("VARCHAR2".equals(data_type)){//�ı�
				if(code_type!=null&&!"".equals(code_type)){
					%>
					<tags:PublicTextIconEdit property="<%=entity[1].toString().toLowerCase() %>" label="<%=entity[2].toString() %>" codetype="<%=code_type.toString() %>" readonly="true"></tags:PublicTextIconEdit>
					<%
				}else{
					%>
					<odin:textEdit property="<%=entity[1].toString().toLowerCase() %>" label="<%=entity[2].toString() %>" ></odin:textEdit>
					<%
				}
			}else if("NUMBER".equals(data_type)){//����
				%>
				<odin:numberEdit property="<%=entity[1].toString().toLowerCase() %>" label="<%=entity[2].toString() %>" ></odin:numberEdit>
				<%
			}else if("DATE".equals(data_type)){//����
				%>
				<odin:NewDateEditTag property="<%=entity[1].toString().toLowerCase() %>" label="<%=entity[2].toString() %>" isCheck="true" maxlength="8" ></odin:NewDateEditTag>
				<%
			}
			index++;
		}
		
		%>
				</tr>
			</table>
		</odin:groupBox>
		<%
		
	}
	
}

%>
	
	<table style="width:100%;margin-top: 20px;">
		<col width="4%">
		<col  width="46%">
		<col  width="50%">
		<tr>
			<odin:textarea property="a7101" label="��ע" colspan='100' rows="8" validator='a7101Length'></odin:textarea>
			<!-- <td>
			<textarea name="a7101" id='a7101' style="width:650px;height:200px"></textarea>
			</td> -->
			<odin:hidden property="a7100" title="id(a7100" ></odin:hidden>
		</tr>
	</table>

</div>


<!--------------------------------------- ���������Ϣ�� --------------------------------------------->
<div id="div10" style="display:'';margin-left:5%;width: 95%;height:200px;border:1px solid #00F">
	<div id="btnToolBarDivA29" style="width: 100%;"></div>
	<odin:toolBar property="btnToolBarA29" applyTo="btnToolBarDivA29">
					<odin:fill></odin:fill>
					<odin:buttonForToolBar text="����" id="bc10" handler="saveEntry2" isLast="true" icon="images/save.gif" cls="x-btn-text-icon"></odin:buttonForToolBar>
	</odin:toolBar>
	<table style="width:100%;height:175px;margin-top: 20px;">
		<col width="10%">
		<col width="23%">
		<col width="10%">
		<col width="23%">
		<col width="10%">
		<col width="24%">
		<tr>
		 	<odin:NewDateEditTag property="a2907"  label="���뱾��λ����" isCheck="true" maxlength="8"></odin:NewDateEditTag>	
			<tags:PublicTextIconEdit property="a2911" label="���뱾��λ�䶯���" required="true" codetype="ZB77" readonly="true"></tags:PublicTextIconEdit>		
			<%-- <odin:textEdit property="a2941" label="��ԭ��λְ��"  validator="a2941Length"></odin:textEdit> --%>
			<tags:PublicTextIconEdit property="a2944s" label="��ԭ��λְ����" codetype="ZB09"></tags:PublicTextIconEdit>
		</tr>
		<tr>
			<odin:textEdit property="a2944" label="��ԭ��λְ����" validator="a2944Length"></odin:textEdit>
			<odin:textEdit property="a2921a" label="���뱾��λǰ������λ����" validator="a2921aLength"></odin:textEdit>
			<odin:NewDateEditTag property="a2949" label="����Ա�Ǽ�ʱ��" isCheck="true" maxlength="8"></odin:NewDateEditTag>	
		</tr>
		<tr></tr>
	</table>
</div>

<script type="text/javascript">
//���������Ϣ��
function saveEntry2(){
	//1�����뱾��λ���ڣ���������ڽ��бȽϣ�һ��Ӧ����18���ꡣ
	
	//�������ڵ�ǰʱ��
	var now = new Date();
	var nowTime = now.toLocaleDateString();
	var year = nowTime.substring(0 , 4);//��
	var MonthIndex = nowTime.indexOf("��");
	var mon = nowTime.substring(5 , MonthIndex);//��
	var day = nowTime.substring(MonthIndex+1,nowTime.length-1);//��
	if(mon.length == 1){
		mon = "0" + mon;
	}
	if(day.length == 1){
		day = "0" + day;
	}
	
	nowTime = year + mon + day;//��ȡ��λ����ʱ���ַ���
	
	var time = document.getElementById("a2907").value;;
	if(time.length == 6){
		time = time + "01";
	}
	if(parseInt(time) > parseInt(nowTime)){
		odin.alert("���뱾��λ���ڲ�������ϵͳ��ǰʱ��");
		return;
	}
	
	var time2 = document.getElementById("a2949").value;;
	if(time2.length == 6){
		time2 = time2 + "01";
	}
	if(parseInt(time2) > parseInt(nowTime)){
		odin.alert("����Ա�Ǽ�ʱ�䲻������ϵͳ��ǰʱ��");
		return;
	}
	
	//2�����뱾��λ�䶯��𣺱�����д��
	var a2911_combo = document.getElementById("a2911_combo").value;
	if(!a2911_combo){
		odin.alert("���뱾��λ�䶯��𲻿�Ϊ�գ�");
		return;
	}
	radow.doEvent("bc10");
}
</script>

<!--------------------------------------- �˳�������Ϣ�� --------------------------------------------->
<div id="div11" style="display:'';margin-left:5%;width: 95%;height:200px;border:1px solid #00F">
	<div id="btnToolBarDivA30" style="width: 100%;"></div>
	<odin:toolBar property="btnToolBarA30" applyTo="btnToolBarDivA30">
					<odin:fill></odin:fill>
					<odin:buttonForToolBar text="����" id="bc11" handler="saveLogout2" isLast="true" icon="images/save.gif" cls="x-btn-text-icon"></odin:buttonForToolBar>
	</odin:toolBar>

	
	<table style="width:100%;height:175px;margin-top: 20px;">
		<col width="10%">
		<col width="23%">
		<col width="10%">
		<col width="23%">
		<col width="10%">
		<col width="24%">
		<tr>
			<tags:PublicTextIconEdit property="a3001" label="�˳�����ʽ" required="true" codetype="ZB78" readonly="true" onchange="a3001change"></tags:PublicTextIconEdit>	
			<tags:PublicTextIconEdit  property="a3007a" label="������λ" readonly="true" codetype="orgTreeJsonData" ></tags:PublicTextIconEdit>
			<odin:select2 property="orgid" label="�˳���λ"></odin:select2>
		</tr>
		<tr>
			<odin:NewDateEditTag property="a3004" label="�˳�����ʱ��" isCheck="true" maxlength="8"></odin:NewDateEditTag>
			<odin:textEdit property="a3034" label="��ע" validator="a3034Length"></odin:textEdit>
		</tr>
		<tr></tr>
	</table>
	
<!-- <table style="width:100%;margin-top: 20px;" >
	<col width="84%">
	<col width="16%">
	<tr>
		<td align="right">
			<odin:button property="bc9" text="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"></odin:button>
		</td>
		<td align="left">
			<odin:button text="���沢��һ��" property="bcn9"></odin:button>
		</td>
	</tr>
</table> -->
</div>

<script type="text/javascript">
function a3001change(rs){
	if(rs.data.key.substring(0,1) != "1" && rs.data.key.substring(0,1) != "2"){
		odin.ext.getCmp('a3007a_combo').disable();
		Ext.query("#a3007a_combo+img")[0].onclick=null;
		document.getElementById('a3007a_combo').value='';
		odin.ext.getCmp('orgid_combo').enable();
		//Ext.query('#orgid_combo+img')[0].onclick=openDiseaseInfoCommonQueryorgid;
		return;
	}

	odin.ext.getCmp('a3007a_combo').enable();
	Ext.query("#a3007a_combo+img")[0].onclick=openDiseaseInfoCommonQuerya3007a;
	odin.ext.getCmp('orgid_combo').disable();
	//Ext.query('#orgid_combo+img')[0].onclick=null;
	document.getElementById('orgid').value='';
	document.getElementById('orgid_combo').value='';

	
}

function returnwinabc(rs){
	if(rs!=null){
		var rss = rs.split(",");
		document.getElementById('orgid').value=rss[0];
	}
}

//�˳���Ϣ��
function saveLogout2(){
	//1���˳�����ʽ��������д��
	var a3001_combo = document.getElementById("a3001_combo").value;
	if(!a3001_combo){
		odin.alert("�˳�����ʽ����Ϊ�գ�");
		return;
	}
	//2���˳�����λ���ڣ�Ӧ���ڲμӹ���ʱ�䡣
	
	//�������ڵ�ǰʱ��
	var now = new Date();
	var nowTime = now.toLocaleDateString();
	var year = nowTime.substring(0 , 4);//��
	var MonthIndex = nowTime.indexOf("��");
	var mon = nowTime.substring(5 , MonthIndex);//��
	var day = nowTime.substring(MonthIndex+1,nowTime.length-1);//��
	if(mon.length == 1){
		mon = "0" + mon;
	}
	if(day.length == 1){
		day = "0" + day;
	}
	
	nowTime = year + mon + day;//��ȡ��λ����ʱ���ַ���
	
	var time = document.getElementById("a3004").value;;
	if(time.length == 6){
		time = time + "01";
	}
	if(parseInt(time) > parseInt(nowTime)){
		odin.alert("�˳�����ʱ�䲻������ϵͳ��ǰʱ��");
		return;
	}
	
	radow.doEvent("bc11");

}
</script>

<!--------------------------------------- ��������Ϣ�� --------------------------------------------->
<div id="div12" style="display:'';margin-left:5%;width: 95%;height:200px;border:1px solid #00F">
	<div id="btnToolBarDivA53" style="width: 100%;"></div>
	<odin:toolBar property="btnToolBarA53" applyTo="btnToolBarDivA53">
					<odin:fill></odin:fill>
					<odin:buttonForToolBar text="����" id="bc12" isLast="true" icon="images/save.gif" cls="x-btn-text-icon"></odin:buttonForToolBar>
	</odin:toolBar>

	<table style="width:100%;height:175px;margin-top: 16px;">
		<col width="10%">
		<col width="23%">
		<col width="10%">
		<col width="23%">
		<col width="10%">
		<col width="24%">
		<tr>
			<odin:textEdit property="a5304" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;����ְ��" validator="a5304Length"></odin:textEdit>
			<odin:textEdit property="a5315" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;����ְ��" validator="a5315Length"></odin:textEdit>
			<odin:textEdit property="a5317" label="&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;��������" validator="a5317Length"></odin:textEdit>
		</tr>
		<tr>
			<odin:hidden property="a5300" title="id(a5300" ></odin:hidden>
			<odin:hidden property="a5399" title="id(a5399" ></odin:hidden>
			<odin:NewDateEditTag property="a5321" label="��������ʱ��" isCheck="true" maxlength="8"></odin:NewDateEditTag>
			<odin:NewDateEditTag property="a5323" label="���ʱ��" isCheck="true" maxlength="8"></odin:NewDateEditTag>
			<odin:textEdit property="a5327" label="�����" validator="a5327Length"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="a5319" label="�ʱ���λ" validator="a5319Length"></odin:textEdit>
		</tr>
		<tr></tr>
	</table>
</div>


<script type="text/javascript">
function djb(b){
	
	radow.doEvent("dj.onclick",b);
	dj(b);
}	
function dj(b){
	$(function(){
		stepBar.init("stepBar", {
			step : b,
			change : false,
			animation : true
		});
	});
}

function trim(s) { return s.replace(/^\s+|\s+$/g, ""); };
//��֤���֤�Ų���ȡ��������
function getBirthdatByIdNo(iIdNo) {
	var tmpStr = "";
	var idDate = "";
	var tmpInt = 0;
	var strReturn = "";
	
	iIdNo = trim(iIdNo);
	if (iIdNo.length == 15) {
		tmpStr = iIdNo.substring(6, 12);
		tmpStr = "19" + tmpStr;
		tmpStr = tmpStr.substring(0, 4) + tmpStr.substring(4, 6) + tmpStr.substring(6)
		
		return tmpStr;
	} else {
		tmpStr = iIdNo.substring(6, 14);
		tmpStr = tmpStr.substring(0, 4) + tmpStr.substring(4, 6) + tmpStr.substring(6)
		return tmpStr;
	}
}

//�뵳ʱ�����õ��� 
function a0140Click(){
	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
 	$h.openWin('addPartyTime','pages.publicServantManage.AddPartyTimeInfoAddPage','�뵳ʱ��',600,300,document.getElementById('a0000').value,ctxPath);
}

//�������ã���ֵcomboxArea_a0111
function a0111Change(){
	var a0111_combo = document.getElementById("a0111_combo").value;
	document.getElementById("comboxArea_a0111").value = document.getElementById("a0111_combo").value;
}

//���������ã���ֵcomboxArea_a0114
function a0114Change(){
	document.getElementById("comboxArea_a0114").value = document.getElementById("a0114_combo").value;
}




//��Ƭ��������¼� 
function showdialog(){
	var isUpdate = document.getElementById('isUpdate').value;
	if(isUpdate == 2){
		return;
	}
	var newwin = Ext.getCmp('picupload');
	newwin.show();
	var iframe = document.getElementById('iframe_picupload');
	iframe.src=iframe.src;
}

/**
*��������
*/
function openWin(id,url){
	var newwin = Ext.getCmp(id);
	if(!newwin.rendered){  
		newwin.show();
		var iframe = document.getElementById('iframe_'+id);
		iframe.src='<%=ctxPath%>/radowAction.do?method=doEvent&pageModel='+url;
		
	}else{  
		newwin.show();//alert("show")  
		var iframe = document.getElementById('iframe_'+id);
		iframe.src='<%=ctxPath%>/radowAction.do?method=doEvent&pageModel='+url;
	} 
}

newWin({id:'nameCheck',title:'�������',modal:true,width:500,height:420,maximizable:true});
newWin({id:'fontConfig',title:'��������',modal:true,width:380,height:230,maximizable:true});
newWin({id:'picupload',title:'ͷ���ϴ�',modal:true,width:900,height:490,src:'<%=ctxPath%>/picCut/picwin.jsp'});



var newwin = new Ext.Window({
	contentEl: "jlcontent",
	title : '��������',
	layout : 'fit',
	width : 525,
	overflow : 'hidden',
	height : 343,
	closeAction : 'hide',
	closable : true,
	minimizable : false,
	maximizable : false,
	modal : false,
	maximized:false,
	id : 'jlfl',
	bodyStyle : 'background-color:#FFFFFF; overflow-x:hidden; overflow-y:scroll',
	plain : true,
	listeners:{}
});

//��������ģʽ
function selectall(){
	var contenttext = document.getElementById("contenttext").innerText;
	Ext.getCmp("a1701").setValue(contenttext);
	var jlfl = Ext.getCmp("jlfl");
	jlfl.hide();
}
//��������ģʽ
function selectall2(){
	var contenttext = document.getElementById("contenttext2").innerText;
	Ext.getCmp("a1701").setValue(contenttext);
	var jlfl = Ext.getCmp("jlfl");
	jlfl.hide();
}


function showwin(){
	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
 		document.getElementById("jlcontent").style.display="block";
 		var jlfl = Ext.getCmp("jlfl");
 		Ext.getCmp("jltab").activate("tab2");
 		if(!jlfl.rendered){  
 			jlfl.show();//alert("no reader")  
 		}else if(jlfl.hidden){  
 			jlfl.show();//alert("hidden")  
 		}else{  
 			jlfl.hide();//alert("show")  
 		}
}

function a7101Length(value) {
	if(value.length>1000) {
		return "���ȳ������ƣ�1000������";
	} else {
		return true;
	}
}


//�鿴Ȩ�ޱ���,û���޸�Ȩ�ޣ���Ϣ��¼�����еı��水ť������ 
function buttonDisabled(){
	Ext.getCmp("InfoSaveA01").setDisabled(true);
	
}

function a0221Click(){
	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
 	$h.openWin('RankAddPageWin','pages.publicServantManage.RankAddPage','��ְ����',711,361,document.getElementById('a0000').value,ctxPath);
}
function a0221Click2(){
	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
 	$h.openWin('RankAddPageWin','pages.publicServantManage.RankAddPage','��ְ����',711,361,document.getElementById('a0000').value,ctxPath);
}

function a0192eClick(){
	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
 	$h.openWin('RradeRankAddPageWin','pages.publicServantManage.RradeRankAddPage','��ְ��',711,361,document.getElementById('a0000').value,ctxPath);
}
function a0192eClick2(){
	var name = document.getElementById("a0101").value;
	var Id = document.getElementById("a0000").value;
 	$h.openWin('RradeRankAddPageWin','pages.publicServantManage.RradeRankAddPage','��ְ��',711,361,document.getElementById('a0000').value,ctxPath);
}

function changea0201d(type){
	if(type == '1'){
		document.getElementById('a0201dSpanId').innerHTML='<font color="red">*</font>�Ƿ���ӳ�Ա';
	}
	if(type == '2'){
		document.getElementById('a0201dSpanId').innerHTML='�Ƿ���ӳ�Ա';
		document.getElementById('a0201eSpanId').innerHTML='��Ա���';
	}
}

//����������ʾ��Ϣ
Ext.onReady(function(){
	var jltab__tab1 = document.getElementById('jltab__tab1');
	var mubiao = jltab__tab1.parentNode;
	
	$("#"+mubiao.id).after("<div style='POSITION: absolute; LEFT: 320px; TOP: 5px;font-size: 12px;color:red;'>���ݹ�����λ��ְ���Զ�����</div>");  
	 
});	

//��д���֤���Ա�ͳ�������������ʾ�����֤��15λ��18λ���� 
function card(){
	var IDCard = document.getElementById("a0184").value;
	
	if(IDCard.length == 15 || IDCard.length == 18){
		var a0104 = 2;			//�Ա�1�У�2Ů 
		var a0104_combo = "Ů";
		var a0107 = 200001;		//��������
		
		if(IDCard.length == 18){			//18λ���֤�������� 
			//�Ա�
			a0104 -= IDCard.substring(16,17)%2;
			//�������� 
			a0107 = IDCard.substring(6,14);
		}
		
		if(IDCard.length == 15){			//15λ���֤�������� 
			//�Ա�
			a0104 -= IDCard.substring(14,15)%2;
			//�������� 
			a0107 = "19" + IDCard.substring(6,12);
		}
		
		
		if(a0104 == 1){
			a0104_combo = "��";
		}
		//��ֵ 
		document.getElementById("a0107").value = a0107;
		document.getElementById("a0107_1").value = a0107;
		document.getElementById("a0104").value = a0104;
		document.getElementById("a0104_combo").value = a0104_combo;
		
		
	}
	
	
}



</script>


<!------------------------------------------------------- ���ӵ���  ----------------------------------------------------->
<script type="text/javascript">

var tree;
var root;




function reloadTree() {
    var tree = Ext.getCmp("group");
    tree.getRootNode().reload();
    tree.expandAll();
}
function reloadThisGroup() {
	document.getElementById('groupname').innerHTML=document.getElementById('optionGroup').value;
}



function XXXGrantRender(value, params, rs, rowIndex, colIndex, ds){
	return "<a href=\"javascript:downloadFolderFile('"+rs.get('id')+"','"+rs.get('catalog')+"')\">����</a>" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + 
	"<a href=\"javascript:deleteFolderFile('"+rs.get('id')+"','"+rs.get('catalog')+"')\">ɾ��</a>" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	
}



</script>

<% 
	String ereaname = (String)(new GroupManagePageModel().areaInfo.get("areaname"));
	String ereaid = (String)(new GroupManagePageModel().areaInfo.get("areaid"));
	String manager = (String)(new GroupManagePageModel().areaInfo.get("manager"));
%>

			<odin:hidden property="treeId"/>
			<odin:hidden property="ereaname" value="<%=ereaname%>" />
			<odin:hidden property="ereaid" value="<%=ereaid%>" />
<div id="div14" style="display:'';margin-left:5%;width: 95%;border:1px solid #00F">

	<table>
		<tr>
			<td style="width: 15%"><div id="tree-div" style="overflow:auto; height:100%; width: 175px;float: left;border: 2px solid #c3daf9;display: inline;">

				</div>
			</td>
			<td style="width: 85%">
							<div style="float: left;display: inline;padding-left: 15px" id="left;">
						
							<odin:groupBox title="�����ļ�����" property="daoru">
								<table>
								<tr>
									<td>
									<odin:textEdit width="600" inputType="file" colspan="3"  property="excelFile" label="ѡ���ļ�:"></odin:textEdit> 
									</td>
									<td align="center" valign="middle">
									<%-- <odin:button text="�ϴ�" property="impwBtn" handler="formupload" />  --%>
									<img src="<%=request.getContextPath()%>/images/go1.jpg" onclick="formupload()" id="impwBtn">
									</td>
								</tr>
								</table>	
							</odin:groupBox>
							
					
						<odin:editgrid property="folderGrid" autoFill="false" pageSize="20" isFirstLoadData="false" url="/">
							<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
								<odin:gridDataCol name="id"/>
								<odin:gridDataCol name="catalog"/>
								<odin:gridDataCol name="name"/>
								<odin:gridDataCol name="filesize"/>
								<odin:gridDataCol name="uploads" />
								<odin:gridDataCol name="a0000" />
								<odin:gridDataCol name="time" isLast="true"/>
							</odin:gridJsonDataModel>
							<odin:gridColumnModel>
								<odin:gridRowNumColumn />
								<odin:gridEditColumn2 dataIndex="id" width="90" hidden="true" header="�ļ�id" align="center" editor="text" edited="false"/>
								<odin:gridEditColumn2 dataIndex="catalog" width="220" header="Ŀ¼����" align="center" editor="text" edited="false" hidden="true"/>
								<odin:gridEditColumn2 dataIndex="name" width="240" header="�ļ�����" align="center" editor="text" edited="false"/>
								<odin:gridEditColumn2 dataIndex="filesize" width="140" header="�ļ���С" align="center" editor="text" edited="false"/>
								<odin:gridEditColumn2 dataIndex="uploads" width="140" header="�ϴ���" align="center" editor="text" edited="false"/>
								<odin:gridEditColumn2 dataIndex="time" width="160" header="����ʱ��" align="center" editor="text" edited="false"/>
								<odin:gridEditColumn dataIndex="op4" header="����" width="200" renderer="XXXGrantRender" align="center" isLast="true" editor="text" edited="false"/> 
							</odin:gridColumnModel>
						</odin:editgrid> 
	</div>
			</td>
		</tr>
	</table>													
	
</div>
<odin:window src="" modal="true" id="AddFolderTree" width="380" height="260" title="�½��ļ���" closable="true" maximizable="false"></odin:window>
<odin:window src="" modal="true" id="UpdateFolderTree" width="380" height="260" title="�޸��ļ���" closable="true" maximizable="false"></odin:window>
<script type="text/javascript">
var win_addwin;
var win_addwinnew;

Ext.onReady(function(){
	/* var side_resize=function(){
		 Ext.getCmp('folderGrid').setHeight(380);
	}
	side_resize();  
	window.onresize=side_resize;  */
	Ext.getCmp('folderGrid').setHeight(380);
})


//��γ�ʼ��js��Ҫ����ҳ������� 
Ext.onReady(function(){
	
	//��ʼ��id
	var a0000id = '<%=a0000==null?"":a0000%>';
	
	document.getElementById("a0000").value = a0000id;
	
	if(a0000id==''){
		thisTab = parent.tabs.getActiveTab();
	}else{
		thisTab = parent.tabs.getItem(a0000id);
	}
	if(thisTab.initialConfig.errorInfo){
		isveryfy=true;
		var errorInfo = thisTab.initialConfig.errorInfo;
		spFeild = new Array();
		//רҵ����ְ�� ѧ��ѧλ ������λ��ְ�� �뵳ʱ�� ������� ��ȿ��˽�� ��ͥ��Ҫ��Ա�������Ҫ��ϵ
		var specialFeild = {"a0196":false,//רҵ����ְ��
							"xlxw":false,//ѧ��ѧλ
							"a0192a":false,//������λ��ְ��
							"a0140":false,//�뵳ʱ��
							"a14z101":false,//�������
							"a15z101":false,//��ȿ��˽��
							"a36":false};//��ͥ��Ҫ��Ա�������Ҫ��ϵ
		var specialFeildids = ['a0196','xlxw','a0192a','a0140','a14z101','a15z101','a36'];
		for(var it=0;it<errorInfo.length;it++){
			if('A06'==errorInfo[it].tableCode){
				specialFeild.a0196 = true;
			}
			if('A08'==errorInfo[it].tableCode){
				specialFeild.xlxw = true;
			}
			if('A02'==errorInfo[it].tableCode){
				specialFeild.a0192a = true;
			}
			if('A14'==errorInfo[it].tableCode){
				specialFeild.a14z101 = true;
			}
			if('A15'==errorInfo[it].tableCode){
				specialFeild.a15z101 = true;
			}
			if('A36'==errorInfo[it].tableCode){
				specialFeild.a36 = true;
			}
			if('a0141'==errorInfo[it].columnCode.toLowerCase()||'a0144'==errorInfo[it].columnCode.toLowerCase()||'a3921'==errorInfo[it].columnCode.toLowerCase()||'a3927'==errorInfo[it].columnCode.toLowerCase()){
				specialFeild.a0140 = true;
			}
			spFeild.push(errorInfo[it].columnCode.toLowerCase());
		}
		for(var i2=0;i2<specialFeildids.length;i2++){
			if(specialFeild[specialFeildids[i2]]){
				spFeild.push(specialFeildids[i2]);
			}
		}
		
	}
	
	
	var a0000 = thisTab.initialConfig.personid;
	var tabType = thisTab.initialConfig.tabType;
	
	
 	if(a0000.indexOf("addTab")!=-1){
		radow.doEvent('tabClick',a0000);
	}else{
		radow.doEvent('tabClick',a0000);
	} 
	
	var viewport = new Ext.Viewport({
		layout: 'border',
		items: [tabs]
	});
	
    applyFontConfig(spFeild);
    applyFontConfig($h.spFeildAll.a36);
    
});



Ext.onReady(function() {
	var a0000 = document.getElementById('a0000').value;
	var a0000New = a0000.replace(/update/, "")
	
	Ext.QuickTips.init();
	
     //var id = document.getElementById('ereaid').value;
	  
     var Tree = Ext.tree;
     tree = new Tree.TreePanel( {
	  id:'group',
      el : 'tree-div',//Ŀ��div����
      split:false,
      collapseMode : 'mini',
      monitorResize :true,   
      height:460,
      width: 270,
      minSize: 164,
      maxSize: 164,
      rootVisible: true,//�Ƿ���ʾ���ϼ��ڵ�
      autoScroll : true,
      animate : true,
      border:false,
      enableDD : false,
      containerScroll : true,
      
      tbar:[
		      '->', 
		        {
		        	text : '����', 
		        	icon : 'images/add.gif', 
		        	handler : function(){
		        		radow.doEvent('openNewWindow');
		        	}
		        }, '-', 
		        {
		        	text : '�޸�', 
		        	icon : 'images/i_2.gif', 
		        	handler : function(){
		        		radow.doEvent("update");
		        	}
		        }, '-', 
		        {
		        	text : 'ɾ��', 
		        	icon : 'images/icon/delete.gif', 
		        	handler : function(){
		        		
		        		radow.doEvent("delete");
		        	}
		        }
		      ],
      loader : new Tree.TreeLoader( {
            dataUrl : 'radowAction.do?method=doEvent&pageModel=pages.publicServantManage.Info2&eventNames=folderTree&a0000New='+a0000New
      })
  });
     
    
	root = new Tree.AsyncTreeNode({
		text : "�����ļ���",
		id : "001.001",					//Ĭ�ϵ�nodeֵ��?node=001.001
		href : "javascript:radow.doEvent('folderGriddb','001.001')"
	});
	tree.setRootNode(root);
	tree.render();
	root.expand(true);
	
}); 


function formupload(){
	var treeId = document.getElementById('treeId').value;
	
	if(treeId == null || treeId == ""){
		odin.info('��ѡ��Ŀ���ļ��� ��');
		return;
	}
	
	if(treeId == '001.001'){
		odin.info('�����ļ��У������ϴ��ļ� ��');
		return;
	}
	
	
	var from = document.getElementsByName("commForm")
	//alert(from.name);
	
	for(var i=0;i<from.length;i++)
	{
		from[i].id="commForm";
	}
	
	if(document.getElementById('excelFile').value!=""){
		
		var a0000 = document.getElementById('subWinIdBussessId').value;
		
		odin.ext.get(document.body).mask('�����ϴ����ݲ�������......', odin.msgCls);
		odin.ext.Ajax.request({
			url:'<%=request.getContextPath()%>/UploadFileServlet?method=uploadFolder&a0000='+a0000+'&treeId='+treeId,
			isUpload:true,
			method:'post',
			fileUpload:true,
			form:'commForm',
			success:function(){
				radow.doEvent('folderGrid.dogridquery');
				odin.alert("�ļ��ϴ��ɹ�!");
				Ext.getBody().unmask();//ȥ��MASK   
			}
		});
	}else{
		odin.info('��ѡ���ļ�֮���������봦��');
	}
}

//ɾ�����ӵ����ļ�
//encodeURI��������urlת�룬������Ĵ����������� 
function deleteFolderFile(id, catalog){
	
	$.ajax({
		url:'<%=request.getContextPath()%>/UploadFileServlet?method=deleteFolderFile',
		type:"GET",
		data:{
			"id":id,
			"filePath":encodeURI(catalog)
		},
		success:function(){
			radow.doEvent('folderGrid.dogridquery');
			odin.alert("�ļ�ɾ���ɹ�!");		
		},
		error:function(){
			odin.alert("�ļ�ɾ��ʧ��!");		
		}
	});
	
}


//���ص��ӵ����ļ�
//encodeURI��������urlת�룬������Ĵ����������� ����̨���յ�ʱ�������ת�봦��ת������
function downloadFolderFile(id, catalog){
	
	window.location="UploadFileServlet?method=downloadFolderFile&filePath="+encodeURI(encodeURI(catalog));
	
}


</script>




