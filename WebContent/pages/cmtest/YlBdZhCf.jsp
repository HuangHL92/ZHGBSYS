<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<ss:toolBar property="bar1">
	<odin:fill></odin:fill>
	<odin:opLogButtonForToolBar></odin:opLogButtonForToolBar>
	<ss:doClickBtn icon="images/add.gif" handlerName="gdata" text="自动生成明细"/>
	<ss:doClickBtn icon="images/add.gif" handlerName="docalc" text="按条件生成明细"/>
	<ss:doSaveBtn></ss:doSaveBtn>
    <ss:resetBtn></ss:resetBtn>
</ss:toolBar>
<ss:hlistDiv id="div_1" cols="8">
	<ss:query property="psquery" onchange="true"></ss:query>
	<ss:textEdit property="aac003" label="姓名" p="D"></ss:textEdit>
	<ss:dateEdit property="aac049" label="首次参保时间" p="D"></ss:dateEdit>
	<ss:select property="eac070" label="人员状态" p="D" codeType="EAC070"></ss:select>
	
	<ss:select property="aac008" label="参保状态" p="D" codeType="AAC008"></ss:select>
	<ss:textEdit property="aab001" label="单位编码" p="D"></ss:textEdit>
	<ss:textEdit property="aab004" label="单位名称" colspan="4" width="260" p="D"></ss:textEdit>
	<ss:numberEdit property="curRow" label="当前选中的年度行" p="H"></ss:numberEdit>
	<ss:numberEdit property="stym" label="当前选中年度的开始年月" p="H"></ss:numberEdit>
	<ss:numberEdit property="edym" label="当前选中年度的结束年月" p="H"></ss:numberEdit>
	<ss:numberEdit property="spgz" label="当前选中年度的社平工资" p="H"></ss:numberEdit>
	<ss:numberEdit property="aaz157" p="H"></ss:numberEdit>
