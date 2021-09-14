<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>

	<table>
		<odin:editgrid property="rolegrid" isFirstLoadData="false" url="/" title="角色信息" width="525" height="340">
				<odin:gridJsonDataModel id="id" root="data"
					totalProperty="totalCount">
					<odin:gridDataCol name="id"/>
					<odin:gridDataCol name="name" />
					<odin:gridDataCol name="status" />
					<odin:gridDataCol name="desc" isLast="true" />
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridEditColumn header="角色id" width="30" dataIndex="id"
						edited="false" editor="text" hidden="true" />
					<odin:gridEditColumn header="角色名" align="center" width="20" 
						dataIndex="name" editor="text" edited="false" />
					<odin:gridEditColumn header="资源描述" dataIndex="desc" align="center" 
						edited="false" editor="text" width="40" />
					<odin:gridEditColumn header="状态" align="center" editor="text"
						edited="false" renderer="radow.commUserfulForGrid" width="10"
						dataIndex="status" isLast="true" />
				</odin:gridColumnModel>
			</odin:editgrid>
	</table>
