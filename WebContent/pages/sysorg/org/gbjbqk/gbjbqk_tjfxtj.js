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
		  zhanbi_xy_zwlb();//重新统计第4行占比
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
		  zhanbi_xy_zwlb();//重新统计第4行占比
		  DCellWeb_tj.SetRowUnhidden(4, 4);//显示
		  return;
	  }
	  var strs= new Array(); //定义一数组 存储职务类别代码 
	  var strs_m= new Array(); //定义一数组  存储行数 
	  strs=zwlb_data.split(","); //字符分割
	  DCellWeb_tj.SetRowhidden(parseInt(array_row[0].split(",")[0])-2, parseInt(array_row[array_row.length-1].split(",")[1]));//隐藏所有行
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
//清空第3行的数据
function clear4(){
	  for(var i=3;i<=tjxm_arr.length-1;i++){
		  DCellWeb_tj.SetCellString(i,4,0,'');
	  }
}
//显示所有行
function xsall(){
	DCellWeb_tj.SetRowUnhidden(parseInt(array_row[0].split(",")[0])-2, parseInt(array_row[array_row.length-1].split(",")[1]));
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
	   for(var i=col_xhj_start;i<=col_xhj_end;i++){
			if(i%2==0){//
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
				zj_temp_total=DCellWeb_tj.GetCellString2(i, parseInt(array_row[j].split(",")[0])-1, 0);
				if(zj_temp_total=='undefined' || zj_temp_total=="" || zj_temp_total==null||zj_temp_total==" "){
						zj_temp_total=0;
				}
				zj_total=zj_total+parseInt(zj_temp_total);
		 	}
	   }
	 	if(i==col_xho_end){
	 		zj_total=total_zj_ittt_38(i);
	 	}
	 	
		return zj_total;
}
//平均年龄
function total_zj_ittt_38(i){
	  var zj_total=0;//总年龄
	   var zj_temp_total=0;//本行平均年龄
	   var zj_temp_3=0;//本行总人数
	   var zrs=0;//总人数
	   for(var j=0;j<array_row.length;j++){
		   if(DCellWeb_tj.IsRowHidden(parseInt(array_row[j].split(",")[0])-1,0)==false){//行显示
			   for(var n=parseInt(array_row[j].split(",")[0]);n<=parseInt(array_row[j].split(",")[1]);n++){//循环职务等级
				   zj_temp_total=DCellWeb_tj.GetCellString2(i, n, 0);//本行平均年龄
				   if(zj_temp_total=='undefined' || zj_temp_total=="" || zj_temp_total==null||zj_temp_total==" "){
					   zj_temp_total=0;
				   }
				   zj_temp_3=r_total_r(n);//本行总人数
				   if(zj_temp_3=='undefined' || zj_temp_3=="" || zj_temp_3==null||zj_temp_3==" "){
					   zj_temp_3=0;
				   }
				   zj_total=zj_total+parseInt(zj_temp_total)*parseInt(zj_temp_3);//本行总人数 叠加
				   zrs=zrs+zj_temp_3;//本行总人数 叠加
			   }
		 	}
	   }
	 	if(zrs=='undefined' || zrs=="" || zrs==null||zrs==" "||zrs==0){
	 		zrs=1;
		}
	 	return Math.round(parseInt(zj_total)/parseInt(zrs)*100)/100;
}
//小计 平均年龄对应人数
function r_total_r(row){
	   var zj_temp_3=0;
	   var zj_temp_cell=0;
	   for(var j=(col_xho_end-10-4);j<=(col_xho_end-2);j++){//列
			if(j%2==1){
				 continue;
			}
			zj_temp_cell=DCellWeb_tj.GetCellString2(j, row, 0);
			if(zj_temp_cell=='undefined' || zj_temp_cell=="" || zj_temp_cell==null||zj_temp_cell==" "){
				zj_temp_cell=0;
			}
			zj_temp_3=zj_temp_3+parseInt(zj_temp_cell);
	 }
	   return zj_temp_3;
}
//紧重新统计第4行的占比
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
//隐藏 0行
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
//显示占比
function xszhanbi(){
	   DCellWeb_tj.SetColUnhidden(3, tjxm_arr.length-1);
}
//隐藏全零行
function displayzero(xy){
	  zwlb_xy();
}
function xs_zwlb_zero(){
	  zwlb_xy();
}