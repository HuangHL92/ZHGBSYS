<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib uri="/WEB-INF/odin-local.tld" prefix="cm"%>

<ss:toolBar property="bar1" isFloat="true">
	<odin:fill></odin:fill>
<ss:doClickBtn icon="images/add.gif" handlerName="zj" text="计算"/>
<ss:doSaveBtn></ss:doSaveBtn>
<ss:resetBtn></ss:resetBtn>
</ss:toolBar>
<br/>

<ss:hlistDiv id="1" cols="6">
	<ss:query property="psquery" p="R"></ss:query>
<ss:textEdit property="aac003" label="姓名" p="D"  />
<ss:select property="aab301"  label="所属行政区"  codeType="AAB301"  p="D" />
<ss:select property="bjtype"  label="补缴类型"  codeType="BJTYPE"  p="P0:R,P1:D" onchange="true"/>

<ss:select property="aab033"  label="托收方式"  codeType="AAB033"  p="P0:R,P1:D" onchange="true" filter="aaa102 in('0','1')"/>
<ss:select property="eaz247"  label="低保标志"  codeType="EAZ247"  p="P0:R,P1:D" onchange="true" filter="aaa102 in('0','1')"/>
<ss:textEdit property="bz" label="备注" p="E"  />

<ss:select property="sybjlx"  label="生育补缴类型"  codeType="SYBJLX"  p="P0:R,P1:D" onchange="true" filter="aaa102 in('0','1')"/>
<ss:select property="aac008"  label="参保状态"  codeType="AAC008"  p="D" />
<ss:select property="eac070"  label="人员性质"  codeType="EAC070"  p="D" />

<ss:select property="aac004"  label="性别"  codeType="AAC004"  p="D" />
<ss:dateEdit property="aac006" label="出生日期" p="D"/>
<ss:select property="eac158_a"  label="人员类别汇总"  codeType="EAC158_A"  p="D" />

<ss:select property="eac157_a"  label="人员类别明细"  codeType="EAC157_A"  p="D" />
<ss:query property="cpquery" p="R" onchange="true"/>
<ss:textEdit property="aab004" label="单位名称" p="D"  />
<ss:textEdit property="flag_com" label="" p="H"  />

</ss:hlistDiv>
<div id="bar2_div"></div>
<ss:toolBar property="bar2"  isFloat="false">
<odin:fill></odin:fill>
<ss:doClickBtn icon="images/add.gif" handlerName="addrow" text="增加"/>
</ss:toolBar>
<odin:editgrid property="div_2"  pageSize="160" afteredit="radow.cm.afteredit"  isFirstLoadData="false" url="/" title=""  height="200">
<odin:gridJsonDataModel>
  <odin:gridDataCol name="aae041" />
  <odin:gridDataCol name="aae042" />
  <odin:gridDataCol name="aae140"/>
  <odin:gridDataCol name="eaa255" />
  <odin:gridDataCol name="aae180" />
  <odin:gridDataCol name="eab203"/>
  <odin:gridDataCol name="eac157_b" />
  <odin:gridDataCol name="eac158_b" />
  <odin:gridDataCol name="deleteRow"/>
  <odin:gridDataCol name="row" isLast="true"/>
</odin:gridJsonDataModel>
<odin:gridColumnModel>
  <odin:gridRowNumColumn />
  <ss:gridEditColumn header="开始年月" width="70" dataIndex="aae041" editor="number" p="E"/>
  <ss:gridEditColumn header="结束年月" width="70" dataIndex="aae042" editor="number" p="E"/>
  <ss:gridEditColumn header="险种" width="120" dataIndex="aae140" editor="select" p="P0:E,P1:D" codeType="AAE140"/>
  <ss:gridEditColumn header="费率" width="260" dataIndex="eaa255" editor="select" p="P0:E,P1:E" codeType="EAA255"/>
  <ss:gridEditColumn header="缴费基数" width="70" dataIndex="aae180" editor="number" p="E"/>
  <ss:gridEditColumn header="应收类型明细" width="140" dataIndex="eab203" editor="select" p="P0:E,P1:D"/>
  <ss:gridEditColumn header="人员类别明细" width="150" dataIndex="eac157_b" editor="select" p="H"/>
  <ss:gridEditColumn header="人员类别汇总" width="80" dataIndex="eac158_b" editor="select" p="H"/>
  <ss:gridEditColumn header="删除" width="50" dataIndex="deleteRow" editor="text" renderer="renderClick"/>
  <ss:gridEditColumn header="行值" width="20" dataIndex="row" editor="text"  isLast="true" p="H"/>
