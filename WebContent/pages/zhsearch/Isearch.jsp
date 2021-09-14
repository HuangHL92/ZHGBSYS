<%@page import="java.util.List"%>
<%@page import="com.insigma.siis.local.pagemodel.customquery.CommSQL"%>
<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%
String qtype = request.getParameter("qt");
String qid = request.getParameter("qid");
%>

<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/pingyin.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comboxWithTree.js"></script>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/basejs/ext/ux/css/LockingGridView.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/qdstyle.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/ext/ux/LockingGridView.js"></script>
  
<%@page
	import="com.insigma.siis.local.pagemodel.search.ComSearchPageModel"%>
<%@page
	import="com.insigma.siis.local.pagemodel.sysorg.org.SysOrgPageModel"%>
<%@page
	import="com.insigma.siis.local.pagemodel.sysmanager.group.GroupManagePageModel"%>
<%@page import="com.insigma.siis.local.business.helperUtil.SysManagerUtils"%>
<%@page import="com.insigma.odin.framework.persistence.HBUtil"%>
<%@page import="com.insigma.siis.local.pagemodel.customquery.ShowControl"%>
<%@page import="javax.servlet.http.HttpSession"%>


<%
String pageSize = "fy";
if(session.getAttribute("pageSize") != null && !session.getAttribute("pageSize").equals("")){
	 pageSize = session.getAttribute("pageSize").toString(); 				 //判断是否设置了自定义每页数量，如果设置了使用自定义    
}
ShowControl showControl = new ShowControl();
String pps_isuseful = showControl.getPpsResult();
%>
<%
	String areaname = (String) new GroupManagePageModel().areaInfo
			.get("areaname");
	String ctxPath = request.getContextPath();
	 //机构查询
	String groupID = request.getParameter("groupID");
	String userid = SysManagerUtils.getUserId();
	CommSQL.initA01_config(userid);
	List<Object[]> gridDataCollist = CommSQL.A01_CONFIG_LIST.get(userid);
	System.out.print(gridDataCollist);
	int size = gridDataCollist.size();
%>

<style>
.x-panel-body{
height: 100%
}
.x-panel-bwrap{
height: 100%
}
.top_btn_style{
 display: inline-block;
    padding: .3em .5em;
    background-color: #6495ED;
    border: 1px solid rgba(0,0,0,.2);
    border-radius: .3em;
    box-shadow: 0 1px white inset;
    text-align: center;
    text-shadow: 0 1px 1px black;
    color:white;
    font-weight: bold;
	cursor:pointer;
}
</style>
<!-- <div id="btnToolBarDiv" ></div> -->
<odin:hidden property="isContain2"/>  <%--单机构点击和多机构点击区分--%>
<odin:hidden property="a0201b" />
<odin:hidden property="fields" />
<odin:hidden property="orgjson"  />
<odin:hidden property="sql" />
<odin:hidden property="downfile" />
<odin:hidden property="a0000s"/>
<odin:hidden property="isContain" />
<odin:hidden property="radioC" />
<odin:hidden property="checkedgroupid" />
<odin:hidden property="checkList" />
<odin:hidden property="orderqueryhidden" />
<odin:hidden property="tabn" />
<odin:hidden property="showTabID"/>
<odin:hidden property="tableType" value="1"/>
<odin:hidden property="personq" value=""/>
<odin:hidden property="radioC1" value="1"/>
<odin:hidden property="qvid"/>
<odin:hidden property="picA0000s"/>
<odin:toolBar property="btnToolBar">
	<odin:fill />
	<odin:buttonForToolBar text="&nbsp;批量维护" icon="image/icon021a6.gif"  id="betchModifyBtn"  />
	<odin:separator />
	<odin:buttonForToolBar text="导出数据" id="expBtn" menu="expMenu" icon="images/icon/table.gif" cls="x-btn-text-icon"></odin:buttonForToolBar>
	<odin:separator /> 
	<odin:buttonForToolBar text="打印任免表" id="printBtn" 
	menu="printMenu" icon="images/icon/printer.gif" cls="x-btn-text-icon"></odin:buttonForToolBar>
	<odin:separator /> 
	<odin:buttonForToolBar text="干部审核" id="cadresAuditBtn" 
	menu="cadresAuditMenu" icon="images/icon/query.gif" cls="x-btn-text-icon" isLast="true"></odin:buttonForToolBar>	
</odin:toolBar>


