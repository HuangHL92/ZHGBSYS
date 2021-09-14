<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@page import="java.net.URLDecoder"%>
<%@page import="com.insigma.siis.local.pagemodel.customquery.PeoplePortraitPageModel"%>
<!-- <script type="text/javascript" src="basejs/helperUtil.js"></script> -->
<script type="text/javascript" src="jslib/echarts/echarts.min.js"></script>
<script type="text/javascript" src="jslib/echarts/themes/macarons.js"></script>
<script type="text/javascript" src="jslib/jquery.min.js"></script>
<script type="text/javascript" src="jslib/jquery-ui.min.js"></script>
<script type="text/javascript" src="jslib/jquery.support.js"></script>
<script type="text/javascript" src="jslib/jquery.json-2.2.js"></script>
<script type="text/javascript" src="jslib/jquery.serializejson.js"></script>
<script type="text/javascript" src="jslib/easyui1.5/jquery.easyui-1.5.1.min.js"></script>
<script type="text/javascript" src="jslib/easyui1.5/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="jslib/easyui1.5/themes/insdep/jquery.insdep-extend.min.js"></script>
<script type="text/javascript" src="jslib/ztree/jquery.ztree.all-3.4.min.js"></script>
<script type="text/javascript" src="jslib/My97DatePicker/WdatePicker.js"></script>	
<script type="text/javascript" src="jslib/system.js"></script>
<script type="text/javascript" src="jslib/system-ex.js"></script>
<script type="text/javascript" src="jslib/utils.js"></script>
<!-- <script type="text/javascript" src="jslib/global.js"></script> -->
<script type="text/javascript" src="jslib/page/archive.js"></script>
<script type="text/javascript" src="jslib/controls/tags.js"></script>
<script type="text/javascript" src="jslib/controls/platpush.js"></script>
<script type="text/javascript" src="jslib/controls/platfav.js"></script>
<script type="text/javascript" src="jslib/controls/unit-custom-tree.js"></script>
<script type="text/javascript" src="jslib/controls/custom_mc_item_picker.js"></script>
<odin:hidden property="a0107StrMin"/><!-- 整体分析时间跨度最小值 -->
<odin:hidden property="a0107StrMax"/><!-- 整体分析时间跨度最大值  -->
<odin:hidden property="legendData"/><!-- 姓名 -->
<odin:hidden property="jsonData"/><!-- 整体分析 - 部分信息 -->
<odin:hidden property="jsonDatamz"/>
<odin:hidden property="jsonDatanl"/>
<odin:hidden property="jsonSeries"/><!-- 成长趋势分析 - 部分信息 -->
<odin:hidden property="zj"/><!-- 职级-->

