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
	<odin:gridSelectColJs2 name="isCan" selectData="['1','��'],['0','��']" />
	<odin:toolBar property="btnToolBar">
		<odin:fill/>
		<odin:buttonForToolBar  text="��ѯ���н�ɫ"  id="find" />
		<odin:buttonForToolBar  text="ȡ����Ȩ"  id="remove" />
		<odin:buttonForToolBar  text="��ѯδ�н�ɫ"  id="find1" />
		<odin:buttonForToolBar   isLast="true" text="��Ȩ"  id="save" />
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
			<odin:gridEditColumn header="��ɫid" width="30" dataIndex="roleid"
						edited="false" editor="text" hidden="true" />
<%-- 			<odin:gridEditColumn editor="select" edited="true" header="�Ƿ�ɷ���Ȩ��"
					 	dataIndex="isCan" renderer="radow.commUserfulForGrid"
					 	selectData="['1','��'],['0','��']" width="20" align="center" />  --%>
			<odin:gridEditColumn header="��ɫ����" align="center" width="20" 
						dataIndex="rolename" editor="text" edited="false" />
			<odin:gridEditColumn header="��ɫ����" dataIndex="roledesc" align="center" 
						edited="false" editor="text" width="40" />
			<odin:gridEditColumn header="״̬" align="center" editor="text"
						edited="false" renderer="radow.commUserfulForGrid" width="10"
						dataIndex="status" isLast="true" />
		</odin:gridColumnModel>
	</odin:editgrid>
</div>
			
<odin:toolBar property="btnToolBar1">
	<odin:fill/>
	<odin:buttonForToolBar  text="��ѯ���н�ɫ"  id="find2" />
	<odin:buttonForToolBar  text="ȡ����Ȩ"  id="remove1" />
	<odin:buttonForToolBar  text="��ѯδ�н�ɫ"  id="find3" />
	<odin:buttonForToolBar   isLast="true" text="��Ȩ"  id="save1" />
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
			<odin:gridEditColumn header="��ɫid" width="30" dataIndex="roleid"
						edited="false" editor="text" hidden="true" />
<%-- 			<odin:gridEditColumn editor="text" dataIndex="dispatchauth"
						edited="false" header="�Ƿ�ɷ���Ȩ��" align="center" renderer="radow.commUserfulForGrid"/> --%>
			<odin:gridEditColumn header="��ɫ����" align="center" width="20"
						dataIndex="rolename" editor="text" edited="false" />
			<odin:gridEditColumn header="��ɫ����" dataIndex="roledesc" align="center"
						edited="false" editor="text" width="40" />
			<odin:gridEditColumn header="״̬" align="center" editor="text"
						edited="false" renderer="radow.commUserfulForGrid" width="10"
						dataIndex="status" isLast="true" />
		</odin:gridColumnModel>
	</odin:editgrid>
</div>

