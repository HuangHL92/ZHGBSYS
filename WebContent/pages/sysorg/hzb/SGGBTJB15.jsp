<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.hzb.SGGBTJB15"%>
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
var data = <%=SGGBTJB15.expData()%>;
$(function(){
	//alert(data.length);
	console.log(data)
	var ROWID={};
	var hj1=0;
	var hj2=0;
	var hj3=0;
	var hj4=0;
	var hj5=0;
	var hj6=0;
	var hj7=0;
	var hj8=0;
	var hj9=0;
	var hj10=0;
	var hj11=0;
	var hj12=0;
	var hj13=0;
	var hj14=0;
	var index = 1;
	$.each(data, function (i,item) {
		var tr = $('<tr><td class="th12 textleft"><td class="th4"></td><td class="th4"></td><td class="th4"></td><td class="th4"></td><td class="th4"></td><td class="th4"/td><td class="th4"/td><td class="th4"/td><td class="th4"/td><td class="th0"/td><td class="th0"/td><td class="th0"/td><td class="th0"/td></tr>');
		var tds = $("td", tr);
		
		SetTDtext(tds[0],item["b0101"]);
		SetTDtext(tds[1],item["cnt"]);
		SetTDtext(tds[2],item["zz"]);
		SetTDtext(tds[3],item["fz"]);
		SetTDtext(tds[4],item["qt"]);
		SetTDtext(tds[5],item["afn74"]);
		SetTDtext(tds[6],item["afc74"]);
		SetTDtext(tds[7],item["afn79"]);
		SetTDtext(tds[8],item["afc79"]);
		SetTDtext(tds[9],item["afn84"]);
		SetTDtext(tds[10],item["ngb"]);
		SetTDtext(tds[11],item["dwgb"]);
		SetTDtext(tds[12],item["qrzdx"]);
		SetTDtext(tds[13],item["qrzyjs"]);
		ROWID[i]=item["b0101"];
		$('#coordTable2').append(tr);
		hj1=hj1+parseInt(item["cnt"]);
		hj2=hj2+parseInt(item["zz"]);
		hj3=hj3+parseInt(item["fz"]);
		hj4=hj4+parseInt(item["qt"]);
		hj5=hj5+parseInt(item["afn74"]);
		//hj6=hj6+parseInt(item["afc74"]);
		hj7=hj7+parseInt(item["afn79"]);
		//hj8=hj8+parseInt(item["afc79"]);
		hj9=hj9+parseInt(item["afn84"]);
		hj10=hj10+parseInt(item["ngb"]);
		hj11=hj11+parseInt(item["dwgb"]);
		hj12=hj12+parseInt(item["qrzdx"]);
		hj13=hj13+parseInt(item["qrzyjs"]);
		
	});
	document.getElementById("hj1").innerHTML=isnull(hj1);
	document.getElementById("hj2").innerHTML=isnull(hj2);
	document.getElementById("hj3").innerHTML=isnull(hj3);
	document.getElementById("hj4").innerHTML=isnull(hj4);
	document.getElementById("hj5").innerHTML=isnull(hj5);
	//document.getElementById("hj6").innerHTML=hj6;
	document.getElementById("hj7").innerHTML=isnull(hj7);
	//document.getElementById("hj8").innerHTML=hj8;
	document.getElementById("hj9").innerHTML=isnull(hj9);
	document.getElementById("hj10").innerHTML=isnull(hj10); 
	document.getElementById("hj11").innerHTML=isnull(hj11);
	document.getElementById("hj12").innerHTML=isnull(hj12);
	document.getElementById("hj13").innerHTML=isnull(hj13);
	hj6=Math.round(hj5*1000/hj1)/10+"%";
	document.getElementById("hj6").innerHTML=isnull(hj6);
	hj8=Math.round(hj7*1000/hj1)/10+"%";
	document.getElementById("hj8").innerHTML=isnull(hj8);
	//hj3=Math.round(hj2*10000/hj1)/100+"%";
	
	
	
	/* $('td:nth-child(8),td:nth-child(10),td:nth-child(11)','#coordTable2 tr:gt(0)').each(function (i, item) {
        if($(this).text()<0){
        	$(this).addClass('colorLight');
        }
		
    }); */
	
	 $('td:nth-child(1),td:nth-child(2),td:nth-child(3),td:nth-child(4),td:nth-child(5),td:nth-child(6),td:nth-child(8),td:nth-child(10),td:nth-child(11),td:nth-child(12),td:nth-child(13),td:nth-child(14)','#coordTable2 tr').each(function (i, item) {
        $(this).addClass('pointer');
        $(this).on('click',function(){
        	var p = {maximizable:false,resizable:false};
        	p['colIndex'] = $(this).index();
        	//alert($(this).parent().index())
        	//console.log($(this).parent().index())
        	debugger;
        	p['b0101'] = ROWID[$(this).parent().index()];
        	p['query_type']="SGGBTJB15";
        	openMate(p);
        })
    }); 
    
});
function isnull(v){
	if(v==""||v==null||v=="null"||v=="0"||v=="0%"||v=="0.0%"){
		v="&nbsp";
	}
	return v;
}
function checkNum(ss){
	var type="^[0-9]*[1-9][0-9]*$";
	var re = new RegExp(type);
	if(ss.match(re)==null)
	{
	return false;
	}else {
		return true;
	}
}
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
	
	console.log(dataArray)
	//alert(dataArray.length);
	if(dataArray.length>0){
	    ajaxSubmit('expExcel',{"dataArray":Ext.encode(dataArray),"excelname":"市直单位市管干部情况统计表"});
	}
}

