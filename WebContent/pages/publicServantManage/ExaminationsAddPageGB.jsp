<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%String ctxPath = request.getContextPath(); 
%>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<%@page import="com.insigma.siis.local.pagemodel.cadremgn.sysmanager.authority.TableColInterface"%>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<style>
body {
	background-color: rgb(214,227,243);
}

</style>

<odin:hidden property="a0000" title="人员主键"/>
<table style="width:100%;margin-top: 50px;">
	<tr>
		<odin:select2 property="a29314" label="考试录用人员" width="160" onchange="Seta03033" codeType="XZ09"></odin:select2>
		<odin:NewDateEditTag property="a03033" isCheck="true" label="录用时间" maxlength="8"  ></odin:NewDateEditTag>
	</tr>
	<tr>
		<odin:select2 property="a29321" label="录用时政治面貌" width="160" codeType="GB4762"></odin:select2>
		<tags:PublicTextIconEdit property="a29337" label="人员来源情况" codetype="ZB146" />
	</tr>
	<tr>
		<tags:PublicTextIconEdit property="a29324b" label="录用时学历代码" onchange="setA29324aValue" codetype="ZB64" readonly="true"></tags:PublicTextIconEdit>
		<odin:textEdit property="a29324a" label="录用时学历名称"  ></odin:textEdit>
	</tr>
	<tr>
		<tags:PublicTextIconEdit property="a29327b" label="录用时学位代码" onchange="setA29327aValue" codetype="GB6864" readonly="true"></tags:PublicTextIconEdit>
		<odin:textEdit property="a29327a" label="录用时学位名称"  ></odin:textEdit>
	</tr>
	<tr>
		<odin:select2 property="a39061" label="是否服务基层项目人员" codeType="XZ09"></odin:select2>
		<odin:select2 property="a39064" label="是否退役士兵" codeType="XZ09"></odin:select2>
	</tr>
	<tr>
		<odin:select2 property="a39067" label="是否退役大学生士兵" codeType="XZ09"></odin:select2>
		<odin:select2 property="a39071" label="是否残疾人" codeType="XZ09"></odin:select2>
	</tr>
	<tr>
		<odin:select2 property="a44031" label="是否有海外工作经历" codeType="XZ09" onchange="SetA39084"></odin:select2>
		<odin:numberEdit property="a39084" label="海外工作年限"></odin:numberEdit>
	</tr>
	<tr>
		<odin:select2 property="a44027" label="是否有海外留学经历" codeType="XZ09" onchange="Seta39077"></odin:select2>
		<odin:numberEdit property="a39077" label="留学年限"></odin:numberEdit>
	</tr>
	<tr>
		<td><span style="font-size: 12px;float:right;">录用时基层工作时间:</span></td>
		<td>
				<table >
					<tr>
						<odin:numberEdit property="a29334_GY"  maxlength="2" width="60" />
						<td><span style="font-size: 12px">年</span></td>
						<td><div style="width: 10px"></div></td>
						<odin:numberEdit property="a29334_GM" maxlength="2" width="60" />
						<td><span style="font-size: 12px">月</span></td>
					</tr>
				</table>
		</td>
		<!-- <td><button onclick=" location=location ">刷新</button></td> -->
	</tr>
	<tr>
		<td colspan="4"><div style="height: 2px"></div></td>
	</tr>
	<tr>
		<odin:textEdit property="a03011" label="报考准考证号"></odin:textEdit>
		<odin:numberEdit property="a03021" label="行政职业能力分数"></odin:numberEdit>
	</tr>
	<tr>
		<odin:numberEdit property="a03095" label="申论分数"></odin:numberEdit>
		<odin:numberEdit property="a03027" label="其他科目分数"></odin:numberEdit>
	</tr>
	<tr>
		<odin:numberEdit property="a03014" label="专业能力测试分数"></odin:numberEdit>
		<odin:numberEdit property="a03017" label="公共科目笔试成绩总分"></odin:numberEdit>
	</tr>
	<tr>
		<odin:numberEdit property="a03018" label="专业考试成绩"></odin:numberEdit>
		<odin:numberEdit property="a03024" label="面试成绩"></odin:numberEdit>
	</tr>
	
