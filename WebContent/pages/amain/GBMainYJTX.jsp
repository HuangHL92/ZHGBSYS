<%@page import="com.insigma.odin.framework.util.GlobalNames"%>
<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>

<style>
body{
	font-family: "Helvetica Neue",Helvetica,"PingFang SC","Hiragino Sans GB","Microsoft YaHei","微软雅黑",Arial,sans-serif;
    font-size: 26px;
    line-height: 1.5;
    color: #515a6e;
    background-color: #fff;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    height: 100%;
    
}
.main-section{
	padding: 0px 3% 0px 3%;
    width: 94%;
    background-color: hsla(0,0%,100%,.8);
    border-radius: 8px;
    margin: 0px 0px 0px 0px;
    overflow: auto;
    position: relative;
    height: 100%;
    display: block;
   /*  background-color: rgb(244, 252, 253) !important; */
}
.main-section-content{
 	background-image: url(pages/amain/img/yj_background.png) ;
 	background-repeat: no-repeat;
 	position: relative;
 	background-attachment: fixed;
 	background-size: cover;
 	width:100%
}
p{
	display: block;
    margin-block-start: 1em;
    margin-block-end: 1em;
    margin-inline-start: 0px;
    margin-inline-end: 0px;
}
.content-title{
	text-align:center;
	height: 33px;
    font-size: 24px;
    margin-top: 20px;
    font-weight: 600;
    color: #000;
    line-height: 33px;
    background: linear-gradient(180deg,#27bcff,#4a5ba8);
    -webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
}

.content-title span {
	
	width: 24px;
    height: 24px;
    display: inline-block;
    margin-right: 10px;
    
}

.subcontent,td{
	line-height: 32px;
    font-size: 18px;
    font-weight: bolder;
    color: #3c3c3c;
    padding-left: 35px;
}
a{
    padding: 0 15px;
    color: #2d8cf0;
    background: transparent;
    text-decoration: none;
    outline: none;
    cursor: pointer;
    transition: color .2s ease;
    background-color: transparent;
    -webkit-text-decoration-skip: objects;
}

a:hover {
    color: #57a3f3;
    outline-width: 0;
    outline: 0;
    text-decoration: none;
}
#ext-gen9{
	width:120px;
	margin-left:25px	
}

#datetime{
	width:140px;
	border:none;
	font-size:16px
}
</style>

<link rel="stylesheet" type="text/css" href="pages/amain/js/yjxx.css"> 




<section class="main-section">
	<div class="main-section-content">
		<p class="content-title">
			<span></span>调配工作提醒
		</p>
		<p class="subcontent" style="text-align:center;">
			1.干部到龄退休人员
		</p>
		<div style="display: table; text-align: center;width: 100% " >
	
			<table id="coordTable" cellspacing="0" style="margin:auto"  >

				<tr>
					<td class="tableTile" style="width: 25%;">-</td>
					<td class="tableTile" style="width: 25%;" >本月到龄</td>
					<td class="tableTile" style="width: 25%;" >下月到龄</td>
					<td class="tableTile" style="width: 25%;" >本年度</td>
				</tr>
				<tr>
					<td>区县市</td>
					<td><a href="javascript:void(0);" class="qxcount1" ></a></td>
					<td><a href="javascript:void(0);" class="qxcount2" ></a></td>
					<td><a href="javascript:void(0);" class="qxcount3" ></a></td>
				</tr>
				<tr>
					<td>市直单位</td>
					<td><a href="javascript:void(0);" class="szcount1" ></a></td>
					<td><a href="javascript:void(0);" class="szcount2" ></a></td>
					<td><a href="javascript:void(0);" class="szcount3" ></a></td>
				</tr>
				<tr>
					<td>国企高校</td>
					<td><a href="javascript:void(0);" class="gqgxcount1" ></a></td>
					<td><a href="javascript:void(0);" class="gqgxcount2" ></a></td>
					<td><a href="javascript:void(0);" class="gqgxcount3" ></a></td>
				</tr>
				<tr>
					<td>总计</td>
					<td><a href="javascript:void(0);" class="count1" ></a></td>
					<td><a href="javascript:void(0);" class="count2" ></a></td>
					<td><a href="javascript:void(0);" class="count3" ></a></td>
				</tr>
			</table>
			
		</div>
		
		
