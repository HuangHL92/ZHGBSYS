<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<%@page import="com.insigma.odin.framework.util.GlobalNames"%>
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
#selectable{
	height:800px;
}
#coordTable{
	overflow-y:auto;
	width:1450px;
}

#jgDiv td{
	border:none
}

#coordTable td,th{
	font-size:15px;
	border:1px solid;
	border-color: #74A6CC;
}

.name{
	cursor:pointer;
} 
.dbwy{
	cursor:pointer;
}
.bz{
	cursor:pointer;
}
.unit{
	cursor:pointer;
} 
.dwbz{
	cursor:pointer;
} 
.hovercolor{
	background-color:#C0C0C0 !important;
} 
.th10{width: 10%;}.th6{width: 6%;}
.th7{width: 7%;}.th9{width: 9%;}.th11{width: 11%;}
}

</style>
<odin:hidden property="b0111"/>
<odin:hidden property="mntp00"/>
<odin:hidden property="mntp00add"/>
<odin:hidden property="mntpnum"/>
<odin:hidden property="b01id"/>
<odin:hidden property="mntp05"/>
<odin:hidden property="data"/>
<odin:hidden property="changedbwy" value='1'/>
<div  style="align:left top;overflow:auto;"  id="selectable">
	<div style="width: 12%;height:8%;float: left;">
		<odin:select2 property="b0101" label="&nbsp;&nbsp;选择查询机构："  multiSelect="true"></odin:select2>
	</div>
	<div style="width: 10%;height:8%;float: left;vertical-align:middle;">
		<br/>
		<input type="checkbox" id="showAll"  checked='true'/>
	    <span style="font-family: SimHei;">显示调配人员</span>
	</div>
	<div style="width: 13%;height:8%;float: left;vertical-align:middle;">
		<br/>
		<input type="checkbox" id="showgrey"  />
	    <span style="font-family: SimHei;">显示选择性显示人员</span>
	</div>
	<div style="width: 10%;height:8%;float: left;">
		<button type='button' onclick="showorhidedbwy()" id='showdbwy' style='margin-top:20px'>显示代表委员</button>
		<%-- <odin:button  text="查看" property="query" handler="query" ></odin:button> --%>
	</div>
	<div style="width: 10%;height:8%;float: left;">
		<button type='button' onclick="query()" style='margin-top:20px'>查看</button>
		<%-- <odin:button  text="查看" property="query" handler="query" ></odin:button> --%>
	</div>
	<div class="top_btn_style" 
		 style="background-color:#F08000;  
		line-height:25px;float:left;margin-left:400px;margin-top:10px" onclick="rybd()">人员比对
	</div>
<!-- 	<div class="top_btn_style" 
		 style="background-color:#F08000;  
		line-height:25px;float:left;margin-top:10px" onclick="fabd()">添加方案对比
	</div> -->
<%-- 	<table id="jgDiv">
		<tr>
			<odin:select2 property="b0101" label="选择查询机构"  multiSelect="true"></odin:select2>
			<td>
			 	<input type="checkbox" id="showAll"  checked='true'/>
                <span style="font-family: SimHei;">显示调配人员</span>
			</td>
			<td id="queryButton">
				<div id="divgo"><odin:button text="查看" property="query" handler="query" ></odin:button></div>
			</td>
			<td>
			<div id="ButtonDiv" style="border:none;text-align:right;">
			<button onclick="rybd()" type="button" style="border-radius:5px;background-color: #F08000;border: none;width:80px;height:30px;
    		cursor:pointer;color: white;text-align: center;text-decoration: none;display: inline-block;font-size: 16px;">人员比对</button>
			</div>
			</td>
		</tr>
	</table> --%>
	<table id="coordTable" cellspacing="0"  >

		<tr>
			<th rowspan="2" style='width: 10%;background: rgb(202,232,234);' colspan='2'>单位</th>
<!-- 			<th rowspan="2" class="th2">机构性质</th> -->
			<th rowspan="2" style='width: 10%;background: rgb(202,232,234);'>拟任职务</th>
			<th colspan="4" class='now' style="background: rgb(202,232,234);">现状</th>
			<th colspan="8" class='tp' style="background: rgb(202,232,234);">调配方案</th>
			
		</tr>
		<tr>
		    <th style='width: 5%;background: rgb(202,232,234);'>姓名</th>
		    <th style='width: 5%;background: rgb(202,232,234);' >出生年月</th>
		    <th style='width: 7%;background: rgb(202,232,234);' class="dbwytitle" >代表委员</th>
		    <th style='width: 8%;background: rgb(202,232,234);' >调配意见</th>
		    
		    <th style='width: 5%;background: rgb(202,232,234);' >姓名</th>
		    <th style='width: 5%;background: rgb(202,232,234);'>出生年月</th>
		    <th style='width: 10%;background: rgb(202,232,234);'>任现工作单位及职务</th>
		    <th style='width: 5%;background: rgb(202,232,234);'>全日制学历</th>
		    <th style='width: 5%;background: rgb(202,232,234);'>最高学历</th>
		    <th style='width: 7%;background: rgb(202,232,234);' class="dbwytitle">代表委员</th>
		    <th style='width: 8%;background: rgb(202,232,234);'>调配意见</th>
		    <th style='width: 217;background: rgb(202,232,234);'>单位备注</th>
		    
		</tr>
	</table>
	
</div>
<div align="right">
					<div class="top_btn_style" 
					 style="background-color:#F08000;  
					  line-height:25px;float:right;" onclick="expExcel()">导出excel
					</div>
					<div class="top_btn_style" 
					 style="background-color:#F08000;  
					  line-height:25px;float:right;" onclick="openBDHZ()">变动汇总
					</div>
<%-- <img id="expSelect" onclick="expExcel()" src="<%=request.getContextPath()%>/images/tpbj/exp_excel.png" width="150px" 
style="cursor:pointer;margin-right:23px"/> --%></div>
<!-- <div  id="selectable2">
	<div >
	<table id="coordTable2" cellspacing="0" width="100%" >
		
		
	</table>
	</div>
</div> -->

<script type="text/javascript">