<div id="groupTreeContent" style="height: 100%; padding-top: 0px;">
<table width="100%" cellspacing="0px" cellpadding="0px;" style="margin-top: 0px;">
		<tr>
			<td width="270" id="td1">
		 		<table class="x-form-item2" style="width: 100%;background-color: rgb(209,223,245);border-right: 1px solid rgb(153,187,232);">
					<tr>
						<tags:ComBoxWithTree property="personq"  label="" required="false" disabled="false" codetype="ZB126" width="150" nodeDblclick="nodeclick"/>
						
						
						
					</tr>
				</table>

				<table width="270"  cellspacing="0px" cellpadding="0px;" style="margin-top: 0px;border-collapse:collapse;height:100%;">
					<tr>
							<td valign="top">
								<odin:tab id="tab" width="270" height="512" tabchange="grantTabChange">
								<odin:tabModel>
				       				<odin:tabItem title="&nbsp&nbsp查询&nbsp&nbsp" id="tab2" isLast="true"></odin:tabItem>
								</odin:tabModel>

								<odin:tabCont itemIndex="tab2" className="tab">
									<div id="divtab2" style="overflow: hidden; overflow-x: hidden; position: absolute; height: 340px; width: 270; float: none;">
									<!-- 	 <table  id='pBottom' style="width: 100%; background-color: #cedff5;font-size: 12px;">
											<tr>
												<td align="center"><label><input name="radioC" id="radioC1" type="radio" value="1" />全库查询</label> </td>
												<td align="left"><label><input name="radioC" id="radioC2" type="radio" value="2" />二次查询</label></td>								
											</tr>
											<tr>
												<td align="center"><label><input name="radioC" id="radioC3" type="radio" value="3" />追加查询</label></td>
												<td align="left"><label><input name="radioC" id="radioC4" type="radio" value="4" />剔除查询</label></td>
											</tr>
										</table> -->
										<br>
										<br>
										<table style="width: 100%;">
											<tr>
												<td style="width: 80px"></td>
												<td><input class="bluebutton bigbutton-customquery" type="button" value="姓名/身份证查询" onclick="queryByName()"></td>
											</tr>
										</table>
										<br>
										<br>
										<table style="width: 100%;">
											<tr>
												<td style="width: 80px"></td>
												<td><input class="bluebutton bigbutton-customquery" type="button" value="常用条件查询" onclick="groupQuery()"></td>
												<!-- <td><input class="bluebutton bigbutton-customquery" type="button" value="自定义查询" onclick="userDefined()"></td> -->
											</tr>
										</table>
										<br>
										<br>
										<table style="width: 100%;">
											<tr>
												<td style="width: 80px"></td>
												<td><input class="bluebutton bigbutton-customquery" type="button" value="自定义查询" onclick="userDefined2()"></td>
											</tr>
										</table>
										<br>
										<br>
										<table style="width: 100%;">
											<tr>
												<td style="width: 80px"></td>
												<td><input class="bluebutton bigbutton-customquery" type="button" value="查询列表" onclick="openSql1()"></td>
												
											</tr>
										</table>

								</odin:tabCont>

							</odin:tab></td>



					</tr>
				</table>
			</td>
			<td style="height: 100%; width: 7px;">
			<div onclick="abcde(this)" id="divresize" style="cursor:pointer; height:100%;width:7px;background:#D6E3F2 url(image/right.png) no-repeat center center;"></div>
			</td>
			<td>
				
					<div id="girdDiv" style="width: 100%;height: 100%;margin:0px 0px 0px 0px;" >
		<table  cellspacing="0px" cellpadding="0px;" style="margin-top: 0px;width: 100%">
			<tr>
				<td><odin:editgrid2 property="peopleInfoGrid" enableColumnHide="false" load="refreshPerson"
					autoFill="false" locked="true" width="100%" height="500" bbarId="pageToolBar" hasRightMenu="false" remoteSort="true" cellmousedown="rowClickPeople"
					pageSize="20" topBarId="btnToolBar">
					<odin:gridJsonDataModel>
						<odin:gridDataCol name="personcheck"  />
						<odin:gridDataCol name="a0000"/>
						<%
						
							int i = 0;
							for(Object[] o : gridDataCollist){
								String name = o[0].toString();
								i++;
								if(i==size ){
									%>
									<odin:gridDataCol name="<%=name %>" isLast="true" />
									<%
								}else{
									%>
									<odin:gridDataCol name="<%=name %>"/>
									<%
								}
								
							}
						%>
					</odin:gridJsonDataModel>
					<odin:gridColumnModel2 >
						<odin:gridRowNumColumn2></odin:gridRowNumColumn2>
						 <odin:gridEditColumn2 locked="true" header="selectall" width="40"
							editor="checkbox" dataIndex="personcheck" edited="true"
							hideable="false" gridName="persongrid" 
							checkBoxClick="getCheckList2" checkBoxSelectAllClick="getCheckList" />
							
						<%
							int i = 0;
							for(Object[] o : CommSQL.A01_CONFIG_LIST.get(userid)){
								String name = o[0].toString();
								String editor = o[5].toString().toLowerCase();
								String header = o[2].toString();
								String desc = o[6].toString();
								String width = o[3].toString();
								String codeType = o[4]==null?"":o[4].toString();
								String renderer = o[7]==null?"":o[7].toString();
								System.err.println(renderer);
								String align = o[9].toString();
								boolean locked =false;
								if("a0101".equals(name)){
									locked = true;
								}
								i++;
								if(!"a0000".equals(name)){
									if(i==size ){
										%>
										<odin:gridEditColumn2 dataIndex="<%=name %>" width="<%=width %>" header="<%=header %>"
											 align="<%=align %>" editor="<%=editor %>" edited="false" codeType="<%=codeType %>"
											renderer="<%=renderer %>"   isLast="true"/>
										
										<%
										
									}else{
										%>
										<odin:gridEditColumn2 dataIndex="<%=name %>" width="<%=width %>" header="<%=header %>"
											align="<%=align %>" editor="<%=editor %>" locked="<%=locked %>" edited="false" codeType="<%=codeType %>"
											renderer="<%=renderer %>"/>
										<%
									}
								}
								
							}
						%>
					</odin:gridColumnModel2>
					<odin:gridJsonData>
	{
        data:[]
    }
</odin:gridJsonData>
				</odin:editgrid2>
				</td>
			</tr>
		</table>
		</div>
			</td>
		</tr>
	</table>
</div>


		<odin:hidden property="SysOrgTreeIds"/>
<script type="text/javascript">
function nodeclick(node,e){
	//console.log(node);
	document.getElementById('personq').value = node.attributes.id;
	document.getElementById('personq_combotree').value = node.attributes.selectText;
	//$('#personq').val(node.attributes.ntype);
}
function expBtnword(){
	radow.doEvent("expBtnwordonclick");
}
function zipBtn(){
	radow.doEvent("importHzbBtn","7z");
}
function hzbBtn(){
	radow.doEvent("importHzbBtn","hzb");
}

function view(){
    <%-- $h.openPageModeWin('WebOffice','pages.weboffice.ViewOffice','历史纪录',870,435,'','<%=request.getContextPath()%>');   --%>
    var iWidth = 200;     
    var iHeight = 400;        
    var top = (window.screen.height-30-iHeight)/2;;       //获得窗口的垂直位置;
    var left = (window.screen.width-10-iWidth)/2;   //获得窗口的水平位置;
<%--     window.open( '<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.weboffice.ExpOffice', '导出', 'height=' + iHeight + ',width=' + iWidth + ',left='+left+',top='+top+', toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no, titlebar=yes, alwaysRaised=yes'); --%>
		 window.showModalDialog('<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.weboffice.ExpOffice',window,'dialogWidth:280px; dialogHeight:400px; status:no;directories:yes;scrollbars:no;resizable:no;help:no');
}

/* 导出任免表时 增加进度条 */
function expLrmGrid(){
	ShowCellCover("start","温馨提示：","正在生成任免表...");
	radow.doEvent('exportLrmBtn');
}
function expLrmxGrid(){
	ShowCellCover("start","温馨提示：","正在生成任免表...");
	radow.doEvent('exportLrmxBtn');
}
var ctxPath = '<%=ctxPath%>';
<odin:menu property="printMenu">
<odin:menuItem text="打印" property="printRmb"  handler="print" ></odin:menuItem>
<odin:menuItem text="打印机设置" property="printSet"  isLast="true"  handler="printSet" ></odin:menuItem>
</odin:menu>

