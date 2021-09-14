<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<script  type="text/javascript" src="<%=request.getContextPath()%>/basejs/jquery/jquery-1.7.2.min.js"></script>

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
//显示
	}else{
		if(radioType==1){
			document.getElementById("b0194a").checked = 'checked';
			document.getElementById("b0194b").disabled=true; 
			document.getElementById("b0194c").disabled=true; 
			Check1();
		}else if(radioType==2){
			document.getElementById("b0194a").disabled=true; 
			document.getElementById("b0194b").checked = 'checked';
			document.getElementById("b0194c").disabled=true; 
			Check2();
		}else if(radioType==3){
			document.getElementById("b0194a").disabled=true; 
			document.getElementById("b0194b").disabled=true; 
			document.getElementById("b0194c").checked = 'checked';
			Check3();
		}else{
			document.getElementById("b0194a").checked = 'checked';
			document.getElementById("b0194a").disabled=true; 
			document.getElementById("b0194b").disabled=true; 
			document.getElementById("b0194c").disabled=true; 
			Check1();
		}
		var tr3 = document.getElementById("tr3");
		tr3.cells[1].style.display="none";
		tr3.cells[3].style.display="none";
	}
}

function myfunction(b0101status,b0104status){
	if(b0101status==0){
			document.getElementById("b0101b").checked = 'checked';
			document.getElementById("b0101a").disabled=true; 
			document.getElementById("b0101b").disabled=true; 
	}else{
			document.getElementById("b0101a").checked = 'checked';
			document.getElementById("b0101a").disabled=false; 
			document.getElementById("b0101b").disabled=false;
	}
	if(b0104status==0){
			document.getElementById("b0104b").checked = 'checked';
			document.getElementById("b0104a").disabled=true; 
			document.getElementById("b0104b").disabled=true; 
	}else{
			document.getElementById("b0104a").checked = 'checked';
			document.getElementById("b0104a").disabled=false; 
			document.getElementById("b0104b").disabled=false;
	}
}
</script>
<odin:hidden property="b0111"/>
<odin:hidden property="a0201a"/>
<odin:hidden property="a0201c"/>
<odin:groupBox property="s10" title="机构全称已经改变">
<table cellspacing="2" width="98%" align="center">
	<tr>
		<td><label style="font-size: 12px">您希望同时更新当前机构下人员的工作单位及职务全称信息吗？</td>
	</tr>
	<tr>
	<td><input type="radio" id ="b0101a" name="b0101" value="1"  ><label style="font-size: 12px">更新</td>
	</tr>
	<tr>
	<td><input type="radio" id ="b0101b" name="b0101" value="0" checked="checked" ><label style="font-size: 12px">不更新</td>
	</tr>
</table>
</odin:groupBox>
<odin:groupBox property="s11" title="机构简称已经改变" >
<table cellspacing="2" width="98%" align="center" >
	<tr>
		<td><label style="font-size: 12px">您希望同时更新当前机构下人员的工作单位及职务简称信息吗？</td>
	</tr>
	<tr>
	<td><input type="radio" id ="b0104a" name="b0104" value="1"  ><label style="font-size: 12px">更新</td>
	</tr>
	<tr>
	<td><input type="radio" id ="b0104b" name="b0104" value="0" checked="checked" ><label style="font-size: 12px">不更新</td>
	</tr>
</table>
</odin:groupBox>


<div >
<table>
<tr >
<td width="400px"></td>
<td><odin:button property="YesBtn" text="确定"></odin:button></td>
<td width="20px"></td>
<td><odin:button property="CancelBtn" text="取消"></odin:button></td>
</tr>
</table>
</div>


