<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>

<script type="text/javascript" src="<%=request.getContextPath() %>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/echarts.js" charset="utf-8"></script>
<script src="<%=request.getContextPath()%>/js/cllauth.js"> </script>
<script src="<%=request.getContextPath()%>/pages/customquery/formanalysis/formanalysis.js"> </script>


<%--用户接受路径参数的值，并保存 --%>
<%@include file="/comOpenWinInit.jsp" %>

<div id="createtable" style="overflow-y:hidden;overflow-x:hidden;overflow: auto;margin-left:15px;display:block;">
	<table  style="width:100%;" border="0">
		<col width="5%">
		<col width="15%">
		<col width="3%">
		<col width="3%">
		<col width="7%">
		<col width="67%">
		<tr height="3px">
		</tr>
		<tr>
			<odin:select2 property="zwlb" label="职务层次" codeType="ZWCELB" multiSelectWithAll="true" onchange="zwlb_func()"></odin:select2>
			<td width="3px"></td>
			<td>
				<odin:checkbox property="xianyin" label="隐藏全零行" value="1" onclick="xianyin_onclick(this)"></odin:checkbox>
			</td>
			<td width="3px"></td>
			<td>
				<table>
					<tr>
						<td>
							<odin:button text="导出" property="imp_excel" handler="exportData"></odin:button>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>
	<table style="width:100%;" border="0">
		<tr>
			<td colspan="3">
				<div>
					<object id="DCellWeb1" style="left: 0px; width: 964px; top: 0px; height: 440px" 
				    classid="clsid:3F166327-8030-4881-8BD2-EA25350E574A" 
				    codebase="softs/cellweb5.cab#version=5,3,8,0429">
				    <!-- #version=5.3.9.16 -->
				    <param name="_Version" value="65536" />
				    <param name="_ExtentX" value="10266" />
				    <param name="_ExtentY" value="7011" />
				    <param name="_StockProps" value="0" />
				    </object>
				</div>
			</td>
		</tr>
	</table>
</div>
<div>
	<odin:hidden property="zwlb_l"/><!-- 职务类别复选隐藏值 -->
	<odin:hidden property="xy_zwlb"/><!-- 显隐选择隐藏值 -->
	<odin:hidden property="jsonString_str"/><!-- 分组分析结果json字符串隐藏值 -->
</div>
<script for="DCellWeb1" event="MouseDClick(col, row)" language="JavaScript">
	myMouseDClick(col, row);
</script>
<script type="text/javascript" language="javascript">
	setAuth(document.getElementById("DCellWeb1"));
</script>
<script type="text/javascript">
////统计项目名称
var tjxm_arr = new Array(
		"",
		"",
		"",
		"",
		"20岁及以下",
		"21岁",
		"22岁",
		"23岁",
		"24岁",
		"25岁",
		"26岁",
		"27岁",
		"28岁",
		"29岁",
		"30岁",
		"31岁",
		"32岁",
		"33岁",
		"34岁",
		"35岁",
		"36岁",
		"37岁",
		"38岁",
		"39岁",
		"40岁",
		"41岁",
		"42岁",
		"43岁",
		"44岁",
		"45岁",
		"46岁",
		"47岁",
		"48岁",
		"49岁",
		"50岁",
		"51岁",
		"52岁",
		"53岁",
		"54岁",
		"55岁",
		"56岁",
		"57岁",
		"58岁",
		"59岁",
		"60岁",
		"61岁",
		"62岁",
		"63岁",
		"64岁",
		"65岁及以上",
		"平均年龄"
		);
////表格双击事件 
function myMouseDClick(col1, row1) {
	
	//参数错误 
	if(col1==0||col1==""||col1==" "||col1==null||col1=="undefined"||col1<3||col1>49){
		return;
	}
	if(row1==0||row1==""||row1==" "||row1==null||row1=="undefined"||row1<4||row1>(parseInt(array_row[array_row.length-1].split(",")[1])+1)){
		return;
	}
	//数值为0 或 空
	var value=DCellWeb_tj.GetCellString(col1,row1,0);
	if(value==0||value==""||value==" "||value==null||value=="undefined"){
		return;
	}
	
	var title1="";
	var title2="";
	title1=DCellWeb_tj.GetCellString(2,row1,0);
	for(var i=0;i<array_row.length;i++){//小计
		if(row1==(parseInt(array_row[i].split(",")[0])-1)){
			title1=DCellWeb_tj.GetCellString(1,row1,0);
		}
	}
	if(row1==4||row1==208){//总计
		title1=DCellWeb_tj.GetCellString(1,row1,0);
	}
	if(col1==3){
		title2=DCellWeb_tj.GetCellString(col1,2,0);
	}else{
		title2=DCellWeb_tj.GetCellString(col1,3,0);
	}
	var title=title1+"-"+title2;
	var dwid=document.getElementById("subWinIdBussessId2").value;
	var	cxtj=dwid+"$"+$('#zwlb_combo').attr("value")+"$"+col1+"$"+row1+"$"+title;
	//系统时间
	var date_time = new Date();
	var time=date_time.getHours()+ date_time.getMinutes()+date_time.getSeconds();
	$h.openWin('formanalysiszge'+time,'pages.customquery.formanalysis.formanalysisagelist',title+'-二维统计',950,550,cxtj,'<%=request.getContextPath()%>');
}
var ctpath="<%=request.getContextPath()%>";


