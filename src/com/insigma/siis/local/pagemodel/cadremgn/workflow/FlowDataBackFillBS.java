package com.insigma.siis.local.pagemodel.cadremgn.workflow;

import java.util.HashMap;
import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class FlowDataBackFillBS {

	/**
	 * 初始化原有指标集grid
	 * @throws AppException 
	 * @throws RadowException 
	 */
	public void initSourceSet(String tableName,PageModel pm) throws AppException, RadowException{
		CommQuery cq = new CommQuery();
		String sql = " SELECT ctci,table_code,col_code,col_name,code_type,col_data_type_should,fldlth FROM code_table_col WHERE table_code = '"+tableName+"' order by col_code";
		List<HashMap<String, Object>> cols = cq.getListBySQL(sql);
		pm.getPageElement("sourceGrid").setValueList(cols);
	}
	
	
}