<!-- 		<p class="subcontent">
			1.<span> 干部到龄退休</span>
			<a href="javascript:void(0);" class="count1" >本月到龄(人)</a> 
			<a href="javascript:void(0);" class="count2" >下月到龄(人)</a>
			<a href="javascript:void(0);" class="count3">本年度到龄(人)</a>
		</p> -->
		
		<p class="subcontent" style="text-align:center;">
			2.可退出领导岗位转任职级人员
		</p>
		<div style="display: table; text-align: center;width: 100% " >
	
			<table id="coordTable" cellspacing="0" style="margin:auto"  >

				<tr>
					<td class="tableTile" style="width: 160;">-</td>
					<td class="tableTile" style="width: 160;" >6月可办理</td>
					<td class="tableTile" style="width: 160;" >12月可办理</td>	
					<td class="tableTile" style="width: 200;" >到龄截至时间</td>		
					<odin:dateEdit property="datetime" format="Ymd" onchange="yearchange()"  width="100" style="width:100%;margin-left:0px;" ></odin:dateEdit>	
					<odin:hidden property="searchdata"/>		
<%-- 					<odin:select2  property="searchdata"  onchange="yearchange()"  data="['1','1'],['2','2'],['3','3'],['4','4'],['5','5'],['6','6']
					,['7','7'],['8','8'],['9','9'],['10','10'],['11','11'],['12','12']"  width="100" style="background-color: rgb(230, 228, 228);"></odin:select2>					
 --%>			
 					
 				</tr>
				<tr>
					<td>正职</td>
					<td><a href="javascript:void(0);" class="zz6ytc" >-</a></td>
					<td><a href="javascript:void(0);" class="zz12ytc" ></a></td>
					<td colspan="2"><a href="javascript:void(0);" class="zzmytc" ></a></td>
				</tr>
				<tr>
					<td>副职</td>
					<td><a href="javascript:void(0);" class="fz6ytc" ></a></td>
					<td><a href="javascript:void(0);" class="fz12ytc" ></a></td>
					<td colspan="2"><a href="javascript:void(0);" class="fzmytc" ></a></td>
				</tr>
			</table>
			
		</div>
<!-- 		<p class="subcontent">
			2.<span> 可退出领导岗位转任职级的</span>
			<a href="javascript:void(0);" class="count7" >市管正职(人)</a>
			<a href="javascript:void(0);" class="count8" >市管副职(人)</a>
		</p> -->
		<p class="subcontent" style="text-align:center;">
			3.<span> 符合晋升公务员职级条件人员</span>
<!-- 			<a href="javascript:void(0);" class="count4" >（一巡）(人)</a>
			<a href="javascript:void(0);" class="count5" >（二巡）(人)</a> -->
		</p>
			<div style="display: table; text-align: center;width: 100% " >
	
				<table id="coordTable" cellspacing="0" style="margin:auto"  >

				<tr>
					<td class="tableTile" style="width: 20%;">-</td>
					<td class="tableTile" style="width: 40%;" >一级巡视员</td>
					<td class="tableTile" style="width: 40%;" >二级巡视员</td>	
 					
 				</tr>
 				<tr>
					<td  style="width: 20%;">今年满57岁</td>
					<td  style="width: 40%;" ><a href="javascript:void(0);" class="count4" ></a></td>
					<td  style="width: 40%;" ><a href="javascript:void(0);" class="count5" ></a></td>	
 					
 				</tr>
 				<tr>
					<td  style="width: 20%;">今年未满57岁</td>
					<td  style="width: 40%;" ><a href="javascript:void(0);" class="count6" ></a></td>
					<td  style="width: 40%;" ><a href="javascript:void(0);" class="count7" ></a></td>	
 					
 				</tr>
 				<tr>
					<td  style="width: 20%;">副师军转干部</td>
					<td  style="width: 40%;" >-</td>
					<td  style="width: 40%;" ><a href="javascript:void(0);" class="count8" ></a></td>	
 					
 				</tr>
 				</table>

			
		</div>
		
		
		<p class="subcontent" style="text-align:center;">
			4.<span> 试用期转正情况</span>
