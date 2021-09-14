<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.hzb.SZDWZCLDGBPBQKDEP"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<html lang="en">
<head>
<odin:head />
<odin:MDParam></odin:MDParam>
<odin:hidden property="docpath" />
<odin:hidden property="con" />
<iframe  id="iframe_expBZYP" style="display: none;" src=""></iframe>
<script src="<%=request.getContextPath()%>/basejs/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>


<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>

<%
String ctxPath = request.getContextPath(); 
String subWinId = request.getParameter("subWinId");
String b = request.getParameter("b");
%>
<script type="text/javascript">
var subWinId = '<%=subWinId%>';
var realParent = parent.Ext.getCmp(subWinId).initialConfig.thisWin;
var data01 = <%=SZDWZCLDGBPBQKDEP.expData01(b)%>;
var data02 = <%=SZDWZCLDGBPBQKDEP.expData02(b)%>;
var data03 = <%=SZDWZCLDGBPBQKDEP.expData03(b)%>;
//console.log(data01);console.log(data02);console.log(data03);
$(function(){
	var ROWID={};
	$.each(data01, function (i,item) {
		document.getElementById("hj0").innerHTML=isnull(item["dep"]);
		document.getElementById("hj1").innerHTML=isnull(item["nu"]);
		document.getElementById("hj2").innerHTML=isnull((parseInt(item["a01"])+parseInt(item["a02"])).toString());
		document.getElementById("hj3").innerHTML=isnull(item["a01"]);
		document.getElementById("hj4").innerHTML=isnull(item["a02"]);
		document.getElementById("hj5").innerHTML=isnull((parseInt(item["a03"])+parseInt(item["a04"])).toString());
		document.getElementById("hj6").innerHTML=isnull(item["a03"]);
		document.getElementById("hj7").innerHTML=isnull(item["a04"]);
		document.getElementById("hj8").innerHTML=isnull((parseInt(item["a01"])-parseInt(item["a03"])+parseInt(item["a02"])-parseInt(item["a04"])).toString());
		document.getElementById("hj9").innerHTML=isnull((parseInt(item["a01"])-parseInt(item["a03"])).toString());
		document.getElementById("hj10").innerHTML=isnull((parseInt(item["a02"])-parseInt(item["a04"])).toString());
		document.getElementById("hj11").innerHTML=isnull(item["a05"]);
		document.getElementById("hj12").innerHTML=isnull(item["a06"]);
		document.getElementById("hj13").innerHTML=isnull(item["a07"]);
		document.getElementById("hj14").innerHTML=isnull(item["a08"]);
		document.getElementById("hj15").innerHTML=isnull(item["a09"]);
		document.getElementById("hj16").innerHTML=isnull(item["a10"]);
		document.getElementById("hj17").innerHTML=isnull(item["a11"]);
		if(parseInt(item["a03"])+parseInt(item["a04"])==0)
			document.getElementById("hj18").innerHTML='0';
		else
			document.getElementById("hj18").innerHTML=isnull((parseInt(item["a11"])*100/(parseInt(item["a03"])+parseInt(item["a04"]))).toFixed(1)+"%");
		document.getElementById("hj19").innerHTML=isnull(item["a12"]);
		document.getElementById("hj20").innerHTML=isnull(item["a13"]);
		document.getElementById("hj21").innerHTML=isnull(item["a14"]);
		document.getElementById("hj22").innerHTML=isnull(item["a15"]);
		document.getElementById("hj23").innerHTML=isnull(item["a16"]);
		document.getElementById("hj24").innerHTML=isnull(item["a17"]);
		document.getElementById("hj25").innerHTML=isnull(item["a18"]);
		document.getElementById("hj26").innerHTML=isnull(item["a19"]);
		document.getElementById("hj27").innerHTML=isnull(item["a20"]);
		document.getElementById("hj28").innerHTML=isnull(item["a21"]);
		document.getElementById("hj29").innerHTML=isnull(item["a22"]);
		document.getElementById("hj30").innerHTML=isnull(item["a23"]);
		document.getElementById("hj31").innerHTML=isnull(item["a24"]);
		document.getElementById("hj32").innerHTML=isnull(item["a25"]);
		document.getElementById("hj33").innerHTML=isnull(item["a26"]);
		document.getElementById("hj34").innerHTML=isnull(item["a27"]);
		document.getElementById("hj35").innerHTML=isnull(item["a28"]);
		document.getElementById("hj36").innerHTML=isnull(item["a29"]);
		document.getElementById("hj37").innerHTML=isnull(item["a30"]);
		document.getElementById("hj38").innerHTML=isnull(item["a31"]);
		document.getElementById("hj39").innerHTML=isnull(item["a32"]);
		document.getElementById("hj40").innerHTML=isnull(item["a33"]);
		document.getElementById("hj41").innerHTML=isnull(item["a34"]);
		document.getElementById("hj42").innerHTML=isnull(item["a35"]);
		document.getElementById("hj43").innerHTML=isnull(item["a36"]);
		document.getElementById("hj44").innerHTML=isnull(item["a37"]);
		document.getElementById("hj45").innerHTML=isnull(item["a38"]);
		
		if(hj8<0)
			document.getElementById("hj8").style.backgroundColor = 'red';
		if(hj9<0)
			document.getElementById("hj9").style.backgroundColor = 'red';
		if(hj10<0)
			document.getElementById("hj10").style.backgroundColor = 'red';
	});
	var index=0;
	$.each(data03, function (i3,item3) {
		var head03=item3["b0101"];
		var cou1=parseInt(ifnull(item3["cou1"]));
		var cou2=parseInt(ifnull(item3["cou2"]));
		var dcou=item3["dcou"];
		var flag=0;
		
		
		$.each(data02, function (i,item) {
			var head01=item["b0101"];
			if(head03==head01){
				var tr = $('<tr><td class="textleft"></td>'
						+'<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>'
						+'<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>'
						+'<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>'
						+'<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>'
						+'<td></td><td></td><td></td><td></td>'
						+'<td class="borderRight"></td></tr>');
				var tds = $("td", tr);
				SetTDtext(tds[0],item["b0101"]);SetTDtext(tds[1],ifnull(dcou));
				SetTDtext(tds[2],(cou1+cou2).toString());
				SetTDtext(tds[3],cou1.toString());SetTDtext(tds[4],cou2.toString());
				/* SetTDtext(tds[2],(parseInt(ifnull(item["a01"]))+parseInt(ifnull(item["a02"]))).toString());
				SetTDtext(tds[3],item["a01"]);SetTDtext(tds[4],item["a02"]); */
				SetTDtext(tds[5],(parseInt(ifnull(item["a03"]))+parseInt(ifnull(item["a04"]))).toString());
				SetTDtext(tds[6],item["a03"]);SetTDtext(tds[7],item["a04"]);
				SetTDtext(tds[8],(cou1-parseInt(ifnull(item["a03"]))+cou2-parseInt(ifnull(item["a04"]))).toString());
				SetTDtext(tds[9],(cou1-parseInt(ifnull(item["a03"]))).toString());
				SetTDtext(tds[10],(cou2-parseInt(ifnull(item["a04"]))).toString());
				SetTDtext(tds[11],item["a05"]);SetTDtext(tds[12],item["a06"]);SetTDtext(tds[13],item["a07"]);SetTDtext(tds[14],item["a08"]);
				SetTDtext(tds[15],item["a09"]);SetTDtext(tds[16],item["a10"]);SetTDtext(tds[17],item["a11"]);
				if(parseInt(item["a03"])+parseInt(item["a04"])==0)
					SetTDtext(tds[18],'0%');
				else
					SetTDtext(tds[18],(parseInt(item["a11"])*100/(parseInt(item["a03"])+parseInt(item["a04"]))).toFixed(1)+"%");
				SetTDtext(tds[19],item["a12"]);SetTDtext(tds[20],item["a13"]);SetTDtext(tds[21],item["a14"]);SetTDtext(tds[22],item["a15"]);
				SetTDtext(tds[23],item["a16"]);SetTDtext(tds[24],item["a17"]);SetTDtext(tds[25],item["a18"]);SetTDtext(tds[26],item["a19"]);
				SetTDtext(tds[27],item["a20"]);SetTDtext(tds[28],item["a21"]);SetTDtext(tds[29],item["a22"]);SetTDtext(tds[30],item["a23"]);
				SetTDtext(tds[31],item["a24"]);SetTDtext(tds[32],item["a25"]);SetTDtext(tds[33],item["a26"]);SetTDtext(tds[34],item["a27"]);
				SetTDtext(tds[35],item["a28"]);SetTDtext(tds[36],item["a29"]);SetTDtext(tds[37],item["a30"]);SetTDtext(tds[38],item["a31"]);
				SetTDtext(tds[39],item["a32"]);SetTDtext(tds[40],item["a33"]);SetTDtext(tds[41],item["a34"]);SetTDtext(tds[42],item["a35"]);
				SetTDtext(tds[43],item["a36"]);SetTDtext(tds[44],item["a37"]);SetTDtext(tds[45],item["a38"]);
				$('#coordTable2').append(tr);
				flag=1;
				if(parseInt(ifnull(item["a01"]))-parseInt(ifnull(item["a03"]))+parseInt(ifnull(item["a02"]))-parseInt(ifnull(item["a04"]))<0)
					tds[8].style.backgroundColor = 'red';
				if(parseInt(ifnull(item["a01"]))-parseInt(ifnull(item["a03"]))<0)
					tds[9].style.backgroundColor = 'red';
				if(parseInt(ifnull(item["a02"]))-parseInt(ifnull(item["a04"]))<0)
					tds[10].style.backgroundColor = 'red';
				
				
				GLOBLE['ROWID'][index]=item["b0101"];
				}
		});
		if(flag!=1){
			var tr = $('<tr><td class="textleft"></td>'
					+'<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>'
					+'<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>'
					+'<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>'
					+'<td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>'
					+'<td></td><td></td><td></td><td></td>'
					+'<td class="borderRight"></td></tr>');
			var tds = $("td", tr);
			SetTDtext(tds[0],head03);SetTDtext(tds[1],dcou);
			SetTDtext(tds[2],(cou1+cou2).toString());
			SetTDtext(tds[3],cou1.toString());SetTDtext(tds[4],cou2.toString());
			SetTDtext(tds[5],'0');
			SetTDtext(tds[6],'0');
			SetTDtext(tds[7],'0');
			SetTDtext(tds[8],(0-cou1-cou2).toString());
			SetTDtext(tds[9],(0-cou1).toString());
			SetTDtext(tds[10],(0-cou2).toString());
			SetTDtext(tds[11],'0');SetTDtext(tds[12],'0');SetTDtext(tds[13],'0');SetTDtext(tds[14],'0');
			SetTDtext(tds[15],'0');SetTDtext(tds[16],'0');SetTDtext(tds[17],'0');
			SetTDtext(tds[18],'0%');
			SetTDtext(tds[19],'0');SetTDtext(tds[20],'0');SetTDtext(tds[21],'0');SetTDtext(tds[22],'0');
			SetTDtext(tds[23],'0');SetTDtext(tds[24],'0');SetTDtext(tds[25],'0');SetTDtext(tds[26],'0');
			SetTDtext(tds[27],'0');SetTDtext(tds[28],'0');SetTDtext(tds[29],'0');SetTDtext(tds[30],'0');
			SetTDtext(tds[31],'0');SetTDtext(tds[32],'0');SetTDtext(tds[33],'0');SetTDtext(tds[34],'0');
			SetTDtext(tds[35],'0');SetTDtext(tds[36],'0');SetTDtext(tds[37],'0');SetTDtext(tds[38],'0');
			SetTDtext(tds[39],'0');SetTDtext(tds[40],'0');SetTDtext(tds[41],'0');SetTDtext(tds[42],'0');
			SetTDtext(tds[43],'0');SetTDtext(tds[44],'0');SetTDtext(tds[45],'0');
			$('#coordTable2').append(tr);
			flag=1;
			GLOBLE['ROWID'][index]=head03;
		}
		index++;
		flag=0;
	});
	
	var con='<%=b%>';
	if("dwbm"==con)
		con="党委部门分表";
	if("zfbm"==con)
		con="政府部门分表";
	if("fjjg"==con)
		con="法检机关分表";
	if("mzdp"==con)
		con="民主党派分表";
	if("qt"==con)
		con="其他市级机关分表";
	if("ssgx"==con)
		con="市属高校分表";
	if("ssgq"==con)
		con="市属国企分表";
	document.getElementById("deptablename").innerHTML = con;
	
	$('#coordTable2 tr:gt(0) td').each(function (i, item) {
		if(($(this).index()<=7&&$(this).index()>=5)||($(this).index()<=45&&$(this).index()>=11)){
			 $(this).addClass('pointer');
		        $(this).bind('click',function(){
		        	var p = {maximizable:false,resizable:false};
		        	p['colIndex'] = $(this).index();
		        	//alert($(this).index());
		        	console.log($(this).parent().index()-1);
		        	console.log(index);
		        	p['b0111'] = GetRowid($(this).parent().index()-1);
		        	p['query_type']="SZDWZCLDGBPBQKDEP";
		        	openMate(p);
		        });
			}
    });
	
	/* $('td:nth-child(6),td:nth-child(7),td:nth-child(8),'+
			'td:nth-child(12),td:nth-child(13),td:nth-child(14),td:nth-child(15),td:nth-child(16),td:nth-child(17),td:nth-child(18),td:nth-child(20),'+
			'td:nth-child(21),td:nth-child(22),td:nth-child(23),td:nth-child(24),td:nth-child(25),td:nth-child(26),td:nth-child(27),td:nth-child(28),td:nth-child(29),td:nth-child(30),'+
			'td:nth-child(31),td:nth-child(32),td:nth-child(33),td:nth-child(34),td:nth-child(35),td:nth-child(36),td:nth-child(37),td:nth-child(38),td:nth-child(39),td:nth-child(40),'+	
			'td:nth-child(41),td:nth-child(42),td:nth-child(43),td:nth-child(44)','#coordTable2 tr:gt(0)').each(function (i, item) {
        $(this).addClass('pointer');
        $(this).bind('click',function(){
        	var p = {maximizable:false,resizable:false};
        	p['colIndex'] = $(this).index();
        	//alert($(this).index());
        	console.log($(this).parent().index()-1);
        	console.log(index);
        	p['b0111'] = GetRowid($(this).parent().index()-1);
        	p['query_type']="SZDWZCLDGBPBQKDEP";
        	openMate(p);
        });
    }); */
});
function ifnull(v){
	if(v==""||v==null||v=="null"||v=="0"||v=="0%"||v=="0.0%"){
		v="0";
	}
	return v;
}
function isnull(v){
	if(v==""||v==null||v=="null"||v=="0"||v=="0%"||v=="0.0%"){
		v="&nbsp";
	}
	return v;
}
function openMate(p){///hzb_hz/WebContent/pages/sysorg/hzb/Mate.jsp
	$h.openWin('mateWin','pages.sysorg.org.hzb.Mate','人员列表',720,540,'','<%=request.getContextPath()%>',null,p,true);
}
function SetTDtext(td,v) {
  $(td).html((v==""||v==null||v=="null"||v=="0"||v=="0%"||v=="0.0%")?" ":v.replace(/\n/g,"<br/>"));
}
//b0180 bz,b0234,b0235,b0236
var GLOBLE = {};
GLOBLE['ROWID']={}
GLOBLE['COL_CONFIG_3']={"10":"b0234","11":"b0235","12":"b0236","13":"b0180"};
//根据id存储行信息
GLOBLE['ID_ROWINFO']={};
//根据行号获取rowid
function GetRowid(rowIndex){
	return GLOBLE['ROWID'][rowIndex];
}
function expExcel(){
	var dataArray = [];
	
	var trend = $("#selectable3 tr");
	$.each(trend,function(iend,itemend){
		var tdend = $("th",$(this));
		var tdEndArray = [];
		$.each(tdend,function (tiend, titemend){
			tdEndArray.push($(this).text());
	 		});
		if(tdEndArray.length>0){
	 		dataArray.push(tdEndArray);
	 	}
	});
	
	
	var tr = $("#selectable2 tr");
	selectable3
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
	    ajaxSubmit('expExcel',{"dataArray":Ext.encode(dataArray),"excelname":"市直单位中层（处级）领导干部配备情况统计分析表"});
	}
}

