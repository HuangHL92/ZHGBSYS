<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>


<odin:groupBox title="�û�������Ϣ">
	<table border="0" id="myform" align="center" width="100%"
		cellpadding="0" cellspacing="0">
		<tr>
			<odin:textEdit property="wdloginname" required="true" label="�û���¼��"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="wdusername" label="�û���"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="wdpassword" inputType="password" label="����"></odin:textEdit>
		</tr>
		<!-- tr>
			<odin:select property="wdstatus" label="״̬" editor="false">['1','��Ч'],['0','��Ч']</odin:select>
		</tr>
		 -->
		<tr>
			<odin:textEdit property="desc" label="����"></odin:textEdit>
		</tr>
		<tr>
			<odin:select property="useful" data="['1','��Ч'],['0','��Ч']" label="�û�״̬"></odin:select>
		</tr>
		<tr>
			<td align="right" colspan="4"><odin:button text="����"
				property="savebut"></odin:button>
			<td>
		</tr>
	</table>
</odin:groupBox>






