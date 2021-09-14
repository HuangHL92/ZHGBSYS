<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<script>
function impFile(){
	odin.showWindowWithSrc("impFileWin",contextPath+"/pages/modeldb/impFileWindow.jsp?businessClass=com.insigma.siis.local.business.modeldb.SaveFromFile"); 
}

<odin:menu property="managerMenu">
<odin:menuItem text="�ֿⴴ��" property="create" ></odin:menuItem>
<odin:menuItem text="�ֿ�ɾ��" property="del" ></odin:menuItem>
<odin:menuItem text="�ֿ�����" property="run" ></odin:menuItem>
<odin:menuItem text="�ֿ���Ȩ" property="grant" isLast="true"></odin:menuItem> 
</odin:menu>
<odin:menu property="defineMenu">
  <odin:menuItem text="���嵼��(Xml)" property="expXml" ></odin:menuItem>
  <odin:menuItem text="���嵼��(Json)" property="expJson"></odin:menuItem> 
  <odin:menuItem text="���嵼��" property="imp" handler="impFile" isLast="true"></odin:menuItem> 
</odin:menu>
<odin:menu property="synMenu">
  <odin:menuItem text="ͬ������" property="start"></odin:menuItem>
  <odin:menuItem text="ͬ����ͣ" property="stop" isLast="true"></odin:menuItem> 
</odin:menu>
</script>
<odin:toolBar property="btnToolBar" >
    <odin:textForToolBar text="<h1>&nbsp;&nbsp;����ֿ��б�<h1>"/>
	<odin:fill/>
	<odin:buttonForToolBar text="�ֿ����" id="manager" menu ="managerMenu" tooltip="Tools"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="�������" id="define" menu="defineMenu"  tooltip="Files"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="����ͬ��" id="sync" menu="synMenu" tooltip="Data"/>
	<odin:separator></odin:separator>
	<odin:textForToolBar text="&nbsp;&nbsp;&nbsp;&nbsp;" isLast="true"/>
</odin:toolBar>
<odin:groupBox property="query_gb" title="��ѯ����" width="800">
  <table>
    <tr>
      <td colspan="6">
        <table cellpadding="0" cellspacing="0">
           <tr>
			<odin:textEdit property="sublibrariesmodelname" label="��������" colspan="4" size="60"/> 
	      </tr>
		   <tr>
			<odin:select property="sublibrariesmodeltype" data="['1','�߼��ֿ�'],['2','����ֿ�']" label="�ֿ�����"  size="20"/>
			<tags:BasicQuery property="gsearch" label="��������" url="/radowAction.do?method=doEvent&pageModel=pages.modeldb.ModelDB&eventNames=orgTreeJsonData" selectEvent="" />
		   </tr>
          <tr>
            <odin:dateEdit property="createtimesta" format="Ymd" label="����ʱ���" size="20" /> 
			<odin:dateEdit property="createtimeend" format="Ymd" label="��&nbsp;&nbsp;"  size="20"/>
          </tr>
        </table>
      </td>
      <td colspan="1" width="30">
        <hr style="width: 1px; height: 60px;"></hr>
      </td>
      <td colspan="1">
        <table cellpadding="0" cellspacing="0">
          <tr>
            <td colspan="1" align="right"><odin:button property="query" text="��ѯ"/></td>
			<td colspan="1" align="left"><odin:button property="clear" text="���"/></td>
          </tr>
        </table>
      </td>
    </tr>
  </table>
