<%@page import="com.insigma.odin.framework.util.GlobalNames"%>
<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@include file="/comOpenWinInit2.jsp" %>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/echarts/echartsn.js" charset="UTF-8"></script>

<script type="text/javascript" src="<%=request.getContextPath()%>/pages/mntpsj/resourse/ctsOptions.js" charset="UTF-8"></script>

<link rel="stylesheet" type="text/css" href="mainPage/css/bootstrap-combined.min.css"> 
<style>
body{
width: 100%!important;
}
.hero-unit{
	padding: 5px 30px;
    margin: 0px;
    font-family: 宋体;
    text-align: center;
}
.gwmane,blockquote,.gwdesc{
	display: inline-block;
}
.gwdesc{
	width: 900px;
	margin: 0px 0px 0px 0px;
	padding: 0px;
}
.contentList{
	height: 100%;
	overflow: auto;
}




.gbtj-nljg{
	width: 25%;
	height: 250px;
	float: left;
	border: 0px solid #c0d1e3;
	margin: 5px 0% 5px 0px;
}
.gbtj-border{
	-webkit-border-radius:8px;
	-ms-border-radius:8px;
	-o-border-radius:8px;
	-moz-border-radius:8px;
	border-radius:8px;
	/* box-shadow: 2px 2px 15px rgb(200,200,200); */
}
.zgbd-content{
	background:url(main/gbmainimg/zgbddt.png) no-repeat;
	background-size: 101% 100%;
	width: 100%;
	height: 100%;
	position: relative;
}
.gb-title-img-tj{
	height: 25px;
	width: 5px;
	display: block;
	float: left;
	margin-right: 5px;
}

.gb-title-tj{
	line-height: 25px;
	padding-top: 10px;
	padding-left: 20px;
}
.gbtj-xlxw{
	width: 25%;
	height: 250px;
	float: left;
	border: 0px solid #c0d1e3;
	margin: 5px 0% 5px 0%;
}
.chart-xlxw{
	background:url(main/gbmainimg/qrzzz.png) no-repeat;
	background-size: 100% 100%;
	height: 25px;
	width: 108px;
	position: absolute;
	top: 10px;
	right: 10px;
	cursor: pointer;
	user-select:none;-moz-user-select:none;
}
.chart-qrz{
	height: 100%;
	width: 60px;
	float: left;
	text-align: center;
}
.chart-zz{
	height: 100%;
	width: 48px;
	float: left;
	text-align: center;
}
.gbtj-xbfb{
	width: 25%;
	height: 250px;
	float: left;
	border: 0px solid #c0d1e3;
	margin: 5px 0% 5px 0%;
}
.gbtj-zzmm{
	width: 25%;
	height: 250px;
	float: left;
	border: 0px solid #c0d1e3;
	margin: 5px 0px 5px 0%;
}
.gb-title-tj h5{
	float: left;
	color: rgb(107,107,107);
	font-size: 22px;
	margin: 3px 0;
}
.chart-qrz-bg{
	color: white;
	background:url(main/gbmainimg/qrz.png) no-repeat;
	background-size: 100% 100%;
}
.chart-zz-bg{
	color: white;
	background:url(main/gbmainimg/zz.png) no-repeat;
	background-size: 100% 100%;
}
.chart {
    height: 215px;
    width: 100%;
}

.tjbg{
	height:285px;
	/* width: 100%;
	height:215px;
	background-color: #8fc1f5; */
}
</style>
<div class="contentList">
	
	
</div>
<div class="unit-content" style="display: none;">
	<div class="hero-unit">
		<h2 class="gwmane">
			
		</h2>
		<!-- <blockquote>
			<p class="gwdesc">
				
			</p>
		</blockquote> -->
	</div>
</div>

