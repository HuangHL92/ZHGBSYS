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
      <odin:groupBox title="������" property="ggBox">
      <table width="100%">
        <tr>
          <odin:textEdit property="searchA0184"  label="����Ա���֤"/>
          <odin:textEdit property="searchA0101"  label="����Ա����"/>
        </tr>
        <tr>
          <td height="5" colspan="4"></td>
        </tr>
      </table>
      </odin:groupBox>
    <odin:editgrid property="personGrid" title="����Ա�б�" autoFill="false" width="500" height="300" bbarId="pageToolBar" pageSize="20" isFirstLoadData="false" url="/">
      <odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
          <odin:gridDataCol name="logchecked"/>
          <odin:gridDataCol name="a0184" />
          <odin:gridDataCol name="a0101" isLast="true"/>
        </odin:gridJsonDataModel>
        <odin:gridColumnModel>
          <odin:gridColumn header="" width="25" editor="checkbox" dataIndex="logchecked" edited="true"/>
          <odin:gridRowNumColumn></odin:gridRowNumColumn>
          <odin:gridColumn dataIndex="a0184" width="110" header="����Ա���֤" align="center"/>
          <odin:gridColumn dataIndex="a0101" width="300" header="����Ա����" align="center" isLast="true"/>    
        </odin:gridColumnModel>
      </odin:editgrid>
    </td>
  </tr>
</table>
</div>

<odin:toolBar property="btnToolBar">
  <odin:textForToolBar text="<h3>����Ա�Ƿ�ɼ�</h3>" />
  <odin:fill />
  <odin:separator></odin:separator>
  <odin:buttonForToolBar text="��ѯ�ɼ���Ա" id="find" tooltip="��ѯ�ɼ���Ա"/>
  <odin:separator></odin:separator>
  <odin:buttonForToolBar text="��ѯ���ɼ���Ա" id="find2" tooltip="��ѯ���ɼ���Ա"/>
  <odin:separator></odin:separator>
  <odin:buttonForToolBar text="����Ϊ�ɼ�" id="visible" tooltip="����Ϊ�ɼ�"/>
  <odin:separator></odin:separator>
  <odin:buttonForToolBar text="����Ϊ���ɼ�" id="Invisible" tooltip="����Ϊ���ɼ�" isLast="true"/>
</odin:toolBar>
<odin:panel contentEl="groupTreeContent" property="groupTreePanel" topBarId="btnToolBar"></odin:panel>
<odin:window src="/blank.htm" id="personComDetailWin" width="600" height="500" title="��Ա���ɼ�����ҳ��"></odin:window>  