</odin:groupBox>
<odin:editgrid property="MGrid" title="" autoFill="false" topBarId="btnToolBar" width="800"
	bbarId="pageToolBar" pageSize="20" isFirstLoadData="false" url="/">
	<odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
		<odin:gridDataCol name="check"/>
	    <odin:gridDataCol name="sub_libraries_model_id"/>
		<odin:gridDataCol name="sub_libraries_model_name" />
		<odin:gridDataCol name="create_time" />
		<odin:gridDataCol name="sub_libraries_model_type" />
		<odin:gridDataCol name="create_group_name" />
		<odin:gridDataCol name="self_create_mark" />
		<odin:gridDataCol name="run_state" />
		<odin:gridDataCol name="sub_libraries_model_key"  isLast="true" />
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn></odin:gridRowNumColumn>
		<odin:gridColumn header="selectall" dataIndex="check"  gridName="MGrid" align="center" width="60" editor="checkbox" edited="true" /> 
		<odin:gridEditColumn header="�����ID" dataIndex="sub_libraries_model_id" hidden="true" edited="false" editor="text"/>
		<odin:gridEditColumn header="�ֿ�ģ������"  dataIndex="sub_libraries_model_name"  editor="text" edited="false" align="left" width="200"/>
		<odin:gridEditColumn header="�ֿ�����" dataIndex="sub_libraries_model_type" align="center" selectData="['1','�߼��ֿ�'],['2','����ֿ�']" edited="false" editor="select" width="80" />
		<odin:gridEditColumn header="����ʱ��"  dataIndex="create_time" editor="text" edited="false" align="center" width="150"/>
		<odin:gridEditColumn header="��������" dataIndex="create_group_name" align="left" edited="false" editor="text" width="120" />
		<odin:gridEditColumn header="��������" dataIndex="self_create_mark"  selectData="['0','�ϼ�����'],['1','�Խ�']" align="center" edited="false" editor="select" width="80" />
		<odin:gridEditColumn header="����״̬" dataIndex="run_state" align="center" edited="false" editor="select" selectData="['0','δ����'],['1','������']" width="80" />
		<odin:gridEditColumn header="�������" dataIndex="sub_libraries_model_key" align="left" edited="false" editor="text" hidden="true"/>
		<odin:gridColumn dataIndex="sub_libraries_model_id" header="�޸�" width="70" align="center" renderer="openEditer"/>
		<odin:gridColumn dataIndex="sub_libraries_model_id" header="����" width="70" align="center" renderer="setter" isLast="true"/>
	</odin:gridColumnModel>
</odin:editgrid>


<odin:window src="/blank.htm" id="createWin" width="500" height="250" modal="true"
	maximizable="false" title="���ⴴ��" />
<odin:window src="/blank.htm" id="defineWin" width="900" height="500" modal="true"
	maximizable="false" title="���ⶨ��" />
<odin:window src="/pages/modeldb/downloadfile.jsp" title="�����ļ�" id="downFileWin" height="160"  modal="true"></odin:window>
<odin:window src="/pages/modeldb/impFileWindow.jsp" title="����" id="impFileWin" height="160"  modal="true"></odin:window>
<odin:window src="/pages/modeldb/LoadGrant.jsp" id="grantWin" width="885" height="485" title="��Ȩ����"  modal="true"></odin:window>
<script>
	function openEditer(value, params, record,rowIndex,colIndex,ds){
		if(value){
			return "<img src='"+contextPath+"/images/update.gif' title='�޸�������Ϣ' style='cursor:pointer' onclick=\"radow.doEvent('dogridgrant','"+value+"');\">";
		}else{
			return null;
		}
	}
	function setter(value, params, record,rowIndex,colIndex,ds){
		var title=record.data.sub_libraries_model_name+'����ⶨ��';
		if(value){
			return "<img src='"+contextPath+"/image/u53.png' title='��������ֿ�'  style='cursor:pointer' onclick=\"openConfigWin('"+value+"','"+title+"');\">";
		}else{
			return null;
		}
	}
	function openConfigWin(value,title){
		var aid="config";  	
	  /*  odin.loadPageInTab(aid,"/radowAction.do?method=doEvent&pageModel=pages.modeldb.ModelDefine&initParams="+value,false,title,false); */
	   	 parent.addTab(title,aid+value,'<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.modeldb.ModelDefine&initParams='+value,false,false)
	}
	function openExp(filename){
		location.href=contextPath+"/pages/modeldb/downloadfile.jsp?filename="+filename;
	}
	
	function reload(){
		odin.reset();
	}
	
</script>