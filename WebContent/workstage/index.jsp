<!DOCTYPE html>
<%@page	import="com.insigma.siis.local.pagemodel.publicServantManage.WarnWindowPageModel"%>
<%@page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@page import="com.insigma.odin.framework.persistence.HBUtil"%>
<%@page import="com.insigma.odin.framework.persistence.HBSession"%>
<%@page import="com.insigma.siis.local.business.entity.Policy"%>
<%@page language="java" import="java.util.*" isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@page import="com.insigma.odin.framework.privilege.vo.UserVO"%>
<%@page import="com.insigma.odin.framework.privilege.PrivilegeManager"%>
<%@page import="com.insigma.siis.local.business.entity.Notice"%>
<%@page import="org.hibernate.transform.Transformers"%>
<%@page import="com.insigma.siis.local.business.entity.NoticeVo"%>
<%@page import="com.insigma.odin.framework.db.DBUtil"%>
<%@page import="com.insigma.odin.framework.db.DBUtil.DBType"%>



<%
String ctxPath = request.getContextPath();
HBSession sess = HBUtil.getHBSession();
//政策法规 
String hql = "";
if(DBUtil.getDBType()==DBType.ORACLE){
	hql = "from Policy order by updatetime desc";
}else{
	hql = "from Policy  where 1=1 order by updatetime desc limit 0,6";
}
List<Policy> policyList = sess.createQuery(hql).list();
UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
//通知公告
String sql = "";
if(DBUtil.getDBType()==DBType.ORACLE){
	sql = "select a.id as id,a.title as title,b.see see from Notice a left join NOTICERECIPIENT b on a.id = b.noticeId where b.recipientid = '"+user.getId()+"' order by b.see,a.updatetime";
}else{
	sql = "select a.id as id,a.title as title,b.see see from Notice a left join NOTICERECIPIENT b on a.id = b.noticeId where b.recipientid = '"+user.getId()+"' order by b.see,a.updatetime limit 0,6 ";
	
}
List<NoticeVo> noticeList = sess.createSQLQuery(sql).setResultTransformer(Transformers.aliasToBean(NoticeVo.class)).list();
%>


<html lang="en" class="ie8">
<head>

<style type="text/css">
   
    
</style>

<title>事业单位人事管理系统</title>
<meta content="width=device-width, initial-scale=1.0" name="viewport" />
<meta content="" name="description" />
<meta content="" name="author" />
<!-- GLOBAL STYLES -->
<link rel="stylesheet" href="assets/plugins/bootstrap/css/bootstrap.css" />
<link rel="stylesheet" href="assets/css/main.css" />
<link rel="stylesheet" href="assets/css/theme.css" />
<link rel="stylesheet" href="assets/css/MoneAdmin.css" />
<link rel="stylesheet" type="text/css" href="../mainPage/css/font-awesome.css">
<link href="<%=request.getContextPath()%>/basejs/ext/resources/css/ext-all.css"	rel=stylesheet type=text/css>
<link href="assets/plugins/dataTables/dataTables.bootstrap.css"	rel="stylesheet" />
<link rel="stylesheet" href="assets/css/workflow.css" />
<script type="text/javascript"	src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript"	src="<%=request.getContextPath()%>/basejs/ext/adapter/ext/ext-base.js"></script>
<script src="<%=request.getContextPath()%>/basejs/ext/ext-all.js"> </script>
<script src="<%=request.getContextPath()%>/basejs/odin.js"> </script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>

<script type="text/javascript">
  function tbfx_func(obj){
     document.getElementById('iframe_tbfx').contentWindow.change_tb(obj.value);
  }
</script>

</head>



