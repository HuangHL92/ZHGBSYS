<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<meta http-equiv="X-UA-Compatible" content="IE=8">
<script type="text/javascript">
function parentload(){
	//window.parent.location.reload();
	window.parent.load();
}
function msg(){
	alert("���Ƴɹ�"); 
}

</script>
<style>
 body{background:#DFE8F6}
 #tool{width:333}
</style>
<body>

<div>
	<table>
		<tr>
			<td width="30"></td>
		    <td  style="font-size: 12">ѡ��Ŀ��У�鷽��:</td>
			<td height="133" ><odin:select2 property="vsc001" maxHeight="50"></odin:select2></td>
		</tr>
	</table>
			
</div>
<div id="tool"></div>
<odin:toolBar property="" applyTo="tool">
  <odin:fill/>
  <odin:separator/>
  <odin:buttonForToolBar text="����" id="copy" ></odin:buttonForToolBar>
  <odin:separator/>
  <odin:buttonForToolBar text="�ر�" id="close" isLast="true"></odin:buttonForToolBar>
</odin:toolBar>
</body>