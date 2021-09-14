<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<odin:toolBar property="btnToolBar" >
	<odin:textForToolBar text="<h3>��ɫ��ѯ</h3>"/>
	<odin:fill/>
	<odin:buttonForToolBar id="clean"  text="����"  icon="images/sx.gif" cls="x-btn-text-icon"/>
	<odin:buttonForToolBar id="btn_query" isLast="true" text="��ѯ"  icon="images/search.gif" cls="x-btn-text-icon"/>
</odin:toolBar>

<div id="roleQueryContent">
<div id="roleQueryPanel"></div>
	<table width="100%">
		<tr>
			<td height="16"  colspan="4"></td>
		</tr>
		<tr>
	    	<odin:textEdit property="roleQName" label="��ɫ����"></odin:textEdit>
	    	<odin:textEdit property="roleQDesc" label="��ɫ����"></odin:textEdit>
		</tr>
		<tr>
			<td height="16" colspan="4"></td>
		</tr>
	</table>		
</div>

<odin:gridSelectColJs2 name="status" selectData="['1','��Ч'],['0','��Ч']"></odin:gridSelectColJs2>

<odin:editgrid property="grid6" hasRightMenu="false" topBarId="btnToolBar" pageSize="20" bbarId="pageToolBar" url="/"  width="778" height="250">
<odin:gridJsonDataModel  id="roleid" root="data" totalProperty="totalCount">
	<odin:gridDataCol name="checked" />
  <odin:gridDataCol name="roleid" />
  <odin:gridDataCol name="roledesc" />
  <odin:gridDataCol name="owner" />
  <odin:gridDataCol name="ownername" />
  <odin:gridDataCol name="hostsys" />
  <odin:gridDataCol name="rolecode" />
  <odin:gridDataCol name="status" />
  <odin:gridDataCol name="rolename" isLast="true"/>
</odin:gridJsonDataModel>
<odin:gridColumnModel>
  <odin:gridRowNumColumn ></odin:gridRowNumColumn>
 <odin:gridColumn  header="selectall"  gridName="grid6" dataIndex="checked" editor="checkbox" edited="true" />
  <odin:gridEditColumn header="��ɫid" hidden="true" dataIndex="roleid" editor="text" edited="false"/>
  <odin:gridEditColumn header="��ɫ����" align="center" width="100" dataIndex="rolename" selectOnFocus="true" editor="text" edited="false"  />
  <odin:gridEditColumn header="��ɫ����" width="130" align="center" dataIndex="roledesc" editor="text" selectOnFocus="true" edited="false" />
  <odin:gridEditColumn header="��ɫ������"  width="80" dataIndex="ownername" edited="false" editor="text" align="center" hidden="true"/>
  <odin:gridEditColumn header="��ɫ����"  width="80" dataIndex="rolecode" edited="false" editor="text" align="center" hidden="true"/>
  <odin:gridEditColumn2 header="��ɫ����ϵͳ" dataIndex="hostsys" align="center" hidden="true" edited="false" editor="select" width="80" codeType="ROLESYS"/>
  <odin:gridEditColumn2 header="״̬" sortable="false" align="center" width="80" dataIndex="status" editor="select" edited="false" renderer="radow.commUserfulForGrid" selectData="['1','��Ч'],['0','��Ч']" isLast="true"/>
<%--   <odin:gridEditColumn sortable="false" align="center" width="60" header="��Ȩ" dataIndex="op1"  editor="text" edited="false" renderer="commGrantForGrid" isLast="true" />
 --%>  <%-- <odin:gridEditColumn  align="center" sortable="false" width="100" header="ɾ��" dataIndex="op2" editor="text" edited="false" renderer="commGridColDelete" isLast="true" /> --%>
 
</odin:gridColumnModel>			
</odin:editgrid>	


<odin:window src="/" modal="true" id="roleWindow" width="500" height="240"></odin:window>
<odin:window src="/" modal="true" id="grantWindow" width="320" height="520"></odin:window>
<script type="text/javascript">
   
    function commGrantForGrid(value, params, record, rowIndex, colIndex, ds){
    	var hsys=record.get('hostsys');
    	if(hsys=='2'){
    		return '';
    	}
		return "<img src='"+contextPath+"/images/icon_photodesk.gif'  onclick=\"radow.doEvent('dogridgrant','"+record.get('roleid')+"');\">";
	}
	
	function commGridColDelete(value, params, record, rowIndex, colIndex, ds){
		return "<img src='"+contextPath+"/images/qinkong.gif' title='ɾ����' onclick=\"radow.doEvent('dogriddelete','"+record.get('roleid')+"');\">";
	}
	function boxClick(a,b,c,d,e){
		console.log(a);
		console.log(b);
		console.log(c);
		console.log(d);
		console.log(odin.ext.getCmp('grid6').getSelectionModel().getSelections().length);
	}


</script>
