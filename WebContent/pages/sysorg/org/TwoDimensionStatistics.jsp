<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/echarts.js" charset="utf-8"></script>
<%@include file="/comOpenWinInit.jsp" %>
<style type="text/css">
table.stable {
color:#333333;
border-width: 1px;
border-color: #999999;
border-collapse: collapse;
}
table.stable th {
background:#d4e9fc ;
border-width: 1px;
padding: 2px;
border-style: solid;
border-color: #999999;
}
table.stable td {
border-width: 1px;
padding: 2px;
border-style: solid;
border-color: #999999;
}
	
	
	.cell1{
	width: 150px !important;
	height: 54px  !important;
	text-align:center;
	}
	.cell2{
	width: 150px !important;
	height: 44px  !important;
	text-align:center;
	}

</style>

<div id="bar_div"></div>
<odin:toolBar property="btnToolBar" applyTo="bar_div">
	<odin:textForToolBar text="<h3></h3>"/>
	<odin:separator></odin:separator>
	<odin:fill/>
	<odin:buttonForToolBar text="导出" icon="" handler="exportData" id="export" ></odin:buttonForToolBar>
	<odin:separator/>
	<odin:buttonForToolBar text="表格" icon="" handler="ctable" id="ctable"  ></odin:buttonForToolBar>
	<odin:separator/>
	<odin:buttonForToolBar text="曲线图" icon="image/u177.png" handler="cquxian" id="cquxian"  ></odin:buttonForToolBar>
	<odin:separator/>
	<odin:buttonForToolBar text="柱状图" icon="image/u175.png" handler="czhuzhuang" id="czhuzhuang"  ></odin:buttonForToolBar>
	<odin:separator/>
	<odin:buttonForToolBar text="雷达图" icon="image/u332.png" handler="cleida" id="cleida" isLast="true" ></odin:buttonForToolBar>
<%-- 	<odin:separator/>
	<odin:buttonForToolBar text="下载" icon=""  id="download"  isLast="true"></odin:buttonForToolBar> --%>
