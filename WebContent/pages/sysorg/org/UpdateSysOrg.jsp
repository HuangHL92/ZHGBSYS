<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="com.insigma.siis.local.business.sysorg.org.CreateSysOrgBS"%>
<%@include file="/comOpenWinInit.jsp" %>
<script  type="text/javascript" src="<%=request.getContextPath()%>/basejs/jquery/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="js/lengthValidator.js"></script>
<style>
<!--
body {
	overflow: hidden;
}
-->
</style>
<%
String unitidDbclAlter=null;
try{
	unitidDbclAlter=(String)request.getSession().getAttribute("unitidDbclAlter");
	if(unitidDbclAlter==null||unitidDbclAlter.length()==0){
		unitidDbclAlter="";
	}else{
		//request.getSession().setAttribute("unitidDbclAlter", "");//�����ֽڿ��ܹ��� ��� ������,��մ���ˢ��ʱ�ᱨ��
	}
}catch(Exception e){
	e.printStackTrace();
}

%>
<script type="text/javascript">

$(function(){  
	$("#b0114SpanId").prepend("<font color='red'>*</font>");
	$("#b0101SpanId").prepend("<font color='red'>*</font>");
	/* $("#B0104SPANID").PREPEND("<FONT COLOR='RED'>&NBSP;&NBSP;&NBSP;*</FONT>"); */
});

function consoleSaveBtn(){
	//�жϸ�ҳ���Ƿ����޸İ�ť����û�У���ѱ��水ť����
	var updateBtn = realParent.Ext.getCmp('updateWinBtn');
	//alert(updateBtn.hidden);
	if(updateBtn.hidden){
		//Ext.getCmp('save').hide();
		$("#save").attr("disabled", true);
	}
	
}

function saveb01(){
	radow.doEvent('sysorg.save');
}
function myfunction(opType,radioType){
	var opType = opType;
	var radioType = radioType;
	if(radioType==1){
		
	}else if(radioType==2){
		$("#b0194a").attr("disabled", true);
		$("#b0194c").attr("disabled", true);
	}else if(radioType==3){
		$("#b0194b").attr("disabled", true);
	}
}
 
function Check1(){
		$(".tr1").show();
		$(".tr2").show();
		//$(".tr3").show();
		$(".tr3").hide();
		$(".tr4").show();
		$(".tr5").hide();
		$('#div11').css('height','250px'); 
		$('#div22').css('height','250px'); 
		$('#div33').css('height','250px');
		$(".td11").show();
		$(".td22").show();
		$(".td22").show();
		$("#b0117SpanId").html("<font color='red'>*</font>��������");
		$("#b0131SpanId").html("<font color='red'>*</font>�������");
		$("#b0127SpanId").html("<font color='red'>*</font>��������");
		$("#b0124SpanId").html("<font color='red'>*</font>������ϵ");
		b0131func("");
	}
