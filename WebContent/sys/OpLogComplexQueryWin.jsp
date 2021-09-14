<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<html>
<head>
<title>高级搜索</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/sys/OpLogComplexQueryWin.js"></script>
<odin:head/>
</head>

<body>
<odin:base>
		<table >
			<tr>
				<td>
					<table>
					<tr>
						<odin:dateEdit property="opperiods" label="&nbsp;&nbsp;业务期" format="Ym"/>
						<odin:dateEdit property="opperiode" label="&nbsp;-" format="Ym"/>
					</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<table>
					<tr>
						<odin:dateEdit property="querydates" label="查询日期"/>
						<odin:dateEdit property="querydatee" label="&nbsp;-" />
					</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<table>
					<tr>
						<td>
							<table>
							<tr>
								<odin:textEdit property="functiontitle" label="业务名称" width="340"/>
							</tr>
							</table>
						</td>
					</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<table>
					<tr>
						<td>
							<table>
							<tr>
								<odin:textEdit property="digest" label="&nbsp;&nbsp;&nbsp;&nbsp;摘要" width="340"/>
							</tr>
							</table>
						</td>
					</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<table width="410">
					<tr>				
						<odin:select property="aae011" label="经办人" codeType="AAE011" maxHeight="120"/>
						<td><odin:checkbox property="rbflag" label="包括已回退的日志" /></td>
					</tr>
					</table>
				</td>
			</tr>
			<tr><td height="50"></td></tr>
			<tr>
				<td align="center">
					<table>
					<tr>
						<td><odin:button property="complexquery" text="&nbsp;搜索&nbsp;" handler ="complexQuery" /></td>
						<td width="10"></td>
						<td><odin:button property="complexquery" text="&nbsp;关闭&nbsp;" handler ="doClose" /></td>
					</tr>
					</table>
				</td>
			</tr>
		</table>
<script>
	Ext.onReady(function(){
		init();		
	});
</script>
</odin:base>
</body>
</html>