function jcHeightWidth(){
	document.getElementById("DCellWeb1").style.height=document.body.offsetHeight-60;//底部滚动条宽度+查询条件高度 =60
	document.getElementById("DCellWeb1").style.width=document.body.offsetWidth;
}
Ext.onReady(function openfile(){
    try{
		document.getElementById("DCellWeb1").openfile(ctpath+"\\template\\gbjbqktjnlry.cll","");
    }catch(err){
    	//alert("提示:请下载安装华表插件!并使用IE浏览器(兼容模式)打开(可联系系统管理员远程协助)!");
    	//alert("请安装华表插件!");
    }
    //设置复选框上下居中
  	checkboxvl("xianyin");
  	document.getElementById("createtable").parentNode.parentNode.style.overflow='hidden';
  	window.onresize=jcHeightWidth;
});
//根据 odin:checkbox 的 property属性 设置复选框的上下居中样式
function checkboxvl(id){
	try{
		var node_66;
		var node_55;
		var node_5_height;
		var node_6_height;
		node_66=$("#"+id).parent();
		node_6_height=node_66[0].offsetHeight;
		node_55=node_66.children();
		node_5_height=node_55[0].offsetHeight;
		var mt=(node_6_height-node_5_height)/2;
		$("#"+id).parent().parent().parent().css({"top": mt+"px"});
	}catch(err){
	}
}
</script>
<script type="text/javascript">
//表格json数据
var json_obj="";
//初始化方法 
var DCellWeb_tj="";
var col_xho_start=3;
var col_xho_end=50;
//json字符串

