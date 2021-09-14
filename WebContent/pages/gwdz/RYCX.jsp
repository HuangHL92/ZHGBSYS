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


<div style="float: left;position: relative;width: 15%">
	<table  cellspacing="0" cellpadding="0">
		<tr style="background-color: #cedff5">
			<td style="padding-left: 30"> <input type="checkbox" id="continueCheckbox" onclick="continueChoose()"><font style="font-size: 13">连续选择</font></td>
			<td style="padding-left: 30"><input type="checkbox" id="existsCheckbox" onclick="existsChoose()" ><font style="font-size: 13">包含下级</font></td>
		</tr>
		<tr>
			<td colspan="2">
				<div id="tree-div" style="overflow: auto;  border: 2px solid #c3daf9; height: 200px;"></div>
				<odin:hidden property="codevalueparameter" />
				<odin:hidden property="SysOrgTreeIds" value="{}"/>
			</td>
		</tr>
	</table>
</div>

<div  style="float: left;width: 60%">
<div id="conditionArea" style="height: 300; overflow-y: scroll;">
	<odin:groupBoxNew title="基本" property="ggBox" contentEl="jbDiv" collapsible="false">
		<div id="jbDiv">
			<table style="height: 530">
				<tr>
					<odin:textEdit property="a0101" label="人员姓名" maxlength="36" />
					
					<odin:select2 property="a0163" label="人员状态" codeType="ZB126"></odin:select2>
					<odin:select2 property="b0131A" label="机构类别" data="['01', '市直党委部门'],['02', '市直政府部门'],['03', '区县市党委部门'],['04', '区县市政府部门']" multiSelect="true"></odin:select2>
										
				</tr>
				<tr>
					<odin:select2 property="a0165A" label="省管干部" data="['02', '省管干部'],['05', '省管委托市管']" multiSelect="true"></odin:select2>
					<odin:select2 property="a0165B" label="市管干部" data="['10', '市管正职'],['11', '市管副职'],['18','市直党组（党委）成员、二巡'],['19', '市管职级公务员（含退出领导岗位）'],['04', '市管其他']" multiSelect="true"></odin:select2>
					<odin:select2 property="a0165C" label="处级（中层）干部" data="['12', '市直正处'],['50', '市直副处'],['07', '区管正职'],['08', '区管副职'],['09', '县市管正职'],['13', '县市管副职'],['51','市属副局级企业副职'],['60','市属副局级企业中层副职'],['52','其他']" multiSelect="true"></odin:select2>
				</tr>
				<tr>
					<td noWrap="nowrap" align=right><span id=sexSpanId style="FONT-SIZE: 12px">性&nbsp;&nbsp;&nbsp;别</span>&nbsp;</td>
					<td colspan="1" style="FONT-SIZE: 12px">
						<label><input id="a01040" name="a0104" type="radio" value="0" />全部</label>
						<label><input id="a01041" name="a0104" type="radio" value="1" />男 </label> 
						<label><input id="a01042" name="a0104" type="radio" value="2" />女 </label> 
					</td>
					
					<td noWrap="nowrap" align=right><span id="a0117SpanId" style="FONT-SIZE: 12px">民&nbsp;&nbsp;&nbsp;族</span>&nbsp;</td>
					<td colspan="1" style="FONT-SIZE: 12px">
						<label><input id="a01170" name="a0117" type="radio" value="0" />全部</label>
						<label><input id="a01171" name="a0117" type="radio" value="1" />汉族 </label> 
						<label><input id="a01172" name="a0117" type="radio" value="2" />少数民族 </label> 
					</td>
					<tags:ComBoxWithTree property="a0141" label="党&nbsp;&nbsp;&nbsp;派" readonly="true" ischecked="true" width="160" codetype="GB4762" />
					
				</tr>
				
				<tr>
					
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
					
					<odin:NewDateEditTag  isCheck="true" property="jiezsj" maxlength="6"  label="年龄年限计算截止"></odin:NewDateEditTag>
					
				</tr>
				
				<tr>
					<odin:select2 property="xlxw" label="学历学位" data="['博士', '博士'],['硕士', '硕士'],['研究生', '研究生'],['大学,本科','大学' ],['大专,专科,高中,中技,中专,小学,初中', '大专及以下']" multiSelect="true"></odin:select2>
					<td><label style="FONT-SIZE: 12px">全日制</label><input id="qrz" name="qrz" type="checkbox" value="1" /> </td>
					<td></td>
					
					<odin:textEdit property="a0824" label="专业（包含）"  />
				</tr>
				
				<tr>
					<tags:ComBoxWithTree property="a0221A" label="现职务层次" readonly="true" ischecked="true" width="160" codetype="ZB09" />
					
					<td noWrap="nowrap" align=right><span id="a0288SpanId" style="FONT-SIZE: 12px">任现职务层次时间</span>&nbsp;</td>
					<td >
						<table><tr>
							<odin:NewDateEditTag property="a0288A"  maxlength="8" width="72" />
							<td><span style="font: 12px">至</span></td>
							<odin:NewDateEditTag property="a0288B" maxlength="8" width="72" />
						</tr></table>
					</td>
					<td noWrap="nowrap" align=right><span id="a0192fSpanId" style="FONT-SIZE: 12px">任现职时间</span>&nbsp;</td>
					<td >
						<table><tr>
							<odin:NewDateEditTag property="a0192fA"  maxlength="8" width="72" />
							<td><span style="font: 12px">至</span></td>
							<odin:NewDateEditTag property="a0192fB" maxlength="8" width="72" />
						</tr></table>
					</td>
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
					