<%@include file="resourse/photoListTemplate.jsp" %>
<div class="tj-tpl" style="display: none;"><div class="tjbg" >
	<div class="gbtj-nljg gbtj-border">
		<div class="zgbd-content">
			<div class="gb-title-tj" >
				<img class="gb-title-img-tj" alt="" src="<%=request.getContextPath()%>/main/gbmainimg/jx-bt.png">
				<h5>年龄结构</h5>
			</div>
			<div style="width: 100%;clear: both;"></div>
			<div class="chart" id="chart1-1"></div>
		</div>
	</div>
	
	
	
	<div class="gbtj-xlxw gbtj-border">
		<div class="zgbd-content">
			<div class="gb-title-tj" >
				<img class="gb-title-img-tj" alt="" src="<%=request.getContextPath()%>/main/gbmainimg/jx-bt.png">
				<h5>学历学位</h5>
			</div>
			<div style="width: 100%;clear: both;"></div>
			<div class="chart" id="chart1-2"></div>
			<div class="chart-xlxw">
				<div class="chart-qrz chart-qrz-bg">全日制</div>
				<div class="chart-zz ">在职</div>
			</div>
		</div>
	</div>
	
	<div class="gbtj-xbfb gbtj-border">
		<div class="zgbd-content">
			<div class="gb-title-tj" >
				<img class="gb-title-img-tj" alt="" src="<%=request.getContextPath()%>/main/gbmainimg/jx-bt.png">
				<h5>性别分布</h5>
			</div>
			<div style="width: 100%;clear: both;"></div>
			<div class="chart" id="chart1-3"></div>
		</div>
	</div>
	
	<div class="gbtj-zzmm gbtj-border">
		<div class="zgbd-content">
			<div class="gb-title-tj" >
				<img class="gb-title-img-tj" alt="" src="<%=request.getContextPath()%>/main/gbmainimg/jx-bt.png">
				<h5>政治面貌</h5>
			</div>
			<div style="width: 100%;clear: both;"></div>
			<div class="chart" id="chart1-4"></div>
		</div>
	</div>

</div></div>
<style>
.gbtj-list{
	padding-top: 2px;
	height: 190px;
}