function json_func(){
   //jsonstr,lengthstr
   var jsonstr=document.getElementById("jsonString_str").value;
   DCellWeb_tj=document.getElementById("DCellWeb1");//获取表格对象
   var obj ="";
   try{
	   obj = eval('(' + jsonstr + ')');
   }catch(err){
   }
   json_obj=obj;
   DCellWeb_tj.SetFixedCol(1, 2); 
   DCellWeb_tj.SetFixedRow(1, 3);
   
	DCellWeb_tj.InsertCol (DCellWeb_tj.GetCols( 0 ), 1, 0);//追加一列
	DCellWeb_tj.SetColHidden(DCellWeb_tj.GetCols( 0 )-1,DCellWeb_tj.GetCols( 0 )-1);//隐藏最后一列
   for(var i=0;i<obj.length;i++){////插入数据 行数 循环
   	   var a0221=obj[i].a0221;//代码
   	   if(a0221=='undefined' || a0221=="" || a0221==null||a0221==" "){
   			//continue;//类别代码为空
   		 	a0221='QT';//类代码为空放在最后一行统计
   	   }
   	   a0221=map[a0221];//行数
   	   if(a0221=='undefined' || a0221=="" || a0221==null||a0221==" "){
   		a0221=parseInt(array_row[array_row.length-1].split(",")[1])+1;//最后一行
   		var i_col=3;
   		var temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
   		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
   			temp=0;
   		}
   		var value_last=obj[i].heji==0?temp:parseInt(obj[i].heji)+parseInt(temp);
 	   	DCellWeb_tj.SetCellString(
 	   			i_col,a0221,0,value_last==0?"":value_last);//合计
 	   i_col=i_col+1;	
 	   temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 			temp=0;
 		}
 		value_last=obj[i].xydy20==0?temp:parseInt(obj[i].xydy20)+parseInt(temp);
 	   	DCellWeb_tj.SetCellString(
 	   			i_col,a0221,0,value_last==0?"":value_last);//20及以下
 	   	i_col=i_col+1;
 	   temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
			temp=0;
		}
		value_last=obj[i].dy21==0?temp:parseInt(obj[i].dy21)+parseInt(temp);
 	   	DCellWeb_tj.SetCellString(
 	   			i_col,a0221,0,value_last==0?"":value_last);//
 		i_col=i_col+1;
		temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 			temp=0;
 		}
 		value_last=obj[i].dy22==0?temp:parseInt(obj[i].dy22)+parseInt(temp);
 	   	DCellWeb_tj.SetCellString(
 	   			i_col,a0221,0,value_last==0?"":value_last);//
 			i_col=i_col+1;
 			temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy23==0?temp:parseInt(obj[i].dy23)+parseInt(temp);
 	   	DCellWeb_tj.SetCellString(
 	   			i_col,a0221,0,value_last==0?"":value_last);//
 			i_col=i_col+1;
 			temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy24==0?temp:parseInt(obj[i].dy24)+parseInt(temp);
 	   	DCellWeb_tj.SetCellString(
 	   			i_col,a0221,0,value_last==0?"":value_last);//
 			i_col=i_col+1;
 			temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy25==0?temp:parseInt(obj[i].dy25)+parseInt(temp);
 		   	DCellWeb_tj.SetCellString(
 		   		i_col,a0221,0,value_last==0?"":value_last);//
 		   	i_col=i_col+1;
 		   temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy26==0?temp:parseInt(obj[i].dy26)+parseInt(temp);
 		   	DCellWeb_tj.SetCellString(
 		   		i_col,a0221,0,value_last==0?"":value_last);//
 		   	i_col=i_col+1;
 		   temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy27==0?temp:parseInt(obj[i].dy27)+parseInt(temp);
 			DCellWeb_tj.SetCellString(
 	   			i_col,a0221,0,value_last==0?"":value_last);//
 			i_col=i_col+1;
 			temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy28==0?temp:parseInt(obj[i].dy28)+parseInt(temp);
 	   	DCellWeb_tj.SetCellString(
 	   			i_col,a0221,0,value_last==0?"":value_last);//
 			i_col=i_col+1;
 			temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy29==0?temp:parseInt(obj[i].dy29)+parseInt(temp);
 	   	DCellWeb_tj.SetCellString(
 	   			i_col,a0221,0,value_last==0?"":value_last);//
 			i_col=i_col+1;
 			temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy30==0?temp:parseInt(obj[i].dy30)+parseInt(temp);
 	   	DCellWeb_tj.SetCellString(
 	   			i_col,a0221,0,value_last==0?"":value_last);//
 			i_col=i_col+1;
 			temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy31==0?temp:parseInt(obj[i].dy31)+parseInt(temp);
 	   	DCellWeb_tj.SetCellString(
 	   			i_col,a0221,0,value_last==0?"":value_last);//
 			i_col=i_col+1;
 			temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy32==0?temp:parseInt(obj[i].dy32)+parseInt(temp);
 	   	DCellWeb_tj.SetCellString(
 	   			i_col,a0221,0,value_last==0?"":value_last);//
 			i_col=i_col+1;
 			temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy33==0?temp:parseInt(obj[i].dy33)+parseInt(temp);
	   	DCellWeb_tj.SetCellString(
	   		i_col,a0221,0,value_last==0?"":value_last);//
 		   	i_col=i_col+1;
 		   temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy34==0?temp:parseInt(obj[i].dy34)+parseInt(temp);
	   	DCellWeb_tj.SetCellString(
	   		i_col,a0221,0,value_last==0?"":value_last);//
 		   	i_col=i_col+1;
 		   temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy35==0?temp:parseInt(obj[i].dy35)+parseInt(temp);
		DCellWeb_tj.SetCellString(
   			i_col,a0221,0,value_last==0?"":value_last);//
 			i_col=i_col+1;	
 			temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy36==0?temp:parseInt(obj[i].dy36)+parseInt(temp);
 	   	DCellWeb_tj.SetCellString(
 	   			i_col,a0221,0,value_last==0?"":value_last);//
 			i_col=i_col+1;
 			temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy37==0?temp:parseInt(obj[i].dy37)+parseInt(temp);
 	   	DCellWeb_tj.SetCellString(
 	   			i_col,a0221,0,value_last==0?"":value_last);//
 			i_col=i_col+1;
 			temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy38==0?temp:parseInt(obj[i].dy38)+parseInt(temp);
 	   	DCellWeb_tj.SetCellString(
 	   			i_col,a0221,0,value_last==0?"":value_last);//
 			i_col=i_col+1;
 			temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy39==0?temp:parseInt(obj[i].dy39)+parseInt(temp);
 	   	DCellWeb_tj.SetCellString(
 	   			i_col,a0221,0,value_last==0?"":value_last);//
 			i_col=i_col+1;
 			temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy40==0?temp:parseInt(obj[i].dy40)+parseInt(temp);
 	   	DCellWeb_tj.SetCellString(
 	   			i_col,a0221,0,value_last==0?"":value_last);//
 			i_col=i_col+1;
 			temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy41==0?temp:parseInt(obj[i].dy41)+parseInt(temp);
 		   	DCellWeb_tj.SetCellString(
 		   		i_col,a0221,0,value_last==0?"":value_last);//
 		   	i_col=i_col+1;
 		   temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy42==0?temp:parseInt(obj[i].dy42)+parseInt(temp);
 		   	DCellWeb_tj.SetCellString(
 		   		i_col,a0221,0,value_last==0?"":value_last);//
 		  	i_col=i_col+1;
 		  	temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy43==0?temp:parseInt(obj[i].dy43)+parseInt(temp);
 		   	DCellWeb_tj.SetCellString( i_col,a0221,0,value_last==0?"":value_last);//
 		   	i_col=i_col+1;
 		   temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy44==0?temp:parseInt(obj[i].dy44)+parseInt(temp);
 		   	DCellWeb_tj.SetCellString( i_col,a0221,0,value_last==0?"":value_last);//
 		   	i_col=i_col+1;
 		   temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy45==0?temp:parseInt(obj[i].dy45)+parseInt(temp);
 		   	DCellWeb_tj.SetCellString( i_col,a0221,0,value_last==0?"":value_last);//
 		   	i_col=i_col+1;
 		   temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy46==0?temp:parseInt(obj[i].dy46)+parseInt(temp);
 		   	DCellWeb_tj.SetCellString( i_col,a0221,0,value_last==0?"":value_last);//
 		   	i_col=i_col+1;
 		   value_last= obj[i].dy47==0?temp:parseInt(obj[i].dy47)+parseInt(temp);
 		   	DCellWeb_tj.SetCellString( i_col,a0221,0,value_last==0?"":value_last);//
 		   	i_col=i_col+1;
 		   temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy48==0?temp:parseInt(obj[i].dy48)+parseInt(temp);
 		   	DCellWeb_tj.SetCellString( i_col,a0221,0,value_last==0?"":value_last);//
 		 	i_col=i_col+1;
 		 	temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy49==0?temp:parseInt(obj[i].dy49)+parseInt(temp);
 	   	DCellWeb_tj.SetCellString( i_col,a0221,0,value_last==0?"":value_last);//
 		   	i_col=i_col+1;
 		   temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy50==0?temp:parseInt(obj[i].dy50)+parseInt(temp);
 		   	DCellWeb_tj.SetCellString(i_col,a0221,0,value_last==0?"":value_last);//
 		   	i_col=i_col+1;
 		   temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy51==0?temp:parseInt(obj[i].dy51)+parseInt(temp);
 		   	DCellWeb_tj.SetCellString( i_col,a0221,0,value_last==0?"":value_last);//
 		   	i_col=i_col+1;
 		   temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy52==0?temp:parseInt(obj[i].dy52)+parseInt(temp);
 		   	DCellWeb_tj.SetCellString(
 		   		i_col,a0221,0,value_last==0?"":value_last);//
 		   	i_col=i_col+1;
 		   temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy53==0?temp:parseInt(obj[i].dy53)+parseInt(temp);
 		   	DCellWeb_tj.SetCellString(
 		   		i_col,a0221,0,value_last==0?"":value_last);//
 		   	i_col=i_col+1;
 		   temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy54==0?temp:parseInt(obj[i].dy54)+parseInt(temp);
 		   	DCellWeb_tj.SetCellString(
 		   		i_col,a0221,0,value_last==0?"":value_last);//
 	    	    		   	
    		i_col=i_col+1;
    		temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
     		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
     			temp=0;
     		}
     		value_last=obj[i].dy55==0?temp:parseInt(obj[i].dy55)+parseInt(temp);
 		   	DCellWeb_tj.SetCellString(
 		   		i_col,a0221,0,value_last==0?"":value_last);//
 	    	    		   	
    		i_col=i_col+1;
    		temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
     		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
     			temp=0;
     		}
     		value_last=obj[i].dy56==0?temp:parseInt(obj[i].dy56)+parseInt(temp);
 		   	DCellWeb_tj.SetCellString(
 		   		i_col,a0221,0,value_last==0?"":value_last);//
 		   		
 		   	i_col=i_col+1;
 		   temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy57==0?temp:parseInt(obj[i].dy57)+parseInt(temp);
 		   	DCellWeb_tj.SetCellString(
 		   		i_col,a0221,0,value_last==0?"":value_last);//
 		   	i_col=i_col+1;
 		   temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy58==0?temp:parseInt(obj[i].dy58)+parseInt(temp);
 		   	DCellWeb_tj.SetCellString(
 		   		i_col,a0221,0,value_last==0?"":value_last);//
 		   	i_col=i_col+1;
 		   temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy59==0?temp:parseInt(obj[i].dy59)+parseInt(temp);
 		   	DCellWeb_tj.SetCellString(
 		   		i_col,a0221,0,value_last==0?"":value_last);//
 		   	i_col=i_col+1;
 		   temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy60==0?temp:parseInt(obj[i].dy60)+parseInt(temp);
 		   	DCellWeb_tj.SetCellString(
 		   		i_col,a0221,0,value_last==0?"":value_last);//
 		   	i_col=i_col+1;
 		   temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy61==0?temp:parseInt(obj[i].dy61)+parseInt(temp);
 		   	DCellWeb_tj.SetCellString(
 		   		i_col,a0221,0,value_last==0?"":value_last);//
 		   	i_col=i_col+1;
 		   temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy62==0?temp:parseInt(obj[i].dy62)+parseInt(temp);
 		   	DCellWeb_tj.SetCellString(
 		   		i_col,a0221,0,value_last==0?"":value_last);//
 		   	i_col=i_col+1;
 		   temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy63==0?temp:parseInt(obj[i].dy63)+parseInt(temp);
 		   	DCellWeb_tj.SetCellString(
 		   		i_col,a0221,0,value_last==0?"":value_last);//
 		   	i_col=i_col+1;
 		   temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dy64==0?temp:parseInt(obj[i].dy64)+parseInt(temp);
 		   	DCellWeb_tj.SetCellString(
 		   		i_col,a0221,0,value_last==0?"":value_last);//
 		   	i_col=i_col+1;
 		   temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].dydy65==0?temp:parseInt(obj[i].dydy65)+parseInt(temp);
 		   	DCellWeb_tj.SetCellString(
 		   		i_col,a0221,0,value_last==0?"":value_last);//
 		   	i_col=i_col+1;
 		   temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=(obj[i].pjnl==0||obj[i].pjnl=='null')?temp:returnFloat(parseInt(obj[i].pjnl)*parseInt(obj[i].heji_zs)+parseInt(temp));
 	 		
 		   	DCellWeb_tj.SetCellString(
 		   		i_col,a0221,0,value_last==0?"":value_last);//
 		   	i_col=i_col+1;
 		   temp=DCellWeb_tj.GetCellString(i_col,a0221,0);
 	 		if(temp=='undefined' || temp=="" || temp==null||temp==" "){
 	 			temp=0;
 	 		}
 	 		value_last=obj[i].heji_zs==0?temp:parseInt(obj[i].heji_zs)+parseInt(temp);
 	 	
 		   	DCellWeb_tj.SetCellString(
 		   		i_col,a0221,0,value_last==0?"":value_last);//年龄字段不为空的人数 (平均年龄根据此数据计算)
   	   }else{
   	   var i_col=3;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,obj[i].heji==0?"":obj[i].heji);//合计
	   i_col=i_col+1;		
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,obj[i].xydy20==0?"":obj[i].xydy20);//20及以下
	   	i_col=i_col+1;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,obj[i].dy21==0?"":obj[i].dy21);//
			i_col=i_col+1;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,obj[i].dy22==0?"":obj[i].dy22);//
			i_col=i_col+1;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,obj[i].dy23==0?"":obj[i].dy23);//
			i_col=i_col+1;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,obj[i].dy24==0?"":obj[i].dy24);//
			i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,obj[i].dy25==0?"":obj[i].dy25);//
		   	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,obj[i].dy26==0?"":obj[i].dy26);//
		   	i_col=i_col+1;
			DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,obj[i].dy27==0?"":obj[i].dy27);//
			i_col=i_col+1;	
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,obj[i].dy28==0?"":obj[i].dy28);//
			i_col=i_col+1;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,obj[i].dy29==0?"":obj[i].dy29);//
			i_col=i_col+1;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,obj[i].dy30==0?"":obj[i].dy30);//
			i_col=i_col+1;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,obj[i].dy31==0?"":obj[i].dy31);//
			i_col=i_col+1;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,obj[i].dy32==0?"":obj[i].dy32);//
			i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,obj[i].dy33==0?"":obj[i].dy33);//
		   	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,obj[i].dy34==0?"":obj[i].dy34);//
		   	i_col=i_col+1;
			DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,obj[i].dy35==0?"":obj[i].dy35);//
			i_col=i_col+1;		
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,obj[i].dy36==0?"":obj[i].dy36);//
			i_col=i_col+1;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,obj[i].dy37==0?"":obj[i].dy37);//
			i_col=i_col+1;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,obj[i].dy38==0?"":obj[i].dy38);//
			i_col=i_col+1;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,obj[i].dy39==0?"":obj[i].dy39);//
			i_col=i_col+1;
	   	DCellWeb_tj.SetCellString(
	   			i_col,a0221,0,obj[i].dy40==0?"":obj[i].dy40);//
			i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,obj[i].dy41==0?"":obj[i].dy41);//
		   	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,obj[i].dy42==0?"":obj[i].dy42);//
		  	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString( i_col,a0221,0,obj[i].dy43==0?"":obj[i].dy43);//
		   	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString( i_col,a0221,0,obj[i].dy44==0?"":obj[i].dy44);//
		   	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString( i_col,a0221,0,obj[i].dy45==0?"":obj[i].dy45);//
		   	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString( i_col,a0221,0,obj[i].dy46==0?"":obj[i].dy46);//
		   	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString( i_col,a0221,0,obj[i].dy47==0?"":obj[i].dy47);//
		   	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString( i_col,a0221,0,obj[i].dy48==0?"":obj[i].dy48);//
		 	i_col=i_col+1;
	   	DCellWeb_tj.SetCellString( i_col,a0221,0,obj[i].dy49==0?"":obj[i].dy49);//
		   	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(i_col,a0221,0,obj[i].dy50==0?"":obj[i].dy50);//
		   	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString( i_col,a0221,0,obj[i].dy51==0?"":obj[i].dy51);//
		   	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,obj[i].dy52==0?"":obj[i].dy52);//
		   	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,obj[i].dy53==0?"":obj[i].dy53);//
	    	  		   	
		   		
		   	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,obj[i].dy54==0?"":obj[i].dy54);//
	    	    		   	
   		i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,obj[i].dy55==0?"":obj[i].dy55);//
	    	    		   	
   		i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,obj[i].dy56==0?"":obj[i].dy56);//
		   		
		   	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,obj[i].dy57==0?"":obj[i].dy57);//
		   	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,obj[i].dy58==0?"":obj[i].dy58);//
		   	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,obj[i].dy59==0?"":obj[i].dy59);//
		   	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,obj[i].dy60==0?"":obj[i].dy60);//
		   	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,obj[i].dy61==0?"":obj[i].dy61);//
		   	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,obj[i].dy62==0?"":obj[i].dy62);//
		   	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,obj[i].dy63==0?"":obj[i].dy63);//
		   	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,obj[i].dy64==0?"":obj[i].dy64);//
		   	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,obj[i].dydy65==0?"":obj[i].dydy65);//
		   	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,obj[i].pjnl==0?"":returnFloat(obj[i].pjnl));//
		   	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   		i_col,a0221,0,obj[i].heji_zs==0?"":obj[i].heji_zs);//
   	   }
   	
	}/// for end
	//重新计算最后一行的平均年龄 
	//最后一行的平均年龄之和 
	 var age_total=DCellWeb_tj.GetCellString(tjxm_arr.length-1,parseInt(array_row[array_row.length-1].split(",")[1])+1,0);
	if(age_total=='undefined' || age_total=="" || age_total==null||age_total==" "){
		age_total=0;
	} 
	//最后一行的人数
	 var num_per=DCellWeb_tj.GetCellString(3,parseInt(array_row[array_row.length-1].split(",")[1])+1,0);   
	if(num_per=='undefined' || num_per=="" || num_per==null||num_per==" "){
		num_per=1;
	}
	DCellWeb_tj.SetCellString(
	tjxm_arr.length-1,parseInt(array_row[array_row.length-1].split(",")[1])+1,0,returnFloat(age_total/num_per)); //
	//小计
   for(var i=0;i<array_row.length;i++){
		total_tj(parseInt(array_row[i].split(",")[0])-1,
				parseInt(array_row[i].split(",")[0]),
				parseInt(array_row[i].split(",")[1]));
	}
	//总计 
	total_zj();
	
	
	//隐藏 全行为0的行
	displayzeroinit();
	//设置页面不可编辑
	DCellWeb_tj.WorkbookReadonly=true;

	//重新设置grid高度
	
}

