<%@page import="java.util.List"%>
<%@page import="com.insigma.siis.local.pagemodel.customquery.CommSQL"%>
<%@page import="com.insigma.siis.local.pagemodel.search.ComSearchPageModel"%>
<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.SysOrgPageModel"%>
<%@page import="com.insigma.siis.local.pagemodel.sysmanager.group.GroupManagePageModel"%>
<%@page import="com.insigma.siis.local.business.helperUtil.SysManagerUtils"%>
<%@page import="com.insigma.odin.framework.persistence.HBUtil"%>
<%@page import="com.insigma.siis.local.pagemodel.customquery.ShowControl"%>
<%@page import="javax.servlet.http.HttpSession"%>

<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>


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

<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/pingyin.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
 

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/basejs/ext/ux/css/LockingGridView.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/qdstyle.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/ext/ux/LockingGridView.js"></script>
<style>
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
<script  type="text/javascript">
var g_contextpath = '<%= request.getContextPath() %>';

</script>
<odin:toolBar property="toolbar1" applyTo="tab1_topbar1">
<odin:textForToolBar text="<h3>岗位情况</h3>"></odin:textForToolBar>
<odin:fill/>
<odin:separator/>
<odin:buttonForToolBar text="选择岗位" id="xzgw"></odin:buttonForToolBar>
<odin:separator/>
<odin:buttonForToolBar text="导出岗位情况" id="dcgwxq"></odin:buttonForToolBar>
<odin:separator isLast="true"/>
</odin:toolBar>

<odin:toolBar property="tab1toolbar2">
<odin:textForToolBar text="<h3>已选岗位</h3>"></odin:textForToolBar>
<odin:fill/>
<odin:separator/>
<odin:buttonForToolBar text="移除岗位" id="ycgw"></odin:buttonForToolBar>
<odin:separator isLast="true"/>
</odin:toolBar>

<odin:toolBar property="tab2toolbar1" >
<odin:textForToolBar text="<h3>待选人员</h3>"></odin:textForToolBar>
<odin:fill/>
<odin:separator/>
<odin:buttonForToolBar text="选择人员" id="xzry"></odin:buttonForToolBar>
<odin:separator isLast="true"/>
</odin:toolBar>

<odin:toolBar property="tab2toolbar2" >
<odin:textForToolBar text="<h3>已选人员</h3>"></odin:textForToolBar>
<odin:fill/>
<odin:separator/>
<odin:buttonForToolBar text="移除人员" id="ycry"></odin:buttonForToolBar>
<odin:separator isLast="true"/>
</odin:toolBar>

<odin:toolBar property="gwtopbar">
	<odin:fill/>
	<odin:separator/>
	<odin:buttonForToolBar text="移除" icon="image/icon021a3.gif" handler="infoDelete" id="infoDelete2" />
	<odin:separator isLast="true"/>
</odin:toolBar>
<odin:toolBar property="rytopbar">
	<odin:fill/>
	<odin:separator/>
	<odin:buttonForToolBar text="移除" icon="image/icon021a3.gif" handler="infoDelete" id="infoDelete" />
	<odin:separator isLast="true"/>
</odin:toolBar>
<odin:toolBar property="comptopbar">
	<odin:fill/>
	<odin:buttonForToolBar text="岗位人员设置" id="setC" handler="setC" icon="image/icon021a2.gif" />
	<odin:separator/>
	<odin:buttonForToolBar text="解除设置" icon="image/icon021a3.gif" handler="cancelC" id="cancelC" />
	<odin:separator/>
	<odin:buttonForToolBar text="导出" id="down" handler="down" icon="images/icon/exp.png" />
	<odin:separator isLast="true"/>
</odin:toolBar>