</odin:toolBar>
<div style="height:15px"></div>
<div id="createtable" style="width:950px;height: 510px; overflow: auto;margin-left:15px;">
	<table  class="stable" style="text-align:center;font-size: 12px">
	<tr style="width:980px">
		<th class="cell1" "></th>
		<th class="cell1" id="t0"></th>
		<th class="cell1" id="t1"></th>
		<th class="cell1" id="t2"></th>
		<th class="cell1" id="t3"></th>
		<th class="cell1" id="t4"></th>
		<th class="cell1" id="t5"></th>
		<th class="cell1" id="t6"></th>
		<th class="cell1" id="t7"></th>
	</tr>
	<tr>
		<th class="cell2" id="v0"></th>
		<td id="v0_t0"></td>
		<td id="v0_t1"></td>
		<td id="v0_t2"></td>
		<td id="v0_t3"></td>
		<td id="v0_t4"></td>
		<td id="v0_t5"></td>
		<td id="v0_t6"></td>
		<td id="v0_t7"></td>
	</tr>
	<tr>
		<th class="cell2" id="v1"></th>
		<td id="v1_t0"></td>
		<td id="v1_t1"></td>
		<td id="v1_t2"></td>
		<td id="v1_t3"></td>
		<td id="v1_t4"></td>
		<td id="v1_t5"></td>
		<td id="v1_t6"></td>
		<td id="v1_t7"></td>
	</tr>
	<tr>
		<th class="cell2" id="v2"></th>
		<td id="v2_t0"></td>
		<td id="v2_t1"></td>
		<td id="v2_t2"></td>
		<td id="v2_t3"></td>
		<td id="v2_t4"></td>
		<td id="v2_t5"></td>
		<td id="v2_t6"></td>
		<td id="v2_t7"></td>
	</tr>
	<tr>
		<th class="cell2" id="v3"></th>
		<td id="v3_t0"></td>
		<td id="v3_t1"></td>
		<td id="v3_t2"></td>
		<td id="v3_t3"></td>
		<td id="v3_t4"></td>
		<td id="v3_t5"></td>
		<td id="v3_t6"></td>
		<td id="v3_t7"></td>
	</tr>
	<tr>
		<th class="cell2" id="v4"></th>
		<td id="v4_t0"></td>
		<td id="v4_t1"></td>
		<td id="v4_t2"></td>
		<td id="v4_t3"></td>
		<td id="v4_t4"></td>
		<td id="v4_t5"></td>
		<td id="v4_t6"></td>
		<td id="v4_t7"></td>
	</tr>
	<tr>
		<th class="cell2" id="v5"></th>
		<td id="v5_t0"></td>
		<td id="v5_t1"></td>
		<td id="v5_t2"></td>
		<td id="v5_t3"></td>
		<td id="v5_t4"></td>
		<td id="v5_t5"></td>
		<td id="v5_t6"></td>
		<td id="v5_t7"></td>
	</tr>
	<tr>
		<th class="cell2" id="v6"></th>
		<td id="v6_t0"></td>
		<td id="v6_t1"></td>
		<td id="v6_t2"></td>
		<td id="v6_t3"></td>
		<td id="v6_t4"></td>
		<td id="v6_t5"></td>
		<td id="v6_t6"></td>
		<td id="v6_t7"></td>
	</tr>
	<tr>
		<th class="cell2" id="v7"></th>
		<td id="v7_t0"></td>
		<td id="v7_t1"></td>
		<td id="v7_t2"></td>
		<td id="v7_t3"></td>
		<td id="v7_t4"></td>
		<td id="v7_t5"></td>
		<td id="v7_t6"></td>
		<td id="v7_t7"></td>
	</tr>
	<tr>
		<th class="cell2" id="v8"></th>
		<td id="v8_t0"></td>
		<td id="v8_t1"></td>
		<td id="v8_t2"></td>
		<td id="v8_t3"></td>
		<td id="v8_t4"></td>
		<td id="v8_t5"></td>
		<td id="v8_t6"></td>
		<td id="v8_t7"></td>
	</tr>
	<tr>
		<th class="cell2" id="v9"></th>
		<td id="v9_t0"></td>
		<td id="v9_t1"></td>
		<td id="v9_t2"></td>
		<td id="v9_t3"></td>
		<td id="v9_t4"></td>
		<td id="v9_t5"></td>
		<td id="v9_t6"></td>
		<td id="v9_t7"></td>
	</tr>
	<tr>
		<th class="cell2" id="v10"></th>
		<td id="v10_t0"></td>
		<td id="v10_t1"></td>
		<td id="v10_t2"></td>
		<td id="v10_t3"></td>
		<td id="v10_t4"></td>
		<td id="v10_t5"></td>
		<td id="v10_t6"></td>
		<td id="v10_t7"></td>
	</tr>
	<tr>
		<th class="cell2" id="v11"></th>
		<td id="v11_t0"></td>
		<td id="v11_t1"></td>
		<td id="v11_t2"></td>
		<td id="v11_t3"></td>
		<td id="v11_t4"></td>
		<td id="v11_t5"></td>
		<td id="v11_t6"></td>
		<td id="v11_t7"></td>
	</tr>
	<tr>
		<th class="cell2" id="v12"></th>
		<td id="v12_t0"></td>
		<td id="v12_t1"></td>
		<td id="v12_t2"></td>
		<td id="v12_t3"></td>
		<td id="v12_t4"></td>
		<td id="v12_t5"></td>
		<td id="v12_t6"></td>
		<td id="v12_t7"></td>
	</tr>
	<tr>
		<th class="cell2" id="v13"></th>
		<td id="v13_t0"></td>
		<td id="v13_t1"></td>
		<td id="v13_t2"></td>
		<td id="v13_t3"></td>
		<td id="v13_t4"></td>
		<td id="v13_t5"></td>
		<td id="v13_t6"></td>
		<td id="v13_t7"></td>
	</tr>
</table>
</div>
	

<odin:hidden property="tran_name"/>
<odin:hidden property="vert_name"/>
<odin:hidden property="qxdata"/>
<odin:hidden property="zzdata"/>
<odin:hidden property="lddata"/>
<odin:hidden property="ld_tran_name"/>
<odin:hidden property="tabledata"/>
<odin:hidden property="str"/>

<odin:hidden property="vert0"/>
<odin:hidden property="vert1"/>
<odin:hidden property="vert2"/>
<odin:hidden property="vert3"/>
<odin:hidden property="vert4"/>
<odin:hidden property="vert5"/>
<odin:hidden property="vert6"/>
<odin:hidden property="vert7"/>
<odin:hidden property="vert8"/>
<odin:hidden property="vert9"/>
<odin:hidden property="vert10"/>
<odin:hidden property="vert11"/>
<odin:hidden property="vert12"/>
<odin:hidden property="vert13"/>
<odin:hidden property="tran0"/>
<odin:hidden property="tran1"/>
<odin:hidden property="tran2"/>
<odin:hidden property="tran3"/>
<odin:hidden property="tran4"/>
<odin:hidden property="tran5"/>
<odin:hidden property="tran6"/>
<odin:hidden property="tran7"/>

