<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%
	String ctxpath = request.getContextPath();
%>
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
	 <odin:textEdit property="worker2" colspan="2"/>
   </tr>
   <tr> 
	  <odin:select property="zjlx1" editor="true" data="['1', '身份证'],['2', '毕业证'],['3', '军官正'],['4', '其他证件']" label="证件号"></odin:select>
      <odin:textEdit property="xming1" label="姓名" /> 
      <odin:select property="zjlx2" editor="false" data="['1', '男'],['2', '女'],['3', '军官正'],['4', '其他证件']" label="性别"></odin:select>
   </tr>
   <tr>
     <odin:dateEdit property="birthday" label="出生日期" /> 
     <odin:dateEdit property="startdate"  label="参加工作日期" />
     <odin:select property="zjlx12" editor="false" data="['1', '身份证'],['2', '毕业证'],['3', '军官正'],['4', '其他证件']" label="户口性质"></odin:select>
   </tr>
</table>
</odin:groupBox> 
<odin:groupBox title="变动信息">
<table border="0" id="myform" align="center" width="100%"  cellpadding="0" cellspacing="0">
<odin:tabLayOut />
   <tr>
     <odin:textEdit property="worker01" label="变动类型"/>   
	 <odin:textEdit property="worker11" label="变动原因"/>
	 <odin:dateEdit property="birthday1" label="变动年月" format="Ym" maxlength="6"/> 
   </tr>
   <tr> 
	 <odin:dateEdit property="birthday2" label="受理日期" />
	 <odin:textEdit property="worker112" label="变动备注" colspan="4"/>
   </tr>
</table>
</odin:groupBox> 
<odin:groupBox title="业务信息">
<table border="0" id="myform" align="center" width="100%"  cellpadding="0" cellspacing="0">
<odin:tabLayOut />
   <tr>
     <odin:textEdit property="worker012" label="用工性质"/>   
	 <odin:textEdit property="worker1122" label="特殊工种"/>
	 <odin:select property="zjl5x1" editor="true" data="['1', '身份证'],['2', '毕业证'],['3', '军官正'],['4', '其他证件']" label="特殊工种月数"></odin:select>
   </tr>
   <tr>
     <odin:textEdit property="wo2rker012" label="医保类型"/>   
	 <odin:textEdit property="worke2r1122" label="申报基数"/>
	 <td></td>
	 <td nowrap="nowrap"><odin:checkbox property="workplace" label="经办机构自收" /></td>
   </tr>
   <tr> 
	 <odin:textEdit property="worker1152" colspan="6" label="档案备注"/>
   </tr>
</table>
</odin:groupBox> 
</td>
</tr>
</table>
<odin:gridWithCheckBox property="grid1" title="险种信息" width="750" height="160">
<odin:gridDataModel>
  <odin:gridDataCol name="company" />
  <odin:gridDataCol name="price" type="float"/>   
  <odin:gridDataCol name="change" type="float"/>
  <odin:gridDataCol name="pctChange" type="float"/>
  <odin:gridDataCol name="lastChange" isLast="true"/>
</odin:gridDataModel>
<odin:gridColumnModel>
  <odin:gridSmColumn />
  <odin:gridColumn  id="yes" header="险种" width="120" dataIndex="company" />
  <odin:gridColumn  header="本次参保时间" dataIndex="price" />
  <odin:gridColumn  header="缴费基数"  dataIndex="change" />
  <odin:gridColumn  dataIndex="补缴开始年月" />
  <odin:gridColumn  header="缴费比例" dataIndex="lastChange" isLast="true"/>
</odin:gridColumnModel>
<odin:griddata>
        ['',,,,''],
        ['',,,,''],
        ['',,,,''],
        ['',,,,''],
        ['',,,,'']
</odin:griddata>		
</odin:gridWithCheckBox>
<table border="0" id="myform" align="center" width="750"  cellpadding="0" cellspacing="0">
   <tr>
    <td width="500"><odin:button text="打印单据" handler ="doSave"/></td>
    <td></td>
    <td width="40"><odin:button text="操作日志" handler ="doSave" /></td>
    <td width="40"><odin:button text="清屏" handler ="doSave" /></td>
    <td width="40"><odin:button text="保存" handler ="doSave" /></td>
    <td width="40"><odin:button text="关闭" handler ="doSave" /></td>
   </tr>
</table>  

</odin:base>
</body>
</html>