<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@include file="/comOpenWinInit.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/echarts.js" charset="utf-8"></script>
<style>
.text {
	/* border-bottom:#666 1px solid; */
	border-left-width:0px;
	border-right-width:0px;
	border-top-width:0px;
	border-bottom-width: 0px;
	text-align:center;
	width:25px;
	overflow-x:visible;
	overflow-y:visible;
}
div {
	font-size: 13px;
}
#d1,#d2,#d3,#d4,#dv1,#dv2,#dv3,#dv4 {
	display: inline;
	position: relative;
	padding: 0px;
}
#div2 {
	display: none;
}
#btn,#btn2 {
	position: relative;
 	left: 25%;
}
#jigou {
	width: 600px;
	text-align: left;
}
input {
	font-weight: bold;
	padding-top:1px
}

</style>
 
<script type="text/javascript">
 	$(function(){
 		$("#input1").click();
 	});
function disabled(){
	var create = document.getElementById("create");
	create.disabled = "true";
}
function change(){
	document.getElementById("div1").style.display='none';
	document.getElementById("div2").style.display='block';
}
function zhuang(){
	document.getElementById("age").style.display='';
	document.getElementById("agebing").style.display='none';
}
function bing(){
	document.getElementById("age").style.display='none';
	document.getElementById("agebing").style.display='';
}
function zhuang2(){
	document.getElementById("age2").style.display='';
	document.getElementById("agebing2").style.display='none';
}
function bing2(){
	document.getElementById("age2").style.display='none';
	document.getElementById("agebing2").style.display='';
}
function jiazai(){
	$("#input2").click();
}

function openDiv(){
	var el = Ext.get(document.body);
	el.mask(odin.msg, odin.msgCls);
	var tId = setInterval(function closeWin(){
		if("1"==$("#dsq").val()){
			el.unmask();
			clearInterval(tId);
		}
	},50);
}

  //设置div1图形数据
  function setOption(option){
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('sex'));
	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);
}
  function setOption2(option2){
		// 基于准备好的dom，初始化echarts实例
		var myChart = echarts.init(document.getElementById('age'));
		// 使用刚指定的配置项和数据显示图表。
		myChart.setOption(option2);
	}
  function setOptionbing(optionbing){
		var myChart = echarts.init(document.getElementById('agebing'));
		myChart.setOption(optionbing);
		}
  function setOption3(option3){
		// 基于准备好的dom，初始化echarts实例
		var myChart = echarts.init(document.getElementById('education'));
		// 使用刚指定的配置项和数据显示图表。
		myChart.setOption(option3);
	}
  function setOption4(option4){
		// 基于准备好的dom，初始化echarts实例
		var myChart = echarts.init(document.getElementById('duty'));
		// 使用刚指定的配置项和数据显示图表。
		myChart.setOption(option4);
	}
  //隐藏div1以后显示的div2数据
  function set(option){
		// 基于准备好的dom，初始化echarts实例
		var myChart = echarts.init(document.getElementById('sex2'));
		// 使用刚指定的配置项和数据显示图表。
		myChart.setOption(option);
	}
  function set2(option2){
		// 基于准备好的dom，初始化echarts实例
		var myChart = echarts.init(document.getElementById('age2'));
		// 使用刚指定的配置项和数据显示图表。
		myChart.setOption(option2);
	}
  function setbing(optionbing){
		var myChart = echarts.init(document.getElementById('agebing2'));
		myChart.setOption(optionbing);
		}
  function set3(option3){
		// 基于准备好的dom，初始化echarts实例
		var myChart = echarts.init(document.getElementById('education2'));
		// 使用刚指定的配置项和数据显示图表。
		myChart.setOption(option3);
	}
  function set4(option4){
		// 基于准备好的dom，初始化echarts实例
		var myChart = echarts.init(document.getElementById('duty2'));
		// 使用刚指定的配置项和数据显示图表。
		myChart.setOption(option4);
	}
</script>

<div id="bar_div"></div>
<odin:toolBar property="btnToolBar" applyTo="bar_div">
	<odin:fill/>
	<odin:buttonForToolBar text="更新统计数据" icon="images/icon/reset.gif" id="create" handler="openDiv" tooltip="生成该机构（含下级）统计数据" isLast="true"></odin:buttonForToolBar>
</odin:toolBar>

<br>