<odin:hidden property="vert0_tran0"/>
<odin:hidden property="vert0_tran1"/>
<odin:hidden property="vert0_tran2"/>
<odin:hidden property="vert0_tran3"/>
<odin:hidden property="vert0_tran4"/>
<odin:hidden property="vert0_tran5"/>
<odin:hidden property="vert0_tran6"/>
<odin:hidden property="vert0_tran7"/>

<odin:hidden property="vert1_tran0"/>
<odin:hidden property="vert1_tran1"/>
<odin:hidden property="vert1_tran2"/>
<odin:hidden property="vert1_tran3"/>
<odin:hidden property="vert1_tran4"/>
<odin:hidden property="vert1_tran5"/>
<odin:hidden property="vert1_tran6"/>
<odin:hidden property="vert1_tran7"/>

<odin:hidden property="vert2_tran0"/>
<odin:hidden property="vert2_tran1"/>
<odin:hidden property="vert2_tran2"/>
<odin:hidden property="vert2_tran3"/>
<odin:hidden property="vert2_tran4"/>
<odin:hidden property="vert2_tran5"/>
<odin:hidden property="vert2_tran6"/>
<odin:hidden property="vert2_tran7"/>

<odin:hidden property="vert3_tran0"/>
<odin:hidden property="vert3_tran1"/>
<odin:hidden property="vert3_tran2"/>
<odin:hidden property="vert3_tran3"/>
<odin:hidden property="vert3_tran4"/>
<odin:hidden property="vert3_tran5"/>
<odin:hidden property="vert3_tran6"/>
<odin:hidden property="vert3_tran7"/>

<odin:hidden property="vert4_tran0"/>
<odin:hidden property="vert4_tran1"/>
<odin:hidden property="vert4_tran2"/>
<odin:hidden property="vert4_tran3"/>
<odin:hidden property="vert4_tran4"/>
<odin:hidden property="vert4_tran5"/>
<odin:hidden property="vert4_tran6"/>
<odin:hidden property="vert4_tran7"/>

<odin:hidden property="vert5_tran0"/>
<odin:hidden property="vert5_tran1"/>
<odin:hidden property="vert5_tran2"/>
<odin:hidden property="vert5_tran3"/>
<odin:hidden property="vert5_tran4"/>
<odin:hidden property="vert5_tran5"/>
<odin:hidden property="vert5_tran6"/>
<odin:hidden property="vert5_tran7"/>

<odin:hidden property="vert6_tran0"/>
<odin:hidden property="vert6_tran1"/>
<odin:hidden property="vert6_tran2"/>
<odin:hidden property="vert6_tran3"/>
<odin:hidden property="vert6_tran4"/>
<odin:hidden property="vert6_tran5"/>
<odin:hidden property="vert6_tran6"/>
<odin:hidden property="vert6_tran7"/>

<odin:hidden property="vert7_tran0"/>
<odin:hidden property="vert7_tran1"/>
<odin:hidden property="vert7_tran2"/>
<odin:hidden property="vert7_tran3"/>
<odin:hidden property="vert7_tran4"/>
<odin:hidden property="vert7_tran5"/>
<odin:hidden property="vert7_tran6"/>
<odin:hidden property="vert7_tran7"/>

<odin:hidden property="vert8_tran0"/>
<odin:hidden property="vert8_tran1"/>
<odin:hidden property="vert8_tran2"/>
<odin:hidden property="vert8_tran3"/>
<odin:hidden property="vert8_tran4"/>
<odin:hidden property="vert8_tran5"/>
<odin:hidden property="vert8_tran6"/>
<odin:hidden property="vert8_tran7"/>

<odin:hidden property="vert9_tran0"/>
<odin:hidden property="vert9_tran1"/>
<odin:hidden property="vert9_tran2"/>
<odin:hidden property="vert9_tran3"/>
<odin:hidden property="vert9_tran4"/>
<odin:hidden property="vert9_tran5"/>
<odin:hidden property="vert9_tran6"/>
<odin:hidden property="vert9_tran7"/>

