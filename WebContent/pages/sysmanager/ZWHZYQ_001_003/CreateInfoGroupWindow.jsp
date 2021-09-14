<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>

<odin:groupBox title="信息项组基本信息" width="300">
	<table border="0" id="myform" align="center" width="100%"
		cellpadding="0" cellspacing="0">
		<tr>
			<!-- <td><font color="red">*</font></td> -->
			<odin:textEdit property="infogroupname" label="信息项组名称" required="true"></odin:textEdit>
		</tr>
		<tr>
			<td align="right" colspan="6"><odin:button text="保存"
				property="savebut"></odin:button>
			</td>
		</tr>
	</table>
</odin:groupBox>
