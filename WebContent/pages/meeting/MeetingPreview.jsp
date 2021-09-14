<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@page import="com.insigma.siis.local.pagemodel.meeting.MeetingPreviewPageModel"%>
<%@page import="java.util.List"%>
<%@page import="java.util.HashMap"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>

<style>
.rmbword{
    font-family: 宋体;
    position: absolute;
    left:0; right:0;  bottom:0;
    top:20px;
    width: 100%;
    float: right;
}
.tbr{
    text-align: left;padding-left:288px;padding-top:8px;margin-bottom:20px;font-size: 18px;
} 
.jianli p{
	font-family:宋体;
	padding-left: 9em!important;
	text-align: left
       
}

.tdlabel{
   text-align: center; 
       
}


#tableJBXX{ 
	font-size: 18px;  
	border-left: 1px solid black;
	border-top: 1px solid black;
	border-right: 2px solid black;
	border-bottom: 1px solid black;
	width: 628px;
}
#tableJBXX tr td{ 
	border-left: 1px solid black;
	border-top: 1px solid black;
}
.width1{ width: 66px; }.width2{ width: 86px; }.width3{ width: 70px; }
.width4{ width: 86px; }.width5{ width: 86px; }.width6{ width: 88px; }.width7{ width: 136px; }
.height1{height: 45px;}.height2{height: 46px;}.height3{height: 44px;}.height4{height: 46px;}
.height5{height: 45px;}.height6{height: 45px;}.height7{height: 47px;}.height8{height: 47px;}
.height9{height: 47px;}



#tableJianli{
	border-left: 1px solid black;
	border-right: 2px solid black;
	border-bottom: 2px solid black;
	width: 628px;
}
#tableJianli tr td{ 
	border-left: 1px solid black;
}

#coordTable {
border-bottom: 2px solid #74A6CC;
border-collapse:collapse;
}
#coordTable2 {
margin-top:-2px;
border-right: 2px solid #74A6CC;
border-bottom: 2px solid #74A6CC;
border-collapse:collapse;
}


#tableJiaTingChengYuan{
	font-size: 18px;  
	border-left: 1px solid black;
	border-top: 1px solid black;
	border-right: 2px solid black;
	border-bottom: 2px solid black;
	width: 628px;
}
#tableJiaTingChengYuan tr td{ 
	border-left: 1px solid black;
	border-top: 1px solid black;
}
.Jwidth1{width: 48px;}.Jwidth2{width: 56px;}.Jwidth3{width: 84px;}.Jwidth4{width: 38px;}
.Jwidth5{width: 80px;}.Jwidth6{width: 314px;}
.Jheight1{height: 93px;}.Jheight2{height: 67px;}.Jheight3{height: 90px;}.Jheight4{height: 44px;}
.Jheight5{height: 45px;}.Jheight6{height: 140px;}

#tableSP{
	font-size: 18px;  
	border-left: 1px solid black;
	border-right: 2px solid black;
	border-bottom: 2px solid black;
	width: 628px;
}
#tableSP tr td{ 
	border-left: 1px solid black;
}
.SPwidth1{width: 46px;}.SPwidth2{width: 268px;}.SPwidth3{width: 52px;}.SPwidth4{width: 255px;}.SPheight{height: 177px;}

.class_tb{
	font-family: 'FangSong_GB2312';
	font-size:22px;
	line-height:28pt;
}
.class1{
	font-size:29px;
	font-family:'方正小标宋简体';
}
.class2{
	font-family: 'KaiTi_GB2312';
	font-size:22px;
}
.class3{
	font-family: 黑体;
	font-size:22px;
}
.class4{
	font-family: 'FangSong_GB2312';
	font-size:22px;
}
.class5{
	font-family: 'KaiTi_GB2312';
	font-size:22px;
	font-weight:bold;
}
.class6{
	font-family: 'FangSong_GB2312';
	font-size:22px;
	font-weight:bold;
}
.class7{
	font-family: 'KaiTi_GB2312';
	font-size:12pt;
	line-height:1.2em;
	text-align:center;
	text-align:justify;
	text-justify:distribute-all-lines;
	text-align-last:justify
}
.class8{
	font-family: 'KaiTi_GB2312';
	font-size:14pt;
	line-height:1.5em;
}
.rmfont{
	font-family: 'Times New Roman';
	
}
</style>


