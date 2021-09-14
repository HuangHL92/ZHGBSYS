<!DOCTYPE html>
<%@page import="com.insigma.odin.framework.persistence.HBUtil"%>
<%@page import="com.insigma.odin.framework.persistence.HBSession"%>
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
<%@page import="com.utils.DBUtils"%>
<%@page import="com.insigma.siis.local.business.helperUtil.SysManagerUtils"%>
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
	
	String tabId = request.getParameter("tabId");
	//System.out.println("1111111111111111111111111111111111111111111111"+tabId);
	
	String[] yh = CodeType2js.getFpInfo(request);
	
	String gbSign = (String)request.getSession().getAttribute("gbSign");
	if(gbSign==null||"".equals(gbSign)){
		gbSign = "ganbu";
	}else if("ganbu".equals(gbSign)){
		gbSign = "ganbu";
	}else if("xitong".equals(gbSign)){
		gbSign = "xitong";
	}else if("renmian".equals(gbSign)){
		gbSign = "renmian";
	}else if("zhonghechaxun".equals(gbSign)){
		gbSign = "zhonghechaxun";
	}else if("banzi".equals(gbSign)){
		gbSign = "banzi";
	}else if("huiyi".equals(gbSign)){
		gbSign = "huiyi";
	}
	//System.out.println("11111111111111111111111111111111111111111111111111111111");
	HBSession sess = HBUtil.getHBSession();
	Object obj  =  (Object)sess.createSQLQuery("select aaa005 from aa01 where aaa001 = 'SJDP_ADDR'").uniqueResult();
	List<Object[]> li =  (List<Object[]>)sess.createSQLQuery("select aaa001,aaa005 from aa01 where aaa001 = 'QDTJ'").list(); 
	Object[] o1 =  li.get(0);
	String url1 = o1[1].toString();
	
	boolean gbmc = true;
	if(!DBUtils.isNoGbmc(SysManagerUtils.getUserId())) {
		gbmc = false;
	}
	
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<meta content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0" name="viewport">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta http-equiv="x-ua-compatible" content="IE=EmulateIE8" > 
<link rel="icon" href="<%=request.getContextPath()%>/image/logo-hz.png" type="image/x-icon" />
<link rel="shortcut icon" href="<%=request.getContextPath()%>/image/logo-hz.png" type="image/x-icon" />
<%
if("ganbu".equals(gbSign)){
	%>
	<title>�ɲ���Ϣ����</title>
	<%
}else if("xitong".equals(gbSign)){
	%>
	<title>ϵͳ����</title>
	<%
}else if("Family".equals(gbSign)){
	%>
	<title>��ͥ��Ա   </title>
	<%
}else if("renmian".equals(gbSign)){
	%>
	<title>�ɲ��������</title>
	<%
}else if("zhonghechaxun".equals(gbSign)){
	%>
	<title>�ɲ��ۺϲ�ѯ</title>
	<%
}else if("banzi".equals(gbSign)){
	%>
	<title>�ɲ���Ϣ�ۺϷ���</title>
	<%
}else if("huiyi".equals(gbSign)){
	%>
	<title>�ϻ�����</title>
	<%
}
%>
<script type="text/javascript">
var g_contextpath = '<%=request.getContextPath()%>';
</script>
<odin:head/>

<link rel="stylesheet" type="text/css" href="mainPage/css/font-awesome.css">
<!--[if lte IE 7]>
<link rel="stylesheet" type="text/css" href="mainPage/css/font-awesome-ie7.min.css">    
<![endif]-->
<link rel="stylesheet" type="text/css" href="mainPage/css/bootstrap.min.css"> 
<link rel="stylesheet" type="text/css" href="mainPage/css/new.css">  
<script type="text/javascript" src="mainPage/js/jquery.js"></script>
<script type="text/javascript" src="mainPage/js/bootstrap.min.js"></script>
<script type="text/javascript" src="basejs/dateutil.js"></script>
<script type="text/javascript" src="radow/corejs/radow.util.js"></script>
<script type='text/javascript' src='js/dropdown.js'></script>

<script type="text/javascript">
function getIdexPage(){
	var m = CodeTypeTree.tree.children;
	var gbgltitle = "";
	for(var i=0;i<m.length;i++){
		if(m[i].id=='40287d815451830d0154519c343e0007'){
			gbgltitle = m[i].title;
			break;
		}
	}
	var groupid = '<%=SysManagerUtils.getDeptId() %>';
	var userid = '<%=SysManagerUtils.getUserId() %>';
	if(userid=='297e9b3367154ab2016716a77abe0ca2'||userid=='402882846f6a49c9016f6a7d6e7b000a'){//managec,manageb
		return [{
			title : '��־����',
			closable : false,
			id : '402881f95446af54015446b14de70003',
			iconCls : 'iconwelcome',
			html : '<Iframe id="i_402881f95446af54015446b14de70003" width="100%" height="100%" scrolling="auto" frameborder="0" src="'
				+ g_contextpath
				+ '/radowAction.do?method=doEvent&pageModel=pages.sysmanager.ZWHZYQ_001_004.LogManage'
				+ '" style="clear:both;margin-left:0px;margin-right:0px;float:right;"></Iframe>'
		}];
	}
	//�ɲ�����
	if(groupid=='9cf7df4b9c76453a826ee27e6c4f577e' || userid=='4028468172a3a59b0172a3a8acaf0006'|| groupid=='824751e5191344de859d79be5a50d193'||userid=='40288103556cc97701556d629135000f'){
	//if(1==2){
		return [{
			title : '����̨',
			closable : false,
			id : 'wkstg',
			iconCls : 'iconwelcome',
			html : '<Iframe id="gzt" width="100%" height="100%" scrolling="auto" frameborder="0" src="'
				+ g_contextpath
				+ '/radowAction.do?method=doEvent&pageModel=pages.amain.GBMain'
				+ '" style="clear:both;margin-left:0px;margin-right:0px;float:right;"></Iframe>'
		}];
	}else{
		return [{
			title : gbgltitle,
			closable : false,
			id : '40287d815451830d0154519c343e0007',
			iconCls : 'iconwelcome',
			html : '<Iframe id="i40287d815451830d0154519c343e0007" width="100%" height="100%" scrolling="auto" frameborder="0" src="'
				+ g_contextpath
				+ '/radowAction.do?method=doEvent&pageModel=pages.customquery.CustomQuery'
				+ '" style="clear:both;margin-left:0px;margin-right:0px;float:right;"></Iframe>'
		}];
	}
}

</script>


<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery.url.js"></script>

