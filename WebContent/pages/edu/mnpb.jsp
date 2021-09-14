<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.SysOrgPageModel"%>
<%@ page import="java.util.*"%> 
<%@ page import="java.text.*"%> 
<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@include file="/comOpenWinInit.jsp" %>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comboxWithTree.js"></script>
<odin:hidden property="a1701Word"/>
<odin:hidden property="a0814Word"/>
<odin:hidden property="a0215aWord"/>
<odin:hidden property="rmbs"/>
<odin:hidden property="colIndex"/>
<%
    SysOrgPageModel sys = new SysOrgPageModel();
	String picType = (String) (sys.areaInfo
			.get("picType"));
	String ereaname = (String) (sys.areaInfo
			.get("areaname"));
	String ereaid = (String) (sys.areaInfo
			.get("areaid"));
	String manager = (String) (sys.areaInfo
			.get("manager"));
	
	String ctxPath = request.getContextPath();
%>
<style>
#autowidth {
	height:100px;
	background-size:cover;
    margin-left:8px;
    margin-top:0px;
    overflow:auto;
}
.x-panel-bwrap {
	height: 100%
}
.x-panel-body {
	height: 100%
}
.titleCls{
	color: blue;
}
table tr td{
	font-size: 20px;
}
</style>


<div style="float: left;position: relative;width: 15%" >
	<table  cellspacing="0" cellpadding="0" style="width:100%">
		<tr style="background-color: #cedff5">
			<td style="padding-left: 30"> <input type="checkbox" id="continueCheckbox" onclick="continueChoose()"><font style="font-size: 13">连续选择</font></td>
			<td style="padding-left: 30"><input type="checkbox" id="existsCheckbox" onclick="existsChoose()" ><font style="font-size: 13">包含下级</font></td>
		</tr>
		<tr>
			<td colspan="2">
				<div id="tree-div" style="overflow: auto;  border: 2px solid #c3daf9;"></div>
				<odin:hidden property="codevalueparameter" />
				<odin:hidden property="SysOrgTreeIds" value="{}"/>
			</td>
		</tr>
	</table>
</div>


<div id="conditionArea" style=" overflow-y: scroll;width: 85%">
		<div id="jbDiv" >
			<table style="width:100%">
				<tr>
					<odin:textEdit property="a0101" label="人员姓名" maxlength="36"  />
					<odin:hidden property="a0163"/>
					<odin:select2 property="a0104" label="性别" data="['1', '男'],['2', '女']" ></odin:select2>
<%-- 					<odin:select2 property="a0163" label="人员状态" codeType="ZB126"></odin:select2> --%>
					<odin:select2 property="b0131A" label="机构类别" data="['01', '市直党委部门'],['02', '市直政府部门'],['03', '区县市党委部门'],['04', '区县市政府部门']" multiSelect="true"></odin:select2>
					<odin:select2 property="a0165A" label="省管干部" data="['02', '省管干部'],['05', '省管委托市管']" multiSelect="true"></odin:select2>
					<odin:select2 property="a0165B" label="市管干部" data="['10', '市管正职'],['11', '市管副职'],['18','市直党组（党委）成员、二巡'],['19', '市管职级公务员（含退出领导岗位）'],['04', '市管其他']" multiSelect="true"></odin:select2>
										
				</tr>

				
				<tr>
<%-- 					<odin:select2 property="a0165C" label="处级（中层）干部" data="['12', '市直正处'],['50', '市直副处'],['07', '区管正职'],['08', '区管副职'],['09', '县市管正职'],['13', '县市管副职'],['51','市属副局级企业副职'],['60','市属副局级企业中层副职'],['52','其他']" multiSelect="true"></odin:select2>
 --%>					<td noWrap="nowrap" align=right><span id="a0288SpanId" style="FONT-SIZE: 12px">任现职务层次时间</span>&nbsp;</td>
					<td >
						<table><tr>
							<odin:NewDateEditTag property="a0288A"  maxlength="8" width="72" />
							<td><span style="font: 12px">至</span></td>
							<odin:NewDateEditTag property="a0288B" maxlength="8" width="72" />
						</tr></table>
					</td>
					<td noWrap="nowrap" align=right><span id="ageASpanId" style="FONT-SIZE: 12px">年&nbsp;&nbsp;&nbsp;龄</span>&nbsp;</td>
						<td >
							<table  ><tr>
								<odin:numberEdit property="ageA"  maxlength="3" width="72" />
								<td><span style="font: 12px">至</span></td>
								<odin:numberEdit property="ageB" maxlength="3" width="72" />
							</tr></table>
					</td>
						
					<td noWrap="nowrap" align=right><span id="birthdaySpanId" style="FONT-SIZE: 12px">出生日期</span>&nbsp;</td>
						<td >
							<table><tr>
								<odin:NewDateEditTag property="a0107A"  maxlength="8" width="72" />
								<td><span style="font: 12px">至</span></td>
								<odin:NewDateEditTag property="a0107B" maxlength="8" width="72" />
							</tr></table>
					</td> 
					<odin:hidden property="jiezsj"/>
