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
<script type="text/javascript">
/*  function getParentUnitId(){//��ȡ��ҳ�汻ѡ�е����صĵ�λid
	 //checkedgroupid
	 //���ظ�ҳ��ѡ�еĵ�λid����ҳ��
	 document.getElementById("checkedgroupid").value=window.parent.document.getElementById("checkedgroupid").value;
 	//ҳ���ʼ��
 	radow.doEvent("initX");
 } */
$(function(){  
	$("#b0114SpanId").prepend("<font color='red'>*</font>");
	$("#b0101SpanId").prepend("<font color='red'>*</font>");
	/* $("#b0104SpanId").prepend("<font color='red'>&nbsp;&nbsp;&nbsp;*</font>"); */
});
//add zepeng 20180418 Ԫ�ؽ϶࣬�������Ⱦ���ε�������������Org ext.onready��ʱ������ʾ
	Ext.onReady(function() {
		Check1();
		$("#Org").show();
	});
	
function myfunction(radioType){
	try{
		var radioType = radioType;
		if(radioType==1){
			$("#b0194a").attr("disabled", false);
			$("#b0194b").attr("disabled", false);
			$("#b0194c").attr("disabled", false);
		}else if(radioType==2){
			$("#b0194a").attr("disabled", true);
			$("#b0194b").attr("disabled", false);
			$("#b0194c").attr("disabled", true);
		}else if(radioType==3){
			$("#b0194a").attr("disabled", false);
			$("#b0194b").attr("disabled", false);
			$("#b0194c").attr("disabled", false);
		}
	}catch(err){
		//alert("myfunction");
	}
}

