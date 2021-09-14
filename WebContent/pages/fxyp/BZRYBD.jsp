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
	height:800px;
}
#coordTable{
	overflow:auto;
	width:1370px;
}
#jgDiv td{
	border:none
}

#coordTable td,th{
	border:1px solid;
	border-color: #74A6CC;
}
.th1{width: 10%;}.th2{width: 6%;}.th3{width: 6%;}.th4{width: 8%;}.th5{width: 3%;}
.th6{width: 14%;}.th7{width: 10%;}.th8{width: 10%;}
}
</style>
<odin:hidden property="b0111"/>
<odin:hidden property="mntp00"/>
<odin:hidden property="b01id"/>
<odin:hidden property="mntp05"/>
<odin:hidden property="data"/>
<div  style="align:left top;overflow:auto;"  id="selectable">
	<table id="jgDiv" >
		<tr>
			<odin:select2 property="b0101" label="选择查询机构"  multiSelect="true"></odin:select2>
			<td>&nbsp;&nbsp;&nbsp;
			</td>
			<td>
			 	<input type="checkbox" id="showAll" checked='true' />
                <span style="font-family: SimHei;">显示调配人员</span>
			</td>
			<td>
				<odin:button text="查询" property="query" handler="query"></odin:button>
			</td>
		</tr>
	</table>
	<table id="coordTable" cellspacing="0" width="100%" >

		<tr>
			<th rowspan="2" class="th1">单位</th>
			<th rowspan="2" class="th2">机构性质</th>
			<th colspan="6">现状</th>
			<th colspan="7">拟任</th>
		</tr>
		<tr>
		    <th class="th2">姓名</th>
		    <th class="th5">性别</th>
		    <th class="th2">出生年月</th>
		    <th class="th6">任现工作单位及职务</th>
		    <th class="th2">政治面貌</th>
		    <th class="th2">去向</th>
		    <th class="th3">任现职务</th>
		    <th class="th2">姓名</th>
		    <th class="th5">性别</th>
		    <th class="th2">出生年月</th>
		    <th class="th1">现工作单位及职务</th>
		    <th class="th2">政治面貌</th>
		    <th class="th2">来源</th>
		</tr>
	</table>
	
</div>
<div align="right"><img id="expSelect" onclick="expExcel()" src="<%=request.getContextPath()%>/images/tpbj/exp_excel.png" width="150px" 
style="cursor:pointer;margin-right:23px"/></div>
<!-- <div  id="selectable2">
	<div >
	<table id="coordTable2" cellspacing="0" width="100%" >
		
		
	</table>
	</div>
</div> -->

<script type="text/javascript">