<div style="width: 100%;text-align: center;">
<div style="text-indent: 2em;width: 98%;" align="left"><span style="font-size: 14px">当前机构名称：</span><input id="jigou" type="text" class="text" readonly="readonly"><br>
</div>
<br>
<div style="text-indent: 2em;width: 98%;" align="left">
	共<input id="a1" type="text" class="text" readonly="readonly"/>人，
	女性<input id="a2" type="text" class="text" readonly="readonly"/>人，占<input id="a3" type="text" class="text" style="width: 42px;" readonly="readonly"/>；
	少数民族<input id="a4" type="text" class="text" readonly="readonly"/>人，占<input id="a5" type="text" class="text" style="width: 42px;" readonly="readonly"/>；
	中共党员<input id="a6" type="text" class="text" readonly="readonly"/>人,占<input id="a7" type="text" class="text" style="width: 42px;" readonly="readonly"/>。
</div>
<br>
<div style="text-indent: 2em;width: 98%;" align="left">
	30岁以下<input id="b2" type="text" class="text" readonly="readonly"/>人，占<input id="b3" type="text" class="text" style="width: 42px;" readonly="readonly"/>；
	31岁至35岁<input id="b4" type="text" class="text" readonly="readonly"/>人，占<input id="b5" type="text" class="text" readonly="readonly" style="width: 42px;"/>；
	36岁至40岁<input id="b6" type="text" class="text" readonly="readonly"/>人，占<input id="b7" type="text" class="text" readonly="readonly" style="width: 42px;"/>；
	41岁至45岁<input id="b8" type="text" class="text" readonly="readonly"/>人，占<input id="b9" type="text" class="text" readonly="readonly" style="width: 42px;"/>；
	46岁至50岁<input id="b10" type="text" class="text" readonly="readonly"/>人，占<input id="b11" type="text" class="text" readonly="readonly" style="width: 42px;"/>；
	51岁至54岁<input id="b12" type="text" class="text" readonly="readonly"/>人，占<input id="b13" type="text" class="text" readonly="readonly" style="width: 42px;"/>；
	55岁至59岁<input id="b14" type="text" class="text" readonly="readonly"/>人，占<input id="b15" type="text" class="text" readonly="readonly" style="width: 42px;"/>；
	60岁以上<input id="b16" type="text" class="text" readonly="readonly"/>人，占<input id="b17" type="text" class="text" readonly="readonly" style="width: 42px;"/>。
</div>
<br>

<div style="text-indent: 2em;width: 98%;" align="left">
	研究生<input id="c2" type="text" class="text" readonly="readonly"/>人，占<input id="c3" type="text" class="text" readonly="readonly" style="width: 42px;"/>；
	大学本科<input id="c4" type="text" class="text" readonly="readonly"/>人，占<input id="c5" type="text" class="text" readonly="readonly" style="width: 42px;"/>；
	大学专科<input id="c6" type="text" class="text" readonly="readonly"/>人，占<input id="c7" type="text" class="text" readonly="readonly" style="width: 42px;"/>；
	中专<input id="c8" type="text" class="text" readonly="readonly"/>人，占<input id="c9" type="text" class="text" readonly="readonly" style="width: 42px;"/>；
	高中及以下<input id="c10" type="text" class="text" readonly="readonly"/>人，占<input id="c11" type="text" class="text" readonly="readonly" style="width: 42px;"/>；
	未填写<input id="c12" type="text" class="text" readonly="readonly"/>人，占<input id="c13" type="text" class="text" readonly="readonly" style="width: 42px;"/>。
</div>
<br>
<div style="text-indent: 2em;width: 98%;" align="left">
	省部级副职<input id="d8" type="text" class="text" readonly="readonly"/>人，占<input id="d9" type="text" class="text" readonly="readonly" style="width: 42px;"/>；
	厅局级正职<input id="d10" type="text" class="text" readonly="readonly"/>人，占<input id="d11" type="text" class="text" readonly="readonly" style="width: 42px;"/>；
	厅局级副职<input id="d12" type="text" class="text" readonly="readonly"/>人，占<input id="d13" type="text" class="text" readonly="readonly" style="width: 42px;"/>；
	县处级正职<input id="d14" type="text" class="text" readonly="readonly"/>人，占<input id="d15" type="text" class="text" readonly="readonly" style="width: 42px;"/>；
	县处级副职<input id="d16" type="text" class="text" readonly="readonly"/>人，占<input id="d17" type="text" class="text" readonly="readonly" style="width: 42px;"/>；
	乡科级正职<input id="d20" type="text" class="text" readonly="readonly"/>人，占<input id="d21" type="text" class="text" readonly="readonly" style="width: 42px;"/>；
	乡科级副职<input id="d22" type="text" class="text" readonly="readonly"/>人，占<input id="d23" type="text" class="text" readonly="readonly" style="width: 42px;"/>；
	科员<input id="d24" type="text" class="text" readonly="readonly"/>人，占<input id="d25" type="text" class="text" readonly="readonly" style="width: 42px;"/>；
	办事员<input id="d26" type="text" class="text" readonly="readonly"/>人，占<input id="d27" type="text" class="text" readonly="readonly" style="width: 42px;"/>；
	其他<input id="d18" type="text" class="text" readonly="readonly"/>人，占<input id="d19" type="text" class="text" readonly="readonly" style="width: 42px;"/>。
