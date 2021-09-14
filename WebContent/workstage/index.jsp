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
//���߷��� 
String hql = "";
if(DBUtil.getDBType()==DBType.ORACLE){
	hql = "from Policy order by updatetime desc";
}else{
	hql = "from Policy  where 1=1 order by updatetime desc limit 0,6";
}
List<Policy> policyList = sess.createQuery(hql).list();
UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
//֪ͨ����
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

<title>��ҵ��λ���¹���ϵͳ</title>
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
								<option value="1A" selected="selected">�ۺϹ�����</option>
								<option value="1B">רҵ������</option>
								<option value="1C">����ִ����</option>
								<option value="2">���񾯲쾯Աְ������</option>
								<option value="3">���ٵȼ�</option>
								<option value="4">���ٵȼ�</option>
								<option value="5">�������ȼ�</option>
								<option value="6">ִ������Աְ��ȼ�</option>
								<option value="71">������ִ��Ա</option>
								<option value="72">�����о�Ա</option>
								<option value="73">���ھ�����ְ��</option>
								<option value="74">����������Ԥ��Ա</option>
								<option value="75">������������ϢԱ</option>
								<option value="9">��ҵ��λ����ȼ�</option>
								<option value="C">��ҵ��λרҵ������λ</option>
								<option value="D">���ؼ������˸�λ</option>
								<option value="E">������ͨ���˸�λ</option>
								<option value="F">��ҵ��λ�������˸�λ</option>
								<option value="G">��ҵ��λ��ͨ���˸�λ</option>
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
					<div class="cell" style="padding-left:7%">��������</div>
				</div>
				<div class="cell" style="float:left;width:26%;height:100%;text-align:right;">
					<a href="#" onclick="noticeConfig()" title="������������"><img src="images/notice_config.png" style="vertical-align:middle" width="22px" height="24px" /></a>
				</div>
				<div class="right_style2" id="event_icon" onclick="hideDivStyle('formulaResult_view','event_icon')"></div>
			</div>
			<div id="formulaResult_view" style="height:84.5%;width:100%;background:#fff;border:1px solid #DEDEDE;border-top:0px solid #DEDEDE;">
				<table class="table table-striped" style="height:100%;margin-bottom:0px;">
					<thead>
						<tr>
							<th style="width:10%;"></th>
							<th style="width:55%">����</th>
							<th style="width:20%">����</th>
							<th style="width:15%"></th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td style="text-align:center;">1</td>
							<td>�����ڵ�����Ա</td>
							<td><span id="td1"></span></td>
							<td><a href="javascript:void(0);" style="text-decoration:none;text-align:center;" onclick="queryResultSYQ()">�鿴</a></td>
						</tr>
						<tr>
							<td style="text-align:center;">2</td>
							<td>�ѳ�������ʱ����Ա</td>
							<td><span id="td2"></span></td>
							<td><a href="javascript:void(0);" style="text-decoration:none;text-align:center;" onclick="queryResultTX()">�鿴</a></td>
						</tr>
						<tr>
							<td style="text-align:center;">3</td>
							<td>������������Ա</td>
							<td><span id="td3"></span></td>
							<td><a href="javascript:void(0);" style="text-decoration:none;text-align:center;" onclick="queryResultSR()">�鿴</a></td>
						</tr>
						<tr>
							<td style="text-align:center;">4</td>
							<td>��ת����Ա</td>
							<td><span id="td4"></span></td>
							<td><a href="javascript:void(0);" style="text-decoration:none;text-align:center;" onclick="queryResultZR()">�鿴</a></td>
						</tr>
						<tr>
							<td style="text-align:center;">5</td>
							<td>��ת����Ա</td>
							<td><span id="td5"></span></td>
							<td><a href="javascript:void(0);" style="text-decoration:none;text-align:center;" onclick="queryResultZC()">�鿴</a></td>
						</tr>
						<tr>
							<td style="text-align:center;">6</td>
							<td>�˻���Ա</td>
							<td><span id="td6"></span></td>
							<td><a href="javascript:void(0);" style="text-decoration:none;text-align:center;" onclick="queryResultTH()">�鿴</a></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div class="bottom_style" style="margin-left:1%">
			<div class="chart-heading">
				<div class="left_style">
					<div class="cell" style="padding-left: 7%">֪ͨ����</div>
				</div>
				<div class="cell" style="float:left;width:26%;height:100%;text-align:right;">
					<a href="#" onclick="openNoticeListTab()" title="����"><img src="images/more.png" style="vertical-align:middle" width="20px" height="50%" /></a>
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
									
									//�Ա�����д����������ȡ 
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
					<div class="cell" style="padding-left:7%">���߷���</div>
				</div>
				<div class="cell" style="float:left;width:26%;height:100%;text-align:right;">
					<a href="#" onclick="policyList()" title="����"><img src="images/more.png" style="vertical-align:middle" width="20px" height="50%" /></a>
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
													
													//�Ա�����д����������ȡ 
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
									<!-- <div onclick="policySet()" style="cursor:pointer;">����</div> -->
									<a onclick="downloadPolicyFile('${policy.fileUrl}')" style="cursor:pointer;">����</a>
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
					<div class="cell" style="padding-left:7%">��������</div>
				</div>
				<div class="cell" style="float:left;width:26%;height:100%;text-align:right;">
					<a href="#" onclick="noticeConfig()" title="������������"><img src="images/notice_config.png" style="vertical-align:middle" width="22px" height="24px" /></a>
				</div>
				<div class="right_style2" id="event_icon" onclick="hideDivStyle('formulaResult_view','event_icon')"></div>
			</div>
			<div id="formulaResult_view" style="height:84.5%;width:100%;background:#fff;border:1px solid #DEDEDE;border-top:0px solid #DEDEDE;">
				<table class="table table-striped" style="height:100%;margin-bottom:0px;">
					<thead>
						<tr>
							<th style="width:10%;"></th>
							<th style="width:55%">����</th>
							<th style="width:20%">����</th>
							<th style="width:15%"></th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td style="text-align:center;">1</td>
							<td>�����ڵ�����Ա</td>
							<td><span id="td1"></span></td>
							<td><a href="javascript:void(0);" style="text-decoration:none;text-align:center;" onclick="queryResultSYQ()">�鿴</a></td>
						</tr>
						<tr>
							<td style="text-align:center;">2</td>
							<td>�ѳ�������ʱ����Ա</td>
							<td><span id="td2"></span></td>
							<td><a href="javascript:void(0);" style="text-decoration:none;text-align:center;" onclick="queryResultTX()">�鿴</a></td>
						</tr>
						<tr>
							<td style="text-align:center;">3</td>
							<td>������������Ա</td>
							<td><span id="td3"></span></td>
							<td><a href="javascript:void(0);" style="text-decoration:none;text-align:center;" onclick="queryResultSR()">�鿴</a></td>
						</tr>
						<tr>
							<td style="text-align:center;">4</td>
							<td>��ת����Ա</td>
							<td><span id="td4"></span></td>
							<td><a href="javascript:void(0);" style="text-decoration:none;text-align:center;" onclick="queryResultZR()">�鿴</a></td>
						</tr>
						<tr>
							<td style="text-align:center;">5</td>
							<td>��ת����Ա</td>
							<td><span id="td5"></span></td>
							<td><a href="javascript:void(0);" style="text-decoration:none;text-align:center;" onclick="queryResultZC()">�鿴</a></td>
						</tr>
						<tr>
							<td style="text-align:center;">6</td>
							<td>�˻���Ա</td>
							<td><span id="td6"></span></td>
							<td><a href="javascript:void(0);" style="text-decoration:none;text-align:center;" onclick="queryResultTH()">�鿴</a></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
		<div class="bottom_style" style="margin-left:1%">
			<div class="chart-heading">
				<div class="left_style">
					<div class="cell" style="padding-left: 7%">֪ͨ����</div>
				</div>
				<div class="cell" style="float:left;width:26%;height:100%;text-align:right;">
					<a href="#" onclick="openNoticeListTab()" title="����"><img src="images/more.png" style="vertical-align:middle" width="20px" height="50%" /></a>
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
									
									//�Ա�����д����������ȡ 
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
					<div class="cell" style="padding-left:7%">���߷���</div>
				</div>
				<div class="cell" style="float:left;width:26%;height:100%;text-align:right;">
					<a href="#" onclick="policyList()" title="����"><img src="images/more.png" style="vertical-align:middle" width="20px" height="50%" /></a>
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
													
													//�Ա�����д����������ȡ 
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
									<!-- <div onclick="policySet()" style="cursor:pointer;">����</div> -->
									<a onclick="downloadPolicyFile('${policy.fileUrl}')" style="cursor:pointer;">����</a>
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

