<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<ss:toolBar property="bar1">
	<odin:fill></odin:fill>
	<odin:opLogButtonForToolBar></odin:opLogButtonForToolBar>
	<ss:doClickBtn icon="images/add.gif" handlerName="gdata" text="�Զ�������ϸ"/>
	<ss:doClickBtn icon="images/add.gif" handlerName="docalc" text="������������ϸ"/>
	<ss:doSaveBtn></ss:doSaveBtn>
    <ss:resetBtn></ss:resetBtn>
</ss:toolBar>
<ss:hlistDiv id="div_1" cols="8">
	<ss:query property="psquery" onchange="true"></ss:query>
	<ss:textEdit property="aac003" label="����" p="D"></ss:textEdit>
	<ss:dateEdit property="aac049" label="�״βα�ʱ��" p="D"></ss:dateEdit>
	<ss:select property="eac070" label="��Ա״̬" p="D" codeType="EAC070"></ss:select>
	
	<ss:select property="aac008" label="�α�״̬" p="D" codeType="AAC008"></ss:select>
	<ss:textEdit property="aab001" label="��λ����" p="D"></ss:textEdit>
	<ss:textEdit property="aab004" label="��λ����" colspan="4" width="260" p="D"></ss:textEdit>
	<ss:numberEdit property="curRow" label="��ǰѡ�е������" p="H"></ss:numberEdit>
	<ss:numberEdit property="stym" label="��ǰѡ����ȵĿ�ʼ����" p="H"></ss:numberEdit>
	<ss:numberEdit property="edym" label="��ǰѡ����ȵĽ�������" p="H"></ss:numberEdit>
	<ss:numberEdit property="spgz" label="��ǰѡ����ȵ���ƽ����" p="H"></ss:numberEdit>
	<ss:numberEdit property="aaz157" p="H"></ss:numberEdit>
</ss:hlistDiv>
<odin:tab id="tab">
  <odin:tabModel>
       <odin:tabItem title="����˻���Ϣ" id="tab1"></odin:tabItem>
       <odin:tabItem title="�����ֵ���ϸ�˻�" id="tab2"></odin:tabItem>
       <odin:tabItem title="���ֵ���ϸ��Ŀ" id="tab3"></odin:tabItem>
       <odin:tabItem title="��������" id="tab4" isLast="true"></odin:tabItem>
   </odin:tabModel>
