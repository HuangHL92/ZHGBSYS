<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/pingyin.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/echarts.js" charset="utf-8"></script>

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/basejs/ext/ux/css/LockingGridView.css" />
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/qdstyle.css" />
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/ext/ux/LockingGridView.js"></script>
<style>
	
</style>

<div style="height: 100%;width:100%">
	<div style="height:50px;width:100%;">
         <span style="color: black;font-size:20px;" id="ryfx"></span>
         <button id="bgbtn" title="部管干部" style="float: right;" onclick="changechart('05')">部管干部</button>
         <button id="sgbtn" title="市管干部" style="float: right;" onclick="changechart('03')">市管干部</button>
         <button id="allbtn" title="全部" style="float: right;" onclick="changechart('01')">全部</button>
    </div>
 	<div id="rychart" style="height: 200px;width:49.9%;float: left;">
 	</div>
 	<div id="workchart" style="height: 200px;width:49.9%;float: right;">
 	</div>
 	<div id="agechart" style="height: 200px;width:49.9%;float: left;">
 	</div>
 	<div id="educhart" style="height: 200px;width:49.9%;float: right;">
 	</div>
 	<div id="drop" style="height: 50px;width:100%;">
		<odin:select property="personq" canOutSelectList="false" width="80" onchange="qwe()" ></odin:select> 	
 	</div>	
 	<div id="chart" style="height: 500px;width:100%;">
 	</div> 
</div>

<script type="text/javascript">
Ext.onReady(function(){
	
});
function changechart(a0165){
	radow.doEvent("getA0165Sql",a0165);
}
function rychart(res){
	res=eval('('+res+')');
	document.getElementById("rychart").innerHTML='';
	var chart = echarts.init(document.getElementById('rychart'));
    var option = {
      title : {
          text: '干部人员分布'
      },
      tooltip : {
          trigger: 'axis'
      },
      legend: {
          data:['人数']
      },
      toolbox: {
          show : true,
          feature : {
              mark : {show: true}
          }
      },
      calculable : true,
      xAxis : [
          {
              data : ['市管干部','部管干部','3月内到60周岁','试用期'],
              axisLabel:{
                //rotate:-45,
                fontSize:10
              }
              //nameLocation:'center'
          }
      ],
      yAxis : [
          {
              type : 'value',
              axisLabel:{
                //rotate:-45
              }
          }
      ],
      series : [
          {
              name:'人数',
              type:'bar',
              data:[res[0].num,res[1].num,res[2].num,res[3].num],
              itemStyle : { normal: {
                color: '#327bc1'
                //borderRadius: 5
            }
            },
              markPoint : {
                symbolSize:40,
                  data : [
                      {value : res[0].num, xAxis: 0, yAxis: res[0].num},
                      {value : res[1].num, xAxis: 1, yAxis: res[1].num},
                      {value : res[2].num, xAxis: 2, yAxis: res[2].num},
                      {value : res[3].num, xAxis: 3, yAxis: res[3].num}
                  ]
              }
          }
      ]
  };
    chart.setOption(option);
}
function workchart(res){
	res=eval('('+res+')');
	document.getElementById("workchart").innerHTML='';
	var chart = echarts.init(document.getElementById('workchart'));
    var option = {
      title : {
          text: '职务层次'
      },
      tooltip : {
          trigger: 'axis'
      },
      legend: {
          data:['人数']
      },
      toolbox: {
          show : true,
          feature : {
              mark : {show: true}
          }
      },
      calculable : true,
      xAxis : [
          {
              data : ['厅局级正职','厅局级副职','县处级正职','县处级副职','乡科级正职','乡科级副职'],
              axisLabel:{
                //rotate:-45,
                fontSize:10
              }
              //nameLocation:'center'
          }
      ],
      yAxis : [
          {
              type : 'value',
              axisLabel:{
                //rotate:-45
              }
          }
      ],
      series : [
          {
              name:'人数',
              type:'bar',
              data:[res[0].num,res[1].num,res[2].num,res[3].num,res[4].num,res[5].num],
              itemStyle : { normal: {
                color: '#327bc1'
                //borderRadius: 5
            }
            },
              markPoint : {
                symbolSize:40,
                  data : [
                      {value : res[0].num, xAxis: 0, yAxis: res[0].num},
                      {value : res[1].num, xAxis: 1, yAxis: res[1].num},
                      {value : res[2].num, xAxis: 2, yAxis: res[2].num},
                      {value : res[3].num, xAxis: 3, yAxis: res[3].num},
                      {value : res[4].num, xAxis: 4, yAxis: res[4].num},
                      {value : res[5].num, xAxis: 5, yAxis: res[5].num}
                  ]
              }
          }
      ]
  };
    chart.setOption(option);
}
function agechart(res){
	res=eval('('+res+')');
	document.getElementById("agechart").innerHTML='';
	var chart = echarts.init(document.getElementById('agechart'));
	var option = {
	          title : {
	              text: '年龄结构'
	          },
	          tooltip : {
	              trigger: 'axis'
	          },
	          legend: {
	              data:['人数']
	          },
	          toolbox: {
	              show : true,
	              feature : {
	                  mark : {show: true}
	              }
	          },
	          calculable : true,
	          xAxis : [
	              {
	                  data : ['35岁及以下','36~40岁','41~45岁','46~50岁','51~55岁','56岁及以上'],
	                  axisLabel:{
	                    //rotate:-45,
	                    fontSize:10
	                  }
	              }
	          ],
	          yAxis : [
	              {
	                  type : 'value',
	                  axisLabel:{
	                    //rotate:-45
	                  }
	              }
	          ],
	          
	          series : [
	              {
	                  name:'人数',
	                  type:'bar',
	                  data:[res[0].num,res[1].num,res[2].num,res[3].num,res[4].num,res[5].num],
	                  itemStyle : { normal: {
	                    color: '#327bc1'
	                    //borderRadius: 5
	                }
	                },
	                  markPoint : {
	                    symbolSize:40,
	                      data : [
	                          {value : res[0].num, xAxis: 0, yAxis: res[0].num},
	                          {value : res[1].num, xAxis: 1, yAxis: res[1].num},
	                          {value : res[2].num, xAxis: 2, yAxis: res[2].num},
	                          {value : res[3].num, xAxis: 3, yAxis: res[3].num},
	                          {value : res[4].num, xAxis: 4, yAxis: res[4].num},
	                          {value : res[5].num, xAxis: 5, yAxis: res[5].num}
	                      ]
	                  }
	              }
	          ]
	      };
	        //console.log(res);
	        chart.setOption(option);
}

