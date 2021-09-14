<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.hzb.QGGBDWQK"%>
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
var data = <%=QGGBDWQK.expData()%>;
$(function(){
	//alert(data.length);
	var ROWID={};
/* 	var hj1=0;
	var hj2=0;
	var hj3=0;
	var hj4=0;
	var hj5=0;
	var hj6=0;
	var hj7=0;
	var hj8=0;
	var hj9=0;
	var hj10=0; */
	$.each(data, function (i,item) {
		//console.log(item['zzsp'])
		var tr = 
		//时间
		$('<tr><td class="th3"></td>'+
		//调配干部人次
		'<td class="th2"></td>'+
		//提拔正职
		'<td class="th2"></td><td class="th2"></td><td class="th2"></td><td class="th2"></td><td class="th2"></td><td class="th2"></td>'+
		//平职交流正职
		'<td class="th2"></td><td class="th2"></td><td class="th2"></td><td class="th2"></td><td class="th2"></td><td class="th2"></td>'+
		//提拔副职
		'<td class="th2"></td><td class="th3"></td><td class="th2"></td><td class="th2"></td><td class="th2"></td><td class="th2"></td>'+
		//平职交流副职
		'<td class="th2"></td><td class="th2"></td><td class="th2"></td><td class="th2"></td><td class="th2"></td><td class="th2"></td><td class="th2"></td>'+
		//晋升非领导职务
		'<td class="th2"></td><td class="th2">'+
		//副师军转安置
		'<td class="th2"></td>'+
		//退休
		'<td class="th2"></td>'+
		//辞职
		'<td class="th2"></td>'+
		//试用期转正
		'<td class="th2"></td>'+
		//其他工作原因正常任免
		'<td class="th2"></td>'+
		//党委（党组）成员
		'<td class="th2"></td><td class="th2"></td>'+
		//组织人事处长
		'<td class="th2"></td><td class="th2"></td>'+
		//挂兼职
		'<td class="th2"></td>'+
		//双管单位征求意见
		'<td class="th2"></td>'+
		//破格提拔
		'<td class="th2"></td>'+
		//党组改党委
		'<td class="th2"></td>'+
		//班子组建和换届
		'<td class="th2"></td>'+
		//退二线
		'<td class="th2"></td>'+
		//因身体等原因改任非领导职务、免职
		'<td class="th2"></td>'+
		//违纪等原因免职
		'<td class="th2"></td>'+
		//其他原因不适宜担任现职调整
		'<td class="th2"></td>'+
		//其他
		'<td class="th2"></td><td class="th2"></td>'+
		'</tr>');
		var tds = $("td", tr);
		//时间
		SetTDtext(tds[0],item["time"]);
		//调配干部人次
		SetTDtext(tds[1],item["tpgbrc"]);
		//提拔正职
		SetTDtext(tds[2],item["tbzzxj"]);
		SetTDtext(tds[3],item["nb1"]);
		SetTDtext(tds[4],item["jldqtsz1"]);
		SetTDtext(tds[5],item["lzqx1"]);
		SetTDtext(tds[6],item["lzssgq1"]);
		SetTDtext(tds[7],item["qt1"]);
		//平职交流正职
		SetTDtext(tds[8],item["jlzzxj"]);
		SetTDtext(tds[9],item["nb2"]);
		SetTDtext(tds[10],item["jldqtsz2"]);
		SetTDtext(tds[11],item["lzqx2"]);
		SetTDtext(tds[12],item["lzssgq2"]);
		SetTDtext(tds[13],item["qt2"]);
		//提拔副职
		SetTDtext(tds[14],item["tbfzxj"]);
		SetTDtext(tds[15],item["nb3"]);
		SetTDtext(tds[16],item["jldqtsz3"]);
		SetTDtext(tds[17],item["lzqx3"]);
		SetTDtext(tds[18],item["lzssgq3"]);
		SetTDtext(tds[19],item["qt3"]);
		//平职交流副职
		SetTDtext(tds[20],item["jlfzxj"]);
		SetTDtext(tds[21],item["bdwybzg"]);
		SetTDtext(tds[22],item["zrzygw"]);
		SetTDtext(tds[23],item["szdwj"]);
		SetTDtext(tds[24],item["cqx"]);
		SetTDtext(tds[25],item["cssqsy"]);
		SetTDtext(tds[26],item["qt4"]);
		//晋升非领导职务
		SetTDtext(tds[27],item["xsy"]);
		SetTDtext(tds[28],item["fxsy"]);
		//副师军转安置
		SetTDtext(tds[29],item["fsjzaz"]);
		//退休
		SetTDtext(tds[30],item["tx"]);
		//辞职
	    SetTDtext(tds[31],item["cz"]);
	    //试用期转正
		SetTDtext(tds[32],item["syqzz"]);
		//其他工作原因正常任免
		SetTDtext(tds[33],item["qtgzyyzcrm"]);
		//党委（党组）成员
		SetTDtext(tds[34],item["r1"]);
		SetTDtext(tds[35],item["m1"]);
		//组织人事处长
		SetTDtext(tds[36],item["r2"]);
		SetTDtext(tds[37],item["m2"]);
		//挂兼职
		SetTDtext(tds[38],item["gjz"]); 
		//双管单位征求意见
		SetTDtext(tds[39],item["sgdwzqyj"]); 
		
		SetTDtext(tds[40],' '); 
		SetTDtext(tds[41],' '); 
		SetTDtext(tds[42],' '); 
		SetTDtext(tds[43],' '); 
		SetTDtext(tds[44],' '); 
		SetTDtext(tds[45],' '); 
		SetTDtext(tds[46],' '); 
		SetTDtext(tds[47],' '); 
		SetTDtext(tds[48],' '); 
		
		ROWID[i]=item["b0111"];
		$('#coordTable2').append(tr);
	});

	
 	$('td:nth-child(1),td:nth-child(2),td:nth-child(3),td:nth-child(4),td:nth-child(4),td:nth-child(5),'+
 	'td:nth-child(7),td:nth-child(8),td:nth-child(9),td:nth-child(10),td:nth-child(11),'+
 	'td:nth-child(12),td:nth-child(13),td:nth-child(14),td:nth-child(15),'+
 	'td:nth-child(16),td:nth-child(17),'+
 	'td:nth-child(19),'+
 	'td:nth-child(21),td:nth-child(22),td:nth-child(23),td:nth-child(24),td:nth-child(25),'+
 	'td:nth-child(26),td:nth-child(27),td:nth-child(28),td:nth-child(29),'+
 	'td:nth-child(30),td:nth-child(31),'+
 	'td:nth-child(33),td:nth-child(35),td:nth-child(37),td:nth-child(39)'
 	,'#coordTable2 tr').each(function (i, item) {
        $(this).addClass('pointer');
        $(this).on('click',function(){
        	var p = {maximizable:false,resizable:false};
        	p['colIndex'] = $(this).index();
        	//alert($(this).parent().index())
        	p['b0111'] = ROWID[$(this).parent().index()];
        	//alert(p['b0111']);
        	p['query_type']="GBTBTHBBWH";
        	openMate(p);
        })
    });
});
function expExcel(){
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
/* 	var tdEndArray = [];
	var trend = $("#selectable3 tr");
	$.each(trend,function(iend,itemend){
		var tdend = $("th",$(this));
		$.each(tdend,function (tiend, titemend){
			tdEndArray.push($(this).text());
	 		});
		if(tdEndArray.length>0){
	 		dataArray.push(tdEndArray);
	 	}
	});	 */
	//alert(dataArray.length);
	if(dataArray.length>0){
	    ajaxSubmit('expExcel',{"dataArray":Ext.encode(dataArray),"excelname":"干部调配情况统计表2019年1月以来（干部二处提交部务会）"});
	}
}

