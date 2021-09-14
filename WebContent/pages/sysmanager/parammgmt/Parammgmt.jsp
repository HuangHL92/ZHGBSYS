<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>

<odin:editgrid property="codegrid"
	bbarId="pageToolBar" isFirstLoadData="false" url="/"  
	title="参数列表" width="788" height="470" pageSize="15">
	<odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
		<odin:gridDataCol name="aaa001" />
		<odin:gridDataCol name="aaa002" />
		<odin:gridDataCol name="aaa105" />
		<odin:gridDataCol name="aaa005" isLast="true" />
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn></odin:gridRowNumColumn>
		<odin:gridEditColumn header="参数类别代码" align="left" edited="false"
			width="200" dataIndex="aaa001"   editor="text" />
		<odin:gridEditColumn header="参数类别名称"   align="left" edited="false"
			width="250" dataIndex="aaa002"   editor="text" />
		<odin:gridEditColumn header="参数值域说明" align="left" edited="false"
			width="270" dataIndex="aaa105"   editor="text" />
		<odin:gridEditColumn header="参数值"   align="left" edited="false"
			width="200" dataIndex="aaa005"   editor="text"  />
		<odin:gridColumn header="修改" align="center" renderer="radow.commEditForGrid" 
		dataIndex="aaa001" width="100" isLast="true"/>
	</odin:gridColumnModel>
</odin:editgrid>

<odin:window src="/blank.htm" id="UpdateWin" width="320" height="215" title="参数修改窗口">
</odin:window>