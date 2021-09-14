<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>



<odin:groupBox title="场景的基本信息" >
	<table border="0" id="myform" align="center"  width="100%"  cellpadding="0" cellspacing="0">
		<tr height="20">
			<odin:textEdit property="name" required="true" label="场景的名名称"></odin:textEdit>
		</tr>
		<tr>
			<odin:textEdit property="description" label="描述"></odin:textEdit>
		</tr>
		<tr>
			<odin:select property="status" required="true" label="状态" editor="false">['1','有效'],['0','无效']</odin:select>
		</tr>
		<tr>
			<td align="right" colspan="4" >
				<odin:button text="保存信息" property="saveSceneBtn" ></odin:button> 
			<td>
		</tr>
 	</table>
 </odin:groupBox> 