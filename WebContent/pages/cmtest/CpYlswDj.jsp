<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<ss:toolBar property="bar1">
	<odin:fill></odin:fill>
<ss:doSaveBtn></ss:doSaveBtn>
<ss:resetBtn></ss:resetBtn>

</ss:toolBar>
<ss:hlistDiv id="1" cols="6">
	<ss:query property="psidquery" p="R" onchange="true"></ss:query>
	<ss:dateEdit property="alc040" label="����ʱ��" p="R" format="Y-m-d"/>
	<ss:select property="eae204" label="����ԭ��" p="R" codeType="EAE204" filter="aaa102 in ('00211001','00211005')"/>
	<ss:numberEdit property="aaz157_nonem" label="aaz157" p="H"></ss:numberEdit>
</ss:hlistDiv>
	  <odin:gridSelectColJs name="eab216"  codeType="EAB216" />
	  <odin:gridSelectColJs name="aac004"  codeType="AAC004" />
	  <odin:gridSelectColJs name="aac005"  codeType="AAC005" />
	  <odin:gridSelectColJs name="eac158"  codeType="EAC158" />
	  <odin:gridSelectColJs name="eac157"  codeType="EAC157" />
	  <odin:editgrid property="div_2" pageSize="150" bbarId="pageToolBar" isFirstLoadData="false" url="/" title="�ɷѻ�����Ϣ" width="780" height="150">
		<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
			<odin:gridDataCol name="info" />
			<odin:gridDataCol name="aae135" />
			<odin:gridDataCol name="aac003" />
			<odin:gridDataCol name="aac004" />
			<odin:gridDataCol name="aac006" />
			<odin:gridDataCol name="aac005" />
			<odin:gridDataCol name="eab216" />
			<odin:gridDataCol name="eac001" />
			<odin:gridDataCol name="eac158" />
			<odin:gridDataCol name="eac157" />
			<odin:gridDataCol name="aaz157" isLast="true" />
	    </odin:gridJsonDataModel>
        <odin:gridColumnModel>
        <odin:gridRowNumColumn />
	  		<ss:gridEditColumn header="���ѡ��" width="80" dataIndex="info" editor="text" renderer="renderClick"/>
	    	<ss:gridEditColumn header="���֤��" width="130" dataIndex="aae135" editor="text"/>
	    	<ss:gridEditColumn header="����" width="80" dataIndex="aac003"  editor="text"/>
	    	<ss:gridEditColumn header="�Ա�" width="70" dataIndex="aac004" editor="select" codeType="AAC004"/>
	    	<ss:gridEditColumn header="��������" width="80" dataIndex="aac006" editor="text"/>
	    	<ss:gridEditColumn header="����" width="50" dataIndex="aac005" editor="select" codeType="AAC005"/>
	    	<ss:gridEditColumn header="���״̬" width="80" dataIndex="eab216" editor="select" codeType="EAB216"/>
	    	<ss:gridEditColumn header="���˱���" width="100" dataIndex="eac001" p="D" editor="text" renderer="renderClick"/>
	    	<ss:gridEditColumn header="ҽ�����" width="150" dataIndex="eac158" editor="select" codeType="EAC158"/>
	    	<ss:gridEditColumn header="ҽ�������" width="200" dataIndex="eac157" editor="select" codeType="EAC157"/>
	    	<ss:gridEditColumn header="aaz157" width="60" dataIndex="aaz157" editor="number" p="H" isLast="true" />
       </odin:gridColumnModel>
	   </odin:editgrid>