<body>
	<%-- <div class="top_style" id="top_height">
		<div class="chart_style">
			<div class="chart-heading" id="chart-heading_style">
				<div class="left_style pic1">
					<div class="cell" id="pg_analysis" style="padding-left:7%"></div>
				</div>
				<div style="display:table;float:left;width:31.8%;height:100%;text-align:right;">
					<%
						if(user.getUsertype().equals("0")){
					%>
					<div id="zwlb_select_div" class="cell" style="font-size :15px;">
						<select id="zwlb_select" onchange="tbfx_func(this)" style="height:30px;margin-top:1%;cursor:pointer;">
								<option value="1A" selected="selected">综合管理类</option>
								<option value="1B">专业技术类</option>
								<option value="1C">行政执法类</option>
								<option value="2">人民警察警员职务序列</option>
								<option value="3">法官等级</option>
								<option value="4">检察官等级</option>
								<option value="5">警务技术等级</option>
								<option value="6">执法勤务警员职务等级</option>
								<option value="71">深圳市执法员</option>
								<option value="72">深圳市警员</option>
								<option value="73">深圳警务技术职务</option>
								<option value="74">深圳市气象预报员</option>
								<option value="75">深圳市气象信息员</option>
								<option value="9">事业单位管理等级</option>
								<option value="C">事业单位专业技术岗位</option>
								<option value="D">机关技术工人岗位</option>
								<option value="E">机关普通工人岗位</option>
								<option value="F">事业单位技术工人岗位</option>
								<option value="G">事业单位普通工人岗位</option>
						</select>
					</div>
					<%
						}
					%>
				</div>
				<div class="right_style2" id="chart_icon" style="width:3%;" onclick="hideDivStyle('chart_view','chart_icon')"></div>
			</div>
			<div style="height:84.5%;width:100%;background:#fff;border:1px solid #DEDEDE;border-top:0px solid #DEDEDE;" id="chart_view" >
				<Iframe width="100%" height="100%" scrolling="auto" frameborder="0"
						src="<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.sysorg.org.gbjbqk.Gztzzt"
						style="clear: both; margin-left: 0px; margin-right: 0px;" id="iframe_tbfx"></Iframe>
			</div>
		</div>
	</div> --%>
	<% 
	String usertype = user.getUsertype();
	if(usertype.equals("0")){ 
	%>
	<div class="top_style">
		<div class="bottom_style">
			<div class="chart-heading">
				<div class="left_style">
					<div class="cell" style="padding-left:7%">事务提醒</div>
				</div>
				<div class="cell" style="float:left;width:26%;height:100%;text-align:right;">
					<a href="#" onclick="noticeConfig()" title="事务提醒配置"><img src="images/notice_config.png" style="vertical-align:middle" width="22px" height="24px" /></a>
				</div>
				<div class="right_style2" id="event_icon" onclick="hideDivStyle('formulaResult_view','event_icon')"></div>
			</div>
			<div id="formulaResult_view" style="height:84.5%;width:100%;background:#fff;border:1px solid #DEDEDE;border-top:0px solid #DEDEDE;">
				<table class="table table-striped" style="height:100%;margin-bottom:0px;">
					<thead>
						<tr>
							<th style="width:10%;"></th>
							<th style="width:55%">事项</th>
							<th style="width:20%">人数</th>
							<th style="width:15%"></th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td style="text-align:center;">1</td>
							<td>试用期到期人员</td>
							<td><span id="td1"></span></td>
							<td><a href="javascript:void(0);" style="text-decoration:none;text-align:center;" onclick="queryResultSYQ()">查看</a></td>
						</tr>
						<tr>
							<td style="text-align:center;">2</td>
							<td>已超过退休时间人员</td>
							<td><span id="td2"></span></td>
							<td><a href="javascript:void(0);" style="text-decoration:none;text-align:center;" onclick="queryResultTX()">查看</a></td>
						</tr>
						<tr>
							<td style="text-align:center;">3</td>
							<td>即将过生日人员</td>
							<td><span id="td3"></span></td>
							<td><a href="javascript:void(0);" style="text-decoration:none;text-align:center;" onclick="queryResultSR()">查看</a></td>
						</tr>
						<tr>
							<td style="text-align:center;">4</td>
							<td>待转入人员</td>
							<td><span id="td4"></span></td>
							<td><a href="javascript:void(0);" style="text-decoration:none;text-align:center;" onclick="queryResultZR()">查看</a></td>
						</tr>
						<tr>
							<td style="text-align:center;">5</td>
							<td>待转出人员</td>
							<td><span id="td5"></span></td>
							<td><a href="javascript:void(0);" style="text-decoration:none;text-align:center;" onclick="queryResultZC()">查看</a></td>
						</tr>
						<tr>
							<td style="text-align:center;">6</td>
							<td>退回人员</td>
							<td><span id="td6"></span></td>
							<td><a href="javascript:void(0);" style="text-decoration:none;text-align:center;" onclick="queryResultTH()">查看</a></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div class="bottom_style" style="margin-left:1%">
			<div class="chart-heading">
				<div class="left_style">
					<div class="cell" style="padding-left: 7%">通知公告</div>
				</div>
				<div class="cell" style="float:left;width:26%;height:100%;text-align:right;">
					<a href="#" onclick="openNoticeListTab()" title="更多"><img src="images/more.png" style="vertical-align:middle" width="20px" height="50%" /></a>
				</div>
				<div class="right_style2" id="notice_icon" onclick="hideDivStyle('notice_view','notice_icon')"></div>
			</div>
			<div id="notice_view" style="height:84.5%;width:100%;background:#fff;border:1px solid #DEDEDE;border-top:0px solid #DEDEDE;">
				<div id="accordion" style="height:100%;width:100%;">
					<%
						for(Integer i=1;i<=6;i++){ 
								int length = noticeList.size();
								NoticeVo notice = new NoticeVo();
								if(length>=i){
									notice = noticeList.get(i-1);
									
									//对标题进行处理，过长则截取 
									String title = notice.getTitle();
									
									if(title.length() > 20){
										title = title.substring(0,20) + "...";
									}
									notice.setTITLE(title);
								}
								request.setAttribute("notice", notice);
					%>
					<c:if test="${notice.id != null}">
						<div style="height:16.5%;vertical-align:middle;">
							<c:if test="${notice.see == 0}">
								<div style="float:left;width:24px;height:100%;background:url(images/red.png) no-repeat center center;margin-top: -7px;"></div>
							</c:if>
							<h4 class="panel-title" style="vertical-align:middle;height:100%;margin-left: 22px;">
								<a data-toggle="collapse" data-parent="#accordion" href="#tzgg1" onclick="noticeInfo('${notice.id}')">${notice.title}</a>
							</h4>
						</div>
					</c:if>
					<c:if test="${notice.id == null}">
						<p class="panel-title" style="vertical-align:middle;height:16.5%;margin-left: 22px;">...</p>
					</c:if>
					<%
						}
					%>
				</div>
			</div>
		</div>
		<div class="bottom_style" style="margin-left:1%">
			<div class="chart-heading">
				<div class="left_style">
					<div class="cell" style="padding-left:7%">政策法规</div>
				</div>
				<div class="cell" style="float:left;width:26%;height:100%;text-align:right;">
					<a href="#" onclick="policyList()" title="更多"><img src="images/more.png" style="vertical-align:middle" width="20px" height="50%" /></a>
				</div>
				<div class="right_style2" id="policy_icon" onclick="hideDivStyle('policy_view','policy_icon')"></div>
			</div>
			<div class="table-responsive" id="policy_view" style="height:84.5%;width:100%;background:#fff;border:1px solid #DEDEDE;border-top:0px solid #DEDEDE;">
				<table class="table" style="height:100%;margin-bottom:0;" id="policy_table">
					<tbody>
						<%
							for(Integer i=1;i<=6;i++){ 
												
												int length = policyList.size();
												Policy policy = new Policy();
												if(length>=i){
													policy = policyList.get(i-1);
													
													//对标题进行处理，过长则截取 
													String title = policy.getTitle();
													
													if(title.length() > 20){
														title = title.substring(0,20) + "...";
													}
													policy.setTitle(title);
												}
												request.setAttribute("policy", policy);
						%>
						<tr>
							<c:if test="${policy.id == null}">
								<td width="10%" style="text-align:center;">...</td>
								<td></td>
								<td></td>
							</c:if>
							<c:if test="${policy.id != null}">
								<td width="10%" style="text-align:center;"><%=i%></td>
								<td width="75%">${policy.title}</td>
								<td width="15%" style="text-align:center;">
									<!-- <div onclick="policySet()" style="cursor:pointer;">下载</div> -->
									<a onclick="downloadPolicyFile('${policy.fileUrl}')" style="cursor:pointer;">下载</a>
								</td>
							</c:if>
						</tr>
						<%
							}
						%>
					</tbody>
				</table>
			</div>
		</div>
	<%
	}
	
	
	
	
	
	
	
	%>
	
	<%-- <div class="top_style">
		<div class="bottom_style">
			<div class="chart-heading">
				<div class="left_style">
					<div class="cell" style="padding-left:7%">事务提醒</div>
				</div>
				<div class="cell" style="float:left;width:26%;height:100%;text-align:right;">
					<a href="#" onclick="noticeConfig()" title="事务提醒配置"><img src="images/notice_config.png" style="vertical-align:middle" width="22px" height="24px" /></a>
				</div>
				<div class="right_style2" id="event_icon" onclick="hideDivStyle('formulaResult_view','event_icon')"></div>
			</div>
			<div id="formulaResult_view" style="height:84.5%;width:100%;background:#fff;border:1px solid #DEDEDE;border-top:0px solid #DEDEDE;">
				<table class="table table-striped" style="height:100%;margin-bottom:0px;">
					<thead>
						<tr>
							<th style="width:10%;"></th>
							<th style="width:55%">事项</th>
							<th style="width:20%">人数</th>
							<th style="width:15%"></th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td style="text-align:center;">1</td>
							<td>试用期到期人员</td>
							<td><span id="td1"></span></td>
							<td><a href="javascript:void(0);" style="text-decoration:none;text-align:center;" onclick="queryResultSYQ()">查看</a></td>
						</tr>
						<tr>
							<td style="text-align:center;">2</td>
							<td>已超过退休时间人员</td>
							<td><span id="td2"></span></td>
							<td><a href="javascript:void(0);" style="text-decoration:none;text-align:center;" onclick="queryResultTX()">查看</a></td>
						</tr>
						<tr>
							<td style="text-align:center;">3</td>
							<td>即将过生日人员</td>
							<td><span id="td3"></span></td>
							<td><a href="javascript:void(0);" style="text-decoration:none;text-align:center;" onclick="queryResultSR()">查看</a></td>
						</tr>
						<tr>
							<td style="text-align:center;">4</td>
							<td>待转入人员</td>
							<td><span id="td4"></span></td>
							<td><a href="javascript:void(0);" style="text-decoration:none;text-align:center;" onclick="queryResultZR()">查看</a></td>
						</tr>
						<tr>
							<td style="text-align:center;">5</td>
							<td>待转出人员</td>
							<td><span id="td5"></span></td>
							<td><a href="javascript:void(0);" style="text-decoration:none;text-align:center;" onclick="queryResultZC()">查看</a></td>
						</tr>
						<tr>
							<td style="text-align:center;">6</td>
							<td>退回人员</td>
							<td><span id="td6"></span></td>
							<td><a href="javascript:void(0);" style="text-decoration:none;text-align:center;" onclick="queryResultTH()">查看</a></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div class="bottom_style" style="margin-left:1%">
			<div class="chart-heading">
				<div class="left_style">
					<div class="cell" style="padding-left: 7%">通知公告</div>
				</div>
				<div class="cell" style="float:left;width:26%;height:100%;text-align:right;">
					<a href="#" onclick="openNoticeListTab()" title="更多"><img src="images/more.png" style="vertical-align:middle" width="20px" height="50%" /></a>
				</div>
				<div class="right_style2" id="notice_icon" onclick="hideDivStyle('notice_view','notice_icon')"></div>
			</div>
			<div id="notice_view" style="height:84.5%;width:100%;background:#fff;border:1px solid #DEDEDE;border-top:0px solid #DEDEDE;">
				<div id="accordion" style="height:100%;width:100%;">
					<%
						for(Integer i=1;i<=6;i++){ 
								int length = noticeList.size();
								NoticeVo notice = new NoticeVo();
								if(length>=i){
									notice = noticeList.get(i-1);
									
									//对标题进行处理，过长则截取 
									String title = notice.getTitle();
									
									if(title.length() > 20){
										title = title.substring(0,20) + "...";
									}
									notice.setTITLE(title);
								}
								request.setAttribute("notice", notice);
					%>
					<c:if test="${notice.id != null}">
						<div style="height:16.5%;vertical-align:middle;">
							<c:if test="${notice.see == 0}">
								<div style="float:left;width:24px;height:100%;background:url(images/red.png) no-repeat center center;margin-top: -7px;"></div>
							</c:if>
							<h4 class="panel-title" style="vertical-align:middle;height:100%;margin-left: 22px;">
								<a data-toggle="collapse" data-parent="#accordion" href="#tzgg1" onclick="noticeInfo('${notice.id}')">${notice.title}</a>
							</h4>
						</div>
					</c:if>
					<c:if test="${notice.id == null}">
						<p class="panel-title" style="vertical-align:middle;height:16.5%;margin-left: 22px;">...</p>
					</c:if>
					<%
						}
					%>
				</div>
			</div>
		</div>
		<div class="bottom_style" style="margin-left:1%">
			<div class="chart-heading">
				<div class="left_style">
					<div class="cell" style="padding-left:7%">政策法规</div>
				</div>
				<div class="cell" style="float:left;width:26%;height:100%;text-align:right;">
					<a href="#" onclick="policyList()" title="更多"><img src="images/more.png" style="vertical-align:middle" width="20px" height="50%" /></a>
				</div>
				<div class="right_style2" id="policy_icon" onclick="hideDivStyle('policy_view','policy_icon')"></div>
			</div>
			<div class="table-responsive" id="policy_view" style="height:84.5%;width:100%;background:#fff;border:1px solid #DEDEDE;border-top:0px solid #DEDEDE;">
				<table class="table" style="height:100%;margin-bottom:0;" id="policy_table">
					<tbody>
						<%
							for(Integer i=1;i<=6;i++){ 
												
												int length = policyList.size();
												Policy policy = new Policy();
												if(length>=i){
													policy = policyList.get(i-1);
													
													//对标题进行处理，过长则截取 
													String title = policy.getTitle();
													
													if(title.length() > 20){
														title = title.substring(0,20) + "...";
													}
													policy.setTitle(title);
												}
												request.setAttribute("policy", policy);
						%>
						<tr>
							<c:if test="${policy.id == null}">
								<td width="10%" style="text-align:center;">...</td>
								<td></td>
								<td></td>
							</c:if>
							<c:if test="${policy.id != null}">
								<td width="10%" style="text-align:center;"><%=i%></td>
								<td width="75%">${policy.title}</td>
								<td width="15%" style="text-align:center;">
									<!-- <div onclick="policySet()" style="cursor:pointer;">下载</div> -->
									<a onclick="downloadPolicyFile('${policy.fileUrl}')" style="cursor:pointer;">下载</a>
								</td>
							</c:if>
						</tr>
						<%
							}
						%>
					</tbody>
				</table>
			</div>
		</div>
	</div> --%>