.a01-content p {
	margin-bottom: 2px;

}
.gb-title {
    top: 19px;
}
.a0215a{
	font-size: 16px;
	color: #000;
	font-weight: 600;
	background: linear-gradient(180deg,#ec3cef,#8b28e4);
	-webkit-background-clip: text;
    -webkit-text-fill-color: transparent;
}

</style>
<odin:hidden property="fabd00"/>
<odin:hidden property="famx00"/>
<odin:hidden property="b0111"/>
<script type="text/javascript">
Ext.onReady(function(){
	document.getElementById("fabd00").value=parentParam.fabd00;
	document.getElementById("famx00").value=parentParam.famx00;
	document.getElementById("b0111").value=parentParam.b0111;
	//alert(parentParam.query_id)
	
}); 

function setTGData(obj,data){
	var dataArray = 
		[
            //{value: data['qrzmybos'], name: '名誉博士',id:'qrzmybos'},
            {value: data['qrzbos']==0?null:data['qrzbos'], name: '研究生',id:'qrzbos',itemStyle:{color: '#1f819c'}},
            {value: data['qrzshuos']==0?null:data['qrzshuos'], name: '大学',id:'qrzshuos',itemStyle:{color: '#28a3c5'}},
            {value: data['qrzxues']==0?null:data['qrzxues'], name: '大专',id:'qrzxues',itemStyle:{color: '#9bd4e4'}},
            {value: data['qrzqt']==0?null:data['qrzqt'], name: '其他',id:'qrzqt',itemStyle:{color: '#d3eff7'}}
        ];
	var dataArray2 = 
		[
            //{value: data['zzmybos'], name: '名誉博士',id:'zzmybos'},
            {value: data['zzbos']==0?null:data['zzbos'], name: '研究生',id:'zzbos',itemStyle:{color: '#1f819c'}},
            {value: data['zzshuos']==0?null:data['zzshuos'], name: '大学',id:'zzshuos',itemStyle:{color: '#28a3c5'}},
            {value: data['zzxues']==0?null:data['zzxues'], name: '大专',id:'zzxues',itemStyle:{color: '#9bd4e4'}},
            {value: data['zzqt']==0?null:data['zzqt'], name: '其他',id:'zzqt',itemStyle:{color: '#d3eff7'}}
        ];
	$('.chart-zz,.chart-qrz',obj).click(function(){
		if($('.chart-zz',obj).hasClass("chart-zz-bg")){
			$('.chart-zz',obj).removeClass('chart-zz-bg');
			$('.chart-qrz',obj).addClass('chart-qrz-bg');
			
			getChart($('#chart1-2',obj)[0]).setOption(OPTIONS.option2(data,dataArray));
		}else{
			$('.chart-zz',obj).addClass('chart-zz-bg');
			$('.chart-qrz',obj).removeClass('chart-qrz-bg');
			
			getChart($('#chart1-2',obj)[0]).setOption(OPTIONS.option2(data,dataArray2));
		}
	});
	getChart($('#chart1-1',obj)[0]).setOption(OPTIONS.option1(data));
	getChart($('#chart1-2',obj)[0]).setOption(OPTIONS.option2(data,dataArray));
	getChart($('#chart1-3',obj)[0]).setOption(OPTIONS.option3(data));
	var dataArray3 = 
		[
            {value: data['zgdy']==0?null:data['zgdy'], name: '中共党员',id:'zgdy',itemStyle:{color: '#1f819c'}},
            {value: data['fzgdy']==0?null:data['fzgdy'], name: '非中共党员',id:'fzgdy',itemStyle:{color: '#2eb6dc'}}
        ];
	getChart($('#chart1-4',obj)[0]).setOption(OPTIONS.option1(data,dataArray3,'政治面貌'));
}
function getChart($obj) {
    if (!echarts.getInstanceByDom($obj)) {
        return echarts.init($obj);
    } else {
        return echarts.getInstanceByDom($obj);
    }
}

function setGWTJInfo(data){
	var html = "";
	$.each(data,function(i,item){
		html += (i+1)+"、"+item['mxname']+"："+(item['mxdsec']||"")+"&nbsp;&nbsp;";
	});
	//$('.gwdesc').html(html);
}


function callBackData(retData){
	$('.contentList').html('');
	eval(retData.elementsScript)
}
//添加机构名称
function addDWMC(dwmc){
	var unit1 = $('.unit-content');
	var contentList = $('.contentList');
	var templateStr = unit1.html();
	var template = $(templateStr);
	$('.gwmane',template).text(dwmc)
	contentList.append(template);
}
//添加人员
function addData(a01Data){
	a01Data = a01Data.replace(/\{RN\}/gi,"");
	Photo_List.setA01Content(eval(a01Data))
	
}


Photo_List.setA01Content = function(data){
	var TJData = {};
	TJData['a35']=0;TJData['a3540']=0;TJData['a4145']=0;TJData['a4650']=0;TJData['a5155']=0;TJData['a56']=0;
	TJData['qrzbos']=0;TJData['qrzshuos']=0;TJData['qrzxues']=0;TJData['qrzqt']=0;
	TJData['zzbos']=0;TJData['zzshuos']=0;TJData['zzxues']=0;TJData['zzqt']=0;
	TJData['zgdy']=0;TJData['fzgdy']=0;
	TJData['ml']=0;TJData['fml']=0;
	
	var contentList = $('.contentList');
	

	
	var _this = this._p;
	
	var a01ObjTem = $('.gbtj-content1');
	
	var templateStr = a01ObjTem.html();
	var age = 0;
	$.each(data,function(i,a01){
		if(a01.a0000){
			if(a01.a0141=='01'){
				TJData['zgdy']++;
			}else{
				TJData['fzgdy']++;
			}
			if(a01.a0104=='1'){
				TJData['ml']++;
			}else if(a01.a0104=='2'){
				TJData['fml']++;
			}
			
			//统计
			age = 0;
			if(a01.a0107){
				age = getAgeRender(a01.a0107);
			}
			if(age<=35){
				TJData['a35']++;
			}
			if(age>35&&age<=35){
				TJData['a3540']++;
			}
			if(age>=41&&age<=45){
				TJData['a4145']++;
			}
			if(age>=46&&age<=50){
				TJData['a4650']++;
			}
			if(age>=51&&age<=55){
				TJData['a5155']++;
			}
			if(age>=56){
				TJData['a56']++;
			}
			var zgxl = a01.zgxl||"";
			var qrzxl = a01.qrzxl||"";
			if(zgxl.indexOf('研究生')>=0||zgxl.indexOf('硕士')>=0){
				TJData['zzbos']++;
			}else if(zgxl.indexOf('大学')>=0){
				TJData['zzshuos']++;
			}else if(zgxl.indexOf('大专')>=0){
				TJData['zzxues']++;
			}else{
				TJData['zzqt']++;
			}
			
			if(qrzxl.indexOf('研究生')>=0||qrzxl.indexOf('硕士')>=0){
				TJData['qrzbos']++;
			}else if(qrzxl.indexOf('大学')>=0){
				TJData['qrzshuos']++;
			}else if(qrzxl.indexOf('大专')>=0){
				TJData['qrzxues']++;
			}else{
				TJData['qrzqt']++;
			}
		}
		
		
		var template = $(templateStr);
		$('.a0101',template).html((a01.a0101||"&nbsp;"));
		$('.a0104',template).html(a01.a0000?(_this.getA0104(a01.a0104,a01.a0111a)||"&nbsp;"):"&nbsp;");
		$('.a0107',template).html(_this.getA0107(a01.a0107)||"&nbsp;");
		$('.a0141',template).html(_this.getA0140(a01.a0140,a01.a0141,a01.a0144||"")||"&nbsp;");
		$('.a0134',template).html(_this.getA0134(a01.a0134||"")||"&nbsp;");
		$('.xlxw',template).html(_this.getXLXW(a01.zgxl||"",a01.zgxw||"")||"&nbsp;");
		$('.a0192a',template).html(a01.a0192a?("来源："+a01.a0192a):"&nbsp;");
		
		$('<p title="'+(a01.a0215a||"")+'"><span class="a0215a" >'+(a01.a0215a||"&nbsp;")+'</span></p>').insertBefore($('.pa0101',template));
		//$('<hr style="color: rgb(152 152 152);border:1px solid rgb(152 152 152);width: 120%; margin: -4px 0px 3px -15px;">').insertBefore($('.pa0101',template));
		$('<p><span class="a0196" >'+(a01.a0196?(a01.a0196.trim()==''?"&nbsp;":a01.a0196):"&nbsp;")+'</span></p>').insertBefore($('.diva0192a',template));
		
		
		$('.a0192a',template).attr('title',_this.getA0192a(a01.a0192a||""));
		$('[name=a01a0000]',template).attr('a0000',a01.a0000);//.css('background-color','')
		$('.gb-title-img',template).attr('src',contextPath+'/servlet/DownloadUserHeadImage?a0000='+a01.a0000);
		if(a01.a0000){
			$('p.pa0101',template).bind('click',function(){
				var rmbs = $("#rmbs").val();
				$("#rmbs").val(rmbs+"@"+a01.a0000);
				if(rmbs.indexOf(a01.a0000)>=0) {
					$h.alert("","已经打开了");
					return ;
				}
				var rmbWin=window.open(contextPath+'/rmb/ZHGBrmb.jsp?a0000='+a01.a0000, '_blank', 'height='+(screen.height-30)+', width=1024, top=0, left='+(screen.width/2-512)+', toolbar=no, menubar=no, scrollbars=no, resizable=no, location=no, status=no');
				
				var loop = setInterval(function() {
					if(rmbWin.closed) {
						clearInterval(loop);
						_this.removeRmbs(a01.a0000);
						}
					}, 500);
				/*radow.doEvent('elearningGrid.rowdbclick',a01.a0000);*/
			});
		}else{
			$('p.pa0101',template).removeClass('pa0101');
		}
		
		contentList.append(template);
		
	});
	contentList.append('<div style="width: 100%;clear: both;"></div>');
	myMask.hide();//隐藏
	
	//统计
	a01ObjTem = $('.tj-tpl');
	templateStr = a01ObjTem.html();
	template = $(templateStr);
	contentList.append(template);
	setTGData(template,TJData);
	//设置统计数据
	
	contentList.append('<div style="width: 100%;clear: both;"></div>');
	


}

</script>