<!-- 			<a href="javascript:void(0);" class="count11" >任现职近5年(人)</a>
			<a href="javascript:void(0);" class="count12" >任现职近8年(人)</a> 
			<a href="javascript:void(0);" class="count13" >任现职近10年(人)</a> 
			<a href="javascript:void(0);" class="count14" >任现职10年及以上(人)</a> -->
		</p>
				<div style="display: table; text-align: center;width: 100% " >
	
				<table id="coordTable" cellspacing="0" style="margin:auto"  >

				<tr>
					<td class="tableTile" style="width: 25%;">-</td>
					<td class="tableTile" style="width: 25%;" >本月试用期满</td>
					<td class="tableTile" style="width: 25%;" >下月试用期满</td>	
 					<td class="tableTile" style="width: 25%;" >试用期人员</td>	

 				</tr>
 				<tr>
					<td  style="width: 25%;">人数</td>
					<td  style="width: 25%;" ><a href="javascript:void(0);" class="bysyqjm" ></a></td>
					<td  style="width: 25%;" ><a href="javascript:void(0);" class="xysyqjm" ></a></td>
					<td  style="width: 25%;" ><a href="javascript:void(0);" class="syqry" ></a></td>
 					
 				</tr>
 				</table>

			
		</div>
		
		
		
		<p class="subcontent" style="text-align:center;">
			5.<span> 任职时间较长干部</span>

		</p>
				<div style="display: table; text-align: center;width: 100% " >
	
				<table id="coordTable" cellspacing="0" style="margin:auto"  >

				<tr>
					<td class="tableTile" style="width: 20%;">-</td>
					<td class="tableTile" style="width: 20%;" >任现职近5年</td>
					<td class="tableTile" style="width: 20%;" >任现职近8年</td>	
 					<td class="tableTile" style="width: 20%;" >任现职近10年</td>	
 					<td class="tableTile" style="width: 20%;" >任现职10年及以上</td>	
 				</tr>
 				<tr>
					<td  style="width: 20%;">人数</td>
					<td  style="width: 20%;" ><a href="javascript:void(0);" class="count11" ></a></td>
					<td  style="width: 20%;" ><a href="javascript:void(0);" class="count12" ></a></td>
					<td  style="width: 20%;" ><a href="javascript:void(0);" class="count13" ></a></td>
					<td  style="width: 20%;" ><a href="javascript:void(0);" class="count14" ></a></td>	
 					
 				</tr>
 				</table>

			
			</div>
		
		<p class="content-title extrayj">
			<span></span>监督管理事项提醒
		</p>
		<p class="subcontent extrayj">
<!-- 			1.<span>个人有关事项工作</span><a href="javascript:void(0);"  >房产5套及以上干部名单</a>
			<a href="javascript:void(0);"  >配偶和子女经商办企业认缴出资2000万元以上干部名单</a>
 			<a href="javascript:void(0);"  >配偶和子女移居国(境)外干部名单</a>
 			<a href="javascript:void(0);"  >家庭金融投资2000万元以上干部</a>
 			<br/> -->
 			<a href="javascript:void(0);"  onclick="openMateXF('信访有关工作')" style="margin-left:120px">1.有关信访情况</a>
 			<br/>
 			<a href="javascript:void(0);"   onclick="openMate2('scfgb')" style="margin-left:120px">2.干部受处分情况</a>
 			<br/>
 			<a href="javascript:void(0);"  onclick="openMate2('gbstjk')" style="margin-left:120px">3.干部身体健康情况</a>
 			<br/>
 			<a href="javascript:void(0);"  onclick="openMateJD('子女移居国外')" style="margin-left:120px">4.子女移居国（境）外的干部名单</a>
			<br/>
 			<a href="javascript:void(0);"  onclick="openMateGJ1('fc5t')" style="margin-left:120px">5.房产数量5套以上干部名单</a>
			<br/>
 			<a href="javascript:void(0);"  onclick="openMateGJ1('jttz2000w')" style="margin-left:120px">6.家庭投资2000万以上干部名单</a>
			<br/>
 			<a href="javascript:void(0);"  onclick="openMateGJ1('jsb2000w')" style="margin-left:120px">7.经商办企业出资总额2000万元及以上干部名单</a>
			
		</p>
		
		
		<p class="content-title">
			<span></span>考核情况提醒
		</p>
		<p class="subcontent">
