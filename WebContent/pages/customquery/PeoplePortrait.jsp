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
<odin:hidden property="a0107StrMin"/><!-- �������ʱ������Сֵ -->
<odin:hidden property="a0107StrMax"/><!-- �������ʱ�������ֵ  -->
<odin:hidden property="legendData"/><!-- ���� -->
<odin:hidden property="jsonData"/><!-- ������� - ������Ϣ -->
<odin:hidden property="jsonSeries"/><!-- �ɳ����Ʒ��� - ������Ϣ -->
<odin:hidden property="zj"/><!-- ְ��-->
<%@include file="/rmb/rmbPortrait.jsp"%> 

<%	
	HBSession se = HBUtil.getHBSession();

	String ssql = "s";


	//���߷��� 
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
			//Echarts�������
			var minDate,maxDate;//����趨
	        var categories = [ '�������', '��ѵ����', '��������', '��ȿ���', 'ְ������' ];
			//��Ӽ����Ϣ		
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
				//������Ϣ
				document.getElementById("jbInfo").innerHTML = dataJlStr;
				document.getElementById("zwInfo").innerHTML = dataXzStr;
				//������Ϣ
				var a1701Str = document.getElementById('a1701').value;
				a1701Str = a1701Str.replace(/\r/g,'').replace(/\n/g,'</p><p>').replace(/�ڼ�/g,'���').replace("- ",'-&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;');
				document.getElementById("jlInfo").innerHTML = "<p>"+a1701Str+"</p>";
				//������Ϣ
				document.getElementById("jcInfo").innerHTML = "<p>"+dataJcStr+"</p>";
				//������Ϣ
				document.getElementById("ndkh").innerHTML = "<p>"+dataKhStr+"</p>";
				//������Ϣ
				document.getElementById("jy").innerHTML = "<p>"+dataJyxlStr+"</p>"+"<p>"+dataJyxwStr+"</p>";
				//ְ����Ϣ
				document.getElementById("zjjl").innerHTML = "<p>"+zj+"</p>";
				//Echarts��Ϣ
				//alert("2222");
				initAllChart();//�������
				//alert("11111");
				initChart();//�ɳ����Ʒ���
			}
			/* ����δ֪ */
			function initAllChart() {
				var myChart = echarts.init(document.getElementById("chartAll"), 'macarons');
				//alert("�������"+myChart);
				
				var option = {
					tooltip: {
						formatter: function (params) {
							if (params.data.value[3] == 'ndkh') {        //��ȿ���
								return params.data.value[4] + '��ȿ���Ϊ' + params.data.value[5];
							}
							else if (params.data.value[3] == 'zj') {     //ְ������
								return params.data.value[4] + '��' + params.data.value[5] + '����' + params.data.value[6];
							}
							else if (params.data.value[3] == 'jy') {    //��������
								return params.data.value[4] + '��' + params.data.value[5] +  params.data.value[6];
							}
							else if (params.data.value[3] == 'px') {     //��ѵ����              
								return params.data.value[4] + '��' + params.data.value[5] +  params.data.value[6];
							}
							else if (params.data.value[3] == 'jc') {   //�������
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
				//alert("�ɳ����Ʒ���"+myChart);
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
						name: 'ʱ��',
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
							name: 'ְ��ְ��',
							boundaryGap : false,
							splitLine: {
							show: true
						},
						  data:['','��Ա��','���Ƽ�','���Ƽ�','������','������']
						  //data:['','���Ʒ��쵼','�����쵼','���Ʒ��쵼','�����쵼','�������쵼','�����쵼','�������쵼','�����쵼','�������쵼','�����쵼','�������쵼','�����쵼']
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
    	<input type="hidden" value="<%=SV(a01.getA1701()) %>" name="a1701" id="a1701" alt="����">
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
							<a name="�������" class="anchor "></a>
						</div>
						<div class="para-title level-2">
							<h2 class="title-text">�������</h2>
						</div>
						<div>
							<div id="chartAll" style="width: 700px; height: 400px; margin: 0 auto;"></div>
						</div>
						<div class="anchor-list">
							<a name="�ɳ����Ʒ���" class="anchor "></a>
						</div>
						<div class="para-title level-2">
							<h2 class="title-text">�ɳ����Ʒ���</h2>
						</div>
						<div>
							<div id="chart" style="width: 700px; height: 400px; margin: 0 auto;"></div>
						</div>
						<div class="anchor-list">
							<a name="��������" class="anchor "></a>
						</div>
						<div class="para-title level-2">
							<h2 class="title-text">��������</h2>
						</div>
						<div class="removalText" id="jlInfo"
							style="white-space: pre-wrap; text-align: left; border: 0px; width: 100%; overflow-x: none;">
						</div>
						<div class="anchor-list">
							<a name="ְ������" class="anchor "></a>
						</div>
						<div class="para-title level-2">
							<h2 class="title-text">ְ������</h2>
						</div>
		                <div  id="zjjl"></div>
		
		
						<div class="anchor-list">
							<a name="��ȿ���" class="anchor "></a>
						</div>
						<div class="para-title level-2">
							<h2 class="title-text">��ȿ���</h2>
						</div>
						<div class="removalText" id="ndkh"></div>
		
						<div class="anchor-list">
							<a name="��������" class="anchor "></a>
						</div>
						<div class="para-title level-2">
							<h2 class="title-text">��������</h2>
						</div>
						<div class="removalText" id="jy"></div>
		
						<div class="anchor-list">
							<a name="��ѵ����" class="anchor "></a>
						</div>
						<div class="para-title level-2">
							<h2 class="title-text">��ѵ����</h2>
						</div>
						<div class="removalText" id="px"><%=SV(a01.getA14z101()) %></div>
		
						<div class="anchor-list">
							<a name="�������" class="anchor"></a>
						</div>
						<div class="para-title level-2">
							<h2 class="title-text">�������</h2>
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
					<div class="promotion_title">�ɲ�����Ԥ����Ϣ</div>
					<div class="promotion_viewport">
						<div class="para" id="warn" style="color: red; font-weight: bold">
						     <table>
					<tbody>
					<tr>
					            <td width="20%"  style="text-align:center;color: red; font-weight: bold;">ʱ��</td>
								<td width="60%"  style="text-align:center;color: red; font-weight: bold">������</td>
					</tr>
					<%
							for(Integer i=1;i<=supervisionList.size();i++){ 
												
												int length = supervisionList.size();
												Supervision supervision = new Supervision();
												if(length>=i){
													supervision = supervisionList.get(i-1);
													
																										
													//�Ա�����д����������ȡ 
													String result = supervision.getResult();
													
													if("jd13".equals(result)){
														result = "���ھ���";
													}else if("jd14".equals(result)){
														result = "�������ؾ���";
													}else if("jd15".equals(result)){
														result = "��������ְ��";
													}else if("jd16".equals(result)){
														result = "�����쿴һ��";
													}else if("jd17".equals(result)){
														result = "�����쿴����";
													}
												    else if("jd18".equals(result)){result = "��������";}else if("jd19".equals(result)){result = "����";}else if("jd20".equals(result)){result = "�ǹ�";}else if("jd21".equals(result)){result = "�Ǵ��";}
												    else if("jd22".equals(result)){result = "����";}else if("jd23".equals(result)){result = "��ְ";}else if("jd24".equals(result)){result = "����";}else if("jd25".equals(result)){result = "������顢����������";}
												    else if("jd26".equals(result)){result = "��Ժ��Ϊʧ�ű�ִ����";}else if("jd27".equals(result)){result = "ͨ��";}else if("jd28".equals(result)){result = "����";}else if("jd29".equals(result)){result = "ְͣ���";}
												    else if("jd30".equals(result)){result = "����ְ��";}else if("jd31".equals(result)){result = "��ְ";}else if("jd32".equals(result)){result = "�����ְ";}else if("jd33".equals(result)){result = "��ְ";}
												    else if("jd34".equals(result)){result = "��������������������顢���ڸ���";}else if("jd35".equals(result)){result = "����";}else if("jd36".equals(result)){result = "ȡ���������(�󱸸ɲ���ѡ)�ʸ�";}else if("jd37".equals(result)){result = "�����λ�����η��쵼ְ����ְ";}
												    else if("jd38".equals(result)){result = "���ɴ���";}else if("jd39".equals(result)){result = "��";}
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
					<div class="promotion_title">��Ա��ǩ��</div>
					<div class="promotion_viewport">
						<div>
							<div class="styleing">
								<a href="*">��˽</a> <a href="*">��������</a> <a href="*">̰ͼС����</a>
							</div>
							<div class="styleing2">
								<a href="*">�˼�</a> <a href="*">Ư��</a> <a href="*">�˷�</a>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>