<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>



<odin:groupBox title="�����Ļ�����Ϣ">
	<table border="0" id="myform" align="center"  width="100%"  cellpadding="0" cellspacing="0">
		<tr height="20">
			<odin:textEdit property="updatename" required="true" label="������������"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="updatedescription" label="����"></odin:textEdit>
		</tr>
		<tr>
			<odin:select property="updatestatus" editor="false" label="״̬">['1','��Ч'],['0','��Ч']</odin:select>
		</tr>
		<tr>
			<td align="right" colspan="3" >
				<odin:button text="������Ϣ" property="update" ></odin:button> 
			<td>
		</tr>
 	</table>
 </odin:groupBox> 