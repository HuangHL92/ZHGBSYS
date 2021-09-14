<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>代码表信息</title>
<odin:head/>
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/comm/codeparameter/addParameterStub.js"></script>
</head>

<body>
<odin:base>

<table width="98%" align="center" >      
<tr>
	<td>
	   <odin:groupBox title="增加参数" >
		   	<table width="100%"  cellpadding="0" cellspacing="0" >
				<tr> 
		   			<odin:textEdit property="aaa100" label="代码类别" />	
		   		</tr>
		   		<tr>		
		   			<odin:textEdit property="aaa101" label="类别名称" />
		   		</tr>
		   		<tr>	
		   			<td align="center" colspan="2">
		   				<table>
		   					<tr>
		   						<td><odin:button text="保 存" handler ="yes" /></td>
		   						<td><odin:button text="取 消" handler ="no"/></td>
		   					</tr>
		   				</table>
		   			</td>
		   		</tr>
		   </table>
		</odin:groupBox>
	</td>	   
</tr>
</table>

<script>
	
	window.returnValue=false;
	
	function yes(){
		var aaa100 = document.all.aaa100.value;
		var aaa101 = document.all.aaa101.value;
		addParameter(aaa100,aaa101,addSucc,addFail);
	}
	
	function no(){
		window.returnValue=false;
		window.close();
	}
	
	function addSucc(){
		alert("添加成功！");
		window.returnValue=true;
		window.close();
	}
	
	function addFail(response){
		odin.showErrorMessage(response);
	}
	
	
</script>

</odin:base>
</body>
</html>