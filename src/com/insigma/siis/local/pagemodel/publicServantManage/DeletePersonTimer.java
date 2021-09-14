package com.insigma.siis.local.pagemodel.publicServantManage;

import java.math.BigDecimal;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.taglib.util.TagConst;
import com.insigma.siis.local.business.entity.A32;
import com.insigma.siis.local.business.sysmanager.ZWHZYQ_001_007.ZWHZYQ_001_007_BS;
import com.insigma.siis.local.jftp.JFTPClient;


public class DeletePersonTimer implements ServletContextListener{
	
	private static final Logger  log = Logger.getLogger(JFTPClient.class);
	private  void deletePersonTimer(final long firstinterval, final int interval) {  
		try{
	        Runnable runnable = new Runnable() {  
	            public void run() { 
	            	exec();
	            	log.info("ɾ����ɣ���һ��ִ����"+interval/3600/24+"���ִ��");
	            }  
	        };  
	        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();  
	        // �ڶ�������Ϊ�״�ִ�е���ʱʱ�䣬����������Ϊ��ʱִ�еļ��ʱ��  
	        service.scheduleAtFixedRate(runnable, firstinterval, interval, TimeUnit.SECONDS);  
		}catch(Exception e){
			log.info("��Ա������ʱ����ɾ����ʱjob����ʧ�ܣ�"+e.getMessage());
        }
        
    }

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		TagConst.TEXT_STYLE_WIDTH="100%";
		log.info("�ı������ԭ����97%������100%");
		
		Enumeration em =  arg0.getServletContext().getInitParameterNames();
		log.info("��ʼ����ѯ�����ʱ��");
		try {
			HBUtil.executeUpdate("truncate table A01SEARCHTEMP");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		log.info("��Ա������ʱ����ɾ����ʱjob����");
		int interval = 24*3600*30;
		long now = System.currentTimeMillis();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();  
		calendar.add(Calendar.DATE, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 3);  
		calendar.set(Calendar.MINUTE, 0);  
		calendar.set(Calendar.SECOND, 0);  
		log.info("��һ��ִ��ʱ�䣺"+formatter.format(calendar.getTime()));
		long start = calendar.getTime().getTime();
		long firstinterval = (start-now)/1000;
		log.info("���һ��ִ��ʱ�仹�У�"+firstinterval);
		deletePersonTimer(firstinterval,interval);
		//tjinfo(firstinterval+500, interval);
		//bzfxinfo(firstinterval, interval);
		//exectjinfo();
		/*try {
			ZWHZYQ_001_007_BS bs = new ZWHZYQ_001_007_BS();
			bs.refreshUsersLst();
			log.info("ˢ��users.lst���û����������");
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		/*try {
			HBUtil.executeUpdate("delete from USER_AUTHENTICATION");
			log.info("��ʼ���û���֤�����");
		} catch (Exception e) {
			e.printStackTrace();
		}*/
	}
	
	
	
