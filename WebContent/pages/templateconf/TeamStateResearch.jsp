<%@ page language="java" contentType="text/html; charset=GBK"
    pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>班子状态调研</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script src="<sicp3:rewrite forward='md5'/>"> </script>
<script src="<sicp3:rewrite forward='globals'/>"> </script>
<script type="text/javascript"src="<%=request.getContextPath()%>/basejs/ext/adapter/ext/ext-base.js"></script>
<script src="<%=request.getContextPath()%>/basejs/ext/ext-all.js"> </script>
<script src="<%=request.getContextPath()%>/basejs/odin.js"> </script>
<script src="<%=request.getContextPath()%>/js/echarts.js"> </script>

<script src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js" type="text/javascript"></script>
<script type="text/javascript">
Ext.onReady(function(){
	//radow.doEvent("bzyp",1);//整体状况
	// 基于准备好的dom，初始化echarts实例
	
});


</script>
<style type="text/css">
	/* .main{
	    
	    background-color: #fff;
	    border-radius: 20px;
	    width: 800px;
	    height: 450px;
	    margin: auto;
	    position: absolute;
	    top: 20;
	    left: 20%;
	    bottom: 0;
	} */
	
	.font-tab {
	font-family: "黑体";
	font-size: 16px;
}
.inputtable{border-collapse:collapse;}
.inputtable th{border-collapse:collapse;background:#EFEFEF;width:100px;border:1px dotted #CCCCCC;
	               font-weight:bold;text-align:center;padding:3px;}
.inputtable tr td{border-collapse:collapse;border:1px dotted #CCCCCC;width:100px;padding:3px;}
</style>
<script type="text/javascript">

function comprevalue(sumcount){
    var count = sumcount.split(",");

	document.getElementById("yhong").innerText=count[0];
	document.getElementById("fyhong").innerText=count[1];
	document.getElementById("huang").innerText=count[2];
	document.getElementById("lv").innerText=count[3];
	document.getElementById("wei").innerText=count[4];
	
	var myChart = echarts.init(document.getElementById('div2'));
	
	//app.title = '环形图';

	option = {
		   title : {
			   text: '班子状态调研',
		       left: 'center'
		   },
		   tooltip : {
		       trigger: 'axis'
		   },
		   /* legend: {
		       data:['蒸发量']
		   }, */
		   toolbox: {
		       show : true/* ,
		       feature : {
		    	   dataView : {show: true, readOnly: false},
		           magicType : {show: true, type: ['line', 'bar']},
		           restore : {show: true},
		           saveAsImage : {show: true}
		       } */
		   },
		   calculable : true,
		   xAxis : [
		       {
		           type : 'category',
		           data : ['红(一票否决)','红','黄','绿','未配置']
		       }
		   ],
		   yAxis : [
		       {
		           type : 'value'
		       }
		   ],
		   series : [
		       {
		           name:'数量',
		           type:'bar',
		           label: {
		               normal: {
		                   show: true,
		                   position: 'top'
		               }
		           },
		           data:[ {value:count[0],name: '红(一票否决)',itemStyle: {color:'Red'}},
		                  {value:count[1],name: '红',itemStyle: {color:'OrangeRed'}},
		                  {value:count[2], name: '黄',itemStyle: {color:'Gold'}},
		                  {value:count[3], name: '绿',itemStyle: {color:'green'}},
		                  {value:count[4], name: '未配置',itemStyle: {color:'rgb(210,223,242)'}}]
		       }
		   ]
	};

	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);
	
	/* myChart.on('click', function (params) { 
        //param具体包含的参数见 https://blog.csdn.net/allenjay11/article/details/76033232
        console.log(option.data[params.dataIndex]);console.log(params.value);
          //updatePage(option.data[params.dataIndex],params.value);
          //refresh();
    }); */
}

function fanc(type){
	$h.openPageModeWin('troupeDetail','pages.templateconf.TroupeDetail','班子状态详情',1350,1100,type,'<%=request.getContextPath()%>',window);
}

function refresh(){             
    
    //局部刷新series内容
    //此处没有用常用的刷新div等方法，而是直接改变了option的值，然后重新赋值给myChart
   
    //简化方法，调用getSeriesData更新数据。
    option.series.data = getSeriesData();
    
    myChart.setOption(option);
}

