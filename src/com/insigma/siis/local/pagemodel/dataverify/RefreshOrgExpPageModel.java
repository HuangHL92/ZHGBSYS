package com.insigma.siis.local.pagemodel.dataverify;

import java.util.ArrayList;

import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.Imprecord;
import com.insigma.siis.local.business.repandrec.local.KingbsconfigBS;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.repandrec.local.ImpThread;

public class RefreshOrgExpPageModel extends PageModel {
	@PageEvent("initX")
	@NoRequiredValidate
	public int initX() throws RadowException {
		String id = this.getPageElement("subWinIdBussessId").getValue();
		try {
			this.getPageElement("id").setValue(id);
			this.setNextEventName("Fgrid.dogridquery");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
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
		StringBuffer sql = new StringBuffer("select PROCESS_NAME name," +
				"PROCESS_STATUS status,PROCESS_INFO info,ADD_FIELD1 adf1 from IMP_PROCESS where ");
		sql.append(" IMPRECORDID='" + id +"'");
		sql.append(" order by PROCESS_TYPE asc");
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
//		String id = this.getPageElement("id").getValue();
//		Imprecord r = (Imprecord) HBUtil.getHBSession().get(Imprecord.class, id);
//		if(r != null){
//			this.getPageElement("psncount").setValue(r.getPsncount()+"");
//			System.out.println(r.getPsncount()+"");
//			this.getPageElement("orgcount").setValue(r.getOrgcount()+"");
//			System.out.println(r.getOrgcount()+"");
//		}
		this.setNextEventName("Fgrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}

	/**
	 * Ë¢ÐÂ
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("orgdataverify")
	public int orgdataverify()throws RadowException{
		this.closeCueWindow("refreshWin");
		return EventRtnType.NORMAL_SUCCESS;
	}
}
