<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.FontConfigPageModel"%>
<%@include file="/comOpenWinInit.jsp" %>
<style>
<%=FontConfigPageModel.getFontConfig()%>
</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>

<div id="cover_wrap"></div>

<script type="text/javascript">

function v_test(type){
	//获取下拉选的值
	var a0141 = document.getElementById('a0141').value;		//第一党派
 	var a0144 = document.getElementById('a0144').value;		//入党时间
 	var a3921 = document.getElementById('a3921').value;		//第二党派
 	var a3927 = document.getElementById('a3927').value;		//第三党派 
 	
 	
 		//$h.dateDisable('a0144');
 		
 		//判断第一党派
 		if(type=='1'){
 			
 			if(a0141 == a3921 && a0141 != ''){
 				document.getElementById('a0141').value='';
 	 		 	document.getElementById('a0141_combo').value='';
 	 		 	alert('不能与第二党派相同！');
 	 		 	return false;
 			}
 			if(a0141 == a3927 && a0141 != ''){
 				document.getElementById('a0141').value=''
 	 		 	document.getElementById('a0141_combo').value='';
 	 		 	alert('不能与第三党派相同！');
 	 		 	return false;
 			}
 			
 			if((a0141=='02' || a0141=='01' || a0141=='03') && (a3921=='02' || a3921=='01' || a3921=='03') ){
 				document.getElementById('a0141').value='';
 	 		 	document.getElementById('a0141_combo').value='';
 	 		 	alert('不能与第二党派相同！');
 	 		 	return false;
 	 	 	}
 			
 			if((a0141=='02' || a0141=='01' || a0141=='03') && (a3927=='02' || a3927=='01' || a3927=='03') ){
 				document.getElementById('a0141').value=''
 	 		 	document.getElementById('a0141_combo').value='';
 	 		 	alert('不能与第三党派相同！');
 	 		 	return false;
 	 	 	}
 			
 		}
 		//第二党派判断
 		if(type == '2'){
 			
 			//是否和第一党派重复
 			if(a0141==a3921 && a3921 != ''){
 				document.getElementById('a3921').value=''
 	 		 	document.getElementById('a3921_combo').value='';
 	 		 	alert('第二党派与第一党派不能相同！');
 	 		 	return false;
 			}
 			//不可和第三党派相同 
 			if(a3921 != '' && a3921==a3927){
 				document.getElementById('a3921').value=''
 	 		 	document.getElementById('a3921_combo').value='';
 	 		 	alert('第二党派与第三党派不能相同！');
 	 		 	return false;
 			}
 			
 			if((a0141=='02' || a0141=='01' || a0141=='03') && (a3921=='02' || a3921=='01' || a3921=='03') ){
 				document.getElementById('a3921').value=''
 	 		 	document.getElementById('a3921_combo').value='';
 	 		 	alert('第二党派与第一党派不能相同！');
 	 		 	return false;
 	 	 	}
 			
 			if((a3921=='02' || a3921=='01' || a3921=='03') && (a3927=='02' || a3927=='01' || a3927=='03') ){
 				document.getElementById('a3921').value=''
 	 		 	document.getElementById('a3921_combo').value='';
 	 		 	alert('第二党派与第三党派不能相同！');
 	 		 	return false;
 	 	 	}
 			
 		}
 		//判断第三党派
 		if(type==3){
 			//判断是否和前两党派相同
 			if(a3927==a3921 || a3927==a0141){
 				document.getElementById('a3927').value=''
 	 		 	document.getElementById('a3927_combo').value='';
 	 		 	if(a3927==a3921 && a3927 !=''){
 	 		 		alert('第三党派与第二党派不能相同！');
 	 		 	}
 	 		 	if(a3927==a0141 && a0141 != ''){
 	 		 		alert('第三党派与第一党派不能相同！');
 	 		 	}
 	 		 	return false;
 			}
 			
 			
 			if((a3927=='02' || a3927=='01' || a3927=='03') && (a3921=='02' || a3921=='01' || a3921=='03') ){
 				document.getElementById('a3927').value='';
 	 		 	document.getElementById('a3927_combo').value='';
 	 		 	alert('第三党派与第二党派不能相同！');
 	 		 	return false;
 	 	 	}
 			
 			if((a0141=='02' || a0141=='01' || a0141=='03') && (a3927=='02' || a3927=='01' || a3927=='03') ){
 				document.getElementById('a3927').value=''
 	 		 	document.getElementById('a3927_combo').value='';
 	 		 	alert('第三党派与第一党派不能相同！');
 	 		 	return false;
 	 	 	}
 		}
 		
 		
 		//最后判断是否有共产党党派，有“参加时间”可以编辑 
 		if(a0141=='02' || a0141=='01' || a0141=='03'|| a3927=='02' || a3927=='01' || a3927=='03' || a3921=='02' || a3921=='01' || a3921=='03' ){//有共产党党派
 	 		$h.dateEnable('a0144');
 	 	} else {
 	 		$h.dateDisable('a0144');
 	 	}
 	
} 
 
function saveparty(){
	v_test('1');
	v_test('2');
	v_test('3');
	radow.doEvent('save.onclick');
}


function pageCallback(){
	document.getElementById('a0144_1').focus();
	document.getElementById('a0144_1').blur();
	var a0141 = document.getElementById('a0141').value;
 	/* if(a0141=='02' || a0141=='01'){
 		//$h.dateEnable('a0144');
 	} else {
 		$h.dateDisable('a0144');
 	} */
}
</script>
<odin:toolBar property="btnToolBar" applyTo="divbtnToolBar">
	<odin:fill />
	<odin:buttonForToolBar id="save1" isLast="true" handler="saveparty" text="确定"
		icon="images/save.gif" cls="x-btn-text-icon" />
</odin:toolBar>

<div id="divbtnToolBar"></div>	
<odin:hidden property="a0107" title="出生年月"/>
<table cellspacing="4" width="100%" align="center">
<tr>
<td>
<odin:select2 property="a0141" label="第一党派" labelSpanId="a0141SpanId" codeType="GB4762" onchange="v_test(1)"></odin:select2>
</td>
<td>
<odin:NewDateEditTag property="a0144" label="参加时间" isCheck="true" labelSpanId="a0144SpanId" maxlength="8" ></odin:NewDateEditTag>
</td>
</tr>
<tr>
<td>
<odin:select2 property="a3921" label="第二党派" onchange="v_test(2)" labelSpanId="a3921SpanId" codeType="GB4762"></odin:select2>
</td>
<td>
<odin:select2 property="a3927" label="第三党派" onchange="v_test(3)" codeType="GB4762"></odin:select2>
</td>
</tr>
<!--<tr>
<td>
<odin:textEdit property="a" label="第四党派"></odin:textEdit>
</td>
</tr>
-->
</table>
<script type="text/javascript">
Ext.onReady(function(){
	odin.setSelectValue("a0141",$("#a0141", realParent.document).val());
	odin.setSelectValue("a0144",$("#a0144", realParent.document).val());
	odin.setSelectValue("a3927",$("#a3927", realParent.document).val());
	odin.setSelectValue("a3921",$("#a3921", realParent.document).val());
	document.getElementById("a0107").value=$("#a0107", realParent.document).val();
	
	$h.applyFontConfig($h.spFeildAll.a01);
	$h.fieldsDisabled(realParent.fieldsDisabled);
	
	if(realParent.buttonDisabled){
		document.getElementById('cover_wrap').className = "divcover_wrap";
	}
	
});
</script>