<%-- 					<odin:NewDateEditTag  isCheck="true" property="jiezsj" maxlength="6"  label="年龄年限计算截止"></odin:NewDateEditTag>
 --%>					<odin:dateEdit property="xrdx05" label="开班时间晚于"
		 maxlength="8" selectOnFocus="true"
		format="Y-m-d" onkeyup="odin.commInputMask(this,'Y-m-d')" />
					<td><label style="FONT-SIZE: 12px">显示省管干部
					</label></td>
					<td>&nbsp;<input id="sggb" name="sggb" type="checkbox" value="1" checked="true"/></td> 
				</tr>

				<tr>
				<odin:hidden property="a0192e"/>
				<odin:hidden property="a0192cA"/>
				<odin:hidden property="a0192cB"/>
<%-- 					<tags:ComBoxWithTree property="a0192e" label="现职级" readonly="true" ischecked="true" width="160" codetype="ZB148" />
					
					<td noWrap="nowrap" align=right><span id="a0192cSpanId" style="FONT-SIZE: 12px">任职级时间</span>&nbsp;</td>
					<td >
						<table><tr>
							<odin:NewDateEditTag property="a0192cA"  maxlength="8" width="72" />
							<td><span style="font: 12px">至</span></td>
							<odin:NewDateEditTag property="a0192cB" maxlength="8" width="72" />
						</tr></table>
					</td> --%>
					<td noWrap="nowrap" align=right><span id="pxsc" style="FONT-SIZE: 12px">班次学制范围(天)</span>&nbsp;</td>
					<td >
						<table><tr>
							<odin:textEdit property="bc1"  maxlength="8" width="72" />
							<td><span style="font: 12px">至</span></td>
							<odin:textEdit property="bc2" maxlength="8" width="72" />
						</tr></table>
					</td>
					<td noWrap="nowrap" align=right><span id="pxsc" style="FONT-SIZE: 12px">总培训天数</span>&nbsp;</td>
					<td >
						<table><tr>
							<odin:textEdit property="zxs1"  maxlength="8" width="72" />
							<td><span style="font: 12px">至</span></td>
							<odin:textEdit property="zxs2" maxlength="8" width="72" />
						</tr></table>
					</td>
					<td noWrap="nowrap" align=right><span id="pxsc" style="FONT-SIZE: 12px">总学时</span>&nbsp;</td>
					<td >
						<table><tr>
							<odin:textEdit property="xs1"  maxlength="8" width="72" />
							<td><span style="font: 12px">至</span></td>
							<odin:textEdit property="xs2" maxlength="8" width="72" />
						</tr></table>
					</td>
						<odin:dateEdit property="scpxsj" label="上次中长期培训早于"
		 maxlength="8" selectOnFocus="true"
		format="Y-m-d" onkeyup="odin.commInputMask(this,'Y-m-d')" />
<%-- 					<odin:textEdit property="scpxsj" label="上次中长期培训早于" maxlength="6" /> --%>
					<odin:textEdit property="scpxsc" label="学&nbsp;&nbsp;&nbsp;制" maxlength="6" />
<!-- 					<td colspan="2" align="center"><label style="FONT-SIZE: 12px">具有乡镇（街道）党政正职经历</label><input name="a0188" id="a0188" type="checkbox" value="1" /> </td>
					
 -->				</tr>

				<odin:hidden property="a0188"/>
				
