<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib uri="/WEB-INF/odin-local.tld" prefix="cm"%>

<ss:toolBar property="bar1">
	<odin:fill></odin:fill>
	<ss:doQueryBtn />
	<ss:doSaveBtn />
	<ss:resetBtn />
</ss:toolBar>

<ss:hlistDiv id="1" cols="6" >
	<ss:query property="psquery_nonem" p="E" ></ss:query>
	<ss:query property="cpquery_nonem" p="E"></ss:query>
<ss:textEdit property="aab004_nonem" label="��λ����" p="D"  width="300"/>

<ss:dateEdit property="aae036" label="ҵ�����ʱ��(��ʼ)" p="E" width="160"/>
<ss:dateEdit property="aae036_S" label="ҵ�����ʱ��(����)" p="E" width="160"/>
<ss:select property="ead201"  label="����֧������"  codeType="EAD201" p="P0:E,P1:D"  width="180"/>

<ss:select property="aae011_b"  label="������Ա"  codeType="USER"  p="E" width="160" onchange="true"/>
<ss:select property="eaz234"  label="��������"  codeType="EAZ234"  p="E" width="160" allAsItem="true" onchange="true"/>
<ss:select property="ead202"  label="ҵ���������"  codeType="EAD202"  p="E" width="180" allAsItem="true" onchange="true"/>

<ss:numberEdit property="aaz157_a" label="��Ա����" p="H"/>
<ss:numberEdit property="aaz001_c" label="��Ա����" p="H"/>
<ss:numberEdit property="bz_nonem" label="Ȩ�޿���" p="H"/>
</ss:hlistDiv>

<odin:gridSelectColJs name="ead040" codeType="EAD040"></odin:gridSelectColJs>
<odin:gridSelectColJs name="ead131" codeType="EAD131"></odin:gridSelectColJs>
<odin:gridSelectColJs name="ead202" codeType="EAD202"></odin:gridSelectColJs>
<odin:gridSelectColJs name="aae010" codeType="USER"></odin:gridSelectColJs>
<odin:gridSelectColJs name="aae011" codeType="AAE011"></odin:gridSelectColJs>
<odin:gridSelectColJs name="eae236" codeType="EAE236"></odin:gridSelectColJs>

<ss:editgrid property="div_2"  pageSize="15" afteredit="radow.cm.afteredit"  isFirstLoadData="false" url="/" title=""  height="400" bbarId="pageToolBar">
<odin:gridJsonDataModel>
  <odin:gridDataCol name="logchecked" />
  <odin:gridDataCol name="info" />
  <odin:gridDataCol name="eac001"/>
  <odin:gridDataCol name="aae135" />
  <odin:gridDataCol name="eab217" />
  <odin:gridDataCol name="aac003"/>
  <odin:gridDataCol name="py" />
  <odin:gridDataCol name="eaz050" />
  <odin:gridDataCol name="ead040"/>
    <odin:gridDataCol name="ead131" />
  <odin:gridDataCol name="aab001" />
  <odin:gridDataCol name="aab004"/>
  <odin:gridDataCol name="ead201" />
  <odin:gridDataCol name="ead202" />
  <odin:gridDataCol name="aae008"/>
  <odin:gridDataCol name="aae010" />
  <odin:gridDataCol name="aae013" />
  <odin:gridDataCol name="aae011"/>
    <odin:gridDataCol name="aae036" />
  <odin:gridDataCol name="aae037" />
    <odin:gridDataCol name="eae237" />
  <odin:gridDataCol name="eae236"/>
  <odin:gridDataCol name="ead028" />
  <odin:gridDataCol name="aaz002" />
  <odin:gridDataCol name="aae037"/>
  <odin:gridDataCol name="ead132" />
  <odin:gridDataCol name="logaaz157" isLast="true"/>
</odin:gridJsonDataModel>
<odin:gridColumnModel>
  <odin:gridRowNumColumn />
  <odin:gridColumn header="selectall" width="170" dataIndex="logchecked" editor="checkbox" edited="true" checkBoxClick="radow.cm.doGridCheck" gridName="div_2"/>
  <ss:gridEditColumn header="������ϸ" width="170" dataIndex="info" editor="text" p="D" renderer="renderClick"/>
  <ss:gridEditColumn header="��Ա����" width="170" dataIndex="eac001" editor="text" p="D"/>
    <ss:gridEditColumn header="��ᱣ�Ϻ�" width="170" dataIndex="aae135" editor="text" p="D"/>
  <ss:gridEditColumn header="����" width="170" dataIndex="eab217" editor="text" p="D"/>
  <ss:gridEditColumn header="����" width="170" dataIndex="aac003" editor="text" p="D"/>
    <ss:gridEditColumn header="���" width="170" dataIndex="py" editor="number" p="D"/>
  <ss:gridEditColumn header="֧Ʊ��" width="170" dataIndex="eaz050" editor="text" p="E"/>
  <ss:gridEditColumn header="������㷽ʽ" width="170" dataIndex="ead040" editor="select" p="E" codeType="EAD040"/>
    <ss:gridEditColumn header="����������" width="170" dataIndex="ead131" editor="date" p="E" />
  <ss:gridEditColumn header="��λ����" width="170" dataIndex="aab001" editor="text" p="D"/>
  <ss:gridEditColumn header="��λ����" width="170" dataIndex="aab004" editor="text" p="D"/>
    <ss:gridEditColumn header="��������" width="170" dataIndex="ead201" editor="text" p="D"/>
  <ss:gridEditColumn header="ҵ���������" width="170" dataIndex="ead202" editor="select" p="D" codeType="EAD202"/>
  <ss:gridEditColumn header="���˿�������" width="170" dataIndex="aae008" editor="text" p="P0:D,P1:H"/>
    <ss:gridEditColumn header="�����ʺ�" width="170" dataIndex="aae010" editor="select" p="P0:D,P1:H" codeType="USER"/>
  <ss:gridEditColumn header="��ע" width="170" dataIndex="aae013" editor="text" p="D"/>
  <ss:gridEditColumn header="������" width="170" dataIndex="aae011" editor="select" p="D" codeType="AAE011"/>
    <ss:gridEditColumn header="����ʱ��" width="170" dataIndex="aae036" editor="number" p="D"/>
  <ss:gridEditColumn header="ҵ������" width="170" dataIndex="aae037" editor="number" p="P0:H,P1:D"/>
  <ss:gridEditColumn header="��ӡ����" width="170" dataIndex="eae237" editor="date" p="D"/>
    <ss:gridEditColumn header="��ӡ����Ա" width="170" dataIndex="eae236" editor="select" p="D" codeType="EAE236"/>
  <ss:gridEditColumn header="����ʵ��ʵ��ID" width="170" dataIndex="ead028" editor="number" p="H"/>
  <ss:gridEditColumn header="�������" width="170" dataIndex="aaz002" editor="number" p="H"/>
    <ss:gridEditColumn header="opym" width="170" dataIndex="aae037" editor="number" p="H"/>
  <ss:gridEditColumn header="reym" width="170" dataIndex="ead132" editor="number" p="H"/>
  <ss:gridEditColumn header="��Ա����" width="120" dataIndex="logaaz157" editor="number"  isLast="true" p="H"/>
</odin:gridColumnModel>		
</ss:editgrid>
