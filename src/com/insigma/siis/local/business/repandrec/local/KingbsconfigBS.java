package com.insigma.siis.local.business.repandrec.local;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.hsqldb.lib.StringUtil;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.BSSupport;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.radow.RadowException;
import com.insigma.odin.framework.util.GlobalNames;
import com.insigma.siis.local.business.entity.Dataexchangeconf;
import com.insigma.siis.local.business.entity.ImpProcess;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.utils.kingbs.KingBSImpUtil;
import com.kingbase.jdbc4.Jdbc4PoolingDataSource;

public class KingbsconfigBS extends BSSupport{

	public static Dataexchangeconf getConfig() {
		HBSession sess = HBUtil.getHBSession();
		List<Dataexchangeconf> list = sess.createQuery("from Dataexchangeconf").list();
		if (list !=null && list.size()>0) {
			return list.get(0);
		}
		return null;
	}

	public static void saveOrUpdate(Dataexchangeconf conf) throws Exception {
		HBSession sess = HBUtil.getHBSession();
		try {
			sess.beginTransaction();
			if(conf.getDataexechangeconfid()!=null && !conf.getDataexechangeconfid().equals("")){
				sess.update(conf);
			} else {
				sess.save(conf);
			}
			try {
				KingBSImpUtil.initKingbs(conf);
			} catch (Exception e) {
				throw new AppException("�������ӱ�׼�����ݿ⣬����Ӧ���Ƿ�װ��û����ݿ�����ѿ�����");
			}
			/*File file = new File(conf.getZzbthreepath());
			if(!file.exists()){
				file.mkdirs();
			}*/
			sess.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			if(sess!=null)
				sess.getTransaction().rollback();
			throw e;
		}
	}
	
/*	public static void initKingbs(Dataexchangeconf conf) throws SQLException, RadowException {
		Jdbc4PoolingDataSource ds = new Jdbc4PoolingDataSource();
		Connection conn = null;
		String KING_BD = conf.getKingbsdb();
		String KING_BD_USER = conf.getKingbsuser();
		String KING_BD_PWD = conf.getKingbspwd();;
		String KING_BD_PORT = conf.getKingbsport();
		String KING_BD_SERVER = conf.getKingbsserverip();
		try {
			if (StringUtil.isEmpty(KING_BD) || StringUtil.isEmpty(KING_BD_USER)|| StringUtil.isEmpty(KING_BD_PWD)
					|| StringUtil.isEmpty(KING_BD_PORT)|| StringUtil.isEmpty(KING_BD_SERVER)) {
				throw new RadowException("������ݿ�������Ч����������д������ϵ����Ա��");
			}
			ds.setServerName(KING_BD_SERVER);
			ds.setDatabaseName(KING_BD);
			ds.setUser(KING_BD_USER);
			ds.setPassword(KING_BD_PWD);
			ds.setMaxConnections(10);
			ds.setInitialConnections(10);
			ds.setPortNumber(Integer.parseInt(KING_BD_PORT));
			conn = ds.getConnection();
			Statement stmt1 = conn.createStatement();
			try {
				stmt1.execute("DROP DATABASE ZWHZYQ;");
			} catch (Exception e) {
				e.printStackTrace();
			}
			try {
				stmt1.execute("DROP user ZWHZYQ;");
			} catch (Exception e) {
				e.printStackTrace();
			}
			Statement stmt = conn.createStatement();
			stmt.execute("create user ZWHZYQ with superuser password '1234';");
			stmt.execute("CREATE DATABASE ZWHZYQ with OWNER=ZWHZYQ ENCODING='GBK';");
			stmt.close();
			conn.close();
			ds.close();
		} catch (SQLException e) {
			throw e;
		}
		
	}*/

