<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<ss:toolBar property="bar1">
	<odin:fill></odin:fill>
	<ss:doSaveBtn></ss:doSaveBtn>
    <ss:resetBtn></ss:resetBtn>
</ss:toolBar>
<ss:hlistDiv id="div_1" cols="6">
	<ss:query property="psquery" onchange="true" p="R"></ss:query>
	<ss:textEdit property="aac003" label="姓名" p="D"></ss:textEdit>
</ss:hlistDiv>	
<odin:groupBox>	
<ss:hlistDiv id="div_2" cols="6">
<ss:textEdit property="aae135" p="D" label="身份证号" onchange="true"></ss:textEdit>
<ss:textEdit property="aac003_2" p="E" label="姓名"></ss:textEdit>
<ss:dateEdit property="aac006" p="D" label="出生日期"></ss:dateEdit>
<ss:select property="eac201" p="D" label="出生日期认定标志" codeType="EAC201"></ss:select>
<ss:select property="aac004" p="P0:E,P1:D" label="性别" codeType="AAC004"></ss:select>
<ss:select property="eab216" p="D" label="身份状态" codeType="EAB216"></ss:select>
<ss:select property="aac005" p="D" label="民族" codeType="AAC005"></ss:select>
<ss:dateEdit property="alc040" p="D" label="死亡日期"></ss:dateEdit>
<ss:select property="aac009" p="P0:E,P1:D" label="户口性质" codeType="AAC009"></ss:select>
<ss:select property="zhbg" p="P0:E,P1:D" label="账户变更原因" onchange="true" data="['1','同意转入',''],['2','转本地户籍',''],['3','错误调整','']"></ss:select>
<ss:select property="eae363" p="P0:E,P1:D" label="本地临时账户标志" codeType="EAE363"></ss:select>
<!--  【aac010 户籍所在地  应该用 ac01中的  AAA130  常住地所属行政区代码  来代替 ，用AAA130  select类型 这个字段显示中文名 即户籍所在地 】-->
<ss:select property="aaa130" p="P0:E,P1:D" label="常住地所属行政区" onchange="true" codeType="AAA130"></ss:select>
<ss:textEdit property="aac010" p="P0:E,P1:D" label="户籍所在地"></ss:textEdit>
<ss:select property="aac011" p="P0:E,P1:D" label="文化程度" codeType="AAC011"></ss:select>
<ss:select property="aac014" p="P0:E,P1:D" label="专业技术职务" codeType="AAC014"></ss:select>		
<ss:select property="aac015" p="P0:E,P1:D" label="国家职业资格等级" codeType="AAC015"></ss:select>
<ss:select property="aac017" p="P0:E,P1:D" label="婚姻状况" codeType="AAC017"></ss:select>
<ss:select property="aac020" p="P0:E,P1:D" label="行政职务(级别)" codeType="AAC020"></ss:select>										
<ss:textEdit property="aae006" p="P0:E,P1:D" label="现居住地址" width="580" colspan="4"></ss:textEdit>								
<ss:textEdit property="aae007" p="P0:E,P1:D" label="邮政编码" maxlength="3"></ss:textEdit>
<ss:textEdit property="aae004" p="P0:E,P1:D" label="联系人姓名"></ss:textEdit>
<ss:textEdit property="aae005" p="P0:E,P1:D" label="联系电话"></ss:textEdit>
<ss:textEdit property="eac101" p="P0:E,P1:D" label="手机"></ss:textEdit>
<ss:textEdit property="aae159" p="P0:E,P1:D" label="联系电子邮箱"></ss:textEdit>
<ss:textEdit property="aab401" p="P0:E,P1:D" label="户号"></ss:textEdit>
<ss:select property="aaa156" p="P0:E,P1:D" label="与户主关系" codeType="AAA156"></ss:select>
<ss:textEdit property="aac012" p="P0:E,P1:D" label="组号"></ss:textEdit>
<ss:textEdit property="aac003_old" label="姓名_修改前" p="H"></ss:textEdit>
<ss:textEdit property="aac003_new" label="姓名_修改后" p="H"></ss:textEdit>
<ss:textEdit property="aae135_old" label="身份证号_修改前" p="H"></ss:textEdit>

<ss:textEdit property="aae135_new" label="身份证号_修改后" p="H"></ss:textEdit>
<ss:textEdit property="loginname" label="操作员" p="H"></ss:textEdit>
<ss:textEdit property="aab001_temp" label="单位编码" p="H"></ss:textEdit>

<ss:textEdit property="aab004_temp" label="单位名称" p="H"></ss:textEdit>
<ss:numberEdit property="eae201" label="当事人变动ID" p="H"></ss:numberEdit>
<ss:textEdit property="aaa027" label="统筹区" p="H"></ss:textEdit>
				
