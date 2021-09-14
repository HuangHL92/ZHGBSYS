<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib uri="/WEB-INF/sicp3.tld" prefix="sicp3"%>
<%@ taglib uri="/WEB-INF/odin-ss.tld" prefix="ss"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/comOpenWinInit.jsp" %>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<script src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/pages/fxyp/js/jquery-1.4.4.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/pages/fxyp/js/CoordTable/js/jquery-ui-1.8.9.custom.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/pages/fxyp/js/CoordTable/js/jquery.contextmenu.r2.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/pages/fxyp/js/CoordTable/js/coordTable.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/pages/fxyp/js/tableEditer.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/pages/fxyp/js/rxfxyp-view.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/basejs/jquery/jquery-1.7.2.min.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comboxWithTree.js"></script>
	
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<odin:hidden property="docpath" />
<iframe  id="iframe_expBZYP" style="display: none;" src=""></iframe>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">

<style>

#coordTable {
border-right: 2px solid #74A6CC;
border-bottom: 2px solid #74A6CC;
border-collapse:collapse;
}
#coordTable2 {
border-right: 2px solid #74A6CC;
border-bottom: 2px solid #74A6CC;
border-collapse:collapse;
}
.tabletd {
border-left: 2px solid #74A6CC;
border-top: 2px solid #74A6CC;
}

.yellowbutton {
	font-family: "Arial","Microsoft YaHei","黑体","宋体",sans-serif;
	font-size:13px;
	/* font-weight:bolder; */
	color:white;
	width:70px;
	height:25px;
	/* line-height:20px; */
	border:0;
	cursor:pointer;
	background-color:#F08000;
}
#selectable{
	overflow:auto;
	height:95%;
}
#jgDiv td{
	border:none
}
.top_btn_style{
	float:left;
	display:block;
	border-radius:5px;
	cursor:pointer;
	margin-left:6px;
	height:25px;
	line-height:14px;
	vertical-align:middle;
	font-size:12px;
	color:#fff;
	background-color:#3680C9;
	text-align: center;
	padding: 3px 5px!important;
}
</style>
<div id="selectable" style="overflow: auto;margin-left: 2px;">
	<table id="jgDiv" >
		<tr>
			<odin:select2 property="b0101" label="选择查询机构"  multiSelect="true"></odin:select2>
			<td>&nbsp;&nbsp;&nbsp;
			</td>
			<td>
				<odin:button text="查询" property="query" handler="query"></odin:button>
			</td>
		</tr>
	</table>
<table id="coordTable" >
	<tr  align="center">
		<td colspan="6" class="tabletd" style="font-size: 24px;height: 60;font-weight:bold;">模拟调配变动汇总表</td>
	</tr>
	<tr  align="center">
		<td width="280" class="tabletd" rowspan="2" style="font-size: 16px;height: 60;font-weight:bold;">单位</td>
		<td  class="tabletd" colspan="3" style="font-size: 16px;height: 30;font-weight:bold;">职数情况</td>
		<td width="640" class="tabletd" rowspan="2" style="font-size: 16px;font-weight:bold;">调配初步考虑</td>
		<td width="200" class="tabletd" rowspan="2" style="font-size: 16px;font-weight:bold;">备注</td>
	</tr>
	<tr align="center">
		<td width="80" class="tabletd" style="font-size: 16px;font-weight:bold;">核定职数</td>
		<td width="80" class="tabletd" style="font-size: 16px;height: 40;font-weight:bold;">实际配备数</td>
		<td width="80" class="tabletd" style="font-size: 16px;font-weight:bold;">可用职数</td>
	</tr>
</table>
<table id="coordTable2">
</table>
</div>
<div align="right" class="top_btn_style" style="float: right;background-color:#F08000;line-height:25px;"  onclick="expExcel()">导出Excel</div>
<div style="height: 10;">
<div align="right" class="top_btn_style" style="float: right;background-color:#F08000;line-height:25px;"  onclick="openViewRYBD_GW($('#mntp00').val())">调配情况</div>
<div style="height: 10;">
</div>
<odin:hidden property="rmbs"/>
<odin:hidden property="data"/>
<odin:hidden property="mntp00"/>
<odin:hidden property="b0111"/>
<script type="text/javascript">
Ext.onReady(function() {
	$('#mntp00').val(parent.Ext.getCmp(subWinId).initialConfig.mntp00);
	$('#b0111').val(parent.Ext.getCmp(subWinId).initialConfig.b0111);
});
function Add(data){
	$.each(data, function (i,item) {
		var tr;
		tr = $('<tr align="center"><td width="280" class="tabletd"></td><td width="80" class="tabletd"></td><td width="80" class="tabletd"></td><td width="80" class="tabletd"></td><td width="640" class="tabletd" align="left"></td><td width="200" class="tabletd"></td></tr>');	
		var tds = $("td", tr);
 		SetTDtext(tds[0],item["b0101"]); 
		SetTDtext(tds[1],item["b0188"]); 
		SetTDtext(tds[2],item["sjzs"]); 
		SetTDtext(tds[4],item["tpry"]); 
		$('#coordTable2').append(tr);
	});
}
function SetTDtext(td,v) {
	  $(td).html((v==""||v==null||v=="null"||v=="0"||v=="-1"||v=="-2"||v=="-3"||v=="-4")?" ":v.replace(/\n/g,"<br/>"));
	}
</script>

<script type="text/javascript">


function removeRmbs(a0000){
	var rmbs=document.getElementById('rmbs').value;
	document.getElementById('rmbs').value=rmbs.replace(a0000,"");
}

function replaceParamVal(paramName,replaceWith) {
    var oUrl = this.location.href.toString();
    var re=eval('/('+ paramName+'=)([^&]*)/gi');
    var nUrl = oUrl.replace(re,paramName+'='+replaceWith);
    this.location = nUrl;
    window.location.href=nUrl;
}
function updatebtn(str){
	replaceParamVal("type",str);
}
function openRmb(a0000){
	radow.doEvent("openRmb",a0000);
}
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
	 		dataArray.push(tdArray);
	 	}
	});
	if(dataArray.length>0){
	    ajaxSubmit('expExcel',{"dataArray":Ext.encode(dataArray),"excelname":"变动汇总"});
	}
}

function downloadByUUID(){
	var downloadUUID = document.getElementById('docpath').value;
	$('#iframe_expBZYP').attr('src','<%= request.getContextPath() %>/PublishFileServlet?method=downloadFile&uuid='+downloadUUID);
	return false
}

function query(){
	var tb = document.getElementById('coordTable2');
    var rowNum=tb.rows.length;
    for (i=0;i<rowNum;i++)
    {
        tb.deleteRow(i);
        rowNum=rowNum-1;
        i=i-1;
    }
	radow.doEvent("query");
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


function openViewRYBD_GW(mntp00){
	var newWin_ = $h.getTopParent().Ext.getCmp('ViewRYBDGWWin');
	  if(newWin_){
	    newWin_.toFront();
	    return;
	  }
	$h.openWin('ViewRYBDGWWin','pages.fxyp.BZRYBDGW','模拟情况',1410,900,'','<%=request.getContextPath()%>',null,{mntp00:mntp00},true);
}
</script>
