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
<%--统计列表 --%>
<%@include file="/pages/sysorg/org/gbjbqk/Gbjbqk_List.jsp" %>
<%--统计表格 --%>
<%@include file="/pages/sysorg/org/gbjbqk/GbjbqkSub_form.jsp" %>
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
	if(col1==0||col1==""||col1==" "||col1==null||col1=="undefined"||col1<3){
		return;
	}
	if(row1==0||row1==""||row1==" "||row1==null||row1=="undefined"||row1<4){
		return;
	}
	//数值为0 或 空
	var value=DCellWeb_tj.GetCellString(col1,row1,0);
	if(value==0||value==""||value==" "||value==null||value=="undefined"){
		return;
	}
	//占比
	if(col1>=col_xho_start&&col1<=col_xho_end){
		if(col1%2==1){
			return;
		}
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
	var id_weiyi="GbjbqkxwxlSub";//不可重复
	//用户id
	var useridh=document.getElementById("userid_h").value;
	id_weiyi=id_weiyi+useridh;
	//系统时间
	var date_time = new Date();
	var time=date_time.getHours()+ date_time.getMinutes()+date_time.getSeconds();
	id_weiyi=id_weiyi+time;
	$h.openWin(id_weiyi,'pages.sysorg.org.gbjbqk.GbjbqkxwxlSub',title+'-二维统计',1300,550,dwid,'<%=request.getContextPath()%>');
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
// //创建json对 
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
			//获取当前网址，如： http://localhost:8083/myproj/view/my.jsp
			var curWwwPath=window.document.location.href;   
			//获取主机地址之后的目录，如： myproj/view/my.jsp 
			var pathName=window.document.location.pathname;  
			var pos=curWwwPath.indexOf(pathName);  
			//获取主机地址，如： http://localhost:8083  
			var localhostPaht=curWwwPath.substring(0,pos);
			 //获取带"/"的项目名，如：/myproj  
			 var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);  
			 //得到了 http://localhost:8083/myproj  
			 var realPath=localhostPaht+projectName;  
			// // 使用刚指定的配置项和数据显示图表。 
			myChart.setOption({ 
				 
				title : {
			        text: title+"(学历学位)",// //标题
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
			    /* toolbox: {
		            show: true,
		            feature: {
		            	myTool2: { 
		            		show: true,
		            		title: '自定义扩展方法1',
		            		icon:'image://'+realPath+'/images/user.png', 
		            		onclick: function (){
		            			
		            			
		            			
		                	}
		            	}
		            }
		        }, */
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
			alert(err);
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
		//wenzi_func_ms();
		flag_cwz=true;//被点击标志
		
	}
	// //统计项目名称
	var tjxm_arr = new Array(
			"",
			"",
			"",
			"",
			"研究生(最高学历)",
			"",
			"大学本科(最高学历)",
			"",
			"大学专科(最高学历)",
			"",
			"中专(最高学历)",
			"",
			"高中及以下(最高学历)",
			"",
			"博士(最高学位)",
			"",
			"硕士(最高学位)",
			"",
			"学士(最高学位)",
			"",
			
			"研究生(全日制学历)",
			"",
			"大学本科(全日制学历)",
			"",
			"大学专科(全日制学历)",
			"",
			"中专(全日制学历)",
			"",
			"高中及以下(全日制学历)",
			"",
			"博士(全日制学位)",
			"",
			"硕士(全日制学位)",
			"",
			"学士(全日制学位)",
			"",
			
			"研究生(在职学历)",
			"",
			"大学本科(在职学历)",
			"",
			"大学专科(在职学历)",
			"",
			"中专(在职学历)",
			"",
			"高中及以下(在职学历)",
			"",
			"博士(在职学位)",
			"",
			"硕士(在职学位)",
			"",
			"学士(在职学位)",
			""
			);
	var wenzi_str="";
	// //文字
	function wenzi_func(){
		// //总计
		var obj=document.getElementById("wenzi");
		// //干部基本情况分析
		wenzi_str="<br><p style='width:100%;text-align:center;'><b>干部基本情况分析(学历学位)</b></p><br><b>基本情况:</b><br><br>"
		wenzi_str=wenzi_str+"　　我省共有公务员(学历学位)<font onclick='opensubowin(\"3\",\"4\")' style='cursor:hand;color:#75acd2;'> <u>"+DCellWeb_tj.GetCellString(3, 4, 0)+"</u> </font>人 其中: ";
		var num_wz=0;
		for(var i=col_xho_start;i<=col_xho_end;i++){//循环列
			   if(i%2==1){
					continue;
				}
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
	function zwlb_fz_func(start,end){// //start 起始行 end 结束行
		var v_wz_zwlb="";////职务层级名称 
		var v_temp_lb_zx=0;////cell小计值 
		var num_wz=0;////cell值 
		
		//小计 统计
		v_temp_lb_zx=DCellWeb_tj.GetCellString2(3, (parseInt(start)-1), 0);
		if(v_temp_lb_zx==0||v_temp_lb_zx==""||v_temp_lb_zx==null||v_temp_lb_zx==" "||v_temp_lb_zx=="undefined"){
			
		}else{
			var zwlb_mc=DCellWeb_tj.GetCellString2(1, parseInt(start), 0);//分类名称
			wenzi_str=wenzi_str+"<br><br><p style='width:100%;text-align:left;'><b>"+zwlb_mc+":</b></p><br>"
			wenzi_str=wenzi_str+"　　共有公务员<font onclick='opensubowin(\"3\",\""+(parseInt(start)-1)+"\")' style='cursor:hand;color:#75acd2;'> <u>"+v_temp_lb_zx+"</u> </font>人 其中: ";//小计
			
			for(var j=col_xho_start;j<=col_xho_end;j++){//列数
				 if(j%2==1){
						continue;
				}
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
				wenzi_str=wenzi_str+"　　共有公务员<font onclick='opensubowin(\"3\",\""+i+"\")' style='cursor:hand;color:#75acd2;'> <u>"+v_temp_lb_zx+"</u> </font>人 其中: ";//小计 
				for(var j=col_xho_start;j<=col_xho_end;j++){//列数
					 if(j%2==1){
							continue;
					}
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
		     var action = "GbjbqkDownServlet?method=Gbjbqkxwxl"; 
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
				document.getElementById("DCellWeb1").openfile(ctpath+"\\template\\gbjbqktjxlxw.cll","");
		    }catch(err){
		    	//alert("提示:请下载安装华表插件!并使用IE浏览器(兼容模式)打开(可联系系统管理员远程协助)!");
		    }
	   });
	
	//表格json数据
	var json_obj="";
	//初始化方法 
	var DCellWeb_tj="";
	var col_xho_start=4;
	var col_xho_end=tjxm_arr.length-1;
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
	   for(var i=0;i<obj.length;i++){////插入数据 行数 循环
	   	   var a0221=obj[i].a0221;//代码
	   	   if(a0221=='undefined' || a0221=="" || a0221==null||a0221==" "){
	   			continue;//类别代码为空
	   	   }
	   	   a0221=map[a0221];//行数
	   	   if(a0221=='undefined' || a0221=="" || a0221==null||a0221==" "){
	   			continue;//js 没有配置此类别代码
	   	   }
	   	   var i_col=4;
		   	DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].yjs==0?"":obj[i].yjs);//研究生
		   i_col=i_col+2;		
		   	DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].dxbk==0?"":obj[i].dxbk);//大学本科
		   	i_col=i_col+2;
		   	DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].dxzz==0?"":obj[i].dxzz);//大学专科
		   	i_col=i_col+2;
		   	DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].zz==0?"":obj[i].zz);//中专
		   	i_col=i_col+2;
		   	DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].gzjyx==0?"":obj[i].gzjyx);//高中及以下
		   			i_col=i_col+2;
		   	DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].bs==0?"":obj[i].bs);//博士
  		   	i_col=i_col+2;
  		   	DCellWeb_tj.SetCellString(
  		   		i_col,a0221,0,obj[i].ss==0?"":obj[i].ss);//硕士
  		   	i_col=i_col+2;
  		   	DCellWeb_tj.SetCellString(
  		   		i_col,a0221,0,obj[i].xs==0?"":obj[i].xs);//学士
  		   	//^^^^^^^^^^最高学历		
  		   	i_col=i_col+2;
  			DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].yjsq==0?"":obj[i].yjsq);//研究生
		   i_col=i_col+2;		
		   	DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].dxbkq==0?"":obj[i].dxbkq);//大学本科
		   	i_col=i_col+2;
		   	DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].dxzzq==0?"":obj[i].dxzzq);//大学专科
		   	i_col=i_col+2;
		   	DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].zzq==0?"":obj[i].zzq);//中专
		   	i_col=i_col+2;
		   	DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].gzjyxq==0?"":obj[i].gzjyxq);//高中及以下
		   			i_col=i_col+2;
		   	DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].bsq==0?"":obj[i].bsq);//博士
  		   	i_col=i_col+2;
  		   	DCellWeb_tj.SetCellString(
  		   		i_col,a0221,0,obj[i].ssq==0?"":obj[i].ssq);//硕士
  		   	i_col=i_col+2;
  		   	DCellWeb_tj.SetCellString(
  		   		i_col,a0221,0,obj[i].xsq==0?"":obj[i].xsq);//学士
		    //^^^^^^^^^^全日制
	   		i_col=i_col+2;
  			DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].yjsz==0?"":obj[i].yjsz);//研究生
		   i_col=i_col+2;		
		   	DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].dxbkz==0?"":obj[i].dxbkz);//大学本科
		   	i_col=i_col+2;
		   	DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].dxzzz==0?"":obj[i].dxzzz);//大学专科
		   	i_col=i_col+2;
		   	DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].zzz==0?"":obj[i].zzz);//中专
		   	i_col=i_col+2;
		   	DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].gzjyxz==0?"":obj[i].gzjyxz);//高中及以下
		   			i_col=i_col+2;
		   	DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].bsz==0?"":obj[i].bsz);//博士
  		   	i_col=i_col+2;
  		   	DCellWeb_tj.SetCellString(
  		   		i_col,a0221,0,obj[i].ssz==0?"":obj[i].ssz);//硕士
  		   	i_col=i_col+2;
  		   	DCellWeb_tj.SetCellString(
  		   		i_col,a0221,0,obj[i].xsz==0?"":obj[i].xsz);//学士
   			//^^^^^^^^^^^在职 
		   	//合计
		  
			DCellWeb_tj.SetCellString(
					3,a0221,0,obj[i].heji==0?"":obj[i].heji);
		}/// for end
		//小计
	   for(var i=0;i<array_row.length;i++){
			total_tj(parseInt(array_row[i].split(",")[0])-1,
					parseInt(array_row[i].split(",")[0]),
					parseInt(array_row[i].split(",")[1]));
		}
		//总计 
		total_zj();
		
		//占比
		zhanbi();
		//隐藏占比
		yincangzb();
		//隐藏 全行为0的行
		displayzeroinit()
		//设置页面不可编辑
		DCellWeb_tj.WorkbookReadonly=true;
		//柱状图 
		//setTimeout('zwlb_zzt()',100);
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
   
   //隐藏占比
   function yincangzb(){
	   for(var i=col_xho_start;i<=col_xho_end;i++){//循环列
		   if(i%2==0){
				continue;
			}
		   DCellWeb_tj.SetColHidden(i, i);
		}
   }
   
   //显示占比
   function xszhanbi(){
	   DCellWeb_tj.SetColUnhidden(col_xho_start, col_xho_end);
   }
   //占比 计算
   function zhanbi(){
	   var temp_hj=0;
	   var temp_col_num=0;
	   var biliz=0;
	   for(var j=4;j<=parseInt(array_row[array_row.length-1].split(",")[1]);j++){//循环行
		 	//合计
			temp_hj=DCellWeb_tj.GetCellString2(3, j, 0);
			if(temp_hj=='undefined' || temp_hj=="" || temp_hj==null||temp_hj==" "||temp_hj==0){
				temp_hj=1;
			}
			 for(var i=col_xho_start;i<=col_xho_end;i++){//循环列
				 if(i%2==0){
		 				continue;
		 		}
				 temp_col_num=DCellWeb_tj.GetCellString2(i-1, j, 0);
 				if(temp_col_num=='undefined' || temp_col_num=="" || temp_col_num==null||temp_col_num==" "){
 					temp_col_num=0;
 				}
 				biliz=Math.round(temp_col_num/temp_hj*10000)/100;
 				DCellWeb_tj.SetCellString(i,j,0,biliz==0?"":biliz+"%" );
			 }
			
	   }
   }
   
   function zhanbi_xy_zwlb(){
	   var temp_hj=0;
	   var temp_col_num=0;
	   var biliz=0;
	   for(var j=4;j<=4;j++){//循环行
		 	//合计
			temp_hj=DCellWeb_tj.GetCellString2(3, j, 0);
			if(temp_hj=='undefined' || temp_hj=="" || temp_hj==null||temp_hj==" "||temp_hj==0){
				temp_hj=1;
			}
			for(var i=col_xho_start;i<=col_xho_end;i++){//循环列
				if(i%2==0){
	 				continue;
	 			}
				temp_col_num=DCellWeb_tj.GetCellString2(i-1, j, 0);
 				if(temp_col_num=='undefined' || temp_col_num=="" || temp_col_num==null||temp_col_num==" "){
 					temp_col_num=0;
 				}
 				biliz=Math.round(temp_col_num/temp_hj*10000)/100;
 				DCellWeb_tj.SetCellString(i,j,0,biliz==0?"":biliz+"%" );
			}
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
  			if(i%2==1){//奇数列
  				continue;
  			}
  			_zj_total=total_zj_ittt(i);
		    //偶数列 小计
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
		return zj_total;
   }
  
	//统计小计
	function total_tj(row_xj,row_s,row_e){//row_xj 小计统计行 row_s开始行 row_e 结束行
		
		//第3列单独计算
		var temp_v_r=parseInt(col_total(3,row_s,row_e));
		DCellWeb_tj.SetCellString(
   			3,row_xj,0,temp_v_r==0?"":temp_v_r );
		
		
		var temp_v="";
		for(var i=col_xho_start;i<=col_xho_end;i++){
   			if(i%2==1){//奇数列
   				continue;
   			}
		    //偶数列 小计
   			
   			temp_v=parseInt(col_total(i,row_s,row_e));
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
 		
 	    return col_num;
	}
  
  //隐藏所有行从4行开始
  function yx_all(){
	  DCellWeb_tj.SetRowHidden(4, parseInt(array_row[array_row.length-1].split(",")[1]));
  }
  
  function displayzeroinit(){
	  var v=0;
	  var i_v=parseInt(array_row[array_row.length-1].split(",")[1]);
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
	DCellWeb_tj.SetRowUnhidden(4, 163);
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
		  zhanbi_xy_zwlb();
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
		  zhanbi_xy_zwlb();
		  DCellWeb_tj.SetRowUnhidden(4, 4);//显示
		  //zhanbi();//重新统计
		  return;
	  }
	  var strs= new Array(); //定义一数组 存储职务类别代码 
	  var strs_m= new Array(); //定义一数组  存储行数 
	  strs=zwlb_data.split(","); //字符分割
	  DCellWeb_tj.SetRowhidden(4, 163);//隐藏所有行
	  for (var i=0;i<strs.length ;i++ ) { 
		  strs_m=map_zwlb[strs[i]].split(",");
		  DCellWeb_tj.SetRowUnhidden(strs_m[0], strs_m[1]);//显示
		  //小计
		  DCellWeb_tj.SetRowUnhidden(parseInt(strs_m[0])-1, parseInt(strs_m[0])-1);//显示
		  //重新统计
		  DCellWeb_tj.SetRowUnhidden(4, 4);//显示
		  total_zj_xy();//总计
		  zhanbi_xy_zwlb();
		  //zhanbi();//重新统计占比
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
	  for(var i=(col_xho_start-1);i<=col_xho_end;i++){
		  DCellWeb_tj.SetCellString(i,4,0,'');
	  }
  }
  
//总计
  function total_zj_xy(){
	   //第3列单独统计 
	   var temp_zj_=parseInt(total_zj_ittt_xy(3));
		DCellWeb_tj.SetCellString(
  			3,4,0,temp_zj_==0?"":temp_zj_ );
		
	   var _zj_total=0;
	   for(var i=col_xho_start;i<=col_xho_end;i++){
 			if(i%2==1){//奇数列
 				continue;
 			}
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
	 
		return zj_total;
  }
</script>
