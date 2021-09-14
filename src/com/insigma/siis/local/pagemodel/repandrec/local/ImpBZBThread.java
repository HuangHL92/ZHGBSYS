package com.insigma.siis.local.pagemodel.repandrec.local;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.siis.local.business.datavaerify.UploadHzbFileBS;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.Dataexchangeconf;
import com.insigma.siis.local.business.entity.Imprecord;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.repandrec.local.KingbsGainBS;
import com.insigma.siis.local.business.repandrec.local.KingbsconfigBS;
import com.insigma.siis.local.business.sysorg.org.CreateSysOrgBS;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.UploadHelpFileServlet;

public class ImpBZBThread implements Runnable {
	
	private String bzbDeptid;
	private String searchDeptid;
	private String imprecordid;
	private CurrentUser user;
	private UserVO userVo;
	private String fxz;
	private String adress;//��Ƭ·��
    public ImpBZBThread(String bzbDeptid, String searchDeptid, String imprecordid, CurrentUser user,UserVO userVo, String fxz, String adress) {
        this.bzbDeptid = bzbDeptid;
        this.searchDeptid = searchDeptid;
        this.imprecordid = imprecordid;
        this.user = user;
        this.userVo = userVo;
        this.fxz = fxz;
        this.adress = adress;
    }

