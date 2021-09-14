////文字单击事件 
function opensubowin(col1, row1){
	myMouseDClick(col1, row1);
}

//全部隐藏 
function def(){
	document.getElementById("wenzi").style.display='none';
	document.getElementById("bingzhuang").style.display='none';
	document.getElementById("fancha_div").style.display='none';
	document.getElementById("createtable").style.display='none';
}
//导出数据 
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
	     var action = "GbjbqkDownServlet?method=Gbjbqk"; 
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
	}else if(dcbz_flag==3){//饼状图
		
		/* window.open("ProblemDownServlet?method=downFiletj2"); */
	}else if(dcbz_flag==4){//列表
		
	}
}
//显示文字 
var flag_cwz=false;
function cwenzi(){
	def();
	document.getElementById("wenzi").style.display='block';
	dcbz_flag=2;//此 tab 显示标志
	if(flag_cwz==true){
		return;
	}
	if(tjfx_dj_flag==false){//未统计
		return;
	}
	wenzi_func();
	flag_cwz=true;//被点击标志
}
var wenzi_str="";	// //文字
function wenzi_func(){
	// //总计
	var obj=document.getElementById("wenzi");
	// //干部基本情况分析
	wenzi_str="<br><p style='width:100%;text-align:center;'><b>干部基本情况分析"+title_zj+"</b></p><br><b>基本情况:</b><br><br>"
	wenzi_str=wenzi_str+"　　共"+title_zj+"<font onclick='opensubowin(\"3\",\"4\")' style='cursor:hand;color:#75acd2;'> <u>"+DCellWeb_tj.GetCellString(3, 4, 0)+"</u> </font>人 其中: ";
	var num_wz=0;
	for(var i=col_xho_start;i<=col_xho_end;i++){//循环列
		   if(i%2==1){
				continue;
			}
		   num_wz=DCellWeb_tj.GetCellString2(i, 4, 0);
			if(num_wz==0||num_wz==""||num_wz==null||num_wz==" "||num_wz=="undefined"){
				
			}else{//#75acd2
				if(col_xho_end==i){
					wenzi_str=wenzi_str+(tjxm_arr[i]=="女"?"女性":tjxm_arr[i])+"<font onclick='opensubowin(\""+i+"\",\"4\")' style='cursor:hand;color:#75acd2;'> <u>"+num_wz+"</u> </font>"+"岁 "
				}else{
					wenzi_str=wenzi_str+(tjxm_arr[i]=="女"?"女性":tjxm_arr[i])+"<font onclick='opensubowin(\""+i+"\",\"4\")' style='cursor:hand;color:#75acd2;'> <u>"+num_wz+"</u> </font>"+"人 "
				}
			}
	}
	for(var i=col_xhj_start;i<=col_xhj_end;i++){//循环列
		   if(i%2==0){
				continue;
			}
		   num_wz=DCellWeb_tj.GetCellString2(i, 4, 0);
			if(num_wz==0||num_wz==""||num_wz==null||num_wz==" "||num_wz=="undefined"){
				
			}else{//#75acd2
				wenzi_str=wenzi_str+(tjxm_arr[i]=="女"?"女性":tjxm_arr[i])+"<font onclick='opensubowin(\""+i+"\",\"4\")' style='cursor:hand;color:#75acd2;'> <u>"+num_wz+"</u> </font>"+"人 "
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
		
		for(var j=col_xho_start;j<=col_xho_end;j++){//列数
			 if(j%2==1){
					continue;
			}
			num_wz=DCellWeb_tj.GetCellString2(j, parseInt(start-1), 0);
			if(num_wz==0||num_wz==""||num_wz==null||num_wz==" "||num_wz=="undefined"){
				//为0不统计
			}else{//不为0统计
				if(col_xho_end==j){
					wenzi_str=wenzi_str+(tjxm_arr[j]=="女"?"女性":tjxm_arr[j])+"<font onclick='opensubowin(\""+j+"\",\""+parseInt(start-1)+"\")' style='cursor:hand;color:#75acd2;'> <u>"+num_wz+"</u> </font>"+"岁 "
				}else{
					wenzi_str=wenzi_str+(tjxm_arr[j]=="女"?"女性":tjxm_arr[j])+"<font onclick='opensubowin(\""+j+"\",\""+parseInt(start-1)+"\")' style='cursor:hand;color:#75acd2;'> <u>"+num_wz+"</u> </font>"+"人 "
				}
			}
		}
		for(var j=col_xhj_start;j<=col_xhj_end;j++){//循环列
			if(j%2==0){
				continue;
			}
			num_wz=DCellWeb_tj.GetCellString2(j, parseInt(start-1), 0);
			if(num_wz==0||num_wz==""||num_wz==null||num_wz==" "||num_wz=="undefined"){
				//为0不统计
			}else{//不为0统计
				wenzi_str=wenzi_str+(tjxm_arr[j]=="女"?"女性":tjxm_arr[j])+"<font onclick='opensubowin(\""+j+"\",\""+parseInt(start-1)+"\")' style='cursor:hand;color:#75acd2;'> <u>"+num_wz+"</u> </font>"+"人 "
			}
		}
		wenzi_str=wenzi_str+"<br><br>";
	}
	wenzi_str=wenzi_str+"";
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
			for(var j=col_xho_start;j<=col_xho_end;j++){//列数
				 if(j%2==1){
						continue;
				}
				num_wz=DCellWeb_tj.GetCellString2(j, i, 0);
				if(num_wz==0||num_wz==""||num_wz==null||num_wz==" "||num_wz=="undefined"){
					//为0不统计
				}else{//不为0统计
					if(col_xho_end==j){
						wenzi_str=wenzi_str+(tjxm_arr[j]=="女"?"女性":tjxm_arr[j])+"<font onclick='opensubowin(\""+j+"\",\""+i+"\")' style='cursor:hand;color:#75acd2;'> <u>"+num_wz+"</u> </font>"+"岁 "
					}else{
						wenzi_str=wenzi_str+(tjxm_arr[j]=="女"?"女性":tjxm_arr[j])+"<font onclick='opensubowin(\""+j+"\",\""+i+"\")' style='cursor:hand;color:#75acd2;'> <u>"+num_wz+"</u> </font>"+"人 "
					}
				}
			}
			for(var j=col_xhj_start;j<=col_xhj_end;j++){//循环列
				if(j%2==0){
					continue;
				}
				num_wz=DCellWeb_tj.GetCellString2(j, i, 0);
				if(num_wz==0||num_wz==""||num_wz==null||num_wz==" "||num_wz=="undefined"){
					//为0不统计
				}else{//不为0统计
					wenzi_str=wenzi_str+(tjxm_arr[j]=="女"?"女性":tjxm_arr[j])+"<font onclick='opensubowin(\""+j+"\",\""+i+"\")' style='cursor:hand;color:#75acd2;'> <u>"+num_wz+"</u> </font>"+"人 "
				}
			}
			wenzi_str=wenzi_str+"<br><br>";
		}
		
	}
}
//饼状图表实现 
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
		        text: title+title_zj,// //标题
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
		    /*toolbox: {
            show: true,
            feature: {
            	myTool2: { 
            		show: true,
            		title: '自定义扩展方法1',
            		icon:'image://'+realPath+'/images/user.png', 
            		onclick: function (){
            			

            			try{
            				//zhgll_func(start,end);
            				//alert(zhgll_json);
            				wenzi_str=document.getElementById('bingzhuang'+ii).outerHTML;
            				var tmp = document.createElement("form"); 
            			     var action = "GbjbqkDownServlet?method=Gbjbqk"; 
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
            			}catch(err){
            				alert(err);
            			}
                	}
            	}
            }
        },*/
		    series : [
		        {
		            name: '公务员汇总系统 ',
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
//显示柱状图表 
var flaf_cbz=false;
function cbingzhuang(){
	def();
	document.getElementById("bingzhuang").style.display='block';
	dcbz_flag=3;//此 tab 显示标志
	if(flaf_cbz==true){
		return;
	}
	if(tjfx_dj_flag==false){//未统计
		return;
	}
	for(var j=0;j<array_row.length;j++){
		zhgll_func(parseInt(array_row[j].split(",")[0]),parseInt(array_row[j].split(",")[1]));//生成数据 
		
		setbingzhuang((j+1)+'');//生成饼图
	}
	flaf_cbz=true;//此 tab被点击标志
}

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
//列表 
function liebiao_l(){
	def();
	document.getElementById("fancha_div").style.display='block';
	dcbz_flag=4;//此 tab 显示标志
}