function returnFloat(value){
	/// var value=Math.round(parseFloat(value)*100)/100;
	 var xsd=value.toString().split(".");
	 if(xsd.length==1){
	 value=value.toString()+".00";
	 return value;
	 }
	 if(xsd.length>1){
	 if(xsd[1].length<2){
	 value=value.toString()+"0";
	 }
	 return value;
	 }
	}
//总计
function total_zj(){
	   //第3列单独统计 
	   var temp_zj_=parseInt(total_zj_ittt(3));
		DCellWeb_tj.SetCellString(
			3,4,0,temp_zj_==0?"":temp_zj_ );
		
	   var _zj_total=0;
	   for(var i=col_xho_start;i<=col_xho_end;i++){
			
			_zj_total=total_zj_ittt(i);
		    
			DCellWeb_tj.SetCellString(i,4,0,_zj_total==0?"":_zj_total );
		}
}

function total_zj_ittt(i){
	   var zj_total=0;
	   var zj_temp_total=0;
	   for(var j=0;j<array_row.length;j++){
		   zj_temp_total=DCellWeb_tj.GetCellString2(i, parseInt(array_row[j].split(",")[0])-1, 0);
			if(zj_temp_total=='undefined' || zj_temp_total=="" || zj_temp_total==null||zj_temp_total==" "){
				zj_temp_total=0;
			}
			zj_total=zj_total+parseInt(zj_temp_total);
	   }
	   //加上最后一行的值
	   var lastvalue=DCellWeb_tj.GetCellString2(i, parseInt(array_row[array_row.length-1].split(",")[1])+1, 0);
	   if(lastvalue=='undefined' || lastvalue=="" || lastvalue==null||lastvalue==" "){
		   lastvalue=0;
	   }
	   zj_total=zj_total+parseInt(lastvalue);
	 	
		if(i==col_xho_end){
			zj_total=pj_38_zj(i);
			
		}
		return zj_total;
}

