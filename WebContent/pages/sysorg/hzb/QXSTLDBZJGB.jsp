<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.hzb.QXSTLDBZJGB"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<html lang="en">
<head>
<odin:head />
<odin:MDParam></odin:MDParam>
<script src="<%=request.getContextPath()%>/basejs/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>

<odin:hidden property="docpath" />
<iframe  id="iframe_expBZYP" style="display: none;" src=""></iframe>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>

<%
String ctxPath = request.getContextPath(); 
String subWinId = request.getParameter("subWinId");
%>
<script type="text/javascript">
var subWinId = '<%=subWinId%>';
var realParent = parent.Ext.getCmp(subWinId).initialConfig.thisWin;
var data = <%=QXSTLDBZJGB.expData()%>;
$(function(){
	//alert(data.length);
	var ROWID={};
	$.each(data, function (i,item) {
		//console.log(item['zzsp'])
		var tr;
		if(i%4==0){
			tr = $('<tr><td rowspan="4" class="textleft"></td><td class="th2"></td><td class="th3"></td>'+
					'<td class="th3"></td><td class="th4"></td><td class="th5"></td><td class="th6"></td><td class="th7 borderRight"></td></tr>');
		}else{
			tr = $('<tr><td class="th2"></td><td class="th3"></td><td class="th3"></td><td class="th4"></td>'+
					'<td class="th5"></td><td class="th6"></td><td class="th7 borderRight"></td></tr>');
		}
		//var tr = $('<tr><td  class="textleft"></td><td></td><td></td><td></td><td></td><td></td><td></td><td class="borderRight"></td></tr>');
		var tds = $("td", tr);
		if(i%4==0){
			SetTDtext(tds[0],item["qxmc"]);
			SetTDtext(tds[1],item["dwmc"]);
			SetTDtext(tds[2],item["ngbyp"]);
			SetTDtext(tds[3],item["ngbsp"]);
			SetTDtext(tds[4],item["ngbqp"]);
			SetTDtext(tds[5],item["dwgbyp"]);
			SetTDtext(tds[6],item["dwgbsp"]);
			SetTDtext(tds[7],item["dwgbqp"]);
		}else{
			SetTDtext(tds[0],item["dwmc"]);
			SetTDtext(tds[1],item["ngbyp"]);
			SetTDtext(tds[2],item["ngbsp"]);
			SetTDtext(tds[3],item["ngbqp"]);
			SetTDtext(tds[4],item["dwgbyp"]);
			SetTDtext(tds[5],item["dwgbsp"]);
			SetTDtext(tds[6],item["dwgbqp"]);
		}
		
		//console.log(i)
		ROWID[i]=item["b0111"];
		//coordTable
		$('#coordTable2').append(tr);
	});
	/*$('td:nth-child(5),td:nth-child(6),td:nth-child(7)','#coordTable2 tr:gt(0)').each(function (i, item) {
        if($(this).text()<0){
        	$(this).addClass('colorLight');
        }
		
    });
	*/
	
	
	$('td:nth-child(4),td:nth-child(7)','#coordTable2 tr:nth-child(4n+1)').each(function (i, item) {
		$(this).addClass('pointer');
        $(this).on('click',function(){
        	var p = {maximizable:false,resizable:false};
        	p['colIndex'] = $(this).index();
        	//alert($(this).parent().index())
        	p['b0111'] = ROWID[$(this).parent().index()];
        	p['query_type']="QXSTLDBZJGB";
        	openMate(p);
        })
        
    });
	$('td:nth-child(3),td:nth-child(6)','#coordTable2 tr:nth-child(4n+3),tr:nth-child(4n),tr:nth-child(4n+2)').each(function (i, item) {
		$(this).addClass('pointer');
        $(this).on('click',function(){
        	var p = {maximizable:false,resizable:false};
        	p['colIndex'] = $(this).index()+1;
        	//alert($(this).parent().index())
        	p['b0111'] = ROWID[$(this).parent().index()];
        	p['query_type']="QXSTLDBZJGB";
        	openMate(p);
        })
    });
});

function openMate(p){///hzb_hz/WebContent/pages/sysorg/hzb/Mate.jsp
	$h.openWin('mateWin','pages.sysorg.org.hzb.Mate','人员列表',700,500,'','<%=request.getContextPath()%>',null,p,true);
}
function SetTDtext(td,v) {
  $(td).html((v==""||v==null||v=="null"||v=="0"||v=="-1"||v=="-2"||v=="-3"||v=="-4")?" ":v.replace(/\n/g,"<br/>"));
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
	

	if(dataArray.length>0){
	    ajaxSubmit('expExcel1',{"dataArray":Ext.encode(dataArray),"excelname":"区、县（市）四套领导班子结构性干部情况统计表"});
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
	height:50px;
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
	height:365px;
	margin-left:23px;
}
table{
	width: 1140px;
}
.th1{width: 20%;}.th2{width: 20%;}.th3{width: 10%;}.th4{width: 10%;}.th5{width: 10%;}
.th6{width: 10%;}.th7{width: 10%;}.th8{width: 10%;}
</style>
</head>
<body>
<div id="selectable">
	<table id="coordTable" cellspacing="0" width="100%" >
		<tr>
		    <th colspan="8" class="borderTop title borderRight">区、县（市）四套领导班子结构性干部情况统计表</th>
		</tr>
		<tr>
			<th rowspan="2" class="th1">区、县（市）</th>
			<th rowspan="2" class="th2">类别</th>
			<th colspan="3">女干部情况</th>
			<th colspan="3">党外干部情况</th>
		</tr>
		<tr>
		    <th >应配</th>
		    <th >实配</th>
		    <th >缺配</th>
		    <th >应配</th>
		    <th >实配</th>
		    <th >缺配</th>
		</tr>
	</table>
</div>
<div  id="selectable2">
	
	<div >
	<table id="coordTable2" cellspacing="0" width="100%" >
		
		
	</table>
	</div>
</div>
<br/>
<div align="right"><img id="expSelect" onclick="expExcel()" src="<%=request.getContextPath()%>/images/tpbj/exp_excel.png" width="150px" 
style="cursor:pointer;margin-right:23px"/></div>

</body>
</html>
