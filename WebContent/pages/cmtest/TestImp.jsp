<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<ss:toolBar property="reptoolbar">
	<odin:fill></odin:fill>
		<ss:buttonForToolBar text="����" cls="x-btn-text-icon" icon="images/add.gif"></ss:buttonForToolBar>
		<ss:doSaveBtn></ss:doSaveBtn>
		<ss:resetBtn></ss:resetBtn>
	<ss:doSaveBtn/>
</ss:toolBar>


<ss:hlistDiv id="1" cols="4" >
	<ss:textEdit property="aac003" label="����"></ss:textEdit>
	<ss:textEdit property="eaz252" label="ת����Ŀֵ"></ss:textEdit>
	<ss:dateEdit property="aac006" label="��������" format="Y-m-d H:i:s"></ss:dateEdit>
	<ss:select property="aac004" label="�Ա�"></ss:select>
</ss:hlistDiv>

<ss:hlistDiv id="2" cols="2" >

<odin:gridSelectColJs name="aae140"  codeType="AAE140" />
<odin:grid property="grid7" pageSize="150" bbarId="pageToolBar" isFirstLoadData="false" url="/" title="��ͨ���" width="780" height="200">
<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
		<odin:gridDataCol name="aae140" />
		<odin:gridDataCol name="aae180" />
		<odin:gridDataCol name="flag" />
		<odin:gridDataCol name="aaz289" />
		<odin:gridDataCol name="aac049" isLast="true" />
	</odin:gridJsonDataModel>
<odin:gridColumnModel>
  <odin:gridRowNumColumn />
	  <odin:gridColumn header="����" width="80" dataIndex="aae140" editor="select" edited="true" codeType="AAE140"/>
	  <odin:gridColumn header="�ɷѻ���" width="180" dataIndex="aae180" editor="number"/>
	  <odin:gridColumn  header="���" width="80" dataIndex="flag" editor="text" />
	  <odin:gridColumn  header="����" width="50" dataIndex="aaz289" editor="text" />
	  <odin:gridColumn  header="����" width="80" dataIndex="aac049" editor="date" isLast="true" />
</odin:gridColumnModel>		
</odin:grid>
</ss:hlistDiv>
<ss:hlistDiv id="3" cols="2" >
	<ss:textEdit property="aab033" label="����"></ss:textEdit>
</ss:hlistDiv>


