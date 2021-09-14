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
<table width="98%" align="center">
<tr><td>
<odin:groupBox title="基本信息">
<table border="0" id="myform" align="center" width="100%"  cellpadding="0" cellspacing="0">
<odin:tabLayOut />
   <tr>
     <odin:textEdit property="worker0" label="社保编码"/>   
	 <odin:textEdit property="worker1" label="单位编号"/>
	 <odin:textEdit property="worker2"/>
   </tr>
   <tr> 
	  <odin:select property="zjlx1" editor="true" data="['1', '身份证'],['2', '毕业证'],['3', '军官正'],['4', '其他证件']" label="证件号"></odin:select>
      <odin:textEdit property="xming1" label="姓名"/> 
      <odin:select property="zjlx2" editor="false" data="['1', '男'],['2', '女'],['3', '军官正'],['4', '其他证件']" label="性别"></odin:select>
   </tr>
   <tr>
     <odin:dateEdit property="birthday" label="出生日期" /> 
     <odin:dateEdit property="startdate" label="工作日期" />
     <odin:select property="zjlx12" editor="true" data="['10', '10　　　　身份证'],['11', '11　　　　毕业证'],['12', '12　　　　军官正'],['90', '90　　　　其他证件']" label="户口性质"></odin:select>
   </tr>
</table>
</odin:groupBox> 
</td>
</tr>
</table>
<table width="98%" align="center">
<tr><td>
<odin:groupBox title="变动信息">
<table border="0" id="myform" align="center" width="100%"  cellpadding="0" cellspacing="0">
<odin:tabLayOut />
   <tr>
     <odin:textEdit property="worker01" label="变动类型" />   
	 <odin:textEdit property="worker11" label="变动原因" />
	 <odin:dateEdit property="birthday1"  label="变动年月"/>
   </tr>
   <tr> 
	 <odin:dateEdit property="birthday2"  label="受理日期" />
	 <odin:textEdit property="worker112"  label="变动备注"/>
   </tr>
</table>
</odin:groupBox> 
</td></tr></table>
<table width="98%" align="center">
<tr><td>
<odin:groupBox title="业务信息">
<table border="0" id="myform" align="center" width="100%"  cellpadding="0" cellspacing="0">
<odin:tabLayOut />
   <tr>
     <odin:textEdit property="worker012" label="用工性质" />   
	 <odin:textEdit property="worker1122" label="特殊工种" />
	 <odin:select property="zjl5x1" editor="true"  data="['1', '身份证'],['2', '毕业证'],['3', '军官正'],['4', '其他证件']" label="工种月数"></odin:select>
   </tr>
   <tr>
     <odin:textEdit property="wo2rker012" label="医保类型" />   
	 <odin:textEdit property="worke2r1122" label="申报基数"/>
	 <td></td>
	 <td nowrap="nowrap"><odin:checkbox property="workplace" label="经办机构自收" /></td>
   </tr>
   <tr> 
	 <odin:textEdit property="worker1152" colspan="6" label="档案备注"/>
   </tr>
</table>
</odin:groupBox> 
</td></tr></table>
</odin:base>
</body>
</html>