<%-- <%@include file="/rmb/rmbServer.jsp" %> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<link media="all" type="text/css" href="css/peopleportrait.css" rel="stylesheet" />
		<script src="basejs/jquery/jquery-1.7.2.min.js"> </script>
		
		<script type="text/javascript">
		function init() {
			series = document.getElementById("jsonData").value;
			series = eval(series);
			
			seriesmz = document.getElementById("jsonDatamz").value;
			seriesmz = eval(seriesmz);
			
			seriesnl = document.getElementById("jsonDatanl").value;
			seriesnl = eval(seriesnl);
			
			initnl();
			initxw();
			initxb();
			initmz();
		}
		function initnl() {
			var myChart = echarts.init(document.getElementById("chartnl"), 'macarons');
			 var optionC =option = {
					 title : {
		        	        text: '年龄构成图',	        	       
		        	        x:'center'
		        	    },
					    tooltip: {
					        trigger: 'axis',
					        axisPointer: {
					            /* type: 'cross', */
					            crossStyle: {
					                color: '#999'
					            }
					        }
					    },
					    toolbox: {
					      
					    },
					    legend: {
					        data:[]
					    },
					    xAxis: [
					        {
					            type: 'category',
					            name: '周岁',
					            data: ['—20','20—30','30—40','40—50','50—60','60—'],
					            axisPointer: {
					                type: 'shadow'
					            }
					        }
					    ],
					    yAxis: [
					        {
					            type: 'value',
					             name: '人数',
					             /* min: 0,
					            max: 30,
					            interval: 5,
					            axisLabel: {
					                formatter: '{value}人'
					            } */
					        },
					    ],
					    series: seriesnl
					    	/* [
					        {
					            name:'人数',
					            type:'bar',
					            data:[6, 16, 22, 27, 12]
					        },
					     
					      
					    ] */
					};

					                    
			 myChart.setOption(optionC);
		}
		
		function initxw() {
			var myChart = echarts.init(document.getElementById("chartxw"), 'macarons');
			//alert("成长趋势分析"+myChart);
	        var optionC = option = {
	        	    title : {
	        	        text: '学位构成图',	        	       
	        	        x:'center'
	        	    },
	        	    tooltip : {
	        	        trigger: 'item',
	        	        formatter: "{a} <br/>{b} : {c} ({d}%)"
	        	    },
	        	    legend: {
	        	        orient: 'vertical',
	        	        left: 'left',
	        	        data: ['其他','学士','硕士','博士']
	        	    },
	        	    series : [
	        	        {
	        	            name: '学位比例',
	        	            type: 'pie',
	        	            radius : '55%',
	        	            center: ['50%', '60%'],
	        	            data:[
	        	                {value:100, name:'其他'},
	        	                {value:200, name:'学士'},
	        	                {value:80, name:'硕士'},
	        	                {value:40, name:'博士'},	     
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
	        	};
	       
	        myChart.setOption(optionC);
		}
		
		function initxb() {
			var myChart = echarts.init(document.getElementById("chartxb"), 'macarons');
			//alert("成长趋势分析"+myChart);
	        var optionC = option = {
	        	    title : {
	        	        text: '性别构成图',	        	       
	        	        x:'center'
	        	    },
	        	    tooltip : {
	        	        trigger: 'item',
	        	        formatter: "{a} <br/>{b} : {c} ({d}%)"
	        	    },
	        	    legend: {
	        	        orient: 'vertical',
	        	        left: 'left',
	        	        data: ['男','女']
	        	    },
	        	    series : series
	        	  //  	 [
	        	  //      {
	        	  //          name: '性别比例',
	        	  //          type: 'pie',
	        	  //          radius : '55%',
	        	 //           center: ['50%', '60%'],
	        	 //           data:[
	        	 ////               {value:335, name:'男'},
	        	 //               {value:310, name:'女'},	        	                
	        	 //           ],	        	          
	        	//        }
	        	 //   ] 
	        	};
		       
	        myChart.setOption(optionC);
		}
		
		function initmz() {
			var myChart = echarts.init(document.getElementById("chartmz"), 'macarons');
			//alert("成长趋势分析"+myChart);
	        var optionC = option = {
	        	    title : {
	        	        text: '民族构成图',	        	       
	        	        x:'center'
	        	    },
	        	    tooltip : {
	        	        trigger: 'item',
	        	        formatter: "{a} <br/>{b} : {c} ({d}%)"
	        	    },
	        	    legend: {
	        	        orient: 'vertical',
	        	        left: 'left',
	        	        data: ['汉族','少数民族']
	        	    },
	        	    series : seriesmz
	        	    	/* [
	        	        {
	        	            name: '民族比例',
	        	            type: 'pie',
	        	            radius : '55%',
	        	            center: ['50%', '60%'],
	        	            data:[
	        	                {value:335, name:'汉族'},
	        	                {value:30, name:'少数民族'},	        	                
	        	            ],	        	            
	        	        }
	        	    ] */
	        	};
		       
	        myChart.setOption(optionC);
		}
		
		</script>
	</head>
    <body>

		<div style="float:left;margin-left:12px;">
			<div class="content-wrapper">
				<div style="float:left;margin-left: 9px;width:850px;border: 1px solid #e5e5e5;">
					<div >
						<%-- <h1 style="font-size: 40px;"><%=SV(a01.getA0101())%></h1> --%>
						
						
						<table>
						 <tr>
						 <td>
						<div class="anchor-list">
							<a name="机构综述" class="anchor "></a>
						</div>
						<div class="para-title level-2">
							<h4 class="title-text">机构综述</h4>
						</div>
						<div class="removalText" id="jgzs"></div>
						</td>
						  </tr>
						  
						<!--    <tr>
						 <td>
						<div class="anchor-list">
							<a name="班子成员" class="anchor "></a>
						</div>
						<div class="para-title level-2">
							<h4 class="title-text">班子成员</h4>
						</div>
						<div class="removalText" id="bzcy"></div>
						
						</td>
						  </tr> -->
						  
						   <tr>
						 <td>
						<div class="anchor-list">
							<a name="年龄结构" class="anchor "></a>
						</div>
						<div class="para-title level-2">
							<h4 class="title-text">年龄结构</h4>
						</div>
						<div>
							<div id="chartnl" style="width: 450px; height: 220px; margin: 0 auto;"></div>
						</div>
                           </td>
		                  <td>
						<div class="anchor-list">
							<a name="学位结构" class="anchor "></a>
						</div>
						<div class="para-title level-2">
							<h4 class="title-text">学位结构</h4>
						</div>
						<div>
							<div id="chartxw" style="width: 450px; height: 220px; margin: 0 auto;"></div>
						</div>
		                  </td>
		                  </tr>
		
		              <tr>
						 <td>
						<div class="anchor-list">
							<a name="性别结构" class="anchor "></a>
						</div>
						<div class="para-title level-2">
							<h4 class="title-text">性别结构</h4>
						</div>
						<div>
							<div id="chartxb" style="width: 450px; height: 220px; margin: 0 auto;"></div>
						</div>
		                </td>

                            <td>
						<div class="anchor-list">
							<a name="民族结构" class="anchor"></a>
						</div>
						<div class="para-title level-2">
							<h4 class="title-text">民族结构</h4>
						</div>
						<div>
							<div id="chartmz" style="width: 450px; height: 220px; margin: 0 auto;"></div>
						</div>
						  </td>
						 </tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	 	<div style="float: left;margin-left: 9px;">
			
			<div style="position: relative;padding-top: 29px;width: 150px;">
				
					<!-- <div class="promotion_title">人员标签：</div>
					<div class="promotion_viewport">
						<div>
							<div class="styleing">
								<a href="*">自私</a> <a href="*">无责任心</a> <a href="*">贪图小便宜</a>
							</div>
							<div class="styleing2">
								<a href="*">顾家</a> <a href="*">漂亮</a> <a href="*">乘风</a>
							</div>
						</div>
					</div> -->
					<table>
				   <tr>
					<td>
						<div>
							<a name="班子成员" class="anchor "></a>
						</div>
						<div >
							<h4 class="title-text">班子成员</h4>
						</div>
						<div class="removalText" id="jgzs"></div>
					</td>
					</tr>
					</table>
			</div>
		</div>
    
    
    
    	
	</body>
</html>