<!--  			1.<span>政治素质考核</span><a href="javascript:void(0);"  >优秀班子</a>
 			<a href="javascript:void(0);"  >优秀干部</a>
 			<a href="javascript:void(0);"  >一般班子</a>
 			<a href="javascript:void(0);"  >一般干部</a>
 			<br/> -->
			<span style="margin-left:130px">2020年年度考核</span><a href="javascript:void(0);"  onclick="openMate3('年度考核优秀班子')">优秀班子</a>
			<a href="javascript:void(0);"   onclick="openMate('ngkhyxgb')">优秀干部</a>
 			<a href="javascript:void(0);"  onclick="openMate3('年度考核一般班子')">一般班子</a>
 			<a href="javascript:void(0);"   onclick="openMate('ngkhybgb')">一般干部</a>
			<br/>
<!-- 			3.<span>专项考核</span><a href="javascript:void(0);"  >优秀班子</a>
			<a href="javascript:void(0);"  >优秀干部</a>
 			<a href="javascript:void(0);"  >一般班子</a>
 			<a href="javascript:void(0);"  >一般干部</a> -->
		</p>
		
		
		
		<p class="content-title">
			<span></span>干部教育相关提醒
		</p>
		<p class="subcontent">
		 	<a href="javascript:void(0);" style="margin-left:120px"  onclick="openMateGJ1('edugzmd')" >&nbsp;&nbsp;1.本年度市管领导干部网络学习学时关注名单</a>
 			<br/>
 			<a href="javascript:void(0);" style="margin-left:120px"  onclick="openMateGJ('edu330')" >&nbsp;&nbsp;2.近三年市管领导干部脱产培训总学时数少于330学时关注名单</a>
 			<br/>
			<a href="javascript:void(0);" style="margin-left:120px" onclick="openMateGJ1('edu36')" >&nbsp;&nbsp;3.近三年区县市党政班子成员理论和党性教育总天数少于36天关注名单</a> 
<!-- 			<a href="javascript:void(0);" class="count13" >任现职近10年(人)</a> 
			<a href="javascript:void(0);" class="count14" >任现职10年及以上(人)</a>  -->
		</p>
		

	</div>
	
	<!-- <div class="main-section-content">
		<p class="content-title">
			<span></span>监督管理事项提醒
		</p>
		<p class="subcontent">
			1.<span> 个人有关事项工作</span><a href="javascript:void(0);">房产5套及以上干部名单</a>
			<a href="javascript:void(0);">配偶和子女经商办企业认缴出资1000万元以上干部名单</a> <a
				href="javascript:void(0);">配偶和子女移居国(境)外干部名单</a> <a
				href="javascript:void(0);">家庭金融投资1000万元以上干部</a> <a
				href="javascript:void(0);">近亲属司法机关责任追究情况</a>
		</p>
		<p class="subcontent">
			2.<span><a href="javascript:void(0);" onclick="openMate2('信访有关工作')" >信访有关工作</a></span>
		</p>
		<p class="subcontent">
			3.<span><a href="javascript:void(0);" onclick="openMate2('受处分干部情况')" >受处分干部情况</a></span>
		</p>
		<p class="subcontent">
			4.<span><a href="javascript:void(0);" onclick="openMate2('干部身体健康情况')" >干部身体健康情况</a></span>
		</p>
	</div>
	<div class="main-section-content">
		<p class="content-title">
			<span></span>结构性干部配备提醒
		</p>
		<p class="subcontent">
			1.<span><a>年轻干部配备</a></span>
		</p>
		<p class="subcontent">
			2.<span><a>女干部配备</a></span>
		</p>
		<p class="subcontent">
			3.<span><a>党外干部配备</a></span>
		</p>
	</div>
	<div class="main-section-content">
		<p class="content-title">
			<span></span>考核情况提醒
		</p>
		<p class="subcontent">
			1.<span> 政治素质考核</span><a href="javascript:void(0);">优秀班子</a> <a
				href="javascript:void(0);">优秀干部</a><a href="javascript:void(0);">一般班子</a>
			<a href="javascript:void(0);">一般干部</a>
		</p>
		<p class="subcontent">
			2.<span> 年度考核</span><a href="javascript:void(0);">优秀班子</a> <a
				href="javascript:void(0);">优秀干部</a><a href="javascript:void(0);">一般班子</a>
			<a href="javascript:void(0);">一般干部</a>
		</p>
		<p class="subcontent">
			3.<span> 专项考核</span><a href="javascript:void(0);">优秀班子</a> <a
				href="javascript:void(0);">优秀干部</a><a href="javascript:void(0);">一般班子</a>
			<a href="javascript:void(0);">一般干部</a>
		</p>
	</div>
	<div class="main-section-content">
		<p class="content-title">
			<span></span>干部教育相关提醒
		</p>
		<p class="subcontent">
			1.<span><a>近三年脱产培训总学时数少于330学时预警关注名单</a></span>
		</p>
		<p class="subcontent">
			2.<span><a>2021年1-2月市管领导干部学分关注名单</a></span>
		</p>
	</div> -->
