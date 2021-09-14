<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<ss:toolBar property="bar1">
	<odin:fill></odin:fill>
<ss:doSaveBtn></ss:doSaveBtn>
<ss:resetBtn></ss:resetBtn>
</ss:toolBar>
<odin:groupBox title="人员基本信息" property="gb1">
  <ss:hlistDiv id="1" cols="6">
	<ss:query property="psquery" p="R"></ss:query>
	<ss:textEdit property="aac003" label="姓名" p="D"/>
	<ss:select property="aac004" label="性别" p="D"></ss:select>
	<ss:textEdit property="eab217" label="工号" p="D"/>
	<ss:select property="aac013" label="人员性质" p="E"></ss:select>
	<ss:textEdit property="aab001" label="单位编码" p="D"/>
	<ss:textEdit property="aab004" label="单位名称" p="D"/>
	<ss:dateEdit property="aac007" label="参加工作时间" p="P0/P1:H,P2:D"/>
	<ss:dateEdit property="aac049" label="养老首次参保时间"/>
	<ss:select property="aac009" label="户籍性质" p="R"></ss:select>
	<ss:select property="eae363" label="临时账户标志" p="R"></ss:select>
	<ss:textEdit property="aac010" label="户籍地"/>
	<ss:numberEdit property="aaz001" label="单位内码" p="H"/>
	<ss:numberEdit property="aac001" label="身份id" p="H"/>
	<ss:dateEdit property="aac006" label="出生日期" p="H"/>
	<ss:textEdit property="aae135" label="社会保障号码" p="H"/>
  </ss:hlistDiv>
</odin:groupBox>
<odin:groupBox title="对方信息" property="gb2">
  <ss:hlistDiv id="2" cols="6">
	<ss:select property="eab208" label="转移类型" p="D"></ss:select>
	<ss:dateEdit property="aae035" label="转移日期" p="E"/>
	<ss:select property="aaa020" label="对方社会保险机构" p="R" onchange="true"></ss:select>
	<ss:textEdit property="eae262" label="对方单位" p="E"/>
	<ss:textEdit property="eae261" label="对方地址" p="E"/>
	<ss:textEdit property="eae260" label="对方邮编" p="E"/>
	<ss:textEdit property="eae258" label="对方开户银行" p="E"/>
	<ss:textEdit property="eae259" label="对方帐号" p="E"/>
  </ss:hlistDiv>
</odin:groupBox>

<odin:groupBox title="转移基本信息" property="gb3">
  <ss:module param="P0">
  <ss:hlistDiv id="3" cols="6">
	<ss:textEdit property="aab305" label="首次参保地实行个人缴费时间" p="E" />
	<ss:textEdit property="aac049_2" label="本人首次缴费时间" p="E" />
	<ss:textEdit property="aac032" label="建立个人账户时间" p="E" />
	<ss:dateEdit property="eaz293" label="本地缴费起始时间" p="E" />
	<ss:dateEdit property="eaz294" label="本地缴费终止时间" p="E" />
  </ss:hlistDiv>
  </ss:module>
  <ss:module param="P1">
  <ss:hlistDiv id="3" cols="6">
	<ss:textEdit property="aab305" label="首次参保地实行个人缴费时间" p="E" />
	<ss:textEdit property="aac049_2" label="本人首次缴费时间" p="E" />
	<ss:textEdit property="aac032" label="建立个人账户时间" p="E" />
	<ss:dateEdit property="eaz293" label="本地缴费起始时间" p="E" />
	<ss:dateEdit property="eaz294" label="本地缴费终止时间" p="E" />
  </ss:hlistDiv>
  </ss:module>