<odin:tabCont itemIndex="tab1" className="tab">
<odin:editgrid property="div_2" pageSize="150"  bbarId="pageToolBar" isFirstLoadData="false" url="/" height="150" autoFill="false">
<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
			<odin:gridDataCol name="info" />
			<odin:gridDataCol name="aae001" />
			<odin:gridDataCol name="aae140" />
			<odin:gridDataCol name="eaz247" />
			<odin:gridDataCol name="eae349" />
			<odin:gridDataCol name="aae002" />
			<odin:gridDataCol name="cymonths" />
			<odin:gridDataCol name="cyremonths" />
			<odin:gridDataCol name="cypayidx" />
			<odin:gridDataCol name="cyrepayidx" />
			<odin:gridDataCol name="aae180" />
			<odin:gridDataCol name="cywage" />
			<odin:gridDataCol name="cyrewage" />
			<odin:gridDataCol name="cycpprin" />
			<odin:gridDataCol name="cypsprin" />
			<odin:gridDataCol name="cyavprin" />
			<odin:gridDataCol name="cyrecpprin" />
			<odin:gridDataCol name="cyrepsprin" />
			<odin:gridDataCol name="cyreavprin" />
			<odin:gridDataCol name="eae265" isLast="true" />
	    </odin:gridJsonDataModel>
        <odin:gridColumnModel>
        <odin:gridRowNumColumn />
            <odin:gridColumn header="ѡ��" width="80" dataIndex="info" editor="checkbox" edited="true" checkBoxClick="radow.cm.doGridCheck"/>
	  		<ss:gridEditColumn header="���" width="50" dataIndex="aae001" editor="number" />
	    	<ss:gridEditColumn header="����" width="80" dataIndex="aae140" editor="select" codeType="AAE140"/>
	    	<ss:gridEditColumn header="��Ŀ��־" width="80" dataIndex="eaz247"  editor="select" codeType="EAZ247"/>
	    	<ss:gridEditColumn header="��ֱ��" width="80" dataIndex="eae349" editor="select" codeType="EAE349"/>
	    	<ss:gridEditColumn header="��������" width="80" dataIndex="aae002" editor="number" />
	    	<ss:gridEditColumn header="��������" width="80" dataIndex="cymonths" editor="number"/>
	    	<ss:gridEditColumn header="��������" width="80" dataIndex="cyremonths" editor="number"/>
	    	<ss:gridEditColumn header="����ָ��" width="80" dataIndex="cypayidx" editor="number" decimalPrecision="4"/>
	    	<ss:gridEditColumn header="����ָ��" width="80" dataIndex="cyrepayidx" editor="number" decimalPrecision="4"/>
	    	<ss:gridEditColumn header="�ɷѻ���" width="80" dataIndex="aae180" editor="number" />
	    	<ss:gridEditColumn header="�ɷѻ�����" width="80" dataIndex="cywage" editor="number" />
	    	<ss:gridEditColumn header="���ɻ�����" width="80" dataIndex="cyrewage" editor="number" />
	    	<ss:gridEditColumn header="��λ���ֱ���" width="100" dataIndex="cycpprin" editor="number" />
	    	<ss:gridEditColumn header="���˲��ֱ���" width="100" dataIndex="cypsprin" editor="number" />
	    	<ss:gridEditColumn header="�绮����" width="100" dataIndex="cyavprin" editor="number" />
	    	<ss:gridEditColumn header="���ɵ�λ���ֱ���" width="120" dataIndex="cyrecpprin" editor="number"/>
	    	<ss:gridEditColumn header="���ɸ��˲��ֱ���" width="120" dataIndex="cyrepsprin" editor="number" />
	    	<ss:gridEditColumn header="�����绮���ֱ���" width="120" dataIndex="cyreavprin" editor="number"/>
	    	<ss:gridEditColumn header="��Ϣ��־" width="80" dataIndex="eae265" editor="select" codeType="EAE265" isLast="true"/>
       </odin:gridColumnModel>