<ss:textEdit property="aac006_temp" label="出生日期" p="H"></ss:textEdit>	
<ss:textEdit property="eac070" label="人员类型（五险统征）" p="H"></ss:textEdit>	
<ss:textEdit property="eac001" label="个人编号（五险统征）" p="H"></ss:textEdit>	

<ss:textEdit property="aae008" label="委托机构::代发机构" p="H"></ss:textEdit>	
<ss:textEdit property="aae010" label="银行账号" p="H"></ss:textEdit>				
</ss:hlistDiv>
</odin:groupBox>
<odin:gridSelectColJs name="eac070"  codeType="EAC070" />
<odin:gridSelectColJs name="eac026"  codeType="EAC026" />
<odin:gridSelectColJs name="aac008_1"  codeType="AAC008" />
<odin:gridSelectColJs name="aac008_2"  codeType="AAC008" />
<odin:gridSelectColJs name="aac008_3"  codeType="AAC008" />
<odin:gridSelectColJs name="aac008_4"  codeType="AAC008" />
<odin:gridSelectColJs name="aac008_5"  codeType="AAC008" />
<odin:gridSelectColJs name="aac008_6"  codeType="AAC008" />
<odin:gridSelectColJs name="aae140_cxjm"  codeType="AAC008" />
<odin:gridSelectColJs name="aab301"  codeType="AAB301" />		
<odin:editgrid property="div_3" pageSize="150" bbarId="pageToolBar"  autoFill=""  url="/" width="780" height="150">
<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
<odin:gridDataCol name="aac003" />
<odin:gridDataCol name="eab217" type="float"/>
<odin:gridDataCol name="eac070" />
<odin:gridDataCol name="eac026" />
<odin:gridDataCol name="aac008_1" />
<odin:gridDataCol name="aac008_2" />
<odin:gridDataCol name="aac008_3" />
<odin:gridDataCol name="aac008_4" />
<odin:gridDataCol name="aac008_5" />
<odin:gridDataCol name="aac008_6" />
<odin:gridDataCol name="aae140_cxjm" />
<odin:gridDataCol name="eac001" />
<odin:gridDataCol name="aab301" />
<odin:gridDataCol name="hz" />
<odin:gridDataCol name="aac007" />
<odin:gridDataCol name="aab001" />
<odin:gridDataCol name="aab004" />
<odin:gridDataCol name="aaz001" />
<odin:gridDataCol name="aaz157" isLast="true"/>
</odin:gridJsonDataModel>
<odin:gridColumnModel>
<odin:gridRowNumColumn />
<ss:gridEditColumn header="姓名" width="60" dataIndex="aac003" editor="text" p="D"/>
<ss:gridEditColumn header="工号1" width="60" dataIndex="eab217" editor="number" maxLength="3"  p="E"/>
<ss:gridEditColumn header="人员类型" width="80" dataIndex="eac070" editor="select" codeType="EAC070" p="D"/>
<ss:gridEditColumn header="参保状态" width="80" dataIndex="eac026" editor="select" codeType="EAC026" p="D"/>
<ss:gridEditColumn header="养老保险" width="60" dataIndex="aac008_1" editor="select" codeType="AAC008" p="D"/>
<ss:gridEditColumn header="医疗保险" width="60" dataIndex="aac008_2" editor="select" codeType="AAC008" p="D"/>
<ss:gridEditColumn header="失业保险" width="60" dataIndex="aac008_3" editor="select" codeType="AAC008" p="D"/>
<ss:gridEditColumn header="工伤保险" width="60" dataIndex="aac008_4" editor="select" codeType="AAC008" p="D"/>
<ss:gridEditColumn header="生育保险" width="60" dataIndex="aac008_5" editor="select" codeType="AAC008" p="D"/>
<ss:gridEditColumn header="教育基金" width="60" dataIndex="aac008_6" editor="select" codeType="AAC008" p="D"/>
<ss:gridEditColumn header="城乡居民" width="80" dataIndex="aae140_cxjm" editor="select" codeType="AAC008" p="D"/>
<ss:gridEditColumn header="个人编码" width="70" dataIndex="eac001" editor="text" p="D"/>
<ss:gridEditColumn header="辖区" width="80" dataIndex="aab301" editor="select" codeType="AAB301" p="D"/>
<ss:gridEditColumn header="参加工作日期" width="80" dataIndex="aac007" editor="text" p="D"/>
<ss:gridEditColumn header="单位编码" width="80" dataIndex="aab001" editor="text" p="D"/>
<ss:gridEditColumn header="单位名称" width="80" dataIndex="aab004" editor="text" p="D"/>
<ss:gridEditColumn header="组织ID" width="80" dataIndex="aaz001" editor="number" p="H"/>
<ss:gridEditColumn header="人员组织ID" width="80" dataIndex="aaz157" editor="number" p="H"/>
<ss:gridEditColumn header="hz" width="80" dataIndex="hz" editor="text" p="H" isLast="true"/>
</odin:gridColumnModel>
</odin:editgrid>