<odin:tab id="tab" tabchange="grantTabChange">
	<odin:tabModel>
		<odin:tabItem title="岗位情况" id="tab1"></odin:tabItem>
		<odin:tabItem title="人员分析" id="tab2"></odin:tabItem>
		<odin:tabItem title="关联设置" id="tab3" isLast="true"></odin:tabItem>
	</odin:tabModel>
	<odin:tabCont itemIndex="tab1">
		<div style="width:800px;height: 100%;float: left;">
			<table id="tab1_table" style="width: 800px;height: 100%;border: 0px;" cellspacing='0'>
				<tr>
					<td colspan="5">
						<div id="tab1_topbar1"></div>
					</td>
				</tr>
				<tr>
					<td colspan="5" height="2px">
					</td>
				</tr>
				<tr>
					<odin:select2 property="orgref"  codeType="SSKSBM" value="01"  label="关联部门"></odin:select2>
					<tags:PublicTextIconEdit3 codetype="orgTreeJsonData" onchange="a0201bChange" label="任职机构" property="a0201b" defaultValue="" readonly="true"/>
					<td>
						<odin:button text="查询"></odin:button>
					</td>
				</tr>
				<tr>
					<td colspan="5">
						<odin:editgrid2 property="jggwInfoGrid" url="/" pageSize="100" bbarId="pageToolBar" isFirstLoadData="false" title="待选岗位"  autoFill="false">
				<odin:gridJsonDataModel>
					<odin:gridDataCol name="personcheck" />
					<odin:gridDataCol name="b0111" />
					<odin:gridDataCol name="b0101" />
					<odin:gridDataCol name="b0114" />
					<odin:gridDataCol name="type" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridEditColumn2 header="selectall" width="50"  editor="checkbox" dataIndex="personcheck" edited="true" />
					<odin:gridEditColumn dataIndex="b0111" width="100" header="机构主键" edited="false" editor="text" hidden="true" />
					<odin:gridEditColumn2 dataIndex="b0101" width="210" header="机构名称" editor="text" edited="false" />
					<odin:gridEditColumn2 dataIndex="b0114" width="100" header="机构编码" editor="text" edited="false" />
					<odin:gridEditColumn2 dataIndex="b0114" width="100" header="职务名称" editor="text" edited="false" />
					<odin:gridEditColumn2 dataIndex="b0114" width="100" header="职务额配" editor="text" edited="false" />
					<odin:gridEditColumn2 dataIndex="b0114" width="100" header="职务超配" editor="text" edited="false" />
					<odin:gridEditColumn2 dataIndex="b0114" width="100" header="职务缺配" editor="text" edited="false" />
					
					<odin:gridEditColumn2 dataIndex="type" width="40" header="权限类型" editor="text" hidden="true" edited="false" isLast="true"/>
				</odin:gridColumnModel>
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
		<div  style="width: 400px;height: 100%;">
			<table style="width: 400px;height: 100%;" cellspacing='0'><tr><td>
			<odin:editgrid2 property="jggwSelGrid" url="/" topBarId="tab1toolbar2" pageSize="100" bbarId="pageToolBar" isFirstLoadData="false" autoFill="true">
				<odin:gridJsonDataModel>
					<odin:gridDataCol name="personcheck" />
					<odin:gridDataCol name="b0111" />
					<odin:gridDataCol name="b0101" />
					<odin:gridDataCol name="b0114" />
					<odin:gridDataCol name="type" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridEditColumn2 header="selectall" width="50"  editor="checkbox" dataIndex="personcheck" edited="true" />
					<odin:gridEditColumn dataIndex="b0111" width="100" header="机构主键" edited="false" editor="text" hidden="true" />
					<odin:gridEditColumn2 dataIndex="b0101" width="210" header="机构名称" editor="text" edited="false" />
					<odin:gridEditColumn2 dataIndex="b0114" width="100" header="机构编码" editor="text" edited="false" />
					<odin:gridEditColumn2 dataIndex="b0114" width="100" header="职务名称" editor="text" edited="false" />
					
					<odin:gridEditColumn2 dataIndex="type" width="40" header="权限类型" editor="text" hidden="true" edited="false" isLast="true"/>
				</odin:gridColumnModel>
				<odin:gridJsonData>
					{
				        data:[]
				    }
				</odin:gridJsonData>
			</odin:editgrid2>
			</td></tr></table>
		</div>
	</odin:tabCont>
	<odin:tabCont itemIndex="tab2">
		<div style="width:800px;height: 100%;float: left;">
			<table  cellspacing="0px" cellpadding="0px;" style="margin-top: 0px;width:800px;height: 100%;">
				<tr>
					<td><odin:editgrid2 property="peopleInfoGrid" enableColumnHide="false" load="refreshPerson" isFirstLoadData="false" topBarId="tab2toolbar1"
						autoFill="false" locked="true" width="100%" height="500" bbarId="pageToolBar" hasRightMenu="false" remoteSort="true" cellmousedown="rowClickPeople"
						pageSize="20">
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
				<tr>
					<td height="30" style="background-color: #cedff5" id="btd">
					<table align="center">
						<tr>
							<!-- <td><div class="top_btn_style" style="width:120px;" onclick="AttributeQuery()" >干部属性查询</div> </td> -->
							<td><div class="top_btn_style" style="width:170px;" onclick="queryByName()" >按姓名/身份证查询</div> </td>
							<td><div class="top_btn_style" style="width:120px;" onclick="groupQuery()" >常用条件查询</div> </td>
							<td><div class="top_btn_style" style="width:120px;" onclick="userDefined2()" >自定义查询</div> </td>
							<!-- <td><div class="top_btn_style" style="width:80px;" onclick="openSql1()" >sql查询</div> </td> -->
						</tr>
					</table>
					</td>
				</tr>
			</table>
			
		</div>
		<div  style="width: 400px;height: 100%;">
			<table style="width: 400px;height: 100%;" cellspacing='0'><tr><td>
			<odin:editgrid2 property="yrSelGrid" url="/" pageSize="100" bbarId="pageToolBar" isFirstLoadData="false" topBarId="tab2toolbar2"  autoFill="true">
				<odin:gridJsonDataModel>
					<odin:gridDataCol name="personcheck" />
					<odin:gridDataCol name="b0111" />
					<odin:gridDataCol name="b0101" />
					<odin:gridDataCol name="b0114" />
					<odin:gridDataCol name="type" isLast="true"/>
				</odin:gridJsonDataModel>
				<odin:gridColumnModel>
					<odin:gridRowNumColumn></odin:gridRowNumColumn>
					<odin:gridEditColumn2 header="selectall" width="50"  editor="checkbox" dataIndex="personcheck" edited="true" />
					<odin:gridEditColumn dataIndex="b0111" width="100" header="机构主键" edited="false" editor="text" hidden="true" />
					<odin:gridEditColumn2 dataIndex="b0101" width="100" header="姓名" editor="text" edited="false" />
					<odin:gridEditColumn2 dataIndex="b0114" width="150" header="身份证" editor="text" edited="false" />
					<odin:gridEditColumn2 dataIndex="b0114" width="200" header="工作单位及职务" editor="text" edited="false" />
					
					<odin:gridEditColumn2 dataIndex="type" width="40" header="权限类型" editor="text" hidden="true" edited="false" isLast="true"/>
				</odin:gridColumnModel>
				<odin:gridJsonData>
					{
				        data:[]
				    }
				</odin:gridJsonData>
			</odin:editgrid2>
			</td></tr></table>
		</div>
	
	</odin:tabCont>
	<odin:tabCont itemIndex="tab3" >
		<table style="border: 0px;vertical-align: top;" cellspacing="0">
			<tr>
				<td style="width: 600px;height: 200px;" valign="top">
					<odin:editgrid property="NiRenGrid" sm="row" isFirstLoadData="false" url="/"
							topBarId="gwtopbar" height="200" title="" pageSize="50" enableDragDrop="true" >
						<odin:gridJsonDataModel id="id" root="data" totalProperty="totalCount">
							<odin:gridDataCol name="checkid"/>
		                    <odin:gridDataCol name="a0000"/>
		                    <odin:gridDataCol name="fy0100"/>
		                    <odin:gridDataCol name="fy2200"/>
		                    <odin:gridDataCol name="fy2201"/>
		                    <odin:gridDataCol name="fy2202"/>
		                    <odin:gridDataCol name="fy2203"/>
		                    <odin:gridDataCol name="fy2207"/>
		                    <odin:gridDataCol name="fy2201b"/>
		                    <odin:gridDataCol name="delete" isLast="true"/>
		                </odin:gridJsonDataModel>
		                <odin:gridColumnModel>
		                    <odin:gridRowNumColumn/>
		                    <odin:gridEditColumn2 header="selectall" width="100" editor="checkbox" dataIndex="checkid"
		                                          edited="true" hideable="false" gridName="NiRenGrid" />
		                    <odin:gridEditColumn2 header="id" edited="false" dataIndex="fy2200" editor="text"
		                                          width="200" hidden="true"/>
		                    <odin:gridEditColumn2 header="拟任单位(简称)" edited="false" dataIndex="fy2201b" 
		                                          editor="text" width="300"/>
		                    <odin:gridEditColumn2 header="拟任单位ID" edited="false" dataIndex="fy2202"
		                    					  editor="text" width="300" hidden="true"/>
		                    <odin:gridEditColumn2 header="拟任职务" edited="false" dataIndex="fy2203" editor="text"
		                                          width="300" isLast="true"/>
		                    
		                </odin:gridColumnModel>
		            </odin:editgrid>
				</td>
				<td style="width: 600px;" rowspan="2" valign="top">
					<odin:editgrid property="gridComp" height="400" topBarId="comptopbar"
						load="selectRow"  isFirstLoadData="false"  pageSize="9999" 
						clicksToEdit="false" autoFill="true" >
						<odin:gridJsonDataModel>
							<odin:gridDataCol name="pcheck" /> 
							<odin:gridDataCol name="a0000" />
							<odin:gridDataCol name="fy0100" />
							<odin:gridDataCol name="fy0108" />
							<odin:gridDataCol name="fy2201b"/>
							<odin:gridDataCol name="fy2203"/>
							<odin:gridDataCol name="fy2500"/>
							<odin:gridDataCol name="fy0102"/>
							
							<odin:gridDataCol name="havefine" isLast="true"/>
						</odin:gridJsonDataModel>
						<odin:gridColumnModel>
							<odin:gridRowNumColumn></odin:gridRowNumColumn>
							<odin:gridEditColumn2 locked="true" header="selectall" width="40"
										editor="checkbox" dataIndex="pcheck" edited="true"
										hideable="false" gridName="gridComp" menuDisabled="true" />
							<odin:gridEditColumn2 header="拟任单位(简称)" edited="false" dataIndex="fy2201b" editor="text" width="250"/>
		                    <odin:gridEditColumn2 header="拟任职务" edited="false" dataIndex="fy2203" editor="text" width="250"/>
							<odin:gridEditColumn2 dataIndex="fy0102" header="姓名" width="150" sortable="false" menuDisabled="true" edited="false" editor="text" align="center" />
							<odin:gridEditColumn2 dataIndex="fy0108" header="现任职务" width="250" sortable="false" menuDisabled="true" edited="false" editor="text" align="center" />
							<odin:gridEditColumn2 dataIndex="fy2500" header="id" hidden="true" width="45" align="center" editor="text" edited="false" isLast="true"/>
						</odin:gridColumnModel>
						<odin:gridJsonData>
							{
						        data:[]
						    }
						</odin:gridJsonData>
					</odin:editgrid>
				</td>
			</tr>
			<tr>
				<td colspan="1" valign="top">
					<odin:editgrid property="gridcq" height="200" topBarId="rytopbar"
						load="selectRow"  isFirstLoadData="false"  pageSize="9999" 
						clicksToEdit="false" autoFill="true" >
						<odin:gridJsonDataModel>
							<odin:gridDataCol name="pcheck" /> 
							<odin:gridDataCol name="a0000" />
							<odin:gridDataCol name="fy0118" />
							<odin:gridDataCol name="fy0119" />
							<odin:gridDataCol name="fy0100" />
							<odin:gridDataCol name="fy0108" />
							<odin:gridDataCol name="fy01001" />
							
							<odin:gridDataCol name="a0101" />
							<odin:gridDataCol name="dc001" />
							<odin:gridDataCol name="dc004" />
							<odin:gridDataCol name="a0104" />
							<odin:gridDataCol name="dc001_2" />
							<odin:gridDataCol name="a0192a"/>
							<odin:gridDataCol name="havefine" isLast="true"/>
						</odin:gridJsonDataModel>
						<odin:gridColumnModel>
							<odin:gridRowNumColumn></odin:gridRowNumColumn>
							<odin:gridEditColumn2 locked="true" header="selectall" width="40"
										editor="checkbox" dataIndex="pcheck" edited="true"
										hideable="false" gridName="gridcq" menuDisabled="true" /> 
							<odin:gridEditColumn2 dataIndex="a0101" header="姓名" width="65" sortable="false" menuDisabled="true" edited="false" editor="text" align="center" />
							<odin:gridEditColumn2 dataIndex="fy0108" header="现任职务" width="200" sortable="false" menuDisabled="true" edited="false" editor="text" align="center" />
							<odin:gridEditColumn2 dataIndex="fy0100" header="id" hidden="true" width="45" align="center" editor="text" edited="false" />
							<odin:gridEditColumn2 dataIndex="a0163" header="人员状态" hidden="true" isLast="true" width="45" align="center" editor="select" edited="false" codeType="ZB126" />
						</odin:gridColumnModel>
						<odin:gridJsonData>
							{
						        data:[]
						    }
						</odin:gridJsonData>
					</odin:editgrid>
					
				</td>
			</tr>
		</table>
		
	</odin:tabCont>
