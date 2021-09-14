<%@page import="com.insigma.odin.framework.sys.manager.sysmanager.comm.entity.Sysopright"%>
<%@page import="java.util.List"%>
<%@page import="com.insigma.odin.framework.persistence.HBUtil"%>
<%@page import="com.insigma.siis.local.pagemodel.customquery.CommSQL"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%String ctxPath = request.getContextPath(); 
%>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.FontConfigPageModel"%>
<%@page import="com.insigma.siis.local.pagemodel.cadremgn.sysmanager.authority.TableColInterface"%>
<style>
<%=FontConfigPageModel.getFontConfig()%>
.inline{
display: inline;
}
.pl{
margin-left: 8px;
}
.savecls{
position: absolute;
left: 25px;
top: 20px;
z-index: 100000;
}
*html{
background-image:url(about:blank);
background-attachment:fixed;
}

.button {
    background-color: #4CAF50; /* Green */
    border: none;
    color: white;
    padding: 15px 32px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
}

#a0222aSpanId,#a0283gSpanId,#a0215aCSpanId,#a0195SpanId{
	font-weight: bold;
	color: #6376d4;
}

</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="js/lengthValidator.js"></script>
<%@include file="/comOpenWinInit2.jsp" %>
<script type="text/javascript">

function deleteRowRenderer(value, params, record,rowIndex,colIndex,ds){
	var a0200 = record.data.a0200;
	if(realParent.buttonDisabled){
		return "ɾ��";
	}
	var fieldsDisabled = <%=TableColInterface.getAllUpdateData()%>;
	if(fieldsDisabled==''||fieldsDisabled==undefined){
		return "<a href=\"javascript:deleteRow2(&quot;"+a0200+"&quot;)\">ɾ��</a>";
	}
	var datas = fieldsDisabled.toString().split(',');
	for(var i=0;i<datas.length;i++){
		if(datas[i]==("a0201a")||datas[i]==("a0201b")||datas[i]==("a0201d")||datas[i]==("a0201e")||datas[i]==("a0215a")||datas[i]==("a0219")||datas[i]==("a0223")||datas[i]==("a0225")||datas[i]==("a0243")||datas[i]==("a0245")||datas[i]==("a0247")||datas[i]==("a0251b")||datas[i]==("a0255")||datas[i]==("a0265")||datas[i]==("a0267")||datas[i]==("a0272")||datas[i]==("a0281")||datas[i]==("a0222")||datas[i]==("a0279")||datas[i]==("a0504")||datas[i]==("a0517")){
			  Ext.getCmp("WorkUnitsAddBtn").setDisabled(true);
			return "<u style=\"color:#D3D3D3\">ɾ��</u>"; 
		}
		
	}
	return "<a href=\"javascript:deleteRow2(&quot;"+a0200+"&quot;)\">ɾ��</a>";
	/* return "<a href=\"javascript:deleteRow2(&quot;"+a0200+"&quot;)\">ɾ��</a>"; */
	
}
function deleteRow2(a0200){ 
	var gridSize = Ext.getCmp("WorkUnitsGrid").getStore().getCount();
	if(gridSize<=1){
		Ext.Msg.alert("ϵͳ��ʾ","���һ�������޷�ɾ����");
		return;
	}
	Ext.Msg.confirm("ϵͳ��ʾ","�Ƿ�ȷ��ɾ����",function(id) { 
		if("yes"==id){
			radow.doEvent('deleteRow',a0200);
		}else{

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
			radow.doEvent('deleteRow',sm.lastActive+'');
		}else{

		}		
	});	
}
Ext.onReady(function(){});
//������λְ���������
function a02checkBoxColClick(rowIndex,colIndex,dataIndex,gridName){
	if(realParent.buttonDisabled){
		return;
	}
	
	var sr = getGridSelected(gridName);
	
	if(!sr){
		return;
	}
	radow.doEvent('workUnitsgridchecked',sr.data.a0200+","+sr.data.a0281);
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
			   //'a0216aSpanId':['<font color="red">*</font>ְ������','<font color="red">*</font>��λ����'],
			   'a0215aSpanId':['<font color="red">*</font>ְ������','<font color="red">*</font>��λ����'],
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

	//��λ���
	//var a0222 = record.data.key;
	var a0222 = document.getElementById("a0222").value;
	var a0201b = document.getElementById("a0201b").value;
	

	document.getElementById("codevalueparameter").value=a0222;
	
	if("01"==a0222){//���ӳ�Ա
		//$h.selectShow('a0201d');
		selecteEnable('a0201d','0');
	}else{
		//$h.selectHide('a0201d');
		selecteDisable('a0201d');
	}
	
	if("01"==a0222||"99"==a0222){//����Ա�����չ�����Ա��λor����
		//$h.selectShow('a0219');//ְ�����
		//$h.selectShow('a0251');//ְ������
		///$h.selectShow('a0247');//ѡ�����÷�ʽ
		selecteEnable('a0219');//ְ�����
		selecteEnable('a0251');//ְ������
		selecteWinEnable('a0247');//ѡ�����÷�ʽ
		//document.getElementById('yimian').style.display="block";
		document.getElementById('yimian').style.visibility="visible";
		//$h.textShow('a0288');//����ְ����ʱ��
		//$h.dateEnable('a0288');//����ְ����ʱ��
		
		
		
		changeSelectData({one:{key:'1',value:'����'},two:{key:'0',value:'����'}});
		changeLabel(0);
	}else if("02"==a0222||"03"==a0222){//��ҵ��λ�����λor��ҵ��λרҵ������λ
		//$h.selectHide('a0219');//ְ�����disabled
		selecteDisable('a0219');//ְ�����disabled
		//$h.selectShow('a0251');//ְ������
		selecteEnable('a0251');//ְ������
		//$h.selectShow('a0247');//ѡ�����÷�ʽ
		selecteWinEnable('a0247');//ѡ�����÷�ʽ
		//document.getElementById('yimian').style.display="block";
		document.getElementById('yimian').style.visibility="visible";
		changeSelectData({one:{key:'1',value:'����'},two:{key:'0',value:'����'}});
		changeLabel(0);
		//$h.dateEnable('a0288');//����ְ����ʱ��
	}else if("04"==a0222||"05"==a0222||"06"==a0222||"07"==a0222){//���ؼ������˸�λor������ͨ���˸�λor��ҵ��λ�������˸�λor��ҵ��λ��ͨ���˸�λ
		//$h.selectHide('a0219');//ְ�����
		//$h.selectHide('a0251');//ְ������
		//$h.selectHide('a0247');//ѡ�����÷�ʽ
		selecteDisable('a0219');//ְ�����
		selecteDisable('a0251');//ְ������
		selecteWinDisable('a0247');//ѡ�����÷�ʽ
		//document.getElementById('yimian').style.display="none";
		document.getElementById('yimian').style.visibility="hidden";
		//$h.textHide('a0288');//����ְ����ʱ��	
		//$h.dateDisable('a0288');//����ְ����ʱ��	
		changeSelectData({one:{key:'1',value:'��ְ'},two:{key:'0',value:'����ְ'}});
		changeLabel(1);
	}else{
		//document.getElementById('yimian').style.display="none";
		document.getElementById('yimian').style.visibility="hidden";
	}
	a0255SelChange();
	//a0251change();	
}
function a0255SelChange(){
	var a0222 = document.getElementById("a0222").value;
	if("04"==a0222||"05"==a0222||"06"==a0222||"07"==a0222){//���ؼ������˸�λor������ͨ���˸�λor��ҵ��λ�������˸�λor��ҵ��λ��ͨ���˸�λ
		return;
	}
	//��ְ����ְ״̬
	/* var a0255Value = document.getElementById("a0255").value;
	
	if("1"==a0255Value){//����
		//document.getElementById('yimian').style.display="none";
		document.getElementById('yimian').style.visibility="hidden";
	}else if("0"==a0255Value){//����
		//document.getElementById('yimian').style.display="block";
		document.getElementById('yimian').style.visibility="visible";
	} */
	//var a0255 = document.getElementByName('a0255').checked;
	
	var a0255 = $("input[name='a0255']:checked").val();
	if("1"==a0255){//����
		document.getElementById('yimian').style.visibility="hidden";
	}else if("0"==a0255){//����
		document.getElementById('yimian').style.visibility="visible";
	}
	
	document.getElementById('a0255').value = a0255;
	
}




