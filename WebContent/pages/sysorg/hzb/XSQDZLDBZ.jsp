<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.hzb.XSQDZLDBZ"%>
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
var data = <%=XSQDZLDBZ.expData()%>;
$(function(){
	//alert(data.length);
	var ROWID={};
/* 	var hj1=0;
	var hj2=0;
	var hj3=0;
	var hj4=0; */
	$.each(data, function (i,item) {
		//console.log(item['zzsp'])
		var tr = $('<tr><td class="th1"></td><td class="th4"></td><td class="th4"></td><td class="th4"></td><td class="th4"></td>'+
				'<td class="th4"></td><td class="th4"></td><td class="th4"></td><td class="th4"></td><td class="th4"></td><td class="th4"></td><td class="th4"></td><td class="th4"></td><td class="th4"></td><td class="th4"></td>'+
				'<td class="th4"></td><td class="th4"></td><td class="th4"></td><td class="th4"></td><td class="th4"></td><td class="th4"></td><td class="th4"></td><td class="th4"></td><td class="th4"></td><td class="th4"></td>'+
				'<td class="th4"></td><td class="th4"></td><td class="th4"></td><td class="th4"></td><td class="th4"></td><td class="th4"></td><td class="th4"></td><td class="th4"></tr>');
		var tds = $("td", tr);
		SetTDtext(tds[0],item["ssxq"]);
		SetTDtext(tds[1],item["sp"]);
		SetTDtext(tds[2],item["age42"]);
		SetTDtext(tds[3],item["age40"]);
		SetTDtext(tds[4],item["age45"]);
		SetTDtext(tds[5],item["age50"]);
		SetTDtext(tds[6],item["age55"]);
		SetTDtext(tds[7],item["age60"]);
		SetTDtext(tds[8],item["avgage"]);
		SetTDtext(tds[9],item["after70"]);
		SetTDtext(tds[10],item["after75"]);
		SetTDtext(tds[11],item["after80"]);
		SetTDtext(tds[12],item["age42zz"]);
		SetTDtext(tds[13],item["zgxlyjs"]);
		SetTDtext(tds[14],item["zgxlyjs40"]);
		SetTDtext(tds[15],item["zgxlbk"]);
		SetTDtext(tds[16],item["zgxlbk40"]);
		SetTDtext(tds[17],item["zgxldz"]);
		SetTDtext(tds[18],item["zgxldz40"]);
		SetTDtext(tds[19],item["qrzxlbs"]);
		SetTDtext(tds[20],item["qrzxlbs40"]);
		SetTDtext(tds[21],item["qrzxlss"]);
		SetTDtext(tds[22],item["qrzxlss40"]);
		SetTDtext(tds[23],item["qrzxlbk"]);
		SetTDtext(tds[24],item["qrzxlbk40"]);
		SetTDtext(tds[25],item["qrzxldz"]);
		SetTDtext(tds[26],item["qrzxldz40"]);
		SetTDtext(tds[27],item["ngb"]);
		SetTDtext(tds[28],item["ngb40"]);
		SetTDtext(tds[29],item["ngbzz"]);
		SetTDtext(tds[30],item["dwgb"]);
		SetTDtext(tds[31],item["dwgb40"]);
		SetTDtext(tds[32],item["dwgbzz"]);
		ROWID[i]=item["b0111"];
		$('#coordTable2').append(tr);
/* 		hj1=hj1+parseInt(item["dzzs"]);
		hj2=hj2+parseInt(item["age43"]);
		hj4=hj4+parseInt(item["age38"]); */
	});
/* 	document.getElementById("hj1").innerHTML=hj1;
	document.getElementById("hj2").innerHTML=hj2;
	document.getElementById("hj4").innerHTML=hj4;
	hj3=Math.round(hj2*10000/hj1)/100+"%";
	document.getElementById("hj3").innerHTML=hj3; */
	/*$('td:nth-child(5),td:nth-child(6),td:nth-child(7)','#coordTable2 tr:gt(0)').each(function (i, item) {
        if($(this).text()<0){
        	$(this).addClass('colorLight');
        }
		
    });*/
	
	$('td:nth-child(2),td:nth-child(3),td:nth-child(4),td:nth-child(5),td:nth-child(6),td:nth-child(7),td:nth-child(8),td:nth-child(10),'+
		'td:nth-child(11),td:nth-child(12),td:nth-child(13),td:nth-child(14),td:nth-child(15),td:nth-child(16),td:nth-child(17),td:nth-child(18),td:nth-child(19),'+
		'td:nth-child(20),td:nth-child(21),td:nth-child(22),td:nth-child(23),td:nth-child(24),td:nth-child(25),td:nth-child(26),td:nth-child(27),td:nth-child(28),'+
		'td:nth-child(29),td:nth-child(30),td:nth-child(31),td:nth-child(32),td:nth-child(33)','#coordTable2 tr').each(function (i, item) {
        $(this).addClass('pointer');
        $(this).on('click',function(){
        	var p = {maximizable:false,resizable:false};
        	p['colIndex'] = $(this).index();
        	//alert($(this).parent().index())
        	p['b0111'] = ROWID[$(this).parent().index()-1];
        	p['query_type']="XSQDZLDBZ";
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

	
	//alert(dataArray.length);
	if(dataArray.length>0){
	    ajaxSubmit('expExcel',{"dataArray":Ext.encode(dataArray),"excelname":"县（市、区）党政领导班子和干部队伍有关情况统计"});
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
	text-align: center;
}
th,td{
	border-left: 1px solid #000000;
	border-bottom: 1px solid #000000;
	font-family: 宋体;
	font-weight: normal;
	font-size: 14px;
}
td{
	text-align: center;
}
.textleft{
	text-align: center;
	height: 40px;
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
#selectable2{
	overflow:auto;
}
#selectable3{
	overflow:auto 0;
}
table{
	width: 1180px;
}
.th1{width: 4%;}.th2{width: 3%;}.th3{width: 3%;}.th4{width: 3%;}.th5{width: 66%;}
.th6{width: 15%;}.th7{width: 5%;text-align: left;}.th8{width: 3%;}
</style>
</head>
<body>


	<div id="selectable">
	
	<table id="coordTable" cellspacing="0" width="100%" >
		<tr>
		    <td colspan="33" class="borderTop title borderRight">县（市、区）党政领导班子和干部队伍有关情况统计</td>
		</tr>
		<tr>
			<th rowspan="5"  class="th1">单位</th>
			<th colspan="32" class="th5">领导班子</th>
		</tr>
		<tr>
		    <th colspan="11">班子成员</th>
		    <th>正职</th>
		    <th colspan="6" rowspan='2' >学历情况</th>
		    <th colspan="8" style="height: 20px;border-left: 0px solid #000000;border-bottom: 1px solid #000000;">&nbsp;</th>
		    <th colspan="2" rowspan='3'>女干部</th>
		    <th  style="height: 20px;border-left: 0px solid #000000;border-bottom: 1px solid #000000;">&nbsp;</th>
		    <th colspan="2" rowspan='3'>党外干部</th>
		    <th  style="height: 20px;border-left: 0px solid #000000;border-bottom: 1px solid #000000;">&nbsp;</th>
		</tr>
		<tr>
			<th rowspan='3' class="th4">实配</th>
			<th rowspan='3' class="th4">40岁<br/>左右<br/>及<br/>以下</th>
			<th rowspan='3' class="th4">40<br/>岁<br/>以<br/>下</th>
			<th rowspan='3' class="th4">41-<br/>45</th>
			<th rowspan='3' class="th4">46-<br/>50</th>
			<th rowspan='3' class="th4">51-<br/>55</th>
			<th rowspan='3' class="th4">56-<br/>60</th>
			<th rowspan='3' class="th4">平<br/>均<br/>年<br/>龄</th>
			<th rowspan='3' class="th4">70<br/>后</th>
			<th rowspan='3' class="th4">75<br/>后</th>
			<th rowspan='3' class="th4">80<br/>后</th>
			<th rowspan='3' class="th4">40岁<br/>左右<br/>及<br/>以下</th>
			<th colspan='8'>全日制学历</th>
			<th rowspan='3' class="th4">正职</th>
			<th rowspan='3' class="th4">正职</th>
		</tr>
		<tr>
			<th colspan='2'>研究生</th>
			<th colspan='2'>本科</th>
			<th colspan='2'>大专<br/>及以下</th>
			<th colspan='2'>博士</th>
			<th colspan='2'>硕士</th>
			<th colspan='2'>本科</th>
			<th colspan='2'>大专<br/>及以下</th>
		</tr>
		<tr>
			<th class="th4">全</th>
			<th class="th4">40</th>
			<th class="th4">全</th>
			<th class="th4">40</th>
			<th class="th4">全</th>
			<th class="th4">40</th>
			<th class="th4">全</th>
			<th class="th4">40</th>
			<th class="th4">全</th>
			<th class="th4">40</th>
			<th class="th4">全</th>
			<th class="th4">40</th>
			<th class="th4">全</th>
			<th class="th4">40</th>
			<th class="th4">全</th>
			<th class="th4">40</th>
			<th class="th4">全</th>
			<th class="th4">40</th>
		</tr>
	</table>
</div>

<div  id="selectable2">
	<div>
	<table id="coordTable2" cellspacing="0" width="100%" >
		<tr>
			<th class="th1"></th><th class="th4"></th><th class="th4"></th><th class="th4"></th><th class="th4"></th><th class="th4"></th>
			<th class="th4"></th><th class="th4"></th><th class="th4"></th><th class="th4"></th><th class="th4"></th><th class="th4"></th>
			<th class="th4"></th><th class="th4"></th><th class="th4"></th><th class="th4"></th><th class="th4"></th><th class="th4"></th>
			<th class="th4"></th><th class="th4"></th><th class="th4"></th><th class="th4"></th><th class="th4"></th><th class="th4"></th>
			<th class="th4"></th><th class="th4"></th><th class="th4"></th><th class="th4"></th><th class="th4"></th><th class="th4"></th>
			<th class="th4"></th><th class="th4"></th><th class="th4"></th>
		</tr>
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

<div align="right"><img id="expSelect" onclick="expExcel()" src="<%=request.getContextPath()%>/images/tpbj/exp_excel.png" width="150px" 
style="cursor:pointer;margin-right:25px"/></div> 
</body>
</html>
