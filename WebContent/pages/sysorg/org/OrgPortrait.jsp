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
<odin:hidden property="a0107StrMin"/><!-- �������ʱ������Сֵ -->
<odin:hidden property="a0107StrMax"/><!-- �������ʱ�������ֵ  -->
<odin:hidden property="legendData"/><!-- ���� -->
<odin:hidden property="jsonData"/><!-- ������� - ������Ϣ -->
<odin:hidden property="jsonDatamz"/>
<odin:hidden property="jsonDatanl"/>
<odin:hidden property="jsonSeries"/><!-- �ɳ����Ʒ��� - ������Ϣ -->
<odin:hidden property="zj"/><!-- ְ��-->

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
		        	        text: '���乹��ͼ',	        	       
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
					            name: '����',
					            data: ['��20','20��30','30��40','40��50','50��60','60��'],
					            axisPointer: {
					                type: 'shadow'
					            }
					        }
					    ],
					    yAxis: [
					        {
					            type: 'value',
					             name: '����',
					             /* min: 0,
					            max: 30,
					            interval: 5,
					            axisLabel: {
					                formatter: '{value}��'
					            } */
					        },
					    ],
					    series: seriesnl
					    	/* [
					        {
					            name:'����',
					            type:'bar',
					            data:[6, 16, 22, 27, 12]
					        },
					     
					      
					    ] */
					};

					                    
			 myChart.setOption(optionC);
		}
		
		function initxw() {
			var myChart = echarts.init(document.getElementById("chartxw"), 'macarons');
			//alert("�ɳ����Ʒ���"+myChart);
	        var optionC = option = {
	        	    title : {
	        	        text: 'ѧλ����ͼ',	        	       
	        	        x:'center'
	        	    },
	        	    tooltip : {
	        	        trigger: 'item',
	        	        formatter: "{a} <br/>{b} : {c} ({d}%)"
	        	    },
	        	    legend: {
	        	        orient: 'vertical',
	        	        left: 'left',
	        	        data: ['����','ѧʿ','˶ʿ','��ʿ']
	        	    },
	        	    series : [
	        	        {
	        	            name: 'ѧλ����',
	        	            type: 'pie',
	        	            radius : '55%',
	        	            center: ['50%', '60%'],
	        	            data:[
	        	                {value:100, name:'����'},
	        	                {value:200, name:'ѧʿ'},
	        	                {value:80, name:'˶ʿ'},
	        	                {value:40, name:'��ʿ'},	     
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
			//alert("�ɳ����Ʒ���"+myChart);
	        var optionC = option = {
	        	    title : {
	        	        text: '�Ա𹹳�ͼ',	        	       
	        	        x:'center'
	        	    },
	        	    tooltip : {
	        	        trigger: 'item',
	        	        formatter: "{a} <br/>{b} : {c} ({d}%)"
	        	    },
	        	    legend: {
	        	        orient: 'vertical',
	        	        left: 'left',
	        	        data: ['��','Ů']
	        	    },
	        	    series : series
	        	  //  	 [
	        	  //      {
	        	  //          name: '�Ա����',
	        	  //          type: 'pie',
	        	  //          radius : '55%',
	        	 //           center: ['50%', '60%'],
	        	 //           data:[
	        	 ////               {value:335, name:'��'},
	        	 //               {value:310, name:'Ů'},	        	                
	        	 //           ],	        	          
	        	//        }
	        	 //   ] 
	        	};
		       
	        myChart.setOption(optionC);
		}
		
		function initmz() {
			var myChart = echarts.init(document.getElementById("chartmz"), 'macarons');
			//alert("�ɳ����Ʒ���"+myChart);
	        var optionC = option = {
	        	    title : {
	        	        text: '���幹��ͼ',	        	       
	        	        x:'center'
	        	    },
	        	    tooltip : {
	        	        trigger: 'item',
	        	        formatter: "{a} <br/>{b} : {c} ({d}%)"
	        	    },
	        	    legend: {
	        	        orient: 'vertical',
	        	        left: 'left',
	        	        data: ['����','��������']
	        	    },
	        	    series : seriesmz
	        	    	/* [
	        	        {
	        	            name: '�������',
	        	            type: 'pie',
	        	            radius : '55%',
	        	            center: ['50%', '60%'],
	        	            data:[
	        	                {value:335, name:'����'},
	        	                {value:30, name:'��������'},	        	                
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
							<a name="��������" class="anchor "></a>
						</div>
						<div class="para-title level-2">
							<h4 class="title-text">��������</h4>
						</div>
						<div class="removalText" id="jgzs"></div>
						</td>
						  </tr>
						  
						<!--    <tr>
						 <td>
						<div class="anchor-list">
							<a name="���ӳ�Ա" class="anchor "></a>
						</div>
						<div class="para-title level-2">
							<h4 class="title-text">���ӳ�Ա</h4>
						</div>
						<div class="removalText" id="bzcy"></div>
						
						</td>
						  </tr> -->
						  
						   <tr>
						 <td>
						<div class="anchor-list">
							<a name="����ṹ" class="anchor "></a>
						</div>
						<div class="para-title level-2">
							<h4 class="title-text">����ṹ</h4>
						</div>
						<div>
							<div id="chartnl" style="width: 450px; height: 220px; margin: 0 auto;"></div>
						</div>
                           </td>
		                  <td>
						<div class="anchor-list">
							<a name="ѧλ�ṹ" class="anchor "></a>
						</div>
						<div class="para-title level-2">
							<h4 class="title-text">ѧλ�ṹ</h4>
						</div>
						<div>
							<div id="chartxw" style="width: 450px; height: 220px; margin: 0 auto;"></div>
						</div>
		                  </td>
		                  </tr>
		
		              <tr>
						 <td>
						<div class="anchor-list">
							<a name="�Ա�ṹ" class="anchor "></a>
						</div>
						<div class="para-title level-2">
							<h4 class="title-text">�Ա�ṹ</h4>
						</div>
						<div>
							<div id="chartxb" style="width: 450px; height: 220px; margin: 0 auto;"></div>
						</div>
		                </td>

                            <td>
						<div class="anchor-list">
							<a name="����ṹ" class="anchor"></a>
						</div>
						<div class="para-title level-2">
							<h4 class="title-text">����ṹ</h4>
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
				
					<!-- <div class="promotion_title">��Ա��ǩ��</div>
					<div class="promotion_viewport">
						<div>
							<div class="styleing">
								<a href="*">��˽</a> <a href="*">��������</a> <a href="*">̰ͼС����</a>
							</div>
							<div class="styleing2">
								<a href="*">�˼�</a> <a href="*">Ư��</a> <a href="*">�˷�</a>
							</div>
						</div>
					</div> -->
					<table>
				   <tr>
					<td>
						<div>
							<a name="���ӳ�Ա" class="anchor "></a>
						</div>
						<div >
							<h4 class="title-text">���ӳ�Ա</h4>
						</div>
						<div class="removalText" id="jgzs"></div>
					</td>
					</tr>
					</table>
			</div>
		</div>
    
    
    
    	
	</body>
</html>