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
	 pageSize = session.getAttribute("pageSize").toString(); 				 //�ж��Ƿ��������Զ���ÿҳ���������������ʹ���Զ���    
}
ShowControl showControl = new ShowControl();
String pps_isuseful = showControl.getPpsResult();
%>
<%
	String areaname = (String) new GroupManagePageModel().areaInfo
			.get("areaname");
	String ctxPath = request.getContextPath();
	 //������ѯ
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
<odin:hidden property="isContain2"/>  <%--����������Ͷ�����������--%>
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
	<odin:buttonForToolBar text="&nbsp;����ά��" icon="image/icon021a6.gif"  id="betchModifyBtn"  />
	<odin:separator />
	<odin:buttonForToolBar text="��������" id="expBtn" menu="expMenu" icon="images/icon/table.gif" cls="x-btn-text-icon"></odin:buttonForToolBar>
	<odin:separator /> 
	<odin:buttonForToolBar text="��ӡ�����" id="printBtn" 
	menu="printMenu" icon="images/icon/printer.gif" cls="x-btn-text-icon"></odin:buttonForToolBar>
	<odin:separator /> 
	<odin:buttonForToolBar text="�ɲ����" id="cadresAuditBtn" 
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
				       				<odin:tabItem title="&nbsp&nbsp��ѯ&nbsp&nbsp" id="tab2" isLast="true"></odin:tabItem>
								</odin:tabModel>

								<odin:tabCont itemIndex="tab2" className="tab">
									<div id="divtab2" style="overflow: hidden; overflow-x: hidden; position: absolute; height: 340px; width: 270; float: none;">
									<!-- 	 <table  id='pBottom' style="width: 100%; background-color: #cedff5;font-size: 12px;">
											<tr>
												<td align="center"><label><input name="radioC" id="radioC1" type="radio" value="1" />ȫ���ѯ</label> </td>
												<td align="left"><label><input name="radioC" id="radioC2" type="radio" value="2" />���β�ѯ</label></td>								
											</tr>
											<tr>
												<td align="center"><label><input name="radioC" id="radioC3" type="radio" value="3" />׷�Ӳ�ѯ</label></td>
												<td align="left"><label><input name="radioC" id="radioC4" type="radio" value="4" />�޳���ѯ</label></td>
											</tr>
										</table> -->
										<br>
										<br>
										<table style="width: 100%;">
											<tr>
												<td style="width: 80px"></td>
												<td><input class="bluebutton bigbutton-customquery" type="button" value="����/���֤��ѯ" onclick="queryByName()"></td>
											</tr>
										</table>
										<br>
										<br>
										<table style="width: 100%;">
											<tr>
												<td style="width: 80px"></td>
												<td><input class="bluebutton bigbutton-customquery" type="button" value="����������ѯ" onclick="groupQuery()"></td>
												<!-- <td><input class="bluebutton bigbutton-customquery" type="button" value="�Զ����ѯ" onclick="userDefined()"></td> -->
											</tr>
										</table>
										<br>
										<br>
										<table style="width: 100%;">
											<tr>
												<td style="width: 80px"></td>
												<td><input class="bluebutton bigbutton-customquery" type="button" value="�Զ����ѯ" onclick="userDefined2()"></td>
											</tr>
										</table>
										<br>
										<br>
										<table style="width: 100%;">
											<tr>
												<td style="width: 80px"></td>
												<td><input class="bluebutton bigbutton-customquery" type="button" value="��ѯ�б�" onclick="openSql1()"></td>
												
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
    <%-- $h.openPageModeWin('WebOffice','pages.weboffice.ViewOffice','��ʷ��¼',870,435,'','<%=request.getContextPath()%>');   --%>
    var iWidth = 200;     
    var iHeight = 400;        
    var top = (window.screen.height-30-iHeight)/2;;       //��ô��ڵĴ�ֱλ��;
    var left = (window.screen.width-10-iWidth)/2;   //��ô��ڵ�ˮƽλ��;
<%--     window.open( '<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.weboffice.ExpOffice', '����', 'height=' + iHeight + ',width=' + iWidth + ',left='+left+',top='+top+', toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no, titlebar=yes, alwaysRaised=yes'); --%>
		 window.showModalDialog('<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.weboffice.ExpOffice',window,'dialogWidth:280px; dialogHeight:400px; status:no;directories:yes;scrollbars:no;resizable:no;help:no');
}

