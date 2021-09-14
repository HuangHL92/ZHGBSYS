package com.insigma.siis.local.pagemodel.sysmanager.ZWHZYQ_001_007;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;

public class InterfaceLogMainPageModel extends PageModel{

	@Override
	public int doInit() throws RadowException {
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//现在时间
		String endTime = sdf.format(now);
		this.getPageElement("end").setValue(endTime);
		//15天前的时间
		String startTime = sdf.format(new Date(now.getTime()-15*24*60*60*1000));
		this.getPageElement("start").setValue(startTime);
		this.setNextEventName("list.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * 根据时间降序的将所有接口访问日志查询出来
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("list.dogridquery")
	public int queryObj(int start, int limit) throws RadowException{
		String startTime = this.getPageElement("start").getValue();
		startTime = startTime.replaceAll("-", "");
		String endTime = this.getPageElement("end").getValue();
		endTime = endTime.replaceAll("-", "");
		String sql = null;
		if(DBType.ORACLE==DBUtil.getDBType()){
			sql = "select "
					+ "INTERFACE_LOG_ID,INTERFACE_CONFIG_ID,INTERFACE_CONFIG_NAME,"
					+ "INTERFACE_SCRIPT_ID,INTERFACE_SCRIPT_NAME,INTERFACE_EXEC_SQL,"
					+ "INTERFACE_ORIGINAL_SQL,INTERFACE_REQUESTTIME,INTERFACE_ACCESS_IP,"
					+ "EXECUTE_STATE_ID,OPERATE_USERNAME,INTERFACE_COMMENTS "
					+ "from "
					+ "INTERFACE_LOG "
					+ "where to_char(INTERFACE_REQUESTTIME,'yyyymmdd') between '"+startTime+"' and '"+endTime+"'"
					+ " order by INTERFACE_REQUESTTIME desc";
			
		} else if(DBType.MYSQL==DBUtil.getDBType()) {
			sql = "select "
					+ "INTERFACE_LOG_ID,INTERFACE_CONFIG_ID,INTERFACE_CONFIG_NAME,"
					+ "INTERFACE_SCRIPT_ID,INTERFACE_SCRIPT_NAME,INTERFACE_EXEC_SQL,"
					+ "INTERFACE_ORIGINAL_SQL,INTERFACE_REQUESTTIME,INTERFACE_ACCESS_IP,"
					+ "EXECUTE_STATE_ID,OPERATE_USERNAME,INTERFACE_COMMENTS "
					+ "from "
					+ "INTERFACE_LOG "
					+ "where date_format(INTERFACE_REQUESTTIME,'%Y%m%d') between '"+startTime+"' and '"+endTime+"'"
					+ " order by INTERFACE_REQUESTTIME desc";
		}
		this.pageQuery(sql, "sql", -1, 20);
		return EventRtnType.SPE_SUCCESS;
	}
	
	/**
	 * 查询按钮相应事件
	 * @return
	 * @throws RadowException 
	 */
	@PageEvent("query.onclick")
	public int query() throws RadowException{
		this.setNextEventName("list.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
}
