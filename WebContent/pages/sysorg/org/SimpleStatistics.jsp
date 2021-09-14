<%@ page contentType="text/html; charset=GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@include file="/comOpenWinInit.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/echarts.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<style type="text/css">

  table.gridtable {
	font-family: verdana,arial,sans-serif;
	font-size:11px;
	color:#333333;
	border-width: 1px;
	border-color: #666666;
	border-collapse: collapse;
	margin: 10px auto 30px ;

}
table.gridtable th {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #666666;
	background-color: #dedede;
}
table.gridtable td {
	border-width: 1px;
	padding: 8px;
	border-style: solid;
	border-color: #666666;
	
}  
.divc{
	width:960px;
	height:430px;
	margin :10px;
	border-top:1px;
	border-left:1px;
	border-bottom:1px;
	border-right:1px;
	border: solid #E6E6E6;
}
.divs{
	background:  #E6E6E6;
	display:block; 
	width: 958px;
	height:20px;
	margin: 0 auto;
} 
</style>

<odin:hidden property="nametu"/>
<odin:hidden property="valuetu"/>
<odin:hidden property="valuebing"/>
<odin:hidden property="indicator"/>
<odin:hidden property="valueleida"/>
<odin:hidden property="arr1"/>
<odin:hidden property="Str"/>



<div id="bar_div"></div>
<odin:toolBar property="btnToolBar" applyTo="bar_div">
	<odin:textForToolBar text="<h3></h3>"/>
	<odin:separator></odin:separator>
	<odin:fill/>
	<odin:buttonForToolBar text="导出excel" icon="" id="export" handler="exportData"></odin:buttonForToolBar>
	<odin:separator/>
	<odin:buttonForToolBar text="饼图" icon="image/u179.png" handler="cbing" id="cbing" ></odin:buttonForToolBar>
	<odin:separator/>
	<odin:buttonForToolBar text="曲线图" icon="image/u177.png" handler="cquxian" id="cquxian"  ></odin:buttonForToolBar>
	<odin:separator/>
	<odin:buttonForToolBar text="柱状图" icon="image/u175.png" handler="czhuzhuang" id="czhuzhuang"  ></odin:buttonForToolBar>
	<odin:separator/>
	<odin:buttonForToolBar text="雷达图" icon="image/u332.png" handler="cleida" id="cleida" isLast="true" ></odin:buttonForToolBar>
	<%-- <odin:separator/>
	<odin:buttonForToolBar text="下载" icon="" handler="downloadImg" id="download"  isLast="true"></odin:buttonForToolBar> --%>
</odin:toolBar>

<br>

<div>
	<table id="createtable" class="gridtable" align="center" style="text-align:center;font-size: 12px">
	<tr id="row1" style="background:#d4e9fc ;border-width: 1px;padding: 2px;border-style: solid;border-color: #999999;"></tr>
	<tr id="row2" style="background-color: #ffffff;"></tr>	
	</table>
</div>

<div class="divc">
	<div class="divs"></div>
	<div id="bing"  style="width:958px;height:400px;margin: 0 auto;"></div>
	<div id="zhuzhuang"  style="width:958px;height:400px;margin: 0 auto;"></div>
	<div id="quxian"  style="width:958px;height:400px;margin: 0 auto;"></div>
	<div id="leida"  style="width:958px;height:400px;margin: 0 auto;"></div>
</div>
<form id="formid" method = 'post'  action = 'user_login_submit.action'  >
<script type="text/javascript">
var myChart;
function createTyTable(){  //传入表数据

	var data = document.getElementById('arr1').value;
	data =  Ext.decode(data);
	var tr1 = document.getElementById('row1');
	var tr2 = document.getElementById('row2');
	for(var i=0;i<data.length;i++){
		var th1 = tr1.insertCell(-1);
		var txt = data.tname[i].name; 			
		th1.innerText = txt;
		
		var td2 = tr2.insertCell(-1);
		var txt = data.tname[i].number; 			
		td2.innerText = txt;
	}
	
}

function setZhuzhuang(){
	var nametu = document.getElementById('nametu').value;
	var valuetu = document.getElementById('valuetu').value;
	nametu = eval(nametu);
	valuetu = eval(valuetu);
	// 基于准备好的dom，初始化echarts实例
	myChart = echarts.init(document.getElementById('zhuzhuang'));
	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption( { 
		title : { 
		    text: '', 
		    x:'center' 
		}, 
	    color: ['#3398DB'], 
	    tooltip : { 
	        trigger: 'axis', 
	        axisPointer : {           
	            type : 'shadow'      
	        } 
	    },
	    /* toolbox: {
	        // y: 'bottom',
	        feature: {
	            magicType: {
	                type: ['stack', 'tiled']
	            },
	            dataView: {},
	            saveAsImage: {
	                pixelRatio: 1
	            }
	        }
	    }, */
	    grid: { 
	        left: '3%', 
	        right: '4%', 
	        bottom: '3%', 
	        containLabel: true 
	    }, 
	    xAxis : [ 
	        { 
	            type : 'category', 
	            data : nametu, 
	            axisTick: { 
	                alignWithLabel: true 
	            },  
	     	 	  axisLabel:{  
	    			interval: 0 ,
              	margin:2          
	    		  }
	    	 } 
	    ], 
	    grid: { 
	        x: 40, 
	    	  x2: 100,      
	    	  y2: 100 
		}, 
	    yAxis : [ 
	        { 
	           type : 'value' 
	        } 
	    ], 
	    series : [ 
	        { 
	            name:'人数', 
	            type:'bar', 
	            barWidth: '60%', 
	            data: valuetu, 
	            itemStyle: {  
                	 normal: {  
                    	label: {   
                        show: true, 
        				  'position': 'top', 
                        textStyle: {  
                            fontWeight:'bolder',  
                            fontSize : '12',   
                            fontFamily : '微软雅黑'  
                        }   
                      }   
                   }  
          	 }  
	        } 
	       ], 
		animation: false  
		}); 
	//window.onresize = myChart.resize;
	//var imgUrlzhuzhuang = myChart.getDataURL();
}

