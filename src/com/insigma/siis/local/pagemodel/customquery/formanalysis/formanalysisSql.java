package com.insigma.siis.local.pagemodel.customquery.formanalysis;


public class formanalysisSql {

	public formanalysisSql() {
		
	}

	public void sqlPj(String param_arr_row,StringBuffer sb){
		try{
			
			if("4".equals(param_arr_row)){//总计
				//不加条件
			}else if( formanalysis_zhPageModel.xioaji.indexOf(","+param_arr_row+",")!=-1 ){//其他类别 小计
				sb.append(" and a01.a0221 like '"+formanalysis_zhPageModel.row[Integer.valueOf(param_arr_row)]+"%' ");
			}else if(Integer.parseInt(param_arr_row)>4&&Integer.parseInt(param_arr_row)<=(formanalysis_zhPageModel.row.length-1)){
				sb.append(" and a01.a0221='"+formanalysis_zhPageModel.row[Integer.valueOf(param_arr_row)]+"' ");
			}else{
				sb.append(" and 1=0 ");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
