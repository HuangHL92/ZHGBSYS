package com.insigma.siis.local.pagemodel.bzpj;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeException;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class PJZBGLPageModel extends PageModel {
	
	
	
	/**
	 * 指标主信息
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("memberGrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
		String ets00 = this.getPageElement("ets01").getValue();
		//定义用来组装sql的变量
		StringBuffer str = new StringBuffer();
		str.append("select * from EVALUATION_TARGET_CLASS where ets00='"+ets00+"' order by etc03 ");
		
		
		
		this.pageQuery(str.toString(), "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	/**
	 * 指标明细信息
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("logGrid.dogridquery")
	public int doLogQuery(int start,int limit) throws RadowException{
		String etc00id = this.getPageElement("etc00id").getValue();
		String sql = "select  * from EVALUATION_TARGET where ETC00='"+etc00id+"'";
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	/**
	 * 刷新
	 * 
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("memberGrid.rowclick")
	@GridDataRange
	public int persongridOnRowDbClick() throws RadowException{  //打开窗口的实例
		String mainid = (String)this.getPageElement("memberGrid").getValueList().get(this.getPageElement("memberGrid").getCueRowIndex()).get("etc00");
		this.getPageElement("etc00id").setValue(mainid);
		this.setNextEventName("logGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	@Override
	public int doInit() throws RadowException {
		
		
		this.setNextEventName("initX");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("initX")
	public int initX(String ets00) throws RadowException, PrivilegeException, AppException{
		String selSqL = "select ets00,ets01 from EVALUATION_TARGET_scheme order by ets02";
		CommQuery cqbs=new CommQuery();
		List<HashMap<String, Object>> listCode;
		try {
			listCode = cqbs.getListBySQL(selSqL);
			HashMap<String, Object> mapCode=new LinkedHashMap<String, Object>();
			for(int i=0;i<listCode.size();i++){
				mapCode.put(listCode.get(i).get("ets00").toString(), listCode.get(i).get("ets01")==null?"":listCode.get(i).get("ets01"));
			}
			Combo c = (Combo)this.getPageElement("ets01");
			((Combo)this.getPageElement("ets01")).setValueListForSelect(mapCode);
		} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(StringUtils.isEmpty(ets00)){
			this.getPageElement("ets01").setValue("000");
		}else{
			this.getPageElement("ets01").setValue(ets00);
		}
		this.setNextEventName("memberGrid.dogridquery");
		return 0;
	}
	@PageEvent("addETCInfo")
	@Transaction
	public int addInfo() throws Exception{
		String etc00 = this.getPageElement("etc00").getValue();
		String etc01 = this.getPageElement("etc01").getValue();
		String ets00 = this.getPageElement("ets01").getValue();
		if(StringUtils.isEmpty(etc00)){
			etc00 = UUID.randomUUID().toString();
			HBUtil.executeUpdate("insert into EVALUATION_TARGET_CLASS(etc00,etc01,ets00,etc03) values(?,?,?,seq_sort.nextval)",
					new Object[]{etc00,etc01,ets00});
		}else{
			HBUtil.executeUpdate("update EVALUATION_TARGET_CLASS set etc01=? where etc00=?",
					new Object[]{etc01,etc00});
		}
		
		this.setNextEventName("memberGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("addETInfo")
	@Transaction
	public int addETInfo() throws Exception{
		String et00 = this.getPageElement("et00").getValue();
		String et01 = this.getPageElement("et01").getValue();
		String et02 = this.getPageElement("et02").getValue();
		String et03 = this.getPageElement("et03").getValue();
		String et04 = this.getPageElement("et04").getValue();
		String etc00id = this.getPageElement("etc00id").getValue();
		if(StringUtils.isEmpty(etc00id)){
			this.setMainMessage("请选择指标类！");
			return EventRtnType.NORMAL_SUCCESS;
		}
		if(StringUtils.isEmpty(et00)){
			et00 = UUID.randomUUID().toString();
			HBUtil.executeUpdate("insert into EVALUATION_TARGET(et00,et01,et02,et03,et04,et05,etc00) values(?,?,?,?,?,seq_sort.nextval,?)",
					new Object[]{et00,et01,et02,et03,et04,etc00id});
		}else{
			HBUtil.executeUpdate("update EVALUATION_TARGET set et01=?,et02=?,et03=?,et04=? where et00=?",
					new Object[]{et01,et02,et03,et04,et00});
		}
		
		this.setNextEventName("logGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
}