//总计  平均年龄
function pj_38_zj(i){
	   var zj_total=0;
	   var zj_temp_total=0;//当前行平均年龄
	   var zj_temp_3=0;//当前行总人数
	 
	   var zrs=0;//总人数
	   for(var j=0;j<array_row.length;j++){//循环职务大类
		   for(var n=parseInt(array_row[j].split(",")[0]);n<=parseInt(array_row[j].split(",")[1]);n++){//循环职务等级
				zj_temp_total=DCellWeb_tj.GetCellString2(i, n, 0);
				if(zj_temp_total=='undefined' || zj_temp_total=="" || zj_temp_total==null||zj_temp_total==" "){
					zj_temp_total=0;
				}
				//zj_temp_3=r_total_r(n);
				zj_temp_3=DCellWeb_tj.GetCellString2(tjxm_arr.length, n, 0);
				//zj_temp_3=DCellWeb_tj.GetCellString2((i+1), n, 0);
				if(zj_temp_3=='undefined' || zj_temp_3=="" || zj_temp_3==null||zj_temp_3==" "){
					zj_temp_3=0;
				}
				zrs=zrs+parseInt(zj_temp_3);
				zj_total=zj_total+parseInt(zj_temp_total)*parseInt(zj_temp_3);
		   }
	    }
		if(zrs=='undefined' || zrs=="" || zrs==null||zrs==" "||zrs==0){
			zrs=1;
		}

		return returnFloat(Math.round(parseInt(zj_total)/parseInt(zrs)*100)/100);
}

	//统计小计
	function total_tj(row_xj,row_s,row_e){//row_xj 小计统计行 row_s开始行 row_e 结束行
		
		var temp_v="";
		for(var i=col_xho_start;i<=(col_xho_end+1);i++){//列循环
			
			temp_v=col_total(i,row_s,row_e);
			DCellWeb_tj.SetCellString(
		   			i,row_xj,0,temp_v==0?"":temp_v );
		}
	}
	
	function col_total(col_xj,row_s,row_e){
	    var col_num=0;
	    var v=0;
	    for(var i=row_s;i<=row_e;i++){
			v=DCellWeb_tj.GetCellString2(col_xj, i, 0);
			if(v=='undefined' || v=="" || v==null||v==" "){
				v=0;
			}
			col_num=col_num+parseInt(v);
	    }
		if(col_xj==col_xho_end){//平均值
			var znl=0;
			var zrs=0; 
			var temp_pjnl=0;
			var dqhrs=0;
			for(var i=row_s;i<=row_e;i++){//行
				temp_pjnl=DCellWeb_tj.GetCellString2(col_xj, i, 0);//当前行平均年龄
				if(temp_pjnl==""||temp_pjnl==" "||temp_pjnl=="undefined"||temp_pjnl==null){
					temp_pjnl=0;
	  			}
				dqhrs=DCellWeb_tj.GetCellString2((col_xho_end+1), i, 0);//人数
				if(dqhrs==""||dqhrs==" "||dqhrs=="undefined"||dqhrs==null){
					dqhrs=0;
	  			}
				zrs=zrs+parseInt(dqhrs);
				znl=znl+parseInt(temp_pjnl)*parseInt(dqhrs);
			}
			if(zrs==""||zrs==" "||zrs=="undefined"||zrs==null||zrs==0){
				zrs=1;
			}
			col_num=returnFloat(Math.round(parseInt(znl)/parseInt(zrs)*100)/100);
		}
	    return col_num;
	}

