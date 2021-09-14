<%@page import="com.insigma.siis.local.pagemodel.xbrm.constant.RMHJ"%>
<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="tags"%>
<%@include file="/comOpenWinInit2.jsp" %>

<%@page isELIgnored="false" %>

<script src="<%=request.getContextPath()%>/pages/fxyp/js/jquery-1.4.4.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/pages/fxyp/js/CoordTable/js/jquery-ui-1.8.9.custom.min.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/pages/fxyp/js/CoordTable/js/jquery.contextmenu.r2.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/pages/fxyp/js/CoordTable/js/coordTable.js" type="text/javascript"></script>
<script src="<%=request.getContextPath()%>/pages/fxyp/js/tableEditer.js" type="text/javascript"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/comboxWithTree.js"></script>
	
<script type="text/javascript" src="<%=request.getContextPath()%>/basejs/helperUtil.js"></script>
<link rel="stylesheet" href="<%=request.getContextPath()%>/pages/fxyp/gbtp.css">

<style>
body{
margin: 1px;overflow-y: hidden;overflow-x: hidden;
font-family:'宋体',Simsun;
word-break:break-all;
margin-bottom: 250px;
}

.kcclClass{
background-color: rgb(102,204,255) !important;
}
.drag_color{
	background-color: rgb(232,232,232) !important;
}
.drag_pre_color{
	background-color: rgb(233,250,238)!important;
}
.default_color{
	background-color: #FFFFFF !important;
}
input{
	border: 1px solid #c0d1e3 !important;
}
</style>
<odin:hidden property="ynId"/>



<script type="text/javascript">
document.title = '人选分析研判';
parent = window;
/* $(function(){
	$.fn.smartFloat = function() {
		 var position = function(element) {
		  var top = element.position().top;
		  $(window).scroll(function() {
		   var scrolls = $(this).scrollTop();
		   element.css({
			      top: scrolls+top
			     }); 
		  });
		 };
		 return $(this).each(function() {
		  position($(this));      
		 });
		};


		$(".btncls").smartFloat();
}) */



var g_contextpath = '<%= request.getContextPath() %>';
Ext.onReady(function(){
	$('#mntp00').val(parentParam?parentParam.mntp00s:"922e35a7-235c-45b1-b570-6fefea12dd62");
});
	Array.prototype.removeIndex = function (index) {
	  if (index > - 1) {
	    this.splice(index, 1);
	  }
	};
	function GUID() {
	  return 'xxxxxxxxxxxx4xxxyxxxxxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
	    var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
	    return v.toString(16);
	  });
	}
	function SetTDtext(td,v) {
	  $(td).html((v==""||v==null||v=="null")?" ":v.replace(/\n/g,"<br/>"));
	}
	//根据行号获取rowid
	function GetRowid(rowIndex){
		return GLOBLE['ROWID'][rowIndex-GLOBLE.rowOffset];
	}
	
	

	
	
	$(document).ready(function () {
		$(function(){
		});
	})
</script>

<script type="text/javascript">
var GLOBLE={};
GLOBLE.colOffset=1;//第三列开始可编辑区域。
GLOBLE.rowOffset=1;//第二行开始可编辑区域。
//根据行号存储人员id 行号=index+1
GLOBLE['ROWID']=[];
//根据类别和列号获取列名
GLOBLE['COL_CONFIG_3']={"2":"fxyp02","4":"tp0101","5":"tp0102","6":"tp0103","7":"tp0104","8":"tp0105","9":"tp0106"};
//剪切后的数据 存放对象
GLOBLE['CUTEDDATA']={};
GLOBLE['CUTEDROWID']=[];

//根据id存储行信息
GLOBLE['ID_ROWINFO']={};

//更新行后更新序号和行号。
var complate = function () {
    $('#coordTable tr:gt(0) td:nth-child(1)').each(function (i, item) {
        $(this).text(i + 1);
    });
    var k = 1;
    $('#coordTable tr:gt(0) td:nth-child(2)').each(function (i, item) {
    	if($(this).attr('gw')=='true'){
    		$(this).text(k++);
    	}
        
    });
    k = 1;
    $('#coordTable tr:gt(0) td:nth-child(4)').each(function (i, item) {
    	if($("td:nth-child(2)", $(this).parent()).attr('gw')=='true'){
    		k = 1;
    		$(this).text(k++);
    	}else{
    		$("td:nth-child(2)", $(this).parent()).text(k++);
    	}
    });
}

