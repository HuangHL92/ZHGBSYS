function conditionToWhereSql(table_code,col_code,code_type,col_data_type,col_data_type_should,conditionName5,conditionName6,conditionName61,conditionName611,conditionName6111,conditionName7,conditionName71,conditionName711){
/**
 * ��;��������ʵ�ֶԴ�����Ϣ����SQL����ƴ��
 * table_code ��Ϣ������
 * col_code ��Ϣ������
 * col_data_type �Զ����ֶ����� Tʱ��C�ַ���(�ı�)N����S����ѡ
 * col_data_type_should �ֶ����� �磺varchar2
 * conditionName5 ���� �磺={v}
 * conditionName6,conditionName61,conditionName611,conditionName6111 ֵһ
 * conditionName7,conditionName71,conditionName711 ֵ��
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
	if(code_type!=null&&code_type!=""&&code_type!="null"&&code_type!=" "){//����ѡ ��between and  ��like  
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
	}else if("date"==col_data_type_should||col_data_type=='t'){//�������ֶ�   ��between and  ��like 
			if(col_data_type_should=='date'){
				if(conditionName5.indexOf('between')!=-1){//��between
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
				if(conditionName5.indexOf('between')!=-1){//��between
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
			||col_data_type_should=="null"){//�ı� ��like ��between and
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
		
	}else if("number"==col_data_type_should){//���� ��like  ��between and
		//alert(conditionName5);
		if(conditionName5.indexOf('between')!=-1){//��between
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