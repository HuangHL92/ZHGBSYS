<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.insigma.siis.local.pagemodel.zj.Utils"%>
<%@page import="java.util.ArrayList"%>
<%@page import="net.sf.json.JSONArray"%>
<%@page import="java.util.List"%>
<%@page import="java.lang.String"%>
<%@page import="java.lang.Object"%>
<%@page import="com.insigma.odin.framework.persistence.HBUtil"%>
<%@page import="com.insigma.odin.framework.persistence.HBSession"%>
<%@page import="com.insigma.siis.local.business.helperUtil.SysManagerUtils"%>
<%@page import="com.insigma.odin.framework.safe.SafeControlCenter"%>
<%@page import="com.insigma.odin.framework.safe.util.SafeConst"%>
<%@page import="com.insigma.odin.framework.util.ExtGlobalNames"%>
<%@page import="com.insigma.odin.framework.util.SysUtil"%>

<%
	//request.getSession().setAttribute("LoginScene","sce:USERNAME;USER_PASS");
	if(SysUtil.isWorkpf(request)){
		out.println("<html>");
		out.println("<head></head>");
		out.println("<body>");
		out.println("<script language='JavaScript'>");
		out.println("qtweb.reLogin();");
		out.println("</script>");
		out.println("</body>");
		out.println("</html>");
	}else{
		
		//获取
		Date d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String token = Utils.encryption("zxf" + SysManagerUtils.getUserloginName() + sdf.format(d));
		
		HBSession sess = HBUtil.getHBSession();
		List<Object[]> li =  (List<Object[]>)sess.createSQLQuery("select aaa001,aaa005 from aa01 where aaa001 in ('GB_ADDR','GBJY_ADDR','GBJD_ADDR','ETL_ADDR','NQGB_ADDR')").list();
		String gb_ip = "";
		String gbjy_ip = "";
		String gbjd_ip = "";
		String etl_ip = "";
		String nqgb_ip = "";
		for(Object[] vals : li){
			if("GB_ADDR".equals(vals[0])){
				gb_ip = ""+vals[1];
				continue;
			}
			if("GBJY_ADDR".equals(vals[0])){
				gbjy_ip = ""+vals[1];
				continue;
			}
			if("GBJD_ADDR".equals(vals[0])){
				gbjd_ip = ""+vals[1];
				continue;
			}
			if("ETL_ADDR".equals(vals[0])){
				etl_ip = ""+vals[1];
				continue;
			}
			if("NQGB_ADDR".equals(vals[0])){
				
				nqgb_ip = ""+vals[1];
				continue;
			}
		}
		//gb_ip="127.0.0.1:8081/hzb";
		List<String> list = new ArrayList<String>();
		list = sess.createSQLQuery("select s.platformid from SMT_PLATFORM s where s.userid = '"+SysManagerUtils.getUserId()+"'").list();
		JSONArray.fromObject(list.toArray());
%>

<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE8" > 
<link rel="icon" href="<%=request.getContextPath()%>/image/logo_wx.png" type="image/x-icon" />
<link rel="shortcut icon" href="<%=request.getContextPath()%>/image/logo_wx.png" type="image/x-icon" />
<title>智慧党建综合大数据平台</title>
<!-- <link rel = "Shortcut Icon" href="icos/hzb.ico"> </link> -->
<script src="basejs/md5.js"> </script>
<script src="basejs/jquery/jquery-1.7.2.min.js"> </script>
<link  href="<%=request.getContextPath()%>/css/newview/djzonghe.css" type="text/css" rel="stylesheet"/>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/ext/adapter/ext/ext-base.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/ext/ext-all.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>

<script type="text/javascript">
var v = '<%=JSONArray.fromObject(list.toArray()) %>';
var gb_ip = '<%=gb_ip %>';
var gbjy_ip = '<%=gbjy_ip %>';
var gbjd_ip = '<%=gbjd_ip %>';
var etl_ip = '<%=etl_ip %>';
var nqgb_ip = '<%=nqgb_ip %>';

$(function(){
  //$("#bodyDiv").css("filter","progid:DXImageTransform.Microsoft.AlphaImageLoader(src='image/login.png',sizingMethod='scale')");
  //判断浏览器是否支持placeholder属性
  supportPlaceholder='placeholder'in document.createElement('input'),
  placeholder=function(input){
    var text = input.attr('placeholder'),
    defaultValue = input.defaultValue;
    if(!defaultValue){
      input.val(text).addClass("phcolor");
    }
    input.focus(function(){
      if(input.val() == text){
        $(this).val("");
      }
    });
 
    input.blur(function(){
      if(input.val() == ""){
        $(this).val(text).addClass("phcolor");
      }
    });
    //输入的字符不为灰色
    input.keydown(function(){
      $(this).removeClass("phcolor");
    });
  };
  
  //当浏览器不支持placeholder属性时，调用placeholder函数
  if(!supportPlaceholder) {
    $('input').each(function(){
      text = $(this).attr("placeholder");
      if($(this).attr("type") == "text" || $(this).attr("type") == "password"){
        placeholder($(this));
      }
    });
  }
}); 
</script> 
</head>
<%
	SafeControlCenter.getInstance(SafeConst.PDT_INSIIS).safeValidate(request,SafeConst.VT_APPCONTEXT,SafeConst.PDT_INSIIS_COMP_ODIN);
	SafeControlCenter.getInstance().safeValidate(SafeConst.VT_LOGINCOUNT,SafeConst.PDT_INSIIS_COMP_ODIN);
	SafeControlCenter.getInstance().safeValidate(SafeConst.VT_APPSERVER,SafeConst.PDT_INSIIS_COMP_ODIN);
	SafeControlCenter.getInstance().safeValidate(SafeConst.VT_EXPIRATION,SafeConst.PDT_INSIIS_COMP_ODIN);
%>
<body>
<!--头部-->
		<div class="djmain-top">
			<h1 class="djmain-title"><span class="djmain-log"></span>贵州省智慧党建大数据平台</h1>
			<div class="djmain-login" >
				<div id="displayPicture" class="dj-login-img">
				<!-- <img src="${ctxStatic}/index/images/tmp-photo.png" /> -->
				<img src="images/newview/user1.jpg" class="user-image" style="cursor: pointer;">
				<div id="userDropDown">
					<ul>
						<li><a href="#" onclick="openChangePasswordWin3();return false;"><span></span> 修改密码</a></li>
						<li><a href="#" onclick="logout();return false;"><span></span> 退出系统</a></li>
					</ul>
				</div>
				<!-- <ul class="dropdown-menu server-activity">
					<li>
						<p>
							<i class="fa fa-lock text-info"></i><span
								onclick="openChangePasswordWin3();return false;">修改密码</span>
						</p>
					</li>
					<li>
						<div class="demo-btn-group clearfix">
							<button class="btn btn-danger" onclick="logout();return false;">退出系统</button>
						</div>
					</li>
				</ul> -->
				</div>
				<div class="dj-login-info">
					<p class="dj-login-name"><%=SysManagerUtils.getUserName()%></p>
				</div>
			</div>
		</div>
		<!--中间banner搜索区-->
		<div class="dj-banner-box">
			<div class="dj-search">
				<div class="dj-search-content">
					
					
					
				</div>
			</div>
		</div>
		<!--菜单-->
		<div class="dj-menu-box" style="margin-top: 3.5%">
			<ul class="dj-menu-list" >
				<!--<li onclick="login5()">
					<img src="${ctxStatic}/index/images/dot-main1.png"  />
					<p>综合办公</p>
				</li>-->
			<%if(list.size()!=0&&list.contains("gb1")){%>
				<li onclick="login6()">
					<img src="images/newview/dot-main2.png"  />
					<p>干部信息管理</p>
				</li>
			<% } %>
			<%if(list.size()!=0&&list.contains("gb2")){%>
				<li onclick="login7()">
					<img src="images/newview/dot-main7.png"  />
					<p>干部任免管理</p>
				</li>
			<% } %>
				<!--<li onclick="login2()">
					<img src="${ctxStatic}/index/images/dot-main3.png"  />
					<p>基层党建</p>
				</li>
				-->
			<%if(list.size()!=0&&list.contains("gb3")){%>
				<li onclick="loginpx()">
					<img src="images/newview/dot-main4.png"  />
					<p>干部教育培训</p>
				</li>
			<% } %>
			<%if(list.size()!=0&&list.contains("gb4")){%>
				<li onclick="login4()">
					<img src="images/newview/dot-main6.png"  />
					<p>干部监督</p>
				</li>
			<% } %>
				<!-- <li onclick="loginpx()">
					<img src="${ctxStatic}/index/images/dot-main1.png"  />
					<p>班子研判</p>
				</li> -->
			<%if(list.size()!=0&&list.contains("gb5")){%>
				<li onclick="loginLeader()">
					<img src="images/newview/dot-main11.png"  />
					<p>信息综合分析</p>
				</li>
			<% } %>
			<%if(list.size()!=0&&list.contains("gb6")){%>
				<li onclick="loginSeek()">
					<img src="images/newview/dot-main8.png"  />
					<p>干部信息查询</p>
				</li>
			<% } %>
			<%if(list.size()!=0&&list.contains("gb7")){%>
				<li onclick="loginetl()">
					<img src="images/newview/dot-main9.png"  />
					<p>数据中心</p>
				</li>
			<% } %>
			<%if(list.size()!=0&&list.contains("gb8")){%>
				<li onclick="loginF()">
					<img src="images/newview/dot-main9.png"  />
					<p>家庭成员</p>
				</li>
			<% } %>
		<%if(list.size()!=0&&list.contains("gb9")){%>
				<li onclick="login9()">
					<img src="images/newview/gb9.png"  />
					<p>年轻干部管理</p>
				</li>
			<% } %>
<%-- 			<%if(list.size()!=0&&list.contains("gb10")){%> --%>
<!-- 				<li onclick="loginH()"> -->
<!-- 					<img src="images/newview/dot-main9.png"  /> -->
<!-- 					<p>上会议题</p> -->
<!-- 				</li> -->
<%-- 			<% } %> --%>
				<!--<li onclick="loginSetup()">
					<img src="${ctxStatic}/index/images/dot-main10.png"  />
					<p>系统管理</p>
				</li>
				 <li onclick="login4()">
					<img src="${ctxStatic}/index/images/dot-main6.png"  />
					<p><a href="">身份认证</a></p>
				</li> -->
			</ul>
		</div>
		
		<div style="display:none">
		<form id="submitform" name="submitform" method="post" target="myframe">
			<input type=text name="username" id="username" size="18" value="" ><br>
			<input type="text" name="params" id="params" size="19" value=""/>  
			<input type=submit id="submit1" name="submit1" value="提交" >  
		</form> 
		</div>





  	

<sicp3:errors />
<script language="JavaScript">
//干部信息 gb1
function login6(){ 
	if(v&&(v.indexOf("gb1")!=-1)){
		var u = '<%=SysManagerUtils.getUserloginName() %>';
		var url = 'http://'+gb_ip+'/logonAction.do';
		var params = "{'username':'"+u+"','password':'','scene':'','ou1':'undefined','ou2':'undefined','sign':'ganbu'}"; 
		
		try{
			$("#submitform").attr("action",url);
			$("#submitform").attr("target","_blank");
			$("#params").val(params);
			$("#username").val(u);
			$("#submit1").click();
		}catch(e){
			alert(e);
		}
	}else{
		alert("您没有登录该平台的权限！如需登录，请联系管理员授权！");
	}
}

//干部任免 gb2
function login7(){
	if(v&&(v.indexOf("gb2")!=-1)){
		var u = '<%=SysManagerUtils.getUserloginName() %>';
		var url = 'http://'+gb_ip+'/logonAction.do';
		var params = "{'username':'"+u+"','password':'','scene':'','ou1':'undefined','ou2':'undefined','sign':'renmian'}"; 
		
		try{
			$("#submitform").attr("action",url);
			$("#submitform").attr("target","_blank");
			$("#params").val(params);
			$("#username").val(u);
			$("#submit1").click();
		}catch(e){
			alert(e);
		}
	}else{
		alert("您没有登录该平台的权限！如需登录，请联系管理员授权！");
	}
}

//干部教育培训 gb3
function loginpx() {
	if(v&&(v.indexOf("gb3")!=-1)){
	    var sessionUser="";
	    var u = '<%=SysManagerUtils.getUserloginName() %>';
		var t = '<%=token %>';
		var url = "\"http://"+gbjy_ip+"/gbjypx/sso/"+u+"/"+t+"?ie=utf-8&f=8&rsv_bp=1&rsv_idx=1&url=/a/index&tn=baidu&oq=post%2520%25E6%2594%25B9%25E6%2588%2590%2520get&rsv_pq=ba1c542d002063c6&rsv_t=2826sMSwOwxtb0tpCaQnZQREz10tg1yehuBNz0KWrZ3J2OxS%2B3sd7sM93UI&relogin=true&rqlang=cn&rsv_enter=0&rsv_dl=tbsugie%25E8%25B7%25B3%25E8%25BD%25AC%25E5%2588%25B0%25E5%2585%25B6%25E4%25BB%2596%25E6%25B5%258F%25E8%25A7%2588%25E5%2599%25A8&inputT=1420&rsv_n=2&rsv_sug3=51&rsv_sug4=1421&&rsv_t=2826sMSwOwxtb0tpCaQnZQREz10tg1yehuBNz0KWrZ3J2OxS%2B3sd7sM93UI&rqlang=cn&rsv_enter=0&rsv_dl=tb&sug=ie%25E8%25B7%25B3%25E8%25BD%25AC%25E5%2588%25B0%25E5%2585%25B6%25E4%25BB%2596%25E6%25B5%258F%25E8%25A7%2588%25E5%2599%25A8&inputT=1420&rsv_n=2&rsv_sug3=51&rsv_sug4=1421\"";
		openFirefox(url);
	    //window.open("http://"+gbjy_ip+"/gbjypx/sso/"+u+"/"+t+"?url=/a/index&relogin=true");
	}else{
		alert("您没有登录该平台的权限！如需登录，请联系管理员授权！");
	}
}

//干部监督 gb4
function login4(){
	if(v&&(v.indexOf("gb4")!=-1)){
		var u = '<%=SysManagerUtils.getUserloginName() %>';
		var url = "http://"+gbjd_ip+"/logonAction.do";
		var params = "{'username':'"+u+"','password':'','scene':'','ou1':'undefined','ou2':'undefined','sign':'renmian'}"; 
		
		try{
			$("#submitform").attr("action",url);
			$("#submitform").attr("target","_blank");
			$("#params").val(params);
			$("#username").val(u);
			$("#submit1").click();
		}catch(e){
			alert(e);
		}
	}else{
		alert("您没有登录该平台的权限！如需登录，请联系管理员授权！");
	}
}

//信息综合分析 gb5
function loginLeader(){
	if(v&&(v.indexOf("gb5")!=-1)){
		var u = '<%=SysManagerUtils.getUserloginName() %>';
		var url = "\"http://"+gb_ip+"/logonAction.do?ie=utf-8&f=8&rsv_bp=1&rsv_idx=1&tn=baidu&oq=post%2520%25E6%2594%25B9%25E6%2588%2590%2520get&rsv_pq=ba1c542d002063c6&rsv_t=2826sMSwOwxtb0tpCaQnZQREz10tg1yehuBNz0KWrZ3J2OxS%2B3sd7sM93UI&rqlang=cn&rsv_enter=0&rsv_dl=tbsugie%25E8%25B7%25B3%25E8%25BD%25AC%25E5%2588%25B0%25E5%2585%25B6%25E4%25BB%2596%25E6%25B5%258F%25E8%25A7%2588%25E5%2599%25A8&inputT=1420&rsv_n=2&rsv_sug3=51&rsv_sug4=1421&username="+u+"&params={%27username%27:%27"+u+"%27,%27password%27:%27%27,%27scene%27:%27%27,%27ou1%27:%27undefined%27,%27ou2%27:%27undefined%27,%27sign%27:%27banzi%27}&rsv_t=2826sMSwOwxtb0tpCaQnZQREz10tg1yehuBNz0KWrZ3J2OxS%2B3sd7sM93UI&rqlang=cn&rsv_enter=0&rsv_dl=tb&sug=ie%25E8%25B7%25B3%25E8%25BD%25AC%25E5%2588%25B0%25E5%2585%25B6%25E4%25BB%2596%25E6%25B5%258F%25E8%25A7%2588%25E5%2599%25A8&inputT=1420&rsv_n=2&rsv_sug3=51&rsv_sug4=1421\"";
		openFirefox(url);
		//var url = 'http://'+gb_ip+'/logonAction.do';
		//var url = 'http://127.0.0.1:61080/hzb/logonAction.do';
		//var params = "{'username':'"+u+"','password':'','scene':'','ou1':'undefined','ou2':'undefined','sign':'banzi'}"; 
		/* try{
			$("#submitform").attr("action",url);
			$("#submitform").attr("target","_blank");
			$("#params").val(params);
			$("#username").val(u);
			$("#submit1").click();
		}catch(e){
			alert(e);
		} */
	}else{
		alert("您没有登录该平台的权限！如需登录，请联系管理员授权！");
	}
}

function openFirefox(url){
    // ActiveObject仅在IE下可创建
    var objShell = new ActiveXObject("WScript.Shell");
    // 注意这里是/c，不可使用/k，否则资源不会释放
    var cmd = "cmd.exe /c start firefox " + url;
    objShell.Run(cmd, 0, true);
}

//干部信息查询 gb6
function loginSeek(){
	if(v&&(v.indexOf("gb6")!=-1)){
		var u = '<%=SysManagerUtils.getUserloginName() %>';
		var url = 'http://'+gb_ip+'/logonAction.do';
		var params = "{'username':'"+u+"','password':'','scene':'','ou1':'undefined','ou2':'undefined','sign':'zhonghechaxun'}"; 
		
		try{
			$("#submitform").attr("action",url);
			$("#submitform").attr("target","_blank");
			$("#params").val(params);
			$("#username").val(u);
			$("#submit1").click();
		}catch(e){
			alert(e);
		}
	}else{
		alert("您没有登录该平台的权限！如需登录，请联系管理员授权！");
	}
}

//数据中心
function loginetl(){
	if(v&&(v.indexOf("gb7")!=-1)){
		var username="EAgi6sfdQG2x+DnH3LLb21ish2LAJBUQI3uUHQjOK7w8HHZ60g50OXniz5MvW1BEHisHJqHjTCCH9goFtq7bTQ==";
	    var password="k252PB6K/FTtaosfZCaTMUnlv/rl8u9O1lnsk7UzJ6oyaSAs5PzlqkNLQv4kCA9nJXVM+3/5Teat1Ll0KEyC9A==";
	    if(username==""){
	        js.showMessage("没有权限");
	        return;
	    }
	    username=encodeURI(username).replace(/\+/g, '%2B');
	    password=encodeURI(password).replace(/\+/g, '%2B');
	    window.open("http://"+etl_ip+"/DataSourcePortal/LogonDialog.jsp?u="+username+"&p="+password);
	}else{
		alert("您没有登录该平台的权限！如需登录，请联系管理员授权！");
	}
}
//家庭成员
function loginF(){ 
	if(v&&(v.indexOf("gb8")!=-1)){
		var u = '<%=SysManagerUtils.getUserloginName() %>';
		var url = 'http://'+gb_ip+'/logonAction.do';
		var params = "{'username':'"+u+"','password':'','scene':'','ou1':'undefined','ou2':'undefined','sign':'Family'}"; 
		//alert(url)
		try{
			$("#submitform").attr("action",url);
			$("#submitform").attr("target","_blank");
			$("#params").val(params);
			$("#username").val(u);
			$("#submit1").click();
		}catch(e){
			alert(e);
		}
	}else{
		alert("您没有登录该平台的权限！如需登录，请联系管理员授权！");
	}
}
//年轻干部管理 gb9
function login9(){
	if(v&&(v.indexOf("gb9")!=-1)){
		var u = '<%=SysManagerUtils.getUserloginName() %>';
		var url = 'http://'+nqgb_ip+'/logonAction.do';
		var params = "{'username':'"+u+"','password':'','scene':'','ou1':'undefined','ou2':'undefined','sign':'renmian'}"; 
		try{
			$("#submitform").attr("action",url);
			$("#submitform").attr("target","_blank");
			$("#params").val(params);
			$("#username").val(u);
			$("#submit1").click();
		}catch(e){
			alert(e);
		}
	}else{
		alert("您没有登录该平台的权限！如需登录，请联系管理员授权！");
	}
}
//上会议题
function loginH(){ 
		if(v&&(v.indexOf("gb10")!=-1)){
			var u = '<%=SysManagerUtils.getUserloginName() %>';
			//var url = 'http://'+gb_ip+'/logonAction.do';
			//var params = "{'username':'"+u+"','password':'','scene':'','ou1':'undefined','ou2':'undefined','sign':'huiyi'}"; 
			//alert(url)
			var url = "\"http://"+gb_ip+"/logonAction.do?ie=utf-8&f=8&rsv_bp=1&rsv_idx=1&tn=baidu&oq=post%2520%25E6%2594%25B9%25E6%2588%2590%2520get&rsv_pq=ba1c542d002063c6&rsv_t=2826sMSwOwxtb0tpCaQnZQREz10tg1yehuBNz0KWrZ3J2OxS%2B3sd7sM93UI&rqlang=cn&rsv_enter=0&rsv_dl=tbsugie%25E8%25B7%25B3%25E8%25BD%25AC%25E5%2588%25B0%25E5%2585%25B6%25E4%25BB%2596%25E6%25B5%258F%25E8%25A7%2588%25E5%2599%25A8&inputT=1420&rsv_n=2&rsv_sug3=51&rsv_sug4=1421&username="+u+"&params={%27username%27:%27"+u+"%27,%27password%27:%27%27,%27scene%27:%27%27,%27ou1%27:%27undefined%27,%27ou2%27:%27undefined%27,%27sign%27:%27huiyi%27}&rsv_t=2826sMSwOwxtb0tpCaQnZQREz10tg1yehuBNz0KWrZ3J2OxS%2B3sd7sM93UI&rqlang=cn&rsv_enter=0&rsv_dl=tb&sug=ie%25E8%25B7%25B3%25E8%25BD%25AC%25E5%2588%25B0%25E5%2585%25B6%25E4%25BB%2596%25E6%25B5%258F%25E8%25A7%2588%25E5%2599%25A8&inputT=1420&rsv_n=2&rsv_sug3=51&rsv_sug4=1421\"";
		    openFirefox(url);

/* 			try{
				$("#submitform").attr("action",url);
				$("#submitform").attr("target","_blank");
				$("#params").val(params);
				$("#username").val(u);
				$("#submit1").click();
			}catch(e){
				alert(e);
			} */
		}else{
			alert("您没有登录该平台的权限！如需登录，请联系管理员授权！");
		}
	}
/**
 * 修改密码
 */
function openChangePasswordWin3(){
	//radow.util.openWindow('pswin','pages.sysmanager.user.PsWindow');
	$h.openPageModeWin('pswin','pages.sysmanager.user.PsWindow','修改密码',485,340,'','<%=request.getContextPath()%>');
}

function logout(){
	var val = document.location.host;
	var vv = val.split(':');
	var ip = vv[0];
	var url = 'http://'+gb_ip+'/';
	window.location.href = url;
}

$("#displayPicture").hover(function(){
	$("#userDropDown").css("display","block");
},function(){})
$("#userDropDown").hover(function(){},function(){
	$("#userDropDown").css("display","none");
});
</script>
</body>
</html>
<%
	}
%>