<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<ss:toolBar property="bar1">
	<odin:fill></odin:fill>
<ss:doSaveBtn></ss:doSaveBtn>
<ss:resetBtn></ss:resetBtn>
</ss:toolBar>
<odin:groupBox title="��Ա������Ϣ" property="gb1">
  <ss:hlistDiv id="1" cols="6">
	<ss:query property="psquery" p="R"></ss:query>
	<ss:textEdit property="aac003" label="����" p="D"/>
	<ss:select property="aac004" label="�Ա�" p="D"></ss:select>
	<ss:textEdit property="eab217" label="����" p="D"/>
	<ss:select property="aac013" label="��Ա����" p="E"></ss:select>
	<ss:textEdit property="aab001" label="��λ����" p="D"/>
	<ss:textEdit property="aab004" label="��λ����" p="D"/>
	<ss:dateEdit property="aac007" label="�μӹ���ʱ��" p="P0/P1:H,P2:D"/>
	<ss:dateEdit property="aac049" label="�����״βα�ʱ��"/>
	<ss:select property="aac009" label="��������" p="R"></ss:select>
	<ss:select property="eae363" label="��ʱ�˻���־" p="R"></ss:select>
	<ss:textEdit property="aac010" label="������"/>
	<ss:numberEdit property="aaz001" label="��λ����" p="H"/>
	<ss:numberEdit property="aac001" label="���id" p="H"/>
	<ss:dateEdit property="aac006" label="��������" p="H"/>
	<ss:textEdit property="aae135" label="��ᱣ�Ϻ���" p="H"/>
  </ss:hlistDiv>
</odin:groupBox>
<odin:groupBox title="�Է���Ϣ" property="gb2">
  <ss:hlistDiv id="2" cols="6">
	<ss:select property="eab208" label="ת������" p="D"></ss:select>
	<ss:dateEdit property="aae035" label="ת������" p="E"/>
	<ss:select property="aaa020" label="�Է���ᱣ�ջ���" p="R" onchange="true"></ss:select>
	<ss:textEdit property="eae262" label="�Է���λ" p="E"/>
	<ss:textEdit property="eae261" label="�Է���ַ" p="E"/>
	<ss:textEdit property="eae260" label="�Է��ʱ�" p="E"/>
	<ss:textEdit property="eae258" label="�Է���������" p="E"/>
	<ss:textEdit property="eae259" label="�Է��ʺ�" p="E"/>
  </ss:hlistDiv>
</odin:groupBox>

<odin:groupBox title="ת�ƻ�����Ϣ" property="gb3">
  <ss:module param="P0">
  <ss:hlistDiv id="3" cols="6">
	<ss:textEdit property="aab305" label="�״βα���ʵ�и��˽ɷ�ʱ��" p="E" />
	<ss:textEdit property="aac049_2" label="�����״νɷ�ʱ��" p="E" />
	<ss:textEdit property="aac032" label="���������˻�ʱ��" p="E" />
	<ss:dateEdit property="eaz293" label="���ؽɷ���ʼʱ��" p="E" />
	<ss:dateEdit property="eaz294" label="���ؽɷ���ֹʱ��" p="E" />
  </ss:hlistDiv>
  </ss:module>
  <ss:module param="P1">
  <ss:hlistDiv id="3" cols="6">
	<ss:textEdit property="aab305" label="�״βα���ʵ�и��˽ɷ�ʱ��" p="E" />
	<ss:textEdit property="aac049_2" label="�����״νɷ�ʱ��" p="E" />
	<ss:textEdit property="aac032" label="���������˻�ʱ��" p="E" />
	<ss:dateEdit property="eaz293" label="���ؽɷ���ʼʱ��" p="E" />
	<ss:dateEdit property="eaz294" label="���ؽɷ���ֹʱ��" p="E" />
  </ss:hlistDiv>
  </ss:module>
