<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<%@page import="com.insigma.siis.local.pagemodel.publicServantManage.ExpTimeWindowPageModel"%>
<odin:groupBox property="s10" title="请填写导出时间">
<table>
<tr>
<td>
<odin:NewDateEditTag property="time" label="导出时间" maxlength="8" isCheck="true"></odin:NewDateEditTag>
</td>
<td width="80px"></td>
<td>
<odin:button text="确定" property="sureBtn"></odin:button>
</td>
</tr>
</table>
</odin:groupBox>