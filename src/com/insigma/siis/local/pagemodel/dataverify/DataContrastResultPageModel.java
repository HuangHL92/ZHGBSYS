package com.insigma.siis.local.pagemodel.dataverify;

import org.apache.commons.lang.StringUtils;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.util.StringUtil;
/*
 * @pywu
 * 2017-06-11
 * 专题信件
 * */
public class DataContrastResultPageModel extends PageModel {

	@Override
	public int doInit() {
		try {
		    this.getPageElement("dataResultType").setValue("");//数据包比对结果  默认全部
		    this.setNextEventName("list1.dogridquery");
            //this.getExecuteSG().addExecuteCode("setGridHeight();"); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * 分页查询最好采取以下方法
	 * 
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 * @throws AppException
	 */
	@PageEvent("list1.dogridquery")
	@NoRequiredValidate
	@AutoNoMask
	public int dogridQuery(int start, int limit) throws RadowException, AppException {
		String jgmc  = this.getPageElement("jgmc").getValue();//机构名称
		String dataResultType  = this.getPageElement("dataResultType").getValue();//数据包比对结果
        String imprecordid = request.getParameter("initParams");//imp_record导入记录表id   一个数据包
		
		if(StringUtils.isNotBlank(imprecordid)){
		    StringBuffer sql = new StringBuffer();
		    sql.append("select t.storeid,t.b0111_n,t.b0111_w,t.b0111_parent_n,t.b0111_parent_w"
		            + ",t.b0101_n,t.b0101_w,t.b0114_n,t.b0114_w"
		            + ",t.imp_record_id,t.comments,t.opptimetype,to_char(t.opptime, 'yyyy-mm-dd hh24:mi:ss') opptime "
		            + "from TAB_DataContrastResult t where 1 = 1 ");
		    if(StringUtils.isNotBlank(jgmc)){
		        jgmc = StringUtil.getStrToLikeSelectStr(jgmc);
		        sql.append(" and t.b0101_n like '"+jgmc+"'");//机构名称
		    }
		    if(StringUtils.isNotBlank(dataResultType)){
		        if(!"4".equals(dataResultType)){//查询全部
		            sql.append(" and t.opptimetype = '"+dataResultType+"'");//数据包比对结果
		        }
		    }else{//默认查询有变动的数据
		        sql.append(" and t.opptimetype != '1' ");//数据包比对结果
		    }
		    sql.append(" and t.imp_record_id = '"+imprecordid+"' ");
		    this.pageQuery(sql.toString(), "SQL", start, limit); //处理分页查询
		    return EventRtnType.SPE_SUCCESS;
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 刷新
	 * @throws RadowException
	 * @throws AppException 
	 */
	@PageEvent("refresh.onclick")
	@NoRequiredValidate
	public int gridrefresh() throws RadowException{
		this.getPageElement("list1").reload();
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 查询
	 * @throws RadowException
	 * @throws AppException 
	 */
	@PageEvent("search.onclick")
	@NoRequiredValidate
	public int query() throws RadowException{
		this.setNextEventName("list1.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 清空
	 * @throws RadowException
	 * @throws AppException 
	 */
	@PageEvent("clear.onclick")
	@NoRequiredValidate
	public int clear() throws RadowException{
	    this.clearDivData("searchTab");
	    this.setNextEventName("list1.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	

	//表格修改按钮点击会调用方法    机构信息修改   add by wupy  
    @PageEvent("dogridgrant")
    public int updateOnClick(String storeid) throws RadowException {
        this.setRadow_parent_data(storeid);//组（部门）ID  
        this.openWindow("dataContrastResultWin", "pages.dataverify.DataContrastResultWin");
        return EventRtnType.NORMAL_SUCCESS;
    }
	
	
	
	
	
}
