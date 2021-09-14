<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>

<odin:tab id="tab">
   <odin:tabModel>
       <odin:tabItem title="������Ϣ����" id="tab1"></odin:tabItem>
       <odin:tabItem title="�������븸�����" id="tab2" isLast="true"></odin:tabItem>
   </odin:tabModel>
   <odin:tabCont itemIndex="tab1" className="tab">
		<table id="basicTable">
			<tr>
				<td height="5"></td>
			</tr>
			<tr>
				<odin:textEdit property="name1" label="�û�������" required="true" />
				<odin:textEdit property="shortname1" label="���" />
			</tr>
			<tr>
				<odin:select property="chargedept1" label="���ܲ���" codeType="AAB301"/>
				<odin:textEdit property="districtcode1" label="��������" required="true"/>
			</tr>
			<tr>
				<odin:textEdit property="principal1" label="����������" />
				<odin:textEdit property="address1" label="��ַ" />
			</tr>
			<tr>
				<odin:textEdit property="tel1" label="�绰" />
				<odin:textEdit property="linkman1" label="��ϵ��" />
			</tr>
			<tr>
				<odin:textEdit property="license1" label="����" />
				<odin:textEdit property="org1" label="ϵͳ��������" />
			</tr>
			<tr>
				<odin:textEdit property="desc1" label="�û�������" width="246" colspan="4"/>
			</tr>
			<tr>
				<odin:textEdit property="otherinfo1" label="������Ϣ"/>
				<odin:select property="rate" label="����"></odin:select>
			</tr>
			<tr>
				<td width="45" height="50"></td>
				<td><odin:button text="�������" property="uptBtn"></odin:button></td>
			</tr>
		</table>
   </odin:tabCont>
   <odin:tabCont itemIndex="tab2">
	<table id="speTable">
		<tr>
			<td height="30"></td>
		</tr>
		<tr>
			<odin:textEdit property="ownerName" label="������" />
			<odin:textEdit property="parentName" label="����" />
		</tr>
		<tr>
			<td width="50" height="50"></td>
			<td><odin:button text="�������" property="uptSpeBtn"></odin:button></td>
		</tr>
      </table>
   </odin:tabCont>  
</odin:tab>



