<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@include file="/comOpenWinInit.jsp" %>

<script type="text/javascript">
function IsEmpty(fData)
{
    return ((fData==null) || (fData.length==0) )
} 

function a3007a()
{
    var a3001 = document.getElementById("a3001").value;
    if(!IsEmpty(a3001)){
        document.getElementById("group2").style.display="block";
    }
}

function Check1()
{
   var a3001 = document.getElementById("a3001").value;
   var New=document.getElementsByName("radio");
   var strNew;
   for(var i=0;i<New.length;i++)
   {
     if(New.item(i).checked){
         strNew=New.item(i).getAttribute("value");  
   break;
  }else{
    continue;
  }
  }
   if(strNew=="1")
   { 
     
     document.getElementById("group").style.display="block";
     
   }else{
     document.getElementById("a3001").value = "";
     document.getElementById("a3001_combo").value = "";
     document.getElementById("a3007a").value = "";
     document.getElementById("a3004").value = "";
     document.getElementById("a3034").value = "";
     document.getElementById("group").style.display="none";
     document.getElementById("group2").style.display="none";
    
   }    

}

function getTabletype(){
	var type = parent.document.getElementById("tableType").value;
	document.getElementById("type").value = type;
}
</script>

<odin:hidden property="type"/>

<odin:groupBox title="请选择删除方式">
<table>
<tr>
<td>
<input type="radio" id ="a" name="radio" value="1" checked="checked" onclick="Check1()">
<label style="font-size: 12" >调到历史库</label>
</td>
<td width="100"></td>
<td>
<input type="radio" id ="b" name="radio" value="2" onclick="Check1()">
<label style="font-size: 12" >完全删除</label>
</td>
</tr>
</table>
</odin:groupBox>

<div style="display:block;margin-left: 80" id="group">
<table>		
<tr>
<tags:PublicTextIconEdit property="a3001" label="退出管理方式" codetype="ZB78" readonly="true" onchange="a3007a()"></tags:PublicTextIconEdit>
</tr>
</table>
</div>

<div style="display:none;margin-left: 100" id="group2">
<table cellspacing="4">
<tr>
<odin:textEdit property="a3007a" label="调往单位"></odin:textEdit>
</tr>

<tr>
<odin:dateEdit property="a3004" label="日期" format="Ymd"></odin:dateEdit>
</tr>

<tr>
<odin:textEdit property="a3034" label="备注" ></odin:textEdit>
</tr>
</table>
</div>

<div>
<table align="center">
<tr>

<td><odin:button text="确定" property="yesBtn"></odin:button></td>
<td width="20"></td>
<td><odin:button text="取消" property="cancelBtn"></odin:button></td>
</tr>
</table>
</div>