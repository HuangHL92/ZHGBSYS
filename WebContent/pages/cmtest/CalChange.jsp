<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<ss:toolBar property="bar1">
	<ss:fill></ss:fill>
	<ss:doClickBtn text="增加" handlerName="zj"></ss:doClickBtn>
	<ss:buttonForToolBar text="日历同步监控" cls="x-btn-text-icon" icon="images/add.gif"></ss:buttonForToolBar>
	<ss:doSaveBtn />
	<ss:resetBtn/>
</ss:toolBar>
<ss:hlistDiv id="1" cols="6">
<ss:textEdit property="yearno_a" label="年度" onchange="true"></ss:textEdit>
</ss:hlistDiv>

<odin:editgrid property="div_2"  pageSize="150" isFirstLoadData="false" url="/" title=""  height="200">
<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
		<odin:gridDataCol name="yearno" />
		<odin:gridDataCol name="monthno" />
		<odin:gridDataCol name="casdate" />
		<odin:gridDataCol name="stsdate" />
		<odin:gridDataCol name="caedate" isLast="true" />
	</odin:gridJsonDataModel>
<odin:gridColumnModel>
  <odin:gridRowNumColumn />
		<odin:gridColumn header="年度" width="80" dataIndex="yearno"
			editor="text" edited="true" />
		<odin:gridColumn header="月份" width="80" dataIndex="monthno"
			editor="text" />
		<odin:gridColumn header="记账月起始日期" width="120" dataIndex="casdate"
			editor="date" />
		<odin:gridColumn header="结算开始日期" width="120" dataIndex="stsdate"
			editor="date" />
		<odin:gridColumn header="结算完成日期" width="120" dataIndex="caedate"
			editor="date"  isLast="true" />
	</odin:gridColumnModel>		
</odin:editgrid>