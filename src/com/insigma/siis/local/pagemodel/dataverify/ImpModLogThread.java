package com.insigma.siis.local.pagemodel.dataverify;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.siis.local.business.entity.Datarecrejlog;
import com.insigma.siis.local.business.entity.Imprecord;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.epsoft.util.LogUtil;

public class ImpModLogThread implements Runnable {

	String table;
	String detail;
	Long sortid;
	String optype;
	String imprecordid;
	boolean islast;
    public ImpModLogThread(String table, String detail,Long sortid, String optype,String imprecordid, boolean islast) {
        this.table = table;
        this.detail = detail;
        this.sortid = sortid;
        this.optype = optype;
        this.imprecordid = imprecordid;
        this.islast = islast;
    }

	@Override
	public void run() {
		HBSession sess = HBUtil.getHBSession();
		try {
			sess.beginTransaction();
			List<Datarecrejlog> pros = sess.createQuery(" from Datarecrejlog where sortid<="+(sortid-1)+" and opstatus='1' and imprecordid='" + imprecordid+ "'").list();
			if(pros!=null && pros.size()>0){
				for (Datarecrejlog datarecrejlog : pros) {
					datarecrejlog.setEndtime(DateUtil.getTimestamp());
					datarecrejlog.setOpstatus("2");
					sess.update(datarecrejlog);
				}
			}
			if(!islast){
				Datarecrejlog pro = new Datarecrejlog();
				pro.setStarttime(DateUtil.getTimestamp());
				pro.setDatatable(table);
				pro.setDetail(detail);
				pro.setImprecordid(imprecordid);
				pro.setSortid(sortid);
				pro.setOptype(optype);
				pro.setOpstatus("1");
				sess.save(pro);
			} else {
				Datarecrejlog pro = new Datarecrejlog();
				pro.setStarttime(DateUtil.getTimestamp());
				pro.setDatatable(table);
				pro.setDetail(detail);
				pro.setImprecordid(imprecordid);
				pro.setSortid(sortid);
				pro.setOptype(optype);
				pro.setEndtime(DateUtil.getTimestamp());
				pro.setOpstatus("2");
				sess.save(pro);
			}
//			sess.flush();
			sess.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(sess != null){
				sess.close();
			}
		}
	}
}
