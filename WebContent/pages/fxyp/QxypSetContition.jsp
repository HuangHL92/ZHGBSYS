<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@include file="/comOpenWinInit2.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comboxWithTree.js"></script>



<html style="background-color: rgb(223,232,246);">
<meta http-equiv="X-UA-Compatible"content="IE=8">

	<style>

.x-panel-bwrap {
	height: 100%
}
.x-panel-body {
	height: 100%
}


#tag_container1 {
	position: relative;
	width: 100%;
	height: 400px;
	border-width: 0;
	border-style: solid;
	border-color: #74A6CC;
	margin-top: 10px;
}
#tag_container1 .tag_div {
	position: relative;
	width: 30%;
	height: 100%;
	float: left;
	margin-left: 2%;
}

#tag_info_div {
	position: relative;
	width: 100%;
}

#tag_info_div1 {
	position: relative;
	width: 100%;
}

#tag_info_div #a0194z {
	width: 98%;
	margin-left: 10px;
	border-width: 1px 1px;
	border-style: solid;
	border-color: #74A6CC;
}

#bottom_div {
	position: relative;
	width: 100%;
	height: 40px;
	margin-top: 5px;
}


#tag_container {
	width: 774px;
	height: 450px;
	margin: 1px;
	border-width: 1px 1px;
	border-style: solid;
	border-color: #74A6CC;
	padding: 2px 0 2px 2px;
} 

#left_div {
	width: 240px;
	height: 450px;
	float: left;
	padding-right: 4px; overflow-x : hidden;
	overflow-y: auto;
	overflow-x: hidden;
}

#left_div div {
	width: 100%;
	height: 26px;
	font-size: 14px;
	border-width: 0 1px 1px 1px;
	border-style: solid;
	border-color: #74A6CC;
	line-height: 26px;
	padding-left: 2px;
}

#right_div {
	width: 520px;
	height: 450px;
	float: left;
	padding-left: 10px;
}

#right_div table {
	font-size: 14px;
}

#right_div div {
	display: none;
	width: 100%;
	height: 450px;
	overflow-y: auto;
}

#right_div div table {
	font-size: 14px;
}

#right_div table tr td {
	height: 26px;
	line-height: 26px;
}

#right_div table tr td input[type=text] {
	width: 50px;
	height: 21px;
}

#bottom_div {
	width: 100%;
	height: 40px;
	padding-top: 5px;
}

#bottom_div table {
	width: 100%;
}




</style>