function Check1(){
	try{
		$(".tr1").show();
		$(".tr2").show();
	//	$(".tr3").show();
		$(".tr3").hide();
		$(".tr4").show();
		$(".tr5").hide();
		$('#div11').css('height','240px'); 
		$('#div22').css('height','240px'); 
		$('#div33').css('height','240px');
		$(".td11").show();
		$(".td22").show();
		$(".td22").show();
		$("#b0117SpanId").html("<font color='red'>*</font>��������");
		$("#b0131SpanId").html("<font color='red'>*</font>�������");
		$("#b0127SpanId").html("<font color='red'>*</font>��������");
		$("#b0124SpanId").html("<font color='red'>*</font>������ϵ");
	}catch(err){
		//alert("Check1");
	}
}
function Check2(){ 
	try{
		$(".tr1").hide();
		$(".tr2").show();
	//	$(".tr3").show();
		$(".tr3").hide();
		$(".tr4").hide();
		$(".tr5").show();
		$('#div11').css('height','120px'); 
		$('#div22').css('height','120px'); 
		$('#div33').css('height','120px'); 
		$(".td11").hide();
		$(".td22").show();
		$("#b0117SpanId").html("��������");
		$("#b0131SpanId").html("�������");
		$("#b0127SpanId").html("��������");
		$("#b0124SpanId").html("������ϵ");
	}catch(err){
		//alert("Check2");
	}
}
function Check3(){ 
	try{
		$(".tr1").hide();
		$(".tr2").hide();
		$(".tr3").hide();
		$(".tr4").hide();
		$(".tr5").hide();
		$('#div11').css('height','90px'); 
		$('#div22').css('height','90px'); 
		$('#div33').css('height','90px'); 
		$(".td11").hide();
		$(".td22").hide();
		$("#b0117SpanId").html("��������");
		$("#b0131SpanId").html("�������");
		$("#b0127SpanId").html("��������");
		$("#b0124SpanId").html("������ϵ");
	}catch(err){
		//alert("Check3");
	}
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
function clearfunc(){
	$("#b0238").attr("value","");
	$("#b0238_1").attr("value","");
	$("#b0239").attr("value","");
	$("#b0239_1").attr("value","");
}
</script>
<odin:hidden property="orifileid" title="���� id"  value="1"></odin:hidden>
<odin:hidden property="orifileid2" title="���� id2"  value="1"></odin:hidden>
<odin:hidden property="orifileid3" title="���� id3"  value="1"></odin:hidden>
<table style="height: 5%">
	<tr>
		<odin:textEdit property="optionGroup" label="�ϼ���������" size="24" disabled="true"></odin:textEdit>
		<odin:textEdit property="parentb0114" label="�ϼ���������" size="24" disabled="true"></odin:textEdit>
		<odin:textEdit property="b0121" size="24" style="display:none" disabled="true"></odin:textEdit>
		<odin:hidden property="b0111"/>
		<odin:hidden property="b0101old"/>
		<odin:hidden property="b0104old"/>
		<odin:hidden property="isstauts" value="0"/>
		<odin:hidden property="checkedgroupid" /><!-- ��ҳ��ѡ�еĵ�λid -->
	</tr>
</table>

<table style="height: 5%">
	<tr>
		<td width="350px;">&nbsp;</td>
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

<div id="Org" style="display:none;height: 85%;">
<table style="width: 100%">
	<tr align="center">
		<td valign="top" width="20%">
			<odin:groupBox title="������Ϣ">
			<div id='div11' style="height: 250px;">
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
		
		<td valign="top" class='tr4' width="20%">
		<odin:groupBox title="ְ����Ϣ">
		<div id='div22' style="height: 250px;">
<table><tr><td>
		<table>			
		       <tr>
		          <td align="center"><label style="font-size: 12" ></label></td>
		          <td align="center"><label style="font-size: 12" >Ӧ��</label></td>
		          <td align="center"><label style="font-size: 12" >ʵ��</label></td>
		       </tr>
			   <tr >
					<odin:textEdit property="b0183" label="��ְ�쵼ְ��" size="8" value="0" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="5"></odin:textEdit>
					<odin:textEdit property="y0183_1"  size="8" value="0" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="5" readonly="true"></odin:textEdit>
				</tr>
				<tr >
					<odin:textEdit property="b0185" label="��ְ�쵼ְ��" size="8" value="0" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="5"></odin:textEdit>
					<odin:textEdit property="y0185_1"  size="8" value="0" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="5" readonly="true"></odin:textEdit>
				</tr>
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
				<%-- <tr >
					<odin:textEdit property="b0250" label="��ְ" size="8" value="0" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="5"></odin:textEdit>
					<odin:textEdit property="b0260"  size="8" value="0" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="5" ></odin:textEdit>
				</tr> --%>
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
		      <!--  <tr>
		          <td align="center"><label style="font-size: 12" ></label></td>
		          <td align="center"><label style="font-size: 12" >Ӧ��</label></td>
		          <td align="center"><label style="font-size: 12" >ʵ��</label></td>
		       </tr>
				
				<tr ><td colspan="4" height="56">&nbsp;</td></tr> -->
		</table>	
</td></tr></table>		
			</div>
			</odin:groupBox>
		</td>	
		
					
		<td valign="top" class='tr4' width="20%">
			<odin:groupBox title="������Ϣ">
			<div id='div22' style="height: 240px;">
			
			<table>
				
				<tr >
				<odin:textarea property="b0240" style="width:300px; height:60px;"  label="�����䱸"   maxlength="500"></odin:textarea>
				</tr>
				<tr>
				<odin:textarea property="b0241" style="width:300px; height:60px;" label="ʵ���䱸"   maxlength="500"></odin:textarea>
				</tr>
				<tr>				
				 <td colspan="2">
	              <iframe id="frame1"  name="frame1" height="100px" width="400px" src="<%=request.getContextPath() %>/pages/sysorg/org/CreateFile.jsp" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes"></iframe>
		        </td>
				</tr>				 								
			</table>			
			</div>
			</odin:groupBox>
		</td>
		<td valign="top" class='tr5' width="26%">
			<odin:groupBox title="ְ����Ϣ">
			<div id='div33' style="height: 250px;">
			<table>
			     <tr>
		          <td align="center"><label style="font-size: 12" ></label></td>
		          <td align="center"><label style="font-size: 12" >Ӧ��</label></td>
		          <td align="center"><label style="font-size: 12" >ʵ��</label></td>
		       </tr>
				<tr>
					<odin:textEdit property="b0150" label="�쵼ְ��" size="6" value="0" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="10"></odin:textEdit>
					<odin:textEdit property="yp0150"  size="6" value="0" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="10" readonly="true"></odin:textEdit>
				</tr>
				<tr>
					<%-- <odin:textEdit property="b0190" label="��&nbsp;&nbsp;&nbsp;&nbsp;ְ" size="6" value="0" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="4"></odin:textEdit> --%>
					<odin:textEdit property="b0183_1" label="��&nbsp;&nbsp;&nbsp;&nbsp;ְ" size="6" value="0" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="4"></odin:textEdit>
					<odin:textEdit property="yp0183_1"  size="6" value="0" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="4" readonly="true"></odin:textEdit>
				</tr>
				<tr>
					<%-- <odin:textEdit property="b0191a" label="��&nbsp;&nbsp;&nbsp;&nbsp;ְ" size="6" value="0" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="4"></odin:textEdit> --%>
					<odin:textEdit property="b0185_1" label="��&nbsp;&nbsp;&nbsp;&nbsp;ְ" size="6" value="0" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="4"></odin:textEdit>
					<odin:textEdit property="yp0185_1"  size="6" value="0" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="4" readonly="true"></odin:textEdit>
				</tr>
			</table>
			</div>
			</odin:groupBox>
		</td>
	</tr>
</table>
<table style="width: 100%" class="tr3" >
	<tr align="center">
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
	<textarea rows="3" cols="140" id="b0180" name="b0180" title="���뱸ע��Ϣ" maxlength="500" style="width: 90%;border:1px solid;border-color:#b5b8c8;"></textarea>
</div>
<table style="width:100%" border = "0">
	<tr height="6px"></tr>
	<col width="30%">
	<col width="10%">
	<col width="5%">
	<col width="10%">
	<col width="5%">
	<col width="10%">
	<col width="30%">
	<tr id="tr3">
	<td></td>
	<td>
		<odin:button property="LegalEntityContinueAddBtn" text="��������"></odin:button>
	</td>
	<td></td>
	<td>
		<odin:button property="LegalEntitySaveBtn" text="&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;"></odin:button>
	</td>
	<td></td>
	<td>
		<odin:button property="LegalEntityCancelBtn" text="&nbsp;&nbsp;��&nbsp;&nbsp;&nbsp;��&nbsp;&nbsp;"></odin:button>
	</td>
	<td></td>
	</tr>
</table>

</div>
<odin:window src="/blank.htm" id="updateNameWin" width="400" height="290"
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


</script>
