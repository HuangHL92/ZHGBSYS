var tjfx_dj_flag=false;//统计分析点击标志
function initTj_func(){//点击统计分析
	if(tjfx_dj_flag==false){
		radow.doEvent("initTj");
	}else{
		ctable();//显示表格
	}
}
//显示表格 
function ctable(){
	def();
	document.getElementById("createtable").style.display='block';
	dcbz_flag=1;//此 tab 显示标志
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
			
			temp_v=col_total(i,row_s,row_e);
			DCellWeb_tj.SetCellString(
	   			i,row_xj,0,temp_v==0?"":temp_v );
		}
	for(var i=col_xhj_start;i<=col_xhj_end;i++){
			if(i%2==0){//偶数列
				continue;
			}
	    //偶数列 小计
			
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
		
	   if(col_xj==col_xho_end){//平均年龄 为平均值 
		  var num_38_3=0;
		  var temp_38=0;//当前行平均年龄
		  var temp_3=0;//当前cell值
		  var num_38_3_a=0;//年龄之和
		  var num_rs=0;//总人数
		  var num_rs_a=0;
		  for(var i=row_s;i<=row_e;i++){//行
  			 temp_38=DCellWeb_tj.GetCellString2(col_xj, i, 0);//当前行平均年龄
  			if(temp_38==""||temp_38==" "||temp_38=="undefined"||temp_38==null){
  				temp_38=0;
  			}
  			 for(var j=(col_xho_end-10-4);j<=(col_xho_end-2);j++){//列 年龄
  				if(j%2==1){
  					 continue;
  				}
  				temp_3=DCellWeb_tj.GetCellString2(j, i, 0);//人数
 	  			if(temp_3==""||temp_3==" "||temp_3=="undefined"||temp_3==null){
 	  				temp_3=0;
 	  			}
	  			num_rs=num_rs+parseInt(temp_3);//当前行总人数
  			}
  			
  			num_rs_a=parseInt(num_rs_a)+parseInt(num_rs);//所有行总人数
  			 
  			num_38_3=parseInt(temp_38)*parseInt(num_rs);//当前行总年龄
  	
  			num_38_3_a=parseInt(num_38_3_a)+parseInt(num_38_3);//所有行总年龄
  			
  			num_rs=0;
  			num_38_3=0;
		  }
		if(num_rs_a==""||num_rs_a==" "||num_rs_a=="undefined"||num_rs_a==null||num_rs_a==0){
			num_rs_a=1;
		}
		 col_num=Math.round(parseInt(num_38_3_a)/parseInt(num_rs_a)*100)/100;
	   }
	   
	   return col_num;
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
	   for(var i=col_xhj_start;i<=col_xhj_end;i++){
			if(i%2==0){//
				continue;
			}
			_zj_total=total_zj_ittt(i);
		    //偶数列 小计
			DCellWeb_tj.SetCellString(i,4,0,_zj_total==0?"":_zj_total );
		}
}
//叠加行值
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
			   if(zj_temp_3=='undefined' || zj_temp_3=="" || zj_temp_3==null||zj_temp_3==" "){
				   zj_temp_3=0;
			   }
			   zrs=zrs+zj_temp_3;
			   zj_total=zj_total+parseInt(zj_temp_total)*parseInt(zj_temp_3);
		   }
	    }
		if(zrs=='undefined' || zrs=="" || zrs==null||zrs==" "||zrs==0){
			zrs=1;
		}
		return Math.round(parseInt(zj_total)/parseInt(zrs)*100)/100;
}
//占比 计算
function zhanbi(){
	   var temp_hj=0;
	   var temp_col_num=0;
	   var biliz=0;
	   var j_v=parseInt(array_row[array_row.length-1].split(",")[1]);//总行数
	   for(var j=4;j<=j_v;j++){//循环行
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
			   for(var i=col_xhj_start;i<=col_xhj_end;i++){//循环列
					if(i%2==1){
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
//隐藏占比
function yincangzb(){
	   for(var i=col_xho_start;i<=col_xho_end;i++){//循环列
		   if(i%2==0){//偶数跳过奇数隐藏
				continue;
			}
		   DCellWeb_tj.SetColHidden(i, i);
		}
	   for(var i=col_xhj_start;i<=col_xhj_end;i++){//循环列
		   if(i%2==1){//奇数跳过偶数隐藏
				continue;
			}
		   DCellWeb_tj.SetColHidden(i, i);
		}
}
//隐藏 全行为0的行
function displayzeroinit(){
	  var v=0;
	  var j_v=parseInt(array_row[array_row.length-1].split(",")[1]);//总行数
		 for(var i=(parseInt(array_row[0].split(",")[0])-2);i<=j_v;i++){//行
			 v=DCellWeb_tj.GetCellString(3, i, 0);//判断合计是否为零  ，为零 则为行全零
			 if(v=='undefined' || v=="" || v==null||v==" "||v==0){
				 DCellWeb_tj.SetRowHidden(i, i);
			 }else{
				 DCellWeb_tj.SetRowUnhidden(i, i);
			 }
		 }
}