/* ���������ʱ ���ӽ����� */
function expLrmGrid(){
	ShowCellCover("start","��ܰ��ʾ��","�������������...");
	radow.doEvent('exportLrmBtn');
}
function expLrmxGrid(){
	ShowCellCover("start","��ܰ��ʾ��","�������������...");
	radow.doEvent('exportLrmxBtn');
}
var ctxPath = '<%=ctxPath%>';
<odin:menu property="printMenu">
<odin:menuItem text="��ӡ" property="printRmb"  handler="print" ></odin:menuItem>
<odin:menuItem text="��ӡ������" property="printSet"  isLast="true"  handler="printSet" ></odin:menuItem>
</odin:menu>

<odin:menu property="expWord">
<odin:menuItem text="�ɲ�����������" property="exp1"  handler="expWord1" ></odin:menuItem>
<odin:menuItem text="����Ա�ǼǱ�" property="exp2"  handler="expWord2" ></odin:menuItem>
<odin:menuItem text="���չ���Ա�ǼǱ�" property="exp3"  handler="expWord3" ></odin:menuItem>
<odin:menuItem text="����Ա�ǼǱ�����" property="exp4"  handler="expWord4" ></odin:menuItem>
<odin:menuItem text="���չ���Ա�ǼǱ�����" property="exp5"  handler="expWord5" ></odin:menuItem>
<odin:menuItem text="����Ա��ȿ��˵ǼǱ�" property="exp6"  handler="expWord6" ></odin:menuItem>
<odin:menuItem text="����������" property="exp7"  handler="expWord7" ></odin:menuItem>
<odin:menuItem text="����Ա¼�ñ�" property="exp10"  handler="expWord10" ></odin:menuItem>
<odin:menuItem text="����Ա����������" property="exp11"  handler="expWord11" ></odin:menuItem>
<odin:menuItem text="�ɲ������ᣨһ��һ�У�" property="exp8"  handler="expWord8" ></odin:menuItem>
<odin:menuItem text="�ɲ������ᣨ���������飩" property="exp9"  handler="expWord9" isLast="true"></odin:menuItem>
</odin:menu>

<odin:menu property="expMenu">
<odin:menuItem text="����Word" menu = "expWord" ></odin:menuItem>
/* <odin:menuItem text="���������Word" property="exportGBDocBtn" ></odin:menuItem> */
<odin:menuItem text="���������Lrmx" property="exportLrmxBtn" handler="expLrmxGrid"></odin:menuItem>
/* <odin:menuItem text="����ȫ������" property="getAll" handler="expExcelFromGrid"></odin:menuItem> */
<odin:menuItem text="���������Lrm" property="exportLrmBtn" handler="expLrmGrid"></odin:menuItem>
/* <odin:menuItem text="���������PDF" property="exportPdfBtn" ></odin:menuItem> */
<odin:menuItem text="���������PDF" property="exportPdfForAspose"></odin:menuItem>
<odin:menuItem text="������������HZB" property="importHzbBtn" handler="hzbBtn"></odin:menuItem>
<%-- <odin:menuItem text="������������Zip" property="importZipBtn" handler="zipBtn1"></odin:menuItem> --%>
<odin:menuItem text="������������7z" property="import7zBtn" handler="zipBtn"></odin:menuItem>
/* <odin:buttonForToolBar text="��ᵼ��" id="expBtnword" menu="expMenuword"   icon="images/icon/table.gif" cls="x-btn-text-icon" ></odin:buttonForToolBar> */
<odin:menuItem text="�����Զ�����" property="importExpButton" handler="expBtnword" isLast="true"></odin:menuItem>
</odin:menu>

/* �ɲ���� */
<odin:menu property="cadresAuditMenu">
<odin:menuItem text="�ɲ������" property="cadresTTFAudit" handler="cadresTTFAudit"></odin:menuItem>
<odin:menuItem text="�ɲ�һ�����" property="cadresOAudit" handler="cadresOAudit"></odin:menuItem>
<odin:menuItem text="ȡ���ɲ������" property="unLockGBC" handler="unLockGBC"></odin:menuItem>
<odin:menuItem text="ȡ���ɲ�һ�����" property="unLockGBYC" handler="unLockGBYC"></odin:menuItem>
<odin:menuItem text="ȡ��ȫ�����" property="unLockAudit" handler="unLockAll" isLast="true"></odin:menuItem>
</odin:menu>

