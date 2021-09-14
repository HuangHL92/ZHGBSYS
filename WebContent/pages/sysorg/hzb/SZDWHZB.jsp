<%@page import="com.insigma.siis.local.pagemodel.sysorg.org.hzb.SZDWHZB"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<html lang="en">
<head>
<odin:head />
<odin:MDParam></odin:MDParam>
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
var data = <%=SZDWHZB.expData(request.getParameter("gqgx"))%>;
$(function(){
	//alert(data.length);
	
	$.each(data, function (i,item) {
		//console.log(item['zzsp'])
		var tr = $('<tr><td  class="textleft">1</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td  class="input-editor"></td><td  class="input-editor"></td><td class="input-editor"></td><td class="borderRight input-editor"></td></tr>');
		var tds = $("td", tr);
		var index = 0;
		SetTDtext(tds[index++],item["jgmc"]);
		//SetTDtext(tds[1],item["jgjb"]);
		SetTDtext(tds[index++],item["xjhd"]);
		SetTDtext(tds[index++],item["xjsp"]);
		//SetTDtext(tds[4],item["zzqp"]);
		//SetTDtext(tds[5],item["fzqp"]);
		//SetTDtext(tds[6],item["zsqp"]);
		
		SetTDtext(tds[index++],item["zzhd"]);
		SetTDtext(tds[index++],item["zzsp"]);
		SetTDtext(tds[index++],item["fzhd"]);
		SetTDtext(tds[index++],item["fzsp"]);
		SetTDtext(tds[index++],item["zshd"]);
		SetTDtext(tds[index++],item["zssp"]);
		SetTDtext(tds[index++],item["bzzs"]);
		
		SetTDtext(tds[index++],item["b0234"]);
		SetTDtext(tds[index++],item["b0235"]);
		SetTDtext(tds[index++],item["b0236"]);
		
		SetTDtext(tds[index++],item["bz"]==null?'':'<div  title="'+item["bz"]+'" style="text-overflow:ellipsis;width:100%;overflow: hidden;"><span>'+item["bz"]+'</span></div>');
		//console.log(i)
		
		GLOBLE['ROWID'][i]=item["b0111"];
		
		GLOBLE['ID_ROWINFO'][item["b0111"]]={"b0180":item["bz"],"b0234":item["b0234"],"b0235":item["b0235"],"b0236":item["b0236"]};
		
		//coordTable
		$('#coordTable2').append(tr);
	});
	/* $('td:nth-child(5),td:nth-child(6),td:nth-child(7)','#coordTable2 tr:gt(0)').each(function (i, item) {
        if($(this).text()<0){
        	//$(this).addClass('colorLight');
        }
		
    }); */
	
	
	
	$('td:nth-child(5),td:nth-child(7),td:nth-child(9),td:nth-child(10)','#coordTable2 tr:gt(0)').each(function (i, item) {
        $(this).addClass('pointer');
        $(this).bind('click',function(){
        	var p = {maximizable:false,resizable:false};
        	p['colIndex'] = $(this).index();
        	//alert($(this).parent().index())
        	p['b0111'] = GetRowid($(this).parent().index()-1);
        	p['query_type']="SZDWHZB";
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
		ajaxSubmit1('expExcel',{'dataArray':Ext.encode(dataArray),"excelname":"市直单位职数情况汇总表"})
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
.input-editor{
	font-size: 12px;
}
.colorLight{
	color: red;
}
.input-editor{
	position: relative;
	padding: 6px 8px;
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
table{
	width: 1140px;
}

#selectable2 tr{
 background-color:expression('#F0F0F0,#FFFFFF'.split(',')[rowIndex%2]);
}
.th1{width: 30%;}.th2{width: 3%;}/*9*/.th3{width: 10%;}.th4{width: 11%;}/*3*/
.th5{width: 4%;}
.th6{width: 5%;}.th7{width: 5%;}.th8{width: 5%;}.th9{width: 5%;}.th10{width: 5%;}
.th11{width: 5%;}.th12{width: 5%;}.th21{width: 5%;}.th22{width: 10%;}.th23{width: 5%;}

.width0{
	width: 0px;display: none;
}

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
<iframe  id="iframe_expBZYP" style="display: none;" src=""></iframe>
<div id="selectable">
	<table id="coordTable" cellspacing="0" width="100%" >
		<tr>
		    <th colspan="15" class="borderTop title borderRight"><%="gqgx".equals(request.getParameter("gqgx"))?"国企高校职数情况汇总表":"市直单位职数情况汇总表" %></th>
		</tr>
		<tr>
			<th rowspan="3" class="th1">单位名称</th>
			<!-- <th rowspan="3" class="th2">机构级别</th> -->
			<th colspan="9">核定单位领导情况</th>
			<th colspan="3">空缺情况</th>
			<!-- <th colspan="8">核定领导职数对应职务级别</th> -->
			<th rowspan="3" class="borderRight th3">超配情况</th>
		</tr>
		<tr>
		    <th colspan="2">小计</th>
		    <th colspan="2">正职</th>
		    <th colspan="2">副职（不含三总师）</th>
		    <th colspan="2">三总师</th>
		    <th rowspan="2" class="th2">不占职数</th>
		    <th rowspan="2" class="th4">空缺正职</th>
		    <th rowspan="2" class="th4">空缺副职</th>
		    <th rowspan="2" class="th4">空缺岗位</th>
		    <!-- <th colspan="2">小计</th>
		    <th colspan="2">正厅职数</th>
		    <th colspan="2">副厅职数</th>
		    <th colspan="2">副局职数</th> -->
		</tr>
		<tr>
		    <th rowspan="1" class="th2">核定</th>
		    <th rowspan="1" class="th2">实配</th>
		   <!--  <th colspan="3">超缺配统计</th> -->
		    
		    
		    
		    <th rowspan="1"  class="th2">核定</th>
		    <th rowspan="1"  class="th2">实配</th>
		    <th rowspan="1"  class="th2">核定</th>
		    <th rowspan="1"  class="th2">实配</th>
		    <th rowspan="1"  class="th2">核定</th>
		    <th rowspan="1"  class="th2">实配</th>
		    <!-- <th class="th12">核定</th>
		    <th class="th13">实配</th>
		    <th class="th14">核定</th>
		    <th class="th15">实配</th>
		    <th class="th16">核定</th>
		    <th class="th17">实配</th>
		    <th class="th18">核定</th>
		    <th class="th19">实配</th> -->
		</tr>
		<!-- <tr>
			<th class="th21">正职</th>
		    <th class="th22">副职（不含三总师）</th>
		    <th class="th23">三总师</th>
		</tr> -->
	</table>
</div>
<div  id="selectable2">
	<div>
	<table id="coordTable2" cellspacing="0" width="100%" style="margin-bottom: 20px;">
		<tr>
			<th class="th1"></th>
			<!-- <th class="width0"></th> -->
			<th class="th2"></th>
			<th class="th2"></th>
			<!-- <th class="width0"></th>
			<th class="width0"></th>
			<th class="width0"></th> -->
			<th class="th2"></th>
			
			<th class="th2"></th>
			<th class="th2"></th>
			<th class="th2"></th>
			<th class="th2"></th>
			<th class="th2"></th>
			<th class="th2"></th>
			<th class="th4"></th>
			<th class="th4"></th>
			<th class="th4"></th>
			<th class="th3"></th>
			<!-- <th class="th13"></th>
			<th class="th14"></th>
			<th class="th15"></th>
			<th class="th16"></th>
			<th class="th17"></th>
			<th class="th18"></th>
			<th class="th19"></th>
			<th class="th20"></th> -->
		</tr>
		
	</table>
	</div>

</div>
<div>
<img id="expSelect" onclick="expExcel()" src="<%=request.getContextPath()%>/images/tpbj/exp_excel.png" width="150" align="right" style="cursor:pointer;margin-right:49px">
</div>

<script type="text/javascript">
$.fn.setCursorPosition = function(position){
    if(this.lengh == 0) return this;
    return $(this).setSelection(position, position);
}

$.fn.setSelection = function(selectionStart, selectionEnd) {
    if(this.lengh == 0) return this;
    input = this[0];

    if (input.createTextRange) {
        var range = input.createTextRange();
        range.collapse(true);
        range.moveEnd('character', selectionEnd);
        range.moveStart('character', selectionStart);
        range.select();
    } else if (input.setSelectionRange) {
        input.focus();
        input.setSelectionRange(selectionStart, selectionEnd);
    }

    return this;
}

$.fn.focusEnd = function(){
    this.setCursorPosition(this.val().length);
}




$(function(){
	$(".input-editor").on('click', function (event) {
		var td = $(this);
		var tagName = td.get(0).tagName;;
		if (td.children("textarea").length > 0) {
            return false;
        }
		//行号 zoulei
        var rowIndex = td.parent().index();
        //列号zoulei
        var colIndex = td.index();
		var text = GRYP.getValue(rowIndex,colIndex,td);
		//加上padding乘以2
		var width = td.width() +6*2;
        var height = td.height()+8;
        if(tagName=="DIV"){
        	width = td.width();
            height = td.height();
        }
      //建立文本框，也就是input的节点   
        var div = $('<div style="position:absolute;top:0px;left:0px;">');
        var input = $('<textarea style="height:' + height + 'px;width:' + width + 'px;">');
        div.append(input);
        //将文本内容加入td   
        td.append(div);
        //设置文本框值，即保存的文本内容   
        input.attr('value', text);
        input.css('border', "0px");
        input.css('text-align', "left");
        //作为jquery选择的标志
        input.addClass('txt_editer');
        if(tagName=="SPAN"){
        	input.css('overflow', "hidden");
        	input.css('border', "solid 1px #000000");
        }
        input.click(function () { return false; });
        input.focusout(function (e) {
        	//zoulei 输入框点击位置如果是自己，就保留输入框。防止点到输入框空白位置也会触发该事件
        	if(e.offsetX<width&&e.offsetY<height&&e.offsetY>0&&e.offsetX>0){
        		return;
        	}
        	//更新数据对象
            GRYP.setValue(td, $(this).val(),rowIndex,colIndex);
            
        });
        input.trigger("focus").focusEnd();
	});
});

// b0180 bz,b0234,b0235,b0236
var GLOBLE = {};
GLOBLE['ROWID']={}
GLOBLE['COL_CONFIG_3']={"10":"b0234","11":"b0235","12":"b0236","13":"b0180"};
//根据id存储行信息
GLOBLE['ID_ROWINFO']={};
//根据行号获取rowid
function GetRowid(rowIndex){
	return GLOBLE['ROWID'][rowIndex];
}
var GRYP = (function(){
	return {
		getValue:function(rowIndex,colIndex,$td){
    		var colName = GLOBLE['COL_CONFIG_3'][colIndex];
    		var rowId = GetRowid(rowIndex-1);
    		
    		var text = GLOBLE['ID_ROWINFO'][rowId][colName];
    		return text==null?"":text;
    		
    	},
    	setValue:function(obj, value,rowIndex,colIndex){
    		
    		//更新数据对象
    		var colName = GLOBLE['COL_CONFIG_3'][colIndex];
    		var rowId = GetRowid(rowIndex-1);
    		
    		SetTDtext(obj,value);
    		GLOBLE['ID_ROWINFO'][rowId][colName]=value;
    		//更新后台
    		ajaxSubmit("saveB01",{"colName":colName,"value":value,"b0111":rowId});
    	}
		
	}
})();

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
	    url: "<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.AddZHGBRmb&eventNames="+radowEvent,
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
//	           Ext.Msg.hide();
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
function ajaxSubmit1(radowEvent,parm,callback){
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
