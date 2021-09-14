<html>
<head>
 	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
</head>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<odin:hidden property="param1"/>
<odin:hidden property="param2"/>
<odin:hidden property="param3"/>
<odin:hidden property="param4"/>
<odin:hidden property="unitid"/>
<odin:hidden property="type"/>
<odin:hidden property="state"/>
<style>
.x-form-item2 tr td .x-form-item{
margin-bottom: 0px !important;
}
.vueBtn {
   display: inline-block;
   padding: .3em .5em;
   background-color: #6495ED;
   border: 1px solid rgba(0,0,0,.2);
   border-radius: .3em;
   box-shadow: 0 1px white inset;
   text-align: center;
   text-shadow: 0 1px 1px black;
   color:white;
   font-weight: bold;
   cursor:pointer;
}
.x-grid-red{
	background-color: #FFEC8B;
}
.x-grid-green{
	background-color: #FFFFFF;
}
</style>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/echarts.js" charset="utf-8"></script>
<script  type="text/javascript">
var g_contextpath = '<%= request.getContextPath() %>';
</script>
<div id="div0" >
<odin:groupBox title="统计条件筛选">
<table style="width: 100%;">
	<tr>
		<td width="6%"></td>
		<%-- <tags:PublicTextIconEdit3 property="tj1" label="单位" codetype="orgTreeJsonData"></tags:PublicTextIconEdit3> --%>
		<odin:select2 property="tj1" label="单位：" multiSelect="false" onchange="loadStat()" ></odin:select2>
		<odin:select2 property="tj2" label="培训年度：" maxlength="4" onchange="loadStat()" multiSelect="false"></odin:select2>
		<odin:select2 property="personClass" label="职务层次：" onchange="loadStat()" codeType="TSZB09" value="1" multiSelect="false"></odin:select2>
		<%-- <td>
			<odin:hidden property="personClass" value="1"/>
			<input type="radio"  name="spp081" id="spp081" value="1" checked="checked" onclick="chooseClass(this.value)"/><label>处级及以上</label>
			<input type="radio"  name="spp081" id="spp082" value="2" onclick="chooseClass(this.value)"/><label>科级及以下</label>
		</td> --%>
		<!-- <td><div onclick="loadSata()" style="width:60px;" class="vueBtn">查 询</div></td> -->
	</tr>
</table>
</odin:groupBox>
<div>
	<table style="width: 100%">
		<tr>
			<!-- <td width="350px" align="center">
				<table>
					<tr>
						<th align="center">
							当前统计
						</th>
					</tr>
				</table>
			</td> -->
			<td align="center">
				<div id="myChart" style="height:475px;width:100%"></div>
			</td>
		</tr>
	</table>