//隐藏所有行从4行开始
function yx_all(){
	  DCellWeb_tj.SetRowHidden(4, parseInt(array_row[array_row.length-1].split(",")[1])+1);
}

function displayzeroinit(){
	  var v=0;
	  var i_v=parseInt(array_row[array_row.length-1].split(",")[1]+1);
		 for(var i=4;i<=i_v;i++){//行
			 v=DCellWeb_tj.GetCellString(3, i, 0);//判断合计是否为零  ，为零 则为行全零
			 if(v=='undefined' || v=="" || v==null||v==" "||v==0){
				 DCellWeb_tj.SetRowHidden(i, i);
			 }else{
				 DCellWeb_tj.SetRowUnHidden(i, i);
			 }
			 
		 }
}

//隐藏全零行
function displayzero(xy){
	 
	  zwlb_xy();
	
}

//显示所有行
function xsall(){
	DCellWeb_tj.SetRowUnhidden(4, parseInt(array_row[array_row.length-1].split(",")[1])+1);
}
function xs_zwlb_zero(){
	  zwlb_xy();
}
</script>
<script type="text/javascript">
//根据选择职位，显示 统计 
var zwlb_data ="";
var xy_zwlb="1";
function zwlb_xy(){
	  xy_zwlb=document.getElementById("xy_zwlb").value;
	  zwlb_data=document.getElementById("zwlb_l").value;
	  clear4();
	  if(zwlb_data=='undefined' || zwlb_data=="" || zwlb_data==null || zwlb_data==" "){
		  if(xy_zwlb=='0'){
			  xsall();//显示所有行
		  }else{
			  displayzeroinit();//隐藏全0行
		  }
		  total_zj_xy();//总计
		  
		  DCellWeb_tj.SetRowUnhidden(4, 4);//显示
		  return;
	  }
	  
	  if(zwlb_data=='all'||zwlb_data=='ALL'){
		  if(xy_zwlb=='0'){
		  	xsall();//显示所有行
		  }else{
			  displayzeroinit();//隐藏全0行
		  }
		  total_zj();//重新统计
		 
		  DCellWeb_tj.SetRowUnhidden(4, 4);//显示
		 
		  return;
	  }
	  var strs= new Array(); //定义一数组 存储职务类别代码 
	  var strs_m= new Array(); //定义一数组  存储行数 
	  strs=zwlb_data.split(","); //字符分割
	  DCellWeb_tj.SetRowhidden(4, parseInt(array_row[array_row.length-1].split(",")[1])+1);//隐藏所有行
	  for (var i=0;i<strs.length ;i++ ) { 
		  strs_m=map_zwlb[strs[i]].split(",");
		  DCellWeb_tj.SetRowUnhidden(strs_m[0], strs_m[1]);//显示
		  if(strs_m[1]!=(parseInt(array_row[array_row.length-1].split(",")[1])+1)){//不是最后一行不显示小计
			  //小计
			  DCellWeb_tj.SetRowUnhidden(parseInt(strs_m[0])-1, parseInt(strs_m[0])-1);//显示
		  }
		  //重新统计
		  DCellWeb_tj.SetRowUnhidden(4, 4);//显示
		  total_zj_xy();//总计
		  
		  if(xy_zwlb=='1'){//隐藏行
			  displayzero_zwlb(strs_m[0], strs_m[1]);//隐藏 0行
		  }
	  } 
}
function displayzero_zwlb(start,end){
	  var v=0;
		 for(var i=parseInt(start);i<=parseInt(end);i++){//行
			 v=DCellWeb_tj.GetCellString(3, i, 0);//判断合计是否为零  ，为零 则为行全零
			 if(v=='undefined' || v=="" || v==null||v==" "||v==0){
				 DCellWeb_tj.SetRowHidden(i, i);
			 }
		 }
		 v=DCellWeb_tj.GetCellString(3, parseInt(start)-1, 0);//判断合计是否为零  ，为零 则为行全零
		 if(v=='undefined' || v=="" || v==null||v==" "||v==0){
			 DCellWeb_tj.SetRowHidden(parseInt(start)-1,parseInt(start)-1);
		 }
}
//清空第三行的数据
function clear4(){
	  for(var i=col_xho_start;i<=col_xho_end;i++){
		  DCellWeb_tj.SetCellString(i,4,0,'');
	  }
}

