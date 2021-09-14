package com.insigma.siis.local.pagemodel.statisticalanalysis.simpleanalysis;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.annotation.GridDataRange.GridData;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.PageElement;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.StopWatch;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.customquery.CommSQL;

/**
 * @author lixy
 *
 */
public class SimpleAnalysisPageModel extends PageModel {
	
	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("grid1.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * @$comment 选择信息集查询对应指标项
	 * @param 
	 * @return 
	 * @throws 
	 * @author caiy
	 */
	
	@PageEvent("collection.onchange")
	public int collectionOnchange() throws RadowException, AppException {
		this.setNextEventName("grid1.dogridquery");		
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("grid1.dogridquery")
	@GridDataRange(GridData.cuerow)
	public int querygrid1(int start,int limit) throws RadowException{
		String collection = this.getPageElement("collection").getValue();		
		String sql = "";
		sql="select col_code,code_type,col_name,table_code,col_data_type_should "
				+ "From Code_table_col where is_zbx='1' and table_code='"+collection+"'";
    	this.pageQuery(sql, "SQL", start, 50); 
		return EventRtnType.SPE_SUCCESS;
	}
	
}