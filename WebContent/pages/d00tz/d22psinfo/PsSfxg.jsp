<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<ss:toolBar property="bar1">
	<odin:fill></odin:fill>
	<ss:doSaveBtn></ss:doSaveBtn>
    <ss:resetBtn></ss:resetBtn>
</ss:toolBar>
<ss:hlistDiv id="div_1" cols="6">
	<ss:query property="psquery" onchange="true" p="R"></ss:query>
	<ss:textEdit property="aac003" label="����" p="D"></ss:textEdit>
</ss:hlistDiv>	
<odin:groupBox>	
<ss:hlistDiv id="div_2" cols="6">
<ss:textEdit property="aae135" p="D" label="���֤��" onchange="true"></ss:textEdit>
<ss:textEdit property="aac003_2" p="E" label="����"></ss:textEdit>
<ss:dateEdit property="aac006" p="D" label="��������"></ss:dateEdit>
<ss:select property="eac201" p="D" label="���������϶���־" codeType="EAC201"></ss:select>
<ss:select property="aac004" p="P0:E,P1:D" label="�Ա�" codeType="AAC004"></ss:select>
<ss:select property="eab216" p="D" label="���״̬" codeType="EAB216"></ss:select>
<ss:select property="aac005" p="D" label="����" codeType="AAC005"></ss:select>
<ss:dateEdit property="alc040" p="D" label="��������"></ss:dateEdit>
<ss:select property="aac009" p="P0:E,P1:D" label="��������" codeType="AAC009"></ss:select>
<ss:select property="zhbg" p="P0:E,P1:D" label="�˻����ԭ��" onchange="true" data="['1','ͬ��ת��',''],['2','ת���ػ���',''],['3','�������','']"></ss:select>
<ss:select property="eae363" p="P0:E,P1:D" label="������ʱ�˻���־" codeType="EAE363"></ss:select>
<!--  ��aac010 �������ڵ�  Ӧ���� ac01�е�  AAA130  ��ס����������������  ������ ����AAA130  select���� ����ֶ���ʾ������ ���������ڵ� ��-->
<ss:select property="aaa130" p="P0:E,P1:D" label="��ס������������" onchange="true" codeType="AAA130"></ss:select>
<ss:textEdit property="aac010" p="P0:E,P1:D" label="�������ڵ�"></ss:textEdit>
<ss:select property="aac011" p="P0:E,P1:D" label="�Ļ��̶�" codeType="AAC011"></ss:select>
<ss:select property="aac014" p="P0:E,P1:D" label="רҵ����ְ��" codeType="AAC014"></ss:select>		
<ss:select property="aac015" p="P0:E,P1:D" label="����ְҵ�ʸ�ȼ�" codeType="AAC015"></ss:select>
<ss:select property="aac017" p="P0:E,P1:D" label="����״��" codeType="AAC017"></ss:select>
<ss:select property="aac020" p="P0:E,P1:D" label="����ְ��(����)" codeType="AAC020"></ss:select>										
<ss:textEdit property="aae006" p="P0:E,P1:D" label="�־�ס��ַ" width="580" colspan="4"></ss:textEdit>								
<ss:textEdit property="aae007" p="P0:E,P1:D" label="��������" maxlength="3"></ss:textEdit>
<ss:textEdit property="aae004" p="P0:E,P1:D" label="��ϵ������"></ss:textEdit>
<ss:textEdit property="aae005" p="P0:E,P1:D" label="��ϵ�绰"></ss:textEdit>
<ss:textEdit property="eac101" p="P0:E,P1:D" label="�ֻ�"></ss:textEdit>
<ss:textEdit property="aae159" p="P0:E,P1:D" label="��ϵ��������"></ss:textEdit>
<ss:textEdit property="aab401" p="P0:E,P1:D" label="����"></ss:textEdit>
<ss:select property="aaa156" p="P0:E,P1:D" label="�뻧����ϵ" codeType="AAA156"></ss:select>
<ss:textEdit property="aac012" p="P0:E,P1:D" label="���"></ss:textEdit>
<ss:textEdit property="aac003_old" label="����_�޸�ǰ" p="H"></ss:textEdit>
<ss:textEdit property="aac003_new" label="����_�޸ĺ�" p="H"></ss:textEdit>
<ss:textEdit property="aae135_old" label="���֤��_�޸�ǰ" p="H"></ss:textEdit>

<ss:textEdit property="aae135_new" label="���֤��_�޸ĺ�" p="H"></ss:textEdit>
<ss:textEdit property="loginname" label="����Ա" p="H"></ss:textEdit>
<ss:textEdit property="aab001_temp" label="��λ����" p="H"></ss:textEdit>

<ss:textEdit property="aab004_temp" label="��λ����" p="H"></ss:textEdit>
<ss:numberEdit property="eae201" label="�����˱䶯ID" p="H"></ss:numberEdit>
<ss:textEdit property="aaa027" label="ͳ����" p="H"></ss:textEdit>
				
