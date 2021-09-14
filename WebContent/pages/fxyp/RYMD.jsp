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
#autowidth {
	height:100px;
	background-size:cover;
    margin-left:8px;
    margin-top:0px;
    overflow:auto;
}
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
	font-family: SimHei;
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
.borderRight{
	border-right: 1px solid #C1DAD7;
}
.borderTop{
	border-top: 1px solid #C1DAD7;
}
#selectable{
	overflow:auto;
	height:700px;
}
#coordTable{
	overflow:auto;
}
#jgDiv td{
	border:none
}

#coordTable td,th{
	border:1px solid;
	border-color: #74A6CC;
}
.th1{width:30%;}.th2{width:15%;}.th3{width:10%;}.th4{width:15%;}.th5{width:30%;}
}
</style>
<odin:hidden property="mntp00"/>
<odin:hidden property="data"/>
<div  id="selectable"  style="overflow:auto;">
	<table id="autowidth" cellspacing="0"  style="margin:20 auto;" >
		<tr>
			<th class="th1">拟任岗位</th>
		    <th class="th2">姓名</th>
		    <th class="th3">性别</th>
		    <th class="th4">出生年月</th>
		    <th class="th5">任现工作单位及职务</th>
		</tr>
	</table>
</div>
<br/>
<%-- <div align="right"><img id="expSelect" onclick="expExcel()" src="<%=request.getContextPath()%>/images/tpbj/exp_excel.png" width="150px" 
style="cursor:pointer;margin-right:23px"/></div> --%>


<script type="text/javascript">
function pageload()
{
    var size = {
        width: window.innerWidth || document.body.clientWidth,
        height: window.innerHeight || document.body.clientHeight
    }

    var cw = (size.width - 60) / 3;
    $('.autowidth').css({ 'width': cw + 'px' });
    $('.autowidth').css({ 'height': cw + 'px' });
}

window.onload = pageload;



Ext.onReady(function() {
	$('#mntp00').val(parent.Ext.getCmp(subWinId).initialConfig.mntp00);
});

function AddGW(data){
	$.each(data, function (i,item) {
		var tr;
		var nrgw=item["nrgw"]
		tr = $('<tr><td colspan='+'5'+'></td></tr>');	
		var tds = $("td", tr);
		SetTDtext(tds[0],item["nrgw"]);
		$('#autowidth').append(tr);
	});
}
function AddGWMore(data){
	$.each(data, function (i,item) {
		var num=length;
		var tr;
		tr = $('<tr><td></td><td></td><td></td><td></td><td style="text-align:left; "></td></tr>');	
		var tds = $("td", tr);
		SetTDtext(tds[0],item["nrgw"]);
		SetTDtext(tds[1],item["name"]);
		SetTDtext(tds[2],item["sex"]);
		SetTDtext(tds[3],item["year"]);
		SetTDtext(tds[4],item["work"]);
		$('#autowidth').append(tr);
	});
}
function AddPeopleMore(data,length){
	$.each(data, function (i,item) {
		var num=length;
		var tr;
		if(i==0){
			tr = $('<tr><td rowspan='+num+'></td><td></td><td></td><td></td><td style="text-align:left; "></td></tr>');	
		}else{
			tr = $('<tr><td></td><td></td><td></td><td style="text-align:left; "></td></tr>');	
		}
		var tds = $("td", tr);
		if(i==0){
			SetTDtext(tds[0],item["nrgw"]);
			SetTDtext(tds[1],item["name"]);
			SetTDtext(tds[2],item["sex"]);
			SetTDtext(tds[3],item["year"]);
			SetTDtext(tds[4],item["work"]);
		}else{
			SetTDtext(tds[0],item["name"]);
			SetTDtext(tds[1],item["sex"]);
			SetTDtext(tds[2],item["year"]);
			SetTDtext(tds[3],item["work"]);
		}
		$('#autowidth').append(tr);
	});
}

function AddGWnull(data){
	$.each(data, function (i,item) {
		var tr;
		tr = $('<tr><td></td><td></td><td></td><td></td><td style="text-align:left; "></td></tr>');	
		var tds = $("td", tr);
		SetTDtext(tds[0],item["nrgw"]);
		SetTDtext(tds[1],item["name"]);
		SetTDtext(tds[2],item["sex"]);
		SetTDtext(tds[3],item["year"]);
		SetTDtext(tds[4],item["work"]);	
		$('#autowidth').append(tr);
	});
}








function expExcel(){
	
	var dataArray = [];
	var tr = $("#selectable tr");
	
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
	    ajaxSubmit('expExcel2',{"dataArray":Ext.encode(dataArray),"excelname":"调配情况"});
	}
}

function downloadByUUID(){
	var downloadUUID = document.getElementById('docpath').value;
	$('#iframe_expBZYP').attr('src','<%= request.getContextPath() %>/PublishFileServlet?method=downloadFile&uuid='+downloadUUID);
	return false
}
function SetTDtext(td,v) {
	  $(td).html((v==""||v==null||v=="null"||v=="0"||v=="-1"||v=="-2"||v=="-3"||v=="-4")?" ":v.replace(/\n/g,"<br/>"));
	}
</script>

