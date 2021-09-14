package com.insigma.siis.local.pagemodel.yntp;

import java.io.File;
import java.util.List;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.xbrm.JSGLBS;

public class YNTPGLPageModel extends PageModel {
	
	/**
	 * 批次信息
	 * @param start
	 * @param limit
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("memberGrid.dogridquery")
	public int doMemberQuery(int start,int limit) throws RadowException{
		String yn_name1 = this.getPageElement("yn_name1").getValue();
		String yn_type1 = this.getPageElement("yn_type1").getValue();
		String where = "";
		if(yn_name1!=null&&!"".equals(yn_name1)){
			where += " and y.yn_name like '%"+yn_name1+"%'";
		}
		if(yn_type1!=null&&!"".equals(yn_type1)){
			where += " and y.yn_type = '"+yn_type1+"'";
		}
		
		String sql="select y.*,i.info02,i.info01,GET_gname(i.info02) gname from hz_JS2_YNTP y left join hz_JS2_YNTP_INFO i on y.yn_id=i.yn_id and i.info02 is not null where y.yn_userid='"+SysManagerUtils.getUserId()+"' "+where+" order by yn_sysno desc";
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("allDelete")
	@Transaction
	public int allDelete(String yn_id) throws RadowException, AppException{
		
		try {
			HBSession sess = HBUtil.getHBSession();
			HBUtil.executeUpdate("delete from hz_JS2_YNTP where yn_id=?",new Object[]{yn_id});
			HBUtil.executeUpdate("delete from tpb_att where rb_id=?",new Object[]{yn_id});
			String directory = YNTPFileExportBS.HZBPATH+"zhgbuploadfiles" + File.separator +yn_id+ File.separator;
			JSGLBS.deleteDirectory(directory);
			
			HBUtil.executeUpdate("delete from hz_TPHJ1 where yn_id=?",new Object[]{yn_id});
			HBUtil.executeUpdate("delete from hz_js2_yntp_info where yn_id=?",new Object[]{yn_id});
			HBUtil.executeUpdate("delete from HZ_TPHJ_zw where yn_id=?",new Object[]{yn_id});
			sess.flush();
			this.getExecuteSG().addExecuteCode("$('#yn_id').val(''); $('#yn_name').val('');");
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
	
	
	@PageEvent("saveShareInfo")
	@Transaction
	public int saveShareInfo(String yn_id) throws RadowException, AppException{
		String tphjselectH = this.getPageElement("tphjselectH").getValue();
		String unameH = this.getPageElement("unameH").getValue();
		
		String username = SysManagerUtils.getUserloginName();
		
		try {
			if(username.equals(unameH)){
				this.setMainMessage("无法共享当前用户！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			HBSession sess = HBUtil.getHBSession();
			String sql = "select t.username from SMT_USER t where t.loginname='"+unameH+"'";
			List<Object> list = sess.createSQLQuery(sql).list();
			if(list.size()==0){
				this.setMainMessage("用户名不存在！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			sess.createSQLQuery("update hz_JS2_YNTP_INFO set info02=?,info03=sysdate where yn_id=? and info01=?")
						.setString(0, unameH).setString(1, yn_id).setString(2, tphjselectH).executeUpdate();
			sess.flush();
			this.setNextEventName("memberGrid.dogridquery");
			this.getExecuteSG().addExecuteCode("Ext.getCmp('expFile').close()");
		}catch (Exception e) {
			this.setMainMessage("保存失败！"+e.getMessage());
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("unsaveShareInfo")
	@Transaction
	public int unsaveShareInfo(String yn_id) throws RadowException, AppException{
		
		
		
		try {
			HBSession sess = HBUtil.getHBSession();
			sess.createSQLQuery("update hz_JS2_YNTP_INFO set info02=null,info03=null where yn_id=? and info02 is not null")
						.setString(0, yn_id).executeUpdate();
			sess.flush();
			this.setNextEventName("memberGrid.dogridquery");
		}catch (Exception e) {
			this.setMainMessage("保存失败！"+e.getMessage());
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
}
