<%@page import="com.insigma.siis.local.pagemodel.search.ListOutPutPageModel"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.WarnWindowPageModel"%>
 
<div >
<table>
<tr>
<td width="100px">&nbsp;</td>
<td>
<br>
<odin:checkbox property="time" label="按日期换行"></odin:checkbox>
</td>
</tr>
<tr>
<td width="100px">&nbsp;</td>
<td>
<br>
<odin:checkbox property="varchar" label="按字符换行" onclick="change()"></odin:checkbox>
</td>
</tr>
</table>
</div>
<div  id="showdiv" style="display:none">
<table>
<tr>
<odin:textEdit property="num" label="指定字符个数后换行"  value="" onkeyup="this.value=this.value.replace(/\D/g,'')"></odin:textEdit> 
</tr>
<tr>
<odin:textEdit property="numvalue" label="指定字符后换行" ></odin:textEdit>
</tr>
</table>
</div>
<table>
<tr>
<td width="100px">&nbsp;</td>
<td align="left"> 
<INPUT TYPE="button" VALUE="确定" ONCLICK="yesBtn()">&nbsp;&nbsp;&nbsp;&nbsp;
<INPUT TYPE="button" VALUE="取消" ONCLICK="cancelBtn()">
</td>
</tr>
</table>
<script type="text/javascript">
function change(){
	var val = document.getElementById("showdiv").style.display;
	if(val == 'none'){
		document.getElementById("showdiv").style.display="";
	}else{
		document.getElementById("showdiv").style.display="none";
	}
}
function cancelBtn(){
	parent.doHiddenPupWin();
}
function yesBtn(){
	parent.document.getElementById("anriqi").value=document.getElementById("time").checked;
	parent.document.getElementById("anzifugeshu").value=document.getElementById("num").value;
	parent.document.getElementById("anzhidingzifu").value=document.getElementById("numvalue").value;
	parent.doHiddenPupWin();
}
</script>