<odin:menu property="expWord">
<odin:menuItem text="干部任免审批表" property="exp1"  handler="expWord1" ></odin:menuItem>
<odin:menuItem text="公务员登记表" property="exp2"  handler="expWord2" ></odin:menuItem>
<odin:menuItem text="参照公务员登记表" property="exp3"  handler="expWord3" ></odin:menuItem>
<odin:menuItem text="公务员登记备案表" property="exp4"  handler="expWord4" ></odin:menuItem>
<odin:menuItem text="参照公务员登记备案表" property="exp5"  handler="expWord5" ></odin:menuItem>
<odin:menuItem text="公务员年度考核登记表" property="exp6"  handler="expWord6" ></odin:menuItem>
<odin:menuItem text="奖励审批表" property="exp7"  handler="expWord7" ></odin:menuItem>
<odin:menuItem text="公务员录用表" property="exp10"  handler="expWord10" ></odin:menuItem>
<odin:menuItem text="公务员调任审批表" property="exp11"  handler="expWord11" ></odin:menuItem>
<odin:menuItem text="干部花名册（一人一行）" property="exp8"  handler="expWord8" ></odin:menuItem>
<odin:menuItem text="干部花名册（按机构分组）" property="exp9"  handler="expWord9" isLast="true"></odin:menuItem>
</odin:menu>

<odin:menu property="expMenu">
<odin:menuItem text="导出Word" menu = "expWord" ></odin:menuItem>
/* <odin:menuItem text="导出任免表Word" property="exportGBDocBtn" ></odin:menuItem> */
<odin:menuItem text="导出任免表Lrmx" property="exportLrmxBtn" handler="expLrmxGrid"></odin:menuItem>
/* <odin:menuItem text="导出全部数据" property="getAll" handler="expExcelFromGrid"></odin:menuItem> */
<odin:menuItem text="导出任免表Lrm" property="exportLrmBtn" handler="expLrmGrid"></odin:menuItem>
/* <odin:menuItem text="导出任免表PDF" property="exportPdfBtn" ></odin:menuItem> */
<odin:menuItem text="导出任免表PDF" property="exportPdfForAspose"></odin:menuItem>
<odin:menuItem text="导出个人数据HZB" property="importHzbBtn" handler="hzbBtn"></odin:menuItem>
<%-- <odin:menuItem text="导出个人数据Zip" property="importZipBtn" handler="zipBtn1"></odin:menuItem> --%>
<odin:menuItem text="导出个人数据7z" property="import7zBtn" handler="zipBtn"></odin:menuItem>
/* <odin:buttonForToolBar text="表册导出" id="expBtnword" menu="expMenuword"   icon="images/icon/table.gif" cls="x-btn-text-icon" ></odin:buttonForToolBar> */
<odin:menuItem text="导出自定义表册" property="importExpButton" handler="expBtnword" isLast="true"></odin:menuItem>
</odin:menu>

/* 干部审核 */
<odin:menu property="cadresAuditMenu">
<odin:menuItem text="干部处审核" property="cadresTTFAudit" handler="cadresTTFAudit"></odin:menuItem>
<odin:menuItem text="干部一处审核" property="cadresOAudit" handler="cadresOAudit"></odin:menuItem>
<odin:menuItem text="取消干部处审核" property="unLockGBC" handler="unLockGBC"></odin:menuItem>
<odin:menuItem text="取消干部一处审核" property="unLockGBYC" handler="unLockGBYC"></odin:menuItem>
<odin:menuItem text="取消全部审核" property="unLockAudit" handler="unLockAll" isLast="true"></odin:menuItem>
</odin:menu>

function expWord1(){
	/* var sql=document.getElementById("sql").value;
	if(sql==""){
		$h.alert('系统提示：','请双击机构树查询！',null,180);
		return;
	} */
	
	var gridId = "peopleInfoGrid";
	var grid = Ext.getCmp(gridId);
	var total = grid.getStore().getCount();//数据行数
	if(total==0){
		Ext.MessageBox.alert('提示','请先进行查询!');
		return;
	}
	var store = grid.getStore();
	var length = store.getCount();
	if(length==0){
		$h.alert('系统提示：','请先选择记录！',null,150);
		return;
	}
	radow.doEvent("chuidrusesson");
	radow.doEvent("expword","1");
}
function expWord2(){
	/* var sql=document.getElementById("sql").value;
	if(sql==""){
		$h.alert('系统提示：','请双击机构树查询！',null,180);
		return;
	} */
	
	var gridId = "peopleInfoGrid";
	var grid = Ext.getCmp(gridId);
	var total = grid.getStore().getCount();//数据行数
	if(total==0){
		Ext.MessageBox.alert('提示','请先进行查询!');
		return;
	}
	var store = grid.getStore();
	var length = store.getCount();
	if(length==0){
		$h.alert('系统提示：','请先选择记录！',null,150);
		return;
	}
	radow.doEvent("chuidrusesson");
	radow.doEvent("expword","2");
}

function expWord3(){
	/* var sql=document.getElementById("sql").value;
	if(sql==""){
		$h.alert('系统提示：','请双击机构树查询！',null,180);
		return;
	} */
	
	var gridId = "peopleInfoGrid";
	var grid = Ext.getCmp(gridId);
	var total = grid.getStore().getCount();//数据行数
	if(total==0){
		Ext.MessageBox.alert('提示','请先进行查询!');
		return;
	}
	var store = grid.getStore();
	var length = store.getCount();
	if(length==0){
		$h.alert('系统提示：','请先选择记录！',null,150);
		return;
	}
	radow.doEvent("chuidrusesson");
	radow.doEvent("expword","3");
}

function expWord4(){
	/* var sql=document.getElementById("sql").value;
	if(sql==""){
		$h.alert('系统提示：','请双击机构树查询！',null,180);
		return;
	} */
	
	var gridId = "peopleInfoGrid";
	var grid = Ext.getCmp(gridId);
	var total = grid.getStore().getCount();//数据行数
	if(total==0){
		Ext.MessageBox.alert('提示','请先进行查询!');
		return;
	}
	var store = grid.getStore();
	var length = store.getCount();
	if(length==0){
		$h.alert('系统提示：','请先选择记录！',null,150);
		return;
	}
	radow.doEvent("chuidrusesson");
	radow.doEvent("expword","4");
}