</odin:groupBox>
<odin:groupBox title="ת���˻���Ϣ" property="gb4">
  <ss:module param="P0">
  <ss:hlistDiv id="4" cols="4">
	<ss:numberEdit property="grjf98" label="��1998��1��1��ǰ�˻����˽ɷ��ۼƴ���" />
	<ss:numberEdit property="aypsacct97" label="���У���97��ĩ�����ۼƱ�Ϣ��"/>
	<ss:numberEdit property="kj98" label="98��1��4�¿ۼ��"/>
	<ss:numberEdit property="snmto98" label="��1998��1��1������ת����ĩ�����˻��ۼƴ���"/>
	<ss:numberEdit property="zsnmze" label="���У������˻��ۼƶ�(��ת������Ȼ��ĩ)��"/>
	<ss:numberEdit property="ayacct97" label="97��ȸ����˻��ۼƶ"/>
	<ss:numberEdit property="rewage7" label="98��1��4�½ɷѻ����͡�7���ı�Ϣ�ͣ�"/>
	<ss:numberEdit property="aypsacct9712" label="97��ȸ��˽ɷѱ�Ϣ��"/>
	<ss:numberEdit property="zyzjgrln" label="2006��5�º󲹽�1998��1����2005��12�µ����Ӷ���겹�ɣ�3%��"/>
	<ss:numberEdit property="zyzjeyznzb" label="۴��ũְ���˻����Ӷ�1%��"/>
	<ss:numberEdit property="zykj" label="2006��1��4�¿ۼ��"/>
	<ss:numberEdit property="dnzhje" label="�۵�ת�����������˻������"/>
	<ss:numberEdit property="zyzjgrdn" label="���У�2006��5�º󲹽�1998��1����2005��12�µ����Ӷ���겹�ɣ�3%��"/>
	<ss:numberEdit property="gzje" label="�ܸ����˻�����ת�ƶ1��2��3����"/>
	<ss:numberEdit property="tcjjze" label="��ͳ�����ת�ƶ"/>
	<ss:numberEdit property="zyze" label="��ת�ƻ����ܶ4��5����"/>
	<ss:numberEdit property="cmonth" label="98��1-4�½ɷ�����" p="H"/>  
	<ss:numberEdit property="crewage" label="���нɷѻ���" p="H"/>
	<ss:numberEdit property="tcjj" label="ͳ�����" p="H"/>
	<ss:numberEdit property="payidx" label="��ָ����" p="H"/>
	<ss:numberEdit property="cymons" label="���б���ɷ�����" p="H"/>
	<ss:numberEdit property="acyears" label="ʵ�ʽɷ����޺�" p="H"/>
  </ss:hlistDiv>
  </ss:module>
  <ss:module param="P1">
  <ss:hlistDiv id="4" cols="4">
	<ss:numberEdit property="cmonth" label="98��1-4�½ɷ�����" p="E" minValue="0" maxValue="4"/>
	<ss:numberEdit property="crewage" label="���нɷѻ���" p="E"/>
	<ss:dateEdit property="eae256" label="��ֹ�ɷ�ʱ��" p="E"/>
	<ss:numberEdit property="payidx" label="��ָ����"/>
	<ss:numberEdit property="bfidyears" label="��ͬ�ɷ�����"/>
	<ss:numberEdit property="acyears" label="ʵ�ʽɷ����޺�"/>
	<ss:numberEdit property="cymons" label="���б���ɷ�����"/>
	<ss:numberEdit property="ljyear" label="�ۼƽɷ�����"/>
	<ss:numberEdit property="wlwgzs" label="������ָ��"/>
	<ss:numberEdit property="wlwgys" label="����������"/>
  </ss:hlistDiv>
  <odin:editgrid property="div_5" pageSize="150" bbarId="pageToolBar" isFirstLoadData="false" url="/" title="�ɷѻ�����Ϣ" width="780" height="200">
		<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
			<odin:gridDataCol name="bt" />
			<odin:gridDataCol name="lyprin" />
			<odin:gridDataCol name="lyins" />
			<odin:gridDataCol name="cyprin" />
			<odin:gridDataCol name="cyins" isLast="true" />
	    </odin:gridJsonDataModel>
        <odin:gridColumnModel>
        <odin:gridRowNumColumn />
	  		<odin:gridColumn header="" width="60" dataIndex="bt" editor="number" />
	    	<odin:gridColumn header="����ֹ�ۼƴ����(1)" width="80" dataIndex="lyprin" editor="number"/>
	    	<odin:gridColumn header="���걾������Ϣ(2)" width="91" dataIndex="lyins"  editor="number"/>
	    	<odin:gridColumn header="������ʶ�(3)" width="70" dataIndex="cyprin" editor="number"/>
	    	<odin:gridColumn header="���걾������Ϣ(4)" width="60" dataIndex="cyins" editor="number" isLast="true" />
       </odin:gridColumnModel>
