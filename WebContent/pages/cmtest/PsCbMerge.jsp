<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<ss:toolBar property="bar1">
	<odin:fill></odin:fill>
<ss:doSaveBtn></ss:doSaveBtn>
<ss:resetBtn></ss:resetBtn>
</ss:toolBar>
<ss:hlistDiv id="1" cols="6">
<ss:query property="psidquery" p="R" />
<ss:textEdit property="aac003" label="����" p="R" p="D"/>
<ss:select property="eab216"  label="���״̬"  codeType="EAB216"  p="H" />
</ss:hlistDiv>

<odin:gridSelectColJs name="yanglao" codeType="YANGLAO"></odin:gridSelectColJs>
<odin:gridSelectColJs name="yibao" codeType="YIBAO"></odin:gridSelectColJs>
<odin:gridSelectColJs name="shiye" codeType="SHIYE"></odin:gridSelectColJs>
<odin:gridSelectColJs name="gongshang" codeType="GONGSHANG"></odin:gridSelectColJs>
<odin:gridSelectColJs name="shengyu" codeType="SHENGYU"></odin:gridSelectColJs>

<odin:gridSelectColJs name="jiaoyu" codeType="JIAOYU"></odin:gridSelectColJs>
<odin:gridSelectColJs name="qiye" codeType="QIYE"></odin:gridSelectColJs>
<odin:gridSelectColJs name="shiye" codeType="SHIYE"></odin:gridSelectColJs>
<odin:gridSelectColJs name="beizhengdi" codeType="BEIZHENGDI"></odin:gridSelectColJs>
<odin:gridSelectColJs name="nongcun" codeType="NONGCUN"></odin:gridSelectColJs>

<odin:gridSelectColJs name="eac158" codeType="EAC158"></odin:gridSelectColJs>
<odin:gridSelectColJs name="eac157" codeType="eac157"></odin:gridSelectColJs>
<odin:gridSelectColJs name="eac070" codeType="EAC070"></odin:gridSelectColJs>
<odin:gridSelectColJs name="eac157" codeType="EAC157"></odin:gridSelectColJs>
<odin:gridSelectColJs name="aab301" codeType="AAB301"></odin:gridSelectColJs>
<odin:editgrid property="div_2"  pageSize="160" afteredit="radow.cm.afteredit"  isFirstLoadData="false" url="/" title=""  height="200" bbarId="pageToolBar">
<odin:gridJsonDataModel>
	<odin:gridDataCol name="checked1" />
	<odin:gridDataCol name="checked" />
  <odin:gridDataCol name="aae135" />
  <odin:gridDataCol name="aac003" />
  <odin:gridDataCol name="yanglao"/>
  <odin:gridDataCol name="yibao" />
  <odin:gridDataCol name="shiye" />
  <odin:gridDataCol name="gongshang"/>
  <odin:gridDataCol name="shengyu" />
  <odin:gridDataCol name="jiaoyu" />
  <odin:gridDataCol name="qiye"/>
  <odin:gridDataCol name="beizhengdi" />
  <odin:gridDataCol name="nongcun" />
  <odin:gridDataCol name="eac001"/>
  <odin:gridDataCol name="eac070" />
  <odin:gridDataCol name="aab001" />
  <odin:gridDataCol name="aab004"/>
  <odin:gridDataCol name="eac158" />
  <odin:gridDataCol name="eac157" />
  <odin:gridDataCol name="aaz001"/>
  <odin:gridDataCol name="eae201" />
  <odin:gridDataCol name="bt" isLast="true"/>
</odin:gridJsonDataModel>
<odin:gridColumnModel>
  <odin:gridRowNumColumn />
    	<odin:gridColumn  dataIndex="checked1" 
			editor="checkbox"  header="����" edited="true"  width="100"/>
  		<odin:gridColumn  dataIndex="checked" 
			editor="checkbox"  header="�ϲ�" edited="true"  width="100"/>
		<ss:gridEditColumn header="���֤��" width="70" dataIndex="aae135"
			editor="text" p="D" />
		<ss:gridEditColumn header="����" width="70" dataIndex="aac003"
			editor="text" p="D" />
		<ss:gridEditColumn header="����" width="120" dataIndex="yanglao"
			editor="select" p="D" codeType="YANGLAO" />
		<ss:gridEditColumn header="ҽ��" width="260" dataIndex="yibao"
			editor="select" p="D" codeType="YIBAO" />
		<ss:gridEditColumn header="ʧҵ" width="70" dataIndex="shiye"
			editor="select" p="D" codeType="SHIYE"/>
		<ss:gridEditColumn header="����" width="140" dataIndex="gongshang"
			editor="select" p="D"  codeType="GONGSHANG"/>
		<ss:gridEditColumn header="����" width="150" dataIndex="shengyu"
			editor="select" p="D" codeType="SHENGYU"/>
		<ss:gridEditColumn header="����" width="80" dataIndex="jiaoyu"
			editor="select" p="D" codeType="JIAOYU"/>
		<ss:gridEditColumn header="��" width="50" dataIndex="qiye"
			editor="select" codeType="QIYE"/>
		<ss:gridEditColumn header="����" width="70" dataIndex="beizhengdi"
			editor="select" p="D" codeType="BEIZHENGDI"/>
		<ss:gridEditColumn header="ũ��" width="70" dataIndex="nongcun"
			editor="select" p="D" codeType="NONGCUN"/>
		<ss:gridEditColumn header="���˱���" width="120" dataIndex="eac001"
			editor="text" p="D"  />
		<ss:gridEditColumn header="��Ա����" width="260" dataIndex="eac070"
			editor="select" p="D" codeType="EAC070" />
		<ss:gridEditColumn header="��λ����" width="70" dataIndex="aab001"
			editor="text" p="D" />
		<ss:gridEditColumn header="��λ����" width="140" dataIndex="aab004"
			editor="text" p="D" />
		<ss:gridEditColumn header="ҽ�����" width="150" dataIndex="eac158"
			editor="text" p="D" codeType="EAC158"/>
		<ss:gridEditColumn header="ҽ�������" width="80" dataIndex="eac157"
			editor="select" p="D" codeType="EAC157"/>
			<ss:gridEditColumn header="Ͻ��" width="80" dataIndex="aab301"
			editor="select" p="D" />
		<ss:gridEditColumn header="��Ա����" width="50" dataIndex="aaz157"
			editor="number"  p="H"/>
		<ss:gridEditColumn header="��λ����" width="50" dataIndex="aaz001"
			editor="number"  p="H"/>
		<ss:gridEditColumn header="�䶯id" width="50" dataIndex="eae201"
			editor="number" p="H"/>
		<ss:gridEditColumn header="����" width="20" dataIndex="bt" editor="number"
			isLast="true" p="H" />
	</odin:gridColumnModel>		
</odin:editgrid>