</div>

</div>



<br><br>

<div id="div1" align="center">

<div id="d1" style="width: 49.9%;">
<div id="sex" style="width: 470px;height:365px;border: 1px solid black;"></div>
<script type="text/javascript">

function setOption(option){
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('sex'));
	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option );
}

</script>
<script type="text/javascript">

function setOption(option){
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('sex'));
	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);
}

</script>
</div>


<div id="d2" style="width: 49.9%;">

<div id="age" style="width: 470px;height:365px;border: 1px solid black;"></div>
<script type="text/javascript">

function setOption2(option2){
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('age'));
	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option2 );
}
	
</script>

<script type="text/javascript">

function setOption2(option2){
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('age'));
	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option2);
}
	
</script>


<div id="agebing" style="width: 470px;height:365px;border: 1px solid black;"></div>

<script type="text/javascript">
	// 基于准备好的dom，初始化echarts实例
function setOptionbing(optionbing){
	var myChart = echarts.init(document.getElementById('agebing'));
	myChart.setOption(optionbing );
	}
	
</script>

<script type="text/javascript">
	// 基于准备好的dom，初始化echarts实例
function setOptionbing(optionbing){
	var myChart = echarts.init(document.getElementById('agebing'));
	myChart.setOption(optionbing);
	}
	
</script>
</div>

<div id="btn"><input id="input1" type="button" value="柱状图" style="border: none;background-color: white;" onclick="zhuang()">|<input type="button" value="饼状图" style="border: none;background-color: white;" onclick="bing()">
</div>

<br><br>

<div id="d3" style="width: 49.9%;">
<div id="education" style="width: 470px;height:365px;border: 1px solid black;"></div>
<script type="text/javascript">

function setOption3(option3){
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('education'));
	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option3 );
}

</script>
<script type="text/javascript">

function setOption3(option3){
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('education'));
	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option3);
}

</script>
</div>

<div id="d4" style="width: 49.9%;">
<div id="duty" style="width: 470px;height:365px;border: 1px solid black;"></div>
<script type="text/javascript">

function setOption4(option4){
	 //alert(option4)
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('duty'));
	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option4 );
}

</script>
<script type="text/javascript">


function setOption4(option4){
	 //alert(option4)
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('duty'));
	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option4);
}

</script>
</div>

</div>

<div id="div2" align="center">

<div id="dv1" style="width: 49.9%;">
<div id="sex2" style="width: 470px;height:365px;border: 1px solid black;"></div>
</div>


<div id="dv2" style="width: 49.9%;">

<div id="age2" style="width: 470px ;height:365px;border: 1px solid black;"></div>

<div id="agebing2" style="width: 470px;height:365px; border: 1px solid black;"></div>
<script type="text/javascript">
function setbing(optionbing){
	var myChart = echarts.init(document.getElementById('agebing2'));
	myChart.setOption(optionbing);
}
	
</script>
<script type="text/javascript">
function setbing(optionbing){
	var myChart = echarts.init(document.getElementById('agebing2'));
	myChart.setOption(optionbing);
	}
	
</script>
</div>

<div id="btn2"><input id="input2" type="button" value="柱状图" style="border: none;background-color: white;" onclick="zhuang2()">|<input type="button" value="饼状图" style="border: none;background-color: white;" onclick="bing2()">
</div>


<br><br>

<div id="dv3" style="width: 49.9%;">

<div id="education2" style="width: 470px;height:365px;border: 1px solid black;"></div>
</div>

<div id="dv4" style="width: 49.9%;">

<div id="duty2" style="width: 470px;height:365px;border: 1px solid black;"></div>
</div>

</div>
<odin:hidden property="biaozhi" value="0"/>
<odin:hidden property="dsq" value="0"/>




