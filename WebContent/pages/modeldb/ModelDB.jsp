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
<odin:menuItem text="分库创建" property="create" ></odin:menuItem>
<odin:menuItem text="分库删除" property="del" ></odin:menuItem>
<odin:menuItem text="分库启用" property="run" ></odin:menuItem>
<odin:menuItem text="分库授权" property="grant" isLast="true"></odin:menuItem> 
</odin:menu>
<odin:menu property="defineMenu">
  <odin:menuItem text="定义导出(Xml)" property="expXml" ></odin:menuItem>
  <odin:menuItem text="定义导出(Json)" property="expJson"></odin:menuItem> 
  <odin:menuItem text="定义导入" property="imp" handler="impFile" isLast="true"></odin:menuItem> 
</odin:menu>
<odin:menu property="synMenu">
  <odin:menuItem text="同步启动" property="start"></odin:menuItem>
  <odin:menuItem text="同步暂停" property="stop" isLast="true"></odin:menuItem> 
</odin:menu>
</script>
<odin:toolBar property="btnToolBar" >
    <odin:textForToolBar text="<h1>&nbsp;&nbsp;主题分库列表<h1>"/>
	<odin:fill/>
	<odin:buttonForToolBar text="分库管理" id="manager" menu ="managerMenu" tooltip="Tools"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="定义管理" id="define" menu="defineMenu"  tooltip="Files"/>
	<odin:separator></odin:separator>
	<odin:buttonForToolBar text="数据同步" id="sync" menu="synMenu" tooltip="Data"/>
	<odin:separator></odin:separator>
	<odin:textForToolBar text="&nbsp;&nbsp;&nbsp;&nbsp;" isLast="true"/>
</odin:toolBar>
<odin:groupBox property="query_gb" title="查询条件" width="800">
  <table>
    <tr>
      <td colspan="6">
        <table cellpadding="0" cellspacing="0">
           <tr>
			<odin:textEdit property="sublibrariesmodelname" label="主题名称" colspan="4" size="60"/> 
	      </tr>
		   <tr>
			<odin:select property="sublibrariesmodeltype" data="['1','逻辑分库'],['2','物理分库']" label="分库类型"  size="20"/>
			<tags:BasicQuery property="gsearch" label="创建机构" url="/radowAction.do?method=doEvent&pageModel=pages.modeldb.ModelDB&eventNames=orgTreeJsonData" selectEvent="" />
		   </tr>
          <tr>
            <odin:dateEdit property="createtimesta" format="Ymd" label="创建时间从" size="20" /> 
			<odin:dateEdit property="createtimeend" format="Ymd" label="至&nbsp;&nbsp;"  size="20"/>
          </tr>
        </table>
      </td>
      <td colspan="1" width="30">
        <hr style="width: 1px; height: 60px;"></hr>
      </td>
      <td colspan="1">
        <table cellpadding="0" cellspacing="0">
          <tr>
            <td colspan="1" align="right"><odin:button property="query" text="查询"/></td>
			<td colspan="1" align="left"><odin:button property="clear" text="清除"/></td>
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
		<odin:gridEditColumn header="主题库ID" dataIndex="sub_libraries_model_id" hidden="true" edited="false" editor="text"/>
		<odin:gridEditColumn header="分库模型名称"  dataIndex="sub_libraries_model_name"  editor="text" edited="false" align="left" width="200"/>
		<odin:gridEditColumn header="分库类型" dataIndex="sub_libraries_model_type" align="center" selectData="['1','逻辑分库'],['2','物理分库']" edited="false" editor="select" width="80" />
		<odin:gridEditColumn header="创建时间"  dataIndex="create_time" editor="text" edited="false" align="center" width="150"/>
		<odin:gridEditColumn header="创建机构" dataIndex="create_group_name" align="left" edited="false" editor="text" width="120" />
		<odin:gridEditColumn header="创建类型" dataIndex="self_create_mark"  selectData="['0','上级创建'],['1','自建']" align="center" edited="false" editor="select" width="80" />
		<odin:gridEditColumn header="运行状态" dataIndex="run_state" align="center" edited="false" editor="select" selectData="['0','未启用'],['1','已启用']" width="80" />
		<odin:gridEditColumn header="主题编码" dataIndex="sub_libraries_model_key" align="left" edited="false" editor="text" hidden="true"/>
		<odin:gridColumn dataIndex="sub_libraries_model_id" header="修改" width="70" align="center" renderer="openEditer"/>
		<odin:gridColumn dataIndex="sub_libraries_model_id" header="定义" width="70" align="center" renderer="setter" isLast="true"/>
	</odin:gridColumnModel>
</odin:editgrid>


<odin:window src="/blank.htm" id="createWin" width="500" height="250" modal="true"
	maximizable="false" title="主题创建" />
<odin:window src="/blank.htm" id="defineWin" width="900" height="500" modal="true"
	maximizable="false" title="主题定义" />
<odin:window src="/pages/modeldb/downloadfile.jsp" title="下载文件" id="downFileWin" height="160"  modal="true"></odin:window>
<odin:window src="/pages/modeldb/impFileWindow.jsp" title="导入" id="impFileWin" height="160"  modal="true"></odin:window>
<odin:window src="/pages/modeldb/LoadGrant.jsp" id="grantWin" width="885" height="485" title="授权窗口"  modal="true"></odin:window>
<script>
	function openEditer(value, params, record,rowIndex,colIndex,ds){
		if(value){
			return "<img src='"+contextPath+"/images/update.gif' title='修改主题信息' style='cursor:pointer' onclick=\"radow.doEvent('dogridgrant','"+value+"');\">";
		}else{
			return null;
		}
	}
	function setter(value, params, record,rowIndex,colIndex,ds){
		var title=record.data.sub_libraries_model_name+'主题库定义';
		if(value){
			return "<img src='"+contextPath+"/image/u53.png' title='定义主题分库'  style='cursor:pointer' onclick=\"openConfigWin('"+value+"','"+title+"');\">";
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