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
<table><tr><td width="80%">
<div class="x-form">
<div id="fieldset"></div>	
</div>
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
 <input type="button" value="debug"  onclick="Ext.log('Hello from the Ext console.');return false;">
</odin:base>	
<script>
Ext.onReady(function(){
var fieldSet = new Ext.form.FieldSet({
            xtype:'fieldset',
            checkboxToggle:true,
            collapsible: true,
            title: 'User Information',
            autoHeight:true,
            width:'100%',
            collapsed: true,
            contentEl:'test',
            renderTo:'fieldset'
        });
});        
</script>
</body>
</html>