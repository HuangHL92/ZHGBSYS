<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gbk">
<meta content="width=device-width, initial-scale=1.0, user-scalable=0, minimum-scale=1.0, maximum-scale=1.0" name="viewport">
<meta content="yes" name="apple-mobile-web-app-capable">
<meta content="black" name="apple-mobile-web-app-status-bar-style">
<meta http-equiv="x-ua-compatible" content="IE=EmulateIE8" > 
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/DataAnalysis/css/css.css">
<script src="<%=request.getContextPath()%>/pages/DataAnalysis/js/jquery1.7.2.min.js"></script>
<%-- <script type="text/javascript" src="<%=request.getContextPath()%>/basejs/echarts/echarts.js" charset="UTF-8"></script>--%>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/echarts/echarts.min.js" charset="UTF-8"></script>
<script src="<%=request.getContextPath()%>/pages/DataAnalysis/js/comm.js"></script>
<script src="<%=request.getContextPath()%>/pages/DataAnalysis/js/option.js"></script>
</head>
<script  type="text/javascript">
var g_contextpath = '<%= request.getContextPath() %>';
</script>
<body>
<div id="container" class="container">
	<!-- <div class="nav-bar-hover">
        <div class="arrow"></div>
    </div> -->
    <!-- <div class="nav-bar">
        <div class="nav-bar-top">
            <div style="background: url(images/Group10.png) center center / 35% auto no-repeat;height: 50%;"></div>
        </div>
        <div class="nav-bar-center">
            <ul>
                <li class="gwy action">公务员</li>
                <li class="csqt">参公群团</li>
                <li class="cssy">参公事业</li>
            </ul>
        </div>
    </div> -->
    <div class="content-title">
            <div class="" style="display: table-cell;vertical-align: middle;">  <!-- style="display: table-cell;vertical-align: middle;background:url(../images/top_bg2.png) ;" -->
                <p style="margin-left: 20px;float: left">整体情况宏观分析</p>
                <!-- <ul style="margin-right: 20px;float: right">
                    <li class="action">全国</li>
                    <li>中央单位</li>
                    <li>地方</li>
                </ul> -->
            </div>
        </div>
	<div class="content-total">
          <div class="content-total-number">
              <div style="HEIGHT: 100%; WIDTH: 35%; BACKGROUND: #fff; FLOAT: left; DISPLAY: block; align-items: center">
              <div class="content-total-number-bc" style=""></div>
              <p style="MARGIN-TOP: 15%; MARGIN-LEFT: 55%; LINE-HEIGHT: 1.2">人员总数</p>
          </div>
          <div style="HEIGHT: 100%; WIDTH: 65%; BACKGROUND: #fff; FLOAT: right; align-items: center">
              <ul></ul><!--comm.js（setPeopleNum）-->
          </div>
      </div>
      <div class="content-total-detail">
          <ul>
              <!-- <li class="nx" id="nx">女性<span>28</span>万人，占<span>28%</span></li>
              <li class="ssmz" id="ssmz">少数名族<span>28</span>万人，占<span>28%</span></li>
              <li class="fdy" id="fdy">非党员<span>28</span>万人，占<span>28%</span></li>
              <li class="bk" id="bk">本科以上学历<span>28</span>万人，占<span>28%</span></li>
              <li class="age30" id="age30">30岁以下<span>28</span>万人，占<span>28%</span></li>
              <li class="age35" id="age35">35岁以下<span>28</span>万人，占<span>28%</span></li>
              <li class="cj" id="cj">处级以上<span>28</span>万人，占<span>28%</span></li> -->
              <li class="nx" id="nx"></li>
              <li class="ssmz" id="ssmz"></li>
              <li class="fdy" id="fdy"></li>
              <li class="bk" id="bk"></li>
              <li class="age30" id="age30"></li>
              <li class="age35" id="age35"></li>
              <li class="cj" id="cj"></li>
          </ul>
      </div>
  </div>
  <div class="content-chart">
      <!-- <div class="chart-box" style="height: 52%;width: 35%;margin: 0 1% 1% 0;">
          <div class="chart-title">全国公务员分布</div>
          <div class="chart" id="chart1-1"></div>
      </div> -->
      <div class="chart-box" style="height: 52%;width: 100%;margin: 0 0 1% 0;">
          <div class="chart-title">各职务层次年龄总数及占比</div>
          <!-- <ul class="chart-box-tab" style="">
              <li class="action">综合</li>
              <li>警察</li>
              <li>法检</li>
          </ul> -->
          <div class="chart" id="chart1-2"></div>
      </div>
      <div class="chart-box" style="height: 43%;width: 39%;margin: 0 1% 0 0;">
          <div class="chart-title">学历情况</div>
          <div class="chart" id="chart2-1"></div>
      </div>
      <div class="chart-box" style="height: 43%;width: 60%;margin: 0;">
          <div class="chart-title">年龄情况分析</div>
          <div class="chart" id="chart2-2"></div>
      </div>
  </div>