//总计
function total_zj_xy(){
		
	   var _zj_total=0;
	   for(var i=col_xho_start;i<=col_xho_end;i++){

			_zj_total=total_zj_ittt_xy(i);
		    //偶数列 小计
			DCellWeb_tj.SetCellString(i,4,0,_zj_total==0?"":_zj_total );
		}
}

function total_zj_ittt_xy(i){
	   var zj_total=0;
	   var zj_temp_total=0;
	   for(var j=0;j<array_row.length;j++){
		   if(DCellWeb_tj.IsRowHidden(parseInt(array_row[j].split(",")[0])-1,0)==false){//行显示
				zj_temp_total=DCellWeb_tj.GetCellString(i, parseInt(array_row[j].split(",")[0])-1,0);
				if(zj_temp_total=='undefined' || zj_temp_total=="" || zj_temp_total==null||zj_temp_total==" "){
						zj_temp_total=0;
				}
				zj_total=zj_total+parseInt(zj_temp_total);
		 	}
	   }
	   //加上最后一行的值
	   var lastvalue=DCellWeb_tj.GetCellString2(i, parseInt(array_row[array_row.length-1].split(",")[1])+1, 0);
	   if(lastvalue=='undefined' || lastvalue=="" || lastvalue==null||lastvalue==" "){
		   lastvalue=0;
	   }
	   zj_total=zj_total+parseInt(lastvalue);
	 	if(i==col_xho_end){
	 		zj_total=total_zj_ittt_pjnl(i);
	 	}
		return zj_total;
}