function Check2(){ 
	$(".tr1").hide();
	$(".tr2").show();
	//$(".tr3").show();
	$(".tr3").hide();
	$(".tr4").hide();
	$(".tr5").show();
	$('#div11').css('height','150px'); 
	$('#div22').css('height','150px'); 
	$('#div33').css('height','150px'); 
	$(".td11").hide();
	$(".td22").show();
	$("#b0117SpanId").html("��������");
	$("#b0131SpanId").html("�������");
	$("#b0127SpanId").html("��������");
	$("#b0124SpanId").html("������ϵ");
}
function Check3(){ 
	$(".tr1").hide();
	$(".tr2").hide();
	$(".tr3").hide();
	$(".tr4").hide();
	$(".tr5").hide();
	$('#div11').css('height','80px'); 
	$('#div22').css('height','80px'); 
	$('#div33').css('height','80px'); 
	$(".td11").hide();
	$(".td22").hide();
	$("#b0117SpanId").html("��������");
	$("#b0131SpanId").html("�������");
	$("#b0127SpanId").html("��������");
	$("#b0124SpanId").html("������ϵ");
}
function b0131func(record){
	//var code=record.data.key;
	var code=$("#b0131").attr("value");
	if(code=='411'||//���չ���Ա�������ҵ��λ(������)
		code=='412'||//���չ���Ա�������ҵ��λ(����һ��)
		code=='413'||//���չ���Ա�������ҵ��λ(�������)	
		code=='419'){//���չ���Ա���������ҵ��λ��δ����)
		$(".tr3").show();
		//clearfunc();
	}else{
		$(".tr3").hide();
		clearfunc();
	}
}
function setSuccess(b0111){
	window.realParent.queryGroupByUpdate(b0111);
	alert("�޸ĳɹ�");
}
function clearfunc(){
	$("#b0238").attr("value","");
	$("#b0238_1").attr("value","");
	$("#b0239").attr("value","");
	$("#b0239_1").attr("value","");
}
var flag3=true;
var units=parent.Ext.getCmp(subWinId).initialConfig.param;//��ȡ����id//�Ҽ�����޸�ʱ����Ļ��� ����ʹ��
var unitidDbclAlter='<%= unitidDbclAlter%>';//���˫��������޸İ�ť����Ļ��� ���Ҽ��޸�
var unitarr='';//ת������
var mount=1;//��ǰ����λ��
function initpage(num){
	if(units==''){
		 units=unitidDbclAlter;//��ȡ����id
	}
	if(unitarr==''){
		unitarr=units.split(",");//ת������
	}
	if(unitarr[0]=='2'){//��������޸İ�ť
		
	}else{//˫�������б�
		if(flag3==true){//
			num=locationDbClUnit();
			flag3=false;
		}
	}
	if(unitarr.length==2){//����һ������
		//������һ����ť���ɱ༭
		radow.doEvent("nextsno");
		//������һ����ť���ɱ༭
		radow.doEvent("nextxno");
		mount=num;
		document.getElementById('b0111').value=unitarr[parseInt(num)];//���ص�ǰ����id��ҳ��
		radow.doEvent("nextUnit",unitarr[parseInt(num)]);//��ʼ��ҳ��
		return;
	}
	if(mount==1){//�Ѿ��ǵ�һ��
		//������һ����ť���ɱ༭
		radow.doEvent("nextsno");
	}else{
		//������һ����ť�ɱ༭
		radow.doEvent("nextsyes");
	}
	if(mount==unitarr.length-1){//�Ѿ������һ��
		//������һ����ť���ɱ༭
		radow.doEvent("nextxno");
	}else{
		//������һ����ť�ɱ༭
		radow.doEvent("nextxyes");
	}
	mount=num;
	document.getElementById('b0111').value=unitarr[parseInt(num)];//���ص�ǰ����id��ҳ��
	radow.doEvent("nextUnit",unitarr[parseInt(num)]);//��ʼ��ҳ��
}
//��һ��
function nexts(){
	if(mount==1){
		return;
	}
	mount=parseInt(mount)-1;
	initpage(mount);
}
//��һ��
function nextx(){
	if(mount==unitarr.length-1){
		return;
	}
	mount=parseInt(mount)+1;
	initpage(mount);
}
//��ȡ˫���ĵ�λid�����ڵ�λ��
function locationDbClUnit(){
	var DbClUnit=unitarr[0];
	var num=0;
	for(var i=1;i<unitarr.length;i++){
		if(DbClUnit==unitarr[i]){
			num=i;
			mount=num;
			return num;
		}
	}
}
</script>
<style>

</style>

<odin:hidden property="orifileid" title="���� id"  value="1"></odin:hidden>
<odin:hidden property="orifileid2" title="���� id2"  value="1"></odin:hidden>
<odin:hidden property="orifileid3" title="���� id3"  value="1"></odin:hidden>
<odin:hidden property="filename1" ></odin:hidden>
<odin:hidden property="filename2" ></odin:hidden>
<odin:hidden property="filename3" ></odin:hidden>
<odin:hidden property="htmlx" ></odin:hidden>
<odin:hidden property="htmly" ></odin:hidden>
<odin:hidden property="htmlz" ></odin:hidden>

<table style="height: 5%">
	<tr>
		<odin:textEdit property="optionGroup" label="�ϼ���������" size="24" disabled="true"></odin:textEdit>
		<odin:textEdit property="parentb0114" label="�ϼ���������" size="24" disabled="true"></odin:textEdit>
	</tr>
</table>

