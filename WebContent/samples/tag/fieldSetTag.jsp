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
			<odin:select property="AKA130" label="报销类型"  required="true" editor="true" data="['1', '门诊报销'],['2', '住院报销'],['3', '基本特殊病报销'],['4', '补充特殊病报销']"/>
			<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
		</tr>
		<tr>
			<odin:dateEdit property="AKC192" label="就诊时间" required="true" mask="yyyy-mm-dd"/>
			<odin:dateEdit property="AKC194" label="结束时间" required="true" mask="yyyy-mm-dd"/>
			<odin:textIconEdit property="AKB020" label="就诊医院" required="true"/>
		</tr>
		<tr>
			<odin:textEdit property="AKC190"  required="true" label="就诊号"/>
			<odin:textIconEdit property="AKA121" label="就诊疾病" required="true"/>
			<odin:textEdit property="EKC113"  required="true" label="床位号"/>
		</tr>
		<tr>
			<odin:textEdit property="EKC122" label="出院科室" required="false"/>
			<odin:select disabled="true" property="AKC195" editor="true"  data="['1', '病愈出院'],['2', '死亡'],['3', '其他']" label="出院原因" required="false"/>
			<odin:numberEdit property="FPZS" label="发票张数" required="false"/>
		</tr>
		<tr>
			<odin:textEdit property="EKC124" label="病情描述" colspan="6" required="false"/>
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