<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<ss:toolBar property="bar1">
	<odin:fill></odin:fill>
	<ss:doSaveBtn/>
	<ss:doQueryBtn/>
</ss:toolBar>
<ss:module param="P0">
<ss:hlistDiv id="1" cols="4">
	<ss:textEdit property="id" label="��ID" required="true" onchange="true"/>
	<ss:textEdit property="aaa001" label="aaa001" p="P0/P1:H,P2:D"/>
	<ss:textEdit property="aaa002" label="aaa002" />
	<ss:dateEdit property="aaa003" label="aaa003" format="Y-m-d H:i:s"/>
</ss:hlistDiv>
</ss:module>
<ss:module param="P1">
<ss:hlistDiv id="2" cols="6">
	<ss:textEdit property="id" label="����" required="true" onchange="true"/>
	<ss:textEdit property="aaa001" label="����1"  p="P0/P1:E,P2:D"/>
	<ss:numberEdit property="aaa002" label="����2"  p="P0/P1:H,P2:D"/>
	<ss:dateEdit property="aaa003" label="����3" format="Y-m-d H:i:s" p="P0/P1:D,P2:D"/>
	<ss:select property="aaa004" label="����4" codeType="WEEKDAY" p="P0/P1:R,P2:D" onchange="true"></ss:select>
</ss:hlistDiv>
</ss:module>