<table style="height: 5%">
	<tr>
		<td width="380px;">&nbsp;</td>
		<td>
		<input type="radio" id ="b0194a" name="b0194" value="LegalEntity" checked="checked" onclick="Check1()">
		<label style="font-size: 12" >���˵�λ&nbsp;&nbsp;&nbsp;</label>
		</td>
		<td width="60px;">&nbsp;</td>
		<td>
		<input type="radio" id ="b0194b" name="b0194" value="InnerOrg" onclick="Check2()">
		<label style="font-size: 12" >�������&nbsp;&nbsp;&nbsp;</label>
		</td>
		<td width="60px;">&nbsp;</td>
		<td>
		<input type="radio" id ="b0194c" name="b0194" value="GroupOrg" onclick="Check3()">
		<label style="font-size: 12" >��������&nbsp;&nbsp;&nbsp;</label>
		</td>
	</tr>
</table>

<div id="Org" style="display:block;height: 85%;">
<div align="center" style="width: 100%">
<table style="width: 100%">
	<tr align="center">
		<td valign="top" width="20%">
			<odin:groupBox title="������Ϣ">
			<div id='div11' >
			<table>
				<tr>
					<odin:textEdit property="b0114" validator="b0114Length"  width="240" label="��������"></odin:textEdit>
				</tr>
				<tr>
					<odin:textEdit property="b0101"  validator="b0101Length"  width="240" label="��������" ></odin:textEdit>
				</tr>
				<tr>
					<odin:textEdit property="b0104"  validator="b0104Length" width="240" label="��&nbsp;&nbsp;&nbsp;&nbsp;��" ></odin:textEdit>
				</tr>
				<tr class='tr1'>
					<tags:PublicTextIconEdit property="b0117" label="��������" width="240" codetype="ZB01" codename="code_name" maxlength="8" readonly="true" />
				</tr>
				<tr class='tr1'>
					<tags:PublicTextIconEdit property="b0124" label="������ϵ" width="240" codetype="ZB87" maxlength="8" readonly="true"/>
				</tr>
				<tr class='tr1'>
					<tags:PublicTextIconEdit property="b0131" label="�������" codetype="ZB04" width="240" maxlength="8" readonly="true" onchange="b0131func"/>
				</tr>
				<tr class='tr2'>
					<tags:PublicTextIconEdit property="b0127" label="��������" codetype="ZB03" width="240" maxlength="8" readonly="true"/>
				</tr>
			</table>
			</div>
			</odin:groupBox>
		</td>
		
		<td valign="top" class='tr4' width="25%">
			<odin:groupBox title="ְ����Ϣ">
			<div id='div22'>