function setA0215aValue(record,index){//ְ����
	Ext.getCmp('a0215a').setValue(record.data.value);
}
function setA0255Value(record,index){
	Ext.getCmp('a0255').setValue(record.data.key)
}
//a01ͳ�ƹ�ϵ���ڵ�λ
function setParentValue(record,index){
	document.getElementById('a0195key').value = record.data.key;
	document.getElementById('a0195value').value = record.data.value;
	
	var a0195 = document.getElementById("a0195").value;
	radow.doEvent('a0195Change',a0195);
	
	//console.log(record);
	
	//document.getElementById('a0195').value=record.data.key;
	
}
function witherTwoYear(){
/*  	var check = '0';
	if($('#a0197').is(':checked')){
		check = '1';
	} */
	
	//realParent.document.getElementById('a0197').value=record.data.key;
}
//a01����
function setParentA0120Value(record,index){
	realParent.document.getElementById('a0120').value=record.data.key;
}
//a01 ���㹤������
function setParentA0194Value(record,index){
	/* var year = Ext.getCmp('a0194_y').getValue(); 
	var month = Ext.getCmp('a0194_m').getValue();
	var y2m = 0,a0197obj = document.getElementById('a0197');
	if(year!=''){
		y2m = parseInt(year)*12;
		if(y2m>=24){
			a0197obj.checked=true;
		}else{
			a0197obj.checked=false;
		}
	}else{
		a0197obj.checked=false;
	}
	if(month!=''){
		if(parseInt(month)>11){
			return;
		}
		y2m = y2m + month;
	}
	realParent.document.getElementById('a0194').value=y2m;
	realParent.document.getElementById('a0197').value=a0197obj.checked?"1":"0";
	*/
}
//ְ����  ְ�񼶱� ��ѡһ
/* function a0221achange(){
	var a0221a = document.getElementById('a0221a').value;
	//var a0221 = document.getElementById('a0221').value;
	if((a0221a!=null&&a0221a!=''&&a0221!=null&&a0221!='')||(a0221a==''&&a0221=='')){
		//$h.selectShow('a0221');
		//$h.selectShow('a0221a');
		selecteWinEnable('a0221');
		selecteWinEnable('a0221a');
		return;
	}
	
	if(a0221a==null||a0221a==''){
		//selecteEnable('a0221');
		selecteWinEnable('a0221');
	}else{
		//$h.selectHide('a0221');
		selecteWinDisable('a0221');
		//document.getElementById('a0288SpanId').innerHTML='����ְ��ȼ�ʱ��';
	}
} */
/* function a0221change(){
	var a0221 = document.getElementById('a0221').value;
	var a0221a = document.getElementById('a0221a').value;
	if(a0221a!=null&&a0221a!=''&&a0221!=null&&a0221!=''){
		//$h.selectShow('a0221');
		//$h.selectShow('a0221a');
		selecteWinEnable('a0221');
		selecteWinEnable('a0221a');
		return;
	}
	if(a0221==null||a0221==''){
		//$h.selectShow('a0221a');
		selecteWinEnable('a0221a');
	}else{
		//$h.selectHide('a0221a');
		selecteWinDisable('a0221a');
		document.getElementById('a0288SpanId').innerHTML='����ְ����ʱ��';
	}
} */
/* function onkeydownfn(id){
	if(id=='a0221a'){
		a0221achange();
	}else if(id=='a0221'){
		a0221change();
	}
} */
function a0215aCChange(record){
	radow.doEvent('a0215aCChange');
	/* document.getElementById('a0215a').value=record.data.value; */
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
		//a0251bOBJ.checked=false;
		//a0251bTD.style.visibility='visible';
		//a0251bOBJ.onclick=function(){return true;};
	}else if('27'==a0251){
		a0251bOBJ.checked=true;
		//a0251bTD.style.visibility='visible';
		//a0251bOBJ.onclick=function(){return false;};
	}else{
		//a0251bTD.style.visibility='hidden';
		//a0251bOBJ.checked=false;
	}
}
<%-- function setA0201eDisabled(){
	var a0201d = document.getElementById("a0201d").value;
	document.getElementById('a0201eSpanId').innerHTML='��Ա���';
	if("0"==a0201d || ""==a0201d){
		document.getElementById("a0201e_combo").disabled=true;
		document.getElementById("a0201e_combo").style.backgroundColor="#EBEBE4";
		document.getElementById("a0201e_combo").style.backgroundImage="none";
		Ext.query("#a0201e_combo+img")[0].style.display="none";
		document.getElementById("a0201e").value="";
		document.getElementById("a0201e_combo").value="";
	}else if("1"==a0201d){
		document.getElementById("a0201e_combo").readOnly=false;
		document.getElementById("a0201e_combo").disabled=false;
		document.getElementById("a0201e_combo").style.backgroundColor="#fff";
		document.getElementById("a0201e_combo").style.backgroundImage="url(<%=ctxPath%>/commform/basejs/ext/resources/images/default/form/text-bg.gif)";
		Ext.query("#a0201e_combo+img")[0].style.display="block";
		if("1"==a0201d){
			document.getElementById('a0201eSpanId').innerHTML='<font color="red">*</font>��Ա���';
		}
	}
} --%>


