<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<ss:toolBar property="bar1">
	<odin:fill></odin:fill>
	<odin:opLogButtonForToolBar></odin:opLogButtonForToolBar>
<ss:doSaveBtn></ss:doSaveBtn>
<ss:resetBtn></ss:resetBtn>
</ss:toolBar>
<ss:hlistDiv id="1" cols="6">
<ss:query property="cpquery" p="P0:H,P1:R" onchange="true"/>
<%-- <ss:textEdit property="cpquery" label="��λ��ѯ" p="P0:H,P1:R" onchange="true"/>
 --%><ss:textEdit property="aab003" label="��֯��������" p="R" onchange="true" maxlength="10"/>
<ss:textEdit property="aab030" label="��˰����" p="R"  onchange="true"/>

<ss:textEdit property="aab004" label="��λ����" p="R" onchange="true"/>
<ss:textEdit property="aab001" label="��λ����" p="P0:R,P1:D" onchange="true"/>
<ss:select property="aab301"  label="Ͻ��"  codeType="AAB301"  p="R"/>

<ss:textEdit property="aae006" label="��λ��ַ" p="R" maxlength="100" width="300" onchange="true"/>
<ss:dateEdit property="eae278" label="�������" p="R" />
<ss:textEdit property="eab231" label="��λ�绰" p="E" maxlength="20"/>

<ss:textEdit property="eae284" label="��λͨѸ��ַ" p="E" maxlength="60" width="300"/>
<ss:textEdit property="aae007" label="�ʱ�" p="E" maxlength="6" onchange="true"/>
<ss:textEdit property="aae159" label="��������" p="E" mask="email" maxlength="50"/>

<ss:textEdit property="eae279" label="�������" p="E" maxlength="30" onchange="true"/>
<ss:select property="aab006" codeType="AAB006" label="ִ������" p="R"/>
<ss:textEdit property="aab007" label="ִ�պ���" p="E"  maxlength="24"/>

<ss:dateEdit property="aab008" label="���̷�֤����" p="E"/>
<ss:dateEdit property="aab009" label="���̵Ǽ���Ч����" p="E"/>
<ss:select property="eab215" codeType="EAB215" label="��λ����" p="R" filter="aaa105 = '0'"/>

<ss:select property="aab022" codeType="AAB022" label="��ҵ����" p="R" onchange="true"/>
<ss:select property="eab226" codeType="EAB226" label="��ҵ����" p="E" />
<ss:textEdit property="aae048" label="��׼��λ" p="E" maxlength="100" />

<ss:dateEdit property="aae049" label="��׼����" p="E"/>
<ss:textEdit property="aae051" label="��׼�ĺ�" p="E" maxlength="100" />
<ss:textEdit property="aae045" label="��������" p="E" maxlength="50" />

<ss:textEdit property="aae046" label="�������֤" p="E" maxlength="18" />
<ss:textEdit property="eae280" label="���˵绰" p="E" maxlength="40" />
<ss:textEdit property="eae281" label="ר��Ա����" p="E" maxlength="50" />

<ss:textEdit property="eae283" label="ר��Ա�绰" p="E" maxlength="64" />
<ss:textEdit property="eab230" label="ר��Ա�ֻ�" p="E" maxlength="11" onchange="true"/>
<ss:select property="aaa149" codeType="AAA149" label="���˷��ʻ�׼" p="E" />

<ss:select property="aab021" codeType="AAB021" label="¼����ϵ" p="R" />
<ss:select property="aab023" codeType="AAB023" label="���ܲ���" p="E" />
<ss:textEdit property="aab002" label="�籣�Ǽ�֤����" p="R"  onchange="true"/>

<ss:textEdit property="eab227" label="�浵��" p="R"  />
<ss:select property="aaf015" codeType="AAF015" label="�����ֵ�" p="R" onchange="true"/>
<ss:select property="aaf020" codeType="AAF020" label="˰�����ղ���" p="E" />

<ss:select property="eab026" codeType="EAB026" label="��λ�α�״̬" p="H" />
<ss:select property="eab014" codeType="EAB014" label="��֯����" p="H" />
<ss:textEdit property="eab219" label="��֯��������" p="H"  />
<ss:select property="aaa027" codeType="AAA027" label="ͳ����" p="H" />
<ss:select property="eab221" codeType="EAB221" label="���幤�̻�" p="H" />
<%-- <ss:textEdit property="aaz001" label="��λ��ѯ" p="H" />
 --%>
</ss:hlistDiv>