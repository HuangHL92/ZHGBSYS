<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>

<ss:toolBar property="bar1">
	<odin:fill></odin:fill>
	<ss:doSaveBtn/>
	<ss:doQueryBtn />
	<ss:doExpBtn></ss:doExpBtn>
	<ss:doClickBtn text="点击" id="chaxun" handlerName="chaxun"></ss:doClickBtn>
	
</ss:toolBar>

<ss:editgrid property="div_2" pageSize="150" afteredit="true"  isFirstLoadData="false" url="/" title="普通表格" width="780" height="200">

<ss:gridColModel>
  <ss:gridCol header="selectall" width="30" name="logchecked" editor="checkbox"  p="E" />
  <ss:gridCol header="id" width="160" name="id" editor="text" p="D" />
  <ss:gridCol header="名称1" width="160" name="name" editor="text" p="E" enterAutoAddRow="true"/>
  <ss:gridCol header="描述" width="260" name="description" editor="text" p="E" />
  <ss:gridCol header="价格" name="money" width="260"  isLast="true" editor="text" p="E"/>
</ss:gridColModel>		
</ss:editgrid>
<script type="text/javascript">
var div_2_checkbox_1231dataindex1231 = "logchecke121d11";
</script >
