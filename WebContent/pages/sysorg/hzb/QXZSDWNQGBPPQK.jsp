<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.hzb.QXZSDWNQGBPBQK"%>
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
var data = <%=QXZSDWNQGBPBQK.expData()%>;
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
	var index = 1;
	$.each(data, function (i,item) {
		var tr = $('<tr><td class="th0 textleft"><td class="th4"></td><td class="th4"></td><td class="th4"></td><td class="th4"></td><td class="th4"></td><td class="th4"/td><td class="th4"/td><td class="th4"/td><td class="th4"/td><td class="th4"/td><td class="th4"/td></tr>');
		var tds = $("td", tr);
		
		SetTDtext(tds[0],item["rownum"]);
		SetTDtext(tds[1],item["ssxq"]);
		SetTDtext(tds[2],item["zsdwzs"]);
		SetTDtext(tds[3],item["dzgzzs"]);
		SetTDtext(tds[4],item["zsdwzsry"]);
		SetTDtext(tds[5],item["dzgzzsry"]);
		SetTDtext(tds[6],item["age35yp"]);
		SetTDtext(tds[7],item["age35sb"]);
		SetTDtext(tds[8],item["age35db"]);
		SetTDtext(tds[9],item["age35zz"]);
		SetTDtext(tds[10],item["age30fz"]);
		SetTDtext(tds[11],item["age35dz"]);
		ROWID[i]=item["b0111"];
		$('#coordTable2').append(tr);
		hj1=hj1+parseInt(item["zsdwzs"]);
		hj2=hj2+parseInt(item["dzgzzs"]);
		hj3=hj3+parseInt(item["zsdwzsry"]);
		hj4=hj4+parseInt(item["dzgzzsry"]);
		hj5=hj5+parseInt(item["age35yp"]);
		hj6=hj6+parseInt(item["age35sb"]);
		hj7=hj7+parseInt(item["age35db"]);
		hj8=hj8+parseInt(item["age35zz"]);
		hj9=hj9+parseInt(item["age30fz"]);
		hj10=hj10+parseInt(item["age35dz"]);
	});
	/* document.getElementById("hj1").innerHTML=hj1;
	document.getElementById("hj2").innerHTML=hj2;
	document.getElementById("hj3").innerHTML=hj3;
	document.getElementById("hj4").innerHTML=hj4;
	document.getElementById("hj5").innerHTML=hj5;
	document.getElementById("hj6").innerHTML=hj6;
	document.getElementById("hj7").innerHTML=hj7;
	document.getElementById("hj8").innerHTML=hj8;
	document.getElementById("hj9").innerHTML=hj9;
	document.getElementById("hj10").innerHTML=hj10; */
	document.getElementById("hj1").innerHTML=hj1;
	document.getElementById("hj2").innerHTML=hj2;
	document.getElementById("hj3").innerHTML=hj3;
	document.getElementById("hj4").innerHTML=hj4;
	document.getElementById("hj5").innerHTML=hj5;
	document.getElementById("hj6").innerHTML=hj6;
	hj7=Math.round(hj6*1000/hj3)/10+"%";
	document.getElementById("hj7").innerHTML=hj7;
	document.getElementById("hj8").innerHTML=hj8;
	document.getElementById("hj9").innerHTML=hj9;
	document.getElementById("hj10").innerHTML=hj10;

	//hj3=Math.round(hj2*10000/hj1)/100+"%";
	
	
	
	$('td:nth-child(8),td:nth-child(10),td:nth-child(11)','#coordTable2 tr:gt(0)').each(function (i, item) {
        if($(this).text()<0){
        	$(this).addClass('colorLight');
        }
		
    });
	
	$('td:nth-child(5),td:nth-child(6),td:nth-child(8),td:nth-child(10),td:nth-child(11)','#coordTable2 tr').each(function (i, item) {
        $(this).addClass('pointer');
        $(this).on('click',function(){
        	var p = {maximizable:false,resizable:false};
        	p['colIndex'] = $(this).index();
        	//alert($(this).parent().index())
        	console.log($(this).parent().index())
        	p['b0111'] = ROWID[$(this).parent().index()];
        	p['query_type']="QXZSDWNQGBPBQK";
        	openMate(p);
        })
    }); 
    
});
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
	
	console.log(dataArray)
	//alert(dataArray.length);
	if(dataArray.length>0){
	    ajaxSubmit('expExcel',{"dataArray":Ext.encode(dataArray),"excelname":"���أ��У�ֱ����λ����ɲ��䱸���"});
	}
}

