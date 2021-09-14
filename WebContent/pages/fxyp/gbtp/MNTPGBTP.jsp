<%@page import="com.insigma.odin.framework.util.GlobalNames"%>
<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<link rel="stylesheet" type="text/css" href="mainPage/css/bootstrap-combined.min.css"> 

<style>
.btn{
	font-size: 18px;
	padding: 6px 18px;
}
</style>



<div class="titlebutton" style="width: 100%;height:45px;margin-top: 20px;text-align: center;border-bottom: 3px solid rgb(152 152 152);">

	<div style=" height:40px;margin-bottom: 5px;margin-right: 30px;display: inline-block;">
		<button type='button' class="btn  btn-primary" onclick="displayGW(3)" >区县市</button>
	</div>
	<div style=" height:40px;margin-bottom: 5px;margin-right: 30px;display: inline-block;">
		<button type='button' class="btn  btn-primary" onclick="displayGW(1)" >市直单位</button>
	</div>
	<div style=" height:40px;margin-bottom: 5px;margin-right: 30px;display: inline-block;">
		<button type='button' class="btn  btn-primary" onclick="displayGW(2)" >国企高校</button>
	</div>
</div>

<div class="gwinfo" style="margin: 15px;overflow: auto;">
	<button class="btn btn-large btn-info" type="button">按钮</button>
	<button class="btn btn-large btn-info" type="button">按钮</button>

</div>

<div class="template" style="display: none">
	<button class="btn btn-large btn-info" type="button" style="margin: 15px;">按钮</button>
</div>

<script type="text/javascript">
function displayGW(t){
	ajaxSubmit('ShowData',{type:t})
}
function setGWContent(data){
	var ObjTem = $('.template');
	var templateStr = ObjTem.html();
	var gwObj = $('.gwinfo');
	gwObj.html('');
	$.each(data,function(i,gw){
		var template = $(templateStr);
		$(template).text((gw.wayname||""));
		$(template).attr('wayid',(gw.wayid||""));
		$(template).bind('click',function(){
			 openMate(gw.wayid);
		});
		
		gwObj.append(template)
	});
	myMask.hide();//隐藏
}
var myMask;
Ext.onReady(function() {
	myMask = new Ext.LoadMask(Ext.getBody(),{msg:"loading..."});
	var viewSize = Ext.getBody().getViewSize();
	$('.gwinfo').height(viewSize.height-$('.titlebutton').height()-65);
	displayGW(3);
});
function ajaxSubmit(radowEvent,parm,callback){
	//myMask.show();//显示
	 if(parm){
	 }else{
	   parm = {};
	 }
	 Ext.Ajax.request({
	   method: 'POST',
	   form:'commForm',
	       async: true,
	       params : parm,
	       timeout :300000,//按毫秒计算
	   url: "<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.fxyp.gbtp.MNTPGBTP&eventNames="+radowEvent,
	    success: function(resData){
	      var cfg = Ext.util.JSON.decode(resData.responseText);
	      //alert(cfg.messageCode)
	     var qdata = cfg.data;
	      //console.log(qdata)
	      setGWContent(qdata)
	      
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
	
function openMate(p){
	$h.openPageModeWin('GWYL','pages.fxyp.gbtp.GWYL','人员列表',1350,680,{query_id:p},'<%=request.getContextPath()%>');
}
</script>