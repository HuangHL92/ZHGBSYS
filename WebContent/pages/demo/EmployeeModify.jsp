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
	<odin:textForToolBar text="ҵ��������"/>
	<odin:fill/>
	<odin:buttonForToolBar text="��ӡ����"  handler="print"/>
	<odin:opLogButtonForToolBar/>
	<odin:buttonForToolBar isLast="true" text="����"  handler="doSave"/>
</odin:toolBar>

<odin:groupBox title="��Ա������Ϣ">	
	<table align="center" width="100%" >
	<odin:tabLayOut/>
		<tr>
			<odin:textIconEdit property="aac001" label="���˱���" onchange="javascript:getEmployeeInfo();"/>
			<odin:textEdit property="aac002" label="���֤����"/>
			<odin:textEdit property="aac003" label="����"/>
		</tr>
		<tr>
			<odin:dateEdit property="aac006" label="��������"/>
			<odin:select property="aac004" label="�Ա�" codeType="AAC004" data="['1', '1&nbsp;&nbsp;&nbsp;&nbsp;��'],['2', '2&nbsp;&nbsp;&nbsp;&nbsp;Ů']"/>
			<td colspan="2"/>
		</tr> 
	</table>
</odin:groupBox>

<odin:gridSelectColJs name="aae140" codeType="AAE140" selectData="['01', '��ҵ���ϱ���'],['04', '���˱���']"></odin:gridSelectColJs>

<odin:editgrid property="ac02" title="�α�����" width="780" height="200" autoFill="false">
<odin:gridJsonDataModel>
	<odin:gridDataCol name="check"/>
	<odin:gridDataCol name="aae140" />
  	<odin:gridDataCol name="eac018" type="float" isLast="true"/>
</odin:gridJsonDataModel>
<odin:gridColumnModel>
  	<odin:gridRowNumColumn />
  	<odin:gridColumn header="ѡ��"  width="40" dataIndex="check" editor="checkbox" edited="false"/>
  	<odin:gridColumn header="����"  width="100" dataIndex="aae140" codeType="AAE140" selectData="['01', '��ҵ���ϱ���'],['04', '���˱���']" editor="select" edited="false"/>
  	<odin:gridEditColumn header="�ɷѻ���" width="100" dataIndex="eac018" editor="number" isLast="true"/>
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
    <td><odin:button text="��ӡ����" handler="print"/></td>
    <td><odin:button text="&nbsp;����&nbsp;" handler ="doSave"/></td>
   </tr>
</table>  
 -->
</odin:form>
</odin:base>
<odin:response onSuccess="doSuccess"/>
</body>
</html>