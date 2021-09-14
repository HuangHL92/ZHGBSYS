<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%String ctxPath = request.getContextPath(); 
%>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.FontConfigPageModel"%>
<%@page import="com.insigma.siis.local.pagemodel.cadremgn.sysmanager.authority.TableColInterface"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<style>
body {
	background-color: rgb(214,227,243);
}

</style>
<odin:hidden property="a0000" title="人员主键"/>
<table style="width:100%;margin-top: 50px;">
	<tr>
		<odin:select2 property="g02001" label="选调生" codeType="XZ09" onchange="Seta29341"></odin:select2>
		<odin:NewDateEditTag property="a29341" isCheck="true" label="进入选调生时间" maxlength="8" />
		
	</tr>
	<tr>
		<odin:select2 property="a29344" label="选调时政治面貌" width="160" codeType="GB4762"></odin:select2>
		<odin:select2 property="a29071" label="来源	" width="160" codeType="ZB138"/>
	</tr>
	<tr>
		<tags:PublicTextIconEdit property="a29347b" label="选调时学历代码" onchange="setA29347aValue" codetype="ZB64" readonly="true"/>
		<odin:textEdit property="a29347a" label="选调时学历名称"/>
	</tr>
	<tr>
		<tags:PublicTextIconEdit property="a29351b" label="选调时学位代码" onchange="setA29347cValue" codetype="GB6864" readonly="true"/>
		<odin:textEdit property="a29347c" label="选调时学位名称"/>
	</tr>
	<tr>
		<odin:textEdit property="a29072" label="初始工作单位"></odin:textEdit>
		<odin:select2 property="a39067" label="是否退役大学生士兵" codeType="XZ09"/>
	</tr>
	<tr>
		
		<odin:select2 property="a44031" label="&nbsp;&nbsp;是否有海外工作经历" codeType="XZ09" onchange="Seta39084"/>
		<odin:numberEdit property="a39084" label="海外工作年限"/>
	</tr>
	<tr>
		<odin:select2 property="a44027" label="是否有海外留学经历" codeType="XZ09" onchange="Seta39077"/>
		<odin:numberEdit property="a39077" label="留学年限"/>
	</tr>
	<tr>
		<td><span  style="font-size: 12px;float:right;">基层工作经历:</span></td>
		<td>
				<table >
					<tr>
						<odin:numberEdit property="a29073_Y"  maxlength="2" width="60" />
						<td><span style="font-size: 12px">年</span></td>
						<td><div style="width: 10px"></div></td>
						<odin:numberEdit property="a29073_M" maxlength="2" width="60" />
						<td><span style="font-size: 12px">月</span></td>
					</tr>
				</table>
		</td>
		<!-- <td><button onclick=" location=location ">刷新</button></td> -->
	</tr>
	<tr>
		<td colspan="4"><div style="height: 5px"></div></td>
		
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
var fieldsDisabled = <%=TableColInterface.getUpdateDataByTable("A81")%>;
var selectDisabled = <%=TableColInterface.getSelectDataByTable("A81")%>;

Ext.onReady(function(){
	//对信息集明细的权限控制，是否可以维护 
	$h.fieldsDisabled(fieldsDisabled); 
	//对信息集明细的权限控制，是否可以查看
	//var imgdata = "<img src='<%=request.getContextPath()%>/image/quanxian1.png' />";
	var imgdata = "url(<%=request.getContextPath()%>/image/suo.jpg)";
	$h.selectDisabled(selectDisabled,imgdata); 
	for(var i=0; i<fieldsDisabled.length; i++){
		var formfield = fieldsDisabled[i].split("_")[1];
		if(formfield=='a29073'){
			Ext.getCmp(formfield+'_Y').disable();
			$('#'+formfield+'_Y').addClass('bgclor');
			Ext.getCmp(formfield+'_M').disable();
			$('#'+formfield+'_M').addClass('bgclor');
		}
	}
	for(var i=0; i<selectDisabled.length; i++){
		var formfield = selectDisabled[i].split("_")[1];
		if(formfield=='a29073'){
			var div_y = document.createElement("div");
			div_y.style.width = document.getElementById(formfield+"_Y").clientWidth;
			div_y.style.height = document.getElementById(formfield+"_Y").clientHeight;
			div_y.style.position = "absolute";
			div_y.style.left = $('#'+formfield+"_Y").offset().left+'px';
			div_y.style.top = $('#'+formfield+"_Y").offset().top+'px';
	　　　　div_y.style.backgroundImage = imgdata;
			div_y.style.backgroundRepeat = "no-repeat";
			div_y.style.backgroundColor = "white";
			div_y.style.backgroundPosition = "center";
	　　　　document.body.appendChild(div_y);
			var div_m = document.createElement("div");
			div_m.style.width = document.getElementById(formfield+"_M").clientWidth;
			div_m.style.height = document.getElementById(formfield+"_M").clientHeight;
			div_m.style.position = "absolute";
			div_m.style.left = $('#'+formfield+"_M").offset().left+'px';
			div_m.style.top = $('#'+formfield+"_M").offset().top+'px';
			div_m.style.backgroundImage = imgdata;
			div_m.style.backgroundRepeat = "no-repeat";
			div_m.style.backgroundColor = "white";
			div_m.style.backgroundPosition = "center";
			document.body.appendChild(div_m);
		}
	}
	
});
Ext.onReady(function(){
	document.getElementById("a0000").value = window.parent.document.getElementById("a0000").value;
});
function save(){
	radow.doEvent('save');
}
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



function Seta39084(){
	if(document.getElementById('a44031').value=="1"){
		Ext.getCmp("a39084").setDisabled(false);
	}else{
		Ext.getCmp("a39084").setValue('');
		Ext.getCmp("a39084").setDisabled(true);
	}
	
}

function Seta29341(){
	if(document.getElementById('g02001').value=="1"){
		$h.dateEnable("a29341");
	}else{
		$h.dateDisable("a29341");
	}
}

function formcheck(){
	return odin.checkValue(document.forms.commForm);
}

function setA29347aValue(record,index){//学位
	Ext.getCmp('a29347a').setValue(record.data.value);
}
function setA29347cValue(record,index){//学位
	Ext.getCmp('a29347c').setValue(record.data.value);
}
</script>
