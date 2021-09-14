package com.insigma.siis.local.pagemodel.repandrec.plat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import com.insigma.odin.framework.AppException;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.PrivilegeManager;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.radow.event.EventRtnType;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.odin.framework.util.SysUtil;
import com.insigma.siis.local.business.datavaerify.DataVerifyBS;
import com.insigma.siis.local.business.datavaerify.UploadHzbFileBS;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.Dataexchangeconf;
import com.insigma.siis.local.business.entity.Ftpuser;
import com.insigma.siis.local.business.entity.Imprecord;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.repandrec.local.KingbsGainBS;
import com.insigma.siis.local.business.repandrec.local.KingbsconfigBS;
import com.insigma.siis.local.business.sysorg.org.CreateSysOrgBS;
import com.insigma.siis.local.business.utils.Dom4jUtil;
import com.insigma.siis.local.business.utils.NewSevenZipUtil;
import com.insigma.siis.local.business.utils.SevenZipUtil;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.epsoft.util.FileUtil;
import com.insigma.siis.local.epsoft.util.JXUtil;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.jtrans.SFileDefine;
import com.insigma.siis.local.jtrans.ZwhzFtpPath;
import com.insigma.siis.local.jtrans.ZwhzPackDefine;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.Map2Temp;
import com.insigma.siis.local.pagemodel.dataverify.UploadHelpFileServlet;
/**
 * ���ݽ����߳�
 * @author zxf
 */
public class RecieiveHzbThread implements Runnable {
	
	private String uuid;
	private String filename;
	private CurrentUser user;
	private UserVO userVo;
    public RecieiveHzbThread(String filename, String uuid, CurrentUser user,UserVO userVo) {
        this.uuid = uuid;
        this.filename = filename;
        this.user = user;
        this.userVo = userVo;
    }