</odin:groupBox>
<odin:groupBox title="转移账户信息" property="gb4">
  <ss:module param="P0">
  <ss:hlistDiv id="4" cols="4">
	<ss:numberEdit property="grjf98" label="①1998年1月1日前账户个人缴费累计储存额：" />
	<ss:numberEdit property="aypsacct97" label="其中：至97年末个人累计本息："/>
	<ss:numberEdit property="kj98" label="98年1－4月扣减额："/>
	<ss:numberEdit property="snmto98" label="②1998年1月1日至调转上年末个人账户累计储存额："/>
	<ss:numberEdit property="zsnmze" label="其中：个人账户累计额(至转移上自然年末)："/>
	<ss:numberEdit property="ayacct97" label="97年度个人账户累计额："/>
	<ss:numberEdit property="rewage7" label="98年1－4月缴费基数和×7％的本息和："/>
	<ss:numberEdit property="aypsacct9712" label="97年度个人缴费本息："/>
	<ss:numberEdit property="zyzjgrln" label="2006年5月后补缴1998年1月至2005年12月的增加额（历年补缴）3%："/>
	<ss:numberEdit property="zyzjeyznzb" label="鄞州农职保账户增加额1%："/>
	<ss:numberEdit property="zykj" label="2006年1－4月扣减额："/>
	<ss:numberEdit property="dnzhje" label="③调转当年计入个人账户本金金额："/>
	<ss:numberEdit property="zyzjgrdn" label="其中：2006年5月后补缴1998年1月至2005年12月的增加额（当年补缴）3%："/>
	<ss:numberEdit property="gzje" label="④个人账户基金转移额（1＋2＋3）："/>
	<ss:numberEdit property="tcjjze" label="⑤统筹基金转移额："/>
	<ss:numberEdit property="zyze" label="⑥转移基金总额（4＋5）："/>
	<ss:numberEdit property="cmonth" label="98年1-4月缴费月数" p="H"/>  
	<ss:numberEdit property="crewage" label="其中缴费基数" p="H"/>
	<ss:numberEdit property="tcjj" label="统筹基金" p="H"/>
	<ss:numberEdit property="payidx" label="总指数和" p="H"/>
	<ss:numberEdit property="cymons" label="其中本年缴费月数" p="H"/>
	<ss:numberEdit property="acyears" label="实际缴费年限和" p="H"/>
  </ss:hlistDiv>
  </ss:module>
  <ss:module param="P1">
  <ss:hlistDiv id="4" cols="4">
	<ss:numberEdit property="cmonth" label="98年1-4月缴费月数" p="E" minValue="0" maxValue="4"/>
	<ss:numberEdit property="crewage" label="其中缴费基数" p="E"/>
	<ss:dateEdit property="eae256" label="截止缴费时间" p="E"/>
	<ss:numberEdit property="payidx" label="总指数和"/>
	<ss:numberEdit property="bfidyears" label="视同缴费年限"/>
	<ss:numberEdit property="acyears" label="实际缴费年限和"/>
	<ss:numberEdit property="cymons" label="其中本年缴费月数"/>
	<ss:numberEdit property="ljyear" label="累计缴费年限"/>
	<ss:numberEdit property="wlwgzs" label="外来务工指数"/>
	<ss:numberEdit property="wlwgys" label="外来务工年限"/>
  </ss:hlistDiv>
  <odin:editgrid property="div_5" pageSize="150" bbarId="pageToolBar" isFirstLoadData="false" url="/" title="缴费基本信息" width="780" height="200">
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
	    	<odin:gridColumn header="上年止累计储存额(1)" width="80" dataIndex="lyprin" editor="number"/>
	    	<odin:gridColumn header="上年本金本年生息(2)" width="91" dataIndex="lyins"  editor="number"/>
	    	<odin:gridColumn header="本年记帐额(3)" width="70" dataIndex="cyprin" editor="number"/>
	    	<odin:gridColumn header="本年本金当年生息(4)" width="60" dataIndex="cyins" editor="number" isLast="true" />
       </odin:gridColumnModel>
</odin:editgrid>
  <ss:hlistDiv id="6" cols="4">
	<ss:numberEdit property="zyhj" label="转移额合计(1+2+3)" p="E"/>
	<ss:numberEdit property="jzehj" label="记帐额转移合计(1+2+3+4)"/>
	<ss:numberEdit property="jzehjps" label="其中个人"/>
	<ss:numberEdit property="zykj" label="扣减额"/>
  </ss:hlistDiv>
  </ss:module>
  <ss:module param="P2">
  <ss:hlistDiv id="4" cols="4">
	<ss:numberEdit property="cmonth" label="98年1-4月缴费月数" p="E" minValue="0" maxValue="4"/>
	<ss:numberEdit property="crewage" label="其中缴费基数" p="E"/>
	<ss:dateEdit property="eae256" label="截止缴费时间" p="E"/>
	<ss:numberEdit property="payidx" label="总指数和"/>
	<ss:numberEdit property="bfidyears" label="视同缴费年限"/>
	<ss:numberEdit property="acyears" label="实际缴费年限和"/>
	<ss:numberEdit property="cymons" label="其中本年缴费月数"/>
	<ss:numberEdit property="ljyear" label="累计缴费年限"/>
	<ss:numberEdit property="wlwgzs" label="外来务工指数"/>
	<ss:numberEdit property="wlwgys" label="外来务工年限"/>
  </ss:hlistDiv>
  <odin:editgrid property="div_5" pageSize="150" bbarId="pageToolBar" isFirstLoadData="false" url="/" title="缴费基本信息" width="780" height="200">
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
	    	<odin:gridColumn header="上年止累计储存额(1)" width="80" dataIndex="lyprin" editor="number"/>
	    	<odin:gridColumn header="上年本金本年生息(2)" width="91" dataIndex="lyins"  editor="number"/>
	    	<odin:gridColumn header="本年记帐额(3)" width="70" dataIndex="cyprin" editor="number"/>
	    	<odin:gridColumn header="本年本金当年生息(4)" width="60" dataIndex="cyins" editor="number" isLast="true" />
       </odin:gridColumnModel>
</odin:editgrid>
  <ss:hlistDiv id="6" cols="4">
	<ss:numberEdit property="zyhj" label="转移额合计(1+2+3)" p="E"/>
	<ss:numberEdit property="jzehj" label="记帐额转移合计(1+2+3+4)"/>
	<ss:numberEdit property="jzehjps" label="其中个人"/>
	<ss:numberEdit property="zykj" label="扣减额"/>
  </ss:hlistDiv>
  </ss:module>
</odin:groupBox>



	