</odin:editgrid>
</odin:tabCont>
<odin:tabCont itemIndex="tab2">
<odin:gridSelectColJs name="eab202"  codeType="EAB202"/>
<odin:gridSelectColJs name="eab204"  codeType="EAB204"/>
<odin:gridSelectColJs name="eaz247"  codeType="EAZ247"/>
<odin:gridSelectColJs name="aaz289"  codeType="AAZ289"/>
<odin:editgrid property="div_3" pageSize="150" bbarId="pageToolBar" isFirstLoadData="false" url="/" width="780" height="150" groupCol="hz" grouping="true" counting="true">
<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
<odin:gridDataCol name="checked3" />
<odin:gridDataCol name="aae002" />
<odin:gridDataCol name="eae323" />
<odin:gridDataCol name="eae324" />
<odin:gridDataCol name="aae079" />
<odin:gridDataCol name="eab202" />
<odin:gridDataCol name="eab204" />
<odin:gridDataCol name="eaz247" />
<odin:gridDataCol name="aae180"  type="float"/>
<odin:gridDataCol name="aae058"  type="float"/>
<odin:gridDataCol name="aae020"  type="float"/>
<odin:gridDataCol name="aae022" type="float" />
<odin:gridDataCol name="aae021"  type="float"/>
<odin:gridDataCol name="aae023"  type="float"/>
<odin:gridDataCol name="aae029" type="float"/>
<odin:gridDataCol name="eac003" type="int"/>
<odin:gridDataCol name="aaz289" />
<odin:gridDataCol name="hz" />
<odin:gridDataCol name="aaz223" isLast="true"/>
</odin:gridJsonDataModel>
<odin:gridColumnModel>
<odin:gridRowNumColumn/>
<odin:gridColumn header="selectall" width="80" dataIndex="checked3" editor="checkbox" edited="true" checkBoxClick="radow.cm.doGridCheck"/>
<ss:gridEditColumn header="��������" width="80" dataIndex="aae002" editor="number"/>
<ss:gridEditColumn header="Ӧ������" width="80" dataIndex="eae323" editor="number"/>
<ss:gridEditColumn header="��ֹ����" width="80" dataIndex="eae324" editor="number"/>
<ss:gridEditColumn header="��������" width="80" dataIndex="aae079" editor="number"/>
<ss:gridEditColumn header="Ӧ������" width="80" dataIndex="eab202" editor="select" codeType="EAB202"/>
<ss:gridEditColumn header="��Ŀ�����־" width="100" dataIndex="eab204" editor="select" codeType="EAB204"/>
<ss:gridEditColumn header="�ͱ���־" width="80" dataIndex="eaz247" editor="select" codeType="EAZ247"/>
<ss:gridEditColumn header="�ɷѻ���" width="80" dataIndex="aae180" editor="number" countType="sum" />
<ss:gridEditColumn header="Ӧ�ɻ���" width="80" dataIndex="aae058" editor="number" countType="sum" />
<ss:gridEditColumn header="��λ�ɷ�" width="80" dataIndex="aae020" editor="number" countType="sum" />
<ss:gridEditColumn header="���˽ɷ�" width="80" dataIndex="aae022" editor="number" countType="sum" />
<ss:gridEditColumn header="��λӦ����" width="90" dataIndex="aae021" editor="number" countType="sum" />
<ss:gridEditColumn header="����Ӧ����" width="90" dataIndex="aae023" editor="number" countType="sum" />
<ss:gridEditColumn header="��ƽ����" width="90" dataIndex="aae029" editor="number" countType="sum" />
<ss:gridEditColumn header="����" width="50" dataIndex="eac003" editor="number" countType="sum"/>
<ss:gridEditColumn header="����" width="80" dataIndex="hz" editor="text" p="H"/>
<ss:gridEditColumn header="���ɹ������" width="120" dataIndex="aaz289" editor="select" codeType="AAZ289"/>
<ss:gridEditColumn header="��Ŀ����" width="80" dataIndex="aaz223" editor="number" p="H" isLast="true"/>
</odin:gridColumnModel>
</odin:editgrid>
</odin:tabCont>
<odin:tabCont itemIndex="tab3">
<odin:gridSelectColJs name="eab202"  codeType="EAB202"/>
<odin:gridSelectColJs name="eab203"  codeType="EAB203"/>
<odin:gridSelectColJs name="eab204"  codeType="EAB204"/>
<odin:gridSelectColJs name="eaz247"  codeType="EAZ247"/>
<odin:gridSelectColJs name="aaz289"  codeType="AAZ289"/>
<odin:editgrid property="div_4" pageSize="150" bbarId="pageToolBar" isFirstLoadData="false" url="/" width="780" height="150">
<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
<odin:gridDataCol name="aae002" />
<odin:gridDataCol name="eae323" />
<odin:gridDataCol name="eae324" />
<odin:gridDataCol name="aae079" />
<odin:gridDataCol name="eab202" />
<odin:gridDataCol name="eab203" />
<odin:gridDataCol name="eaz265" />
<odin:gridDataCol name="eab204" />
<odin:gridDataCol name="eaz247" />
<odin:gridDataCol name="aae180" />
<odin:gridDataCol name="aae058" />
<odin:gridDataCol name="aae020" />
<odin:gridDataCol name="aae022" />
<odin:gridDataCol name="aae021" />
<odin:gridDataCol name="aae023" />
<odin:gridDataCol name="aae029" />
<odin:gridDataCol name="eac003" />
<odin:gridDataCol name="aaz289" isLast="true"/>
</odin:gridJsonDataModel>
<odin:gridColumnModel>
<odin:gridRowNumColumn />
<ss:gridEditColumn header="��������" width="80" dataIndex="aae002" editor="number"/>
<ss:gridEditColumn header="Ӧ������" width="80" dataIndex="eae323" editor="number"/>
<ss:gridEditColumn header="��ֹ����" width="80" dataIndex="eae324" editor="number"/>
<ss:gridEditColumn header="��������" width="80" dataIndex="aae079" editor="number"/>
<ss:gridEditColumn header="Ӧ������" width="120" dataIndex="eab202" editor="select" codeType="EAB202"/>
<ss:gridEditColumn header="Ӧ��������ϸ" width="80" dataIndex="eab203" editor="select" codeType="EAB203"/>
<ss:gridEditColumn header="����ָ��" width="80" dataIndex="eaz265" editor="number" decimalPrecision="4"/>
<ss:gridEditColumn header="��Ŀ�����־" width="120" dataIndex="eab204" editor="select" codeType="EAb204"/>
<ss:gridEditColumn header="�ͱ���־" width="80" dataIndex="eaz247" editor="select" codeType="EAZ247"/>
<ss:gridEditColumn header="�ɷѻ���" width="80" dataIndex="aae180" editor="number" />
<ss:gridEditColumn header="Ӧ�ɻ���" width="80" dataIndex="aae058" editor="number" />
<ss:gridEditColumn header="��λ�ɷ�" width="80" dataIndex="aae020" editor="number" />
<ss:gridEditColumn header="���˽ɷ�" width="80" dataIndex="aae022" editor="number" />
<ss:gridEditColumn header="��λӦ����" width="90" dataIndex="aae021" editor="number" />
<ss:gridEditColumn header="����Ӧ����" width="90" dataIndex="aae023" editor="number" />
<ss:gridEditColumn header="��ƽ����" width="80" dataIndex="aae029" editor="number" />
<ss:gridEditColumn header="����" width="80" dataIndex="eac003" editor="number"/>
<ss:gridEditColumn header="���ɹ������" width="120" dataIndex="aaz289" editor="select" codeType="AAZ289" isLast="true"/>
</odin:gridColumnModel>
</odin:editgrid>
</odin:tabCont>
<odin:tabCont itemIndex="tab4">
<odin:gridSelectColJs name="eaz247"  codeType="EAZ247"/>
<odin:gridSelectColJs name="aae140"  codeType="AAE140"/>
<odin:gridSelectColJs name="eae265"  codeType="EAE265"/>
<odin:editgrid property="div_6" pageSize="150" bbarId="pageToolBar" isFirstLoadData="false" url="/" width="780" height="150">
<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
<odin:gridDataCol name="aae001" />
<odin:gridDataCol name="eaz247" />
<odin:gridDataCol name="dnljzs" />
<odin:gridDataCol name="dnbjzs" />
<odin:gridDataCol name="dnjfys" />
<odin:gridDataCol name="dnbjys" />
<odin:gridDataCol name="dnzcjfjs" />
<odin:gridDataCol name="dnbjjfjs" />
<odin:gridDataCol name="gzdwbnbj" />
<odin:gridDataCol name="gzgrbnbj" />
<odin:gridDataCol name="gzbjdwbnbj" />
<odin:gridDataCol name="gzbjgrbnbj" />
<odin:gridDataCol name="aae140" />
<odin:gridDataCol name="eae265" isLast="true"/>
</odin:gridJsonDataModel>
<odin:gridColumnModel>
<odin:gridRowNumColumn />
<ss:gridEditColumn header="���" width="60" dataIndex="aae001" editor="number"/>
<ss:gridEditColumn header="�˻���־" width="80" dataIndex="eaz247" editor="select" codeType="EAZ247"/>
<ss:gridEditColumn header="����ָ����" width="80" dataIndex="dnljzs" editor="number" decimalPrecision="4"/>
<ss:gridEditColumn header="����ָ����" width="80" dataIndex="dnbjzs" editor="number" decimalPrecision="4"/>
<ss:gridEditColumn header="����������" width="80" dataIndex="dnjfys" editor="number"/>
<ss:gridEditColumn header="����������" width="80" dataIndex="dnbjys" editor="number"/>
<ss:gridEditColumn header="����������" width="80" dataIndex="dnzcjfjs" editor="number" />
<ss:gridEditColumn header="���ɻ�����" width="80" dataIndex="dnbjjfjs" editor="number" />
<ss:gridEditColumn header="��λ�ɷѲ��" width="100" dataIndex="gzdwbnbj" editor="number" />
<ss:gridEditColumn header="���˽ɷѲ��" width="100" dataIndex="gzgrbnbj" editor="number" />
<ss:gridEditColumn header="��λ���ɲ��" width="100" dataIndex="gzbjdwbnbj" editor="number" />
<ss:gridEditColumn header="���˲��ɲ��" width="100" dataIndex="gzbjgrbnbj" editor="number" />
<ss:gridEditColumn header="����" width="80" dataIndex="aae140" editor="select" codeType="AAE140"/>
<ss:gridEditColumn header="��Ϣ��־" width="80" dataIndex="eae265" editor="select" codeType="EAE265" isLast="true"/>
</odin:gridColumnModel>
</odin:editgrid>
</odin:tabCont>
</odin:tab>
<odin:toolBar property="topbat1">
<odin:textForToolBar text="������������ӡ�"></odin:textForToolBar>
<odin:fill></odin:fill>
<ss:doClickBtn icon="images/add.gif" handlerName="zjlrtj" text="����¼������"/>
</odin:toolBar>
<odin:gridSelectColJs name="eab202"  codeType="EAB202"/>
<odin:gridSelectColJs name="lx7"  codeType="LX7"/>
<odin:gridSelectColJs name="bjbz"  codeType="BJBZ"/>
<odin:editgrid property="div_7" pageSize="150" bbarId="topbat1" title="¼����������" isFirstLoadData="false" url="/" width="780" height="150">
<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
<odin:gridDataCol name="checked7" />
<odin:gridDataCol name="eae323" />
<odin:gridDataCol name="eae324" />
<odin:gridDataCol name="eab202" />
<odin:gridDataCol name="lx7" />
<odin:gridDataCol name="bjbz" />
<odin:gridDataCol name="aae180" />
<odin:gridDataCol name="aae079_in" />
<odin:gridDataCol name="remove7" />
<odin:gridDataCol name="aaz223" isLast="true"/>
</odin:gridJsonDataModel>
<odin:gridColumnModel>
<odin:gridRowNumColumn />
<odin:gridColumn header="selectall" width="40" dataIndex="checked7" editor="checkbox" edited="true" checkBoxClick="radow.cm.doGridCheck"/>
<ss:gridEditColumn header="��ʼ����" width="80" dataIndex="eae323" editor="number" p="E"/>
<ss:gridEditColumn header="��������" width="80" dataIndex="eae324" editor="number" p="E"/>
<ss:gridEditColumn header="Ӧ������" width="80" dataIndex="eab202" editor="select" p="E" codeType="EAB202"/>
<ss:gridEditColumn header="����" width="80" dataIndex="lx7" editor="select" p="E" codeType="LX7"/>
<ss:gridEditColumn header="���ɱ�־" width="80" dataIndex="bjbz" editor="select" p="E" codeType="BJBZ"/>
<ss:gridEditColumn header="����" width="80" dataIndex="aae180" editor="number" p="E" />
<ss:gridEditColumn header="��������" width="80" dataIndex="aae079_in" editor="number" p="H"/>
<ss:gridEditColumn header="ɾ��" width="80" dataIndex="remove7" editor="text" renderer="renderClick"/>
<ss:gridEditColumn header="��Ŀ����" width="80" dataIndex="aaz223" editor="number" p="H" isLast="true"/>
</odin:gridColumnModel>
</odin:editgrid>
<odin:toolBar property="topbat2">
<odin:textForToolBar text="������������ӡ�"></odin:textForToolBar>
<odin:fill></odin:fill>
<ss:doClickBtn icon="images/add.gif" handlerName="zjcfsj" text="���Ӳ������"/>
</odin:toolBar>
<odin:gridSelectColJs name="eab202"  codeType="EAB202"/>
<odin:gridSelectColJs name="eaz247"  codeType="EAZ247"/>
<odin:gridSelectColJs name="lx"  codeType="LX"/>
<odin:editgrid property="div_8" pageSize="150" bbarId="topbat2" counting="true" grouping="true" groupCol="hz" title="�������" isFirstLoadData="false" url="/" width="780" height="150">
<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
<odin:gridDataCol name="eae323" />
<odin:gridDataCol name="eae324" />
<odin:gridDataCol name="eab202" />
<odin:gridDataCol name="eac003" type="int"/>
<odin:gridDataCol name="aae079" />
<odin:gridDataCol name="lx" />
<odin:gridDataCol name="aae180"  type="float"/>
<odin:gridDataCol name="aae020"  type="float"/>
<odin:gridDataCol name="aae022"  type="float"/>
<odin:gridDataCol name="aae021" type="float" />
<odin:gridDataCol name="aae023"  type="float"/>
<odin:gridDataCol name="aae029"  type="float"/>
<odin:gridDataCol name="eaz247"  type="float"/>
<odin:gridDataCol name="remove8" />
<odin:gridDataCol name="aaz223" />
<odin:gridDataCol name="eab203" />
<odin:gridDataCol name="hz" />
<odin:gridDataCol name="aaz002" isLast="true"/>
</odin:gridJsonDataModel>
<odin:gridColumnModel>
<odin:gridRowNumColumn />
<ss:gridEditColumn header="Ӧ������" width="80" dataIndex="eae323" editor="number" p="E"/>
<ss:gridEditColumn header="��ֹ����" width="80" dataIndex="eae324" editor="number" />
<ss:gridEditColumn header="Ӧ������" width="80" dataIndex="eab202" editor="select" p="E" codeType="EAB202"/>
<ss:gridEditColumn header="����" width="50" dataIndex="eac003" editor="number" countType="sum"/>
<ss:gridEditColumn header="��������" width="80" dataIndex="aae079" editor="number" p="E"/>
<ss:gridEditColumn header="����" width="50" dataIndex="lx" editor="select" p="E" codeType="LX"/>
<ss:gridEditColumn header="����" width="80" dataIndex="aae180" editor="number" p="E" countType="sum" />
<ss:gridEditColumn header="��λ�ɷ�" width="80" dataIndex="aae020" editor="number" p="E" countType="sum" />
<ss:gridEditColumn header="���˽ɷ�" width="80" dataIndex="aae022" editor="number" p="E" countType="sum" />
<ss:gridEditColumn header="��λӦ����" width="80" dataIndex="aae021" editor="number" p="E" countType="sum" />
<ss:gridEditColumn header="����Ӧ����" width="80" dataIndex="aae023" editor="number" p="E" countType="sum" />
<ss:gridEditColumn header="��ƽ����" width="80" dataIndex="aae029" editor="number" p="E" countType="sum" />
<ss:gridEditColumn header="��Ŀ��־" width="80" dataIndex="eaz247" editor="select" p="E" codeType="EAZ247"/>
<ss:gridEditColumn header="ɾ��" width="80" dataIndex="remove8" editor="text" renderer="renderClick"/>
<ss:gridEditColumn header="��Ŀ����" width="80" dataIndex="aaz223" editor="number" p="H"/>
<ss:gridEditColumn header="Ӧ��������ϸ" width="80" dataIndex="eab203" editor="text" p="H"/>
<ss:gridEditColumn header="����" width="80" dataIndex="hz" editor="text" p="H"/>
<ss:gridEditColumn header="ҵ����ˮ��" width="80" dataIndex="aaz002" editor="number" p="H" isLast="true"/>
</odin:gridColumnModel>
</odin:editgrid>
<script type="text/javascript">

function onClickTab(tabObj,item){
    var id = item.id;
}
</script>
 
    