</odin:gridColumnModel>		
</odin:editgrid>

<odin:editgrid property="div_3"  pageSize="160" afteredit="radow.cm.afteredit" isFirstLoadData="false" url="/" title=""  height="200">
<odin:gridJsonDataModel>
  <odin:gridDataCol name="eae323" />
  <odin:gridDataCol name="aae002" />
  <odin:gridDataCol name="aae140"/>
  <odin:gridDataCol name="eac003" />
  <odin:gridDataCol name="aae180" />
  <odin:gridDataCol name="aae058"/>
  <odin:gridDataCol name="aae022" />
  <odin:gridDataCol name="aae020" />
  <odin:gridDataCol name="aae026" />
  <odin:gridDataCol name="aae024" />
  <odin:gridDataCol name="aae028"/>
  <odin:gridDataCol name="aae021" />
  <odin:gridDataCol name="aae023" />
  <odin:gridDataCol name="aae025"/>
  <odin:gridDataCol name="aae027" />
  <odin:gridDataCol name="aae029" />
  <odin:gridDataCol name="aaz289" />
  <odin:gridDataCol name="eae253" />
  <odin:gridDataCol name="eab202"/>
  <odin:gridDataCol name="eab204" />
  <odin:gridDataCol name="eaz247" />
  <odin:gridDataCol name="eac157"/>
  <odin:gridDataCol name="eac158" />
  <odin:gridDataCol name="eae324" />
  <odin:gridDataCol name="eab203" isLast="true"/>
</odin:gridJsonDataModel>
<odin:gridColumnModel>
  <odin:gridRowNumColumn />
  <odin:gridEditColumn header="应收年月" width="70" dataIndex="eae323" editor="number" edited="true"/>
  <odin:gridEditColumn header="结算年月" width="70" dataIndex="aae002" editor="number" edited="true"/>
  <odin:gridEditColumn header="险种" width="120" dataIndex="aae140" editor="select" edited="false"/>
  <odin:gridEditColumn header="月数" width="90" dataIndex="eac003" editor="number" edited="false"/>
  <odin:gridEditColumn header="缴费基数" width="70" dataIndex="aae180" editor="number" edited="false"/>
  <odin:gridEditColumn header="缴费总额" width="140" dataIndex="aae058" editor="number" edited="false"/>
  <odin:gridEditColumn header="个人缴费" width="150" dataIndex="aae022" editor="number" hidden="true"/>
  <odin:gridEditColumn header="单位缴费" width="80" dataIndex="aae020" editor="number" hidden="true"/>
  <odin:gridEditColumn header="大病缴费" width="50" dataIndex="aae026" editor="number"/>
    <odin:gridEditColumn header="公补缴费" width="70" dataIndex="aae024" editor="number"  hidden="true"/>
  <odin:gridEditColumn header="预留缴费" width="70" dataIndex="aae028" editor="number" edited="false"/>
  <odin:gridEditColumn header="单位划账" width="120" dataIndex="aae021" editor="number" edited="false"/>
  <odin:gridEditColumn header="个人划账" width="90" dataIndex="aae023" editor="number" edited="false"/>
  <odin:gridEditColumn header="公补划账" width="70" dataIndex="aae025" editor="number"  hidden="true"/>
  <odin:gridEditColumn header="大病划账" width="140" dataIndex="aae027" editor="number"  hidden="true"/>
  <odin:gridEditColumn header="社平划账" width="150" dataIndex="aae029" editor="number" hidden="true"/>
  <odin:gridEditColumn header="缴费档次" width="80" dataIndex="aaz289" editor="select" hidden="true"/>
  <odin:gridEditColumn header="年龄月数" width="50" dataIndex="eae253" editor="number"/>
    <odin:gridEditColumn header="应收类型" width="120" dataIndex="eab202" editor="select" edited="false"/>
  <odin:gridEditColumn header="帐目处理标记" width="90" dataIndex="eab204" editor="select" edited="false"/>
  <odin:gridEditColumn header="低保标志" width="70" dataIndex="eaz247" editor="select" hidden="true"/>
  <odin:gridEditColumn header="人员类别明细" width="140" dataIndex="eac157" editor="select" hidden="true"/>
  <odin:gridEditColumn header="人员类别汇总" width="150" dataIndex="eac158" editor="select" hidden="true"/>
  <odin:gridEditColumn header="应收截止年月" width="80" dataIndex="eae324" editor="number" hidden="true"/>
  <odin:gridEditColumn header="应收类型明细" width="20" dataIndex="eab203" editor="text"  isLast="true" hidden="true"/>
</odin:gridColumnModel>		
</odin:editgrid>