</odin:editgrid>
  <ss:hlistDiv id="6" cols="4">
	<ss:numberEdit property="zyhj" label="ת�ƶ�ϼ�(1+2+3)" p="E"/>
	<ss:numberEdit property="jzehj" label="���ʶ�ת�ƺϼ�(1+2+3+4)"/>
	<ss:numberEdit property="jzehjps" label="���и���"/>
	<ss:numberEdit property="zykj" label="�ۼ���"/>
  </ss:hlistDiv>
  </ss:module>
  <ss:module param="P2">
  <ss:hlistDiv id="4" cols="4">
	<ss:numberEdit property="cmonth" label="98��1-4�½ɷ�����" p="E" minValue="0" maxValue="4"/>
	<ss:numberEdit property="crewage" label="���нɷѻ���" p="E"/>
	<ss:dateEdit property="eae256" label="��ֹ�ɷ�ʱ��" p="E"/>
	<ss:numberEdit property="payidx" label="��ָ����"/>
	<ss:numberEdit property="bfidyears" label="��ͬ�ɷ�����"/>
	<ss:numberEdit property="acyears" label="ʵ�ʽɷ����޺�"/>
	<ss:numberEdit property="cymons" label="���б���ɷ�����"/>
	<ss:numberEdit property="ljyear" label="�ۼƽɷ�����"/>
	<ss:numberEdit property="wlwgzs" label="������ָ��"/>
	<ss:numberEdit property="wlwgys" label="����������"/>
  </ss:hlistDiv>
  <odin:editgrid property="div_5" pageSize="150" bbarId="pageToolBar" isFirstLoadData="false" url="/" title="�ɷѻ�����Ϣ" width="780" height="200">
		<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
			<odin:gridDataCol name="bt" />
			<odin:gridDataCol name="lyprin" />
			<odin:gridDataCol name="lyins" />
			<odin:gridDataCol name="cyprin" />
			<odin:gridDataCol name="cyins" isLast="true" />
	    </odin:gridJsonDataModel>
        <odin:gridColumnModel>
        <odin:gridRowNumColumn />
	  		<odin:gridColumn header="" width="60" dataIndex="bt" editor="number" />
	    	<odin:gridColumn header="����ֹ�ۼƴ����(1)" width="80" dataIndex="lyprin" editor="number"/>
	    	<odin:gridColumn header="���걾������Ϣ(2)" width="91" dataIndex="lyins"  editor="number"/>
	    	<odin:gridColumn header="������ʶ�(3)" width="70" dataIndex="cyprin" editor="number"/>
	    	<odin:gridColumn header="���걾������Ϣ(4)" width="60" dataIndex="cyins" editor="number" isLast="true" />
       </odin:gridColumnModel>
</odin:editgrid>
  <ss:hlistDiv id="6" cols="4">
	<ss:numberEdit property="zyhj" label="ת�ƶ�ϼ�(1+2+3)" p="E"/>
	<ss:numberEdit property="jzehj" label="���ʶ�ת�ƺϼ�(1+2+3+4)"/>
	<ss:numberEdit property="jzehjps" label="���и���"/>
	<ss:numberEdit property="zykj" label="�ۼ���"/>
  </ss:hlistDiv>
  </ss:module>
</odin:groupBox>



	