function setA0201eDisabled(){
	
	var a0201d = document.getElementById("a0201d").checked;
	document.getElementById('a0201eSpanId').innerHTML='��Ա���';
	
	
	if(!a0201d){
		document.getElementById("a0201e_combo").disabled=true;
		document.getElementById("a0201e_combo").style.backgroundColor="#EBEBE4";
		document.getElementById("a0201e_combo").style.backgroundImage="none";
		//Ext.query("#a0201e_combo+img")[0].style.display="none";
		Ext.getCmp('a0201e_combo').setDisabled(true);
		document.getElementById("a0201e").value="";
		document.getElementById("a0201e_combo").value="";
	}else{
		document.getElementById("a0201e_combo").readOnly=false;
		document.getElementById("a0201e_combo").disabled=false;
		document.getElementById("a0201e_combo").style.backgroundColor="#fff";
		document.getElementById("a0201e_combo").style.backgroundImage="url(<%=ctxPath%>/commform/basejs/ext/resources/images/default/form/text-bg.gif)";
		//Ext.query("#a0201e_combo+img")[0].style.display="block";
		Ext.getCmp('a0201e_combo').setDisabled(false);
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

<div id="btnToolBarDiv" style="width: 1019px;"></div>
<odin:toolBar property="btnToolBar" applyTo="btnToolBarDiv">
<odin:fill></odin:fill>
				<odin:buttonForToolBar text="��¡" id="KL" handler="kl"  icon="images/add.gif"></odin:buttonForToolBar>
				<odin:buttonForToolBar text="����" id="WorkUnitsAddBtn" icon="images/add.gif"></odin:buttonForToolBar>
				<odin:buttonForToolBar text="����" id="save" isLast="true" icon="images/save.gif" cls="x-btn-text-icon" handler="saveA02" ></odin:buttonForToolBar>
</odin:toolBar>

<odin:hidden property="a0200" title="����id" ></odin:hidden>
<odin:hidden property="a0255" title="��ְ״̬" ></odin:hidden>
<odin:hidden property="a0225" title="��Ա������" value="0"></odin:hidden>
<odin:hidden property="a0223" title="��ְ������" ></odin:hidden>
<odin:hidden property="a0201c" title="�������" ></odin:hidden>
<odin:hidden property="codevalueparameter" title="ְ���κ͸�λ��������"/>
<odin:hidden property="ChangeValue" title="ְ�����ƴ���͵�λ��������"/>
<odin:hidden property="a0271" value=''/>
<odin:hidden property="a0222" value=''/>
<odin:hidden property="a0195key" value=''/>
<odin:hidden property="a0195value" value=''/>
<odin:hidden property="b0194Type" value=''/>
<odin:hidden property="a8600" value=''/>
<odin:hidden property="a0192f" title="������λ��ְ��ȫ�ƶ�Ӧ�ģ���ְʱ��" ></odin:hidden>
<odin:groupBox title="�й�ͳ����" property="yyya" width="959">
	<table align="left" style="width: 100%" >
		<tr align="left">  
		    <tags:PublicTextIconEdit3 onchange="setParentValue" property="a0195" label="ͳ�ƹ�ϵ���ڵ�λ" readonly="true" codetype="orgTreeJsonData" width="250"></tags:PublicTextIconEdit3>
			<!-- <td align="right" style="padding-right: 8px;"><span id="a0194SpanId_s" style="font-size: 12;white-space: nowrap;" >���㹤������ʱ��</span> </td> -->
			<td colspan="3" align="left">
				<table cellpadding="0" cellspacing="0">
				  <tr style="padding-left: 0px;margin-left: 0px;">
			  		<td width="210" align="right" valign="middle">
				  		<label for="a0197" style="font-size: 12px;" id="a0197SpanId">�����������ϻ��㹤������ </label>
						<input type="checkbox" name="a0197"  id="a0197" onclick="witherTwoYear()" />
				  	</td>
				  	<td width="210" align="right" valign="middle">
				  		<label for="a0188" style="font-size: 12px;" id="a0188SpanId">�������򣨽ֵ���������ְ���� </label>
						<input type="checkbox" name="a0188"  id="a0188" />
				  	</td>
				  </tr>
				  <tr style="padding-left: 0px;margin-left: 0px;">
			  		<td width="210" align="right" valign="middle">
				  		<label for="a0132" style="font-size: 12px;" id="a0132SpanId">�������򣨽ֵ�����������ί���</label>
						<input type="checkbox" name="a0132"  id="a0132" />
				  	</td>
				  	<td width="210" align="right" valign="middle">
				  		<label for="a0133" style="font-size: 12px;" id="a0133SpanId">�������򣨽ֵ����򳤣����Σ� </label>
						<input type="checkbox" name="a0133"  id="a0133" />
				  	</td>
				  </tr>
				</table>
			</td>
		</tr>
	</table>
</odin:groupBox>		

<odin:groupBox title="ְ��" property="zhiwugroup">
<table  style="width: 100%">
	<tr align="left">
		<td colspan="2">
			<table>
				<tr>
					<odin:textEdit property="a0192a" width="550" label="ȫ��"  maxlength="1000" ><span>&nbsp;&nbsp;(���������)</span></odin:textEdit>
					<td rowspan="2"><odin:button text="��������" property="UpdateTitleBtn" ></odin:button></td>
					<td rowspan="2"><odin:button text="����������" property="personGRIDSORT" handler="openSortWin" ></odin:button></td>
				</tr>
				<tr style="display: none">
			       <odin:textEdit property="a0192" width="550" label="���"  maxlength="1000"><span>&nbsp;&nbsp;(��������)</span></odin:textEdit>
			    </tr>	
			</table>
		</td>
	</tr>
    <tr>
	    <td>
	    	<table width="410"><tr><td>
		    	<odin:editgrid property="WorkUnitsGrid" sm="row" isFirstLoadData="false" url="/" 
					 height="330" title="" pageSize="50"  >
						<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
				     		<odin:gridDataCol name="a0281"/>
				     		<odin:gridDataCol name="a0200" />
					  		<odin:gridDataCol name="a0201b" />
					  		<odin:gridDataCol name="a0201a" />
					  		<%-- <odin:gridDataCol name="a0215a" /> --%>
					  		<odin:gridDataCol name="a0215a" />
					  		<odin:gridDataCol name="a0222" />
					   		<odin:gridDataCol name="a0255"/>
					   		<odin:gridDataCol name="delete" isLast="true"/>
					   		
						</odin:gridJsonDataModel>
						<odin:gridColumnModel>
						  <odin:gridRowNumColumn />
						  <odin:gridEditColumn2 header="���" width="100" editor="checkbox" dataIndex="a0281" edited="true"/><%-- checkBoxClick="a02checkBoxColClick" --%> 
						  <odin:gridEditColumn2 header="id" edited="false" dataIndex="a0200" editor="text" width="200" hidden="true"/>
						  <odin:gridEditColumn2 header="��ְ��������" edited="false"  dataIndex="a0201b"  editor="text" width="300" hidden="true"/>
						  <odin:gridEditColumn2 header="��ְ��������" edited="false" dataIndex="a0201a" renderer="changea0201a" editor="text" width="300"/>
						  <%-- <odin:gridEditColumn2 header="ְ�����ƴ���" edited="false"  dataIndex="a0215a" editor="select" codeType="ZB08" hidden="true" width="100"/> --%>
						  <odin:gridEditColumn2 header="ְ������" edited="false"  dataIndex="a0215a" editor="text" width="200"/>
						  <odin:gridEditColumn2 header="��λ���" edited="false"  dataIndex="a0222" editor="text" hidden="true"/>
						  <odin:gridEditColumn2 header="��ְ״̬" edited="false" dataIndex="a0255"  codeType="ZB14" editor="select" width="160"/>
						  <odin:gridEditColumn header="����" width="100" dataIndex="delete" editor="text" edited="false" renderer="deleteRowRenderer" isLast="true"/>
						  	   
					</odin:gridColumnModel>
				</odin:editgrid>
				<label><input type="checkbox" checked="checked" id="xsymzw" onclick="checkChange(this)"/><font style="font-size: 12px;">��ʾ����ְ��</font></label>
				<div id="btngroup"> </div>
				<div style="margin-top: 8px;" id="btngroup2"> </div>
				</td>
			</tr>
		</table>
    	</td>
    	<td >
    		<table>
    			<tr>
    				<tags:PublicTextIconEdit3 codetype="orgTreeJsonData" onchange="a0201bChange" label="��ְ����" property="a0201b" defaultValue="" readonly="true"/>
    				
				</tr>
    			<tr  align="left">
    				<odin:textEdit property="a0201a" label="��ְ��������" required="true"></odin:textEdit>
    				<!-- <odin:select2 property="a0255" label="��ְ״̬" required="true" onchange="a0255SelChange" value="1" codeType="ZB14"></odin:select2> -->
    				<td align="right"><span id="a0195SpanId_s" style="font-size: 12px;" class="fontConfig"><!-- <font color="red">*</font>��ְ״̬&nbsp; --></span></td>
    				
				    <td align="left">
						<input align="middle" type="radio" name="a0255" id="a02551" checked="checked" value="1" class="radioItem" />
						<label for="a0255" style="font-size: 12px;">����</label>
						<span>&nbsp;</span>
						<input align="middle" type="radio" name="a0255" id="a02550" value="0" class="radioItem"/>
						<label for="a0255" style="font-size: 12px;">����</label>
					</td>
				</tr>
				<tr>
					<tags:PublicTextIconEdit3 codetype="GWGLLB" onchange="a0215aCChange" label="ѡ��ְλ" property="a0215aC" readonly="true"/>
					<%-- <tags:PublicTextIconEdit3 property="a0222a" label="�쵼���쵼ͳ�Ʊ�ʶ" codetype="KZA0222A" required="true" readonly="true"></tags:PublicTextIconEdit3> --%>
					<td></td>
					<td align="left" id="a0222bTD">
						<input type="checkbox" name="a0222b" id="a0222b" />
						<label id="a0222bSpanId" for="a0222b" style="font-size: 12px;">����ְ��</label>
					</td>
				</tr>
				
				<tr>
					<odin:textEdit property="a0215a" label="ְ������" onblur="validatea0215a()"  maxlength="100"></odin:textEdit>
					<!-- <odin:select2 property="a0201d" label="�Ƿ���ӳ�Ա" data="['1','��'],['0','��']" onchange="setA0201eDisabled"></odin:select2> -->
					
					<td></td>
					<td align="left" id="a0219TD">
						<input type="checkbox" name="a0279" id="a0279" />
						<label id="a0279SpanId" for="a0279" style="font-size: 12px;">��������</label>
						<input type="checkbox" name="a0219" id="a0219" />
						<label id="a0219SpanId" for="a0219" style="font-size: 12px;">�쵼ְ��</label>
					</td>
				
				<tr align="left">
				    <odin:select2 property="a0201e" label="��Ա���" onchange="disableA0248" codeType="ZB129"></odin:select2>
				    <td></td>
				    <td align="left" id="a0201dTD">
						<input type="checkbox" name="a0201d" id="a0201d"/>
						<label id="a0201dSpanId" for="a0201d" style="font-size: 12px;">�쵼��Ա</label>
						<input type="checkbox" name="a0248" id="a0248"/>
						<label id="a0248SpanId" for="a0248" style="font-size: 12px;">��ռְ��</label>
					</td>
				</tr>
				<tr>
					<tags:PublicTextIconEdit3 property="a0247" label="ѡ�����÷�ʽ" codetype="ZB122" readonly="true"></tags:PublicTextIconEdit3>
					<td></td>
					<td align="left" id="a0251bTD">
						<input type="checkbox" name="a0251b" id="a0251b"/>
						<label id="a0251bfSpanId" for="a0251b" style="font-size: 12px;">�Ƹ����</label>
						<input type="checkbox" name="a0277" id="a0277"/>
						<label id="a0277SpanId" for="a0277" style="font-size: 12px;">�Ƿ���</label>
					</td>
				</tr>
				<tr>
					<odin:NewDateEditTag property="a0243" labelSpanId="a0243SpanId" maxlength="8" label="��ְʱ��" required="true"></odin:NewDateEditTag>
					<odin:textEdit property="a0245" label="��ְ�ĺ�" validator="a0245Length"></odin:textEdit>
				</tr>
				<tr align="left">
				    <odin:hidden property="a0221a" value="0"/> 
				</tr>
				<tr align="left" >
				    <odin:hidden property="a0272" value="0"/>
				    <odin:hidden property="a0251" value="0"/>
				</tr>
				
				
				<tr id='yimian'>
					<odin:NewDateEditTag property="a0265" label="��ְʱ��" labelSpanId="a0265SpanId"  maxlength="8" required="true"></odin:NewDateEditTag>
					<odin:textEdit property="a0267" label="��ְ�ĺ�" validator="a0267Length"></odin:textEdit>
				</tr>
				
				<tr>
				<odin:textEdit property="a0283g" width="490" label="��������"  maxlength="1000" required="true" colspan="4"/>
				</tr>
				<%-- <tr>
					<odin:textEdit property="a0272" label="ְ��䶯ԭ������"></odin:textEdit>
					<odin:textarea property="a0229" label="�ֹܴ��¹���" colspan='4' rows="4"></odin:textarea>
				</tr>
				<tr align="left">
					<odin:dateEdit  label="ʱ��" format="Ymd" property="a8601"  title="ʱ��" colspan="5" width="100"  />
				</tr> --%>
				<tr>
					<odin:textarea  property="a0229" label="�ֹܹ���" colspan='4' rows="3" ></odin:textarea>
				</tr>
				<tr>
					<odin:textEdit property="a0256" label="�ֹܵ�λ" colspan='2'></odin:textEdit>
					<odin:textEdit property="a0256a" label="��ע" colspan='2'></odin:textEdit>
				</tr>
				
				<%-- <tr>
					<td align="right" colspan='4' rowspan="2"><odin:button text="�鿴��ʷ" handler="showa0229"></odin:button></td>
				</tr> --%>
								
				<%
				String sql = CommSQL.getInfoSQL("'A02'");

				List<Object[]> list = HBUtil.getHBSession().createSQLQuery(sql).list();
				if(list.size()>0){
				%>
				<tr>
					<td><odin:button text="������Ϣ" property="SubInfo" handler="openSubWin"></odin:button></td>
				</tr>
				<%} %>
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
</odin:groupBox>

<odin:hidden property="a0281" title="�������"/>
<odin:hidden property="a0000" title="��Ա����"/>
<script type="text/javascript">
//��¡��ǰְ��
function kl(){
	var a0200 = document.getElementById('a0200').value;
	if(a0200==null||a0200==''){
		$h.alert('ϵͳ��ʾ��','���ȱ���ְ����Ϣ!');

	}else{
		document.getElementById('a0200').value='';
		$("#a0279").attr("checked", '');//ȥ����ְ���ʶ
		$h.alert('��¡�ɹ���','ȷ����Ϣ����������棬������������ְ����Ϣ!');
    }
}

function updateZWMCMC() {
    radow.doEvent('updateZWMCMC.onclick');
}

// ���������д�����
function WorkflushWindowsOpen(a0192, a0192a) {

    window.realParent.document.getElementById('a0192').value = feildIsNull(a0192) ? "" : a0192;
    window.realParent.document.getElementById('a0192a').value = feildIsNull(a0192a) ? "" : a0192a;
    window.realParent.document.getElementById('a0192a_p').innerHTML = feildIsNull(a0192a) ? "" : a0192a;

    window.realParent.document.getElementById('a0192').value = feildIsNull(a0192) ? "" : a0192;
    window.document.getElementById('a0192').value = feildIsNull(a0192) ? "" : a0192;
    window.document.getElementById('a0192a').value = feildIsNull(a0192a) ? "" : a0192a;

}

function showa0229() {
    var a0200 = document.getElementById('a0200').value;
    if (a0200 == null || a0200 == '') {
        $h.alert('ϵͳ��ʾ��', '���ȱ���ְ����Ϣ!');
        return;
    }
    $h.openPageModeWin('openSubWin', 'pages.publicServantManage.ChargeWork', '�ֹܴ��¹���', 630, 300, document.getElementById('a0200').value, '<%=ctxPath%>')
}

function openSubWin(){
	var a0200 = document.getElementById('a0200').value;
	if(a0200==null||a0200==''){
		$h.alert('ϵͳ��ʾ��','���ȱ���ְ����Ϣ!');
		return;
	}
	$h.openPageModeWin('openSubWin','pages.publicServantManage.WorkUnitsSubInfo','ְ�񲹳���',600,400,document.getElementById('a0200').value,'<%=ctxPath%>')
}

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
		
		/* new Ext.Button({
			icon : 'images/add.gif',
			id:'WorkUnitsAddBtn',
			cls :'inline',
		    text:'����',
		    renderTo:"btngroup2"
		}); */
		
		/* new Ext.Button({
			icon : 'images/back.gif',
			id:'delete1',
		    text:'ɾ��',
		    cls :'inline pl',
		    renderTo:"btngroup2",
		    handler:deleteRow
		}); */
		 
		/* new Ext.Button({
			icon : 'images/save.gif',
			id:'save1',
		    text:'����',
		    width:80,
		    height:40,
		    renderTo:"btngroup3",
		    handler:saveA02
		}); */
		
	});
	//ͳ�ƹ�ϵ���ڵ�λ����ְ������������
	function saveA02(){
		
		//ͳ�ƹ�ϵ���ڵ�λ����Ϊ��
		var a0195 = document.getElementById('a0195').value;
		
		/* if(a0195==""){
			Ext.Msg.alert("ϵͳ��ʾ","ͳ�ƹ�ϵ���ڵ�λ����Ϊ�գ�");
			return;
		} */
		
		var a0255 = document.getElementById('a0255').value;		//��ְ״̬
		var a0201a = document.getElementById('a0201a').value;		//��ְ��������
		var a0201b = document.getElementById('a0201b').value;
		var a0195 = document.getElementById('a0195').value;
		var a0215a = document.getElementById('a0215a').value;//ְ������
		if(a0201b==""){
			Ext.Msg.alert("ϵͳ��ʾ","���ȵ��ͼ�������ְ������ѡ��");
			return;
		}
		
		if(a0201a==""){
			Ext.Msg.alert("ϵͳ��ʾ","��ְ�������Ʋ���Ϊ�գ�");
			return;
		}
		
		//��ְʱ��
		var a0243 = document.getElementById('a0243').value;	
		var a0243_1 = document.getElementById('a0243_1').value;	
		
		if(!a0243_1){
			$h.alert('ϵͳ��ʾ','��ְʱ�䲻��Ϊ�գ�', null,200);
			return false;
		}
		
		var text1 = dateValidateBeforeTady(a0243_1);
		if(a0243_1.indexOf(".") > 0){
			text1 = dateValidateBeforeTady(a0243);
		}
		if(text1!==true){
			$h.alert('ϵͳ��ʾ','��ְʱ�䣺' + text1, null,400);
			return false;
		}
		
		
		//��ְʱ��
		var a0265 = document.getElementById('a0265').value;	
		var a0265_1 = document.getElementById('a0265_1').value;	
		
		//���ְ��Ϊ���⣬����ְʱ�䲻��Ϊ��
		if(a0255 != null && a0255 == 0){
			if(!a0265_1){
				$h.alert('ϵͳ��ʾ','��ְʱ�䲻��Ϊ�գ�', null,200);
				return false;
			}
		}
		
		
		
		var text2 = dateValidateBeforeTady(a0265_1);
		if(a0265_1.indexOf(".") > 0){
			text2 = dateValidateBeforeTady(a0265);
		}
		if(text2!==true){
			$h.alert('ϵͳ��ʾ','��ְʱ�䣺' + text2, null,400);
			return false;
		}
		
		//"ְ��䶯ԭ������"���ɳ���50��
		var a0229 = document.getElementById('a0229').value;	
		
		
		var it=true;
		if(a0215a==""){
			validatea0215a();
			$h.confirm("ϵͳ��ʾ��",'û��ְ�����ƣ�<br/>�Ƿ�������棿',400,function(id) { 
				if("ok"==id){
					if(a0195 != null && a0195 != a0201b){
						radow.doEvent('check'); //�ж��Ƿ��������¼����������ϵ
					}else{
						radow.doEvent('save');
					}
				}else{
					return false;
				}		
			});
			it=false;
			//alert(11);
			
			
		}
		if(it==true){
			if(a0195 != null && a0195 != a0201b){
				radow.doEvent('check'); //�ж��Ƿ��������¼����������ϵ
			}else{
				radow.doEvent('save');
			}
		}
	
	}
