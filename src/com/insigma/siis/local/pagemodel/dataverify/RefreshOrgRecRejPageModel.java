package com.insigma.siis.local.pagemodel.dataverify;

import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;

public class RefreshOrgRecRejPageModel extends PageModel {
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		String ids = this.getPageElement("subWinIdBussessId").getValue();
		String str[] = ids.split(",");
		try {
			String id = str[0];
			this.getPageElement("id").setValue(id);
			if(str.length == 2){
				this.getPageElement("type").setValue("1");
			}
			this.setNextEventName("Fgrid.dogridquery");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("initX");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	/**
	 * ²éÑ¯
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("Fgrid.dogridquery")
	@NoRequiredValidate           ///??????
	public int dogridQuery(int start,int limit) throws RadowException{
		String id = this.getPageElement("id").getValue();
		//--------------------------------------------------------------------------------------
		String sq = DBUtil.getDBType().equals(DBType.MYSQL)?"date_format(START_TIME,'%Y-%m-%d %T')":"to_char(START_TIME,'yyyy-mm-dd hh24:mi:ss')";
		String sq1 = DBUtil.getDBType().equals(DBType.MYSQL)?"date_format(END_TIME,'%Y-%m-%d %T')":"to_char(END_TIME,'yyyy-mm-dd hh24:mi:ss')";
		//--------------------------------------------------------------------------------------
		StringBuffer sql = new StringBuffer("select detail detail,OP_STATUS status,"+ sq +" starttime,"+ sq1 +" endtime,data_table dtable from DATA_RECREJ_LOG t where ");
		sql.append(" IMPRECORDID='" + id +"'");
		sql.append(" order by SORTID desc");
		this.pageQuery(sql.toString(),"SQL", start, limit); 
		return EventRtnType.SPE_SUCCESS;
	}
	/**
	 * Ë¢ÐÂ
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("btnsx")
	public int btnsxOnClick()throws RadowException{
		this.setNextEventName("Fgrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}

}