function educhart(res){
	res=eval('('+res+')');
	document.getElementById("educhart").innerHTML='';
	var chart = echarts.init(document.getElementById('educhart'));
	var option = {
	          title : {
	              text: '最高学历统计'
	          },
	          tooltip : {
	              trigger: 'axis'
	          },
	          legend: {
	              data:['人数']
	          },
	          toolbox: {
	              show : true,
	              feature : {
	                  mark : {show: true}
	              }
	          },
	          calculable : true,
	          xAxis : [
	              {
	                  data : ['研究生及以上','本科','专科','高中及以下'],
	                  axisLabel:{
	                    //rotate:-45,
	                    fontSize:10
	                  }
	              }
	          ],
	          yAxis : [
	              {
	                  type : 'value',
	                  axisLabel:{
	                    //rotate:-45
	                  }
	              }
	          ],
	          series : [
	              {
	                  name:'人数',
	                  type:'bar',
	                  data:[res[0].num,res[1].num,res[2].num,res[3].num],
	                  itemStyle : { normal: {
	                    color: '#327bc1'
	                    //borderRadius: 5
	                }
	                },
	                  markPoint : {
	                    symbolSize:40,
	                      data : [
	                          {value : res[0].num, xAxis: 0, yAxis: res[0].num},
	                          {value : res[1].num, xAxis: 1, yAxis: res[1].num},
	                          {value : res[2].num, xAxis: 2, yAxis: res[2].num},
	                          {value : res[3].num, xAxis: 3, yAxis: res[3].num}
	                      ]
	                  }
	              }
	          ]
	      };
	      chart.setOption(option);
}

function qwe(){
	var peo=document.getElementById('personq').value;
	radow.doEvent("asd",peo);
}


function charta(param1,param2,param3){
	param1=eval('('+param1+')');
	param2=eval('('+param2+')');
	param3=eval('('+param3+')');
		 // 基于准备好的dom，初始化echarts实例
//		try{
			document.getElementById("chart").innerHTML='';
			var myChart = echarts.init(document.getElementById('chart'));
			var xAxisData = param1;
			var data1 = param2;
			var data2 = param3;
			var itemStyle = {
			    normal: {
			    },
			    emphasis: {
			        barBorderWidth: 1,
			        shadowBlur: 10,
			        shadowOffsetX: 0,
			        shadowOffsetY: 0,
			        shadowColor: 'rgba(0,0,0,0.5)'
			    }
			};

			var option = {
			    backgroundColor: '#eee',
			    legend: {
			        data: ['正职领导超/缺配职数', '副职领导超/缺配职数'],
			        align: 'left',
			        left: 40
			    },
			    toolbox: {
			        feature: {
			            magicType: {
			                type: ['tiled','stack']
			            }
			        },
			        right:20
			    },			    
			    tooltip: {},
			    xAxis: {
			        data: xAxisData,
			        name: '法人单位',
			        silent: false,
			        axisLine: {onZero: true},
			        splitLine: {show: false},
			        splitArea: {show: false}
			    },
			    yAxis: {
			        inverse: false,
			        splitArea: {show: false}
			    },
			    grid: {
			        left: 40
			    },

			    series: [
			        {
			            name: '正职领导超/缺配职数',
			            type: 'bar',
			            stack: 'one',
			            itemStyle: itemStyle,
			            data: data1
			        },
			        {
			            name: '副职领导超/缺配职数',
			            type: 'bar',
			            stack: 'one',
			            itemStyle: itemStyle,
			            data: data2
			        }
			    ]
			};
			myChart.setOption(option);
}

</script>



