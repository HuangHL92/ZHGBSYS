<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.hzb.QXSCJLDGBYGQKTJ"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<html lang="en">
<head>
<odin:head />
<odin:MDParam></odin:MDParam>
<odin:hidden property="docpath" />
<iframe  id="iframe_expBZYP" style="display: none;" src=""></iframe>
<script src="<%=request.getContextPath()%>/basejs/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<%
String ctxPath = request.getContextPath(); 
String subWinId = request.getParameter("subWinId");
%>
<script type="text/javascript">
var subWinId = '<%=subWinId%>';
var realParent = parent.Ext.getCmp(subWinId).initialConfig.thisWin;
var data = <%=QXSCJLDGBYGQKTJ.expData()%>;
$(function(){
	//alert(data.length);
	var ROWID={};
/* 	var hj41=0; */
	$.each(data, function (i,item) {
		//console.log(item['zzsp'])
		var tr = $('<tr><td class="td0"></td>'
				+'<td class="td1"></td><td class="td1"></td><td class="td1"></td><td class="td1"></td><td class="td1"></td><td class="td1"></td><td class="td1"></td><td class="td1"></td><td class="td1"></td>'
				+'<td class="td1"></td></tr>');
		var tds = $("td", tr);
		SetTDtext(tds[0],item["t00"]);SetTDtext(tds[1],item["t01"]);SetTDtext(tds[2],item["t02"]);SetTDtext(tds[3],item["t03"]);SetTDtext(tds[4],item["t04"]);
		SetTDtext(tds[5],item["t05"]);SetTDtext(tds[6],item["t06"]);SetTDtext(tds[7],item["t07"]);SetTDtext(tds[8],item["t08"]);SetTDtext(tds[9],item["t09"]);
		SetTDtext(tds[10],item["t10"]);
		ROWID[i]=item["t00"];
		$('#coordTablex').append(tr);

		
	});
	


	$('td:nth-child(2),td:nth-child(3),td:nth-child(4),td:nth-child(5),td:nth-child(8),td:nth-child(9),td:nth-child(10),td:nth-child(11)'
			,'#coordTablex tr').each(function (i, item) {
        $(this).addClass('pointer');
        $(this).on('click',function(){
        	var p = {maximizable:false,resizable:false};
        	p['colIndex'] = $(this).index();
        	p['query_type']="QXSCJLDGBYGQKTJ";
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
function openMate(p){///hzb_hz/WebContent/pages/sysorg/hzb/Mate.jsp
	$h.openWin('mateWin','pages.sysorg.org.hzb.Mate','人员列表',700,500,'','<%=request.getContextPath()%>',null,p,true);
}
function SetTDtext(td,v) {
  $(td).html((v==""||v==null||v=="null"||v=="0"||v=="0%"||v=="0.0%")?" ":v.replace(/\n/g,"<br/>"));
}
function expExcel(){
	var dataArray = [];
	
	var trend = $("#selectablex tr");
	$.each(trend,function(iend,itemend){
		var tdend = $("td",$(this));
		var tdEndArray = [];
		$.each(tdend,function (tiend, titemend){
			tdEndArray.push($(this).text());
	 		});
		if(tdEndArray.length>0){
	 		dataArray.push(tdEndArray);
	 	}
	});
	
	if(dataArray.length>0){
	    ajaxSubmit('expExcel',{"dataArray":Ext.encode(dataArray),"excelname":"处级领导干部"});
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
.td0{
	text-align: center;
	border-left: 1px solid #000000;
	border-top: 1px solid #000000;
	font-family: 宋体;
	font-weight: normal;
	font-size: 14px;
	border-collapse:collapse;
	width: 80px;
}
.td0a{
	text-align: center;
	border-left: 1px solid #000000;
	border-top: 1px solid #000000;
	font-family: 宋体;
	font-weight: normal;
	font-size: 14px;
	border-collapse:collapse;
	width: 1100px;
}
.td1{
	text-align: center;
	border-left: 1px solid #000000;
	border-top: 1px solid #000000;
	font-family: 宋体;
	font-weight: normal;
	font-size: 14px;
	border-collapse:collapse;
	width: 110px;
}
.td1x{
	text-align: center;
	border-left: 1px solid #000000;
	font-family: 宋体;
	font-weight: normal;
	font-size: 14px;
	border-collapse:collapse;
	width: 110px;
}
.td1a{
	text-align: center;
	border-left: 1px solid #000000;
	border-top: 1px solid #000000;
	font-family: 宋体;
	font-weight: normal;
	font-size: 14px;
	border-collapse:collapse;
	width: 330px;
}
.td1b{
	text-align: center;
	border-left: 1px solid #000000;
	border-top: 1px solid #000000;
	font-family: 宋体;
	font-weight: normal;
	font-size: 14px;
	border-collapse:collapse;
	width: 440px;
}
table {
	border-right: 1px solid #000000;
	border-bottom: 1px solid #000000;
	border-collapse:collapse;
}
td,th{
	height: 60px;
}
.colora{
	background: #CAE8EA ; 
}
.textleft{
	text-align: center;
	height: 40px;
}
.borderRight{
	border-right: 1px solid #C1DAD7;
}
#selectablex{
	overflow:auto;
}
.th0{
	width: 80px;
}
.th1{
	width: 110px;
}
.pointer{
	cursor: pointer;
}
.title{
	font-family: 方正小标宋简体;
	font-size: 28px;
	font-weight: normal;
}
</style>
</head>
<body>
	<DIV>
		<table class="colora" style="width: 1180px">
			<tr>
				<th class="td0" rowspan="3">单位</th>
				<th class="td0a title" colspan="10">处级领导干部</th>
			</tr>
			<tr>
				<th class="td1" rowspan="2">总数</th>
				<th class="td1a" colspan="3">40岁左右及以下县处级领导干部</th>
				<th class="td1" rowspan="2">40岁左右及以下处级干部占比</th>
				<th class="td1" rowspan="2">40岁左右及以下处级干部有基层工作经验的</th>
				<th class="td1b" colspan="4">40岁左右及以下全日制学历</th>
			</tr>
			<tr>
				<th class="td1x"></th>
				<th class="td1">正处</th>
				<th class="td1">副处</th>
				<th class="td1">博士</th>
				<th class="td1">硕士</th>
				<th class="td1">本科</th>
				<th class="td1">大专及以下</th>
			</tr>
		</table>
	</DIV>
	
	<div>
	<div id="selectablex">
	<table id="coordTablex" cellspacing="0" width="1180px" >

	</table>
	</div>
</div>
<br/>
<div align="right"><img id="expSelect" onclick="expExcel()" src="<%=request.getContextPath()%>/images/tpbj/exp_excel.png" width="150px" 
style="cursor:pointer;margin-right:8px"/></div>
</body>
</html>
