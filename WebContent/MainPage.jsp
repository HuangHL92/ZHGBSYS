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
	String downloadfile=ExpRar.getExeclPath()+"\\ȫ������Ա������Ϣϵͳ2017������ֲ�.doc";
	downloadfile= downloadfile.replace("\\", "/");
	
	String downloadfileTxt = ExpRar.getExeclPath()+"\\ϵͳ����˵��.txt";
	downloadfileTxt= downloadfileTxt.replace("\\", "/");
	
	//�Ա�����д����������ȡ 
	String name = SysUtil.getCacheCurrentUser().getName();
	
	if(name.length() > 7){
		name = name.substring(0,7) + "...";
	}
	
	
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
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<!--�������źͻ���-->
<meta name="viewport" content="width=device-width, initial-scale=1,minmum-scale=1,maxmum-scale=1,user-scalable=no">
<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1">
<title>�ɲ��������ۺϹ���ƽ̨</title>
<script type="text/javascript">
var g_contextpath = '<%=request.getContextPath()%>';
</script>
<odin:head/>

<link rel="stylesheet" href="css/main.css" />
<!--[if lt IE 9]>
    <script src="http://css3-mediaqueries-js.googlecode.com/svn/trunk/css3-mediaqueries.js"></script>
<![endif]-->
<!--����meta IE�����ļ�-->
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
				<li><img src="mainimages/dot-touxiang.png" /><b>��������Ա</b></li>
				
			</ul>
		</div>
		<div class="main-box main-bg">
			<h3 class="main-title">������Ϣ����</h3>
			<ul class="main-list">
				<li>
					<img src="mainimages/dot-1.png"  /> 
					<!-- <a href="javascript:void(0)" id="402881ef533533800153354ae1c00006" onclick="addTab1(&quot;402881ef533533800153354ae1c00006&quot;,&quot;������Ϣ&quot;,&quot;/radowAction.do?method=doEvent&pageModel=pages.sysorg.org.SysOrgOther&quot;)">������Ϣ</a> -->
				    <a href="/hzb/NewFile1.jsp?para=1" target="_Blank1"> ������Ϣ</a> 
				</li>
				<li>
					<img src="mainimages/dot-2.png"  /> 
					<a href="/hzb/NewFile1.jsp?para=2" target="_Blank2"> �ɲ���Ϣ</a>
				</li>
			</ul>
			<h3 class="main-title">ҵ����</h3>
			<ul class="main-list">
				<li>
					<img src="mainimages/dot-3.png"  /> 
					<a href="/hzb/NewFile1.jsp?para=3" target="_Blank3"> ʵ����ʵ</a>
				</li>
				<li>
					<img src="mainimages/dot-4.png"  /> 
					<a href="/hzb/NewFile1.jsp?para=4" target="_Blank4"> �ɲ��ල</a>
				</li>
				<li>
					<img src="mainimages/dot-6.png"  /> 
					<a href="/hzb/NewFile1.jsp?para=5" target="_Blank5"> �����о�</a>
				</li>
				<li>
					<img src="mainimages/dot-7.png"  /> 
					<a href="/hzb/NewFile1.jsp?para=6" target="_Blank6"> �ɲ�����</a>
				</li>
				<li>
					<img src="mainimages/dot-8.png"  /> 
					<a href="/hzb/NewFile1.jsp?para=7" target="_Blank7"> �ϱ�����</a>
				</li>
				<li>
					<img src="mainimages/dot-9.png"  /> 
					<a href="/hzb/NewFile1.jsp?para=8" target="_Blank8"> ����</a>
				</li>
			</ul>
			<h3 class="main-title">ϵͳ����</h3>
			<ul class="main-list mar-bottom">
				<li>
					<img src="mainimages/dot-10.png"  /> 
					<a href="/hzb/NewFile1.jsp?para=9" target="_Blank9"> ��Ϣ����</a>
				</li>
				<li>
					<img src="mainimages/dot-11.png"  /> 
					<a href="/hzb/NewFile1.jsp?para=10" target="_Blank10"> �û�����</a>
				</li>
				<li>
					<img src="mainimages/dot-12.png"  /> 
					<a href="/hzb/NewFile1.jsp?para=11" target="_Blank11"> Ȩ������</a>
				</li>
				<li>
					<img src="mainimages/dot-13.png"  /> 
					<a href="/hzb/NewFile1.jsp?para=12" target="_Blank12"> ���ݽ���</a>
				</li>
				<li>
					<img src="mainimages/dot-14.png"  /> 
					<a href="/hzb/NewFile1.jsp?para=13" target="_Blank13"> ָ�����</a>
				</li>
			</ul>
		</div>
		<div class="main-box">
			<div class="content-box">
			
			
				<div class="main-content"><!--֪ͨ����-->
					<h3 class="content-title">֪ͨ����<span class="content-more"><a href=""><img src="mainimages/dot-more.png" /></a></span></h3>
					<table>
					<tbody>
					<%
						for(Integer i=1;i<=8;i++){ 
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
									<%-- <a onclick="downloadPolicyFile('${notice.fileUrl}')" style="cursor:pointer;">����</a> --%>
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
						<h4 class="content-date">2017��12��12��<span class="date-1">���ڶ�</span></h4>
						<p class="content-txt"><span class="dot-tongzhi">&nbsp;</span><a href="">֪ͨ����ı���������ʾ֪ͨ����ı���������ʾ</a></p>
						<p class="content-txt"><span class="dot-tongzhi">&nbsp;</span><a href="">��ı���������ʾ</a></p>
					</div>
					<div class="content-list">
						<h4 class="content-date">2017��12��12��<span class="date-1">���ڶ�</span></h4>
						<p class="content-txt"><span class="dot-tongzhi">&nbsp;</span><a href="">��ʾ֪ͨ����ı���������ʾ</a></p>
						<p class="content-txt"><span class="dot-tongzhi">&nbsp;</span><a href="">֪����ı���������ʾ</a></p>
					</div>
					<div class="content-list">
						<h4 class="content-date">2017��12��12��<span class="date-1">���ڶ�</span></h4>
						<p class="content-txt"><span class="dot-tongzhi">&nbsp;</span><a href="">��ʾ֪ͨ����ı���������ʾ</a></p>
						<p class="content-txt"><span class="dot-tongzhi">&nbsp;</span><a href="">֪����ı���������ʾ</a></p>
					</div>
					<div class="content-list">
						<h4 class="content-date">2017��12��12��<span class="date-1">���ڶ�</span></h4>
						<p class="content-txt"><span class="dot-tongzhi">&nbsp;</span><a href="">��ʾ֪ͨ����ı���������ʾ</a></p>
						<p class="content-txt"><span class="dot-tongzhi">&nbsp;</span><a href="">֪����ı���������ʾ</a></p>
					</div> -->
					
				</div>
				
				
				<div class="main-content"><!--��������-->
					<h3 class="content-title">��������<span class="content-more"><a href=""><img src="mainimages/dot-more.png" /></a></span></h3>
					<div class="content-list ">
						<!-- <h4 class="content-date">2017��12��12��<span class="date-1">���ڶ�</span></h4> -->
						<p class="content-txt"><span class="dot-daiban">&nbsp;</span><a href="">�������ѱ���������ʾ�������ѱ���������ʾ</a></p>
						<p class="content-txt"><span class="dot-daiban">&nbsp;</span><a href="">�������ѱ���������ʾ</a></p>
					</div>
					<div class="content-list">
						<!-- <h4 class="content-date">2017��12��12��<span class="date-1">���ڶ�</span></h4> -->
						<p class="content-txt"><span class="dot-daiban">&nbsp;</span><a href="">�������ѱ���������ʾ</a></p>
						<p class="content-txt"><span class="dot-daiban">&nbsp;</span><a href="">�������ѱ���������ʾ</a></p>
					</div>
					<div class="content-list">
						<!-- <h4 class="content-date">2017��12��12��<span class="date-1">���ڶ�</span></h4> -->
						<p class="content-txt"><span class="dot-daiban">&nbsp;</span><a href="">�������ѱ���������ʾ</a></p>
						<p class="content-txt"><span class="dot-daiban">&nbsp;</span><a href="">�������ѱ���������ʾ</a></p>
					</div>
					<div class="content-list">
						<!-- <h4 class="content-date">2017��12��12��<span class="date-1">���ڶ�</span></h4> -->
						<p class="content-txt"><span class="dot-daiban">&nbsp;</span><a href="">�������ѱ���������ʾ</a></p>
						<p class="content-txt"><span class="dot-daiban">&nbsp;</span><a href="">�������ѱ���������ʾ</a></p>
					</div>
				</div>
				
				<div class="main-content"><!--���߷���-->
				
					<h3 class="content-title">���߷���<span class="content-more"><a href=""><img src="mainimages/dot-more.png" /></a></span></h3>
					<table>
					<tbody>
					<%
							for(Integer i=1;i<=8;i++){ 
												
												int length = policyList.size();
												Policy policy = new Policy();
												if(length>=i){
													policy = policyList.get(i-1);
													
													//�Ա�����д����������ȡ 
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
					
					<!-- <div class="content-list ">
						<h4 class="content-date">2017��12��12��<span class="date-1">���ڶ�</span></h4>
						<p class="content-txt"><span class="dot-zhengce">&nbsp;</span><a href="">���߷�������������߷����������</a></p>
						<p class="content-txt"><span class="dot-zhengce">&nbsp;</span><a href="">���߷����������</a></p>
					</div>
					<div class="content-list">
						<h4 class="content-date">2017��12��12��<span class="date-1">���ڶ�</span></h4>
						<p class="content-txt"><span class="dot-zhengce">&nbsp;</span><a href="">���߷����������</a></p>
						<p class="content-txt"><span class="dot-zhengce">&nbsp;</span><a href="">���߷����������</a></p>
					</div>
					<div class="content-list">
						<h4 class="content-date">2017��12��12��<span class="date-1">���ڶ�</span></h4>
						<p class="content-txt"><span class="dot-zhengce">&nbsp;</span><a href="">���߷����������</a></p>
						<p class="content-txt"><span class="dot-zhengce">&nbsp;</span><a href="">���߷����������</a></p>
					</div>
					<div class="content-list">
						<h4 class="content-date">2017��12��12��<span class="date-1">���ڶ�</span></h4>
						<p class="content-txt"><span class="dot-zhengce">&nbsp;</span><a href="">���߷����������</a></p>
						<p class="content-txt"><span class="dot-zhengce">&nbsp;</span><a href="">���߷����������</a></p>
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
//��֪ͨ��������ҳ�� 
function noticeInfo(id){
	//var para = "14@@@"+id
	//window.location ="/hzb/NewFile1.jsp?para=1";
	window.open("/hzb/NewFile1.jsp?para=notice"+id+"", "_blank");  
	//location.reload(); 			//��֪ͨ��������ҳ��֮ǰ��ˢ��indexҳ�� 
<%--noticeAddTab('֪ͨ��������',id,'<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.notice.NoticeInfo',false, false);--%>
}
//�������߷����ļ� 
//encodeURI��������urlת�룬������Ĵ����������� ����̨���յ�ʱ�������ת�봦��ת������
function downloadPolicyFile(fileurl){
	
	window.location="<%=request.getContextPath()%>/PublishFileServlet?method=downloadPolicyFile&filePath="+encodeURI(encodeURI(fileurl));
}
</script>
	</body>
</html>