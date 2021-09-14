<%@page import="com.insigma.siis.local.business.entity.Supervision"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@page import="com.insigma.odin.framework.db.DBUtil"%>
<%@page import="com.insigma.odin.framework.db.DBUtil.DBType"%>
<%@page import="com.insigma.odin.framework.persistence.HBUtil"%>
<%@page import="com.insigma.odin.framework.persistence.HBSession"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@page language="java" import="java.util.*" isELIgnored="false"%>
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
<odin:hidden property="jsonSeries"/><!-- 成长趋势分析 - 部分信息 -->
<odin:hidden property="zj"/><!-- 职级-->
<%@include file="/rmb/rmbPortrait.jsp"%> 

<%	
	HBSession se = HBUtil.getHBSession();

	String ssql = "s";


	//政策法规 
	String hql = "";
	if(DBUtil.getDBType()==DBType.ORACLE){
		hql = "from Supervision where A0000 = '"+a0000+"' order by startdate desc";
		//hql = "select * from Supervision s  left join CODE_VALUE c  on  s.result=c.CODE_VALUE where A0000 = '"+a0000+"' order by startdate desc";
	}else{
		hql = "from Supervision  where 1=1 order by updatetime desc limit 0,6";
	}
	List<Supervision> supervisionList = sess.createQuery(hql).list();