var B0131DECODE = {"1":"正职","3":"副职",'1001':'党委','1004':'政府','1003':'人大','1005':'政协','1006':'院长','1007':'检查长'};
//decode(b0131,'1001','1党委','1004','2政府','1003','3人大','1005','4政协','1006','5院长','1007','6检查长')
function decodeZRRY(value, params, record, rowIndex, colIndex, ds) {
	
	//return value;
	return B0131DECODE[value];
}
Ext.onReady(function() {
	
/* 	Ext.getCmp('pgrid').setWidth(document.body.clientWidth/2);
	Ext.getCmp('pgrid').setHeight(document.body.clientHeight);
	Ext.getCmp('pgrid2').setWidth(document.body.clientWidth/2);
	Ext.getCmp('pgrid2').setHeight(document.body.clientHeight); */
	
	$('#b0111').val(parent.Ext.getCmp(subWinId).initialConfig.b0111);
	$('#b01id').val(parent.Ext.getCmp(subWinId).initialConfig.b01id);
	$('#mntp00').val(parent.Ext.getCmp(subWinId).initialConfig.mntp00);
	$('#mntp05').val(parent.Ext.getCmp(subWinId).initialConfig.mntp05);
	
	var viewSize = Ext.getBody().getViewSize();
	var height=viewSize.height;
	$("#selectable").css('height',height-40);	
	$("#selectable").css('width',viewSize.width);
	
});
function AddSz1(data,data1,length){
	$.each(data, function (i,item) {
		//console.log(item['zzsp'])
		var num=length;
		var tr;
		var fxyp07=item["fxyp07"]
		if(i==0){
			tr = $('<tr><td rowspan='+num+' ></td><td rowspan='+num+'></td>'+
					'<td></td><td></td><td></td><td></td><td></td><td></td>'+
					'<td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>');
		}else{
			tr = $('<tr>'+
					'<td></td><td></td><td></td><td></td><td></td><td></td>'+
					'<td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>');
		}
		//var tr = $('<tr><td  class="textleft"></td><td></td><td></td><td></td><td></td><td></td><td></td><td class="borderRight"></td></tr>');
		var tds = $("td", tr);
 		if(i==0){
			SetTDtext(tds[0],item["b0101"]);
			SetTDtext(tds[1],"");
			SetTDtext(tds[2],item["a0101"]);
			SetTDtext(tds[3],item["a0104"]);
			SetTDtext(tds[4],item["a0107"]);
			SetTDtext(tds[5],item["a0192a"]);
			SetTDtext(tds[6],item["a0141"]);
			SetTDtext(tds[7],item["a01bzdesc"]);
			if(fxyp07 !=null){
				SetNMcolor(tds[2]);
				SetNMcolor(tds[3]);
				SetNMcolor(tds[4]);
				SetNMcolor(tds[5]);
				SetNMcolor(tds[6]);
				SetNMcolor(tds[7]);
			}
		}else{
/* 			SetTDtext(tds[0],""); */
			SetTDtext(tds[0],item["a0101"]);
			SetTDtext(tds[1],item["a0104"]);
			SetTDtext(tds[2],item["a0107"]);
			SetTDtext(tds[3],item["a0192a"]);
			SetTDtext(tds[4],item["a0141"]);
			SetTDtext(tds[5],item["a01bzdesc"]);
			if(fxyp07 !=null){
				SetNMcolor(tds[0]);
				SetNMcolor(tds[1]);
				SetNMcolor(tds[2]);
				SetNMcolor(tds[3]);
				SetNMcolor(tds[4]);
				SetNMcolor(tds[5]);
			}
		}
 		$.each(data1, function (j,item1) {
 			var fxyp07a=item1["fxyp07"];
 			if(i==j && i==0){
 				SetTDtext(tds[8],item1["a0215a"]);
 				SetTDtext(tds[9],item1["a0101"]);
 				SetTDtext(tds[10],item1["a0104"]);
 				SetTDtext(tds[11],item1["a0107"]);
 				SetTDtext(tds[12],item1["a0192a"]);
 				SetTDtext(tds[13],item1["a0141"]);
 				SetTDtext(tds[14],item1["a01bzdesc"]);
 				if(fxyp07a !=null){
 					SetNRcolor(tds[8]);
 					SetNRcolor(tds[9]);
 					SetNRcolor(tds[10]);
 					SetNRcolor(tds[11]);
 					SetNRcolor(tds[12]);
 					SetNRcolor(tds[13]);
 					SetNRcolor(tds[14]);
 				}
 			}else if(i==j && i!=0){
 				SetTDtext(tds[6],item1["a0215a"]);
 				SetTDtext(tds[7],item1["a0101"]);
 				SetTDtext(tds[8],item1["a0104"]);
 				SetTDtext(tds[9],item1["a0107"]);
 				
 				SetTDtext(tds[10],item1["a0192a"]);
 				SetTDtext(tds[11],item1["a0141"]);
 				SetTDtext(tds[12],item1["a01bzdesc"]);
 				if(fxyp07a !=null){
 					SetNRcolor(tds[6]);
 					SetNRcolor(tds[7]);
 					SetNRcolor(tds[8]);
 					SetNRcolor(tds[9]);
 					SetNRcolor(tds[10]);
 					SetNRcolor(tds[11]);
 					SetNRcolor(tds[12]);
 				}
 			}
 		}); 
		//console.log(i)
		//coordTable
		$('#coordTable').append(tr);
	});
}