<table><tr><td>
			<table>
			    <tr>
		          <td align="center"><label style="font-size: 12" ></label></td>
		          <td align="center"><label style="font-size: 12" >Ӧ��</label></td>
		          <td align="center"><label style="font-size: 12" >ʵ��</label></td>
		       </tr>
			   <tr >
					<odin:textEdit property="b0183" label="��ְ�쵼ְ��" size="8" value="0" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="5"></odin:textEdit>
					<odin:textEdit property="yp0183_1"  size="8" value="0" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="5" readonly="true"></odin:textEdit>
				</tr>
				<tr >
					<odin:textEdit property="b0185" label="��ְ�쵼ְ��" size="8" value="0" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="5"></odin:textEdit>
					<odin:textEdit property="yp0185_1"  size="8" value="0" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="5" readonly="true"></odin:textEdit>
				</tr>
				<!-- <tr>
		          <td align="center"><label style="font-size: 12" ></label></td>
		          <td align="center"><label style="font-size: 12" >Ӧ��</label></td>
		          <td align="center"><label style="font-size: 12" >ʵ��</label></td>
		       </tr> -->
				<tr >
					<odin:textEdit property="b0246" label="��ʦ" size="8" value="0" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="5"></odin:textEdit>
					<odin:textEdit property="b0256"  size="8" value="0" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="5" ></odin:textEdit>
				</tr>
				<tr >
					<odin:textEdit property="b0247" label="����ְ��" size="8" value="0" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="5"></odin:textEdit>
					<odin:textEdit property="b0257"  size="8" value="0" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="5" ></odin:textEdit>
				</tr>
				<tr >
					<odin:textEdit property="b0248" label="����ְ��" size="8" value="0" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="5"></odin:textEdit>
					<odin:textEdit property="b0258"  size="8" value="0" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="5" ></odin:textEdit>
				</tr>
				<tr >
					<odin:textEdit property="b0249" label="����ְ��" size="8" value="0" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="5"></odin:textEdit>
					<odin:textEdit property="b0259"  size="8" value="0" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="5"  ></odin:textEdit>
				</tr>
				<tr >
					<odin:textEdit property="b0234" label="��ȱ��ְ" size="8" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="5"></odin:textEdit>
					<td></td>
				</tr>
				<tr >
					<odin:textEdit property="b0235" label="��ȱ��ְ" size="8" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="5"></odin:textEdit>
					<td></td>				
				</tr>
				<%-- 
				<tr >
					<odin:textarea property="b0236" colspan="4" rows="2" label="��ȱ��λ" readonly="true" onclick="openQGXX()"></odin:textarea>				
				</tr>--%>
				<%-- <tr >
					<odin:textEdit property="b0227" label="����������" size="8" value="0" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="5"></odin:textEdit>
					<odin:textEdit property="y0227_1"  size="8" value="0" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="5" readonly="true"></odin:textEdit>
				</tr>
				<tr >
					<odin:textEdit property="b0232" label="��ҵ����(�ι�)" size="8" value="0" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="5"></odin:textEdit>
					<odin:textEdit property="y0232_1"  size="8" value="0" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="5" readonly="true"></odin:textEdit>
				</tr>
				<tr >
					<odin:textEdit property="b0233" label="��ҵ����(�ǲι�)" size="8" value="0" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="5"></odin:textEdit>
					<odin:textEdit property="y0233_1"  size="8" value="0" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="5" readonly="true"></odin:textEdit>
				</tr>
				<tr >
					<odin:textEdit property="b0236" label="��&nbsp;��&nbsp;��&nbsp;��&nbsp;��" size="8" value="0" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="5"></odin:textEdit>
					<odin:textEdit property="y0236_1"  size="8" value="0" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="5" readonly="true"></odin:textEdit>
				</tr>
				<tr >
					<odin:textEdit property="b0234" label="��&nbsp;��&nbsp;��&nbsp;��&nbsp;��" size="8" value="0" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="5"></odin:textEdit>
					<odin:textEdit property="y0234_1"  size="8" value="0" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="5" readonly="true"></odin:textEdit>
				</tr> 		 --%>						
			</table>
</td><td>		
		<table>			
		       
				<%-- <tr >
					<odin:textEdit property="b0250" label="��ְ" size="8" value="0" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="5"></odin:textEdit>
					<odin:textEdit property="b0260"  size="8" value="0" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="5" ></odin:textEdit>
				</tr> --%>
				<tr ><td colspan="4" height="56">&nbsp;</td></tr>
		</table>	
</td></tr></table>	
			</div>
			</odin:groupBox>
		</td>		
		<td valign="top" class='tr4' width="20%">
			<odin:groupBox title="������Ϣ">
			<div id='div22'>
			<table>				
				<tr >
				<odin:textarea property="b0240" style="width:300px; height:60px;"  label="�����䱸"   maxlength="500"></odin:textarea>
				</tr>
				<tr>
				<odin:textarea property="b0241" style="width:300px; height:60px;" label="ʵ���䱸"   maxlength="500"></odin:textarea>
				</tr>
				<tr>				
				 <td colspan="2">
	              <iframe id="frame1"  name="frame1" height="115px" width="400px" src="<%=request.getContextPath() %>/pages/sysorg/org/UpdateFile.jsp" frameborder=��no�� border=��0�� marginwidth=��0�� marginheight=��0�� scrolling=��no�� allowtransparency=��yes��></iframe>
		        </td>
				</tr>				 								
			</table>			
			</div>
			</odin:groupBox>
		</td>
		<td valign="top" class='tr5' width="26%">
			<odin:groupBox title="ְ����Ϣ">
			<div id='div33'>
			<table>
				<tr>
					<td align="center"><label style="font-size: 12" ></label></td>
					<td align="center"><label style="font-size: 12" >Ӧ��</label></td>
					<td align="center"><label style="font-size: 12">ʵ��</label></td> 
				</tr>
				<tr>
					<odin:textEdit property="b0150" label="�쵼ְ��" size="15" value="0" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="10"></odin:textEdit>
					<odin:textEdit property="y0150"  size="15" value="0" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="10" readonly="true"></odin:textEdit>
				</tr>
				<tr>
					<odin:textEdit property="b0183_1" label="��&nbsp;&nbsp;&nbsp;&nbsp;ְ" size="15" value="0" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="4"></odin:textEdit>
					<odin:textEdit property="y0183"  size="15" value="0" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="4" readonly="true"></odin:textEdit>
				</tr>
				<tr>
					<odin:textEdit property="b0185_1" label="��&nbsp;&nbsp;&nbsp;&nbsp;ְ" size="15" value="0" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="4"></odin:textEdit>
					<odin:textEdit property="y0185"  size="15" value="0" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="4" readonly="true"></odin:textEdit>
				</tr>
			</table>
			</div>
			</odin:groupBox>
		</td>
	</tr>
