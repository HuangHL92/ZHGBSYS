<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.hzb.QSXZDZZZPBB"%>
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
        timeout :300000,//���������
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

        if("�����ɹ���"!=cfg.mainMessage){
          Ext.Msg.hide();
          Ext.Msg.alert('ϵͳ��ʾ:',cfg.mainMessage);

        }else{
//           Ext.Msg.hide();
        }
      }else{
        //Ext.Msg.hide();

        /* if(cfg.mainMessage.indexOf("<br/>")>0){

          $h.alert('ϵͳ��ʾ',cfg.mainMessage,null,380);
          return;
        } */

        if("�����ɹ���"!=cfg.mainMessage){
          Ext.Msg.hide();
          Ext.Msg.alert('ϵͳ��ʾ:',cfg.mainMessage);
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
      alert("�����쳣��");
    }
  });
}
</script>
<script type="text/javascript">
var subWinId = '<%=subWinId%>';
var realParent = parent.Ext.getCmp(subWinId).initialConfig.thisWin;
var data = <%=QSXZDZZZPBB.expData()%>;
$(function(){
	//alert(data.length);
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
	$.each(data, function (i,item) {
		//console.log(item['zzsp'])
		var tr = 
		$('<tr><td class="th1 textleft"></th><td class="th2"></td><td class="th3"></td><td class="th4"></td><td class="th5"></td>'+
		'<td class="th6"></td><td class="th7"></td><td class="th8"></td><td class="th9"></td><td class="th10"></td><td class="th11 borderRight"></td></tr>');
		var tds = $("td", tr);
		SetTDtext(tds[0],item["ssxq"]);
		SetTDtext(tds[1],item["xzsl"]);
		SetTDtext(tds[2],item["zszz"]);
		SetTDtext(tds[3],item["agezz38"]);
		SetTDtext(tds[4],item["zbzz"]);
		SetTDtext(tds[5],item["zssj"]);
		SetTDtext(tds[6],item["agesj38"]);
		SetTDtext(tds[7],item["zbsj"]);
		SetTDtext(tds[8],item["zszr"]);
		SetTDtext(tds[9],item["agezr38"]);
		SetTDtext(tds[10],item["zbzr"]);
		ROWID[i]=item["b0111"];
		$('#coordTable2').append(tr);
		hj1=hj1+parseInt(item["xzsl"]);
		hj2=hj2+parseInt(item["zszz"]);
		hj3=hj3+parseInt(item["agezz38"]);
		hj5=hj5+parseInt(item["zssj"]);
		hj6=hj6+parseInt(item["agesj38"]);
		hj8=hj8+parseInt(item["zszr"]);
		hj9=hj9+parseInt(item["agezr38"])
	});
	document.getElementById("hj1").innerHTML=hj1;
	document.getElementById("hj2").innerHTML=hj2;
	document.getElementById("hj3").innerHTML=hj3;
	document.getElementById("hj5").innerHTML=hj5;
	document.getElementById("hj6").innerHTML=hj6;
	document.getElementById("hj8").innerHTML=hj8;
	document.getElementById("hj9").innerHTML=hj9;
	hj4=Math.round(hj3*10000/hj2)/100+"%";
	document.getElementById("hj4").innerHTML=hj4;
	hj7=Math.round(hj6*10000/hj5)/100+"%";
	document.getElementById("hj7").innerHTML=hj7;
	hj10=Math.round(hj9*10000/hj8)/100+"%";
	document.getElementById("hj10").innerHTML=hj10;
	/*$('td:nth-child(5),td:nth-child(6),td:nth-child(7)','#coordTable2 tr:gt(0)').each(function (i, item) {
        if($(this).text()<0){
        	$(this).addClass('colorLight');
        }
		
    });*/
	
	$('td:nth-child(3),td:nth-child(4),td:nth-child(6),td:nth-child(7),td:nth-child(9),td:nth-child(10)','#coordTable2 tr').each(function (i, item) {
        $(this).addClass('pointer');
        $(this).on('click',function(){
        	var p = {maximizable:false,resizable:false};
        	p['colIndex'] = $(this).index();
        	//alert($(this).parent().index())
        	p['b0111'] = ROWID[$(this).parent().index()];
        	p['query_type']="QSXZDZZZPBB";
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
	    ajaxSubmit('expExcel',{"dataArray":Ext.encode(dataArray),"excelname":"ȫ�����򣨽ֵ���������ְ�䱸���ͳ�Ʊ�"});
	}
}

function downloadByUUID(){
	var downloadUUID = document.getElementById('docpath').value;
	$('#iframe_expBZYP').attr('src','<%= request.getContextPath() %>/PublishFileServlet?method=downloadFile&uuid='+downloadUUID);
	return false
}

function openMate(p){///hzb_hz/WebContent/pages/sysorg/hzb/Mate.jsp
	$h.openWin('mateWin','pages.sysorg.org.hzb.Mate','��Ա�б�',700,500,'','<%=request.getContextPath()%>',null,p,true);
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
	height: 50px;
}
th,td{
	border-left: 1px solid #C1DAD7;
	border-bottom: 1px solid #C1DAD7;
	font-family: ����;
	font-weight: normal;
	height: 50px;
}
td{
	text-align: center;
	height: 50px;
}
.textleft{
	text-align: center;
}
.title{
	font-family: ����С���μ���;
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
	width: 1140px;
}
.th1{width: 10%; text-align: center;}.th2{width: 9%;}.th3{width: 6%;}.th4{width: 15%;}.th5{width: 6%;}.th6{width: 6%;}
.th7{width: 15%;}.th8{width: 6%;}.th9{width: 6%;}.th10{width: 15%;}.th11{width: 6%;}
.th12{width: 27%;}.th13{width: 27%;}.th14{width: 27%;}
</style>
</head>
<body>

<div id="selectable">
	
	<table id="coordTable" cellspacing="0" width="100%" >
		<tr>
		    <td colspan="11" class="borderTop title borderRight">ȫ�����򣨽ֵ���������ְ�䱸���ͳ�Ʊ�</td>
		</tr>
		<tr>
			<th rowspan="2" class="th1">��λ</th>
			<th rowspan="2" class="th2">���򣨽ֵ�������</th>
			<th colspan="3" class="th12">ʵ���䱸���򣨽ֵ���������ְ</th>
			<th colspan="3" class="th13">ʵ���䱸���򣨽ֵ�����������ί���</th>
			<th colspan="3" class="th14">ʵ���䱸���򣨽ֵ����򳤣����Σ�</th>
		</tr>
		<tr>
		    <th class="th3">����</th>
		    <th class="th4">35�����ҵ�����ְ</th>
		    <th class="th5">ռ��</th>
		    <th class="th6">����</th>
		    <th class="th7">35�����ҵ�������ί���</th>
		    <th class="th8">ռ��</th>
		    <th class="th9">����</th>
		    <th class="th10">35�������򳤣����Σ�</th>
		    <th class="th11">ռ��</th>
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
			<th  class="th1" >�ϼ�</th>
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
</div>

<br/>
<div align="right"><img id="expSelect" onclick="expExcel()" src="<%=request.getContextPath()%>/images/tpbj/exp_excel.png" width="150px" 
style="cursor:pointer;margin-right:25px"/></div>
</body>
</html>