function downloadByUUID(){
	var downloadUUID = document.getElementById('docpath').value;
	$('#iframe_expBZYP').attr('src','<%= request.getContextPath() %>/PublishFileServlet?method=downloadFile&uuid='+downloadUUID);
	return false
}

function openMate(p){///hzb_hz/WebContent/pages/sysorg/hzb/Mate.jsp
	$h.openWin('mateWin','pages.sysorg.org.hzb.Mate','人员列表',850,650,'','<%=request.getContextPath()%>',null,p,true);
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
	width: 1280px;
}
.th0{width: 4%;}
.th1{width: 8%;}
.th2{width: 8%;}
.th3{width: 8%;}
.th4{width: 8%;}
.th5{width: 8%;}
.th6{width: 8%;}
.th7{width: 8%;}
.th9{width: 8%}
.th10{width: 8%}
.th11{width: 8%}
.th12{width: 12%}
</style>
</head>
<body>


	<div id="selectable">
	
	<table id="coordTable" cellspacing="0" width="100%" >
		<tr>
		    <td colspan="18" class="borderTop title borderRight">市直单位市管干部情况统计表</td>
		</tr>
		<tr>
			<th rowspan="4" class="th12">单位</th>
			<th colspan="4" class="th4">市管干部</th>
			<th colspan="2" class="th4">45岁左右<br>（74.07后）</th>
			<th colspan="2" class="th4">40岁左右<br>（79.07后）</th>
			<th rowspan="2" class="th4">35岁左右<br>（84.07<br>后）</th>
			<th rowspan="2" class="th0">女干部</th>
			<th rowspan="2" class="th0">党处<br>干部</th>
			<th colspan="2" >全日制学历</th>
		</tr>
		<tr>
			<th  class="th4">总数</th>
			<th  class="th4">正职</th>
			<th  class="th4">副职</th>
			<th  class="th4">其他</th>
			<th  class="th4">数量</th>
			<th  class="th4">占比</th>
			<th  class="th4">数量</th>
			<th  class="th4">占比</th>
			<th  class="th0">大学以上</th>
			<th  class="th0">研究生</th>
		</tr>
	</table>
</div>

<div  id="selectable2">
	<div>
	<table id="coordTable2" cellspacing="0" width="100%" >
		
	</table>
	</div>
</div>
<div id="selectable3">
	<table  cellspacing="0" width="100%" >
		<tr>
			<th  rowspan="4" class="th12" >合计</th>
			<th id="hj1"  class="th4"></th>
			<th id="hj2"  class="th4"></th>
			<th id="hj3"  class="th4"></th>
			<th id="hj4"  class="th4"></th>
			<th id="hj5"  class="th4"></th>
			<th id="hj6"  class="th4"></th>
			<th id="hj7"  class="th4"></th>
			<th id="hj8"  class="th4"></th>
			<th id="hj9"  class="th4"></th>
			<th id="hj10"  class="th0"></th>
			<th id="hj11"  class="th0"></th>
			<th id="hj12"  class="th0"></th>
			<th id="hj13"  class="th0"></th>
			
		</tr>
	</table>
</div>


<br/>

<div align="right"><img id="expSelect" onclick="expExcel()" src="<%=request.getContextPath()%>/images/tpbj/exp_excel.png" width="150px" 
style="cursor:pointer;margin-right:25px"/></div>
</body>
</html>
