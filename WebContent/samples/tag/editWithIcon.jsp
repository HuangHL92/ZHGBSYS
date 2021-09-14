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
<table><tr><td align="center">
<div class="x-form-item"><div class="x-form-element">
	<input type="text" name="aab001" value="">
</div></div>
</td></tr></table>
<div id='toolbar'></div>
<script type="text/javascript">
function doTrigger(e){ 
	alert('1'); 
}
var aab001 = new Ext.form.TriggerField({ onTriggerClick:doTrigger,width:160,triggerClass:'x-form-search-trigger\" id=\'id\'' ,applyTo:'aab001'}); 
//alert(document.all('id').src);

var tb = new Ext.Toolbar();
    tb.render('toolbar');
    tb.add({
            text:'Button w/ Menu',
            iconCls: 'bmenu'

        },{
            text:'&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;'

        },new Ext.form.TriggerField({ 
            onTriggerClick:doTrigger,width:160,
            triggerClass:'x-form-search-trigger\" id=\'id\''})
        );

</script>
</odin:base>
</body>
</html>