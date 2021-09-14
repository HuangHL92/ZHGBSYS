<%@page import="com.insigma.odin.framework.persistence.HBUtil"%>
<%@page import="java.util.List"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>	

<html>
<head>
<title>全国公务员管理信息系统</title>
<script type="text/javascript">
var g_contextpath = '<%=request.getContextPath()%>';

</script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/ext/adapter/ext/ext-base.js"></script>
<script src="<%=request.getContextPath()%>/basejs/ext/ext-all.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/DataAnalysis/css/css.css">
<script src="<%=request.getContextPath()%>/pages/DataAnalysis/js/jquery1.7.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/echarts/echarts.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/fxyp/js/ctsOptions.js" charset="UTF-8"></script>
<style type="text/css">
.slayout{
	height: 47%;width: 48%;float: left;border: 1px solid #c0d1e3;
	margin: 5px 5px 5px 5px;
}
.top_btn_style{
	display:inline;
	border-radius:5px;
	cursor:pointer;
	margin-left:7px;
	height:20px;
	line-height:20px;
	font-size:14px;
	color:#fff;
	background-color:#3680C9;
	text-align: center;
}
</style>
</head>

<body style="overflow:hidden;">

<div class="top_btn_style" 
 style="background-color:#F08000; font-size:14px;
 width: 60px;" onclick="submitForm()">当前机构
</div>




<span class="dqjg" style="padding-right: 20px;padding-left: 10px;">
 <select id="dwSel" onchange="changeV(this.options[this.options.selectedIndex].value)">
	<%
	List<Object[]> tpdw= HBUtil.getHBSession().createSQLQuery("select b0111,b0101,b01id from b01 c "+
			" where b01id='"+request.getParameter("b01id")+"' or b01id in (select b01id from fxyp where mntp00='"+request.getParameter("mntp00")+"') " + 
			"order by c.b0269").list();
	for(Object[] o : tpdw){
		%>
		<option value ="<%=o[0]%>"><%=o[1]%></option>
		<%
	}
	
	%>
 </select>

</span>
<div class="top_btn_style" 
 style="background-color:#F08000; font-size:14px;
 width: 60px;" onclick="$('#mntp05').val('2');submitForm('1');">查看区县市全部
</div>
<div class="" style="height: 96%">
    <div class="chart-box slayout" style="">
        <div class="chart-title">年龄结构
       <!--  <span  style="margin-left: 60px;color: #d14a61;">调配前平均年龄：<span class="pjnl2"></span>岁</span>
        <span  style="margin-left: 110px;color: #5EB3F3;">调配后平均年龄：<span class="pjnl"></span>岁</span> -->
        
        </div>
        
        <div class="chart" id="chart1-2"></div>
    </div>
    <div class="chart-box slayout" style="">
        <div class="chart-title">学历结构</div>
        
        <div class="chart" id="chart2-2"></div>
    </div>
    <div class="chart-box slayout" style="position: relative;">
        <div class="chart-title">结构性干部</div>
        
        <div  style="position: absolute; font-size:14px; top:20px; left: 32%;color: #5EB3F3;">
     		  <br/><br/>调配前<br/> 党外干部：<span class="dwgb2"></span>人<br/><br/><br/><br/><br/>调配后<br/> 党外干部：<span class="dwgb"></span>人
        </div>
        
        <div  style="position: absolute; font-size:14px; top:20px; left: 77%;color: #5EB3F3;">
      		     <br/><br/>调配前 <br/>女干部：<span class="ngb2"></span>人<br/><br/><br/><br/><br/>调配后 <br/>女干部：<span class="ngb"></span>人
        </div>
        
        <div class="chart" id="chart3-2"></div>
    </div>
    
    <div class="chart-box slayout" style="">
        <div class="chart-title">专业类型</div>
        
        <div class="chart" id="chart4-2"></div>
    </div>
</div>

<form name="cform" id="cform" action="">

<odin:hidden property="mntp00"/>
<odin:hidden property="mntp05"/>
<odin:hidden property="b0111"/>
<odin:hidden property="b01id"/>
</form>



</body>
<script type="text/javascript">
var realParent = parent.Ext.getCmp("structuralAnalysis").initialConfig.thisWin;

function changeV(v){
	if(v.length==15&&v.substring(0,11)=='001.001.004'){
		$('#mntp05').val('2')
	}else{
		$('#mntp05').val('1')
	}
	
	$('#b0111').val(v);
	submitForm();
}
function submitForm(all){
	
	
	
	
	
	Ext.Ajax.request({
		method: 'POST',
        async: true,
        form: 'cform',
        params : {all:all||""},
		//timeout :30000,//按毫秒计算
		url: "<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.fxyp.Kqzsjzhj&eventNames=EChartsInfo",
		success: function(resData){
			//console.log(resData.responseText)
			var jsonDataAll = eval("("+resData.responseText+")");
			var jsonData = jsonDataAll['data1'];
			var jsonData2 = jsonDataAll['data2'];
			setNumY(jsonData,jsonData2);
			//console.log((jsonData['female']||0) / (jsonData['totalCount']))
			//$('.pjnl').html((jsonData['ageCount']/jsonData['totalCount']).toFixed(2));
			//$('.pjnl2').html((jsonData2['ageCount']/jsonData2['totalCount']).toFixed(2));
			$('.dwgb').html(jsonData['noZGParty']);
			$('.ngb').html(jsonData['female']);
			$('.dwgb2').html(jsonData2['noZGParty']);
			$('.ngb2').html(jsonData2['female']);
				
			setchart2(jsonData,jsonData2);
			setchart3(jsonData,jsonData2);
			setchart4(jsonData,jsonData2);
		},
		failure : function(res, options){ 
			Ext.Msg.hide();
			alert("网络异常！");
		}  
	});
}



$(function() {
	
	//有单位id
	if(realParent.$('#b0111').length==1){
		
		$('#mntp00').val(realParent.$('#mntp00').val())
		$('#mntp05').val(realParent.$('#mntp05').val())
		$('#b0111').val(realParent.$('#b0111').val())
		$('#b01id').val(realParent.$('#b01id').val())
		$('#dwSel').val($('#b0111').val());
		/* var idx = 0;
		var store = realParent.Ext.getCmp('noticeSetgrid').getStore();
		for(;idx<store.getCount();idx++){
			var rc = store.getAt(idx);
			if(rc.data.b01id==$('#b01id').val()){
				//$('.dqjg').html(rc.data.jgmc);
				$('#dwSel').val(rc.data.b0111);
				break;
			}
		} */
	}else{
		$('#mntp00').val(realParent.$('#mntp00').val())
		$('#mntp05').val('2')
		$('#dwSel').val("001.001.004.001");
		$('#b0111').val("001.001.004.001");
	}
	submitForm();
	
	
	
	
}); 



</script>


</html>