function dataTb(){
	ShowCellCover("start","温馨提示：","正在同步数据，可能时间较长，请稍后...");
	
	 Ext.Ajax.request({
			method: 'POST',
	        async: true,
	        params : {},
	        timeout :300000,//按毫秒计算
			url: "<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.templateconf.TeamStateResearch&eventNames=dataTb",
			success: function(resData){
				var cfg = Ext.util.JSON.decode(resData.responseText);
				if(0==cfg.messageCode){
					Ext.Msg.hide();	
					
					if(cfg.elementsScript.indexOf("\n")>0){
						cfg.elementsScript = cfg.elementsScript.replace(/\r/gi,"");
						cfg.elementsScript = cfg.elementsScript.replace(/\n/gi,"\\n");
					}
					
					eval(cfg.elementsScript);
					Ext.Msg.alert("温馨提示","同步成功！");
					radow.doEvent("initX");
				}else{
					Ext.Msg.hide();	
					
					if(cfg.mainMessage.indexOf("<br/>")>0){
						Ext.Msg.alert('系统提示',cfg.mainMessage,null,380);
						return;
					}
					Ext.Msg.alert("温馨提示","同步失败！");
				}
			},
			failure : function(res, options){ 
				Ext.Msg.hide();
				odin.alert("网络异常！");
			}  
		});
}

function ShowCellCover(elementId, titles, msgs)
{	
	Ext.MessageBox.buttonText.ok = "关闭";
	if(elementId.indexOf("start") != -1){
	
		Ext.MessageBox.show({
			title:titles,
			msg:msgs,
			width:300,
	        height:300,
			closable:false,
		//	buttons: Ext.MessageBox.OK,		
			modal:true,
			progress:true,
			wait:true,
			animEl: 'elId',
			increment:5, 
			waitConfig: {interval:150}
			//,icon:Ext.MessageBox.INFO        
		});
	}else if(elementId.indexOf("success") != -1){
			Ext.MessageBox.confirm("系统提示", msgs, function(but) {  
				
			}); 
	}else if(elementId.indexOf("failure") != -1){
			Ext.MessageBox.show({
				title:titles,
				msg:msgs,
				width:300,
				modal:true,
		        height:300,
				closable:true,
				//icon:Ext.MessageBox.INFO,
				buttons: Ext.MessageBox.OK		
			});
			/*
			setTimeout(function(){
					Ext.MessageBox.hide();
			}, 2000);
			*/
	}else {
			Ext.MessageBox.show({
				title:titles,
				msg:msgs,
				width:300,
				modal:true,
		        height:300,
				closable:true,
				//icon:Ext.MessageBox.INFO,
				buttons: Ext.MessageBox.OK		
			});
		}
}
</script>
</head>
<input type="hidden" name="flag" id="flag"/> 
<body>
	
		<div id="div1" style="float: left;margin-left:1%">
		<table>
			<tr><td style="height: 20px"><td></tr>
		</table>
		<table class="inputtable"  style="width:280px;text-align:center;margin:0 auto;border-bottom:0px;margin-left: 100px;" >
			<tr>
				<td height=80px; style='width:50%;text-align: center;background-color: rgb(239,239,239)'><font class="font-tab">类型</font></td>
				<td style='width:25%;text-align: center;background-color: rgb(239,239,239)'><font class="font-tab">数量</font></td>
			</tr>
			<tr>
				<td height=80px; style="width:50%;text-align: center;background-color: red"><font class="font-tab">红<br/>（一票否决）</font></td>
				<td><a href='javascript:void(0)'><span id="yhong" class="css1" " onclick="fanc(1)"></a></td>
			</tr>
			<tr>
				<td height=80px; style="width:50%;background-color:OrangeRed;text-align: center;"><font class="font-tab">红<br/>（非一票否决）</font></td>
				<td><a href='javascript:void(0)'><span id="fyhong" class="css1" " onclick="fanc(2)"></a></td>
			</tr>
			<tr>
				<td height=80px; style="width:50%;background-color:yellow;text-align: center;"><font class="font-tab">黄</font></td>
				<td><a href='javascript:void(0)'><span id="huang" class="css1" " onclick="fanc(3)"></a></td>
			</tr>
			<tr>
				<td height=80px; style="width:50%;background-color:green;text-align: center;"><font class="font-tab">绿</font></td>
				<td><a href='javascript:void(0)'><span id="lv" class="css1" " onclick="fanc(4)"></a></td>
			</tr>
			<tr>
				<td height=80px; style="width:50%;background-color:rgb(210,223,242);text-align: center;"><font class="font-tab">未配置</font></td>
				<td><a href='javascript:void(0)'><span id="wei" class="css1" " onclick="fanc(5)"></a></td>
			</tr>
			
        </table>
        
		</div>
        
    <div id="div2" style="width:880px;height:520px;text-align:center;float: right;margin-right: 3%;margin-top: 1%">
        	<div style="float: right;"><odin:button text="数据刷新" handler="dataTb"></odin:button></div>
    </div>
    
</body>
</html>