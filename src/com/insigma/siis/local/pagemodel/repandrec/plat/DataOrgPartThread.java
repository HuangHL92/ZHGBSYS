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
import com.insigma.siis.local.business.utils.Xml4HZBNewUtil;
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
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.UploadHelpFileServlet;

public class DataOrgPartThread implements Runnable {
	
	private String uuid;
	private String linkpsn;
	private String linktel;
	private String remark;
	private String ftpid;
	private String time;
	private String time1;
	private UserVO userVo;
	private DataOrgRepControl control;
	private String no;
	
	public DataOrgPartThread(String uuid, String linkpsn, String linktel,
			String remark, String ftpid, String time, String time1,
			UserVO userVo, DataOrgRepControl control,String no) {
		super();
		this.uuid = uuid;
		this.linkpsn = linkpsn;
		this.linktel = linktel;
		this.remark = remark;
		this.ftpid = ftpid;
		this.time = time;
		this.time1 = time1;
		this.userVo = userVo;
		this.control = control;
		this.no = no;
	}

	@Override
	public void run() {
		//----- �� packcount ÿ 15000 �˽��зְ������ɶ�����ܰ��ļ�  ------------------------------------------------
		HBSession sess = HBUtil.getHBSession();
		Map<String, String> map = new HashMap<String, String>();	
		String localf = AppConfig.LOCAL_BACKUP_FILE;		//fpt ���ر������ݴ�Ÿ�Ŀ¼
		String process_run = "1";							//���̹������
		String a01new = control.getA01new();
		int count = control.getCount();
		int packcount = control.getPackcount();
		B01 b01 = control.getB01();
		ZwhzPackDefine info = control.getInfo();
		Long orgrows = 0l;
		String path = control.getPath();
		String sid = control.getSid();
		String packageFile = control.getPackageFile();
		List<SFileDefine> sfile = control.getSfile();
		try {
			process_run = "2";
			KingbsconfigBS.saveImpDetail(process_run,"1","������",uuid);			//��¼�������
			// ---- 03-���������ļ� ----------------------------------------------------------------------------
			int i = 0;
			do {
				i = control.getNewNumber();
				if(i > count){
					break;
				}
				SFileDefine sf = new SFileDefine();
				StringBuilder a01sql = new StringBuilder();
				a01sql.append(" select a0000 from ");
				a01sql.append(a01new);
				a01sql.append(" where rn between  " + ((i - 1) * packcount) + " and " + i*packcount);
				a01sql.append(" ");
				Long personrows = Long.valueOf(packcount);					//����������Ա��Ŀ
				if (i == count) {
					if (DBUtil.getDBType().equals(DBType.MYSQL)) {
						BigInteger a01size = (BigInteger) sess.createSQLQuery("select count(1) from ("+a01sql+") as a ").uniqueResult();
						personrows = a01size.longValue();
					} else if (DBUtil.getDBType().equals(DBType.ORACLE)) {
						BigDecimal a01size = (BigDecimal) sess.createSQLQuery("select count(1) from ("+a01sql+") ").uniqueResult();
						personrows = a01size.longValue();
					}
				}
				
				List<Map> list17 = new ArrayList<Map>();					//���ݰ���ͷ�ļ���Ϣ gwyinfo.xml
				map.put("type", "2");
				map.put("time", time);
				map.put("dataversion", "20121221");
				map.put("psncount", personrows+"");
				map.put("photodir", "Photos");
				map.put("B0101", b01.getB0101());
				map.put("B0111", b01.getB0111());
				map.put("B0114", b01.getB0114());
				map.put("B0194", b01.getB0194());
				map.put("linkpsn", linkpsn);
				map.put("linktel", linktel);
				map.put("remark", remark);
				list17.add(map);
				
				//�����ļ�������Ϣ ����ʱ�� ���������������� 
				sf.setTime(time);
				if(i==1){
					if (DBUtil.getDBType().equals(DBType.MYSQL)) {
						BigInteger b01size = (BigInteger) sess.createSQLQuery("select count(1) from b01 ").uniqueResult();
						info.setOrgcount(b01size.longValue());
						orgrows = b01size.longValue();
					} else if (DBUtil.getDBType().equals(DBType.ORACLE)) {
						BigDecimal b01size = (BigDecimal) sess.createSQLQuery("select count(1) from b01 ").uniqueResult();
						info.setOrgcount(b01size.longValue());
						orgrows = b01size.longValue();
					}
					sf.setOrgrows(orgrows);
				} else {
					sf.setOrgrows(0l);
				}
				sf.setPersonrows(personrows);
				
				String number = ("000" + i).substring(("000" + i).length() - 3);	//ȷ�����������ļ��� ͨ�� i ����001 002 ���ļ���
				String zippath = path + "�����������ļ�_" + b01.getB0111() + "_"
					+ b01.getB0101() + "_" + time1 + "/" + number + "/";		//��ʱ�ļ�����·��
				File file = new File(zippath);
				if (!file.exists() && !file.isDirectory()) {						
				file.mkdirs();
				}
				String zippathtable = path + "�����������ļ�_" + b01.getB0111() + "_"	//��ʱ�ļ�table�������xml��·��
					+ b01.getB0101() + "_" + time1 + "/" + number
					+ "/Table/";
				File file1 = new File(zippathtable);
				if (!file1.exists() && !file1.isDirectory()) {
				file1.mkdirs();
				}
				String zipfile = localf + "/" + "�����������ļ�_" + b01.getB0114() + "_"	//�ļ���
						+ b01.getB0101() + "_" + time1 + "_" + number + ".hzb";
				int number1 = 1;														//�ѽ��������ľ
				int number2 = 20;														//δ���������ľ
				//---- 03-01 ���������ļ� ---------------------------------------------------------------------
				Connection conn = sess.connection();
				Statement stmt = null;
				KingbsconfigBS.saveImpDetail(process_run,"1","�ļ�"+(number1++)+"��A01.xml�������ɴ���ʣ��"+(number2--)+"",uuid);		//��¼�������
				
				Xml4PackageNewUtil.data2Xml(a01sql.toString(), "A01", zippath, conn, packcount, i, a01new);
				CommonQueryBS.systemOut(DateUtil.getTime());
				
				KingbsconfigBS.saveImpDetail(process_run,"1","�ļ�"+(number1++)+"��A02.xml�������ɴ���ʣ��"+(number2--)+"",uuid);		//��¼�������
				Xml4PackageNewUtil.data2Xml(a01sql.toString(), "A02", zippath, conn);
				
				KingbsconfigBS.saveImpDetail(process_run,"1","�ļ�"+(number1++)+"��A06.xml�������ɴ���ʣ��"+(number2--)+"",uuid);		//��¼�������
				Xml4PackageNewUtil.data2Xml(a01sql.toString(), "A06", zippath, conn);
				
				KingbsconfigBS.saveImpDetail(process_run,"1","�ļ�"+(number1++)+"��A08.xml�������ɴ���ʣ��"+(number2--)+"",uuid);		//��¼�������
				Xml4PackageNewUtil.data2Xml(a01sql.toString(), "A08", zippath, conn);
				
				KingbsconfigBS.saveImpDetail(process_run,"1","�ļ�"+(number1++)+"��A11.xml�������ɴ���ʣ��"+(number2--)+"",uuid);		//��¼�������
				Xml4PackageNewUtil.data2Xml(a01sql.toString(), "A11", zippath, conn);
				
				KingbsconfigBS.saveImpDetail(process_run,"1","�ļ�"+(number1++)+"��A14.xml�������ɴ���ʣ��"+(number2--)+"",uuid);		//��¼�������
				Xml4PackageNewUtil.data2Xml(a01sql.toString(), "A14", zippath, conn);
				
				KingbsconfigBS.saveImpDetail(process_run,"1","�ļ�"+(number1++)+"��A15.xml�������ɴ���ʣ��"+(number2--)+"",uuid);		//��¼�������
				Xml4PackageNewUtil.data2Xml(a01sql.toString(), "A15", zippath, conn);
				
				KingbsconfigBS.saveImpDetail(process_run,"1","�ļ�"+(number1++)+"��A29.xml�������ɴ���ʣ��"+(number2--)+"",uuid);		//��¼�������
				Xml4PackageNewUtil.data2Xml(a01sql.toString(), "A29", zippath, conn);
				
				KingbsconfigBS.saveImpDetail(process_run,"1","�ļ�"+(number1++)+"��A30.xml�������ɴ���ʣ��"+(number2--)+"",uuid);		//��¼�������
				Xml4PackageNewUtil.data2Xml(a01sql.toString(), "A30", zippath, conn);
				
				KingbsconfigBS.saveImpDetail(process_run,"1","�ļ�"+(number1++)+"��A31.xml�������ɴ���ʣ��"+(number2--)+"",uuid);		//��¼�������
				Xml4PackageNewUtil.data2Xml(a01sql.toString(), "A31", zippath, conn);
				
				KingbsconfigBS.saveImpDetail(process_run,"1","�ļ�"+(number1++)+"��A36.xml�������ɴ���ʣ��"+(number2--)+"",uuid);		//��¼�������
				Xml4PackageNewUtil.data2Xml(a01sql.toString(), "A36", zippath, conn);
				
				KingbsconfigBS.saveImpDetail(process_run,"1","�ļ�"+(number1++)+"��A37.xml�������ɴ���ʣ��"+(number2--)+"",uuid);		//��¼�������
				Xml4PackageNewUtil.data2Xml(a01sql.toString(), "A37", zippath, conn);
				
				KingbsconfigBS.saveImpDetail(process_run,"1","�ļ�"+(number1++)+"��A41.xml�������ɴ���ʣ��"+(number2--)+"",uuid);		//��¼�������
				Xml4PackageNewUtil.data2Xml(a01sql.toString(), "A41", zippath, conn);
				
				KingbsconfigBS.saveImpDetail(process_run,"1","�ļ�"+(number1++)+"��A53.xml�������ɴ���ʣ��"+(number2--)+"",uuid);		//��¼�������
				Xml4PackageNewUtil.data2Xml(a01sql.toString(), "A53", zippath, conn);
				
				KingbsconfigBS.saveImpDetail(process_run,"1","�ļ�"+(number1++)+"��A57.xml�������ɴ���ʣ��"+(number2--)+"",uuid);		//��¼�������
				Xml4PackageNewUtil.data2Xml(a01sql.toString(), "A57", zippath, conn);
				
				KingbsconfigBS.saveImpDetail(process_run,"1","�ļ�"+(number1++)+"��A60.xml�������ɴ���ʣ��"+(number2--)+"",uuid);		//��¼�������
				Xml4PackageNewUtil.data2Xml(a01sql.toString(), "A60", zippath, conn);
				
				KingbsconfigBS.saveImpDetail(process_run,"1","�ļ�"+(number1++)+"��A61.xml�������ɴ���ʣ��"+(number2--)+"",uuid);		//��¼�������
				Xml4PackageNewUtil.data2Xml(a01sql.toString(), "A61", zippath, conn);
				
				KingbsconfigBS.saveImpDetail(process_run,"1","�ļ�"+(number1++)+"��A62.xml�������ɴ���ʣ��"+(number2--)+"",uuid);		//��¼�������
				Xml4PackageNewUtil.data2Xml(a01sql.toString(), "A62", zippath, conn);
				
				KingbsconfigBS.saveImpDetail(process_run,"1","�ļ�"+(number1++)+"��A63.xml�������ɴ���ʣ��"+(number2--)+"",uuid);		//��¼�������
				Xml4PackageNewUtil.data2Xml(a01sql.toString(), "A63", zippath, conn);
				
				KingbsconfigBS.saveImpDetail(process_run,"1","�ļ�"+(number1++)+"��A64.xml�������ɴ���ʣ��"+(number2--)+"",uuid);		//��¼�������
				Xml4PackageNewUtil.data2Xml(a01sql.toString(), "A64", zippath, conn);
				
				KingbsconfigBS.saveImpDetail(process_run,"1","�ļ�"+(number1++)+"��B01.xml�������ɴ���ʣ��"+(number2--)+"",uuid);		//��¼�������
				if (i == 1) {
					Xml4PackageNewUtil.data2Xml(a01sql.toString(), "B01", zippath, conn);
				} else {
					Xml4HZBUtil.List2Xml(new ArrayList(), "B01", zippath);
				}
				
				//--------- ������Ϣxml���� INFO_EXTEND.xml\B01_EXT.xml---------------------------------------------
				KingbsconfigBS.saveImpDetail(process_run,"1","�����������ɴ���",uuid);		//��¼�������
				Xml4PackageNewUtil.data2Xml(a01sql.toString(), "INFO_EXTEND", zippath, conn);
				
				if (i == 1) {
					Xml4PackageNewUtil.data2Xml(a01sql.toString(), "B01_EXT", zippath, conn);
				} else {
					stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
					stmt.setFetchSize(5000);
					stmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
					ResultSet b01_info = stmt.executeQuery("select * from B01_EXT where 1<>1");
					Xml4HZBUtil2.List2Xml(b01_info, "B01_EXT", zippath);
					b01_info.close();
					stmt.close();
				}
				CommonQueryBS.systemOut("���ݵ���"+ DateUtil.getTime()+"\n"+"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				
				KingbsconfigBS.saveImpDetail(process_run,"1","����˵���ļ����ɴ�����",uuid);	
				Xml4PackageNewUtil.List2Xml(list17, "info", zippath);
				
				KingbsconfigBS.saveImpDetail(process_run,"1","��Ա��Ƭͷ�����ɴ�����",uuid);	

				KingbsconfigBS.saveImpDetail(process_run,"1","��Ա��Ƭͷ�����ɴ�����",uuid);	
				String newTable = "A57" + System.currentTimeMillis();
				String createsql = "";
				if(DBUtil.getDBType().equals(DBType.MYSQL)){
					createsql = "create table " + newTable + " as "
							+ "SELECT @rownum :=@rownum + 1 AS rn, "
							+ "a0000,a5714,photoname,photstype,updated,photopath "
							+ " FROM(SELECT @rownum := 0,"
							+ "a0000,a5714,photoname,photstype,updated,photopath"
							+ " FROM A57 t where a0000 in(" + a01sql + ")) as a";
				}  else {
					createsql = "create table " + newTable + " as "
							+ "select rownum rn,a0000,a5714,photoname,photstype,updated,photopath from A57 t where a0000 in(" + a01sql + ") ";
				}
				conn.createStatement().execute(createsql);
				conn.createStatement().execute("create index index_" + newTable + " on " + newTable + " (rn)");
				Object obj = HBUtil.getHBSession().createSQLQuery(			//������������
						"select count(1) from " + newTable).uniqueResult();
				int count1 = 0;		//�����˴�
				if (obj != null) {
					if (DBUtil.getDBType().equals(DBType.MYSQL)) {			
						count1 = ((BigInteger) obj).intValue()/ 2000
								+ (((BigInteger) obj).intValue() % 2000 != 0 ? 1 : 0);
					} else if (DBUtil.getDBType().equals(DBType.ORACLE)) {
						count1 = ((BigDecimal) obj).intValue()/ 2000
								+ (((BigDecimal) obj).intValue() % 2000 != 0 ? 1 : 0);
					}
				}
				if(count1 == 0){
					count1 = 1;
				}
				for (int j = 0; j < count1; j++) {
					stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
					stmt.setFetchSize(500);
					stmt.setFetchDirection(ResultSet.FETCH_REVERSE); 
					CommonQueryBS.systemOut("neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
					ResultSet rs = null;
					rs = stmt.executeQuery("select a0000,a5714,photoname,photstype,updated,photopath "+ 
							"from " + newTable + " t where rn between " + (2000*j) +" and " + (2000*(1+j)) );
					
					if(rs!=null){
						String photopath = zippath + "Photos/";				//����ͼƬ·��      
						File file2 =new File(photopath);    
						if  (!file2 .exists()  && !file2 .isDirectory()){   //����ļ��в������򴴽�       
							file2 .mkdirs();    
						}
						List<String> photolist = new ArrayList<String>();
						while (rs.next()) {
							String a0000 = rs.getString("a0000");
							String photoname = rs.getString("photoname");
							String photop = rs.getString("photopath");
							String photon = (photoname!=null&&!photoname.equals(""))? photoname:a0000 +"." + "jpg";
							photolist.add(photop+photon);
						}
						PhotosUtil.copyPhotos(photolist, photopath);
					}
					rs.close();
					stmt.close();
				}
				conn.createStatement().execute("drop table " + newTable);
				conn.close();
				NewSevenZipUtil.zip7zNew(zippath, zipfile, "1");
				File file0 = new File(zipfile);
				sf.setName(file0.getName());
				sf.setSize(getFileSize(file0));
				sfile.add(sf);
				this.delFolder(zippath);
			} while (true);
			if(control.getStatus()==2){
				KingbsconfigBS.saveImpDetail(process_run,"2","���",uuid);			//��¼�������
				process_run = "3";
				KingbsconfigBS.saveImpDetail(process_run,"1","������",uuid);			//��¼�������
				HBUtil.getHBSession().createSQLQuery("drop table " + a01new).executeUpdate();
				info.setDatainfo("������λ��Ϣ������" + info.getOrgcount() + "������Ա������Ϣ��"
						+ info.getPersoncount() + "����");
				info.setSfile(sfile);
				CommonQueryBS.systemOut(JXUtil.Object2Xml(info, true));
				FileUtil.createFile(localf + "/" + packageFile,
						JXUtil.Object2Xml(info, true), false, "UTF-8");
				if (StringUtil.isEmpty(ftpid)) {
					throw new AppException("�ϱ���λΪ�գ�������ѡ��");
				}
				//--------- ��������id��ȡ����fpt������Ϣ -------------------------------------
				TransConfig jfcc = (TransConfig) HBUtil.getHBSession().get(
						TransConfig.class, ftpid);
				//---------------�ϱ���Ϣ��¼�����ö�Ӧֵ���������ݿ�-------------
				Reportftp rfpt = new Reportftp();
				rfpt.setFilename("");
				rfpt.setPackageindex(sid);
				rfpt.setPackagename(packageFile);
				rfpt.setRecieveftpuserid(jfcc.getId());
				rfpt.setRecieveftpusername(jfcc.getName());
				rfpt.setReporttime(DateUtil.getTimestamp());
				rfpt.setB0111(b01.getB0111());
				rfpt.setReporttype("1");
				rfpt.setReportuser(userVo.getId());
				rfpt.setReportusername(userVo.getName());
				try {
					sess.beginTransaction();
					sess.save(rfpt);
					//--------- �ϱ�hzb��Ϣ ---------------------------------------------
					ZwhzFtpClient.uploadHzb(jfcc, localf + "/" + packageFile);
					sess.getTransaction().commit();
				} catch (Exception e) {
					e.printStackTrace();
					try {
						KingbsconfigBS.saveImpDetail(process_run,"4", e.getMessage(),uuid);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					sess.getTransaction().rollback();
					throw new AppException(e.getMessage());

				}
				KingbsconfigBS.saveImpDetail(process_run,"2","���",uuid,"datarep");			//��¼�������
				//--------- �ϱ�hzb��Ϣ ---------------------------------------------
				try {
					new LogUtil("431", "REPORT_FTP", "", "", "�����ϱ�", new ArrayList(),userVo).start();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			
			try {
				KingbsconfigBS.saveImpDetail(process_run,"4", e.getMessage(),uuid);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			try {
				HBUtil.getHBSession().createSQLQuery("drop table " + a01new).executeUpdate();
			} catch (Exception e2) {
				e.printStackTrace();
			}
			this.delFolder(path);
			if(no.equals("1")){
				control.errStatus("1");
			}else {
				control.errStatus("2");
			}
			this.delFolder(control.getPath());
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
