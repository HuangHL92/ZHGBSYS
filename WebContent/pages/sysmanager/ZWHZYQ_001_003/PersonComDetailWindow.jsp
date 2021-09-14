<%@page import="com.insigma.odin.framework.sys.manager.sysmanager.comm.entity.Sysopright"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@page import="com.insigma.siis.local.pagemodel.sysmanager.group.GroupManagePageModel"%>
<script  type="text/javascript" src="basejs/jquery.js"></script>
<script  type="text/javascript" src="basejs/jquery.min.js"></script>

<div id="groupTreeContent" style="height: 100%">
<table width="100%">
  <tr>
    <td>
      <odin:groupBox title="搜索框" property="ggBox">
      <table width="100%">
        <tr>
          <odin:textEdit property="searchA0184"  label="公务员身份证"/>
          <odin:textEdit property="searchA0101"  label="公务员姓名"/>
        </tr>
        <tr>
          <td height="5" colspan="4"></td>
        </tr>
      </table>
      </odin:groupBox>
    <odin:editgrid property="personGrid" title="公务员列表" autoFill="false" width="500" height="300" bbarId="pageToolBar" pageSize="20" isFirstLoadData="false" url="/">
      <odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
          <odin:gridDataCol name="logchecked"/>
          <odin:gridDataCol name="a0184" />
          <odin:gridDataCol name="a0101" isLast="true"/>
        </odin:gridJsonDataModel>
        <odin:gridColumnModel>
          <odin:gridColumn header="" width="25" editor="checkbox" dataIndex="logchecked" edited="true"/>
          <odin:gridRowNumColumn></odin:gridRowNumColumn>
          <odin:gridColumn dataIndex="a0184" width="110" header="公务员身份证" align="center"/>
          <odin:gridColumn dataIndex="a0101" width="300" header="公务员姓名" align="center" isLast="true"/>    
        </odin:gridColumnModel>
      </odin:editgrid>
    </td>
  </tr>
</table>
</div>

<odin:toolBar property="btnToolBar">
  <odin:textForToolBar text="<h3>公务员是否可见</h3>" />
  <odin:fill />
  <odin:separator></odin:separator>
  <odin:buttonForToolBar text="查询可见人员" id="find" tooltip="查询可见人员"/>
  <odin:separator></odin:separator>
  <odin:buttonForToolBar text="查询不可见人员" id="find2" tooltip="查询不可见人员"/>
  <odin:separator></odin:separator>
  <odin:buttonForToolBar text="设置为可见" id="visible" tooltip="设置为可见"/>
  <odin:separator></odin:separator>
  <odin:buttonForToolBar text="设置为不可见" id="Invisible" tooltip="设置为不可见" isLast="true"/>
</odin:toolBar>
<odin:panel contentEl="groupTreeContent" property="groupTreePanel" topBarId="btnToolBar"></odin:panel>
<odin:window src="/blank.htm" id="personComDetailWin" width="600" height="500" title="人员不可见设置页面"></odin:window>  