function total_zj_ittt_pjnl(i){
	  var zj_total=0;
	   var zj_temp_total=0;//当前行平均年龄
	   var zj_temp_3=0;//当前行总人数
	 
	   var zrs=0;//总人数
	   for(var j=0;j<array_row.length;j++){
		   if(DCellWeb_tj.IsRowHidden(parseInt(array_row[j].split(",")[0])-1,0)==false){//行显示
			   for(var n=parseInt(array_row[j].split(",")[0]);n<=parseInt(array_row[j].split(",")[1]);n++){//循环职务等级
					
					zj_temp_total=DCellWeb_tj.GetCellString2(i, n, 0);//当前行平均年龄
					if(zj_temp_total=='undefined' || zj_temp_total=="" || zj_temp_total==null||zj_temp_total==" "){
						zj_temp_total=0;
					}
					zj_temp_3=DCellWeb_tj.GetCellString2(tjxm_arr.length, n, 0);
				    //zj_temp_3=r_total_r(n);//当前行总人数
					if(zj_temp_3=='undefined' || zj_temp_3=="" || zj_temp_3==null||zj_temp_3==" "){
						zj_temp_3=0;
					}
					zrs=zrs+parseInt(zj_temp_3);
					zj_total=zj_total+parseInt(zj_temp_total)*parseInt(zj_temp_3);
			   }
		 	}
	   }
	 	if(zrs=='undefined' || zrs=="" || zrs==null||zrs==" "||zrs==0){
			zrs=1;
		}

		return returnFloat(Math.round(parseInt(zj_total)/parseInt(zrs)*100)/100);
		
}
//平均年龄对应人数
function r_total_r(row){
	   var zj_temp_3=0;
	   var zj_temp_cell=0;
	   for(var j=col_xho_start+1;j<=col_xho_end-1;j++){//列
			zj_temp_cell=DCellWeb_tj.GetCellString2(j, row, 0);
			if(zj_temp_cell=='undefined' || zj_temp_cell=="" || zj_temp_cell==null||zj_temp_cell==" "){
				zj_temp_cell=0;
			}
			zj_temp_3=zj_temp_3+parseInt(zj_temp_cell);
	 }
	   return zj_temp_3;
}
</script>
<script type="text/javascript">

function xianyin_onclick(obj){
	if(document.getElementById("xianyin").checked==true){//隐藏全零行
		document.getElementById("xy_zwlb").value=1;
		zwlb_xy();
	}else if(document.getElementById("xianyin").checked==false){//显示全零行
		document.getElementById("xy_zwlb").value=0;
		zwlb_xy();
	}
}
</script>
<script type="text/javascript">
function exportData(){
	DCellWeb_tj.ExportExcelDlg();
}
</script>