function AddSz2(data,data1,length){
	$.each(data, function (i,item) {
		//console.log(item['zzsp'])
		var num=length;
		var tr;
		var fxyp07=item["fxyp07"]
		if(i==0){
			tr = $('<tr><td rowspan='+num+' ></td><td rowspan='+num+'></td>'+
					'<td></td><td></td><td></td><td></td><td></td><td></td>'+
					'<td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>');
		}else{
			tr = $('<tr>'+
					'<td></td><td></td><td></td><td></td><td></td><td></td>'+
					'<td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>');
		}
		//var tr = $('<tr><td  class="textleft"></td><td></td><td></td><td></td><td></td><td></td><td></td><td class="borderRight"></td></tr>');
		var tds = $("td", tr);
 		if(i==0){
 			SetTDtext(tds[0],item["b0101"]);
			SetTDtext(tds[1],"");
			SetTDtext(tds[8],item["a0215a"]);
 			SetTDtext(tds[9],item["a0101"]);
			SetTDtext(tds[10],item["a0104"]);
			SetTDtext(tds[11],item["a0107"]);
			
			SetTDtext(tds[12],item["a0192a"]);
			SetTDtext(tds[13],item["a0141"]);
			SetTDtext(tds[14],item["a01bzdesc"]);
			if(fxyp07 !=null){
				SetNRcolor(tds[8]);
				SetNRcolor(tds[9]);
				SetNRcolor(tds[10]);
				SetNRcolor(tds[11]);
				SetNRcolor(tds[12]);
				SetNRcolor(tds[13]);
				SetNRcolor(tds[14]);
			}
		}else{
/* 			SetTDtext(tds[0],""); */
			SetTDtext(tds[6],item["a0215a"]);
			SetTDtext(tds[7],item["a0101"]);
 			SetTDtext(tds[8],item["a0104"]);
 		    SetTDtext(tds[9],item["a0107"]);
 			
 			SetTDtext(tds[10],item["a0192a"]);
 			SetTDtext(tds[11],item["a0141"]);
 			SetTDtext(tds[12],item["a01bzdesc"]);
			if(fxyp07 !=null){
				SetNRcolor(tds[6]);
				SetNRcolor(tds[7]);
				SetNRcolor(tds[8]);
				SetNRcolor(tds[9]);
				SetNRcolor(tds[10]);
				SetNRcolor(tds[11]);
				SetNRcolor(tds[12]);
			}
		}
 		$.each(data1, function (j,item1) {
 			var fxyp07a=item1["fxyp07"];
 			if(i==j && i==0){
 				SetTDtext(tds[2],item1["a0101"]);
 				SetTDtext(tds[3],item1["a0104"]);
 				SetTDtext(tds[4],item1["a0107"]);
 				SetTDtext(tds[5],item1["a0192a"]);
 				SetTDtext(tds[6],item1["a0141"]);
 				SetTDtext(tds[7],item1["a01bzdesc"]);
 				if(fxyp07a !=null){
 					SetNMcolor(tds[2]);
 					SetNMcolor(tds[3]);
 					SetNMcolor(tds[4]);
 					SetNMcolor(tds[5]);
 					SetNMcolor(tds[6]);
 					SetNMcolor(tds[7]);
 				}
 			}else if(i==j && i!=0){
 				SetTDtext(tds[0],item1["a0101"]);
 				SetTDtext(tds[1],item1["a0104"]);
 				SetTDtext(tds[2],item1["a0107"]);
 				SetTDtext(tds[3],item1["a0192a"]);
 				SetTDtext(tds[4],item1["a0141"]);
 				SetTDtext(tds[5],item1["a01bzdesc"]);
 				if(fxyp07a !=null){
 					SetNMcolor(tds[0]);
 					SetNMcolor(tds[1]);
 					SetNMcolor(tds[2]);
 					SetNMcolor(tds[3]);
 					SetNMcolor(tds[4]);
 					SetNMcolor(tds[5]);
 				}
 			}
 		}); 
		//console.log(i)
		//coordTable
		$('#coordTable').append(tr);
	});
}