</div>
</div>
<div id="div1" style="display: none">
	<div style="width: 100%">
		<table>
			<tr>
				<td width="99%"><span style="font-weight:bold;color:#F00">统计条件:</span><span id="tjtitle"></span></td>
				<td><div onclick="back()" style="width:68px;" class="vueBtn">返 回</div></td>
			</tr>
		</table>
		<!-- <div onclick="back()" style="width:60px;float: right;" class="vueBtn">返 回</div> -->
	</div>
	<div style="width: 470px;float: left;margin-top: -7px;">
	<odin:groupBox property="jg" title="单位信息">
	<div id="Orgdiv1" style="display: none">
		<odin:editgrid2 property="grid11" hasRightMenu="false" autoFill="true"  bbarId="pageToolBar" pageSize="20" url="/" >
			<odin:gridJsonDataModel>
				<odin:gridDataCol name="unitid" />
				<odin:gridDataCol name="unitname" />
				<odin:gridDataCol name="rs"/>
				<odin:gridDataCol name="zxs"/>
				<odin:gridDataCol name="jxs"/>
			</odin:gridJsonDataModel>
			<odin:gridColumnModel>
				<odin:gridRowNumColumn2></odin:gridRowNumColumn2>
				<odin:gridEditColumn2 dataIndex="unitid" width="10" header="单位主键" editor="text"  hidden="true" />
				<odin:gridEditColumn2 dataIndex="unitname" width="400" header="单位名称" editor="text"  edited="false" align="center"/>
				<odin:gridEditColumn2 dataIndex="rs" width="100" header="单位总人数" editor="text"  edited="false" align="center"/>
				<odin:gridEditColumn2 dataIndex="zxs" width="120" header="脱产培训总学时" editor="text" edited="false" align="center"/>
				<odin:gridEditColumn2 dataIndex="jxs" width="150" header="人均脱产培训学时数" editor="text" edited="false" align="center" renderer="handleData1" isLast="true"/>
			</odin:gridColumnModel>
		</odin:editgrid2>
	</div>
	<div id="Orgdiv2" style="display: none">
		<odin:editgrid2 property="grid12" hasRightMenu="false" autoFill="true"  bbarId="pageToolBar" pageSize="20"  url="/">
			<odin:gridJsonDataModel>
				<odin:gridDataCol name="unitid" />
				<odin:gridDataCol name="unitname" />
				<odin:gridDataCol name="rs"/>
				<odin:gridDataCol name="ctr"/>
				<odin:gridDataCol name="dxl"/>
			</odin:gridJsonDataModel>
			<odin:gridColumnModel>
				<odin:gridRowNumColumn2></odin:gridRowNumColumn2>
				<odin:gridEditColumn2 dataIndex="unitid" width="10" header="单位主键" editor="text"  hidden="true"/>
				<odin:gridEditColumn2 dataIndex="unitname" width="400" header="单位名称" editor="text"  edited="false" align="center"/>
				<odin:gridEditColumn2 dataIndex="rs" width="100" header="单位总人数" editor="text"  edited="false" align="center"/>
				<odin:gridEditColumn2 dataIndex="ctr" width="120" header="参加脱产培训人数" editor="text" edited="false" align="center"/>
				<odin:gridEditColumn2 dataIndex="dxl" width="152" header="脱产培训调训率(%)" editor="text" edited="false" align="center" renderer="handleData2" isLast="true"/>
			</odin:gridColumnModel>
		</odin:editgrid2>
	</div>
	<div id="Orgdiv3" style="display: none">
		<odin:editgrid2 property="grid13" hasRightMenu="false" autoFill="true"  bbarId="pageToolBar" pageSize="20" url="/">
			<odin:gridJsonDataModel>
				<odin:gridDataCol name="unitid" />
				<odin:gridDataCol name="unitname" />
				<odin:gridDataCol name="rs"/>
				<odin:gridDataCol name="cgr"/>
				<odin:gridDataCol name="cxl"/>
			</odin:gridJsonDataModel>
			<odin:gridColumnModel>
				<odin:gridRowNumColumn2></odin:gridRowNumColumn2>
				<odin:gridEditColumn2 dataIndex="unitid" width="10" header="单位主键" editor="text"  hidden="true"/>
				<odin:gridEditColumn2 dataIndex="unitname" width="400" header="单位名称" editor="text"  edited="false" align="center"/>
				<odin:gridEditColumn2 dataIndex="rs" width="100" header="单位总人数" editor="text"  edited="false" align="center"/>
				<odin:gridEditColumn2 dataIndex="cgr" width="120" header="参加干部培训人数" editor="text" edited="false" align="center"/>
				<odin:gridEditColumn2 dataIndex="cxl" width="150" header="干部培训参训率(%)" editor="text" edited="false" align="center" renderer="handleData3" isLast="true"/>
			</odin:gridColumnModel>
		</odin:editgrid2>
	</div>
	<div id="Orgdiv4" style="display: none">
		<odin:editgrid2 property="grid14" hasRightMenu="false" autoFill="true"  bbarId="pageToolBar" pageSize="20" url="/">
			<odin:gridJsonDataModel>
				<odin:gridDataCol name="unitid" />
				<odin:gridDataCol name="unitname" />
				<odin:gridDataCol name="rs"/>
				<odin:gridDataCol name="wps"/>
				<odin:gridDataCol name="rws"/>
			</odin:gridJsonDataModel>
			<odin:gridColumnModel>
				<odin:gridRowNumColumn2></odin:gridRowNumColumn2>
				<odin:gridEditColumn2 dataIndex="unitid" width="10" header="单位主键" editor="text"  hidden="true"/>
				<odin:gridEditColumn2 dataIndex="unitname" width="400" header="单位名称" editor="text"  edited="false" align="center"/>
				<odin:gridEditColumn2 dataIndex="rs" width="100" header="单位总人数" editor="text"  edited="false" align="center"/>
				<odin:gridEditColumn2 dataIndex="wps" width="120" header="网络培训总学时" editor="text" edited="false" align="center"/>
				<odin:gridEditColumn2 dataIndex="rws" width="150" header="人均网络培训学时数" editor="text" edited="false" align="center" renderer="handleData4" isLast="true"/>
			</odin:gridColumnModel>
		</odin:editgrid2>
	</div>
	</odin:groupBox>
	</div>
	<div style="width:45%;float: left; margin-left: 8px;margin-top: -7px;">
      <odin:groupBox title="人员信息">
      	<table class="x-form-item2" style="width: 100%;background-color: rgb(209,223,245);border-left: 1px solid rgb(153,187,232);border-top: 1px solid rgb(153,187,232);border-right: 1px solid rgb(153,187,232);">
			<tr>
				<td><div style="width: 5px"></div></td>
				<odin:textEdit property="seachName" label="姓名："  width="100" />
				<odin:select2 property="seachG11027" label="现任职务层次：" multiSelect="true" width="150"></odin:select2>
				<td align="right" style="width: 20%"><odin:button text="查询" property="personQuery" handler="PersonQuery"></odin:button></td>
			</tr>
		</table>
          <odin:editgrid2 property="grid2" hasRightMenu="false" topBarId="btnToolBar2" autoFill="true"  bbarId="pageToolBar" pageSize="20" url="/">
			<odin:gridJsonDataModel>
				<odin:gridDataCol name="a0184"/>
				<odin:gridDataCol name="a0101"/>
				<odin:gridDataCol name="a0104"/>
				<odin:gridDataCol name="a0192a"/>
				<odin:gridDataCol name="a1108"/>
				<odin:gridDataCol name="cz"/>
			</odin:gridJsonDataModel>
			<odin:gridColumnModel>
				<odin:gridRowNumColumn2></odin:gridRowNumColumn2>
				<odin:gridEditColumn2 dataIndex="a0184" width="10" header="人员身份证号" editor="text" hidden="true" edited="false" align="center"/>
				<odin:gridEditColumn2 dataIndex="a0101" width="90" header="姓名" editor="text" edited="false" align="center"/>
				<odin:gridEditColumn2 dataIndex="a0104" width="60" header="性别" editor="select" codeType="GB2261" edited="false" align="center"/>
				<odin:gridEditColumn2 dataIndex="a0192a" width="300" header="现工作单位及职务 " editor="text" edited="false" align="center"/>
				<odin:gridEditColumn2 dataIndex="a1108" width="100" header="获得学时数" editor="text" edited="false" align="center"/>
				<odin:gridEditColumn2 dataIndex="cz" width="120" header="操作" editor="text" edited="false" align="center" renderer="queryTrain" isLast="true"/>
			</odin:gridColumnModel>
		  </odin:editgrid2>
		</odin:groupBox>
	  </div>  
