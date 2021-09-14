<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="com.insigma.siis.local.business.sysorg.org.CreateSysOrgBS"%>
<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.SysOrgPageModel"%>
<%@page import="com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_001.GroupManagePageModel"%>
<%@include file="/comOpenWinInit.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<%String ctxPath = request.getContextPath(); 

%>
<%
	String picType = (String) (new SysOrgPageModel().areaInfo
			.get("picType"));
	String ereaname = (String) (new SysOrgPageModel().areaInfo
			.get("areaname"));
	String ereaid = (String) (new SysOrgPageModel().areaInfo
			.get("areaid"));
	String manager = (String) (new SysOrgPageModel().areaInfo
			.get("manager"));
%>

<div>
<odin:hidden property="ereaname" value="<%=ereaname%>" />
<odin:hidden property="ereaid" value="<%=ereaid%>" />
<odin:hidden property="manager" value="<%=manager%>" />
<odin:hidden property="picType" value="<%=picType%>" />
<odin:hidden property="isList" value="0"/>


</div>

<div style="height:100%;width: 100%;" id="hideDiv">
<table style="width:100%;height:100%;">
	<tr>
		<odin:textEdit property="optionGroup" label="�ϼ���������" size="24" disabled="true"></odin:textEdit>
		<odin:textEdit property="parentb0114" label="�ϼ���������" size="24" disabled="true"></odin:textEdit>
	</tr>
</table>
<div style="width: 100%;height: 10%;">
<table style="width:100%;height:100%;">
	<tr align="left">
		<td class='type11'>
		<input type="radio" id ="b0194a" name="b0194" value="LegalEntity" checked="checked" onclick="Check1()">
		<label style="font-size: 12" >���˵�λ&nbsp;&nbsp;&nbsp;</label>
		</td>
		<td class='type22'>
		<input type="radio" id ="b0194b" name="b0194" value="InnerOrg" onclick="Check2()">
		<label style="font-size: 12" >�������&nbsp;&nbsp;&nbsp;</label>
		</td>
		<td class='type33'>
		<input type="radio" id ="b0194c" name="b0194" value="GroupOrg" onclick="Check3()">
		<label style="font-size: 12" >��������&nbsp;&nbsp;&nbsp;</label>
		</td>
	</tr>
</table>
</div>

<div id="Org" style="display:block;width: 100%;height: 90%;">
<div align="left" style="width: 100%;">
<table style="width: 100%;height: 60%;" border="0">
	<tr align="center" style="width: 100%;height: 100%;">
		<td valign="top" class='tr33' id='tr33' width="41%">
			<odin:groupBox title="������Ϣ">
			<div id='div11' style="height: 100%;">
			<div id='temp_div'>
			</div>
			<table border="0">
				<tr >
					<odin:textEdit property="b01a" label="��������" width="240" readonly="true"></odin:textEdit>
				</tr>
				<tr>
					<odin:textEdit property="b0114" label="��������" width="240" readonly="true"></odin:textEdit>
				</tr>
				<tr>
					<odin:textEdit property="b0101" label="��������" width="240" readonly="true"></odin:textEdit>
				</tr>
				<tr>
					<odin:textEdit property="b0104" label="��&nbsp;&nbsp;&nbsp;&nbsp;��" width="240" readonly="true"></odin:textEdit>
				</tr>
				<tr class='tr1'>
					<odin:select property="b0117" hideTrigger="true" label="��������" codeType ="zb01" codename="code_name" width="240" readonly="true"/>
				</tr>
				<tr class='tr1'>
					<odin:select property="b0124" hideTrigger="true" label="������ϵ" codeType="zb87" width="240" readonly="true"/>
				</tr>
				<tr class='tr1'>
					<odin:select property="b0131" hideTrigger="true" label="�������" codeType="zb04" width="240" readonly="true"/>
				</tr>
				<tr class='tr2'>
					<odin:select property="b0127" hideTrigger="true" label="��������" codeType="zb03" width="240" readonly="true"/>
				</tr>
			</table>
			</div>
			</odin:groupBox>
		</td>
		<td valign="top" class='tr4' id='tr4' height="100%" width="33%">
			<odin:groupBox title="ְ���������Ϣ">
			<div id='div22' style="height: 100%">
			<table>
				<tr>
					<td>&nbsp;</td>
					<td align="center"><label style="font-size: 12">Ӧ��</label></td>
					<td align="center"><label style="font-size: 12">ʵ��</label></td>
				</tr>
				<tr>
					<odin:textEdit property="b0183" label="��ְ�쵼ְ��" size="6" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="4" readonly="true"></odin:textEdit>
				</tr>
				<tr>
					<odin:textEdit property="b0185" label="��ְ�쵼ְ��" size="6" value="" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="4" readonly="true"></odin:textEdit>
				</tr>
				<tr>
					<odin:textEdit property="b0227" label="����������" size="6" value="" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="4" readonly="true"></odin:textEdit>
				</tr>
				<tr>
					<odin:textEdit property="b0232" label="��ҵ����(�ι�)" size="6" value="" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="4" readonly="true"></odin:textEdit>
				</tr>
				<tr>
					<odin:textEdit property="b0233" label="��ҵ����(�ǲι�)" size="6" value="" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="4" readonly="true"></odin:textEdit>
				</tr>
				<tr>
					<odin:textEdit property="b0236" label="��&nbsp;��&nbsp;��&nbsp;��&nbsp;��" size="6" value="" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="4" readonly="true"></odin:textEdit>
				</tr>
				<tr>
					<odin:textEdit property="b0234" label="��&nbsp;��&nbsp;��&nbsp;��&nbsp;��" size="6" value="" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="4" readonly="true"></odin:textEdit>
				</tr>
			</table>
			</div>
			</odin:groupBox>
		</td>
		<td valign="top" class='tr5' id='tr5' height="100%" width="26%">
			<odin:groupBox title="ְ����Ϣ">
			<div id='div33' style="height: 250px;">
			<table>
				<tr>
					<td>&nbsp;</td>
					<td align="center"><label style="font-size: 12" >Ӧ��</label></td>
					<td align="center"><label style="font-size: 12">ʵ��</label></td>
				</tr>
				<tr>
					<odin:textEdit property="b0150" label="�쵼ְ��" size="6" value="" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="10" readonly="true"></odin:textEdit>
				</tr>
				<tr>
					<odin:textEdit property="b0183_1" label="��&nbsp;&nbsp;&nbsp;&nbsp;ְ" size="6" value="" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="4" readonly="true"></odin:textEdit>
				</tr>
				<tr>
					<odin:textEdit property="b0185_1" label="��&nbsp;&nbsp;&nbsp;&nbsp;ְ" size="6" value="" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="4" readonly="true"></odin:textEdit>
				</tr>
			</table>
			</div>
			</odin:groupBox>
		</td>
	</tr>