function AddQx1(data,data1,totallength,length,num){
	$.each(data, function (i,item) {
		var tr;
		var fxyp07=item["fxyp07"]
		if(num==0 && i==0){
			tr = $('<tr><td rowspan='+totallength+' ></td><td rowspan='+length+'></td>'+
					'<td></td><td></td><td></td><td></td><td></td><td></td>'+
					'<td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>');
		}else if(i==0){
			tr = $('<tr><td rowspan='+length+'></td>'+
					'<td></td><td></td><td></td><td></td><td></td><td></td>'+
					'<td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>');
		}else{
			tr = $('<tr>'+
					'<td></td><td></td><td></td><td></td><td></td><td></td>'+
					'<td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>');
		}
		var tds = $("td", tr);
		if(num==0 && i==0){
			SetTDtext(tds[0],item["b0101"]);
			SetTDtext(tds[1],item["zrrx"]);
			SetTDtext(tds[2],item["a0101"]);
			SetTDtext(tds[3],item["a0104"]);
			SetTDtext(tds[4],item["a0107"]);
			SetTDtext(tds[5],item["a0192a"]);
			SetTDtext(tds[6],item["a0141"]);
			SetTDtext(tds[7],item["a01bzdesc"]);
			if(fxyp07 !=null){
				SetNMcolor(tds[2]);
				SetNMcolor(tds[3]);
				SetNMcolor(tds[4]);
				SetNMcolor(tds[5]);
				SetNMcolor(tds[6]);
				SetNMcolor(tds[7]);
			}
		}else if (i==0){
			SetTDtext(tds[0],item["zrrx"]);
			SetTDtext(tds[1],item["a0101"]);
			SetTDtext(tds[2],item["a0104"]);
			SetTDtext(tds[3],item["a0107"]);
			SetTDtext(tds[4],item["a0192a"]);
			SetTDtext(tds[5],item["a0141"]);
			SetTDtext(tds[6],item["a01bzdesc"]);
			if(fxyp07 !=null){
				SetNMcolor(tds[1]);
				SetNMcolor(tds[2]);
				SetNMcolor(tds[3]);
				SetNMcolor(tds[4]);
				SetNMcolor(tds[5]);
				SetNMcolor(tds[6]);
			}
		}else{
			SetTDtext(tds[0],item["a0101"]);
			SetTDtext(tds[1],item["a0104"]);
			SetTDtext(tds[2],item["a0107"]);
			SetTDtext(tds[3],item["a0192a"]);
			SetTDtext(tds[4],item["a0141"]);
			SetTDtext(tds[5],item["a01bzdesc"]);
			if(fxyp07 !=null){
				SetNMcolor(tds[0]);
				SetNMcolor(tds[1]);
				SetNMcolor(tds[2]);
				SetNMcolor(tds[3]);
				SetNMcolor(tds[4]);
				SetNMcolor(tds[5]);
			}
		}
		$.each(data1, function (k,item1) {
 			var fxyp07a=item1["fxyp07"];
 			if(i==k && i==0 && num==0){
 				SetTDtext(tds[8],item1["a0215a"]);
 				SetTDtext(tds[9],item1["a0101"]);
 				SetTDtext(tds[10],item1["a0104"]);
 				SetTDtext(tds[11],item1["a0107"]);
 				
 				SetTDtext(tds[12],item1["a0192a"]);
 				SetTDtext(tds[13],item1["a0141"]);
 				SetTDtext(tds[14],item1["a01bzdesc"]);
 				if(fxyp07a!= null){
 					SetNRcolor(tds[8]);
 					SetNRcolor(tds[9]);
 					SetNRcolor(tds[10]);
 					SetNRcolor(tds[11]);
 					SetNRcolor(tds[12]);
 					SetNRcolor(tds[13]);
 					SetNRcolor(tds[14]);
 				}
 			}else if(i==k && i==0){
 				SetTDtext(tds[7],item1["a0215a"]);
 				SetTDtext(tds[8],item1["a0101"]);
 				SetTDtext(tds[9],item1["a0104"]);
 				SetTDtext(tds[10],item1["a0107"]);
 				
 				SetTDtext(tds[11],item1["a0192a"]);
 				SetTDtext(tds[12],item1["a0141"]);
 				SetTDtext(tds[13],item1["a01bzdesc"]);
 				if(fxyp07a!= null){
 					SetNRcolor(tds[7]);
 					SetNRcolor(tds[8]);
 					SetNRcolor(tds[9]);
 					SetNRcolor(tds[10]);
 					SetNRcolor(tds[11]);
 					SetNRcolor(tds[12]);
 					SetNRcolor(tds[13]);
 				}
 			}else if (i==k && i!=0){
 				SetTDtext(tds[6],item1["a0215a"]);
 				SetTDtext(tds[7],item1["a0101"]);
 	 			SetTDtext(tds[8],item1["a0104"]);
 	 		    SetTDtext(tds[9],item1["a0107"]);
 	 			
 	 			SetTDtext(tds[10],item1["a0192a"]);
 	 			SetTDtext(tds[11],item1["a0141"]);
 	 			SetTDtext(tds[12],item1["a01bzdesc"]);
 	 			if(fxyp07a!= null){
 	 				SetNRcolor(tds[6]);
 					SetNRcolor(tds[7]);
 					SetNRcolor(tds[8]);
 					SetNRcolor(tds[9]);
 					SetNRcolor(tds[10]);
 					SetNRcolor(tds[11]);
 					SetNRcolor(tds[12]);
 				}
 			}
		});
		$('#coordTable').append(tr);
	});
}

