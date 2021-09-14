<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ taglib uri="/WEB-INF/odin.tld" prefix="odin"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<script type="text/javascript" src="<%=request.getContextPath() %>/basejs/helperUtil.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/jquery-1.4.2.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/picCut/js/echarts.js" charset="utf-8"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/pages/sysorg/org/gbjbqk/gbjbqk.js" ></script>
<%@include file="/comOpenWinInit.jsp" %>

<%--页面最上方工具条 导出-表格-文字-饼状图 --%>
<%@include file="/pages/sysorg/org/gbjbqk/Gbjbqk_ToolBar.jsp" %>

<!-- <div style="height:3px"></div> -->
<div id="createtable" style="overflow-y:hidden;overflow-x:hi;overflow: auto;margin-left:15px;display:none;">
	<table  style="width:100%;" border="0">
		<col width="5%">
		<col width="15%">
		<col width="80%">
		<tr>
			<odin:select property="zwlb" label="职务类别" codeType="zwcelb" multiSelectWithAll="true" onchange="zwlb_func()"></odin:select>
			<td>
				<odin:checkbox property="xianyin" label="隐藏全零行" value="1"></odin:checkbox>
			</td>
			
		</tr>
	</table>
	<table style="width:100%;" border="0">
		
		<tr>
			<td colspan="3">
				<div>
					<object id="DCellWeb1" style="left: 0px; width: 1250px; top: 0px; height: 400px" 
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
<%--统计列表 --%>
<%@include file="/pages/sysorg/org/gbjbqk/Gbjbqk_List.jsp" %>
<%--统计饼状图 --%>
<%@include file="/pages/sysorg/org/gbjbqk/Gbjbqk_Bzt.jsp" %>
<%--文字与隐藏字段 --%>
<%@include file="/pages/sysorg/org/gbjbqk/Gbjbqk_sub_WAndHT.jsp" %>
<odin:window src="/" id="gbjbqktjsubid" width="1300" height="550"></odin:window>
<script for="DCellWeb1" event="MouseDClick(col, row)" language="JavaScript">
myMouseDClick(col, row);
</script>
<script type="text/javascript" language="javascript">
try{
	setAuth(document.getElementById("DCellWeb1"));
}catch(err){
	
}
</script>
<script type="text/javascript">
var tjfx_dj_flag=false;
function initTj_func(){
	if(tjfx_dj_flag==false){
		radow.doEvent("initTj");
	}else{
		ctable();//显示表格
	}
}
////表格双击事件 
function myMouseDClick(col1, row1) {
	//参数错误 
	if(col1==0||col1==""||col1==" "||col1==null||col1=="undefined"||col1<3||col1>49){
		return;
	}
	if(row1==0||row1==""||row1==" "||row1==null||row1=="undefined"||row1<4||row1>parseInt(array_row[array_row.length-1].split(",")[1])){
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
	if(row1==4){//总计
		title1=DCellWeb_tj.GetCellString(1,row1,0);
	}
	if(col1==3){
		title2=DCellWeb_tj.GetCellString(col1,2,0);
	}else{
		title2=DCellWeb_tj.GetCellString(col1,3,0);
	}
	var title=title1+"-"+title2;
	
	var dwid=document.getElementById("subWinIdBussessId2").value;
	if(dcbz_flag==2){//文字显示标志
		dwid=dwid+"$"+''+"$"+col1+"$"+row1+"$"+title;
	}else{
		dwid=dwid+"$"+$('#zwlb_combo').attr("value")+"$"+col1+"$"+row1+"$"+title;
	}
	//dwid=dwid+"$"+col1+"$"+row1+"$"+title;
	
	//alert(dwid);
	var id_weiyi="GbjbqknlSub";//不可重复
	//用户id
	var useridh=document.getElementById("userid_h").value;
	id_weiyi=id_weiyi+useridh;
	//系统时间
	var date_time = new Date();
	var time=date_time.getHours()+ date_time.getMinutes()+date_time.getSeconds();
	id_weiyi=id_weiyi+time;
	$h.openWin(id_weiyi,'pages.sysorg.org.gbjbqk.GbjbqknlSub',title+'-二维统计',1300,550,dwid,'<%=request.getContextPath()%>');
}

// //文字单击事件 
function opensubowin(col1, row1){
	myMouseDClick(col1, row1);
}


// //综合管理类公务员 
var zwlb_zhgll_mc=[];// //职务层次名称 
var zhgll_json=[];// //职务层次名称 与 数量 
var title="";// //职务类别 
function zhgll_func(start,end){// //start 开始行 end结束行
	var value="";
	var name="";
	zhgll_json=[];
	zwlb_zhgll_mc=[];
	for(var i=start;i<=end;i++){
		 value=DCellWeb_tj.GetCellString(3,i,0);
		 name=DCellWeb_tj.GetCellString(2,i,0);
		if(value=="" || value==" " ||value=="undefined"||value==null){
			 value=0;
		 }
		zwlb_zhgll_mc.push(name);
		 zhgll_json.push(CreateJson(value,name)); 
	 }
	title=DCellWeb_tj.GetCellString(1,start,0);
}
////创建json对 
function CreateJson(value,name){// //JS 里面是不需要参数属性的 
	var jsonStr = {};
	jsonStr.value = value;
	jsonStr.name = name;
	return jsonStr;
}

	// 饼状图表实现 
	function setbingzhuang(ii){
		// // 基于准备好的dom，初始化echarts实例
		try{
			var myChart = echarts.init(document.getElementById('bingzhuang'+ii));
			// // 使用刚指定的配置项和数据显示图表。 
			myChart.setOption({ 
				 
				title : {
			        text: title+"(年龄)",// //标题
			        subtext: '',
			        x:'center'
			    },
			    tooltip : {
			        trigger: 'item',
			        formatter: "{a} <br/>{b} : {c} ({d}%)"
			    },
			    legend: {
			        orient: 'vertical',
			        left: 'left',
			        data: zwlb_zhgll_mc// //统计类别
			    },
			    series : [
			        {
			            name: '公务员汇总系统',
			            type: 'pie',
			            radius : '55%',
			            center: ['50%', '60%'],
			            data:zhgll_json,// //饼图数组
			            itemStyle: {
			                emphasis: {
			                    shadowBlur: 10,
			                    shadowOffsetX: 0,
			                    shadowColor: 'rgba(0, 0, 0, 0.5)'
			                },
					        normal:{ 
				                 label:{ 
				                     show: true, 
				                     formatter: '{b} : {c} ({d}%)' 
				                 }, 
				                 labelLine :{show:true} 
				             }
			            }
			        }
			    ]
				
				});
		}catch(err){
			
		}
	}

	// 全部隐藏 
	function def(){
		document.getElementById("wenzi").style.display='none';
		document.getElementById("bingzhuang").style.display='none';
		document.getElementById("createtable").style.display='none';
		document.getElementById("fancha_div").style.display='none';
	}
	// 列表 
	function liebiao_l(){
		def();
		document.getElementById("fancha_div").style.display='block';
		dcbz_flag=4;//此 tab 显示标志
	}
	function loding(){
		ctable();
	}
	// 显示表格 
	function ctable(){
		def();
		document.getElementById("createtable").style.display='block';
		dcbz_flag=1;//此 tab 显示标志
	}
	// 显示柱状图表 
	var flaf_cbz=false;
	function cbingzhuang(){
		def();
		document.getElementById("bingzhuang").style.display='block';
		dcbz_flag=3;//此 tab 显示标志
		if(flaf_cbz==true){
			return;
		}
		if(tjfx_dj_flag==false){
			return;
		}
		for(var j=0;j<array_row.length;j++){
			zhgll_func(parseInt(array_row[j].split(",")[0]),parseInt(array_row[j].split(",")[1]));//生成数据 
			
			setbingzhuang((j+1)+'');//生成饼图
		}
		flaf_cbz=true;
		
	}
	// 显示文字 
	var flag_cwz=false;
	function cwenzi(){
		def();
		document.getElementById("wenzi").style.display='block';
		dcbz_flag=2;//此 tab 显示标志
		if(flag_cwz==true){
			return;
		}
		if(tjfx_dj_flag==false){
			return;
		}
		//wenzi_func();
		
		flag_cwz=true;//被点击标志
		
	}
	// //统计项目名称
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
	var wenzi_str="";
	// //文字
	function wenzi_func(){
		// //总计
		var obj=document.getElementById("wenzi");
		// //干部基本情况分析
		wenzi_str="<br><p style='width:100%;text-align:center;'><b>干部基本情况分析(年龄)</b></p><br><b>基本情况:</b><br><br>"
		wenzi_str=wenzi_str+"　　共(年龄)<font onclick='opensubowin(\"3\",\"4\")' style='cursor:hand;color:#75acd2;'> <u>"+DCellWeb_tj.GetCellString(3, 4, 0)+"</u> </font>人 其中: ";
		var num_wz=0;
		for(var i=col_xho_start+1;i<=col_xho_end;i++){//循环列
			   num_wz=DCellWeb_tj.GetCellString2(i, 4, 0);
				if(num_wz==0||num_wz==""||num_wz==null||num_wz==" "||num_wz=="undefined"){
					
				}else{//#75acd2
					wenzi_str=wenzi_str+tjxm_arr[i]+"<font onclick='opensubowin(\""+i+"\",\"4\")' style='cursor:hand;color:#75acd2;'> <u>"+num_wz+"</u> </font>"+"人 "
				}
		}

		wenzi_str=wenzi_str+"<br>";
		for(var i=0;i<array_row.length;i++){
			zwlb_fz_func(parseInt(array_row[i].split(",")[0]),parseInt(array_row[i].split(",")[1]));
		}
		// //文字
		obj.innerHTML=wenzi_str;
	}
	////文字统计 循环类别 
	function zwlb_fz_func(start,end){//start 起始行 end 结束行
		var v_wz_zwlb="";////职务层级名称 
		var v_temp_lb_zx=0;////cell小计值 
		var num_wz=0;////cell值 
		
		//小计 统计
		v_temp_lb_zx=DCellWeb_tj.GetCellString2(3, (parseInt(start)-1), 0);
		if(v_temp_lb_zx==0||v_temp_lb_zx==""||v_temp_lb_zx==null||v_temp_lb_zx==" "||v_temp_lb_zx=="undefined"){
			
		}else{
			var zwlb_mc=DCellWeb_tj.GetCellString2(1, parseInt(start), 0);//分类名称
			wenzi_str=wenzi_str+"<br><br><p style='width:100%;text-align:left;'><b>"+zwlb_mc+":</b></p><br>"
			wenzi_str=wenzi_str+"　　共<font onclick='opensubowin(\"3\",\""+(parseInt(start)-1)+"\")' style='cursor:hand;color:#75acd2;'> <u>"+v_temp_lb_zx+"</u> </font>人 其中: ";//小计
			
			for(var j=col_xho_start+1;j<=col_xho_end;j++){//列数
				num_wz=DCellWeb_tj.GetCellString2(j, parseInt(start-1), 0);
				if(num_wz==0||num_wz==""||num_wz==null||num_wz==" "||num_wz=="undefined"){
					//为0不统计
				}else{//不为0统计
					wenzi_str=wenzi_str+tjxm_arr[j]+"<font onclick='opensubowin(\""+j+"\",\""+parseInt(start-1)+"\")' style='cursor:hand;color:#75acd2;'> <u>"+num_wz+"</u> </font>"+"人 "
				}
			}
			wenzi_str=wenzi_str+"<br><br>";
		}
		//wenzi_str=wenzi_str+"<br>";
		//具体统计 
		var v_wz_zwlb="";////职务层级名称 
		var v_temp_lb_zx=0;////cell小计值 
		var num_wz=0;////cell值 
		for(var i=start;i<=end;i++){// //行数 
			v_temp_lb_zx=DCellWeb_tj.GetCellString2(3, i, 0);
			if(v_temp_lb_zx==0||v_temp_lb_zx==""||v_temp_lb_zx==null||v_temp_lb_zx==" "||v_temp_lb_zx=="undefined"){
				////小计为0,不统计 
			}else{////小计不为零 
				v_wz_zwlb="<b>"+DCellWeb_tj.GetCellString2(2, i, 0)+"</b>";//职务层级名称 
				wenzi_str=wenzi_str+v_wz_zwlb+":<br><br>"
				wenzi_str=wenzi_str+"　　共<font onclick='opensubowin(\"3\",\""+i+"\")' style='cursor:hand;color:#75acd2;'> <u>"+v_temp_lb_zx+"</u> </font>人 其中: ";//小计 
				for(var j=col_xho_start+1;j<=col_xho_end;j++){//列数
					num_wz=DCellWeb_tj.GetCellString2(j, i, 0);
					if(num_wz==0||num_wz==""||num_wz==null||num_wz==" "||num_wz=="undefined"){
						//为0不统计
					}else{//不为0统计
						wenzi_str=wenzi_str+tjxm_arr[j]+"<font onclick='opensubowin(\""+j+"\",\""+i+"\")' style='cursor:hand;color:#75acd2;'> <u>"+num_wz+"</u> </font>"+"人 "
					}
				}
				wenzi_str=wenzi_str+"<br><br>";
			}
			
		}
	}

	// 导出数据 
	var dcbz_flag=1;//当前显示页面 1 表格
	
	function exportData(){
		if(dcbz_flag==1){//表格
			if(tjfx_dj_flag==false){//没有点击统计分析，数据为空，不给导出
				return;
			}
			DCellWeb_tj.ExportExcelDlg();
		}else if(dcbz_flag==2){//文字
			if(tjfx_dj_flag==false){//没有点击统计分析，数据为空，不给导出
				return;
			}
			
			 var tmp = document.createElement("form"); 
		     var action = "GbjbqkDownServlet?method=Gbjbqknl"; 
		     tmp.action = action; 
		     tmp.method = "post"; 
		     tmp.target="_blank";
		     
		     var newElement = document.createElement("input");
		     newElement.setAttribute("name","wenzims_str");
		     newElement.setAttribute("type","hidden");
		     newElement.setAttribute("value",wenzi_str);
		     tmp.appendChild(newElement); 
		     
		     document.body.appendChild(tmp); 
		     tmp.submit();
		     return tmp;
			//window.open("GbjbqkDownServlet?method=Gbjbqk");
		}else if(dcbz_flag==3){//饼状图
			/* window.open("ProblemDownServlet?method=downFiletj2"); */
		}else if(dcbz_flag==4){//列表
			
		}
		
	}
	var ctpath="<%=request.getContextPath()%>";
	Ext.onReady(function openfile(){
		    try{
				document.getElementById("DCellWeb1").openfile(ctpath+"\\template\\gbjbqktjnl.cll","");
		    }catch(err){
		    	//alert("提示:请下载安装华表插件!并使用IE浏览器(兼容模式)打开(可联系系统管理员远程协助)!");
		    }
	   });
	
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
	   
		DCellWeb_tj.InsertCol (DCellWeb_tj.GetCols( 0 ), 1, 0);
		DCellWeb_tj.SetColHidden(DCellWeb_tj.GetCols( 0 )-1,DCellWeb_tj.GetCols( 0 )-1);
	   for(var i=0;i<obj.length;i++){////插入数据 行数 循环
	   	   var a0221=obj[i].a0221;//代码
	   	   if(a0221=='undefined' || a0221=="" || a0221==null||a0221==" "){
	   			continue;//类别代码为空
	   	   }
	   	   a0221=map[a0221];//行数
	   	   if(a0221=='undefined' || a0221=="" || a0221==null||a0221==" "){
	   			continue;//js 没有配置此类别代码
	   	   }
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
  		   		i_col,a0221,0,obj[i].pjnl==0?"":obj[i].pjnl);//
  		   	i_col=i_col+1;
  		   	DCellWeb_tj.SetCellString(
  		   		i_col,a0221,0,obj[i].heji_zs==0?"":obj[i].heji_zs);//
		}/// for end
		
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
	
		tjfx_dj_flag=true;//点击统计分析标志 
		ctable();//显示表格
		document.getElementById( "bar_div").style.display= "block";//显示工具条
		//重新设置grid高度
		try{
			//var grid = odin.ext.getCmp('gridfc');
			//grid.setHeight(487);
		}catch(err){
			
		}
		wenzi_func();
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
				zj_temp_3=r_total_r(n);
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

		return Math.round(parseInt(zj_total)/parseInt(zrs)*100)/100;
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
			col_num=Math.round(parseInt(znl)/parseInt(zrs)*100)/100;
		}
	    return col_num;
	}
 
 //隐藏所有行从4行开始
 function yx_all(){
	  DCellWeb_tj.SetRowHidden(4, parseInt(array_row[array_row.length-1].split(",")[1]));
 }
 
 function displayzeroinit(){
	  var v=0;
		 for(var i=4;i<=parseInt(array_row[array_row.length-1].split(",")[1]);i++){//行
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
	DCellWeb_tj.SetRowUnhidden(4, parseInt(array_row[array_row.length-1].split(",")[1]));
 }
 function xs_zwlb_zero(){
	  zwlb_xy();
 }
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
	  DCellWeb_tj.SetRowhidden(4, parseInt(array_row[array_row.length-1].split(",")[1]));//隐藏所有行
	  for (var i=0;i<strs.length ;i++ ) { 
		  strs_m=map_zwlb[strs[i]].split(",");
		  DCellWeb_tj.SetRowUnhidden(strs_m[0], strs_m[1]);//显示
		  //小计
		  DCellWeb_tj.SetRowUnhidden(parseInt(strs_m[0])-1, parseInt(strs_m[0])-1);//显示
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
				    zj_temp_3=r_total_r(n);//当前行总人数
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

		return Math.round(parseInt(zj_total)/parseInt(zrs)*100)/100;
		
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
<odin:hidden property="tran_length"/>