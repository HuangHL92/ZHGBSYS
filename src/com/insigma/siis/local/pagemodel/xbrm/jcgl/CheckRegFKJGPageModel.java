package com.insigma.siis.local.pagemodel.xbrm.jcgl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.hibernate.Query;

import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.NoRequiredValidate;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.event.EventMessageType;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.radow.util.PMPropertyCopyUtil;
import com.insigma.odin.framework.util.commform.CodeUtil;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.Checkreg;
import com.insigma.siis.local.business.entity.Checkregperson;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.utils.CommonQueryBS;

public class CheckRegFKJGPageModel extends PageModel {

	@Override
	public int doInit() throws RadowException {
		this.setNextEventName("memberGrid.dogridquery");
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
	@NoRequiredValidate
	public int doMemberQuery(int start,int limit) throws RadowException{
		String checkregid = this.getPageElement("checkregid").getValue();
		/*String sql="select * from CHECKREGPERSON t where t.checkregid='"+checkregid+
				"'  order by SORTID1,SORTID2 ";*/
		String sql="select * from CHECKREGPERSON t where checkregid='"+checkregid+
				"' and  CRP007='1' order by SORTID1 ";
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	@PageEvent("saveBtn")
	public int saveBtn() throws RadowException{
		String crp011 = this.getPageElement("crp011").getValue();
		String crp012 = this.getPageElement("crp012").getValue();
		String crp013 = this.getPageElement("crp013").getValue();
		String crp014 = this.getPageElement("crp014_combo").getValue();
		String crp015 = this.getPageElement("crp015").getValue();
		String crp016 = this.getPageElement("crp016_combo").getValue();
		String crp017 = this.getPageElement("crp017").getValue();
		String crp018 = this.getPageElement("crp018").getValue();
		
		HBSession sess  =HBUtil.getHBSession();
		Connection conn =  null;
		PreparedStatement pstmt = null;
		int count = 0;
		try {
			conn = sess.connection();
			conn.setAutoCommit(false);
			String sql = "update CHECKREGPERSON set crp011=?,crp012=?,crp013=?,crp014=?,"
					+ "crp015=?,crp016=?,crp017=?,crp018=? where crp000=?";
			pstmt = conn.prepareStatement(sql);
			List<HashMap<String,Object>> list = this.getPageElement("memberGrid").getValueList();
			if(list.size()>0) {
				for (int j = 0; j < list.size();j++) {
					HashMap<String, Object> map = list.get(j);
					Object check1 = map.get("pcheck");
					if (check1!= null && check1.equals(true)) {
						
						pstmt.setString(1, crp011);
						pstmt.setString(1, crp011);
						pstmt.setString(2, crp012);
						pstmt.setString(3, crp013);
						pstmt.setString(4, crp014);
						pstmt.setString(5, crp015);
						pstmt.setString(6, crp016);
						pstmt.setString(7, crp017);
						pstmt.setString(8, crp018);
						pstmt.setString(9, map.get("crp000")+"");
						pstmt.addBatch();
						
						count ++;
					}
				}
				if(count!=0) {
					pstmt.executeBatch();
					pstmt.clearBatch();
					pstmt.clearParameters();
				}
			}
			if(count==0) {
				this.setMainMessage("请选择信息！");
				this.setMessageType(EventMessageType.WARNING);
				return EventRtnType.NORMAL_SUCCESS;
			}
			conn.commit();
			this.setMainMessage("设置成功！");
			this.setNextEventName("memberGrid.dogridquery");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if(conn!=null)
					conn.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		} finally {
			try {
				if(pstmt!=null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(conn!=null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
				
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("save2Btn")
	public int save2Btn() throws RadowException{
		String crp011 = this.getPageElement("crp011").getValue();
		String crp012 = this.getPageElement("crp012").getValue();
		String crp013 = this.getPageElement("crp013").getValue();
		String crp014 = this.getPageElement("crp014_combo").getValue();
		String crp015 = this.getPageElement("crp015").getValue();
		String crp016 = this.getPageElement("crp016_combo").getValue();
		String crp017 = this.getPageElement("crp017").getValue();
		String crp018 = this.getPageElement("crp018").getValue();
		String crp000 = this.getPageElement("crp000").getValue();
		
		HBSession sess  =HBUtil.getHBSession();
		Connection conn =  null;
		PreparedStatement pstmt = null;
		try {
			if(crp000==null || crp000.trim().equals("")) {
				this.setMainMessage("请选择数据！");
				return EventRtnType.NORMAL_SUCCESS;
			}
			
			conn = sess.connection();
			conn.setAutoCommit(false);
			String sql = "update CHECKREGPERSON set crp011=?,crp012=?,crp013=?,crp014=?,"
					+ "crp015=?,crp016=?,crp017=?,crp018=? where crp000=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, crp011);
			pstmt.setString(1, crp011);
			pstmt.setString(2, crp012);
			pstmt.setString(3, crp013);
			pstmt.setString(4, crp014);
			pstmt.setString(5, crp015);
			pstmt.setString(6, crp016);
			pstmt.setString(7, crp017);
			pstmt.setString(8, crp018);
			pstmt.setString(9,crp000);
			pstmt.addBatch();
			
			pstmt.executeBatch();
			pstmt.clearBatch();
			pstmt.clearParameters();
		
			conn.commit();
			this.setMainMessage("设置成功！");
			this.setNextEventName("memberGrid.dogridquery");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if(conn!=null)
					conn.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		} finally {
			try {
				if(pstmt!=null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(conn!=null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
				
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	/**
	 * 选择显示人员
	 * @return
	 * @throws RadowException
	 */
	@PageEvent("rowPclick")
	@NoRequiredValidate
	public int rowPclick(String crp000) throws RadowException{
		HBSession sess = HBUtil.getHBSession();
		String checkregid = this.getPageElement("checkregid").getValue();
		try {
			if(crp000 != null && !crp000.equals("")) {
				Checkregperson crp = (Checkregperson) sess.get(Checkregperson.class, crp000);
				this.copyObjValueToElement(crp, this);
				/*if(crp.getCrp013()==null || crp.getCrp013().trim().equals("")) {
					List<A01> list = sess.createQuery(" from A01 where a0184='"+crp.getCrp006()+"'").list();
					if(list.size()>0) {
						A01 a01 = list.get(0);
						this.getPageElement("crp012").setValue(HBUtil.getCodeName("ZB125", a01.getA0160()));
						this.getPageElement("crp011").setValue(HBUtil.getCodeName("ZB148", a01.getA0192e()));
					}
				}*/
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RadowException("数据信息异常！");
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
}
