<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>


<odin:groupBox title="用户基本信息">
	<table border="0" id="myform" align="center" width="100%"
		cellpadding="0" cellspacing="0">
		<tr>
			<odin:textEdit property="wdloginname" required="true" label="用户登录名"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="wdusername" label="用户名"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="wdpassword" inputType="password" label="密码"></odin:textEdit>
		</tr>
		<!-- tr>
			<odin:select property="wdstatus" label="状态" editor="false">['1','有效'],['0','无效']</odin:select>
		</tr>
		 -->
		<tr>
			<odin:textEdit property="desc" label="描述"></odin:textEdit>
		</tr>
		<tr>
			<odin:select property="useful" data="['1','有效'],['0','无效']" label="用户状态"></odin:select>
		</tr>
		<tr>
			<td align="right" colspan="4"><odin:button text="保存"
				property="savebut"></odin:button>
			<td>
		</tr>
	</table>
</odin:groupBox>