</table>
<script type="text/javascript">
var fieldsDisabled = <%=TableColInterface.getUpdateDataByTable("A80")%>;
var selectDisabled = <%=TableColInterface.getSelectDataByTable("A80")%>;

Ext.onReady(function(){
	//对信息集明细的权限控制，是否可以维护 
	$h.fieldsDisabled(fieldsDisabled); 
	//对信息集明细的权限控制，是否可以查看
	//var imgdata = "<img src='<%=request.getContextPath()%>/image/quanxian1.png' />";
	var imgdata = "url(<%=request.getContextPath()%>/image/suo.jpg)";
	$h.selectDisabled(selectDisabled,imgdata); 
	for(var i=0; i<fieldsDisabled.length; i++){
		var formfield = fieldsDisabled[i].split("_")[1];
		if(formfield=='a29334'){
			Ext.getCmp(formfield+'_GY').disable();
			$('#'+formfield+'_GY').addClass('bgclor');
			Ext.getCmp(formfield+'_GM').disable();
			$('#'+formfield+'_GM').addClass('bgclor');
		}
	}
	for(var i=0; i<selectDisabled.length; i++){
		var formfield = selectDisabled[i].split("_")[1];
		if(formfield=='a29334'){
			var div_y = document.createElement("div");
			div_y.style.width = document.getElementById(formfield+"_GY").clientWidth;
			div_y.style.height = document.getElementById(formfield+"_GY").clientHeight;
			div_y.style.position = "absolute";
			div_y.style.left = $('#'+formfield+"_GY").offset().left+'px';
			div_y.style.top = $('#'+formfield+"_GY").offset().top+'px';
	　　　　div_y.style.backgroundImage = imgdata;
			div_y.style.backgroundRepeat = "no-repeat";
			div_y.style.backgroundColor = "white";
			div_y.style.backgroundPosition = "center";
	　　　　document.body.appendChild(div_y);
			var div_m = document.createElement("div");
			div_m.style.width = document.getElementById(formfield+"_GM").clientWidth;
			div_m.style.height = document.getElementById(formfield+"_GM").clientHeight;
			div_m.style.position = "absolute";
			div_m.style.left = $('#'+formfield+"_GM").offset().left+'px';
			div_m.style.top = $('#'+formfield+"_GM").offset().top+'px';
			div_m.style.backgroundImage = imgdata;
			div_m.style.backgroundRepeat = "no-repeat";
			div_m.style.backgroundColor = "white";
			div_m.style.backgroundPosition = "center";
			document.body.appendChild(div_m);
		}
	}
	
});
function save(){
	radow.doEvent('save');
}
Ext.onReady(function(){
	document.getElementById("a0000").value = window.parent.document.getElementById("a0000").value;
});
function reShowMsg(){
	document.getElementById("a0000").value = window.parent.document.getElementById("a0000").value;
	radow.doEvent('initX');
}

function Seta39077(){
	if(document.getElementById('a44027').value=="1"){
		Ext.getCmp("a39077").setDisabled(false);
	
	}else{
		Ext.getCmp("a39077").setValue('');
		Ext.getCmp("a39077").setDisabled(true);
	}
	
}



function SetA39084(){
	if(document.getElementById('a44031').value=="1"){
		
		Ext.getCmp("a39084").setDisabled(false);
	}else{
		Ext.getCmp("a39084").setValue('');
		Ext.getCmp("a39084").setDisabled(true);
	}
	
}

function Seta03033(){
	if(document.getElementById('a29314').value=="1"){
		$h.dateEnable("a03033");
	}else{
		$h.dateDisable("a03033");
	}
}
function formcheck(){
	return odin.checkValue(document.forms.commForm);
}
function setA29324aValue(record,index){//学位
	Ext.getCmp('a29324a').setValue(record.data.value);
}
function setA29327aValue(record,index){//学位
	Ext.getCmp('a29327a').setValue(record.data.value);
}
</script>



