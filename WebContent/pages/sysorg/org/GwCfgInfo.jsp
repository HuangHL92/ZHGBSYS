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
<odin:hidden property="b0111" title="机构id"/>
<odin:hidden property="b01id" title="机构id"/>
<odin:hidden property="b0101" title="机构"/>
<odin:hidden property="jggwconfid" title="考核id"/>
<odin:hidden property="zjcode" />
<odin:hidden property="status" />
<odin:hidden property="id" title="职位条件id"/>
<div id="bar" ></div>
<div style="margin-left: 10px;margin-top: 5px;">
<odin:groupBox title="配置信息">
<table>
	<tr>
		<td width="10px"></td>
		<tags:PublicTextIconEdit property="gwcode" onchange="gwcodeChange" codetype="GWGLLB" label="职位名称" required="true" readonly="true"/>
		<td width="5px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
		<odin:textEdit property="gwnum" label="职位数目" value="0" onkeyup="this.value=this.value.replace(/\D/g,'')" maxlength="5" required="true" />
		
	</tr>
	<tr>
		<td colspan="6" height="3px"> </td>
	</tr>
	<tr>
		<td width="10px"></td>
		<tags:PublicTextIconEdit property="zwcode" codetype="ZB09" label="对应职务层次" required="true" readonly="true"/>
		<td width="5px"></td>
		<td colspan="2"></td>
	</tr>
	<tr>
		<td width="10px"></td>
		<td colspan="2">
			&nbsp;&nbsp;
			<input type="checkbox" name="a0219" id="a0219" />
			<label id="a0219SpanId" for="a0219" style="font-size: 12px;">领导职务</label>
			&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="checkbox" name="a0201d" id="a0201d"/>
			<label id="a0201dSpanId" for="a0201d" style="font-size: 12px;">领导成员</label>
		</td>
		<td width="5px"></td>
		<odin:select2 property="a0201e" label="成员类别" codeType="ZB129"></odin:select2>
	</tr>
	<tr>
		<td colspan="6" height="3px"> </td>
	</tr>
	<tr>
		<td width="10px"></td>
		<td align="right">
			<label style="font-size: 12px;">是否计职数&nbsp;</label>
		</td>
		<td>
			<input type="radio" name="iscount" id="iscount" value="1" checked="checked"/>
			<label style="font-size: 12px;">是</label>
			<input type="radio" name="iscount" id="iscount" value="0" />
			<label style="font-size: 12px;">否</label>
		</td>
		<td width="5px"></td>
		<td colspan="2"></td>
		<!-- <td align="right">
			<label style="font-size: 12px;">是否有效&nbsp;</label>
		</td>
		<td>
			<input type="radio" name="status" id="status"  value="1" checked="checked"/>
			<label  style="font-size: 12px;">是</label>
			<input type="radio" name="status" id="status"  value="0" />
			<label  style="font-size: 12px;">否</label>
		</td> -->
	</tr>
	<tr>
		<td colspan="6" height="10px"> </td>
	</tr>
</table>
</odin:groupBox>




		<odin:groupBox title="资格条件">
					<table>
						<tr>
							<odin:textEdit property="ageup" label="年龄大于等于（岁）" onkeyup="if (this.value != this.value.replace(/[^{0-9}\$]+/,'')) this.value=this.value.replace(/[^{0-9}\$]+/,'');"></odin:textEdit>
							<odin:textEdit property="agedown" label="年龄小于等于（岁）" onkeyup="if (this.value != this.value.replace(/[^{0-9}\$]+/,'')) this.value=this.value.replace(/[^{0-9}\$]+/,'');"></odin:textEdit>
						</tr>

						<tr>
							<odin:select2 property="a0104" label="性别" readonly="false"
								codeType="GB2261" required="false"></odin:select2>
						
							<tags:PublicTextIconEdit property="a0801b" label="学历"
								required="false" codetype="ZB64" readonly="true"></tags:PublicTextIconEdit>
						</tr>

						<tr>
							<tags:PublicTextIconEdit property="a0827" label="专业"
								required="false" codetype="GB16835" readonly="true"></tags:PublicTextIconEdit>
						
							<tags:PublicTextIconEdit property="a0141" label="党派"
								required="false" codetype="GB4762" readonly="true"></tags:PublicTextIconEdit>
						</tr>

						<tr>
							<tags:PublicTextIconEdit property="know" label="熟悉领域"
								required="false" codetype="SXLY" readonly="true"></tags:PublicTextIconEdit>
						</tr>
						
						<tr>
							<td style="height: 30px"></td>
						</tr>

						<tr>
							<tags:PublicTextIconEdit property="a0221" label="职务层次"
								required="false" codetype="ZB09" readonly="true"></tags:PublicTextIconEdit>
							<odin:textEdit property="a0221n" label="任该职务层次年限（年）" onkeyup="if (this.value != this.value.replace(/[^{0-9}\$]+/,'')) this.value=this.value.replace(/[^{0-9}\$]+/,'');"></odin:textEdit>
						</tr>
						<tr>
							<tags:PublicTextIconEdit property="a0148" label="职级"
								required="false" codetype="ZB148" readonly="true"></tags:PublicTextIconEdit>
							<odin:textEdit property="a0148n" label="任该职级年限（年）" onkeyup="if (this.value != this.value.replace(/[^{0-9}\$]+/,'')) this.value=this.value.replace(/[^{0-9}\$]+/,'');"></odin:textEdit>
						</tr>
						
					</table>
			</odin:groupBox>

</div>
<odin:toolBar property="ToolBar" applyTo="bar" >
    <odin:fill />

	<odin:buttonForToolBar text="保存" id="save" icon="image/icon021a2.gif" isLast="true"/>
	
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
	document.getElementById('a0201eSpanId').innerHTML='成员类别';
	
	
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
			document.getElementById('a0201eSpanId').innerHTML='<font color="red">*</font>成员类别';
		}
	}
}

</script>