<%-- 				<tr>
					<odin:select2 property="a0144age" label="党龄" data="['3', '三年以上'],['5', '五年以上']" ></odin:select2>
					<tags:ComBoxWithTree property="a0194c" label="重要任职经历" readonly="true" ischecked="true" width="160" codetype="ATTR_LRZW"  />
					<td colspan="2" align="center"><label style="FONT-SIZE: 12px">现任乡镇（街道）党（工）委书记</label><input name="a0132" id="a0132" type="checkbox" value="1" /> </td>
				</tr>
				<tr id="tr_h">
					<odin:select2 property="a0196z" label="专业类型" codeType="EXTRA_TAGS" multiSelect="true"></odin:select2>
					<odin:select2 property="a0196c" label="两头干部" codeType="EXTRA_A0196C" multiSelect="true"></odin:select2>
					<td colspan="2" align="center"><label style="FONT-SIZE: 12px">现任乡镇（街道）镇长（主任）</label>
					<input name="a0133" id="a0133" type="checkbox" value="1" /> </td>
				</tr> --%>
				<odin:hidden property="a0196z"/>
				<odin:hidden property="a0196c"/>
				<odin:hidden property="a0133"/>
				<odin:hidden property="a0144age"/>
				<odin:hidden property="a0194c"/>
				<odin:hidden property="a0132"/>
				
<%-- 				<tr>
					<odin:textEdit property="a1701" label="简历（包含）"  />
					<tags:ComBoxWithTree  property="a1705" label="重点岗位" readonly="true" ischecked="true" width="160" codetype="JL02"></tags:ComBoxWithTree>
										
 					<tags:ComBoxWithTree property="zdgwq" label="重点岗位" readonly="true" ischecked="true" width="160" codetype="ZDGWBQ"></tags:ComBoxWithTree>
 					<tags:ComBoxWithTree property="a1706" label="分管工作类型" readonly="true" ischecked="true" width="160" codetype="EXTRA_TAGS"></tags:ComBoxWithTree>
				</tr>
				<tr>
					<odin:select2 property="sfwxr" label="简历类型" data="['01', '现任'],['02', '历任']" ></odin:select2>
					<tags:ComBoxWithTree property="newRZJL" label="任职经历（新）" codetype="RZJL2" readonly="true" ischecked="true" width="160"></tags:ComBoxWithTree>
					<tags:ComBoxWithTree property="A0194_TAG" label="熟悉领域" codetype="SXLY2" readonly="true" ischecked="true" width="160"></tags:ComBoxWithTree>
				</tr> --%>
				<odin:hidden property="a1701"/>
				<odin:hidden property="a1705"/>
				<odin:hidden property="zdgwq"/>
				<odin:hidden property="a1706"/>
				<odin:hidden property="sfwxr"/>
				<odin:hidden property="newRZJL"/>
				<odin:hidden property="A0194_TAG"/>
				
<%-- 				<tr id="tr_h">
					<td colspan="2" align="center"><label style="FONT-SIZE: 12px">是否为选调生</label>
					<input name="a99z103" id="a99z103" type="checkbox" value="1" /> </td>
					<td noWrap="nowrap" align=right><span id="a99z104SpanId" style="FONT-SIZE: 12px">选调为选调生时间</span>&nbsp;</td>
					<td >
						<table><tr>
							<odin:NewDateEditTag property="a99z104A"  maxlength="6" width="72" />
							<td><span style="font: 12px">至</span></td>
							<odin:NewDateEditTag property="a99z104B" maxlength="6" width="72" />
						</tr></table>
					</td>
				</tr> --%>
				<odin:hidden property="a99z103"/>
				<odin:hidden property="a99z104A"/>
				<odin:hidden property="a99z104B"/>
				
			</table>
		</div>
			
