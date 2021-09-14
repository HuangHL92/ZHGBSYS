<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<ss:toolBar property="bar1">
	<odin:fill></odin:fill>
	<ss:doSaveBtn/>
	<ss:doQueryBtn/>
</ss:toolBar>
<ss:hlistDiv id="1" cols="4">
	<ss:dateEdit property="date1" label="��������1" p="R"/>
	<ss:dateEdit property="date2" label="��������2" p="E"/>
	<ss:select property="select1" label="��������1" p="R" codeType="EAZ216" onchange="true"></ss:select>
	<ss:select property="select2" label="��������2" p="E" codeType="AAB301" value="330200"></ss:select>
	<ss:numberEdit property="number1" label="���ֲ���1" p="R"/>
	<ss:numberEdit property="number2" label="���ֲ���2" p="E"/>
	<ss:textEdit property="text1" label="�ı�����1" p="R"/>
	<ss:textEdit property="text2" label="�ı�����2" p="E"/>
	<ss:textarea property="area1" label="�ı������1" p="R" rows="4" colspan="4" />
	<ss:textarea property="area2" label="�ı������2" p="E" rows="4" colspan="4"/>
</ss:hlistDiv>