</div>
</html>
<script type="text/javascript">
Ext.onReady(function() {
	var viewSize = Ext.getBody().getViewSize();
	var grid13 = Ext.getCmp('grid13');
	grid13.setWidth(viewSize.width/1.953);
	grid13.setHeight(viewSize.height/1.2);
	var grid14 = Ext.getCmp('grid14');
	grid14.setWidth(viewSize.width/1.953);
	grid14.setHeight(viewSize.height/1.2);
	var grid12 = Ext.getCmp('grid12');
	grid12.setWidth(viewSize.width/1.953);
	grid12.setHeight(viewSize.height/1.2);
	var grid11 = Ext.getCmp('grid11');
	grid11.setWidth(viewSize.width/1.953);
	grid11.setHeight(viewSize.height/1.2);
	
	grid11.on('rowdblclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index);
		document.getElementById('unitid').value = rc.data.unitid;
		radow.doEvent("QueryData");
	});
	grid12.on('rowdblclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index);
		document.getElementById('unitid').value = rc.data.unitid;
		radow.doEvent("QueryData");
	});
	grid13.on('rowdblclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index);
		document.getElementById('unitid').value = rc.data.unitid;
		radow.doEvent("QueryData");
	});
	grid14.on('rowdblclick',function(gridobj,index,e){
		var rc = gridobj.getStore().getAt(index);
		document.getElementById('unitid').value = rc.data.unitid;
		radow.doEvent("QueryData");
	});
	
	var grid2 = Ext.getCmp('grid2');
	grid2.setHeight(viewSize.height/1.26);
	grid2.setWidth(viewSize.width/2.22);
	Ext.get('commForm').setWidth(viewSize.width);
});
Ext.onReady(function() {
	//Query();
});

