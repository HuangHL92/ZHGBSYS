<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>

<%-- <odin:toolBar property="btnToolBar">
	<odin:fill></odin:fill>
	<odin:buttonForToolBar text="生成呈报单" id="makeCBD" isLast="true"></odin:buttonForToolBar>
</odin:toolBar> --%>

<div id="cbdInfo">
	<table>
		<tr>
			<odin:textEdit property="cdb_word_year_no" label="审批字_年_号" required="true" maxlength="20"></odin:textEdit>
		</tr>
		<%-- <tr>
			<odin:textEdit property="cbd_year" label="审批字_年" required="true"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="cbd_no" label="审批字_号" required="true"></odin:textEdit>
		</tr> --%>
		<tr>
			<odin:textEdit property="cbd_leader" label="主送机关" required="true" maxlength="20"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="cbd_organ" label="发文机关" required="true" maxlength="20"></odin:textEdit>
		</tr>
		<tr>
			<odin:textarea property="cbd_text" label="呈报单正文" rows="5" cols="50"  required="true" onchange="checkLength()"></odin:textarea>
		</tr>
		<tr>
			<odin:dateEdit property="cbd_date1" label="呈报单日期"  required="true"></odin:dateEdit>
		</tr>
		<tr>
		    <td width="40"></td>
		    <td>
			    <input type="button" value="生成呈报单" onclick="makeCBD()" name="cbd" id="cbd"/>
			</td>
		</tr>
	</table>
</div>
<odin:hidden property="cbd_userid"/>
<odin:hidden property="cbd_username"/>
<odin:hidden property="cbd_personid"/>
<odin:hidden property="cbd_id" />
<odin:hidden property="objectno" />
<odin:panel contentEl="cbdInfo" property="cbdPanel" topBarId="btnToolBar"/>

<odin:window src="/blank.htm" id="selectPerson" width="320" height="150" title="选择人员窗口" modal="true"></odin:window>
<script type="text/javascript">
    function makeCBD(){
	       radow.doEvent('makeCBD.onclick');
    }

	function selectPerson(){
	
		doOpenPupWin("/radowAction.do?method=doEvent&pageModel=pages.cbdHandler.SelectPerson",
		"选择人员窗口",500,400,null);
		
	}
	
	//判断呈报单正文输入的长度，如果超长给出提示，并截取符合要求的长度到控件中。
	function checkLength(){
		var cbd_text = document.getElementById("cbd_text").value;
		var size = cbd_text.length;
		//var reallength = 0;
		///var charCode = -1;
		//for(var i=0;i<size;i++){
		//	charCode = cbd_text.charCodeAt(i);
		//	if(charCode>=0 && charCode<=128){
		//		reallength = reallength+1;
		//	}else{
		//		reallength = reallength +2;
		//	}
		//}
		if(size > 100){
			alert("呈报单正文输入内容长度超过限制,最大长度为100！");
			document.getElementById("cbd_text").value=cbd_text.substring(0,100);
		}
	}

</script>