var TABLE_CONFIG={};

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
	
	var changedbwy=document.getElementById('changedbwy').value;
	if(changedbwy==1){
		$('.now').attr("colSpan",3); 
		$('.tp').attr("colSpan",7); 
		$('.dbwytitle').hide();
		$('.dbwy').hide();	
		document.getElementById('showdbwy').innerHTML='显示代表委员';
		document.getElementById('changedbwy').value=0;
	}else if(changedbwy==0){
		$('.now').attr("colSpan",4); 
		$('.tp').attr("colSpan",8);
		$('.dbwytitle').show();
		$('.dbwy').show();
		document.getElementById('showdbwy').innerHTML='隐藏代表委员';
		document.getElementById('changedbwy').value=1;
	}	
	
});
function AddSz(data,data1,length){
	var k=0;
	$.each(data, function (i,item) {
		//console.log(item['zzsp'])
		var num=item["peoplenum"];
		var tr;
		var bdfxyp00=item["bdfxyp00"];
		var fxyp07=item["fxyp07"];
		var tds;
		if(i==0){
			if(num>0){		
				tr = $('<tr><td class="unit" rowspan='+length+' colspan="2"></td><td rowspan='+num+'></td>'+
						'<td class="name"></td><td class="name"></td><td class="dbwy"></td><td class="bz"></td>'+
						'<td class="name"></td><td class="name"></td><td class="name"></td><td class="name"></td><td class="name"></td>'+
						'<td class="dbwy"></td><td class="bz"></td><td class="dwbz" rowspan='+length+'></td></tr>');
				tds= $("td", tr);
				SetTDtext(tds[0],item["b0101"]);SetB0111(tds[0],item["b0111p"]);SetB0111(tds[13],item["b0111p"]);
				SetTDtext(tds[1],item["a0215a"]);
				if(item["zwqc01"]>1){
					SetTDtext(tds[1],item["a0215a"]+'('+item["zwqc01"]+')');
				}
				if(bdfxyp00!=null && bdfxyp00!=""){
					$.each(data1, function (m,item1) {
						if(item1["fxyp00"]==bdfxyp00){
							SetTDtext(tds[2],item1["a0101"]);SetA0000(tds[2],item1["a0000"]);
/* 							SetTDtext(tds[3],item1["a0104"]); */
							SetTDtext(tds[3],item1["a0107"]);SetA0000(tds[3],item1["a0000"]);
							SetTDtext(tds[4],item1["dbwy"]);SetA0000(tds[4],item1["a0000"]);
							SetTDtext(tds[5],item1["rybz"]);SetA0000(tds[5],item1["a0000"]);
							
							if(item1["fxyp07"]==-1){
								for(var j=2;j<=5;j++){
									SetNMcolor(tds[j]);
								}
							}
						}
						
					});
				}else{
					for(var j=2;j<=5;j++){
						SetTDtext(tds[j],"");
					}
				}
				SetTDtext(tds[6],item["a0101"]);SetA0000(tds[6],item["a0000"]);
/* 				SetTDtext(tds[9],item["a0104"]); */
				SetTDtext(tds[7],item["a0107"]);SetA0000(tds[7],item["a0000"]);
				SetTDtext(tds[8],item["a0192a"]);SetA0000(tds[8],item["a0000"]);
				SetTDtext(tds[9],item["qrzxl"]);SetA0000(tds[9],item["a0000"]);
				SetTDtext(tds[10],item["zgxl"]);SetA0000(tds[10],item["a0000"]);
				SetTDtext(tds[11],item["dbwy"]);SetA0000(tds[11],item["a0000"]);
				SetTDtext(tds[12],item["rybz"]);SetA0000(tds[12],item["a0000"]);
/* 				SetTDtext(tds[14],item["dwbz"]);SetA0000(tds[14],item["a0000"]); */
				for(var j=6;j<=12;j++){
					SetNRcolor(tds[j]);
				}
				k=num-1;
			}else{
				tr = $('<tr><td class="unit"  rowspan='+length+' colspan="2"></td><td></td>'+
						'<td class="name"></td><td class="name"></td><td class="dbwy"></td><td class="bz"></td>'+
						'<td class="name"></td><td class="name"></td><td class="name"></td><td class="name"></td><td class="name"></td>'+
						'<td class="dbwy"></td><td class="bz"></td><td class="dwbz" rowspan='+length+' ></td></tr>');
				tds= $("td", tr);
				SetTDtext(tds[0],item["b0101"]);SetB0111(tds[0],item["b0111p"]);SetB0111(tds[13],item["b0111p"]);
				SetTDtext(tds[1],item["a0215a"]);
				SetTDtext(tds[2],item["a0101"]);SetA0000(tds[2],item["a0000"]);
/* 				SetTDtext(tds[4],item["a0104"]); */
				SetTDtext(tds[3],item["a0107"]);SetA0000(tds[3],item["a0000"]);
				SetTDtext(tds[4],item["dbwy"]);SetA0000(tds[4],item["a0000"]);
				SetTDtext(tds[5],item["rybz"]);SetA0000(tds[5],item["a0000"]);
/* 				SetTDtext(tds[6],item["dwbz"]);SetA0000(tds[6],item["a0000"]); */
				
				SetTDtext(tds[6],item["a0101"]);SetA0000(tds[6],item["a0000"]);
				SetTDtext(tds[7],item["a0107"]);SetA0000(tds[7],item["a0000"]);
/* 				SetTDtext(tds[11],item["a0104"]); */
				SetTDtext(tds[8],item["a0192a"]);SetA0000(tds[8],item["a0000"]);
				SetTDtext(tds[9],item["qrzxl"]);SetA0000(tds[9],item["a0000"]);
				SetTDtext(tds[10],item["zgxl"]);SetA0000(tds[10],item["a0000"]);
				SetTDtext(tds[11],item["dbwy"]);SetA0000(tds[11],item["a0000"]);
				SetTDtext(tds[12],item["rybz"]);SetA0000(tds[12],item["a0000"]);
/* 				SetTDtext(tds[14],item["dwbz"]);SetA0000(tds[14],item["a0000"]); */
				if(fxyp07==-1){
					for(var j=1;j<=12;j++){
						SetNMcolor(tds[j]);
							
					}
					for(var j=6;j<=12;j++){
						SetTDtext(tds[j],"");
						SetA0000(tds[j],"");
					}
				}else if(fxyp07==1){
					for(var j=6;j<=12;j++){
						SetNRcolor(tds[j]);
					}
					if(bdfxyp00!=null && bdfxyp00!=""){
						$.each(data1, function (m,item1) {
							if(item1["fxyp00"]==bdfxyp00){
								SetTDtext(tds[2],item1["a0101"]);SetA0000(tds[2],item1["a0000"]);
/* 								SetTDtext(tds[4],item1["a0104"]); */
								SetTDtext(tds[3],item1["a0107"]);SetA0000(tds[3],item1["a0000"]);
								SetTDtext(tds[4],item1["dbwy"]);SetA0000(tds[4],item1["a0000"]);
								SetTDtext(tds[5],item1["rybz"]);SetA0000(tds[5],item1["a0000"]);
/* 								SetTDtext(tds[6],item1["dwbz"]);SetA0000(tds[6],item1["a0000"]); */
								if(item1["fxyp07"]==-1){
									for(var j=2;j<=5;j++){
										SetNMcolor(tds[j]);
									}
								}
							}
							
							
						});
					}else{
						for(var j=2;j<=5;j++){
							SetTDtext(tds[j],"");
						}
					}
				}
			}
		}else{
			if(num>0){
				if(k==0){
					tr = $('<tr><td rowspan='+num+'></td>'+
							'<td class="name"></td><td class="name"></td><td class="dbwy"></td><td class="bz"></td>'+
							'<td class="name"></td><td class="name"></td><td class="name"></td><td class="name"></td><td class="name"></td>'+
							'<td class="dbwy"></td><td class="bz"></td></tr>');
					tds= $("td", tr);
					SetTDtext(tds[0],item["a0215a"]);
					if(item["zwqc01"]>1){
						SetTDtext(tds[0],item["a0215a"]+'('+item["zwqc01"]+')');
					}
					if(bdfxyp00!=null && bdfxyp00!=""){
						$.each(data1, function (m,item1) {
							if(item1["fxyp00"]==bdfxyp00){
								SetTDtext(tds[1],item1["a0101"]);SetA0000(tds[1],item1["a0000"]);
/* 								SetTDtext(tds[2],item1["a0104"]); */
								SetTDtext(tds[2],item1["a0107"]);SetA0000(tds[2],item1["a0000"]);
								SetTDtext(tds[3],item1["dbwy"]);SetA0000(tds[3],item1["a0000"]);
								SetTDtext(tds[4],item1["rybz"]);SetA0000(tds[4],item1["a0000"]);
/* 								SetTDtext(tds[5],item1["dwbz"]);SetA0000(tds[5],item1["a0000"]); */
								if(item1["fxyp07"]==-1){
									for(var j=1;j<=4;j++){
										SetNMcolor(tds[j]);
									}
								}
							}						
						});
					}else{
						for(var j=1;j<=4;j++){
							SetTDtext(tds[j],"");
						}
					}
					SetTDtext(tds[5],item["a0101"]);SetA0000(tds[5],item["a0000"]);
					SetTDtext(tds[6],item["a0107"]);SetA0000(tds[6],item["a0000"]);
/* 					SetTDtext(tds[9],item["a0104"]); */
					SetTDtext(tds[7],item["a0192a"]);SetA0000(tds[7],item["a0000"]);
					SetTDtext(tds[8],item["qrzxl"]);SetA0000(tds[8],item["a0000"]);
					SetTDtext(tds[9],item["zgxl"]);SetA0000(tds[9],item["a0000"]);
					SetTDtext(tds[10],item["dbwy"]);SetA0000(tds[10],item["a0000"]);
					SetTDtext(tds[11],item["rybz"]);SetA0000(tds[11],item["a0000"]);
/* 					SetTDtext(tds[12],item["dwbz"]);SetA0000(tds[13],item["a0000"]); */
					for(var j=5;j<=12;j++){
						SetNRcolor(tds[j]);
					}
					k=num-1;	
				}else{
					tr = $('<tr>'+
							'<td class="name"></td><td class="name"></td><td class="dbwy"></td><td class="bz"></td>'+
							'<td class="name"></td><td class="name"></td><td class="name"></td><td class="name"></td><td class="name"></td>'+
							'<td class="dbwy"></td><td class="bz"></td></tr>');
					tds= $("td", tr);
					for(var j=0;j<=3;j++){
						SetTDtext(tds[j],"");
					}
					SetTDtext(tds[4],item["a0101"]);SetA0000(tds[4],item["a0000"]);
					SetTDtext(tds[5],item["a0107"]);SetA0000(tds[5],item["a0000"]);
/* 					SetTDtext(tds[7],item["a0104"]); */
					SetTDtext(tds[6],item["a0192a"]);SetA0000(tds[6],item["a0000"]);
					SetTDtext(tds[7],item["qrzxl"]);SetA0000(tds[7],item["a0000"]);
					SetTDtext(tds[8],item["zgxl"]);SetA0000(tds[8],item["a0000"]);
					SetTDtext(tds[9],item["dbwy"]);SetA0000(tds[9],item["a0000"]);
					SetTDtext(tds[10],item["rybz"]);SetA0000(tds[10],item["a0000"]);
/* 					SetTDtext(tds[12],item["dwbz"]);SetA0000(tds[12],item["a0000"]); */
					for(var j=4;j<=10;j++){
						SetNRcolor(tds[j]);
					}
					k=k-1;
				}
			}else{
				tr = $('<tr><td></td>'+
						'<td class="name"></td><td class="name"></td><td class="dbwy"></td><td class="bz"></td>'+
						'<td class="name"></td><td class="name"></td><td class="name"></td><td class="name"></td><td class="name"></td>'+
						'<td class="dbwy"></td><td class="bz"></td></tr>');
				tds= $("td", tr);
				SetTDtext(tds[0],item["a0215a"]);
				SetTDtext(tds[1],item["a0101"]);SetA0000(tds[1],item["a0000"]);
/* 				SetTDtext(tds[2],item["a0104"]); */
				SetTDtext(tds[2],item["a0107"]);SetA0000(tds[2],item["a0000"]);
				SetTDtext(tds[3],item["dbwy"]);SetA0000(tds[3],item["a0000"]);
				SetTDtext(tds[4],item["rybz"]);SetA0000(tds[4],item["a0000"]);
/*  				SetTDtext(tds[5],item["dwbz"]); SetA0000(tds[5],item["a0000"]); */
 				
				SetTDtext(tds[5],item["a0101"]);SetA0000(tds[5],item["a0000"]);
				SetTDtext(tds[6],item["a0107"]);SetA0000(tds[6],item["a0000"]);
/* 				SetTDtext(tds[7],item["a0104"]); */
				SetTDtext(tds[7],item["a0192a"]);SetA0000(tds[7],item["a0000"]);
				SetTDtext(tds[8],item["qrzxl"]);SetA0000(tds[8],item["a0000"]);
				SetTDtext(tds[9],item["zgxl"]);SetA0000(tds[9],item["a0000"]);
				SetTDtext(tds[10],item["dbwy"]);SetA0000(tds[10],item["a0000"]);
				SetTDtext(tds[11],item["rybz"]);SetA0000(tds[11],item["a0000"]);
/* 				SetTDtext(tds[13],item["dwbz"]);SetA0000(tds[13],item["a0000"]); */
				if(fxyp07==-1){
					for(var j=0;j<=11;j++){
						SetNMcolor(tds[j]);
					}
					for(var j=5;j<=11;j++){
						SetTDtext(tds[j],"");
						SetA0000(tds[j],"");
					}
				}else if(fxyp07==1){
					for(var j=5;j<=11;j++){
						SetNRcolor(tds[j]);
					}
					if(bdfxyp00!=null && bdfxyp00!=""){
						$.each(data1, function (m,item1) {
							if(item1["fxyp00"]==bdfxyp00){
								SetTDtext(tds[1],item1["a0101"]);SetA0000(tds[1],item1["a0000"]);
/* 								SetTDtext(tds[2],item1["a0104"]); */
								SetTDtext(tds[2],item1["a0107"]);SetA0000(tds[2],item1["a0000"]);
								SetTDtext(tds[3],item1["dbwy"]);SetA0000(tds[3],item1["a0000"]);
								SetTDtext(tds[4],item1["rybz"]);SetA0000(tds[4],item1["a0000"]);
/* 								SetTDtext(tds[5],item1["dwbz"]);SetA0000(tds[5],item1["a0000"]); */
								if(item1["fxyp07"]==-1){
									for(var j=1;j<=4;j++){
										SetNMcolor(tds[j]);
									}
								}
							}	
						});
					}else{
						for(var j=1;j<=4;j++){
							SetTDtext(tds[j],"");
						}
					}
				}
			}
			
		}
		
		$('#coordTable').append(tr);
	});
}






