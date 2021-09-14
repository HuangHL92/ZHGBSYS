<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<ss:toolBar property="bar1" isFloat="true">
	<odin:fill />
	<odin:opLogButtonForToolBar />
	<ss:doSaveBtn />
	<ss:resetBtn />
</ss:toolBar>
<ss:hlistDiv id="1" cols="6">
<ss:module param="P0,P3">
<ss:query property="psidnew" p="R" onchange="true"></ss:query>
</ss:module>
<ss:module param="P1,P2">
<ss:query property="psquery" p="R" onchange="true"></ss:query>
</ss:module>
<ss:textEdit property="aac003" label="姓名" p="P0/P3:R,P1/P2:E"  />
<ss:select property="aac004"  label="性别"  codeType="AAC004" multiSelect="true" onchange="true" p="E"/>
<ss:query property="cpquery" p="R"></ss:query>
<ss:textEdit property="aab004" label="单位名称" p="D"  />
<ss:dateEdit property="aac006" label="出生日期" format="Y-m-d" p="D"/>
<ss:select property="aac005"  label="名族"  codeType="AAC005"  p="P0/P3:R,P1/P2:D" />
<ss:textEdit property="eac001" label="个人编码" p="D"  />
<ss:textEdit property="eab217" label="工号" p="P0/P1/P2:R,P3:D"  onchange="true"/>
<ss:numberEdit property="aic020" label="上报工资" p="R" onchange="true" decimalPrecision="0"/>
<ss:dateEdit property="aac007" label="参加工作时间" p="P0/P3:R,P1/P2:D"  format="Ym"/>
<ss:select property="eac070"  label="人员性质"  codeType="EAC070"  p="D" />
<ss:select property="eac066"  label="个人身份"  codeType="EAC066"  p="R" />
<ss:select property="aac013"  label="用工形式"  codeType="AAC013"  p="E" />
<ss:dateEdit property="eae247" label="变动时间" p="P0/P3:R,P1/P2:D" />
<ss:module param="P0,P1,P3">
<ss:select property="eae204"  label="变动原因"  codeType="EAE204"  p="R" />
</ss:module>
<ss:textEdit property="eae206" label="变动备注" p="E"  />
<ss:textEdit property="aae013" label="参保备注" p="E"  />
<ss:select property="aac009"  label="户粮关系"  codeType="AAC009"  p="P0/P3:R,P1/P2:D" onchange="true"/>
<ss:select property="aaz289_1"  label="养老类别"  codeType="AAZ289"  p="P0/P1/P2:R,P3:D" onchange="true"/>
<ss:select property="eac158"  label="医保类别"  codeType="EAC158"  p="R" filter="aaa102 not in('30')" onchange="true"/>
<ss:select property="eac157"  label="医保子类别"  codeType="EAC157"  p="R" onchange="true"/>
<ss:textEdit property="aab030" label="纳税代码" p="E"  onchange="true"/>
<ss:textEdit property="aae004" label="联系人" p="E"  />
<ss:textEdit property="aae005" label="联系电话" p="E"  />
<ss:textEdit property="aae006" label="联系地址" p="E"  />
<ss:select property="aae008"  label="委托机构"  codeType="AAE008"  onchange="true"/>
<ss:numberEdit property="aae010" label="银行账号" p="E" onchange="true" decimalPrecision="0"/>
<ss:select property="aaa130"  label="行政区"  codeType="AAA130"  p="R" onchange="true"/>
<ss:textEdit property="aac010" label="户籍地" p="E"  />
<ss:select property="eac238"  label="外来人员"  codeType="EAC238"  p="E" onchange="true"/>
<ss:select property="eac239"  label="综合险种"  codeType="EAC239"  p="E" onchange="true"/>
<ss:select property="z_eac203_eac202_021810"  label="被征地人员"  codeType="Z_EAC203_EAC202_021810"  p="E"  onchange="true"/>
<ss:select property="ybzdhf"  label="医保中断补缴"  codeType="YBZDHF"  p="D" onchange="true"/>
<ss:select property="ylzdhf"  label="养老中断补缴"  codeType="YLZDHF"  p="D" onchange="true"/>
<ss:select property="syzdhf"  label="失业中断补缴"  codeType="SYZDHF"  p="D" onchange="true"/>
<ss:numberEdit property="eae007" label="退休年月" p="P0/P2:D,P1/P3:E" onchange="true" decimalPrecision="0"/>
<ss:module param="P0,P3">
<ss:numberEdit property="aaz157" label="人员内码" p="H" />
</ss:module>
<ss:module param="P1,P2">
<ss:numberEdit property="aac001" label="身份内码" p="H" />
</ss:module>
<ss:textEdit property="aae135" label="身份证号" p="H"  />
<ss:select property="eac026"  label="人员状态"  codeType="EAC026"  p="H" />
<ss:select property="eab216"  label="身份状态"  codeType="EAB216"  p="H" />
<ss:textEdit property="eae363" label="临时账户标记" p="H"  />
<ss:textEdit property="z_eac203_eac202_001400" label="外国人标志" p="H"  />
<ss:textEdit property="aaa130_old" label="行政区初值" p="H"  />
<ss:textEdit property="aac010_old" label="户籍地初值" p="H"  />
<ss:textEdit property="ry_aab030" label="人员纳税代码" p="H"  />
<ss:textEdit property="zdbj" label="中断补缴标志" p="H"  />
<ss:numberEdit property="eae323" label="应收年月" p="H" />
<ss:textEdit property="msg" label="补缴提示信息" p="H"  />
</ss:hlistDiv>
<odin:gridSelectColJs name="aac008" codeType="AAC008"></odin:gridSelectColJs>
<odin:gridSelectColJs name="aae140" codeType="AAE140"></odin:gridSelectColJs>
<ss:editgrid property="div_2"  pageSize="200" afteredit="radow.cm.afteredit"  isFirstLoadData="false" url="/" title=""  height="500">
<odin:gridJsonDataModel>
  <odin:gridDataCol name="checked" />
  <odin:gridDataCol name="aae140" />
  <odin:gridDataCol name="aae180" />
  <odin:gridDataCol name="aaa042"/>
  <odin:gridDataCol name="aaa041" />
  <odin:gridDataCol name="aaa046" />
  <odin:gridDataCol name="aaa048"/>
  <odin:gridDataCol name="aac049" />
  <odin:gridDataCol name="aaz289" />
  <odin:gridDataCol name="aaa044"/>
    <odin:gridDataCol name="aac008" />
  <odin:gridDataCol name="eaa255" />
  <odin:gridDataCol name="aaz159"/>
  <odin:gridDataCol name="aaz157" />
  <odin:gridDataCol name="aae206" />
  <odin:gridDataCol name="eaz001"/>
  <odin:gridDataCol name="aaz001" isLast="true"/>
