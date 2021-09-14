package com.insigma.siis.local.pagemodel.otherdb.dxscg;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;

public class DxscgPageModel extends PageModel {
	
	/**
	 * 批次信息
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("memberGrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
		UserVO user = PrivilegeManager.getInstance().getCueLoginUser();
		String cvo001q = this.getPageElement("cvo001q").getValue();
		String cvo002q = this.getPageElement("cvo002q").getValue();
		String cvo008q = this.getPageElement("cvo008q").getValue();
		String cvo009q = this.getPageElement("cvo009q").getValue();
		
		String where = "";
		if(cvo001q!=null&&!"".equals(cvo001q)){
			where += " and t.cvo001 like '%"+cvo001q+"%'";
		}
		if(cvo002q!=null&&!"".equals(cvo002q)){
			where += " and t.cvo002c = '"+cvo002q+"'";
		}
		if(cvo008q!=null&&!"".equals(cvo008q)){
			where += " and t.cvo008 like '%"+cvo008q+"%'";
		}
		if(cvo009q!=null&&!"".equals(cvo009q)){
			where += " and t.cvo009 like '%"+cvo009q+"%'";
		}
		String sql="select * from COLLEGEVOFFICAIL t where 1=1 "+where+" order by CVO011 desc ";
		this.pageQueryByAsynchronous(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("allDelete")
	public int allDelete(String cvo000s) throws RadowException, AppException{
		try {
			HBSession sess = HBUtil.getHBSession();
			String ids = cvo000s.replace(",", "','");
			String sql = "delete from COLLEGEVOFFICAIL where CVO000 in ('"+ids+"')";
			sess.createSQLQuery(sql).executeUpdate();
			sess.flush();
			this.getExecuteSG().addExecuteCode("odin.alert('删除成功！');");
			this.setNextEventName("memberGrid.dogridquery");
		}catch (Exception e) {
			e.printStackTrace();
			throw new RadowException("删除异常："+e.getMessage());
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	@Override
	public int doInit() throws RadowException {
		
		this.setNextEventName("memberGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
}