function expWord5(){
	/* var sql=document.getElementById("sql").value;
	if(sql==""){
		$h.alert('系统提示：','请双击机构树查询！',null,180);
		return;
	} */
	
	var gridId = "peopleInfoGrid";
	var grid = Ext.getCmp(gridId);
	var total = grid.getStore().getCount();//数据行数
	if(total==0){
		Ext.MessageBox.alert('提示','请先进行查询!');
		return;
	}
	var store = grid.getStore();
	var length = store.getCount();
	if(length==0){
		$h.alert('系统提示：','请先选择记录！',null,150);
		return;
	}
	radow.doEvent("chuidrusesson");
	radow.doEvent("expword","5");
}

function expWord6(){
	/* var sql=document.getElementById("sql").value;
	if(sql==""){
		$h.alert('系统提示：','请双击机构树查询！',null,180);
		return;
	} */
	
	var gridId = "peopleInfoGrid";
	var grid = Ext.getCmp(gridId);
	var total = grid.getStore().getCount();//数据行数
	if(total==0){
		Ext.MessageBox.alert('提示','请先进行查询!');
		return;
	}
	var store = grid.getStore();
	var length = store.getCount();
	if(length==0){
		$h.alert('系统提示：','请先选择记录！',null,150);
		return;
	}
	radow.doEvent("chuidrusesson");
	radow.doEvent("expword","6");
}

function expWord7(){
	/* var sql=document.getElementById("sql").value;
	if(sql==""){
		$h.alert('系统提示：','请双击机构树查询！',null,180);
		return;
	} */
	
	var gridId = "peopleInfoGrid";
	var grid = Ext.getCmp(gridId);
	var total = grid.getStore().getCount();//数据行数
	if(total==0){
		Ext.MessageBox.alert('提示','请先进行查询!');
		return;
	}
	var store = grid.getStore();
	var length = store.getCount();
	if(length==0){
		$h.alert('系统提示：','请先选择记录！',null,150);
		return;
	}
	radow.doEvent("chuidrusesson");
	radow.doEvent("expword","7");
}

function expWord8(){
	/* var sql=document.getElementById("sql").value;
	if(sql==""){
		$h.alert('系统提示：','请双击机构树查询！',null,180);
		return;
	} */
	
	var gridId = "peopleInfoGrid";
	var grid = Ext.getCmp(gridId);
	var total = grid.getStore().getCount();//数据行数
	if(total==0){
		Ext.MessageBox.alert('提示','请先进行查询!');
		return;
	}
	var store = grid.getStore();
	var length = store.getCount();
	if(length==0){
		$h.alert('系统提示：','请先选择记录！',null,150);
		return;
	}
	radow.doEvent("chuidrusesson");
	radow.doEvent("expword","8");
}

function expWord9(){
	/* var sql=document.getElementById("sql").value;
	if(sql==""){
		$h.alert('系统提示：','请双击机构树查询！',null,180);
		return;
	} */
	
	var gridId = "peopleInfoGrid";
	var grid = Ext.getCmp(gridId);
	var total = grid.getStore().getCount();//数据行数
	if(total==0){
		Ext.MessageBox.alert('提示','请先进行查询!');
		return;
	}
	var store = grid.getStore();
	var length = store.getCount();
	if(length==0){
		$h.alert('系统提示：','请先选择记录！',null,150);
		return;
	}
	radow.doEvent("chuidrusesson");
	radow.doEvent("expword","9");
}

function expWord10(){
	/* var sql=document.getElementById("sql").value;
	if(sql==""){
		$h.alert('系统提示：','请双击机构树查询！',null,180);
		return;
	} */
	
	var gridId = "peopleInfoGrid";
	var grid = Ext.getCmp(gridId);
	var total = grid.getStore().getCount();//数据行数
	if(total==0){
		Ext.MessageBox.alert('提示','请先进行查询!');
		return;
	}
	var store = grid.getStore();
	var length = store.getCount();
	if(length==0){
		$h.alert('系统提示：','请先选择记录！',null,150);
		return;
	}
	radow.doEvent("chuidrusesson");
	radow.doEvent("expword","10");
}

function expWord11(){
	/* var sql=document.getElementById("sql").value;
	if(sql==""){
		$h.alert('系统提示：','请双击机构树查询！',null,180);
		return;
	} */
	
	var gridId = "peopleInfoGrid";
	var grid = Ext.getCmp(gridId);
	var total = grid.getStore().getCount();//数据行数
	if(total==0){
		Ext.MessageBox.alert('提示','请先进行查询!');
		return;
	}
	var store = grid.getStore();
	var length = store.getCount();
	if(length==0){
		$h.alert('系统提示：','请先选择记录！',null,150);
		return;
	}
	radow.doEvent("chuidrusesson");
	radow.doEvent("expword","11");
}

function printSet(){
	//得到默认打印机
	var oShell = new ActiveXObject("WScript.Shell");
    var sRegVal = 'HKEY_CURRENT_USER\\Software\\Microsoft\\Windows NT\\CurrentVersion\\Windows\\Device';
    var sDefault = oShell.RegRead(sRegVal);
    var printer =sDefault.split(",")[0];
	/* radow.doEvent("printSetInit",printer); */
	
	//得到所有的打印机传到后端
    var nt = new ActiveXObject("WScript.Network"); //获取插件
    var oPrinters = nt.EnumPrinterConnections(); //需要编写 ActiveX 控件来获得d打印机列表
    if (oPrinters == null || oPrinters.length == 0)
    {
        alert('当前你的机器暂没安装打印机');
        return;
    } else
    {
        //alert('当前打印机台数:'+oPrinters.length);
    }
     
	var printers = "";
    for (i = 0; i < oPrinters.length; i += 2)
    {

        var name = oPrinters.Item(i + 1);
        if(i==0){
        	printers=name;
        }else{
        	printers=printers+"|@|"+name;
        }
    }
    var param = printer+"&%"+printers;
	$h.openWin('printSetWin','pages.publicServantManage.PrintSet','打印机设置',500,180,param,ctxPath);
}
//打印
function print(){
	//("http://127.0.0.1:8080/hzb/ziploud/57bee0d0ec4940119742e007e5015113/expFiles_20180514110455/1_江  海.doc");
	var gridId = "peopleInfoGrid";
	var grid = Ext.getCmp(gridId);
	//var grid = Ext.getCmp('peopleInfoGrid');
	var total = grid.getStore().getCount();//数据行数
	if(total==0){
		Ext.MessageBox.alert('提示','请先进行查询!');
		return;
	}
	/* var sql=document.getElementById("sql").value;
	if(sql==""){
		$h.alert('系统提示：','请双击机构树查询！',null,180);
		return;
	} */
	var store = grid.getStore();
	var length = store.getCount();
	if(length==0){
		$h.alert('系统提示：','请先选择记录！',null,150);
		return;
	}
	try{ 
		var wdapp = new ActiveXObject("Word.Application");
  	}catch(e){ 
  		$helper.alertActiveX();
	    return; 
  	}  
  	Ext.MessageBox.wait('正在打印中，请稍后。。。');
	radow.doEvent("chuidrusesson");
	/* radow.doEvent("printRmb","1");   */
	ajaxSubmit("printRmb","1");
	
}

