<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<%@include file="/comOpenWinInit.jsp" %>
<script type="text/javascript">
	function save(param){
		$h.openWin('SaveStatistics','pages.statisticalanalysis.simpleanalysis.SaveStatistics','保存自定义二维统计',400,570,param,'<%=request.getContextPath()%>');
	}
	function tjfx(param){
		$h.openWin('TwoDStatisticsShow','pages.statisticalanalysis.simpleanalysis.TwoDStatisticsShow','自定义二维统计图',1000,650,param,'<%=request.getContextPath()%>');
	} 
	function tjfx2(param){
		var title = param.split('&')[1];
		var data = param.split('&')[0];
		$h.openWin('TwoDStatisticsShow','pages.statisticalanalysis.simpleanalysis.TwoDStatisticsShow',title+'二维统计图',1000,650,data,'<%=request.getContextPath()%>');
	} 
	function tran_set_change(){
		radow.doEvent('tran_set_change');
	}
	function vert_set_change(){
		radow.doEvent('vert_set_change');
	}
	function tran_item_change(obj){
		var num;
		if(obj.id.length == 11){
			num = obj.id.substring(4,5);
		}else{
			num = obj.id.substring(4,6);
		}
		radow.doEvent('tran_item_change',num);
	}
	function vert_item_change(obj){
		var num;
		if(obj.id.length == 11){
			num = obj.id.substring(4,5);
		}else{
			num = obj.id.substring(4,6);
		}
		radow.doEvent('vert_item_change',num);
	}
	function tran_item_delete(){
		var current_tran = document.getElementById('tran_length').value ;
		document.getElementById("tran_"+current_tran).style.display='none';
		var vert_length = document.getElementById('vert_length').value;
		for(var i = 0; i < vert_length; i++){
			document.getElementById("vert"+i+"_tran"+current_tran).style.display='none';
		} 
	}
	function tran_item_add(){
		var current_tran = document.getElementById('tran_length').value - 1;
		document.getElementById("tran_"+current_tran).style.display='block';
		document.getElementById("tran_"+current_tran).setAttribute("class", "cell1"); 
		var vert_length = document.getElementById('vert_length').value;
		for(var i = 0; i < vert_length; i++){
			document.getElementById("vert"+i+"_tran"+current_tran).style.display='block';
		} 
	}
	function vert_item_delete(){
		var current_vert = document.getElementById('vert_length').value ;
		document.getElementById("vert_"+current_vert).style.display='none';
		var tran_length = document.getElementById('tran_length').value;
		for(var i = 0; i < tran_length; i++){
			document.getElementById("vert"+current_vert+"_tran"+i).style.display='none';
		} 
	}
	function vert_item_add(){
		var current_vert = document.getElementById('vert_length').value - 1;
		document.getElementById("vert_"+current_vert).style.display='block';
		document.getElementById("vert_"+current_vert).setAttribute("class", "cell2"); 
		var tran_length = document.getElementById('tran_length').value;
		for(var i = 0; i < tran_length; i++){
			document.getElementById("vert"+current_vert+"_tran"+i).style.display='block';
		} 
	}
	function editor_load(){
		var vert_length = document.getElementById("vert_length").value;
		var tran_length = document.getElementById('tran_length').value;
		for(var i = 0; i < vert_length; i++){
			document.getElementById("vert_"+i).style.display='block';
			for(var j = 0; j < tran_length; j++){
				document.getElementById("tran_"+j).style.display='block';
				document.getElementById("vert"+i+"_tran"+j).style.display='block';
			} 
		}
	
	}
	function first_load(){
		for(var i = 0; i < 3; i++){
			document.getElementById("vert_"+i).style.display='block';
			for(var j = 0; j < 3; j++){
				document.getElementById("tran_"+j).style.display='block';
				document.getElementById("vert"+i+"_tran"+j).style.display='block';
			} 
		}
	}
	
	function vert_delete_message(num){
		Ext.MessageBox.show({
		    title:"系统提示",
		    msg:"是否确定删除该统计项？",
		    buttons:{"ok":"删除","cancel":"取消"},
			modal:true,
			closable:false,
		    fn:function(e){
				if(e == "ok"){
					radow.doEvent("vert_item_delete",num);
				}
				if(e == "cancel"){
					return ;
				}
			
			}
		  
		});

	}
	function tran_delete_message(num){
		Ext.MessageBox.show({
		    title:"系统提示",
		    msg:"是否确定删除该统计项？",
		    buttons:{"ok":"删除","cancel":"取消"},
			modal:true,
			closable:false,
		    fn:function(e){
				if(e == "ok"){
					radow.doEvent("tran_item_delete",num);
				}
				if(e == "cancel"){
					return ;
				}
			
			}
		  
		});

	}
	function alldisplay(){
		for(var i = 0; i < 15; i++){
			document.getElementById("vert_"+i).style.display='none';
			for(var j = 0; j < 15; j++){
				document.getElementById("tran_"+j).style.display='none';
				document.getElementById("vert"+i+"_tran"+j).style.display='none';
			} 
		}
	}
	function recovery_vert(){
		alldisplay();
		var tran_length = document.getElementById('tran_length').value;
		for(var i = 0; i < 3; i++){
			document.getElementById("vert_"+i).style.display='block';
			for(var j = 0; j < tran_length; j++){
				document.getElementById("tran_"+j).style.display='block';
				document.getElementById("vert"+i+"_tran"+j).style.display='block';
			} 
		}
		document.getElementById("vert_length").value = 3;
		var vert_length = document.getElementById("vert_length").value;
	}
	function recovery_tran(){
		alldisplay();
		var vert_length = document.getElementById("vert_length").value;
		for(var i = 0; i < vert_length; i++){
			document.getElementById("vert_"+i).style.display='block';
			for(var j = 0; j < 3; j++){
				document.getElementById("tran_"+j).style.display='block';
				document.getElementById("vert"+i+"_tran"+j).style.display='block';
			} 
		}
		document.getElementById("tran_length").value = 3;
		var tran_length = document.getElementById("tran_length").value;
	}