//创建行
//岗位下有多人  岗位需要行合并 isHeBing 及 需要合并几行countf
var createRow = function (rowIndex,guid,rowData,isHeBing,countf,rowCount,trCount) {//splice(index, 0, val);
	var borderTop = '';
	if(rowCount>0&&(countf==0||isHeBing)){//首次加载
		borderTop = 'borderTop';
	}
	if(rowIndex>1&&(countf==0||isHeBing)){//插入
		borderTop = 'borderTop';
	}
	if(rowIndex==0&&trCount>1&&(countf==0||isHeBing)){//追加
		borderTop = 'borderTop';
	}
	if(!guid){
		guid = GUID();
	}
	var tr;
	var rowd;
	
	tr = $('<tr class="data">'+
			'<td class="rownum default_color"></td>'+//查看
			//'<td class="TNR"></td>'+//序号
			(countf==0?'<td gw="true" class="TNR"></td>':(isHeBing?'<td gw="true" rowSpan="'+countf+'" class="TNR"></td>':''))+
			(countf==0?'<td class="align-left '+borderTop+'">&nbsp;</td>':(isHeBing?'<td rowSpan="'+countf+'" class="align-left  '+borderTop+'">&nbsp;</td>':''))+//岗位名称
			'<td class="TNR  '+borderTop+'"></td>'+//序号
			'<td class=" '+borderTop+'">&nbsp;</td>'+//姓名
			'<td class="TNR '+borderTop+'">&nbsp;</td>'+//出生年月
			'<td class="TNR '+borderTop+'">&nbsp;</td>'+//任现职时间
			'<td class="TNR '+borderTop+'">&nbsp;</td>'+//任同级时间
			'<td class=" '+borderTop+'">&nbsp;</td>'+//学历职称
			'<td class="align-left '+borderTop+'">&nbsp;</td></tr>');//任现职务
	
	if(rowData){
		var tsIndex = 0;
		var tds = $("td:nth-child(n+3)", tr);
    	if(isHeBing||countf==0){
    		var fxyp07 = rowData["fxyp07"]
    		if(fxyp07=='1'){
	      		 rm = "任"
	      	}else if(fxyp07=='-1'){
	      		 rm = "免"
			}
    		var v = rowData["fxyp02"];
    		SetTDtext(tds[tsIndex++],(v==""||v==null||v=="null")?"":(rm+v));
    		tsIndex++
    	}
    	
    	SetTDtext(tds[tsIndex++],rowData["tp0101"]);
    	SetTDtext(tds[tsIndex++],rowData["tp0102"]);
    	SetTDtext(tds[tsIndex++],rowData["tp0103"]);
    	SetTDtext(tds[tsIndex++],rowData["tp0104"]);
    	SetTDtext(tds[tsIndex++],rowData["tp0105"]);
    	SetTDtext(tds[tsIndex++],rowData["tp0106"]);
    	rowd=rowData;
	}else{
    	rowd={"fxyp07":"","tp0100":guid,"fxyp02":"","tp0101":"","tp0102":"","tp0103":"","tp0104":"","tp0105":"","tp0106":"","tp0107":"","tp0108":"","tp0109":"","tp0110":"","tp0111":"","tp0112":"","tp0113":"","tp0114":"","tp0115":""};
	}
	//更新数据对象
	if(rowIndex>0){
		GLOBLE['ROWID'].splice(rowIndex-GLOBLE.rowOffset, 0, guid);
		GLOBLE['ID_ROWINFO'][guid]=rowd;
	}else if(rowIndex==0){//在表头上表示追加
		GLOBLE['ROWID'].push(guid);
		GLOBLE['ID_ROWINFO'][guid]=rowd;
	}
    return tr;
}