<odin:tab id="tab">
   <odin:tabModel>
       <odin:tabItem title="������Ϣ" id="tab1"></odin:tabItem>
       <odin:tabItem title="�½ɷ���Ϣ" id="tab2"></odin:tabItem>
       <odin:tabItem title="�����Ϣ" id="tab3" isLast="true"></odin:tabItem>
   </odin:tabModel>
   <odin:tabCont itemIndex="tab1" className="tab">
       <ss:hlistDiv id="3" cols="8">
	     <ss:textEdit property="aae135" label="���֤��" p="D"/>   
		 <ss:textEdit property="aac003" label="����" p="D"/>
		 <ss:select property="aac005" label="����" p="D" codeType="AAC005"/>
		 <ss:select property="eac026" label="��Ա״̬" p="D" codeType="EAC026"/>
		 <ss:select property="aab301" label="Ͻ��" codeType="AAB301"/>
		 <ss:select property="aac004" label="�Ա�" p="D" codeType="AAC004"/>
		 <ss:textEdit property="aab004" label="��λ����" width="435" colspan="4" p="D"/>
		 <ss:select property="aaf015" label="�ֵ�" p="D" codeType="AAF015"/>
		 <ss:textEdit property="aic020" label="�ϱ�����" p="D"/>
	     <ss:select property="eac216" label="��Ա״̬" p="D" codeType="EAC216"/>
	     <ss:select property="eac070" label="��ǰ��ҵ״̬" p="D" codeType="EAC070"/>
	     <ss:select property="aac009" label="������ϵ" p="D" codeType="AAC009"/>
	     <ss:dateEdit property="aac006" label="��������" p="D"></ss:dateEdit>
	     <ss:select property="eac001" label="���˱���" p="D" codeType="EAC001"/> 
	     <ss:textEdit property="z_aac156_aac155_010010" label="������Ա��־" p="D"/>
	     <ss:textEdit property="z_aac156_aac155_000020"  label="�����񹤱�־" p="D"/>
	     <ss:select property="eac066" label="������Ա����" codeType="EAC006" p="D"/>
	     <ss:textEdit property="z_aac156_aac155_010040"  label="��ͬȫ������" p="D"/>
	     <ss:textEdit property="aab030" label="��˰����" p="D"/>
	     <ss:textEdit property="eab217" label="����" p="D"/>
	     <ss:textEdit property="ybbfidyears" label="ҽ����ͬ����" p="D"></ss:textEdit>	
	     <ss:textEdit property="ybsjjfnx" label="ҽ��ʵ������(����ɻ���)" p="D"></ss:textEdit>
	     <ss:select property="eac158" label="ҽ����Ա����" p="D" codeType="EAC158"/>	
		 <ss:select property="eac157" label="ҽ�������" p="D" codeType="EAC157"/>			
		 <ss:dateEdit property="aac007" label="�μӹ���ʱ��" p="D" format="Y-m-d"></ss:dateEdit>	
	     <ss:textEdit property="eab213" label="ԭ���ϵ�λ����" p="D"/>
	   </ss:hlistDiv>
		<odin:tab id="tabd">
   		<odin:tabModel>
       	<odin:tabItem title="������Ϣ" id="tabd1"></odin:tabItem>
       	<odin:tabItem title="������Ա" id="tabd2" isLast="true"></odin:tabItem>
   	   </odin:tabModel>
      <odin:tabCont itemIndex="tabd1" className="tab">
	    <odin:gridSelectColJs name="aae140"  codeType="AAE140" />
	    <odin:gridSelectColJs name="aac008"  codeType="AAC008" />
	    <odin:editgrid property="div_4" pageSize="150" bbarId="pageToolBar" isFirstLoadData="false" url="/" title="������Ϣ" width="780" height="200">
		<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
			<odin:gridDataCol name="aae140" />
			<odin:gridDataCol name="aae180" />
			<odin:gridDataCol name="aaa042" />
			<odin:gridDataCol name="aaa041" />
			<odin:gridDataCol name="aaa048" />
			<odin:gridDataCol name="aaa046" />
			<odin:gridDataCol name="aaz289" />
			<odin:gridDataCol name="aaa044" />
			<odin:gridDataCol name="aac008" />
			<odin:gridDataCol name="aae206" isLast="true"/>
	    </odin:gridJsonDataModel>
        <odin:gridColumnModel>
        <odin:gridRowNumColumn />
	  		<odin:gridColumn header="����" width="60" dataIndex="aae140" editor="select" edited="true" codeType="AAE140"/>
	    	<odin:gridColumn header="�ɷѻ���" width="80" dataIndex="aae180" editor="number"/>
	    	<odin:gridColumn header="��λ" width="60" dataIndex="aaa042" editor="number" />
	    	<odin:gridColumn header="����" width="60" dataIndex="aaa041" editor="number" />
	    	<odin:gridColumn header="��" width="60" dataIndex="aaa048" editor="number" />
	    	<odin:gridColumn header="����" width="60" dataIndex="aaa046" editor="number" />
	    	<odin:gridColumn header="���ʱ���" width="80" dataIndex="aaz289" editor="text"/>
	    	<odin:gridColumn header="��������" width="280" dataIndex="aaa044" editor="text"/>
	    	<odin:gridColumn header="���ֲα�״̬" width="91" dataIndex="aac008"  editor="select" codeType="AAC008"/>
	    	<odin:gridColumn header="����ʱ��" width="70" dataIndex="aae206" editor="date" isLast="true" format="Y-m-d"/>
       </odin:gridColumnModel>
      </odin:editgrid>
    </odin:tabCont>
    <odin:tabCont itemIndex="tabd2">
	  <odin:gridSelectColJs name="aac155"  codeType="AAC155" />
	  <odin:editgrid property="div_5" pageSize="150" bbarId="pageToolBar" isFirstLoadData="false" url="/" title="������Ա" width="780" height="200">
		<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
			<odin:gridDataCol name="aac155" />
			<odin:gridDataCol name="aac156" isLast="true"/>
	    </odin:gridJsonDataModel>
        <odin:gridColumnModel>
        <odin:gridRowNumColumn />
	  		<odin:gridColumn header="������Ա" width="160" dataIndex="aac155" editor="text" edited="true" codeType="AAC155"/>
	    	<odin:gridColumn header="��ע" width="450" dataIndex="aac156" editor="text" isLast="true"/>
       </odin:gridColumnModel>
      </odin:editgrid>
   </odin:tabCont>
   </odin:tab>
	   
   </odin:tabCont>
   <odin:tabCont itemIndex="tab2">
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
	  <odin:editgrid property="div_6" pageSize="150" bbarId="pageToolBar" isFirstLoadData="false" url="/" title="�½ɷ���Ϣ" width="780" height="310">
		<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
			<odin:gridDataCol name="aae140" />
			<odin:gridDataCol name="eae323" />
			<odin:gridDataCol name="eae324" />
			<odin:gridDataCol name="aae079" />
			<odin:gridDataCol name="aae002" />
			<odin:gridDataCol name="aae180" />
			<odin:gridDataCol name="aae058" />
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
			<odin:gridDataCol name="eab213" isLast="true"/>
	    </odin:gridJsonDataModel>
        <odin:gridColumnModel>
        <odin:gridRowNumColumn />
	  		<ss:gridEditColumn header="����" width="80" dataIndex="aae140" editor="select" codeType="AAE140"/>
	    	<ss:gridEditColumn header="Ӧ������" width="80" dataIndex="eae323" editor="number"/>
	    	<ss:gridEditColumn header="Ӧ�ս�ֹ����" width="100" dataIndex="eae324"  editor="number"/>
	    	<ss:gridEditColumn header="��������" width="80" dataIndex="aae079" editor="number"/>
	    	<ss:gridEditColumn header="��������" width="80" dataIndex="aae002" editor="number" />
	    	<ss:gridEditColumn header="�ɷѻ���" width="80" dataIndex="aae180" editor="number" />
	    	<ss:gridEditColumn header="�ɷ��ܶ�" width="80" dataIndex="aae058" editor="number"/>
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
	    	<ss:gridEditColumn header="��Ŀ�״δ����־��ϸ" width="140" dataIndex="eab205" editor="text" />
	    	<ss:gridEditColumn header="��Ŀ���δ����־��ϸ" width="140" dataIndex="eab206" editor="text" />
	    	<ss:gridEditColumn header="��λ����" width="180" dataIndex="aab004" editor="text"/>
	    	<ss:gridEditColumn header="��λ����" width="80" dataIndex="aab001" editor="text"/>
	    	<ss:gridEditColumn header="ԭ���ϱ���" width="80" dataIndex="eab213" editor="text" isLast="true"/>
       </odin:gridColumnModel>
      </odin:editgrid>
	  <odin:editgrid property="div_7" pageSize="150" bbarId="pageToolBar" isFirstLoadData="false" url="/" title="�½ɷ���Ϣ" width="780" height="310">
		<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
			<odin:gridDataCol name="aae002" />
			<odin:gridDataCol name="aae079" />
			<odin:gridDataCol name="eac003" />
			<odin:gridDataCol name="eaz265" />
			<odin:gridDataCol name="aae058" />
			<odin:gridDataCol name="eab203" />
			<odin:gridDataCol name="aab001" />
			<odin:gridDataCol name="eaz266" isLast="true" />
	    </odin:gridJsonDataModel>
        <odin:gridColumnModel>
        <odin:gridRowNumColumn />
	    	<ss:gridEditColumn header="��������" width="80" dataIndex="aae002" editor="number"/>
	    	<ss:gridEditColumn header="��������" width="80" dataIndex="aae079" editor="number"/>
	    	<ss:gridEditColumn header="�ɷ�����" width="80" dataIndex="eac003" editor="number"/>
	    	<ss:gridEditColumn header="����ָ��" width="80" dataIndex="eaz265" editor="number"/>
	    	<ss:gridEditColumn header="�������" width="80" dataIndex="aae058" editor="number"/>
	    	<ss:gridEditColumn header="����ԭ��" width="180" dataIndex="eab203" editor="text" />
	    	<ss:gridEditColumn header="��λ����" width="80" dataIndex="aab001" editor="text"/>
	    	<ss:gridEditColumn header="��ע" width="180" dataIndex="eaz266" editor="text" isLast="true" />
       </odin:gridColumnModel>
      </odin:editgrid>
   </odin:tabCont>
   <odin:tabCont itemIndex="tab3">
	  <odin:gridSelectColJs name="eae202"  codeType="EAE202" />
	  <odin:gridSelectColJs name="eae204"  codeType="EAE204" />
	  <odin:gridSelectColJs name="aae140"  codeType="AAE140" />
	  <odin:gridSelectColJs name="aae011"  codeType="AAE011" />
	  <odin:gridSelectColJs name="eae224"  codeType="EAE224" />
	  <odin:editgrid property="div_8" pageSize="150" bbarId="pageToolBar" isFirstLoadData="false" url="/" title="�����Ϣ" width="780" height="210">
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
	  <odin:gridSelectColJs name="rbflag"  codeType="EAE024" />
	  <odin:gridSelectColJs name="aab301"  codeType="AAB301" />
	  <odin:editgrid property="div_9" pageSize="150" bbarId="pageToolBar" isFirstLoadData="false" url="/" title="�����Ϣ" width="780" height="210">
		<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
			<odin:gridDataCol name="title" />
		    <odin:gridDataCol name="aae011" />
			<odin:gridDataCol name="aab301" />
			<odin:gridDataCol name="aae036" />
			<odin:gridDataCol name="aae002" />
			<odin:gridDataCol name="aae013" />
			<odin:gridDataCol name="rbflag" />
			<odin:gridDataCol name="eae124" />
			<odin:gridDataCol name="eae125" />
			<odin:gridDataCol name="frontver" />
			<odin:gridDataCol name="backver" isLast="true" />
	    </odin:gridJsonDataModel>
        <odin:gridColumnModel>
        <odin:gridRowNumColumn />
	  		<ss:gridEditColumn header="��������" width="100" dataIndex="title" editor="text"/>
	    	<ss:gridEditColumn header="����Ա" width="100" dataIndex="aae011"  editor="text"/>
	    	<ss:gridEditColumn header="Ͻ��" width="80" dataIndex="aab301"  editor="select" codeType="AAB301"/>
	    	<ss:gridEditColumn header="����ʱ��" width="120" dataIndex="aae036" editor="text"/>
	    	<ss:gridEditColumn header="ҵ��������" width="100" dataIndex="aae002" editor="number"/>
	    	<ss:gridEditColumn header="��ע" width="80" dataIndex="aae013" editor="text" />
	    	<ss:gridEditColumn header="���˱�־" width="70" dataIndex="rbflag" editor="select" codeType="EAE024"/>
	    	<ss:gridEditColumn header="������" width="60" dataIndex="eae124" editor="text" />
	    	<ss:gridEditColumn header="��������" width="80" dataIndex="eae125" editor="text" />
	    	<ss:gridEditColumn header="ǰ̨�汾" width="80" dataIndex="frontver" editor="text"/>
	    	<ss:gridEditColumn header="��̨�汾" width="80" dataIndex="backver" editor="text"  isLast="true" />
       </odin:gridColumnModel>
      </odin:editgrid>
   </odin:tabCont>
 </odin:tab>
 
<script type="text/javascript">

function onClickTab(tabObj,item){
    var id = item.id;
}
</script>
 
    