</script>
<style type="text/css">
table.stable {
color:#333333;
border-width: 1px;
border-color: #999999;
border-collapse: collapse;
}
table.stable th {
background:#d4e9fc ;
border-width: 1px;
padding: 2px;
border-style: solid;
border-color: #999999;

}
table.stable td {
border-width: 1px;
padding: 2px;
border-style: solid;
border-color: #999999;
}

	.width1-130{
	width: 130px !important;
	height: 25px !important;
	}
	
	.cell1{
	width: 130px !important;
	height: 25px  !important;
	}
	.cell2{
	width: 130px !important;
	height: 25px  !important;
	}

</style>	
<table style="width:900px;height:50px;">
	<tr>
		<td style="width:150px" align="right" >
		横轴信息集
		</td>
		<td><tags:SelectInput  property="transverse" codetypeJS=""  onchange="tran_set_change()"/></td>
		<td style="width:230px" align="right">
		竖轴信息集
		</td>
		<td><tags:SelectInput  property="vertical" codetypeJS=""  onchange="vert_set_change()"/></td>
	</tr>
</table>
<div style="width:950px;height: 510px; overflow: auto;margin-left:15px;">
<table class="stable" >
	<tr >
		<th class="cell1" ></th>
		<!-- src="image/ewtable.png" -->
		<%-- <th class="cell1" ><tags:SelectInput label="民族" property="a0117" codetypeJS="GB3304" cls="width1-130" onchange="tran_item_change(this)"/></th>
		 --%>
		<th  id = "tran_0" style="display:none"><tags:SelectInput  property="tran0" codetypeJS="" cls="width1-130" onchange="tran_item_change(this)"/></th>
		<th  id = "tran_1" style="display:none"><tags:SelectInput  property="tran1" codetypeJS="" cls="width1-130" onchange="tran_item_change(this)"/></th>
		<th  id = "tran_2" style="display:none"><tags:SelectInput  property="tran2" codetypeJS="" cls="width1-130" onchange="tran_item_change(this)"/></th>
		<th  id = "tran_3" style="display:none"><tags:SelectInput  property="tran3" codetypeJS="" cls="width1-130" onchange="tran_item_change(this)"/></th>
		<th  id = "tran_4" style="display:none"><tags:SelectInput  property="tran4" codetypeJS="" cls="width1-130" onchange="tran_item_change(this)"/></th>
		<th  id = "tran_5" style="display:none"><tags:SelectInput  property="tran5" codetypeJS="" cls="width1-130" onchange="tran_item_change(this)"/></th>
		<th  id = "tran_6" style="display:none"><tags:SelectInput  property="tran6" codetypeJS="" cls="width1-130" onchange="tran_item_change(this)"/></th>
		<th  id = "tran_7" style="display:none"><tags:SelectInput  property="tran7" codetypeJS="" cls="width1-130" onchange="tran_item_change(this)"/></th>
		<th  id = "tran_8" style="display:none"><tags:SelectInput  property="tran8" codetypeJS="" cls="width1-130" onchange="tran_item_change(this)"/></th>
		<th  id = "tran_9" style="display:none"><tags:SelectInput  property="tran9" codetypeJS="" cls="width1-130" onchange="tran_item_change(this)"/></th>
		<th  id = "tran_10" style="display:none"><tags:SelectInput  property="tran10" codetypeJS="" cls="width1-130" onchange="tran_item_change(this)"/></th>
		<th  id = "tran_11" style="display:none"><tags:SelectInput  property="tran11" codetypeJS="" cls="width1-130" onchange="tran_item_change(this)"/></th>
		<th  id = "tran_12" style="display:none"><tags:SelectInput  property="tran12" codetypeJS="" cls="width1-130" onchange="tran_item_change(this)"/></th>
		<th  id = "tran_13" style="display:none"><tags:SelectInput  property="tran13" codetypeJS="" cls="width1-130" onchange="tran_item_change(this)"/></th>
		<th  id = "tran_14" style="display:none"><tags:SelectInput  property="tran14" codetypeJS="" cls="width1-130" onchange="tran_item_change(this)"/></th>
	</tr>
	<tr id = "vert_0" style="display:none">
		<th class="cell2" ><tags:SelectInput  property="vert0" codetypeJS="" cls="width1-130" onchange="vert_item_change(this)"/></th>
		<td id = "vert0_tran0" style="display:none"></td>
		<td id = "vert0_tran1" style="display:none"></td>
		<td id = "vert0_tran2" style="display:none"></td>
		<td id = "vert0_tran3" style="display:none"></td>
		<td id = "vert0_tran4" style="display:none"></td>
		<td id = "vert0_tran5" style="display:none"></td>
		<td id = "vert0_tran6" style="display:none"></td>
		<td id = "vert0_tran7" style="display:none"></td>
		<td id = "vert0_tran8" style="display:none"></td>
		<td id = "vert0_tran9" style="display:none"></td>
		<td id = "vert0_tran10" style="display:none"></td>
		<td id = "vert0_tran11" style="display:none"></td>
		<td id = "vert0_tran12" style="display:none"></td>
		<td id = "vert0_tran13" style="display:none"></td>
		<td id = "vert0_tran14" style="display:none"></td>
	</tr>
	<tr id = "vert_1" style="display:none">
		<th class="cell2" ><tags:SelectInput  property="vert1" codetypeJS="" cls="width1-130" onchange="vert_item_change(this)"/></th>
		<td id = "vert1_tran0" style="display:none"></td>
		<td id = "vert1_tran1" style="display:none"></td>
		<td id = "vert1_tran2" style="display:none"></td>
		<td id = "vert1_tran3" style="display:none"></td>
		<td id = "vert1_tran4" style="display:none"></td>
		<td id = "vert1_tran5" style="display:none"></td>
		<td id = "vert1_tran6" style="display:none"></td>
		<td id = "vert1_tran7" style="display:none"></td>
		<td id = "vert1_tran8" style="display:none"></td>
		<td id = "vert1_tran9" style="display:none"></td>
		<td id = "vert1_tran10" style="display:none"></td>
		<td id = "vert1_tran11" style="display:none"></td>
		<td id = "vert1_tran12" style="display:none"></td>
		<td id = "vert1_tran13" style="display:none"></td>
		<td id = "vert1_tran14" style="display:none"></td>
	</tr>
	<tr id = "vert_2" style="display:none">
		<th class="cell2" ><tags:SelectInput  property="vert2" codetypeJS="" cls="width1-130" onchange="vert_item_change(this)"/></th>
		<td id = "vert2_tran0" style="display:none"></td>
		<td id = "vert2_tran1" style="display:none"></td>
		<td id = "vert2_tran2" style="display:none"></td>
		<td id = "vert2_tran3" style="display:none"></td>
		<td id = "vert2_tran4" style="display:none"></td>
		<td id = "vert2_tran5" style="display:none"></td>
		<td id = "vert2_tran6" style="display:none"></td>
		<td id = "vert2_tran7" style="display:none"></td>
		<td id = "vert2_tran8" style="display:none"></td>
		<td id = "vert2_tran9" style="display:none"></td>
		<td id = "vert2_tran10" style="display:none"></td>
		<td id = "vert2_tran11" style="display:none"></td>
		<td id = "vert2_tran12" style="display:none"></td>
		<td id = "vert2_tran13" style="display:none"></td>
		<td id = "vert2_tran14" style="display:none"></td>
	</tr>
	<tr id = "vert_3" style="display:none">
		<th class="cell2" ><tags:SelectInput  property="vert3" codetypeJS="" cls="width1-130" onchange="vert_item_change(this)"/></th>
		<td id = "vert3_tran0" style="display:none"></td>
		<td id = "vert3_tran1" style="display:none"></td>
		<td id = "vert3_tran2" style="display:none"></td>
		<td id = "vert3_tran3" style="display:none"></td>
		<td id = "vert3_tran4" style="display:none"></td>
		<td id = "vert3_tran5" style="display:none"></td>
		<td id = "vert3_tran6" style="display:none"></td>
		<td id = "vert3_tran7" style="display:none"></td>
		<td id = "vert3_tran8" style="display:none"></td>
		<td id = "vert3_tran9" style="display:none"></td>
		<td id = "vert3_tran10" style="display:none"></td>
		<td id = "vert3_tran11" style="display:none"></td>
		<td id = "vert3_tran12" style="display:none"></td>
		<td id = "vert3_tran13" style="display:none"></td>
		<td id = "vert3_tran14" style="display:none"></td>
	</tr>
	<tr id = "vert_4" style="display:none">
		<th class="cell2"><tags:SelectInput  property="vert4" codetypeJS="" cls="width1-130" onchange="vert_item_change(this)"/></th>
		<td id = "vert4_tran0" style="display:none"></td>
		<td id = "vert4_tran1" style="display:none"></td>
		<td id = "vert4_tran2" style="display:none"></td>
		<td id = "vert4_tran3" style="display:none"></td>
		<td id = "vert4_tran4" style="display:none"></td>
		<td id = "vert4_tran5" style="display:none"></td>
		<td id = "vert4_tran6" style="display:none"></td>
		<td id = "vert4_tran7" style="display:none"></td>
		<td id = "vert4_tran8" style="display:none"></td>
		<td id = "vert4_tran9" style="display:none"></td>
		<td id = "vert4_tran10" style="display:none"></td>
		<td id = "vert4_tran11" style="display:none"></td>
		<td id = "vert4_tran12" style="display:none"></td>
		<td id = "vert4_tran13" style="display:none"></td>
		<td id = "vert4_tran14" style="display:none"></td>
	</tr>
	<tr id = "vert_5" style="display:none">
		<th class="cell2"><tags:SelectInput  property="vert5" codetypeJS="" cls="width1-130" onchange="vert_item_change(this)"/></th>
		<td id = "vert5_tran0" style="display:none"></td>
		<td id = "vert5_tran1" style="display:none"></td>
		<td id = "vert5_tran2" style="display:none"></td>
		<td id = "vert5_tran3" style="display:none"></td>
		<td id = "vert5_tran4" style="display:none"></td>
		<td id = "vert5_tran5" style="display:none"></td>
		<td id = "vert5_tran6" style="display:none"></td>
		<td id = "vert5_tran7" style="display:none"></td>
		<td id = "vert5_tran8" style="display:none"></td>
		<td id = "vert5_tran9" style="display:none"></td>
		<td id = "vert5_tran10" style="display:none"></td>
		<td id = "vert5_tran11" style="display:none"></td>
		<td id = "vert5_tran12" style="display:none"></td>
		<td id = "vert5_tran13" style="display:none"></td>
		<td id = "vert5_tran14" style="display:none"></td>
	</tr>
	<tr id = "vert_6" style="display:none">
		<th class="cell2"><tags:SelectInput  property="vert6" codetypeJS="" cls="width1-130" onchange="vert_item_change(this)"/></th>
		<td id = "vert6_tran0" style="display:none"></td>
		<td id = "vert6_tran1" style="display:none"></td>
		<td id = "vert6_tran2" style="display:none"></td>
		<td id = "vert6_tran3" style="display:none"></td>
		<td id = "vert6_tran4" style="display:none"></td>
		<td id = "vert6_tran5" style="display:none"></td>
		<td id = "vert6_tran6" style="display:none"></td>
		<td id = "vert6_tran7" style="display:none"></td>
		<td id = "vert6_tran8" style="display:none"></td>
		<td id = "vert6_tran9" style="display:none"></td>
		<td id = "vert6_tran10" style="display:none"></td>
		<td id = "vert6_tran11" style="display:none"></td>
		<td id = "vert6_tran12" style="display:none"></td>
		<td id = "vert6_tran13" style="display:none"></td>
		<td id = "vert6_tran14" style="display:none"></td>
	</tr>
	<tr id = "vert_7" style="display:none">
		<th class="cell2"><tags:SelectInput  property="vert7" codetypeJS="" cls="width1-130" onchange="vert_item_change(this)"/></th>
		<td id = "vert7_tran0" style="display:none"></td>
		<td id = "vert7_tran1" style="display:none"></td>
		<td id = "vert7_tran2" style="display:none"></td>
		<td id = "vert7_tran3" style="display:none"></td>
		<td id = "vert7_tran4" style="display:none"></td>
		<td id = "vert7_tran5" style="display:none"></td>
		<td id = "vert7_tran6" style="display:none"></td>
		<td id = "vert7_tran7" style="display:none"></td>
		<td id = "vert7_tran8" style="display:none"></td>
		<td id = "vert7_tran9" style="display:none"></td>
		<td id = "vert7_tran10" style="display:none"></td>
		<td id = "vert7_tran11" style="display:none"></td>
		<td id = "vert7_tran12" style="display:none"></td>
		<td id = "vert7_tran13" style="display:none"></td>
		<td id = "vert7_tran14" style="display:none"></td>
	</tr>
	<tr id = "vert_8" style="display:none">
		<th class="cell2"><tags:SelectInput  property="vert8" codetypeJS="" cls="width1-130" onchange="vert_item_change(this)"/></th>
		<td id = "vert8_tran0" style="display:none"></td>
		<td id = "vert8_tran1" style="display:none"></td>
		<td id = "vert8_tran2" style="display:none"></td>
		<td id = "vert8_tran3" style="display:none"></td>
		<td id = "vert8_tran4" style="display:none"></td>
		<td id = "vert8_tran5" style="display:none"></td>
		<td id = "vert8_tran6" style="display:none"></td>
		<td id = "vert8_tran7" style="display:none"></td>
		<td id = "vert8_tran8" style="display:none"></td>
		<td id = "vert8_tran9" style="display:none"></td>
		<td id = "vert8_tran10" style="display:none"></td>
		<td id = "vert8_tran11" style="display:none"></td>
		<td id = "vert8_tran12" style="display:none"></td>
		<td id = "vert8_tran13" style="display:none"></td>
		<td id = "vert8_tran14" style="display:none"></td>
	</tr>
	<tr id = "vert_9" style="display:none">
		<th class="cell2"><tags:SelectInput  property="vert9" codetypeJS="" cls="width1-130" onchange="vert_item_change(this)"/></th>
		<td id = "vert9_tran0" style="display:none"></td>
		<td id = "vert9_tran1" style="display:none"></td>
		<td id = "vert9_tran2" style="display:none"></td>
		<td id = "vert9_tran3" style="display:none"></td>
		<td id = "vert9_tran4" style="display:none"></td>
		<td id = "vert9_tran5" style="display:none"></td>
		<td id = "vert9_tran6" style="display:none"></td>
		<td id = "vert9_tran7" style="display:none"></td>
		<td id = "vert9_tran8" style="display:none"></td>
		<td id = "vert9_tran9" style="display:none"></td>
		<td id = "vert9_tran10" style="display:none"></td>
		<td id = "vert9_tran11" style="display:none"></td>
		<td id = "vert9_tran12" style="display:none"></td>
		<td id = "vert9_tran13" style="display:none"></td>
		<td id = "vert9_tran14" style="display:none"></td>
	</tr>
	<tr id = "vert_10" style="display:none">
		<th class="cell2"><tags:SelectInput  property="vert10" codetypeJS="" cls="width1-130" onchange="vert_item_change(this)"/></th>
		<td id = "vert10_tran0" style="display:none"></td>
		<td id = "vert10_tran1" style="display:none"></td>
		<td id = "vert10_tran2" style="display:none"></td>
		<td id = "vert10_tran3" style="display:none"></td>
		<td id = "vert10_tran4" style="display:none"></td>
		<td id = "vert10_tran5" style="display:none"></td>
		<td id = "vert10_tran6" style="display:none"></td>
		<td id = "vert10_tran7" style="display:none"></td>
		<td id = "vert10_tran8" style="display:none"></td>
		<td id = "vert10_tran9" style="display:none"></td>
		<td id = "vert10_tran10" style="display:none"></td>
		<td id = "vert10_tran11" style="display:none"></td>
		<td id = "vert10_tran12" style="display:none"></td>
		<td id = "vert10_tran13" style="display:none"></td>
		<td id = "vert10_tran14" style="display:none"></td>
	</tr>
	<tr id = "vert_11" style="display:none">
		<th class="cell2"><tags:SelectInput  property="vert11" codetypeJS="" cls="width1-130" onchange="vert_item_change(this)"/></th>
		<td id = "vert11_tran0" style="display:none"></td>
		<td id = "vert11_tran1" style="display:none"></td>
		<td id = "vert11_tran2" style="display:none"></td>
		<td id = "vert11_tran3" style="display:none"></td>
		<td id = "vert11_tran4" style="display:none"></td>
		<td id = "vert11_tran5" style="display:none"></td>
		<td id = "vert11_tran6" style="display:none"></td>
		<td id = "vert11_tran7" style="display:none"></td>
		<td id = "vert11_tran8" style="display:none"></td>
		<td id = "vert11_tran9" style="display:none"></td>
		<td id = "vert11_tran10" style="display:none"></td>
		<td id = "vert11_tran11" style="display:none"></td>
		<td id = "vert11_tran12" style="display:none"></td>
		<td id = "vert11_tran13" style="display:none"></td>
		<td id = "vert11_tran14" style="display:none"></td>
	</tr>
	<tr id = "vert_12" style="display:none">
		<th class="cell2"><tags:SelectInput  property="vert12" codetypeJS="" cls="width1-130" onchange="vert_item_change(this)"/></th>
		<td id = "vert12_tran0" style="display:none"></td>
		<td id = "vert12_tran1" style="display:none"></td>
		<td id = "vert12_tran2" style="display:none"></td>
		<td id = "vert12_tran3" style="display:none"></td>
		<td id = "vert12_tran4" style="display:none"></td>
		<td id = "vert12_tran5" style="display:none"></td>
		<td id = "vert12_tran6" style="display:none"></td>
		<td id = "vert12_tran7" style="display:none"></td>
		<td id = "vert12_tran8" style="display:none"></td>
		<td id = "vert12_tran9" style="display:none"></td>
		<td id = "vert12_tran10" style="display:none"></td>
		<td id = "vert12_tran11" style="display:none"></td>
		<td id = "vert12_tran12" style="display:none"></td>
		<td id = "vert12_tran13" style="display:none"></td>
		<td id = "vert12_tran14" style="display:none"></td>
	</tr>
	<tr id = "vert_13" style="display:none">
		<th class="cell2"><tags:SelectInput  property="vert13" codetypeJS="" cls="width1-130" onchange="vert_item_change(this)"/></th>
		<td id = "vert13_tran0" style="display:none"></td>
		<td id = "vert13_tran1" style="display:none"></td>
		<td id = "vert13_tran2" style="display:none"></td>
		<td id = "vert13_tran3" style="display:none"></td>
		<td id = "vert13_tran4" style="display:none"></td>
		<td id = "vert13_tran5" style="display:none"></td>
		<td id = "vert13_tran6" style="display:none"></td>
		<td id = "vert13_tran7" style="display:none"></td>
		<td id = "vert13_tran8" style="display:none"></td>
		<td id = "vert13_tran9" style="display:none"></td>
		<td id = "vert13_tran10" style="display:none"></td>
		<td id = "vert13_tran11" style="display:none"></td>
		<td id = "vert13_tran12" style="display:none"></td>
		<td id = "vert13_tran13" style="display:none"></td>
		<td id = "vert13_tran14" style="display:none"></td>
	</tr>
	<tr id = "vert_14" style="display:none">
		<th class="cell2"><tags:SelectInput  property="vert14" codetypeJS="" cls="width1-130" onchange="vert_item_change(this)"/></th>
		<td id = "vert14_tran0" style="display:none"></td>
		<td id = "vert14_tran1" style="display:none"></td>
		<td id = "vert14_tran2" style="display:none"></td>
		<td id = "vert14_tran3" style="display:none"></td>
		<td id = "vert14_tran4" style="display:none"></td>
		<td id = "vert14_tran5" style="display:none"></td>
		<td id = "vert14_tran6" style="display:none"></td>
		<td id = "vert14_tran7" style="display:none"></td>
		<td id = "vert14_tran8" style="display:none"></td>
		<td id = "vert14_tran9" style="display:none"></td>
		<td id = "vert14_tran10" style="display:none"></td>
		<td id = "vert14_tran11" style="display:none"></td>
		<td id = "vert14_tran12" style="display:none"></td>
		<td id = "vert14_tran13" style="display:none"></td>
		<td id = "vert14_tran14" style="display:none"></td>
	</tr>
</table>
</div>
<table style="width:900px;height:40px;">
   <tr >
    <td align="right" width="70%">
    <odin:button text="保存" property="save"></odin:button>
    </td>
    <td style="width:140px" align="right">
    </td>
    <td align="right">
    <odin:button text="统计" property="statistics" ></odin:button>    
    </td>
    <td style="width:140px" align="right">
    </td>
    <td align="right">
    <odin:button text="关闭" property="close" ></odin:button>    
    </td>
  </tr>
</table>
<odin:hidden property="vert_length"/>
<odin:hidden property="tran_length"/>
