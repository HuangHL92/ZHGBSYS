<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<ss:toolBar property="bar1" isFloat="true">
	<odin:fill />
	<odin:opLogButtonForToolBar />
	<ss:doSaveBtn />
	<ss:resetBtn />
</ss:toolBar>
<ss:hlistDiv id="1" cols="6">
<ss:module param="P0,P3">
<ss:query property="psidnew" p="R" onchange="true"></ss:query>
</ss:module>
<ss:module param="P1,P2">
<ss:query property="psquery" p="R" onchange="true"></ss:query>
</ss:module>
<ss:textEdit property="aac003" label="����" p="P0/P3:R,P1/P2:E"  />
<ss:select property="aac004"  label="�Ա�"  codeType="AAC004" multiSelect="true" onchange="true" p="E"/>
<ss:query property="cpquery" p="R"></ss:query>
<ss:textEdit property="aab004" label="��λ����" p="D"  />
<ss:dateEdit property="aac006" label="��������" format="Y-m-d" p="D"/>
<ss:select property="aac005"  label="����"  codeType="AAC005"  p="P0/P3:R,P1/P2:D" />
<ss:textEdit property="eac001" label="���˱���" p="D"  />
<ss:textEdit property="eab217" label="����" p="P0/P1/P2:R,P3:D"  onchange="true"/>
<ss:numberEdit property="aic020" label="�ϱ�����" p="R" onchange="true" decimalPrecision="0"/>
<ss:dateEdit property="aac007" label="�μӹ���ʱ��" p="P0/P3:R,P1/P2:D"  format="Ym"/>
<ss:select property="eac070"  label="��Ա����"  codeType="EAC070"  p="D" />
<ss:select property="eac066"  label="�������"  codeType="EAC066"  p="R" />
<ss:select property="aac013"  label="�ù���ʽ"  codeType="AAC013"  p="E" />
<ss:dateEdit property="eae247" label="�䶯ʱ��" p="P0/P3:R,P1/P2:D" />
<ss:module param="P0,P1,P3">
<ss:select property="eae204"  label="�䶯ԭ��"  codeType="EAE204"  p="R" />
</ss:module>
<ss:textEdit property="eae206" label="�䶯��ע" p="E"  />
<ss:textEdit property="aae013" label="�α���ע" p="E"  />
<ss:select property="aac009"  label="������ϵ"  codeType="AAC009"  p="P0/P3:R,P1/P2:D" onchange="true"/>
<ss:select property="aaz289_1"  label="�������"  codeType="AAZ289"  p="P0/P1/P2:R,P3:D" onchange="true"/>
<ss:select property="eac158"  label="ҽ�����"  codeType="EAC158"  p="R" filter="aaa102 not in('30')" onchange="true"/>
<ss:select property="eac157"  label="ҽ�������"  codeType="EAC157"  p="R" onchange="true"/>
<ss:textEdit property="aab030" label="��˰����" p="E"  onchange="true"/>
<ss:textEdit property="aae004" label="��ϵ��" p="E"  />
<ss:textEdit property="aae005" label="��ϵ�绰" p="E"  />
<ss:textEdit property="aae006" label="��ϵ��ַ" p="E"  />
<ss:select property="aae008"  label="ί�л���"  codeType="AAE008"  onchange="true"/>
<ss:numberEdit property="aae010" label="�����˺�" p="E" onchange="true" decimalPrecision="0"/>
<ss:select property="aaa130"  label="������"  codeType="AAA130"  p="R" onchange="true"/>
<ss:textEdit property="aac010" label="������" p="E"  />
<ss:select property="eac238"  label="������Ա"  codeType="EAC238"  p="E" onchange="true"/>
<ss:select property="eac239"  label="�ۺ�����"  codeType="EAC239"  p="E" onchange="true"/>
<ss:select property="z_eac203_eac202_021810"  label="��������Ա"  codeType="Z_EAC203_EAC202_021810"  p="E"  onchange="true"/>
<ss:select property="ybzdhf"  label="ҽ���жϲ���"  codeType="YBZDHF"  p="D" onchange="true"/>
<ss:select property="ylzdhf"  label="�����жϲ���"  codeType="YLZDHF"  p="D" onchange="true"/>
<ss:select property="syzdhf"  label="ʧҵ�жϲ���"  codeType="SYZDHF"  p="D" onchange="true"/>
<ss:numberEdit property="eae007" label="��������" p="P0/P2:D,P1/P3:E" onchange="true" decimalPrecision="0"/>
<ss:module param="P0,P3">
<ss:numberEdit property="aaz157" label="��Ա����" p="H" />
</ss:module>
<ss:module param="P1,P2">
<ss:numberEdit property="aac001" label="�������" p="H" />
</ss:module>
<ss:textEdit property="aae135" label="���֤��" p="H"  />
<ss:select property="eac026"  label="��Ա״̬"  codeType="EAC026"  p="H" />
<ss:select property="eab216"  label="���״̬"  codeType="EAB216"  p="H" />
<ss:textEdit property="eae363" label="��ʱ�˻����" p="H"  />
<ss:textEdit property="z_eac203_eac202_001400" label="����˱�־" p="H"  />
<ss:textEdit property="aaa130_old" label="��������ֵ" p="H"  />
<ss:textEdit property="aac010_old" label="�����س�ֵ" p="H"  />
<ss:textEdit property="ry_aab030" label="��Ա��˰����" p="H"  />
<ss:textEdit property="zdbj" label="�жϲ��ɱ�־" p="H"  />
<ss:numberEdit property="eae323" label="Ӧ������" p="H" />
<ss:textEdit property="msg" label="������ʾ��Ϣ" p="H"  />
</ss:hlistDiv>
<odin:gridSelectColJs name="aac008" codeType="AAC008"></odin:gridSelectColJs>
<odin:gridSelectColJs name="aae140" codeType="AAE140"></odin:gridSelectColJs>
<ss:editgrid property="div_2"  pageSize="200" afteredit="radow.cm.afteredit"  isFirstLoadData="false" url="/" title=""  height="500">
<odin:gridJsonDataModel>
  <odin:gridDataCol name="checked" />
  <odin:gridDataCol name="aae140" />
  <odin:gridDataCol name="aae180" />
  <odin:gridDataCol name="aaa042"/>
  <odin:gridDataCol name="aaa041" />
  <odin:gridDataCol name="aaa046" />
  <odin:gridDataCol name="aaa048"/>
  <odin:gridDataCol name="aac049" />
  <odin:gridDataCol name="aaz289" />
  <odin:gridDataCol name="aaa044"/>
    <odin:gridDataCol name="aac008" />
  <odin:gridDataCol name="eaa255" />
  <odin:gridDataCol name="aaz159"/>
  <odin:gridDataCol name="aaz157" />
  <odin:gridDataCol name="aae206" />
  <odin:gridDataCol name="eaz001"/>
  <odin:gridDataCol name="aaz001" isLast="true"/>
