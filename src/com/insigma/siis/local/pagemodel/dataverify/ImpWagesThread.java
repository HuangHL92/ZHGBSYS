package com.insigma.siis.local.pagemodel.dataverify;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import com.insigma.odin.framework.db.DBUtil;
import com.insigma.odin.framework.db.DBUtil.DBType;
import com.insigma.odin.framework.persistence.HBSession;
import com.insigma.odin.framework.persistence.HBUtil;
import com.insigma.odin.framework.privilege.vo.UserVO;
import com.insigma.odin.framework.util.CurrentUser;
import com.insigma.siis.local.business.datavaerify.UploadHzbFileBS;
import com.insigma.siis.local.business.entity.Imprecord;
import com.insigma.siis.local.business.helperUtil.DateUtil;
import com.insigma.siis.local.business.repandrec.local.KingbsconfigBS;
import com.insigma.siis.local.business.utils.Dom4jUtil;
import com.insigma.siis.local.epsoft.config.AppConfig;
import com.insigma.siis.local.epsoft.util.LogUtil;
import com.insigma.siis.local.pagemodel.comm.CommonQueryBS;

public class ImpWagesThread implements Runnable {
	
	private String uuid;
	private String filename;
	private CurrentUser user;
	private UserVO userVo;
    public ImpWagesThread(String filename, String uuid, CurrentUser user,UserVO userVo) {
        this.uuid = uuid;
        this.filename = filename;
        this.user = user;
        this.userVo = userVo;
    }

	@Override
	public void run() {
		String rootPath = "";									// ��Ŀ·��
		String imprecordid = uuid;								// �����¼id
		String process_run = "1";								// ����������
		UploadHzbFileBS uploadbs = new UploadHzbFileBS();		// ҵ����bs
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
				// 001==============�����ļ� ��׺  ��ʽ  �ϴ�·��  ��ѹ·��==============================================================
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
				Zip7z.unzip7zAll(filePath, unzip, null);
				appendFileContent(logfilename, "��ѹ������:"+ DateUtil.getTime()+"\n");					//��¼��־
				KingbsconfigBS.saveImpDetail("1","2","���",imprecordid);								//��¼�������
			process_run = "2";																		//�������
			tableExt = getNo() + DateUtil.timeToString(DateUtil.getTimestamp(), "yyyyMMddHHmmssS");
			
			//002================  ����ͷ�ļ�   =========================================================================
			KingbsconfigBS.saveImpDetail(process_run,"1","������",imprecordid);						//��¼�������
			List<Map<String, String>> headlist = Dom4jUtil
					.gwyinfoF(unzip + "" + "gwyinfo.xml");
			String impdeptid = "";													//���ڵ����id
			List<Imprecord> imprecords = Map2Temp.toTemp("Imprecord", headlist);
			if (imprecords != null && imprecords.size() > 0) {
				Imprecord imprecord = imprecords.get(0);
				imprecord.setImptime(DateUtil.getTimestamp());
				imprecord.setImpuserid(user.getId());
				imprecord.setIsvirety("0");
				imprecord.setFilename(filename);
				imprecord.setFiletype(houzhui);
				imprecord.setImptype("5");
				imprecord.setImpdeptid(impdeptid);
				imprecord.setImpstutas("1");
				imprecord.setPsncount((headlist.get(0).get("psncount")!=null&& !headlist.get(0).get("psncount").equals(""))?Long.parseLong(headlist.get(0).get("psncount")):0L);
				imprecord.setTotalnumber((headlist.get(0).get("psncount")!=null&& !headlist.get(0).get("psncount").equals(""))?headlist.get(0).get("psncount"):"0");
				imprecord.setImprecordid(uuid);
				imprecord.setImptemptable(tableExt);
				sess.update(imprecord);
				imprecordid = imprecord.getImprecordid();
				uploadbs.createTempTableWages(tableExt);	//����������ʱ��
				KingbsconfigBS.saveImpDetail(process_run,"2","���",imprecordid);				//��¼�������
				process_run = "3";																//�������
				//==========  ���������ļ����������ݿ�   =================================================================================
				int number1 = 1;																//�ѽ��������ľ
				int number2 = 1;
				String[] tables = {"A33"};
				if(DBUtil.getDBType().equals(DBType.MYSQL)){
					for(int i = 0;i<tables.length;i++){
						appendFileContent(logfilename, "==============================================="+"\n");
						appendFileContent(logfilename, tables[i]+"���ݵ���"+ DateUtil.getTime()+"\n"+"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
						KingbsconfigBS.saveImpDetail(process_run,"1","��ȡ��"+(number1++)+"�ű�"+tables[i]+"���ݣ� ʣ��"+(number2--)+"�š�",imprecordid);
						uploadbs.saveData_SaxHanderWagesMysql(houzhui.toLowerCase(), tables[i], imprecordid, uuid,from_file, impdeptid, tableExt);
						appendFileContent(logfilename, tables[i]+"���ݵ������"+ DateUtil.getTime()+"\n"+"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
						CommonQueryBS.systemOut(tables[i]);
					}
					
				}else{
					for(int i = 0;i<tables.length;i++){
						appendFileContent(logfilename, "==============================================="+"\n");
						appendFileContent(logfilename, tables[i]+"���ݵ���"+ DateUtil.getTime()+"\n"+"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
						KingbsconfigBS.saveImpDetail(process_run,"1","��ȡ��"+(number1++)+"�ű�"+tables[i]+"���ݣ� ʣ��"+(number2--)+"�š�",imprecordid);
						uploadbs.saveData_SaxHanderWagesOracle(houzhui.toLowerCase(), tables[i], imprecordid, uuid,from_file, impdeptid, tableExt);
						appendFileContent(logfilename, tables[i]+"���ݵ������"+ DateUtil.getTime()+"\n"+"neicun��"+(Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()));
						CommonQueryBS.systemOut(tables[i]);
					}
				}
				
				imprecord.setProcessstatus("2");
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
				delFolder(unzip+"Table/");
				delFolder(unzip+"gwyinfo.xml");
				delFolder(filePath);
				delFolder(upload_file);
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
			delFolder(unzip);
			delFolder(filePath);
			delFolder(upload_file);
			if(sess != null)
				sess.getTransaction().rollback();
			e.printStackTrace();
		}finally{
			if(sess != null){
				sess.close();
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
