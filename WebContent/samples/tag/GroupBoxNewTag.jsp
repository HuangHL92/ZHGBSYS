<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>    
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>Insert title here</title>
<odin:head/>
</head>
<body>
<odin:base>

<div id="test1">
<table border="0"  align="center" width="750" cellpadding="0" cellspacing="0">
		<odin:tabLayOut />
		<tr>
			<odin:select property="A1KA130" label="��������"  required="true" editor="true" data="['1', '���ﱨ��'],['2', 'סԺ����'],['3', '�������ⲡ����'],['4', '�������ⲡ����']"/>
			<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
		</tr>
		<tr>
			<odin:dateEdit property="A1KC192" label="����ʱ��" required="true" mask="yyyy-mm-dd"/>
			<odin:dateEdit property="A1KC194" label="����ʱ��" required="true" mask="yyyy-mm-dd"/>
			<odin:textIconEdit property="A1KB020" label="����ҽԺ" required="true"/>
		</tr>
		<tr>
			<odin:textEdit property="A1KC190"  required="true" label="�����"/>
			<odin:textIconEdit property="A1KA121" label="���Ｒ��" required="true"/>
			<odin:textEdit property="E1KC113"  required="true" label="��λ��"/>
		</tr>
		<tr>
			<odin:textEdit property="EK1C122" label="��Ժ����" required="false"/>
			<odin:select disabled="true" property="A1KC195" editor="true"  data="['1', '������Ժ'],['2', '����'],['3', '����']" label="��Ժԭ��" required="false"/>
			<odin:numberEdit property="F1PZS" label="��Ʊ����" required="false"/>
		</tr>
		<tr>
			<odin:textEdit property="E1KC124" label="��������" colspan="6" required="false"/>
		</tr>
	</table>
</div>

<table width="100%"><tr><td align="center">
<odin:groupBoxNew contentEl="test1"   property="mygroupBox" 
     title="������Ϣ" checkboxToggle="true" collapsed="true"></odin:groupBoxNew>	
</td></tr></table>


             
<div id="test">   
<table border="0" id="myform2" align="center" width="750" cellpadding="0" cellspacing="0">
		<odin:tabLayOut />
		<tr>
			<odin:select property="AKA130" label="��������"  required="true" editor="true" data="['1', '���ﱨ��'],['2', 'סԺ����'],['3', '�������ⲡ����'],['4', '�������ⲡ����']"/>
			<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
		</tr>
		<tr>
			<odin:dateEdit property="AKC192" label="����ʱ��" required="true" mask="yyyy-mm-dd"/>
			<odin:dateEdit property="AKC194" label="����ʱ��" required="true" mask="yyyy-mm-dd"/>
			<odin:textIconEdit property="AKB020" label="����ҽԺ" required="true"/>
		</tr>
		<tr>
			<odin:textEdit property="AKC190"  required="true" label="�����"/>
			<odin:textIconEdit property="AKA121" label="���Ｒ��" required="true"/>
			<odin:textEdit property="EKC113"  required="true" label="��λ��"/>
		</tr>
		<tr>
			<odin:textEdit property="EKC122" label="��Ժ����" required="false"/>
			<odin:select disabled="true" property="AKC195" editor="true"  data="['1', '������Ժ'],['2', '����'],['3', '����']" label="��Ժԭ��" required="false"/>
			<odin:numberEdit property="FPZS" label="��Ʊ����" required="false"/>
		</tr>
		<tr>
			<odin:textEdit property="EKC124" label="��������" colspan="6" required="false"/>
		</tr>
	</table>
</div>

<table width="100%"><tr><td align="center">
<odin:groupBoxNew contentEl="test" collapsible="true"   property="mygroupBox1" 
     title="������Ϣ"></odin:groupBoxNew>	
</td></tr></table> 

</odin:base>	
</body>
</html>