function printStart(path){
	var url=window.location.protocol+"//"+window.location.host+"/hzb/";
	path = url+path;
	//Ext.MessageBox.hide();
	var wdapp = new ActiveXObject("Word.Application");
    wdapp.Documents.Open(path);//打开word模板url
	wdapp.Application.Printout();
}
function ajaxSubmit(radowEvent,parm){
	if(parm){
	}else{
		parm = {};
	}
	Ext.Ajax.request({
		method: 'POST',
        async: true,
        params : {},
        timeout :300000,//按毫秒计算
		url: "<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.customquery.CustomQuery&eventNames="+radowEvent,
		success: function(resData){
			//alert(resData.responseText);
			var cfg = Ext.util.JSON.decode(resData.responseText);
			if(0==cfg.messageCode){
				Ext.Msg.hide();	
				
				if(cfg.elementsScript.indexOf("\n")>0){
					cfg.elementsScript = cfg.elementsScript.replace(/\r/gi,"");
					cfg.elementsScript = cfg.elementsScript.replace(/\n/gi,"\\n");
				}
				
				//console.log(cfg.elementsScript);
				eval(cfg.elementsScript);
				//var realParent = parent.Ext.getCmp("setFields").initialConfig.thisWin;
				//parent.document.location.reload();
				//alert(cfg.elementsScript);
				//realParent.resetCM(cfg.elementsScript);
				//parent.Ext.getCmp("setFields").close();
				//console.log(cfg.mainMessage);
				if("操作成功！"!=cfg.mainMessage){
					Ext.Msg.alert('系统提示:',cfg.mainMessage);
				}
			}else{
				//Ext.Msg.hide();	
				
				if(cfg.mainMessage.indexOf("<br/>")>0){
					
					$h.alert('系统提示',cfg.mainMessage,null,380);
					return;
				}
				
				if("操作成功！"!=cfg.mainMessage){
					Ext.Msg.alert('系统提示:',cfg.mainMessage);
				}
			}
			
		},
		failure : function(res, options){ 
			Ext.Msg.hide();
			alert("网络异常！");
		}  
	});
}
function load(){
	radow.doEvent("peopleInfoGrid.dogridquery");
}
function getCheckList2(num){
	var grid = Ext.getCmp('peopleInfoGrid');
	var listString = document.getElementById("checkList").value;
	var sign = 0;
	if("" == listString){
		sign = 1;//说明是首次修改
	}
	
	var a0000 = "";
	var personcheck = grid.store.getAt(num).get('personcheck');
	//先对checkBox进行操作,如果是"true",改为"false";如果是"false",改为"true"
	//changeCheckBox(personcheck,num);
	
	var personid = grid.store.getAt(num).get('a0000');
	var peopleName = grid.store.getAt(num).get('a0101');
	a0000 = personid;
	//true 说明将要勾选这个人员
	if(personcheck){
		listString=listString+"@|"+personid+"|";
	}
	if(!personcheck){
		if(listString.length == 38){
			listString = listString.replace("|"+personid+"|","");
		}else{
			/* 确保清除取消勾选的a0000 */
			listString = listString.replace("@|"+personid+"|","");
			listString = listString.replace("|"+personid+"|@","");
		}
	}
	if(sign == 1){
		listString=listString.substring(listString.indexOf("@")+1,listString.length);
	}
	document.getElementById("checkList").value = listString;
	
	document.getElementById("a0000s").value = a0000;
	//changeTabId(peopleName);
}

function getCheckList(gridId,fieldName,obj){
	//x-grid3-check-col
	if("x-grid3-check-col"==obj.className){
		var objs = Ext.query(".x-grid3-check-col-on");
		for(var i=0;i<objs.length;i++){
			objs[i].className = "x-grid3-check-col";
		}
	}else{
		var objs = Ext.query(".x-grid3-check-col");
		for(var i=0;i<objs.length;i++){
			objs[i].className = "x-grid3-check-col-on";
		}
	}
	radow.doEvent('getCheckList');
}
function reloadTree(){
	setTimeout(xx,1000);
}
function xx(){
	var downfile = document.getElementById('downfile').value;
	/* w = window.open("ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile))); */
	window.location.href="ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile));
	ShowCellCover("","温馨提示","导出成功！");
	setTimeout(cc,3000);
}
function ShowCellCover(elementId, titles, msgs)
{	
	Ext.MessageBox.buttonText.ok = "关闭";
	if(elementId.indexOf("start") != -1){
	
		Ext.MessageBox.show({
			title:titles,
			msg:msgs,
			width:300,
	        height:300,
			closable:false,
		//	buttons: Ext.MessageBox.OK,		
			modal:true,
			progress:true,
			wait:true,
			animEl: 'elId',
			increment:5, 
			waitConfig: {interval:150}
			//,icon:Ext.MessageBox.INFO        
		});
	}else if(elementId.indexOf("success") != -1){
			Ext.MessageBox.confirm("系统提示", msgs, function(but) {  
				
			}); 
	}else if(elementId.indexOf("failure") != -1){
			Ext.MessageBox.show({
				title:titles,
				msg:msgs,
				width:300,
				modal:true,
		        height:300,
				closable:true,
				//icon:Ext.MessageBox.INFO,
				buttons: Ext.MessageBox.OK		
			});
			/*
			setTimeout(function(){
					Ext.MessageBox.hide();
			}, 2000);
			*/
	}else {
			Ext.MessageBox.show({
				title:titles,
				msg:msgs,
				width:300,
				modal:true,
		        height:300,
				closable:true,
				//icon:Ext.MessageBox.INFO,
				buttons: Ext.MessageBox.OK		
			});
		}
}

