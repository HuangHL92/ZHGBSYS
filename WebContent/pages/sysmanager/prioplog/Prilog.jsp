<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<odin:toolBar property="btnToolBar">
	<odin:textForToolBar text="<h3>Ȩ��ϵͳ��־</h3>"/>
	<odin:fill/>
	<odin:buttonForToolBar id="btn_query"  text="��ѯ"  icon="/images/search.gif" cls="x-btn-text-icon"/>
	<odin:buttonForToolBar id="clean"  text="����"  icon="/images/sx.gif" isLast="true" cls="x-btn-text-icon"/>
</odin:toolBar>

<div id="QueryContent">
	<table width="100%">
		<tr>
			<td height="16"  colspan="4"></td>
		</tr>
		<tr>
	    	<odin:select property="model"  label="ģ��" data="['RESOURCE', '��Դ'],['ROLE', '��ɫ'],['USER', '�û�'],['GROUP', '�û���']" />
	    	<odin:dateEdit property="timefr" label="ʱ��" ></odin:dateEdit>
	    	<odin:textEdit property="otherMess"  label="�ؼ���Ϣ"/>
		</tr>
		<tr>
			<td height="16" colspan="4"></td>
		</tr>
	</table>		
</div>
<odin:panel contentEl="QueryContent" property="QueryPanel" topBarId="btnToolBar"></odin:panel>

<odin:editgrid property="loggrid" title="��־����" autoFill="false" width="771" height="410" bbarId="pageToolBar" pageSize="20" isFirstLoadData="false" url="/">
			<odin:gridJsonDataModel  id="logid" root="data" totalProperty="totalCount">
					  <odin:gridDataCol name="logid" />
					  <odin:gridDataCol name="loginname" />
					  <odin:gridDataCol name="opdate" />
					  <odin:gridDataCol name="opdesc" />
					  <odin:gridDataCol name="opaddress" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					  <odin:gridRowNumColumn></odin:gridRowNumColumn>
					  <odin:gridColumn header="logid" hidden="true" dataIndex="id"/>
					  <odin:gridColumn header="������Ա" dataIndex="loginname" align="center" width="105"/>
					  <odin:gridColumn header="����ʱ��" dataIndex="opdate" align="center" width="155"/>
					  <odin:gridColumn header="��������" dataIndex="opdesc" align="center" width="190"/>
					  <odin:gridColumn header="������ע" dataIndex="opaddress" align="center" width="240"/>
					  <odin:gridColumn header="ɾ��" dataIndex="logid" renderer="radow.commGridColDelete" align="center" width="40" isLast="true"/>
				</odin:gridColumnModel>
			</odin:editgrid>

