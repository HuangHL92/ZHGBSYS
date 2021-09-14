<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>

<table>
	<tr>
		<td height="10"></td>
	</tr>
	<tr>
		<odin:textEdit property="name" label="用户组名称" required="true" />
		<odin:textEdit property="shortname" label="简称" />
	</tr>
	<tr>
		<odin:select property="status" label="状态"
			data="['1', '有效'],['0', '无效']" required="true"></odin:select>
		<odin:textEdit property="districtcode" label="地区代码" required="true"/>
	</tr>
	<tr>
		<odin:select property="rate" label="级别" codeType="RATE" required="true"></odin:select>
		<odin:select property="address" label="地址" codeType="AAB301"/>
	</tr>
	<tr>
		<odin:textEdit property="tel" label="电话" />
		<odin:textEdit property="linkman" label="联系人" />
	</tr>
	<tr>
		<odin:textEdit property="chargedept" label="主管部门" />
		<odin:textEdit property="org" label="系统机构编码" />
	</tr>
	<tr>
		<odin:textEdit property="desc" label="用户组描述" width="246" colspan="4"/>
	</tr>
	<tr>
		<odin:textEdit property="otherinfo" label="其他信息"/>
		
		<odin:textEdit property="principal" label="机构负责人" />
	</tr>
	<tr>
		<td width="50" height="50"></td>
		<td><odin:button text="保存" property="saveBtn"></odin:button></td>
	</tr>
</table>

