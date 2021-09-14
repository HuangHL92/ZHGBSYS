package com.insigma.siis.local.pagemodel.zj.impHzb;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.siis.local.business.entity.B01;
import com.insigma.siis.local.business.entity.Imprecord;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.repandrec.local.KingbsconfigBS;
import com.insigma.siis.local.business.utils.Dom4jUtil;
import com.insigma.siis.local.business.utils.NewSevenZipUtil;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;
import com.insigma.siis.local.pagemodel.dataverify.Zip7z;
import com.insigma.siis.local.util.TYGSsqlUtil;

public class ZjImpHzbThread implements Runnable {
	
	private String uuid;
	private String filename;
	private CurrentUser user;
	private UserVO userVo;
	private String a0165;
	private String fxz;
    public ZjImpHzbThread(String filename, String uuid, CurrentUser user,UserVO userVo,String a0165,String fxz) {
        this.uuid = uuid;
        this.filename = filename;
        this.user = user;
        this.userVo = userVo;
        this.a0165 = a0165;
        this.fxz = fxz;
    }

	@Override
	public void run() {
		String rootPath = "";									// ��Ŀ·��
		String imprecordid = uuid;								// �����¼id
		String process_run = "1";								// ����������
		ZjUploadHzbFileBS uploadbs = new ZjUploadHzbFileBS();		// ҵ����bs
		HBSession sess = HBUtil.getHBSession();
		String filePath ="";									// �ϴ��ļ�����·��
		String unzip = "";										// ��ѹ·��
		String upload_file = "";
		String tableExt = "";
		try {
			// ��¼��־�ļ�
			String logfilename = AppConfig.HZB_PATH + "/temp/upload/" +DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmss") +".txt";
			File logfile = new File(logfilename);
			if(!logfile.exists()){
				logfile.createNewFile();
			}
			appendFileContent(logfilename, "��ʼ����:"+ DateUtil.getTime()+"\n");
			
			String houzhui = filename.substring(filename.lastIndexOf(".") + 1, filename.length());  //�ļ���׺
			String from_file = "";
				String classPath = getClass().getClassLoader().getResource("/").getPath();				// class ·��
				if ("\\".equals(File.separator)) {														// windows��
					rootPath = classPath.substring(1, classPath.indexOf("WEB-INF/classes"));
					rootPath = rootPath.replace("/", "\\");
				}
				rootPath = URLDecoder.decode(rootPath, "GBK");
				upload_file = AppConfig.HZB_PATH + "/temp/upload/" + uuid + "/";									// �ϴ�·��
				unzip = AppConfig.HZB_PATH + "/temp/upload/unzip/" + uuid + "/";										// ��ѹ·��
				File file = new File(unzip);															// ����ļ��в������򴴽�
				if (!file.exists() && !file.isDirectory()) {
					file.mkdirs();
				}
				
				filePath = upload_file + "/" + uuid + "." +houzhui;									    //�ϴ��ļ�����·��
				from_file = unzip + "Photos/";													//��ѹ��ͼƬ���·��
				File f_file = new File(from_file);
				if (!f_file.exists() && !f_file.isDirectory()) {
					f_file.mkdirs();
				}
				
				// 002================  �ļ���ѹ   =========================================================================
				CommonQueryBS.systemOut("START UPZIP---------" +DateUtil.getTime() +"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
				appendFileContent(logfilename, "��ѹ����ʼ:"+ DateUtil.getTime()+"\n");					//��¼��־
				if (houzhui.equalsIgnoreCase("hzb")) {
					NewSevenZipUtil.unzip7zAll(filePath, unzip, "1");
				} else if(houzhui.equalsIgnoreCase("7z")) {
					Zip7z.unzip7zAll(filePath, unzip, "20171020");
				} else if(houzhui.equalsIgnoreCase("zip")) {
					Zip7z.unzip7zAll(filePath, unzip, null);
				} else {
					NewSevenZipUtil.unzip7zAll(filePath, unzip, "2");
				}
				appendFileContent(logfilename, "��ѹ������:"+ DateUtil.getTime()+"\n");					//��¼��־
				KingbsconfigBS.saveImpDetail("1","2","���",imprecordid);								//��¼�������
			process_run = "2";																		//�������
			tableExt = getNo() + DateUtil.timeToString(DateUtil.getTimestamp(), "MMddHHmmssS");
			
			//002================  ����ͷ�ļ�   =========================================================================
			KingbsconfigBS.saveImpDetail(process_run,"1","������",imprecordid);						//��¼�������
			List<Map<String, String>> headlist = Dom4jUtil
					.gwyinfoF(unzip + "" + "gwyinfo.xml");
			List<B01> grps = HBUtil.getHBSession().createQuery(
					"from B01 t where t.b0111 in(select b0111 from UserDept where userid='"
							+ user.getId() + "')").list();
			B01 gr = null;
			if (grps != null && grps.size() > 0) {
				gr = grps.get(0);
			}
			String B0111 = "";
			String deptid = "";														// ���ڵ��ϼ�����id
			String impdeptid = "";													//���ڵ����id
			List<Imprecord> imprecords = ZjMap2Temp.toTemp("Imprecord", headlist);
			if (imprecords != null && imprecords.size() > 0) {
				Imprecord imprecord = imprecords.get(0);
				imprecord.setImptime(DateUtil.getTimestamp());
				imprecord.setImpuserid(user.getId());
				if (gr != null) {
					imprecord.setImpgroupid(gr.getB0111());
					imprecord.setImpgroupname(gr.getB0101());
				}
				imprecord.setIsvirety("0");
				imprecord.setFilename(filename);
				imprecord.setFiletype(houzhui);
				imprecord.setImptype("4");
				imprecord.setEmpdeptid(headlist.get(0).get("B0111"));
				imprecord.setEmpdeptname(headlist.get(0).get("B0101"));
				imprecord.setImpdeptid(impdeptid);
				imprecord.setImpstutas("1");
				imprecord.setPsncount((headlist.get(0).get("psncount")!=null&& !headlist.get(0).get("psncount").equals(""))?Long.parseLong(headlist.get(0).get("psncount")):0L);
				imprecord.setLinkpsn(headlist.get(0).get("linkpsn")!=null?headlist.get(0).get("linkpsn"):headlist.get(0).get("contact"));
				imprecord.setLinktel(headlist.get(0).get("linktel")!=null?headlist.get(0).get("linktel"):headlist.get(0).get("tel"));
				imprecord.setImprecordid(uuid);
				imprecord.setImptemptable(tableExt);
				imprecord.setA0165(a0165);
				sess.update(imprecord);
				imprecordid = imprecord.getImprecordid();
				uploadbs.createTempTable(tableExt);
				KingbsconfigBS.saveImpDetail(process_run,"2","���",imprecordid);				//��¼�������
				process_run = "3";																//�������
				int t_n = 0;
				int k = 0;
				//==========  ���������ļ����������ݿ�   =================================================================================
				String[] tables = {"A01","A02","A06","A08","A14","A15","A36","A57","B01","A05","A99Z1","A30", "Extra_Tags", "A0193_Tag", "A0194_Tag", "Tag_Rclx", "Tag_Cjlx", "Tag_Sbjysjl", "Tag_Kcclfj", "Tag_Kcclfj2", "Tag_Ndkhdjbfj", "Tag_Dazsbfj"};
				int number1 = 1;
				int number2 = tables.length;
				if(DBUtil.getDBType().equals(DBType.MYSQL)){
					for(int i = 0;i<tables.length;i++){
						appendFileContent(logfilename, "==============================================="+"\n");
						appendFileContent(logfilename, tables[i]+"���ݵ���"+ DateUtil.getTime()+"\n"+"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
						KingbsconfigBS.saveImpDetail(process_run,"1","��ȡ��"+(number1++)+"�ű�"+tables[i]+"���ݣ� ʣ��"+(number2--)+"�š�",imprecordid);
						t_n = uploadbs.saveData_SaxHander4(houzhui.toLowerCase(), tables[i], imprecordid, uuid,from_file, B0111, deptid, impdeptid, tableExt);
						appendFileContent(logfilename, tables[i]+"���ݵ������"+ DateUtil.getTime()+"\n"+"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
						CommonQueryBS.systemOut(tables[i]);
						if("A01".equals( tables[i])){
							k= t_n;
						}
					}
					
				}else{
					for(int i = 0;i<tables.length;i++){
						appendFileContent(logfilename, "==============================================="+"\n");
						appendFileContent(logfilename, tables[i]+"���ݵ���"+ DateUtil.getTime()+"\n"+"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
						KingbsconfigBS.saveImpDetail(process_run,"1","��ȡ��"+(number1++)+"�ű�"+tables[i]+"���ݣ� ʣ��"+(number2--)+"�š�",imprecordid);
						t_n = uploadbs.saveData_SaxHander3(houzhui.toLowerCase(), tables[i], imprecordid, uuid,from_file, B0111, deptid, impdeptid, tableExt);
						appendFileContent(logfilename, tables[i]+"���ݵ������"+ DateUtil.getTime()+"\n"+"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
						CommonQueryBS.systemOut(tables[i]);
						if("A01".equals( tables[i])){
							k= t_n;
						}
					}
				}
				
								
				if(houzhui.equalsIgnoreCase("hzb")){
					KingbsconfigBS.saveImpDetail(process_run,"1","��ȡ������Ϣ��",imprecordid);
					CommonQueryBS.systemOut("������Ϣ"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݿ�ʼ");
					uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "INFO_EXTEND", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
					CommonQueryBS.systemOut("������Ϣ"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݿ�ʼ");
					uploadbs.saveData_SaxHander2(houzhui.toLowerCase(), "B01_EXT", imprecordid, t_n, uuid, from_file, B0111, deptid, impdeptid);
					CommonQueryBS.systemOut("������Ϣ"+DateUtil.dateToString(new Date(), "yyyyMMdd HH:mi:ss")+"===>�������ݽ���");
					
				}
				
				if(houzhui.equalsIgnoreCase("zip")){
					KingbsconfigBS.saveImpDetail(process_run, "1", "���ڸ���ѧ��ѧλ",imprecordid);
					TYGSsqlUtil.updateA08(tableExt);
				}
				
				Integer n = 0;
				
				//����ְ����
				if("false".equals(fxz)){
					String fxzSql = "SELECT T.a0000 FROM ( SELECT a.a0000 FROM a01"+tableExt+" a WHERE a.a0163 <> '1' ) T";
					
					String numSql = "SELECT count(1) FROM ( SELECT a.a0000 FROM a01"+tableExt+" a WHERE a.a0163 <> '1' ) T";
					Object obj = sess.createSQLQuery(numSql).uniqueResult();
					if(obj!=null){
						n = Integer.parseInt(""+obj);
					}
					String tables2[] = { "A02", "A06", "A08", "A11", "A14", "A15",
								"A29", "A30", "A31", "A36", "A37", "A41", "A53", "A57", 
								"I_E", "A60", "A61", "A62", "A63", "A64" , "A05" , "A68", "A69", "A71", "A99Z1", "A01", "Extra_Tags", "A0193_Tag", "A0194_Tag", "Tag_Rclx", "Tag_Cjlx", "Tag_Sbjysjl", "Tag_Kcclfj", "Tag_Kcclfj2", "Tag_Ndkhdjbfj", "Tag_Dazsbfj"};
					for(int i=0;i<tables2.length;i++){
						sess.createSQLQuery("delete from "+tables2[i]+tableExt+" where A0000 in ("+fxzSql+")").executeUpdate();
					}
				}
				
				imprecord.setProcessstatus("2");
				imprecord.setTotalnumber((k-n) + "");
				sess.update(imprecord);
				sess.flush();
			}
			CommonQueryBS.systemOut("END INSERT---------" +DateUtil.getTime());
			sess.flush();
			appendFileContent(logfilename, "�������"+"\n");
			/**/
			KingbsconfigBS.saveImpDetail(process_run, "2", "��ȡ���", imprecordid);
			try {
				if (houzhui.equalsIgnoreCase("hzb")) {
					new LogUtil("421", "IMP_RECORD", "", "", "������ʱ��", new ArrayList(),userVo).start();
				} else {
					new LogUtil("422", "IMP_RECORD", "", "", "������ʱ��", new ArrayList(),userVo).start();
				}
			} catch (Exception e) {
				try {
					if (houzhui.equalsIgnoreCase("hzb")) {
						new LogUtil().createLog("421", "IMP_RECORD", "", "", "������ʱ��", new ArrayList());
					} else {
						new LogUtil().createLog("422", "IMP_RECORD", "", "", "������ʱ��", new ArrayList());
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			try {
				this.delFolder(unzip+"Table/");
				this.delFolder(unzip+"gwyinfo.xml");
				this.delFolder(filePath);
				this.delFolder(upload_file);
			} catch (Exception e) {
				e.printStackTrace();
			}
			appendFileContent(logfilename, "ɾ�������ļ�"+ DateUtil.getTime()+"\n");
			CommonQueryBS.systemOut("delete file END---------" +DateUtil.getTime());
		} catch (Exception e) {
			e.printStackTrace();
			try {
				KingbsconfigBS.saveImpDetail(process_run ,"4","ʧ��:"+e.getMessage(),imprecordid);
			} catch (Exception e1) {
				e1.printStackTrace();
			}//������ʾ��Ϣ�� 
			uploadbs.rollbackImpTable(imprecordid, tableExt);
			this.delFolder(unzip);
			this.delFolder(filePath);
			this.delFolder(upload_file);
			if(sess != null)
				sess.getTransaction().rollback();
			e.printStackTrace();
		}finally{
			if(sess != null){
				sess.close();
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
