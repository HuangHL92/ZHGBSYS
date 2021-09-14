package com.insigma.siis.local.pagemodel.repandrec.plat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.nio.channels.FileChannel;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.fr.third.org.hsqldb.lib.StringUtil;
import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.datavaerify.UploadHzbFileBS;
import com.insigma.siis.local.business.entity.A01;
import com.insigma.siis.local.business.entity.A02;
import com.insigma.siis.local.business.entity.A06;
import com.insigma.siis.local.business.entity.A08;
import com.insigma.siis.local.business.entity.A11;
import com.insigma.siis.local.business.entity.A14;
import com.insigma.siis.local.business.entity.A15;
import com.insigma.siis.local.business.entity.A29;
import com.insigma.siis.local.business.entity.A30;
import com.insigma.siis.local.business.entity.A31;
import com.insigma.siis.local.business.entity.A36;
import com.insigma.siis.local.business.entity.A37;
import com.insigma.siis.local.business.entity.A41;
import com.insigma.siis.local.business.entity.A53;
import com.insigma.siis.local.business.entity.A57;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.CodeValue;
import com.insigma.siis.local.business.entity.Dataexchangeconf;
import com.insigma.siis.local.business.entity.Imprecord;
import com.insigma.siis.local.business.entity.Reportftp;
import com.insigma.siis.local.business.entity.TransConfig;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.helperUtil.PhotosUtil;
import com.insigma.siis.local.business.repandrec.local.KingbsGainBS;
import com.insigma.siis.local.business.repandrec.local.KingbsconfigBS;
import com.insigma.siis.local.business.sysorg.org.CreateSysOrgBS;
import com.insigma.siis.local.business.utils.Dom4jUtil;
import com.insigma.siis.local.business.utils.NewSevenZipUtil;
import com.insigma.siis.local.business.utils.SevenZipUtil;
import com.insigma.siis.local.business.utils.Xml4HZBUtil;
import com.insigma.siis.local.business.utils.Xml4HZBUtil2;
import com.insigma.siis.local.business.utils.Xml4PackageNewUtil;
import com.insigma.siis.local.business.utils.Xml4Zb3Util;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.epsoft.util.FileUtil;
import com.insigma.siis.local.epsoft.util.JXUtil;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.jtrans.SFileDefine;
import com.insigma.siis.local.jtrans.ZwhzFtpClient;
import com.insigma.siis.local.jtrans.ZwhzPackDefine;
import com.insigma.siis.local.pagemodel.dataverify.UploadHelpFileServlet;

public class DataOrgRepNewThread implements Runnable {
	
	private String uuid;
	private String ltry;
	private String gzlbry;
	private String gllbry;
	private String searchDeptid;
	private String linkpsn;
	private String linktel;
	private String remark;
	private String gz_lb;
	private String gl_lb;
	private String ftpid;
	private UserVO userVo;
	private String lsry;
    public DataOrgRepNewThread(String uuid, String ltry,String lsry,String gzlbry
    		,String gllbry,String searchDeptid,String linkpsn,String linktel,
    		String remark,String gz_lb,String gl_lb,UserVO userVo,String ftpid) {
        this.lsry = lsry;
        this.uuid = uuid;
        
        this.ltry = ltry;
        this.gzlbry = gzlbry;
        this.gllbry = gllbry;
        this.searchDeptid = searchDeptid;
        this.linkpsn = linkpsn;
        this.linktel = linktel;
        this.remark = remark;
        this.gz_lb = gz_lb;
        this.gl_lb = gl_lb;
        this.userVo = userVo;
        this.ftpid = ftpid;
        
    }