<!-- 	<table id="ltb">
		<tr>
			<td style="width: 20px"></td>
			
						
		</tr>
	</table> -->
	<div style="float: left;width: 100%;height:550px">
			<odin:editgrid2 property="ryGrid" hasRightMenu="false" bbarId="pageToolBar" pageSize="50"  width="300" height="530" isFirstLoadData="false" pageSize="200" url="/">
				<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
						<odin:gridDataCol name="checked"/>
						<odin:gridDataCol name="a0000" />		
						<odin:gridDataCol name="a0101" />
						<odin:gridDataCol name="a0104" />
						<odin:gridDataCol name="a0165" />
						<odin:gridDataCol name="zgxl" />
						<odin:gridDataCol name="b0101" />
						<odin:gridDataCol name="a0288" />
						<odin:gridDataCol name="a0184" />
						<odin:gridDataCol name="a0107" />
						<odin:gridDataCol name="zxs" />
						<odin:gridDataCol name="zts" />
						<odin:gridDataCol name="a0192a" isLast="true" />
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
						<odin:gridRowNumColumn></odin:gridRowNumColumn>
						<odin:gridEditColumn2 dataIndex="a0000"  header="人员主键" hidden="true" edited="false"  editor="text"/> 
						<odin:gridEditColumn2 dataIndex="a0101"   width="50"   header="姓名" editor="text"  edited="false" align="center"   />
						<odin:gridEditColumn2 dataIndex="a0104"   width="20"   header="性别" editor="text"  edited="false" align="center"   />
						<odin:gridEditColumn2 dataIndex="a0107"  header="出生年月" width="50" edited="false" align="center"  editor="text"/> 
						<odin:gridEditColumn2 dataIndex="a0165"  header="管理类别" width="80" edited="false"  editor="text"  />
						<odin:gridEditColumn2 dataIndex="b0101"  header="任职机构" width="80" edited="false"  editor="text"  />
						<odin:gridEditColumn2 dataIndex="a0192a"  header="任现职务" width="140" edited="false"  editor="text"  /> 
						<odin:gridEditColumn2 dataIndex="a0288" width="50" header="任现职务<br/>层次时间" editor="text" edited="false" align="center"/>
						<odin:gridEditColumn2 dataIndex="a0184" width="70" header="身份证号" editor="text" edited="false" align="center"/>
						<odin:gridEditColumn2 dataIndex="zgxl"  header="最高学历" width="50" edited="false" align="center"  editor="text" /> 
						<odin:gridEditColumn2 dataIndex="zts"  header="合计天数" width="50" edited="false" align="center"  editor="text"/> 
						<odin:gridEditColumn2 dataIndex="zxs"  header="合计学时" width="50" edited="false" align="center"  editor="text" isLast="true"/> 
					</odin:gridColumnModel>
				</odin:editgrid2>
	<!-- <table id="autowidth">
		<tr>
			<th></th>
			<th  style="display:none">a0111</th>
		    <th style="width:50">姓名</th>
		    <th style="width:50">出生年月</th>
		    <th style="width:100">任现工作单位及职务</th>
		</tr>
	</table> -->
</div>

	<div id="bottomDiv" style="width: 100%;">
		<table style="width: 100%; background-color: #cedff5">
			<tr align="center">
				<td>
					<table>
						<tr>
							<td><odin:button text="清除条件" property="clearCon2" handler="clearConbtn"></odin:button>
							</td>
							<td align="center"><odin:button text="开始查询" property="mQuery" handler="dosearch"/></td>
							<td align="center"><odin:button text="导出名单" property="confim" handler="confim"/></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>
</div>	


<odin:hidden property="fxyp00"/>
<odin:hidden property="data"/>
<input type="reset" name="reset" id="resetBtn" style="display: none;" />
<odin:hidden property="sql"/>
<script type="text/javascript">

Ext.onReady(function(){
	window.onresize=resizeframe;
	resizeframe();
})


function tfckbox(checkboxName,hiddenName){
	var checkboxes = document.getElementsByName(checkboxName);
	var hiddenValue = "";
	for (i=0; i<checkboxes.length; i++) {  
        if (checkboxes[i].checked) {  
        	hiddenValue = hiddenValue + checkboxes[i].value+',';
        }  
    }
	if(hiddenValue.length>0){
		hiddenValue = hiddenValue.substring(0,hiddenValue.length-1);
	}
	document.getElementById(hiddenName).value = hiddenValue;
}

function dosearch(){
	document.getElementById("SysOrgTreeIds").value=Ext.util.JSON.encode(doQuery());
	/* tfckbox('xla0801b','xla0801bv');
	tfckbox('xwa0901b','xwa0901bv');
	tfckbox('a0601','a0601v'); */
	var param;
	radow.doEvent('mQueryonclick',param);
}

