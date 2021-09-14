<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.hzb.CJNQGBTJ"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<html lang="en">
<head>
<odin:head />
<odin:MDParam></odin:MDParam>
<odin:hidden property="docpath" />
<script src="<%=request.getContextPath()%>/basejs/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>


<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<iframe  id="iframe_expBZYP" style="display: none;" src=""></iframe>
<%
String ctxPath = request.getContextPath(); 
String subWinId = request.getParameter("subWinId");
%>
<script type="text/javascript">
function ajaxSubmit(radowEvent,parm,callback){
  if(parm){
  }else{
    parm = {};
  }
  Ext.Ajax.request({
    method: 'POST',
    //form:'rmbform',
        async: true,
        params : parm,
        timeout :300000,//按毫秒计算
    url: "<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.sysorg.org.hzb.ExpExcel&eventNames="+radowEvent,
    success: function(resData){
      var cfg = Ext.util.JSON.decode(resData.responseText);
      //alert(cfg.messageCode)
      if(0==cfg.messageCode){
                Ext.Msg.hide();

                if(cfg.elementsScript.indexOf("\n")>0){
          cfg.elementsScript = cfg.elementsScript.replace(/\r/gi,"");
          cfg.elementsScript = cfg.elementsScript.replace(/\n/gi,"\\n");
        }

        //console.log(cfg.elementsScript);

        eval(cfg.elementsScript);
        //var realParent = parent.Ext.getCmp("setFields").initialConfig.thisWin;
        //parent.document.location.reload();
        //alert(cfg.elementsScript);
        //realParent.resetCM(cfg.elementsScript);
        //parent.Ext.getCmp("setFields").close();
        //console.log(cfg.mainMessage);

        if("操作成功！"!=cfg.mainMessage){
          Ext.Msg.hide();
          Ext.Msg.alert('系统提示:',cfg.mainMessage);

        }else{
//           Ext.Msg.hide();
        }
      }else{
        //Ext.Msg.hide();

        /* if(cfg.mainMessage.indexOf("<br/>")>0){

          $h.alert('系统提示',cfg.mainMessage,null,380);
          return;
        } */

        if("操作成功！"!=cfg.mainMessage){
          Ext.Msg.hide();
          Ext.Msg.alert('系统提示:',cfg.mainMessage);
        }else{
          Ext.Msg.hide();
        }
      }
      if(!!callback){
        callback();
      }
    },
    failure : function(res, options){
      Ext.Msg.hide();
      alert("网络异常！");
    }
  });
}
</script>
<script type="text/javascript">
var subWinId = '<%=subWinId%>';
var realParent = parent.Ext.getCmp(subWinId).initialConfig.thisWin;
var data = <%=CJNQGBTJ.expData()%>;
$(function(){
	//alert(data.length);
	var ROWID={};
 	$.each(data, function (i,item) {
		//console.log(item['zzsp'])
		var tr = $('<tr><td></td>'+
		'<td class="th1"></td>'+
		'<td class="th1"></td><td class="th1"></td><td class="th1"></td><td class="th1"></td><td class="th1"></td>'+
		'<td class="th1"></td><td class="th1"></td><td class="th1"></td><td class="th1"></td><td class="th1"></td></tr>');
		var tds = $("td", tr);
		SetTDtext(tds[0],item["dw"]);
		SetTDtext(tds[1],item["zs"]);
		SetTDtext(tds[2],item["zcjgb"]);
		SetTDtext(tds[3],item["zc1975"]);
		SetTDtext(tds[4],item["zc1980"]);
		SetTDtext(tds[5],item["zcngb"]);
		SetTDtext(tds[6],item["zcdwgb"]);
		SetTDtext(tds[7],item["fcjgb"]);
		SetTDtext(tds[8],item["fc1980"]);
		SetTDtext(tds[9],item["fc1985"]);
		SetTDtext(tds[10],item["fcngb"]);
		SetTDtext(tds[11],item["fcdwgb"]);
		ROWID[i]=item["b0111"];
		if(item["dw"]=='杭州市管干部'){
			$('#coordTable').append(tr);
		}else if(item["dw"]=='杭州'){
			$('#coordTable2').append(tr);
		}
		
	}); 
	/*$('td:nth-child(5),td:nth-child(6),td:nth-child(7)','#coordTable2 tr:gt(0)').each(function (i, item) {
        if($(this).text()<0){
        	$(this).addClass('colorLight');
        }
		
    });*/
	
	$('td:nth-child(2),td:nth-child(3),td:nth-child(4),td:nth-child(5),td:nth-child(6),td:nth-child(7),'+
		'td:nth-child(8),td:nth-child(9),td:nth-child(10),td:nth-child(11),td:nth-child(12)'
			,'#coordTable tr').each(function (i, item) {
        $(this).addClass('pointer');
        $(this).on('click',function(){
        	var p = {maximizable:false,resizable:false};
        	p['colIndex'] = $(this).index();
        	//alert($(this).parent().index())
        	p['query_type']="CJNQGBTJ1";
        	openMate(p);
        })
    });
	$('td:nth-child(2),td:nth-child(3),td:nth-child(4),td:nth-child(5),td:nth-child(6),td:nth-child(7),'+
			'td:nth-child(8),td:nth-child(9),td:nth-child(10),td:nth-child(11),td:nth-child(12)'
				,'#coordTable2 tr').each(function (i, item) {
	        $(this).addClass('pointer');
	        $(this).on('click',function(){
	        	var p = {maximizable:false,resizable:false};
	        	p['colIndex'] = $(this).index();
	        	//alert($(this).parent().index())
	        	p['query_type']="CJNQGBTJ2";
	        	openMate(p);
	        })
	    });
});
<%-- function expExcel(){
	var dataArray = [];
	var tr = $("#selectable2 tr");
	
	$.each(tr,function (i, item){
		var tdArray = [];
		var td = $("td",$(this));
	 	$.each(td,function (ti, titem){
	 		tdArray.push($(this).text());
	 		});
	 	if(tdArray.length>0){
	 		dataArray.push(tdArray);
	 	}
	});
	var tdEndArray = [];
	var trend = $("#selectable3 tr");
	$.each(trend,function(iend,itemend){
		var tdend = $("th",$(this));
		$.each(tdend,function (tiend, titemend){
			tdEndArray.push($(this).text());
	 		});
		if(tdEndArray.length>0){
	 		dataArray.push(tdEndArray);
	 	}
	});
	
	
	//alert(dataArray.length);
	if(dataArray.length>0){
	    ajaxSubmit('expExcel',{"dataArray":Ext.encode(dataArray),"excelname":"区、县（市）党政领导干部情况统计表"});
	}
}