	@Override
	public void run() {
		//----- �ϱ����ݣ�ͷ�ļ��Ͷ�Ӧ�������ļ���hzb��ʽ�� �����ļ��ְ����� ---------------------------------------------
		//----- �� packcount ÿ 15000 �˽��зְ������ɶ�����ܰ��ļ�  ------------------------------------------------
		HBSession sess = HBUtil.getHBSession();
		Map<String, String> map = new HashMap<String, String>();	
		String localf = AppConfig.LOCAL_FILE_BASEURL;		//fpt ���ر������ݴ�Ÿ�Ŀ¼
		int packcount = 100000;		//����ÿ�����ݰ� ����������
		String process_run = "1";							//���̹������
		try {
			// ---- 01-����sql��ѯ �ϱ����� ---------------------------------------------------------------------
			StringBuilder b = new StringBuilder();
			b.append(" 1=1");
			if (ltry.equals("0")) {						//����ѯ������Ա
				b.append(" and status<>'3'");
			}
			if (lsry.equals("0")) {						//����ѯ��ʷ��Ա
				b.append(" and status<>'2'");
			}
			if(!gz_lb.equals("")){						//��ѡ�������Ϊ��
				if(gzlbry.equals("0")){					//�������ȫѡ������������������ ����ѡ����ѯ
					b.append(" and a0160 in (" + gz_lb.substring(0, gz_lb.length()-1) + ")");
				}
			}
			if(!gl_lb.equals("")){						//��ѡ�������Ϊ��
				if(gllbry.equals("0")){					//�������ȫѡ������������������ ����ѡ����ѯ
					b.append(" and a0165 in (" + gl_lb.substring(0, gl_lb.length()-1) + ")");
				}
			}
			String newTable = "A01" + System.currentTimeMillis();
			String createsql = "";
			if(DBUtil.getDBType().equals(DBType.MYSQL)){
				createsql = "create table " + newTable + " as "
						+ "SELECT @rownum :=@rownum + 1 AS rn, "
						+ "a0000,a0101,a0104,a0104a,a0107,a0111,a0111a,a0114,a0114a,a0117,"+
				              "a0117a,a0134,a0144,a0144b,a0144c,a0148,a0149,a0151,a0153,a0155,a0157,"+
				              "a0158,a0159,a015a,a0160,a0161,a0162,a0163,a0165,a0184,a0191,"+
				              "a0192,a0192a,a0192b,a0193,a0195,a0196,a0198,a0199,a01k01,a01k02,"+
				              "age,cbdw,isvalid,jsnlsj,nl,nmzw,nrzw,qrzxl,qrzxlxx,qrzxw,"+
				              "qrzxwxx,resultsortid,rmly,tbr,tbsj,userlog,xgr,xgsj,zzxl,zzxlxx,"+
				              "zzxw,zzxwxx,a3927,a0102,a0128b,a0128,a0140,a0187a,a0148c,a1701,"+
				              "a14z101,a15z101,a0141d,a0141,a3921,sortid,a0180,a0194,a0192d,"+
				              "STATUS,tbrjg,a0120,a0121,a0122,a0194u,cbdresult,ORGID "
						+ " FROM(SELECT @rownum := 0,"
						+ "t.a0000,t.a0101,t.a0104,t.a0104a,t.a0107,t.a0111,t.a0111a,t.a0114,t.a0114a,t.a0117,"+
							"t.a0117a,t.a0134,t.a0144,t.a0144b,t.a0144c,t.a0148,t.a0149,t.a0151,t.a0153,t.a0155,t.a0157,"+
							"t.a0158,t.a0159,t.a015a,t.a0160,t.a0161,t.a0162,t.a0163,t.a0165,t.a0184,t.a0191,"+
							"t.a0192,t.a0192a,t.a0192b,t.a0193,t.a0195,t.a0196,t.a0198,t.a0199,t.a01k01,t.a01k02,"+
							"t.age,t.cbdw,t.isvalid,t.jsnlsj,t.nl,t.nmzw,t.nrzw,t.qrzxl,t.qrzxlxx,t.qrzxw,"+
							"t.qrzxwxx,t.resultsortid,t.rmly,t.tbr,t.tbsj,t.userlog,t.xgr,t.xgsj,t.zzxl,t.zzxlxx,"+
							"t.zzxw,t.zzxwxx,t.a3927,t.a0102,t.a0128b,t.a0128,t.a0140,t.a0187a,t.a0148c,t.a1701,"+
							"t.a14z101,t.a15z101,t.a0141d,t.a0141,t.a3921,t.sortid,t.a0180,t.a0194,t.a0192d,"+
							"t.STATUS,t.tbrjg,t.a0120,t.a0121,t.a0122,t.a0194u,t.cbdresult,t.ORGID"
						+ " FROM A01 t where " + (b.toString()) + ") as a";
			}  else {
				createsql = "create table " + newTable + " as "
						+ "select rownum rn,t.a0000,t.a0101,t.a0104,t.a0104a,t.a0107,t.a0111,t.a0111a,t.a0114,t.a0114a,t.a0117,"+
							"t.a0117a,t.a0134,t.a0144,t.a0144b,t.a0144c,t.a0148,t.a0149,t.a0151,t.a0153,t.a0155,t.a0157,"+
							"t.a0158,t.a0159,t.a015a,t.a0160,t.a0161,t.a0162,t.a0163,t.a0165,t.a0184,t.a0191,"+
							"t.a0192,t.a0192a,t.a0192b,t.a0193,t.a0195,t.a0196,t.a0198,t.a0199,t.a01k01,t.a01k02,"+
							"t.age,t.cbdw,t.isvalid,t.jsnlsj,t.nl,t.nmzw,t.nrzw,t.qrzxl,t.qrzxlxx,t.qrzxw,"+
							"t.qrzxwxx,t.resultsortid,t.rmly,t.tbr,t.tbsj,t.userlog,t.xgr,t.xgsj,t.zzxl,t.zzxlxx,"+
							"t.zzxw,t.zzxwxx,t.a3927,t.a0102,t.a0128b,t.a0128,t.a0140,t.a0187a,t.a0148c,t.a1701,"+
							"t.a14z101,t.a15z101,t.a0141d,t.a0141,t.a3921,t.sortid,t.a0180,t.a0194,t.a0192d,"+
							"t.STATUS,t.tbrjg,t.a0120,t.a0121,t.a0122,t.a0194u,t.cbdresult,t.ORGID from a01 t where " 
							+ (b.toString()) + "";
			}
			HBUtil.getHBSession().connection().createStatement().execute(createsql);
			HBUtil.getHBSession().createSQLQuery("create index index_" + newTable + " on " + newTable + " (rn)").executeUpdate();
			
			Object obj = HBUtil.getHBSession().createSQLQuery(			//��ѯ��������
					"select count(1) from " + newTable + " ").uniqueResult();
			// ---- 02-����ͷ�ļ���Ϣ -----------------------------------------------------------------------
			int count = 1;												//��������Ĭ��ֵΪ1
			B01 b01 = (B01) sess.get(B01.class, searchDeptid);			//��ѯ������
			String path = getPath();									//�����ļ���ʱ·��
			java.sql.Timestamp now = DateUtil.getTimestamp();			//��ȡ��ǰʱ��
			String time = DateUtil.timeToString(now);
			String time1 = DateUtil.timeToString(now, "yyyyMMddHHmmss");
			ZwhzPackDefine info = new ZwhzPackDefine();					//ͷ�ļ�ʵ���࣬��������ͷ�ļ�
			String sid = UUID.randomUUID().toString().replace("-", "");	//�ļ�����id
			info.setId(sid);
			info.setB0101(b01.getB0101());								//���û�����Ϣ
			info.setB0111(b01.getB0111());
			info.setB0114(b01.getB0114());
			info.setB0194(b01.getB0194());
			info.setDataversion(DateUtil.dateToString(DateUtil.getSysDate(),
					"yyyyMMdd"));
			info.setLinkpsn(linkpsn);									//���û�����Ϣ
			info.setLinktel(linktel);									//���û�����Ϣ
			info.setRemark(remark);										//��ע
			info.setStype("2");											//2 Ϊ�����ϱ����
			info.setStypename("����������");
			info.setTime(time);
			info.setTranstype("up");									//�ϱ�
			info.setErrortype("��");
			info.setErrorinfo("��");
			if (obj != null) {											//�������� ʵ��ֵ
				if (DBUtil.getDBType().equals(DBType.MYSQL)) {			
					count = ((BigInteger) obj).intValue()/ packcount
							+ (((BigInteger) obj).intValue() % packcount != 0 ? 1 : 0);
					info.setPersoncount(Long.valueOf(((BigInteger) obj)
							.intValue()));
				} else if (DBUtil.getDBType().equals(DBType.ORACLE)) {
					count = ((BigDecimal) obj).intValue()/ packcount
							+ (((BigDecimal) obj).intValue() % packcount != 0 ? 1 : 0);
					info.setPersoncount(Long.valueOf(((BigDecimal) obj).intValue()));
				}
			}
			if(count == 0){
				count = 1;
			}
			List<SFileDefine> sfile = new ArrayList<SFileDefine>();				//���������ļ���hzb������
			String packageFile = "Pack_�����������ļ�_" + b01.getB0114() + "_"		//ͷ�ļ�����
					+ b01.getB0101() + "_" + time1 + ".xml";
			KingbsconfigBS.saveImpDetail(process_run,"2","���",uuid);			//��¼�������
			process_run = "2";
			KingbsconfigBS.saveImpDetail(process_run,"1","������",uuid);			//��¼�������
			// ---- 03-���������ļ� ----------------------------------------------------------------------------
			DataOrgRepControl control = new DataOrgRepControl();
			DataOrgPartThread t1 = new DataOrgPartThread(uuid, linkpsn, linktel, remark, ftpid, time, time1, userVo, control, "1");
			DataOrgPartThread t2 = new DataOrgPartThread(uuid, linkpsn, linktel, remark, ftpid, time, time1, userVo, control, "2");
			control.setA01new(newTable);
			control.setInfo(info);
			control.setCount(count);
			control.setPackageFile(packageFile);
			control.setPackcount(packcount);
			control.setPath(path);
			control.setSid(sid);
			control.setB01(b01);
			control.setSfile(sfile);
			control.setThd1(new Thread(t1, "DataOrgRep_1_"+uuid));
			control.setThd2(new Thread(t2, "DataOrgRep_1_"+uuid));
			control.start();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				KingbsconfigBS.saveImpDetail(process_run,"4", e.getMessage(),uuid);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
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
	
	// ɾ���ļ���
	// param folderPath �ļ�����������·��
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath); // ɾ����������������
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // ɾ�����ļ���
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// ɾ��ָ���ļ����������ļ�
	// param path �ļ�����������·��
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// ��ɾ���ļ���������ļ�
				delFolder(path + "/" + tempList[i]);// ��ɾ�����ļ���
				flag = true;
			}
		}
		return flag;
	}
	
	private String getPath() {
		//�ϴ�·��
		String upload_file = AppConfig.HZB_PATH + "/temp/zipload/";
		try {
			File file =new File(upload_file);    
			//����ļ��в������򴴽�    
			if  (!file .exists()  && !file .isDirectory())      
			{       
			    file .mkdirs();    
			}
		} catch (Exception e1) {
			e1.printStackTrace();			
		}
		//��ѹ·��
		String zip = upload_file + uuid + "/";
		return zip;
	}
	
	public static Long getFileSize(File f) {
		FileChannel fc = null;
		try {
			if (f.exists() && f.isFile()) {
				FileInputStream fis = new FileInputStream(f);
				fc = fis.getChannel();
				return fc.size();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != fc) {
				try {
					fc.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return 0L;
	}
}