	public static void saveImpDetailInit(String imprecordid) throws Exception {
		HBSession sess = HBUtil.getHBSession();
		try {
			sess.beginTransaction();
			ImpProcess pro = new ImpProcess();
			pro.setStarttime(DateUtil.getTimestamp());
			pro.setImprecordid(imprecordid);
			pro.setProcessinfo("��ѹ��");
			pro.setProcessname("��ѹ�ļ�");
			pro.setProcesstype("1");
			pro.setProcessstatus("1");
			sess.save(pro);
			ImpProcess pro2 = new ImpProcess();
			pro2.setImprecordid(imprecordid);
			pro2.setProcessname("�����м��");
			pro2.setProcesstype("2");
			pro2.setProcessstatus("0");
			sess.save(pro2);
			ImpProcess pro3 = new ImpProcess();
			pro3.setImprecordid(imprecordid);
			pro3.setProcessname("����Ӧ�����ݿ�");
			pro3.setProcesstype("3");
			pro3.setProcessstatus("0");
			sess.save(pro3);
			sess.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	public static void saveImpDetail(String type, String status,
			String info, String imprecordid) throws Exception {
		HBSession sess = HBUtil.getHBSession();
		try {
//			sess.beginTransaction();
			List<ImpProcess> pros = sess.createQuery(" from ImpProcess where processtype='"+type+"' and imprecordid='" + imprecordid+ "'").list();
			ImpProcess pro = pros.get(0);
			pro.setProcessstatus(status);
			pro.setProcessinfo(info);
			if(status.equals("1")&& pro.getStarttime()==null){
				pro.setStarttime(DateUtil.getTimestamp());
			} else if(status.equals("2")){
				pro.setEndtime(DateUtil.getTimestamp());
			}
			sess.update(pro);
			sess.flush();
//			sess.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	public static void saveImpDetailInit2(String imprecordid) throws Exception {
		HBSession sess = HBUtil.getHBSession();
		try {
			sess.beginTransaction();
			ImpProcess pro = new ImpProcess();
			pro.setStarttime(DateUtil.getTimestamp());
			pro.setImprecordid(imprecordid);
			pro.setProcessinfo("��ѹ��");
			pro.setProcessname("��ѹ�ļ�");
			pro.setProcesstype("1");
			pro.setProcessstatus("1");
			sess.save(pro);
			ImpProcess pro2 = new ImpProcess();
			pro2.setImprecordid(imprecordid);
			pro2.setProcessname("��������");
			pro2.setProcesstype("2");
			pro2.setProcessstatus("0");
			sess.save(pro2);
			ImpProcess pro3 = new ImpProcess();
			pro3.setImprecordid(imprecordid);
			pro3.setProcessname("�����ļ���������");
			pro3.setProcesstype("3");
			pro3.setProcessstatus("0");
			sess.save(pro3);
			sess.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	public static void saveImpDetailInit3(String imprecordid) throws Exception {
		HBSession sess = HBUtil.getHBSession();
		try {
			sess.beginTransaction();
			ImpProcess pro = new ImpProcess();
			pro.setStarttime(DateUtil.getTimestamp());
			pro.setImprecordid(imprecordid);
			pro.setProcessinfo("�������");
			pro.setProcessname("�����");
			pro.setProcesstype("1");
			pro.setProcessstatus("1");
			sess.save(pro);
			ImpProcess pro2 = new ImpProcess();
			pro2.setImprecordid(imprecordid);
			pro2.setProcessname("�����ļ�");
			pro2.setProcesstype("2");
			pro2.setProcessstatus("0");
			sess.save(pro2);
			ImpProcess pro3 = new ImpProcess();
			pro3.setImprecordid(imprecordid);
			pro3.setProcessname("ѹ���ļ�");
			pro3.setProcesstype("3");
			pro3.setProcessstatus("0");
			sess.save(pro3);
			sess.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public static void saveImpDetail(String type, String status,
			String info, String imprecordid, String downfile) throws Exception {
		HBSession sess = HBUtil.getHBSession();
		try {
//			sess.beginTransaction();
			List<ImpProcess> pros = sess.createQuery(" from ImpProcess where processtype='"+type+"' and imprecordid='" + imprecordid+ "'").list();
			ImpProcess pro = pros.get(0);
			pro.setProcessstatus(status);
			pro.setProcessinfo(info);
			pro.setAddfidld1(downfile);		// downfile �����������ļ��� ���ձ�ʾorgdataverify �ϱ���ʾdatarep
			if(status.equals("1")&& pro.getStarttime()==null){
				pro.setStarttime(DateUtil.getTimestamp());
			} else if(status.equals("2")){
				pro.setEndtime(DateUtil.getTimestamp());
			}
			sess.update(pro);
			sess.flush();
//			sess.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	public static void saveImpDetailInit4(String imprecordid) throws Exception {
		HBSession sess = HBUtil.getHBSession();
		try {
			sess.beginTransaction();
			ImpProcess pro = new ImpProcess();
			pro.setStarttime(DateUtil.getTimestamp());
			pro.setImprecordid(imprecordid);
			pro.setProcessinfo("�������");
			pro.setProcessname("�����");
			pro.setProcesstype("1");
			pro.setProcessstatus("1");
			sess.save(pro);
			ImpProcess pro2 = new ImpProcess();
			pro2.setImprecordid(imprecordid);
			pro2.setProcessname("��׼�����ݲ�ѯ");
			pro2.setProcesstype("2");
			pro2.setProcessstatus("0");
			sess.save(pro2);
			ImpProcess pro3 = new ImpProcess();
			pro3.setImprecordid(imprecordid);
			pro3.setProcessname("����Ӧ�����ݿ�");
			pro3.setProcesstype("3");
			pro3.setProcessstatus("0");
			sess.save(pro3);
			sess.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
	
	public static void saveImpDetailInit5(String imprecordid) throws Exception {
		HBSession sess = HBUtil.getHBSession();
		try {
			sess.beginTransaction();
			ImpProcess pro = new ImpProcess();
			pro.setStarttime(DateUtil.getTimestamp());
			pro.setImprecordid(imprecordid);
			pro.setProcessinfo("������");
			pro.setProcessname("�����ļ���Ϣ");
			pro.setProcesstype("1");
			pro.setProcessstatus("1");
			sess.save(pro);
			ImpProcess pro2 = new ImpProcess();
			pro2.setImprecordid(imprecordid);
			pro2.setProcessname("��������");
			pro2.setProcesstype("2");
			pro2.setProcessstatus("0");
			sess.save(pro2);
			ImpProcess pro3 = new ImpProcess();
			pro3.setImprecordid(imprecordid);
			pro3.setProcessname("����У��");
			pro3.setProcesstype("3");
			pro3.setProcessstatus("0");
			sess.save(pro3);
			sess.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}
}
