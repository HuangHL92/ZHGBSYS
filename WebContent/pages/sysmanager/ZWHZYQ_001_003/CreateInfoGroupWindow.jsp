<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>

<odin:groupBox title="��Ϣ���������Ϣ" width="300">
	<table border="0" id="myform" align="center" width="100%"
		cellpadding="0" cellspacing="0">
		<tr>
			<!-- <td><font color="red">*</font></td> -->
			<odin:textEdit property="infogroupname" label="��Ϣ��������" required="true"></odin:textEdit>
		</tr>
		<tr>
			<td align="right" colspan="6"><odin:button text="����"
				property="savebut"></odin:button>
			</td>
		</tr>
	</table>
</odin:groupBox>
