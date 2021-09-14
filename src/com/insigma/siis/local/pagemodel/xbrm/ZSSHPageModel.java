package com.insigma.siis.local.pagemodel.xbrm;


import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;


public class ZSSHPageModel extends PageModel {
	
	JSGLBS bs = new JSGLBS();
	/**
	 * 批次信息
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("memberGrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
		String sign = this.getPageElement("sign").getValue();
		if(sign==null||"".equals(sign)||"0".equals(sign)){
			sign = " and j.js1214 is null ";
		}else{
			sign = " and j.js1214 is not null ";
		}
		
		String rb_name1 = this.getPageElement("rb_name1").getValue();
		String rb_date1 = this.getPageElement("rb_date1").getValue();
		String where = "";
		if(rb_name1!=null&&!"".equals(rb_name1)){
			where += " and t.rb_name like '%"+rb_name1+"%'";
		}
		if(rb_date1!=null&&!"".equals(rb_date1)){
			where += " and t.rb_date like '%"+rb_date1+"%'";
		}
		//String sql="select * from RECORD_BATCH t where 1=1 "+where+" "+sign+" order by rb_sysno desc";
		String sql="select * from RECORD_BATCH r where r.rb_id in (select t.rb_id from RECORD_BATCH t inner join js12 j on t.rb_id = j.rb_id where 1 = 1 "+where+" "+sign+" )";
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	

	@Override
	public int doInit() throws RadowException {
		
		
		this.setNextEventName("memberGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	
	
}