function cc(){
	
}
//保存干部属性的条件
function setAttributeFields(param){
	document.getElementById("fields").value = param;
}

//人员列表单击事件 
function rowClickPeople(a,index){
	//getCheckList3(index);
}
function resetCM(cmConfig,dmConfig){
	var cfg = cmConfig.split("{split}");
	var grid = Ext.getCmp('peopleInfoGrid');
	//var cm = grid.getColumnModel();
	//alert(cmConfig);
	//cm.setConfig(eval(cfg[0]));
	//var lastOptions = grid.getStore().lastOptions;
	var reader =  new Ext.data.JsonReader({root: 'data',totalProperty: 'totalCount',id: 'id'}, eval(cfg[1]));
	var gridData = {data:[]};
	
	var ds = new Ext.data.Store({reader: reader,baseParams: {cueGridId:'peopleInfoGrid'},data: gridData,proxy:new Ext.data.MemoryProxy(gridData),remoteSort:true});
	var colModel = new Ext.ux.grid.LockingColumnModel(eval(cfg[0]));
	grid.reconfigure(ds,colModel);//重新生成列模型和数据对象。
	//ds.load(lastOptions);
	
	var bbar = grid.getBottomToolbar();
	bbar.bind(ds);//重新绑定分页栏数据对象
	
	var sql=document.getElementById("sql").value;
	var qvid=document.getElementById("qvid").value;
	if(sql==""&&qvid==""){
		return;
	};
	
	radow.doEvent("peopleInfoGrid.dogridquery");
	  
	
	
	return;
}
/* sql查询   青 岛 */
function openSql1(){
	$h.openPageModeWin('searchListWin','pages.zhsearch.searchList','查询列表',460,400,'',contextPath,null,{maximizable:false,resizable:false});
}
function groupQuery(){
	radow.doEvent("initListAddGroupFlag");
	//var newWin_ = $h.getTopParent().Ext.getCmp('group');
	//if(!newWin_){
		$h.openPageModeWin('group','pages.customquery.Group','组合查询',1200,720,'',contextPath,null,
				{modal:true,collapsed:false,collapsible:false,titleCollapse:false,maximized:false});
	//}else{
	//	newWin_.expand(true); 
	//}
	//radow.util.openWindow('group','pages.customquery.Group');
}
function queryByName(){
	radow.doEvent("initListAddGroupFlag");
	$h.openWin('findById','pages.customquery.CustomQueryByName','按姓名/身份证查询 ',1020,520,null,contextPath,null,{maximizable:false,resizable:false});
	//radow.util.openWindow('findById','pages.customquery.CustomQueryByName');
} 

function AttributeQuery(){
	$h.openWin('AttributeWin','pages.zhsearch.AttributeQuery','干部属性查询 ',1000,600,null,contextPath,null,{maximizable:false,resizable:false});
}
function clearFields(){
	document.getElementById("fields").value="";
}

function refreshPerson(){
	
	var a0201b = document.getElementById("a0201b").value;
	
	if(a0201b == null || a0201b == ''){
		return;
	}
	
	
	var tableType = document.getElementById("tableType").value;
	
	
	if(tableType == 1){								//列表
		
		showgrid()
	}
	
	
	if(tableType == 2){								//小资料
		//radow.doEvent("ShowData");
		datashow()
	}
	
	if(tableType == 3){								//照片
		//radow.doEvent("Show");
		picshow()
	}
	
}
function querypep(name){
	 if(name==""){
		 return;
	 }
	 radow.doEvent("pic.dbclick",name);
}
Ext.onReady(function(){
	var pgrid = Ext.getCmp('peopleInfoGrid');
	var bbar = pgrid.getBottomToolbar();
	//找不到event的错误  功能未影响  
	try{
		bbar.insertButton(11,[
			new Ext.menu.Separator({cls:'xtb-sep'}),
			new Ext.Button({
				icon : 'images/keyedit.gif',
				id:'setPageSize',
			    text:'设置每页条数',
			    handler:setPageSize1
			}),
			
			new Ext.Button({
				icon : 'images/keyedit.gif',
				id:'setFields',
			    text:'设置显示字段',
			    handler:setFields
			}),
			new Ext.Button({
				icon : 'images/icon/table.gif',
				id:'getAll',
			    text:'导出Excel',
			    handler:expExcelFromGrid
			}),
			new Ext.Button({
				icon : 'image/u53.png',
				id:'sortHand',
			    text:'手动排序',
			    handler:sortHand
			}),
			new Ext.Button({
				icon : 'image/u53.png',
				id:'saveSortBtn',
			    text:'临时排序',
			    handler:sort
			}),
		]); 
	}catch(err){
		
	}

});
//手动排序 ,设置自定义排序字段
function sortHand(){
	var url = contextPath + "/pages/customquery/A01SortConfig.jsp?";
	$h.showWindowWithSrc("sortHand",url,"设置排序", 400, 600);
}

//临时排序 
function sort(){
	$h.openWin('Sort','pages.publicServantManage.Sort','临时排序 ',560,680,document.getElementById('tableType').value,ctxPath);
}


