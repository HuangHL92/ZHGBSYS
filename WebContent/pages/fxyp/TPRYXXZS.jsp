<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ include file="/comOpenWinInit2.jsp" %>
<script src="<%=request.getContextPath()%>/basejs/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<html>
<head>
<meta http-equiv=Content-Type content="text/html; charset=gb2312">
<title></title>
<script type="text/javascript">
</script>
<style>
.rmbword{
    font-family: 宋体;
    position: absolute;
    left:0; right:0;  bottom:0;
    margin:auto;
    top:10px;
    width: 100%;
    float: right;
}
td{
	font-family: 宋体;
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

.class1 {
border-left: 2px solid #74A6CC;
border-top: 2px solid #74A6CC;
}
.class2 {
border-left: 2px solid #74A6CC;
border-right: 2px solid #74A6CC;
border-top: 2px solid #74A6CC;
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

a:link {color: black; text-decoration:none;} 
a:active:{color: red; } 
a:hover {color: red; text-decoration:underline;} //鼠标移近：红色、下划线

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
</style>
</head>
<odin:hidden property="a0000"/>
<odin:hidden property="location" value="1"/>
<body >
	  <div id="div_top" style="width: 200px;height:800;position:fixed;z-index:3;">
	  	<table style="margin-top: 30px;">
	  		<tr>
	  			<td style="width: 130px;height: 35px;" bgcolor="#00BFFF" align="center"><a href="#tab1"><span id="sp1">人员基本信息</span></a></td>
	  		</tr>
	  		<tr >
	  			<td style="width: 130px;height: 35px"  bgcolor="#00BFFF" align="center" ><a href="#tab2"><span id="sp2">经历标签信息</span></a></td>
	  		</tr>
	  		<tr >
	  			<td style="width: 130px;height: 35px"  bgcolor="#00BFFF" align="center"><a href="#tab3"><span id="sp3">代表委员信息</span></a></td>
	  		</tr>
	  		<tr>
	  			<td style="width: 130px;height: 35px"  bgcolor="#00BFFF" align="center"><a href="#tab4"><span id="sp4">干部预警信息</span></a></td>
	  		</tr>
	  	</table>
	  </div>
      <div  class="rmbword" >
        <div style='text-align:center;'  align="center">
          	<h1 style="font-size: 24px;"  ><a name="tab1" style="text-decoration: none">干&ensp;&ensp;部&ensp;&ensp;任&ensp;&ensp;免&ensp;&ensp;审&ensp;&ensp;批&ensp;&ensp;表</a></h1>
	         <br><br>
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
	                  <img src='<%= request.getContextPath()%>/servlet/DownloadUserHeadImage?a0000=' id="iphoto_pathi" style=" width:136px;height:170px;" />
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
        </div>
        <br>
        <br>
        <div style='text-align:center;'  align="center">
        	<h1 style="font-size: 24px;" ><a name="tab2" style="text-decoration: none">经&ensp;&ensp;历&ensp;&ensp;标&ensp;&ensp;签&ensp;&ensp;信&ensp;&ensp;息</a></h1>
        	<br>
        	<table style="font-size: 18px;margin-left: 150px;">
				<tr>
					<td>历任重要职务重要经历：</td>
				</tr>
				<tr>
					<td id="a0193z"></td>
				</tr>
				<tr height="10">
					<td></td>
				</tr>
				<tr>
					<td>熟悉领域：</td>
				</tr>
				<tr>
					<td id="a0194z"></td>
				</tr>
			</table>
        </div>
        <br>
        <br>
        <div style='text-align:center;'  align="center">
        	<h1 style="font-size: 24px;"><a name="tab3" style="text-decoration: none" >代&ensp;&ensp;表&ensp;&ensp;委&ensp;&ensp;员&ensp;&ensp;信&ensp;&ensp;息</a></h1>
        	<br>
        	<table style="font-size: 18px;margin-left: 150px;">
				<tr>
					<td>代表委员综述：</td>
				</tr>
				<tr>
					<td id="dbwy"></td>
				</tr>
			</table>
        </div>
        <br>
        <br>
        <div style='text-align:center;'  align="center">
        	<h1 style="font-size: 24px;"><a name="tab4" style="text-decoration: none">干&ensp;&ensp;部&ensp;&ensp;预&ensp;&ensp;警&ensp;&ensp;信&ensp;&ensp;息</a></h1>
        	<br>
        	<table style="font-size: 18px;margin-left: 150px;">
				<tr>
					<td>到龄到任情况：</td>
				</tr>
				<tr>
					<td id="dldr"></td>
				</tr>
			</table>
			<br>
			<table id="coordTable" cellspacing="0" style="font-size: 18px;margin-left: 150px;">
				<tr><td colspan="3">提醒函询诫勉情况：</td></tr>
				<tr  align="center">
					<td width="150" class='class1'>处理时间</td>
					<td width="150" class='class1'>受处理情况</td>
					<td width="400" class='class2'>案情摘要</td>
				</tr>
			</table>
			<table id="coordTable2" cellspacing="0" style="font-size: 18px;margin-left: 150px;">
			</table>
        </div>
        <div style="height: 400px"></div>
      </div>
</body>
</html>
<script type="text/javascript">
<%
String a0000 = request.getParameter("a0000");

%>
Ext.onReady(function() {
	if(""!='<%=a0000==null?"":a0000%>'){
		$('#a0000').val('<%=a0000%>');
	}else{
		document.getElementById('a0000').value = parentParam.a0000;
	}
	
	//document.getElementById('location').value = parentParam.location;
});

function updateImg(str){
	document.getElementById('iphoto_pathi').src='<%=request.getContextPath()%>/servlet/DownloadUserHeadImage?a0000='+str;
}

function Add(data){
	$.each(data, function (i,item) {
		var tr;
		tr = $('<tr align="center"><td width="150" class="class1"></td><td width="150" class="class1"></td><td width="400" class="class1"></td></tr>');	
		var tds = $("td", tr);
 		SetTDtext(tds[0],item["time"]); 
		SetTDtext(tds[1],item["nr"]); 
		SetTDtext(tds[2],item["type"]);
		$('#coordTable2').append(tr);
	});
}
function SetTDtext(td,v) {
  $(td).html((v==""||v==null||v=="null"||v=="0")?" ":v.replace(/\n/g,"<br/>"));
}

</script>

