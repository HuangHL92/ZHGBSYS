<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@include file="/comOpenWinInit.jsp" %>
<%String ctxPath = request.getContextPath(); %>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="js/lengthValidator.js"></script>

<style>
#grid1 {
	width: 316px !important;
}
</style>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/qdstyle.css" />
<script type="text/javascript">

</script>
<odin:hidden property="b0111" title="����id"/>
<odin:hidden property="b01id" title="����id"/>
<odin:hidden property="b0101" title="����"/>
<odin:hidden property="jggwconfid" title="����id"/>
<odin:hidden property="zjcode" />
<odin:hidden property="status" />
<odin:hidden property="id" title="ְλ����id"/>
<div id="bar" ></div>
<div style="margin-left: 10px;margin-top: 5px;">
<odin:groupBox title="������Ϣ">
<table>
	<tr>
		<td width="10px"></td>
		<tags:PublicTextIconEdit property="gwcode" onchange="gwcodeChange" codetype="GWGLLB" label="ְλ����" required="true" readonly="true"/>
		<td width="5px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<odin:textEdit property="gwnum" label="ְλ��Ŀ" value="0" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="5" required="true" />
		
	</tr>
	<tr>
		<td colspan="6" height="3px"> </td>
	</tr>
	<tr>
		<td width="10px"></td>
		<tags:PublicTextIconEdit property="zwcode" codetype="ZB09" label="��Ӧְ����" required="true" readonly="true"/>
		<td width="5px"></td>
		<td colspan="2"></td>
	</tr>
	<tr>
		<td width="10px"></td>
		<td colspan="2">
			&nbsp;&nbsp;
			<input type="checkbox" name="a0219" id="a0219" />
			<label id="a0219SpanId" for="a0219" style="font-size: 12px;">�쵼ְ��</label>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="checkbox" name="a0201d" id="a0201d"/>
			<label id="a0201dSpanId" for="a0201d" style="font-size: 12px;">�쵼��Ա</label>
		</td>
		<td width="5px"></td>
		<odin:select2 property="a0201e" label="��Ա���" codeType="ZB129"></odin:select2>
	</tr>
	<tr>
		<td colspan="6" height="3px"> </td>
	</tr>
	<tr>
		<td width="10px"></td>
		<td align="right">
			<label style="font-size: 12px;">�Ƿ��ְ��&nbsp;</label>
		</td>
		<td>
			<input type="radio" name="iscount" id="iscount" value="1" checked="checked"/>
			<label style="font-size: 12px;">��</label>
			<input type="radio" name="iscount" id="iscount" value="0" />
			<label style="font-size: 12px;">��</label>
		</td>
		<td width="5px"></td>
		<td colspan="2"></td>
		<!-- <td align="right">
			<label style="font-size: 12px;">�Ƿ���Ч&nbsp;</label>
		</td>
		<td>
			<input type="radio" name="status" id="status"  value="1" checked="checked"/>
			<label  style="font-size: 12px;">��</label>
			<input type="radio" name="status" id="status"  value="0" />
			<label  style="font-size: 12px;">��</label>
		</td> -->
	</tr>
	<tr>
		<td colspan="6" height="10px"> </td>
	</tr>
</table>
</odin:groupBox>




		<odin:groupBox title="�ʸ�����">
					<table>
						<tr>
							<odin:textEdit property="ageup" label="������ڵ��ڣ��꣩" onkeyup="if (this.value != this.value.replace(/[^{0-9}\$]+/,'')) this.value=this.value.replace(/[^{0-9}\$]+/,'');"></odin:textEdit>
							<odin:textEdit property="agedown" label="����С�ڵ��ڣ��꣩" onkeyup="if (this.value != this.value.replace(/[^{0-9}\$]+/,'')) this.value=this.value.replace(/[^{0-9}\$]+/,'');"></odin:textEdit>
						</tr>

						<tr>
							<odin:select2 property="a0104" label="�Ա�" readonly="false"
								codeType="GB2261" required="false"></odin:select2>
						
							<tags:PublicTextIconEdit property="a0801b" label="ѧ��"
								required="false" codetype="ZB64" readonly="true"></tags:PublicTextIconEdit>
						</tr>

						<tr>
							<tags:PublicTextIconEdit property="a0827" label="רҵ"
								required="false" codetype="GB16835" readonly="true"></tags:PublicTextIconEdit>
						
							<tags:PublicTextIconEdit property="a0141" label="����"
								required="false" codetype="GB4762" readonly="true"></tags:PublicTextIconEdit>
						</tr>

						<tr>
							<tags:PublicTextIconEdit property="know" label="��Ϥ����"
								required="false" codetype="SXLY" readonly="true"></tags:PublicTextIconEdit>
						</tr>
						
						<tr>
							<td style="height: 30px"></td>
						</tr>

						<tr>
							<tags:PublicTextIconEdit property="a0221" label="ְ����"
								required="false" codetype="ZB09" readonly="true"></tags:PublicTextIconEdit>
							<odin:textEdit property="a0221n" label="�θ�ְ�������ޣ��꣩" onkeyup="if (this.value != this.value.replace(/[^{0-9}\$]+/,'')) this.value=this.value.replace(/[^{0-9}\$]+/,'');"></odin:textEdit>
						</tr>
						<tr>
							<tags:PublicTextIconEdit property="a0148" label="ְ��"
								required="false" codetype="ZB148" readonly="true"></tags:PublicTextIconEdit>
							<odin:textEdit property="a0148n" label="�θ�ְ�����ޣ��꣩" onkeyup="if (this.value != this.value.replace(/[^{0-9}\$]+/,'')) this.value=this.value.replace(/[^{0-9}\$]+/,'');"></odin:textEdit>
						</tr>
						
					</table>
			</odin:groupBox>

</div>
<odin:toolBar property="ToolBar" applyTo="bar" >
    <odin:fill />

	<odin:buttonForToolBar text="����" id="save" icon="image/icon021a2.gif" isLast="true"/>
	
</odin:toolBar>	
<script type="text/javascript">
Ext.onReady(function() {
	var height=document.body.clientHeight;
	var width=document.body.clientWidth;
	document.getElementById("ToolBar").style.width=width+'px';
});

function gwcodeChange(record){
	radow.doEvent('isExists',record.data.key);
}

$(function(){
    $("#a0201d").change(function() {
    	setA0201eDisabled();
    });
}); 
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

</script>