$(document).ready(function () {


//主调函数
    $('#coordTable').coordTable({
        selecte_col_len: 4, //坐标每行的列数
        selector_row: 'tr.data td:nth-child(1)', //一个jquery选择器，表示可以点击选择行的区域
        selector_td: 'tr.data td:nth-child(n+2)', //一个jquery选择器，表示可以用鼠标拖拽的区域
        hasMenu:false
    });

  

  //点击选中行
    $("#coordTable tr.data td:nth-child(1)").live('dblclick', function (e) {
    	rowIndex = $(this).parent().index();
    	var rowId = GetRowid(rowIndex);
    	var a0000 = GLOBLE['ID_ROWINFO'][rowId]["a0000"];
    	if(a0000==""||a0000==null){
    		return;
    	}
    	$h.showModalDialog('personInfoOP',g_contextpath+'/rmb/ZHGBrmb.jsp?a0000='+a0000,'人员信息修改',1009,730,null,
		{a0000:a0000,gridName:'',maximizable:false,resizable:false,draggable:false},true);
        return false;
    });
    
 });

function genTPB(){
	var tp0100s='';
	$(".selectTag").each(function(i,item){
		var rowId = GetRowid($(this).index());
   		var rowData = GLOBLE['ID_ROWINFO'][rowId];
   		var tp0100 = rowData["tp0100"];
   		tp0100s = tp0100s + tp0100 + ',';
	});
	if (tp0100s == '') {
		//odin.alert("请选择人员！");
		//return;
		$("#coordTable tr:gt(0)").each(function(i,item){
			var rowId = GetRowid($(this).index());
	   		var rowData = GLOBLE['ID_ROWINFO'][rowId];
	   		var tp0100 = rowData["tp0100"];
	   		tp0100s = tp0100s + tp0100 + ',';
		});
	}
	if (tp0100s == '') {
		odin.alert("请选择人员！");
		return;
	}
	tp0100s = tp0100s.substring(0, tp0100s.length - 1);
	$("#tp0100s").val(tp0100s);
	radow.doEvent("genTPB");
}
function infoSearch(){}
function openGDview(yn_id){
	realParent.realParent.openGDview(yn_id,window);
}




</script>






<div id="selectable" style="overflow-y:scroll;overflow-x:hidden;height: 500px;">
<div style="text-align:left; margin-left:120px;margin-top:20px;margin-bottom:100px; width:1000px;">
<p class="BiaoTouP">干&nbsp;部&nbsp;调&nbsp;配&nbsp;人&nbsp;选&nbsp;分&nbsp;析&nbsp;研&nbsp;判&nbsp;表</p>


<!-- <div class="top_btn_style chakan" 
 style="background-color:#F08000;margin-top: 15px; margin-bottom:5px;font-size:20px;
 width: 150px;" onclick="rybd()">人员比对
</div> -->
<div class="top_btn_style" 
 style="background-color:#F08000;margin-top: 15px; margin-bottom:5px;font-size:20px; margin-left:830px;
 width: 150px;" onclick="genTPB()">生成酝酿用表
</div>

<table id="coordTable" cellspacing="0" width="100%" >
	<tr>
	    <th style="width:4%">对<br/>比</th>
	    <th style="width:4%">序<br/>号</th>
	    <th id="GangWeiMingCheng" style="width:21%">拟任免岗位名称</th>
	    <th style="width:4%">序<br/>号</th>
	    <th style="width:8%">姓名</th>
	    <th style="width:8%">出生<br/>年月</th>
	    <th id="RenXianZhiShiJian" style="width:8%">任现职<br/>时间</th>
	    <th id="RenTongJiShiJian" style="width:8%">任同级<br/>时间</th>
	    <th style="width:10%">学历<br/>职称</th>
	    <th id="XianRenZhiWu" style="width:25%">现任职务</th>
	    <!-- <th id="NiRenMianZhiWu" style="width:25%">拟任免职务</th> -->
	</tr>
	
</table>


	
</div>
</div>

<iframe  id="iframe_expTPB" style="display: none;" src=""></iframe>
<script type="text/javascript">
$(function(){
	var ah = screen.availHeight - 35;
	$('#selectable').css('height',ah);
});

