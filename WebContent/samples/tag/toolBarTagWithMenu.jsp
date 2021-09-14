<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>    
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>Insert title here</title>
<odin:head/>
<style type="text/css">
.menu-title{
    background: #ebeadb url(../../basejs/ext/resources/images/default/grid/grid3-hrow.gif) repeat-x;
    border-bottom:1px solid #99bbe8;
    color:#15428b;
    font:bold 10px tahoma,arial,verdana,sans-serif;
    display:block;
    padding:3px;
}
</style>
</head>                            
<body>
<odin:base>                             
<script>    
function doClick(){
   alert(1);
}
function onItemCheck(item, checked){
  alert(item.text+' the '+(checked ? 'checked' : 'unchecked')+' menu item.');
}
</script>
<div id="toolBarDiv"></div>
<script>
<odin:dateMenu property="mydateMenu"></odin:dateMenu>
<odin:colorMenu property="mycolorMenu"></odin:colorMenu>
<odin:menu property="myRadioMenu">
    <odin:textForToolBar text="<b class=menu-title >Choose a Theme</b>"></odin:textForToolBar>
	<odin:menuCheckItem text="��ѯ" group="g" checked="true" checkHandler="onItemCheck"></odin:menuCheckItem>
	<odin:menuCheckItem text="��ѡ" group="g"></odin:menuCheckItem>
	<odin:menuCheckItem text="ˢ��" group="g" isLast="true"></odin:menuCheckItem>
</odin:menu> 
<odin:menu property="myMenu">
	<odin:menuItem text="ֹͣ" ></odin:menuItem>
	<odin:menuItem text="����"></odin:menuItem> 
	<odin:menuItem icon="../../basejs/ext/resources/images/default/shared/calendar.gif" text="����" menu="mydateMenu"></odin:menuItem>
	<odin:menuItem text="��ɫ" menu="mycolorMenu"></odin:menuItem>
	<odin:menuSeparator />
	<odin:menuCheckItem text="��ѯ" checked="true" checkHandler="onItemCheck"></odin:menuCheckItem>
	<odin:menuItem text="��ѡ" menu="myRadioMenu"></odin:menuItem>
	<odin:menuItem text="ˢ��" isLast="true"></odin:menuItem>
</odin:menu>
</script>

                      
<odin:toolBar property="myToolBar" applyTo="toolBarDiv">
  <odin:textForToolBar text="<img src=/insiis/img/icon/member.gif >"></odin:textForToolBar>
  <odin:buttonForToolBar text="�˵�" split="true" menu="myMenu"></odin:buttonForToolBar>
  <odin:buttonForToolBar text="�ļ�" menu="myMenu"></odin:buttonForToolBar>
  <odin:fill></odin:fill>                                                
  <odin:buttonForToolBar  text="���"  handler="doClick" icon="../../basejs/ext/resources/images/default/dd/drop-no.gif" cls="x-btn-text-icon"></odin:buttonForToolBar>
  <odin:separator />
  <odin:buttonForToolBar isLast="true" text="����"  handler="doClick" icon="../../basejs/ext/resources/images/default/form/exclamation.gif" cls="x-btn-text-icon"></odin:buttonForToolBar>
</odin:toolBar>
<table width="100%"><tr>
<td width="50%">
   <input type="button" value="debug"  onclick="Ext.log('Hello from the Ext console.');return false;"> 
</td>
<td width="50%"><div id="menuDiv"></div></td></tr>
</table>  
<script>
var menuDiv1 = <odin:menuItem applyTo="menuDiv" text="����" menu="mydateMenu" isLast="true"></odin:menuItem>; 
menuDiv1.render(Ext.get('menuDiv'));
</script>
</odin:base>
</body>
</html>