</odin:gridJsonDataModel>
<odin:gridColumnModel>
  <odin:gridRowNumColumn />
    <odin:gridColumn header="selectall" width="50" dataIndex="checked" editor="checkbox" edited="true" checkBoxClick="radow.cm.doGridCheck"/>
  <ss:gridEditColumn header="����" width="80" dataIndex="aae140" editor="select" p="E" codeType="AAE140"/>
  <ss:gridEditColumn header="�ɷѻ���" width="80" dataIndex="aae180" editor="number" p="R" decimalPrecision="0"/>
  <ss:gridEditColumn header="��λ�ɷ�" width="80" dataIndex="aaa042" editor="number" p="D" />
  <ss:gridEditColumn header="���˽ɷ�" width="80" dataIndex="aaa041" editor="number" p="D" />
  <ss:gridEditColumn header="��������" width="80" dataIndex="aaa046" editor="number" p="E"/>
  <ss:gridEditColumn header="��������" width="80" dataIndex="aaa048" editor="number" p="D"/>
  <ss:gridEditColumn header="����ʱ��" width="80" dataIndex="aac049" editor="date" p="D"/>
  <ss:gridEditColumn header="���ʱ���" width="80" dataIndex="aaz289" editor="text" p="D"/>
  <ss:gridEditColumn header="������Ϣ" width="300" dataIndex="aaa044" editor="text" />
    <ss:gridEditColumn header="����״̬" width="70" dataIndex="aac008" editor="select" p="H" codeType="AAC008"/>
  <ss:gridEditColumn header="����������" width="70" dataIndex="eaa255" editor="text" p="H"/>
  <ss:gridEditColumn header="��������" width="120" dataIndex="aaz159" editor="number" p="H" />
  <ss:gridEditColumn header="��ԱID" width="260" dataIndex="aaz157" editor="number" p="H" />
  <ss:gridEditColumn header="��������" width="70" dataIndex="aae206" editor="number" p="H"/>
  <ss:gridEditColumn header="�½ɷ���֯ID" width="140" dataIndex="eaz001" editor="number" p="H"/>
  <ss:gridEditColumn header="ԭ�ɷ���֯ID" width="20" dataIndex="aaz001" editor="number"  isLast="true" p="H"/>
</odin:gridColumnModel>		
</ss:editgrid>