	public static void exec(){
		try {
			HBSession sess = HBUtil.getHBSession();
			String delA01SQL = "select a0000 from a01 where status='4' ";
			List<String> a0000slist = sess.createSQLQuery(delA01SQL).list();
			if(a0000slist==null || a0000slist.size()==0){
				return;
			}
			if(a0000slist.size()>0 && a0000slist.size()<900){
				StringBuffer sb = new StringBuffer("");
				for(String a0000 : a0000slist){
					sb.append("'"+a0000+"',");
				}
				sb.deleteCharAt(sb.length()-1);
				HBUtil.executeUpdate("delete from a02  where a0000 in("+sb+")");
				log.info("ɾ����Ա������ʱ����(a02)");
				HBUtil.executeUpdate("delete from a05 where a0000 in("+sb+")");
	        	log.info("ɾ����Ա������ʱ����(a05)");
				HBUtil.executeUpdate("delete from a06 where a0000 in("+sb+")");
	        	log.info("ɾ����Ա������ʱ����(a06)");
	        	HBUtil.executeUpdate("delete from a08  where a0000 in("+sb+")");
	        	log.info("ɾ����Ա������ʱ����(a08)");
	        	HBUtil.executeUpdate("delete from a14  where a0000 in("+sb+")");
	        	log.info("ɾ����Ա������ʱ����(a14)");
	        	HBUtil.executeUpdate("delete from a15  where a0000 in("+sb+")");
	        	log.info("ɾ����Ա������ʱ����(a15)");
	        	HBUtil.executeUpdate("delete from a36  where a0000 in("+sb+")");
	        	log.info("ɾ����Ա������ʱ����(a36)");
	    		HBUtil.executeUpdate("delete from a11  where exists (select a1100 from a41 t where a11.a1100=t.a1100 and a0000 in("+sb+")) ");
	    		log.info("ɾ����Ա������ʱ����(a11)");
	    		HBUtil.executeUpdate("delete from a41  where a0000 in("+sb+")");
	    		log.info("ɾ����Ա������ʱ����(a41)");
	    		HBUtil.executeUpdate("delete from a29  where a0000 in("+sb+")");
	    		log.info("ɾ����Ա������ʱ����(a29)");
	    		HBUtil.executeUpdate("delete from a53  where a0000 in("+sb+")");
	    		log.info("ɾ����Ա������ʱ����(a53)");
	    		HBUtil.executeUpdate("delete from a37  where a0000 in("+sb+")");
	    		log.info("ɾ����Ա������ʱ����(a37)");
	    		HBUtil.executeUpdate("delete from a31  where a0000 in("+sb+")");
	    		log.info("ɾ����Ա������ʱ����(a31)");
	    		HBUtil.executeUpdate("delete from a30  where a0000 in("+sb+")");
	    		log.info("ɾ����Ա������ʱ����(a30)");
	    		HBUtil.executeUpdate("delete from a57  where a0000 in("+sb+")");
	    		log.info("ɾ����Ա������ʱ����(a57)");
	    		HBUtil.executeUpdate("delete from A99Z1  where a0000 in("+sb+")");
	    		log.info("ɾ����Ա������ʱ����(a99Z1)");
	    		HBUtil.executeUpdate("delete from a01 where status='4' ");  
	    		log.info("ɾ����Ա������ʱ����(a01)");
				HBUtil.getHBSession().flush();
			}else{
				HBUtil.executeUpdate("delete from a02  where exists (select a0000 from a01 t where t.a0000=a02.a0000 and t.status='4'  )");
	        	log.info("ɾ����Ա������ʱ����(a02)");
	        	HBUtil.executeUpdate("delete from a05  where exists (select a0000 from a01 t where t.a0000=a05.a0000 and t.status='4'  )");
	        	log.info("ɾ����Ա������ʱ����(a05)");
	        	HBUtil.executeUpdate("delete from a06  where exists (select a0000 from a01 t where t.a0000=a06.a0000 and t.status='4'  )");
	        	log.info("ɾ����Ա������ʱ����(a06)");
	        	HBUtil.executeUpdate("delete from a08  where exists (select a0000 from a01 t where t.a0000=a08.a0000 and t.status='4'  )");
	        	log.info("ɾ����Ա������ʱ����(a08)");
	        	HBUtil.executeUpdate("delete from a14  where exists (select a0000 from a01 t where t.a0000=a14.a0000 and t.status='4'  )");
	        	log.info("ɾ����Ա������ʱ����(a14)");
	        	HBUtil.executeUpdate("delete from a15  where exists (select a0000 from a01 t where t.a0000=a15.a0000 and t.status='4'  )");
	        	log.info("ɾ����Ա������ʱ����(a15)");
	        	HBUtil.executeUpdate("delete from a36  where exists (select a0000 from a01 t where t.a0000=a36.a0000 and t.status='4'  )");
	        	log.info("ɾ����Ա������ʱ����(a36)");
	    		HBUtil.executeUpdate("delete from a11  where exists (select a1100 from a41 t where a11.a1100=t.a1100 and exists (select a0000 from a01 t1 where t1.a0000=t.a0000 and t1.status='4'  )) ");
	    		log.info("ɾ����Ա������ʱ����(a11)");
	    		HBUtil.executeUpdate("delete from a41  where exists (select a0000 from a01 t where t.a0000=a41.a0000 and t.status='4'  )");
	    		log.info("ɾ����Ա������ʱ����(a41)");
	    		HBUtil.executeUpdate("delete from a29  where exists (select a0000 from a01 t where t.a0000=a29.a0000 and t.status='4'  )");
	    		log.info("ɾ����Ա������ʱ����(a29)");
	    		HBUtil.executeUpdate("delete from a53  where exists (select a0000 from a01 t where t.a0000=a53.a0000 and t.status='4'  )");
	    		log.info("ɾ����Ա������ʱ����(a53)");
	    		HBUtil.executeUpdate("delete from a37  where exists (select a0000 from a01 t where t.a0000=a37.a0000 and t.status='4'  )");
	    		log.info("ɾ����Ա������ʱ����(a37)");
	    		HBUtil.executeUpdate("delete from a31  where exists (select a0000 from a01 t where t.a0000=a31.a0000 and t.status='4'  )");
	    		log.info("ɾ����Ա������ʱ����(a31)");
	    		HBUtil.executeUpdate("delete from a30  where exists (select a0000 from a01 t where t.a0000=a30.a0000 and t.status='4'  )");
	    		log.info("ɾ����Ա������ʱ����(a30)");
	    		HBUtil.executeUpdate("delete from a57  where exists (select a0000 from a01 t where t.a0000=a57.a0000 and t.status='4'  )");
	    		log.info("ɾ����Ա������ʱ����(a57)");
	    		HBUtil.executeUpdate("delete from A99Z1  where exists (select a0000 from a01 t where t.a0000=A99Z1.a0000 and t.status='4'  )");
	    		log.info("ɾ����Ա������ʱ����(a99Z1)");
	    		HBUtil.executeUpdate("delete from a01 where status='4' ");  
	    		log.info("ɾ����Ա������ʱ����(a01)");
	    		HBUtil.getHBSession().flush();
			}
        	
    		
    	} catch (AppException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private  void tjinfo(final long firstinterval, final int interval) {  
		//add 20180328 zepeng �ĳ�1��ִ��һ�ι���̨ͳ��
		final int intervalStep = 2*3600*24;
		try{
	        Runnable runnable = new Runnable() {  
	            public void run() { 
	            	/*exectjinfo();
	            	log.info("ͳ����ɣ���һ��ִ����"+intervalStep/3600/24+"���ִ��");*/
	            }  
	        };  
	        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();  
	        // �ڶ�������Ϊ�״�ִ�е���ʱʱ�䣬����������Ϊ��ʱִ�еļ��ʱ��  
	        service.scheduleAtFixedRate(runnable, firstinterval, intervalStep, TimeUnit.SECONDS);  
		}catch(Exception e){
			log.info("ͳ�ƶ�ʱjob����ʧ�ܣ�"+e.getMessage());
        }
        
    }

	protected void exectjinfo() {
		try {
			log.info("����������״ͼͳ������");
			HBUtil.executeUpdate("truncate table zzt_a01 ");
			HBUtil.executeUpdate("insert into zzt_a01  SELECT count(a.a0000) num, (select sub_code_value from code_value t where code_type = 'ZB09' and code_status = '1' "
				+ "and sub_code_value != '-1' and code_value = a.a0221) sub_code_value, (select code_name3 from code_value t where t.code_type = 'ZB09' "
				+ "and code_leaf = '1' and t.code_status = '1' and code_value = a.a0221) code_name, a.A0221 FROM (SELECT a01.a0000, a01.A0221 "
				+ "FROM A01 a01 where a01.a0000 in (select a02.a0000 from a02 where a02.a0255 = '1' ) and a01.a0163 = '1' ) a "
				+ "group by A0221 order by a0221 asc");
			log.info("������״ͼͳ���������");
		} catch (AppException e) {
			try {
				HBUtil.executeUpdate("create  table zzt_a01 as SELECT count(a.a0000) num, (select sub_code_value from code_value t where code_type = 'ZB09' and code_status = '1' "
						+ "and sub_code_value != '-1' and code_value = a.a0221) sub_code_value, (select code_name3 from code_value t where t.code_type = 'ZB09' "
						+ "and code_leaf = '1' and t.code_status = '1' and code_value = a.a0221) code_name, a.A0221 FROM (SELECT a01.a0000, a01.A0221 "
						+ "FROM A01 a01 where a01.a0000 in (select a02.a0000 from a02 where a02.a0255 = '1' ) and a01.a0163 = '1' ) a "
						+ "group by A0221 order by a0221 asc");
				log.info("������״ͼͳ���������");
			} catch (AppException e1) {
				log.info("��״ͼͳ��ִ��ʧ�ܣ�"+e.getMessage());
				e1.printStackTrace();
			}
			//e.printStackTrace();
		}
		
		
		final HBSession sess;
		sess = HBUtil.getHBSession();
		String userid = "40288103556cc97701556d629135000f";//��ȡ�û�id
		String sqlA32 = "from A32 t where t.userid = '"+userid+"'";
		List<A32> list = sess.createQuery(sqlA32).list();
		A32 a32 = null;
		if(list.size()>0){
			a32 = list.get(0);
		}else{
			a32 = new A32();
			a32.setBirthday(BigDecimal.valueOf(30));
			a32.setFmage((short)55);
			a32.setMage((short)60);
			a32.setSyday(BigDecimal.valueOf(30));
			a32.setUseful("1");
		}
		//��A32���������ݵ����
		//���յ�����������
		
		final StringBuffer sbbirth = new  StringBuffer();
		sbbirth.append("SELECT count(isdate3(RPAD(b.A0107, 8, '01'))) newA0107 "
				+ "FROM a01 b "
				+ "WHERE b.a0163 = '1' "
				+ "AND (LENGTH(b.A0107) = 6 OR LENGTH(b.A0107) = 8) AND "
				+ "isdate3(RPAD(b.A0107, 8, '01'))<> TO_DATE('18000101', 'yyyyMMdd')  "
				+ "and TO_CHAR(isdate3(RPAD(b.A0107, 8, '01')), 'mmdd')<=TO_CHAR(sysdate, 'mmdd')  "
				+ "and TO_CHAR(isdate3(RPAD(b.A0107, 8, '01')), 'mmdd')>=TO_CHAR(sysdate-"+a32.getBirthday()+", 'mmdd')");
		
		//�����ڵ�������
		final StringBuffer sbpro = new StringBuffer();
		sbpro.append("SELECT count(isdate3(RPAD(b.a0288, 8, '01'))) FROM a01 b WHERE b.a0163 = '1' AND b.A0221 IN "
				+ "('1A98', '1B98', '1C98', '27', '911', 'C98') AND (LENGTH(b.a0288) = 6 OR LENGTH(b.a0288) = 8) "
				+ "and isdate3(RPAD(b.A0288, 8, '01'))<> TO_DATE('18000101', 'yyyyMMdd') "
				+ "and (add_months(isdate3(RPAD(b.a0288, 8, '01')),12)<=SYSDATE "
				+ "or (add_months(isdate3(RPAD(b.a0288, 8, '01')),12)<=SYSDATE+ "+a32.getSyday()
				+ " and add_months(isdate3(RPAD(b.a0288, 8, '01')),12)>=SYSDATE))");
		
		//�ѳ�������ʱ����Ա
		final StringBuffer sbretire = new StringBuffer();
		sbretire.append("SELECT count(isdate3(RPAD(b.A0107, 8, '01'))) FROM a01 b "
				+ "WHERE b.a0163 = '1' AND (LENGTH(b.A0107) = 6 OR LENGTH(b.A0107) = 8) "
				+ "and isdate3(RPAD(b.A0107, 8, '01'))<> TO_DATE('18000101', 'yyyyMMdd') "
				+ "and to_char(add_months(isdate3(RPAD(b.A0107, 8, '01')),DECODE(b.a0104, '1', "+a32.getMage()+" * 12, "+a32.getFmage()+" * 12)),'yyyymm')<=to_char(sysdate,'yyyymm')");
		
		try {
			
			log.info("����������������ͳ������");
			HBUtil.executeUpdate("truncate table swtx_a01 ");
			HBUtil.executeUpdate("insert into swtx_a01  SELECT ("+sbbirth + ") birthday, (" + sbpro + ") probation, (" + sbretire +") retire from dual");
			log.info("������������ͳ���������");
		} catch (AppException e) {
			try {
				HBUtil.executeUpdate("create  table swtx_a01 as SELECT ("+sbbirth + ") birthday, (" + sbpro + ") probation, (" + sbretire +") retire from dual");
				log.info("�����������Ѽ��������");
			} catch (AppException e1) {
				log.info("��������ͳ��ִ��ʧ�ܣ�"+e.getMessage());
				e1.printStackTrace();
			}
			//e.printStackTrace();
		}
		
		
		
		
		
		
		
	}
	
	private  void bzfxinfo(final long firstinterval, final int interval) {  
		//add 20180328 zepeng �ĳ�1��ִ��һ�ι���̨ͳ��
		final int intervalStep = 2*3600*24;
		try{
	        Runnable runnable = new Runnable() {  
	            public void run() { 
	            	updateBZFX();
	            	log.info("������ɣ���һ��ִ����"+intervalStep/3600/24+"���ִ��");
	            }  
	        };  
	        ScheduledExecutorService service = Executors.newSingleThreadScheduledExecutor();  
	        // �ڶ�������Ϊ�״�ִ�е���ʱʱ�䣬����������Ϊ��ʱִ�еļ��ʱ��  
	        service.scheduleAtFixedRate(runnable, firstinterval, intervalStep, TimeUnit.SECONDS);  
		}catch(Exception e){
			log.info("����ʧ�ܣ�"+e.getMessage());
        }
        
    }
	
	
	private void updateBZFX() {
		HBSession sess = HBUtil.getHBSession();
		try {
			Statement stmt = sess.connection().createStatement();
			String sql="";
			List<String> b0111 = HBUtil.getHBSession().createSQLQuery("select b0111 from BZFX t ;").list();
			
			List<String> b0111s = HBUtil.getHBSession().createSQLQuery("select b0111 from GQBZFX t ;").list();
			for(int i=0;i<b0111.size();i++) {
				StringBuffer bzfx=new StringBuffer();
				String checkedgroupid=b0111.get(i);
//				if("001.001.002.01O".equals(checkedgroupid) || "001.001.002.01Q".equals(checkedgroupid) || "001.001.002.02O".equals(checkedgroupid)) {
//					continue;
//				}
				//���ӷ��� ʵ�����
				bzfx.append("ʵ�������");
				//��ȱ��ְ
				@SuppressWarnings("unchecked")
				List<String> kqzz= HBUtil.getHBSession().createSQLQuery("select b0234 from b01  where B0111='"+checkedgroupid+"'").list();
			
				//��ȱ��ְ
				@SuppressWarnings("unchecked")
				List<String> kqfz= HBUtil.getHBSession().createSQLQuery("select b0235 from b01  where B0111='"+checkedgroupid+"'").list();
				
				//����ְλ
				@SuppressWarnings("unchecked")
				List<String> cpzw= HBUtil.getHBSession().createSQLQuery("select b0180 from b01  where B0111='"+checkedgroupid+"'").list();
				
				if(kqzz.get(0)==null && kqfz.get(0)==null && cpzw.get(0)==null) {
					bzfx.append("������������");
				}else {
					if(kqzz.get(0)!=null) {
						bzfx.append("���ӿ�ȱ��ְ"+String.valueOf(kqzz.get(0))+"����");
					}
					if(kqfz.get(0)!=null) {
						bzfx.append("���ӿ�ȱ��ְ"+String.valueOf(kqfz.get(0))+"����");
					}
					if(cpzw.get(0)!=null) {
						bzfx.append("���ӳ��������"+String.valueOf(cpzw.get(0))+"��");
					}
				}
				bzfx.deleteCharAt(bzfx.length()-1);
				
				
				bzfx.append("\n����ṹ");
				
				
				//ƽ������
				@SuppressWarnings("unchecked")
				List<String> avgAge = HBUtil.getHBSession().createSQLQuery("SELECT substr(avg(substr(to_char(sysdate,'yyyymm')-substr(a01.a0107,1,6),1,2)),1,4) FROM a02, a01,b01 t\r\n" + 
						"       WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1'\r\n" + 
						"       and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and a01.a0163 ='1'  and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
						"       and a02.a0201b=t.b0111 and a02.a0201b='"+checkedgroupid+"'").list();
				
				//��С����
				@SuppressWarnings("unchecked")
				List<String> minAge = HBUtil.getHBSession().createSQLQuery("SELECT substr(min(substr(to_char(sysdate,'yyyymm')-substr(a01.a0107,1,6),1,2)),1,4) FROM a02, a01,b01 t\r\n" + 
						"       WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1'\r\n" + 
						"       and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31')  and a01.a0163 ='1'  and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
						"       and a02.a0201b=t.b0111 and a02.a0201b='"+checkedgroupid+"'").list();
				
				
				//�������
				@SuppressWarnings("unchecked")
				List<String> maxAge = HBUtil.getHBSession().createSQLQuery("SELECT substr(max(substr(to_char(sysdate,'yyyymm')-substr(a01.a0107,1,6),1,2)),1,4) FROM a02, a01,b01 t\r\n" + 
						"       WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1'\r\n" + 
						"       and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31')  and a01.a0163 ='1'  and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
						"       and a02.a0201b=t.b0111 and a02.a0201b='"+checkedgroupid+"'").list();
				double avg=0;
				if(avgAge.get(0)!=null && !"".equals(avgAge.get(0))) {
					avg=Double.parseDouble(String.valueOf(avgAge.get(0)));
				}
				
				double min=0;
				if(minAge.get(0)!=null && !"".equals(minAge.get(0))) {
					min=Double.parseDouble(String.valueOf(minAge.get(0)));
				}
				double max=0;
				if(maxAge.get(0)!=null && !"".equals(maxAge.get(0))) {
					max=Double.parseDouble(String.valueOf(maxAge.get(0)));
				}
				if(avg>51.4) {
					if(min>=50) {
						bzfx.append("(���)��������ֱƽ��������û��50�����¸ɲ���");
						sql="update bzfx set NLJGYJ='3' where b0111='"+checkedgroupid+"'";
						stmt.executeUpdate(sql);
					}else{
						bzfx.append("(�Ƶ�)��������ֱƽ�����䣬����50�����¸ɲ���");
						sql="update bzfx set NLJGYJ='2' where b0111='"+checkedgroupid+"'";
						stmt.executeUpdate(sql);
					}
				}else {
					if(min>=50) {
						bzfx.append("(�Ƶ�)��С����ֱƽ�����䣬��û��50�����¸ɲ���");
						sql="update bzfx set NLJGYJ='2' where b0111='"+checkedgroupid+"'";
						stmt.executeUpdate(sql);
					}else {
						if((max-min)<=5){
							bzfx.append("(�Ƶ�)��С����ֱƽ�����䣬��50�����¸ɲ�����Ա����������5�����ڡ�");
							sql="update bzfx set NLJGYJ='2' where b0111='"+checkedgroupid+"'";
							stmt.executeUpdate(sql);
						}else {
							bzfx.append("(�̵�)��С����ֱƽ�����䣬��50�����¸ɲ��ҳ�Ա����������5�����ϡ�");
							sql="update bzfx set NLJGYJ='1' where b0111='"+checkedgroupid+"'";
							stmt.executeUpdate(sql);
						}
					}
				}
				
				bzfx.append("\n��Դ�ṹ");
				//�����Ų�������
				@SuppressWarnings("unchecked")
				List<String> bbmRs = HBUtil.getHBSession().createSQLQuery("SELECT count(1) FROM a02, a01,b01 t, EXTRA_TAGS e\r\n" + 
						" WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' and e.a0000=a01.a0000\r\n" + 
						" and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and a01.a0163 ='1'  and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
						" and a02.a0201b=t.b0111 and a02.a0201b='"+checkedgroupid+"'and e.a0194c='1'").list();
				
				double bbm=Double.parseDouble(String.valueOf(bbmRs.get(0)));
				
				
				//�����Ų�������
//				@SuppressWarnings("unchecked")
//				List<String> bbmRs = HBUtil.getHBSession().createSQLQuery("select BDWRS from SZBDW where b0111='"+checkedgroupid+"'").list();
//				double bbm=0;
//				if(bbmRs.size()>0) {
//					if(bbmRs.get(0)!=null && !"".equals(bbmRs.get(0))) {
//						bbm=Double.parseDouble(String.valueOf(bbmRs.get(0)));
//					}
//				}
				
				//������
				@SuppressWarnings("unchecked")
				List<String> totalRs = HBUtil.getHBSession().createSQLQuery("SELECT count(1) FROM a02, a01,b01 t \r\n" + 
						" WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' \r\n" + 
						" and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and a01.a0163 ='1'  and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
						" and a02.a0201b=t.b0111 and a02.a0201b='"+checkedgroupid+"'").list();
				
				double total=Double.parseDouble(String.valueOf(totalRs.get(0)));
				
				double lyzb=0;
				if(total!=0) {
					lyzb=bbm/total;
				}		
				if(lyzb>=(2/3.0)) {
					bzfx.append("(���)�����ӳ�Ա������֮�������ڲ�������");
					sql="update bzfx set LYJGYJ='3' where b0111='"+checkedgroupid+"'";
					stmt.executeUpdate(sql);
				}else if(lyzb>=1/2.0) {
					bzfx.append("(�Ƶ�)�����ӳ�Ա�ж���֮һ�����ڲ�������");
					sql="update bzfx set LYJGYJ='2' where b0111='"+checkedgroupid+"'";
					stmt.executeUpdate(sql);
				}else {
					bzfx.append("(�̵�)�����ӳ�Ա�в������֮һ�ڲ�������");
					sql="update bzfx set LYJGYJ='1' where b0111='"+checkedgroupid+"'";
					stmt.executeUpdate(sql);
				}
				
				if(bbmRs.size()<1) {
					sql="update bzfx set LYJGYJ='' where b0111='"+checkedgroupid+"'";
					stmt.executeUpdate(sql);
				}else{
					if(bbmRs.get(0)==null ||"".equals(bbmRs.get(0))) {
						sql="update bzfx set LYJGYJ='' where b0111='"+checkedgroupid+"'";
						stmt.executeUpdate(sql);
					}
				}
				
				bzfx.append("\nרҵ�ṹ");
				
				@SuppressWarnings("unchecked")
				List<String> zygbrs = HBUtil.getHBSession().createSQLQuery("SELECT count(1) FROM a02, a01,b01 t,attr_lrzw\r\n" + 
						"     WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' and attr_lrzw.a0000=a01.a0000\r\n" + 
						"         and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31')  and a01.a0163 ='1'  and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
						"            and a02.a0201b=t.b0111 and  (attr_lrzw.attr101='1' or attr_lrzw.attr102='1' or attr_lrzw.attr103='1'\r\n" + 
						"            or attr_lrzw.attr104='1'or attr_lrzw.attr105='1'or  attr_lrzw.attr106='1'\r\n" + 
						"           or  attr_lrzw.attr107='1' or attr_lrzw.attr108='1' or attr_lrzw.attr109='1' or attr_lrzw.attr110='1'\r\n" + 
						"           or attr_lrzw.attr111='1' or attr_lrzw.attr112='1'\r\n" + 
						"            ) and a02.a0201b='"+checkedgroupid+"'").list();
				 double zygb=Double.parseDouble(String.valueOf(zygbrs.get(0)));
				 double zyzb=0;
				 if(total!=0) {
					 zyzb=zygb/total;
				 }
				 
				 @SuppressWarnings("unchecked")
				List<String> zyxbm = HBUtil.getHBSession().createSQLQuery("select zyxbm from bzfx where b0111='"+checkedgroupid+"'").list();
				if("0".equals(zyxbm.get(0))){
					 bzfx.append("����רҵ�Բ���");
					 sql="update bzfx set ZYJGYJ='' where b0111='"+checkedgroupid+"'";
					 stmt.executeUpdate(sql);
				}else {
					if(zyzb<(1/3.0)) {
						bzfx.append("(���)��רҵ����רҵ���ɲ���������֮һ��");
						sql="update bzfx set ZYJGYJ='3' where b0111='"+checkedgroupid+"'";
						stmt.executeUpdate(sql);
					}else if(zyzb<(1/2.0)) {
						bzfx.append("(�Ƶ�)��רҵ����רҵ���ɲ��������֮һ��");
						sql="update bzfx set ZYJGYJ='2' where b0111='"+checkedgroupid+"'";
						stmt.executeUpdate(sql);
					}else{
						bzfx.append("(�̵�)��רҵ����רҵ���ɲ���������֮һ��");
						sql="update bzfx set ZYJGYJ='1' where b0111='"+checkedgroupid+"'";
						stmt.executeUpdate(sql);
					}
				}
				 
				
				 
				bzfx.append("\n");
				 //Ů�Ըɲ�����
				@SuppressWarnings("unchecked")
				List<String> NumFemale = HBUtil.getHBSession().createSQLQuery("SELECT count(1) FROM a02, a01,b01 t\r\n" + 
						"       WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' \r\n" + 
						"       and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and a01.a0163 ='1'  and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
						"       and a02.a0201b=t.b0111 and  a01.a0104= '2' and a02.a0201b='"+checkedgroupid+"'").list();
				
				
				//����ɲ�����
				@SuppressWarnings("unchecked")
				List<String> NumDW = HBUtil.getHBSession().createSQLQuery("SELECT count(1) FROM a02, a01,b01 t\r\n" + 
						"       WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' \r\n" + 
						"       and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and a01.a0163 ='1'  and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
						"       and a02.a0201b=t.b0111 and  a01.a0141 not in ('01','02','03') and a02.a0201b='"+checkedgroupid+"'").list();
				
				bzfx.append("Ů�Ըɲ�"+String.valueOf(NumFemale.get(0))+"��������ɲ�"+String.valueOf(NumDW.get(0))+"����");
				sql="update bzfx set BZFX='"+bzfx.toString()+"' where b0111='"+checkedgroupid+"'";
				stmt.executeUpdate(sql);
			}
			
			
			
			//�����У���ӷ���
			for(int i=0;i<b0111s.size();i++) {
				StringBuffer bzfx=new StringBuffer();
				String checkedgroupid=b0111s.get(i);
//				if("001.001.002.01O".equals(checkedgroupid) || "001.001.002.01Q".equals(checkedgroupid) || "001.001.002.02O".equals(checkedgroupid)) {
//					continue;
//				}
				//���ӷ��� ʵ�����
				bzfx.append("ʵ�������");
				//��ȱ��ְ
				@SuppressWarnings("unchecked")
				List<String> kqzz= HBUtil.getHBSession().createSQLQuery("select b0234 from b01  where B0111='"+checkedgroupid+"'").list();
			
				//��ȱ��ְ
				@SuppressWarnings("unchecked")
				List<String> kqfz= HBUtil.getHBSession().createSQLQuery("select b0235 from b01  where B0111='"+checkedgroupid+"'").list();
				
				//����ְλ
				@SuppressWarnings("unchecked")
				List<String> cpzw= HBUtil.getHBSession().createSQLQuery("select b0180 from b01  where B0111='"+checkedgroupid+"'").list();
				
				if(kqzz.get(0)==null && kqfz.get(0)==null && cpzw.get(0)==null) {
					bzfx.append("������������");
				}else {
					if(kqzz.get(0)!=null) {
						bzfx.append("���ӿ�ȱ��ְ"+String.valueOf(kqzz.get(0))+"����");
					}
					if(kqfz.get(0)!=null) {
						bzfx.append("���ӿ�ȱ��ְ"+String.valueOf(kqfz.get(0))+"����");
					}
					if(cpzw.get(0)!=null) {
						bzfx.append("���ӳ��������"+String.valueOf(cpzw.get(0))+"��");
					}
				}
				bzfx.deleteCharAt(bzfx.length()-1);
				
				
				bzfx.append("\n����ṹ");
				
				
				//ƽ������
				@SuppressWarnings("unchecked")
				List<String> avgAge = HBUtil.getHBSession().createSQLQuery("SELECT substr(avg(substr(to_char(sysdate,'yyyymm')-substr(a01.a0107,1,6),1,2)),1,4) FROM a02, a01,b01 t\r\n" + 
						"       WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1'\r\n" + 
						"       and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and a01.a0163 ='1'  and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
						"       and a02.a0201b=t.b0111 and a02.a0201b='"+checkedgroupid+"'").list();
				
				//��С����
				@SuppressWarnings("unchecked")
				List<String> minAge = HBUtil.getHBSession().createSQLQuery("SELECT substr(min(substr(to_char(sysdate,'yyyymm')-substr(a01.a0107,1,6),1,2)),1,4) FROM a02, a01,b01 t\r\n" + 
						"       WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1'\r\n" + 
						"       and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31')  and a01.a0163 ='1'  and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
						"       and a02.a0201b=t.b0111 and a02.a0201b='"+checkedgroupid+"'").list();
				
				
				//�������
				@SuppressWarnings("unchecked")
				List<String> maxAge = HBUtil.getHBSession().createSQLQuery("SELECT substr(max(substr(to_char(sysdate,'yyyymm')-substr(a01.a0107,1,6),1,2)),1,4) FROM a02, a01,b01 t\r\n" + 
						"       WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1'\r\n" + 
						"       and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31')  and a01.a0163 ='1'  and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
						"       and a02.a0201b=t.b0111 and a02.a0201b='"+checkedgroupid+"'").list();
				double avg=0;
				if(avgAge.get(0)!=null && !"".equals(avgAge.get(0))) {
					avg=Double.parseDouble(String.valueOf(avgAge.get(0)));
				}
				
				double min=0;
				if(minAge.get(0)!=null && !"".equals(minAge.get(0))) {
					min=Double.parseDouble(String.valueOf(minAge.get(0)));
				}
				double max=0;
				if(maxAge.get(0)!=null && !"".equals(maxAge.get(0))) {
					max=Double.parseDouble(String.valueOf(maxAge.get(0)));
				}
				if(avg>51.4) {
					if(min>=50) {
						bzfx.append("(���)��������ֱƽ��������û��50�����¸ɲ���");
						sql="update gqbzfx set NLJGYJ='3' where b0111='"+checkedgroupid+"'";
						stmt.executeUpdate(sql);
					}else{
						bzfx.append("(�Ƶ�)��������ֱƽ�����䣬����50�����¸ɲ���");
						sql="update gqbzfx set NLJGYJ='2' where b0111='"+checkedgroupid+"'";
						stmt.executeUpdate(sql);
					}
				}else {
					if(min>=50) {
						bzfx.append("(�Ƶ�)��С����ֱƽ�����䣬��û��50�����¸ɲ���");
						sql="update gqbzfx set NLJGYJ='2' where b0111='"+checkedgroupid+"'";
						stmt.executeUpdate(sql);
					}else {
						if((max-min)<=5){
							bzfx.append("(�Ƶ�)��С����ֱƽ�����䣬��50�����¸ɲ�����Ա����������5�����ڡ�");
							sql="update gqbzfx set NLJGYJ='2' where b0111='"+checkedgroupid+"'";
							stmt.executeUpdate(sql);
						}else {
							bzfx.append("(�̵�)��С����ֱƽ�����䣬��50�����¸ɲ��ҳ�Ա����������5�����ϡ�");
							sql="update gqbzfx set NLJGYJ='1' where b0111='"+checkedgroupid+"'";
							stmt.executeUpdate(sql);
						}
					}
				}
				
				bzfx.append("\n��Դ�ṹ");
				//�����Ų�������
				@SuppressWarnings("unchecked")
				List<String> bbmRs = HBUtil.getHBSession().createSQLQuery("SELECT count(1) FROM a02, a01,b01 t, EXTRA_TAGS e\r\n" + 
						" WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' and e.a0000=a01.a0000\r\n" + 
						" and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and a01.a0163 ='1'  and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
						" and a02.a0201b=t.b0111 and a02.a0201b='"+checkedgroupid+"'and e.a0194c='1'").list();
				
				double bbm=Double.parseDouble(String.valueOf(bbmRs.get(0)));
				
				
				//�����Ų�������
//				@SuppressWarnings("unchecked")
//				List<String> bbmRs = HBUtil.getHBSession().createSQLQuery("select BDWRS from SZBDW where b0111='"+checkedgroupid+"'").list();
//				double bbm=0;
//				if(bbmRs.size()>0) {
//					if(bbmRs.get(0)!=null && !"".equals(bbmRs.get(0))) {
//						bbm=Double.parseDouble(String.valueOf(bbmRs.get(0)));
//					}
//				}
				
				//������
				@SuppressWarnings("unchecked")
				List<String> totalRs = HBUtil.getHBSession().createSQLQuery("SELECT count(1) FROM a02, a01,b01 t \r\n" + 
						" WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' \r\n" + 
						" and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and a01.a0163 ='1'  and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
						" and a02.a0201b=t.b0111 and a02.a0201b='"+checkedgroupid+"'").list();
				
				double total=Double.parseDouble(String.valueOf(totalRs.get(0)));
				
				double lyzb=0;
				if(total!=0) {
					lyzb=bbm/total;
				}		
				if(lyzb>=(2/3.0)) {
					bzfx.append("(���)�����ӳ�Ա������֮�������ڲ�������");
					sql="update gqbzfx set LYJGYJ='3' where b0111='"+checkedgroupid+"'";
					stmt.executeUpdate(sql);
				}else if(lyzb>=1/2.0) {
					bzfx.append("(�Ƶ�)�����ӳ�Ա�ж���֮һ�����ڲ�������");
					sql="update gqbzfx set LYJGYJ='2' where b0111='"+checkedgroupid+"'";
					stmt.executeUpdate(sql);
				}else {
					bzfx.append("(�̵�)�����ӳ�Ա�в������֮һ�ڲ�������");
					sql="update gqbzfx set LYJGYJ='1' where b0111='"+checkedgroupid+"'";
					stmt.executeUpdate(sql);
				}
				
				if(bbmRs.size()<1) {
					sql="update gqbzfx set LYJGYJ='' where b0111='"+checkedgroupid+"'";
					stmt.executeUpdate(sql);
				}else{
					if(bbmRs.get(0)==null ||"".equals(bbmRs.get(0))) {
						sql="update gqbzfx set LYJGYJ='' where b0111='"+checkedgroupid+"'";
						stmt.executeUpdate(sql);
					}
				}
				
				bzfx.append("\nרҵ�ṹ");
				
				@SuppressWarnings("unchecked")
				List<String> zygbrs = HBUtil.getHBSession().createSQLQuery("SELECT count(1) FROM a02, a01,b01 t,attr_lrzw\r\n" + 
						"     WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' and attr_lrzw.a0000=a01.a0000\r\n" + 
						"         and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31')  and a01.a0163 ='1'  and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
						"            and a02.a0201b=t.b0111 and  (attr_lrzw.attr101='1' or attr_lrzw.attr102='1' or attr_lrzw.attr103='1'\r\n" + 
						"            or attr_lrzw.attr104='1'or attr_lrzw.attr105='1'or  attr_lrzw.attr106='1'\r\n" + 
						"           or  attr_lrzw.attr107='1' or attr_lrzw.attr108='1' or attr_lrzw.attr109='1' or attr_lrzw.attr110='1'\r\n" + 
						"           or attr_lrzw.attr111='1' or attr_lrzw.attr112='1'\r\n" + 
						"            ) and a02.a0201b='"+checkedgroupid+"'").list();
				 double zygb=Double.parseDouble(String.valueOf(zygbrs.get(0)));
				 double zyzb=0;
				 if(total!=0) {
					 zyzb=zygb/total;
				 }
				 
				 @SuppressWarnings("unchecked")
				List<String> zyxbm = HBUtil.getHBSession().createSQLQuery("select zyxbm from bzfx where b0111='"+checkedgroupid+"'").list();
				if("0".equals(zyxbm.get(0))){
					 bzfx.append("����רҵ�Բ���");
					 sql="update gqbzfx set ZYJGYJ='' where b0111='"+checkedgroupid+"'";
					 stmt.executeUpdate(sql);
				}else {
					if(zyzb<(1/3.0)) {
						bzfx.append("(���)��רҵ����רҵ���ɲ���������֮һ��");
						sql="update gqbzfx set ZYJGYJ='3' where b0111='"+checkedgroupid+"'";
						stmt.executeUpdate(sql);
					}else if(zyzb<(1/2.0)) {
						bzfx.append("(�Ƶ�)��רҵ����רҵ���ɲ��������֮һ��");
						sql="update gqbzfx set ZYJGYJ='2' where b0111='"+checkedgroupid+"'";
						stmt.executeUpdate(sql);
					}else{
						bzfx.append("(�̵�)��רҵ����רҵ���ɲ���������֮һ��");
						sql="update gqbzfx set ZYJGYJ='1' where b0111='"+checkedgroupid+"'";
						stmt.executeUpdate(sql);
					}
				}
				 
				
				 
				bzfx.append("\n");
				 //Ů�Ըɲ�����
				@SuppressWarnings("unchecked")
				List<String> NumFemale = HBUtil.getHBSession().createSQLQuery("SELECT count(1) FROM a02, a01,b01 t\r\n" + 
						"       WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' \r\n" + 
						"       and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and a01.a0163 ='1'  and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
						"       and a02.a0201b=t.b0111 and  a01.a0104= '2' and a02.a0201b='"+checkedgroupid+"'").list();
				
				
				//����ɲ�����
				@SuppressWarnings("unchecked")
				List<String> NumDW = HBUtil.getHBSession().createSQLQuery("SELECT count(1) FROM a02, a01,b01 t\r\n" + 
						"       WHERE a01.A0000 = a02.a0000 AND a02.a0281 = 'true' AND a02.a0255 = '1' \r\n" + 
						"       and (a02.a0201e='1' or a02.a0201e='3' or a02.a0201e='31') and a01.a0163 ='1'  and  (decode(a02.a0248,null,'0','','0',a02.a0248) = '0') \r\n" + 
						"       and a02.a0201b=t.b0111 and  a01.a0141 not in ('01','02','03') and a02.a0201b='"+checkedgroupid+"'").list();
				
				bzfx.append("Ů�Ըɲ�"+String.valueOf(NumFemale.get(0))+"��������ɲ�"+String.valueOf(NumDW.get(0))+"����");
				sql="update gqbzfx set BZFX='"+bzfx.toString()+"' where b0111='"+checkedgroupid+"'";
				stmt.executeUpdate(sql);
			}
		}catch(Exception e){
			log.info("���ӷ�������ʧ��");
        }
		
	}
}
