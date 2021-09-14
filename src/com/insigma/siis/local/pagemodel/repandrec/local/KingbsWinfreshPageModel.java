package com.insigma.siis.local.pagemodel.repandrec.local;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.element.ElementType;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.event.NextEvent;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.entity.Dataexchangeconf;
import com.insigma.siis.local.business.entity.Imprecord;
import com.insigma.siis.local.business.repandrec.local.KingbsconfigBS;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class KingbsWinfreshPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		String param = this.request.getParameter("initParams");
		String parry[] = param.split("\\|\\|");
		try {
			String id = parry[0];
			if(parry.length != 1){
				if(parry.length<4){
					this.setMainMessage("参数不正确，请重新选择。");
					return EventRtnType.NORMAL_SUCCESS;
				}
				String file = parry[1];
				String deptid = parry[2];
				//非现职
				String fxz= parry[3];
				if(deptid.equals("")){
					this.setMainMessage("上级机构请重新选择。");
					return EventRtnType.NORMAL_SUCCESS;
				}
				CurrentUser user = SysUtil.getCacheCurrentUser();   //获取当前执行导入的操作人员信息
				HBUtil.getHBSession().createSQLQuery("insert into IMP_RECORD(IMP_RECORD_ID) values ('"+ id +"')").executeUpdate();
				KingbsconfigBS.saveImpDetailInit(id);
				UserVO userVo = PrivilegeManager.getInstance().getCueLoginUser();
				ImpThread thr = new ImpThread(file, deptid, id, user, userVo, fxz);   //启动线程导入
				new Thread(thr,"zzb3线程1").start();
//				new LogUtil().createLog("451", "IMP_RECORD", "", "", "导入临时库", new ArrayList());
			}
//			new KingbsZZBLoadPageModel().imp3btn(file, deptid, id);
//			this.getExecuteSG().addExecuteCode("radow.doEvent('"+ request.getContextPath() +
//					"/radowAction.do?method=doEvent&pageModel=pages.repandrec.local.KingbsZZBLoad&eventNames=imp3btn.onclick')");
			this.getPageElement("id").setValue(id);
			this.setNextEventName("Fgrid.dogridquery");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("imp")
	public int imp(String param) throws RadowException {
		String parry[] = param.split("\\|\\|");
		String id = parry[0];
		String file = parry[1];
		String deptid = parry[2];
//		new KingbsZZBLoadPageModel().imp3btn(file, deptid, id);
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 查询
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
				"PROCESS_STATUS status,PROCESS_INFO info from IMP_PROCESS where ");
		sql.append(" IMPRECORDID='" + id +"'");
		sql.append(" order by PROCESS_TYPE asc");
		this.pageQuery(sql.toString(),"SQL", start, limit); 
		return EventRtnType.SPE_SUCCESS;
	}
	/**
	 * 刷新
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("btnsx")
	public int btnsxOnClick()throws RadowException{
		String id = this.getPageElement("id").getValue();
		Imprecord r = (Imprecord) HBUtil.getHBSession().get(Imprecord.class, id);
		if(r != null){
			this.getPageElement("psncount").setValue(r.getPsncount()+"");
			CommonQueryBS.systemOut(r.getPsncount()+"");
			this.getPageElement("orgcount").setValue(r.getOrgcount()+"");
			CommonQueryBS.systemOut(r.getOrgcount()+"");
		}
		this.setNextEventName("Fgrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}

}
