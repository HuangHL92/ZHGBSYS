<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<odin:toolBar property="btnToolBar">
	<odin:fill/>
	<odin:buttonForToolBar isLast="true" text="ȡ����Ȩ"  id="remove" />
</odin:toolBar>
			<odin:editgrid property="rolegrid" bbarId="pageToolBar"
				isFirstLoadData="false" url="/" title="" topBarId="btnToolBar"
				width="525" height="340" title="" pageSize="10">
				<odin:gridJsonDataModel id="gridid" root="data"
					totalProperty="totalCount">
					<odin:gridDataCol name="rolecheck" />
					<odin:gridDataCol name="roleid"/>
					<odin:gridDataCol name="dispatchauth"/>
					<odin:gridDataCol name="rolename" />
					<odin:gridDataCol name="status" />
					<odin:gridDataCol name="roledesc" isLast="true" />
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridColumn header="selectall" gridName="rolegrid" align="center" width="7"
						editor="checkbox" edited="true" dataIndex="rolecheck" />
					<odin:gridEditColumn header="��ɫid" width="30" dataIndex="roleid"
						edited="false" editor="text" hidden="true" />
					<odin:gridEditColumn editor="text" dataIndex="dispatchauth"
						edited="false" header="�Ƿ�ɷ���Ȩ��" align="center" renderer="radow.commUserfulForGrid"/>
					<odin:gridEditColumn header="��ɫ��" align="center" width="20"
						dataIndex="rolename" editor="text" edited="false" />
					<odin:gridEditColumn header="��Դ����" dataIndex="roledesc" align="center"
						edited="false" editor="text" width="40" />
					<odin:gridEditColumn header="״̬" align="center" editor="text"
						edited="true" renderer="radow.commUserfulForGrid" width="10"
						dataIndex="status" isLast="true" />
				</odin:gridColumnModel>
			</odin:editgrid>