function AddQx2(data,data1,totallength,length,num){
	$.each(data, function (i,item) {
		var tr;
		var fxyp07=item["fxyp07"]
		if(num==0 && i==0){
			tr = $('<tr><td rowspan='+totallength+' ></td><td rowspan='+length+'></td>'+
					'<td></td><td></td><td></td><td></td><td></td><td></td>'+
					'<td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>');
		}else if(i==0){
			tr = $('<tr><td rowspan='+length+'></td>'+
					'<td></td><td></td><td></td><td></td><td></td><td></td>'+
					'<td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>');
		}else{
			tr = $('<tr>'+
					'<td></td><td></td><td></td><td></td><td></td><td></td>'+
					'<td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>');
		}
		var tds = $("td", tr);
		if(num==0 && i==0){
			SetTDtext(tds[0],item["b0101"]);
			SetTDtext(tds[1],item["zrrx"]);
			SetTDtext(tds[8],item["a0215a"]);
			SetTDtext(tds[9],item["a0101"]);
			SetTDtext(tds[10],item["a0104"]);
			SetTDtext(tds[11],item["a0107"]);
			
			SetTDtext(tds[12],item["a0192a"]);
			SetTDtext(tds[13],item["a0141"]);
			SetTDtext(tds[14],item["a01bzdesc"]);
			if(fxyp07!=null){
				SetNRcolor(tds[8]);
				SetNRcolor(tds[9]);
				SetNRcolor(tds[10]);
				SetNRcolor(tds[11]);
				SetNRcolor(tds[12]);
				SetNRcolor(tds[13]);
				SetNRcolor(tds[14]);
			}
		}else if (i==0){
			SetTDtext(tds[0],item["zrrx"]);
			SetTDtext(tds[7],item["a0215a"]);
			SetTDtext(tds[8],item["a0101"]);
		    SetTDtext(tds[9],item["a0104"]);
			SetTDtext(tds[10],item["a0107"]);
			
			SetTDtext(tds[11],item["a0192a"]);
			SetTDtext(tds[12],item["a0141"]);
			SetTDtext(tds[13],item["a01bzdesc"]);
			if(fxyp07!=null){
				SetNRcolor(tds[7]);
				SetNRcolor(tds[8]);
				SetNRcolor(tds[9]);
				SetNRcolor(tds[10]);
				SetNRcolor(tds[11]);
				SetNRcolor(tds[12]);
				SetNRcolor(tds[13]);
			}
		}else{
			SetTDtext(tds[6],item["a0215a"]);
			SetTDtext(tds[7],item["a0101"]);
	 		SetTDtext(tds[8],item["a0104"]);
	 		SetTDtext(tds[9],item["a0107"]);
	 		
	 		SetTDtext(tds[10],item["a0192a"]);
	 		SetTDtext(tds[11],item["a0141"]);
	 		SetTDtext(tds[12],item["a01bzdesc"]);
	 		if(fxyp07!=null){
				SetNRcolor(tds[6]);
				SetNRcolor(tds[7]);
				SetNRcolor(tds[8]);
				SetNRcolor(tds[9]);
				SetNRcolor(tds[10]);
				SetNRcolor(tds[11]);
				SetNRcolor(tds[12]);
			}
		}
		$.each(data1, function (k,item1) {
 			var fxyp07a=item1["fxyp07"];
 			if(i==k && i==0 && num==0){
 				SetTDtext(tds[2],item1["a0101"]);
 				SetTDtext(tds[3],item1["a0104"]);
 				SetTDtext(tds[4],item1["a0107"]);
 				SetTDtext(tds[5],item1["a0192a"]);
 				SetTDtext(tds[6],item1["a0141"]);
 				SetTDtext(tds[7],item1["a01bzdesc"]);
 				if(fxyp07a !=null){
 					SetNMcolor(tds[2]);
 					SetNMcolor(tds[3]);
 					SetNMcolor(tds[4]);
 					SetNMcolor(tds[5]);
 					SetNMcolor(tds[6]);
 					SetNMcolor(tds[7]);
 				}
 			}else if(i==k && i==0){
 				SetTDtext(tds[1],item1["a0101"]);
 				SetTDtext(tds[2],item1["a0104"]);
 				SetTDtext(tds[3],item1["a0107"]);
 				SetTDtext(tds[4],item1["a0192a"]);
 				SetTDtext(tds[5],item1["a0141"]);
 				SetTDtext(tds[6],item1["a01bzdesc"]);
 				if(fxyp07a !=null){
 					SetNMcolor(tds[1]);
 					SetNMcolor(tds[2]);
 					SetNMcolor(tds[3]);
 					SetNMcolor(tds[4]);
 					SetNMcolor(tds[5]);
 					SetNMcolor(tds[6]);
 				}
 			}else if (i==k && i!=0){
 				SetTDtext(tds[0],item1["a0101"]);
 				SetTDtext(tds[1],item1["a0104"]);
 				SetTDtext(tds[2],item1["a0107"]);
 				SetTDtext(tds[3],item1["a0192a"]);
 				SetTDtext(tds[4],item1["a0141"]);
 				SetTDtext(tds[5],item1["a01bzdesc"]);
 				if(fxyp07a !=null){
 					SetNMcolor(tds[0]);
 					SetNMcolor(tds[1]);
 					SetNMcolor(tds[2]);
 					SetNMcolor(tds[3]);
 					SetNMcolor(tds[4]);
 					SetNMcolor(tds[5]);
 				}
 			}
		});
		$('#coordTable').append(tr);
	});
}


