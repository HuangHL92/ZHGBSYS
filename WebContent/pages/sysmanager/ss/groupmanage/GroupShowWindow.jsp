<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>


<odin:editgrid property="groupgrid" bbarId="pageToolBar" isFirstLoadData="false" url="/"
				pageSize="12" title="�û����б�" width="650" height="355">
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
  <odin:gridColumn  header="�û���ID" hidden="true" dataIndex="id"/>
  <odin:gridColumn  header="�û�����" dataIndex="name"/>
  <odin:gridColumn  header="������" dataIndex="parentid"/>
  <odin:gridColumn header="����" dataIndex="desc"/>
  <odin:gridColumn header="���" dataIndex="shortname"/>
  <odin:gridColumn  header="�鿴��ϸ��Ϣ" renderer="radow.commGrantForGrid" align="center" dataIndex="id" isLast="true"/>
</odin:gridColumnModel>		
</odin:editgrid>	
	 
<odin:window src="/blank.htm" id="uptWin" width="500" height="320" title="�û����޸Ĵ���"></odin:window>