</table>
<table style="width: 100%" class="tr3">
	<tr  align="center">
		<td>
			<label style="font-size: 12" id="b0238lab" >���չ���Ա����������ʱ��&nbsp;</label>
		</td>
			<odin:NewDateEditTag property="b0238" isCheck="true" maxlength="8"></odin:NewDateEditTag>
		<td>
			<label style="font-size: 12" id="b0239lab" >���չ���Ա�����������ĺ�&nbsp;</label>
		</td>

			<odin:textEdit property="b0239" validator="b0239Length" width="190" ></odin:textEdit>
	</tr>
</table>
<div align="center" style="width: 100%;">
	<label id="lab" style="width: 8%;font-size: 12;position: relative;top: -15px;">&nbsp;��ע��Ϣ</label>
	<textarea rows="3" cols="140" id="b0180" name="b0180" title="���뱸ע��Ϣ" style="-moz-opacity:1;opacity:1;width: 90%;border:1px solid;border-color:#b5b8c8;"></textarea>
</div>
<table style="width:100%" border="0">
	<tr height="12px"></tr>
	<col width="30%">
	<col width="8%">
	<col width="3%">
	
	<col width="8%">
	<col width="3%">
	<col width="8%">
	
	<col width="3%">
	<col width="8%">
	<col width="30%">
	<tr id="tr3" align="right">
		<td></td>
		<td>
			<odin:button property="nexts" text="��һ��" handler="nexts"></odin:button>
		</td>
		<td></td>
		<td>
			<odin:button property="nextx" text="��һ��" handler="nextx"></odin:button>
		</td>
		<td></td>
		<td>
			<odin:button property="save" text="&nbsp;��&nbsp;&nbsp;��&nbsp;"></odin:button>
		</td>
		<td></td>
		<td>
			<odin:button property="closeBtn" text="&nbsp;��&nbsp;&nbsp;��&nbsp;"></odin:button>
		</td>
		<td></td>
	</tr>
</table>
</div>
</div>
<odin:hidden property="b0192"/>
<odin:hidden property="b0193"/>
<odin:hidden property="b0111"/>
<odin:hidden property="b0121"/>
<odin:hidden property="b0101old"/>
<odin:hidden property="b0104old"/>
<odin:hidden property="isstauts" value="0"/>
<%-- <odin:hidden property="unitidDbclAlter"/> --%>
<odin:window src="/blank.htm" id="updateNameWin" width="400" height="320"
	title="��Ա������λ��ְ����Ϣ������ʾ" modal="true"></odin:window>
	
<script type="text/javascript">

function scfj1(){
    
	var xfoid=document.getElementById('orifileid').value;
	var xfoid2=document.getElementById('orifileid2').value;
	var xfoid3=document.getElementById('orifileid3').value;
	
	//alert(xfoid);
	//alert(xfoid2);
	//alert(xfoid3);
	
	frame1.window.imp(xfoid,xfoid2,xfoid3);
}
function init(){
    var htmlx = document.getElementById('htmlx').value;
    var htmly = document.getElementById('htmly').value;
    var htmlz = document.getElementById('htmlz').value;
	
	frame1.window.ht(htmlx,htmly,htmlz);
}

var g_contextpath = '<%= request.getContextPath() %>';
function openQGXX(){
	$h.openPageModeWin('GWQP','pages.sysorg.org.BGWQP','ȱ����Ϣ',630,350,'',g_contextpath);
}

</script>
