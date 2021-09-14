<%@page import="com.insigma.siis.local.business.comm.VerWindowBS"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ page import="com.insigma.odin.framework.util.SysUtil" %>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3" %>
<%@page import="com.insigma.odin.framework.util.GlobalNames"%>
<%@page import="com.insigma.odin.framework.safe.SafeControlCenter"%>
<%@page import="com.insigma.odin.framework.safe.util.SafeConst"%>
<%@page import="com.insigma.siis.local.epsoft.util.LogUtil"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.insigma.odin.framework.db.DBUtil"%>
<%@page import="com.insigma.siis.local.lrmx.ExpRar"%>
<%@page import="com.insigma.siis.local.pagemodel.sysmanager.user.PsWindowPageModel"%>
<%@page import="com.insigma.siis.local.business.helperUtil.CodeType2js"%>
<%@page import="com.insigma.odin.framework.persistence.HBUtil"%>
<%@page import="com.insigma.odin.framework.persistence.HBSession"%>
<%@page import="com.insigma.siis.local.business.entity.Policy"%>
<%@page import="com.insigma.odin.framework.db.DBUtil"%>
<%@page import="com.insigma.odin.framework.db.DBUtil.DBType"%>
<%@page import="com.insigma.odin.framework.privilege.vo.UserVO"%>
<%@page import="com.insigma.odin.framework.privilege.PrivilegeManager"%>
<%@page import="org.hibernate.transform.Transformers"%>
<%@page import="com.insigma.siis.local.business.entity.NoticeVo"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@page language="java" import="java.util.*" isELIgnored="false"%>
<%-- <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%> --%>
<%
	SafeControlCenter.getInstance(SafeConst.PDT_INSIIS).safeValidate(request,SafeConst.VT_APPCONTEXT,SafeConst.PDT_INSIIS_COMP_ODIN);
	SafeControlCenter.getInstance().safeValidate(SafeConst.VT_LOGINCOUNT,SafeConst.PDT_INSIIS_COMP_ODIN);
	String data = CodeType2js.getCodeTypeJS(request);
	String downloadfile=ExpRar.getExeclPath()+"\\全国公务员管理信息系统2017版操作手册.doc";
	downloadfile= downloadfile.replace("\\", "/");
	
	String downloadfileTxt = ExpRar.getExeclPath()+"\\系统更新说明.txt";
	downloadfileTxt= downloadfileTxt.replace("\\", "/");
	
	//对标题进行处理，过长则截取 
	String name = SysUtil.getCacheCurrentUser().getName();
	
	if(name.length() > 7){
		name = name.substring(0,7) + "...";
	}
	
	
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
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<!--设置缩放和绘制-->
<meta name="viewport" content="width=device-width, initial-scale=1,minmum-scale=1,maxmum-scale=1,user-scalable=no">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<title>干部大数据综合管理平台</title>
<script type="text/javascript">
var g_contextpath = '<%=request.getContextPath()%>';
</script>
<odin:head/>

<link rel="stylesheet" href="css/main.css" />
<!--[if lt IE 9]>
    <script src="http://css3-mediaqueries-js.googlecode.com/svn/trunk/css3-mediaqueries.js"></script>
<![endif]-->
<!--加载meta IE兼容文件-->
<!--[if lt IE 9]>
<script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
<script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
<![endif]-->


<script type="text/javascript" src="mainPage/js/jquery.js"></script>
<script type="text/javascript" src="mainPage/js/bootstrap.min.js"></script>
<script type="text/javascript" src="basejs/dateutil.js"></script>
<script type="text/javascript" src="radow/corejs/radow.util.js"></script>
<script type='text/javascript' src='js/dropdown.js'></script>
<script type="text/javascript" src="basejs/indexX.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>

</head>