//设置每页条数
function setPageSize1(){
	var gridId = 'peopleInfoGrid';
	if (!Ext.getCmp(gridId)) {
		odin.error("要导出的grid不存在！gridId=" + gridId);
		return;
	}
	var grid = Ext.getCmp(gridId);
	var store = grid.getStore();
	if (store.getCount() == 0) {
	}
	var pageingToolbar = (grid.getBottomToolbar() || grid.getTopToolbar());
	if (pageingToolbar && pageingToolbar.pageSize) {
		
		gridIdForSeting = gridId;
		var url = contextPath + "/sys/comm/commSetGrid.jsp";
		doOpenPupWin(url, "设置每页条数", 300, 150);
		//$h.openWin('policyListWin',url,'政策法规',1050,550,'',ctxPath);
		
		
	} else {
		odin.error("非分页grid不能使用此功能！");
		return;
	}
}
//设置显示字段
function setFields(){
	var url = contextPath + "/pages/customquery/A01FieldsConfig.jsp?";
	//doOpenPupWin(url, "设置显示字段", 400, 550);
	$h.showWindowWithSrc("setFields",url,"设置显示字段", 400, 730);
}
//导出Excel
function expExcelFromGrid(){
	
	
	/* var sql=document.getElementById("sql").value;
	var qvid=document.getElementById("qvid").value;
	if(sql=="" && qvid==""){
		$h.alert('系统提示：','请进行查询！',null,180);
		return;
	} */
	
	
	var excelName = null;
	
	//excel导出名称的拼接    
	var pgrid = Ext.getCmp('peopleInfoGrid');
	var dstore = pgrid.getStore();
	
	var num = dstore.getTotalCount()
	var length = dstore.getCount();
	if(length==0){
		$h.alert('系统提示：','没有要导出的数据！',null,180);
		return;
	}
	if(num != 0){
		//获得列表第一个人
		excelName = dstore.getAt(0).get('a0101');
		if(typeof excelName=='undefined' || excelName== null ||excelName==''){
			excelName = dstore.getAt(0).get('a01a0101');
		}
		if(num > 1){
			excelName = excelName + "等" + num +"人";
		}
	}
	
	excelName = "人员信息" + "_" + excelName
	+ "_" + Ext.util.Format.date(new Date(), "Ymd");
	
	odin.grid.menu.expExcelFromGrid('peopleInfoGrid', excelName, null,null, false);
	
}
//按姓名查询
function queryByNameAndIDS(list){
	radow.doEvent("initListAddGroupFlag");
	radow.doEvent('queryByNameAndIDS',list);
}
function changeField(){
	
	Ext.Ajax.request({
		method: 'POST',
        async: true,
        params : {'jsonp':""},
        //timeout :30000,//按毫秒计算
		url: "<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.zhsearch.Isearch&eventNames=saveA01_config",
		success: function(resData){
			var cfg = Ext.util.JSON.decode(resData.responseText);
			if(0==cfg.messageCode){
				resetCM(cfg.elementsScript);
			}else{
				Ext.Msg.alert('系统提示',cfg.mainMessage)
			}
			
				
		},
		failure : function(res, options){ 
			Ext.Msg.hide();
			alert("网络异常！");
		}  
	});
}
/* 自定义查询  青 岛 */
function userDefined2(a,b,c,cid){
	var subWinIdBussessId2 = "";
	if(cid){
		subWinIdBussessId2 = cid;
	}
	<%--$h.openWin('win1','pages.customquery.QueryConditionEdit','自定义查询',1250,600,subWinIdBussessId2,'<%=ctxPath%>');--%>
	//新的自定义查询
	$h.openPageModeWin('win1','pages.cadremgn.infmtionquery.UserDefinedQuery','自定义查询',1250,604,subWinIdBussessId2,'<%=ctxPath%>');
}
var type="1"
Ext.onReady(function(){
	 var pgrid = Ext.getCmp('peopleInfoGrid');
	var viewSize = Ext.getBody().getViewSize();
	pgrid.setHeight(viewSize.height+14);
	pgrid.setWidth(viewSize.width-261);

	
	document.getElementById('personq').value = '1';
	document.getElementById('personq_combotree').value = '现职人员';
	
});

function init(){
	var qtype = '<%=qtype %>';
	var qid = '<%=qid %>';
	//alert(qtype);
	if(qtype!=null && qtype!='' && qid!=null && qid!=''){
		if(qtype=='1'){
			radow.doEvent("cQueryById",qid);
		} else if(qtype=='2'){
			//alert(qid);
			document.getElementById("qvid").value = qid;
			//radow.doEvent("query_config");
			Ext.Ajax.request({
				method: 'POST',
			    async: true,
			    params : {'qvid':qid},
			    //timeout :300000,//按毫秒计算
				url: "<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.zhsearch.Isearch&eventNames=query_config",
				success: function(resData){
					//var sql = eval("("+resData.responseText+")");
					var cfg = Ext.util.JSON.decode(resData.responseText);
					if(0==cfg.messageCode){
						document.getElementById('sql').value='';
						//alert(11111);
						//document.getElementById("SysOrgTreeIds").value=Ext.util.JSON.encode(doQuery());
						//alert(22222);
						Ext.getCmp('peopleInfoGrid').show();
						//alert(33333);
						resetCM(cfg.elementsScript);
					}else{
						Ext.Msg.alert('系统提示',cfg.mainMessage)
					}
				},
				failure : function(res, options){ 
					Ext.Msg.hide();
					alert("网络异常！");
				}  
			});
		}
	}
	return;
	if(type==1){
		queryByName();
	}else if(type==2){
		groupQuery();
	}else if(type==3){
		userDefined2();
	}else if(type==4){
		openSql1();
	}else if(type==5){
		AttributeQuery();
	}
}

function cadresTTFAudit(){
	/* if(""==$("#sql").val()){
		Ext.MessageBox.alert('提示','请双击机构树查询!');
		return;
	} */
	var grid = Ext.getCmp('peopleInfoGrid');
	var total = grid.getStore().getCount();//数据行数
	if(total==0){
		Ext.MessageBox.alert('提示','请先进行查询!');
		return;
	}
	var a0000s = getChechA0000S();
	if(""==a0000s){
		Ext.MessageBox.alert("提示","请选择人员!");
	} else{
		radow.doEvent('cadresTTFAudit',a0000s);
	}
}
function cadresOAudit(){
	/* if(""==$("#sql").val()){
		Ext.MessageBox.alert('提示','请双击机构树查询!');
		return;
	} */
	var grid = Ext.getCmp('peopleInfoGrid');
	var total = grid.getStore().getCount();//数据行数
	if(total==0){
		Ext.MessageBox.alert('提示','请先进行查询!');
		return;
	}
	var a0000s = getChechA0000S();
	if(""==a0000s){
		Ext.MessageBox.alert("提示","请选择人员!");
	} else{
		radow.doEvent('cadresOAudit',a0000s);
	}
}


function unLockGBC(){
	unLock(1)
}
function unLockGBYC(){
	unLock(2)
}
function unLockAll(){
	unLock('all')
}

function unLock(f){
	/* if(""==$("#sql").val()){
		Ext.MessageBox.alert('提示','请双击机构树查询!');
		return;
	} */
	var grid = Ext.getCmp('peopleInfoGrid');
	var total = grid.getStore().getCount();//数据行数
	if(total==0){
		Ext.MessageBox.alert('提示','请先进行查询!');
		return;
	}
	var a0000s = getChechA0000S();
	if(""==a0000s){
		Ext.MessageBox.alert("提示","请选择人员!");
	} else{
		radow.doEvent('unLockAudit',f+","+a0000s);
	}
}