</table>
<table style="width: 100%;height: 10%;">
	<tr>
		<td>&nbsp;</td>
	</tr>
	<tr class="tr3">
		<td>&nbsp;</td>
		<td align="right">
			<label style="font-size: 12" id="b0238lab" >���չ���Ա����������ʱ��&nbsp;</label>
		</td>
			<odin:NewDateEditTag property="b0238" readonly="true" isCheck="true" maxlength="8"></odin:NewDateEditTag>
		<td width="60">&nbsp;</td>
		<td align="right">
			<label style="font-size: 12" id="b0239lab" >���չ���Ա�����������ĺ�&nbsp;</label>
		</td>
			<odin:textEdit property="b0239" readonly="true" width="190"  ></odin:textEdit>
	</tr>	
	<tr class="tr3">
		<td>&nbsp;</td>
	</tr>
</table>

<div align="center" style="width: 100%;">
	<label id="lab" style="width: 8%;font-size: 12;position: relative;top: -15px;">&nbsp;��ע��Ϣ</label>
	<textarea rows="3" cols="140" id="b0180" name="b0180" title="���뱸ע��Ϣ" style="-moz-opacity:1;opacity:1;width: 90%;background:EBEBE4;border:1px solid;border-color:#b5b8c8;" readonly="true"></textarea>
</div>
</div>
<odin:hidden property="sb0192"/>
<odin:hidden property="sb0193"/>
<%-----------------------------�Զ�����Ϣ��-------------------------------------------------------%>
<%
Map<String, List<Object[]>> os_list = CreateSysOrgBS.getB01Ext();
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
				if(code_type!=null){
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
			</div>
			</div>
			
<script type="text/javascript">

function myfunction(opType,radioType){
	var opType = opType;
	var radioType = radioType;
	if(opType==1){
		document.getElementById("b0194a").checked = 'checked';
		document.getElementById("b0194a").disabled=false; 
		document.getElementById("b0194b").disabled=false; 
		document.getElementById("b0194c").disabled=false; 
		Check1();
//��ʾ
	}else{
		if(radioType==1){
			document.getElementById("b0194a").checked = 'checked';
			document.getElementById("b0194b").disabled=false; 
			document.getElementById("b0194c").disabled=false; 
			Check1();
		}else if(radioType==2){
			document.getElementById("b0194a").disabled=false; 
			document.getElementById("b0194b").checked = 'checked';
			document.getElementById("b0194c").disabled=false; 
			Check2();
		}else if(radioType==3){
			document.getElementById("b0194a").disabled=false; 
			document.getElementById("b0194b").disabled=false; 
			document.getElementById("b0194c").checked = 'checked';
			Check3();
		}else{
			document.getElementById("b0194a").checked = 'checked';
			document.getElementById("b0194a").disabled=false; 
			document.getElementById("b0194b").disabled=false; 
			document.getElementById("b0194c").disabled=false; 
			Check1();
		}
	}
}
function Check1(){
	$(".tr1").show();
	$(".tr2").show();
	$(".tr3").hide();
	$(".tr4").show();
	$(".tr5").hide();
	$('#div11').css('height','250px'); 
	$('#div22').css('height','250px'); 
	$('#div33').css('height','250px');
	$('#temp_div').show();
	var b0238tt = $('#b0238').val();
	if(b0238tt.length>5){
		$(".tr3").show();
	}else{
		$(".tr3").hide();
	}
	document.getElementById("b01a").value='���˵�λ';
	b0131func("");
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
function Check2(){

	
	$(".tr1").hide();
	$(".tr2").show();
	$(".tr4").hide();
	$(".tr5").show();
	$('#div11').css('height','150px'); 
	$('#div22').css('height','150px'); 
	$('#div33').css('height','150px'); 
	$('#temp_div').show();

	var b0238tt = $('#b0238').val();
	if(b0238tt.length>5){
		$(".tr3").show();
	}else{
		$(".tr3").hide();
	}
	$(".tr3").hide();
		document.getElementById("b01a").value='�������';
}
function Check3(){ 

	
	
	$(".tr1").hide();
	$(".tr2").hide();
	$(".tr4").hide();
	$(".tr5").hide();
	$('#div11').css('height','120px'); 
	$('#div22').css('height','120px'); 
	$('#div33').css('height','120px'); 
	$('#temp_div').hide();

	$(".tr3").hide();
	document.getElementById("b01a").value='��������';
}
</script>