</body>
<script type="text/javascript">
	var ctxPath = '<%=ctxPath%>';

//政策法规设置弹窗 
function policySet(){
	//$h.openWin('warnWin','pages.publicServantManage.WarnWindow','事务提醒',550,450,'',ctxPath);
}

//通知公告设置弹窗 
function noticeSet(){
	//$h.openWin('warnWin','pages.publicServantManage.WarnWindow','事务提醒',550,450,'',ctxPath);
}

function openAffairs(){
	$h.openWin('setWarnWin','pages.publicServantManage.SetWarnWindow','事务提醒',450,350,'',ctxPath);
}
var personTabsId=[];
function addTab(atitle,aid,src,forced,autoRefresh){
    var tab=parent.tabs.getItem(aid);
    if (tab && !forced){ 
		parent.tabs.activate(tab);
		if(typeof autoRefresh!='undefined' && autoRefresh){
			document.getElementById('I'+aid).src = src;
		}
    }else{
  	  //alert(Ext.urlEncode({'asd':'三大'}));
  	  src = src+'&'+Ext.urlEncode({'a0000':aid});
      personTabsId.push(aid);
      parent.tabs.add({
      title: (atitle),
      id: aid,
      tabid:aid,
      personid:aid,
      html: '<Iframe width="100%" height="100%" scrolling="auto" id="I'+aid+'" frameborder="0" src="'+src+'" style="clear:both;margin-left:0px;margin-right:0px;float:right;"></Iframe>',
	    listeners:{//判断页面是否更改，
	    	
	    },
	    closable:true
      }).show();  
		
    }
  }
  
