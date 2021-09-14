<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/js/jquery-1.8.2.js"></script>
<%@page import="org.hibernate.transform.Transformers"%>
<%@page import="com.insigma.odin.framework.persistence.HBUtil"%>
<%@page import="com.insigma.odin.framework.persistence.HBSession"%>
<%@page import="com.insigma.siis.local.business.entity.Notice"%>
<%@page import="com.insigma.siis.local.business.entity.NoticeFileVo"%>
<%@page import="com.insigma.odin.framework.privilege.vo.UserVO"%>
<%@page import="com.insigma.odin.framework.privilege.PrivilegeManager"%>
<%@ page language="java" import="java.util.*" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<style>
html,body{height:100%;}
body{
overflow-x: hidden ! important;
width:100%;
min-width:1000px;
min-height:500px;
overflow-x: hidden ! important;
background:#ebeef6 !important;
font-size:14px;
font-family: "微软雅黑"
}
.tzgg_box{
   width:100%;
   margin-top:10px;
   margin-left:15px;
   background:#fff;
}
/*以下是通知公告详情页面的样式*/
.tzgg_con_title{height:80px;line-height:48px;text-align:center; font-size:24px; font-weight:bold; }
.tzgg_con_sub{height:40px;line-height:24px;text-align:center; font-size:14px; font-weight:normal;color:#333;border-bottom:1px solid #f3f3f3;}
.tzgg_info{width:88%;margin-top:20px;margin-bottom:20px;margin-left:7%;}
.tzgg_info p{font-size:14px; line-height:36px; text-indent:2em;}
.tzgg_other{width:88%;margin-top:20px;margin-bottom:20px;margin-left:7%;height:48px; font-size:12px;}
.tzgg_view{width:70%;float:left;}
.tzgg_option{width:25%;float:right;text-align:right;}
.tzgg_option a{margin-left:5px;margin-right:5px;color:#0c7eb3;text-decoration: none}
</style>

<%
HBSession sess = HBUtil.getHBSession();


//通知公告详情
String id = request.getParameter("id");

Notice notice = (Notice)sess.get(Notice.class, id);

request.setAttribute("notice", notice);

//查询出所有的附件
String sqlFlie = "select id,noticeId,fileName,fileUrl,fileSize from NoticeFile where noticeId ='"+id+"'";
List<NoticeFileVo> noticeFileList = sess.createSQLQuery(sqlFlie).setResultTransformer(Transformers.aliasToBean(NoticeFileVo.class)).list();
request.setAttribute("noticeFileList", noticeFileList);

//标记为已查看
UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
String sql = "UPDATE NOTICERECIPIENT SET SEE = '1' WHERE recipientid = '"+user.getId()+"' and noticeid = '"+id+"'";
sess.createSQLQuery(sql).executeUpdate();
sess.flush();


%>
<script type="text/javascript" src="basejs/pageUtil.js"></script>
<body>
<!-- 
<div style="height: 100%;" align="center">
	<div style="height: 80px;width: 80%;">
		<h2 style="padding-top: 28px;">${notice.title}</h2>
	</div>
	<div style="background-color: white;height: 70%;width: 80%;" align="justify">
		${notice.text}
	</div>
	<div style="height: 80px;width: 80%;"></div>
	<div style="height: 40px;width: 80%;" align="left">
		<span>创建人：${notice.a0000Name}</span>
		<span style="padding-left: 25px;">更新时间：${notice.updateTime}</span>
		<span style="padding-left: 25px;">附件：
			<% 
// 			for(Integer i=1;i<=noticeFileList.size();i++){ 
								
// 				int length = noticeFileList.size();
// 				NoticeFileVo noticeFile = new NoticeFileVo();
// 				if(length>=i){
// 					noticeFile = noticeFileList.get(i-1);
// 				}
// 				request.setAttribute("noticeFile", noticeFile);
			%>
				<a href="javascript:downloadNoticeFile('${noticeFile.fileUrl}')">${noticeFile.fileName}</a>
			<%//} %>
		</span>
	</div>
</div>
 -->
 <div class="tzgg_box">
  <h3 class="tzgg_con_title">${notice.title}</h3>
  <h5 class="tzgg_con_sub">发布时间：${notice.updateTime} &nbsp;&nbsp;&nbsp;&nbsp;发布人：${notice.a0000Name}</h5>
  <div class="tzgg_info"><!-- 通知公告的正文 -->
    	
    	
    	<textarea style="height:100%;width: 100%;" id="contenttext" style="border-style: hidden;" readonly="readonly">${notice.text}</textarea>
  </div>
  <div class="tzgg_other"><!-- 底部附加信息 -->
  <!-- <p class="tzgg_view">附件：<span>200次</span></p> -->
  <span style="padding-left: 25px;">附件：
			<% 
 			for(Integer i=1;i<=noticeFileList.size();i++){ 
								
 				int length = noticeFileList.size();
 				NoticeFileVo noticeFile = new NoticeFileVo();
 				if(length>=i){
 					noticeFile = noticeFileList.get(i-1);
 				}
 				request.setAttribute("noticeFile", noticeFile);
			%>
				<a href="javascript:downloadNoticeFile('${noticeFile.fileUrl}')">${noticeFile.fileName}</a>
			<%} %>
		</span>
  
  <!-- <p class="tzgg_option">页面功能：<a href="">[推荐]</a><a href="">[打印]</a> <a href="">[关闭]</a></p> -->
  </div>
</div>
</body>
 <script type="text/javascript">  
	//下载通知公告附件
	//encodeURI，用来做url转码，解决中文传输乱码问题 （后台接收的时候会再做转码处理，转回来）
	function downloadNoticeFile(fileurl){
		window.location="PublishFileServlet?method=downloadPolicyFile&filePath="+encodeURI(encodeURI(fileurl));
	}
	
	//parent.parent.location.reload();
	
</script>