	@Override
	public void run() {
		CommonQueryBS.systemOut("�������ʼ��-----"+ DateUtil.getTime());
		String bzbDeptid = this.bzbDeptid;
		String searchDeptid =this.searchDeptid;
		HBSession sess = HBUtil.getHBSession();
		String filename = "";
		String imprecordid =this.imprecordid;
		String process_run = "1";
		String tableExt = "";
		UploadHzbFileBS uploadbs = new UploadHzbFileBS();
		try {
			//--���� zzb3������־�ļ�
			String logfilename = AppConfig.HZB_PATH + "/temp/upload/zzb3" +DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss") +".txt";
			File logfile = new File(logfilename);
			if(!logfile.exists()){
				logfile.createNewFile();
			}
			appendFileContent(logfilename, "��ʼ����:"+ DateUtil.getTime()+"\n");
			List<Dataexchangeconf> list = sess.createQuery("from Dataexchangeconf").list();
			//Photos ·��
			String photo_file = "D:/Photos/";
			if (list !=null && list.size()>0) {
				photo_file = list.get(0).getBzbpath() + "/Client/Photos/";
			} else {
				KingbsconfigBS.saveImpDetail(process_run ,"4","ʧ��:����ǰ����������Ӧ��Ϣ",imprecordid);
				return ;
			}
			
			//NO.004 ��ȡ��ǰִ�е���Ĳ�����Ա����������Ϣ gr��������Ϣ��
			CurrentUser user = this.user;   //��ȡ��ǰִ�е���Ĳ�����Ա��Ϣ
			List<B01> grps = null;
			if(user!=null && user.getId()!=null && !user.getId().equals("")){
				grps = HBUtil.getHBSession().createQuery(
						"from B01 t where t.b0111 in(select b0111 from UserDept where userid='"
								+ user.getId() + "')").list();
			}
			
			B01 gr = null;
			if (grps != null && grps.size() > 0) {
				gr = grps.get(0);     //��ȡ ��ǰִ�е���Ĳ�����Ա����������Ϣ
				grps.removeAll(grps); //�ͷ� ��ǰִ�е���Ĳ�����Ա����������Ϣ�б�
				grps.clear();
				grps = null;
				CommonQueryBS.systemOut("NO.004 ��ȡ��ǰִ�е���Ĳ�����Ա����������Ϣ gr��������Ϣ������Ψһ���룺"+gr.getB0111()+"��������:"+gr.getB0101()+" ���ʱ��"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss"));
			}
			//==========================================================================================================
			//NO.005 ���ε�����̼�¼�뵼���ѯ��¼��imp_record������ȡ imprecordid�������ѯ��¼��Ψһ������
//			sess.createSQLQuery("insert into IMP_RECORD(IMP_RECORD_ID) values ('"+ imprecordid +"')").executeUpdate();
			Imprecord imprecord = (Imprecord) sess.get(Imprecord.class, imprecordid);           //�½� �����ѯ��¼�� ��¼
			imprecord.setImptime(DateUtil.getTimestamp());     //���� ���뿪ʼʱ�� 
			imprecord.setImpuserid(user.getId());            //���� ���������Ա���� 
			if (gr != null) {   
				imprecord.setImpgroupid(gr.getB0111());      //���� �������Ա������������
				imprecord.setImpgroupname(gr.getB0101());    //���� �������Ա������������
			}
			imprecord.setIsvirety("0");                      //���� ����У���־ ��ʼ��Ϊ0��ʾ û��У�� 
			imprecord.setFilename(filename);                 //���� ���ε�����ļ����ļ�����
			imprecord.setFiletype("ZZB3");                   //���� ���ε�����ļ����� 
			imprecord.setImptype("3");                       //���� �������� 3 ��ʾ ZZB3
			imprecord.setImpstutas("1");                     //���� ����״̬Ϊ1 ������״̬ 1��δ���롢2���ѵ��롢3����أ�
			imprecord.setWrongnumber("0");                   //���� У�����ļ�¼����(δʹ��)
			imprecord.setTotalnumber("");                   //���� ������ܼ�¼����
			imprecord.setProcessstatus("1");
			sess.update(imprecord);                            //���浼���ѯ��¼�� 
			imprecordid = imprecord.getImprecordid();
			//============================================================================================================
			process_run = "2";
			imprecord.setPsncount(Long.parseLong(KingbsGainBS.getSizeByTable("A01", bzbDeptid)+""));
			imprecord.setOrgcount(Long.parseLong(KingbsGainBS.getAllB01Size()+""));
		
			sess.update(imprecord);
			KingbsconfigBS.saveImpDetail("1","2","���",imprecordid);
			KingbsconfigBS.saveImpDetail("2","1","������",imprecordid);
			//CommonQueryBS.systemOut("zzb3��ѹ��������-----"+ DateUtil.getTime());
			
			//==========================================================================================================
			//NO.003 ��ȡ��Ӧ���к͵�������ڵ������Ӧ�Ļ�����Ϣ����Ҫ��impdeptid����Ӧ�� ����Ψһ���룩deptid����Ӧ�� �ϼ�������Ψһ���룩
			//2016��06��15��   ���� �����룬���ע�� ��
			//��ȡ���� �˴������ݿ��л�ȡ��������Ϣ(b01) 
			B01 b01 = KingbsGainBS.getB01byid(bzbDeptid);
			int tabsize = KingbsGainBS.getTableSize();
			if(b01 == null || (tabsize != 16 && tabsize != 21)){
				KingbsGainBS.dealRollback(imprecordid, tableExt);;
				KingbsconfigBS.saveImpDetail(process_run ,"4","ʧ��:������ݸ�ʽ��ƥ�䣬����ϵ����Ա��",imprecordid);//������ʾ��Ϣ�� 
				return ;             //��������
			}
			//��ȡ���� ��Ӧ�����ݿ��л�ȡ��������Ϣ(b01) 
			B01 detp = null;
			String deptid = "";// ���ڵ��ϼ�����id
			String impdeptid = "";//���ڵ����id
			List<B01> detps = HBUtil.getHBSession().createQuery(
					"from B01 t where t.b0101='" + b01.getB0101()  //�������
							+ "' and t.b0114='" + b01.getB0114()   //��֯��������
							+ "'").list();
			if (detps != null && detps.size() > 0) {
				detp = detps.get(0);                   //��ȡ��Ӧ���к��˴������ݵĸ�����ͬ��֯�������롢ͬ���ƵĻ�����Ϣ
				impdeptid = detps.get(0).getB0111();   //��Ӧ�� ����Ψһ����
				deptid = detps.get(0).getB0121();      //��Ӧ�� �ϼ�������Ψһ���� 
				detps.removeAll(detps);                //�ͷ� ��Ӧ�û�����Ϣ�б�
				detps.clear();                         
				detps = null;
			} else {
				detp = new B01();
				detp.setB0101(b01.getB0101());
				detp.setB0114(b01.getB0114());
				detp.setB0194(b01.getB0194());
				detp.setB0111(CreateSysOrgBS.selectB0111BySubId(searchDeptid));
				detp.setB0121(searchDeptid);
				detp.setSortid(0L);
				sess.save(detp);
				sess.flush();
				impdeptid = detp.getB0111();   //��Ӧ�� ����Ψһ����
				deptid = detp.getB0121();      //��Ӧ�� �ϼ�������Ψһ���� 
			}
			CommonQueryBS.systemOut("NO.003 ��⵽��Ӧ���к͵�������ڵ������Ӧ�Ļ�����Ϣimpdeptid����Ӧ�� ����Ψһ���룩��"+impdeptid+"deptid����Ӧ�� �ϼ�������Ψһ���룩:"+deptid+" ���ʱ��"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss"));
			//==========================================================================================================
			//NO.005 ���ε�����̼�¼�뵼���ѯ��¼��imp_record������ȡ imprecordid�������ѯ��¼��Ψһ������
			tableExt = getNo() + DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmssS");
			imprecord.setImptemptable(tableExt);
			imprecord.setEmpdeptid(b01.getB0111());          //���� �����ļ��и�������Ψһ����
			imprecord.setEmpdeptname(b01.getB0101());        //���� �����ļ��и�����������
			imprecord.setImpdeptid(impdeptid);               //���� ��Ӧ�� ����Ψһ����
			imprecord.setB0114(b01.getB0114());              //���� �����ļ��и��������ڲ�����
			imprecord.setB0194(b01.getB0194());              //���� �����ļ��и������ķ��˵�λ��ʶ
			imprecord.setTablenumber(Long.valueOf(tabsize));
			sess.update(imprecord);                            //���浼���ѯ��¼�� 
			uploadbs.createTempTable(tableExt);
			CommonQueryBS.systemOut("NO.005 ���ε�����̼�¼�뵼���ѯ��¼��imprecordid�������ѯ��¼��Ψһ��������"+imprecordid+" ���ʱ��"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss"));
			//==========================================================================================================
			//NO.006 ���˴������ݿ�ֱ������ε��뱾Ӧ�����ݿ⣬�������ݸ���personsize�趨
			CommonQueryBS.systemOut("NO.006 �ܵ������ݿ�ʼ��---"+ DateUtil.getTime());
			appendFileContent(logfilename, "==============================================="+"\n");
			//��������������� 
			KingbsconfigBS.saveImpDetail("2","2","���",imprecordid);
			ImpZzb3Control control = new ImpZzb3Control();
			ImpBZBPartThread t1 = new ImpBZBPartThread(logfilename, imprecord, deptid,  bzbDeptid,userVo, control, tabsize, "1", fxz, adress);
			ImpBZBPartThread t2 = new ImpBZBPartThread(logfilename, imprecord, deptid,  bzbDeptid,userVo, control, tabsize, "2", fxz, adress);
			control.setTableExt(tableExt);
			control.setThd1(new Thread(t1, "zzb3_1_"+imprecordid));
			control.setThd2(new Thread(t2, "zzb3_2_"+imprecordid));
			control.setPhoto_file(photo_file);
			control.start();
		} catch (Exception e) {
			try {
				KingbsconfigBS.saveImpDetail(process_run ,"4","ʧ��:"+e.getMessage(),imprecordid);
			} catch (Exception e1) {
				e1.printStackTrace();
			}//������ʾ��Ϣ�� 
			e.printStackTrace();
			KingbsGainBS.dealRollback(imprecordid, tableExt);;
			e.printStackTrace();
		}finally{
			System.gc();
		}

	}
	
	private String getRootPath() {
		String classPath = this.getClass().getClassLoader().getResource("/").getPath(); 
		try {
			classPath = URLDecoder.decode(classPath, "GBK");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String rootPath  = ""; 
		if("\\".equals(File.separator)){   
			rootPath  = classPath.substring(1,classPath.indexOf("WEB-INF/classes")); 
			rootPath = rootPath.replace("/", "\\"); 
		} 
		if("/".equals(File.separator)){   
			rootPath  = classPath.substring(0,classPath.indexOf("WEB-INF/classes")); 
			rootPath = rootPath.replace("\\", "/"); 
		}
		return rootPath;
	}
	public static void appendFileContent(String fileName, String content) {
        try {
            //��һ��д�ļ��������캯���еĵڶ�������true��ʾ��׷����ʽд�ļ�
           FileWriter writer = new FileWriter(fileName, true);
            writer.write(content);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	private static String getNo() {
		String no = "";
		String[] key = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A",
				"B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
				"N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
		for (int i = 0; i < 4; i++) {
			Random random = new Random();
			int r = random.nextInt(35);
			no = no + key[r];
		}
		CommonQueryBS.systemOut(no);
		return no;
	}
}
