<%@page import="com.insigma.odin.framework.util.GlobalNames"%>
<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<script src="<%=request.getContextPath()%>/pages/xbrm/jquery1.10.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<link rel="stylesheet" type="text/css" href="mainPage/css/bootstrap-combined.min.css"> 
<style>
#tag_container {
	/* position: relative;
	width: 100%;
	height: 450px;
	border-width: 0;
	border-style: solid;
	border-color: #74A6CC;
	margin-top: 10px; */
	margin: 10px;
}
#tag_info_div01 {
	position: relative;
	width: 100%;
}
#tag_info_div01 #a0196z {
	width: 98%;
	margin-left: 10px;
	border-width: 1px 1px;
	border-style: solid;
	border-color: #74A6CC;
}
#tag_container .tag_div {
	/* position: relative;
	width: 30%;
	height: 100%;
	float: left;
	margin-left: 2%; */
	
}

#tag_info_div {
	position: relative;
	width: 100%;
}

#tag_info_div #a0194z {
	width: 98%;
	margin-left: 10px;
	border-width: 1px 1px;
	border-style: solid;
	border-color: #74A6CC;
}

#bottom_div {
	position: relative;
	width: 100%;
	height: 40px;
	margin-top: 5px;
}

.x-form-item{
display: inline;
}

.x-form-check-wrap{
height: 13px;

}
.x-form-item{
	font-size: 13px;
}
.x-fieldset{
	padding:3px;
	margin-bottom:4px
}


body {
	background-color: rgb(214,227,243);
}
#tag_container01 {
	position: relative;
	width: 100%;
	height: 100px;
	border-width: 0;
	border-style: solid;
	border-color: #74A6CC;
	margin-top: 10px;
}

#tag_container01 .tag_div {
	position: relative;
	height: 100%;
	float: left;
	margin-left: 2%;
}

#tag_info_div01 {
	position: relative;
	width: 100%;
}
#tag_info_div01 #a0196z {
	width: 98%;
	margin-left: 10px;
	border-width: 1px 1px;
	border-style: solid;
	border-color: #74A6CC;
}
#tag_info_div02 {
	position: relative;
	width: 100%;
}
#tag_info_div02 #a0196c {
	width: 98%;
	margin-left: 10px;
	border-width: 1px 1px;
	border-style: solid;
	border-color: #74A6CC;
}
#tag_info_div02 #sza0193z {
	width: 98%;
	margin-left: 10px;
	border-width: 1px 1px;
	border-style: solid;
	border-color: #74A6CC;
}
#tag_info_div02 #sza0194z {
	width: 98%;
	margin-left: 10px;
	border-width: 1px 1px;
	border-style: solid;
	border-color: #74A6CC;
}
#tag_info_div02 #a99 {
	width: 98%;
	margin-left: 20px;
	border-width: 1px 1px;
	border-style: solid;
	border-color: #74A6CC;
}
#tag_info_div02 #a98 {
	width: 98%;
	margin-left: 20px;
	border-width: 1px 1px;
	border-style: solid;
	border-color: #74A6CC;
}

#bottom_div01 {
	position: relative;
	width: 100%;
	height: 40px;
	margin-top: 5px;
}
table{
width: 100%
}
</style>

<odin:hidden property="a0196s"/>
<div id="tag_container">
	<odin:groupBox title="一处" property="dw" >
	<input style="display:inline-block;" type="checkbox" name="attr107" id="attr101"  class="marginLeft20"/><label id="template" style="display:inline-block;"> </label>
	</odin:groupBox>
	
	<odin:groupBox title="二处" property="jingji">	
	</odin:groupBox>
		
	<odin:groupBox title="三处" property="ghyjs">
	</odin:groupBox>	
</div>

<div id="tag_info_div01">
	<textarea rows="3" cols="113" id="a0196z" name="a0196z"></textarea>
</div>
<div id="bottom_div">
	<div align="center">
		<odin:button text="保&nbsp;&nbsp;存" property="save" />
	</div>		
</div> 	
<script type="text/javascript">
function displayGW(){
	ajaxSubmit('ShowData')
}
function setGWContent(data){
	var ObjTem = $('#template');
	var templateStr = ObjTem.html('');
	var gwObj = $('.gwinfo');
	gwObj.html('');
	$.each(data.data,function(i,gw){
		var template = $(templateStr);
		$(template).text((gw.tags||""));
		$(template).attr('tags',(gw.tags||""));
		gwObj.append(template);
	    ObjTem.append(template);
	});
	myMask.hide();//隐藏
}
var myMask;
Ext.onReady(function() {
	myMask = new Ext.LoadMask(Ext.getBody(),{msg:"loading..."});
	var viewSize = Ext.getBody().getViewSize();
	$('.gwinfo').height(viewSize.height-$('.titlebutton').height()-65);
	displayGW();
	$('input:checkbox').bind('click',function(obj){
		fullContent(this,$(this).attr('id').replace('attr',''),$(this).parent().children('label').text());
	});
});
function ajaxSubmit(radowEvent,callback){
	//myMask.show();//显示
	 Ext.Ajax.request({
	   method: 'POST',
	   form:'commForm',
	       async: true,
	       timeout :300000,//按毫秒计算
	   url: "<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.publicServantManage.ZZBQ&eventNames="+radowEvent,
	   success: function(resData){
	     var cfg = Ext.util.JSON.decode(resData.responseText);
	     var qdata = cfg.data;
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
	

//输入框中显示选中标签
function fullContent(check,value,valuename){
	var a0196z = document.getElementById("a0196z").value;
	var a0196s = document.getElementById("a0196s").value;
	if($(check).is(':checked')) {
		if( a0196z == null || a0196z == '' ){
			a0196z = valuename;
		}else{
			a0196z = a0196z + "，" + valuename;
		}	
		if( a0196s == null || a0196s == '' ){
			a0196s = value;
		}else{
			a0196s = a0196s  + "，" + value;
		}			
	}else{
		a0196z = a0196z.replace('，'+valuename, '').replace(valuename+'，', '').replace(valuename, '');
		a0196s = a0196s.replace('，'+value, '').replace(value+'，', '').replace(value, ''); 
	}
	document.getElementById("a0196z").value = a0196z;
	document.getElementById("a0196s").value = a0196s;
}
</script>

