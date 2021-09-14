<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<ss:toolBar property="bar1">
	<odin:fill></odin:fill>
	<ss:doQueryBtn></ss:doQueryBtn>
	<ss:doSaveBtn></ss:doSaveBtn>
	<ss:resetBtn></ss:resetBtn>
</ss:toolBar>
<ss:hlistDiv id="div_1" cols="4">
	<ss:select property="aab301_a" label="辖区" p="E" codeType="AAB301"></ss:select>
	<ss:textEdit property="ylcpcode_a" label="养老单位编码" p="E"></ss:textEdit>
	<ss:textEdit property="iscode_a" label="身份证号" p="E" onchange="true"></ss:textEdit>
	<ss:textEdit property="psname_a" label="姓名" p="E"></ss:textEdit>
</ss:hlistDiv>
<odin:toolBar property="bar2">
	<odin:textForToolBar text="添加请点击【增加】"></odin:textForToolBar>
	<odin:fill></odin:fill>
	<ss:doClickBtn icon="images/add.gif" handlerName="zj" text="增加" />
</odin:toolBar>
<odin:gridSelectColJs name="aab301" codeType="AAB301" />
<odin:gridSelectColJs name="rylb" codeType="RYLB" />
<odin:gridSelectColJs name="psstatus" codeType="AAC008" />
<odin:gridSelectColJs name="pstype" codeType="EAC070" />
<odin:gridSelectColJs name="sex" codeType="AAC004" />
<odin:editgrid property="div_2" pageSize="150" topBarId="bar2" autoFill="false"
	bbarId="pageToolBar" isFirstLoadData="false" url="/" width="780" afteredit="radow.cm.afteredit"
	height="300">
	<odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
		<odin:gridDataCol name="iscode" />
		<odin:gridDataCol name="psname" />
		<odin:gridDataCol name="sex" />
		<odin:gridDataCol name="csrq" />
		<odin:gridDataCol name="rewage" />
		<odin:gridDataCol name="ybjfyfs" />
		<odin:gridDataCol name="fdtxny" />
		<odin:gridDataCol name="xjyfs" />
		<odin:gridDataCol name="xjqsyf" />
		<odin:gridDataCol name="pstype" />
		<odin:gridDataCol name="psstatus" />
		<odin:gridDataCol name="xjjsyf" />
		<odin:gridDataCol name="yljfyfs" />
		<odin:gridDataCol name="rylb" />
		<odin:gridDataCol name="aab301" />
		<odin:gridDataCol name="bz" />
		<odin:gridDataCol name="ylcpcode" />
		<odin:gridDataCol name="yldwmc" />
		<odin:gridDataCol name="prseno" isLast="true" />
	</odin:gridJsonDataModel>
	<odin:gridColumnModel>
		<odin:gridRowNumColumn />
		<ss:gridEditColumn editor="text" dataIndex="iscode" header="身份证号"
			p="D" width="150"></ss:gridEditColumn>
		<ss:gridEditColumn editor="text" dataIndex="psname" header="姓名" p="E"
			width="80"></ss:gridEditColumn>
		<ss:gridEditColumn editor="select" dataIndex="sex" header="性别" p="E"
			width="80" codeType="AAC004"></ss:gridEditColumn>
		<ss:gridEditColumn editor="date" dataIndex="csrq" header="出生日期" p="E"
			width="150"></ss:gridEditColumn>
		<ss:gridEditColumn editor="number" dataIndex="rewage" header="缴费基数"
			p="E" width="120"></ss:gridEditColumn>
		<ss:gridEditColumn editor="number" dataIndex="ybjfyfs"
			header="医保缴费月份数" p="E" width="100"></ss:gridEditColumn>
		<ss:gridEditColumn editor="number" dataIndex="fdtxny" header="法定退休年月"
			p="E" width="100"></ss:gridEditColumn>
		<ss:gridEditColumn editor="number" dataIndex="xjyfs" header="协缴月份数"
			p="E" width="80"></ss:gridEditColumn>
		<ss:gridEditColumn editor="number" dataIndex="xjqsyf" header="协缴起始年月"
			p="E" width="80"></ss:gridEditColumn>
		<ss:gridEditColumn editor="select" dataIndex="pstype" header="养老在职情况"
			p="E" width="100" codeType="EAC070"></ss:gridEditColumn>
		<ss:gridEditColumn editor="select" dataIndex="psstatus"
			header="养老参保状态" p="E" width="100" codeType="AAC008"></ss:gridEditColumn>
		<ss:gridEditColumn editor="number" dataIndex="xjjsyf" header="协缴结束年月"
			p="E" width="80"></ss:gridEditColumn>
		<ss:gridEditColumn editor="number" dataIndex="yljfyfs"
			header="养老缴费月份数01-02年" p="E" width="150"></ss:gridEditColumn>
		<ss:gridEditColumn editor="select" dataIndex="rylb" header="鄞州人员类别"
			p="E" width="100" codeType="RYLB"></ss:gridEditColumn>
		<ss:gridEditColumn editor="select" dataIndex="aab301" header="辖区"
			p="E" width="100" codeType="AAB301"></ss:gridEditColumn>
		<ss:gridEditColumn editor="text" dataIndex="bz" header="备注" p="E"
			width="80"></ss:gridEditColumn>
		<ss:gridEditColumn editor="text" dataIndex="ylcpcode" header="养老单位编码"
			p="E" width="80"></ss:gridEditColumn>
		<ss:gridEditColumn editor="text" dataIndex="yldwmc" header="养老单位名称"
			p="E" width="160"></ss:gridEditColumn>
		<ss:gridEditColumn editor="number" dataIndex="prseno" header="业务流水号"
			p="H" width="60" isLast="true"></ss:gridEditColumn>
	</odin:gridColumnModel>
</odin:editgrid>

