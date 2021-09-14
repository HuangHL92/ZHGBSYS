<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<odin:toolBar property="btnToolBar">
	<odin:fill/>
	<odin:buttonForToolBar isLast="true" text="授权"  id="save" />
</odin:toolBar>

		<odin:gridSelectColJs name="isCan" codeType="YESNO" ></odin:gridSelectColJs>
		<odin:editgrid property="rolegrid" bbarId="pageToolBar"
				isFirstLoadData="false" url="/" title="" topBarId="btnToolBar"
				width="515" height="344" title="" pageSize="10">
				<odin:gridJsonDataModel id="gridid" root="data"
					totalProperty="totalCount">
					<odin:gridDataCol name="rolecheck" />
					<odin:gridDataCol name="id"/>
					<odin:gridDataCol name="name" />
					<odin:gridDataCol name="status" />
					<odin:gridDataCol  name="isCan"/>
					<odin:gridDataCol name="desc" isLast="true" />
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridColumn header="selectall" gridName="rolegrid" align="center" width="7"
						editor="checkbox" edited="true" dataIndex="rolecheck" />
					<odin:gridEditColumn header="角色id" width="30" dataIndex="id"
						edited="false" editor="text" hidden="true" />
					<odin:gridEditColumn editor="select" edited="true" header="是否可分授权限"
					 	dataIndex="isCan" renderer="radow.commUserfulForGrid"
					 	codeType="YESNO" width="20" align="center" />
					<odin:gridEditColumn header="角色名" align="center" width="20" 
						dataIndex="name" editor="text" edited="false" />
					<odin:gridEditColumn header="资源描述" dataIndex="desc" align="center" 
						edited="false" editor="text" width="40" />
					<odin:gridEditColumn header="状态" align="center" editor="text"
						edited="false" renderer="radow.commUserfulForGrid" width="10"
						dataIndex="status" isLast="true" />
				</odin:gridColumnModel>
			</odin:editgrid>