<style type="text/css">
/* .x-panel-bwrap, .x-panel-body{
overflow: visible; 
}
*/
/* .x-panel{height:64px!important;} */
.header-height{
	height: 63px!important;
}
.iconwelcome{ 
	background-image:url("images/welcome.gif") !important;	
}
.x-tab-panel{
	top: 64px !important;
}
.pull-right{width: 160px!important;}
.header{width:100%;min-width:1000px;height:63px;overflow-y:visible;}
</style>
</head>
<body style="position:relative;">
<odin:base></odin:base>
<odin:window src="/blank.htm" id="pswin"  width="485" height="340" title="�޸�����"></odin:window>
<odin:window src="/blank.htm" id="ListOutPutWin" width="750" height="580" title="ģ�����"></odin:window>
<iframe id="xbdjWidow" src="" style="width: 100%;height: 100%;display: none; "></iframe>
<div id="header" class="header">
		<div id="header2">
			<!-- <div class="logo2" style="font-family:'΢���ź�';display:inline-block;overflow:hidden;">�����ɲ�������Ϣϵͳ</div> -->
			<%
				if("ganbu".equals(gbSign)){
			%>
			<div class="logo2" style="font-family:'΢���ź�';display:inline-block;overflow:hidden;position: relative;">
			<font style="margin-left: 16px;">�������Ǹɲ�</font>
			
			
			
			</div>
			<%
				}else if("xitong".equals(gbSign)){
			%>
			<div class="logo2" style="font-family:'΢���ź�';display:inline-block;overflow:hidden;"><font style="margin-left: 16px;">ϵͳ����</font></div>
			<%
			}else if("renmian".equals(gbSign)){
			%>
			<div class="logo2" style="font-family:'΢���ź�';display:inline-block;overflow:hidden;"><font style="margin-left: 16px;">�ɲ��������</font></div>
			<%
			}else if("zhonghechaxun".equals(gbSign)){
				%>
			<div class="logo2" style="font-family:'΢���ź�';display:inline-block;overflow:hidden;"><font style="margin-left: 16px;">�ɲ��ۺϲ�ѯ</font></div>
				<%
			}else if("banzi".equals(gbSign)){
				%>
			<div class="logo2" style="font-family:'΢���ź�';display:inline-block;overflow:hidden;"><font style="margin-left: 16px;">�ɲ���Ϣ�ۺϷ���</font></div>
				<%
			}else if("huiyi".equals(gbSign)){
			%>
			<div class="logo2" style="font-family:'΢���ź�';display:inline-block;overflow:hidden;"><font style="margin-left: 16px;">�ϻ�ϵͳ</font></div>
			
			<%
			}
			%>
			<div class="container">
				<div id="cssmenu"
					style="display: inline; width: 100%; height: 63px;"></div>
			</div>
			<div class="pull-right">
				
				<ul id="mini-nav" class="clearfix">
					<!-- <li class="list-box"><a title="��������"
						href="javascript:void(0);" style="height: 63px;"> <i
							class="fa fa-bell "></i></a><span class="info-label success-bg">0</span></li> -->
					
							
					<!-- <li class="list-box"><a href="#"> <i class="fa fa-file"></i></a></li>
			<li class="list-box"><a href="#"> <i class="fa fa-envelope"></i></a> <span class="info-label success-bg">0</span></li> -->
					<!--data-toggle="dropdown"  -->
					
					
					<li class="list-box user-profile"><a id="drop7"
						href="javascript:void(0);" role="button"
						class="dropdown-toggle user-avtar" data-toggle="dropdown"> <img
							id="dropimage" src="mainPage/images/user5.png"
							alt="<%=SysUtil.getCacheCurrentUser().getLoginname()%>">
					</a>
						<ul class="dropdown-menu server-activity">
							<li>
								<p class="center-align-text">
									��ӭ��, <b><%=SysUtil.getCacheCurrentUser().getName()%></b>
									
								</p>
							</li>
							<li>
								<p class="center-align-text">
									<%-- �汾��<b onclick="openVersion()"><%=VerWindowBS.getVersionName()%></b> --%>
									�汾��<b  ></b>
									
									<!-- <b onclick="downLoadTxt();return false;" style="color:#0066FF">&nbsp;&nbsp;�汾˵��</b> -->
								</p>
								
								
								
							</li>
							<!-- <li>
								<p>
									<i class="fa fa-download text-info"></i><span
										onclick="downLoadbook();return false;">ϵͳ����</span>
										
									<i class="fa fa-download text-info"></i><span
										>ϵͳ����</span>
								</p>
							</li> -->
							<li>
								<p>
									<i class="fa fa-lock text-info"></i><span
										onclick="openChangePasswordWin3(true);return false;">�޸�����</span>
								</p>
							</li>
							<li>
								<div class="demo-btn-group clearfix">
									<button class="btn btn-danger" onclick="logout();return false;">�˳�ϵͳ</button>
								</div>
							</li>
						</ul></li>
					<li class="list-box">
						<a href="javascript:void(0);"
							style="cursor: default; height: 63px;width: 110px;">
							<span class="text-white" title="<%=SysUtil.getCacheCurrentUser().getName()%>"><%=name%></span> 
						</a>
					</li>
					<!--
					<li><a href="javascript:void(0);"
						style="cursor: pointer; height: 63px;"
						onclick="hideMenu()"> <i class="fa fa-chevron-up"
							style="font-size: 12px; color: white; padding-top: 32px;"
							id="icon_change"></i>
					</a></li>
					-->
				</ul>
			</div>
		</div>
		<div id="showHeader" class="header" style="display:none;height:20px;">
			<div style="display:inline">
				<a href="javascript:void(0);" style="cursor:pointer;" onclick="hideMenu()">
					<i class="fa fa-chevron-down" style="font-size:12px;color:white;padding-left:97%;" id="icon_change"></i>
				</a>
			</div>
		</div>
</div>
<iframe id="TemplateDBIframe" style="dispaly:none;width:0;height;"></iframe>
<sicp3:errors/> 
<%-- <%
				if("ganbu".equals(gbSign)){
			%>
				<script type="text/javascript" src="basejs/indexX.js"></script>
			<%
				}else if("Family".equals(gbSign)){
			%>
			    <script type="text/javascript" src="basejs/indexX2.js"></script>
			<%
				}else if("xitong".equals(gbSign)){
			%>
				<script type="text/javascript" src="basejs/indexX_XTGL.js"></script>
			<%
				}else if("renmian".equals(gbSign)){
			%>
				<script type="text/javascript" src="basejs/indexX_GBRM.js"></script>
			<%
				}else if("zhonghechaxun".equals(gbSign)){
			%>
				<script type="text/javascript" src="basejs/indexX_ZHCX.js"></script>
			<%
				}else if("banzi".equals(gbSign)){
			%>
				<script type="text/javascript" src="basejs/indexX_bz.js"></script>
			<%
				}else if("huiyi".equals(gbSign)){
					//System.out.println("22222222222222222222222222");
			%>
			<script type="text/javascript" src="basejs/indexX3.js"></script>
			<%
				}
			%> --%>
<script type="text/javascript">
var dataId='';
var dataText='';
var dataLocation='';
var g_contextpath = '<%=request.getContextPath()%>';
var gbSign = '<%=gbSign %>';
<%=data%>;