<body>
<odin:base></odin:base>
		<div class="top-box">
			<ul class="top-list">
		    <!-- <li><img src="mainimages/dot-renyuan.png" /><span class="dot-nums">1</span></li>
				<li><img src="mainimages/dot-youxiang.png" /><span class="dot-nums">1</span></li> -->
				<li><img src="mainimages/dot-touxiang.png" /><b>超级管理员</b></li>
				
			</ul>
		</div>
		<div class="main-box main-bg">
			<h3 class="main-title">基础信息管理</h3>
			<ul class="main-list">
				<li>
					<img src="mainimages/dot-1.png"  /> 
					<!-- <a href="javascript:void(0)" id="402881ef533533800153354ae1c00006" onclick="addTab1(&quot;402881ef533533800153354ae1c00006&quot;,&quot;机构信息&quot;,&quot;/radowAction.do?method=doEvent&pageModel=pages.sysorg.org.SysOrgOther&quot;)">机构信息</a> -->
				    <a href="/hzb/NewFile1.jsp?para=1" target="_Blank1"> 机构信息</a> 
				</li>
				<li>
					<img src="mainimages/dot-2.png"  /> 
					<a href="/hzb/NewFile1.jsp?para=2" target="_Blank2"> 干部信息</a>
				</li>
			</ul>
			<h3 class="main-title">业务工作</h3>
			<ul class="main-list">
				<li>
					<img src="mainimages/dot-3.png"  /> 
					<a href="/hzb/NewFile1.jsp?para=3" target="_Blank3"> 实绩纪实</a>
				</li>
				<li>
					<img src="mainimages/dot-4.png"  /> 
					<a href="/hzb/NewFile1.jsp?para=4" target="_Blank4"> 干部监督</a>
				</li>
				<li>
					<img src="mainimages/dot-6.png"  /> 
					<a href="/hzb/NewFile1.jsp?para=5" target="_Blank5"> 分析研究</a>
				</li>
				<li>
					<img src="mainimages/dot-7.png"  /> 
					<a href="/hzb/NewFile1.jsp?para=6" target="_Blank6"> 干部档案</a>
				</li>
				<li>
					<img src="mainimages/dot-8.png"  /> 
					<a href="/hzb/NewFile1.jsp?para=7" target="_Blank7"> 上报审批</a>
				</li>
				<li>
					<img src="mainimages/dot-9.png"  /> 
					<a href="/hzb/NewFile1.jsp?para=8" target="_Blank8"> 更多</a>
				</li>
			</ul>
			<h3 class="main-title">系统管理</h3>
			<ul class="main-list mar-bottom">
				<li>
					<img src="mainimages/dot-10.png"  /> 
					<a href="/hzb/NewFile1.jsp?para=9" target="_Blank9"> 信息发布</a>
				</li>
				<li>
					<img src="mainimages/dot-11.png"  /> 
					<a href="/hzb/NewFile1.jsp?para=10" target="_Blank10"> 用户管理</a>
				</li>
				<li>
					<img src="mainimages/dot-12.png"  /> 
					<a href="/hzb/NewFile1.jsp?para=11" target="_Blank11"> 权限设置</a>
				</li>
				<li>
					<img src="mainimages/dot-13.png"  /> 
					<a href="/hzb/NewFile1.jsp?para=12" target="_Blank12"> 数据交换</a>
				</li>
				<li>
					<img src="mainimages/dot-14.png"  /> 
					<a href="/hzb/NewFile1.jsp?para=13" target="_Blank13"> 指标管理</a>
				</li>
			</ul>
		</div>
		<div class="main-box">
			<div class="content-box">
			
			
				<div class="main-content"><!--通知公告-->
					<h3 class="content-title">通知公告<span class="content-more"><a href=""><img src="mainimages/dot-more.png" /></a></span></h3>
					<table>
					<tbody>
					<%
						for(Integer i=1;i<=8;i++){ 
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
<tr>
					<c:if test="${notice.id != null}">
						<%-- <div style="height:16.5%;vertical-align:middle;">
							<c:if test="${notice.see == 0}">
								<div style="float:left;width:24px;height:100%;background:url(images/red.png) no-repeat center center;margin-top: -7px;"></div>
							</c:if>
							<h4 class="panel-title" style="vertical-align:middle;height:100%;margin-left: 22px;">
								<a data-toggle="collapse" data-parent="#accordion" href="#tzgg1" onclick="noticeInfo('${notice.id}')">${notice.title}</a>
							</h4>
						</div> --%>
						<td width="25%" style="text-align:center;"><span class="dot-tongzhi"></span></td>
								<%-- <td width="75%">${notice.title}</td> --%>
								<td width="75%" style="margin-left: 22px;">		
								<a data-toggle="collapse" data-parent="#accordion" href="#tzgg1" onclick="noticeInfo('${notice.id}')">${notice.title}</a>							
									<%-- <a onclick="downloadPolicyFile('${notice.fileUrl}')" style="cursor:pointer;">下载</a> --%>
								</td>
					</c:if>
					<c:if test="${notice.id == null}">
					    <td width="25%" style="text-align:center;"><span class="dot-tongzhi"></span></td>
						<td width="10%" style="text-align:center;">...</td>
						<td></td>
						
					</c:if>		
					</tr>	
					<%	
						}
					%>
					</tbody>
					</table>
					<!-- <div class="content-list ">
						<h4 class="content-date">2017年12月12日<span class="date-1">星期二</span></h4>
						<p class="content-txt"><span class="dot-tongzhi">&nbsp;</span><a href="">通知公告的标题文字显示通知公告的标题文字显示</a></p>
						<p class="content-txt"><span class="dot-tongzhi">&nbsp;</span><a href="">告的标题文字显示</a></p>
					</div>
					<div class="content-list">
						<h4 class="content-date">2017年12月12日<span class="date-1">星期二</span></h4>
						<p class="content-txt"><span class="dot-tongzhi">&nbsp;</span><a href="">显示通知公告的标题文字显示</a></p>
						<p class="content-txt"><span class="dot-tongzhi">&nbsp;</span><a href="">知公告的标题文字显示</a></p>
					</div>
					<div class="content-list">
						<h4 class="content-date">2017年12月12日<span class="date-1">星期二</span></h4>
						<p class="content-txt"><span class="dot-tongzhi">&nbsp;</span><a href="">显示通知公告的标题文字显示</a></p>
						<p class="content-txt"><span class="dot-tongzhi">&nbsp;</span><a href="">知公告的标题文字显示</a></p>
					</div>
					<div class="content-list">
						<h4 class="content-date">2017年12月12日<span class="date-1">星期二</span></h4>
						<p class="content-txt"><span class="dot-tongzhi">&nbsp;</span><a href="">显示通知公告的标题文字显示</a></p>
						<p class="content-txt"><span class="dot-tongzhi">&nbsp;</span><a href="">知公告的标题文字显示</a></p>
					</div> -->
					
				</div>
				
				
				<div class="main-content"><!--待办提醒-->
					<h3 class="content-title">待办提醒<span class="content-more"><a href=""><img src="mainimages/dot-more.png" /></a></span></h3>
					<div class="content-list ">
						<!-- <h4 class="content-date">2017年12月12日<span class="date-1">星期二</span></h4> -->
						<p class="content-txt"><span class="dot-daiban">&nbsp;</span><a href="">待办提醒标题文字显示待办提醒标题文字显示</a></p>
						<p class="content-txt"><span class="dot-daiban">&nbsp;</span><a href="">待办提醒标题文字显示</a></p>
					</div>
					<div class="content-list">
						<!-- <h4 class="content-date">2017年12月12日<span class="date-1">星期二</span></h4> -->
						<p class="content-txt"><span class="dot-daiban">&nbsp;</span><a href="">待办提醒标题文字显示</a></p>
						<p class="content-txt"><span class="dot-daiban">&nbsp;</span><a href="">待办提醒标题文字显示</a></p>
					</div>
					<div class="content-list">
						<!-- <h4 class="content-date">2017年12月12日<span class="date-1">星期二</span></h4> -->
						<p class="content-txt"><span class="dot-daiban">&nbsp;</span><a href="">待办提醒标题文字显示</a></p>
						<p class="content-txt"><span class="dot-daiban">&nbsp;</span><a href="">待办提醒标题文字显示</a></p>
					</div>
					<div class="content-list">
						<!-- <h4 class="content-date">2017年12月12日<span class="date-1">星期二</span></h4> -->
						<p class="content-txt"><span class="dot-daiban">&nbsp;</span><a href="">待办提醒标题文字显示</a></p>
						<p class="content-txt"><span class="dot-daiban">&nbsp;</span><a href="">待办提醒标题文字显示</a></p>
					</div>
				</div>
				
				<div class="main-content"><!--政策法规-->
				
					<h3 class="content-title">政策法规<span class="content-more"><a href=""><img src="mainimages/dot-more.png" /></a></span></h3>
					<table>
					<tbody>
					<%
							for(Integer i=1;i<=8;i++){ 
												
												int length = policyList.size();
												Policy policy = new Policy();
												if(length>=i){
													policy = policyList.get(i-1);
													
													//对标题进行处理，过长则截取 
													String title = policy.getTitle();
													
													if(title.length() > 16){
														title = title.substring(0,16) + "...";
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
					
					            <%-- <td width="10%" style="text-align:center;"><%=i%></td> --%>
								<td width="20%" style="text-align:center;"><span class="dot-zhengce"></span></td>
								<td width="60%"  style="text-align:left;">${policy.title}</td>
								<td width="20%" style="text-align:center;">
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
					
					<!-- <div class="content-list ">
						<h4 class="content-date">2017年12月12日<span class="date-1">星期二</span></h4>
						<p class="content-txt"><span class="dot-zhengce">&nbsp;</span><a href="">政策法规标题文字政策法规标题文字</a></p>
						<p class="content-txt"><span class="dot-zhengce">&nbsp;</span><a href="">政策法规标题文字</a></p>
					</div>
					<div class="content-list">
						<h4 class="content-date">2017年12月12日<span class="date-1">星期二</span></h4>
						<p class="content-txt"><span class="dot-zhengce">&nbsp;</span><a href="">政策法规标题文字</a></p>
						<p class="content-txt"><span class="dot-zhengce">&nbsp;</span><a href="">政策法规标题文字</a></p>
					</div>
					<div class="content-list">
						<h4 class="content-date">2017年12月12日<span class="date-1">星期二</span></h4>
						<p class="content-txt"><span class="dot-zhengce">&nbsp;</span><a href="">政策法规标题文字</a></p>
						<p class="content-txt"><span class="dot-zhengce">&nbsp;</span><a href="">政策法规标题文字</a></p>
					</div>
					<div class="content-list">
						<h4 class="content-date">2017年12月12日<span class="date-1">星期二</span></h4>
						<p class="content-txt"><span class="dot-zhengce">&nbsp;</span><a href="">政策法规标题文字</a></p>
						<p class="content-txt"><span class="dot-zhengce">&nbsp;</span><a href="">政策法规标题文字</a></p>
					</div> -->
				</div>
			</div>
		</div>
		<script type="text/javascript" src="js/jquery-1.10.2.js" ></script>
<script type="text/javascript">
var g_contextpath = '<%=request.getContextPath()%>';

function addTab1(id,text,location){ 
	alert(id);
	alert(text);
	alert(location);
	alert(g_contextpath);
	addTab(text,id,g_contextpath+location,'','')
	//alert("1111"+g_contextpath);
}
//打开通知公告详情页面 
function noticeInfo(id){
	//var para = "14@@@"+id
	//window.location ="/hzb/NewFile1.jsp?para=1";
	window.open("/hzb/NewFile1.jsp?para=notice"+id+"", "_blank");  
	//location.reload(); 			//打开通知公告详情页面之前，刷新index页面 
<%--noticeAddTab('通知公告详情',id,'<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.notice.NoticeInfo',false, false);--%>
}
//下载政策法规文件 
//encodeURI，用来做url转码，解决中文传输乱码问题 （后台接收的时候会再做转码处理，转回来）
function downloadPolicyFile(fileurl){
	
	window.location="<%=request.getContextPath()%>/PublishFileServlet?method=downloadPolicyFile&filePath="+encodeURI(encodeURI(fileurl));
}
</script>
	</body>
</html>