function expWord1(){
	/* var sql=document.getElementById("sql").value;
	if(sql==""){
		$h.alert('ϵͳ��ʾ��','��˫����������ѯ��',null,180);
		return;
	} */
	
	var gridId = "peopleInfoGrid";
	var grid = Ext.getCmp(gridId);
	var total = grid.getStore().getCount();//��������
	if(total==0){
		Ext.MessageBox.alert('��ʾ','���Ƚ��в�ѯ!');
		return;
	}
	var store = grid.getStore();
	var length = store.getCount();
	if(length==0){
		$h.alert('ϵͳ��ʾ��','����ѡ���¼��',null,150);
		return;
	}
	radow.doEvent("chuidrusesson");
	radow.doEvent("expword","1");
}
function expWord2(){
	/* var sql=document.getElementById("sql").value;
	if(sql==""){
		$h.alert('ϵͳ��ʾ��','��˫����������ѯ��',null,180);
		return;
	} */
	
	var gridId = "peopleInfoGrid";
	var grid = Ext.getCmp(gridId);
	var total = grid.getStore().getCount();//��������
	if(total==0){
		Ext.MessageBox.alert('��ʾ','���Ƚ��в�ѯ!');
		return;
	}
	var store = grid.getStore();
	var length = store.getCount();
	if(length==0){
		$h.alert('ϵͳ��ʾ��','����ѡ���¼��',null,150);
		return;
	}
	radow.doEvent("chuidrusesson");
	radow.doEvent("expword","2");
}

function expWord3(){
	/* var sql=document.getElementById("sql").value;
	if(sql==""){
		$h.alert('ϵͳ��ʾ��','��˫����������ѯ��',null,180);
		return;
	} */
	
	var gridId = "peopleInfoGrid";
	var grid = Ext.getCmp(gridId);
	var total = grid.getStore().getCount();//��������
	if(total==0){
		Ext.MessageBox.alert('��ʾ','���Ƚ��в�ѯ!');
		return;
	}
	var store = grid.getStore();
	var length = store.getCount();
	if(length==0){
		$h.alert('ϵͳ��ʾ��','����ѡ���¼��',null,150);
		return;
	}
	radow.doEvent("chuidrusesson");
	radow.doEvent("expword","3");
}

function expWord4(){
	/* var sql=document.getElementById("sql").value;
	if(sql==""){
		$h.alert('ϵͳ��ʾ��','��˫����������ѯ��',null,180);
		return;
	} */
	
	var gridId = "peopleInfoGrid";
	var grid = Ext.getCmp(gridId);
	var total = grid.getStore().getCount();//��������
	if(total==0){
		Ext.MessageBox.alert('��ʾ','���Ƚ��в�ѯ!');
		return;
	}
	var store = grid.getStore();
	var length = store.getCount();
	if(length==0){
		$h.alert('ϵͳ��ʾ��','����ѡ���¼��',null,150);
		return;
	}
	radow.doEvent("chuidrusesson");
	radow.doEvent("expword","4");
}

function expWord5(){
	/* var sql=document.getElementById("sql").value;
	if(sql==""){
		$h.alert('ϵͳ��ʾ��','��˫����������ѯ��',null,180);
		return;
	} */
	
	var gridId = "peopleInfoGrid";
	var grid = Ext.getCmp(gridId);
	var total = grid.getStore().getCount();//��������
	if(total==0){
		Ext.MessageBox.alert('��ʾ','���Ƚ��в�ѯ!');
		return;
	}
	var store = grid.getStore();
	var length = store.getCount();
	if(length==0){
		$h.alert('ϵͳ��ʾ��','����ѡ���¼��',null,150);
		return;
	}
	radow.doEvent("chuidrusesson");
	radow.doEvent("expword","5");
}

function expWord6(){
	/* var sql=document.getElementById("sql").value;
	if(sql==""){
		$h.alert('ϵͳ��ʾ��','��˫����������ѯ��',null,180);
		return;
	} */
	
	var gridId = "peopleInfoGrid";
	var grid = Ext.getCmp(gridId);
	var total = grid.getStore().getCount();//��������
	if(total==0){
		Ext.MessageBox.alert('��ʾ','���Ƚ��в�ѯ!');
		return;
	}
	var store = grid.getStore();
	var length = store.getCount();
	if(length==0){
		$h.alert('ϵͳ��ʾ��','����ѡ���¼��',null,150);
		return;
	}
	radow.doEvent("chuidrusesson");
	radow.doEvent("expword","6");
}

