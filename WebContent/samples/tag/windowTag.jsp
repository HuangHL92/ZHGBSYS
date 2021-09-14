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
<table border="0" id="myform" align="center" width="100%"  cellpadding="0" cellspacing="0">
<odin:tabLayOut />
<tr>
	 <odin:textIconEdit property="dwxx" label="单位信息"></odin:textIconEdit>
</tr>
<tr>
	 <odin:textIconEdit property="dwxx2" label="单位信息" iconId="btn2"></odin:textIconEdit>
</tr>
</table>
<input type="button" id="btn" value="计算" /><input type="button" id="btn" value="传参数" onclick="doSetPFWindow()"/>
<odin:window width="700" title="window" buttonId="btn" id="window" src="/samples/tag/propertyGridTag.jsp"></odin:window>
<odin:window buttonId="btn2" maximizable="true"  id="window2" src="/samples/tag/propertyGridTag.jsp"></odin:window>
<script>
var i=1;
function doSetPFWindow()
{
    //Ext.getCmp('window2').html = "<iframe width=\"100%\" height=\"100%\" src=\"/insiis/samples/tag/propertyGridTag.jsp?test=test\"></iframe>";
    //Ext.getCmp('window2').show(Ext.getCmp('window2'));
    odin.showWindowWithSrc('window2','/insiis/samples/tag/propertyGridTag.jsp?test='+i);
    i++;
}
</script>
<table><tr><td align="center">
<div class="x-form-item"><div class="x-form-element">
	<input type="text" name="aab001" value="">
</div></div>
</td></tr></table>
<script type="text/javascript">
function doTrigger(e){ 
	//alert('1'); 
}
var aab001 = new Ext.form.TriggerField({ onTriggerClick:doTrigger,width:160,triggerClass:'x-form-search-trigger\" id=\'id\'' ,applyTo:'aab001'}); 
//alert(document.all('id').src);

</script>
</body>
</html>