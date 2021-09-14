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
<%-- <ss:textEdit property="cpquery" label="单位查询" p="P0:H,P1:R" onchange="true"/>
 --%><ss:textEdit property="aab003" label="组织机构代码" p="R" onchange="true" maxlength="10"/>
<ss:textEdit property="aab030" label="纳税代码" p="R"  onchange="true"/>

<ss:textEdit property="aab004" label="单位名称" p="R" onchange="true"/>
<ss:textEdit property="aab001" label="单位编码" p="P0:R,P1:D" onchange="true"/>
<ss:select property="aab301"  label="辖区"  codeType="AAB301"  p="R"/>

<ss:textEdit property="aae006" label="单位地址" p="R" maxlength="100" width="300" onchange="true"/>
<ss:dateEdit property="eae278" label="填表日期" p="R" />
<ss:textEdit property="eab231" label="单位电话" p="E" maxlength="20"/>

<ss:textEdit property="eae284" label="单位通迅地址" p="E" maxlength="60" width="300"/>
<ss:textEdit property="aae007" label="邮编" p="E" maxlength="6" onchange="true"/>
<ss:textEdit property="aae159" label="电子邮箱" p="E" mask="email" maxlength="50"/>

<ss:textEdit property="eae279" label="传真号码" p="E" maxlength="30" onchange="true"/>
<ss:select property="aab006" codeType="AAB006" label="执照种类" p="R"/>
<ss:textEdit property="aab007" label="执照号码" p="E"  maxlength="24"/>

<ss:dateEdit property="aab008" label="工商发证日期" p="E"/>
<ss:dateEdit property="aab009" label="工商登记有效期限" p="E"/>
<ss:select property="eab215" codeType="EAB215" label="单位类型" p="R" filter="aaa105 = '0'"/>

<ss:select property="aab022" codeType="AAB022" label="行业代码" p="R" onchange="true"/>
<ss:select property="eab226" codeType="EAB226" label="行业大类" p="E" />
<ss:textEdit property="aae048" label="批准单位" p="E" maxlength="100" />

<ss:dateEdit property="aae049" label="批准日期" p="E"/>
<ss:textEdit property="aae051" label="批准文号" p="E" maxlength="100" />
<ss:textEdit property="aae045" label="法人姓名" p="E" maxlength="50" />

<ss:textEdit property="aae046" label="法人身份证" p="E" maxlength="18" />
<ss:textEdit property="eae280" label="法人电话" p="E" maxlength="40" />
<ss:textEdit property="eae281" label="专管员姓名" p="E" maxlength="50" />

<ss:textEdit property="eae283" label="专管员电话" p="E" maxlength="64" />
<ss:textEdit property="eab230" label="专管员手机" p="E" maxlength="11" onchange="true"/>
<ss:select property="aaa149" codeType="AAA149" label="工伤费率基准" p="E" />

<ss:select property="aab021" codeType="AAB021" label="录属关系" p="R" />
<ss:select property="aab023" codeType="AAB023" label="主管部门" p="E" />
<ss:textEdit property="aab002" label="社保登记证编码" p="R"  onchange="true"/>

<ss:textEdit property="eab227" label="存档号" p="R"  />
<ss:select property="aaf015" codeType="AAF015" label="所属街道" p="R" onchange="true"/>
<ss:select property="aaf020" codeType="AAF020" label="税务征收部门" p="E" />

<ss:select property="eab026" codeType="EAB026" label="单位参保状态" p="H" />
<ss:select property="eab014" codeType="EAB014" label="组织类型" p="H" />
<ss:textEdit property="eab219" label="组织托收类型" p="H"  />
<ss:select property="aaa027" codeType="AAA027" label="统筹区" p="H" />
<ss:select property="eab221" codeType="EAB221" label="个体工商户" p="H" />
<%-- <ss:textEdit property="aaz001" label="单位查询" p="H" />
 --%>
</ss:hlistDiv>