%>
<%-- <%@include file="/rmb/rmbServer.jsp" %> --%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=GBK">
		<link media="all" type="text/css" href="css/peopleportrait.css" rel="stylesheet" />
		<script src="basejs/jquery/jquery-1.7.2.min.js"> </script>
		
		<script type="text/javascript">
			var legend,data,series;
			//Echarts所需变量
			var minDate,maxDate;//年份设定
	        var categories = [ '奖惩情况', '培训经历', '教育经历', '年度考核', '职级经历' ];
			//添加简介信息		
			function insertInfo(dataJlStr,dataXzStr,dataJcStr,dataKhStr,dataJyxlStr,dataJyxwStr){
      
				var zj = document.getElementById("zj").value;
				
				legend = document.getElementById("legendData").value;
				legend = eval(legend);
				//console.log(legend);
				
				data = document.getElementById("jsonData").value;
				data = eval(data);
				//alert("123"+data);
				//console.log(data);
				
				series = document.getElementById("jsonSeries").value;
				series = eval(series);
				//console.log(series);
				
				minDate = document.getElementById("a0107StrMin").value;
				maxDate = document.getElementById("a0107StrMax").value;
				minDate = parseInt(minDate);
				maxDate = parseInt(maxDate);
				//基本信息
				document.getElementById("jbInfo").innerHTML = dataJlStr;
				document.getElementById("zwInfo").innerHTML = dataXzStr;
				//简历信息
				var a1701Str = document.getElementById('a1701').value;
				a1701Str = a1701Str.replace(/\r/g,'').replace(/\n/g,'</p><p>').replace(/期间/g,'其间').replace("- ",'-&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');
				document.getElementById("jlInfo").innerHTML = "<p>"+a1701Str+"</p>";
				//奖惩信息
				document.getElementById("jcInfo").innerHTML = "<p>"+dataJcStr+"</p>";
				//考核信息
				document.getElementById("ndkh").innerHTML = "<p>"+dataKhStr+"</p>";
				//教育信息
				document.getElementById("jy").innerHTML = "<p>"+dataJyxlStr+"</p>"+"<p>"+dataJyxwStr+"</p>";
				//职级信息
				document.getElementById("zjjl").innerHTML = "<p>"+zj+"</p>";
				//Echarts信息
				//alert("2222");
				initAllChart();//整体分析
				//alert("11111");
				initChart();//成长趋势分析
			}
			/* 功能未知 */
			function initAllChart() {
				var myChart = echarts.init(document.getElementById("chartAll"), 'macarons');
				//alert("整体分析"+myChart);
				
				var option = {
					tooltip: {
						formatter: function (params) {
							if (params.data.value[3] == 'ndkh') {        //年度考核
								return params.data.value[4] + '年度考核为' + params.data.value[5];
							}
							else if (params.data.value[3] == 'zj') {     //职级经历
								return params.data.value[4] + '至' + params.data.value[5] + '，任' + params.data.value[6];
							}
							else if (params.data.value[3] == 'jy') {    //教育经历
								return params.data.value[4] + '至' + params.data.value[5] +  params.data.value[6];
							}
							else if (params.data.value[3] == 'px') {     //培训经历              
								return params.data.value[4] + '至' + params.data.value[5] +  params.data.value[6];
							}
							else if (params.data.value[3] == 'jc') {   //奖惩情况
								return params.data.value[4] + params.data.value[5];
							}
							else {
								return params.data.value[0];
							}
						}
					},
					title: {
						text: '',
						left: 'center'
					},
					legend: {
						data: ['bar', 'error']
					},
					dataZoom: [{
						type: 'slider',
						filterMode: 'weakFilter',
						showDataShadow: false,
						top: 340,
						height: 10,
						borderColor: 'transparent',
						backgroundColor: '#e2e2e2',
						handleIcon: 'M10.7,11.9H9.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4h1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7v-1.2h6.6z M13.3,22H6.7v-1.2h6.6z M13.3,19.6H6.7v-1.2h6.6z', // jshint ignore:line
						handleSize: 20,
						handleStyle: {
							shadowBlur: 6,
							shadowOffsetX: 1,
							shadowOffsetY: 2,
							shadowColor: '#aaa'
						},
						labelFormatter: ''
					}, {
						type: 'inside',
						filterMode: 'weakFilter'
					}],
					grid: {
						height:240
					},
					xAxis: {
						min: minDate,
						max: maxDate,
						scale: true,
						axisLabel: {
							formatter: function (val) {
								//console.log((val + '').slice(0, 4));
								return (val + '').slice(0, 4);
							}
						}
					},
					yAxis: {
						data: categories
					},
					series: [{
						type: 'custom',
						renderItem: function (params, api) {
						    var categoryIndex = api.value(0);
						    var start = api.coord([api.value(1), categoryIndex]);
						    var end = api.coord([api.value(2), categoryIndex]);
						    var height = api.size([0, 1])[1] * 0.6;
							var width = end[0] - start[0];
							if (width < 10) width = 10;
							
						    return {
						        type: 'rect',
						        shape: echarts.graphic.clipRectByRect({
						            x: start[0],
						            y: start[1] - height / 2,
						            width: width,
						            height: height
						        }, {
						            x: params.coordSys.x,
						            y: params.coordSys.y,
						            width: params.coordSys.width,
						            height: params.coordSys.height
						        }),
						        style: api.style()
						    }
						},
						itemStyle: {
							normal: {
								opacity: 0.8
							}
						},
						encode: {
							x: [1, 3],
							y: 0
						},
						data: data
					}]
				};
		
				myChart.setOption(option);
			}
			function initChart() {
				var myChart = echarts.init(document.getElementById("chart"), 'macarons');
				//alert("成长趋势分析"+myChart);
		        var optionC = {
					tooltip: {
						trigger: 'item',
						formatter:'{a}<br/>{b}<br/>{c}'
					},
					grid: {
						x:100
					},
					legend: {
						data: legend
					},
					xAxis: [{
						name: '时间',
						type: 'time',
						 splitLine: {
							show: false
						},
						axisLabel:{
								formatter: function (value, index) {
									var date = new Date(value);
									
									var month = date.getMonth()+1;
									if (month < 10) {
										month = '0'+ month;
									}
									var texts = [date.getFullYear(),month];
									return texts.join('/');
						}
							}
					}],
					yAxis: [{
							type: 'category',
							name: '职务职级',
							boundaryGap : false,
							splitLine: {
							show: true
						},
						  data:['','科员级','副科级','正科级','副处级','正处级']
						  //data:['','副科非领导','副科领导','正科非领导','正科领导','副处非领导','副处领导','正处非领导','正处领导','副厅非领导','副厅领导','正厅非领导','正厅领导']
						}
					],
					series: series
				};		       
		        myChart.setOption(optionC);
			}
		</script>
	</head>
    <body>
    
    	<odin:hidden property="a0000Str" value="<%=SV(a01.getA0000()) %>" />
    	<input type="hidden" value="<%=SV(a01.getA1701()) %>" name="a1701" id="a1701" alt="简历">
		<div class="main2">
			<div class="content-wrapper">
				<div class="content">
					<div class="main-content">
						<h1 style="font-size: 40px;"><%=SV(a01.getA0101())%></h1>
						<div class="summary">
							<div class="para" id="jbInfo"></div>
							<div class="para" id="zwInfo"></div>
						</div>
						<div class="anchor-list">
							<a name="整体分析" class="anchor "></a>
						</div>
						<div class="para-title level-2">
							<h2 class="title-text">整体分析</h2>
						</div>
						<div>
							<div id="chartAll" style="width: 700px; height: 400px; margin: 0 auto;"></div>
						</div>
						<div class="anchor-list">
							<a name="成长趋势分析" class="anchor "></a>
						</div>
						<div class="para-title level-2">
							<h2 class="title-text">成长趋势分析</h2>
						</div>
						<div>
							<div id="chart" style="width: 700px; height: 400px; margin: 0 auto;"></div>
						</div>
						<div class="anchor-list">
							<a name="工作简历" class="anchor "></a>
						</div>
						<div class="para-title level-2">
							<h2 class="title-text">工作简历</h2>
						</div>
						<div class="removalText" id="jlInfo"
							style="white-space: pre-wrap; text-align: left; border: 0px; width: 100%; overflow-x: none;">
						</div>
						<div class="anchor-list">
							<a name="职级经历" class="anchor "></a>
						</div>
						<div class="para-title level-2">
							<h2 class="title-text">职级经历</h2>
						</div>
		                <div  id="zjjl"></div>
		
		
						<div class="anchor-list">
							<a name="年度考核" class="anchor "></a>
						</div>
						<div class="para-title level-2">
							<h2 class="title-text">年度考核</h2>
						</div>
						<div class="removalText" id="ndkh"></div>
		
						<div class="anchor-list">
							<a name="教育经历" class="anchor "></a>
						</div>
						<div class="para-title level-2">
							<h2 class="title-text">教育经历</h2>
						</div>
						<div class="removalText" id="jy"></div>
		
						<div class="anchor-list">
							<a name="培训经历" class="anchor "></a>
						</div>
						<div class="para-title level-2">
							<h2 class="title-text">培训经历</h2>
						</div>
						<div class="removalText" id="px"><%=SV(a01.getA14z101()) %></div>
		
						<div class="anchor-list">
							<a name="奖惩情况" class="anchor"></a>
						</div>
						<div class="para-title level-2">
							<h2 class="title-text">奖惩情况</h2>
						</div>
						<div class="removalText" id="jcInfo"></div>
					</div>
				</div>
			</div>
		</div>
		<div class="main2">
			<div class="side-content">
				<div class="summary-pic">
					<img src="<%= request.getContextPath()%>/servlet/DownloadUserHeadImage?a0000=<%=SV(a01.getA0000())%>" width="268" height="335" />
				</div>
				<div class="promotion-vbaike">
					<div class="promotion_title">干部任免预警信息</div>
					<div class="promotion_viewport">
						<div class="para" id="warn" style="color: red; font-weight: bold">
						     <table>
					<tbody>
					<tr>
					            <td width="20%"  style="text-align:center;color: red; font-weight: bold;">时间</td>
								<td width="60%"  style="text-align:center;color: red; font-weight: bold">处理结果</td>
					</tr>
					<%
							for(Integer i=1;i<=supervisionList.size();i++){ 
												
												int length = supervisionList.size();
												Supervision supervision = new Supervision();
												if(length>=i){
													supervision = supervisionList.get(i-1);
													
																										
													//对标题进行处理，过长则截取 
													String result = supervision.getResult();
													
													if("jd13".equals(result)){
														result = "党内警告";
													}else if("jd14".equals(result)){
														result = "党内严重警告";
													}else if("jd15".equals(result)){
														result = "撤销党内职务";
													}else if("jd16".equals(result)){
														result = "留党察看一年";
													}else if("jd17".equals(result)){
														result = "留党察看二年";
													}
												    else if("jd18".equals(result)){result = "开除党籍";}else if("jd19".equals(result)){result = "警告";}else if("jd20".equals(result)){result = "记过";}else if("jd21".equals(result)){result = "记大过";}
												    else if("jd22".equals(result)){result = "降级";}else if("jd23".equals(result)){result = "撤职";}else if("jd24".equals(result)){result = "开除";}else if("jd25".equals(result)){result = "立案审查、拘留、逮捕";}
												    else if("jd26".equals(result)){result = "法院列为失信被执行人";}else if("jd27".equals(result)){result = "通报";}else if("jd28".equals(result)){result = "诫勉";}else if("jd29".equals(result)){result = "停职检查";}
												    else if("jd30".equals(result)){result = "调整职务";}else if("jd31".equals(result)){result = "降职";}else if("jd32".equals(result)){result = "责令辞职";}else if("jd33".equals(result)){result = "免职";}
												    else if("jd34".equals(result)){result = "批评教育、责令做出检查、限期改正";}else if("jd35".equals(result)){result = "诫勉";}else if("jd36".equals(result)){result = "取消考察对象(后备干部人选)资格";}else if("jd37".equals(result)){result = "调离岗位、改任非领导职务、免职";}
												    else if("jd38".equals(result)){result = "纪律处分";}else if("jd39".equals(result)){result = "无";}
													supervision.setResult(result);													
												}
												request.setAttribute("supervision", supervision);
												
							
						%>
					<tr>
					            						
					<c:if test="${supervision.id != null}">
					
					            <%-- <td width="10%" style="text-align:center;"><%=i%></td> --%>								
								<td width="20%"  style="text-align:left;color: red; ">${supervision.startdate}</td>
								<td width="60%"  style="text-align:center;color: red;">${supervision.result}</td>
								
				    </c:if>
		
					</tr>
					<%
							}
					%>
					</tbody>
					</table>
						
						
						
						</div>
					</div>
				</div>
			</div>
			<div class="side-content" style="display:none;" >
				<div class="promotion-vbaike">
					<div class="promotion_title">人员标签：</div>
					<div class="promotion_viewport">
						<div>
							<div class="styleing">
								<a href="*">自私</a> <a href="*">无责任心</a> <a href="*">贪图小便宜</a>
							</div>
							<div class="styleing2">
								<a href="*">顾家</a> <a href="*">漂亮</a> <a href="*">乘风</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>