//���߷������õ��� 
function policySet(){
	//$h.openWin('warnWin','pages.publicServantManage.WarnWindow','��������',550,450,'',ctxPath);
}

//֪ͨ�������õ��� 
function noticeSet(){
	//$h.openWin('warnWin','pages.publicServantManage.WarnWindow','��������',550,450,'',ctxPath);
}

function openAffairs(){
	$h.openWin('setWarnWin','pages.publicServantManage.SetWarnWindow','��������',450,350,'',ctxPath);
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
  	  //alert(Ext.urlEncode({'asd':'����'}));
  	  src = src+'&'+Ext.urlEncode({'a0000':aid});
      personTabsId.push(aid);
      parent.tabs.add({
      title: (atitle),
      id: aid,
      tabid:aid,
      personid:aid,
      html: '<Iframe width="100%" height="100%" scrolling="auto" id="I'+aid+'" frameborder="0" src="'+src+'" style="clear:both;margin-left:0px;margin-right:0px;float:right;"></Iframe>',
	    listeners:{//�ж�ҳ���Ƿ���ģ�
	    	
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
		 	//alert("�����쳣��");
		}  
	});
}

/* function loadResult(data1){
	var xg = Ext.grid;
    
	var reader = new Ext.data.ArrayReader ({id:0},[ {name: 'NUMBER'},     //No.
	                                            	{name: 'NAME'},    //����
	                                            	{name: 'SEX'},       //�Ա�
	                                            	{name: 'BIRTH'},   //��������
	                                            	{name: 'WORK'}     //������λ��ְ��
	                                            	]);
	var grid1 = new xg.GridPanel ({
		id:'formulaResult',
		store: new Ext.data.Store ({
			reader: reader, 
			data: data1
		}), 
		cm: new xg.ColumnModel ([ 
		{id: 'id', header: "", width: 80, sortable: false, dataIndex: 'NUMBER'}, 
		{header: "����", width: 80, sortable: false, dataIndex: 'NAME'},
		{header: "�Ա�", width: 80, sortable: false, dataIndex: 'SEX'},
		{header: "��������", width: 100, sortable: false, dataIndex: 'BIRTH'},
		{header: "������λ��ְ��", width: 240, sortable: false, dataIndex: 'WORK'}
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
		autoScroll : true//������
	}); 
	grid1.render('formulaResult_view'); 
} */

