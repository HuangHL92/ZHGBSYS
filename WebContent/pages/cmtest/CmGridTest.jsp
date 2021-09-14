<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<script type="text/javascript">
function test(){
	//var saveBtn = odin.ext.getCmp('doSaveBtn');
	//saveBtn.handler.call('click',saveBtn,null);
	odin.startPJob('pjob1',"jin123,admin");
	//var msel = odin.ext.getCmp('msel_combo');
	//msel.setValue("1,3",'key');
	//alert(msel.getXType());
	//alert(msel.getValue());
}
function test2(){
	odin.monitorPJob('pjob1');
}
function test3(){
	odin.autoFitHeight('div_3');
}
function test4(){
	//odin.ext.getCmp("msel2_combo").focus();
	opItemLabel("div_3","aaa003","新标签");
}
function rend(value, params, record, rowIndex, colIndex, ds){
	return "<a href=#>"+value+"</a>";
}
//var div_1_checkbox_dataindex = "check";
//var div_1_exp_dataindex = "aaa001,aaa002";
</script>
<ss:hlistDiv id="div_2" cols="6">
	<tr height="26">
		<td style="font-size: 12px" align="right" nowrap="nowrap">查询代码信息：</td>
		<td>
		<tags:IntelligentSeach3 property="S1" displayField="aaa103" dataColNames="aaa100,aaa102,aaa103" listWidth="200" queryClass="com.hsqldb.test.SearchDemo" showColNames="aaa100,aaa102,aaa103"/>
		</td>
		<td style="font-size: 12px" align="right" nowrap="nowrap">查询代码信息2：</td>
		<td>
		<tags:IntelligentSeach2 property="S2" displayField="aaa103" dataColNames="aaa100,aaa102,aaa103" listWidth="280" queryClass="com.hsqldb.test.SearchDemo" showColNames="aaa100,aaa102,aaa103"
			showColHeads="代码类别:120,代码值:80,代码名:300"/>
		</td>
	</tr>
	<ss:dateEdit property="d1" label="日期测试" onchange="true" p="E"></ss:dateEdit>
	<ss:select property="taab301" label="下拉测试" p="E" codeType="EAZ216" allAsItem="true"></ss:select>
	<ss:textEdit property="tt1"  label="测试" p="E" value="ttt123test"/>
	<ss:select property="aab301" label="区信息" p="E" onchange="true"></ss:select>
	<ss:select property="aaf015" label="街道信息" p="E" onchange="true"></ss:select>
	<ss:empeyTD empeyTDCount="2"><td rowspan="6" colspan="2" align="center"><img width="120" height="150" src="http://pic2.sc.chinaz.com/files/pic/pic9/201306/xpic11690.jpg"/></td></ss:empeyTD>
	<ss:select property="aaf030" label="社区信息" p="E"></ss:select>
	<ss:numberEdit property="test1" isMoney="true" label="钱" p="R"></ss:numberEdit>
	<ss:empeyTD empeyTDCount="2"></ss:empeyTD>
	<ss:numberEdit property="aaa001" isPercent="true" label="百分比" p="E"></ss:numberEdit>
	<ss:numberEdit property="test3" isPercentOrNot="true" label="百分比数字" p="E"></ss:numberEdit>
	<ss:empeyTD empeyTDCount="2"></ss:empeyTD>
	<ss:select property="msel" multiSelect="true" label="多选测试" codeType="EAZ216" p="E" onchange="true"></ss:select>
	<ss:select property="msel2" label="分页测试" minChars="1" isPageSelect="true" codeType="AAB023" p="E" onchange="true" pageSize="10"></ss:select>
	<ss:empeyTD empeyTDCount="2"></ss:empeyTD>
	<ss:empeyTD empeyTDCount="2"><td colspan="2"><ss:button text="更新" handlerName="updateBtn"/></td></ss:empeyTD>
