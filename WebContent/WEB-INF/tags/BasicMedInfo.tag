<%@ tag pageEncoding="GBK" body-content="empty" small-icon=""
 display-name="ҽ����Ա������Ϣ" description="��Ա������Ϣ" %>
 <%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<% 
String ctxpathemp = request.getContextPath();
%>
<table width="98%" algin="center">
<tr><td>
	<odin:groupBox title="ҽ����Ա������Ϣ">	
		<table border="0" cellpadding="0" cellspacing="0" align="center" width="100%" >
		<odin:tabLayOut/>
			<tr>
				<odin:textIconEdit property="eac012" label="�籣����" size="12"></odin:textIconEdit>
				<odin:textEdit property="aac003" label="����" readonly="true" size="12"></odin:textEdit>
				<odin:textEdit property="AAC004" label="�Ա�" readonly="true" size="12"/>
				<odin:textEdit property="aab002" label="��λ����" readonly="true" size="30"></odin:textEdit>			
			</tr>
			<tr>
				<odin:textEdit property="yblb" label="�������" readonly="true" size="12"/>
				<odin:textEdit property="aac006" label="��������" readonly="true" size="12"/>
				<odin:textEdit property="cbrq" label="�α�����" readonly="true" size="12"/>
				<odin:textEdit property="aac002" label="���֤����" readonly="true" size="30"/>
			</tr>
			<tr>
			   <odin:textEdit property="ryxz" label="�������" readonly="true" size="12"/>
			   <odin:textEdit property="DNZHYE" label="�����ʻ����" readonly="true" size="12" ></odin:textEdit>
			   <odin:textEdit property="LNZHYE" label="�����ʻ����" readonly="true" size="12"></odin:textEdit>
			   <odin:textEdit property="ZHZYE" label="�ʻ������" readonly="true" size="12"></odin:textEdit>
			</tr>
		</table>
	</odin:groupBox>
</td></tr>
</table>