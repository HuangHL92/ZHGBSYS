<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>    
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>Insert title here</title>
<odin:head/>
</head>
<body>
<odin:base>
<script>
function doSave()
{
	alert('按钮单击事件触发！');     
}
</script>
<table align="center" width="100%">
<tr><td>
<odin:simplePanel title="简单Panel" width="765" bodyWidth="740">
<odin:groupBox title="基本信息">
<table border="0" id="myform" align="center" width="100%"  cellpadding="0" cellspacing="0">
   <tr>
     <odin:textEdit property="worker0" label="工作"/> 
	 <odin:textEdit property="worker1" label="工作"/>
	 <odin:textEdit property="worker2" label="工作"/>
   </tr>
  <tr>
     <odin:textEdit property="dwbm" label="单位编码"/>
	 <odin:textEdit property="name" label="单位名称" colspan="4"/>
   </tr>
   <tr>
      <odin:textEdit property="xming" label="性命"/>
	  <odin:select property="zjlx" editor="true" data="['1', '身份证'],['2', '毕业证'],['3', '军官正'],['4', '其他证件']" label="证件类型" colspan="4"></odin:select>

   </tr>	 
 </table>
 </odin:groupBox>
 <br><br>
 <odin:groupBox title="基本信息">
<table border="0" id="myform" width="100%"  cellpadding="0" cellspacing="0">
   <tr>
     <odin:textEdit property="worker10" label="工作"/> 
	 <odin:textEdit property="worker11" label="工作"/>
	 <odin:textEdit property="worker12" label="工作"/>  
   </tr>
  <tr>
     <odin:textEdit property="dwbm1" label="单位编码"/>
	 <odin:textEdit property="name1" label="单位名称" colspan="4"/>
   </tr>
   <tr>
      <odin:textEdit property="xming1" label="性命"/>
	  <odin:select property="zjlx1" editor="true" data="['1', '身份证'],['2', '毕业证'],['3', '军官正'],['4', '其他证件']" label="证件类型" colspan="4"></odin:select>
   </tr>	 
 </table>
 </odin:groupBox> 
 <br><br>
 <table border="0" id="myform" width="100%" align="center"  cellpadding="0" cellspacing="0">
   <tr>
     <odin:textEdit property="worker110" label="工作"/> 
	 <odin:textEdit property="worker111" label="工作"/>
	 <odin:dateEdit property="aab1007" label="单位名称" ></odin:dateEdit>
   </tr>
  <tr>
     <odin:textEdit property="dwbm11" label="单位编码"/>
	 <odin:textEdit property="name11" label="单位名称" colspan="4"/>
   </tr>
   <tr>
      <odin:textEdit property="xming11" label="性命"/>
	  <odin:select property="zjlx11" editor="true" data="['1', '身份证'],['2', '毕业证'],['3', '军官正'],['4', '其他证件']" label="证件类型"></odin:select>
	  <td nowrap="nowrap"><odin:checkbox property="workplace" label="工作地点1" /></td>
	  <td>
	  <odin:checkbox property="workplace2" label="工作地点2" />
	  </td>
   </tr>
   <tr><td height="120"></td></tr>	 
 </table>   
</odin:simplePanel> 
</td></tr>
</table>
</odin:base>
</body>
</html>