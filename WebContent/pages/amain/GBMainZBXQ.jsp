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
    font-size: 18px;
    font-weight: bolder;
    color: #3c3c3c;
    padding-left: 35px;
}
a{
    /* padding: 0 15px; */
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
.coordTable{
	max-width: none!important;
	border: 3px solid #1890FF!important;
	width: 95%;
	border-color:#1890FF;
}
.coordTable th{
	background: #CAE8EA ; 
	text-align: center;
	height:40px;
	border-color:#1890FF;
}

.coordTable th,.coordTable td{
	font-family: 宋体,Times New Roman ;
	font-weight: bold;
	border:1px solid;
	border-color: rgb(162 162 162);
	padding: 2px;
}
.tableTile{
	font-size: 16px;
	background-color: #DAEAFD!important;
	font-family: 黑体!important ;
}
.coordTable td{
	text-align: center;
	height:40px;
	border-color:#1890FF;
	background-color: rgb(248,248,248);
}
.classBT{
	border-top: 3px solid rgb(152 152 152)!important;
}
.classBR{
	border-right: 3px solid rgb(152 152 152)!important;
}
.warning{
	color:red
}

tr[class^="dw-rowdata"] td{
	background-color: rgb(255,255,255)!important;
}
.toclick{
cursor: pointer;
}
</style>


<script type="text/javascript">
function openMate(p){
	$h.openWin('zbxqmateWin','pages.amain.GBMainZBXQMate','人员列表',1410,900,'','<%=request.getContextPath()%>',null,p,true);
}
</script>


<section class="main-section">
	<div class="main-section-content">
		<p class="content-title">
			<span></span>指标详情
		</p>
		<p class="subcontent" style="text-align:center;">
			1.推进高质量发展排名情况
		</p>
		<div style="display: table; text-align: center;width: 100% " >
	
			<table class="coordTable" cellspacing="0" style="margin:auto"  >

				<tr>
					<td class="tableTile" style="width: 50%;">年度</td>
					<td class="tableTile" style="width: 50%;" >排名</td>
				</tr>
				<tr>
					<td>2021</td>
					<td><a href="javascript:void(0);" class="zb1" ></a></td>
				</tr>
				
			</table>
			
		</div>
		
		
		<p class="subcontent" style="text-align:center;">
			2.市直单位领导干部有关指标（指标2、3）
		</p>
		<div style="display: table; text-align: center;width: 100% " >
	
			<table class="coordTable tablezb2" cellspacing="0" style="margin:auto"  >

				<tr>
					<td class="tableTile" rowspan="2" style="width: 16%;">单位</td>
					<td class="tableTile" colspan="2">市直单位领导班子成员人数</td>
					<td class="tableTile" colspan="2" >市直单位领导班子成员中45岁左右干部</td>
					<td class="tableTile" colspan="2" >市直单位领导班子成员中40岁左右及以下干部</td>
					<td class="tableTile" colspan="2">市直单位处级领导干部人数</td>
					<td class="tableTile" colspan="2" >市直单位处级领导干部中40岁以下干部</td>
					<td class="tableTile" colspan="2" >市直单位处级领导干部中35岁左右及以下干部</td>
				</tr>
				<tr>
					<td class="tableTile" style="width: 7%;">职数</td>
					<td class="tableTile" style="width: 7%;">实配</td>
					<td class="tableTile" style="width: 7%;">人数</td>
					<td class="tableTile" style="width: 7%;">占比</td>
					<td class="tableTile" style="width: 7%;">人数</td>
					<td class="tableTile" style="width: 7%;">占比</td>
					<td class="tableTile" style="width: 7%;">职数</td>
					<td class="tableTile" style="width: 7%;">实配</td>
					<td class="tableTile" style="width: 7%;">人数</td>
					<td class="tableTile" style="width: 7%;">占比</td>
					<td class="tableTile" style="width: 7%;">人数</td>
					<td class="tableTile" style="width: 7%;">占比</td>
				</tr>
			</table>
			
		</div>
		
		
		
		
		
		<p class="subcontent" style="text-align:center;">
			4.区、县（市）班子有关指标（指标4、5、16）
		</p>
		<div style="display: table; text-align: center;width: 100% " >
	
			<table class="coordTable tablezb4" cellspacing="0" style="margin:auto"  >

				<tr>
					<td class="tableTile" rowspan="2" style="width: 9%;">单位</td>
					<td class="tableTile" colspan="3" >党政班子成员人数</td>
					<td class="tableTile" colspan="2" >党政正职中40岁左右及以下干部</td>
					<td class="tableTile" colspan="2" >党政领导班子中40岁左右及以下干部</td>
					<td class="tableTile" colspan="2" >具有乡镇（街道）党政正职经历的党政领导班子成员</td>
					<td class="tableTile" colspan="2" >市域范围内，县级党政领导班子成员中具有乡镇（街道）党政正职经历达到50%以上的县市区</td>
				</tr>
				<tr>
					<td class="tableTile" style="width: 8%;">职数</td>
					<td class="tableTile" style="width: 8%;">实配</td>
					<td class="tableTile" style="width: 8%;">交叉任职</td>
					<td class="tableTile" style="width: 8%;">人数</td>
					<td class="tableTile" style="width: 8%;">占比</td>
					<td class="tableTile" style="width: 8%;">人数</td>
					<td class="tableTile" style="width: 8%;">占比</td>
					<td class="tableTile" style="width: 8%;">人数</td>
					<td class="tableTile" style="width: 9%;">占比</td>
					<td class="tableTile" style="width: 9%;">总数</td>
					<td class="tableTile" style="width: 9%;">占比</td>
				</tr>
				<tr>
					<td b0111="0" >全市</td>
					<td><a href="javascript:void(0);" class="zb4a1" ></a></td>
					<td><a href="javascript:void(0);" class="zb4a2" ></a></td>
					<td><a href="javascript:void(0);" class="zb4a11" ></a></td>
					<td><a href="javascript:void(0);" class="zb4a3" ></a></td>
					<td><a href="javascript:void(0);" class="zb4a4" ></a></td>
					<td><a href="javascript:void(0);" class="zb4a5" ></a></td>
					<td><a href="javascript:void(0);" class="zb4a6" ></a></td>
					<td><a href="javascript:void(0);" class="zb4a7" ></a></td>
					<td><a href="javascript:void(0);" class="zb4a8" ></a></td>
					<td><a href="javascript:void(0);" class="zb4a9" ></a></td>
					<td><a href="javascript:void(0);" class="zb4a10" ></a></td>
				</tr>
				
			</table>
			
		</div>
		
		
		<p class="subcontent" style="text-align:center;">
			5.乡镇领导班子有关指标（指标6、7、8、9）
		</p>
		<div style="display: table; text-align: center;width: 100% " >
	
			<table class="coordTable tablezb5" cellspacing="0" style="margin:auto"  >

				<tr>
					<td class="tableTile" rowspan="2" style="width: 10%;">单位</td>
					<td class="tableTile" colspan="2" >乡镇（街道）领导班子成员人数</td>
					<td class="tableTile" colspan="2" >乡镇（街道）领导班子中35岁以下干部</td>
					<td class="tableTile" colspan="4" >乡镇（街道）党政正职中35岁以下干部</td>
					<td class="tableTile" rowspan="2" style="width: 10%;">乡镇（街道）领导班子中30岁以下干部人数</td>
				</tr>
				<tr>
					<td class="tableTile" style="width: 10%;">总数</td>
					<td class="tableTile" style="width: 10%;">党政正职</td>
					<td class="tableTile" style="width: 10%;">总数</td>
					<td class="tableTile" style="width: 10%;">占比</td>
					<td class="tableTile" style="width: 10%;">总数</td>
					<td class="tableTile" style="width: 10%;">占比</td>
					<td class="tableTile" style="width: 10%;">其中书记占比</td>
					<td class="tableTile" style="width: 10%;">其中行政正职占比</td>
				</tr>
				
				
			</table>
			
		</div>
		
		
		<p class="subcontent" style="text-align:center;">
			6.街道领导班子有关指标（指标6、7、8、9）
		</p>
		<div style="display: table; text-align: center;width: 100% " >
	
			<table class="coordTable tablezb6" cellspacing="0" style="margin:auto"  >

				<tr>
					<td class="tableTile" rowspan="2" style="width: 10%;">单位</td>
					<td class="tableTile" colspan="2" >乡镇（街道）领导班子成员人数</td>
					<td class="tableTile" colspan="2" >乡镇（街道）领导班子中35岁以下干部</td>
					<td class="tableTile" colspan="4" >乡镇（街道）党政正职中35岁以下干部</td>
					<td class="tableTile" rowspan="2" style="width: 10%;">乡镇（街道）领导班子中30岁以下干部人数</td>
				</tr>
				<tr>
					<td class="tableTile" style="width: 10%;">总数</td>
					<td class="tableTile" style="width: 10%;">党政正职</td>
					<td class="tableTile" style="width: 10%;">总数</td>
					<td class="tableTile" style="width: 10%;">占比</td>
					<td class="tableTile" style="width: 10%;">总数</td>
					<td class="tableTile" style="width: 10%;">占比</td>
					<td class="tableTile" style="width: 10%;">其中书记占比</td>
					<td class="tableTile" style="width: 10%;">其中行政正职占比</td>
				</tr>
				
				
			</table>
			
		</div>
		
		
		<p class="subcontent" style="text-align:center;">
			7.乡镇（街道）领导班子有关指标（指标6、7、8、9）
		</p>
		<div style="display: table; text-align: center;width: 100% " >
	
			<table class="coordTable tablezb7" cellspacing="0" style="margin:auto"  >

				<tr>
					<td class="tableTile" rowspan="2" style="width: 10%;">单位</td>
					<td class="tableTile" colspan="2" >乡镇（街道）领导班子成员人数</td>
					<td class="tableTile" colspan="2" >乡镇（街道）领导班子中35岁以下干部</td>
					<td class="tableTile" colspan="4" >乡镇（街道）党政正职中35岁以下干部</td>
					<td class="tableTile" rowspan="2" style="width: 10%;">乡镇（街道）领导班子中30岁以下干部人数</td>
				</tr>
				<tr>
					<td class="tableTile" style="width: 10%;">总数</td>
					<td class="tableTile" style="width: 10%;">党政正职</td>
					<td class="tableTile" style="width: 10%;">总数</td>
					<td class="tableTile" style="width: 10%;">占比</td>
					<td class="tableTile" style="width: 10%;">总数</td>
					<td class="tableTile" style="width: 10%;">占比</td>
					<td class="tableTile" style="width: 10%;">其中书记占比</td>
					<td class="tableTile" style="width: 10%;">其中行政正职占比</td>
				</tr>
				
				
			</table>
			
		</div>
		
		<p class="subcontent" style="text-align:center;">
			8.结构性干部（女干部、党外干部）有关指标（指标17、18、19、20）
		</p>
		<div style="display: table; text-align: center;width: 100% " >
	
			<table class="coordTable tablezb8" cellspacing="0" style="margin:auto"  >

				<tr>
					<td class="tableTile" rowspan="2" style="width: 10%;" >单位</td>
					<td class="tableTile" colspan="3" >区县党政班子中女干部人数</td>
					<td class="tableTile" rowspan="2" style="width: 10%;" >市级党政工作部门数量</td>
					<td class="tableTile" colspan="2" >市级党政工作部门配备女干部的领导班子</td>
					<td class="tableTile" colspan="3">区县级政协领导班子中党外干部</td>
				</tr>
				<tr>
					<td class="tableTile" style="width: 10%;">总数</td>
					<td class="tableTile" style="width: 10%;">党委</td>
					<td class="tableTile" style="width: 10%;">政府</td>
					<td class="tableTile" style="width: 10%;">总数</td>
					<td class="tableTile" style="width: 10%;">占比</td>
					<td class="tableTile" style="width: 10%;">总数</td>
					<td class="tableTile" style="width: 10%;">人数</td>
					<td class="tableTile" style="width: 10%;">占比</td>
				</tr>
				<tr>
					<td b0111='0'>全市</td>
					<td><a href="javascript:void(0);" class="zb8a1" ></a></td>
					<td><a href="javascript:void(0);" class="zb8a2" ></a></td>
					<td><a href="javascript:void(0);" class="zb8a3" ></a></td>
					<td><a href="javascript:void(0);" class="zb8a4" ></a></td>
					<td><a href="javascript:void(0);" class="zb8a5" ></a></td>
					<td><a href="javascript:void(0);" class="zb8a6" ></a></td>
					<td>-</td>
					<td>-</td>
					<td>-</td>
				</tr>
				
			</table>
			
		</div>
		
		<p class="subcontent" style="text-align:center;">
			9.干部日常管理有关指标（指标10、12、13、21、22、27、28）
		</p>
		<div style="display: table; text-align: center;width: 100% " >
	
			<table class="coordTable tablezb9" cellspacing="0" style="margin:auto"  >

				<tr>
					<td class="tableTile" rowspan="2" style="width: 6%;">单位</td>
					<td class="tableTile" colspan="2" >新提任40岁以下市管干部全日制大学以上学历</td>
					<td class="tableTile" colspan="2" >新提任40岁以下市直单位处级领导干部全日制大学以上学历</td>
					<td class="tableTile" colspan="2" >新提任40岁以下区县（市）管领导干部全日制大学以上学历</td>
					<td class="tableTile" colspan="2" >本级管理的领导干部全日制大学以上学历</td>
					<td class="tableTile" rowspan="2" style="width: 6%;">招录选调生数量</td>
					<td class="tableTile" colspan="4">本级管理领导班子</td>
					<td class="tableTile" rowspan="2" style="width: 6%;">党委管理的干部人事档案数字化率</td>
					<td class="tableTile" rowspan="2" style="width: 6%;">选人用人综合应用省市县贯通率</td>
				</tr>
				<tr>
					<td class="tableTile" style="width: 6%;">人数</td>
					<td class="tableTile" style="width: 6%;">本级占比</td>
					<td class="tableTile" style="width: 6%;">人数</td>
					<td class="tableTile" style="width: 6%;">本级占比</td>
					<td class="tableTile" style="width: 6%;">人数</td>
					<td class="tableTile" style="width: 6%;">本级占比</td>
					<td class="tableTile" style="width: 6%;">人数</td>
					<td class="tableTile" style="width: 6%;">本级占比</td>
					<td class="tableTile" style="width: 6%;">总职数</td>
					<td class="tableTile" style="width: 6%;">空缺职数</td>
					<td class="tableTile" style="width: 8%;">空缺占比</td>
					<td class="tableTile" style="width: 8%;">缺配三个月以上关键岗位领导干部数</td>
				</tr>
				<tr b0111='0'>
					<td>全市</td>
					<td><a href="javascript:void(0);" class="zb9r1a1" ></a></td>
					<td><a href="javascript:void(0);" class="zb9r1a2" ></a></td>
					<td><a href="javascript:void(0);" class="zb9r1a3" ></a></td>
					<td><a href="javascript:void(0);" class="zb9r1a4" ></a></td>
					<td><a href="javascript:void(0);" class="zb9r1a5" ></a></td>
					<td><a href="javascript:void(0);" class="zb9r1a6" ></a></td>
					<td><a href="javascript:void(0);" class="zb9r1a7" ></a></td>
					<td><a href="javascript:void(0);" class="zb9r1a8" ></a></td>
					<td><a href="javascript:void(0);" class="zb9r1a9" ></a></td>
					<td><a href="javascript:void(0);" class="zb9r1a10" ></a></td>
					<td><a href="javascript:void(0);" class="zb9r1a11" ></a></td>
					<td><a href="javascript:void(0);" class="zb9r1a12" ></a></td>
					<td><a href="javascript:void(0);" class="zb9r1a13" ></a></td>
					<td><a href="javascript:void(0);" class="zb9r1a14" ></a></td>
					<td><a href="javascript:void(0);" class="zb9r1a15" ></a></td>
				</tr>
				
				
			</table>
			
		</div>
		
		
		<p class="subcontent" style="text-align:center;">
			10.干部监督有关指标（指标23、24、25）
		</p>
		<div style="display: table; text-align: center;width: 100% " >
	
			<table class="coordTable tablezb10" cellspacing="0" style="margin:auto"  >

				<tr>
					<td class="tableTile" style="width: 25%;">单位</td>
					<td class="tableTile" style="width: 25%;">当年被查处的本级管理干部中为近5年新提任的比例</td>
					<td class="tableTile" style="width: 25%;">“一报告两评议”选人用人工作满意度</td>
					<td class="tableTile" style="width: 25%;">个人有关事项核查不一致率</td>
					
				</tr>
				<tr>
					<td>全市</td>
					<td><a href="javascript:void(0);" class="zb10r1a1" ></a></td>
					<td><a href="javascript:void(0);" class="zb10r1a2" ></a></td>
					<td><a href="javascript:void(0);" class="zb10r1a3" ></a></td>
				</tr>
				
				
			</table>
			
		</div>
		
		
	</div>	
		
		
</section>


<script type="text/javascript">
function addZBXX4(datajson){
	//console.log(datajson);
	var html = "";
	var ri = 2;
	$.each(datajson,function(i,row){
		html += "<tr b0111='"+(row['b0111'])+"'>"+
		'<td>'+row['b0101']+'</td>'+
		'<td><a href="javascript:void(0);" class="zb4r'+ri+'a1" >'+row['zs']+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb4r'+ri+'a2" >'+row['max']+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb4r'+ri+'a9" >'+row['jcrz']+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb4r'+ri+'a3" >'+row['a0201eage']+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb4r'+ri+'a4" >'+
		(Math.round((row['a0201eage'])/row['a0201e']*10000)/100.00)+
		'%</a></td>'+
		'<td><a href="javascript:void(0);" class="zb4r'+ri+'a5" >'+(row['age42'])+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb4r'+ri+'a6" >'+(Math.round(row['age42']/row['max']*10000)/100.00)+'%</a></td>'+
		'<td><a href="javascript:void(0);" class="zb4r'+ri+'a7" >'+(row['dzzz'])+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb4r'+ri+'a8" >'+(Math.round(row['dzzz']/row['max']*10000)/100.00)+'%</a></td>'+
		'<td>-</td><td>-</td>'+
		"</tr>";
		ri++;
	});
	$('.tablezb4').append(html);
}

function addZBXX7(datajson,tableclass,b0101,ri,b0111){
	//console.log(datajson);
	var html = "";
	$.each(datajson,function(i,row){
		html += "<tr b0111='"+(row['b0111']||b0111)+"'>"+
		'<td>'+(b0101||row['b0101'])+'</td>'+
		'<td><a href="javascript:void(0);" class="zb7r'+ri+'a1" >'+row['max']+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb7r'+ri+'a2" >'+row['dzzz']+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb7r'+ri+'a3" >'+row['age35']+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb7r'+ri+'a4" >'+(Math.round(row['age35']/row['max']*10000)/100.00)+'%</a></td>'+
		'<td><a href="javascript:void(0);" class="zb7r'+ri+'a5" >'+(row['age35dzzz'])+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb7r'+ri+'a6" >'+(Math.round(row['age35dzzz']/row['dzzz']*10000)/100.00)+'%</a></td>'+
		'<td><a href="javascript:void(0);" class="zb7r'+ri+'a7" >'+(Math.round(row['age35dw']/row['dzzz']*10000)/100.00)+'%</a></td>'+
		'<td><a href="javascript:void(0);" class="zb7r'+ri+'a8" >'+(Math.round(row['age35zf']/row['dzzz']*10000)/100.00)+'%</a></td>'+
		'<td><a href="javascript:void(0);" class="zb7r'+ri+'a9" >'+row['age30']+'</a></td>'+
		"</tr>";
		ri++;
	});
	$(tableclass).append(html);
}

function addZBXX8(datajson,tableclass){
	//console.log(datajson);
	var html = "";
	var ri = 1;
	$.each(datajson,function(i,row){
		html += "<tr b0111='"+(row['b0111'])+"'>"+
		'<td>'+row['b0101']+'</td>'+
		'<td><a href="javascript:void(0);" class="zb8r'+ri+'a1" >'+row['党政女干部人数']+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb8r'+ri+'a2" >'+row['党委女干部人数']+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb8r'+ri+'a3" >'+row['政府女干部人数']+'</a></td>'+
		'<td>-</td>'+
		'<td>-</td>'+
		'<td>-</td>'+
		'<td><a href="javascript:void(0);" class="zb8r'+ri+'a4" >'+row['政协干部']+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb8r'+ri+'a5" >'+(row['政协党外干部'])+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb8r'+ri+'a6" >'+(Math.round(row['政协党外干部']/row['政协干部']*10000)/100.00)+'%</a></td>'+
		"</tr>";
		ri++;
	});
	$(tableclass).append(html);
}

function addZBXX9_szqb(datajson,tableclass,b0101,ri,b0111,async){
	var toclick = b0101?" class='toclick first-show' clsname='"+tableclass.replace('.','')+"' ":"";
	//展开的表格样式
	var trclass = async?"class='dw-rowdata"+b0111+"'":""
	var html = "";
	$.each(datajson,function(i,row){
		row['总职数'] = row['总职数']==undefined?'--':row['总职数'];
		row['市直空缺'] = row['市直空缺']==undefined?'--':row['市直空缺'];
		row['市管新提任40全日制'] = row['市管新提任40全日制']==undefined?'--':row['市管新提任40全日制'];
		row['新40处级全日制'] = row['新40处级全日制']==undefined?'--':row['新40处级全日制'];
		row['市管全日制'] = row['市管全日制']==undefined?'--':row['市管全日制'];
		html += "<tr b0111='"+(row['b0111']||b0111)+"' "+trclass+">"+
		'<td '+toclick+' >'+(b0101||row['b0101'])+'</td>'+
		'<td><a href="javascript:void(0);" class="zb9r'+ri+'a1" >'+row['市管新提任40全日制']+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb9r'+ri+'a2" >'+((row['市管新提任40']==undefined||row['市管新提任40']=='0')?'--':(Math.round(row['市管新提任40全日制']/row['市管新提任40']*10000)/100.00+"%"))+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb9r'+ri+'a3" >'+row['新40处级全日制']+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb9r'+ri+'a4" >'+((row['新提任40处级']==undefined||row['新提任40处级']=='0')?'--':(Math.round(row['新40处级全日制']/row['新提任40处级']*10000)/100.00+"%"))+'</a></td>'+
		'<td>-</td>'+
		'<td>-</td>'+
		'<td><a href="javascript:void(0);" class="zb9r'+ri+'a7" >'+row['市管全日制']+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb9r'+ri+'a8" >'+((row['市管干部']==undefined||row['市管干部']=='0')?'--':Math.round(row['市管全日制']/row['市管干部']*10000)/100.00+"%")+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb9r'+ri+'a9" ></a></td>'+
		'<td><a href="javascript:void(0);" class="zb9r'+ri+'a10" >'+row['总职数']+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb9r'+ri+'a11" >'+row['市直空缺']+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb9r'+ri+'a12" >'+(row['总职数']=='--'||row['总职数']=='0'?'--':(Math.round(row['市直空缺']/row['总职数']*10000)/100.00+"%"))+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb9r'+ri+'a13" ></a></td>'+
		'<td><a href="javascript:void(0);" class="zb9r'+ri+'a14" ></a></td>'+
		'<td><a href="javascript:void(0);" class="zb9r'+ri+'a15" ></a></td>'+
		"</tr>";
		ri++;
	});
	if(async){
		$(html).insertAfter($(tableclass+" tr[b0111="+b0111+"]"));
	}else{
		$(tableclass).append(html);
	}
}



function addZBXX9_qxqb(datajson,tableclass,b0101,ri,b0111,async){
	var toclick = b0101?" class='toclick first-show' clsname='"+tableclass.replace('.','')+"' ":"";
	//展开的表格样式
	var trclass = async?"class='dw-rowdata"+b0111+"'":""
	var html = "";
	$.each(datajson,function(i,row){
		html += "<tr b0111='"+(row['b0111']||b0111)+"' "+trclass+">"+
		'<td '+toclick+' >'+(b0101||row['b0101'])+'</td>'+
		'<td><a href="javascript:void(0);" class="zb9r'+ri+'a1" >'+row['市管新提任40全日制']+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb9r'+ri+'a2" >'+(row['市管新提任40全日制']=='0'?'0':(Math.round(row['市管新提任40全日制']/row['市管新提任40']*10000)/100.00))+'%</a></td>'+
		'<td>-</td>'+
		'<td>-</td>'+
		'<td><a href="javascript:void(0);" class="zb9r'+ri+'a5" >'+(row['区县新40处级全日制']||0)+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb9r'+ri+'a6" >'+((row['区县处级新提任40']==undefined||row['区县处级新提任40']=='0')?'0':(Math.round(row['区县新40处级全日制']/row['区县处级新提任40']*10000)/100.00))+'%</a></td>'+
		
		'<td><a href="javascript:void(0);" class="zb9r'+ri+'a7" >'+row['市管全日制']+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb9r'+ri+'a8" >'+(Math.round(row['市管全日制']/row['市管干部']*10000)/100.00)+'%</a></td>'+
		
		'<td><a href="javascript:void(0);" class="zb9r'+ri+'a9" ></a></td>'+
		'<td><a href="javascript:void(0);" class="zb9r'+ri+'a10" >'+row['总职数']+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb9r'+ri+'a11" >'+row['区县空缺']+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb9r'+ri+'a12" >'+(row['总职数']=='0'?'0':(Math.round(row['区县空缺']/row['总职数']*10000)/100.00))+'%</a></td>'+
		'<td><a href="javascript:void(0);" class="zb9r'+ri+'a13" ></a></td>'+
		'<td><a href="javascript:void(0);" class="zb9r'+ri+'a14" ></a></td>'+
		'<td><a href="javascript:void(0);" class="zb9r'+ri+'a15" ></a></td>'+
		"</tr>";
		ri++;
	});
	if(async){
		$(html).insertAfter($(tableclass+" tr[b0111="+b0111+"]"));
	}else{
		$(tableclass).append(html);
	}
}

//(row['区县处级新提任40']==undefined||row['区县处级新提任40']=='0')?'0':
function addZBXX2(datajson,tableclass,b0101,ri,b0111,async){
	var toclick = b0101?" class='toclick first-show' clsname='"+tableclass.replace('.','')+"' ":"";
	//展开的表格样式
	var trclass = async?"class='dw-rowdata"+b0111+"'":""
	var html = "";
	$.each(datajson,function(i,row){
		html += "<tr b0111='"+(row['b0111']||b0111)+"' "+trclass+">"+
		'<td '+toclick+' >'+(b0101||row['b0101'])+'</td>'+
		'<td><a href="javascript:void(0);" class="zb2r'+ri+'a1" >'+(row['总职数']||'--')+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb2r'+ri+'a2" >'+(row['市直市管干部']||'--')+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb2r'+ri+'a3" >'+(row['市直市管干部45']||'--')+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb2r'+ri+'a4" >'+((row['市直市管干部']==undefined||row['市直市管干部']=='0')?'--':Math.round(row['市直市管干部45']/row['市直市管干部']*10000)/100.00+'%')+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb2r'+ri+'a5" >'+(row['市直市管干部40']||'--')+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb2r'+ri+'a6" >'+((row['市直市管干部']==undefined||row['市直市管干部']=='0')?'--':Math.round(row['市直市管干部40']/row['市直市管干部']*10000)/100.00+'%')+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb2r'+ri+'a7" >'+(row['处级职数']||'--')+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb2r'+ri+'a8" >'+(row['市直处级干部']||'--')+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb2r'+ri+'a9" >'+(row['市直处级干部40']||'--')+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb2r'+ri+'a10" >'+((row['市直处级干部']==undefined||row['市直处级干部']=='0')?'--':Math.round(row['市直处级干部40']/row['市直处级干部']*10000)/100.00+'%')+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb2r'+ri+'a11" >'+(row['市直处级干部35']||'--')+'</a></td>'+
		'<td><a href="javascript:void(0);" class="zb2r'+ri+'a12" >'+((row['市直处级干部']==undefined||row['市直处级干部']=='0')?'--':Math.round(row['市直处级干部35']/row['市直处级干部']*10000)/100.00+'%')+'</a></td>'+
		"</tr>";
		ri++;
	});
	if(async){
		$(html).insertAfter($(tableclass+" tr[b0111="+b0111+"]"));
	}else{
		$(tableclass).append(html);
	}
	
}


//绑定反查
function addZBXXClick(){
	$('td:nth-child(3),td:nth-child(4),td:nth-child(6),td:nth-child(9),td:nth-child(10),td:nth-child(12)',".tablezb2 tr:gt(1)").unbind('click').bind('click',function(){
    	var p = {maximizable:false,resizable:false};
    	p['colIndex'] = $(this).index();
    	//alert($(this).parent().index())
    	p['b0111'] = $(this).parent().attr('b0111');
    	p['query_type']="市直单位领导干部有关指标（指标2、3）";
    	//alert(p['colIndex'])
    	//alert(p['b0111'])
    	openMate(p);
    });
	
	
	$('td:nth-child(3),td:nth-child(5),td:nth-child(4),td:nth-child(7),td:nth-child(9)',".tablezb4 tr:gt(1)").unbind('click').bind('click',function(){
    	var p = {maximizable:false,resizable:false};
    	p['colIndex'] = $(this).index();
    	//alert($(this).parent().index())
    	p['b0111'] = $(this).parent().attr('b0111');
    	p['query_type']="区、县（市）班子有关指标（指标4、5、16）";
    	//alert(p['colIndex'])
    	//alert(p['b0111'])
    	openMate(p);
    });
	
	
	$('td:nth-child(2),td:nth-child(3),td:nth-child(4),td:nth-child(6),td:nth-child(8),td:nth-child(9),td:nth-child(10)',".tablezb7 tr:gt(1),.tablezb5 tr:gt(1),.tablezb6 tr:gt(1)").unbind('click').bind('click',function(){
    	var p = {maximizable:false,resizable:false};
    	p['colIndex'] = $(this).index();
    	//alert($(this).parent().index())
    	p['b0111'] = $(this).parent().attr('b0111');
    	var table = $(this).parent().parent().parent();
    	if(table.hasClass('tablezb7')){
    		p['query_type']="乡镇（街道）领导班子有关指标（指标6、7、8、9）（乡镇街道）";
    	}else if(table.hasClass('tablezb5')){
    		p['query_type']="乡镇（街道）领导班子有关指标（指标6、7、8、9）（乡镇）";
    	}else if(table.hasClass('tablezb6')){
    		p['query_type']="乡镇（街道）领导班子有关指标（指标6、7、8、9）（街道）";
    	}
    	
    	//alert(p['colIndex'])
    	//alert(p['b0111'])
    	openMate(p);
    });
	
	
	
	$('td:nth-child(2),td:nth-child(3),td:nth-child(4),td:nth-child(9),td:nth-child(8)',".tablezb8 tr:gt(1)").unbind('click').bind('click',function(){
    	var p = {maximizable:false,resizable:false};
    	p['colIndex'] = $(this).index();
    	//alert($(this).parent().index())
    	p['b0111'] = $(this).parent().attr('b0111');
    	p['query_type']="结构性干部（女干部、党外干部）有关指标（指标17、18、19、20）";
    	//alert(p['colIndex'])
    	//alert(p['b0111'])
    	openMate(p);
    });
	
	
	$('td:nth-child(2),td:nth-child(4),td:nth-child(6),td:nth-child(8)',".tablezb9 tr:gt(1)").unbind('click').bind('click',function(){
    	var p = {maximizable:false,resizable:false};
    	p['colIndex'] = $(this).index();
    	//alert($(this).parent().index())
    	p['b0111'] = $(this).parent().attr('b0111');
    	p['query_type']="干部日常管理有关指标（指标10、12、13、21、22、27、28）";
    	//alert(p['colIndex'])
    	//alert(p['b0111'])
    	openMate(p);
    });
}

//单位细项切换
function addZBXXDWClick(){
	$('.toclick').click(function(){
		
		if($(this).hasClass('data-show')){
			$(this).removeClass('data-show');
		}else{
			$(this).addClass('data-show');
		}
	
		//表类别
		var tbtype = $(this).attr('clsname');
		//单位
		var dwtype = $(this).parent().attr('b0111');
		//第一次从后台加载
		if($(this).hasClass('first-show')){
			radow.doEvent('showDWData',tbtype+"@@"+dwtype+"@@"+$("."+tbtype+" tr").length);
			$(this).removeClass('first-show');
		}else{
			if($(this).hasClass('data-show')){
				$('.dw-rowdata'+dwtype,"."+tbtype).show();
			}else{
				$('.dw-rowdata'+dwtype,"."+tbtype).hide();
			}
		}
	});
}


</script>