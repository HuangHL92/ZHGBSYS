<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.hzb.XSQLDBZTJ"%>
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
var data = <%=XSQLDBZTJ.expData()%>;
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
	var hj11=0;
	var hj12=0;
	var hj13=0;
	var hj14=0;
 	 $.each(data, function (i,item) {
		//console.log(item['zzsp'])
		var tr = $('<tr><td></td>'+
		'<td class="th1"></td>'+
		'<td class="th1"></td><td class="th1"></td><td class="th1"></td><td class="th1"></td>'+
		'<td class="th1"></td><td class="th1"></td><td class="th1"></td><td class="th1"></td>'+
		'<td class="th1"></td><td class="th1"></td>'+
		'<td class="th1"></td><td class="th1"></td><td class="th1"></td></tr>');
		var tds = $("td", tr);
		SetTDtext(tds[0],item["b0101"]);
		SetTDtext(tds[1],item["dzbz"]);hj1=hj1+parseInt(item["dzbz"]);
		SetTDtext(tds[2],item["dz1980"]);hj2=hj2+parseInt(item["dz1980"]);
		SetTDtext(tds[3],item["dz1985"]);hj3=hj3+parseInt(item["dz1985"]);
		SetTDtext(tds[4],item["dzxjzz"]);hj4=hj4+parseInt(item["dzxjzz"]);
		SetTDtext(tds[5],item["dzxjsj"]);
		SetTDtext(tds[6],item["dwn"]);hj6=hj6+parseInt(item["dwn"]);
		SetTDtext(tds[7],item["dwnzz"]);hj7=hj7+parseInt(item["dwnzz"]);
		SetTDtext(tds[8],item["zfn"]);hj8=hj8+parseInt(item["zfn"]);
		SetTDtext(tds[9],item["zfnzz"]);hj9=hj9+parseInt(item["zfnzz"]);
		SetTDtext(tds[10],item["dz1975zz"]);hj10=hj10+parseInt(item["dz1975zz"]);
		SetTDtext(tds[11],item["dz1980zz"]);hj11=hj11+parseInt(item["dz1980zz"]);
		SetTDtext(tds[12],item["zxzs"]);hj12=hj12+parseInt(item["zxzs"]);
		SetTDtext(tds[13],item["zxdwfzx"]);hj13=hj13+parseInt(item["zxdwfzx"]);
		SetTDtext(tds[14],item["hj"]);hj14=hj14+parseInt(item["hj"]);
		ROWID[i]=item["b0111"];
		$('#coordTable').append(tr);
		
		
	});  
 	 

 	document.getElementById("hj1").innerHTML=hj1;
 	document.getElementById("hj2").innerHTML=hj2;
 	document.getElementById("hj3").innerHTML=hj3;
 	document.getElementById("hj4").innerHTML=hj4;
 	document.getElementById("hj5").innerHTML=hj5;
 	document.getElementById("hj6").innerHTML=hj6;
 	document.getElementById("hj7").innerHTML=hj7;
 	document.getElementById("hj8").innerHTML=hj8;
 	document.getElementById("hj9").innerHTML=hj9;
 	document.getElementById("hj10").innerHTML=hj10;
 	document.getElementById("hj11").innerHTML=hj11;
 	document.getElementById("hj12").innerHTML=hj12;
 	document.getElementById("hj13").innerHTML=hj13;
 	document.getElementById("hj14").innerHTML=hj14;
	/*$('td:nth-child(5),td:nth-child(6),td:nth-child(7)','#coordTable2 tr:gt(0)').each(function (i, item) {
        if($(this).text()<0){
        	$(this).addClass('colorLight');
        }
		
    });*/
	
	$('td:nth-child(2),td:nth-child(3),td:nth-child(4),td:nth-child(5),td:nth-child(7),'+
		'td:nth-child(8),td:nth-child(9),td:nth-child(10),td:nth-child(11),td:nth-child(12),'+
		'td:nth-child(13),td:nth-child(14),td:nth-child(15)'
			,'#coordTable tr').each(function (i, item) {
        $(this).addClass('pointer');
        $(this).on('click',function(){
        	var p = {maximizable:false,resizable:false};
        	p['colIndex'] = $(this).index();
        	p['b0111'] = ROWID[$(this).parent().index()-5];
        	//alert($(this).parent().index())
        	p['query_type']="XSQLDBZTJ";
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
	    ajaxSubmit('expExcel',{"dataArray":Ext.encode(dataArray),"excelname":"�����أ��У������쵼�ɲ����ͳ�Ʊ�"});
	}
}

function downloadByUUID(){
	var downloadUUID = document.getElementById('docpath').value;
	$('#iframe_expBZYP').attr('src','<%= request.getContextPath() %>/PublishFileServlet?method=downloadFile&uuid='+downloadUUID);
	return false
} --%>

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
	border:1px solid;
	background: #CAE8EA ; 
	text-align: center;
	height:50px;
}
th,td{
	border-left: 1px solid #C1DAD7;
	border-bottom: 1px solid #C1DAD7;
	font-family: ����;
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
	overflow-y:auto;
	overflow-x:hidden;
	height:550px;
	margin-left:23px;
}
#selectable td{
	height:30px
}
#selectable2{
	overflow-y:auto;
	height:315px;
	margin-left:23px;
}