Ext.onReady(function() {
	<%	if(user.getUsertype().equals("0")){
	%>
		 getAffairJson();
 	<%
	}
	%>
	
});

function getAffairJson(){
	Ext.Ajax.request({
		 method: 'POST',   		
		 url: ctxPath + "/MsgResponse.do?method=load",
		 timeout:1000000,
		 params: {},
		 success: function(response, options){
			 var res = Ext.util.JSON.decode(response.responseText);
			 var result = res.data;
			 document.getElementById("td1").innerHTML = result.probation;
			 document.getElementById("td2").innerHTML = result.retire;
			 document.getElementById("td3").innerHTML = result.birthday;
			 document.getElementById("td4").innerHTML = result.getIn;
			 document.getElementById("td5").innerHTML = result.getOut;
			 document.getElementById("td6").innerHTML = result.back;
//			 alert(data1);
			 
		 },
		 failure : function(response, options){ 
		 	//alert("网络异常！");
		}  
	});
}

/* function loadResult(data1){
	var xg = Ext.grid;
    
	var reader = new Ext.data.ArrayReader ({id:0},[ {name: 'NUMBER'},     //No.
	                                            	{name: 'NAME'},    //姓名
	                                            	{name: 'SEX'},       //性别
	                                            	{name: 'BIRTH'},   //出生年月
	                                            	{name: 'WORK'}     //工作单位及职务
	                                            	]);
	var grid1 = new xg.GridPanel ({
		id:'formulaResult',
		store: new Ext.data.Store ({
			reader: reader, 
			data: data1
		}), 
		cm: new xg.ColumnModel ([ 
		{id: 'id', header: "", width: 80, sortable: false, dataIndex: 'NUMBER'}, 
		{header: "姓名", width: 80, sortable: false, dataIndex: 'NAME'},
		{header: "性别", width: 80, sortable: false, dataIndex: 'SEX'},
		{header: "出生年月", width: 100, sortable: false, dataIndex: 'BIRTH'},
		{header: "工作单位及职务", width: 240, sortable: false, dataIndex: 'WORK'}
		]),
		viewConfig: {
			forceFit: true
		} , 
		width: document.getElementById('affair').width, 
		height: 250, 
		enableColumnMove:false,
		collapsible: true, 
		animCollapse: false,
		iconCls: 'icon-grid',
		autoScroll : true//滚动条
	}); 
	grid1.render('formulaResult_view'); 
} */