</section>


<script type="text/javascript">
function setCountData(adata){
	$.each(adata,function(i,dataJSON){
		$('.count'+dataJSON['type']).text(dataJSON['dl']+"人")
		.attr('YJTXSQLType','YJTXSQLType'+dataJSON['type'])
		.bind('click',function(){
			openMate('YJTXSQLType'+dataJSON['type'])
		});
		if(dataJSON['type']=='1' ){
			if(parseInt(dataJSON['dl'])>0){
				$('.count'+dataJSON['type']).text('★'+dataJSON['dl']+"人").addClass('warning');					
			}
		};
	});
	
}

function setGBDLTXData(adata){
	$.each(adata,function(i,dataJSON){
		$('.'+dataJSON['type']).text(dataJSON['count(1)']+"人")
		.attr('YJTXSQLType',dataJSON['type'])
		.bind('click',function(){
			openMate(dataJSON['type'])
		});
		if(dataJSON['type']=='szcount1' || dataJSON['type']=='qxcount1' || dataJSON['type']=='gqgxcount1' || 
			dataJSON['type']=='bysyqjm' 	){
			if(parseInt(dataJSON['count(1)'])>0){
				$('.'+dataJSON['type']).text('★'+dataJSON['count(1)']+"人").addClass('warning');		
			}
		};
	});
	
}

function yearchange(){

	radow.doEvent("yearchange");
	
}
//普通列表
function openMate(p){
	$h.openWin('gbmainListWin','pages.amain.GBMainList','人员列表',1410,900,'','<%=request.getContextPath()%>',null,{query_type:p},true);
}
//受处分 干部身体健康列表
function openMate2(p){
	$h.openWin('gbmainListWin2','pages.amain.GBMainYJList','人员列表',1410,900,'','<%=request.getContextPath()%>',null,{query_type:p},true);
}
//信访列表
function openMateXF(p){
	$h.openWin('gbmainListWin2','pages.amain.GBMainYJList1','人员列表',1410,900,'','<%=request.getContextPath()%>',null,{query_type:p},true);
}
//班子列表
function openMate3(p){
	$h.openWin('gbmainListWin3','pages.amain.GBMainYJListBZ','班子列表',1410,900,'','<%=request.getContextPath()%>',null,{query_type:p},true);
}
//干部教育列表1
function openMateGJ(p){
	$h.openWin('gbmainListWin3','pages.amain.GBMainYJListGJ','人员列表',1410,900,'','<%=request.getContextPath()%>',null,{query_type:p},true);
}
//干部教育列表2
function openMateGJ1(p){
	$h.openWin('gbmainListWin3','pages.amain.GBMainYJListGJ1','人员列表',1410,900,'','<%=request.getContextPath()%>',null,{query_type:p},true);
}
//干部监督列表
function openMateJD(p){
	$h.openWin('gbmainListWin3','pages.amain.GBMainYJListJD','人员列表',1410,900,'','<%=request.getContextPath()%>',null,{query_type:p},true);
}
</script>