</script>
<script>

//У��ְ�������Ƿ�Ϊ��
function validatea0215a(){
	var a0215a = document.getElementById('a0215a').value;	
	if(a0215a==""){
		document.getElementById('a0215a').style.border = '1px solid red';	
	}
}
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
	}else if("04"==a0222||"05"==a0222||"06"==a0222||"07"==a0222){//���ؼ������˸�λor������ͨ���˸�λor��ҵ��λ�������˸�λor��ҵ��λ��ͨ���˸�λ
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
	//alert(store.getCount());
	
	if (sm.length<=0){
		
		$h.alert('ϵͳ��ʾ��','��ѡ����Ҫ�����ְ��',null,180);
		return;	
	}
	
	var selectdata = sm[0];  //ѡ�����еĵ�һ��
	var index = store.indexOf(selectdata);
	if (index==0){
		
		$h.alert('ϵͳ��ʾ��','��ְ���Ѿ�������ϣ�',null,180);
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
		
		$h.alert('ϵͳ��ʾ��','��ѡ����Ҫ�����ְ��',null,180);
		return;	
	}
	
	var selectdata = sm[0];  //ѡ�����еĵ�һ��
	var index = store.indexOf(selectdata);
	var total = store.getCount();
	if (index==(total-1) ){
		
		$h.alert('ϵͳ��ʾ��','��ְ���Ѿ���������£�',null,180);
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
	/*bbar.insertButton(11,[
						new Ext.menu.Separator({cls:'xtb-sep'}),
						new Ext.Spacer({width:50}),
						new Ext.Button({
							icon : 'images/icon/arrowup.gif',
							id:'UpBtn',
						    text:'����',
						    handler:UpBtn
						}),
						new Ext.Button({
							icon : 'images/icon/arrowdown.gif',
							id:'DownBtn',
						    text:'����',
						    handler:DownBtn
						}),
						new Ext.Button({
							icon : 'images/icon/save.gif',
							id:'saveSortBtn',
						    text:'��������',
						    handler:function(){
								radow.doEvent('worksort');
						    }
						}),
						new Ext.Spacer({width:50}),
						new Ext.Button({
							icon : 'images/icon/save.gif',
							id:'sortUseTimeS',
						    text:'����ְʱ������',
						    handler:function(){
								radow.doEvent('sortUseTime');
						    }
						})]);
	
	*/
	
	
	
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
   
   
/* document.getElementById('a01y').style.display='none';
document.getElementById('a01m').style.display='none'; 
document.getElementById('a0215aSpanId').parentNode.style.display='none';
Ext.getCmp('a0215a_combo').hide();*/
//document.body.scrollTop=document.getElementById('zhiwugroup').offsetTop;

});
/* if(!parent.Ext.getCmp('A01SortGrid')){
	newWin({id:'A01SortGrid',title:'����������',modal:true,width:500,height:480,maximizable:true,parentWinObj:parent});
} */
function openSortWin(){
	var a0201b = document.getElementById("a0201b").value;
	if(a0201b==''){
		$h.alert('ϵͳ��ʾ��','����ѡ�����!');
		return;
	}
	realParent.window.a0201b = a0201b;
	$h.openPageModeWin('A01SortGrid','pages.publicServantManage.PersonSort','����������',650,450,document.getElementById('a0000').value,'<%=ctxPath%>',window);
}