function downloadByUUID(){
	var downloadUUID = document.getElementById('docpath').value;
	$('#iframe_expBZYP').attr('src','<%= request.getContextPath() %>/PublishFileServlet?method=downloadFile&uuid='+downloadUUID);
	return false
}

function openMate(p){///hzb_hz/WebContent/pages/sysorg/hzb/Mate.jsp
	$h.openWin('mateWin','pages.sysorg.org.hzb.Mate','��Ա�б�',850,650,'','<%=request.getContextPath()%>',null,p,true);
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
.th0{width: 4.3%;}
.th1{width: 8.3%;}
.th2{width: 8.3%;}
.th3{width: 8.3%;}
.th4{width: 8.3%;}
.th5{width: 8.3%;}
.th6{width: 8.3%;}
.th7{width: 8.3%;}
.th9{width: 8.3%}
.th10{width: 8.3%}
.th11{width: 8.3%}
.th12{width: 12.6%}
</style>
</head>
<body>


	<div id="selectable">
	
	<table id="coordTable" cellspacing="0" width="100%" >
		<tr>
		    <td colspan="15" class="borderTop title borderRight">���أ��У�ֱ����λ����ɲ��䱸���</td>
		</tr>
		<tr>
			<th rowspan="2" class="th0">���</th>
			<th rowspan="2" class="th4">���أ��У�</th>
			<th colspan="2" class="th4">ֱ����λ</th>
			<th colspan="2" class="th4">ֱ����λ�쵼���ӳ�Ա</th>
			<th colspan="3" class="th4">35�����ң�����37�꣩�쵼���ӳ�Ա�䱸���</th>
			<th rowspan="2" class="th4">35�����ң�����37�꣩��ְ����</th>
			<th rowspan="2" class="th4">30�����ң�����32�꣩��ְ����</th>
			<th rowspan="2" class="th4">�䱸35�����ң�����37�꣩�ɲ��ĵ���������</th>
		</tr>
		<tr>
			<th  class="th4">����</th>
			<th  class="th4">����������������</th>
			<th  class="th4">����</th>
			<th  class="th4">����������������</th>
			<th  class="th4">Ӧ������һ��ﵽ15%��</th>
			<th  class="th4">ʵ����</th>
			<th  class="th4">ռ��</th>
		</tr>
	</table>
</div>

<div id="selectable3">
	<table  cellspacing="0" width="100%" >
		<tr>
			<th class="th12" style="display:none" ></th>
			<th colspan="2" class="th12" >�ϼ�</th>
			<th id="hj1"  class="th4"></th>
			<th id="hj2"  class="th4"></th>
			<th id="hj3"  class="th4"></th>
			<th id="hj4"  class="th4"></th>
			<th id="hj5"  class="th4"></th>
			<th id="hj6"  class="th4"></th>
			<th id="hj7"  class="th4"></th>
			<th id="hj8"  class="th4"></th>
			<th id="hj9"  class="th4"></th>
			<th id="hj10"  class="th4"></th>
			
		</tr>
	</table>
</div>
<div  id="selectable2">
	<div>
	<table id="coordTable2" cellspacing="0" width="100%" >
		
	</table>
	</div>
</div>


<br/>

<div align="right"><img id="expSelect" onclick="expExcel()" src="<%=request.getContextPath()%>/images/tpbj/exp_excel.png" width="150px" 
style="cursor:pointer;margin-right:25px"/></div>
</body>
</html>
