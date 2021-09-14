<%@ tag pageEncoding="GBK" body-content="empty" small-icon=""
 display-name="人员基础信息" description="人员基础信息" %>
 <%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<% 
String ctxpathemp = request.getContextPath();
%>
<table width="98%" align="center">
<tr><td>
<odin:groupBox title="基本信息">
<table border="0" id="myform4" align="center" width="100%"  cellpadding="0" cellspacing="0">
<odin:tabLayOut />
   <tr>
     <odin:textEdit property="EAC012" label="社保编号" required="true" width="144"/> 
     <odin:textEdit property="AAC003" label="姓名" required="false" disabled="true" width="144"/>   
	 <odin:textEdit property="EAC109" label="身份证号码"   required="false" disabled="true" width="144"/>     
   </tr>
   <tr> 
     <odin:textEdit property="AAB001" label="单位编号"   required="false" disabled="true" width="144"/>
	 <odin:textEdit property="AAB004" label="单位名称" colspan="4" width="396" required="false" disabled="true"/>    
   </tr>
   <tr>
	 <odin:select property="AAC004" editor="false" data="['1', '男'],['2', '女'],['9', '未说明']" label="性别" required="false" disabled="true" width="144"></odin:select> 
     <odin:dateEdit property="AAC006" label="出生日期"   required="false" disabled="true" width="144"/>
	 <odin:textEdit property="EA0144" label="年&nbsp&nbsp龄"  required="false" disabled="true" width="144"/> 
   </tr>
   <tr>
	 <odin:select property="EABJD" editor="false" data="['1', '华星路'],['2', '绍兴路'],['3', '文一路']" label="乡镇街道" required="false" disabled="true" width="144"></odin:select>
     <odin:dateEdit property="AAC030" label="首次参保日期" required="false" disabled="true" width="144"/>
   </tr>
   <tr>
   </tr>
</table>
</odin:groupBox> 
</td>
</tr></table>