/*
window.onscroll = function(){ 
    var t = document.documentElement.scrollTop || document.body.scrollTop; 
    var l = document.documentElement.scrollLeft || document.body.scrollLeft;   
    var save_div = document.getElementById( "btngroup3" ); 
    save_div.style.top = 385+t; 
    save_div.style.left = 830+l; 
} */
Ext.onReady(function(){
	
	
	document.getElementById("a0000").value = realParent.document.getElementById("a0000").value;
	
	$h.applyFontConfig($h.spFeildAll.a02);
	$h.applyFontConfig($h.spFeildAll.a01);
	if(realParent.buttonDisabled){
		$h.setDisabled($h.disabledButtons.a02);
		
		/* var cover_wrap1 = document.getElementById('cover_wrap1');
		var cover_wrap2 = document.getElementById('cover_wrap2');
		var cover_wrap3 = document.getElementById('cover_wrap3');
		var ext_gridobj = Ext.getCmp('WorkUnitsGrid');
		var gridobj = document.getElementById('forView_WorkUnitsGrid');
		var viewSize = Ext.getBody().getViewSize();
		var grid_pos = $h.pos(gridobj);
		
		cover_wrap1.className= "divcover_wrap";
		cover_wrap1.style.cssText="height:" + $h.pos(gridobj).top + "px;";
		
		cover_wrap2.className=  "divcover_wrap";
		cover_wrap2.style.cssText= "margin-top: " + (grid_pos.top + ext_gridobj.getHeight()) + "px;"+
		"height:" + (viewSize.height - (grid_pos.top + ext_gridobj.getHeight()))+"px;";
		
		
		cover_wrap3.className= "divcover_wrap";
		cover_wrap3.style.cssText= "margin-left: " + (grid_pos.left + ext_gridobj.getWidth())+"px;"+
		"width:" + (viewSize.width - (grid_pos.left + ext_gridobj.getWidth())) + "px;";  */
		//alert(Ext.getBody().getViewSize().height);
		//alert($h.pos(document.getElementById('forView_WorkUnitsGrid')).top);
		//alert(Ext.getCmp('WorkUnitsGrid').getWidth());
		//alert(Ext.getCmp('WorkUnitsGrid').getHeight()); 
	}
	
	
	Ext.getCmp('WorkUnitsGrid').setWidth(400);
	Ext.getCmp('WorkUnitsGrid').setHeight(250);
	
	document.getElementById('a0201bSpanId').innerHTML='<font color="red">*</font>��ְ����';
	//document.getElementById('a0219SpanId').innerHTML='<font color="red">*</font>�쵼ְ��';

    document.getElementById('a0192aSpanId').innerHTML='<font color="red">*</font>ȫ��';


    //�Ƿ����ε�����
    a0255SelChange();
    $(".radioItem").change(function () {
        var a0255 = $("input[name='a0255']:checked").val();
        if ("1" == a0255) {//����
            document.getElementById('yimian').style.visibility = "hidden";
        } else if ("0" == a0255) {//����
            document.getElementById('yimian').style.visibility = "visible";
        }
        document.getElementById('a0255').value = a0255;
    });

    //������ְ����ѡ�����
    document.getElementById('a0201b_combo').style.width = "190px";
    document.getElementById('a0201a').style.width = "207px";
    //����Ϣ����ϸ��Ȩ�޿��ƣ��Ƿ����ά��
    $h.fieldsDisabled(realParent.fieldsDisabled);
    //����Ϣ����ϸ��Ȩ�޿��ƣ��Ƿ���Բ鿴
    //var imgdata = "<img src='<%=request.getContextPath()%>/image/quanxian1.png' />";
	var imgdata = "url(<%=request.getContextPath()%>/image/suo.jpg)";
	$h.selectDisabled(realParent.selectDisabled,imgdata); 
	//a0195��������
	for(var i=0; i<realParent.fieldsDisabled.length; i++){
		if(realParent.fieldsDisabled[i]=='a0195'){
			if(formobj = Ext.getCmp(realParent.fieldsDisabled[i]+'_combo')){
				formobj.disable();
				var img = Ext.query("#"+realParent.fieldsDisabled[i]+"_combo+img")[0];
				//img.style.display="none";
				img.onclick=null;
				$('#'+realParent.fieldsDisabled[i]+'_combo').parent().removeClass('x-item-disabled');
				$('#'+realParent.fieldsDisabled[i]+'_combo').addClass('bgclor');
			}
		}
	}
	for(var i=0; i<realParent.selectDisabled.length; i++){
		if(realParent.selectDisabled[i]=='a0195'){
			if(formobj = Ext.getCmp(realParent.selectDisabled[i]+'_combo')){
				var div = document.createElement("div");
				div.style.border="1px solid rgb(192,192,192)";
				div.style.width = document.getElementById(realParent.selectDisabled[i]+'_combo').offsetWidth;
				div.style.height = document.getElementById(realParent.selectDisabled[i]+'_combo').offsetHeight;
				div.style.position = "absolute";
				div.style.left = $('#'+realParent.selectDisabled[i]+'_combo').offset().left+'px';
				div.style.top = $('#'+realParent.selectDisabled[i]+'_combo').offset().top+'px';
		��������div.style.backgroundImage = imgdata;
				div.style.backgroundRepeat = "no-repeat";
				div.style.backgroundColor = "white";
				div.style.backgroundPosition = "center";
		��������document.body.appendChild(div);
				
				
				formobj.disable();
				var img = Ext.query("#"+realParent.selectDisabled[i]+"_combo+img")[0];
				//img.style.display="none";
				img.onclick=null;
				$('#'+realParent.selectDisabled[i]+'_combo').parent().removeClass('x-item-disabled');
				$('#'+realParent.selectDisabled[i]+'_combo').addClass('bgclor');
			}
		}
	}
});


