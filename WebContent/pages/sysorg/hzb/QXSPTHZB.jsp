<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.hzb.QXSPTHZB"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<html lang="en">
<head>
<odin:head />
<odin:MDParam></odin:MDParam>
<iframe  id="iframe_expBZYP" style="display: none;" src=""></iframe>
<script src="<%=request.getContextPath()%>/basejs/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<odin:hidden property="docpath" />
<odin:hidden property="dataArray" />

<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>

<%
String ctxPath = request.getContextPath(); 
String subWinId = request.getParameter("subWinId");
%>
<script type="text/javascript">
var subWinId = '<%=subWinId%>';
var realParent = parent.Ext.getCmp(subWinId).initialConfig.thisWin;
var data = <%=QXSPTHZB.expData()%>;
$(function(){
	//alert(data.length);
	var ROWID={};
	$.each(data, function (i,item) {
		//console.log(item['zzsp'])
		var tr = $('<tr><td  class="textleft">1</td><td></td><td></td><td></td><td></td><td></td><td></td><td class="borderRight"></td></tr>');
		var tds = $("td", tr);
		SetTDtext(tds[0],item["jgmc"]);
		SetTDtext(tds[1],item["bzzz"]);
		SetTDtext(tds[2],item["bzfz"]);
		SetTDtext(tds[3],item["bzspzz"]);
		SetTDtext(tds[4],item["bzspfz"]);
		
		SetTDtext(tds[5],item["bzqpzz"]);
		SetTDtext(tds[6],item["bzqpfz"]);
		
		SetTDtext(tds[7],item["bz"]==null?'':'<div  title="'+item["bz"]+'" style="text-overflow:ellipsis;width:99%;overflow: hidden;"><span>'+item["bz"]+'</span></div>');
		//console.log(i)
		ROWID[i]=item["b0111"];
		//coordTable
		$('#coordTable2').append(tr);
	});
	 $('td:nth-child(6),td:nth-child(7)','#coordTable2 tr:gt(0)').each(function (i, item) {
        if($(this).text()<0){
        	$(this).addClass('colorLight');
        }
		
    }); 
	
	
	
	 $('td:nth-child(4),td:nth-child(5)','#coordTable2 tr:gt(0)').each(function (i, item) {
        $(this).addClass('pointer');
        $(this).on('click',function(){
        	var p = {maximizable:false,resizable:false};
        	p['colIndex'] = $(this).index();
        	//alert($(this).parent().index())
        	p['b0111'] = ROWID[$(this).parent().index()-1];
        	p['query_type']="QXSPTHZB";
        	openMate(p);
        })
    }); 
});
function expExcel(){
	var dataArray = [];
	var tr = $("#coordTable2 tr");
	$.each(tr,function (i, item){
		var tdArray = [];
	 	var td = $("td",$(this));
	 	$.each(td,function (ti, titem){
	 		tdArray.push($(this).text());
		});
		if(tdArray.length>0){
	 	dataArray.push(tdArray)};
	});
	if(dataArray.length>0){
		//$("#dataArray").val(Ext.encode(dataArray));
		//radow.doEvent('expExcel');
		ajaxSubmit('expExcel',{'dataArray':Ext.encode(dataArray),"excelname":"区、县（市）平台领导班子职数统计表"})
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
  $(td).html((v==""||v==null||v=="null"||v=="0")?" ":v.replace(/\n/g,"<br/>"));
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
	border-left: 1px solid #C1DAD7;
	border-bottom: 1px solid #C1DAD7;
	font-family: 宋体;
	font-weight: normal;
}
td{
	text-align: center;
	padding: 5px;
}
.textleft{
	text-align: left;
}
.title{
	font-family: 方正小标宋简体;
	font-size: 28px;
	font-weight: normal;
	background: #FFF ; 
}
.borderRight{
	border-right: 1px solid #C1DAD7;
}
.borderTop{
	border-top: 1px solid #C1DAD7;
}

#selectable2{
	height: 400px;
	overflow:auto;
}
#selectable2 tr{
 background-color:expression('#F0F0F0,#FFFFFF'.split(',')[rowIndex%2]);
}
table{
	width: 1140px;
}
.th1{width: 15%;}.th2{width: 7%;}.th3{width: 7%;}.th4{width: 7%;}.th5{width: 7%;}
.th6{width: 7%;}.th7{width: 7%;}.th8{width: 43%;}

.th9{width: 4%;}.th10{width: 4%;}
.th11{width: 4%;}.th12{width: 4%;}

.th13{width: 4%;}.th14{width: 4%;}.th15{width: 4%;}
.th16{width: 4%;}.th17{width: 4%;}.th18{width: 4%;}.th19{width: 4%;}.th20{width: 4%;}

#selectable2,#selectable{
	margin-left: 10px;
}
#selectable{
	margin-top: 10px;
}
</style>
</head>
<body>
<div id="selectable">
	<table id="coordTable" cellspacing="0" width="100%" >
		<tr>
		    <th colspan="8" class="borderTop title borderRight">区、县（市）平台领导班子职数统计表</th>
		</tr>
		<tr>
			<th rowspan="2" class="th1">单位</th>
			<th colspan="2">班子职数</th>
			<th colspan="2">实配人数</th>
			<th colspan="2">缺配人数</th>
			<th rowspan="2" class="borderRight th8">缺配情况</th>
		</tr>
		<tr>
		    <th class="th2">正职</th>
		    <th class="th3">副职</th>
		    <th class="th4">正职</th>
		    <th class="th5">副职</th>
		    <th class="th6">正职</th>
		    <th class="th7">副职</th>
		</tr>
		
	</table>
</div>
<div  id="selectable2">
	<div>
	<table id="coordTable2" cellspacing="0" width="100%" >
		<tr>
			<th class="th1"></th>
			<th class="th2"></th>
			<th class="th3"></th>
			<th class="th4"></th>
			<th class="th5"></th>
			<th class="th6"></th>
			<th class="th7"></th>
			<th class="th8"></th>
		</tr>
		
	</table>
	</div>
</div>
<div>
<img id="expSelect" onclick="expExcel()" src="<%=request.getContextPath()%>/images/tpbj/exp_excel.png" width="150" align="right" style="cursor:pointer;margin-right:49px">
</div>
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
// 					Ext.Msg.hide();
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


</body>
</html>