function expWord7(){
	/* var sql=document.getElementById("sql").value;
	if(sql==""){
		$h.alert('ϵͳ��ʾ��','��˫����������ѯ��',null,180);
		return;
	} */
	
	var gridId = "peopleInfoGrid";
	var grid = Ext.getCmp(gridId);
	var total = grid.getStore().getCount();//��������
	if(total==0){
		Ext.MessageBox.alert('��ʾ','���Ƚ��в�ѯ!');
		return;
	}
	var store = grid.getStore();
	var length = store.getCount();
	if(length==0){
		$h.alert('ϵͳ��ʾ��','����ѡ���¼��',null,150);
		return;
	}
	radow.doEvent("chuidrusesson");
	radow.doEvent("expword","7");
}

function expWord8(){
	/* var sql=document.getElementById("sql").value;
	if(sql==""){
		$h.alert('ϵͳ��ʾ��','��˫����������ѯ��',null,180);
		return;
	} */
	
	var gridId = "peopleInfoGrid";
	var grid = Ext.getCmp(gridId);
	var total = grid.getStore().getCount();//��������
	if(total==0){
		Ext.MessageBox.alert('��ʾ','���Ƚ��в�ѯ!');
		return;
	}
	var store = grid.getStore();
	var length = store.getCount();
	if(length==0){
		$h.alert('ϵͳ��ʾ��','����ѡ���¼��',null,150);
		return;
	}
	radow.doEvent("chuidrusesson");
	radow.doEvent("expword","8");
}

function expWord9(){
	/* var sql=document.getElementById("sql").value;
	if(sql==""){
		$h.alert('ϵͳ��ʾ��','��˫����������ѯ��',null,180);
		return;
	} */
	
	var gridId = "peopleInfoGrid";
	var grid = Ext.getCmp(gridId);
	var total = grid.getStore().getCount();//��������
	if(total==0){
		Ext.MessageBox.alert('��ʾ','���Ƚ��в�ѯ!');
		return;
	}
	var store = grid.getStore();
	var length = store.getCount();
	if(length==0){
		$h.alert('ϵͳ��ʾ��','����ѡ���¼��',null,150);
		return;
	}
	radow.doEvent("chuidrusesson");
	radow.doEvent("expword","9");
}

function expWord10(){
	/* var sql=document.getElementById("sql").value;
	if(sql==""){
		$h.alert('ϵͳ��ʾ��','��˫����������ѯ��',null,180);
		return;
	} */
	
	var gridId = "peopleInfoGrid";
	var grid = Ext.getCmp(gridId);
	var total = grid.getStore().getCount();//��������
	if(total==0){
		Ext.MessageBox.alert('��ʾ','���Ƚ��в�ѯ!');
		return;
	}
	var store = grid.getStore();
	var length = store.getCount();
	if(length==0){
		$h.alert('ϵͳ��ʾ��','����ѡ���¼��',null,150);
		return;
	}
	radow.doEvent("chuidrusesson");
	radow.doEvent("expword","10");
}

function expWord11(){
	/* var sql=document.getElementById("sql").value;
	if(sql==""){
		$h.alert('ϵͳ��ʾ��','��˫����������ѯ��',null,180);
		return;
	} */
	
	var gridId = "peopleInfoGrid";
	var grid = Ext.getCmp(gridId);
	var total = grid.getStore().getCount();//��������
	if(total==0){
		Ext.MessageBox.alert('��ʾ','���Ƚ��в�ѯ!');
		return;
	}
	var store = grid.getStore();
	var length = store.getCount();
	if(length==0){
		$h.alert('ϵͳ��ʾ��','����ѡ���¼��',null,150);
		return;
	}
	radow.doEvent("chuidrusesson");
	radow.doEvent("expword","11");
}