/* 事务翻页 */
/* function fristPage(){//首页
	var page="1";
	document.getElementById("page").value=page;
	//radow.doEvent("datashow");
	document.getElementById("formulaResult_view").innerHTML = "";
	getAffairJson(page);
}
function lastPage(){//上一页
	var page=document.getElementById("page").value;
	if(page==1){
		alert("已经是第一页");
		return;
	}
	var length=document.getElementById("l").innerHTML;
	var newpage=parseInt(page)-1;
	document.getElementById("page").value=newpage;
	//radow.doEvent("datashow");
	document.getElementById("formulaResult_view").innerHTML = "";
	getAffairJson(newpage);
}
function nextPage(){//下一页
	var page=document.getElementById("page").value;
	var length=document.getElementById("l").innerHTML;
	var maxpage=Math.ceil(length/10);
	var newpage=parseInt(page)+1;
	if(newpage>maxpage){
		alert("已经是最后一页");
		return;
	}
	document.getElementById("page").value=newpage;
	//radow.doEvent("datashow");
	document.getElementById("formulaResult_view").innerHTML = "";
	getAffairJson(newpage);
}
function endPage(){//尾页
	var length=document.getElementById("l").innerHTML;
	var maxpage=Math.ceil(length/10);
	document.getElementById("page").value=maxpage;
	//radow.doEvent("datashow");
	document.getElementById("formulaResult_view").innerHTML = "";
	getAffairJson(maxpage);
} */