var doAddPerson = (function(){//增加人员
	return {
		queryByNameAndIDS:function(list,fxyp00){//按姓名查询
			$("#a0000s").val(list);
			radow.doEvent('queryByNameAndIDS',fxyp00);
		},
		addPerson:function(plist,rowIndex){//按姓名查询 插入人员
			if(!rowIndex){
				rowIndex = 0;
			}
			var trCount =  $("#coordTable tr").length;
       		if(rowIndex>0){
       			var fxyp00Prev = '';
       			var insertTR = $("#coordTable tr:nth-child("+(rowIndex+1)+")");
				$.each(plist,function (i, item){
					var countf = item["countf"];
					var fxyp00Cur = item["fxyp00"];
					var isHeBing = false;
					if(fxyp00Cur!=fxyp00Prev){
						fxyp00Prev = fxyp00Cur
						if(countf>0){
							isHeBing = true;
						}
					}
					var $tr = createRow(rowIndex+i,item["tp0100"],item,isHeBing,countf,i,trCount);
					$tr.insertBefore(insertTR);
          		});
      		}else if(rowIndex==0){//追加
      			var fxyp00Prev = '';
				$.each(plist,function (i, item){
					var countf = item["countf"];
					var fxyp00Cur = item["fxyp00"];
					var isHeBing = false;
					if(fxyp00Cur!=fxyp00Prev){
						fxyp00Prev = fxyp00Cur
						if(countf>0){
							isHeBing = true;
						}
					}
					var $tr = createRow(rowIndex,item["tp0100"],item,isHeBing,countf,i,trCount);
          			$("#coordTable").append($tr);
          		});
      		}
			
			complate();
		}
	}
})();



var TIME_INIT = (function(){
	return {
		clearData : function(){
			$(".data").remove();
			GLOBLE['ROWID']=[];
			GLOBLE['ID_ROWINFO']={};
			//情空缓存
    		GLOBLE['CUTEDROWID']=[];
    		GLOBLE['CUTEDDATA']={}
		}
	}
})();







function eventajax(eventType,params,cbfun){
	var path = '<%=request.getContextPath()%>/radowAction.do?method=doEvent&pageModel=pages.yntp.TPB&eventNames='+eventType;
   	Ext.Ajax.request({
   		timeout: 60000,
   		url: path,
   		async: true,
   		method :"post",
   		form : 'commform',
   		params : params,
        callback: function (options, success, response) {
      	   if (success) {
      		   var result = response.responseText;
 			   if(result){
 				  var cfg = Ext.util.JSON.decode(result);
 				  cbfun(cfg.messageCode)
 				}
      	   }
        }
   });
}



function ShowCellCover(elementId, titles, msgs) {	
	Ext.MessageBox.buttonText.ok = "关闭";
	if(elementId.indexOf("start") != -1){
	
		Ext.MessageBox.show({
			title:titles,
			msg:msgs,
			width:300,
	        height:300,
			closable:false,
			modal:true,
			progress:true,
			wait:true,
			animEl: 'elId',
			increment:5, 
			waitConfig: {interval:150}
		});
	}
}






function downloadByUUID(){
	var downloadUUID = document.getElementById('docpath').value;
	$('#iframe_expTPB').attr('src','<%= request.getContextPath() %>/PublishFileServlet?method=downloadFile&uuid='+downloadUUID);
	return false
}



function rybd(){
	var a0000s='';
	$(".selectTag").each(function(i,item){
		var rowId = GetRowid($(this).index());
   		var rowData = GLOBLE['ID_ROWINFO'][rowId];
   		var a0000 = rowData["a0000"];
   		a0000s = a0000s + a0000 + ',';
	});
	if (a0000s == '') {
		odin.alert("请选择人员！");
		return;
	}
	a0000s = a0000s.substring(0, a0000s.length - 1);
	$("#a0000srybd").val(a0000s);
	radow.doEvent('tpbj.onclick');
}

function clearSelected() {
	 //列表
   jQuery.coordTable.unSelect($('.selectTag'));
   jQuery.coordTable.unSelect($('.ui-selected'));
   $('#a0000srybd').val('');
}




</script>



<odin:hidden property="mntp00" />
<odin:hidden property="tp0100s" />
<odin:hidden property="a0000s" title="查询"/>
<odin:hidden property="a0000srybd" title="人员比对"/>
<odin:hidden property="docpath" />
<odin:hidden property="ID_ROWINFO" title="保存对象"/>
<odin:hidden property="ROWID" title="保存对象id"/>


<odin:hidden property="a1701Word"/>
<odin:hidden property="a0814Word"/>
<odin:hidden property="a0215aWord"/>
<odin:hidden property="rmbs"/>