function changea0201d(type){
	if(type == '1'){
		//document.getElementById('a0201dSpanId').innerHTML='<font color="red">*</font>�쵼��Ա';
	}
	if(type == '2'){
		document.getElementById('a0201dSpanId').innerHTML='�쵼��Ա';
		document.getElementById('a0201eSpanId').innerHTML='��Ա���';
	}
}

Ext.onReady(function(){
	
   });  
   
   
   
odin.accCheckedForE3 = function(obj,rowIndex,colIndex,colName,gridId){
	<%
	  String data = TableColInterface.getAllUpdateData();
	  String info = "a02checkBoxColClick";
	  if(data==null||data.equals("")){
		  info = "a02checkBoxColClick";
	  }else{
		  data = data.replaceAll("'","");
		  String[] datas = data.split(",");
		  boolean flag = false;
		  for(String str:datas){
			  if(str.equals("a0277")||str.equals("a0201a")||str.equals("a0201b")||str.equals("a0201d")||str.equals("a0201e")||str.equals("a0215a")||str.equals("a0219")||str.equals("a0223")||str.equals("a0225")||str.equals("a0243")||str.equals("a0245")||str.equals("a0247")||str.equals("a0251b")||str.equals("a0255")||str.equals("a0265")||str.equals("a0267")||str.equals("a0272")||str.equals("a0281")||str.equals("a0222")||str.equals("a0279")||str.equals("a0504")||str.equals("a0517")){
				  info="-1";
				  break;
			  }
		  }
	  }
	  %>
	if (<%=info %>== "-1")
	{
		return;
	}

	if (obj.className == 'x-grid3-check-col') {
		if (typeof (gridId) == 'undefined' || (typeof (gridId) == 'string' && gridId == '')) {
			odin.checkboxds.getAt(rowIndex).set(colName, true);
		} else {
			odin.ext.getCmp(gridId).store.getAt(rowIndex).set(colName, true);
		}
		obj.className = 'x-grid3-check-col-on';
    } else {
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
	<%=info %>(rowIndex,colIndex,null,gridId);
	objs={'obj':obj};
	rowIndexs={'rowIndex':rowIndex};
	colIndexs={'colIndex':colIndex};
	colNames={'colName':colName};
	gridIds={'gridId':gridId};

};

function nosystem(){
	
}
var objs ={};
var rowIndexs ={};
var colIndexs ={};
var colNames ={};
var gridIds ={};
//���һ�β���box�仯
function changebox(){
	var obj=objs.obj;
	var rowIndex=rowIndexs.rowIndex;
	var colIndex=colIndexs.colIndex;
	var colName=colNames.colName;
	var gridId=gridIds.gridId;
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
}   

function lockINFO(){
	Ext.getCmp("WorkUnitsAddBtn").disable(); 
	Ext.getCmp("save").disable(); 
	Ext.getCmp("UpdateTitleBtn").disable(); 
	Ext.getCmp("personGRIDSORT").disable(); 
	Ext.getCmp("UpBtn").disable(); 
	Ext.getCmp("DownBtn").disable(); 
	Ext.getCmp("saveSortBtn").disable(); 
	Ext.getCmp("sortUseTimeS").disable(); 
	Ext.getCmp("WorkUnitsGrid").getColumnModel().setHidden(8,true);
}


function disableA0248(){
	var a0201e = document.getElementById("a0201e").value;
	if(a0201e=='Z'){
		$('#a0248').attr("checked", false).attr('disabled',true);
	}else{
		$('#a0248').attr('disabled',false);
	}
}

function disableA0277(){
	if(a0201e=='Z'){
		$('#a0277').attr("checked", false).attr('disabled',true);
	}else{
		$('#a0277').attr('disabled',false);
	}
}
</script>

<div id="cover_wrap1"></div>
<div id="cover_wrap2"></div>
<div id="cover_wrap3"></div>