</ss:hlistDiv>
<ss:toolBar property="bar1">
	<ss:opLogButtonForToolBar/>
	<ss:doClickBtn text="测试" handlerName="test5"></ss:doClickBtn>
	<ss:fill></ss:fill>
	<ss:doClickBtn text="测试" handler="test4"></ss:doClickBtn>
	<ss:doClickBtn text="适应高度" handler="test3"></ss:doClickBtn>
	<ss:doClickBtn text="任务监控" handler="test2"></ss:doClickBtn>
	<ss:doClickBtn text="任务启动" handler="test"></ss:doClickBtn>
	<ss:wzOpLogBtn text="温州日志测试" sqlInfo="select ab01.aaz001,ab01.aab004,ab01.aab008 from ab01"></ss:wzOpLogBtn>
	<ss:wzOpLogBtn sqlInfo="select smt_user.loginname,smt_user.username,smt_act.objectid,smt_act.roleid from smt_user,smt_act where smt_act.objectid = smt_user.userid"></ss:wzOpLogBtn>
	<ss:doImpBtn></ss:doImpBtn>
	<ss:doQueryBtn noRequiredValidate="true"></ss:doQueryBtn>
	<ss:doClickBtn icon="images/folder_go.png" text="123" handlerName="add" noRequiredValidate="true"/>
	<ss:doSaveBtn/>
	<ss:resetBtn/>
	<ss:doExpBtn></ss:doExpBtn>
</ss:toolBar>
<ss:editgrid property="div_1" hasAllRightMenu="true" rowDbClick="true" sm="cell" afteredit="radow.cm.afteredit" bbarId="pageToolBar" isFirstLoadData="false" url="/" title=""  height="-237,0.5">
<odin:gridJsonDataModel>
  <odin:gridDataCol name="id" />
  <odin:gridDataCol name="check" />
  <odin:gridDataCol name="aaa001" />
  <odin:gridDataCol name="aaa002"/>
  <odin:gridDataCol name="aae140"/>
  <odin:gridDataCol name="aaa003" isLast="true"/>
</odin:gridJsonDataModel>
<odin:gridColumnModel>
  <odin:gridRowNumColumn />
  <odin:gridColumn header="selectall" width="80" dataIndex="check" edited="true" gridName="div_1" editor="checkbox"  checkBoxClick="radow.cm.doGridCheck" checkBoxSelectAllClick="radow.cm.doCheck"/>
  <ss:gridEditColumn header="测试<br>列1" width="160" dataIndex="aaa001" align="center" editor="text" p="E"/>
  <ss:gridEditColumn header="测试列2" width="160" dataIndex="aaa002" editor="number" />
  <ss:gridEditColumn header="测试列1" width="160" dataIndex="aae140" editor="select" displayField="key" p="H"/>
  <ss:gridEditColumn header="测试列3" width="260" dataIndex="aaa003" editor="date" format="Y-m-d" p="E" isLast="true"/>
</odin:gridColumnModel>		
</ss:editgrid>
<%
/*
//以后grid的新的写法
*/
%>
<ss:editgrid property="div_3" sm="cell"  pageSize="2" afteredit="radow.cm.afteredit" hasRightMenu="false"  bbarId="pageToolBar" isFirstLoadData="false" url="/" title=""  height="-237,0.5">
<ss:gridColModel rownumWidth="32">
  <ss:gridCol header="selectall" width="80" name="check" editor="checkbox" p="E" checkBoxClick="radow.cm.doGridCheck" checkBoxSelectAllClick="radow.cm.doCheck"/>
  <ss:gridCol header="测试列1" width="160" name="aaa001" editor="text" p="R" enterAutoAddRow="true"/>
  <ss:gridCol header="测试列2" width="160" name="aaa002" editor="number" type="float" p="R"/>
  <ss:gridCol header="测试列1" width="160" name="aae140" editor="select" p="E"/>
  <ss:gridCol header="测试列3" width="260" name="aaa003" editor="date" format="Y-m-d" p="E"/>
</ss:gridColModel>		
</ss:editgrid>