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
      <td>ѡ��excel�ļ�:</td>  
      <td><input type="file" name="upload" id="name" width="100%"></td>  
   </tr>  
   <tr ></tr> 
    <tr>
      <td></td>   
      <td width="100px"></td>    
      <td   colspan="2">
      	<input type="submit" style="cursor:hand;" value="  ���� " onclick="daoru()">
      </td>  
    </tr>
    <tr ></tr> 
    <tr width="100px" >
		<td colspan="4">
			<label id="bz1" style="font-size: 12;color: red">ע����������Ե���Excel 2.1,Excel 3.0,Excel 4.0,Excel 5.0,Excel 95,Excel 97,Excel 2000,Excel XP,Excel 2003 �ļ�������ǰ���ȹرմ򿪸��ļ���</label><br>
			<label id="bz1" style="font-size: 12;color: red">ע����ѡ���Ӧ��ģ����е��롣</label><br>
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
///�η����Ѿ�����
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
		$h.alert("ѡ����ļ�����excel�ļ�");
		
	}
	return;
	
}
</script>