table{
	width: 1350px;
}
.th1{width: 6.5%;}.th2{width: 9%;}.th3{width: 20%;}.th4{width: 20%;}.th5{width: 20%;}
.th5{width: 40%;}.th6{width: 20%;text-align: center;}
</style>
</head>
<body>


	<div id="selectable">
	
	<table id="coordTable" cellspacing="0" width="100%" >
		<tr>
		    <td colspan="15" class="borderTop title borderRight">�أ��С������쵼�����й����ͳ��</td>
		</tr>
		<tr>
			<th rowspan="4"  class="th2">��λ</th>
			<th colspan="14" >�쵼����</th>
		</tr>
		<tr>
			<th colspan="9" >�������ӳ�Ա������ְ��</th>
			<th colspan="2" >������ְ</th>
			<th colspan="3" >��Э����</th>
		</tr>
		<tr>
			<th rowspan="2" class="th1" style="border-top:none">ʵ��</th>
			<th rowspan="2" class="th1">1980��3��<br/>�����</th>
			<th rowspan="2" class="th1">1985��3��<br/>�����</th>
			<th colspan="2" >����ֵ���<br/>��ְ������</th>
			<th colspan="4" >Ů�ɲ�</th>
			<th rowspan="2" class="th1">1975��5��<br/>�����</th>
			<th rowspan="2" class="th1">1980��3��<br/>�����</th>
			<th colspan="3" >��ϯ����ϯ��</th>
		</tr>
		<tr>
		    <th class="th1" style="border-top:none"> </th>
		    <th class="th1" >�������<br/>�Ǿ�����</th>
		    <th class="th1" >��ί</th>
		    <th class="th1" >��ί��ְ</th>
		    <th class="th1" >����</th>
		    <th class="th1" >������ְ</th>
		    <th class="th1" style="border-top:none"> </th>
		    <th class="th1" >����<br/>����ϯ</th>
		    <th class="th1" >����ʱ��<br/>�ϼ�����<br/>�������</th>
		</tr>
	</table>
</div>

<div  id="selectable2">
	<div>
	<table id="coordTable2" cellspacing="0" width="100%" >
		<tr style="background: #d0d0d0">
		<td class="th2" >�ϼ�</td>
		<td class="th1" id="hj1"></td>
		<td class="th1" id="hj2"></td>
		<td class="th1" id="hj3"></td>
		<td class="th1" id="hj4"></td>
		<td class="th1" id="hj5"></td>
		<td class="th1" id="hj6"></td>
		<td class="th1" id="hj7"></td>
		<td class="th1" id="hj8"></td>
		<td class="th1" id="hj9"></td>
		<td class="th1" id="hj10"></td>
		<td class="th1" id="hj11"></td>
		<td class="th1" id="hj12"></td>
		<td class="th1" id="hj13"></td>
		<td class="th1" id="hj14"></td>
		
		</tr>
	</table>
	</div>
</div>
<!-- <div id="selectable3">
	<table  cellspacing="0" width="100%" >
		<tr>
			<th  class="th6" >�ϼ�</th>
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
