<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.chooseZDYtemPageModel"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin-commform.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/cellweb.css">


<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title></title>
<odin:commformhead/>
<odin:commformMDParam></odin:commformMDParam>
</head>
<body>
<form action="" method="post" >  
  <table> 
  	<tr ></tr> 
  	<tr ></tr> 
    <tr>
      <td width="10px"></td>   
      <td>选择excel文件:</td>  
      <td><input type="file" name="upload" id="name" width="100%"></td>  
   </tr>  
   <tr ></tr> 
    <tr>
      <td></td>   
      <td width="100px"></td>    
      <td   colspan="2">
      	<input type="submit" style="cursor:hand;" value="  导入 " onclick="daoru()">
      </td>  
    </tr>
    <tr ></tr> 
    <tr width="100px" >
		<td colspan="4">
			<label id="bz1" style="font-size: 12;color: red">注：本程序可以导入Excel 2.1,Excel 3.0,Excel 4.0,Excel 5.0,Excel 95,Excel 97,Excel 2000,Excel XP,Excel 2003 文件。导入前请先关闭打开该文件。</label><br>
			<label id="bz1" style="font-size: 12;color: red">注：请选择对应的模板进行导入。</label><br>
		</td>
	</tr>  
  </table>  
</form> 
</body>
</html>
<script type="text/javascript">
function daoru(){
	var name = document.getElementById('name').value;
	parent.toleadcexcel(name);
}
///次方法已经放弃
function daoru1(){
	var cell = document.getElementById("cellweb1");
	var name = document.getElementById('name').value;
	var Namefile = new String(name);
	var a = Namefile.indexOf('.');
	var b = Namefile.length;
	var houzhui = Namefile.substring((a+1),b);
	alert(houzhui); 
	if(houzhui == 'xls'){
		var jqname = Namefile.substring((Namefile.lastIndexOf('\\')+1), Namefile.indexOf('.'));
///		var aa = cell.ImportExcelFile(name);
		parent.toleadcll22(jqname);
	}else{
		$h.alert("选择的文件不是excel文件");
		
	}
	return;
	
}
</script>