function setBing(bing){
	var valuebing = document.getElementById('valuebing').value;
	valuebing = eval(valuebing);
	// 基于准备好的dom，初始化echarts实例
	myChart = echarts.init(document.getElementById('bing'));
	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption({ 
	    title : { 
	        text: '', 
	        x:'center' 
	    }, 
	    tooltip : { 
	        trigger: 'item', 
	        formatter: '{a} <br/>{b} : {c} ({d}%)' 
	    },
	    
	    toolbox: {
	        show : true,
	        feature : {
	            
	            saveAsImage : {show: true}
	        }
	    },
	  
	    series : [ 
	        { 
	            name: '人数', 
	            type: 'pie', 
	            radius : '70%', 
	            center: ['50%', '50%'], 
	            data: valuebing, 
	            itemStyle: { 
	                emphasis: { 
	                    shadowBlur: 10, 
	                    shadowOffsetX: 0, 
	                    shadowColor: 'rgba(0, 0, 0, 0.5)' 
	                } 
	            } 
		        } 
	    ], 
	    animation: false 
		});
	//window.onresize = myChart.resize;
	var offcanvas= myChart.getRenderedCanvas({  
	       pixelRatio: 2,  
	       backgroundColor: '#fff'  
	   }); 
	
}

function setQuxian(){
	var nametu = document.getElementById('nametu').value;
	var valuetu = document.getElementById('valuetu').value;
	nametu = eval(nametu);
	valuetu = eval(valuetu);
	// 基于准备好的dom，初始化echarts实例
	myChart = echarts.init(document.getElementById('quxian'));
	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption({ 
	    title: { 
	        text: '' ,
	        x:'center' 
	    }, 
	    tooltip: { 
	        trigger: 'axis' 
	    }, 
	    grid: { 
	        left: '3%', 
	        right: '4%', 
	        bottom: '3%', 
	        containLabel: true 
	    }, 
	    xAxis: { 
	        type: 'category', 
	        boundaryGap: false,
	        data: nametu,
	     	 axisLabel:{  
	    		  interval: 0 ,
                margin:2          
	    	  }
	    }, 
	    grid: { 
	        x: 40, 
	    	  x2: 100,    
	    	  y2: 100 
		}, 
	    yAxis: { 
	        type: 'value' 
	    }, 
	    series: [ 
	        { 
	            name:'人数', 
	            type:'line', 
	            data:valuetu,
	            showAllSymbol:true,
	            itemStyle: {  
                	 normal: {  
                    	label: {   
                        show: true, 
        				  'position': 'top', 
                        textStyle: {  
                            fontWeight:'bolder', 
                            fontSize : '12',   
                            fontFamily : '微软雅黑' 
                        }  
                      }   
                   }  
            	 }
	        } 
	    ], 
		animation: false 
		});
	//window.onresize = myChart.resize;
	//imgUrlquxian = myChart.getDataURL();
}

function setLeida(){
	var name = document.getElementById('indicator').value;
	var valueleida = document.getElementById('valueleida').value;
	name = eval(name);
	valueleida = eval(valueleida);
	// 基于准备好的dom，初始化echarts实例
	myChart = echarts.init(document.getElementById('leida'));
	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption({ 
		title: { 
		  text: '', 
		  x:'center' 
		}, 
		tooltip: {}, 
		radar: { 
		  indicator: name, 
		  nameGap:'10', 
		  radius:'65%' 
		}, 
		series: [{ 
			name: '人数', 
			type: 'radar', 
			data : valueleida 
		}], 
		animation: false 
		}); 
	//window.onresize = myChart.resize;
	//imgUrlleida = myChart.getDataURL();
	
}
function def(){
	document.getElementById("leida").style.display='none';
	document.getElementById("zhuzhuang").style.display='none';
	document.getElementById("quxian").style.display='none';
	document.getElementById("bing").style.display='none';
}

function loding(){
	cbing();
}

function cbing(){
	document.getElementById("leida").style.display='none';
	document.getElementById("zhuzhuang").style.display='none';
	document.getElementById("quxian").style.display='none';
	document.getElementById("bing").style.display='block';
	//imgUrl = imgUrlbing;
	
}
function czhuzhuang(){
	document.getElementById("bing").style.display='none';
	document.getElementById("leida").style.display='none';
	document.getElementById("quxian").style.display='none';
	document.getElementById("zhuzhuang").style.display='block';
	//imgUrl = imgUrlzhuzhuang;
}
function cquxian(){
	document.getElementById("bing").style.display='none';
	document.getElementById("leida").style.display='none';
	document.getElementById("zhuzhuang").style.display='none';
	document.getElementById("quxian").style.display='block';
	//imgUrl = imgUrlquxian;
	
}
function cleida(){
	document.getElementById("bing").style.display='none';
	document.getElementById("zhuzhuang").style.display='none';
	document.getElementById("quxian").style.display='none';
	document.getElementById("leida").style.display='block';
	//imgUrl = imgUrlleida;
}


function downloadImg(){
 	//document.getElementById("dContent").value=imgUrl;
    //document.getElementById("form2").submit();  
}

function exportData(){
	radow.doEvent("export");
}

function autosubmit(param){
	w = window.open("ProblemDownServlet?method=downFiletj&prid="+param); 
}

function cc()
{
	w.close();
}
</script>