<!-- 					<td colspan="2" align="center"><label style="FONT-SIZE: 12px">具有乡镇（街道）党政正职经历</label><input name="a0188" id="a0188" type="checkbox" value="1" /> </td>
					
 -->				</tr>
 				<tr>
					<odin:textEdit property="scpxsc" label="上次培训时长" maxlength="6" />
					<odin:textEdit property="scpxsj" label="上次培训结束时间早于" maxlength="6" />
					
				</tr>
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
	</odin:groupBoxNew>
			
	<table id="ltb">
		<tr>
			<td style="width: 20px"></td>
			
						
		</tr>
	</table>
	
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
							<td align="center"><odin:button text="确认选择" property="confim" handler="confim"/></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</div>

</div>

<div style="float: left;width: 25%;">
			<odin:editgrid2 property="ryGrid" hasRightMenu="false" bbarId="pageToolBar" pageSize="50"  width="300" height="670" isFirstLoadData="false" pageSize="200" url="/">
				<odin:gridJsonDataModel  id="id" root="data" totalProperty="totalCount">
						<odin:gridDataCol name="checked"/>
						<odin:gridDataCol name="a0000" />		
						<odin:gridDataCol name="a0101" />
						<odin:gridDataCol name="a0107" />
						<odin:gridDataCol name="a0192a" isLast="true" />
					</odin:gridJsonDataModel>
					<odin:gridColumnModel>
						<odin:gridRowNumColumn></odin:gridRowNumColumn>
						<odin:gridEditColumn2 locked="true" header="selectall" width="30" editor="checkbox" dataIndex="checked"  edited="true"/>
						<odin:gridEditColumn2 dataIndex="a0000"  header="人员主键" hidden="true" edited="false"  editor="text"/> 
						<odin:gridEditColumn2 dataIndex="a0101"   width="50"   header="姓名" editor="text"  edited="false" align="center"   />
						<odin:gridEditColumn2 dataIndex="a0107"  header="出生年月" width="50" edited="false" align="center"  editor="text"/> 
						<odin:gridEditColumn2 dataIndex="a0192a"  header="任现职务" width="130" edited="false"  editor="text" isLast="true" /> 
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
	Ext.getCmp("a0141_combotree").clearCheck();
	Ext.getCmp("a0221A_combotree").clearCheck();
	Ext.getCmp("a0192e_combotree").clearCheck();
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
	var viewSize = Ext.getBody().getViewSize();
	conditionArea.style.width = viewSize.width-240;
	conditionArea.style.height = viewSize.height-36;
	var pos = $h.pos(document.getElementById("ltb"));
	//alert(viewSize.height-pos.top);
	//document.getElementById("bottomDiv").style.marginTop = viewSize.height - pos.top-62;
	treediv.style.height = viewSize.height-25;
	
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
	 var grid = Ext.getCmp('ryGrid');
     var store = grid.store;
     var a0000list='';
     for(var i =0;i<store.getCount();i++){
     	var record=store.getAt(i);
     	var data=record.get('checked');
     	if(data==true){
     		a0000list+=record.get('a0000');
     		a0000list+=',';
     	}
     }
      if(a0000list==null || a0000list==''){	
 		$h.alert('系统提示：','请选择人员！',null,150);
 		return;	
 	} 
    
    realParent.callback(a0000list);
}
</script>


<%@include file="/pages/customquery/otjs.jsp" %>