//获取选中人员
function getChechA0000S(){
	var grid = Ext.getCmp('peopleInfoGrid');
	
	var total = grid.getStore().getCount();//数据行数
	var record; //行数据
	var a0000s = ""; //拟任干部名单
	
	for(var i=0; i<total; i++){
		record = grid.getStore().getAt(i);
		if(true==record.get('personcheck')){
			a0000s += record.get('a0000')+"@#@";
		}
	}
	
	return a0000s;
}

function sList(qvid){
	
	//radow.doEvent("query_config");
	Ext.Ajax.request({
		method: 'POST',
	    async: true,
	    params : {'qvid':qvid},
	    //timeout :300000,//按毫秒计算
		url: "<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.zhsearch.Isearch&eventNames=query_config",
		success: function(resData){
			var sql = eval("("+resData.responseText+")");
			var cfg = Ext.util.JSON.decode(resData.responseText);
			if(0==cfg.messageCode){
				document.getElementById('sql').value='';
				//alert(11111);
				//document.getElementById("SysOrgTreeIds").value=Ext.util.JSON.encode(doQuery());
				//alert(22222);
				Ext.getCmp('peopleInfoGrid').show();
				//alert(33333);
				resetCM(cfg.elementsScript);
			}else{
				Ext.Msg.alert('系统提示',cfg.mainMessage)
			}
		},
		failure : function(res, options){ 
			Ext.Msg.hide();
			alert("网络异常！");
		}  
	});
	


	
}

    var doAddPerson = (function () {
        var obj = null;
        return {

            queryByNameAndIDS: function (list,sub) {//按姓名查询
			document.getElementById("checkedgroupid").value = sub;
                //if(obj){
                radow.doEvent('queryByNameAndIDS', list);
                //}
            }

        }
    })();

    
    var flag_ss=false;

    function abcde(obj){
    	document.getElementById("groupTreeContent").parentNode.style.width=document.body.clientWidth+'px';
    	   if(flag_ss==false){
    		 	//收缩
    		 	document.getElementById("td1").style.display="none";
    	   		/* document.getElementById("divtab2").firstChild.style.width=1;//查询列表宽度设置
    	        var tree = Ext.getCmp('group');
    	        //tree的宽度设置
    	        tree.setWidth(1);
    	        //tab的宽度设置
    	        var resizeobj =Ext.getCmp('tab');
    			resizeobj.setWidth(1); */
    			
    			flag_ss=true;//隐藏标志
    			//document.getElementById(obj.id).innerHTML='>';
    			document.getElementById(obj.id).style.background="url(image/left.png) #D6E3F2 no-repeat center center";
    			var grid=Ext.getCmp('peopleInfoGrid');
    	    	grid.setWidth(10);//缩小girdDiv宽度(使div可以自动缩小，自适应宽度)
    	    	var width=document.getElementById("girdDiv").offsetWidth;//获取当前div宽度
    	    	grid.setWidth(width);//重置grid的宽度
    	    	
    	    	var grid_width=grid.getWidth();
    	    	//50 150 300 400 200
    	    	//动态设置列宽
    	    	/* grid.colModel.setColumnWidth(0,grid_column_0*grid_width,'');//第0列
    	    	//grid.colModel.setColumnWidth(1,grid_column_1*grid_width,'');//第1列
    	    	grid.colModel.setColumnWidth(2,grid_column_2*grid_width,'');//第2列
    	    	grid.colModel.setColumnWidth(3,grid_column_3*grid_width,'');//第3列
    	    	grid.colModel.setColumnWidth(4,grid_column_4*grid_width,'');//第4列
    	    	grid.colModel.setColumnWidth(5,grid_column_5*grid_width,'');//第5列
    	    	grid.colModel.setColumnWidth(6,grid_column_6*grid_width,'');//第6列
    	    	grid.colModel.setColumnWidth(7,grid_column_7*grid_width,'');//第7列
    	    	grid.colModel.setColumnWidth(8,grid_column_8*grid_width,'');//第8列 */
    	    	//grid.colModel.setColumnWidth(9,grid_column_9*grid_width,'');//第9列
    	   }else{ //伸展开
    			
    			/*  document.getElementById("divtab2").firstChild.style.width=250;//查询列表宽度设置
    		     var tree = Ext.getCmp('group')
    		     //tree的宽度设置
    		     tree.setWidth(250);
    		     //tab的宽度设置
    		     var resizeobj =Ext.getCmp('tab');
    			 resizeobj.setWidth(250); */
    			 document.getElementById("td1").style.display="block";
    			 
    			 flag_ss=false;//伸展开标志
    			 //document.getElementById(obj.id).innerHTML='<span><</span>';
    			 document.getElementById(obj.id).style.background="url(image/right.png) #D6E3F2 no-repeat center center";
    			var grid=Ext.getCmp('peopleInfoGrid');
    	    	grid.setWidth(10);//缩小girdDiv宽度(使div可以自动缩小，自适应宽度)
    	    	var width=document.getElementById("girdDiv").offsetWidth;//获取当前div宽度
    	    	grid.setWidth(width);//重置grid的宽度
    	    	
    	    	var grid_width=grid.getWidth();
    	    	//50 150 300 400 200
    	    	//动态设置列宽
    	    	/* grid.colModel.setColumnWidth(0,grid_column_0*grid_width,'');//第0列
    	    	//grid.colModel.setColumnWidth(1,grid_column_1*grid_width,'');//第1列
    	    	grid.colModel.setColumnWidth(2,grid_column_2*grid_width,'');//第2列
    	    	grid.colModel.setColumnWidth(3,grid_column_3*grid_width,'');//第3列
    	    	grid.colModel.setColumnWidth(4,grid_column_4*grid_width,'');//第4列
    	    	grid.colModel.setColumnWidth(5,grid_column_5*grid_width,'');//第5列
    	    	grid.colModel.setColumnWidth(6,grid_column_6*grid_width,'');//第6列
    	    	grid.colModel.setColumnWidth(7,grid_column_7*grid_width,'');//第7列
    	    	grid.colModel.setColumnWidth(8,grid_column_8*grid_width,'');//第8列 */
    	    	//grid.colModel.setColumnWidth(9,grid_column_9*grid_width,'');//第9列
    	   }
    }	

</script>
