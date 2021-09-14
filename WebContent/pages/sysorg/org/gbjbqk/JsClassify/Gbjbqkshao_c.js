// //统计项目名称
	var tjxm_arr = new Array(
			"",
			"",
			"",
			"",
			"女",
			"",
			/* "少数民族",
			"", */
			"中共党员",
			"",
			"非中共党员",
			"",
			"研究生",
			"",
			"大学本科",
			"",
			"大学专科",
			"",
			"中专",
			"",
			"高中及以下",
			"",
			"博士",
			"",
			"硕士",
			"",
			"学士",
			"",
			"35岁及以下",
			"",
			"36岁至40岁",
			"",
			"41岁至45岁",
			"",
			"46岁至50岁",
			"",
			"51岁至54岁",
			"",
			"55岁至59岁",
			"",
			"60岁及以上",
			"",
			"平均年龄",
			"不满1年",
			"",
			"1年至不满3年",
			"",
			"3年至不满5年",
			"",
			"5年至不满10年",
			"",
			"10年及以上",
			"",
			"具有2年以上基层工作经历"
			);

	Ext.onReady(function openfile(){
		    try{
				document.getElementById("DCellWeb1").openfile(ctpath+"\\template\\gbjbqktjshao.cll","");
		    }catch(err){
		    	//alert("提示:请下载安装华表插件!并使用IE浏览器(兼容模式)打开(可联系系统管理员远程协助)!");
		    }
	   });
	//表格json数据
	var json_obj="";
	//初始化方法 
	var DCellWeb_tj="";
	var col_xho_start=4;
	var col_xho_end=36+4;
	var col_xhj_start=37+4;
	var col_xhj_end=47+4;
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
	   				i_col,a0221,0,obj[i].nv==0?"":obj[i].nv);//女
		   	/* DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].sm==0?"":obj[i].sm);//少数民族 */
		   i_col=i_col+2;		
		   	DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].zd==0?"":obj[i].zd);//中共党员
		   	i_col=i_col+2;
		   	DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].fzd==0?"":obj[i].fzd);//非中共党员
		   	i_col=i_col+2;
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
 		   	i_col=i_col+2;
 		   	DCellWeb_tj.SetCellString(
 		   		i_col,a0221,0,obj[i].xydy35==0?"":obj[i].xydy35);//35岁及以下
			i_col=i_col+2;
			DCellWeb_tj.SetCellString(
					i_col,a0221,0,obj[i].dy36xy40==0?"":obj[i].dy36xy40);//36岁至40岁
			i_col=i_col+2;
			DCellWeb_tj.SetCellString(
					i_col,a0221,0,obj[i].dy41xy45==0?"":obj[i].dy41xy45);//41岁至45岁
			i_col=i_col+2;
			DCellWeb_tj.SetCellString(
					i_col,a0221,0,obj[i].dy46xy50==0?"":obj[i].dy46xy50);//46岁至50岁
			i_col=i_col+2;
			DCellWeb_tj.SetCellString(
					i_col,a0221,0,obj[i].dy51xy54==0?"":obj[i].dy51xy54);//51岁至54岁
			i_col=i_col+2;
			DCellWeb_tj.SetCellString(
					i_col,a0221,0,obj[i].dy55xy59==0?"":obj[i].dy55xy59);//55岁至59岁
			i_col=i_col+2;
		    DCellWeb_tj.SetCellString(
		    		i_col,a0221,0,obj[i].dy60==0?"":obj[i].dy60);//60岁及以上
	   		i_col=i_col+2;
	   		DCellWeb_tj.SetCellString(
	   				i_col,a0221,0,obj[i].pjnl==0?"":obj[i].pjnl);//平均年龄
		   	i_col=i_col+1;
		   	DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].zhccxy1==0?"":obj[i].zhccxy1);//不满1年
		   	i_col=i_col+2;
		   	DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].zhccxy3==0?"":obj[i].zhccxy3);//1年至不满3年
		   	i_col=i_col+2;
		    DCellWeb_tj.SetCellString(
		    		i_col,a0221,0,obj[i].zhccxy5==0?"":obj[i].zhccxy5);//3年至不满5年
		   	i_col=i_col+2;
		   	DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].zhccxy10==0?"":obj[i].zhccxy10);//5年至不满10年
		   	i_col=i_col+2;
		   	DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].zhccxy11==0?"":obj[i].zhccxy11);//10年及以上
		   	i_col=i_col+2;		
		   			DCellWeb_tj.SetCellString(
		   			i_col,a0221,0,obj[i].a0197==0?"":obj[i].a0197);//是否具有两年以上基层工作经历
		   	//合计
		   	// 中共党员
		   	var zg=obj[i].zd==0?"":obj[i].zd;
		   	if(zg=='undefined' || zg=="" || zg==null||zg==" "){
		   		zg=0;
		   	}
		   	//非中共党员
		   	var fzg=obj[i].fzd==0?"":obj[i].fzd;
			if(fzg=='undefined' || fzg=="" || fzg==null||fzg==" "){
				fzg=0;
		   	}
			DCellWeb_tj.SetCellString(
		   			3,a0221,0, parseInt(zg)+parseInt(fzg));
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
		tjfx_dj_flag=true;//点击统计分析标志 
		cwenzi();//文字 
		ctable();//显示表格
		document.getElementById( "bar_div").style.display= "block";//显示工具条
		//重新设置grid高度
		try{
			//var grid = odin.ext.getCmp('gridfc');
			//grid.setHeight(487);
		}catch(err){
			
		}
		
		DCellWeb_tj.SetCellString(
				51,2,0,"具有2年以上基层工作经历");
		//setTimeout(ctable,500);
   }