function back(){
	document.getElementById('unitid').value = "";
	radow.doEvent("grid2.dogridquery");
	$("#div1").hide();
	$("#div0").show();
}
function chooseClass(v){
	if(v)
		$('#personClass').val(v);
	loadStat();
}
var indicators1 = [110,30,50,50];//处级以上
var indicators2 = [90,25,40,50];//科级以下
var myChart;
function Query(){
	var personClass = document.getElementById("personClass").value;
	var param1 = document.getElementById("param1").value;
	var param2 = document.getElementById("param2").value;
	var param3 = document.getElementById("param3").value;
	var param4 = document.getElementById("param4").value;
	if(personClass=='1'||personClass=='2'||personClass=='3'||personClass=='4'){
		indicators=indicators1;
	}else{
		indicators=indicators2;
	}
	option = {
		    color: ['#32CD32', '#FF0000'],
		    tooltip: {
		        trigger: 'axis',
		        axisPointer: {
		            type: 'shadow'
		        }
		    },
		    legend: {
		        data: ['实际培训量', '培训指标量']
		    },
		    calculable: true,
		    xAxis: [
		        {
		            type: 'category',
		            axisTick: {show: false},
		            axisLabel: {  
		           	    interval:0
		           	},
		            data: ['每年每单位人均脱产培训学时数', '每年每单位脱产培训调训率(%)', '每年每单位干部参训率(%)', '每人每单位网络培训学时数']
		        }
		    ],
		    yAxis: [
		        {
		            type: 'value'
		        }
		    ],
		    series: [
		        {
		            name: '实际培训量',
		            type: 'bar',
		            barGap: '15%',
		            barCategoryGap: 140,
		            label: '1',
		            data: [param1,param2,param3,param4]
		        },
		        {
		            name: '培训指标量',
		            type: 'bar',
		            label: '2',
		            data: indicators
		        }
		    ]
		};
	if (myChart != null && myChart != "" && myChart != undefined) {        
		myChart.dispose();
	}
	myChart = echarts.init(document.getElementById('myChart'));
	myChart.setOption(option);
	myChart.on('click', function(param) {
		$("#Orgdiv1").hide();
		$("#Orgdiv2").hide();
		$("#Orgdiv3").hide();
		$("#Orgdiv4").hide();
		radow.doEvent('initGrid2g11027');
		if(param.dataIndex==0){
			radow.doEvent('grid11.dogridquery');
			$("#Orgdiv1").show();
			document.getElementById("type").value = "1";
			document.getElementById("state").value = "1";
		}else if(param.dataIndex==1){
			radow.doEvent('grid12.dogridquery');
			$("#Orgdiv2").show();
			document.getElementById("type").value = "1";
			document.getElementById("state").value = "2";
		}else if(param.dataIndex==2){
			radow.doEvent('grid13.dogridquery');
			$("#Orgdiv3").show();
			document.getElementById("type").value = "2";
			document.getElementById("state").value = "3";
		}else{
			radow.doEvent('grid14.dogridquery');
			$("#Orgdiv4").show();
			document.getElementById("type").value = "3";
			document.getElementById("state").value = "4";
		}
		var personClass = document.getElementById("personClass").value;
		var tj1 = document.getElementById("tj1_combo").value;
		var tj2 = document.getElementById("tj2").value;
		var unitname = "";
		if(tj1==null||tj1==""){
			unitname="所有单位";
		}else{
			unitname=tj1+"单位下";
		}
		if(personClass==1){
			document.getElementById("tjtitle").innerText = tj2+'年度'+unitname+param.name+'(市管干部(正职))';
		}else if(personClass==2){
			document.getElementById("tjtitle").innerText = tj2+'年度'+unitname+param.name+'(市管干部(副职))';
		}else if(personClass==3){
			document.getElementById("tjtitle").innerText = tj2+'年度'+unitname+param.name+'(处级干部(正职))';
		}else if(personClass==4){
			document.getElementById("tjtitle").innerText = tj2+'年度'+unitname+param.name+'(处级干部(副职))';
		}else{
			document.getElementById("tjtitle").innerText = tj2+'年度'+unitname+param.name+'(科级及以下)';
		}
		$("#div0").hide();
		$("#div1").show();
		/* $("#Orgdiv1").show();
		$("#div0").hide();
		$("#div1").show(); */
	});
}
function queryTrain(value, params, record, rowIndex, colIndex, ds){
	return "<font color=blue><a style='cursor:pointer;' onclick=\"TrainQuery('"+record.get("a0101")+","+record.get("a0184")+"');\">"+"查看培训详情"+"</a></font>";
}

