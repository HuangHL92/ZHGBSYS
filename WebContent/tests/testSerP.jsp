<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
测试页面
<input type="text" id = "text1" value="阿萨德法师打发斯蒂芬" width="50"/>
<input name="rxmlcell" type="button" value = "设置返回值" onclick="javascript:setReturnValue();"/>
</body>
<script type="text/javascript">
function setReturnValue(){
	var text1val = document.getElementById("text1").value;
	parent.getWinValue(text1val);
	alert("设置成功！");
}
</script>
</script>
</html>