<div style="float: left;position: relative;overflow-y:auto;height:700px">
<odin:hidden property="tp00"/>
<script type="text/javascript">
</script>
	</div>

	<div  style="float: left;">
	<div id="conditionArea" style="height: 650;overflow-y: scroll;">
   		<odin:groupBoxNew title="����" property="ggBox" contentEl="jbDiv" collapsible="false">
		<div id="jbDiv">
			<table style="width: 100%;height: 530">
				<tr>
					<odin:textEdit property="a0101" label="��Ա����" maxlength="36" />
					
					<odin:select2 property="a0163" label="��Ա״̬" codeType="ZB126" disabled="true" value="1"></odin:select2>
										
				</tr>
				<tr>
					<odin:select2 property="a0165A" label="ʡ�ܸɲ�" data="['02', 'ʡ�ܸɲ�'],['05', 'ʡ��ί���й�']" multiSelect="true"></odin:select2>
					<odin:select2 property="a0165B" label="�йܸɲ�" data="['10', '�й���ְ'],['11', '�йܸ�ְ'],['18','��ֱ���飨��ί����Ա����Ѳ'],['19', '�й�ְ������Ա�����˳��쵼��λ��'],['04', '�й�����']" multiSelect="true"></odin:select2>
					<odin:select2 property="a0165C" label="�������в㣩�ɲ�" data="['12', '��ֱ����'],['50', '��ֱ����'],['07', '������ְ'],['08', '���ܸ�ְ'],['09', '���й���ְ'],['13', '���йܸ�ְ'],['51','�������ּ���ҵ��ְ'],['60','�������ּ���ҵ�в㸱ְ'],['52','����']" multiSelect="true"></odin:select2>
				</tr>
				<tr>
					<td noWrap="nowrap" align=right><span id=sexSpanId style="FONT-SIZE: 12px">��&nbsp;&nbsp;&nbsp;��</span>&nbsp;</td>
					<td colspan="1" style="FONT-SIZE: 12px">
						<label><input name="a0104" type="radio" value="0" />ȫ��</label>
						<label><input name="a0104" type="radio" value="1" />�� </label> 
						<label><input name="a0104" type="radio" value="2" />Ů </label> 
					</td>
					
					<td noWrap="nowrap" align=right><span id="a0117SpanId" style="FONT-SIZE: 12px">��&nbsp;&nbsp;&nbsp;��</span>&nbsp;</td>
					<td colspan="1" style="FONT-SIZE: 12px">
						<label><input name="a0117" type="radio" value="0" />ȫ��</label>
						<label><input name="a0117" type="radio" value="1" />���� </label> 
						<label><input name="a0117" type="radio" value="2" />�������� </label> 
					</td>
					
					<!-- <td noWrap="nowrap" align=right><span id=a0141SpanId style="FONT-SIZE: 12px">��&nbsp;&nbsp;&nbsp;��</span>&nbsp;</td>
					<td colspan="1" style="FONT-SIZE: 12px">
						<label><input name="a0141" type="radio" value="0" />ȫ��</label>
						<label><input name="a0141" type="radio" value="1" />�й���Ա </label> 
						<label><input name="a0141" type="radio" value="2" />���й���Ա</label> 
					</td> -->
					<tags:ComBoxWithTree property="a0141" label="��&nbsp;&nbsp;&nbsp;��" readonly="true" ischecked="true" width="160" codetype="GB4762" />
					
				</tr>
				
				<tr>
					
					<td noWrap="nowrap" align=right><span id="ageASpanId" style="FONT-SIZE: 12px">��&nbsp;&nbsp;&nbsp;��</span>&nbsp;</td>
						<td >
							<table  ><tr>
								<odin:numberEdit property="ageA"  maxlength="3" width="72" />
								<td><span style="font: 12px">��</span></td>
								<odin:numberEdit property="ageB" maxlength="3" width="72" />
							</tr></table>
					</td>
						
					<td noWrap="nowrap" align=right><span id="birthdaySpanId" style="FONT-SIZE: 12px">��������</span>&nbsp;</td>
						<td >
							<table><tr>
								<odin:NewDateEditTag property="a0107A"  maxlength="8" width="72" />
								<td><span style="font: 12px">��</span></td>
								<odin:NewDateEditTag property="a0107B" maxlength="8" width="72" />
							</tr></table>
					</td> 
					
					<odin:NewDateEditTag  isCheck="true" property="jiezsj" maxlength="6"  label="�������޼����ֹ"></odin:NewDateEditTag>
					
				</tr>
				
				<tr>
					<odin:select2 property="xlxw" label="ѧ��ѧλ" data="['��ʿ', '��ʿ'],['˶ʿ', '˶ʿ'],['�о���', '�о���'],['��ѧ,����','��ѧ' ],['��ר,ר��,����,�м�,��ר,Сѧ,����', '��ר������']" multiSelect="true"></odin:select2>
					<td><label style="FONT-SIZE: 12px">ȫ����</label><input name="qrz" type="checkbox" value="1" /> </td>
					<td></td>
					
					<td noWrap="nowrap" align=right><span id="a0192fSpanId" style="FONT-SIZE: 12px">����ְʱ��</span>&nbsp;</td>
					<td >
						<table><tr>
							<odin:NewDateEditTag property="a0192fA"  maxlength="8" width="72" />
							<td><span style="font: 12px">��</span></td>
							<odin:NewDateEditTag property="a0192fB" maxlength="8" width="72" />
						</tr></table>
					</td>
				</tr>
				
				<tr>
					<tags:ComBoxWithTree property="a0221A" label="��ְ����" readonly="true" ischecked="true" width="160" codetype="ZB09" />
					
					<td noWrap="nowrap" align=right><span id="a0288SpanId" style="FONT-SIZE: 12px">����ְ����ʱ��</span>&nbsp;</td>
					<td >
						<table><tr>
							<odin:NewDateEditTag property="a0288A"  maxlength="8" width="72" />
							<td><span style="font: 12px">��</span></td>
							<odin:NewDateEditTag property="a0288B" maxlength="8" width="72" />
						</tr></table>
					</td>
					<td colspan="2" align="center"><label style="FONT-SIZE: 12px">�������򣨽ֵ���������ְ����</label><input name="a0188" type="checkbox" value="1" /> </td>
				</tr>
				
				<tr>
					<%-- <tags:PublicTextIconEdit isLoadData="false" property="a0192e" codetype="ZB09" readonly="true" label="��ְ��"></tags:PublicTextIconEdit> --%>
					<tags:ComBoxWithTree property="a0192e" label="��ְ��" readonly="true" ischecked="true" width="160" codetype="ZB148" />
					
					
					<td noWrap="nowrap" align=right><span id="a0192cSpanId" style="FONT-SIZE: 12px">��ְ��ʱ��</span>&nbsp;</td>
					<td >
						<table><tr>
							<odin:NewDateEditTag property="a0192cA"  maxlength="8" width="72" />
							<td><span style="font: 12px">��</span></td>
							<odin:NewDateEditTag property="a0192cB" maxlength="8" width="72" />
						</tr></table>
					</td>
					<td colspan="2" align="center"><label style="FONT-SIZE: 12px">�������򣨽ֵ�����������ί���</label><input name="a0132" type="checkbox" value="1" /> </td>
				</tr>
				
				<tr>
					<odin:select2 property="a0194z" label="רҵ����" codeType="EXTRA_TAGS" multiSelect="true"></odin:select2>
					
					<tags:ComBoxWithTree property="a0194c" label="��Ҫ��ְ����" readonly="true" ischecked="true" width="160" codetype="ATTR_LRZW" />
					<td colspan="2" align="center"><label style="FONT-SIZE: 12px">�������򣨽ֵ����򳤣����Σ�</label><input name="a0133" type="checkbox" value="1" /> </td>
				</tr>
				
			</table>
		</div>
	</odin:groupBoxNew>