</ss:hlistDiv>
<odin:tab id="tab">
  <odin:tabModel>
       <odin:tabItem title="年度账户信息" id="tab1"></odin:tabItem>
       <odin:tabItem title="不需拆分的明细账户" id="tab2"></odin:tabItem>
       <odin:tabItem title="需拆分的明细账目" id="tab3"></odin:tabItem>
       <odin:tabItem title="差异数据" id="tab4" isLast="true"></odin:tabItem>
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
            <odin:gridColumn header="选择" width="80" dataIndex="info" editor="checkbox" edited="true" checkBoxClick="radow.cm.doGridCheck"/>
	  		<ss:gridEditColumn header="年度" width="50" dataIndex="aae001" editor="number" />
	    	<ss:gridEditColumn header="险种" width="80" dataIndex="aae140" editor="select" codeType="AAE140"/>
	    	<ss:gridEditColumn header="账目标志" width="80" dataIndex="eaz247"  editor="select" codeType="EAZ247"/>
	    	<ss:gridEditColumn header="拆分标记" width="80" dataIndex="eae349" editor="select" codeType="EAE349"/>
	    	<ss:gridEditColumn header="结算年月" width="80" dataIndex="aae002" editor="number" />
	    	<ss:gridEditColumn header="正常月数" width="80" dataIndex="cymonths" editor="number"/>
	    	<ss:gridEditColumn header="补缴月数" width="80" dataIndex="cyremonths" editor="number"/>
	    	<ss:gridEditColumn header="正常指数" width="80" dataIndex="cypayidx" editor="number" decimalPrecision="4"/>
	    	<ss:gridEditColumn header="补缴指数" width="80" dataIndex="cyrepayidx" editor="number" decimalPrecision="4"/>
	    	<ss:gridEditColumn header="缴费基数" width="80" dataIndex="aae180" editor="number" />
	    	<ss:gridEditColumn header="缴费基数和" width="80" dataIndex="cywage" editor="number" />
	    	<ss:gridEditColumn header="补缴基数和" width="80" dataIndex="cyrewage" editor="number" />
	    	<ss:gridEditColumn header="单位部分本金" width="100" dataIndex="cycpprin" editor="number" />
	    	<ss:gridEditColumn header="个人部分本金" width="100" dataIndex="cypsprin" editor="number" />
	    	<ss:gridEditColumn header="社划本金" width="100" dataIndex="cyavprin" editor="number" />
	    	<ss:gridEditColumn header="补缴单位部分本金" width="120" dataIndex="cyrecpprin" editor="number"/>
	    	<ss:gridEditColumn header="补缴个人部分本金" width="120" dataIndex="cyrepsprin" editor="number" />
	    	<ss:gridEditColumn header="补缴社划部分本金" width="120" dataIndex="cyreavprin" editor="number"/>
	    	<ss:gridEditColumn header="结息标志" width="80" dataIndex="eae265" editor="select" codeType="EAE265" isLast="true"/>
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
<ss:gridEditColumn header="结算年月" width="80" dataIndex="aae002" editor="number"/>
<ss:gridEditColumn header="应收年月" width="80" dataIndex="eae323" editor="number"/>
<ss:gridEditColumn header="截止年月" width="80" dataIndex="eae324" editor="number"/>
<ss:gridEditColumn header="到账年月" width="80" dataIndex="aae079" editor="number"/>
<ss:gridEditColumn header="应收类型" width="80" dataIndex="eab202" editor="select" codeType="EAB202"/>
<ss:gridEditColumn header="账目处理标志" width="100" dataIndex="eab204" editor="select" codeType="EAB204"/>
<ss:gridEditColumn header="低保标志" width="80" dataIndex="eaz247" editor="select" codeType="EAZ247"/>
<ss:gridEditColumn header="缴费基数" width="80" dataIndex="aae180" editor="number" countType="sum" />
<ss:gridEditColumn header="应缴基金" width="80" dataIndex="aae058" editor="number" countType="sum" />
<ss:gridEditColumn header="单位缴费" width="80" dataIndex="aae020" editor="number" countType="sum" />
<ss:gridEditColumn header="个人缴费" width="80" dataIndex="aae022" editor="number" countType="sum" />
<ss:gridEditColumn header="单位应划入" width="90" dataIndex="aae021" editor="number" countType="sum" />
<ss:gridEditColumn header="个人应划入" width="90" dataIndex="aae023" editor="number" countType="sum" />
<ss:gridEditColumn header="社平划入" width="90" dataIndex="aae029" editor="number" countType="sum" />
<ss:gridEditColumn header="月数" width="50" dataIndex="eac003" editor="number" countType="sum"/>
<ss:gridEditColumn header="汇总" width="80" dataIndex="hz" editor="text" p="H"/>
<ss:gridEditColumn header="征缴规则类别" width="120" dataIndex="aaz289" editor="select" codeType="AAZ289"/>
<ss:gridEditColumn header="账目内码" width="80" dataIndex="aaz223" editor="number" p="H" isLast="true"/>
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
<ss:gridEditColumn header="结算年月" width="80" dataIndex="aae002" editor="number"/>
<ss:gridEditColumn header="应收年月" width="80" dataIndex="eae323" editor="number"/>
<ss:gridEditColumn header="截止年月" width="80" dataIndex="eae324" editor="number"/>
<ss:gridEditColumn header="到账年月" width="80" dataIndex="aae079" editor="number"/>
<ss:gridEditColumn header="应收类型" width="120" dataIndex="eab202" editor="select" codeType="EAB202"/>
<ss:gridEditColumn header="应收类型明细" width="80" dataIndex="eab203" editor="select" codeType="EAB203"/>
<ss:gridEditColumn header="调整指数" width="80" dataIndex="eaz265" editor="number" decimalPrecision="4"/>
<ss:gridEditColumn header="账目处理标志" width="120" dataIndex="eab204" editor="select" codeType="EAb204"/>
<ss:gridEditColumn header="低保标志" width="80" dataIndex="eaz247" editor="select" codeType="EAZ247"/>
<ss:gridEditColumn header="缴费基数" width="80" dataIndex="aae180" editor="number" />
<ss:gridEditColumn header="应缴基金" width="80" dataIndex="aae058" editor="number" />
<ss:gridEditColumn header="单位缴费" width="80" dataIndex="aae020" editor="number" />
<ss:gridEditColumn header="个人缴费" width="80" dataIndex="aae022" editor="number" />
<ss:gridEditColumn header="单位应划入" width="90" dataIndex="aae021" editor="number" />
<ss:gridEditColumn header="个人应划入" width="90" dataIndex="aae023" editor="number" />
<ss:gridEditColumn header="社平划入" width="80" dataIndex="aae029" editor="number" />
<ss:gridEditColumn header="月数" width="80" dataIndex="eac003" editor="number"/>
<ss:gridEditColumn header="征缴规则类别" width="120" dataIndex="aaz289" editor="select" codeType="AAZ289" isLast="true"/>
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
<ss:gridEditColumn header="年度" width="60" dataIndex="aae001" editor="number"/>
<ss:gridEditColumn header="账户标志" width="80" dataIndex="eaz247" editor="select" codeType="EAZ247"/>
<ss:gridEditColumn header="正常指数差" width="80" dataIndex="dnljzs" editor="number" decimalPrecision="4"/>
<ss:gridEditColumn header="补缴指数差" width="80" dataIndex="dnbjzs" editor="number" decimalPrecision="4"/>
<ss:gridEditColumn header="正常月数差" width="80" dataIndex="dnjfys" editor="number"/>
<ss:gridEditColumn header="补缴月数差" width="80" dataIndex="dnbjys" editor="number"/>
<ss:gridEditColumn header="正常基数差" width="80" dataIndex="dnzcjfjs" editor="number" />
<ss:gridEditColumn header="补缴基数差" width="80" dataIndex="dnbjjfjs" editor="number" />
<ss:gridEditColumn header="单位缴费差额" width="100" dataIndex="gzdwbnbj" editor="number" />
<ss:gridEditColumn header="个人缴费差额" width="100" dataIndex="gzgrbnbj" editor="number" />
<ss:gridEditColumn header="单位补缴差额" width="100" dataIndex="gzbjdwbnbj" editor="number" />
<ss:gridEditColumn header="个人补缴差额" width="100" dataIndex="gzbjgrbnbj" editor="number" />
<ss:gridEditColumn header="险种" width="80" dataIndex="aae140" editor="select" codeType="AAE140"/>
<ss:gridEditColumn header="结息标志" width="80" dataIndex="eae265" editor="select" codeType="EAE265" isLast="true"/>
</odin:gridColumnModel>
</odin:editgrid>
</odin:tabCont>
</odin:tab>
<odin:toolBar property="topbat1">
<odin:textForToolBar text="添加请点击【增加】"></odin:textForToolBar>
<odin:fill></odin:fill>
<ss:doClickBtn icon="images/add.gif" handlerName="zjlrtj" text="增加录入条件"/>
</odin:toolBar>
<odin:gridSelectColJs name="eab202"  codeType="EAB202"/>
<odin:gridSelectColJs name="lx7"  codeType="LX7"/>
<odin:gridSelectColJs name="bjbz"  codeType="BJBZ"/>
<odin:editgrid property="div_7" pageSize="150" bbarId="topbat1" title="录入生成条件" isFirstLoadData="false" url="/" width="780" height="150">
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
<ss:gridEditColumn header="开始年月" width="80" dataIndex="eae323" editor="number" p="E"/>
<ss:gridEditColumn header="结束年月" width="80" dataIndex="eae324" editor="number" p="E"/>
<ss:gridEditColumn header="应收类型" width="80" dataIndex="eab202" editor="select" p="E" codeType="EAB202"/>
<ss:gridEditColumn header="类型" width="80" dataIndex="lx7" editor="select" p="E" codeType="LX7"/>
<ss:gridEditColumn header="补缴标志" width="80" dataIndex="bjbz" editor="select" p="E" codeType="BJBZ"/>
<ss:gridEditColumn header="基数" width="80" dataIndex="aae180" editor="number" p="E" />
<ss:gridEditColumn header="到账年月" width="80" dataIndex="aae079_in" editor="number" p="H"/>
<ss:gridEditColumn header="删除" width="80" dataIndex="remove7" editor="text" renderer="renderClick"/>
<ss:gridEditColumn header="账目内码" width="80" dataIndex="aaz223" editor="number" p="H" isLast="true"/>
</odin:gridColumnModel>
</odin:editgrid>
<odin:toolBar property="topbat2">
<odin:textForToolBar text="添加请点击【增加】"></odin:textForToolBar>
<odin:fill></odin:fill>
<ss:doClickBtn icon="images/add.gif" handlerName="zjcfsj" text="增加拆分数据"/>
</odin:toolBar>
<odin:gridSelectColJs name="eab202"  codeType="EAB202"/>
<odin:gridSelectColJs name="eaz247"  codeType="EAZ247"/>
<odin:gridSelectColJs name="lx"  codeType="LX"/>
<odin:editgrid property="div_8" pageSize="150" bbarId="topbat2" counting="true" grouping="true" groupCol="hz" title="拆分数据" isFirstLoadData="false" url="/" width="780" height="150">
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
<ss:gridEditColumn header="应收年月" width="80" dataIndex="eae323" editor="number" p="E"/>
<ss:gridEditColumn header="截止年月" width="80" dataIndex="eae324" editor="number" />
<ss:gridEditColumn header="应收类型" width="80" dataIndex="eab202" editor="select" p="E" codeType="EAB202"/>
<ss:gridEditColumn header="月数" width="50" dataIndex="eac003" editor="number" countType="sum"/>
<ss:gridEditColumn header="到账年月" width="80" dataIndex="aae079" editor="number" p="E"/>
<ss:gridEditColumn header="类型" width="50" dataIndex="lx" editor="select" p="E" codeType="LX"/>
<ss:gridEditColumn header="基数" width="80" dataIndex="aae180" editor="number" p="E" countType="sum" />
<ss:gridEditColumn header="单位缴费" width="80" dataIndex="aae020" editor="number" p="E" countType="sum" />
<ss:gridEditColumn header="个人缴费" width="80" dataIndex="aae022" editor="number" p="E" countType="sum" />
<ss:gridEditColumn header="单位应划入" width="80" dataIndex="aae021" editor="number" p="E" countType="sum" />
<ss:gridEditColumn header="个人应划入" width="80" dataIndex="aae023" editor="number" p="E" countType="sum" />
<ss:gridEditColumn header="社平划入" width="80" dataIndex="aae029" editor="number" p="E" countType="sum" />
<ss:gridEditColumn header="账目标志" width="80" dataIndex="eaz247" editor="select" p="E" codeType="EAZ247"/>
<ss:gridEditColumn header="删除" width="80" dataIndex="remove8" editor="text" renderer="renderClick"/>
<ss:gridEditColumn header="账目内码" width="80" dataIndex="aaz223" editor="number" p="H"/>
<ss:gridEditColumn header="应收类型明细" width="80" dataIndex="eab203" editor="text" p="H"/>
<ss:gridEditColumn header="汇总" width="80" dataIndex="hz" editor="text" p="H"/>
<ss:gridEditColumn header="业务流水号" width="80" dataIndex="aaz002" editor="number" p="H" isLast="true"/>
</odin:gridColumnModel>
</odin:editgrid>
<script type="text/javascript">

function onClickTab(tabObj,item){
    var id = item.id;
}
</script>
 
    