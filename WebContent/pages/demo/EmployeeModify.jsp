<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>Demo</title>
<odin:head/>
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/demo/EmployeeModify.js"></script>
</head>

<body>
<odin:base>
<odin:form action="/pages/demo/employeeModifyAction.do?method=modify" method="post">
<tags:BasicMedSearch/>
<div id="btnToolBarDiv" style="height:0"></div>
<odin:toolBar property="btnToolBar" applyTo="btnToolBarDiv">
	<odin:textForToolBar text="业务输入区"/>
	<odin:fill/>
	<odin:buttonForToolBar text="打印单据"  handler="print"/>
	<odin:opLogButtonForToolBar/>
	<odin:buttonForToolBar isLast="true" text="保存"  handler="doSave"/>
</odin:toolBar>

<odin:groupBox title="人员基础信息">	
	<table align="center" width="100%" >
	<odin:tabLayOut/>
		<tr>
			<odin:textIconEdit property="aac001" label="个人编码" onchange="javascript:getEmployeeInfo();"/>
			<odin:textEdit property="aac002" label="身份证号码"/>
			<odin:textEdit property="aac003" label="姓名"/>
		</tr>
		<tr>
			<odin:dateEdit property="aac006" label="出生日期"/>
			<odin:select property="aac004" label="性别" codeType="AAC004" data="['1', '1&nbsp;&nbsp;&nbsp;&nbsp;男'],['2', '2&nbsp;&nbsp;&nbsp;&nbsp;女']"/>
			<td colspan="2"/>
		</tr> 
	</table>
</odin:groupBox>

<odin:gridSelectColJs name="aae140" codeType="AAE140" selectData="['01', '企业养老保险'],['04', '工伤保险']"></odin:gridSelectColJs>

<odin:editgrid property="ac02" title="参保险种" width="780" height="200" autoFill="false">
<odin:gridJsonDataModel>
	<odin:gridDataCol name="check"/>
	<odin:gridDataCol name="aae140" />
  	<odin:gridDataCol name="eac018" type="float" isLast="true"/>
</odin:gridJsonDataModel>
<odin:gridColumnModel>
  	<odin:gridRowNumColumn />
  	<odin:gridColumn header="选择"  width="40" dataIndex="check" editor="checkbox" edited="false"/>
  	<odin:gridColumn header="险种"  width="100" dataIndex="aae140" codeType="AAE140" selectData="['01', '企业养老保险'],['04', '工伤保险']" editor="select" edited="false"/>
  	<odin:gridEditColumn header="缴费基数" width="100" dataIndex="eac018" editor="number" isLast="true"/>
</odin:gridColumnModel>
<odin:gridJsonData>
	{
        data:[]
    }
</odin:gridJsonData>		
</odin:editgrid>

<!-- 
<table border="0" align="center" width="100%">
   <tr>
    <td width="100%"></td>
    <td><odin:button text="ajaxtest" handler="ajaxtest"/></td>
    <td><odin:button text="打印单据" handler="print"/></td>
    <td><odin:button text="&nbsp;保存&nbsp;" handler ="doSave"/></td>
   </tr>
</table>  
 -->
</odin:form>
</odin:base>
<odin:response onSuccess="doSuccess"/>
</body>
</html>