function AddQx(data,data1,totallength,length,line){
	var k=0;
	$.each(data, function (i,item) {
		var num=item["peoplenum"];
		var tr;
		var bdfxyp00=item["bdfxyp00"];
		var fxyp07=item["fxyp07"];
		var tds;
		//区县的第一个机构性质
		if(line==0){
			//机构性质下的第一行
			if(i==0){
				//拟任有多个人员
				if(num>0){	
					tr = $('<tr><td  class="unit"  rowspan='+totallength+' ></td><td   rowspan='+length+'></td><td rowspan='+num+'></td>'+
							'<td class="name"></td><td class="name"></td><td class="dbwy"></td><td class="bz"></td>'+
							'<td class="name"></td><td class="name"></td><td class="name"></td><td class="name"></td><td class="name"></td>'+
							'<td class="dbwy"></td><td class="bz"></td><td class="dwbz" rowspan='+totallength+' ></td></tr>');
					tds= $("td", tr);
					SetTDtext(tds[0],item["b0101"]);SetB0111(tds[0],item["b0111p"]);SetB0111(tds[14],item["b0111p"]);
					SetTDtext(tds[1],item["zrrx"]);
					SetTDtext(tds[2],item["a0215a"]);
					if(item["zwqc01"]>1){
						SetTDtext(tds[2],item["a0215a"]+'('+item["zwqc01"]+')');
					}
					if(bdfxyp00!=null && bdfxyp00!=""){
						$.each(data1, function (m,item1) {
							if(item1["fxyp00"]==bdfxyp00){
								SetTDtext(tds[3],item1["a0101"]);SetA0000(tds[3],item1["a0000"]);
/* 								SetTDtext(tds[4],item1["a0104"]); */
								SetTDtext(tds[4],item1["a0107"]);SetA0000(tds[4],item1["a0000"]);
								SetTDtext(tds[5],item1["dbwy"]);SetA0000(tds[5],item1["a0000"]);
								SetTDtext(tds[6],item1["rybz"]);SetA0000(tds[6],item1["a0000"]);
/* 								SetTDtext(tds[7],item1["dwbz"]);SetA0000(tds[7],item1["a0000"]); */
								if(item1["fxyp07"]==-1){
									for(var j=3;j<=6;j++){
										SetNMcolor(tds[j]);
									}
								}
							}			
						});
					}else{
						for(var j=3;j<=6;j++){
							SetTDtext(tds[j],"");
						}
					}
					SetTDtext(tds[7],item["a0101"]);SetA0000(tds[7],item["a0000"]);
					SetTDtext(tds[8],item["a0107"]);SetA0000(tds[8],item["a0000"]);
/* 					SetTDtext(tds[11],item["a0104"]); */
					SetTDtext(tds[9],item["a0192a"]);SetA0000(tds[9],item["a0000"]);
					SetTDtext(tds[10],item["qrzxl"]);SetA0000(tds[10],item["a0000"]);
					SetTDtext(tds[11],item["zgxl"]);SetA0000(tds[11],item["a0000"]);
					SetTDtext(tds[12],item["dbwy"]);SetA0000(tds[12],item["a0000"]);
					SetTDtext(tds[13],item["rybz"]);SetA0000(tds[13],item["a0000"]);
/* 					SetTDtext(tds[15],item["dwbz"]);SetA0000(tds[15],item["a0000"]); */
					for(var j=7;j<=13;j++){
						SetNRcolor(tds[j]);
					}
					k=num-1;
				//无多个人员	
				}else{
					tr = $('<tr><td class="unit"  rowspan='+totallength+' ></td><td  rowspan='+length+'></td><td></td>'+
							'<td class="name"></td><td class="name"></td><td class="dbwy"></td><td class="bz"></td>'+
							'<td class="name"></td><td class="name"></td><td class="name"></td><td class="name"></td><td class="name"></td>'+
							'<td class="dbwy"></td><td class="bz"></td><td class="dwbz" rowspan='+totallength+'></td></tr>');
					tds= $("td", tr);
					SetTDtext(tds[0],item["b0101"]);SetB0111(tds[0],item["b0111p"]);SetB0111(tds[14],item["b0111p"]);
					SetTDtext(tds[1],item["zrrx"]);
					SetTDtext(tds[2],item["a0215a"]);
					SetTDtext(tds[3],item["a0101"]);SetA0000(tds[3],item["a0000"]);
/* 					SetTDtext(tds[4],item["a0104"]); */
					SetTDtext(tds[4],item["a0107"]);SetA0000(tds[4],item["a0000"]);
					SetTDtext(tds[5],item["dbwy"]);SetA0000(tds[5],item["a0000"]);
					SetTDtext(tds[6],item["rybz"]);SetA0000(tds[6],item["a0000"]);
/* 					SetTDtext(tds[7],item["dwbz"]);SetA0000(tds[7],item["a0000"]); */
					
					SetTDtext(tds[7],item["a0101"]);SetA0000(tds[7],item["a0000"]);
					SetTDtext(tds[8],item["a0107"]);SetA0000(tds[8],item["a0000"]);
/* 					SetTDtext(tds[11],item["a0104"]); */
					SetTDtext(tds[9],item["a0192a"]);SetA0000(tds[9],item["a0000"]);
					SetTDtext(tds[10],item["qrzxl"]);SetA0000(tds[10],item["a0000"]);
					SetTDtext(tds[11],item["zgxl"]);SetA0000(tds[11],item["a0000"]);
					SetTDtext(tds[12],item["dbwy"]);SetA0000(tds[12],item["a0000"]);
					SetTDtext(tds[13],item["rybz"]);SetA0000(tds[13],item["a0000"]);
/* 					SetTDtext(tds[15],item["dwbz"]);SetA0000(tds[15],item["a0000"]); */
					if(fxyp07==-1){
						for(var j=2;j<=13;j++){
							SetNMcolor(tds[j]);
						}
						for(var j=7;j<=13;j++){
							SetTDtext(tds[j],"");
							SetA0000(tds[j],"");
						}
					}else if(fxyp07==1){
						for(var j=7;j<=13;j++){
							SetNRcolor(tds[j]);
						}
						if(bdfxyp00!=null && bdfxyp00!=""){
							$.each(data1, function (m,item1) {
								if(item1["fxyp00"]==bdfxyp00){
									SetTDtext(tds[3],item1["a0101"]);SetA0000(tds[3],item1["a0000"]);
/* 									SetTDtext(tds[4],item1["a0104"]); */
									SetTDtext(tds[4],item1["a0107"]);SetA0000(tds[4],item1["a0000"]);
									SetTDtext(tds[5],item1["dbwy"]);SetA0000(tds[5],item1["a0000"]);
									SetTDtext(tds[6],item1["rybz"]);SetA0000(tds[6],item1["a0000"]);
/* 									SetTDtext(tds[7],item1["dwbz"]);SetA0000(tds[7],item1["a0000"]); */
									if(item1["fxyp07"]==-1){
										for(var j=3;j<=6;j++){
											SetNMcolor(tds[j]);
										}
									}
								}		
							});
						}else{
							for(var j=3;j<=6;j++){
								SetTDtext(tds[j],"");
							}
						}
					}
				}
			//机构性质下不是第一行
			}else{
				//拟任有多个人员
				if(num>0){
					//多个人员第一行
					if(k==0){
						tr = $('<tr><td rowspan='+num+'></td>'+
								'<td class="name"></td><td class="name"></td><td class="dbwy"></td><td class="bz"></td>'+
								'<td class="name"></td><td class="name"></td><td class="name"></td><td class="name"></td><td class="name"></td>'+
								'<td class="dbwy"></td><td class="bz"></td></tr>');
						tds= $("td", tr);
						SetTDtext(tds[0],item["a0215a"]);
						if(item["zwqc01"]>1){
							SetTDtext(tds[0],item["a0215a"]+'('+item["zwqc01"]+')');
						}
						if(bdfxyp00!=null && bdfxyp00!=""){
							$.each(data1, function (m,item1) {
								if(item1["fxyp00"]==bdfxyp00){
									SetTDtext(tds[1],item1["a0101"]);SetA0000(tds[1],item1["a0000"]);
/* 									SetTDtext(tds[2],item1["a0104"]); */
									SetTDtext(tds[2],item1["a0107"]);SetA0000(tds[2],item1["a0000"]);
									SetTDtext(tds[3],item1["dbwy"]);SetA0000(tds[3],item1["a0000"]);
									SetTDtext(tds[4],item1["rybz"]);SetA0000(tds[4],item1["a0000"]);
/* 									SetTDtext(tds[5],item1["dwbz"]);SetA0000(tds[5],item1["a0000"]); */
									if(item1["fxyp07"]==-1){
										for(var j=1;j<=4;j++){
											SetNMcolor(tds[j]);
										}
									}	
								}							
							});
						}else{
							for(var j=1;j<=4;j++){
								SetTDtext(tds[j],"");
							}
						}
						SetTDtext(tds[5],item["a0101"]);SetA0000(tds[5],item["a0000"]);
						SetTDtext(tds[6],item["a0107"]);SetA0000(tds[6],item["a0000"]);
/* 						SetTDtext(tds[9],item["a0104"]); */
						SetTDtext(tds[7],item["a0192a"]);SetA0000(tds[7],item["a0000"]);
						SetTDtext(tds[8],item["qrzxl"]);SetA0000(tds[8],item["a0000"]);
						SetTDtext(tds[9],item["zgxl"]);SetA0000(tds[9],item["a0000"]);
						SetTDtext(tds[10],item["dbwy"]);SetA0000(tds[10],item["a0000"]);
						SetTDtext(tds[11],item["rybz"]);SetA0000(tds[11],item["a0000"]);
						SetTDtext(tds[12],item["dwbz"]);SetA0000(tds[12],item["a0000"]);
						for(var j=5;j<=12;j++){
							SetNRcolor(tds[j]);
						}
						k=num-1;
					}else{
						tr = $('<tr>'+
								'<td class="name"></td><td class="name"></td><td class="dbwy"></td><td class="bz"></td>'+
								'<td class="name"></td><td class="name"></td><td class="name"></td><td class="name"></td><td class="name"></td>'+
								'<td class="dbwy"></td><td class="bz"></td></tr>');
						tds= $("td", tr);
						for(var j=0;j<=3;j++){
							SetTDtext(tds[j],"");
						}
						SetTDtext(tds[4],item["a0101"]);SetA0000(tds[4],item["a0000"]);
						SetTDtext(tds[5],item["a0107"]);SetA0000(tds[5],item["a0000"]);
/* 						SetTDtext(tds[8],item["a0104"]); */
						SetTDtext(tds[6],item["a0192a"]);SetA0000(tds[6],item["a0000"]);
						SetTDtext(tds[7],item["qrzxl"]);SetA0000(tds[7],item["a0000"]);
						SetTDtext(tds[8],item["zgxl"]);SetA0000(tds[8],item["a0000"]);
						SetTDtext(tds[9],item["dbwy"]);SetA0000(tds[9],item["a0000"]);
						SetTDtext(tds[10],item["rybz"]);SetA0000(tds[10],item["a0000"]);
/* 						SetTDtext(tds[11],item["dwbz"]);SetA0000(tds[12],item["a0000"]); */
						for(var j=4;j<=10;j++){
							SetNRcolor(tds[j]);
						}
						k=k-1;
					}
				//无多个人员	
				}else{
					tr = $('<tr><td></td>'+
							'<td class="name"></td><td class="name"></td><td class="dbwy"></td><td class="bz"></td>'+
							'<td class="name"></td><td class="name"></td><td class="name"></td><td class="name"></td><td class="name"></td>'+
							'<td class="dbwy"></td><td class="bz"></td></tr>');
					tds= $("td", tr);
					SetTDtext(tds[0],item["a0215a"]);
					SetTDtext(tds[1],item["a0101"]);SetA0000(tds[1],item["a0000"]);
/* 					SetTDtext(tds[2],item["a0104"]); */
					SetTDtext(tds[2],item["a0107"]);SetA0000(tds[2],item["a0000"]);
					SetTDtext(tds[3],item["dbwy"]);SetA0000(tds[3],item["a0000"]);
					SetTDtext(tds[4],item["rybz"]);SetA0000(tds[4],item["a0000"]);
/* 					SetTDtext(tds[5],item["dwbz"]);SetA0000(tds[5],item["a0000"]); */
					
					SetTDtext(tds[5],item["a0101"]);SetA0000(tds[5],item["a0000"]);
					SetTDtext(tds[6],item["a0107"]);SetA0000(tds[6],item["a0000"]);
/* 					SetTDtext(tds[9],item["a0104"]); */
					SetTDtext(tds[7],item["a0192a"]);SetA0000(tds[7],item["a0000"]);
					SetTDtext(tds[8],item["qrzxl"]);SetA0000(tds[8],item["a0000"]);
					SetTDtext(tds[9],item["zgxl"]);SetA0000(tds[9],item["a0000"]);
					SetTDtext(tds[10],item["dbwy"]);SetA0000(tds[10],item["a0000"]);
					SetTDtext(tds[11],item["rybz"]);SetA0000(tds[11],item["a0000"]);
/* 					SetTDtext(tds[12],item["dwbz"]);SetA0000(tds[13],item["a0000"]); */
					if(fxyp07==-1){
						for(var j=0;j<=11;j++){
							SetNMcolor(tds[j]);
						}
						for(var j=5;j<=11;j++){
							SetTDtext(tds[j],"");
							SetA0000(tds[j],"");
						}
					}else if(fxyp07==1){
						for(var j=5;j<=11;j++){
							SetNRcolor(tds[j]);
						}
						if(bdfxyp00!=null && bdfxyp00!=""){
							$.each(data1, function (m,item1) {
								if(item1["fxyp00"]==bdfxyp00){
									SetTDtext(tds[1],item1["a0101"]);SetA0000(tds[1],item1["a0000"]);
/* 									SetTDtext(tds[2],item1["a0104"]); */
									SetTDtext(tds[2],item1["a0107"]);SetA0000(tds[2],item1["a0000"]);
									SetTDtext(tds[3],item1["dbwy"]);SetA0000(tds[3],item1["a0000"]);
									SetTDtext(tds[4],item1["rybz"]);SetA0000(tds[4],item1["a0000"]);
/* 									SetTDtext(tds[5],item1["dwbz"]);SetA0000(tds[5],item["a0000"]); */
									if(item1["fxyp07"]==-1){
										for(var j=1;j<=4;j++){
											SetNMcolor(tds[j]);
										}
									}
								}
								
								
							});
						}else{
							for(var j=1;j<=4;j++){
								SetTDtext(tds[j],"");
							}
						}
					}
				}
			}
		//区县性质非第一个机构性质
		}else{
			//机构性质的第一行
			if(i==0){
				//拟任有多个人员
				if(num>0){
					//=====================================================
					tr = $('<tr><td  rowspan='+length+'></td><td rowspan='+num+'></td>'+
							'<td class="name"></td><td class="name"></td><td class="dbwy"></td><td class="bz"></td>'+
							'<td class="name"></td><td class="name"></td><td class="name"></td><td class="name"></td><td class="name"></td>'+
							'<td class="dbwy"></td><td class="bz"></td></tr>');
					tds= $("td", tr);
					SetTDtext(tds[0],item["zrrx"]);
					SetTDtext(tds[1],item["a0215a"]);
					if(item["zwqc01"]>1){
						SetTDtext(tds[1],item["a0215a"]+'('+item["zwqc01"]+')');
					}
					if(bdfxyp00!=null && bdfxyp00!=""){
						$.each(data1, function (m,item1) {
							if(item1["fxyp00"]==bdfxyp00){
								SetTDtext(tds[2],item1["a0101"]);SetA0000(tds[2],item1["a0000"]);
/* 								SetTDtext(tds[3],item1["a0104"]); */
								SetTDtext(tds[3],item1["a0107"]);SetA0000(tds[3],item1["a0000"]);
								SetTDtext(tds[4],item1["dbwy"]);SetA0000(tds[4],item1["a0000"]);
								SetTDtext(tds[5],item1["rybz"]);SetA0000(tds[5],item1["a0000"]);
/* 								SetTDtext(tds[6],item1["dwbz"]);SetA0000(tds[6],item1["a0000"]); */
								if(item1["fxyp07"]==-1){
									for(var j=2;j<=5;j++){
										SetNMcolor(tds[j]);
									}
								}
							}
							
							
						});
					}else{
						for(var j=2;j<=5;j++){
							SetTDtext(tds[j],"");
						}
					}
					SetTDtext(tds[6],item["a0101"]);SetA0000(tds[6],item["a0000"]);
					SetTDtext(tds[7],item["a0107"]);SetA0000(tds[7],item["a0000"]);
/* 					SetTDtext(tds[10],item["a0104"]); */
					SetTDtext(tds[8],item["a0192a"]);SetA0000(tds[8],item["a0000"]);
					SetTDtext(tds[9],item["qrzxl"]);SetA0000(tds[9],item["a0000"]);
					SetTDtext(tds[10],item["zgxl"]);SetA0000(tds[10],item["a0000"]);
					SetTDtext(tds[11],item["dbwy"]);SetA0000(tds[11],item["a0000"]);
					SetTDtext(tds[12],item["rybz"]);SetA0000(tds[12],item["a0000"]);
/* 					SetTDtext(tds[13],item["dwbz"]);SetA0000(tds[14],item["a0000"]); */
					for(var j=6;j<=12;j++){
						SetNRcolor(tds[j]);
					}
					k=num-1;
					//=====================================================
				//无多个人员
				}else{
					//=====================================================
					tr = $('<tr><td  rowspan='+length+'></td><td ></td>'+
							'<td class="name"></td><td class="name"></td><td class="dbwy"></td><td class="bz"></td>'+
							'<td class="name"></td><td class="name"></td><td class="name"></td><td class="name"></td><td class="name"></td>'+
							'<td class="dbwy"></td><td class="bz"></td></tr>');
					tds= $("td", tr);
					SetTDtext(tds[0],item["zrrx"]);
					SetTDtext(tds[1],item["a0215a"]);
					SetTDtext(tds[2],item["a0101"]);SetA0000(tds[2],item["a0000"]);
/* 					SetTDtext(tds[3],item["a0104"]); */
					SetTDtext(tds[3],item["a0107"]);SetA0000(tds[3],item["a0000"]);
					SetTDtext(tds[4],item["dbwy"]);SetA0000(tds[4],item["a0000"]);
					SetTDtext(tds[5],item["rybz"]);SetA0000(tds[5],item["a0000"]);
/* 					SetTDtext(tds[6],item["dwbz"]);SetA0000(tds[6],item["a0000"]); */
					
					SetTDtext(tds[6],item["a0101"]);SetA0000(tds[6],item["a0000"]);
					SetTDtext(tds[7],item["a0107"]);SetA0000(tds[7],item["a0000"]);
/* 					SetTDtext(tds[10],item["a0104"]); */
					SetTDtext(tds[8],item["a0192a"]);SetA0000(tds[8],item["a0000"]);
					SetTDtext(tds[9],item["qrzxl"]);SetA0000(tds[9],item["a0000"]);
					SetTDtext(tds[10],item["zgxl"]);SetA0000(tds[10],item["a0000"]);
					SetTDtext(tds[11],item["dbwy"]);SetA0000(tds[11],item["a0000"]);
					SetTDtext(tds[12],item["rybz"]);SetA0000(tds[12],item["a0000"]);
/* 					SetTDtext(tds[14],item["dwbz"]);SetA0000(tds[14],item["a0000"]); */
					if(fxyp07==-1){
						for(var j=1;j<=12;j++){
							SetNMcolor(tds[j]);
						}
						for(var j=6;j<=12;j++){
							SetTDtext(tds[j],"");
							SetA0000(tds[j],"");
						}
					}else if(fxyp07==1){
						for(var j=6;j<=12;j++){
							SetNRcolor(tds[j]);
						}
						if(bdfxyp00!=null && bdfxyp00!=""){
							$.each(data1, function (m,item1) {
								if(item1["fxyp00"]==bdfxyp00){
									SetTDtext(tds[2],item1["a0101"]);SetA0000(tds[2],item1["a0000"]);
/* 									SetTDtext(tds[3],item1["a0104"]); */
									SetTDtext(tds[3],item1["a0107"]);SetA0000(tds[3],item1["a0000"]);
									SetTDtext(tds[4],item1["dbwy"]);SetA0000(tds[4],item1["a0000"]);
									SetTDtext(tds[5],item1["rybz"]);SetA0000(tds[5],item1["a0000"]);
/* 									SetTDtext(tds[6],item1["dwbz"]);SetA0000(tds[6],item1["a0000"]); */
									if(item1["fxyp07"]==-1){
										for(var j=2;j<=5;j++){
											SetNMcolor(tds[j]);
										}
									}
								}
								
								
							});
						}else{
							for(var j=2;j<=5;j++){
								SetTDtext(tds[j],"");
							}
						}
					}
					//=====================================================
				}
			//机构性质不是第一行
			}else{
				//拟任多个人员
				if(num>0){
					//多个人员第一行
					if(k==0){
						//=====================================================
						tr = $('<tr><td rowspan='+num+'></td>'+
								'<td class="name"></td><td class="name"></td><td class="dbwy"></td><td class="bz"></td>'+
								'<td class="name"></td><td class="name"></td><td class="name"></td><td class="name"></td><td class="name"></td>'+
								'<td class="dbwy"></td><td class="bz"></td></tr>');
						tds= $("td", tr);
						SetTDtext(tds[0],item["a0215a"]);
						if(item["zwqc01"]>1){
							SetTDtext(tds[0],item["a0215a"]+'('+item["zwqc01"]+')');
						}
						if(bdfxyp00!=null && bdfxyp00!=""){
							$.each(data1, function (m,item1) {
								if(item1["fxyp00"]==bdfxyp00){
									SetTDtext(tds[1],item1["a0101"]);SetA0000(tds[1],item1["a0000"]);
/* 									SetTDtext(tds[2],item1["a0104"]); */
									SetTDtext(tds[2],item1["a0107"]);SetA0000(tds[2],item1["a0000"]);
									SetTDtext(tds[3],item1["dbwy"]);SetA0000(tds[3],item1["a0000"]);
									SetTDtext(tds[4],item1["rybz"]);SetA0000(tds[4],item1["a0000"]);
/* 									SetTDtext(tds[5],item1["dwbz"]);SetA0000(tds[5],item1["a0000"]); */
									if(item1["fxyp07"]==-1){
										for(var j=1;j<=4;j++){
											SetNMcolor(tds[j]);
										}
									}
								}
								
								
							});
						}else{
							for(var j=1;j<=4;j++){
								SetTDtext(tds[j],"");
							}
						}
						SetTDtext(tds[5],item["a0101"]);SetA0000(tds[5],item["a0000"]);
						SetTDtext(tds[6],item["a0107"]);SetA0000(tds[6],item["a0000"]);
/* 						SetTDtext(tds[9],item["a0104"]); */
						SetTDtext(tds[7],item["a0192a"]);SetA0000(tds[7],item["a0000"]);
						SetTDtext(tds[8],item["qrzxl"]);SetA0000(tds[8],item["a0000"]);
						SetTDtext(tds[9],item["zgxl"]);SetA0000(tds[9],item["a0000"]);
						SetTDtext(tds[10],item["dbwy"]);SetA0000(tds[10],item["a0000"]);
						SetTDtext(tds[11],item["rybz"]);SetA0000(tds[11],item["a0000"]);
/* 						SetTDtext(tds[13],item["dwbz"]);SetA0000(tds[13],item["a0000"]); */
						for(var j=5;j<=11;j++){
							SetNRcolor(tds[j]);
						}
						k=num-1;
						//=====================================================
					}else{
						//=====================================================
						tr = $('<tr>'+
								'<td class="name"></td><td class="name"></td><td class="dbwy"></td><td class="bz"></td>'+
								'<td class="name"></td><td class="name"></td><td class="name"></td><td class="name"></td><td class="name"></td>'+
								'<td class="dbwy"></td><td class="bz"></td></tr>');
						tds= $("td", tr);
						for(var j=0;j<=3;j++){
							SetTDtext(tds[j],"");
						}
						SetTDtext(tds[4],item["a0101"]);SetA0000(tds[4],item["a0000"]);
						SetTDtext(tds[5],item["a0107"]);SetA0000(tds[5],item["a0000"]);
/* 						SetTDtext(tds[8],item["a0104"]); */
						SetTDtext(tds[6],item["a0192a"]);SetA0000(tds[6],item["a0000"]);
						SetTDtext(tds[7],item["qrzxl"]);SetA0000(tds[7],item["a0000"]);
						SetTDtext(tds[8],item["zgxl"]);SetA0000(tds[8],item["a0000"]);
						SetTDtext(tds[9],item["dbwy"]);SetA0000(tds[9],item["a0000"]);
						SetTDtext(tds[10],item["rybz"]);SetA0000(tds[10],item["a0000"]);
/* 						SetTDtext(tds[11],item["dwbz"]);SetA0000(tds[12],item["a0000"]); */
						for(var j=4;j<=10;j++){
							SetNRcolor(tds[j]);
						}
						k=k-1;
					}
					//=====================================================
				//无多人员
				}else{
					//=====================================================
					tr = $('<tr><td></td>'+
							'<td class="name"></td><td class="name"></td><td class="dbwy"></td><td class="bz"></td>'+
							'<td class="name"></td><td class="name"></td><td class="name"></td><td class="name"></td><td class="name"></td>'+
							'<td class="dbwy"></td><td class="bz"></td></tr>');
					tds= $("td", tr);
					SetTDtext(tds[0],item["a0215a"]);
					SetTDtext(tds[1],item["a0101"]);SetA0000(tds[1],item["a0000"]);
/* 					SetTDtext(tds[2],item["a0104"]); */
					SetTDtext(tds[2],item["a0107"]);SetA0000(tds[2],item["a0000"]);
					SetTDtext(tds[3],item["dbwy"]);SetA0000(tds[3],item["a0000"]);
					SetTDtext(tds[4],item["rybz"]);SetA0000(tds[4],item["a0000"]);
/* 					SetTDtext(tds[5],item["dwbz"]);SetA0000(tds[5],item["a0000"]); */
					SetTDtext(tds[5],item["a0101"]);SetA0000(tds[5],item["a0000"]);
					SetTDtext(tds[6],item["a0107"]);SetA0000(tds[6],item["a0000"]);
/* 					SetTDtext(tds[9],item["a0104"]); */
					SetTDtext(tds[7],item["a0192a"]);SetA0000(tds[7],item["a0000"]);
					SetTDtext(tds[8],item["qrzxl"]);SetA0000(tds[8],item["a0000"]);
					SetTDtext(tds[9],item["zgxl"]);SetA0000(tds[9],item["a0000"]);
					SetTDtext(tds[10],item["dbwy"]);SetA0000(tds[10],item["a0000"]);
					SetTDtext(tds[11],item["rybz"]);SetA0000(tds[11],item["a0000"]);
/* 					SetTDtext(tds[12],item["dwbz"]);SetA0000(tds[13],item["a0000"]); */
					if(fxyp07==-1){
						for(var j=0;j<=11;j++){
							SetNMcolor(tds[j]);
						}
						for(var j=5;j<=11;j++){
							SetTDtext(tds[j],"");
							SetA0000(tds[j],"");
						}
					}else if(fxyp07==1){
						for(var j=5;j<=11;j++){
							SetNRcolor(tds[j]);
						}
						if(bdfxyp00!=null && bdfxyp00!=""){
							$.each(data1, function (m,item1) {
								if(item1["fxyp00"]==bdfxyp00){
									SetTDtext(tds[1],item1["a0101"]);SetA0000(tds[1],item1["a0000"]);
/* 									SetTDtext(tds[2],item1["a0104"]); */
									SetTDtext(tds[2],item1["a0107"]);SetA0000(tds[2],item1["a0000"]);
									SetTDtext(tds[3],item1["dbwy"]);SetA0000(tds[3],item1["a0000"]);
									SetTDtext(tds[4],item1["rybz"]);SetA0000(tds[4],item1["a0000"]);
/* 									SetTDtext(tds[5],item1["dwbz"]);SetA0000(tds[5],item1["a0000"]); */
									if(item1["fxyp07"]==-1){
										for(var j=1;j<=4;j++){
											SetNMcolor(tds[j]);
										}
									}
								}
								
								
							});
						}else{
							for(var j=1;j<=4;j++){
								SetTDtext(tds[j],"");
							}
						}
					}
					//=====================================================
				}
			}
		}
		
		
		
		
		$('#coordTable').append(tr);
	});
}

