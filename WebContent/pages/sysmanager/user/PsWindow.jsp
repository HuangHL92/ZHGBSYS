<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>


<style>
	div{
		text-align: center;
	}
</style>
<script type="text/javascript">
	//旧密码校验
	function odpasswordMarkTrue(){
		document.getElementById('odpasswordMark').style.display='inline';
		document.getElementById('odpasswordMark').src = 'images/d.png';
		document.getElementById('hint').value = '';
	}
	function odpasswordMarkFalse(){
		document.getElementById('odpasswordMark').style.display='inline';
		document.getElementById('odpasswordMark').src = 'images/c.png';
		document.getElementById('hint').value = '输入的旧密码不正确，请重新输入！';
	}
	//新密码校验
	function newpasswordTrue(){
		document.getElementById('newpasswordMark').style.display='inline';
		document.getElementById('newpasswordMark').src = 'images/d.png';
		document.getElementById('hint').value = '';
	}
	function newpasswordFalse1(){
		document.getElementById('newpasswordMark').style.display='inline';
		document.getElementById('newpasswordMark').src = 'images/c.png';
		document.getElementById('hint').value = '请先输入旧密码！！';
	}
	function newpasswordFalse2(){
		document.getElementById('newpasswordMark').style.display='inline';
		document.getElementById('newpasswordMark').src = 'images/c.png';
		document.getElementById('hint').value = '请输入新密码！！';
	}
	function newpasswordFalse3(){
		document.getElementById('newpasswordMark').style.display='inline';
		document.getElementById('newpasswordMark').src = 'images/c.png';
		document.getElementById('hint').value = '不能与新密码相同！';
	}
	function newpasswordFalse4(){
		document.getElementById('newpasswordMark').style.display='inline';
		document.getElementById('newpasswordMark').src = 'images/c.png';
		document.getElementById('hint').value = '密码长度必须大于10位，由数字，字母，特殊字符组成！';
	}
	
	//确认密码校验
	function checkpasswordFalse1(){
		document.getElementById('checkpasswordMark').style.display='inline';
		document.getElementById('checkpasswordMark').src = 'images/c.png';
		document.getElementById('hint').value = '请先输入密码！';
	}
	function checkpasswordFalse2(){
		document.getElementById('checkpasswordMark').style.display='inline';
		document.getElementById('checkpasswordMark').src = 'images/c.png';
		document.getElementById('hint').value = '请先输入新密码！';
	}
	function checkpasswordFalse3(){
		document.getElementById('checkpasswordMark').style.display='inline';
		document.getElementById('checkpasswordMark').src = 'images/c.png';
		document.getElementById('hint').value = '两次输入的新密码不一样！';
	}
	function checkpasswordTrue(){
		document.getElementById('checkpasswordMark').style.display='inline';
		document.getElementById('checkpasswordMark').src = 'images/d.png';
		document.getElementById('hint').value = '';
	}
	function showResult(btn){
		parent.location.href='LogonDialog.jsp';
	}
	
	
</script>
<div>


<odin:groupBox title="修改密码" property="gropBoxId">
	<table>
		<tr>
			<td>
				<span style="color: red;">*</span><odin:textEdit property="odpassword" inputType="password" label="旧&nbsp;&nbsp;密&nbsp;&nbsp;码"></odin:textEdit>
			</td>
			<td>
				<img  src="" width="20" height="20" id="odpasswordMark" style="display: none;"  />
			</td>
		</tr>
		<tr>
			<td>
				<span style="color: red;">*</span><odin:textEdit property="newpassword" inputType="password" label="新&nbsp;&nbsp;密&nbsp;&nbsp;码"></odin:textEdit>
			</td>
			<td>
				<img src="" width="20" height="20" id="newpasswordMark" style="display: none;"/>
			</td>
		</tr>
		<tr>
			<td>
				<span style="color: red;">*</span><odin:textEdit property="checkpassword" inputType="password" label="确认新密码" ></odin:textEdit>
			</td>
			<td>
				<img src="" width="20" height="20" id="checkpasswordMark" style="display: none;"/>
			</td>
		</tr>
		
	</table>
	<table style="width: 100%;">
		<tr>
			<td style="padding-right: 20px;">&nbsp;</td>
			<td style="text-align: center;">
				<odin:button property="savePd" text="保存"></odin:button>
			</td>
		</tr>
	</table>
</odin:groupBox>

<input id="hint" type="text" value="" style="border: none;text-align: center;color: red;width:98%;background-color: white;" disabled="disabled" readonly="readonly"/>


</div>


