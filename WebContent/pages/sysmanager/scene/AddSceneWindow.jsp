<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>



<odin:groupBox title="�����Ļ�����Ϣ" >
	<table border="0" id="myform" align="center"  width="100%"  cellpadding="0" cellspacing="0">
		<tr height="20">
			<odin:textEdit property="name" required="true" label="������������"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="description" label="����"></odin:textEdit>
		</tr>
		<tr>
			<odin:select property="status" required="true" label="״̬" editor="false">['1','��Ч'],['0','��Ч']</odin:select>
		</tr>
		<tr>
			<td align="right" colspan="4" >
				<odin:button text="������Ϣ" property="saveSceneBtn" ></odin:button> 
			<td>
		</tr>
 	</table>
 </odin:groupBox> 