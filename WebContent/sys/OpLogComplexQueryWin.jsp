<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<html>
<head>
<title>�߼�����</title>
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
						<odin:dateEdit property="opperiods" label="&nbsp;&nbsp;ҵ����" format="Ym"/>
						<odin:dateEdit property="opperiode" label="&nbsp;-" format="Ym"/>
					</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td>
					<table>
					<tr>
						<odin:dateEdit property="querydates" label="��ѯ����"/>
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
								<odin:textEdit property="functiontitle" label="ҵ������" width="340"/>
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
								<odin:textEdit property="digest" label="&nbsp;&nbsp;&nbsp;&nbsp;ժҪ" width="340"/>
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
						<odin:select property="aae011" label="������" codeType="AAE011" maxHeight="120"/>
						<td><odin:checkbox property="rbflag" label="�����ѻ��˵���־" /></td>
					</tr>
					</table>
				</td>
			</tr>
			<tr><td height="50"></td></tr>
			<tr>
				<td align="center">
					<table>
					<tr>
						<td><odin:button property="complexquery" text="&nbsp;����&nbsp;" handler ="complexQuery" /></td>
						<td width="10"></td>
						<td><odin:button property="complexquery" text="&nbsp;�ر�&nbsp;" handler ="doClose" /></td>
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