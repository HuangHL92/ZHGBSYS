<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>



<odin:groupBox title="场景的基本信息">
	<table border="0" id="myform" align="center"  width="100%"  cellpadding="0" cellspacing="0">
		<tr height="20">
			<odin:textEdit property="updatename" required="true" label="场景的名名称"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="updatedescription" label="描述"></odin:textEdit>
		</tr>
		<tr>
			<odin:select property="updatestatus" editor="false" label="状态">['1','有效'],['0','无效']</odin:select>
		</tr>
		<tr>
			<td align="right" colspan="3" >
				<odin:button text="保存信息" property="update" ></odin:button> 
			<td>
		</tr>
 	</table>
 </odin:groupBox> 