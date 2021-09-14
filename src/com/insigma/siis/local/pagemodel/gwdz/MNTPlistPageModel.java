package com.insigma.siis.local.pagemodel.gwdz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.PageModel;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.radow.annotation.AutoNoMask;
import com.insigma.odin.framework.radow.annotation.GridDataRange;
import com.insigma.odin.framework.radow.annotation.PageEvent;
import com.insigma.odin.framework.radow.annotation.Transaction;
import com.insigma.odin.framework.radow.annotation.GridDataRange.GridData;
import com.insigma.odin.framework.radow.element.Grid;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.SysManagerUtils;
import com.insigma.siis.local.pagemodel.comm.CommQuery;

public class MNTPlistPageModel extends PageModel {
	@Override
	public int doInit() throws RadowException {
		
		
		this.setNextEventName("initX");
		
		return EventRtnType.NORMAL_SUCCESS;
	}
	@PageEvent("initX")
	public int initX() throws RadowException, AppException{
		String fabd00 = this.getPageElement("fabd00").getValue();
		String userid = this.getCurUserid();
		try{
			if(StringUtils.isEmpty(fabd00)){
				fabd00 = UUID.randomUUID().toString();
				HBUtil.executeUpdate("insert into HZ_MNTP_FABD(fabd00,fabd02,fabd04)"
						+ " values(?,?,?)",new Object[]{fabd00,"模拟情况比对",userid});
				this.getPageElement("fabd00").setValue(fabd00);
				this.getPageElement("fabd02").setValue("模拟情况比对");
			}else{
				String fabd02 = HBUtil.getValueFromTab("fabd02", "HZ_MNTP_FABD", "fabd00='"+fabd00+"'");
				this.getPageElement("fabd02").setValue(fabd02);
			}
		} catch (Exception e) {
			this.setMainMessage("查询失败！");
			e.printStackTrace();
		}
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
	public int doMemberQuery(int start,int limit) throws RadowException{
		String fabd00 = this.getPageElement("fabd00").getValue();
		String sql="select famx00,fabd00,famx01,mntp00,famx02,"
				+ " (select mntp04 from hz_mntp m where m.mntp00=t.mntp00) mntp04  "
				+ " from HZ_MNTP_FABD_famx t where t.fabd00='"+fabd00+"' order by t.FAMX01,t.famx02";
		this.pageQuery(sql, "SQL", start, limit);
		return EventRtnType.SPE_SUCCESS;
	}
	
	@PageEvent("memberGrid.rowclick")
	@GridDataRange
	public int persongridOnRowDbClick() throws Exception{  //打开窗口的实例
		String famx00 = (String)this.getPageElement("memberGrid").getValueList().get(this.getPageElement("memberGrid").getCueRowIndex()).get("famx00");
		this.getPageElement("famx00").setValue(famx00);
//		ReturnFA("fa4ce239-cab9-43af-9c7c-3d90904cf722");
		return EventRtnType.NORMAL_SUCCESS;		
	}
	
	@PageEvent("infoDelete")
	@Transaction
	public int infoDelete() throws RadowException, AppException{
		String famx00=this.getPageElement("famx00").getValue();
		HBSession sess = HBUtil.getHBSession();
		try {
			HBUtil.executeUpdate("delete from  HZ_MNTP_FABD_org where famx00='"+famx00+"'");
			HBUtil.executeUpdate("delete from  HZ_MNTP_FABD_famx where famx00='"+famx00+"'");
			sess.flush();
			this.getPageElement("famx00").setValue("");
		}catch (Exception e) {
			this.setMainMessage("删除失败！"+e.getMessage());
			e.printStackTrace();
		}
		this.setNextEventName("memberGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	public static List ReturnFA(String fabd00) throws Exception {
		ArrayList<List> fabdList= new ArrayList<List>();
		CommQuery cqbs=new CommQuery();
//		List<HashMap<String,String>> famxList;
		try {
			@SuppressWarnings("unchecked")
			List<String> famx00s= HBUtil.getHBSession().createSQLQuery("select famx00 from HZ_MNTP_FABD_famx where fabd00='"+fabd00+"' order by famx01, famx02").list();
			if(famx00s.size()>0) {
				for(int i=0;i<famx00s.size();i++) {
					String famx00=famx00s.get(i);
					String sql="select x.famx00,x.famx01,x.mntp00,g.b0111,g.b0131 " + 
							"from HZ_MNTP_FABD_famx x ,HZ_MNTP_FABD_ORG g " + 
							"where g.famx00='"+famx00+"' " + 
							"and x.famx00=g.famx00  order by  b0111,b0131";
					List<HashMap<String, Object>> famxList;
					famxList = cqbs.getListBySQL(sql);
					if(famxList.size()>0){
						fabdList.add(famxList);
					}
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
		}	
		return fabdList;
	}
	
	@PageEvent("memberGrid.afteredit")
	@GridDataRange(GridData.cuerow)
	@Transaction
	@AutoNoMask
	public int dogrid6AfterEdit() throws Exception {
		//获取页面数据
		Grid grid6 = (Grid) this.getPageElement("memberGrid");
		String famx00 = grid6.getValue("famx00") +"";
		String famx01 = grid6.getValue("famx01") +"";
		HBSession sess = HBUtil.getHBSession();
		
		sess.createSQLQuery("update HZ_MNTP_FABD_famx set famx01=? where famx00=?")
		.setString(0, famx01).setString(1, famx00).executeUpdate();
		//切换类型 清掉单位信息
		HBUtil.executeUpdate("delete HZ_MNTP_FABD_org where famx00=?",new Object[]{famx00});
		//切换类型 清掉单位信息
		HBUtil.executeUpdate("update HZ_MNTP_FABD_famx set mntp00='' where famx00=?",new Object[]{famx00});
		sess.flush();
		this.setNextEventName("memberGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	
	@PageEvent("saveFABD")
	@Transaction
	public int saveFABD() throws RadowException, AppException{
		//String userid = this.getCurUserid();
		//famx00 fabd00 famx01 mntp00
		String fabd00 = this.getPageElement("fabd00").getValue();
		String famx00 = UUID.randomUUID().toString();
		try {
			HBSession sess = HBUtil.getHBSession();
			@SuppressWarnings("unchecked")
			List<String> famx02s= HBUtil.getHBSession().createSQLQuery("select famx02 from HZ_MNTP_FABD_famx " + 
					"  where fabd00='"+fabd00+"' order by famx02 desc").list();
			int famx02=1;
			if(famx02s.size()>0) {
				if(famx02s.get(0)!=null && !"".equals(famx02s.get(0))) {
					famx02=Integer.valueOf(String.valueOf(famx02s.get(0)))+1;
				}
			}
			sess.createSQLQuery("insert into HZ_MNTP_FABD_famx(famx00,fabd00,famx01,famx02) values(?,?,?,?)")
			.setString(0, famx00).setString(1, fabd00).setString(2, "1").setInteger(3, famx02).executeUpdate();
//			sess.createSQLQuery("insert into HZ_MNTP_FABD_famx(famx00,fabd00,famx01) values(?,?,?)")
//			.setString(0, famx00).setString(1, fabd00).setString(2, "1").executeUpdate();
			sess.flush();
			this.setNextEventName("rolesort");
			//this.setNextEventName("memberGrid.dogridquery");
			//this.getExecuteSG().addExecuteCode("openKqzsdw();");
			
		}catch (Exception e) {
			this.setMainMessage("保存失败！"+e.getMessage());
			e.printStackTrace();
		}
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("saveFabd02")
	@Transaction
	public int saveFabd02() throws Exception{
		String fabd00 = this.getPageElement("fabd00").getValue();
		String fabd02 = this.getPageElement("fabd02").getValue();
		HBUtil.executeUpdate("update HZ_MNTP_FABD set fabd02=?"
				+ " where fabd00=?",new Object[]{fabd02,fabd00});
		return EventRtnType.NORMAL_SUCCESS;
	}
	
	@PageEvent("rolesort")
	@Transaction
	public int publishsort() throws RadowException {
		List<HashMap<String, String>> list = this.getPageElement("memberGrid").getStringValueList();
		HBSession sess = HBUtil.getHBSession();
		Connection con = null;
		try {
			con = sess.connection();
			con.setAutoCommit(false);
			String sql = "update HZ_MNTP_FABD_FAMX set famx02 = ? where famx00=?";
			PreparedStatement ps = con.prepareStatement(sql);
			int i = 1;
			for (HashMap<String, String> m : list) {
				String zwzc00 = m.get("famx00");
				ps.setInt(1, i);
				ps.setString(2, zwzc00);
				ps.addBatch();
				i++;
			}
			ps.executeBatch();
			con.commit();
			ps.close();
			con.close();
		} catch (Exception e) {
			try {
				con.rollback();
				if (con != null) {
					con.close();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			this.setMainMessage("排序失败！");
			return EventRtnType.FAILD;
		}
		this.toastmessage("排序已保存！");
		this.setNextEventName("memberGrid.dogridquery");
		return EventRtnType.NORMAL_SUCCESS;
	}
	

}