function downloadByUUID(){
	var downloadUUID = document.getElementById('docpath').value;
	$('#iframe_expBZYP').attr('src','<%= request.getContextPath() %>/PublishFileServlet?method=downloadFile&uuid='+downloadUUID);
	return false
}

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
	background: #CAE8EA ; 
	height: 50px;	
}
th,td{
	border-left: 1px solid #C1DAD7;
	border-bottom: 1px solid #C1DAD7;
	text-align: center;
	font-family: SimHei;
	font-weight: normal;
	height: 50px;
	font-size:12px;
}
td{
	text-align: center;
	height: 50px;
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
	overflow-x:hidden;
	height:340px;
	margin-left:23px;
}
table{
	width: 1350px;
}
.th1{width: 8%; text-align: center;}.th2{width: 2.5%;}.th3{width: 3.3%;}.th4{width: 8%;}.th5{width: 8%;}.th6{width: 6%;}


</style>
</head>
<body>

<div id="selectable">
	
	<table id="coordTable" cellspacing="0" width="100%" >
		<tr>
		    <td colspan="39" class="borderTop title borderRight">干部调配情况统计表2019年1月以来（干部二处提交部务会）</td>
		</tr>
		<tr>
			<th rowspan="2" class="th3">时间</th>
			<th rowspan="2" class="th2">调配干部</th>
			<th colspan="6" >提拔正职</th>
			<th colspan="6" >平职交流正职</th>
			<th colspan="6" >提拔副职</th>
			<th colspan="6" >平职交流副职</th>
			<th colspan="2" >晋升非领导职务</th>
			<th rowspan="2" >副师军转安置</th>
			<th rowspan="2" >退休</th>
			<th rowspan="2" >辞职</th>
			<th rowspan="2" >试用期转正</th>
			<th rowspan="2" >其他工作原因正常任免</th>
			<th colspan="2" >党委（党组）成员</th>
			<th colspan="2" >组织人事处长</th>
			<th rowspan="2" >挂兼职</th>
			<th rowspan="2" >双管单位征求意见</th>
			<th rowspan="2" >破格提拔</th>
			<th rowspan="2" >党组改党委</th>
			<th rowspan="2" >班子组建和换届</th>
			<th rowspan="2" >退二线</th>
			<th rowspan="2" >因身体等原因改任非领导职务、免职</th>
			<th rowspan="2" >违纪等原因免职</th>
			<th rowspan="2" >其他原因不适宜担任现职调整</th>
			<th colspan="2" >其他</th>
		</tr>
		<tr>
			<th class='th2'>提拔正职小计</th>
			<th class='th2'>内部</th>
			<th class='th2'>交流到其他市直单位</th>
			<th class='th2'>来自区县（市）</th>
			<th class='th2'>来自市属国企</th>
			<th class='th2'>其他</th>
			<th class='th2'>交流正职小计</th>
			<th class='th2'>内部</th>
			<th class='th2'>到市直单位</th>
			<th class='th2'>来自区县（市）</th>
			<th class='th2'>来自市属国企</th>
			<th class='th2'>其他</th>
			<th class='th2'>提拔副职小计</th>
			<th class='th2'>内部</th>
			<th class='th2'>交流到其他市直单位\企业</th>
			<th class='th2'>来自区县（市）</th>
			<th class='th2'>来自市属国企</th>
			<th class='th2'>其他</th>
			<th class='th2'>交流副职小计</th>
			<th class='th2'>本单位一般转岗</th>
			<th class='th2'>转任重要岗位</th>
			<th class='th2'>市直单位间</th>
			<th class='th2'>从区县（市）</th>
			<th class='th2'>从市属企事业</th>
			<th class='th2'>其他</th>
			<th class='th2'>巡视员</th>
			<th class='th2'>副巡视员</th>
			<th class='th3'>任</th>
			<th class='th2'>免</th>
			<th class='th2'>任</th>
			<th class='th2'>免</th>
			<th class='th2'>人数</th>
			<th class='th2'>备注</th>
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
			<th  class="th1" >合计</th>
			<th id="hj1"  class="th2"></th>
			<th id="hj2"  class="th3"></th>
			<th id="hj3"  class="th4"></th>
			<th id="hj4"  class="th5"></th>
			<th id="hj5"  class="th6"></th>
			<th id="hj6"  class="th7"></th>
			<th id="hj7"  class="th8"></th>
			<th id="hj8"  class="th9"></th>
			<th id="hj9"  class="th10"></th>
			<th id="hj10"  class="th11"></th>
		</tr>
	</table>
</div> -->

 
<div align="right"><img id="expSelect" onclick="expExcel()" src="<%=request.getContextPath()%>/images/tpbj/exp_excel.png" width="150px" 
style="cursor:pointer;margin-right:25px"/></div> 
</body>
</html>