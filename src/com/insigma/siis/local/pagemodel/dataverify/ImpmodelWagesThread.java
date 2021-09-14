package com.insigma.siis.local.pagemodel.dataverify;


import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.siis.local.business.entity.Imprecord;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class ImpmodelWagesThread implements Runnable{
	
	private Imprecord  imp;
    public ImpmodelWagesThread(Imprecord  imp,String impno, CurrentUser user,UserVO userVo) {
        this.imp = imp;
    }

    @Override
	public void run() {
		HBSession sess = HBUtil.getHBSession();
		String imprecordid = imp.getImprecordid();
		String imptemptable = imp.getImptemptable().toUpperCase();
		Long sortid = 1L;
		String optype = "1";						//日志类型 1、接收
		String tempTable="a"+imptemptable+"a33";
		try {
			sess.beginTransaction();
			imp.setImpstutas("4");
			sess.update(imp);
			sess.flush();
			String A0000Sql = "";
			if(DBUtil.getDBType().equals(DBType.MYSQL)){
				new Thread(new ImpModLogThread(imptemptable+"A33", "工资信息数据比较", sortid++, optype, imprecordid, false)).start();
				sess.createSQLQuery(CommonQueryBS.sysOutRtn("create table "+tempTable+"  as  select a01.a0000 A0000,uuid(),"
						+ "A3310,A3321,A3321C,A3321D,A3322,A3323,A3331,A3332,A3333,A3350,A3361,A3362,A3371,A3372,A3381,A3382,A3385,A3360,'' gznx from A33"+imptemptable+" t LEFT JOIN a01 ON (t.a0101 = a01.a0101 )"
						+ " where case "
						+ "when (t.a0184 is null or t.a0184 ='') and (t.a0134 is not null and t.a0134 <> '') then t.a0134=a01.a0134 "
						+ "when (t.a0134 is null or t.a0134 ='') and (t.a0184 is not null and t.a0184 <> '')  then t.a0184=a01.a0184 "
						+ "when (t.a0134 is not null and t.a0134 <> '') and (t.a0184 is not null and t.a0184 <> '') then t.a0184=a01.a0184 "
						+ "end GROUP BY a01.a0000")).executeUpdate();
				
				new Thread(new ImpModLogThread("A33", "工资信息接收", sortid++, optype, imprecordid, false)).start();
				A0000Sql = "SELECT X.a0000 FROM " + tempTable + " X group by X.a0000";
				CommonQueryBS.systemOut("接收A33数据");
				sess.createSQLQuery(CommonQueryBS.sysOutRtn(" delete from a33 where A0000 in ("+A0000Sql+")")).executeUpdate();
				sess.createSQLQuery(CommonQueryBS.sysOutRtn("replace into a33 select * from "+tempTable)).executeUpdate();
			} else {
				new Thread(new ImpModLogThread(imptemptable+"A33", "工资信息数据比较", sortid++, optype, imprecordid, false)).start();
				sess.createSQLQuery(CommonQueryBS.sysOutRtn("create table "+tempTable+" as  select "
						+ "A0000,A3300，A3310,A3321,A3321C,A3321D,A3322,A3323,A3331,A3332,A3333,A3350,A3361,A3362,A3371,A3372,A3381,A3382,A3385,A3360,gznx  from (select "
						+ "ROW_NUMBER() OVER(PARTITION BY a01.a0000 ORDER BY a01.A0000) rn, a01.a0000 A0000, sys_guid() A3300,"
						+ "A3310,A3321,A3321C,A3321D,A3322,A3323,A3331,A3332,A3333,A3350,A3361,A3362,A3371,A3372,A3381,A3382,A3385,A3360,' ' gznx  from A33"+imptemptable+" t LEFT JOIN a01 ON (t.a0101 = a01.a0101 )"
						+ " where  ( "
						+ "(case   when(t.a0184 is null or t.a0184 = '') and (t.a0134 is not null or t.a0134 <> '')  then t.a0134 end)=a01.a0134  or "
						+ "(case   when(t.a0134 is null or t.a0134 = '') and (t.a0184 is not null or t.a0184 <> '')   then t.a0184 end)=a01.a0184  or "
						+ " (case   when(t.a0134 is not null or t.a0134 <> '') and (t.a0184 is not null or t.a0184 <> '')   then t.a0184 end)=a01.a0184 "
						+ ")) where rn='1'")).executeUpdate();
				new Thread(new ImpModLogThread("A33", "工资信息接收", sortid++, optype, imprecordid, false)).start();
				A0000Sql = "SELECT X.a0000 FROM " + tempTable + " X group by X.a0000";
				CommonQueryBS.systemOut("接收A33数据");
				sess.createSQLQuery(CommonQueryBS.sysOutRtn(" delete from a33 where A0000 in ("+A0000Sql+")")).executeUpdate();
				sess.createSQLQuery(CommonQueryBS.sysOutRtn("MERGE INTO a33 a USING "+tempTable+" t ON (a.A3300 = t.A3300)  WHEN MATCHED THEN UPDATE"+
						" SET a.A0000=t.A0000,a.A3310=t.A3310,a.A3321=t.A3321,a.A3321C=t.A3321C,a.A3321D=t.A3321D,a.A3322=t.A3322,a.A3323=t.A3323,a.A3331=t.A3331,a.A3332=t.A3332,a.A3333=t.A3333,a.A3350=t.A3350,a.A3361=t.A3361,a.A3362=t.A3362,a.A3371=t.A3371,a.A3372=t.A3372,a.A3381=t.A3381,a.A3382=t.A3382,a.A3385=t.A3385,a.A3360=t.A3360,a.GZNX=t.GZNX "+
						"  WHEN NOT MATCHED THEN INSERT (a.A0000,a.A3300,a.A3310,a.A3321,a.A3321C,a.A3321D,a.A3322,a.A3323,a.A3331,a.A3332,a.A3333,a.A3350,a.A3361,a.A3362,a.A3371,a.A3372,a.A3381,a.A3382,a.A3385,a.A3360,a.GZNX)" +
						" VALUES (t.A0000,t.A3300,t.A3310,t.A3321,t.A3321C,t.A3321D,t.A3322,t.A3323,t.A3331,t.A3332,t.A3333,t.A3350,t.A3361,t.A3362,t.A3371,t.A3372,t.A3381,t.A3382,t.A3385,t.A3360,t.GZNX)"+
						" ")).executeUpdate();
			}
			imp.setImpstutas("2");
			sess.update(imp);
			sess.getTransaction().commit();
			new Thread(new ImpModLogThread("DONE", "迁移完成", sortid++, optype, imprecordid, true)).start();
			sess.flush();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				new Thread(new ImpModLogThread("DONE", "迁移异常", sortid++, optype, imprecordid, true)).start();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			if(sess!=null){
				try {
					sess.getTransaction().rollback();
					imp.setImpstutas("1");
					sess.update(imp);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			e.printStackTrace();
		}finally{
			try {
				sess.createSQLQuery(" drop table "+tempTable+"").executeUpdate();
			} catch (Exception e2) {}
			try {
				sess.createSQLQuery(" drop table a33"+imptemptable+"").executeUpdate();
			} catch (Exception e2) {}
			if(sess != null){
				sess.close();
			}
		}
		
	}
}