</odin:tab>

<script type="text/javascript">


function deleterow(value, params, record, rowIndex, colIndex, ds){
	var fy2200=record.data.fy2200;
	return "<a href=\"javascript:deleteRow2(&quot;" + fy2200 + "&quot;)\">删除</a>";
}
function deleteRow2(fy2200){
	Ext.Msg.confirm('系统提示','你确定要删除选中数据吗？',function (btn){
		if(btn=='yes'){
			radow.doEvent("delete22row",fy2200);
		}
	})
	
}


function infoDelete(){
	var fyId = document.getElementById('fyId').value;
	var a0101s='';
	var fy0100s='';
	var grid=Ext.getCmp('gridcq');
	var store=grid.getStore();
	var rowcount=store.getCount();
	var num=0;
	for(var i=0;i<rowcount;i++){
		var record=store.getAt(i);
		var pcheck=record.data.pcheck;
		var a0101=record.data.a0101;
		var fy0100=record.data.fy0100;
		if(true == pcheck){
			num=num+1;
			if(num==1){
				a0101s=a0101;	
				fy0100s=fy0100;
			}else{
				a0101s=a0101s+','+a0101;
				fy0100s=fy0100s+','+fy0100;
			}
		}
	}
	if(fy0100s.length<1){
		$h.alert('系统提示','请勾选人员！');
		return;
	}
	Ext.Msg.confirm('系统提示','你确定要删除选中人员数据吗？',function (btn){
		if(btn=='yes'){
			radow.doEvent("delete01row", fy0100s);
		}else{
			return false;
		}
	});
}
//按姓名查询
function queryByNameAndIDS(list){
	radow.doEvent('queryByNameAndIDS',list);
}