//alert(gbSign);
var html='<ul style="width:100%">';
$(function(){
	debugger;
	var id = '<%=tabId %>';
	
	var data = CodeTypeTree.tree;
	var data = data.children;
	if(!(id ==null || id =='' || id =='null')){
		for(var i=0;i<data.length;i++){
			if(data[i].text=='����ɲ�'&&id=='21'){
				html += '<li class=""><a href="javascript:void(0)" id="'+data[i].id+'"  onclick="'+
				'addTab1(&quot;'+data[i].id+'&quot;,&quot;'+data[i].title+'&quot;,&quot;'+data[i].location+'&quot;)"><i class="fa '+data[i].icon+'"></i>'+data[i].text;
				html += '</a></li>';
				dataId = data[i].id;
				dataText = data[i].text;
				dataLocation = data[i].location;
			}

			if(data[i].text=='��ѧ�����'&&id=='20'){
				html += '<li class=""><a href="javascript:void(0)" id="'+data[i].id+'"  onclick="'+
				'addTab1(&quot;'+data[i].id+'&quot;,&quot;'+data[i].title+'&quot;,&quot;'+data[i].location+'&quot;)"><i class="fa '+data[i].icon+'"></i>'+data[i].text;
				html += '</a></li>';
				dataId = data[i].id;
				dataText = data[i].text;
				dataLocation = data[i].location;
			}
			if(data[i].text=='�ɲ�����'&&id=='2'){
				html += '<li class=""><a href="javascript:void(0)" id="'+data[i].id+'"  onclick="'+
				'addTab1(&quot;'+data[i].id+'&quot;,&quot;'+data[i].title+'&quot;,&quot;'+data[i].location+'&quot;)"><i class="fa '+data[i].icon+'"></i>'+data[i].text;
				html += '</a></li>';
				dataId = data[i].id;
				dataText = data[i].text;
				dataLocation = data[i].location;
			}
			if(data[i].text=='��������'&&id=='1'){
				html += '<li class=""><a href="javascript:void(0)" id="'+data[i].id+'"  onclick="'+
				'addTab1(&quot;'+data[i].id+'&quot;,&quot;'+data[i].title+'&quot;,&quot;'+data[i].location+'&quot;)"><i class="fa '+data[i].icon+'"></i>'+data[i].text;
				html += '</a></li>';
				dataId = data[i].id;
				dataText = data[i].text;
				dataLocation = data[i].location;
			}
			if(data[i].text=='�ɲ�����'&&id=='3'){
				html += '<li class="active has-sub"><span class="submenu-button"></span>'+
				'<a href="javascript:void(0);"> <i class="fa '+data[i].icon+'"></i>'+data[i].text+
				'</a>';
				if(!data[i].leaf){
					addChildren(data[i].children);
				}
				var childData = data[i].children;
				dataId = childData[0].id;
				dataText = childData[0].text;
				dataLocation = childData[0].location;
			}
			if(data[i].text=='�ۺϲ�ѯ'&&id=='4'){
				html += '<li class=""><a href="javascript:void(0)" id="'+data[i].id+'"  onclick="'+
				'addTab1(&quot;'+data[i].id+'&quot;,&quot;'+data[i].title+'&quot;,&quot;'+data[i].location+'&quot;)"><i class="fa '+data[i].icon+'"></i>'+data[i].text;
				html += '</a></li>';
				dataId = data[i].id;
				dataText = data[i].text;
				dataLocation = data[i].location;
			}
			if(data[i].text=='���ݽ���'&&id=='5'){
				html += '<li class="active has-sub"><span class="submenu-button"></span>'+
				'<a href="javascript:void(0);"> <i class="fa '+data[i].icon+'"></i>'+data[i].text+
				'</a>';
				if(!data[i].leaf){
					addChildren(data[i].children);
				}
				var childData = data[i].children;
				dataId = childData[0].id;
				dataText = childData[0].text;
				dataLocation = childData[0].location;
			}
			if(data[i].text=='ϵͳ����'&&id=='6'){
				html += '<li class="active has-sub"><span class="submenu-button"></span>'+
				'<a href="javascript:void(0);"> <i class="fa '+data[i].icon+'"></i>'+data[i].text+
				'</a>';
				if(!data[i].leaf){
					addChildren(data[i].children);
				}
				var childData = data[i].children;
				dataId = childData[0].id;
				dataText = childData[0].text;
				dataLocation = childData[0].location;
			}
			if(data[i].text=='���ݷ���'&&id=='7'){
				html += '<li class=""><a href="javascript:void(0)" id="'+data[i].id+'"  onclick="'+
				'addTab1(&quot;'+data[i].id+'&quot;,&quot;'+data[i].title+'&quot;,&quot;'+data[i].location+'&quot;)"><i class="fa '+data[i].icon+'"></i>'+data[i].text;
				html += '</a></li>';
				dataId = data[i].id;
				dataText = data[i].text;
				dataLocation = data[i].location;
			}
			
		}
		
	} else {
		for(var i=0;i<data.length;i++){
			if(<%=!gbmc %>){
				if(data[i].text=='�ɲ�����'||data[i].text=='�ɲ�����'){
					continue;
				}
			}
			
			if(data[i].text=='��������'||data[i].text=='�ɲ�����'||data[i].text=='Ԯ���ְ'||data[i].text=='�ɲ�����'||data[i].text=='�ɲ�����'||data[i].text=='�ɲ��ල'||data[i].text=='�ۺϲ�ѯ'||data[i].text=='��־����'
				||data[i].text=='��������'||data[i].text=='Ȩ�޹���'||data[i].text=='�ɲ�ѡ������'||data[i].text=='������ȡ���'
				||data[i].text=='���Ʊ���'||data[i].text=='ְ�����'||data[i].text=='���Ԥ�����'||data[i].text=='��ͥ��Ա'
				||data[i].text=='�ṹģ������'||data[i].text=='����ͳ�Ʒ���'||data[i].text=='�ṹ����'       //||data[i].text=='�ɲ�һ����'||
				||data[i].text=='�������'||data[i].text=='����ɲ�'||data[i].text=='��������'||data[i].text=='��ʷ����'||data[i].text=='���ݷ���ƽ̨'
			    ||data[i].text=='��Ϣ�䶯'||data[i].text=='����ѧϰ�켣'||data[i].text=='������Ϣ'||data[i].text=='���ӽṹ����'||data[i].text=='���ӽṹ����'
				){
				/* <li class=""><a href="#"> <i class="fa fa-comments"></i>
				��Ϣά��
		</a></li>if(data[i].text=='��ϢУ��'){
						html += '<li class=""><a href="javascript:void(0)" onclick="openDataVerify()"><i class="fa fa-tag "></i>'+data[i].text;
						html += '</a></li>';
					
					}else */ 
				if(data[i].text=='��ͥ��Ա'){
					html += '<li class="active has-sub"><span class="submenu-button"></span>'+
					'<a href="javascript:void(0);"> <i class="fa '+data[i].icon+'"></i>'+data[i].text+
					'</a>';
					if(!data[i].leaf){
						addChildren(data[i].children);
					}
					//add zepeng 202020521 ���try��� ��ͥ��Ա��δ��������Ȩ����ɺ���Ĳ˵��޷�����
					try{
						var childData = data[i].children;
						dataId = childData[0].id;
						dataText = childData[0].text;
						dataLocation = childData[0].location;
					}catch(e){
						
					}
					continue;
				}
		//alert(data[i].text);
// 				if(data[i].text=='�ϻ�ϵͳ'){
					
// 					html += '<li class="active has-sub"><span class="submenu-button"></span>'+
// 					'<a href="javascript:void(0);"> <i class="fa '+data[i].icon+'"></i>'+data[i].text+
// 					'</a>';
// 					if(!data[i].leaf){
// 						addChildren(data[i].children);
// 					}
// 					var childData = data[i].children;
// 					dataId = childData[0].id;
// 					dataText = childData[0].text;
// 					dataLocation = childData[0].location;
// 					continue;
// 				}
					if (data[i].text=='ģ�����'){
						html += '<li class=""><a href="javascript:void(0)" onclick="openOutPut()"><i class="fa fa-tasks "></i>'+data[i].text;
						html += '</a></li>';
					}else if (data[i].text=='�ɲ��ල'){
						html += '<li class=""><a href="javascript:void(0)" id="'+data[i].id+'"  onclick="openGBJD(&quot;'+data[i].location+'&quot;)"><i class="fa '+data[i].icon+'"></i>'+data[i].text;
						html += '</a></li>';	
					}else if (data[i].text=='���ݷ���ƽ̨'){
						html += '<li class=""><a href="javascript:void(0)" id="'+data[i].id+'"  onclick="openSJPTFX()"><i class="fa '+data[i].icon+'"></i>'+data[i].text;
						html += '</a></li>';	
					}else{
						if(data[i].text=='���Ʊ���'){
							html += '<li>';
							html +=	'<a href="javascript:void(0)" onclick="openCommPMWin(&quot;'+data[i].id+'&quot;,&quot;'+data[i].text+'&quot;,&quot;'+data[i].location+'&quot;,1000,604)" ><i class="fa '+data[i].icon+'"></i>'
								+ data[i].text+'</a>'+
							'</li>';
						}else if(data[i].text=='�������'){
							html += '<li>';
							html +=	'<a href="javascript:void(0)" onclick="openCommPMWin2(&quot;'+data[i].id+'&quot;,&quot;'+data[i].text+'&quot;,&quot;'+data[i].location+'&quot;,1000,604)" ><i class="fa '+data[i].icon+'"></i>'
								+ data[i].text+'</a>'+
							'</li>';
						}else if(data[i].text=='�ɲ�����'){
							html += '<li class=""><a href="javascript:void(0)" id="'+data[i].id+'"  onclick="'+
							'addTab1(&quot;'+data[i].id+'&quot;,&quot;'+data[i].title+'&quot;,&quot;/radowAction.do?method=doEvent&pageModel=pages.customquery.CustomQuery&quot;)"><i class="fa '+data[i].icon+'"></i>'+data[i].text;
							html += '</a></li>';
						}else{
							html += '<li class=""><a href="javascript:void(0)" id="'+data[i].id+'"  onclick="'+
							'addTab1(&quot;'+data[i].id+'&quot;,&quot;'+data[i].title+'&quot;,&quot;'+data[i].location+'&quot;)"><i class="fa '+data[i].icon+'"></i>'+data[i].text;
							html += '</a></li>';
						}	
						
					}
					
			}else {
				/* <li class="active has-sub"><span class="submenu-button"></span>
				<a href="javascript:void(0);"> <i class="fa fa-th-large"></i>
					��������
			</a>
				<ul> */
				if(data[i].text=='���Ʊ���'){
					html += '<li>';
					html +=	'<a href="javascript:void(0)" onclick="openCommPMWin(&quot;'+data[i].id+'&quot;,&quot;'+data[i].text+'&quot;,&quot;'+data[i].location+'&quot;,1000,604)" >'
						+ data[i].text+'</a>'+
					'</li>';
				}else if(data[i].text=='�������'){
					html += '<li>';
					html +=	'<a href="javascript:void(0)" onclick="openCommPMWin2(&quot;'+data[i].id+'&quot;,&quot;'+data[i].text+'&quot;,&quot;'+data[i].location+'&quot;,1000,604)" >'
						+ data[i].text+'</a>'+
					'</li>';
				}else{
					html += '<li class="active has-sub"><span class="submenu-button"></span>'+
					'<a href="javascript:void(0);"> <i class="fa '+data[i].icon+'"></i>'+data[i].text+
					'</a>';
					if(!data[i].leaf){
						addChildren(data[i].children);
					}
				}
					
					//alert(html);
			}
		}
	}
	
	html += '</ul>';
	document.getElementById("cssmenu").innerHTML= html;
	
});
Ext.onReady(function(){
	///addTab1(dataId,dataText,dataLocation);
});
function addChildren(data){
	html += '<ul>';
	for(var j=0;j<data.length;j++){
		if(!data[j].leaf){
			//html += '<li>';
			html += '<li class="active has-sub"><span class="submenu-button"></span>'+
			'<a href="javascript:void(0);"> <i class="fa"></i>'+data[j].text+
			'</a>';
			addChildren(data[j].children);
			html +=	'</li>';
		}else{	
			if('/radowAction.do?method=doEvent&pageModel=pages.sysmanager.dbInit.DbInit'==data[j].location){
				html += '<li >';
				html +=	'<a href="javascript:void(0)" onclick="openWindowXX(&quot;'+data[j].id+'&quot;,&quot;'+data[j].text+'&quot;,&quot;'+contextPath+data[j].location+'&quot;,350,150)" >'
					+ data[j].text+'</a>'+
				'</li>';
			}else{
					/* if(data[j].text=='������ϢУ��'){
						html += '<li>';
						
						html +=	'<a href="javascript:void(0)" onclick="openDataVerify()" >'
							+ data[j].text+'</a>'+
						'</li>';
					}else */ if(data[j].text=='�Զ���ͳ��'){
                        html += '<li>';
						var url = '<%=url1 %>';
						html +=	"<a href='javascript:void(0)' onclick='addTab1(&quot;"+data[j].id+"&quot;,&quot;"+data[j].title+"&quot;,\"http://"+url+"/radowAction.do?method=doEvent&pageModel=pages.tbyearconf.TableBaseSet&flag=2&vid=321B138965EC826CD59CEB97D095DF86E69C7E7A1E62A225399AE01716F6A2D9A35357087DA8170CC225B73294BAFD762C25EAB37B6D7F6BFB44B1105545C94E31683467FF6FC844FE26C5F6E42A210F4BB95B59CAA916CCB21EE8E64E00D961F83EF6D07A334B63A7CB79C45F46B7A7921880F57B4D2DEDDD97F652B9086CD7\",1)' >";
						
						html += data[j].text+'</a>';
						
						html +='</li>';
					}
					else if(data[j].text=='����ά��'){
						html += '<li>';
						html +=	'<a href="javascript:void(0)" onclick="openCodeMaintain()" >'
							+ data[j].text+'</a>'+
						'</li>';
					}
					else if(data[j].text=='��������У��'){
						/* html += '<li>';
						
						html +=	'<a href="javascript:void(0)" onclick="openOrgVerify()" >'
							+ data[j].text+'</a>'+
						'</li>'; */
					
					}else if(data[j].text=='���֤У��'){
						/* html += '<li>';
						
						html +=	'<a href="javascript:void(0)" onclick="openIdVerify()" >'
							+ data[j].text+'</a>'+
						'</li>';
					 */
					}else if(data[j].text=='Wordģ�嶨��'){
						html += '<li>';
						
						html +=	'<a href="javascript:void(0)" onclick="openZdyWord()" >'
							+ data[j].text+'</a>'+
						'</li>';
					
					}else if(data[j].text=='Excelģ�嶨��'){
						html += '<li>';
						
						html +=	'<a href="javascript:void(0)" onclick="openZdyExcel()" >'
							+ data[j].text+'</a>'+
						'</li>';
					}else if(data[j].text=='����У��'){
						html += '<li>';
						html +=	'<a href="javascript:void(0)" onclick="openSingleVerify()" >'
							+ data[j].text+'</a>'+
						'</li>';
					}else if(data[j].text=='��ϢȺ����'){
						html += '<li>';
						html +=	'<a href="javascript:void(0)" onclick="openMessageGroup()" >'
							+ data[j].text+'</a>'+
						'</li>';
					}else if(data[j].text=='�����ֶ�ά��'){
						html += '<li>';
						html +=	'<a href="javascript:void(0)" onclick="openBaseField()" >'
							+ data[j].text+'</a>'+
						'</li>';
					}else if(data[j].text=='����ֶ�ά��'){
						html += '<li>';
						html +=	'<a href="javascript:void(0)" onclick="openGroupField()" >'
							+ data[j].text+'</a>'+
						'</li>';
					}else if(data[j].text=='����ֶ�ά��'){
						html += '<li>';
						html +=	'<a href="javascript:void(0)" onclick="openTableField()" >'
							+ data[j].text+'</a>'+
						'</li>';	
					}else if(data[j].text=='�Զ����ѯ��ͼ'){
						html += '<li>';
						html +=	'<a href="javascript:void(0)" onclick="openViewCreate()" >'
							+ data[j].text+'</a>'+
						'</li>';
					}else if(data[j].text=='���ݽ�����ʽ����'){
						html += '<li>';
						html +=	'<a href="javascript:void(0)" onclick="openExpDefinCreate()" >'
							+ data[j].text+'</a>'+
						'</li>';
					}else if(data[j].text=='���ݵ���'){
						html += '<li>';
						html +=	'<a href="javascript:void(0)" onclick="openTrainDataImp()" >'
							+ data[j].text+'</a>'+
						'</li>';
					}else if(data[j].text=='���Ʊ���'){
						html += '<li>';
						html +=	'<a href="javascript:void(0)" onclick="openCommPMWin(&quot;'+data[j].id+'&quot;,&quot;'+data[j].text+'&quot;,&quot;'+data[j].location+'&quot;,1000,604)" >'
							+ data[j].text+'</a>'+
						'</li>';
					}else if(data[j].text=='�������'){
						html += '<li>';
						html +=	'<a href="javascript:void(0)" onclick="openCommPMWin2(&quot;'+data[j].id+'&quot;,&quot;'+data[j].text+'&quot;,&quot;'+data[j].location+'&quot;,1000,604)" >'
							+ data[j].text+'</a>'+
						'</li>';
					}else if(data[j].text=='����/���֤�Ų�ѯ'){
						html += '<li>';
						html +=	'<a href="javascript:void(0)" onclick="zhsearch(&quot;'+'zhIsearch1'+'&quot;,&quot;'+'�ۺϲ�ѯ'+'&quot;,&quot;'+'/radowAction.do?method=doEvent&pageModel=pages.zhsearch.Isearch'+'&quot;)" >'
						+ data[j].text+'</a>'+
						'</li>';
					}else if(data[j].text=='�Զ����ѯ'){
						html += '<li>';
						html +=	'<a href="javascript:void(0)" onclick="zhsearch(&quot;'+'zhIsearch3'+'&quot;,&quot;'+'�ۺϲ�ѯ'+'&quot;,&quot;'+'/radowAction.do?method=doEvent&pageModel=pages.zhsearch.Isearch'+'&quot;)" >'
						+ data[j].text+'</a>'+
						'</li>';
					}else if(data[j].text=='SQL��ѯ'){
						html += '<li>';
						html +=	'<a href="javascript:void(0)" onclick="zhsearch(&quot;'+'zhIsearch4'+'&quot;,&quot;'+'�ۺϲ�ѯ'+'&quot;,&quot;'+'/radowAction.do?method=doEvent&pageModel=pages.zhsearch.Isearch'+'&quot;)" >'
						+ data[j].text+'</a>'+
						'</li>';
					}else if(data[j].text=='����������ѯ'){
						html += '<li>';
						html +=	'<a href="javascript:void(0)" onclick="zhsearch(&quot;'+'zhIsearch2'+'&quot;,&quot;'+'�ۺϲ�ѯ'+'&quot;,&quot;'+'/radowAction.do?method=doEvent&pageModel=pages.zhsearch.Isearch'+'&quot;)" >'
						+ data[j].text+'</a>'+
						'</li>';
					}else if(data[j].text=='�ɲ����Բ�ѯ'){
						html += '<li>';
						html +=	'<a href="javascript:void(0)" onclick="zhsearch(&quot;'+'zhIsearch5'+'&quot;,&quot;'+'�ۺϲ�ѯ'+'&quot;,&quot;'+'/radowAction.do?method=doEvent&pageModel=pages.zhsearch.Isearch'+'&quot;)" >'
						+ data[j].text+'</a>'+
						'</li>';
					}else if(data[j].text=='Ȩ�޹���'){
						html += '<li>';
						html +=	'<a href="javascript:void(0)" onclick="openUserManager()" >'
							+ data[j].text+'</a>'+
						'</li>';
					}else if(data[j].text=='����Ȩ��'){
						continue;
					}else{
						html += '<li>';
						
						html +=	'<a href="javascript:void(0)" id="'+data[j].id+'" onclick="addTab1(&quot;'+data[j].id+'&quot;,&quot;'+data[j].title+'&quot;,&quot;'+data[j].location+'&quot;)" >'
							+ data[j].text+'</a>'+
						'</li>';
					}				
			}
		}

	}
	html +=	 '</ul>';	
}
function openUserManager(){
	$h.openWin('userManager','pages.cadremgn.sysmanager.authority.UsersManager','�ۺ���ϢȨ�޹���',800,600,'',g_contextpath,null,{maximizable:false,resizable:false});
}