/* ����ҳ */
/* function fristPage(){//��ҳ
	var page="1";
	document.getElementById("page").value=page;
	//radow.doEvent("datashow");
	document.getElementById("formulaResult_view").innerHTML = "";
	getAffairJson(page);
}
function lastPage(){//��һҳ
	var page=document.getElementById("page").value;
	if(page==1){
		alert("�Ѿ��ǵ�һҳ");
		return;
	}
	var length=document.getElementById("l").innerHTML;
	var newpage=parseInt(page)-1;
	document.getElementById("page").value=newpage;
	//radow.doEvent("datashow");
	document.getElementById("formulaResult_view").innerHTML = "";
	getAffairJson(newpage);
}
function nextPage(){//��һҳ
	var page=document.getElementById("page").value;
	var length=document.getElementById("l").innerHTML;
	var maxpage=Math.ceil(length/10);
	var newpage=parseInt(page)+1;
	if(newpage>maxpage){
		alert("�Ѿ������һҳ");
		return;
	}
	document.getElementById("page").value=newpage;
	//radow.doEvent("datashow");
	document.getElementById("formulaResult_view").innerHTML = "";
	getAffairJson(newpage);
}
function endPage(){//βҳ
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
	$h.openWin('warnWin1','pages.publicServantManage.WarnWindowSYQ','��������',701,527,'',ctxPath);
}
function queryResultTX(){
	$h.openWin('warnWin2','pages.publicServantManage.WarnWindowTX','��������',701,527,'',ctxPath);
}
function queryResultSR(){
	$h.openWin('warnWin3','pages.publicServantManage.WarnWindowSR','��������',701,527,'',ctxPath);
}
function queryResultZR(){
	$h.openWin('warnWin4','pages.publicServantManage.WarnWindowZR','��������',701,527,'',ctxPath);
}
function queryResultZC(){
	$h.openWin('warnWin5','pages.publicServantManage.WarnWindowZC','��������',701,527,'',ctxPath);
}
function queryResultTH(){
	$h.openWin('warnWin6','pages.publicServantManage.WarnWindowTH','��������',701,527,'',ctxPath);
}

//�������߷����ļ� 
//encodeURI��������urlת�룬������Ĵ����������� ����̨���յ�ʱ�������ת�봦��ת������
function downloadPolicyFile(fileurl){
	window.location="<%=request.getContextPath()%>/PublishFileServlet?method=downloadPolicyFile&filePath="+encodeURI(encodeURI(fileurl));
}

//���߷����б����� 
/* function policyList(){
	$h.openWin('policyListWin','pages.policy.PolicyWindow','���߷���',1050,550,'',ctxPath);
} */

function policyList(){
	addTab('���߷����б�','111111','<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.policy.PolicyWindow',false,false);
}

//��֪ͨ��������б�ҳ�� 
function openNoticeListTab(){
	addTab('֪ͨ�����б�','11111','<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.notice.NoticeWindow',false,false);
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
  	  //alert(Ext.urlEncode({'asd':'����'}));
  	src = src+'&'+Ext.urlEncode({'id':aid});
  	TabsId.push(aid);
      parent.tabs.add({
      title: (atitle),
      id: aid,
      tabid:aid,
      personid:aid,
      html: '<Iframe width="100%" height="100%" scrolling="auto" id="I'+aid+'" frameborder="0" src="'+src+'" style="clear:both;margin-left:0px;margin-right:0px;float:right;"></Iframe>',
	    listeners:{//�ж�ҳ���Ƿ���ģ�
	    	
	    },
	    closable:true
      }).show();  
		
    }
  }

//��֪ͨ��������ҳ�� 
function noticeInfo(id){
	location.reload(); 			//��֪ͨ��������ҳ��֮ǰ��ˢ��indexҳ�� 
	noticeAddTab('֪ͨ��������',id,'<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.notice.NoticeInfo',false, false);
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
	$h.openWin('setWarnWin','pages.publicServantManage.SetWarnWindow','��������',450,350,'',ctxPath);
}
</script>
</html>