function setC(){
	var store=Ext.getCmp('NiRenGrid').getStore();
	var nrId='';
	var count = 0;
	for(var i=0;i<store.getCount();i++){
		var record=store.getAt(i);
		var checkid=record.data.checkid;
		var fy2200=record.data.fy2200;
		if(true==checkid){
			nrId = fy2200;
			count ++;
		}
	}
	if(count==0){
		$h.alert('系统提示','没有选择拟任职务信息!');
		return;
	}
	if(count>1){
		$h.alert('系统提示','只能勾选一条拟任职务信息!');
		return;
	}
	radow.doEvent('setC',nrId);
}
function cancelC(){
	var grid  = Ext.getCmp('gridComp');
	var store = grid.getStore();
	var ids = '';
	var count = 0;
	for(var i=0; i<store.getCount();i++){
		var record=store.getAt(i);
		var pcheck=record.data.pcheck;
		var fy2500=record.data.fy2500;
		if(true==pcheck){
			ids = ids + fy2500 +",";
			count ++;
		}
	}
	if(count==0){
		$h.alert('系统提示','没有选择信息!');
		return;
	}
	Ext.Msg.confirm('系统提示','你确定要取消已勾选的数据吗？',function (btn){
		if(btn=='yes'){
			radow.doEvent('cancelC', ids);
		}else{
			return false;
		}
	});
}

