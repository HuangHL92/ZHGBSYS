<%@ tag pageEncoding="GBK" body-content="empty" small-icon=""
 display-name="人员基础信息" description="人员基础信息" %>
 <%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<% 
String ctxpathemp = request.getContextPath();
%>
<table width="98%" align="center">
<tr><td>
<odin:groupBox title="基本信息">
<table border="0" id="myform4" align="center" width="100%" height="70" cellpadding="0" cellspacing="0">
<odin:tabLayOut />
   <tr>
     <odin:textEdit property="EAC012" label="社保编号" required="true" /> 
     <odin:textEdit property="AAC003" label="姓名" required="false" disabled="true"/>   
	 <odin:select property="AAC004" editor="false" data="['1', '男'],['2', '女'],['9', '未说明']" label="性别" required="false" disabled="true" width="135"></odin:select>      
   </tr>
   <tr> 
     <odin:textEdit property="AAB001" label="单位编号"   required="false" disabled="true"/>
	 <odin:textEdit property="AAB004" label="单位名称" colspan="4" width="388" required="false" disabled="true"/>    
   </tr>
   <tr>
     <odin:textEdit property="EAC109" label="身份证号码"   required="false" disabled="true"/>
     <odin:dateEdit property="AAC006" label="出生日期"   required="false" disabled="true" />
   </tr>
</table>
</odin:groupBox> 
</td>
</tr></table>
