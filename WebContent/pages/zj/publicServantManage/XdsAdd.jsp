<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%String ctxPath = request.getContextPath(); 
%>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.FontConfigPageModel"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="js/lengthValidator.js"></script>
<%@include file="/comOpenWinInit2.jsp" %>
<body>
	

<odin:toolBar property="toolBar8" applyTo="tol2">
				<odin:fill></odin:fill>
				<odin:buttonForToolBar text="保存" id="save1" handler="saveXds" icon="images/save.gif" cls="x-btn-text-icon" isLast="true"></odin:buttonForToolBar>
</odin:toolBar>

<div id="border">
<div id="tol2" align="left"></div>
<odin:hidden property="a99Z100" title="主键id"></odin:hidden>
<odin:hidden property="a0000" title="人员主键"></odin:hidden>

<table cellspacing="2" width="600" align="left">
	<tr>
		<odin:select2 property="a99z103" label="是否为选调生" data="['0','否'],['1','是']" onchange="setDisabled()" />
		<td>&nbsp;</td>
		<odin:select2 property="xdsgwcj" codeType="ZBXDSGWCJ" label="选调生岗位层级" />
	</tr>
	<tr>	
		<odin:NewDateEditTag property="dbjjggzsj" label="到本级机关工作时间" isCheck="true" labelSpanId="dbjjggzsjSpanId" maxlength="8" />
		<td>&nbsp;</td>
		<odin:select2 property="a99zxdslb" codeType="ZBXDSLB" label="选调生类别" />
	</tr>
	<tr>
		<odin:textEdit property="xdsdwjzw" label="选调时单位及职务" />
		<td>&nbsp;</td>
		<odin:select2 property="xdsly" label="选调生来源" codeType="ZBXDSLY" />
	</tr>
	<tr>	
		<odin:textEdit property="dsjggzsj" label="地市机关工作时间（X年）"  />
		<td>&nbsp;</td>
		<odin:textEdit property="jcgzjlsj"  label="基层工作经历时间（X年）" />
	</tr>
	<tr>
		<tags:PublicTextIconEdit property="xxmc" label="学校名称(无选项可手填)" codetype="ZBSCHOOL" />
		<td>&nbsp;</td>
		<tags:PublicTextIconEdit property="zy" label="专业(无选项可手填)" codetype="GB16835" />
	</tr>
	<tr>
		<odin:NewDateEditTag property="a99z104" label="进入选调生时间" isCheck="true" labelSpanId="a99z104SpanId" maxlength="8" />
	</tr>
</table>
</div>
</body>
<script type="text/javascript">
//保存选调生
function saveXds(){
	var a99z103 = document.getElementById('a99z103').value;	
	if( a99z103 == '1' ){
		
		//选调生岗位层级
		var xdsgwcj = document.getElementById('xdsgwcj').value;
		if( xdsgwcj == '' || xdsgwcj == null ){
			Ext.Msg.alert("系统提示","选调生岗位层级不能为空！");
			return;
		}
		
		//到本级机关工作时间
		var dbjjggzsj = document.getElementById('dbjjggzsj').value;	
		var dbjjggzsj_1 = document.getElementById('dbjjggzsj_1').value;	
		if( dbjjggzsj_1 == '' || dbjjggzsj_1 == null ){
			Ext.Msg.alert("系统提示","到本级机关工作时间不能为空！");
			return;
		}
		var text1 = dateValidate(dbjjggzsj_1);
		if(dbjjggzsj_1.indexOf(".") > 0){
			text1 = dateValidate(dbjjggzsj);
		}
		if(text1!==true){
			$h.alert('系统提示','到本级机关工作时间：' + text1, null,400);
			return false;
		} 
		
		//选调生类别
		var a99zxdslb = document.getElementById('a99zxdslb').value;
		if( a99zxdslb == '' || a99zxdslb == null ){
			Ext.Msg.alert("系统提示","选调生类别不能为空！");
			return;
		}
		
		//选调时单位及职务
		var xdsdwjzw = document.getElementById('xdsdwjzw').value;
		if( xdsdwjzw == '' || xdsdwjzw == null ){
			Ext.Msg.alert("系统提示","选调时单位及职务不能为空！");
			return;
		}
		
		//选调生来源
		var xdsly = document.getElementById('xdsly').value;
		if( xdsly == '' || xdsly == null ){
			Ext.Msg.alert("系统提示","选调生来源不能为空！");
			return;
		}
		
		//地市机关工作时间
		var dsjggzsj = document.getElementById('dsjggzsj').value;
		if( dsjggzsj == '' || dsjggzsj == null ){
			Ext.Msg.alert("系统提示","地市机关工作时间不能为空！");
			return;
		}
		
		//基层工作经历时间
		var jcgzjlsj = document.getElementById('jcgzjlsj').value;
		if( jcgzjlsj == '' || jcgzjlsj == null ){
			Ext.Msg.alert("系统提示","基层工作经历时间不能为空！");
			return;
		}
		
		//学校名称
		var xxmc = document.getElementById('xxmc_combo').value;
		if( xxmc == '' || xxmc == null ){
			Ext.Msg.alert("系统提示","学校名称不能为空！");
			return;
		}
		
		//专业
		var zy = document.getElementById('zy_combo').value;
		if( zy == '' || zy == null ){
			Ext.Msg.alert("系统提示","专业不能为空！");
			return;
		}
		
	}

	radow.doEvent('save.onclick');
}

//不是选调生不让选择
function setDisabled(){
	var a99z103 = document.getElementById("a99z103").value;
	if( a99z103 == '1' ){
	  $h.dateEnable('dbjjggzsj');
	  $h.dateEnable('a99z104');
	  selecteEnable('xdsgwcj');
	  selecteEnable('a99zxdslb');
	  selecteEnable('xdsly');
	  selecteEnable('xxmc');
	  selecteEnable('zy');
	  document.getElementById("xdsdwjzw").disabled=false;
	  document.getElementById("dsjggzsj").disabled=false;
	  document.getElementById("jcgzjlsj").disabled=false;
	}else{
	  $h.dateDisable('dbjjggzsj');
	  $h.dateDisable('a99z104');
	  selecteDisable('xdsgwcj');
 	  selecteDisable('a99zxdslb');
 	  selecteDisable('xdsly');
 	  selecteDisable('xxmc');
 	  selecteDisable('zy');
	  document.getElementById("xdsdwjzw").value="";
	  document.getElementById("xdsdwjzw").disabled=true;
	  document.getElementById("dsjggzsj").value="";
	  document.getElementById("dsjggzsj").disabled=true;
	  document.getElementById("jcgzjlsj").value="";
	  document.getElementById("jcgzjlsj").disabled=true;
	}
}



</script>
<style>
<%=FontConfigPageModel.getFontConfig()%>
.vfontConfig{
color: red;
}

#border {
	position: relative;
	left: 0px;
	top: 0px;
	width: 0px;
	border: 1px solid #99bbe8;
}

#toolBar8 {
	width: 690px !important;
}
</style>