</div>
</body>

<script type="text/javascript">
	function autoResize() {
		var clientWidth = document.documentElement.clientWidth || document.body.clientWidth;
		var clientHeight = document.documentElement.clientHeight || document.body.clientHeight;
		var container = document.getElementById("container");
		container.style.width = clientWidth + "px";
		container.style.height = clientHeight + "px";
		
		// 窗口大小改变时，初始化页面布局
		window.onresize = autoResize;
		
		/* $(window).resize(function() {
			autoResize();
		}); */
	}
	/* IE9以下浏览器  初始化页面元素大小 */
	autoResize();
</script>
	
<script type="text/javascript">
//初始化
$(document).ready(function(){
	initx();
	inity();
	initz();
	initm();
	//showBar();
});
function initx(){
	<%-- var path = '<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.DataAnalysis.Entirety&eventNames=querybyid'; --%>
	$.ajax({
        async: true,   
        type: 'post',
        url: "<%=request.getContextPath()%>/SjfxServlet?method=initX",
        //url:path,
        data:{},
        dataType: "text",
        success: function (data){ 
        	//console.log(data);
        	setPeopleNum(data);
        },
        error:function(error,status){
       	 alert("数据错误!");
         }
	});
}
function setPeopleNum(data){
	var datastr = data.split("&");
	var zong = datastr[0];
	var nx = datastr[1];
	var nxb = datastr[2];
	var ssmz = datastr[3];
	var ssmzb = datastr[4];
	var fdy = datastr[5];
	var fdyb = datastr[6];
	var bkxl = datastr[7];
	var bkxlb = datastr[8];
	var nl1 = datastr[9];
	var nl1b = datastr[10];
	var nl2 = datastr[11];
	var nl2b = datastr[12];
	var cjnx = datastr[13];
	var cjnxb = datastr[14];
    var value = zong.toString(10);
    //alert(value);
    for(var i=0;i<value.length;i++){
        $(".content-total-number ul").append('<li>' + value[i] + '</li>')
    }
    $("#nx").html("女性<span>"+nx+"</span>人,占<span>"+nxb+"</span>"); 
    $("#ssmz").html("少数民族<span>"+ssmz+"</span>人,占<span>"+ssmzb+"</span>"); 
    $("#fdy").html("非中共党员<span>"+fdy+"</span>人,占<span>"+fdyb+"</span>"); 
    //$("#bk").html("本科以上<span>"+bkxl+"</span>人,占<span>"+bkxlb+"</span>"); 
    $("#age30").html("30岁以下<span>"+nl1+"</span>人,占<span>"+nl1b+"</span>"); 
    $("#age35").html("35岁以下<span>"+nl2+"</span>人,占<span>"+nl2b+"</span>");
    $("#cj").html("处级以上女性<span>"+cjnx+"</span>人,占<span>"+cjnxb+"</span>");   
}

function inity(){
	$.ajax({
        async: true,   
        type: 'post',
        url: "<%=request.getContextPath()%>/SjfxServlet?method=initY",
        //url:path,
        data:{},
        dataType: "text",
        success: function (data){ 
        	//console.log(data);
        	setNumY(data);
        },
        error:function(error,status){
       	 alert("数据错误!");
         }
	});
}