function openSJPTFX(){
	var url = "http://<%=obj %>/AnalysissMain.jsp";
	window.open(url, '_blank', '', false);
    /* // ActiveObject����IE�¿ɴ���
    var objShell = new ActiveXObject("WScript.Shell");
    // ע��������/c������ʹ��/k��������Դ�����ͷ�
    var cmd = "cmd.exe /c start firefox " + url;
    objShell.Run(cmd, 0, true); */
}

function zhsearch(id,text,location){ 
	var type = id.substr(id.length-1,1);
	id = id.substr(0,id.length-1);
	Ext.Ajax.request({
		method: 'POST',
        async: true,
        params : '1',
        timeout :300000,//���������
		url: "<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.zhsearch.Isearch&eventNames=open&type="+type,
		success: function(resData){
			addTab(text,id,g_contextpath+location,'','');
		},
		failure : function(res, options){ 
			Ext.Msg.hide();
			alert("�����쳣��");
		}  
	});
	
	
	
}
function addTab1(id,text,location){  
	//addTab(text,id,g_contextpath+location,'','')
}

function openWindowXX(id,title,url,width,height){
    var winx = Ext.getCmp(id);
    if(winx){
    	winx.close();
    	return;
    }
    winx = new Ext.Window({
        html: "<iframe style=\"background:white;border:none;\" width=\"100%\" height=\"100%\" src=\"" + url + "\"></iframe>",
        id:id,
        title:title,
        layout:'fit',
        width:width,
        height:height,
        closeAction:'close',
        collapsible:false,
        plain: true,
        modal : true,
        listeners:{ 
			"drag":function(){ 
						return false;
				   }
					} 	,
        resizable: false
    });
    winx.show();
    winx.setWidth(width);
    winx.setHeight(height);
} 	
/**
 * ���ذ����ļ�
 */
