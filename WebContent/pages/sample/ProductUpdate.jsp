<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<odin:toolBar property="tb1">
	<odin:fill></odin:fill>
	<odin:buttonForToolBar text="����" id="saveBtn" isLast="true"></odin:buttonForToolBar>
</odin:toolBar>
<odin:panel contentEl="productDiv" property="productPanel" topBarId="tb1"></odin:panel>
<div id="productDiv">
<table border="0" id="myform" width="100%"  cellpadding="0" cellspacing="0">
    <tr>
    	<td colspan="6" height="6"><odin:hidden property="id"/></td>
    </tr>
    <tr>
    	<odin:textEdit property="name" label="��Ʒ����" required="true"></odin:textEdit>
    </tr>
    <tr>	
    	<odin:select property="ptype" label="��Ʒ���" codeType="PTYPE"></odin:select>
    </tr>
    <tr>
    	<odin:numberEdit property="price" label="��Ʒ�۸�" decimalPrecision="2"></odin:numberEdit>
    </tr>
    <tr>	
    	<odin:select property="status" label="״̬" codeType="USEFUL" value="1"></odin:select>
    </tr>
    <tr>	
    	<odin:dateEdit property="makedate" label="��������" format="Y-m-d"></odin:dateEdit>
    </tr>
    <tr>	
    	<odin:numberEdit property="effectmonths" label="��Ч����" maxlength="2"></odin:numberEdit>
    </tr>
    <tr>
    	<odin:textEdit property="description" label="����" colspan="4" width="'70%'"></odin:textEdit>
    </tr>
    <tr>
		<td colspan="6" height="6"></td>
	</tr>	 
 </table>
</div>