function setNumY(data){
	var datastr = data.split("&");
	var zsb = parseInt(datastr[0]);
	var fsb = parseInt(datastr[1]);
	var ztb = parseInt(datastr[2]);
	var ftb = parseInt(datastr[3]);
	var zcb = parseInt(datastr[4]);
	var fcb = parseInt(datastr[5]);
	var zkb = parseInt(datastr[6]);
	var fkb = parseInt(datastr[7]);
	var kyb = parseInt(datastr[8]);
	var bsyb = parseInt(datastr[9]);
	var syqb = parseInt(datastr[10]);
	var qtb = parseInt(datastr[11]);
	
	var zs2b = parseInt(datastr[12]);
	var fs2b = parseInt(datastr[13]);
	var zt2b = parseInt(datastr[14]);
	var ft2b = parseInt(datastr[15]);
	var zc2b = parseInt(datastr[16]);
	var fc2b = parseInt(datastr[17]);
	var zk2b = parseInt(datastr[18]);
	var fk2b = parseInt(datastr[19]);
	var ky2b = parseInt(datastr[20]);
	var bsy2b = parseInt(datastr[21]);
	var syq2b = parseInt(datastr[22]);
	var qt2b = parseInt(datastr[23]);
	
	var zs3b = parseInt(datastr[24]);
	var fs3b = parseInt(datastr[25]);
	var zt3b = parseInt(datastr[26]);
	var ft3b = parseInt(datastr[27]);
	var zc3b = parseInt(datastr[28]);
	var fc3b = parseInt(datastr[29]);
	var zk3b = parseInt(datastr[30]);
	var fk3b = parseInt(datastr[31]);
	var ky3b = parseInt(datastr[32]);
	var bsy3b = parseInt(datastr[33]);
	var syq3b = parseInt(datastr[34]);
	var qt3b = parseInt(datastr[35]);
	
	var zs4b = parseInt(datastr[36]);
	var fs4b = parseInt(datastr[37]);
	var zt4b = parseInt(datastr[38]);
	var ft4b = parseInt(datastr[39]);
	var zc4b = parseInt(datastr[40]);
	var fc4b = parseInt(datastr[41]);
	var zk4b = parseInt(datastr[42]);
	var fk4b = parseInt(datastr[43]);
	var ky4b = parseInt(datastr[44]);
	var bsy4b = parseInt(datastr[45]);
	var syq4b = parseInt(datastr[46]);
	var qt4b = parseInt(datastr[47]);
	
	var zs5b = parseInt(datastr[48]);
	var fs5b = parseInt(datastr[49]);
	var zt5b = parseInt(datastr[50]);
	var ft5b = parseInt(datastr[51]);
	var zc5b = parseInt(datastr[52]);
	var fc5b = parseInt(datastr[53]);
	var zk5b = parseInt(datastr[54]);
	var fk5b = parseInt(datastr[55]);
	var ky5b = parseInt(datastr[56]);
	var bsy5b = parseInt(datastr[57]);
	var syq5b = parseInt(datastr[58]);
	var qt5b = parseInt(datastr[59]);

    var option = {
            color : ["#ffa995","#fff797","#a6ffd2","#a3c1ff","#ffd898"],
            tooltip : {
                trigger: 'axis',
                axisPointer : {
                    type : 'shadow'
                },
                formatter:function(parmas){
                    var str = parmas[0].axisValue + "<br>";
                    for(var i=0;i<parmas.length;i++){
                        if(parmas[i]){
                            var val = parmas[i];
                            str += '<span style="display:inline-block;margin-right:5px;border-radius:10px;width:9px;height:9px;background-color:'+ val.color +';"></span>' + val.seriesName + ': ' + val.data + '%<br>';
                        }
                    }
                    return str;
                }
            },
            legend: {
                data: ['30岁及以下', '30-40岁','40-50岁','50-60岁','60岁以上'],
                right:"4%",
                top:"13%",
                width:"100%",
                icon:"circle"
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                top:"25%",
                containLabel: true
            },
            yAxis:  {
                type: 'value',
                axisLabel:{
                    interval:0,
                    formatter: '{value}%'},
                axisLine:{show:false},
                axisTick:{show:false},
            },
            xAxis: {
                type: 'category',
                axisTick:{show:false},
                data : ['省部级正职','省部级副职','厅局级正职','厅局级副职','县处级正职','县处级副职','乡科级正职','乡科级副职','科员','办事员','试用期人员','其他']
            },
            series : [
	        {
	            name:'30岁及以下',
	            type:'bar',
	            barWidth : 40,
	            stack: '离任',
	            label: {
	                normal: {
	                   show: false
	                }
	            },
	            data:[zsb,fsb,ztb,ftb,zcb,fcb,zkb,fkb,kyb,bsyb,syqb,qtb]
	        },
	        {
	            name:'30-40岁',
	            type:'bar',
	            barWidth : 40,
	            stack: '离任',
	            label: {
	                normal: {
	                   show: false
	                }
	            },
	            data:[zs2b,fs2b,zt2b,ft2b,zc2b,fc2b,zk2b,fk2b,ky2b,bsy2b,syq2b,qt2b]
	        },
	        {
	            name:'40-50岁',
	            type:'bar',
	            barWidth : 40,
	            stack: '离任',
	            data:[zs3b,fs3b,zt3b,ft3b,zc3b,fc3b,zk3b,fk3b,ky3b,bsy3b,syq3b,qt3b]
	        },
	        {
	            name:'50-60岁',
	            type:'bar',
	            barWidth : 40,
	            stack: '离任',
	            data:[zs4b,fs4b,zt4b,ft4b,zc4b,fc4b,zk4b,fk4b,ky4b,bsy4b,syq4b,qt4b]
	        },
	        {
	            name:'60岁以上',
	            type:'bar',
	            barWidth : 40,
	            stack: '离任',
	            data:[zs5b,fs5b,zt5b,ft5b,zc5b,fc5b,zk5b,fk5b,ky5b,bsy5b,syq5b,qt5b]
	        }
	    ]
        };
        getChart("chart1-2").setOption(option);
} 