<ss:textEdit property="aac006_temp" label="��������" p="H"></ss:textEdit>	
<ss:textEdit property="eac070" label="��Ա���ͣ�����ͳ����" p="H"></ss:textEdit>	
<ss:textEdit property="eac001" label="���˱�ţ�����ͳ����" p="H"></ss:textEdit>	

<ss:textEdit property="aae008" label="ί�л���::��������" p="H"></ss:textEdit>	
<ss:textEdit property="aae010" label="�����˺�" p="H"></ss:textEdit>				
</ss:hlistDiv>
</odin:groupBox>
<odin:gridSelectColJs name="eac070"  codeType="EAC070" />
<odin:gridSelectColJs name="eac026"  codeType="EAC026" />
<odin:gridSelectColJs name="aac008_1"  codeType="AAC008" />
<odin:gridSelectColJs name="aac008_2"  codeType="AAC008" />
<odin:gridSelectColJs name="aac008_3"  codeType="AAC008" />
<odin:gridSelectColJs name="aac008_4"  codeType="AAC008" />
<odin:gridSelectColJs name="aac008_5"  codeType="AAC008" />
<odin:gridSelectColJs name="aac008_6"  codeType="AAC008" />
<odin:gridSelectColJs name="aae140_cxjm"  codeType="AAC008" />
<odin:gridSelectColJs name="aab301"  codeType="AAB301" />		
<odin:editgrid property="div_3" pageSize="150" bbarId="pageToolBar"  autoFill=""  url="/" width="780" height="150">
<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
<odin:gridDataCol name="aac003" />
<odin:gridDataCol name="eab217" type="float"/>
<odin:gridDataCol name="eac070" />
<odin:gridDataCol name="eac026" />
<odin:gridDataCol name="aac008_1" />
<odin:gridDataCol name="aac008_2" />
<odin:gridDataCol name="aac008_3" />
<odin:gridDataCol name="aac008_4" />
<odin:gridDataCol name="aac008_5" />
<odin:gridDataCol name="aac008_6" />
<odin:gridDataCol name="aae140_cxjm" />
<odin:gridDataCol name="eac001" />
<odin:gridDataCol name="aab301" />
<odin:gridDataCol name="hz" />
<odin:gridDataCol name="aac007" />
<odin:gridDataCol name="aab001" />
<odin:gridDataCol name="aab004" />
<odin:gridDataCol name="aaz001" />
<odin:gridDataCol name="aaz157" isLast="true"/>
</odin:gridJsonDataModel>
<odin:gridColumnModel>
<odin:gridRowNumColumn />
<ss:gridEditColumn header="����" width="60" dataIndex="aac003" editor="text" p="D"/>
<ss:gridEditColumn header="����1" width="60" dataIndex="eab217" editor="number" maxLength="3"  p="E"/>
<ss:gridEditColumn header="��Ա����" width="80" dataIndex="eac070" editor="select" codeType="EAC070" p="D"/>
<ss:gridEditColumn header="�α�״̬" width="80" dataIndex="eac026" editor="select" codeType="EAC026" p="D"/>
<ss:gridEditColumn header="���ϱ���" width="60" dataIndex="aac008_1" editor="select" codeType="AAC008" p="D"/>
<ss:gridEditColumn header="ҽ�Ʊ���" width="60" dataIndex="aac008_2" editor="select" codeType="AAC008" p="D"/>
<ss:gridEditColumn header="ʧҵ����" width="60" dataIndex="aac008_3" editor="select" codeType="AAC008" p="D"/>
<ss:gridEditColumn header="���˱���" width="60" dataIndex="aac008_4" editor="select" codeType="AAC008" p="D"/>
<ss:gridEditColumn header="��������" width="60" dataIndex="aac008_5" editor="select" codeType="AAC008" p="D"/>
<ss:gridEditColumn header="��������" width="60" dataIndex="aac008_6" editor="select" codeType="AAC008" p="D"/>
<ss:gridEditColumn header="�������" width="80" dataIndex="aae140_cxjm" editor="select" codeType="AAC008" p="D"/>
<ss:gridEditColumn header="���˱���" width="70" dataIndex="eac001" editor="text" p="D"/>
<ss:gridEditColumn header="Ͻ��" width="80" dataIndex="aab301" editor="select" codeType="AAB301" p="D"/>
<ss:gridEditColumn header="�μӹ�������" width="80" dataIndex="aac007" editor="text" p="D"/>
<ss:gridEditColumn header="��λ����" width="80" dataIndex="aab001" editor="text" p="D"/>
<ss:gridEditColumn header="��λ����" width="80" dataIndex="aab004" editor="text" p="D"/>
<ss:gridEditColumn header="��֯ID" width="80" dataIndex="aaz001" editor="number" p="H"/>
<ss:gridEditColumn header="��Ա��֯ID" width="80" dataIndex="aaz157" editor="number" p="H"/>
<ss:gridEditColumn header="hz" width="80" dataIndex="hz" editor="text" p="H" isLast="true"/>
</odin:gridColumnModel>
</odin:editgrid>