function downLoadbook(){
	var downfile = '<%=downloadfile %>';
	w = window.open("ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile)));
	setTimeout(cc,3000);
}

/**
 * ���ذ汾˵���ļ� 
 */
function downLoadTxt(){
	var downfileTxt = '<%=downloadfileTxt %>';
	w = window.open("ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfileTxt)));
	setTimeout(cc,3000);
}


/**
 * �޸�����
 */
function openChangePasswordWin3(closable){
	//radow.util.openWindow('pswin','pages.sysmanager.user.PsWindow');
	$h.openWin('pswin','pages.sysmanager.user.PsWindow','�޸�����',350,230, '',g_contextpath,null,{maximizable:false,resizable:false,closable:closable});
}
function openDataVerify(){
	$h.openWin('DataVerifyWin','pages.sysorg.org.orgdataverify.OrgDataVerify2','У�˴���',700,590, '',g_contextpath,null,{maximizable:false,resizable:false});
}
function openOrgVerify(){
	$h.openWin('OrgVerifyWin','pages.sysorg.org.orgdataverify.OrgVerify','��������У��',700,590,'',g_contextpath,null,{maximizable:false,resizable:false});
}
function openIdVerify(){
	$h.openWin('IdVerifyWin','pages.sysorg.org.orgdataverify.IdVerify','���֤У��',700,590,'',g_contextpath,null,{maximizable:false,resizable:false});

}
var hh=$(window).height();
var ww=$(window).width()-20;
function openZdyWord(){
// 	$h.openWin('openZdyWord','pages.weboffice.WebOffice','Wordģ�嶨��','100%',800,'',g_contextpath,null,{maximizable:false,resizable:false});
	 window.open("<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.weboffice.WebOffice",'Wordģ�嶨��',"resizable=yes,top=0,left=0,width="+ww+",height="+hh+",status=no,menubar=no,toolbar=no,Scrollbars=no,Location=no,Direction=no,resizable=no");  
 
 //$h.openPageModeWin('webOffice','pages.weboffice.WebOffice','Wordģ�嶨��',ww,hh,'',g_contextpath);
}
function openZdyExcel(){
	//$h.openWin('openZdyExcel','pages.weboffice.ExcelOffice','Excelģ�嶨��','100%',800,'',g_contextpath,null,{maximizable:false,resizable:false});
	window.open("<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.weboffice.ExcelOffice",'Excelģ�嶨��',"resizable=yes,top=0,left=0,width="+ww+",height="+hh+",status=no,menubar=no,toolbar=no,Scrollbars=no,Location=no,Direction=no,resizable=no"); 
	//$h.openPageModeWin('ExcelOffice','pages.weboffice.ExcelOffice','Excelģ�嶨��',ww,hh,'',g_contextpath);
}
function openSingleVerify() {
	$h.openPageModeWin('SingleVerify','pages.cadremgn.dataproofread.sysorg.SingleVerify','����У�� ',900,570,'',g_contextpath);
}
function openCodeMaintain(){
	 $h.openPageModeWin('codeMaintain','pages.cadremgn.sysbuilder.CodeMaintain','����ά��',750,535,'',g_contextpath,null,{maximizable:false,resizable:false});
}

function openZdybc(){
	$h.openWin('openZdybc','pages.weboffice.WebOffice','�Զ�����','100%',800,'',g_contextpath,null,{maximizable:false,resizable:false});

}
function openOutPut(){
	radow.util.openWindow('ListOutPutWin','pages.search.ListOutPut');
}
function openMessageGroup(){
	 $h.openPageModeWin('infmtionGroup','pages.cadremgn.sysbuilder.InfmtionGroup','������ϢȺ',680,220,'',g_contextpath,null,{maximizable:false,resizable:false});
}
function openBaseField(){
	 $h.openPageModeWin('baseField','pages.cadremgn.sysbuilder.BaseField','�����ֶ�ά��',900,645,'',g_contextpath,null,{maximizable:false,resizable:false});	
}
function openGroupField(){
	 $h.openPageModeWin('groupField','pages.cadremgn.sysbuilder.GroupField','����ֶ�ά��',680,380,'',g_contextpath,null,{maximizable:false,resizable:false});
}
function openTableField(){
	 $h.openPageModeWin('tableField','pages.cadremgn.sysbuilder.TableField','����ֶ�ά��',770,320,'',g_contextpath,null,{maximizable:false,resizable:false});
}
function openViewCreate(){
	$h.openPageModeWin('ViewCreateWin','pages.cadremgn.sysmanager.ViewCreate','�Զ����ѯ��ͼ',1000,600,'',g_contextpath);
}
function openExpDefinCreate(){
	$h.openPageModeWin('ExpDefinWin','pages.dataverify.DataExpDefin','���ݽ�����ʽ����',1000,600,'',g_contextpath);
}
function openTrainDataImp(){
	$h.openPageModeWin('TrainDataImpWin','pages.train.TrainDataImp','��ѵ���ݵ���',330,133,'',g_contextpath);
	//$h.openWin('TrainDataImpWin','pages.train.TrainDataImp','��ѵ���ݵ���',360,163,'',g_contextpath,null,{maximizable:false,resizable:false});
}
function openCommPMWin(id,text,location,w,h){
	$h.openPageModeWin(id,location.slice(41),text,w,h,'',g_contextpath);
}
function openCommPMWin2(id,text,location,w,h){
	//$h.openPageModeWin(id,location.slice(41),text,1400,800,'',g_contextpath);
	//window.open('http://www.wlzhys.com','target');
   <%--  window.open( '<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.DataAnalysis.Entirety','target' );  --%>
	window.open('<%=request.getContextPath()%>/pages/DataAnalysis/Entirety.jsp','target');
}
function openGBJD(url){
	Ext.Ajax.request({
		method: 'POST',
        async: true,
        params : {url:url},
        timeout :300000,//���������
		url: "<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.sysorg.org.PublicWindow&eventNames=gbjdLogin",
		success: function(resData){
			//alert(resData.responseText);
			var cfg = Ext.util.JSON.decode(resData.responseText);
			if(0==cfg.messageCode){
				if(cfg.elementsScript.indexOf("\n")>0){
					cfg.elementsScript = cfg.elementsScript.replace(/\r/gi,"");
					cfg.elementsScript = cfg.elementsScript.replace(/\n/gi,"\\n");
				}
				
				//console.log(cfg.elementsScript);
				eval(cfg.elementsScript);
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

function logout(){
  if (confirm('ȷ��Ҫ�˳�ϵͳ��?')){
  	//http://ip:�˿�/��Ŀ����/UserLoginAction.do?method=LoginOut
  	
  	window.top.location.href=g_contextpath+'/mainLogOff.jsp';//'/logoffAction.do';
  } 
}

/**
tabҳ֮�亯������
**/
function exeOtherTab(aid,method){
	var frameid = 'I'+aid;
	if(document.getElementById(frameid)){
		eval("document.getElementById('"+frameid+"').contentWindow."+method+"();");
	}else{
		alert("ID��ҳ����󲻴��ڣ�");
	}
}


Ext.onReady(function(){
	//����޸�����״̬
	var checkPassWord='<%=PsWindowPageModel.getCheck()%>';
	if(checkPassWord == 'true'){
		//����30�� ��������
		openChangePasswordWin3(false);
	}
	
	

});

//�򿪰汾��Ϣ����
function openVersion(){
	var iHeight = 200;
	var iWidth = 300;
	var iTop = (window.screen.availHeight-30-iHeight)/2;       //��ô��ڵĴ�ֱλ��;
	var iLeft = (window.screen.availWidth-10-iWidth)/2;           //��ô��ڵ�ˮƽλ��;
	window.open  
	('tbabout.jsp','newwindow','height='+iHeight+',width='+iWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=no,resizable=no,location=no, status=no') ; 
}
window.onresize = autoHeight;
function autoHeight() {
	var clientWidth = document.documentElement.clientWidth;
	$(".container").css("width",clientWidth-300-160);
}
autoHeight();

function hideMenu() {
	var old = $('#header2').css("display");
	var totalHeight = document.documentElement.clientHeight;
	if(old == 'none') {
		var nowHeight = totalHeight - 64 - 30;
		$('#ext-gen8').css('height',nowHeight+'px');
		$('#ext-gen15').css('height',nowHeight+'px');
		$('#ext-comp-1001').css('top','64px');
		$("#header").css('height','64px');
		$('#header2').css('display','block');
		$('#showHeader').css('display','none');
	} else {
		var nowHeight = totalHeight - 20 - 30;
		$('#ext-gen8').css('height',nowHeight+'px');
		$('#ext-gen15').css('height',nowHeight+'px');
		$('#ext-comp-1001').removeClass('x-tab-panel');
		$('#ext-comp-1002').removeClass('x-panel');
		$('#ext-gen5').removeClass('header-height');
		$('#ext-gen5').css('height','20px');
		$('#ext-comp-1001').css('top','20px');
		$("#header").css('height','20px');
		$('#header2').css('display','none');
		$('#showHeader').css('display','block');
	}
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
<%-- window.onload= function(){
	//var v = getQueryString("para");//�������в���
	var v= jQuery.url.param("tabId");
	//alert(v);
	var lx = '<%=yh[0] %>';
	var fid = '<%=yh[1] %>';
	if(lx == 'zjj' || lx == 'zjh' || lx == 'ga' || lx == 'gs'){
		setTimeout("click('"+fid+"')",10 );
		return;
	} else if (lx == 'ryxx'){
		v='<%=tabId%>';
		if(v == '' || v=='null'){
			v=2;
		}
	} else {
		return;
	}
	//����Աҳ��
	if(v=='' || v=='null'){
		v=2;
	}
	/* var notice = v.substr(0,6);
	var noticeid = v.substr(6,v.length); */	
	//setTimeout("changeState()",3000 );
	 if(v==1){
		//addTab1("402881ef533533800153354ae1c00006","������Ϣ","/radowAction.do?method=doEvent&pageModel=pages.sysorg.org.SysOrgOther");
		 //document.getElementById("402881ef533533800153354ae1c00006").click();
		 setTimeout("click('402881ef533533800153354ae1c00006')",10 );
	 }else if(v==2){
	    setTimeout("addTab1('40287d815451830d0154519c343e0007','�ɲ�����','/radowAction.do?method=doEvent&pageModel=pages.customquery.CustomQuery')",10);
		//addTab1("40287d815451830d0154519c343e0007","��Ա��Ϣ","/radowAction.do?method=doEvent&pageModel=pages.customquery.CustomQuery");
		 //document.getElementById("40287d815451830d0154519c343e0007").click();
	 }else if(v==3){
		 //addTab1("297e9b33654165f4016541fe37f7000a","ȫ�̼�ʵ����","/radowAction.do?method=doEvent&pageModel=pages.xbrm.JSGL")
		 setTimeout("click('297e9b33654165f4016541fe37f7000a')",10 );   //ʵ����ʵ
	 }else if(v==4){
		 var qt= jQuery.url.param("qt");
		 var qid= jQuery.url.param("qid");
		 var p = '&qt='+qt+'&qid='+qid;
		 setTimeout("addTab1('402882f265c693770165c6c7d65b0007','�ۺϲ�ѯ','/radowAction.do?method=doEvent&pageModel=pages.zhsearch.Isearch"+p+"')",10 );   //�ۺϲ�ѯ
	 }else if(v==5){		
		 setTimeout("addTab1('2c935181545119cc015451236a360007','���ݽ���','/radowAction.do?method=doEvent&pageModel=pages.dataverify.QueryOrgImp')",10 );   //ʵ����ʵ
	 }else if(v==6){		
		 setTimeout("addTab1('402881ee5dd63ea4015dd6542ff80005','֪ͨ����','/radowAction.do?method=doEvent&pageModel=pages.notice.NoticeSetList')",10 );   //ʵ����ʵ
	 }else if(v==7){
		 setTimeout("addTab1('297e91cb6b8487c5016b848d548d0003','���ݷ���','/radowAction.do?method=doEvent&pageModel=pages.DataAnalysis.DataEcharts')",10 );   //ͼ�����
	 }else if(v==8){
		 setTimeout("addTab1('402881e96b6a15c1016b6a57bb5f0007','�������','/radowAction.do?method=doEvent&pageModel=pages.zhsearch.SearchCond')",10 );   //ʵ����ʵ
	 }else if(v=='dbInfo'){
		 var rid= jQuery.url.param("rid");
		 dbInfoF(rid);
	 }else if(v=='dbInfoMore'){											  
		 setTimeout("addTab1('402881e46b78dc13016b790b5a1b000e','������Ϣ','/radowAction.do?method=doEvent&pageModel=pages.comm.InfoToDoQ')",10 );   //ʵ����ʵ
	 }else if(v==9){		
		 setTimeout("click('402881ee5dd63ea4015dd6542ff80005')",10 );   //֪ͨ����
	 }else if(v==10){		
		 setTimeout("click('402881fe538436cc0153843a10eb0002')",10 );   //�û�����		 	 
	 }else if(v==12){
		 setTimeout("click('2c935181545119cc015451236a360007')",10 );
	/*  }else if(notice=='notice'){                                               
		 setTimeout(function(){opennotice(noticeid)}, 10);	 */            //֪ͨ��������
	 }else if(v==13){
		setTimeout("addTab1('402881ee5dd63ea4015dd654db9a0006','���߷���','/radowAction.do?method=doEvent&pageModel=pages.policy.PolicySetList')",10 );   //ʵ����ʵ

	 } else if(v==20){
		setTimeout("addTab1('402881e76c65996c016c659b903f0003','��ѧ�����','/radowAction.do?method=doEvent&pageModel=pages.otherdb.dxscg.Dxscg')",10);
	 } else if(v==21){
		setTimeout("addTab1('402881e76c6a1fc3016c6aa7e0220005','����ɲ�','/radowAction.do?method=doEvent&pageModel=pages.otherdb.nqgb.CustomQueryNQ')",10);
	 }		 
	
} --%>

function dbInfoF(rid){
	Ext.Ajax.request({
		method: 'POST',
        async: true,
        params: {'rid':rid},
        timeout :300000,//���������
		url: "<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.comm.InfoToDo&eventNames=getDbById",
		success: function(resData){
			var info = Ext.util.JSON.decode(resData.responseText);
			var v1 = info.itdc003;
			var v2 = info.itdc004;
			var v3 = info.itdc005;
			var v4 = info.itdc008;
			var v5 = info.itdr013;
			var p = "&mainXdbid="+v5;
			if(v1=='tab'){
				var tab=parent.tabs.getItem(v5);
				if (tab){
					document.getElementById('I'+v3).src = g_contextpath + v2 +p;
					parent.tabs.activate(tab);
			    }else{
			    	setTimeout("addTab1('"+v3+"','"+v4+"','"+v2+p+"')",10 );   //�ۺϲ�ѯ
			    }
			} else {
				
			}
			
		},
		failure : function(res, options){ 
			Ext.Msg.hide();
			alert("�����쳣��");
		}  
	});
}
function opennotice(v){
	noticeAddTab('֪ͨ��������',v,'<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.notice.NoticeInfo',false, false);
}

function click(id){
	document.getElementById(id).click();
}


function addTab1(id,text,location,flag){  
	if(flag==1)
		{
		addTab(text,id,location,'','');
		return;
		}
	if ("2c909e1d6d05d72d016d065be08000f2" == id){
		//toTemplateDB(id,text,location);
		loginTemplateDB();   
		if (isFirstLoginTemplateDB){
			setTimeout("toTemplateDBUrl('"+text+"','"+id+"')", 1000); 
		}else{
			isFirstLoginTemplateDB = false;
			setTimeout("toTemplateDBUrl('"+text+"','"+id+"')", 500); 
		}
	}else{
		addTab(text,id,g_contextpath+location,'','')
	}
}

var isFirstLoginTemplateDB = true;
function toTemplateDB(id,text,location){   
	Ext.Ajax.request({
		method: 'get',
        async: true,
        params : '1',
        timeout :300000,//���������
		url: "<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.xbrm2.TPB&eventNames=getTemplateDBURL",
		success: function(resData){  
			var cfg = Ext.util.JSON.decode(resData.responseText); 
			if(0==cfg.messageCode){
				var url = cfg.selfResponseFunc;
				//alert(url);
				$h.alert('ϵͳ��ʾ',"��ת���ɲ��м����Ϣά��\n"+url,null,380);
				addTab(text,id,url,'','');
				return;
				
				if (isFirstLoginTemplateDB){
					$('#TemplateDBIframe').attr('src',url);
					
				    setTimeout(function () { 
				    	addTab(text,id,url,'',''); 
				    }, 2000);				
				}else{
					addTab(text,id,url,'',''); 
				}
				isFirstLoginTemplateDB = true;
			}else{ 
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
function getQueryString(name) {
	var Ohref=window.location.href;
	var arrhref=Ohref.split("?para=");
	var para = arrhref[1];
    /* var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
    	alert("2222");
    	alert(r[2]);
        return unescape(r[2]);
    }*/
    return para; 
}

/*  window.onbeforeunload = function(){
	  if(document.all){
	   var n = window.event.screenX - window.screenLeft;   
	   var b = n > document.documentElement.scrollWidth-20;
	   if(b || window.event.clientY < 0 || window.event.altKey){   
	          
		 /-*  if (confirm('ȷ��Ҫ�˳�ϵͳ��?')){
			   alert("ȷ��");
		  }else{
			  alert("ȡ��");
			  
		  } *-/
		  
		  return "�˳�ϵͳǰ���뱣������ ";
		 
	   }
	  }
	}   */
	  
</script>

<script type="text/javascript" src="basejs/indexX.js"></script>

<script type="text/javascript">
	function loginTemplateDB(){  
		var u = '<%=com.insigma.siis.local.business.helperUtil.SysManagerUtils.getUserloginName() %>'; 
		var url = remoteServer + '/logonAction.do';
		var params = "{'username':'"+u+"','password':'','scene':'','ou1':'undefined','ou2':'undefined','sign':'ganbu','CheckLogin':'1'}"; 
		try{
			$("#submitform").attr("action",url);
			$("#submitform").attr("target","myframe");
			$("#params").val(params);
			$("#username").val(u);
			$("#submit1").click();
		}catch(e){
			alert(e); 
		} 				
	}
	
	var remoteServer = "<%=com.insigma.siis.local.pagemodel.xbrm2.TPBPageModel.getRemoteServer()%>";
	function toTemplateDBUrl(text,id){
		var url = "";
		
		if (remoteServer.indexOf('hzbtj')>0){
			url = remoteServer + '/radowAction.do?method=doEvent&pageModel=pages.customquery.CustomQuery&IsTemplateDB=1';
		}else{
			url = remoteServer + 'hzb/radowAction.do?method=doEvent&pageModel=pages.FamilyMember.addFam&IsTemplateDB=1';
		} 
		addTab(text,id,url,'','');
		/* window.open(url,'fullscreen','fullscreen,toolbar=no,  menubar=no,location=no,   directories=no,   copyhistory=no,  scrollbars=no,   resizable=no,status=no')   ; */
	}

</script> 

<iframe id="myframe" name="myframe" style="display:none"></iframe> 
<div style="display:none">
<form id="submitform" name="submitform" method="post" target="myframe">
	<input type=text name="username" id="username" size="18" value="" ><br>
	<input type="text" name="params" id="params" size="19" value=""/>  
	<input type=submit id="submit1" name="submit1" value="�ύ" >  
</form> 
</div>


</body>
</html>