function openErrorPerson(data){
	var s = data.split("\\,");
	for(var i=0;i<s.length;i++){
		var a0000 = s[i];
		if(a0000){
			addTab('',a0000,ctxPath+'/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.AddPerson',false,false);
		}
	}
}
function closeWarnWin(){
	var win = parent.Ext.getCmp('warnWin');
    if (win) {win.close();}
}


function queryResultSYQ(){
	$h.openWin('warnWin1','pages.publicServantManage.WarnWindowSYQ','事务提醒',701,527,'',ctxPath);
}
function queryResultTX(){
	$h.openWin('warnWin2','pages.publicServantManage.WarnWindowTX','事务提醒',701,527,'',ctxPath);
}
function queryResultSR(){
	$h.openWin('warnWin3','pages.publicServantManage.WarnWindowSR','事务提醒',701,527,'',ctxPath);
}
function queryResultZR(){
	$h.openWin('warnWin4','pages.publicServantManage.WarnWindowZR','事务提醒',701,527,'',ctxPath);
}
function queryResultZC(){
	$h.openWin('warnWin5','pages.publicServantManage.WarnWindowZC','事务提醒',701,527,'',ctxPath);
}
function queryResultTH(){
	$h.openWin('warnWin6','pages.publicServantManage.WarnWindowTH','事务提醒',701,527,'',ctxPath);
}

