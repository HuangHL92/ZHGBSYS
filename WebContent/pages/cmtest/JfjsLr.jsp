<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<ss:toolBar property="bar1">
	<odin:fill></odin:fill>
	<ss:doImpBtn></ss:doImpBtn>
	<ss:doSaveBtn />
	<ss:resetBtn/>
</ss:toolBar>
<ss:hlistDiv id="1" cols="6">
<ss:query property="psquery_a" p="E" onchange="true"/>
<ss:textEdit property="aac003_nonem" label="����" />
<ss:query property="cpquery_a" p="R" onchange="true"/>
<ss:textEdit property="aab004_nonem" label="��λ����" />
<ss:select property="aab301_a"  label="����������"  codeType="AAB301"  p="H" />

</ss:hlistDiv>
<odin:gridSelectColJs name="eac238" codeType="EAC238"></odin:gridSelectColJs>
<odin:gridSelectColJs name="aaz289" codeType="AAZ289"></odin:gridSelectColJs>
<odin:gridSelectColJs name="eaa226" codeType="EAA226"></odin:gridSelectColJs>
<odin:gridSelectColJs name="ryfl" codeType="RYFL"></odin:gridSelectColJs>
<odin:gridSelectColJs name="aac009" codeType="AAC009"></odin:gridSelectColJs>
<odin:gridSelectColJs name="eac158" codeType="EAC158"></odin:gridSelectColJs>
<odin:gridSelectColJs name="eac157" codeType="EAC157"></odin:gridSelectColJs>
<odin:gridSelectColJs name="eaz247" codeType="EAZ247"></odin:gridSelectColJs>
<odin:gridSelectColJs name="eab014" codeType="EAB014"></odin:gridSelectColJs>

<odin:editgrid property="div_2"  pageSize="150"  bbarId="pageToolBar" isFirstLoadData="false" url="/" title=""  height="400">
<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
		<odin:gridDataCol name="check" />
		<odin:gridDataCol name="aae135" />
		<odin:gridDataCol name="aac003" />
		<odin:gridDataCol name="eac238" />
		<odin:gridDataCol name="aae180" />
		<odin:gridDataCol name="aaz289" />
		<odin:gridDataCol name="eaa226" />
		<odin:gridDataCol name="eac240" />
		<odin:gridDataCol name="eac241" />
		<odin:gridDataCol name="eac242" />
		<odin:gridDataCol name="eac243" />
		<odin:gridDataCol name="eac244" />
		<odin:gridDataCol name="ylgr" />
		<odin:gridDataCol name="ybgr" />
		<odin:gridDataCol name="sygr" />
		<odin:gridDataCol name="ryfl" />
		<odin:gridDataCol name="eac001" />
		<odin:gridDataCol name="aac009" />
		<odin:gridDataCol name="yl" />
		<odin:gridDataCol name="yb" />
		<odin:gridDataCol name="sye" />
		<odin:gridDataCol name="gs" />
		<odin:gridDataCol name="syu" />
		<odin:gridDataCol name="aab001" />
		<odin:gridDataCol name="aab004" />
		<odin:gridDataCol name="eab014" />
		<odin:gridDataCol name="eac158" />
		<odin:gridDataCol name="eac157" />
		<odin:gridDataCol name="eaz247" />
		<odin:gridDataCol name="logaaz157" />
		<odin:gridDataCol name="logaaz001" isLast="true" />
	</odin:gridJsonDataModel>
<odin:gridColumnModel>
  <odin:gridRowNumColumn />
		<ss:gridEditColumn header="���֤����" width="120" dataIndex="aae135"
			editor="text"  p="D"/>
		<ss:gridEditColumn header="����" width="120" dataIndex="aac003"
			editor="text" p="D"/>
		<ss:gridEditColumn header="��������Ա���걨ǰ��" width="120" dataIndex="eac238"
			editor="select" p="D"/>
		<ss:gridEditColumn header="������ƽ������" width="120" dataIndex="aae180"
			editor="number" p="D"/>
		<ss:gridEditColumn header="���ϲα����ͣ��걨ǰ��" width="120" dataIndex="aaz289"
			editor="select"  p="D"/>
		<ss:gridEditColumn header="ҽ���α����ͣ��걨ǰ��" width="120" dataIndex="eaa226"
			editor="select" p="D"/>
		<ss:gridEditColumn header="���Ͻɷѻ���" width="120" dataIndex="eac240"
			editor="number" p="D"/>
		<ss:gridEditColumn header="ҽ���ɷѻ���" width="120" dataIndex="eac241"
			editor="number" p="D"/>
		<ss:gridEditColumn header="ʧҵ�ɷѻ���" width="120" dataIndex="eac242"
			editor="number" p="D"/>
		<ss:gridEditColumn header="���˽ɷѻ���" width="120" dataIndex="eac243"
			editor="number" p="D"/>
		<ss:gridEditColumn header="�����ɷѻ���" width="120" dataIndex="eac244"
			editor="number" p="D"/>
		<ss:gridEditColumn header="���ϸ��˽ɷ�" width="120" dataIndex="ylgr"
			editor="number" p="D"/>
		<ss:gridEditColumn header="ҽ�����˽ɷ�" width="120" dataIndex="ybgr"
			editor="number" p="D"/>
		<ss:gridEditColumn header="ʧҵ���˽ɷ�" width="120" dataIndex="sygr"
			editor="number" p="D"/>
		<ss:gridEditColumn header="��Ա����" width="120" dataIndex="ryfl"
			editor="select"  p="D"/>
		<ss:gridEditColumn header="���˱���" width="120" dataIndex="eac001"
			editor="text" p="D"/>
		<ss:gridEditColumn header="������ϵ" width="120" dataIndex="aac009"
			editor="select" p="D"/>
		<ss:gridEditColumn header="����" width="120" dataIndex="yl"
			editor="number" p="D"/>
		<ss:gridEditColumn header="ҽ��" width="120" dataIndex="yb"
			editor="number" p="D"/>
		<ss:gridEditColumn header="ʧҵ" width="120" dataIndex="sye"
			editor="number" p="D"/>
		<ss:gridEditColumn header="����" width="120" dataIndex="gs"
			editor="number" p="D"/>
		<ss:gridEditColumn header="����" width="120" dataIndex="syu"
			editor="number" p="D"/>
		<ss:gridEditColumn header="��λ����" width="120" dataIndex="aab001"
			editor="text" p="D"/>
		<ss:gridEditColumn header="��λ����" width="120" dataIndex="aab004"
			editor="text" p="D"/>
		<ss:gridEditColumn header="���ܱ�־" width="120" dataIndex="eab014"
			editor="select"  p="D"/>
		<ss:gridEditColumn header="ҽ����Ա���" width="120" dataIndex="eac158"
			editor="select" p="H"/>
		<ss:gridEditColumn header="ҽ����Ա�����" width="120" dataIndex="eac157"
			editor="select" p="H"/>
		<ss:gridEditColumn header="�ͱ���־" width="120" dataIndex="eaz247"
			editor="select" p="H"/>
		<ss:gridEditColumn header="����Ա����" width="120" dataIndex="logaaz157"
			editor="number" p="H"/>
		<ss:gridEditColumn header="��λ����" width="120" dataIndex="logaaz001"
			editor="number"  isLast="true" p="H"/>
			</odin:gridColumnModel>
</odin:editgrid>