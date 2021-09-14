<%@page
	import="com.insigma.siis.local.pagemodel.sysorg.org.hzb.SZDWZCNQGB"%>
<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<html lang="en">
<head>
<odin:head />
<odin:MDParam></odin:MDParam>
<script
	src="<%=request.getContextPath()%>/basejs/jquery/jquery-1.7.2.min.js"
	type="text/javascript"></script>

<odin:hidden property="docpath" />
<odin:hidden property="dataArray" />

<script type="text/javascript"
	src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>

<%
String ctxPath = request.getContextPath(); 
String subWinId = request.getParameter("subWinId");
%>
<script type="text/javascript">
var subWinId = '<%=subWinId%>';
var realParent = parent.Ext.getCmp(subWinId).initialConfig.thisWin;
var data = <%=SZDWZCNQGB.getColData(request.getParameter(""))%>;
$(function(){
	var ROWID={};
	var hj1=0; var hj2=0; var hj3=0; var hj4=0; var hj5=0;
	var hj6=0; var hj7=0; var hj8=0;
	$.each(data, function (i,item) {
		var tr = $('<tr><td class="th0 textleft"></td><td class="th4"></td><td class="th4"></td><td class="th4"></td><td class="th4"></td><td class="th4"/td><td class="th4"/td><td class="th4"/td><td class="th4"/td></tr>');
		var tds = $("td", tr);
		var index = 0;
		
		ROWID[i]=item["b0111"];
		SetTDtext(tds[0],item["b0101"]);
		SetTDtext(tds[1],item["cjzs"]);
		SetTDtext(tds[2],item["cjzz"]);
		SetTDtext(tds[3],item["ssxq"]);
		SetTDtext(tds[4],item["cj40zb"]);
		SetTDtext(tds[5],item["a37zz"]);
		SetTDtext(tds[6],item["cj37zb"]);
		SetTDtext(tds[7],item["a37"]);
		SetTDtext(tds[8],item["a32fz"]);
		
		$('#coordTable2').append(tr);
		
		hj1=hj1+parseInt(item["cjzs"]);
		hj2=hj2+parseInt(item["cjzz"]);
		hj3=hj3+parseInt(item["ssxq"]);
		hj5=hj5+parseInt(item["a37zz"]);
		hj7=hj7+parseInt(item["a37"]);
		hj8=hj8+parseInt(item["a32fz"]);
	});
	
	document.getElementById("hj1").innerHTML=hj1;
	document.getElementById("hj2").innerHTML=hj2;
	document.getElementById("hj3").innerHTML=hj3;
	
	hj4=Math.round(hj3*1000/hj1)/10+"%";
	document.getElementById("hj4").innerHTML=hj4;
	document.getElementById("hj5").innerHTML=hj5;
	hj6=Math.round(hj5*1000/hj1)/10+"%";
	document.getElementById("hj6").innerHTML=hj6;
	document.getElementById("hj7").innerHTML=hj7;
	document.getElementById("hj8").innerHTML=hj8;
	
	$('td:nth-child(2),td:nth-child(3),td:nth-child(4),td:nth-child(6),td:nth-child(8),td:nth-child(9)','#coordTable2 tr:gt(0)').each(function (i, item) {
        $(this).addClass('pointer');
        $(this).bind('click',function(){
        	var p = {maximizable:false,resizable:false};
        	p['colIndex'] = $(this).index();
        	//alert($(this).parent().index())
        	p['b0111'] = ROWID[$(this).parent().index()-1];
        	console.log(p['b0111'])
        	p['query_type']="SZDWZCNQGB";
        	openMate(p);
        })
    });
	
});
function expExcel(){
	var dataArray = [];
	
	var tr = $("#coordTable3 tr");
	$.each(tr,function (i, item){
		var tdArray = [];
	 	var td = $("th",$(this));
	 	$.each(td,function (ti, titem){
	 		tdArray.push($(this).text());
		});
		if(tdArray.length>0){
	 	dataArray.push(tdArray)};
	});
	
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
		ajaxSubmit1('expExcel',{'dataArray':Ext.encode(dataArray),"excelname":"市直单位（处级）中层年轻干部配备情况"})
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
  $(td).html((v==""||v==null||v=="null"||v=="0"||v=="0.0%")?" ":v.replace(/\n/g,"<br/>"));
}
</script>
<style type="text/css">
.pointer {
	cursor: pointer;
}

.input-editor {
	font-size: 12px;
}

.colorLight {
	color: red;
}

.input-editor {
	position: relative;
	padding: 6px 8px;
}

th {
	background: #CAE8EA;
	text-align: center;
}

th, td {
	border-left: 1px solid #C1DAD7;
	border-bottom: 1px solid #C1DAD7;
	font-family: 宋体;
	font-weight: normal;
}

td {
	text-align: center;
	padding: 5px;
}

.textleft {
	text-align: left;
}

.title {
	font-family: 方正小标宋简体;
	font-size: 28px;
	font-weight: normal;
	background: #FFF;
}

.borderRight {
	border-right: 1px solid #C1DAD7;
}

.borderTop {
	border-top: 1px solid #C1DAD7;
}

#selectable2 {
	height: 400px;
	overflow: auto;
}

table {
	width: 1140px;
}

#selectable2 tr {
	background-color: expression('#F0F0F0,#FFFFFF' .split ( ',')[rowIndex%2]);
}
#selectable3{
	overflow: auto;
}

.th0 {
	width: 15%;
}

.th1 {
	width: 30%;
}