function showorhidedbwy(){
	var changedbwy=document.getElementById('changedbwy').value;
	if(changedbwy==1){
		$('.now').attr("colSpan",3); 
		$('.tp').attr("colSpan",7); 
		$('.dbwytitle').hide();
		$('.dbwy').hide();
		document.getElementById('showdbwy').innerHTML='显示代表委员';
		document.getElementById('changedbwy').value=0;
	}else if(changedbwy==0){
		$('.now').attr("colSpan",4); 
		$('.tp').attr("colSpan",8);
		$('.dbwytitle').show();
		$('.dbwy').show();	
		document.getElementById('showdbwy').innerHTML='隐藏代表委员';
		document.getElementById('changedbwy').value=1;
	}	
}

/* function test(){
	 $('#coordTable tr').each(function(i){     
		if(i==5){
			var tr;
			tr = $('<td>啊啊啊啊啊啊啊啊啊</td>');
			$(this).append(tr);
		}
	 });
} */

/* function mntp00callback(mntp00add){
	document.getElementById('mntp00add').value=mntp00add;
	$h.getTopParent().Ext.getCmp('MNTPlist').close();
	

	
	var widthbefore=$("#coordTable").width();
	$("#coordTable").css('width',widthbefore+1233);
	
	$('#coordTable tr').each(function(i){     
		if(i==0){
			var tr;
			tr = $('<th rowspan="2" style="width: 137">拟任职务</th><th class="now" colspan="5">现状</th><th class="tp" colspan="8">调配方案</th>');
			$(this).append(tr);
		}else if(i==1){
			var tr;
			tr = $('<th style="width: 68.5">姓名</th>'+
				    '<th style="width: 68.5">出生年月</th>'+
				    '<th style="width: 68.5" class="dbwytitle">代表委员</th>'+
				    '<th style="width: 95.9" class="th6">调配意见</th>'+
				    '<th style="width: 68.5">备注</th>'+
				    '<th style="width: 68.5">姓名</th>'+
				    '<th style="width: 68.5">出生年月</th>'+
				    '<th style="width: 68.5">全日制学历</th>'+
				    '<th style="width: 68.5">最高学历</th>'+
				    '<th style="width: 137">任现工作单位及职务</th>'+
				    '<th style="width: 109.6" class="dbwytitle">代表委员</th>'+
				    '<th style="width: 95.9">调配意见</th>'+
				    '<th style="width: 68.5">备注</th>")');
			$(this).append(tr);
		}
	 });
} */
function dwxxadd(b0111,b01id,sb){
	$(".dwbz").each(function(){
		var b0111s=$(this).attr("b0111");
		if(b0111s==b0111){
			$(this).html(sb);
			$(this).attr("b01id",b01id);
		}
	});
	
}