</odin:gridJsonDataModel>
<odin:gridColumnModel>
  <odin:gridRowNumColumn />
    <odin:gridColumn header="selectall" width="50" dataIndex="checked" editor="checkbox" edited="true" checkBoxClick="radow.cm.doGridCheck"/>
  <ss:gridEditColumn header="险种" width="80" dataIndex="aae140" editor="select" p="E" codeType="AAE140"/>
  <ss:gridEditColumn header="缴费基数" width="80" dataIndex="aae180" editor="number" p="R" decimalPrecision="0"/>
  <ss:gridEditColumn header="单位缴费" width="80" dataIndex="aaa042" editor="number" p="D" />
  <ss:gridEditColumn header="个人缴费" width="80" dataIndex="aaa041" editor="number" p="D" />
  <ss:gridEditColumn header="公补比例" width="80" dataIndex="aaa046" editor="number" p="E"/>
  <ss:gridEditColumn header="救助比例" width="80" dataIndex="aaa048" editor="number" p="D"/>
  <ss:gridEditColumn header="建账时间" width="80" dataIndex="aac049" editor="date" p="D"/>
  <ss:gridEditColumn header="费率编码" width="80" dataIndex="aaz289" editor="text" p="D"/>
  <ss:gridEditColumn header="费率信息" width="300" dataIndex="aaa044" editor="text" />
    <ss:gridEditColumn header="险种状态" width="70" dataIndex="aac008" editor="select" p="H" codeType="AAC008"/>
  <ss:gridEditColumn header="费率年龄组" width="70" dataIndex="eaa255" editor="text" p="H"/>
  <ss:gridEditColumn header="主键序列" width="120" dataIndex="aaz159" editor="number" p="H" />
  <ss:gridEditColumn header="人员ID" width="260" dataIndex="aaz157" editor="number" p="H" />
  <ss:gridEditColumn header="建账年月" width="70" dataIndex="aae206" editor="number" p="H"/>
  <ss:gridEditColumn header="新缴费组织ID" width="140" dataIndex="eaz001" editor="number" p="H"/>
  <ss:gridEditColumn header="原缴费组织ID" width="20" dataIndex="aaz001" editor="number"  isLast="true" p="H"/>
</odin:gridColumnModel>		
</ss:editgrid>