.th2 {
	width: 3%;
} /*9*/
.th3 {
	width: 10%;
}

.th4 {
	width: 7%;
} /*3*/
.th5 {
	width: 4%;
}

.th6 {
	width: 5%;
}

.th7 {
	width: 5%;
}

.th8 {
	width: 5%;
}

.th9 {
	width: 5%;
}

.th10 {
	width: 5%;
}

.th11 {
	width: 5%;
}

.th12 {
	width: 5%;
}

.th21 {
	width: 5%;
}

.th22 {
	width: 10%;
}

.th23 {
	width: 5%;
}

.width0 {
	width: 0px;
	display: none;
}

.th13 {
	width: 4%;
}

.th14 {
	width: 4%;
}

.th15 {
	width: 4%;
}

.th16 {
	width: 4%;
}

.th17 {
	width: 4%;
}

.th18 {
	width: 4%;
}

.th19 {
	width: 4%;
}

.th20 {
	width: 4%;
}

#selectable2, #selectable, #selectable3 {
	margin-left: 10px;
}

#selectable {
	margin-top: 10px;
}
.tt1 {
height: 100px
}
</style>
</head>
<body>
	<iframe id="iframe_expBZYP" style="display: none;" src=""></iframe>
	<div id="selectable">
		<table id="coordTable" cellspacing="0" width="100%">
			<tr>
				<th colspan="15" class="borderTop title borderRight">市直单位处级（中层）年轻干部配备情况</th>
			</tr>
			<tr>
				<th rowspan="2" class="th0 tt1	">单位名称</th>
				<!-- <th rowspan="3" class="th2">机构级别</th> -->
				<th rowspan="2" class="th4"	style="border-right: 0px solid #C1DAD7;">实配处级领导干部数</th>
				<th  class="th4" style="border-left: 0px solid #C1DAD7;">&nbsp;</th>
				<th colspan="2" class="th4" >40岁以下(不满41岁)处级年轻干部配备情况（≥15%）</th>
				<th colspan="3" class="th4" >35岁左右及以下(不满37岁)处级年轻干部配备情况（一般达到15%）</th>
				<th rowspan="2" class="borderRight th4">35岁左右(不满37)正处人数</th>
				<th rowspan="2" class="borderRight th4">30岁左右(不满32岁)副处人数</th>
			</tr>
			<tr>
				<th  class="th4" style="border-top: 1px solid #C1DAD7;">正处</th>
				<th  class="th4" >实配</th>
				<th  class="th4">占比</th>
				<th  class="th4" >实配</th>
				<th  class="th4">占比</th>
			</tr>
			
		</table>
	</div>
	<div id="selectable3">
		<div>
			<table  id="coordTable3"  cellspacing="0" width="100%">
				<tr  height="30px">
					<th class="th0">合计</th>
					<th id="hj1"  class="th4"></th>
					<th id="hj2"  class="th4"></th>
					<th id="hj3"  class="th4"></th>
					<th id="hj4"  class="th4"></th>
					<th id="hj5"  class="th4"></th>
					<th id="hj6"  class="th4"></th>
					<th id="hj7"  class="th4"></th>
					<th id="hj8"  class="th4"></th>

				</tr> 
			</table>
		</div>

	</div>
	<div id="selectable2">
		<div>
			<table id="coordTable2" cellspacing="0" width="100%"
				style="margin-bottom: 20px;">
				<!-- <tr>
					<th class="th1"></th>
					<th class="th2"></th>
					<th class="th2"></th>
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

				</tr> -->
			</table>
		</div>

	</div>
	<div align="right"><img id="expSelect" onclick="expExcel()" src="<%=request.getContextPath()%>/images/tpbj/exp_excel.png" width="150px" 
style="cursor:pointer;margin-right:25px"/></div>

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
		url: "<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.sysorg.org.hzb.ExpExcel&eventNames="
								+ radowEvent,
						success : function(resData) {
							var cfg = Ext.util.JSON
									.decode(resData.responseText);
							//alert(cfg.messageCode)
							if (0 == cfg.messageCode) {
								Ext.Msg.hide();

								if (cfg.elementsScript.indexOf("\n") > 0) {
									cfg.elementsScript = cfg.elementsScript
											.replace(/\r/gi, "");
									cfg.elementsScript = cfg.elementsScript
											.replace(/\n/gi, "\\n");
								}

								//console.log(cfg.elementsScript);

								eval(cfg.elementsScript);
								//var realParent = parent.Ext.getCmp("setFields").initialConfig.thisWin;
								//parent.document.location.reload();
								//alert(cfg.elementsScript);
								//realParent.resetCM(cfg.elementsScript);
								//parent.Ext.getCmp("setFields").close();
								//console.log(cfg.mainMessage);

								if ("操作成功！" != cfg.mainMessage) {
									Ext.Msg.hide();
									Ext.Msg.alert('系统提示:', cfg.mainMessage);

								} else {
									// 					Ext.Msg.hide();
								}
							} else {
								//Ext.Msg.hide();

								/* if(cfg.mainMessage.indexOf("<br/>")>0){

									$h.alert('系统提示',cfg.mainMessage,null,380);
									return;
								} */

								if ("操作成功！" != cfg.mainMessage) {
									Ext.Msg.hide();
									Ext.Msg.alert('系统提示:', cfg.mainMessage);
								} else {
									Ext.Msg.hide();
								}
							}
							if (!!callback) {
								callback();
							}
						},
						failure : function(res, options) {
							Ext.Msg.hide();
							alert("网络异常！");
						}
					});
		}
	</script>
</body>
</html>