<%
	String a0000="";
	String sh000="";
	String wordpath="";
	MeetingPreviewPageModel mpp=new MeetingPreviewPageModel();
	try{
		String publishid=request.getParameter("publishid");
		if(publishid==null||"".equals(publishid)){
			
		}else if(publishid.contains("sh000@@")){
			//String pngPath=getClass().getResource("/").getFile().toString().replace("WEB-INF/classes/", "yulan/");
			//pngPath=pngPath.substring(1);
			//String pngname=publishid.replace("sh000@@","")+"_rmb.jpg";
			String sh000_rmb=publishid.replace("sh000@@","");
			sh000=sh000_rmb;
			List<HashMap<String, Object>> list_a0000=mpp.commQuerySQL("select a0000 from hz_sh_a01 where sh000='"+sh000_rmb+"' ");
			if(list_a0000.get(0).get("a0000")!=null && !"".equals(list_a0000.get(0).get("a0000"))){
				a0000=list_a0000.get(0).get("a0000").toString();
			}
%>

<table style="width: 100%">
	<tr>
		<td>
				<%-- <img  src="<%=request.getContextPath()+"/yulan/"+pngname%>" width="700px">--%>
			      <div  class="rmbword" >
			        <div style='text-align:center;'  align="center">
			          	<h1 style="font-size: 24px;"  ><a name="tab1" style="text-decoration: none">干&ensp;&ensp;部&ensp;&ensp;任&ensp;&ensp;免&ensp;&ensp;审&ensp;&ensp;批&ensp;&ensp;表</a></h1>
				         <br>
				         <button type='button' onclick="openRmb()" >编辑任免表</button>
				         <br>
				         <br>
				          <table  cellpadding="0" cellspacing="0" id="tableJBXX" style="margin:auto;">
				            <tr align="center">
				              <td class="tdlabel width1 height1">姓&ensp;&ensp;名</td>
				              <td class="width2" id="iA0101i">&ensp;</td>
				              <td class="tdlabel width3">性&ensp;&ensp;别</td>
				              <td class="width4" id="iA0104i">&ensp;</td>
				              <td class="tdlabel width5">出生年月<br/>(岁)</td>
				              <td class="width6" id="iA0107_1i">&ensp;</td>
				              <td  class="width7" rowspan=4>
				                <div style='width:100%; height:100%'>
				                  <img src='<%= request.getContextPath()%>/servlet/DownloadUserHeadImage?a0000=' id="iphoto_pathi" style="max-height:none;width:100%;height:100%" />
				                </div>
				
				              </td>
				            </tr>
				            <tr  align="center">
				              <td  class="tdlabel height2">民&ensp;&ensp;族</td>
				              <td  id="iA0117i">&ensp;</td>
				              <td  class="tdlabel">籍&ensp;&ensp;贯</td>
				              <td  id="iA0111Ai">&ensp;</td>
				              <td  class="tdlabel">出&ensp;生&ensp;地</td>
				              <td  id="iA0114Ai">&ensp;</td>
				
				            </tr>
				            <tr align="center">
				              <td  class="tdlabel height3">入&ensp;&ensp;党<br/>时&ensp;&ensp;间</td>
				              <td  id="iA0140i">&ensp;</td>
				              <td  class="tdlabel">参加工<br/>作时间</td>
				              <td  id="iA0134_1i">&ensp;</td>
				              <td  class="tdlabel">健康状况</td>
				              <td  id="iA0128i">&ensp;</td>
				            </tr>
				            <tr align="center">
				              <td  class="tdlabel height4">专业技<br/>术职务</td>
				              <td  colspan=2 id="iA0196i">&ensp;</td>
				              <td  class="tdlabel">熟悉专业<br/>有何特长</td>
				              <td  colspan=2 id="iA0187Ai">&ensp;</td>
				            </tr>
				            <tr align="center">
				              <td  class="tdlabel "  rowspan=2>学&ensp;&ensp;历<br/>学&ensp;&ensp;位</td>
				              <td  class="tdlabel height5">全日制<br/>教&ensp;&ensp;育</td>
				              <td  colspan=2 id="iQRZXLi">&ensp;</td>
				              <td  class="tdlabel">毕业院校<br/>系及专业</td>
				              <td  colspan=2 id="iQRZXLXXi" align="left">&ensp;</td>
				
				            </tr>
				            <tr align="center">
				
				              <td class="tdlabel height6">在&ensp;&ensp;职<br/>教&ensp;&ensp;育</td>
				              <td colspan=2 id="iZZXLi">&ensp;</td>
				              <td class="tdlabel">毕业院校<br/>系及专业</td>
				              <td  colspan=2 id="iZZXLXXi" align="left">&ensp;</td>
				            </tr>
				
				            <tr>
				              <td class="tdlabel height7" colspan=2>现任职务</td>
				              <td colspan=5 id="iA0192Ai">&ensp;</td>
				            </tr>
				            <tr>
				              <td class="tdlabel height8" colspan=2>拟任职务</td>
				              <td colspan=5 id="iNRZWi">&ensp;</td>
				            </tr>
				            <tr>
				              <td class="tdlabel height9" colspan=2>拟免职务</td>
				              <td colspan=5 id="iNMZWi">&ensp;</td>
				            </tr>
				          </table>
			          
			          
				          <table cellpadding="0" cellspacing="0" id="tableJianli" style="margin:auto">
				            <tr>
				              <td style='width:56px;font-size: 18px' class="tdlabel">简<br/><br/><br/><br/><br/>历</td>
				              <td >
				                <div class="jianli" style='width:568px;text-align:left; margin:4px;' id="iA1701i">
				                  
				                </div>
				              </td>
				            </tr>
				          </table>
			        </div>
			        <div  style='text-align:center;'>
			          <table cellpadding="0" cellspacing="0" id="tableJiaTingChengYuan" style="margin:auto">
			            <tr>
			              <td  align="center">奖惩<br />情况</td>
			              <td colspan="5">
			                <div style='width:572px;border-bottom:0px;text-align:left;' id="iA14Z101i">
			                  
			                </div>
			              </td>
			            </tr>
			            <tr>
			              <td align="center">年度<br />考核</td>
			              <td colspan="5">
			                <div style='width:572px;border-bottom:0px;text-align:left; ' id="iA15Z101i">
			                  
			                </div>
			
			              </td>
			            </tr>
						<tr>
			              <td align="center">任免<br />理由</td>
			              <td colspan="5">
			                <div style='width:572px;border-bottom:0px;text-align:left; overFlow-y:auto;' id="iRMLYi">
			                  
			                </div>
			              </td>
			            </tr>
			          
			            <tr align="center">
			              <td  class="tdlabel Jwidth1 " rowspan="8">家<br />庭<br />主<br />要<br />成<br />员<br />及<br />重<br />要<br />社<br />会<br />关<br />系</td>
			              <td class="tdlabel Jwidth2 Jheight4">称&ensp;谓</td>
			              <td class="tdlabel Jwidth3">姓&ensp;&ensp;名</td>
			              <td class="tdlabel Jwidth4">年<br/>龄</td>
			              <td class="tdlabel Jwidth5">政&ensp;治<br />面&ensp;貌</td>
			              <td class="tdlabel Jwidth6">工&ensp;&ensp;作&ensp;&ensp;单&ensp;&ensp;位&ensp;&ensp;及&ensp;&ensp;职&ensp;&ensp;务</td>
			            </tr>
			            <tr align="center">
			
			              <td  id="iA3604A_1i" class="Jheight5">&ensp;</td>
			              <td  id="iA3601_1i">&ensp;</td>
			              <td  id="iA3607_1i">&ensp;</td>
			              <td  id="iA3627_1i">&ensp;</td>
			              <td id="iA3611_1i" align="left">&ensp;</td>
			            </tr>
			            <tr align="center">
			
			              <td  id="iA3604A_2i" class="Jheight5">&ensp;</td>
			              <td  id="iA3601_2i">&ensp;</td>
			              <td  id="iA3607_2i">&ensp;</td>
			              <td  id="iA3627_2i">&ensp;</td>
			              <td   id="iA3611_2i"  align="left">&ensp;</td>
			            </tr>
			            <tr align="center">
			
			              <td  id="iA3604A_3i" class="Jheight5">&ensp;</td>
			              <td  id="iA3601_3i">&ensp;</td>
			              <td  id="iA3607_3i">&ensp;</td>
			              <td  id="iA3627_3i">&ensp;</td>
			              <td  id="iA3611_3i"  align="left">&ensp;</td>
			            </tr>
			            <tr align="center">
			
			              <td  id="iA3604A_4i" class="Jheight5">&ensp;</td>
			              <td  id="iA3601_4i">&ensp;</td>
			              <td  id="iA3607_4i">&ensp;</td>
			              <td  id="iA3627_4i">&ensp;</td>
			              <td  id="iA3611_4i"  align="left">&ensp;</td>
			            </tr>
			            <tr align="center">
			
			              <td  id="iA3604A_5i" class="Jheight5">&ensp;</td>
			              <td  id="iA3601_5i">&ensp;</td>
			              <td  id="iA3607_5i">&ensp;</td>
			              <td  id="iA3627_5i">&ensp;</td>
			              <td  id="iA3611_5i" align="left">&ensp;</td>
			            </tr>
			            <tr align="center">
			
			              <td  id="iA3604A_6i" class="Jheight5">&ensp;</td>
			              <td  id="iA3601_6i">&ensp;</td>
			              <td  id="iA3607_6i">&ensp;</td>
			              <td  id="iA3627_6i">&ensp;</td>
			              <td  id="iA3611_6i" align="left">&ensp;</td>
			            </tr>
			            <tr align="center">
			
			              <td id="iA3604A_7i" class="Jheight5">&ensp;</td>
			              <td id="iA3601_7i">&ensp;</td>
			              <td id="iA3607_7i">&ensp;</td>
			              <td id="iA3627_7i">&ensp;</td>
			              <td id="iA3611_7i" align="left">&ensp;</td>
			            </tr>	  
			          </table>
			          <table  cellpadding="0" cellspacing="0" id="tableSP" style="margin:auto;">
			            <tr>
			              <td class="tdlabel SPwidth1 SPheight">审意<br/>批&ensp;&ensp;<br/>机&ensp;&ensp;<br/>关见</td>
			              <td class="SPwidth2">
			                <div style="position:relative;left:140px;bottom:-40px;border-top-width:0px;">&nbsp;&nbsp;(盖章)<br/>年&nbsp;&nbsp;月&nbsp;&nbsp;日</div>
			              </td>
			              <td class="tdlabel SPwidth3">行任<br/>政免<br/>机意<br/>关见</td>
			              <td class="SPwidth4">
			                <div style="position:relative;left:120px;bottom:-40px;border-top-width:0px;">&nbsp;&nbsp;(盖章)<br/>年&nbsp;&nbsp;月&nbsp;&nbsp;日</div>
			              </td>
			            </tr>
			           
			          </table>
			          <div colspan="4" class="tbr" > 填表人：<span id="itbri"></span></div>
			        </div>
			        <div style="height: 20px"></div>
			      </div>
		</td>
	</tr>
</table>
<% 		
		}else{
		List<HashMap<String, Object>> list_publish=mpp.queryPublish(publishid);
		String publishname="";
		String type="";
		String qx_type="";
		if(list_publish!=null&&list_publish.size()>0) {
			publishname=list_publish.get(0).get("agendaname").toString();
			type=list_publish.get(0).get("agendatype").toString();
			qx_type=list_publish.get(0).get("qx_type").toString();
		}
		String meetingtime=mpp.queryMeetingTime(publishid);
		String htmlstr="<html>														  "
		+"		<head>                                                                "
		+"<meta http-equiv='Content-Type' content='text/html; charset=GBK'>           "
		+"<title>                                                                     "
		+"</title>                                                                    "
		+"</head>                                                                     "
		+"<body   >                                                                   "
		+"<style>                                                                     "
		+"table{                                                                      "
		+"	font-family: 'FangSong_GB2312';                                           "
		+"	font-size:16pt;                                                           "
		+"	line-height:28pt;                                                        "
		+"}                                                                           "
		+".class1{                                                                    "
		+"	font-size:22pt;                                                           "
		+"	font-family:'方正小标宋简体';                                             "
		+"  line-height:28pt;"
		+"}                                                                           "
		+".class2{                                                                    "
		+"	font-family: 'KaiTi_GB2312';                                              "
		+"	font-size:16pt;                                                           "
		+"  line-height:28pt;"
		+"}                                                                           "
		+".class3{                                                                    "
		+"	font-family: 黑体;                                                        "
		+"	font-size:16pt;                                                           "
		+"}                                                                           "
		+".class4{                                                                    "
		+"	font-family: 'FangSong_GB2312';                                           "
		+"	font-size:16pt;                                                           "
		+"}                                                                           "
		+".class5{                                                                    "
		+"	font-family: 'KaiTi_GB2312';                                              "
		+"	font-size:16pt;                                                           "
		+"	font-weight:bold;                                                         "
		+"}                                                                           "
		+".class6{                                                                    "
		+"	font-family: 'FangSong_GB2312';                                           "
		+"	font-size:16pt;                                                           "
		+"	font-weight:bold;                                                         "
		+"}                                                                           "
		+".class7{                                                                    "
		+"	font-family: 'KaiTi_GB2312';                                              "
		+"	font-size:12pt;                                                           "
		+"	line-height:1.2em;                                                        "
		+"	text-align:center;                                                        "
		+"	text-align:justify;                                                       "
		+"	text-justify:distribute-all-lines;                                        "
		+"	text-align-last:justify                                                   "
		+"}                                                                           "
		+".class8{                                                                    "
		+"	font-family: 'KaiTi_GB2312';                                              "
		+"	font-size:14pt;                                                           "
		+"	line-height:1.5em;                                                        "
		+"}                                                                           "
		+".rmfont{                                                                    "
		+"	font-family: 'Times New Roman';                                         "
		+"}                                                                           "
		+"p{                                                                    "
		+"	mso-char-indent-count:2.0000;                                           "
		+"  line-height:28pt;mso-line-height-rule:exactly;mso-para-margin-top:0gd;mso-para-margin-bottom:0gd;"
		+"}                                                                           "
		+"</style>                                                                    "
		+"<script type='text/javascript'>                                             "
		+"</script>                                                                   "
		+"<div style='width: 100%;' class='class4'> <table style='width: 100%'><tr><td width='50%'>                                   "
		+"		";
%>
<table style="width: 100%">
<tr>
<td  width="2%">
</td>
<td  width="96%">

<table style="width: 100%" class="class_tb">
	<tr height="20px" >
		<td width="100%"></td>
	</tr>
	<tr>
		<td>
			<table style="width: 100%;">
				<tr>
								<%
								List<HashMap<String, Object>> list_meet=mpp.queryMeeting(publishid);
								String agendatype=list_meet.get(0).get("agendatype").toString();
								String meetingtype=list_meet.get(0).get("meetingtype").toString();
								if("1".equals(agendatype)||"3".equals(agendatype)){
									%>
					 <td colspan="3" align="right">
					 	<button type='button' onclick="wordbtn()" >导出word</button>
					</td>
				</tr>
					<td style="border: 1px solid black;" width="22%"  align="center">
						<table>
									<% 
									if("1".equals(meetingtype)){
										htmlstr=htmlstr+"<table style='width:135;border: 1px solid black;'>"
											+"	<tr>                        "
											+"		<td class='class7'>     "
											+"			市委组织部          "
											+"		</td>                   "
											+"	</tr>                      "
											+"	<tr>                        "
											+"		<td class='class7'>     "
											+"			部务会议材料        "
											+"		</td>                   "
											+"	</tr>                       ";
										%>
								<tr>
									<td class="class7">
										市委组织部
									</td>
								</tr>
								<tr>
									<td class="class7">
										部务会议材料
									</td>
								</tr>
										<% 
									}else if("2".equals(meetingtype)){
										htmlstr=htmlstr+"<table style='width:150;border: 1px solid black;'>"
												+"	<tr>                        "
												+"		<td class='class7'>     "
												+"			市委五人小组会          "
												+"		</td>                   "
												+"	</tr>                     "
												+"	<tr>                        "
												+"		<td class='class7'>     "
												+"			会议材料        "
												+"		</td>                   "
												+"	</tr>                       ";
										%>
								<tr>
									<td class="class7">
										市委五人小组会
									</td>
								</tr>
								<tr>
									<td class="class7">
										会议材料
									</td>
								</tr>
										<%
									}else if("3".equals(meetingtype)){
										String meetingjc=list_meet.get(0).get("meetingjc").toString();
										String meetingpc=list_meet.get(0).get("meetingpc").toString();
										meetingjc=mpp.repNum(meetingjc);
										meetingpc=mpp.repNum(meetingpc);
										htmlstr=htmlstr+"<table style='width:180;border: 1px solid black;'>"
												+"	<tr>                        "
												+"		<td class='class7'>     "
												+"			"+meetingjc+"届市委常委会          "
												+"		</td>                   "
												+"	</tr>                      "
												+"	<tr>                        "
												+"		<td class='class7'>     "
												+"		第"+meetingpc+"次会议材料        "
												+"		</td>                   "
												+"	</tr>                       ";
										%>
								<tr>
									<td class="class7">
										<%=meetingjc %>届市委常委会
									</td>
								</tr>
								<tr>
									<td class="class7">
										第<%=meetingpc %>次会议材料
									</td>
								</tr>
										<%
									}
									htmlstr=htmlstr+"</table></td>"                             
											+"				<td width=50%' align='right'><div style='float:right;'>     "
											+"					<table>                           "
											+"						<tr>                          "
											+"							<td class='class8'>       "
											+"								会议材料              "
											+"							</td>                     "
											+"						</tr>                         "
											+"						<tr>                          "
											+"							<td class='class8'>       "
											+"								注意保管              "
											+"							</td>                     "
											+"						</tr>                         "
											+"					</table>                          "
											+"				</div>  </td></tr></table>                               "
											+"	<p></p>                                             ";
											%>
								
								</table>
					</td>
					<td width="63%">
					</td>
					<td  width="15%"  align="center">
						<table>
							<tr>
								<td class="class8">
									会议材料
								</td>
							</tr>
							<tr>
								<td class="class8">
									注意保管
								</td>
							</tr>
						</table>
					</td>
									<% 
								}else if("2".equals(agendatype)){
									
									%>
									<td></td>
									<%
								}%>
						
				</tr>
			</table>
		</td>
	</tr>
	<tr height="40px">
		<td></td>
	</tr>
	<%
	if("2".equals(type)){
		
	}else{
		htmlstr=htmlstr+"<div width='100%' align='center' class='class1'>            "
			+mpp.repNum(publishname)
			+"</div>       "
			+"<div width='100%' align='center' class='class2'>        "
			+mpp.repNum(meetingtime) 
			+"</div>   "
			+"<p></p>	                                      ";
	%>
	<tr>
		<td align="center" class="class1">
			<%=mpp.repNum(publishname) %>
		</td>
	</tr>
	<tr>
		<td align="center" class="class2">
			<%=mpp.repNum(meetingtime) %>
		</td>
	</tr>
	<tr height="40px">
		<td></td>
	</tr>
	<%
	}
	List<HashMap<String, Object>> list1=mpp.queryTitle(publishid, "-1");
	if(list1!=null&&list1.size()>0){
		int x1=0;
		int y1=0;
		int z1=0;
		for(int i=1;i<=list1.size();i++){
			HashMap<String, Object> map1=list1.get(i-1);
	%>	
	<tr>
	<%
			if("2".equals(type)){
				
			}else{
			if("1".equals(map1.get("title02"))){
				x1++;
				htmlstr=htmlstr+"<p class='class3'> "
					+mpp.queryNum(x1)+"、"+mpp.repNum(map1.get("title01"))
					+"</p>";
	%>
		<td class="class3"> 
			&nbsp;&nbsp;&nbsp;&nbsp;<%= mpp.queryNum(x1)%>、<%=mpp.repNum(map1.get("title01"))%>
		</td>
	<%
			}else if("2".equals(map1.get("title02"))){
				y1++;
				htmlstr=htmlstr+"<p class='class5'> "
						+"（"+mpp.queryNum(y1)+"）"+mpp.repNum(map1.get("title01"))
						+"</p>";
	%>
		<td  class="class5">
			&nbsp;&nbsp;&nbsp;&nbsp;（<%= mpp.queryNum(y1)%>）<%=mpp.repNum(map1.get("title01"))%>
		</td>
	<%
			}else if("3".equals(map1.get("title02"))){
				z1++;
				htmlstr=htmlstr+"<p class='class6'> "
				+mpp.repNum(z1)+"."+mpp.repNum(map1.get("title01"))
				+"</p>";
	%>
		<td  class="class6">
			&nbsp;&nbsp;&nbsp;&nbsp;<%=mpp.repNum(z1+"")+"."+mpp.repNum(map1.get("title01"))%>
		</td>
	<%
			}
			if(map1.get("title03")!=null&&!"".equals(map1.get("title03"))){
				htmlstr=htmlstr+"<p class='class4'> "
						+mpp.repNum(map1.get("title03"))
						+"</p>";
		%>
		</tr>
		<tr>
		<td class="class4">
			&nbsp;&nbsp;&nbsp;&nbsp;<%=mpp.repNum(map1.get("title03"))%>
		</td>
		<%
			}
			}
	%>
	</tr>
	<%
			if("1".equals(type)){
			List<HashMap<String, Object>> list2=mpp.queryPenson(publishid, map1.get("titleid").toString(),qx_type);
			if(list2!=null&&list2.size()>0){
				
	%>
	<tr>
		<td>
			<table class="class_tb">
	<%
				for(HashMap<String, Object> map2:list2){
					htmlstr=htmlstr+"<p class='class_tb'>"
						+"<span class='class3'>"+map2.get("a0101")+"</span>"+mpp.repNum(map2.get("a0102"))
						+"</p>"
						+"<p>"
						+""+mpp.repNum(map2.get("a0192a"))
						+"</p>";
	%>
			<tr>
				<td>
					&nbsp;&nbsp;&nbsp;&nbsp;<span class="class3" onclick="openPersonxx('<%=map2.get("sh000")%>','<%=map2.get("type")%>','<%=map2.get("publishname")%>','<%=map2.get("titleid")%>','<%=map2.get("titlename")%>');"><%=map2.get("a0101")%></span><%=mpp.repNum(map2.get("a0102"))%>
				</td>
			</tr>
			<tr>
				<td >
					&nbsp;&nbsp;&nbsp;&nbsp;<%=mpp.repNum(map2.get("a0192a"))%>
				</td>
			</tr>
					<%if(map2.get("tp0111")!=null){
						htmlstr=htmlstr+"<p>"
							+mpp.repNum((map2.get("tp0121")==null?"":map2.get("tp0121").toString())+map2.get("tp0111").toString())
							+"</p>";
					%>
			<tr>
				<td>
					&nbsp;&nbsp;&nbsp;&nbsp;<%=mpp.repNum((map2.get("tp0121")==null?"":map2.get("tp0121").toString())+map2.get("tp0111").toString())%>
				</td>
				
				
			</tr>
					<%}else if(map2.get("tp0111")==null&&"拟按期转正".equals(map2.get("tp0121"))){
						htmlstr=htmlstr+"<p>"
								+mpp.repNum((map2.get("tp0121")==null?"":map2.get("tp0121").toString()))
								+mpp.repNum((map2.get("tp0112")==null?"":map2.get("tp0112").toString()))
								+"</p>";
						%>
				<tr>
					<td>
						&nbsp;&nbsp;&nbsp;&nbsp;<%=mpp.repNum((map2.get("tp0121")==null?"":map2.get("tp0121").toString()))+mpp.repNum((map2.get("tp0112")==null?"":map2.get("tp0112").toString()))%>
					</td>
					
					
				</tr>
						<%
					}
					if(map2.get("tp0122")!=null||map2.get("tp0112")!=null){
						htmlstr=htmlstr+"<p>"
								+mpp.repNum(mpp.isnull(map2.get("tp0122"))+(map2.get("tp0112")==null?"":map2.get("tp0112").toString()))
								+"</p>";
					%>
			<tr>
				<td>
					&nbsp;&nbsp;&nbsp;&nbsp;<%=mpp.repNum(mpp.isnull(map2.get("tp0122"))+(map2.get("tp0112")==null?"":map2.get("tp0112").toString()))%>
				</td>
			</tr>
					<%}
					if(map2.get("tp0114")!=null){
						htmlstr=htmlstr+"<p>"
								+mpp.repNum(map2.get("tp0114"))
								+"</p>";
					%>
			<tr>
				<td>
					&nbsp;&nbsp;&nbsp;&nbsp;<%=mpp.repNum(map2.get("tp0114"))%>
				</td>
			</tr>	
	<%
					}
				}
	%>
			</table>
		</td>
	</tr>
	<%
			}
			}else if("2".equals(type)){//文档议题
				if(map1.get("title06")==null||"".equals(map1.get("title06").toString())){
					
				}else{
					String path=mpp.disk+map1.get("title05").toString();
					String pngPath=getClass().getResource("/").getFile().toString().replace("WEB-INF/classes/", "yulan/");
					pngPath=pngPath.substring(1);
					String pngname=map1.get("titleid").toString()+".svg";
					mpp.queryImg(path,pngPath,pngPath+pngname);
					
	%>
		<tr>
			<td>
				<img  src="<%=request.getContextPath()+"/yulan/"+pngname%>" width="700px">
			</td>
		</tr>
	<%
				}
			}else if("3".equals(type)){//动议议题
				List<HashMap<String, Object>> list2=mpp.queryDYPenson(publishid, map1.get("titleid").toString(),qx_type);
				if(list2!=null&&list2.size()>0){
					if(list2.size()>1){
					%>
					<tr>
						<td>
							<table class="class_tb">
					<%
								for(int d1=1;d1<=list2.size();d1++){
									HashMap<String, Object> map2=list2.get(d1-1);
									htmlstr=htmlstr+"<p class='class_tb'>"
										+"<span class='class3' >建议人选"+mpp.repNum(d1+"")+"："+map2.get("a0101")+"</span>，"+mpp.repNum(map2.get("a0102"))
										+"</p>";
						%>
								<tr>
									<td>
										&nbsp;&nbsp;&nbsp;&nbsp;<span class="class3">建议人选<%=mpp.repNum(d1+"")+"："+map2.get("a0101")%></span>，<%=mpp.repNum(map2.get("a0102"))%>
									</td>
								</tr>
							<%
										if(map2.get("tp0114")!=null){
											htmlstr=htmlstr+"<p class='class_tb'>"
													+mpp.repNum(map2.get("tp0114"))
													+"</p>";
							%>
								<tr>
									<td>
										&nbsp;&nbsp;&nbsp;&nbsp;<%=mpp.repNum(map2.get("tp0114"))%>
									</td>
								</tr>	
						<%
										}
									}
						%>
							</table>
						</td>
					</tr>
					<%
					}else{
					%>
					<tr>
						<td>
							<table class="class_tb">
					<%
									HashMap<String, Object> map2=list2.get(0);
									htmlstr=htmlstr+"<p class='class_tb'>"
											+"<span class='class3'>建议人选："+map2.get("a0101")+"，</span>"+mpp.repNum(map2.get("a0102"))
											+"</p>";
						%>
								<tr>
									<td>
										&nbsp;&nbsp;&nbsp;&nbsp;<span class="class3">建议人选：<%=map2.get("a0101")%>，</span><%=mpp.repNum(map2.get("a0102"))%>
									</td>
								</tr>
							<%
									if(map2.get("tp0114")!=null){
										htmlstr=htmlstr+"<p class='class_tb'>"
												+mpp.repNum(map2.get("tp0114"))
												+"</p>";
							%>
								<tr>
									<td>
										&nbsp;&nbsp;&nbsp;&nbsp;<%=mpp.repNum(map2.get("tp0114"))%>
									</td>
								</tr>	
						<%
									}
						%>
							</table>
						</td>
					</tr>
					<%
					}
				}
			}
			List<HashMap<String, Object>> list3=mpp.queryTitle(publishid, map1.get("titleid").toString());
			if(list3!=null&&list3.size()>0){
	%>
	<tr>
		<td>
			<table class="class_tb">
	<%
				int x2=0;
				int y2=0;
				int z2=0;
				for(int j=1;j<=list3.size();j++){
					HashMap<String, Object> map3=list3.get(j-1);
	%>
				<tr>
				<%
						if("1".equals(map3.get("title02"))){
							x2++;
							htmlstr=htmlstr+"<p class='class3'>"
								+ mpp.queryNum(x2)+"、"+mpp.repNum(map3.get("title01"))+"</td>";
				%>
					<td class="class3"> 
						&nbsp;&nbsp;&nbsp;&nbsp;<%= mpp.queryNum(x2)%>、<%=mpp.repNum(map3.get("title01"))%>
					</td>
				<%
						}else if("2".equals(map3.get("title02"))){
							y2++;
							htmlstr=htmlstr+"<p class='class5'>"
									+"（"+mpp.queryNum(y2)+"）"+mpp.repNum(map3.get("title01"))+"</p>";
				%>
					<td  class="class5">
						&nbsp;&nbsp;&nbsp;&nbsp;（<%= mpp.queryNum(y2)%>）<%=mpp.repNum(map3.get("title01"))%>
					</td>
				<%
						}else if("3".equals(map3.get("title02"))){
							z2++;
							htmlstr=htmlstr+"<p class='class6'>"
									+mpp.repNum(z2)+"."+mpp.repNum(map3.get("title01"))+"</p>";
				%>
					<td  class="class6">
						&nbsp;&nbsp;&nbsp;&nbsp;<%=mpp.repNum(z2)+"."+mpp.repNum(map3.get("title01"))%>
					</td>
				<%
						}
				%>
				</tr>
	<%
						if(map3.get("title03")!=null&&!"".equals(map3.get("title03"))){
							htmlstr=htmlstr+"<p class='class4'>"
								+mpp.repNum(map3.get("title03"))+"</p>";
					%>
					<tr>
						<td class="class4">
							&nbsp;&nbsp;&nbsp;&nbsp;<%=mpp.repNum(map3.get("title03"))%>
						</td>
					</tr>
					<%
						}
						if("1".equals(type)){
						List<HashMap<String, Object>> list4=mpp.queryPenson(publishid, map3.get("titleid").toString(),qx_type);
						if(list4!=null&&list4.size()>0){
				%>
				<tr>
					<td>
						<table class="class_tb">
				<%
							for(HashMap<String, Object> map4:list4){
								htmlstr=htmlstr+"<p class='class_tb'>"
									+"<span class='class3'>"+map4.get("a0101")+"</span>"+mpp.repNum(map4.get("a0102"))
									+"</p>"
									+"<p>"
									+""+mpp.repNum(map4.get("a0192a"))
									+"</p>";
				%>
						<tr>
							<td>
								&nbsp;&nbsp;&nbsp;&nbsp;<span class="class3"  onclick="openPersonxx('<%=map4.get("sh000")%>','<%=map4.get("type")%>','<%=map4.get("publishname")%>','<%=map4.get("titleid")%>','<%=map4.get("titlename")%>');"><%=map4.get("a0101")%></span><%=mpp.repNum(map4.get("a0102"))%>
							</td>
						</tr>
						<tr>
							<td>
								&nbsp;&nbsp;&nbsp;&nbsp;<%=mpp.repNum(map4.get("a0192a"))%>
							</td>
						</tr>
								<%if(map4.get("tp0111")!=null){
									htmlstr=htmlstr+"<p>"
										+mpp.repNum((map4.get("tp0121")==null?"":map4.get("tp0121").toString())+map4.get("tp0111").toString())
										+"</p>";
								%>
						<tr>
							<td>
								&nbsp;&nbsp;&nbsp;&nbsp;<%=mpp.repNum((map4.get("tp0121")==null?"":map4.get("tp0121").toString())+map4.get("tp0111").toString())%>
							</td>
						</tr>
								<%}else if(map4.get("tp0111")==null&&"拟按期转正".equals(map4.get("tp0121"))){
									htmlstr=htmlstr+"<p>"
											+mpp.repNum((map4.get("tp0121")==null?"":map4.get("tp0121").toString()))
											+"</p>";
									%>
							<tr>
								<td>
									&nbsp;&nbsp;&nbsp;&nbsp;<%=mpp.repNum((map4.get("tp0121")==null?"":map4.get("tp0121").toString()))%>
								</td>
								
								
							</tr>
									<%
								}
								if(map4.get("tp0122")!=null||map4.get("tp0112")!=null){
									htmlstr=htmlstr+"<p>"
											+mpp.repNum(mpp.isnull(map4.get("tp0122"))+(map4.get("tp0112")==null?"":map4.get("tp0112").toString()))
											+"</p>";
								%>
						<tr>
							<td>
								&nbsp;&nbsp;&nbsp;&nbsp;<%=mpp.repNum(mpp.isnull(map4.get("tp0122"))+(map4.get("tp0112")==null?"":map4.get("tp0112").toString()))%>
							</td>
						</tr>
								<%}
								if(map4.get("tp0114")!=null){
									htmlstr=htmlstr+"<p>"
											+mpp.repNum(map4.get("tp0114"))
											+"</p>";
								%>
						<tr>
							<td>
								&nbsp;&nbsp;&nbsp;&nbsp;<%=mpp.repNum(map4.get("tp0114"))%>
							</td>
						</tr>	
						<%
								}
							}
						%>
						</table>
					</td>
				</tr>
				<%
						}
						}else if("2".equals(type)){
							if(map3.get("title06")==null||"".equals(map3.get("title06").toString())){
								
							}else{
								String path=mpp.disk+map3.get("title05").toString()+map3.get("title06").toString();
								String pngPath=getClass().getResource("/").getFile().toString().replace("WEB-INF/classes/", "yulan/");
								pngPath=pngPath.substring(1);
								String pngname=map3.get("titleid").toString()+".png";
								mpp.queryImg(path,pngPath,pngPath+pngname);
								
				%>
					<tr>
						<td>
							<img  src="<%=request.getContextPath()+"/yulan/"+pngname%>" width="700px">
						</td>
					</tr>
				<%
							}
						}else if("3".equals(type)){//动议议题
							List<HashMap<String, Object>> list4=mpp.queryDYPenson(publishid, map3.get("titleid").toString(),qx_type);
							if(list4!=null&&list4.size()>0){
								if(list4.size()>1){
								%>
								<tr>
									<td>
										<table class="class_tb">
								<%
											for(int d2=1;d2<=list4.size();d2++){
												HashMap<String, Object> map4=list4.get(d2-1);
												htmlstr=htmlstr+"<p class='class_tb'>"
													+"<span class='class3'>建议人选"+mpp.repNum(d2+"")+"："+map4.get("a0101")+"</span>，"+mpp.repNum(map4.get("a0102"))
													+"</p>";
									%>
											<tr>
												<td>
													&nbsp;&nbsp;&nbsp;&nbsp;<span class="class3">建议人选<%=mpp.repNum(d2+"")+"："+map4.get("a0101")%></span>，<%=mpp.repNum(map4.get("a0102"))%>
												</td>
											</tr>
											<%
													if(map4.get("tp0114")!=null){
														htmlstr=htmlstr+"<p class='class_tb'>"
																+mpp.repNum(map4.get("tp0114"))
																+"</p>";
										%>
											<tr>
												<td>
													&nbsp;&nbsp;&nbsp;&nbsp;<%=mpp.repNum(map4.get("tp0114"))%>
												</td>
											</tr>	
									<%
													}
												}
									%>
										</table>
									</td>
								</tr>
								<%
								}else{
								%>
								<tr>
									<td>
										<table class="class_tb">
								<%
												HashMap<String, Object> map4=list4.get(0);
												htmlstr=htmlstr+"<p class='class_tb'>"
														+"<span class='class3'>建议人选："+map4.get("a0101")+"，</span>"+mpp.repNum(map4.get("a0102"))
														+"</p>";
									%>
											<tr>
												<td>
													&nbsp;&nbsp;&nbsp;&nbsp;<span class="class3">建议人选：<%=map4.get("a0101")%>，</span><%=mpp.repNum(map4.get("a0102"))%>
												</td>
											</tr>
											<%
													if(map4.get("tp0114")!=null){
														htmlstr=htmlstr+"<p class='class_tb'>"
																+mpp.repNum(map4.get("tp0114"))
																+"</p>";
										%>
											<tr>
												<td>
													&nbsp;&nbsp;&nbsp;&nbsp;<%=mpp.repNum(map4.get("tp0114"))%>
												</td>
											</tr>	
									<%
													}
									%>
										</table>
									</td>
								</tr>
								<%
								}
							}
						}
						List<HashMap<String, Object>> list5=mpp.queryTitle(publishid, map3.get("titleid").toString());
						if(list5!=null&&list5.size()>0){
					%>
					<tr>
						<td>
							<table class="class_tb">
					<%
								int x3=0;
								int y3=0;
								int z3=0;
								for(int k=1;k<=list5.size();k++){
									HashMap<String, Object> map5=list5.get(k-1);
					%>
								<tr>
									<%
											if("1".equals(map5.get("title02"))){
												x3++;
												htmlstr=htmlstr+"<p class='class3'>"+mpp.queryNum(x3)+"、"+mpp.repNum(map5.get("title01"))+"</p>";
									%>
										<td class="class3"> 
											&nbsp;&nbsp;&nbsp;&nbsp;<%= mpp.queryNum(x3)%>、<%=mpp.repNum(map5.get("title01"))%>
										</td>
									<%
											}else if("2".equals(map5.get("title02"))){
												y3++;
												htmlstr=htmlstr+"<p class='class5'>（"+ mpp.queryNum(y3)+"）"+mpp.repNum(map5.get("title01"))+"</p>";
									%>
										<td  class="class5">
											&nbsp;&nbsp;&nbsp;&nbsp;（<%= mpp.queryNum(y3)%>）<%=mpp.repNum(map5.get("title01"))%>
										</td>
									<%
											}else if("3".equals(map5.get("title02"))){
												z3++;
												htmlstr=htmlstr+"<p class='class6'>"+mpp.repNum(z3)+"."+mpp.repNum(map5.get("title01"))+"</p>";
									%>
										<td  class="class6">
											&nbsp;&nbsp;&nbsp;&nbsp;<%=mpp.repNum(z3)+"."+mpp.repNum(map5.get("title01"))%>
										</td>
									<%
											}
									%>
								</tr>
					<%
										if(map5.get("title03")!=null&&!"".equals(map5.get("title03"))){
											htmlstr=htmlstr+"<p class='class4'>"+mpp.repNum(map5.get("title03"))+"</p>";
						%>
									<tr>
										<td class="class4">
											&nbsp;&nbsp;&nbsp;&nbsp;<%=mpp.repNum(map5.get("title03"))%>
										</td>
									</tr>
									<%
										}
										if("1".equals(type)){
										List<HashMap<String, Object>> list6=mpp.queryPenson(publishid, map5.get("titleid").toString(),qx_type);
										if(list6!=null&&list6.size()>0){
								%>
								<tr>
									<td>
										<table class="class_tb">
								<%
											for(HashMap<String, Object> map6:list6){
												htmlstr=htmlstr+"<p class='class_tb'><span class='class3'>"+map6.get("a0101")+"</span>"+mpp.repNum(map6.get("a0102"))
												+"</p>"
												+"<p>"+mpp.repNum(map6.get("a0192a"))
												+"</p>";
								%>
										<tr>
											<td>
												&nbsp;&nbsp;&nbsp;&nbsp;<span class="class3"  onclick="openPersonxx('<%=map6.get("sh000")%>','<%=map6.get("type")%>','<%=map6.get("publishname")%>','<%=map6.get("titleid")%>','<%=map6.get("titlename")%>');"><%=map6.get("a0101")%></span><%=mpp.repNum(map6.get("a0102"))%>
											</td>
										</tr>
										<tr>
											<td>
												&nbsp;&nbsp;&nbsp;&nbsp;<%=mpp.repNum(map6.get("a0192a"))%>
											</td>
										</tr>
												<%if(map6.get("tp0111")!=null){
													htmlstr=htmlstr+"<p>"
														+mpp.repNum((map6.get("tp0121")==null?"":map6.get("tp0121").toString())+map6.get("tp0111").toString())
														+"</p>";
												%>
										<tr>
											<td>
												&nbsp;&nbsp;&nbsp;&nbsp;<%=mpp.repNum((map6.get("tp0121")==null?"":map6.get("tp0121").toString())+map6.get("tp0111").toString())%>
											</td>
										</tr>
												<%}else if(map6.get("tp0111")==null&&"拟按期转正".equals(map6.get("tp0121"))){
													htmlstr=htmlstr+"<p>"
															+mpp.repNum((map6.get("tp0121")==null?"":map6.get("tp0121").toString()))
															+"</p>";
													%>
											<tr>
												<td>
													&nbsp;&nbsp;&nbsp;&nbsp;<%=mpp.repNum((map6.get("tp0121")==null?"":map6.get("tp0121").toString()))%>
												</td>
												
												
											</tr>
													<%
												}
												if(map6.get("tp0122")!=null||map6.get("tp0112")!=null){
													htmlstr=htmlstr+"<p>"
															+mpp.repNum(mpp.isnull(map6.get("tp0122"))+(map6.get("tp0112")==null?"":map6.get("tp0112").toString()))
															+"</p>";
												%>
										<tr>
											<td>
												&nbsp;&nbsp;&nbsp;&nbsp;<%=mpp.repNum(mpp.isnull(map6.get("tp0122"))+(map6.get("tp0112")==null?"":map6.get("tp0112").toString()))%>
											</td>
										</tr>
												<%}
												if(map6.get("tp0114")!=null){
													htmlstr=htmlstr+"<p>"
															+mpp.repNum(map6.get("tp0114"))
															+"</p>";
												%>
										<tr>
											<td>
												&nbsp;&nbsp;&nbsp;&nbsp;<%=mpp.repNum(map6.get("tp0114"))%>
											</td>
										</tr>	
								<%
												}
											}
								%>
										</table>
									</td>
								</tr>
				<% 
									}
									}else if("2".equals(type)){
										if(map5.get("title06")==null||"".equals(map5.get("title06").toString())){
											
										}else{
											String path=mpp.disk+map5.get("title05").toString()+map5.get("title06").toString();
											String pngPath=getClass().getResource("/").getFile().toString().replace("WEB-INF/classes/", "yulan/");
											pngPath=pngPath.substring(1);
											String pngname=map5.get("titleid").toString()+".png";
											mpp.queryImg(path,pngPath,pngPath+pngname);
											
							%>
								<tr>
									<td>
										<img  src="<%=request.getContextPath()+"/yulan/"+pngname%>" width="700px">
									</td>
								</tr>
							<%
										}
									}else if("3".equals(type)){//动议议题
										List<HashMap<String, Object>> list6=mpp.queryDYPenson(publishid, map5.get("titleid").toString(),qx_type);
										if(list6!=null&&list6.size()>0){
											if(list6.size()>1){
											%>
											<tr>
												<td>
													<table class="class_tb">
											<%
														for(int d3=1;d3<=list6.size();d3++){
															HashMap<String, Object> map6=list6.get(d3-1);
															htmlstr=htmlstr+"<p class='class_tb'>"
																	+"<span class='class3'>建议人选"+mpp.repNum(d3+"")+"："+map6.get("a0101")+"</span>，"+mpp.repNum(map6.get("a0102"))
																	+"</p>";
												%>
														<tr>
															<td>
																&nbsp;&nbsp;&nbsp;&nbsp;<span class="class3">建议人选<%=mpp.repNum(d3+"")+"："+map6.get("a0101")%></span>，<%=mpp.repNum(map6.get("a0102"))%>
															</td>
														</tr>
														<%
																if(map6.get("tp0114")!=null){
																	htmlstr=htmlstr+"<p class='class_tb'>"
																			+mpp.repNum(map6.get("tp0114"))
																			+"</p>";
													%>
														<tr>
															<td>
																&nbsp;&nbsp;&nbsp;&nbsp;<%=mpp.repNum(map6.get("tp0114"))%>
															</td>
														</tr>	
												<%
																}
															}
												%>
													</table>
												</td>
											</tr>
											<%
											}else{
											%>
											<tr>
												<td>
													<table class="class_tb">
											<%
															HashMap<String, Object> map6=list6.get(0);
															htmlstr=htmlstr+"<p class='class_tb'>"
																	+"<span class='class3'>建议人选："+map6.get("a0101")+"，</span>"+mpp.repNum(map6.get("a0102"))
																	+"</p>";
												%>
														<tr>
															<td>
																&nbsp;&nbsp;&nbsp;&nbsp;<span class="class3">建议人选：<%=map6.get("a0101")%>，</span><%=mpp.repNum(map6.get("a0102"))%>
															</td>
														</tr>
														<%
																if(map6.get("tp0114")!=null){
																	htmlstr=htmlstr+"<p class='class_tb'>"
																			+mpp.repNum(map6.get("tp0114"))
																			+"</p>";
													%>
														<tr>
															<td>
																&nbsp;&nbsp;&nbsp;&nbsp;<%=mpp.repNum(map6.get("tp0114"))%>
															</td>
														</tr>	
												<%
																}
												%>
													</table>
												</td>
											</tr>
											<%
											}
										}
									}
							}
				%>
						</table>
					</td>
				</tr>
	<%
						}
				}
	%>
			</table>
		</td>
	</tr>
	<%
			}
		}
	}
	htmlstr=htmlstr+"</div></body></html>";
	wordpath=mpp.exportWord(htmlstr,publishid);
	%>
	<tr height="40px">
		<td></td>
	</tr>
</table>
</td>
<td width="2%">
</td>
</tr>
</table>
<% 		}
	}catch(Exception e){
		e.printStackTrace();
	}
%>
<odin:hidden property="rmbs"/>
<odin:hidden property="a0000" value="<%=a0000 %>"/>
<odin:hidden property="sh000" value="<%=sh000 %>"/>
<odin:hidden property="downfile" value="<%=wordpath %>"/>
<script type="text/javascript">

function removeRmbs(a0000){
	var rmbs=document.getElementById('rmbs').value;
	document.getElementById('rmbs').value=rmbs.replace(a0000,"");
}

function openPersonxx(sh000,type,publishname,titleid,titlename){
	if(type=='2'){
		alert("该人员为引用人员，信息来源为 "+publishname+" 议题！")
	}else if(type=='3'){
		
	}else{
		parent.queryPersonx(sh000,titleid,titlename);
	}
}

function wordbtn(){
	var downfile = document.getElementById('downfile').value;
	window.location.href="ProblemDownServlet?method=downFile&prid="+encodeURI(encodeURI(downfile));
}
function updateImg(str){
	document.getElementById('iphoto_pathi').src='<%=request.getContextPath()%>/servlet/DownloadUserHeadImage?a0000='+str;
}

function openRmb(){
	radow.doEvent("openRmb");
}

</script>