</div> 	


<div id="bottomDiv" style="width: 100%;">
		<table style="width: 100%; background-color: #cedff5">
			<tr align="center">
				<td>
					<table>
						<tr>
							<td><odin:button text="�������" property="clearCon2" handler="clearConbtn"></odin:button>
							</td>
							<td><odin:button text="��������" property="saveCon1" handler="dosearch"></odin:button>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>

</div>


<input type="reset" name="reset" id="resetBtn" style="display: none;" />
<odin:hidden property="radioC"/>
<odin:hidden property="sql"/>
</html>

<script type="text/javascript">

var config ={};


function setCheckBox(c, v){
	var check = document.getElementsByName(c);
	var arr = ","+v+",";
	//alert(arr);
	for(i=0;i<check.length;i++){
		//alert(arr.indexOf(','+check[i].value+','));
		if(arr.indexOf(','+check[i].value+',')>-1){
			check[i].checked=true;
		} else {
			check[i].checked=false;
		}
	}
	
	
}



function tfckbox(checkboxName,hiddenName){
	var checkboxes = document.getElementsByName(checkboxName);
	var hiddenValue = "";
	for (i=0; i<checkboxes.length; i++) {  
        if (checkboxes[i].checked) {  
        	hiddenValue = hiddenValue + checkboxes[i].value+',';
        }  
    }
	if(hiddenValue.length>0){
		hiddenValue = hiddenValue.substring(0,hiddenValue.length-1);
	}
	document.getElementById(hiddenName).value = hiddenValue;
}

function dosearch(){
	radow.doEvent('mQueryonclick');
	
} 


Ext.onReady(function(){

	var tp00 = parent.document.getElementById("tp00").value;
// 	var tp00='001';
	document.getElementById("tp00").value = tp00;
});



function clearConbtn(){
	Ext.getCmp("a0141_combotree").clearCheck();
	Ext.getCmp("a0221A_combotree").clearCheck();
	Ext.getCmp("a0192e_combotree").clearCheck();
	Ext.getCmp("a0194c_combotree").clearCheck();
	radow.doEvent('clearReset');
	radow.doEvent('initX');
}


function collapseGroupWin(){
	window.close();
}

function changeTag(node, codevalue){

	var codevaluelist = ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12', '13', '14', '15', '16', '17', '18', '20', '21', '22', '23', '24'];
	for(var i = 0,len=codevaluelist.length; i < len; i++) {
		document.getElementById("tag" + codevaluelist[i]).style.display = "none";
	}

	document.getElementById("tag" + codevalue).style.display = "block";
	changeTagMenuHover(node);
}

//��ǩ��
function disableInputTag(check,inputId) {
	var obj = $('#'+inputId);
	if($(check).is(':checked')) {
		obj.attr("disabled",false);
	} else {
		obj.val("");
		obj.attr("disabled","disabled");
	}
}

</script>


<%@include file="/pages/customquery/otjs.jsp" %>