function initz(){
	//setNumZ();
	$.ajax({
        async: true,   
        type: 'post',
        url: "<%=request.getContextPath()%>/SjfxServlet?method=initZ",
        //url:path,
        data:{},
        dataType: "text",
        success: function (data){ 
        	//console.log(data);
        	setNumZ(data);
        },
        error:function(error,status){
       	 alert("数据错误!");
         }
	})
}
function setNumZ(data) {
	var datastr = data.split("&");
	var yjs = datastr[0];
	var yjs = datastr[0];
	var dxbk = datastr[1];
	var dxzk = datastr[2];
	var zzyx = datastr[3];
	var bkxl = datastr[4];
	var bkxlb = datastr[5];
	
	$("#bk").html("本科以上<span>"+bkxl+"</span>人,占<span>"+bkxlb+"</span>"); 
	option = {
		    tooltip : {
		        trigger: 'item',
		        formatter: "{a} <br/>{b} : {c} ({d}%)"
		    },
		    color:['#FFA995', '#FED59F','#A6FFD2','#A3C1FF'],
		    series : [
		        {
		            name: '学历情况',
		            type: 'pie',
		            radius : '70%',
		            center: ['50%', '60%'],
		            data:[
		                {value:yjs, name:'研究生'},
		                {value:dxbk, name:'大学本科'},
		                {value:dxzk, name:'大学专科'},
		                {value:zzyx, name:'中专及以下'}
		            ],
		            itemStyle: {
		                emphasis: {
		                    shadowBlur: 10,
		                    shadowOffsetX: 0,
		                    shadowColor: 'rgba(0, 0, 0, 0.5)'
		                }
		            }
		        }
		    ]
		}
	getChart("chart2-1").setOption(option);
}

function initm(){
	//setNumM();
	$.ajax({
        async: true,   
        type: 'post',
        url: "<%=request.getContextPath()%>/SjfxServlet?method=initM",
        //url:path,
        data:{},
        dataType: "text",
        success: function (data1){ 
        	//console.log(data1);
        	setNumM(data1);
        },
        error:function(error,status){
       	 alert("数据错误!");
         }
	})
}

function setNumM(data1) {
	var data1str = data1.split("&");
	var nl1 = data1str[0];
	var nl2 = data1str[1];
	var nl3 = data1str[2];
	var nl4 = data1str[3];
	var nl5 = data1str[4];
	var nl6 = data1str[5];
	var nl7 = data1str[6];
	var nl8 = data1str[7];
	var nl9 = data1str[8];
	var nl10 = data1str[9];
	var nl11 = data1str[10];

    var data = data || [
        {name:"20-25岁",value:nl2},
        {name:"25-30岁",value:nl3},
        {name:"30-35岁",value:nl4},
        {name:"35-40岁",value:nl5},
        {name:"40-45岁",value:nl6},
        {name:"45-50岁",value:nl7},
        {name:"50-55岁",value:nl8},
        {name:"55-60岁",value:nl9},
        {name:"60-65岁",value:nl10},
        {name:"66岁及以上",value:nl11}
    ];
    var option = {
        color:["#fec36a"],
        tooltip:{show:true},
        grid: {
            left: '3%',
            right: '4%',
            bottom: '3%',
            top:"25%",
            containLabel: true
        },
        yAxis:  {
            type: 'value',
            axisLabel:{
                interval:0,
                formatter: '{value}%'},
            axisLine:{show:false},
            axisTick:{show:false},
        },
        xAxis: {
            type: 'category',
            axisTick:{show:false},
            data: getArrValue(data,"name"),
        },
        series: [{
            name: '年龄情况',
            type: 'line',
            symbolSize:8,
            symbol:"circle",
            label: {
                normal: {
                    show: false
                }
            },
            areaStyle:{
                normal:{
                    shadowColor: 'rgba(0, 0, 0, 0)',
                    shadowBlur: 10,
                    shadowOffsetX: 10
                }
            },
            data: getArrValue(data)
        }]
    };
    getChart("chart2-2").setOption(option);
}
</script>
</html>