//下载政策法规文件 
//encodeURI，用来做url转码，解决中文传输乱码问题 （后台接收的时候会再做转码处理，转回来）
function downloadPolicyFile(fileurl){
	window.location="<%=request.getContextPath()%>/PublishFileServlet?method=downloadPolicyFile&filePath="+encodeURI(encodeURI(fileurl));
}

//政策法规列表，弹窗 
/* function policyList(){
	$h.openWin('policyListWin','pages.policy.PolicyWindow','政策法规',1050,550,'',ctxPath);
} */

function policyList(){
	addTab('政策法规列表','111111','<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.policy.PolicyWindow',false,false);
}

//打开通知公告更多列表页面 
function openNoticeListTab(){
	addTab('通知公告列表','11111','<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.notice.NoticeWindow',false,false);
}


var TabsId=[];
function noticeAddTab(atitle,aid,src,forced,autoRefresh){
    var tab=parent.tabs.getItem(aid);
    if (tab && !forced){ 
		parent.tabs.activate(tab);
		if(typeof autoRefresh!='undefined' && autoRefresh){
			document.getElementById('I'+aid).src = src;
		}
    }else{
  	  //alert(Ext.urlEncode({'asd':'三大'}));
  	src = src+'&'+Ext.urlEncode({'id':aid});
  	TabsId.push(aid);
      parent.tabs.add({
      title: (atitle),
      id: aid,
      tabid:aid,
      personid:aid,
      html: '<Iframe width="100%" height="100%" scrolling="auto" id="I'+aid+'" frameborder="0" src="'+src+'" style="clear:both;margin-left:0px;margin-right:0px;float:right;"></Iframe>',
	    listeners:{//判断页面是否更改，
	    	
	    },
	    closable:true
      }).show();  
		
    }
  }

//打开通知公告详情页面 
function noticeInfo(id){
	location.reload(); 			//打开通知公告详情页面之前，刷新index页面 
	noticeAddTab('通知公告详情',id,'<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.notice.NoticeInfo',false, false);
}

function hideDivStyle(id,oriId) {
	var old = $('#'+id).css("display");
	if(old == 'none') {
		document.getElementById(oriId).style.background="url(images/shrink2.png) no-repeat 0px center";
		if(id == 'chart_view') {
			$('#top_height').css('height','48.5%');
			$('#chart-heading_style').css('height','15%');
		}
		$('#'+id).css('display','block');
	} else {
		document.getElementById(oriId).style.background="url(images/spread.png) no-repeat 0px center";
		if(id == 'chart_view') {
			$('#top_height').css('height','7.5%');
			$('#chart-heading_style').css('height','100%');
		}
		$('#'+id).css('display','none');
	}
}

function noticeConfig() {
	$h.openWin('setWarnWin','pages.publicServantManage.SetWarnWindow','事务提醒',450,350,'',ctxPath);
}
</script>
</html>