function downloadByUUID(){
	var downloadUUID = document.getElementById('docpath').value;
	$('#iframe_expBZYP').attr('src','<%= request.getContextPath() %>/PublishFileServlet?method=downloadFile&uuid='+downloadUUID);
	return false
}
</script>
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
	text-align:center;
	height: 40px;
	width:200px;
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
	width: 1800px;
}
#selectable2{
	width:1800px;
	height:390px;
	overflow-y:scroll;
}
#selectable3{
width:1800px;
}
#selectable4{
	width:1200px;
	white-space:nowrap;
	overflow-x:scroll;
}
table{
	width: 1750px;
}
.th1{width: 6%;}.th2{width: 3%;}.th3{width: 3%;}.th4{width: 2%;}.th5{width: 66%;}
.th6{width: 15%;}.th7{width: 5%;text-align: left;}.th8{width: 3%;}
.th12{width: 40px;}.th14{width: 30px;}.th11{width:200px}
</style>
</head>
<body>


<div id="selectable4">
	<div id="selectable">
	<table id="coordTable" cellspacing="0" width="100%" >
		<tr>
		    <td colspan="64" class="borderTop title borderRight" id="deptablename"></td>
		</tr>
		<tr>
			<th rowspan="3"  class="th11">部门/所属市直单位</th>
			<!-- <th rowspan="3" class="th2">单位数</th> -->
			<th rowspan="3" colspan="1" class="th14">单位数</th>
			<th rowspan="1" colspan="9" >中层干部职数配备</th>
			<th colspan="24" >年龄结构</th>
			<th rowspan="1" colspan="4" >女干部和党外干部</th>
			<th colspan="5"  >最高学历</th>
			<th colspan="2"  >全日制学历</th>
		</tr>
		<tr>
			
			<th rowspan="2" class="th12">核定总职数</th>
		    <th rowspan="2" class="th12">核定正职数</th>
		    <th rowspan="2" class="th12">核定副职数</th>
		    <th rowspan="2" class="th12">实配总职数</th>
		    <th rowspan="2" class="th12">实配正职数</th>
		    <th rowspan="2" class="th12">实配副职数</th>
		    <th rowspan="2" class="th12">空缺总职数</th>
		    <th rowspan="2" class="th12">空缺正职数</th>
		    <th rowspan="2" class="th12">空缺副职数</th>
			
		    <th rowspan="2" class="th14">60年生</th>
		    <th rowspan="2" class="th14">61年生</th>
		    <th rowspan="2" class="th14">62年生</th>
		    <th rowspan="2" class="th14">63年生</th>
		    <th rowspan="2" class="th14">64年生</th>
		    <th rowspan="2" class="th14">65年生</th>
		    <th rowspan="2" class="th14">80后</th>
		    <th colspan="10" style="height: 20px;border-left: 0px solid #000000;border-bottom: 1px solid #000000;">&nbsp;</th>
		    <th rowspan="2" class="th14">80后正职</th>
		    <th colspan="6" style="height: 20px;border-left: 0px solid #000000;border-bottom: 1px solid #000000;">&nbsp;</th>
		    
		    <th rowspan="2" class="th12">女干部</th>
		    <th rowspan="2" class="th14">女正职</th>
		    <th rowspan="2" class="th14">党外干部</th>
		    <th rowspan="2" class="th14">党外正职</th>
			
		    <th rowspan="2" class="th14">大专及以下</th>
		    <th rowspan="2" class="th12">大学</th>
		    <th   style="border-left: 0px solid #000000;border-bottom: 1px solid #000000;">&nbsp;</th>
		    <th rowspan="2" class="th12">研究生</th>
		    <th   style="border-left: 0px solid #000000;border-bottom: 1px solid #000000;">&nbsp;</th>
		    <th rowspan="2" class="th12">大学以上</th>
		    <th  style="border-left: 0px solid #000000;border-bottom: 1px solid #000000;">&nbsp;</th>
		</tr>
		<tr>
			<th class="th12">80后占比</th>
			<th class="th14">80年生</th>
			<th class="th14">81年生</th>
			<th class="th14">82年生</th>
			<th class="th14">83年生</th>
			<th class="th14">84年生</th>
			<th class="th14">85年生</th>
			<th class="th14">86年生</th>
			<th class="th14">87年生</th>
			<th class="th14">88后</th>
			
			<th class="th14">80年生正职</th>
			<th class="th14">81年生正职</th>
			<th class="th14">82年生正职</th>
			<th class="th14">83年生正职</th>
			<th class="th14">84年生正职</th>
			<th class="th14">85后正职</th>
			
			<th class="th12">硕士</th>
			<th class="th14">博士</th>
			<th class="th14">研究生</th>
		</tr>
	</table>
	</div>
	<div id="selectable3">
	<table  cellspacing="0" width="100%" >
	 	<tr>
			<th  id="hj0" class="th11" style="height: 40px;"></th>
			<th  id="hj1" class="th14"></th><th  id="hj2" class="th12"></th><th  id="hj3" class="th12"></th><th  id="hj4" class="th12"></th><th  id="hj5" class="th12"></th>
			<th  id="hj6" class="th12"></th><th  id="hj7" class="th12"></th><th  id="hj8" class="th12"></th><th  id="hj9" class="th12"></th><th  id="hj10" class="th12"></th>
			<th  id="hj11" class="th14"></th><th  id="hj12" class="th14"></th><th  id="hj13" class="th14"></th><th  id="hj14" class="th14"></th><th  id="hj15" class="th14"></th>
			<th  id="hj16" class="th14"></th><th  id="hj17" class="th14"></th><th  id="hj18" class="th12"></th><th id="hj19" class="th14"></th> <th id="hj20" class="th14"></th>
			<th  id="hj21" class="th14"></th><th  id="hj22" class="th14"></th><th  id="hj23" class="th14"></th><th  id="hj24" class="th14"></th><th  id="hj25" class="th14"></th>
			<th  id="hj26" class="th14"></th><th  id="hj27" class="th14"></th><th  id="hj28" class="th14"></th><th  id="hj29" class="th14"></th><th  id="hj30" class="th14"></th>
			<th  id="hj31" class="th14"></th><th  id="hj32" class="th14"></th><th  id="hj33" class="th14"></th><th  id="hj34" class="th14"></th><th  id="hj35" class="th12"></th>
			<th  id="hj36" class="th14"></th><th  id="hj37" class="th14"></th><th  id="hj38" class="th14"></th><th  id="hj39" class="th14"></th><th  id="hj40" class="th12"></th>
			<th  id="hj41" class="th12"></th><th  id="hj42" class="th12"></th><th  id="hj43" class="th14"></th><th  id="hj44" class="th12"></th><th  id="hj45" class="th14"></th>
		</tr>
	</table>
	</div>
	<div  id="selectable2">
	
	<table id="coordTable2" cellspacing="0" width="100%" >
		<tr>
			<th class="th11"></th><th class="th14"></th><th class="th12"></th><th class="th12"></th><th class="th12"></th><th class="th12"></th>
			<th class="th12"></th><th class="th12"></th><th class="th12"></th><th class="th12"></th><th class="th12"></th><th class="th14"></th>
			<th class="th14"></th><th class="th14"></th><th class="th14"></th><th class="th14"></th><th class="th14"></th><th class="th14"></th>
			<th class="th12"></th><th class="th14"></th><th class="th14"></th><th class="th14"></th><th class="th14"></th>
			<th class="th14"></th><th class="th14"></th><th class="th14"></th><th class="th14"></th><th class="th14"></th><th class="th14"></th>
			<th class="th14"></th><th class="th14"></th><th class="th14"></th><th class="th14"></th><th class="th14"></th><th class="th14"></th>
			<th class="th12"></th><th class="th14"></th><th class="th14"></th><th class="th14"></th><th class="th14"></th><th class="th12"></th>
			<th class="th12"></th><th class="th12"></th><th class="th14"></th><th class="th12"></th><th class="th14"></th>
		</tr>
	</table>
	
</div>
</div>
</body>
</html>