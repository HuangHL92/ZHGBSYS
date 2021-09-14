<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>


<odin:editgrid property="groupgrid" bbarId="pageToolBar" isFirstLoadData="false" url="/"
				pageSize="12" title="用户组列表" width="650" height="355">
<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
 <odin:gridDataCol name="id" />
  <odin:gridDataCol name="name" />
  <odin:gridDataCol name="ownerId" />
  <odin:gridDataCol name="parentid" />
  <odin:gridDataCol name="desc"/>
  
  <odin:gridDataCol name="org"/>
  <odin:gridDataCol name="type"/>
  <odin:gridDataCol name="principal"/>
  <odin:gridDataCol name="linkman"/>
  <odin:gridDataCol name="address"/>
  <odin:gridDataCol name="tel"/>
  <odin:gridDataCol name="districtcode"/>
  <odin:gridDataCol name="chargedept"/>
  <odin:gridDataCol name="createdate"/>
  <odin:gridDataCol name="otherinfo"/>
  <odin:gridDataCol name="status"/>
  <odin:gridDataCol name="shortname" isLast="true"/>
</odin:gridJsonDataModel>
<odin:gridColumnModel>
  <odin:gridRowNumColumn />
  <odin:gridColumn  header="用户组ID" hidden="true" dataIndex="id"/>
  <odin:gridColumn  header="用户组名" dataIndex="name"/>
  <odin:gridColumn  header="父类组" dataIndex="parentid"/>
  <odin:gridColumn header="描述" dataIndex="desc"/>
  <odin:gridColumn header="简称" dataIndex="shortname"/>
  <odin:gridColumn  header="查看详细信息" renderer="radow.commGrantForGrid" align="center" dataIndex="id" isLast="true"/>
</odin:gridColumnModel>		
</odin:editgrid>	
	 
<odin:window src="/blank.htm" id="uptWin" width="500" height="320" title="用户组修改窗口"></odin:window>