function down(){
	radow.doEvent('down');
}

function a0201bChange(record){
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

//人员列表单击事件 
function rowClickPeople(a,index){
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
var tab_flag="tab1";//tab页显示标志
function grantTabChange(tabObj,item){
	var viewSize = Ext.getBody().getViewSize();
	if(item.getId()=='tab1'){
		var jggwSelGrid = Ext.getCmp('jggwSelGrid');
		jggwSelGrid.setWidth(viewSize.width-800-5);
	}else if(item.getId()=='tab2'){
		var yrSelGrid = Ext.getCmp('yrSelGrid');
		yrSelGrid.setWidth(viewSize.width-800-5);
	} else {
		
	}
	//重置页面高度样式
	/*jainceHeight2(); */
}
function jainceHeight2(){
	var heightClient=window.parent.document.body.clientHeight;
	var gwyxt_height=window.parent.document.body.firstChild.offsetHeight;
	var tabHeight=28;
	var tr1Height=30;
	var tr2ButtomHeight=30;
	var t2Height=heightClient-gwyxt_height-tabHeight-tr1Height;

	var gird_dom=Ext.getCmp('orgInfoGrid');//获取grid对象
	var jg_tj=document.getElementById("jg_tj").offsetHeight;
	document.getElementById("tree-div").style.height=t2Height-parseInt(jg_tj)-30;
	document.getElementById("div_tab2").style.height=t2Height-30;
}

Ext.onReady(function() {
	var viewSize = Ext.getBody().getViewSize();
	Ext.get('commForm').setWidth(viewSize.width);
	
	var jggwInfoGrid = Ext.getCmp('jggwInfoGrid');
	jggwInfoGrid.setHeight(viewSize.height-95);
	var jggwSelGrid = Ext.getCmp('jggwSelGrid');
	jggwSelGrid.setHeight(viewSize.height-39);
	jggwSelGrid.setWidth(viewSize.width-800-5);
	
	var pgrid = Ext.getCmp('peopleInfoGrid');
	var viewSize = Ext.getBody().getViewSize();
	pgrid.setHeight(viewSize.height-110);
	var yrSelGrid = Ext.getCmp('yrSelGrid');
	yrSelGrid.setHeight(viewSize.height-82);
	yrSelGrid.setWidth(viewSize.width-800-5);
	
	
	var NiRenGrid = Ext.getCmp('NiRenGrid');
	NiRenGrid.setHeight(viewSize.height/2-42);
	var gridcq = Ext.getCmp('gridcq');
	gridcq.setHeight(viewSize.height/2-42);
	var gridComp = Ext.getCmp('gridComp');
	gridComp.setHeight(viewSize.height-57);
	gridComp.setWidth(viewSize.width-600-5);
	//memberGrid.setWidth(viewSize.width);
	
	/* memberGrid.on('rowclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index)
		document.getElementById('fy_id').value = rc.data.fy_id;
		document.getElementById('fy_name').value = rc.data.fy_name;
	});
	
	memberGrid.on('rowdblclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index)
		document.getElementById('fy_id').value = rc.data.fy_id;
		document.getElementById('fy_no').value = rc.data.fy_no;
		$h.openPageModeWin('qcjs','pages.xbrm.fx.DealFXYP','分析研判',1150,800,{fy_id:rc.data.fy_id},g_contextpath);
	}); */
	
});


function infoSearch(){
	radow.doEvent('memberGrid.dogridquery');
}
</script>