<odin:hidden property="vert10_tran0"/>
<odin:hidden property="vert10_tran1"/>
<odin:hidden property="vert10_tran2"/>
<odin:hidden property="vert10_tran3"/>
<odin:hidden property="vert10_tran4"/>
<odin:hidden property="vert10_tran5"/>
<odin:hidden property="vert10_tran6"/>
<odin:hidden property="vert10_tran7"/>

<odin:hidden property="vert11_tran0"/>
<odin:hidden property="vert11_tran1"/>
<odin:hidden property="vert11_tran2"/>
<odin:hidden property="vert11_tran3"/>
<odin:hidden property="vert11_tran4"/>
<odin:hidden property="vert11_tran5"/>
<odin:hidden property="vert11_tran6"/>
<odin:hidden property="vert11_tran7"/>

<odin:hidden property="vert12_tran0"/>
<odin:hidden property="vert12_tran1"/>
<odin:hidden property="vert12_tran2"/>
<odin:hidden property="vert12_tran3"/>
<odin:hidden property="vert12_tran4"/>
<odin:hidden property="vert12_tran5"/>
<odin:hidden property="vert12_tran6"/>
<odin:hidden property="vert12_tran7"/>

<odin:hidden property="vert13_tran0"/>
<odin:hidden property="vert13_tran1"/>
<odin:hidden property="vert13_tran2"/>
<odin:hidden property="vert13_tran3"/>
<odin:hidden property="vert13_tran4"/>
<odin:hidden property="vert13_tran5"/>
<odin:hidden property="vert13_tran6"/>
<odin:hidden property="vert13_tran7"/>

<div id="zhuzhuang"  style="width:960px;height:580px;margin: 0 auto;"></div>
<div id="quxian"  style="width:960px;height:580px;margin: 0 auto;"></div>
<div id="leida"  style="width:960px;height:580px;margin: 0 auto;"></div>