function SetTDtext(td,v) {
	  $(td).html((v==""||v==null||v=="null"||v=="0"||v=="-1"||v=="-2"||v=="-3"||v=="-4")?" ":v.replace(/\n/g,"<br/>"));
	}
function SetNMcolor(td){
	$(td).css("background","#CAFFFF");
}
function SetNRcolor(td){
	$(td).css("background","#FFC1E0");
}

function expExcel(){
	
	var dataArray = [];
	var tr = $("#coordTable tr");
	
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
	colorArray=color();
	if(dataArray.length>0){
	    ajaxSubmit('expExcel2',{"dataArray":Ext.encode(dataArray),"excelname":"调配情况","colorArray":Ext.encode(colorArray) });
	}
}

function downloadByUUID(){
	var downloadUUID = document.getElementById('docpath').value;
	$('#iframe_expBZYP').attr('src','<%= request.getContextPath() %>/PublishFileServlet?method=downloadFile&uuid='+downloadUUID);
	return false
}
function query(){
	var tb = document.getElementById('coordTable');
    var rowNum=tb.rows.length;
    for (i=2;i<rowNum;i++)
    {
        tb.deleteRow(i);
        rowNum=rowNum-1;
        i=i-1;
    }
	radow.doEvent("query");
}

function color(){
	var colorArray = [];
	 var tableObj = document.getElementById('coordTable');
	 colorArray.push("BZRYBD")
	 for (var i = 2; i < tableObj.rows.length; i++) {    //遍历Table的所有Row
		 var aa=tableObj.rows[i].cells[5].style.backgroundColor;
		 var bb=tableObj.rows[i].cells[12].style.backgroundColor;
		 	if(aa=="rgb(202, 255, 255)"||aa=="#caffff") {
		 		colorArray.push("B");
		 	}else{
		 		colorArray.push("W");
		 	}
		 
		 	if(bb=="rgb(255, 193, 224)"||aa=="#ffc1e0") {
		 		colorArray.push("R");
		 	}else{
		 		colorArray.push("W");
		 	}

	    } 
	 return colorArray;
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
</script>