function SetTDtext(td,v) {
	  $(td).html((v==""||v==null||v=="null"||v=="0"||v=="-1"||v=="-2"||v=="-3"||v=="-4")?" ":v.replace(/\n/g,"<br/>"));
}
function dwaddclass(td,v) {
	 $(td).addClass(v);
}
function SetNMcolor(td){
	$(td).css("background","#CAFFFF");
}
function SetNRcolor(td){
	$(td).css("background","#FFC1E0");
}
function SetA0000(td,v){
	$(td).attr("a0000",v);
}
function SetB0111(td,v){
	$(td).attr("b0111",v);
}

function expExcel(){
	var dataArray = [];
	var tr = $("#coordTable tr");
	$.each(tr,function (i, item){
		var tdArray = [];
	    var th = $("th",$(this));  
	    $.each(th,function (ti, titem){ 
	    if($(this).css("display")!="none"){
	      tdArray.push($(this).text());
	    }
	    });
	    if(tdArray.length>0){
	       dataArray.push(tdArray);
	    }
	});
	$.each(tr,function (i, item){
		var tdArray = [];
	    var td = $("td",$(this));  
	    $.each(td,function (ti, titem){
		if($(this).css("display")!="none"){
			 tdArray.push($(this).text());
		}
	    });
	    if(tdArray.length>0){
	       dataArray.push(tdArray);
	    }
	});
	colorArray=color();
	spansArray=spans();
	  if(dataArray.length>0){
		  ajaxSubmit('expExcelnew',{"dataArray":Ext.encode(dataArray),"spansArray":Ext.encode(spansArray),"colorArray":Ext.encode(colorArray),"excelname":"调配情况（岗位）"});
		}
}
function spans(){
	var allmessage = [];
	var tableObj = document.getElementById('coordTable');
	for (var i = 0; i < tableObj.rows.length; i++) {    //遍历Table的所有行
		var messages2=[];
	    for(var j=0;j<tableObj.rows[i].cells.length;j++){  //遍历table第i行的格子
	    	var messages = [];
	        var rowspans=tableObj.rows[i].cells[j].getAttribute("rowspan");//多少行
	        var colspans=tableObj.rows[i].cells[j].getAttribute("colspan");//多少列
	        var displays=tableObj.rows[i].cells[j].style.display;//多少列
	        if(displays!="none"){
	        if(rowspans==null){
	          messages.push(1);
	        }else{
	          messages.push(rowspans);
	        }
	        if(colspans==null){
	          messages.push(1);
	        }else{
	          messages.push(colspans);
	        }
	        if(messages.length>0){
	          messages2.push(messages);
	        }
	        }
	    }
		if(messages2.length>0){
	        allmessage.push(messages2);
	    }      
	} 
	return allmessage;
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



function rybd(){
	var mntp00=document.getElementById('mntp00').value;
	$h.openWin('GWrybd','pages.gwdz.GWrybd','人员比对',1500,731,null,'<%= request.getContextPath() %>',null,
			{mntp00:mntp00},true);
}

function fabd(){
	var mntp00=document.getElementById('mntp00').value;
	$h.openWin('MNTPlist','pages.gwdz.MNTPlist','对比方案列表',600,470,null,'<%= request.getContextPath() %>',null,
			{mntp00:mntp00},true);
}



function color(){
	 var colorArray = [];
	 var tableObj = document.getElementById('coordTable');
	 for (var i = 0; i < tableObj.rows.length; i++) {    //遍历Table的所有Row
	    var colorlist=[];
	    for(var j=0;j<tableObj.rows[i].cells.length;j++){  //遍历table第i行的格子
	      var bcolor=tableObj.rows[i].cells[j].style.backgroundColor;
	      var displays=tableObj.rows[i].cells[j].style.display;//多少列
	      if(displays!="none"){
	      if(bcolor=="rgb(202, 255, 255)"||bcolor=="#caffff") {
	        colorlist.push("B");
	      }else if(bcolor=="rgb(255, 193, 224)"||bcolor=="#ffc1e0"){
	        colorlist.push("R");
	      }else if(bcolor=="rgb(202, 232, 234)"||bcolor=="#CAE8EA"){
	        colorlist.push("T");
	      }else{
	        colorlist.push("W");
	      }
	    }
	    }
	    if(colorlist.length>0){
	      colorArray.push(colorlist);
	     }
	  } 
	   
	 return colorArray;
}
 
 function openBDHZ(){//变动汇总
	 var mntp00=document.getElementById('mntp00').value;
	 var newWin_ = $h.getTopParent().Ext.getCmp('BDHZView');
	  if(newWin_){
	    newWin_.toFront();
	    return;
	  }
	$h.openWin('BDHZView','pages.fxyp.BDHZ','变动汇总',1400,1200,'','<%=request.getContextPath()%>',null,{mntp00:mntp00},true);
	//$h.getTopParent().Ext.getCmp('BDHZView').on('close',function(){$h.getTopParent().Ext.getCmp('BDHZView').toFront()})
}
 var g_contextpath = '<%= request.getContextPath() %>';
 function bindRMB(){
	  $('.name').bind('click',function(){
		 var a0000=$(this).attr("a0000")
		 if(a0000==null || a0000==''){
			 return;
		 }
		  $h.openPageModeWin('openTPRmb','pages.fxyp.TPRYXXZS','人员信息',1000,800,{a0000:a0000,location:'1'},'<%=request.getContextPath()%>'); 
		});
	 
	 
	 $('.dbwy').bind('click',function(){
		 var a0000=$(this).attr("a0000")
		 if(a0000==null || a0000==''){
			 return;
		 }
		 $h.openPageModeWin('openTPRmb','pages.fxyp.TPRYXXZS','人员信息',1000,800,{a0000:a0000,location:'3'},'<%=request.getContextPath()%>'); 
		});
	 
	 $('.bz').bind('click',function(){
		 var a0000=$(this).attr("a0000")
		 if(a0000==null || a0000==''){
			 return;
		 }
		 $h.openPageModeWin('openTPRmb','pages.fxyp.TPRYXXZS','人员信息',1000,800,{a0000:a0000,location:'4'},'<%=request.getContextPath()%>'); 
		});
	 
	 $('.dwbz').bind('click',function(){
		
		 var mntp00=document.getElementById('mntp00').value;
		 var b01id=$(this).attr("b01id");
		 var b0111=$(this).attr("b0111");
		 $('#b01id').val(b01id);
		 $('#b0111').val(b0111);
		 if(b0111.substring(0,11)=='001.001.004' && b0111.length==15 ){
			 $('#mntp05').val('2');
		 }else{
			 $('#mntp05').val('1');
		 } 
		 if(b01id==null || b01id==''){
			 return;
		 }
		 var url = g_contextpath + "/pages/fxyp/StructuralAnalysis.jsp?mntp00="+mntp00+"&b01id="+b01id;
		$h.showWindowWithSrc("structuralAnalysis",url,"图标分析", 1400, 700,null,{closeAction:'close'},true);
	 });

	 $('.unit').bind('click',function(){
		 var b0111=$(this).attr("b0111");
		 var b0101=$(this).text();
		 if(b0111==null || b0111==''){
			 return;
		 }
		 var ip='<%=GlobalNames.sysConfig.get("XBDJ_IP")%>';
		 var port='<%=GlobalNames.sysConfig.get("XBDJ_MAINPORT")%>';
		 var url = "http://"+ip+":"+port+"/ngbdp/team/?code="+b0111+"&name="+b0101+"&hasback=false"; 
		$h.showWindowWithSrc("BZFX",url,"班子分析", 1500, 1200,null,{closeAction:'close'},true,true);
		}); 
	 
	/*  $('.dwbz').bind('click',function(){
		 var b0111=$(this).attr("b0111");
		 alert(b0111);
		}); 
	  */
	 
	 $('.name,.bz,.dbwy,.unit,.dwbz').bind('mouseover',function(){
		  var b0111=$(this).attr("b0111");
		  var a0000=$(this).attr("a0000");
		  var html=$(this).innerHTML;
		  if(b0111==null  && html==null){
			  if(a0000==null || a0000==''){
				  $(this).css('cursor','default');
				  return;
			  }	 
		  }
		  $(this).addClass("hovercolor"); 
	  });
	  $('.name,.dbwy,.bz,.unit,.dwbz').bind('mouseout',function(){
		  $(this).removeClass("hovercolor"); 
	  });
	  
	  var changedbwy=document.getElementById('changedbwy').value;
		if(changedbwy==0){
			$('.dbwy').hide();
		}else if(changedbwy==1){
			$('.dbwy').show();
		}
	}
</script>

<script type="text/javascript">
function aaaa(){
	var subwid = $h.getTopParent().document.getElementById('iframe_BZFX').contentWindow;
	console.log(subwid)
	return subwid
}
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
