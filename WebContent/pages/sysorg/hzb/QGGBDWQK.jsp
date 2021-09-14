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
		//单位
		$('<tr><td class="th3"></td><td class="th2"></td><td class="th2"></td>'+
		//正职
		'<td class="th2"></td><td class="th2"></td><td class="th2"></td>'+
		//年龄
		'<td class="th2"></td><td class="th2"></td><td class="th2"></td><td class="th2"></td><td class="th2"></td>'+
		//人员类型
		'<td class="th2"></td><td class="th2"></td><td class="th2"></td><td class="th2"></td>'+
		//工龄情况
		'<td class="th2"></td><td class="th3"></td>'+
		//副职
		'<td class="th2"></td><td class="th2"></td><td class="th2"></td>'+
		//年龄
		'<td class="th2"></td><td class="th2"></td><td class="th2"></td><td class="th2"></td><td class="th2"></td>'+
		//人员类型
		'<td class="th2"></td><td class="th2"></td><td class="th2"></td><td class="th2"></td>'+
		//工龄情况
		'<td class="th2"></td><td class="th3"></td>'+
		//职级公务员
		'<td class="th2"></td><td class="th2"></td><td class="th2"></td><td class="th2"></td>'+
		'<td class="th2"></td><td class="th2"></td><td class="th2"></td><td class="th2"></td>'+
		'</tr>');
		var tds = $("td", tr);
		//单位
		SetTDtext(tds[0],item["b0101"]);
		/* SetTDtext(tds[1],item["zzs"]);
		SetTDtext(tds[2],item["sps"]); */
		SetTDtext(tds[1],' ');
		SetTDtext(tds[2],' ');
		//正职
		/* SetTDtext(tds[3],item["zzhdzs"]); */
		SetTDtext(tds[3],' ');
		SetTDtext(tds[4],item["zzsps"]);
		/* SetTDtext(tds[5],item["zzqps"]); */
		SetTDtext(tds[5],' ');
		//年龄
		SetTDtext(tds[6],item["age40zz"]);
		SetTDtext(tds[7],item["age45zz"]);
		SetTDtext(tds[8],item["age50zz"]);
		SetTDtext(tds[9],item["age55zz"]);
		SetTDtext(tds[10],item["age56zz"]);
		//人员类型
		SetTDtext(tds[11],item["gwyzz"]);
		SetTDtext(tds[12],item["cgzz"]);
		SetTDtext(tds[13],item["syzz"]);
		SetTDtext(tds[14],item["gqzz"]);
		//工龄情况
		SetTDtext(tds[15],item["workage30zz"]);
		SetTDtext(tds[16],item["workage20zz"]);
		//副职
		/* SetTDtext(tds[17],item["fzhdzs"]); */
		SetTDtext(tds[17],' ');
		SetTDtext(tds[18],item["fzsps"]);
		/* SetTDtext(tds[19],item["fzqps"]); */
		SetTDtext(tds[19],' ');
		//年龄
		SetTDtext(tds[20],item["age40fz"]);
		SetTDtext(tds[21],item["age45fz"]);
		SetTDtext(tds[22],item["age50fz"]);
		SetTDtext(tds[23],item["age55fz"]);
		SetTDtext(tds[24],item["age56fz"]);
		//人员类型
		SetTDtext(tds[25],item["gwyfz"]);
		SetTDtext(tds[26],item["cgfz"]);
		SetTDtext(tds[27],item["syfz"]);
		SetTDtext(tds[28],item["gqfz"]);
		//工龄情况
		SetTDtext(tds[29],item["workage30fz"]);
		SetTDtext(tds[30],item["workage20fz"]);
		//职级公务员
	    SetTDtext(tds[31],item["hd1"]);
		SetTDtext(tds[32],item["sp1"]);
		SetTDtext(tds[33],item["hd2"]);
		SetTDtext(tds[34],item["sp2"]);
		SetTDtext(tds[35],item["hd3"]);
		SetTDtext(tds[36],item["sp3"]);
		SetTDtext(tds[37],item["hd4"]);
		SetTDtext(tds[38],item["sp4"]); 
/* 		SetTDtext(tds[31],' ');
		SetTDtext(tds[32],' ');
		SetTDtext(tds[33],' ');
		SetTDtext(tds[34],' ');
		SetTDtext(tds[35],' ');
		SetTDtext(tds[36],' ');
		SetTDtext(tds[37],' ');
		SetTDtext(tds[38],' '); */
		
		ROWID[i]=item["b0111"];
		$('#coordTable2').append(tr);
	});

	
 	$('td:nth-child(5),'+
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
        	p['query_type']="QGGBDWQK";
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
	    ajaxSubmit('expExcel',{"dataArray":Ext.encode(dataArray),"excelname":"区管干部队伍情况统计表"});
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
		    <td colspan="39" class="borderTop title borderRight">区管干部队伍情况统计表</td>
		</tr>
		<tr>
			<th rowspan="3" class="th3">单位</th>
			<th rowspan="3" class="th2">区管领导干部总职数</th>
			<th rowspan="3" class="th2">区管领导干部实配数</th>
			<th colspan="14" >区管正职领导干部</th>
			<th colspan="14" >区管副职领导干部</th>
			<th colspan="8">职级公务员</th>
		</tr>
		<tr>
		 	<th colspan='3'>配备情况</th>
		    <th colspan='5'>年龄分布</th>
		    <th colspan='4'>人员类型</th>
		    <th colspan='2'>工龄情况<br/>(不重复计算)</th>
		    <th colspan='3'>配备情况</th>
		    <th colspan='5'>年龄分布</th>
		    <th colspan='4'>人员类型</th>
		    <th colspan='2'>工龄情况<br/>(不重复计算)</th>
		    <th colspan='2'>一级调研员</th>
		    <th colspan='2'>二级调研员</th>
		    <th colspan='2'>三级调研员</th>
		    <th colspan='2'>四级调研员</th>
		</tr>
		<tr>
			<th class='th2'>核定职数</th>
			<th class='th2'>实配数</th>
			<th class='th2'>缺配数</th>
			<th class='th2'>40岁及以下</th>
			<th class='th2'>41-45岁</th>
			<th class='th2'>46-50岁</th>
			<th class='th2'>51-55岁</th>
			<th class='th2'>56岁及以上</th>
			<th class='th2'>公务员</th>
			<th class='th2'>参公</th>
			<th class='th2'>事业</th>
			<th class='th2'>国企</th>
			<th class='th2'>工作年限满30年</th>
			<th class='th3'>年满55周岁且工作年限满20年</th>
			<th class='th2'>核定职数</th>
			<th class='th2'>实配数</th>
			<th class='th2'>缺配数</th>
			<th class='th2'>40岁及以下</th>
			<th class='th2'>41-45岁</th>
			<th class='th2'>46-50岁</th>
			<th class='th2'>51-55岁</th>
			<th class='th2'>56岁及以上</th>
			<th class='th2'>公务员</th>
			<th class='th2'>参公</th>
			<th class='th2'>事业</th>
			<th class='th2'>国企</th>
			<th class='th2'>工作年限满30年</th>
			<th class='th3'>年满55周岁且工作年限满20年</th>
			<th class='th2'>核定职数</th>
			<th class='th2'>实配数</th>
			<th class='th2'>核定职数</th>
			<th class='th2'>实配数</th>
			<th class='th2'>核定职数</th>
			<th class='th2'>实配数</th>
			<th class='th2'>核定职数</th>
			<th class='th2'>实配数</th>
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