function downloadByUUID(){
	var downloadUUID = document.getElementById('docpath').value;
	$('#iframe_expBZYP').attr('src','<%= request.getContextPath() %>/PublishFileServlet?method=downloadFile&uuid='+downloadUUID);
	return false
} --%>

function openMate(p){///hzb_hz/WebContent/pages/sysorg/hzb/Mate.jsp
	$h.openWin('mateWin','pages.sysorg.org.hzb.Mate','人员列表',700,500,'','<%=request.getContextPath()%>',null,p,true);
}
function SetTDtext(td,v) {
  $(td).html((v==""||v==null||v=="null"||v=="0"||v=="0%")?" ":v.replace(/\n/g,"<br/>"));
}
</script>
<style type="text/css">
.pointer{
	cursor: pointer;
}
.colorLight{
	color: red;
}
th{
	border:1px solid;
	background: #CAE8EA ; 
	text-align: center;
	height:50px;
}
th,td{
	border-left: 1px solid #C1DAD7;
	border-bottom: 1px solid #C1DAD7;
	font-family: 宋体;
	font-weight: normal;
	height:50px;
}
td{
	text-align: center;
}
.textleft{
	text-align: center;
}
.title{
	font-family: 方正小标宋简体;
	font-size: 28px;
	font-weight: normal;
}
.borderRight{
	border-right: 1px solid #C1DAD7;
}
.borderTop{
	border-top: 1px solid #C1DAD7;
}
#selectable{
	margin-left:23px;
}
#selectable2{
	overflow-y:auto;
	height:315px;
	margin-left:23px;
}
#selectable3{
	overflow:auto 0;
	margin-left:23px;
}

table{
	width: 1350px;
}
.th1{width: 8%;}.th2{width: 20%;}.th3{width: 20%;}.th4{width: 20%;}.th5{width: 20%;}
.th5{width: 40%;}.th6{width: 20%;text-align: center;}
</style>
</head>
<body>


	<div id="selectable">
	
	<table id="coordTable" cellspacing="0" width="100%" >
		<tr>
		    <td colspan="12" class="borderTop title borderRight">设区市县处级年轻干部有关情况统计</td>
		</tr>
		<tr>
			<th rowspan="4" >单位</th>
			<th colspan="11" >处级领导干部</th>
		</tr>
		<tr>
			<th rowspan="3" class="th1" style="border-top:none">总数</th>
			<th colspan="5" class="th2">正处级干部</th>
			<th colspan="5" class="th2">副处级干部</th>
		</tr>
		<tr>
			<th rowspan="2" class="th1" style="border-top:none"> </th>
			<th colspan="2" >1975年5月后出生</th>
			<th rowspan="2" class="th1">女干部</th>
			<th rowspan="2" class="th1">党外干部</th>
			<th rowspan="2" class="th1" style="border-top:none"> </th>
			<th colspan="2" >1980年3月后出生</th>
			<th rowspan="2" class="th1">女干部</th>
			<th rowspan="2" class="th1">党外干部</th>
		</tr>
		<tr>
		    <th class="th1" style="border-top:none"> </th>
		    <th class="th1" >1980年3月后出生</th>
		    <th class="th1" style="border-top:none"> </th>
		    <th class="th1" >1985年3月后出生</th>
		</tr>
	</table>
</div>

<div  id="selectable2">
	<div>
	<table id="coordTable2" cellspacing="0" width="100%" >
		
	</table>
	</div>
</div>
<!-- <div id="selectable3">
	<table  cellspacing="0" width="100%" >
		<tr>
			<th  class="th6" >合计</th>
			<th id="hj1"  class="th2"></th>
			<th id="hj2"  class="th3"></th>
			<th id="hj3"  class="th4"></th>
			<th id="hj4"  class="th4"></th>
		</tr>
	</table>
</div> -->


<br/>

<%-- <div align="right"><img id="expSelect" onclick="expExcel()" src="<%=request.getContextPath()%>/images/tpbj/exp_excel.png" width="150px" 
style="cursor:pointer;margin-right:25px"/></div> --%>
</body>
</html>