function printSet(){
	//�õ�Ĭ�ϴ�ӡ��
	var oShell = new ActiveXObject("WScript.Shell");
    var sRegVal = 'HKEY_CURRENT_USER\\Software\\Microsoft\\Windows NT\\CurrentVersion\\Windows\\Device';
    var sDefault = oShell.RegRead(sRegVal);
    var printer =sDefault.split(",")[0];
	/* radow.doEvent("printSetInit",printer); */
	
	//�õ����еĴ�ӡ���������
    var nt = new ActiveXObject("WScript.Network"); //��ȡ���
    var oPrinters = nt.EnumPrinterConnections(); //��Ҫ��д ActiveX �ؼ������d��ӡ���б�
    if (oPrinters == null || oPrinters.length == 0)
    {
        alert('��ǰ��Ļ�����û��װ��ӡ��');
        return;
    } else
    {
        //alert('��ǰ��ӡ��̨��:'+oPrinters.length);
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
	$h.openWin('printSetWin','pages.publicServantManage.PrintSet','��ӡ������',500,180,param,ctxPath);
}
//��ӡ
function print(){
	//("http://127.0.0.1:8080/hzb/ziploud/57bee0d0ec4940119742e007e5015113/expFiles_20180514110455/1_��  ��.doc");
	var gridId = "peopleInfoGrid";
	var grid = Ext.getCmp(gridId);
	//var grid = Ext.getCmp('peopleInfoGrid');
	var total = grid.getStore().getCount();//��������
	if(total==0){
		Ext.MessageBox.alert('��ʾ','���Ƚ��в�ѯ!');
		return;
	}
	/* var sql=document.getElementById("sql").value;
	if(sql==""){
		$h.alert('ϵͳ��ʾ��','��˫����������ѯ��',null,180);
		return;
	} */
	var store = grid.getStore();
	var length = store.getCount();
	if(length==0){
		$h.alert('ϵͳ��ʾ��','����ѡ���¼��',null,150);
		return;
	}
	try{ 
		var wdapp = new ActiveXObject("Word.Application");
  	}catch(e){ 
  		$helper.alertActiveX();
	    return; 
  	}  
  	Ext.MessageBox.wait('���ڴ�ӡ�У����Ժ󡣡���');
	radow.doEvent("chuidrusesson");
	/* radow.doEvent("printRmb","1");   */
	ajaxSubmit("printRmb","1");
	
}

function printStart(path){
	var url=window.location.protocol+"//"+window.location.host+"/hzb/";
	path = url+path;
	//Ext.MessageBox.hide();
	var wdapp = new ActiveXObject("Word.Application");
    wdapp.Documents.Open(path);//��wordģ��url
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
        timeout :300000,//���������
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
				if("�����ɹ���"!=cfg.mainMessage){
					Ext.Msg.alert('ϵͳ��ʾ:',cfg.mainMessage);
				}
			}else{
				//Ext.Msg.hide();	
				
				if(cfg.mainMessage.indexOf("<br/>")>0){
					
					$h.alert('ϵͳ��ʾ',cfg.mainMessage,null,380);
					return;
				}
				
				if("�����ɹ���"!=cfg.mainMessage){
					Ext.Msg.alert('ϵͳ��ʾ:',cfg.mainMessage);
				}
			}
			
		},
		failure : function(res, options){ 
			Ext.Msg.hide();
			alert("�����쳣��");
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
		sign = 1;//˵�����״��޸�
	}
	
	var a0000 = "";
	var personcheck = grid.store.getAt(num).get('personcheck');
	//�ȶ�checkBox���в���,�����"true",��Ϊ"false";�����"false",��Ϊ"true"
	//changeCheckBox(personcheck,num);
	
	var personid = grid.store.getAt(num).get('a0000');
	var peopleName = grid.store.getAt(num).get('a0101');
	a0000 = personid;
	//true ˵����Ҫ��ѡ�����Ա
	if(personcheck){
		listString=listString+"@|"+personid+"|";
	}
	if(!personcheck){
		if(listString.length == 38){
			listString = listString.replace("|"+personid+"|","");
		}else{
			/* ȷ�����ȡ����ѡ��a0000 */
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
	ShowCellCover("","��ܰ��ʾ","�����ɹ���");
	setTimeout(cc,3000);
}
function ShowCellCover(elementId, titles, msgs)
{	
	Ext.MessageBox.buttonText.ok = "�ر�";
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
			Ext.MessageBox.confirm("ϵͳ��ʾ", msgs, function(but) {  
				
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
//����ɲ����Ե�����
function setAttributeFields(param){
	document.getElementById("fields").value = param;
}

//��Ա�б����¼� 
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
	grid.reconfigure(ds,colModel);//����������ģ�ͺ����ݶ���
	//ds.load(lastOptions);
	
	var bbar = grid.getBottomToolbar();
	bbar.bind(ds);//���°󶨷�ҳ�����ݶ���
	
	var sql=document.getElementById("sql").value;
	var qvid=document.getElementById("qvid").value;
	if(sql==""&&qvid==""){
		return;
	};
	
	radow.doEvent("peopleInfoGrid.dogridquery");
	  
	
	
	return;
}
/* sql��ѯ   �� �� */
function openSql1(){
	$h.openPageModeWin('searchListWin','pages.zhsearch.searchList','��ѯ�б�',460,400,'',contextPath,null,{maximizable:false,resizable:false});
}
function groupQuery(){
	radow.doEvent("initListAddGroupFlag");
	//var newWin_ = $h.getTopParent().Ext.getCmp('group');
	//if(!newWin_){
		$h.openPageModeWin('group','pages.customquery.Group','��ϲ�ѯ',1200,720,'',contextPath,null,
				{modal:true,collapsed:false,collapsible:false,titleCollapse:false,maximized:false});
	//}else{
	//	newWin_.expand(true); 
	//}
	//radow.util.openWindow('group','pages.customquery.Group');
}
function queryByName(){
	radow.doEvent("initListAddGroupFlag");
	$h.openWin('findById','pages.customquery.CustomQueryByName','������/���֤��ѯ ',1020,520,null,contextPath,null,{maximizable:false,resizable:false});
	//radow.util.openWindow('findById','pages.customquery.CustomQueryByName');
} 

function AttributeQuery(){
	$h.openWin('AttributeWin','pages.zhsearch.AttributeQuery','�ɲ����Բ�ѯ ',1000,600,null,contextPath,null,{maximizable:false,resizable:false});
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
	
	
	if(tableType == 1){								//�б�
		
		showgrid()
	}
	
	
	if(tableType == 2){								//С����
		//radow.doEvent("ShowData");
		datashow()
	}
	
	if(tableType == 3){								//��Ƭ
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
	//�Ҳ���event�Ĵ���  ����δӰ��  
	try{
		bbar.insertButton(11,[
			new Ext.menu.Separator({cls:'xtb-sep'}),
			new Ext.Button({
				icon : 'images/keyedit.gif',
				id:'setPageSize',
			    text:'����ÿҳ����',
			    handler:setPageSize1
			}),
			
			new Ext.Button({
				icon : 'images/keyedit.gif',
				id:'setFields',
			    text:'������ʾ�ֶ�',
			    handler:setFields
			}),
			new Ext.Button({
				icon : 'images/icon/table.gif',
				id:'getAll',
			    text:'����Excel',
			    handler:expExcelFromGrid
			}),
			new Ext.Button({
				icon : 'image/u53.png',
				id:'sortHand',
			    text:'�ֶ�����',
			    handler:sortHand
			}),
			new Ext.Button({
				icon : 'image/u53.png',
				id:'saveSortBtn',
			    text:'��ʱ����',
			    handler:sort
			}),
		]); 
	}catch(err){
		
	}

});
//�ֶ����� ,�����Զ��������ֶ�
function sortHand(){
	var url = contextPath + "/pages/customquery/A01SortConfig.jsp?";
	$h.showWindowWithSrc("sortHand",url,"��������", 400, 600);
}

//��ʱ���� 
function sort(){
	$h.openWin('Sort','pages.publicServantManage.Sort','��ʱ���� ',560,680,document.getElementById('tableType').value,ctxPath);
}


//����ÿҳ����
function setPageSize1(){
	var gridId = 'peopleInfoGrid';
	if (!Ext.getCmp(gridId)) {
		odin.error("Ҫ������grid�����ڣ�gridId=" + gridId);
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
		doOpenPupWin(url, "����ÿҳ����", 300, 150);
		//$h.openWin('policyListWin',url,'���߷���',1050,550,'',ctxPath);
		
		
	} else {
		odin.error("�Ƿ�ҳgrid����ʹ�ô˹��ܣ�");
		return;
	}
}
//������ʾ�ֶ�
function setFields(){
	var url = contextPath + "/pages/customquery/A01FieldsConfig.jsp?";
	//doOpenPupWin(url, "������ʾ�ֶ�", 400, 550);
	$h.showWindowWithSrc("setFields",url,"������ʾ�ֶ�", 400, 730);
}
//����Excel
function expExcelFromGrid(){
	
	
	/* var sql=document.getElementById("sql").value;
	var qvid=document.getElementById("qvid").value;
	if(sql=="" && qvid==""){
		$h.alert('ϵͳ��ʾ��','����в�ѯ��',null,180);
		return;
	} */
	
	
	var excelName = null;
	
	//excel�������Ƶ�ƴ��    
	var pgrid = Ext.getCmp('peopleInfoGrid');
	var dstore = pgrid.getStore();
	
	var num = dstore.getTotalCount()
	var length = dstore.getCount();
	if(length==0){
		$h.alert('ϵͳ��ʾ��','û��Ҫ���������ݣ�',null,180);
		return;
	}
	if(num != 0){
		//����б��һ����
		excelName = dstore.getAt(0).get('a0101');
		if(typeof excelName=='undefined' || excelName== null ||excelName==''){
			excelName = dstore.getAt(0).get('a01a0101');
		}
		if(num > 1){
			excelName = excelName + "��" + num +"��";
		}
	}
	
	excelName = "��Ա��Ϣ" + "_" + excelName
	+ "_" + Ext.util.Format.date(new Date(), "Ymd");
	
	odin.grid.menu.expExcelFromGrid('peopleInfoGrid', excelName, null,null, false);
	
}
//��������ѯ
function queryByNameAndIDS(list){
	radow.doEvent("initListAddGroupFlag");
	radow.doEvent('queryByNameAndIDS',list);
}
function changeField(){
	
	Ext.Ajax.request({
		method: 'POST',
        async: true,
        params : {'jsonp':""},
        //timeout :30000,//���������
		url: "<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.zhsearch.Isearch&eventNames=saveA01_config",
		success: function(resData){
			var cfg = Ext.util.JSON.decode(resData.responseText);
			if(0==cfg.messageCode){
				resetCM(cfg.elementsScript);
			}else{
				Ext.Msg.alert('ϵͳ��ʾ',cfg.mainMessage)
			}
			
				
		},
		failure : function(res, options){ 
			Ext.Msg.hide();
			alert("�����쳣��");
		}  
	});
}
/* �Զ����ѯ  �� �� */
function userDefined2(a,b,c,cid){
	var subWinIdBussessId2 = "";
	if(cid){
		subWinIdBussessId2 = cid;
	}
	<%--$h.openWin('win1','pages.customquery.QueryConditionEdit','�Զ����ѯ',1250,600,subWinIdBussessId2,'<%=ctxPath%>');--%>
	//�µ��Զ����ѯ
	$h.openPageModeWin('win1','pages.cadremgn.infmtionquery.UserDefinedQuery','�Զ����ѯ',1250,604,subWinIdBussessId2,'<%=ctxPath%>');
}
var type="1"
Ext.onReady(function(){
	 var pgrid = Ext.getCmp('peopleInfoGrid');
	var viewSize = Ext.getBody().getViewSize();
	pgrid.setHeight(viewSize.height+14);
	pgrid.setWidth(viewSize.width-261);

	
	document.getElementById('personq').value = '1';
	document.getElementById('personq_combotree').value = '��ְ��Ա';
	
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
			    //timeout :300000,//���������
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
						Ext.Msg.alert('ϵͳ��ʾ',cfg.mainMessage)
					}
				},
				failure : function(res, options){ 
					Ext.Msg.hide();
					alert("�����쳣��");
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
		Ext.MessageBox.alert('��ʾ','��˫����������ѯ!');
		return;
	} */
	var grid = Ext.getCmp('peopleInfoGrid');
	var total = grid.getStore().getCount();//��������
	if(total==0){
		Ext.MessageBox.alert('��ʾ','���Ƚ��в�ѯ!');
		return;
	}
	var a0000s = getChechA0000S();
	if(""==a0000s){
		Ext.MessageBox.alert("��ʾ","��ѡ����Ա!");
	} else{
		radow.doEvent('cadresTTFAudit',a0000s);
	}
}
function cadresOAudit(){
	/* if(""==$("#sql").val()){
		Ext.MessageBox.alert('��ʾ','��˫����������ѯ!');
		return;
	} */
	var grid = Ext.getCmp('peopleInfoGrid');
	var total = grid.getStore().getCount();//��������
	if(total==0){
		Ext.MessageBox.alert('��ʾ','���Ƚ��в�ѯ!');
		return;
	}
	var a0000s = getChechA0000S();
	if(""==a0000s){
		Ext.MessageBox.alert("��ʾ","��ѡ����Ա!");
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
		Ext.MessageBox.alert('��ʾ','��˫����������ѯ!');
		return;
	} */
	var grid = Ext.getCmp('peopleInfoGrid');
	var total = grid.getStore().getCount();//��������
	if(total==0){
		Ext.MessageBox.alert('��ʾ','���Ƚ��в�ѯ!');
		return;
	}
	var a0000s = getChechA0000S();
	if(""==a0000s){
		Ext.MessageBox.alert("��ʾ","��ѡ����Ա!");
	} else{
		radow.doEvent('unLockAudit',f+","+a0000s);
	}
}

//��ȡѡ����Ա
function getChechA0000S(){
	var grid = Ext.getCmp('peopleInfoGrid');
	
	var total = grid.getStore().getCount();//��������
	var record; //������
	var a0000s = ""; //���θɲ�����
	
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
	    //timeout :300000,//���������
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
				Ext.Msg.alert('ϵͳ��ʾ',cfg.mainMessage)
			}
		},
		failure : function(res, options){ 
			Ext.Msg.hide();
			alert("�����쳣��");
		}  
	});
	


	
}

    var doAddPerson = (function () {
        var obj = null;
        return {

            queryByNameAndIDS: function (list,sub) {//��������ѯ
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
    		 	//����
    		 	document.getElementById("td1").style.display="none";
    	   		/* document.getElementById("divtab2").firstChild.style.width=1;//��ѯ�б�������
    	        var tree = Ext.getCmp('group');
    	        //tree�Ŀ������
    	        tree.setWidth(1);
    	        //tab�Ŀ������
    	        var resizeobj =Ext.getCmp('tab');
    			resizeobj.setWidth(1); */
    			
    			flag_ss=true;//���ر�־
    			//document.getElementById(obj.id).innerHTML='>';
    			document.getElementById(obj.id).style.background="url(image/left.png) #D6E3F2 no-repeat center center";
    			var grid=Ext.getCmp('peopleInfoGrid');
    	    	grid.setWidth(10);//��СgirdDiv���(ʹdiv�����Զ���С������Ӧ���)
    	    	var width=document.getElementById("girdDiv").offsetWidth;//��ȡ��ǰdiv���
    	    	grid.setWidth(width);//����grid�Ŀ��
    	    	
    	    	var grid_width=grid.getWidth();
    	    	//50 150 300 400 200
    	    	//��̬�����п�
    	    	/* grid.colModel.setColumnWidth(0,grid_column_0*grid_width,'');//��0��
    	    	//grid.colModel.setColumnWidth(1,grid_column_1*grid_width,'');//��1��
    	    	grid.colModel.setColumnWidth(2,grid_column_2*grid_width,'');//��2��
    	    	grid.colModel.setColumnWidth(3,grid_column_3*grid_width,'');//��3��
    	    	grid.colModel.setColumnWidth(4,grid_column_4*grid_width,'');//��4��
    	    	grid.colModel.setColumnWidth(5,grid_column_5*grid_width,'');//��5��
    	    	grid.colModel.setColumnWidth(6,grid_column_6*grid_width,'');//��6��
    	    	grid.colModel.setColumnWidth(7,grid_column_7*grid_width,'');//��7��
    	    	grid.colModel.setColumnWidth(8,grid_column_8*grid_width,'');//��8�� */
    	    	//grid.colModel.setColumnWidth(9,grid_column_9*grid_width,'');//��9��
    	   }else{ //��չ��
    			
    			/*  document.getElementById("divtab2").firstChild.style.width=250;//��ѯ�б�������
    		     var tree = Ext.getCmp('group')
    		     //tree�Ŀ������
    		     tree.setWidth(250);
    		     //tab�Ŀ������
    		     var resizeobj =Ext.getCmp('tab');
    			 resizeobj.setWidth(250); */
    			 document.getElementById("td1").style.display="block";
    			 
    			 flag_ss=false;//��չ����־
    			 //document.getElementById(obj.id).innerHTML='<span><</span>';
    			 document.getElementById(obj.id).style.background="url(image/right.png) #D6E3F2 no-repeat center center";
    			var grid=Ext.getCmp('peopleInfoGrid');
    	    	grid.setWidth(10);//��СgirdDiv���(ʹdiv�����Զ���С������Ӧ���)
    	    	var width=document.getElementById("girdDiv").offsetWidth;//��ȡ��ǰdiv���
    	    	grid.setWidth(width);//����grid�Ŀ��
    	    	
    	    	var grid_width=grid.getWidth();
    	    	//50 150 300 400 200
    	    	//��̬�����п�
    	    	/* grid.colModel.setColumnWidth(0,grid_column_0*grid_width,'');//��0��
    	    	//grid.colModel.setColumnWidth(1,grid_column_1*grid_width,'');//��1��
    	    	grid.colModel.setColumnWidth(2,grid_column_2*grid_width,'');//��2��
    	    	grid.colModel.setColumnWidth(3,grid_column_3*grid_width,'');//��3��
    	    	grid.colModel.setColumnWidth(4,grid_column_4*grid_width,'');//��4��
    	    	grid.colModel.setColumnWidth(5,grid_column_5*grid_width,'');//��5��
    	    	grid.colModel.setColumnWidth(6,grid_column_6*grid_width,'');//��6��
    	    	grid.colModel.setColumnWidth(7,grid_column_7*grid_width,'');//��7��
    	    	grid.colModel.setColumnWidth(8,grid_column_8*grid_width,'');//��8�� */
    	    	//grid.colModel.setColumnWidth(9,grid_column_9*grid_width,'');//��9��
    	   }
    }	

</script>
