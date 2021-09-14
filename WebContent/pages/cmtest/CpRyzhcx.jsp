<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<ss:toolBar property="bar1">
	<odin:fill></odin:fill>
<ss:resetBtn></ss:resetBtn>
</ss:toolBar>
<ss:hlistDiv id="1" cols="4">
	<ss:query property="psquery" p="R" onchange="true"></ss:query>
	<ss:textEdit property="aac003_nonem" label="����"/>
	<ss:numberEdit property="aac001_nonem" label="��Ա���ID" p="H"></ss:numberEdit>
	
</ss:hlistDiv>
<odin:tab id="tab">
   <odin:tabModel>
       <odin:tabItem title="������Ϣ" id="tab1"></odin:tabItem>
       <odin:tabItem title="�½ɷ���Ϣ" id="tab2"></odin:tabItem>
       <odin:tabItem title="�����Ϣ" id="tab3"></odin:tabItem>
       <odin:tabItem title="ҵ������" id="tab4"></odin:tabItem>
       <odin:tabItem title="�������" id="tab5" isLast="true"></odin:tabItem>
   </odin:tabModel>
   <odin:tabCont itemIndex="tab1">
       <ss:hlistDiv id="2" cols="3">
	     <ss:textEdit property="aae135" label="���֤��" p="D" required="ture"/>   
		 <ss:textEdit property="aac003" label="����" p="D"/>
		 <ss:select property="aab301" label="Ͻ��" p="D"/>
		 <ss:select property="aaf015" label="�ֵ�" p="D"/>
		 <ss:select property="aac005" label="����" p="D"/>
		 <ss:select property="eac026" label="��Ա״̬" p="D"/>
		 <ss:textEdit property="aab004" label="��λ����" width="450" colspan="4" p="D"/>
	     <ss:textEdit property="aab001" label="��λ����" p="D"/>
	     <ss:select property="aac004" label="�Ա�" p="D"/>
	     <ss:textEdit property="aic020" label="�ϱ�����" p="D"/>
		 <ss:select property="aac009" label="������ϵ" p="D"/>
		 <ss:dateEdit property="aac006" label="��������" p="D"></ss:dateEdit>
		 <ss:select property="eac066" label="������Ա����" p="D"/>
		 <ss:select property="eac238" label="�����񹤱�־" p="D"/>
		 <ss:select property="eac001" label="���˱���" p="D"/>
		 <ss:numberEdit property="ybsjjfnx" label="ҽ��ʵ�ʽɷ�����" p="D"></ss:numberEdit>
		 <ss:numberEdit property="ybstnx" label="ҽ����ͬ����" p="D"></ss:numberEdit>						
		 <ss:select property="eac158" label="ҽ����Ա����" p="D"/>	
		 <ss:select property="eac157" label="ҽ�������" p="D"/>				
		 <ss:dateEdit property="aac007" label="�μӹ���ʱ��" p="D"></ss:dateEdit>	
		 <ss:textEdit property="eab217" label="����" p="D"/>
	     <ss:textEdit property="eab213" label="ԭ���ϵ�λ����" p="D"/>
		 <ss:textEdit property="aab030" label="��˰����" p="D"/>
		 <ss:textEdit property="aae004" label="��ϵ��" p="D"/>
		 <ss:textEdit property="aae006" label="��ϵ��ַ" colspan="4" width="450" p="D"/>
		 <ss:textEdit property="aae005" label="��ϵ�绰" p="D"/>
		 <ss:textEdit property="aac010" label="������" colspan="4" width="450" p="D"/>						
		 <ss:textEdit property="aae010" label="�����ʺ�" p="D"/>	
		 <ss:select property="eae363" label="��ʱ�˻����" p="D"/>			
		 <ss:select property="aae008" label="ί�л���" p="D"/>					
		 <ss:textEdit property="aae013" label="��ע" p="D"/>
		 <ss:textEdit property="eac101" label="�ֻ�����" p="D"/>		
		 <ss:textEdit property="aaz501" label="�籣������" p="D"/>
		 <ss:textEdit property="aaz502" label="��״̬" p="D"/>			
		 <ss:textEdit property="aaz500" label="��ʶ����" p="D"/>
	  </ss:hlistDiv>
	  
	  <odin:gridSelectColJs name="aae140"  codeType="AAE140" />
	  <odin:editgrid property="div_3" pageSize="150" bbarId="pageToolBar" isFirstLoadData="false" url="/" title="�ɷѻ�����Ϣ" width="780" height="200">
		<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
			<odin:gridDataCol name="aae140" />
			<odin:gridDataCol name="aab001" />
			<odin:gridDataCol name="aac008" />
			<odin:gridDataCol name="aae180" />
			<odin:gridDataCol name="aaa042" />
			<odin:gridDataCol name="aaa041" />
			<odin:gridDataCol name="aaa048" />
			<odin:gridDataCol name="aaa046" />
			<odin:gridDataCol name="aaz289" />
			<odin:gridDataCol name="aaa044" />
			<odin:gridDataCol name="zhzt" />
			<odin:gridDataCol name="aac049" />
			<odin:gridDataCol name="aaz001" isLast="true" />
	    </odin:gridJsonDataModel>
        <odin:gridColumnModel>
        <odin:gridRowNumColumn />
	  		<odin:gridColumn header="����" width="60" dataIndex="aae140" editor="select" edited="true" codeType="AAE140"/>
	    	<odin:gridColumn header="��λ���" width="80" dataIndex="aab001" editor="text"/>
	    	<odin:gridColumn header="�α�״̬" width="91" dataIndex="aac008"  editor="text"/>
	    	<odin:gridColumn header="�ɷѻ���" width="70" dataIndex="aae180" editor="number"/>
	    	<odin:gridColumn header="��λ" width="50" dataIndex="aaa042" editor="number" />
	    	<odin:gridColumn header="����" width="50" dataIndex="aaa041" editor="number" />
	    	<odin:gridColumn header="��" width="50" dataIndex="aaa048" editor="number" />
	    	<odin:gridColumn header="����" width="50" dataIndex="aaa046" editor="number" />
	    	<odin:gridColumn header="���ʱ���" width="70" dataIndex="aaz289" editor="text"/>
	    	<odin:gridColumn header="��������" width="200" dataIndex="aaa044" editor="text"/>
	    	<odin:gridColumn header="�ʻ�״̬" width="80" dataIndex="zhzt" editor="text"/>
	    	<odin:gridColumn header="����ʱ��" width="70" dataIndex="aaa049" editor="date"/>
	    	<odin:gridColumn header="��λ����" width="60" dataIndex="aaz001" editor="number" isLast="true" />
       </odin:gridColumnModel>
      </odin:editgrid>
   </odin:tabCont>
   <odin:tabCont itemIndex="tab2">
       <ss:hlistDiv id="4" cols="6">
             <table>
             <tr>
             <td width="15%">
       		 <ss:select property="aae140" label="����" codeType="AAE140" filter="aaa102 in ('01','02','03','04','05')" onchange="true"/>
       		 </td>
       		 <td width="40%">
       		 <ss:select property="eab204" label="��Ŀ�����־" codeType="EAB204" allAsItem="true" onchange="true"/>
       		 </td>
       		 </tr>
       		 </table>
	   </ss:hlistDiv> 
	  <odin:gridSelectColJs name="aae140"  codeType="AAE140" />
	  <odin:gridSelectColJs name="eab205"  codeType="EAB205" />
	  <odin:gridSelectColJs name="eab206"  codeType="EAB206" />
	  <odin:gridSelectColJs name="eac158"  codeType="EAC158" />
	  <odin:gridSelectColJs name="eac157"  codeType="EAC157" />
	  <odin:gridSelectColJs name="eab204"  codeType="EAB204" />
	  <odin:gridSelectColJs name="aab033"  codeType="AAB033" />
	  <odin:gridSelectColJs name="aae078"  codeType="AAE078" />
	  <odin:gridSelectColJs name="eae249"  codeType="EAE249" />
	  <odin:gridSelectColJs name="eaz247"  codeType="EAZ247" />
	  <odin:editgrid property="div_5" pageSize="150" bbarId="pageToolBar" isFirstLoadData="false" url="/" title="�½ɷ���Ϣ" width="780" height="310">
		<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
			<odin:gridDataCol name="aae140" />
			<odin:gridDataCol name="eae323" />
			<odin:gridDataCol name="eae324" />
			<odin:gridDataCol name="aae079" />
			<odin:gridDataCol name="aae002" />
			<odin:gridDataCol name="aae180" />
			<odin:gridDataCol name="aae058" />
			<odin:gridDataCol name="aae020" />
			<odin:gridDataCol name="aae022" />
			<odin:gridDataCol name="aae026" />
			<odin:gridDataCol name="aae024" />
			<odin:gridDataCol name="eac003" />
			<odin:gridDataCol name="aaz289" />
			<odin:gridDataCol name="eac158" />
			<odin:gridDataCol name="eac157" />
			<odin:gridDataCol name="eab202" />
			<odin:gridDataCol name="eab204" />
			<odin:gridDataCol name="aab033" />
			<odin:gridDataCol name="ead203" />
			<odin:gridDataCol name="eab203" />
			<odin:gridDataCol name="eae252" />
			<odin:gridDataCol name="aae078" />
			<odin:gridDataCol name="eae249" />
			<odin:gridDataCol name="eaz247" />
			<odin:gridDataCol name="aab004" />
			<odin:gridDataCol name="aab001" />
			<odin:gridDataCol name="eab213" />
			<odin:gridDataCol name="gzhr" />
			<odin:gridDataCol name="aae021" />
			<odin:gridDataCol name="aae023" />
			<odin:gridDataCol name="aae029" />
			<odin:gridDataCol name="eaz265" />
			<odin:gridDataCol name="eaz266" isLast="true" />
	    </odin:gridJsonDataModel>
        <odin:gridColumnModel>
        <odin:gridRowNumColumn />
	  		<ss:gridEditColumn header="����" width="80" dataIndex="aae140" editor="select" codeType="AAE140"/>
	    	<ss:gridEditColumn header="Ӧ������" width="80" dataIndex="eae323" editor="number"/>
	    	<ss:gridEditColumn header="Ӧ�ս�ֹ����" width="100" dataIndex="eae324"  editor="number"/>
	    	<ss:gridEditColumn header="��������" width="80" dataIndex="aae079" editor="number"/>
	    	<ss:gridEditColumn header="��������" width="80" dataIndex="aae002" editor="number" />
	    	<ss:gridEditColumn header="��Ŀ�״δ����־��ϸ" width="140" dataIndex="eab205" editor="text" />
	    	<ss:gridEditColumn header="��Ŀ���δ����־��ϸ" width="140" dataIndex="eab206" editor="text" />
	    	<ss:gridEditColumn header="�ɷѻ���" width="80" dataIndex="aae180" editor="number" />
	    	<ss:gridEditColumn header="�ɷ��ܶ�" width="80" dataIndex="aae058" editor="number"/>
	    	<ss:gridEditColumn header="��λ�ɷ�" width="80" dataIndex="aae020" editor="number"/>
	    	<ss:gridEditColumn header="���˽ɷ�" width="80" dataIndex="aae022" editor="number"/>
	    	<ss:gridEditColumn header="�󲡽ɷ�" width="80" dataIndex="aae026" editor="number"/>
	    	<ss:gridEditColumn header="�����ɷ�" width="80" dataIndex="aae024" editor="number"/>
	    	<ss:gridEditColumn header="��������" width="80" dataIndex="eac003" editor="text"/>
	    	<ss:gridEditColumn header="���ʱ���" width="80" dataIndex="aaz289" editor="text"/>
	    	<ss:gridEditColumn header="ҽ�����" width="130" dataIndex="eac158" editor="select"/>
	    	<ss:gridEditColumn header="ҽ�������" width="150" dataIndex="eac157" editor="select"/>
	        <ss:gridEditColumn header="Ӧ������" width="80" dataIndex="eab202" editor="text"/>
	    	<ss:gridEditColumn header="��Ŀ�����־" width="100" dataIndex="eab204" editor="select"/>
	    	<ss:gridEditColumn header="�ɷѷ�ʽ" width="120" dataIndex="aab033" editor="select"/>
	    	<ss:gridEditColumn header="����ʽ" width="80" dataIndex="ead203" editor="text"/>
	    	<ss:gridEditColumn header="Ӧ��������ϸ" width="100" dataIndex="eab203" editor="text"/>
	    	<ss:gridEditColumn header="��������" width="80" dataIndex="eae252" editor="number"/>
	    	<ss:gridEditColumn header="���ʱ�־" width="80" dataIndex="aae078" editor="select"/>
	    	<ss:gridEditColumn header="���ʷ�ʽ" width="80" dataIndex="eae249" editor="select"/>
	    	<ss:gridEditColumn header="�ͱ���־" width="80" dataIndex="eaz247" editor="select"/>
	    	<ss:gridEditColumn header="��λ����" width="180" dataIndex="aab004" editor="text"/>
	    	<ss:gridEditColumn header="��λ����" width="80" dataIndex="aab001" editor="text"/>
	    	<ss:gridEditColumn header="ԭ���ϱ���" width="80" dataIndex="eab213" editor="text"/>
	    	<ss:gridEditColumn header="���ʻ���" width="80" dataIndex="gzhr" editor="text"/>
	    	<ss:gridEditColumn header="ͳ�ﻮ��" width="80" dataIndex="aae021" editor="number"/>
	    	<ss:gridEditColumn header="���˻���" width="80" dataIndex="aae023" editor="number"/>
	    	<ss:gridEditColumn header="��������" width="80" dataIndex="aae029" editor="number"/>
	    	<ss:gridEditColumn header="����ָ��" width="70" dataIndex="eaz265" editor="number"/>
	    	<ss:gridEditColumn header="������Ŀ��ע" width="60" dataIndex="eaz266" editor="text" isLast="true" />
       </odin:gridColumnModel>
      </odin:editgrid>
   </odin:tabCont>
   <odin:tabCont itemIndex="tab3">
       
	  <odin:gridSelectColJs name="eae202"  codeType="EAE202" />
	  <odin:gridSelectColJs name="eae204"  codeType="EAE204" />
	  <odin:gridSelectColJs name="aae140"  codeType="AAE140" />
	  <odin:gridSelectColJs name="aae011"  codeType="AAE011" />
	  <odin:gridSelectColJs name="eae224"  codeType="EAE224" />
	  <odin:editgrid property="div_7" pageSize="150" bbarId="pageToolBar" isFirstLoadData="false" url="/" title="�����Ϣ" width="780" height="210">
		<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
			<odin:gridDataCol name="eae202" />
			<odin:gridDataCol name="eae204" />
			<odin:gridDataCol name="aae140" />
			<odin:gridDataCol name="eae205" />
			<odin:gridDataCol name="aab001" />
			<odin:gridDataCol name="eab213" />
			<odin:gridDataCol name="eae206" />
			<odin:gridDataCol name="eae203" />
			<odin:gridDataCol name="aae011" />
			<odin:gridDataCol name="aae036" />
			<odin:gridDataCol name="aae037" />
			<odin:gridDataCol name="eae224" isLast="true" />
	    </odin:gridJsonDataModel>
        <odin:gridColumnModel>
        <odin:gridRowNumColumn />
	  		<ss:gridEditColumn header="ҵ������" width="110" dataIndex="eae202" editor="select" codeType="EAE202"/>
	    	<ss:gridEditColumn header="�䶯ԭ��" width="120" dataIndex="eae204" editor="select" codeType="EAE204"/>
	    	<ss:gridEditColumn header="����" width="80" dataIndex="aae140"  editor="select" codeType="AAE140"/>
	    	<ss:gridEditColumn header="�䶯����" width="80" dataIndex="eae205" editor="number"/>
	    	<ss:gridEditColumn header="ԭ��λ����" width="80" dataIndex="eab213" editor="text" />
	    	<ss:gridEditColumn header="ԭ���ϵ�λ����" width="110" dataIndex="eab205" editor="text" />
	    	<ss:gridEditColumn header="�䶯��ע" width="80" dataIndex="eae206" editor="text" />
	    	<ss:gridEditColumn header="ҵ��ע" width="80" dataIndex="eae203" editor="text" />
	    	<ss:gridEditColumn header="����Ա" width="80" dataIndex="aae011" editor="select"  codeType="AAE011"/>
	    	<ss:gridEditColumn header="����ʱ��" width="80" dataIndex="aae036" editor="text"/>
	    	<ss:gridEditColumn header="ҵ����" width="80" dataIndex="aae037" editor="number"/>
	    	<ss:gridEditColumn header="������־" width="80" dataIndex="eae224" editor="select"  isLast="true" />
       </odin:gridColumnModel>
      </odin:editgrid>
	  <odin:gridSelectColJs name="eae024"  codeType="EAE024" />
	  <odin:editgrid property="div_8" pageSize="150" bbarId="pageToolBar" isFirstLoadData="false" url="/" title="ҵ�񾭰���Ϣ" width="780" height="210">
		<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
			<odin:gridDataCol name="title" />
			<odin:gridDataCol name="aae002" />
			<odin:gridDataCol name="aae011" />
			<odin:gridDataCol name="aae036" />
			<odin:gridDataCol name="aae013" />
			<odin:gridDataCol name="eae024" />
			<odin:gridDataCol name="eae124" />
			<odin:gridDataCol name="eae125" />
			<odin:gridDataCol name="czcode" />
			<odin:gridDataCol name="czdate" />
			<odin:gridDataCol name="tbgyy" />
			<odin:gridDataCol name="frontver" />
			<odin:gridDataCol name="backver" isLast="true" />
	    </odin:gridJsonDataModel>
        <odin:gridColumnModel>
        <odin:gridRowNumColumn />
	  		<ss:gridEditColumn header="��������" width="110" dataIndex="title" editor="text"/>
	    	<ss:gridEditColumn header="ҵ������" width="70" dataIndex="aae002" editor="number"/>
	    	<ss:gridEditColumn header="������" width="60" dataIndex="aae011"  editor="text"/>
	    	<ss:gridEditColumn header="��������" width="120" dataIndex="aae036" editor="text"/>
	    	<ss:gridEditColumn header="��ע" width="80" dataIndex="aae013" editor="text" />
	    	<ss:gridEditColumn header="���˱�־" width="70" dataIndex="eae024" editor="select" codeType="EAE024"/>
	    	<ss:gridEditColumn header="������" width="60" dataIndex="eae124" editor="text" />
	    	<ss:gridEditColumn header="��������" width="80" dataIndex="eae125" editor="text" />
	    	<ss:gridEditColumn header="�����" width="60" dataIndex="czcode" editor="text"/>
	    	<ss:gridEditColumn header="�������" width="80" dataIndex="czdate" editor="text"/>
	    	<ss:gridEditColumn header="ͨ����ԭ��" width="80" dataIndex="tbgyy" editor="text"/>
	    	<ss:gridEditColumn header="ǰ̨�汾��" width="80" dataIndex="frontver" editor="number"/>
	    	<ss:gridEditColumn header="��̨�汾��" width="80" dataIndex="backver" editor="number"  isLast="true" />
       </odin:gridColumnModel>
      </odin:editgrid>
   </odin:tabCont>
   <odin:tabCont itemIndex="tab4">
	  <odin:gridSelectColJs name="aac155"  codeType="AAC155" />
	  <odin:gridSelectColJs name="aac156"  codeType="AAC156" />
	  <odin:gridSelectColJs name="aae100"  codeType="AAE100" />
	  <odin:editgrid property="div_9" pageSize="150" bbarId="pageToolBar" isFirstLoadData="false" url="/" title="�����Ϣ" width="780" height="355">
		<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
			<odin:gridDataCol name="aac155_z" />
			<odin:gridDataCol name="aac155" />
			<odin:gridDataCol name="aac156" />
			<odin:gridDataCol name="aae100" isLast="true" />
	    </odin:gridJsonDataModel>
        <odin:gridColumnModel>
        <odin:gridRowNumColumn />
	    	<ss:gridEditColumn header="���Ա���" width="120" dataIndex="aac155_z" editor="text" />
	    	<ss:gridEditColumn header="���Ա���ֵ" width="150" dataIndex="aac155" editor="select" codeType="AAC155"/>
	    	<ss:gridEditColumn header="��Ա��ע" width="150" dataIndex="aac156" editor="select"  codeType="AAC156"/>
	    	<ss:gridEditColumn header="��Ч���" width="80" dataIndex="aae100" editor="select" codeType="AAE100" isLast="true"/>
       </odin:gridColumnModel>
      </odin:editgrid>
   </odin:tabCont>
     <odin:tabCont itemIndex="tab5">
	  <odin:gridSelectColJs name="eac202"  codeType="EAC202" />
	  <odin:gridSelectColJs name="eac203"  codeType="EAC203" />
	  <odin:gridSelectColJs name="aae100"  codeType="AAE100" />
	  <odin:editgrid property="div_10" pageSize="150" bbarId="pageToolBar" isFirstLoadData="false" url="/" title="�����Ϣ" width="780" height="355">
		<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
			<odin:gridDataCol name="eac202_z" />
			<odin:gridDataCol name="eac202" />
			<odin:gridDataCol name="eac203" />
			<odin:gridDataCol name="aae100" isLast="true" />
	    </odin:gridJsonDataModel>
        <odin:gridColumnModel>
        <odin:gridRowNumColumn />
    		<ss:gridEditColumn header="��������" width="120" dataIndex="eac202_z" editor="text" />
	    	<ss:gridEditColumn header="��������" width="150" dataIndex="eac202" editor="select" codeType="EAC202"/>
	    	<ss:gridEditColumn header="����ֵ" width="150" dataIndex="eac203" editor="select"  codeType="EAC203"/>
	    	<ss:gridEditColumn header="��Ч���" width="80" dataIndex="aae100" editor="select" codeType="AAE100" isLast="true"/>
       </odin:gridColumnModel>
      </odin:editgrid>
   </odin:tabCont>
</odin:tab>