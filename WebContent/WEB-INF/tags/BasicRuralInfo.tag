<%@ tag pageEncoding="GBK" body-content="empty" small-icon=""
 display-name="ũ����Ա������Ϣ" description="��Ա������Ϣ" %>
 <%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<% 
String ctxpathemp = request.getContextPath();
%>
<table width="98%" algin="center">
<tr><td>
	<odin:groupBox title="ũ����Ա������Ϣ">	
		<table border="0" cellpadding="0" cellspacing="0" align="center" width="100%" >
		<odin:tabLayOut/>
			<tr>
				<odin:textIconEdit property="eac012" label="�籣����" size="12"></odin:textIconEdit>
				<odin:textEdit property="aac003" label="����" readonly="true" size="12"/>
				<odin:textEdit property="AAC004" label="�Ա�" readonly="true" size="12"/>
				<odin:textEdit property="aac002" label="���֤����" readonly="true" size="20"/>			
			</tr>
			<tr>
				<odin:textEdit property="yblb" label="�������" readonly="true" size="12"/>
				<odin:textEdit property="aac006" label="��������" readonly="true" size="12"/>
				<odin:textEdit property="aab002" label="��ͥ��ַ" colspan="4" readonly="true" size="50"/>
			</tr>
		</table>
	</odin:groupBox>
</td></tr>
</table>