	@Override
	public void run() {
		UploadHzbFileBS uploadbs = new UploadHzbFileBS();
		String packXmlStr = null;							//�ܰ��ļ������ַ���
		ZwhzPackDefine zpdefine = null;						//�ܰ��ļ���������
	    String rootpath = getrPath();
	    HBSession sess = HBUtil.getHBSession();
	    String imprecordid = uuid;
	    List<String> delps = new ArrayList<String>();
	    long t_n = 0;
	    String process_run = "1";							//���̹������
	    boolean boo = true;
	    String tableExt = "";
		try {
//			sess.beginTransaction();
			tableExt = getNo() + DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmssS");
			List<Ftpuser> ftpus = sess.createQuery("from Ftpuser t").list();	//��ѯftp�û��������û���Ŀ¼���жϴ����ļ����Ǹ��û���
			for (Ftpuser ftpuser : ftpus) {																
				String zhbupfile = ftpuser.getHomedirectory()+ZwhzFtpPath.HZB_UP +"/" +filename; 
				File pfile = new File(zhbupfile);
				if (pfile.exists() && pfile.isFile()) {													//�ҵ���Ӧ�ļ������д���
					packXmlStr = FileUtil.readFileByChars(pfile.getPath(),"UTF-8"); 					//��ȡ�ܰ��ļ�����String
					if(packXmlStr!=null){
						zpdefine = (ZwhzPackDefine)JXUtil.Xml2Object(packXmlStr, ZwhzPackDefine.class); //�����ܰ��ļ� ��װ����
						List<SFileDefine> files = zpdefine.getSfile();									//��ȡ�����������ļ���Ϣ
						Imprecord imprecord = null;
						if(files!=null && files.size()>0){					//���������ļ����ֱ���н�ѹ����������xml����������
							for (SFileDefine fileDefine : files) {										//�����ж������ļ��Ƿ�ȫ������
								File datafile = new File(ftpuser.getHomedirectory()+ZwhzFtpPath.HZB_UP +"/" +fileDefine.getName());
								if(!datafile.exists() || datafile.isDirectory()){
									KingbsconfigBS.saveImpDetail(process_run,"4","�ϴ������ļ���ȫ",uuid);	//��¼�������
									boo = false;
									return;
								}
							}
							KingbsconfigBS.saveImpDetail(process_run,"2","���",imprecordid);
							process_run = "2";
							KingbsconfigBS.saveImpDetail(process_run,"1","������",imprecordid);
							uploadbs.createTempTable(tableExt);//������ʱ��
							t_n = zpdefine.getPersoncount();
							for (SFileDefine fileDefine : files) {
								String unzip = rootpath + "unzip/" + uuid + "/";						// �ļ���ѹ·��
								File file = new File(unzip);
								if (!file.exists() && !file.isDirectory()) {							// ����ļ��в������򴴽�
									file.mkdirs();
								}
								delps.add(unzip);
								// ��ѹ�ļ��� �ļ���ѹ·��
								NewSevenZipUtil.unzip7zAll((ftpuser.getHomedirectory().substring(1)+ZwhzFtpPath.HZB_UP +"/" +fileDefine.getName()).replace("/", "\\"), unzip.replace("/", "\\"), "1");
								List<Map<String, String>> headlist = Dom4jUtil							//��ȡ����imprecord��Ϣ��
										.gwyinfoF(rootpath + "unzip/" + uuid + "/gwyinfo.xml");
								String from_file = unzip + "Photos/";
								List<B01> grps = HBUtil.getHBSession().createQuery(						//��ȡ��ǰ�û�������Ϣ��
										"from B01 t where t.b0111 in(select b0111 from UserDept where userid='"
												+ user.getId() + "')").list();
								B01 gr = null;
								if (grps != null && grps.size() > 0) {
									gr = grps.get(0);
								}
								List<B01> detps = null;													//��ȡҪ����Ļ�����Ϣ��
								String B0111 = headlist.get(0).get("B0111");							// ���ڵ��ϼ�����id
								String deptid = "";														// ���ڵ��ϼ�����id
								String impdeptid = "";													//���ڵ����id
								detps = HBUtil.getHBSession().createQuery(
										"from B01 t where t.b0101='" + headlist.get(0).get("B0101")
												+ "' and t.b0114='" + headlist.get(0).get("B0114")
												+ "'").list();
								if (detps != null && detps.size() > 0) {
									impdeptid = detps.get(0).getB0111();
									deptid = detps.get(0).getB0121();
								} else {
									KingbsconfigBS.saveImpDetail(process_run,"4","δƥ�䵽�������",uuid);	//��¼�������
									boo = false;
									throw new Exception("δƥ�䵽���������");
								}
								List<Imprecord> imprecords = Map2Temp.toTemp("Imprecord", headlist);	//�����¼��Ϣ
								if (imprecords != null && imprecords.size() > 0) {
									if(imprecord == null){
										imprecord = imprecords.get(0);
										imprecord.setImptime(DateUtil.getTimestamp());
										imprecord.setImpuserid(user.getId());
										if (gr != null) {
											imprecord.setImpgroupid(gr.getB0111());
											imprecord.setImpgroupname(gr.getB0101());
										}
										imprecord.setIsvirety("1");
										imprecord.setFilename(filename);
										imprecord.setFiletype("hzb");
										imprecord.setImptype("1");
										imprecord.setEmpdeptid(headlist.get(0).get("B0111"));
										imprecord.setEmpdeptname(headlist.get(0).get("B0101"));
										imprecord.setImpdeptid(impdeptid);
										imprecord.setImpstutas("1");
										imprecord.setTotalnumber(t_n + "");
										imprecord.setWrongnumber(0 + "");
										imprecord.setImprecordid(uuid);
										imprecord.setImptemptable(tableExt);
										sess.update(imprecord);
									}
									
									imprecordid = imprecord.getImprecordid();
									//�ֱ��ȡxml����������
									// ----------------------------------------------------------------------------------------
									String tables[] = { "A01", "A02", "A06", "A08", "A11", "A14", "A15",
											"A29", "A30", "A31", "A36", "A37", "A41", "A53", "A57", "B01",
											"I_E", "B_E","A60", "A61", "A62", "A63", "A64"  };
									if(DBUtil.getDBType().equals(DBType.MYSQL)){
										for (int i = 0; i < tables.length; i++) {
											KingbsconfigBS.saveImpDetail(process_run, "1", "��ȡ��"+ tables[i]+"����" , imprecordid);
											CommonQueryBS.systemOut(tables[i]+ "���ݵ���"+ DateUtil.getTime() + "===>�������ݿ�ʼ"+ "neicun��"+ (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
											uploadbs.saveData_SaxHander4("hzb".toLowerCase(), tables[i], imprecordid, uuid,from_file, B0111, deptid, impdeptid, tableExt);
											CommonQueryBS.systemOut(tables[i]+ "���ݵ���"+ DateUtil.getTime() + "===>�������ݽ���"+ "neicun��"+ (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
											
										}
										
									} else {
										for (int i = 0; i < tables.length; i++) {
											KingbsconfigBS.saveImpDetail(process_run, "1", "��ȡ��"+ tables[i] + "���ݣ� ʣ��"+ "����", imprecordid);
											CommonQueryBS.systemOut(tables[i]+ "���ݵ���"+ DateUtil.getTime() + "===>�������ݿ�ʼ"+ "neicun��"+ (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
											uploadbs.saveData_SaxHander3("hzb".toLowerCase(), tables[i], imprecordid, uuid,from_file, B0111, deptid, impdeptid, tableExt);
											CommonQueryBS.systemOut(tables[i]+ "���ݵ���"+ DateUtil.getTime() + "===>�������ݽ���"+ "neicun��"+ (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
											
										}
									}
								}
								try {
									UploadHelpFileServlet.delFolder(unzip+"Table");
									UploadHelpFileServlet.delFolder(unzip+"gwyinfo.xml");
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							if(imprecord != null){
								imprecord.setProcessstatus("2");
								sess.update(imprecord);
							}
						} else {
							KingbsconfigBS.saveImpDetail(process_run,"4","�ļ��쳣������",uuid);	//��¼�������
							boo = false;
						}
						KingbsconfigBS.saveImpDetail(process_run,"2","���",imprecordid);
						process_run = "3";
						KingbsconfigBS.saveImpDetail(process_run,"1","������",imprecordid);
						if(boo){
							try{
//								boolean flag = DataVerifyBS.dataVerifyByBSType(imprecordid, null,"2", null,userVo,"0");
								KingbsconfigBS.saveImpDetail(process_run,"2","���",imprecordid, "orgdataverify");
							}catch (Exception e) {
								e.printStackTrace();
								KingbsconfigBS.saveImpDetail(process_run,"4","У��������⣬�����Ѿ����롣",uuid);	//��¼�������
								
							}
							try {
								new LogUtil("441", "IMP_RECORD", "", "", "���ݽ���", new ArrayList(),userVo).start();
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
				} 
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				KingbsconfigBS.saveImpDetail(process_run,"4",e.getMessage(),uuid);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			if(delps!=null && delps.size()>0){
				for (int i = 0; i < delps.size(); i++) {
					UploadHelpFileServlet.delFolder(delps.get(i));
				}
			}
			uploadbs.rollbackImp(imprecordid);
			if(sess != null){
				try {
					sess.getTransaction().rollback();
				} catch (Exception e2) {
					e.printStackTrace();
				}
			}
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
	
	private String getrPath() {
		String upload_file = AppConfig.HZB_PATH + "/temp/upload/";
		return upload_file;
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
