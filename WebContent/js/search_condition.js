function conditionToWhereSql(table_code,col_code,code_type,col_data_type,col_data_type_should,conditionName5,conditionName6,conditionName61,conditionName611,conditionName6111,conditionName7,conditionName71,conditionName711){
/**
 * 用途：本方法实现对传如信息进行SQL条件拼接
 * table_code 信息集名称
 * col_code 信息项名称
 * col_data_type 自定义字段类型 T时间C字符串(文本)N数字S下拉选
 * col_data_type_should 字段类型 如：varchar2
 * conditionName5 符号 如：={v}
 * conditionName6,conditionName61,conditionName611,conditionName6111 值一
 * conditionName7,conditionName71,conditionName711 值二
 */
/*
var code_type = document.getElementById("code_type").value;
var col_data_type = document.getElementById("col_data_type").value;
var col_data_type_should = document.getElementById("col_data_type_should").value;
var conditionName5 = document.getElementById("conditionName5").value;
var conditionName6 = document.getElementById("conditionName6").value;
var conditionName61 = document.getElementById("conditionName61").value;
var conditionName611 = document.getElementById("conditionName611").value;
var conditionName6111 = document.getElementById("conditionName6111").value;
var conditionName7 = document.getElementById("conditionName7").value;
var conditionName71 = document.getElementById("conditionName71").value;
var conditionName711 = document.getElementById("conditionName711").value;
*/
	var conditionStr = "";
	if(code_type!=null&&code_type!=""&&code_type!="null"&&code_type!=" "){//下拉选 有between and  有like  
		if(conditionName5.indexOf('between')!=-1){
			conditionStr=" ( "+table_code+"."+col_code+" "+" between "+" '"+conditionName6+"' and '"+conditionName71+"'";
			conditionStr=conditionStr+" or "+table_code+"."+col_code+" "+" between "+" '"+conditionName71+"' and '"+conditionName6+"' )";
		}else if(conditionName5.indexOf('like')!=-1){
			if(conditionName5.indexOf('%v%')!=-1){
				conditionStr=" "+table_code+"."+col_code+" "+conditionName5+" "+"'%"+conditionName6+"%'"+" ";
			}else if(conditionName5.indexOf('%v')!=-1){
				conditionStr=" "+table_code+"."+col_code+" "+conditionName5+" "+"'%"+conditionName6+"'"+" ";
			}else if(conditionName5.indexOf('v%')!=-1){
				conditionStr=" "+table_code+"."+col_code+" "+conditionName5+" "+"'"+conditionName6+"%'"+" ";
			}
		}else if(conditionName5.indexOf('null')!=-1){
			conditionStr = conditionName5.replace(/\{c\}/g, table_code+"."+col_code).replace(/\{v\}/g, "''");
		}else{
			conditionStr=" "+nvl+"("+table_code+"."+col_code+",'') "+conditionName5+" "+"'"+conditionName6+"'"+" ";
		}
	}else if("date"==col_data_type_should||col_data_type=='t'){//日期型字段   有between and  无like 
			if(col_data_type_should=='date'){
				if(conditionName5.indexOf('between')!=-1){//有between
					tempright="to_date('"+conditionName61+"','yyyy-mm-dd')";
					templeft="to_date('"+conditionName7+"','yyyy-mm-dd')";
					conditionStr=" ( "+table_code+"."+col_code+" "+" between "+" "+tempright+" and "+templeft;
					conditionStr=conditionStr+" or "+table_code+"."+col_code+" "+" between "+" "+templeft+" and "+tempright+") ";
				}else if(conditionName5.indexOf('null')!=-1){
					conditionStr = conditionName5.replace(/\{c\}/g, table_code+"."+col_code).replace(/\{v\}/g, "''");
				}else{
					tempright="to_date('"+conditionName61+"','yyyy-mm-dd')";
					conditionStr=" "+table_code+"."+col_code+" "+" "+conditionName5+" "+tempright+" ";
				}
				
			}else if(col_data_type_should!='date'&&col_data_type=='t'){
				if(conditionName5.indexOf('between')!=-1){//有between
					tempright="to_date('"+conditionName61+"','yyyy-mm-dd')";
					templeft="to_date('"+conditionName7+"','yyyy-mm-dd')";
					conditionStr=" ( to_date("+table_code+"."+col_code+",'yyyy-mm-dd') "+" between "+" "+tempright+" and "+templeft;
					conditionStr=conditionStr+" or to_date("+table_code+"."+col_code+",'yyyy-mm-dd') "+" between "+" "+templeft+" and "+tempright+")";
				}else if(conditionName5.indexOf('null')!=-1){
					conditionStr = conditionName5.replace(/\{c\}/g, table_code+"."+col_code).replace(/\{v\}/g, "''");
				}else{
					tempright="to_date('"+conditionName61+"','yyyy-mm-dd')";
					conditionStr=" to_date("+table_code+"."+col_code+",'yyyy-mm-dd')"+" "+conditionName5+" "+tempright+" ";
				}
			}
	}else if("varchar2"==col_data_type_should
			||col_data_type_should=='clob'
			||col_data_type_should==""
			||col_data_type_should==" "
			||col_data_type_should=="null"){//文本 有like 无between and
		if(conditionName5.indexOf('like')!=-1){
			if(conditionName5.indexOf('%v%')!=-1){
				conditionStr=" "+table_code+"."+col_code+" "+conditionName5+" "+"'%"+conditionName611+"%'";
			}else if(conditionName5.indexOf('%v')!=-1){
				conditionStr=" "+table_code+"."+col_code+" "+conditionName5+" "+"'%"+conditionName611+"'";
			}else if(conditionName5.indexOf('v%')!=-1){
				conditionStr=" "+table_code+"."+col_code+" "+conditionName5+" "+"'"+conditionName611+"%'";
			}
		}else if(conditionName5.indexOf('null')!=-1){
			conditionStr = conditionName5.replace(/\{c\}/g, table_code+"."+col_code).replace(/\{v\}/g, "''");
		}else{
			conditionStr=" "+nvl+"("+table_code+"."+col_code+" ,'')"+conditionName5+" "+"'"+conditionName611+"'"+" ";
		}
		
	}else if("number"==col_data_type_should){//数字 有like  有between and
		//alert(conditionName5);
		if(conditionName5.indexOf('between')!=-1){//有between
			tempright=conditionName6111;
			templeft=conditionName711;
			conditionStr=" ("+table_code+"."+col_code+" "+" between "+" "+tempright+" and "+templeft;
			conditionStr=conditionStr+" or "+table_code+"."+col_code+" "+" between "+" "+templeft+" and "+tempright+")";
		}else if(conditionName5.indexOf('null')!=-1){
			conditionStr = conditionName5.replace(/\{c\}/g, table_code+"."+col_code).replace(/\{v\}/g, "''");
		}else{
			tempright=conditionName6111;
			conditionStr=" "+nvl+"("+table_code+"."+col_code+",'') "+conditionName5+" "+tempright+" ";
		}
	}
	//alert(conditionStr)
	return conditionStr;
}