function clearConbtn(condi){
	if(condi!==true){
		condi = "clear";
	}
//	Ext.getCmp("a0141_combotree").clearCheck();
//	Ext.getCmp("a0221A_combotree").clearCheck();
//	Ext.getCmp("a0192e_combotree").clearCheck();
//	Ext.getCmp("a0194c_combotree").clearCheck();
//	Ext.getCmp("zdgwq_combotree").clearCheck();
//	Ext.getCmp("a1706_combotree").clearCheck();
//	Ext.getCmp("A0194_TAG_combotree").clearCheck();
//	Ext.getCmp("newRZJL_combotree").clearCheck();
	document.getElementById("resetBtn").click();
	radow.doEvent('clearReset');
	document.getElementById("fxyp00").value=parent.Ext.getCmp(subWinId).initialConfig.fxyp00;
	radow.doEvent('initX',condi);
}



function collapseGroupWin(){

<%-- 	document.getElementById("fxyp00").value="<% out.print(new java.text.SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new Date())); %>"; --%>
	radow.doEvent('ryGrid.dogridquery');
	//window.close();
}




function resizeframe(){
	var conditionArea = document.getElementById("conditionArea");
	var treediv = document.getElementById("tree-div");
	var jbDiv = document.getElementById("jbDiv");
	var viewSize = Ext.getBody().getViewSize();
//	conditionArea.style.width = viewSize.width-230;
	conditionArea.style.height = viewSize.height;
	jbDiv.style.height = viewSize.height/7;
//	var pos = $h.pos(document.getElementById("ltb"));
	//alert(viewSize.height-pos.top);
	//document.getElementById("bottomDiv").style.marginTop = viewSize.height - pos.top-62;
	treediv.style.height = viewSize.height*0.95;
//	treediv.style.height = tree.style.height ;
	//alert(conditionArea.parentNode.parentNode.style.width);
	conditionArea.parentNode.parentNode.style.width=viewSize.width;
}


function reloadtree(){
	var treep = Ext.getCmp('group');
	var rootNode = treep.getRootNode();
	rootNode.reload();
	rootNode.expand();
}

function gbmcQueryBtn(){
	$h.openPageModeWin('gbmcQueryMntp','pages.customquery.gbmcQuery_mntp','干部查询列表',650,480,'','<%=request.getContextPath()%>',window);
}

/* function Add(data){
	$.each(data, function (i,item) {
		var num=length;
		var tr;
		tr = $('<tr><td style="display:none"></td><td><input role="checkbox" type="checkbox"></td><td style="width:50"></td><td style="width:50"></td><td style="width:100"></td></tr>');	
		var tds = $("td", tr);
		SetTDtext(tds[0],item["a0111"]);
		SetTDtext(tds[2],item["a0101"]);
		SetTDtext(tds[3],item["a0107"]);
		SetTDtext(tds[4],item["a0192a"]);
		$('#autowidth').append(tr);
	});
} */


function SetTDtext(td,v) {
	  $(td).html((v==""||v==null||v=="null"||v=="0"||v=="-1"||v=="-2"||v=="-3"||v=="-4")?" ":v.replace(/\n/g,"<br/>"));
	}
	
function checkboxs() {
	var checkboxs = $("#tbody").find("input[type='checkbox']");
	          var isChecked = $(this).is(":checked");
	          //严禁使用foreach，jq对象的遍历会使浏览器崩溃
	          for(var i = 0; i < checkboxs.length; i++) {
	              //临时变量，必须，否则只能选中最后一条记录
	            var temp = i;
	              $(checkboxs[temp]).prop("checked",isChecked);
	      }
	}
	
function confim() {
    var excelName = null;
    //excel导出名称的拼接
    var pgrid = Ext.getCmp('ryGrid');
    var row = pgrid.getSelectionModel().getSelections();
    var dstore = pgrid.getStore();
    var num = dstore.getTotalCount();
    var length = dstore.getCount();
    if (length == 0) {
        $h.alert('系统提示：', '没有要导出的数据！', null, 180);
        return;
    }
/*     odin.grid.menu.expExcelFromGrid('peopleInfoGrid', excelName, null,null, false); */

/* 
        excelName = "人员信息" + "_" + excelName
        + "_" + Ext.util.Format.date(new Date(), "Ymd");  */
    excelName ='模拟排班人员名单';

	odin.grid.menu.expExcelFromGrid('ryGrid', excelName, null,null, false);
	/* radow.doEvent("expExcelFromGrid"); */
}
</script>


<%@include file="/pages/customquery/otjs.jsp" %>

