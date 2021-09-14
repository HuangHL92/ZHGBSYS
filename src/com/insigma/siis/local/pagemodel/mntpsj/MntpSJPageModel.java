package com.insigma.siis.local.pagemodel.mntpsj;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.annotation.GridDataRange.GridData;
import com.insigma.odin.framework.radow.element.Combo;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class MntpSJPageModel extends PageModel {
	
	
	
	
	@PageEvent("memberGrid.afteredit")
	@GridDataRange(GridData.cuerow)
	@Transaction
	@AutoNoMask
	public int dogrid6AfterEdit() throws RadowException, SQLException {
		String fabd00 = this.getPageElement("fabd00").getValue();
		//获取页面数据
		Grid grid6 = (Grid) this.getPageElement("memberGrid");
		String fabd02 = grid6.getValue("fabd02") +"";
		String mntp05 = grid6.getValue("mntp05") +"";
		HBSession sess = HBUtil.getHBSession();
		
		sess.createSQLQuery("update HZ_MNTP_SJFA set fabd02=?,mntp05=?  where fabd00=?")
		.setString(0, fabd02)
		.setString(1, mntp05)
		.setString(2, fabd00)
		.executeUpdate();
		sess.flush();
		
		UserVO user = SysUtil.getCacheCurrentUser().getUserVO();
		new LogUtil(user).createLogNew(user.getId(),"修改班子调配方案","HZ_MNTP_SJFA",fabd00,fabd02, new ArrayList());
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	/**
	 * 批次信息
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("memberGrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
		
		String sql=" select fabd00, fabd01, fabd02, fabd03, fabd04, mntp05," + 
				"               (select to_char(wm_concat(s.username))" + 
				"                  from HZ_MNTP_SJFA_USERREF u, smt_user s" + 
				"                 where s.userid = u.mnur01" + 
				"                   and mnur02 = '"+SysManagerUtils.getUserId()+"' and mnur01 != '"+SysManagerUtils.getUserId()+"' " + 
				"                   and y.fabd00 = u.fabd00) mnur01," + 
				"               (select (select username from smt_user where userid = u.mnur02)" + 
				"                  from HZ_MNTP_SJFA_USERREF u" + 
				"                 where mnur01 = '"+SysManagerUtils.getUserId()+"'  and mnur02 != '"+SysManagerUtils.getUserId()+"'  " + 
				"                   and y.fabd00 = u.fabd00) mnur02" + 
				"  from HZ_MNTP_SJFA y" + 
				"   where (fabd04 = '"+SysManagerUtils.getUserId()+"' and fabd05='1'  and fabd06!='1') " + 
				"    or (exists (select 1" + 
				"                  from HZ_MNTP_SJFA_USERREF u" + 
				"                 where mnur01 = '"+SysManagerUtils.getUserId()+"'" + 
				"                   and u.fabd00 = y.fabd00 and y.fabd05='1'  and y.fabd06!='1'))" + 
				"  order by fabd04,fabd03, fabd01 desc";
		
		
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	
	@PageEvent("allDelete")
	@Transaction
	public int allDelete(String fabd00) throws RadowException, AppException{
			String mnur02=this.getPageElement("mnur02").getValue();
		try {
			HBSession sess = HBUtil.getHBSession();
			if(mnur02==null || "".equals(mnur02)) {
				HBUtil.executeUpdate("delete from  HZ_RXFXYP_SJFA where fxyp00 in (select fxyp00 from HZ_FXYP_SJFA where famx00 in (select famx00 from HZ_MNTP_SJFA_famx where fabd00='"+fabd00+"'))");
				HBUtil.executeUpdate("delete from  HZ_FXYP_SJFA where famx00 in (select famx00 from HZ_MNTP_SJFA_famx where fabd00='"+fabd00+"')");
				HBUtil.executeUpdate("delete from  HZ_MNTP_SJFA_ORG where famx00 in (select famx00 from HZ_MNTP_SJFA_famx where fabd00='"+fabd00+"')");
				HBUtil.executeUpdate("delete from  HZ_MNTP_SJFA_famx where fabd00='"+fabd00+"'");
				HBUtil.executeUpdate("delete from  HZ_MNTP_SJFA_INFO where fabd00='"+fabd00+"'");
				HBUtil.executeUpdate("delete from  HZ_MNTP_SJFA where fabd00='"+fabd00+"'");
				HBUtil.executeUpdate("delete from  HZ_MNTP_SJFA_USERREF where fabd00='"+fabd00+"'");
			}else {
				HBUtil.executeUpdate("delete from  HZ_MNTP_SJFA_USERREF where fabd00='"+fabd00+"' and mnur01='"+SysManagerUtils.getUserId()+"'");
				this.getPageElement("mnur02").setValue("");
			}
			
			sess.flush();
			
			String fabd02=this.getPageElement("fabd02").getValue();
			UserVO user = SysUtil.getCacheCurrentUser().getUserVO();
			new LogUtil(user).createLogNew(user.getId(),"删除班子调配方案","HZ_MNTP_SJFA",fabd00,fabd02, new ArrayList());

			//this.getExecuteSG().addExecuteCode("$('#mntp00').val('');");
			this.setNextEventName("memberGrid.dogridquery");
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	@Override
	public int doInit() throws RadowException {
		
		
		this.setNextEventName("memberGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("initX")
	@Transaction
	public int initX(String yn_id) throws RadowException, AppException{
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("saveMntp")
	@Transaction
	public int savesaveMntp(String yn_id) throws RadowException, AppException{
		try{
			String fabd00 = UUID.randomUUID().toString();
			String userid = this.getCurUserid();
			String fabd02 = DateUtil.dateToString(new Date(), "yyyyMMdd")+"模拟调配";
			try{
					
					HBUtil.executeUpdate("insert into HZ_MNTP_SJFA(fabd00,fabd02,fabd04,mntp05)"
							+ " values(?,?,?,'2')",
							new Object[]{fabd00,fabd02,userid});
					this.getPageElement("fabd00").setValue(fabd00);
					this.getPageElement("fabd02").setValue(fabd02);
			} catch (Exception e) {
				this.setMainMessage("查询失败！");
				e.printStackTrace();
			}
			
			UserVO user = SysUtil.getCacheCurrentUser().getUserVO();
			new LogUtil(user).createLogNew(user.getId(),"新增班子调配方案","HZ_MNTP_SJFA",fabd00,fabd02, new ArrayList());

			
			this.setNextEventName("memberGrid.dogridquery");
			this.getExecuteSG().addExecuteCode("openBDFAPage('"+fabd00+"')");
			
		}catch (Exception e) {
			this.setMainMessage("保存失败！"+e.getMessage());
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("saveTuiSongInfo.onclick")
    public int saveTuiSongInfo() throws RadowException {
		String curuserid = this.getCurUserid();
		String fabd00 = this.getPageElement("fabd00").getValue();
		String mnur01 = this.getPageElement("mnur01").getValue();
		String mnur01_combotree = this.getPageElement("mnur01_combotree").getValue();
		try {
			HBUtil.executeUpdate("delete from HZ_MNTP_SJFA_USERREF where fabd00=? and mnur02=?",new Object[]{fabd00,curuserid});
			if(!StringUtils.isEmpty(mnur01)){
				
				String[] mnur01_combotrees = mnur01_combotree.split(",");
				String[] mnur01s = mnur01.split(",");
				for(int i=0;i<mnur01s.length;i++){
					HBUtil.executeUpdate("insert into HZ_MNTP_SJFA_USERREF(mnur00,fabd00,mnur01,mnur02,mnur04) "
							+ "values(sys_guid(),?,?,?,?)",new Object[]{fabd00,mnur01s[i],curuserid,mnur01_combotrees[i]});
				}
			}
			String fabd02 = this.getPageElement("fabd02").getValue();
			UserVO user = SysUtil.getCacheCurrentUser().getUserVO();
			new LogUtil(user).createLogNew(user.getId(),"共享班子调配方案","HZ_MNTP_SJFA_USERREF",fabd00,fabd02, new ArrayList());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		this.getExecuteSG().addExecuteCode("Ext.getCmp('tuiSong').hide();");
		this.setNextEventName("memberGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}

	
	
	
	
	
	
	
}