function TrainQuery(param){
	param = param+","+document.getElementById("tj2").value;
	$h.openPageModeWin('trainWin','pages.train.TrainMessage','培训详情',900,700,param,g_contextpath);
}
function handleData1(value, params, record, rowIndex, colIndex, ds){
	if(value.substr(0, 1)=="."){
		value = "0"+value;
	}
	return value;
	/* var flag;
	if(document.getElementById("personClass").value=='1'){
		flag = 110;
	}else{
		flag = 90;
	}
	if(value>=flag){
		return "<font color=green>"+value+"</font>";
	}
	return "<font color=red>"+value+"</font>"; */
}

function handleData2(value, params, record, rowIndex, colIndex, ds){
	if(value.substr(0, 1)=="."){
		value = "0"+value;
	}
	return value;
	/* var flag;
	if(document.getElementById("personClass").value=='1'){
		flag = 30;
	}else{
		flag = 25;
	}
	if(value>=flag){
		return "<font color=green>"+value+"</font>";
	}
	return "<font color=red>"+value+"</font>"; */
}
function handleData3(value, params, record, rowIndex, colIndex, ds){
	if(value.substr(0, 1)=="."){
		value = "0"+value;
	}
	return value;
	/* var flag;
	if(document.getElementById("personClass").value=='1'){
		flag = 50;
	}else{
		flag = 40;
	}
	if(value>=flag){
		return "<font color=green>"+value+"</font>";
	}
	return "<font color=red>"+value+"</font>"; */
}
function handleData4(value, params, record, rowIndex, colIndex, ds){
	if(value.substr(0, 1)=="."){
		value = "0"+value;
	}
	return value;
	/* var flag = 50;
	if(value>=flag){
		return "<font color=green>"+value+"</font>";
	}
	return "<font color=red>"+value+"</font>"; */
}
function changeRowClass(record, rowIndex, rowParams, store){
	var flag;
	var state = document.getElementById("state").value;
	if(state=="1"){
		if(document.getElementById("personClass").value=='1'||document.getElementById("personClass").value=='2'||document.getElementById("personClass").value=='3'||document.getElementById("personClass").value=='4'){
			flag = 110;
		}else{
			flag = 90;
		}
		if(record.data.jxs<flag){
			return "x-grid-red";
		}
		if(record.data.a1108<flag){
			return "x-grid-red";
		}
		return "x-grid-green";
	}else if(state=="2"){
		if(document.getElementById("personClass").value=='1'||document.getElementById("personClass").value=='2'||document.getElementById("personClass").value=='3'||document.getElementById("personClass").value=='4'){
			flag = 30;
		}else{
			flag = 25;
		}
		if(record.data.dxl<flag){
			return "x-grid-red";
		}
		return "x-grid-green";
	}else if(state=="3"){
		if(document.getElementById("personClass").value=='1'||document.getElementById("personClass").value=='2'||document.getElementById("personClass").value=='3'||document.getElementById("personClass").value=='4'){
			flag = 50;
		}else{
			flag = 40;
		}
		if(record.data.cxl<flag){
			return "x-grid-red";
		}
		return "x-grid-green";
	}else{
		flag = 50;
		if(record.data.rws<flag){
			return "x-grid-red";
		}
		if(record.data.a1108<flag){
			return "x-grid-red";
		}
		return "x-grid-green";
	}
}
function loadStat(){
	radow.doEvent("statData");
}
function PersonQuery(){
	radow.doEvent("grid2.dogridquery");
}
</script>