<script type="text/javascript">
	/* function createEwTable(tabledata){
		//var tabledata = document.getElementById('tabledata').value;
		var tabledata = tabledata;
		//tabledata = eval(tabledata);
		tabledata = Ext.decode(tabledata);
		alert(1);
		for(var i=0;i<tabledata.vert_num;i++){
			alert(2);
			var str = tabledata.(vert_data+i);
			document.getElementById('vert'+i).setValue(tabledata.(vert_name+i));
			alert(str);
			var str_arr = str.split("@");
			for(var j=0;j<tabledata.tran_num;j++){
				alert(str_arr);
				document.getElementById('vert'+i+'_tran'+j).setValue(str_arr[j]);
				if(i=0){
					document.getElementById('tran'+j).setValue(tabledata.(tran_name+j));
				}
			}
		}
		
	} */
	function first_load(){
		for(var i = 0; i < 8; i++){
			document.getElementById("t"+i).style.display='none';
			for(var j = 0; j < 14; j++){
				document.getElementById("v"+j).style.display='none';
				document.getElementById("v"+j+"_t"+i).style.display='none';
			} 
		}
	}
	
	function createEwTable(){
		var tran_length = document.getElementById("tran_length").value;
		for(var i = 0;i < tran_length; i++){
			var tran = document.getElementById('tran'+i).value;
			document.getElementById('t'+i).style.display="";
			document.getElementById('t'+i).innerHTML=tran;
			for(var j = 0; j < 14; j++){
				var vert = document.getElementById('vert'+j).value;
				document.getElementById('v'+j).style.display="";
				document.getElementById('v'+j).innerHTML=vert;
				var vert_tran = document.getElementById('vert'+j+'_tran'+i).value;
				document.getElementById('v'+j+'_t'+i).style.display="";
				document.getElementById('v'+j+'_t'+i).innerHTML=vert_tran;
			}
		}
	}
	
	function setZhuzhuang(){
		var vert_name = document.getElementById('vert_name').value;
		var tran_name = document.getElementById('tran_name').value;
		var zzdata = document.getElementById('zzdata').value;
		tran_name = eval(tran_name);
		vert_name = eval(vert_name);
		zzdata = eval(zzdata);
		// 基于准备好的dom，初始化echarts实例
		var myChart = echarts.init(document.getElementById('zhuzhuang'));
		// 使用刚指定的配置项和数据显示图表。
		myChart.setOption({ 
				tooltip : { 	
					trigger: 'axis', 
					axisPointer : { 		
						type : 'shadow'  	
					} 
				}, 
				legend: { 	
					x: 'left', 
					data: vert_name
				}, 
				grid: { 	
					left: '3%', 	
					right: '4%', 	
					bottom: '3%', 	
					containLabel: true 
				}, 
				xAxis : [ 	{ 		
					type : 'category', 		
					data : tran_name,       
					axisLabel:{  interval: 0 ,rotate:20}	
				} ], 
				grid: {     
					x: 70, 	  
					x2: 100, 	  
					y2: 100 
				}, 
				yAxis : [ 	{ 		
					type : 'value' 	
				} ], 
				series : zzdata
			});
		//window.onresize = myChart.resize;
		//imgUrlzhuzhuang = myChart.getDataURL();
		
	}


	function setQuxian(){
		var tran_name = document.getElementById('tran_name').value;
		var vert_name = document.getElementById('vert_name').value;
		var qxdata = document.getElementById('qxdata').value;
		tran_name = eval(tran_name);
		vert_name = eval(vert_name);
		qxdata = eval(qxdata);
		// 基于准备好的dom，初始化echarts实例
		var myChart = echarts.init(document.getElementById('quxian'));
		// 使用刚指定的配置项和数据显示图表。
		myChart.setOption({ 	
				tooltip: { 	
				trigger: 'axis' 
				}, 
				legend: { 	
						//orient: 'vertical', 	
						x: 'letf', 	
						data: vert_name 
				}, 
				xAxis:  { 	
						type: 'category', 	
						boundaryGap: false, 	
						data: tran_name,   
						axisLabel:{  
							interval: 0,
							rotate:20 
						}
				}, 
				grid:   {     
						x: 70, 	 
						x2: 100, 	  
						y2: 100 
				}, 
				yAxis: { 	
						type: 'value' 
				}, 
				series: qxdata
			});
		//window.onresize = myChart.resize;
		//imgUrlquxian = myChart.getDataURL();
		
	}

	function setLeida(){
		var vert_name = document.getElementById('vert_name').value;
		var lddata = document.getElementById('lddata').value;
		var ld_tran_name = document.getElementById('ld_tran_name').value;
		vert_name = eval(vert_name);
		lddata = eval(lddata);
		ld_tran_name = eval(ld_tran_name);
		// 基于准备好的dom，初始化echarts实例
		var myChart = echarts.init(document.getElementById('leida'));
		// 使用刚指定的配置项和数据显示图表。
		myChart.setOption({ 
				tooltip : { },
				legend: { 	
					//orient: 'vertical', 	
					x: 'left',  	
					data: vert_name
				}, 
				radar: { 	
					indicator: ld_tran_name,     
					nameGap:'10',      
					radius:'65%' 
				}, 
				series: [{ 	 
					type: 'radar', 	 
					data : lddata
				}]
			}); 
		//window.onresize = myChart.resize;
		//imgUrlleida = myChart.getDataURL();
		
	}
	function def(){
		document.getElementById("leida").style.display='none';
		document.getElementById("zhuzhuang").style.display='none';
		document.getElementById("quxian").style.display='none';
		document.getElementById("createtable").style.display='none';
	}

	function loding(){
		ctable();
	}
	
	function ctable(){
		document.getElementById("createtable").style.display='block';
		document.getElementById("leida").style.display='none';
		document.getElementById("zhuzhuang").style.display='none';
		document.getElementById("quxian").style.display='none';
	}
	function czhuzhuang(){
		document.getElementById("createtable").style.display='none';
		document.getElementById("leida").style.display='none';
		document.getElementById("zhuzhuang").style.display='block';
		document.getElementById("quxian").style.display='none';
		//imgUrl = imgUrlzhuzhuang;
	}
	function cquxian(){
		document.getElementById("createtable").style.display='none';
		document.getElementById("leida").style.display='none';
		document.getElementById("zhuzhuang").style.display='none';
		document.getElementById("quxian").style.display='block';
		//imgUrl = imgUrlquxian;
	
	}
	function cleida(){
		document.getElementById("createtable").style.display='none';
		document.getElementById("leida").style.display='block';
		document.getElementById("zhuzhuang").style.display='none';
		document.getElementById("quxian").style.display='none';
		//imgUrl = imgUrlleida;
	}


	function exportData(){
		 w = window.open("ProblemDownServlet?method=downFiletj2");
	}

	function downloadImg(){
 		//document.getElementById("dContent").value=imgUrl;
    	//document.getElementById("form2").submit();  
	} 
</script>
<odin:hidden property="tran_length"/>