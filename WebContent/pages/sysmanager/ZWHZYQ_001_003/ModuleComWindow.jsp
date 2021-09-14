<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<script type="text/javascript">
function checkType(){
	var save = document.getElementById('save1');
	save.disabled=true;
	var remove = document.getElementById('remove1');
	remove.disabled=false;
	var div = document.getElementById('123');
	div.style.display='block';
	/* var rolecheck =  document.getElementById('rolecheck');
	rolecheck.width='7'; */
	var div1 = document.getElementById('345');
	div1.style.display='none';
}
function checkType2(){
	var remove = document.getElementById('remove');
	remove.disabled=true;
	var save = document.getElementById('save');
	save.disabled=false;
	var div = document.getElementById('345');
	div.style.display='block';
	var div1 = document.getElementById('123');
	div1.style.display='none';
	
	var find = document.getElementById('find');
	find.disabled=false;
	var find1 = document.getElementById('find1');
	find1.disabled=true;
}
function checkType3(){
	var div = document.getElementById('123');
	div.style.display='none';
	var remove = document.getElementById('remove');
	remove.disabled=true;
	var save = document.getElementById('save');
	save.disabled=true;
	var find = document.getElementById('find2');
	find.disabled=true;
	var find1 = document.getElementById('find3');
	find1.disabled=false;
}
</script>

<div id="345">
	<odin:gridSelectColJs2 name="isCan" selectData="['1','是'],['0','否']" />
	<odin:toolBar property="btnToolBar">
		<odin:fill/>
		<odin:buttonForToolBar  text="查询已有角色"  id="find" />
		<odin:buttonForToolBar  text="取消授权"  id="remove" />
		<odin:buttonForToolBar  text="查询未有角色"  id="find1" />
		<odin:buttonForToolBar   isLast="true" text="授权"  id="save" />
	</odin:toolBar>
	<odin:editgrid property="rolegrid" bbarId="pageToolBar" isFirstLoadData="false" url="/" title="" topBarId="btnToolBar" width="515" height="344" title="" pageSize="10">
		<odin:gridJsonDataModel id="gridid" root="data" totalProperty="totalCount">
			<odin:gridDataCol name="rolecheck" />
			<odin:gridDataCol name="roleid"/>
			<odin:gridDataCol name="rolename" />
			<odin:gridDataCol name="status" />
			<odin:gridDataCol  name="isCan"/>
			<odin:gridDataCol name="roledesc" isLast="true" />
		</odin:gridJsonDataModel>
		<odin:gridColumnModel>
			<odin:gridRowNumColumn></odin:gridRowNumColumn>
			<odin:gridColumn header="selectall" gridName="rolegrid" align="center" width="7"
						editor="checkbox" edited="true" dataIndex="rolecheck" />
			<odin:gridEditColumn header="角色id" width="30" dataIndex="roleid"
						edited="false" editor="text" hidden="true" />
<%-- 			<odin:gridEditColumn editor="select" edited="true" header="是否可分授权限"
					 	dataIndex="isCan" renderer="radow.commUserfulForGrid"
					 	selectData="['1','是'],['0','否']" width="20" align="center" />  --%>
			<odin:gridEditColumn header="角色名称" align="center" width="20" 
						dataIndex="rolename" editor="text" edited="false" />
			<odin:gridEditColumn header="角色描述" dataIndex="roledesc" align="center" 
						edited="false" editor="text" width="40" />
			<odin:gridEditColumn header="状态" align="center" editor="text"
						edited="false" renderer="radow.commUserfulForGrid" width="10"
						dataIndex="status" isLast="true" />
		</odin:gridColumnModel>
	</odin:editgrid>
</div>
			
<odin:toolBar property="btnToolBar1">
	<odin:fill/>
	<odin:buttonForToolBar  text="查询已有角色"  id="find2" />
	<odin:buttonForToolBar  text="取消授权"  id="remove1" />
	<odin:buttonForToolBar  text="查询未有角色"  id="find3" />
	<odin:buttonForToolBar   isLast="true" text="授权"  id="save1" />
</odin:toolBar>
<div id="123" >
	<odin:editgrid property="rolegrid1" bbarId="pageToolBar1"
				isFirstLoadData="false" url="/" title="" topBarId="btnToolBar1"
				width="525" height="340" title="" pageSize="10">
		<odin:gridJsonDataModel id="gridid1" root="data"
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
			<odin:gridEditColumn header="角色id" width="30" dataIndex="roleid"
						edited="false" editor="text" hidden="true" />
<%-- 			<odin:gridEditColumn editor="text" dataIndex="dispatchauth"
						edited="false" header="是否可分受权限" align="center" renderer="radow.commUserfulForGrid"/> --%>
			<odin:gridEditColumn header="角色名称" align="center" width="20"
						dataIndex="rolename" editor="text" edited="false" />
			<odin:gridEditColumn header="角色描述" dataIndex="roledesc" align="center"
						edited="false" editor="text" width="40" />
			<odin:gridEditColumn header="状态" align="center" editor="text"
						edited="false" renderer="radow.commUserfulForGrid" width="10"
						dataIndex="status" isLast="true" />
		</odin:gridColumnModel>
	</odin:editgrid>
</div>

